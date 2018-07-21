/*
 * To change this template, choose Tools | Templates
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

public class PstBenefitPeriodDeduction extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_BENEFIT_PERIOD_DEDUCTION = "pay_benefit_period_deduction";
    public static final int FLD_BENEFIT_PERIOD_DEDUCTION_ID = 0;
    public static final int FLD_DEDUCTION_ID = 1;
    public static final int FLD_DEDUCTION_RESULT = 2;
    public static final int FLD_BENEFIT_PERIOD_ID = 3;
    public static String[] fieldNames = {
        "BENEFIT_PERIOD_DEDUCTION_ID",
        "DEDUCTION_ID",
        "DEDUCTION_RESULT",
        "BENEFIT_PERIOD_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_LONG
    };

    public PstBenefitPeriodDeduction() {
    }

    public PstBenefitPeriodDeduction(int i) throws DBException {
        super(new PstBenefitPeriodDeduction());
    }

    public PstBenefitPeriodDeduction(String sOid) throws DBException {
        super(new PstBenefitPeriodDeduction(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstBenefitPeriodDeduction(long lOid) throws DBException {
        super(new PstBenefitPeriodDeduction(0));
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
        return TBL_BENEFIT_PERIOD_DEDUCTION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstBenefitPeriodDeduction().getClass().getName();
    }

    public static BenefitPeriodDeduction fetchExc(long oid) throws DBException {
        try {
            BenefitPeriodDeduction entBenefitPeriodDeduction = new BenefitPeriodDeduction();
            PstBenefitPeriodDeduction pstBenefitPeriodDeduction = new PstBenefitPeriodDeduction(oid);
            entBenefitPeriodDeduction.setOID(oid);
            entBenefitPeriodDeduction.setDeductionId(pstBenefitPeriodDeduction.getLong(FLD_DEDUCTION_ID));
            entBenefitPeriodDeduction.setDeductionResult(pstBenefitPeriodDeduction.getdouble(FLD_DEDUCTION_RESULT));
            entBenefitPeriodDeduction.setBenefitPeriodId(pstBenefitPeriodDeduction.getLong(FLD_BENEFIT_PERIOD_ID));
            return entBenefitPeriodDeduction;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriodDeduction(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        BenefitPeriodDeduction entBenefitPeriodDeduction = fetchExc(entity.getOID());
        entity = (Entity) entBenefitPeriodDeduction;
        return entBenefitPeriodDeduction.getOID();
    }

    public static synchronized long updateExc(BenefitPeriodDeduction entBenefitPeriodDeduction) throws DBException {
        try {
            if (entBenefitPeriodDeduction.getOID() != 0) {
                PstBenefitPeriodDeduction pstBenefitPeriodDeduction = new PstBenefitPeriodDeduction(entBenefitPeriodDeduction.getOID());
                pstBenefitPeriodDeduction.setLong(FLD_DEDUCTION_ID, entBenefitPeriodDeduction.getDeductionId());
                pstBenefitPeriodDeduction.setDouble(FLD_DEDUCTION_RESULT, entBenefitPeriodDeduction.getDeductionResult());
                pstBenefitPeriodDeduction.setLong(FLD_BENEFIT_PERIOD_ID, entBenefitPeriodDeduction.getBenefitPeriodId());
                pstBenefitPeriodDeduction.update();
                return entBenefitPeriodDeduction.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriodDeduction(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((BenefitPeriodDeduction) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstBenefitPeriodDeduction pstBenefitPeriodDeduction = new PstBenefitPeriodDeduction(oid);
            pstBenefitPeriodDeduction.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriodDeduction(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(BenefitPeriodDeduction entBenefitPeriodDeduction) throws DBException {
        try {
            PstBenefitPeriodDeduction pstBenefitPeriodDeduction = new PstBenefitPeriodDeduction(0);
            pstBenefitPeriodDeduction.setLong(FLD_DEDUCTION_ID, entBenefitPeriodDeduction.getDeductionId());
            pstBenefitPeriodDeduction.setDouble(FLD_DEDUCTION_RESULT, entBenefitPeriodDeduction.getDeductionResult());
            pstBenefitPeriodDeduction.setLong(FLD_BENEFIT_PERIOD_ID, entBenefitPeriodDeduction.getBenefitPeriodId());
            pstBenefitPeriodDeduction.insert();
            entBenefitPeriodDeduction.setOID(pstBenefitPeriodDeduction.getLong(FLD_BENEFIT_PERIOD_DEDUCTION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriodDeduction(0), DBException.UNKNOWN);
        }
        return entBenefitPeriodDeduction.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((BenefitPeriodDeduction) entity);
    }

    public static void resultToObject(ResultSet rs, BenefitPeriodDeduction entBenefitPeriodDeduction) {
        try {
            entBenefitPeriodDeduction.setOID(rs.getLong(PstBenefitPeriodDeduction.fieldNames[PstBenefitPeriodDeduction.FLD_BENEFIT_PERIOD_DEDUCTION_ID]));
            entBenefitPeriodDeduction.setDeductionId(rs.getLong(PstBenefitPeriodDeduction.fieldNames[PstBenefitPeriodDeduction.FLD_DEDUCTION_ID]));
            entBenefitPeriodDeduction.setDeductionResult(rs.getDouble(PstBenefitPeriodDeduction.fieldNames[PstBenefitPeriodDeduction.FLD_DEDUCTION_RESULT]));
            entBenefitPeriodDeduction.setBenefitPeriodId(rs.getLong(PstBenefitPeriodDeduction.fieldNames[PstBenefitPeriodDeduction.FLD_BENEFIT_PERIOD_ID]));
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
            String sql = "SELECT * FROM " + TBL_BENEFIT_PERIOD_DEDUCTION;
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
                BenefitPeriodDeduction periodDeduction = new BenefitPeriodDeduction();
                resultToObject(rs, periodDeduction);
                lists.add(periodDeduction);
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
    
    public static boolean checkOID(long entBenefitPeriodDeductionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_BENEFIT_PERIOD_DEDUCTION + " WHERE "
                    + PstBenefitPeriodDeduction.fieldNames[PstBenefitPeriodDeduction.FLD_BENEFIT_PERIOD_DEDUCTION_ID] + " = " + entBenefitPeriodDeductionId;

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
            String sql = "SELECT COUNT(" + PstBenefitPeriodDeduction.fieldNames[PstBenefitPeriodDeduction.FLD_BENEFIT_PERIOD_DEDUCTION_ID] + ") FROM " + TBL_BENEFIT_PERIOD_DEDUCTION;
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
                    BenefitPeriodDeduction entBenefitPeriodDeduction = (BenefitPeriodDeduction) list.get(ls);
                    if (oid == entBenefitPeriodDeduction.getOID()) {
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
    
    public static BenefitPeriodDeduction getOneResult(long deductionId, long bPeriodId) {
        BenefitPeriodDeduction deduction = new BenefitPeriodDeduction();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT * FROM pay_benefit_period_deduction ";
            sql += " WHERE pay_benefit_period_deduction.DEDUCTION_ID="+deductionId+" AND pay_benefit_period_deduction.BENEFIT_PERIOD_ID="+bPeriodId;
            sql += " LIMIT 1 ";
        
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                resultToObject(rs, deduction);
            }
            rs.close();
            return deduction;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return deduction;
    }
}
