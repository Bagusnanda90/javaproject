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
public class PstKPI_Group extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_KPI_GROUP = "hr_kpi_group";
   public static final int FLD_KPI_GROUP_ID = 0;
   public static final int FLD_KPI_TYPE_ID = 1;
   public static final int FLD_GROUP_TITLE = 2;
   public static final int FLD_DESCRIPTION = 3;
   
    public static final String[] fieldNames = {
      "KPI_GROUP_ID",
      "KPI_TYPE_ID",
      "GROUP_TITLE",
      "DESCRIPTION"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING
    };

   public PstKPI_Group() {
   }

    public PstKPI_Group(int i) throws DBException {
        super(new PstKPI_Group());
    }

    public PstKPI_Group(String sOid) throws DBException {
        super(new PstKPI_Group(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstKPI_Group(long lOid) throws DBException {
        super(new PstKPI_Group(0));
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
        return TBL_HR_KPI_GROUP;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstKPI_Group().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        KPI_Group kPI_Group = fetchExc(ent.getOID());
        ent = (Entity) kPI_Group;
        return kPI_Group.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((KPI_Group) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((KPI_Group) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static KPI_Group fetchExc(long oid) throws DBException {
        try {
            KPI_Group kPI_Group = new KPI_Group();
            PstKPI_Group pstKPI_Group = new PstKPI_Group(oid);
            kPI_Group.setOID(oid);
            
            kPI_Group.setKpi_type_id(pstKPI_Group.getlong(FLD_KPI_TYPE_ID));
            kPI_Group.setGroup_title(pstKPI_Group.getString(FLD_GROUP_TITLE));
            kPI_Group.setDescription(pstKPI_Group.getString(FLD_DESCRIPTION));

            return kPI_Group;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Group(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(KPI_Group kPI_Group) throws DBException {
        try {
            PstKPI_Group pstKPI_Group = new PstKPI_Group(0);

            pstKPI_Group.setLong(FLD_KPI_TYPE_ID, kPI_Group.getKpi_type_id());
            pstKPI_Group.setString(FLD_GROUP_TITLE, kPI_Group.getGroup_title());
            pstKPI_Group.setString(FLD_DESCRIPTION, kPI_Group.getDescription());
          
            pstKPI_Group.insert();
            kPI_Group.setOID(pstKPI_Group.getlong(FLD_KPI_GROUP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Group(0), DBException.UNKNOWN);
        }
        return kPI_Group.getOID();
    }

    public static long updateExc(KPI_Group kPI_Group) throws DBException {
        try {
            if (kPI_Group.getOID() != 0) {
                PstKPI_Group pstKPI_Group = new PstKPI_Group(kPI_Group.getOID());

                pstKPI_Group.setLong(FLD_KPI_TYPE_ID, kPI_Group.getKpi_type_id());
                pstKPI_Group.setString(FLD_GROUP_TITLE, kPI_Group.getGroup_title());
                pstKPI_Group.setString(FLD_DESCRIPTION, kPI_Group.getDescription());
            
                pstKPI_Group.update();
                return kPI_Group.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Group(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstKPI_Group pstKPI_Group = new PstKPI_Group(oid);
            pstKPI_Group.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Group(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_KPI_GROUP;
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
                KPI_Group kPI_Group = new KPI_Group();
                resultToObject(rs, kPI_Group);
                lists.add(kPI_Group);
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
    
      public static void resultToObject(ResultSet rs, KPI_Group kPI_Group) {
        try {
            kPI_Group.setOID(rs.getLong(PstKPI_Group.fieldNames[PstKPI_Group.FLD_KPI_GROUP_ID]));
            kPI_Group.setKpi_type_id(rs.getLong(PstKPI_Group.fieldNames[PstKPI_Group.FLD_KPI_TYPE_ID]));
            kPI_Group.setGroup_title(rs.getString(PstKPI_Group.fieldNames[PstKPI_Group.FLD_GROUP_TITLE]));
            kPI_Group.setDescription(rs.getString(PstKPI_Group.fieldNames[PstKPI_Group.FLD_DESCRIPTION]));
            
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long kPI_GroupId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_KPI_GROUP + " WHERE "
                    + PstKPI_Group.fieldNames[PstKPI_Group.FLD_KPI_GROUP_ID] + " = " + kPI_GroupId;

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
            String sql = "SELECT COUNT(" + PstKPI_Group.fieldNames[PstKPI_Group.FLD_KPI_GROUP_ID] + ") FROM " + TBL_HR_KPI_GROUP;
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
                    KPI_Group kPI_Group = (KPI_Group) list.get(ls);
                    if (oid == kPI_Group.getOID()) {
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
