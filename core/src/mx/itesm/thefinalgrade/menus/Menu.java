package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.util.Pantalla;

public abstract class Menu extends Pantalla {

    protected TheFinalGrade game;
    protected String backgroundPath;
    protected Texture background;
    protected Stage menuStage;

    public Menu(TheFinalGrade game, String backgroundPath){
        this.game = game;
        this.backgroundPath = backgroundPath;
    }

    @Override
    public void show() {
        menuStage = new Stage();
        if(backgroundPath.length() > 0 && backgroundPath != null){
            background = new Texture(this.backgroundPath);
            createMenu();
        }
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
        background.dispose();
        menuStage.dispose();
    }
}
