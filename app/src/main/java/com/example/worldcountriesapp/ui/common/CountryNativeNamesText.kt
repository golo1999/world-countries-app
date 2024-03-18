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
import com.example.worldcountriesapp.data.entity.Name

@Composable
fun CountryNativeNamesText(
    name: Name,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(
                    when (name.nativeName.isNullOrEmpty() || name.nativeName.size == 1) {
                        true -> stringResource(id = R.string.native_name)
                        false -> stringResource(id = R.string.native_names)
                    }
                )
                append(stringResource(id = R.string.colon))
            }
            append(stringResource(id = R.string.non_breaking_space))
            if (name.nativeName.isNullOrEmpty()) {
                append(stringResource(id = R.string.not_available))
            } else {
                name.nativeName.entries.forEachIndexed { index, entry ->
                    append(
                        entry.value.common,
                        stringResource(id = R.string.non_breaking_space),
                        stringResource(id = R.string.left_parenthesis),
                        entry.key.uppercase(),
                        stringResource(id = R.string.right_parenthesis)
                    )
                    if (index < name.nativeName.size - 1) {
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