package com.example.worldcountriesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcountriesapp.repository.CountryRepository
import com.example.worldcountriesapp.ui.screen.info.CountryInfoScreenEvent
import com.example.worldcountriesapp.ui.screen.info.CountryInfoScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryInfoScreenViewModel @Inject constructor(
    private val countryRepository: CountryRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CountryInfoScreenState())
    val state: StateFlow<CountryInfoScreenState> = _state.asStateFlow()

    fun onEvent(event: CountryInfoScreenEvent) {
        when (event) {
            is CountryInfoScreenEvent.GetBorderCountries -> getBorderCountries(event.countryCodeList)
            is CountryInfoScreenEvent.GetCountryByCode -> getCountryByCode(event.code)
            is CountryInfoScreenEvent.ResetBorderCountries -> resetBorderCountries()
            is CountryInfoScreenEvent.ResetCountryInfo -> resetCountryInfo()
            is CountryInfoScreenEvent.SetIsDialogOpen -> setIsDialogOpen(event.value)
        }
    }

    private fun getBorderCountries(countryCodeList: List<String>) {
        viewModelScope.launch {
            _state.update { currentState -> currentState.copy(isFetchingBorderCountries = true) }
            try {
                countryCodeList.forEach { countryCode ->
                    val response = countryRepository.getCountryByCode(countryCode)
                    if (!response.isSuccessful) {
                        throw Exception("An error occurred while fetching the border countries. Please try again!")
                    }

                    val responseBody = response.body()?.get(0)
                    if (responseBody != null) {
                        _state.update { currentState ->
                            currentState.copy(borderCountries = currentState.borderCountries.plus(responseBody))
                        }
                    }
                }
            } catch (exception: Exception) {
                _state.update { currentState -> currentState.copy(fetchingBorderCountriesException = exception) }
            } finally {
                _state.update { currentState -> currentState.copy(isFetchingBorderCountries = false) }
            }
        }
    }

    private fun getCountryByCode(code: String) {
        viewModelScope.launch {
            _state.update { currentState -> currentState.copy(isFetchingCountryInfo = true) }
            try {
                val response = countryRepository.getCountryByCode(code)
                if (!response.isSuccessful) {
                    throw Exception("An error occurred while fetching the country information. Please try again!")
                }

                val responseBody = response.body()?.get(0)
                _state.update { currentState -> currentState.copy(countryInfo = responseBody) }
                if (responseBody?.borders != null) {
                    getBorderCountries(responseBody.borders)
                }
            } catch (exception: Exception) {
                _state.update { currentState -> currentState.copy(fetchingCountryInfoException = exception) }
            } finally {
                _state.update { currentState -> currentState.copy(isFetchingCountryInfo = false) }
            }
        }
    }

    private fun resetBorderCountries() {
        _state.update { currentState -> currentState.copy(borderCountries = emptyList()) }
    }

    private fun resetCountryInfo() {
        _state.update { currentState -> currentState.copy(countryInfo = null) }
    }

    private fun setIsDialogOpen(value: Boolean) {
        _state.update { currentState -> currentState.copy(isDialogOpen = value) }
    }
}