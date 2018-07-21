/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class PstKPI_Company_Target extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_KPI_COMPANY_TARGET = "hr_kpi_company_target";
   public static final int FLD_KPI_COMPANY_TARGET_ID = 0;
   public static final int FLD_KPI_LIST_ID = 1;
   public static final int FLD_STARTDATE = 2;
   public static final int FLD_ENDDATE = 3;
   public static final int FLD_COMPANY_ID = 4;
   public static final int FLD_TARGET = 5;
   public static final int FLD_ACHIEVEMENT = 6;
   public static final int FLD_COMPETITOR_ID = 7;
   public static final int FLD_COMPETITOR_VALUE = 8;
   
    public static final String[] fieldNames = {
      "KPI_COMPANY_TARGET_ID",
      "KPI_LIST_ID",
      "STARTDATE",
      "ENDDATE",
      "COMPANY_ID",
      "TARGET",
      "ACHIEVEMENT",
      "COMPETITOR_ID",
      "COMPETITOR_VALUE"
    };
    public static final int[] fieldTypes = {
      TYPE_LONG + TYPE_PK + TYPE_ID,
      TYPE_STRING,
      TYPE_DATE,
      TYPE_DATE,
      TYPE_LONG,
      TYPE_FLOAT,
      TYPE_FLOAT,
      TYPE_LONG,
      TYPE_FLOAT
    };

   public PstKPI_Company_Target() {
   }

    public PstKPI_Company_Target(int i) throws DBException {
        super(new PstKPI_Company_Target());
    }

    public PstKPI_Company_Target(String sOid) throws DBException {
        super(new PstKPI_Company_Target(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstKPI_Company_Target(long lOid) throws DBException {
        super(new PstKPI_Company_Target(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_HR_KPI_COMPANY_TARGET;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstKPI_Company_Target().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        KPI_Company_Target kPI_Company_Target = fetchExc(ent.getOID());
        ent = (Entity) kPI_Company_Target;
        return kPI_Company_Target.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((KPI_Company_Target) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((KPI_Company_Target) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static KPI_Company_Target fetchExc(long oid) throws DBException {
        try {
            KPI_Company_Target kPI_Company_Target = new KPI_Company_Target();
            PstKPI_Company_Target pstKPI_Company_Target = new PstKPI_Company_Target(oid);
            kPI_Company_Target.setOID(oid);

         kPI_Company_Target.setOID(oid);
         kPI_Company_Target.setKpiCompanyTargetId(pstKPI_Company_Target.getlong(FLD_KPI_COMPANY_TARGET_ID));
         kPI_Company_Target.setKpiListId(pstKPI_Company_Target.getlong(FLD_KPI_LIST_ID));
         kPI_Company_Target.setStartDate(pstKPI_Company_Target.getDate(FLD_STARTDATE));
         kPI_Company_Target.setEndDate(pstKPI_Company_Target.getDate(FLD_ENDDATE));
         kPI_Company_Target.setCompanyId(pstKPI_Company_Target.getlong(FLD_COMPANY_ID));
         kPI_Company_Target.setTarget(pstKPI_Company_Target.getdouble(FLD_TARGET));
         kPI_Company_Target.setAchievement(pstKPI_Company_Target.getdouble(FLD_ACHIEVEMENT));
         kPI_Company_Target.setCompetitorId(pstKPI_Company_Target.getLong(FLD_COMPETITOR_ID));
         kPI_Company_Target.setCompetitorValue(pstKPI_Company_Target.getdouble(FLD_COMPETITOR_VALUE));
         
         return kPI_Company_Target;

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Company_Target(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(KPI_Company_Target kPI_Company_Target) throws DBException {
        try {
            PstKPI_Company_Target pstKPI_Company_Target = new PstKPI_Company_Target(0);

            pstKPI_Company_Target.setLong(FLD_KPI_LIST_ID, kPI_Company_Target.getKpiListId());
            pstKPI_Company_Target.setDate(FLD_STARTDATE, kPI_Company_Target.getStartDate());
            pstKPI_Company_Target.setDate(FLD_ENDDATE, kPI_Company_Target.getEndDate());
            pstKPI_Company_Target.setLong(FLD_COMPANY_ID, kPI_Company_Target.getCompanyId());
            pstKPI_Company_Target.setDouble(FLD_TARGET, kPI_Company_Target.getTarget());
            pstKPI_Company_Target.setDouble(FLD_ACHIEVEMENT, kPI_Company_Target.getAchievement());
            pstKPI_Company_Target.setLong(FLD_COMPETITOR_ID, kPI_Company_Target.getCompetitorId());
            pstKPI_Company_Target.setDouble(FLD_COMPETITOR_VALUE, kPI_Company_Target.getCompetitorValue());
          
            pstKPI_Company_Target.insert();
            kPI_Company_Target.setOID(pstKPI_Company_Target.getlong(FLD_KPI_COMPANY_TARGET_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Company_Target(0), DBException.UNKNOWN);
        }
        return kPI_Company_Target.getOID();
    }

    public static long updateExc(KPI_Company_Target kPI_Company_Target) throws DBException {
        try {
            if (kPI_Company_Target.getOID() != 0) {
                PstKPI_Company_Target pstKPI_Company_Target = new PstKPI_Company_Target(kPI_Company_Target.getOID());

            pstKPI_Company_Target.setLong(FLD_KPI_COMPANY_TARGET_ID, kPI_Company_Target.getKpiCompanyTargetId());
            pstKPI_Company_Target.setLong(FLD_KPI_LIST_ID, kPI_Company_Target.getKpiListId());
            pstKPI_Company_Target.setDate(FLD_STARTDATE, kPI_Company_Target.getStartDate());
            pstKPI_Company_Target.setDate(FLD_ENDDATE, kPI_Company_Target.getEndDate());
            pstKPI_Company_Target.setLong(FLD_COMPANY_ID, kPI_Company_Target.getCompanyId());
            pstKPI_Company_Target.setDouble(FLD_TARGET, kPI_Company_Target.getTarget());
            pstKPI_Company_Target.setDouble(FLD_ACHIEVEMENT, kPI_Company_Target.getAchievement());
            pstKPI_Company_Target.setLong(FLD_COMPETITOR_ID, kPI_Company_Target.getCompetitorId());
            pstKPI_Company_Target.setDouble(FLD_COMPETITOR_VALUE, kPI_Company_Target.getCompetitorValue());
            pstKPI_Company_Target.update();

                pstKPI_Company_Target.update();
                return kPI_Company_Target.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Company_Target(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstKPI_Company_Target pstKPI_Company_Target = new PstKPI_Company_Target(oid);
            pstKPI_Company_Target.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Company_Target(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_KPI_COMPANY_TARGET;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                KPI_Company_Target kPI_Company_Target = new KPI_Company_Target();
                resultToObject(rs, kPI_Company_Target);
                lists.add(kPI_Company_Target);
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
   
    
     public static Vector listAlldatawithKPI(int tahun, long kpiListId, long CompanyId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
      
        try {
            String sql = " SELECT DISTINCT `kpil`.`KPI_LIST_ID`, `kpil`.`KPI_TITLE` ,`kpil`.`DESCRIPTION` , ";
                    for (int i=1; i <= 12; i++ ){
                       if (i > 9){
                        if (i==12){
                            sql= sql + "(SELECT k.TARGET FROM hr_kpi_company_target k where k.STARTDATE >= \"" +tahun+ "-"+i+"-01 00:00:00\" AND k.ENDDATE <= \""+tahun+"-"+i+"-31 23:59:59\" AND k.`KPI_LIST_ID` = "+kpiListId+" AND k.`COMPANY_ID` = "+CompanyId+" LIMIT 0,1) AS \" bulan "+tahun+"\", ";
                        } else {
                            sql= sql + "(SELECT k.TARGET FROM hr_kpi_company_target k where k.STARTDATE >= \"" +tahun+ "-"+i+"-01 00:00:00\" AND k.ENDDATE <= \""+tahun+"-"+i+"-31 23:59:59\" AND k.`KPI_LIST_ID` = "+kpiListId+" AND k.`COMPANY_ID` = "+CompanyId+" LIMIT 0,1) AS \" bulan "+tahun+"\", ";
                        }
                       } else{
                           if (i==12){
                            sql= sql + "(SELECT k.TARGET FROM hr_kpi_company_target k where k.STARTDATE >= \"" +tahun+ "-0"+i+"-01 00:00:00\" AND k.ENDDATE <= \""+tahun+"-0"+i+"-31 23:59:59\" AND k.`KPI_LIST_ID` = "+kpiListId+" AND k.`COMPANY_ID` = "+CompanyId+" LIMIT 0,1) AS \" bulan "+tahun+"\", ";
                        } else {
                            sql= sql + "(SELECT k.TARGET FROM hr_kpi_company_target k where k.STARTDATE >= \"" +tahun+ "-0"+i+"-01 00:00:00\" AND k.ENDDATE <= \""+tahun+"-0"+i+"-31 23:59:59\" AND k.`KPI_LIST_ID` = "+kpiListId+" AND k.`COMPANY_ID` = "+CompanyId+" LIMIT 0,1) AS \" bulan "+tahun+"\", ";
                        }
                       }
                       
                   }
                    for (int i=1; i <= 12; i++ ){
                       if (i > 9){
                        if (i==12){
                            sql= sql + "(SELECT k.ACHIEVEMENT FROM hr_kpi_company_target k where k.STARTDATE >= \"" +tahun+ "-"+i+"-01 00:00:00\" AND k.ENDDATE <= \""+tahun+"-"+i+"-31 23:59:59\" AND k.`KPI_LIST_ID` = "+kpiListId+" AND k.`COMPANY_ID` = "+CompanyId+" LIMIT 0,1) AS \" bulan "+tahun+"\" ";
                        } else {
                            sql= sql + "(SELECT k.ACHIEVEMENT FROM hr_kpi_company_target k where k.STARTDATE >= \"" +tahun+ "-"+i+"-01 00:00:00\" AND k.ENDDATE <= \""+tahun+"-"+i+"-31 23:59:59\" AND k.`KPI_LIST_ID` = "+kpiListId+" AND k.`COMPANY_ID` = "+CompanyId+" LIMIT 0,1) AS \" bulan "+tahun+"\", ";
                        }
                       } else{
                           if (i==12){
                            sql= sql + "(SELECT k.ACHIEVEMENT FROM hr_kpi_company_target k where k.STARTDATE >= \"" +tahun+ "-0"+i+"-01 00:00:00\" AND k.ENDDATE <= \""+tahun+"-0"+i+"-31 23:59:59\" AND k.`KPI_LIST_ID` = "+kpiListId+" AND k.`COMPANY_ID` = "+CompanyId+" LIMIT 0,1) AS \" bulan "+tahun+"\" ";
                        } else {
                            sql= sql + "(SELECT k.ACHIEVEMENT FROM hr_kpi_company_target k where k.STARTDATE >= \"" +tahun+ "-0"+i+"-01 00:00:00\" AND k.ENDDATE <= \""+tahun+"-0"+i+"-31 23:59:59\" AND k.`KPI_LIST_ID` = "+kpiListId+" AND k.`COMPANY_ID` = "+CompanyId+" LIMIT 0,1) AS \" bulan "+tahun+"\", ";
                        }
                       }
                       
                   }
                   sql = sql + " FROM " + PstKPI_Company_Target.TBL_HR_KPI_COMPANY_TARGET + " k  INNER JOIN `hr_kpi_list` kpil ON `kpil`.`KPI_LIST_ID` = "+kpiListId+" ";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
              // int q = rs.getDouble(PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.])
                String vals = rs.getString(1);
                lists.add(vals);
                String vals2 = rs.getString(2);
                lists.add(vals2);
                String vals3 = rs.getString(3);
                lists.add(vals3);
                for (int i=4; i<=15;i++){
                      Double val = rs.getDouble(i);
                      lists.add(val);
                }
                for (int i=16; i<=27;i++){
                      Double val = rs.getDouble(i);
                      lists.add(val);
                }
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
    
    public static Vector listAlldata(int tahun, long kpiListId, long CompanyId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        
//        SELECT DISTINCT
//(SELECT k.`TARGET` FROM hr_kpi_company_target k WHERE STARTDATE >= "2015-01-01 00:00:00" AND ENDDATE <= "2015-01-31 00:00:00") AS "JANUARI 2015",
//(SELECT k.`TARGET` FROM hr_kpi_company_target k WHERE STARTDATE >= "2015-02-01 00:00:00" AND ENDDATE <= "2015-02-31 00:00:00") AS "FEBRUARI 2015",
//(SELECT k.`TARGET` FROM hr_kpi_company_target k WHERE STARTDATE >= "2015-03-01 00:00:00" AND ENDDATE <= "2015-03-31 00:00:00") AS "MARET 2015",
//(SELECT k.`TARGET` FROM hr_kpi_company_target k WHERE STARTDATE >= "2015-04-01 00:00:00" AND ENDDATE <= "2015-04-31 00:00:00") AS "APRIL 2015",
//(SELECT k.`TARGET` FROM hr_kpi_company_target k WHERE STARTDATE >= "2015-05-01 00:00:00" AND ENDDATE <= "2015-05-31 00:00:00") AS "MEI 2015"
//FROM `hr_kpi_company_target` k;

        try {
            String sql = " SELECT DISTINCT ";
                    for (int i=1; i <= 12; i++ ){
                       if (i > 9){
                        if (i==12){
                            sql= sql + "(SELECT k.TARGET FROM hr_kpi_company_target k where k.STARTDATE >= \"" +tahun+ "-"+i+"-01 00:00:00\" AND k.ENDDATE <= \""+tahun+"-"+i+"-31 23:59:59\" AND k.`KPI_LIST_ID` = "+kpiListId+" AND k.`COMPANY_ID` = "+CompanyId+" LIMIT 0,1) AS \" bulan "+tahun+"\" ";
                        } else {
                            sql= sql + "(SELECT k.TARGET FROM hr_kpi_company_target k where k.STARTDATE >= \"" +tahun+ "-"+i+"-01 00:00:00\" AND k.ENDDATE <= \""+tahun+"-"+i+"-31 23:59:59\" AND k.`KPI_LIST_ID` = "+kpiListId+" AND k.`COMPANY_ID` = "+CompanyId+" LIMIT 0,1) AS \" bulan "+tahun+"\", ";
                        }
                       } else{
                           if (i==12){
                            sql= sql + "(SELECT k.TARGET FROM hr_kpi_company_target k where k.STARTDATE >= \"" +tahun+ "-0"+i+"-01 00:00:00\" AND k.ENDDATE <= \""+tahun+"-0"+i+"-31 23:59:59\" AND k.`KPI_LIST_ID` = "+kpiListId+" AND k.`COMPANY_ID` = "+CompanyId+" LIMIT 0,1) AS \" bulan "+tahun+"\" ";
                        } else {
                            sql= sql + "(SELECT k.TARGET FROM hr_kpi_company_target k where k.STARTDATE >= \"" +tahun+ "-0"+i+"-01 00:00:00\" AND k.ENDDATE <= \""+tahun+"-0"+i+"-31 23:59:59\" AND k.`KPI_LIST_ID` = "+kpiListId+" AND k.`COMPANY_ID` = "+CompanyId+" LIMIT 0,1) AS \" bulan "+tahun+"\", ";
                        }
                       }
                       
                   }
                   sql = sql + " FROM " + PstKPI_Company_Target.TBL_HR_KPI_COMPANY_TARGET + " k ";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
              // int q = rs.getDouble(PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.])
                for (int i=1; i<=12;i++){
                      Double val = rs.getDouble(i);
                      lists.add(val);
                }
              
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

    public static Vector listAlldataNew(int tahun, long kpiListId, long CompanyId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        
//        SELECT DISTINCT
//(SELECT k.`TARGET` FROM hr_kpi_company_target k WHERE STARTDATE >= "2015-01-01 00:00:00" AND ENDDATE <= "2015-01-31 00:00:00") AS "JANUARI 2015",
//(SELECT k.`TARGET` FROM hr_kpi_company_target k WHERE STARTDATE >= "2015-02-01 00:00:00" AND ENDDATE <= "2015-02-31 00:00:00") AS "FEBRUARI 2015",
//(SELECT k.`TARGET` FROM hr_kpi_company_target k WHERE STARTDATE >= "2015-03-01 00:00:00" AND ENDDATE <= "2015-03-31 00:00:00") AS "MARET 2015",
//(SELECT k.`TARGET` FROM hr_kpi_company_target k WHERE STARTDATE >= "2015-04-01 00:00:00" AND ENDDATE <= "2015-04-31 00:00:00") AS "APRIL 2015",
//(SELECT k.`TARGET` FROM hr_kpi_company_target k WHERE STARTDATE >= "2015-05-01 00:00:00" AND ENDDATE <= "2015-05-31 00:00:00") AS "MEI 2015"
//FROM `hr_kpi_company_target` k;

        try {
            String sql = " SELECT * FROM " + PstKPI_Company_Target.TBL_HR_KPI_COMPANY_TARGET + " WHERE KPI_LIST_ID = "+kpiListId+" AND "
            
                    + PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_STARTDATE] + " LIKE \"%" + tahun + "%\" AND "
                    + PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_ENDDATE] + " LIKE \"%" + tahun + "%\" AND "
                    + PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_COMPANY_ID] + " = " + CompanyId + " ";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
              KPI_Company_Target kPI_Company_Target = new KPI_Company_Target();
              resultToObject(rs, kPI_Company_Target);
              lists.add(kPI_Company_Target);
              
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
    
    
        public static Vector listAllKPIdata(int tahun, Vector kpiV, long CompanyId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
       
        try {

            for (int i = 0; i<kpiV.size(); i++){
                KPI_List kPI_List = (KPI_List) kpiV.get(i);
                long kpiId = kPI_List.getOID(); 
                Vector listKPI_Company_Target = PstKPI_Company_Target.listAlldatawithKPI(tahun, kpiId, CompanyId);
                lists.add(listKPI_Company_Target);
            }
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    
    public static Vector listAlldataEmployee(int tahun, long kpiListId, long CompanyId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        

        try {
            String sql = " SELECT divi."+ PstDivision.fieldNames[PstDivision.FLD_DIVISION] + " , " +
                         " dep."+ PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] + " , " +
                         " s."+ PstSection.fieldNames[PstSection.FLD_SECTION] + " , " +
                         " e."+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " , "+
                         " e."+ PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " , "+
                         " e."+ PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " , "+
                         " e."+ PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " , "+
                         " e."+ PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " , "+
                         " e."+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " , " +
                         " kpie."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_TARGET] + " , "+
                         " kpie."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_ACHIEVEMENT] + "  ";
              sql = sql +" FROM " + PstEmployee.TBL_HR_EMPLOYEE + " e " +
                         " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " divi ON divi." +PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+ " = e." +PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + 
                         " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " dep ON dep." +PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+ " = e." +PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                         " LEFT JOIN " + PstKPI_Employee_Target.TBL_HR_KPI_EMPLOYEE_TARGET + " kpie ON (kpie." +PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_EMPLOYEE_ID]+ " = e." +PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +" AND kpie."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_KPI_LIST_ID] + " = " + kpiListId + " ) "+
                         " LEFT JOIN " + PstSection.TBL_HR_SECTION + " s ON s." +PstSection.fieldNames[PstSection.FLD_SECTION_ID]+ " = e." +PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] ; 
              sql = sql + " WHERE e." +PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = " + CompanyId  + " AND e."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + 0 +"  ORDER BY divi.`DIVISION`,dep.`DEPARTMENT`,s.`SECTION`,e.`FULL_NAME` ";  
            
              dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            Vector vtotal = PstKPI_Company_Target.listAlldataNew(tahun, kpiListId, CompanyId);
            double total = 0;
                
                for (int ik=0; ik<vtotal.size();ik++){
                    KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) vtotal.get(ik); 
                   total = total + ( kPI_Company_Target.getTarget() > 0 ? ((Double)kPI_Company_Target.getTarget()).doubleValue() : 0 );
                }
            while (rs.next()) {
              KPI_ListAllDataEmp kPI_ListAllDataEmp = new KPI_ListAllDataEmp();
                kPI_ListAllDataEmp.setDivision(rs.getString("divi."+ PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                kPI_ListAllDataEmp.setDepartement(rs.getString("dep."+ PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                kPI_ListAllDataEmp.setSection(rs.getString("s."+ PstSection.fieldNames[PstSection.FLD_SECTION]) != null? rs.getString("s."+ PstSection.fieldNames[PstSection.FLD_SECTION]) : ""  ); 
                kPI_ListAllDataEmp.setEmployeeId(rs.getLong("e."+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                kPI_ListAllDataEmp.setDivisionId(rs.getLong("e."+ PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID])); 
                kPI_ListAllDataEmp.setDepartmentId(rs.getLong("e."+ PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID])); 
                kPI_ListAllDataEmp.setSectionId(rs.getLong("e."+ PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID])); 
                kPI_ListAllDataEmp.setFullname(rs.getString("e."+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME])); 
                double target = rs.getDouble("kpie."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_TARGET]);
                
                
                double percent = target/(total/100);
                if (percent < 1 || percent == 0){
                    percent=0;
                }
                kPI_ListAllDataEmp.setPercentOfTarget(percent);
                kPI_ListAllDataEmp.setTarget(rs.getDouble("kpie."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_TARGET])); 
                kPI_ListAllDataEmp.setAchievement(rs.getDouble("kpie."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_ACHIEVEMENT])); 
               lists.add(kPI_ListAllDataEmp);
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

    
    public static Vector listAlldataEmployeeView(int tahun, long kpiListId, long CompanyId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        

        try {
            String sql = " SELECT divi."+ PstDivision.fieldNames[PstDivision.FLD_DIVISION] + " , " +
                         " dep."+ PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] + " , " +
                         " s."+ PstSection.fieldNames[PstSection.FLD_SECTION] + " , " +
                         " e."+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " , "+
                         " e."+ PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " , "+
                         " e."+ PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " , "+
                         " e."+ PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " , "+
                         " e."+ PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " , "+
                         " e."+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " , " +
                         " kpie."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_TARGET] + " , "+
                         " kpie."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_ACHIEVEMENT] + "  ";
              sql = sql +" FROM " + PstEmployee.TBL_HR_EMPLOYEE + " e " +
                         " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " divi ON divi." +PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+ " = e." +PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + 
                         " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " dep ON dep." +PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+ " = e." +PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                         " INNER JOIN " + PstKPI_Employee_Target.TBL_HR_KPI_EMPLOYEE_TARGET + " kpie ON (kpie." +PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_EMPLOYEE_ID]+ " = e." +PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +" AND kpie."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_KPI_LIST_ID] + " = " + kpiListId + " ) "+
                         " LEFT JOIN " + PstSection.TBL_HR_SECTION + " s ON s." +PstSection.fieldNames[PstSection.FLD_SECTION_ID]+ " = e." +PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] ; 
              sql = sql + " WHERE e." +PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = " + CompanyId  + " AND e."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + 0 +"  ORDER BY divi.`DIVISION`,dep.`DEPARTMENT`,s.`SECTION`,e.`FULL_NAME` ";  
            
              dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            Vector vtotal = PstKPI_Company_Target.listAlldataNew(tahun, kpiListId, CompanyId);
            double total = 0;
                
                for (int ik=0; ik<vtotal.size();ik++){
                    KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) vtotal.get(ik); 
                   total = total + ( kPI_Company_Target.getTarget() > 0 ? ((Double)kPI_Company_Target.getTarget()).doubleValue() : 0 );
                }
            while (rs.next()) {
              KPI_ListAllDataEmp kPI_ListAllDataEmp = new KPI_ListAllDataEmp();
                kPI_ListAllDataEmp.setDivision(rs.getString("divi."+ PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                kPI_ListAllDataEmp.setDepartement(rs.getString("dep."+ PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                kPI_ListAllDataEmp.setSection(rs.getString("s."+ PstSection.fieldNames[PstSection.FLD_SECTION]) != null? rs.getString("s."+ PstSection.fieldNames[PstSection.FLD_SECTION]) : ""  ); 
                kPI_ListAllDataEmp.setEmployeeId(rs.getLong("e."+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                kPI_ListAllDataEmp.setDivisionId(rs.getLong("e."+ PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID])); 
                kPI_ListAllDataEmp.setDepartmentId(rs.getLong("e."+ PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID])); 
                kPI_ListAllDataEmp.setSectionId(rs.getLong("e."+ PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID])); 
                kPI_ListAllDataEmp.setFullname(rs.getString("e."+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME])); 
                double target = rs.getDouble("kpie."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_TARGET]);
                
                
                double percent = target/(total/100);
                if (percent < 1 || percent == 0){
                    percent=0;
                }
                kPI_ListAllDataEmp.setPercentOfTarget(percent);
                kPI_ListAllDataEmp.setTarget(rs.getDouble("kpie."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_TARGET])); 
                kPI_ListAllDataEmp.setAchievement(rs.getDouble("kpie."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_ACHIEVEMENT])); 
               lists.add(kPI_ListAllDataEmp);
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
    public static double CountAchievedataEmployee(int tahun, long kpiListId, long CompanyId) {
        double cAchiev = 0;
        DBResultSet dbrs = null;
        

        try {
            String sql = " SELECT COUNT("+
                         " kpie."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_ACHIEVEMENT] + ")  ";
              sql = sql +" FROM " + PstEmployee.TBL_HR_EMPLOYEE + " e " +
                         " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " divi ON divi." +PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+ " = e." +PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + 
                         " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " dep ON dep." +PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+ " = e." +PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                         " LEFT JOIN " + PstKPI_Employee_Target.TBL_HR_KPI_EMPLOYEE_TARGET + " kpie ON kpie." +PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_EMPLOYEE_ID]+ " = e." +PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                         " LEFT JOIN " + PstSection.TBL_HR_SECTION + " s ON s." +PstSection.fieldNames[PstSection.FLD_SECTION_ID]+ " = e." +PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] ; 
              sql =sql + " WHERE e." +PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = " + CompanyId + " ORDER BY divi.`DIVISION`,dep.`DEPARTMENT`,s.`SECTION`,e.`FULL_NAME` ";  
            
              dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
              
                cAchiev = rs.getDouble(1);
                
            }
            rs.close();
            return cAchiev;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }
    
      public static void resultToObject(ResultSet rs, KPI_Company_Target kPI_Company_Target) {
        try {
            kPI_Company_Target.setKpiCompanyTargetId(rs.getLong(PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_KPI_COMPANY_TARGET_ID]));
            kPI_Company_Target.setKpiListId(rs.getLong(PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_KPI_LIST_ID]));
            kPI_Company_Target.setStartDate(rs.getDate(PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_STARTDATE]));
            kPI_Company_Target.setEndDate(rs.getDate(PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_ENDDATE]));
            kPI_Company_Target.setCompanyId(rs.getLong(PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_COMPANY_ID]));
            kPI_Company_Target.setTarget(rs.getDouble(PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_TARGET]));
            kPI_Company_Target.setAchievement(rs.getDouble(PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_ACHIEVEMENT]));
            kPI_Company_Target.setCompetitorId(rs.getLong(PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_COMPETITOR_ID]));
            kPI_Company_Target.setCompetitorValue(rs.getDouble(PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_COMPETITOR_VALUE]));
            
        } catch (Exception e) {
        }
    }
    
      public static long deletewhereYear(int year, long kpiListId, long companyId) {
        DBResultSet dbrs = null;
        long resulthasil =0;
        try {
            String sql = "DELETE  FROM " + TBL_HR_KPI_COMPANY_TARGET + " WHERE "
                    + PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_STARTDATE] + " LIKE \"%" + year + "%\" AND "
                    + PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_ENDDATE] + " LIKE \"%" + year + "%\" AND "
                    + PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_KPI_LIST_ID] + " = " + kpiListId + " AND "
                    + PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_COMPANY_ID] + " = " + companyId;
            
            DBHandler.execSqlInsert(sql);
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
            
        } finally {
            DBResultSet.close(dbrs);
            return resulthasil;
        }
    }
      
      
    
      
    public static boolean checkOID(long kPI_Company_TargetId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_KPI_COMPANY_TARGET + " WHERE "
                    + PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_KPI_COMPANY_TARGET_ID] + " = " + kPI_Company_TargetId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstKPI_Company_Target.fieldNames[PstKPI_Company_Target.FLD_KPI_COMPANY_TARGET_ID] + ") FROM " + TBL_HR_KPI_COMPANY_TARGET;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }


    /* This method used to find current data */
    public static int findLimitStart(long oid, int recordToGet, String whereClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, order);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) list.get(ls);
                    if (oid == kPI_Company_Target.getOID()) {
                        found = true;
                    }
                }
            }
        }
        if ((start >= size) && (size > 0)) {
            start = start - recordToGet;
        }

        return start;
    }

  
}
