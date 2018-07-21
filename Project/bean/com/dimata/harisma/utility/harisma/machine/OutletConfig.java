/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine;

import com.dimata.harisma.utility.harisma.machine.db.DBConnectionBroker;
import com.dimata.harisma.utility.harisma.machine.db.OIDFactory;
import com.dimata.qdep.db.DBConfigReader;
import com.dimata.qdep.db.DBException;
import java.util.Vector;

/**
 *
 * @author Dimata 007
 */
public class OutletConfig {
    private static String OUTLET_CONFIG_FILE = com.dimata.RootClass.getThisClassPath() + System.getProperty("file.separator") + "outletharisma.xml";    
    static String locationMesin="01";
     public static String DATE_FORMAT = "dd.MM.yyyy";
    public static char STR_DELIMITER = '\'';
    public static char DECIMAL_POINT = '.';
    public static final int DBSVR_MYSQL = 0;
    public static final int DBSVR_POSTGRESQL = 1;
    public static final int DBSVR_SYBASE = 2;
    public static final int DBSVR_ORACLE = 3;
    public static final int DBSVR_MSSQL = 4;
    public static final int DBSVR_TYPE = DBSVR_MYSQL;  // this is a DB Handler for MySQL Gede_28Nov2011 rubah DBSVR_MSSQL jadi DBSVR_MYSQL
    protected static String connLog = "tmsconn.log";
    protected static String dbDriver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
    //protected static String dbDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
    protected static String dbUrl = "jdbc:microsoft:sqlserver://192.168.0.5:1433;databasename=ZIPREALTY;";
    //protected static String dbUrl 	 = "jdbc:odbc:odbcZipRealty";
    protected static String dbUser = "sa";
    protected static String dbPwd = "aa";
    protected static String dateFormat = "dd.MM.yyyy";
    protected static String decimalFormat = "#,##0.##";
    protected static String currencyFormat = "#,##0.00 '\u20AC'";
    protected static String dbSQLDecimalFormat = "#.##########";
    protected static String dbSQLQuoteString = "'";
    protected static String dbSQLIntegerFormat = "#";
    protected static int dbMinConn = 2;
    protected static int dbMaxConn = 5;
    
    //update by satrya 2014-02-14
    //protected static String locationMesin = "01";
     
    public static boolean configLoaded = false;
    public static DBConfigReader cnfReader;// = new DBConfigReader(CONFIG_FILE);
    private String tableName;
    private String pstName;
    private String[] fieldNames;
    private int[] fieldTypes;
    protected Vector fieldValues;
    protected boolean hasData;
    protected boolean recordModified;
    protected int[] keyIndex;
    protected int[] keyValues;
    protected int keyCount;
    protected int idIndex;
    protected int timestampIndex;
    //private static DBLogger dbLog = new DBLogger("");
    protected static DBConnectionBroker connPool = null;
    private static OIDFactory oidFactory = new OIDFactory();
    /**
     * @return the OUTLET_CONFIG_FILE
     */
    public static String getOUTLET_CONFIG_FILE() {
        return OUTLET_CONFIG_FILE;
    }
    
     /**
    * create by satrya 2014-02-14
    * <p>untuk mengambil no mesin dari xml</p>
    */
   public static String getMesinLocation() throws Exception {
      try{
       DBConfigReader configReader = new DBConfigReader(OUTLET_CONFIG_FILE);
        locationMesin = configReader.getConfigValue("stationmesin"); 
      }catch (Exception exception) {
            exception.printStackTrace(System.err);
            throw new DBException(null, DBException.CONFIG_ERROR);
       }
      return locationMesin;
    }
      protected static void loadConfig()
            throws DBException {
        if (!configLoaded) {
            //getLog();
            try {

                System.out.println("CONFIG_FILE " + OUTLET_CONFIG_FILE);
                DBConfigReader configReader = new DBConfigReader(OUTLET_CONFIG_FILE);

                dbDriver = configReader.getConfigValue("dbdriver");
                dbUrl = configReader.getConfigValue("dburl");
                dbUser = configReader.getConfigValue("dbuser");
                dbPwd = configReader.getConfigValue("dbpasswd");
                
                //update by satrya 2014-02-14
                locationMesin = configReader.getConfigValue("stationmesin");
                // Set the minimum and maximum connection
                String configValue = configReader.getConfigValue("dbminconn");
                if (configValue != null && !configValue.equals("")) {
                    dbMinConn = (new Integer(configValue)).intValue();
                }

                configValue = configReader.getConfigValue("dbmaxconn");
                if (configValue != null && !configValue.equals("")) {
                    dbMaxConn = (new Integer(configValue)).intValue();
                }

                // Set log file for connection
                configValue = configReader.getConfigValue("logconn");
                if (configValue != null && !configValue.equals("")) {
                    connLog = configValue;
                }

                // Set format for: date, decimal and currency
                configValue = configReader.getConfigValue("fordate");
                if (configValue != null && !configValue.equals("")) {
                    dateFormat = configValue;
                }

                configValue = configReader.getConfigValue("fordecimal");
                if (configValue != null && !configValue.equals("")) {
                    decimalFormat = configValue;
                }


                configValue = configReader.getConfigValue("forcurrency");
                if (configValue != null && !configValue.equals("")) {
                    currencyFormat = configValue;
                }

                configLoaded = true;
                System.out.println("/*********************************************************/");
                System.out.println("                        LOAD DBCONFIG            ");
                System.out.println("");
                System.out.println("\t dbDriver : " + dbDriver);
                System.out.println("\t dbUrl    : " + dbUrl);
                System.out.println("\t dbUser   : " + dbUser);
                
                //update by satrya 2014-02-14
                System.out.println("\t No Mesin   : " + locationMesin);
                System.out.println("\t dbPwd    : *************");
                System.out.println("/********************************************************/");

            } catch (Exception exception) {
                exception.printStackTrace(System.err);
                throw new DBException(null, DBException.CONFIG_ERROR);
            }
        }
    }


    
}
