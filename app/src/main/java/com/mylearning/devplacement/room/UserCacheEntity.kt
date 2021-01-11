package com.mylearning.devplacement.room

import androidx.room.*
import com.mylearning.devplacement.utils.ColorConverters
import com.mylearning.devplacement.utils.CountriesConverters

@Entity (tableName = "accounts")
data class UserCacheEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id : String,

    @ColumnInfo(name = "image")
    var image : String,

    @ColumnInfo(name = "name")
    var name : String,

    @ColumnInfo(name = "gender")
    var gender : String,

    @TypeConverters(ColorConverters::class)
    @ColumnInfo(name = "colors")  var colors : Colors,

    @TypeConverters(ColorConverters::class)
    @ColumnInfo(name = "countries") var countries : Colors,

    @ColumnInfo(name = "createAt")
    var date : String

)