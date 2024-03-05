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
import com.example.worldcountriesapp.data.model.country.Currency

@Composable
fun CountryCurrenciesText(
    currencies: Map<String, Currency>?,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(
                    when (currencies.isNullOrEmpty() || currencies.size == 1) {
                        true -> stringResource(id = R.string.currency)
                        false -> stringResource(id = R.string.currencies)
                    }
                )
                append(stringResource(id = R.string.colon))
            }
            append(stringResource(id = R.string.non_breaking_space))
            if (currencies.isNullOrEmpty()) {
                append(stringResource(id = R.string.not_available))
            } else {
                currencies.entries.forEachIndexed { index, entry ->
                    append(entry.value.name)
                    if (entry.value.symbol != null) {
                        append(
                            stringResource(id = R.string.non_breaking_space),
                            stringResource(id = R.string.left_parenthesis),
                            entry.value.symbol,
                            stringResource(id = R.string.right_parenthesis)
                        )
                    }
                    if (index < currencies.size - 1) {
                        append(
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