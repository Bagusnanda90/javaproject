
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
package com.dimata.harisma.session.clinic;

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
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.clinic.*;
import com.dimata.harisma.entity.masterdata.*;

public class SessMedicalRecord {

    public static int MaxMedRecToGet = 100;
    public static final String SESS_SRC_EMPVISIT = "SESSION_SRC_EMPVISIT";
    public static final int INT_SORT_BY_REC_DATE = 0;
    public static final int INT_SORT_BY_EMPLOYEE = 1;
    public static final int INT_SORT_BY_DISEASE_TYPE = 2;
    public static final int INT_SORT_BY_MEDICAL_TYPE = 3;
    public static final String[] sortText = {
        "Record Date",
        "Employee",
        "Disease Type",
        "Medical Type"
    };

    public static Vector getKeySortMedicalBy() {
        Vector result = new Vector(1, 1);
        for (int i = 0; i < sortText.length; i++) {
            result.add("" + i);
        }
        return result;
    }

    public static Vector getValSortMedicalBy() {
        Vector result = new Vector(1, 1);
        for (int i = 0; i < sortText.length; i++) {
            result.add(sortText[i]);
        }
        return result;
    }

    private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN)) &&
                    ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }
        return vector;
    }

    public static Vector searchMedicalRecord(SrcMedicalRecord srcMedicalRecord, int start, int recordToGet) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        if (srcMedicalRecord == null) {
            return new Vector(1, 1);
        }

        try {
            String sql = " SELECT MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_RECORD_ID] +
                    " , MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID] +
                    " , MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_FAMILY_MEMBER_ID] +
                    " , MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISEASE_TYPE_ID] +
                    " , MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] +
                    " , MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] +
                    " , MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_AMOUNT] +
                    " , MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISCOUNT_IN_PERCENT] +
                    " , MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISCOUNT_IN_RP] +
                    " , MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_TOTAL] +
                    " , MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_CASE_ID] +
                    " , MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_CASE_QUANTITY] +
                    " FROM " + PstMedicalRecord.TBL_HR_MEDICAL_RECORD + " AS MR " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EM " +
                    " ON MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID] +
                    " = EM." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstDiseaseType.TBL_HR_DISEASE_TYPE + " AS DS " +
                    " ON MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISEASE_TYPE_ID] +
                    " = DS." + PstDiseaseType.fieldNames[PstDiseaseType.FLD_DISEASE_TYPE_ID] +
                    " INNER JOIN " + PstMedicalType.TBL_HR_MEDICAL_TYPE + " AS MET " +
                    " ON MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] +
                    " = MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID];

            Date startRecordDate = srcMedicalRecord.getStartDate();// null;
            /*if(srcMedicalRecord.getStartRecordDate_yr()>0 && srcMedicalRecord.getStartRecordDate_mn()>0 && srcMedicalRecord.getStartRecordDate_dy()>0){
            startRecordDate = new Date(srcMedicalRecord.getStartRecordDate_yr()-1900,srcMedicalRecord.getStartRecordDate_mn()-1,srcMedicalRecord.getStartRecordDate_dy());
            }*/
            Date endRecordDate = srcMedicalRecord.getEndDate();// null;null;
            /*if(srcMedicalRecord.getEndRecordDate_yr()>0 && srcMedicalRecord.getEndRecordDate_mn()>0 && srcMedicalRecord.getEndRecordDate_dy()>0){
            startRecordDate = new Date(srcMedicalRecord.getEndRecordDate_yr()-1900,srcMedicalRecord.getEndRecordDate_mn()-1,srcMedicalRecord.getEndRecordDate_dy());
            } */

            System.out.println("startRecordDate..... : " + startRecordDate);
            System.out.println("endRecordDate..... : " + endRecordDate);

            String whereClause = "";
            if (startRecordDate != null && endRecordDate != null) {
                whereClause = " WHERE MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] +
                        " BETWEEN \"" + Formater.formatDate(startRecordDate, "yyyy-MM-dd") + "\"" +
                        " AND \"" + Formater.formatDate(endRecordDate, "yyyy-MM-dd") + "\"";
            }

            if (srcMedicalRecord.getDepartmentId() != 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND EM." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                            " = " + srcMedicalRecord.getDepartmentId();
                } else {
                    whereClause = " WHERE EM." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                            " = " + srcMedicalRecord.getDepartmentId();
                }
            }
            
            if (srcMedicalRecord.getEmployeeId() != 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID] +
                            " = " + srcMedicalRecord.getEmployeeId();
                } else {
                    whereClause = " WHERE MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID] +
                            " = " + srcMedicalRecord.getEmployeeId();
                }
            }


            if (srcMedicalRecord.getMedicalCaseId() != 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_CASE_ID] +
                            " = " + srcMedicalRecord.getMedicalCaseId();
                } else {
                    whereClause = " WHERE MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_CASE_ID] +
                            " = " + srcMedicalRecord.getMedicalCaseId();
                }
            }

            if (srcMedicalRecord.getDiseaseTypeId() != 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISEASE_TYPE_ID] +
                            " = " + srcMedicalRecord.getDiseaseTypeId();
                } else {
                    whereClause = " WHERE MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISEASE_TYPE_ID] +
                            " = " + srcMedicalRecord.getDiseaseTypeId();
                }
            }

            if (srcMedicalRecord.getMedicalTypeId() != 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] +
                            " = " + srcMedicalRecord.getMedicalTypeId();
                } else {
                    whereClause = " WHERE MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] +
                            " = " + srcMedicalRecord.getMedicalTypeId();
                }
            }


            String orderBy = "";
            switch (srcMedicalRecord.getOrderBy()) {
                case INT_SORT_BY_REC_DATE:
                    orderBy = orderBy + " ORDER BY MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE];
                    break;
                case INT_SORT_BY_EMPLOYEE:
                    orderBy = orderBy + " ORDER BY EM." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
                case INT_SORT_BY_DISEASE_TYPE:
                    orderBy = orderBy + " ORDER BY DS." + PstDiseaseType.fieldNames[PstDiseaseType.FLD_DISEASE_TYPE];
                    break;
                case INT_SORT_BY_MEDICAL_TYPE:
                    orderBy = orderBy + " ORDER BY MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_NAME];
                    break;
            }

            if (start == 0 && recordToGet == 0) {
                sql = sql + whereClause + orderBy;
            } else {
                sql = sql + whereClause + orderBy + " LIMIT " + start + "," + recordToGet;
            }

            System.out.println("SessMedicalRecord.searchMedicalRecord() sql : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                MedicalRecord medRecord = new MedicalRecord();

                medRecord.setOID(rs.getLong(1));
                medRecord.setEmployeeId(rs.getLong(2));
                medRecord.setFamilyMemberId(rs.getLong(3));
                medRecord.setDiseaseTypeId(rs.getLong(4));
                medRecord.setMedicalTypeId(rs.getLong(5));
                medRecord.setRecordDate(rs.getDate(6));
                medRecord.setAmount(rs.getDouble(7));
                medRecord.setDiscountInPercent(rs.getDouble(8));
                medRecord.setDiscountInRp(rs.getDouble(9));
                medRecord.setTotal(rs.getDouble(10));
                medRecord.setMedicalCaseId(rs.getLong(11));
                medRecord.setCaseQuantity(rs.getDouble(12));

                result.add(medRecord);
            }
            return result;
        } catch (Exception exc) {
            System.out.println("SessMedicalRecord.searchMedicalRecord() exc : " + exc.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static int countMedicalRecord(SrcMedicalRecord srcMedicalRecord) {
        DBResultSet dbrs = null;
        int result = 0;
        if (srcMedicalRecord == null) {
            return 0;
        }

        try {
            String sql = " SELECT COUNT(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_RECORD_ID] + ") " +
                    " FROM " + PstMedicalRecord.TBL_HR_MEDICAL_RECORD + " AS MR " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EM " +
                    " ON MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID] +
                    " = EM." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstDiseaseType.TBL_HR_DISEASE_TYPE + " AS DS " +
                    " ON MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISEASE_TYPE_ID] +
                    " = DS." + PstDiseaseType.fieldNames[PstDiseaseType.FLD_DISEASE_TYPE_ID] +
                    " INNER JOIN " + PstMedicalType.TBL_HR_MEDICAL_TYPE + " AS MET " +
                    " ON MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] +
                    " = MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID];

            Date startRecordDate = srcMedicalRecord.getStartDate();//null;
            /*if(srcMedicalRecord.getStartRecordDate_yr()>0 && srcMedicalRecord.getStartRecordDate_mn()>0 && srcMedicalRecord.getStartRecordDate_dy()>0){
            startRecordDate = new Date(srcMedicalRecord.getStartRecordDate_yr()-1900,srcMedicalRecord.getStartRecordDate_mn()-1,srcMedicalRecord.getStartRecordDate_dy());
            } */
            Date endRecordDate = srcMedicalRecord.getEndDate();//null;
            /*if(srcMedicalRecord.getEndRecordDate_yr()>0 && srcMedicalRecord.getEndRecordDate_mn()>0 && srcMedicalRecord.getEndRecordDate_dy()>0){
            startRecordDate = new Date(srcMedicalRecord.getEndRecordDate_yr()-1900,srcMedicalRecord.getEndRecordDate_mn()-1,srcMedicalRecord.getEndRecordDate_dy());
            } */
            String whereClause = "";
            if (startRecordDate != null && endRecordDate != null) {
                whereClause = " WHERE MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] +
                        " BETWEEN \"" + Formater.formatDate(startRecordDate, "yyyy-MM-dd") + "\"" +
                        " AND \"" + Formater.formatDate(endRecordDate, "yyyy-MM-dd") + "\"";
            }
            
            
            if (srcMedicalRecord.getDepartmentId() != 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND EM." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                            " = " + srcMedicalRecord.getDepartmentId();
                } else {
                    whereClause = " WHERE EM." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                            " = " + srcMedicalRecord.getDepartmentId();
                }
            }
            
            if (srcMedicalRecord.getEmployeeId() != 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID] +
                            " = " + srcMedicalRecord.getEmployeeId();
                } else {
                    whereClause = " WHERE MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID] +
                            " = " + srcMedicalRecord.getEmployeeId();
                }
            }

            if (srcMedicalRecord.getMedicalCaseId() != 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_CASE_ID] +
                            " = " + srcMedicalRecord.getMedicalCaseId();
                } else {
                    whereClause = " WHERE MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_CASE_ID] +
                            " = " + srcMedicalRecord.getMedicalCaseId();
                }
            }


            if (srcMedicalRecord.getDiseaseTypeId() != 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISEASE_TYPE_ID] +
                            " = " + srcMedicalRecord.getDiseaseTypeId();
                } else {
                    whereClause = " WHERE MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISEASE_TYPE_ID] +
                            " = " + srcMedicalRecord.getDiseaseTypeId();
                }
            }

            if (srcMedicalRecord.getMedicalTypeId() != 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] +
                            " = " + srcMedicalRecord.getMedicalTypeId();
                } else {
                    whereClause = " WHERE MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] +
                            " = " + srcMedicalRecord.getMedicalTypeId();
                }
            }


            String orderBy = "";
            switch (srcMedicalRecord.getOrderBy()) {
                case INT_SORT_BY_REC_DATE:
                    orderBy = orderBy + " ORDER BY MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE];
                    break;
                case INT_SORT_BY_EMPLOYEE:
                    orderBy = orderBy + " ORDER BY EM." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
                case INT_SORT_BY_DISEASE_TYPE:
                    orderBy = orderBy + " ORDER BY DS." + PstDiseaseType.fieldNames[PstDiseaseType.FLD_DISEASE_TYPE];
                    break;
                case INT_SORT_BY_MEDICAL_TYPE:
                    orderBy = orderBy + " ORDER BY MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_NAME];
                    break;
            }

            sql = sql + whereClause + orderBy;

            System.out.println("SessMedicalRecord.countMedicalRecord() sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception exc) {
            System.out.println("SessMedicalRecord.countMedicalRecord() exc : " + exc.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static Vector getMedicalExpense(Date dtPeriod) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        try {
            String sql = " SELECT " +
                    "  MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID] +
                    ", MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_MED_EXPENSE_TYPE_ID] +
                    ", MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_CODE] +
                    ", MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_NAME] +
                    ", SUM(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISCOUNT_IN_PERCENT] + ") AS SUM_DIS_IN_PERCENT " +
                    ", SUM(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_AMOUNT] + " ) AS SUM_AMOUNT " +
                    ", SUM(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISCOUNT_IN_RP] + ") AS SUM_DIS_IN_RP " +
                    ", SUM(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_TOTAL] + ") AS SUM_TOTAL " +
                    ", COUNT(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_RECORD_ID] + ") AS PERSONS " +
                    ", MG." + PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_TYPE] +
                    " FROM " + PstMedicalRecord.TBL_HR_MEDICAL_RECORD + " MR " +
                    " , " + PstMedicalType.TBL_HR_MEDICAL_TYPE + " MET " +
                    " , " + PstMedExpenseType.TBL_HR_MEDICAL_EXPENSE_TYPE + " MG " +
                    " WHERE " +
                    " MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID] +
                    " = MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] +
                    " AND MG." + PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_MEDICINE_EXPENSE_TYPE_ID] +
                    " = MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_MED_EXPENSE_TYPE_ID] +
                    " AND ( MONTH(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] + ")" +
                    " = " + (dtPeriod.getMonth() + 1) +
                    " AND YEAR(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] + ")" +
                    " = " + (dtPeriod.getYear() + 1900) + ")" +
                    " GROUP BY MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] +
                    " ORDER BY MG." + PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_TYPE];

            System.out.println("getMedicalExpense " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector temp = new Vector(1, 1);
                MedicalType medicalType = new MedicalType();
                MedExpenseType medExpenseType = new MedExpenseType();
                MedicalRecord medicalRecord = new MedicalRecord();
                Vector person = new Vector(1, 1);

                medicalType.setOID(rs.getLong(PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID]));
                medicalType.setMedExpenseTypeId(rs.getLong(PstMedicalType.fieldNames[PstMedicalType.FLD_MED_EXPENSE_TYPE_ID]));
                medicalType.setTypeCode(rs.getString(PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_CODE]));
                medicalType.setTypeName(rs.getString(PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_NAME]));
                temp.add(medicalType);

                medExpenseType.setType(rs.getString(PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_TYPE]));
                temp.add(medExpenseType);

                medicalRecord.setAmount(rs.getDouble("SUM_AMOUNT"));
                medicalRecord.setTotal(rs.getDouble("SUM_TOTAL"));
                medicalRecord.setDiscountInPercent(rs.getDouble("SUM_DIS_IN_PERCENT"));
                medicalRecord.setDiscountInRp(rs.getDouble("SUM_DIS_IN_RP"));
                temp.add(medicalRecord);

                person.add("" + rs.getInt("PERSONS"));
                temp.add(person);

                result.add(temp);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }

    /** for get periode by start date and end date.
     * @param dtPeriodStart
     * @param dtPeriodEnd
     * @return
     */
    public static Vector getMedicalExpense(Date dtPeriodStart, Date dtPeriodEnd) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        String strPrdStartDate = Formater.formatDate(dtPeriodStart, "yyyy-MM-dd");
        String strPrdEndDate = Formater.formatDate(dtPeriodEnd, "yyyy-MM-dd");

        try {
            String sql = " SELECT " +
                    "  MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID] +
                    ", MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_MED_EXPENSE_TYPE_ID] +
                    ", MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_CODE] +
                    ", MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_NAME] +
                    ", SUM(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISCOUNT_IN_PERCENT] + ") AS SUM_DIS_IN_PERCENT " +
                    ", SUM(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_AMOUNT] + " ) AS SUM_AMOUNT " +
                    ", SUM(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_DISCOUNT_IN_RP] + ") AS SUM_DIS_IN_RP " +
                    ", SUM(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_TOTAL] + ") AS SUM_TOTAL " +
                    ", COUNT(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_RECORD_ID] + ") AS PERSONS " +
                    ", MG." + PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_TYPE] +
                    " FROM " + PstMedicalRecord.TBL_HR_MEDICAL_RECORD + " MR " +
                    " , " + PstMedicalType.TBL_HR_MEDICAL_TYPE + " MET " +
                    " , " + PstMedExpenseType.TBL_HR_MEDICAL_EXPENSE_TYPE + " MG " +
                    " WHERE " +
                    " MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID] +
                    " = MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] +
                    " AND MG." + PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_MEDICINE_EXPENSE_TYPE_ID] +
                    " = MET." + PstMedicalType.fieldNames[PstMedicalType.FLD_MED_EXPENSE_TYPE_ID] +
                    " AND MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] +
                    " BETWEEN '" + strPrdStartDate + "' AND '" + strPrdEndDate + "'" +
                    " GROUP BY MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] +
                    " ORDER BY MG." + PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_TYPE];

            System.out.println("getMedicalExpense " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector temp = new Vector(1, 1);
                MedicalType medicalType = new MedicalType();
                MedExpenseType medExpenseType = new MedExpenseType();
                MedicalRecord medicalRecord = new MedicalRecord();
                Vector person = new Vector(1, 1);

                medicalType.setOID(rs.getLong(PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID]));
                medicalType.setMedExpenseTypeId(rs.getLong(PstMedicalType.fieldNames[PstMedicalType.FLD_MED_EXPENSE_TYPE_ID]));
                medicalType.setTypeCode(rs.getString(PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_CODE]));
                medicalType.setTypeName(rs.getString(PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_NAME]));
                temp.add(medicalType);

                medExpenseType.setType(rs.getString(PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_TYPE]));
                temp.add(medExpenseType);

                medicalRecord.setAmount(rs.getDouble("SUM_AMOUNT"));
                medicalRecord.setTotal(rs.getDouble("SUM_TOTAL"));
                medicalRecord.setDiscountInPercent(rs.getDouble("SUM_DIS_IN_PERCENT"));
                medicalRecord.setDiscountInRp(rs.getDouble("SUM_DIS_IN_RP"));
                temp.add(medicalRecord);

                person.add("" + rs.getInt("PERSONS"));
                temp.add(person);

                result.add(temp);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }

    public static Vector getSummaryExpense(Date dtPeriod, long typeId) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        try {
            String sql = " SELECT " +
                    "  DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] +
                    ", SUM(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_TOTAL] + ") AS SUM_TOTAL " +
                    " FROM " + PstMedicalRecord.TBL_HR_MEDICAL_RECORD + " MR " +
                    " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP " +
                    " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEP " +
                    " WHERE " +
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " = MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID] +
                    " AND DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    " AND ( MONTH(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] + ")" +
                    " = " + (dtPeriod.getMonth() + 1) +
                    " AND YEAR(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] + ")" +
                    " = " + (dtPeriod.getYear() + 1900) + ")" +
                    " AND MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] +
                    " = " + typeId +
                    " GROUP BY MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID];

            System.out.println("getSummaryExpense " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector temp = new Vector(1, 1);

                temp.add(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                temp.add("" + (rs.getDouble("SUM_TOTAL")));

                result.add(temp);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }

    /**
     * Get list of summary of expenses link by medical cases having same set of MEDICAL CASE LINK
     * @param employeeId
     * @param caseId
     * @param startDate
     * @param endDate
     * @param existMedRec : except the existing medical records
     * @return
     */
    public static Vector getSumExpenseLinkByCase(long employeeId, long caseId, Date startDate, Date endDate, MedicalRecord existMedRec) {
        Vector vSummLinked = PstMedicalCase.listByCaseLink(caseId);
        if (vSummLinked != null && vSummLinked.size() > 0) {
            for (int i = 0; i < vSummLinked.size(); i++) {
                MedicalCase medCase = (MedicalCase) vSummLinked.get(i);
                SumEmpMedExpense sumMed = getSummaryExpense(employeeId, medCase.getOID(), startDate, endDate, existMedRec);
                if (sumMed != null) {
                    vSummLinked.add(sumMed);
                }
            }
        }
        return vSummLinked;
    }

    /**
     * 
     * @param employeeId
     * @param caseId
     * @param startDate
     * @param endDate
     * @param existMedRec : if set / OID != 0 then means get summary except di existMedRec.
     * @return
     */
    public static SumEmpMedExpense getSummaryExpense(long employeeId, long caseId, Date startDate, Date endDate, MedicalRecord existMedRec) {
        DBResultSet dbrs = null;
        SumEmpMedExpense result = new SumEmpMedExpense();

        try {
            String sql = " SELECT " +
                    "  MAX(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] + ") AS MAX_DATE" +
                    ", SUM(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_TOTAL] + ") AS SUM_TOTAL " +
                    ", SUM(MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_CASE_QUANTITY] + ") AS SUM_QTY " +
                    " FROM " + PstMedicalRecord.TBL_HR_MEDICAL_RECORD + " MR " +
                    " WHERE " +
                    " MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID] + "='" + employeeId + "'" +
                    " AND ( MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] + "" +
                    " >= '" + Formater.formatDate(startDate, "yyyy-MM-dd hh:mm:ss") + "') " +
                    " AND ( MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] + "" +
                    " <= '" + Formater.formatDate(endDate, "yyyy-MM-dd hh:mm:ss") + "') " +
                    " AND MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_CASE_ID] + "='" + caseId + "' " +
                    ((existMedRec != null && existMedRec.getOID() != 0 ? (" AND MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_RECORD_ID] + "!='" + existMedRec.getOID() + "' ") : "")) +
                    " GROUP BY MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID] +
                    " ORDER BY MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] + " DESC";

            System.out.println("getSummaryExpense by employee and case" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result.setLastTaken(rs.getDate("MAX_DATE"));
                result.setTotalAmount(rs.getDouble("SUM_TOTAL"));
                result.setTotalQty(rs.getDouble("SUM_QTY"));
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return null;
    }

    public static double getMedExpForMedTypeAndDepartment(long medTypeOID, long depOID, Date periode) {
        DBResultSet dbrs = null;
        double result = 0;
        try {

            Date start = (Date) periode.clone();
            start.setDate(1);

            Date end = (Date) periode.clone();
            end.setMonth(end.getMonth() + 1);
            end.setDate(1);
            end.setDate(end.getDate() - 1);

            String sql = "SELECT SUM(" + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_AMOUNT] + ")  FROM " + PstMedicalRecord.TBL_HR_MEDICAL_RECORD + " MED " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = MED." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID] +
                    " WHERE " +
                    " MED." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] + " = " + medTypeOID +
                    " AND MED." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] +
                    " BETWEEN \"" + Formater.formatDate(start, "yyyy-MM-dd") + "\" AND \"" + Formater.formatDate(end, "yyyy-MM-dd") + "\"" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + depOID;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getDouble(1);
            }

        } catch (Exception e) {
            System.out.println("EXC ... " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return result;
    }

    /** 
     * @param medTypeOID
     * @param depOID
     * @param start
     * @param end
     * @return
     */
    public static double getMedExpForMedTypeAndDepartment(long medTypeOID, long depOID, Date start, Date end) {
        DBResultSet dbrs = null;
        double result = 0;
        try {

            String sql = "SELECT SUM(" + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_AMOUNT] + ")  FROM " + PstMedicalRecord.TBL_HR_MEDICAL_RECORD + " MED " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = MED." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID] +
                    " WHERE " +
                    " MED." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID] + " = " + medTypeOID +
                    " AND MED." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] +
                    " BETWEEN \"" + Formater.formatDate(start, "yyyy-MM-dd") + "\" AND \"" + Formater.formatDate(end, "yyyy-MM-dd") + "\"" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + depOID;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getDouble(1);
            }

        } catch (Exception e) {
            System.out.println("EXC ... " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return result;

    }

    /**
     * Checking budget including case quantity
     * @param employeeId   : required
     * @param familymemberId : optional set =0 if employee medical record
     * @param caseId  : case / treatment Id
     * @param caseQty  : quantity of case
     * @param Amount : amount each case quantity
     * @param recDate : medical record date
     * @param startDate : beginning of medical budget check per year
     * @param endDate : ending date of medical budget check per year
     * @param startDayHrPeriod : start date of HR period per months
     * @return
     */
    public static CheckMedBudgetRslt checkMedicalBudget(long employeeId, long familyMemberId, MedicalRecord existMedRec,
            long caseId, double caseQty, double amount, Date recDate, Date startDate, Date endDate, int startDateHrPeriod) {
        CheckMedBudgetRslt chkRslt = new CheckMedBudgetRslt();

        if (employeeId == 0) {
            chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_PARAMETER_NOT_COMPLETE);
            chkRslt.setMessage(" Employee not yet selected" + ". ");
            return chkRslt;
        }

        if (recDate == null) {
            chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_PARAMETER_NOT_COMPLETE);
            chkRslt.setMessage(" Medical record date is not yet set" + ". ");
            return chkRslt;
        }

        if (startDate == null) {
            chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_PARAMETER_NOT_COMPLETE);
            chkRslt.setMessage(" HR Start date to check is not yet set" + ". ");
            return chkRslt;
        }

        if (endDate == null) {
            chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_PARAMETER_NOT_COMPLETE);
            chkRslt.setMessage(" HR End date to check is not yet set" + ". ");
            return chkRslt;
        }


        Employee emp = null;
        try {
            emp = PstEmployee.fetchExc(employeeId);
        } catch (Exception exc) {
        }

        if (emp == null) {
            chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_PARAMETER_NOT_COMPLETE);
            chkRslt.setMessage(" Employee does not exists ( employee oid=" + employeeId);
            return chkRslt;
        }


        MedicalLevel medLevel = null;
        MedicalCase medCase = null;
        Level empLevel = null;
        try {
            empLevel = PstLevel.fetchExc(emp.getLevelId());
            medCase = PstMedicalCase.fetchExc(caseId);
            if (empLevel != null && employeeId != 0 && familyMemberId != 0) { // family medical record
                medLevel = PstMedicalLevel.fetchExc(empLevel.getFamilyMedicalId());
            } else {
                medLevel = PstMedicalLevel.fetchExc(empLevel.getEmployeeMedicalId());
            }
        } catch (Exception exc) {
        }

        if (empLevel == null) {
            chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_PARAMETER_NOT_COMPLETE);
            chkRslt.setMessage(" Employee Level does not exist" + ". ");
            return chkRslt;
        }

        if (medLevel == null) {
            chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_PARAMETER_NOT_COMPLETE);
            chkRslt.setMessage(" Medical Level for " + (familyMemberId == 0 ? "employee" : "family member") + " does not exist" + ". ");
        }

        if (medCase == null) {
            chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_PARAMETER_NOT_COMPLETE);
            chkRslt.setMessage(" Medical Case does not exist" + ". ");
        }

        MedicalBudget medBudget = null;
        try {
            medBudget = PstMedicalBudget.fetchExc(medLevel.getOID(), caseId);
        } catch (Exception exc) {
        }

        if (medBudget == null) {
            chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_PARAMETER_NOT_COMPLETE);
            chkRslt.setMessage(" Medical Budget for " + (familyMemberId == 0 ? "employee" : "family member") + " does not exists" + ". ");
            return chkRslt;
        }


        SumEmpMedExpense sumMedRec = null;

        long personID = 0;
        if (familyMemberId != 0) { // this is employee  family MR
            personID = familyMemberId;
        } else {
            personID = employeeId;
        }


        long lReCDate = recDate.getTime();
        Date beginDate = null;
        Vector linkedMedRec = null;

        // get summary of medical records by  medical minimum taken periode
        switch (medCase.getMinTakenByPeriod()) {
            case MedicalCase.PERIOD_FOLLOW_HR_PERIOD: // means get base on start date to end date
                sumMedRec = getSummaryExpense(personID, caseId, startDate, endDate, existMedRec);
                break;
            case MedicalCase.PERIOD_DAY:
                beginDate = new Date(lReCDate - medCase.getMinTakenBy() * 24L * 60L * 60L * 1000L);
                sumMedRec = getSummaryExpense(personID, caseId, beginDate, endDate, existMedRec);
                break;

            case MedicalCase.PERIOD_MONTH:
                beginDate = new Date(lReCDate - medCase.getMinTakenBy() * 30L * 24L * 60L * 60L * 1000L);
                sumMedRec = getSummaryExpense(personID, caseId, beginDate, endDate, existMedRec);
                break;

            case MedicalCase.PERIOD_YEAR:
                beginDate = new Date(lReCDate - medCase.getMinTakenBy() * 365L * 24L * 60L * 60L * 1000L);
                sumMedRec = getSummaryExpense(personID, caseId, beginDate, endDate, existMedRec);
                break;

            case MedicalCase.PERIOD_TIMES:
                beginDate = new Date(lReCDate - 50L * 365L * 24L * 60L * 60L * 1000L); // set 50 year maximum working time
                sumMedRec = getSummaryExpense(personID, caseId, beginDate, endDate, existMedRec);
                break;

            default:
                sumMedRec = getSummaryExpense(personID, caseId, startDate, endDate, existMedRec); //means get base on start date to end date
        }

        // ?? cannot make hour and minutes of last taken date = medical record date, cause of depricated function setHours and set Minutes of  Date
        //recDate.setHours(12);
        //recDate.setMinutes(00);        
        //sumMedRec.getLastTaken().setHours(12);
        //sumMedRec.getLastTaken().setMinutes(00);
        if (sumMedRec != null && sumMedRec.getLastTaken() != null &&
                ((medCase.getMaxUse() > 0 && (medCase.getMaxUsePeriod() != MedicalCase.QTY_UNIT_NO_LIMIT)) ||
                (medCase.getMinTakenBy() > 0 && (medCase.getMinTakenByPeriod() != MedicalCase.PERIOD_FOLLOW_HR_PERIOD)))) {
            //if medical case has quantity maximum, check max quantity vs quantity used & date                
            if (recDate.getTime() < sumMedRec.getLastTaken().getTime()) {
                // last taken date newest then med rec date
                chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_PARAMETER_NOT_COMPLETE);
                chkRslt.setMessage(" Medical Record Date " + Formater.formatDate(recDate, "dd-MM-yyyy") +
                        " is older then last taken date " + Formater.formatDate(sumMedRec.getLastTaken(), "dd-MM-yyyy") + ". ");
                return chkRslt;
            }

            switch (medCase.getMinTakenByPeriod()) {
                case MedicalCase.PERIOD_DAY:
                    int diffDays = (int) ((recDate.getTime() - sumMedRec.getLastTaken().getTime()) / (1000L * 60L * 60L * 24L));
                    if (medCase.getMinTakenBy() > diffDays) {
                        // minimum taken days distance not fullfilled
                        chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_CASE_OVER_QTY);
                        chkRslt.setMessage(" Taken days difference is " + diffDays + " days exceeds minimum distance between taken days  of " +
                                medCase.getMinTakenBy() + " " + medCase.PeriodTitle[medCase.PERIOD_DAY] + ". ");
                        return chkRslt;
                    }
                    break;
                case MedicalCase.PERIOD_MONTH:
                    int monthDiff = 0;
                    if (recDate.getYear() == sumMedRec.getLastTaken().getYear()) {
                        monthDiff = recDate.getMonth() - sumMedRec.getLastTaken().getMonth();
                    } else {
                        int yearDiff = recDate.getYear() - sumMedRec.getLastTaken().getYear();
                        monthDiff = yearDiff * 12 - (sumMedRec.getLastTaken().getMonth() + 1) + (recDate.getMonth() + 1);
                    }

                    if (medCase.getMinTakenBy() > monthDiff) {
                        // minimum taken months distance not fullfilled
                        chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_CASE_OVER_QTY);
                        chkRslt.setMessage(" Taken month(s) difference is  " + monthDiff + " month(s) exceeds minimum distance between taken month(s)  of " +
                                medCase.getMinTakenBy() + " " + MedicalCase.PeriodTitle[MedicalCase.PERIOD_MONTH] + ". ");
                        return chkRslt;
                    }
                    break;

                case MedicalCase.PERIOD_YEAR:
                    int yearDiff = recDate.getYear() - sumMedRec.getLastTaken().getYear();
                    if (medCase.getMinTakenBy() > yearDiff) {
                        // minimum taken year distance not fullfilled
                        chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_CASE_OVER_QTY);
                        chkRslt.setMessage(" Taken year(s) difference is  " + yearDiff + " year(s) exceeds minimum distance between taken year(s)  of " +
                                medCase.getMinTakenBy() + " " + MedicalCase.PeriodTitle[MedicalCase.PERIOD_MONTH] + ". ");
                        return chkRslt;
                    }
                    break;

                case MedicalCase.PERIOD_TIMES:
                    beginDate = new Date(lReCDate - 50L * 365L * 24L * 60L * 60L * 1000L); // set 50 year maximum working time                    
                    String whereStr = " WHERE " +
                            " MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID] + "='" + employeeId + "'" +
                            " AND ( MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] + "" +
                            " >= '" + Formater.formatDate(beginDate, "yyyy-MM-dd hh:mm:ss") + "') " +
                            " AND ( MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] + "" +
                            " <= '" + Formater.formatDate(endDate, "yyyy-MM-dd hh:mm:ss") + "') " +
                            " AND MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_CASE_ID] + "='" + caseId + "'" +
                            " GROUP BY MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE];
                    String orderStr = "MR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] + " DESC";

                    Vector listMedRec = PstMedicalRecord.list(0, MaxMedRecToGet, whereStr, orderStr);
                    int listNum = 0;
                    if (listMedRec != null) {
                        listNum = listMedRec.size();
                    }
                    if (medCase.getMinTakenBy() < (listNum + 1)) {
                        // number of times taken is already maximum
                        chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_CASE_OVER_QTY);
                        chkRslt.setMessage(" Taken times of " + listNum + 1 + " time(s) exceeds maximum times of " +
                                medCase.getMinTakenBy() + " " + MedicalCase.PeriodTitle[MedicalCase.PERIOD_TIMES] + ". ");
                        return chkRslt;
                    }
                    break;
                default:
                    ;
            }
        }


        // get last date, amount and quantity of the budget used between stardate and enddate
        if (medCase.getMaxUsePeriod() != MedicalCase.QTY_UNIT_NO_LIMIT && medCase.getMaxUse() < (sumMedRec.getTotalQty() + caseQty)) {
            // over quantity
            chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_CASE_OVER_QTY);
            chkRslt.setMessage(" Quantity (" + (sumMedRec.getTotalQty() + caseQty) + ") exceeds maximum medical case quantity (" + medCase.getMaxUse() + ")" + ". ");
            return chkRslt;
        }

        // compare the budget used between stardate or begin date and enddate         
        Date endBudgetGet = null;
        switch (medBudget.getUsePeriod()) {
            case MedicalBudget.PERIOD_DAY:
                if (amount > medBudget.getBudget()) {
                    chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_OVER_BUDGET);
                    chkRslt.setMessage(" Amount (" + Formater.formatNumber(amount, "###,###.#") + ") exceeds maximum medical budget of " + Formater.formatNumber(medBudget.getBudget(), "###,###.#") +
                            " per " + MedicalBudget.PeriodTitle[MedicalBudget.PERIOD_DAY] + ". ");
                    return chkRslt;
                }
                break;
            case MedicalBudget.PERIOD_MONTH:
                if (startDateHrPeriod > recDate.getDate()) {
                    beginDate = new Date(recDate.getTime());
                    if (recDate.getMonth() == 0) { // it's januari 
                        beginDate.setMonth(11); // set beginn date to december 
                    } else {
                        beginDate.setMonth(recDate.getMonth() - 1); // set previous months
                    }
                    beginDate.setDate(startDateHrPeriod);
                    endBudgetGet = new Date(recDate.getTime());
                    endBudgetGet.setDate(startDateHrPeriod - 1);
                } else {
                    if (startDateHrPeriod == 1) { // if HR period start on 1st date every month
                        beginDate = new Date(recDate.getTime());
                        beginDate.setDate(startDateHrPeriod);
                        // set end budget to last date on the months                         
                        endBudgetGet = new Date(recDate.getTime());
                        if (recDate.getMonth() == 11) { // it's december
                            endBudgetGet.setMonth(1); // set beginn date to next year
                            endBudgetGet.setYear(recDate.getYear() + 1);
                        } else {
                            endBudgetGet.setMonth(recDate.getMonth() + 1); // set next months
                        }
                        endBudgetGet = new Date(endBudgetGet.getTime() - 24L * 60L * 60L * 1000L); // set to the last months of the period of medical record
                    } else {
                        beginDate = new Date(recDate.getTime());
                        beginDate.setDate(startDateHrPeriod);
                        // set end budget end date next months
                        endBudgetGet = new Date(recDate.getTime());
                        if (recDate.getMonth() == 11) { // it's december
                            endBudgetGet.setMonth(1); // set beginn date to next year
                            endBudgetGet.setYear(recDate.getYear() + 1);
                        } else {
                            endBudgetGet.setMonth(recDate.getMonth() + 1); // set next months
                        }
                        endBudgetGet.setDate(startDateHrPeriod - 1);
                    }
                }

                sumMedRec = getSummaryExpense(personID, caseId, beginDate, endBudgetGet, existMedRec);
                if ((caseQty * amount + sumMedRec.getTotalAmount()) > medBudget.getBudget()) {
                    chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_OVER_BUDGET);
                    chkRslt.setMessage(" New total amount of " + Formater.formatNumber(caseQty * amount + sumMedRec.getTotalAmount(), "###,###.##") + " exceeds maximum medical budget of " + Formater.formatNumber(medBudget.getBudget(), "###,###.##") +
                            " per " + MedicalBudget.PeriodTitle[MedicalBudget.PERIOD_MONTH] + ". ");
                    return chkRslt;
                }
                break;

            case MedicalBudget.PERIOD_YEAR: // assumsion set periode to none is same like per year periode of HR
            case MedicalBudget.PERIOD_NONE: //                
                sumMedRec = getSummaryExpense(personID, caseId, startDate, endDate, existMedRec);
                if ((caseQty * amount + sumMedRec.getTotalAmount()) > medBudget.getBudget()) {
                    chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_OVER_BUDGET);
                    chkRslt.setMessage(" New total amount of " + Formater.formatNumber(caseQty * amount + sumMedRec.getTotalAmount(), "###,###.##") + " exceeds maximum medical budget of " + Formater.formatNumber(medBudget.getBudget(), "###,###.##") +
                            " per " + MedicalBudget.PeriodTitle[MedicalBudget.PERIOD_YEAR] + ". ");
                    return chkRslt;
                }
                break;

            case MedicalBudget.PERIOD_IN_TOTAL:
                beginDate = new Date(lReCDate - 50L * 365L * 24L * 60L * 60L * 1000L); // set 50 year maximum working time
                sumMedRec = getSummaryExpense(personID, caseId, beginDate, endDate, existMedRec);
                if ((caseQty * amount + sumMedRec.getTotalAmount()) > medBudget.getBudget()) {
                    chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_OVER_BUDGET);
                    chkRslt.setMessage(" New total amount of " + Formater.formatNumber(caseQty * amount + sumMedRec.getTotalAmount(), "###,###.##") + " exceeds maximum medical budget of " + Formater.formatNumber(medBudget.getBudget(), "###,###.##") +
                            " per " + MedicalBudget.PeriodTitle[MedicalBudget.PERIOD_IN_TOTAL] + ". ");
                    return chkRslt;
                }
                break;
        }

        chkRslt.setErrCode(CheckMedBudgetRslt.RESULT_OK);
        chkRslt.setMessage(" New total amount of " + Formater.formatNumber(caseQty * amount, "###,###.##") + " approved . Total amount will be " + Formater.formatNumber((caseQty * amount) + sumMedRec.getTotalAmount(), "###,###.##") + ". ");

        return chkRslt;
    }

    public static void main2(String[] a) {
        Date s = new Date();
        Date e = new Date();
        s.setYear(108);
        MedicalRecord existMedRec = new MedicalRecord();

        SumEmpMedExpense sum = SessMedicalRecord.getSummaryExpense(100071L,
                504404393654191614L, s, e, existMedRec);
        System.out.println(sum.getTotalAmount());
    }

    public static void main(String[] params) {

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setEmployeeId(100071);
        medicalRecord.setMedicalCaseId(504404393654191614L);
        medicalRecord.setCaseQuantity(504404393654276255L);
        medicalRecord.setAmount(200000);
        medicalRecord.setCaseQuantity(2);
        medicalRecord.setRecordDate(new Date());

        String whereStr = " ( " + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + "" +
                " <= '" + Formater.formatDate(medicalRecord.getRecordDate(), "yyyy-MM-dd hh:mm:ss") + "') " +
                " AND ( " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + "" +
                " <= '" + Formater.formatDate(medicalRecord.getRecordDate(), "yyyy-MM-dd hh:mm:ss") + "') ";

        String orderStr = " " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " DESC";

        Vector listHRPeriod = PstPeriod.list(0, 1, whereStr, orderStr);
        Period perHr = null;
        Date hrMedStart = null;
        Date hrMedEnd = null;

        if (listHRPeriod != null && listHRPeriod.size() > 0) {
            perHr = (Period) listHRPeriod.get(0);
            hrMedStart = perHr.getStartDate();
            hrMedEnd = perHr.getEndDate();
        } else {
            System.out.println("No periode");
            return;
        }

        MedicalRecord medRec = new MedicalRecord();
        CheckMedBudgetRslt chkMed = SessMedicalRecord.checkMedicalBudget(medicalRecord.getEmployeeId(), medicalRecord.getFamilyMemberId(),
                medRec,
                medicalRecord.getMedicalCaseId(), medicalRecord.getCaseQuantity(), medicalRecord.getAmount(), medicalRecord.getRecordDate(),
                hrMedStart, hrMedEnd, hrMedStart.getDate());

        System.out.println(chkMed.getMessage());
        return;

    }
}
