package com.example.worldcountriesapp.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.worldcountriesapp.MainViewModel
import com.example.worldcountriesapp.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val filteredCountries by viewModel.filteredCountries.observeAsState(initial = emptyList())
    val isFetchingAPI by viewModel.isFetchingAPI.observeAsState(initial = true)
    val regions by viewModel.regions.observeAsState(initial = listOf("All"))
    val searchQuery by viewModel.searchQuery.observeAsState(initial = "")
    val selectedRegion by viewModel.selectedRegion.observeAsState(initial = "All")

    var isSearchActive by rememberSaveable {
        mutableStateOf(false)
    }
    var isSearchExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.getCountries()
    }

    Column {
        if (isFetchingAPI) {
            Text(
                text = "Loading...",
                modifier = Modifier.padding(
                    start = 32.dp,
                    top = 32.dp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    DockedSearchBar(
                        query = searchQuery,
                        onQueryChange = { newSearchQuery -> viewModel.setSearchQuery(newSearchQuery) },
                        onSearch = {
                            isSearchActive = false
                            viewModel.filterCountriesByName(it)
                        },
                        active = isSearchActive,
                        onActiveChange = { newActiveStatus -> isSearchActive = newActiveStatus },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 32.dp,
                                top = 32.dp,
                                end = 32.dp,
                                bottom = 8.dp
                            )
                            .shadow(elevation = 4.dp),
                        placeholder = { Text(text = "Search for a country...") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "SearchIcon",
                                tint = when (isSearchActive) {
                                    true -> MaterialTheme.colorScheme.onBackground
                                    false -> MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        },
                        trailingIcon = {
                            if (isSearchActive && searchQuery.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "ClearIcon",
                                    modifier = Modifier.clickable {
                                        viewModel.setSearchQuery("")
                                        viewModel.filterCountriesByName("")
                                        isSearchActive = false
                                    },
                                    tint = MaterialTheme.colorScheme.onBackground
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
                        expanded = isSearchExpanded,
                        onExpandedChange = { isSearchExpanded = !isSearchExpanded },
                        modifier = Modifier.padding(
                            horizontal = 32.dp,
                            vertical = 8.dp
                        )
                    ) {
                        TextField(
                            value = selectedRegion,
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .shadow(elevation = 4.dp),
                            readOnly = true,
                            label = { Text(text = "Filter by region") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSearchExpanded) },
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
                            expanded = isSearchExpanded,
                            onDismissRequest = { isSearchExpanded = false }
                        ) {
                            regions.forEach { region ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = region,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    },
                                    onClick = {
                                        viewModel.setSelectedRegion(region)
                                        viewModel.filterCountriesByRegion(region)
                                        isSearchExpanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                }
                if (filteredCountries.isEmpty()) {
                    item {
                        Text(
                            text = "No data...",
                            modifier = Modifier.padding(
                                start = 32.dp,
                                top = 8.dp,
                                end = 32.dp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                itemsIndexed(
                    items = filteredCountries,
                    key = { index, country -> country.name.common.lowercase().plus(index) }
                ) { index, countryPresentation ->
                    CountryPresentationCard(
                        countryPresentation,
                        isFirst = index == 0,
                        isLast = index == filteredCountries.size - 1,
                        onClick = {
                            navController.navigate(
                                route = "${Screen.CountryInfoScreen.route}/${countryPresentation.name.common.lowercase()}"
                            )
                        })
                }
            }
        }
    }
}