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
import mx.itesm.thefinalgrade.levels.MorningLevel;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class OptionsMenu extends Menu{

    private Texture textcalendar;
    private Texture textniño;
    private Texture instructionsTexture, instructionsTexturePress;
    private Texture soundsSettingsTexture, soundsSettingsTexturePress;
    private Texture backButtonTexture, backButtonTexturePressed;
    private float paddingPlayRight = 20f;

    private Music music;

    public OptionsMenu(TheFinalGrade game, String backgroundPath) {
        super(game, backgroundPath);
    }

    @Override
    protected void createMenu() {

        menuStage = new Stage(vista);


        instructionsTexture = new Texture("Sprites/buttons/Credits.png");
        TextureRegionDrawable textureRegionInstructionsButton = new TextureRegionDrawable(new TextureRegion(instructionsTexture));
        instructionsTexturePress = new Texture("Sprites/buttons/Credits.png");

        TextureRegionDrawable textureRegionInstructionsButtonPress = new TextureRegionDrawable(new TextureRegion(instructionsTexturePress));

        ImageButton instructionsButton = new ImageButton(textureRegionInstructionsButton, textureRegionInstructionsButtonPress);
        instructionsButton.setPosition(ANCHO/3, 6*ALTO/7);


        backButtonTexture = new Texture("Sprites/buttons/Credits.png");
        TextureRegionDrawable textureRegionBackButton = new TextureRegionDrawable(new TextureRegion(backButtonTexture));
        backButtonTexturePressed = new Texture("Sprites/buttons/Credits.png");

        TextureRegionDrawable textureRegionBackButtonPressed = new TextureRegionDrawable(new TextureRegion(backButtonTexturePressed));

        ImageButton backButton = new ImageButton(textureRegionBackButton, textureRegionBackButtonPressed);
        backButton.setPosition(ANCHO/12, 8*ALTO/9);

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);

                game.setScreen(new MainMenu(game, "Sprites/backgrounds/Fondo_StartMenu.png"));
            }
        });

        soundsSettingsTexture = new Texture("Sprites/buttons/Credits.png");
        TextureRegionDrawable textureRegionSoundSettings = new TextureRegionDrawable(new TextureRegion(soundsSettingsTexture));
        soundsSettingsTexturePress = new Texture("Sprites/buttons/Credits.png");
        TextureRegionDrawable textureRegionSoundSettingsPress = new TextureRegionDrawable(new TextureRegion(soundsSettingsTexturePress));

        ImageButton soundSettingsButton = new ImageButton(textureRegionSoundSettings, textureRegionSoundSettingsPress);
        soundSettingsButton.setPosition(ANCHO/3, 5*ALTO/7);

        soundSettingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new SoundSettingsMenu(game, "Sprites/backgrounds/Fondo_StartMenu.png"));
            }
        });

        menuStage.addActor(instructionsButton);
        menuStage.addActor(soundSettingsButton);
        menuStage.addActor(backButton);

        //imagen Niño

        textniño = new Texture("Niño/Niño.png");
        //imagen calendario
        textcalendar = new Texture("Sprites/elements/Calendario.png");

        // Add input processor
        Gdx.input.setInputProcessor(menuStage);

        music = Gdx.audio.newMusic(Gdx.files.internal("music/Mushroom Theme.mp3"));
        music.setVolume(UserPreferences.getInstance().getVolume());
        music.setLooping(true);
        music.setPosition(UserPreferences.getInstance().getPosition());
        music.play();

    }

    @Override
    public void render(float delta){
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background,0,0);
        batch.draw(textniño,550,0);
        batch.end();
        menuStage.draw();

    }
    @Override
    public void dispose() {
        background.dispose();
        instructionsTexture.dispose();
        instructionsTexturePress.dispose();
        UserPreferences.getInstance().setPosition(music.getPosition());
        music.dispose();
        textcalendar.dispose();
        textniño.dispose();

        backButtonTexture.dispose();
        backButtonTexturePressed.dispose();
    }
}
