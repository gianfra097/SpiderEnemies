package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.Level1;
import com.mygdx.game.Level2;
import com.mygdx.game.Level3;
import com.mygdx.game.SpiderEnemies;

public class LoadingScreen implements Screen{
    
    private Texture LoadingScreenBackground;
    private Stage Background;  //Serve per il fadein
    
    private SpiderEnemies game;
    
    //Variabile livello final non puo' essere cambiata in quanto prende il valore che si ha nella classe SecondMenu
    private final int lv;
    
    public LoadingScreen (SpiderEnemies game, int getLevel){
        this.game = game;     
        lv = getLevel; //lv prende il valore del return di getLevel()
    }

    @Override
    public void show() {
  
        if (lv == 1){    //Applico l'incapsulamento con getter
            LoadingScreenBackground = new Texture("SpiegazioneGiocolv1.png");
            Background = new Stage(new ExtendViewport(SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT));
            Image image = new Image(LoadingScreenBackground);
            Background.addActor(image);
            Background.addAction(
                // Creo un azione per il fadeIn
                Actions.sequence(
                        Actions.alpha(0.0f),
                        Actions.fadeIn(2.0f)
                )
            );
        } else if(lv == 2){
            LoadingScreenBackground = new Texture("SpiegazioneGiocolv2.png");
            Background = new Stage(new ExtendViewport(SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT));
            Image image = new Image(LoadingScreenBackground);
            Background.addActor(image);
            Background.addAction(
                // Creo un azione per il fadeIn
                Actions.sequence(
                        Actions.alpha(0.0f),
                        Actions.fadeIn(2.0f)
                )
            );
        } else if(lv == 3){
            LoadingScreenBackground = new Texture("SpiegazioneGiocolv3.png");
            Background = new Stage(new ExtendViewport(SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT));
            Image image = new Image(LoadingScreenBackground);
            Background.addActor(image);
            Background.addAction(
                // Creo un azione per il fadeIn
                Actions.sequence(
                        Actions.alpha(0.0f),
                        Actions.fadeIn(2.0f)
                )
            );
        }        
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	game.batch.begin();
        
        Background.act(); // Fa funzionare le "Actions"
        Background.draw(); // Renderizza lo stage, cioe lo sfondo in questo caso
        
        if (lv == 1 && Gdx.input.isKeyJustPressed(Keys.ENTER)){  //Se lv1 = 1 (quindi l'utente ha selezionato lv1) e preme invio
            game.setScreen(new Level1(game));  //Passa al primo livello
        } else if (lv == 2 && Gdx.input.isKeyJustPressed(Keys.ENTER)){  //Se lv2 = 1 (quindi l'utente ha selezionato lv2) e preme invio
            game.setScreen(new Level2(game));  //Passa al secondo livello
        } else if (lv == 3 && Gdx.input.isKeyJustPressed(Keys.ENTER)){  //Se lv3 = 1 (quindi l'utente ha selezionato lv3) e preme invio
            game.setScreen(new Level3(game));  //Passa al terzo livello)
        }
        
	game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        Background.getViewport().update(width,height);  //Aggiorniamo il viewport
        Background.getCamera().position.set(SpiderEnemies.WIDTH/2, SpiderEnemies.HEIGHT/2, 0);  //Centriamo l'immagine
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
