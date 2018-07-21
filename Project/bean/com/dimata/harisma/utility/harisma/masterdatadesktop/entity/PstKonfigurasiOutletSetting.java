/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.masterdatadesktop.entity;

import com.dimata.harisma.session.aplikasidesktop.attendance.SessAplicationDestopAbsensiAttendance;
import com.dimata.harisma.utility.harisma.machine.db.DBException;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.db.DBResultSet;
import com.dimata.harisma.utility.harisma.machine.db.I_DBInterface;
import com.dimata.harisma.utility.harisma.machine.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class PstKonfigurasiOutletSetting extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_KONFIGURATION_OUTLET_SETTING = "konfigurasi_outlet";//"HR_EDUCATION";
    public static final int FLD_KONFIGURASI_ID = 0;
    public static final int FLD_URL_IMAGES = 1;
    public static final int FLD_URL_IMAGES_BUTTON_ICON = 2;
    public static final int FLD_URL_LETAK_MYSQL_EXE = 3;
    public static final int FLD_USERNAME_MYSQL = 4;
    public static final int FLD_PASSWORD_MYSQL = 5;
    public static final int FLD_PORT_N_DB_NAME = 6;
    public static final int FLD_OID_POSITION = 7;
    public static final int FLD_TAMBAHAN_TIME_BOLEH_ABSEN = 8;
    public static final int FLD_AUTODOWNLOAD = 9; //priska
    
    public static final String[] fieldNames = {
        "KONFIGURASI_ID",
        "URL_IMAGES",
        "URL_IMAGES_BUTTON_ICON",
        "URL_LETAK_MYSQL_EXE",
        "USERNAME_MYSQL",
        "PASSWORD_MYSQL",
        "DSN_MYSQL",
        "OID_POSITION"    ,
        "TAMBAHAN_TIME_BOLEH_ABSEN",
        "AUTODOWNLOAD"
    };
    public static final int[] fieldTypes = {
        //TYPE_LONG + TYPE_PK + TYPE_ID,
        //TYPE_STRING,
        TYPE_LONG + TYPE_PK + TYPE_ID,// "KONFIGURASI_ID",
        TYPE_STRING,//"URL_IMAGES",
        TYPE_STRING,//"URL_IMAGES_BUTTON_ICON",
        TYPE_STRING,//"URL_LETAK_MYSQL_EXE",
        TYPE_STRING,//"USERNAME_MYSQL",
        TYPE_STRING,//"PASSWORD_MYSQL",
        TYPE_STRING,//"DSN_MYSQL",
        TYPE_STRING,
        TYPE_INT, //TAMBAHAN_TIME_BOLEH_ABSEN
        TYPE_STRING
    };

    public PstKonfigurasiOutletSetting() {
    }

    public PstKonfigurasiOutletSetting(int i) throws DBException {
        super(new PstKonfigurasiOutletSetting());
    }

    public PstKonfigurasiOutletSetting(String sOid) throws DBException {
        super(new PstKonfigurasiOutletSetting(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstKonfigurasiOutletSetting(long lOid) throws DBException {
        super(new PstKonfigurasiOutletSetting(0));
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
        return TBL_KONFIGURATION_OUTLET_SETTING;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstKonfigurasiOutletSetting().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        KonfigurasiMasterOutletSetting konfigurasiMasterOutletSetting = fetchExc(ent.getOID());
        ent = (Entity) konfigurasiMasterOutletSetting;
        return konfigurasiMasterOutletSetting.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((KonfigurasiMasterOutletSetting) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((KonfigurasiMasterOutletSetting) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static KonfigurasiMasterOutletSetting fetchExc(long oid) throws DBException {
        try {
            KonfigurasiMasterOutletSetting konfigurasiMasterOutletSetting = new KonfigurasiMasterOutletSetting();
            PstKonfigurasiOutletSetting pstKonfigurasiOutletSetting = new PstKonfigurasiOutletSetting(oid);
            konfigurasiMasterOutletSetting.setOID(oid);

            konfigurasiMasterOutletSetting.setUrlImages(pstKonfigurasiOutletSetting.getString(FLD_URL_IMAGES));
            konfigurasiMasterOutletSetting.setUrlImagesIcon(pstKonfigurasiOutletSetting.getString(FLD_URL_IMAGES_BUTTON_ICON));
            konfigurasiMasterOutletSetting.setPortMysqlnDbName(pstKonfigurasiOutletSetting.getString(FLD_PORT_N_DB_NAME));
            konfigurasiMasterOutletSetting.setPasswordMysql(pstKonfigurasiOutletSetting.getString(FLD_PASSWORD_MYSQL));
            konfigurasiMasterOutletSetting.setUrlLetakMysqlExe(pstKonfigurasiOutletSetting.getString(FLD_URL_LETAK_MYSQL_EXE));
            konfigurasiMasterOutletSetting.setUsernameMysql(pstKonfigurasiOutletSetting.getString(FLD_USERNAME_MYSQL));
            konfigurasiMasterOutletSetting.setOidPositionKonfig(pstKonfigurasiOutletSetting.getString(FLD_OID_POSITION));
            konfigurasiMasterOutletSetting.setTambahanBolehAbs(pstKonfigurasiOutletSetting.getInt(FLD_TAMBAHAN_TIME_BOLEH_ABSEN));
            konfigurasiMasterOutletSetting.setAutoStart(pstKonfigurasiOutletSetting.getString(FLD_AUTODOWNLOAD));
            
            return konfigurasiMasterOutletSetting;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKonfigurasiOutletSetting(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(KonfigurasiMasterOutletSetting konfigurasiMasterOutletSetting) throws DBException {
        try {
            PstKonfigurasiOutletSetting pstKonfigurasiOutletSetting = new PstKonfigurasiOutletSetting(0);

            pstKonfigurasiOutletSetting.setString(FLD_PORT_N_DB_NAME, konfigurasiMasterOutletSetting.getPortMysqlnDbName());
            pstKonfigurasiOutletSetting.setString(FLD_PASSWORD_MYSQL, konfigurasiMasterOutletSetting.getPasswordMysql());
            pstKonfigurasiOutletSetting.setString(FLD_URL_LETAK_MYSQL_EXE, konfigurasiMasterOutletSetting.getUrlLetakMysqlExe());
            pstKonfigurasiOutletSetting.setString(FLD_USERNAME_MYSQL, konfigurasiMasterOutletSetting.getUsernameMysql());
            pstKonfigurasiOutletSetting.setString(FLD_URL_IMAGES, konfigurasiMasterOutletSetting.getUrlImages());
            pstKonfigurasiOutletSetting.setString(FLD_URL_IMAGES_BUTTON_ICON, konfigurasiMasterOutletSetting.getUrlImagesIcon());
            pstKonfigurasiOutletSetting.setString(FLD_OID_POSITION, konfigurasiMasterOutletSetting.getOidPositionKonfig());
            pstKonfigurasiOutletSetting.setInt(FLD_TAMBAHAN_TIME_BOLEH_ABSEN, konfigurasiMasterOutletSetting.getTambahanBolehAbs());
            pstKonfigurasiOutletSetting.setString(FLD_AUTODOWNLOAD, konfigurasiMasterOutletSetting.getAutoStart());
            
            pstKonfigurasiOutletSetting.insert();
            konfigurasiMasterOutletSetting.setOID(pstKonfigurasiOutletSetting.getlong(FLD_KONFIGURASI_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKonfigurasiOutletSetting(0), DBException.UNKNOWN);
        }
        return konfigurasiMasterOutletSetting.getOID();
    }

    public static long updateExc(KonfigurasiMasterOutletSetting konfigurasiMasterOutletSetting) throws DBException {
        try {
            if (konfigurasiMasterOutletSetting.getOID() != 0) {
                PstKonfigurasiOutletSetting pstKonfigurasiOutletSetting = new PstKonfigurasiOutletSetting(konfigurasiMasterOutletSetting.getOID());

                pstKonfigurasiOutletSetting.setString(FLD_PORT_N_DB_NAME, konfigurasiMasterOutletSetting.getPortMysqlnDbName());
                pstKonfigurasiOutletSetting.setString(FLD_PASSWORD_MYSQL, konfigurasiMasterOutletSetting.getPasswordMysql());
                pstKonfigurasiOutletSetting.setString(FLD_URL_LETAK_MYSQL_EXE, konfigurasiMasterOutletSetting.getUrlLetakMysqlExe());
                pstKonfigurasiOutletSetting.setString(FLD_USERNAME_MYSQL, konfigurasiMasterOutletSetting.getUsernameMysql());
                pstKonfigurasiOutletSetting.setString(FLD_URL_IMAGES, konfigurasiMasterOutletSetting.getUrlImages());
                pstKonfigurasiOutletSetting.setString(FLD_URL_IMAGES_BUTTON_ICON, konfigurasiMasterOutletSetting.getUrlImagesIcon());
                pstKonfigurasiOutletSetting.setString(FLD_OID_POSITION, konfigurasiMasterOutletSetting.getOidPositionKonfig());
                pstKonfigurasiOutletSetting.setInt(FLD_TAMBAHAN_TIME_BOLEH_ABSEN, konfigurasiMasterOutletSetting.getTambahanBolehAbs());
                pstKonfigurasiOutletSetting.setString(FLD_AUTODOWNLOAD, konfigurasiMasterOutletSetting.getAutoStart());
                
                pstKonfigurasiOutletSetting.update();
                return konfigurasiMasterOutletSetting.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKonfigurasiOutletSetting(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstKonfigurasiOutletSetting pstKonfigurasiOutletSetting = new PstKonfigurasiOutletSetting(oid);
            pstKonfigurasiOutletSetting.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstKonfigurasiOutletSetting(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_KONFIGURATION_OUTLET_SETTING;
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
                KonfigurasiMasterOutletSetting konfigurasiMasterOutletSetting = new KonfigurasiMasterOutletSetting();
                resultToObject(rs, konfigurasiMasterOutletSetting);
                lists.add(konfigurasiMasterOutletSetting);
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
    
    public static String oidPosition() {
       String oidPosition="0";
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_KONFIGURATION_OUTLET_SETTING;
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                oidPosition= rs.getString(fieldNames[FLD_OID_POSITION]);
            }
            rs.close();
           

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
             return oidPosition;
        }
    }
    
    
    
    public static SessAplicationDestopAbsensiAttendance getListSetting(int limitStart, int recordToGet, String whereClause, String order) {
        SessAplicationDestopAbsensiAttendance sessAplicationDestopAbsensiAttendance = new SessAplicationDestopAbsensiAttendance();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_KONFIGURATION_OUTLET_SETTING;
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
                KonfigurasiMasterOutletSetting konfigurasiMasterOutletSetting = new KonfigurasiMasterOutletSetting();
                resultToObject(rs, konfigurasiMasterOutletSetting);
                sessAplicationDestopAbsensiAttendance.setUrlImages(konfigurasiMasterOutletSetting.getUrlImages());
                sessAplicationDestopAbsensiAttendance.setUrlImagesIcon(konfigurasiMasterOutletSetting.getUrlImagesIcon());
                sessAplicationDestopAbsensiAttendance.setUrlLocationMysql(konfigurasiMasterOutletSetting.getUrlLetakMysqlExe());
                sessAplicationDestopAbsensiAttendance.setPortnDbName(konfigurasiMasterOutletSetting.getPortMysqlnDbName());
                sessAplicationDestopAbsensiAttendance.setUsernameMysql(konfigurasiMasterOutletSetting.getUsernameMysql());
                sessAplicationDestopAbsensiAttendance.setPasswordMysql(konfigurasiMasterOutletSetting.getPasswordMysql());
                sessAplicationDestopAbsensiAttendance.setOidSettingOutlet(konfigurasiMasterOutletSetting.getOID());
                sessAplicationDestopAbsensiAttendance.setTambahanBolehAbs(konfigurasiMasterOutletSetting.getTambahanBolehAbs());
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return sessAplicationDestopAbsensiAttendance;
        }
        
    }

    private static void resultToObject(ResultSet rs, KonfigurasiMasterOutletSetting konfigurasiMasterOutletSetting) {
        try {
            konfigurasiMasterOutletSetting.setOID(rs.getLong(PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_KONFIGURASI_ID]));
            
            konfigurasiMasterOutletSetting.setPortMysqlnDbName(rs.getString(PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_PORT_N_DB_NAME]));
            konfigurasiMasterOutletSetting.setPasswordMysql(rs.getString(PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_PASSWORD_MYSQL]));
            konfigurasiMasterOutletSetting.setUsernameMysql(rs.getString(PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_USERNAME_MYSQL]));
            konfigurasiMasterOutletSetting.setUrlLetakMysqlExe(rs.getString(PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_URL_LETAK_MYSQL_EXE]));
            
            konfigurasiMasterOutletSetting.setUrlImages(rs.getString(PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_URL_IMAGES]));
            konfigurasiMasterOutletSetting.setUrlImagesIcon(rs.getString(PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_URL_IMAGES_BUTTON_ICON]));
            konfigurasiMasterOutletSetting.setOidPositionKonfig(rs.getString(PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_OID_POSITION]));
            konfigurasiMasterOutletSetting.setTambahanBolehAbs(rs.getInt(PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_TAMBAHAN_TIME_BOLEH_ABSEN]));
            konfigurasiMasterOutletSetting.setAutoStart(rs.getString(PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_AUTODOWNLOAD]));
            
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long konfigurasiMasterOutletSettingId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_KONFIGURATION_OUTLET_SETTING + " WHERE "
                    + PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_KONFIGURASI_ID] + " = " + konfigurasiMasterOutletSettingId;

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
            String sql = "SELECT COUNT(" + PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_KONFIGURASI_ID] + ") FROM " + TBL_KONFIGURATION_OUTLET_SETTING;
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
    
    public static long getOid(String whereClause,int limitStart,int recordToGet) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_KONFIGURASI_ID] + " FROM " + TBL_KONFIGURATION_OUTLET_SETTING;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            long count = 0;
            while (rs.next()) {
                count = rs.getLong(1);
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
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    KonfigurasiMasterOutletSetting konfigurasiMasterOutletSetting = (KonfigurasiMasterOutletSetting) list.get(ls);
                    if (oid == konfigurasiMasterOutletSetting.getOID()) {
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
    /* This method used to find command where current data */

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + (recordToGet - mdl);
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

//priska 20150701 chek jam    
    public static String getAutoStart(String whereClause,int limitStart,int recordToGet) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstKonfigurasiOutletSetting.fieldNames[PstKonfigurasiOutletSetting.FLD_AUTODOWNLOAD] + " FROM " + TBL_KONFIGURATION_OUTLET_SETTING;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            String count = "";
            while (rs.next()) {
                count = rs.getString(1);
            }

            rs.close();
            return count;
        } catch (Exception e) {
            return null;
        } finally {
            DBResultSet.close(dbrs);
        }
    }


    public static boolean checkMaster(long oid) {
        if (PstKonfigurasiOutletSetting.checkMaster(oid)) {
            return true;
        } else {
            return false;
        }
    }
}
