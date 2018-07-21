/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.leave;

import com.dimata.harisma.entity.attendance.AlStockExpired;
import com.dimata.harisma.entity.attendance.AlStockManagement;
import com.dimata.harisma.entity.attendance.AlStockTaken;
import com.dimata.harisma.entity.attendance.PstAlStockExpired;
import com.dimata.harisma.entity.attendance.PstAlStockManagement;
import com.dimata.harisma.entity.attendance.PstAlStockTaken;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.leave.AlUpload;
import com.dimata.harisma.entity.leave.PstAlUpload;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.search.SrcAlUpload;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import com.dimata.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.qdep.entity.I_DocStatus;
import javax.print.DocFlavor.STRING;

/**
 *
 * @author gArtha
 */
public class SessAlUpload {

    public static final int TOT_DAY_A_YEAR = 365;  /* total day in 1 year */
    public static final int AL_ENTITLE_LESS_1_YEAR = 0;  /* total day in 1 year */


    /**
     * @desc menghitung banyak al yang diperoleh (entitled) sampai dengan tanggal tertentu
     * @param id employee
     * @param tanggal perhitungan
     * @return banyak al yang entitled pada tanggal tertentu
     */
    public static float getAlEntitled(long empId, Date opnameDate) {
        float iAlEntitled = 0;

        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
         boolean adaStok = PstAlStockManagement.CekAdaDataStok(""+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = "+empId);
                     
        if (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING  || (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING_PERIODE && !adaStok) ) {
            try {
                Employee objEmployee = new Employee();
                Date dCommencingDate = new Date();
                objEmployee = PstEmployee.fetchExc(empId);
                if (objEmployee.getCommencingDate() != null) {
                    dCommencingDate = new Date(objEmployee.getCommencingDate().getTime());
                    // dCommencingDate.setYear(opnameDate.getYear());
                    // Jika waktu commencing date lebih kecil dari waktu terkini
                    // atau dengan kata lain, earned ytd akan muncul jika waktu commencing
                    // pegawai telah lewat dari waktu sekarang
                    Level objLevel = new Level();
                    objLevel = PstLevel.fetchExc(objEmployee.getLevelId());
                    EmpCategory objEmpCategory = new EmpCategory();
                    objEmpCategory = PstEmpCategory.fetchExc(objEmployee.getEmpCategoryId());
                    //  System.out.println("Comm Date   : "+dCommencingDate);
                    //  System.out.println("Opname Date : "+opnameDate);
                    if (dCommencingDate.getTime() <= opnameDate.getTime()) {
                        int LoS = (int) DateCalc.dayDifference(objEmployee.getCommencingDate(), opnameDate);
                        iAlEntitled = leaveConfig.getALEntitleAnualLeave(objLevel.getLevel(), objEmpCategory.getEmpCategory(), LoS, 
                                objEmployee.getCommencingDate(),opnameDate);
                    }
                }
            } catch (DBException ex) {
                ex.printStackTrace();
                System.out.println("Exception : " + ex.toString());
            }
        } else {
            //Leave by periode (not commencing date)
            DBResultSet dbrs = null;
            try{
                Employee objEmployee = new Employee();
                Date dCommencingDate = new Date();
                objEmployee = PstEmployee.fetchExc(empId);
                if (objEmployee.getCommencingDate() != null)
                    dCommencingDate = new Date(objEmployee.getCommencingDate().getTime());

                int commencingYear = dCommencingDate.getYear() + 1900;
                int commencingMonth = dCommencingDate.getMonth()+1;
                String strCommencingMonth = "";
                if(commencingMonth<10)
                    strCommencingMonth = "0" + String.valueOf(commencingMonth);
                else
                    strCommencingMonth = String.valueOf(commencingMonth);
                String commencingYearMonth = String.valueOf(commencingYear) + strCommencingMonth;

                int nowYear = opnameDate.getYear()+ 1900;
                int nowMonth = opnameDate.getMonth()+1;
                String strNowMonth = "";
                if(nowMonth<10)
                    strNowMonth = "0" + String.valueOf(nowMonth);
                else
                    strNowMonth = String.valueOf(nowMonth);
                String nowYearMonth = String.valueOf(nowYear) + strNowMonth;

                int diffMonth = 0;
                String sql = "SELECT PERIOD_DIFF('" +nowYearMonth+ "','"+commencingYearMonth+ "')";
                System.out.println("Sql Diff = " + sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while (rs.next()) {
                    diffMonth = rs.getInt(1);
                }
                if(diffMonth>11){
                    Level objLevel = new Level();
                    objLevel = PstLevel.fetchExc(objEmployee.getLevelId());
                    EmpCategory objEmpCategory = new EmpCategory();
                    objEmpCategory = PstEmpCategory.fetchExc(objEmployee.getEmpCategoryId());
                    int LoS = (int) DateCalc.dayDifference(objEmployee.getCommencingDate(), opnameDate);
                    iAlEntitled = leaveConfig.getALEntitleAnualLeave(objLevel.getLevel(), objEmpCategory.getEmpCategory(), LoS, 
                                objEmployee.getCommencingDate(),opnameDate);
                }else{
                    iAlEntitled = diffMonth;
                }
            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            } finally {
                DBResultSet.close(dbrs);
            }
        }
        return iAlEntitled;
    }

    /**
     * @desc menghitung banyak al yang diperoleh dan bisa dipergunakan (earned) sampai dengan tanggal tertentu
     * @param id employee
     * @param tanggal perhitungan
     * @return banyak al yang earned pada tanggal tertentu
     */
    public static float getAlEarned(long empId, Date opnameDate) {
        float iAlEarned = 0;
        float iAlEntitled = 0;

        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

        try {
            Employee objEmployee = new Employee();
            objEmployee = PstEmployee.fetchExc(empId);
            //System.out.println(":::::: OPNAME "+opnameDate+" ::::: COMM "+objEmployee.getCommencingDate());
            //System.out.println(":::::: OPNAME "+opnameDate.getTime()+" ::::: COMM "+objEmployee.getCommencingDate().getTime());

            if (opnameDate.getTime() >= objEmployee.getCommencingDate().getTime()) {
                iAlEntitled = getAlEntitled(empId, opnameDate);
                //   System.out.println("::::::: 1 "+iAlEntitled);
                 boolean adaStok = PstAlStockManagement.CekAdaDataStok(""+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = "+empId);
                if (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING || (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING_PERIODE && !adaStok)) {
                    //       System.out.println("::::::: 2");    
                    iAlEarned = iAlEntitled;
                } else {
                    if (iAlEntitled > 0) {
                        iAlEarned = opnameDate.getMonth() + 1;
                        if (opnameDate.getDate() > objEmployee.getCommencingDate().getDate()) {
                            iAlEarned += 1;
                        }
                    }
                }
            }
        } catch (DBException ex) {
            ex.printStackTrace();
        }
        return iAlEarned;
    }

    /**
     * @desc menghitung banyak al yang diperoleh dan bisa dipergunakan (earned) sampai dengan tanggal tertentu
     * @param id employee
     * @param tanggal perhitungan
     * @return banyak al yang earned pada tanggal tertentu
     */
    public static float getAlEntitle(long empId, Date opnameDate) {

        float iAlEarned = 0;
        float iAlEntitled = 0;

        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

        try {

            Employee objEmployee = new Employee();
            objEmployee = PstEmployee.fetchExc(empId);

            if (opnameDate.getTime() >= objEmployee.getCommencingDate().getTime()) {

                Date nxt1Year = DateAdd(objEmployee.getCommencingDate());

                if (nxt1Year != null) {

                    int interval = 0;

                    interval = getDiff(nxt1Year, opnameDate);
                    System.out.println("Interval = " + interval);

                     boolean adaStok = PstAlStockManagement.CekAdaDataStok(""+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = "+empId);
                    
                    if (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_PERIOD || (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING_PERIODE && adaStok)) {
                        iAlEntitled = getAlEntitled(empId, opnameDate);
                        iAlEarned = iAlEntitled;
                    }else{

                        if (interval >= 0) {

                            iAlEntitled = getAlEntitled(empId, opnameDate);

                            if (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING || (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING_PERIODE && !adaStok)) {

                                iAlEarned = iAlEntitled;

                            } else {
                                if (iAlEntitled > 0) {
                                    iAlEarned = opnameDate.getMonth() + 1;
                                    if (opnameDate.getDate() > objEmployee.getCommencingDate().getDate()) {
                                        iAlEarned += 1;

                                    }

                                }
                            }

                        } else {

                            iAlEarned = AL_ENTITLE_LESS_1_YEAR;
                        }
                    }
                }
            }
        } catch (DBException ex) {
            ex.printStackTrace();
        }
        return iAlEarned;
    }

    /**
     * @Author Roy Andika
     * @Desc in interval year
     * @param inputDate
     * @return Date
     */
    public static Date DateAdd(Date inputDate) {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT DATE_ADD('" + Formater.formatDate(inputDate, "yyyy-MM-dd") + "',interval 1 year)";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Date next = rs.getDate(1);
                return next;
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            DBResultSet.close(dbrs);
        }
        return null;
    }

    public static int getDiff(Date fnFirst, Date fnLast) {

        DBResultSet dbrs = null;

        int diff = 0;

        try {

            String sql = "SELECT DATEDIFF('" + Formater.formatDate(fnLast, "yyyy-MM-dd") + "','" + Formater.formatDate(fnFirst, "yyyy-MM-dd") + "')";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                diff = rs.getInt(1);
                return diff;

            }

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return diff;
    }

    /**
     * @desc mencari tanggal start periode dari employee dan tanggal diopname
     * @param id employee
     * @param tanggal perhitungan
     * @return tanggal start period dari masa opname
     */
    public static Date getStartPeriodDate(long empId, Date opnameDate) {

        Date dateStartPer = null;

        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        try {
            Employee objEmployee = new Employee();
            objEmployee = PstEmployee.fetchExc(empId);
            Date tempDate = new Date(objEmployee.getCommencingDate().getYear(), objEmployee.getCommencingDate().getMonth(), objEmployee.getCommencingDate().getDate());
            boolean adaStok = PstAlStockManagement.CekAdaDataStok(""+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = "+empId);
               
            if (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING || (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING_PERIODE && !adaStok)) {
                if (opnameDate.getMonth() >= objEmployee.getCommencingDate().getMonth()) {
                    if (opnameDate.getMonth() > objEmployee.getCommencingDate().getMonth()) {
                        tempDate.setYear(opnameDate.getYear());
                    } else {
                        if (opnameDate.getDate() >= objEmployee.getCommencingDate().getDate()) {
                            tempDate.setYear(opnameDate.getYear());
                        } else {
                            tempDate.setYear(opnameDate.getYear() - 1);
                        }
                    }
                } else {
                    tempDate.setYear(opnameDate.getYear() - 1);
                }

            } else {
                tempDate.setDate(1);
                tempDate.setMonth(1);
                tempDate.setYear(opnameDate.getYear());
            }
            
            dateStartPer=tempDate;
//            dateStartPer.setDate(tempDate.getDate());
//            dateStartPer.setMonth(tempDate.getMonth());
//            dateStartPer.setYear(tempDate.getYear());
        } catch (DBException ex) {
            ex.printStackTrace();
        }
        return dateStartPer;
    }

    public static Vector getEmployeeAndDataUpload(SrcAlUpload srcAlUpload, int limitStart, int recordToGet, String order) {
        Vector result = new Vector();
        Vector temp = new Vector();
        DBResultSet dbrs = null;
        String sql = "";
        String where = "";

        try {
            sql = "SELECT HR_EMP.*,HR_UP.* FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS HR_EMP LEFT JOIN " + PstAlUpload.TBL_AL_UPLOAD + " AS HR_UP ON HR_EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = HR_UP." + PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID]
                    + " WHERE HR_EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = HR_UP." + PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID];

            if (srcAlUpload.getEmployeeName().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += " ( HR_EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \'%" + srcAlUpload.getEmployeeName() + "%\')";
            }
            if (srcAlUpload.getEmployeePayroll().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += "( HR_EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE \'%" + srcAlUpload.getEmployeePayroll() + "%\')";
            }
            if (srcAlUpload.getEmployeeCategory() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += "( HR_EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "=" + srcAlUpload.getEmployeeCategory() + ")";
            }
            if (srcAlUpload.getEmployeeDepartement() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += "( HR_EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + srcAlUpload.getEmployeeDepartement() + ")";
            }
            if (srcAlUpload.getEmployeeSection() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += ""
                        + "( HR_EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + srcAlUpload.getEmployeeSection() + ")";
            }
            if (srcAlUpload.getEmployeePosition() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += "( HR_EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + srcAlUpload.getEmployeePosition() + ")";
            }

