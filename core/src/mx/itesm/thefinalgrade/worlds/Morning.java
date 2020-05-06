package mx.itesm.thefinalgrade.worlds;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import javax.swing.Box;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.levels.Level;
import mx.itesm.thefinalgrade.util.actors.PlatformActor;
import mx.itesm.thefinalgrade.util.actors.PlayerActor;
import mx.itesm.thefinalgrade.util.variables.BaseScreen;

public class Morning extends BaseScreen {

    private Stage stage;

    private World world;

    private PlayerActor player;

    private PlatformActor platform;
    
    private Texture background;

    private Box2DDebugRenderer debugRenderer;

    private OrthographicCamera debugCamera;

    public Morning(TheFinalGrade game) {
        super(game);

    }

    @Override
    public void show() {
        createLevel();
        Gdx.input.setInputProcessor(stage);
    }

    public void createLevel(){
        stage = new Stage(new FitViewport(ANCHO, ALTO));
        stage.setDebugAll(true);


        debugRenderer = new Box2DDebugRenderer();
        debugCamera = new OrthographicCamera(64, 36);
        debugCamera.translate(0, 1);

        world = new World(new Vector2(0, -10), true);

        world.setContactListener(new ContactListener() {

            private boolean areCollided(Contact contact, Object userA, Object userB){
                return (contact.getFixtureA().getUserData().equals(userA)
                        && contact.getFixtureB().getUserData().equals(userB))
                        || (contact.getFixtureA().getUserData().equals(userB)
                        && contact.getFixtureB().getUserData().equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                if(areCollided(contact, "player", "platform")){
                    player.setJumping(false);
                }
                if(areCollided(contact, "player", "item")){
                    player.setJumping(false);
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        createBackground();
        createPlayer();
        createPlatforms();
        createHUD();
        createButton();
    }

    protected void createButton(){

        TextureRegionDrawable brincarBoton = new TextureRegionDrawable((Texture) game.getManager().get("Sprites/buttons/Brincar.png"));
        TextureRegionDrawable brincarBotonClicked = new TextureRegionDrawable((Texture) game.getManager().get("Sprites/buttons/BrincarClicked.png"));
        ImageButton jumpBtn = new ImageButton(brincarBoton, brincarBotonClicked);
        jumpBtn.setPosition(5*ANCHO/6, ALTO/18);


        jumpBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                player.jump();
            }
        });

        stage.addActor(jumpBtn);

    }

    public void createHUD() {

        Skin skin = new Skin();
        skin.add("background", game.getManager().get("Sprites/buttons/padBack.png"));
        skin.add("button", game.getManager().get("Sprites/buttons/padKnob.png"));
        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("background");
        estilo.knob = skin.getDrawable("button");
        // Crear el pad
        Touchpad pad = new Touchpad(64, estilo);
        pad.setBounds(16,16,128,128); //limites del pad
        pad.setColor(1,1,1,0.7f);
        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad)actor;
                player.move(pad.getKnobPercentX());
            }
        });

        // EVENTOS
        stage.addActor(pad);
    }

    private void createBackground() {
        background = game.getManager().get("Sprites/backgrounds/skybackground.png");
    }

    public void createPlayer(){
        Texture playerTexture = game.getManager().get("Sprites/player/nino-r-r.png");
        Texture[] frames = new Texture[6];
        for(int i = 0 ; i < 6 ; i++){
            frames[i] = game.getManager().get("Sprites/player/boy/animations/walk/" + (i+1) + ".png");
        }
        Animation<Texture> walkAnimation = new Animation<Texture>(1f/6f, frames);
        player = new PlayerActor(world, playerTexture, walkAnimation, new Vector2(4, 2));
        stage.addActor(player);
    }

    public void createPlatforms(){
        Texture platformTexture = game.getManager().get("Sprites/platforms/platform2resized.png");
        TextureRegion platformRegion = new TextureRegion(platformTexture, 145, 21, 667, 450);
        platform = new PlatformActor(world, platformRegion, new Vector2(4, 1));
        stage.addActor(platform);
    }

    @Override
    public void hide() {
        player.detach();
        player.remove();
        platform.detach();
        platform.remove();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        world.step(delta, 6, 2);
        stage.draw();
        debugCamera.update();
        debugRenderer.render(world, debugCamera.combined);

    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
