package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.util.actors.ObjectAnimation;
import mx.itesm.thefinalgrade.util.actors.ObjectMove;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class StartMenu extends Menu {

    // Declare padding's to avoid using constants in the methods
    private float paddingPlayRight = 20f;

    // Declare textures
    private Texture playTexture, playTextureP, texturewalkNiño, textureCalendario, textureNiño;

    private Music music;
    private ObjectAnimation niñowalk;
    private ObjectMove niñoPortada;
    public StartMenu(TheFinalGrade game)
    {
        super(game);


    }

    @Override
    protected void createMenu() {

        menuStage = new Stage(vista);

        background = game.getManager().get("Sprites/backgrounds/skybackground.png");
        niñowalk = new ObjectAnimation(1280/10*8,720/3*2);

        niñoPortada = new ObjectMove(1280/2,720/2);
        // Play button
        playTexture = game.getManager().get("Sprites/buttons/Start.png");
        TextureRegionDrawable playRegionDrawable = new TextureRegionDrawable(new TextureRegion(playTexture));

        playTextureP = game.getManager().get("Sprites/buttons/StartClick.png");
        TextureRegionDrawable playRegionDrawableP = new TextureRegionDrawable(new TextureRegion(playTextureP));

        textureNiño = game.getManager().get("Sprites/player/boy/boy-start.png");
        textureCalendario = game.getManager().get("Sprites/elements/CalendarioTransparente.png");

        ImageButton playButton = new ImageButton(playRegionDrawable, playRegionDrawableP);

        playButton.setPosition(ANCHO-(3*playButton.getWidth()/2), playButton.getHeight());
        playButton.getImageCell().size(3*playButton.getWidth()/2, 3*playButton.getHeight()/2);

        // Add actors
        menuStage.addActor(playButton);

        // Add listeners
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new MainMenu(game));
            }
        });

        // Add input processor
        Gdx.input.setInputProcessor(menuStage);

        music = game.getManager().get("music/Mushroom Theme.mp3");
        music.setVolume(UserPreferences.getInstance().getVolume());
        music.setLooping(true);
        music.setPosition(UserPreferences.getInstance().getPosition());
        music.play();
    }
    @Override
    public void render(float delta) {
        borrarPantalla();
        //Actualiza objetos
        updateObject();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        niñowalk.render(batch);
        niñoPortada.drawObject(batch);
        //batch.draw(textureNiño, 10, 0);
        batch.draw(textureCalendario, 375, 0);
        batch.end();
        menuStage.draw();
    }
    private void updateObject(){
        niñoPortada.moveX();
    }

    @Override
    public void dispose() {
        // Dispose resources to clean memory
        music.stop();
        UserPreferences.getInstance().setPosition(music.getPosition());
        menuStage.dispose();
    }
}
