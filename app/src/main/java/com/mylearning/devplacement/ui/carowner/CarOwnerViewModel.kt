package com.mylearning.devplacement.ui.carowner

import android.os.Environment
import androidx.lifecycle.ViewModel
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.utils.Utility
import com.mylearning.devplacement.utils.Utility.CAR_OWNER_DATA
import java.io.File

class CarOwnerViewModel (private val data : User) : ViewModel() {

    private val absoluteFile by lazy {
        File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                Utility.FOLDER.plus("/$CAR_OWNER_DATA")
        )
    }






}