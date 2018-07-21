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
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class PstKPI_Employee_Achiev extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_KPI_EMPLOYEE_ACHIEV = "hr_kpi_employee_achiev";
   public static final int FLD_KPI_EMPLOYEE_ACHIEV_ID = 0;
   public static final int FLD_KPI_LIST_ID = 1;
   public static final int FLD_STARTDATE = 2;
   public static final int FLD_ENDDATE = 3;
   public static final int FLD_EMPLOYEE_ID = 4;
   public static final int FLD_ENTRYDATE = 5;
   public static final int FLD_ACHIEVEMENT = 6;
   
    public static final String[] fieldNames = {
   "KPI_EMPLOYEE_ACHIEV_ID",
      "KPI_LIST_ID",
      "STARTDATE",
      "ENDDATE",
      "EMPLOYEE_ID",
      "ENTRYDATE",
      "ACHIEVEMENT"
    };
    public static final int[] fieldTypes = {
      TYPE_LONG + TYPE_PK + TYPE_ID,
      TYPE_LONG,
      TYPE_DATE,
      TYPE_DATE,
      TYPE_LONG,
      TYPE_DATE,
      TYPE_FLOAT
    };

   public PstKPI_Employee_Achiev() {
   }

    public PstKPI_Employee_Achiev(int i) throws DBException {
        super(new PstKPI_Employee_Achiev());
    }

    public PstKPI_Employee_Achiev(String sOid) throws DBException {
        super(new PstKPI_Employee_Achiev(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstKPI_Employee_Achiev(long lOid) throws DBException {
        super(new PstKPI_Employee_Achiev(0));
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
        return TBL_HR_KPI_EMPLOYEE_ACHIEV;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstKPI_Employee_Achiev().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        KPI_Employee_Achiev kPI_Employee_Achiev = fetchExc(ent.getOID());
        ent = (Entity) kPI_Employee_Achiev;
        return kPI_Employee_Achiev.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((KPI_Employee_Achiev) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((KPI_Employee_Achiev) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static KPI_Employee_Achiev fetchExc(long oid) throws DBException {
        try {
            KPI_Employee_Achiev kPI_Employee_Achiev = new KPI_Employee_Achiev();
            PstKPI_Employee_Achiev pstKPI_Employee_Achiev = new PstKPI_Employee_Achiev(oid);
            kPI_Employee_Achiev.setOID(oid);

         kPI_Employee_Achiev.setOID(oid);
         kPI_Employee_Achiev.setKpiListId(pstKPI_Employee_Achiev.getlong(FLD_KPI_LIST_ID));
         kPI_Employee_Achiev.setStartDate(pstKPI_Employee_Achiev.getDate(FLD_STARTDATE));
         kPI_Employee_Achiev.setEndDate(pstKPI_Employee_Achiev.getDate(FLD_ENDDATE));
         kPI_Employee_Achiev.setEmployeeId(pstKPI_Employee_Achiev.getlong(FLD_EMPLOYEE_ID));
         kPI_Employee_Achiev.setEntryDate(pstKPI_Employee_Achiev.getDate(FLD_ENTRYDATE));
         kPI_Employee_Achiev.setAchievement(pstKPI_Employee_Achiev.getdouble(FLD_ACHIEVEMENT));
         return kPI_Employee_Achiev;

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Employee_Achiev(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(KPI_Employee_Achiev kPI_Employee_Achiev) throws DBException {
        try {
            PstKPI_Employee_Achiev pstKPI_Employee_Achiev = new PstKPI_Employee_Achiev(0);

           // pstKPI_Employee_Achiev.setLong(FLD_KPI_EMPLOYEE_ACHIEV_ID, kPI_Employee_Achiev.getKpiListId());
            pstKPI_Employee_Achiev.setLong(FLD_KPI_LIST_ID, kPI_Employee_Achiev.getKpiListId());
            pstKPI_Employee_Achiev.setDate(FLD_STARTDATE, kPI_Employee_Achiev.getStartDate());
            pstKPI_Employee_Achiev.setDate(FLD_ENDDATE, kPI_Employee_Achiev.getEndDate());
            pstKPI_Employee_Achiev.setLong(FLD_EMPLOYEE_ID, kPI_Employee_Achiev.getEmployeeId());
            pstKPI_Employee_Achiev.setDate(FLD_ENTRYDATE, kPI_Employee_Achiev.getEntryDate());
            pstKPI_Employee_Achiev.setDouble(FLD_ACHIEVEMENT, kPI_Employee_Achiev.getAchievement());
          
            pstKPI_Employee_Achiev.insert();
            kPI_Employee_Achiev.setOID(pstKPI_Employee_Achiev.getlong(FLD_KPI_EMPLOYEE_ACHIEV_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Employee_Achiev(0), DBException.UNKNOWN);
        }
        return kPI_Employee_Achiev.getOID();
    }

    public static long updateExc(KPI_Employee_Achiev kPI_Employee_Achiev) throws DBException {
        try {
            if (kPI_Employee_Achiev.getOID() != 0) {
                PstKPI_Employee_Achiev pstKPI_Employee_Achiev = new PstKPI_Employee_Achiev(kPI_Employee_Achiev.getOID());

            pstKPI_Employee_Achiev.setLong(FLD_KPI_EMPLOYEE_ACHIEV_ID, kPI_Employee_Achiev.getOID());
            pstKPI_Employee_Achiev.setLong(FLD_KPI_LIST_ID, kPI_Employee_Achiev.getKpiListId());
            pstKPI_Employee_Achiev.setDate(FLD_STARTDATE, kPI_Employee_Achiev.getStartDate());
            pstKPI_Employee_Achiev.setDate(FLD_ENDDATE, kPI_Employee_Achiev.getEndDate());
            pstKPI_Employee_Achiev.setLong(FLD_EMPLOYEE_ID, kPI_Employee_Achiev.getEmployeeId());
            pstKPI_Employee_Achiev.setDate(FLD_ENTRYDATE, kPI_Employee_Achiev.getEntryDate());
            pstKPI_Employee_Achiev.setDouble(FLD_ACHIEVEMENT, kPI_Employee_Achiev.getAchievement());
            pstKPI_Employee_Achiev.update();

                return kPI_Employee_Achiev.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Employee_Achiev(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstKPI_Employee_Achiev pstKPI_Employee_Achiev = new PstKPI_Employee_Achiev(oid);
            pstKPI_Employee_Achiev.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Employee_Achiev(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_KPI_EMPLOYEE_ACHIEV;
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
                KPI_Employee_Achiev kPI_Employee_Achiev = new KPI_Employee_Achiev();
                resultToObject(rs, kPI_Employee_Achiev);
                lists.add(kPI_Employee_Achiev);
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
    
     public static double listAlldataAchCount(int tahun, long kpiListId, long CompanyId, long divisionId, long departmentId, long sectionId) {
        double value = 0; 
        DBResultSet dbrs = null;
 

        try {
            String sql = " SELECT SUM(KEA."+PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_ACHIEVEMENT]+") FROM " + TBL_HR_KPI_EMPLOYEE_ACHIEV + 
                    " KEA INNER JOIN hr_employee HE ON KEA."+PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_EMPLOYEE_ID]+" = HE."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" "+
                    " WHERE KPI_LIST_ID = "+kpiListId+" AND KEA."
            
                    + PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_STARTDATE] + " LIKE \"%" + tahun + "%\" AND KEA."
                    + PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_ENDDATE] + " LIKE \"%" + tahun + "%\" AND HE."
                    + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = " + CompanyId + " ";
            if (divisionId > 0){
                sql = sql +" AND HE."+  PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = " + divisionId + " ";
            }
            if (departmentId > 0){
                sql = sql +" AND HE."+  PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + departmentId + " ";
            }
            if (sectionId > 0){
                sql = sql +" AND HE."+  PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + sectionId + " ";
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
             
                value = rs.getInt(1);
              
            }
            rs.close();
            return value;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }
    
    
      public static void resultToObject(ResultSet rs, KPI_Employee_Achiev kPI_Employee_Achiev) {
        try {
            kPI_Employee_Achiev.setKpiListId(rs.getLong(PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_KPI_EMPLOYEE_ACHIEV_ID]));
            kPI_Employee_Achiev.setKpiListId(rs.getLong(PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_KPI_LIST_ID]));
            kPI_Employee_Achiev.setStartDate(rs.getDate(PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_STARTDATE]));
            kPI_Employee_Achiev.setEndDate(rs.getDate(PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_ENDDATE]));
            kPI_Employee_Achiev.setEmployeeId(rs.getLong(PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_EMPLOYEE_ID]));
            kPI_Employee_Achiev.setEntryDate(rs.getDate(PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_ENTRYDATE]));
            kPI_Employee_Achiev.setAchievement(rs.getDouble(PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_ACHIEVEMENT]));
            
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long kPI_Employee_AchievId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_KPI_EMPLOYEE_ACHIEV + " WHERE "
                    + PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_KPI_EMPLOYEE_ACHIEV_ID] + " = " + kPI_Employee_AchievId;

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

    public static long deleteKpiEmployeAchiev(int year, long kpiListId, long companyId) {
        DBResultSet dbrs = null;
        long resulthasil =0;
        try {
            String sql = "DELETE  FROM " + TBL_HR_KPI_EMPLOYEE_ACHIEV + " WHERE "
                    + PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_STARTDATE] + " LIKE \"%" + year + "%\" AND "
                    + PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_ENDDATE] + " LIKE \"%" + year + "%\" AND "
                    + PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_KPI_LIST_ID] + " = " + kpiListId + "";
             
            DBHandler.execSqlInsert(sql);
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
            
        } finally {
            DBResultSet.close(dbrs);
            return resulthasil;
        }
    }
    
    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_KPI_EMPLOYEE_ACHIEV_ID] + ") FROM " + TBL_HR_KPI_EMPLOYEE_ACHIEV;
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
    
        public static double getTotalAchievEmployee(long employeeId, long kpiListId) {
        double nilai = 0;
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT SUM(ket." + PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_ACHIEVEMENT] + ") FROM  " + PstKPI_Employee_Achiev.TBL_HR_KPI_EMPLOYEE_ACHIEV +" ket ";
            sql =  sql + " WHERE ket." + PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_EMPLOYEE_ID] + " = " + employeeId ; 
            sql =  sql + " AND ket." + PstKPI_Employee_Achiev.fieldNames[PstKPI_Employee_Achiev.FLD_KPI_LIST_ID] + " = " + kpiListId ; 
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
               nilai  = rs.getInt(1);
            }
            rs.close();
            return nilai;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return nilai;
    }

    public static Hashtable Hlist(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashListSec = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_KPI_EMPLOYEE_ACHIEV;
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
                KPI_Employee_Achiev kPI_Employee_Achiev = new KPI_Employee_Achiev();
                resultToObject(rs, kPI_Employee_Achiev);
                hashListSec.put(kPI_Employee_Achiev.getEmployeeId(), kPI_Employee_Achiev);  
            }
            rs.close();
            return hashListSec;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Hashtable();
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
                    KPI_Employee_Achiev kPI_Employee_Achiev = (KPI_Employee_Achiev) list.get(ls);
                    if (oid == kPI_Employee_Achiev.getOID()) {
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
