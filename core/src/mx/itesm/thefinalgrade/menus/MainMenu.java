package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.levels.EveningLevel;
import mx.itesm.thefinalgrade.levels.MorningLevel;
import mx.itesm.thefinalgrade.util.variables.UserPreferences;

public class MainMenu extends Menu {

    public MainMenu(TheFinalGrade game, String backgroundPath) {
        super(game, backgroundPath);

    }
    //Botones
    private Texture textContinueButton; private Texture textContinueButton2;
    private Texture textNewGameButton; private Texture textNewGameButton2;
    private Texture textOptionsButton; private Texture textOptionsButton2;
    private Texture textCreditsButton; private Texture textCreditsButton2;
    private Texture textExit; private Texture textExit2;
    private Texture textcalendar;
    private Texture textniño;
    private Texture textNiña;


    private Music music;


    @Override
    protected void createMenu() {
        //la escena se conecta con la vista
        menuStage = new Stage(vista);


        //boton continuar

        textContinueButton = new Texture("Sprites/buttons/Continue.png");
        TextureRegionDrawable trdcontinue = new TextureRegionDrawable(
                new TextureRegion(textContinueButton));

        //imagen btn continuar presionado
        textContinueButton2 = new Texture("Sprites/buttons/Continue_Click.png");
        TextureRegionDrawable trdcontinue2 = new TextureRegionDrawable(
                new TextureRegion(textContinueButton2));
        ImageButton continuebtn = new ImageButton(trdcontinue, trdcontinue2);
        continuebtn.setPosition(ANCHO/3,530);

        //boton NUEVO JUEGO
        textNewGameButton = new Texture("Sprites/buttons/NewGame.png");
        TextureRegionDrawable trdnewG = new TextureRegionDrawable(
                new TextureRegion(textNewGameButton));

        //imagen btn NUEVO JUEGO presionado
        textNewGameButton2 = new Texture("Sprites/buttons/NewGame_Click.png");
        TextureRegionDrawable trdNewG2 = new TextureRegionDrawable(
                new TextureRegion(textNewGameButton2));
        ImageButton newGameBtn = new ImageButton(trdnewG, trdNewG2);
        newGameBtn.setPosition(ANCHO/3,400);

        //boton OPCIONES
        textOptionsButton = new Texture("Sprites/buttons/Opciones.png");
        TextureRegionDrawable trdoption = new TextureRegionDrawable(
                new TextureRegion(textOptionsButton));

        //imagen btn OPCIONES presionado
        textOptionsButton2 = new Texture("Sprites/buttons/Opciones.png");
        TextureRegionDrawable trdoption2 = new TextureRegionDrawable(
                new TextureRegion(textOptionsButton2));

        ImageButton optionBtn = new ImageButton(trdoption, trdoption2);
        optionBtn.setPosition(ANCHO/3,250);

        //boton CREDITOS
        textCreditsButton = new Texture("Sprites/buttons/Credits.png");
        TextureRegionDrawable trdCredits = new TextureRegionDrawable(
                new TextureRegion(textCreditsButton));
        //imagen btn CREDITOS presionado

        textCreditsButton2 = new Texture("Sprites/buttons/Credits.png");
        TextureRegionDrawable trdCredits2 = new TextureRegionDrawable(
                new TextureRegion(textCreditsButton2));
        ImageButton creditsBtn = new ImageButton(trdCredits, trdCredits2);
        creditsBtn.setPosition(ANCHO/3,130);

        textExit = new Texture("Sprites/buttons/Instructions.png");
        TextureRegionDrawable trdExit = new TextureRegionDrawable(
                new TextureRegion(textExit));

        textExit2 = new Texture("Sprites/buttons/Instructions.png");
        TextureRegionDrawable trdExitPress = new TextureRegionDrawable(
                new TextureRegion(textExit2));
        ImageButton instructionsBtn = new ImageButton(trdExit, trdExitPress);
        instructionsBtn.setPosition(ANCHO/3, 20);


        //imagen Niño
        textniño = new Texture("Niño/niño(1).png");
        //imagen calendario
        textcalendar = new Texture("Sprites/elements/Calendario.png");
        textNiña = new Texture("Niña/Niña.png");

        // Add listeners
        newGameBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new Historia1(game, "Historia/1_Cuarto_1.png"));

            }
        });

        optionBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new SoundSettingsMenu(game, "Sprites/backgrounds/Fondo_StartMenu.png")); // Forums tells that this lane has to follow Gdx.app.exit because some things remain open in task manager
            }
        });


        creditsBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new CreditsMenu(game, "Sprites/backgrounds/Fondo_StartMenu.png"));
            }
        });


        instructionsBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new InstructionsMenu(game, "Sprites/backgrounds/Instructions.jpg"));
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

        music = Gdx.audio.newMusic(Gdx.files.internal("music/Mushroom Theme.mp3"));
        System.out.println("Current Position: " + UserPreferences.getInstance().getPosition());
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
        batch.draw(background,0,0);
        batch.draw(textniño, 800,0);
        batch.draw(textNiña, 0, 0);
        batch.end();
        menuStage.draw();

    }
    @Override
    public void dispose() {
        background.dispose();
        UserPreferences.getInstance().setPosition(music.getPosition());
        music.dispose();
        textContinueButton.dispose();
        textContinueButton2.dispose();
        textNewGameButton.dispose();
        textNewGameButton2.dispose();
        textOptionsButton.dispose();
        textOptionsButton2.dispose();
        textCreditsButton.dispose();
        textCreditsButton2.dispose();
        textExit.dispose();
        textExit2.dispose();
        textcalendar.dispose();
        textniño.dispose();
        menuStage.dispose();
    }
}
