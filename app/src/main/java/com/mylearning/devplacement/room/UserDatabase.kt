package com.mylearning.devplacement.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.utils.ColorConverters

import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(UserCacheEntity::class), version = 1, exportSchema = false)
@TypeConverters(*arrayOf(ColorConverters::class))
abstract class UserDatabase : RoomDatabase () {

    abstract fun userDao(): UserDao

    companion object {
       var DATEBASE_NAME = "Data"

        }

}