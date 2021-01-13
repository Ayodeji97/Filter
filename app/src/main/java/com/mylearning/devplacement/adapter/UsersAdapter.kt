package com.mylearning.devplacement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mylearning.devplacement.R
import com.mylearning.devplacement.databinding.UserItemListBinding
import com.mylearning.devplacement.model.User

class UsersAdapter (private val userList : List<User>, private val listener: OnItemClickListener ) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

   inner class UserViewHolder (private val binding: UserItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = userList[position]

                    if(item != null) {
                       listener.onItemClick(item)
                    }
                }
                //listener.onItemClick()
            }

            binding.userFilterList.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = userList[position]

                    if (item != null) {
                        listener.onFilterIconClick(item)
                    }
                }
            }
        }

        fun bind (user: User) {
            binding.apply {

                userNameTv.text = user.name
                userDateCreatedTv.text = user.date.substring(5,16)


                Glide.with(itemView)
                        .load(user.image)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_users)
                        .into(userImageIv)
            }
        }

//        companion object {
//            fun from (parent: ViewGroup) : UserViewHolder {
//                val binding = UserItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                return UserViewHolder(binding)
//            }
//        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       // return UserViewHolder.from(parent)
        val binding = UserItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = userList[position]


        holder.bind(currentItem)

    }

    override fun getItemCount() = userList.size

}

interface OnItemClickListener  {

    fun onItemClick (user: User)

    fun onFilterIconClick (user: User)
}

