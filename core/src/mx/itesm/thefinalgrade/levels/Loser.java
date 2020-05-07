package mx.itesm.thefinalgrade.levels;

import com.badlogic.gdx.Gdx;
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

public class Loser extends Menu {

    private Texture botonRegresar, botonRegresarP;

    public Loser(TheFinalGrade game, String backgroundPath){
        super(game, backgroundPath);
        this.backgroundPath = backgroundPath;

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    protected void createMenu() {

        menuStage = new Stage(vista);

        botonRegresar = new Texture("Sprites/buttons/BotonRegresar.png");
        TextureRegionDrawable regresarBoton = new TextureRegionDrawable(botonRegresar);

        botonRegresarP = new Texture("Sprites/buttons/BotonRegresar_Click.png");
        TextureRegionDrawable regresarBotonP = new TextureRegionDrawable(botonRegresarP);

        ImageButton returnButton = new ImageButton(regresarBoton, regresarBotonP);
        returnButton.setPosition(50, 500);

        menuStage.addActor(returnButton);

       returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new MainMenu(game, "Sprites/backgrounds/Fondo_StartMenu.png"));
            }
        });

        Gdx.input.setInputProcessor(menuStage);

    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background, 0, 0);
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

    }
}
