/* Created on 	:  20 April 2015 [time] AM/PM
 *
 * @author  	:  Priska
 * @version  	:  [version]
 */
/*******************************************************************
 * Class Description 	: PstArApCreditor
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Priska
 */
/* package java */
import java.sql.*;
import java.util.*;


/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

public class PstArApCreditor extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_ARAP_CREDITOR = "hr_arap_creditor";
    public static final int FLD_ARAP_CREDITOR_ID = 0;
    public static final int FLD_CREDITOR_NAME = 1;
    public static final int FLD_DESCRIPTION = 2;
    public static final String[] fieldNames = {
        "ARAP_CREDITOR_ID", 
        "CREDITOR_NAME", 
        "DESCRIPTION", 
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
    };

    public PstArApCreditor() {
    }

    public PstArApCreditor(int i) throws DBException {
        super(new PstArApCreditor());
    }

    public PstArApCreditor(String sOid) throws DBException {
        super(new PstArApCreditor(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstArApCreditor(long lOid) throws DBException {
        super(new PstArApCreditor(0));
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
        return TBL_HR_ARAP_CREDITOR;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstArApCreditor().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        ArApCreditor arApCreditor = fetchExc(ent.getOID());
        ent = (Entity) arApCreditor;
        return arApCreditor.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((ArApCreditor) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((ArApCreditor) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static ArApCreditor fetchExc(long oid) throws DBException {
        try {
            ArApCreditor arApCreditor = new ArApCreditor();
            PstArApCreditor pstArApCreditor = new PstArApCreditor(oid);
            arApCreditor.setOID(oid);

            arApCreditor.setCreditorName(pstArApCreditor.getString(FLD_CREDITOR_NAME));
            arApCreditor.setDescription(pstArApCreditor.getString(FLD_DESCRIPTION));
            return arApCreditor;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstArApCreditor(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(ArApCreditor arApCreditor) throws DBException {
        try {
            PstArApCreditor pstArApCreditor = new PstArApCreditor(0);

            pstArApCreditor.setString(FLD_CREDITOR_NAME, arApCreditor.getCreditorName());
            pstArApCreditor.setString(FLD_DESCRIPTION, arApCreditor.getDescription());
            pstArApCreditor.insert();
            arApCreditor.setOID(pstArApCreditor.getlong(FLD_ARAP_CREDITOR_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstArApCreditor(0), DBException.UNKNOWN);
        }
        return arApCreditor.getOID();
    }

    public static long updateExc(ArApCreditor arApCreditor) throws DBException {
        try {
            if (arApCreditor.getOID() != 0) {
                PstArApCreditor pstArApCreditor = new PstArApCreditor(arApCreditor.getOID());

                pstArApCreditor.setString(FLD_CREDITOR_NAME, arApCreditor.getCreditorName());
                pstArApCreditor.setString(FLD_DESCRIPTION, arApCreditor.getDescription());
                pstArApCreditor.update();
                return arApCreditor.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstArApCreditor(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstArApCreditor pstArApCreditor = new PstArApCreditor(oid);
            pstArApCreditor.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstArApCreditor(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_ARAP_CREDITOR;
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
                ArApCreditor arApCreditor = new ArApCreditor();
                resultToObject(rs, arApCreditor);
                lists.add(arApCreditor);
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
    
    
    public static void resultToObject(ResultSet rs, ArApCreditor arApCreditor) {
        try {
            arApCreditor.setOID(rs.getLong(PstArApCreditor.fieldNames[PstArApCreditor.FLD_ARAP_CREDITOR_ID]));
            arApCreditor.setCreditorName(rs.getString(PstArApCreditor.fieldNames[PstArApCreditor.FLD_CREDITOR_NAME]));
            arApCreditor.setDescription(rs.getString(PstArApCreditor.fieldNames[PstArApCreditor.FLD_DESCRIPTION]));
            
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long arApCreditorId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_ARAP_CREDITOR + " WHERE "
                    + PstArApCreditor.fieldNames[PstArApCreditor.FLD_ARAP_CREDITOR_ID] + " = " + arApCreditorId;

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
            String sql = "SELECT COUNT(" + PstArApCreditor.fieldNames[PstArApCreditor.FLD_ARAP_CREDITOR_ID] + ") FROM " + TBL_HR_ARAP_CREDITOR;
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
                    ArApCreditor arApCreditor = (ArApCreditor) list.get(ls);
                    if (oid == arApCreditor.getOID()) {
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
