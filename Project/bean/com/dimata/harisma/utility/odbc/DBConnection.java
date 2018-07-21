/*
 * DBConnection.java
 *
 * Created on September 15, 2004, 11:00 AM
 */

package com.dimata.harisma.utility.odbc;

/**
 *
 * @author  gedhy
 */
import java.sql.*;
import java.util.*;  

public class DBConnection {
    
    private Connection conn = null; 
    //private static final String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
    //private static final String url = "jdbc:odbc:harismaodbc"; 
    //public String driver = "org.gjt.mm.mysql.Driver";
    //public String url = "jdbc:mysql://localhost:3306/harisma";
    
    public Connection doConnect(String driver, String url){
        try{            
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url);                        
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("DBConnection.connect() err : "+e.toString());
        }
        return conn;
    }

    public void doDisconnect(){
        try{
            conn.close();                        
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("DBConnection.disconnect() err : " + e.toString());
        }
    }        
    
}
