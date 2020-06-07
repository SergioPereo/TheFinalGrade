package mx.itesm.thefinalgrade.util.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import mx.itesm.thefinalgrade.util.variables.Constants;

public class NormalPlatformActor extends Actor {

    private TextureRegion texture;

    private World world;

    private Body body;

    private Fixture fixture;

    private float width = 2f, height = 1f;
    private float boxHeight = 0.1f;

    public NormalPlatformActor(World world, TextureRegion textureRegion, Vector2 position){
        this.world = world;
        this.texture = textureRegion;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.80f, boxHeight);
        fixture = body.createFixture(box, 3);
        box.dispose();

        fixture.setUserData("platform");

        setSize((width)* (5*Constants.PIXELS_IN_METER/4), (height)*(2*Constants.PIXELS_IN_METER)/3);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 1.28f) * Constants.PIXELS_IN_METER,
                (body.getPosition().y - 0.35f) * Constants.PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }


    public Body getBody() {
        return body;
    }

    public void moverArriba(){
        Vector2 vec = new Vector2(0.0f, 0.17f);
        this.getBody().setLinearVelocity(vec);
    }

}
