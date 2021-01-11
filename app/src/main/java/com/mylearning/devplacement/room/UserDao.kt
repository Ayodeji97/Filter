package com.mylearning.devplacement.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (userCacheEntity: UserCacheEntity) : Long

    @Query("SELECT * FROM accounts")
    suspend fun getUsers () : List<UserCacheEntity>
}