package mx.itesm.thefinalgrade.util.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import mx.itesm.thefinalgrade.util.variables.Constants;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class PlayerActor extends Actor {

    private Texture textureBoy, textureGirl;

    private Sprite sprite;

    private World world;

    private Body body;

    private Fixture fixture;

    private boolean alive = true, jumping = false, walking = false;

    private Animation<Texture> walkBoyAnimation, walkGirlAnimation, standBoyAnimation, standGirlAnimation, jumpBoyAnimation, jumpGirlAnimation;

    private float playerWidth = 0.5f, playerHeight = 0.5f, velocity = 0f, animationTime = 0f;

    private boolean isBoy;

    public PlayerActor(World world, Texture textureBoy, Animation<Texture> walkBoyAnimation,
                       Texture textureGirl, Animation<Texture> walkGirlAnimation, Animation<Texture> standBoyAnimation,
                       Animation<Texture> standGirlAnimation, Animation<Texture> jumpGirlAnimation, Animation<Texture> jumpBoyAnimation,Vector2 position){
        this.world = world;
        this.textureBoy = textureBoy;
        this.walkBoyAnimation = walkBoyAnimation;
        this.standBoyAnimation = standBoyAnimation;
        this.jumpBoyAnimation = jumpBoyAnimation;
        this.textureGirl = textureGirl;
        this.walkGirlAnimation = walkGirlAnimation;
        this.standGirlAnimation = standGirlAnimation;
        this.jumpGirlAnimation = jumpGirlAnimation;
        this.isBoy = UserPreferences.getInstance().getGender();
        if(isBoy){
            this.sprite = new Sprite(textureBoy);
        } else {
            this.sprite = new Sprite(textureGirl);
        }
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);
        body.setFixedRotation(true);


        PolygonShape box = new PolygonShape();
        box.setAsBox(playerWidth, playerHeight);
        fixture = body.createFixture(box, 3);
        fixture.setFriction(0);
        box.dispose();

        fixture.setUserData("player");

        sprite.setSize((2*playerWidth)*Constants.PIXELS_IN_METER, (2*playerHeight)*Constants.PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition((body.getPosition().x - playerWidth) * Constants.PIXELS_IN_METER,
                    (body.getPosition().y - playerHeight) * Constants.PIXELS_IN_METER);

        //Estando quieto
        if(!isJumping() && velocity == 0){
            if (isBoy){
                sprite.setTexture(standBoyAnimation.getKeyFrame(animationTime));
            } else{
                sprite.setTexture(standGirlAnimation.getKeyFrame(animationTime));
            }
            sprite.setFlip(false, false);
            sprite.draw(batch);

        //Moviendose a la derecha
        } else if(velocity >  0.05 && !isJumping()) {
            walking = true;
            if (isBoy) {
                sprite.setTexture(walkBoyAnimation.getKeyFrame(animationTime));
            } else {
                sprite.setTexture(walkGirlAnimation.getKeyFrame(animationTime));
            }
            sprite.setFlip(false, false);
            sprite.draw(batch);

        //Saltando
        } else if(isJumping()){
            if (isBoy) {
                sprite.setTexture(jumpBoyAnimation.getKeyFrame(animationTime));
            } else {
                sprite.setTexture(jumpGirlAnimation.getKeyFrame(animationTime));
            }
            sprite.setFlip(false, false);
            sprite.draw(batch);

        //Moviendose a la izquierda
        } else if(velocity < 0.05 && !isJumping()){
            walking = true;
            if(isBoy){
                sprite.setTexture(walkBoyAnimation.getKeyFrame(animationTime));
            } else {
                sprite.setTexture(walkGirlAnimation.getKeyFrame(animationTime));
            }
            sprite.setFlip(true, false);
            sprite.draw(batch);
        }
    }

    @Override
    public void act(float delta) {
        if(walking){
            animationTime+= delta;
            if(animationTime > 1f){
                animationTime = 0f;
            }
        }else if(isJumping()){
            animationTime+= delta;
            if(animationTime > 1f){
                animationTime = 0f;
            }
        }else if(!walking){
            animationTime+= delta;
            if(animationTime > 1f){
                animationTime = 0f;
            }
        }
    }

    public void jump() {
        if(!jumping){
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, 20, position.x, position.y, true);
            jumping = true;
        }
    }

    public void move(float impulse){
        velocity = impulse;
        body.setLinearVelocity(2*velocity, body.getLinearVelocity().y);
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isJumping(){
        return jumping;
    }

    public void setJumping(boolean jumping){
        this.jumping = jumping;
    }

    public Body getBody(){
        return body;
    }

}
