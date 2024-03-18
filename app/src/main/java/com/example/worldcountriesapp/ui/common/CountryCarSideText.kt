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
import com.example.worldcountriesapp.data.entity.Driving
import java.util.Locale

@Composable
fun CountryCarSideText(
    driving: Driving,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(
                    stringResource(id = R.string.driving_side),
                    stringResource(id = R.string.colon)
                )
            }
            append(
                stringResource(id = R.string.non_breaking_space),
                driving.side.replaceFirstChar { character ->
                    when (character.isLowerCase()) {
                        true -> character.titlecase(Locale.getDefault())
                        false -> character.toString()
                    }
                }
            )
        },
        modifier,
        color
    )
}