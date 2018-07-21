/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

/**
 *
 * @author dimata005
 */
import java.sql.*;
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.Command;
import java.util.Vector;

public class PstOutSourceEvaluation extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_OUTSOURCEEVALUATION = "hr_outsource_eval";
    public static final int FLD_OUTSOURCE_EVAL_ID = 0;
    public static final int FLD_TITLE = 1;
    public static final int FLD_COMPANY_ID = 2;
    public static final int FLD_CREATED_DATE = 3;
    public static final int FLD_CREATED_BY_ID = 4;
    public static final int FLD_APPROVED_BY_DATE = 5;
    public static final int FLD_APPROVED_BY_ID = 6;
    public static final int FLD_NOTE = 7;
    public static final int FLD_STATUS_DOC = 8;
    public static final int FLD_DIVISION_ID = 9;
    public static final int FLD_DEPARTMENT_ID = 10;
    public static final int FLD_SECTION_ID = 11;
    public static final int FLD_DATE_OF_EVAL = 12;
    public static final int FLD_PERIOD_ID = 13;
    
    public static final String[] typeKey = {"PLAN & INPUT", "LINK & INPUT"};
    public static final int[] typeValue = {0, 1};
    
    public static String[] fieldNames = {
        "OUTSOURCE_EVAL_ID",
        "TITLE",
        "COMPANY_ID",
        "CREATED_DATE",
        "CREATED_BY_ID",
        "APPROVED_BY_DATE",
        "APPROVED_BY_ID",
        "NOTE",
        "STATUS_DOC",
        "DIVISION_ID",
        "DEPARTMENT_ID",
        "SECTION_ID",
        "DATE_OF_EVAL",
        "PERIOD_ID"
            
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG
    };

    public PstOutSourceEvaluation() {
    }

    public PstOutSourceEvaluation(int i) throws DBException {
        super(new PstOutSourceEvaluation());
    }

    public PstOutSourceEvaluation(String sOid) throws DBException {
        super(new PstOutSourceEvaluation(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstOutSourceEvaluation(long lOid) throws DBException {
        super(new PstOutSourceEvaluation(0));
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
        return TBL_OUTSOURCEEVALUATION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstOutSourceEvaluation().getClass().getName();
    }

    public static OutSourceEvaluation fetchExc(long oid) throws DBException {
        try {
         OutSourceEvaluation entOutSourceEvaluation = new OutSourceEvaluation();
         PstOutSourceEvaluation pstOutSourceEvaluation = new PstOutSourceEvaluation(oid);
         entOutSourceEvaluation.setOID(oid);
         entOutSourceEvaluation.setTitle(pstOutSourceEvaluation.getString(FLD_TITLE));
         entOutSourceEvaluation.setCompanyId(pstOutSourceEvaluation.getLong(FLD_COMPANY_ID));
         entOutSourceEvaluation.setCreatedDate(pstOutSourceEvaluation.getDate(FLD_CREATED_DATE));
         entOutSourceEvaluation.setCreatedById(pstOutSourceEvaluation.getLong(FLD_CREATED_BY_ID));
         entOutSourceEvaluation.setApprovedDate(pstOutSourceEvaluation.getDate(FLD_APPROVED_BY_DATE));
         entOutSourceEvaluation.setApprovedById(pstOutSourceEvaluation.getLong(FLD_APPROVED_BY_ID));
         entOutSourceEvaluation.setNote(pstOutSourceEvaluation.getString(FLD_NOTE));
         entOutSourceEvaluation.setStatusDoc(pstOutSourceEvaluation.getInt(FLD_STATUS_DOC));
         entOutSourceEvaluation.setDivisionId(pstOutSourceEvaluation.getLong(FLD_DIVISION_ID));
         entOutSourceEvaluation.setDepartmentId(pstOutSourceEvaluation.getLong(FLD_DEPARTMENT_ID));
         entOutSourceEvaluation.setSectionId(pstOutSourceEvaluation.getLong(FLD_SECTION_ID));
         entOutSourceEvaluation.setDateOfEval(pstOutSourceEvaluation.getDate(FLD_DATE_OF_EVAL));
         entOutSourceEvaluation.setPeriodId(pstOutSourceEvaluation.getLong(FLD_PERIOD_ID));
         return entOutSourceEvaluation;
            
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceEvaluation(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        OutSourceEvaluation entOutSourceEvaluation = fetchExc(entity.getOID());
        entity = (Entity) entOutSourceEvaluation;
        return entOutSourceEvaluation.getOID();
    }

    public static synchronized long updateExc(OutSourceEvaluation entOutSourceEvaluation) throws DBException {
        try {
            if (entOutSourceEvaluation.getOID() != 0) {
                PstOutSourceEvaluation pstOutSourceEvaluation = new PstOutSourceEvaluation(entOutSourceEvaluation.getOID());
            pstOutSourceEvaluation.setString(FLD_TITLE, entOutSourceEvaluation.getTitle());
            pstOutSourceEvaluation.setLong(FLD_COMPANY_ID, entOutSourceEvaluation.getCompanyId());
            pstOutSourceEvaluation.setDate(FLD_CREATED_DATE, entOutSourceEvaluation.getCreatedDate());
            pstOutSourceEvaluation.setLong(FLD_CREATED_BY_ID, entOutSourceEvaluation.getCreatedById());
            pstOutSourceEvaluation.setDate(FLD_APPROVED_BY_DATE, entOutSourceEvaluation.getApprovedDate());
            pstOutSourceEvaluation.setLong(FLD_APPROVED_BY_ID, entOutSourceEvaluation.getApprovedById());
            pstOutSourceEvaluation.setString(FLD_NOTE, entOutSourceEvaluation.getNote());
            pstOutSourceEvaluation.setInt(FLD_STATUS_DOC, entOutSourceEvaluation.getStatusDoc());
            pstOutSourceEvaluation.setLong(FLD_DIVISION_ID, entOutSourceEvaluation.getDivisionId());
            pstOutSourceEvaluation.setLong(FLD_DEPARTMENT_ID, entOutSourceEvaluation.getDepartmentId());
            pstOutSourceEvaluation.setLong(FLD_SECTION_ID, entOutSourceEvaluation.getSectionId());
            pstOutSourceEvaluation.setDate(FLD_DATE_OF_EVAL, entOutSourceEvaluation.getDateOfEval());
            pstOutSourceEvaluation.setLong(FLD_PERIOD_ID, entOutSourceEvaluation.getPeriodId());
            pstOutSourceEvaluation.setDate(FLD_DATE_OF_EVAL, entOutSourceEvaluation.getDateOfEval());
            pstOutSourceEvaluation.setLong(FLD_PERIOD_ID, entOutSourceEvaluation.getPeriodId());
            pstOutSourceEvaluation.update();
            return entOutSourceEvaluation.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceEvaluation(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((OutSourceEvaluation) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstOutSourceEvaluation pstOutSourceEvaluation = new PstOutSourceEvaluation(oid);
            pstOutSourceEvaluation.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceEvaluation(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(OutSourceEvaluation entOutSourceEvaluation) throws DBException {
        try {
            PstOutSourceEvaluation pstOutSourceEvaluation = new PstOutSourceEvaluation(0);
            pstOutSourceEvaluation.setString(FLD_TITLE, entOutSourceEvaluation.getTitle());
            pstOutSourceEvaluation.setLong(FLD_COMPANY_ID, entOutSourceEvaluation.getCompanyId());
            pstOutSourceEvaluation.setDate(FLD_CREATED_DATE, entOutSourceEvaluation.getCreatedDate());
            pstOutSourceEvaluation.setLong(FLD_CREATED_BY_ID, entOutSourceEvaluation.getCreatedById());
            pstOutSourceEvaluation.setDate(FLD_APPROVED_BY_DATE, entOutSourceEvaluation.getApprovedDate());
            pstOutSourceEvaluation.setLong(FLD_APPROVED_BY_ID, entOutSourceEvaluation.getApprovedById());
            pstOutSourceEvaluation.setString(FLD_NOTE, entOutSourceEvaluation.getNote());
            pstOutSourceEvaluation.setInt(FLD_STATUS_DOC, entOutSourceEvaluation.getStatusDoc());
            pstOutSourceEvaluation.setLong(FLD_DIVISION_ID, entOutSourceEvaluation.getDivisionId());
            pstOutSourceEvaluation.setLong(FLD_DEPARTMENT_ID, entOutSourceEvaluation.getDepartmentId());
            pstOutSourceEvaluation.setLong(FLD_SECTION_ID, entOutSourceEvaluation.getSectionId());
            pstOutSourceEvaluation.setDate(FLD_DATE_OF_EVAL, entOutSourceEvaluation.getDateOfEval());
            pstOutSourceEvaluation.setLong(FLD_PERIOD_ID, entOutSourceEvaluation.getPeriodId());
            pstOutSourceEvaluation.insert();
            entOutSourceEvaluation.setOID(pstOutSourceEvaluation.getlong(FLD_OUTSOURCE_EVAL_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceEvaluation(0), DBException.UNKNOWN);
        }
        return entOutSourceEvaluation.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((OutSourceEvaluation) entity);
    }

    public static void resultToObject(ResultSet rs, OutSourceEvaluation entOutSourceEvaluation) {
        try {
            entOutSourceEvaluation.setOID(rs.getLong(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_OUTSOURCE_EVAL_ID]));
            entOutSourceEvaluation.setTitle(rs.getString(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_TITLE]));
            entOutSourceEvaluation.setCompanyId(rs.getLong(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_COMPANY_ID]));
            entOutSourceEvaluation.setCreatedDate(rs.getDate(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_CREATED_DATE]));
            entOutSourceEvaluation.setCreatedById(rs.getLong(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_CREATED_BY_ID]));
            entOutSourceEvaluation.setApprovedDate(rs.getDate(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_APPROVED_BY_DATE]));
            entOutSourceEvaluation.setApprovedById(rs.getLong(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_APPROVED_BY_ID]));
            entOutSourceEvaluation.setNote(rs.getString(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_NOTE]));
            entOutSourceEvaluation.setStatusDoc(rs.getInt(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_STATUS_DOC]));
            entOutSourceEvaluation.setDivisionId(rs.getLong(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_DIVISION_ID]));
            entOutSourceEvaluation.setDepartmentId(rs.getLong(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_DEPARTMENT_ID]));
            entOutSourceEvaluation.setSectionId(rs.getLong(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_SECTION_ID]));
            entOutSourceEvaluation.setDateOfEval(rs.getDate(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_DATE_OF_EVAL]));
            entOutSourceEvaluation.setPeriodId(rs.getLong(PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_PERIOD_ID]));
        } catch (Exception e) {
        }
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_OUTSOURCEEVALUATION;
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
                OutSourceEvaluation entOutSourceEvaluation = new OutSourceEvaluation();
                resultToObject(rs, entOutSourceEvaluation);
                lists.add(entOutSourceEvaluation);
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
    
    public static String getName(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_CREATED_DATE] + " FROM " + TBL_OUTSOURCEEVALUATION;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            String name = "";
            while (rs.next()) {
                name = rs.getString(1);
            }
            rs.close();
            return name;
        } catch (Exception e) {
            return "";
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static boolean checkOID(long entOutSourceEvaluationId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_OUTSOURCEEVALUATION + " WHERE "
                    + PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_OUTSOURCE_EVAL_ID] + " = " + entOutSourceEvaluationId;
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
            String sql = "SELECT COUNT(" + PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_OUTSOURCE_EVAL_ID] + ") FROM " + TBL_OUTSOURCEEVALUATION;
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
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    OutSourceEvaluation entOutSourceEvaluation = (OutSourceEvaluation) list.get(ls);
                    if (oid == entOutSourceEvaluation.getOID()) {
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
}
