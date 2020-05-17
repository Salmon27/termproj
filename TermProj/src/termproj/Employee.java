/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

/**
 *
 * @author AJSal
 */
public class Employee extends User{
    
    protected void getHistory(User customer, Socket socket, BufferedWriter out)
    {
        //prints out the user's transaction history to the console
    }
    
    protected void addBook(Socket socket, BufferedWriter out, BufferedReader in)
    {
        // gets book informsation and creates a book, sends book information to
        // server to add
    }
}
