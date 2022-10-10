package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.screen.EndGame;
import com.mygdx.game.screen.Market;
import static com.mygdx.game.screen.Options.mute;
import com.mygdx.game.screen.SecondMenu;
import com.mygdx.game.tools.DataBase;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BonusLevel implements Screen{

    private int i;
  
    //Variabile per salvare il punteggio
    private int userResponse;
    private boolean inputusername;
    
    private Color worldColor;
    private Color buttonsColor;
    
    //Inizializzo le variabili che mi serviranno per stampare il tempo e il punteggio
    private BitmapFont font;
        
    //Variabile barra punteggio e variabile sfondo
    private Texture Barra;
    private Texture BackgroundBonusLevel;
    private int punteggio;
    private int stop_punteggio;
    
    //Timer 
    private long time = 0;
    private long timerlevel = 30; 
    
    private Texture texturepizza;
    
    //Contenitore pizze
    private Array<Rectangle> pizze;
    
    //Velocita' con cui scende la pizza
    private float speed;
    
    private int add_point;
    
    private Music music;
    
    //Classe SpideyPizze
    private SpideyPizze spideypizze;
    
    //Classe EndGame per visualizzare bottoni a fine gioco
    private EndGame endgame;
    
    //Variabili booleane pergestire gli eventi
    private boolean gameover;
    private boolean pick_pizza;
    private long k;
    
    private Sound sound;
    
    private Market market;
    
    //Carico le immagini per le animazioni nell'endgame e creo un array characters che comprende sia il nemico che il personaggio principale (polimorfismo)
    private GoblinEnemy goblinenemy = new GoblinEnemy(new Texture("goblin_enemy.png"));
    private SpideyHero spideyhero = new SpideyHero(new Texture("spidey_hero.png"));
    private Characters[] characters = {goblinenemy, spideyhero}; //Mi servira' per chiamare il metodo "Run()" (ereditarieta' e polimorfismo)

    private SpiderEnemies game;
    
    //Importo il punteggio del livello 1,2 o 3
    public BonusLevel(int punteggio, SpiderEnemies game){
        this.punteggio = punteggio;
        this.game = game;
    }
    
    //Crea spawn delle pizze
    public void makeSpawn(){
        Rectangle boxpizza = new Rectangle();
        boxpizza.width = 63;
        boxpizza.height = 63;
        boxpizza.x = MathUtils.random(0, game.WIDTH - boxpizza.width);  //Creo una pizza in posizione casuale nell'asse x
        boxpizza.y = (game.HEIGHT);  //Partendo dall'altezza della finestra
        pizze.add(boxpizza);
    }
 
    @Override
    public void show() {

        //Viene impostata a false quando l'utente supera la schermata di inserimento del nome per la classifica
        inputusername = false;

        //Variabili per opacita' e gameover
        gameover = false;
        pick_pizza = false;
        buttonsColor = new Color (1, 1, 1, 1); //Colore normale
        worldColor = new Color (1, 1, 1, 0.5f);  //Colore piu' trasparente

        endgame = new EndGame(game);
        
        //Variabili che mi servono per disegnare il tempo che scorre nella barra in alto
        font = new BitmapFont(Gdx.files.internal("myfont.fnt"));
        
        //Carico la barra
        Barra = new Texture("barra.png");
        
        //Carico lo sfondo in base a cio' che ha acquistato il player
        if(market.shop_bonuslevel == 0){
            BackgroundBonusLevel = new Texture("bonuslevel.png"); 
        } else {
            BackgroundBonusLevel = new Texture("bonuslevel2.png"); 
        }
          
        //Carico l'immagine in base a cio' che ha acquistato il player
        if(market.shop_spidey == 0){
            spideypizze = new SpideyPizze(new Texture("spideypizze.png"), 250);  //Passo immagine e velocita'
        } else if(market.shop_spidey == 1){
            spideypizze = new SpideyPizze(new Texture("spideypizze2.png"), 250);  //Passo immagine e velocita'
        }
        
        //Immagine pizza
        texturepizza = new Texture("pizza.png");
        
        //Dimensioni immagine peter su motorino
        spideypizze.spideybox.width = 200;
        spideypizze.spideybox.height = 100;  //Diminuisco l'altezza di peter per diminuire di conseguenza il rettangolo, utile per sistemare l'animazione della pizza nel motorino

        //Velocita' con cui scendono le pizze
        speed = 100;
        
        add_point = 0;
        
        //Creo l'array di pizze
        pizze = new Array<Rectangle>();
        
        //Inizializzo il tempo corrente
        time = TimeUtils.nanoTime();
        
        //Suoni e Musica
        game.music.stop();  //Stoppo la musica del menu'
        
        sound = Gdx.audio.newSound(Gdx.files.internal("pizzaeffect.mp3"));  //Colpo ragnatela
        music = Gdx.audio.newMusic(Gdx.files.internal("bonuslevel.mp3"));  //Musica livello
        
        //Se il giocatore ha cliccato l'icona per mutare il gioco su options, mute sara' 1, quindi l'audio del livello sara' 0
        if(mute == 1){ 
            music.setVolume(0.0f);
        } else {  //Altrimenti inizializza il volume della musica
            music.setVolume(0.2f);
            music.setLooping(true);
            music.play();
        }
        
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
               
        //Se e' passato 1 secondo allora diminuisci il timer
        if (TimeUtils.timeSinceNanos(time) > 1000000000) { 
            
            //Diminuisco il tempo, se e' uguale a 0 = GameOver
            timerlevel--;
            if(timerlevel == 0){
                music.stop();  //Stoppo la musica del livello
                gameover = true;
                inputusername = true;
            } else{
                makeSpawn();
            }      
            
            time = TimeUtils.nanoTime();  //Aggiorna il tempo corrente
        }
                
        //Se siamo nella condizione di gameover
        if(gameover){
            spideypizze.update(0);
        } else {
            //Richiamo l'update per poter muovere il nemico e gli passo il delta (f)
            spideypizze.update(f);
        }
        
        //Se gameover == true e inputusername == true, inserisci nome utente per salvare il punteggio
        if(gameover && inputusername){
            Object[] options1 = {"OK"};
            JPanel panel = new JPanel();
            panel.add(new JLabel("Nome:"));
            JTextField textField = new JTextField(10);
            panel.add(textField);
            
            userResponse = JOptionPane.showOptionDialog(null, panel, "Inserisci un nome per salvare il punteggio", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, null);
            if(userResponse == JOptionPane.OK_OPTION && !textField.getText().isEmpty()){  //Se viene inserito del testo
                JOptionPane.showMessageDialog(null, "Complimenti " + textField.getText() + ", sei stato salvato in classifica!");
                try{
                    String url = "jdbc:mysql://localhost/spiderenemies?";
                    String user_data = "user=gianfranco&password=root";
                    DataBase db = new DataBase(url,user_data);
                    db.open();
                    db.save(textField.getText(),punteggio);
                    //System.exit(0);
                    db.close();
                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }
                
            } else {  //Se l'inputbox e' vuoto
                JOptionPane.showMessageDialog(null, "Non hai salvato il tuo nome ed il tuo punteggio");
            }
            
            inputusername = false;
        
        }

        game.batch.begin();

        //Background
        game.batch.draw(BackgroundBonusLevel, 0, 0, SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT);
            
        //Se il tempo e' 0 (gameover)
        if(gameover){ 
            //Fermo il tempo a 0
            timerlevel = 0;
                
            //Faccio partire la musica del menu'
            if(mute == 0){
                game.music.play();
            }
  
            //Imposto il colore normale (non trasparente) dei bottoni, dell'input text e del font, solo se ci troviamo nella schermata di gameover
            game.batch.setColor(buttonsColor);
            font.setColor(buttonsColor);
                
            //Disegno a schermo i bottoni play ed exit
            //Play
            if(game.camera.getInputInGameWorld().x < endgame.x + endgame.PLAY_BUTTON_WIDTH && game.camera.getInputInGameWorld().x >  endgame.x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < endgame.PLAY_BUTTON_Y + endgame.PLAY_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > endgame.PLAY_BUTTON_Y){
                game.batch.draw(endgame.playButtonActive, endgame.x, endgame.PLAY_BUTTON_Y, endgame.PLAY_BUTTON_WIDTH, endgame.PLAY_BUTTON_HEIGHT);
                if (Gdx.input.isTouched()){  //Gestisco il pulsante play, se cliccato
                    this.dispose();
                    game.setScreen(new SecondMenu(game));  //Torna indietro e scegli il livello
                }            
            } else {
                game.batch.draw(endgame.playButtonInactive, endgame.x, endgame.PLAY_BUTTON_Y, endgame.PLAY_BUTTON_WIDTH, endgame.PLAY_BUTTON_HEIGHT);
            } 
                
            //Exit
            if(game.camera.getInputInGameWorld().x < endgame.x + endgame.EXIT_BUTTON_WIDTH && game.camera.getInputInGameWorld().x > endgame.x && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y < endgame.EXIT_BUTTON_Y + endgame.EXIT_BUTTON_HEIGHT && SpiderEnemies.HEIGHT - game.camera.getInputInGameWorld().y > endgame.EXIT_BUTTON_Y){
                game.batch.draw(endgame.exitButtonActive, endgame.x, endgame.EXIT_BUTTON_Y, endgame.EXIT_BUTTON_WIDTH, endgame.EXIT_BUTTON_HEIGHT);
                if (Gdx.input.isTouched()){  //Gestisco il pulsante exit, se cliccato
                    this.dispose();
                    Gdx.app.exit(); //Chiudo il gioco
                }         
            } else {
                game.batch.draw(endgame.exitButtonInactive, endgame.x, endgame.EXIT_BUTTON_Y, endgame.EXIT_BUTTON_WIDTH, endgame.EXIT_BUTTON_HEIGHT);
            }    
                
            //Compaiono a schermo il punteggio corrente, l'inserimento del nome per salvare il punteggio e la possibilita' di rigiocare
            font.getData().setScale(1f,1f);
            font.draw(game.batch,"HAI TOTALIZZATO: ",270,630);
            font.draw(game.batch,String.format("%08d",punteggio) + " PUNTI!",280,580);
                
            font.draw(game.batch,"VUOI GIOCARE ANCORA?",200,500);
            
            //Per ogni elemento dell'array characters (classe padre) stampa l'animazione ed esegui il metodo run()
            for(int i = 0; i<characters.length; i++){
                //Animazione
                goblinenemy.elapsedTime+=Gdx.graphics.getDeltaTime();
                spideyhero.elapsedTime+=Gdx.graphics.getDeltaTime();
                game.batch.draw((TextureRegion) goblinenemy.animation.getKeyFrame(goblinenemy.elapsedTime,true),goblinenemy.goblinbox.x,goblinenemy.goblinbox.y,200,200);
                game.batch.draw((TextureRegion) spideyhero.animation.getKeyFrame(spideyhero.elapsedTime,true),spideyhero.spideyherobox.x,spideyhero.spideyherobox.y,200,200);
                characters[i].Run();
            }
                
            //Abbasso l'opacita' delle immagini e del font relativo al punteggio ed al tempo
            game.batch.setColor(worldColor);
            font.setColor(worldColor);    
        }           

        for (i=0;i < pizze.size; i++){
            Rectangle pizza = pizze.get(i);
            //Se siamo nella condizione di gameover
            if(gameover){
                speed = 0;
                game.batch.draw(texturepizza, pizza.x, pizza.y, pizza.width, pizza.height);
            } else{
                pizza.y -= speed*f;
                game.batch.draw(texturepizza, pizza.x, pizza.y, pizza.width, pizza.height);
            }
        }

        //Barra conta punteggio e tempo
        game.batch.draw(Barra, 0, 0, SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT);
                              
        //Inserisco la grandezza del font e le variabili da stampare.
        font.getData().setScale(1,1);
        font.draw(game.batch,String.format("%02d",timerlevel),743,743); //Stampo il tempo
        font.draw(game.batch,String.format("%08d",punteggio),75,743);  //Stampo il punteggio
        font.draw(game.batch,String.format("%03d",game.coins),510,770);  //Stampo i coins

        //Se siamo nella condizione di gameover ferma peter, altrimenti aumenta il tempo e l'animazione
        if(gameover){
            game.batch.draw((TextureRegion) spideypizze.animation.getKeyFrame(spideypizze.elapsedTime,true), spideypizze.spideybox.x,spideypizze.spideybox.y,spideypizze.spideybox.width,spideypizze.spideybox.height+100);
        } else {
            //Tempo che fa scorrere l'animazione e stampa peter animato
            spideypizze.elapsedTime+=Gdx.graphics.getDeltaTime(); 
            game.batch.draw((TextureRegion) spideypizze.animation.getKeyFrame(spideypizze.elapsedTime,true), spideypizze.spideybox.x,spideypizze.spideybox.y,spideypizze.spideybox.width,spideypizze.spideybox.height+100);
        }
            
        //Dopo game.batch.end
        for (i=0;i < pizze.size; i++){
            Rectangle pizza = pizze.get(i);
            if(pizza.overlaps(spideypizze.spideybox)){
                if(gameover){  //Se ci troviamo nello stato di gameover, il punteggio si ferma
                    stop_punteggio = punteggio;
                } else{ //Altrimenti aumenta il punteggio di 50 per ogni pizza raccolta, setta test = true e memorizza il tempo - 1 nella variabile k 
                    pick_pizza = true;
                    k = timerlevel-1;
                    punteggio += 50;
                    pizze.removeIndex(i);   
                        
                    //Effetto pizza raccolta
                    long id = sound.play(0.2f);
                    if(mute == 1){ //Se il giocatore ha cliccato l'icona per mutare il gioco su options, mute sara' 1, quindi l'effetto sara' 0
                        sound.setVolume(id,0.0f);
                    } else{
                        sound.setPitch(id, 2);
                        sound.setLooping(id, false);
                    }   
                }
            } else if(pizza.y + pizza.height < 0){
                 pizze.removeIndex(i);
            }
        }

        //Se test == true, stampa a schermo "+50" ogni volta che viene raccolta una pizza ed i secondi scendono di 1 (cioe' k che sarebbe timerlevel - 1)
        if(pick_pizza){
            font.getData().setScale(.50f,.50f);
            font.draw(game.batch,"+50",spideypizze.spideybox.x+150,spideypizze.spideybox.y+150);
            if(timerlevel == k){
                pick_pizza = false;
            }
        }
            
        game.batch.end();

    }

    @Override
    public void resize(int i, int i1) {
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
