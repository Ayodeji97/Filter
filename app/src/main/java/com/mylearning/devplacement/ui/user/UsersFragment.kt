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


@AndroidEntryPoint
class UsersFragment : Fragment() {
    private val TAG : String = "AppDebug"
    private var _ui : FragmentUsersBinding? = null

    private val viewModel : UserViewModel by viewModels ()

    private val ui get() = _ui!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_users, container, false)
        _ui = FragmentUsersBinding.inflate(inflater, container, false)
        return ui.root
    }

    private fun subscribeObservers () {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {

                is DataState.Success<List<User>>  -> {
//                    displayProgressBar(false)
//                    appendBlogTitles(dataState.data)
                }

                is DataState.Error -> {
//                    displayProgressBar(false)
//                    displayError(dataState.exception.message)

                }

                is DataState.Loading -> {
//                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayErrorMessage (message : String?) {
        if (message != null) {

        } else {

        }
    }

    private fun displayProgressBar (isDisplay : Boolean) {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }


}