
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
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
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package HRIS */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.clinic.*; 

public class PstMedicalType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_MEDICAL_TYPE = "hr_medical_type";//"HR_MEDICAL_TYPE";

	public static final  int FLD_MEDICAL_TYPE_ID = 0;
    public static final  int FLD_MED_EXPENSE_TYPE_ID = 1;
	public static final  int FLD_TYPE_CODE = 2;
	public static final  int FLD_TYPE_NAME = 3;
        public static final  int FLD_YEARLY_AMOUNT = 4;

	public static final  String[] fieldNames = {
		"MEDICAL_TYPE_ID",
        "MED_EXPENSE_TYPE_ID",
		"TYPE_CODE",
		"TYPE_NAME",
                "YEARLY_AMOUNT"

	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
		TYPE_STRING,
		TYPE_STRING,
                TYPE_FLOAT
	 }; 

	public PstMedicalType(){
	}

	public PstMedicalType(int i) throws DBException { 
		super(new PstMedicalType()); 
	}

	public PstMedicalType(String sOid) throws DBException { 
		super(new PstMedicalType(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstMedicalType(long lOid) throws DBException { 
		super(new PstMedicalType(0)); 
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
		return TBL_HR_MEDICAL_TYPE;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstMedicalType().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		MedicalType medicaltype = fetchExc(ent.getOID()); 
		ent = (Entity)medicaltype; 
		return medicaltype.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((MedicalType) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((MedicalType) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static MedicalType fetchExc(long oid) throws DBException{ 
		try{ 
			System.out.println("\n fetching medical type oid = "+oid);
                        
                        MedicalType medicaltype = new MedicalType();
			PstMedicalType pstMedicalType = new PstMedicalType(oid); 
			medicaltype.setOID(oid);

                        medicaltype.setMedExpenseTypeId(pstMedicalType.getlong(FLD_MED_EXPENSE_TYPE_ID));
			medicaltype.setTypeCode(pstMedicalType.getString(FLD_TYPE_CODE));
			medicaltype.setTypeName(pstMedicalType.getString(FLD_TYPE_NAME));
                        medicaltype.setYearlyAmount(pstMedicalType.getdouble(FLD_YEARLY_AMOUNT));
                        
                        System.out.println(" fetching medical type oid = "+oid);
                        System.out.println(" end fetching medical type medicaltype : "+medicaltype.getOID());

			return medicaltype; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedicalType(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(MedicalType medicaltype) throws DBException{ 
		try{ 
			PstMedicalType pstMedicalType = new PstMedicalType(0);

            pstMedicalType.setLong(FLD_MED_EXPENSE_TYPE_ID, medicaltype.getMedExpenseTypeId());
			pstMedicalType.setString(FLD_TYPE_CODE, medicaltype.getTypeCode());
			pstMedicalType.setString(FLD_TYPE_NAME, medicaltype.getTypeName());
                        pstMedicalType.setDouble(FLD_YEARLY_AMOUNT, medicaltype.getYearlyAmount());

			pstMedicalType.insert(); 
			medicaltype.setOID(pstMedicalType.getlong(FLD_MEDICAL_TYPE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedicalType(0),DBException.UNKNOWN); 
		}
		return medicaltype.getOID();
	}

	public static long updateExc(MedicalType medicaltype) throws DBException{ 
		try{ 
			if(medicaltype.getOID() != 0){ 
				PstMedicalType pstMedicalType = new PstMedicalType(medicaltype.getOID());

                pstMedicalType.setLong(FLD_MED_EXPENSE_TYPE_ID, medicaltype.getMedExpenseTypeId());
				pstMedicalType.setString(FLD_TYPE_CODE, medicaltype.getTypeCode());
				pstMedicalType.setString(FLD_TYPE_NAME, medicaltype.getTypeName());
                                pstMedicalType.setDouble(FLD_YEARLY_AMOUNT, medicaltype.getYearlyAmount());

				pstMedicalType.update(); 
				return medicaltype.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedicalType(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstMedicalType pstMedicalType = new PstMedicalType(oid);
			pstMedicalType.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedicalType(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_MEDICAL_TYPE; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;

			switch (DBHandler.DBSVR_TYPE) { 
			case DBHandler.DBSVR_MYSQL : 
					if(limitStart == 0 && recordToGet == 0)
						sql = sql + ""; 
					else 
						sql = sql + " LIMIT " + limitStart + ","+ recordToGet ; 
				 break;
			case DBHandler.DBSVR_POSTGRESQL : 
 					if(limitStart == 0 && recordToGet == 0) 
						sql = sql + ""; 
					else 
						sql = sql + " LIMIT " +recordToGet + " OFFSET "+ limitStart ;
				 break;
			case DBHandler.DBSVR_SYBASE :
				 break;
			case DBHandler.DBSVR_ORACLE :
				 break;
			case DBHandler.DBSVR_MSSQL :
				 break;

			default:
                if(limitStart == 0 && recordToGet == 0)
					sql = sql + ""; 
				else 
					sql = sql + " LIMIT " + limitStart + ","+ recordToGet ; 
			}
            dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				MedicalType medicaltype = new MedicalType();
				resultToObject(rs, medicaltype);
				lists.add(medicaltype);
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

	public static void resultToObject(ResultSet rs, MedicalType medicaltype){
		try{
			medicaltype.setOID(rs.getLong(PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID]));
            medicaltype.setMedExpenseTypeId(rs.getLong(PstMedicalType.fieldNames[PstMedicalType.FLD_MED_EXPENSE_TYPE_ID]));
			medicaltype.setTypeCode(rs.getString(PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_CODE]));
			medicaltype.setTypeName(rs.getString(PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_NAME]));
                        medicaltype.setYearlyAmount(rs.getDouble(PstMedicalType.fieldNames[PstMedicalType.FLD_YEARLY_AMOUNT]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long medicalTypeId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_MEDICAL_TYPE + " WHERE " + 
						PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID] + " = " + medicalTypeId;

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
			String sql = "SELECT COUNT("+ PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID] + ") FROM " + TBL_HR_MEDICAL_TYPE;
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
	public static int findLimitStart( long oid, int recordToGet, String whereClause, String orderClause){
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, orderClause); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   MedicalType medicaltype = (MedicalType)list.get(ls);
				   if(oid == medicaltype.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}
	/* This method used to find command where current data */
	public static int findLimitCommand(int start, int recordToGet, int vectSize){
		 int cmd = Command.LIST;
		 int mdl = vectSize % recordToGet;
		 vectSize = vectSize + (recordToGet - mdl);
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

