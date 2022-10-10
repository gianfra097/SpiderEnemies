package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

//Classe che viene richiamata per eseguire l'animazione di eliminazione di ogni singolo nemico

public class DestroyAnimation {
    //Variabili per l'animazione di ogni personaggio
    private Texture destroy_animation;
    private TextureRegion[] animationFrames;
    
    //Variabili pubbliche perche' utilizzate nei livelli
    public Animation animation;   
    public boolean drawAnimation;   
    public float elapsedTime;
    public Vector2 ActualPosition;
    
    public DestroyAnimation(Texture img){
        drawAnimation = false; //Serve a rendere attiva o inattiva l'animazione
        ActualPosition = new Vector2(-1000,-1000);  //Posizione per far sparire il nemico
        //Per ogni nemico esegui l'animazione quando viene cliccato
        destroy_animation = img;
        TextureRegion[][] tmpFrames = TextureRegion.split(destroy_animation,destroy_animation.getWidth()/2,destroy_animation.getHeight()/2);
        animationFrames = new TextureRegion[4];
        int index = 0;
        for (int i=0; i<2; i++){
            for (int j=0; j<2; j++){
                animationFrames[index++] = tmpFrames[i][j];
            }
        }
        animation = new Animation(0.13f, (Object[]) animationFrames);    
        elapsedTime = 0;
    }
    
}
