package mx.itesm.thefinalgrade.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.util.objects.Bonus;
import mx.itesm.thefinalgrade.util.objects.LittleGrass;
import mx.itesm.thefinalgrade.util.objects.Obstacle;
import mx.itesm.thefinalgrade.util.objects.Platform;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class EveningLevel extends Level {

    protected Texture floorTexture;
    protected Texture sunTexture;
    protected Texture casa1Texture;
    protected Texture casa2Texture;



    public EveningLevel(TheFinalGrade game, String backgroundPath) {
        super(game, backgroundPath);
        this.backgroundPath = backgroundPath;
    }

    protected void loadTextures(){
        floorTexture = new Texture("Mapa 1/Pasto_Base.png");
        sunTexture = new Texture("MAPA 2/Sol.png");
        casa1Texture = new Texture("MAPA 2/Casita 1_Mapa 2.png");
        casa2Texture = new Texture("MAPA 2/Casita 2_Mapa 2.png");

    }

    @Override
    protected void createPlatforms() {

    }

    @Override
    protected void createLevel() {
        levelStage = new Stage(vista);
        loadTextures();

    }

    @Override
    public void show() {
        if (backgroundPath.length() > 0 && backgroundPath != null) {
            background = new Texture(this.backgroundPath);
            createLevel();
        }
        //Gdx.input.setInputProcessor(new CustomInputProcessor());//Para el Touchdown
        Gdx.input.setInputProcessor(levelStage);

    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(floorTexture, 0, 0);
        batch.draw(sunTexture, 0, 250);
        batch.draw(casa1Texture,50,92);
        batch.draw(casa2Texture, 400, 92);
        batch.end();

        levelStage.draw();

    }

    @Override
    public void dispose() {

    }
}
