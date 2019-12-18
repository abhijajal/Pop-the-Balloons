/*
Written by Abhishek Jajal for CS6326.001, assignment 6, starting November  10, 2019.
NetID: apj180001
This is an interface for Circular Shape, Balloon class implements it.
 */

package com.example.apj180001asg6

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

interface CircularShape
{
    // functions required for drawing Circular Shape
    fun drawCircularShape(canvas: Canvas?)
    fun draw(canvas: Canvas?)
    fun isWithinTheBounds(Xcoord:Float?,Ycoord:Float?):Int
}