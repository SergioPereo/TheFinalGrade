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

public class ItemActor extends Actor {

    private Texture sheetTexture, brokenSheetTexture, coffeeTexture, wrinkledSheetTexture;

    private World world;

    private Body body;

    private Fixture fixture;

    private float width = 0.5f, height = 0.5f;

    private ItemType type;

    public enum ItemType{
        SHEET, BROKEN_SHEET, COFFEE, WRINKLED_SHEET
    }

    public ItemActor(World world, Texture sheetTexture, Texture brokenSheetTexture,
                     Texture coffeeTexture, Texture wrinkledSheetTexture, Vector2 position, ItemType type){
        this.world = world;
        this.sheetTexture = sheetTexture;
        this.brokenSheetTexture = brokenSheetTexture;
        this.coffeeTexture = coffeeTexture;
        this.wrinkledSheetTexture = wrinkledSheetTexture;
        this.type = type;
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.25f, 0.25f);
        fixture = body.createFixture(box, 3);
        box.dispose();

        fixture.setUserData("item");

        setSize((2*width)* Constants.PIXELS_IN_METER, (2*height)*Constants.PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - width) * Constants.PIXELS_IN_METER,
                (body.getPosition().y - height) * Constants.PIXELS_IN_METER);
        switch (type){
            case SHEET:
                batch.draw(sheetTexture, getX(), getY(), getWidth(), getHeight());
                break;
            case COFFEE:
                batch.draw(coffeeTexture, getX(), getY(), getWidth(), getHeight());
                break;
            case BROKEN_SHEET:
                batch.draw(brokenSheetTexture, getX(), getY(), getWidth(), getHeight());
                break;
            case WRINKLED_SHEET:
                batch.draw(wrinkledSheetTexture, getX(), getY(), getWidth(), getHeight());
                break;
        }
    }

    public ItemType getType(){
        return type;
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

    public void moverArriba(){
        Vector2 vec = new Vector2(0.0f, 0.15f);
        this.getBody().setLinearVelocity(vec);
    }

    public void moverDerecha(){
        Vector2 vec = new Vector2(0.12f, 0.0f);
        this.getBody().setLinearVelocity(vec);
    }

    public void moverIzquierda(){
        Vector2 vec = new Vector2(-0.12f, 0.0f);
        this.getBody().setLinearVelocity(vec);
    }

    public void moverAbajo(){
        Vector2 vec = new Vector2(0.0f, -0.25f);
        this.getBody().setLinearVelocity(vec);
    }

}
