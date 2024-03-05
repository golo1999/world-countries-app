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
import com.example.worldcountriesapp.data.model.country.Translation

@Composable
fun CountryTranslationsText(
    translations: Map<String, Translation>,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(
                    stringResource(id = R.string.translations),
                    stringResource(id = R.string.colon)
                )
            }
            append(stringResource(id = R.string.non_breaking_space))
            translations.entries.forEachIndexed { index, entry ->
                append(
                    entry.value.common,
                    stringResource(id = R.string.non_breaking_space),
                    stringResource(id = R.string.left_parenthesis),
                    entry.key.uppercase(),
                    stringResource(id = R.string.right_parenthesis)
                )
                if (index < translations.size - 1) {
                    append(
                        stringResource(id = R.string.comma),
                        stringResource(id = R.string.non_breaking_space)
                    )
                }
            }
        },
        modifier,
        color
    )
}