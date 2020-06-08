package mx.itesm.thefinalgrade.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.menus.StartMenu;
import mx.itesm.thefinalgrade.util.variables.BaseScreen;

public class LoadingScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private Label loading;
    private Texture  background = game.getManager().get("Sprites/backgrounds/skybackground.png");

    public LoadingScreen(TheFinalGrade game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skins/comic/skin/comic-ui.json"));
        loading = new Label("Loading...", skin);
        loading.setPosition(320-loading.getWidth()/2, 180-loading.getHeight() / 2);
        stage.addActor(loading);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getCamera().update();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, ANCHO, ALTO);
        stage.getBatch().end();

        if (game.getManager().update()){
            game.setScreen(new StartMenu(game));
        } else {
            loading.setText("Loading..."+(game.getManager().getProgress()*100)+"%");
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
    }
}
