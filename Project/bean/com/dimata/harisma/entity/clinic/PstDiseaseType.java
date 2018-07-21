
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
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.clinic.*; 

public class PstDiseaseType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_DISEASE_TYPE ="hr_disease_type";// "HR_DISEASE_TYPE";

	public static final  int FLD_DISEASE_TYPE_ID = 0;
	public static final  int FLD_DISEASE_TYPE = 1;

	public static final  String[] fieldNames = {
		"DISEASE_TYPE_ID",
		"DISEASE_TYPE"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING
	 }; 

	public PstDiseaseType(){
	}

	public PstDiseaseType(int i) throws DBException { 
		super(new PstDiseaseType()); 
	}

	public PstDiseaseType(String sOid) throws DBException { 
		super(new PstDiseaseType(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstDiseaseType(long lOid) throws DBException { 
		super(new PstDiseaseType(0)); 
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
		return TBL_HR_DISEASE_TYPE;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstDiseaseType().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		DiseaseType diseasetype = fetchExc(ent.getOID()); 
		ent = (Entity)diseasetype; 
		return diseasetype.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((DiseaseType) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((DiseaseType) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static DiseaseType fetchExc(long oid) throws DBException{ 
		try{ 
			DiseaseType diseasetype = new DiseaseType();
			PstDiseaseType pstDiseaseType = new PstDiseaseType(oid); 
			diseasetype.setOID(oid);

			diseasetype.setDiseaseType(pstDiseaseType.getString(FLD_DISEASE_TYPE));

			return diseasetype; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstDiseaseType(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(DiseaseType diseasetype) throws DBException{ 
		try{ 
			PstDiseaseType pstDiseaseType = new PstDiseaseType(0);

			pstDiseaseType.setString(FLD_DISEASE_TYPE, diseasetype.getDiseaseType());

			pstDiseaseType.insert(); 
			diseasetype.setOID(pstDiseaseType.getlong(FLD_DISEASE_TYPE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstDiseaseType(0),DBException.UNKNOWN); 
		}
		return diseasetype.getOID();
	}

	public static long updateExc(DiseaseType diseasetype) throws DBException{ 
		try{ 
			if(diseasetype.getOID() != 0){ 
				PstDiseaseType pstDiseaseType = new PstDiseaseType(diseasetype.getOID());

				pstDiseaseType.setString(FLD_DISEASE_TYPE, diseasetype.getDiseaseType());

				pstDiseaseType.update(); 
				return diseasetype.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstDiseaseType(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstDiseaseType pstDiseaseType = new PstDiseaseType(oid);
			pstDiseaseType.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstDiseaseType(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_DISEASE_TYPE; 
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
				DiseaseType diseasetype = new DiseaseType();
				resultToObject(rs, diseasetype);
				lists.add(diseasetype);
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

	private static void resultToObject(ResultSet rs, DiseaseType diseasetype){
		try{
			diseasetype.setOID(rs.getLong(PstDiseaseType.fieldNames[PstDiseaseType.FLD_DISEASE_TYPE_ID]));
			diseasetype.setDiseaseType(rs.getString(PstDiseaseType.fieldNames[PstDiseaseType.FLD_DISEASE_TYPE]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long diseaseTypeId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_DISEASE_TYPE + " WHERE " + 
						PstDiseaseType.fieldNames[PstDiseaseType.FLD_DISEASE_TYPE_ID] + " = " + diseaseTypeId;

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
			String sql = "SELECT COUNT("+ PstDiseaseType.fieldNames[PstDiseaseType.FLD_DISEASE_TYPE_ID] + ") FROM " + TBL_HR_DISEASE_TYPE;
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
			  	   DiseaseType diseasetype = (DiseaseType)list.get(ls);
				   if(oid == diseasetype.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}
}
