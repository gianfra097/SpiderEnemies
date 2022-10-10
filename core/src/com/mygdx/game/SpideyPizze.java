package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

//Classe che viene richiamata per eseguire l'animazione di peter sul motorino

public class SpideyPizze {
    //Variabili per l'animazione e velocita'
    private Texture spidey_animation;
    private TextureRegion[] animationFrames;
    private float speed;
    private float stop_delta;
    
    //Variabili per l'animazione usate anche su BonusLevel
    public Animation animation;
    public float elapsedTime;
    public Rectangle spideybox;
    
    public SpideyPizze(Texture img, float speed){
        
        this.speed = speed;
        spideybox = new Rectangle();
        
        //Per ogni nemico esegui l'animazione quando viene cliccato
        spidey_animation = img;
        TextureRegion[][] tmpFrames = TextureRegion.split(spidey_animation,spidey_animation.getWidth()/2,spidey_animation.getHeight()/2);
        animationFrames = new TextureRegion[4];
        int index = 0;
        for (int i=0; i<2; i++){
            for (int j=0; j<2; j++){
                animationFrames[index++] = tmpFrames[i][j];
            }
        }
        animation = new Animation(0.25f, (Object[]) animationFrames);    
        elapsedTime = 0;
    }
    
    public void update(float delta){
        if(Gdx.input.isKeyPressed(Keys.LEFT)){
            
            //Se il personaggio si trova fuori dalla mappa a sinistra, bloccalo
            if(spideybox.x + spideybox.width < 158){
                stop_delta = delta - 0.2f;
                spideybox.x = speed*stop_delta;
            } else {  //Altrimenti fallo scorrere a sinistra
                spideybox.x-= speed*delta;
            }
        } 
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            
            //Se il personaggio si trova fuori dalla mappa a destra, bloccalo
            if(spideybox.x + spideybox.width > 1050){
                stop_delta = delta + 3.4f;
                spideybox.x = speed*stop_delta;
            } else { //Altrimenti fallo scorrere a destra
                spideybox.x+= speed*delta;
            }
        } 
    }
    
}
