/*
 * Description : Benefit Period
 * Date : 2015-02-27
 * Author : Hendra Putu
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.session.payroll.SessBenefitLevel;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PstBenefitPeriod extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_BENEFIT_PERIOD = "pay_benefit_period";
    public static final int FLD_BENEFIT_PERIOD_ID = 0;
    public static final int FLD_BENEFIT_CONFIG_ID = 1;
    public static final int FLD_PERIOD_FROM = 2;
    public static final int FLD_PERIOD_TO = 3;
    public static final int FLD_PERIOD_ID = 4;
    public static final int FLD_TOTAL_REVENUE = 5;
    public static final int FLD_PART_1_VALUE = 6;
    public static final int FLD_PART_1_TOTAL_DIVIDER = 7;
    public static final int FLD_PART_2_VALUE = 8;
    public static final int FLD_PART_2_TOTAL_DIVIDER = 9;
    public static final int FLD_APPROVE_1_EMP_ID = 10;
    public static final int FLD_APPROVE_1_DATE = 11;
    public static final int FLD_APPROVE_2_EMP_ID = 12;
    public static final int FLD_APPROVE_2_DATE = 13;
    public static final int FLD_CREATE_EMP_ID = 14;
    public static final int FLD_CREATE_EMP_DATE = 15;
    public static final int FLD_DOC_STATUS = 16;
    public static String[] fieldNames = {
        "BENEFIT_PERIOD_ID",
        "BENEFIT_CONFIG_ID",
        "PERIOD_FROM",
        "PERIOD_TO",
        "PERIOD_ID",
        "TOTAL_REVENUE",
        "PART_1_VALUE",
        "PART_1_TOTAL_DIVIDER",
        "PART_2_VALUE",
        "PART_2_TOTAL_DIVIDER",
        "APPROVE_1_EMP_ID",
        "APPROVE_1_DATE",
        "APPROVE_2_EMP_ID",
        "APPROVE_2_DATE",
        "CREATE_EMP_ID",
        "CREATE_EMP_DATE",
        "DOC_STATUS"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT
    };
/* yoo you yoi ap draft */
    public static final int[] docStatusVal = {0,1,2,3};
    public static final int statusDraft = 0;
    public static final int statusApproved = 1;
    public static final int statusFinal = 2;
    public static final int statusClosed = 3;
    public static final String[] docStatusKey = {"Draft","To Be Approved", "Final", "Closed"};

    public PstBenefitPeriod() {
    }

    public PstBenefitPeriod(int i) throws DBException {
        super(new PstBenefitPeriod());
    }

    public PstBenefitPeriod(String sOid) throws DBException {
        super(new PstBenefitPeriod(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstBenefitPeriod(long lOid) throws DBException {
        super(new PstBenefitPeriod(0));
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
        return TBL_BENEFIT_PERIOD;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstBenefitPeriod().getClass().getName();
    }

    public static BenefitPeriod fetchExc(long oid) throws DBException {
        try {
            BenefitPeriod entBenefitPeriod = new BenefitPeriod();
            PstBenefitPeriod pstBenefitPeriod = new PstBenefitPeriod(oid);
            entBenefitPeriod.setOID(oid);
            entBenefitPeriod.setBenefitConfigId(pstBenefitPeriod.getLong(FLD_BENEFIT_CONFIG_ID));
            entBenefitPeriod.setPeriodFrom(pstBenefitPeriod.getDate(FLD_PERIOD_FROM));
            entBenefitPeriod.setPeriodTo(pstBenefitPeriod.getDate(FLD_PERIOD_TO));
            entBenefitPeriod.setPeriodId(pstBenefitPeriod.getLong(FLD_PERIOD_ID));
            entBenefitPeriod.setTotalRevenue(pstBenefitPeriod.getdouble(FLD_TOTAL_REVENUE));
            entBenefitPeriod.setPart1Value(pstBenefitPeriod.getdouble(FLD_PART_1_VALUE));
            entBenefitPeriod.setPart1TotalDivider(pstBenefitPeriod.getInt(FLD_PART_1_TOTAL_DIVIDER));
            entBenefitPeriod.setPart2Value(pstBenefitPeriod.getdouble(FLD_PART_2_VALUE));
            entBenefitPeriod.setPart2TotalDivider(pstBenefitPeriod.getInt(FLD_PART_2_TOTAL_DIVIDER));
            entBenefitPeriod.setApprove1EmpId(pstBenefitPeriod.getLong(FLD_APPROVE_1_EMP_ID));
            entBenefitPeriod.setApprove1Date(pstBenefitPeriod.getDate(FLD_APPROVE_1_DATE));
            entBenefitPeriod.setApprove2EmpId(pstBenefitPeriod.getLong(FLD_APPROVE_2_EMP_ID));
            entBenefitPeriod.setApprove2Date(pstBenefitPeriod.getDate(FLD_APPROVE_2_DATE));
            entBenefitPeriod.setCreateEmpId(pstBenefitPeriod.getLong(FLD_CREATE_EMP_ID));
            entBenefitPeriod.setCreateEmpDate(pstBenefitPeriod.getDate(FLD_CREATE_EMP_DATE));
            entBenefitPeriod.setDocStatus(pstBenefitPeriod.getInt(FLD_DOC_STATUS));
            return entBenefitPeriod;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriod(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        BenefitPeriod entBenefitPeriod = fetchExc(entity.getOID());
        entity = (Entity) entBenefitPeriod;
        return entBenefitPeriod.getOID();
    }

    public static synchronized long updateExc(BenefitPeriod entBenefitPeriod) throws DBException {
        try {
            if (entBenefitPeriod.getOID() != 0) {
                PstBenefitPeriod pstBenefitPeriod = new PstBenefitPeriod(entBenefitPeriod.getOID());
                pstBenefitPeriod.setLong(FLD_BENEFIT_CONFIG_ID, entBenefitPeriod.getBenefitConfigId());
                pstBenefitPeriod.setDate(FLD_PERIOD_FROM, entBenefitPeriod.getPeriodFrom());
                pstBenefitPeriod.setDate(FLD_PERIOD_TO, entBenefitPeriod.getPeriodTo());
                pstBenefitPeriod.setLong(FLD_PERIOD_ID, entBenefitPeriod.getPeriodId());
                pstBenefitPeriod.setDouble(FLD_TOTAL_REVENUE, entBenefitPeriod.getTotalRevenue());
                pstBenefitPeriod.setDouble(FLD_PART_1_VALUE, entBenefitPeriod.getPart1Value());
                pstBenefitPeriod.setInt(FLD_PART_1_TOTAL_DIVIDER, entBenefitPeriod.getPart1TotalDivider());
                pstBenefitPeriod.setDouble(FLD_PART_2_VALUE, entBenefitPeriod.getPart2Value());
                pstBenefitPeriod.setInt(FLD_PART_2_TOTAL_DIVIDER, entBenefitPeriod.getPart2TotalDivider());
                pstBenefitPeriod.setLong(FLD_APPROVE_1_EMP_ID, entBenefitPeriod.getApprove1EmpId());
                pstBenefitPeriod.setDate(FLD_APPROVE_1_DATE, entBenefitPeriod.getApprove1Date());
                pstBenefitPeriod.setLong(FLD_APPROVE_2_EMP_ID, entBenefitPeriod.getApprove2EmpId());
                pstBenefitPeriod.setDate(FLD_APPROVE_2_DATE, entBenefitPeriod.getApprove2Date());
                pstBenefitPeriod.setLong(FLD_CREATE_EMP_ID, entBenefitPeriod.getCreateEmpId());
                pstBenefitPeriod.setDate(FLD_CREATE_EMP_DATE, entBenefitPeriod.getCreateEmpDate());
                pstBenefitPeriod.setInt(FLD_DOC_STATUS, entBenefitPeriod.getDocStatus());
                pstBenefitPeriod.update();
                updateDeduction(entBenefitPeriod.getOID(), entBenefitPeriod.getBenefitConfigId(), entBenefitPeriod.getTotalRevenue());
                if (entBenefitPeriod.getDocStatus() == statusClosed){
                    /* insert to history */
                    PstBenefitPeriodHistory.insertHistory(entBenefitPeriod);
                }
                return entBenefitPeriod.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriod(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((BenefitPeriod) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstBenefitPeriod pstBenefitPeriod = new PstBenefitPeriod(oid);
            pstBenefitPeriod.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriod(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(BenefitPeriod entBenefitPeriod) throws DBException {
        try {
            PstBenefitPeriod pstBenefitPeriod = new PstBenefitPeriod(0);
            pstBenefitPeriod.setLong(FLD_BENEFIT_CONFIG_ID, entBenefitPeriod.getBenefitConfigId());
            pstBenefitPeriod.setDate(FLD_PERIOD_FROM, entBenefitPeriod.getPeriodFrom());
            pstBenefitPeriod.setDate(FLD_PERIOD_TO, entBenefitPeriod.getPeriodTo());
            pstBenefitPeriod.setLong(FLD_PERIOD_ID, entBenefitPeriod.getPeriodId());
            pstBenefitPeriod.setDouble(FLD_TOTAL_REVENUE, entBenefitPeriod.getTotalRevenue());
            pstBenefitPeriod.setDouble(FLD_PART_1_VALUE, entBenefitPeriod.getPart1Value());
            pstBenefitPeriod.setInt(FLD_PART_1_TOTAL_DIVIDER, entBenefitPeriod.getPart1TotalDivider());
            pstBenefitPeriod.setDouble(FLD_PART_2_VALUE, entBenefitPeriod.getPart2Value());
            pstBenefitPeriod.setInt(FLD_PART_2_TOTAL_DIVIDER, entBenefitPeriod.getPart2TotalDivider());
            pstBenefitPeriod.setLong(FLD_APPROVE_1_EMP_ID, entBenefitPeriod.getApprove1EmpId());
            pstBenefitPeriod.setDate(FLD_APPROVE_1_DATE, entBenefitPeriod.getApprove1Date());
            pstBenefitPeriod.setLong(FLD_APPROVE_2_EMP_ID, entBenefitPeriod.getApprove2EmpId());
            pstBenefitPeriod.setDate(FLD_APPROVE_2_DATE, entBenefitPeriod.getApprove2Date());
            pstBenefitPeriod.setLong(FLD_CREATE_EMP_ID, entBenefitPeriod.getCreateEmpId());
            pstBenefitPeriod.setDate(FLD_CREATE_EMP_DATE, entBenefitPeriod.getCreateEmpDate());
            pstBenefitPeriod.setInt(FLD_DOC_STATUS, entBenefitPeriod.getDocStatus());
            pstBenefitPeriod.insert();
            entBenefitPeriod.setOID(pstBenefitPeriod.getLong(FLD_BENEFIT_PERIOD_ID));
            insertBenefitPeriodDeduction(pstBenefitPeriod.getLong(FLD_BENEFIT_PERIOD_ID), entBenefitPeriod.getBenefitConfigId(), entBenefitPeriod.getTotalRevenue());
            updateDistribution(pstBenefitPeriod.getLong(FLD_BENEFIT_PERIOD_ID));
            // insert emp
            insertBenefitEmp(pstBenefitPeriod.getLong(FLD_BENEFIT_PERIOD_ID), entBenefitPeriod.getBenefitConfigId(), entBenefitPeriod.getPeriodId());
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriod(0), DBException.UNKNOWN);
        }
        return entBenefitPeriod.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((BenefitPeriod) entity);
    }

    public static void resultToObject(ResultSet rs, BenefitPeriod entBenefitPeriod) {
        try {
            entBenefitPeriod.setOID(rs.getLong(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_BENEFIT_PERIOD_ID]));
            entBenefitPeriod.setBenefitConfigId(rs.getLong(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_BENEFIT_CONFIG_ID]));
            entBenefitPeriod.setPeriodFrom(rs.getDate(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_PERIOD_FROM]));
            entBenefitPeriod.setPeriodTo(rs.getDate(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_PERIOD_TO]));
            entBenefitPeriod.setPeriodId(rs.getLong(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_PERIOD_ID]));
            entBenefitPeriod.setTotalRevenue(rs.getDouble(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_TOTAL_REVENUE]));
            entBenefitPeriod.setPart1Value(rs.getDouble(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_PART_1_VALUE]));
            entBenefitPeriod.setPart1TotalDivider(rs.getInt(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_PART_1_TOTAL_DIVIDER]));
            entBenefitPeriod.setPart2Value(rs.getDouble(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_PART_2_VALUE]));
            entBenefitPeriod.setPart2TotalDivider(rs.getInt(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_PART_2_TOTAL_DIVIDER]));
            entBenefitPeriod.setApprove1EmpId(rs.getLong(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_APPROVE_1_EMP_ID]));
            entBenefitPeriod.setApprove1Date(rs.getDate(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_APPROVE_1_DATE]));
            entBenefitPeriod.setApprove2EmpId(rs.getLong(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_APPROVE_2_EMP_ID]));
            entBenefitPeriod.setApprove2Date(rs.getDate(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_APPROVE_2_DATE]));
            entBenefitPeriod.setCreateEmpId(rs.getLong(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_CREATE_EMP_ID]));
            entBenefitPeriod.setCreateEmpDate(rs.getDate(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_CREATE_EMP_DATE]));
            entBenefitPeriod.setDocStatus(rs.getInt(PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_DOC_STATUS]));
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
            String sql = "SELECT * FROM " + TBL_BENEFIT_PERIOD;
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
                BenefitPeriod benefitPeriod = new BenefitPeriod();
                resultToObject(rs, benefitPeriod);
                lists.add(benefitPeriod);
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
    
    public static boolean checkOID(long entBenefitPeriodId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_BENEFIT_PERIOD + " WHERE "
                    + PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_BENEFIT_PERIOD_ID] + " = " + entBenefitPeriodId;

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
            String sql = "SELECT COUNT(" + PstBenefitPeriod.fieldNames[PstBenefitPeriod.FLD_BENEFIT_PERIOD_ID] + ") FROM " + TBL_BENEFIT_PERIOD;
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
                    BenefitPeriod entBenefitPeriod = (BenefitPeriod) list.get(ls);
                    if (oid == entBenefitPeriod.getOID()) {
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
    
    public static void insertBenefitPeriodDeduction(long benefitPeriodId, long benefitConfigId, double totalRevenue) {
        long oid = 0;
        double[] arrReference;
        int inc = 0;
        String whereDeduction = PstBenefitConfigDeduction.fieldNames[PstBenefitConfigDeduction.FLD_BENEFIT_CONFIG_ID]+" = "+benefitConfigId;
        Vector listBCDeduction = new Vector();
        /* ambil list benefit config deduction berdasarkan BENEFIT_CONFIG_ID */
        listBCDeduction = PstBenefitConfigDeduction.list(0, 0, whereDeduction, "");
        if (listBCDeduction != null && listBCDeduction.size() > 0){
            /* arrReference adalah array yg menyimpan hasil dari percent * nilai */
            arrReference = new double[listBCDeduction.size()+1];
            for (int i=0; i<listBCDeduction.size(); i++){
                BenefitConfigDeduction bcDeduction = (BenefitConfigDeduction)listBCDeduction.get(i);
                double reference = 0;
                double deductionResult = 0;
                double percentResult = 0;
                if (bcDeduction.getDeductionReference()==0){
                    reference = totalRevenue;
                    arrReference[inc] = totalRevenue;
                } else {
                    if (bcDeduction.getDeductionReference() !=0){
                        BenefitConfigDeduction ded = new BenefitConfigDeduction();
                        try{
                            ded = PstBenefitConfigDeduction.fetchExc(bcDeduction.getDeductionReference());
                            reference = arrReference[ded.getDeductionIndex()];
                        } catch(Exception e){
                            System.out.println(e+" BenefitConfigDeduction | getDeductionReference()");
                        }
                        
                    } else {
                        reference = arrReference[0];
                    }
                }
                inc++;
                percentResult   = (bcDeduction.getDeductionPercent() * reference)/100;
                deductionResult = reference - percentResult;
                arrReference[inc] = deductionResult;
                try {
                    BenefitPeriodDeduction bperiodDeduction = new BenefitPeriodDeduction();
                    bperiodDeduction.setDeductionId(bcDeduction.getOID());
                    bperiodDeduction.setDeductionResult(deductionResult);
                    bperiodDeduction.setBenefitPeriodId(benefitPeriodId);
                    oid = PstBenefitPeriodDeduction.insertExc(bperiodDeduction);
                } catch (Exception e){
                    System.out.println(e+" PstBenefitPeriod : insertBenefitPeriodDeduction ");
                }
            } /* End For*/
        
        } /*End If*/
    }
    
    public static int getTotalEmployee(long benefitConfigId, long periodId){
        // dapatkan data Benefit Config
        BenefitConfig bConfig = new BenefitConfig();
        try {
            bConfig = PstBenefitConfig.fetchExc(benefitConfigId);
        } catch (DBException ex) {
            Logger.getLogger(PstBenefitPeriod.class.getName()).log(Level.SEVERE, null, ex);
        }
        // dapatkan start date and end date pay period
        PayPeriod payPeriod = new PayPeriod();
        try {
            payPeriod = PstPayPeriod.fetchExc(periodId);
        } catch (DBException ex) {
            Logger.getLogger(PstPayPeriod.class.getName()).log(Level.SEVERE, null, ex);
        }
        String exceptionNoByPayroll = "";
        for (String retval : bConfig.getExceptionNoByPayroll().split(",")) {
            exceptionNoByPayroll += "'"+retval+"',";
        }
        exceptionNoByPayroll += "'0'";
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT COUNT("+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+") AS total_employee ";
            sql += " FROM "+PstEmployee.TBL_HR_EMPLOYEE+" ";
            
            sql += " INNER JOIN "+PstDivision.TBL_HR_DIVISION+" ON "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+PstDivision.TBL_HR_DIVISION+"."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" ";
            sql += " INNER JOIN "+PstEmpCategory.TBL_HR_EMP_CATEGORY+" ON "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+PstEmpCategory.TBL_HR_EMP_CATEGORY+"."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]+" ";
            sql += " INNER JOIN "+PstLevel.TBL_HR_LEVEL+" ON "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+"="+PstLevel.TBL_HR_LEVEL+"."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+" ";
            sql += " INNER JOIN "+PstPosition.TBL_HR_POSITION+" ON "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+PstPosition.TBL_HR_POSITION+"."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" ";
            
            sql += " WHERE ";            
            sql += " "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = 0 ";
            sql += " AND "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+" <= '"+payPeriod.getEndDate()+"' ";
            sql += " AND "+PstEmpCategory.TBL_HR_EMP_CATEGORY+"."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]+" NOT IN("+bConfig.getExceptionNoByCategory()+") ";
            sql += " AND "+PstDivision.TBL_HR_DIVISION+"."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" NOT IN("+bConfig.getExceptionNoByDivision()+") ";
            sql += " AND "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" NOT IN("+exceptionNoByPayroll+") ";           
            sql += " AND "+PstPosition.TBL_HR_POSITION+"."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" NOT IN("+bConfig.getExceptionNoByPosition()+") ";
            sql += " OR "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]+" BETWEEN '"+payPeriod.getStartDate()+"' AND '"+payPeriod.getEndDate()+"' ";

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
    
    public static Vector getTotalPoint(long benefitConfigId, long periodId){
        // dapatkan data Benefit Config
        BenefitConfig bConfig = new BenefitConfig();
        try {
            bConfig = PstBenefitConfig.fetchExc(benefitConfigId);
        } catch (DBException ex) {
            Logger.getLogger(PstBenefitPeriod.class.getName()).log(Level.SEVERE, null, ex);
        }
        // dapatkan start date and end date pay period
        PayPeriod payPeriod = new PayPeriod();
        try {
            payPeriod = PstPayPeriod.fetchExc(periodId);
        } catch (DBException ex) {
            Logger.getLogger(PstPayPeriod.class.getName()).log(Level.SEVERE, null, ex);
        }
        String exceptionNoByPayroll = "";
        for (String retval : bConfig.getExceptionNoByPayroll().split(",")) {
            exceptionNoByPayroll += "'"+retval+"',";
        }
        exceptionNoByPayroll += "'0'";
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT "+PstLevel.TBL_HR_LEVEL+"."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+", "+PstLevel.TBL_HR_LEVEL+".`LEVEL`, ";
            sql += " "+PstLevel.TBL_HR_LEVEL+"."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_POINT]+", ";
            sql += " COUNT("+PstLevel.TBL_HR_LEVEL+"."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_POINT]+") AS COUNT_POINT, ";
            sql += " SUM("+PstLevel.TBL_HR_LEVEL+"."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_POINT]+") AS SUM_POINT ";
            sql += " FROM "+PstEmployee.TBL_HR_EMPLOYEE+" ";
            
            sql += " INNER JOIN "+PstDivision.TBL_HR_DIVISION+" ON "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+PstDivision.TBL_HR_DIVISION+"."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" ";
            sql += " INNER JOIN "+PstEmpCategory.TBL_HR_EMP_CATEGORY+" ON "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+PstEmpCategory.TBL_HR_EMP_CATEGORY+"."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]+" ";
            sql += " INNER JOIN "+PstLevel.TBL_HR_LEVEL+" ON "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+"="+PstLevel.TBL_HR_LEVEL+"."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+" ";
            sql += " INNER JOIN "+PstPosition.TBL_HR_POSITION+" ON "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+PstPosition.TBL_HR_POSITION+"."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" ";
            
            sql += " WHERE ";
            sql += " "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = 0 ";
            sql += " AND "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+" <= '"+payPeriod.getEndDate()+"' ";
            sql += " AND "+PstEmpCategory.TBL_HR_EMP_CATEGORY+"."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]+" NOT IN("+bConfig.getExceptionNoByCategory()+") ";
            sql += " AND "+PstDivision.TBL_HR_DIVISION+"."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" NOT IN("+bConfig.getExceptionNoByDivision()+") ";
            sql += " AND "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" NOT IN("+exceptionNoByPayroll+") ";           
            sql += " AND "+PstPosition.TBL_HR_POSITION+"."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" NOT IN("+bConfig.getExceptionNoByPosition()+") ";
            sql += " OR "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]+" BETWEEN '"+payPeriod.getStartDate()+"' AND '"+payPeriod.getEndDate()+"' ";
            sql += " GROUP BY "+PstLevel.TBL_HR_LEVEL+"."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+" ";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                SessBenefitLevel sessLevel = new SessBenefitLevel();
                sessLevel.setLevelId(rs.getLong(PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]));
                sessLevel.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                sessLevel.setLevelPoint(rs.getInt(PstLevel.fieldNames[PstLevel.FLD_LEVEL_POINT]));
                sessLevel.setCountPoint(rs.getInt("COUNT_POINT"));
                sessLevel.setSumPoint(rs.getInt("SUM_POINT"));
                lists.add(sessLevel);
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
    
    public static int getAllTotalPoint(long benefitConfigId, long periodId){
        int totalPoint = 0;
        Vector listPoint = getTotalPoint(benefitConfigId, periodId);
        if(listPoint != null && listPoint.size()>0){
            for(int i=0; i<listPoint.size(); i++){
                SessBenefitLevel pLevel = (SessBenefitLevel)listPoint.get(i);
                totalPoint = totalPoint + pLevel.getSumPoint();
            }
        }
        return totalPoint;
    }
    
    public static void updateDistribution(long oid){
        double distributedValue = 0;
        double forEmployee = 0;
        double forPoint = 0;
        int totalEmployee = 0;
        int totalPoint = 0;
        try {
            BenefitPeriod benefitPeriod = fetchExc(oid);
            // get oid by index terbesar benefit config deduction
            // get deduction result on benefit period deduction
            BenefitConfigDeduction bcd = PstBenefitConfigDeduction.getOneRecordDesc(benefitPeriod.getBenefitConfigId());
            BenefitPeriodDeduction bpd = PstBenefitPeriodDeduction.getOneResult(bcd.getOID(), oid);
            BenefitConfig bconfig = PstBenefitConfig.fetchExc(benefitPeriod.getBenefitConfigId());
            totalEmployee = getTotalEmployee(benefitPeriod.getBenefitConfigId(), benefitPeriod.getPeriodId());
            totalPoint = getAllTotalPoint(benefitPeriod.getBenefitConfigId(), benefitPeriod.getPeriodId());
            distributedValue = bpd.getDeductionResult();
            forEmployee = ((bconfig.getDistributionPercent1() * distributedValue)/100)/totalEmployee;
            forPoint = ((bconfig.getDistributionPercent2() * distributedValue)/100)/totalPoint;
            BenefitPeriod entBenefitPeriod = new BenefitPeriod();
            entBenefitPeriod.setOID(oid);
            entBenefitPeriod.setBenefitConfigId(benefitPeriod.getBenefitConfigId());
            entBenefitPeriod.setPeriodFrom(benefitPeriod.getPeriodFrom());
            entBenefitPeriod.setPeriodTo(benefitPeriod.getPeriodTo());
            entBenefitPeriod.setPeriodId(benefitPeriod.getPeriodId());
            entBenefitPeriod.setTotalRevenue(benefitPeriod.getTotalRevenue());
            entBenefitPeriod.setPart1Value(forEmployee);
            entBenefitPeriod.setPart1TotalDivider(totalEmployee);
            entBenefitPeriod.setPart2Value(forPoint);
            entBenefitPeriod.setPart2TotalDivider(totalPoint);
            entBenefitPeriod.setApprove1EmpId(benefitPeriod.getApprove1EmpId());
            entBenefitPeriod.setApprove1Date(benefitPeriod.getApprove1Date());
            entBenefitPeriod.setApprove2EmpId(benefitPeriod.getApprove2EmpId());
            entBenefitPeriod.setApprove2Date(benefitPeriod.getApprove2Date());
            entBenefitPeriod.setCreateEmpId(benefitPeriod.getCreateEmpId());
            entBenefitPeriod.setCreateEmpDate(benefitPeriod.getCreateEmpDate());
            entBenefitPeriod.setDocStatus(benefitPeriod.getDocStatus());
            updateExc(entBenefitPeriod);
        } catch(Exception e){
            System.out.println(e+" updateDistribution");
        }
    }
    
    public static void updateDeduction(long benefitPeriodId, long benefitConfigId, double totalRevenue){
        String whereDeduction = PstBenefitConfigDeduction.fieldNames[PstBenefitConfigDeduction.FLD_BENEFIT_CONFIG_ID]+" = " + benefitConfigId;
        String orderDeduction = PstBenefitConfigDeduction.fieldNames[PstBenefitConfigDeduction.FLD_DEDUCTION_INDEX]+" ASC ";
        String whereBPDeduction = PstBenefitPeriodDeduction.fieldNames[PstBenefitPeriodDeduction.FLD_BENEFIT_PERIOD_ID]+" = "+ benefitPeriodId;
        double deductionResult = 0;
        Vector listDeduction = PstBenefitConfigDeduction.list(0, 0, whereDeduction, orderDeduction);
        Vector listBPDeduction = PstBenefitPeriodDeduction.list(0, 0, whereBPDeduction, "");
        double[] arrDeduction = new double[listDeduction.size() + 1];
        arrDeduction[0] = totalRevenue;
        try {
            for (int i = 0; i < listDeduction.size(); i++) {
                BenefitConfigDeduction deduction = (BenefitConfigDeduction) listDeduction.get(i);
                if (deduction.getDeductionReference() == 0) {
                    arrDeduction[deduction.getDeductionIndex()] = totalRevenue - ((deduction.getDeductionPercent() * totalRevenue) / 100);
                    deductionResult = (deduction.getDeductionPercent() * totalRevenue) / 100;
                } else {
                    BenefitConfigDeduction ded = PstBenefitConfigDeduction.fetchExc(deduction.getDeductionReference());
                    arrDeduction[deduction.getDeductionIndex()] = arrDeduction[deduction.getDeductionIndex() - 1] - ((deduction.getDeductionPercent() * arrDeduction[ded.getDeductionIndex()]) / 100);
                    deductionResult = (deduction.getDeductionPercent() * arrDeduction[ded.getDeductionIndex()]) / 100;
                }
            }
            for (int j=0; j < listBPDeduction.size(); j++){
                BenefitPeriodDeduction deduction = (BenefitPeriodDeduction)listBPDeduction.get(j);
                BenefitPeriodDeduction updateDeduction = new BenefitPeriodDeduction();
                updateDeduction.setOID(deduction.getOID());
                updateDeduction.setBenefitPeriodId(benefitPeriodId);
                updateDeduction.setDeductionId(deduction.getDeductionId());
                updateDeduction.setDeductionResult(arrDeduction[j+1]);
                PstBenefitPeriodDeduction.updateExc(updateDeduction);
            }
        } catch(Exception e){
            System.out.println(e+" updateDeduction");
        }
    }
    
    public static void insertBenefitEmp(long benefitPeriodId, long benefitConfigId, long periodId){
        double distribution1 = 0;
        double distribution2 = 0;
        BenefitPeriod bPeriod = new BenefitPeriod();
        try {
            bPeriod = PstBenefitPeriod.fetchExc(benefitPeriodId);
            distribution1 = bPeriod.getPart1Value();
            distribution2 = bPeriod.getPart2Value();
        } catch (DBException ex) {
            Logger.getLogger(PstBenefitPeriod.class.getName()).log(Level.SEVERE, null, ex);
        }
        long oid = 0;
        // dapatkan data Benefit Config
        BenefitConfig bConfig = new BenefitConfig();
        try {
            bConfig = PstBenefitConfig.fetchExc(benefitConfigId);
        } catch (DBException ex) {
            Logger.getLogger(PstBenefitConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        // dapatkan start date and end date pay period
        PayPeriod payPeriod = new PayPeriod();
        try {
            payPeriod = PstPayPeriod.fetchExc(periodId);
        } catch (DBException ex) {
            Logger.getLogger(PstPayPeriod.class.getName()).log(Level.SEVERE, null, ex);
        }
        String exceptionNoByPayroll = "";
        for (String retval : bConfig.getExceptionNoByPayroll().split(",")) {
            exceptionNoByPayroll += "'"+retval+"',";
        }
        exceptionNoByPayroll += "'0'";
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+", "+PstLevel.TBL_HR_LEVEL+".`LEVEL`, "+PstLevel.TBL_HR_LEVEL+"."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_POINT]+" ";
            sql += " FROM "+PstEmployee.TBL_HR_EMPLOYEE+" ";
            sql += " INNER JOIN "+PstDivision.TBL_HR_DIVISION+" ON "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+PstDivision.TBL_HR_DIVISION+"."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" ";
            sql += " INNER JOIN "+PstEmpCategory.TBL_HR_EMP_CATEGORY+" ON "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+PstEmpCategory.TBL_HR_EMP_CATEGORY+"."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]+" ";
            sql += " INNER JOIN "+PstLevel.TBL_HR_LEVEL+" ON "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+"="+PstLevel.TBL_HR_LEVEL+"."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+" ";
            sql += " INNER JOIN "+PstPosition.TBL_HR_POSITION+" ON "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+PstPosition.TBL_HR_POSITION+"."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" ";
            sql += " WHERE ";
            sql += " "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = 0 ";
            sql += " AND "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+" <= '"+payPeriod.getEndDate()+"' ";
            sql += " AND "+PstEmpCategory.TBL_HR_EMP_CATEGORY+"."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]+" NOT IN("+bConfig.getExceptionNoByCategory()+") ";
            sql += " AND "+PstDivision.TBL_HR_DIVISION+"."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" NOT IN("+bConfig.getExceptionNoByDivision()+") ";
            sql += " AND "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" NOT IN("+exceptionNoByPayroll+") ";           
            sql += " AND "+PstPosition.TBL_HR_POSITION+"."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" NOT IN("+bConfig.getExceptionNoByPosition()+") ";
            sql += " OR "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]+" BETWEEN '"+payPeriod.getStartDate()+"' AND '"+payPeriod.getEndDate()+"' ";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                BenefitPeriodEmp benefitEmp = new BenefitPeriodEmp();
                benefitEmp.setEmployeeId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                benefitEmp.setAmountPart1(distribution1);
                benefitEmp.setAmountPart2(rs.getInt(PstLevel.fieldNames[PstLevel.FLD_LEVEL_POINT])*distribution2);
                benefitEmp.setLevelCode(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                benefitEmp.setLevelPoint(rs.getInt(PstLevel.fieldNames[PstLevel.FLD_LEVEL_POINT]));
                benefitEmp.setBenefitPeriodId(benefitPeriodId);
                oid = PstBenefitPeriodEmp.insertExc(benefitEmp);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
    }


}
