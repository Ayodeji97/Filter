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

private const val MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1

@AndroidEntryPoint
class UsersFragment : Fragment(), OnItemClickListener {
    private val TAG: String = "AppDebug"
    private var _ui: FragmentUsersBinding? = null


    private lateinit var loadingDialog: ProgressDialog

    private lateinit var networkFailureDialog: NetworkFailureDialog

    private val viewModel: UserViewModel by viewModels()

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

        loadingDialog = ProgressDialog(requireActivity())

        //Check if network is available
        if (context?.let { isNetworkAvailable(it) } == false) {

            networkFailureDialog = context?.let { NetworkFailureDialog(it) }!!
            networkFailureDialog.showDialog()
            loadingDialog.dismiss()
            displayProgressBar(true)
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show()
        } else {
            displayProgressBar(false)
            networkFailureDialog = context?.let { NetworkFailureDialog(it) }!!
            networkFailureDialog.dismiss()
            // networkFailureDialog.dismiss()
        }


        checkPermissionAndStart()

        subscribeObservers()
        viewModel.setStateEvent(UserViewModel.MainStateEvent.GetUserEvent)

        return ui.root
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {

                is DataState.Success<List<User>> -> {
                    displayProgressBar(false)
                    myList = dataState.data

                    val adapter = UsersAdapter(myList, this)
                    ui.recyclerView.adapter = adapter
                    ui.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    adapter.notifyDataSetChanged()
                    //appendUserTitle (dataState.data)
                }

                is DataState.Error -> {
                    displayProgressBar(false)
                    displayErrorMessage(dataState.exception.message)

                }

                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayErrorMessage(message: String?) {
        if (message != null) {
            ui.text.text = message
        } else {
            ui.text.text = "Unknown Error"
        }
    }

    // u can change this to animation
    private fun displayProgressBar(isDisplay: Boolean) {
        ui.progressBar.visibility = if (isDisplay) View.VISIBLE else View.GONE
        ui.buttonRetry.visibility = if (isDisplay) View.VISIBLE else View.GONE
        ui.networkItemTv.visibility = if (isDisplay) View.VISIBLE else View.GONE
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
            context?.let { viewModel.checkDataExist(it) }
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
                        context?.let { viewModel.checkDataExist(it) }
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
        context?.let { viewModel.checkDataExist(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }

    override fun onItemClick(user: User) {
        val action = UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment(user)
        findNavController().navigate(action)


    }

    override fun onFilterIconClick(user: User) {
        val filterIcon = UsersFragmentDirections.actionUsersFragmentToCarOwnersFragment(user)
        findNavController().navigate(filterIcon)
    }

}


