package mx.itesm.thefinalgrade.util.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Element {

    protected Sprite sprite;

    public Element(Texture texture, float x, float y){
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public float getTextureX(){
        return sprite.getX();
    }

    public float getTextureY(){
        return sprite.getY();
    }

    public float getTextureWidth(){
        return sprite.getWidth();
    }

    public float getTextureHeight(){
        return sprite.getHeight();
    }

}
