package object;
/*
*enemy is a character which always move in the direction of the player
*the enemy class is an extension of a circle which is an extension of gameobject
 */

import android.content.Context;
import object.Circle;
import object.Player;
import object.GameObject;
import androidx.core.content.ContextCompat;

import com.example.firstgame.GameLoop;
import com.example.firstgame.R;

public class Enemy extends Circle{
    private static final double SPEED_PIXEL_PER_SECOND= Player.SPEED_PIXEL_PER_SECOND*0.8;
    private static final double MAX_SPEED = SPEED_PIXEL_PER_SECOND / GameLoop.MAX_UPS;

    private static final double SPAWN_PER_MINUTES = 20;
    private static final double SPAWN_PER_SECOND = SPAWN_PER_MINUTES/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWN_PER_SECOND;
    private static double updateUntilNextSpawn=UPDATES_PER_SPAWN;
    private final Player player;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius);
        this.player = player;

    }


    public Enemy(Context context, Player player) {
        super(
            context,
            ContextCompat.getColor(context, R.color.enemy),
            Math.random()*1000,
            Math.random()*1000,
            40);
        this.player = player;
    }

    public static boolean readyToSpawn() {
        if(updateUntilNextSpawn <= 0){
            updateUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        }else{
            updateUntilNextSpawn --;
            return false;
        }
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
        positionX += velocityY;
        positionY += velocityY;


    }

}
