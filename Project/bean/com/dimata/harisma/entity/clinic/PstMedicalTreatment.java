
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

public class PstMedicalTreatment extends DBHandler implements I_DBType, I_DBInterface, I_PersintentExc, I_Language {

    public static final  String TBL_HR_MEDICAL_TREATMENT    = "hr_medical_treatment";

    public static final  int FLD_MEDICAL_TREATMENT_ID       = 0;
    public static final  int FLD_CASE_GROUP_ID              = 1;
    public static final  int FLD_CASE_NAME                  = 2;
    public static final  int FLD_MAX_OCCUR                  = 3;
    public static final  int FLD_OCCUR_PERIOD               = 4;
    public static final  int FLD_MAX_BUDGET                 = 5;
    public static final  int FLD_BUDGET_PERIOD              = 6;
    public static final  int FLD_BUDGET_TARGET              = 7;

    public static String[] fieldNames = {
            "MEDICAL_TREATMENT_ID",
            "CASE_GROUP_ID",
            "CASE_NAME",
            "MAX_OCCUR",
            "OCCUR_PERIOD",
            "MAX_BUDGET",
            "BUDGET_PERIOD",
            "BUDGET_TARGET",           
    }; 

    public static int[] fieldTypes = {
            TYPE_LONG + TYPE_PK + TYPE_ID,
            TYPE_LONG,
            TYPE_STRING,
            TYPE_INT,
            TYPE_INT,
            TYPE_FLOAT,
            TYPE_INT,
            TYPE_INT
    }; 
    
    
    public static final int PERIOD_TIMES = 0;
    public static final int PERIOD_DAY = 1;
    public static final int PERIOD_MONTH = 2;
    public static final int PERIOD_YEAR = 3;
    
    public static String[] periodNames = {
            "Times",
            "Day",
            "Month",
            "Year"
    };
    
    public static final int TARGET_PAX = 0;
    public static final int TARGET_EMP = 1;
    
    public static String[] targetNames = {
            "Pax",
            "Employee"
    };
    

    public PstMedicalTreatment(){
    }

    public PstMedicalTreatment(int i) throws DBException { 
            super(new PstMedicalTreatment()); 
    }

    public PstMedicalTreatment(String sOid) throws DBException { 
            super(new PstMedicalTreatment(0)); 
            if(!locate(sOid)) 
                    throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            else 
                    return; 
    }

    public PstMedicalTreatment(long lOid) throws DBException { 
            super(new PstMedicalTreatment(0)); 
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
            return TBL_HR_MEDICAL_TREATMENT ;
    }

    public String[] getFieldNames(){ 
            return fieldNames; 
    }

    public int[] getFieldTypes(){ 
            return fieldTypes; 
    }
    
    public String getPersistentName(){ 
            return new PstMedicalTreatment().getClass().getName(); 
    }
    

    public long fetchExc(Entity ent) throws Exception{ 
            MedicalTreatment medicalTreatment = fetchExc(ent.getOID()); 
            ent = (Entity)medicalTreatment; 
            return medicalTreatment.getOID(); 
    }

    public long insertExc(Entity ent) throws Exception{ 
            return insertExc((MedicalTreatment) ent); 
    }

    public long updateExc(Entity ent) throws Exception{ 
            return updateExc((MedicalTreatment) ent); 
    }

    public long deleteExc(Entity ent) throws Exception{ 
            if(ent==null){ 
                    throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            } 
            return deleteExc(ent.getOID()); 
    }

    public static MedicalTreatment fetchExc(long oid) throws DBException{ 
            try{ 
                    MedicalTreatment medicalTreatment = new MedicalTreatment();
                    PstMedicalTreatment pstMedicalTreatment = new PstMedicalTreatment(oid); 
                    medicalTreatment.setOID(oid);

                    medicalTreatment.setCaseGroupId(pstMedicalTreatment.getlong(FLD_CASE_GROUP_ID));
                    medicalTreatment.setCaseName(pstMedicalTreatment.getString(FLD_CASE_NAME));   
                    medicalTreatment.setMaxOccurance(pstMedicalTreatment.getInt(FLD_MAX_OCCUR));
                    medicalTreatment.setOccurancePeriod(pstMedicalTreatment.getInt(FLD_OCCUR_PERIOD));
                    medicalTreatment.setMaxBudget(pstMedicalTreatment.getdouble(FLD_MAX_BUDGET));
                    medicalTreatment.setBudgetPeriod(pstMedicalTreatment.getInt(FLD_BUDGET_PERIOD));
                    medicalTreatment.setBudgetTarget(pstMedicalTreatment.getInt(FLD_BUDGET_TARGET));
                   

                    return medicalTreatment; 
            }
            catch(DBException dbe){ 
                    throw dbe; 
            }
            catch(Exception e){ 
                    throw new DBException(new PstMedicalTreatment(0),DBException.UNKNOWN); 
            } 
    }

