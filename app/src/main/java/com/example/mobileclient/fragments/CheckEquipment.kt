package com.example.mobileclient.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentCheckEquipmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [CheckEquipment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckEquipment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentCheckEquipmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCheckEquipmentBinding.inflate(inflater, container, false)
        val view = binding.root

        var x1 = 0
        var x2 = 0
        var x3 = 0
        var x4 = 0
        var x5 = 0
        var x6 = 0
        var x7 = 0


        binding.btnLess.setOnClickListener{
            if(x1!=0){
                x1--
                binding.etNumber.setText(x1.toString())
            }
        }
        binding.btnMore.setOnClickListener{
            x1++
            binding.etNumber.setText(x1.toString())
        }
        binding.btn1Less.setOnClickListener{
            if(x2!=0){
                x2--
                binding.et1Number.setText(x2.toString())
            }
        }
        binding.btn1More.setOnClickListener{
            x2++
            binding.et1Number.setText(x2.toString())
        }
        binding.btn2Less.setOnClickListener{
            if(x3!=0){
                x3--
                binding.et2Number.setText(x3.toString())
            }
        }
        binding.btn2More.setOnClickListener{
            x3++
            binding.et2Number.setText(x3.toString())
        }
        binding.btn3Less.setOnClickListener{
            if(x4!=0){
                x4--
                binding.et3Number.setText(x4.toString())
            }
        }
        binding.btn3More.setOnClickListener{
            x4++
            binding.et3Number.setText(x4.toString())
        }
        binding.btn4Less.setOnClickListener{
            if(x5!=0){
                x5--
                binding.et4Number.setText(x5.toString())
            }
        }
        binding.btn4More.setOnClickListener{
            x5++
            binding.et4Number.setText(x5.toString())
        }
        binding.btn5Less.setOnClickListener{
            if(x6!=0){
                x6--
                binding.et5Number.setText(x6.toString())
            }
        }
        binding.btn5More.setOnClickListener{
            x6++
            binding.et5Number.setText(x6.toString())
        }
        binding.btn6Less.setOnClickListener{
            if(x7!=0){
                x7--
                binding.et6Number.setText(x7.toString())
            }
        }
        binding.btn6More.setOnClickListener{
            x7++
            binding.et6Number.setText(x7.toString())
        }


        binding.button2.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.equipment)
        }

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CheckEquipment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckEquipment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}