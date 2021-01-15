package com.mylearning.devplacement.ui.user

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mylearning.devplacement.adapter.OnItemClickListener
import com.mylearning.devplacement.adapter.UsersAdapter
import com.mylearning.devplacement.databinding.FragmentUsersBinding
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
/* permission constant to write to file storage */
private const val MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1

@AndroidEntryPoint
class UsersFragment : Fragment(), OnItemClickListener {

    private var _ui: FragmentUsersBinding? = null
    private val ui get() = _ui!!
    private lateinit var networkFailureDialog: NetworkFailureDialog
    private val viewModel: UserViewModel by viewModels()
    private var userList = listOf<User>()


    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _ui = FragmentUsersBinding.inflate(inflater, container, false)

        checkPermissionAndStart()
        checkInternetConnection()
        subscribeObservers()

        viewModel.setStateEvent(UserViewModel.MainStateEvent.GetUserEvent)

        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // check if csv exist on check
        context?.let { viewModel.checkDataExist(it) }
    }

    // on click of an item in the list, navigate to the detail screen
    override fun onItemClick(user: User) {
        val action = UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment(user)
        findNavController().navigate(action)
    }

    // on click on a filter icon navigate to the car owner list and display the filter items
    override fun onFilterIconClick(user: User) {
        val filterIcon = UsersFragmentDirections.actionUsersFragmentToCarOwnersFragment(user)
        findNavController().navigate(filterIcon)
    }


    // Function responsible for observing the network state and display user list on the screen
    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {

                is DataState.Success<List<User>> -> {
                    displayProgressBar(false)
                    userList = dataState.data
                    val adapter = UsersAdapter(userList, this)

                    if (userList.size > 1){
                        networkFailureDialog.dismiss()
                    }
                    ui.recyclerView.adapter = adapter
                    ui.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    adapter.notifyDataSetChanged()

                }

                is DataState.Error -> {
                    displayProgressBar(false)
                    displayErrorMessage(dataState.exception.message)
                    println("ERROR MESSAGE")

                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    /* display error function */
    private fun displayErrorMessage(message: String?) {
        if (message != null) {
            ui.text.text = message
        } else {
            ui.text.text = "Unknown Error"
        }
    }

    /* display progress bar */
    private fun displayProgressBar(isDisplay: Boolean) {
        ui.progressBar.visibility = if (isDisplay) View.VISIBLE else View.GONE
    }


    // function to check for permission
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
            context?.let { viewModel.checkDataExist(it) }
          // viewModel.grantAccess.value = true
        }

    }

    // function to display permission dialog
    private fun promptDialogPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_WRITE_STORAGE
            )
        }
    }

    // function to take action base on user permission request response
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
                        context?.let { viewModel.checkDataExist(it) }
                       //viewModel.grantAccess.value = true
                    }
                } else {
                    promptDialogPermission()
                }

            }
        }
    }

    // check for internet connection
    private fun checkInternetConnection () {
        if (context?.let { isNetworkAvailable(it) } == false) {
            networkFailureDialog = context?.let { NetworkFailureDialog(it) }!!
            networkFailureDialog.showDialog()
            displayProgressBar(true)
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show()
        } else {
            displayProgressBar(false)
            networkFailureDialog = context?.let { NetworkFailureDialog(it) }!!
            networkFailureDialog.dismiss()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }

}


