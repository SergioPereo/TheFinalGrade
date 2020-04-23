package mx.itesm.thefinalgrade.util;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text {

    private BitmapFont font;

    public Text(float scale){
        font = new BitmapFont();
        font.setColor(0,0 ,0 ,1);
        font.getData().setScale(scale);
    }

    public void draw(SpriteBatch batch, String text, float x, float y){
        font.draw(batch, text, x, y);
    }

}
