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
import com.mygdx.game.BonusLevel;
import com.mygdx.game.SpiderEnemies;

public class LoadingBonus implements Screen{
    
    private Texture LoadingBonusBackground;
    private Stage Background;  //Serve per il fadein
    public int punteggio;
    
    private Market market;
    
    private SpiderEnemies game;
    
    public LoadingBonus (int punteggio, SpiderEnemies game){
        this.punteggio = punteggio;
        this.game = game;     
    }

    @Override
    public void show() {
        
        if(market.shop_spidey == 0 && market.shop_bonuslevel == 0){
            LoadingBonusBackground = new Texture("SpiegazioneBonus.png");
        } else if(market.shop_spidey == 1 && market.shop_bonuslevel == 0){
            LoadingBonusBackground = new Texture("SpiegazioneBonus2.png");
        } else if(market.shop_bonuslevel == 1 && market.shop_spidey == 0) {
            LoadingBonusBackground = new Texture("SpiegazioneBonus3.png");
        } else if(market.shop_spidey == 1 && market.shop_bonuslevel == 1){
            LoadingBonusBackground = new Texture("SpiegazioneBonus4.png");
        }
        
        
        Background = new Stage(new ExtendViewport(SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT));
        Image image = new Image(LoadingBonusBackground);
        Background.addActor(image);
        
        Background.addAction(
            // Creo un azione per il fadeIn
            Actions.sequence(
                Actions.alpha(0.0f),
                Actions.fadeIn(2.0f)
            )
        ); 
        
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	game.batch.begin();
        
        Background.act(); // Fa funzionare le "Actions"
        Background.draw(); // Renderizza lo stage, cioe lo sfondo in questo caso
        
        if (Gdx.input.isKeyJustPressed(Keys.ENTER)){  //Se lv1 = 1 (quindi l'utente ha selezionato lv1) e preme invio
            game.setScreen(new BonusLevel(punteggio, game));  //Passa al primo livello
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
