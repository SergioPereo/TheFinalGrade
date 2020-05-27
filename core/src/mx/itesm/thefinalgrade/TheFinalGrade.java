package mx.itesm.thefinalgrade;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

import mx.itesm.thefinalgrade.menus.Historia1;
import mx.itesm.thefinalgrade.menus.MainMenu;
import mx.itesm.thefinalgrade.menus.StartMenu;
import mx.itesm.thefinalgrade.worlds.Evening;
import mx.itesm.thefinalgrade.worlds.Morning;

public class TheFinalGrade extends Game {

	private AssetManager manager;

	public AssetManager getManager(){
		return manager;
	}

	@Override
	public void create() {
		manager = new AssetManager();

		// region
		manager.load("Sprites/player/boy/nino-r-r.png", Texture.class);
		manager.load("Sprites/player/girl/nina-r-r.png", Texture.class);
		manager.load("Sprites/platforms/platform2resized.png", Texture.class);
		manager.load("Sprites/backgrounds/skybackground.png", Texture.class);
		manager.load("Sprites/backgrounds/FondoCieloTarde.png", Texture.class);

		manager.load("Sprites/buttons/padBack.png", Texture.class);
		manager.load("Sprites/buttons/padKnob.png", Texture.class);
		manager.load("Sprites/buttons/Brincar.png", Texture.class);
		manager.load("Sprites/buttons/BrincarClicked.png", Texture.class);

		manager.load("Sprites/player/boy/animations/walk/1.png", Texture.class);
		manager.load("Sprites/player/boy/animations/walk/2.png", Texture.class);
		manager.load("Sprites/player/boy/animations/walk/3.png", Texture.class);
		manager.load("Sprites/player/boy/animations/walk/4.png", Texture.class);
		manager.load("Sprites/player/boy/animations/walk/5.png", Texture.class);
		manager.load("Sprites/player/boy/animations/walk/6.png", Texture.class);
		manager.load("Sprites/player/girl/animations/walk/1.png", Texture.class);
		manager.load("Sprites/player/girl/animations/walk/2.png", Texture.class);
		manager.load("Sprites/player/girl/animations/walk/3.png", Texture.class);
		manager.load("Sprites/player/girl/animations/walk/4.png", Texture.class);
		manager.load("Sprites/player/girl/animations/walk/5.png", Texture.class);
		manager.load("Sprites/player/girl/animations/walk/6.png", Texture.class);
		manager.load("Sprites/items/broken-sheet.png", Texture.class);
		manager.load("Sprites/items/sheet.png", Texture.class);
		manager.load("Sprites/buttons/Brincar.png", Texture.class);
		manager.load("Sprites/buttons/BrincarClicked.png", Texture.class);
		manager.load("Sprites/morning/Arbolito 1.png", Texture.class);
		manager.load("Sprites/morning/Arbolito 2.png", Texture.class);
		manager.load("Sprites/morning/Casita.png", Texture.class);
		manager.load("Sprites/morning/Casita 2.png", Texture.class);
		manager.load("Sprites/morning/Casita 3.png", Texture.class);
		manager.load("Sprites/morning/Pasto_Base.png", Texture.class);
		manager.load("Sprites/morning/Pastito.png", Texture.class);
		manager.load("Sprites/morning/Nube.png", Texture.class);
		manager.load("Sprites/evening/Casita 1_Mapa 2.png", Texture.class);
		manager.load("Sprites/evening/Casita 2_Mapa 2.png", Texture.class);
		manager.load("Sprites/evening/Plataforma 1_Mapa 2.png", Texture.class);
		manager.load("Sprites/evening/Plataforma 2_Mapa 2.png", Texture.class);
		manager.load("Sprites/evening/CDT.png", Texture.class);
		manager.load("Sprites/evening/piso.png", Texture.class);
		manager.load("Sprites/evening/Sol.png", Texture.class);
		manager.load("Sprites/items/coffee.png", Texture.class);
		manager.load("Sprites/items/wrinkled-sheet.png", Texture.class);
		manager.load("Sprites/platforms/platform1-r-r.png", Texture.class);

		manager.load("Sprites/backgrounds/Instructions.jpg", Texture.class);
		manager.load("Sprites/backgrounds/Us.jpeg", Texture.class);
		manager.load("Sprites/backgrounds/Failed_Niño.png", Texture.class);
		manager.load("Sprites/backgrounds/Failed_Niña.png", Texture.class);
		manager.load("Sprites/backgrounds/boyWin.png", Texture.class);
		manager.load("Sprites/backgrounds/girlWin.png", Texture.class);
		manager.load("Sprites/backgrounds/choose_character.png", Texture.class);

		manager.load("Sprites/player/boy/boy-start.png", Texture.class);
		manager.load("Sprites/elements/CalendarioTransparente.png", Texture.class);
		manager.load("Sprites/elements/Calendario.png", Texture.class);
		manager.load("Sprites/elements/niño.png", Texture.class);

		manager.load("Sprites/player/girl/nina-r.png", Texture.class);

		manager.load("Sprites/buttons/Start.png", Texture.class);
		manager.load("Sprites/buttons/StartClick.png", Texture.class);

		manager.load("Sprites/buttons/Continue.png", Texture.class);
		manager.load("Sprites/buttons/Continue_Click.png", Texture.class);

		manager.load("Sprites/buttons/NewGame.png", Texture.class);
		manager.load("Sprites/buttons/NewGame_Click.png", Texture.class);

		manager.load("Sprites/buttons/Opciones.png", Texture.class);

		manager.load("Sprites/buttons/Credits.png", Texture.class);

		manager.load("Sprites/buttons/Instructions.png", Texture.class);

		manager.load("Sprites/buttons/BotonRegresar.png", Texture.class);
		manager.load("Sprites/buttons/BotonRegresar_Click.png", Texture.class);

		//endregion


		manager.load("Sprites/buttons/BotonAdelante.png", Texture.class);
		manager.load("Sprites/buttons/BotonAdelante_Click.png", Texture.class);

		manager.load("Sprites/history/boy/1.png", Texture.class);
		manager.load("Sprites/history/boy/2.png", Texture.class);
		manager.load("Sprites/history/boy/3.png", Texture.class);
		manager.load("Sprites/history/boy/4.png", Texture.class);

		manager.load("Sprites/history/girl/1.png", Texture.class);
		manager.load("Sprites/history/girl/2.png", Texture.class);
		manager.load("Sprites/history/girl/3.png", Texture.class);
		manager.load("Sprites/history/girl/4.png", Texture.class);

		manager.load("music/Mushroom Theme.mp3", Music.class);

		manager.load("Sprites/backgrounds/girl_bw.png", Texture.class);
		manager.load("Sprites/backgrounds/girl_color.png", Texture.class);

		manager.load("Sprites/backgrounds/boy_bw.png", Texture.class);
		manager.load("Sprites/backgrounds/boy_color.png", Texture.class);

		manager.finishLoading();
		//setScreen(new Morning(this));
		setScreen(new StartMenu(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
	}
}
