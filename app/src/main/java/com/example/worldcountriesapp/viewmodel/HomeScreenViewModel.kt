package com.example.worldcountriesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcountriesapp.repository.CountryRepository
import com.example.worldcountriesapp.ui.screen.home.HomeScreenEvent
import com.example.worldcountriesapp.ui.screen.home.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val countryRepository: CountryRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.FilterCountriesByName -> filterCountriesByName(event.name)
            is HomeScreenEvent.FilterCountriesByRegion -> filterCountriesByRegion(event.region)
            is HomeScreenEvent.GetAllCountries -> getAllCountries()
            is HomeScreenEvent.ResetFilteredCountries -> resetFilteredCountries()
            is HomeScreenEvent.SetIsFirstRender -> setIsFirstRender(event.value)
            is HomeScreenEvent.SetIsSearchBarActive -> setIsSearchBarActive(event.value)
            is HomeScreenEvent.SetIsSearchBarExpanded -> setIsSearchBarExpanded(event.value)
            is HomeScreenEvent.SetSearchQuery -> setSearchQuery(event.query)
            is HomeScreenEvent.SetSelectedRegion -> setSelectedRegion(event.region)
        }
    }

    private fun filterCountriesByName(name: String) {
        when (name) {
            "" -> {
                when (state.value.selectedRegion) {
                    "All" -> resetFilteredCountries()
                    else -> _state.update { currentState ->
                        currentState.copy(
                            filteredCountries = currentState.allCountries.filter {
                                it.region == currentState.selectedRegion
                            }
                        )
                    }
                }
            }

            else -> when (state.value.selectedRegion) {
                "All" -> _state.update { currentState ->
                    currentState.copy(filteredCountries = currentState.allCountries.filter {
                        it.name.common.lowercase().contains(name.lowercase())
                    })
                }

                else -> _state.update { currentState ->
                    currentState.copy(filteredCountries = currentState.allCountries.filter {
                        it.region == currentState.selectedRegion && it.name.common.lowercase()
                            .contains(name.lowercase())
                    })
                }
            }
        }
    }

    private fun filterCountriesByRegion(region: String) {
        when (region) {
            "All" -> when (state.value.searchQuery) {
                "" -> resetFilteredCountries()
                else -> _state.update { currentState ->
                    currentState.copy(filteredCountries = currentState.allCountries.filter {
                        it.name.common.lowercase().contains(currentState.searchQuery.lowercase())
                    })
                }
            }

            else -> when (state.value.searchQuery) {
                "" -> _state.update { currentState ->
                    currentState.copy(filteredCountries = currentState.allCountries.filter { it.region == region })
                }

                else -> _state.update { currentState ->
                    currentState.copy(filteredCountries = currentState.allCountries.filter {
                        it.region == region && it.name.common.lowercase().contains(currentState.searchQuery.lowercase())
                    })
                }
            }
        }
    }

    private fun getAllCountries() {
        viewModelScope.launch {
            try {
                val response = countryRepository.getAllCountries()
                if (!response.isSuccessful) {
                    throw Exception("An error occurred while fetching the country list. Please try again!")
                }

                val responseBody = response.body()
                if (responseBody.isNullOrEmpty()) {
                    throw Exception("An error occurred while fetching the country list. Please try again!")
                }

                _state.update { currentState ->
                    currentState.copy(allCountries = responseBody.sortedBy { it.name.common })
                }
                _state.update { currentState ->
                    currentState.copy(
                        allRegions = currentState.allCountries.asSequence().distinctBy { it.region }.map { it.region }
                            .sortedDescending().toList().plus("All").reversed()
                    )
                }
                filterCountriesByRegion(state.value.selectedRegion)
            } catch (exception: Exception) {
                _state.update { currentState -> currentState.copy(fetchingCountriesException = exception) }
            } finally {
                _state.update { currentState -> currentState.copy(isFetchingAllCountries = false) }
            }
        }
    }

    private fun resetFilteredCountries() {
        _state.update { currentState -> currentState.copy(filteredCountries = currentState.allCountries) }
    }

    private fun setIsFirstRender(value: Boolean) {
        _state.update { currentState -> currentState.copy(isFirstRender = value) }
    }

    private fun setIsSearchBarActive(value: Boolean) {
        _state.update { currentState -> currentState.copy(isSearchBarActive = value) }
    }

    private fun setIsSearchBarExpanded(value: Boolean) {
        _state.update { currentState -> currentState.copy(isSearchBarExpanded = value) }
    }

    private fun setSearchQuery(query: String) {
        _state.update { currentState -> currentState.copy(searchQuery = query) }
    }

    private fun setSelectedRegion(region: String) {
        _state.update { currentState -> currentState.copy(selectedRegion = region) }
    }
}