package com.example.worldcountriesapp.ui.screen.countrylist

sealed interface CountryListScreenEvent {
    data class FilterCountriesByName(val name: String) : CountryListScreenEvent
    data class FilterCountriesByRegion(val region: String) : CountryListScreenEvent
    data object GetAllCountries : CountryListScreenEvent
    data object ResetFilteredCountries : CountryListScreenEvent
    data class SetIsFirstRender(val value: Boolean) : CountryListScreenEvent
    data class SetIsSearchBarActive(val value: Boolean) : CountryListScreenEvent
    data class SetIsSearchBarExpanded(val value: Boolean) : CountryListScreenEvent
    data class SetSearchQuery(val query: String) : CountryListScreenEvent
    data class SetSelectedRegion(val region: String) : CountryListScreenEvent
}