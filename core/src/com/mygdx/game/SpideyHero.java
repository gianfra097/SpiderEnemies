package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class SpideyHero extends Characters{

    //Spidey animation
    private Texture spideyhero_animate;
    private TextureRegion[] animationFrames;
    
    //Variabili animazione usate su BonusLevel
    public Animation animation;
    public float elapsedTime;
    public Rectangle spideyherobox;  //Rettangolo che mi servira' per muovere spidey
   
    static boolean rewind = false;
   
    public SpideyHero (Texture img){
       
        spideyhero_animate = img;
        spideyherobox = new Rectangle();
       
        //MJ animation
        TextureRegion[][] tmpFrames = TextureRegion.split(spideyhero_animate,spideyhero_animate.getWidth()/2,spideyhero_animate.getHeight()/2);
        animationFrames = new TextureRegion[4];
        int index = 0;
        for (int i=0; i<2; i++){
            for (int j=0; j<2; j++){
                animationFrames[index++] = tmpFrames[i][j];
            }
        }
        animation = new Animation(0.40f, (Object[]) animationFrames);
        elapsedTime = 0;    
       
    }
   
    @Override
    public void Run(){
        //Se spidey e' fuori dal quadrato di gioco, ricomincia ponendo la posizione a 0 ed attivando il rewind
        if(spideyherobox.x > 850){
            spideyherobox.x = 0;
            rewind = true;
        } else{
            spideyherobox.x += 7;  //Muovo spidey nell'asse x se e' all'interno del quadrato di gioco
            rewind = false;
        }
    }
}