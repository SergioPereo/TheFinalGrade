package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.util.Pantalla;

public abstract class Menu extends Pantalla {

    protected TheFinalGrade game;
    protected Texture background;
    protected Stage menuStage;

    public Menu(TheFinalGrade game){
        this.game = game;
    }

    @Override
    public void show() {
        menuStage = new Stage(new FitViewport(ANCHO, ALTO));
        createMenu();
    }

    // Create buttons here

    protected abstract void createMenu();


    @Override
    public void render(float delta) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        menuStage.dispose();
    }
}
