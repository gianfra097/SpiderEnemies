package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.SpiderEnemies;

public class Market implements Screen{
    
    private BitmapFont font;
    
    //Creo classi per passare dimensioni bottoni
    private static final int BACK_BUTTON_WIDTH = 150;
    private static final int BACK_BUTTON_HEIGHT = 70;
    private static final int BACK_BUTTON_Y = 710;
    
    private static final int CENTO_BUTTON_WIDTH = 90;
    private static final int CENTO_BUTTON_HEIGHT = 40;
    private static final int CENTO_BUTTON_Y = 380;
    private static final int DUECENTO_BUTTON_WIDTH = 90;
    private static final int DUECENTO_BUTTON_HEIGHT = 40;
    private static final int DUECENTO_BUTTON_Y = 220;
    private static final int TRECENTO_BUTTON_WIDTH = 90;
    private static final int TRECENTO_BUTTON_HEIGHT = 40;
    private static final int TRECENTO_BUTTON_Y = 220;
    
    //Buttons
    private Texture backButtonActive;
    private Texture backButtonInactive;
    private Texture centoButtonActive;
    private Texture centoButtonInactive;
    private Texture duecentoButtonActive;
    private Texture duecentoButtonInactive;
    private Texture trecentoButtonActive;
    private Texture trecentoButtonInactive;
    
    //Immagini items shop
    private Texture cursor2;
    private Texture cursor3;
    private Texture spideybonus;
    private Texture bonuslevel2;
    
    //Background
    private Texture MarketBackground;
    
    //Title
    private Texture Title;
    
    //Coins
    private Texture Coins;
    
    //Variabili che tengono conto dell'acquisto
    private int shop_cursor2 = 0;
    private int shop_cursor3 = 0;
    
    //Public perche' tengono conto dell'acquisto e quindi vengono utilizzate su BonusLevel
    public static int shop_spidey = 0;
    public static int shop_bonuslevel = 0;
    
    private SpiderEnemies game;

    public Market(SpiderEnemies game) {
        this.game = game;
    }

    @Override
    public void show() {
        
        //Carico il font per scrivere lo stato dell'audio
        font = new BitmapFont(Gdx.files.internal("myfont.fnt"));
        
        //Buttons
        backButtonActive = new Texture("back1.png");
        backButtonInactive = new Texture("back2.png");
        centoButtonActive = new Texture("100a.png");
        centoButtonInactive = new Texture("100i.png");
        duecentoButtonActive = new Texture("200a.png");
        duecentoButtonInactive = new Texture("200i.png");
        trecentoButtonActive = new Texture("300a.png");
        trecentoButtonInactive = new Texture("300i.png");
        
        //Immagini items shop
        cursor2 = new Texture("cursor2.png");
        cursor3 = new Texture("cursor3.png");
        spideybonus = new Texture("spideybonus.png");
        bonuslevel2 = new Texture("bonuslevel2.png");
        
        //Background
        MarketBackground = new Texture("MarketBackground.png");
        
        //Title
        Title = new Texture("title.png");
        
        //Coins
        Coins = new Texture("coins.png");

    }
    
