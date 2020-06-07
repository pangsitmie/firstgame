package com.example.firstgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import object.Circle;
import object.Enemy;
import object.Player;
import object.Spell;

/*
*game manager all object in the game and is responsible for updating all states and render all
* objects to the screen
*/

class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    //private final Enemy enemy;

    private GameLoop gameLoop;
    private List<Enemy> enemyList = new ArrayList<>();
    private List<Spell> spellList = new ArrayList<Spell>();

    public Game(Context context) {
        super(context);

        //get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop  = new GameLoop(this,surfaceHolder);
        //initialize player
        joystick = new Joystick(275,700, 70,40);
        player = new Player(getContext(), joystick , 2*500,500, 40);
        //enemy = new Enemy(getContext(), player , 500,400, 40);

        setFocusable(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(joystick.getIsPressed()) {
                    //joystick was pressed before this event
                    spellList.add(new Spell(getContext(), player));
                } else if(joystick.isPressed((double) event.getX(),(double) event.getY())) {
                    joystick.setIsPressed(true);
                }else{
                    //joystick was not pressed -> cast spell
                    spellList.add(new Spell(getContext(), player));
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                //joystick was pressed previously
                if(joystick.getIsPressed()){
                    joystick.setActuator((double) event.getX(),(double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                // joystick was let go of -> setIsPressed(false) and reset Actuator
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;

        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void drawUPS(Canvas canvas)
    {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100,100,paint);
    }

    public void drawFPS(Canvas canvas)
    {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100,200,paint);
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);

        player.draw(canvas);
        joystick.draw(canvas);
        for(Enemy enemy: enemyList){
            enemy.draw(canvas);
        }
        for(Spell spell : spellList){
            spell.draw(canvas);
        }



    }

    public void update() {
        //update game state
        joystick.update();
        player.update();

        //spawn enemy if it is time to spawn new enemies
        if(Enemy.readyToSpawn()){
            enemyList.add(new Enemy(getContext(), player));
        }

        //update state of each enemy
        for(Enemy enemy : enemyList){
            enemy.update();
        }

        //update state of each spell
        for(Spell spell : spellList){
            spell.update();
        }

        //Iterate through enemyList and check for colliusion between each enem and the player
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();

        while(iteratorEnemy.hasNext()){
            if(Circle.isColliding(iteratorEnemy.next(), player)){
                //remove enemy if it collides with the player
                iteratorEnemy.remove();

            }
        }

    }
}
