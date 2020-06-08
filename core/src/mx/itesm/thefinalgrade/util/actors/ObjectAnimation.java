package mx.itesm.thefinalgrade.util.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ObjectAnimation {
    //Animación

    public int x, y;
    private Animation<TextureRegion> animation;
    private float time;
    private Texture texturewalkNiño;
    private Texture textureStandNiño;
    private TextureRegion[] regionsMovement;
    private TextureRegion actualframe;

    public ObjectAnimation(int x, int y)
    {
        this.x = x;
        this.y = y;
        //sprite = new Sprite(new Texture(Gdx.files.internal("Sprites/player/boy/boy-start.png")),512,128);
        //cargar la img
        texturewalkNiño = new Texture(Gdx.files.internal("Sprites/player/girl/animations/walk/SecuenciaSalto_Niña2.png"));
        TextureRegion[][] tmp = TextureRegion.split(texturewalkNiño,
                texturewalkNiño.getWidth()/12,texturewalkNiño.getHeight());
        regionsMovement = new TextureRegion[12];
        for(int i=0; i<12;i++){
            regionsMovement[i] = tmp[0][i];
            animation = new Animation<>(1/10f,regionsMovement);
            time = 0f;
        }

        textureStandNiño = new Texture(Gdx.files.internal("Sprites/player/girl/jump/SecuenciaSalto_Niña.png"));
        TextureRegion[][] tms = TextureRegion.split(textureStandNiño,
                textureStandNiño.getWidth()/12,textureStandNiño.getHeight());
        regionsMovement = new TextureRegion[12];
        for(int i=0; i<12;i++){
            regionsMovement[i] = tms[0][i];
            animation = new Animation<>(1/10f,regionsMovement);
            time = 0f;
        }
    }
    public void render(final SpriteBatch batch){
        time += Gdx.graphics.getDeltaTime();//tiempo que paso del último render
        actualframe = animation.getKeyFrame(time,true);

        batch.draw(actualframe,x,y);
    }
}
