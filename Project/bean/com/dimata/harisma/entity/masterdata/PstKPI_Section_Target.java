/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

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
public class PstKPI_Section_Target extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_KPI_SECTION_TARGET = "hr_kpi_section_target";
   public static final int FLD_KPI_SECTION_TARGET_ID = 0;
   public static final int FLD_KPI_LIST_ID = 1;
   public static final int FLD_STARTDATE = 2;
   public static final int FLD_ENDDATE = 3;
   public static final int FLD_SECTION_ID = 4;
   public static final int FLD_TARGET = 5;
   public static final int FLD_ACHIEVEMENT = 6;
   
    public static final String[] fieldNames = {
      "KPI_SECTION_TARGET_ID",
      "KPI_LIST_ID",
      "STARTDATE",
      "ENDDATE",
      "SECTION_ID",
      "TARGET",
      "ACHIEVEMENT"
    };
    public static final int[] fieldTypes = {
      TYPE_LONG + TYPE_PK + TYPE_ID,
      TYPE_STRING,
      TYPE_DATE,
      TYPE_DATE,
      TYPE_LONG,
      TYPE_FLOAT,
      TYPE_FLOAT
    };

   public PstKPI_Section_Target() {
   }

    public PstKPI_Section_Target(int i) throws DBException {
        super(new PstKPI_Section_Target());
    }

    public PstKPI_Section_Target(String sOid) throws DBException {
        super(new PstKPI_Section_Target(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstKPI_Section_Target(long lOid) throws DBException {
        super(new PstKPI_Section_Target(0));
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
        return TBL_HR_KPI_SECTION_TARGET;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstKPI_Section_Target().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        KPI_Section_Target kPI_Section_Target = fetchExc(ent.getOID());
        ent = (Entity) kPI_Section_Target;
        return kPI_Section_Target.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((KPI_Section_Target) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((KPI_Section_Target) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static KPI_Section_Target fetchExc(long oid) throws DBException {
        try {
            KPI_Section_Target kPI_Section_Target = new KPI_Section_Target();
            PstKPI_Section_Target pstKPI_Section_Target = new PstKPI_Section_Target(oid);
            kPI_Section_Target.setOID(oid);

         kPI_Section_Target.setOID(oid);
         kPI_Section_Target.setKpiSectionTargetId(pstKPI_Section_Target.getlong(FLD_KPI_SECTION_TARGET_ID));
         kPI_Section_Target.setKpiListId(pstKPI_Section_Target.getlong(FLD_KPI_LIST_ID));
         kPI_Section_Target.setStartDate(pstKPI_Section_Target.getDate(FLD_STARTDATE));
         kPI_Section_Target.setEndDate(pstKPI_Section_Target.getDate(FLD_ENDDATE));
         kPI_Section_Target.setSectionId(pstKPI_Section_Target.getlong(FLD_SECTION_ID));
         kPI_Section_Target.setTarget(pstKPI_Section_Target.getdouble(FLD_TARGET));
         kPI_Section_Target.setAchievement(pstKPI_Section_Target.getdouble(FLD_ACHIEVEMENT));
         return kPI_Section_Target;

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Section_Target(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(KPI_Section_Target kPI_Section_Target) throws DBException {
        try {
            PstKPI_Section_Target pstKPI_Section_Target = new PstKPI_Section_Target(0);

           // pstKPI_Section_Target.setLong(FLD_KPI_SECTION_TARGET_ID, kPI_Section_Target.getKpiSectionTargetId());
            pstKPI_Section_Target.setLong(FLD_KPI_LIST_ID, kPI_Section_Target.getKpiListId());
            pstKPI_Section_Target.setDate(FLD_STARTDATE, kPI_Section_Target.getStartDate());
            pstKPI_Section_Target.setDate(FLD_ENDDATE, kPI_Section_Target.getEndDate());
            pstKPI_Section_Target.setLong(FLD_SECTION_ID, kPI_Section_Target.getSectionId());
            pstKPI_Section_Target.setDouble(FLD_TARGET, kPI_Section_Target.getTarget());
            pstKPI_Section_Target.setDouble(FLD_ACHIEVEMENT, kPI_Section_Target.getAchievement());
          
            pstKPI_Section_Target.insert();
            kPI_Section_Target.setOID(pstKPI_Section_Target.getlong(FLD_KPI_SECTION_TARGET_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Section_Target(0), DBException.UNKNOWN);
        }
        return kPI_Section_Target.getOID();
    }

    public static long updateExc(KPI_Section_Target kPI_Section_Target) throws DBException {
        try {
            if (kPI_Section_Target.getOID() != 0) {
                PstKPI_Section_Target pstKPI_Section_Target = new PstKPI_Section_Target(kPI_Section_Target.getOID());

            pstKPI_Section_Target.setLong(FLD_KPI_SECTION_TARGET_ID, kPI_Section_Target.getKpiSectionTargetId());
            pstKPI_Section_Target.setLong(FLD_KPI_LIST_ID, kPI_Section_Target.getKpiListId());
            pstKPI_Section_Target.setDate(FLD_STARTDATE, kPI_Section_Target.getStartDate());
            pstKPI_Section_Target.setDate(FLD_ENDDATE, kPI_Section_Target.getEndDate());
            pstKPI_Section_Target.setLong(FLD_SECTION_ID, kPI_Section_Target.getSectionId());
            pstKPI_Section_Target.setDouble(FLD_TARGET, kPI_Section_Target.getTarget());
            pstKPI_Section_Target.setDouble(FLD_ACHIEVEMENT, kPI_Section_Target.getAchievement());
            pstKPI_Section_Target.update();

                pstKPI_Section_Target.update();
                return kPI_Section_Target.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Section_Target(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstKPI_Section_Target pstKPI_Section_Target = new PstKPI_Section_Target(oid);
            pstKPI_Section_Target.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Section_Target(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_KPI_SECTION_TARGET;
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
                KPI_Section_Target kPI_Section_Target = new KPI_Section_Target();
                resultToObject(rs, kPI_Section_Target);
                lists.add(kPI_Section_Target);
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
    public static Hashtable Hlist(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashListSec = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_KPI_SECTION_TARGET;
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
                KPI_Section_Target kPI_Section_Target = new KPI_Section_Target();
                resultToObject(rs, kPI_Section_Target);
                hashListSec.put(kPI_Section_Target.getSectionId(), kPI_Section_Target);  
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
      public static void resultToObject(ResultSet rs, KPI_Section_Target kPI_Section_Target) {
        try {
            kPI_Section_Target.setKpiSectionTargetId(rs.getLong(PstKPI_Section_Target.fieldNames[PstKPI_Section_Target.FLD_KPI_SECTION_TARGET_ID]));
            kPI_Section_Target.setKpiListId(rs.getLong(PstKPI_Section_Target.fieldNames[PstKPI_Section_Target.FLD_KPI_LIST_ID]));
            kPI_Section_Target.setStartDate(rs.getDate(PstKPI_Section_Target.fieldNames[PstKPI_Section_Target.FLD_STARTDATE]));
            kPI_Section_Target.setEndDate(rs.getDate(PstKPI_Section_Target.fieldNames[PstKPI_Section_Target.FLD_ENDDATE]));
            kPI_Section_Target.setSectionId(rs.getLong(PstKPI_Section_Target.fieldNames[PstKPI_Section_Target.FLD_SECTION_ID]));
            kPI_Section_Target.setTarget(rs.getDouble(PstKPI_Section_Target.fieldNames[PstKPI_Section_Target.FLD_TARGET]));
            kPI_Section_Target.setAchievement(rs.getDouble(PstKPI_Section_Target.fieldNames[PstKPI_Section_Target.FLD_ACHIEVEMENT]));
            
        } catch (Exception e) {
        }
    }
    
      
      
      public static long deleteKpiSectionTarget(int year, long kpiListId, long companyId) {
        DBResultSet dbrs = null;
        long resulthasil =0;
        try {
            String sql = "DELETE  FROM " + TBL_HR_KPI_SECTION_TARGET + " WHERE "
                    + PstKPI_Section_Target.fieldNames[PstKPI_Section_Target.FLD_STARTDATE] + " LIKE \"%" + year + "%\" AND "
                    + PstKPI_Section_Target.fieldNames[PstKPI_Section_Target.FLD_ENDDATE] + " LIKE \"%" + year + "%\" AND "
                    + PstKPI_Section_Target.fieldNames[PstKPI_Section_Target.FLD_KPI_LIST_ID] + " = " + kpiListId + "";
             
            DBHandler.execSqlInsert(sql);
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
            
        } finally {
            DBResultSet.close(dbrs);
            return resulthasil;
        }
    }
      
      
    public static boolean checkOID(long kPI_Section_TargetId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_KPI_SECTION_TARGET + " WHERE "
                    + PstKPI_Section_Target.fieldNames[PstKPI_Section_Target.FLD_KPI_SECTION_TARGET_ID] + " = " + kPI_Section_TargetId;

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
            String sql = "SELECT COUNT(" + PstKPI_Section_Target.fieldNames[PstKPI_Section_Target.FLD_KPI_SECTION_TARGET_ID] + ") FROM " + TBL_HR_KPI_SECTION_TARGET;
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
                    KPI_Section_Target kPI_Section_Target = (KPI_Section_Target) list.get(ls);
                    if (oid == kPI_Section_Target.getOID()) {
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
