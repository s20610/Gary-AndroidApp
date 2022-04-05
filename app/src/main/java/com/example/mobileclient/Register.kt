package com.example.mobileclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.core.widget.doAfterTextChanged
import com.example.mobileclient.databinding.FragmentRegisterBinding
import com.example.mobileclient.databinding.FragmentSplashScreenBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Register.newInstance] factory method to
 * create an instance of this fragment.
 */
class Register : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentRegisterBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: Zrobić wstepną walidacje pól (required, length, email, password)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        val firstNameInput : TextInputEditText = binding.firstNameInput
        val lastNameInput : TextInputEditText = binding.lastNameInput
        val emailInput : TextInputEditText = binding.emailInput
        val passwordInput : TextInputEditText = binding.passwordInput
        val birthdayInput: TextInputEditText = binding.birthdayInput
        val agreeSwitch: SwitchMaterial = binding.switchMaterial
        val registerButton: Button = binding.registerButton
        registerButton.isEnabled = false
        val constraintBuilder =
            CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Your birth date")
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT).setCalendarConstraints(constraintBuilder.build()).build()

        fun buttonEnable() {
            registerButton.isEnabled = agreeSwitch.isChecked && nameValidate(firstNameInput) && nameValidate(lastNameInput)  && passwordValidate(passwordInput) && emailValidate(emailInput)
        }
        datePicker.addOnPositiveButtonClickListener {
            birthdayInput.setText(datePicker.headerText)
        }
        datePicker.addOnCancelListener {
            datePicker.dismiss()
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
        return view
    }


    private fun nameValidate(nameInput : TextInputEditText) : Boolean {
        return nameInput.text?.isNotEmpty() == true
    }

    private fun emailValidate (emailInput : TextInputEditText) : Boolean {
        return if (emailInput.text?.isEmpty() == true){
            false
        } else android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.text.toString()).matches()
    }

    private fun passwordValidate(passwordInput : TextInputEditText) : Boolean {
        return passwordInput.text?.isNotEmpty() == true
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Register.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Register().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}