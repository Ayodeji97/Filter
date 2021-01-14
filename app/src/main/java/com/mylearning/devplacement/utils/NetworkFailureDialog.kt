package com.mylearning.devplacement.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.mylearning.devplacement.R

class NetworkFailureDialog (context : Context) {

    private val dialog = Dialog(context!!)
    fun showDialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.network_failure)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}