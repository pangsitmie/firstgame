package com.example.firstgame;

import android.graphics.Canvas;
/*
game object is an abstract class which is the foundation of all world object in the game
 */
public abstract class GameObject {
    protected double positionX;
    protected double positionY;

    protected double velocityX;
    protected double velocityY;

    public GameObject(double positionX, double positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void draw(Canvas canvas);
    public abstract void update();

}
