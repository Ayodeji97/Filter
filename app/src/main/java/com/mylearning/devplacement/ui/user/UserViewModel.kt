package com.mylearning.devplacement.ui.user

import android.os.Environment
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.repository.MainRepository
import com.mylearning.devplacement.utils.DataState
import com.mylearning.devplacement.utils.Utility
import com.mylearning.devplacement.utils.Utility.CAR_OWNER_DATA
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
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

    private val absoluteFile by lazy {
        File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            Utility.FOLDER.plus("/$CAR_OWNER_DATA")
        )
    }


    private val _startDialogDownload = MutableLiveData<Boolean>()
    val startDialogDownload: LiveData<Boolean>
        get() = _startDialogDownload

    private val _completeDownload = MutableLiveData<Boolean>()
    val completeDownload: LiveData<Boolean>
        get() = _completeDownload


    val grantAccess = MutableLiveData<Boolean>()



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

    fun checkDataExist() {
        if (!absoluteFile.exists()) {
            _startDialogDownload.value = false
            startDownload()
        }
    }

    // download
    private fun startDownload(): Int {
        if (!file.exists()) file.mkdir()
        return PRDownloader.download(
            Utility.DOWNLOAD_URL,
            file.absolutePath,
            CAR_OWNER_DATA
        )
            .build()
            .setOnStartOrResumeListener {
                Timber.i("Started")
            }
            .setOnPauseListener {
                Timber.i("Paused")
            }
            .setOnCancelListener {
                Timber.i("Cancelled")
            }
            .setOnProgressListener { }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    _completeDownload.value = true
                    grantAccess.value = true
                    Timber.i("Completed")
                }

                override fun onError(error: com.downloader.Error?) {
                    Timber.e(error?.serverErrorMessage)
                    _completeDownload.value = true

                }
            })
    }


    sealed class MainStateEvent {

        object GetUserEvent : MainStateEvent()

        object None : MainStateEvent()
    }
}