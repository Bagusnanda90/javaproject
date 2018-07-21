/*
 * PstPayTaxVariabel.java
 *
 * Created on August 10, 2007, 1:14 PM
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
 * @author  emerliana
 */
public class PstPayTaxVariabel extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final  String TBL_PAY_TAX_VARIABEL = "pay_tax_variabel";//"PAY_TAX_VARIABEL";
    
    public static final  int FLD_TAX_VARIABEL_ID = 0;
    public static final  int FLD_LEVEL_CODE = 1;
    public static final  int FLD_VARIABEL_NAME = 2;
    public static final  int FLD_VARIABEL_VALUE = 3;
    public static final  int FLD_JENIS_VARIABEL = 4;
    public static final  int FLD_PERSEN_VARIABEL = 5;
    
    public static final  String[] fieldNames = {
                "TAX_VARIABEL_ID",
                "LEVEL_CODE",
                "NAME_VARIABEL",
                "VARIABEL_VALUE",
                "JENIS_VARIABEL",
                "PERSEN_VARIABEL"
      };
      
    public static final  int[] fieldTypes = {
                TYPE_LONG + TYPE_PK + TYPE_ID,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_INT,
                TYPE_INT,
                TYPE_FLOAT
    };
    
    
      // Pay Variabel
       public static int BIAYA_TUNJ_POT_JABATAN= 0;
       public static int TUNJANGAN_HARI_TUA = 1;
       public static int JAMSOSTEK = 2;
       
      public static String[] taxPotongan = {
        "Biaya Tunjangan Pot Jabatan", "Tunjangan Hari Tua", "Jamsostek"
      };
    
    public PstPayTaxVariabel() {
    }
    
    public PstPayTaxVariabel(int i) throws DBException { 
		super(new PstPayTaxVariabel()); 
    }
    
    public PstPayTaxVariabel(String sOid) throws DBException { 
		super(new PstPayTaxVariabel(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
    }
    
    public PstPayTaxVariabel(long lOid) throws DBException { 
		super(new PstPayTaxVariabel(0)); 
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
        PayTaxVariabel payTaxVariabel = fetchExc(ent.getOID()); 
        ent = (Entity)payTaxVariabel; 
        return payTaxVariabel.getOID(); 
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
        return new PstPayTaxVariabel().getClass().getName();
    }
    
    public String getTableName() {
        return TBL_PAY_TAX_VARIABEL;
    }
    
    public long insertExc(Entity ent) throws Exception {
         return insertExc((PayTaxVariabel) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((PayTaxVariabel) ent);
    }
    
    public static PayTaxVariabel fetchExc(long oid) throws DBException{ 
		try{ 
			PayTaxVariabel payTaxVariabel = new PayTaxVariabel();
			PstPayTaxVariabel pstPayTaxVariabel = new PstPayTaxVariabel(oid); 
			payTaxVariabel.setOID(oid);
                        payTaxVariabel.setLevelCode(pstPayTaxVariabel.getString(FLD_LEVEL_CODE));
                        payTaxVariabel.setNameVariabel(pstPayTaxVariabel.getString(FLD_VARIABEL_NAME));
                        payTaxVariabel.setValueVariabel(pstPayTaxVariabel.getInt(FLD_VARIABEL_VALUE));
                        payTaxVariabel.setJenis_Variabel(pstPayTaxVariabel.getInt(FLD_JENIS_VARIABEL));
                        payTaxVariabel.setPersen_variabel(pstPayTaxVariabel.getdouble(FLD_PERSEN_VARIABEL));
                        
                	return payTaxVariabel;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayTaxVariabel(0),DBException.UNKNOWN); 
		} 
    }
    
    public static long insertExc(PayTaxVariabel payTaxVariabel) throws DBException{ 
		try{ 
                        PstPayTaxVariabel pstPayTaxVariabel = new PstPayTaxVariabel(0);
                	pstPayTaxVariabel.setString(FLD_LEVEL_CODE, payTaxVariabel.getLevelCode());
                        pstPayTaxVariabel.setString(FLD_VARIABEL_NAME, payTaxVariabel.getNameVariabel());
                        pstPayTaxVariabel.setInt(FLD_VARIABEL_VALUE, payTaxVariabel.getValueVariabel());
                        pstPayTaxVariabel.setInt(FLD_JENIS_VARIABEL, payTaxVariabel.getJenis_Variabel());
                        pstPayTaxVariabel.setDouble(FLD_PERSEN_VARIABEL, payTaxVariabel.getPersen_variabel());
             	
                pstPayTaxVariabel.insert();
			payTaxVariabel.setOID(pstPayTaxVariabel.getlong(FLD_TAX_VARIABEL_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayComponent(0),DBException.UNKNOWN); 
		}
		return payTaxVariabel.getOID();
	}
    
    
    public static long updateExc(PayTaxVariabel payTaxVariabel) throws DBException{ 
		try{ 
		  if(payTaxVariabel.getOID() != 0){ 
                        PstPayTaxVariabel pstPayTaxVariabel = new PstPayTaxVariabel(payTaxVariabel.getOID());
                        pstPayTaxVariabel.setString(FLD_LEVEL_CODE, payTaxVariabel.getLevelCode());
                        pstPayTaxVariabel.setString(FLD_VARIABEL_NAME, payTaxVariabel.getNameVariabel());
                        pstPayTaxVariabel.setInt(FLD_VARIABEL_VALUE, payTaxVariabel.getValueVariabel());
                        pstPayTaxVariabel.setInt(FLD_JENIS_VARIABEL, payTaxVariabel.getJenis_Variabel());
                        pstPayTaxVariabel.setDouble(FLD_PERSEN_VARIABEL, payTaxVariabel.getPersen_variabel());
                        
                pstPayTaxVariabel.update();
				return payTaxVariabel.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayComponent(0),DBException.UNKNOWN); 
		}
		return 0;
	}
    
     public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstPayTaxVariabel pstPayTaxVariabel = new PstPayTaxVariabel(oid);
			pstPayTaxVariabel.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayTaxVariabel(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_PAY_TAX_VARIABEL; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql);
                        //System.out.println("SQL LIST"+sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				PayTaxVariabel payTaxVariabel = new PayTaxVariabel();
				resultToObject(rs, payTaxVariabel);
				lists.add(payTaxVariabel);
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
      
      public static void resultToObject(ResultSet rs, PayTaxVariabel payTaxVariabel){
		try{
			payTaxVariabel.setOID(rs.getLong(PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_TAX_VARIABEL_ID]));
                        payTaxVariabel.setLevelCode(rs.getString(PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_LEVEL_CODE]));
                        payTaxVariabel.setNameVariabel(rs.getString(PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_VARIABEL_NAME]));
                        payTaxVariabel.setValueVariabel(rs.getInt(PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_VARIABEL_VALUE]));
                        payTaxVariabel.setJenis_Variabel(rs.getInt(PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_JENIS_VARIABEL]));
                        payTaxVariabel.setPersen_variabel(rs.getDouble(PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_PERSEN_VARIABEL]));
                        
		}catch(Exception e){ }
	}
      
       public static boolean checkOID(long taxVariabelId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_PAY_TAX_VARIABEL + " WHERE " + 
						PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_TAX_VARIABEL_ID] + " = '" + taxVariabelId+"'";

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
			String sql = "SELECT COUNT("+ PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_TAX_VARIABEL_ID] + ") FROM " + TBL_PAY_TAX_VARIABEL;
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
			  	   PayComponent payComponent = (PayComponent)list.get(ls);
				   if(oid == payComponent.getOID())
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
   
   public static boolean checkMaster(long oid){
    	if(PstPayTaxVariabel.checkOID(oid))
            return true;
    	else
            return false;
    }
   
   public static void main(String args[])
   {
       PayTaxVariabel payTax = new PayTaxVariabel();
       payTax.setLevelCode("Level 6");
       payTax.setNameVariabel("Tunjangan");
       payTax.setValueVariabel(45000);
       try{
           PstPayTaxVariabel.insertExc(payTax);
       }catch(Exception e){;}
   }
}
