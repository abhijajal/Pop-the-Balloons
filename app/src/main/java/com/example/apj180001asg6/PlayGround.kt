/*
Written by Abhishek Jajal for CS6326.001, assignment 6, starting November  10, 2019.
NetID: apj180001
This is the customView, which creates and manages the balloons and all the activities related to it.
 */
package com.example.apj180001asg6

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.apj180001asg6.PlayActivity.score.score
import com.example.apj180001asg6.PlayGround.balloon.activeItems
import com.example.apj180001asg6.PlayGround.balloon.noOfBalloons

lateinit private var rectangle: Rect
lateinit private var paint: Paint
private var balloonSize: Int =0
private var padding : Int = 0
private var balloonList: ArrayList<Balloon>? = null
private var expectedNoOfBalloons: Int = 6
private var maxNoOfBalloons : Int =0
private var minNoOfBalloons : Int = 0
class PlayGround @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0

) : View(context, attrs, defStyleAttr)
{
    // Stores all the info about the current state of balloons
    object balloon{
        var noOfBalloons: Int = 0
        // It keeps track of whether, a bulloon is alive/ displayed on the screen or not.
        var activeItems:  IntArray = intArrayOf(0,0,0,0,0,0,0,0,0,0,0,0)
    }

    init {
        rectangle = Rect()
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        maxNoOfBalloons= 12
        minNoOfBalloons= 1
        PlayActivity.score.timeLeft = 60 * 1000
        PlayActivity.score.score = 0
        PlayActivity.score.noOfMissedBalloons = 0

    }
    override fun onDraw(canvas: Canvas?) {
        //It is called again and again until the game gets overed, by using invalidate() at the end
        // It is reponsible for drawing and updating things on the screen.
        canvas?.drawColor(Color.BLACK)

        if(balloonList == null)
        {
            // If, it the very first time... create max no of ballloons on the screen
            balloonList = ArrayList<Balloon>()
            balloonSize = (64 * getResources().getDisplayMetrics().density).toInt();
            padding = (12 * getResources().getDisplayMetrics().density).toInt();

            for(i in 0..maxNoOfBalloons-1)
            {
                //Assings the x and y co-ordinates to the balloon randomly
                var startX = ((Math.random() *(((width-2* padding - balloonSize))+1))+ padding).toInt()
                var startY = ((Math.random() *(((height- balloonSize))+1))+ balloonSize).toInt()
                //Creates and add balloons to the list
                balloonList?.add(Balloon(startX,startY, balloonSize,i))
            }
        }
        else if(noOfBalloons < expectedNoOfBalloons)
        {
            //Whenever, the no of Balloons seems to be less... and random no of balloons
            var diff =  ((Math.random() *(((7 - 1))+1))+1).toInt()
            var found = 0
            for (i in 0..maxNoOfBalloons-1)
            {
                if(activeItems[i]==0  )
                {
                    //Creating new balloons, from previously inactive balloon objects.
                    var startX = ((Math.random() *(((width-2* padding - balloonSize))+1))+ padding).toInt()
                    found++
                    balloonList?.removeAt(i) // removing the old balloon object and adding new one
                    // y  coordinate is height, since new balloons are added from the bottom
                    balloonList?.add(i,Balloon(startX,height, balloonSize,i))
                    if(found == diff)
                    {
                        break
                    }
                }
            }
        }

        for(i in 0..(maxNoOfBalloons-1))
        {
            // If, the balloon is active, Check for collions and update its location.
            if(activeItems[i]==1)
                checkForCollions(i)
                balloonList?.get(i)?.draw(canvas)
        }

        //calls the invalidate(), after every 1/60 th of a second. i.e keeping frame rate as 60 fps.
        var h = Handler()
        val r = Runnable {
            invalidate()

        }
        h.postDelayed(r,1/60)


    }

    private fun checkForCollions(i: Int) {
        //Checks for collions and update the location of each balloons accordingly, called for each and every balloon
        // Gets the center X,Y co-ordinates
        var thisX= balloonList?.get(i)?.getCenterX()!!
        var thisY= balloonList?.get(i)?.getCenterY()!!
        var otherX:Int=0
        var otherY:Int=0
        var xdis:Double=0.0
        var ydis:Double=0.0

        for(j in 0..(maxNoOfBalloons-1))
        {
            // Comparing the co-ordinates with every other active balloon
            if(activeItems[j]==1 && j!=i)
            {
                otherX= balloonList?.get(j)?.getCenterX()!!
                otherY= balloonList?.get(j)?.getCenterY()!!
                xdis= Math.sqrt(((otherX-thisX)*(otherX-thisX)).toDouble())
                ydis= Math.sqrt(((otherY-thisY)*(otherY-thisY)).toDouble())
                if(xdis <= balloonSize &&  ydis <= balloonSize)
                {
                    //Collision detected
                    // Whoever, is higher, increase it's speed and y - coordinate
                    if(thisY>otherY)
                    {
                        // updating the speed and y co-ordinate
                        balloonList?.get(i)?.speed = ((Math.random() *((20-15)+1))+15).toInt()
                        balloonList?.get(j)?.setCenterY((otherY - balloonSize))
                        balloonList?.get(j)?.speed = ((Math.random() *((10-5)+1))+5).toInt()

                    }
                    else
                    {
                        // updating the speed and y co-ordinate
                        balloonList?.get(i)?.speed = ((Math.random() *((15-10)+1))+10).toInt()
                        balloonList?.get(i)?.setCenterY((otherY - balloonSize))
                        balloonList?.get(j)?.speed = ((Math.random() *((10-5)+1))+5).toInt()

                    }

                }
            }

        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // Gets the touch co-ordinates, calls isCorrectTouch and updates the socres
        var xP = event?.getX()
        var yP = event?.getY()
        if(isCorrectTouch(xP,yP) == 1) {
            //correct touch
            score += 1
            // If the score is a mulitple of 10, add 10 secs to the timer.
            if(score %10 ==0)
            {
                PlayActivity.score.cancelTime()
                PlayActivity.score.timeLeft+=10*1000
                PlayActivity.score.startTimer()
            }

        }
        else if(isCorrectTouch(xP,yP) == -1 && score >0)
            // Incorrect touch
            score -=1

        // updates the score, on the screen
        PlayActivity.score.updateScores(score)

        return super.onTouchEvent(event)

    }

    fun isCorrectTouch(xCoord: Float? , yCoord:Float?): Int? {
        // Determines, whether the Touch is correct or not
        for (i in 0..maxNoOfBalloons-1) {
            if (xCoord != null && yCoord != null) {
                if(activeItems[i]==1) {
                    if (balloonList?.get(i)?.isWithinTheBounds(xCoord, yCoord) == 1)
                        // returns 1 for correct touch
                        return 1
                    else if (balloonList?.get(i)?.isWithinTheBounds(xCoord, yCoord) == -1)
                        // returns -1 for incorrect touch
                        return -1
                }
            }
        }
        // returns 0 for accidental touches
        return 0
    }
}