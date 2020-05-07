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
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class Winner extends Menu {

    private Texture botonRegresar, botonRegresarP;

    private Music music;

    public Winner(TheFinalGrade game){
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
            background = game.getManager().get("Sprites/backgrounds/boyWin.png");
        } else {
            background = game.getManager().get("Sprites/backgrounds/girlWin.png");
        }

        menuStage = new Stage(vista);

        botonRegresar = game.getManager().get("Sprites/buttons/BotonRegresar.png");
        TextureRegionDrawable regresarBoton = new TextureRegionDrawable(botonRegresar);

        botonRegresarP = game.getManager().get("Sprites/buttons/BotonRegresar_Click.png");
        TextureRegionDrawable regresarBotonP = new TextureRegionDrawable(botonRegresarP);

        ImageButton returnButton = new ImageButton(regresarBoton, regresarBotonP);
        returnButton.setPosition(50, 500);

        menuStage.addActor(returnButton);

        returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new MainMenu(game));
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
