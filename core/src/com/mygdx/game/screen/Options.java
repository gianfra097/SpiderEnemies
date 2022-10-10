package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.SpiderEnemies;

public class Options implements Screen{
    
    //Creo classi per passare dimensioni bottoni
    private static final int BACK_BUTTON_WIDTH = 150;
    private static final int BACK_BUTTON_HEIGHT = 70;
    private static final int BACK_BUTTON_Y = 710;
    private static final int MUSIC_BUTTON_WIDTH = 150;
    private static final int MUSIC_BUTTON_HEIGHT = 70;
    private static final int MUSIC_BUTTON_Y = 380;
    private static final int MUSIC2_BUTTON_WIDTH = 150;
    private static final int MUSIC2_BUTTON_HEIGHT = 70;
    private static final int MUSIC2_BUTTON_Y = 380;
    private static final int RESULTS_BUTTON_WIDTH = 150;
    private static final int RESULTS_BUTTON_HEIGHT = 70;
    private static final int RESULTS_BUTTON_Y = 230;
    private static final int MARKET_BUTTON_WIDTH = 150;
    private static final int MARKET_BUTTON_HEIGHT = 70;
    private static final int MARKET_BUTTON_Y = 230;
    
    //Buttons
    private Texture backButtonActive;
    private Texture backButtonInactive;
    private Texture musicButtonActive;
    private Texture musicButtonInactive;
    private Texture music2ButtonActive;
    private Texture music2ButtonInactive;
    private Texture resultsButtonActive;
    private Texture resultsButtonInactive;
    private Texture marketButtonActive;
    private Texture marketButtonInactive;
    
    //Background
    private Texture OptionsBackground;
    
    //Title
    private Texture Title;

    //Serve a mutare i livelli se il giocatore lo decide. Public perché se 0 muta tutte le altre classi
    public static int mute = 0;
    
    private BitmapFont font;
    
    private SpiderEnemies game;

    Options(SpiderEnemies game) {
        this.game = game;
    }

    @Override
    public void show() {
        
        //Buttons
        backButtonActive = new Texture("back1.png");
        backButtonInactive = new Texture("back2.png");
        musicButtonActive = new Texture("mute1.png");
        musicButtonInactive = new Texture("mute2.png");
        music2ButtonActive = new Texture("unmute1.png");
        music2ButtonInactive = new Texture("unmute2.png");
        resultsButtonActive = new Texture("results1.png");
        resultsButtonInactive = new Texture("results2.png");
        marketButtonActive = new Texture("market1.png");
        marketButtonInactive = new Texture("market2.png");

        //Background
        OptionsBackground = new Texture("OptionsBackground.png");
        
        //Title
        Title = new Texture("title.png");
        
        //Carico il font per scrivere lo stato dell'audio
        font = new BitmapFont(Gdx.files.internal("myfont.fnt"));
        
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
	game.batch.begin();      
        
        //Background
        game.batch.draw(OptionsBackground, 0, 0, SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT);
        
        //Title
        game.batch.draw(Title,200,600,700,130);
        
        //Calcolo il posiziomento dei tasti per utilizzare le due immagini colorate
        //Back
        int x = SpiderEnemies.WIDTH / 2 - BACK_BUTTON_WIDTH / 2 + 420;
        if(game.camera.getInputInGameWorld().x < x + BACK_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < BACK_BUTTON_Y + BACK_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > BACK_BUTTON_Y){
            game.batch.draw(backButtonActive, 17, BACK_BUTTON_Y-10, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
            if (Gdx.input.justTouched()){  //Gestisco il pulsante play, se cliccato
                this.dispose();
                game.setScreen(new SecondMenu(game));  //Passa al secondo menu che contiene gli altri tasti
            }
        } else {
            game.batch.draw(backButtonInactive, 17, BACK_BUTTON_Y-10, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        }
        
        //Mute music
        x = SpiderEnemies.WIDTH / 2 - MUSIC_BUTTON_WIDTH / 2 + 80;
        if(game.camera.getInputInGameWorld().x < x + MUSIC_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < MUSIC_BUTTON_Y + MUSIC_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > MUSIC_BUTTON_Y){
            game.batch.draw(musicButtonActive, 350, MUSIC_BUTTON_Y, MUSIC_BUTTON_WIDTH, MUSIC_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){  //Gestisco il pulsante mute, se cliccato
                mute = 0;    //Variabile che usero' nei livelli
                game.music.play();    //Avvio la musica                
            }            
        } else {
            game.batch.draw(musicButtonInactive, 350, MUSIC_BUTTON_Y, MUSIC_BUTTON_WIDTH, MUSIC_BUTTON_HEIGHT);
        }
        
        x = SpiderEnemies.WIDTH / 2 - MUSIC2_BUTTON_WIDTH / 2 - 120;
        if(game.camera.getInputInGameWorld().x < x + MUSIC2_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < MUSIC2_BUTTON_Y + MUSIC2_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > MUSIC2_BUTTON_Y){
            game.batch.draw(music2ButtonActive, 550, MUSIC2_BUTTON_Y, MUSIC2_BUTTON_WIDTH, MUSIC2_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){  //Gestisco il pulsante mute, se cliccato
                mute = 1;    //Variabile che usero' nei livelli
                game.music.stop();    //Stoppo la musica
            }
        } else {
            game.batch.draw(music2ButtonInactive, 550, MUSIC2_BUTTON_Y, MUSIC2_BUTTON_WIDTH, MUSIC2_BUTTON_HEIGHT);
        }      

        font.getData().setScale(1,1);
        if(mute == 0){
            font.draw(game.batch,String.format("STATUS: UNMUTE"),320,360);
        } else{
            font.draw(game.batch,String.format("STATUS: MUTE"),320,360);
        }
        
        x = SpiderEnemies.WIDTH / 2 - RESULTS_BUTTON_WIDTH / 2 + 83;
        if(game.camera.getInputInGameWorld().x < x + RESULTS_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < RESULTS_BUTTON_Y + RESULTS_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > RESULTS_BUTTON_Y){
            game.batch.draw(resultsButtonActive, 350, RESULTS_BUTTON_Y, RESULTS_BUTTON_WIDTH, RESULTS_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){  //Gestisco il pulsante mute, se cliccato
                this.dispose();
                game.setScreen(new Results(game));  //Passa al secondo menu che contiene gli altri tasti
            }
        } else {
            game.batch.draw(resultsButtonInactive, 350, RESULTS_BUTTON_Y, RESULTS_BUTTON_WIDTH, RESULTS_BUTTON_HEIGHT);
        }   
        
        x = SpiderEnemies.WIDTH / 2 - MARKET_BUTTON_WIDTH / 2 - 120;
        if(game.camera.getInputInGameWorld().x < x + MARKET_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < MARKET_BUTTON_Y + MARKET_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > MARKET_BUTTON_Y){
            game.batch.draw(marketButtonActive, 550, MARKET_BUTTON_Y, MARKET_BUTTON_WIDTH, MARKET_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){  //Gestisco il pulsante mute, se cliccato
                this.dispose();
                game.setScreen(new Market(game));  //Passa al secondo menu che contiene gli altri tasti
            }
        } else {
            game.batch.draw(marketButtonInactive, 550, MARKET_BUTTON_Y, MARKET_BUTTON_WIDTH, MARKET_BUTTON_HEIGHT);
        }   


        game.batch.end();
         
    }

    @Override
    public void resize(int i, int i1) {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        
    }
    
}
