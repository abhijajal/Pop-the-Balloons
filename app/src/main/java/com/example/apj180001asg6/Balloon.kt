/*
Written by Abhishek Jajal for CS6326.001, assignment 6, starting November  10, 2019.
NetID: apj180001
Balloon class implements two interfaces : SquareShape and Circular Shape and also override, its methods.
This is a class for Balloon, all the Balloons on the screen, are the instances of class.
It handles all the activities and events related to a specific balloon
 */


package com.example.apj180001asg6

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import com.example.apj180001asg6.PlayActivity.score.isCheatActivated

class Balloon : SquareShape,CircularShape{

    private var currentColor: Int = 0
    private var currentColorCode: Int =0
    private var currentY: Int = 0
    var speed : Int =0
    private var currentBulloonSize:Int =0
    private var currentShape: Int = 0
    private var startX: Int = 0
    private var startY: Int = 0
    private var bulloonSize: Int = 0
    private var rectangles : Rect
    private var paints: Paint
    private var isPopped : Boolean = false
    private var index : Int = 0

    //This is the constructor, which requires the starting co-ordinate, max Bulloon Size, and index.
    constructor(startX:Int,startY: Int,bulloonSize: Int,index: Int)
    {
        this.startX = startX
        this.startY = startY
        this.bulloonSize = bulloonSize
        rectangles = Rect()
        paints = Paint(Paint.ANTI_ALIAS_FLAG)
        currentBulloonSize = bulloonSize
        this.index = index
        //Making sure, that the balloon which, is being is created is not active
        if(PlayGround.balloon.activeItems[index]==0)
        {
            PlayGround.balloon.activeItems[index]=1
            PlayGround.balloon.noOfBalloons+=1
            Log.v("Balllonn","Created @"+index)
            isPopped = false
            currentY = startY
            Log.v("currentY,StartY",""+currentY+","+startY)
            //Random speed from 15 to 5 co-ordinates per cycle
            speed = ((Math.random() *((15-5)+1))+5).toInt()
            // Random balloon size from 64 tp 32 dp
            currentBulloonSize = ((bulloonSize * ((Math.random() *50)+50))/100).toInt()
            // Random shape
            currentShape = (Math.random() *((1)+1)).toInt()
            // Random color from 0 to 6
            currentColorCode =(Math.random() *((6)+1)).toInt()
            // If, cheat code is Activated, every new bulloon should be correct color and shape
            if(isCheatActivated== true)
            {
                currentColorCode = MainActivity.correct.correctColor
                currentShape = MainActivity.correct.correctShape
            }
            when (currentColorCode) {
                0 -> currentColor = Color.RED
                1 -> currentColor = Color.parseColor("#ffa500")
                2 -> currentColor = Color.YELLOW
                3 -> currentColor = Color.GREEN
                4 -> currentColor = Color.BLUE
                5 -> currentColor = Color.parseColor("#800080")
                6 -> currentColor = Color.WHITE
            }
        }
        else{
            Log.v("Failed","To create Bulloon")
        }


    }

    override fun drawCircularShape(canvas: Canvas?)
    {
        // Draws a circle on Canvas
        canvas?.drawCircle((startX + currentBulloonSize/2.0).toFloat(),(currentY-(currentBulloonSize/2.0)).toFloat(),(currentBulloonSize/2.0).toFloat(),paints)
    }


