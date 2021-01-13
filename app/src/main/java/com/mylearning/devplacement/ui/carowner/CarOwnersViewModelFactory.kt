package com.mylearning.devplacement.ui.carowner

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mylearning.devplacement.model.User


class CarOwnersViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarOwnerViewModel::class.java)) {
            return CarOwnerViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}