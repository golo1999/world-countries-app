package com.example.worldcountriesapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.worldcountriesapp.mock.MockedData
import com.example.worldcountriesapp.R
import com.example.worldcountriesapp.data.model.country.CountryPresentation
import com.example.worldcountriesapp.ui.theme.WorldCountriesAppTheme

@Composable
fun CountryPresentationCard(
    countryPresentation: CountryPresentation,
    isFirst: Boolean,
    isLast: Boolean,
    onClick: () -> Unit = {}
) {
    Card(
        onClick,
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
                .data(countryPresentation.flags[stringResource(id = R.string.svg).lowercase()])
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
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.W800,
                color = MaterialTheme.colorScheme.onSurface
            )
            CountryPopulationText(population = countryPresentation.population)
            CountryRegionText(region = countryPresentation.region)
            CountryCapitalText(capitals = countryPresentation.capital)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CountryPresentationCardPreview() {
    WorldCountriesAppTheme {
        CountryPresentationCard(
            countryPresentation = MockedData.countryPresentation,
            isFirst = true,
            isLast = true
        )
    }
}