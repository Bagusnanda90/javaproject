/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.util.Vector;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
/**
 *
 * @author Satrtya Ramayu
 */
public class PstPublicLeave extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
      public static final String TBL_PUBLIC_LEAVE = "hr_public_leave";//"HR_PUBLIC_HOLIDAYS";

    public static final int FLD_PUBLIC_LEAVE_ID = 0;
    public static final int FLD_PUBLIC_HOLIDAY_ID = 1;
    public static final int FLD_PUBLIC_DATE_FROM = 2;
    public static final int FLD_PUBLIC_DATE_TO = 3;
    public static final int FLD_TYPE_LEAVE = 4;
    public static final int FLD_PUBLIC_EMPCAT_ID= 5;
    public static final int FLD_FLAG_SCHDEULE=6;

    public static final String[] fieldNames = {
        "PUBLIC_LEAVE_ID",
        "PUBLIC_HOLIDAY_ID",
        "DATE_FROM",
        "DATE_TO",
        "TYPE_LEAVE",
        "EMPLOYEE_CATEGORY_ID",
        "FLAG_SCHDEULE"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT
    };

    public static int STS_NONE = 0;
    public static int STS_NATIONAL = 1;
    public static int STS_BLACK_DAY = 2;
    public static int STS_YELLOW_DAY = 3;
    
    
    public static String HINDU_STR = "Hindu";

    public static String[] stHolidaySts = {
        "-","National", "Black Day", "Yellow Day"
    }; 
      
    //artinya ini di tentukan apakah schedulenya di ambil di awal atau akhir
      public static int FLAG_SCH_AWAL= 0;
    public static int FLAG_SCH_AKHIR = 1;
    
    public static String[] flagSch= {
        "Awal","Akhir"
    }; 
    
    public PstPublicLeave() {
    }

    public PstPublicLeave(int i) throws DBException {
        super(new PstPublicLeave());
    }

    public PstPublicLeave(String sOid) throws DBException {
        super(new PstPublicLeave(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public PstPublicLeave(long lOid) throws DBException {
        super(new PstPublicLeave(0));
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

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_PUBLIC_LEAVE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPublicLeave().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        PublicLeave objPublicLeave = fetchExc(ent.getOID());
        return objPublicLeave.getOID();
    }

    public static PublicLeave fetchExc(long oid) throws DBException {
        try {
            PublicLeave objPublicLeave = new PublicLeave();
            PstPublicLeave objPstPublicLeave = new PstPublicLeave(oid);
            objPublicLeave.setOID(oid);

            objPublicLeave.setPublicHolidayId(objPstPublicLeave.getlong(FLD_PUBLIC_HOLIDAY_ID));
            objPublicLeave.setDateLeaveFrom(objPstPublicLeave.getDate(FLD_PUBLIC_DATE_FROM));
            objPublicLeave.setDateLeaveTo(objPstPublicLeave.getDate(FLD_PUBLIC_DATE_TO));
            objPublicLeave.setTypeLeave(objPstPublicLeave.getlong(FLD_TYPE_LEAVE));
             objPublicLeave.setEmpCat(objPstPublicLeave.getlong(FLD_PUBLIC_EMPCAT_ID));
             objPublicLeave.setFlagSch(objPstPublicLeave.getInt(FLD_FLAG_SCHDEULE));
            return objPublicLeave;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPublicLeave(0), DBException.UNKNOWN);
        }
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((PublicLeave) ent);
    }

    public static long updateExc(PublicLeave objPublicLeave) throws DBException {
        try {
            if (objPublicLeave.getOID() != 0) {
                PstPublicLeave objPstPublicLeave = new PstPublicLeave(objPublicLeave.getOID());

                objPstPublicLeave.setLong(FLD_PUBLIC_HOLIDAY_ID, objPublicLeave.getPublicHolidayId());
                objPstPublicLeave.setDate(FLD_PUBLIC_DATE_FROM, objPublicLeave.getDateLeaveFrom());
                objPstPublicLeave.setDate(FLD_PUBLIC_DATE_TO, objPublicLeave.getDateLeaveTo());
                objPstPublicLeave.setLong(FLD_TYPE_LEAVE, objPublicLeave.getTypeLeave());
                objPstPublicLeave.setLong(FLD_PUBLIC_EMPCAT_ID, objPublicLeave.getEmpCat());
                objPstPublicLeave.setInt(FLD_FLAG_SCHDEULE, objPublicLeave.getFlagSch());
                objPstPublicLeave.update();
                return objPublicLeave.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPublicLeave(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPublicLeave objPstPublicLeave = new PstPublicLeave(oid);
            objPstPublicLeave.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPublicLeave(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((PublicLeave) ent);
    }

    public static long insertExc(PublicLeave objPublicLeave) throws DBException {
        try {
            PstPublicLeave objPstPublicLeave = new PstPublicLeave(0);

            objPstPublicLeave.setLong(FLD_PUBLIC_HOLIDAY_ID, objPublicLeave.getPublicHolidayId());
            objPstPublicLeave.setDate(FLD_PUBLIC_DATE_FROM, objPublicLeave.getDateLeaveFrom());
            objPstPublicLeave.setDate(FLD_PUBLIC_DATE_TO, objPublicLeave.getDateLeaveTo());
            objPstPublicLeave.setLong(FLD_TYPE_LEAVE, objPublicLeave.getTypeLeave());
            objPstPublicLeave.setLong(FLD_PUBLIC_EMPCAT_ID, objPublicLeave.getEmpCat());
            objPstPublicLeave.setInt(FLD_FLAG_SCHDEULE, objPublicLeave.getFlagSch());
            objPstPublicLeave.insert();
            objPublicLeave.setOID(objPstPublicLeave.getlong(FLD_PUBLIC_LEAVE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPublicLeave(0), DBException.UNKNOWN);
        }
        return objPublicLeave.getOID();
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PUBLIC_LEAVE;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            
            //System.out.println("SQL List PstPublicLeave : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PublicLeave objPublicLeave = new PublicLeave();
                resultToObject(rs, objPublicLeave);
                lists.add(objPublicLeave);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
   
    
    private static void resultToObject(ResultSet rs, PublicLeave objPublicLeave) {
        try {
            objPublicLeave.setOID(rs.getLong(PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_LEAVE_ID]));
            objPublicLeave.setPublicHolidayId(rs.getLong(PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_HOLIDAY_ID]));
            objPublicLeave.setDateLeaveFrom(DBHandler.convertDate(rs.getDate(PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_DATE_FROM]), rs.getTime(PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_DATE_FROM])));            
            objPublicLeave.setDateLeaveTo(DBHandler.convertDate(rs.getDate(PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_DATE_TO]), rs.getTime(PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_DATE_TO])));
            objPublicLeave.setTypeLeave(rs.getLong(PstPublicLeave.fieldNames[PstPublicLeave.FLD_TYPE_LEAVE]));
            objPublicLeave.setEmpCat(rs.getLong(PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_EMPCAT_ID]));
            objPublicLeave.setFlagSch(rs.getInt(PstPublicLeave.fieldNames[PstPublicLeave.FLD_FLAG_SCHDEULE]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkOID(long publicLeaveId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_PUBLIC_LEAVE + " WHERE " +
						PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_LEAVE_ID] + " = " + publicLeaveId;

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
			String sql = "SELECT COUNT("+ PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_LEAVE_ID] + ") FROM " + TBL_PUBLIC_LEAVE;
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

    public static int findLimitStart( long oid, int recordToGet, String whereClause, String orderClause){
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, orderClause);
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){
			  	   PublicLeave objPublicLeave = (PublicLeave)list.get(ls);
				   if(oid == objPublicLeave.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}

    public static int findLimitCommand(int start, int recordToGet, int vectSize){
		 int cmd = Command.LIST;
		 int mdl = vectSize % recordToGet;
		 vectSize = vectSize + (recordToGet - mdl);
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
