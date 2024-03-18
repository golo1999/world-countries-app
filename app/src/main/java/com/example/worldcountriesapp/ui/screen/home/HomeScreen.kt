package com.example.worldcountriesapp.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worldcountriesapp.R

@Composable
fun HomeScreen(
    onCapitalCitiesQuizClick: () -> Unit,
    onCoatOfArmsQuizClick: () -> Unit,
    onCountryListClick: () -> Unit,
    onFlagsQuizClick: () -> Unit
) {
    Column {
        ElevatedButton(
            onClick = onCountryListClick,
            modifier = Modifier.padding(bottom = 72.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = when (isSystemInDarkTheme()) {
                    true -> MaterialTheme.colorScheme.surface
                    false -> MaterialTheme.colorScheme.background
                },
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = stringResource(id = R.string.country_list),
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.country_list),
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
        }
        ElevatedButton(
            onClick = onFlagsQuizClick,
            modifier = Modifier.padding(bottom = 72.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = when (isSystemInDarkTheme()) {
                    true -> MaterialTheme.colorScheme.surface
                    false -> MaterialTheme.colorScheme.background
                },
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_flag),
                contentDescription = stringResource(id = R.string.flags_quiz),
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.flags_quiz),
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
        }
        ElevatedButton(
            onClick = onCoatOfArmsQuizClick,
            modifier = Modifier.padding(bottom = 72.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = when (isSystemInDarkTheme()) {
                    true -> MaterialTheme.colorScheme.surface
                    false -> MaterialTheme.colorScheme.background
                },
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_badge),
                contentDescription = stringResource(id = R.string.coat_of_arms_quiz),
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.coat_of_arms_quiz),
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
        }
        ElevatedButton(
            onClick = onCapitalCitiesQuizClick,
            modifier = Modifier.padding(bottom = 72.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = when (isSystemInDarkTheme()) {
                    true -> MaterialTheme.colorScheme.surface
                    false -> MaterialTheme.colorScheme.background
                },
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_stars),
                contentDescription = stringResource(id = R.string.capital_cities_quiz),
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.capital_cities_quiz),
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
        }
    }
}