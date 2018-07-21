package com.dimata.harisma.entity.arap;

import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.payroll.PayComponent;
import com.dimata.harisma.entity.payroll.PayPeriod;
import com.dimata.harisma.entity.payroll.PaySlipComp;
import com.dimata.harisma.entity.payroll.PstPayComponent;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.harisma.entity.payroll.PstPaySlipComp;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.util.Formater;

import java.util.Vector;
import java.sql.ResultSet;
import java.util.Date;

public class PstArApMain extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc {

    public static final String TBL_ARAP_MAIN = "hr_arap_main";
    public static final int FLD_ARAP_MAIN_ID = 0;
    public static final int FLD_VOUCHER_NO = 1;
    public static final int FLD_VOUCHER_DATE = 2;
    public static final int FLD_CONTACT_ID = 3;
    public static final int FLD_NUMBER_OF_PAYMENT = 4;
    public static final int FLD_ID_PERKIRAAN_LAWAN = 5;
    public static final int FLD_ID_PERKIRAAN = 6;
    public static final int FLD_ID_CURRENCY = 7;
    public static final int FLD_COUNTER = 8;
    public static final int FLD_RATE = 9;
    public static final int FLD_AMOUNT = 10;
    public static final int FLD_NOTA_NO = 11;
    public static final int FLD_NOTA_DATE = 12;
    public static final int FLD_DESCRIPTION = 13;
    public static final int FLD_ARAP_MAIN_STATUS = 14;
    public static final int FLD_ARAP_TYPE = 15;
    public static final int FLD_ARAP_DOC_STATUS = 16;
    public static final int FLD_JOURNAL_ID = 17;
    public static final int FLD_COMPONENT_DEDUCTION_ID = 18;
    public static final int FLD_EMPLOYEE_ID = 19;
    public static final int FLD_ENTRY_DATE = 20;
    public static final int FLD_PERIOD_EVERY = 21;
    public static final int FLD_PERIOD_EVERY_DMY = 22;
    public static final int FLD_START_OF_PAYMENT_DATE = 23;
    public static final int FLD_PAYMENT_AMOUNT_PLAN = 24;
    public static final int FLD_PERIOD_TYPE = 25;
    public static final int FLD_PERIOD_ID = 26;

    public static final String[] fieldNames = {
        "ARAP_MAIN_ID",
        "VOUCHER_NO",
        "VOUCHER_DATE",
        "CONTACT_ID",
        "NUMBER_OF_PAYMENT",
        "ID_PERKIRAAN_LAWAN",
        "ID_PERKIRAAN",
        "ID_CURRENCY",
        "COUNTER",
        "RATE",
        "AMOUNT",
        "NOTA_NO",
        "NOTA_DATE",
        "DESCRIPTION",
        "ARAP_MAIN_STATUS",
        "ARAP_TYPE",
        "ARAP_DOC_STATUS",
        "JURNAL_ID",
        "COMPONENT_DEDUCTION_ID",
        "EMPLOYEE_ID",
        "ENTRY_DATE",
        "PERIOD_EVERY",
        "PERIOD_EVERY_DMY",
        "START_OF_PAYMENT_DATE",
        "PAYMENT_AMOUNT_PLAN",
        "PERIOD_TYPE",
        "PERIOD_ID"
    };

    public static int[] fieldTypes = {
        TYPE_PK + TYPE_ID + TYPE_LONG,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_LONG
    };

    // document status untuk ArAp Main
    public static final int STATUS_OPEN = 0;
    public static final int STATUS_CLOSED = 1;

    // type ar/ap
    public static final int TYPE_AR = 0;
    public static final int TYPE_AP = 1;
    
    public static final String[][] stTypeArAp = {
        {
            "Piutang",
            "Hutang"
        }   ,
        {
            "Receivable",
            "Payable"
        }
    };

    public PstArApMain() {
    }

    public PstArApMain(int i) throws DBException {
        super(new PstArApMain());
    }

