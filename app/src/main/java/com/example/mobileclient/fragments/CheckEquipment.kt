package com.example.mobileclient.fragments

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
import com.example.mobileclient.databinding.FragmentCheckEquipmentBinding
import com.example.mobileclient.viewmodels.ParamedicViewModel


class CheckEquipment : Fragment() {
    private var _binding: FragmentCheckEquipmentBinding? = null
    private val binding get() = _binding!!
    private val paramedicViewModel: ParamedicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCheckEquipmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.cancelButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.paramedicScreen)
        }
        val licensePlate = "WWL5A688"
        paramedicViewModel.getAmbulanceEquipment(licensePlate)
        binding.ambulanceTextApi.setText(licensePlate)
        val heightDp =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50F, resources.displayMetrics)
        val marginStartDp =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20F, resources.displayMetrics)
        val marginTopDp =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10F, resources.displayMetrics)
        paramedicViewModel.ambulanceEquipmentResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                response.body()?.forEach { equipment ->
                    Log.d("Equipment Response", equipment.toString())
                    val linearLayout = LinearLayout(context)
                    linearLayout.doOnLayout {
                        val params = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            heightDp.toInt()
                        )
                        params.setMargins(
                            marginStartDp.toInt(),
                            marginTopDp.toInt(),
                            marginStartDp.toInt(),
                            0
                        )
                        linearLayout.layoutParams = params
                        linearLayout.orientation = LinearLayout.HORIZONTAL
                        val itemName = TextView(context).apply {
                            text = equipment.item.name
                            textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                            setTextAppearance(com.google.android.material.R.style.TextAppearance_MaterialComponents_Headline6)
                            this.doOnLayout {
                                val params = LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )
                                params.weight = 1F
                                this.layoutParams = params
                            }
                        }

                        val itemCount = TextView(context).apply {
                            text = equipment.itemData.count.toString()
                            textAlignment = View.TEXT_ALIGNMENT_CENTER
                            setTextAppearance(com.google.android.material.R.style.TextAppearance_MaterialComponents_Headline6)
                            this.doOnLayout {
                                val params = LinearLayout.LayoutParams(
                                    0,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                params.weight = 1F
                                params.gravity = View.TEXT_ALIGNMENT_CENTER
                                this.layoutParams = params
                            }
                        }

                        val minusButton = Button(context).apply {
                            text = "-"
                            setTextAppearance(com.google.android.material.R.style.TextAppearance_MaterialComponents_Headline6)
                            setOnClickListener {
                                paramedicViewModel.removeAmbulanceItem(
                                    licensePlate,
                                    equipment.item.itemId
                                )
                                paramedicViewModel.ambulanceEquipmentResponse.observe(
                                    viewLifecycleOwner
                                ) { response ->
                                    if (response.isSuccessful) {
                                        itemCount.text =
                                            itemCount.text.toString().toInt().minus(1).toString()
                                        paramedicViewModel.ambulanceEquipmentResponse.value?.body()
                                            ?.find { it.item.itemId == equipment.item.itemId }?.itemData?.count =
                                            itemCount.text.toString().toInt()
                                    }
                                }
                            }
                            this.doOnLayout {
                                val params = LinearLayout.LayoutParams(
                                    0,
                                    heightDp.toInt()
                                )
                                params.weight = 1F
                                this.layoutParams = params
                            }
                        }

                        val plusButton = Button(context).apply {
                            text = "+"
                            setTextAppearance(com.google.android.material.R.style.TextAppearance_MaterialComponents_Headline6)
                            setOnClickListener {
                                paramedicViewModel.addAmbulanceItem(
                                    licensePlate,
                                    equipment.item.itemId
                                )
                                paramedicViewModel.ambulanceEquipmentResponse.observe(
                                    viewLifecycleOwner
                                ) { response ->
                                    if (response.isSuccessful) {
                                        itemCount.text =
                                            itemCount.text.toString().toInt().plus(1).toString()
                                        paramedicViewModel.ambulanceEquipmentResponse.value?.body()
                                            ?.find { it.item.itemId == equipment.item.itemId }?.itemData?.count =
                                            itemCount.text.toString().toInt()
                                    }
                                }
                            }
                            this.doOnLayout {
                                val params = LinearLayout.LayoutParams(
                                    0,
                                    heightDp.toInt()
                                )
                                params.weight = 1F
                                this.layoutParams = params
                            }
                        }
                        linearLayout.addView(itemName)
                        linearLayout.addView(minusButton)
                        linearLayout.addView(itemCount)
                        linearLayout.addView(plusButton)
                    }
                    binding.equipmentList.addView(linearLayout)
                }
            }
        }
        return view
    }

}