/*
 * PstTax_Slip_Nr.java
 *
 * Created on April 5, 2007, 1:54 PM
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

public class PstTax_Slip_Nr extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    
     public static final  String TBL_TAX_SLIP_NR = "pay_tax_slip_nr";//"PAY_TAX_SLIP_NR";
     
        public static final  int FLD_TAX_SLIP_NR_ID = 0;
	public static final  int FLD_PERIOD_ID = 1;
	public static final  int FLD_TAX_CODE = 2;
        public static final  int FLD_PREFIX = 3;
	public static final  int FLD_SUFIX = 4;
        public static final  int FLD_DIGIT = 5;
        
        public static final  String[] fieldNames = {
		"TAX_SLIP_NR_ID",
		"PERIOD_ID",
		"TAX_CODE",
                "PREFIX",
                "SUFIX",
                "DIGIT"
	 };
         
         public static final  int[] fieldTypes = {
		TYPE_LONG  + TYPE_PK + TYPE_ID,
		TYPE_LONG,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_INT
         };
         
    public static final int DIGIT_I = 1;
    public static final int DIGIT_II = 2;
    public static final int DIGIT_III = 3;

    public static final String[]digit = {
        "","0","00","000"
    };
         
    /** Creates a new instance of PstTax_Slip_Nr */
    public PstTax_Slip_Nr() {
    }
    
    public PstTax_Slip_Nr(int i) throws DBException {
        super(new PstTax_Slip_Nr());
    }
    
    public PstTax_Slip_Nr(String sOid) throws DBException {
        super(new PstTax_Slip_Nr(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstTax_Slip_Nr(long lOid) throws DBException {
        super(new PstTax_Slip_Nr(0));
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
        return new PstTax_Slip_Nr().getClass().getName();
    }
    
    public String getTableName() {
        return TBL_TAX_SLIP_NR;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((Tax_Slip_Nr) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((Tax_Slip_Nr) ent);
    }
    
    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public long fetchExc(Entity ent) throws Exception {
        Tax_Slip_Nr tax_Slip_Nr = fetchExc(ent.getOID());
        ent = (Entity)tax_Slip_Nr;
        return tax_Slip_Nr.getOID();
    }
    
    public static Tax_Slip_Nr fetchExc(long oid) throws DBException {
        try {
            Tax_Slip_Nr tax_Slip_Nr = new Tax_Slip_Nr();
            PstTax_Slip_Nr pstTax_Slip_Nr = new PstTax_Slip_Nr(oid);
            tax_Slip_Nr.setOID(oid);
            
            tax_Slip_Nr.setDigit(pstTax_Slip_Nr.getInt(FLD_DIGIT));
            tax_Slip_Nr.setPeriod_id(pstTax_Slip_Nr.getlong(FLD_PERIOD_ID));
            tax_Slip_Nr.setPrefix(pstTax_Slip_Nr.getString(FLD_PREFIX));
            tax_Slip_Nr.setSufix(pstTax_Slip_Nr.getString(FLD_SUFIX));
            tax_Slip_Nr.setTax_code(pstTax_Slip_Nr.getString(FLD_TAX_CODE));
            
            return tax_Slip_Nr;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTax_Slip_Nr(0), DBException.UNKNOWN);
        }
    }
    
    public static long insertExc(Tax_Slip_Nr tax_Slip_Nr) throws DBException{ 
		try{ 
			PstTax_Slip_Nr pstTax_Slip_Nr = new PstTax_Slip_Nr(0);

			pstTax_Slip_Nr.setInt(FLD_DIGIT, tax_Slip_Nr.getDigit());
                        pstTax_Slip_Nr.setLong(FLD_PERIOD_ID, tax_Slip_Nr.getPeriod_id());
                        pstTax_Slip_Nr.setString(FLD_PREFIX, tax_Slip_Nr.getPrefix());
                        pstTax_Slip_Nr.setString(FLD_SUFIX, tax_Slip_Nr.getSufix());
                        pstTax_Slip_Nr.setString(FLD_TAX_CODE, tax_Slip_Nr.getTax_code());
                        
                pstTax_Slip_Nr.insert();
			tax_Slip_Nr.setOID(pstTax_Slip_Nr.getlong(FLD_TAX_SLIP_NR_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTax_Slip_Nr(0),DBException.UNKNOWN); 
		}
		return tax_Slip_Nr.getOID();
	}
    
     public static long updateExc(Tax_Slip_Nr tax_Slip_Nr) throws DBException{ 
		try{ 
		  if(tax_Slip_Nr.getOID() != 0){ 
                        PstTax_Slip_Nr pstTax_Slip_Nr = new PstTax_Slip_Nr(tax_Slip_Nr.getOID());
                        
                        pstTax_Slip_Nr.setInt(FLD_DIGIT, tax_Slip_Nr.getDigit());
                        pstTax_Slip_Nr.setLong(FLD_PERIOD_ID, tax_Slip_Nr.getPeriod_id());
                        pstTax_Slip_Nr.setString(FLD_PREFIX, tax_Slip_Nr.getPrefix());
                        pstTax_Slip_Nr.setString(FLD_SUFIX, tax_Slip_Nr.getSufix());
                        pstTax_Slip_Nr.setString(FLD_TAX_CODE, tax_Slip_Nr.getTax_code());
                        
                        
                pstTax_Slip_Nr.update();
				return tax_Slip_Nr.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTax_Slip_Nr(0),DBException.UNKNOWN); 
		}
		return 0;
	}
     
     public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstTax_Slip_Nr pstTax_Slip_Nr = new PstTax_Slip_Nr(oid);
			pstTax_Slip_Nr.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTax_Slip_Nr(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_TAX_SLIP_NR; 
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
				Tax_Slip_Nr tax_Slip_Nr = new Tax_Slip_Nr();
				resultToObject(rs, tax_Slip_Nr);
				lists.add(tax_Slip_Nr);
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
       
       
       
        public static void resultToObject(ResultSet rs, Tax_Slip_Nr tax_Slip_Nr){
		try{
			tax_Slip_Nr.setOID(rs.getLong(PstTax_Slip_Nr.fieldNames[PstTax_Slip_Nr.FLD_TAX_SLIP_NR_ID]));
                        tax_Slip_Nr.setDigit(rs.getInt(PstTax_Slip_Nr.fieldNames[PstTax_Slip_Nr.FLD_DIGIT]));
                        tax_Slip_Nr.setPeriod_id(rs.getLong(PstTax_Slip_Nr.fieldNames[PstTax_Slip_Nr.FLD_PERIOD_ID]));
			tax_Slip_Nr.setPrefix(rs.getString(PstTax_Slip_Nr.fieldNames[PstTax_Slip_Nr.FLD_PREFIX]));
                        tax_Slip_Nr.setSufix(rs.getString(PstTax_Slip_Nr.fieldNames[PstTax_Slip_Nr.FLD_SUFIX]));
                        tax_Slip_Nr.setTax_code(rs.getString(PstTax_Slip_Nr.fieldNames[PstTax_Slip_Nr.FLD_TAX_CODE]));
                        
                   }catch(Exception e){ }
	}
        
         public static boolean checkOID(long taxSlipNrId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_TAX_SLIP_NR + " WHERE " + 
						PstTax_Slip_Nr.fieldNames[PstTax_Slip_Nr.FLD_TAX_SLIP_NR_ID] + " = '" + taxSlipNrId+"'";

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
			String sql = "SELECT COUNT("+ PstTax_Slip_Nr.fieldNames[PstTax_Slip_Nr.FLD_TAX_SLIP_NR_ID] + ") FROM " + TBL_TAX_SLIP_NR;
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
			  	  Tax_Slip_Nr tax_Slip_Nr = (Tax_Slip_Nr)list.get(ls);
				   if(oid == tax_Slip_Nr.getOID())
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
