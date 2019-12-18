/*
Written by Abhishek Jajal for CS6326.001, assignment 6, starting November  10, 2019.
NetID: apj180001
This activity is executed when the user clicks on "Add to Highscore" button after the game is over, it displays the list of top 10 highscores.
It primarily manages the listView and makes fileIO function calls to read.write from/to the file

 */

package com.example.apj180001asg6

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import android.app.Activity
import android.R.attr.data
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.apj180001asg6.AddScoreActivity
import com.example.apj180001asg6.FileIO
import com.example.apj180001asg6.Score
import com.example.apj180001asg6.customListAdapter
import kotlinx.android.synthetic.main.activity_high_score.*
import kotlinx.android.synthetic.main.content_highscore.*
import kotlinx.android.synthetic.main.highscore_row.*
import java.lang.Exception


class HighScoreActivity : AppCompatActivity() {

    var listOfScores = ArrayList<Score>()
     lateinit var listView : ListView
    object Lowest{
        @RequiresApi(Build.VERSION_CODES.N)
        fun getLowestScore(appContext:Context):Long
        {
            // This function returns the least highscore.
            var x =FileIO.readScoresFromFile(appContext)
            if(x.size==10)
                return x[9].score
            else
                return 0

        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    //This function initializes the listView with the list, that it gets from the Tab Seperated file, and also manages the fab button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)
        setSupportActionBar(toolbar)

        listView = findViewById<ListView>(R.id.highScoreList)
        listOfScores= FileIO.readScoresFromFile(this)
        listView.adapter= customListAdapter(this,listOfScores)
        checkForIntent()



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    //This function deals the data returned from the AddScoreActivity, updates the listView and makes calls to FileIO functions to update the file.
    fun checkForIntent() {
        try {
            //Getting the user details and adding it to the list
            val playerName: String = intent.getStringExtra("playerName").toString()
            val scoreString: String = intent.getStringExtra("score").toString()
                .trim() // Making sure name doesn't contain extra whitespaces
            // If nothing is entered in the Score field, then it is stored as 0.
            var score: Long = 0
            if (!scoreString.isEmpty()) {
                score = scoreString.toLong()
            }
            val dateTime: String = intent.getStringExtra("dateTime").toString()
            // Creating a Score using the results returned and adding it to the listOfScores.
            listOfScores.add(Score(playerName, score, dateTime))
            listView.adapter = customListAdapter(this, listOfScores)
            //Writes the updated list in to the file
            FileIO.writeScoresToFile(this, listOfScores)

        }
        catch (e:Exception)
        {
            // It fails to recieve the intent, when opening from the main menu
        }

        }

}
