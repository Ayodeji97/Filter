package com.mylearning.devplacement.ui.user

import android.content.Context
import android.os.Environment
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.repository.MainRepository
import com.mylearning.devplacement.utils.DataState
import com.mylearning.devplacement.filemanager.FileDownloader
import com.mylearning.devplacement.utils.Utility
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File

class UserViewModel @ViewModelInject constructor
    (private val mainRepository: MainRepository,
     @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<User>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<User>>>
        get() = _dataState

    private val file by lazy {
        File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            Utility.FOLDER
        )
    }
    //val grantAccess = MutableLiveData<Boolean>()


    // Function responsible for getting all user from the database
    @ExperimentalCoroutinesApi
    fun setStateEvent (mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {

                is MainStateEvent.GetUserEvent -> {
                    mainRepository.getUser()
                        .onEach { dataState ->
                            _dataState.value = dataState

                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

    // check if the the csv file exist
    fun checkDataExist(context: Context) {
      FileDownloader.downloadCsv(Utility.DOWNLOAD_URL, context)

        if (!file.exists()) {
            println("CHECKING")
            FileDownloader.readCsv(context)
        }
    }

    // State class
    sealed class MainStateEvent {
        object GetUserEvent : MainStateEvent()
        object None : MainStateEvent()
    }

}