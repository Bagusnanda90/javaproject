/*
 * PstBarcodeLog.java
 *
 * Created on May 14, 2004, 2:08 PM
 */

package com.dimata.harisma.utility.service.tma;   

// package java 
import java.io.*;
import java.sql.*;
import java.util.*;

// package qdep 
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.*;

// package harisma 
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.attendance.PstPresence;

/**
 *
 * @author  gedhy
 */
public class PstBarcodeLog extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 
    
	public static final  String TBL_BARCODE_LOGGER = "hr_barcode_logger";//"HR_BARCODE_LOGGER";

	public static final  int FLD_BARCODE_LOGGER_ID = 0;
        public static final  int FLD_DATE = 1;        
	public static final  int FLD_CMD_TYPE = 2;
        public static final  int FLD_NOTES = 3;

	public static final  String[] fieldNames = {
            "BARCODE_LOGGER_ID",            
            "DATE",            
            "CMD_TYPE",            
            "NOTES"
	 };

	public static final  int[] fieldTypes = {
            TYPE_LONG + TYPE_PK + TYPE_ID,
            TYPE_DATE,            
            TYPE_STRING,            
            TYPE_STRING
	 };

	public PstBarcodeLog() {
	}

	public PstBarcodeLog(int i) throws DBException { 
		super(new PstBarcodeLog());
	}

	public PstBarcodeLog(String sOid) throws DBException { 
		super(new PstBarcodeLog(0));
		if (!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstBarcodeLog(long lOid) throws DBException { 
		super(new PstBarcodeLog(0));
		String sOid = "0"; 
		try { 
			sOid = String.valueOf(lOid); 
		} catch (Exception e) { 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		if (!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	} 

	public int getFieldSize(){ 
		return fieldNames.length; 
	}

	public String getTableName(){ 
		return TBL_BARCODE_LOGGER;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstBarcodeLog().getClass().getName();
	}

	public long fetchExc(Entity ent) throws Exception{ 
		BarcodeLog logger = fetchExc(ent.getOID());
		ent = (Entity) logger;
		return logger.getOID();
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((BarcodeLog) ent);
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((BarcodeLog) ent);
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if (ent==null) { 
			throw new DBException(this, DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static BarcodeLog fetchExc(long oid) throws DBException{
		try { 
			BarcodeLog logger = new BarcodeLog();
			PstBarcodeLog pstLogger = new PstBarcodeLog(oid);
			logger.setOID(oid);			
			logger.setCmdType(pstLogger.getString(FLD_CMD_TYPE));
                        logger.setDate(pstLogger.getDate(FLD_DATE));
                        logger.setNotes(pstLogger.getString(FLD_NOTES));
			return logger;
		} catch (DBException dbe) { 
			throw dbe; 
		} catch (Exception e) { 
			throw new DBException(new PstBarcodeLog(0), DBException.UNKNOWN);
		} 
	}

	public static long insertExc(BarcodeLog logger) throws DBException{
		try { 
			PstBarcodeLog pstLogger = new PstBarcodeLog(0);
			pstLogger.setString(FLD_CMD_TYPE, logger.getCmdType());
                        pstLogger.setDate(FLD_DATE, logger.getDate());
                        pstLogger.setString(FLD_NOTES, logger.getNotes());
			pstLogger.insert();
			logger.setOID(pstLogger.getlong(FLD_BARCODE_LOGGER_ID));
		} catch (DBException dbe) { 
			throw dbe; 
		} catch (Exception e) { 
			throw new DBException(new PstBarcodeLog(0), DBException.UNKNOWN);
		}
		return logger.getOID();
	}

	public static long updateExc(BarcodeLog logger) throws DBException{
		try { 
			if (logger.getOID() != 0) {
				PstBarcodeLog pstLogger = new PstBarcodeLog(logger.getOID());
                                pstLogger.setString(FLD_CMD_TYPE, logger.getCmdType());
                                pstLogger.setDate(FLD_DATE, logger.getDate());
                                pstLogger.setString(FLD_NOTES, logger.getNotes());
				pstLogger.update();
				return logger.getOID();
                        }
		} catch (DBException dbe) { 
			throw dbe; 
		} catch (Exception e) { 
			throw new DBException(new PstBarcodeLog(0), DBException.UNKNOWN);
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException { 
		try { 
			PstBarcodeLog pstLogger = new PstBarcodeLog(oid);
			pstLogger.delete();
		} catch (DBException dbe) { 
			throw dbe; 
		} catch (Exception e) { 
			throw new DBException(new PstBarcodeLog(0), DBException.UNKNOWN);
		}
		return oid;
	}

	public static Vector listAll(){ 
		return list(0, 500, "", ""); 
	}

	public static Vector list(int limitStart, int recordToGet, String whereClause, String order){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_BARCODE_LOGGER;
			if (whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if (order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if (limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ", "+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql); 
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				BarcodeLog logger = new BarcodeLog();
				resultToObject(rs, logger);
				lists.add(logger);
			}
			rs.close();
			return lists;

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBResultSet.close(dbrs);
		}
			return new Vector();
	}

	public static void resultToObject(ResultSet rs, BarcodeLog logger){
            try {
                logger.setOID(rs.getLong(PstBarcodeLog.fieldNames[PstBarcodeLog.FLD_BARCODE_LOGGER_ID]));                    
                logger.setCmdType(rs.getString(PstBarcodeLog.fieldNames[PstBarcodeLog.FLD_CMD_TYPE]));
                java.util.Date tm = DBHandler.convertDate(rs.getDate(PstBarcodeLog.fieldNames[PstBarcodeLog.FLD_DATE]),rs.getTime(PstBarcodeLog.fieldNames[PstBarcodeLog.FLD_DATE]));
                logger.setDate(tm);
                logger.setNotes(rs.getString(PstBarcodeLog.fieldNames[PstBarcodeLog.FLD_NOTES]));
            } catch (Exception e) { 
            }
	}

	public static boolean checkOID(long loggerId) {
            DBResultSet dbrs = null;
            boolean result = false;
            try {
                String sql = "SELECT * FROM " + TBL_BARCODE_LOGGER
                           + " WHERE " + PstBarcodeLog.fieldNames[PstBarcodeLog.FLD_BARCODE_LOGGER_ID]
                           + " = " + loggerId;
                           
		dbrs = DBHandler.execQueryResult(sql);
		ResultSet rs = dbrs.getResultSet();
		
                while (rs.next())
                    result = true;
                
		rs.close();
            } catch (Exception e) {
		System.out.println("Error : " + e.toString());
            } finally {
                DBResultSet.close(dbrs);
            }
            return result;
	}

	public static int getCount(String whereClause){
            DBResultSet dbrs = null;
            try {
            	String sql = "SELECT COUNT(" + PstBarcodeLog.fieldNames[PstBarcodeLog.FLD_BARCODE_LOGGER_ID]
                           + ") FROM " + TBL_BARCODE_LOGGER;
		if (whereClause != null && whereClause.length() > 0)
                    sql = sql + " WHERE " + whereClause;
		
                dbrs = DBHandler.execQueryResult(sql);
		ResultSet rs = dbrs.getResultSet();
		int count = 0;
		
                while (rs.next())
                    count = rs.getInt(1);
		
                rs.close();
		return count;
            } catch (Exception e) {
		return 0;
	    } finally {
		DBResultSet.close(dbrs);
            }
	}
}
