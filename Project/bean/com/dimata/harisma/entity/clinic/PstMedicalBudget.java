/*
 * PstPaySlipComp.java
 *
 * Created on April 26, 2007, 2:43 PM
 */
package com.dimata.harisma.entity.clinic;

/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
import com.dimata.system.entity.system.*;

/**
 *
 * @author  yunny
 */
public class PstMedicalBudget extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_MEDICAL_BUDGET = "hr_medical_budget";//"PAY_SLIP_COMP";
    public static final int FLD_MEDICAL_LEVEL_ID = 0;
    public static final int FLD_MEDICAL_CASE_ID = 1;
    public static final int FLD_BUDGET = 2;
    public static final int FLD_USE_PERIOD = 3;
    public static final int FLD_USE_PAX = 4;
    public static final String[] fieldNames = {
        "MEDICAL_LEVEL_ID",
        "MEDICAL_CASE_ID",
        "BUDGET",
        "USE_PERIOD",
        "USE_PAX"
    };
    public static int[] fieldTypes = {
        TYPE_PK + TYPE_FK + TYPE_LONG,
        TYPE_PK + TYPE_FK + TYPE_LONG,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_INT
    };

    public PstMedicalBudget() {
    }

    public PstMedicalBudget(int i) throws DBException {
        super(new PstMedicalBudget());

    }

    public PstMedicalBudget(String sOid) throws DBException {
        super(new PstMedicalBudget(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }

    }

    public PstMedicalBudget(long lOid, long lOid1) throws DBException {
        super(new PstMedicalBudget(0));
        if (!locate(lOid, lOid1)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    /**
     *	Implemanting I_Entity interface methods
     */
    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {

        return TBL_MEDICAL_BUDGET;

    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstMedicalBudget().getClass().getName();
    }

    /**
     *	Implemanting I_DBInterface interface methods
     */
    public long fetchExc(Entity ent) throws DBException {
        MedicalBudget medBudget = PstMedicalBudget.fetchExc(ent.getOID(0), ent.getOID(1));
        ent = (Entity) medBudget;
        return medBudget.getMedicalLevelId();
    }

    public long insertExc(Entity ent) throws DBException {
        return PstMedicalBudget.insertExc((MedicalBudget) ent);

    }

    public long updateExc(Entity ent) throws DBException {
        return updateExc((MedicalBudget) ent);
    }

    public long deleteExc(Entity ent) {
        return deleteExc((MedicalBudget) ent);
    }

    public static MedicalBudget fetchExc(long medLevelId, long medCaseId) throws DBException {
        MedicalBudget medBudget = new MedicalBudget();
        try {
            PstMedicalBudget pstMedicalBudget = new PstMedicalBudget(medLevelId, medCaseId);
            medBudget.setMedicalCaseId(medCaseId);
            medBudget.setMedicalLevelId(medLevelId);
            medBudget.setBudget(pstMedicalBudget.getdouble(FLD_BUDGET));
            medBudget.setUsePeriod(pstMedicalBudget.getInt(FLD_USE_PERIOD));
            medBudget.setUsePax(pstMedicalBudget.getInt(FLD_USE_PAX));
        } catch (DBException e) {
            System.out.println(e);
        }
        return medBudget;
    }

    public static long insertExc(MedicalBudget medBudget) throws DBException {
        try {
            PstMedicalBudget pstMedicalBudget = new PstMedicalBudget(0);
            pstMedicalBudget.setLong(FLD_MEDICAL_LEVEL_ID, medBudget.getMedicalLevelId());
            pstMedicalBudget.setLong(FLD_MEDICAL_CASE_ID, medBudget.getMedicalCaseId());
            pstMedicalBudget.setDouble(FLD_BUDGET, medBudget.getBudget());
            pstMedicalBudget.setInt(FLD_USE_PERIOD, medBudget.getUsePeriod());
            pstMedicalBudget.setInt(FLD_USE_PAX, medBudget.getUsePax());
            pstMedicalBudget.insert();
            return medBudget.getMedicalCaseId();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMedicalCase(0), DBException.UNKNOWN);
        }
    }

    /**
     * 
     * @param oid = medical level ID
     * @param oid1 = medical case ID
     * @return
     */
    public static long deleteExc(long oid, long oid1) throws DBException {
        try {
            PstMedicalBudget pstMedicalBudget = new PstMedicalBudget(oid, oid1);
            pstMedicalBudget.delete();
            return oid;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMedicalCase(0), DBException.UNKNOWN);
        }
    }

    public static long updateExc(MedicalBudget medBudget) throws DBException {
        if (medBudget.getMedicalLevelId() != 0 && medBudget.getMedicalCaseId() != 0) {
            try {
                PstMedicalBudget pstMedicalBudget = new PstMedicalBudget(medBudget.getMedicalLevelId(), medBudget.getMedicalCaseId());
                pstMedicalBudget.setLong(FLD_MEDICAL_LEVEL_ID, medBudget.getMedicalLevelId());
                pstMedicalBudget.setLong(FLD_MEDICAL_CASE_ID, medBudget.getMedicalCaseId());
                pstMedicalBudget.setDouble(FLD_BUDGET, medBudget.getBudget());
                pstMedicalBudget.setInt(FLD_USE_PERIOD, medBudget.getUsePeriod());
                pstMedicalBudget.setInt(FLD_USE_PAX, medBudget.getUsePax());
                pstMedicalBudget.update();
                return medBudget.getMedicalLevelId();
            } catch (DBException dbe) {
                throw dbe;
            } catch (Exception e) {
                throw new DBException(new PstMedicalCase(0), DBException.UNKNOWN);
            }
        }
        return 0;
    }

    public static Vector listAll() {
        return list(0, 0, null, null);
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_MEDICAL_BUDGET;
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
                MedicalBudget medicalBudget = new MedicalBudget();
                resultToObject(rs, medicalBudget);
                lists.add(medicalBudget);
            }
            rs.close();

        } catch (Exception e) {
            System.out.println();
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

    private static void resultToObject(ResultSet rs, MedicalBudget medicalBudget) {
        try {
            medicalBudget.setMedicalLevelId(rs.getLong(PstMedicalBudget.fieldNames[PstMedicalBudget.FLD_MEDICAL_LEVEL_ID]));
            medicalBudget.setMedicalCaseId(rs.getLong(PstMedicalBudget.fieldNames[PstMedicalBudget.FLD_MEDICAL_CASE_ID]));
            medicalBudget.setBudget(rs.getFloat(PstMedicalBudget.fieldNames[PstMedicalBudget.FLD_BUDGET]));
            medicalBudget.setUsePeriod(rs.getInt(PstMedicalBudget.fieldNames[PstMedicalBudget.FLD_USE_PERIOD]));
            medicalBudget.setUsePax(rs.getInt(PstMedicalBudget.fieldNames[PstMedicalBudget.FLD_USE_PAX]));
        } catch (Exception e) {
        }
    }

    public static long deleteByMedicalLevel(long oid) {

        try {
            String sql = "DELETE FROM " + TBL_MEDICAL_BUDGET +
                    " WHERE " + fieldNames[FLD_MEDICAL_LEVEL_ID] + " = " + oid;
            int status = DBHandler.execUpdate(sql);
        } catch (Exception e) {
            System.out.println("Err : " + e.toString());
            oid = 0;
        }

        return oid;
    }

    public static long deleteByMedicalCase(long oid) {

        try {
            String sql = "DELETE FROM " + TBL_MEDICAL_BUDGET +
                    " WHERE " + fieldNames[FLD_MEDICAL_CASE_ID] + " = " + oid;
            DBHandler.execUpdate(sql);
        } catch (Exception e) {
            System.out.println("Err : " + e.toString());
            oid = 0;
        }

        return oid;
    }

    public static boolean checkOID(long medicalLevelId, long medicalCaseId) {
        DBResultSet dbrs = null;
        boolean result = false;

        try {
            String sql = "SELECT * FROM " + TBL_MEDICAL_BUDGET +
                    " WHERE " + PstMedicalBudget.fieldNames[PstMedicalBudget.FLD_MEDICAL_LEVEL_ID] +
                    " = " + medicalLevelId +
                    " AND " + PstMedicalBudget.fieldNames[PstMedicalBudget.FLD_MEDICAL_CASE_ID] +
                    " = " + medicalCaseId;

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
        }
        return result;
    }

    /**
     * 
     * @param strDel : list of Medical case ID : e.g. :  12121,23434,34343
     */
    public static void DeleteByMedCaseIds(String strDel) {
        DBResultSet dbrs = null;
        try {
            String sql = "DELETE FROM " + TBL_MEDICAL_BUDGET +
                    " WHERE " + fieldNames[FLD_MEDICAL_CASE_ID] +
                    " IN (" + strDel + ")";
            dbrs = DBHandler.execQueryResult(sql);
        } catch (Exception e) {
            System.out.println("Err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /***  function for data synchronization ***/
    public long insertExcSynch(Entity ent) throws Exception {
        return insertExcSynch((MedicalBudget) ent);
    }

    public static long insertExcSynch(MedicalBudget medBudget) throws DBException {
        try {

            insertExc(medBudget);
            return medBudget.getOID(0);

        } catch (Exception e) {

            throw new DBException(new PstMedicalBudget(0), DBException.UNKNOWN);

        }

    }
    /***************************************/
}
