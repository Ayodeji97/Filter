package com.mylearning.devplacement.repository

import android.util.Log
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.retrofit.NetworkMapper
import com.mylearning.devplacement.retrofit.UserRetrofit
import com.mylearning.devplacement.room.CacheMapper
import com.mylearning.devplacement.room.UserDao
import com.mylearning.devplacement.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepository  constructor(
    private val userDao: UserDao,
    private val userRetrofit: UserRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
){

    suspend fun getUser () : Flow<DataState<List<User>>> = flow {

        try {
            val networkUsers = userRetrofit.getUsers()
            val users = networkMapper.mapFromEntityList(networkUsers)
            Log.d("DATAGOTTEN", "${users}")
            for (user in users) {
                cacheMapper.mapToEntity(user)?.let { userDao.insert(it) }
            }

            // retrieve all the user object
            val cachedUsers = userDao.getUsers()



             //emit to the ui
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedUsers)))
        } catch (e : Exception) {
//            emit(DataState.Error(e))
            val cachedUsers = userDao.getUsers()



            //emit to the ui
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedUsers)))

        }
    }
}