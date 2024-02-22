package com.example.worldcountriesapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcountriesapp.retrofit.RetrofitInstance
import com.example.worldcountriesapp.retrofit.country.Country
import com.example.worldcountriesapp.retrofit.country.CountryPresentation
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val countriesApiService = RetrofitInstance.apiService

    private val _allCountries = MutableLiveData<List<CountryPresentation>>()

    private val _filteredCountries = MutableLiveData<List<CountryPresentation>>()
    val filteredCountries: LiveData<List<CountryPresentation>> = _filteredCountries

    private val _countriesByCodeInfo = MutableLiveData<List<Country>?>()
    val countriesByCodeInfo: MutableLiveData<List<Country>?> = _countriesByCodeInfo

    private val _countryByNameInfo = MutableLiveData<Country?>()
    val countryByNameInfo: LiveData<Country?> = _countryByNameInfo

    private val _regions = MutableLiveData<List<String>>()
    val regions: LiveData<List<String>> = _regions

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _selectedRegion = MutableLiveData("All")
    val selectedRegion: LiveData<String> = _selectedRegion

    private val _isFetchingAPI = MutableLiveData(true)
    val isFetchingAPI: LiveData<Boolean> = _isFetchingAPI

    fun filterCountriesByName(name: String) {
        when (name) {
            "" -> {
                when (_selectedRegion.value) {
                    "All" -> resetFilteredCountries()
                    else -> _filteredCountries.value =
                        _allCountries.value?.filter { country -> country.region == _selectedRegion.value }
                }
            }

            else -> when (_selectedRegion.value) {
                "All" -> _filteredCountries.value =
                    _allCountries.value?.filter { country ->
                        country.name.common.lowercase().contains(name.lowercase())
                    }

                else -> _filteredCountries.value =
                    _allCountries.value?.filter { country ->
                        country.region == _selectedRegion.value &&
                                country.name.common.lowercase().contains(name.lowercase())
                    }
            }
        }
    }

    fun filterCountriesByRegion(region: String) {
        when (region) {
            "All" -> when (_searchQuery.value) {
                "" -> resetFilteredCountries()
                else -> _filteredCountries.value =
                    _allCountries.value?.filter { country ->
                        country.name.common.lowercase().contains(_searchQuery.value?.lowercase().toString())
                    }
            }

            else -> when (_searchQuery.value) {
                "" -> _filteredCountries.value =
                    _allCountries.value?.filter { country -> country.region == region }

                else -> _filteredCountries.value = _allCountries.value?.filter { country ->
                    country.region == region && country.name.common.lowercase().contains(
                        _searchQuery.value?.lowercase()
                            .toString()
                    )
                }
            }
        }
    }

    fun getCountries() {
        viewModelScope.launch {
            try {
                val response = countriesApiService.getCountries()
                _allCountries.value =
                    response.body()?.sortedBy { countryPresentation -> countryPresentation.name.common }
                // Retrieving unique regions
                _regions.value =
                    ((_allCountries.value?.asSequence()?.distinctBy { it.region }?.map { it.region }?.sortedDescending()
                        ?.toList())?.plus("All"))?.reversed()
                filterCountriesByRegion(_selectedRegion.value.toString())
                _isFetchingAPI.value = false
            } catch (exception: Exception) {
                // TODO
                Log.d("getCountriesException", exception.toString())
            }
        }
    }

    private fun getCountriesByCode(countryCodeList: List<String>?) {
        viewModelScope.launch {
            try {
                if (countryCodeList == null) {
                    _countriesByCodeInfo.value = emptyList()
                } else {
                    countryCodeList.forEach { countryCode ->
                        val response = countriesApiService.getCountryByCode(countryCode)
                        val responseBody = response.body()?.get(0)

                        if (responseBody != null) {
                            _countriesByCodeInfo.value =
                                _countriesByCodeInfo.value?.plus(responseBody) ?: listOf(responseBody)
                        }
                    }
                }
            } catch (exception: Exception) {
                // TODO
                Log.d("getCountriesException", exception.toString())
            }
        }
    }

    fun getCountryByName(name: String) {
        viewModelScope.launch {
            try {
                val response = countriesApiService.getCountryByName(name)
                val responseBody = response.body()?.get(0)

                _countryByNameInfo.value = responseBody
                getCountriesByCode(responseBody?.borders)
            } catch (exception: Exception) {
                // TODO
                Log.d("getCountriesException", exception.toString())
            }
        }
    }

    fun resetCountriesByCodeInfo() {
        _countriesByCodeInfo.value = null
    }

    fun resetCountryInfo() {
        _countryByNameInfo.value = null
    }

    private fun resetFilteredCountries() {
        _filteredCountries.value = _allCountries.value
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedRegion(region: String) {
        _selectedRegion.value = region
    }
}