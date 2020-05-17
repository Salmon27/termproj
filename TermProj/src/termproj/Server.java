/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproj;

/**
 *
 * @author AJSal
 */
import java.io.*;
import java.net.*;
import java.sql.*;

class Server {

    
    public static void insertUser(User user, Connection conn)
    {
        // inserts into DB based on User's type
    }
    
    public static User searchUser(String uname, String pword, Connection conn)
    {
        return null;
        // searches db for user, returns the user if found
    }
    
    public static void checkOut(User user, Book book, Connection conn)
    {
        // creates trasaction record and inserts it
    }
    
    public static void insertBook(Book book, Connection conn)
    {
        // inserts book into DB or adds 1 to the book's quantity
    }
    
    public static void removeBook(Book book, int quantity, Connection conn)
    {
        // subtracts quantity from the book's quantity
    }
    
    public static void deleteUser(Admin admin, User user, Connection conn)
    {
        // removes a user from the DB using admin's credentials
    }
    
    
    public static void main(String args[]) throws Exception {
        
        Connection conn;  
        conn = DriverManager.getConnection("jdbc:ucanaccess://c://Users//AJSal//Downloads//JDBC//BookStore.accdb");
        System.out.println("Connected Successfully");
        
        //Create welcome socket atport 6789
        ServerSocket welcomeSocket = new ServerSocket(6789);
        while (true) //waiting for other client sockets
        {
            //listen for client connection and accepts it 
            Socket connectSocket = welcomeSocket.accept();

            //Creta input stream, attached to socket
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectSocket.getInputStream()));
            //Create output stream, attached to socket
            BufferedWriter outToClient = new BufferedWriter(new OutputStreamWriter(connectSocket.getOutputStream()));

            //Read in line from socket
            String clientSentence = inFromClient.readLine();

            if (clientSentence.toLowerCase().equals("bye")) {
                outToClient.write("Goodbye!");
                outToClient.newLine();
                outToClient.flush();
                break;
            } else {

                String[] parts = clientSentence.trim().split(" ");

                //Write out line to socket 
                outToClient.write("Here's the message");
                outToClient.newLine();
                outToClient.flush();

                connectSocket.close();
            }
        }   //End of while loop, loop back and wait for another client connection
    }
    
}