            if (srcAlUpload.getOpnameDate() != null) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += "HR_UP." + PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE] + " = \"" + Formater.formatDate(srcAlUpload.getOpnameDate(), "yyyy-MM-dd") + "\"";
            }
            if (srcAlUpload.getDataStatus() > -1) {
                //-1 default untuk semua data
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += "(HR_UP." + PstAlUpload.fieldNames[PstAlUpload.FLD_DATA_STATUS] + "=" + srcAlUpload.getDataStatus() + ")";
            }

            if (where != null && where.length() > 0) {
                sql = sql + " AND " + where;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("Query Employee and upload : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Employee employee = new Employee();
                AlUpload objAlUpload = new AlUpload();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                employee.setLevelId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                employee.setPhone(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                employee.setHandphone(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_HANDPHONE]));
                employee.setPostalCode(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_POSTAL_CODE]));
                employee.setSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                employee.setBirthPlace(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_PLACE]));
                employee.setBirthDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]));
                employee.setReligionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]));
                employee.setBloodType(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]));
                employee.setAstekNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_NUM]));
                employee.setAstekDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_ASTEK_DATE]));
                employee.setMaritalId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]));
                employee.setLockerId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setResigned(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]));
                employee.setResignedDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]));
                employee.setBarcodeNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                employee.setResignedReasonId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_REASON_ID]));
                employee.setResignedDesc(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DESC]));
                employee.setBasicSalary(rs.getDouble(PstEmployee.fieldNames[PstEmployee.FLD_BASIC_SALARY]));
                employee.setIsAssignToAccounting(rs.getBoolean(PstEmployee.fieldNames[PstEmployee.FLD_ASSIGN_TO_ACCOUNTING]));
                employee.setDivisionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]));
                employee.setCurier(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_CURIER]));
                employee.setIndentCardNr(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_INDENT_CARD_NR]));
                employee.setIndentCardValidTo(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_INDENT_CARD_VALID_TO]));
                employee.setTaxRegNr(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_TAX_REG_NR]));
                employee.setAddressForTax(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_FOR_TAX]));
                employee.setNationalityType(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_NATIONALITY_TYPE]));
                employee.setEmailAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]));
                employee.setCategoryDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_CATEGORY_DATE]));
                employee.setLeaveStatus(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_LEAVE_STATUS]));
                employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                employee.setRace(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RACE]));
                employee.setAddressPermanent(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_PERMANENT]));
                employee.setPhoneEmergency(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_PHONE_EMERGENCY]));

                objAlUpload.setOID(rs.getLong(PstAlUpload.fieldNames[PstAlUpload.FLD_AL_UPLOAD_ID]));
                objAlUpload.setCurrPerTaken(rs.getInt(PstAlUpload.fieldNames[PstAlUpload.FLD_CURR_PERIOD_TAKEN]));
                objAlUpload.setDataStatus(rs.getInt(PstAlUpload.fieldNames[PstAlUpload.FLD_DATA_STATUS]));
                objAlUpload.setEmployeeId(rs.getLong(PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID]));
                objAlUpload.setLastPerToClear(rs.getInt(PstAlUpload.fieldNames[PstAlUpload.FLD_LAST_PER_TO_CLEAR]));
                objAlUpload.setOpnameDate(rs.getDate(PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE]));
                objAlUpload.setNewAl(rs.getInt(PstAlUpload.fieldNames[PstAlUpload.FLD_NEW_AL]));

                temp.add(employee);
                temp.add(objAlUpload);
                temp.add(srcAlUpload);
                result.add(temp);
            }
            rs.close();

            return result;

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return new Vector();
    }

    public static int getEmployee(SrcAlUpload srcAlUpload) {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT COUNT(*) FROM " + PstEmployee.TBL_HR_EMPLOYEE;

            String where = "";

            if (srcAlUpload.getEmployeeName().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += " (" + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \'%" + srcAlUpload.getEmployeeName() + "%\')";
            }

            if (srcAlUpload.getEmployeePayroll().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE \'%" + srcAlUpload.getEmployeePayroll() + "%\')";
            }
            if (srcAlUpload.getEmployeeCategory() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "=" + srcAlUpload.getEmployeeCategory() + ")";
            }
            if (srcAlUpload.getEmployeeDepartement() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + srcAlUpload.getEmployeeDepartement() + ")";
            }
            if (srcAlUpload.getEmployeeSection() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += ""
                        + "(" + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + srcAlUpload.getEmployeeSection() + ")";
            }
            if (srcAlUpload.getEmployeePosition() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + srcAlUpload.getEmployeePosition() + ")";
            }
            if (srcAlUpload.getResignStatus() == 0){
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + srcAlUpload.getResignStatus() + ")";
            } else if (srcAlUpload.getResignStatus() == 1){
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + srcAlUpload.getResignStatus() + ")";
            }
            
            if (where.length() > 0) {

                sql = sql + " WHERE " + where + " " ;

            } 

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                int size = 0;
                size = rs.getInt(1);
                return size;
            }


        } catch (Exception E) {
            System.out.println("[ exception ] " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }

    /**
     * @desc mengambil data AL yang akan diset
     * @param srcAlUpload menampung data yang menjadi acuan dalam pencarian
     * @param start menentukan awal dari pencarian data
     * @param recordToGet menentukan banyak data yang akan diambil
     * @return Vector
     */
    public static Vector searchAlData(SrcAlUpload srcAlUpload, int start, int recordToGet) {

        Vector vResult = new Vector(1, 1);
        Vector vEmployee = new Vector(1, 1);

        vEmployee = getDataEmployee(srcAlUpload, start, recordToGet);

        Vector VAlUpload = getAlUploadedData(srcAlUpload, start, recordToGet);

        for (int i = 0; i < vEmployee.size(); i++) {

            Employee employee = new Employee();
            AlUpload alUpload = new AlUpload();

            employee = (Employee) vEmployee.get(i);

            Vector temp = new Vector(1, 1);
            temp.add(employee);

            boolean status = false;
            for (int v = 0; v < VAlUpload.size(); v++) {
                AlUpload alUploaded = (AlUpload) VAlUpload.get(v);
                if (alUploaded.getEmployeeId() == employee.getOID()) {
                    temp.add(alUploaded); VAlUpload.remove(v); v=v-1;
                    status = true;
                    break;
                }
            }
            if (status == false) {
                temp.add(alUpload);
            }

            temp.add(srcAlUpload);
            vResult.add(temp);
        }

        return vResult;
    }

    /**
     * @desc mencari data employee berdasarkan acuan pencarian
     * @param srcAlUpload menampung data yang menjadi acuan dalam pencarian
     * @param start menentukan awal dari pencarian data
     * @param recordToGet menentukan banyak data yang akan diambil
     * @return Vector
     */
    private static Vector getDataEmployee(SrcAlUpload srcAlUpload, int start, int recordToGet) {
        Vector result = new Vector(1, 1);

        String where = "";

        if (srcAlUpload.getEmployeeName().length() > 0) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += " (" + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \'%" + srcAlUpload.getEmployeeName() + "%\')";
        }

        if (srcAlUpload.getEmployeePayroll().length() > 0) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE \'%" + srcAlUpload.getEmployeePayroll() + "%\')";
        }
        if (srcAlUpload.getEmployeeCategory() > 0) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "=" + srcAlUpload.getEmployeeCategory() + ")";
        }
        //if (srcAlUpload.getEmployeeDepartement() > 0) {
        if (srcAlUpload.getEmployeeDepartement() != 0) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + srcAlUpload.getEmployeeDepartement() + ")";
        }
        if (srcAlUpload.getEmployeeSection() > 0) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += ""
                    + "(" + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + srcAlUpload.getEmployeeSection() + ")";
        }
        if (srcAlUpload.getEmployeePosition() > 0) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + srcAlUpload.getEmployeePosition() + ")";
        }

      
        
        if (srcAlUpload.getResignStatus() == 0) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + srcAlUpload.getResignStatus() + ")";
        }
        if (srcAlUpload.getResignStatus() == 1) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + srcAlUpload.getResignStatus() + ")";
        }
        

        result = PstEmployee.list(start, recordToGet, where, null);
        return result;
    }

    /**
     * @desc mencari data al yang telah diupload berdasarkan acuan pencarian
     * @param srcAlUpload menampung data yang menjadi acuan dalam pencarian
     * @param start menentukan awal dari pencarian data
     * @param recordToGet menentukan banyak data yang akan diambil
     * @return Vector
     */
    private static Hashtable getAlUploaded(SrcAlUpload srcAlUpload, int start, int recordToGet) {

        Vector result = new Vector(1, 1);
        String where = "";
        Hashtable hashTable;

        if (srcAlUpload.getOpnameDate() != null) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE] + " = \"" + Formater.formatDate(srcAlUpload.getOpnameDate(), "yyyy-MM-dd") + "\"";
        }
        if (srcAlUpload.getDataStatus() > -1) {
            //-1 default untuk semua data
            if (where.length() > 0) {
                where += " AND ";
            }
            where += "(" + PstAlUpload.fieldNames[PstAlUpload.FLD_DATA_STATUS] + "=" + srcAlUpload.getDataStatus() + ")";
        }

        System.out.println("where " + where);
        result = PstAlUpload.list(start, recordToGet, where, null);
        hashTable = new Hashtable(result.size());
        for (int i = 0; i < result.size(); i++) {
            AlUpload alUpload = new AlUpload();
            alUpload = (AlUpload) result.get(i);
            hashTable.put(String.valueOf(alUpload.getEmployeeId()), alUpload);
        }

        return hashTable;
    }

    private static Vector getAlUploadedData(SrcAlUpload srcAlUpload, int start, int recordToGet) {
        Vector result = new Vector(1, 1);

        String where = "";


        if (srcAlUpload.getOpnameDate() != null) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE] + " = \"" + Formater.formatDate(srcAlUpload.getOpnameDate(), "yyyy-MM-dd") + "\"";
        }
        if (srcAlUpload.getDataStatus() > -1) {
            //-1 default untuk semua data
            if (where.length() > 0) {
                where += " AND ";
            }
            where += "(" + PstAlUpload.fieldNames[PstAlUpload.FLD_DATA_STATUS] + "=" + srcAlUpload.getDataStatus() + ")";
        }

        //System.out.println("where " + where);
        result = PstAlUpload.list(0, 0, where, null);
        return result;
    }

    public static long getStockID(long employeeID) {
        long StockID = 0;
        DBResultSet dbrs = null;

        try {
            String sql = "SELECT " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] + " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT
                    + " WHERE " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = " + employeeID + " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + " = " + PstAlStockManagement.AL_STS_AKTIF;
            dbrs = DBHandler.execQueryResult(sql);
            System.out.println("SQL GET STOCK ID " + sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                StockID = rs.getLong(1);
            }
            rs.close();
            return StockID;

        } catch (Exception e) {
            System.out.println(" [EXCEPTION ] :::: " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return StockID;
    }

    //aded by Roy Andika
    /**
     * @desc method untuk menyimpan data pada database (AlUpload)
     *      - jika data telah ada sebelumnya, maka data akan diupdate
     *      - jika data belum ada sebelumnya, maka data akan dibuat
     * @param vector dari data yang diperlukan dalam proses
     * @return 
     */
    public synchronized static Vector saveAlUploadBalance(Vector vAlUpload, boolean process) {
        Vector vAlUploadId = new Vector(1, 1);
        String sysMessage="SYSTEM :<BR>";
        try {
            Date dateOpname = new Date();

            String[] emp_id = (String[]) vAlUpload.get(0);
            String[] alUpload_id = (String[]) vAlUpload.get(1);
            String[] emp_to_clear_last_per = (String[]) vAlUpload.get(2);
            String[] emp_taken_curr_per = (String[]) vAlUpload.get(3);
            String[] data_status = (String[]) vAlUpload.get(4);
            boolean[] is_process = (boolean[]) vAlUpload.get(5);
            dateOpname = (Date) vAlUpload.get(6);
            String[] emp_earned_curr_per = (String[]) vAlUpload.get(7);
            String[] emp_earned_last_per = (String[]) vAlUpload.get(8);
            String[] emp_new_qty = (String[]) vAlUpload.get(9);

            System.out.println("emp_id                  : " + emp_id.length);
            System.out.println("alUpload_id             : " + alUpload_id.length);
            System.out.println("emp_to_clear_last_per   : " + emp_to_clear_last_per.length);
            System.out.println("emp_taken_curr_per      : " + emp_taken_curr_per.length);
            System.out.println("data_status             : " + data_status.length);
            System.out.println("new_qty             : " + emp_new_qty.length);
            System.out.println("is_process              : " + is_process.length);
            for (int i = 0; i < emp_id.length; i++) {
                if (is_process[i]) {
                    System.out.println("Data yang berhasil di upload");
                    System.out.println("employee id : " + emp_id[i]);
                    System.out.println("employee previous : " + emp_to_clear_last_per[i]);

                    AlUpload alUpload = new AlUpload();
                    int iTmp = 0;
                    long lTmp = 0L;                    
                    try {
                        lTmp = Long.parseLong(emp_id[i]);
                    } catch (Exception exc) {
                        System.out.println("Exception " + exc.toString());
                        sysMessage = sysMessage + " ERROR at : idx="+(i+1)+" | Exc emp_id <br>"+exc;                        
                    }
                    alUpload.setEmployeeId(lTmp);

                    long stockID = getStockID(lTmp);

                    alUpload.setStockId(stockID);

                    float qty = 0;
                    try {
                        qty = Float.parseFloat(emp_new_qty[i]);
                    } catch (Exception e) {
                        System.out.println("EXCEPTION " + e.toString());
                        //sysMessage = sysMessage + " ERROR at : idx="+(i+1)+" | Exc emp_new_balance <br>"+e;
                    }

                    alUpload.setNewQty(qty);

                    try {
                        iTmp = 0;
                        iTmp = Integer.parseInt(data_status[i]);
                    } catch (Exception exc) {
                        System.out.println("Exception " + exc.toString());
                        //sysMessage = sysMessage + " ERROR at : idx="+(i+1)+" | Exc data_status <br>"+exc;
                    }
                    alUpload.setDataStatus(process ?  PstAlUpload.FLD_DOC_STATUS_PROCESS: iTmp);

                    try {
                        qty = Float.parseFloat(emp_earned_curr_per[i]);
                    } catch (Exception exc) {
                        System.out.println("Exception " + exc.toString());
                        //sysMessage = sysMessage + " ERROR at : idx="+(i+1)+" | Exc emp_earned_curr_per <br>"+exc;
                        qty = 0;
                    }
                    alUpload.setNewAl(qty);

                    alUpload.setOpnameDate(dateOpname);

                    try {
                        qty =  Float.parseFloat(emp_to_clear_last_per[i]);
                    } catch (Exception exc) {
                        System.out.println("Exception " + exc.toString());
                        //sysMessage = sysMessage + " ERROR at : idx="+(i+1)+" | Exc emp_to_clear_last_per <br>"+exc;
                        qty = 0;
                    }
                    alUpload.setLastPerToClear(qty);

                    try {
                        qty =  Float.parseFloat(emp_earned_last_per[i]);
                    } catch (Exception exc) {
                        System.out.println("Exception " + exc.toString());
                        //sysMessage = sysMessage + " ERROR at : idx="+(i+1)+" | Exc emp_earned_last_per <br>"+exc;
                        qty = 0;
                    }
                    alUpload.setPrevSysAlBalance(qty);

                    if (emp_taken_curr_per != null && emp_taken_curr_per.length >= i) {
                        if (emp_taken_curr_per[i].equals("")) {
                            alUpload.setCurrPerTaken(0);
                        } else {
                            try {
                                qty =  Float.parseFloat(emp_taken_curr_per[i]);
                            } catch (Exception exc) {
                                System.out.println("Exception " + exc.toString());
                                //sysMessage = sysMessage + " ERROR at : idx="+(i+1)+" | Exc emp_taken_curr_per <br>"+exc;
                                qty = 0;
                            }
                            alUpload.setCurrPerTaken(qty);
                        }
                    } else {
                        alUpload.setCurrPerTaken(0);
                    }

                    if (Long.parseLong(alUpload_id[i]) != 0) { //Jika al upload telah ada maka akan diupdate, jika tidak maka akan disimpan yang baru
                        try {
                            lTmp = 0;
                            try {
                                lTmp = Long.parseLong(alUpload_id[i]);
                            } catch (Exception exc) {
                                System.out.println("Exception " + exc.toString());
                                iTmp = 0;
                            }                            
                            alUpload.setOID(lTmp);
                            PstAlUpload.updateExc(alUpload);
                            vAlUploadId.add(String.valueOf(Long.parseLong(alUpload_id[i])));
                        } catch (DBException ex) {
                            ex.printStackTrace();
                            sysMessage = sysMessage + " ERROR at : idx="+(i+1)+" | Exc updateExc <br>"+ex;
                        }
                    } else {
                        try {
                            long id = PstAlUpload.insertExc(alUpload);
                            vAlUploadId.add(String.valueOf(id));
                        } catch (DBException ex) {
                            ex.printStackTrace();
                            sysMessage = sysMessage + " ERROR at : idx="+(i+1)+" | Exc updateExc <br>"+ex;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("SessAlUpload saveAlUpload [ERROR] :::::::: " + ex.toString());
            sysMessage = "<br> Loop error : "+ex;
        }
        
        if(!sysMessage.equals("SYSTEM :<BR>")){
            vAlUploadId.add(sysMessage);
        }
        return vAlUploadId;
    }

    public static void updateEntitleDate(Vector vAlUpload) {

        try {

            String[] emp_id = (String[]) vAlUpload.get(0);
            boolean[] is_process = (boolean[]) vAlUpload.get(5);
            String[] emp_entitle_date = (String[]) vAlUpload.get(10);

            for (int i = 0; i < emp_id.length; i++) {

                if (is_process[i]) {
                    ///update by satrya 2012-10-16
                    String where = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = " + emp_id[i] + " AND "
                            + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + "=" + PstAlStockManagement.AL_STS_AKTIF;

                    Vector listAlAktif = PstAlStockManagement.list(0, 0, where, null);

                    AlStockManagement alStockManagement = new AlStockManagement();

                    if (listAlAktif != null && listAlAktif.size() > 0) {

                        if (emp_entitle_date[i].compareTo("") != 0) {

                            Date entitleDate = Formater.formatDate(emp_entitle_date[i], "yyyy-MM-dd");
                            alStockManagement = (AlStockManagement) listAlAktif.get(0);
                            alStockManagement.setEntitleDate(entitleDate);

                            try {
                                PstAlStockManagement.updateExc(alStockManagement);
                            } catch (Exception e) {
                                System.out.println("Exeption " + e.toString());
                            }

                        }

                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }
    }

    /**
     * @desc method untuk menyimpan data pada database (AlUpload)
     *      - jika data telah ada sebelumnya, maka data akan diupdate
     *      - jika data belum ada sebelumnya, maka data akan dibuat
     * @param vector dari data yang diperlukan dalam proses
     * @return 
     */
    public synchronized static Vector saveAlUpload(Vector vAlUpload) {
        Vector vAlUploadId = new Vector(1, 1);
        try {
            Date dateOpname = new Date();

            String[] emp_id = (String[]) vAlUpload.get(0);
            String[] alUpload_id = (String[]) vAlUpload.get(1);
            String[] emp_to_clear_last_per = (String[]) vAlUpload.get(2);
            String[] emp_taken_curr_per = (String[]) vAlUpload.get(3);
            String[] data_status = (String[]) vAlUpload.get(4);
            boolean[] is_process = (boolean[]) vAlUpload.get(5);
            dateOpname = (Date) vAlUpload.get(6);
            String[] emp_earned_curr_per = (String[]) vAlUpload.get(7);
            String[] emp_earned_last_per = (String[]) vAlUpload.get(8);

            System.out.println("emp_id                  : " + emp_id.length);
            System.out.println("alUpload_id             : " + alUpload_id.length);
            System.out.println("emp_to_clear_last_per   : " + emp_to_clear_last_per.length);
            System.out.println("emp_taken_curr_per      : " + emp_taken_curr_per.length);
            System.out.println("data_status             : " + data_status.length);
            System.out.println("is_process              : " + is_process.length);

            for (int i = 0; i < emp_id.length; i++) {
                if (is_process[i]) {

                    AlUpload alUpload = new AlUpload();
                    int iTmp = 0;
                    long lTmp = 0L;
                    try {
                        lTmp = Long.parseLong(emp_id[i]);
                    } catch (Exception exc) {
                        System.out.println("Exception " + exc.toString());
                    }
                    alUpload.setEmployeeId(lTmp);

                    try {
                        iTmp = 0;
                        iTmp = Integer.parseInt(data_status[i]);
                    } catch (Exception exc) {
                        System.out.println("Exception " + exc.toString());
                    }
                    alUpload.setDataStatus(iTmp);

                    float qty=0f;
                    try {
                        qty = Float.parseFloat(emp_earned_curr_per[i]);
                    } catch (Exception exc) {
                        System.out.println("Exception " + exc.toString());
                        qty = 0;
                    }
                    alUpload.setNewAl(qty);

                    alUpload.setOpnameDate(dateOpname);

                    try {
                        qty = Float.parseFloat(emp_to_clear_last_per[i]);
                    } catch (Exception exc) {
                        System.out.println("Exception " + exc.toString());
                        qty = 0;
                    }
                    alUpload.setLastPerToClear(qty);

                    try {
                        qty = Float.parseFloat(emp_earned_last_per[i]);
                    } catch (Exception exc) {
                        System.out.println("Exception " + exc.toString());
                        qty = 0;
                    }
                    alUpload.setPrevSysAlBalance(qty);


                    if (emp_taken_curr_per != null && emp_taken_curr_per.length >= i) {
                        if (emp_taken_curr_per[i].equals("")) {
                            alUpload.setCurrPerTaken(0);
                        } else {
                            try {
                                qty = Float.parseFloat(emp_taken_curr_per[i]);
                            } catch (Exception exc) {
                                System.out.println("Exception " + exc.toString());
                                qty = 0;
                            }
                            alUpload.setCurrPerTaken(qty);
                        }
                    } else {
                        alUpload.setCurrPerTaken(0);
                    }

                    if (Long.parseLong(alUpload_id[i]) > 0) { //Jika al upload telah ada maka akan diupdate, jika tidak maka akan disimpan yang baru
                        try {
                            lTmp = 0;
                            try {
                                lTmp = Long.parseLong(alUpload_id[i]);
                            } catch (Exception exc) {
                                System.out.println("Exception " + exc.toString());
                                iTmp = 0;
                            }
                            alUpload.setOID(lTmp);
                            PstAlUpload.updateExc(alUpload);
                            vAlUploadId.add(String.valueOf(Long.parseLong(alUpload_id[i])));
                        } catch (DBException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        try {
                            long id = PstAlUpload.insertExc(alUpload);
                            vAlUploadId.add(String.valueOf(id));
                        } catch (DBException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("SessAlUpload saveAlUpload [ERROR] :::::::: " + ex.toString());
        }
        return vAlUploadId;
    }

    /////////////proses opname/////////////////////
    /**
     * @desc mencari al stock management dari periode dan id employee
     * @param id employee
     * @param tanggal perolehan
     * @return id dari hr_al_stock_management
     */
    private static AlStockManagement getALStockManagement(long employeeId, Date ownDate) {
        AlStockManagement objAlStockManagement = new AlStockManagement();

        DBResultSet dbrs;
        //update by satrya 20912-10-16
        String strWhere = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = " + employeeId + " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + " = \"" + Formater.formatDate(ownDate, "yyyy-MM-dd 00:00:00") + "\"";

        Vector vAlStockManagement = new Vector();
        vAlStockManagement = PstAlStockManagement.list(0, 1, strWhere, PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + " ASC");

        if (vAlStockManagement.size() > 0) {
            objAlStockManagement = (AlStockManagement) vAlStockManagement.get(0);
        } else {
            return new AlStockManagement();
        }
        return objAlStockManagement;
    }

    /**
     * @desc mengopname al stock taken
     * @param employee id
     * @param al stock management id
     * @param tanggal pelaksanaan opname
     * @param jumlah hari yang seharusnya tersisa
     * @return boolean status proses opname (true jika berhasil, false jika gagal)
     */
    private static boolean opnameAlStockTaken(long employeeId, long lAlStockManId, Date opnameDate, float iTaken) {
        boolean isSuccess = true;

        float iCountTakenCurrPeriod = 0;

        //Mengupdate al taken yang masih dalam status hutang
        Vector vPayAble = new Vector();
        vPayAble = PstAlStockTaken.getAlPayable(employeeId);
        if (vPayAble.size() > 0) {
            for (int i = 0; i < vPayAble.size(); i++) {
                try {
                    AlStockTaken objAlStockTaken = new AlStockTaken();
                    objAlStockTaken = (AlStockTaken) vPayAble.get(i);
                    objAlStockTaken.setAlStockId(lAlStockManId);
                    objAlStockTaken.setPaidDate(opnameDate);
                    PstAlStockTaken.updateExc(objAlStockTaken);
                } catch (DBException ex) {
                    isSuccess = false;
                    ex.printStackTrace();
                }
            }
        }

        //Jika berhasil diupdate maka semua data yang taken akan diopname
        if (isSuccess) {
            String strWhere = "";

            strWhere = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] + " = " + employeeId + " AND " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID] + " = " + lAlStockManId;
            iCountTakenCurrPeriod = PstAlStockTaken.getSumLeave(strWhere);
            if (iCountTakenCurrPeriod != iTaken) {
                float temp = 0;
                temp = iTaken - iCountTakenCurrPeriod;
                AlStockTaken objAlStockTaken = new AlStockTaken();
                objAlStockTaken.setAlStockId(lAlStockManId);
                objAlStockTaken.setEmployeeId(employeeId);
                objAlStockTaken.setPaidDate(opnameDate);
                objAlStockTaken.setTakenDate(opnameDate);
                objAlStockTaken.setTakenQty(temp);
                try {
                    PstAlStockTaken.insertExc(objAlStockTaken);
                } catch (DBException ex) {
                    isSuccess = false;
                    ex.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    /**
     * @desc mengopname al stock expired
     * @param employee id
     * @param tanggal opname
     * @param jumlah al yang expired
     */
    private static boolean opnameAlStockExpired(long iAlStockManId, Date expiredDate, float iAlExpired) {
        boolean isSuccess = true;
        if (iAlExpired > 0) {
            Vector vAlStockExpired = new Vector();
            String strWhere = "";
            strWhere = PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_AL_STOCK_ID] + " = " + iAlStockManId;
            vAlStockExpired = PstAlStockExpired.list(0, 0, strWhere, PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_EXPIRED_DATE]);

            AlStockExpired objAlStockExpired = new AlStockExpired();

            if (vAlStockExpired.size() > 0) {
                objAlStockExpired = (AlStockExpired) vAlStockExpired.get(0);
                objAlStockExpired.setExpiredDate(expiredDate);
                objAlStockExpired.setExpiredQty(iAlExpired);
                try {
                    PstAlStockExpired.updateExc(objAlStockExpired);
                } catch (DBException ex) {
                    isSuccess = false;
                    ex.printStackTrace();
                }
            } else {
                objAlStockExpired.setExpiredDate(expiredDate);
                objAlStockExpired.setExpiredQty(iAlExpired);
                objAlStockExpired.setAlStockId(iAlStockManId);
                try {
                    PstAlStockExpired.insertExc(objAlStockExpired);
                } catch (DBException ex) {
                    isSuccess = false;
                    ex.printStackTrace();
                }
            }

            AlStockManagement alStockMan = new AlStockManagement();
            try {
                alStockMan = (AlStockManagement) PstAlStockManagement.fetchExc(iAlStockManId);
                if (alStockMan.getOID() > 0) {
                    alStockMan.setAlStatus(PstAlStockManagement.AL_STS_EXPIRED);
                    PstAlStockManagement.updateExc(alStockMan);
                }
            } catch (Exception ex) {
                isSuccess = false;
                System.out.println("SessAlUpload.opnameAlStockExpired [ERROR] :::::::: " + ex.toString());
            }
        }
        return isSuccess;
    }

    /**
     * @desc mengopname data stock management dari awal sampai 2 period sebelum opname  yang active menjadi expired
     * @param employee id
     * @param untilDate
     */
    public static boolean opnameAlStockManagementUntil(long employeeId, Date untilDate) {
        boolean status = false;
        DBResultSet dbResultSet = null;

        // get al management from previous upload with same period date
        String strSql = "SELECT " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] + ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] + " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " WHERE EMPLOYEE_ID = " + String.valueOf(employeeId) + " AND OWNING_DATE < \"" + Formater.formatDate(untilDate, "yyyy-MM-dd") + "\"" + " AND AL_STATUS = " + PstAlStockManagement.AL_STS_AKTIF;

        //System.out.println("[SQL] SessAlUpload.opnameAlStockManagementUntil :::: "+strSql);
        try {
            dbResultSet = DBHandler.execQueryResult(strSql);
            ResultSet rs = dbResultSet.getResultSet();
            while (rs.next()) {
                long alStockManId = 0;
                int qtyResidue = 0;
                //System.out.println(":::::::::::::::::::OPNAME AL STOCK EXPIRED ::::::::::::::::::");
                alStockManId = rs.getLong(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]);
                qtyResidue = rs.getInt(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE]);
                status = opnameAlStockExpired(alStockManId, untilDate, qtyResidue);
                //System.out.println("ID       : "+alStockManId);
                //System.out.println("RESIDUE  : "+qtyResidue);
            }
            status = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DBException ex) {
            ex.printStackTrace();
        }

        return status;
    }

    /**
     * @desc mencari banyak periode yang ada sampai tgl opname
     * @param employee id
     * @param tanggal opname
     * @return banyak periode sampai dengan tgl opname
     */
    private static int getPeriodAL(long employeeId, Date dateOpname) {
        int iPeriod = 0;

        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

        Employee objEmployee = new Employee();
        try {
            objEmployee = PstEmployee.fetchExc(employeeId);
        } catch (DBException ex) {
            ex.printStackTrace();
        }
        Date commencingDate = new Date();
        commencingDate = objEmployee.getCommencingDate();

        boolean isByCommencing = false;
        boolean adaStok = PstAlStockManagement.CekAdaDataStok(""+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = "+objEmployee.getOID());
               
        if (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING || (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING_PERIODE && !adaStok)) {
            isByCommencing = true;
        }
        if (isByCommencing) {
            int tempPeriod = 0;
            tempPeriod = dateOpname.getYear() - commencingDate.getYear();

            Date tempCommDate = new Date(dateOpname.getYear(), commencingDate.getMonth(), commencingDate.getDate());
            if (tempCommDate.getTime() < dateOpname.getTime()) {
                tempPeriod += -1;
            }
            iPeriod = tempPeriod;
        } else {
            int tempPeriod = 0;
            tempPeriod = dateOpname.getYear() - (commencingDate.getYear() + 1);
            iPeriod = tempPeriod;
        }
        if (iPeriod < 0) {
            iPeriod = 0;
        }
        return iPeriod;
    }

    /**
     * @desc memproses data pada al upload
     * @param vector id dari employee
     * 
     */
    //update by satrya 2013-01-06
    public static boolean opnameALAllData(Vector vAlUploadId,Date OpnameSelect) {
        
        // public static boolean opnameALAllData(Vector vAlUploadId) {
        boolean status = true;

        if (vAlUploadId == null && vAlUploadId.size() <= 0) {
            return status;
        }

        I_Leave leaveConfig = null;

        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

        for (int i = 0; i < vAlUploadId.size(); i++) {

            String strAlUploadId = "";
            strAlUploadId = (String) vAlUploadId.get(i);
            long alUploadId = 0;
            alUploadId = Long.parseLong(strAlUploadId);
            AlUpload alUpload = new AlUpload();

            try {

                alUpload = PstAlUpload.fetchExc(alUploadId);
                String strError = "";

                boolean adaStok = PstAlStockManagement.CekAdaDataStok(""+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = "+alUpload.getEmployeeId());
                boolean expiredCommencing = PstAlStockManagement.afterComencingYear(alUpload.getEmployeeId(), OpnameSelect); 
                /* JIka menggunakan configurasi by entitle / periode */
                if(leaveConfig.getALEntitleBy() == leaveConfig.AL_ENTITLE_ANUAL||leaveConfig.getALEntitleBy()==I_Leave.AL_ENTITLE_BY_PERIOD || ((leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING_PERIODE && adaStok) ||  (expiredCommencing && !adaStok) )) {

                   strError = processAlBalancingbyEntitle(alUpload, leaveConfig,OpnameSelect);
                           //update by satrya 2013-01-06
                  //strError = processAlBalancingbyEntitle(alUpload, leaveConfig,);
                } else {

                    strError = processALBalancing(alUpload);

                }

                if (strError.length() > 0) {
                    System.out.println("SessAlUpload opnameAlAllData [ERROR] ::::::::::::: " + strError);
                    return false;
                }

            } catch (DBException ex) {
                status = false;
                ex.printStackTrace();
                System.out.println("[ EXCEPTION ] :::: " + ex.toString());
            }
        }
        return status;
    }

    /**
     * @desc memproses data pada al upload
     * @param vector id dari employee
     * 
     */
    public static boolean opnameALAllData_HR(Vector vAlUploadId) {
        boolean status = true;
        for (int i = 0; i < vAlUploadId.size(); i++) {
            String strAlUploadId = "";
            strAlUploadId = (String) vAlUploadId.get(i);
            long alUploadId = 0;
            alUploadId = Long.parseLong(strAlUploadId);
            AlUpload alUpload = new AlUpload();
            try {
                alUpload = PstAlUpload.fetchExc(alUploadId);

                String strError = "";
                strError = proccessOpnameAL_HR(alUpload);
                if (strError.length() > 0) {
                    System.out.println("SessAlUpload opnameAlAllData [ERROR] ::::::::::::: " + strError);
                    return false;
                }
            } catch (DBException ex) {
                status = false;
                ex.printStackTrace();
            }
        }
        return status;
    }

    /*
     *
     * 
     *  
     */
    private static AlStockManagement getALStockManagementAktif(long employeeId) {

        AlStockManagement objAlStockManagement = new AlStockManagement();

        //DBResultSet dbrs;
        //update by satrya 2012-10-16
        String strWhere = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = " + employeeId + " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + " = " + PstAlStockManagement.AL_STS_AKTIF;

        Vector vAlStockManagement = new Vector();
        vAlStockManagement = PstAlStockManagement.list(0, 1, strWhere, PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + " ASC");

        if (vAlStockManagement.size() != 0) {
            objAlStockManagement = (AlStockManagement) vAlStockManagement.get(0);
        } else {
            return new AlStockManagement();
        }
        return objAlStockManagement;
    }

    public static AlUpload getDataUpload(long UploadID) {
        AlUpload uploadData = new AlUpload();

        String where = PstAlUpload.fieldNames[PstAlUpload.FLD_AL_UPLOAD_ID] + " = " + UploadID;

        Vector AlUploadData = PstAlUpload.list(0, 1, where, null);

        if (AlUploadData.size() > 0) {
            uploadData = (AlUpload) AlUploadData.get(0);
            return uploadData;
        } else {
            return null;
        }
    }

    public static Vector getIdStockManagementAktif(long employeeID) {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED] + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OPENING] + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_PREV_BALANCE] + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED] + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_RECORD_DATE] + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE] + " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT
                    + " WHERE " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = " + employeeID + " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + " = " + PstAlStockManagement.AL_STS_AKTIF;

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();
            Vector result = new Vector();

            while (rs.next()) {

                AlStockManagement alstockmanagement = new AlStockManagement();
                alstockmanagement.setEmployeeId(rs.getLong(1));
                alstockmanagement.setEntitled(rs.getInt(2));
                alstockmanagement.setOID(rs.getInt(3));
                alstockmanagement.setDtOwningDate(rs.getDate(4));
                alstockmanagement.setOpening(rs.getFloat(5));
                alstockmanagement.setPrevBalance(rs.getFloat(6));
                alstockmanagement.setAlQty(rs.getFloat(7));
                alstockmanagement.setQtyUsed(rs.getFloat(8));
                alstockmanagement.setQtyResidue(rs.getFloat(9));
                alstockmanagement.setAlStatus(rs.getInt(10));
                alstockmanagement.setEntitled(rs.getFloat(11));
                alstockmanagement.setRecordDate(rs.getDate(12));
                alstockmanagement.setEntitleDate(rs.getDate(13));

                result.add(alstockmanagement);
            }
            rs.close();
            return result;

        } catch (Exception e) {
            System.out.println(" [EXCEPTION ] :::: " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return null;

    }

    //add by Roy Andika
    public static AlStockManagement getValueStockManagement(long empID) {

        String where = PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT+"."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = " + empID + " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + " = " + PstAlStockManagement.AL_STS_AKTIF;
        Vector VStockManagement = PstAlStockManagement.listStockManagement(0, 1, where, null);

        if (VStockManagement.size() > 0) {
            AlStockManagement AlStockManagementData = (AlStockManagement) VStockManagement.get(0);
            return AlStockManagementData;
        }

        return null;
    }

    /**
     * @Author  Roy Andika
     * @param   empID
     * @return
     */
    public static AlUpload getOpnamePrevious(long empID) {

        String where = PstAlUpload.fieldNames[PstAlUpload.FLD_DATA_STATUS] + " = " + PstAlUpload.FLD_DOC_STATUS_NOT_PROCESS
                + " AND " + PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID] + " = " + empID;

        Vector vAlUpload = PstAlUpload.list(0, 1, where, null);

        if (vAlUpload.size() > 0) {
            AlUpload AlUploadData = (AlUpload) vAlUpload.get(0);
            return AlUploadData;
        } else {
            return null;
        }
    }

    //create by satrya 2013-01-06
    /**
     * 
     * @param empID
     * @param selected
     * @return 
     */
     public static AlUpload getALOpnamePrevious(long empID,long alUploadId) {
         String where = "p."+PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE] + " < (SELECT p1."
+ PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE] + "  FROM "+ PstAlUpload.TBL_AL_UPLOAD + " p1 WHERE p1."
+ PstAlUpload.fieldNames[PstAlUpload.FLD_AL_UPLOAD_ID] + " =\""+alUploadId+"\""+") AND "
+ PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID]+"="+empID;
       
        Vector vAlUpload = PstAlUpload.listAlOpname(empID, alUploadId,where);

        if (vAlUpload.size() > 0) {
            AlUpload AlUploadData = (AlUpload) vAlUpload.get(0);
            return AlUploadData;
        } else {
            return null;
        }
    }
/**
      * Create by satrya 2013-01-06
      * @param empID
      * @param alUploadId
      * @return 
      */
     public static AlUpload getALOpnameCurr(long empID,long alUploadId) {

       String where = "p."+PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE] + " > (SELECT p1."
+ PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE] + "  FROM "+ PstAlUpload.TBL_AL_UPLOAD + " p1 WHERE p1."
+ PstAlUpload.fieldNames[PstAlUpload.FLD_AL_UPLOAD_ID] + " =\""+alUploadId+"\""+") AND "
+ PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID]+"="+empID;
       
        Vector vAlUpload = PstAlUpload.listAlOpname(empID, alUploadId,where);

        if (vAlUpload.size() > 0) {
            AlUpload AlUploadData = (AlUpload) vAlUpload.get(0);
            return AlUploadData;
        } else {
            return null;
        }
    }
     /**
      * Create by satrya 2013-01-06
      * @param empID
      * @param alUploadId
      * @return 
      */
