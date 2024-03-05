package com.example.worldcountriesapp.ui.screen.info

import android.os.Parcelable
import com.example.worldcountriesapp.data.model.country.Country
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class CountryInfoScreenState(
    val borderCountries: @RawValue List<Country> = emptyList(),
    val countryInfo: @RawValue Country? = null,
    val dialogImageData: String? = null,
    val fetchingCountryInfoException: Exception? = null,
    val fetchingBorderCountriesException: Exception? = null,
    val isDialogOpen: Boolean = false,
    val isFetchingCountryInfo: Boolean = true,
    val isFetchingBorderCountries: Boolean = true
) : Parcelable