    public static long insertExc(MedicalTreatment medicalTreatment) throws DBException{ 
            try{ 
                    PstMedicalTreatment pstMedicalTreatment = new PstMedicalTreatment(0);

                    pstMedicalTreatment.setLong(FLD_CASE_GROUP_ID, medicalTreatment.getCaseGroupId());
                    pstMedicalTreatment.setString(FLD_CASE_NAME, medicalTreatment.getCaseName());
                    pstMedicalTreatment.setInt(FLD_MAX_OCCUR, medicalTreatment.getMaxOccurance());
                    pstMedicalTreatment.setInt(FLD_OCCUR_PERIOD, medicalTreatment.getOccurancePeriod());
                    pstMedicalTreatment.setDouble(FLD_MAX_BUDGET, medicalTreatment.getMaxBudget());
                    pstMedicalTreatment.setInt(FLD_BUDGET_PERIOD, medicalTreatment.getBudgetPeriod());
                    pstMedicalTreatment.setInt(FLD_BUDGET_TARGET, medicalTreatment.getBudgetTarget());                   

                    pstMedicalTreatment.insert(); 
                    medicalTreatment.setOID(pstMedicalTreatment.getlong(FLD_MEDICAL_TREATMENT_ID));
            }
            catch(DBException dbe){ 
                    throw dbe; 
            }
            catch(Exception e){ 
                    throw new DBException(new PstMedicalTreatment(0),DBException.UNKNOWN); 
            }
            return medicalTreatment.getOID();
    }

    public static long updateExc(MedicalTreatment medicalTreatment) throws DBException{ 
            try{ 
                    if(medicalTreatment.getOID() != 0){ 
                            PstMedicalTreatment pstMedicalTreatment = new PstMedicalTreatment(medicalTreatment.getOID());

                            pstMedicalTreatment.setLong(FLD_CASE_GROUP_ID, medicalTreatment.getCaseGroupId());
                            pstMedicalTreatment.setString(FLD_CASE_NAME, medicalTreatment.getCaseName());
                            pstMedicalTreatment.setInt(FLD_MAX_OCCUR, medicalTreatment.getMaxOccurance());
                            pstMedicalTreatment.setInt(FLD_OCCUR_PERIOD, medicalTreatment.getOccurancePeriod());
                            pstMedicalTreatment.setDouble(FLD_MAX_BUDGET, medicalTreatment.getMaxBudget());
                            pstMedicalTreatment.setInt(FLD_BUDGET_PERIOD, medicalTreatment.getBudgetPeriod());
                            pstMedicalTreatment.setInt(FLD_BUDGET_TARGET, medicalTreatment.getBudgetTarget());                  
                            
                            pstMedicalTreatment.update(); 
                            return medicalTreatment.getOID();
                    }
            }
            catch(DBException dbe){ 
                    throw dbe; 
            }
            catch(Exception e){ 
                    throw new DBException(new PstMedicalTreatment(0),DBException.UNKNOWN); 
            }
            return 0;
    }

    public static long deleteExc(long oid) throws DBException{ 
            try{ 
                    PstMedicalTreatment pstMedicalTreatment = new PstMedicalTreatment(oid);
                    pstMedicalTreatment.delete();
            }
            catch(DBException dbe){ 
                    throw dbe; 
            }
            catch(Exception e){ 
                    throw new DBException(new PstMedicalTreatment(0),DBException.UNKNOWN); 
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
                    String sql = "SELECT * FROM " + TBL_HR_MEDICAL_TREATMENT ; 
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
                            MedicalTreatment medicalTreatment = new MedicalTreatment();
                            resultToObject(rs, medicalTreatment);
                            lists.add(medicalTreatment);
                    }
                    rs.close();
                    return lists;

            }
            catch(Exception e) {
                    System.out.println(e);
            }
            finally {
                    DBResultSet.close(dbrs);
            }
            
            return new Vector();
    }

    private static void resultToObject(ResultSet rs, MedicalTreatment medicalTreatment){
            try{
                    medicalTreatment.setOID(rs.getLong(PstMedicalTreatment.fieldNames[PstMedicalTreatment.FLD_MEDICAL_TREATMENT_ID]));
                    medicalTreatment.setCaseGroupId(rs.getLong(PstMedicalTreatment.fieldNames[PstMedicalTreatment.FLD_CASE_GROUP_ID]));
                    medicalTreatment.setCaseName(rs.getString(PstMedicalTreatment.fieldNames[PstMedicalTreatment.FLD_CASE_NAME]));
                    medicalTreatment.setMaxOccurance(rs.getInt(PstMedicalTreatment.fieldNames[PstMedicalTreatment.FLD_MAX_OCCUR]));
                    medicalTreatment.setOccurancePeriod(rs.getInt(PstMedicalTreatment.fieldNames[PstMedicalTreatment.FLD_OCCUR_PERIOD]));
                    medicalTreatment.setMaxBudget(rs.getDouble(PstMedicalTreatment.fieldNames[PstMedicalTreatment.FLD_MAX_BUDGET]));
                    medicalTreatment.setBudgetPeriod(rs.getInt(PstMedicalTreatment.fieldNames[PstMedicalTreatment.FLD_BUDGET_PERIOD]));
                    medicalTreatment.setBudgetTarget(rs.getInt(PstMedicalTreatment.fieldNames[PstMedicalTreatment.FLD_BUDGET_TARGET]));
                   
            }catch(Exception e){ }
    }

    public static boolean checkOID(long medicalRecordId){
            DBResultSet dbrs = null;
            boolean result = false;
            try{
                    String sql = "SELECT * FROM " + TBL_HR_MEDICAL_TREATMENT  + " WHERE " + 
                                            PstMedicalTreatment.fieldNames[PstMedicalTreatment.FLD_MEDICAL_TREATMENT_ID] + " = " + medicalRecordId;

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
                    String sql = "SELECT COUNT("+ PstMedicalTreatment.fieldNames[PstMedicalTreatment.FLD_MEDICAL_TREATMENT_ID] + ") FROM " + TBL_HR_MEDICAL_TREATMENT ;
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
                               MedicalTreatment medicalTreatment = (MedicalTreatment)list.get(ls);
                               if(oid == medicalTreatment.getOID())
                                      found=true;
                      }
              }
            }
            if((start >= size) && (size > 0))
                start = start - recordToGet;

            return start;
    }

}
