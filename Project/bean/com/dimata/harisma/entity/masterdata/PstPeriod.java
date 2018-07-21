/* Created on 	:  [date] [time] AM/PM
 *
 * @author  	: karya
 * @version  	: 01
 */

/*******************************************************************
 * Class Description 	: [project description ... ]
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.entity.masterdata;

/* package java */

import java.util.Date;
import java.util.Vector;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.ResultSet;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.system.entity.system.*;
import java.util.Hashtable;


public class PstPeriod extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_PERIOD = "hr_period";//"HR_PERIOD";

    public static final int FLD_PERIOD_ID = 0;
    public static final int FLD_PERIOD = 1;
    public static final int FLD_START_DATE = 2;
    public static final int FLD_END_DATE = 3;
    
    public static final int FLD_WORK_DAYS = 4;
    public static final int FLD_PAY_SLIP_DATE = 5;
    public static final int FLD_MIN_REG_WAGE = 6;
    
    public static final int FLD_PAY_PROCESS = 7;
    public static final int FLD_PAY_PROC_BY = 8;
    public static final int FLD_PAY_PROC_DATE = 9;
    
    public static final int FLD_TAX_IS_PAID = 10;
    public static final int FLD_PAY_PROCESS_CLOSE = 11;
    public static final int FLD_PAY_PROC_BY_CLOSE = 12;
    public static final int FLD_PAY_PROC_DATE_CLOSE = 13;

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
        
        "TAX_IS_PAID",
        "PAY_PROCESS_CLOSE",
        "PAY_PROC_BY_CLOSE",
        "PAY_PROC_DATE_CLOSE"
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
        
        TYPE_INT,
        TYPE_STRING,
        TYPE_DATE
        
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

    public PstPeriod() {
    }

    public PstPeriod(int i) throws DBException {
        super(new PstPeriod());
    }

    public PstPeriod(String sOid) throws DBException {
        super(new PstPeriod(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public PstPeriod(long lOid) throws DBException {
        super(new PstPeriod(0));
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
        return TBL_HR_PERIOD;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPeriod().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        Period period = fetchExc(ent.getOID());
        ent = (Entity) period;
        return period.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((Period) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((Period) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static Period fetchExc(long oid) throws DBException {
        try {
            Period period = new Period();
            PstPeriod pstPeriod = new PstPeriod(oid);
            period.setOID(oid);

            period.setPeriod(pstPeriod.getString(FLD_PERIOD));
            period.setStartDate(pstPeriod.getDate(FLD_START_DATE));
            period.setEndDate(pstPeriod.getDate(FLD_END_DATE));
            
            period.setWorkDays(pstPeriod.getInt(FLD_WORK_DAYS));
            period.setPaySlipDate(pstPeriod.getDate(FLD_PAY_SLIP_DATE));
            period.setMinRegWage(pstPeriod.getdouble(FLD_MIN_REG_WAGE));
            
            period.setPayProcess(pstPeriod.getInt(FLD_PAY_PROCESS));
            period.setPayProcBy(pstPeriod.getString(FLD_PAY_PROC_BY));
            period.setPayProcDate(pstPeriod.getDate(FLD_PAY_PROC_DATE));
            period.setTaxIsPaid(pstPeriod.getInt(FLD_TAX_IS_PAID));
            
            period.setPayProcessClose(pstPeriod.getInt(FLD_PAY_PROCESS_CLOSE));
            period.setPayProcByClose(pstPeriod.getString(FLD_PAY_PROC_BY_CLOSE));
            period.setPayProcDateClose(pstPeriod.getDate(FLD_PAY_PROC_DATE_CLOSE));
            
            return period;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPeriod(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(Period period) throws DBException {
        try {
            PstPeriod pstPeriod = new PstPeriod(0);

            pstPeriod.setString(FLD_PERIOD, period.getPeriod());
            pstPeriod.setDate(FLD_START_DATE, period.getStartDate());
            pstPeriod.setDate(FLD_END_DATE, period.getEndDate());
            
            pstPeriod.setInt(FLD_WORK_DAYS, period.getWorkDays());
            pstPeriod.setDate(FLD_PAY_SLIP_DATE, period.getPaySlipDate());
            pstPeriod.setDouble(FLD_MIN_REG_WAGE, period.getMinRegWage());
            
            pstPeriod.setInt(FLD_PAY_PROCESS, period.getPayProcess());
            pstPeriod.setString(FLD_PAY_PROC_BY, period.getPayProcBy());
            pstPeriod.setDate(FLD_PAY_PROC_DATE, period.getPayProcDate());
            pstPeriod.setInt(FLD_TAX_IS_PAID, period.getTaxIsPaid());
            
            pstPeriod.setInt(FLD_PAY_PROCESS_CLOSE, period.getPayProcessClose());
            pstPeriod.setString(FLD_PAY_PROC_BY_CLOSE, period.getPayProcByClose());
            pstPeriod.setDate(FLD_PAY_PROC_DATE_CLOSE, period.getPayProcDateClose());

            pstPeriod.insert();
            period.setOID(pstPeriod.getlong(FLD_PERIOD_ID));
            sendPeriodToLeavePeriod(period.getStartDate(), period.getEndDate());

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPeriod(0), DBException.UNKNOWN);
        }
        return period.getOID();
    }

    public static long updateExc(Period period) throws DBException {
        try {
            if (period.getOID() != 0) {
                PstPeriod pstPeriod = new PstPeriod(period.getOID());

                pstPeriod.setString(FLD_PERIOD, period.getPeriod());
                pstPeriod.setDate(FLD_START_DATE, period.getStartDate());
                pstPeriod.setDate(FLD_END_DATE, period.getEndDate());
                
                pstPeriod.setInt(FLD_WORK_DAYS, period.getWorkDays());
                pstPeriod.setDate(FLD_PAY_SLIP_DATE, period.getPaySlipDate());
                pstPeriod.setDouble(FLD_MIN_REG_WAGE, period.getMinRegWage());
                pstPeriod.setInt(FLD_PAY_PROCESS, period.getPayProcess());
                pstPeriod.setString(FLD_PAY_PROC_BY, period.getPayProcBy());
                pstPeriod.setDate(FLD_PAY_PROC_DATE, period.getPayProcDate());
                pstPeriod.setInt(FLD_TAX_IS_PAID, period.getTaxIsPaid());
                
                pstPeriod.setInt(FLD_PAY_PROCESS_CLOSE, period.getPayProcessClose());
                pstPeriod.setString(FLD_PAY_PROC_BY_CLOSE, period.getPayProcByClose());
                pstPeriod.setDate(FLD_PAY_PROC_DATE_CLOSE, period.getPayProcDateClose());
                
                pstPeriod.update();
                
                sendPeriodToLeavePeriod(period.getStartDate(), period.getEndDate());
                return period.getOID();


            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPeriod(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPeriod pstPeriod = new PstPeriod(oid);
            pstPeriod.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPeriod(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_PERIOD;
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
                Period period = new Period();
                resultToObject(rs, period);
                lists.add(period);
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
    
    
    /**
     * Create by satrya 2014-02-03
     * untuk list hashtable period
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
     public static Hashtable hashlistTblPeriod(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashtablePeriod = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_PERIOD;
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
                Period period = new Period();
                resultToObject(rs, period);
                hashtablePeriod.put(rs.getLong(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]), period);
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
     
     
    
/**
 * create by satrya 20130216
 * @param startDate
 * @param endDate
 * @return 
 */
      public static Vector getListStartEndDatePeriod(Date startDate, Date endDate) {
        Vector list = new Vector();
        DBResultSet dbrs = null;
       if(startDate!=null && endDate!=null){
        try {
            String sql = " SELECT * FROM "+PstPeriod.TBL_HR_PERIOD
               /*+ " WHERE ("+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]
               + " BETWEEN \""+Formater.formatDate(startDate, "yyyy-MM-dd")+"\" AND \""+Formater.formatDate(endDate, "yyyy-MM-dd")+"\")"
               + " OR ("+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE]
               + " BETWEEN  \""+Formater.formatDate(startDate, "yyyy-MM-dd")+"\" AND \""+Formater.formatDate(endDate, "yyyy-MM-dd")+"\")"; */
               
           //update by satrya 2013-04-28
           + " WHERE \""+Formater.formatDate(endDate, "yyyy-MM-dd")+"\" >= " +PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + "  AND  " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + ">= \""+Formater.formatDate(startDate, "yyyy-MM-dd")+"\"";
                    
                    //+ " WHERE \""+Formater.formatDate(endDate, "yyyy-MM-dd")+"\" > " +PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + "  AND  " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + "> \""+Formater.formatDate(startDate, "yyyy-MM-dd")+"\"";
            //menambahkan >= tgl 20130524
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while (rs.next()) {
                 Period period = new Period();
                resultToObject(rs, period);
                list.add(period);
            }
            rs.close();
            return list;

        } catch (Exception e) {
            System.out.println("Exception period"+e);
        } finally {
            DBResultSet.close(dbrs);
        }
      }
        return list;
    }
      
      /**
       * mencari oid period
       * create by satrya 2014-05-27
       * @param startDate
       * @param endDate
       * @return 
       */
      public static String oidPeriodByStartAndDate(Date startDate, Date endDate) {
        String list = "";
        DBResultSet dbrs = null;
       if(startDate!=null && endDate!=null){
        try {
            String sql = " SELECT "+PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]+" FROM "+PstPeriod.TBL_HR_PERIOD
               
           //update by satrya 2013-04-28
           + " WHERE \""+Formater.formatDate(endDate, "yyyy-MM-dd")+"\" >= " +PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + "  AND  " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + ">= \""+Formater.formatDate(startDate, "yyyy-MM-dd")+"\"";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while (rs.next()) {
                 list = list + rs.getLong(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID])+",";
            }
            if(list!=null && list.length()>0){
                list = list.substring(0, list.length()-1);
            }
            rs.close();
            return list;

        } catch (Exception e) {
            System.out.println("Exception period"+e);
        } finally {
            DBResultSet.close(dbrs);
        }
      }
        return list;
    }
      
      
      
      /**
       * Create by satrya 2014-02-08
       * untuk membuat list table periode
       * @param startDate
       * @param endDate
       * @return 
       */
       public static HashTblPeriod getTblListStartEndDatePeriod(Date startDate, Date endDate) {
        Vector list = new Vector();
        DBResultSet dbrs = null;
        HashTblPeriod hashTblPeriod = new HashTblPeriod();
       if(startDate!=null && endDate!=null){
            if (startDate.getTime() > endDate.getTime()) {
                Date tempFromDate = startDate;
                Date tempToDate = endDate;
                startDate = tempToDate;
                endDate = tempFromDate;
            }
         
        try {
            String sql = " SELECT * FROM "+PstPeriod.TBL_HR_PERIOD
               /*+ " WHERE ("+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]
               + " BETWEEN \""+Formater.formatDate(startDate, "yyyy-MM-dd")+"\" AND \""+Formater.formatDate(endDate, "yyyy-MM-dd")+"\")"
               + " OR ("+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE]
               + " BETWEEN  \""+Formater.formatDate(startDate, "yyyy-MM-dd")+"\" AND \""+Formater.formatDate(endDate, "yyyy-MM-dd")+"\")"; */
               
           //update by satrya 2013-04-28
           + " WHERE \""+Formater.formatDate(endDate, "yyyy-MM-dd")+"\" >= " +PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + "  AND  " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + ">= \""+Formater.formatDate(startDate, "yyyy-MM-dd")+"\"";
            //menambahkan >= tgl 20130524
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while (rs.next()) {
                 Period period = new Period();
                resultToObject(rs, period);
                 hashTblPeriod.addPeriod(DBHandler.convertDate(rs.getDate(fieldNames[FLD_START_DATE])), DBHandler.convertDate(rs.getDate(fieldNames[FLD_END_DATE])),period);
            }
            rs.close();
            return hashTblPeriod;

        } catch (Exception e) {
            System.out.println("Exception period"+e);
        } finally {
            DBResultSet.close(dbrs);
        }
      }
        return hashTblPeriod;
    }

    
    private static void resultToObject(ResultSet rs, Period period) {
        try {
            period.setOID(rs.getLong(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]));
            period.setPeriod(rs.getString(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]));
            period.setStartDate(rs.getDate(PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]));
            period.setEndDate(rs.getDate(PstPeriod.fieldNames[PstPeriod.FLD_END_DATE]));
            
            period.setWorkDays(rs.getInt(PstPeriod.fieldNames[PstPeriod.FLD_WORK_DAYS]));
            period.setPaySlipDate(rs.getDate(PstPeriod.fieldNames[PstPeriod.FLD_PAY_SLIP_DATE]));
            period.setMinRegWage(rs.getDouble(PstPeriod.fieldNames[PstPeriod.FLD_MIN_REG_WAGE]));
            period.setPayProcess(rs.getInt(PstPeriod.fieldNames[PstPeriod.FLD_PAY_PROCESS]));
            period.setPayProcBy(rs.getString(PstPeriod.fieldNames[PstPeriod.FLD_PAY_PROC_BY]));
            period.setPayProcDate(rs.getDate(PstPeriod.fieldNames[PstPeriod.FLD_PAY_PROC_DATE]));
            period.setTaxIsPaid(rs.getInt(PstPeriod.fieldNames[PstPeriod.FLD_TAX_IS_PAID]));
            
            period.setPayProcessClose(rs.getInt(PstPeriod.fieldNames[PstPeriod.FLD_PAY_PROCESS_CLOSE]));
            period.setPayProcByClose(rs.getString(PstPeriod.fieldNames[PstPeriod.FLD_PAY_PROC_BY_CLOSE]));
            period.setPayProcDateClose(rs.getDate(PstPeriod.fieldNames[PstPeriod.FLD_PAY_PROC_DATE_CLOSE]));

        } catch (Exception e) {
            System.out.println("Period result:"+ e);
        }
    }

    public static boolean checkOID(long periodId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_PERIOD + " WHERE " +
                    PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] + " = " + periodId;

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
            String sql = "SELECT COUNT(" + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] + ") FROM " + TBL_HR_PERIOD;
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
                    Period period = (Period) list.get(ls);
                    if (oid == period.getOID())
                        found = true;
                }
            }
        }
        if ((start >= size) && (size > 0))
            start = start - recordToGet;

        return start;
    }


    public static boolean checkMaster(long oid) {
        if (PstEmpSchedule.checkPeriode(oid))
            return true;
        else
            return false;
    }

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
     * get OID of period object that wrap selectedDate
     * @param selectedDate
     * @return long periodId
     * @created by Edhy
     */
    public static long getPeriodIdBySelectedDate(Date selectedDate) {
        
        long result = 0;
        DBResultSet dbrs = null;
        String strDate = "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "\"";
        try {
            String sql = "SELECT " + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] +
                    " FROM " + TBL_HR_PERIOD +
                    " WHERE " + strDate +
                    " BETWEEN " + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] +   
                    " AND " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE];
            
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
     * create by devin 2014-04-09
     * @param startDate
     * @param employeeId
     * @return 
     */
    public static long cariPeriod(Date startDate,long employeeId){
    long result=0;
     DBResultSet dbrs=null;
     String datee = Formater.formatDate(startDate, "yyyy-MM-dd");
     try{
         String sql="SELECT " + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] + " FROM " + PstPeriod.TBL_HR_PERIOD + " WHERE \"" +
                datee + "\" >= " + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + " AND " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] +
                 " >= \"" + datee + "\"" ;
         dbrs=DBHandler.execQueryResult(sql);
         ResultSet rs = dbrs.getResultSet();
         while(rs.next()){
             result=rs.getLong(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]);
         }
     }catch(Exception exc){
         
     }finally{
         DBResultSet.close(dbrs);
         return result;
     }
}
     //update by satrya 2012-09-03
    /**
     * 
     * @param selectedDate 
     * @return 
     * @descrip: untuk mencari periode sebelumnya
     */
    public static Period getPrevPeriodBySelectedDate(Date selectedDate) {        
        Period result = null;
        DBResultSet dbrs = null;
        try {
            Period resultCurrPeriod = getPeriodBySelectedDate(selectedDate);
            long lCurrPeriod = resultCurrPeriod.getOID();
            String sql = "SELECT * FROM " +TBL_HR_PERIOD + "  p WHERE p."
                    +PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]
                    +" < (SELECT p1."+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]
                    +" FROM " + TBL_HR_PERIOD + " p1 WHERE p1."+PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]
                    +" = " + lCurrPeriod +")" 
                    +" ORDER BY p."+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + " DESC LIMIT 0,1 ";
    
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                 result = new Period();
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
     * @desc: untuk mencari periode sekarang
     */
    public static Period getPeriodBySelectedDate(Date selectedDate) {        
        Period result = null;
        DBResultSet dbrs = null;
    
        String strDate = "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "\"";
        try {
            String sql = "SELECT * " + /*  PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] + */
                    " FROM " + TBL_HR_PERIOD +
                    " WHERE " + strDate +
                    " BETWEEN " + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] +   
                    " AND " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE];
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = new Period();
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
     * @desc : untuk mencari periode selanjutnya
     */
    public static Period getNextPeriodBySelectedDate(Date selectedDate) {        
        Period result = null;
        DBResultSet dbrs = null;
        try {
            Period resultCurrPeriod = getPeriodBySelectedDate(selectedDate);
            long lCurrPeriod = resultCurrPeriod.getOID();
             String sql = "SELECT * FROM " +TBL_HR_PERIOD + "  p WHERE p."
                    +PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]
                    +" > (SELECT p1."+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]
                    +" FROM " + TBL_HR_PERIOD + " p1 WHERE p1."+PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]
                    +" = " + lCurrPeriod +")" 
                    +" ORDER BY p."+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + " ASC LIMIT 0,1 ";
                   
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                 result = new Period();
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
     * create by priska 2014-12-07
     * @param startDate
     * @return 
     */
    public static long cariPeriodIdnya(Date startDate){
    long result=0;
     DBResultSet dbrs=null;
     String datee = Formater.formatDate(startDate, "yyyy-MM-dd");
     try{
         String sql="SELECT " + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] 
                 + " FROM " + PstPeriod.TBL_HR_PERIOD + " WHERE \"" +
                datee + "\" BETWEEN " + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + " AND " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] ;
         dbrs=DBHandler.execQueryResult(sql);
         ResultSet rs = dbrs.getResultSet();
         while(rs.next()){
             result=rs.getLong(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]);
         }
     }catch(Exception exc){
         
     }finally{
         DBResultSet.close(dbrs);
         return result;
     }
}
    
    /**
     * get OID of period object that wrap selectedDate
     * @param selectedDate
     * @return
     * @created by Yunny
     */
    public static long getPeriodIdBySelectedDateString(String selectedDate) {
        long result = 0;
        DBResultSet dbrs = null;
        String strDate = "\"" + selectedDate + "\"";
        try {
            String sql = "SELECT " + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] +
                    " FROM " + TBL_HR_PERIOD +
                    " WHERE " + strDate +
                    " BETWEEN " + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] +   
                    " AND " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE];
            //System.out.println("\tgetPeriodIdBySelectedDate : "+sql);
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
     * this method used to get periodId just before current period
     * @param currPeriodId
     * @return
     */
    public static long getPeriodIdJustBefore(long currPeriodId) {
        long result = 0;
        if (currPeriodId != 0) {
            String orderBy = PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " DESC";
            Vector vectLastPeriod = PstPeriod.list(0, 0, "", orderBy);
            if (vectLastPeriod != null && vectLastPeriod.size() > 1) {
                for (int i = 0; i < vectLastPeriod.size(); i++) {
                    Period per = (Period) vectLastPeriod.get(i);
                    if (currPeriodId == per.getOID() && i < vectLastPeriod.size() - 1) {
                        per = (Period) vectLastPeriod.get(i + 1);
                        return per.getOID();
                    }
                }
            }

            if (vectLastPeriod.size() == 1) {
                Period per = (Period) vectLastPeriod.get(0);
                if (per.getOID() != currPeriodId) {
                    return per.getOID();
                }
            }
        }
        return result;
    }

    /** gadnyana
     * untuk pembuatan leave period
     * @param startDt
     * @param endDt
     */
    public static void sendPeriodToLeavePeriod(Date startPeriodDt, Date endDt) {
        try {
            Date startDt = (Date)startPeriodDt.clone();
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDt);
            int month1st = startDt.getMonth();
            int month2st = endDt.getMonth();
            int monthDiff = month2st - month1st;

            if ((monthDiff > -1) && (startDt.getYear()==endDt.getYear())) { // true
                if (monthDiff == 0) { // jika bulan hanya 1
                    LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(startDt);
                    if (leavePeriod.getOID() == 0) {
                        // start date
                        startDt.setDate(1);
                        leavePeriod.setStartDate(startDt);

                        // end date
                        Calendar gre = Calendar.getInstance();
                        gre.setTime(startDt);
                        int day = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                        Date dtEnd = new Date(startDt.getYear(), startDt.getMonth(), day);
                        leavePeriod.setEndDate(dtEnd);
                        System.out.println("===>> CREATE LEAVE PERIOD : TYPE FULL");
                        try {
                            PstLeavePeriod.insertExc(leavePeriod);
                        } catch (Exception e) {
                            System.out.println("Exc insert PstLeavePeriod"+e);
                        }
                    }
                } else { // jika bulan nya lebih dari 1
                    for (int k = startDt.getMonth(); k <= endDt.getMonth(); k++) {
                        LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(startDt);
                        if (SystemProperty.SYS_PROP_SCHEDULE_LEAVE_PERIOD == SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL) { // jika tidak mengikuti tanggal period
                            if (leavePeriod.getOID() == 0) {
                                // start date
                                startDt.setDate(1);
                                leavePeriod.setStartDate(startDt);

                                // end date
                                Calendar gre = Calendar.getInstance();
                                gre.setTime(startDt);
                                int day = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                                Date dtEnd = new Date(startDt.getYear(), startDt.getMonth(), day);
                                leavePeriod.setEndDate(dtEnd);
                                System.out.println("===>> CREATE LEAVE PERIOD : TYPE FULL");
                                try {
                                    PstLeavePeriod.insertExc(leavePeriod);
                                } catch (Exception e) {
                                }
                            }
                            startDt.setMonth(k + 1);

                        } else { // jika mengikuti tanggal period
                            if (leavePeriod.getOID() == 0) {
                                leavePeriod.setStartDate(startDt);
                                leavePeriod.setEndDate(endDt);
                                System.out.println("===>> CREATE LEAVE PERIOD : TYPE HAFT");
                                try {
                                    PstLeavePeriod.insertExc(leavePeriod);
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
            }else{ // jika start date dan end date beda tahun
                for (int k = startDt.getMonth(); k <= 12; k++) {
                    LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(startDt);
                    if (SystemProperty.SYS_PROP_SCHEDULE_LEAVE_PERIOD == SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL) { // jika tidak mengikuti tanggal period
                        if (leavePeriod.getOID() == 0) {
                            // start date
                            startDt.setDate(1);
                            leavePeriod.setStartDate(startDt);

                            // end date
                            Calendar gre = Calendar.getInstance();
                            gre.setTime(startDt);
                            int day = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                            Date dtEnd = new Date(startDt.getYear(), startDt.getMonth(), day);
                            leavePeriod.setEndDate(dtEnd);
                            System.out.println("===>> CREATE LEAVE PERIOD : TYPE FULL");
                            try {
                                PstLeavePeriod.insertExc(leavePeriod);
                            } catch (Exception e) {
                            }
                        }
                        startDt.setMonth(k + 1);

                    } else { // jika mengikuti tanggal period
                        if (leavePeriod.getOID() == 0) {
                            leavePeriod.setStartDate(startDt);
                            leavePeriod.setEndDate(endDt);
                            System.out.println("===>> CREATE LEAVE PERIOD : TYPE HAFT");
                            try {
                                PstLeavePeriod.insertExc(leavePeriod);
                            } catch (Exception e) {
                            }
                        }
                    }
                }
                //
                for (int k = 0; k <= endDt.getMonth(); k++) {
                    LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(endDt);
                    if (SystemProperty.SYS_PROP_SCHEDULE_LEAVE_PERIOD == SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL) { // jika tidak mengikuti tanggal period
                        if (leavePeriod.getOID() == 0) {
                            // start date
                            startDt.setDate(1);
                            leavePeriod.setStartDate(endDt);

                            // end date
                            Calendar gre = Calendar.getInstance();
                            gre.setTime(endDt);
                            int day = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                            Date dtEnd = new Date(endDt.getYear(), endDt.getMonth(), day);
                            leavePeriod.setEndDate(dtEnd);
                            System.out.println("===>> CREATE LEAVE PERIOD : TYPE FULL");
                            try {
                                PstLeavePeriod.insertExc(leavePeriod);
                            } catch (Exception e) {
                            }
                        }
                        endDt.setMonth(k + 1);

                    } else { // jika mengikuti tanggal period
                        if (leavePeriod.getOID() == 0) {
                            leavePeriod.setStartDate(startDt);
                            leavePeriod.setEndDate(endDt);
                            System.out.println("===>> CREATE LEAVE PERIOD : TYPE HAFT");
                            try {
                                PstLeavePeriod.insertExc(leavePeriod);
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
     * untuk get tanggal paling besar di period
     * @return
     */
    public static Date getMaxDatePeriod() {
        DBResultSet dbrs = null;
        Date date = new Date();
        try {
            String sql = "SELECT MAX(" + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] +
                    ") AS MAX_DT FROM " + PstPeriod.TBL_HR_PERIOD;
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

    /** gadnyana
     * untuk get tanggal paling besar di period
     * @return
     */
    public static Date getMinDatePeriod() {
        DBResultSet dbrs = null;
        Date date = new Date();
        try {
            String sql = "SELECT MIN(" + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] +
                    ") AS MAX_DT FROM " + PstPeriod.TBL_HR_PERIOD;
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

    /**
     * This method used to list periodID where selectDate between startDate and dueDate of period
     * @param selectedDate --> specify selectedDate
     */
    public static long getPeriodeIdBetween(Date selectedDate) {
        DBResultSet dbrs = null;
        long result = 0;
        String strDate = "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "\"";
        try {
            String sql = "SELECT " + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] +
                    " FROM " + PstPeriod.TBL_HR_PERIOD +
                    " WHERE " + strDate + " BETWEEN " + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] +
                    " AND " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = rs.getLong(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]);
                break;
            }
        } catch (Exception e) {
            System.out.println("Err list period : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    
    public static Period getPreviousPeriod(long currentPeriodId){
       Period currPer = null;
       try{
        currPer=PstPeriod.fetchExc(currentPeriodId);
       } catch (Exception exc){
           
       }
       String whereClause = " "+fieldNames[FLD_START_DATE]+" < \"" + com.dimata.util.Formater.formatDate(currPer.getStartDate(), "yyyy-MM-dd")+"\" ";
       
       Vector vct = PstPeriod.list(0,1,whereClause, " "+fieldNames[FLD_START_DATE]+" DESC ");
       if(vct!=null && vct.size()>0){
           Period lastPer = (Period) vct.get(0);
           return lastPer;
       }       
       return null;        
    }
    
    


    public static void main(String args[]) {
        //long result = getPeriodIdBySelectedDate(new Date(104,5,31));
        //long result = getPeriodIdJustBefore(504404240112808778L);
        //System.out.println("result : " + result);
        
        Period objPeriod = new Period();
        /**
        try{
            objPeriod = PstPeriod.fetchExc(504404333500583958L);
        }catch(Exception e){
            System.out.println("Err"+e.toString());
        }*/
        
        objPeriod = getPreviousPeriod(504404361671495942L);
        System.out.println("test"+objPeriod.getStartDate());
        System.out.println("test"+objPeriod.getPayProcBy());
        System.out.println("test"+objPeriod.getPayProcByClose());
    }
}
