package mx.itesm.thefinalgrade.util.objects;

import com.badlogic.gdx.graphics.Texture;

public class Platform extends Element {
    public Platform(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    public void move(float dx, float dy){
        sprite.setPosition(sprite.getX()+dx, sprite.getY()+dy);
    }

}
