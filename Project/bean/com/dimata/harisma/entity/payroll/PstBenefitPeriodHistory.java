/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

/**
 *
 * @author Dimata 007
 */
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

public class PstBenefitPeriodHistory extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_BENEFIT_PERIOD_HISTORY = "pay_benefit_period_history";
    public static final int FLD_BENEFIT_PERIOD_HISTORY_ID = 0;
    public static final int FLD_BENEFIT_PERIOD_ID = 1;
    public static final int FLD_BENEFIT_CONFIGURATION = 2;
    public static final int FLD_PERIOD_FROM = 3;
    public static final int FLD_PERIOD_TO = 4;
    public static final int FLD_PAYROLL_PERIOD = 5;
    public static final int FLD_TOTAL_REVENUE = 6;
    public static final int FLD_DISTRIBUTION_VALUE = 7;
    public static final int FLD_DISTRIBUTION_DESC_1 = 8;
    public static final int FLD_DISTRIBUTION_DESC_2 = 9;
    public static final int FLD_DISTRIBUTION_PERCENT_1 = 10;
    public static final int FLD_DISTRIBUTION_PERCENT_2 = 11;
    public static final int FLD_PART_1_TOTAL_DIVIDER = 12;
    public static final int FLD_PART_2_TOTAL_DIVIDER = 13;
    public static final int FLD_PART_1_VALUE = 14;
    public static final int FLD_PART_2_VALUE = 15;
    public static final int FLD_DOC_STATUS = 16;
    public static final int FLD_APPROVE_1 = 17;
    public static final int FLD_APPROVE_2 = 18;
    public static final int FLD_CREATED_BY = 19;

    public static String[] fieldNames = {
        "BENEFIT_PERIOD_HISTORY_ID",
        "BENEFIT_PERIOD_ID",
        "BENEFIT_CONFIGURATION",
        "PERIOD_FROM",
        "PERIOD_TO",
        "PAYROLL_PERIOD",
        "TOTAL_REVENUE",
        "DISTRIBUTION_VALUE",
        "DISTRIBUTION_DESC_1",
        "DISTRIBUTION_DESC_2",
        "DISTRIBUTION_PERCENT_1",
        "DISTRIBUTION_PERCENT_2",
        "PART_1_TOTAL_DIVIDER",
        "PART_2_TOTAL_DIVIDER",
        "PART_1_VALUE",
        "PART_2_VALUE",
        "DOC_STATUS",
        "APPROVE_1",
        "APPROVE_2",
        "CREATED_BY"
    };

    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING
    };
    
    public PstBenefitPeriodHistory() {
    }

    public PstBenefitPeriodHistory(int i) throws DBException {
        super(new PstBenefitPeriodHistory());
    }

    public PstBenefitPeriodHistory(String sOid) throws DBException {
        super(new PstBenefitPeriodHistory(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstBenefitPeriodHistory(long lOid) throws DBException {
        super(new PstBenefitPeriodHistory(0));
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
        return TBL_BENEFIT_PERIOD_HISTORY;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstBenefitPeriodHistory().getClass().getName();
    }

    public static BenefitPeriodHistory fetchExc(long oid) throws DBException {
        try {
            BenefitPeriodHistory entBenefitPeriodHistory = new BenefitPeriodHistory();
            PstBenefitPeriodHistory pstBenefitPeriodHistory = new PstBenefitPeriodHistory(oid);
            entBenefitPeriodHistory.setOID(oid);
            entBenefitPeriodHistory.setBenefitPeriodId(pstBenefitPeriodHistory.getLong(FLD_BENEFIT_PERIOD_ID));
            entBenefitPeriodHistory.setBenefitConfiguration(pstBenefitPeriodHistory.getString(FLD_BENEFIT_CONFIGURATION));
            entBenefitPeriodHistory.setPeriodFrom(pstBenefitPeriodHistory.getString(FLD_PERIOD_FROM));
            entBenefitPeriodHistory.setPeriodTo(pstBenefitPeriodHistory.getString(FLD_PERIOD_TO));
            entBenefitPeriodHistory.setPayrollPeriod(pstBenefitPeriodHistory.getString(FLD_PAYROLL_PERIOD));
            entBenefitPeriodHistory.setTotalRevenue(pstBenefitPeriodHistory.getdouble(FLD_TOTAL_REVENUE));
            entBenefitPeriodHistory.setDistributionValue(pstBenefitPeriodHistory.getdouble(FLD_DISTRIBUTION_VALUE));
            entBenefitPeriodHistory.setDistributionDesc1(pstBenefitPeriodHistory.getString(FLD_DISTRIBUTION_DESC_1));
            entBenefitPeriodHistory.setDistributionDesc2(pstBenefitPeriodHistory.getString(FLD_DISTRIBUTION_DESC_2));
            entBenefitPeriodHistory.setDistributionPercent1(pstBenefitPeriodHistory.getInt(FLD_DISTRIBUTION_PERCENT_1));
            entBenefitPeriodHistory.setDistributionPercent2(pstBenefitPeriodHistory.getInt(FLD_DISTRIBUTION_PERCENT_2));
            entBenefitPeriodHistory.setPart1TotalDivider(pstBenefitPeriodHistory.getInt(FLD_PART_1_TOTAL_DIVIDER));
            entBenefitPeriodHistory.setPart2TotalDivider(pstBenefitPeriodHistory.getInt(FLD_PART_2_TOTAL_DIVIDER));
            entBenefitPeriodHistory.setPart1Value(pstBenefitPeriodHistory.getdouble(FLD_PART_1_VALUE));
            entBenefitPeriodHistory.setPart2Value(pstBenefitPeriodHistory.getdouble(FLD_PART_2_VALUE));
            entBenefitPeriodHistory.setDocStatus(pstBenefitPeriodHistory.getString(FLD_DOC_STATUS));
            entBenefitPeriodHistory.setApprove1(pstBenefitPeriodHistory.getString(FLD_APPROVE_1));
            entBenefitPeriodHistory.setApprove2(pstBenefitPeriodHistory.getString(FLD_APPROVE_2));
            entBenefitPeriodHistory.setCreatedBy(pstBenefitPeriodHistory.getString(FLD_CREATED_BY));
            return entBenefitPeriodHistory;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriodHistory(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        BenefitPeriodHistory entBenefitPeriodHistory = fetchExc(entity.getOID());
        entity = (Entity) entBenefitPeriodHistory;
        return entBenefitPeriodHistory.getOID();
    }

    public static synchronized long updateExc(BenefitPeriodHistory entBenefitPeriodHistory) throws DBException {
        try {
            if (entBenefitPeriodHistory.getOID() != 0) {
                PstBenefitPeriodHistory pstBenefitPeriodHistory = new PstBenefitPeriodHistory(entBenefitPeriodHistory.getOID());
                pstBenefitPeriodHistory.setLong(FLD_BENEFIT_PERIOD_ID, entBenefitPeriodHistory.getBenefitPeriodId());
                pstBenefitPeriodHistory.setString(FLD_BENEFIT_CONFIGURATION, entBenefitPeriodHistory.getBenefitConfiguration());
                pstBenefitPeriodHistory.setString(FLD_PERIOD_FROM, entBenefitPeriodHistory.getPeriodFrom());
                pstBenefitPeriodHistory.setString(FLD_PERIOD_TO, entBenefitPeriodHistory.getPeriodTo());
                pstBenefitPeriodHistory.setString(FLD_PAYROLL_PERIOD, entBenefitPeriodHistory.getPayrollPeriod());
                pstBenefitPeriodHistory.setDouble(FLD_TOTAL_REVENUE, entBenefitPeriodHistory.getTotalRevenue());
                pstBenefitPeriodHistory.setDouble(FLD_DISTRIBUTION_VALUE, entBenefitPeriodHistory.getDistributionValue());
                pstBenefitPeriodHistory.setString(FLD_DISTRIBUTION_DESC_1, entBenefitPeriodHistory.getDistributionDesc1());
                pstBenefitPeriodHistory.setString(FLD_DISTRIBUTION_DESC_2, entBenefitPeriodHistory.getDistributionDesc2());
                pstBenefitPeriodHistory.setInt(FLD_DISTRIBUTION_PERCENT_1, entBenefitPeriodHistory.getDistributionPercent1());
                pstBenefitPeriodHistory.setInt(FLD_DISTRIBUTION_PERCENT_2, entBenefitPeriodHistory.getDistributionPercent2());
                pstBenefitPeriodHistory.setInt(FLD_PART_1_TOTAL_DIVIDER, entBenefitPeriodHistory.getPart1TotalDivider());
                pstBenefitPeriodHistory.setInt(FLD_PART_2_TOTAL_DIVIDER, entBenefitPeriodHistory.getPart2TotalDivider());
                pstBenefitPeriodHistory.setDouble(FLD_PART_1_VALUE, entBenefitPeriodHistory.getPart1Value());
                pstBenefitPeriodHistory.setDouble(FLD_PART_2_VALUE, entBenefitPeriodHistory.getPart2Value());
                pstBenefitPeriodHistory.setString(FLD_DOC_STATUS, entBenefitPeriodHistory.getDocStatus());
                pstBenefitPeriodHistory.setString(FLD_APPROVE_1, entBenefitPeriodHistory.getApprove1());
                pstBenefitPeriodHistory.setString(FLD_APPROVE_2, entBenefitPeriodHistory.getApprove2());
                pstBenefitPeriodHistory.setString(FLD_CREATED_BY, entBenefitPeriodHistory.getCreatedBy());
                pstBenefitPeriodHistory.update();
                return entBenefitPeriodHistory.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriodHistory(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((BenefitPeriodHistory) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstBenefitPeriodHistory pstBenefitPeriodHistory = new PstBenefitPeriodHistory(oid);
            pstBenefitPeriodHistory.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriodHistory(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(BenefitPeriodHistory entBenefitPeriodHistory) throws DBException {
        try {
            PstBenefitPeriodHistory pstBenefitPeriodHistory = new PstBenefitPeriodHistory(0);
            pstBenefitPeriodHistory.setLong(FLD_BENEFIT_PERIOD_ID, entBenefitPeriodHistory.getBenefitPeriodId());
            pstBenefitPeriodHistory.setString(FLD_BENEFIT_CONFIGURATION, entBenefitPeriodHistory.getBenefitConfiguration());
            pstBenefitPeriodHistory.setString(FLD_PERIOD_FROM, entBenefitPeriodHistory.getPeriodFrom());
            pstBenefitPeriodHistory.setString(FLD_PERIOD_TO, entBenefitPeriodHistory.getPeriodTo());
            pstBenefitPeriodHistory.setString(FLD_PAYROLL_PERIOD, entBenefitPeriodHistory.getPayrollPeriod());
            pstBenefitPeriodHistory.setDouble(FLD_TOTAL_REVENUE, entBenefitPeriodHistory.getTotalRevenue());
            pstBenefitPeriodHistory.setDouble(FLD_DISTRIBUTION_VALUE, entBenefitPeriodHistory.getDistributionValue());
            pstBenefitPeriodHistory.setString(FLD_DISTRIBUTION_DESC_1, entBenefitPeriodHistory.getDistributionDesc1());
            pstBenefitPeriodHistory.setString(FLD_DISTRIBUTION_DESC_2, entBenefitPeriodHistory.getDistributionDesc2());
            pstBenefitPeriodHistory.setInt(FLD_DISTRIBUTION_PERCENT_1, entBenefitPeriodHistory.getDistributionPercent1());
            pstBenefitPeriodHistory.setInt(FLD_DISTRIBUTION_PERCENT_2, entBenefitPeriodHistory.getDistributionPercent2());
            pstBenefitPeriodHistory.setInt(FLD_PART_1_TOTAL_DIVIDER, entBenefitPeriodHistory.getPart1TotalDivider());
            pstBenefitPeriodHistory.setInt(FLD_PART_2_TOTAL_DIVIDER, entBenefitPeriodHistory.getPart2TotalDivider());
            pstBenefitPeriodHistory.setDouble(FLD_PART_1_VALUE, entBenefitPeriodHistory.getPart1Value());
            pstBenefitPeriodHistory.setDouble(FLD_PART_2_VALUE, entBenefitPeriodHistory.getPart2Value());
            pstBenefitPeriodHistory.setString(FLD_DOC_STATUS, entBenefitPeriodHistory.getDocStatus());
            pstBenefitPeriodHistory.setString(FLD_APPROVE_1, entBenefitPeriodHistory.getApprove1());
            pstBenefitPeriodHistory.setString(FLD_APPROVE_2, entBenefitPeriodHistory.getApprove2());
            pstBenefitPeriodHistory.setString(FLD_CREATED_BY, entBenefitPeriodHistory.getCreatedBy());
            pstBenefitPeriodHistory.insert();
            entBenefitPeriodHistory.setOID(pstBenefitPeriodHistory.getLong(FLD_BENEFIT_PERIOD_HISTORY_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriodHistory(0), DBException.UNKNOWN);
        }
        return entBenefitPeriodHistory.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((BenefitPeriodHistory) entity);
    }

    public static void insertHistory(BenefitPeriod benefitPeriod){
        BenefitPeriodHistory history = new BenefitPeriodHistory();
        history.setBenefitPeriodId(benefitPeriod.getOID());
        BenefitConfig bConfig = new BenefitConfig();
        try {
            bConfig = PstBenefitConfig.fetchExc(benefitPeriod.getBenefitConfigId());
        } catch (Exception ex){
            System.out.println(ex.toString());
        }
        history.setBenefitConfiguration(bConfig.getTitle());
        history.setPeriodFrom(""+Formater.formatDate(benefitPeriod.getPeriodFrom()));
        history.setPeriodTo(""+Formater.formatDate(benefitPeriod.getPeriodTo()));
        PayPeriod payPeriod = new PayPeriod();
        try {
            payPeriod = PstPayPeriod.fetchExc(benefitPeriod.getPeriodId());
        } catch (Exception ex){
            System.out.println(ex.toString());
        }
        history.setPayrollPeriod(payPeriod.getPeriod());
        history.setTotalRevenue(benefitPeriod.getTotalRevenue());
        history.setDistributionValue(1000);
        history.setDistributionDesc1(bConfig.getDistributionDescription1());
        history.setDistributionDesc2(bConfig.getDistributionDescription2());
        history.setDistributionPercent1(bConfig.getDistributionPercent1());
        history.setDistributionPercent2(bConfig.getDistributionPercent2());
        history.setPart1TotalDivider(benefitPeriod.getPart1TotalDivider());
        history.setPart2TotalDivider(benefitPeriod.getPart2TotalDivider());
        history.setPart1Value(benefitPeriod.getPart1Value());
        history.setPart2Value(benefitPeriod.getPart2Value());
        history.setDocStatus("Closed");
        history.setApprove1(getNameEmployee(benefitPeriod.getApprove1EmpId()));
        history.setApprove2(getNameEmployee(benefitPeriod.getApprove2EmpId()));
        history.setCreatedBy(getNameEmployee(benefitPeriod.getCreateEmpId()));
        long historyId = 0;
        try {
            historyId = insertExc(history);
        } catch (Exception ex){
            System.out.println(ex.toString());
        }
        /* to deduction history */
        DeductionHistory dedHistory = new DeductionHistory();
        String whBPD = " BENEFIT_PERIOD_ID="+benefitPeriod.getOID();
        Vector listBPDeduction = PstBenefitPeriodDeduction.list(0, 0, whBPD, "");
        double distributionValue = 0;
        if (listBPDeduction != null && listBPDeduction.size() > 0){
            for(int a=0; a<listBPDeduction.size(); a++){
                BenefitPeriodDeduction bpDeduction = (BenefitPeriodDeduction)listBPDeduction.get(a);
                dedHistory.setBenefitPeriodHistoryId(historyId);
                BenefitConfigDeduction bConfigDed = new BenefitConfigDeduction();
                try {
                    bConfigDed = PstBenefitConfigDeduction.fetchExc(bpDeduction.getDeductionId());
                } catch (Exception ex){
                    System.out.println(ex.toString());
                }
                dedHistory.setPercen(bConfigDed.getDeductionPercent());
                dedHistory.setDescription(bConfigDed.getDeductionDescription());
                double reference = 0;
                if (bConfigDed.getDeductionReference() == 0){
                    reference = benefitPeriod.getTotalRevenue();
                } else {
                    String whR = " BENEFIT_PERIOD_ID="+benefitPeriod.getOID()+" AND DEDUCTION_ID="+bConfigDed.getDeductionReference()+" ";
                    Vector listRef = PstBenefitPeriodDeduction.list(0, 0, whR, "");
                    if (listRef != null && listRef.size()>0){
                        for (int r = 0; r < listRef.size(); r++){
                            BenefitPeriodDeduction bpd = (BenefitPeriodDeduction)listRef.get(r);
                            reference = bpd.getDeductionResult();
                        }
                    }
                }
                double persenResult = (reference * bConfigDed.getDeductionPercent())/100;
                dedHistory.setPercenResult(persenResult);
                dedHistory.setDeductionResult(bpDeduction.getDeductionResult());
                distributionValue = bpDeduction.getDeductionResult();
                try {
                    PstDeductionHistory.insertExc(dedHistory);
                } catch(Exception ex){
                    System.out.println(ex.toString());
                }
            }
        }
        /* update history distributionValue */
        try {
            history.setOID(historyId);
            history.setDistributionValue(distributionValue);
            updateExc(history);
        } catch(Exception ex){
            System.out.println(ex.toString());
        }
        
        
    }
    
    public static String getNameEmployee(long oid){
        String fullName = "Not yet approved";
        if (oid != 0){
            try {
                Employee emp = PstEmployee.fetchExc(oid);
                fullName = emp.getFullName();
                return fullName;
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
        return fullName;
    }
    
    public static void resultToObject(ResultSet rs, BenefitPeriodHistory entBenefitPeriodHistory) {
        try {
            entBenefitPeriodHistory.setOID(rs.getLong(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_BENEFIT_PERIOD_HISTORY_ID]));
            entBenefitPeriodHistory.setBenefitPeriodId(rs.getLong(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_BENEFIT_PERIOD_ID]));
            entBenefitPeriodHistory.setBenefitConfiguration(rs.getString(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_BENEFIT_CONFIGURATION]));
            entBenefitPeriodHistory.setPeriodFrom(rs.getString(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_PERIOD_FROM]));
            entBenefitPeriodHistory.setPeriodTo(rs.getString(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_PERIOD_TO]));
            entBenefitPeriodHistory.setPayrollPeriod(rs.getString(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_PAYROLL_PERIOD]));
            entBenefitPeriodHistory.setTotalRevenue(rs.getDouble(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_TOTAL_REVENUE]));
            entBenefitPeriodHistory.setDistributionValue(rs.getDouble(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_DISTRIBUTION_VALUE]));
            entBenefitPeriodHistory.setDistributionDesc1(rs.getString(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_DISTRIBUTION_DESC_1]));
            entBenefitPeriodHistory.setDistributionDesc2(rs.getString(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_DISTRIBUTION_DESC_2]));
            entBenefitPeriodHistory.setDistributionPercent1(rs.getInt(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_DISTRIBUTION_PERCENT_1]));
            entBenefitPeriodHistory.setDistributionPercent2(rs.getInt(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_DISTRIBUTION_PERCENT_2]));
            entBenefitPeriodHistory.setPart1TotalDivider(rs.getInt(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_PART_1_TOTAL_DIVIDER]));
            entBenefitPeriodHistory.setPart2TotalDivider(rs.getInt(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_PART_2_TOTAL_DIVIDER]));
            entBenefitPeriodHistory.setPart1Value(rs.getDouble(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_PART_1_VALUE]));
            entBenefitPeriodHistory.setPart2Value(rs.getDouble(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_PART_2_VALUE]));
            entBenefitPeriodHistory.setDocStatus(rs.getString(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_DOC_STATUS]));
            entBenefitPeriodHistory.setApprove1(rs.getString(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_APPROVE_1]));
            entBenefitPeriodHistory.setApprove2(rs.getString(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_APPROVE_2]));
            entBenefitPeriodHistory.setCreatedBy(rs.getString(PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_CREATED_BY]));
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
            String sql = "SELECT * FROM " + TBL_BENEFIT_PERIOD_HISTORY;
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
                BenefitPeriodHistory entBenefitPeriodHistory = new BenefitPeriodHistory();
                resultToObject(rs, entBenefitPeriodHistory);
                lists.add(entBenefitPeriodHistory);
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

    public static boolean checkOID(long entBenefitPeriodHistoryId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_BENEFIT_PERIOD_HISTORY + " WHERE "
                    + PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_BENEFIT_PERIOD_HISTORY_ID] + " = " + entBenefitPeriodHistoryId;
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
            String sql = "SELECT COUNT(" + PstBenefitPeriodHistory.fieldNames[PstBenefitPeriodHistory.FLD_BENEFIT_PERIOD_HISTORY_ID] + ") FROM " + TBL_BENEFIT_PERIOD_HISTORY;
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
                    BenefitPeriodHistory entBenefitPeriodHistory = (BenefitPeriodHistory) list.get(ls);
                    if (oid == entBenefitPeriodHistory.getOID()) {
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
