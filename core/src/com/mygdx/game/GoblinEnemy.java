package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import static com.mygdx.game.SpideyHero.rewind;

public class GoblinEnemy extends Characters{
   
    //Goblin animation
    private Texture goblin_animate;
    private TextureRegion[] animationFrames;
    
    //Variabili animazione usate su BonusLevel
    public Animation animation;
    public float elapsedTime;
    public Rectangle goblinbox;  //Rettangolo che mi servira' per muovere Goblin
   
    public GoblinEnemy (Texture img){

        goblin_animate = img;
        goblinbox = new Rectangle();
       
        //Goblin animation
        TextureRegion[][] tmpFrames = TextureRegion.split(goblin_animate,goblin_animate.getWidth()/2,goblin_animate.getHeight()/2);
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
        //Se anche spider e' fuori dal quadrato di gioco, allora ricomincia la corsa
        if(rewind == true){
            goblinbox.x = 0;
        }
               
        //Se Goblin e' fuori dal quadrato di gioco, nascondilo fornendogli una posizione lontana
        if(goblinbox.x > 850){
            goblinbox.x = 3000;
        }
        else{
            goblinbox.x += 10;  //Muovo Goblin nell'asse x se e' all'interno del quadrato di gioco
        }

    }

}