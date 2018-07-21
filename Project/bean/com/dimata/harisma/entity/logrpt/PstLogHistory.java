
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
import com.dimata.harisma.entity.admin.PstAppUser;
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
/*import com.dimata.harisma.entity.masterdata.*;*/
/*import com.dimata.harisma.entity.employee.*;*/

public class PstLogHistory extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_LOG_HISTORY = "log_history";

public static final  int FLD_LOG_HISTORY_ID = 0;
public static final  int FLD_LOG_DATE = 1;
public static final  int FLD_LOG_NOTE= 2;
public static final  int FLD_LOG_OBJ_ID = 3;
public static final  int FLD_USER_ID = 4;

	public static final  String[] fieldNames = {
"LOG_HISTORY_ID",
"LOG_DATE",
"LOG_NOTE",
"LOG_OBJ_ID",
"USER_ID"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,        
		TYPE_DATE,
                TYPE_STRING,
                TYPE_LONG,
                TYPE_LONG                        
	 }; 

	public PstLogHistory(){
	}

	public PstLogHistory(int i) throws DBException { 
		super(new PstLogHistory()); 
	}

	public PstLogHistory(String sOid) throws DBException { 
		super(new PstLogHistory(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstLogHistory(long lOid) throws DBException { 
		super(new PstLogHistory(0)); 
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
		return TBL_LOG_HISTORY;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstLogHistory().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		LogHistory logHistory = fetchExc(ent.getOID());
		ent = (Entity)logHistory;
		return logHistory.getOID();
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((LogHistory) ent);
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((LogHistory) ent);
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static LogHistory fetchExc(long OID) throws DBException{
		try{ 
			LogHistory logHistory = new LogHistory();
			PstLogHistory pstLogHistory = new PstLogHistory(OID);
			logHistory.setOID(OID);
                        logHistory.setLogDate(pstLogHistory.getDate(FLD_LOG_DATE));
                        logHistory.setLogNote(pstLogHistory.getString(FLD_LOG_NOTE));
                        logHistory.setLogObjId(pstLogHistory.getlong(FLD_LOG_OBJ_ID));
                        logHistory.setUserId(pstLogHistory.getlong(FLD_USER_ID));
			return logHistory;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogHistory(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(LogHistory logHistory) throws DBException{
		try{ 
			PstLogHistory pstLogHistory = new PstLogHistory(0);

                        pstLogHistory.setDate(FLD_LOG_DATE, logHistory.getLogDate());
                        pstLogHistory.setString(FLD_LOG_NOTE, logHistory.getLogNote());
                        pstLogHistory.setLong(FLD_LOG_OBJ_ID, logHistory.getLogObjId());
                        pstLogHistory.setLong(FLD_USER_ID, logHistory.getUserId());
			pstLogHistory.insert();
			//logHistory.setOID(pstLogHistory.getlong(FLD_LOG_OBJ_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogHistory(0),DBException.UNKNOWN); 
		}
		return logHistory.getOID();
	}

	public static long updateExc(LogHistory logHistory) throws DBException{
		try{ 
			if(logHistory.getOID() != 0){
				PstLogHistory pstLogHistory = new PstLogHistory(logHistory.getOID());

                        pstLogHistory.setDate(FLD_LOG_DATE, logHistory.getLogDate());
                        pstLogHistory.setString(FLD_LOG_NOTE, logHistory.getLogNote());
                        pstLogHistory.setLong(FLD_LOG_OBJ_ID, logHistory.getLogObjId());
                        pstLogHistory.setLong(FLD_USER_ID, logHistory.getUserId());
				pstLogHistory.update();
				return logHistory.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogHistory(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstLogHistory pstLogHistory = new PstLogHistory(oid);
			pstLogHistory.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogHistory(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_LOG_HISTORY;
                       
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
//                        System.out.println("sql logHistory : " + sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				LogHistory logHistory = new LogHistory();
				resultToObject(rs, logHistory);
				lists.add(logHistory);
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

	private static void resultToObject(ResultSet rs, LogHistory logHistory){
		try{
			logHistory.setOID(rs.getLong(PstLogHistory.fieldNames[PstLogHistory.FLD_LOG_HISTORY_ID]));
                        logHistory.setLogDate(rs.getDate(PstLogHistory.fieldNames[PstLogHistory.FLD_LOG_DATE]));
                        logHistory.setLogNote(rs.getString(PstLogHistory.fieldNames[PstLogHistory.FLD_LOG_NOTE]));
                        logHistory.setLogObjId(rs.getLong(PstLogHistory.fieldNames[PstLogHistory.FLD_LOG_OBJ_ID]));
                        logHistory.setUserId(rs.getLong(PstLogHistory.fieldNames[PstLogHistory.FLD_USER_ID]));
		}catch(Exception e){ }
	}


	public static Vector listWithJoin(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector();
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT a.*, b.* FROM " + TBL_LOG_HISTORY +" as a left join " +
                                PstAppUser.TBL_APP_USER +" as b on a."+ fieldNames[FLD_USER_ID]+"="+
                                "b."+PstAppUser.fieldNames[PstAppUser.FLD_USER_ID] ;

			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
//                        System.out.println("sql logHistory : " + sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				LogHistory logHistory = new LogHistory();
				resultToObjectWithJoin(rs, logHistory);
				lists.add(logHistory);
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

	private static void resultToObjectWithJoin(ResultSet rs, LogHistory logHistory){
		try{
			logHistory.setOID(rs.getLong(PstLogHistory.fieldNames[PstLogHistory.FLD_LOG_HISTORY_ID]));
                        logHistory.setLogDate(rs.getDate(PstLogHistory.fieldNames[PstLogHistory.FLD_LOG_DATE]));
                        logHistory.setLogNote(rs.getString(PstLogHistory.fieldNames[PstLogHistory.FLD_LOG_NOTE]));
                        logHistory.setLogObjId(rs.getLong(PstLogHistory.fieldNames[PstLogHistory.FLD_LOG_OBJ_ID]));
                        logHistory.setUserId(rs.getLong(PstLogHistory.fieldNames[PstLogHistory.FLD_USER_ID]));
                        logHistory.setUserName(rs.getString(PstAppUser.fieldNames[PstAppUser.FLD_FULL_NAME]));
		}catch(Exception e){ }
	}


	public static boolean checkOID(long objId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_LOG_HISTORY + " WHERE " +
						PstLogHistory.fieldNames[PstLogHistory.FLD_LOG_HISTORY_ID] + " = " + objId ;

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
			String sql = "SELECT COUNT("+ PstLogHistory.fieldNames[PstLogHistory.FLD_LOG_OBJ_ID] + ") FROM " + TBL_LOG_HISTORY;
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
			  	   LogHistory logHistory = (LogHistory)list.get(ls);
				   if(oid == logHistory.getOID())
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
