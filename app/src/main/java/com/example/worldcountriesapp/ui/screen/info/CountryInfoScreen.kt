package com.example.worldcountriesapp.ui.screen.info

import android.text.Html
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.worldcountriesapp.R
import com.example.worldcountriesapp.ui.common.CountryCapitalText
import com.example.worldcountriesapp.ui.common.CountryFlagDialog
import com.example.worldcountriesapp.ui.common.CountryPopulationText
import com.example.worldcountriesapp.ui.common.CountryRegionText
import com.example.worldcountriesapp.ui.screen.error.ErrorScreen
import com.example.worldcountriesapp.ui.screen.loading.LoadingScreen
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
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

    if (state.isFetchingCountryInfo || (state.countryInfo?.borders != null && state.isFetchingBorderCountries)) {
        LoadingScreen()
    } else if (state.fetchingCountryInfoException != null) {
        ErrorScreen(exception = state.fetchingCountryInfoException)
    } else if (state.fetchingBorderCountriesException != null) {
        ErrorScreen(exception = state.fetchingBorderCountriesException)
    } else if (state.countryInfo == null) {
        ErrorScreen(
            exception = Exception("An error occurred while retrieving the country information. Please try again!")
        )
    } else {
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
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append(
                                    when (state.countryInfo.name.nativeName.isNullOrEmpty() ||
                                            state.countryInfo.name.nativeName.size == 1) {
                                        true -> stringResource(id = R.string.native_name)
                                        false -> stringResource(id = R.string.native_names)
                                    }
                                )
                                append(stringResource(id = R.string.colon))
                            }
                            append(stringResource(id = R.string.non_breaking_space))
                            if (state.countryInfo.name.nativeName.isNullOrEmpty()) {
                                append(stringResource(id = R.string.not_available))
                            } else {
                                state.countryInfo.name.nativeName.entries.forEachIndexed { index, entry ->
                                    append(
                                        entry.value.common,
                                        stringResource(id = R.string.non_breaking_space),
                                        stringResource(id = R.string.left_parenthesis),
                                        entry.key.uppercase(),
                                        stringResource(id = R.string.right_parenthesis)
                                    )
                                    if (index < state.countryInfo.name.nativeName.size - 1) {
                                        append(
                                            stringResource(id = R.string.comma),
                                            stringResource(id = R.string.non_breaking_space)
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append(
                                    stringResource(id = R.string.area),
                                    stringResource(id = R.string.colon)
                                )
                            }
                            // Checking if it's a whole number
                            // https://discuss.kotlinlang.org/t/type-check-and-conversion/19533/3
                            val formattedArea: String =
                                when (state.countryInfo.area.compareTo(state.countryInfo.area.toInt()) == 0) {
                                    true -> "%,d".format(Locale.getDefault(), state.countryInfo.area.toInt())
                                    false -> "%,.2f".format(Locale.getDefault(), state.countryInfo.area)
                                }
                            append(
                                stringResource(id = R.string.non_breaking_space),
                                formattedArea,
                                stringResource(id = R.string.non_breaking_space),
                                stringResource(id = R.string.kilometre_abbreviated),
                                Html.fromHtml(
                                    stringResource(id = R.string.superscript_two),
                                    HtmlCompat.FROM_HTML_MODE_LEGACY
                                )
                            )
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    CountryPopulationText(
                        population = state.countryInfo.population,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    CountryRegionText(
                        region = state.countryInfo.region,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append(
                                    stringResource(id = R.string.sub_region),
                                    stringResource(id = R.string.colon)
                                )
                            }
                            append(
                                stringResource(id = R.string.non_breaking_space),
                                when (state.countryInfo.subregion == null) {
                                    true -> stringResource(id = R.string.not_available)
                                    false -> state.countryInfo.subregion
                                }
                            )
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    CountryCapitalText(
                        capitals = state.countryInfo.capital,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append(
                                    when (state.countryInfo.continents.size < 2) {
                                        true -> stringResource(id = R.string.continent)
                                        false -> stringResource(id = R.string.continents)
                                    }
                                )
                                append(stringResource(id = R.string.colon))
                            }
                            append(stringResource(id = R.string.non_breaking_space))
                            state.countryInfo.continents.forEachIndexed { index, continent ->
                                append(continent)
                                if (index < state.countryInfo.continents.size - 1) {
                                    append(
                                        stringResource(id = R.string.comma),
                                        stringResource(id = R.string.non_breaking_space)
                                    )
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append(
                                    stringResource(id = R.string.landlocked),
                                    stringResource(id = R.string.colon)
                                )
                            }
                            append(
                                stringResource(id = R.string.non_breaking_space),
                                when (state.countryInfo.landlocked) {
                                    true -> stringResource(id = R.string.yes)
                                    false -> stringResource(id = R.string.no)
                                }
                            )
                        },
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Column(
                    modifier = Modifier.padding(
                        bottom = when (state.borderCountries.isNotEmpty()) {
                            true -> 32.dp
                            false -> 0.dp
                        }
                    )
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append(
                                    when (state.countryInfo.tld.isNullOrEmpty() || state.countryInfo.tld.size == 1) {
                                        true -> stringResource(id = R.string.top_level_domain)
                                        false -> stringResource(id = R.string.top_level_domains)
                                    }
                                )
                                append(stringResource(id = R.string.colon))
                            }
                            append(stringResource(id = R.string.non_breaking_space))
                            if (state.countryInfo.tld.isNullOrEmpty()) {
                                append(stringResource(id = R.string.not_available))
                            } else {
                                state.countryInfo.tld.forEachIndexed { index, domain ->
                                    append(domain)
                                    if (index < state.countryInfo.tld.size - 1) {
                                        append(
                                            stringResource(id = R.string.non_breaking_space),
                                            stringResource(id = R.string.slash),
                                            stringResource(id = R.string.non_breaking_space)
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append(
                                    when (
                                        state.countryInfo.currencies.isNullOrEmpty() ||
                                                state.countryInfo.currencies.size == 1
                                    ) {
                                        true -> stringResource(id = R.string.currency)
                                        false -> stringResource(id = R.string.currencies)
                                    }
                                )
                                append(stringResource(id = R.string.colon))
                            }
                            append(stringResource(id = R.string.non_breaking_space))
                            if (state.countryInfo.currencies.isNullOrEmpty()) {
                                append(stringResource(id = R.string.not_available))
                            } else {
                                state.countryInfo.currencies.entries.forEachIndexed { index, entry ->
                                    append(entry.value.name)
                                    if (entry.value.symbol != null) {
                                        append(
                                            stringResource(id = R.string.non_breaking_space),
                                            stringResource(id = R.string.left_parenthesis),
                                            entry.value.symbol,
                                            stringResource(id = R.string.right_parenthesis)
                                        )
                                    }
                                    if (index < state.countryInfo.currencies.size - 1) {
                                        append(
                                            stringResource(id = R.string.comma),
                                            stringResource(id = R.string.non_breaking_space)
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append(
                                    when (
                                        state.countryInfo.languages.isNullOrEmpty() ||
                                                state.countryInfo.languages.size == 1
                                    ) {
                                        true -> stringResource(id = R.string.language)
                                        false -> stringResource(id = R.string.languages)
                                    }
                                )
                                append(stringResource(id = R.string.colon))
                            }
                            append(stringResource(id = R.string.non_breaking_space))
                            if (state.countryInfo.languages.isNullOrEmpty()) {
                                append(stringResource(id = R.string.not_available))
                            } else {
                                state.countryInfo.languages.entries.forEachIndexed { index, entry ->
                                    append(entry.value)
                                    if (index < state.countryInfo.languages.size - 1) {
                                        append(
                                            stringResource(id = R.string.comma),
                                            stringResource(id = R.string.non_breaking_space)
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append(
                                    when (state.countryInfo.timezones.size < 2) {
                                        true -> stringResource(id = R.string.timezone)
                                        false -> stringResource(id = R.string.timezones)
                                    }
                                )
                                append(stringResource(id = R.string.colon))
                            }
                            append(stringResource(id = R.string.non_breaking_space))
                            if (state.countryInfo.timezones.isEmpty()) {
                                append(stringResource(id = R.string.not_available))
                            } else {
                                state.countryInfo.timezones.forEachIndexed { index, timezone ->
                                    append(timezone)
                                    if (index < state.countryInfo.timezones.size - 1) {
                                        append(
                                            stringResource(id = R.string.comma),
                                            stringResource(id = R.string.non_breaking_space)
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append(
                                    stringResource(id = R.string.start_of_the_week),
                                    stringResource(id = R.string.colon)
                                )
                            }
                            append(
                                stringResource(id = R.string.non_breaking_space),
                                state.countryInfo.startOfWeek.replaceFirstChar { character ->
                                    when (character.isLowerCase()) {
                                        true -> character.titlecase(Locale.getDefault())
                                        false -> character.toString()
                                    }
                                }
                            )
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append(
                                    stringResource(id = R.string.driving_side),
                                    stringResource(id = R.string.colon)
                                )
                            }
                            append(
                                stringResource(id = R.string.non_breaking_space),
                                state.countryInfo.car.side.replaceFirstChar { character ->
                                    when (character.isLowerCase()) {
                                        true -> character.titlecase(Locale.getDefault())
                                        false -> character.toString()
                                    }
                                }
                            )
                        },
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                if (state.borderCountries.isNotEmpty()) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append(
                                    stringResource(id = R.string.border_countries),
                                    stringResource(id = R.string.colon)
                                )
                            }
                        },
                        modifier = Modifier.padding(bottom = 24.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(space = 4.dp)
                    ) {
                        state.borderCountries.forEach { borderCountry ->
                            ElevatedButton(
                                onClick = {
                                    // Finding the first uppercase alt spelling
                                    val borderCountryCode =
                                        borderCountry.altSpellings.filter { it == it.uppercase() }[0]
                                    onBorderCountryClick(borderCountryCode)
                                },
                                shape = RectangleShape,
                                colors = ButtonDefaults.elevatedButtonColors(
                                    containerColor = when (isSystemInDarkTheme()) {
                                        true -> MaterialTheme.colorScheme.surface
                                        false -> MaterialTheme.colorScheme.background
                                    },
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                ),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                            ) {
                                Text(
                                    text = borderCountry.name.common,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.W400
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}