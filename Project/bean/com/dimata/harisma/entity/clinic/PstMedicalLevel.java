
package com.dimata.harisma.entity.clinic;

/* package java */ 
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.*;

/**
 *
 * @author bayu
 */

public class PstMedicalLevel extends DBHandler implements I_DBType, I_DBInterface, I_PersintentExc, I_Language {

    public static final  String TBL_HR_MEDICAL_LEVEL = "hr_medical_level";

    public static final  int FLD_MEDICAL_LEVEL_ID = 0;
    public static final  int FLD_MEDICAL_LEVEL_NAME = 1;
    public static final  int FLD_MEDICAL_LEVEL_CLASS = 2;
    public static final  int FLD_SORT_NUMBER = 3;
   
    public static String[] fieldNames = {
            "MEDICAL_LEVEL_ID",
            "MEDICAL_LEVEL_NAME",
            "MEDICAL_LEVEL_CLASS",
            "SORT_NUMBER"
     }; 

    public static int[] fieldTypes = {
            TYPE_LONG + TYPE_PK + TYPE_ID,
            TYPE_STRING,
            TYPE_STRING,
            TYPE_INT
     }; 

    
    public PstMedicalLevel(){
    }

    public PstMedicalLevel(int i) throws DBException { 
            super(new PstMedicalLevel()); 
    }

    public PstMedicalLevel(String sOid) throws DBException { 
            super(new PstMedicalLevel(0)); 
            if(!locate(sOid)) 
                    throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            else 
                    return; 
    }

