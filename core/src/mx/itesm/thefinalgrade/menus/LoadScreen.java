package mx.itesm.thefinalgrade.menus;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import mx.itesm.thefinalgrade.TheFinalGrade;

public class LoadScreen extends TheFinalGrade {
    private final AssetManager assetManager = new AssetManager();
    @Override
    public void create() {
        assetManager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        //Pantalla Inicial
        setScreen(new MainMenu(this));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.clear();//Ocurre al final de la app
    }
}
