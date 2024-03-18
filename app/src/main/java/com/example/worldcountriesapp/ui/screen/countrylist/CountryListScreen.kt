package com.example.worldcountriesapp.ui.screen.countrylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.worldcountriesapp.R
import com.example.worldcountriesapp.ui.common.CountryCard
import com.example.worldcountriesapp.ui.screen.error.ErrorScreen
import com.example.worldcountriesapp.ui.screen.loading.LoadingScreen
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(
    state: CountryListScreenState,
    onEvent: KSuspendFunction1<CountryListScreenEvent, Unit>,
    onCardClick: (countryCode: String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    when {
        state.isFirstRender -> {
            LaunchedEffect(Unit) {
                onEvent(CountryListScreenEvent.GetAllCountries)
                onEvent(CountryListScreenEvent.SetIsFirstRender(value = false))
            }
        }

        state.isFetchingAllCountries -> {
            LoadingScreen()
        }

        state.fetchingCountriesException != null -> {
            ErrorScreen(exception = state.fetchingCountriesException)
        }

        else -> {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    // https://proandroiddev.com/jetpack-compose-search-screen-recommendations-7b5c8c119c0e
                    // https://semicolonspace.com/jetpack-compose-material3-search-bar/
                    // Nested scrolling in the same direction is not currently allowed
                    // Using "DockedSearchBar" instead of "SearchBar"
                    DockedSearchBar(
                        query = state.searchQuery,
                        onQueryChange = { newSearchQuery ->
                            coroutineScope.launch {
                                onEvent(
                                    CountryListScreenEvent.SetSearchQuery(
                                        query = newSearchQuery
                                    )
                                )
                            }
                        },
                        onSearch = {
                            coroutineScope.launch {
                                onEvent(CountryListScreenEvent.SetIsSearchBarActive(value = false))
                                onEvent(CountryListScreenEvent.FilterCountriesByName(name = it))
                            }
                        },
                        active = state.isSearchBarActive,
                        onActiveChange = { newActiveStatus ->
                            coroutineScope.launch {
                                onEvent(
                                    CountryListScreenEvent.SetIsSearchBarActive(
                                        value = newActiveStatus
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 32.dp,
                                top = 32.dp,
                                end = 32.dp,
                                bottom = 8.dp
                            )
                            .shadow(elevation = 4.dp),
                        placeholder = { Text(text = stringResource(id = R.string.search_for_a_country)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(id = R.string.search_icon),
                                tint = when (state.isSearchBarActive) {
                                    true -> MaterialTheme.colorScheme.onBackground
                                    false -> MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        },
                        trailingIcon = {
                            if (state.searchQuery.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = stringResource(id = R.string.clear_icon),
                                    modifier = Modifier.clickable {
                                        coroutineScope.launch {
                                            onEvent(CountryListScreenEvent.SetSearchQuery(query = ""))
                                            onEvent(CountryListScreenEvent.FilterCountriesByName(name = ""))
                                            onEvent(CountryListScreenEvent.SetIsSearchBarActive(value = false))
                                        }
                                    },
                                    tint = when (state.isSearchBarActive) {
                                        true -> MaterialTheme.colorScheme.onBackground
                                        false -> MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            }
                        },
                        shape = RoundedCornerShape(size = 4.dp),
                        colors = SearchBarDefaults.colors(
                            containerColor = when (isSystemInDarkTheme()) {
                                true -> MaterialTheme.colorScheme.surface
                                false -> MaterialTheme.colorScheme.background
                            },
                            dividerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            inputFieldColors = SearchBarDefaults.inputFieldColors(
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                cursorColor = MaterialTheme.colorScheme.onBackground,
                                focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    ) {

                    }
                }
                item {
                    ExposedDropdownMenuBox(
                        expanded = state.isSearchBarExpanded,
                        onExpandedChange = {
                            coroutineScope.launch {
                                onEvent(
                                    CountryListScreenEvent.SetIsSearchBarExpanded(
                                        value = !state.isSearchBarExpanded
                                    )
                                )
                            }
                        },
                        modifier = Modifier.padding(
                            horizontal = 32.dp,
                            vertical = 8.dp
                        )
                    ) {
                        TextField(
                            value = state.selectedRegion,
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .shadow(elevation = 4.dp),
                            readOnly = true,
                            label = { Text(text = stringResource(id = R.string.filter_by_region)) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isSearchBarExpanded) },
                            shape = RoundedCornerShape(size = 4.dp),
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                focusedContainerColor = when (isSystemInDarkTheme()) {
                                    true -> MaterialTheme.colorScheme.surface
                                    false -> MaterialTheme.colorScheme.background
                                },
                                unfocusedContainerColor = when (isSystemInDarkTheme()) {
                                    true -> MaterialTheme.colorScheme.surface
                                    false -> MaterialTheme.colorScheme.background
                                },
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = state.isSearchBarExpanded,
                            onDismissRequest = {
                                coroutineScope.launch {
                                    onEvent(
                                        CountryListScreenEvent.SetIsSearchBarExpanded(
                                            value = false
                                        )
                                    )
                                }
                            }
                        ) {
                            state.allRegions.forEach { region ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = region,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    },
                                    onClick = {
                                        coroutineScope.launch {
                                            onEvent(CountryListScreenEvent.SetSelectedRegion(region))
                                            onEvent(CountryListScreenEvent.FilterCountriesByRegion(region))
                                            onEvent(CountryListScreenEvent.SetIsSearchBarExpanded(value = false))
                                        }
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                }
                if (state.filteredCountries.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(id = R.string.no_countries_found),
                            modifier = Modifier.padding(
                                start = 32.dp,
                                top = 8.dp,
                                end = 32.dp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                } else {
                    itemsIndexed(
                        items = state.filteredCountries,
                        key = { index, country -> country.name.common.lowercase().plus(index) }
                    ) { index, country ->
                        CountryCard(
                            country,
                            isFirst = index == 0,
                            isLast = index == state.filteredCountries.size - 1,
                            onClick = {
                                // Finding the first uppercase alt spelling
                                val countryCode = country.altSpellings.filter { it == it.uppercase() }[0]
                                onCardClick(countryCode)
                            }
                        )
                    }
                }
            }
        }
    }
}