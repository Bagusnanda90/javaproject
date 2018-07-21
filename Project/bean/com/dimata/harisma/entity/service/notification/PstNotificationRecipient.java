/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.service.notification;

import com.dimata.harisma.entity.service.notification.NotificationRecipient;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class PstNotificationRecipient extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_NOTIFICATION_RECIPIENT = "hr_notification_recipient";
    public static final int FLD_NOTIFICATION_RECIPIENT_ID = 0;
    public static final int FLD_RECIPIENT_EMAIL = 1;
    public static final int FLD_NOTIFICATION_ID = 2;
    public static final int FLD_RECIPIENT_AS = 3;
    public static final int FLD_FROM_ADD = 4;
    public static final int FLD_RECIPIENT_SMS = 5;
    public static final String[] fieldNames = {
        "NOTIFICATION_RECIPIENT_ID",
        "RECIPIENT_EMAIL",
        "NOTIFICATION_ID",
        "RECIPIENT_AS",
        "FROM_ADD",
        "RECIPIENT_SMS"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING
    };
 
   
    public PstNotificationRecipient() {
    }

    public PstNotificationRecipient(int i) throws DBException {
        super(new PstNotificationRecipient());
    }

    public PstNotificationRecipient(String sOid) throws DBException {
        super(new PstNotificationRecipient(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstNotificationRecipient(long lOid) throws DBException {
        super(new PstNotificationRecipient(0));
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
        return TBL_HR_NOTIFICATION_RECIPIENT;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstNotificationRecipient().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        NotificationRecipient notificationRecipient = fetchExc(ent.getOID());
        ent = (Entity) notificationRecipient;
        return notificationRecipient.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((NotificationRecipient) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((NotificationRecipient) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static NotificationRecipient fetchExc(long oid) throws DBException {
        try {
            NotificationRecipient notificationRecipient = new NotificationRecipient();
            PstNotificationRecipient pstNotificationRecipient = new PstNotificationRecipient(oid);
            notificationRecipient.setOID(oid);

            notificationRecipient.setRecipientsEmail(pstNotificationRecipient.getString(FLD_RECIPIENT_EMAIL));
            notificationRecipient.setNotificationId(pstNotificationRecipient.getLong(FLD_NOTIFICATION_ID));
            notificationRecipient.setRecipientAs(pstNotificationRecipient.getInt(FLD_RECIPIENT_AS));
            notificationRecipient.setFromAdd(pstNotificationRecipient.getInt(FLD_FROM_ADD));
            notificationRecipient.setRecipientsSms(pstNotificationRecipient.getString(FLD_RECIPIENT_SMS));
            return notificationRecipient;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstNotificationRecipient(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(NotificationRecipient notificationRecipient) throws DBException {
        try {
            PstNotificationRecipient pstNotificationRecipient = new PstNotificationRecipient(0);

            pstNotificationRecipient.setString(FLD_RECIPIENT_EMAIL, notificationRecipient.getRecipientsEmail());
            pstNotificationRecipient.setLong(FLD_NOTIFICATION_ID, notificationRecipient.getNotificationId());
            pstNotificationRecipient.setInt(FLD_RECIPIENT_AS, notificationRecipient.getRecipientAs());
            pstNotificationRecipient.setInt(FLD_FROM_ADD, notificationRecipient.getFromAdd());
            pstNotificationRecipient.setString(FLD_RECIPIENT_SMS, notificationRecipient.getRecipientsSms());
            
            pstNotificationRecipient.insert();
            notificationRecipient.setOID(pstNotificationRecipient.getlong(FLD_NOTIFICATION_RECIPIENT_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstNotificationRecipient(0), DBException.UNKNOWN);
        }
        return notificationRecipient.getOID();
    }

    public static long updateExc(NotificationRecipient notificationRecipient) throws DBException {
        try {
            if (notificationRecipient.getOID() != 0) {
                PstNotificationRecipient pstNotificationRecipient = new PstNotificationRecipient(notificationRecipient.getOID());
                
                pstNotificationRecipient.setLong(FLD_NOTIFICATION_RECIPIENT_ID, notificationRecipient.getNotificationRecipientId());
                pstNotificationRecipient.setString(FLD_RECIPIENT_EMAIL, notificationRecipient.getRecipientsEmail());
                pstNotificationRecipient.setLong(FLD_NOTIFICATION_ID, notificationRecipient.getNotificationId());
                pstNotificationRecipient.setInt(FLD_RECIPIENT_AS, notificationRecipient.getRecipientAs());
                pstNotificationRecipient.setInt(FLD_FROM_ADD, notificationRecipient.getFromAdd());
                pstNotificationRecipient.setString(FLD_RECIPIENT_SMS, notificationRecipient.getRecipientsEmail());

                pstNotificationRecipient.update();
                return notificationRecipient.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstNotificationRecipient(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstNotificationRecipient pstNotificationRecipient = new PstNotificationRecipient(oid);
            pstNotificationRecipient.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstNotificationRecipient(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_NOTIFICATION_RECIPIENT;
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
                NotificationRecipient notificationRecipient = new NotificationRecipient();
                resultToObject(rs, notificationRecipient);
                lists.add(notificationRecipient);
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
    
    
    public static Hashtable hListEmail(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable lists = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_NOTIFICATION_RECIPIENT;
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
                NotificationRecipient notificationRecipient = new NotificationRecipient();
                resultToObject(rs, notificationRecipient);
                lists.put(notificationRecipient.getRecipientsEmail(),notificationRecipient);
            }
            rs.close();
            return lists;

        } catch (Exception e) {

            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Hashtable();
    }
    
    public static String listStringEmail(int limitStart, int recordToGet, String whereClause, String order) {
        String lists = "";
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_NOTIFICATION_RECIPIENT;
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
                NotificationRecipient notificationRecipient = new NotificationRecipient();
                resultToObject(rs, notificationRecipient);
                lists += ""+notificationRecipient.getRecipientsEmail()+",";
                
            }
            lists = lists.substring(0,lists.length()-1);
            rs.close();
            return lists;

        } catch (Exception e) {

            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return "";
    }

    public static String listStringSms(int limitStart, int recordToGet, String whereClause, String order) {
        String lists = "";
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_NOTIFICATION_RECIPIENT;
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
                NotificationRecipient notificationRecipient = new NotificationRecipient();
                resultToObject(rs, notificationRecipient);
                lists += ""+notificationRecipient.getRecipientsSms()+",";
            }
            lists = lists.substring(0,lists.length()-1);
            rs.close();
            return lists;

        } catch (Exception e) {

            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return "";
    }

    public static void resultToObject(ResultSet rs, NotificationRecipient notificationRecipient) {
        try {
            notificationRecipient.setOID(rs.getLong(PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_RECIPIENT_ID]));
            notificationRecipient.setRecipientsEmail(rs.getString(PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_EMAIL]));
            notificationRecipient.setNotificationId(rs.getLong(PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_ID]));
            notificationRecipient.setRecipientAs(rs.getInt(PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_AS]));
            notificationRecipient.setFromAdd(rs.getInt(PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_FROM_ADD]));
            notificationRecipient.setRecipientsSms(rs.getString(PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_SMS]));
         
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long oid) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_NOTIFICATION_RECIPIENT + " WHERE "
                    + PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_RECIPIENT_ID] + " = " + oid;

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
            String sql = "SELECT COUNT(" + PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_RECIPIENT_ID] + ") FROM " + TBL_HR_NOTIFICATION_RECIPIENT;
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
                    NotificationRecipient notificationRecipient = (NotificationRecipient) list.get(ls);
                    if (oid == notificationRecipient.getOID()) {
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
    
      public static long deleteRecipient(int as, long notificationId) {
        DBResultSet dbrs = null;
        long resulthasil =0;
        try {
            String sql = "DELETE  FROM " + TBL_HR_NOTIFICATION_RECIPIENT + " WHERE "
                    + PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_RECIPIENT_AS] + " = " + as + " AND "
                    + PstNotificationRecipient.fieldNames[PstNotificationRecipient.FLD_NOTIFICATION_ID] + " = " + notificationId ;
            
            DBHandler.execSqlInsert(sql);
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
            
        } finally {
            DBResultSet.close(dbrs);
            return resulthasil;
        }
    }
}

