package com.anurag.idz_assignment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var name:TextInputEditText
    lateinit var dob:TextInputEditText
    lateinit var email: TextInputEditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
         name=findViewById(R.id.name)
        dob=findViewById(R.id.dob)
        email=findViewById(R.id.email)
        var SubmitButton=findViewById<MaterialButton>(R.id.Continue)
        var Check=findViewById<MaterialButton>(R.id.Direct)

        dob.setOnClickListener {
            showDatePickerDialog()
        }
        SubmitButton.setOnClickListener {
            AddData()
        }
        Check.setOnClickListener {
            Intent(this,UserDetailsActivity::class.java).also {
                startActivity(it)
            }
        }
    }
    fun AddData(){
        val db=DatabaseHelper(this)
        val nametext=name.text.toString()
        val dobtext=dob.text.toString()
        val emailtext=email.text.toString()
        if (nametext.isEmpty()||dobtext.isEmpty()||emailtext.isEmpty())
        {
            return Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_SHORT).show()
        }else{
            db.addUser(UserDetails(nametext,dobtext,emailtext))
            Intent(this,UserDetailsActivity::class.java).also {
                startActivity(it)
            }
            Toast.makeText(this,"User Added", Toast.LENGTH_SHORT).show()
        }

    }
    private fun showDatePickerDialog() {
        val constraintBulder= CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.now())
        val calendar = Calendar.getInstance()
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraintBulder.build())
            .build()
        datePicker.addOnPositiveButtonClickListener { selectedDate ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.timeInMillis = selectedDate
            val selectedDay = selectedCalendar.get(Calendar.DAY_OF_MONTH)
            val selectedMonth = selectedCalendar.get(Calendar.MONTH)
            val selectedYear = selectedCalendar.get(Calendar.YEAR)
            val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            dob.setText(formattedDate)
        }
        datePicker.show(supportFragmentManager, "datePicker")
    }
}