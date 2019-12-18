/*
Written by Abhishek Jajal for CS6326.001, assignment 6, starting November  10, 2019.
NetID: apj180001
This is the Activity which manages the GamePlay,
It keeps the track of scores, and timer.
It contains a customView which is "PlayGround"
Also, it has access to Accelerometer for cheatCodes
 */
package com.example.apj180001asg6

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.apj180001asg6.PlayActivity.score.isCheatActivated
import com.example.apj180001asg6.PlayActivity.score.scoreLabel
import android.R.string.cancel
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.CountDownTimer
import android.os.Handler
import java.util.*
import android.hardware.SensorEvent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.apj180001asg6.PlayActivity.score.startTimer
import com.example.apj180001asg6.PlayActivity.score.timeLabel
import com.example.apj180001asg6.PlayActivity.score.timeLeft
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.*
import com.example.apj180001asg6.PlayActivity.score.appContext
import com.example.apj180001asg6.PlayActivity.score.cancelTime


class PlayActivity : AppCompatActivity(), SensorEventListener {

    lateinit var instructionLabel : TextView
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    // It stores all the information and methods related to scores
    object score{
        lateinit var appContext: Context
        private lateinit var gameTimer:CountDownTimer
        lateinit var timeLabel : TextView
        var noOfMissedBalloons : Int = 0
        var score:Int = 0
        lateinit var scoreLabel : TextView
        var isCheatActivated:Boolean = false

        fun updateScores(score: Int)
        {
            //updates the score, on the screen
            scoreLabel.setText("Score: "+score)
        }
        var timeLeft : Long =60 *1000 // 60 secs

        fun startTimer() {
            //starts the timer...
            gameTimer = object :CountDownTimer(timeLeft, 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    // updates the timer on screen
                    timeLabel.setText("Seconds Left: " + millisUntilFinished / 1000)
                    timeLeft = millisUntilFinished
                }

                override fun onFinish() {
                    timeLabel.setText("Seconds Left: 0")
                    //After, timer is finished.. it launches GameOver Activity
                    val intent=newIntent(appContext)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(appContext,intent,Bundle())
                    
                }
            }.start()

        }

        fun newIntent(context: Context): Intent {
            //Retruns an intent for calling GameOver Activity, also passing score, and NoOfmissedBalloons value to it.
            val intent = Intent(context, GameOverActivity::class.java)
            intent.putExtra("score", score)
            intent.putExtra("missed", noOfMissedBalloons)
            return intent
        }
        fun cancelTime(){
            //Cancels the timmer
            gameTimer.cancel()
        }



    }
    var label : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        appContext=this
        instructionLabel = findViewById(R.id.instructionLabel)
        timeLabel = findViewById(R.id.timeLabel)
        scoreLabel = findViewById(R.id.scoreLabel)
        //Getting the sensor, and registering the listener
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)

        //updates the Screen, to display the correct shape & color instruction
        when (MainActivity.correct.correctColor) {
            0 -> label += "" + resources.getString(R.string.color0)
            1 -> label += "" + resources.getString(R.string.color1)
            2 -> label += "" + resources.getString(R.string.color2)
            3 -> label += "" + resources.getString(R.string.color3)
            4 -> label += "" + resources.getString(R.string.color4)
            5 -> label += "" + resources.getString(R.string.color5)
            6 -> label += "" + resources.getString(R.string.color6)
        }

        if (MainActivity.correct.correctShape == 0)
            label += " " + resources.getString(R.string.shape0)+"s"
        else
            label += " " + resources.getString(R.string.shape1)+"s"
        instructionLabel.setText("Pop only "+label)

        // starting the timer
       startTimer()

    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //Called when, sensor, accuracy is changed.
    }

    override fun onSensorChanged(event: SensorEvent?) {
        //No Sensor value changed, checking if the device was tilted left or right
        var x= event?.values?.get(0)
        //If tilted left, then make all the news balloons to be of correct shape and color
        if(x?.compareTo(5)==1 )
            isCheatActivated=true
        else
            isCheatActivated=false

        // If tilted right, then make the time left to 10 secs
        if(x?.compareTo(-5)==-1)
        {
            score.cancelTime()
            timeLeft=10*1000
            startTimer()
        }
    }

    override fun onBackPressed() {
        //On Back Pressed, reset everything
        super.onBackPressed()
        cancelTime()
        score.score=0
        score.noOfMissedBalloons=0
        timeLeft = 60 * 1000
        finish()
    }

}
