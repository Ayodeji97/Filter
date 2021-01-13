package com.mylearning.devplacement.ui.carowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mylearning.devplacement.R
import com.mylearning.devplacement.adapter.CarOwnerAdapter
import com.mylearning.devplacement.databinding.FragmentCarOwnersBinding
import com.mylearning.devplacement.model.CarOwner
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.ui.filter.FilterManager
import com.mylearning.devplacement.utils.FileDownloader
import kotlinx.coroutines.launch

class CarOwnersFragment : Fragment(R.layout.fragment_car_owners) {

   // private val carOwnersList = generateDummyData(100)

    private var _ui: FragmentCarOwnersBinding? = null

    private lateinit var viewModel: CarOwnerViewModel

    private val ui get() = _ui!!

    private val args by navArgs<CarOwnersFragmentArgs>()

    var filterList = listOf<CarOwner>()

    private val adapter = CarOwnerAdapter(filterList)


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_car_owners, container, false)

        val user =  args.filter
        val factory = CarOwnersViewModelFactory(user, requireContext())
        viewModel = ViewModelProvider(this, factory).get(CarOwnerViewModel::class.java)

        _ui = FragmentCarOwnersBinding.inflate(inflater, container, false)

        val adapter = adapter


        val recyclerView = ui.recyclerView

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.setHasFixedSize(true)

        viewModel.filterResult.observe(viewLifecycleOwner, { carOwnerList ->
            adapter.setCarList(carOwnerList)
        })


        return ui.root
    }

//    fun generateDummyData(size: Int): ArrayList<CarOwner> {
//        val list = ArrayList<CarOwner>()
//
//        for (item in 0 until size) {
//            val drawable = when (item % 3) {
//                0 -> R.drawable.car
//                1 -> R.drawable.car
//                else -> R.drawable.car
//            }
//
//            val item = CarOwner(id = 1, firstName = "John", lastName = "Doe", image = R.drawable.car,
//                    email = "danny@gmail.com", country = "Nigeria", carColor = "red", carModel = "BWM", year = "1996",
//                    gender = "male", jobTitle = "Operator", bio = "A great mind")
//
//            list += item
//        }
//
//        return list
//    }

}