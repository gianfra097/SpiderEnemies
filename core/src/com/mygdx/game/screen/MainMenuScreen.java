package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.mygdx.game.SpiderEnemies;

public class MainMenuScreen implements Screen{
    
    //Spidey
    private Texture spidey;
    private TextureRegion[] animationFrames;
    private Animation animation;
    private float elapsedTime;
    
    //Variabile booleana che viene posta true se non e' aperto chatbot, false se e' aperto
    public static boolean not_open = true;
    
    //Creo classi per passare dimensioni bottoni
    private static final int PLAY_BUTTON_WIDTH = 180;
    private static final int PLAY_BUTTON_HEIGHT = 80;
    private static final int OPTIONS_BUTTON_WIDTH = 180;
    private static final int OPTIONS_BUTTON_HEIGHT = 80;
    private static final int CHATBOT_BUTTON_WIDTH = 100;
    private static final int CHATBOT_BUTTON_HEIGHT = 60;
    private static final int PLAY_BUTTON_Y = 120;
    private static final int OPTIONS_BUTTON_Y = 30;
    private static final int CHATBOT_BUTTON_Y = 30;
    
    private SpiderEnemies game;
    
    //Buttons
    private Texture playButtonActive;
    private Texture playButtonInactive;
    private Texture optionsButtonActive;
    private Texture optionsButtonInactive;
    private Texture chatbotButtonActive;
    private Texture chatbotButtonInactive;
    
    //Background
    private Texture MenuBackground1;
    
    //Title
    private Texture Title;
    
    public MainMenuScreen (SpiderEnemies game){
        this.game = game;
    }
    
    @Override
    public void show() {
        
        //Spidey
        spidey = new Texture("spidey.png");
        TextureRegion[][] tmpFrames = TextureRegion.split(spidey,spidey.getWidth()/3,spidey.getHeight()/5);
        animationFrames = new TextureRegion[15];
        int index = 0;
        for (int i=0; i<5; i++){
            for (int j=0; j<3; j++){
                animationFrames[index++] = tmpFrames[i][j];
            }
        }
        animation = new Animation(0.15f, (Object[]) animationFrames);
        
        //Buttons
        playButtonActive = new Texture("play1.png");
        playButtonInactive = new Texture("play2.png");
        optionsButtonActive = new Texture("options1.png");
        optionsButtonInactive = new Texture("options2.png");
        chatbotButtonActive = new Texture("chatbot1.png");
        chatbotButtonInactive = new Texture("chatbot2.png");
        
        //Background
        MenuBackground1 = new Texture("MenuBackground.png");
        
        //Title
        Title = new Texture("title.png");
    }    

    @Override
    public void render(float f) {  //Colore e carica bottoni
               
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //Spidey
        elapsedTime += Gdx.graphics.getDeltaTime();
        
	game.batch.begin();
                
        //Background
        game.batch.draw(MenuBackground1, 0, 0, SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT);
        
        //Spidey
        game.batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true), 426,300,150,200);
        
        //Title
         game.batch.draw(Title,200,600,700,130);
        
        //Calcolo il posiziomento dei tasti per utilizzare le due immagini colorate
        
        //Play
        int x = SpiderEnemies.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2 ;
        if(game.camera.getInputInGameWorld().x < x + PLAY_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > PLAY_BUTTON_Y){
            game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){  //Gestisco il pulsante play, se cliccato
                this.dispose();
                game.setScreen(new SecondMenu(game));  //Passa al secondo menu che contiene gli altri tasti
            }
        } else {
            game.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }
        
        //Options
        x = SpiderEnemies.WIDTH / 2 - OPTIONS_BUTTON_WIDTH / 2 ;
        if(game.camera.getInputInGameWorld().x < x + OPTIONS_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < OPTIONS_BUTTON_Y + OPTIONS_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > OPTIONS_BUTTON_Y){
            game.batch.draw(optionsButtonActive, x, OPTIONS_BUTTON_Y, OPTIONS_BUTTON_WIDTH, OPTIONS_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){  //Gestisco il pulsante options, se cliccato
                this.dispose();
                game.setScreen(new Options(game));  //Passa alla pagina relativa alle opzioni
            }
        } else {
            game.batch.draw(optionsButtonInactive, x, OPTIONS_BUTTON_Y, OPTIONS_BUTTON_WIDTH, OPTIONS_BUTTON_HEIGHT);
        }
        
        //Chatbot
        x = SpiderEnemies.WIDTH / 2 - CHATBOT_BUTTON_WIDTH / 2 - 410;
        if(game.camera.getInputInGameWorld().x < x + CHATBOT_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < CHATBOT_BUTTON_Y + CHATBOT_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > CHATBOT_BUTTON_Y){
            game.batch.draw(chatbotButtonActive, 870, CHATBOT_BUTTON_Y, CHATBOT_BUTTON_WIDTH, CHATBOT_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){  //Gestisco il pulsante chatbot, se cliccato
                this.dispose();
                if(not_open == true){
                    ChatBot cb = new ChatBot();  //Apri la chat
                    not_open = false;
                }
            } 
        } else {
            game.batch.draw(chatbotButtonInactive, 870, CHATBOT_BUTTON_Y, CHATBOT_BUTTON_WIDTH, CHATBOT_BUTTON_HEIGHT);
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