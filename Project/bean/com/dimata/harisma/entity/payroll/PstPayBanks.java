/*
 * PstPayBanks.java
 *
 * Created on March 31, 2007, 10:33 AM
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

/*package harisma,entity*/
import com.dimata.harisma.entity.payroll.*;
/**
 *
 * @author  autami
 */
public class PstPayBanks extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    
    public static final  String TBL_PAY_BANKS = "pay_banks";//"PAY_BANKS";

	public static final  int FLD_BANK_ID = 0;
        public static final  int FLD_BANK_NAME = 1;
        public static final  int FLD_BANK_BRANCH = 2;
	public static final  int FLD_BANK_ADDRESS = 3;
        public static final  int FLD_BANK_SWIFTCODE = 4;
        public static final  int FLD_BANK_TELP = 5;
        public static final  int FLD_BANK_FAX = 6;
    
        public static final  String[] fieldNames = {
		"BANK_ID",
		"BANK_NAME",
		"BRANCH",
		"ADDRESS",
		"SWIFT_CODE",
		"TEL",
		"FAX"	
		
	 };
         
         public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,		
		
	 };
         
    /** Creates a new instance of PstPayBanks */
    public PstPayBanks() {
    }
    
    public PstPayBanks(int i) throws DBException { 
		super(new PstPayBanks()); 
    }
    
    public PstPayBanks(String sOid) throws DBException { 
		super(new PstPayBanks(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
    }
    
    public PstPayBanks(long lOid) throws DBException { 
		super(new PstPayBanks(0)); 
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
    
    public String[] getFieldNames(){ 
		return fieldNames; 
    }
    
    public int getFieldSize() {
         return fieldNames.length; 
    }
    
    public String getTableName(){ 
		return TBL_PAY_BANKS;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes; 
    }
    
     public String getPersistentName() {
         return new PstPayBanks().getClass().getName(); 
    }  
    
    public long deleteExc(Entity ent) throws Exception {
         if(ent==null){ 
		throw new DBException(this,DBException.RECORD_NOT_FOUND); 
	 } 
	 return deleteExc(ent.getOID());
    }    
   
    public long fetchExc(Entity ent) throws Exception {
         PayBanks payBanks = fetchExc(ent.getOID()); 
	 ent = (Entity)payBanks; 
	 return payBanks.getOID(); 
    }     
 
    public long insertExc(Entity ent) throws Exception {
        return insertExc((PayBanks) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((PayBanks) ent); 
    }
    
    public static PayBanks fetchExc(long oid) throws DBException {
        try{
            PayBanks payBanks = new PayBanks();
            PstPayBanks pstPayBanks = new PstPayBanks(oid); 
            payBanks.setOID(oid);
            
            payBanks.setBankName(pstPayBanks.getString(FLD_BANK_NAME));
            payBanks.setBankBranch(pstPayBanks.getString(FLD_BANK_BRANCH));
            payBanks.setBankAddress(pstPayBanks.getString(FLD_BANK_ADDRESS));
            payBanks.setSwiftCode(pstPayBanks.getString(FLD_BANK_SWIFTCODE));
            payBanks.setBankTelp(pstPayBanks.getString(FLD_BANK_TELP));
            payBanks.setBankFax(pstPayBanks.getString(FLD_BANK_FAX)); 
            
            return payBanks;
            
        }catch(DBException dbe){
            throw dbe; 
        }catch(Exception e){
            throw new DBException(new PstPayBanks(0),DBException.UNKNOWN); 
        }
            
			 
    }
    
    public static long insertExc(PayBanks payBanks) throws DBException{
        try{
            PstPayBanks pstPayBanks = new PstPayBanks(0);
            
            pstPayBanks.setString(FLD_BANK_NAME, payBanks.getBankName());
            pstPayBanks.setString(FLD_BANK_BRANCH, payBanks.getBankBranch());
            pstPayBanks.setString(FLD_BANK_ADDRESS, payBanks.getBankAddress());
            pstPayBanks.setString(FLD_BANK_SWIFTCODE, payBanks.getSwiftCode());
            pstPayBanks.setString(FLD_BANK_TELP, payBanks.getBankTelp());
            pstPayBanks.setString(FLD_BANK_FAX, payBanks.getBankFax());
            
            pstPayBanks.insert();
	    payBanks.setOID(pstPayBanks.getlong(FLD_BANK_ID));
            
        }catch(DBException dbe){
            throw dbe;
        }catch(Exception e){
           throw new DBException(new PstPayBanks(0),DBException.UNKNOWN);  
        }
        return payBanks.getOID();
    }
    
    public static long updateExc(PayBanks payBanks) throws DBException{
        try{
            if(payBanks.getOID()!=0){
                PstPayBanks pstPayBanks = new PstPayBanks(payBanks.getOID());
                
                pstPayBanks.setString(FLD_BANK_NAME, payBanks.getBankName());
                pstPayBanks.setString(FLD_BANK_BRANCH, payBanks.getBankBranch());
                pstPayBanks.setString(FLD_BANK_ADDRESS, payBanks.getBankAddress());
                pstPayBanks.setString(FLD_BANK_SWIFTCODE, payBanks.getSwiftCode());
                pstPayBanks.setString(FLD_BANK_TELP, payBanks.getBankTelp());
                pstPayBanks.setString(FLD_BANK_FAX, payBanks.getBankFax());
                
                pstPayBanks.update();
		return payBanks.getOID();
            }
            
        }catch(DBException dbe){ 
            throw dbe; 
	}catch(Exception e){ 
            throw new DBException(new PstPayGeneral(0),DBException.UNKNOWN); 
	}
	return 0;
    }
        
    public static long deleteExc(long oid) throws DBException{ 
        try{ 
            
            PstPayBanks pstPayBanks = new PstPayBanks(oid);
            pstPayBanks.delete();
            
	}catch(DBException dbe){ 
            throw dbe; 
	}catch(Exception e){ 
            throw new DBException(new PstPayGeneral(0),DBException.UNKNOWN); 
	}
            return oid;
    }

   public static PayBanks getByName(String bankName){
       String where = fieldNames[FLD_BANK_NAME]+"=\""+bankName.trim()+"\"";
       Vector list= list(0,0,where,"");
       if(list!=null && list.size()>0){
           return (PayBanks)list.get(0);
       } 
       return null;
   }
   
   public static long getOidByName(String bankName){
       String where = fieldNames[FLD_BANK_NAME]+"=\""+bankName.trim()+"\"";
       Vector list= list(0,0,where,"");
       if(list!=null && list.size()>0){
           return ((PayBanks)list.get(0)).getOID();
       } 
       return 0;
   }
   
    
   public static Vector listAll(){ 
       	return list(0, 1000, "","");
   }
   
   public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
        Vector lists = new Vector(); 
        DBResultSet dbrs = null;
        try {
                String sql = "SELECT * FROM " + TBL_PAY_BANKS; 
                if(whereClause != null && whereClause.length() > 0)
                        sql = sql + " WHERE " + whereClause;
                if(order != null && order.length() > 0)
                        sql = sql + " ORDER BY " + order;
                if(limitStart == 0 && recordToGet == 0)
                        sql = sql + "";
                else
                        sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                dbrs = DBHandler.execQueryResult(sql);
                
                System.out.println("ini sql dari pay banks : "+sql);
                ResultSet rs = dbrs.getResultSet();
                while(rs.next()) {
                        PayBanks payBanks = new PayBanks();
                        resultToObject(rs, payBanks);
                        lists.add(payBanks);
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
   
   
   public static void resultToObject(ResultSet rs, PayBanks payBanks){
        try{
                payBanks.setOID(rs.getLong(PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ID]));
                payBanks.setBankName(rs.getString(PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_NAME]));
                payBanks.setBankBranch(rs.getString(PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_BRANCH]));
                payBanks.setBankAddress(rs.getString(PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ADDRESS]));
                payBanks.setSwiftCode(rs.getString(PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_SWIFTCODE]));
                payBanks.setBankTelp(rs.getString(PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_TELP]));
                payBanks.setBankFax(rs.getString(PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_FAX]));                               

        }catch(Exception e){ }
   }
   
   public static boolean checkOID(long bankId){
        DBResultSet dbrs = null;
        boolean result = false;
        try{
                String sql = "SELECT * FROM " + TBL_PAY_BANKS + " WHERE " + 
                                        PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ID] + " = '" + bankId+"'";

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while(rs.next()) { 
                    result = true; 
                }                
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
                String sql = "SELECT COUNT("+ PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ID] + ") FROM " + TBL_PAY_BANKS;
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
                           PayBanks payBanks = (PayBanks)list.get(ls);
                           if(oid == payBanks.getOID())
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
    	if(start == 0){
            cmd =  Command.FIRST;
        }else{
            if(start == (vectSize-recordToGet)){
                cmd = Command.LAST;
            }else{
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
