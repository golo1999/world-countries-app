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
fun CountryTopLevelDomainText(
    tld: List<String>?,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(
                    when (tld.isNullOrEmpty() || tld.size == 1) {
                        true -> stringResource(id = R.string.top_level_domain)
                        false -> stringResource(id = R.string.top_level_domains)
                    }
                )
                append(stringResource(id = R.string.colon))
            }
            append(stringResource(id = R.string.non_breaking_space))
            if (tld.isNullOrEmpty()) {
                append(stringResource(id = R.string.not_available))
            } else {
                tld.forEachIndexed { index, domain ->
                    append(domain)
                    if (index < tld.size - 1) {
                        append(
                            stringResource(id = R.string.non_breaking_space),
                            stringResource(id = R.string.slash),
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