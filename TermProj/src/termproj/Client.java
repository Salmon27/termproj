/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author AJSal
 */
public class Client {
    
    
    public static int menu()
    {
        return -1;
        // show options, read input, return choice
    }
    
    public static boolean searchBook(BufferedReader in, Socket socket)
    {
        return true;        
        // searches for a book
    }
    
    public static void addToCart(Book[] cart, Socket socket)
    {
        // adds to cart
    }
    
    public static void checkOut(User user, Book[] cart, BufferedReader in)
    {
        // checks out
    }
    
    public static boolean validateCard(String cardNo)
    {
        return true;
        //validates credid cards
    }
    
    public static boolean validateEmail(String email)
    {
        return true;
        // validates emails
    }
    
     public static boolean validatePhoneNo(String num)
    {
        return true;
        // validates phone numbers
    }
     
      public static boolean validatePword(String pword)
    {
        return true;
        // validates passwords based on constraints
    }
      
    public static boolean validateID(String uname, Socket socket, BufferedWriter out)
    {
        return true;
    }
    
    public static boolean addUser(BufferedReader in, Socket socket)
    {
        return true;
        // get user information from user
        // check if information is valid
        // create User and send to server for insertion
    }
    
    public static User searchUser(Socket socket, BufferedWriter out, String uName, String encryptedPword)
    {
        User u = null;
        return u;
        // send a username and password to the server, and the server will respond
        // with a user or with null. If User is found, the user is loggedIn
    }
    
    public static void main(String args[]) throws Exception {

        while (true) {
            System.out.println("Enter your equation (ex.: 8 x 10). or enter BYE to quit. \n>>> ");
            Socket clientSocket = new Socket("localhost", 6789);
            //Create a input stream from user
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

            //Create a input stream, attached to socket
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter outToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String sentence = inFromUser.readLine();
            outToServer.write(sentence);   //Send line to server
            outToServer.newLine();
            outToServer.flush();

            String answer = inFromServer.readLine(); //Read line from server
            System.out.println("FROM SERVER: " + answer);
            if (answer.equals("Goodbye!")) {
                break;
            }
            clientSocket.close();
        }
        
    }
    
}
