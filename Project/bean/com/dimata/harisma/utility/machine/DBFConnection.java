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
 * @author RAMA
 */
public class DBFConnection {
     public static Connection getConnection(String dsnName,String user, String password) throws Exception {
    Driver d = (Driver)Class.forName
    ("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
    String URL = "jdbc:odbc:"+"DRIVER={Microsoft Visual FoxPro Driver};SourceType=DBF;SourceDB="+dsnName;
    //String URL = "jdbc:odbc:"+"DRIVER={Microsoft dBase Driver (*.dbf)};datasource="+dsnName+";DriverID=533";
    Connection c = DriverManager.getConnection(URL, user, password);
    return c;
    }
}
