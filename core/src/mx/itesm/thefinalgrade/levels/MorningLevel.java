package mx.itesm.thefinalgrade.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
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
import mx.itesm.thefinalgrade.util.objects.LittleGrass;
import mx.itesm.thefinalgrade.util.objects.Obstacle;
import mx.itesm.thefinalgrade.util.objects.Platform;
import mx.itesm.thefinalgrade.util.objects.Player;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class MorningLevel extends Level {
    // Player padding
    private float playerPaddingLeft = 20, playerPaddingBottom = 20;

    Texture platformTexture;
    Texture platform2Texture;
    Texture obstaclehojaTexture;
    Texture arbolTexture;
    Texture arbol2Texture;
    Texture casa1Texture;
    Texture casa2Texture;
    Texture casa3Texture;
    Texture pasto;
    Texture pastito;
    Texture cloudTexture;
    Texture cafeTexture;
    Texture obstaclehoja2Texture;

    private float movementMultiplicator = 60f;

    private boolean isGrounded = true;

    private Text score = new Text(4);

    private Music music;

    public MorningLevel(TheFinalGrade game, String backgroundPath) {
        super(game, backgroundPath);
        this.backgroundPath = backgroundPath;
    }
    protected void loadTextures(){

        platformTexture = new Texture("Mapa 1/Plataforma 1.png");
        platform2Texture = new Texture("Mapa 1/Plataforma 2.png");
        obstaclehojaTexture = new Texture("items/HojaRota.png");
        playerTexture = new Texture("Niño/Niño.png");
        hojaBuenaTexture = new Texture("items/Hoja.png");
        botonBrincar = new Texture("Sprites/buttons/Brincar.png");
        brincarClicked = new Texture("Sprites/buttons/BrincarClicked.png");
        arbolTexture = new Texture("Mapa 1/Arbolito 1.png");
        arbol2Texture = new Texture("Mapa 1/Arbolito 2.png");
        casa1Texture = new Texture("Mapa 1/Casita.png");
        casa2Texture = new Texture("Mapa 1/Casita 2.png");
        casa3Texture = new Texture("Mapa 1/Casita 3.png");
        pasto = new Texture("Mapa 1/Pasto_Base.png");
        pastito = new Texture("Mapa 1/Pastito.png");
        cloudTexture = new Texture("Mapa 1/Nube.png");
        cafeTexture = new Texture("items/tazacafe.png");
        obstaclehoja2Texture = new Texture("items/HojaArrugada.png");
    }



    protected void createPlatforms() {
        // Must be the same lengths unless you want the game to crash or do strange platforms
        float[] platformsXCoordinates = {0, ANCHO / 3 - 100, ANCHO / 2 - 100, ANCHO / 2 + 200, ANCHO - platformTexture.getWidth()-100};
        float[] platformsYCoordinates = {92, 200, ALTO / 2, ALTO - 450, ALTO - platformTexture.getHeight() * 2};
        platforms = new Array<>(5);

        for (int i = 0; i < platformsXCoordinates.length; i++) {
            if (i % 2 == 0){
                platforms.add(new Platform(platform2Texture, platformsXCoordinates[i], platformsYCoordinates[i]));

            } else {
                platforms.add(new Platform(platformTexture, platformsXCoordinates[i], platformsYCoordinates[i]));
            }

        }
    }

    private void createObstacles(){
        obstacles = new Array<>();
        obstacles.add(new Obstacle(obstaclehojaTexture, ANCHO/3, ALTO/3+25));
        obstacles.add(new Obstacle(obstaclehoja2Texture,ANCHO / 2 + 320,ALTO/2+120));

    }

    private void createLittleGrass(){
        float y = 92;
        float x = 0;
        littleGrass = new Array<>();
        for (int i = 0; i < 10; i++){
            littleGrass.add(new LittleGrass(pastito, x, y));
            x += 120;
        }

    }

    private void createBonus(){
        bonus = new Array<>();
        bonus.add(new Bonus(hojaBuenaTexture, ANCHO / 2 + 260, ALTO - 370));
        bonus.add(new Bonus(cafeTexture, ANCHO/2,ALTO/2+25));
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
        music = Gdx.audio.newMusic(Gdx.files.internal("music/Mushroom Theme.mp3"));
        music.setVolume(UserPreferences.getInstance().getVolume());
        music.setLooping(true);
        music.setPosition(UserPreferences.getInstance().getPosition());
        music.play();
        levelStage = new Stage(vista);
        loadTextures();
        super.createPlayer();
        createObstacles();
        createPlatforms();
        createBonus();
        createHUD();
        createButton();
        createLittleGrass();
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        updates(delta);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        batch.draw(background,0,0);
        batch.draw(pasto, 0, 0);
        batch.draw(casa1Texture, 50, 92);
        batch.draw(casa2Texture, 420, 92);
        batch.draw(casa3Texture, 800, 92);
        batch.draw(cloudTexture, 0, 520);
        batch.draw(cloudTexture, 400, 520);
        batch.draw(cloudTexture, 800, 520);
        batch.draw(arbolTexture, 350, 92);
        batch.draw(arbol2Texture, 750, 92);
        score.draw(batch, "" + UserPreferences.getInstance().getScore(), 8*ANCHO/9, 20*ALTO/21);

        for (LittleGrass pasto : littleGrass){
            pasto.render(batch);
        }
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
