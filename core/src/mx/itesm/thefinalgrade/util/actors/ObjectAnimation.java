package mx.itesm.thefinalgrade.util.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ObjectAnimation {
    //Animación

    public int x, y;
    private Animation animation;
    private float time;
    private Texture texturewalkNiño;
    private TextureRegion[] regionsMovement;
    private TextureRegion actualframe;

    public ObjectAnimation(int x, int y)
    {
        this.x = x;
        this.y = y;
        //sprite = new Sprite(new Texture(Gdx.files.internal("Sprites/player/boy/boy-start.png")),512,128);
        //cargar la img
        texturewalkNiño = new Texture(Gdx.files.internal("Sprites/player/boy/animations/walk/Secuencia_Niño2.png"));
        TextureRegion[][] tmp = TextureRegion.split(texturewalkNiño,
                texturewalkNiño.getWidth()/6,texturewalkNiño.getHeight());
        regionsMovement = new TextureRegion[6];
        for(int i=0; i<6;i++){
            regionsMovement[i] = tmp[0][i];
            animation = new Animation(1/10f,regionsMovement);
            time = 0f;
        }


    }
    public void render(final SpriteBatch batch){
        time += Gdx.graphics.getDeltaTime();//tiempo que paso del último render
        actualframe = (TextureRegion) animation.getKeyFrame(time,true);


        batch.draw(actualframe,x,y);
    }
}
