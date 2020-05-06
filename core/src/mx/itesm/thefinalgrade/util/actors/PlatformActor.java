package mx.itesm.thefinalgrade.util.actors;

import com.badlogic.gdx.graphics.Texture;
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

public class PlatformActor extends Actor {
    private TextureRegion texture;

    private World world;

    private Body body;

    private Fixture fixture;

    private boolean alive = true, jumping = false;

    private float width = 2f, height = 1f;
    private float boxHeight = 0.1f;

    public PlatformActor(World world, TextureRegion textureRegion, Vector2 position){
        this.world = world;
        this.texture = textureRegion;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(width, boxHeight);
        fixture = body.createFixture(box, 3);
        box.dispose();

        fixture.setUserData("platform");

        setSize((2*width)* Constants.PIXELS_IN_METER, (2*height)*Constants.PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - width) * Constants.PIXELS_IN_METER,
                    (body.getPosition().y - 0.25f) * Constants.PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
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
}
