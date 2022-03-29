package com.example.mobileclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_TEXT
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_form)
        val birthdayInput: TextInputEditText = findViewById(R.id.birthday_input)
        val constraintBuilder =
            CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Your birth date")
            .setInputMode(INPUT_MODE_TEXT).setCalendarConstraints(constraintBuilder.build()).build()
        datePicker.addOnPositiveButtonClickListener {
            birthdayInput.setText(datePicker.headerText)
        }
        datePicker.addOnCancelListener {
            datePicker.dismiss()
        }
        birthdayInput.setOnClickListener {
            datePicker.show(supportFragmentManager, "birthdate_picker")
        }
    }
}