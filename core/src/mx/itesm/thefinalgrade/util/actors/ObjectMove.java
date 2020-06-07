package mx.itesm.thefinalgrade.util.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class ObjectMove {
    private Sprite sprite;
    public int x, y;

    public ObjectMove(int x, int y)
    {
        this.x = x;
        this.y = y;
        sprite = new Sprite(new Texture(Gdx.files.internal("Sprites/elements/CalendarioTransparente.png")),800,450);
    }

    public void render(final SpriteBatch batch){
        batch.draw(sprite,x,y);
    }
}
