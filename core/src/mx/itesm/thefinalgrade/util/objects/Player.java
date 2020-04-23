package mx.itesm.thefinalgrade.util.objects;

import com.badlogic.gdx.graphics.Texture;

public class Player extends Element{

    private float yAcceleration = 0f;
    private float initialPosition;
    private boolean isGrounded = false, onGroundForFirstTime = false;
    public Player(Texture texture, float x, float y) {
        super(texture, x, y);
        initialPosition=y;
    }

    public void move(float dx, float dy){
        sprite.setPosition(sprite.getX()+dx, sprite.getY()+dy);
    }

    public void gravity(float force){
        if(isGrounded&&onGroundForFirstTime){
            initialPosition=sprite.getY();
            yAcceleration = 0;
            onGroundForFirstTime = false;
        } else if(isGrounded){

        } else if(!isGrounded){
            onGroundForFirstTime = true;
            yAcceleration += force;
        }

        initialPosition+=yAcceleration;
        sprite.setY(initialPosition);
        System.out.println("Y Acceleration: " + yAcceleration);
        System.out.println("Player Y: " + sprite.getY());

    }

    public void setYAcceleration(float acceleration){
        yAcceleration = acceleration;
        System.out.println("Y Acceleration: " + yAcceleration);
    }

    public void setIsGrounded(boolean isJumping){
        this.isGrounded = isJumping;
    }

    public void setIsGroundedForFirstTime(boolean isGroundedForFirstTime){
        this.onGroundForFirstTime = isGroundedForFirstTime;
    }

    public float getyAcceleration(){
        return yAcceleration;
    }

    public boolean isGrounded(){
        return isGrounded;
    }

    public boolean isOnGroundForFirstTime(){
        return this.onGroundForFirstTime;
    }
    //No está siendo utilizado
}
