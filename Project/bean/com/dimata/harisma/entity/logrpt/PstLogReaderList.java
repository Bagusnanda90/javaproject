
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

public class PstLogReaderList extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_LOG_READER_LIST = "log_reader_list";

public static final  int FLD_READ_LIST_ID = 0;
public static final  int FLD_READ_DATE_TIME = 1;
public static final  int FLD_COMMENT = 2;
public static final  int FLD_LOG_REPORT_ID = 3;
public static final  int FLD_USER_ID = 4;

	public static final  String[] fieldNames = {
"READ_LIST_ID",
"READ_DATE_TIME",
"COMMENT",
"LOG_REPORT_ID",
"USER_ID"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,        
		TYPE_DATE,
                TYPE_STRING,
                TYPE_LONG,
                TYPE_LONG                        
	 }; 

	public PstLogReaderList(){
	}

	public PstLogReaderList(int i) throws DBException { 
		super(new PstLogReaderList()); 
	}

	public PstLogReaderList(String sOid) throws DBException { 
		super(new PstLogReaderList(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstLogReaderList(long lOid) throws DBException { 
		super(new PstLogReaderList(0)); 
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
		return TBL_LOG_READER_LIST;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstLogReaderList().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		LogReaderList logReaderList = fetchExc(ent.getOID()); 
		ent = (Entity)logReaderList; 
		return logReaderList.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((LogReaderList) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((LogReaderList) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static LogReaderList fetchExc(long OID) throws DBException{ 
		try{ 
			LogReaderList logReaderList = new LogReaderList();
			PstLogReaderList pstLogReaderList = new PstLogReaderList(OID); 
			logReaderList.setOID(OID);
                        logReaderList.setReadDateTime(pstLogReaderList.getDate(FLD_READ_DATE_TIME));			
                        logReaderList.setComment(pstLogReaderList.getString(FLD_COMMENT));			
                        logReaderList.setLogReportId(pstLogReaderList.getlong(FLD_LOG_REPORT_ID));			
                        logReaderList.setUserId(pstLogReaderList.getlong(FLD_USER_ID));			
			return logReaderList; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogReaderList(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(LogReaderList logReaderList) throws DBException{ 
		try{ 
			PstLogReaderList pstLogReaderList = new PstLogReaderList(0);

                        pstLogReaderList.setDate(FLD_READ_DATE_TIME, logReaderList.getReadDateTime());			
                        pstLogReaderList.setString(FLD_COMMENT, logReaderList.getComment());			
                        pstLogReaderList.setLong(FLD_LOG_REPORT_ID, logReaderList.getLogReportId());			
                        pstLogReaderList.setLong(FLD_USER_ID, logReaderList.getUserId());			                        
			pstLogReaderList.insert(); 
			logReaderList.setOID(pstLogReaderList.getlong(FLD_LOG_REPORT_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogReaderList(0),DBException.UNKNOWN); 
		}
		return logReaderList.getOID();
	}

	public static long updateExc(LogReaderList logReaderList) throws DBException{ 
		try{ 
			if(logReaderList.getOID() != 0){ 
				PstLogReaderList pstLogReaderList = new PstLogReaderList(logReaderList.getOID());

                        pstLogReaderList.setDate(FLD_READ_DATE_TIME, logReaderList.getReadDateTime());			
                        pstLogReaderList.setString(FLD_COMMENT, logReaderList.getComment());			
                        pstLogReaderList.setLong(FLD_LOG_REPORT_ID, logReaderList.getLogReportId());			
                        pstLogReaderList.setLong(FLD_USER_ID, logReaderList.getUserId());			
				pstLogReaderList.update(); 
				return logReaderList.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogReaderList(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstLogReaderList pstLogReaderList = new PstLogReaderList(oid);
			pstLogReaderList.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogReaderList(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_LOG_READER_LIST; 
                        
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
//                        System.out.println("sql logReaderList : " + sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				LogReaderList logReaderList = new LogReaderList();
				resultToObject(rs, logReaderList);
				lists.add(logReaderList);
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

	private static void resultToObject(ResultSet rs, LogReaderList logReaderList){
		try{
			logReaderList.setOID(rs.getLong(PstLogReaderList.fieldNames[PstLogReaderList.FLD_READ_LIST_ID]));
                        logReaderList.setReadDateTime(rs.getDate(PstLogReaderList.fieldNames[PstLogReaderList.FLD_READ_DATE_TIME]));			
                        logReaderList.setComment(rs.getString(PstLogReaderList.fieldNames[PstLogReaderList.FLD_COMMENT]));			
                        logReaderList.setLogReportId(rs.getLong(PstLogReaderList.fieldNames[PstLogReaderList.FLD_LOG_REPORT_ID]));			
                        logReaderList.setUserId(rs.getLong(PstLogReaderList.fieldNames[PstLogReaderList.FLD_USER_ID]));			
		}catch(Exception e){ }
	}


	public static Vector listWithJoin(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector();
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT a.*, b.* FROM " + TBL_LOG_READER_LIST +" as a inner join " +
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
//                        System.out.println("sql logReaderList : " + sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				LogReaderList logReaderList = new LogReaderList();
				resultToObjectWithJoin(rs, logReaderList);
				lists.add(logReaderList);
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

        public static boolean checkRead(int limitStart,int recordToGet, String whereClause, String order){
		//Vector lists = new Vector();
		DBResultSet dbrs = null;
                boolean result = false;
		try {
			String sql = "SELECT a.*, b.* FROM " + TBL_LOG_READER_LIST +" as a inner join " +
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
//                        System.out.println("sql logReaderList : " + sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) { result = true; break; }
			rs.close();
			return result;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return result;
	}

	private static void resultToObjectWithJoin(ResultSet rs, LogReaderList logReaderList){
		try{
			logReaderList.setOID(rs.getLong(PstLogReaderList.fieldNames[PstLogReaderList.FLD_READ_LIST_ID]));
                        logReaderList.setReadDateTime(rs.getDate(PstLogReaderList.fieldNames[PstLogReaderList.FLD_READ_DATE_TIME]));
                        logReaderList.setComment(rs.getString(PstLogReaderList.fieldNames[PstLogReaderList.FLD_COMMENT]));
                        logReaderList.setLogReportId(rs.getLong(PstLogReaderList.fieldNames[PstLogReaderList.FLD_LOG_REPORT_ID]));
                        logReaderList.setUserId(rs.getLong(PstLogReaderList.fieldNames[PstLogReaderList.FLD_USER_ID]));
                        logReaderList.setUserName(rs.getString(PstAppUser.fieldNames[PstAppUser.FLD_FULL_NAME]));
		}catch(Exception e){ }
	}


	public static boolean checkOID(long readId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_LOG_READER_LIST + " WHERE " + 
						PstLogReaderList.fieldNames[PstLogReaderList.FLD_READ_LIST_ID] + " = " + readId ;

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
			String sql = "SELECT COUNT("+ PstLogReaderList.fieldNames[PstLogReaderList.FLD_LOG_REPORT_ID] + ") FROM " + TBL_LOG_READER_LIST;
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
			  	   LogReaderList logReaderList = (LogReaderList)list.get(ls);
				   if(oid == logReaderList.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}


    public static boolean checkReaderByReportID(long rptId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_LOG_READER_LIST + " WHERE " + 
						PstLogReaderList.fieldNames[PstLogReaderList.FLD_LOG_REPORT_ID] + " = " + rptId+ " LIMIT 0,1";

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
