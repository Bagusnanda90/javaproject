
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya
 * @version  	: 01
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.entity.masterdata;

/* package java */
import java.util.Date;
import java.util.Vector;
import java.sql.ResultSet;


/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.employee.PstEmployee;
import static com.dimata.qdep.db.I_DBType.TYPE_INT;
import com.dimata.util.Formater;
import java.util.Hashtable;

public class PstScheduleSymbol extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String SESS_HR_SCHEDULE_SYMBOL = "SESS_HR_SCHEDULE_SYMBOL";
    public static final String TBL_HR_SCHEDULE_SYMBOL = "hr_schedule_symbol";//"HR_SCHEDULE_SYMBOL";
    public static final int FLD_SCHEDULE_ID = 0;
    public static final int FLD_SCHEDULE_CATEGORY_ID = 1;
    public static final int FLD_SCHEDULE = 2;
    public static final int FLD_SYMBOL = 3;
    public static final int FLD_TIME_IN = 4;
    public static final int FLD_TIME_OUT = 5;
    //Tambahan untuk special leave pada hardrock \m/
    public static final int FLD_MAX_ENTITLE = 6;
    public static final int FLD_PERIODE = 7;
    public static final int FLD_PERIODE_TYPE = 8;
    public static final int FLD_MIN_SERVICE = 9;
    public static final int FLD_BREAK_OUT = 10;
    public static final int FLD_BREAK_IN = 11;
    // FIELD IS ADDED BY MCHEN
    public static final int FLD_TRANSPORT_ALLOWANCE = 12;
    public static final int FLD_NIGHT_ALLOWANCE = 13;
    public static final int FLD_WORK_DAYS = 14;
    public static final String[] fieldNames = {
        "SCHEDULE_ID",
        "SCHEDULE_CATEGORY_ID",
        "SCHEDULE",
        "SYMBOL",
        "TIME_IN",
        "TIME_OUT",
        //Tambahan untuk special leave pada hardrock
        "MAX_ENTITLE",
        "PERIODE",
        "PERIODE_TYPE",
        "MIN_SERVICE",
        "BREAK_OUT",
        "BREAK_IN",
        "TRANSPORT_ALLOWANCE",
        "NIGHT_ALLOWANCE",
        "WORK_DAYS"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT
    };
    public static final int PERIODE_TYPE_TIME_AT_ALL = 0;
    public static final int PERIODE_TYPE_MONTH = 1;
    public static final int PERIODE_TYPE_YEAR = 2;
    public static final String[] fieldNamesPeriodeType = {
        "Time At All",
        "Month",
        "Year"
    };
    public static final int SCHEDULE_TYPE_NORMAL = 0;
    public static final int SCHEDULE_TYPE_EXTRA = 1;
    public static final String[] fieldNamesTypeSchedule = {
        "Normal Schedule",
        "Extra Schedule"
    };

    public PstScheduleSymbol() {
    }

    public PstScheduleSymbol(int i) throws DBException {
        super(new PstScheduleSymbol());
    }

    public PstScheduleSymbol(String sOid) throws DBException {
        super(new PstScheduleSymbol(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstScheduleSymbol(long lOid) throws DBException {
        super(new PstScheduleSymbol(0));
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
        return TBL_HR_SCHEDULE_SYMBOL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstScheduleSymbol().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        ScheduleSymbol schedulesymbol = fetchExc(ent.getOID());
        ent = (Entity) schedulesymbol;
        return schedulesymbol.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((ScheduleSymbol) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((ScheduleSymbol) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static ScheduleSymbol fetchExc(long oid) throws DBException {
        try {
            ScheduleSymbol schedulesymbol = new ScheduleSymbol();
            PstScheduleSymbol pstScheduleSymbol = new PstScheduleSymbol(oid);
            schedulesymbol.setOID(oid);

            schedulesymbol.setScheduleCategoryId(pstScheduleSymbol.getlong(FLD_SCHEDULE_CATEGORY_ID));
            schedulesymbol.setSchedule(pstScheduleSymbol.getString(FLD_SCHEDULE));
            schedulesymbol.setSymbol(pstScheduleSymbol.getString(FLD_SYMBOL));
            schedulesymbol.setTimeIn(pstScheduleSymbol.getDate(FLD_TIME_IN));
            schedulesymbol.setTimeOut(pstScheduleSymbol.getDate(FLD_TIME_OUT));

            schedulesymbol.setMaxEntitle(pstScheduleSymbol.getInt(FLD_MAX_ENTITLE));
            schedulesymbol.setPeriode(pstScheduleSymbol.getInt(FLD_PERIODE));
            schedulesymbol.setPeriodeType(pstScheduleSymbol.getInt(FLD_PERIODE_TYPE));
            schedulesymbol.setMinService(pstScheduleSymbol.getInt(FLD_MIN_SERVICE));
            schedulesymbol.setBreakOut(pstScheduleSymbol.getDate(FLD_BREAK_OUT));
            schedulesymbol.setBreakIn(pstScheduleSymbol.getDate(FLD_BREAK_IN));
            // ADDED BY MCHEN
            schedulesymbol.setTransportAllowance(pstScheduleSymbol.getInt(FLD_TRANSPORT_ALLOWANCE));
            schedulesymbol.setNightAllowance(pstScheduleSymbol.getInt(FLD_NIGHT_ALLOWANCE));
            schedulesymbol.setWorkDays(pstScheduleSymbol.getInt(FLD_WORK_DAYS));

            return schedulesymbol;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstScheduleSymbol(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(ScheduleSymbol schedulesymbol) throws DBException {
        try {
            PstScheduleSymbol pstScheduleSymbol = new PstScheduleSymbol(0);

            pstScheduleSymbol.setLong(FLD_SCHEDULE_CATEGORY_ID, schedulesymbol.getScheduleCategoryId());
            pstScheduleSymbol.setString(FLD_SCHEDULE, schedulesymbol.getSchedule());
            pstScheduleSymbol.setString(FLD_SYMBOL, schedulesymbol.getSymbol());
            pstScheduleSymbol.setDate(FLD_TIME_IN, schedulesymbol.getTimeIn());
            pstScheduleSymbol.setDate(FLD_TIME_OUT, schedulesymbol.getTimeOut());

            pstScheduleSymbol.setInt(FLD_MAX_ENTITLE, schedulesymbol.getMaxEntitle());
            pstScheduleSymbol.setInt(FLD_PERIODE, schedulesymbol.getPeriode());
            pstScheduleSymbol.setInt(FLD_PERIODE_TYPE, schedulesymbol.getPeriodeType());
            pstScheduleSymbol.setInt(FLD_MIN_SERVICE, schedulesymbol.getMinService());
            pstScheduleSymbol.setDate(FLD_BREAK_OUT, schedulesymbol.getBreakOut());
            pstScheduleSymbol.setDate(FLD_BREAK_IN, schedulesymbol.getBreakIn());
            // Added by McHen
            pstScheduleSymbol.setInt(FLD_TRANSPORT_ALLOWANCE, schedulesymbol.getTransportAllowance());
            pstScheduleSymbol.setInt(FLD_NIGHT_ALLOWANCE, schedulesymbol.getNightAllowance());
            pstScheduleSymbol.setInt(FLD_WORK_DAYS, schedulesymbol.getWorkDays());

            pstScheduleSymbol.insert();
            schedulesymbol.setOID(pstScheduleSymbol.getlong(FLD_SCHEDULE_ID));
        } catch (DBException dbe) {
            System.out.println("Exception Save Sysmbol" + dbe);
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstScheduleSymbol(0), DBException.UNKNOWN);
        }
        return schedulesymbol.getOID();
    }

    public static long updateExc(ScheduleSymbol schedulesymbol) throws DBException {
        try {
            if (schedulesymbol.getOID() != 0) {
                PstScheduleSymbol pstScheduleSymbol = new PstScheduleSymbol(schedulesymbol.getOID());

                pstScheduleSymbol.setLong(FLD_SCHEDULE_CATEGORY_ID, schedulesymbol.getScheduleCategoryId());
                pstScheduleSymbol.setString(FLD_SCHEDULE, schedulesymbol.getSchedule());
                pstScheduleSymbol.setString(FLD_SYMBOL, schedulesymbol.getSymbol());
                pstScheduleSymbol.setDate(FLD_TIME_IN, schedulesymbol.getTimeIn());
                pstScheduleSymbol.setDate(FLD_TIME_OUT, schedulesymbol.getTimeOut());

                pstScheduleSymbol.setInt(FLD_MAX_ENTITLE, schedulesymbol.getMaxEntitle());
                pstScheduleSymbol.setInt(FLD_PERIODE, schedulesymbol.getPeriode());
                pstScheduleSymbol.setInt(FLD_PERIODE_TYPE, schedulesymbol.getPeriodeType());
                pstScheduleSymbol.setInt(FLD_MIN_SERVICE, schedulesymbol.getMinService());
                pstScheduleSymbol.setDate(FLD_BREAK_OUT, schedulesymbol.getBreakOut());
                pstScheduleSymbol.setDate(FLD_BREAK_IN, schedulesymbol.getBreakIn());
                // Added by McHen
                pstScheduleSymbol.setInt(FLD_TRANSPORT_ALLOWANCE, schedulesymbol.getTransportAllowance());
                pstScheduleSymbol.setInt(FLD_NIGHT_ALLOWANCE, schedulesymbol.getNightAllowance());
                pstScheduleSymbol.setInt(FLD_WORK_DAYS, schedulesymbol.getWorkDays());

                pstScheduleSymbol.update();
                return schedulesymbol.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstScheduleSymbol(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstScheduleSymbol pstScheduleSymbol = new PstScheduleSymbol(oid);
            pstScheduleSymbol.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstScheduleSymbol(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 500, "", fieldNames[FLD_SYMBOL]);
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_SCHEDULE_SYMBOL;
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
//                        System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                ScheduleSymbol schedulesymbol = new ScheduleSymbol();
                resultToObject(rs, schedulesymbol);
                lists.add(schedulesymbol);
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

    public static int getIdxNameOfTableBySelectedDate(Date selectedDate) {
        if (selectedDate != null) {
            return selectedDate.getDate();
        } else {
            return 0;
        }
    }

    
   
    
    
    //update by satrya 2012-08-01
    /**
     *
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return
     */
    public static Hashtable<String, String> getScheduleSymbolIdMap(Vector<Long> vectSchldCategoryId) {
        DBResultSet dbrs = null;

        Hashtable<String, String> scheduleSymbolIdMap = new Hashtable();

        String strWhereClause = "";
        if (vectSchldCategoryId != null && vectSchldCategoryId.size() > 0) {
            int maxCategory = vectSchldCategoryId.size();
            for (int i = 0; i < maxCategory; i++) {
                strWhereClause = strWhereClause + ((Long) vectSchldCategoryId.get(i)).longValue() + ",";
            }

            if (strWhereClause != null && strWhereClause.length() > 0) {
                strWhereClause = strWhereClause.substring(0, strWhereClause.length() - 1);
            }
        }
        try {
            String sql = " SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " , SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL
                    + " AS SYM WHERE SYM." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " IN( " + strWhereClause + ")";

            //System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                scheduleSymbolIdMap.put("" + rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]), rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
            }
            rs.close();
            return scheduleSymbolIdMap;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Hashtable();
    }
    //update by satrya 2012-09-20

    public static Hashtable<String, Long> getBreakTimeDuration() {
        DBResultSet dbrs = null;

        Hashtable<String, Long> scheduleSymbolMap = new Hashtable();
        long breakDurationTime = 0;
        /// ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
        try {
            String sql = " SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + ","
                    + " SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_OUT] + ","
                    + " SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN]
                    + " , SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL
                    + " AS SYM ";

            //System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {

                breakDurationTime = rs.getTime(3).getTime() - rs.getTime(2).getTime();
                scheduleSymbolMap.put("" + rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]), breakDurationTime);

            }

            rs.close();
            return scheduleSymbolMap;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Hashtable();
    }

    // update by satrya 2012-08-21
    public static Hashtable<String, Long> getSymbolMap(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = list(limitStart, recordToGet, whereClause, order);
        Hashtable<String, Long> symbolMap = new Hashtable();
        try {
            if (lists != null && lists.size() > 0) {
                for (int i = 0; i < lists.size(); i++) {
                    ScheduleSymbol schedulesymbol = (ScheduleSymbol) lists.get(i);
                    symbolMap.put(schedulesymbol.getSymbol().toUpperCase(), schedulesymbol.getOID());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return symbolMap;
    }

    /**
     * Create by satrya 2013-06-27
     *
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return
     */
    public static Hashtable getHashTblSchedule(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = list(limitStart, recordToGet, whereClause, order);
        Hashtable symbolMap = new Hashtable();
        try {
            if (lists != null && lists.size() > 0) {
                for (int i = 0; i < lists.size(); i++) {
                    ScheduleSymbol schedulesymbol = (ScheduleSymbol) lists.get(i);
                    symbolMap.put(schedulesymbol.getOID(), schedulesymbol);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return symbolMap;
    }

    
       /**
     * Create by priska 2015-10-22
     *
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return
     */
    public static Hashtable getHashTblScheduleSymbol(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = list(limitStart, recordToGet, whereClause, order);
        Hashtable symbolMap = new Hashtable();
        try {
            if (lists != null && lists.size() > 0) {
                for (int i = 0; i < lists.size(); i++) {
                    ScheduleSymbol schedulesymbol = (ScheduleSymbol) lists.get(i);
                    symbolMap.put(schedulesymbol.getOID(), schedulesymbol.getSymbol());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return symbolMap;
    }
    
    
    /**
     * update by satrya 2014-03-07
     *
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return
     */
    public static Hashtable getHashTblScheduleVer2(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = list(limitStart, recordToGet, whereClause, order);
        Hashtable symbolMap = new Hashtable();
        try {
            if (lists != null && lists.size() > 0) {
                for (int i = 0; i < lists.size(); i++) {
                    ScheduleSymbol schedulesymbol = (ScheduleSymbol) lists.get(i);
                    symbolMap.put("" + schedulesymbol.getOID(), schedulesymbol);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return symbolMap;
    }

    public static Hashtable getHashTlScheduleAll() {
        return getHashTblSchedule(0, 0, "", "");
    }

    private static void resultToObject(ResultSet rs, ScheduleSymbol schedulesymbol) {
        try {
            schedulesymbol.setOID(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]));
            schedulesymbol.setScheduleCategoryId(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]));
            schedulesymbol.setSchedule(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]));
            schedulesymbol.setSymbol(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]).toUpperCase());
            //schedulesymbol.setTimeIn(rs.getDate(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]));
            //schedulesymbol.setTimeOut(rs.getDate(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]));
            schedulesymbol.setTimeIn(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]));
            schedulesymbol.setTimeOut(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]));

            schedulesymbol.setMaxEntitle(rs.getInt(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_MAX_ENTITLE]));
            schedulesymbol.setPeriode(rs.getInt(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_PERIODE]));
            schedulesymbol.setPeriodeType(rs.getInt(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_PERIODE_TYPE]));
            schedulesymbol.setMinService(rs.getInt(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_MIN_SERVICE]));
            schedulesymbol.setBreakOut(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_OUT]));
            schedulesymbol.setBreakIn(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN]));
            // Add by McHen
            schedulesymbol.setTransportAllowance(rs.getInt(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TRANSPORT_ALLOWANCE]));
            schedulesymbol.setNightAllowance(rs.getInt(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_NIGHT_ALLOWANCE]));
            schedulesymbol.setWorkDays(rs.getInt(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_WORK_DAYS]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long scheduleId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_SCHEDULE_SYMBOL + " WHERE "
                    + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + " = " + scheduleId;

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
            String sql = "SELECT COUNT(" + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + ") FROM " + TBL_HR_SCHEDULE_SYMBOL;
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
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String order) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, order);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    ScheduleSymbol schedulesymbol = (ScheduleSymbol) list.get(ls);
                    if (oid == schedulesymbol.getOID()) {
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

    public static boolean checkScheduleCategory(long scheduleCategoryId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_SCHEDULE_SYMBOL + " WHERE "
                    + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] + " = '" + scheduleCategoryId + "'";

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

    /**
     * @param scheduleId
     * @return
     */
    public static int getCategoryType(long scheduleId) {
        DBResultSet dbrs = null;
        int result = -2;
        try {
            String sql = "SELECT CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SCH"
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT"
                    + " ON SCH." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE SCH." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " = " + scheduleId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * @param scheduleSymbol
     * @return
     */
    public static int getCategoryType(String scheduleSymbol) {
        DBResultSet dbrs = null;
        int result = -2;
        try {
            String sql = "SELECT CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SCH"
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT"
                    + " ON SCH." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE SCH." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + " = '" + scheduleSymbol + "'";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            ///System.out.println("getCategoryType  "+sql);
            while (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static boolean checkMaster(long oid) {
        if (PstEmpSchedule.checkScheduleSymbol(oid)) {
            return true;
        } else {
            return false;
        }
    }

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + mdl;
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

    /**
     * get schedule symbol of selected symbol OID
     *
     * @param scheduleId
     * @param schldCat
     * @return
     * @created by Edhy
     */
    public static String getScheduleSymbol(long scheduleId, int schldCat) {
        DBResultSet dbrs = null;
        String result = "";
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT"
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " = " + scheduleId
                    + " AND CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + " = " + schldCat;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getString(1);
                break;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * get schedule OID of selected symbol Symbol
     *
     * @param scheduleSymbol
     * @return
     * @created by Artha
     */
    public static long getScheduleId(String scheduleSymbol) {
        DBResultSet dbrs = null;
        long result = 0;
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                    + " WHERE SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + " = '" + scheduleSymbol + "'";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getLong(1);
                break;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     *
     * @param scheduleId
     * @return
     */
    public static String getScheduleSymbol(long scheduleId) {
        DBResultSet dbrs = null;
        String result = "";
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT"
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " = " + scheduleId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getString(1);
                break;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * mencari schedule nya create by satrya 2013-07-12
     *
     * @param scheduleId
     * @param hashSchedule
     * @return
     */
    public static ScheduleSymbol getSchedule(long scheduleId, Hashtable hashSchedule) {
        ScheduleSymbol scheduleSymbol = null;
        try {
            scheduleSymbol = (ScheduleSymbol) hashSchedule.get(scheduleId);
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            return scheduleSymbol;
        }
    }

    /**
     *
     * @param schCategType
     * @return
     */
    public static Vector getScheduleId(int schCategType) {
        DBResultSet dbrs = null;
        Vector result = new Vector();
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT"
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + " = " + schCategType;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Long LO = new Long(rs.getLong(1));
                result.add(LO);
                // update by satrya 2013-12-12 break;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * create by satrya
     *
     * @param schCategType
     * @return
     */
    public static Hashtable getHashScheduleId(int schCategType) {
        DBResultSet dbrs = null;
        Hashtable result = new Hashtable();
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT"
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + " = " + schCategType;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Long LO = new Long(rs.getLong(1));
                result.put("" + LO, "" + LO);
                // update by satrya 2013-12-12 break;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * Mencari hashtable schedule OFF create by satrya 2014-01-32
     *
     * @param schCategType
     * @return Hashtable
     */
    public static Hashtable getHashScheduleIdOFF(int schCategType) {
        DBResultSet dbrs = null;
        Hashtable result = new Hashtable();
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT"
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + " = " + schCategType;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Long LO = new Long(rs.getLong(1));
                result.put(LO, true);
                // update by satrya 2013-12-12 break;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * get schedule symbol of selected symbol OID
     *
     * @param scheduleId
     * @return
     * @created by Edhy
     */
    public static Vector getScheduleData(long scheduleId) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT"
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " = " + scheduleId;

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vectTemp = new Vector(1, 1);
                vectTemp.add(rs.getString(1));
                vectTemp.add(rs.getString(2));
                vectTemp.add(rs.getTime(3));
                vectTemp.add(rs.getTime(4));

                result.add(vectTemp);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * get schedule symbol of selected symbol OID
     *
     * @param scheduleId
     * @return
     * @created by Edhy
     */
    public static Vector getScheduleData(long scheduleId, Date selectedDate) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]
                    + //update by satrya 2012-09-26
                    ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_OUT]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT"
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " = " + scheduleId;

            dbrs = DBHandler.execQueryResult(sql);
            /// System.out.println("SQL Schedule  "+sql);
            ResultSet rs = dbrs.getResultSet();
            java.sql.Date sqlCurrDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());
            java.sql.Date sqlNextDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate() + 1);
            while (rs.next()) {
                Vector vectTemp = new Vector(1, 1);
                vectTemp.add(rs.getString(1));
                vectTemp.add(rs.getString(2));

                // jika tipe schedule lintas hari maka tanggal OUT adalah sehari setelah tanggal IN
                if (rs.getInt(2) == PstScheduleCategory.CATEGORY_NIGHT_WORKER
                        || rs.getInt(2) == PstScheduleCategory.CATEGORY_ACCROSS_DAY
                        || rs.getInt(2) == PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY) {
                    if (rs.getTime(3) != null) {
                        vectTemp.add(DBHandler.convertDate(sqlCurrDate, rs.getTime(3)));
                    } else {
                        vectTemp.add(rs.getTime(3));
                    }

                    if (rs.getTime(4) != null) {
                        vectTemp.add(DBHandler.convertDate(sqlNextDate, rs.getTime(4)));
                    } else {
                        vectTemp.add(rs.getTime(4));
                    }
                    //update by satrya 2012-09-26
                    if (rs.getTime(5) != null) {//break out
                        vectTemp.add(rs.getTime(5));

                    }
                    if (rs.getTime(6) != null) {//break IN
                        vectTemp.add(rs.getTime(6));

                    }
                    if (rs.getString(7) != null) {//schedule
                        vectTemp.add(rs.getString(7));
                    }

                } else {
                    if (rs.getTime(3) != null) {
                        vectTemp.add(DBHandler.convertDate(sqlCurrDate, rs.getTime(3)));
                    } else {
                        vectTemp.add(rs.getTime(3));
                    }

                    if (rs.getTime(4) != null) {
                        //update by satrya 2012-08-22
                        vectTemp.add(DBHandler.convertDate(sqlCurrDate, rs.getTime(4)));
                        Date inSch = (Date) vectTemp.get(2);
                        Date outSch = (Date) vectTemp.get(3);
                        if ((inSch != null && outSch != null) && inSch.getTime() > outSch.getTime()) {
                            outSch = new Date(outSch.getTime() + 24 * 60 * 60 * 1000);
                            vectTemp.set(3, outSch);
                        }
                    } else {
                        vectTemp.add(rs.getTime(4));
                    }
                    //update by satrya 2012-09-26
                    if (rs.getTime(5) != null) {//break out
                        //vectTemp.add(rs.getTime(5));
                        vectTemp.add(DBHandler.convertDate(sqlCurrDate, rs.getTime(5)));

                    }
                    if (rs.getTime(6) != null) {//break IN
                        //vectTemp.add(rs.getTime(6));
                        vectTemp.add(DBHandler.convertDate(sqlCurrDate, rs.getTime(6)));
                        Date BoutSch = (Date) vectTemp.get(4);
                        Date BinSch = (Date) vectTemp.get(5);
                        if ((BoutSch != null && BinSch != null) && BoutSch.getTime() > BoutSch.getTime()) {
                            BinSch = new Date(BinSch.getTime() + 24 * 60 * 60 * 1000);
                            vectTemp.set(5, BinSch);
                        }
                    }
                    if (rs.getString(7) != null) {//schedule
                        vectTemp.add(rs.getString(7));
                    }

                }
                result.add(vectTemp);
            }
            rs.close();
        } catch (Exception e) {
            //update by satrya 2012-10-04
            System.out.println("Exception getScheduleData : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * gadnyana untuk mengambil schedule yang typenya al,dp,dan ll
     *
     * @return
     */
    public static Vector getScheduleDPALLL() {
        DBResultSet dbrs = null;
        Vector list = new Vector(1, 1);
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM "
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT "
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                    + " OR CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                    + " OR CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_LONG_LEAVE;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector(1, 1);
                ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                ScheduleCategory scheduleCategory = new ScheduleCategory();

                scheduleSymbol.setOID(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]));
                scheduleCategory.setCategoryType(rs.getInt(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]));
                vt.add(scheduleSymbol);
                vt.add(scheduleCategory);

                list.add(vt);
            }
        } catch (Exception e) {
            System.out.println("err :" + e.toString());
        }
        return list;
    }

    public static Vector getScheduleDpAlLlSpecial() {
        DBResultSet dbrs = null;
        Vector list = new Vector(1, 1);
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM "
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT "
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                    + " OR CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                    + " OR CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_LONG_LEAVE
                    + " OR CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_SPECIAL_LEAVE;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector(1, 1);
                ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                ScheduleCategory scheduleCategory = new ScheduleCategory();

                scheduleSymbol.setOID(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]));
                scheduleCategory.setCategoryType(rs.getInt(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]));
                vt.add(scheduleSymbol);
                vt.add(scheduleCategory);

                list.add(vt);
            }
        } catch (Exception e) {
            System.out.println("err :" + e.toString());
        }
        return list;
    }

    public static Vector getOidScheduleDpAlLlSpecial() {
        DBResultSet dbrs = null;
        Vector list = new Vector(1, 1);
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM "
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT "
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                    + " OR CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                    + " OR CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_LONG_LEAVE
                    + " OR CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_SPECIAL_LEAVE;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector(1, 1);
                ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                ScheduleCategory scheduleCategory = new ScheduleCategory();

                Long oid = rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]);
                list.add(oid);
            }
        } catch (Exception e) {
            System.out.println("err :" + e.toString());
        }
        return list;
    }

    public static long[] getOidArrayScheduleDpAlLlSpecial() {
        DBResultSet dbrs = null;
        Vector list = new Vector(1, 1);
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM "
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT "
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                    + " OR CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                    + " OR CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_LONG_LEAVE
                    + " OR CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_SPECIAL_LEAVE;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Long oid = rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]);
                list.add(oid);
            }
            if (list.size() > 0) {
                long oids[] = new long[list.size()];
                for (int idx = 0; idx < list.size(); idx++) {
                    oids[idx] = ((Long) list.get(idx)).longValue();
                }
                return oids;
            }

        } catch (Exception e) {
            System.out.println("err :" + e.toString());
        }
        return null;
    }

    public static long[] getOidSpecial() {
        DBResultSet dbrs = null;
        Vector list = new Vector(1, 1);
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM "
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT "
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_SPECIAL_LEAVE;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Long oid = rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]);
                list.add(oid);
            }
            if (list.size() > 0) {
                long oids[] = new long[list.size()];
                for (int idx = 0; idx < list.size(); idx++) {
                    oids[idx] = ((Long) list.get(idx)).longValue();
                }
                return oids;
            }

        } catch (Exception e) {
            System.out.println("err :" + e.toString());
        }
        return null;
    }

    public static long[] getOidArrayUnpaidLeave() {
        DBResultSet dbrs = null;
        Vector list = new Vector(1, 1);
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM "
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT "
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + PstScheduleCategory.CATEGORY_UNPAID_LEAVE;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Long oid = rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]);
                list.add(oid);
            }
            if (list.size() > 0) {
                long oids[] = new long[list.size()];
                for (int idx = 0; idx < list.size(); idx++) {
                    oids[idx] = ((Long) list.get(idx)).longValue();
                }
                return oids;
            }

        } catch (Exception e) {
            System.out.println("err :" + e.toString());
        }
        return null;
    }

    /**
     * untuk mengambil schedule symbol sesuai dengan parameter category
     *
     * @param vectSchldCategory
     * @return
     * @created by Edhy
     */
    public static Vector getScheduleSymbolByCategory(Vector vectSchldCategory) {
        DBResultSet dbrs = null;
        Vector list = new Vector(1, 1);

        String strWhereClause = "";
        if (vectSchldCategory != null && vectSchldCategory.size() > 0) {
            int maxCategory = vectSchldCategory.size();
            for (int i = 0; i < maxCategory; i++) {
                strWhereClause = strWhereClause + vectSchldCategory.get(i) + ",";
            }

            if (strWhereClause != null && strWhereClause.length() > 0) {
                strWhereClause = strWhereClause.substring(0, strWhereClause.length() - 1);
            }
        }

        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM "
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT "
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + " IN (" + strWhereClause + ")";
            //System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                scheduleSymbol.setOID(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]));
                scheduleSymbol.setSymbol(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
                list.add(scheduleSymbol);
            }
        } catch (Exception e) {
            System.out.println("err :" + e.toString());
        }
        return list;
    }

    /**
     * list schedule symbol and its category
     *
     * @param scheduleId
     * @return
     * @created by Edhy
     */
    public static Vector listScheduleSymbolAndCategory() {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_DESCRIPTION]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT"
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vectTemp = new Vector(1, 1);

                ScheduleSymbol objScheduleSymbol = new ScheduleSymbol();
                objScheduleSymbol.setOID(rs.getLong(1));
                objScheduleSymbol.setScheduleCategoryId(rs.getLong(2));
                objScheduleSymbol.setSchedule(rs.getString(3));
                objScheduleSymbol.setSymbol(rs.getString(4).toUpperCase());
                objScheduleSymbol.setTimeIn(rs.getTime(5));
                objScheduleSymbol.setTimeOut(rs.getTime(6));
                vectTemp.add(objScheduleSymbol);

                ScheduleCategory objScheduleCategory = new ScheduleCategory();
                objScheduleCategory.setOID(rs.getLong(7));
                objScheduleCategory.setCategoryType(rs.getInt(8));
                objScheduleCategory.setCategory(rs.getString(9));
                objScheduleCategory.setDescription(rs.getString(10));
                vectTemp.add(objScheduleCategory);

                result.add(vectTemp);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * Create by satrya 2013-04-24
     *
     * @return
     */
    public static Hashtable listScheduleSymbol() {
        DBResultSet dbrs = null;
        Hashtable scheduleSymbolIdMap = new Hashtable();
        Vector result = new Vector(1, 1);
        try {
            String sql = " SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " , SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL;

            //System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                scheduleSymbolIdMap.put("" + rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]), rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
            }
            rs.close();
            return scheduleSymbolIdMap;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Hashtable();
    }

    /**
     * create by satrya 2014-02-18
     *
     * @return
     */
    public static String listScheduleIdSymbol() {
        DBResultSet dbrs = null;
        String result = "";
        try {
            String sql = "SELECT " + fieldNames[FLD_SCHEDULE_ID] + " FROM " + TBL_HR_SCHEDULE_SYMBOL + " WHERE \"00:00:00\" <=" + fieldNames[FLD_TIME_OUT]
                    + " AND " + fieldNames[FLD_TIME_IN] + "<= \"" + Formater.formatDate(new Date(), "HH:mm:00") + "\"";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = result + rs.getString(fieldNames[FLD_SCHEDULE_ID]) + ",";
            }
            if (result != null && result.length() > 0) {
                result = result.substring(0, result.length() - 1);
            }
            rs.close();
            return result;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }
}
