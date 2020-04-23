package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.util.Text;

class CreditsMenu extends Menu {

    private Texture botonRegresar, botonRegresarP;

    private Text text = new Text(2);

    public CreditsMenu(TheFinalGrade game, String backgroundPath) {
            super(game, backgroundPath);
    }

    public void show() {
        super.show();
    }

    @Override
    protected void createMenu() {

        menuStage = new Stage(vista);

        botonRegresar = new Texture("BotonRegresar.png");
        TextureRegionDrawable regresarBoton = new TextureRegionDrawable(botonRegresar);

        botonRegresarP = new Texture("BotonRegresar_Click.png");
        TextureRegionDrawable regresarBotonP = new TextureRegionDrawable(botonRegresarP);

        ImageButton returnButton = new ImageButton(regresarBoton, regresarBotonP);
        returnButton.setPosition(ANCHO/100, 6*ALTO/9);

        menuStage.addActor(returnButton);

        returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new MainMenu(game, "Fondo_StartMenu.png"));
            }
        });

        Gdx.input.setInputProcessor(menuStage);

    }

    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        text.draw(batch, "La historía va aquí", ANCHO/2, 3*ALTO/9);
        text.draw(batch, "La historía va aquí", ANCHO/2, 4*ALTO/9);
        text.draw(batch, "La historía va aquí", ANCHO/2, 5*ALTO/9);
        text.draw(batch, "La historía va aquí", ANCHO/2, 6*ALTO/9);
        text.draw(batch, "La historía va aquí", ANCHO/2, 7*ALTO/9);
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
        menuStage.dispose();
        botonRegresar.dispose();
        botonRegresarP.dispose();
    }
}
