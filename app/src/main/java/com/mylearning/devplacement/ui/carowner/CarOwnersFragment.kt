package com.mylearning.devplacement.ui.carowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mylearning.devplacement.R
import com.mylearning.devplacement.adapter.CarOwnerAdapter
import com.mylearning.devplacement.databinding.FragmentCarOwnersBinding
import com.mylearning.devplacement.model.CarOwner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarOwnersFragment : Fragment(R.layout.fragment_car_owners) {

    private var _ui: FragmentCarOwnersBinding? = null
    private val ui get() = _ui!!
    private lateinit var viewModel: CarOwnerViewModel
    private val args by navArgs<CarOwnersFragmentArgs>()
    private var filterList = listOf<CarOwner>()
    private val adapter = CarOwnerAdapter(filterList)


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val user =  args.filter
        val factory = user?.let { CarOwnersViewModelFactory(it, requireContext()) }
        viewModel = factory?.let { ViewModelProvider(this, it).get(CarOwnerViewModel::class.java) }!!

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ui.fragmentCarOwnerFilter.setOnClickListener {
            findNavController().navigate(R.id.usersFragment)
        }
    }

}