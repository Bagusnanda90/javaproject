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
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class PstKPI_List_Group extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_KPI_LIST_GROUP = "hr_kpi_list_group";
   public static final int FLD_KPI_LIST_GROUP_ID = 0;
   public static final int FLD_KPI_LIST_ID = 1;
   public static final int FLD_KPI_GROUP_ID = 2;
   
    public static final String[] fieldNames = {
      "KPI_LIST_GROUP_ID",
      "KPI_LIST_ID",
      "KPI_GROUP_ID"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG
    };

   public PstKPI_List_Group() {
   }

    public PstKPI_List_Group(int i) throws DBException {
        super(new PstKPI_List_Group());
    }

    public PstKPI_List_Group(String sOid) throws DBException {
        super(new PstKPI_List_Group(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstKPI_List_Group(long lOid) throws DBException {
        super(new PstKPI_List_Group(0));
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
        return TBL_HR_KPI_LIST_GROUP;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstKPI_List_Group().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        KPI_List_Group kPI_List_Group = fetchExc(ent.getOID());
        ent = (Entity) kPI_List_Group;
        return kPI_List_Group.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((KPI_List_Group) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((KPI_List_Group) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static KPI_List_Group fetchExc(long oid) throws DBException {
        try {
            KPI_List_Group kPI_List_Group = new KPI_List_Group();
            PstKPI_List_Group pstKPI_List_Group = new PstKPI_List_Group(oid);
            kPI_List_Group.setOID(oid);
            
            kPI_List_Group.setKpiListId(pstKPI_List_Group.getlong(FLD_KPI_LIST_ID));
            kPI_List_Group.setKpiListId(pstKPI_List_Group.getLong(FLD_KPI_GROUP_ID));
         
            return kPI_List_Group;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_List_Group(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(KPI_List_Group kPI_List_Group) throws DBException {
        try {
            PstKPI_List_Group pstKPI_List_Group = new PstKPI_List_Group(0);

            pstKPI_List_Group.setLong(FLD_KPI_LIST_ID, kPI_List_Group.getKpiListId());
            pstKPI_List_Group.setLong(FLD_KPI_GROUP_ID, kPI_List_Group.getKpiGroupId());          
            pstKPI_List_Group.insert();
            kPI_List_Group.setOID(pstKPI_List_Group.getlong(FLD_KPI_GROUP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_List_Group(0), DBException.UNKNOWN);
        }
        return kPI_List_Group.getOID();
    }

    public static long updateExc(KPI_List_Group kPI_List_Group) throws DBException {
        try {
            if (kPI_List_Group.getOID() != 0) {
                PstKPI_List_Group pstKPI_List_Group = new PstKPI_List_Group(kPI_List_Group.getOID());

                pstKPI_List_Group.setLong(FLD_KPI_LIST_ID, kPI_List_Group.getKpiListId());
                pstKPI_List_Group.setLong(FLD_KPI_GROUP_ID, kPI_List_Group.getKpiGroupId()); 
            
                pstKPI_List_Group.update();
                return kPI_List_Group.getOID();
    }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_List_Group(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstKPI_List_Group pstKPI_List_Group = new PstKPI_List_Group(oid);
            pstKPI_List_Group.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_List_Group(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_KPI_LIST_GROUP;
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
                KPI_List_Group kPI_List_Group = new KPI_List_Group();
                resultToObject(rs, kPI_List_Group);
                lists.add(kPI_List_Group);
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
    
      public static void resultToObject(ResultSet rs, KPI_List_Group kPI_List_Group) {
        try {
            kPI_List_Group.setOID(rs.getLong(PstKPI_List_Group.fieldNames[PstKPI_List_Group.FLD_KPI_LIST_ID]));
            kPI_List_Group.setKpiListId(rs.getLong(PstKPI_List_Group.fieldNames[PstKPI_List_Group.FLD_KPI_LIST_GROUP_ID]));
            kPI_List_Group.setKpiGroupId(rs.getLong(PstKPI_List_Group.fieldNames[PstKPI_List_Group.FLD_KPI_GROUP_ID]));
            
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long kPI_List_GroupId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_KPI_LIST_GROUP + " WHERE "
                    + PstKPI_List_Group.fieldNames[PstKPI_List_Group.FLD_KPI_LIST_GROUP_ID] + " = " + kPI_List_GroupId;

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
            String sql = "SELECT COUNT(" + PstKPI_List_Group.fieldNames[PstKPI_List_Group.FLD_KPI_LIST_GROUP_ID] + ") FROM " + TBL_HR_KPI_LIST_GROUP;
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

  public static long deletewhereGroup(long listGroup) {
        DBResultSet dbrs = null;
        long resulthasil =0;
        try {
            String sql = "DELETE  FROM " + TBL_HR_KPI_LIST_GROUP + " WHERE "
                    + PstKPI_List_Group.fieldNames[PstKPI_List_Group.FLD_KPI_LIST_ID] + " = " + listGroup;
            DBHandler.execSqlInsert(sql);

        } catch (Exception e) {
            System.out.println("err : " + e.toString());
            
        } finally {
            DBResultSet.close(dbrs);
            return resulthasil;
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
                    KPI_List_Group kPI_List_Group = (KPI_List_Group) list.get(ls);
                    if (oid == kPI_List_Group.getOID()) {
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
