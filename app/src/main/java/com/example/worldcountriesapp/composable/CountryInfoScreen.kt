package com.example.worldcountriesapp.composable

import android.text.Html
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.worldcountriesapp.MainViewModel
import com.example.worldcountriesapp.Screen
import com.example.worldcountriesapp.ui.theme.WorldCountriesAppTheme
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CountryInfoScreen(
    name: String,
    navController: NavController,
    viewModel: MainViewModel
) {
    val countryInfo by viewModel.countryByNameInfo.observeAsState(null)
    val countriesByCodeInfo by viewModel.countriesByCodeInfo.observeAsState(null)

    val countryInfoCopy = countryInfo
    val countriesByCodeInfoCopy = countriesByCodeInfo

    LaunchedEffect(Unit) {
        viewModel.resetCountriesByCodeInfo()
        viewModel.resetCountryInfo()
        viewModel.getCountryByName(name)
    }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.padding(all = 32.dp)
        ) {
            if (countryInfoCopy == null || countriesByCodeInfoCopy == null) {
                Text(
                    text = "Loading...",
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                ElevatedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(bottom = 72.dp),
                    shape = RectangleShape,
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Navigate back",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Back",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400
                    )
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(countryInfoCopy.flags["svg"])
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    modifier = Modifier.padding(bottom = 48.dp),
                    contentDescription = null
                )
                Text(
                    text = countryInfoCopy.name.common,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W800,
                    modifier = Modifier.padding(bottom = 32.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Column(modifier = Modifier.padding(bottom = 32.dp)) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                if (countryInfoCopy.name.nativeName.isNullOrEmpty() ||
                                    countryInfoCopy.name.nativeName.size == 1
                                ) {
                                    append("Native name:")
                                } else {
                                    append("Native names:")
                                }
                            }
                            append(" ")
                            if (countryInfoCopy.name.nativeName.isNullOrEmpty()) {
                                append("N/A")
                            } else {
                                countryInfoCopy.name.nativeName.entries.forEachIndexed { index, entry ->
                                    append("${entry.value.common} (${entry.key.uppercase()})")
                                    if (index < countryInfoCopy.name.nativeName.size - 1) {
                                        append(", ")
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append("Area:")
                            }
                            // Checking if it's a whole number
                            // https://discuss.kotlinlang.org/t/type-check-and-conversion/19533/3
                            val formattedArea: String =
                                when (countryInfoCopy.area.compareTo(countryInfoCopy.area.toInt()) == 0) {
                                    true -> "%,d".format(Locale.getDefault(), countryInfo?.area?.toInt())
                                    false -> "%,.2f".format(Locale.getDefault(), countryInfo?.area)
                                }
                            append(
                                " $formattedArea km${
                                    Html.fromHtml(
                                        "&#178;",
                                        HtmlCompat.FROM_HTML_MODE_LEGACY
                                    )
                                }"
                            )
                        },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append("Population:")
                            }
                            val populationToString = countryInfoCopy.population.toString()
                            val populationValueText = populationToString.reversed()
                                .mapIndexed { index, character ->
                                    if (index % 3 == 2 && index < populationToString.length - 1) {
                                        "$character,"
                                    } else {
                                        character
                                    }
                                }
                                .joinToString("")
                                .reversed()
                            append(
                                " $populationValueText"
                            )
                        },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append("Region:")
                            }
                            append(" ${countryInfoCopy.region}")
                        },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append("Sub region:")
                            }
                            append(" ")
                            if (countryInfoCopy.subregion == null) {
                                append("N/A")
                            } else {
                                append(countryInfoCopy.subregion)
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                if (countryInfoCopy.capital.isNullOrEmpty() || countryInfoCopy.capital.size == 1) {
                                    append("Capital:")
                                } else {
                                    append("Capitals:")
                                }
                            }
                            append(" ")
                            if (countryInfoCopy.capital.isNullOrEmpty()) {
                                append("N/A")
                            } else if (countryInfoCopy.capital.size == 1) {
                                append(countryInfoCopy.capital[0])
                            } else {
                                countryInfoCopy.capital.forEachIndexed { index, capital ->
                                    if (index == countryInfoCopy.capital.size - 1) {
                                        append(capital)
                                    } else {
                                        append("$capital, ")
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                if (countryInfoCopy.continents.size < 2) {
                                    append("Continent:")
                                } else {
                                    append("Continents:")
                                }
                            }
                            append(" ")
                            countryInfoCopy.continents.forEachIndexed { index, continent ->
                                if (index == countryInfoCopy.continents.size - 1) {
                                    append(continent)
                                } else {
                                    append("$continent, ")
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append("Landlocked:")
                            }
                            if (countryInfoCopy.landlocked) {
                                append(" Yes")
                            } else {
                                append(" No")
                            }
                        }
                    )
                }
                Column(
                    modifier = Modifier.padding(
                        bottom = if (countriesByCodeInfoCopy.isNotEmpty()) {
                            32.dp
                        } else {
                            0.dp
                        }
                    )
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                if (countryInfoCopy.tld.isNullOrEmpty() || countryInfoCopy.tld.size == 1) {
                                    append("Top level domain:")
                                } else {
                                    append("Top level domains:")
                                }
                            }
                            append(" ")
                            if (countryInfoCopy.tld.isNullOrEmpty()) {
                                append("N/A")
                            } else {
                                countryInfoCopy.tld.forEachIndexed { index, domain ->
                                    append(domain)
                                    if (index < countryInfoCopy.tld.size - 1) {
                                        append(" / ")
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                if (countryInfoCopy.currencies.isNullOrEmpty() || countryInfoCopy.currencies.size == 1
                                ) {
                                    append("Currency:")
                                } else {
                                    append("Currencies:")
                                }
                            }
                            append(" ")
                            if (countryInfoCopy.currencies.isNullOrEmpty()) {
                                append("N/A")
                            } else {
                                countryInfoCopy.currencies.entries.forEachIndexed { index, entry ->
                                    append(entry.value.name)
                                    if (entry.value.symbol != null) {
                                        append(" (${entry.value.symbol})")
                                    }
                                    if (index < countryInfoCopy.currencies.size - 1) {
                                        append(", ")
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                if (countryInfoCopy.languages.isNullOrEmpty() || countryInfoCopy.languages.size == 1
                                ) {
                                    append("Language:")
                                } else {
                                    append("Languages:")
                                }
                            }
                            append(" ")
                            if (countryInfoCopy.languages.isNullOrEmpty()) {
                                append("N/A")
                            } else {
                                countryInfoCopy.languages.entries.forEachIndexed { index, entry ->
                                    append(entry.value)
                                    if (index < countryInfoCopy.languages.size - 1) {
                                        append(", ")
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                if (countryInfoCopy.timezones.size < 2
                                ) {
                                    append("Timezone:")
                                } else {
                                    append("Timezones:")
                                }
                            }
                            append(" ")
                            if (countryInfoCopy.timezones.isEmpty()) {
                                append("N/A")
                            } else {
                                countryInfoCopy.timezones.forEachIndexed { index, timezone ->
                                    append(timezone)
                                    if (index < countryInfoCopy.timezones.size - 1) {
                                        append(", ")
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append("Start of week:")
                            }
                            append(
                                " ${
                                    countryInfoCopy.startOfWeek.replaceFirstChar { character ->
                                        if (character.isLowerCase()) character.titlecase(
                                            Locale.getDefault()
                                        ) else character.toString()
                                    }
                                }"
                            )
                        },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append("Driving side:")
                            }
                            append(
                                " ${
                                    countryInfoCopy.car.side.replaceFirstChar { character ->
                                        if (character.isLowerCase()) character.titlecase(
                                            Locale.getDefault()
                                        ) else character.toString()
                                    }
                                }"
                            )
                        }
                    )
                }
                if (countriesByCodeInfoCopy.isNotEmpty()) {
                    Text(
                        text = "Border countries:",
                        modifier = Modifier.padding(bottom = 24.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        countriesByCodeInfoCopy.forEach { borderCountry ->
                            ElevatedButton(
                                onClick = {
                                    navController.navigate(
                                        route = "${Screen.CountryInfoScreen.route}/${borderCountry.name.common.lowercase()}"
                                    )
                                },
                                shape = RectangleShape,
                                colors = ButtonDefaults.elevatedButtonColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                ),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
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

@Preview(showBackground = true)
@Composable
fun CountryInfoScreenPreview() {
    WorldCountriesAppTheme {
        CountryInfoScreen(name = "romania", navController = rememberNavController(), viewModel = MainViewModel())
    }
}