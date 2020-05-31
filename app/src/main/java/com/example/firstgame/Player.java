package com.example.firstgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
/*
*player is the main character of the game, which the user can control with a touch joystick.
*the player class is an extension of a Circle, which is an extension of a GameObject
 */
public class Player extends Circle {
    private static final double SPEED_PIXEL_PER_SECOND= 400.0;
    private static final double MAX_SPEED = SPEED_PIXEL_PER_SECOND / GameLoop.MAX_UPS;
    private final Joystick joystick;

    public Player(Context context,Joystick joystick, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.radius = radius;
        this.joystick = joystick;
    }
    public void update() {
        //update velocity based on actuator of joystick
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;
        //update position
        positionX =positionX+ velocityX;
        positionY = positionY+ velocityY;
    }
    public void setPosition(double positionX, double positionY) {
        this.positionX= positionX;
        this.positionY=positionY;
    }
}
