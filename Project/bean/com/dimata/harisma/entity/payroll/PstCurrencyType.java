/*
 * PstCurrencyType.java
 *
 * Created on March 30, 2007, 1:03 PM
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
public class PstCurrencyType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 
     public static final  String TBL_CURRENCY_TYPE = "currency_type";//"CURRENCY_TYPE";
     
        public static final  int FLD_CURRENCY_TYPE_ID = 0;
	public static final  int FLD_CODE = 1;
    	public static final  int FLD_NAME= 2;
	public static final  int FLD_TAB_INDEX= 3;
	public static final  int FLD_INCLUDE_INF_PROCESS= 4;
	public static final  int FLD_DESCRIPTION = 5;
        public static final  int FLD_FORMAT_CURRENCY = 6;
        
	
        public static final  String[] fieldNames = {
        	"CURRENCY_TYPE_ID",
                "CODE",
		"NAME",
		"TAB_INDEX",
		"INCLUDE_INF_PROCESS",
		"DESCRIPTION",
                "FORMAT_CURRENCY"
		
	 };
         
         public static final  int[] fieldTypes = {
                TYPE_LONG + TYPE_PK + TYPE_ID,
                TYPE_STRING ,
		TYPE_STRING,
                TYPE_INT,
                TYPE_INT,
                TYPE_STRING,
                TYPE_STRING
         
	 };
         
         
    // used in formula
       public static int NO_USED= 0;
       public static int YES_USED = 1;
    
      public static String[] used= {
        "No", "Yes"
       };
    
    /** Creates a new instance of PstCurrencyType */
    public PstCurrencyType() {
    }
     public PstCurrencyType(int i) throws DBException { 
		super(new PstCurrencyType()); 
    }
     
    public PstCurrencyType(String sOid) throws DBException { 
		super(new PstCurrencyType(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}
     
     
    public PstCurrencyType(long lOid) throws DBException { 
		super(new PstCurrencyType(0)); 
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
        return new PstCurrencyType().getClass().getName(); 
    }
    
    public String getTableName() {
        return TBL_CURRENCY_TYPE;
    }
    
    public long deleteExc(Entity ent) throws Exception {
         if(ent==null){ 
		throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
	 return deleteExc(ent.getOID());
        
    }
    
    public long fetchExc(Entity ent) throws Exception {
            CurrencyType currencyType = fetchExc(ent.getOID()); 
		ent = (Entity)currencyType; 
		return currencyType.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception {
         return insertExc((CurrencyType) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
         return updateExc((CurrencyType) ent); 
    }
    
    
    public static CurrencyType fetchExc(long oid) throws DBException{ 
		try{ 
			CurrencyType currencyType = new CurrencyType();
			PstCurrencyType pstCurrencyType = new PstCurrencyType(oid); 
			currencyType.setOID(oid);
                	currencyType.setCode(pstCurrencyType.getString(FLD_CODE));
                        currencyType.setName(pstCurrencyType.getString(FLD_NAME));
                        currencyType.setTabIndex(pstCurrencyType.getInt(FLD_TAB_INDEX));
                        currencyType.setIncludeInfProcess(pstCurrencyType.getInt(FLD_INCLUDE_INF_PROCESS));
                        currencyType.setDescription(pstCurrencyType.getString(FLD_DESCRIPTION));
                        currencyType.setFormatCurrency(pstCurrencyType.getString(FLD_FORMAT_CURRENCY));
           	
			return currencyType;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstCurrencyType(0),DBException.UNKNOWN); 
		} 
	}
    
    public static long insertExc(CurrencyType currencyType) throws DBException{ 
		try{ 
			PstCurrencyType pstCurrencyType = new PstCurrencyType(0);

			pstCurrencyType.setString(FLD_CODE, currencyType.getCode());
                        pstCurrencyType.setString(FLD_NAME, currencyType.getName());
                        pstCurrencyType.setInt(FLD_TAB_INDEX, currencyType.getTabIndex());
                        pstCurrencyType.setInt(FLD_TAB_INDEX, currencyType.getTabIndex());
                        pstCurrencyType.setString(FLD_DESCRIPTION, currencyType.getDescription());
                        pstCurrencyType.setString(FLD_FORMAT_CURRENCY, currencyType.getFormatCurrency());
                        
                pstCurrencyType.insert();
			currencyType.setOID(pstCurrencyType.getlong(FLD_CURRENCY_TYPE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstCurrencyType(0),DBException.UNKNOWN); 
		}
		return currencyType.getOID();
	}
    
    public static long updateExc(CurrencyType currencyType) throws DBException{ 
		try{ 
		  if(currencyType.getOID() != 0){ 
                        PstCurrencyType pstCurrencyType = new PstCurrencyType(currencyType.getOID());
                        pstCurrencyType.setString(FLD_CODE, currencyType.getCode());
                        pstCurrencyType.setString(FLD_NAME, currencyType.getName());
                        pstCurrencyType.setInt(FLD_TAB_INDEX, currencyType.getTabIndex());
                        pstCurrencyType.setInt(FLD_INCLUDE_INF_PROCESS, currencyType.getIncludeInfProcess());
                        pstCurrencyType.setString(FLD_DESCRIPTION, currencyType.getDescription());
                        pstCurrencyType.setString(FLD_FORMAT_CURRENCY, currencyType.getFormatCurrency());
                        
                        
                pstCurrencyType.update();
				return currencyType.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstCurrencyType(0),DBException.UNKNOWN); 
		}
		return 0;
	}
    
    public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstCurrencyType pstCurrencyType = new PstCurrencyType(oid);
			pstCurrencyType.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstCurrencyType(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_CURRENCY_TYPE; 
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
				CurrencyType currencyType = new CurrencyType();
				resultToObject(rs, currencyType);
				lists.add(currencyType);
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
    
    
    public static void resultToObject(ResultSet rs, CurrencyType currencyType){
		try{
			currencyType.setOID(rs.getLong(PstCurrencyType.fieldNames[PstCurrencyType.FLD_CURRENCY_TYPE_ID]));
                        currencyType.setCode(rs.getString(PstCurrencyType.fieldNames[PstCurrencyType.FLD_CODE]));
                        currencyType.setName(rs.getString(PstCurrencyType.fieldNames[PstCurrencyType.FLD_NAME]));
                        currencyType.setTabIndex(rs.getInt(PstCurrencyType.fieldNames[PstCurrencyType.FLD_TAB_INDEX]));
                        currencyType.setIncludeInfProcess(rs.getInt(PstCurrencyType.fieldNames[PstCurrencyType.FLD_INCLUDE_INF_PROCESS]));
                        currencyType.setDescription(rs.getString(PstCurrencyType.fieldNames[PstCurrencyType.FLD_DESCRIPTION]));
                        currencyType.setFormatCurrency(rs.getString(PstCurrencyType.fieldNames[PstCurrencyType.FLD_FORMAT_CURRENCY]));
		}catch(Exception e){ }
	}
   
    
    public static boolean checkOID(long currencyId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_CURRENCY_TYPE + " WHERE " + 
						PstCurrencyType.fieldNames[PstCurrencyType.FLD_CURRENCY_TYPE_ID] + " = '" + currencyId+"'";

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
			String sql = "SELECT COUNT("+ PstCurrencyType.fieldNames[PstCurrencyType.FLD_CURRENCY_TYPE_ID] + ") FROM " + TBL_CURRENCY_TYPE;
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
			  	   CurrencyType currencyType = (CurrencyType)list.get(ls);
				   if(oid == currencyType.getOID())
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
    
     /*public static void main(String args[]) {
        //long result = getPeriodIdBySelectedDate(new Date(104,5,31));
        //long result = getPeriodIdJustBefore(504404240112808778L);
        //System.out.println("result : " + result);
        CurrencyType currencyType = new CurrencyType();
        currencyType.setCode("sdfs5");
        currencyType.setName("asdasdas");
        
        try{
            PstCurrencyType.insertExc(currencyType);
        }catch(Exception e){
            System.out.println("Err"+e.toString());
        }
        
    }*/
   
    
}
