
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

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
/*import com.dimata.harisma.entity.masterdata.*;*/
/*import com.dimata.harisma.entity.employee.*;*/

public class PstLogPasalKhusus extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_LOG_PASAL_KHUSUS = "log_pasal_khusus";//"HR_SECTION";
    public static final int FLD_PASAL_KHUSUS_ID = 0;
    public static final int FLD_PASAL_UMUM_ID = 1;
    public static final int FLD_PASAL_KHUSUS = 2;
    public static final String[] fieldNames = {
        "PASAL_KHUSUS_ID",
        "PASAL_UMUM_ID",
        "PASAL_KHUSUS"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING
    };

    public PstLogPasalKhusus() {
    }

    public PstLogPasalKhusus(int i) throws DBException {
        super(new PstLogPasalKhusus());
    }

    public PstLogPasalKhusus(String sOid) throws DBException {
        super(new PstLogPasalKhusus(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstLogPasalKhusus(long lOid) throws DBException {
        super(new PstLogPasalKhusus(0));
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
        return TBL_LOG_PASAL_KHUSUS;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstLogPasalKhusus().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        LogPasalKhusus logPasalKhusus = fetchExc(ent.getOID());
        ent = (Entity) logPasalKhusus;
        return logPasalKhusus.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((LogPasalKhusus) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((LogPasalKhusus) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static LogPasalKhusus fetchExc(long oid) throws DBException {
        try {
            LogPasalKhusus logPasalKhusus = new LogPasalKhusus();
            PstLogPasalKhusus pstLogPasalKhusus = new PstLogPasalKhusus(oid);
            logPasalKhusus.setOID(oid);

            logPasalKhusus.setPasalUmumId(pstLogPasalKhusus.getlong(FLD_PASAL_UMUM_ID));
            logPasalKhusus.setPasalKhusus(pstLogPasalKhusus.getString(FLD_PASAL_KHUSUS));

            return logPasalKhusus;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLogPasalKhusus(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(LogPasalKhusus logPasalKhusus) throws DBException {
        try {
            PstLogPasalKhusus pstLogPasalKhusus = new PstLogPasalKhusus(0);

            pstLogPasalKhusus.setLong(FLD_PASAL_UMUM_ID, logPasalKhusus.getPasalUmumId());
            pstLogPasalKhusus.setString(FLD_PASAL_KHUSUS, logPasalKhusus.getPasalKhusus());


            pstLogPasalKhusus.insert();
            logPasalKhusus.setOID(pstLogPasalKhusus.getlong(FLD_PASAL_KHUSUS_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLogPasalKhusus(0), DBException.UNKNOWN);
        }
        return logPasalKhusus.getOID();
    }

    public static long updateExc(LogPasalKhusus logPasalKhusus) throws DBException {
        try {
            if (logPasalKhusus.getOID() != 0) {
                PstLogPasalKhusus pstLogPasalKhusus = new PstLogPasalKhusus(logPasalKhusus.getOID());
                pstLogPasalKhusus.setLong(FLD_PASAL_UMUM_ID, logPasalKhusus.getPasalUmumId());
                pstLogPasalKhusus.setString(FLD_PASAL_KHUSUS, logPasalKhusus.getPasalKhusus());
                pstLogPasalKhusus.update();
                return logPasalKhusus.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLogPasalKhusus(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstLogPasalKhusus pstLogPasalKhusus = new PstLogPasalKhusus(oid);
            pstLogPasalKhusus.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLogPasalKhusus(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_LOG_PASAL_KHUSUS;
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
//                        System.out.println("sql logPasalKhusus : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                LogPasalKhusus logPasalKhusus = new LogPasalKhusus();
                resultToObject(rs, logPasalKhusus);
                lists.add(logPasalKhusus);
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

    private static void resultToObject(ResultSet rs, LogPasalKhusus logPasalKhusus) {
        try {
            logPasalKhusus.setOID(rs.getLong(PstLogPasalKhusus.fieldNames[PstLogPasalKhusus.FLD_PASAL_KHUSUS_ID]));
            logPasalKhusus.setPasalUmumId(rs.getLong(PstLogPasalKhusus.fieldNames[PstLogPasalKhusus.FLD_PASAL_UMUM_ID]));
            logPasalKhusus.setPasalKhusus(rs.getString(PstLogPasalKhusus.fieldNames[PstLogPasalKhusus.FLD_PASAL_KHUSUS]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long pasalKhusus) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_LOG_PASAL_KHUSUS + " WHERE " +
                    PstLogPasalKhusus.fieldNames[PstLogPasalKhusus.FLD_PASAL_KHUSUS_ID] + " = " + pasalKhusus;

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
            String sql = "SELECT COUNT(" + PstLogPasalKhusus.fieldNames[PstLogPasalKhusus.FLD_PASAL_KHUSUS_ID] + ") FROM " + TBL_LOG_PASAL_KHUSUS;
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
                    LogPasalKhusus logPasalKhusus = (LogPasalKhusus) list.get(ls);
                    if (oid == logPasalKhusus.getOID()) {
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

    public static boolean checkLogPasalKhusus(long pasalUmumId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_LOG_PASAL_KHUSUS + " WHERE " +
                    PstLogPasalKhusus.fieldNames[PstLogPasalKhusus.FLD_PASAL_UMUM_ID] + " = " + pasalUmumId;

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
