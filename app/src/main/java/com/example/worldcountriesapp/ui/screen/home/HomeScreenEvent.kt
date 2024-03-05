package com.example.worldcountriesapp.ui.screen.home

sealed interface HomeScreenEvent {
    data class FilterCountriesByName(val name: String) : HomeScreenEvent
    data class FilterCountriesByRegion(val region: String) : HomeScreenEvent
    data object GetAllCountries : HomeScreenEvent
    data object ResetFilteredCountries : HomeScreenEvent
    data class SetIsFirstRender(val value: Boolean) : HomeScreenEvent
    data class SetIsSearchBarActive(val value: Boolean) : HomeScreenEvent
    data class SetIsSearchBarExpanded(val value: Boolean) : HomeScreenEvent
    data class SetSearchQuery(val query: String) : HomeScreenEvent
    data class SetSelectedRegion(val region: String) : HomeScreenEvent
}