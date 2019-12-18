/*
Written by Abhishek Jajal for CS6326.001, assignment 6, starting November  10, 2019.
NetID: apj180001
This is the activity where user enters his/her name and date.
and those details are returned to the HighScoreActivity, when fab button is clicked
 */

package com.example.apj180001asg6

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout

import kotlinx.android.synthetic.main.activity_add_score.*
import kotlinx.android.synthetic.main.content_add_score.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.app.Activity
import android.content.Intent
import android.widget.*
import android.widget.NumberPicker
import android.widget.Button
import android.app.Dialog
import com.example.apj180001asg6.R


class AddScoreActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, NumberPicker.OnValueChangeListener {

    lateinit var playerName_textInput : TextInputLayout
    lateinit var score_textInput : TextInputLayout
    lateinit var dateTime_textInput : TextInputLayout
    lateinit var dateTimePicker : ImageButton
    lateinit var displaySeconds : TextView
    var year : Int = 0
    var month : Int = 0
    var day : Int = 0
    var hour : Int = 0
    var minute : Int = 0
    var seconds : Int = 0
    var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_score)
        setSupportActionBar(toolbar)

        playerName_textInput = findViewById(R.id.textInputLayout_playerName)
        score_textInput = findViewById(R.id.textInputLayout_score)
        dateTime_textInput = findViewById(R.id.textInputLayout_dateTime)
        dateTimePicker = findViewById(R.id.dateTimePicker)
        score = intent.getIntExtra("score",0)
        score_textInput.editText?.setText(""+score)

        //Gets the current dateTime
        val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())
        // Sets the current dateTime to the editText
        // This field is not directly, editable by the user, instead the dateTimePicker button should be clicked.
        dateTime_textInput.editText?.setText(currentDateTimeString)
        //When the dateTimePicker button is clicked.
        dateTimePicker.setOnClickListener(View.OnClickListener {
            var calender = Calendar.getInstance()
            year = calender.get(Calendar.YEAR)
            month = calender.get(Calendar.MONTH)
            day = calender.get(Calendar.DAY_OF_MONTH)
            //Creates a dialog for Picking date.
            var datePickerDialog = DatePickerDialog(this@AddScoreActivity, this@AddScoreActivity, year, month, day)
            //To disable future dates
            //If you want to allow future dates... just commet the line below
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        })

        // When fab button is clicked, do the validityChecks and returns the data to HighScoreActivity
        fab.setOnClickListener { view ->
            if (isNameValid()) {
                val returnIntent = Intent(this,HighScoreActivity::class.java)
                returnIntent.putExtra("playerName", playerName_textInput.editText?.text.toString().trim())
                returnIntent.putExtra("score", score_textInput.editText?.text.toString())
                returnIntent.putExtra("dateTime", dateTime_textInput.editText?.text.toString())
                startActivity(returnIntent)
                finish()

            }
        }
    }
    // Checks if the name field is valid or not.
    fun isNameValid(): Boolean {
        val name = playerName_textInput.editText?.text.toString().trim()

        if(name.isEmpty())
        {
            //Display an error
            playerName_textInput.error="Please enter your name"
            return false
        }
        else{
            return true
        }
    }

    // Called when date is set, called timePicker
    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        year = p1
        month = p2
        day = p3
        var calendar = Calendar.getInstance()
        hour =calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)
        // Creates a dialog box to select Hour and minutes
        var timePickerDialog = TimePickerDialog(this@AddScoreActivity,this@AddScoreActivity,hour
            ,minute,false)
        timePickerDialog.show()

    }

    // Called when time is setm calls secondsPicker
    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        hour = p1
        minute = p2
        //Creates a dialog for selecting Seconds
        secondsPicker()
    }


    //Creates a dialog for seconds selection and updates the dateTime field
    fun secondsPicker()
    {
        val dialog = Dialog(this@AddScoreActivity)
        dialog.setTitle("Seconds")
        // Loads the layout for dialog
        dialog.setContentView(R.layout.seconds_dialog)
        displaySeconds = dialog.findViewById(R.id.displaySec)
        val okButton = dialog.findViewById<View>(R.id.buttonOK) as Button
        val cancelButton = dialog.findViewById<View>(R.id.buttonCancel) as Button
        val numberPicker = dialog.findViewById(R.id.numberPicker1) as NumberPicker
        //Sets the min and max values available for selection
        numberPicker.maxValue = 59
        numberPicker.minValue = 0
        numberPicker.setOnValueChangedListener(this)
        numberPicker.wrapSelectorWheel = true
        //Opens the dialog box.
        dialog.show()

        // cancel button, closes the dialog box
        cancelButton.setOnClickListener(View.OnClickListener {
            dialog.hide()
        })

        // ok button, closes the dialog box and updates the dateTime
        okButton.setOnClickListener(View.OnClickListener {
            seconds = Integer.parseInt(displaySeconds.text.substring(1,3).toString())
            val df = SimpleDateFormat("MM-dd-yy HH:mm:ss")
            val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date(year-1900,month,day,hour, minute,seconds))
            //year -1900 is passend instead of year, for more details : https://docs.oracle.com/javase/6/docs/api/java/util/Date.html
            //updates the dateTime
            textInputLayout_dateTime.editText?.setText(currentDateTimeString)
            dialog.hide()
        })
    }

    // When, value is changing in the secondsPicker, updates the display of the secondsPicker
    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
      displaySeconds.setText(String.format(":%02d s",newVal))
    }
}
