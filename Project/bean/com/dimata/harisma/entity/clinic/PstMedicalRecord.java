/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
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



/* package harisma */

//import com.dimata.harisma.db.DBHandler;

//import com.dimata.harisma.db.DBException;

//import com.dimata.harisma.db.DBLogger;

import com.dimata.harisma.entity.clinic.*;

import com.dimata.util.*;

public class PstMedicalRecord extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_MEDICAL_RECORD = "hr_medical_record";//"HR_MEDICAL_RECORD";
    public static final int FLD_MEDICAL_RECORD_ID = 0;
    public static final int FLD_FAMILY_MEMBER_ID = 1;
    public static final int FLD_DISEASE_TYPE_ID = 2;
    public static final int FLD_MEDICAL_TYPE_ID = 3;
    public static final int FLD_EMPLOYEE_ID = 4;
    public static final int FLD_RECORD_DATE = 5;
    public static final int FLD_AMOUNT = 6;
    public static final int FLD_DISCOUNT_IN_PERCENT = 7;
    public static final int FLD_DISCOUNT_IN_RP = 8;
    public static final int FLD_TOTAL = 9;
    public static final int FLD_MEDICAL_CASE_ID = 10;
    public static final int FLD_CASE_QUANTITY=11;
    public static final String[] fieldNames = {
        "MEDICAL_RECORD_ID",
        "FAMILY_MEMBER_ID",
        "DISEASE_TYPE_ID",
        "MEDICAL_TYPE_ID",
        "EMPLOYEE_ID",
        "RECORD_DATE",
        "AMOUNT",
        "DISCOUNT_IN_PERCENT",
        "DISCOUNT_IN_RP",
        "TOTAL",
        "MEDICAL_CASE_ID",
        "CASE_QUANTITY"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_FLOAT
    };

    public PstMedicalRecord() {

    }

    public PstMedicalRecord(int i) throws DBException {

        super(new PstMedicalRecord());

    }

    public PstMedicalRecord(String sOid) throws DBException {

        super(new PstMedicalRecord(0));

        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }

    }

    public PstMedicalRecord(long lOid) throws DBException {

        super(new PstMedicalRecord(0));

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

        return TBL_HR_MEDICAL_RECORD;

    }

    public String[] getFieldNames() {

        return fieldNames;

    }

    public int[] getFieldTypes() {

        return fieldTypes;

    }

    public String getPersistentName() {

        return new PstMedicalRecord().getClass().getName();

    }

    public long fetchExc(Entity ent) throws Exception {

        MedicalRecord medicalrecord = fetchExc(ent.getOID());

        ent = (Entity) medicalrecord;

        return medicalrecord.getOID();

    }

    public long insertExc(Entity ent) throws Exception {

        return insertExc((MedicalRecord) ent);

    }

    public long updateExc(Entity ent) throws Exception {

        return updateExc((MedicalRecord) ent);

    }

    public long deleteExc(Entity ent) throws Exception {

        if (ent == null) {

            throw new DBException(this, DBException.RECORD_NOT_FOUND);

        }

        return deleteExc(ent.getOID());

    }

    public static MedicalRecord fetchExc(long oid) throws DBException {

        try {

            MedicalRecord medicalrecord = new MedicalRecord();

            PstMedicalRecord pstMedicalRecord = new PstMedicalRecord(oid);

            medicalrecord.setOID(oid);



            medicalrecord.setFamilyMemberId(pstMedicalRecord.getlong(FLD_FAMILY_MEMBER_ID));

            medicalrecord.setDiseaseTypeId(pstMedicalRecord.getlong(FLD_DISEASE_TYPE_ID));

            medicalrecord.setMedicalTypeId(pstMedicalRecord.getlong(FLD_MEDICAL_TYPE_ID));

            medicalrecord.setEmployeeId(pstMedicalRecord.getlong(FLD_EMPLOYEE_ID));

            medicalrecord.setRecordDate(pstMedicalRecord.getDate(FLD_RECORD_DATE));

            medicalrecord.setAmount(pstMedicalRecord.getdouble(FLD_AMOUNT));

            medicalrecord.setDiscountInPercent(pstMedicalRecord.getdouble(FLD_DISCOUNT_IN_PERCENT));

            medicalrecord.setDiscountInRp(pstMedicalRecord.getdouble(FLD_DISCOUNT_IN_RP));

            medicalrecord.setTotal(pstMedicalRecord.getdouble(FLD_TOTAL));
            medicalrecord.setMedicalCaseId(pstMedicalRecord.getlong(FLD_MEDICAL_CASE_ID));
            medicalrecord.setCaseQuantity(pstMedicalRecord.getdouble(FLD_CASE_QUANTITY));


            return medicalrecord;

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstMedicalRecord(0), DBException.UNKNOWN);

        }

    }

    public static long insertExc(MedicalRecord medicalrecord) throws DBException {

        try {

            PstMedicalRecord pstMedicalRecord = new PstMedicalRecord(0);



            pstMedicalRecord.setLong(FLD_FAMILY_MEMBER_ID, medicalrecord.getFamilyMemberId());

            pstMedicalRecord.setLong(FLD_DISEASE_TYPE_ID, medicalrecord.getDiseaseTypeId());

            pstMedicalRecord.setLong(FLD_MEDICAL_TYPE_ID, medicalrecord.getMedicalTypeId());

            pstMedicalRecord.setLong(FLD_EMPLOYEE_ID, medicalrecord.getEmployeeId());

            pstMedicalRecord.setDate(FLD_RECORD_DATE, medicalrecord.getRecordDate());

            pstMedicalRecord.setDouble(FLD_AMOUNT, medicalrecord.getAmount());

            pstMedicalRecord.setDouble(FLD_DISCOUNT_IN_PERCENT, medicalrecord.getDiscountInPercent());

            pstMedicalRecord.setDouble(FLD_DISCOUNT_IN_RP, medicalrecord.getDiscountInRp());

            pstMedicalRecord.setDouble(FLD_TOTAL, medicalrecord.getTotal());

            pstMedicalRecord.setLong(FLD_MEDICAL_CASE_ID, medicalrecord.getMedicalCaseId());
            pstMedicalRecord.setDouble(FLD_CASE_QUANTITY, medicalrecord.getCaseQuantity());
            
            pstMedicalRecord.insert();

            medicalrecord.setOID(pstMedicalRecord.getlong(FLD_MEDICAL_RECORD_ID));

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstMedicalRecord(0), DBException.UNKNOWN);

        }

        return medicalrecord.getOID();

    }

    public static long updateExc(MedicalRecord medicalrecord) throws DBException {

        try {

            if (medicalrecord.getOID() != 0) {

                PstMedicalRecord pstMedicalRecord = new PstMedicalRecord(medicalrecord.getOID());



                pstMedicalRecord.setLong(FLD_FAMILY_MEMBER_ID, medicalrecord.getFamilyMemberId());

                pstMedicalRecord.setLong(FLD_DISEASE_TYPE_ID, medicalrecord.getDiseaseTypeId());

                pstMedicalRecord.setLong(FLD_MEDICAL_TYPE_ID, medicalrecord.getMedicalTypeId());

                pstMedicalRecord.setLong(FLD_EMPLOYEE_ID, medicalrecord.getEmployeeId());

                pstMedicalRecord.setDate(FLD_RECORD_DATE, medicalrecord.getRecordDate());

                pstMedicalRecord.setDouble(FLD_AMOUNT, medicalrecord.getAmount());

                pstMedicalRecord.setDouble(FLD_DISCOUNT_IN_PERCENT, medicalrecord.getDiscountInPercent());

                pstMedicalRecord.setDouble(FLD_DISCOUNT_IN_RP, medicalrecord.getDiscountInRp());

                pstMedicalRecord.setDouble(FLD_TOTAL, medicalrecord.getTotal());

                pstMedicalRecord.setLong(FLD_MEDICAL_CASE_ID, medicalrecord.getMedicalCaseId());
                pstMedicalRecord.setDouble(FLD_CASE_QUANTITY, medicalrecord.getCaseQuantity());

                pstMedicalRecord.update();
                return medicalrecord.getOID();



            }

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstMedicalRecord(0), DBException.UNKNOWN);

        }

        return 0;

    }

    public static long deleteExc(long oid) throws DBException {

        try {

            PstMedicalRecord pstMedicalRecord = new PstMedicalRecord(oid);

            pstMedicalRecord.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstMedicalRecord(0), DBException.UNKNOWN);

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

            String sql = "SELECT * FROM " + TBL_HR_MEDICAL_RECORD;

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

                MedicalRecord medicalrecord = new MedicalRecord();

                resultToObject(rs, medicalrecord);

                lists.add(medicalrecord);

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

    private static void resultToObject(ResultSet rs, MedicalRecord medicalrecord) {

        try {

            medicalrecord.setOID(rs.getLong(PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_RECORD_ID]));

            medicalrecord.setFamilyMemberId(rs.getLong(PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_FAMILY_MEMBER_ID]));

            medicalrecord.setDiseaseTypeId(rs.getLong(PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISEASE_TYPE_ID]));

            medicalrecord.setMedicalTypeId(rs.getLong(PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID]));

            medicalrecord.setEmployeeId(rs.getLong(PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID]));

            medicalrecord.setRecordDate(rs.getDate(PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE]));

            medicalrecord.setAmount(rs.getDouble(PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_AMOUNT]));

            medicalrecord.setDiscountInPercent(rs.getDouble(PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISCOUNT_IN_PERCENT]));

            medicalrecord.setDiscountInRp(rs.getDouble(PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISCOUNT_IN_RP]));

            medicalrecord.setTotal(rs.getDouble(PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_TOTAL]));
            medicalrecord.setMedicalCaseId(rs.getLong(PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_CASE_ID]));
            medicalrecord.setCaseQuantity(rs.getDouble(PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_CASE_QUANTITY]));


        } catch (Exception e) {
        }

    }

    public static boolean checkOID(long medicalRecordId) {

        DBResultSet dbrs = null;

        boolean result = false;

        try {

            String sql = "SELECT * FROM " + TBL_HR_MEDICAL_RECORD + " WHERE " +
                    PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_RECORD_ID] + " = " + medicalRecordId;



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

            String sql = "SELECT COUNT(" + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_RECORD_ID] + ") FROM " + TBL_HR_MEDICAL_RECORD;

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

                    MedicalRecord medicalrecord = (MedicalRecord) list.get(ls);

                    if (oid == medicalrecord.getOID()) {
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

    //eka
    public static double getTotalMedicalUsed(long oidEmployee, long oidMedicalType) {



        Date startDate = new Date();

        startDate.setDate(1);

        startDate.setMonth(0);



        Date endDate = (Date) startDate.clone();

        endDate.setYear(endDate.getYear() + 1);

        endDate.setDate(endDate.getDate() - 1);



        DBResultSet dbrs = null;

        double result = 0;

        try {

            String sql = "select sum(" + fieldNames[FLD_TOTAL] + ") " +
                    "from " + TBL_HR_MEDICAL_RECORD + " where " +
                    fieldNames[FLD_EMPLOYEE_ID] + " = " + oidEmployee +
                    " and " + fieldNames[FLD_MEDICAL_TYPE_ID] + " = " + oidMedicalType +
                    " and (" + fieldNames[FLD_RECORD_DATE] + " between '" + Formater.formatDate(startDate, "yyyy-MM-dd") + "'" +
                    " and '" + Formater.formatDate(endDate, "yyyy-MM-dd") + "')";



            System.out.println(sql);

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                result = rs.getDouble(1);

            }



        } catch (Exception e) {

            System.out.println("exception e : " + e.toString());

        } finally {

            DBResultSet.close(dbrs);

        }



        return result;

    }
}

