package com.example.worldcountriesapp.ui.screen.countryinfo

sealed interface CountryInfoScreenEvent {
    data class GetBorderCountries(val countryCodeList: List<String>) : CountryInfoScreenEvent
    data class GetCountryByCode(val code: String) : CountryInfoScreenEvent
    data object ResetBorderCountries : CountryInfoScreenEvent
    data object ResetCountryInfo : CountryInfoScreenEvent
    data class SetDialogImageData(val imageData: String) : CountryInfoScreenEvent
    data class SetIsDialogOpen(val value: Boolean) : CountryInfoScreenEvent
}