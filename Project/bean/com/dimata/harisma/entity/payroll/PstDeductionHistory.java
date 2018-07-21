/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

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

public class PstDeductionHistory extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_DEDUCTION_HISTORY = "pay_deduction_history";
    public static final int FLD_DEDUCTION_HISTORY_ID = 0;
    public static final int FLD_PERCEN = 1;
    public static final int FLD_DESCRIPTION = 2;
    public static final int FLD_PERCEN_RESULT = 3;
    public static final int FLD_DEDUCTION_RESULT = 4;
    public static final int FLD_BENEFIT_PERIOD_HISTORY_ID = 5;

    public static String[] fieldNames = {
        "DEDUCTION_HISTORY_ID",
        "PERCEN",
        "DESCRIPTION",
        "PERCEN_RESULT",
        "DEDUCTION_RESULT",
        "BENEFIT_PERIOD_HISTORY_ID"
    };

    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_INT,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_LONG
    };

    public PstDeductionHistory() {
    }

    public PstDeductionHistory(int i) throws DBException {
        super(new PstDeductionHistory());
    }

    public PstDeductionHistory(String sOid) throws DBException {
        super(new PstDeductionHistory(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDeductionHistory(long lOid) throws DBException {
        super(new PstDeductionHistory(0));
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
        return TBL_DEDUCTION_HISTORY;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDeductionHistory().getClass().getName();
    }

    public static DeductionHistory fetchExc(long oid) throws DBException {
        try {
            DeductionHistory entDeductionHistory = new DeductionHistory();
            PstDeductionHistory pstDeductionHistory = new PstDeductionHistory(oid);
            entDeductionHistory.setOID(oid);
            entDeductionHistory.setPercen(pstDeductionHistory.getInt(FLD_PERCEN));
            entDeductionHistory.setDescription(pstDeductionHistory.getString(FLD_DESCRIPTION));
            entDeductionHistory.setPercenResult(pstDeductionHistory.getdouble(FLD_PERCEN_RESULT));
            entDeductionHistory.setDeductionResult(pstDeductionHistory.getdouble(FLD_DEDUCTION_RESULT));
            entDeductionHistory.setBenefitPeriodHistoryId(pstDeductionHistory.getLong(FLD_BENEFIT_PERIOD_HISTORY_ID));
            return entDeductionHistory;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDeductionHistory(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        DeductionHistory entDeductionHistory = fetchExc(entity.getOID());
        entity = (Entity) entDeductionHistory;
        return entDeductionHistory.getOID();
    }

    public static synchronized long updateExc(DeductionHistory entDeductionHistory) throws DBException {
        try {
            if (entDeductionHistory.getOID() != 0) {
                PstDeductionHistory pstDeductionHistory = new PstDeductionHistory(entDeductionHistory.getOID());
                pstDeductionHistory.setInt(FLD_PERCEN, entDeductionHistory.getPercen());
                pstDeductionHistory.setString(FLD_DESCRIPTION, entDeductionHistory.getDescription());
                pstDeductionHistory.setDouble(FLD_PERCEN_RESULT, entDeductionHistory.getPercenResult());
                pstDeductionHistory.setDouble(FLD_DEDUCTION_RESULT, entDeductionHistory.getDeductionResult());
                pstDeductionHistory.setLong(FLD_BENEFIT_PERIOD_HISTORY_ID, entDeductionHistory.getBenefitPeriodHistoryId());
                pstDeductionHistory.update();
                return entDeductionHistory.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDeductionHistory(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((DeductionHistory) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstDeductionHistory pstDeductionHistory = new PstDeductionHistory(oid);
            pstDeductionHistory.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDeductionHistory(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(DeductionHistory entDeductionHistory) throws DBException {
        try {
            PstDeductionHistory pstDeductionHistory = new PstDeductionHistory(0);
            pstDeductionHistory.setInt(FLD_PERCEN, entDeductionHistory.getPercen());
            pstDeductionHistory.setString(FLD_DESCRIPTION, entDeductionHistory.getDescription());
            pstDeductionHistory.setDouble(FLD_PERCEN_RESULT, entDeductionHistory.getPercenResult());
            pstDeductionHistory.setDouble(FLD_DEDUCTION_RESULT, entDeductionHistory.getDeductionResult());
            pstDeductionHistory.setLong(FLD_BENEFIT_PERIOD_HISTORY_ID, entDeductionHistory.getBenefitPeriodHistoryId());
            pstDeductionHistory.insert();
            entDeductionHistory.setOID(pstDeductionHistory.getLong(FLD_DEDUCTION_HISTORY_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDeductionHistory(0), DBException.UNKNOWN);
        }
        return entDeductionHistory.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((DeductionHistory) entity);
    }

    public static void resultToObject(ResultSet rs, DeductionHistory entDeductionHistory) {
        try {
            entDeductionHistory.setOID(rs.getLong(PstDeductionHistory.fieldNames[PstDeductionHistory.FLD_DEDUCTION_HISTORY_ID]));
            entDeductionHistory.setPercen(rs.getInt(PstDeductionHistory.fieldNames[PstDeductionHistory.FLD_PERCEN]));
            entDeductionHistory.setDescription(rs.getString(PstDeductionHistory.fieldNames[PstDeductionHistory.FLD_DESCRIPTION]));
            entDeductionHistory.setPercenResult(rs.getDouble(PstDeductionHistory.fieldNames[PstDeductionHistory.FLD_PERCEN_RESULT]));
            entDeductionHistory.setDeductionResult(rs.getDouble(PstDeductionHistory.fieldNames[PstDeductionHistory.FLD_DEDUCTION_RESULT]));
            entDeductionHistory.setBenefitPeriodHistoryId(rs.getLong(PstDeductionHistory.fieldNames[PstDeductionHistory.FLD_BENEFIT_PERIOD_HISTORY_ID]));
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
            String sql = "SELECT * FROM " + TBL_DEDUCTION_HISTORY;
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
                DeductionHistory entDeductionHistory = new DeductionHistory();
                resultToObject(rs, entDeductionHistory);
                lists.add(entDeductionHistory);
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

    public static boolean checkOID(long entDeductionHistoryId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_DEDUCTION_HISTORY + " WHERE "
                    + PstDeductionHistory.fieldNames[PstDeductionHistory.FLD_DEDUCTION_HISTORY_ID] + " = " + entDeductionHistoryId;
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
            String sql = "SELECT COUNT(" + PstDeductionHistory.fieldNames[PstDeductionHistory.FLD_DEDUCTION_HISTORY_ID] + ") FROM " + TBL_DEDUCTION_HISTORY;
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
                    DeductionHistory entDeductionHistory = (DeductionHistory) list.get(ls);
                    if (oid == entDeductionHistory.getOID()) {
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
