package com.example.mobileclient.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.adapter.EquipmentAdapter
import com.example.mobileclient.databinding.FragmentCheckEquipmentBinding
import com.example.mobileclient.util.Constants
import com.example.mobileclient.viewmodels.ParamedicViewModel


class CheckEquipment : Fragment(), EquipmentAdapter.OnItemClickListener {
    private var _binding: FragmentCheckEquipmentBinding? = null
    private val binding get() = _binding!!
    private val paramedicViewModel: ParamedicViewModel by activityViewModels()
    private var licensePlate = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCheckEquipmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.cancelButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.paramedicScreen)
        }
        paramedicViewModel.currentAmbulanceResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                licensePlate = response.body()?.licensePlate.toString()
                binding.ambulanceTextApi.text = licensePlate
                val token = requireActivity().getSharedPreferences(
                    Constants.USER_INFO_PREFS,
                    Context.MODE_PRIVATE
                )
                    .getString(Constants.USER_TOKEN_TO_PREFS, "")
                binding.refresh.setOnRefreshListener {
                    paramedicViewModel.getAmbulanceEquipment(licensePlate, token ?: "")
                    binding.equipmentRecycler.adapter = EquipmentAdapter(
                        paramedicViewModel.ambulanceEquipmentResponse.value?.body()!!,
                        this
                    )
                    binding.refresh.isRefreshing = false
                }
            } else {
                Log.d("CheckEquipment", "Error: ${response.code()}")
            }
        }

        paramedicViewModel.ambulanceEquipmentResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                binding.equipmentRecycler.adapter = EquipmentAdapter(response.body()!!, this)
                val adapter = binding.equipmentRecycler.adapter as EquipmentAdapter
            }
        }
        return view
    }

    override fun onItemClick(position: Int) {
        val adapter = binding.equipmentRecycler.adapter as EquipmentAdapter
        val equipment = adapter.getEquipment(position)
        Log.d("Equipment", equipment.toString())
    }

    override fun onPlusClick(position: Int) {
        val adapter = binding.equipmentRecycler.adapter as EquipmentAdapter
        val equipment = adapter.getEquipment(position)
        paramedicViewModel.addAmbulanceItem(
            licensePlate,
            equipment.item.itemId
        )
        paramedicViewModel.updateAmbulanceInfoResponse.observe(
            viewLifecycleOwner
        ) { response ->
            if (response.isSuccessful) {
                paramedicViewModel.ambulanceEquipmentResponse.value?.body()
                    ?.find { it.item.itemId == equipment.item.itemId }?.itemData?.count =
                    equipment.itemData.count.plus(1)
                adapter.notifyItemChanged(position)
            }
        }
    }

    override fun onMinusClick(position: Int) {
        val adapter = binding.equipmentRecycler.adapter as EquipmentAdapter
        val equipment = adapter.getEquipment(position)
        paramedicViewModel.removeAmbulanceItem(
            licensePlate,
            equipment.item.itemId
        )
        paramedicViewModel.updateAmbulanceInfoResponse.observe(
            viewLifecycleOwner
        ) { response ->
            if (response.isSuccessful) {
                paramedicViewModel.ambulanceEquipmentResponse.value?.body()
                    ?.find { it.item.itemId == equipment.item.itemId }?.itemData?.count =
                    equipment.itemData.count.minus(1)
                adapter.notifyItemChanged(position)
            }
        }
    }

}