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
 * @author ktanjana
 */
public class ODBCConnection {

  public static Connection getConnection(String dsnName, String user, String password) throws Exception {
    Driver d = (Driver)Class.forName
     ("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
    String URL = "jdbc:odbc:"+dsnName;
    Connection c = DriverManager.getConnection(URL, user, password);
    return c;
    }
}
