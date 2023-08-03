
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dhruv
 */
public class Database_Operations {
    static Connection conn;
    static PreparedStatement ps;
    static Statement st;
    static ResultSet rs;
    static{
        conn=MySQLConnect.getConnection();
    }
    public static boolean signUp(String username,String password){
        String sql="INSERT into user VALUES(?,?)";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            ps.execute();
            return true;
        }catch(SQLException e){
            return false;
        }
    }
    public static boolean createNewTable(String username){
        String sql="CREATE TABLE "+username+" (username varchar(255) PRIMARY KEY)";
        try{
            st=conn.createStatement();
            st.execute(sql);
            return true;
        }catch(SQLException e){
            return false;
        }
    }
    public static boolean addUserInto(String username){
        String sql="INSERT into "+ UserWindow.username+" VALUES('"+username+"')";
        try{
            st=conn.createStatement();
            st.execute(sql);
            return true;
        }catch(SQLException e){
            return false;
        }
    } 
    public static List<String> getListOfContacts(){
        String sql="SELECT * from "+UserWindow.username;
        List<String> listOfContacts=new ArrayList<>();
        try{
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                listOfContacts.add(rs.getString("username"));
            }
            return listOfContacts;
        }catch(SQLException e){
            return null;
        }
    }
    public static boolean addMessage(String sender,String message,String receiver){
        String sql="INSERT into messages(sender,message,receiver,message_read) VALUES (?,?,?,?)";
        try{
            ps= conn.prepareStatement(sql);
            ps.setString(1, sender);
            ps.setString(2, message);
            ps.setString(3, receiver);
            ps.setBoolean(4, false);
            ps.execute();
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static List<String> getMessagesWhenOffline(String receiver){
        List<String> listOfMessages=new ArrayList<>();
        String sql="SELECT sender,message from messages where receiver=?";
        try{
            ps= conn.prepareStatement(sql);;
            ps.setString(1, receiver);
            rs=ps.executeQuery();
            while(rs.next()){
                String sender=rs.getString("sender");
                String message=rs.getString("message");
                listOfMessages.add(sender+" - "+message);
                System.out.println("Got message : " + message + " from " + sender);
            }
            removeAddedMessages(receiver);
            return listOfMessages;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    private static void removeAddedMessages(String receiver){
        String sql="DELETE from messages where receiver=?";
        try{
            ps= conn.prepareStatement(sql);;
            ps.setString(1, receiver);
            ps.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