    //Funzione per calcolare i coins dopo l'acquisto
    private int calculate_coins(int less){
        game.coins = game.coins - less;
        return game.coins;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
	game.batch.begin();    
        
        //Background
        game.batch.draw(MarketBackground, 0, 0, SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT);
        
        //Title
        game.batch.draw(Title,200,600,700,130);
        
        //Coins
        game.batch.draw(Coins,450,470,100,100);
        font.getData().setScale(0.8f,0.8f);
        font.draw(game.batch,String.format("="),540,535);
        font.draw(game.batch,String.format("%03d",game.coins),565,530);  //Stampo i coins
        
        //Items
        game.batch.draw(cursor2,370,410,50,50);
        game.batch.draw(cursor3,650,415,70,70);
        game.batch.draw(spideybonus,355,250,90,100);
        game.batch.draw(bonuslevel2,620,250,130,90);
        
        
        //Back
        int x = SpiderEnemies.WIDTH / 2 - BACK_BUTTON_WIDTH / 2 + 420;
        if(game.camera.getInputInGameWorld().x < x + BACK_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < BACK_BUTTON_Y + BACK_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > BACK_BUTTON_Y){
            game.batch.draw(backButtonActive, 17, BACK_BUTTON_Y-10, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
            if (Gdx.input.justTouched()){  //Gestisco il pulsante play, se cliccato
                this.dispose();
                game.setScreen(new Options(game));  //Passa alle opzioni
            }
        } else {
            game.batch.draw(backButtonInactive, 17, BACK_BUTTON_Y-10, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        }
        
        //Bottone 100 per il puntatore a sinistra, solo se game.cursor2 e' uguale a 0 (cioe' non e' stato acquistato)
        if(shop_cursor2 == 0){
            x = SpiderEnemies.WIDTH / 2 - CENTO_BUTTON_WIDTH / 2 + 115;
            if(game.camera.getInputInGameWorld().x < x + CENTO_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < CENTO_BUTTON_Y + CENTO_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > CENTO_BUTTON_Y){
                game.batch.draw(centoButtonActive, 350, CENTO_BUTTON_Y-10, CENTO_BUTTON_WIDTH, CENTO_BUTTON_HEIGHT);
                if (Gdx.input.justTouched()){  //Gestisco il pulsante play, se cliccato
                    this.dispose();
                    if(game.coins >= 100){  //Se si possiedono almeno 100 coins, acquisto
                        calculate_coins(100);  //Sottraggo i coins tramite la funzione calculate_coins
                        shop_cursor2 = 1;  //Variabile che tiene traccia dell'acquisto
                        Pixmap pm = new Pixmap(Gdx.files.internal("cursor2.png"));  //Cambio il cursore in quello desiderato
                        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
                        pm.dispose();
                    }              
                }
            } else {
                game.batch.draw(centoButtonInactive, 350, CENTO_BUTTON_Y-10, CENTO_BUTTON_WIDTH, CENTO_BUTTON_HEIGHT);
            }            
        } else {
            font.getData().setScale(0.5f,0.5f);
            font.draw(game.batch,"ACQUISTATO!",320,400);
        }

        
        //Bottone 100 per il puntatore a destra solo se game.cursor3 e' uguale a 0 (cioe' non e' stato acquistato)
        if(shop_cursor3 == 0){
            x = SpiderEnemies.WIDTH / 2 - CENTO_BUTTON_WIDTH / 2 - 176;
            if(game.camera.getInputInGameWorld().x < x + CENTO_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < CENTO_BUTTON_Y + CENTO_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > CENTO_BUTTON_Y){
                game.batch.draw(centoButtonActive, 640, CENTO_BUTTON_Y-10, CENTO_BUTTON_WIDTH, CENTO_BUTTON_HEIGHT);
                if (Gdx.input.justTouched()){  //Gestisco il pulsante play, se cliccato
                    this.dispose();
                    if(game.coins >= 100){ //Se si possiedono almeno 100 coins, acquisto
                        calculate_coins(100); //Sottraggo i coins tramite la funzione calculate_coins
                        shop_cursor3 = 1; //Variabile che tiene traccia dell'acquisto
                        Pixmap pm = new Pixmap(Gdx.files.internal("cursor3.png"));  //Cambio il cursore in quello desiderato
                        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
                        pm.dispose();
                    }    
                }
            } else {
                game.batch.draw(centoButtonInactive, 640, CENTO_BUTTON_Y-10, CENTO_BUTTON_WIDTH, CENTO_BUTTON_HEIGHT);
            }
        } else {
            font.getData().setScale(0.5f,0.5f);
            font.draw(game.batch,"ACQUISTATO!",610,400);
        }
        
        //Bottone 300 
        if(shop_spidey == 0){
            x = SpiderEnemies.WIDTH / 2 - CENTO_BUTTON_WIDTH / 2 + 115;
            if(game.camera.getInputInGameWorld().x < x + TRECENTO_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < TRECENTO_BUTTON_Y + TRECENTO_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > TRECENTO_BUTTON_Y){
                game.batch.draw(trecentoButtonActive, 350, TRECENTO_BUTTON_Y-10, TRECENTO_BUTTON_WIDTH, TRECENTO_BUTTON_HEIGHT);
                if (Gdx.input.justTouched()){  //Gestisco il pulsante play, se cliccato
                    this.dispose();
                    if(game.coins >= 300){
                        game.coins = game.coins - 300;
                        shop_spidey = 1;
                    }    
                }
            } else {
                game.batch.draw(trecentoButtonInactive, 350, TRECENTO_BUTTON_Y-10, TRECENTO_BUTTON_WIDTH, TRECENTO_BUTTON_HEIGHT);
            }
        } else {
            font.getData().setScale(0.5f,0.5f);
            font.draw(game.batch,"ACQUISTATO!",320,225); 
        }
        
        //Bottone 200
        if(shop_bonuslevel == 0){
            x = SpiderEnemies.WIDTH / 2 - CENTO_BUTTON_WIDTH / 2 - 176;
            if(game.camera.getInputInGameWorld().x < x + DUECENTO_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < DUECENTO_BUTTON_Y + DUECENTO_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > DUECENTO_BUTTON_Y){
                game.batch.draw(duecentoButtonActive, 640, DUECENTO_BUTTON_Y-10, DUECENTO_BUTTON_WIDTH, DUECENTO_BUTTON_HEIGHT);
                if (Gdx.input.justTouched()){  //Gestisco il pulsante play, se cliccato
                    this.dispose();
                    if(game.coins >= 200){
                        game.coins = game.coins - 200;
                        shop_bonuslevel = 1;
                    }    
                }
            } else {
                game.batch.draw(duecentoButtonInactive, 640, DUECENTO_BUTTON_Y-10, DUECENTO_BUTTON_WIDTH, DUECENTO_BUTTON_HEIGHT);
            }
        } else {
            font.getData().setScale(0.5f,0.5f);
            font.draw(game.batch,"ACQUISTATO!",610,225); 
        }
        
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        
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
