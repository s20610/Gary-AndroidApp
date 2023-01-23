package com.example.mobileclient.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentRegisterBinding
import com.example.mobileclient.model.NewUser
import com.example.mobileclient.viewmodels.UserViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class Register : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val sharedViewModel: UserViewModel by activityViewModels()

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        val firstNameInput: TextInputEditText = binding.firstNameInput
        val lastNameInput: TextInputEditText = binding.lastNameInput
        val emailInput: TextInputEditText = binding.emailInput
        val phoneNumber: TextInputEditText = binding.phonenumberInput
        val passwordInput: TextInputEditText = binding.passwordInput
        val birthdayInput: TextInputEditText = binding.birthdayInput
        val openCalendarButton: Button = binding.openCalendarButton
        val agreeSwitch: SwitchMaterial = binding.switchMaterial
        val registerButton: Button = binding.registerButton
        registerButton.isEnabled = false
        val constraintBuilder =
            CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Your birth date")
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .setCalendarConstraints(constraintBuilder.build()).build()

        fun buttonEnable() {
            registerButton.isEnabled =
                agreeSwitch.isChecked && nameValidate(firstNameInput) && nameValidate(lastNameInput) && passwordValidate(
                    passwordInput
                ) && emailValidate(emailInput)
        }
        datePicker.addOnPositiveButtonClickListener {
            birthdayInput.setText(datePicker.headerText)
        }
        datePicker.addOnCancelListener {
            datePicker.dismiss()
        }
        openCalendarButton.setOnClickListener {
            datePicker.show(parentFragmentManager, "birthdate_picker")
        }
        birthdayInput.setOnClickListener {
            datePicker.show(parentFragmentManager, "birthdate_picker")
        }
        firstNameInput.doAfterTextChanged {
            buttonEnable()
        }
        lastNameInput.doAfterTextChanged {
            buttonEnable()
        }
        emailInput.doAfterTextChanged {
            buttonEnable()
        }
        passwordInput.doAfterTextChanged {
            buttonEnable()
        }
        agreeSwitch.setOnCheckedChangeListener { _, _ ->
            buttonEnable()
        }

        binding.collectDataButton.setOnClickListener {
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1).setTitle(getString(R.string.why_do_we_collect_data))
                    .setMessage(getString(R.string.we_need_this_data_to_provide_our_services_with_high_quality))
                    .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                        dialog.cancel()
                    }
                    .setPositiveButton(getString(R.string.accept)) { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            }
        }

        //New user creation on register button click
        binding.registerButton.setOnClickListener {
            val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            utc.timeInMillis = datePicker.selection!!
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN)
            val birthdate = format.format(utc.time)
            Log.d("Birthdate", birthdate)
            val newUser = NewUser(
                firstNameInput.text.toString(),
                lastNameInput.text.toString(),
                emailInput.text.toString(),
                passwordInput.text.toString(),
                birthdate,
                phoneNumber.text.toString(),
            )
            sharedViewModel.registerNewUser(newUser)
            sharedViewModel.registerResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    Log.d("Register response", response.body().toString())
                    Log.d("Response Code", response.code().toString())
                    Toast.makeText(context, "Registration successful", LENGTH_LONG).show()
                    Navigation.findNavController(view)
                        .navigate(R.id.action_register_to_splashScreen)
                } else {
                    Toast.makeText(context, "Register error " + response.code(), LENGTH_LONG).show()
                    Log.d("Register Response", response.body().toString())
                    Log.d("Response Code: ", response.code().toString())
                }
            }

        }
        return view
    }

    private fun nameValidate(nameInput: TextInputEditText): Boolean {
        return if (nameInput.text?.isNotEmpty() == true) {
            true
        } else {
            nameInput.error = "This field is required"
            false
        }
    }

    private fun emailValidate(emailInput: TextInputEditText): Boolean {
        return when {
            emailInput.text?.isEmpty() == true -> {
                emailInput.error = "This field is required"
                false
            }

            android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.text.toString()).matches() -> {
                true
            }

            else -> {
                emailInput.error = "Enter valid e-mail"
                false
            }
        }
    }

    private fun passwordValidate(passwordInput: TextInputEditText): Boolean {
        return if (passwordInput.text?.isNotEmpty() == true) {
            true
        } else {
            passwordInput.error = "This field is required"
            false
        }
    }
}