package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class MainMenu extends Menu {


    //Botones
    private Texture textContinueButton; private Texture textContinueButton2;
    private Texture textNewGameButton; private Texture textNewGameButton2;
    private Texture textOptionsButton; private Texture textOptionsButton2;
    private Texture textCreditsButton; private Texture textCreditsButton2;
    private Texture textExit; private Texture textExit2;
    private Texture textcalendar;
    private Texture textniño;
    private Texture textNiña;

    //Asset manager que tenemos en la clase LoadScreen
    //private final AssetManager assetManager;
    private Music music;

    public MainMenu(TheFinalGrade game) {
        super(game);


    }
    @Override
    public void show() {
        super.show();
    }

    @Override
    protected void createMenu() {
        //la escena se conecta con la vista
        menuStage = new Stage(vista);

        background = game.getManager().get("Sprites/backgrounds/skybackground.png");

        //boton continuar

        textContinueButton = game.getManager().get("Sprites/buttons/Continue.png");
        TextureRegionDrawable trdcontinue = new TextureRegionDrawable(
                new TextureRegion(textContinueButton));

        //imagen btn continuar presionado
        textContinueButton2 = game.getManager().get("Sprites/buttons/Continue_Click.png");
        TextureRegionDrawable trdcontinue2 = new TextureRegionDrawable(
                new TextureRegion(textContinueButton2));
        ImageButton continuebtn = new ImageButton(trdcontinue, trdcontinue2);
        continuebtn.setPosition(ANCHO/3+75,550);

        //boton NUEVO JUEGO
        textNewGameButton = game.getManager().get("Sprites/buttons/NewGame.png");
        TextureRegionDrawable trdnewG = new TextureRegionDrawable(
                new TextureRegion(textNewGameButton));

        //imagen btn NUEVO JUEGO presionado
        textNewGameButton2 = game.getManager().get("Sprites/buttons/NewGame_Click.png");
        TextureRegionDrawable trdNewG2 = new TextureRegionDrawable(
                new TextureRegion(textNewGameButton2));
        ImageButton newGameBtn = new ImageButton(trdnewG, trdNewG2);
        newGameBtn.setPosition(ANCHO/3+75,380);

        //boton OPCIONES
        textOptionsButton = game.getManager().get("Sprites/buttons/Opciones.png");
        TextureRegionDrawable trdoption = new TextureRegionDrawable(
                new TextureRegion(textOptionsButton));

        //imagen btn OPCIONES presionado
        textOptionsButton2 = game.getManager().get("Sprites/buttons/Opciones.png");
        TextureRegionDrawable trdoption2 = new TextureRegionDrawable(
                new TextureRegion(textOptionsButton2));
        ImageButton optionBtn = new ImageButton(trdoption, trdoption2);
        optionBtn.setPosition(ANCHO/3,250);
        optionBtn.setSize(412.5f, 105);
        optionBtn.getImageCell().size(412.5f, 105);


        //boton CREDITOS
        textCreditsButton = game.getManager().get("Sprites/buttons/Credits.png");
        TextureRegionDrawable trdCredits = new TextureRegionDrawable(
                new TextureRegion(textCreditsButton));
        //imagen btn CREDITOS presionado

        textCreditsButton2 = game.getManager().get("Sprites/buttons/Credits.png");
        TextureRegionDrawable trdCredits2 = new TextureRegionDrawable(
                new TextureRegion(textCreditsButton2));
        ImageButton creditsBtn = new ImageButton(trdCredits, trdCredits2);
        creditsBtn.setPosition(ANCHO/3,135);
        creditsBtn.setSize(412.5f, 105);
        creditsBtn.getImageCell().size(412.5f, 105);

        textExit = game.getManager().get("Sprites/buttons/Instructions.png");
        TextureRegionDrawable trdExit = new TextureRegionDrawable(
                new TextureRegion(textExit));

        textExit2 = game.getManager().get("Sprites/buttons/Instructions.png");
        TextureRegionDrawable trdExitPress = new TextureRegionDrawable(
                new TextureRegion(textExit2));
        ImageButton instructionsBtn = new ImageButton(trdExit, trdExitPress);
        instructionsBtn.setPosition(ANCHO/3, 20);
        instructionsBtn.setSize(412.5f, 105);
        instructionsBtn.getImageCell().size(412.5f, 105);

        //imagen Niño
        textniño = game.getManager().get("Sprites/player/boy/boy-start.png");
        //imagen calendario
        textcalendar = game.getManager().get("Sprites/elements/Calendario.png");
        textNiña = game.getManager().get("Sprites/player/girl/nina-r.png");

        // Add listeners
        newGameBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new CharacterChooser(game));

            }
        });

        optionBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new SoundSettingsMenu(game)); // Forums tells that this lane has to follow Gdx.app.exit because some things remain open in task manager
            }
        });


        creditsBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new CreditsMenu(game));
            }
        });


        instructionsBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new InstructionsMenu(game));
            }
        });


        // Add actors
        menuStage.addActor(newGameBtn);
        menuStage.addActor(optionBtn);
        menuStage.addActor(continuebtn);
        menuStage.addActor(creditsBtn);
        menuStage.addActor(instructionsBtn);


        // Add input processor
        Gdx.input.setInputProcessor(menuStage);

        music = game.getManager().get("music/Mushroom Theme.mp3");
        music.setVolume(UserPreferences.getInstance().getVolume());
        music.setLooping(true);
        music.setPosition(UserPreferences.getInstance().getPosition());
        music.play();

    }
    @Override
    public void render(float delta){
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background,0,0, ANCHO, ALTO);
        batch.draw(textniño, 800,0);
        batch.draw(textNiña, 0, 0);
        batch.end();
        menuStage.draw();


    }
    @Override
    public void dispose() {
        music.stop();
        UserPreferences.getInstance().setPosition(music.getPosition());
        menuStage.dispose();
    }
}
