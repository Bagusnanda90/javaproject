
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: Satrya Ramayu
 * @version  	: 01
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.utility.service.bkpdtoutlet;

/* package java */
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.*;
import java.sql.*;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.entity.*;

//Gede_7Feb2012 {
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.ScheduleSymbolDesktop;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Ari_20111002 Menambah Company, Division, Level dan EmpCategory
 *
 * @author Wiweka
 */
public class PstBakupScheduleDestopTransfer extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String SESS_HR_SCHEDULE_SYMBOL = "SESS_HR_SCHEDULE_SYMBOL";
    public static final String TBL_HR_SCHEDULE_SYMBOL = "hr_schedule_symbol";//"HR_SCHEDULE_SYMBOL";
    public static final int FLD_SCHEDULE_ID = 0;
    public static final int FLD_SCHEDULE_CATEGORY_ID = 1;
    public static final int FLD_SCHEDULE = 2;
    public static final int FLD_SYMBOL = 3;
    public static final int FLD_TIME_IN = 4;
    public static final int FLD_TIME_OUT = 5;
    //Tambahan untuk special leave pada hardrock
    public static final int FLD_MAX_ENTITLE = 6;
    public static final int FLD_PERIODE = 7;
    public static final int FLD_PERIODE_TYPE = 8;
    public static final int FLD_MIN_SERVICE = 9;
    public static final int FLD_BREAK_OUT = 10;
    public static final int FLD_BREAK_IN = 11;
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
        "BREAK_IN"
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
        TYPE_DATE
    };

    public PstBakupScheduleDestopTransfer() {
    }

    public PstBakupScheduleDestopTransfer(int i) throws DBException {
        super(new PstBakupScheduleDestopTransfer());
    }

    public PstBakupScheduleDestopTransfer(String sOid) throws DBException {
        super(new PstBakupScheduleDestopTransfer(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstBakupScheduleDestopTransfer(long lOid) throws DBException {
        super(new PstBakupScheduleDestopTransfer(0));
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
        return new PstBakupScheduleDestopTransfer().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        return 0;
    }

    public long insertExc(Entity ent) throws Exception {
        return 0;
    }

    public long updateExc(Entity ent) throws Exception {
        return 0;
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static String insertExc(TabelEmployeeScheduleTransfer tabelEmployeeScheduleTransfer) throws DBException {
        String sql="";
        try {
            PstBakupScheduleDestopTransfer PstBakupScheduleDestopTransfer = new PstBakupScheduleDestopTransfer(0);

            PstBakupScheduleDestopTransfer.setString(FLD_SCHEDULE, tabelEmployeeScheduleTransfer.getNameSchedule());
            PstBakupScheduleDestopTransfer.setString(FLD_SYMBOL, tabelEmployeeScheduleTransfer.getScheduleSymbol());
            PstBakupScheduleDestopTransfer.setLong(FLD_SCHEDULE_CATEGORY_ID, tabelEmployeeScheduleTransfer.getScheduleCategory());
            PstBakupScheduleDestopTransfer.setDate(FLD_TIME_IN, tabelEmployeeScheduleTransfer.getTimeIn());
            PstBakupScheduleDestopTransfer.setDate(FLD_TIME_OUT, tabelEmployeeScheduleTransfer.getTimeOut());
            PstBakupScheduleDestopTransfer.setDate(FLD_BREAK_IN, tabelEmployeeScheduleTransfer.getBreakIn());
            PstBakupScheduleDestopTransfer.setDate(FLD_BREAK_OUT, tabelEmployeeScheduleTransfer.getBreakOut());


            sql = PstBakupScheduleDestopTransfer.SyntacInsert(tabelEmployeeScheduleTransfer.getScheduleId());
        }catch (Exception e) {
           System.out.println("Exc"+e);
        }
        return sql;
    }

    public static long updateExc(TabelEmployeeScheduleTransfer tabelEmployeeScheduleTransfer) throws DBException {
        try {
            if (tabelEmployeeScheduleTransfer.getScheduleId() != 0) {
                PstBakupScheduleDestopTransfer PstBakupScheduleDestopTransfer = new PstBakupScheduleDestopTransfer(tabelEmployeeScheduleTransfer.getScheduleId());


                PstBakupScheduleDestopTransfer.setString(FLD_SCHEDULE, tabelEmployeeScheduleTransfer.getNameSchedule());
                PstBakupScheduleDestopTransfer.setString(FLD_SYMBOL, tabelEmployeeScheduleTransfer.getScheduleSymbol());
                PstBakupScheduleDestopTransfer.setLong(FLD_SCHEDULE_CATEGORY_ID, tabelEmployeeScheduleTransfer.getScheduleCategory());
                PstBakupScheduleDestopTransfer.setDate(FLD_TIME_IN, tabelEmployeeScheduleTransfer.getTimeIn());
                PstBakupScheduleDestopTransfer.setDate(FLD_TIME_OUT, tabelEmployeeScheduleTransfer.getTimeOut());
                PstBakupScheduleDestopTransfer.setDate(FLD_BREAK_IN, tabelEmployeeScheduleTransfer.getBreakIn());
                PstBakupScheduleDestopTransfer.setDate(FLD_BREAK_OUT, tabelEmployeeScheduleTransfer.getBreakOut());


                PstBakupScheduleDestopTransfer.update();
                return tabelEmployeeScheduleTransfer.getScheduleId();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBakupScheduleDestopTransfer(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstBakupScheduleDestopTransfer pstCareerPath = new PstBakupScheduleDestopTransfer(oid);
            pstCareerPath.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBakupScheduleDestopTransfer(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static boolean checkOID(long positionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_SCHEDULE_SYMBOL + " WHERE "
                    + PstBakupScheduleDestopTransfer.fieldNames[PstBakupScheduleDestopTransfer.FLD_SCHEDULE_ID] + " = " + positionId;

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
    public static Hashtable<String,Boolean> hashSchSdhAda(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashSchSdhAda= new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "+fieldNames[FLD_SCHEDULE_ID]+" FROM " + TBL_HR_SCHEDULE_SYMBOL;
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
                hashSchSdhAda.put(""+rs.getLong(fieldNames[FLD_SCHEDULE_ID]), true);
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashSchSdhAda;
        }
        
    }
    
     public static Vector List(int limitStart, int recordToGet, String whereClause, String order) {
        Vector list= new Vector();
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
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                ScheduleSymbolDesktop scheduleSymbolDesktop = new ScheduleSymbolDesktop();
                scheduleSymbolDesktop.setOID(rs.getLong(fieldNames[FLD_SCHEDULE_ID]));
                scheduleSymbolDesktop.setBreakIn(rs.getTime(fieldNames[FLD_BREAK_IN]));
                scheduleSymbolDesktop.setBreakOut(rs.getTime(fieldNames[FLD_BREAK_OUT]));
                
                scheduleSymbolDesktop.setMaxEntitle(rs.getInt(fieldNames[FLD_MAX_ENTITLE]));
                scheduleSymbolDesktop.setMinService(rs.getInt(fieldNames[FLD_MIN_SERVICE]));
                scheduleSymbolDesktop.setPeriode(rs.getInt(fieldNames[FLD_PERIODE]));
                scheduleSymbolDesktop.setPeriodeType(rs.getInt(fieldNames[FLD_PERIODE_TYPE]));
                scheduleSymbolDesktop.setSchedule(rs.getString(fieldNames[FLD_SCHEDULE]));
                scheduleSymbolDesktop.setScheduleCategoryId(rs.getLong(fieldNames[FLD_SCHEDULE_CATEGORY_ID]));
                scheduleSymbolDesktop.setSymbol(rs.getString(fieldNames[FLD_SYMBOL]));
                scheduleSymbolDesktop.setTimeIn(rs.getTime(fieldNames[FLD_TIME_IN]));
                scheduleSymbolDesktop.setTimeOut(rs.getTime(fieldNames[FLD_TIME_OUT]));
               list.add(scheduleSymbolDesktop);
               
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return list;
        }
        
    }
}
