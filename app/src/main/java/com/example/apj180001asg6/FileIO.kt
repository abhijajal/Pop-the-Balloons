/*
Written by Abhishek Jajal for CS6326.001, assignment 6, starting November  10, 2019..
NetID: apj180001
This is a singleton class.
It defines all the functions which deals with Tab seperated file.
 */

package com.example.apj180001asg6

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.*
import java.lang.Exception


object FileIO{
    val fileName = "HighScores.txt" //name of the file
    lateinit var file:File;
    val separator: String ="\t" // sepearator or deliminator

    @RequiresApi(Build.VERSION_CODES.N)
    //Checks for the file, and writes the ArrayList into the file.
    fun writeScoresToFile(mContext:Context,listOfScores: ArrayList<Score>)
    {
        createFile(mContext)
        try {
            var fileWriter: FileWriter = FileWriter(file);
            var bfWriter: BufferedWriter = BufferedWriter(fileWriter);
            var count: Int = 0
            // For each object in ArrayList
            for (eachScore in listOfScores) {
                if(count >= 10)
                {
                    //Only write the top 10 objects from the ArrayList into the file.
                    break
                }
                //Writes into the file
                bfWriter.write(eachScore.playerName + separator + eachScore.score + separator + eachScore.dateTime + "\n")
                count++
            }
            bfWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    // Reads the file, and returns an ArrayList of Score objects.
    fun readScoresFromFile(mContext: Context) : ArrayList<Score>
    {
        var listOfScores: ArrayList<Score> = ArrayList<Score>()
        createFile(mContext)
        var strLine: String
        var data: List<String>
        val fis = FileInputStream(file)
        val r = BufferedReader(InputStreamReader(fis))
        var noException : Boolean = true
        do {
            try {
                strLine = r.readLine()
                data= strLine.split(separator) //split the line using the separator
                listOfScores.add(Score(data[0],data[1].toLong(),data[2])) //adding the object to the ArrayList

            }
            catch (e:Exception)
            {
                noException= false
            }

        } while (noException)
        //Loops until exception is occured i.e it reaches end of the file.
        r.close()
        //Returns the ArrayList
        return listOfScores
    }

    @RequiresApi(Build.VERSION_CODES.N)
    //Initializing the file variable, creates a file if it doesn't exists.
    fun createFile(context : Context){
        //dataDir is directory inside the app package, where file is being created.
         file = File(context.dataDir,fileName)
        // creates a file if it doesn't exists.
        if(!file.exists())
        {
            file.createNewFile()
        }
    }
}