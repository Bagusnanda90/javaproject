package com.dimata.harisma.entity.clinic;

/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.*;

/**
 *
 * @author bayu
 */
public class PstMedicalCase extends DBHandler implements I_DBType, I_DBInterface, I_PersintentExc, I_Language {

    public static final String TBL_HR_MEDICAL_CASE = "hr_medical_case";
    public static final int FLD_MEDICAL_CASE_ID = 0;
    public static final int FLD_SORT_NUMBER = 1;
    public static final int FLD_CASE_GROUP = 2;
    public static final int FLD_CASE_NAME = 3;
    public static final int FLD_MAX_USE = 4;
    public static final int FLD_MAX_USE_PERIOD = 5;
    public static final int FLD_MIN_TAKEN_BY = 6;
    public static final int FLD_MIN_TAKEN_BY_PERIOD = 7;
    public static final int FLD_CASE_LINK=8;
    public static final int FLD_FORMULA=9;
    public static String[] fieldNames = {
        "MEDICAL_CASE_ID",
        "SORT_NUMBER",
        "CASE_GROUP",
        "CASE_NAME",
        "MAX_USE",
        "MAX_USE_PERIOD",
        "MIN_TAKEN_BY",
        "MIN_TAKEN_BY_PERIOD",
        "CASE_LINK",
        "FORMULA"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstMedicalCase() {
    }

    public PstMedicalCase(int i) throws DBException {
        super(new PstMedicalCase());
    }

    public PstMedicalCase(String sOid) throws DBException {
        super(new PstMedicalCase(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstMedicalCase(long lOid) throws DBException {
        super(new PstMedicalCase(0));
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
        return TBL_HR_MEDICAL_CASE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstMedicalCase().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        MedicalCase medicalCase = fetchExc(ent.getOID());
        ent = (Entity) medicalCase;
        return medicalCase.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((MedicalCase) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((MedicalCase) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static MedicalCase fetchExc(long oid) throws DBException {
        try {
            MedicalCase medicalCase = new MedicalCase();
            PstMedicalCase pstMedicalCase = new PstMedicalCase(oid);
            medicalCase.setOID(oid);
            medicalCase.setSortNumber(pstMedicalCase.getInt(FLD_SORT_NUMBER));
            medicalCase.setCaseGroup(pstMedicalCase.getString(FLD_CASE_GROUP));
            medicalCase.setCaseName(pstMedicalCase.getString(FLD_CASE_NAME));
            medicalCase.setMaxUse(pstMedicalCase.getInt(FLD_MAX_USE));
            medicalCase.setMaxUsePeriod(pstMedicalCase.getInt(FLD_MAX_USE_PERIOD));
            medicalCase.setMinTakenBy(pstMedicalCase.getInt(FLD_MIN_TAKEN_BY));
            medicalCase.setMinTakenByPeriod(pstMedicalCase.getInt(FLD_MIN_TAKEN_BY_PERIOD));
            medicalCase.setCaseLink(pstMedicalCase.getString(FLD_CASE_LINK));
            medicalCase.setFormula(pstMedicalCase.getString(FLD_FORMULA));
            return medicalCase;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMedicalCase(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(MedicalCase medicalCase) throws DBException {
        try {
            PstMedicalCase pstMedicalCase = new PstMedicalCase(0);

            pstMedicalCase.setInt(FLD_SORT_NUMBER, medicalCase.getSortNumber());
            pstMedicalCase.setString(FLD_CASE_GROUP, medicalCase.getCaseGroup());
            pstMedicalCase.setString(FLD_CASE_NAME, medicalCase.getCaseName());
            pstMedicalCase.setInt(FLD_MAX_USE, medicalCase.getMaxUse());
            pstMedicalCase.setInt(FLD_MAX_USE_PERIOD, medicalCase.getMaxUsePeriod());
            pstMedicalCase.setInt(FLD_MIN_TAKEN_BY, medicalCase.getMinTakenBy());
            pstMedicalCase.setInt(FLD_MIN_TAKEN_BY_PERIOD, medicalCase.getMinTakenByPeriod());
            pstMedicalCase.setString(FLD_CASE_LINK,medicalCase.getCaseLink());
            pstMedicalCase.setString(FLD_FORMULA, medicalCase.getFormula());
            pstMedicalCase.insert();
            medicalCase.setOID(pstMedicalCase.getlong(FLD_MEDICAL_CASE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMedicalCase(0), DBException.UNKNOWN);
        }
        return medicalCase.getOID();
    }

    public static long updateExc(MedicalCase medicalCase) throws DBException {
        try {
            if (medicalCase.getOID() != 0) {
                PstMedicalCase pstMedicalCase = new PstMedicalCase(medicalCase.getOID());

                pstMedicalCase.setInt(FLD_SORT_NUMBER, medicalCase.getSortNumber());
                pstMedicalCase.setString(FLD_CASE_GROUP, medicalCase.getCaseGroup());
                pstMedicalCase.setString(FLD_CASE_NAME, medicalCase.getCaseName());
                pstMedicalCase.setInt(FLD_MAX_USE, medicalCase.getMaxUse());
                pstMedicalCase.setInt(FLD_MAX_USE_PERIOD, medicalCase.getMaxUsePeriod());
                pstMedicalCase.setInt(FLD_MIN_TAKEN_BY, medicalCase.getMinTakenBy());
                pstMedicalCase.setInt(FLD_MIN_TAKEN_BY_PERIOD, medicalCase.getMinTakenByPeriod());
                pstMedicalCase.setString(FLD_CASE_LINK, medicalCase.getCaseLink());
                pstMedicalCase.setString(FLD_FORMULA, medicalCase.getFormula());
                pstMedicalCase.update();
                return medicalCase.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMedicalCase(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstMedicalCase pstMedicalCase = new PstMedicalCase(oid);
            pstMedicalCase.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMedicalCase(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }
    
    public static Vector listByCaseLink(String caseLink){
        String whereClause =" "+fieldNames[FLD_CASE_LINK]+"='"+caseLink+"' ";
        return list(0,100,whereClause,"");
    }

    public static Vector listByCaseLink(long caseId){
        try{
        MedicalCase  medCase = fetchExc(caseId);
        String whereClause =" "+fieldNames[FLD_CASE_LINK]+"='"+medCase.getCaseLink()+"' ";
            return list(0,100,whereClause,"");
        } catch(Exception exc){
            System.out.println (exc);
            return new Vector();
        }
    }
    
    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_MEDICAL_CASE;
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
                MedicalCase medicalCase = new MedicalCase();
                resultToObject(rs, medicalCase);
                lists.add(medicalCase);
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

    private static void resultToObject(ResultSet rs, MedicalCase medicalCase) {
        try {
            medicalCase.setOID(rs.getLong(PstMedicalCase.fieldNames[PstMedicalCase.FLD_MEDICAL_CASE_ID]));
            medicalCase.setSortNumber(rs.getInt(PstMedicalCase.fieldNames[PstMedicalCase.FLD_SORT_NUMBER]));
            medicalCase.setCaseGroup(rs.getString(PstMedicalCase.fieldNames[FLD_CASE_GROUP]));
            medicalCase.setCaseName(rs.getString(PstMedicalCase.fieldNames[FLD_CASE_NAME]));
            medicalCase.setMaxUse(rs.getInt(PstMedicalCase.fieldNames[FLD_MAX_USE]));
            medicalCase.setMaxUsePeriod(rs.getInt(PstMedicalCase.fieldNames[FLD_MAX_USE_PERIOD]));
            medicalCase.setMinTakenBy(rs.getInt(PstMedicalCase.fieldNames[FLD_MIN_TAKEN_BY]));
            medicalCase.setMinTakenByPeriod(rs.getInt(PstMedicalCase.fieldNames[FLD_MIN_TAKEN_BY_PERIOD]));
            medicalCase.setCaseLink(rs.getString(PstMedicalCase.fieldNames[FLD_CASE_LINK]));
            medicalCase.setFormula(rs.getString(PstMedicalCase.fieldNames[FLD_FORMULA]));            
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long medicalRecordId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_MEDICAL_CASE + " WHERE " +
                    PstMedicalCase.fieldNames[PstMedicalCase.FLD_MEDICAL_CASE_ID] + " = " + medicalRecordId;

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
            String sql = "SELECT COUNT(" + PstMedicalCase.fieldNames[PstMedicalCase.FLD_MEDICAL_CASE_ID] + ") FROM " + TBL_HR_MEDICAL_CASE;
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
                    MedicalCase medicalCase = (MedicalCase) list.get(ls);
                    if (oid == medicalCase.getOID()) {
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
