package com.mylearning.devplacement.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.concurrent.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (userCacheEntity: UserCacheEntity) : Long

    @Query("SELECT * FROM accounts")
    suspend fun getUsers () : List<UserCacheEntity>
}