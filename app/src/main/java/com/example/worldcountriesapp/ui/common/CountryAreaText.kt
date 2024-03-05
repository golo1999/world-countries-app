package com.example.worldcountriesapp.ui.common

import android.text.Html
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
import androidx.core.text.HtmlCompat
import com.example.worldcountriesapp.R
import java.util.Locale

@Composable
fun CountryAreaText(
    area: Double,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(
                    stringResource(id = R.string.area),
                    stringResource(id = R.string.colon)
                )
            }
            // Checking if it's a whole number
            // https://discuss.kotlinlang.org/t/type-check-and-conversion/19533/3
            val formattedArea: String =
                when (area.compareTo(area.toInt()) == 0) {
                    true -> "%,d".format(Locale.getDefault(), area.toInt())
                    false -> "%,.2f".format(Locale.getDefault(), area)
                }
            append(
                stringResource(id = R.string.non_breaking_space),
                formattedArea,
                stringResource(id = R.string.non_breaking_space),
                stringResource(id = R.string.kilometre_abbreviated),
                Html.fromHtml(
                    stringResource(id = R.string.superscript_two),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            )
        },
        modifier,
        color
    )
}