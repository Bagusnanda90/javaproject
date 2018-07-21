/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

import com.dimata.harisma.entity.masterdata.PstEmpDoc;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

public class PstEmpDocumentStatus extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_EMPDOCUMENTSTATUS = "hr_emp_doc_status";
    public static final int FLD_EMP_DOC_STATUS_ID = 0;
    public static final int FLD_EMP_DOC_ID = 1;
    public static final int FLD_EMPLOYEE_ID = 2;
    public static final int FLD_STATUS = 3;

    public static String[] fieldNames = {
        "EMP_DOC_STATUS_ID",
        "EMP_DOC_ID",
        "EMPLOYEE_ID",
        "STATUS"
    };

    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING
    };

    public PstEmpDocumentStatus() {
    }

    public PstEmpDocumentStatus(int i) throws DBException {
        super(new PstEmpDocumentStatus());
    }

    public PstEmpDocumentStatus(String sOid) throws DBException {
        super(new PstEmpDocumentStatus(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpDocumentStatus(long lOid) throws DBException {
        super(new PstEmpDocumentStatus(0));
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
        return TBL_EMPDOCUMENTSTATUS;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpDocumentStatus().getClass().getName();
    }

    public static EmpDocumentStatus fetchExc(long oid) throws DBException {
        try {
            EmpDocumentStatus entEmpDocumentStatus = new EmpDocumentStatus();
            PstEmpDocumentStatus pstEmpDocumentStatus = new PstEmpDocumentStatus(oid);
            entEmpDocumentStatus.setOID(oid);
            entEmpDocumentStatus.setEmpDocId(pstEmpDocumentStatus.getLong(FLD_EMP_DOC_ID));
            entEmpDocumentStatus.setEmployeeId(pstEmpDocumentStatus.getLong(FLD_EMPLOYEE_ID));
            entEmpDocumentStatus.setStatus(pstEmpDocumentStatus.getString(FLD_STATUS));
            return entEmpDocumentStatus;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocumentStatus(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        EmpDocumentStatus entEmpDocumentStatus = fetchExc(entity.getOID());
        entity = (Entity) entEmpDocumentStatus;
        return entEmpDocumentStatus.getOID();
    }

    public static synchronized long updateExc(EmpDocumentStatus entEmpDocumentStatus) throws DBException {
        try {
            if (entEmpDocumentStatus.getOID() != 0) {
                PstEmpDocumentStatus pstEmpDocumentStatus = new PstEmpDocumentStatus(entEmpDocumentStatus.getOID());
                pstEmpDocumentStatus.setLong(FLD_EMP_DOC_ID, entEmpDocumentStatus.getEmpDocId());
                pstEmpDocumentStatus.setLong(FLD_EMPLOYEE_ID, entEmpDocumentStatus.getEmployeeId());
                pstEmpDocumentStatus.setString(FLD_STATUS, entEmpDocumentStatus.getStatus());
                pstEmpDocumentStatus.update();
                return entEmpDocumentStatus.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocumentStatus(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((EmpDocumentStatus) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstEmpDocumentStatus pstEmpDocumentStatus = new PstEmpDocumentStatus(oid);
            pstEmpDocumentStatus.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocumentStatus(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(EmpDocumentStatus entEmpDocumentStatus) throws DBException {
        try {
            PstEmpDocumentStatus pstEmpDocumentStatus = new PstEmpDocumentStatus(0);
            pstEmpDocumentStatus.setLong(FLD_EMP_DOC_ID, entEmpDocumentStatus.getEmpDocId());
            pstEmpDocumentStatus.setLong(FLD_EMPLOYEE_ID, entEmpDocumentStatus.getEmployeeId());
            pstEmpDocumentStatus.setString(FLD_STATUS, entEmpDocumentStatus.getStatus());
            pstEmpDocumentStatus.insert();
            entEmpDocumentStatus.setOID(pstEmpDocumentStatus.getLong(FLD_EMP_DOC_STATUS_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocumentStatus(0), DBException.UNKNOWN);
        }
        return entEmpDocumentStatus.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((EmpDocumentStatus) entity);
    }

    public static void resultToObject(ResultSet rs, EmpDocumentStatus entEmpDocumentStatus) {
        try {
            entEmpDocumentStatus.setOID(rs.getLong(PstEmpDocumentStatus.fieldNames[PstEmpDocumentStatus.FLD_EMP_DOC_STATUS_ID]));
            entEmpDocumentStatus.setEmpDocId(rs.getLong(PstEmpDocumentStatus.fieldNames[PstEmpDocumentStatus.FLD_EMP_DOC_ID]));
            entEmpDocumentStatus.setEmployeeId(rs.getLong(PstEmpDocumentStatus.fieldNames[PstEmpDocumentStatus.FLD_EMPLOYEE_ID]));
            entEmpDocumentStatus.setStatus(rs.getString(PstEmpDocumentStatus.fieldNames[PstEmpDocumentStatus.FLD_STATUS]));
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
            String sql = "SELECT * FROM " + TBL_EMPDOCUMENTSTATUS;
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
                EmpDocumentStatus entEmpDocumentStatus = new EmpDocumentStatus();
                resultToObject(rs, entEmpDocumentStatus);
                lists.add(entEmpDocumentStatus);
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
    
    public static Vector listMemo(String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;

        try {
            String sql = "SELECT MEM.*  FROM " + TBL_EMPDOCUMENTSTATUS + " AS MEM "
                    + " INNER JOIN " + PstEmpDoc.TBL_HR_EMP_DOC + " AS EMPDOC ON MEM." + PstEmpDocumentStatus.fieldNames[PstEmpDocumentStatus.FLD_EMP_DOC_ID] + "=EMPDOC." + PstEmpDoc.fieldNames[PstEmpDoc.FLD_EMP_DOC_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }


            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
                EmpDocumentStatus entEmpDocumentStatus = new EmpDocumentStatus();
                resultToObject(rs, entEmpDocumentStatus);
                lists.add(entEmpDocumentStatus);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return lists;
        }
    }

    public static boolean checkOID(long entEmpDocumentStatusId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_EMPDOCUMENTSTATUS + " WHERE "
                    + PstEmpDocumentStatus.fieldNames[PstEmpDocumentStatus.FLD_EMP_DOC_STATUS_ID] + " = " + entEmpDocumentStatusId;
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
            String sql = "SELECT COUNT(" + PstEmpDocumentStatus.fieldNames[PstEmpDocumentStatus.FLD_EMP_DOC_STATUS_ID] + ") FROM " + TBL_EMPDOCUMENTSTATUS;
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
                    EmpDocumentStatus entEmpDocumentStatus = (EmpDocumentStatus) list.get(ls);
                    if (oid == entEmpDocumentStatus.getOID()) {
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
        } else if (start == (vectSize - recordToGet)) {
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
        return cmd;
    }
}
