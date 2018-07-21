
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
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.*;

/* package HRIS */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
/*import com.dimata.harisma.entity.masterdata.*; */
/*import com.dimata.harisma.entity.employee.*;*/

public class PstLogReportType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_LOG_REPORT_TYPE = "log_report_type";//"HR_DEPARTMENT";

	public static final  int FLD_RPT_TYPE_ID = 0;
	public static final  int FLD_TYPE_NAME= 1;

	public static final  String[] fieldNames = {
		"RPT_TYPE_ID",
		"TYPE_NAME"
	 };

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING
	 };

	public PstLogReportType(){
	}

	public PstLogReportType(int i) throws DBException { 
		super(new PstLogReportType()); 
	}

	public PstLogReportType(String sOid) throws DBException { 
		super(new PstLogReportType(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstLogReportType(long lOid) throws DBException { 
		super(new PstLogReportType(0)); 
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
		return TBL_LOG_REPORT_TYPE;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstLogReportType().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		LogReportType logReportType = fetchExc(ent.getOID()); 
		ent = (Entity)logReportType; 
		return logReportType.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((LogReportType) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((LogReportType) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static LogReportType fetchExc(long oid) throws DBException{ 
		try{ 
			LogReportType logReportType = new LogReportType();
			PstLogReportType pstLogReportType = new PstLogReportType(oid); 
			logReportType.setOID(oid);
			logReportType.setTypeName(pstLogReportType.getString(FLD_TYPE_NAME));

			return logReportType;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogReportType(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(LogReportType logReportType) throws DBException{ 
		try{ 
			PstLogReportType pstLogReportType = new PstLogReportType(0);

			pstLogReportType.setString(FLD_TYPE_NAME, logReportType.getTypeName());
			
			pstLogReportType.insert();
			logReportType.setOID(pstLogReportType.getlong(FLD_RPT_TYPE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogReportType(0),DBException.UNKNOWN); 
		}
		return logReportType.getOID();
	}

	public static long updateExc(LogReportType logReportType) throws DBException{ 
		try{ 
			if(logReportType.getOID() != 0){ 
				PstLogReportType pstLogReportType = new PstLogReportType(logReportType.getOID());
                                pstLogReportType.setString(FLD_TYPE_NAME, logReportType.getTypeName());

				pstLogReportType.update();
				return logReportType.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogReportType(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstLogReportType pstLogReportType = new PstLogReportType(oid);
			pstLogReportType.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogReportType(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_LOG_REPORT_TYPE; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				LogReportType logReportType = new LogReportType();
				resultToObject(rs, logReportType);
				lists.add(logReportType);
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

	public static void resultToObject(ResultSet rs, LogReportType logReportType){
		try{
			logReportType.setOID(rs.getLong(PstLogReportType.fieldNames[PstLogReportType.FLD_RPT_TYPE_ID]));
			logReportType.setTypeName(rs.getString(PstLogReportType.fieldNames[PstLogReportType.FLD_TYPE_NAME]));
		}catch(Exception e){ }
	}

	public static boolean checkOID(long reportId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_LOG_REPORT_TYPE + " WHERE " + 
						PstLogReportType.fieldNames[PstLogReportType.FLD_RPT_TYPE_ID] + " = " + reportId;

			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) { result = true; }
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
			String sql = "SELECT COUNT("+ PstLogReportType.fieldNames[PstLogReportType.FLD_RPT_TYPE_ID] + ") FROM " + TBL_LOG_REPORT_TYPE;
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
			  	   LogReportType logReportType = (LogReportType)list.get(ls);
				   if(oid == logReportType.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
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
