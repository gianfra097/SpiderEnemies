package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screen.MainMenuScreen;
import static com.mygdx.game.screen.Options.mute;
import com.mygdx.game.tools.GameCamera;

public class SpiderEnemies extends Game {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    
    public SpriteBatch batch;
    public GameCamera camera;
    
    public Music music;
    
    //Coins in modo che posso usarli in tutto il gioco
    public int coins;

    @Override
    public void create() {  //Apertura app
        batch = new SpriteBatch();         //Serve a disegnare
        camera = new GameCamera(WIDTH, HEIGHT);
        this.setScreen(new MainMenuScreen(this)); //Visualizza menu
        
        //Puntatore con immagine custom
        Pixmap pm = new Pixmap(Gdx.files.internal("cursor.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        pm.dispose();
        
        //Seleziono la musica ed inserisco volume e loop
        music = Gdx.audio.newMusic(Gdx.files.internal("menumusic.mp3"));
        music.setVolume(0.2f);
        music.setLooping(true);
        music.play();
        
    }
    
    @Override
    public void render(){  //Esecuzione app
        batch.setProjectionMatrix(camera.combined());
        super.render();
        
        //Se l'utente preme F5, stoppo la musica dei livelli con "mute = 1" e la musica del menu con "music.stop();"
        if(mute == 0 && Gdx.input.isKeyPressed(Keys.F5)){
            mute = 1;
            music.stop();
        } else if(mute == 1 && Gdx.input.isKeyPressed(Keys.F5)){ //Se l'utente ri-preme F5, avvio la musica dei livelli e del menu'"
            mute = 0;
            music.play();
        }
    }
    
    @Override
    public void resize(int width, int height){  
        camera.update(width, height);
        super.resize(width, height);
    }

    Object getBatch() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
