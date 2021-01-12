package com.mylearning.devplacement.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.Fragment


fun Fragment.isNetworkAvailable(context: Context): Boolean? {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                return true
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                return true
            }
        } else {
            return false
        }
        return false
    }
