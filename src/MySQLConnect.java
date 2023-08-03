
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author dhruvshah619
 */
public class MySQLConnect {
    private static final String CONNECTION_STRING="jdbc:mysql://localhost:3306/chat_system";
    //                                                                  portno/database_name
    private static final String USERNAME="dhruv";
    private static final String PASSWORD="dhruv";
    /**
     * @return
     * returns a valid Connection to employee_management DB if it can,otherwise it returns null.
     */
    
    public static Connection getConnection(){
        Connection conn=null;
        try{
            conn=DriverManager.getConnection(CONNECTION_STRING,USERNAME,PASSWORD);
//            JOptionPane.showMessageDialog(null,"Connection Established!");
        }
        catch(SQLException se){
            JOptionPane.showMessageDialog(null,"Connection Failed:"+se.getMessage());
        }
        return conn;
    }
}
