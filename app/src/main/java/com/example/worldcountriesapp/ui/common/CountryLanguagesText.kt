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
fun CountryLanguagesText(
    languages: Map<String, String>?,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(
                    when (languages.isNullOrEmpty() || languages.size == 1) {
                        true -> stringResource(id = R.string.language)
                        false -> stringResource(id = R.string.languages)
                    }
                )
                append(stringResource(id = R.string.colon))
            }
            append(stringResource(id = R.string.non_breaking_space))
            if (languages.isNullOrEmpty()) {
                append(stringResource(id = R.string.not_available))
            } else {
                languages.entries.forEachIndexed { index, entry ->
                    append(entry.value)
                    if (index < languages.size - 1) {
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