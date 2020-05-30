package mx.itesm.thefinalgrade.util.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mx.itesm.thefinalgrade.util.Pantalla;

public class ObjectMove {
    //Animación
    private Sprite sprite;
    public int x, y;
    private float DX = 4;
    private float DY = 4;
    public ObjectMove(int x, int y)
    {
        this.x = x;
        this.y = y;
        sprite = new Sprite(new Texture(Gdx.files.internal("Sprites/player/boy/boy-start.png")),512,512);
    }
    public void drawObject(SpriteBatch batch){
        sprite.draw(batch);
    }
    public void moveX(){
        float xp = sprite.getX();
        float yp = sprite.getY();
        if(xp>= Pantalla.ANCHO-sprite.getWidth()||xp<=0){
            DX = -DX;
        }
        /*if(yp>= Pantalla.ANCHO-sprite.getWidth()||yp<=0){
            DX = -DY;
        }

         */
        sprite.setPosition(xp+DX,yp);
    }
}
