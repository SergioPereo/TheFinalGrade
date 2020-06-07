package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class CharacterChooser extends Menu {

    private Texture botonAvanzar, botonAvanzarP;

    private Texture backButtonTexture,backButtonTexturePressed;

    private Music music;

    private ImageButton boyButton, girlButton;

    private boolean isBoySelected;

    public CharacterChooser(TheFinalGrade game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    protected void createMenu() {
        music = game.getManager().get("music/Mushroom Theme.mp3");
        music.setVolume(UserPreferences.getInstance().getVolume());
        music.setLooping(true);
        music.setPosition(UserPreferences.getInstance().getPosition());
        music.play();

        background = game.getManager().get("Sprites/backgrounds/choose_character.png");

        //Boton de adelante funcionalidad
        botonAvanzar = game.getManager().get("Sprites/buttons/BotonAdelante.png");
        TextureRegionDrawable adelanteBoton = new TextureRegionDrawable(botonAvanzar);

        botonAvanzarP = game.getManager().get("Sprites/buttons/BotonAdelante_Click.png");
        TextureRegionDrawable adelanteBotonP = new TextureRegionDrawable(botonAvanzarP);

        ImageButton goButton = new ImageButton(adelanteBoton, adelanteBotonP);
        goButton.setPosition(ANCHO - 350, 6*ALTO/9);


        goButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new Historia1(game));
            }
        });

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
        menuStage.addActor(backButton);

        Gdx.input.setInputProcessor(menuStage);

        Texture boyButtonTexture = game.getManager().get("Sprites/backgrounds/boy_bw.png");
        TextureRegionDrawable textureRegionBoy = new TextureRegionDrawable(new TextureRegion(boyButtonTexture));
        Texture boyButtonTexturePressed = game.getManager().get("Sprites/backgrounds/boy_color.png");
        TextureRegionDrawable textureRegionBoyPressed = new TextureRegionDrawable(new TextureRegion(boyButtonTexturePressed));

        boyButton = new ImageButton(textureRegionBoy, textureRegionBoyPressed);
        boyButton.getImageCell().size(boyButton.getWidth()/2, boyButton.getHeight()/2);
        boyButton.setPosition(4, 5);
        boyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                UserPreferences.getInstance().setGender(true);
                game.setScreen(new Historia1(game));
            }
        });

        Texture girlButtonTexture = game.getManager().get("Sprites/backgrounds/girl_bw.png");
        TextureRegionDrawable textureRegionGirl = new TextureRegionDrawable(new TextureRegion(girlButtonTexture));
        Texture girlButtonTexturePressed = game.getManager().get("Sprites/backgrounds/girl_color.png");
        TextureRegionDrawable textureRegionGirlPressed = new TextureRegionDrawable(new TextureRegion(girlButtonTexturePressed));

        girlButton = new ImageButton(textureRegionGirl, textureRegionGirlPressed);
        girlButton.getImageCell().size(girlButton.getWidth()/2, girlButton.getHeight()/2);
        girlButton.setPosition(3*ANCHO/5, 5);

        girlButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                UserPreferences.getInstance().setGender(false);
                game.setScreen(new Historia1(game));
            }
        });


        menuStage.addActor(boyButton);

        menuStage.addActor(girlButton);

        Gdx.input.setInputProcessor(menuStage);

    }


    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background, 0, 0, ANCHO, ALTO);
        batch.end();
        menuStage.act();
        menuStage.draw();
        //Tecla de Back
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new MainMenu(game));
        }
    }

    @Override
    public void dispose() {
        music.stop();
        UserPreferences.getInstance().setPosition(music.getPosition());
        menuStage.dispose();
    }


}
