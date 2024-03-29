package com.mylearning.devplacement.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mylearning.devplacement.R
import com.mylearning.devplacement.databinding.FragmentUserDetailsBinding
import com.mylearning.devplacement.utils.Colors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    private var _ui : FragmentUserDetailsBinding? = null
    private val ui get() = _ui!!

    // arguments passed from the car list
    private val args by navArgs<UserDetailsFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _ui = FragmentUserDetailsBinding.inflate(inflater, container, false)

        // load user details to views
        ui.apply {
            val user = args.user

            Glide.with(this@UserDetailsFragment)
                    .load(user.image)
                    .error(R.drawable.ic_error)
                    .into(ui.userDetailsImageIv)

            ui.userDetailNameTv.text = user.name
            ui.detailCardDateCreatedTv.text = user.date!!.substring(5, 16)
            ui.userDetailGenderTv.text = user.gender
            ui.detailCardCountryTv.text = user.countries?.colors.toString()
            ui.detailCardColorTv.text = user.colors?.colors.toString()


        }
        return ui.root
    }
}