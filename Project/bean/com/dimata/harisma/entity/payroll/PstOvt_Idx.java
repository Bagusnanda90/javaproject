/*
 * PstOvt_Idx.java
 *
 * Created on April 12, 2007, 9:44 AM
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
public class PstOvt_Idx extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    
     public static final  String TBL_OVT_IDX = "pay_ovt_idx";//"PAY_OVT_IDX";
     
        public static final  int FLD_OVT_IDX_ID = 0;
	public static final  int FLD_HOUR_FROM = 1;
	public static final  int FLD_HOUR_TO = 2;
	public static final  int FLD_OVT_IDX  = 3;
	public static final  int FLD_OVT_TYPE_CODE  = 4;
     
        
         public static final  String[] fieldNames = {
		"OVT_IDX_ID",
		"HOUR_FROM",
		"HOUR_TO",
		"OVT_IDX",
                "OVT_TYPE_CODE"
        };
         
         public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_STRING
         };
    
    /** Creates a new instance of PstOvt_Idx */
    public PstOvt_Idx() {
    }
    
     public PstOvt_Idx(int i) throws DBException {
        super(new PstOvt_Idx());
    }
     
     public PstOvt_Idx(String sOid) throws DBException {
        super(new PstOvt_Idx(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
     
    public PstOvt_Idx(long lOid) throws DBException {
        super(new PstOvt_Idx(0));
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
        return new PstOvt_Idx().getClass().getName();
    }
    
    public String getTableName() {
        return TBL_OVT_IDX;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((Ovt_Idx) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((Ovt_Idx) ent);
    }
    
     public long deleteExc(Entity ent) throws Exception {
           if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public long fetchExc(Entity ent) throws Exception {
        Ovt_Idx ovt_Idx = fetchExc(ent.getOID());
        ent = (Entity) ovt_Idx;
        return ovt_Idx.getOID();
    }
    
     public static Ovt_Idx fetchExc(long oid) throws DBException {
        try {
            Ovt_Idx ovt_Idx = new Ovt_Idx();
            PstOvt_Idx pstOvt_Idx = new PstOvt_Idx(oid);
            ovt_Idx.setOID(oid);

            ovt_Idx.setHour_from(pstOvt_Idx.getdouble(FLD_HOUR_FROM));
            ovt_Idx.setHour_to(pstOvt_Idx.getdouble(FLD_HOUR_TO));
            ovt_Idx.setOvt_idx(pstOvt_Idx.getdouble(FLD_OVT_IDX));
            ovt_Idx.setOvt_type_code(pstOvt_Idx.getString(FLD_OVT_TYPE_CODE));
            
            return ovt_Idx;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvt_Idx(0), DBException.UNKNOWN);
        }
    }
     
     public static long insertExc(Ovt_Idx ovt_Idx) throws DBException {
        try {
            PstOvt_Idx pstOvt_Idx = new PstOvt_Idx(0);

            pstOvt_Idx.setDouble(FLD_HOUR_FROM, ovt_Idx.getHour_from());
            pstOvt_Idx.setDouble(FLD_HOUR_TO, ovt_Idx.getHour_to());
            pstOvt_Idx.setDouble(FLD_OVT_IDX, ovt_Idx.getOvt_idx());
            pstOvt_Idx.setString(FLD_OVT_TYPE_CODE, ovt_Idx.getOvt_type_code());
            
            pstOvt_Idx.insert();
            ovt_Idx.setOID(pstOvt_Idx.getlong(FLD_OVT_IDX_ID));
        }catch(DBException dbe){ 
                throw dbe; 
        }catch(Exception e){ 
                throw new DBException(new PstOvt_Idx(0),DBException.UNKNOWN); 
        }
        return ovt_Idx.getOID();
    }
    
    public static long updateExc(Ovt_Idx ovt_Idx) throws DBException {
        try {
            if (ovt_Idx.getOID() != 0) {
                PstOvt_Idx pstOvt_Idx = new PstOvt_Idx(ovt_Idx.getOID());

                pstOvt_Idx.setDouble(FLD_HOUR_FROM, ovt_Idx.getHour_from());
                pstOvt_Idx.setDouble(FLD_HOUR_TO, ovt_Idx.getHour_to());
                pstOvt_Idx.setDouble(FLD_OVT_IDX, ovt_Idx.getOvt_idx());
                pstOvt_Idx.setString(FLD_OVT_TYPE_CODE, ovt_Idx.getOvt_type_code());
            
                
                pstOvt_Idx.update();
                
            return ovt_Idx.getOID();
            }
    }catch(DBException dbe){ 
            throw dbe; 
    }catch(Exception e){ 
            throw new DBException(new PstOvt_Idx(0),DBException.UNKNOWN); 
    }
    return 0;
    }
    
     public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstOvt_Idx pstOvt_Idx = new PstOvt_Idx(oid);
			pstOvt_Idx.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstOvt_Idx(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_OVT_IDX; 
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
				Ovt_Idx ovt_Idx = new Ovt_Idx();
				resultToObject(rs, ovt_Idx);
				lists.add(ovt_Idx);
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
     
     
      public static void resultToObject(ResultSet rs, Ovt_Idx ovt_Idx){
		try{
			ovt_Idx.setOID(rs.getLong(PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_IDX_ID]));
                        ovt_Idx.setHour_from(rs.getDouble(PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_HOUR_FROM]));
                        ovt_Idx.setHour_to(rs.getDouble(PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_HOUR_TO]));
                        ovt_Idx.setOvt_idx(rs.getDouble(PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_IDX]));
                        ovt_Idx.setOvt_type_code(rs.getString(PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_TYPE_CODE]));
                        
		}catch(Exception e){ }
	}
      
       public static boolean checkOID(long ovt_IdxId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_OVT_IDX + " WHERE " + 
						PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_IDX_ID] + " = '" +ovt_IdxId+"'";

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
			String sql = "SELECT COUNT("+PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_IDX_ID] + ") FROM " + TBL_OVT_IDX ;
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
			  	  Ovt_Idx ovt_Idx = (Ovt_Idx)list.get(ls);
				   if(oid == ovt_Idx.getOID())
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

 /**
    * this method used to get max of overtime idx
    * @param = dayOfType
    * created by Yunny
    */
   public static double getMaxOvtIdx(int dayOfType){
        double  result =0; 
        DBResultSet dbrs = null;
        try {
                 String sql = "SELECT MAX("+PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_IDX]+")FROM " + PstOvt_Idx.TBL_OVT_IDX + " AS idx"+  
                           " INNER JOIN "+PstOvt_Type.TBL_OVT_TYPE+ " as type"+
                           " ON idx."+PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_TYPE_CODE]+
                           " = type."+PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_CODE]+
                           " WHERE type."+PstOvt_Type.fieldNames[PstOvt_Type.FLD_TYPE_OF_DAY]+"="+dayOfType;
                 
               // System.out.println("sql getMaxIdx  "+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                 while(rs.next()) { result = rs.getDouble(1); }
                rs.close();
                return result;

        }catch(Exception e) {
                System.out.println(e);
        }finally {
                DBResultSet.close(dbrs);
        }
                return 0;
   }
   
   /**
    * this method used to get max of overtime idx
    * @param = dayOfType
    * created by Yunny
    */
   public static double getMinOvtIdx(int dayOfType){
        double  result =0; 
        DBResultSet dbrs = null;
        try {
                 String sql = "SELECT MIN("+PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_IDX]+")FROM " + PstOvt_Idx.TBL_OVT_IDX + " AS idx"+  
                           " INNER JOIN "+PstOvt_Type.TBL_OVT_TYPE+ " as type"+
                           " ON idx."+PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_TYPE_CODE]+
                           " = type."+PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_CODE]+
                           " WHERE type."+PstOvt_Type.fieldNames[PstOvt_Type.FLD_TYPE_OF_DAY]+"="+dayOfType;
                 
               // System.out.println("sql getMinIdx  "+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                 while(rs.next()) { result = rs.getDouble(1); }
                rs.close();
                return result;

        }catch(Exception e) {
                System.out.println(e);
        }finally {
                DBResultSet.close(dbrs);
        }
                return 0;
   }
    
    
}
