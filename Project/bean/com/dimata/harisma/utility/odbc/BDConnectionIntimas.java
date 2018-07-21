/*
 * BDConnectionIntimas.java
 *
 * Created on September 1, 2006, 10:31 AM
 */

package com.dimata.harisma.utility.odbc;

/**
 *
 * @author  yunny
 */
import java.sql.*;
import java.util.*;

public class BDConnectionIntimas {
    
    private Connection conn = null; 
   // private static final user = "root";
   // private static final pwd ="";
    private static final String driver = "org.gjt.mm.mysql.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/intimas_2006"; 
    
    public Connection doConnect(){
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
