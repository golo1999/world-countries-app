package com.example.worldcountriesapp.ui.screen.info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.worldcountriesapp.R
import com.example.worldcountriesapp.ui.common.CountryAreaText
import com.example.worldcountriesapp.ui.common.CountryBorderCountries
import com.example.worldcountriesapp.ui.common.CountryCapitalText
import com.example.worldcountriesapp.ui.common.CountryCarSideText
import com.example.worldcountriesapp.ui.common.CountryContinentsText
import com.example.worldcountriesapp.ui.common.CountryCurrenciesText
import com.example.worldcountriesapp.ui.common.CountryFlagDialog
import com.example.worldcountriesapp.ui.common.CountryLandlockedText
import com.example.worldcountriesapp.ui.common.CountryLanguagesText
import com.example.worldcountriesapp.ui.common.CountryNativeNamesText
import com.example.worldcountriesapp.ui.common.CountryPopulationText
import com.example.worldcountriesapp.ui.common.CountryRegionText
import com.example.worldcountriesapp.ui.common.CountryStartOfWeekText
import com.example.worldcountriesapp.ui.common.CountrySubregionText
import com.example.worldcountriesapp.ui.common.CountryTimezonesText
import com.example.worldcountriesapp.ui.common.CountryTopLevelDomainText
import com.example.worldcountriesapp.ui.common.CountryTranslationsText
import com.example.worldcountriesapp.ui.screen.error.ErrorScreen
import com.example.worldcountriesapp.ui.screen.loading.LoadingScreen

@Composable
fun CountryInfoScreen(
    code: String,
    state: CountryInfoScreenState,
    onEvent: (CountryInfoScreenEvent) -> Unit,
    onBackClick: () -> Unit,
    onBorderCountryClick: (countryCode: String) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent(CountryInfoScreenEvent.ResetBorderCountries)
        onEvent(CountryInfoScreenEvent.ResetCountryInfo)
        onEvent(CountryInfoScreenEvent.GetCountryByCode(code))
    }

    when {
        state.isFetchingCountryInfo -> {
            LoadingScreen()
        }

        state.countryInfo?.borders != null && state.isFetchingBorderCountries -> {
            LoadingScreen()
        }

        state.fetchingCountryInfoException != null -> {
            ErrorScreen(exception = state.fetchingCountryInfoException)
        }

        state.fetchingBorderCountriesException != null -> {
            ErrorScreen(exception = state.fetchingBorderCountriesException)
        }

        state.countryInfo == null -> {
            ErrorScreen(exception = Exception("An error occurred while retrieving the country information. Please try again!"))
        }

        else -> {
            val svgFlag = state.countryInfo.flags[stringResource(id = R.string.svg).lowercase()]
            if (state.isDialogOpen) {
                CountryFlagDialog(
                    flagPath = svgFlag,
                    onDismiss = { onEvent(CountryInfoScreenEvent.SetIsDialogOpen(value = false)) })
            }
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier.padding(all = 32.dp)
                ) {
                    ElevatedButton(
                        onClick = { onBackClick() },
                        modifier = Modifier.padding(bottom = 72.dp),
                        shape = RectangleShape,
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = when (isSystemInDarkTheme()) {
                                true -> MaterialTheme.colorScheme.surface
                                false -> MaterialTheme.colorScheme.background
                            },
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.back),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400
                        )
                    }
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(svgFlag)
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        modifier = Modifier
                            .clickable {
                                onEvent(CountryInfoScreenEvent.SetIsDialogOpen(value = true))
                            }
                            .padding(bottom = 48.dp),
                        contentDescription = null
                    )
                    Text(
                        text = state.countryInfo.name.common,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W800,
                        modifier = Modifier.padding(bottom = 32.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Column(modifier = Modifier.padding(bottom = 32.dp)) {
                        CountryNativeNamesText(
                            name = state.countryInfo.name,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        CountryAreaText(
                            area = state.countryInfo.area,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        CountryPopulationText(
                            population = state.countryInfo.population,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        CountryRegionText(
                            region = state.countryInfo.region,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        CountrySubregionText(
                            subregion = state.countryInfo.subregion,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        CountryCapitalText(
                            capitals = state.countryInfo.capital,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        CountryContinentsText(
                            continents = state.countryInfo.continents,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        CountryLandlockedText(landlocked = state.countryInfo.landlocked)
                    }
                    Column(
                        modifier = Modifier.padding(
                            bottom = when (state.borderCountries.isNotEmpty()) {
                                true -> 32.dp
                                false -> 0.dp
                            }
                        )
                    ) {
                        CountryTopLevelDomainText(
                            tld = state.countryInfo.tld,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        CountryCurrenciesText(
                            currencies = state.countryInfo.currencies,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        CountryLanguagesText(
                            languages = state.countryInfo.languages,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        CountryTimezonesText(
                            timezones = state.countryInfo.timezones,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        CountryStartOfWeekText(
                            startOfWeek = state.countryInfo.startOfWeek,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        CountryCarSideText(
                            driving = state.countryInfo.car,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        CountryTranslationsText(translations = state.countryInfo.translations)
                    }
                    if (state.borderCountries.isNotEmpty()) {
                        CountryBorderCountries(
                            borderCountries = state.borderCountries,
                            onBorderCountryClick = { code -> onBorderCountryClick(code) })
                    }
                }
            }
        }
    }
}