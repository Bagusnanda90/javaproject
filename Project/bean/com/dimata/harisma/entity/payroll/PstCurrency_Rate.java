/*
 * PstCurrency_Rate.java
 *
 * Created on April 4, 2007, 5:24 PM
 */

package com.dimata.harisma.entity.payroll;

/* package java */

//import java.util.Date;
import java.util.Vector;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
import java.sql.ResultSet;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;
//import com.dimata.util.Formater;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
//import com.dimata.system.entity.system.SystemProperty;
//import com.dimata.harisma.entity.payroll.*;
    
/**
 *
 * @author  emerliana
 */
public class PstCurrency_Rate extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    
     public static final  String TBL_CURRENCY_RATE = "currency_rate";//"CURRENCY_RATE";
     
        public static final  int FLD_CURR_RATE_ID = 0;
	public static final  int FLD_CURR_CODE = 1;
	public static final  int FLD_RATE_BANK = 2;
	public static final  int FLD_RATE_COMPANY = 3;
	public static final  int FLD_RATE_TAX = 4;
	public static final  int FLD_START_DATE = 5;
        public static final  int FLD_END_DATE = 6;
        public static final  int FLD_STATUS = 7;
        
         public static final  String[] fieldNames = {
		"CURR_RATE_ID",
		"CURR_CODE",
		"RATE_BANK",
		"RATE_COMPANY",
		"RATE_TAX",
		"START_DATE",
                "END_DATE",
                "STATUS"
		
	 };
         
         public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_INT
         
	 };
         
    public static final int AKTIF = 1;
    public static final int HISTORY = 2;

    public static final String[]status = {
        "","Aktif","History"
    };
    /** Creates a new instance of PstCurrency_Rate */
    public PstCurrency_Rate() {
    }
    
    public PstCurrency_Rate(int i) throws DBException {
        super(new PstCurrency_Rate());
    }
    
    public PstCurrency_Rate(String sOid) throws DBException {
        super(new PstCurrency_Rate(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
     public PstCurrency_Rate(long lOid) throws DBException {
        super(new PstCurrency_Rate(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public String[] getFieldNames() {
        return fieldNames; 
    }
    
    public int getFieldSize() {
        return fieldNames.length;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
         return new PstCurrency_Rate().getClass().getName();
    }
    
    public String getTableName() {
        return TBL_CURRENCY_RATE;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((Currency_Rate) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((Currency_Rate) ent);
    }
    
    public long deleteExc(Entity ent) throws Exception {
         if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public long fetchExc(Entity ent) throws Exception {
        Currency_Rate currency_Rate = fetchExc(ent.getOID());
        ent = (Entity)currency_Rate;
        return currency_Rate.getOID();
    }
    
     public static Currency_Rate fetchExc(long oid) throws DBException {
        try {
            Currency_Rate currency_Rate = new Currency_Rate();
            PstCurrency_Rate pstCurrency_Rate = new PstCurrency_Rate(oid);
            currency_Rate.setOID(oid);
            
            currency_Rate.setCurr_code(pstCurrency_Rate.getString(FLD_CURR_CODE));
            currency_Rate.setRate_bank(pstCurrency_Rate.getdouble(FLD_RATE_BANK));
            currency_Rate.setRate_company(pstCurrency_Rate.getdouble(FLD_RATE_COMPANY));
            currency_Rate.setStatus(pstCurrency_Rate.getInt(FLD_STATUS));
            currency_Rate.setTax_rate(pstCurrency_Rate.getdouble(FLD_RATE_TAX));
            currency_Rate.setTgl_akhir(pstCurrency_Rate.getDate(FLD_END_DATE));
            currency_Rate.setTgl_mulai(pstCurrency_Rate.getDate(FLD_START_DATE));
            
            return currency_Rate;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCurrency_Rate(0), DBException.UNKNOWN);
        }
    }
     
     public static long insertExc(Currency_Rate currency_Rate) throws DBException{ 
		try{ 
			PstCurrency_Rate pstCurrency_Rate = new PstCurrency_Rate(0);

			pstCurrency_Rate.setString(FLD_CURR_CODE, currency_Rate.getCurr_code());
                        pstCurrency_Rate.setDouble(FLD_RATE_BANK, currency_Rate.getRate_bank());
                        pstCurrency_Rate.setDouble(FLD_RATE_COMPANY, currency_Rate.getRate_company());
                        pstCurrency_Rate.setInt(FLD_STATUS, currency_Rate.getStatus());
                        pstCurrency_Rate.setDouble(FLD_RATE_TAX, currency_Rate.getTax_rate());
                        pstCurrency_Rate.setDate(FLD_END_DATE, currency_Rate.getTgl_akhir());
                        pstCurrency_Rate.setDate(FLD_START_DATE, currency_Rate.getTgl_mulai());
                        
                pstCurrency_Rate.insert();
			currency_Rate.setOID(pstCurrency_Rate.getlong(FLD_CURR_RATE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstCurrency_Rate(0),DBException.UNKNOWN); 
		}
		return currency_Rate.getOID();
	} 
     
     public static long updateExc(Currency_Rate currency_Rate) throws DBException{ 
		try{ 
		  if(currency_Rate.getOID() != 0){ 
                        PstCurrency_Rate pstCurrency_Rate = new PstCurrency_Rate(currency_Rate.getOID());
                        
                        pstCurrency_Rate.setString(FLD_CURR_CODE, currency_Rate.getCurr_code());
                        pstCurrency_Rate.setDouble(FLD_RATE_BANK, currency_Rate.getRate_bank());
                        pstCurrency_Rate.setDouble(FLD_RATE_COMPANY, currency_Rate.getRate_company());
                        pstCurrency_Rate.setInt(FLD_STATUS, currency_Rate.getStatus());
                        pstCurrency_Rate.setDouble(FLD_RATE_TAX, currency_Rate.getTax_rate());
                        pstCurrency_Rate.setDate(FLD_END_DATE, currency_Rate.getTgl_akhir());
                        pstCurrency_Rate.setDate(FLD_START_DATE, currency_Rate.getTgl_mulai());
                pstCurrency_Rate.update();
				return currency_Rate.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstCurrency_Rate(0),DBException.UNKNOWN); 
		}
		return 0;
	}
     
      public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstCurrency_Rate pstCurrency_Rate = new PstCurrency_Rate(oid);
			pstCurrency_Rate.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstCurrency_Rate(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_CURRENCY_RATE; 
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
				Currency_Rate currency_Rate = new Currency_Rate();
				resultToObject(rs, currency_Rate);
				lists.add(currency_Rate);
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
       
       
        public static void resultToObject(ResultSet rs, Currency_Rate currency_Rate){
		try{
			currency_Rate.setOID(rs.getLong(PstCurrency_Rate.fieldNames[PstCurrency_Rate.FLD_CURR_RATE_ID]));
                        currency_Rate.setCurr_code(rs.getString(PstCurrency_Rate.fieldNames[PstCurrency_Rate.FLD_CURR_CODE]));
                        currency_Rate.setRate_bank(rs.getDouble(PstCurrency_Rate.fieldNames[PstCurrency_Rate.FLD_RATE_BANK]));
                        currency_Rate.setRate_company(rs.getDouble(PstCurrency_Rate.fieldNames[PstCurrency_Rate.FLD_RATE_COMPANY]));
                        currency_Rate.setStatus(rs.getInt(PstCurrency_Rate.fieldNames[PstCurrency_Rate.FLD_STATUS]));
                        currency_Rate.setTax_rate(rs.getDouble(PstCurrency_Rate.fieldNames[PstCurrency_Rate.FLD_RATE_TAX]));
                        currency_Rate.setTgl_akhir(rs.getDate(PstCurrency_Rate.fieldNames[PstCurrency_Rate.FLD_END_DATE]));
                        currency_Rate.setTgl_mulai(rs.getDate(PstCurrency_Rate.fieldNames[PstCurrency_Rate.FLD_START_DATE]));
                        
		}catch(Exception e){ }
	}
        
        public static boolean checkOID(long currencyRateId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_CURRENCY_RATE + " WHERE " + 
						PstCurrency_Rate.fieldNames[PstCurrency_Rate.FLD_CURR_RATE_ID] + " = '" + currencyRateId+"'";

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
			String sql = "SELECT COUNT("+ PstCurrency_Rate.fieldNames[PstCurrency_Rate.FLD_CURR_RATE_ID] + ") FROM " + TBL_CURRENCY_RATE;
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
     
     
     public static int findLimitStart( long oid, int recordToGet, String whereClause,String orderClause){
		String order = "";
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, orderClause); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	  Currency_Rate currency_Rate = (Currency_Rate)list.get(ls);
				   if(oid == currency_Rate.getOID())
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
