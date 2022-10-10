package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.screen.LoadingBonus;
import static com.mygdx.game.screen.Options.mute;

public class Level2 implements Screen{
    
    private int i;

    //Inizializzo le variabili che mi serviranno per stampare il tempo e il punteggio
    private BitmapFont font;

    //Creo l'array che contiene i nemici e l'array di posizioni
    private Array<Enemy> enemies; 
    private Array<Vector2> spawn; 
    
    //Serve a far partire il nemico piu' in basso per non far vedere la testa    
    public static final float ENEMIES_HEIGHT = 60;

    //Timer e punteggio. 
    private long time = 0;
    private long timerlevel = 60;
    private long k;  
    private long add_time = 0; //Ogni 1000 punti fornisce +1 secondi per continuare a giocare
    public int punteggio;   //Utilizzo su BonusLevel e serve a memorizzarsi in classifica
    private boolean one_second; 
    private boolean one_animation;
    
    //Variabile per calcolare i coins
    private int add_coins = 0;
    
    //Variabile barra punteggio e variabile sfondo
    private Texture Barra;    
    private Texture BackgroundLevel2;
    
    //Variabili per le hitbox
    private ShapeRenderer tavolaRettangolo;
    private Rectangle rectangleMouse;
    private Vector3 mousePosition;
    
    //Variabili audio
    private Sound sound;
    private Music music;
    
    private SpiderEnemies game;
    
    public Level2 (SpiderEnemies game){
        this.game = game;
    }
    
    //Inizializzo l'array spawn con le varie posizioni
    public void makeSpawn(){
        spawn = new Array<Vector2>();
        spawn.add(new Vector2(45,202-ENEMIES_HEIGHT));
        spawn.add(new Vector2(207,255-ENEMIES_HEIGHT));
        spawn.add(new Vector2(372,255-ENEMIES_HEIGHT));
        spawn.add(new Vector2(320,167-ENEMIES_HEIGHT));
        spawn.add(new Vector2(264,80-ENEMIES_HEIGHT)); 
        spawn.add(new Vector2(485,167-ENEMIES_HEIGHT));
        spawn.add(new Vector2(648,205-ENEMIES_HEIGHT));
        spawn.add(new Vector2(648,93-ENEMIES_HEIGHT));
        spawn.add(new Vector2(868,150-ENEMIES_HEIGHT));
    }  
    
    //Inizializzo l'array enemy pasando i valori richiesti
    public void makeEnemies() {
        enemies = new Array<Enemy>();
        enemies.add(new Enemy(new Sprite(new Texture("mysterio.png")), spawn, 200));
        enemies.add(new Enemy(new Sprite(new Texture("ironman.png")), spawn, -200));
        enemies.add(new Enemy(new Sprite(new Texture("rhyno.png")), spawn, 200));
        enemies.add(new Enemy(new Sprite(new Texture("venom.png")), spawn, 200));
        enemies.add(new Enemy(new Sprite(new Texture("scorpion.png")), spawn, 150));
        enemies.add(new Enemy(new Sprite(new Texture("electro.png")), spawn, 150));
        enemies.add(new Enemy(new Sprite(new Texture("goblin.png")), spawn, 150));
        enemies.add(new Enemy(new Sprite(new Texture("mj.png")), spawn, -200));
        enemies.add(new Enemy(new Sprite(new Texture("ned.png")), spawn, -200));        
    }
    
