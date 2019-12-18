/*
Written by Abhishek Jajal for CS6326.001, assignment 6, starting November  10, 2019.
NetID: apj180001
This is the MainActivity, it gets called as soon as the app is opened.
It greets the user, displays the instructions and provides the user with "OK!" play button to play the game,
and also the View Highscore button, to check the top 10 highscores.
 */
package com.example.apj180001asg6

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.apj180001asg6.MainActivity.correct.correctColor
import com.example.apj180001asg6.MainActivity.correct.correctShape

class MainActivity : AppCompatActivity() {

    private lateinit var okButton:Button
    private lateinit var textInstruction : TextView
    private lateinit var highscoreButton: Button
    private var instruction : String = ""
    //This stores the infomation, about which shape and color is correct
    object correct{

        var correctColor: Int = 0
        var correctShape: Int = 0

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        okButton = findViewById(R.id.button)
        textInstruction = findViewById(R.id.textInstruction)
        highscoreButton = findViewById(R.id.highscore)
        //Randomly assigns the shape
        correctShape = (Math.random() *((1)+1)).toInt()
        //Randomly assigns the correctColor
        correctColor = (Math.random() *((6)+1)).toInt()

        instruction=textInstruction.text.toString()

        when (correctColor) {
            0 -> instruction+="\n"+resources.getString(R.string.color0)
            1 -> instruction+="\n"+resources.getString(R.string.color1)
            2 -> instruction+="\n"+resources.getString(R.string.color2)
            3 -> instruction+="\n"+resources.getString(R.string.color3)
            4 -> instruction+="\n"+resources.getString(R.string.color4)
            5 -> instruction+="\n"+resources.getString(R.string.color5)
            6 -> instruction+="\n"+resources.getString(R.string.color6)
        }

        //Updates the Instructions, based on the correct Color and correct shape.
        if(correctShape == 0)
            instruction+= " " + resources.getString(R.string.shape0)+"s"
        else
            instruction+= " " + resources.getString(R.string.shape1)+"s"


        textInstruction.setText(instruction)

        okButton.setOnClickListener {
            // Launches the Game Play screen
           val openPlayActivity = Intent(this, PlayActivity::class.java)
            startActivity(openPlayActivity)

        }

        highscoreButton.setOnClickListener {
            //Launches the HighScore Activity
            val openHighScoreActivity = Intent(this, HighScoreActivity::class.java)
            startActivity(openHighScoreActivity)

        }
    }
}
