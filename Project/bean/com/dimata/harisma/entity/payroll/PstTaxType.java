/*
 * PstTaxType.java
 *
 * Created on March 30, 2007, 5:10 PM
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
import com.dimata.harisma.entity.payroll.*;

/**
 *
 * @author  emerliana
 */
public class PstTaxType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

     public static final  String TBL_TAX_TYPE = "pay_tax_type";//"PAY_TAX_TYPE";
     
        public static final  int FLD_TAX_TYPE_ID = 0;
	public static final  int FLD_TAX_CODE = 1;
	public static final  int FLD_TAX_TYPE= 2;
	
        public static final  String[] fieldNames = {
		"TAX_TYPE_ID",
		"TAX_CODE",
		"TAX_TYPE"
	 };
         
         public static final  int[] fieldTypes = {
		TYPE_LONG  + TYPE_PK + TYPE_ID,
		TYPE_STRING,
                TYPE_STRING
         };
         
    
public PstTaxType() {
}

 public PstTaxType(int i) throws DBException { 
            super(new PstTaxType()); 
}
     
public PstTaxType(String sOid) throws DBException { 
        super(new PstTaxType(0)); 
        if(!locate(sOid)) 
                throw new DBException(this,DBException.RECORD_NOT_FOUND); 
        else 
                return; 
}

public PstTaxType(long lOid) throws DBException { 
        super(new PstTaxType(0)); 
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
    return new PstTaxType().getClass().getName(); 
}

public String getTableName() {
    return TBL_TAX_TYPE;
}

public long deleteExc(Entity ent) throws Exception {
     if(ent==null){ 
            throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            } 
     return deleteExc(ent.getOID());
}
    
public long fetchExc(Entity ent) throws Exception {
     TaxType taxType = fetchExc(ent.getOID()); 
        ent = (Entity)taxType; 
        return taxType.getOID();
}



public long insertExc(Entity ent) throws Exception {
    return insertExc((TaxType) ent);
}

public long updateExc(Entity ent) throws Exception {
    return updateExc((TaxType) ent); 
}


public static TaxType fetchExc(long oid) throws DBException{ 
        try{ 
                TaxType taxType = new TaxType();
                PstTaxType pstTaxType = new PstTaxType(oid); 
                taxType.setOID(oid);
                taxType.setTaxCode(pstTaxType.getString(FLD_TAX_CODE));
                taxType.setTaxType(pstTaxType.getString(FLD_TAX_TYPE));
                
                return taxType;
        }catch(DBException dbe){ 
                throw dbe; 
        }catch(Exception e){ 
                throw new DBException(new PstCurrencyType(0),DBException.UNKNOWN); 
        } 
}



public static long insertExc(TaxType taxType) throws DBException{ 
		try{ 
			PstTaxType pstTaxType = new PstTaxType(0);

			pstTaxType.setString(FLD_TAX_CODE, taxType.getTaxCode());
			pstTaxType.setString(FLD_TAX_TYPE, taxType.getTaxType());

			pstTaxType.insert(); 
			taxType.setOID(pstTaxType.getlong(FLD_TAX_TYPE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTaxType(0),DBException.UNKNOWN); 
		}
		return taxType.getOID();
	}

 public static long updateExc(TaxType taxType) throws DBException{ 
		try{ 
		  if(taxType.getOID() != 0){ 
                        PstTaxType pstTaxType = new PstTaxType(taxType.getOID());
                        pstTaxType.setString(FLD_TAX_CODE, taxType.getTaxCode());
                        pstTaxType.setString(FLD_TAX_TYPE, taxType.getTaxType());

                      pstTaxType.update();
		      return taxType.getOID();

		     }
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTaxType(0),DBException.UNKNOWN); 
		}
		return 0;
}
 
 public static long deleteExc(long oid) throws DBException{ 
        try{ 
                PstTaxType pstTaxType = new PstTaxType(oid);
                pstTaxType.delete();
        }catch(DBException dbe){ 
                throw dbe; 
        }catch(Exception e){ 
                throw new DBException(new PstTaxType(0),DBException.UNKNOWN); 
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
                String sql = "SELECT * FROM " + TBL_TAX_TYPE; 
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
                        TaxType taxType = new TaxType();
                        resultToObject(rs, taxType);
                        lists.add(taxType);
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
  
   public static void resultToObject(ResultSet rs, TaxType taxType){
		try{
                        taxType.setOID(rs.getLong(PstTaxType.fieldNames[PstTaxType.FLD_TAX_TYPE_ID]));
                        taxType.setTaxCode(rs.getString(PstTaxType.fieldNames[PstTaxType.FLD_TAX_CODE]));
                        taxType.setTaxType(rs.getString(PstTaxType.fieldNames[PstTaxType.FLD_TAX_TYPE]));
		}catch(Exception e){ }
	}
   
   
   public static boolean checkOID(long taxTypeId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_TAX_TYPE + " WHERE " + 
				PstTaxType.fieldNames[PstTaxType.FLD_TAX_TYPE_ID] + " = '" + taxTypeId+"'";

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
			String sql = "SELECT COUNT("+ PstTaxType.fieldNames[PstTaxType.FLD_TAX_TYPE_ID] + ") FROM " + TBL_TAX_TYPE;
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
			  	   TaxType taxType = (TaxType)list.get(ls);
				   if(oid == taxType.getOID())
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
    
  public static void main(String args[]) {
        //long result = getPeriodIdBySelectedDate(new Date(104,5,31));
        //long result = getPeriodIdJustBefore(504404240112808778L);
        //System.out.println("result : " + result);
        TaxType taxType = new TaxType();
        taxType.setTaxCode("pph21");
        taxType.setTaxType("Pendapatan");
        
        try{
            PstTaxType.insertExc(taxType);
        }catch(Exception e){
            System.out.println("Err"+e.toString());
        }
        
    }
   
    
}
