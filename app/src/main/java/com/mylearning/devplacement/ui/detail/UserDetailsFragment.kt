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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

   private var _ui : FragmentUserDetailsBinding? = null
    private val ui get() = _ui!!

    private val args by navArgs<UserDetailsFragmentArgs>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_user_details, container, false)
        _ui = FragmentUserDetailsBinding.inflate(inflater, container, false)

        ui.apply {
            val user = args.user

            Glide.with(this@UserDetailsFragment)
                    .load(user.image)
                    .error(R.drawable.ic_error)
                    .into(ui.userDetailsImageIv)


            ui.userDetailNameTv.text = user.name
            ui.userDetailDateCreateTv.text = user.date
            ui.detailCardCountryTv.text = user.countries.toString()
            ui.detailCardColorTv.text = user.countries.toString()

        }



        return ui.root
    }


}