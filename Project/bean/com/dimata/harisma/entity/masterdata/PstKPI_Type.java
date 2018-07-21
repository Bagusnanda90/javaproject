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
public class PstKPI_Type extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_KPI_TYPE = "hr_kpi_type";
   public static final int FLD_KPI_TYPE_ID = 0;
   public static final int FLD_TYPE_NAME = 1;
   public static final int FLD_DESCRIPTION = 2;
   
    public static final String[] fieldNames = {
      "KPI_TYPE_ID",
      "TYPE_NAME",
      "DESCRIPTION"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING
    };

   public PstKPI_Type() {
   }

    public PstKPI_Type(int i) throws DBException {
        super(new PstKPI_Type());
    }

    public PstKPI_Type(String sOid) throws DBException {
        super(new PstKPI_Type(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstKPI_Type(long lOid) throws DBException {
        super(new PstKPI_Type(0));
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
        return TBL_HR_KPI_TYPE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstKPI_Type().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        KPI_Type kPI_Type = fetchExc(ent.getOID());
        ent = (Entity) kPI_Type;
        return kPI_Type.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((KPI_Type) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((KPI_Type) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static KPI_Type fetchExc(long oid) throws DBException {
        try {
            KPI_Type kPI_Type = new KPI_Type();
            PstKPI_Type pstKPI_Type = new PstKPI_Type(oid);
            kPI_Type.setOID(oid);

            kPI_Type.setType_name(pstKPI_Type.getString(FLD_TYPE_NAME));
            kPI_Type.setDescription(pstKPI_Type.getString(FLD_DESCRIPTION));

            return kPI_Type;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Type(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(KPI_Type kPI_Type) throws DBException {
        try {
            PstKPI_Type pstKPI_Type = new PstKPI_Type(0);

            
            pstKPI_Type.setString(FLD_TYPE_NAME, kPI_Type.getType_name());
            pstKPI_Type.setString(FLD_DESCRIPTION, kPI_Type.getDescription());
          
            pstKPI_Type.insert();
            kPI_Type.setOID(pstKPI_Type.getlong(FLD_KPI_TYPE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Type(0), DBException.UNKNOWN);
        }
        return kPI_Type.getOID();
    }

    public static long updateExc(KPI_Type kPI_Type) throws DBException {
        try {
            if (kPI_Type.getOID() != 0) {
                PstKPI_Type pstKPI_Type = new PstKPI_Type(kPI_Type.getOID());

                pstKPI_Type.setString(FLD_TYPE_NAME, kPI_Type.getType_name());
                pstKPI_Type.setString(FLD_DESCRIPTION, kPI_Type.getDescription());

                pstKPI_Type.update();
                return kPI_Type.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Type(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstKPI_Type pstKPI_Type = new PstKPI_Type(oid);
            pstKPI_Type.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKPI_Type(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_KPI_TYPE;
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
                KPI_Type kPI_Type = new KPI_Type();
                resultToObject(rs, kPI_Type);
                lists.add(kPI_Type);
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
    
      public static void resultToObject(ResultSet rs, KPI_Type kPI_Type) {
        try {
            kPI_Type.setOID(rs.getLong(PstKPI_Type.fieldNames[PstKPI_Type.FLD_KPI_TYPE_ID]));
            kPI_Type.setType_name(rs.getString(PstKPI_Type.fieldNames[PstKPI_Type.FLD_TYPE_NAME]));
            kPI_Type.setDescription(rs.getString(PstKPI_Type.fieldNames[PstKPI_Type.FLD_DESCRIPTION]));
            
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long kPI_TypeId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_KPI_TYPE + " WHERE "
                    + PstKPI_Type.fieldNames[PstKPI_Type.FLD_KPI_TYPE_ID] + " = " + kPI_TypeId;

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
            String sql = "SELECT COUNT(" + PstKPI_Type.fieldNames[PstKPI_Type.FLD_KPI_TYPE_ID] + ") FROM " + TBL_HR_KPI_TYPE;
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
                    KPI_Type kPI_Type = (KPI_Type) list.get(ls);
                    if (oid == kPI_Type.getOID()) {
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
