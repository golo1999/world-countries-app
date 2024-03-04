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
fun CountryCapitalText(
    capitals: List<String>?,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(
                    when (capitals.isNullOrEmpty() || capitals.size == 1) {
                        true -> stringResource(id = R.string.capital)
                        false -> stringResource(id = R.string.capitals)
                    },
                    stringResource(id = R.string.colon)
                )
            }
            append(stringResource(id = R.string.non_breaking_space))
            if (capitals.isNullOrEmpty()) {
                append(stringResource(id = R.string.not_available))
            } else if (capitals.size == 1) {
                append(capitals[0])
            } else {
                capitals.forEachIndexed { index, capital ->
                    if (index == capitals.size - 1) {
                        append(capital)
                    } else {
                        append(
                            capital,
                            stringResource(id = R.string.comma),
                            stringResource(id = R.string.non_breaking_space)
                        )
                    }
                }
            }
        },
        modifier,
        color
    )
}