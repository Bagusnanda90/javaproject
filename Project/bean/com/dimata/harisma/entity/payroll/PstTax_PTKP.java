/*
 * PstTax_PTKP.java
 *
 * Created on August 20, 2007, 10:19 AM
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
public class PstTax_PTKP extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    
    public static final  String TBL_PAY_TAX_PTKP = "pay_tax_ptkp";//"PAY_TAX_PTKP";
    
    public static final  int FLD_PAY_TAX_PTKP_ID = 0;
    public static final  int FLD_MARITAL_ID = 1;
    public static final  int FLD_PTKP_SETAHUN = 2;
    public static final  int FLD_PTKP_SEBULAN = 3;
    public static final  int FLD_REGULASI_ID = 4;
    
    public static final  String[] fieldNames = {
        "PAY_TAX_PTKP_ID",
        "MARITAL_ID",
        "PTKP_SETAHUN",
        "PTKP_SEBULAN",
        "REGULASI_ID"
    };
    
    public static final  int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG
    };
    
      public PstTax_PTKP(){
	}

	public PstTax_PTKP(int i) throws DBException { 
		super(new PstTax_PTKP()); 
	}

	public PstTax_PTKP(String sOid) throws DBException { 
		super(new PstTax_PTKP(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstTax_PTKP(long lOid) throws DBException { 
		super(new PstTax_PTKP(0)); 
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
        return new PstTax_PTKP().getClass().getName(); 
    }
    
    public String getTableName() {
        return TBL_PAY_TAX_PTKP;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((Tax_PTKP) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((Tax_PTKP) ent); 
    }
    
    public long deleteExc(Entity ent) throws Exception {
        if(ent==null){ 
                throw new DBException(this,DBException.RECORD_NOT_FOUND); 
        } 
        return deleteExc(ent.getOID()); 
    }
    
    public long fetchExc(Entity ent) throws Exception {
        Tax_PTKP tax_ptkp = fetchExc(ent.getOID()); 
        ent = (Entity)tax_ptkp; 
        return tax_ptkp.getOID(); 
    }
    
    public static Tax_PTKP fetchExc(long oid) throws DBException{ 
		try{ 
			Tax_PTKP tax_ptkp = new Tax_PTKP();
			PstTax_PTKP pstTax_PTKP = new PstTax_PTKP(oid); 
			tax_ptkp.setOID(oid);
                        tax_ptkp.setMartialId(pstTax_PTKP.getlong(FLD_MARITAL_ID));
                        tax_ptkp.setPtkp_setahun(pstTax_PTKP.getInt(FLD_PTKP_SETAHUN));
                        tax_ptkp.setPtkp_sebulan(pstTax_PTKP.getInt(FLD_PTKP_SEBULAN));
                        tax_ptkp.setRegulasi_id(pstTax_PTKP.getlong(FLD_REGULASI_ID));

			return tax_ptkp; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTax_PTKP(0),DBException.UNKNOWN); 
		} 
	}
    
    public static long insertExc(Tax_PTKP tax_ptkp) throws DBException{ 
		try{ 
			PstTax_PTKP pstTax_PTKP = new PstTax_PTKP(0);

			pstTax_PTKP.setLong(FLD_MARITAL_ID, tax_ptkp.getMartialId());
                        pstTax_PTKP.setInt(FLD_PTKP_SETAHUN, tax_ptkp.getPtkp_setahun());
                        pstTax_PTKP.setInt(FLD_PTKP_SEBULAN, tax_ptkp.getPtkp_sebulan());
                        pstTax_PTKP.setLong(FLD_REGULASI_ID, tax_ptkp.getRegulasi_id());

			pstTax_PTKP.insert(); 
			tax_ptkp.setOID(pstTax_PTKP.getlong(FLD_PAY_TAX_PTKP_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTax_PTKP(0),DBException.UNKNOWN); 
		}
		return tax_ptkp.getOID();
	}
    
    public static long updateExc(Tax_PTKP tax_ptkp) throws DBException{ 
		try{ 
			if(tax_ptkp.getOID() != 0){ 
				PstTax_PTKP pstTax_PTKP = new PstTax_PTKP(tax_ptkp.getOID());

				pstTax_PTKP.setLong(FLD_MARITAL_ID, tax_ptkp.getMartialId());
                                pstTax_PTKP.setInt(FLD_PTKP_SETAHUN, tax_ptkp.getPtkp_setahun());
                                pstTax_PTKP.setInt(FLD_PTKP_SEBULAN, tax_ptkp.getPtkp_sebulan());
                                pstTax_PTKP.setLong(FLD_REGULASI_ID, tax_ptkp.getRegulasi_id());

				pstTax_PTKP.update(); 
				return tax_ptkp.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTax_PTKP(0),DBException.UNKNOWN); 
		}
		return 0;
	}
    
    public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstTax_PTKP pstTax_PTKP = new PstTax_PTKP(oid);
			pstTax_PTKP.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTax_PTKP(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_PAY_TAX_PTKP; 
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
				Tax_PTKP tax_ptkp = new Tax_PTKP();
				resultToObject(rs, tax_ptkp);
				lists.add(tax_ptkp);
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

	private static void resultToObject(ResultSet rs, Tax_PTKP tax_ptkp){
		try{
			tax_ptkp.setOID(rs.getLong(PstTax_PTKP.fieldNames[PstTax_PTKP.FLD_PAY_TAX_PTKP_ID]));
			tax_ptkp.setMartialId(rs.getLong(PstTax_PTKP.fieldNames[PstTax_PTKP.FLD_MARITAL_ID]));
                        tax_ptkp.setPtkp_setahun(rs.getInt(PstTax_PTKP.fieldNames[PstTax_PTKP.FLD_PTKP_SETAHUN]));
                        tax_ptkp.setPtkp_sebulan(rs.getInt(PstTax_PTKP.fieldNames[PstTax_PTKP.FLD_PTKP_SEBULAN]));
                        tax_ptkp.setRegulasi_id(rs.getLong(PstTax_PTKP.fieldNames[PstTax_PTKP.FLD_REGULASI_ID]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long tax_ptkpId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_PAY_TAX_PTKP + " WHERE " + 
						PstTax_PTKP.fieldNames[PstTax_PTKP.FLD_PAY_TAX_PTKP_ID] + " = " + tax_ptkpId;

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
			String sql = "SELECT COUNT("+ PstTax_PTKP.fieldNames[PstTax_PTKP.FLD_PAY_TAX_PTKP_ID] + ") FROM " + TBL_PAY_TAX_PTKP;
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


}
