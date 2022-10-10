package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import static com.mygdx.game.Level1.ENEMIES_HEIGHT;

public class Enemy {
    private Sprite sprite;  
    private Array<Vector2> spawn;
    public int x; //Variabile che contiene i punti relativi al nemico (es: 150,200,-150) e mi servira' nelle classi dei livelli
    private float lowerBound;  //Valore di partenza (posizione iniziale nemico)
    private float upperBound; // Serve per farli salire nello spawn (posizione piu' in alto "finale" del nemico)
    private float velocity = 1.5f;
    private boolean goUp;    
    
    //Variabili per i personaggi. Sono utilizzate nei livelli
    public Vector2 ActualPosition;
    public int ActualEnemy;
    public boolean animationIsPlaying;    
    
    //Variabili per tempo ed intervallo di spawn
    private long timerspawn;
    private long timerWait;
    private long interval;
    
    //Variabili per l'animazione di ogni personaggio
    public Texture enemy_movement;
    public TextureRegion[] animationFrames;
    public Animation animation;
    public float elapsedTime;
    public DestroyAnimation hitAnimation;  //Si riferisce alla classe DestroyAnimation che leggera' un'immagine matrice 2x2
    
    //Variabile per creare rettangolo attorno al nemico. Mi servira' per colpirli
    public Rectangle enemy_hitbox;

    public Enemy(Sprite sprite, Array<Vector2> spawn, int x) {
        hitAnimation = new DestroyAnimation(new Texture("pow.png"));  //Classe DestroyAnimation immagine matrice
        this.sprite = sprite;
        this.spawn = spawn;
        this.x = x;
        timerspawn = TimeUtils.nanosToMillis(TimeUtils.nanoTime());  //Prendo il tempo corrente che mi servira' per lo spawn (rimane sempre lo stesso, mentre all'interno di update aumenta)
        timerWait = TimeUtils.nanosToMillis(TimeUtils.nanoTime());   //Prendo il tempo corrente che mi servira' per fare stare fermo il nemico per "x" secondi nella posizione piu' alta
        goUp = true;
        interval = MathUtils.random(1000,5000);  //Intervallo casuale per prendere un nemico casuale
        animationIsPlaying = false;
        enemy_movement = sprite.getTexture();
        enemy_hitbox = new Rectangle(-1000, 1000, 60, 80);  //Rettangolo: larghezza 60, altezza 80
        
        //Per ogni nemico prendi la texture dallo sprite ed esegui l'animazione
        TextureRegion[][] tmpFrames = TextureRegion.split(enemy_movement,enemy_movement.getWidth()/2,enemy_movement.getHeight()/2);
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
    
    //Animazione nemico colpito
    public boolean destroyEnemy(Rectangle rectangleMouse){
        if(Gdx.input.justTouched() && enemy_hitbox.overlaps(rectangleMouse)){ //Se il rettangolo del mouse e' in collisione con il rettangolo del nemico, ed e' stato cliccato
            hitAnimation.ActualPosition.set(ActualPosition); //L'animazione "pow" sara' nella posizione del nemico cliccato (cioe' l'ActualPosition di hitAnimation, quindi x e y saranno uguali ad ActualPosition del nemico)
            ActualPosition.y = lowerBound;  //Aggiorno la posizione del nemico altrimenti rimarrebbe in alto ed al prossimo spawn non partira' dal basso
            timerspawn = TimeUtils.nanosToMillis(TimeUtils.nanoTime()); //Aggiorno tempo spawn
            goUp = true; //Per entrare nel controllo e vedere se nemico deve salire o fermarsi
            spawn.add(ActualPosition);  //Aggiungo le coordinate x e y di actual position agli spawn disponibili dato che adesso e' libero
            animationIsPlaying = false;  //Animazione non piu' in corso quindi false
            interval = MathUtils.random(1000,5000);  //Nuovo intervallo di tempo casuale per lo spawn
            enemy_hitbox.setPosition(ActualPosition); //Rettangolo prende la posizione x e y
            return true;
        }
        return false;
    }
    
    public void update(){        
        if(TimeUtils.nanosToMillis(TimeUtils.nanoTime()) - timerspawn >= interval) { //Tra 1 e 5 secondi, iniziera' l'animazione del nemico
            if(!animationIsPlaying){  //Se il nemico non e' gia' durante l'animazione
                int i = MathUtils.random(0,spawn.size-1); //Numero casuale da 0 a 9 in questo caso 
                ActualPosition = spawn.removeIndex(i); //Viene considerata una posizione casuale per il nemico corrente (es: 800,400)
                lowerBound = ActualPosition.y;  //Posizione attuale piu' bassa
                upperBound = ActualPosition.y+ENEMIES_HEIGHT;  //Posizione nemico attuale + altezza per farlo vedere intero
                enemy_hitbox.setPosition(ActualPosition);
                animationIsPlaying = true;
            }
            if(goUp) {
                if (ActualPosition.y < upperBound) { //Se la posizione e' minore di upperbound
                    ActualPosition.y += velocity;    //Continua a far salire il nemico
                    timerWait = TimeUtils.nanosToMillis(TimeUtils.nanoTime());  //Aggiorna il tempo per non far fermare il nemico in anticipo
                } else if (ActualPosition.y >= upperBound) {  //Se e' maggiore
                    ActualPosition.y = upperBound;   //Il nemico si ferma
                    goUp = false;  //goUp false cosi' non sale piu' 
                }
                enemy_hitbox.setPosition(ActualPosition);
            }
            if(TimeUtils.nanosToMillis(TimeUtils.nanoTime()) - timerWait >= 3000) {  //Aggiornando sempre timerwait su update non iniziera' a scendere. Inizera' a scendere dopo 3 secondi da quando si e' fermato
                if(ActualPosition.y > lowerBound){ //Se la posizione e' maggiore di lowerbound
                    ActualPosition.y -= velocity;  //Inizia a far scendere il nemico
                }
                else if(ActualPosition.y <= lowerBound){ //Se la posizione e' minore di lowerBound
                    ActualPosition.y = lowerBound;    //Il nemico si ferma
                    timerspawn = TimeUtils.nanosToMillis(TimeUtils.nanoTime()); //Aggiorno timerspawn cosi' quando passa uno/cinque secondi ricomincia l'animazione
                    goUp = true;
                    spawn.add(ActualPosition);
                    animationIsPlaying = false;
                    interval = MathUtils.random(1000,5000);
                } 
                enemy_hitbox.setPosition(ActualPosition);
            }
        }      
    }
}