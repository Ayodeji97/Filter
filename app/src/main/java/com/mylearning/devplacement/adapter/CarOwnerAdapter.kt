package com.mylearning.devplacement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mylearning.devplacement.R
import com.mylearning.devplacement.databinding.CarOwnerListItemBinding
import com.mylearning.devplacement.model.CarOwner

// car adapter
class CarOwnerAdapter (private var carList : List<CarOwner>) : RecyclerView.Adapter <CarOwnerAdapter.CarOwnerViewHolder> () {

    class CarOwnerViewHolder private constructor (private val binding: CarOwnerListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (carOwner: CarOwner) {
            binding.apply {
                carOwnerFirstNameTv.text = carOwner.firstName
                carOwnerLastNameTv.text = carOwner.lastName
                carOwnerBrandNameTv.text = carOwner.carModel
                carOwnerBrandYearTv.text = carOwner.year
                carOwnerCountryTv.text = carOwner.country
                carOwnerImage.setImageResource(R.drawable.car)
                carOwnerGenderTv.text = carOwner.gender
                carOwnerEmailAddressTv.text = carOwner.email
                carOwnerBioTv.text = carOwner.bio
                carOwnerJobTv.text = carOwner.jobTitle
            }
        }

        companion object {
            fun from (parent: ViewGroup) : CarOwnerViewHolder {
                val binding = CarOwnerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return CarOwnerViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarOwnerViewHolder {
        return CarOwnerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CarOwnerViewHolder, position: Int) {
        val currentItem = carList[position]

        holder.bind(currentItem)
    }

    override fun getItemCount() = carList.size

    fun setCarList (carOwner: MutableList<CarOwner>){
        this.carList = carOwner
        notifyDataSetChanged()
    }
}