
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
/*import com.dimata.harisma.entity.masterdata.*;*/
/*import com.dimata.harisma.entity.employee.*;*/

public class PstLogCategory extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_LOG_CATEGORY = "log_category";

	public static final  int FLD_RPT_CATEGORY_ID = 0;
    public static final  int FLD_RPT_TYPE_ID = 1;
	public static final  int FLD_CATEGORY_NAME = 2;

	public static final  String[] fieldNames = {
		"RPT_CATEGORY_ID",
        "RPT_TYPE_ID",
		"CATEGORY_NAME"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
		TYPE_STRING
	 }; 

	public PstLogCategory(){
	}

	public PstLogCategory(int i) throws DBException { 
		super(new PstLogCategory()); 
	}

	public PstLogCategory(String sOid) throws DBException { 
		super(new PstLogCategory(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstLogCategory(long lOid) throws DBException { 
		super(new PstLogCategory(0)); 
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
		return TBL_LOG_CATEGORY;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstLogCategory().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		LogCategory logCategory = fetchExc(ent.getOID()); 
		ent = (Entity)logCategory; 
		return logCategory.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((LogCategory) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((LogCategory) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static LogCategory fetchExc(long oid) throws DBException{ 
		try{ 
			LogCategory logCategory = new LogCategory();
			PstLogCategory pstLogCategory = new PstLogCategory(oid); 
			logCategory.setOID(oid);

                        logCategory.setReportTypeId(pstLogCategory.getlong(FLD_RPT_TYPE_ID));
			logCategory.setCategoryName(pstLogCategory.getString(FLD_CATEGORY_NAME));			

			return logCategory; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogCategory(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(LogCategory logCategory) throws DBException{ 
		try{ 
			PstLogCategory pstLogCategory = new PstLogCategory(0);

            pstLogCategory.setLong(FLD_RPT_TYPE_ID, logCategory.getReportTypeId());
			pstLogCategory.setString(FLD_CATEGORY_NAME, logCategory.getCategoryName());
			

			pstLogCategory.insert(); 
			logCategory.setOID(pstLogCategory.getlong(FLD_RPT_CATEGORY_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogCategory(0),DBException.UNKNOWN); 
		}
		return logCategory.getOID();
	}

	public static long updateExc(LogCategory logCategory) throws DBException{ 
		try{ 
			if(logCategory.getOID() != 0){ 
				PstLogCategory pstLogCategory = new PstLogCategory(logCategory.getOID());

                pstLogCategory.setLong(FLD_RPT_TYPE_ID, logCategory.getReportTypeId());
				pstLogCategory.setString(FLD_CATEGORY_NAME, logCategory.getCategoryName());				
				pstLogCategory.update(); 
				return logCategory.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogCategory(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstLogCategory pstLogCategory = new PstLogCategory(oid);
			pstLogCategory.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLogCategory(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_LOG_CATEGORY; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
//                        System.out.println("sql logCategory : " + sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				LogCategory logCategory = new LogCategory();
				resultToObject(rs, logCategory);
				lists.add(logCategory);
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

	private static void resultToObject(ResultSet rs, LogCategory logCategory){
		try{
			logCategory.setOID(rs.getLong(PstLogCategory.fieldNames[PstLogCategory.FLD_RPT_CATEGORY_ID]));
       		logCategory.setReportTypeId(rs.getLong(PstLogCategory.fieldNames[PstLogCategory.FLD_RPT_TYPE_ID]));
			logCategory.setCategoryName(rs.getString(PstLogCategory.fieldNames[PstLogCategory.FLD_CATEGORY_NAME]));			

		}catch(Exception e){ }
	}

	public static boolean checkOID(long rptCategoryId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_LOG_CATEGORY + " WHERE " + 
						PstLogCategory.fieldNames[PstLogCategory.FLD_RPT_CATEGORY_ID] + " = " + rptCategoryId;

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
			String sql = "SELECT COUNT("+ PstLogCategory.fieldNames[PstLogCategory.FLD_RPT_CATEGORY_ID] + ") FROM " + TBL_LOG_CATEGORY;
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
			  	   LogCategory logCategory = (LogCategory)list.get(ls);
				   if(oid == logCategory.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}


    public static boolean checkLogCategory(long categoryId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_LOG_CATEGORY + " WHERE " + 
						PstLogCategory.fieldNames[PstLogCategory.FLD_RPT_TYPE_ID] + " = " + categoryId;

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
