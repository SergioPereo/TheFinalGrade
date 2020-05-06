package mx.itesm.thefinalgrade;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import mx.itesm.thefinalgrade.levels.MorningLevel;
import mx.itesm.thefinalgrade.menus.StartMenu;
import mx.itesm.thefinalgrade.worlds.Morning;

public class TheFinalGrade extends Game {

	private AssetManager manager;

	public AssetManager getManager(){
		return manager;
	}

	@Override
	public void create() {
		manager = new AssetManager();
		manager.load("Sprites/player/nino-r-r.png", Texture.class);
		manager.load("Sprites/platforms/platform2resized.png", Texture.class);
		manager.load("Sprites/backgrounds/skybackground.png", Texture.class);
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
		manager.finishLoading();
		//setScreen(new Morning(this,"Sprites/backgrounds/Fondo_StartMenu.png"));
		setScreen(new StartMenu(this, "Sprites/backgrounds/Fondo_StartMenu.png"));
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
