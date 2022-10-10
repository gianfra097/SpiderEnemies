package com.mygdx.game.screen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static com.mygdx.game.screen.MainMenuScreen.not_open;  //Carico la variabile "not_open" che si trova su MainMenuScreen, per renderla false quando viene chiusa la finestra
import com.mygdx.game.tools.DataBase;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JScrollPane;

public class ChatBot extends JFrame{
    
    //Barra inserimento
    private JTextField inputBar = new JTextField();
      
    //Chat Area
    private JTextArea chatOutput = new JTextArea();
    
    //Barra scorrimento orizzontale
    private JScrollPane scroll = new JScrollPane(chatOutput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    public ChatBot() {

        //Creo i frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);  //Posizione centrale
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());  //Creo layout
        frame.setTitle("SpiderEnemies - ChatBot");
        
        //Elementi frame (inputbar, barra scorrimento)
        inputBar.setPreferredSize(new Dimension(1, 30));
        frame.add(inputBar, BorderLayout.SOUTH);
        frame.add(scroll, BorderLayout.CENTER);
        frame.setVisible(true);
                       
        //Applico il metodo WindowListener per utilizzare WindowClosing
        frame.addWindowListener(new WindowListener() { 
            
            @Override
            public void windowClosing(WindowEvent e) {  //Quando la finestra viene chiusa, not_open torna a true cosi' e' possibile aprire un'altra finestra dopo averla chiusa
                not_open = true;
            }
            
            //Metodi non utilizzati 
            @Override
            public void windowOpened(WindowEvent e) {
            }
            @Override
            public void windowClosed(WindowEvent e) {
            }
            @Override
            public void windowIconified(WindowEvent e) {
            }
            @Override
            public void windowDeiconified(WindowEvent e) {
            }
            @Override
            public void windowActivated(WindowEvent e) {
            }
            @Override
            public void windowDeactivated(WindowEvent e) {
            }
            
        });
               
        //Il bot si presenta come primo messaggio
        chatOutput.append("- ChatBot: Eccomi qui, pronto ad aiutarti! Digita il comando: Help, Game, Info o Chat" + "\n");
        
        //Eventi barra inserimento
        inputBar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                String userInput = inputBar.getText();        
                chatOutput.append("- Tu: " + userInput + "\n");
                
                try{ //Collegamento al db
                    String url = "jdbc:mysql://localhost/spiderenemies?";
                    String user_data = "user=gianfranco&password=root";
                    DataBase db = new DataBase(url,user_data);
                    db.open();
                    if(db.whichCommand(userInput)){ //Se la stringa inserita dall'utente, corrisponde ad un comando
                        botOutput(db.getCommand(userInput));  //Stampa il testo corrispondente al comando
                    /*}else if(userInput.equals("Chat") || userInput.equals("chat")){
                        botOutput("Collegamento con la chat in corso...");*/
                    }else{
                        botOutput("Mi dispiace, ma il tuo comando non e' stato riconosciuto."); //Altrimenti stampa messaggio di errore
                    }
                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }
                
                inputBar.setText("");
            }
        });
                
        //Chat output
        chatOutput.setBackground(Color.white);
        chatOutput.setLineWrap(true);
        chatOutput.setEditable(false);

    }
    
    public void botOutput(String s){
        chatOutput.append("- ChatBot: " + s + "\n");
    }
            
}
