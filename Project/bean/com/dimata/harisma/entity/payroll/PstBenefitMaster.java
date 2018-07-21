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
 * @author Hendra McHen | 20150210
 */
public class PstBenefitMaster extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_BENEFIT_MASTER = "hr_benefit_master";
    public static final int FLD_BENEFIT_MASTER_ID = 0;
    public static final int FLD_PERIOD_FROM = 1;
    public static final int FLD_PERIOD_TO = 2;
    public static final int FLD_CODE = 3;
    public static final int FLD_TITLE = 4;
    public static final int FLD_DESCRIPTION = 5;
    public static final int FLD_COMPANY_STRUCTURE = 6;
    public static final int FLD_PRORATE_EMPLOYEE_PRESENCE = 7;
    public static final int FLD_EMPLOYEE_LEVEL_POINT = 8;
    public static final int FLD_DISTRIBUTION_PART_1 = 9;
    public static final int FLD_DISTRIBUTION_CODE_1 = 10;
    public static final int FLD_DISTRIBUTION_DESCRIPTION_1 = 11;
    public static final int FLD_DISTRIBUTION_TOTAL_1 = 12;
    public static final int FLD_DISTRIBUTION_PART_2 = 13;
    public static final int FLD_DISTRIBUTION_CODE_2 = 14;
    public static final int FLD_DISTRIBUTION_DESCRIPTION_2 = 15;
    public static final int FLD_DISTRIBUTION_TOTAL_2 = 16;
    public static final int FLD_DISTRIBUTION_PART_3 = 17;
    public static final int FLD_DISTRIBUTION_CODE_3 = 18;
    public static final int FLD_DISTRIBUTION_DESCRIPTION_3 = 19;
    public static final int FLD_DISTRIBUTION_TOTAL_3 = 20;
    public static final int FLD_EXCEPTION_NO_BY_CATEGORY = 21;
    public static final int FLD_EXCEPTION_NO_BY_POSITION = 22;
    public static final int FLD_EXCEPTION_NO_BY_PAYROLL = 23;
    public static final int FLD_EXCEPTION_NO_BY_SPECIAL_LEAVE = 24;
    public static final int FLD_EMPLOYEE_BY_ENTITLE = 25;
    public static String[] fieldNames = {
        "BENEFIT_MASTER_ID",
        "PERIOD_FROM",
        "PERIOD_TO",
        "CODE",
        "TITLE",
        "DESCRIPTION",
        "COMPANY_STRUCTURE",
        "PRORATE_EMPLOYEE_PRESENCE",
        "EMPLOYEE_LEVEL_POINT",
        "DISTRIBUTION_PART_1",
        "DISTRIBUTION_CODE_1",
        "DISTRIBUTION_DESCRIPTION_1",
        "DISTRIBUTION_TOTAL_1",
        "DISTRIBUTION_PART_2",
        "DISTRIBUTION_CODE_2",
        "DISTRIBUTION_DESCRIPTION_2",
        "DISTRIBUTION_TOTAL_2",
        "DISTRIBUTION_PART_3",
        "DISTRIBUTION_CODE_3",
        "DISTRIBUTION_DESCRIPTION_3",
        "DISTRIBUTION_TOTAL_3",
        "EXCEPTION_NO_BY_CATEGORY",
        "EXCEPTION_NO_BY_POSITION",
        "EXCEPTION_NO_BY_PAYROLL",
        "EXCEPTION_NO_BY_SPECIAL_LEAVE",
        "EMPLOYEE_BY_ENTITLE"
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
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING
    };

    public static final String[] companyStructurKey = {"Company", "Division", "Department", "Section"};
    public static final int[] companyStructurValue = {0, 1, 2, 3};
    
    public static final String[] prorateKey = {"Working Day", "Total Presence Company"};
    public static final int[] prorateValue = {0, 1};
    
    public static final String[] levelPointKey = {"Not Include", "Include"};
    public static final int[] levelPointValue = {0, 1};
    
    public static final String[] distributionTotalKey = {"Total Employee Entitle", "Total Presence", "Total Level Point"};
    public static final int[] distributionTotalValue = {0, 1, 2};
   
    public PstBenefitMaster() {
    }

    public PstBenefitMaster(int i) throws DBException {
        super(new PstBenefitMaster());
    }

    public PstBenefitMaster(String sOid) throws DBException {
        super(new PstBenefitMaster(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstBenefitMaster(long lOid) throws DBException {
        super(new PstBenefitMaster(0));
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
        return TBL_BENEFIT_MASTER;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstBenefitMaster().getClass().getName();
    }

    public static BenefitMaster fetchExc(long oid) throws DBException {
        try {
            BenefitMaster entBenefitMaster = new BenefitMaster();
            PstBenefitMaster pstBenefitMaster = new PstBenefitMaster(oid);
            entBenefitMaster.setOID(oid);
            entBenefitMaster.setPeriodFrom(pstBenefitMaster.getDate(FLD_PERIOD_FROM));
            entBenefitMaster.setPeriodTo(pstBenefitMaster.getDate(FLD_PERIOD_TO));
            entBenefitMaster.setCode(pstBenefitMaster.getString(FLD_CODE));
            entBenefitMaster.setTitle(pstBenefitMaster.getString(FLD_TITLE));
            entBenefitMaster.setDescription(pstBenefitMaster.getString(FLD_DESCRIPTION));
            entBenefitMaster.setCompanyStructure(pstBenefitMaster.getString(FLD_COMPANY_STRUCTURE));
            entBenefitMaster.setProrateEmployeePresence(pstBenefitMaster.getInt(FLD_PRORATE_EMPLOYEE_PRESENCE));
            entBenefitMaster.setEmployeeLevelPoint(pstBenefitMaster.getInt(FLD_EMPLOYEE_LEVEL_POINT));
            entBenefitMaster.setDistributionPart1(pstBenefitMaster.getString(FLD_DISTRIBUTION_PART_1));
            entBenefitMaster.setDistributionCode1(pstBenefitMaster.getString(FLD_DISTRIBUTION_CODE_1));
            entBenefitMaster.setDistributionDescription1(pstBenefitMaster.getString(FLD_DISTRIBUTION_DESCRIPTION_1));
            entBenefitMaster.setDistributionTotal1(pstBenefitMaster.getInt(FLD_DISTRIBUTION_TOTAL_1));
            entBenefitMaster.setDistributionPart2(pstBenefitMaster.getString(FLD_DISTRIBUTION_PART_2));
            entBenefitMaster.setDistributionCode2(pstBenefitMaster.getString(FLD_DISTRIBUTION_CODE_2));
            entBenefitMaster.setDistributionDescription2(pstBenefitMaster.getString(FLD_DISTRIBUTION_DESCRIPTION_2));
            entBenefitMaster.setDistributionTotal2(pstBenefitMaster.getInt(FLD_DISTRIBUTION_TOTAL_2));
            entBenefitMaster.setDistributionPart3(pstBenefitMaster.getString(FLD_DISTRIBUTION_PART_3));
            entBenefitMaster.setDistributionCode3(pstBenefitMaster.getString(FLD_DISTRIBUTION_CODE_3));
            entBenefitMaster.setDistributionDescription3(pstBenefitMaster.getString(FLD_DISTRIBUTION_DESCRIPTION_3));
            entBenefitMaster.setDistributionTotal3(pstBenefitMaster.getInt(FLD_DISTRIBUTION_TOTAL_3));
            entBenefitMaster.setExceptionNoByCategory(pstBenefitMaster.getString(FLD_EXCEPTION_NO_BY_CATEGORY));
            entBenefitMaster.setExceptionNoByPosition(pstBenefitMaster.getString(FLD_EXCEPTION_NO_BY_POSITION));
            entBenefitMaster.setExceptionNoByPayroll(pstBenefitMaster.getString(FLD_EXCEPTION_NO_BY_PAYROLL));
            entBenefitMaster.setExceptionNoBySpecialLeave(pstBenefitMaster.getString(FLD_EXCEPTION_NO_BY_SPECIAL_LEAVE));
            entBenefitMaster.setEmployeeByEntitle(pstBenefitMaster.getString(FLD_EMPLOYEE_BY_ENTITLE));
            return entBenefitMaster;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitMaster(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        BenefitMaster entBenefitMaster = fetchExc(entity.getOID());
        entity = (Entity) entBenefitMaster;
        return entBenefitMaster.getOID();
    }

    public static synchronized long updateExc(BenefitMaster entBenefitMaster) throws DBException {
        try {
            if (entBenefitMaster.getOID() != 0) {
                PstBenefitMaster pstBenefitMaster = new PstBenefitMaster(entBenefitMaster.getOID());
                pstBenefitMaster.setDate(FLD_PERIOD_FROM, entBenefitMaster.getPeriodFrom());
                pstBenefitMaster.setDate(FLD_PERIOD_TO, entBenefitMaster.getPeriodTo());
                pstBenefitMaster.setString(FLD_CODE, entBenefitMaster.getCode());
                pstBenefitMaster.setString(FLD_TITLE, entBenefitMaster.getTitle());
                pstBenefitMaster.setString(FLD_DESCRIPTION, entBenefitMaster.getDescription());
                pstBenefitMaster.setString(FLD_COMPANY_STRUCTURE, entBenefitMaster.getCompanyStructure());
                pstBenefitMaster.setInt(FLD_PRORATE_EMPLOYEE_PRESENCE, entBenefitMaster.getProrateEmployeePresence());
                pstBenefitMaster.setInt(FLD_EMPLOYEE_LEVEL_POINT, entBenefitMaster.getEmployeeLevelPoint());
                pstBenefitMaster.setString(FLD_DISTRIBUTION_PART_1, entBenefitMaster.getDistributionPart1());
                pstBenefitMaster.setString(FLD_DISTRIBUTION_CODE_1, entBenefitMaster.getDistributionCode1());
                pstBenefitMaster.setString(FLD_DISTRIBUTION_DESCRIPTION_1, entBenefitMaster.getDistributionDescription1());
                pstBenefitMaster.setInt(FLD_DISTRIBUTION_TOTAL_1, entBenefitMaster.getDistributionTotal1());
                pstBenefitMaster.setString(FLD_DISTRIBUTION_PART_2, entBenefitMaster.getDistributionPart2());
                pstBenefitMaster.setString(FLD_DISTRIBUTION_CODE_2, entBenefitMaster.getDistributionCode2());
                pstBenefitMaster.setString(FLD_DISTRIBUTION_DESCRIPTION_2, entBenefitMaster.getDistributionDescription2());
                pstBenefitMaster.setInt(FLD_DISTRIBUTION_TOTAL_2, entBenefitMaster.getDistributionTotal2());
                pstBenefitMaster.setString(FLD_DISTRIBUTION_PART_3, entBenefitMaster.getDistributionPart3());
                pstBenefitMaster.setString(FLD_DISTRIBUTION_CODE_3, entBenefitMaster.getDistributionCode3());
                pstBenefitMaster.setString(FLD_DISTRIBUTION_DESCRIPTION_3, entBenefitMaster.getDistributionDescription3());
                pstBenefitMaster.setInt(FLD_DISTRIBUTION_TOTAL_3, entBenefitMaster.getDistributionTotal3());
                pstBenefitMaster.setString(FLD_EXCEPTION_NO_BY_CATEGORY, entBenefitMaster.getExceptionNoByCategory());
                pstBenefitMaster.setString(FLD_EXCEPTION_NO_BY_POSITION, entBenefitMaster.getExceptionNoByPosition());
                pstBenefitMaster.setString(FLD_EXCEPTION_NO_BY_PAYROLL, entBenefitMaster.getExceptionNoByPayroll());
                pstBenefitMaster.setString(FLD_EXCEPTION_NO_BY_SPECIAL_LEAVE, entBenefitMaster.getExceptionNoBySpecialLeave());
                pstBenefitMaster.setString(FLD_EMPLOYEE_BY_ENTITLE, entBenefitMaster.getEmployeeByEntitle());
                pstBenefitMaster.update();
                return entBenefitMaster.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitMaster(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((BenefitMaster) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstBenefitMaster pstBenefitMaster = new PstBenefitMaster(oid);
            pstBenefitMaster.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitMaster(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(BenefitMaster entBenefitMaster) throws DBException {
        try {
            PstBenefitMaster pstBenefitMaster = new PstBenefitMaster(0);
            pstBenefitMaster.setDate(FLD_PERIOD_FROM, entBenefitMaster.getPeriodFrom());
            pstBenefitMaster.setDate(FLD_PERIOD_TO, entBenefitMaster.getPeriodTo());
            pstBenefitMaster.setString(FLD_CODE, entBenefitMaster.getCode());
            pstBenefitMaster.setString(FLD_TITLE, entBenefitMaster.getTitle());
            pstBenefitMaster.setString(FLD_DESCRIPTION, entBenefitMaster.getDescription());
            pstBenefitMaster.setString(FLD_COMPANY_STRUCTURE, entBenefitMaster.getCompanyStructure());
            pstBenefitMaster.setInt(FLD_PRORATE_EMPLOYEE_PRESENCE, entBenefitMaster.getProrateEmployeePresence());
            pstBenefitMaster.setInt(FLD_EMPLOYEE_LEVEL_POINT, entBenefitMaster.getEmployeeLevelPoint());
            pstBenefitMaster.setString(FLD_DISTRIBUTION_PART_1, entBenefitMaster.getDistributionPart1());
            pstBenefitMaster.setString(FLD_DISTRIBUTION_CODE_1, entBenefitMaster.getDistributionCode1());
            pstBenefitMaster.setString(FLD_DISTRIBUTION_DESCRIPTION_1, entBenefitMaster.getDistributionDescription1());
            pstBenefitMaster.setInt(FLD_DISTRIBUTION_TOTAL_1, entBenefitMaster.getDistributionTotal1());
            pstBenefitMaster.setString(FLD_DISTRIBUTION_PART_2, entBenefitMaster.getDistributionPart2());
            pstBenefitMaster.setString(FLD_DISTRIBUTION_CODE_2, entBenefitMaster.getDistributionCode2());
            pstBenefitMaster.setString(FLD_DISTRIBUTION_DESCRIPTION_2, entBenefitMaster.getDistributionDescription2());
            pstBenefitMaster.setInt(FLD_DISTRIBUTION_TOTAL_2, entBenefitMaster.getDistributionTotal2());
            pstBenefitMaster.setString(FLD_DISTRIBUTION_PART_3, entBenefitMaster.getDistributionPart3());
            pstBenefitMaster.setString(FLD_DISTRIBUTION_CODE_3, entBenefitMaster.getDistributionCode3());
            pstBenefitMaster.setString(FLD_DISTRIBUTION_DESCRIPTION_3, entBenefitMaster.getDistributionDescription3());
            pstBenefitMaster.setInt(FLD_DISTRIBUTION_TOTAL_3, entBenefitMaster.getDistributionTotal3());
            pstBenefitMaster.setString(FLD_EXCEPTION_NO_BY_CATEGORY, entBenefitMaster.getExceptionNoByCategory());
            pstBenefitMaster.setString(FLD_EXCEPTION_NO_BY_POSITION, entBenefitMaster.getExceptionNoByPosition());
            pstBenefitMaster.setString(FLD_EXCEPTION_NO_BY_PAYROLL, entBenefitMaster.getExceptionNoByPayroll());
            pstBenefitMaster.setString(FLD_EXCEPTION_NO_BY_SPECIAL_LEAVE, entBenefitMaster.getExceptionNoBySpecialLeave());
            pstBenefitMaster.setString(FLD_EMPLOYEE_BY_ENTITLE, entBenefitMaster.getEmployeeByEntitle());
            pstBenefitMaster.insert();
            entBenefitMaster.setOID(pstBenefitMaster.getLong(FLD_BENEFIT_MASTER_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitMaster(0), DBException.UNKNOWN);
        }
        return entBenefitMaster.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((BenefitMaster) entity);
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_BENEFIT_MASTER;
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
                BenefitMaster benefitMaster = new BenefitMaster();
                resultToObject(rs, benefitMaster);
                lists.add(benefitMaster);
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
    
    public static void resultToObject(ResultSet rs, BenefitMaster entBenefitMaster) {
        try {
            entBenefitMaster.setOID(rs.getLong(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_BENEFIT_MASTER_ID]));
            entBenefitMaster.setPeriodFrom(rs.getDate(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_PERIOD_FROM]));
            entBenefitMaster.setPeriodTo(rs.getDate(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_PERIOD_TO]));
            entBenefitMaster.setCode(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_CODE]));
            entBenefitMaster.setTitle(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_TITLE]));
            entBenefitMaster.setDescription(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_DESCRIPTION]));
            entBenefitMaster.setCompanyStructure(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_COMPANY_STRUCTURE]));
            entBenefitMaster.setProrateEmployeePresence(rs.getInt(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_PRORATE_EMPLOYEE_PRESENCE]));
            entBenefitMaster.setEmployeeLevelPoint(rs.getInt(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_EMPLOYEE_LEVEL_POINT]));
            entBenefitMaster.setDistributionPart1(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_DISTRIBUTION_PART_1]));
            entBenefitMaster.setDistributionCode1(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_DISTRIBUTION_CODE_1]));
            entBenefitMaster.setDistributionDescription1(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_DISTRIBUTION_DESCRIPTION_1]));
            entBenefitMaster.setDistributionTotal1(rs.getInt(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_DISTRIBUTION_TOTAL_1]));
            entBenefitMaster.setDistributionPart2(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_DISTRIBUTION_PART_2]));
            entBenefitMaster.setDistributionCode2(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_DISTRIBUTION_CODE_2]));
            entBenefitMaster.setDistributionDescription2(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_DISTRIBUTION_DESCRIPTION_2]));
            entBenefitMaster.setDistributionTotal2(rs.getInt(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_DISTRIBUTION_TOTAL_2]));
            entBenefitMaster.setDistributionPart3(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_DISTRIBUTION_PART_3]));
            entBenefitMaster.setDistributionCode3(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_DISTRIBUTION_CODE_3]));
            entBenefitMaster.setDistributionDescription3(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_DISTRIBUTION_DESCRIPTION_3]));
            entBenefitMaster.setDistributionTotal3(rs.getInt(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_DISTRIBUTION_TOTAL_3]));
            entBenefitMaster.setExceptionNoByCategory(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_EXCEPTION_NO_BY_CATEGORY]));
            entBenefitMaster.setExceptionNoByPosition(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_EXCEPTION_NO_BY_POSITION]));
            entBenefitMaster.setExceptionNoByPayroll(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_EXCEPTION_NO_BY_PAYROLL]));
            entBenefitMaster.setExceptionNoBySpecialLeave(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_EXCEPTION_NO_BY_SPECIAL_LEAVE]));
            entBenefitMaster.setEmployeeByEntitle(rs.getString(PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_EMPLOYEE_BY_ENTITLE]));
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long entBenefitMasterId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_BENEFIT_MASTER + " WHERE "
                    + PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_BENEFIT_MASTER_ID] + " = " + entBenefitMasterId;

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
            String sql = "SELECT COUNT(" + PstBenefitMaster.fieldNames[PstBenefitMaster.FLD_BENEFIT_MASTER_ID] + ") FROM " + TBL_BENEFIT_MASTER;
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
                    BenefitMaster entBenefitMaster = (BenefitMaster) list.get(ls);
                    if (oid == entBenefitMaster.getOID()) {
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