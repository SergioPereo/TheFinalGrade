package mx.itesm.thefinalgrade.util.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import mx.itesm.thefinalgrade.util.variables.BaseScreen;
import mx.itesm.thefinalgrade.util.variables.Constants;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class WinActor extends Actor {

    private World world;

    private Body body;

    private Fixture fixture;

    private float width = 0.5f, height = 0.5f;

    public WinActor(World world, Vector2 position){
        this.world = world;
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.25f, 0.25f);
        fixture = body.createFixture(box, 3);
        box.dispose();

        fixture.setUserData("win");

        setSize((2*width)* Constants.PIXELS_IN_METER, (2*height)*Constants.PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }


    public Fixture getFixture(){
        return fixture;
    }

    public Body getBody(){
        return body;
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
