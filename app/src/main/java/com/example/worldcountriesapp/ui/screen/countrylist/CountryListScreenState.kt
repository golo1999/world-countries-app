package com.example.worldcountriesapp.ui.screen.countrylist

import android.os.Parcelable
import com.example.worldcountriesapp.data.model.country.CountryPresentation
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class CountryListScreenState(
    val allCountries: @RawValue List<CountryPresentation> = emptyList(),
    val allRegions: List<String> = emptyList(),
    val fetchingCountriesException: Exception? = null,
    val filteredCountries: @RawValue List<CountryPresentation> = emptyList(),
    val isFetchingAllCountries: Boolean = true,
    val isFirstRender: Boolean = true,
    val isSearchBarActive: Boolean = false,
    val isSearchBarExpanded: Boolean = false,
    val searchQuery: String = "",
    val selectedRegion: String = "All"
) : Parcelable
