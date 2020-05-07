package mx.itesm.thefinalgrade.util.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class PropsActor extends Actor {

    private Texture texture;
    private Vector2 position = new Vector2(0, 0);
    private Vector2 size = new Vector2(0, 0);
    private boolean canMove;

    public PropsActor(Texture texture, Vector2 position, Vector2 size){

        this.texture = texture;
        this.position = position;
        this.size = size;
        this.canMove = false;

        setPosition(position.x, position.y);
        setSize(size.x, size.y);
    }

    public PropsActor(Texture texture, Vector2 position, Vector2 size, boolean canMove){

        this.texture = texture;
        this.position = position;
        this.size = size;
        this.canMove = canMove;

        setPosition(position.x, position.y);
        setSize(size.x, size.y);
    }

    public PropsActor(Texture texture, Vector2 position){

        this.texture = texture;
        this.position = position;
        this.canMove = false;

        setPosition(position.x, position.y);
        setSize(texture.getWidth(), texture.getHeight());
    }

    public PropsActor(Texture texture, Vector2 position, boolean canMove){

        this.texture = texture;
        this.position = position;
        this.canMove = canMove;

        setPosition(position.x, position.y);
        setSize(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(position.x, position.y);
        setSize(size.x, size.y);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void setPosition(float x, float y){
        position.set(x, y);
    }

    public void setSize(float width, float height){
        size.set(width, height);
    }

    public Vector2 getPosition(){
        return position;
    }

    public Vector2 getSize(){
        return size;
    }
}
