
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

public class PstExpRecapitulate extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_REC_MEDICAL_EXPENCE = "hr_rec_medical_expense";//"HR_REC_MEDICAL_EXPENSE";

	public static final  int FLD_REC_MEDICAL_EXPENCE_ID = 0;
	public static final  int FLD_PERIODE = 1;
	public static final  int FLD_MEDICAL_TYPE_ID = 2;
	public static final  int FLD_AMOUNT = 3;
	public static final  int FLD_DISCOUNT_IN_PERCENT = 4;
	public static final  int FLD_DISCOUNT_IN_RP = 5;
	public static final  int FLD_TOTAL = 6;
	public static final  int FLD_PERSON = 7;

	public static final  String[] fieldNames = {
		"REC_MEDICAL_EXPENCE_ID",
		"PERIODE",
		"MEDICAL_TYPE_ID",
		"AMOUNT",
		"DISCOUNT_IN_PERCENT",
		"DISCOUNT_IN_RP",
		"TOTAL",
		"PERSON"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_DATE,
		TYPE_LONG,
		TYPE_FLOAT,
		TYPE_FLOAT,
		TYPE_FLOAT,
		TYPE_FLOAT,
		TYPE_INT
	 }; 

	public PstExpRecapitulate(){
	}

	public PstExpRecapitulate(int i) throws DBException { 
		super(new PstExpRecapitulate()); 
	}

	public PstExpRecapitulate(String sOid) throws DBException { 
		super(new PstExpRecapitulate(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstExpRecapitulate(long lOid) throws DBException { 
		super(new PstExpRecapitulate(0)); 
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
		return TBL_HR_REC_MEDICAL_EXPENCE;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstExpRecapitulate().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		ExpRecapitulate exprecapitulate = fetchExc(ent.getOID()); 
		ent = (Entity)exprecapitulate; 
		return exprecapitulate.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((ExpRecapitulate) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((ExpRecapitulate) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static ExpRecapitulate fetchExc(long oid) throws DBException{ 
		try{ 
			ExpRecapitulate exprecapitulate = new ExpRecapitulate();
			PstExpRecapitulate pstExpRecapitulate = new PstExpRecapitulate(oid); 
			exprecapitulate.setOID(oid);

			exprecapitulate.setPeriode(pstExpRecapitulate.getDate(FLD_PERIODE));
			exprecapitulate.setMedicalTypeId(pstExpRecapitulate.getlong(FLD_MEDICAL_TYPE_ID));
			exprecapitulate.setAmount(pstExpRecapitulate.getdouble(FLD_AMOUNT));
			exprecapitulate.setDiscountInPercent(pstExpRecapitulate.getdouble(FLD_DISCOUNT_IN_PERCENT));
			exprecapitulate.setDiscountInRp(pstExpRecapitulate.getdouble(FLD_DISCOUNT_IN_RP));
			exprecapitulate.setTotal(pstExpRecapitulate.getdouble(FLD_TOTAL));
			exprecapitulate.setPerson(pstExpRecapitulate.getInt(FLD_PERSON));

			return exprecapitulate; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstExpRecapitulate(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(ExpRecapitulate exprecapitulate) throws DBException{ 
		try{ 
			PstExpRecapitulate pstExpRecapitulate = new PstExpRecapitulate(0);

			pstExpRecapitulate.setDate(FLD_PERIODE, exprecapitulate.getPeriode());
			pstExpRecapitulate.setLong(FLD_MEDICAL_TYPE_ID, exprecapitulate.getMedicalTypeId());
			pstExpRecapitulate.setDouble(FLD_AMOUNT, exprecapitulate.getAmount());
			pstExpRecapitulate.setDouble(FLD_DISCOUNT_IN_PERCENT, exprecapitulate.getDiscountInPercent());
            System.out.println(">>>>> "+ exprecapitulate.getDiscountInRp());
			pstExpRecapitulate.setDouble(FLD_DISCOUNT_IN_RP, exprecapitulate.getDiscountInRp());
			pstExpRecapitulate.setDouble(FLD_TOTAL, exprecapitulate.getTotal());
			pstExpRecapitulate.setInt(FLD_PERSON, exprecapitulate.getPerson());

			pstExpRecapitulate.insert(); 
			exprecapitulate.setOID(pstExpRecapitulate.getlong(FLD_REC_MEDICAL_EXPENCE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstExpRecapitulate(0),DBException.UNKNOWN); 
		}
		return exprecapitulate.getOID();
	}

	public static long updateExc(ExpRecapitulate exprecapitulate) throws DBException{ 
		try{ 
			if(exprecapitulate.getOID() != 0){ 
				PstExpRecapitulate pstExpRecapitulate = new PstExpRecapitulate(exprecapitulate.getOID());

				pstExpRecapitulate.setDate(FLD_PERIODE, exprecapitulate.getPeriode());
				pstExpRecapitulate.setLong(FLD_MEDICAL_TYPE_ID, exprecapitulate.getMedicalTypeId());
				pstExpRecapitulate.setDouble(FLD_AMOUNT, exprecapitulate.getAmount());
				pstExpRecapitulate.setDouble(FLD_DISCOUNT_IN_PERCENT, exprecapitulate.getDiscountInPercent());
                System.out.println(">>>>> "+ exprecapitulate.getDiscountInRp());
				pstExpRecapitulate.setDouble(FLD_DISCOUNT_IN_RP, exprecapitulate.getDiscountInRp());
				pstExpRecapitulate.setDouble(FLD_TOTAL, exprecapitulate.getTotal());
				pstExpRecapitulate.setInt(FLD_PERSON, exprecapitulate.getPerson());

				pstExpRecapitulate.update(); 
				return exprecapitulate.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstExpRecapitulate(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstExpRecapitulate pstExpRecapitulate = new PstExpRecapitulate(oid);
			pstExpRecapitulate.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstExpRecapitulate(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_REC_MEDICAL_EXPENCE; 
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
				ExpRecapitulate exprecapitulate = new ExpRecapitulate();
				resultToObject(rs, exprecapitulate);
				lists.add(exprecapitulate);
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

	private static void resultToObject(ResultSet rs, ExpRecapitulate exprecapitulate){
		try{
			exprecapitulate.setOID(rs.getLong(PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_REC_MEDICAL_EXPENCE_ID]));
			exprecapitulate.setPeriode(rs.getDate(PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_PERIODE]));
			exprecapitulate.setMedicalTypeId(rs.getLong(PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_MEDICAL_TYPE_ID]));
			exprecapitulate.setAmount(rs.getDouble(PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_AMOUNT]));
			exprecapitulate.setDiscountInPercent(rs.getDouble(PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_DISCOUNT_IN_PERCENT]));
			exprecapitulate.setDiscountInRp(rs.getDouble(PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_DISCOUNT_IN_RP]));
			exprecapitulate.setTotal(rs.getDouble(PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_TOTAL]));
			exprecapitulate.setPerson(rs.getInt(PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_PERSON]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long recMedicalExpenceId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_REC_MEDICAL_EXPENCE + " WHERE " + 
						PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_REC_MEDICAL_EXPENCE_ID] + " = " + recMedicalExpenceId;

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
			String sql = "SELECT COUNT("+ PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_REC_MEDICAL_EXPENCE_ID] + ") FROM " + TBL_HR_REC_MEDICAL_EXPENCE;
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
			  	   ExpRecapitulate exprecapitulate = (ExpRecapitulate)list.get(ls);
				   if(oid == exprecapitulate.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}



    public static Vector getMedicalExpense (Date dtPeriod){
    	DBResultSet dbrs = null;
		Vector result = new Vector(1,1);
		try{
			String sql = " SELECT "+
                		 "  MET."+PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID]+
                         ", MET."+PstMedicalType.fieldNames[PstMedicalType.FLD_MED_EXPENSE_TYPE_ID]+
                         ", MET."+PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_CODE]+
                         ", MET."+PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_NAME]+
                         ", RME."+PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_REC_MEDICAL_EXPENCE_ID]+
                         ", RME."+PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_PERIODE]+
                         ", RME."+PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_MEDICAL_TYPE_ID]+
                         ", RME."+PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_AMOUNT]+
                         ", RME."+PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_DISCOUNT_IN_PERCENT]+
                         ", RME."+PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_DISCOUNT_IN_RP]+
                         ", RME."+PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_TOTAL]+
                         ", RME."+PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_PERSON]+
                         ", MG."+PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_TYPE]+
                		 " FROM " + PstExpRecapitulate.TBL_HR_REC_MEDICAL_EXPENCE + " RME "+
                         " , "+PstMedicalType.TBL_HR_MEDICAL_TYPE+ " MET "+
                         " , "+PstMedExpenseType.TBL_HR_MEDICAL_EXPENSE_TYPE+ " MG "+
                         " WHERE " +
                         " MET."+PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID]+
                         " = RME."+PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_MEDICAL_TYPE_ID]+
                         " AND MG."+PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_MEDICINE_EXPENSE_TYPE_ID]+
                         " = MET."+PstMedicalType.fieldNames[PstMedicalType.FLD_MED_EXPENSE_TYPE_ID]+
						 " AND RME."+PstExpRecapitulate.fieldNames[PstExpRecapitulate.FLD_PERIODE] +
                         " = '" + Formater.formatDate(dtPeriod,"yyyy-MM-dd") +"'"+
                         " ORDER BY MG."+PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_TYPE];

           // System.out.println("getMedicalExpense "+sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) {
                Vector temp = new Vector(1,1);
                MedicalType medicalType = new MedicalType();
                MedExpenseType medExpenseType = new  MedExpenseType();
                ExpRecapitulate expRecapitulate = new ExpRecapitulate();

                medicalType.setOID(rs.getLong(PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID]));
                medicalType.setMedExpenseTypeId(rs.getLong(PstMedicalType.fieldNames[PstMedicalType.FLD_MED_EXPENSE_TYPE_ID]));
                medicalType.setTypeCode(rs.getString(PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_CODE]));
                medicalType.setTypeName(rs.getString(PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_NAME]));
                temp.add(medicalType);

                medExpenseType.setType(rs.getString(PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_TYPE]));
                temp.add(medExpenseType);

                resultToObject(rs, expRecapitulate);
                temp.add(expRecapitulate);

                result.add(temp);
            }
			rs.close();
		}catch(Exception e){
			System.out.println("err : "+e.toString());
		}finally{
			DBResultSet.close(dbrs);
			return result;
		}
    }
}
