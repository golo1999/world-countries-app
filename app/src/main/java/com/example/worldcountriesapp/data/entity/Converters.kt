package com.example.worldcountriesapp.data.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun doubleListToJson(list: List<Double>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun drivingToJson(driving: Driving): String {
        return gson.toJson(driving)
    }

    @TypeConverter
    fun mapOfStringAndCurrencyToJson(map: Map<String, Currency>?): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun mapOfStringAndDemonymToJson(map: Map<String, Demonym>?): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun mapOfStringAndDoubleListToJson(map: Map<String, List<Double>>): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun mapOfStringAndStringToJson(map: Map<String, String>?): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun mapOfStringAndTranslationToJson(map: Map<String, Translation>): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun nameToJson(name: Name): String {
        return gson.toJson(name)
    }

    @TypeConverter
    fun stringListToJson(list: List<String>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun jsonToDoubleList(json: String): List<Double> {
        return gson.fromJson(json, object : TypeToken<List<Double>>() {}.type)
    }

    @TypeConverter
    fun jsonToDriving(json: String): Driving {
        return gson.fromJson(json, object : TypeToken<Driving>() {}.type)
    }

    @TypeConverter
    fun jsonToMapOfStringAndCurrency(json: String): Map<String, Currency>? {
        return gson.fromJson(json, object : TypeToken<Map<String, Currency>?>() {}.type)
    }

    @TypeConverter
    fun jsonToMapOfStringAndDemonym(json: String): Map<String, Demonym>? {
        return gson.fromJson(json, object : TypeToken<Map<String, Demonym>?>() {}.type)
    }

    @TypeConverter
    fun jsonToMapOfStringAndDoubleList(json: String): Map<String, List<Double>> {
        return gson.fromJson(json, object : TypeToken<Map<String, List<Double>>>() {}.type)
    }

    @TypeConverter
    fun jsonToMapOfStringAndString(json: String): Map<String, String>? {
        return gson.fromJson(json, object : TypeToken<Map<String, String>?>() {}.type)
    }

    @TypeConverter
    fun jsonToMapOfStringAndTranslation(json: String): Map<String, Translation> {
        return gson.fromJson(json, object : TypeToken<Map<String, Translation>>() {}.type)
    }

    @TypeConverter
    fun jsonToName(json: String): Name {
        return gson.fromJson(json, object : TypeToken<Name>() {}.type)
    }

    @TypeConverter
    fun jsonToStringList(json: String): List<String>? {
        return gson.fromJson(json, object : TypeToken<List<String>?>() {}.type)
    }
}