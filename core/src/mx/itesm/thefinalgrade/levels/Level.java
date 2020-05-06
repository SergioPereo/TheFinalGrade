package mx.itesm.thefinalgrade.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.menus.MainMenu;
import mx.itesm.thefinalgrade.util.Pantalla;
import mx.itesm.thefinalgrade.util.objects.Bonus;
import mx.itesm.thefinalgrade.util.objects.LittleGrass;
import mx.itesm.thefinalgrade.util.objects.Obstacle;
import mx.itesm.thefinalgrade.util.objects.Platform;
import mx.itesm.thefinalgrade.util.objects.Player;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public abstract class Level extends Pantalla {

    protected TheFinalGrade game;
    protected String backgroundPath;

    protected Stage levelStage;
    //Personaje
    protected Player player;
    //Elementos
    protected Array<Obstacle> obstacles;
    protected Array<Platform> platforms;
    protected Array<Bonus> bonus;
    protected Array<LittleGrass> littleGrass;
    private float playerPaddingLeft = 20, playerPaddingBottom = 20;
    //Texturas
    protected Texture background;
    Texture playerTexture;
    Texture hojaBuenaTexture;
    Texture botonBrincar, brincarClicked;


    // HUD (joystick virtual)

    private float movementMultiplicator = 60f;
    private float jumpMultiplicator = 40f;
    private Movement playerMovement = Movement.IDLE;
    private boolean isGrounded = true;

    public Level(TheFinalGrade game, String backgroundPath) {
        this.game = game;
        this.backgroundPath = backgroundPath;
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

    protected void loadTextures(){

        playerTexture = new Texture("Niño/Niño.png");
        botonBrincar = new Texture("Brincar.png");
        brincarClicked = new Texture("BrincarClicked.png");

    }

    public void createHUD() {

        Skin skin = new Skin();
        skin.add("fondo", new Texture("padBack.png"));
        skin.add("boton", new Texture("padKnob.png"));
        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("fondo");
        estilo.knob = skin.getDrawable("boton");
        // Crear el pad
        Touchpad pad = new Touchpad(64, estilo);
        pad.setBounds(16,16,128,128); //limites del pad
        pad.setColor(1,1,1,0.7f);
        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad)actor;
                if(pad.getKnobPercentX()>0){
                    playerMovement = Movement.RIGHT;
                }
                else if(pad.getKnobPercentX()<0){
                    playerMovement = Movement.LEFT;
                }
                else{
                    playerMovement = Movement.IDLE;
                }
            }
        });

        // EVENTOS
        levelStage.addActor(pad);
    }

    protected void createButton(){

        TextureRegionDrawable brincarBoton = new TextureRegionDrawable(botonBrincar);
        TextureRegionDrawable brincarBotonClicked = new TextureRegionDrawable(brincarClicked);
        ImageButton jumpBtn = new ImageButton(brincarBoton, brincarBotonClicked);
        jumpBtn.setPosition(5*ANCHO/6, ALTO/18);


        jumpBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                if(player.isGrounded()){
                    System.out.println("Jumpinggg");
                    player.setYAcceleration(1*jumpMultiplicator);
                    player.setIsGrounded(false);
                }
            }
        });

        levelStage.addActor(jumpBtn);

    }

    protected void createPlayer(){
        player = new Player(playerTexture, playerTexture.getWidth() +
                playerPaddingLeft, playerTexture.getHeight() + playerPaddingBottom);
    }


    protected abstract void createPlatforms();
    protected abstract void createLevel();

    @Override
    public void render(float delta) {

    }

    protected void itemsCollisions(){
        Rectangle playerRect = new Rectangle(player.getTextureX(), player.getTextureY(),
                player.getTextureWidth(), player.getTextureHeight());

        if (playerRect.getY() < 0){
            game.setScreen(new Loser(game, "Failed_Niño.png"));
        }

        for (int i = obstacles.size-1; i >= 0; i--){
            Rectangle obstacleRect = new Rectangle(obstacles.get(i).getTextureX(), obstacles.get(i).getTextureY(), obstacles.get(i).getTextureWidth(), obstacles.get(i).getTextureHeight());

            if (playerRect.overlaps(obstacleRect)){
                UserPreferences.getInstance().setScore(UserPreferences.getInstance().getScore()-10);
                obstacles.removeIndex(i);
                break;
            }
        }

        for (int i = bonus.size-1; i >= 0; i--){
            Rectangle bonusRect = new Rectangle(bonus.get(i).getTextureX(), bonus.get(i).getTextureY(), bonus.get(i).getTextureWidth(), bonus.get(i).getTextureHeight());

            if (playerRect.overlaps(bonusRect)){
                UserPreferences.getInstance().setScore(UserPreferences.getInstance().getScore()+10);
                bonus.removeIndex(i);
                break;
            }
        }
    }

    protected boolean platformsCollision() {
        Rectangle playerRect = new Rectangle(player.getTextureX(), player.getTextureY(),
                player.getTextureWidth(), player.getTextureHeight());

        for (Platform platform : platforms) {
            Rectangle platformRect = new Rectangle(platform.getTextureX(), platform.getTextureY(),
                    platform.getTextureWidth(), platform.getTextureHeight()-48);

            if (playerRect.overlaps(platformRect)) {
                return true;
            }
        }

        return false;
    }

    public void updates(float delta) {
        movePlayer(delta);
        itemsCollisions();
        if (platformsCollision()) {
            player.setIsGrounded(true);
            player.gravity(0);
        } else {
            player.setIsGrounded(false);
            player.gravity(-3);
        }

    }

    //Movimiento
    public enum Movement{
        //Constantes
        RIGHT,
        LEFT,
        IDLE
    }

    protected void movePlayer(float delta) {
        switch (playerMovement) {
            case IDLE:
                break;
            case RIGHT:
                player.move(movementMultiplicator * delta, 0);
                break;
            case LEFT:
                player.move(-movementMultiplicator * delta, 0);
                break;
            default:
                break;
        }
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
        levelStage.dispose();
    }
    protected class CustomInputProcessor implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }
        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 v = new Vector3(screenX, screenY, 0);
            camara.unproject(v);
            if (v.y >= ALTO / 2) {

                player.setYAcceleration(10f);
                System.out.println("JUMP");
            } /*else {
                if (v.x >= ANCHO / 2) {
                    playerMovement = Movement.RIGHT;
                    System.out.println("RIGHT");
                } else {
                    playerMovement = Movement.LEFT;
                    System.out.println("LEFT");
                }
            }*/

            return true;

        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            playerMovement = Movement.IDLE;
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
