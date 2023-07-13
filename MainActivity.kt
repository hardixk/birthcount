package com.example.agecal

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
//import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
//import java.time.Year
import java.util.*
import kotlin.math.ceil
import java.time.*

@Suppress("NAME_SHADOWING")
class MainActivity : AppCompatActivity() {
    private var tvSelectedDate : TextView? = null
    private var tvAge : TextView? = null
    private var tvDays : TextView? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvDays= findViewById(R.id.tvDays)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAge= findViewById(R.id.tvAge)


        val btn1: Button = findViewById(R.id.btn1)
        btn1.setOnClickListener {
            clickDatePicker()
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("MemberVisibilityCanBePrivate")
    fun clickDatePicker(){
        val mycalendar= Calendar.getInstance()
        val year= mycalendar.get(Calendar.YEAR)
        val month= mycalendar.get(Calendar.MONTH)
        val day= mycalendar.get(Calendar.DAY_OF_MONTH)
        val dpd= DatePickerDialog(this, { _, year, month, dayOfMonth ->

            val selectedDate= "$dayOfMonth/${month+1}/$year"
            //val selectedDate2= "dayOfMonth/${month+1}/$year"

            tvSelectedDate?.text= "Selected Date: $selectedDate"

            val sdf= SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            //val sdf2 = SimpleDateFormat("yyyy/MM/dd")

            val theDate = sdf.parse(selectedDate)
            //val date= sdf2.parse(selectedDate)

            //to only run if it is not null .let is used

            theDate?.let {
                val selectedDateMin = theDate.time/ 60000 //converting milliseconds in minutes; getTime and time are same thing

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                currentDate?.let {
                    val currentDateMin= currentDate.time/ 60000
                    val currentDateDay= currentDate.time * 0.000000011574

                    val diffInMin= (currentDateMin - selectedDateMin) //total time passed from 1970 to current date - total time passed from 1970 to the birth date will give the age
                    tvAge?.text= "Your age in minutes ${diffInMin.toString()} "
                    val diffInYear=  ceil(diffInMin * 0.0000019013).toInt()
                    //val period = Period.of(diffInYear,0,0)

                    val nextDate = "$dayOfMonth/${month+1}/${ceil((year*525960+diffInMin) *0.0000019013).toInt()}"

                    val nextDateIn= sdf.parse(nextDate)
                    val nextDateDay = nextDateIn.time * 0.000000011574

                    val daysto = ceil(nextDateDay - currentDateDay).toInt()
                    tvDays?.text= "Days to birthday:     ${daysto.toString()} days"






                }


            }

//            val currentYear = Year.now().toString()
//            val selectedYear = Year.parse(date.toString()).toString()







        }, year, month,day)
        dpd.datePicker.maxDate= System.currentTimeMillis() - 86400000 //3.6 million milliseconds in one hour * 24 will give in a day
        dpd.show()

    }
}


