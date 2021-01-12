package com.mylearning.devplacement.ui.user

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import com.google.android.material.snackbar.Snackbar
import com.mylearning.devplacement.R
import com.mylearning.devplacement.adapter.UsersAdapter
import com.mylearning.devplacement.databinding.FragmentUsersBinding
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.utils.DataState
import com.mylearning.devplacement.utils.NetworkCheck
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1

@AndroidEntryPoint
class UsersFragment : Fragment() {
    private val TAG : String = "AppDebug"
    private var _ui : FragmentUsersBinding? = null



    private val viewModel : UserViewModel by viewModels ()

    private val ui get() = _ui!!
    var myList = listOf<User>()


    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_users, container, false)
        _ui = FragmentUsersBinding.inflate(inflater, container, false)


        checkPermissionAndStart()
        val config = PRDownloaderConfig.newBuilder().setDatabaseEnabled(true).build()
        PRDownloader.initialize(context, config)


        subscribeObservers()
        viewModel.setStateEvent(UserViewModel.MainStateEvent.GetUserEvent)
        return ui.root
    }

    private fun subscribeObservers () {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {

                is DataState.Success<List<User>>  -> {
                    displayProgressBar(false)
                    myList = dataState.data

                    val adapter = UsersAdapter(myList)
                    ui.recyclerView.adapter = adapter
                    ui.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    adapter.notifyDataSetChanged()
                    //appendUserTitle (dataState.data)
                }

                is DataState.Error -> {
                    displayProgressBar(false)
                    displayErrorMessage (dataState.exception.message)

                }

                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayErrorMessage (message : String?) {
        if (message != null) {
            ui.text.text = message
        } else {
            ui.text.text = "Unknown Error"
        }
    }

    private fun displayProgressBar (isDisplay : Boolean) {
        ui.progressBar.visibility = if (isDisplay) View.VISIBLE else View.GONE
    }


    private fun checkPermissionAndStart() {
        if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
        ) {
            promptDialogPermission()

        } else {
            viewModel.checkDataExist()
            viewModel.grantAccess.value = true
        }

    }


    private fun promptDialogPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_STORAGE -> {
                if ((grantResults.isNotEmpty() && permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        viewModel.checkDataExist()
                        viewModel.grantAccess.value = true
                    }
                } else {
                    promptDialogPermission()
                }

            }


        }
    }

//    private fun appendUserTitle(users : List<User>){
//        val sb = StringBuilder()
//
//        for (user in users) {
//            sb.append(user.name)
//        }
//
//        ui.text.text = sb.toString()
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }


}