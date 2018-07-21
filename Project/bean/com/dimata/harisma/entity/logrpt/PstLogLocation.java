
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
/*import com.dimata.harisma.entity.masterdata.*;*/ 
/*import com.dimata.harisma.entity.employee.*;*/

public class PstLogLocation extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_LOG_LOCATION = "log_location";//"HR_DEPARTMENT";

	public static final  int FLD_LOG_LOCATION_ID = 0;
	public static final  int FLD_LOC_NAME= 1;
        public static final int FLD_CUSTOMER_ID=2;

	public static final  String[] fieldNames = {
		"LOG_LOCATION_ID",
		"LOC_NAME",
                "CUSTOMER_ID"
	 };

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
                TYPE_LONG
	 };

	public PstLogLocation(){
	}

	public PstLogLocation(int i) throws DBException { 
		super(new PstLogLocation()); 
	}

	public PstLogLocation(String sOid) throws DBException { 
		super(new PstLogLocation(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstLogLocation(long lOid) throws DBException { 
		super(new PstLogLocation(0)); 
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
		return TBL_LOG_LOCATION;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstLogLocation().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		LogLocation logLocation = fetchExc(ent.getOID()); 
		ent = (Entity)logLocation; 
		return logLocation.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((LogLocation) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((LogLocation) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static LogLocation fetchExc(long oid) throws DBException{ 
		try{ 
			LogLocation logLocation = new LogLocation();
			PstLogLocation pstLogLocation = new PstLogLocation(oid); 
			logLocation.setOID(oid);
			logLocation.setLocName(pstLogLocation.getString(FLD_LOC_NAME));
                        logLocation.setCustomerId(pstLogLocation.getlong(FLD_CUSTOMER_ID));
			return logLocation;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogLocation(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(LogLocation logLocation) throws DBException{ 
		try{ 
			PstLogLocation pstLogLocation = new PstLogLocation(0);

			pstLogLocation.setString(FLD_LOC_NAME, logLocation.getLocName());
			pstLogLocation.setLong(FLD_CUSTOMER_ID, logLocation.getCustomerId());
			pstLogLocation.insert();
			logLocation.setOID(pstLogLocation.getlong(FLD_LOG_LOCATION_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogLocation(0),DBException.UNKNOWN); 
		}
		return logLocation.getOID();
	}

	public static long updateExc(LogLocation logLocation) throws DBException{ 
		try{ 
			if(logLocation.getOID() != 0){ 
				PstLogLocation pstLogLocation = new PstLogLocation(logLocation.getOID());
                                pstLogLocation.setString(FLD_LOC_NAME, logLocation.getLocName());
                                pstLogLocation.setLong(FLD_CUSTOMER_ID, logLocation.getCustomerId());
				pstLogLocation.update();
				return logLocation.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogLocation(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstLogLocation pstLogLocation = new PstLogLocation(oid);
			pstLogLocation.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogLocation(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_LOG_LOCATION; 
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
				LogLocation logLocation = new LogLocation();
				resultToObject(rs, logLocation);
				lists.add(logLocation);
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

	public static void resultToObject(ResultSet rs, LogLocation logLocation){
		try{
			logLocation.setOID(rs.getLong(PstLogLocation.fieldNames[PstLogLocation.FLD_LOG_LOCATION_ID]));
			logLocation.setLocName(rs.getString(PstLogLocation.fieldNames[PstLogLocation.FLD_LOC_NAME]));
                        logLocation.setCustomerId(rs.getLong(PstLogLocation.fieldNames[PstLogLocation.FLD_CUSTOMER_ID]));
		}catch(Exception e){ }
	}

	public static boolean checkOID(long logLocationId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_LOG_LOCATION + " WHERE " + 
						PstLogLocation.fieldNames[PstLogLocation.FLD_LOG_LOCATION_ID] + " = " + logLocationId;

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
			String sql = "SELECT COUNT("+ PstLogLocation.fieldNames[PstLogLocation.FLD_LOG_LOCATION_ID] + ") FROM " + TBL_LOG_LOCATION;
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
			  	   LogLocation logLocation = (LogLocation)list.get(ls);
				   if(oid == logLocation.getOID())
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
