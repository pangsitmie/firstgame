package object;
/*
*enemy is a caracter which always move in the direction of the player
*the enemy class is an extension of a circle which is an extension of gameobject
 */

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.firstgame.GameLoop;
import com.example.firstgame.R;

public class Enemy extends Circle{
    private static final double SPEED_PIXEL_PER_SECOND= Player.SPEED_PIXEL_PER_SECOND*0.5;
    private static final double MAX_SPEED = SPEED_PIXEL_PER_SECOND / GameLoop.MAX_UPS;


    private final Player player;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius);
        this.player = player;
        this.radius=radius;
    }

    @Override
    public void update() {
        //================================================================================
        //update velocity of enemy so that the velocity is in the direction of the player
        //================================================================================

        //calculate vector from enemy to player (in x and y)
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        //calculate (absolute) distance between enemy (this) and player
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this,player);

        //calculate direction from enemy to player
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        //set velocity in the direction to the player
        if(distanceToPlayer > 0){
            velocityX = directionX*MAX_SPEED;
            velocityY = directionY*MAX_SPEED;
        }else
        {
            velocityX=0;
            velocityY=0;
        }
        //update the position of the enemy
        positionX = positionX+ velocityX;
        positionY = positionY+ velocityY;



    }

}
