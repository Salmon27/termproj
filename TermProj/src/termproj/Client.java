/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

//import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;
/**
 *
 * @author AJSal
 */
public class Client {

    public static int menu(User user) {

        Scanner input = new Scanner(System.in);
        int choice;

        while (true) {
            if (user == null) {
                System.out.println("=======================");
                System.out.println("= WELCOME TO BOOKSTOP =");
                System.out.println("=======================");
                System.out.println();
                System.out.println("1. Register User");
                System.out.println("2. Sign in");
                System.out.println("0. Exit");
                System.out.println();
            } else {
                System.out.println("======================");
                System.out.println("=  BOOKSTOP CATALOG  =");
                System.out.println("======================");
                System.out.println();
                System.out.println("1. Search Book");
                System.out.println("2. Add to cart");
                System.out.println("3. Checkout");
                System.out.println("0. Logoff");
                System.out.println();
            }
            System.out.print("Choice: ");
            choice = input.nextInt();

            try {
                if (choice >= 0 || choice <= 3) {
                    input.close();
                    return choice;
                } else {
                    System.out.println("ERR: Choice not recognized!");
                }
            } catch (Exception e) {
                System.out.println("ERR: Choice not recognized!");
            }
        }

        // show options, read input, return choice
        /*
         * menu 1
         * 
         * 1. Adduser 2. Signin
         */
        /*
         * menu 2
         * 
         * 1. search book 2. add to cart 3. checkout
         */

    }

    public static boolean searchBook(BufferedReader inFromServer, BufferedWriter outToServer) throws IOException {
        Scanner input = new Scanner(System.in);
        String userInput;

        System.out.println("Enter a title of a book: ");
        userInput = input.nextLine();

        outToServer.write(userInput);

        String answer = inFromServer.readLine();
        System.out.println("From Server: " + answer);

        input.close();

        return true;
        // read from user, send title search to server
        // scanner reads title, writer writes title, reader reads response from server
        // and prints it out
    }

    public static void addToCart(Book[] cart, Socket socket) {
        // adds to cart
    }

    public static void checkOut(User user, Book[] cart, BufferedReader in) {
        // checks out
    }

    public static boolean validateCard(String cardNo) {

        // return true;
        int[] ints = new int[cardNo.length()];
        for (int i = 0; i < cardNo.length(); i++) {
            ints[i] = Integer.parseInt(cardNo.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        if (sum % 10 == 0) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean validateEmail(String email) {
        return true;
        // return email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
    }

    public static boolean validatePhoneNo(String num) {
        return true;
        // return
        // num.matches("^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$")
    }

    public static boolean validatePword(String pword) {
        return true;
        // validates passwords based on constraints
        // more than 8characters, must contain upper and lower and a number
        // return pword.matches("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})")
    }

    public static boolean validateID(String uname, BufferedWriter outToServer, BufferedReader inFromServer) {

        return true;
    }

    public static boolean addUser(ObjectOutputStream objToServer, BufferedWriter outToServer,
            BufferedReader inFromServer) throws IOException, NoSuchAlgorithmException {

        User user = new User();
        Scanner input = new Scanner(System.in);
        String tempAnswer = "";

        // get user information from user
        while (true) {
            System.out.print("Enter your username: ");
            tempAnswer = input.nextLine();

            if (validateID(tempAnswer, outToServer, inFromServer)) {
                user.setID(tempAnswer);
                break;
            }
            System.out.println("ERR: Input Invalid!");
        }

        System.out.print("Enter your first name: ");
        user.setfName(input.nextLine());

        System.out.print("Enter your last name: ");
        user.setlName(input.nextLine());

        while (true) {
            System.out.print("Enter your email: ");
            tempAnswer = input.nextLine();

            if (validateEmail(tempAnswer)) {
                user.setEmail(tempAnswer);
                break;
            }
            System.out.println("ERR: Input Invalid!");
        }

        System.out.println("Enter your home address: ");
        user.setAddr(input.nextLine());

        while (true) {
            System.out.print("Enter your phone number: ");
            tempAnswer = input.nextLine();

            if (validatePhoneNo(tempAnswer)) {
                user.setPhone(tempAnswer);
                break;
            }
            System.out.println("ERR: Input Invalid!");
        }

        while (true) {
            System.out.print("Enter your password: ");
            tempAnswer = input.nextLine();

            if (validatePword(tempAnswer)) {
                user.setPword(encrypt(tempAnswer));
                break;
            }
            System.out.println("ERR: Input Invalid!");
        }

        input.close();

        outToServer.write("ADD USER");
        objToServer.writeObject(user); // Send object to server
        objToServer.flush();

        return true;
        // check if information is valid
        // create User and send to server for insertion
    }

    public static User searchUser(Socket socket, BufferedWriter out, String uName, String encryptedPword) {
        User u = null;
        return u;
        // send a username and password to the server, and the server will respond
        // with a user or with null. If User is found, the user is loggedIn
    }

    public static String encrypt(String line) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(line.getBytes());
        return byteToString(digest);
    }

    public static String byteToString(byte[] digest) {
        String dig = "";
        for (int i = 0; i < digest.length; i++) {
            dig += digest[i];
        }
        return dig;
    }

    public static void main(String args[]) throws Exception {

        while (true) {
            System.out.println("Enter your equation (ex.: 8 x 10). or enter BYE to quit. \n>>> ");
            Socket clientSocket = new Socket("localhost", 6789);
            // Create a input stream from user
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

            // Create a input stream, attached to socket
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter outToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            ObjectOutputStream objToServer = new ObjectOutputStream(clientSocket.getOutputStream());

            String sentence = inFromUser.readLine();
            outToServer.write(sentence); // Send line to server
            outToServer.newLine();
            outToServer.flush();

            String answer = inFromServer.readLine(); // Read line from server
            System.out.println("FROM SERVER: " + answer);
            if (answer.equals("Goodbye!")) {
                break;
            }
            clientSocket.close();
        }

    }

}
