/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.service.notification;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class PstNotification extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_NOTIFICATION = "hr_notification";
    public static final int FLD_NOTIFICATION_ID = 0;
    public static final int FLD_MODUL_NAME = 1;
    public static final int FLD_TYPE = 2;
    public static final int FLD_TIME_DISTANCE = 3;
    public static final int FLD_SUBJECT = 4;
    public static final int FLD_TEXT = 5;
    public static final int FLD_DATETIME = 6;
    public static final int FLD_STATUS = 7;
    public static final int FLD_TARGET_EMPLOYEE = 8;
    public static final int FLD_TIME = 9;
    public static final String[] fieldNames = {
        "NOTIFICATION_ID",
        "MODUL_NAME",
        "TYPE",
        "TIME_DISTANCE",
        "SUBJECT",
        "TEXT",
        "DATETIME",
        "STATUS",
        "TARGET_EMPLOYEE",
        "TIME"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING
    };
 
    
    
    public static final String[] modulNames = {
        "END_CONTRACT",
        "BIRTH DATE",
        "ID CARD EXPIRED",
        "END WORK ASSIGN"    
    };
    
    public static final String[] recipientAs = {
        "Recipient",
        "CC",
        "BCC"
    };
    public PstNotification() {
    }

    public PstNotification(int i) throws DBException {
        super(new PstNotification());
    }

    public PstNotification(String sOid) throws DBException {
        super(new PstNotification(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstNotification(long lOid) throws DBException {
        super(new PstNotification(0));
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
        return TBL_HR_NOTIFICATION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstNotification().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        Notification notification = fetchExc(ent.getOID());
        ent = (Entity) notification;
        return notification.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((Notification) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((Notification) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static Notification fetchExc(long oid) throws DBException {
        try {
            Notification notification = new Notification();
            PstNotification pstNotification = new PstNotification(oid);
            notification.setOID(oid);

            notification.setModulName(pstNotification.getInt(FLD_MODUL_NAME));
            notification.setType(pstNotification.getString(FLD_TYPE));
            notification.setTimeDistance(pstNotification.getString(FLD_TIME_DISTANCE));
            notification.setSubject(pstNotification.getString(FLD_SUBJECT));
            notification.setText(pstNotification.getString(FLD_TEXT));
            notification.setDateTime(pstNotification.getDate(FLD_DATETIME));
            notification.setStatus(pstNotification.getInt(FLD_STATUS));
            notification.setTargetEmployee(pstNotification.getInt(FLD_TARGET_EMPLOYEE));
            notification.setTime(pstNotification.getString(FLD_TIME));
            return notification;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstNotification(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(Notification notification) throws DBException {
        try {
            PstNotification pstNotification = new PstNotification(0);

            pstNotification.setInt(FLD_MODUL_NAME, notification.getModulName());
            pstNotification.setString(FLD_TYPE, notification.getType());
            pstNotification.setString(FLD_TIME_DISTANCE, notification.getTimeDistance());
            pstNotification.setString(FLD_SUBJECT, notification.getSubject());
            pstNotification.setString(FLD_TEXT, notification.getText());
            pstNotification.setDate(FLD_DATETIME, notification.getDateTime());
            pstNotification.setInt(FLD_STATUS, notification.getStatus());
            pstNotification.setInt(FLD_TARGET_EMPLOYEE, notification.getTargetEmployee());
            pstNotification.setString(FLD_TIME, notification.getTime());
            
            pstNotification.insert();
            notification.setOID(pstNotification.getlong(FLD_NOTIFICATION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstNotification(0), DBException.UNKNOWN);
        }
        return notification.getOID();
    }

    public static long updateExc(Notification notification) throws DBException {
        try {
            if (notification.getOID() != 0) {
                PstNotification pstNotification = new PstNotification(notification.getOID());
                pstNotification.setInt(FLD_MODUL_NAME, notification.getModulName());
                pstNotification.setString(FLD_TYPE, notification.getType());
                pstNotification.setString(FLD_TIME_DISTANCE, notification.getTimeDistance());
                pstNotification.setString(FLD_SUBJECT, notification.getSubject());
                pstNotification.setString(FLD_TEXT, notification.getText());
                pstNotification.setDate(FLD_DATETIME, notification.getDateTime());
                pstNotification.setInt(FLD_STATUS, notification.getStatus());
                pstNotification.setInt(FLD_TARGET_EMPLOYEE, notification.getTargetEmployee());
                pstNotification.setString(FLD_TIME, notification.getTime());

                pstNotification.update();
                return notification.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstNotification(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstNotification pstNotification = new PstNotification(oid);
            pstNotification.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstNotification(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_NOTIFICATION;
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

            //System.out.println("sql xxxxx " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Notification notification = new Notification();
                resultToObject(rs, notification);
                lists.add(notification);
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

    public static void resultToObject(ResultSet rs, Notification notification) {
        try {
            notification.setOID(rs.getLong(PstNotification.fieldNames[PstNotification.FLD_NOTIFICATION_ID]));
            notification.setModulName(rs.getInt(PstNotification.fieldNames[PstNotification.FLD_MODUL_NAME]));
            notification.setType(rs.getString(PstNotification.fieldNames[PstNotification.FLD_TYPE]));
            notification.setTimeDistance(rs.getString(PstNotification.fieldNames[PstNotification.FLD_TIME_DISTANCE]));
            notification.setSubject(rs.getString(PstNotification.fieldNames[PstNotification.FLD_SUBJECT]));
            notification.setText(rs.getString(PstNotification.fieldNames[PstNotification.FLD_TEXT]));
            notification.setDateTime(rs.getDate(PstNotification.fieldNames[PstNotification.FLD_DATETIME]));
            notification.setStatus(rs.getInt(PstNotification.fieldNames[PstNotification.FLD_STATUS]));
            notification.setTargetEmployee(rs.getInt(PstNotification.fieldNames[PstNotification.FLD_TARGET_EMPLOYEE]));
            notification.setTime(rs.getString(PstNotification.fieldNames[PstNotification.FLD_TIME]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long positionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_NOTIFICATION + " WHERE "
                    + PstNotification.fieldNames[PstNotification.FLD_NOTIFICATION_ID] + " = " + positionId;

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
            String sql = "SELECT COUNT(" + PstNotification.fieldNames[PstNotification.FLD_NOTIFICATION_ID] + ") FROM " + TBL_HR_NOTIFICATION;
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
                    Notification notification = (Notification) list.get(ls);
                    if (oid == notification.getOID()) {
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
    

}

