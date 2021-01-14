package com.mylearning.devplacement.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
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


fun Fragment.checkNetworkChanges(context: Context){
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager?.let {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    //take action when network connection is gained

                }
                override fun onLost(network: Network) {
                    //take action when network connection is lost
                }
            })
        }
    }

}


