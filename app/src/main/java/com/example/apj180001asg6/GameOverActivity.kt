/*
Written by Abhishek Jajal for CS6326.001, assignment 6, starting November  10, 2019.
NetID: apj180001
This activity is called when Game is Over, it displays final score, and no of missed balloons
It allows the user, to go back to Main Menu
Or if it's an highscore, add it to the AddScoreActivity
 */
package com.example.apj180001asg6

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi

class GameOverActivity : AppCompatActivity() {

    lateinit var finalScore: TextView
    lateinit var missedBalloons : TextView
    lateinit var goToHighScoreActivity: Button
    lateinit var goToMainMainMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        finalScore = findViewById(R.id.finalScore)
        missedBalloons = findViewById(R.id.missedBalloons)
        goToHighScoreActivity = findViewById(R.id.goToHighscores)
        goToMainMainMenu = findViewById(R.id.goToMain)
        //Gets the data passed by the Play Activity
        var fScore = intent.getIntExtra("score",0)
        var missed = intent.getIntExtra("missed",0)

        //Sets the final Score and No of missed Balloons
        finalScore.setText("Score: "+fScore)
        missedBalloons.setText("Missed Balloons: "+missed)

        goToMainMainMenu.setOnClickListener {
            //This will take user back to the main menu
            val openMainActivity = Intent(this, MainActivity::class.java)
            openMainActivity.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(openMainActivity)
        }
        @RequiresApi(Build.VERSION_CODES.N)
        if(fScore >= HighScoreActivity.Lowest.getLowestScore(this) )
        {
            // If it's an highscore, it would take us to the AddScoreActivity
            goToHighScoreActivity.isEnabled = true
            goToHighScoreActivity.setOnClickListener {
                val openHighScoreActivity = Intent(this, AddScoreActivity::class.java)
                openHighScoreActivity.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                openHighScoreActivity.putExtra("score",fScore)
                startActivity(openHighScoreActivity)
            }
        }
        else
            //Disables the button if its not an highscore
            goToHighScoreActivity.isEnabled = false
    }
}
