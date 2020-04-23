package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class StartMenu extends Menu {

    // Declare padding's to avoid using constants in the methods
    private float paddingPlayRight = 20f;

    // Declare textures
    private Texture playTexture, playTextureP, textureNiño, textureCalendario;

    private Music music;

    public StartMenu(TheFinalGrade game, String backgroundPath) {
        super(game, backgroundPath);
    }

    @Override
    protected void createMenu() {

        menuStage = new Stage(vista);

        // Play button
        playTexture = new Texture("Start.png");
        TextureRegionDrawable playRegionDrawable = new TextureRegionDrawable(new TextureRegion(playTexture));

        playTextureP = new Texture("StartClick.png");
        TextureRegionDrawable playRegionDrawableP = new TextureRegionDrawable(new TextureRegion(playTextureP));

        textureNiño = new Texture("Niño/niño(1).png");
        textureCalendario = new Texture("CalendarioTransparente.png");

        ImageButton playButton = new ImageButton(playRegionDrawable, playRegionDrawableP);

        playButton.setPosition(ANCHO-playButton.getWidth()-paddingPlayRight, playButton.getHeight());

        // Add actors
        menuStage.addActor(playButton);

        // Add listeners
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new MainMenu(game, "Fondo_StartMenu.png"));
            }
        });

        // Add input processor
        Gdx.input.setInputProcessor(menuStage);

        music = Gdx.audio.newMusic(Gdx.files.internal("music/Mushroom Theme.mp3"));
        music.setVolume(UserPreferences.getInstance().getVolume());
        music.setLooping(true);
        music.setPosition(UserPreferences.getInstance().getPosition());
        music.play();
    }
    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(textureNiño, 10, 0);
        batch.draw(textureCalendario, 375, 0);
        batch.end();


        menuStage.draw();
    }

    @Override
    public void dispose() {
        // Dispose resources to clean memory
        UserPreferences.getInstance().setPosition(music.getPosition());
        music.dispose();
        background.dispose();
        playTexture.dispose();
        playTextureP.dispose();
        menuStage.dispose();
    }
}
