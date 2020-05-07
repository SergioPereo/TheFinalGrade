package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.menus.Menu;

public class Historia1 extends Menu {

    private Texture botonAvanzar, botonAvanzarP;
    Texture backButtonTexture,backButtonTexturePressed;

    private Texture fondo;

    public Historia1(TheFinalGrade game, String backgroundPath) {
        super(game, backgroundPath);
    }

    @Override
    protected void createMenu() {

        menuStage = new Stage(vista);

        fondo = new Texture("Historia/1_Cuarto_1.png");

        //Boton de adelante funcionalidad
        botonAvanzar = new Texture("Sprites/buttons/BotonAdelante.png");
        TextureRegionDrawable adelanteBoton = new TextureRegionDrawable(botonAvanzar);

        botonAvanzarP = new Texture("Sprites/buttons/BotonAdelante_Click.png");
        TextureRegionDrawable adelanteBotonP = new TextureRegionDrawable(botonAvanzarP);

        ImageButton goButton = new ImageButton(adelanteBoton, adelanteBotonP);
        goButton.setPosition(ANCHO - 350, 6*ALTO/9);

        menuStage.addActor(goButton);

        goButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new Historia2(game, "Sprites/backgrounds/Fondo_StartMenu.png"));
            }
        });

        //Boton de atrás funcionalidad
        backButtonTexture = new Texture("Sprites/buttons/BotonRegresar.png");
        TextureRegionDrawable textureRegionBackButton = new TextureRegionDrawable(new TextureRegion(backButtonTexture));
        backButtonTexturePressed = new Texture("Sprites/buttons/BotonRegresar_Click.png");
        TextureRegionDrawable textureRegionBackButtonPressed = new TextureRegionDrawable(new TextureRegion(backButtonTexturePressed));

        ImageButton backButton = new ImageButton(textureRegionBackButton, textureRegionBackButtonPressed);
        backButton.setPosition(ANCHO/100, 6*ALTO/9);

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MainMenu(game, "Sprites/backgrounds/Fondo_StartMenu.png"));
            }
        });
        menuStage.addActor(backButton);

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