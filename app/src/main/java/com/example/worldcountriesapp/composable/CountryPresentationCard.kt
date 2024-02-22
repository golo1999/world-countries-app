package com.example.worldcountriesapp.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.worldcountriesapp.Mocked
import com.example.worldcountriesapp.retrofit.country.CountryPresentation
import com.example.worldcountriesapp.ui.theme.WorldCountriesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryPresentationCard(
    countryPresentation: CountryPresentation,
    isFirst: Boolean,
    isLast: Boolean,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 32.dp,
                top = when (isFirst) {
                    true -> 16.dp
                    false -> 8.dp
                },
                end = 32.dp,
                bottom = when (isLast) {
                    true -> 32.dp
                    false -> 16.dp
                }
            ),
        shape = RoundedCornerShape(size = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(countryPresentation.flags["svg"])
                .decoderFactory(SvgDecoder.Factory())
                .build(),
            contentDescription = null
        )
        Column(
            Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            Text(
                text = countryPresentation.name.common,
                Modifier.padding(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 16.dp
                ),
                fontSize = 20.sp,
                fontWeight = FontWeight.W800,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Population:")
                    }
                    val populationToString = countryPresentation.population.toString()
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
                    append(" $populationValueText")
                },
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Region:")
                    }
                    append(" ${countryPresentation.region}")
                },
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        if (countryPresentation.capital.isNullOrEmpty() || countryPresentation.capital.size == 1) {
                            append("Capital:")
                        } else {
                            append("Capitals:")
                        }
                    }
                    append(" ")
                    if (countryPresentation.capital.isNullOrEmpty()) {
                        append("N/A")
                    } else if (countryPresentation.capital.size == 1) {
                        append(countryPresentation.capital[0])
                    } else {
                        countryPresentation.capital.forEachIndexed { index, c ->
                            append(c)
                            if (index < countryPresentation.capital.size - 1) {
                                append(", ")
                            }
                        }
                    }
                },
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryPresentationCardPreview() {
    WorldCountriesAppTheme {
        CountryPresentationCard(
            countryPresentation = Mocked.countryPresentation,
            isFirst = true,
            isLast = true
        )
    }
}