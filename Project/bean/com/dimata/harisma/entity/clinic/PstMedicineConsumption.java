
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
//import com.dimata. harisma.db.DBLogger;
import com.dimata.harisma.entity.clinic.*; 

public class PstMedicineConsumption extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_MEDICINE_CONSUMPTION = "hr_medicine_consumption";//"HR_MEDICINE_CONSUMPTION";

	public static final  int FLD_MEDICINE_CONSUMPTION_ID = 0;
	public static final  int FLD_MEDICINE_ID = 1;
	public static final  int FLD_MONTH = 2;
	public static final  int FLD_LAST_MONTH = 3;
	public static final  int FLD_PURCHASE_THIS_MONTH = 4;
	public static final  int FLD_STOCK_THIS_MONTH = 5;
	public static final  int FLD_CONSUMP_THIS_MONTH = 6;
	public static final  int FLD_TOTAL_CONSUMP = 7;
	public static final  int FLD_CLOSE_INVENTORY = 8;
	public static final  int FLD_CLOSE_AMOUNT = 9;

	public static final  String[] fieldNames = {
		"MEDICINE_CONSUMPTION_ID",
		"MEDICINE_ID",
		"MONTH",
		"LAST_MONTH",
		"PURCHASE_THIS_MONTH",
		"STOCK_THIS_MONTH",
		"CONSUMP_THIS_MONTH",
		"TOTAL_CONSUMP",
		"CLOSE_INVENTORY",
		"CLOSE_AMOUNT"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
		TYPE_DATE,
		TYPE_INT,
		TYPE_INT,
		TYPE_INT,
		TYPE_INT,
		TYPE_FLOAT,
		TYPE_INT,
		TYPE_FLOAT
	 }; 

	public PstMedicineConsumption(){
	}

	public PstMedicineConsumption(int i) throws DBException { 
		super(new PstMedicineConsumption()); 
	}

	public PstMedicineConsumption(String sOid) throws DBException { 
		super(new PstMedicineConsumption(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstMedicineConsumption(long lOid) throws DBException { 
		super(new PstMedicineConsumption(0)); 
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
		return TBL_HR_MEDICINE_CONSUMPTION;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstMedicineConsumption().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		MedicineConsumption medicineconsumption = fetchExc(ent.getOID()); 
		ent = (Entity)medicineconsumption; 
		return medicineconsumption.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((MedicineConsumption) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((MedicineConsumption) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static MedicineConsumption fetchExc(long oid) throws DBException{ 
		try{ 
			MedicineConsumption medicineconsumption = new MedicineConsumption();
			PstMedicineConsumption pstMedicineConsumption = new PstMedicineConsumption(oid); 
			medicineconsumption.setOID(oid);

			medicineconsumption.setMedicineId(pstMedicineConsumption.getlong(FLD_MEDICINE_ID));
			medicineconsumption.setMonth(pstMedicineConsumption.getDate(FLD_MONTH));
			medicineconsumption.setLastMonth(pstMedicineConsumption.getInt(FLD_LAST_MONTH));
			medicineconsumption.setPurchaseThisMonth(pstMedicineConsumption.getInt(FLD_PURCHASE_THIS_MONTH));
			medicineconsumption.setStockThisMonth(pstMedicineConsumption.getInt(FLD_STOCK_THIS_MONTH));
			medicineconsumption.setConsumpThisMonth(pstMedicineConsumption.getInt(FLD_CONSUMP_THIS_MONTH));
			medicineconsumption.setTotalConsump(pstMedicineConsumption.getdouble(FLD_TOTAL_CONSUMP));
			medicineconsumption.setCloseInventory(pstMedicineConsumption.getInt(FLD_CLOSE_INVENTORY));
			medicineconsumption.setCloseAmount(pstMedicineConsumption.getdouble(FLD_CLOSE_AMOUNT));

			return medicineconsumption; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedicineConsumption(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(MedicineConsumption medicineconsumption) throws DBException{ 
		try{ 
			PstMedicineConsumption pstMedicineConsumption = new PstMedicineConsumption(0);

			pstMedicineConsumption.setLong(FLD_MEDICINE_ID, medicineconsumption.getMedicineId());
			pstMedicineConsumption.setDate(FLD_MONTH, medicineconsumption.getMonth());
			pstMedicineConsumption.setInt(FLD_LAST_MONTH, medicineconsumption.getLastMonth());
			pstMedicineConsumption.setInt(FLD_PURCHASE_THIS_MONTH, medicineconsumption.getPurchaseThisMonth());
			pstMedicineConsumption.setInt(FLD_STOCK_THIS_MONTH, medicineconsumption.getStockThisMonth());
			pstMedicineConsumption.setInt(FLD_CONSUMP_THIS_MONTH, medicineconsumption.getConsumpThisMonth());
			pstMedicineConsumption.setDouble(FLD_TOTAL_CONSUMP, medicineconsumption.getTotalConsump());
			pstMedicineConsumption.setInt(FLD_CLOSE_INVENTORY, medicineconsumption.getCloseInventory());
			pstMedicineConsumption.setDouble(FLD_CLOSE_AMOUNT, medicineconsumption.getCloseAmount());

			pstMedicineConsumption.insert(); 
			medicineconsumption.setOID(pstMedicineConsumption.getlong(FLD_MEDICINE_CONSUMPTION_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedicineConsumption(0),DBException.UNKNOWN); 
		}
		return medicineconsumption.getOID();
	}

	public static long updateExc(MedicineConsumption medicineconsumption) throws DBException{ 
		try{ 
			if(medicineconsumption.getOID() != 0){ 
				PstMedicineConsumption pstMedicineConsumption = new PstMedicineConsumption(medicineconsumption.getOID());

				pstMedicineConsumption.setLong(FLD_MEDICINE_ID, medicineconsumption.getMedicineId());
				pstMedicineConsumption.setDate(FLD_MONTH, medicineconsumption.getMonth());
				pstMedicineConsumption.setInt(FLD_LAST_MONTH, medicineconsumption.getLastMonth());
				pstMedicineConsumption.setInt(FLD_PURCHASE_THIS_MONTH, medicineconsumption.getPurchaseThisMonth());
				pstMedicineConsumption.setInt(FLD_STOCK_THIS_MONTH, medicineconsumption.getStockThisMonth());
				pstMedicineConsumption.setInt(FLD_CONSUMP_THIS_MONTH, medicineconsumption.getConsumpThisMonth());
				pstMedicineConsumption.setDouble(FLD_TOTAL_CONSUMP, medicineconsumption.getTotalConsump());
				pstMedicineConsumption.setInt(FLD_CLOSE_INVENTORY, medicineconsumption.getCloseInventory());
				pstMedicineConsumption.setDouble(FLD_CLOSE_AMOUNT, medicineconsumption.getCloseAmount());

				pstMedicineConsumption.update(); 
				return medicineconsumption.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedicineConsumption(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstMedicineConsumption pstMedicineConsumption = new PstMedicineConsumption(oid);
			pstMedicineConsumption.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstMedicineConsumption(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_MEDICINE_CONSUMPTION; 
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
				MedicineConsumption medicineconsumption = new MedicineConsumption();
				resultToObject(rs, medicineconsumption);
				lists.add(medicineconsumption);
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

	private static void resultToObject(ResultSet rs, MedicineConsumption medicineconsumption){
		try{
			medicineconsumption.setOID(rs.getLong(PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MEDICINE_CONSUMPTION_ID]));
			medicineconsumption.setMedicineId(rs.getLong(PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MEDICINE_ID]));
			medicineconsumption.setMonth(rs.getDate(PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MONTH]));
			medicineconsumption.setLastMonth(rs.getInt(PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_LAST_MONTH]));
			medicineconsumption.setPurchaseThisMonth(rs.getInt(PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_PURCHASE_THIS_MONTH]));
			medicineconsumption.setStockThisMonth(rs.getInt(PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_STOCK_THIS_MONTH]));
			medicineconsumption.setConsumpThisMonth(rs.getInt(PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_CONSUMP_THIS_MONTH]));
			medicineconsumption.setTotalConsump(rs.getDouble(PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_TOTAL_CONSUMP]));
			medicineconsumption.setCloseInventory(rs.getInt(PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_CLOSE_INVENTORY]));
			medicineconsumption.setCloseAmount(rs.getDouble(PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_CLOSE_AMOUNT]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long medicineConsumptionId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_MEDICINE_CONSUMPTION + " WHERE " + 
						PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MEDICINE_CONSUMPTION_ID] + " = " + medicineConsumptionId;

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
			String sql = "SELECT COUNT("+ PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MEDICINE_CONSUMPTION_ID] + ") FROM " + TBL_HR_MEDICINE_CONSUMPTION;
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
			  	   MedicineConsumption medicineconsumption = (MedicineConsumption)list.get(ls);
				   if(oid == medicineconsumption.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}

    public static Vector listMedicine(Date dtMonth, boolean isNotNull){
    	DBResultSet dbrs = null;
		try {
			String sql = " SELECT MED."+PstMedicine.fieldNames[PstMedicine.FLD_MEDICINE_ID]+
                		 ", MED."+PstMedicine.fieldNames[PstMedicine.FLD_NAME]+
                		 ", MED."+PstMedicine.fieldNames[PstMedicine.FLD_UNIT_PRICE]+
                         ", MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MONTH]+
                         ", MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_LAST_MONTH]+
                         ", MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_CLOSE_INVENTORY]+
                     //    ", MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_PURCHASE_THIS_MONTH]+
                         " FROM "+PstMedicine.TBL_HR_MEDICINE+" MED "+
                         " LEFT JOIN  "+PstMedicineConsumption.TBL_HR_MEDICINE_CONSUMPTION+ " MC "+
                         " ON MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MEDICINE_ID]+
                         " = MED."+PstMedicine.fieldNames[PstMedicine.FLD_MEDICINE_ID];

            if(isNotNull)
                 sql = sql + " WHERE MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MONTH]+
                       "  = '"+Formater.formatDate(dtMonth,"yyyy-MM-dd") +"' OR "+
                       "MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MEDICINE_ID]+
                       " IS NULL ";

            sql = sql + " ORDER BY MED."+PstMedicine.fieldNames[PstMedicine.FLD_NAME];

			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			Vector result = new Vector(1,1);
			while(rs.next())
            {
            	Vector temp = new Vector(1,1);
                Medicine medicine = new  Medicine();
                MedicineConsumption medicineConsumption = new MedicineConsumption();

                medicine.setOID(rs.getLong(PstMedicine.fieldNames[PstMedicine.FLD_MEDICINE_ID]));
                medicine.setName(rs.getString(PstMedicine.fieldNames[PstMedicine.FLD_NAME]));
                medicine.setUnitPrice(rs.getDouble(PstMedicine.fieldNames[PstMedicine.FLD_UNIT_PRICE]));
                temp.add(medicine);

                medicineConsumption.setMonth(rs.getDate(PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MONTH]));
                if(isNotNull)
                    medicineConsumption.setLastMonth(rs.getInt(PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_CLOSE_INVENTORY]));
                else
					medicineConsumption.setLastMonth(rs.getInt(PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_LAST_MONTH]));

                int stock =medicineConsumption.getLastMonth()+medicineConsumption.getPurchaseThisMonth();
				medicineConsumption.setStockThisMonth(stock);
			/*	medicineConsumption.setTotalConsump(medicineConsumption.getConsumpThisMonth()* medicine.getUnitPrice());
                int closeInv =  stock - medicineConsumption.getConsumpThisMonth();   */
				medicineConsumption.setCloseInventory(stock);
				medicineConsumption.setCloseAmount(stock * medicine.getUnitPrice());
                temp.add(medicineConsumption);

                result.add(temp);
            }

			rs.close();
			return result;

		}catch(Exception e) {
             System.out.println("errr "+e.toString());
			return new Vector(1,1);
		}finally {
			DBResultSet.close(dbrs);
		}
    }


    public static Vector listMedConsumpt(Date dtMonth){
    	DBResultSet dbrs = null;
		try {
			String sql = " SELECT MED."+PstMedicine.fieldNames[PstMedicine.FLD_MEDICINE_ID]+
                		 ", MED."+PstMedicine.fieldNames[PstMedicine.FLD_NAME]+
                		 ", MED."+PstMedicine.fieldNames[PstMedicine.FLD_UNIT_PRICE]+
                         ", MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MEDICINE_CONSUMPTION_ID]+
                         ", MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MONTH]+
                         ", MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_LAST_MONTH]+
                         ", MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_STOCK_THIS_MONTH]+
                         ", MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_CONSUMP_THIS_MONTH]+
                         ", MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_PURCHASE_THIS_MONTH]+
                         ", MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_TOTAL_CONSUMP]+
                         ", MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_CLOSE_INVENTORY]+
                         ", MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_CLOSE_AMOUNT]+
                         " FROM "+PstMedicine.TBL_HR_MEDICINE+" MED "+
                         " LEFT JOIN  "+PstMedicineConsumption.TBL_HR_MEDICINE_CONSUMPTION+ " MC "+
                         " ON MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MEDICINE_ID]+
                         " = MED."+PstMedicine.fieldNames[PstMedicine.FLD_MEDICINE_ID]+
                         " WHERE MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MONTH]+
                         "  = '"+Formater.formatDate(dtMonth,"yyyy-MM-dd") +"'"+
                         " OR "+
                         " MC."+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MEDICINE_ID]+
                         " IS NULL "+
                         " ORDER BY MED."+PstMedicine.fieldNames[PstMedicine.FLD_NAME];

			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			Vector result = new Vector(1,1);
			while(rs.next())
            {
            	Vector temp = new Vector(1,1);
                Medicine medicine = new  Medicine();
                MedicineConsumption medicineConsumption = new MedicineConsumption();

                medicine.setOID(rs.getLong(PstMedicine.fieldNames[PstMedicine.FLD_MEDICINE_ID]));
                medicine.setName(rs.getString(PstMedicine.fieldNames[PstMedicine.FLD_NAME]));
                medicine.setUnitPrice(rs.getDouble(PstMedicine.fieldNames[PstMedicine.FLD_UNIT_PRICE]));
                temp.add(medicine);

                resultToObject(rs, medicineConsumption);
                temp.add(medicineConsumption);

                result.add(temp);
            }

			rs.close();
			return result;
		}catch(Exception e) {
			return new Vector(1,1);
		}finally {
			DBResultSet.close(dbrs);
		}
    }


    public static void deleteMedicineConsum(Date dtMonth)
    {
    	try{
        	String sql =" DELETE FROM "+PstMedicineConsumption.TBL_HR_MEDICINE_CONSUMPTION+
                		" WHERE "+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MONTH]+
                        " >= '"+Formater.formatDate(dtMonth,"yyyy-MM-dd")+"'";

        	int status = DBHandler.execUpdate(sql);

    	}catch(Exception exc){
        	System.out.println("error delete "+exc.toString());
    	}
    }

}
