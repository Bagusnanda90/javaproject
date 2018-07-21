/*
 * PstPayTaxTariff.java
 *
 * Created on April 3, 2007, 1:32 PM
 */

package com.dimata.harisma.entity.payroll;

/* package java */ 
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
//import com.dimata. harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.payroll.*;
//import com.dimata.harisma.entity.locker.*;

/**
 *
 * @author  yunny
 */
public class PstPayTaxTariff extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
     public static final  String TBL_PAY_TAX_TARIFF = "pay_tax_tariff";//"PAY_TAX_TARIFF";
     
     public static final  int FLD_TAX_TARIFF_ID = 0;
     public static final  int FLD_TAX_YEAR = 1;
     public static final  int FLD_LEVEL = 2;
     public static final  int FLD_SALARY_MIN= 3;
     public static final  int FLD_SALARY_MAX= 4;
     public static final  int FLD_TAX_TARIFF= 5;
     
       public static final  String[] fieldNames = {
                "TAX_TARIFF_ID",
                "TAX_YEAR",
                "LEVEL",
		"SALARY_MIN",
		"SALARY_MAX",
                "TAX_TARIFF",
                
      };
      
       public static final  int[] fieldTypes = {
                TYPE_LONG + TYPE_PK + TYPE_ID,
                TYPE_INT,
                TYPE_STRING,
                TYPE_FLOAT,
                TYPE_INT,
                TYPE_FLOAT,
                
       };
    
    /** Creates a new instance of PstPayTaxTariff */
    public PstPayTaxTariff() {
    }
    
     public PstPayTaxTariff(int i) throws DBException { 
		super(new PstPayTaxTariff()); 
    }
     
     public PstPayTaxTariff(String sOid) throws DBException { 
		super(new PstPayTaxTariff(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
    }
     
    public PstPayTaxTariff(long lOid) throws DBException { 
		super(new PstPayTaxTariff(0)); 
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
    
    
     public long deleteExc(Entity ent) throws Exception {
        if(ent==null){ 
		throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
	return deleteExc(ent.getOID());
    }

   
    public long fetchExc(Entity ent) throws Exception {
         PayTaxTariff payTaxTariff = fetchExc(ent.getOID()); 
		ent = (Entity)payTaxTariff; 
		return payTaxTariff.getOID(); 
    }
    

    public long insertExc(Entity ent) throws Exception {
         return insertExc((PayTaxTariff) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
         return updateExc((PayTaxTariff) ent);
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
        return new PstPayTaxTariff().getClass().getName();
    }
    
    public String getTableName() {
          return TBL_PAY_TAX_TARIFF;
    }
     public static PayTaxTariff fetchExc(long oid) throws DBException{ 
		try{ 
			PayTaxTariff payTaxTariff = new PayTaxTariff();
			PstPayTaxTariff pstPayTaxTariff = new PstPayTaxTariff(oid); 
			payTaxTariff.setOID(oid);
                        payTaxTariff.setTaxYear(pstPayTaxTariff.getInt(FLD_TAX_YEAR));
                        payTaxTariff.setLevel(pstPayTaxTariff.getString(FLD_LEVEL));
                        payTaxTariff.setSalaryMin(pstPayTaxTariff.getdouble(FLD_SALARY_MIN));
                        payTaxTariff.setSalaryMax(pstPayTaxTariff.getInt(FLD_SALARY_MAX));
                        payTaxTariff.setTaxTariff(pstPayTaxTariff.getdouble(FLD_TAX_TARIFF));
                	return payTaxTariff;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayTaxTariff(0),DBException.UNKNOWN); 
		} 
	}
     
      public static long insertExc(PayTaxTariff payTaxTariff) throws DBException{ 
		try{ 
                        PstPayTaxTariff pstPayTaxTariff = new PstPayTaxTariff(0);
                	pstPayTaxTariff.setInt(FLD_TAX_YEAR, payTaxTariff.getTaxYear());
                        pstPayTaxTariff.setString(FLD_LEVEL, payTaxTariff.getLevel());
                        pstPayTaxTariff.setDouble(FLD_SALARY_MIN, payTaxTariff.getSalaryMin());
                        pstPayTaxTariff.setInt(FLD_SALARY_MAX, payTaxTariff.getSalaryMax());
                        pstPayTaxTariff.setDouble(FLD_TAX_TARIFF, payTaxTariff.getTaxTariff());
                        
                pstPayTaxTariff.insert();
			payTaxTariff.setOID(pstPayTaxTariff.getlong(FLD_TAX_TARIFF_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayTaxTariff(0),DBException.UNKNOWN); 
		}
		return payTaxTariff.getOID();
	}
    
      public static long updateExc(PayTaxTariff payTaxTariff) throws DBException{ 
		try{ 
		  if(payTaxTariff.getOID() != 0){ 
                        PstPayTaxTariff pstPayTaxTariff = new PstPayTaxTariff(payTaxTariff.getOID());
                        pstPayTaxTariff.setInt(FLD_TAX_YEAR, payTaxTariff.getTaxYear());
                        pstPayTaxTariff.setString(FLD_LEVEL, payTaxTariff.getLevel());
                        pstPayTaxTariff.setDouble(FLD_SALARY_MIN, payTaxTariff.getSalaryMin());
                        pstPayTaxTariff.setInt(FLD_SALARY_MAX, payTaxTariff.getSalaryMax());
                        pstPayTaxTariff.setDouble(FLD_TAX_TARIFF, payTaxTariff.getTaxTariff());
                 
                pstPayTaxTariff.update();
				return payTaxTariff.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayTaxTariff(0),DBException.UNKNOWN); 
		}
		return 0;
	}
      
       public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstPayTaxTariff pstPayTaxTariff = new PstPayTaxTariff(oid);
			pstPayTaxTariff.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayTaxTariff(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_PAY_TAX_TARIFF; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql);
                        System.out.println("SQL LIST"+sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				PayTaxTariff payTaxTariff = new PayTaxTariff();
				resultToObject(rs, payTaxTariff);
				lists.add(payTaxTariff);
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
       
       public static void resultToObject(ResultSet rs, PayTaxTariff payTaxTariff){
		try{
			payTaxTariff.setOID(rs.getLong(PstPayTaxTariff.fieldNames[PstPayTaxTariff.FLD_TAX_TARIFF_ID]));
                        payTaxTariff.setTaxYear(rs.getInt(PstPayTaxTariff.fieldNames[PstPayTaxTariff.FLD_TAX_YEAR]));
                        payTaxTariff.setLevel(rs.getString(PstPayTaxTariff.fieldNames[PstPayTaxTariff.FLD_LEVEL]));
                        payTaxTariff.setSalaryMin(rs.getDouble(PstPayTaxTariff.fieldNames[PstPayTaxTariff.FLD_SALARY_MIN]));
                        payTaxTariff.setSalaryMax(rs.getInt(PstPayTaxTariff.fieldNames[PstPayTaxTariff.FLD_SALARY_MAX]));
                        payTaxTariff.setTaxTariff(rs.getDouble(PstPayTaxTariff.fieldNames[PstPayTaxTariff.FLD_TAX_TARIFF]));
                      
		}catch(Exception e){ }
	}
       
       public static boolean checkOID(long tariffId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_PAY_TAX_TARIFF + " WHERE " + 
						PstPayTaxTariff.fieldNames[PstPayTaxTariff.FLD_TAX_TARIFF_ID] + " = '" + tariffId+"'";

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
			String sql = "SELECT COUNT("+ PstPayTaxTariff.fieldNames[PstPayTaxTariff.FLD_TAX_TARIFF_ID] + ") FROM " + TBL_PAY_TAX_TARIFF;
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
			  	   PayTaxTariff payTaxTariff = (PayTaxTariff)list.get(ls);
				   if(oid == payTaxTariff.getOID())
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
