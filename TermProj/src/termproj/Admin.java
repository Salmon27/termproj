/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author AJSal
 */
public class Admin implements Serializable{
    protected void deleteUser(User customer, Socket socket, BufferedWriter out)
    {
        // sends instructions to the server to delete a customer or employee 
        // from the database
    }
    
    protected void addEmployee(Socket socket, BufferedWriter out, BufferedReader in)
    {
        // sends employee to server to add to database
    }
}
