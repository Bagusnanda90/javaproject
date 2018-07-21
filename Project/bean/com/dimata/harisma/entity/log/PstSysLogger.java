
package com.dimata.harisma.entity.log;

/* package java */ 
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;


public class PstSysLogger extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

        public static final String TBL_SYS_LOGGER    =   "system_log";

	public static final int FLD_LOG_ID           =   0;
	public static final int FLD_LOG_DATE         =   1;
	public static final int FLD_LOG_SYSMODE      =   2;
	public static final int FLD_LOG_CATEGORY     =   3;
        public static final int FLD_LOG_NOTE         =   4;

	public static final  String[] fieldNames = {
		"LOG_ID",
                "LOG_DATE",
                "LOG_SYSMODE",
                "LOG_CATEGORY",
                "LOG_NOTE"
	}; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
                TYPE_DATE,
		TYPE_STRING,
		TYPE_INT,
		TYPE_STRING
	}; 
        
        public static final int LOG_LEVEL_ERROR     =   1;
        public static final int LOG_LEVEL_WARNING   =   2;
        public static final int LOG_LEVEL_INFO      =   3;
        
        public static final String[] levelNames = {
                "",
                "ERROR",
                "WARNING",
                "INFO",
        };

	public PstSysLogger(){
	}

	public PstSysLogger(int i) throws DBException { 
            super(new PstSysLogger()); 
	}

	public PstSysLogger(String sOid) throws DBException { 
            super(new PstSysLogger(0)); 
            
            if(!locate(sOid)) 
                throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            else 
                return; 
	}

	public PstSysLogger(long lOid) throws DBException { 
            super(new PstSysLogger(0)); 
            String sOid = "0"; 
            
            try { 
                sOid = String.valueOf(lOid); 
            }
            catch(Exception e) { 
                throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            } 
            
            if(!locate(sOid)) 
                throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            else 
                return; 
	} 

	public int getFieldSize(){ 
            return fieldNames.length; 
	}

	public String getTableName(){ 
            return TBL_SYS_LOGGER;
	}

	public String[] getFieldNames(){ 
            return fieldNames; 
	}

	public int[] getFieldTypes(){ 
            return fieldTypes; 
	}

	public String getPersistentName(){ 
            return getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
            SysLogger logger = fetchExc(ent.getOID()); 
            ent = (Entity)logger; 
            return logger.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
            return insertExc((SysLogger) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
            return updateExc((SysLogger) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
            if(ent==null){ 
                throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            } 
            return deleteExc(ent.getOID()); 
	}

	public static SysLogger fetchExc(long oid) throws DBException{ 
            try{ 
                SysLogger logger = new SysLogger();
                PstSysLogger pstLogger = new PstSysLogger(oid); 
                
                logger.setOID(oid);
                logger.setLogDate(pstLogger.getDate(FLD_LOG_DATE));
                logger.setLogNote(pstLogger.getString(FLD_LOG_NOTE));
                logger.setLogSysMode(pstLogger.getString(FLD_LOG_SYSMODE));
                logger.setLogCategory(pstLogger.getInt(FLD_LOG_CATEGORY));
                      
                return logger; 
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstSysLogger(0),DBException.UNKNOWN); 
            } 
	}

	public static long insertExc(SysLogger logger) throws DBException{ 
            try{ 
                PstSysLogger pstLogger = new PstSysLogger(0);

                pstLogger.setString(FLD_LOG_SYSMODE, logger.getLogSysMode());
                pstLogger.setString(FLD_LOG_NOTE, logger.getLogNote());
                pstLogger.setInt(FLD_LOG_CATEGORY, logger.getLogCategory());
                pstLogger.setDate(FLD_LOG_DATE, logger.getLogDate());

                pstLogger.insert(); 
                logger.setOID(pstLogger.getlong(FLD_LOG_ID));
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstSysLogger(0),DBException.UNKNOWN); 
            }
            return logger.getOID();
	}

	public static long updateExc(SysLogger logger) throws DBException{ 
            try{ 
                if(logger.getOID() != 0){ 
                    PstSysLogger pstLogger = new PstSysLogger(logger.getOID());

                    pstLogger.setString(FLD_LOG_SYSMODE, logger.getLogSysMode());
                    pstLogger.setString(FLD_LOG_NOTE, logger.getLogNote());
                    pstLogger.setInt(FLD_LOG_CATEGORY, logger.getLogCategory());
                    pstLogger.setDate(FLD_LOG_DATE, logger.getLogDate());

                    pstLogger.update();
                    return logger.getOID();
                }
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstSysLogger(0),DBException.UNKNOWN); 
            }
            return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
            try{ 
                PstSysLogger pstLogger = new PstSysLogger(oid);
                pstLogger.delete();
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstSysLogger(0),DBException.UNKNOWN); 
            }
            return oid;
	}

	public static Vector listAll(){ 
            return list(0, 500, "",""); 
	}

	public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
            Vector lists = new Vector(); 
            DBResultSet dbrs = null;
            
            try {
                String sql = "SELECT * FROM " + TBL_SYS_LOGGER; 
                if(whereClause != null && whereClause.length() > 0)
                    sql = sql + " WHERE " + whereClause;
                if(order != null && order.length() > 0)
                    sql = sql + " ORDER BY " + order;
                if(limitStart == 0 && recordToGet == 0)
                    sql = sql + "";
                else
                    sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                
                /**************************/
                //System.out.print(sql);
                
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                
                while(rs.next()) {
                    SysLogger logger = new SysLogger();
                    resultToObject(rs, logger);
                    lists.add(logger);
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

	private static void resultToObject(ResultSet rs, SysLogger logger){
            try{             
                logger.setOID(rs.getLong(PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_ID]));
                logger.setLogDate(rs.getDate(PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_DATE]));
                logger.setLogNote(rs.getString(PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_NOTE]));
                logger.setLogSysMode(rs.getString(PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_SYSMODE]));
                logger.setLogCategory(rs.getInt(PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_CATEGORY]));
            }
            catch(Exception e){ }
	}

	public static boolean checkOID(long logId){
            DBResultSet dbrs = null;
            boolean result = false;
            
            try{
                String sql = "SELECT * FROM " + TBL_SYS_LOGGER + " WHERE " + 
                              PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_ID] + " = " + logId;

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while(rs.next()) { result = true; }
                rs.close();
            }
            catch(Exception e){
                System.out.println("err : "+e.toString());
            }
            finally{
                DBResultSet.close(dbrs);
                return result;
            }
	}

	public static int getCount(String whereClause){
            DBResultSet dbrs = null;
            
            try {
                String sql = "SELECT COUNT("+ PstSysLogger.fieldNames[PstSysLogger.FLD_LOG_ID] + ") FROM " + TBL_SYS_LOGGER;
                if(whereClause != null && whereClause.length() > 0)
                        sql = sql + " WHERE " + whereClause;

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                
                /**************************/
                //System.out.println(sql);

                int count = 0;
                while(rs.next()) { count = rs.getInt(1); }

                rs.close();
                return count;
            }
            catch(Exception e) {
                return 0;
            }
            finally {
                DBResultSet.close(dbrs);
            }
	}


	/* This method used to find current data */
	public static int findLimitStart( long oid, int recordToGet, String whereClause){
            String order = "";
            int size = getCount(whereClause);
            int start = 0;
            boolean found =false;
            
            for(int i=0; (i < size) && !found ; i=i+recordToGet){
                 Vector list =  list(i,recordToGet, whereClause, order); 
                 start = i;
                 if(list.size()>0){
                     
                 for(int ls=0;ls<list.size();ls++){ 
                       SysLogger logger = (SysLogger)list.get(ls);
                       if(oid == logger.getOID())
                              found=true;
                 }
              }
            }
            if((start >= size) && (size > 0))
                start = start - recordToGet;

            return start;
	}
}
