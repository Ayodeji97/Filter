package com.mylearning.devplacement.utils

import androidx.room.TypeConverter
import com.mylearning.devplacement.room.Colors
import com.mylearning.devplacement.room.Countries

class ColorConverters {

    @TypeConverter
    fun toCategories(value: String?): Colors {
        if (value == null || value.isEmpty()) {
            return Colors()
        }

        val list: List<String> = value.split(",")
        val longList = mutableListOf<String>()
        for (item in list) {
            if (!item.isEmpty()) {
                longList.add(item)
            }
        }
        return Colors(longList)
    }

    @TypeConverter
    fun toString(categories: Colors?): String {

        var string = ""

        if (categories == null) {
            return string
        }

        categories.colors.forEach {
            string += "$it,"
        }
        return string
    }

}

//class CountriesConverters {
//
//    @TypeConverter
//    fun toCountries(value: String?): Colors {
//        if (value == null || value.isEmpty()) {
//            return Colors()
//        }
//
//        val list: List<String> = value.split(",")
//        val longList = ArrayList<String>()
//        for (item in list) {
//            if (!item.isEmpty()) {
//                longList.add(item.toString())
//            }
//        }
//        return Colors(longList)
//    }
//
//    @TypeConverter
//    fun toString(categories: Countries?): String {
//
//        var string = ""
//
//        if (categories == null) {
//            return string
//        }
//
//        categories.countries.forEach {
//            string += "$it,"
//        }
//        return string
//    }
//
//}