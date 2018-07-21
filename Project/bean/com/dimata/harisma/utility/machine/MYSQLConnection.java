/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

/**
 *
 * @author Satrya Ramayu
 */
public class MYSQLConnection {
      public static Connection getConnection(String dsnName,String dbName, String user, String password) throws Exception {
    Driver d = (Driver)Class.forName
     ("com.mysql.jdbc.Driver").newInstance();
    String URL = "jdbc:mysql://"+dsnName+"/"+dbName;
    Connection c = DriverManager.getConnection(URL, user, password);
    return c;
    }
}

