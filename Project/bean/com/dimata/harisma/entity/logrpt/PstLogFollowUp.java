
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.entity.logrpt;

/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.harisma.entity.admin.PstAppUser;
/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
/*import com.dimata.harisma.entity.masterdata.*;*/
import com.dimata.harisma.entity.logrpt.*;

public class PstLogFollowUp extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_LOG_FOLLOW_UP = "log_follow_up";
    public static final int FLD_FOLLOW_UP_ID = 0;
    public static final int FLD_START_DATE_TIME = 1;
    public static final int FLD_FLW_NOTE = 2;
    public static final int FLD_LOG_REPORT_ID = 3;
    public static final int FLD_FLW_UP_BY_USER_ID = 4;
    public static final int FLD_FLW_UP_STATUS = 5;
    public static final int FLD_END_DATE_TIME = 6;
    public static final int FLD_CHK_BY_USER_ID = 7;
    public static final int FLD_SUBMIT_BY_USER_ID=8;
    public static final String[] fieldNames = {
        "FOLLOW_UP_ID",
        "START_DATE_TIME",
        "FLW_NOTE",
        "LOG_REPORT_ID",
        "FLW_UP_BY_USER_ID",
        "FLW_UP_STATUS",
        "END_DATE_TIME",
        "CHK_BY_USER_ID",
        "SUBMIT_BY_USER_ID"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_LONG
    };

    public PstLogFollowUp() {
    }

    public PstLogFollowUp(int i) throws DBException {
        super(new PstLogFollowUp());
    }

    public PstLogFollowUp(String sOid) throws DBException {
        super(new PstLogFollowUp(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstLogFollowUp(long lOid) throws DBException {
        super(new PstLogFollowUp(0));
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
        return TBL_LOG_FOLLOW_UP;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstLogFollowUp().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        LogFollowUp logFollowUp = fetchExc(ent.getOID());
        ent = (Entity) logFollowUp;
        return logFollowUp.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((LogFollowUp) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((LogFollowUp) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static LogFollowUp fetchExc(long oid) throws DBException {
        try {
            LogFollowUp logFollowUp = new LogFollowUp();
            PstLogFollowUp pstLogFollowUp = new PstLogFollowUp(oid);
            logFollowUp.setOID(oid);
            logFollowUp.setStartDate(pstLogFollowUp.getDate(FLD_START_DATE_TIME));
            logFollowUp.setFlwNote(pstLogFollowUp.getString(FLD_FLW_NOTE));
            logFollowUp.setLogReportId(pstLogFollowUp.getlong(FLD_LOG_REPORT_ID));
            logFollowUp.setFlwUpByUserId(pstLogFollowUp.getlong(FLD_FLW_UP_BY_USER_ID));
            logFollowUp.setFlwUpStatus(pstLogFollowUp.getInt(FLD_FLW_UP_STATUS));
            logFollowUp.setEndDateTime(pstLogFollowUp.getDate(FLD_END_DATE_TIME));
            logFollowUp.setChkByUserId(pstLogFollowUp.getlong(FLD_CHK_BY_USER_ID));
            logFollowUp.setSubmitByUserId(pstLogFollowUp.getlong(FLD_SUBMIT_BY_USER_ID));
            return logFollowUp;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLogFollowUp(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(LogFollowUp logFollowUp) throws DBException {
        try {
            PstLogFollowUp pstLogFollowUp = new PstLogFollowUp(0);

            if (logFollowUp.getFlwUpByUserId() == 0) {
                throw new DBException(new PstLogFollowUp(0), DBException.INVALID_NUMBER);
            } else {
                pstLogFollowUp.setDate(FLD_START_DATE_TIME, logFollowUp.getStartDate());
                pstLogFollowUp.setString(FLD_FLW_NOTE, logFollowUp.getFlwNote());
                pstLogFollowUp.setLong(FLD_LOG_REPORT_ID, logFollowUp.getLogReportId());
                pstLogFollowUp.setLong(FLD_FLW_UP_BY_USER_ID, logFollowUp.getFlwUpByUserId());
                pstLogFollowUp.setInt(FLD_FLW_UP_STATUS, logFollowUp.getFlwUpStatus());
                pstLogFollowUp.setDate(FLD_END_DATE_TIME, logFollowUp.getEndDateTime());
                pstLogFollowUp.setLong(FLD_CHK_BY_USER_ID, logFollowUp.getChkByUserId());
                pstLogFollowUp.setLong(FLD_SUBMIT_BY_USER_ID, logFollowUp.getSubmitByUserId());

                pstLogFollowUp.insert();
                logFollowUp.setOID(pstLogFollowUp.getlong(FLD_FOLLOW_UP_ID));
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLogFollowUp(0), DBException.UNKNOWN);
        }
        return logFollowUp.getOID();
    }

    public static long updateExc(LogFollowUp logFollowUp) throws DBException {
        try {
            if (logFollowUp.getFlwUpByUserId() == 0) {
                throw new DBException(new PstLogFollowUp(0), DBException.INVALID_NUMBER);
            } else {

                if (logFollowUp.getOID() != 0) {
                    PstLogFollowUp pstLogFollowUp = new PstLogFollowUp(logFollowUp.getOID());


                    pstLogFollowUp.setDate(FLD_START_DATE_TIME, logFollowUp.getStartDate());
                    pstLogFollowUp.setString(FLD_FLW_NOTE, logFollowUp.getFlwNote());
                    pstLogFollowUp.setLong(FLD_LOG_REPORT_ID, logFollowUp.getLogReportId());
                    pstLogFollowUp.setLong(FLD_FLW_UP_BY_USER_ID, logFollowUp.getFlwUpByUserId());
                    pstLogFollowUp.setInt(FLD_FLW_UP_STATUS, logFollowUp.getFlwUpStatus());
                    pstLogFollowUp.setDate(FLD_END_DATE_TIME, logFollowUp.getEndDateTime());
                    pstLogFollowUp.setLong(FLD_CHK_BY_USER_ID, logFollowUp.getChkByUserId());
                    pstLogFollowUp.setLong(FLD_SUBMIT_BY_USER_ID, logFollowUp.getSubmitByUserId());

                    pstLogFollowUp.update();
                    return logFollowUp.getOID();

                }
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLogFollowUp(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstLogFollowUp pstLogFollowUp = new PstLogFollowUp(oid);
            pstLogFollowUp.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLogFollowUp(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 1000, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_LOG_FOLLOW_UP;
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
//                        System.out.println("sql logFollowUp : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                LogFollowUp logFollowUp = new LogFollowUp();
                resultToObject(rs, logFollowUp);
                lists.add(logFollowUp);
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

    private static void resultToObject(ResultSet rs, LogFollowUp logFollowUp) {
        try {
            logFollowUp.setOID(rs.getLong(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_FOLLOW_UP_ID]));
            logFollowUp.setStartDate(rs.getDate(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_START_DATE_TIME]));
            logFollowUp.setFlwNote(rs.getString(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_FLW_NOTE]));
            logFollowUp.setLogReportId(rs.getLong(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_LOG_REPORT_ID]));
            logFollowUp.setFlwUpByUserId(rs.getLong(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_FLW_UP_BY_USER_ID]));
            logFollowUp.setFlwUpStatus(rs.getInt(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_FLW_UP_BY_USER_ID]));
            logFollowUp.setEndDateTime(rs.getDate(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_END_DATE_TIME]));
            logFollowUp.setChkByUserId(rs.getLong(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_CHK_BY_USER_ID]));
            logFollowUp.setSubmitByUserId(rs.getLong(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_SUBMIT_BY_USER_ID]));

        } catch (Exception e) {
        }
    }

    public static Vector listWithJoin(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT a.*,b.*, c.* FROM " + TBL_LOG_FOLLOW_UP + " as a inner join "
                    + PstAppUser.TBL_APP_USER + " as b on a." + fieldNames[FLD_FLW_UP_BY_USER_ID] + "="
                    + "b." + PstAppUser.fieldNames[PstAppUser.FLD_USER_ID] + " left join "
                    + PstAppUser.TBL_APP_USER + " as c on a." + fieldNames[FLD_CHK_BY_USER_ID] + "="
                    + "c." + PstAppUser.fieldNames[PstAppUser.FLD_USER_ID];
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            } 

           // sql = sql + " GROUP BY " + fieldNames[FLD_LOG_REPORT_ID];

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
//                        System.out.println("sql logFollowUp : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                LogFollowUp logFollowUp = new LogFollowUp();
                resultToObjectWithJoin(rs, logFollowUp);
                lists.add(logFollowUp);
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

    private static void resultToObjectWithJoin(ResultSet rs, LogFollowUp logFollowUp) {
        try {
            logFollowUp.setOID(rs.getLong(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_FOLLOW_UP_ID]));
            logFollowUp.setStartDate(rs.getDate(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_START_DATE_TIME]));
            logFollowUp.setFlwNote(rs.getString(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_FLW_NOTE]));
            logFollowUp.setLogReportId(rs.getLong(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_LOG_REPORT_ID]));
            logFollowUp.setFlwUpByUserId(rs.getLong(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_FLW_UP_BY_USER_ID]));
            logFollowUp.setFlwUpStatus(rs.getInt(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_FLW_UP_STATUS]));
            logFollowUp.setEndDateTime(rs.getDate(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_END_DATE_TIME]));
            logFollowUp.setChkByUserId(rs.getLong(PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_CHK_BY_USER_ID]));
            logFollowUp.setFlwUpByUser(rs.getString("b." + PstAppUser.fieldNames[PstAppUser.FLD_FULL_NAME]));
            logFollowUp.setLoginIdFollowUp(rs.getString("b."+PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]));
            logFollowUp.setChkByUser(rs.getString("c." + PstAppUser.fieldNames[PstAppUser.FLD_FULL_NAME]));
            logFollowUp.setEmailFollowUp(rs.getString("c."+PstAppUser.fieldNames[PstAppUser.FLD_EMAIL]));
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long flwUpId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_LOG_FOLLOW_UP + " WHERE "
                    + PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_FOLLOW_UP_ID] + " = " + flwUpId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
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

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_LOG_REPORT_ID] + ") FROM " + TBL_LOG_FOLLOW_UP;
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
                    LogFollowUp logFollowUp = (LogFollowUp) list.get(ls);
                    if (oid == logFollowUp.getOID()) {
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

    public static boolean checkFollowUpByReportID(long rptId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_LOG_FOLLOW_UP + " WHERE "
                    + PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_LOG_REPORT_ID] + " = " + rptId + " LIMIT 0,1";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
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

    public static boolean checkFollowUpByReportID(long rptId, int status) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_LOG_FOLLOW_UP + " WHERE "
                    + PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_LOG_REPORT_ID] + " = '" + rptId
                    + "' AND " + PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_FLW_UP_STATUS] + " = '" + status + "' LIMIT 0,1";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
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


    public static boolean updateNotifFollowUp(long rptId) {
        DBResultSet dbrs = null;
        boolean notif = true;
        try {
            String sql = "UPDATE " + TBL_LOG_FOLLOW_UP + "SET STATUS_FOLLOW_UP='1' WHERE "
                    + PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_LOG_REPORT_ID] + " = '" + rptId;

             int result = DBHandler.execUpdate(sql);
             
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return notif;
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
}
