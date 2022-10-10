package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.SpiderEnemies;
import static com.mygdx.game.screen.Options.mute;

//Classe relativa ai bottoni che appaiono in endgame
public class EndGame {
    
    //Bottoni play ed exit, tutti utilizzati poi su BonusLevel
    public int PLAY_BUTTON_WIDTH = 180;
    public int PLAY_BUTTON_HEIGHT = 80;
    public int EXIT_BUTTON_WIDTH = 180;
    public int EXIT_BUTTON_HEIGHT = 85;
    public int PLAY_BUTTON_Y = 330;
    public int EXIT_BUTTON_Y = 230;
    
    public Texture playButtonActive;
    public Texture playButtonInactive;
    public Texture exitButtonActive;
    public Texture exitButtonInactive;
    
    public int x;
    
    private SpiderEnemies game;
    
    public EndGame(SpiderEnemies game){
        this.game = game;
        
        //Bottoni play ed exit
        playButtonActive = new Texture("play1.png");
        playButtonInactive = new Texture("play2.png");
        exitButtonActive = new Texture("exit1.png");
        exitButtonInactive = new Texture("exit2.png");
        
        x = SpiderEnemies.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2 ;
        
    }
    
}
