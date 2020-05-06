package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.thefinalgrade.TheFinalGrade;

public class Historia3 extends Menu {

    private Texture botonAvanzar;
    private Texture botonAvanzarP;

    private Texture fondo;

    public Historia3(TheFinalGrade game, String backgroundPath) {
        super(game, backgroundPath);
    }

    @Override
    protected void createMenu() {

        menuStage = new Stage(vista);

        fondo = new Texture("Historia/3_OhNo_1.png");

        botonAvanzar = new Texture("BotonRegresar.png");
        TextureRegionDrawable regresarBoton = new TextureRegionDrawable(botonAvanzar);

        botonAvanzarP = new Texture("BotonRegresar_Click.png");
        TextureRegionDrawable regresarBotonP = new TextureRegionDrawable(botonAvanzarP);

        ImageButton returnButton = new ImageButton(regresarBoton, regresarBotonP);
        returnButton.setPosition(ANCHO - 300, 6 * ALTO / 9);

        menuStage.addActor(returnButton);

        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new Historia4(game, "Fondo_StartMenu.png"));
            }
        });

        Gdx.input.setInputProcessor(menuStage);
    }

    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(fondo, 0, 0);
        batch.end();
        menuStage.draw();
    }
}