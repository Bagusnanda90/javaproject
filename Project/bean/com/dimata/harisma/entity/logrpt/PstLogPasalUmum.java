
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

public class PstLogPasalUmum extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_LOG_PASAL_UMUM = "log_pasal_umum";//"HR_DEPARTMENT";

	public static final  int FLD_PASAL_UMUM_ID = 0;
	public static final  int FLD_PASAL_UMUM= 1;

	public static final  String[] fieldNames = {
		"PASAL_UMUM_ID",
		"PASAL_UMUM"
	 };

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING
	 };

	public PstLogPasalUmum(){
	}

	public PstLogPasalUmum(int i) throws DBException { 
		super(new PstLogPasalUmum()); 
	}

	public PstLogPasalUmum(String sOid) throws DBException { 
		super(new PstLogPasalUmum(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstLogPasalUmum(long lOid) throws DBException { 
		super(new PstLogPasalUmum(0)); 
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
		return TBL_LOG_PASAL_UMUM;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstLogPasalUmum().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		LogPasalUmum logPasalUmum = fetchExc(ent.getOID()); 
		ent = (Entity)logPasalUmum; 
		return logPasalUmum.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((LogPasalUmum) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((LogPasalUmum) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static LogPasalUmum fetchExc(long oid) throws DBException{ 
		try{ 
			LogPasalUmum logPasalUmum = new LogPasalUmum();
			PstLogPasalUmum pstLogPasalUmum = new PstLogPasalUmum(oid); 
			logPasalUmum.setOID(oid);
			logPasalUmum.setPasalUmum(pstLogPasalUmum.getString(FLD_PASAL_UMUM));

			return logPasalUmum;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogPasalUmum(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(LogPasalUmum logPasalUmum) throws DBException{ 
		try{ 
			PstLogPasalUmum pstLogPasalUmum = new PstLogPasalUmum(0);

			pstLogPasalUmum.setString(FLD_PASAL_UMUM, logPasalUmum.getPasalUmum());
			
			pstLogPasalUmum.insert();
			logPasalUmum.setOID(pstLogPasalUmum.getlong(FLD_PASAL_UMUM_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogPasalUmum(0),DBException.UNKNOWN); 
		}
		return logPasalUmum.getOID();
	}

	public static long updateExc(LogPasalUmum logPasalUmum) throws DBException{ 
		try{ 
			if(logPasalUmum.getOID() != 0){ 
				PstLogPasalUmum pstLogPasalUmum = new PstLogPasalUmum(logPasalUmum.getOID());
                                pstLogPasalUmum.setString(FLD_PASAL_UMUM, logPasalUmum.getPasalUmum());

				pstLogPasalUmum.update();
				return logPasalUmum.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogPasalUmum(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstLogPasalUmum pstLogPasalUmum = new PstLogPasalUmum(oid);
			pstLogPasalUmum.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogPasalUmum(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_LOG_PASAL_UMUM; 
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
				LogPasalUmum logPasalUmum = new LogPasalUmum();
				resultToObject(rs, logPasalUmum);
				lists.add(logPasalUmum);
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

	public static void resultToObject(ResultSet rs, LogPasalUmum logPasalUmum){
		try{
			logPasalUmum.setOID(rs.getLong(PstLogPasalUmum.fieldNames[PstLogPasalUmum.FLD_PASAL_UMUM_ID]));
			logPasalUmum.setPasalUmum(rs.getString(PstLogPasalUmum.fieldNames[PstLogPasalUmum.FLD_PASAL_UMUM]));
		}catch(Exception e){ }
	}

	public static boolean checkOID(long reportId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_LOG_PASAL_UMUM + " WHERE " + 
						PstLogPasalUmum.fieldNames[PstLogPasalUmum.FLD_PASAL_UMUM_ID] + " = " + reportId;

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
			String sql = "SELECT COUNT("+ PstLogPasalUmum.fieldNames[PstLogPasalUmum.FLD_PASAL_UMUM_ID] + ") FROM " + TBL_LOG_PASAL_UMUM;
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
			  	   LogPasalUmum logPasalUmum = (LogPasalUmum)list.get(ls);
				   if(oid == logPasalUmum.getOID())
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
