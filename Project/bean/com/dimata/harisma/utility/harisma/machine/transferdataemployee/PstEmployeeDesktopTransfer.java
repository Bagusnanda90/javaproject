
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya
 * @version  	: 01
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...]
 * *****************************************************************
 */
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

/* package java */
import com.dimata.harisma.entity.admin.PstAppUser;
import com.dimata.harisma.entity.attendance.employeeoutlet.PstEmployeeOutlet;
import com.dimata.harisma.entity.attendance.mappingoutlet.PstExtraScheduleOutletDetail;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;

//Gede_2April2012{
import com.dimata.harisma.entity.overtime.*;
//}

/* package harisma */
//import com.dimata. harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.masterdata.location.PstLocation;
import com.dimata.harisma.entity.payroll.PstPayEmpLevel;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.harisma.utility.harisma.machine.db.DBException;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.db.DBResultSet;
import com.dimata.harisma.utility.harisma.machine.db.I_DBInterface;
import com.dimata.harisma.utility.harisma.machine.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;

public class PstEmployeeDesktopTransfer extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_EMPLOYEE = "hr_employee";//"HR_EMPLOYEE";
    public static final int FLD_EMPLOYEE_ID = 0;
    public static final int FLD_DEPARTMENT_ID = 1;
    public static final int FLD_POSITION_ID = 2;
    public static final int FLD_SECTION_ID = 3;
    public static final int FLD_EMPLOYEE_NUM = 4;
    //public static final int FLD_EMP_CATEGORY_ID = 5;
    public static final int FLD_LEVEL_ID = 5;
    public static final int FLD_FULL_NAME = 6;
    public static final int FLD_ADDRESS = 7;
    public static final int FLD_PHONE = 8;
    public static final int FLD_HANDPHONE = 9;
    public static final int FLD_POSTAL_CODE = 10;
    public static final int FLD_SEX = 11;
    public static final int FLD_BIRTH_PLACE = 12;
    public static final int FLD_BIRTH_DATE = 13;
    public static final int FLD_RELIGION_ID = 14;
    public static final int FLD_BLOOD_TYPE = 15;
    public static final int FLD_ASTEK_NUM = 16;
    public static final int FLD_ASTEK_DATE = 17;
    public static final int FLD_MARITAL_ID = 18;
    public static final int FLD_LOCKER_ID = 19;
    public static final int FLD_COMMENCING_DATE = 20;
    public static final int FLD_RESIGNED = 21;
    public static final int FLD_RESIGNED_DATE = 22;
    public static final int FLD_BARCODE_NUMBER = 23;
    public static final int FLD_RESIGNED_REASON_ID = 24;
    public static final int FLD_RESIGNED_DESC = 25;
    public static final int FLD_BASIC_SALARY = 26;
    public static final int FLD_ASSIGN_TO_ACCOUNTING = 27;
    public static final int FLD_DIVISION_ID = 28;
    /*kususus untuk keperluan intimas
     *updated by Yunny
     */
    public static final int FLD_CURIER = 29;
    /*penambahan kolom sehubungan dengan payroll
     *Updated By Yunny
     */
    public static final int FLD_INDENT_CARD_NR = 30;
    public static final int FLD_INDENT_CARD_VALID_TO = 31;
    public static final int FLD_TAX_REG_NR = 32;
    public static final int FLD_ADDRESS_FOR_TAX = 33;
    public static final int FLD_NATIONALITY_TYPE = 34;
    public static final int FLD_EMAIL_ADDRESS = 35;
    public static final int FLD_CATEGORY_DATE = 36;
    public static final int FLD_LEAVE_STATUS = 37;
    public static final int FLD_EMP_PIN = 38;
    public static final int FLD_RACE = 39;
    //add by artha
    public static final int FLD_ADDRESS_PERMANENT = 40;
    public static final int FLD_PHONE_EMERGENCY = 41;
    public static final int FLD_COMPANY_ID = 42;//Gede_15N0v2011{
    public static final int FLD_FATHER = 43;
    public static final int FLD_MOTHER = 44;
    public static final int FLD_PARENTS_ADDRESS = 45;
    public static final int FLD_NAME_EMG = 46;
    public static final int FLD_PHONE_EMG = 47;
    public static final int FLD_ADDRESS_EMG = 48;//}
    //Gede_27Nov2011{
    public static final int FLD_HOD_EMPLOYEE_ID = 49;//
    //add by Kartika 1 Dec 2011
    public static final int FLD_ADDR_COUNTRY_ID = 50;//
    public static final int FLD_ADDR_PROVINCE_ID = 51;//
    public static final int FLD_ADDR_REGENCY_ID = 52;//
    public static final int FLD_ADDR_SUBREGENCY_ID = 53;//
    public static final int FLD_ADDR_PMNT_COUNTRY_ID = 54;//
    public static final int FLD_ADDR_PMNT_PROVINCE_ID = 55;//
    public static final int FLD_ADDR_PMNT_REGENCY_ID = 56;//
    public static final int FLD_ADDR_PMNT_SUBREGENCY_ID = 57;
    public static final int FLD_ID_CARD_ISSUED_BY = 58;
    public static final int FLD_ID_CARD_BIRTH_DATE = 59;
    public static final int FLD_TAX_MARITAL_ID = 60; //Kartika; 2012-05-08
    //update by satrya 2012-11-14
    public static final int FLD_EDUCATION_ID = 61;
    public static final String[] fieldNames = {
        "EMPLOYEE_ID",//1
        "DEPARTMENT_ID",//2
        "POSITION_ID",//3
        "SECTION_ID",//4
        "EMPLOYEE_NUM",//5
        //"EMP_CATEGORY_ID",
        "LEVEL_ID",//6
        "FULL_NAME",//7
        "ADDRESS",//8
        "PHONE",//9
        "HANDPHONE",//10
        "POSTAL_CODE",//11
        "SEX",//12
        "BIRTH_PLACE",//13
        "BIRTH_DATE",//14
        "RELIGION_ID",//15
        "BLOOD_TYPE",//16
        "ASTEK_NUM",//17
        "ASTEK_DATE",//18
        "MARITAL_ID",//19
        "LOCKER_ID",//20
        "COMMENCING_DATE",//21
        "RESIGNED",//22
        "RESIGNED_DATE",//23
        "BARCODE_NUMBER",//24
        "RESIGNED_REASON_ID",
        "RESIGNED_DESC",
        "BASIC_SALARY",
        "IS_ASSIGN_TO_ACCOUNTING",
        "DIVISION_ID",
        "CURIER",
        "INDENT_CARD_NR",
        "INDENT_CARD_VALID_TO",
        "TAX_REG_NR",
        "ADDRESS_FOR_TAX",
        "NATIONALITY_TYPE",
        "EMAIL_ADDRESS",
        "CATEGORY_DATE",
        "LEAVE_STATUS",
        "EMP_PIN",
        "RACE",
        "ADDRESS_PERMANENT",
        "PHONE_EMERGENCY",
        "COMPANY_ID", //Gede_15Nov2011{
        "FATHER",
        "MOTHER",
        "PARENTS_ADDRESS",
        "NAME_EMG",
        "PHONE_EMG",
        "ADDRESS_EMG",//}
        //Gede_15Nov2011{
        "HOD_EMPLOYEE_ID",
        //add by Kartika 1 Dec 2011
        "ADDR_COUNTRY_ID",
        "ADDR_PROVINCE_ID",
        "ADDR_REGENCY_ID",
        "ADDR_SUBREGENCY_ID",
        "ADDR_PMNT_COUNTRY_ID",
        "ADDR_PMNT_PROVINCE_ID",
        "ADDR_PMNT_REGENCY_ID",
        "ADDR_PMNT_SUBREGENCY_ID",
        "ID_CARD_ISSUED_BY",
        "ID_CARD_BIRTH_DATE",
        "TAX_MARITAL_ID", //Kartika; 2012-05-08
        "EDUCATION_ID"//update by satrya 2012-11-14
    };
    public static final int[] fieldTypes = {
        
        TYPE_LONG + TYPE_PK + TYPE_ID,//"EMPLOYEE_ID",//1
        TYPE_LONG, //"DEPARTMENT_ID",//2
        TYPE_LONG, //"POSITION_ID",//3
        TYPE_LONG, //"SECTION_ID",//4
        TYPE_STRING, //"EMPLOYEE_NUM",//5
        //"EMP_CATEGORY_ID",
        TYPE_LONG, //"LEVEL_ID",//6
        TYPE_STRING, //"FULL_NAME",//7
        TYPE_STRING, //"ADDRESS",//8
        TYPE_STRING, //"PHONE",//9
        TYPE_STRING, //"HANDPHONE",//10
        TYPE_STRING, //"POSTAL_CODE",//11
        TYPE_INT, //"SEX",//12
        TYPE_STRING, //"BIRTH_PLACE",//13
        TYPE_DATE, //"BIRTH_DATE",//14
        TYPE_LONG, //"RELIGION_ID",//15
        TYPE_STRING, //"BLOOD_TYPE",//16
        TYPE_STRING, //"ASTEK_NUM",//17
        TYPE_DATE, //"ASTEK_DATE",//18
        TYPE_LONG, //"MARITAL_ID",//19
        TYPE_LONG, //"LOCKER_ID",//20
        TYPE_DATE, //"COMMENCING_DATE",//21
        TYPE_INT, //"RESIGNED",//22
        TYPE_DATE, //"RESIGNED_DATE",//23
        TYPE_STRING, //"BARCODE_NUMBER",//24
        TYPE_LONG, //"RESIGNED_REASON_ID",
        TYPE_STRING, //"RESIGNED_DESC",
        TYPE_FLOAT, //"BASIC_SALARY",
        TYPE_INT, //"IS_ASSIGN_TO_ACCOUNTING",
        TYPE_LONG, //"DIVISION_ID",
        TYPE_STRING, //"CURIER",
        TYPE_STRING, //"INDENT_CARD_NR",
        TYPE_DATE, //"INDENT_CARD_VALID_TO",
        TYPE_STRING, //"TAX_REG_NR",
        TYPE_STRING, //"ADDRESS_FOR_TAX",
        TYPE_INT, //"NATIONALITY_TYPE",
        TYPE_STRING, //"EMAIL_ADDRESS",
        TYPE_DATE, //"CATEGORY_DATE",
        TYPE_INT, //"LEAVE_STATUS",
        TYPE_STRING, //"EMP_PIN",
        TYPE_LONG, //"RACE",
        TYPE_STRING, //"ADDRESS_PERMANENT",
        TYPE_STRING, //"PHONE_EMERGENCY",
        TYPE_LONG, //"COMPANY_ID", //Gede_15Nov2011{
        TYPE_STRING, //"FATHER",
        TYPE_STRING, //"MOTHER",
        TYPE_STRING, //"PARENTS_ADDRESS",
        TYPE_STRING, //"NAME_EMG",
        TYPE_STRING, //"PHONE_EMG",
        TYPE_STRING, //"ADDRESS_EMG",//}
        //Gede_15Nov2011{
        TYPE_LONG, //"HOD_EMPLOYEE_ID",
        //add by Kartika 1 Dec 2011
        TYPE_LONG, //"ADDR_COUNTRY_ID",
        TYPE_LONG, //"ADDR_PROVINCE_ID",
        TYPE_LONG, //"ADDR_REGENCY_ID",
        TYPE_LONG, //"ADDR_SUBREGENCY_ID",
        TYPE_LONG, //"ADDR_PMNT_COUNTRY_ID",
        TYPE_LONG, //"ADDR_PMNT_PROVINCE_ID",
        TYPE_LONG, //"ADDR_PMNT_REGENCY_ID",
        TYPE_LONG, //"ADDR_PMNT_SUBREGENCY_ID",
        TYPE_STRING, //"ID_CARD_ISSUED_BY",
        TYPE_DATE, //"ID_CARD_BIRTH_DATE",
        TYPE_LONG, //"TAX_MARITAL_ID", //Kartika; 2012-05-08
        TYPE_LONG, //"EDUCATION_ID"//update by satrya 2012-11-14

    };
    //gender----
    public static final int MALE = 0;
    public static final int FEMALE = 1;
    public static final String[] sexKey = {"Male", "Female"};
    public static final int[] sexValue = {0, 1};
    //resigned
    public static final int NO_RESIGN = 0;
    public static final int YES_RESIGN = 1;
    public static final String[] resignKey = {"No", "Yes"};
    public static final int[] resignValue = {0, 1};
    public static final String[] leaveKey = {"Paid", "Unpaid"};
    public static final int[] leaveValue = {0, 1};
    //change leave with money
    public static final int YES_CHANGE = 0;
    public static final int NO_CHANGE = 1;
    // public static int STS_BOTH = 1;
    public static int STS_COMMING = 0;
    public static int STS_RESIGN = 1;
    public static String COMMING_STR = "COMMING";
    public static String[] stResignation = {
        "COMMING", "RESIGN"
    };
    //resigned
    public static final String[] blood = {"?", "A", "B", "O", "AB"/*"A", "B", "O", "AB", "?"*/};

    /**
     * create by satrya 2014-01-31 keterangan: untuk pencarian data menggunakan
     * koma
     *
     * @param text
     * @return
     */
    private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }

        return vector;
    }

    public static Vector getBlood() {
        Vector result = new Vector(1, 1);
        for (int i = 0; i < blood.length; i++) {
            result.add(blood[i]);
        }
        return result;
    }
    //nationality type
    public static int LOCAL = 0;
    public static int FOREIGNER = 1;
    public static String[] nationalityType = {
        "Local", "Foreigner"
    };
    //statutPayroll
    public static int ACTIVE = 0;
    public static int RESIGN_THIS_MONTH = 1;
    public static String[] resignPayroll = {
        "Active", "Resign This Month"
    };

    public static Vector getStatusPayroll() {
        Vector result = new Vector(1, 1);
        for (int i = 0; i < resignPayroll.length; i++) {
            result.add(resignPayroll[i]);
        }
        return result;
    }

    public PstEmployeeDesktopTransfer() {
    }

    public PstEmployeeDesktopTransfer(int i) throws DBException {
        super(new PstEmployeeDesktopTransfer());
    }

    public PstEmployeeDesktopTransfer(String sOid) throws DBException {
        super(new PstEmployeeDesktopTransfer(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmployeeDesktopTransfer(long lOid) throws DBException {
        super(new PstEmployeeDesktopTransfer(0));
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
        return TBL_HR_EMPLOYEE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmployeeDesktopTransfer().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        //belum di pakai
        return 0;
    }

    public long insertExc(Entity ent) throws Exception {
        //belum di pakai
        return 0;
    }

    public long updateExc(Entity ent) throws Exception {
        //belum d pakai
        return 0;
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    /**
     * @Author : Kartika , 2 December 2011 Mengambil gabungan data alamat
     * address dan address permanent ke employee geoAddress dan geoAdressPmnt
     * @param employee
     */
    public static long insertExc(TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData) throws DBException {
        try {
            PstEmployeeDesktopTransfer pstEmployee = new PstEmployeeDesktopTransfer(0);

            pstEmployee.setLong(FLD_POSITION_ID, tabelEmployeeOutletTransferData.getPositionId());
            pstEmployee.setString(FLD_EMPLOYEE_NUM, tabelEmployeeOutletTransferData.getEmployeeNumber());
            //pstEmployee.setLong(FLD_EMP_CATEGORY_ID, tabelEmployeeOutletTransferData.getEmpCategoryId());
            pstEmployee.setString(FLD_FULL_NAME, tabelEmployeeOutletTransferData.getFullName());
            pstEmployee.setString(FLD_ADDRESS, tabelEmployeeOutletTransferData.getAddress());
            pstEmployee.setString(FLD_PHONE, tabelEmployeeOutletTransferData.getPhone());
            pstEmployee.setString(FLD_HANDPHONE, tabelEmployeeOutletTransferData.getHandPhone());
            pstEmployee.setInt(FLD_SEX, tabelEmployeeOutletTransferData.getSex());

            pstEmployee.setDate(FLD_COMMENCING_DATE, tabelEmployeeOutletTransferData.getCommencingDate());
            pstEmployee.setInt(FLD_RESIGNED, tabelEmployeeOutletTransferData.getResigned());
            pstEmployee.setDate(FLD_RESIGNED_DATE, tabelEmployeeOutletTransferData.getResignedDate());
            pstEmployee.setString(FLD_BARCODE_NUMBER, tabelEmployeeOutletTransferData.getBarcodeNumber());

            pstEmployee.setString(FLD_EMP_PIN, tabelEmployeeOutletTransferData.getEmpPin());

            //hard rock
            pstEmployee.setString(FLD_ADDRESS_PERMANENT, tabelEmployeeOutletTransferData.getPermanentAddress());

            pstEmployee.setString(FLD_PHONE_EMERGENCY, tabelEmployeeOutletTransferData.getPhoneEmergency());
            pstEmployee.setString(FLD_ADDRESS_EMG, tabelEmployeeOutletTransferData.getAddressEmg());//}

//            pstEmployee.setLong(FLD_EMPLOYEE_ID, );
            pstEmployee.insert(tabelEmployeeOutletTransferData.getEmployeeId());
            //employee.setOID(pstEmployee.getlong(FLD_EMPLOYEE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeDesktopTransfer(0), DBException.UNKNOWN);
        }
        return tabelEmployeeOutletTransferData.getEmployeeId();
    }

    public static long updateExc(TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData) throws DBException {
        try {
            if (tabelEmployeeOutletTransferData.getEmployeeId() != 0) {
                PstEmployeeDesktopTransfer pstEmployee = new PstEmployeeDesktopTransfer(tabelEmployeeOutletTransferData.getEmployeeId());

                pstEmployee.setLong(FLD_POSITION_ID, tabelEmployeeOutletTransferData.getPositionId());
                pstEmployee.setString(FLD_EMPLOYEE_NUM, tabelEmployeeOutletTransferData.getEmployeeNumber());
//                pstEmployee.setLong(FLD_EMP_CATEGORY_ID, tabelEmployeeOutletTransferData.getEmpCategoryId());
                pstEmployee.setString(FLD_FULL_NAME, tabelEmployeeOutletTransferData.getFullName());
                pstEmployee.setString(FLD_ADDRESS, tabelEmployeeOutletTransferData.getAddress());
                pstEmployee.setString(FLD_PHONE, tabelEmployeeOutletTransferData.getPhone());
                pstEmployee.setString(FLD_HANDPHONE, tabelEmployeeOutletTransferData.getHandPhone());
                pstEmployee.setInt(FLD_SEX, tabelEmployeeOutletTransferData.getSex());

                pstEmployee.setDate(FLD_COMMENCING_DATE, tabelEmployeeOutletTransferData.getCommencingDate());
                pstEmployee.setInt(FLD_RESIGNED, tabelEmployeeOutletTransferData.getResigned());
                pstEmployee.setDate(FLD_RESIGNED_DATE, tabelEmployeeOutletTransferData.getResignedDate());
                pstEmployee.setString(FLD_BARCODE_NUMBER, tabelEmployeeOutletTransferData.getBarcodeNumber());

                pstEmployee.setString(FLD_EMP_PIN, tabelEmployeeOutletTransferData.getEmpPin());

                //hard rock
                pstEmployee.setString(FLD_ADDRESS_PERMANENT, tabelEmployeeOutletTransferData.getPermanentAddress());

                pstEmployee.setString(FLD_PHONE_EMERGENCY, tabelEmployeeOutletTransferData.getPhoneEmergency());
                pstEmployee.setString(FLD_ADDRESS_EMG, tabelEmployeeOutletTransferData.getAddressEmg());//}

//            pstEmployee.setLong(FLD_EMPLOYEE_ID, );
                //employee.setOID(pstEmployee.getlong(FLD_EMPLOYEE_ID));

                pstEmployee.update();
                return tabelEmployeeOutletTransferData.getEmployeeId();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeDesktopTransfer(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmployeeDesktopTransfer pstEmployee = new PstEmployeeDesktopTransfer(oid);
            pstEmployee.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeDesktopTransfer(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static boolean checkOID(long employeeId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMPLOYEE + " WHERE "
                    + PstEmployeeDesktopTransfer.fieldNames[PstEmployeeDesktopTransfer.FLD_EMPLOYEE_ID] + " = '" + employeeId + "'";

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
     * create by satrya 2013-08-18 untuk mencari komersing date
     *
     * @param employeeId
     * @return
     */
    public static Date getCommercingDatePerEmployee(long employeeId) {
        DBResultSet dbrs = null;
        Date dtComercing = null;
        try {
            String sql = "SELECT " + PstEmployeeDesktopTransfer.fieldNames[PstEmployeeDesktopTransfer.FLD_COMMENCING_DATE] + " FROM " + TBL_HR_EMPLOYEE + " WHERE "
                    + PstEmployeeDesktopTransfer.fieldNames[PstEmployeeDesktopTransfer.FLD_EMPLOYEE_ID] + " = '" + employeeId + "'";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                dtComercing = rs.getDate(PstEmployeeDesktopTransfer.fieldNames[PstEmployeeDesktopTransfer.FLD_COMMENCING_DATE]);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return dtComercing;
        }
    }
    
     public static Hashtable<String,Boolean> hashEmpIdAda(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashEmpEMployeeIdSdhAda= new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "+fieldNames[FLD_EMPLOYEE_ID]+" FROM " + TBL_HR_EMPLOYEE;
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
                hashEmpEMployeeIdSdhAda.put(""+rs.getLong(fieldNames[FLD_EMPLOYEE_ID]), true);
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashEmpEMployeeIdSdhAda;
        }
        
    }
}
