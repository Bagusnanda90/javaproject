/*
 * PstPayExecutive.java
 *
 * Created on March 31, 2007, 1:53 PM
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
public class PstPayExecutive extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
     public static final  String TBL_PAY_EXECUTIVES = "pay_executives";//"PAY_EXECUTIVES";
     
     public static final  int FLD_EXECUTIVE_ID = 0;
     public static final  int FLD_TAX_FORM = 1;
     public static final  int FLD_EXECUTIVE_NAME= 2;
     
     
      public static final  String[] fieldNames = {
		"EXECUTIVE_ID",
		"TAX_FORM",
		"EXECUTIVE_NAME",
      };
      
       public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
                TYPE_STRING,
       };
    //tax_form   
    public static final String[] taxForm = {"Form SPT PPh 21 Masa","Form SPT PPh 23 Masa"};
    
     public static Vector getTaxForm(){
    	Vector result = new Vector(1,1);
        for(int i=0;i<taxForm.length;i++){
        	result.add(taxForm[i]);
        }
        return result;
    }
    
    /** Creates a new instance of PstPayExecutive */
    public PstPayExecutive() {
    }
    
     public PstPayExecutive(int i) throws DBException { 
		super(new PstPayExecutive()); 
    }
     
     public PstPayExecutive(String sOid) throws DBException { 
		super(new PstPayExecutive(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}
    
     public PstPayExecutive(long lOid) throws DBException { 
		super(new PstPayExecutive(0)); 
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
        PayExecutive payExecutive = fetchExc(ent.getOID()); 
		ent = (Entity)payExecutive; 
		return payExecutive.getOID(); 
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
        return new PstPayExecutive().getClass().getName(); 
    }
    
    public String getTableName() {
        return TBL_PAY_EXECUTIVES;
    }
    
    public long insertExc(Entity ent) throws Exception {
         return insertExc((PayExecutive) ent); 
    }
    
    public long updateExc(Entity ent) throws Exception {
         return updateExc((PayExecutive) ent);
    }
    
    
     public static PayExecutive fetchExc(long oid) throws DBException{ 
		try{ 
			PayExecutive payExecutive = new PayExecutive();
			PstPayExecutive pstPayExecutive = new PstPayExecutive(oid); 
			payExecutive.setOID(oid);

			payExecutive.setTaxForm(pstPayExecutive.getString(FLD_TAX_FORM));
                        payExecutive.setExecutiveName(pstPayExecutive.getString(FLD_EXECUTIVE_NAME));
                       
			
			return payExecutive;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayExecutive(0),DBException.UNKNOWN); 
		} 
	}
     
     public static long insertExc(PayExecutive payExecutive) throws DBException{ 
		try{ 
			PstPayExecutive pstPayExecutive = new PstPayExecutive(0);

			pstPayExecutive.setString(FLD_TAX_FORM, payExecutive.getTaxForm());
                        pstPayExecutive.setString(FLD_EXECUTIVE_NAME, payExecutive.getExecutiveName());
                      
             	
                pstPayExecutive.insert();
			payExecutive.setOID(pstPayExecutive.getlong(FLD_EXECUTIVE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayExecutive(0),DBException.UNKNOWN); 
		}
		return payExecutive.getOID();
	}
    
     public static long updateExc(PayExecutive payExecutive) throws DBException{ 
		try{ 
		  if(payExecutive.getOID() != 0){ 
                        PstPayExecutive pstPayExecutive = new PstPayExecutive(payExecutive.getOID());
                        pstPayExecutive.setString(FLD_TAX_FORM, payExecutive.getTaxForm());
                        pstPayExecutive.setString(FLD_EXECUTIVE_NAME, payExecutive.getExecutiveName());
                       
                        
                pstPayExecutive.update();
				return payExecutive.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayExecutive(0),DBException.UNKNOWN); 
		}
		return 0;
	}
     
      public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstPayExecutive pstPayExecutive = new PstPayExecutive(oid);
			pstPayExecutive.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayExecutive(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_PAY_EXECUTIVES; 
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
				PayExecutive payExecutive = new PayExecutive();
				resultToObject(rs, payExecutive);
				lists.add(payExecutive);
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
      
      
      public static void resultToObject(ResultSet rs, PayExecutive payExecutive){
		try{
			payExecutive.setOID(rs.getLong(PstPayExecutive.fieldNames[PstPayExecutive.FLD_EXECUTIVE_ID]));
                        payExecutive.setTaxForm(rs.getString(PstPayExecutive.fieldNames[PstPayExecutive.FLD_TAX_FORM]));
                        payExecutive.setExecutiveName(rs.getString(PstPayExecutive.fieldNames[PstPayExecutive.FLD_EXECUTIVE_NAME]));
                      
		}catch(Exception e){ }
	}
      
      public static boolean checkOID(long executiveId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_PAY_EXECUTIVES + " WHERE " + 
						PstPayExecutive.fieldNames[PstPayExecutive.FLD_EXECUTIVE_ID] + " = '" + executiveId+"'";

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
			String sql = "SELECT COUNT("+ PstPayExecutive.fieldNames[PstPayExecutive.FLD_EXECUTIVE_ID] + ") FROM " + TBL_PAY_EXECUTIVES;
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
			  	   PayExecutive payExecutive = (PayExecutive)list.get(ls);
				   if(oid == payExecutive.getOID())
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
