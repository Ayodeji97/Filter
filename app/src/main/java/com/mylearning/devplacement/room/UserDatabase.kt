package com.mylearning.devplacement.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserCacheEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase () {

    abstract fun userDao () : UserDao

    companion object {
        val DATEBASE_NAME : String = "user_db"
    }
}