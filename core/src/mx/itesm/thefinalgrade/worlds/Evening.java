package mx.itesm.thefinalgrade.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.levels.LoserEvening;
import mx.itesm.thefinalgrade.menus.MainMenu;
import mx.itesm.thefinalgrade.util.Text;
import mx.itesm.thefinalgrade.util.actors.ItemActor;
import mx.itesm.thefinalgrade.util.actors.NormalPlatformActor;
import mx.itesm.thefinalgrade.util.actors.PlayerActor;
import mx.itesm.thefinalgrade.util.actors.PropsActor;
import mx.itesm.thefinalgrade.util.actors.WinActor;
import mx.itesm.thefinalgrade.util.variables.BaseScreen;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class Evening extends BaseScreen {

    private Stage stage;

    private World world;

    private PlayerActor player;

    private Array<NormalPlatformActor> polePlatforms;

    private Array<NormalPlatformActor> normalPlatformsDown;

    private Array<NormalPlatformActor> normalPlatformsUp;

    private Array<ItemActor> items;

    private Array<Body> bodiesToBeDestroyed;

    private Array<PropsActor> props;

    private WinActor winActor;

    private boolean win = false;

    private Texture background, tree1,
            tree2, house1, house2, house3, floor, sun;

    private Box2DDebugRenderer debugRenderer;

    private OrthographicCamera debugCamera;

    private Window pause;

    private Text score = new Text(4);

    private boolean isPause = false;

    Skin skin;

    private Music music;



    public Evening(TheFinalGrade game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(ANCHO, ALTO));
        normalPlatformsUp = new Array<NormalPlatformActor>();
        normalPlatformsDown = new Array<NormalPlatformActor>();
        polePlatforms = new Array<NormalPlatformActor>();
        items = new Array<ItemActor>();
        bodiesToBeDestroyed = new Array<Body>(12);
        props = new Array<PropsActor>(20);
        createLevel();
        createPause();
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getCamera().viewportWidth = ANCHO;
        stage.getCamera().viewportHeight = ALTO;
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2, stage.getCamera().viewportHeight / 2, 0);
        stage.getCamera().update();
    }

    private void createPause() {
        skin = new Skin(Gdx.files.internal("skins/comic/skin/comic-ui.json"));
        TextButton pauseButton = new TextButton("Pause", skin);
        TextButton continuePauseButton = new TextButton("Continue", skin);
        TextButton exitPauseButton = new TextButton("Exit", skin);
        pauseButton.setPosition(0, ALTO-pauseButton.getHeight());
        pauseButton.setScale(12);
        pause = new Window("PAUSE", skin);
        pause.add(continuePauseButton).padBottom(50).row();
        pause.add(exitPauseButton);
        pause.padTop(64);
        pause.setSize(ANCHO/1.5f, ALTO/1.5f);
        pause.setPosition(stage.getWidth()/2 - pause.getWidth()/2, stage.getHeight()/2  - pause.getHeight()/2);
        continuePauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!isPause){
                    isPause = true;
                    stage.addActor(pause);
                } else {
                    isPause = false;
                    pause.remove();
                }
            }
        });
        exitPauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!isPause){
                    isPause = true;
                    stage.addActor(pause);
                } else {
                    isPause = false;
                    pause.remove();
                }
            }
        });
        stage.addActor(pauseButton);
    }

    public void createLevel(){

        music = game.getManager().get("music/Mushroom Theme.mp3");
        music.setVolume(UserPreferences.getInstance().getVolume());
        music.setLooping(true);
        music.setPosition(UserPreferences.getInstance().getPosition());
        music.play();

        /**debugRenderer = new Box2DDebugRenderer();
         debugCamera = new OrthographicCamera(64, 36);
         debugCamera.translate(0, 1);*/

        world = new World(new Vector2(0, -8), true);

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
                    for(int i = 0 ; i < items.size ; i++){
                        if(contact.getFixtureA().equals(items.get(i).getFixture())
                                || contact.getFixtureB().equals(items.get(i).getFixture())){
                            switch (items.get(i).getType()){
                                case SHEET:
                                    UserPreferences.getInstance().setScore(UserPreferences.getInstance().getScore() + 10);
                                    break;
                                case WRINKLED_SHEET:
                                    UserPreferences.getInstance().setScore(UserPreferences.getInstance().getScore() - 10);
                                    break;
                                case BROKEN_SHEET:
                                    UserPreferences.getInstance().setScore(UserPreferences.getInstance().getScore() - 10);
                                    break;
                                case COFFEE:
                                    UserPreferences.getInstance().setScore(UserPreferences.getInstance().getScore() + 15);
                                    break;
                            }
                            bodiesToBeDestroyed.add(items.get(i).getBody());
                            items.get(i).remove();
                            items.removeIndex(i);
                        }
                    }
                }
                if(areCollided(contact, "player", "win")){
                    win = true;
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

        winActor = new WinActor(world, new Vector2(13.5f, 6.4f));
        stage.addActor(winActor);
        loadTextures();
        createPlayer();
        createPolePlatforms();
        createNormalPlatforms();
        createItems();
        createHUD();
        createButton();
        moverPlataformas();
        moverItems();
    }

    private void loadTextures() {

        background = game.getManager().get("Sprites/backgrounds/FondoCieloTarde.png");
        tree1 = game.getManager().get("Sprites/morning/Arbolito 1.png");
        tree2 = game.getManager().get("Sprites/morning/Arbolito 2.png");
        house1 = game.getManager().get("Sprites/evening/Casita 1_Mapa 2.png");
        house2 = game.getManager().get("Sprites/evening/Casita 2_Mapa 2.png");
        house3 = game.getManager().get("Sprites/evening/CDT.png");
        floor = game.getManager().get("Sprites/evening/Piso.png");
        sun = game.getManager().get("Sprites/evening/Sol.png");

    }


    protected void createButton(){

        TextureRegionDrawable brincarBoton = new TextureRegionDrawable((Texture) game.getManager().get("Sprites/buttons/Boton_Saltar3.png"));
        ImageButton jumpBtn = new ImageButton(brincarBoton);
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
        Touchpad pad = new Touchpad(0, estilo);
        pad.setBounds(50,40,128,128); //limites del pad
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

    public void createPlayer(){
        Texture playerBoyTexture = game.getManager().get("Sprites/player/boy/nino-r-r.png");
        Texture playerGirlTexture = game.getManager().get("Sprites/player/girl/nina-r-r.png");
        Texture[] framesBoy = new Texture[6];
        Texture[] framesGirl = new Texture[6];
        Texture[] framesBoyStand = new Texture[12];
        Texture[] framesGirlStand = new Texture[12];
        Texture[] framesBoyJump = new Texture[8];
        Texture[] framesGirlJump = new Texture[8];


        //Walk
        for(int i = 0 ; i < 6 ; i++){
            framesBoy[i] = game.getManager().get("Sprites/player/boy/animations/walk/" + (i+1) + ".png");
            framesGirl[i] = game.getManager().get("Sprites/player/girl/animations/walk/" + (i+1) + ".png");
        }

        //Stand
        for (int i = 0; i < 12; i++){
            framesBoyStand[i] = game.getManager().get("Sprites/player/boy/stand/" + (i+1)+ ".png");
            framesGirlStand[i] = game.getManager().get("Sprites/player/girl/stand/" + (i+1) + ".png");
        }

        //Jump
        for (int i = 0; i < 8; i++){
            framesBoyJump[i] = game.getManager().get("Sprites/player/boy/jump/" + (i+1)+ ".png");
            framesGirlJump[i] = game.getManager().get("Sprites/player/girl/jump/" + (i+1) + ".png");
        }

        Animation<Texture> walkBoyAnimation = new Animation<Texture>(1f/6f, framesBoy);
        Animation<Texture> walkGirlAnimation = new Animation<Texture>(1f/6f, framesGirl);
        Animation<Texture> BoyAnimationStand = new Animation<Texture>(1f/12f, framesBoyStand);
        Animation<Texture> GirlAnimationStand = new Animation<Texture>(1f/12f, framesGirlStand);
        Animation<Texture> BoyAnimationJump = new Animation<Texture>(1f/8f, framesBoyJump);
        Animation<Texture> GirlAnimationJump = new Animation<Texture>(1f/8f, framesGirlJump);
        player = new PlayerActor(world, playerBoyTexture, walkBoyAnimation, playerGirlTexture, walkGirlAnimation,
                BoyAnimationStand, GirlAnimationStand, GirlAnimationJump, BoyAnimationJump,new Vector2(1, 7));
        stage.addActor(player);
    }


    public void createPolePlatforms(){
        Texture platformTexture = game.getManager().get("Sprites/evening/Plataforma 2_Mapa 2.png");
        TextureRegion platformRegion = new TextureRegion(platformTexture, 170, 50, 667, 185);
        polePlatforms.add(new NormalPlatformActor(world, platformRegion, new Vector2(1.2f, 6f)));
        polePlatforms.add(new NormalPlatformActor(world, platformRegion, new Vector2(13f, 2.5f)));
        polePlatforms.add(new NormalPlatformActor(world, platformRegion, new Vector2(1.2f, 2f)));
        polePlatforms.add(new NormalPlatformActor(world, platformRegion, new Vector2(7f, 0.5f)));
        polePlatforms.add(new NormalPlatformActor(world, platformRegion, new Vector2(7f, 4f)));
        polePlatforms.add(new NormalPlatformActor(world, platformRegion, new Vector2(13f, 6.2f)));


        for(NormalPlatformActor actor: polePlatforms){
            stage.addActor(actor);
        }
    }


    public void createNormalPlatforms(){

        Texture platformTexture = game.getManager().get("Sprites/evening/Plataforma 1_Mapa 2.png");
        TextureRegion platformRegion = new TextureRegion(platformTexture, 170, 50, 667, 185);
        normalPlatformsDown.add(new NormalPlatformActor(world, platformRegion, new Vector2(4f, 6f)));
        normalPlatformsUp.add(new NormalPlatformActor(world, platformRegion, new Vector2(10f, 0f)));

        for(NormalPlatformActor actor: normalPlatformsDown) {
            stage.addActor(actor);
        }

        for (NormalPlatformActor actor : normalPlatformsUp){
            stage.addActor(actor);
        }

    }


    public void moverPlataformas(){
        for (NormalPlatformActor actor : normalPlatformsDown) {
            actor.moverAbajo();
        }

        for (NormalPlatformActor actor: normalPlatformsUp){
            actor.moverArriba();
        }
    }

    public void createItems(){

        Texture brokenSheet = game.getManager().get("Sprites/items/coca2.png");
        Texture sheet = game.getManager().get("Sprites/items/sheet.png");
        Texture coffee = game.getManager().get("Sprites/items/jugo2.png");
        Texture wrinkledSheet = game.getManager().get("Sprites/items/broken-sheet.png");

        items.add(new ItemActor(world, sheet, brokenSheet, coffee, wrinkledSheet, new Vector2(4.5f, 6.6f),
                ItemActor.ItemType.BROKEN_SHEET));
        items.add(new ItemActor(world, sheet, brokenSheet, coffee, wrinkledSheet, new Vector2(1.7f, 2.6f),
                ItemActor.ItemType.COFFEE));
        items.add(new ItemActor(world, sheet, brokenSheet, coffee, wrinkledSheet, new Vector2(7.6f, 1.1f),
                ItemActor.ItemType.SHEET));
        items.add(new ItemActor(world, sheet, brokenSheet, coffee, wrinkledSheet, new Vector2(10.2f, 0.7f),
                ItemActor.ItemType.WRINKLED_SHEET));
        items.add(new ItemActor(world, sheet, brokenSheet, coffee, wrinkledSheet, new Vector2(12.8f, 3f),
                ItemActor.ItemType.COFFEE));
        items.add(new ItemActor(world, sheet, brokenSheet, coffee, wrinkledSheet, new Vector2(7.2f, 4.6f),
                ItemActor.ItemType.SHEET));


        for(ItemActor item : items){
            stage.addActor(item);
        }
    }

    public void moverItems(){

        for (ItemActor actor : items) {
            if (actor.getType() == ItemActor.ItemType.BROKEN_SHEET) {
                actor.moverAbajo();
            }

            if (actor.getType() == ItemActor.ItemType.WRINKLED_SHEET){
                actor.moverArriba();
            }
        }

    }

    @Override
    public void hide() {
        player.detach();
        player.remove();
        for(NormalPlatformActor actor: normalPlatformsDown){
            actor.detach();
            actor.remove();
        }

        for(NormalPlatformActor actor: normalPlatformsUp){
            actor.detach();
            actor.remove();
        }

        for(NormalPlatformActor actor: polePlatforms){
            actor.detach();
            actor.remove();
        }
        for(ItemActor item: items){
            item.detach();
            item.remove();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getCamera().update();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, ANCHO, ALTO);
        stage.getBatch().draw(house3, 150, 20, 285, 285);
        stage.getBatch().draw(house2, 335, 20, 285, 285);
        stage.getBatch().draw(house1, 555, 20, 285, 285);
        stage.getBatch().draw(house2, 945, 20, 285, 285);
        stage.getBatch().draw(house1, 1120, 20, 370, 370);
        stage.getBatch().draw(sun, 0, 520);

        score.draw(stage.getBatch(), "" + UserPreferences.getInstance().getScore(), 8*ANCHO/9, 20*ALTO/21);
        stage.getBatch().end();
        stage.act();
        world.step(delta, 6, 2);

        stage.draw();

        if(player.getBody().getPosition().y < 0){
            game.setScreen(new LoserEvening(game));
        }
        /**debugCamera.update();
         debugRenderer.render(world, debugCamera.combined);
         */
        for(Body body: bodiesToBeDestroyed){
            world.destroyBody(body);
        }

        bodiesToBeDestroyed.clear();

        if(win){
            game.setScreen(new Night(game));
        }
        //Tecla de Back
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new MainMenu(game));
        }
    }


    @Override
    public void dispose() {
        music.stop();
        UserPreferences.getInstance().setPosition(music.getPosition());
        for(NormalPlatformActor platform : polePlatforms){
            platform.detach();
        }
        for(NormalPlatformActor platformActor : normalPlatformsDown){
            platformActor.detach();
        }

        for(NormalPlatformActor platformActor : normalPlatformsUp){
            platformActor.detach();
        }

        for(ItemActor actor : items){
            actor.detach();
        }
        winActor.detach();
        player.detach();
        stage.dispose();
        world.dispose();
        skin.dispose();
    }
}
