package com.example.mobileclient.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentUserInfoBinding
import com.example.mobileclient.viewmodels.UserViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker

class UserInfo : Fragment() {
    private var _binding: FragmentUserInfoBinding? = null
    private val sharedViewModel: UserViewModel by activityViewModels()

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.saveChangesButton.setOnClickListener {
            //TODO: Save changes to database with api call
            Navigation.findNavController(view)
                .navigate(R.id.action_userInfo_to_loggedInScreen)
        }
        val constraintBuilder =
            CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())
        val birthdayString = resources.getString(R.string.birthday)
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText(birthdayString)
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT).setNegativeButtonText(R.string.cancel)
            .setCalendarConstraints(constraintBuilder.build()).build()
        datePicker.addOnPositiveButtonClickListener {
            binding.birthdayInput.setText(datePicker.headerText)
        }
        datePicker.addOnCancelListener {
            datePicker.dismiss()
        }
        binding.openCalendarButton.setOnClickListener {
            datePicker.show(parentFragmentManager, "birthdate_picker")
        }

        return view
    }
}