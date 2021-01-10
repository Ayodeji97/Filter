package com.mylearning.devplacement.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
class UserCacheEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id : String,

    @ColumnInfo(name = "image")
    var image : String,

    @ColumnInfo(name = "name")
    var name : String,

    @ColumnInfo(name = "gender")
    var gender : String,

    @ColumnInfo(name = "colors")
    var colors : List<String>,

    @ColumnInfo(name = "countries")
    var countries : List<String>,

    @ColumnInfo(name = "createAt")
    var date : String

)