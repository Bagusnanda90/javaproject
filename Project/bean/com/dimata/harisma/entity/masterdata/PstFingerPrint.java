
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

package com.dimata.harisma.entity.masterdata;

/* package java */ 

import java.sql.*;
import java.util.*;


/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

public class PstFingerPrint extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_FINGER_PRINT = "hr_finger_print";//"`hr_finger_print`";

	public static final  int FLD_FINGER_PRINT_ID = 0;
	public static final  int FLD_EMPLOYEE_NUM = 1;
	public static final  int FLD_FINGER_PRINT = 2;

	public static final  String[] fieldNames = {
		"FINGER_FRINT_ID",
		"EMPLOYEE_NUM",
		"FINGER_PRINT"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
		TYPE_INT
	 }; 

	public PstFingerPrint(){
	}

	public PstFingerPrint(int i) throws DBException { 
		super(new PstFingerPrint());
	}

	public PstFingerPrint(String sOid) throws DBException { 
		super(new PstFingerPrint(0));
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstFingerPrint(long lOid) throws DBException { 
		super(new PstFingerPrint(0));
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
		return TBL_HR_FINGER_PRINT;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstFingerPrint().getClass().getName();
	}

	public long fetchExc(Entity ent) throws Exception{ 
		FingerPrint fingerPrint = fetchExc(ent.getOID());
		ent = (Entity)fingerPrint;
		return fingerPrint.getOID();
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((FingerPrint) ent);
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((FingerPrint) ent);
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static FingerPrint fetchExc(long oid) throws DBException{
		try{ 
			FingerPrint fingerPrint = new FingerPrint();
			PstFingerPrint pstFingerPrint = new PstFingerPrint(oid);
			fingerPrint.setOID(oid);

			fingerPrint.setEmployeeNum(pstFingerPrint.getString(FLD_EMPLOYEE_NUM));
			fingerPrint.setFingerPrint(pstFingerPrint.getInt(FLD_FINGER_PRINT));

			return fingerPrint;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstFingerPrint(0),DBException.UNKNOWN);
		} 
	}

	public static long insertExc(FingerPrint fingerPrint) throws DBException{
		try{ 
			PstFingerPrint pstFingerPrint = new PstFingerPrint(0);

			pstFingerPrint.setString(FLD_EMPLOYEE_NUM, fingerPrint.getEmployeeNum());
			pstFingerPrint.setInt(FLD_FINGER_PRINT, fingerPrint.getFingerPrint());

			pstFingerPrint.insert();
			fingerPrint.setOID(pstFingerPrint.getlong(FLD_FINGER_PRINT_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstFingerPrint(0),DBException.UNKNOWN);
		}
		return fingerPrint.getOID();
	}

	public static long updateExc(FingerPrint fingerPrint) throws DBException{
		try{ 
			if(fingerPrint.getOID() != 0){
				PstFingerPrint pstFingerPrint = new PstFingerPrint(fingerPrint.getOID());

				pstFingerPrint.setString(FLD_EMPLOYEE_NUM, fingerPrint.getEmployeeNum());
				pstFingerPrint.setInt(FLD_FINGER_PRINT, fingerPrint.getFingerPrint());

				pstFingerPrint.update();
				return fingerPrint.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstFingerPrint(0),DBException.UNKNOWN);
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstFingerPrint pstPosition = new PstFingerPrint(oid);
			pstPosition.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstFingerPrint(0),DBException.UNKNOWN);
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
			String sql = "SELECT * FROM " + TBL_HR_FINGER_PRINT;
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
				FingerPrint fingerPrint = new FingerPrint();
				resultToObject(rs, fingerPrint);
				lists.add(fingerPrint);
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

	public static void resultToObject(ResultSet rs, FingerPrint fingerPrint){
		try{
			fingerPrint.setOID(rs.getLong(PstFingerPrint.fieldNames[PstFingerPrint.FLD_FINGER_PRINT_ID]));
			fingerPrint.setEmployeeNum(rs.getString(PstFingerPrint.fieldNames[PstFingerPrint.FLD_EMPLOYEE_NUM]));
			fingerPrint.setFingerPrint(rs.getInt(PstFingerPrint.fieldNames[PstFingerPrint.FLD_FINGER_PRINT]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long positionId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_FINGER_PRINT+ " WHERE " +
						PstFingerPrint.fieldNames[PstFingerPrint.FLD_FINGER_PRINT_ID] + " = " + positionId;

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
			String sql = "SELECT COUNT("+ PstFingerPrint.fieldNames[PstFingerPrint.FLD_FINGER_PRINT_ID] + ") FROM " + TBL_HR_FINGER_PRINT;
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
			  	   FingerPrint fingerPrint = (FingerPrint)list.get(ls);
				   if(oid == fingerPrint.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}
        
        public static boolean checkEmpNum(String empNum){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_FINGER_PRINT+ " WHERE " +
						PstFingerPrint.fieldNames[PstFingerPrint.FLD_EMPLOYEE_NUM] + " = '" + empNum+"'";

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
}
