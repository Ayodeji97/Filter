package com.mylearning.devplacement.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mylearning.devplacement.R
import com.mylearning.devplacement.databinding.FragmentUsersBinding
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
class UsersFragment : Fragment() {
    private val TAG : String = "AppDebug"
    private var _ui : FragmentUsersBinding? = null

    private val viewModel : UserViewModel by viewModels ()

    private val ui get() = _ui!!

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_users, container, false)
        _ui = FragmentUsersBinding.inflate(inflater, container, false)

        subscribeObservers()
        viewModel.setStateEvent(UserViewModel.MainStateEvent.GetUserEvent)
        return ui.root
    }

    private fun subscribeObservers () {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {

                is DataState.Success<List<User>>  -> {
                    displayProgressBar(false)
                    appendUserTitle (dataState.data)
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

    private fun appendUserTitle(users : List<User>) {
        val sb = StringBuilder()

        for (user in users) {
            sb.append(user.colors)
        }

        ui.text.text = sb.toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }


}