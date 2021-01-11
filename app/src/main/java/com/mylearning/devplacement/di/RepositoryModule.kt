package com.mylearning.devplacement.di

import com.mylearning.devplacement.repository.MainRepository
import com.mylearning.devplacement.retrofit.NetworkMapper
import com.mylearning.devplacement.retrofit.UserRetrofit
import com.mylearning.devplacement.room.CacheMapper
import com.mylearning.devplacement.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository (
        userDao: UserDao,
        retrofit: UserRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper

    ) : MainRepository {

        return MainRepository(userDao, retrofit, cacheMapper, networkMapper)
    }
}