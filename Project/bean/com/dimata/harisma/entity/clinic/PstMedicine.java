
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
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.clinic.*; 

public class PstMedicine extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_MEDICINE = "hr_medicine";//"HR_MEDICINE";

	public static final  int FLD_MEDICINE_ID = 0;
	public static final  int FLD_NAME = 1;
	public static final  int FLD_UNIT_PRICE = 2;
	public static final  int FLD_DESCRIPTION = 3;

	public static final  String[] fieldNames = {
		"MEDICINE_ID",
		"NAME",
		"UNIT_PRICE",
		"DESCRIPTION"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
		TYPE_FLOAT,
		TYPE_STRING
	 }; 

	public PstMedicine(){
	}

	public PstMedicine(int i) throws DBException { 
		super(new PstMedicine()); 
	}

	public PstMedicine(String sOid) throws DBException { 
		super(new PstMedicine(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstMedicine(long lOid) throws DBException { 
		super(new PstMedicine(0)); 
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
		return TBL_HR_MEDICINE;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstMedicine().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		Medicine medicine = fetchExc(ent.getOID()); 
		ent = (Entity)medicine; 
		return medicine.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((Medicine) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((Medicine) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static Medicine fetchExc(long oid) throws DBException{ 
		try{ 
			Medicine medicine = new Medicine();
			PstMedicine pstMedicine = new PstMedicine(oid); 
			medicine.setOID(oid);

			medicine.setName(pstMedicine.getString(FLD_NAME));
			medicine.setUnitPrice(pstMedicine.getdouble(FLD_UNIT_PRICE));
			medicine.setDescription(pstMedicine.getString(FLD_DESCRIPTION));

			return medicine; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedicine(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(Medicine medicine) throws DBException{ 
		try{ 
			PstMedicine pstMedicine = new PstMedicine(0);

			pstMedicine.setString(FLD_NAME, medicine.getName());
			pstMedicine.setDouble(FLD_UNIT_PRICE, medicine.getUnitPrice());
			pstMedicine.setString(FLD_DESCRIPTION, medicine.getDescription());

			pstMedicine.insert(); 
			medicine.setOID(pstMedicine.getlong(FLD_MEDICINE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedicine(0),DBException.UNKNOWN); 
		}
		return medicine.getOID();
	}

	public static long updateExc(Medicine medicine) throws DBException{ 
		try{ 
			if(medicine.getOID() != 0){ 
				PstMedicine pstMedicine = new PstMedicine(medicine.getOID());

				pstMedicine.setString(FLD_NAME, medicine.getName());
				pstMedicine.setDouble(FLD_UNIT_PRICE, medicine.getUnitPrice());
				pstMedicine.setString(FLD_DESCRIPTION, medicine.getDescription());

				pstMedicine.update(); 
				return medicine.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedicine(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstMedicine pstMedicine = new PstMedicine(oid);
			pstMedicine.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedicine(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_MEDICINE; 
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
				Medicine medicine = new Medicine();
				resultToObject(rs, medicine);
				lists.add(medicine);
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

	private static void resultToObject(ResultSet rs, Medicine medicine){
		try{
			medicine.setOID(rs.getLong(PstMedicine.fieldNames[PstMedicine.FLD_MEDICINE_ID]));
			medicine.setName(rs.getString(PstMedicine.fieldNames[PstMedicine.FLD_NAME]));
			medicine.setUnitPrice(rs.getDouble(PstMedicine.fieldNames[PstMedicine.FLD_UNIT_PRICE]));
			medicine.setDescription(rs.getString(PstMedicine.fieldNames[PstMedicine.FLD_DESCRIPTION]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long medicineId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_MEDICINE + " WHERE " + 
						PstMedicine.fieldNames[PstMedicine.FLD_MEDICINE_ID] + " = " + medicineId;

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
			String sql = "SELECT COUNT("+ PstMedicine.fieldNames[PstMedicine.FLD_MEDICINE_ID] + ") FROM " + TBL_HR_MEDICINE;
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
	public static int findLimitStart( long oid, int recordToGet, String whereClause,String order){
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, order); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   Medicine medicine = (Medicine)list.get(ls);
				   if(oid == medicine.getOID())
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
