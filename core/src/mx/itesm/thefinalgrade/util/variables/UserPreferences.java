package mx.itesm.thefinalgrade.util.variables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class UserPreferences {
    private static UserPreferences m_instance;
    private Preferences preferences;
    private float volume = 0.3f;
    private float position = 0f;
    private boolean isBoy = false;
    private int score = 0;

    private UserPreferences(){
        preferences = Gdx.app.getPreferences("Game Preferences");
        preferences.putFloat("volume", volume);
        preferences.putFloat("position", position);
        preferences.putBoolean("isBoy", isBoy);
    }

    public static UserPreferences getInstance(){
        if(m_instance == null){
            m_instance = new UserPreferences();
        }
        return m_instance;
    }

    private Preferences getPreferences(){
        if(preferences == null){
            preferences = Gdx.app.getPreferences("Game Preferences");
        }
        return preferences;
    }

    public void setVolume(float volume){
        this.volume = volume;
        getPreferences().putFloat("volume", volume);
        getPreferences().flush();
    }

    public void setPosition(float position){
        this.position = position;
        getPreferences().putFloat("position", position);
        getPreferences().flush();
    }

    public void setGender(boolean isBoy){
        this.isBoy = isBoy;
        getPreferences().putBoolean("isBoy", isBoy);
        getPreferences().flush();
    }

    public void setScore(int score){
        this.score = score;
    }

    public float getPosition(){
        return position;
    }

    public float getVolume(){
        return volume;
    }

    public int getScore(){ return score; }

    public boolean getGender() { return isBoy; }
}
