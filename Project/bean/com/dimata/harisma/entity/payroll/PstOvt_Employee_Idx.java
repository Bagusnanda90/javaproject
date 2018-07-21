/*
 * PstOvt_Employee_Idx.java
 *
 * Created on April 6, 2007, 5:18 PM
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

public class PstOvt_Employee_Idx extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    
        public static final  String TBL_OVT_EMPLOYEE_IDX = "pay_ovt_empl_idx";//"PAY_OVT_EMPL_IDX";
     
        public static final  int FLD_OVT_EMPL_IDX = 0;
	public static final  int FLD_OVT_IDX_ID = 1;
	public static final  int FLD_OVT_EMPLY_ID = 2;
	public static final  int FLD_VALUE_IDX = 3;
	
        
         public static final  String[] fieldNames = {
		"OVT_EMPL_IDX",
		"OVT_IDX_ID",
		"OVT_EMPLY_ID",
		"VALUE_IDX"
	};
         
         public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
                TYPE_LONG,
                TYPE_FLOAT
         };
    /** Creates a new instance of PstOvt_Employee_Idx */
    public PstOvt_Employee_Idx() {
    }
    
    public PstOvt_Employee_Idx(int i) throws DBException {
        super(new PstOvt_Employee_Idx());
    }
    
    public PstOvt_Employee_Idx(String sOid) throws DBException {
        super(new PstOvt_Employee_Idx(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
     public PstOvt_Employee_Idx(long lOid) throws DBException {
        super(new PstOvt_Employee_Idx(0));
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
         return new PstOvt_Employee_Idx().getClass().getName();
    }
    
    public String getTableName() {
         return TBL_OVT_EMPLOYEE_IDX;
    }
    
    public long insertExc(Entity ent) throws Exception {
         return insertExc((Ovt_Employee_Idx) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
         return updateExc((Ovt_Employee_Idx) ent);
    }
    
     public long deleteExc(Entity ent) throws Exception {
          if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public long fetchExc(Entity ent) throws Exception {
        Ovt_Employee_Idx ovt_Employee_Idx = fetchExc(ent.getOID());
        ent = (Entity) ovt_Employee_Idx;
        return ovt_Employee_Idx.getOID();
    }
    
     public static Ovt_Employee_Idx fetchExc(long oid) throws DBException {
        try {
            Ovt_Employee_Idx ovt_Employee_Idx = new Ovt_Employee_Idx();
            PstOvt_Employee_Idx pstOvt_Employee_Idx = new PstOvt_Employee_Idx(oid);
            ovt_Employee_Idx.setOID(oid);

            ovt_Employee_Idx.setEmployee_id(pstOvt_Employee_Idx.getlong(FLD_OVT_EMPLY_ID));
            ovt_Employee_Idx.setOvt_idx_id(pstOvt_Employee_Idx.getlong(FLD_OVT_IDX_ID));
            ovt_Employee_Idx.setValue_idx(pstOvt_Employee_Idx.getdouble(FLD_VALUE_IDX));
            
            return ovt_Employee_Idx;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvt_Employee_Idx(0), DBException.UNKNOWN);
        }
    }
    
    public static long insertExc(Ovt_Employee_Idx ovt_Employee_Idx) throws DBException {
        try {
            PstOvt_Employee_Idx pstOvt_Employee_Idx = new PstOvt_Employee_Idx(0);

            pstOvt_Employee_Idx.setLong(FLD_OVT_EMPLY_ID, ovt_Employee_Idx.getEmployee_id());
            pstOvt_Employee_Idx.setLong(FLD_OVT_IDX_ID, ovt_Employee_Idx.getOvt_idx_id());
            pstOvt_Employee_Idx.setDouble(FLD_VALUE_IDX, ovt_Employee_Idx.getValue_idx());
            
            pstOvt_Employee_Idx.insert();
            ovt_Employee_Idx.setOID(pstOvt_Employee_Idx.getlong(FLD_OVT_EMPL_IDX));
        }catch(DBException dbe){ 
                throw dbe; 
        }catch(Exception e){ 
                throw new DBException(new PstOvt_Employee_Idx(0),DBException.UNKNOWN); 
        }
        return ovt_Employee_Idx.getOID();
    }
    
    public static long updateExc(Ovt_Employee_Idx ovt_Employee_Idx) throws DBException {
        try {
            if (ovt_Employee_Idx.getOID() != 0) {
                PstOvt_Employee_Idx pstOvt_Employee_Idx = new PstOvt_Employee_Idx(ovt_Employee_Idx.getOID());

                pstOvt_Employee_Idx.setLong(FLD_OVT_EMPLY_ID, ovt_Employee_Idx.getEmployee_id());
                pstOvt_Employee_Idx.setLong(FLD_OVT_IDX_ID, ovt_Employee_Idx.getOvt_idx_id());
                pstOvt_Employee_Idx.setDouble(FLD_VALUE_IDX, ovt_Employee_Idx.getValue_idx());
                
                
                pstOvt_Employee_Idx.update();
                
            return ovt_Employee_Idx.getOID();
            }
    }catch(DBException dbe){ 
            throw dbe; 
    }catch(Exception e){ 
            throw new DBException(new PstOvt_Employee_Idx(0),DBException.UNKNOWN); 
    }
    return 0;
    }
    
      public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstOvt_Employee_Idx pstOvt_Employee_Idx = new PstOvt_Employee_Idx(oid);
			pstOvt_Employee_Idx.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstOvt_Employee_Idx(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_OVT_EMPLOYEE_IDX; 
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
				Ovt_Employee_Idx ovt_Employee_Idx = new Ovt_Employee_Idx();
				resultToObject(rs, ovt_Employee_Idx);
				lists.add(ovt_Employee_Idx);
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
      
      public static void resultToObject(ResultSet rs, Ovt_Employee_Idx ovt_Employee_Idx){
		try{
			ovt_Employee_Idx.setOID(rs.getLong(PstOvt_Employee_Idx.fieldNames[PstOvt_Employee_Idx.FLD_OVT_EMPL_IDX]));
                        ovt_Employee_Idx.setEmployee_id(rs.getLong(PstOvt_Employee_Idx.fieldNames[PstOvt_Employee_Idx.FLD_OVT_EMPLY_ID]));
                        ovt_Employee_Idx.setOvt_idx_id(rs.getLong(PstOvt_Employee_Idx.fieldNames[PstOvt_Employee_Idx.FLD_OVT_IDX_ID]));
                        ovt_Employee_Idx.setValue_idx(rs.getDouble(PstOvt_Employee_Idx.fieldNames[PstOvt_Employee_Idx.FLD_VALUE_IDX]));
                       
		}catch(Exception e){ }
	}
      
      public static boolean checkOID(long ovt_Employee_IdxId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_OVT_EMPLOYEE_IDX + " WHERE " + 
						PstOvt_Employee_Idx.fieldNames[PstOvt_Employee_Idx.FLD_OVT_EMPL_IDX] + " = '" +ovt_Employee_IdxId+"'";

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
			String sql = "SELECT COUNT("+PstOvt_Employee_Idx.fieldNames[PstOvt_Employee_Idx.FLD_OVT_EMPL_IDX] + ") FROM " + TBL_OVT_EMPLOYEE_IDX ;
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
			  	  Ovt_Employee_Idx ovt_Employee_Idx = (Ovt_Employee_Idx)list.get(ls);
				   if(oid == ovt_Employee_Idx.getOID())
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

