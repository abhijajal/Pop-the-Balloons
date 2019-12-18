/*
Written by Abhishek Jajal for CS6326.001, assignment 6, starting November  10, 2019.
NetID: apj180001
This is an interface for Square Shape, Balloon class implements it.
 */
package com.example.apj180001asg6

import android.graphics.Canvas

interface SquareShape
{
    // functions required for drawing Square Shape
    fun drawSquareShape(canvas: Canvas?)
    fun draw(canvas: Canvas?)
    fun isWithinTheBounds(Xcoord:Float?,Ycoord:Float?):Int
}