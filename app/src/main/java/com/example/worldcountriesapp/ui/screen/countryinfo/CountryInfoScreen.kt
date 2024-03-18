package com.example.worldcountriesapp.ui.screen.countryinfo

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
import com.example.worldcountriesapp.ui.common.CountryInfoDialog
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
    onBackClick: () -> Unit,
    onBorderCountryClick: (countryCode: String) -> Unit,
    onEvent: (CountryInfoScreenEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent(CountryInfoScreenEvent.ResetBorderCountries)
        onEvent(CountryInfoScreenEvent.ResetCountryInfo)
        onEvent(CountryInfoScreenEvent.GetCountryByCode(code))
    }

    when {
        state.isFetchingCountryInfo || (state.countryInfo?.borders != null && state.isFetchingBorderCountries) -> {
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
            if (state.isDialogOpen) {
                CountryInfoDialog(
                    imageData = state.dialogImageData,
                    onDismiss = { onEvent(CountryInfoScreenEvent.SetIsDialogOpen(value = false)) })
            }
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier.padding(all = 32.dp)
                ) {
                    ElevatedButton(
                        onClick = onBackClick,
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
                    state.countryInfo.flags[stringResource(id = R.string.svg).lowercase()]?.let {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(it)
                                .decoderFactory(SvgDecoder.Factory())
                                .build(),
                            modifier = Modifier
                                .clickable {
                                    if (!state.dialogImageData.equals(it)) {
                                        onEvent(CountryInfoScreenEvent.SetDialogImageData(imageData = it))
                                    }
                                    onEvent(CountryInfoScreenEvent.SetIsDialogOpen(value = true))
                                }
                                .padding(bottom = 48.dp),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = state.countryInfo.name.official,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W800,
                        modifier = Modifier.padding(bottom = 32.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Column(modifier = Modifier.padding(bottom = 32.dp)) {
                        state.countryInfo.flags[stringResource(id = R.string.alt).lowercase()]?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(bottom = 32.dp)
                            )
                        }
                        state.countryInfo.coatOfArms[stringResource(id = R.string.svg).lowercase()]?.let {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                        append(
                                            stringResource(id = R.string.coat_of_arms),
                                            stringResource(id = R.string.colon)
                                        )
                                    }
                                },
                                modifier = Modifier.padding(bottom = 12.dp),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(it)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .build(),
                                modifier = Modifier
                                    .clickable {
                                        if (!state.dialogImageData.equals(it)) {
                                            onEvent(CountryInfoScreenEvent.SetDialogImageData(imageData = it))
                                        }
                                        onEvent(CountryInfoScreenEvent.SetIsDialogOpen(value = true))
                                    }
                                    .padding(bottom = 32.dp),
                                contentDescription = null
                            )
                        }
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