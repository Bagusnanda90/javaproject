/**
 * Description : Benefit Config Persistent
 * Date : Feb, 21 2015
 * @author Hendra Putu
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

public class PstBenefitConfig extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_BENEFIT_CONFIG = "pay_benefit_config";
    public static final int FLD_BENEFIT_CONFIG_ID = 0;
    public static final int FLD_PERIOD_FROM = 1;
    public static final int FLD_PERIOD_TO = 2;
    public static final int FLD_CODE = 3;
    public static final int FLD_TITLE = 4;
    public static final int FLD_DESCRIPTION = 5;
    public static final int FLD_DISTRIBUTION_PART_1 = 6;
    public static final int FLD_DISTRIBUTION_PERCENT_1 = 7;
    public static final int FLD_DISTRIBUTION_DESCRIPTION_1 = 8;
    public static final int FLD_DISTRIBUTION_TOTAL_1 = 9;
    public static final int FLD_DISTRIBUTION_PART_2 = 10;
    public static final int FLD_DISTRIBUTION_PERCENT_2 = 11;
    public static final int FLD_DISTRIBUTION_DESCRIPTION_2 = 12;
    public static final int FLD_DISTRIBUTION_TOTAL_2 = 13;
    public static final int FLD_EXCEPTION_NO_BY_CATEGORY = 14;
    public static final int FLD_EXCEPTION_NO_BY_POSITION = 15;
    public static final int FLD_EXCEPTION_NO_BY_PAYROLL = 16;
    public static final int FLD_EXCEPTION_NO_BY_DIVISION = 17;
    public static final int FLD_APPROVE_1_EMP_ID = 18;
    public static final int FLD_APPROVE_2_EMP_ID = 19;
    
    public static String[] fieldNames = {
        "BENEFIT_CONFIG_ID",
        "PERIOD_FROM",
        "PERIOD_TO",
        "CODE",
        "TITLE",
        "DESCRIPTION",
        "DISTRIBUTION_PART_1",
        "DISTRIBUTION_PERCENT_1",
        "DISTRIBUTION_DESCRIPTION_1",
        "DISTRIBUTION_TOTAL_1",
        "DISTRIBUTION_PART_2",
        "DISTRIBUTION_PERCENT_2",
        "DISTRIBUTION_DESCRIPTION_2",
        "DISTRIBUTION_TOTAL_2",
        "EXCEPTION_NO_BY_CATEGORY",
        "EXCEPTION_NO_BY_POSITION",
        "EXCEPTION_NO_BY_PAYROLL",
        "EXCEPTION_NO_BY_DIVISION",
        "APPROVE_1_EMP_ID",
        "APPROVE_2_EMP_ID"
    };
    
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG
    };

    public static final String[] distributionTotalKey = {"Employee Entitle", "Level Point"};
    public static final int[] distributionTotalValue = {0, 1};
    
    public PstBenefitConfig() {
    }

    public PstBenefitConfig(int i) throws DBException {
        super(new PstBenefitConfig());
    }

    public PstBenefitConfig(String sOid) throws DBException {
        super(new PstBenefitConfig(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstBenefitConfig(long lOid) throws DBException {
        super(new PstBenefitConfig(0));
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
        return TBL_BENEFIT_CONFIG;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstBenefitConfig().getClass().getName();
    }

    public static BenefitConfig fetchExc(long oid) throws DBException {
        try {
            BenefitConfig entBenefitConfig = new BenefitConfig();
            PstBenefitConfig pstBenefitConfig = new PstBenefitConfig(oid);
            entBenefitConfig.setOID(oid);
            entBenefitConfig.setPeriodFrom(pstBenefitConfig.getDate(FLD_PERIOD_FROM));
            entBenefitConfig.setPeriodTo(pstBenefitConfig.getDate(FLD_PERIOD_TO));
            entBenefitConfig.setCode(pstBenefitConfig.getString(FLD_CODE));
            entBenefitConfig.setTitle(pstBenefitConfig.getString(FLD_TITLE));
            entBenefitConfig.setDescription(pstBenefitConfig.getString(FLD_DESCRIPTION));
            entBenefitConfig.setDistributionPart1(pstBenefitConfig.getString(FLD_DISTRIBUTION_PART_1));
            entBenefitConfig.setDistributionPercent1(pstBenefitConfig.getInt(FLD_DISTRIBUTION_PERCENT_1));
            entBenefitConfig.setDistributionDescription1(pstBenefitConfig.getString(FLD_DISTRIBUTION_DESCRIPTION_1));
            entBenefitConfig.setDistributionTotal1(pstBenefitConfig.getInt(FLD_DISTRIBUTION_TOTAL_1));
            entBenefitConfig.setDistributionPart2(pstBenefitConfig.getString(FLD_DISTRIBUTION_PART_2));
            entBenefitConfig.setDistributionPercent2(pstBenefitConfig.getInt(FLD_DISTRIBUTION_PERCENT_2));
            entBenefitConfig.setDistributionDescription2(pstBenefitConfig.getString(FLD_DISTRIBUTION_DESCRIPTION_2));
            entBenefitConfig.setDistributionTotal2(pstBenefitConfig.getInt(FLD_DISTRIBUTION_TOTAL_2));
            entBenefitConfig.setExceptionNoByCategory(pstBenefitConfig.getString(FLD_EXCEPTION_NO_BY_CATEGORY));
            entBenefitConfig.setExceptionNoByPosition(pstBenefitConfig.getString(FLD_EXCEPTION_NO_BY_POSITION));
            entBenefitConfig.setExceptionNoByPayroll(pstBenefitConfig.getString(FLD_EXCEPTION_NO_BY_PAYROLL));
            entBenefitConfig.setExceptionNoByDivision(pstBenefitConfig.getString(FLD_EXCEPTION_NO_BY_DIVISION));
            entBenefitConfig.setApprove1EmpId(pstBenefitConfig.getLong(FLD_APPROVE_1_EMP_ID));
            entBenefitConfig.setApprove2EmpId(pstBenefitConfig.getLong(FLD_APPROVE_2_EMP_ID));
            return entBenefitConfig;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitConfig(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        BenefitConfig entBenefitConfig = fetchExc(entity.getOID());
        entity = (Entity) entBenefitConfig;
        return entBenefitConfig.getOID();
    }

    public static synchronized long updateExc(BenefitConfig entBenefitConfig) throws DBException {
        try {
            if (entBenefitConfig.getOID() != 0) {
                PstBenefitConfig pstBenefitConfig = new PstBenefitConfig(entBenefitConfig.getOID());
                pstBenefitConfig.setDate(FLD_PERIOD_FROM, entBenefitConfig.getPeriodFrom());
                pstBenefitConfig.setDate(FLD_PERIOD_TO, entBenefitConfig.getPeriodTo());
                pstBenefitConfig.setString(FLD_CODE, entBenefitConfig.getCode());
                pstBenefitConfig.setString(FLD_TITLE, entBenefitConfig.getTitle());
                pstBenefitConfig.setString(FLD_DESCRIPTION, entBenefitConfig.getDescription());
                pstBenefitConfig.setString(FLD_DISTRIBUTION_PART_1, entBenefitConfig.getDistributionPart1());
                pstBenefitConfig.setInt(FLD_DISTRIBUTION_PERCENT_1, entBenefitConfig.getDistributionPercent1());
                pstBenefitConfig.setString(FLD_DISTRIBUTION_DESCRIPTION_1, entBenefitConfig.getDistributionDescription1());
                pstBenefitConfig.setInt(FLD_DISTRIBUTION_TOTAL_1, entBenefitConfig.getDistributionTotal1());
                pstBenefitConfig.setString(FLD_DISTRIBUTION_PART_2, entBenefitConfig.getDistributionPart2());
                pstBenefitConfig.setInt(FLD_DISTRIBUTION_PERCENT_2, entBenefitConfig.getDistributionPercent2());
                pstBenefitConfig.setString(FLD_DISTRIBUTION_DESCRIPTION_2, entBenefitConfig.getDistributionDescription2());
                pstBenefitConfig.setInt(FLD_DISTRIBUTION_TOTAL_2, entBenefitConfig.getDistributionTotal2());
                pstBenefitConfig.setString(FLD_EXCEPTION_NO_BY_CATEGORY, entBenefitConfig.getExceptionNoByCategory());
                pstBenefitConfig.setString(FLD_EXCEPTION_NO_BY_POSITION, entBenefitConfig.getExceptionNoByPosition());
                pstBenefitConfig.setString(FLD_EXCEPTION_NO_BY_PAYROLL, entBenefitConfig.getExceptionNoByPayroll());
                pstBenefitConfig.setString(FLD_EXCEPTION_NO_BY_DIVISION, entBenefitConfig.getExceptionNoByDivision());
                pstBenefitConfig.setLong(FLD_APPROVE_1_EMP_ID, entBenefitConfig.getApprove1EmpId());
                pstBenefitConfig.setLong(FLD_APPROVE_2_EMP_ID, entBenefitConfig.getApprove2EmpId());
                pstBenefitConfig.update();
                return entBenefitConfig.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitConfig(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((BenefitConfig) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstBenefitConfig pstBenefitConfig = new PstBenefitConfig(oid);
            pstBenefitConfig.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitConfig(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(BenefitConfig entBenefitConfig) throws DBException {
        try {
            PstBenefitConfig pstBenefitConfig = new PstBenefitConfig(0);
            pstBenefitConfig.setDate(FLD_PERIOD_FROM, entBenefitConfig.getPeriodFrom());
            pstBenefitConfig.setDate(FLD_PERIOD_TO, entBenefitConfig.getPeriodTo());
            pstBenefitConfig.setString(FLD_CODE, entBenefitConfig.getCode());
            pstBenefitConfig.setString(FLD_TITLE, entBenefitConfig.getTitle());
            pstBenefitConfig.setString(FLD_DESCRIPTION, entBenefitConfig.getDescription());
            pstBenefitConfig.setString(FLD_DISTRIBUTION_PART_1, entBenefitConfig.getDistributionPart1());
            pstBenefitConfig.setInt(FLD_DISTRIBUTION_PERCENT_1, entBenefitConfig.getDistributionPercent1());
            pstBenefitConfig.setString(FLD_DISTRIBUTION_DESCRIPTION_1, entBenefitConfig.getDistributionDescription1());
            pstBenefitConfig.setInt(FLD_DISTRIBUTION_TOTAL_1, entBenefitConfig.getDistributionTotal1());
            pstBenefitConfig.setString(FLD_DISTRIBUTION_PART_2, entBenefitConfig.getDistributionPart2());
            pstBenefitConfig.setInt(FLD_DISTRIBUTION_PERCENT_2, entBenefitConfig.getDistributionPercent2());
            pstBenefitConfig.setString(FLD_DISTRIBUTION_DESCRIPTION_2, entBenefitConfig.getDistributionDescription2());
            pstBenefitConfig.setInt(FLD_DISTRIBUTION_TOTAL_2, entBenefitConfig.getDistributionTotal2());
            pstBenefitConfig.setString(FLD_EXCEPTION_NO_BY_CATEGORY, entBenefitConfig.getExceptionNoByCategory());
            pstBenefitConfig.setString(FLD_EXCEPTION_NO_BY_POSITION, entBenefitConfig.getExceptionNoByPosition());
            pstBenefitConfig.setString(FLD_EXCEPTION_NO_BY_PAYROLL, entBenefitConfig.getExceptionNoByPayroll());
            pstBenefitConfig.setString(FLD_EXCEPTION_NO_BY_DIVISION, entBenefitConfig.getExceptionNoByDivision());
            pstBenefitConfig.setLong(FLD_APPROVE_1_EMP_ID, entBenefitConfig.getApprove1EmpId());
            pstBenefitConfig.setLong(FLD_APPROVE_2_EMP_ID, entBenefitConfig.getApprove2EmpId());
            pstBenefitConfig.insert();
            entBenefitConfig.setOID(pstBenefitConfig.getLong(FLD_BENEFIT_CONFIG_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitConfig(0), DBException.UNKNOWN);
        }
        return entBenefitConfig.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((BenefitConfig) entity);
    }

    public static void resultToObject(ResultSet rs, BenefitConfig entBenefitConfig) {
        try {
            entBenefitConfig.setOID(rs.getLong(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_BENEFIT_CONFIG_ID]));
            entBenefitConfig.setPeriodFrom(rs.getDate(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_PERIOD_FROM]));
            entBenefitConfig.setPeriodTo(rs.getDate(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_PERIOD_TO]));
            entBenefitConfig.setCode(rs.getString(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_CODE]));
            entBenefitConfig.setTitle(rs.getString(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_TITLE]));
            entBenefitConfig.setDescription(rs.getString(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_DESCRIPTION]));
            entBenefitConfig.setDistributionPart1(rs.getString(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_DISTRIBUTION_PART_1]));
            entBenefitConfig.setDistributionPercent1(rs.getInt(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_DISTRIBUTION_PERCENT_1]));
            entBenefitConfig.setDistributionDescription1(rs.getString(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_DISTRIBUTION_DESCRIPTION_1]));
            entBenefitConfig.setDistributionTotal1(rs.getInt(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_DISTRIBUTION_TOTAL_1]));
            entBenefitConfig.setDistributionPart2(rs.getString(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_DISTRIBUTION_PART_2]));
            entBenefitConfig.setDistributionPercent2(rs.getInt(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_DISTRIBUTION_PERCENT_2]));
            entBenefitConfig.setDistributionDescription2(rs.getString(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_DISTRIBUTION_DESCRIPTION_2]));
            entBenefitConfig.setDistributionTotal2(rs.getInt(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_DISTRIBUTION_TOTAL_2]));
            entBenefitConfig.setExceptionNoByCategory(rs.getString(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_EXCEPTION_NO_BY_CATEGORY]));
            entBenefitConfig.setExceptionNoByPosition(rs.getString(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_EXCEPTION_NO_BY_POSITION]));
            entBenefitConfig.setExceptionNoByPayroll(rs.getString(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_EXCEPTION_NO_BY_PAYROLL]));
            entBenefitConfig.setExceptionNoByDivision(rs.getString(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_EXCEPTION_NO_BY_DIVISION]));
            entBenefitConfig.setApprove1EmpId(rs.getLong(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_APPROVE_1_EMP_ID]));
            entBenefitConfig.setApprove2EmpId(rs.getLong(PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_APPROVE_2_EMP_ID]));
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
            String sql = "SELECT * FROM " + TBL_BENEFIT_CONFIG;
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
                BenefitConfig benefitConfig = new BenefitConfig();
                resultToObject(rs, benefitConfig);
                lists.add(benefitConfig);
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
    
    public static boolean checkOID(long entBenefitConfigId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_BENEFIT_CONFIG + " WHERE "
                    + PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_BENEFIT_CONFIG_ID] + " = " + entBenefitConfigId;

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
            String sql = "SELECT COUNT(" + PstBenefitConfig.fieldNames[PstBenefitConfig.FLD_BENEFIT_CONFIG_ID] + ") FROM " + TBL_BENEFIT_CONFIG;
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
                    BenefitConfig entBenefitConfig = (BenefitConfig) list.get(ls);
                    if (oid == entBenefitConfig.getOID()) {
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
