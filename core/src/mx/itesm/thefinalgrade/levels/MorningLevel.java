package mx.itesm.thefinalgrade.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.menus.MainMenu;
import mx.itesm.thefinalgrade.util.Text;
import mx.itesm.thefinalgrade.util.objects.Bonus;
import mx.itesm.thefinalgrade.util.objects.Obstacle;
import mx.itesm.thefinalgrade.util.objects.Platform;
import mx.itesm.thefinalgrade.util.objects.Player;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class MorningLevel extends Level {
    // Player padding
    private float playerPaddingLeft = 20, playerPaddingBottom = 20;

    Texture platformTexture;
    Texture obstacleTexture;
    Texture obstacle;

    private float movementMultiplicator = 60f;

    private boolean isGrounded = true;

    private Text score = new Text(4);

    public MorningLevel(TheFinalGrade game, String backgroundPath) {
        super(game, backgroundPath);
        this.backgroundPath = backgroundPath;
    }
    protected void loadTextures(){

        platformTexture = new Texture("Plataforma 1.png");
        obstacleTexture = new Texture("HojaRota.png");
        playerTexture = new Texture("Niño/Niño.png");
        hojaBuenaTexture = new Texture("Hoja.png");
        botonBrincar = new Texture("Brincar.png");
        brincarClicked = new Texture("BrincarClicked.png");

    }



    protected void createPlatforms() {
        // Must be the same lengths unless you want the game to crash or do strange platforms
        float[] platformsXCoordinates = {0, ANCHO / 3 - 100, ANCHO / 2, ANCHO / 2 + 300, ANCHO - platformTexture.getWidth()};
        float[] platformsYCoordinates = {0, ALTO / 3 - 100, ALTO / 2, ALTO - 400, ALTO - platformTexture.getHeight() * 2};
        platforms = new Array<>(5);

        for (int i = 0; i < platformsXCoordinates.length; i++) {
            platforms.add(new Platform(platformTexture, platformsXCoordinates[i], platformsYCoordinates[i]));
        }
    }

    private void createObstacles(){
        obstacles = new Array<>();
        obstacles.add(new Obstacle(obstacleTexture, ANCHO/3, ALTO/3));

    }

    private void createBonus(){
        bonus = new Array<Bonus>();
        bonus.add(new Bonus(hojaBuenaTexture, ANCHO/2+300, ALTO-500));
        bonus.add(new Bonus(hojaBuenaTexture,200, 80));
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
    protected void createLevel() {
        levelStage = new Stage(vista);
        loadTextures();
        super.createPlayer();
        createObstacles();
        createPlatforms();
        createBonus();
        createHUD();
        createButton();
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        updates(delta);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background,0,0);
        score.draw(batch, "" + UserPreferences.getInstance().getScore(), 8*ANCHO/9, 20*ALTO/21);
        player.render(batch);

        for(Platform platform: platforms){
            platform.render(batch);
        }
        for (Bonus bonus : bonus){
            bonus.render(batch);
        }
        for(Obstacle obstacle: obstacles){
            obstacle.render(batch);
        }
        batch.end();
        levelStage.draw();
        levelStage.act();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
