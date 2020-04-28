package mx.itesm.thefinalgrade.util.actors;

import com.badlogic.gdx.Gdx;
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

public class PlayerActor extends Actor {

    private Texture texture;

    private Sprite sprite;

    private World world;

    private Body body;

    private Fixture fixture;

    private boolean alive = true, jumping = false, walking = false;

    private Animation<Texture> walkAnimation;

    private float playerWidth = 0.5f, playerHeight = 0.5f, velocity = 0f, animationTime = 0f;

    public PlayerActor(World world, Texture texture, Animation<Texture> walkAnimation, Vector2 position){
        this.world = world;
        this.texture = texture;
        this.walkAnimation = walkAnimation;
        this.sprite = new Sprite(texture);
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);
        body.setFixedRotation(false);


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
        if(velocity < 0.05 && velocity > -0.05 ){
            walking = false;
            sprite.setTexture(texture);
            sprite.draw(batch);
        } else if(velocity >  0.05){
            walking = true;
            sprite.setTexture(walkAnimation.getKeyFrame(animationTime));
            sprite.setFlip(false, false);
            sprite.draw(batch);
        } else {
            walking = true;
            sprite.setTexture(walkAnimation.getKeyFrame(animationTime));
            sprite.setFlip(true, false);
            sprite.draw(batch);
        }
    }

    @Override
    public void act(float delta) {
        if(walking){
            animationTime+=delta;
            if(animationTime > 1f){
                animationTime = 0f;
            }
        }
    }

    public void jump() {
        if(!jumping){
            jumping = true;
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, 20, position.x, position.y, true);
        }
    }

    public void move(float impulse){
        velocity = impulse;
        body.setLinearVelocity(velocity, 0);
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

}
