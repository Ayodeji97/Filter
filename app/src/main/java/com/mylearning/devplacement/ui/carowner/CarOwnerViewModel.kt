package com.mylearning.devplacement.ui.carowner

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mylearning.devplacement.model.CarOwnerList
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.filemanager.FilterManager
import com.mylearning.devplacement.filemanager.FileDownloader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

class CarOwnerViewModel(private val data: User, private val context: Context) : ViewModel() {

    private fun getCsv(): File {
        return FileDownloader.readCsv(context)
    }


    private var viewModelJob = Job()

    private var viewScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _filterResult = MutableLiveData<CarOwnerList>()
    val filterResult: LiveData<CarOwnerList>
        get() = _filterResult

    private val _isDatabaseAvailable = MutableLiveData<Boolean>()
    val isDatabaseAvailable: LiveData<Boolean>
        get() = _isDatabaseAvailable

    init {

        if (!getCsv().exists()) {
            _isDatabaseAvailable.value = false
        } else {
            viewScope.launch {
                val fileList = FilterManager.readFile(getCsv())

                println(fileList)
               _filterResult.value = FilterManager.filterItem(fileList, data)
            }

            _isDatabaseAvailable.value = true
        }

    }
}