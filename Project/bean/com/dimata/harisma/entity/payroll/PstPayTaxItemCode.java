/*
 * PstPayTaxItemCode.java
 *
 * Created on April 4, 2007, 2:26 PM
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
public class PstPayTaxItemCode extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    public static final  String TBL_PAY_TAX_ITEM_CODE = "pay_tax_item_code";//"PAY_TAX_ITEM_CODE";
    
    public static final  int FLD_TAX_ITEM_CD_ID = 0;
    public static final  int FLD_TAX_ITEM_CODE = 1;
    public static final  int FLD_TAX_CODE = 2;
    public static final  int FLD_TAX_ITEM_NAME = 3;
    
    public static final  String[] fieldNames = {
		"TAX_ITEM_CD_ID",
		"TAX_ITEM_CODE",
		"TAX_CODE",
                "TAX_ITEM_NAME",
      };
      
    public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
       };
       
    
  
    
    /** Creates a new instance of PstPayTaxItemCode */
    public PstPayTaxItemCode() {
    }
    
    public PstPayTaxItemCode(int i) throws DBException { 
		super(new PstPayTaxItemCode()); 
    }
     
    public PstPayTaxItemCode(String sOid) throws DBException { 
		super(new PstPayTaxItemCode(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
    }
    
     public PstPayTaxItemCode(long lOid) throws DBException { 
		super(new PstPayTaxItemCode(0)); 
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
        PayTaxItemCode payTaxItemCode = fetchExc(ent.getOID()); 
		ent = (Entity)payTaxItemCode; 
		return payTaxItemCode.getOID(); 
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
        return new PstPayTaxItemCode().getClass().getName(); 
    }
    
    public String getTableName() {
          return TBL_PAY_TAX_ITEM_CODE;
    }
    
    public long insertExc(Entity ent) throws Exception {
         return insertExc((PayTaxItemCode) ent); 
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((PayTaxItemCode) ent);
    }
  
    public static PayTaxItemCode fetchExc(long oid) throws DBException{ 
		try{ 
			PayTaxItemCode payTaxItemCode = new PayTaxItemCode();
			PstPayTaxItemCode pstPayTaxItemCode = new PstPayTaxItemCode(oid); 
			payTaxItemCode.setOID(oid);

			payTaxItemCode.setTaxItemCode(pstPayTaxItemCode.getString(FLD_TAX_ITEM_CODE));
                        payTaxItemCode.setTaxCode(pstPayTaxItemCode.getString(FLD_TAX_CODE));
                        payTaxItemCode.setTaxItemName(pstPayTaxItemCode.getString(FLD_TAX_ITEM_NAME));
              	
			return payTaxItemCode;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayTaxItemCode(0),DBException.UNKNOWN); 
		} 
	}
    
    public static long insertExc(PayTaxItemCode payTaxItemCode) throws DBException{ 
		try{ 
			PstPayTaxItemCode pstPayTaxItemCode = new PstPayTaxItemCode(0);

			pstPayTaxItemCode.setString(FLD_TAX_ITEM_CODE, payTaxItemCode.getTaxItemCode());
                        pstPayTaxItemCode.setString(FLD_TAX_CODE, payTaxItemCode.getTaxCode());
                        pstPayTaxItemCode.setString(FLD_TAX_ITEM_NAME, payTaxItemCode.getTaxItemName());
                       
             	
                pstPayTaxItemCode.insert();
			payTaxItemCode.setOID(pstPayTaxItemCode.getlong(FLD_TAX_ITEM_CD_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayTaxItemCode(0),DBException.UNKNOWN); 
		}
		return payTaxItemCode.getOID();
	}
    
     public static long updateExc(PayTaxItemCode payTaxItemCode) throws DBException{ 
		try{ 
		  if(payTaxItemCode.getOID() != 0){ 
                        PstPayTaxItemCode pstPayTaxItemCode = new PstPayTaxItemCode(payTaxItemCode.getOID());
                        pstPayTaxItemCode.setString(FLD_TAX_ITEM_CODE, payTaxItemCode.getTaxItemCode());
                        pstPayTaxItemCode.setString(FLD_TAX_CODE, payTaxItemCode.getTaxCode());
                        pstPayTaxItemCode.setString(FLD_TAX_ITEM_NAME, payTaxItemCode.getTaxItemName());
                        
                        
                pstPayTaxItemCode.update();
				return payTaxItemCode.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayTaxItemCode(0),DBException.UNKNOWN); 
		}
		return 0;
	}
     
     public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstPayTaxItemCode pstPayTaxItemCode = new PstPayTaxItemCode(oid);
			pstPayTaxItemCode.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayTaxItemCode(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_PAY_TAX_ITEM_CODE; 
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
				PayTaxItemCode payTaxItemCode = new PayTaxItemCode();
				resultToObject(rs, payTaxItemCode);
				lists.add(payTaxItemCode);
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
  
   public static void resultToObject(ResultSet rs, PayTaxItemCode payTaxItemCode){
		try{
			payTaxItemCode.setOID(rs.getLong(PstPayTaxItemCode.fieldNames[PstPayTaxItemCode.FLD_TAX_ITEM_CD_ID]));
                        payTaxItemCode.setTaxItemCode(rs.getString(PstPayTaxItemCode.fieldNames[PstPayTaxItemCode.FLD_TAX_ITEM_CODE]));
                        payTaxItemCode.setTaxCode(rs.getString(PstPayTaxItemCode.fieldNames[PstPayTaxItemCode.FLD_TAX_CODE]));
                        payTaxItemCode.setTaxItemName(rs.getString(PstPayTaxItemCode.fieldNames[PstPayTaxItemCode.FLD_TAX_ITEM_NAME]));
                        
		}catch(Exception e){ }
	}
   
   public static boolean checkOID(long payTaxItemCodeId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_PAY_TAX_ITEM_CODE + " WHERE " + 
						PstPayTaxItemCode.fieldNames[PstPayTaxItemCode.FLD_TAX_ITEM_CD_ID] + " = '" + payTaxItemCodeId+"'";

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
			String sql = "SELECT COUNT("+ PstPayTaxItemCode.fieldNames[PstPayTaxItemCode.FLD_TAX_ITEM_CD_ID] + ") FROM " + TBL_PAY_TAX_ITEM_CODE;
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
			  	   PayTaxItemCode payTaxItemCode = (PayTaxItemCode)list.get(ls);
				   if(oid == payTaxItemCode.getOID())
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
