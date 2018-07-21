/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.harisma.entity.masterdata.LeavePeriod;
import com.dimata.harisma.entity.masterdata.PstLeavePeriod;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import static com.dimata.qdep.db.I_DBType.TYPE_DATE;
import static com.dimata.qdep.db.I_DBType.TYPE_FLOAT;
import static com.dimata.qdep.db.I_DBType.TYPE_ID;
import static com.dimata.qdep.db.I_DBType.TYPE_INT;
import static com.dimata.qdep.db.I_DBType.TYPE_LONG;
import static com.dimata.qdep.db.I_DBType.TYPE_PK;
import static com.dimata.qdep.db.I_DBType.TYPE_STRING;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.system.entity.system.SystemProperty;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Dimata 007
 */
public class PstPayPeriod extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_PAY_PERIOD = "pay_period";

    public static final int FLD_PERIOD_ID = 0;
    public static final int FLD_PERIOD = 1;
    public static final int FLD_START_DATE = 2;
    public static final int FLD_END_DATE = 3;
    
    public static final int FLD_WORK_DAYS = 4;
    public static final int FLD_PAY_SLIP_DATE = 5;
    public static final int FLD_MIN_REG_WAGE = 6;//upah minimum regional
    public static final int FLD_PAY_PROCESS = 7;
    public static final int FLD_PAY_PROC_BY = 8;
    public static final int FLD_PAY_PROC_DATE = 9;
    
    public static final int FLD_PAY_PROCESS_CLOSE = 10;
    public static final int FLD_PAY_PROC_BY_CLOSE = 11;
    public static final int FLD_PAY_PROC_DATE_CLOSE = 12;
    public static final int FLD_TAX_IS_PAID = 13;

    public static final String[] fieldNames = {
        "PERIOD_ID",
        "PERIOD",
        "START_DATE",
        "END_DATE",
        
        "WORK_DAYS",
        "PAY_SLIP_DATE",
        "MIN_REG_WAGE",
        "PAY_PROCESS",
        "PAY_PROC_BY",
        "PAY_PROC_DATE",
        
        "PAY_PROCESS_CLOSE",
        "PAY_PROC_BY_CLOSE",
        "PAY_PROC_DATE_CLOSE",
        "TAX_IS_PAID"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        
        TYPE_INT,
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_DATE,
        
        TYPE_INT,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_INT,
        
    };
    
    
    public static final int SUDAH = 1;
    public static final int BELUM = 2;

    public static final String[]prosesGaji = {
        "","Process","Not Process"
    };

    
    public static final int PAJAK_TIDAK_DISETOR = 0;
    public static final int PAJAK_DISETOR = 1;

    public static final String[]pajakNames = {
      "Tidak",
      "Ya"
    };

    public PstPayPeriod() {
    }

    public PstPayPeriod(int i) throws DBException {
        super(new PstPayPeriod());
    }

    public PstPayPeriod(String sOid) throws DBException {
        super(new PstPayPeriod(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public PstPayPeriod(long lOid) throws DBException {
        super(new PstPayPeriod(0));
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
        return TBL_HR_PAY_PERIOD;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPayPeriod().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        PayPeriod payPayPeriod = fetchExc(ent.getOID());
        ent = (Entity) payPayPeriod;
        return payPayPeriod.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((PayPeriod) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((PayPeriod) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static PayPeriod fetchExc(long oid) throws DBException {
        try {
            PayPeriod payPeriod = new PayPeriod();
            PstPayPeriod pstPayPeriod = new PstPayPeriod(oid);
            payPeriod.setOID(oid);

            payPeriod.setPeriod(pstPayPeriod.getString(FLD_PERIOD));
            payPeriod.setStartDate(pstPayPeriod.getDate(FLD_START_DATE));
            payPeriod.setEndDate(pstPayPeriod.getDate(FLD_END_DATE));
            
            payPeriod.setWorkDays(pstPayPeriod.getInt(FLD_WORK_DAYS));
            payPeriod.setPaySlipDate(pstPayPeriod.getDate(FLD_PAY_SLIP_DATE));
            payPeriod.setMinRegWage(pstPayPeriod.getdouble(FLD_MIN_REG_WAGE)); 
            
            payPeriod.setPayProsess(pstPayPeriod.getInt(FLD_PAY_PROCESS));
            payPeriod.setPayProcBy(pstPayPeriod.getString(FLD_PAY_PROC_BY));
            payPeriod.setPayProcDate(pstPayPeriod.getDate(FLD_PAY_PROC_DATE));
           
            
            payPeriod.setPayProcessClose(pstPayPeriod.getInt(FLD_PAY_PROCESS_CLOSE));
            payPeriod.setPayProcByClose(pstPayPeriod.getString(FLD_PAY_PROC_BY_CLOSE));
            payPeriod.setPayProcDateClose(pstPayPeriod.getDate(FLD_PAY_PROC_DATE_CLOSE));
             payPeriod.setTaxIsPaid(pstPayPeriod.getInt(FLD_TAX_IS_PAID));
            return payPeriod;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayPeriod(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(PayPeriod payPeriod) throws DBException {
        try {
            PstPayPeriod pstPayPeriod = new PstPayPeriod(0);

            pstPayPeriod.setString(FLD_PERIOD, payPeriod.getPeriod());
            pstPayPeriod.setDate(FLD_START_DATE, payPeriod.getStartDate());
            pstPayPeriod.setDate(FLD_END_DATE, payPeriod.getEndDate());
            
            pstPayPeriod.setInt(FLD_WORK_DAYS, payPeriod.getWorkDays());
            pstPayPeriod.setDate(FLD_PAY_SLIP_DATE, payPeriod.getPaySlipDate());
            pstPayPeriod.setDouble(FLD_MIN_REG_WAGE, payPeriod.getMinRegWage());
            pstPayPeriod.setInt(FLD_PAY_PROCESS, payPeriod.getPayProsess());
            pstPayPeriod.setString(FLD_PAY_PROC_BY, payPeriod.getPayProcBy());
            pstPayPeriod.setDate(FLD_PAY_PROC_DATE, payPeriod.getPayProcDate());
           
            
            pstPayPeriod.setInt(FLD_PAY_PROCESS_CLOSE, payPeriod.getPayProcessClose());
            pstPayPeriod.setString(FLD_PAY_PROC_BY_CLOSE, payPeriod.getPayProcByClose());
            pstPayPeriod.setDate(FLD_PAY_PROC_DATE_CLOSE, payPeriod.getPayProcDateClose());
             pstPayPeriod.setInt(FLD_TAX_IS_PAID, payPeriod.getTaxIsPaid());

            pstPayPeriod.insert();
            payPeriod.setOID(pstPayPeriod.getlong(FLD_PERIOD_ID));
            //update by satrya 2013-02-12
            //di matikan karena belum tau fungsinya (persetujuan pak tut)
            //sendPayPeriodToLeavePayPeriod(payPeriod.getStartDate(), payPeriod.getEndDate());

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayPeriod(0), DBException.UNKNOWN);
        }
        return payPeriod.getOID();
    }

    public static long updateExc(PayPeriod payPeriod) throws DBException {
        try {
            if (payPeriod.getOID() != 0) {
                PstPayPeriod pstPayPeriod = new PstPayPeriod(payPeriod.getOID());

            pstPayPeriod.setString(FLD_PERIOD, payPeriod.getPeriod());
            pstPayPeriod.setDate(FLD_START_DATE, payPeriod.getStartDate());
            pstPayPeriod.setDate(FLD_END_DATE, payPeriod.getEndDate());
            
            pstPayPeriod.setInt(FLD_WORK_DAYS, payPeriod.getWorkDays());
            pstPayPeriod.setDate(FLD_PAY_SLIP_DATE, payPeriod.getPaySlipDate());
            pstPayPeriod.setDouble(FLD_MIN_REG_WAGE, payPeriod.getMinRegWage());
            pstPayPeriod.setInt(FLD_PAY_PROCESS, payPeriod.getPayProsess());
            pstPayPeriod.setString(FLD_PAY_PROC_BY, payPeriod.getPayProcBy());
            pstPayPeriod.setDate(FLD_PAY_PROC_DATE, payPeriod.getPayProcDate());
           
            
            pstPayPeriod.setInt(FLD_PAY_PROCESS_CLOSE, payPeriod.getPayProcessClose());
            pstPayPeriod.setString(FLD_PAY_PROC_BY_CLOSE, payPeriod.getPayProcByClose());
            pstPayPeriod.setDate(FLD_PAY_PROC_DATE_CLOSE, payPeriod.getPayProcDateClose());
             pstPayPeriod.setInt(FLD_TAX_IS_PAID, payPeriod.getTaxIsPaid());
    
                pstPayPeriod.update();
                
                //sendPayPeriodToLeavePayPeriod(payPeriod.getStartDate(), payPeriod.getEndDate());
                return payPeriod.getOID();


            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayPeriod(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPayPeriod pstPayPeriod = new PstPayPeriod(oid);
            pstPayPeriod.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayPeriod(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    
     /**
     * Create by priska 2015-09-03
     * untuk list hashtable period
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
     public static Hashtable hashlistTblPeriodName(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashtablePeriod = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_PAY_PERIOD;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            
           // System.out.println(":::::::::::::::::: "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PayPeriod payPeriod = new PayPeriod();
                resultToObject(rs, payPeriod);
                hashtablePeriod.put(rs.getLong(PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID]), payPeriod.getPeriod());
            }
            rs.close();
            return hashtablePeriod;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return hashtablePeriod;
    }
     
    
    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_PAY_PERIOD;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            
           // System.out.println(":::::::::::::::::: "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PayPeriod payPeriod = new PayPeriod();
                resultToObject(rs, payPeriod);
                lists.add(payPeriod);
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
    
  
    private static void resultToObject(ResultSet rs, PayPeriod payPeriod) {
        try {
            payPeriod.setOID(rs.getLong(PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID]));
            payPeriod.setPeriod(rs.getString(PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD]));
            payPeriod.setStartDate(rs.getDate(PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE]));
            payPeriod.setEndDate(rs.getDate(PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE]));
            
            payPeriod.setWorkDays(rs.getInt(PstPayPeriod.fieldNames[PstPayPeriod.FLD_WORK_DAYS]));
            payPeriod.setPaySlipDate(rs.getDate(PstPayPeriod.fieldNames[PstPayPeriod.FLD_PAY_SLIP_DATE]));
            payPeriod.setMinRegWage(rs.getDouble(PstPayPeriod.fieldNames[PstPayPeriod.FLD_MIN_REG_WAGE]));
            payPeriod.setPayProsess(rs.getInt(PstPayPeriod.fieldNames[PstPayPeriod.FLD_PAY_PROCESS]));
            payPeriod.setPayProcBy(rs.getString(PstPayPeriod.fieldNames[PstPayPeriod.FLD_PAY_PROC_BY]));
            payPeriod.setPayProcDate(rs.getDate(PstPayPeriod.fieldNames[PstPayPeriod.FLD_PAY_PROC_DATE]));
            
            
            payPeriod.setPayProcessClose(rs.getInt(PstPayPeriod.fieldNames[PstPayPeriod.FLD_PAY_PROCESS_CLOSE]));
            payPeriod.setPayProcByClose(rs.getString(PstPayPeriod.fieldNames[PstPayPeriod.FLD_PAY_PROC_BY_CLOSE]));
            payPeriod.setPayProcDateClose(rs.getDate(PstPayPeriod.fieldNames[PstPayPeriod.FLD_PAY_PROC_DATE_CLOSE]));
            payPeriod.setTaxIsPaid(rs.getInt(PstPayPeriod.fieldNames[PstPayPeriod.FLD_TAX_IS_PAID]));
        } catch (Exception e) {
            System.out.println("PayPeriod result:"+ e);
        }
    }

    public static boolean checkOID(long payPeriodId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_PAY_PERIOD + " WHERE " +
                    PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] + " = " + payPeriodId;

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
            String sql = "SELECT COUNT(" + PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] + ") FROM " + TBL_HR_PAY_PERIOD;
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
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }


    /* This method used to find current data */
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String order) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, order);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    PayPeriod payPeriod = (PayPeriod) list.get(ls);
                    if (oid == payPeriod.getOID())
                        found = true;
                }
            }
        }
        if ((start >= size) && (size > 0))
            start = start - recordToGet;

        return start;
    }


   /* public static boolean checkMaster(long oid) {
        if (PstEmpSchedule.checkPayPeriode(oid))
            return true;
        else
            return false;
    }*/

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + mdl;
        if (start == 0)
            cmd = Command.FIRST;
        else {
            if (start == (vectSize - recordToGet))
                cmd = Command.LAST;
            else {
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


    /**
     * get OID of payPeriod object that wrap selectedDate
     * @param selectedDate
     * @return long payPeriodId
     * @created by Edhy
     */
    public static long getPayPeriodIdBySelectedDate(Date selectedDate) {
        
        long result = 0;
        DBResultSet dbrs = null;
        String strDate = "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "\"";
        try {
            String sql = "SELECT " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] +
                    " FROM " + TBL_HR_PAY_PERIOD +
                    " WHERE " + strDate +
                    " BETWEEN " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] +   
                    " AND " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE];
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getLong(1);
            }
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    //priska 20161003
     public static long getPayPeriodIdByName(String Name ) {
        
        long result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] +
                    " FROM " + TBL_HR_PAY_PERIOD +
                    " WHERE " +  PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD] +
                    " = \"" + Name+ "\"";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getLong(1);
            }
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
     //update by satrya 2012-09-03
    /**
     * 
     * @param selectedDate 
     * @return 
     * @descrip: untuk mencari payPeriode sebelumnya
     */
    public static PayPeriod getPrevPayPeriodBySelectedDate(Date selectedDate) {        
        PayPeriod result = null;
        DBResultSet dbrs = null;
        try {
            PayPeriod resultCurrPayPeriod = getPayPeriodBySelectedDate(selectedDate);
            long lCurrPayPeriod = resultCurrPayPeriod.getOID();
            String sql = "SELECT * FROM " +TBL_HR_PAY_PERIOD + "  p WHERE p."
                    +PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE]
                    +" < (SELECT p1."+PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE]
                    +" FROM " + TBL_HR_PAY_PERIOD + " p1 WHERE p1."+PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID]
                    +" = " + lCurrPayPeriod +")" 
                    +" ORDER BY p."+PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " DESC LIMIT 0,1 ";
    
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                 result = new PayPeriod();
                resultToObject(rs, result);
            }
        } catch (Exception e) {
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    //update by satrya 2012-09-03
    /**
     * 
     * @param selectedDate
     * @return 
     * @desc: untuk mencari payPeriode sekarang
     */
    public static PayPeriod getPayPeriodBySelectedDate(Date selectedDate) {        
        PayPeriod result = null;
        DBResultSet dbrs = null;
    
        String strDate = "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "\"";
        try {
            String sql = "SELECT * " + /*  PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] + */
                    " FROM " + TBL_HR_PAY_PERIOD +
                    " WHERE " + strDate +
                    " BETWEEN " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] +   
                    " AND " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE];
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = new PayPeriod();
                resultToObject(rs, result);
                //result = rs.getLong(1);
            }
        } catch (Exception e) {
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    //update by satrya 2012-09-03
    /**
     * 
     * @param selectedDate
     * @return 
     * @desc : untuk mencari payPeriode selanjutnya
     */
    public static PayPeriod getNextPayPeriodBySelectedDate(Date selectedDate) {        
        PayPeriod result = null;
        DBResultSet dbrs = null;
        try {
            PayPeriod resultCurrPayPeriod = getPayPeriodBySelectedDate(selectedDate);
            long lCurrPayPeriod = resultCurrPayPeriod.getOID();
             String sql = "SELECT * FROM " +TBL_HR_PAY_PERIOD + "  p WHERE p."
                    +PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE]
                    +" > (SELECT p1."+PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE]
                    +" FROM " + TBL_HR_PAY_PERIOD + " p1 WHERE p1."+PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID]
                    +" = " + lCurrPayPeriod +")" 
                    +" ORDER BY p."+PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + " ASC LIMIT 0,1 ";
                   
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                 result = new PayPeriod();
                resultToObject(rs, result);
                //result = rs.getLong(1);
            }
        } catch (Exception e) {
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    /**
     * get OID of payPeriod object that wrap selectedDate
     * @param selectedDate
     * @return
     * @created by Yunny
     */
    public static long getPayPeriodIdBySelectedDateString(String selectedDate) {
        long result = 0;
        DBResultSet dbrs = null;
        String strDate = "\"" + selectedDate + "\"";
        try {
            String sql = "SELECT " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] +
                    " FROM " + TBL_HR_PAY_PERIOD +
                    " WHERE " + strDate +
                    " BETWEEN " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] +   
                    " AND " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE];
            //System.out.println("\tgetPayPeriodIdBySelectedDate : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getLong(1);
            }
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }



    /**
     * this method used to get payPeriodId just before current payPeriod
     * @param currPayPeriodId
     * @return
     */
    public static long getPayPeriodIdJustBefore(long currPayPeriodId) {
        long result = 0;
        if (currPayPeriodId != 0) {
            String orderBy = PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] + " DESC";
            Vector vectLastPayPeriod = PstPayPeriod.list(0, 0, "", orderBy);
            if (vectLastPayPeriod != null && vectLastPayPeriod.size() > 1) {
                for (int i = 0; i < vectLastPayPeriod.size(); i++) {
                    PayPeriod per = (PayPeriod) vectLastPayPeriod.get(i);
                    if (currPayPeriodId == per.getOID() && i < vectLastPayPeriod.size() - 1) {
                        per = (PayPeriod) vectLastPayPeriod.get(i + 1);
                        return per.getOID();
                    }
                }
            }

            if (vectLastPayPeriod.size() == 1) {
                PayPeriod per = (PayPeriod) vectLastPayPeriod.get(0);
                if (per.getOID() != currPayPeriodId) {
                    return per.getOID();
                }
            }
        }
        return result;
    }

    /** gadnyana
     * untuk pembuatan leave payPeriod
     * @param startDt
     * @param endDt
     */
    public static void sendPayPeriodToLeavePayPeriod(Date startDt, Date endDt) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDt);
            int month1st = startDt.getMonth();
            int month2st = endDt.getMonth();
            int monthDiff = month2st - month1st;

            if ((monthDiff > -1) && (startDt.getYear()==endDt.getYear())) { // true
                if (monthDiff == 0) { // jika bulan hanya 1
                    LeavePeriod leavePayPeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(startDt);
                    if (leavePayPeriod.getOID() == 0) {
                        // start date
                        startDt.setDate(1);
                        leavePayPeriod.setStartDate(startDt);

                        // end date
                        Calendar gre = Calendar.getInstance();
                        gre.setTime(startDt);
                        int day = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                        Date dtEnd = new Date(startDt.getYear(), startDt.getMonth(), day);
                        leavePayPeriod.setEndDate(dtEnd);
                        System.out.println("===>> CREATE LEAVE PERIOD : TYPE FULL");
                        try {
                            PstLeavePeriod.insertExc(leavePayPeriod);
                        } catch (Exception e) {
                        }
                    }
                } else { // jika bulan nya lebih dari 1
                    for (int k = startDt.getMonth(); k <= endDt.getMonth(); k++) {
                        LeavePeriod leavePayPeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(startDt);
                        if (SystemProperty.SYS_PROP_SCHEDULE_LEAVE_PERIOD == SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL) { // jika tidak mengikuti tanggal payPeriod
                            if (leavePayPeriod.getOID() == 0) {
                                // start date
                                startDt.setDate(1);
                                leavePayPeriod.setStartDate(startDt);

                                // end date
                                Calendar gre = Calendar.getInstance();
                                gre.setTime(startDt);
                                int day = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                                Date dtEnd = new Date(startDt.getYear(), startDt.getMonth(), day);
                                leavePayPeriod.setEndDate(dtEnd);
                                System.out.println("===>> CREATE LEAVE PERIOD : TYPE FULL");
                                try {
                                    PstLeavePeriod.insertExc(leavePayPeriod);
                                } catch (Exception e) {
                                }
                            }
                            startDt.setMonth(k + 1);

                        } else { // jika mengikuti tanggal payPeriod
                            if (leavePayPeriod.getOID() == 0) {
                                leavePayPeriod.setStartDate(startDt);
                                leavePayPeriod.setEndDate(endDt);
                                System.out.println("===>> CREATE LEAVE PERIOD : TYPE HAFT");
                                try {
                                    PstLeavePeriod.insertExc(leavePayPeriod);
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
            }else{ // jika start date dan end date beda tahun
                for (int k = startDt.getMonth(); k <= 12; k++) {
                    LeavePeriod leavePayPeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(startDt);
                    if (SystemProperty.SYS_PROP_SCHEDULE_LEAVE_PERIOD == SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL) { // jika tidak mengikuti tanggal payPeriod
                        if (leavePayPeriod.getOID() == 0) {
                            // start date
                            startDt.setDate(1);
                            leavePayPeriod.setStartDate(startDt);

                            // end date
                            Calendar gre = Calendar.getInstance();
                            gre.setTime(startDt);
                            int day = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                            Date dtEnd = new Date(startDt.getYear(), startDt.getMonth(), day);
                            leavePayPeriod.setEndDate(dtEnd);
                            System.out.println("===>> CREATE LEAVE PERIOD : TYPE FULL");
                            try {
                                PstLeavePeriod.insertExc(leavePayPeriod);
                            } catch (Exception e) {
                            }
                        }
                        startDt.setMonth(k + 1);

                    } else { // jika mengikuti tanggal payPeriod
                        if (leavePayPeriod.getOID() == 0) {
                            leavePayPeriod.setStartDate(startDt);
                            leavePayPeriod.setEndDate(endDt);
                            System.out.println("===>> CREATE LEAVE PERIOD : TYPE HAFT");
                            try {
                                PstLeavePeriod.insertExc(leavePayPeriod);
                            } catch (Exception e) {
                            }
                        }
                    }
                }
                //
                for (int k = 0; k <= endDt.getMonth(); k++) {
                    LeavePeriod leavePayPeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(endDt);
                    if (SystemProperty.SYS_PROP_SCHEDULE_LEAVE_PERIOD == SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL) { // jika tidak mengikuti tanggal payPeriod
                        if (leavePayPeriod.getOID() == 0) {
                            // start date
                            startDt.setDate(1);
                            leavePayPeriod.setStartDate(endDt);

                            // end date
                            Calendar gre = Calendar.getInstance();
                            gre.setTime(endDt);
                            int day = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                            Date dtEnd = new Date(endDt.getYear(), endDt.getMonth(), day);
                            leavePayPeriod.setEndDate(dtEnd);
                            System.out.println("===>> CREATE LEAVE PERIOD : TYPE FULL");
                            try {
                                PstLeavePeriod.insertExc(leavePayPeriod);
                            } catch (Exception e) {
                            }
                        }
                        endDt.setMonth(k + 1);

                    } else { // jika mengikuti tanggal payPeriod
                        if (leavePayPeriod.getOID() == 0) {
                            leavePayPeriod.setStartDate(startDt);
                            leavePayPeriod.setEndDate(endDt);
                            System.out.println("===>> CREATE LEAVE PERIOD : TYPE HAFT");
                            try {
                                PstLeavePeriod.insertExc(leavePayPeriod);
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    /** gadnyana
     * untuk get tanggal paling besar di payPeriod
     * @return
     */
    public static Date getMaxDatePayPeriod() {
        DBResultSet dbrs = null;
        Date date = new Date();
        try {
            String sql = "SELECT MAX(" + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] +
                    ") AS MAX_DT FROM " + PstPayPeriod.TBL_HR_PAY_PERIOD;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                date = rs.getDate(1);
                break;
            }
        } catch (Exception e) {
            System.out.println("Err list payPeriod : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return date;
        }
    }

    /** gadnyana
     * untuk get tanggal paling besar di payPeriod
     * @return
     */
    public static Date getMinDatePayPeriod() {
        DBResultSet dbrs = null;
        Date date = new Date();
        try {
            String sql = "SELECT MIN(" + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] +
                    ") AS MAX_DT FROM " + PstPayPeriod.TBL_HR_PAY_PERIOD;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                date = rs.getDate(1);
                break;
            }
        } catch (Exception e) {
            System.out.println("Err list payPeriod : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return date;
        }
    }

    /**
     * This method used to list payPeriodID where selectDate between startDate and dueDate of payPeriod
     * @param selectedDate --> specify selectedDate
     */
    public static long getPayPeriodeIdBetween(Date selectedDate) {
        DBResultSet dbrs = null;
        long result = 0;
        String strDate = "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "\"";
        try {
            String sql = "SELECT " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] +
                    " FROM " + PstPayPeriod.TBL_HR_PAY_PERIOD +
                    " WHERE " + strDate + " BETWEEN " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] +
                    " AND " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = rs.getLong(PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID]);
                break;
            }
        } catch (Exception e) {
            System.out.println("Err list payPeriod : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    
    public static PayPeriod getPreviousPayPeriod(long currentPayPeriodId){
       PayPeriod currPer = null;
       try{
        currPer=PstPayPeriod.fetchExc(currentPayPeriodId);
       } catch (Exception exc){
           
       }
       String whereClause = " "+fieldNames[FLD_START_DATE]+" < \"" + com.dimata.util.Formater.formatDate(currPer.getStartDate(), "yyyy-MM-dd")+"\" ";
       
       Vector vct = PstPayPeriod.list(0,1,whereClause, " "+fieldNames[FLD_START_DATE]+" DESC ");
       if(vct!=null && vct.size()>0){
           PayPeriod lastPer = (PayPeriod) vct.get(0);
           return lastPer;
       }       
       return null;        
    }
    /**
     * mencari periode penggajian sebelumnya
     * @param currentPeriodId
     * @return 
     */
     public static PayPeriod getPreviousPeriod(long currentPeriodId){
       PayPeriod currPer = null;
       try{
        currPer=PstPayPeriod.fetchExc(currentPeriodId);
       } catch (Exception exc){
           
       }
       String whereClause = " "+fieldNames[FLD_START_DATE]+" < \"" + com.dimata.util.Formater.formatDate(currPer.getStartDate(), "yyyy-MM-dd")+"\" ";
       
       Vector vct = PstPayPeriod.list(0,1,whereClause, " "+fieldNames[FLD_START_DATE]+" DESC ");
       if(vct!=null && vct.size()>0){
           PayPeriod lastPer = (PayPeriod) vct.get(0);
           return lastPer;
       }       
       return null;        
    }
    
    



    public static void main(String args[]) {
        //long result = getPayPeriodIdBySelectedDate(new Date(104,5,31));
        //long result = getPayPeriodIdJustBefore(504404240112808778L);
        //System.out.println("result : " + result);
        
        PayPeriod objPayPeriod = new PayPeriod();
        /**
        try{
            objPayPeriod = PstPayPeriod.fetchExc(504404333500583958L);
        }catch(Exception e){
            System.out.println("Err"+e.toString());
        }*/
        
        objPayPeriod = getPreviousPayPeriod(504404361671495942L);
        System.out.println("test"+objPayPeriod.getStartDate());
        System.out.println("test"+objPayPeriod.getPayProcBy());
        System.out.println("test"+objPayPeriod.getPayProcByClose());
    }
    
    //update by priska 2015-09-03
    /**
     * 
     * @param selectedDate
     * @return 
     * @desc: untuk mencari payPeriode sekarang
     */
    public static String getPayPeriodBySelectedPeriod(long startPeriodeId, long endperiodeId ) {        
        String oidPayPeriod = "";
        DBResultSet dbrs = null;
    
        PayPeriod startPayPeriode = new PayPeriod();
        try{
            startPayPeriode  = fetchExc(startPeriodeId);  
        }catch (Exception e) {
        }
        PayPeriod endPayPeriode = new PayPeriod();
        try{
            endPayPeriode  = fetchExc(endperiodeId);  
        }catch (Exception e) {
        }
        
        
        
        String strDate = "\"" + Formater.formatDate(startPayPeriode.getStartDate(), "yyyy-MM-dd") + "\"";
        String endDate = "\"" + Formater.formatDate(endPayPeriode.getEndDate(), "yyyy-MM-dd") + "\"";
        
        try {
            String sql = "SELECT * " + /*  PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] + */
                    " FROM " + TBL_HR_PAY_PERIOD +
                    " WHERE ( " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] +
                    " BETWEEN " + strDate +   
                    " AND " + endDate+" ) AND (" + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] +
                    " BETWEEN " + strDate +   
                    " AND " + endDate+")" ;
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
               PayPeriod payPeriod = new PayPeriod();
                resultToObject(rs, payPeriod);
                oidPayPeriod  = ""+oidPayPeriod + payPeriod.getOID() +" ,";
                
                //result = rs.getLong(1);
            }
        } catch (Exception e) {
            return oidPayPeriod;
        } finally {
            DBResultSet.close(dbrs);
            return oidPayPeriod;
        }
    }
    
     public static Vector getPayPeriodBySelectedPeriodV(long startPeriodeId, long endperiodeId ) {        
        Vector oidPayPeriod = new Vector();
        DBResultSet dbrs = null;
    
        PayPeriod startPayPeriode = new PayPeriod();
        try{
            startPayPeriode  = fetchExc(startPeriodeId);  
        }catch (Exception e) {
        }
        PayPeriod endPayPeriode = new PayPeriod();
        try{
            endPayPeriode  = fetchExc(endperiodeId);  
        }catch (Exception e) {
        }
        
        
        
        String strDate = "\"" + Formater.formatDate(startPayPeriode.getStartDate(), "yyyy-MM-dd") + "\"";
        String endDate = "\"" + Formater.formatDate(endPayPeriode.getEndDate(), "yyyy-MM-dd") + "\"";
        
        try {
            String sql = "SELECT * " + /*  PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID] + */
                    " FROM " + TBL_HR_PAY_PERIOD +
                    " WHERE ( " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] +
                    " BETWEEN " + strDate +   
                    " AND " + endDate+" ) AND (" + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] +
                    " BETWEEN " + strDate +   
                    " AND " + endDate+")" ;
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
               PayPeriod payPeriod = new PayPeriod();
                resultToObject(rs, payPeriod);
                oidPayPeriod.add(payPeriod.getOID());
                
            }
        } catch (Exception e) {
            return oidPayPeriod;
        } finally {
            DBResultSet.close(dbrs);
            return oidPayPeriod;
        }
    }
    
    //menambahkan pencarian date from by priska 2015-01-05
     public static Date getfromdate(long periodid) {
        DBResultSet dbrs = null;
        Date date = new Date();
        try {
            String sql = "SELECT " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] +
                    " FROM " + PstPayPeriod.TBL_HR_PAY_PERIOD + " WHERE PERIOD_ID = " + periodid;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                date = rs.getDate(1);
                break;
            }
        } catch (Exception e) {
            System.out.println("Err list period : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return date;
        }
    }
    
     //menambahkan pencarian date end by priska 2015-01-05
     public static Date getenddate(long periodid) {
        DBResultSet dbrs = null;
        Date date = new Date();
        try {
            String sql = "SELECT " + PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE] +
                    " FROM " + PstPayPeriod.TBL_HR_PAY_PERIOD + " WHERE PERIOD_ID = " + periodid;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                date = rs.getDate(1);
                break;
            }
        } catch (Exception e) {
            System.out.println("Err list period : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return date;
        }
    }
}
