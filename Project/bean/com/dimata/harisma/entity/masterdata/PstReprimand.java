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

import com.dimata.harisma.entity.masterdata.*;

import com.dimata.harisma.entity.employee.*;
/**
 *
 * @author Wiweka
 */
public class PstReprimand extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    public static final String TBL_HR_REPRIMAND = "hr_point_of_reprimand";
    public static final int FLD_REPRIMAND_ID = 0;
    public static final int FLD_REPRIMAND_DESC = 1;
    public static final int FLD_REPRIMAND_POINT = 2;

    public static final String[] fieldNames = {
        "REPRIMAND_ID",
        "REPRIMAND_DESC",
        "REPRIMAND_POINT"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_INT
    };

     public PstReprimand() {
    }

   public PstReprimand(int i) throws DBException {
        super(new PstReprimand());
    }

    public PstReprimand(String sOid) throws DBException {
        super(new PstReprimand(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }
    public PstReprimand(long lOid) throws DBException {
        super(new PstReprimand(0));
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
        return TBL_HR_REPRIMAND;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }
    public String getPersistentName() {
        return new PstReprimand().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        Reprimand reprimand = fetchExc(ent.getOID());
        ent = (Entity) reprimand;
        return reprimand.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((Reprimand) ent);
    }
     public long updateExc(Entity ent) throws Exception {
        return updateExc((Reprimand) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    public static Reprimand fetchExc(long oid) throws DBException {
        try {
            Reprimand reprimand = new Reprimand();
            PstReprimand pstReprimand = new PstReprimand(oid);
            reprimand.setOID(oid);

            reprimand.setReprimandDesc(pstReprimand.getString(FLD_REPRIMAND_DESC));
            reprimand.setReprimandPoint(pstReprimand.getInt(FLD_REPRIMAND_POINT));

            return reprimand;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstReprimand(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(Reprimand reprimand) throws DBException {
        try {
            PstReprimand pstReprimand = new PstReprimand(0);

            pstReprimand.setString(FLD_REPRIMAND_DESC, reprimand.getReprimandDesc());
            pstReprimand.setInt(FLD_REPRIMAND_POINT, reprimand.getReprimandPoint());

            pstReprimand.insert();
            reprimand.setOID(pstReprimand.getlong(FLD_REPRIMAND_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstReprimand(0), DBException.UNKNOWN);
        }
        return reprimand.getOID();
    }

   public static long updateExc(Reprimand reprimand) throws DBException {
        try {
            if (reprimand.getOID() != 0) {
                PstReprimand pstReprimand = new PstReprimand(reprimand.getOID());

                pstReprimand.setString(FLD_REPRIMAND_DESC, reprimand.getReprimandDesc());
                pstReprimand.setInt(FLD_REPRIMAND_POINT, reprimand.getReprimandPoint());

                pstReprimand.update();
                return reprimand.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstReprimand(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstReprimand pstReprimand = new PstReprimand(oid);
            pstReprimand.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstReprimand(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_REPRIMAND;
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
                Reprimand reprimand = new Reprimand();
                resultToObject(rs, reprimand);
                lists.add(reprimand);
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

    private static void resultToObject(ResultSet rs, Reprimand reprimand) {
        try {
            reprimand.setOID(rs.getLong(PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_ID]));
            reprimand.setReprimandDesc(rs.getString(PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_DESC]));
            reprimand.setReprimandPoint(rs.getInt(PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_POINT]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(int reprimandId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_REPRIMAND + " WHERE "
                    + PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_ID] + " = " + reprimandId;

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
            String sql = "SELECT COUNT(" + PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_ID] + ") FROM " + TBL_HR_REPRIMAND;
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
                    Reprimand reprimand = (Reprimand) list.get(ls);
                    if (oid == reprimand.getOID()) {
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

    public static boolean checkMaster(long reprimandId) {
        if (PstEmpReprimand.checkWarning(reprimandId)) {
            return true;
        } else {
            return false;
        }
    }


}
