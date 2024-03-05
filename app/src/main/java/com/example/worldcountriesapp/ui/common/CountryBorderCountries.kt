package com.example.worldcountriesapp.ui.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worldcountriesapp.R
import com.example.worldcountriesapp.data.model.country.Country

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CountryBorderCountries(
    borderCountries: List<Country>,
    onBorderCountryClick: (countryCode: String) -> Unit
) {
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
        borderCountries.forEach { borderCountry ->
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