package com.mygdx.game.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    
    private String url;
    private Connection conn;
    
    public DataBase(String address, String dbuser){
        this.url = address + dbuser;
        this.conn = null;  //Oggetto che conterra' il parametro della nostra connessione totale
    }
    
    //Errori exception
    public void open() throws SQLException{
        this.conn = DriverManager.getConnection(this.url);
    }
    
    public void close() throws SQLException{
        this.conn.close();
    }
        
    //Statement: elemento previsto dalla sintassi SQL che puo' andare ad impattare sullo stato del database 
    //o gestire il flusso di esecuzione di una query, gestire transazioni e quant'altro.
    
    //PreparedStatement: permette di sviluppare uno schema di query (con parametri) che puo' essere utilizzato piu' volte con valori differenti.

    public void save(String username, int points) throws SQLException{ //Salvo lo score
        PreparedStatement pst = this.conn.prepareStatement("INSERT INTO scores(name,points) VALUES(?,?)");  
        pst.setString(1, username);
        pst.setInt(2, points);
        pst.executeUpdate();
    }
    
    //Statement: rappresenta una query semplice con tutti i dati specificati all'atto della creazione.
    
    public String getScores() throws SQLException{  //Visualizzo gli ultimi 5 score partendo dal primo
        int position = 1;
        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT name,points FROM scores ORDER BY points DESC LIMIT 0,5");   //Prendo i primi 10 risultati dal piu' grande al piu' piccolo e li ordino in ordine decrescente
        String result="";
        while(rs.next()){
            result += position + " " + rs.getString("name")+ " " +rs.getString("points")+ "\n" + "\n";
            position++;
        }
        return result;
    }
    
    public boolean whichCommand(String cmd) throws SQLException{ //Il comando inserito esiste?
        /*Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT comando FROM chatbot");
        String result = "";
        while(rs.next()){
            if(rs == null){
                rs.close();
            }
        }
        return result;*/
        PreparedStatement pst = this.conn.prepareStatement("SELECT id FROM chatbot WHERE comando = ?");
        pst.setString(1,cmd);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }
    
    public String getCommand(String cmd) throws SQLException{ //Visualizzo il testo corrispondente al comando inserito
        PreparedStatement pst = this.conn.prepareStatement("SELECT testo FROM chatbot WHERE comando = ?");
        pst.setString(1, cmd);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getString("testo");
    }
    
}
