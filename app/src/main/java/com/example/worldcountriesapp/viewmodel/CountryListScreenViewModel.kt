package com.example.worldcountriesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcountriesapp.repository.CountryRepository
import com.example.worldcountriesapp.repository.RegionRepository
import com.example.worldcountriesapp.ui.screen.countrylist.CountryListScreenEvent
import com.example.worldcountriesapp.ui.screen.countrylist.CountryListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListScreenViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
    private val regionRepository: RegionRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CountryListScreenState())
    val state: StateFlow<CountryListScreenState> = _state.asStateFlow()

    suspend fun onEvent(event: CountryListScreenEvent) {
        when (event) {
            is CountryListScreenEvent.FilterCountriesByName -> filterCountriesByName(event.name)
            is CountryListScreenEvent.FilterCountriesByRegion -> filterCountriesByRegion(event.region)
            is CountryListScreenEvent.GetAllCountries -> getAllCountries()
            is CountryListScreenEvent.ResetFilteredCountries -> resetFilteredCountries()
            is CountryListScreenEvent.SetIsFirstRender -> setIsFirstRender(event.value)
            is CountryListScreenEvent.SetIsSearchBarActive -> setIsSearchBarActive(event.value)
            is CountryListScreenEvent.SetIsSearchBarExpanded -> setIsSearchBarExpanded(event.value)
            is CountryListScreenEvent.SetSearchQuery -> setSearchQuery(event.query)
            is CountryListScreenEvent.SetSelectedRegion -> setSelectedRegion(event.region)
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

    private suspend fun filterCountriesByRegion(region: String) {
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
                    currentState.copy(filteredCountries = countryRepository.getByRegion(region))
                }

                else -> _state.update { currentState ->
                    currentState.copy(filteredCountries = currentState.allCountries.filter {
                        it.region == region && it.name.common.lowercase()
                            .contains(currentState.searchQuery.lowercase())
                    })
                }
            }
        }
    }

    private fun getAllCountries() {
        viewModelScope.launch {
            try {
                val countries = countryRepository.getAll()
                val regions = regionRepository.getAll()

                if (countries.isEmpty()) {
                    throw Exception("An error occurred while fetching the country list. Please try again!")
                }

                if (regions.isEmpty()) {
                    throw Exception("An error occurred while fetching the region list. Please try again!")
                }

                _state.update { currentState ->
                    currentState.copy(allCountries = countries)
                }
                _state.update { currentState ->
                    currentState.copy(allRegions = regions)
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