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

/**
 *
 * @author Hendra Putu | 2015-02-10
 */
public class PstBenefitConfigDeduction extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_DEDUCTION = "pay_benefit_config_deduction";
    public static final int FLD_DEDUCTION_ID = 0;
    public static final int FLD_DEDUCTION_PERCENT = 1;
    public static final int FLD_DEDUCTION_DESCRIPTION = 2;
    public static final int FLD_DEDUCTION_REFERENCE = 3;
    public static final int FLD_DEDUCTION_INDEX = 4;
    public static final int FLD_BENEFIT_CONFIG_ID = 5;

    public static String[] fieldNames = {
        "DEDUCTION_ID",
        "DEDUCTION_PERCENT",
        "DEDUCTION_DESCRIPTION",
        "DEDUCTION_REFERENCE",
        "DEDUCTION_INDEX",
        "BENEFIT_CONFIG_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_INT,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_INT,
        TYPE_LONG
    };
    
    public PstBenefitConfigDeduction() {
    }
    
    public PstBenefitConfigDeduction(int i) throws DBException {
        super(new PstBenefitConfigDeduction());
    }
    
    public PstBenefitConfigDeduction(String sOid) throws DBException {
        super(new PstBenefitConfigDeduction(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }
    
    public PstBenefitConfigDeduction(long lOid) throws DBException {
        super(new PstBenefitConfigDeduction(0));
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
        return TBL_DEDUCTION;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstBenefitConfigDeduction().getClass().getName();
    }
    
    public static BenefitConfigDeduction fetchExc(long oid) throws DBException {
        try {
            BenefitConfigDeduction entDeduction = new BenefitConfigDeduction();
            PstBenefitConfigDeduction pstDeduction = new PstBenefitConfigDeduction(oid);
            entDeduction.setOID(oid);
            entDeduction.setDeductionPercent(pstDeduction.getInt(FLD_DEDUCTION_PERCENT));
            entDeduction.setDeductionDescription(pstDeduction.getString(FLD_DEDUCTION_DESCRIPTION));
            entDeduction.setDeductionReference(pstDeduction.getlong(FLD_DEDUCTION_REFERENCE));
            entDeduction.setDeductionIndex(pstDeduction.getInt(FLD_DEDUCTION_INDEX));
            entDeduction.setBenefitConfigId(pstDeduction.getlong(FLD_BENEFIT_CONFIG_ID));
            return entDeduction;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitConfigDeduction(0), DBException.UNKNOWN);
        }
    }
    
    public long fetchExc(Entity entity) throws Exception {
        BenefitConfigDeduction entDeduction = fetchExc(entity.getOID());
        entity = (Entity) entDeduction;
        return entDeduction.getOID();
    }
    
    public static synchronized long updateExc(BenefitConfigDeduction entDeduction) throws DBException {
        try {
            if (entDeduction.getOID() != 0) {
                PstBenefitConfigDeduction pstDeduction = new PstBenefitConfigDeduction(entDeduction.getOID());
                pstDeduction.setInt(FLD_DEDUCTION_PERCENT, entDeduction.getDeductionPercent());
                pstDeduction.setString(FLD_DEDUCTION_DESCRIPTION, entDeduction.getDeductionDescription());
                pstDeduction.setLong(FLD_DEDUCTION_REFERENCE, entDeduction.getDeductionReference());
                pstDeduction.setInt(FLD_DEDUCTION_INDEX, entDeduction.getDeductionIndex());
                pstDeduction.setLong(FLD_BENEFIT_CONFIG_ID, entDeduction.getBenefitConfigId());
                pstDeduction.update();
                return entDeduction.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitConfigDeduction(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long updateExc(Entity entity) throws Exception {
        return updateExc((BenefitConfigDeduction) entity);
    }
    
    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstBenefitConfigDeduction pstDeduction = new PstBenefitConfigDeduction(oid);
            pstDeduction.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitConfigDeduction(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(BenefitConfigDeduction entDeduction) throws DBException {
        try {
            PstBenefitConfigDeduction pstDeduction = new PstBenefitConfigDeduction(0);
            pstDeduction.setInt(FLD_DEDUCTION_PERCENT, entDeduction.getDeductionPercent());
            pstDeduction.setString(FLD_DEDUCTION_DESCRIPTION, entDeduction.getDeductionDescription());
            pstDeduction.setLong(FLD_DEDUCTION_REFERENCE, entDeduction.getDeductionReference());
            pstDeduction.setInt(FLD_DEDUCTION_INDEX, entDeduction.getDeductionIndex());
            pstDeduction.setLong(FLD_BENEFIT_CONFIG_ID, entDeduction.getBenefitConfigId());
            pstDeduction.insert();
            entDeduction.setOID(pstDeduction.getLong(FLD_DEDUCTION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitConfigDeduction(0), DBException.UNKNOWN);
        }
        return entDeduction.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((BenefitConfigDeduction) entity);
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_DEDUCTION;
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
                BenefitConfigDeduction deduction = new BenefitConfigDeduction();
                resultToObject(rs, deduction);
                lists.add(deduction);
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
    
    public static BenefitConfigDeduction getOneRecord(String whereClause) {
        BenefitConfigDeduction deduc = new BenefitConfigDeduction();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_DEDUCTION;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause + " LIMIT 1";
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            BenefitConfigDeduction deduction = new BenefitConfigDeduction();
            resultToObject(rs, deduction);

            rs.close();
            return deduction;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return deduc;
    }
    
    public static BenefitConfigDeduction getOneRecordDesc(long benefitConfigId) {
        BenefitConfigDeduction deduction = new BenefitConfigDeduction();
        DBResultSet dbrs = null;

        try {
            String sql = " SELECT * FROM "+TBL_DEDUCTION+" ";
            sql += " WHERE "+PstBenefitConfigDeduction.fieldNames[PstBenefitConfigDeduction.FLD_BENEFIT_CONFIG_ID]+"="+benefitConfigId+" ";
            sql += " ORDER BY "+PstBenefitConfigDeduction.fieldNames[PstBenefitConfigDeduction.FLD_DEDUCTION_INDEX]+" DESC LIMIT 1";
        
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
    
    public static void resultToObject(ResultSet rs, BenefitConfigDeduction entDeduction) {
        try {
            entDeduction.setOID(rs.getLong(PstBenefitConfigDeduction.fieldNames[PstBenefitConfigDeduction.FLD_DEDUCTION_ID]));
            entDeduction.setDeductionPercent(rs.getInt(PstBenefitConfigDeduction.fieldNames[PstBenefitConfigDeduction.FLD_DEDUCTION_PERCENT]));
            entDeduction.setDeductionDescription(rs.getString(PstBenefitConfigDeduction.fieldNames[PstBenefitConfigDeduction.FLD_DEDUCTION_DESCRIPTION]));
            entDeduction.setDeductionReference(rs.getLong(PstBenefitConfigDeduction.fieldNames[PstBenefitConfigDeduction.FLD_DEDUCTION_REFERENCE]));
            entDeduction.setDeductionIndex(rs.getInt(PstBenefitConfigDeduction.fieldNames[PstBenefitConfigDeduction.FLD_DEDUCTION_INDEX]));
            entDeduction.setBenefitConfigId(rs.getLong(PstBenefitConfigDeduction.fieldNames[PstBenefitConfigDeduction.FLD_BENEFIT_CONFIG_ID]));
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long entDeductionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_DEDUCTION + " WHERE "
                    + PstBenefitConfigDeduction.fieldNames[PstBenefitConfigDeduction.FLD_DEDUCTION_ID] + " = " + entDeductionId;

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
            String sql = "SELECT COUNT(" + PstBenefitConfigDeduction.fieldNames[PstBenefitConfigDeduction.FLD_DEDUCTION_ID] + ") FROM " + TBL_DEDUCTION;
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
                    BenefitConfigDeduction entDeduction = (BenefitConfigDeduction) list.get(ls);
                    if (oid == entDeduction.getOID()) {
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
    
}