    @Override
    public void show() {

        //Variabili che mi servono per disegnare il tempo che scorre nella barra in alto
        font = new BitmapFont(Gdx.files.internal("myfont.fnt"));
        font.getData().markupEnabled = true;  //Permette di cambiare colore per ogni testo        
        
        //Carico la barra e lo sfondo
        Barra = new Texture("barra.png");
        BackgroundLevel2 = new Texture("BackgroundLevel2.png");  
        
        //Inizializzo il tempo corrente
        time = TimeUtils.nanoTime();
        
        //Punteggio
        punteggio = 0;
        one_second = false;
        one_animation = false;
        
        //Click per le hitbox
        mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        tavolaRettangolo = new ShapeRenderer();
        game.camera.camera.unproject(mousePosition);
        rectangleMouse = new Rectangle(mousePosition.x, mousePosition.y, 5, 5);
        
        //Suoni e Musica
        game.music.stop();  //Stoppo la musica del menu'
        
        sound = Gdx.audio.newSound(Gdx.files.internal("webshooter.mp3"));  //Colpo ragnatela
        music = Gdx.audio.newMusic(Gdx.files.internal("levelmusic.mp3"));  //Musica livello
        
        //Se il giocatore ha cliccato l'icona per mutare il gioco su options, mute sara' 1, quindi l'audio del livello sara' 0
        if(mute == 1){ 
            music.setVolume(0.0f);
        } else {  //Altrimenti inizializza il volume della musica
            music.setVolume(0.2f);
            music.setLooping(true);
            music.play();
        }
        
        //Richiamo le funzioni per eseguire gli add delle posizioni(Spawn) e poi dei nemici(Enemies)
        makeSpawn();
        makeEnemies();        
        
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.camera.camera.unproject(mousePosition);
        rectangleMouse.setPosition(mousePosition.x, mousePosition.y);          

        //Se e' passato 1 secondo allora diminuisci il timer
        if (TimeUtils.timeSinceNanos(time) > 1000000000) { 
            timerlevel--;
            if(timerlevel == 0){
                music.stop();  //Stoppo la musica del livello
                game.setScreen(new LoadingBonus(punteggio, game));
            }
            time = TimeUtils.nanoTime();  //Aggiorna il tempo corrente
        }

	game.batch.begin();
        
        //Creo un ciclo for per stampare i nemici
        for (i=0;i < enemies.size; i++){
            Enemy enemy = enemies.get(i);  //Per comodita', invece di scrivere "enemies.get(i).sprite" ecc, creo una variabile enemy
            enemy.update();
            if(enemy.animationIsPlaying){
                //Stampo l'animazione 
                enemy.elapsedTime+=Gdx.graphics.getDeltaTime();  //Tempo che fa scorrere l'animazione
                game.batch.draw((TextureRegion) enemy.animation.getKeyFrame(enemy.elapsedTime,true), enemy.ActualPosition.x,enemy.ActualPosition.y,60,60);
                if(enemy.destroyEnemy(rectangleMouse)){   //Se il nemico viene distrutto, cioe' il metodo restituisce true
                                        
                    //Suono della ragnatela
                    long id = sound.play(0.2f);
                    if(mute == 1){ //Se il giocatore ha cliccato l'icona per mutare il gioco su options, mute sara' 1, quindi l'effetto sara' 0
                        sound.setVolume(id,0.0f);
                    } else{
                        sound.setPitch(id, 2);
                        sound.setLooping(id, false);
                    }
                    
                    //Impostiamo drawAnimation = true per attivare l'animazione
                    enemy.hitAnimation.drawAnimation = true;
                    
                    switch(enemy.x){
                        case -200:  //Se e' -200 togli 200 solo se il punteggio lo permette (non vado in negativo)
                            if(punteggio>=200){
                                punteggio = punteggio - 200;
                                break;                                
                            } else{
                                punteggio = 0;
                                break;
                            }
                        case 150: //Altrimenti aggiungi 150 o 200
                            punteggio = punteggio + 150;
                            break;
                        case 200:
                            punteggio = punteggio + 200;
                            break;
                    }
                    
                    //Se il punteggio e' maggiore o uguale a 0+1000, 1000+1000, 2000+1000 ecc, aggiungi +1 al tempo
                    if(punteggio >= add_time + 1000){ 
                        add_time = add_time + 1000;
                        timerlevel = timerlevel + 1;
                        k = timerlevel - 2;  //Salvo timerlevel - 2 nella variabile k che mi servira' per mostrare il +1 per almeno 2 secondi 
                        one_second = true;  //On-Off mostra +1 sullo schermo
                    }  
                    
                    //Ogni volta che il punteggio aumenta di 3000 punti, aggiungo +50 ai coins
                    if(punteggio >= add_coins + 3000){
                        add_coins = add_coins + 3000;
                        game.coins = game.coins + 50;
                    }
                      
                }
            }
            if(enemy.hitAnimation.drawAnimation){  //Se l'animazione e' stata attivata, stampala
                enemy.hitAnimation.elapsedTime+=Gdx.graphics.getDeltaTime();
                game.batch.draw((TextureRegion) enemy.hitAnimation.animation.getKeyFrame(enemy.hitAnimation.elapsedTime,true), enemy.hitAnimation.ActualPosition.x+5,enemy.hitAnimation.ActualPosition.y,50,50);
                
                //Se l'animazione e' attiva stampa anche il numero di punti acquisiti cliccando il nemico
                if(enemy.x == 150){
                    font.getData().setScale(.25f,.25f);
                    font.draw(game.batch,"[GREEN]+150",enemy.hitAnimation.ActualPosition.x+10,enemy.hitAnimation.ActualPosition.y+48);
                } else if(enemy.x == 200){
                    font.getData().setScale(.25f,.25f);
                    font.draw(game.batch,"[GREEN]+200",enemy.hitAnimation.ActualPosition.x+10,enemy.hitAnimation.ActualPosition.y+48);                    
                } else if(enemy.x == -200){
                    font.getData().setScale(.25f,.25f);
                    font.draw(game.batch,"[RED]-200",enemy.hitAnimation.ActualPosition.x+10,enemy.hitAnimation.ActualPosition.y+48);                    
                }
                
                //Se l'animazione e' finita, poni a false drawAnimation e reimposta elapsedTime in modo da eseguire la successiva
                if(enemy.hitAnimation.animation.isAnimationFinished(enemy.hitAnimation.elapsedTime)){
                    enemy.hitAnimation.drawAnimation = false;
                    enemy.hitAnimation.elapsedTime=0;
                }
            }
        }   
        
        //Background
        game.batch.draw(BackgroundLevel2, 0, 0, SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT);
        
        //Barra conta punteggio e tempo
        game.batch.draw(Barra, 0, 0, SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT);
       
        //Se two_seconds e' true (cioe' e' stato aggiunto 1 secondo), visualizza "+1" accanto al tempo
        if(one_second){ 
            font.getData().setScale(0.5f,0.5f);
            font.draw(game.batch,"+1",815,745);  
            one_animation = true;
        } //Quando il timer, diminuisce di un secondo, togli il +2 dallo schermo
        if(one_animation == true && timerlevel == k){
            one_second = false;
        }   

        //Inserisco la grandezza del font e le variabili da stampare.
        font.getData().setScale(1,1);
        font.draw(game.batch,String.format("%02d",timerlevel),743,743);  //Stampo il tempo
        font.draw(game.batch,String.format("%08d",punteggio),75,743);  //Stampo il punteggio
        font.draw(game.batch,String.format("%03d",game.coins),510,770);  //Stampo i coins
        
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
        sound.dispose();
        game.music.dispose();
        music.dispose();
    }
    
}