    public PstMedicalLevel(long lOid) throws DBException { 
            super(new PstMedicalLevel(0)); 
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

    public int getFieldSize(){ 
            return fieldNames.length; 
    }

    public String getTableName(){ 
            return TBL_HR_MEDICAL_LEVEL;
    }

    public String[] getFieldNames(){ 
            return fieldNames; 
    }

    public int[] getFieldTypes(){ 
            return fieldTypes; 
    }

    public String getPersistentName(){ 
            return new PstMedicalLevel().getClass().getName(); 
    }

    
    public long fetchExc(Entity ent) throws Exception{ 
            MedicalLevel medicalLevel = fetchExc(ent.getOID()); 
            ent = (Entity)medicalLevel; 
            return medicalLevel.getOID(); 
    }

    public long insertExc(Entity ent) throws Exception{ 
            return insertExc((MedicalLevel) ent); 
    }

    public long updateExc(Entity ent) throws Exception{ 
            return updateExc((MedicalLevel) ent); 
    }

    public long deleteExc(Entity ent) throws Exception{ 
            if(ent==null){ 
                    throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            } 
            return deleteExc(ent.getOID()); 
    }
    

    public static MedicalLevel fetchExc(long oid) throws DBException{ 
            try{ 
                    MedicalLevel medicalLevel = new MedicalLevel();
                    PstMedicalLevel pstMedicalLevel = new PstMedicalLevel(oid); 
                    medicalLevel.setOID(oid);                  
                    medicalLevel.setLevelName(pstMedicalLevel.getString(FLD_MEDICAL_LEVEL_NAME));
                    medicalLevel.setLevelClass(pstMedicalLevel.getString(FLD_MEDICAL_LEVEL_CLASS));
                    medicalLevel.setSortNumber(pstMedicalLevel.getInt(FLD_SORT_NUMBER));
                    return medicalLevel; 
            }
            catch(DBException dbe){ 
                    throw dbe; 
            }
            catch(Exception e){ 
                    throw new DBException(new PstMedicalLevel(0),DBException.UNKNOWN); 
            } 
    }

    public static long insertExc(MedicalLevel medicalLevel) throws DBException{ 
            try{ 
                    PstMedicalLevel pstMedicalLevel = new PstMedicalLevel(0);

                    pstMedicalLevel.setString(FLD_MEDICAL_LEVEL_NAME, medicalLevel.getLevelName());
                    pstMedicalLevel.setString(FLD_MEDICAL_LEVEL_CLASS, medicalLevel.getLevelClass());
                    pstMedicalLevel.setInt(FLD_SORT_NUMBER,medicalLevel.getSortNumber());
                    pstMedicalLevel.insert(); 
                    medicalLevel.setOID(pstMedicalLevel.getlong(FLD_MEDICAL_LEVEL_ID));
            }
            catch(DBException dbe){ 
                    throw dbe; 
            }
            catch(Exception e){ 
                    throw new DBException(new PstMedicalLevel(0),DBException.UNKNOWN); 
            }
            return medicalLevel.getOID();
    }

    public static long updateExc(MedicalLevel medicalLevel) throws DBException{ 
            try{ 
                    if(medicalLevel.getOID() != 0){ 
                            PstMedicalLevel pstMedicalLevel = new PstMedicalLevel(medicalLevel.getOID());

                            pstMedicalLevel.setString(FLD_MEDICAL_LEVEL_NAME, medicalLevel.getLevelName());
                            pstMedicalLevel.setString(FLD_MEDICAL_LEVEL_CLASS, medicalLevel.getLevelClass());
                            pstMedicalLevel.setInt(FLD_SORT_NUMBER, medicalLevel.getSortNumber());
                            pstMedicalLevel.update(); 
                            return medicalLevel.getOID();

                    }
            }
            catch(DBException dbe){ 
                    throw dbe; 
            }
            catch(Exception e){ 
                    throw new DBException(new PstMedicalLevel(0),DBException.UNKNOWN); 
            }
            return 0;
    }

    public static long deleteExc(long oid) throws DBException{ 
            try{ 
                    PstMedicalLevel pstMedicalLevel = new PstMedicalLevel(oid);
                    pstMedicalLevel.delete();
            }
            catch(DBException dbe){ 
                    throw dbe; 
            }
            catch(Exception e){ 
                    throw new DBException(new PstMedicalLevel(0),DBException.UNKNOWN); 
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
                    String sql = "SELECT * FROM " + TBL_HR_MEDICAL_LEVEL; 
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
                            MedicalLevel medicalLevel = new MedicalLevel();
                            resultToObject(rs, medicalLevel);
                            lists.add(medicalLevel);
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

    private static void resultToObject(ResultSet rs, MedicalLevel medicalLevel){
            try{
                    medicalLevel.setOID(rs.getLong(PstMedicalLevel.fieldNames[PstMedicalLevel.FLD_MEDICAL_LEVEL_ID]));
                    medicalLevel.setLevelName(rs.getString(PstMedicalLevel.fieldNames[PstMedicalLevel.FLD_MEDICAL_LEVEL_NAME]));
                    medicalLevel.setLevelClass(rs.getString(PstMedicalLevel.fieldNames[PstMedicalLevel.FLD_MEDICAL_LEVEL_CLASS]));
                    medicalLevel.setSortNumber(rs.getInt(PstMedicalLevel.fieldNames[PstMedicalLevel.FLD_SORT_NUMBER]));
            }catch(Exception e){ }
    }

    public static boolean checkOID(long medicalRecordId){
            DBResultSet dbrs = null;
            boolean result = false;
            try{
                    String sql = "SELECT * FROM " + TBL_HR_MEDICAL_LEVEL + " WHERE " + 
                                  PstMedicalLevel.fieldNames[PstMedicalLevel.FLD_MEDICAL_LEVEL_ID] + " = " + medicalRecordId;

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
                    String sql = "SELECT COUNT("+ PstMedicalLevel.fieldNames[PstMedicalLevel.FLD_MEDICAL_LEVEL_ID] + ") FROM " + TBL_HR_MEDICAL_LEVEL;
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
    public static int findLimitStart( long oid, int recordToGet, String whereClause){
            String order = "";
            int size = getCount(whereClause);
            int start = 0;
            boolean found =false;
            for(int i=0; (i < size) && !found ; i=i+recordToGet){
                     Vector list =  list(i,recordToGet, whereClause, order); 
                     start = i;
                     if(list.size()>0){
                      for(int ls=0;ls<list.size();ls++){ 
                               MedicalLevel medicalLevel = (MedicalLevel)list.get(ls);
                               if(oid == medicalLevel.getOID())
                                      found=true;
                      }
              }
            }
            if((start >= size) && (size > 0))
                start = start - recordToGet;

            return start;
    }
        
   
}
