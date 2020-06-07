package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;


class CreditsMenu extends Menu {

    private Texture botonRegresar, botonRegresarP;

    private Music music;

    public CreditsMenu(TheFinalGrade game) {
        super(game);
    }

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
        background =  game.getManager().get("Sprites/backgrounds/Us.jpeg");

        menuStage = new Stage(vista);

        botonRegresar = game.getManager().get("Sprites/buttons/BotonRegresar.png");
        TextureRegionDrawable regresarBoton = new TextureRegionDrawable(botonRegresar);

        botonRegresarP = game.getManager().get("Sprites/buttons/BotonRegresar_Click.png");
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

        Gdx.input.setInputProcessor(menuStage);

    }

    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();

        menuStage.draw();
        //Tecla de Back
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new MainMenu(game));
        }
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
        menuStage.dispose();
    }
}