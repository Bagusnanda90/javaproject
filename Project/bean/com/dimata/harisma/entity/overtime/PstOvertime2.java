/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.overtime;

//package com.dimata.harisma.entity.payroll;

/* package java */
import java.util.Date;
import java.util.Vector;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
import java.sql.ResultSet;

/* package qdep */

import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Wiweka
 */
public class PstOvertime2 extends DBHandler implements I_DBType, I_Language, I_DBInterface, I_PersintentExc {

    public static final String TBL_OVERTIME = "hr_overtime";
    public static final int FLD_OVERTIME_ID = 0;
    public static final int FLD_REQ_DATE = 1;
    public static final int FLD_OV_NUMBER = 2;
    public static final int FLD_OBJECTIVE = 3;
    public static final int FLD_STATUS_DOC = 4;
    public static final int FLD_CUSTOMER_TASK_ID = 5;
    public static final int FLD_LOGBOOK_ID = 6;
    public static final int FLD_REQ_ID = 7;
    public static final int FLD_APPROVAL_ID = 8;
    public static final int FLD_ACK_ID = 9;
    public static final int FLD_COMPANY_ID = 10;
    public static final int FLD_DIVISION_ID = 11;
    public static final int FLD_DEPARTMENT_ID = 12;
    public static final int FLD_SECTION_ID = 13;
    public static final String[] fieldNames = {
        "OVERTIME_ID", "REQ_ID",
        "OV_NUMBER", "OBJECTIVE", "STATUS_DOC",
        "CUSTOMER_TASK_ID", "LOGBOOK_ID",
        "REQ_ID", "APPROVAL_ID",
        "ACK_ID", "COMPANY_ID",
        "DIVISION_ID", "DEPARTMENT_ID",
        "SECTION_ID"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG
    };
    public static final int DRAFT = 1;
    public static final int APPROVE = 2;
    public static final int FINAL = 3;
    public static final String[] statusNames = {
        "Draft", "To Be Approval", "Final"
    };

    public static Vector getLevelKeys() {
        Vector keys = new Vector();

        for (int i = 0; i < statusNames.length; i++) {
            keys.add(statusNames[i]);
        }

        return keys;
    }

    public static Vector getLevelValues() {
        Vector values = new Vector();

        for (int i = 0; i < statusNames.length; i++) {
            values.add(String.valueOf(i));
        }

        return values;
    }

    public PstOvertime2() {
    }

    public PstOvertime2(int i) throws DBException {
        super(new PstOvertime2());
    }

