package com.mylearning.devplacement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mylearning.devplacement.R
import com.mylearning.devplacement.databinding.UserItemListBinding
import com.mylearning.devplacement.model.User

class UsersAdapter (private val userList : List<User>) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    class UserViewHolder private constructor(private val binding: UserItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (user: User) {
            binding.apply {

                userNameTv.text = user.name
                userDateCreatedTv.text = user.date


                Glide.with(itemView)
                        .load(user.image)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_users)
                        .into(userImageIv)
            }
        }

        companion object {
            fun from (parent: ViewGroup) : UserViewHolder {
                val binding = UserItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return UserViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var currentItem = userList[position]

    }

    override fun getItemCount() = userList.size

}

interface OnItemClickListener () {

}