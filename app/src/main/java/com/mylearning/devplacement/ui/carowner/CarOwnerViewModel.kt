package com.mylearning.devplacement.ui.carowner

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mylearning.devplacement.model.CarOwnerList
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.ui.filter.FilterManager
import com.mylearning.devplacement.utils.Utility
import com.mylearning.devplacement.utils.Utility.CAR_OWNER_DATA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

class CarOwnerViewModel (private val data : User) : ViewModel() {

    private val absoluteFile by lazy {
        File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                Utility.FOLDER.plus("/$CAR_OWNER_DATA")
        )
    }

    private var viewModelJob = Job()

    private var viewScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _filterResult = MutableLiveData<CarOwnerList>()
    val filterResult : LiveData<CarOwnerList>
        get() = _filterResult

    private val _isDatabaseAvailable = MutableLiveData<Boolean>()
    val isDatabaseAvailable : LiveData<Boolean>
    get() = _isDatabaseAvailable

    init {

        if (!absoluteFile.exists()) {
            _isDatabaseAvailable.value = false
        } else {
            val filterManager = FilterManager()
            viewScope.launch {
                val fileList = filterManager.readFile(absoluteFile)
                _filterResult.value = filterManager.filterItem(fileList, data)
            }

            _isDatabaseAvailable.value = true
        }
    }






}