    override fun drawSquareShape(canvas: Canvas?)
    {
        // Draws a square on Canvas
        rectangles.set(startX,currentY-(currentBulloonSize),startX+currentBulloonSize,currentY)
        canvas?.drawRect(rectangles, paints)

    }
    override fun draw(canvas: Canvas?)
    {
        // This function, is reponsible for drawing a specific balloon and its motion
        if(currentY < currentBulloonSize ){
                    //Destorying the bullon, if it reaches the top
            if(PlayGround.balloon.activeItems[index]==1)
            {
                //Making sure the balloon, we are destroying is active

                if(currentColorCode.equals(MainActivity.correct.correctColor)) {
                    if (currentShape.equals(MainActivity.correct.correctShape)) {
                        if(isPopped == false) {
                            //Calculates and updates the noOfMissedBalloons
                            PlayActivity.score.noOfMissedBalloons += 1
                            Log.v("Missed Bullons", "" + PlayActivity.score.noOfMissedBalloons)
                        }
                    }
                }

                PlayGround.balloon.activeItems[index]=0
                PlayGround.balloon.noOfBalloons -= 1
                Log.v("Balllonn","Destroyed @"+index)
                Log.v("CurrentY",""+currentY)
                Log.v("CurrentBalloonSize",""+currentBulloonSize)

            }
            else{
                Log.v("Failed","To Destory Bulloon")
            }
            return

        }

        // If it's not popped yet, than update its location, by drawing it again
        if(isPopped == false)
        {
            DrawShape(canvas)
        }


    }
    fun DrawShape(canvas: Canvas?)
    {
        //This function is reponsible for drawing the balloon
        paints.setColor(currentColor)
        if(currentShape == 1){
            drawSquareShape(canvas)
        }
        else
            drawCircularShape(canvas)
        paints.setColor(Color.WHITE)
        paints.setStyle(Paint.Style.STROKE);

        // This draws a string attached to a balloon
        canvas?.drawArc((startX+currentBulloonSize*3/8).toFloat(),currentY.toFloat(),(startX+currentBulloonSize*5/8).toFloat(),(currentY+currentBulloonSize/4).toFloat(),90.0f,180.0f,false,paints)
        canvas?.drawArc((startX+currentBulloonSize*3/8).toFloat(),(currentY+currentBulloonSize/4-0.25).toFloat(),(startX+currentBulloonSize*5/8).toFloat(),(currentY+currentBulloonSize/2).toFloat(),270.0f,180.0f,false,paints)
        canvas?.drawArc((startX+currentBulloonSize*3/8).toFloat(),(currentY+currentBulloonSize/2-0.25).toFloat(),(startX+currentBulloonSize*5/8).toFloat(),(currentY+currentBulloonSize*3/4).toFloat(),90.0f,180.0f,false,paints)
        canvas?.drawArc((startX+currentBulloonSize*3/8).toFloat(),(currentY+currentBulloonSize*3/4-0.25).toFloat(),(startX+currentBulloonSize*5/8).toFloat(),(currentY+currentBulloonSize).toFloat(),270.0f,180.0f,false,paints)

        paints.setStyle(Paint.Style.FILL)
        // Y coordinate will decrease based on the speed
        currentY -= speed
    }
    fun getCenterX(): Int
    {
        //Returns the X center co-ordinate
        return startX+currentBulloonSize/2
    }
    fun getCenterY(): Int
    {
        // Returns the Y center co-ordinate
        return currentY-currentBulloonSize/2
    }
    fun setCenterX(Xp:Int)
    {
        //Sets the X center co-ordinate
        startX=Xp-currentBulloonSize/2
    }
    fun setCenterY(Yp:Int)
    {
        //Sets the Y center co-ordinate
        currentY= Yp+ currentBulloonSize/2
    }

    override fun isWithinTheBounds(Xcoord:Float?,Ycoord:Float?):Int
    {
        //This function, is used to detect onTouch events
        // Returns 1 for correct Touch, -1 for incoorect shape/color and 0 for any other case.
        if (Xcoord != null) {
            if(Xcoord >= startX && Xcoord <= startX+currentBulloonSize )
            {
                Log.v("X","within")
                if (Ycoord != null) {
                    //checking if the position clicked, is on the balloon
                    if(Ycoord >= currentY-currentBulloonSize && Ycoord <= currentY )
                    {
                        // checking if color is correct
                        if(currentColorCode.equals(MainActivity.correct.correctColor))
                        {
                            //checking if shape is correct
                            if(currentShape.equals(MainActivity.correct.correctShape) )
                            {

                                isPopped = true
                                //Destorying the bullon
                                if(PlayGround.balloon.activeItems[index]==1)
                                {
                                    PlayGround.balloon.activeItems[index]=0
                                    PlayGround.balloon.noOfBalloons -= 1
                                    Log.v("Balllonn","Destroyed @"+index)
                                    Log.v("CurrentY",""+currentY)
                                    Log.v("CurrentBalloonSize",""+currentBulloonSize)

                                }
                                else{
                                    Log.v("Failed","To Destory Bulloon")
                                }
                                return 1
                            }
                            else
                            {
                                return -1
                                //Incorrect Shape
                            }
                        }
                        else
                        {
                            return -1
                            //Incorrect color
                        }
                    }
                    else
                        return 0
                }
                else
                    return 0
            }
            else
                return 0
        }
        else
            return 0
    }
} 