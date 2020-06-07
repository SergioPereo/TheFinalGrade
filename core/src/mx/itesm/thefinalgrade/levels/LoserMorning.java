package mx.itesm.thefinalgrade.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.menus.MainMenu;
import mx.itesm.thefinalgrade.menus.Menu;
import mx.itesm.thefinalgrade.util.Pantalla;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;
import mx.itesm.thefinalgrade.worlds.Morning;

public class LoserMorning extends Menu {

    private Texture botonRegresar, botonRegresarP, botonContinuar, botonContinuarP;

    private Music music;

    public LoserMorning(TheFinalGrade game){
        super(game);

    }

    @Override
    public void show() {
        super.show();
        music = game.getManager().get("music/Mushroom Theme.mp3");
        music.setVolume(UserPreferences.getInstance().getVolume());
        music.setLooping(true);
        music.setPosition(UserPreferences.getInstance().getPosition());
        music.play();
    }

    @Override
    protected void createMenu() {

        if(UserPreferences.getInstance().getGender()){
            background = game.getManager().get("Sprites/backgrounds/Failed_Niño.png");
        } else {
            background = game.getManager().get("Sprites/backgrounds/Failed_Niña.png");
        }

        menuStage = new Stage(vista);
        //Boton de regreso
        botonRegresar = game.getManager().get("Sprites/buttons/BotonRegresar.png");
        TextureRegionDrawable regresarBoton = new TextureRegionDrawable(botonRegresar);

        botonRegresarP =game.getManager().get("Sprites/buttons/BotonRegresar_Click.png");
        TextureRegionDrawable regresarBotonP = new TextureRegionDrawable(botonRegresarP);
        ImageButton returnButton = new ImageButton(regresarBoton, regresarBotonP);
        returnButton.setPosition(ANCHO/100, 6*ALTO/9);
        menuStage.addActor(returnButton);
        returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new MainMenu(game));
            }
        });
        //Boton de continuar
        botonContinuar = game.getManager().get("Sprites/buttons/Continue.png");
        TextureRegionDrawable continuarBoton = new TextureRegionDrawable(botonContinuar);
        botonContinuarP = game.getManager().get("Sprites/buttons/Continue_Click.png");
        TextureRegionDrawable continuarBotonP = new TextureRegionDrawable(botonContinuarP);
        ImageButton continueButton = new ImageButton(continuarBoton, continuarBotonP);
        continueButton.setPosition(ANCHO/2+250, 100);
        menuStage.addActor(continueButton);
       continueButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               super.clicked(event, x, y);
               game.setScreen(new Morning(game));
               UserPreferences.getInstance().setScore(0);
           }
       });
        Gdx.input.setInputProcessor(menuStage);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background, 0, 0, ANCHO, ALTO);
        batch.end();

        menuStage.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        music.stop();
        UserPreferences.getInstance().setPosition(music.getPosition());
    }
}
