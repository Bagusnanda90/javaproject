
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
package com.dimata.harisma.utility.service.bkpdtoutlet;

/* package java */
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.entity.*;

//Gede_7Feb2012 {
//}

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.employee.*;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Ari_20111002 Menambah Company, Division, Level dan EmpCategory
 *
 * @author Wiweka
 */
public class PstBakupCareerPathDestopTransfer extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    private static String tgl, bln, thn, buln = "";
    private static int tg, bl, th;            //tgl sekarang
    private static int tgL, blL, thL;        //tgl lahir
    private static int h, b, t;            //usia
    public static final String TBL_HR_WORK_HISTORY_NOW = "hr_work_history_now";//"HR_WORK_HISTORY_NOW";
    public static final int FLD_WORK_HISTORY_NOW_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final int FLD_COMPANY_ID = 2;
    public static final int FLD_COMPANY = 3;
    public static final int FLD_DEPARTMENT_ID = 4;
    public static final int FLD_DEPARTMENT = 5;
    public static final int FLD_POSITION_ID = 6;
    public static final int FLD_POSITION = 7;
    public static final int FLD_SECTION_ID = 8;
    public static final int FLD_SECTION = 9;
    public static final int FLD_DIVISION_ID = 10;
    public static final int FLD_DIVISION = 11;
    public static final int FLD_LEVEL_ID = 12;
    public static final int FLD_LEVEL = 13;
    public static final int FLD_EMP_CATEGORY_ID = 14;
    public static final int FLD_EMP_CATEGORY = 15;
    public static final int FLD_WORK_FROM = 16;
    public static final int FLD_WORK_TO = 17;
    public static final int FLD_DESCRIPTION = 18;
    public static final int FLD_SALARY = 19;
    public static final String[] fieldNames = {
        "WORK_HISTORY_NOW_ID",
        "EMPLOYEE_ID",
        "COMPANY_ID",
        "COMPANY",
        "DEPARTMENT_ID",
        "DEPARTMENT",
        "POSITION_ID",
        "POSITION",
        "SECTION_ID",
        "SECTION",
        "DIVISION_ID",
        "DIVISION",
        "LEVEL_ID",
        "LEVEL",
        "EMP_CATEGORY_ID",
        "EMP_CATEGORY",
        "WORK_FROM",
        "WORK_TO",
        "DESCRIPTION",
        "SALARY"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_FLOAT
    };

    public PstBakupCareerPathDestopTransfer() {
    }

    public PstBakupCareerPathDestopTransfer(int i) throws DBException {
        super(new PstBakupCareerPathDestopTransfer());
    }

    public PstBakupCareerPathDestopTransfer(String sOid) throws DBException {
        super(new PstBakupCareerPathDestopTransfer(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstBakupCareerPathDestopTransfer(long lOid) throws DBException {
        super(new PstBakupCareerPathDestopTransfer(0));
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
        return TBL_HR_WORK_HISTORY_NOW;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstBakupCareerPathDestopTransfer().getClass().getName();
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

    private static void resultToObject(ResultSet rs, CareerPath careerPath) {
        try {
            careerPath.setOID(rs.getLong("whn."+fieldNames[FLD_WORK_HISTORY_NOW_ID]));

            careerPath.setEmployeeId(rs.getLong("whn."+fieldNames[FLD_EMPLOYEE_ID])); 
            careerPath.setWorkFrom(rs.getDate(fieldNames[FLD_WORK_FROM]));
            careerPath.setWorkTo(rs.getDate(fieldNames[FLD_WORK_TO]));

        } catch (Exception e) {
        }
    }

    public static String insertExc(TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData) throws DBException {
        String sql="";
        try {
            PstBakupCareerPathDestopTransfer pstCareerPath = new PstBakupCareerPathDestopTransfer(0);

            pstCareerPath.setLong(FLD_EMPLOYEE_ID, tabelEmployeeOutletTransferData.getEmployeeId());
            pstCareerPath.setLong(FLD_EMP_CATEGORY_ID, tabelEmployeeOutletTransferData.getEmpCategoryId());
            pstCareerPath.setDate(FLD_WORK_FROM, tabelEmployeeOutletTransferData.getWorkHistoryDateStart());
            pstCareerPath.setDate(FLD_WORK_TO, tabelEmployeeOutletTransferData.getWorkHistoryDateEnd());

            //pstCareerPath.insert(tabelEmployeeOutletTransferData.getWorkHistoryId());
            sql=pstCareerPath.SyntacInsert(tabelEmployeeOutletTransferData.getWorkHistoryId());
        } catch (Exception e) {
            return sql;
        }
        return sql;
    }

    public static String updateExc(TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData) throws DBException {
        String sql="";
        try {
            if (tabelEmployeeOutletTransferData.getWorkHistoryId() != 0) {
                PstBakupCareerPathDestopTransfer pstCareerPath = new PstBakupCareerPathDestopTransfer(tabelEmployeeOutletTransferData.getWorkHistoryId());


                pstCareerPath.setLong(FLD_EMPLOYEE_ID, tabelEmployeeOutletTransferData.getEmployeeId());
                pstCareerPath.setLong(FLD_EMP_CATEGORY_ID, tabelEmployeeOutletTransferData.getEmpCategoryId());
                pstCareerPath.setDate(FLD_WORK_FROM, tabelEmployeeOutletTransferData.getWorkHistoryDateStart());
                pstCareerPath.setDate(FLD_WORK_TO, tabelEmployeeOutletTransferData.getWorkHistoryDateEnd());


                //pstCareerPath.update();
                 sql=pstCareerPath.SyntacUpdate();
            }
        }catch (Exception e) {
            return sql;
        }
        return sql;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstBakupCareerPathDestopTransfer pstCareerPath = new PstBakupCareerPathDestopTransfer(oid);
            pstCareerPath.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBakupCareerPathDestopTransfer(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static boolean checkOID(long workHistoryNowId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_WORK_HISTORY_NOW + " WHERE "
                    + PstBakupCareerPathDestopTransfer.fieldNames[PstBakupCareerPathDestopTransfer.FLD_WORK_HISTORY_NOW_ID] + " = " + workHistoryNowId;

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

//     public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
//        Vector lists = new Vector();
//        DBResultSet dbrs = null;
//        try {
//            String sql = "SELECT * FROM " + TBL_KONFIGURATION_OUTLET_SETTING;
//            if (whereClause != null && whereClause.length() > 0) {
//                sql = sql + " WHERE " + whereClause;
//            }
//            if (order != null && order.length() > 0) {
//                sql = sql + " ORDER BY " + order;
//            }
//            if (limitStart == 0 && recordToGet == 0) {
//                sql = sql + "";
//            } else {
//                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
//            }
//            dbrs = DBHandler.execQueryResult(sql);
//            ResultSet rs = dbrs.getResultSet();
//            while (rs.next()) {
//                KonfigurasiMasterOutletSetting konfigurasiMasterOutletSetting = new KonfigurasiMasterOutletSetting();
//                resultToObject(rs, konfigurasiMasterOutletSetting);
//                lists.add(konfigurasiMasterOutletSetting);
//            }
//            rs.close();
//            return lists;
//
//        } catch (Exception e) {
//            System.out.println(e);
//        } finally {
//            DBResultSet.close(dbrs);
//        }
//        return new Vector();
//    }
    public static Hashtable hashExpiredWorkingDays(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashExpiredWorkingDays = new Hashtable();
        DBResultSet dbrs = null;
        try {
           String sql = "SELECT whn.*,emp."+PstEmployeeDesktopTransfer.fieldNames[PstEmployeeDesktopTransfer.FLD_EMPLOYEE_NUM]+" FROM " + TBL_HR_WORK_HISTORY_NOW + " AS whn"
                   + " INNER JOIN "+PstEmployeeDesktopTransfer.TBL_HR_EMPLOYEE  + " AS emp ON emp."+PstEmployeeDesktopTransfer.fieldNames[PstEmployeeDesktopTransfer.FLD_EMPLOYEE_ID]+"=whn."+fieldNames[FLD_EMPLOYEE_ID];
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            sql = sql + " GROUP BY whn." + fieldNames[FLD_EMPLOYEE_ID];
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
                CareerPath careerPath = new CareerPath();
                resultToObject(rs, careerPath);

                String expirednya = "";
               // hashExpiredWorkingDays.put(careerPath.getEmployeeId(), expirednya);
                Date dtNew = new Date();
                if(careerPath.getWorkTo().getTime()>dtNew.getTime()){
                  expirednya =  " kurang dari "+  setDtExpiredKerja(Formater.formatDate(careerPath.getWorkTo(), "ddMMyyyy"),dtNew) + " ("+Formater.formatDate(careerPath.getWorkTo(), " dd/MM-yyyy") +")";
                }else{
                    expirednya = " sudah expired sejak "+setDtExpiredKerja(Formater.formatDate(dtNew, "ddMMyyyy"),careerPath.getWorkTo()) + " ("+Formater.formatDate(careerPath.getWorkTo(), " dd/MM-yyyy") +")";
                }
                hashExpiredWorkingDays.put(rs.getString("emp."+PstEmployeeDesktopTransfer.fieldNames[PstEmployeeDesktopTransfer.FLD_EMPLOYEE_NUM]), expirednya);
                
            }
            rs.close();


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashExpiredWorkingDays;
        }

    }

    public static void setTglLahir(int h, int b, int t) {
        tgL = h;
        blL = b;
        thL = t;
    }

    public static void hitungUsia() {
        h = tg - tgL;
        b = bl - blL;
        t = th - thL;
        if (b < 0) {
            bl += 12;
            t -= 1;
            b = bl - blL;
        }
        if (h < 0) {
            b -= 1;
            bl -= 1;
            if (bl == 2 && t % 4 == 0) {
                h = (tg + 29) - tgL;
            } else if (bl == 2 && t % 4 != 0) {
                h = (tg + 28) - tgL;
            } else if (bl == 1 || bl == 3 || bl == 5 || bl == 7 || bl == 8 || bl == 10 || bl == 12) {
                h = (tg + 31) - tgL;
            } else {
                h = (tg + 30) - tgL;
            }
        }
        //System.out.print("hari:" + h + "bulan:" + b + "tahun:" + t);
    }

    /**
     * 
     * @param end
     * @param Start
     * @return 
     */
    public static String setDtExpiredKerja(String end,Date Start) {
        String expired = "";
        try {
            //new Test().MakeBackup();
            //new Test().test();
            DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            //Calendar cal = Calendar.getInstance();
             long time = new SimpleDateFormat("ddMMyyyy").parse(end).getTime();
            String ab = dateFormat.format(time);
            char a[] = new char[8];
            for (int i = 0; i < 8; i++) {
                a[i] = ab.charAt(i);
            }
            tgl = "" + a[0] + a[1];
            bln = "" + a[2] + a[3];
            thn = "" + a[4] + a[5] + a[6] + a[7];
            if (bln.equals("01")) {
                buln = "Januari";
            } else if (bln.equals("02")) {
                buln = "Februari";
            } else if (bln.equals("03")) {
                buln = "Maret";
            } else if (bln.equals("04")) {
                buln = "April";
            } else if (bln.equals("05")) {
                buln = "Mei";
            } else if (bln.equals("06")) {
                buln = "Juni";
            } else if (bln.equals("07")) {
                buln = "Juli";
            } else if (bln.equals("08")) {
                buln = "Agustus";
            } else if (bln.equals("09")) {
                buln = "September";
            } else if (bln.equals("10")) {
                buln = "Oktober";
            } else if (bln.equals("11")) {
                buln = "November";
            } else if (bln.equals("12")) {
                buln = "Desember";
            }
            tg = Integer.parseInt(tgl);
            bl = Integer.parseInt(bln);
            th = Integer.parseInt(thn);

            setTglLahir(Start.getDate(), Start.getMonth() + 1, Start.getYear() + 1900);
            hitungUsia();

            if (h != 0) {
                expired = h + " day ";
            }
            if (b != 0) {
                expired = expired + b + " month ";
            }
            if (t != 0) {
                expired = expired + t + " year ";
            }
            //System.out.print("hari:" + h + "bulan:" + b + "tahun:" + t);
        } catch (Exception exc) {
            System.out.println(exc);
        }
        return expired;
    }

    public static Hashtable<String, Boolean> hashWorkHistoryAda(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashWorkHistoryAda = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + fieldNames[FLD_WORK_HISTORY_NOW_ID] + " FROM " + TBL_HR_WORK_HISTORY_NOW;
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
                hashWorkHistoryAda.put("" + rs.getLong(fieldNames[FLD_WORK_HISTORY_NOW_ID]), true);
            }
            rs.close();


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashWorkHistoryAda;
        }

    }
}
