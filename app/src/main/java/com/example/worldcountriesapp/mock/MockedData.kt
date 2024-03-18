package com.example.worldcountriesapp.mock

import com.example.worldcountriesapp.data.entity.Country
import com.example.worldcountriesapp.data.entity.Currency
import com.example.worldcountriesapp.data.entity.Demonym
import com.example.worldcountriesapp.data.entity.Driving
import com.example.worldcountriesapp.data.entity.Name
import com.example.worldcountriesapp.data.entity.SimpleName
import com.example.worldcountriesapp.data.entity.Translation

object MockedData {
    private val countryCurrencies = mapOf(
        "RON" to Currency(
            name = "Romanian leu",
            symbol = "lei"
        )
    )
    private val countryDriving = Driving(
        signs = listOf("RO"),
        side = "right"
    )
    private val countryLatLng = listOf(46.toDouble(), 25.toDouble())
    private val countryName = Name(
        common = "Romania",
        official = "Romania",
        nativeName = mapOf(
            "ron" to SimpleName(
                common = "România",
                official = "România"
            )
        )
    )
    private val countryTranslations = mapOf(
        "ara" to Translation(official = "رومانيا", common = "رومانيا"),
        "bre" to Translation(common = "Roumania", official = "Roumania"),
        "ces" to Translation(common = "Rumunsko", official = "Rumunsko"),
        "cym" to Translation(common = "Romania", official = "Romania"),
        "deu" to Translation(common = "Rumänien", official = "Rumänien"),
        "est" to Translation(common = "Rumeenia", official = "Rumeenia"),
        "fin" to Translation(common = "Romania", official = "Romania"),
        "fra" to Translation(common = "Roumanie", official = "Roumanie"),
        "hrv" to Translation(common = "Rumunija", official = "Rumunjska"),
        "hun" to Translation(common = "Románia", official = "Románia"),
        "ita" to Translation(common = "Romania", official = "Romania"),
        "jpn" to Translation(common = "ルーマニア", official = "ルーマニア"),
        "kor" to Translation(common = "루마니아", official = "루마니아"),
        "nld" to Translation(common = "Roemenië", official = "Roemenië"),
        "per" to Translation(common = "رومانی", official = "رومانی"),
        "pol" to Translation(common = "Rumunia", official = "Rumunia"),
        "por" to Translation(common = "Romênia", official = "Romênia"),
        "rus" to Translation(common = "Румыния", official = "Румыния"),
        "slk" to Translation(common = "Rumunsko", official = "Rumunsko"),
        "spa" to Translation(common = "Rumania", official = "Rumania"),
        "srp" to Translation(common = "Румунија", official = "Румунија"),
        "swe" to Translation(common = "Rumänien", official = "Rumänien"),
        "tur" to Translation(common = "Romanya", official = "Romanya"),
        "urd" to Translation(common = "رومانیہ", official = "رومانیہ"),
        "zho" to Translation(common = "罗马尼亚", official = "罗马尼亚")
    )
    val country = Country(
        name = countryName,
        tld = listOf(".ro"),
        independent = true,
        status = "officially-assigned",
        currencies = countryCurrencies,
        altSpellings = listOf("RO", "Rumania", "Roumania", "România"),
        capital = listOf("Bucharest"),
        region = "Europe",
        subregion = "Southeast Europe",
        languages = mapOf("ron" to "Romanian"),
        translations = countryTranslations,
        latlng = countryLatLng,
        landlocked = false,
        borders = listOf("BGR", "HUN", "MDA", "SRB", "UKR"),
        area = 238391.toDouble(),
        demonyms = mapOf(
            "eng" to Demonym(f = "Romanian", m = "Romanian"),
            "fra" to Demonym(f = "Roumaine", m = "Roumain")
        ),
        maps = mapOf(
            "googleMaps" to "https://goo.gl/maps/845hAgCf1mDkN3vr7",
            "openStreetMaps" to "https://www.openstreetmap.org/relation/90689"
        ),
        population = 19286123,
        car = countryDriving,
        timezones = listOf("UTC+02:00"),
        continents = listOf("Europe"),
        flags = mapOf(
            "png" to "https://flagcdn.com/w320/ro.png",
            "svg" to "https://flagcdn.com/ro.svg",
            "alt" to "The flag of Romania is c…y blue, yellow and red."
        ),
        coatOfArms = mapOf(
            "png" to "https://mainfacts.com/media/images/coats_of_arms/ro.png",
            "svg" to "https://mainfacts.com/media/images/coats_of_arms/ro.svg"
        ),
        startOfWeek = "monday",
        capitalInfo = mapOf("latlng" to listOf(44.43, 26.1))
    )
}