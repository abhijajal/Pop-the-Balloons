/*
Written by Abhishek Jajal for CS6326.001, assignment 6, starting November  10, 2019.
NetID: apj180001
This class represents a Score, which consists of playerName,score and DateTime.
It consist of constructor and comparable methods.
 */
package com.example.apj180001asg6

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale

class Score : Comparable<Score> {
    lateinit var playerName: String
    var score: Long =0
    lateinit var dateTime: String

    //Creates an object of class Score.
    constructor(playerName: String, score: Long, dateTime :String) {
        this.playerName = playerName
        this.score = score
        this.dateTime = dateTime
    }

    //Compares other score objects, used for Collections.sort.
    override fun compareTo(other: Score): Int {
        var x:Int =0
        // if current score is greater return -1, since we want list to be in decending order
        if(this.score > other.score)
        {
            x = -1
        }
        // if current score is same as the other score, check their dateTime
        else if(this.score == other.score)
        {
            val dateFormat = SimpleDateFormat("MMMM d, yyyy hh:mm:ss a", Locale.ENGLISH);
            val thisDate = dateFormat.parse(this.dateTime)
            val otherDate = dateFormat.parse(other.dateTime)

            // Compare the date and the most recents goes first.
            if(thisDate.compareTo(otherDate) >0 )
            {
                x = -1
            }
            else
            {
                x = 1
            }
        }
        // if current score is smaller return 1, since we want list to be in decending order
        else if(this.score < other.score)
        {
            x = 1
        }
        return x;
    }
}