package com.mylearning.devplacement.di

import android.content.Context
import androidx.room.Room
import com.mylearning.devplacement.room.UserDao
import com.mylearning.devplacement.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn (ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideBlogDb (@ApplicationContext context : Context) : UserDatabase {


     var INSTANCE: UserDatabase? = null

        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                UserDatabase.DATEBASE_NAME
            ).build()
            INSTANCE = instance
            // return instance
            instance
        }
//        return Room.databaseBuilder(
//            context,
//            UserDatabase::class.java,
//            UserDatabase.DATEBASE_NAME
//        ).fallbackToDestructiveMigration()
//            .build()

    }

    @Singleton
    @Provides
    fun provideBlogDao (userDatabase: UserDatabase) : UserDao {
        return userDatabase.userDao()
    }


}