package com.mylearning.devplacement.room

import androidx.annotation.NonNull
import androidx.room.*
import com.mylearning.devplacement.utils.ColorConverters



@Entity (tableName = "accounts")
data class UserCacheEntity (

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    var id : String,

    @ColumnInfo(name = "image")
    var image : String?,

    @ColumnInfo(name = "name")
    var name : String?,

    @ColumnInfo(name = "gender")
    var gender : String?,

    @TypeConverters(ColorConverters::class)
    @ColumnInfo(name = "colors")  var colors : Colors?,

    @TypeConverters(ColorConverters::class)
    @ColumnInfo(name = "countries") var countries : Colors?,

    @ColumnInfo(name = "createAt")
    var date : String?

)