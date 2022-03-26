package com.example.mobileclient

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_form)
        var birthdayInput : EditText= findViewById(R.id.birthdayInput)
        birthdayInput.showSoftInputOnFocus = false
        val calendar = Calendar.getInstance()

        birthdayInput.onFocusChangeListener = View.OnFocusChangeListener{ view, focused ->
            if(focused) {
                var day = calendar.get(Calendar.DAY_OF_MONTH)
                var month = calendar.get(Calendar.MONTH)
                var year = calendar.get(Calendar.YEAR)
                val picker = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { datePicker, mYear, mMonth, mDay ->
                        birthdayInput.setText("" + mDay + "/" + mMonth + "/" + mYear)
                    },
                    year,
                    month,
                    day
                )
                picker.show()
                (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                    hideSoftInputFromWindow(birthdayInput.windowToken, 0)
                }
            }
        }
    }
}