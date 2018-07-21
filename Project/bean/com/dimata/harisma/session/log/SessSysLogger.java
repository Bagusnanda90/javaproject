package com.dimata.harisma.session.log;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import java.text.*;

import com.dimata.qdep.db.*;

import com.dimata.harisma.entity.log.*;
import com.dimata.harisma.entity.search.*;


public class SessSysLogger {
    
    public static final String SESS_NAME_SYS_LOGGER     =   "SESS_SYS_LOGGER";
    
    public static final int LOG_LEVEL_ERROR             =   1;
    public static final int LOG_LEVEL_WARNING           =   2;
    public static final int LOG_LEVEL_INFO              =   3;
    
    
    public SessSysLogger() {
        
    }
    
    
    public static void log(String logSysMode, String logNote, int logLevel) {
        try {
            PstSysLogger pstLogger = new PstSysLogger();
            SysLogger logger = new SysLogger();
            
            logger.setLogDate(new Date());
            logger.setLogSysMode(logSysMode);
            logger.setLogNote(logNote);
            logger.setLogCategory(logLevel);
            
            pstLogger.insertExc(logger);
        }
        catch(DBException e) {
            System.out.println("Error inserting log record > " + e.getMessage());
        }
    }
    
    public static void logError(String logSysMode, String logNote) {
        try {
            PstSysLogger pstLogger = new PstSysLogger();
            SysLogger logger = new SysLogger();
            
            logger.setLogDate(new Date());
            logger.setLogSysMode(logSysMode);
            logger.setLogNote(logNote);
            logger.setLogCategory(LOG_LEVEL_ERROR);
            
            pstLogger.insertExc(logger);
        }
        catch(DBException e) {
            System.out.println("Error inserting error log record > " + e.getMessage());
        }
    }
    
    public static void logWarning(String logSysMode, String logNote) {
        try {
            PstSysLogger pstLogger = new PstSysLogger();
            SysLogger logger = new SysLogger();
            
            logger.setLogDate(new Date());
            logger.setLogSysMode(logSysMode);
            logger.setLogNote(logNote);
            logger.setLogCategory(LOG_LEVEL_WARNING);
            
            pstLogger.insertExc(logger);
        }
        catch(DBException e) {
            System.out.println("Error inserting warning log record > " + e.getMessage());
        }
    }
    
    public static void logInfo(String logSysMode, String logNote) {
        try {
            PstSysLogger pstLogger = new PstSysLogger();
            SysLogger logger = new SysLogger();
            
            logger.setLogDate(new Date());
            logger.setLogSysMode(logSysMode);
            logger.setLogNote(logNote);
            logger.setLogCategory(LOG_LEVEL_INFO);
            
            pstLogger.insertExc(logger);
        }
        catch(DBException e) {
            System.out.println("Error inserting info log record > " + e.getMessage());
        }
    }
    
    public static Vector getSystemLog(SrcSysLogger logger, int start, int recordToGet) {
        Vector result = new Vector();
     
        Date dateStart = logger.getLogDateStart();
        Date dateEnd = logger.getLogDateEnd();
    
        String startDate = (dateStart.getYear() + 1900) + "-" + (dateStart.getMonth() + 1) + "-" + dateStart.getDate();
        String endDate = (dateEnd.getYear() + 1900) + "-" + (dateEnd.getMonth() + 1) + "-" + dateEnd.getDate();
       
        try {
            PstSysLogger pstSysLogger = new PstSysLogger();
            
            StringBuffer whereClause = new StringBuffer();
            
            whereClause.append(PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_DATE]);
            whereClause.append(" BETWEEN '").append(startDate).append("' AND '").append(endDate).append("'");
            
            if(logger.getLogCategory() > 0) {
                whereClause.append(" AND ").append(PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_CATEGORY]);
                whereClause.append(" = ").append(logger.getLogCategory());
            }
            
            if(!logger.getLogSysMode().equals("0") /*logger.getLogSysMode().trim().length() > 0*/) {
                whereClause.append(" AND ").append(PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_SYSMODE]);
                whereClause.append(" LIKE '%").append(logger.getLogSysMode()).append("%'");
            }
         
            String orderClause = PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_DATE];

            result = pstSysLogger.list(start, recordToGet, whereClause.toString(), orderClause);
        }
        catch(Exception e) {
            System.out.println("listSysLogExc : "+e.toString());
        }
        finally {
            return result;
        }
    }
    
    public static Vector listLogSysMode() {
        Vector lists = new Vector(); 
        DBResultSet dbrs = null;

        try {
            String sql = "SELECT DISTINCT(" + PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_SYSMODE] + ")";
            sql += " FROM " + PstSysLogger.TBL_SYS_LOGGER; 
            sql += " ORDER BY " + PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_SYSMODE];
          
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {                
                lists.add(rs.getString(1));
            }
            rs.close();

            return lists;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    public static int countSystemLog(SrcSysLogger logger) {
        int result = 0;
     
        Date dateStart = logger.getLogDateStart();
        Date dateEnd = logger.getLogDateEnd();
    
        String startDate = (dateStart.getYear() + 1900) + "-" + (dateStart.getMonth() + 1) + "-" + dateStart.getDate();
        String endDate = (dateEnd.getYear() + 1900) + "-" + (dateEnd.getMonth() + 1) + "-" + dateEnd.getDate();
       
        try {            
            StringBuffer whereClause = new StringBuffer();
            
            whereClause.append(PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_DATE]);
            whereClause.append(" BETWEEN '").append(startDate).append("' AND '").append(endDate).append("'");
           
            if(logger.getLogCategory() > 0) {
                whereClause.append(" AND ").append(PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_CATEGORY]);
                whereClause.append(" = ").append(logger.getLogCategory());
            }
            
            if(!logger.getLogSysMode().equals("0") /*logger.getLogSysMode().trim().length() > 0*/) {
                whereClause.append(" AND ").append(PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_SYSMODE]);
                whereClause.append(" LIKE '%").append(logger.getLogSysMode()).append("%'");
            }
           
            result = PstSysLogger.getCount(whereClause.toString());  
        }
        catch(Exception e) {
            System.out.println("listSysLogExc : "+e.toString());
        }
        finally {
            return result;
        }
    }
    
    public static void main(String[] args) {        
        /* logging test */
        SessSysLogger.log("test", "message for test", SessSysLogger.LOG_LEVEL_WARNING);
    }
    
}