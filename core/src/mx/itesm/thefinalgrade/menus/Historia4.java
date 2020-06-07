package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import mx.itesm.thefinalgrade.worlds.Morning;

public class Historia4 extends Menu {

    private Texture botonAvanzar, botonAvanzarP;

    Texture backButtonTexture,backButtonTexturePressed;

    private Music music;

    public Historia4(TheFinalGrade game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    protected void createMenu() {

        menuStage = new Stage(vista);

        if(UserPreferences.getInstance().getGender()){
            background = game.getManager().get("Sprites/history/boy/4.png");
        } else {
            background = game.getManager().get("Sprites/history/girl/4.png");
        }

        //Boton de adelante funcionalidad
        botonAvanzar = game.getManager().get("Sprites/buttons/BotonAdelante.png");
        TextureRegionDrawable adelanteBoton = new TextureRegionDrawable(botonAvanzar);

        botonAvanzarP = game.getManager().get("Sprites/buttons/BotonAdelante_Click.png");
        TextureRegionDrawable adelanteBotonP = new TextureRegionDrawable(botonAvanzarP);

        ImageButton goButton = new ImageButton(adelanteBoton, adelanteBotonP);
        goButton.setPosition(ANCHO - 350, 6*ALTO/9);

        menuStage.addActor(goButton);

        goButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new Morning(game));
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
                game.setScreen(new Historia3(game));
            }
        });
        menuStage.addActor(backButton);

        Gdx.input.setInputProcessor(menuStage);

        music = game.getManager().get("music/Mushroom Theme.mp3");
        music.setVolume(UserPreferences.getInstance().getVolume());
        music.setLooping(true);
        music.setPosition(UserPreferences.getInstance().getPosition());
        music.play();

    }

    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
        menuStage.draw();
        //Tecla de Back
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new Historia3(game));
        }
    }

    @Override
    public void dispose() {
        music.stop();
        UserPreferences.getInstance().setPosition(music.getPosition());
        menuStage.dispose();
    }
}
