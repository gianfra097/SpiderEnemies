package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.mygdx.game.SpiderEnemies;

public class SecondMenu implements Screen{
    
    //public perché mi servirà poi nel livello bonus per abbassare l'opacita'
    public Color gamecolor;
    
    //Spidey
    private Texture spidey;
    private TextureRegion[] animationFrames;
    private Animation animation;
    private float elapsedTime;
    
    //Variabile che mi servira' per scegliere il livello
    private int lv = 0;
    
    //Creo classi per passare dimensioni dei bottoni "level"
    private static final int LEVEL1_BUTTON_WIDTH = 180;
    private static final int LEVEL1_BUTTON_HEIGHT = 80;
    private static final int LEVEL2_BUTTON_WIDTH = 180;
    private static final int LEVEL2_BUTTON_HEIGHT = 80;
    private static final int LEVEL3_BUTTON_WIDTH = 180;
    private static final int LEVEL3_BUTTON_HEIGHT = 80;
    private static final int OPTIONS_BUTTON_WIDTH = 180;
    private static final int OPTIONS_BUTTON_HEIGHT = 80;
    private static final int CHATBOT_BUTTON_WIDTH = 100;
    private static final int CHATBOT_BUTTON_HEIGHT = 60;
    private static final int LEVEL1_BUTTON_Y = 200;
    private static final int LEVEL2_BUTTON_Y = 120;
    private static final int LEVEL3_BUTTON_Y = 120;
    private static final int OPTIONS_BUTTON_Y = 30;
    private static final int CHATBOT_BUTTON_Y = 30;
    
    //Fade - Transizione
    public static final float FADEOUT = 0.5f;
    
    private SpiderEnemies game;
    
    //Buttons
    private Texture level1ButtonActive;
    private Texture level1ButtonInactive;
    private Texture level2ButtonActive;
    private Texture level2ButtonInactive;
    private Texture level3ButtonActive;
    private Texture level3ButtonInactive;    
    private Texture optionsButtonActive;
    private Texture optionsButtonInactive;
    private Texture chatbotButtonActive;
    private Texture chatbotButtonInactive;
    
    //Background
    private Texture MenuBackground1;
    
    //Title
    private Texture Title;
    
    public SecondMenu (SpiderEnemies game){
        this.game = game;
    }
        
    @Override
    public void show() {

        gamecolor = new Color (1, 1, 1, 1); 
        
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
        level1ButtonActive = new Texture("level1.png");
        level1ButtonInactive = new Texture("level1(2).png");
        level2ButtonActive = new Texture("level2.png");
        level2ButtonInactive = new Texture("level2(2).png");
        level3ButtonActive = new Texture("level3.png");
        level3ButtonInactive = new Texture("level3(2).png"); 
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
        
        //Reimposto il colore normale, poiche' se il gioco e' gia' stato completato una volta, l'opacita' era impostata a 0.5f
        game.batch.setColor(gamecolor);
                
        //Background
        game.batch.draw(MenuBackground1, 0, 0, SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT);
        
        //Spidey
        game.batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true), 426,300,150,200);
        
        //Title
        game.batch.draw(Title,200,600,700,130);
        
        //Calcolo il posiziomento dei 4 per utilizzare le immagini colorate (hover)
        
        //Level1
        int x = SpiderEnemies.WIDTH / 2 - LEVEL1_BUTTON_WIDTH / 2 ;  //Posizione centrale per il bottone 1
        if(game.camera.getInputInGameWorld().x < x + LEVEL1_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < LEVEL1_BUTTON_Y + LEVEL1_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > LEVEL1_BUTTON_Y){
            game.batch.draw(level1ButtonActive, x, LEVEL1_BUTTON_Y, LEVEL1_BUTTON_WIDTH, LEVEL1_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){  //Gestisco il primo bottone se cliccato
                this.dispose();
                lv = 1;
                setLevel(lv);  //Richiamo il setter per aggiornare lv1 che e' private
                game.setScreen(new LoadingScreen(game, getLevel()));  //Passa alla spiegazione del livello selezionato. lv viene aggiornato grazie a getLevel()
            }
        } else {
            game.batch.draw(level1ButtonInactive, x, LEVEL1_BUTTON_Y, LEVEL1_BUTTON_WIDTH, LEVEL1_BUTTON_HEIGHT);
        }
        
        //Level2
        int k = SpiderEnemies.WIDTH / 2 - LEVEL2_BUTTON_WIDTH / 2 + 200;  //Posizione che preferisco per il bottone 2
        if(game.camera.getInputInGameWorld().x < k + LEVEL2_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > k && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < LEVEL2_BUTTON_Y + LEVEL2_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > LEVEL2_BUTTON_Y){
            game.batch.draw(level2ButtonActive, 210, LEVEL2_BUTTON_Y, LEVEL2_BUTTON_WIDTH, LEVEL2_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){  //Gestisco il primo bottone se cliccato
                lv = 2;
                setLevel(lv);  //Richiamo il setter per aggiornare lv1 che e' private
                game.setScreen(new LoadingScreen(game, getLevel()));  //Passa alla spiegazione del livello selezionato. lv viene aggiornato grazie a getLevel()
            }
        } else {
            game.batch.draw(level2ButtonInactive, 210, LEVEL2_BUTTON_Y, LEVEL2_BUTTON_WIDTH, LEVEL2_BUTTON_HEIGHT);
        }
        
        //Level3
        int z = SpiderEnemies.WIDTH / 2 - LEVEL3_BUTTON_WIDTH / 2 - 200;  //Posizione che preferisco per il bottone 3
        if(game.camera.getInputInGameWorld().x < z + LEVEL3_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > z && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < LEVEL3_BUTTON_Y + LEVEL3_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > LEVEL3_BUTTON_Y){
            game.batch.draw(level3ButtonActive, 610, LEVEL3_BUTTON_Y, LEVEL3_BUTTON_WIDTH, LEVEL3_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){  //Gestisco il primo bottone se cliccato
                lv = 3;
                setLevel(lv);  //Richiamo il setter per aggiornare lv1 che e' private
                game.setScreen(new LoadingScreen(game, getLevel()));  //Passa alla spiegazione del livello selezionato. lv viene aggiornato grazie a getLevel()
            }
        } else {
            game.batch.draw(level3ButtonInactive, 610, LEVEL3_BUTTON_Y, LEVEL3_BUTTON_WIDTH, LEVEL3_BUTTON_HEIGHT);
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
                ChatBot cb = new ChatBot();  //Apri la chat
            }
        } else {
            game.batch.draw(chatbotButtonInactive, 870, CHATBOT_BUTTON_Y, CHATBOT_BUTTON_WIDTH, CHATBOT_BUTTON_HEIGHT);
        }           
                        
	game.batch.end();
        
    }
   
    //Incapsulamento (getter) variabile lv1 che mi servira' per fare il LoadingScreen del livello selezionato
    public int getLevel(){
        return lv;
    }   
    
    //Incapsulamento (setter) variabile lv1 che mi servira' su BonusLevel per poter scegliere un nuovo livello a fine gioco
    public void setLevel(int lv){
        this.lv = lv;
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