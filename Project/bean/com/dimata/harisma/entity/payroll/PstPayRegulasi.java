/*
 * PstPayRegulasi.java
 *
 * Created on August 29, 2007, 4:07 PM
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
import com.dimata.harisma.entity.payroll.*;
/**
 *
 * @author  emerliana
 */
public class PstPayRegulasi extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    
    public static final  String TBL_PAY_REGULASI = "pay_regulasi";//"PAY_REGULASI";
    
    public static final  int FLD_REGULASI_ID = 0;
    public static final  int FLD_PERIODE = 1;
    public static final  int FLD_START_DATE = 2;
    public static final  int FLD_END_DATE = 3;
    public static final  int FLD_STATUS = 4;
    
    public static final  String[] fieldNames = {
        "REGULASI_ID",
        "PERIODE",
        "START_DATE",
        "END_DATE",
        "STATUS"
    };
    
    public static final  int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT
    };
    
     public static final int VALID = 1;
     public static final int NOT_VALID = 2;

      public static final String[]nameStatus = {
            "","Valid","Not Valid"
      };
    
    public PstPayRegulasi(){
	}

	public PstPayRegulasi(int i) throws DBException { 
		super(new PstPayRegulasi()); 
	}

	public PstPayRegulasi(String sOid) throws DBException { 
		super(new PstPayRegulasi(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstPayRegulasi(long lOid) throws DBException { 
		super(new PstPayRegulasi(0)); 
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
        PayRegulasi payRegulasi = fetchExc(ent.getOID()); 
        ent = (Entity)payRegulasi; 
        return payRegulasi.getOID(); 
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
         return new PstPayRegulasi().getClass().getName(); 
    }
    
    public String getTableName() {
        return TBL_PAY_REGULASI;
    }
    
    public long insertExc(Entity ent) throws Exception {
         return insertExc((PayRegulasi) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((PayRegulasi) ent); 
    }
    
    public static PayRegulasi fetchExc(long oid) throws DBException{ 
		try{ 
			PayRegulasi payRegulasi = new PayRegulasi();
			PstPayRegulasi pstPayRegulasi = new PstPayRegulasi(oid); 
			payRegulasi.setOID(oid);
                        payRegulasi.setRegulasi_id(pstPayRegulasi.getlong(FLD_REGULASI_ID));
                        payRegulasi.setPeriod(pstPayRegulasi.getString(FLD_PERIODE));
                        payRegulasi.setStartDate(pstPayRegulasi.getDate(FLD_START_DATE));
                        payRegulasi.setEndDate(pstPayRegulasi.getDate(FLD_END_DATE));
                        payRegulasi.setStatus(pstPayRegulasi.getInt(FLD_STATUS));
                        
			return payRegulasi; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTax_PTKP(0),DBException.UNKNOWN); 
		} 
	}
    
    public static long insertExc(PayRegulasi payRegulasi) throws DBException{ 
		try{ 
			PstPayRegulasi pstPayRegulasi = new PstPayRegulasi(0);

			pstPayRegulasi.setString(FLD_PERIODE, payRegulasi.getPeriod());
                        pstPayRegulasi.setDate(FLD_START_DATE, payRegulasi.getStartDate());
                        pstPayRegulasi.setDate(FLD_END_DATE, payRegulasi.getEndDate());
                        pstPayRegulasi.setInt(FLD_STATUS, payRegulasi.getStatus());
                        
			pstPayRegulasi.insert(); 
			payRegulasi.setOID(pstPayRegulasi.getlong(FLD_REGULASI_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayRegulasi(0),DBException.UNKNOWN); 
		}
		return payRegulasi.getOID();
	}
    
    public static long updateExc(PayRegulasi payRegulasi) throws DBException{ 
		try{ 
			if(payRegulasi.getOID() != 0){ 
				PstPayRegulasi pstPayRegulasi = new PstPayRegulasi(payRegulasi.getOID());

				pstPayRegulasi.setString(FLD_PERIODE, payRegulasi.getPeriod());
                                pstPayRegulasi.setDate(FLD_START_DATE, payRegulasi.getStartDate());
                                pstPayRegulasi.setDate(FLD_END_DATE, payRegulasi.getEndDate());
                                pstPayRegulasi.setInt(FLD_STATUS, payRegulasi.getStatus());

				pstPayRegulasi.update(); 
				return payRegulasi.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayRegulasi(0),DBException.UNKNOWN); 
		}
		return 0;
	}
    
     public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstPayRegulasi pstPayRegulasi = new PstPayRegulasi(oid);
			pstPayRegulasi.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayRegulasi(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_PAY_REGULASI; 
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
				PayRegulasi payRegulasi = new PayRegulasi();
				resultToObject(rs, payRegulasi);
				lists.add(payRegulasi);
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
      
      
   private static void resultToObject(ResultSet rs, PayRegulasi payRegulasi){
		try{
			payRegulasi.setOID(rs.getLong(PstPayRegulasi.fieldNames[PstPayRegulasi.FLD_REGULASI_ID]));
			payRegulasi.setPeriod(rs.getString(PstPayRegulasi.fieldNames[PstPayRegulasi.FLD_PERIODE]));
                        payRegulasi.setStartDate(rs.getDate(PstPayRegulasi.fieldNames[PstPayRegulasi.FLD_START_DATE]));
                        payRegulasi.setEndDate(rs.getDate(PstPayRegulasi.fieldNames[PstPayRegulasi.FLD_END_DATE]));
                        payRegulasi.setStatus(rs.getInt(PstPayRegulasi.fieldNames[PstPayRegulasi.FLD_STATUS]));
                        
		}catch(Exception e){ }
	}
   
   public static boolean checkOID(long regulasiId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_PAY_REGULASI + " WHERE " + 
						PstPayRegulasi.fieldNames[PstPayRegulasi.FLD_REGULASI_ID] + " = " + regulasiId;

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
			String sql = "SELECT COUNT("+ PstPayRegulasi.fieldNames[PstPayRegulasi.FLD_REGULASI_ID] + ") FROM " + TBL_PAY_REGULASI;
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

 public static void UpdateStatusRegulasi() {
        DBResultSet dbrs = null;
         try{
            String sql = "";
                sql = " UPDATE " + TBL_PAY_REGULASI + " SET " + 
                   PstPayRegulasi.fieldNames[PstPayRegulasi.FLD_STATUS] +"="+PstPayRegulasi.NOT_VALID+" WHERE "+          
                   PstPayRegulasi.fieldNames[PstPayRegulasi.FLD_STATUS]+"="+PstPayRegulasi.VALID;
       
             System.out.println("\tupdateStatusRegulasi : " + sql);
            //dbrs = DBHandler.execQueryResult(sql);
            int status = DBHandler.execUpdate(sql);
            //ResultSet rs = dbrs.getResultSet();
           
            //while(rs.next()) { result = true; }
            //rs.close();
        } catch(Exception e) {
            System.err.println("\tupdateSetupEmployee error : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            //return result;
        }
    }

public static void main(String args[]) {
     PayRegulasi payRegulasi = new PayRegulasi();
     payRegulasi.setPeriod("2003");
     payRegulasi.setStartDate(new Date());
     payRegulasi.setEndDate(new Date());
     payRegulasi.setStatus(1);
     try{
        PstPayRegulasi.insertExc(payRegulasi);
     }catch(Exception e){;}
        
    }
    
}
