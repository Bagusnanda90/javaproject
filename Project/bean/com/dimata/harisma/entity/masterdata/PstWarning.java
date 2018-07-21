/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

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
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.masterdata.*;

import com.dimata.harisma.entity.employee.*;

/**
 *
 * @author Wiweka
 */
public class PstWarning extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_WARNING = "hr_point_of_warning";
    public static final int FLD_WARN_ID = 0;
    public static final int FLD_WARN_DESC = 1;
    public static final int FLD_WARN_POINT = 2;
    public static final String[] fieldNames = {
        "WARN_ID",
        "WARN_DESC",
        "WARN_POINT"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_INT
    };

    public PstWarning() {
    }

    public PstWarning(int i) throws DBException {
        super(new PstWarning());
    }

    public PstWarning(String sOid) throws DBException {
        super(new PstWarning(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else 
            return;
    }

    public PstWarning(long lOid) throws DBException {
        super(new PstWarning(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else 
            return;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_HR_WARNING;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstWarning().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        Warning warning = fetchExc(ent.getOID());
        ent = (Entity) warning;
        return warning.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((Warning) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((Warning) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static Warning fetchExc(long oid) throws DBException {
        try {
            Warning warning = new Warning();
            PstWarning pstWarning = new PstWarning(oid);
            warning.setOID(oid);

            warning.setWarnDesc(pstWarning.getString(FLD_WARN_DESC));
            warning.setWarnPoint(pstWarning.getInt(FLD_WARN_POINT));

            return warning;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstWarning(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(Warning warning) throws DBException {
        try {
            PstWarning pstWarning = new PstWarning(0);

            pstWarning.setString(FLD_WARN_DESC, warning.getWarnDesc());
            pstWarning.setInt(FLD_WARN_POINT, warning.getWarnPoint());

            pstWarning.insert();
            warning.setOID(pstWarning.getlong(FLD_WARN_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstWarning(0), DBException.UNKNOWN);
        }
        return warning.getOID();
    }

    public static long updateExc(Warning warning) throws DBException {
        try {
            if (warning.getOID() != 0) {
                PstWarning pstWarning = new PstWarning(warning.getOID());

                pstWarning.setString(FLD_WARN_DESC, warning.getWarnDesc());
                pstWarning.setInt(FLD_WARN_POINT, warning.getWarnPoint());

                pstWarning.update();
                return warning.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstWarning(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstWarning pstWarning = new PstWarning(oid);
            pstWarning.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstWarning(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_WARNING;
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
                Warning warning = new Warning();
                resultToObject(rs, warning);
                lists.add(warning);
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

    private static void resultToObject(ResultSet rs, Warning warning) {
        try {
            warning.setOID(rs.getLong(PstWarning.fieldNames[PstWarning.FLD_WARN_ID]));
            warning.setWarnDesc(rs.getString(PstWarning.fieldNames[PstWarning.FLD_WARN_DESC]));
            warning.setWarnPoint(rs.getInt(PstWarning.fieldNames[PstWarning.FLD_WARN_POINT]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(int warnId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_WARNING + " WHERE "
                    + PstWarning.fieldNames[PstWarning.FLD_WARN_ID] + " = " + warnId;

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
            String sql = "SELECT COUNT(" + PstWarning.fieldNames[PstWarning.FLD_WARN_ID] + ") FROM " + TBL_HR_WARNING;
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
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    Warning warning = (Warning) list.get(ls);
                    if (oid == warning.getOID()) {
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
    /* This method used to find command where current data */

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + (recordToGet - mdl);
        if (start == 0) {
            cmd = Command.FIRST;
        } else {
            if (start == (vectSize - recordToGet)) {
                cmd = Command.LAST;
            } else {
                start = start + recordToGet;
                if (start <= (vectSize - recordToGet)) {
                    cmd = Command.NEXT;
                    System.out.println("next.......................");
                } else {
                    start = start - recordToGet;
                    if (start > 0) {
                        cmd = Command.PREV;
                        System.out.println("prev.......................");
                    }
                }
            }
        }

        return cmd;
    }

    public static boolean checkMaster(long warnId) {
        if (PstEmpWarning.checkWarning(warnId)) {
            return true;
        } else {
            return false;
        }
    }
}