    public PstArApMain(String sOid) throws DBException {
        super(new PstArApMain(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public PstArApMain(long lOid) throws DBException {
        super(new PstArApMain(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }

        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_ARAP_MAIN;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstArApMain().getClass().getName();
    }

    public long fetchExc(Entity ent) throws DBException {
        ArApMain arap = fetchExc(ent.getOID());
        ent = (Entity) arap;
        return arap.getOID();
    }

    public long insertExc(Entity ent) throws DBException {
        return insertExc((ArApMain) ent);
    }

    public long updateExc(Entity ent) throws DBException {
        return updateExc((ArApMain) ent);
    }

    public long deleteExc(Entity ent) throws DBException {
        if (ent == null) {
            throw  new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static ArApMain fetchExc(long Oid) throws DBException {
        try {
            ArApMain arap = new ArApMain();
            PstArApMain pstArApMain = new PstArApMain(Oid);
            arap.setOID(Oid);

            arap.setVoucherNo(pstArApMain.getString(FLD_VOUCHER_NO));
            arap.setVoucherDate(pstArApMain.getDate(FLD_VOUCHER_DATE));
            arap.setNumberOfPayment(pstArApMain.getInt(FLD_NUMBER_OF_PAYMENT));
            arap.setContactId(pstArApMain.getlong(FLD_CONTACT_ID));
            arap.setIdPerkiraanLawan(pstArApMain.getlong(FLD_ID_PERKIRAAN_LAWAN));
            arap.setIdPerkiraan(pstArApMain.getlong(FLD_ID_PERKIRAAN));
            arap.setIdCurrency(pstArApMain.getlong(FLD_ID_CURRENCY));
            arap.setCounter(pstArApMain.getInt(FLD_COUNTER));
            arap.setRate(pstArApMain.getdouble(FLD_RATE));
            arap.setAmount(pstArApMain.getdouble(FLD_AMOUNT));
            arap.setNotaNo(pstArApMain.getString(FLD_NOTA_NO));
            arap.setNotaDate(pstArApMain.getDate(FLD_NOTA_DATE));
            arap.setDescription(pstArApMain.getString(FLD_DESCRIPTION));
            arap.setArApMainStatus(pstArApMain.getInt(FLD_ARAP_MAIN_STATUS));
            arap.setArApType(pstArApMain.getInt(FLD_ARAP_TYPE));
            arap.setArApDocStatus(pstArApMain.getInt(FLD_ARAP_DOC_STATUS));
            arap.setJournalId(pstArApMain.getlong(FLD_JOURNAL_ID));//!=null ? pstArApMain.getLong(FLD_JOURNAL_ID) : 0);
            arap.setComponentDeductionId(pstArApMain.getlong(FLD_COMPONENT_DEDUCTION_ID));
            arap.setEmployeeId(pstArApMain.getlong(FLD_EMPLOYEE_ID));
            arap.setEntryDate(pstArApMain.getDate(FLD_ENTRY_DATE));
            arap.setPeriodeEvery(pstArApMain.getInt(FLD_PERIOD_EVERY));
            arap.setPeriodeEveryDMY(pstArApMain.getInt(FLD_PERIOD_EVERY_DMY));
            arap.setStartofpaymentdate(pstArApMain.getDate(FLD_START_OF_PAYMENT_DATE));
            arap.setPayment_amount_plan(pstArApMain.getdouble(FLD_PAYMENT_AMOUNT_PLAN));
            arap.setPeriodType(pstArApMain.getInt(FLD_PERIOD_TYPE));
            arap.setPeriodId(pstArApMain.getlong(FLD_PERIOD_ID));
            return arap;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstArApMain(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(ArApMain arap) throws DBException {
        try {
            PstArApMain pstArApMain = new PstArApMain(0);

            pstArApMain.setString(FLD_VOUCHER_NO, arap.getVoucherNo());
            pstArApMain.setInt(FLD_NUMBER_OF_PAYMENT, arap.getNumberOfPayment());
            pstArApMain.setDate(FLD_VOUCHER_DATE, arap.getVoucherDate());
            pstArApMain.setLong(FLD_CONTACT_ID, arap.getContactId());
            pstArApMain.setLong(FLD_ID_PERKIRAAN_LAWAN, arap.getIdPerkiraanLawan());
            pstArApMain.setLong(FLD_ID_PERKIRAAN, arap.getIdPerkiraan());
            pstArApMain.setLong(FLD_ID_CURRENCY, arap.getIdCurrency());
            pstArApMain.setInt(FLD_COUNTER, arap.getCounter());
            pstArApMain.setDouble(FLD_RATE, arap.getRate());
            pstArApMain.setDouble(FLD_AMOUNT, arap.getAmount());
            pstArApMain.setString(FLD_NOTA_NO, arap.getNotaNo());
            pstArApMain.setDate(FLD_NOTA_DATE, arap.getNotaDate());
            pstArApMain.setString(FLD_DESCRIPTION, arap.getDescription());
            pstArApMain.setInt(FLD_ARAP_MAIN_STATUS, arap.getArApMainStatus());
            pstArApMain.setInt(FLD_ARAP_TYPE, arap.getArApType());
            pstArApMain.setInt(FLD_ARAP_DOC_STATUS, arap.getArApDocStatus());
            pstArApMain.setLong(FLD_JOURNAL_ID, arap.getJournalId());
            pstArApMain.setLong(FLD_COMPONENT_DEDUCTION_ID, arap.getComponentDeductionId());
            pstArApMain.setLong(FLD_EMPLOYEE_ID, arap.getEmployeeId());
            pstArApMain.setDate(FLD_ENTRY_DATE, arap.getEntryDate());
            pstArApMain.setInt(FLD_PERIOD_EVERY, arap.getPeriodeEvery());
            pstArApMain.setInt(FLD_PERIOD_EVERY_DMY, arap.getPeriodeEveryDMY());
            pstArApMain.setDate(FLD_START_OF_PAYMENT_DATE, arap.getStartofpaymentdate());
            pstArApMain.setDouble(FLD_PAYMENT_AMOUNT_PLAN, arap.getPayment_amount_plan());
            pstArApMain.setInt(FLD_PERIOD_TYPE, arap.getPeriodType());
            pstArApMain.setLong(FLD_PERIOD_ID, arap.getPeriodId());
            pstArApMain.insert();
            arap.setOID(pstArApMain.getlong(FLD_ARAP_MAIN_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstArApMain(0), DBException.UNKNOWN);
        }
        return arap.getOID();
    }

    public static long updateExc(ArApMain arap) throws DBException {
        try {
            if (arap != null && arap.getOID() != 0) {
                PstArApMain pstArApMain = new PstArApMain(arap.getOID());

                pstArApMain.setString(FLD_VOUCHER_NO, arap.getVoucherNo());
                pstArApMain.setInt(FLD_NUMBER_OF_PAYMENT, arap.getNumberOfPayment());
                pstArApMain.setDate(FLD_VOUCHER_DATE, arap.getVoucherDate());
                pstArApMain.setLong(FLD_CONTACT_ID, arap.getContactId());
                pstArApMain.setLong(FLD_ID_PERKIRAAN_LAWAN, arap.getIdPerkiraanLawan());
                pstArApMain.setLong(FLD_ID_PERKIRAAN, arap.getIdPerkiraan());
                pstArApMain.setLong(FLD_ID_CURRENCY, arap.getIdCurrency());
                pstArApMain.setInt(FLD_COUNTER, arap.getCounter());
                pstArApMain.setDouble(FLD_RATE, arap.getRate());
                pstArApMain.setDouble(FLD_AMOUNT, arap.getAmount());
                pstArApMain.setString(FLD_NOTA_NO, arap.getNotaNo());
                pstArApMain.setDate(FLD_NOTA_DATE, arap.getNotaDate());
                pstArApMain.setString(FLD_DESCRIPTION, arap.getDescription());
                pstArApMain.setInt(FLD_ARAP_MAIN_STATUS, arap.getArApMainStatus());
                pstArApMain.setInt(FLD_ARAP_TYPE, arap.getArApType());
                pstArApMain.setInt(FLD_ARAP_DOC_STATUS, arap.getArApDocStatus());
                pstArApMain.setLong(FLD_JOURNAL_ID, arap.getJournalId());
                pstArApMain.setLong(FLD_COMPONENT_DEDUCTION_ID, arap.getComponentDeductionId());
                pstArApMain.setLong(FLD_EMPLOYEE_ID, arap.getEmployeeId());
                pstArApMain.setDate(FLD_ENTRY_DATE, arap.getEntryDate());
                pstArApMain.setInt(FLD_PERIOD_EVERY, arap.getPeriodeEvery());
                pstArApMain.setInt(FLD_PERIOD_EVERY_DMY, arap.getPeriodeEveryDMY());
                pstArApMain.setDate(FLD_START_OF_PAYMENT_DATE, arap.getStartofpaymentdate());
                pstArApMain.setDouble(FLD_PAYMENT_AMOUNT_PLAN, arap.getPayment_amount_plan());
                pstArApMain.setInt(FLD_PERIOD_TYPE, arap.getPeriodType());
                pstArApMain.setLong(FLD_PERIOD_ID, arap.getPeriodId());
                pstArApMain.update();
                return arap.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstArApMain(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long Oid) throws DBException {
        try {
            PstArApMain pstArApMain = new PstArApMain(Oid);
            pstArApMain.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstArApMain(0), DBException.UNKNOWN);
        }
        return Oid;
    }
       
        public static int deleteByJournalId(long oid) {
        DBResultSet dbrs = null;
        int iResult = 0;
        String whereClause = fieldNames[FLD_JOURNAL_ID] + "=" + oid;
        try {                        
            String sql = " DELETE FROM " + PstArApItem.TBL_ARAP_ITEM + " WHERE " + PstArApItem.fieldNames[PstArApItem.FLD_ARAP_MAIN_ID] +
                    " IN ( SELECT "+fieldNames[FLD_ARAP_MAIN_ID]+" FROM "+ TBL_ARAP_MAIN + " m WHERE m."+fieldNames[FLD_JOURNAL_ID] + "=" + oid +")";                    
            DBHandler.execUpdate(sql);            
            
            sql = " DELETE FROM " + PstArApPayment.TBL_ARAP_PAYMENT + " WHERE " + PstArApPayment.fieldNames[PstArApPayment.FLD_ARAP_MAIN_ID] +
                    " IN ( SELECT "+fieldNames[FLD_ARAP_MAIN_ID]+" FROM "+ TBL_ARAP_MAIN + " m WHERE m."+fieldNames[FLD_JOURNAL_ID] + "=" + oid +")";
            DBHandler.execUpdate(sql);                        
            
            sql = " DELETE FROM " + TBL_ARAP_MAIN + " WHERE " + whereClause;
            iResult = DBHandler.execUpdate(sql);
            
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);            
        }
        return iResult;
    }

    public static ArApMain fetchByJournalId(long journalId) {
        String where = fieldNames[FLD_JOURNAL_ID]+"="+journalId;
        Vector v = list( 0,  1,  where, "");
        if(v!=null && v.size()>0){
            return (ArApMain) v.get(0);
        }
        return null;
    }            
        
    public static Vector listByJournalId(int limitStart, int recordToGet, long journalId , String order) {
        String where = fieldNames[FLD_JOURNAL_ID]+"="+journalId;
        return list( limitStart,  recordToGet,  where,  order);
    }
     
        
    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_ARAP_MAIN + " ";
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;

            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0)
                        sql = sql + "";
                    else
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    break;

                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0)
                        sql = sql + "";
                    else
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;

                    break;

                case DBHandler.DBSVR_SYBASE:
                    break;

                case DBHandler.DBSVR_ORACLE:
                    break;

                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    break;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                ArApMain arap = new ArApMain();
                resultToObject(rs, arap);
                lists.add(arap);
            }
        } catch (Exception error) {
            System.out.println(".:: " + new PstArApMain().getClass().getName() + ".list() : " + error.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

    private static void resultToObject(ResultSet rs, ArApMain arap) {
        try {
            arap.setOID(rs.getLong(PstArApMain.fieldNames[PstArApMain.FLD_ARAP_MAIN_ID]));
            arap.setVoucherNo(rs.getString(PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_NO]));
            arap.setVoucherDate(rs.getDate(PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_DATE]));
            arap.setNumberOfPayment(rs.getInt(PstArApMain.fieldNames[PstArApMain.FLD_NUMBER_OF_PAYMENT]));
            arap.setContactId(rs.getLong(PstArApMain.fieldNames[PstArApMain.FLD_CONTACT_ID]));
            arap.setIdPerkiraanLawan(rs.getLong(PstArApMain.fieldNames[PstArApMain.FLD_ID_PERKIRAAN_LAWAN]));
            arap.setIdCurrency(rs.getLong(PstArApMain.fieldNames[PstArApMain.FLD_ID_CURRENCY]));
            arap.setCounter(rs.getInt(PstArApMain.fieldNames[PstArApMain.FLD_COUNTER]));
            arap.setRate(rs.getDouble(PstArApMain.fieldNames[PstArApMain.FLD_RATE]));
            arap.setAmount(rs.getDouble(PstArApMain.fieldNames[PstArApMain.FLD_AMOUNT]));
            arap.setNotaNo(rs.getString(PstArApMain.fieldNames[PstArApMain.FLD_NOTA_NO]));
            arap.setNotaDate(rs.getDate(PstArApMain.fieldNames[PstArApMain.FLD_NOTA_DATE]));
            arap.setDescription(rs.getString(PstArApMain.fieldNames[PstArApMain.FLD_DESCRIPTION]));
            arap.setArApMainStatus(rs.getInt(PstArApMain.fieldNames[PstArApMain.FLD_ARAP_MAIN_STATUS]));
            arap.setArApType(rs.getInt(PstArApMain.fieldNames[PstArApMain.FLD_ARAP_TYPE]));
            arap.setArApDocStatus(rs.getInt(PstArApMain.fieldNames[PstArApMain.FLD_ARAP_DOC_STATUS]));
            arap.setJournalId(rs.getLong(PstArApMain.fieldNames[PstArApMain.FLD_JOURNAL_ID]));
            arap.setComponentDeductionId(rs.getLong(PstArApMain.fieldNames[PstArApMain.FLD_COMPONENT_DEDUCTION_ID]));
            arap.setEmployeeId(rs.getLong(PstArApMain.fieldNames[PstArApMain.FLD_EMPLOYEE_ID]));
            arap.setEntryDate(rs.getDate(PstArApMain.fieldNames[PstArApMain.FLD_ENTRY_DATE]));
            arap.setPeriodeEvery(rs.getInt(PstArApMain.fieldNames[PstArApMain.FLD_PERIOD_EVERY]));
            arap.setPeriodeEveryDMY(rs.getInt(PstArApMain.fieldNames[PstArApMain.FLD_PERIOD_EVERY_DMY]));
            arap.setStartofpaymentdate(rs.getDate(PstArApMain.fieldNames[PstArApMain.FLD_START_OF_PAYMENT_DATE]));
            arap.setPayment_amount_plan(rs.getDouble(PstArApMain.fieldNames[PstArApMain.FLD_PAYMENT_AMOUNT_PLAN]));
            arap.setPeriodType(rs.getInt(PstArApMain.fieldNames[PstArApMain.FLD_PERIOD_TYPE]));
            arap.setPeriodId(rs.getLong(PstArApMain.fieldNames[PstArApMain.FLD_PERIOD_ID]));
        
        } catch (Exception e) {
            System.out.println("resultToObject() " + e.toString());
        }
    }

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            //String sql = "SELECT COUNT(" + PstArApMain.fieldNames[PstArApMain.FLD_ARAP_MAIN_ID] + ") " +
              //      " FROM " + TBL_ARAP_MAIN;
            String sql = "SELECT MAX(" + PstArApMain.fieldNames[PstArApMain.FLD_COUNTER] + ") " +
                " FROM " + TBL_ARAP_MAIN;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            return count;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    
    public static Vector listDeductionPayroll(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
//        
//        SELECT 
//  he.`EMPLOYEE_NUM`,
//  he.`FULL_NAME`,
//  hd.`DEPARTMENT`,
//  ham.`amount`,
//  ham.`description` 
//FROM
//  `hr_arap_main` ham 
//  INNER JOIN `hr_employee` he 
//    ON he.`EMPLOYEE_ID` = ham.`employee_id` 
//  INNER JOIN `hr_department` hd 
//    ON hd.`DEPARTMENT_ID` = he.`DEPARTMENT_ID` 
        
        try {
            String sql = "SELECT "
                    + " he." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] 
                    + ", he." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] 
                    + ", hd." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", SUM(hai." + PstArApItem.fieldNames[PstArApItem.FLD_LEFT_TO_PAY]+ " ) as total "
                    + ", ham." + PstArApMain.fieldNames[PstArApMain.FLD_DESCRIPTION]
                    + ", ham." + PstArApMain.fieldNames[PstArApMain.FLD_ARAP_MAIN_ID]
                    + ", he." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                    + ", ham." + PstArApMain.fieldNames[PstArApMain.FLD_COMPONENT_DEDUCTION_ID] ; 
                    
                    sql = sql + " FROM " + PstArApItem.TBL_ARAP_ITEM + " hai "
                    + " INNER JOIN " + PstArApMain.TBL_ARAP_MAIN + " ham ON ham."+PstArApMain.fieldNames[PstArApMain.FLD_ARAP_MAIN_ID] + " = hai." +PstArApItem.fieldNames[PstArApItem.FLD_ARAP_MAIN_ID] 

                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " he ON he."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = ham." +PstArApMain.fieldNames[PstArApMain.FLD_EMPLOYEE_ID] 

                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " hd ON hd." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = he." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] ;
                    
                    if (whereClause != null && whereClause.length() > 0)
                
                        sql = sql + " WHERE " + whereClause;

            
                    if (order != null && order.length() > 0)
                
                        sql = sql + " ORDER BY " + order;

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0)
                        sql = sql + "";
                    else
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    break;

                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0)
                        sql = sql + "";
                    else
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;

                    break;

                case DBHandler.DBSVR_SYBASE:
                    break;

                case DBHandler.DBSVR_ORACLE:
                    break;

                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    break;
            }
            sql =sql + "GROUP BY ham.`arap_main_id`";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                ArApEmpDeduction arApEmpDeduction = new ArApEmpDeduction() ;
                 arApEmpDeduction.setEmpNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                 arApEmpDeduction.setEmpName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                 arApEmpDeduction.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                 arApEmpDeduction.setAmount(rs.getDouble("total"));
                 arApEmpDeduction.setArapMainId(rs.getLong(PstArApMain.fieldNames[PstArApMain.FLD_ARAP_MAIN_ID]));
                 arApEmpDeduction.setDescription(rs.getString(PstArApMain.fieldNames[PstArApMain.FLD_DESCRIPTION]));
                 arApEmpDeduction.setEmployeeId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                 arApEmpDeduction.setCompdeductionId(rs.getLong(PstArApMain.fieldNames[PstArApMain.FLD_COMPONENT_DEDUCTION_ID]));
                 
                 lists.add(arApEmpDeduction);
            }
        } catch (Exception error) {
            System.out.println(".:: " + new PstArApItem().getClass().getName() + ".list() : " + error.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }
    
public static String listDeductionPayrollDistinct(int limitStart, int recordToGet, String whereClause, String order) {
        String deductionId = "";
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "
                    + "DISTINCT (ham." + PstArApMain.fieldNames[PstArApMain.FLD_COMPONENT_DEDUCTION_ID]+" )"; 
                    
                    sql = sql + " FROM " + TBL_ARAP_MAIN + " ham "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " he ON he."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = ham." +PstArApMain.fieldNames[PstArApMain.FLD_EMPLOYEE_ID] 

                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " hd ON hd." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = he." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] ;
                    sql = sql+" , `hr_arap_item` hai ";
                    if (whereClause != null && whereClause.length() > 0)
                
                        sql = sql + " WHERE " + whereClause;

            
                    if (order != null && order.length() > 0)
                
                        sql = sql + " ORDER BY " + order;

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0)
                        sql = sql + "";
                    else
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    break;

                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0)
                        sql = sql + "";
                    else
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;

                    break;

                case DBHandler.DBSVR_SYBASE:
                    break;

                case DBHandler.DBSVR_ORACLE:
                    break;

                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    break;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
             
                  deductionId = deductionId + " " +rs.getLong(PstArApMain.fieldNames[PstArApMain.FLD_COMPONENT_DEDUCTION_ID]) +" , ";
                
              
               
            }
        } catch (Exception error) {
            System.out.println(".:: " + new PstArApItem().getClass().getName() + ".list() : " + error.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return deductionId;
    }
    

    /**
     * untuk pembuatan nomor doc arap
     */
    public synchronized static ArApMain createOrderNomor(ArApMain arApMain) {
        String nmr = "";
        try {
            nmr = Formater.formatDate(arApMain.getVoucherDate(), "yyMM");
            String s = PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_DATE];
            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    s = "DATE_FORMAT(" + PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_DATE] + ", '%Y-%m')";
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    s = "TO_CHAR(" + PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_DATE] + ", 'YYYY-MM')";
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;
                default:
                    s = "DATE_FORMAT(" + PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_DATE] + ", '%Y-%m')";
            }
            String where = s + " = '" + Formater.formatDate(arApMain.getVoucherDate(), "yyyy-MM") + "'";
            int cnt = getCount(where) + 1;
            arApMain.setCounter(cnt);
            switch (String.valueOf(cnt).length()) {
                case 1:
                    nmr = nmr + "-00" + cnt;
                    break;
                case 2:
                    nmr = nmr + "-0" + cnt;
                    break;
                default:
                    nmr = nmr + "-" + cnt;
            }
            arApMain.setVoucherNo(nmr);
        } catch (Exception e) {
            System.out.println(e);
            arApMain.setVoucherNo("0000-000");
        }
        return arApMain;
    }
    
    
    
    public static long getPaySlipId(long periodId, long employeeId) {
        DBResultSet dbrs = null;
        long result=0;
        try {
            String sql = "SELECT " + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] + 
                    " FROM " + PstPaySlip.TBL_PAY_SLIP;
            if (periodId > 0){
                sql = sql + " WHERE EMPLOYEE_ID = " + employeeId + " AND PERIOD_ID = " + periodId +" AND PERIOD_ID = " + periodId ;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

           // double result = 0;
            if (rs.next()) {
                result = rs.getLong(1);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
        public static long getPaySlipCompId(long paySlipId, String compCode) {
        DBResultSet dbrs = null;
        long result=0;
        try {
            String sql = "SELECT " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_COMP_ID] + 
                    " FROM " + PstPaySlipComp.TBL_PAY_SLIP_COMP;
      
                sql = sql + " WHERE PAY_SLIP_ID = " + paySlipId + " AND COMP_CODE = \"" + compCode + "\" " ;
          
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

           // double result = 0;
            if (rs.next()) {
                result = rs.getLong(1);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
  /**
         * update payroll
         * @param originalId
         * @param newId
         * @return
         */
        public static long ExportPayroll(Vector listArapItem, long PayperiodId, long companyId){
            DBResultSet dbrs = null;
            
            for (int i = 0; i<listArapItem.size(); i++ ){
                ArApEmpDeduction arApEmpDeduction = (ArApEmpDeduction) listArapItem.get(i);
                arApEmpDeduction.getEmployeeId();
                PayComponent payComponent = new PayComponent();
                try {
                    payComponent = PstPayComponent.fetchExc(arApEmpDeduction.getCompdeductionId());
                }catch (Exception e ){
                    
                }
                
                long payslipId = getPaySlipId(PayperiodId, arApEmpDeduction.getEmployeeId());
                long payslipcompId = getPaySlipCompId(payslipId, payComponent.getCompCode());
                if (payslipcompId > 0 && payslipId > 0 ){
                    //update
                    try {
                        PaySlipComp paySlipComp = PstPaySlipComp.fetchExc(payslipcompId);
                        paySlipComp.setCompValue(arApEmpDeduction.getAmount());
                        paySlipComp.setPaySlipId(payslipId);
                        paySlipComp.setCompCode(payComponent.getCompCode());
                        paySlipComp.setOID(payslipcompId);
                        long update = PstArApMain.updatePayCompVal(paySlipComp);
                    } catch (Exception e){
                        
                    }
                } else if (payslipcompId == 0 && payslipId > 0 ){ 
                    //insert
                    try {
                        PaySlipComp paySlipComp = new PaySlipComp();
                        paySlipComp.setCompValue(arApEmpDeduction.getAmount());
                        paySlipComp.setPaySlipId(payslipId);
                        paySlipComp.setCompCode(payComponent.getCompCode());
                        paySlipComp.setOID(payslipcompId);
                        long insert = PstPaySlipComp.insertExc(paySlipComp);
                    } catch (Exception e){
                        
                    }
                    
                    
                } else {
                    return 0;
                           
                }
            }
//            
//            try {
//                String sql = "UPDATE " + TBL_ARAP_MAIN+ " SET " +
//                PstArApMain.fieldNames[PstArApMain.FLD_ARAP_MAIN_ID] + " = " + newId +
//                " WHERE " + PstArApMain.fieldNames[PstArApMain.FLD_ARAP_MAIN_ID] +
//                " = " + originalId;
//                DBHandler.execUpdate(sql);
//                return newId;
//            }catch(Exception e) {
//                return 0;
//            }finally {
//                DBResultSet.close(dbrs);
//            }
                return 0;
        }

        
        
  /**
         * update payroll
         * @param originalId
         * @param newId
         * @return
         */
        public static long ExporttoPayment(Vector listArapItem, long PayperiodId, long companyId){
            DBResultSet dbrs = null;
            
            for (int i = 0; i<listArapItem.size(); i++ ){
                ArApEmpDeduction arApEmpDeduction = (ArApEmpDeduction) listArapItem.get(i);
                arApEmpDeduction.getEmployeeId();
                PayComponent payComponent = new PayComponent();
                try {
                    payComponent = PstPayComponent.fetchExc(arApEmpDeduction.getCompdeductionId());
                }catch (Exception e ){
                    
                }
                
                long payslipId = getPaySlipId(PayperiodId, arApEmpDeduction.getEmployeeId());
                long payslipcompId = getPaySlipCompId(payslipId, payComponent.getCompCode());
                
                PaySlipComp paySlipComp = new PaySlipComp();
                try {
                    paySlipComp = PstPaySlipComp.fetchExc(payslipcompId);
                }catch (Exception e){
                    System.out.printf("payslip componen kosong");
                }
                
                
                String whereclause = "ARAP_ITEM_STATUS = 0 AND ARAP_MAIN_ID = " + arApEmpDeduction.getArapMainId(); 
                Vector arapitem = PstArApItem.list(0, 0, whereclause, "DUE_DATE");
                ArApItem arApItemFirst = (ArApItem)arapitem.get(0);
                
                
                double amountPayment = paySlipComp.getCompValue();
                for (int ap = 0; ap < arapitem.size() ;ap++){
                    ArApItem arApItem = (ArApItem)arapitem.get(ap);
                    if (amountPayment > 0){
                  //  double angsuranPay = amountPayment - arApItem.getLeftToPay() ;
                    double sisalefttopay = 0 ;
                    if (sisalefttopay < 0){
                        sisalefttopay = Math.abs(sisalefttopay);
                    }
                    
                    if (amountPayment >= arApItem.getLeftToPay()){
                       amountPayment = amountPayment - arApItem.getLeftToPay();
                       sisalefttopay = 0 ;
                    } else {
                        sisalefttopay = arApItem.getLeftToPay() - amountPayment;
                        amountPayment = 0;
                    }
                    
                  
                        if (sisalefttopay < 1){
                         arApItem.setArApItemStatus(1);
                        }
                        arApItem.setLeftToPay(sisalefttopay);
                        
                        try{
                            long save = PstArApItem.updateExc(arApItem);
                           // amountPayment =angsuranPay;
                        } catch (Exception e){
                            System.out.printf("gagal update" + arApItem.getDescription() );
                        }
                    }
                }
                
                //cek apakah semua lunas
                 Vector allitem = PstArApItem.list(0, 0, whereclause, "DUE_DATE");
                if (allitem.size() == 0 || allitem.size() <= 0 ){
                    
                    try{
                        ArApMain arApMain = PstArApMain.fetchExc(arApEmpDeduction.getArapMainId());
                        arApMain.setArApMainStatus(1);
                        long updatemain = PstArApMain.updateExc(arApMain);
                    }catch (Exception e){
                        System.out.printf("gagal update arap main" );
                    }
                }
                //update arappayment
                Date datenya = new Date();
                    try {
                        ArApPayment arApPayment = new ArApPayment(); 
                        arApPayment.setAmount(arApPayment.getAmount() - amountPayment);
                        arApPayment.setPaymentType(1);  
                        arApPayment.setPaymentDate(datenya);
                        arApPayment = PstArApPayment.createOrderNomor(arApPayment);
                        
                           long oid = PstArApPayment.insertExc(arApPayment); 
                       
                        
                    } catch (Exception exc) {
                        
                    }
                
                
                
            }
                return 0;
        }

        
        
    /**
         * update oid lama dengan yang baru
         * @param originalId
         * @param newId
         * @return
         */
        public static long updateSynchOID(long originalId, long newId){
            DBResultSet dbrs = null;
            try {
                String sql = "UPDATE " + TBL_ARAP_MAIN+ " SET " +
                PstArApMain.fieldNames[PstArApMain.FLD_ARAP_MAIN_ID] + " = " + newId +
                " WHERE " + PstArApMain.fieldNames[PstArApMain.FLD_ARAP_MAIN_ID] +
                " = " + originalId;
                DBHandler.execUpdate(sql);
                return newId;
            }catch(Exception e) {
                return 0;
            }finally {
                DBResultSet.close(dbrs);
            }
        }

        // new from priska 20150421
         public static long updatePayCompVal(PaySlipComp paySlipComp){
            DBResultSet dbrs = null;
            try {
                String sql = "UPDATE " + PstPaySlipComp.TBL_PAY_SLIP_COMP+ " SET " +
                PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + " = " + paySlipComp.getCompValue() +
                " WHERE " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_COMP_ID] +
                " = " + paySlipComp.getOID();
                DBHandler.execUpdate(sql);
                return paySlipComp.getOID();
            }catch(Exception e) {
                return 0;
            }finally {
                DBResultSet.close(dbrs);
            }
        }
        

}