    public PstOvertime2(String sOid) throws DBException {
        super(new PstOvertime2(0));

        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstOvertime2(long lOid) throws DBException {
        super(new PstOvertime2(0));
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
        return TBL_OVERTIME;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return this.getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        Overtime overtime = fetchExc(ent.getOID());
        ent = (Entity) overtime;
        return overtime.getOID();
    }

    public long updateExc(Entity ent) throws Exception {
        return insertExc((Overtime) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        return updateExc((Overtime) ent);
    }

    public long insertExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static Overtime fetchExc(long oid) throws DBException {
        try {
            Overtime overtime = new Overtime();
            PstOvertime2 pstOvertime = new PstOvertime2(oid);
            overtime.setOID(oid);

            overtime.setRequestDate(pstOvertime.getDate(FLD_REQ_DATE));
            overtime.setOvertimeNum(pstOvertime.getString(FLD_OV_NUMBER));
            overtime.setObjective(pstOvertime.getString(FLD_OBJECTIVE));
            overtime.setStatusDoc(pstOvertime.getInt(FLD_STATUS_DOC));
            overtime.setCustomerTaskId(pstOvertime.getlong(FLD_CUSTOMER_TASK_ID));
            overtime.setLogbookId(pstOvertime.getlong(FLD_LOGBOOK_ID));
            overtime.setRequestId(pstOvertime.getlong(FLD_REQ_ID));
            overtime.setApprovalId(pstOvertime.getlong(FLD_APPROVAL_ID));
            overtime.setAckId(pstOvertime.getlong(FLD_ACK_ID));
            overtime.setCompanyId(pstOvertime.getlong(FLD_COMPANY_ID));
            overtime.setDivisionId(pstOvertime.getlong(FLD_DIVISION_ID));
            overtime.setDepartmentId(pstOvertime.getlong(FLD_DEPARTMENT_ID));
            overtime.setSectionId(pstOvertime.getlong(FLD_SECTION_ID));



            return overtime;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvertime2(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(Overtime overtime) throws DBException {
        try {
            PstOvertime2 pstOvertime = new PstOvertime2(0);

            pstOvertime.setDate(FLD_REQ_DATE, overtime.getRequestDate());
            pstOvertime.setString(FLD_OV_NUMBER, overtime.getOvertimeNum());
            pstOvertime.setString(FLD_OBJECTIVE, overtime.getObjective());
            pstOvertime.setInt(FLD_STATUS_DOC, overtime.getStatusDoc());
            pstOvertime.setLong(FLD_CUSTOMER_TASK_ID, overtime.getCustomerTaskId());
            pstOvertime.setLong(FLD_LOGBOOK_ID, overtime.getLogbookId());
            pstOvertime.setLong(FLD_REQ_ID, overtime.getRequestId());
            pstOvertime.setLong(FLD_APPROVAL_ID, overtime.getApprovalId());
            pstOvertime.setLong(FLD_ACK_ID, overtime.getAckId());
            pstOvertime.setLong(FLD_COMPANY_ID, overtime.getCompanyId());
            pstOvertime.setLong(FLD_DIVISION_ID, overtime.getDivisionId());
            pstOvertime.setLong(FLD_DEPARTMENT_ID, overtime.getDepartmentId());
            pstOvertime.setLong(FLD_SECTION_ID, overtime.getSectionId());
            pstOvertime.insert();

            overtime.setOID(pstOvertime.getlong(FLD_OVERTIME_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvertime2(0), DBException.UNKNOWN);
        }

        return overtime.getOID();
    }

    public static long updateExc(Overtime overtime) throws DBException {
        try {
            if (overtime.getOID() != 0) {
                PstOvertime2 pstOvertime = new PstOvertime2(overtime.getOID());

                pstOvertime.setDate(FLD_REQ_DATE, overtime.getRequestDate());
                pstOvertime.setString(FLD_OV_NUMBER, overtime.getOvertimeNum());
                pstOvertime.setString(FLD_OBJECTIVE, overtime.getObjective());
                pstOvertime.setInt(FLD_STATUS_DOC, overtime.getStatusDoc());
                pstOvertime.setLong(FLD_CUSTOMER_TASK_ID , overtime.getCustomerTaskId());
                pstOvertime.setLong(FLD_LOGBOOK_ID , overtime.getLogbookId());
                pstOvertime.setLong(FLD_REQ_ID, overtime.getRequestId());
                pstOvertime.setLong(FLD_APPROVAL_ID, overtime.getApprovalId());
                pstOvertime.setLong(FLD_ACK_ID, overtime.getAckId());
                pstOvertime.setLong(FLD_COMPANY_ID, overtime.getCompanyId());
                pstOvertime.setLong(FLD_DIVISION_ID, overtime.getDivisionId());
                pstOvertime.setLong(FLD_DEPARTMENT_ID, overtime.getDepartmentId());
                pstOvertime.setLong(FLD_SECTION_ID, overtime.getSectionId());

                pstOvertime.update();

                return overtime.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvertime2(0), DBException.UNKNOWN);
        }

        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstOvertime2 pstOvertime = new PstOvertime2(oid);
            pstOvertime.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvertime2(0), DBException.UNKNOWN);
        }

        return oid;
    }

    public static Vector listAll() {
        return list(0, 0, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;

        try {
            String sql = "SELECT * FROM " + TBL_OVERTIME;
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
                Overtime overtime = new Overtime();
                resultToObject(rs, overtime);
                lists.add(overtime);
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

    public static void resultToObject(ResultSet rs, Overtime overtime) {
        try {
            overtime.setOID(rs.getLong(PstOvertime2.fieldNames[PstOvertime2.FLD_OVERTIME_ID]));
            overtime.setRequestDate(rs.getDate(PstOvertime2.fieldNames[PstOvertime2.FLD_REQ_DATE]));
            overtime.setOvertimeNum(rs.getString(PstOvertime2.fieldNames[PstOvertime2.FLD_OV_NUMBER]));
            overtime.setObjective(rs.getString(PstOvertime2.fieldNames[PstOvertime2.FLD_OBJECTIVE]));
            overtime.setStatusDoc(rs.getInt(PstOvertime2.fieldNames[PstOvertime2.FLD_STATUS_DOC]));
            overtime.setCustomerTaskId(rs.getLong(PstOvertime2.fieldNames[PstOvertime2.FLD_CUSTOMER_TASK_ID ]));
            overtime.setLogbookId(rs.getLong(PstOvertime2.fieldNames[PstOvertime2.FLD_LOGBOOK_ID ]));
            overtime.setRequestId(rs.getLong(PstOvertime2.fieldNames[PstOvertime2.FLD_REQ_ID]));
            overtime.setApprovalId(rs.getLong(PstOvertime2.fieldNames[PstOvertime2.FLD_APPROVAL_ID]));
            overtime.setAckId(rs.getLong(PstOvertime2.fieldNames[PstOvertime2.FLD_ACK_ID]));
            overtime.setCompanyId(rs.getLong(PstOvertime2.fieldNames[PstOvertime2.FLD_COMPANY_ID]));
            overtime.setDivisionId(rs.getLong(PstOvertime2.fieldNames[PstOvertime2.FLD_DIVISION_ID]));
            overtime.setDepartmentId(rs.getLong(PstOvertime2.fieldNames[PstOvertime2.FLD_DEPARTMENT_ID]));
            overtime.setSectionId(rs.getLong(PstOvertime2.fieldNames[PstOvertime2.FLD_SECTION_ID]));
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long overtimeId) {
        DBResultSet dbrs = null;
        boolean result = false;

        try {
            String sql = "SELECT * FROM " + TBL_OVERTIME + " WHERE "
                    + PstOvertime2.fieldNames[PstOvertime2.FLD_OVERTIME_ID] + " = '" + overtimeId + "'";

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
            String sql = "SELECT COUNT(" + PstOvertime2.fieldNames[PstOvertime2.FLD_OVERTIME_ID]
                    + ") FROM " + TBL_OVERTIME;

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

    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;

        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    Overtime overtime = (Overtime) list.get(ls);
                    if (oid == overtime.getOID()) {
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

    /*public static boolean checkWarning(long warnId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_OVERTIME + " WHERE "
                    + //PstOvertime2.fieldNames[PstOvertime2.FLD_WARN_LEVEL] + " = '" + warnId + "'";
                    PstOvertime2.fieldNames[PstOvertime2.FLD_WARN_LEVEL_ID] + " = '" + warnId + "'";

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
    }*/
    /*}*/
}
