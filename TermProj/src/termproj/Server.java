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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class Server {

    public static void insertUser(User user, Connection conn) {

        String table = "";
        if (user.getClass().getName() == "Admin") {
            table = "Admins";
        } else if (user.getClass().getName() == "Employee") {
            table = "Employees";
        } else if (user.getClass().getName() == "User") {
            table = "Users";
        }
        String sqlString = "INSERT INTO Users VALUES(?,?,?,?,?,?,?);";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sqlString);
            stmt.setString(1, user.getID().toLowerCase());
            stmt.setString(2, user.getfName());
            stmt.setString(3, user.getlName());
            stmt.setString(4, user.getAddr());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getPword());
            stmt.setString(7, user.getEmail());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static User searchUser(String uname, String pword, Connection conn) throws SQLException {
        String[] tables = {"Admins", "Users", "Employees"};
        User user = new User();
        ResultSet rs = null;
        for (int i = 0; i < tables.length; i++) {
            PreparedStatement stmt;
            try {
                stmt = conn.prepareStatement("SELECT * FROM ? WHERE ID = ? OR email = ? AND password = ?");
                stmt.setString(1, tables[i]);
                stmt.setString(2, uname.toLowerCase());
                stmt.setString(3, uname.toLowerCase());
                stmt.setString(4, pword);
                rs = stmt.executeQuery();

            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (rs.next()) {
                user.setID(rs.getString("ID"));
                user.setfName(rs.getString("fName"));
                user.setlName(rs.getString("lName"));
                user.setAddr(rs.getString("addr"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        }

        return null;
        // searches db for user, returns the user if found
    }

    public static void checkOut(User user, ArrayList<Book> bookList, Connection conn) {
        // creates trasaction record and inserts it
    }

    public static void insertBook(Book book, Connection conn) {
        String sqlString = "INSERT INTO ? VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sqlString);
            stmt.setString(1, "Books");
            stmt.setString(2, book.getISBN());
            stmt.setString(3, book.getTitle());
            stmt.setString(4, book.getAuthor());
            stmt.setString(5, book.getCondition());
            stmt.setInt(6, book.getNumPages());
            stmt.setDate(7, book.getPubDate());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Failed");
        }
    }

    public static ArrayList<Book> searchBook(String title, Connection conn) throws SQLException {
        ResultSet rs = null;
        ArrayList<Book> bookList = new ArrayList<>();
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("SELECT * FROM ? WHERE title = ?");
            stmt.setString(1, "Books");
            stmt.setString(2, title.toLowerCase());
            rs = stmt.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (rs.next()) {
            Book currentBook = new Book();
            currentBook.setISBN(rs.getString("ISBN"));
            currentBook.setTitle(rs.getString("title"));
            currentBook.setAuthor(rs.getString("author"));
            currentBook.setCondition(rs.getString("addr"));
            currentBook.setNumPages(rs.getInt("numPages"));
            currentBook.setPubDate(rs.getDate("pubDate"));
            currentBook.setQuantity(rs.getInt("quantity"));
            bookList.add(currentBook);
        }

        return bookList;
    }

    public static void removeBook(Book book, int quantity, Connection conn) {
        String sqlString = "UPDATE ? SET ? = ? WHERE ISBN = ?;";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sqlString);
            stmt.setString(1, "Books");
            stmt.setString(2, "quantity");
            stmt.setInt(3, book.getQuantity() - quantity);
            stmt.setString(4, book.getISBN());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Failed");
        }
    }

    public static void deleteUser(Admin admin, User user, Connection conn) {
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

            ObjectInputStream objFromClient = new ObjectInputStream(connectSocket.getInputStream());

            //Read in line from socket
            String clientSentence = inFromClient.readLine();

            if (clientSentence.equals("ADD USER")) {

                User user = (User) objFromClient.readObject();
                insertUser(user, conn);

            } else if (clientSentence.equals("SEARCH BOOK")) {
                String title = inFromClient.readLine();
                String returnString = "";
                ArrayList<Book> bookList = searchBook(title, conn);
                for (int i = 0; i < bookList.size(); i++) {
                    returnString += bookList.get(i).toString();
                }
                outToClient.write(returnString);
                outToClient.newLine();
                outToClient.flush();
            }
 {

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
