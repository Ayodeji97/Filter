package com.mylearning.devplacement.ui.user

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
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
import com.mylearning.devplacement.utils.ProgressDialog
import com.mylearning.devplacement.utils.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

private const val MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1

@AndroidEntryPoint
class UsersFragment : Fragment() {
    private val TAG : String = "AppDebug"
    private var _ui : FragmentUsersBinding? = null


    private lateinit var loadingDialog: ProgressDialog

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



        //Check if network is available
        if (context?.let { isNetworkAvailable(it) } == true) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show()
        }


        checkPermissionAndStart()
        val config = PRDownloaderConfig.newBuilder().setDatabaseEnabled(true).build()
        PRDownloader.initialize(context, config)


        subscribeObservers()
        viewModel.setStateEvent(UserViewModel.MainStateEvent.GetUserEvent)


        // observe list download
        viewModel.startDialogDownload.observe(requireActivity(), Observer { isDownloading ->
            if(!isDownloading) {
                loadingDialog = context?.let { ProgressDialog(it) }!!
                loadingDialog.showDialog()
            }

        })

        // observe when file is completely downloaded
        viewModel.completeDownload.observe(requireActivity(), Observer { isCompleted ->
            isCompleted?.let { result ->
                if (result) {
                    loadingDialog.dismiss()
                }
            }
        })

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

    // u can change this to animation
    private fun displayProgressBar (isDisplay : Boolean) {
        ui.progressBar.visibility = if (isDisplay) View.VISIBLE else View.GONE
    }


    // check for permission
    private fun checkPermissionAndStart() {
        if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
        ) {
            promptDialogPermission()

        } else {
            println("CHECKINGDATAEXIST")
            viewModel.checkDataExist()
//            startDownload("https://drive.google.com/u/0/uc?id=1giBv3pK6qbOPo0Y02H-wjT9ULPksfBCm&export=download",
//                requireActivity()
//            )
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
                        Log.i("SEEE", "Called")
//                        startDownload("https://drive.google.com/u/0/uc?id=1giBv3pK6qbOPo0Y02H-wjT9ULPksfBCm&export=download",
//                            requireActivity()
//                        )
                       viewModel.checkDataExist()
                        viewModel.grantAccess.value = true
                    }
                } else {
                    promptDialogPermission()
                }

            }


        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       Log.d("SEEE", "see all")
        viewModel.checkDataExist()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }


}



//    fun startDownload(
//        url: String,
//        activity: Activity
//    ) {
//        Thread {
//            val fileUrl = URL(url)
//            val connection = fileUrl.openConnection() as HttpURLConnection
//            connection.doInput = true
//            try {
//                connection.connect()
//                val inputStream = connection.inputStream
//                val extStorageDirectory = activity.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
//                val child = "CARS_OWNERS_.csv"
//                val outFile = File(extStorageDirectory, child)
//                val outputStream = FileOutputStream(outFile)
//                try {
//                    outputStream.use { output ->
//                        val buffer = ByteArray(4 * 1024)
//                        var byteCount = inputStream.read(buffer)
//                        while (byteCount > 0) {
//                            output.write(buffer, 0, byteCount)
//                            byteCount = inputStream.read(buffer)
//                        }
//                        output.flush()
//                        output.close()
//                    }
//                    activity.runOnUiThread {
//                        Toast.makeText(activity, "SUCCESS", Toast.LENGTH_SHORT).show()
//
//                        Log.i("Suce", "Successsss")
//                    }
//                } catch (e: FileNotFoundException) {
//                    activity.runOnUiThread {
//                        Toast.makeText(activity, e.message.toString(), Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//            } catch (e: Exception) {
//                activity.runOnUiThread {
//                    Toast.makeText(activity, "Exception caught", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }.start()
//    }