public static AlUpload getALOpnameSameDateOpname(long empID,long alUploadId) {

       String where = "p."+PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE] + " = (SELECT p1."
+ PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE] + "  FROM "+ PstAlUpload.TBL_AL_UPLOAD + " p1 WHERE p1."
+ PstAlUpload.fieldNames[PstAlUpload.FLD_AL_UPLOAD_ID] + " =\""+alUploadId+"\""+") AND "
+ PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID]+"="+empID;
       
        Vector vAlUpload = PstAlUpload.listAlOpname(empID, alUploadId,where);

        if (vAlUpload.size() > 0) {
            AlUpload AlUploadData = (AlUpload) vAlUpload.get(0);
            return AlUploadData;
        } else {
            return null;
        }
    }

     
    /**
     * @Author  Roy Andika
     * @Desc    Untuk melakukan proces balancing dengan type leave by entitle / by period
     * @return
     */
    public static String processAlBalancingbyEntitle(AlUpload alUploadArg, I_Leave leaveConfig,Date selectedDateOpName){

        /* Jika id dari al yang akan di apload tidak 0 */
        if (alUploadArg.getOID() != 0) {

            if (alUploadArg.getEmployeeId() != 0) { /* Jika kondisi employee id tidak sama dengan 0 */

                Employee employee = new Employee();
                Level level = new Level();
                EmpCategory empCategory = new EmpCategory();

                try {
                    employee = (Employee) PstEmployee.fetchExc(alUploadArg.getEmployeeId());
                } catch (Exception E) {
                    System.out.println("[exception]" + E.toString());
                }

                try {
                    level = (Level) PstLevel.fetchExc(employee.getLevelId());
                } catch (Exception E) {
                    System.out.println("[exception]" + E.toString());
                }

                try {
                    empCategory = (EmpCategory) PstEmpCategory.fetchExc(employee.getEmpCategoryId());
                } catch (Exception E) {
                    System.out.println("[exception]" + E.toString());
                }

                AlStockManagement alStockManagementData = getALStockManagementAktif(alUploadArg.getEmployeeId());

                AlStockManagement alStockNewData = new AlStockManagement();
            if(alUploadArg.getOpnameDate()!=null && alStockManagementData.getEntitleDate()!=null && alStockManagementData.getDtOwningDate()!=null 
                    && (com.dimata.util.DateCalc.timeDifference(alStockManagementData.getEntitleDate(), alUploadArg.getOpnameDate())>0 
                    ||com.dimata.util.DateCalc.timeDifference(alStockManagementData.getDtOwningDate(), alUploadArg.getOpnameDate())>0)
                    || (selectedDateOpName!=null && com.dimata.util.DateCalc.timeDifference(selectedDateOpName, alUploadArg.getOpnameDate())==0)){
                if (alStockManagementData.getOID() != 0) {

                    //untuk data yang baru, pertama data yang baru akan diisi data yang lama, setelah itu baru di set ulang
                    alStockNewData = alStockManagementData;
                    alStockNewData.setEntitled(alUploadArg.getNewAl());                   
                    alStockNewData.setAlQty(alUploadArg.getNewQty());                    
                    alStockNewData.setQtyUsed(alUploadArg.getCurrPerTaken());
                    alStockNewData.setOpening(alUploadArg.getLastPerToClear());
                    alStockNewData.setPrevBalance(alUploadArg.getLastPerToClear());
                    alStockNewData.setQtyResidue(alUploadArg.getLastPerToClear() + alUploadArg.getNewAl() - alUploadArg.getCurrPerTaken() /*update by satrya 2013-10-24 alUploadArg.getLastPerToClear() + alUploadArg.getNewQty() - alUploadArg.getCurrPerTaken()*/);

                    try {
                       
                            PstAlStockManagement.updateExc(alStockNewData);
                            System.out.println("Stock management insert");
                       

                    }catch (DBException ex) {
                        System.out.println("Error update stock management " + ex.toString());
                        return "BALANCING ERROR";
                    }

                } else { /* Kalau tidak di temukan stock yang aktiv, maka akan dilakukan proses closing */

                    /* Prosedur :
                     *  1. Cek jumlah data stock management yang dimiliki oleh employee tersebut
                     *  2. lakukan closing pertama
                     */
                    int countData = 0;

                    try {
                        /* Untuk mendapatkan jumlah data stock yang aktif dan tidak aktif berdasarkan employee yang diinginkan */
                        countData = SessLeaveClosing.countAlStock(alUploadArg.getEmployeeId());
                    } catch (Exception e) {
                        System.out.println("[exception] " + e.toString());
                    }

                    if (countData == 0) { // jika stock aktif dan tidak aktif belum ada

                        float entitle = 0;
                        int LoS = (int) DateCalc.dayDifference(employee.getCommencingDate(), new Date());
                        float tmpentitle = leaveConfig.getALEntitleAnualLeave(level.getLevel(), empCategory.getEmpCategory(), LoS, 
                                employee.getCommencingDate(),alUploadArg.getOpnameDate());

                        /*Untuk mendapatkan leave closing pertama */
                        Date tmpEntitleDt = SessLeaveApplication.getEntitleFirstClosing(employee.getCommencingDate(), leaveConfig);

                        if (tmpEntitleDt != null) {

                            if (tmpentitle == 0) { /* Jika entitle yang sebenarnya yang harus dia dapatkan adalah 0 */

                                entitle = 0;

                            } else {

                                entitle = leaveConfig.getQtyEntitle(tmpEntitleDt, level.getLevel(), empCategory.getEmpCategory(), employee.getCommencingDate());

                            }

                            AlStockManagement alStockManagement = new AlStockManagement();
                            alStockManagement.setLeavePeriodeId(0);
                            alStockManagement.setEmployeeId(alUploadArg.getEmployeeId());
                            alStockManagement.setEntitleDate(tmpEntitleDt);
                            alStockManagement.setOpening(0);
                            alStockManagement.setEntitled(alUploadArg.getNewAl());
                            alStockManagement.setAlQty(alUploadArg.getNewQty());
                            alStockManagement.setQtyUsed(alUploadArg.getCurrPerTaken()); // 7 Jan 2012 modified by Kartika
                            alStockManagement.setPrevBalance(alUploadArg.getLastPerToClear()); // 7 Jan 2012 modified by kartika
                            alStockManagement.setQtyResidue(alUploadArg.getLastPerToClear() + alUploadArg.getNewAl() - alUploadArg.getCurrPerTaken()/* update by satrya 2013-10-24 alUploadArg.getLastPerToClear() + alUploadArg.getNewQty() - alUploadArg.getCurrPerTaken()*/);
                            alStockManagement.setStNote("");
                            alStockManagement.setRecordDate(new Date());
                            alStockManagement.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);

                            try {
                                PstAlStockManagement.insertExc(alStockManagement);
                            } catch (Exception e) {
                                System.out.println("[exception] " + e.toString());
                            }
                        }

                    }
                    
                    /*
                    int countAlStockEmployee = 0; // parameter untuk menampung stock al yang dimiliki employee tersebut
                    /*
                    try {
                         Untuk mendapatkan jumlah data stock yang aktif dan tidak aktif berdasarkan employee yang diinginkan 
                        countAlStockEmployee = SessLeaveClosing.countAlStock(alUploadArg.getEmployeeId());

                    } catch (Exception e) {
                        System.out.println("[exception] " + e.toString());
                    }

                    int datePeriod = leaveConfig.getDatePeriod();

                    Date periodeDT = new Date();

                    periodeDT = SessLeaveClosing.getPeriodeClosing(employee.getCommencingDate(), datePeriod, leaveConfig);

                    if (countAlStockEmployee == 0) { // jika stock aktif dan tidak aktif belum ada

                        int entitle = 0;
                        int tmpentitle = leaveConfig.getALEntitleAnualLeave(level.getLevel(), empCategory.getEmpCategory());

                        if (tmpentitle == 0) { /* Jika entitle yang sebenarnya yang harus dia dapatkan adalah 0 */
                            //entitle = 0;
                        /*} else {
                            entitle = SessLeaveClosing.getEntitleEmployee(employee.getCommencingDate(), level.getLevel(), empCategory.getEmpCategory(), leaveConfig);
                        }

                        AlStockManagement alStockManagement = new AlStockManagement();
                        alStockManagement.setLeavePeriodeId(0);
                        alStockManagement.setEmployeeId(employee.getOID());
                        alStockManagement.setEntitleDate(periodeDT);
                        alStockManagement.setOpening(0);
                        alStockManagement.setEntitled(entitle);
                        alStockManagement.setAlQty(entitle);
                        alStockManagement.setQtyUsed(0);
                        alStockManagement.setPrevBalance(0);
                        alStockManagement.setStNote("");
                        alStockManagement.setRecordDate(new Date());
                        alStockManagement.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);

                        try {
                            PstAlStockManagement.insertExc(alStockManagement);
                        } catch (Exception e) {
                            System.out.println("[exception] " + e.toString());
                        }
                    }
                    */
                }
              }
            }
        }

        return "";
    }

    /** 
     * Created by roy, updated by Kartika
     * @param alUploadArg
     * @return
     */
    public static String processALBalancing(AlUpload alUploadArg) {
        String strError = "";
        long employeeId = 0;
        Date dateOpname = new Date();
        dateOpname = alUploadArg.getOpnameDate();
        employeeId = alUploadArg.getEmployeeId();
        AlUpload alUpload = new AlUpload();
        alUpload = alUploadArg;
        I_Leave leaveConfig = null;

        try{
            leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        }catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

        if (alUploadArg.getOID() !=0 ) {

            Date dateStartCurrPer = new Date();
            dateStartCurrPer = getStartPeriodDate(employeeId, dateOpname);
            Date dateStartLastPer = new Date();
            dateStartLastPer = (Date) dateStartCurrPer.clone();
            dateStartLastPer.setYear(dateStartLastPer.getYear() - 1);

            Date dateNextPeriode = new Date();
            dateNextPeriode = (Date) dateStartCurrPer.clone();
            dateNextPeriode.setYear(dateStartCurrPer.getYear() + 1);

            AlStockManagement alStockManagementData = getALStockManagementAktif(employeeId);

            if ((alStockManagementData.getOID() != 0) && !((dateOpname.getTime() >= dateStartCurrPer.getTime()) && (dateOpname.getTime() < dateNextPeriode.getTime()))) {
                if (dateOpname.getTime() <= dateStartCurrPer.getTime()) {
                    return "Old stock period is currently active please close the AL stock first.";
                } else {
                    return "Newer stock period is currently already active, cannot process the opname to newer period";
                }
            }


            if (alStockManagementData.getOID() == 0) {

                alStockManagementData.setEmployeeId(alUploadArg.getEmployeeId());
                alStockManagementData.setEntitleDate(dateStartCurrPer);
                alStockManagementData.setDtOwningDate(dateStartCurrPer);  // Add by Kartika
                alStockManagementData.setEntitleDate(dateStartCurrPer); // Add by Kartika
                Date dtExpired =  (Date) dateStartCurrPer.clone(); // Add by Kartika         
                boolean adaStok = PstAlStockManagement.CekAdaDataStok(""+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = "+employeeId);
                if(leaveConfig.getALEntitleBy()==leaveConfig.AL_ENTITLE_BY_COMMENCING || (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING_PERIODE && !adaStok)){
                    dtExpired.setYear(dtExpired.getYear()+1);  // Add by Kartika
                } else {
                    dtExpired.setMonth(11);  // Add by Kartika
                    dtExpired.setDate(31);  // Add by Kartika
                }
                alStockManagementData.setExpiredDate(dtExpired); // Add by Kartika
                alStockManagementData.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
                alStockManagementData.setLeavePeriodeId(0);
                alStockManagementData.setEntitled(alUploadArg.getNewAl()); 
                alStockManagementData.setAlQty(alUploadArg.getNewQty());
                alStockManagementData.setQtyUsed(alUploadArg.getCurrPerTaken());
                alStockManagementData.setOpening(alUploadArg.getLastPerToClear());
                alStockManagementData.setPrevBalance(alUploadArg.getLastPerToClear());
                alStockManagementData.setQtyResidue(alUploadArg.getLastPerToClear() + alUploadArg.getNewAl() - alUploadArg.getCurrPerTaken()/* update by satrya 2013-10-24 alUploadArg.getLastPerToClear() + alUploadArg.getNewQty() - alUploadArg.getCurrPerTaken()*/);
                alStockManagementData.setRecordDate(new Date());

                try {
                    PstAlStockManagement.insertExc(alStockManagementData);
                    System.out.println("Stock management insert");
                } catch (DBException ex) {
                    System.out.println("Error update stock management " + ex.toString());
                    return "Insert new AL stock data failed";
                }
            } else {

                boolean statusPrevExpired = SessLeaveApplication.getStatusLeaveAlExpired(alStockManagementData.getOID());

                alStockManagementData.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
                alStockManagementData.setLeavePeriodeId(0);
                alStockManagementData.setEntitled(alUploadArg.getNewAl());
                alStockManagementData.setAlQty(alUploadArg.getNewQty());
                alStockManagementData.setQtyUsed(alUploadArg.getCurrPerTaken());
                Date dtExpired =  (alStockManagementData.getEntitleDate()!=null) ? (Date) alStockManagementData.getEntitleDate().clone():
                     ( dateStartCurrPer!=null ? dateStartCurrPer : new Date()); // Add by Kartika 
                boolean adaStok = PstAlStockManagement.CekAdaDataStok(""+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = "+alUpload.getEmployeeId());
                
                if(leaveConfig.getALEntitleBy()==leaveConfig.AL_ENTITLE_BY_COMMENCING || (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING_PERIODE && !adaStok)){
                    dtExpired.setYear(dtExpired.getYear()+1);  // Add by Kartika
                } else {
                    dtExpired.setMonth(11);  // Add by Kartika
                    dtExpired.setDate(31);  // Add by Kartika
                }
                //jika status prev balance expired
                if (statusPrevExpired == true) {
                    alStockManagementData.setPrevBalance(alStockManagementData.getPrevBalance());
                } else {
                    alStockManagementData.setPrevBalance(alUploadArg.getLastPerToClear());
                }

                alStockManagementData.setQtyResidue((alStockManagementData.getPrevBalance()+alStockManagementData.getEntitled()) - alUploadArg.getCurrPerTaken()/*update by satrya 2013-10-24 alUploadArg.getNewQty() - alUploadArg.getCurrPerTaken()*/);
                //update by satrya 2013-10-02
                //alStockManagementData.setQtyResidue(alUploadArg.getLastPerToClear() + alUploadArg.getNewQty() - alUploadArg.getCurrPerTaken());
                alStockManagementData.setRecordDate(new Date());
                alStockManagementData.setExpiredDate(dtExpired);
                try {
                    PstAlStockManagement.updateExc(alStockManagementData);
                    System.out.println("Stock management update");
                } catch (DBException ex) {
                    System.out.println("Error update stock management " + ex.toString());
                    return "Update AL stock failed";
                }
            }


            if (alStockManagementData.getOID() != 0) {
                try {
                    int sum_taken = 0;
                    DBResultSet dbrs = null;

                    try {
                        String sql = "SELECT SUM(" + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + ") FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " WHERE " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID] + " = " + alStockManagementData.getOID();

                        dbrs = DBHandler.execQueryResult(sql);
                        ResultSet rs = dbrs.getResultSet();

                        while (rs.next()) {
                            sum_taken = rs.getInt(1);
                        }

                        rs.close();

                    } catch (Exception e) {
                        System.out.println("Exception " + e.toString());
                    }

                    if (alStockManagementData.getQtyUsed() > sum_taken) {
                        //insert taken qty (+)
                        float selisih = alStockManagementData.getQtyUsed() - sum_taken;
                        prosesInsertDataTaken(1, alStockManagementData.getOID(), alStockManagementData.getEmployeeId(), selisih, dateOpname);

                    } else if (alStockManagementData.getQtyUsed() < sum_taken) {
                        //insert taken qty (-)
                        float selisih = sum_taken - alStockManagementData.getQtyUsed();
                        prosesInsertDataTaken(2, alStockManagementData.getOID(), alStockManagementData.getEmployeeId(), selisih, dateOpname);
                    }

                    AlUpload objalUpload = new AlUpload();
                    objalUpload = getDataUpload(alUpload.getOID());
                    objalUpload.setDataStatus(PstAlUpload.FLD_DOC_STATUS_PROCESS);
                    PstAlUpload.updateExc(objalUpload);
                } catch (Exception e) {
                    System.out.println("Exception ::: " + e.toString());
                }
            }

        }
        return strError;
    }

    /**
     * @desc proses opname dari AL secara keseluruhan
     * @param employee id dan tanggal opname
     * @return string, error
     */
    public static String proccessOpnameAL(AlUpload alUploadArg) {
        String strError = "";
        long employeeId = 0;
        Date dateOpname = new Date();
        dateOpname = alUploadArg.getOpnameDate();
        employeeId = alUploadArg.getEmployeeId();
        AlUpload alUpload = new AlUpload();
        alUpload = alUploadArg;

        if (alUploadArg.getOID() > 0) {
            I_Leave leaveConfig = null;
            try {
                leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
            } catch (Exception e) {
                System.out.println("[ EXCEPTION ] :::: " + e.getMessage());
            }

            Employee employee = new Employee();

            try {
                employee = PstEmployee.fetchExc(employeeId);
            } catch (DBException ex) {
                strError = "[ERROR] ::: EMPLOYEE NOT FOUND";
                ex.printStackTrace();
            }
boolean adaStok = PstAlStockManagement.CekAdaDataStok(""+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = "+employeeId);
            // entitle counted on commencing date
            if (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING || (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING_PERIODE && !adaStok)) {

                // earn only once a year on beginning period
                if (leaveConfig.getAlEarnedBy() == I_Leave.AL_EARNED_BY_TOTAL) {

                    //Jika waktu commencing date employee lebih kecil dari waktu opname
                    Date dateStartCurrPer = new Date();
                    dateStartCurrPer = getStartPeriodDate(employeeId, dateOpname);
                    Date dateStartLastPer = new Date();
                    dateStartLastPer = (Date) dateStartCurrPer.clone();
                    dateStartLastPer.setYear(dateStartLastPer.getYear() - 1);

                    //Membersihkan data 2 periode sebelumnya
                    opnameAlStockManagementUntil(employeeId, dateStartLastPer);

                    //PENGECEKAN TO CLEAR LAST PERIOD
                    if (getPeriodAL(employeeId, dateOpname) > 1) {

                        AlStockManagement alStockManLastPer = new AlStockManagement();
                        alStockManLastPer = getALStockManagement(employeeId, dateStartLastPer);

                        //Jika pada last period masih ada yang tersisa                    
                        if (alUpload.getLastPerToClear() > 0) {
                            alStockManLastPer.setQtyResidue(alUpload.getLastPerToClear());
                            alStockManLastPer.setEmployeeId(employeeId);
                            alStockManLastPer.setEntitled(getAlEntitled(employeeId, new Date(dateOpname.getYear(), dateOpname.getMonth(), dateOpname.getDate())));
                            alStockManLastPer.setAlQty(getAlEarned(employeeId, new Date(dateOpname.getYear(), dateOpname.getMonth(), dateOpname.getDate())));
                            alStockManLastPer.setQtyUsed(alStockManLastPer.getAlQty() - alUpload.getLastPerToClear());
                            alStockManLastPer.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
                            alStockManLastPer.setDtOwningDate(dateStartLastPer);
                            alStockManLastPer.setLeavePeriodeId(0);
                            alStockManLastPer.setCommencingDateHave(dateStartLastPer);
                            alStockManLastPer.setStNote("AL OPNAME");
                            /*alStockManLastPer.setPrevEntitled(0);
                            alStockManLastPer.setPrevExpired(0);
                            alStockManLastPer.setPrevExtended(0);
                            alStockManLastPer.setPrevTaken(0);*/

                            //jika data al stock management telah ada sebelumnya
                            long alStockManId = 0;
                            if (alStockManLastPer.getOID() > 0) {
                                try {
                                    System.out.println("update stock management");
                                    alStockManId = PstAlStockManagement.updateExc(alStockManLastPer);
                                } catch (DBException ex) {
                                    strError = "FAILED TO UPDATE AL STOCK MANAGEMENT LAST PERSION!";
                                    ex.printStackTrace();
                                }
                            } else {
                                try {
                                    System.out.println("insert stock management");
                                    alStockManId = PstAlStockManagement.insertExc(alStockManLastPer);
                                } catch (DBException ex) {
                                    strError = "FAILED TO INSERT AL STOCK MANAGEMENT LAST PERSION!";
                                    ex.printStackTrace();
                                }
                            }
                            if (alStockManId > 0) {
                                opnameAlStockTaken(employeeId, alStockManId, dateOpname, alStockManLastPer.getAlQty() - alUpload.getLastPerToClear());
                                //to XXX
                            }

                        } //tidak ada sisa pada periode sebelumnya
                        else {
                            if (alStockManLastPer.getOID() > 0) {
                                opnameAlStockExpired(alStockManLastPer.getOID(), dateOpname, alStockManLastPer.getQtyResidue());
                            }//else to XXX
                        }
                    }

                    //PENGECEKAN TAKEN CURRENT PERIOD
                    float iTakenCurrPeriod = 0;
                    iTakenCurrPeriod = alUpload.getCurrPerTaken();

                    AlStockManagement alStockManCurrPer = new AlStockManagement();
                    alStockManCurrPer = getALStockManagement(employeeId, dateStartCurrPer);
                    long alStockManCurrPerID = 0;
                    alStockManCurrPerID = alStockManCurrPer.getOID();
                    //al stock management tidak ditemukan
                    if (alStockManCurrPer.getOID() <= 0) {
                        alStockManCurrPer.setEmployeeId(employeeId);
                        alStockManCurrPer.setAlQty(getAlEarned(employeeId, dateOpname));
                        alStockManCurrPer.setEntitled(getAlEntitled(employeeId, dateOpname));
                        alStockManCurrPer.setDtOwningDate(dateStartCurrPer);
                        alStockManCurrPer.setLeavePeriodeId(0);
                        alStockManCurrPer.setQtyResidue(alStockManCurrPer.getAlQty());
                        alStockManCurrPer.setQtyUsed(0);
                        alStockManCurrPer.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
                        alStockManCurrPer.setStNote("AL OPNAME");
                        try {
                            alStockManCurrPerID = PstAlStockManagement.insertExc(alStockManCurrPer);
                            alStockManCurrPer.setOID(alStockManCurrPerID);
                        } catch (DBException ex) {
                            strError = "FAILED TO INSERT AL STOCK MANAGEMENT CURRENT PERSION!";
                            ex.printStackTrace();

                        }
                    }
                    //Kerja lebih dari satu period, berarti memiliki last period
                    if (getPeriodAL(employeeId, dateOpname) > 1) {
                        AlStockManagement alStockManLastPer = new AlStockManagement();
                        alStockManLastPer = getALStockManagement(employeeId, dateStartLastPer);
                        //jika ada sisa maka residu dari periode sebelumnya harus diexpireadkan
                        if (alStockManLastPer.getOID() > 0 && alUpload.getLastPerToClear() <= 0) {
                            opnameAlStockExpired(alStockManLastPer.getOID(), dateOpname, alStockManLastPer.getQtyResidue());
                        }
                    }
                    //Pengesetan curr period
                    if (iTakenCurrPeriod > 0) {
                        alStockManCurrPer.setQtyUsed(iTakenCurrPeriod);
                        alStockManCurrPer.setQtyResidue((alStockManCurrPer.getAlQty() - iTakenCurrPeriod) < 0 ? 0 : (alStockManCurrPer.getAlQty() - iTakenCurrPeriod));
                        alStockManCurrPer.setAlStatus(alStockManCurrPer.getQtyResidue() > 0 ? PstAlStockManagement.AL_STS_AKTIF : PstAlStockManagement.AL_STS_TAKEN);

                        //Cek jika  ada pengambilan lebih dari yang diperoleh
                        if ((alStockManCurrPer.getAlQty() - iTakenCurrPeriod) < 0) {
                            opnameAlStockTaken(employeeId, alStockManCurrPerID, dateOpname, alStockManCurrPer.getAlQty());
                            AlStockTaken alStockTakenCurrPer = new AlStockTaken();
                            alStockTakenCurrPer.setAlStockTakenId(0);
                            alStockTakenCurrPer.setEmployeeId(employeeId);
                            alStockTakenCurrPer.setTakenDate(dateOpname);
                            alStockTakenCurrPer.setTakenQty(iTakenCurrPeriod - alStockManCurrPer.getAlQty());

                            // added
                            alStockTakenCurrPer.setPaidDate(dateOpname);
                            alStockTakenCurrPer.setAlStockId(alStockManCurrPer.getOID());

                            try {
                                PstAlStockTaken.insertExc(alStockTakenCurrPer);
                            } catch (DBException ex) {
                                strError = "FAILED TO INSERT AL STOCK TAKEN CURRENT PERSION!";
                                ex.printStackTrace();
                            }
                        } else {
                            opnameAlStockTaken(employeeId, alStockManCurrPerID, dateOpname, iTakenCurrPeriod);
                        }

                        try {
                            PstAlStockManagement.updateExc(alStockManCurrPer);
                        } catch (DBException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        opnameAlStockTaken(employeeId, alStockManCurrPerID, dateOpname, 0);
                    }

                    /* update stock management */
                    /***************************/
                    if (alStockManCurrPer.getOID() != 0) {
                        alStockManCurrPer.setAlQty(getAlEarned(employeeId, dateOpname));
                        alStockManCurrPer.setEntitled(getAlEntitled(employeeId, dateOpname));
                        alStockManCurrPer.setQtyResidue(alStockManCurrPer.getAlQty() - iTakenCurrPeriod);
                        alStockManCurrPer.setQtyUsed(iTakenCurrPeriod);
                        try {
                            PstAlStockManagement.updateExc(alStockManCurrPer);
                        } catch (DBException ex) {
                            ex.printStackTrace();
                        }
                    }
                    /***************************/
                    // Bukan total earned
                } else {
                    // not implemented
                }

                // By NEW YEAR, bukan commencing
            } else {
                // not implemented
            }

            // alUploadArg.getOID() == 0
        } else {
            strError = "Al Upload data not found!";
        }

        //MENDAFTARKAN STATUS AL YANG TELAH DIPROSES
        if (strError.length() <= 0) {
            alUploadArg.setDataStatus(PstAlUpload.FLD_DOC_STATUS_PROCESS);
            try {
                PstAlUpload.updateExc(alUploadArg);
            } catch (DBException ex) {
                ex.printStackTrace();
            }
        }
        return strError;
    }

    /**
     * @desc  reset nilai AL management dari AL upload pada saat opname ulang
     * @param oid
     */
    public static void resetAL(long oid) {
        DBResultSet dbrs = null;

        try {
            String sql = " UPDATE " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT
                    + " SET " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + " = 0 "
                    + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] + " = 0 "
                    + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] + "= 0"
                    + "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED] + "= 0"
                    + " WHERE " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + "=" + oid
                    + " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + " <> 0";        // qty = 0 untuk al request yang belum ditaken

            int status = DBHandler.execUpdate(sql);

            //add by artha
            PstAlStockTaken.resetAL(oid);
            Vector vListAlMan = new Vector(1, 1);
            //update by satrya 2012-10-16
            String where = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + "=" + oid;
            vListAlMan = PstAlStockManagement.list(0, 0, where, null);
            for (int i = 0; i < vListAlMan.size(); i++) {
                AlStockManagement objAlStockManagement = (AlStockManagement) vListAlMan.get(i);
                PstAlStockExpired.deleteByAlStock(objAlStockManagement.getOID());
            }

            System.out.println("SQL RESET AL  " + sql);
        } catch (Exception e) {
            System.err.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     * @desc proses opname dari AL secara keseluruhan
     * @param employee id dan tanggal opname
     * @return string, error
     */
    public static String proccessOpnameAL_HR(AlUpload alUploadArg) {
        String strError = "";

        Date dateOpname = alUploadArg.getOpnameDate();
        long employeeId = alUploadArg.getEmployeeId();
        AlUpload alUpload = alUploadArg;

        // if al upload is available
        if (alUploadArg.getOID() > 0) {
            I_Leave leaveConfig = null;
            try {
                leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }

            Employee employee = new Employee();
            try {
                employee = PstEmployee.fetchExc(employeeId);
            } catch (DBException ex) {
                strError = "EMPLOYEE NOT FOUND!";
                ex.printStackTrace();
            }

            // TRY TO REMOVE AL RECORD HERE
            resetAL(employeeId);
            boolean adaStok = PstAlStockManagement.CekAdaDataStok(""+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = "+employeeId);
            // entitled got based on commencing date
            if (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING || (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING_PERIODE && !adaStok) ) {

                // got on yearly period
                if (leaveConfig.getAlEarnedBy() == I_Leave.AL_EARNED_BY_TOTAL) {

                    Date dateStartCurrPer = getStartPeriodDate(employeeId, dateOpname);
                    Date dateStartLastPer = (Date) dateStartCurrPer.clone();
                    dateStartLastPer.setYear(dateStartLastPer.getYear() - 1);

                    // PAY ATTENTION ON THIS
                    // Membersihkan data 2 periode sebelumnya
                    opnameAlStockManagementUntil(employeeId, dateStartLastPer);


                    // PENGECEKAN TO CLEAR LAST PERIOD
                    // hitung banyak periode yang ada (dlm th) dari commencing sampai date opname
                    if (getPeriodAL(employeeId, dateOpname) >= 1) {

                        AlStockManagement alStockManLastPer = new AlStockManagement();

                        // Jika pada last period masih ada yang tersisa    
                        // nilai C/F diinput oleh user
                        if (alUpload.getLastPerToClear() > 0) {
                            String empLevel = "";
                            String empType = "";

                            try {
                                Level level = PstLevel.fetchExc(employee.getLevelId());
                                empLevel = level.getLevel();
                            } catch (Exception e) {
                            }

                            try {
                                EmpCategory cat = PstEmpCategory.fetchExc(employee.getEmpCategoryId());
                                empType = cat.getEmpCategory();
                            } catch (Exception e) {
                            }

                            // mendapatkan jumlah jatah entitle AL tiap employee berdasar kategori dan level
                            int LoS = (int) DateCalc.dayDifference(employee.getCommencingDate(), new Date());
                            float alEntitle = leaveConfig.getALEntitleAnualLeave(empLevel, empType, LoS, 
                                employee.getCommencingDate(),dateOpname);

                            // jika CF lebih besar dari entitle tahunan (carry sisa AL dari th sebelumnya + AL tahun sekarang)   
                            if (alUpload.getLastPerToClear() > alEntitle) {
                                float numOfPrevCF = alUpload.getLastPerToClear() / alEntitle;     // akumulasi jumlah tahun untuk nominal AL opname
                                float residueCF = alUpload.getLastPerToClear() % alEntitle;       // sisa AL terakhir setelah dikurangi taken (pada tahun terlama)

                                Calendar calendar = new GregorianCalendar(dateStartCurrPer.getYear() + 1900, dateStartCurrPer.getMonth(), dateStartCurrPer.getDate());

                                // process previous CF
                                // hitung mundur tahun upload dimulai periode sekarang 
                                for (int i = 0; i < numOfPrevCF; i++) {

                                    // ambil AL management yang telah tersimpan sesuai peiode opname jika ada
                                    alStockManLastPer = getALStockManagement(employeeId, calendar.getTime());

                                    alStockManLastPer.setQtyResidue(alEntitle);
                                    alStockManLastPer.setEmployeeId(employeeId);
                                    alStockManLastPer.setEntitled(getAlEntitled(employeeId, new Date(dateOpname.getYear(), dateOpname.getMonth(), dateOpname.getDate())));
                                    alStockManLastPer.setAlQty(getAlEarned(employeeId, new Date(dateOpname.getYear(), dateOpname.getMonth(), dateOpname.getDate())));
                                    alStockManLastPer.setQtyUsed(alStockManLastPer.getAlQty() - alStockManLastPer.getQtyResidue());
                                    alStockManLastPer.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
                                    alStockManLastPer.setDtOwningDate(calendar.getTime());
                                    alStockManLastPer.setLeavePeriodeId(0);
                                    alStockManLastPer.setStNote("AL OPNAME");

                                    //lanjutkan ke periode sebelumnya
                                    calendar.add(Calendar.YEAR, -1);

                                    //jika data al stock management telah ada sebelumnya
                                    long alStockManId = 0;
                                    if (alStockManLastPer.getOID() > 0) {
                                        try {
                                            alStockManId = PstAlStockManagement.updateExc(alStockManLastPer);
                                        } catch (DBException ex) {
                                            strError = "FAILED TO UPDATE AL STOCK MANAGEMENT LAST PERSION!";
                                            ex.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            alStockManId = PstAlStockManagement.insertExc(alStockManLastPer);
                                        } catch (DBException ex) {
                                            strError = "FAILED TO INSERT AL STOCK MANAGEMENT LAST PERSION!";
                                            ex.printStackTrace();
                                        }
                                    }
                                }

                                // process residue CF untuk tahun terjauh (current calendar value)                        
                                alStockManLastPer = getALStockManagement(employeeId, calendar.getTime());

                                alStockManLastPer.setQtyResidue(residueCF);
                                alStockManLastPer.setEmployeeId(employeeId);
                                alStockManLastPer.setEntitled(getAlEntitled(employeeId, new Date(dateOpname.getYear(), dateOpname.getMonth(), dateOpname.getDate())));
                                alStockManLastPer.setAlQty(getAlEarned(employeeId, new Date(dateOpname.getYear(), dateOpname.getMonth(), dateOpname.getDate())));
                                alStockManLastPer.setQtyUsed(alStockManLastPer.getAlQty() - alStockManLastPer.getQtyResidue());
                                alStockManLastPer.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
                                alStockManLastPer.setDtOwningDate(calendar.getTime());
                                alStockManLastPer.setLeavePeriodeId(0);
                                alStockManLastPer.setStNote("AL OPNAME");

                                //jika data al stock management telah ada sebelumnya
                                long alStockManId = 0;
                                if (alStockManLastPer.getOID() > 0) {
                                    try {
                                        alStockManId = PstAlStockManagement.updateExc(alStockManLastPer);
                                    } catch (DBException ex) {
                                        strError = "FAILED TO UPDATE AL STOCK MANAGEMENT LAST PERSION!";
                                        ex.printStackTrace();
                                    }
                                } else {
                                    try {
                                        alStockManId = PstAlStockManagement.insertExc(alStockManLastPer);
                                    } catch (DBException ex) {
                                        strError = "FAILED TO INSERT AL STOCK MANAGEMENT LAST PERSION!";
                                        ex.printStackTrace();
                                    }
                                }

                                // jika al stock man berhasil diupdate/diinsert, update nilai taken AL
                                if (alStockManId > 0) {
                                    opnameAlStockTaken(employeeId, alStockManId, calendar.getTime(), alStockManLastPer.getQtyUsed());
                                }
                            } //alUpload.getLastPerToClear() <= alEntitle
                            /* upload < entitle */ else {
                                Calendar calendar = new GregorianCalendar(dateStartCurrPer.getYear() + 1900, dateStartCurrPer.getMonth(), dateStartCurrPer.getDate());
                                alStockManLastPer = getALStockManagement(employeeId, calendar.getTime());

                                alStockManLastPer.setQtyResidue(alUpload.getLastPerToClear());
                                alStockManLastPer.setEmployeeId(employeeId);
                                alStockManLastPer.setEntitled(getAlEntitled(employeeId, new Date(dateOpname.getYear(), dateOpname.getMonth(), dateOpname.getDate())));
                                alStockManLastPer.setAlQty(getAlEarned(employeeId, new Date(dateOpname.getYear(), dateOpname.getMonth(), dateOpname.getDate())));
                                alStockManLastPer.setQtyUsed(alStockManLastPer.getAlQty() - alUpload.getLastPerToClear());
                                alStockManLastPer.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
                                //alStockManLastPer.setDtOwningDate(dateStartLastPer);
                                alStockManLastPer.setDtOwningDate(calendar.getTime());
                                alStockManLastPer.setLeavePeriodeId(0);
                                alStockManLastPer.setStNote("AL OPNAME");

                                //jika data al stock management telah ada sebelumnya
                                long alStockManId = 0;
                                if (alStockManLastPer.getOID() > 0) {
                                    try {
                                        alStockManId = PstAlStockManagement.updateExc(alStockManLastPer);
                                    } catch (DBException ex) {
                                        strError = "FAILED TO UPDATE AL STOCK MANAGEMENT LAST PERSION!";
                                        ex.printStackTrace();
                                    }
                                } else {
                                    try {
                                        alStockManId = PstAlStockManagement.insertExc(alStockManLastPer);
                                    } catch (DBException ex) {
                                        strError = "FAILED TO INSERT AL STOCK MANAGEMENT LAST PERSION!";
                                        ex.printStackTrace();
                                    }
                                }

                                if (alStockManId > 0) {
                                    //opnameAlStockTaken(employeeId, alStockManId, dateOpname, alStockManLastPer.getAlQty()-alUpload.getLastPerToClear());
                                    opnameAlStockTaken(employeeId, alStockManId, calendar.getTime(), alStockManLastPer.getAlQty() - alUpload.getLastPerToClear());
                                }
                            }

                            /* upload < entitle */
                        } //tidak ada sisa pada periode sebelumnya
                        else {
                            if (alStockManLastPer.getOID() > 0) {
                                opnameAlStockExpired(alStockManLastPer.getOID(), dateOpname, alStockManLastPer.getQtyResidue());
                            }//else to XXX
                        }
                    }

                    // achieved on monthly period
                } else { /* not implemented */ }

                // achieved by new year
            } else { /* not implemented */ }

            // al upload is not available
        } else {
            strError = "Al Upload data not found!";
        }

        //MENDAFTARKAN STATUS AL YANG TELAH DIPROSES
        if (strError.length() <= 0) {
            alUploadArg.setDataStatus(PstAlUpload.FLD_DOC_STATUS_PROCESS);

            try {
                PstAlUpload.updateExc(alUploadArg);
            } catch (DBException ex) {
                ex.printStackTrace();
                System.out.println("Exception : " + ex.toString());
            }
        }

        return strError;
    }

    /**
     * @desc mencari jumlah data yang akan ditampilkan per tgl
     * @return int jumlah data
     */
    public static Vector getAllAlUpload(int start, int recordToGet,String EmpNumb) {
        Vector vAlUploadOpname = new Vector();
        //select distinct taken_date from hr_ll_stock_taken
        DBResultSet dbResultSet = null;
        String sql = "SELECT DISTINCT " + PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE] + " FROM " + PstAlUpload.TBL_AL_UPLOAD + " AS AU "
        //update by satrya 2013-01-06
        + " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE 
        + " AS EMP ON(AU."+PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID]+"=EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+") WHERE (1=1) ";
        //update by satrya 2013-01-06        
        if(EmpNumb != null && EmpNumb.length() > 0){
                sql += " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+"="+EmpNumb;
        }
        sql+= " ORDER BY " + PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE];
        if (start != 0 || recordToGet != 0) {
            sql += " LIMIT " + start + "," + recordToGet;
        }
        //System.out.println("All al upload...SQL ::: " + sql);
        try {
            dbResultSet = DBHandler.execQueryResult(sql);
            ResultSet rs = dbResultSet.getResultSet();
            while (rs.next()) {
                Date date = rs.getDate(PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE]);
                vAlUploadOpname.add(Formater.formatDate(date, "yyyy-MM-dd"));
            }
        } catch (Exception ex) {
            System.out.println("Exception " + ex.toString());
        }
        return vAlUploadOpname;
    }

    public static void ClosingByYear() {
        Vector emp = new Vector();
        DBResultSet dbresultset = null;

        String sql = "";

        try {
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static boolean searchData(Date opname, long empID) {
        boolean status = false;
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID] + " FROM "
                    + PstAlUpload.TBL_AL_UPLOAD + " WHERE " + PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE]
                    + " = " + opname + " AND " + PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID] + " = " + empID;

            System.out.println("sql cek upload " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                status = true;
            }
            rs.close();
            return status;

        } catch (Exception e) {
            System.out.println("Exception : " + e.toString());
        }

        return status;
    }

    public void cekStatusOpname(Date DateOpname, Date commencingDate) {

        Date nextCommencingDate = new Date();
        int int_nextYear = 0;

        String date_month = commencingDate.toString().substring(0, 4);
        String str_year_commencingDate = Formater.formatDate(commencingDate, "yyyy");
        int_nextYear = Integer.parseInt(str_year_commencingDate) + 1;

        String nextYear = date_month + "-" + str_year_commencingDate;

    }

    public static Date getDateInsertTakenDate(long empId, long stockId, Date opnameDate) {
        //DBResultSet dbrs = null;
        //Vector listStockTaken = new Vector();
        try {

            /*
            String where = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]+" = "+empId+" AND "+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID]+" = "+stockId ;
            listStockTaken = PstAlStockTaken.list(0, 0, where, ""+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+" desc ");
            AlStockTaken objAlStockTaken = (AlStockTaken)listStockTaken.get(0);
            Date lastDateTaken = objAlStockTaken.getTakenDate();
             */
            boolean statusTakenNotEmpty = true;

            int tanggal = opnameDate.getDate();
            int bulan = opnameDate.getMonth() + 1;
            int tahun = opnameDate.getYear() + 1900;

            while (statusTakenNotEmpty == true) {

                String dateSekarang = tahun + "-" + bulan + "-" + tanggal;

                Date dateformat = Formater.formatDate(dateSekarang, "yyyy-MM-dd");

                String dateformatString = Formater.formatDate(dateformat, "yyyy-MM-dd");

                statusTakenNotEmpty = statusTakeDate(empId, stockId, dateformatString);

                if (statusTakenNotEmpty == false) {
                    return dateformat;
                }

                tanggal--;

                if (tanggal == 0) {
                    if (bulan < 0) {
                        tahun--;
                        bulan = 11;
                        if (bulan == 1) {
                            tanggal = 31;
                        } else if (bulan == 2) {
                            tanggal = 28;
                        } else if (bulan == 3) {
                            tanggal = 31;
                        } else if (bulan == 4) {
                            tanggal = 30;
                        } else if (bulan == 5) {
                            tanggal = 31;
                        } else if (bulan == 6) {
                            tanggal = 30;
                        } else if (bulan == 7) {
                            tanggal = 31;
                        } else if (bulan == 8) {
                            tanggal = 31;
                        } else if (bulan == 9) {
                            tanggal = 30;
                        } else if (bulan == 10) {
                            tanggal = 31;
                        } else if (bulan == 11) {
                            tanggal = 30;
                        } else if (bulan == 11) {
                            tanggal = 31;
                        }

                    } else {
                        if (bulan == 1) {
                            tanggal = 31;
                        } else if (bulan == 2) {
                            tanggal = 28;
                        } else if (bulan == 3) {
                            tanggal = 31;
                        } else if (bulan == 4) {
                            tanggal = 30;
                        } else if (bulan == 5) {
                            tanggal = 31;
                        } else if (bulan == 6) {
                            tanggal = 30;
                        } else if (bulan == 7) {
                            tanggal = 31;
                        } else if (bulan == 8) {
                            tanggal = 31;
                        } else if (bulan == 9) {
                            tanggal = 30;
                        } else if (bulan == 10) {
                            tanggal = 31;
                        } else if (bulan == 11) {
                            tanggal = 30;
                        } else if (bulan == 12) {
                            tanggal = 31;
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION :::" + e.toString());
        }

        return null;
    }

    public static boolean statusTakeDate(long empId, long stockId, String dateTake) {
        DBResultSet dbrs = null;
        Vector listStockTaken = new Vector();
        try {
            String where = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] + " = " + empId + " AND " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID] + " = " + stockId + " AND " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " = '" + dateTake + "'";

            System.out.println("Where " + where);

            listStockTaken = PstAlStockTaken.list(0, 0, where, null);

            if (listStockTaken.size() > 0) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION ::::" + e.toString());
        }
        return false;
    }

    public static long prosesInsertDataTaken(int statusInsert, long stockId, long empId, float selisih, Date opnameDate) {

        Date dateSekarang = getDateInsertTakenDate(empId, stockId, opnameDate);

        long StockTakenId = 0;

        if (statusInsert == 1) {
            //data qty di stock taken < dari pada stock management
            float qty = selisih;
            AlStockTaken objAlStockTaken = new AlStockTaken();
            //PstAlStockTaken a = new PstAlStockTaken();

            objAlStockTaken.setEmployeeId(empId);
            objAlStockTaken.setAlStockId(stockId);
            objAlStockTaken.setTakenFromStatus(PstAlStockTaken.TAKEN_FROM_STATUS_SYSTEM);
            objAlStockTaken.setTakenQty(qty);
            objAlStockTaken.setTakenDate(dateSekarang);
            try {
                StockTakenId = PstAlStockTaken.insertExc(objAlStockTaken);
            } catch (Exception e) {
                System.out.println("EXCEPTION ::" + e.toString());
            }
            return StockTakenId;

        } else if (statusInsert == 2) {
            //data qty di stock taken > dari pada stock management
            float qty = 0 - selisih;
            AlStockTaken objAlStockTaken = new AlStockTaken();

            objAlStockTaken.setEmployeeId(empId);
            objAlStockTaken.setAlStockId(stockId);
            objAlStockTaken.setTakenFromStatus(PstAlStockTaken.TAKEN_FROM_STATUS_SYSTEM);
            objAlStockTaken.setTakenQty(qty);
            objAlStockTaken.setTakenDate(dateSekarang);

            try {
                StockTakenId = PstAlStockTaken.insertExc(objAlStockTaken);
            } catch (Exception e) {
                System.out.println("EXCEPTION ::" + e.toString());
            }
            return StockTakenId;
        }

        return 0;
    }
}
