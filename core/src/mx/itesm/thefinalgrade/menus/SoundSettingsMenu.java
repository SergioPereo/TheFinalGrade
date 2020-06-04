package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class SoundSettingsMenu extends Menu{

    private Texture textureNiño, textureCalendario;
    private Texture backButtonTexture, backButtonTexturePressed;

    private CheckBox checkBox;
    private Skin skin;
    private Slider slider;
    private Music music;

    public SoundSettingsMenu(TheFinalGrade game) {
        super(game);
    }

    public void show() {
        super.show();
    }

    @Override
    protected void createMenu() {

        background = game.getManager().get("Sprites/backgrounds/skybackground.png");

        menuStage = new Stage(vista);

        textureNiño = game.getManager().get("Sprites/elements/niño.png");
        textureCalendario = game.getManager().get("Sprites/elements/Calendario.png");

        backButtonTexture = game.getManager().get("Sprites/buttons/BotonRegresar.png");
        TextureRegionDrawable textureRegionBackButton = new TextureRegionDrawable(new TextureRegion(backButtonTexture));
        backButtonTexturePressed = game.getManager().get("Sprites/buttons/BotonRegresar_Click.png");
        TextureRegionDrawable textureRegionBackButtonPressed = new TextureRegionDrawable(new TextureRegion(backButtonTexturePressed));

        ImageButton backButton = new ImageButton(textureRegionBackButton, textureRegionBackButtonPressed);
        backButton.setPosition(ANCHO/100, 6*ALTO/9);

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MainMenu(game));
            }
        });

        skin = new Skin(Gdx.files.internal("skins/glassy/skin/glassy-ui.json"));

        checkBox = new CheckBox("Audio", skin);

        checkBox.setChecked(true);

        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if(!checkBox.isChecked()){
                    UserPreferences.getInstance().setPosition(music.getPosition());
                    music.stop();
                } else {
                    music.setPosition(UserPreferences.getInstance().getPosition());
                    music.setVolume(UserPreferences.getInstance().getVolume());
                    music.setLooping(true);
                    music.play();
                }
            }
        });

        checkBox.setPosition(4*ANCHO/5, 5*ALTO/7);
        checkBox.getImage().scaleBy(0.7f);
        checkBox.getImageCell().width(80f);
        checkBox.getImage().setWidth(checkBox.getImage().getImageWidth() + 20f);
        checkBox.getLabel().setFontScale(2.5f, 2.5f);

        slider = new Slider(0, 1, 0.1f, false, skin);
        slider.setValue(UserPreferences.getInstance().getVolume());

        slider.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                UserPreferences.getInstance().setVolume(slider.getValue());
                music.setVolume(slider.getValue());
            }
        });

        slider.setPosition(4*ANCHO/5, 4*ALTO/7);

        menuStage.addActor(checkBox);
        menuStage.addActor(slider);
        menuStage.addActor(backButton);

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
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(textureNiño, 10, 0);
        batch.draw(textureCalendario, 375, 0);
        batch.end();
        menuStage.draw();
        //Tecla de Back
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new MainMenu(game));
        }
    }

    @Override
    public void dispose() {
        // Dispose resources to clean memory
        music.stop();
        UserPreferences.getInstance().setPosition(music.getPosition());
    }

}
