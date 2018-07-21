
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.logrpt; 

/* package java */ 
import java.io.*;
import java.sql.*;
import java.util.*;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
//import com.dimata.harisma.entity.masterdata.*;
//import com.dimata.harisma.entity.employee.*;

public class PstLogReport extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

public static final  String TBL_LOG_REPORT = "log_report";

public static final  int FLD_LOG_REPORT_ID = 0;
public static final  int FLD_LOG_NUMBER = 1;
public static final  int FLD_LOG_DESC = 2;
public static final  int FLD_REPORT_DATE = 3;
public static final  int FLD_RECORD_DATE = 4;
public static final  int FLD_STATUS = 5;
public static final  int FLD_RPT_TYPE_ID = 6;
public static final  int FLD_RPT_CATEGORY_ID = 7;
public static final  int FLD_PASAL_UMUM_ID = 8;
public static final  int FLD_PASAL_KHUSUS_ID = 9;
public static final  int FLD_REPORT_BY_USER_ID = 10;
public static final  int FLD_RECORD_BY_USER_ID = 11;
public static final  int FLD_PIC_USER_ID = 12;
public static final  int FLD_LOG_LOCATION_ID = 13;
public static final  int FLD_DUE_DATETIME = 14;
public static final  int FLD_REAL_FINISH_DATETIME = 15;
public static final  int FLD_LOG_CUSTOMER_ID = 16;
public static final  int FLD_LOG_PRIORITY = 17;
public static final  int FLD_INFORMATION = 17;

	public static final  String[] fieldNames = {
            "LOG_REPORT_ID",
            "LOG_NUMBER",
            "LOG_DESC",
            "REPORT_DATE",
            "RECORD_DATE",
            "STATUS",
            "RPT_TYPE_ID",
            "RPT_CATEGORY_ID",
            "PASAL_UMUM_ID",
            "PASAL_KHUSUS_ID",
            "REPORT_BY_USER_ID",
            "RECORD_BY_USER_ID",
            "PIC_USER_ID",
            "LOG_LOCATION_ID",
            "DUE_DATETIME",
            "REAL_FINISH_DATETIME",
            "CUSTOMER_ID",
            "PRIORITY",
            "INFORMATION"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,        
		TYPE_STRING + TYPE_AI,
                TYPE_STRING,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_INT,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_LONG,
                TYPE_INT,
                TYPE_INT
	 }; 

	public PstLogReport(){
	}

	public PstLogReport(int i) throws DBException { 
		super(new PstLogReport()); 
	}

	public PstLogReport(String sOid) throws DBException { 
		super(new PstLogReport(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstLogReport(long lOid) throws DBException { 
		super(new PstLogReport(0)); 
		String sOid = "0"; 
		try { 
			sOid = String.valueOf(lOid); 
		}catch(Exception e) { 
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
		return TBL_LOG_REPORT;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstLogReport().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		LogReport logReport = fetchExc(ent.getOID()); 
		ent = (Entity)logReport; 
		return logReport.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((LogReport) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((LogReport) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static LogReport fetchExc(long oid) throws DBException{ 
		try{ 
			LogReport logReport = new LogReport();
			PstLogReport pstLogReport = new PstLogReport(oid); 
			logReport.setOID(oid);
                        logReport.setLogNumber(pstLogReport.getString(FLD_LOG_NUMBER));			
                        logReport.setLogDesc(pstLogReport.getString(FLD_LOG_DESC));			
                        logReport.setReportDate(pstLogReport.getDate(FLD_REPORT_DATE));			
                        logReport.setRecordDate(pstLogReport.getDate(FLD_RECORD_DATE));			
                        logReport.setStatus(pstLogReport.getInt(FLD_STATUS));			
                        logReport.setRptTypeId(pstLogReport.getlong(FLD_RPT_TYPE_ID));			
                        logReport.setRptCategoryId(pstLogReport.getlong(FLD_RPT_CATEGORY_ID));			
                        logReport.setPasalUmumId(pstLogReport.getlong(FLD_PASAL_UMUM_ID));			
                        logReport.setPasalKhususId(pstLogReport.getlong(FLD_PASAL_KHUSUS_ID));			
                        logReport.setReportByUserId(pstLogReport.getlong(FLD_REPORT_BY_USER_ID));			
                        logReport.setRecordByUserId(pstLogReport.getlong(FLD_RECORD_BY_USER_ID));			
                        logReport.setPicUserId(pstLogReport.getlong(FLD_PIC_USER_ID));			
                        logReport.setLogLocationId(pstLogReport.getlong(FLD_LOG_LOCATION_ID));			
                        logReport.setDueDateTime(pstLogReport.getDate(FLD_DUE_DATETIME));			
                        logReport.setRealFinishDateTime(pstLogReport.getDate(FLD_REAL_FINISH_DATETIME));			                        
                        logReport.setLogCustomerId(pstLogReport.getlong(FLD_LOG_CUSTOMER_ID));
                        logReport.setPriority(pstLogReport.getInt(FLD_LOG_PRIORITY));
                        logReport.setReportInformation(pstLogReport.getInt(FLD_INFORMATION));
                        
			return logReport; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogReport(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(LogReport logReport) throws DBException{ 
		try{ 
			PstLogReport pstLogReport = new PstLogReport(0);

                        pstLogReport.setString(FLD_LOG_NUMBER,logReport.getLogNumber());			
                        pstLogReport.setString(FLD_LOG_DESC,logReport.getLogDesc());
                        pstLogReport.setDate(FLD_REPORT_DATE,logReport.getReportDate());			
                        pstLogReport.setDate(FLD_RECORD_DATE,logReport.getRecordDate());			
                        pstLogReport.setInt(FLD_STATUS,logReport.getStatus());			
                        pstLogReport.setLong(FLD_RPT_TYPE_ID,logReport.getRptTypeId());			
                        pstLogReport.setLong(FLD_RPT_CATEGORY_ID,logReport.getRptCategoryId());			
                        pstLogReport.setLong(FLD_PASAL_UMUM_ID,logReport.getPasalUmumId());			
                        pstLogReport.setLong(FLD_PASAL_KHUSUS_ID,logReport.getPasalKhususId());			
                        pstLogReport.setLong(FLD_REPORT_BY_USER_ID,logReport.getReportByUserId());			
                        pstLogReport.setLong(FLD_RECORD_BY_USER_ID,logReport.getRecordByUserId());			
                        pstLogReport.setLong(FLD_PIC_USER_ID,logReport.getPicUserId());			
                        pstLogReport.setLong(FLD_LOG_LOCATION_ID,logReport.getLogLocationId());			
                        pstLogReport.setDate(FLD_DUE_DATETIME, logReport.getDueDateTime());			
                        pstLogReport.setDate(FLD_REAL_FINISH_DATETIME, logReport.getRealFinishDateTime());			                        
                        pstLogReport.setLong(FLD_LOG_CUSTOMER_ID, logReport.getLogCustomerId());
                        pstLogReport.setInt(FLD_LOG_PRIORITY, logReport.getPriority());
                        pstLogReport.setInt(FLD_INFORMATION, logReport.getReportInformation());
			pstLogReport.insert(); 
			logReport.setOID(pstLogReport.getlong(FLD_LOG_REPORT_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogReport(0),DBException.UNKNOWN); 
		}
		return logReport.getOID();
	}

	public static long updateExc(LogReport logReport) throws DBException{ 
		try{ 
			if(logReport.getOID() != 0){ 
				PstLogReport pstLogReport = new PstLogReport(logReport.getOID());

                        pstLogReport.setString(FLD_LOG_NUMBER,logReport.getLogNumber());			
                        pstLogReport.setString(FLD_LOG_DESC,logReport.getLogDesc());			
                        pstLogReport.setDate(FLD_REPORT_DATE,logReport.getReportDate());			
                        pstLogReport.setDate(FLD_RECORD_DATE,logReport.getRecordDate());			
                        pstLogReport.setInt(FLD_STATUS,logReport.getStatus());			
                        pstLogReport.setLong(FLD_RPT_TYPE_ID,logReport.getRptTypeId());			
                        pstLogReport.setLong(FLD_RPT_CATEGORY_ID,logReport.getRptCategoryId());			
                        pstLogReport.setLong(FLD_PASAL_UMUM_ID,logReport.getPasalUmumId());			
                        pstLogReport.setLong(FLD_PASAL_KHUSUS_ID,logReport.getPasalKhususId());			
                        pstLogReport.setLong(FLD_REPORT_BY_USER_ID,logReport.getReportByUserId());			
                        pstLogReport.setLong(FLD_RECORD_BY_USER_ID,logReport.getRecordByUserId());			
                        pstLogReport.setLong(FLD_PIC_USER_ID,logReport.getPicUserId());			
                        pstLogReport.setLong(FLD_LOG_LOCATION_ID,logReport.getLogLocationId());			
                        pstLogReport.setDate(FLD_DUE_DATETIME, logReport.getDueDateTime());			
                        pstLogReport.setDate(FLD_REAL_FINISH_DATETIME, logReport.getRealFinishDateTime());
                        pstLogReport.setLong(FLD_LOG_CUSTOMER_ID, logReport.getLogCustomerId());
                        pstLogReport.setInt(FLD_LOG_PRIORITY, logReport.getPriority());
                        pstLogReport.setInt(FLD_INFORMATION, logReport.getReportInformation());
                        
				pstLogReport.update(); 
				return logReport.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogReport(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstLogReport pstLogReport = new PstLogReport(oid);
			pstLogReport.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogReport(0),DBException.UNKNOWN); 
		}
		return oid;
	}

	public static Vector listAll(){ 
		return list(0, 1000, "",""); 
	}

	public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_LOG_REPORT; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
//                        System.out.println("sql logReport : " + sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				LogReport logReport = new LogReport();
				resultToObject(rs, logReport);
				lists.add(logReport);
			}
			rs.close();
			return lists;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return new Vector();
	}
        
        /**
         * 
         * @param limitStart
         * @param recordToGet
         * @param whereClause
         * @param order
         * @return Vector of LogReportListItem
         */
	public static Vector listSearch(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql =   
                        "SELECT l.*, l."+fieldNames[FLD_LOG_DESC]+", t.TYPE_NAME, c.CATEGORY_NAME, d.PASAL_UMUM, e.PASAL_KHUSUS, f.LOC_NAME FROM "+ TBL_LOG_REPORT + " as l "
                         + " left join log_report_type t on l.RPT_TYPE_ID=t.RPT_TYPE_ID " 
                         + " left Join log_category c ON l.RPT_CATEGORY_ID=c.RPT_CATEGORY_ID " 
                         + " left Join log_pasal_umum d ON l.PASAL_UMUM_ID=d.PASAL_UMUM_ID "
                         + " left Join log_pasal_khusus e ON l.PASAL_KHUSUS_ID=e.PASAL_KHUSUS_ID "
                         + " left join log_location f on l.LOG_LOCATION_ID=f.LOG_LOCATION_ID ";
                        
                        
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
//                        System.out.println("sql logReport : " + sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				LogReportListItem logReportListItem = new LogReportListItem();
				resultToObject(rs, logReportListItem);
                                logReportListItem.setLogTypeName(rs.getString(PstLogReportType.fieldNames[PstLogReportType.FLD_TYPE_NAME]));
                                logReportListItem.setLogCategoryName(rs.getString(PstLogCategory.fieldNames[PstLogCategory.FLD_CATEGORY_NAME]));
                                logReportListItem.setLogPasalUmum(rs.getString(PstLogPasalUmum.fieldNames[PstLogPasalUmum.FLD_PASAL_UMUM]));
                                logReportListItem.setLogPasalKhusus(rs.getString(PstLogPasalKhusus.fieldNames[PstLogPasalKhusus.FLD_PASAL_KHUSUS]));
                                logReportListItem.setLogLocation(rs.getString(PstLogLocation.fieldNames[PstLogLocation.FLD_LOC_NAME]));
                                lists.add(logReportListItem);
			}
			rs.close();
			return lists;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return new Vector();
	}
        
        


	private static void resultToObject(ResultSet rs, LogReport logReport){
		try{
			logReport.setOID(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_LOG_REPORT_ID]));
                        logReport.setLogNumber(rs.getString(PstLogReport.fieldNames[PstLogReport.FLD_LOG_NUMBER]));			
                        logReport.setLogDesc(rs.getString(PstLogReport.fieldNames[PstLogReport.FLD_LOG_DESC]));			
                        logReport.setReportDate(rs.getDate(PstLogReport.fieldNames[PstLogReport.FLD_REPORT_DATE]));			
                        logReport.setRecordDate(rs.getDate(PstLogReport.fieldNames[PstLogReport.FLD_RECORD_DATE]));			
                        logReport.setStatus(rs.getInt(PstLogReport.fieldNames[PstLogReport.FLD_STATUS]));			
                        logReport.setRptTypeId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_RPT_TYPE_ID]));			
                        logReport.setRptCategoryId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_RPT_CATEGORY_ID]));			
                        logReport.setPasalUmumId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_PASAL_UMUM_ID]));			
                        logReport.setPasalKhususId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_PASAL_KHUSUS_ID]));			
                        logReport.setReportByUserId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_REPORT_BY_USER_ID]));			
                        logReport.setRecordByUserId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_RECORD_BY_USER_ID]));			
                        logReport.setPicUserId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_PIC_USER_ID]));			
                        logReport.setLogLocationId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_LOG_LOCATION_ID]));			
                        logReport.setDueDateTime(rs.getDate(PstLogReport.fieldNames[PstLogReport.FLD_DUE_DATETIME]));			
                        logReport.setRealFinishDateTime(rs.getDate(PstLogReport.fieldNames[PstLogReport.FLD_REAL_FINISH_DATETIME]));			                        
                        logReport.setLogCustomerId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_LOG_CUSTOMER_ID]));
                        logReport.setPriority(rs.getInt(PstLogReport.fieldNames[PstLogReport.FLD_LOG_PRIORITY]));
                        logReport.setReportInformation(rs.getInt(PstLogReport.fieldNames[PstLogReport.FLD_INFORMATION]));
		}catch(Exception e){ }
	}

	public static boolean checkOID(long reportId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_LOG_REPORT + " WHERE " + 
						PstLogReport.fieldNames[PstLogReport.FLD_LOG_REPORT_ID] + " = " + reportId ;

			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) { result = true; break; }
			rs.close();
		}catch(Exception e){
			System.out.println("err : "+e.toString());
		}finally{
			DBResultSet.close(dbrs);
			return result;
		}
	}

	public static int getCount(String whereClause){
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT COUNT("+ PstLogReport.fieldNames[PstLogReport.FLD_LOG_REPORT_ID] + ") FROM " + TBL_LOG_REPORT;
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;

			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			int count = 0;
			while(rs.next()) { count = rs.getInt(1); }

			rs.close();
			return count;
		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);
		}
	}


	/* This method used to find current data */
	public static int findLimitStart( long oid, int recordToGet, String whereClause, String order){
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, order); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   LogReport logReport = (LogReport)list.get(ls);
				   if(oid == logReport.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}


    public static boolean checkLogreportByType(long rptTypeId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_LOG_REPORT + " WHERE " + 
						PstLogReport.fieldNames[PstLogReport.FLD_RPT_TYPE_ID] + " = " + rptTypeId;

			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) { result = true; break; }
			rs.close();
		}catch(Exception e){
			System.out.println("err : "+e.toString());
		}finally{
			DBResultSet.close(dbrs);
			return result;
		}
	}


        public static int findLimitCommand(int start, int recordToGet, int vectSize)
    {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + mdl;
    	if(start == 0)
            cmd =  Command.FIRST;
        else{
            if(start == (vectSize-recordToGet))
                cmd = Command.LAST;
            else{
            	start = start + recordToGet;
             	if(start <= (vectSize - recordToGet)){
                 	cmd = Command.NEXT;
                    System.out.println("next.......................");
             	}else{
                    start = start - recordToGet;
		             if(start > 0){
                         cmd = Command.PREV;
                         System.out.println("prev.......................");
		             }
                }
            }
        }

        return cmd;
    }

}
