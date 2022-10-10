package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.SpiderEnemies;
import com.mygdx.game.tools.DataBase;
import java.sql.SQLException;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Results implements Screen{
    
    //Creo classi per passare dimensioni bottoni
    private static final int BACK_BUTTON_WIDTH = 150;
    private static final int BACK_BUTTON_HEIGHT = 70;
    private static final int BACK_BUTTON_Y = 710;
    
    //Buttons
    private Texture backButtonActive;
    private Texture backButtonInactive;
    
    //Inizializzo le variabili che mi serviranno per stampare il tempo e il punteggio
    private BitmapFont font;
        
    private SpiderEnemies game;
    
    //Background
    private Texture ResultsBackground;
    
    //Title
    private Texture Title;
    
    //Trofei
    private Texture firsttrophy;
    private Texture secondtrophy;
    private Texture thirdtrophy;
    
    //Variabile che salva gli score del db per poi stamparli nel render
    private String scores;    

    public Results(SpiderEnemies game) {
        this.game = game;
    }

    @Override
    public void show() {
        
        //Buttons
        backButtonActive = new Texture("back1.png");
        backButtonInactive = new Texture("back2.png");
        
        //Background
        ResultsBackground = new Texture("ResultsBackground.png");
        
        //Title
        Title = new Texture("title.png");
        
        firsttrophy = new Texture("firsttrophy.png");
        secondtrophy = new Texture("secondtrophy.png");
        thirdtrophy = new Texture("thirdtrophy.png");
        
        //Variabili che mi servono per disegnare il tempo che scorre nella barra in alto
        font = new BitmapFont(Gdx.files.internal("myfont.fnt"));
        
        try{
            String url = "jdbc:mysql://localhost/spiderenemies?";
            String user_data = "user=gianfranco&password=root";
            DataBase db = new DataBase(url,user_data);
            db.open();
            scores = db.getScores();  //Salvo i dati nella variabile scores
            //System.exit(0);
            db.close();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
	game.batch.begin();
        
        //Background
        game.batch.draw(ResultsBackground, 0, 0, SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT);
        
        //Title
        game.batch.draw(Title,200,600,700,130);
        
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
        
        //Compaiono a schermo i migliori 10 punteggi
        font.getData().setScale(0.8f,0.8f);
        font.draw(game.batch,"BEST SCORES: ",320,530);
        font.draw(game.batch,scores,320,480);
        
        //Trofei
        game.batch.draw(firsttrophy,252,450,50,50);
        game.batch.draw(secondtrophy,252,390,50,50);
        game.batch.draw(thirdtrophy,252,335,50,50);
        //Immagine a 270
        
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
