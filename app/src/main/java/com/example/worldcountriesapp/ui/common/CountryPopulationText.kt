package com.example.worldcountriesapp.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.example.worldcountriesapp.R

@Composable
fun CountryPopulationText(
    population: Long,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(
                    stringResource(id = R.string.population),
                    stringResource(id = R.string.colon)
                )
            }
            val populationToString = population.toString()
            val populationValueText = populationToString.reversed()
                .mapIndexed { index, character ->
                    when (index % 3 == 2 && index < populationToString.length - 1) {
                        true -> "$character${stringResource(id = R.string.comma)}"
                        false -> character
                    }
                }
                .joinToString("")
                .reversed()
            append(
                stringResource(id = R.string.non_breaking_space),
                populationValueText
            )
        },
        modifier,
        color
    )
}