/*
Written by Abhishek Jajal for CS6326.001, assignment 6, starting November  10, 2019.
NetID: apj180001
This a customListAdapter for the listView which stores objects of scores in a list.
 */

package com.example.apj180001asg6

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.apj180001asg6.R
import java.util.*
import kotlin.collections.ArrayList

class customListAdapter(context: Context,listOfScore: ArrayList<Score>) : BaseAdapter(){

    private var listOfScores: ArrayList<Score>
    private val mContext:Context
    init {
            mContext = context
            listOfScores = listOfScore
            // If the list size is greater than 1 than sort it.
            if(listOfScores.size >1)
            {
                Collections.sort(listOfScores)
            }
            // else If the list is empty, display a Toast message!
            else if(listOfScores.size == 0)
            {
                //Toast.makeText(mContext,"No Scores found !",Toast.LENGTH_LONG).show()
            }
        }
    // Returns the number of items in the list
     override fun getCount(): Int {
         // If the list size is greater than 10, then only return 10
         if(listOfScores.size > 10)
             return 10
         else
             return listOfScores.size
     }

     override fun getItem(p0: Int): Any {
         return "TEST STRING"
     }

     override fun getItemId(p0: Int): Long {
         return p0.toLong()
     }
    // Returns the view for each item in the list
     override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val rowMain= layoutInflater.inflate(R.layout.highscore_row,p2, false)
         var currentScore = listOfScores.get(p0)

         val ranking = rowMain.findViewById<TextView>(R.id.ranking)
        //sets the ranks into the list
         ranking.setText((p0+1).toString())
         val playerName = rowMain.findViewById<TextView>(R.id.playerName)
        //sets the playerName
         playerName.setText(currentScore.playerName)

         val score = rowMain.findViewById<TextView>(R.id.score)
        //sets the score
         score.setText(currentScore.score.toString())

         val dateTime = rowMain.findViewById<TextView>(R.id.dateTime)
        //sets the dateTime
         dateTime.setText(currentScore.dateTime)

         return  rowMain
     }
 }