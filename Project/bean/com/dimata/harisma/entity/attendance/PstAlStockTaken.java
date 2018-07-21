/*
 * PstAlStockTaken.java
 *
 * Created on December 22, 2004, 4:47 PM
 */
package com.dimata.harisma.entity.attendance;

// package core java
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

// package qdep
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

// package harisma
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.util.DateCalc;
import com.dimata.util.Formater;

/**
 *
 * @author  gedhy
 */
public class PstAlStockTaken extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_AL_STOCK_TAKEN = "hr_al_stock_taken";// "HR_AL_STOCK_TAKEN";
    public static final int FLD_AL_STOCK_TAKEN_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final int FLD_AL_STOCK_ID = 2;
    public static final int FLD_TAKEN_DATE = 3;
    public static final int FLD_TAKEN_QTY = 4;
    public static final int FLD_PAID_DATE = 5;
    public static final int FLD_TAKEN_FROM_STATUS = 6;
    public static final int FLD_LEAVE_APPLICATION_ID = 7;
    public static final int FLD_TAKEN_FINNISH_DATE = 8;
    public static final String[] fieldNames = {
        "AL_STOCK_TAKEN_ID",
        "EMPLOYEE_ID",
        "AL_STOCK_ID",
        "TAKEN_DATE",
        "TAKEN_QTY",
        "PAID_DATE",
        "TAKEN_FROM_STATUS",
        "LEAVE_APPLICATION_ID",
        "TAKEN_FINNISH_DATE"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_INT,
        TYPE_LONG,
        TYPE_DATE
    };
    public static int TAKEN_FROM_STATUS_USER = 0;
    public static int TAKEN_FROM_STATUS_SYSTEM = 1;

    public PstAlStockTaken() {
    }

    public PstAlStockTaken(int i) throws DBException {
        super(new PstAlStockTaken());
    }

    public PstAlStockTaken(String sOid) throws DBException {
        super(new PstAlStockTaken(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstAlStockTaken(long lOid) throws DBException {
        super(new PstAlStockTaken(0));
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
        return TBL_HR_AL_STOCK_TAKEN;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstAlStockTaken().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        AlStockTaken alStockTaken = fetchExc(ent.getOID());
        ent = (Entity) alStockTaken;
        return alStockTaken.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((AlStockTaken) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((AlStockTaken) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static int deleteByLeaveAppId(long leaveAppOid) throws Exception {
        try {
            String where = " " + fieldNames[FLD_LEAVE_APPLICATION_ID] + " = " + leaveAppOid;
            PstAlStockTaken pst = new PstAlStockTaken(0);
            pst.deleteRecords(0, where);
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockTaken(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static AlStockTaken fetchExc(long oid) throws DBException {
        try {
            AlStockTaken alStockTaken = new AlStockTaken();
            PstAlStockTaken pstAlStockTaken = new PstAlStockTaken(oid);
            alStockTaken.setOID(oid);

            alStockTaken.setEmployeeId(pstAlStockTaken.getlong(FLD_EMPLOYEE_ID));
            alStockTaken.setAlStockId(pstAlStockTaken.getlong(FLD_AL_STOCK_ID));
            alStockTaken.setTakenDate(pstAlStockTaken.getDate(FLD_TAKEN_DATE));
            alStockTaken.setTakenQty(pstAlStockTaken.getfloat(FLD_TAKEN_QTY));
            alStockTaken.setPaidDate(pstAlStockTaken.getDate(FLD_PAID_DATE));
            alStockTaken.setTakenFromStatus(pstAlStockTaken.getInt(FLD_TAKEN_FROM_STATUS));
            alStockTaken.setLeaveApplicationId(pstAlStockTaken.getlong(FLD_LEAVE_APPLICATION_ID));
            alStockTaken.setTakenFinnishDate(pstAlStockTaken.getDate(FLD_TAKEN_FINNISH_DATE));

            return alStockTaken;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockTaken(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(AlStockTaken alStockTaken) throws DBException {
        try {
            PstAlStockTaken pstAlStockTaken = new PstAlStockTaken(0);

            pstAlStockTaken.setLong(FLD_EMPLOYEE_ID, alStockTaken.getEmployeeId());
            pstAlStockTaken.setLong(FLD_AL_STOCK_ID, alStockTaken.getAlStockId());
            pstAlStockTaken.setDate(FLD_TAKEN_DATE, alStockTaken.getTakenDate());
            pstAlStockTaken.setFloat(FLD_TAKEN_QTY, alStockTaken.getTakenQty());
            pstAlStockTaken.setDate(FLD_PAID_DATE, alStockTaken.getPaidDate());
            pstAlStockTaken.setInt(FLD_TAKEN_FROM_STATUS, alStockTaken.getTakenFromStatus());
            pstAlStockTaken.setLong(FLD_LEAVE_APPLICATION_ID, alStockTaken.getLeaveApplicationId());
            pstAlStockTaken.setDate(FLD_TAKEN_FINNISH_DATE, alStockTaken.getTakenFinnishDate());

            pstAlStockTaken.insert();
            alStockTaken.setOID(pstAlStockTaken.getlong(FLD_AL_STOCK_TAKEN_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockTaken(0), DBException.UNKNOWN);
        }
        return alStockTaken.getOID();
    }

    public static long updateExc(AlStockTaken alStockTaken) throws DBException {
        try {
            if (alStockTaken.getOID() != 0) {
                PstAlStockTaken pstAlStockTaken = new PstAlStockTaken(alStockTaken.getOID());

                pstAlStockTaken.setLong(FLD_EMPLOYEE_ID, alStockTaken.getEmployeeId());
                pstAlStockTaken.setLong(FLD_AL_STOCK_ID, alStockTaken.getAlStockId());
                pstAlStockTaken.setDate(FLD_TAKEN_DATE, alStockTaken.getTakenDate());
                pstAlStockTaken.setFloat(FLD_TAKEN_QTY, alStockTaken.getTakenQty());
                pstAlStockTaken.setDate(FLD_PAID_DATE, alStockTaken.getPaidDate());
                pstAlStockTaken.setInt(FLD_TAKEN_FROM_STATUS, alStockTaken.getTakenFromStatus());
                pstAlStockTaken.setLong(FLD_LEAVE_APPLICATION_ID, alStockTaken.getLeaveApplicationId());
                pstAlStockTaken.setDate(FLD_TAKEN_FINNISH_DATE, alStockTaken.getTakenFinnishDate());

                pstAlStockTaken.update();
                return alStockTaken.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockTaken(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstAlStockTaken pstAlStockTaken = new PstAlStockTaken(oid);
            pstAlStockTaken.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockTaken(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_AL_STOCK_TAKEN;
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
                AlStockTaken alStockTaken = new AlStockTaken();
                resultToObject(rs, alStockTaken);
                lists.add(alStockTaken);
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
     * menghitung AL qunatity ( dalam unit workday ( 8 jam kerja ) dari seorang karyawan pada rentang periode waktu tertentu
     * @param employeeId
     * @param startDate
     * @param endDate
     * @return 
     */
    public static float getAlQty(long employeeId, Date startDate, Date endDate) {
        float qty = 0.0f;
        String where = fieldNames[FLD_EMPLOYEE_ID] + "=\""+ employeeId + "\" AND ( (" + fieldNames[FLD_TAKEN_DATE] + " BETWEEN \""
                + Formater.formatDate(startDate, "yyyy-MM-dd ") + "00:00:00\" AND \""
                + Formater.formatDate(endDate, "yyyy-MM-dd ") + "23:59:59\") OR "
                + " (" + fieldNames[FLD_TAKEN_FINNISH_DATE] + " BETWEEN \""
                + Formater.formatDate(endDate, "yyyy-MM-dd ") + "00:00:00\" AND \""
                + Formater.formatDate(endDate, "yyyy-MM-dd ") + "23:59:59\") ) ";

        Vector list = list(0, 100, where, fieldNames[FLD_TAKEN_DATE]);
        if (list != null && list.size() > 0) {
            for (int idx = 0; idx < list.size(); idx++) {
                AlStockTaken alStockTaken = (AlStockTaken) list.get(idx);
                if ((DateCalc.dayDifference(alStockTaken.getTakenDate(), startDate) >= 0)
                        && (DateCalc.dayDifference(alStockTaken.getTakenFinnishDate(), endDate) <= 0)) {
                    // taken date and taken finish di dalam start and end date
                    if (alStockTaken.getTakenQty() == (float) Math.floor(alStockTaken.getTakenQty())) {
                        // quantity stock taken bulat ( artinya tidak ada pecahan 8 jam kerja
                        qty = qty + DateCalc.dayDifference(startDate, endDate)+1;
                    } else {
                        // quantity tidak bulat, artinya hari terakhir pecahan  8 jam;
                        qty = qty + DateCalc.dayDifference(startDate, endDate);
                        qty = qty + alStockTaken.getTakenQty() - (float) Math.floor(alStockTaken.getTakenQty());
                    }
                } else {
                    if ((DateCalc.dayDifference(alStockTaken.getTakenDate(), startDate) >= 0)
                            && (DateCalc.dayDifference(alStockTaken.getTakenFinnishDate(), endDate) > 0)) {
                        // taken date and taken finish di dalam start and end date
                        if (alStockTaken.getTakenQty() == (float) Math.floor(alStockTaken.getTakenQty())) {
                            // quantity stock taken bulat ( artinya tidak ada pecahan 8 jam kerja
                            qty = qty + DateCalc.dayDifference(startDate, alStockTaken.getTakenFinnishDate())+1;
                        } else {
                            // quantity tidak bulat, artinya hari terakhir pecahan  8 jam;
                            qty = qty + DateCalc.dayDifference(startDate, alStockTaken.getTakenFinnishDate());
                            qty = qty + alStockTaken.getTakenQty() - (float) Math.floor(alStockTaken.getTakenQty());
                        }
                    } else {
                        if ((DateCalc.dayDifference(alStockTaken.getTakenDate(), startDate) < 0)
                                && (DateCalc.dayDifference(alStockTaken.getTakenFinnishDate(), endDate) <= 0)) {
                            // taken date and taken finish di dalam start and end date
                            if (alStockTaken.getTakenQty() == (float) Math.floor(alStockTaken.getTakenQty())) {
                                // quantity stock taken bulat ( artinya tidak ada pecahan 8 jam kerja
                                qty = qty + DateCalc.dayDifference(alStockTaken.getTakenDate(), endDate)+1;
                            } else {
                                // quantity tidak bulat, artinya hari terakhir pecahan  8 jam;
                                qty = qty + DateCalc.dayDifference(alStockTaken.getTakenDate(), endDate);
                                qty = qty + alStockTaken.getTakenQty() - (float) Math.floor(alStockTaken.getTakenQty());
                            }
                        } else {
                            // startdate  and enddate  beyond takendate and takenfinishdate
                            qty = qty + alStockTaken.getTakenQty();                            
                        }
                    }
                }
            }
        }
        return qty;
    }


    public static void resultToObject(ResultSet rs, AlStockTaken alStockTaken) {
        try {
            alStockTaken.setOID(rs.getLong(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID]));
            alStockTaken.setEmployeeId(rs.getLong(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]));
            alStockTaken.setAlStockId(rs.getLong(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID]));
            alStockTaken.setTakenFromStatus(rs.getInt(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FROM_STATUS]));
            alStockTaken.setLeaveApplicationId(rs.getLong(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID]));
            alStockTaken.setTakenQty(rs.getFloat(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY]));

            alStockTaken.setTakenDate(
                    rs.getTime(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]) != null
                    ? PstEmpSchedule.convertDate(rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]),
                    rs.getTime(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]))
                    : rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]));
            alStockTaken.setPaidDate(
                    rs.getTime(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_PAID_DATE]) != null
                    ? PstEmpSchedule.convertDate(rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_PAID_DATE]),
                    rs.getTime(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_PAID_DATE]))
                    : rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_PAID_DATE]));
            alStockTaken.setTakenFinnishDate(
                    rs.getTime(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]) != null
                    ? PstEmpSchedule.convertDate(rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]),
                    rs.getTime(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]))
                    : rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]));

        } catch (Exception e) {
            System.out.println("EXCEPTION : " + e.toString());
        }
    }

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] + ") FROM " + TBL_HR_AL_STOCK_TAKEN;
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
    
     public static boolean getHaveTakenExecuteForm(Date dateFrom, long empId) {
        DBResultSet dbrs = null;
        boolean ada = false;
        try {
            String sql = "SELECT hast.* FROM `hr_al_stock_taken` hast INNER JOIN `hr_leave_application` hla ON hla.`LEAVE_APPLICATION_ID` = `hast`.`LEAVE_APPLICATION_ID`"; 
                   sql += "WHERE `hast`.`EMPLOYEE_ID` = "+empId+" AND ( \""+ Formater.formatDate(dateFrom, "yyyy-MM-dd HH:mm:ss") +"\" BETWEEN `hast`.`TAKEN_DATE` AND `hast`.`TAKEN_FINNISH_DATE`  )  AND `hla`.`DOC_STATUS` > 2 ";
            
          dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                ada = true;
            }

            rs.close();
            return ada;
        } catch (Exception e) {
            return ada;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    

    /* This method used to find current data */
    public static int findLimitStart(long oid, int recordToGet, String whereClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, order);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    AlStockTaken alStockTaken = (AlStockTaken) list.get(ls);
                    if (oid == alStockTaken.getOID()) {
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

    /**
     * @param employeeId
     *
     * @return
     * @created by Edhy
     */
    public static Vector getAlPayable(long employeeId) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        String stSQL = "SELECT " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID]
                + ", " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]
                + ", " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID]
                + ", " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]
                + ", " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY]
                + ", " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_PAID_DATE]
                + " FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN
                + " WHERE " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]
                + " = " + employeeId
                + " AND " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID]
                + " = 0"
                + " ORDER BY " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE];
        try {
//            System.out.println("SQL getAlPayable : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AlStockTaken objAlStockTaken = new AlStockTaken();
                objAlStockTaken.setOID(rs.getLong(1));
                objAlStockTaken.setEmployeeId(rs.getLong(2));
                objAlStockTaken.setAlStockId(rs.getInt(3));
                objAlStockTaken.setTakenDate(rs.getDate(4));
                objAlStockTaken.setTakenQty(rs.getFloat(5));
                objAlStockTaken.setPaidDate(rs.getDate(6));

                result.add(objAlStockTaken);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Exc when getAlPayable : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * Create by satrya 2013-12-23
     * keterangan: untuk mencari al taken per form leave
     * @param leaveApplicationId
     * @param employeeId
     * @return 
     */
    public static Vector getAlTaken(long leaveApplicationId,long employeeId) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        String stSQL = "SELECT * "
                + " FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN
                + " WHERE " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]
                + " = " + employeeId
                + " AND " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID]
                + " = "+leaveApplicationId
                + " ORDER BY " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE];
        try {

            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AlStockTaken objAlStockTaken = new AlStockTaken();
                resultToObject(rs, objAlStockTaken);
                result.add(objAlStockTaken);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Exc when getAlPayable : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    /**
     * Create by priska 2015-08-07
     * keterangan: untuk mencari al taken per form leave
     * @param leaveApplicationId
     * @param employeeId
     * @return 
     */
    public static Vector getAlTakenDateDiff_3(long leaveApplicationId,long employeeId) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        String stSQL = "SELECT * "
                + " FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN
                + " WHERE " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]
                + " = " + employeeId
                + " AND " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID]
                + " = "+leaveApplicationId
                + " ORDER BY " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE];
        try {

            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AlStockTaken objAlStockTaken = new AlStockTaken();
                resultToObject(rs, objAlStockTaken);
                
                Date startDate =  (rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]));
                Date endDate =  (rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]));
                if  (((((endDate.getTime()) - (startDate.getTime())) / (24L * 60L * 60L * 1000L)) ) > (2) ){
                    result.add(objAlStockTaken);
                }
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Exc when getAlPayable : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
     /**
     * Create by priska 2014-12-23
     * keterangan: untuk mencari al taken per form leave
     * @param leaveApplicationId
     * @param employeeId
     * @return 
//     */
//    public static Vector getAnnualLeave(Date dtfrom, Date dtto ) {
//        Vector result = new Vector(1, 1);
//        DBResultSet dbrs = null;
//         if (dtfrom != null && dtto != null) {
//            //update by satrya 2012-10-15
//            if (dtfrom.getTime() > dtto.getTime()) {
//                Date tempFromDate = dtfrom;
//                Date tempToDate = dtto;
//                dtfrom = tempToDate;
//                dtto = tempFromDate;
//            }
//         }
//        
//        String stSQL = "SELECT  `hast`."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]
//                            +", `hast`."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]
//                            +", `hast`."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]
//                            +" FROM " +PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN+ " hast"
//                            +" INNER JOIN `hr_leave_application` hla ON (`hla`.`LEAVE_APPLICATION_ID` = `hast`."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID]+") "
//                            +" WHERE hla.`DOC_STATUS` = '3' ";
//        if(dtfrom!=null && dtto!=null){
//                  stSQL = stSQL + " AND ((hast."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " BETWEEN \"" + Formater.formatDate(dtfrom, "yyyy-MM-dd") + " 00:00:01 \" AND \"" + Formater.formatDate(dtto, "yyyy-MM-dd") + " 23:59:59 \" ) ";
//                  stSQL = stSQL + " OR  ( hast."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " BETWEEN \"" + Formater.formatDate(dtfrom, "yyyy-MM-dd") + " 00:00:01 \" AND \"" + Formater.formatDate(dtto, "yyyy-MM-dd") + " 23:59:59 \" ) ";
//                  
//                  stSQL = stSQL + " OR  ( hast."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " = \"" + Formater.formatDate(dtfrom, "yyyy-MM-dd") + " 00:00:01 \" ) ";
//                  stSQL = stSQL + " OR  ( hast."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " = \"" + Formater.formatDate(dtto, "yyyy-MM-dd") + " 23:59:59 \" ) ";
//                  
//                  stSQL = stSQL + " OR  ( hast."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " = \"" + Formater.formatDate(dtfrom, "yyyy-MM-dd") + " 00:00:01 \" ) ";
//                  stSQL = stSQL + " OR  ( hast."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " = \"" + Formater.formatDate(dtto, "yyyy-MM-dd") + " 23:59:59 \" )) ";
//        }
//                  stSQL = stSQL + "ORDER BY `hast`."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] + " ASC";
//        try {
//
//            dbrs = DBHandler.execQueryResult(stSQL);
//            ResultSet rs = dbrs.getResultSet();
//            while (rs.next()) {
//               
//                
//                
//            }
//            rs.close();
//        } catch (Exception e) {
//            System.out.println("Exc when getAlPayable : " + e.toString());
//        } finally {
//            DBResultSet.close(dbrs);
//            return result;
//        }
//    }
    
    /**
     * @param alStockOid
    //     * @param employeeId
     * @param takenDate
     * @return
     */
    public static boolean existAlStockTaken(long alStockOid, long employeeId, Date takenDate) {
        boolean result = false;
        DBResultSet dbrs = null;
        String strTakenDate = "\"" + com.dimata.util.Formater.formatDate(takenDate, "yyyy-MM-dd") + "\"";
        String stSQL = "SELECT " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID]
                + " FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN
                + " WHERE " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]
                + " = " + employeeId
                + " AND " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID]
                + " = " + alStockOid
                + " AND " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]
                + " = " + strTakenDate;
        try {
//            System.out.println("SQL existAlStockTaken : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Exc when existAlStockTaken : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    ;    
    
    
    /**     
     * @param employeeId
     * @param takenDate
     * @return
     */    
    public static boolean existAlStockTaken(long employeeId, Date takenDate) {
        boolean result = false;
        DBResultSet dbrs = null;
        String strTakenDate = "\"" + com.dimata.util.Formater.formatDate(takenDate, "yyyy-MM-dd") + "\"";
        String stSQL = "SELECT " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID]
                + " FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN
                + " WHERE " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]
                + " = " + employeeId
                + " AND " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]
                + " = " + strTakenDate;
        try {
//            System.out.println("SQL existAlStockTaken : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Exc when existAlStockTaken : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    ;        
    
    
    /**
     * @param lDepartmentOid
     * @created by Edhy     
     */
    public void deleteAlStockTakenPerDepartment(long lDepartmentOid) {
        DBResultSet dbrs = null;
        String stSQL = " SELECT AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID]
                + " FROM " + TBL_HR_AL_STOCK_TAKEN + " AS AL"
                + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                + " ON AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]
                + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                + " = " + lDepartmentOid;
        try {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                long lAlStockOid = rs.getLong(1);

                // delete Al stock
                try {
                    long oidAlStock = PstAlStockTaken.deleteExc(lAlStockOid);
                } catch (Exception e) {
                    System.out.println("Exc : " + e.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Exc : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    //khsusus untuk resetAL
    public static void resetAL(long oid) {
        DBResultSet dbrs = null;
        //boolean result = false;

        try {
            String sql = " UPDATE " + TBL_HR_AL_STOCK_TAKEN
                    + " SET " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + " = 0 "
                    + " where " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] + "=" + oid;


            /*String sql = " DELETE FROM " + TBL_HR_AL_STOCK_TAKEN +
            " WHERE " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]+"="+oid;
             */

            int status = DBHandler.execUpdate(sql);
            System.out.println("SQL RESET AL  " + sql);
        } catch (Exception e) {
            System.err.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /* @param :whereClause
     * this method used to get sum of leave per month
     * created By Yunny
     **/
    public static float getSumLeave(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT SUM(" + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + ") FROM " + TBL_HR_AL_STOCK_TAKEN;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //   System.out.println("sql getSum  "+sql);
            float count = 0;
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
}
