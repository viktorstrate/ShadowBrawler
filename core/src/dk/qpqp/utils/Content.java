package dk.qpqp.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * Created by Viktor on 19/04/2015.
 * The Content class loads resources into memory.
 */
public class Content {

    public static HashMap<String, Texture> textures = new HashMap<String, Texture>();
    private static HashMap<String, Music> music = new HashMap<String, Music>();
    private static HashMap<String, Sound> sounds = new HashMap<String, Sound>();

    public static Texture getTexture(String key) {
        return textures.get(key);
    }

    public static void loadTexture(String texturePath, String key) {
        Content.textures.put(key, new Texture(Gdx.files.internal(texturePath)));
    }

    public static Music getMusic(String key) {
        return music.get(key);
    }

    public static void loadMusic(String musicPath, String key) {
        Content.music.put(key, Gdx.audio.newMusic(Gdx.files.internal(musicPath)));
    }

    public static Sound getSound(String key) {
        return sounds.get(key);
    }

    public static void loadSounds(String soundPath, String key) {
        Content.sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(soundPath)));
    }
}
