/*
 * PstPayComponent.java
 *
 * Created on April 2, 2007, 1:01 PM
 */
package com.dimata.harisma.entity.payroll;
/* package java */

import com.dimata.harisma.entity.arap.PstArApMain;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
//import com.dimata. harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.payroll.*;
//import com.dimata.harisma.entity.locker.*;

/**
 *
 * @author yunny
 */
public class PstPayComponent extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_PAY_COMPONENT = "pay_component";//"PAY_COMPONENT";

    public static final int FLD_COMPONENT_ID = 0;
    public static final int FLD_COMP_CODE = 1;
    public static final int FLD_COMP_TYPE = 2;
    public static final int FLD_SORT_IDX = 3;
    public static final int FLD_COMP_NAME = 4;
    public static final int FLD_YEAR_ACCUMLT = 5;
    public static final int FLD_PAY_PERIOD = 6;
    public static final int FLD_USED_IN_FORML = 7;
    public static final int FLD_TAX_ITEM = 8;
    public static final int FLD_TYPE_TUNJANGAN = 9;
    //update by satrya 2013-01-24
    public static final int FLD_PAYSLIP_GROUP_ID = 10;
    //update by satrya 20130206
    public static final int FLD_SHOW_PAYSLIP = 11;
    public static final int FLD_SHOW_IN_REPORTS = 12;
    public static final int FLD_PROPORSIONAL_CALCULATE = 13;
    //update by Kartika 20150802
    public static final int FLD_TAX_RPT_GROUP = 14;
    /* Update by Hendra Putu 20160412 */
    public static final int FLD_DECIMAL_FORMAT = 15;
    public static final int FLD_SHOW_LOS_REPORT = 16;

    public static final String[] fieldNames = {
        "COMPONENT_ID",
        "COMP_CODE",
        "COMP_TYPE",
        "SORT_IDX",
        "COMP_NAME",
        "YEAR_ACCUMLT",
        "PAY_PERIOD",
        "USED_IN_FORML",
        "TAX_ITEM",
        "TYPE_TUNJANGAN",
        //UPDATE BY SATRYA 2013-01-24
        "PAYSLIP_GROUP_ID",
        "SHOW_PAYSLIP",
        //priska 2015-03-12
        "SHOW_IN_REPORTS",
        "PROPORSIONAL_CALCULATE",
        //Kartika 2015-09-02
        "TAX_RPT_GROUP",
        /* Update by Hendra Putu 20160412 */
        "DECIMAL_FORMAT",
        "SHOW_LOS_REPORT"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        //update by satrya 2013-01-24
        TYPE_INT,
        //update by satrya 2013-0206
        TYPE_INT,
        //priska 2015-03-12
        TYPE_INT,
        TYPE_INT,
        //Kartika 2015-09-02
        TYPE_INT,
        TYPE_INT,
        TYPE_INT
    };

    // komponen gaji
    public static int TYPE_BLANK = 0;
    public static int TYPE_BENEFIT = 1;
    public static int TYPE_DEDUCTION = 2;

    public static String[] tpeComponent = {
        "", "Benefit", "Deduction"
    };

    // akumulasi tahunan
    public static int NO_AKUMULASI = 0;
    public static int YES_AKUMULASI = 1;

    public static String[] akTahunan = {
        "No", "Yes"
    };

    // default period
    public static int MINGGUAN = 0;
    public static int BULANAN = 1;
    public static int TAHUNAN = 2;

    public static String[] defPeriod = {
        "Weekly", "Monthly", "Yearly"
    };

    // used in formula
    public static int NO_USED = 0;
    public static int YES_USED = 1;

    public static String[] usedForm = {
        "No", "Yes"
    };

    // Tax Item
    public static int NO_TAX = 0;
    public static int GAJI = 1;
    public static int TUNJANGAN = 2;
    public static int BONUS_THR = 3;
    public static int POTONGAN_GAJI = 4;
       //untuk deduction
    //public static int BONUS_THR = 3;

    public static String[] taxItem = {
        "Non Tax Component", "Continue Salary", "Non Continue", "Yearly Bonus & THR", "Salary to Tax Deduction"
    };

    public static String[] taxItemDescription = {
        "Salary component will not included in tax calculation as well as tax paid", "Common salary component included in tax calculation", "Allowance,Overtime,etc Common salary component included in tax calculation", "Bonus & THR Common salary component included in tax calculation", "Deduction of salary to be calculated for tax"
    };

    // type tunjangan
    public static int NO_TUNJANGAN = 0;
    public static int YES_TUNJANGAN = 1;

    public static String[] tunjangan = {
        "No", "Yes"
    };

    // tax report group
    public final static int TAX_RPT_NONE = 0;
    //Penghasilan brutto
    public final static int TAX_RPT_GAJI = 1;
    public final static int TAX_RPT_TUNJ_PPH = 2;
    public final static int TAX_RPT_TUNJ_LAIN_LEMBUR = 3;
    public final static int TAX_RPT_HONOR = 4;
    public final static int TAX_RPT_PREMI_ASURANSI = 5;
    public final static int TAX_RPT_NATURA = 6;
    public final static int TAX_RPT_TANTIEM_BONUS_THR = 7;
    public final static int TAX_RPT_BIAYA_JABATAN_OR_PENSIUN = 8;
    public final static int TAX_RPT_IURAN_PENSIUN_THT_JHT = 9;   
    
    public final static String TAX_RPT_GROUP[] = {
        "-Non Tax-",
        //Penghasilan
        "Gaji/Pensiun atau THT/JHT", //1
        "Tunjangan PPh",
        "Tunjangan Lainnya, Uang Lembur Dsbnya.",
        "Honorarium dan imbalan sejenis", 
        "Premi asuransi yg dibayar pemberi kerja", //5
        "Natura & Kenikmatan lainnya",
        "Tantiem, Bonus, Gratifikasi, Jasa Produksi & THR",
        // pengurangan
        "Biaya Jabatan/Biaya Pensiun", // 8
        "Iuran Pensiun atau Iuran THT/JHT", //9
    };

    public final static int TAX_RPT_SELECT_INCOME = 1;
    public final static int TAX_RPT_SELECT_DEDUCTION = -1;
    public final static int TAX_RPT_SELECT_BOTH = 0;
    public final static int TAX_RPT_GROUP_SELECT[] = {
        TAX_RPT_SELECT_BOTH,
        //Penghasilan 
        TAX_RPT_SELECT_BOTH, //1
        TAX_RPT_SELECT_INCOME,
        TAX_RPT_SELECT_BOTH,
        TAX_RPT_SELECT_BOTH,
        TAX_RPT_SELECT_INCOME,
        TAX_RPT_SELECT_BOTH,
        TAX_RPT_SELECT_BOTH,
        // pengurangan
        TAX_RPT_SELECT_DEDUCTION,
        TAX_RPT_SELECT_DEDUCTION       
    };

    public static Vector<String> getTaxReportSelectString(int type) {
        Vector vct = new Vector();
        for (int i = 0; i < TAX_RPT_GROUP.length; i++) {
            if (TAX_RPT_GROUP_SELECT[i] == type || TAX_RPT_GROUP_SELECT[i]==TAX_RPT_SELECT_BOTH ) {
                vct.add(TAX_RPT_GROUP[i]);
            }
        }
        return vct;
    }

    public static Vector<String> getTaxReportSelectInt(int type) {
        Vector vct = new Vector();
        for (int i = 0; i < TAX_RPT_GROUP.length; i++) {
            if (TAX_RPT_GROUP_SELECT[i] == type || TAX_RPT_GROUP_SELECT[i]==TAX_RPT_SELECT_BOTH) {
                vct.add(""+i);
            }
        }
        return vct;
    }

    //update by satrya 2013-02-06
    /**
     * Keterangan: menampilkan componen di payslip
     */
    public static int NO_SHOW_PAYSLIP = 0;
    public static int YES_SHOW_PAYSLIP = 1;
    public static int YES_NOT_EQUALS_0 = 2;
    public static String[] showPayslip = {
        "No", "Yes", "Yes != 0"
    };

    //update by priska 2015-03-12
    /**
     * Keterangan: menampilkan componen di payslip
     */
    public static int NO_PROPORSIONAL = 0;
    public static int YES_PROPORSIONAL_BY_WORKDAYS = 1;
    public static int YES_PROPORSIONAL_BY_COMENCING_AND_RESIGNED = 2;
    public static String[] proporsional_calculate = {
        "No", "Yes by Workdays", "Yes by Commencing and resigned"
    };

    //update by priska 2014-12-30
    /**
     * Keterangan: menampilkan componen di report
     */
    public static int NO_SHOW_IN_REPORTS = 0;
    public static int YES_SHOW_IN_REPORTS = 1;
    public static int YES_NOT_EQUALS_0_IN_REPORTS = 2; // 2015-01-12 | Hendra McHen
    public static String[] showinReports = {
        "No", "Yes", "Yes != 0"
    };
    /* Update by Hendra Putu | 2016-04-12 */
    public static int TANPA_PEMBULATAN = 0;
    public static int SATU_ANGKA_BELAKANG_KOMA = 1;
    public static int DUA_ANGKA_BELAKANG_KOMA = 2; 
    public static int TIGA_ANGKA_BELAKANG_KOMA = 3;
    
    public static int SATUAN_TERDEKAT = 4;
    public static int PULUHAN_TERDEKAT = 5;
    public static int RATUSAN_TERDEKAT = 6;
    public static int RIBUAN_TERDEKAT = 7;
    
    public static String[] valueStringDecimalFormat = {
      "Tanpa Pembulatan",
      "Satu Angka Belakang Koma",
      "Dua Angka Belakang Koma",
      "Tiga Angka Belakang Koma",
      "Satuan Terdekat",
      "Puluhan Terdekat",
      "Ratusan Terdekat",
      "Ribuan Terdekat"
    };

    /**
     * Creates a new instance of PstPayComponent
     */
    public PstPayComponent() {
    }

    public PstPayComponent(int i) throws DBException {
        super(new PstPayComponent());
    }

    public PstPayComponent(String sOid) throws DBException {
        super(new PstPayComponent(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPayComponent(long lOid) throws DBException {
        super(new PstPayComponent(0));
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

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public long fetchExc(Entity ent) throws Exception {
        PayComponent payComponent = fetchExc(ent.getOID());
        ent = (Entity) payComponent;
        return payComponent.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((PayComponent) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((PayComponent) ent);
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPayComponent().getClass().getName();
    }

    public String getTableName() {
        return TBL_PAY_COMPONENT;
    }

    public static PayComponent fetchExc(long oid) throws DBException {
        try {

            PayComponent payComponent = new PayComponent();

            PstPayComponent pstPayComponent = new PstPayComponent(oid);
            payComponent.setOID(oid);
            payComponent.setCompCode(pstPayComponent.getString(FLD_COMP_CODE));
            payComponent.setCompType(pstPayComponent.getInt(FLD_COMP_TYPE));
            payComponent.setSortIdx(pstPayComponent.getInt(FLD_SORT_IDX));
            payComponent.setCompName(pstPayComponent.getString(FLD_COMP_NAME));
            payComponent.setYearAccumlt(pstPayComponent.getInt(FLD_YEAR_ACCUMLT));
            payComponent.setPayPeriod(pstPayComponent.getInt(FLD_PAY_PERIOD));
            payComponent.setUsedInForml(pstPayComponent.getInt(FLD_USED_IN_FORML));
            payComponent.setTaxItem(pstPayComponent.getInt(FLD_TAX_ITEM));
            payComponent.setTypeTunjangan(pstPayComponent.getInt(FLD_TYPE_TUNJANGAN));
            //update by satrya 2013-01-24
            payComponent.setPayslipGroupId(pstPayComponent.getlong(FLD_PAYSLIP_GROUP_ID));
            //update by satrya 20130206
            payComponent.setShowpayslip(pstPayComponent.getInt(FLD_SHOW_PAYSLIP));
            //update by priska 2014-12-30
            payComponent.setShowinreports(pstPayComponent.getInt(FLD_SHOW_IN_REPORTS));
            payComponent.setProporsionalCalculate(pstPayComponent.getInt(FLD_PROPORSIONAL_CALCULATE));
            //Kartika 2015-09-02
            payComponent.setTaxRptGroup(pstPayComponent.getInt(FLD_TAX_RPT_GROUP));
            payComponent.setDecimalFormat(pstPayComponent.getInt(FLD_DECIMAL_FORMAT));
            payComponent.setShowinlosreports(pstPayComponent.getInt(FLD_SHOW_LOS_REPORT));
            return payComponent;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayComponent(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(PayComponent payComponent) throws DBException {
        try {
            PstPayComponent pstPayComponent = new PstPayComponent(0);
            pstPayComponent.setString(FLD_COMP_CODE, payComponent.getCompCode());
            pstPayComponent.setInt(FLD_COMP_TYPE, payComponent.getCompType());
            pstPayComponent.setInt(FLD_SORT_IDX, payComponent.getSortIdx());
            pstPayComponent.setString(FLD_COMP_NAME, payComponent.getCompName());
            pstPayComponent.setInt(FLD_YEAR_ACCUMLT, payComponent.getYearAccumlt());
            pstPayComponent.setInt(FLD_PAY_PERIOD, payComponent.getPayPeriod());
            pstPayComponent.setInt(FLD_USED_IN_FORML, payComponent.getUsedInForml());
            pstPayComponent.setInt(FLD_TAX_ITEM, payComponent.getTaxItem());
            pstPayComponent.setInt(FLD_TYPE_TUNJANGAN, payComponent.getTypeTunjangan());
                        //pstPayExecutive.setString(FLD_EXECUTIVE_NAME, payExecutive.getExecutiveName());
            //update by satrya 2013-01-24
            pstPayComponent.setLong(FLD_PAYSLIP_GROUP_ID, payComponent.getPayslipGroupId());
            //update by satrya 20130206
            pstPayComponent.setInt(FLD_SHOW_PAYSLIP, payComponent.getShowpayslip());
            pstPayComponent.setInt(FLD_SHOW_IN_REPORTS, payComponent.getShowinreports());
            pstPayComponent.setInt(FLD_PROPORSIONAL_CALCULATE, payComponent.getProporsionalCalculate());
            //Kartika 2015-09-02
            pstPayComponent.setInt(FLD_TAX_RPT_GROUP, payComponent.getTaxRptGroup());
            pstPayComponent.setInt(FLD_DECIMAL_FORMAT, payComponent.getDecimalFormat());
            pstPayComponent.setInt(FLD_SHOW_LOS_REPORT, payComponent.getShowinlosreports());
            
            pstPayComponent.insert();
            payComponent.setOID(pstPayComponent.getlong(FLD_COMPONENT_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayComponent(0), DBException.UNKNOWN);
        }
        return payComponent.getOID();
    }

    public static long updateExc(PayComponent payComponent) throws DBException {
        try {
            if (payComponent.getOID() != 0) {
                PstPayComponent pstPayComponent = new PstPayComponent(payComponent.getOID());
                pstPayComponent.setString(FLD_COMP_CODE, payComponent.getCompCode());
                pstPayComponent.setInt(FLD_COMP_TYPE, payComponent.getCompType());
                pstPayComponent.setInt(FLD_SORT_IDX, payComponent.getSortIdx());
                pstPayComponent.setString(FLD_COMP_NAME, payComponent.getCompName());
                pstPayComponent.setInt(FLD_YEAR_ACCUMLT, payComponent.getYearAccumlt());
                pstPayComponent.setInt(FLD_PAY_PERIOD, payComponent.getPayPeriod());
                pstPayComponent.setInt(FLD_USED_IN_FORML, payComponent.getUsedInForml());
                pstPayComponent.setInt(FLD_TAX_ITEM, payComponent.getTaxItem());
                pstPayComponent.setInt(FLD_TYPE_TUNJANGAN, payComponent.getTypeTunjangan());
                /* pstPayExecutive.setString(FLD_EXECUTIVE_NAME, payExecutive.getExecutiveName());*/
                //update by satrya 2013-01-24
                pstPayComponent.setLong(FLD_PAYSLIP_GROUP_ID, payComponent.getPayslipGroupId());
                //update by satrya 20130206
                pstPayComponent.setInt(FLD_SHOW_PAYSLIP, payComponent.getShowpayslip());
                //update by priska 2014-12-30
                pstPayComponent.setInt(FLD_SHOW_IN_REPORTS, payComponent.getShowinreports());
                pstPayComponent.setInt(FLD_PROPORSIONAL_CALCULATE, payComponent.getProporsionalCalculate());
                //Kartika 2015-09-02
                pstPayComponent.setInt(FLD_TAX_RPT_GROUP, payComponent.getTaxRptGroup());
                pstPayComponent.setInt(FLD_DECIMAL_FORMAT, payComponent.getDecimalFormat());
                pstPayComponent.setInt(FLD_SHOW_LOS_REPORT, payComponent.getShowinlosreports());
                pstPayComponent.update();
                return payComponent.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayComponent(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPayComponent pstPayComponent = new PstPayComponent(oid);
            pstPayComponent.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPayComponent(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 1000, "", "");
    }

    public static Vector<PayComponent> list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_COMPONENT;
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
            //System.out.println("SQL LIST"+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PayComponent payComponent = new PayComponent();
                resultToObject(rs, payComponent);
                lists.add(payComponent);
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
    
     public static Hashtable<String, PayComponent> listHashtableKeyCompCode(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable<String, PayComponent> lists = new Hashtable<String, PayComponent>();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_COMPONENT;
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
            //System.out.println("SQL LIST"+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PayComponent payComponent = new PayComponent();
                resultToObject(rs, payComponent);
                lists.put(""+payComponent.getCompCode(), payComponent);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return null;
    }

    /**
     * create satrya 2013-01-24 Keterangan : list gabungan antara PaySLipGroup
     * dan Pay SLip COmponen
     *
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return
     */
    public static Vector listPaySlipGroup(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_COMPONENT + " AS PC "
                    + " left join " + PstPaySlipGroup.TBL_PAYSLIP_GROUP + "  as PPG on PC." + PstPayComponent.fieldNames[PstPayComponent.FLD_PAYSLIP_GROUP_ID]
                    + " = PPG." + PstPaySlipGroup.fieldNames[PstPaySlipGroup.FLD_PAYSLIP_GROUP_ID];
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
            //System.out.println("SQL LIST"+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PayComponent payComponent = new PayComponent();
                resultToObject(rs, payComponent);
                payComponent.setPaySlipGroupName(rs.getString(PstPaySlipGroup.fieldNames[PstPaySlipGroup.FLD_PAYSLIP_GROUP_NAME]));
                lists.add(payComponent);
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

    public static void resultToObject(ResultSet rs, PayComponent payComponent) {
        try {
            payComponent.setOID(rs.getLong(PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID]));
            payComponent.setCompCode(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]));
            payComponent.setCompType(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]));
            payComponent.setSortIdx(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_SORT_IDX]));
            payComponent.setCompName(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]));
            payComponent.setYearAccumlt(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_YEAR_ACCUMLT]));
            payComponent.setPayPeriod(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_PAY_PERIOD]));
            payComponent.setUsedInForml(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_USED_IN_FORML]));
            payComponent.setTaxItem(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM]));
            payComponent.setTypeTunjangan(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_TYPE_TUNJANGAN]));
            //update by satrya 2013-01-24
            payComponent.setPayslipGroupId(rs.getLong(PstPayComponent.fieldNames[PstPayComponent.FLD_PAYSLIP_GROUP_ID]));
            //update by satrya 20130206
            payComponent.setShowpayslip(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_PAYSLIP]));
            //update by satrya 2014-12-30
            payComponent.setShowinreports(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_IN_REPORTS]));
            payComponent.setProporsionalCalculate(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_PROPORSIONAL_CALCULATE]));
            //Kartika 2015-09-02
            payComponent.setTaxRptGroup(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP]));
            payComponent.setDecimalFormat(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_DECIMAL_FORMAT]));
            payComponent.setShowinlosreports(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_LOS_REPORT]));

        } catch (Exception e) {
            System.out.println("Exception PstPayCOmponen" + e);
        }
    }

    public static Vector<String> listCompString(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + fieldNames[FLD_COMP_CODE] + " FROM " + TBL_PAY_COMPONENT;
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
            //System.out.println("SQL LIST"+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                lists.add(rs.getString(1));
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

    public static boolean checkOID(long componentId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_COMPONENT + " WHERE "
                    + PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID] + " = '" + componentId + "'";

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
            String sql = "SELECT COUNT(" + PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID] + ") FROM " + TBL_PAY_COMPONENT;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            //System.out.println("SQL count"+sql);
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

    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    PayComponent payComponent = (PayComponent) list.get(ls);
                    if (oid == payComponent.getOID()) {
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

    // ------------------------ start added by yunny ---------------------------
    /**
     * this method used to get id of component by Take Home Pay created by yunny
     */
    public static long getIdName(String componentCode) {
        DBResultSet dbrs = null;
        long result = 0;
        try {
            String sql = "SELECT component_id FROM " + TBL_PAY_COMPONENT + " WHERE "
                    + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE] + " = '" + componentCode + "'";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("sql   "+sql);
            while (rs.next()) {
                result = rs.getLong(1);
            }
            rs.close();

        } catch (Exception e) {
            System.out.println("Error");
        }
        return result;
    }

    /**
     * create by satrya 2014-04-02 Keterangan: mencari Id berdasarkan componen
     * name
     *
     * @param componentName
     * @return
     */
    public static long getIdComonenName(String componentName) {
        DBResultSet dbrs = null;
        long result = 0;
        try {
            String sql = "SELECT component_id FROM " + TBL_PAY_COMPONENT + " WHERE "
                    + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME] + " = '" + componentName + "'";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("sql   "+sql);
            while (rs.next()) {
                result = rs.getLong(1);
            }
            rs.close();

        } catch (Exception e) {
            System.out.println("Error");
        }
        return result;
    }

    /**
     * this method used to get name of component salary created by Yunny
     */
    public static String getComponentName(String compCode) {
        DBResultSet dbrs = null;
        String result = "";
        try {
            String sql = "SELECT COMP_NAME FROM " + TBL_PAY_COMPONENT + " WHERE "
                    + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE] + " = '" + compCode.trim() + "' ";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("sql getComponentName  "+sql);
            while (rs.next()) {
                result = rs.getString(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        return result;
    }

    /**
     * this method used to get name of component salary created by Yunny
     */
    public static int getProporsional(String compCode) {
        DBResultSet dbrs = null;
        int result = 0;
        try {
            String sql = "SELECT " + PstPayComponent.fieldNames[PstPayComponent.FLD_PROPORSIONAL_CALCULATE] + " FROM " + TBL_PAY_COMPONENT + " WHERE "
                    + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE] + " = '" + compCode.trim() + "' ";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("sql getComponentName  "+sql);
            while (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        return result;
    }
    //mencari pph21 dalam setahun
     public static double getPPH21OneYears(long employeeId, long periodId) {
        DBResultSet dbrs = null;
        double result = 0;
        Vector periodList = PstPaySlip.getYearPeriodListToThisPeriod(periodId);
        String periodeIdOneYears = "";
        if (periodList.size() != 0){
        for (int x = 0 ; x<periodList.size() ; x++){
            PayPeriod payPeriod = (PayPeriod) periodList.get(x);
             periodeIdOneYears = periodeIdOneYears+ payPeriod.getOID()+",";
        }
        periodeIdOneYears =periodeIdOneYears.substring(0, periodeIdOneYears.length()-1);
        }
        try {
           // String sql = "SELECT psc.`COMP_VALUE` "
             //       + "FROM  " + PstPaySlipComp.TBL_PAY_SLIP_COMP + " psc "
               //     + "INNER JOIN "+ PstPaySlip.TBL_PAY_SLIP +" ps ON ps."+PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]+" = psc."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+" "
                 //   + "INNER JOIN "+ PstPayPeriod.TBL_HR_PAY_PERIOD +" pp ON pp."+PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD_ID]+" = ps."+PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]+" "
                   // + "WHERE ps."+PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]+" = "+employeeId+"   AND psc."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+" = \"PPH21\"   AND (pp.`START_DATE` > \"2014-12-30\" AND pp.`END_DATE` < \"2016-01-01\" ) ORDER BY pp.`START_DATE`";
            
            String sql = "SELECT SUM(psc.`COMP_VALUE`) "
                    + "FROM  " + PstPaySlipComp.TBL_PAY_SLIP_COMP + " psc "
                    + "INNER JOIN "+ PstPaySlip.TBL_PAY_SLIP +" ps ON ps."+PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]+" = psc."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+" "
                    + "WHERE ps."+PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]+" = "+employeeId+"   AND (psc."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+" = \"PPH21\" OR psc."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+" = \"DED58\")   AND ps."+PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]+" IN ("+periodeIdOneYears+") ";

            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("sql getComponentName  "+sql);
            while (rs.next()) {
                result = rs.getDouble(1);
            }
            if (result < 0){
                result = 0;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        return result;
    }

    /**
     * this method used to get name of component which must input manual created
     * by Yunny
     */
    /*public static String getManualComponent(){
     DBResultSet dbrs = null;
     String result = "";
     try{
     String sql = "SELECT COMP_NAME FROM " + TBL_PAY_COMPONENT + " as pay " + 
     " INNER JOIN "+PstSalaryLevelDetail.TBL_PAY_LEVEL_COM+ " as slip"+
     " ON pay."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
     " = slip."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE]+
     " WHERE slip."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]+
     " LIKE '%IN_%' ";
               
     dbrs = DBHandler.execQueryResult(sql);
     ResultSet rs = dbrs.getResultSet();
     System.out.println("sql getManualComponent  "+sql);
     while(rs.next()) { 
     result = rs.getString(1);}
     rs.close();
     }catch(Exception e){
     System.out.println("Error");
     }
     return result;
     }*/
    /**
     * this method used to get name of component which must input manual created
     * by Yunny
     */
    public static Vector getManualComponent(String whereClause) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT pay.COMP_CODE,pay.comp_name FROM " + TBL_PAY_COMPONENT + " as pay "
                    + " INNER JOIN " + PstSalaryLevelDetail.TBL_PAY_LEVEL_COM + " as slip"
                    + " ON pay." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]
                    + " = slip." + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            /* " WHERE slip."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]+
             " LIKE '%IN_%' ";*/

            System.out.println("sql getManualComponent  " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
               // System.out.println("SQL    "+compType+"-"+sql);
            //System.out.println("masuk:::::::::::: "+rs);
            while (rs.next()) {

                PayComponent payComponent = new PayComponent();
                payComponent.setCompCode(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]));
                payComponent.setCompName(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]));
                resultToObject(rs, payComponent);
                lists.add(payComponent);

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
     * Mencari component yang diinput dengan pattter IN_$$$$$$_$$$$$$$ created
     * by kartika
     */
    public static PayComponent getManualInputComponent(String payCompName) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            if (payCompName == null || payCompName.length() < 1) {
                return null;
            }

            //String payComp = "IN_"+(payCompName.trim()).replace(" ", "_");
            String payComp = "" + (payCompName.trim());

            String sql = "SELECT * FROM " + TBL_PAY_COMPONENT + " as pay " + //pay.COMP_CODE,pay.comp_name FROM " + TBL_PAY_COMPONENT + " as pay " +
                    " WHERE " + fieldNames[FLD_COMP_NAME] + "=\"" + payComp + "\"";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            PayComponent payComponent = new PayComponent();
            while (rs.next()) {
                payComponent.setCompCode(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]));
                payComponent.setCompName(payComponent.getCompName() + " " + rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]));
                resultToObject(rs, payComponent);
                lists.add(payComponent);
            }
            rs.close();
            return payComponent;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return null;
    }

    //addd by priska 20150606
     public static Vector listInMain(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT DISTINCT(pc.`COMP_CODE`),pc.*  FROM " + TBL_PAY_COMPONENT + " pc ";
                               sql = sql + " INNER JOIN " + PstArApMain.TBL_ARAP_MAIN + 
                                            " ham ON pc." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID] + " = ham." + PstArApMain.fieldNames[PstArApMain.FLD_COMPONENT_DEDUCTION_ID]; 
			sql =sql + " , `hr_arap_item` hai ";
                               
                               if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql);
                        //System.out.println("SQL LIST"+sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				PayComponent payComponent = new PayComponent();
				resultToObject(rs, payComponent);
				lists.add(payComponent);
			}
			rs.close();
			return lists;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return new Vector();
	}
    
    /**
     * this method used to get name of component which must input manual created
     * by Yunny
     */
    public static Vector getManualComponent(String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT pay.COMP_CODE,pay.comp_name,pay.comp_type FROM " + TBL_PAY_COMPONENT + " as pay "
                    + " INNER JOIN " + PstSalaryLevelDetail.TBL_PAY_LEVEL_COM + " as slip"
                    + " ON pay." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]
                    + " = slip." + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            /* " WHERE slip."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]+
             " LIKE '%IN_%' ";*/
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            dbrs = DBHandler.execQueryResult(sql);
            System.out.println("sql getManualComponent  " + sql);

            ResultSet rs = dbrs.getResultSet();
               //System.out.println("SQL :::::::::::::::::::::::::::::::::::::"+sql);
            //System.out.println("masuk:::::::::::: "+rs);
            while (rs.next()) {

                PayComponent payComponent = new PayComponent();
                payComponent.setCompCode(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]));
                payComponent.setCompName(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]));
                payComponent.setCompType(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]));
                resultToObject(rs, payComponent);
                lists.add(payComponent);

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
     * this method used to get name of component which must input manual created
     * by Yunny
     */
    public static Vector getHeaderComponent(String whereClause, String order, String groupBy) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT pay.COMP_CODE,pay.comp_name,pay.comp_type FROM " + TBL_PAY_COMPONENT + " as pay "
                    + " INNER JOIN " + PstSalaryLevelDetail.TBL_PAY_LEVEL_COM + " as slip"
                    + " ON pay." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]
                    + " = slip." + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            /* " WHERE slip."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA]+
             " LIKE '%IN_%' ";*/
            if (groupBy != null && groupBy.length() > 0) {
                sql = sql + " GROUP BY " + groupBy;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            dbrs = DBHandler.execQueryResult(sql);
            System.out.println("sql PstPayComponent.getHeaderComponent  " + sql);

            ResultSet rs = dbrs.getResultSet();
               //System.out.println("SQL :::::::::::::::::::::::::::::::::::::"+sql);
            //System.out.println("masuk:::::::::::: "+rs);
            while (rs.next()) {

                PayComponent payComponent = new PayComponent();
                payComponent.setCompCode(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]));
                payComponent.setCompName(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]));
                payComponent.setCompType(rs.getInt(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]));
                resultToObject(rs, payComponent);
                lists.add(payComponent);

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

    /* public static Vector getPayComponent(String whereClause,String order){
     Vector lists = new Vector(); 
     DBResultSet dbrs = null;
     try {
     String sql = "SELECT pay.COMP_CODE,pay.comp_name FROM " + TBL_PAY_COMPONENT + " as pay " + 
     " INNER JOIN "+PstSalaryLevelDetail.TBL_PAY_LEVEL_COM+ " as slip"+
     " ON pay."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+
     " = slip."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE];
                 
     if(whereClause != null && whereClause.length() > 0)
     sql = sql + " WHERE " + whereClause;
                       
     if(order != null && order.length() > 0)
     sql = sql + " ORDER BY " + order;
                
     dbrs = DBHandler.execQueryResult(sql);
     ResultSet rs = dbrs.getResultSet();
     while(rs.next()) {
                        
     PayComponent payComponent = new PayComponent();
     payComponent.setCompCode(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]));
     payComponent.setCompName(rs.getString(PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME]));
     resultToObject(rs, payComponent);
     lists.add(payComponent);
                        
                        
     }
     rs.close();
     return lists;

     }catch(Exception e) {
     System.out.println(e);
     }finally {
     DBResultSet.close(dbrs);
     }
     return new Vector();
     }*/
    public static void main(String args[]) {
        //long result = getPeriodIdBySelectedDate(new Date(104,5,31));
        //long result = getPeriodIdJustBefore(504404240112808778L);
        //System.out.println("result : " + result);
        PayComponent payComponent = new PayComponent();
        payComponent.setCompName("sdfssdkjsdkg");
        payComponent.setCompCode("112");
        payComponent.setCompType(0);

        try {
            PstPayComponent.insertExc(payComponent);
        } catch (Exception e) {
            System.out.println("Err" + e.toString());
        }

    }

}
