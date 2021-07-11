package com.mylearning.devplacement.utils

import androidx.room.TypeConverter
import com.mylearning.devplacement.room.Colors

/* Class responsible for converting a list of string to a string */

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

