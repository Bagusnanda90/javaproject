
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya
 * @version  	: 01
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.clinic; 

/* package java */ 
import java.io.*
;
import java.sql.*
;import java.util.*
;import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.clinic.*; 

public class PstMedExpenseType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_MEDICAL_EXPENSE_TYPE = "hr_medical_expense_type";//"HR_MEDICAL_EXPENSE_TYPE";

	public static final  int FLD_MEDICINE_EXPENSE_TYPE_ID = 0;
	public static final  int FLD_TYPE = 1;
        // Add Field show status | 2015-01-12 | Hendra McHen
        public static final int FLD_SHOW_STATUS = 2;
        public static final int SHOW_NO = 0;
        public static final int SHOW_YES = 1;
        public static final String[] showKey = {"No", "Yes"};
        public static final int[] showValue = {0, 1};

	public static final  String[] fieldNames = {
		"MEDICINE_EXPENSE_TYPE_ID",
		"TYPE", "SHOW_STATUS"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING, TYPE_INT
	 }; 

	public PstMedExpenseType(){
	}

	public PstMedExpenseType(int i) throws DBException { 
		super(new PstMedExpenseType()); 
	}

	public PstMedExpenseType(String sOid) throws DBException { 
		super(new PstMedExpenseType(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstMedExpenseType(long lOid) throws DBException { 
		super(new PstMedExpenseType(0)); 
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
		return TBL_HR_MEDICAL_EXPENSE_TYPE;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstMedExpenseType().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		MedExpenseType medexpensetype = fetchExc(ent.getOID()); 
		ent = (Entity)medexpensetype; 
		return medexpensetype.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((MedExpenseType) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((MedExpenseType) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static MedExpenseType fetchExc(long oid) throws DBException{ 
		try{ 
			MedExpenseType medexpensetype = new MedExpenseType();
			PstMedExpenseType pstMedExpenseType = new PstMedExpenseType(oid); 
			medexpensetype.setOID(oid);
			medexpensetype.setType(pstMedExpenseType.getString(FLD_TYPE));
                        medexpensetype.setShowStatus(pstMedExpenseType.getInt(FLD_SHOW_STATUS));
			return medexpensetype; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedExpenseType(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(MedExpenseType medexpensetype) throws DBException{ 
		try{ 
			PstMedExpenseType pstMedExpenseType = new PstMedExpenseType(0);

			pstMedExpenseType.setString(FLD_TYPE, medexpensetype.getType());
                        pstMedExpenseType.setInt(FLD_SHOW_STATUS, medexpensetype.getShowStatus());

			pstMedExpenseType.insert(); 
			medexpensetype.setOID(pstMedExpenseType.getlong(FLD_MEDICINE_EXPENSE_TYPE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedExpenseType(0),DBException.UNKNOWN); 
		}
		return medexpensetype.getOID();
	}

	public static long updateExc(MedExpenseType medexpensetype) throws DBException{ 
		try{ 
			if(medexpensetype.getOID() != 0){ 
				PstMedExpenseType pstMedExpenseType = new PstMedExpenseType(medexpensetype.getOID());

				pstMedExpenseType.setString(FLD_TYPE, medexpensetype.getType());
                                pstMedExpenseType.setInt(FLD_SHOW_STATUS, medexpensetype.getShowStatus());

				pstMedExpenseType.update(); 
				return medexpensetype.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedExpenseType(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstMedExpenseType pstMedExpenseType = new PstMedExpenseType(oid);
			pstMedExpenseType.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedExpenseType(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_MEDICAL_EXPENSE_TYPE; 
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
				MedExpenseType medexpensetype = new MedExpenseType();
				resultToObject(rs, medexpensetype);
				lists.add(medexpensetype);
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

	private static void resultToObject(ResultSet rs, MedExpenseType medexpensetype){
		try{
			medexpensetype.setOID(rs.getLong(PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_MEDICINE_EXPENSE_TYPE_ID]));
			medexpensetype.setType(rs.getString(PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_TYPE]));
                        medexpensetype.setShowStatus(rs.getInt(PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_SHOW_STATUS]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long medicineExpenseTypeId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_MEDICAL_EXPENSE_TYPE + " WHERE " + 
						PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_MEDICINE_EXPENSE_TYPE_ID] + " = " + medicineExpenseTypeId;

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
			String sql = "SELECT COUNT("+ PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_MEDICINE_EXPENSE_TYPE_ID] + ") FROM " + TBL_HR_MEDICAL_EXPENSE_TYPE;
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
			  	   MedExpenseType medexpensetype = (MedExpenseType)list.get(ls);
				   if(oid == medexpensetype.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}


   
}
