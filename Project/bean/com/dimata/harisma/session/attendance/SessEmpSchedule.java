/*
 * Session Name  	:  SessEmpSchedule.java
 * Created on 	:  [date] [time] AM/PM
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
package com.dimata.harisma.session.attendance;

import com.dimata.harisma.entity.attendance.AttendanceReportDaily;
import com.dimata.util.LogicParser;
import com.dimata.harisma.entity.search.SrcEmpSchedule;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.EmpSchedule;
import com.dimata.harisma.entity.attendance.PstAlStockTaken;
import com.dimata.harisma.entity.attendance.PstDpStockTaken;
import com.dimata.harisma.entity.attendance.PstLlStockTaken;
import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.harisma.entity.leave.PstSpecialUnpaidLeaveTaken;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.payroll.PayPeriod;
import com.dimata.harisma.entity.payroll.PstPayGeneral;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.harisma.entity.search.SrcReportDailyXls;
import com.dimata.harisma.form.search.FrmSrcEmpSchedule;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import com.dimata.qdep.db.DBException;
import com.dimata.system.entity.system.*;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.*;

import com.dimata.util.DateCalc;
import com.dimata.util.Formater;
import java.util.Vector;
import java.util.GregorianCalendar;
import java.util.Date;
import java.sql.ResultSet;

/* java package */
import java.util.Hashtable;

public class SessEmpSchedule {

    public static final String TBL_VIEW_EMP_SCHEDULE = "hr_view_emp_schedule";
    public static final String SESS_SRC_EMPSCHEDULE = "SESSION_SRC_EMPSCHEDULE";
    //update by satrya
    //private static final String SESS_SRC_EMPSCHEDULE = "SESSION_SRC_EMPSCHEDULE";

    /**
     * @return the SESS_SRC_EMPSCHEDULE
     */
    public static String getSESS_SRC_EMPSCHEDULE() {
        return SESS_SRC_EMPSCHEDULE;
    }
    //update by satrya 2012-07-18
    private Date fromDate = null;
    private Date toDate = null;
    private String empNum;
    private String empFullName;
    private long departement;
    private long section;
    private int start;//untuk start
    private String status1;
    //update by satrya 2013-04-08
    private int reasonSts;
    private long oidCompany;
    private long oidDivision;
    //public static Vector searchEmpSchedule(SrcEmpSchedule srcempschedule, int start, int recordToGet){
    //Vector result = new Vector(1,1);
    //return result;

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

    public static Vector searchEmpSchedule(SrcEmpSchedule srcempschedule, int start, int recordToGet) {

        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        if (srcempschedule == null) {
            return new Vector(1, 1);
        }

        try {
            String sql = " SELECT EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND2]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND3]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND4]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND5]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND6]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND7]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND8]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND9]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND10]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND11]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND12]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND13]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND14]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND15]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND16]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND17]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND18]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND19]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND20]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND21]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND22]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND23]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND24]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND25]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND26]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND27]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND28]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND29]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND30]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND31]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]
                    + ", PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]
                    + ", PRD." + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]
                    + " FROM "
                    + " " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " EMPSCD "
                    + " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstPeriod.TBL_HR_PERIOD + " PRD "
                    + " WHERE "
                    + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]
                    + " AND EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + //added on Wed, 23-01-2003
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = 0 ";

            String whereClause = "";
            if ((srcempschedule.getEmployee() != null) && (srcempschedule.getEmployee().length() > 0)) {
                Vector vectName = logicParser(srcempschedule.getEmployee());
                if (vectName != null && vectName.size() > 0) {
                    //whereClause = whereClause + " AND (";
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            //String s = "";
            //s = srcempschedule.getPeriod();
            //System.out.println("\tsrcempschedule.getPeriod() = " + srcempschedule.getPeriod());
            if (srcempschedule.getPeriod().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " = " + srcempschedule.getPeriod() + " AND ";
            }

            //s = srcempschedule.getDepartment();
            if (srcempschedule.getDepartment().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcempschedule.getDepartment() + " AND ";
            }

            //s = srcempschedule.getPosition();
            //System.out.println("srcempschedule.getPosition() = " + srcempschedule.getPosition());
            if (srcempschedule.getPosition().compareToIgnoreCase("0") > 0) {
                //System.out.println("srcempschedule.getPosition() = " + srcempschedule.getPosition());
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcempschedule.getPosition() + " AND ";
            }

            if (srcempschedule.getSection().compareToIgnoreCase("0") > 0) {
                //System.out.println("srcempschedule.getSection() = " + srcempschedule.getSection());
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcempschedule.getSection() + " AND ";
            }

            /*
             if(srcEmployee.getDepartment() != 0)
             whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
             " = "+srcEmployee.getDepartment() + " AND ";
             if(srcEmployee.getPosition() != 0)
             whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
             " = "+ srcEmployee.getPosition() + " AND ";
             if(srcEmployee.getSection() != 0)
             whereClause = whereClause +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
             " = "+ srcEmployee.getSection() + " AND ";
             */
            if (whereClause != null && whereClause.length() > 0) {
                //whereClause = whereClause.substring(0,whereClause.length()-4);
                //System.out.println("\twhereClause.length() = " + whereClause.length());
                whereClause += " 1 = 1 ";
                sql = sql + " AND " + whereClause;
            }

            //sql = sql + " GROUP BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+ " LIMIT " + start + "," + recordToGet;
            sql = sql + " ORDER BY EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID];

            if (start != 0 || recordToGet != 0) {
                sql = sql + " LIMIT " + start + "," + recordToGet;
            }

//            System.out.println("\t SQL searchEmpSchedule : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                EmpSchedule empschedule = new EmpSchedule();
                Employee employee = new Employee();
                Period period = new Period();
                //                Department department = new Department();
                //                Position position = new Position();
                //                Section section = new Section();
                //                EmpCategory empcategory = new EmpCategory();
                //                Level level = new Level();

                empschedule.setOID(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
                empschedule.setPeriodId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]));
                empschedule.setEmployeeId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]));
                empschedule.setD1(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1]));
                empschedule.setD2(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2]));
                empschedule.setD3(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3]));
                empschedule.setD4(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4]));
                empschedule.setD5(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5]));
                empschedule.setD6(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6]));
                empschedule.setD7(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7]));
                empschedule.setD8(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8]));
                empschedule.setD9(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9]));
                empschedule.setD10(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10]));
                empschedule.setD11(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11]));
                empschedule.setD12(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12]));
                empschedule.setD13(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13]));
                empschedule.setD14(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14]));
                empschedule.setD15(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15]));
                empschedule.setD16(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16]));
                empschedule.setD17(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17]));
                empschedule.setD18(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18]));
                empschedule.setD19(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19]));
                empschedule.setD20(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20]));
                empschedule.setD21(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21]));
                empschedule.setD22(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22]));
                empschedule.setD23(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23]));
                empschedule.setD24(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24]));
                empschedule.setD25(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25]));
                empschedule.setD26(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26]));
                empschedule.setD27(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27]));
                empschedule.setD28(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28]));
                empschedule.setD29(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29]));
                empschedule.setD30(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30]));
                empschedule.setD31(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31]));

                empschedule.setD2nd1(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1]));
                empschedule.setD2nd2(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND2]));
                empschedule.setD2nd3(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND3]));
                empschedule.setD2nd4(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND4]));
                empschedule.setD2nd5(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND5]));
                empschedule.setD2nd6(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND6]));
                empschedule.setD2nd7(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND7]));
                empschedule.setD2nd8(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND8]));
                empschedule.setD2nd9(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND9]));
                empschedule.setD2nd10(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND10]));
                empschedule.setD2nd11(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND11]));
                empschedule.setD2nd12(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND12]));
                empschedule.setD2nd13(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND13]));
                empschedule.setD2nd14(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND14]));
                empschedule.setD2nd15(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND15]));
                empschedule.setD2nd16(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND16]));
                empschedule.setD2nd17(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND17]));
                empschedule.setD2nd18(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND18]));
                empschedule.setD2nd19(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND19]));
                empschedule.setD2nd20(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND20]));
                empschedule.setD2nd21(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND21]));
                empschedule.setD2nd22(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND22]));
                empschedule.setD2nd23(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND23]));
                empschedule.setD2nd24(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND24]));
                empschedule.setD2nd25(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND25]));
                empschedule.setD2nd26(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND26]));
                empschedule.setD2nd27(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND27]));
                empschedule.setD2nd28(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND28]));
                empschedule.setD2nd29(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND29]));
                empschedule.setD2nd30(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND30]));
                empschedule.setD2nd31(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND31]));

                vect.add(empschedule);

                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                vect.add(employee);

                period.setOID(rs.getLong(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]));
                period.setPeriod(rs.getString(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]));
                period.setStartDate(rs.getDate(PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]));
                vect.add(period);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on searchEmpSchedule : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /**
     * @Author Roy A
     * @param srcempschedule
     * @param levelId
     * @param start
     * @param recordToGet
     * @return
     */
    public static Vector searchEmpSchedule(SrcEmpSchedule srcempschedule, String levelId[], int start, int recordToGet) {

        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        if (srcempschedule == null) {
            return new Vector(1, 1);
        }

        try {
            String sql = " SELECT EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND2]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND3]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND4]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND5]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND6]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND7]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND8]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND9]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND10]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND11]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND12]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND13]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND14]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND15]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND16]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND17]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND18]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND19]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND20]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND21]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND22]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND23]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND24]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND25]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND26]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND27]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND28]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND29]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND30]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND31]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]
                    + ", PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]
                    + ", PRD." + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]
                    + " FROM "
                    + " " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " EMPSCD "
                    + " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstPeriod.TBL_HR_PERIOD + " PRD "
                    + " WHERE "
                    + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]
                    + " AND EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
                  //  + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;

            String whereClause = "";
            if ((srcempschedule.getEmployee() != null) && (srcempschedule.getEmployee().length() > 0)) {
                Vector vectName = logicParser(srcempschedule.getEmployee());
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }
//update by satrya 2014-11-16
//            if (srcempschedule.getEmpNumber() != null && srcempschedule.getEmpNumber().trim().length() > 0) {
//                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                        + " LIKE  \"%" + srcempschedule.getEmpNumber() + "%\" AND ";
//            }

            if ((srcempschedule.getEmpNumber() != null) && (srcempschedule.getEmpNumber().length() > 0)) {
                Vector vectName = logicParser(srcempschedule.getEmpNumber());
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            
            
            if (srcempschedule.getPeriod().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " = " + srcempschedule.getPeriod() + " AND ";
            }
            if (srcempschedule.getDivision().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + srcempschedule.getDivision() + " AND ";
            }

            if (srcempschedule.getDepartment().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcempschedule.getDepartment() + " AND ";
            }

            if (srcempschedule.getPosition().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcempschedule.getPosition() + " AND ";
            }

            if (srcempschedule.getSection().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcempschedule.getSection() + " AND ";
            }
            //priska 20151118
            if (srcempschedule.getResigned() != -1) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + srcempschedule.getResigned() + " AND ";
            }


            if (whereClause != null && whereClause.length() > 0) {
                whereClause += " 1 = 1 ";
                sql = sql + " AND " + whereClause;
            }


            Vector vLevel = new Vector();
            String order = PstLevel.fieldNames[PstLevel.FLD_LEVEL] + " ASC ";
            try {
                vLevel = PstLevel.list(0, 0, "", order);
            } catch (Exception E) {
                System.out.println("Exception " + E.toString());
            }

            String whereLev = "";

            
            
            for (int idLev = 0; idLev < vLevel.size(); idLev++) {

                Level level = new Level();
                level = (Level) vLevel.get(idLev);

                if (levelId[idLev].equals("1")) {

                    if (whereLev.length() <= 0) {
                        whereLev = whereLev + " EMP." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + " = " + level.getOID();
                    } else {
                        whereLev = whereLev + " OR EMP." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + " = " + level.getOID();

                    }

                }

            }

            if (whereLev.length() > 0) {
                sql = sql + " AND (" + whereLev + ") ";
            }
            //update by satrya 2013-12-06
            if (srcempschedule.getSortBy() == 0) {
                sql = sql + " ORDER BY EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + " DESC"
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID];
            } else {
                //update by satrya 2013-12-02
                int sortby = srcempschedule.getSortBy();
                switch (sortby) {
                    case FrmSrcEmpSchedule.SORT_BY_EMP_NUMBER:
                        sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                        break;
                    case FrmSrcEmpSchedule.SORT_BY_NAME:
                        sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                        break;
                    default:
                        sql = sql + "";
                }
            }


            if (start != 0 || recordToGet != 0) {
                sql = sql + " LIMIT " + start + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                EmpSchedule empschedule = new EmpSchedule();
                Employee employee = new Employee();
                Period period = new Period();


                empschedule.setOID(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
                empschedule.setPeriodId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]));
                empschedule.setEmployeeId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]));
                empschedule.setD1(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1]));
                empschedule.setD2(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2]));
                empschedule.setD3(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3]));
                empschedule.setD4(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4]));
                empschedule.setD5(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5]));
                empschedule.setD6(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6]));
                empschedule.setD7(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7]));
                empschedule.setD8(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8]));
                empschedule.setD9(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9]));
                empschedule.setD10(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10]));
                empschedule.setD11(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11]));
                empschedule.setD12(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12]));
                empschedule.setD13(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13]));
                empschedule.setD14(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14]));
                empschedule.setD15(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15]));
                empschedule.setD16(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16]));
                empschedule.setD17(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17]));
                empschedule.setD18(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18]));
                empschedule.setD19(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19]));
                empschedule.setD20(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20]));
                empschedule.setD21(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21]));
                empschedule.setD22(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22]));
                empschedule.setD23(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23]));
                empschedule.setD24(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24]));
                empschedule.setD25(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25]));
                empschedule.setD26(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26]));
                empschedule.setD27(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27]));
                empschedule.setD28(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28]));
                empschedule.setD29(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29]));
                empschedule.setD30(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30]));
                empschedule.setD31(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31]));

                empschedule.setD2nd1(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1]));
                empschedule.setD2nd2(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND2]));
                empschedule.setD2nd3(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND3]));
                empschedule.setD2nd4(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND4]));
                empschedule.setD2nd5(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND5]));
                empschedule.setD2nd6(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND6]));
                empschedule.setD2nd7(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND7]));
                empschedule.setD2nd8(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND8]));
                empschedule.setD2nd9(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND9]));
                empschedule.setD2nd10(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND10]));
                empschedule.setD2nd11(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND11]));
                empschedule.setD2nd12(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND12]));
                empschedule.setD2nd13(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND13]));
                empschedule.setD2nd14(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND14]));
                empschedule.setD2nd15(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND15]));
                empschedule.setD2nd16(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND16]));
                empschedule.setD2nd17(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND17]));
                empschedule.setD2nd18(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND18]));
                empschedule.setD2nd19(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND19]));
                empschedule.setD2nd20(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND20]));
                empschedule.setD2nd21(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND21]));
                empschedule.setD2nd22(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND22]));
                empschedule.setD2nd23(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND23]));
                empschedule.setD2nd24(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND24]));
                empschedule.setD2nd25(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND25]));
                empschedule.setD2nd26(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND26]));
                empschedule.setD2nd27(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND27]));
                empschedule.setD2nd28(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND28]));
                empschedule.setD2nd29(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND29]));
                empschedule.setD2nd30(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND30]));
                empschedule.setD2nd31(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND31]));

                vect.add(empschedule);

                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                vect.add(employee);

                period.setOID(rs.getLong(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]));
                period.setPeriod(rs.getString(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]));
                period.setStartDate(rs.getDate(PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]));
                vect.add(period);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on searchEmpSchedule : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    
    
    /**
     * @Author Roy A
     * @param srcempschedule
     * @return
     */
    public static int getCountSearch(SrcEmpSchedule srcempschedule, String levelId[]) {

        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        if (srcempschedule == null) {
            return 0;
        }

        try {
            String sql = " SELECT COUNT(EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + ") "
                    + " FROM "
                    + " " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " EMPSCD "
                    + " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstPeriod.TBL_HR_PERIOD + " PRD "
                    + " WHERE "
                    + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]
                    + " AND EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " =  "+srcempschedule.getResigned();

            String whereClause = "";
            if ((srcempschedule.getEmployee() != null) && (srcempschedule.getEmployee().length() > 0)) {
                Vector vectName = logicParser(srcempschedule.getEmployee());
                if (vectName != null && vectName.size() > 0) {
                    //whereClause = whereClause + " AND (";
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }
//update by satrya 2013-03-01
            if ((srcempschedule.getEmpNumber() != null) && (srcempschedule.getEmpNumber().length() > 0)) {
                Vector vectNumber = logicParser(srcempschedule.getEmpNumber());
                if (vectNumber != null && vectNumber.size() > 0) {
                    //whereClause = whereClause + " AND (";
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNumber.size(); i++) {
                        String str = (String) vectNumber.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }
            //String s = "";
            //s = srcempschedule.getPeriod();
            if (srcempschedule.getPeriod().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " = " + srcempschedule.getPeriod() + " AND ";
            }

            //s = srcempschedule.getDepartment();
            if (srcempschedule.getDepartment().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcempschedule.getDepartment() + " AND ";
            }

            //s = srcempschedule.getPosition();
            //System.out.println("srcempschedule.getPosition() = " + srcempschedule.getPosition());
            if (srcempschedule.getPosition().compareToIgnoreCase("0") > 0) {
                //System.out.println("srcempschedule.getPosition() = " + srcempschedule.getPosition());
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcempschedule.getPosition() + " AND ";
            }

            if (srcempschedule.getSection().compareToIgnoreCase("0") > 0) {
                //System.out.println("srcempschedule.getSection() = " + srcempschedule.getSection());
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcempschedule.getSection() + " AND ";
            }


            if (whereClause != null && whereClause.length() > 0) {
                //whereClause = whereClause.substring(0,whereClause.length()-4);                
                whereClause += " 1 = 1 ";
                sql = sql + " AND " + whereClause;
            }

            Vector vLevel = new Vector();
            String order = PstLevel.fieldNames[PstLevel.FLD_LEVEL] + " ASC ";
            try {
                vLevel = PstLevel.list(0, 0, "", order);
            } catch (Exception E) {
                System.out.println("Exception " + E.toString());
            }

            String whereLev = "";

            for (int idLev = 0; idLev < vLevel.size(); idLev++) {

                Level level = new Level();
                level = (Level) vLevel.get(idLev);

                if (levelId[idLev].equals("1")) {

                    if (whereLev.length() <= 0) {
                        whereLev = whereLev + " EMP." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + " = " + level.getOID();
                    } else {
                        whereLev = whereLev + " OR EMP." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + " = " + level.getOID();

                    }

                }

            }

            if (whereLev.length() > 0) {
                sql = sql + " AND (" + whereLev + ") ";
            }

            //update by satrya 2013-12-02
            int sortby = srcempschedule.getSortBy();
            switch (sortby) {
                case FrmSrcEmpSchedule.SORT_BY_EMP_NUMBER:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    break;
                case FrmSrcEmpSchedule.SORT_BY_NAME:
                    sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
                default:
                    sql = sql + "";
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int num = 0;
            while (rs.next()) {
                num = rs.getInt(1);
            }
            return num;
        } catch (Exception e) {
            System.out.println("\t Exception on getCountSearch : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }
    
    public static Vector searchEmpScheduleToBeCheck(Vector departmentId) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        if (departmentId == null || departmentId.size() <= 0) {
            return null;
        }

        try {
            String sql = " SELECT EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND2]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND3]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND4]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND5]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND6]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND7]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND8]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND9]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND10]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND11]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND12]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND13]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND14]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND15]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND16]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND17]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND18]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND19]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND20]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND21]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND22]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND23]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND24]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND25]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND26]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND27]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND28]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND29]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND30]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND31]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]
                    + ", PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]
                    + ", PRD." + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]
                    + " FROM "
                    + " " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " EMPSCD "
                    + " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstPeriod.TBL_HR_PERIOD + " PRD "
                    + " WHERE "
                    + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]
                    + " AND EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN
                    + " AND EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_SCHEDULE_TYPE] + " = " + PstEmpSchedule.SCHEDULE_TO_BE_CHECK;

            if (departmentId != null && departmentId.size() > 0) {

                int maks = departmentId.size() - 1;
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " IN (";

                for (int i = 0; i < departmentId.size(); i++) {

                    Department department = (Department) departmentId.get(i);
                    sql = sql + department.getOID();

                    if (i != maks) {
                        sql = sql + ",";
                    }
                }
                sql = sql + ")";
            }


            sql = sql + " ORDER BY EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID];

            System.out.println("\t SQL Search Employee Schedule to Check: " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                EmpSchedule empschedule = new EmpSchedule();
                Employee employee = new Employee();
                Period period = new Period();

                empschedule.setOID(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
                empschedule.setPeriodId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]));
                empschedule.setEmployeeId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]));
                empschedule.setD1(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1]));
                empschedule.setD2(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2]));
                empschedule.setD3(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3]));
                empschedule.setD4(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4]));
                empschedule.setD5(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5]));
                empschedule.setD6(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6]));
                empschedule.setD7(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7]));
                empschedule.setD8(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8]));
                empschedule.setD9(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9]));
                empschedule.setD10(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10]));
                empschedule.setD11(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11]));
                empschedule.setD12(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12]));
                empschedule.setD13(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13]));
                empschedule.setD14(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14]));
                empschedule.setD15(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15]));
                empschedule.setD16(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16]));
                empschedule.setD17(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17]));
                empschedule.setD18(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18]));
                empschedule.setD19(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19]));
                empschedule.setD20(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20]));
                empschedule.setD21(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21]));
                empschedule.setD22(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22]));
                empschedule.setD23(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23]));
                empschedule.setD24(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24]));
                empschedule.setD25(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25]));
                empschedule.setD26(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26]));
                empschedule.setD27(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27]));
                empschedule.setD28(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28]));
                empschedule.setD29(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29]));
                empschedule.setD30(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30]));
                empschedule.setD31(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31]));

                empschedule.setD2nd1(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1]));
                empschedule.setD2nd2(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND2]));
                empschedule.setD2nd3(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND3]));
                empschedule.setD2nd4(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND4]));
                empschedule.setD2nd5(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND5]));
                empschedule.setD2nd6(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND6]));
                empschedule.setD2nd7(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND7]));
                empschedule.setD2nd8(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND8]));
                empschedule.setD2nd9(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND9]));
                empschedule.setD2nd10(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND10]));
                empschedule.setD2nd11(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND11]));
                empschedule.setD2nd12(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND12]));
                empschedule.setD2nd13(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND13]));
                empschedule.setD2nd14(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND14]));
                empschedule.setD2nd15(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND15]));
                empschedule.setD2nd16(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND16]));
                empschedule.setD2nd17(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND17]));
                empschedule.setD2nd18(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND18]));
                empschedule.setD2nd19(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND19]));
                empschedule.setD2nd20(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND20]));
                empschedule.setD2nd21(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND21]));
                empschedule.setD2nd22(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND22]));
                empschedule.setD2nd23(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND23]));
                empschedule.setD2nd24(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND24]));
                empschedule.setD2nd25(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND25]));
                empschedule.setD2nd26(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND26]));
                empschedule.setD2nd27(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND27]));
                empschedule.setD2nd28(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND28]));
                empschedule.setD2nd29(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND29]));
                empschedule.setD2nd30(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND30]));
                empschedule.setD2nd31(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND31]));

                vect.add(empschedule);

                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                vect.add(employee);

                period.setOID(rs.getLong(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]));
                period.setPeriod(rs.getString(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]));
                period.setStartDate(rs.getDate(PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]));
                vect.add(period);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on searchEmpSchedule : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /**
     * @Desc untuk menghitung size dari employee schedule to be check
     * @param departmentId
     * @return
     */
    public static int sizeSearchEmpScheduleToBeCheck(Vector departmentId) {

        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        if (departmentId == null || departmentId.size() <= 0) {
            return 0;
        }

        try {
            String sql = " SELECT COUNT(EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + " ) FROM "
                    + " " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " EMPSCD "
                    + " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstPeriod.TBL_HR_PERIOD + " PRD "
                    + " WHERE "
                    + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]
                    + " AND EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN
                    + " AND EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_SCHEDULE_TYPE] + " = " + PstEmpSchedule.SCHEDULE_TO_BE_CHECK;

            if (departmentId != null && departmentId.size() > 0) {

                int maks = departmentId.size() - 1;
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " IN (";

                for (int i = 0; i < departmentId.size(); i++) {

                    Department department = (Department) departmentId.get(i);
                    sql = sql + department.getOID();

                    if (i != maks) {
                        sql = sql + ",";
                    }
                }
                sql = sql + ")";
            }


            sql = sql + " ORDER BY EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID];

            System.out.println("\t SQL Count Search Employee Schedule to Check: " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                int size = rs.getInt(1);
                return size;
            }

        } catch (Exception e) {
            System.out.println("\t Exception on searchEmpSchedule : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;

    }

    public static int getCountSearch(SrcEmpSchedule srcempschedule) {
        //int count = 1;
        //return count;
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        if (srcempschedule == null) {
            return 0;
        }

        try {
            String sql = " SELECT COUNT(EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + ") "
                    + " FROM "
                    + " " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " EMPSCD "
                    + " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstPeriod.TBL_HR_PERIOD + " PRD "
                    + " WHERE "
                    + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]
                    + " AND EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + //added on Wed, 23-01-2003
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = 0 ";

            String whereClause = "";
            if ((srcempschedule.getEmployee() != null) && (srcempschedule.getEmployee().length() > 0)) {
                Vector vectName = logicParser(srcempschedule.getEmployee());
                if (vectName != null && vectName.size() > 0) {
                    //whereClause = whereClause + " AND (";
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            //String s = "";
            //s = srcempschedule.getPeriod();
            if (srcempschedule.getPeriod().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " = " + srcempschedule.getPeriod() + " AND ";
            }

            //s = srcempschedule.getDepartment();
            if (srcempschedule.getDepartment().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcempschedule.getDepartment() + " AND ";
            }

            //s = srcempschedule.getPosition();
            //System.out.println("srcempschedule.getPosition() = " + srcempschedule.getPosition());
            if (srcempschedule.getPosition().compareToIgnoreCase("0") > 0) {
                //System.out.println("srcempschedule.getPosition() = " + srcempschedule.getPosition());
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcempschedule.getPosition() + " AND ";
            }

            if (srcempschedule.getSection().compareToIgnoreCase("0") > 0) {
                //System.out.println("srcempschedule.getSection() = " + srcempschedule.getSection());
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcempschedule.getSection() + " AND ";
            }

            /*
             if(srcEmployee.getDepartment() != 0)
             whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
             " = "+srcEmployee.getDepartment() + " AND ";
             if(srcEmployee.getPosition() != 0)
             whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
             " = "+ srcEmployee.getPosition() + " AND ";
             if(srcEmployee.getSection() != 0)
             whereClause = whereClause +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
             " = "+ srcEmployee.getSection() + " AND ";
             */
            if (whereClause != null && whereClause.length() > 0) {
                //whereClause = whereClause.substring(0,whereClause.length()-4);                
                whereClause += " 1 = 1 ";
                sql = sql + " AND " + whereClause;
            }

//            System.out.println("\t SQL getCountSearch : " + sql);  

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int num = 0;
            while (rs.next()) {
                num = rs.getInt(1);
            }
            return num;
        } catch (Exception e) {
            System.out.println("\t Exception on getCountSearch : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    /* get manning based on year, month, date, department */
    public static int getManning(int year, int month, int date, long departmentId) {
        int count = 0;
        DBResultSet dbrs = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(emp.employee_id) FROM ");
        sql.append(" hr_employee emp, hr_emp_schedule es, hr_department dep, hr_period per, ");
        sql.append(" hr_schedule_category c1, hr_schedule_symbol s1 ");
        sql.append(" WHERE ");
        sql.append(" dep.department_id = ");
        sql.append(departmentId);
        sql.append(" AND ");
        sql.append(" month(per.end_date) = ");
        sql.append(month);
        sql.append(" AND ");
        sql.append(" year(per.end_date) = ");
        sql.append(year);
        sql.append(" AND ");
        sql.append(" emp.resigned = 0 AND ");
        sql.append(" emp.department_id = dep.department_id AND ");
        sql.append(" emp.employee_id = es.employee_id AND ");
        sql.append(" per.period_id = es.period_id AND ");
        sql.append(" es.d");
        sql.append(date);
        sql.append(" = s1.schedule_id AND ");
        sql.append(" c1.schedule_category_id = s1.schedule_category_id AND ");
        sql.append(" c1.category = 'Schedule Symbol' ");

        System.out.println("SQL get Manning : " + sql);

        try {
            dbrs = DBHandler.execQueryResult(sql.toString());
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            System.out.println("\t Exception on getManning : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    public static int getTotalManning(long departmentId) {
        int count = 0;
        DBResultSet dbrs = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(emp.employee_id) FROM ");
        sql.append(" hr_employee emp, hr_department dep ");
        sql.append(" WHERE ");
        sql.append(" dep.department_id = ");
        sql.append(departmentId);
        sql.append(" AND ");
        sql.append(" emp.resigned = 0 AND ");
        sql.append(" emp.department_id = dep.department_id ");

        try {
            dbrs = DBHandler.execQueryResult(sql.toString());
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            System.out.println("\t Exception on getTotalManning : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    /**
     * used to get schedule symbol with its category
     *
     * @return
     * @created by Edhy
     */
    public static Vector getMasterSchedule() {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_DESCRIPTION]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT "
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " ORDER BY SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL];

            //System.out.println("SQL MASTER SCHEDULE : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                ScheduleCategory scheduleCategory = new ScheduleCategory();

                scheduleSymbol.setOID(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]));
                scheduleSymbol.setSymbol(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
                scheduleSymbol.setSchedule(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]));
                scheduleSymbol.setTimeIn(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]));
                scheduleSymbol.setTimeOut(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]));
                vect.add(scheduleSymbol);

                scheduleCategory.setOID(rs.getLong(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]));
                scheduleCategory.setCategoryType(rs.getInt(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]));
                scheduleCategory.setCategory(rs.getString(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY]));
                scheduleCategory.setDescription(rs.getString(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_DESCRIPTION]));
                vect.add(scheduleCategory);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on getMasterSchedule : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /**
     * used to get schedule symbol with its category base on schedule category
     *
     * @return
     * @created by Edhy
     */
    public static Vector getMasterSchedule(int intScheduleCategory) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_DESCRIPTION]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT "
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID];

            if (intScheduleCategory != PstScheduleCategory.CATEGORY_ALL) {
                sql = sql + " WHERE CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                        + " = " + intScheduleCategory;
            }

            sql = sql + " ORDER BY SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL];

            //System.out.println("SQL MASTER SCHEDULE CATEGORY : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                ScheduleCategory scheduleCategory = new ScheduleCategory();

                scheduleSymbol.setOID(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]));
                scheduleSymbol.setSymbol(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
                scheduleSymbol.setSchedule(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]));
                scheduleSymbol.setTimeIn(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]));
                scheduleSymbol.setTimeOut(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]));
                vect.add(scheduleSymbol);

                scheduleCategory.setOID(rs.getLong(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]));
                scheduleCategory.setCategoryType(rs.getInt(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]));
                scheduleCategory.setCategory(rs.getString(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY]));
                scheduleCategory.setDescription(rs.getString(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_DESCRIPTION]));
                vect.add(scheduleCategory);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on getMasterSchedule category : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /**
     * used to get schedule symbol with its category base on schedule category
     *
     * @return
     * @created by Edhy
     */
    public static Vector getMasterSchedule(Vector vectScheduleCategory) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        String strScheduleCategory = "";
        if (vectScheduleCategory != null && vectScheduleCategory.size() > 0) {
            int maxVectScheduleCategory = vectScheduleCategory.size();
            for (int i = 0; i < maxVectScheduleCategory; i++) {
                strScheduleCategory = strScheduleCategory + String.valueOf(vectScheduleCategory.get(i)) + ",";
            }

            if (strScheduleCategory != null && strScheduleCategory.length() > 0) {
                strScheduleCategory = strScheduleCategory.substring(0, strScheduleCategory.length() - 1);
            }
        }

        try {
            String sql = "SELECT SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]
                    + ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY]
                    + ", CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_DESCRIPTION]
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM"
                    + " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT "
                    + " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                    + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]
                    + " WHERE CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                    + " IN (" + strScheduleCategory + ")"
                    + " ORDER BY SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL];

            //System.out.println("SQL MASTER SCHEDULE CATEGORY : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                Vector vect = new Vector(1, 1);

                ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                ScheduleCategory scheduleCategory = new ScheduleCategory();

                scheduleSymbol.setOID(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]));
                scheduleSymbol.setSymbol(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
                scheduleSymbol.setSchedule(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]));
                scheduleSymbol.setTimeIn(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]));
                scheduleSymbol.setTimeOut(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]));
                vect.add(scheduleSymbol);

                scheduleCategory.setOID(rs.getLong(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]));
                scheduleCategory.setCategoryType(rs.getInt(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]));
                scheduleCategory.setCategory(rs.getString(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY]));
                scheduleCategory.setDescription(rs.getString(PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_DESCRIPTION]));
                vect.add(scheduleCategory);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on get Master Schedule category : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /**
     * get list employee absence based on selected period
     *
     * @param srcempschedule
     * @param
     * @param recordToGet
     * @return
     */
    public static Vector listEmployeeAbsence(SrcEmpSchedule srcempschedule, int limitStart, int recordToGet) {

        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        if (srcempschedule == null) {
            return new Vector(1, 1);
        }

        try {
            String sql = "SELECT EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]
                    + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS EMPSCD "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " ON EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPeriod.TBL_HR_PERIOD + " AS PRD "
                    + " ON EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID];

            String whereClause = "";
            String statusClause = " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + "!=0";

            /*  khusus intimas 
             jika bukan intimas buka komentra dibawah ini ( statusClause pake yang bawah )
             Yunny 
             */

            /*String statusClause = " (EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]+ " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +
             " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]+" = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE +")";*/

            if ((srcempschedule.getEmployee() != null) && (srcempschedule.getEmployee().length() > 0)) {
                Vector vectName = logicParser(srcempschedule.getEmployee());
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if (srcempschedule.getPeriod().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " = " + srcempschedule.getPeriod() + " AND ";
            }

            if (srcempschedule.getDepartment().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcempschedule.getDepartment() + " AND ";
            }

            if (srcempschedule.getPosition().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcempschedule.getPosition() + " AND ";
            }

            if (srcempschedule.getSection().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcempschedule.getSection() + " AND ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause + statusClause;
            } else {
                sql = sql + " WHERE " + statusClause;
            }

            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("\t SQL listEmployeeAbsence : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);
                EmpSchedule empschedule = new EmpSchedule();
                Employee employee = new Employee();

                empschedule.setOID(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
                empschedule.setStatus1(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]));
                empschedule.setStatus2(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]));
                empschedule.setStatus3(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]));
                empschedule.setStatus4(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]));
                empschedule.setStatus5(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]));
                empschedule.setStatus6(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]));
                empschedule.setStatus7(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]));
                empschedule.setStatus8(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]));
                empschedule.setStatus9(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]));
                empschedule.setStatus10(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]));
                empschedule.setStatus11(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]));
                empschedule.setStatus12(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]));
                empschedule.setStatus13(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]));
                empschedule.setStatus14(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]));
                empschedule.setStatus15(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]));
                empschedule.setStatus16(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]));
                empschedule.setStatus17(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]));
                empschedule.setStatus18(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]));
                empschedule.setStatus19(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]));
                empschedule.setStatus20(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]));
                empschedule.setStatus21(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]));
                empschedule.setStatus22(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]));
                empschedule.setStatus23(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]));
                empschedule.setStatus24(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]));
                empschedule.setStatus25(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]));
                empschedule.setStatus26(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]));
                empschedule.setStatus27(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]));
                empschedule.setStatus28(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]));
                empschedule.setStatus29(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]));
                empschedule.setStatus30(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]));
                empschedule.setStatus31(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]));
                empschedule.setReason1(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]));
                empschedule.setReason2(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]));
                empschedule.setReason3(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]));
                empschedule.setReason4(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]));
                empschedule.setReason5(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]));
                empschedule.setReason6(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]));
                empschedule.setReason7(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]));
                empschedule.setReason8(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]));
                empschedule.setReason9(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]));
                empschedule.setReason10(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]));
                empschedule.setReason11(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]));
                empschedule.setReason12(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]));
                empschedule.setReason13(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]));
                empschedule.setReason14(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]));
                empschedule.setReason15(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]));
                empschedule.setReason16(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]));
                empschedule.setReason17(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]));
                empschedule.setReason18(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]));
                empschedule.setReason19(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]));
                empschedule.setReason20(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]));
                empschedule.setReason21(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]));
                empschedule.setReason22(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]));
                empschedule.setReason23(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]));
                empschedule.setReason24(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]));
                empschedule.setReason25(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]));
                empschedule.setReason26(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]));
                empschedule.setReason27(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]));
                empschedule.setReason28(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]));
                empschedule.setReason29(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]));
                empschedule.setReason30(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]));
                empschedule.setReason31(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]));
                vect.add(empschedule);

                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                vect.add(employee);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on listEmployeeAbsence : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }

    /**
     * get list employee absence based on selected period
     *
     * @param srcempschedule
     * @param
     * @param
     * @return
     */
    public static int countEmployeeAbsence(SrcEmpSchedule srcempschedule) {
        int result = 0;
        DBResultSet dbrs = null;

        try {
            String sql = "SELECT COUNT(EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + ")"
                    + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS EMPSCD "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " ON EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPeriod.TBL_HR_PERIOD + " AS PRD "
                    + " ON EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID];

            String whereClause = "";
            String statusClause = " (EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE + ")";

            if ((srcempschedule.getEmployee() != null) && (srcempschedule.getEmployee().length() > 0)) {
                Vector vectName = logicParser(srcempschedule.getEmployee());
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if (srcempschedule.getPeriod().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " = " + srcempschedule.getPeriod() + " AND ";
            }

            if (srcempschedule.getDepartment().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcempschedule.getDepartment() + " AND ";
            }

            if (srcempschedule.getPosition().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcempschedule.getPosition() + " AND ";
            }

            if (srcempschedule.getSection().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcempschedule.getSection() + " AND ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause + statusClause;
            } else {
                sql = sql + " WHERE " + statusClause;
            }

//            System.out.println("\t SQL countEmployeeAbsence : " + sql);            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = rs.getInt(1);
            }
            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on countEmployeeAbsence : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    /**
     * get list presence/attendance record per employee monthly
     *
     * @param periodId
     * @param employeeId
     * @param departmentId
     * @return
     * @created by Edhy
     */
    public static Vector listEmpPresenceMonthly(long departmentId, long periodId, long employeeId) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        // get maximum date on selected periodId        
        Period schedulePeriod = new Period();
        int maxDayOnSelectedPeriod = 0;
        try {
            PstPeriod pstPeriod = new PstPeriod();
            schedulePeriod = pstPeriod.fetchExc(periodId);
            GregorianCalendar calenderWeek = new GregorianCalendar(schedulePeriod.getEndDate().getYear() + 1900, schedulePeriod.getEndDate().getMonth(), 1);
            maxDayOnSelectedPeriod = calenderWeek.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            System.out.println("periodId  " + periodId);
        } catch (Exception e) {
            System.out.println(">>> Exc fetch Period object on SessEmpSchedule : " + e.toString());
        }

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            // schedule first
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i];
            }

            // schedule second
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i];
            }

            // in first
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i];
            }

            // in second
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i];
            }

            // out first
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i];
            }

            // out second
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i];
            }

            sql += " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH"
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = " + periodId
                    + " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " = " + departmentId
                    + " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = " + employeeId
                    + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            System.out.println("\tlistEmpPresenceMonthlyN : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                    Vector vectTemp = new Vector(1, 1);

                    vectTemp.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));

                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]) != null) {
                        vectTemp.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i])));
                    } else {
                        vectTemp.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]));
                    }

                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]) != null) {
                        vectTemp.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i])));
                    } else {
                        vectTemp.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]));
                    }

                    vectTemp.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));

                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]) != null) {
                        vectTemp.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                    } else {
                        vectTemp.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]));
                    }

                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]) != null) {
                        vectTemp.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                    } else {
                        vectTemp.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]));
                    }

                    result.add(vectTemp);
                }
            }
        } catch (Exception e) {
            System.out.println("listEmpPresenceMonthly exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * get list presence/attendance record per employee monthly
     *
     * @param periodId
     * @param employeeId
     * @param departmentId
     * @return
     * @created by Yunny
     */
    public static Vector listEmpPresenceMonthlyDinamis(long departmentId, long periodId, long employeeId) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;

        // get maximum date on selected periodId        
        Period schedulePeriod = new Period();
        int maxDayOnSelectedPeriod = 0;
        try {
            PstPeriod pstPeriod = new PstPeriod();
            schedulePeriod = pstPeriod.fetchExc(periodId);
            GregorianCalendar calenderWeek = new GregorianCalendar(schedulePeriod.getStartDate().getYear() + 1900, schedulePeriod.getStartDate().getMonth(), 1);
            maxDayOnSelectedPeriod = calenderWeek.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            System.out.println("maxDayOnSelectedPeriod  " + maxDayOnSelectedPeriod);
        } catch (Exception e) {
            System.out.println(">>> Exc fetch Period object on SessEmpSchedule : " + e.toString());
        }

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            // schedule first
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i];
            }

            // schedule second
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i];
            }

            // in first
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i];
            }

            // in second
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i];
            }

            // out first
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i];
            }

            // out second
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i];
            }

             // in first
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + i];
            }
            
            // in first
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + i];
            }
            // in first
            for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                sql += ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i];
            }
            sql += " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH"
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = " + periodId
                    + " AND DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " = " + departmentId
                    + " AND SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = " + employeeId
                    + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            System.out.println("\tlistEmpPresenceMonthlyD : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                for (int i = 0; i < maxDayOnSelectedPeriod; i++) {
                    Vector vectTemp = new Vector(1, 1);

                    vectTemp.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + i])));

                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]) != null) {
                        vectTemp.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i])));
                    } else {
                        vectTemp.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + i]));
                    }

                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]) != null) {
                        vectTemp.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i])));
                    } else {
                        vectTemp.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + i]));
                    }

                    vectTemp.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));

                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]) != null) {
                        vectTemp.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                    } else {
                        vectTemp.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]));
                    }

                    if (rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]) != null) {
                        vectTemp.add(DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i])));
                    } else {
                        vectTemp.add(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + i]));
                    }
                    
                     vectTemp.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + i])));
                     vectTemp.add(String.valueOf(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + i])));
                     vectTemp.add(String.valueOf(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + i])));
                    result.add(vectTemp);
                }
            }
        } catch (Exception e) {
            System.out.println("listEmpPresenceMonthly exc : " + e.toString());
            return result;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * get list presence/attendance record per department daily
     *
     * @param selectedDate
     * @param departmentId
     * @return
     * @created by Edhy
     */
    public static Vector listEmpPresenceDaily(long departmentId, Date selectedDate, long sectionId) {
        //update by satrya 2012-09-28
        return listEmpPresenceDaily(departmentId, selectedDate, sectionId, "", "", "", 0, 0, null, null, 0, null, 0, 0, 0);
    }
//UPDATE BY SATRYA 2012-08-18

    /**
     * Keterangan : untuk report Daily
     *
     * @param departmentId
     * @param fromDate
     * @param toDate
     * @param sectionId
     * @param empNumId
     * @param fullName
     * @param status1st
     * @param limitStart
     * @param recordToGet
     * @return
     */
    public static Vector listEmpPresenceDaily(long departmentId, Date fromDate, Date toDate, long sectionId, String empNumId, String fullName, String status1st, int limitStart, int recordToGet, Vector stStatus, int reasonSts, String stsEmpCategorySel, int statusResign, long oidCompany) {
        return listEmpPresenceDaily(departmentId, fromDate,toDate, sectionId, empNumId, fullName, status1st,limitStart,  recordToGet, stStatus, reasonSts,  stsEmpCategorySel,statusResign,oidCompany, 0);
    }
    public static Vector listEmpPresenceDaily(long departmentId, Date fromDate, Date toDate, long sectionId, String empNumId, String fullName, String status1st, int limitStart, int recordToGet, Vector stStatus, int reasonSts, String stsEmpCategorySel, int statusResign, long oidCompany, long oidDivision) {
        //public static Vector listEmpPresenceDaily(long departmentId, Date fromDate, Date toDate, long sectionId, String empNumId, String fullName,String status1st, int limitStart, int recordToGet,Vector stStatus) {
        //public static Vector listEmpPresenceDaily(long departmentId, Date fromDate, Date toDate, long sectionId, String empNumId, String fullName,String status1st, int limitStart, int recordToGet) {
        Vector result = new Vector();

        // loo[p dateFrom sampai toDate
        //panggil

        if (fromDate != null && toDate != null) {

            if (fromDate.getTime() > toDate.getTime()) {
                Date tempFromDate = fromDate;
                Date tempToDate = toDate;
                fromDate = tempToDate;
                toDate = tempFromDate;
            }
            long diffStartToFinish = toDate.getTime() - fromDate.getTime();
            int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));
            int countX = 0;
            int limitStartX = limitStart, recordToGetX = recordToGet;
            int recordGot = 0;

            PayPeriod payPeriod = new PayPeriod();
            try{
                payPeriod = PstPayPeriod.getPayPeriodBySelectedDate(fromDate);
            }catch(Exception e){}
            
            if (payPeriod == null ){
                payPeriod = new PayPeriod();
            }
            Date startDt = null;
            Date endDt = null;
            String getEmployeePresence = "";
            for (int i = 0; i <= itDate; i++) {
                /**
                 * kasusnya : "2013-03-01 12:00:00" AND "2013-03-04 14:00:00";
                 * maka di pecah menjadi: "2013-03-01 12:00:00" AND "2013-03-01
                 * 23:59:00"; "2013-03-02 00:00:00" AND "2013-03-02 23:59:00";
                 * "2013-03-03 00:00:00" AND "2013-03-03 23:59:00"; "2013-03-04
                 * 00:00:00" AND "2013-03-04 14:00:00";
                 */
                if (stStatus != null && stStatus.size() > 0) {
                    if (itDate == 0) {
                        startDt = (Date) fromDate.clone();
                        endDt = (Date) toDate.clone();

                    } else if (i == 0 && itDate > 0) {

                        startDt = (Date) fromDate.clone();

                        endDt = (Date) fromDate.clone();
                        endDt.setHours(23);
                        endDt.setMinutes(59);
                        endDt.setSeconds(59);

                    } else if (itDate == i && itDate > 0) {
                        startDt = (Date) toDate.clone();
                        startDt.setHours(0);
                        startDt.setMinutes(0);
                        startDt.setSeconds(0);

                        endDt = (Date) toDate.clone();

                    } else {
                        startDt = (Date) new Date(fromDate.getTime() + i * 1000L * 60 * 60 * 24).clone();

                        startDt.setHours(0);
                        startDt.setMinutes(0);
                        startDt.setSeconds(0);

                        endDt = (Date) new Date(fromDate.getTime() + i * 1000L * 60 * 60 * 24).clone();
                        endDt.setHours(23);
                        endDt.setMinutes(59);
                        endDt.setSeconds(59);
                    }
                }
                if (startDt != null && endDt != null) {
                    getEmployeePresence = PstPresence.getEmployee(0, 0, "", departmentId, fullName.trim(),
                            startDt, endDt, sectionId, empNumId.trim(), stStatus, oidCompany, oidDivision);

                    if (getEmployeePresence.length() <= 0) {
                        getEmployeePresence = "" + 0;
                    }

                }

                Date selectedDate = new Date(fromDate.getTime() + i * 1000L * 60 * 60 * 24);//Date(getFromDate().getYear(), getFromDate().getMonth(), (getFromDate().getDate() + i));
                int countbyDate = countEmpPresenceDaily(departmentId, selectedDate, sectionId, empNumId, fullName, status1st, getEmployeePresence, stStatus, reasonSts, stsEmpCategorySel, statusResign, oidCompany, oidDivision,payPeriod.getStartDate(),payPeriod.getEndDate());
                //int countbyDate = countEmpPresenceDaily(departmentId, selectedDate, sectionId, empNumId, fullName, limitStartX, recordToGetX,status1st);
                if (limitStartX > countbyDate || limitStartX >= countbyDate) {
                    limitStartX = limitStartX - countbyDate;
                    continue;
                }

                Vector dPresence = listEmpPresenceDaily(departmentId, selectedDate, sectionId, empNumId, fullName, status1st, limitStartX, recordToGetX, getEmployeePresence, stStatus, reasonSts, stsEmpCategorySel, statusResign, oidCompany, oidDivision,payPeriod.getStartDate(),payPeriod.getEndDate());
                if (dPresence != null && dPresence.size() > 0) {
                    result.addAll(dPresence);
                    countX = dPresence.size();
                    recordGot = recordGot + countX;
                    if (recordGot >= recordToGet) {
                        return result; //// jika jumlah yg didapat sudah = jumlah yang harus di dapat berhenti dan return
                    }

                    if (countX < recordToGetX) {
                        limitStartX = 0;
                        recordToGetX = recordToGetX - countX;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Keterangan : untuk melakukan cheking ada data kasusnya : ada salah
     * karyawan yg tidak ada sectionnya tpi ada juga yg ada didalam 1 department
     * create by satrya 2013-12-06
     *
     * @param departmentId
     * @param fromDate
     * @param toDate
     * @param sectionId
     * @param empNumId
     * @param fullName
     * @param status1st
     * @param limitStart
     * @param recordToGet
     * @param stStatus
     * @param reasonSts
     * @param stsEmpCategorySel
     * @param statusResign
     * @param oidCompany
     * @param oidDivision
     * @return
     */
    public static boolean getCheckAdaDataPresence(long departmentId, Date fromDate, Date toDate, long sectionId, String empNumId, String fullName, String status1st, int limitStart, int recordToGet, Vector stStatus, int reasonSts, String stsEmpCategorySel, int statusResign, long oidCompany, long oidDivision) {
        boolean isAdaData = false;
        if (fromDate != null && toDate != null) {

            if (fromDate.getTime() > toDate.getTime()) {
                Date tempFromDate = fromDate;
                Date tempToDate = toDate;
                fromDate = tempToDate;
                toDate = tempFromDate;
            }
            long diffStartToFinish = toDate.getTime() - fromDate.getTime();
            int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));

            Date startDt = null;
            Date endDt = null;
            String getEmployeePresence = "";
            for (int i = 0; i <= itDate; i++) {
                /**
                 * kasusnya : "2013-03-01 12:00:00" AND "2013-03-04 14:00:00";
                 * maka di pecah menjadi: "2013-03-01 12:00:00" AND "2013-03-01
                 * 23:59:00"; "2013-03-02 00:00:00" AND "2013-03-02 23:59:00";
                 * "2013-03-03 00:00:00" AND "2013-03-03 23:59:00"; "2013-03-04
                 * 00:00:00" AND "2013-03-04 14:00:00";
                 */
                if (stStatus != null && stStatus.size() > 0) {
                    if (itDate == 0) {
                        startDt = (Date) fromDate.clone();
                        endDt = (Date) toDate.clone();

                    } else if (i == 0 && itDate > 0) {

                        startDt = (Date) fromDate.clone();

                        endDt = (Date) fromDate.clone();
                        endDt.setHours(23);
                        endDt.setMinutes(59);
                        endDt.setSeconds(59);

                    } else if (itDate == i && itDate > 0) {
                        startDt = (Date) toDate.clone();
                        startDt.setHours(0);
                        startDt.setMinutes(0);
                        startDt.setSeconds(0);

                        endDt = (Date) toDate.clone();

                    } else {
                        startDt = (Date) new Date(fromDate.getTime() + i * 1000L * 60 * 60 * 24).clone();

                        startDt.setHours(0);
                        startDt.setMinutes(0);
                        startDt.setSeconds(0);

                        endDt = (Date) new Date(fromDate.getTime() + i * 1000L * 60 * 60 * 24).clone();
                        endDt.setHours(23);
                        endDt.setMinutes(59);
                        endDt.setSeconds(59);
                    }
                }
                if (startDt != null && endDt != null) {
                    getEmployeePresence = PstPresence.getEmployee(0, 0, "", departmentId, fullName.trim(),
                            startDt, endDt, sectionId, empNumId.trim(), stStatus, oidCompany, oidDivision);

                    if (getEmployeePresence.length() <= 0) {
                        getEmployeePresence = "" + 0;
                    }

                }

                Date selectedDate = new Date(fromDate.getTime() + i * 1000L * 60 * 60 * 24);//Date(getFromDate().getYear(), getFromDate().getMonth(), (getFromDate().getDate() + i));
                int countbyDate = countEmpPresenceDaily(departmentId, selectedDate, sectionId, empNumId, fullName, status1st, getEmployeePresence, stStatus, reasonSts, stsEmpCategorySel, statusResign, oidCompany, oidDivision);
                //int countbyDate = countEmpPresenceDaily(departmentId, selectedDate, sectionId, empNumId, fullName, limitStartX, recordToGetX,status1st);
                if (countbyDate > 0) {
                    isAdaData = true;
                    return isAdaData;
                }

            }
        }
        return isAdaData;
    }

    
    public static Vector listEmpPresenceDaily(long departmentId, Date selectedDate, long sectionId, String empNumId, String fullName, String status1st, int limitStart, int recordToGet, String getEmployeePresence, Vector stStatus, int reasonSts, String stsEmpCategorySel, int statusResign, long oidCompany, long oidDivision) {
        return listEmpPresenceDaily(departmentId, selectedDate, sectionId, empNumId, fullName,  status1st, limitStart, recordToGet, getEmployeePresence, stStatus, reasonSts, stsEmpCategorySel, statusResign, oidCompany, oidDivision, null, null) ;  
    }
    
    public static Vector listEmpPresenceDaily(long departmentId, Date selectedDate, long sectionId, String empNumId, String fullName, String status1st, int limitStart, int recordToGet, String getEmployeePresence, Vector stStatus, int reasonSts, String stsEmpCategorySel, int statusResign, long oidCompany, long oidDivision,Date startPayPeriod,Date endPayPeriod) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        // get index field on selectedDate        
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);

        // get periodId from selectedDate        
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    //update by satrya 2012-08-20
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    // end
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]
                    //update by satrya 2012-08-20
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    //update by satrya 2012-10-09
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    //update by satrya 2012-10-31
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    //update by satrya 2014-02-10
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    //update by satrya 2012-12-23
                    //                    + ", DIV." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    //                    + ", DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    //                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH"
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    //update by satrya 2012-12-23
                    //                    + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " AS DIV"
                    //                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    //                    + " = DIV." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    //                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP"
                    //                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    //                    + " = DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    //                    + " INNER JOIN " + PstSection.TBL_HR_SECTION + " AS SEC"
                    //                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    //                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    //end

                    + " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = " + periodId;
            //update by satrya 2012-09-10
            //update by Priska 2015-08-16
            if (statusResign == PstEmployee.YES_RESIGN) {
                sql += " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.YES_RESIGN
                        +" OR " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]  + " >= \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd  00:00:00") + "\" ) ";
//                    + " = " +PstEmployee.YES_RESIGN
            } else if (statusResign == PstEmployee.NO_RESIGN) {
                if (startPayPeriod != null && endPayPeriod!=null){
                    sql += " AND ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN 
                    +" OR " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]  + " >= \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd  00:00:00") + "\"  "
                    +" OR " + "((EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]  + " BETWEEN \"" + Formater.formatDate(startPayPeriod, "yyyy-MM-dd  00:00:00") + "\" AND \""+ Formater.formatDate(endPayPeriod, "yyyy-MM-dd  00:00:00") +"\" ) AND  (  \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd  00:00:00") + "\" <=  \""+ Formater.formatDate(endPayPeriod, "yyyy-MM-dd  00:00:00") +"\"  ) )) ";        
                } else {
                    sql += " AND ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN 
                    +" OR " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]  + " >= \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd  00:00:00") + "\" ) ";        
                }
                     
            }
            //else if (statusResign == PstEmployee.NO_RESIGN) {
            //    sql += " AND ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN + " OR EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]+ ">= \""+Formater.formatDate(selectedDate, "yyyy-MM-dd")+"\" ) ";
            //}
            if (stsEmpCategorySel != null && stsEmpCategorySel.length() > 0) {
                stsEmpCategorySel = stsEmpCategorySel.substring(0, stsEmpCategorySel.length() - 1);
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + " IN  (" + stsEmpCategorySel + ")";
            }
            if (departmentId != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + departmentId;
            }
            //update by satrya 2013-12-03
            if (oidCompany != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                        + " = " + oidCompany;
            }
            //update by satrya 2013-12-03
            if (oidDivision != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + oidDivision;
            }
            if (sectionId != 0) {
                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + sectionId;
            }
            //update by satrya 2013-04-08
            if (reasonSts != 0) {
                sql = sql + " AND " + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1]
                        + " = " + reasonSts;
            }
            //update by satrya 2012-07-16
            if (empNumId != null && empNumId != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                        + " = " + "\"" + empNumId.trim() + "\"";//penambahan trima
                Vector vectNum = logicParser(empNumId);
                sql = sql + " AND ";
                if (vectNum != null && vectNum.size() > 0) {
                    sql = sql + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + ")";
                }

            }
            //update by satrya 2012-07-16
            if (fullName != null && fullName != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                        + " LIKE " + "\"%" + fullName.trim() + "%\"";//penambahan trim
                Vector vectFullName = logicParser(fullName);
                sql = sql + " AND ";
                if (vectFullName != null && vectFullName.size() > 0) {
                    sql = sql + " ( ";
                    for (int i = 0; i < vectFullName.size(); i++) {
                        String str = (String) vectFullName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }

            }
            //update by satrya 2012-9-28

            if (status1st != null && status1st.length() > 0) {
                status1st = status1st.substring(0, status1st.length() - 1);
                sql = sql + " AND " + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1]
                        + " IN (" + status1st + ")";
            }
            /*if(status2nd!=0){
             sql = sql + " AND " +" SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
             + " = " + status2nd; 
             }*/
            //update by satrya 2013-03-30
            if (getEmployeePresence != null && getEmployeePresence.length() > 0 && stStatus != null && stStatus.size() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN (" + getEmployeePresence + ")";
            }
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }


            //System.out.println("\tlistEmpPresenceDaily : " + sql);
            //java.sql.Date sqlCurrDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());
            //java.sql.Date sqlNextDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate() + 1);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {

                PresenceReportDaily presenceReportDaily = new PresenceReportDaily();

                String strEmpNum = rs.getString(1);
                String strEmpName = rs.getString(2);
                long firstSchedule = rs.getLong(3);
                long secondSchedule = rs.getLong(4);
                //update by satrya 2012-08-20
                int reason1nd = rs.getInt(11);
                int reason2nd = rs.getInt(12);
                String note1nd = rs.getString(13);
                String note2nd = rs.getString(14);
                //end
                //update by satrya 2012-10-09
                long religion_id = rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]);

                presenceReportDaily.setScheduleId1(firstSchedule);
                presenceReportDaily.setScheduleId2(secondSchedule);
                // first schedule data
                Vector vectFirstSchedule = PstScheduleSymbol.getScheduleData(firstSchedule, selectedDate);
                String strSchedule1st = "";
                int intCatSchedule1st = 0;
                Date dtScheduleIn1st = null;
                Date dtScheduleOut1st = null;
                //update by satrya 2012-09-26
                Date dtScheduleBo1st = null;
                Date dtScheduleBi1st = null;
                String scheduleDesc1st = "";
                //update by satrya 2013-05-28
                Date schIn = null;
                Date schOut = null;
                Date schBreakIn = null;
                Date schBreakOut = null;
                if (vectFirstSchedule != null && vectFirstSchedule.size() > 0) {
                    Vector vectSchldTemp = (Vector) vectFirstSchedule.get(0);
                    strSchedule1st = String.valueOf(vectSchldTemp.get(0));
                    intCatSchedule1st = Integer.parseInt(String.valueOf(vectSchldTemp.get(1)));
                    dtScheduleIn1st = (Date) vectSchldTemp.get(2);
                    dtScheduleOut1st = (Date) vectSchldTemp.get(3);
                    //update by satrya 2012-09-26
                    dtScheduleBo1st = (Date) vectSchldTemp.get(4);//untuk schedule BO
                    dtScheduleBi1st = (Date) vectSchldTemp.get(5);//untuk schedule BI
                    scheduleDesc1st = (String) vectSchldTemp.get(6);//untuk schedule Descp

                }

                // second schedule data
                Vector vectSecondSchedule = PstScheduleSymbol.getScheduleData(secondSchedule, selectedDate);
                String strSchedule2nd = "";
                int intCatSchedule2nd = 0;
                Date dtScheduleIn2nd = null;
                Date dtScheduleOut2nd = null;
                //update by satrya 2012-09-26
                Date dtScheduleBo2nd = null;
                Date dtScheduleBi2nd = null;
                String scheduleDesc2nd = "";
                if (vectSecondSchedule != null && vectSecondSchedule.size() > 0) {
                    Vector vectSchldTemp = (Vector) vectSecondSchedule.get(0);
                    strSchedule2nd = String.valueOf(vectSchldTemp.get(0));
                    intCatSchedule2nd = Integer.parseInt(String.valueOf(vectSchldTemp.get(1)));
                    dtScheduleIn2nd = (Date) vectSchldTemp.get(2);
                    dtScheduleOut2nd = (Date) vectSchldTemp.get(3);
                    //update by satrya 2012-09-26
                    dtScheduleBo2nd = (Date) vectSchldTemp.get(4);//untuk schedule BO
                    dtScheduleBi2nd = (Date) vectSchldTemp.get(5);//untuk schedule BI
                    scheduleDesc2nd = (String) vectSchldTemp.get(6);//untuk schedule Descp
                }

                //updateby satrya 2013-05-28

                presenceReportDaily.setSchTimeIn(schIn);
                presenceReportDaily.setSchTimeOut(schOut);
                presenceReportDaily.setSchBreakOut(schBreakOut);
                presenceReportDaily.setSchBreakIn(schBreakIn);

                presenceReportDaily.setSelectedDate(selectedDate);
                presenceReportDaily.setEmpNum(strEmpNum);
                presenceReportDaily.setEmpFullName(strEmpName);
                presenceReportDaily.setSchldCategory1st(intCatSchedule1st);
                presenceReportDaily.setScheduleSymbol1(strSchedule1st);
                presenceReportDaily.setScheduleIn1st(dtScheduleIn1st);
                presenceReportDaily.setScheduleOut1st(dtScheduleOut1st);
                presenceReportDaily.setSchldCategory2nd(intCatSchedule2nd);
                presenceReportDaily.setScheduleSymbol2(strSchedule2nd);
                presenceReportDaily.setScheduleIn2nd(dtScheduleIn2nd);
                presenceReportDaily.setScheduleOut2nd(dtScheduleOut2nd);
                //update by satrya 2012-09-26
                presenceReportDaily.setScheduleBO1st(dtScheduleBo1st);
                presenceReportDaily.setScheduleBO2nd(dtScheduleBo2nd);
                presenceReportDaily.setScheduleBI1st(dtScheduleBi1st);
                presenceReportDaily.setScheduleBI2nd(dtScheduleBi2nd);
                presenceReportDaily.setScheduleDesc1st(scheduleDesc1st);
                presenceReportDaily.setScheduleDesc2nd(scheduleDesc2nd);
                //update by satrya 2012-10-09
                presenceReportDaily.setReligion_id(religion_id);
                //update by satrya 2012-10-31
                presenceReportDaily.setDepartement_id(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                //update by satrya 2014-02-10
                presenceReportDaily.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));

                if (rs.getTime(5) != null) {
                    presenceReportDaily.setActualIn1st(DBHandler.convertDate(rs.getDate(5), rs.getTime(5)));
                } else {
                    presenceReportDaily.setActualIn1st(rs.getTime(5));
                }

                if (rs.getTime(6) != null) {
                    presenceReportDaily.setActualIn2nd(DBHandler.convertDate(rs.getDate(6), rs.getTime(6)));
                } else {
                    presenceReportDaily.setActualIn2nd(rs.getTime(6));
                }

                if (rs.getTime(7) != null) {
                    presenceReportDaily.setActualOut1st(DBHandler.convertDate(rs.getDate(7), rs.getTime(7)));
                } else {
                    presenceReportDaily.setActualOut1st(rs.getTime(7));
                }

                if (rs.getTime(8) != null) {
                    presenceReportDaily.setActualOut2nd(DBHandler.convertDate(rs.getDate(8), rs.getTime(8)));
                } else {
                    presenceReportDaily.setActualOut2nd(rs.getTime(8));
                }

                int status1 = rs.getInt(9);
                int status2 = rs.getInt(10);

                presenceReportDaily.setStatus1(status1);
                presenceReportDaily.setStatus2(status2);
                //update by satrya 2012-08-20
                presenceReportDaily.setReasonNo1nd(reason1nd);
                presenceReportDaily.setReasonNo2nd(reason2nd);
                presenceReportDaily.setNote1nd(note1nd);
                presenceReportDaily.setNote2nd(note2nd);
                presenceReportDaily.setEmpScheduleId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
                //update by satrya 2012-07-23
                presenceReportDaily.setEmpId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]));

                //update by satrya 2012-07-23
//                presenceReportDaily.setDivisionName(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
//                presenceReportDaily.setDepartmentName(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
//                presenceReportDaily.setSectionName(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                //end
                result.add(presenceReportDaily);
            }
            // hidden by satrya 2014-05-26 dbrs = DBHandler.execQueryResult(sql);
        } catch (Exception e) {
            System.out.println("listEmpPresenceDaily exc : " + e.toString());
            return result;
        } finally {

            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * create by satrya 2013-08-17 untuk mengitung di report excel daily attd
     *
     * @param selectedDate
     * @param srcReportDailyXls
     * @return
     */
    public static Vector getListReportDailyXls(Date selectedDate, SrcReportDailyXls srcReportDailyXls, String getEmployeePresence) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        // get index field on selectedDate        
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);

        // get periodId from selectedDate        
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    //update by satrya 2012-12-23
                    + ", COMP." + PstPayGeneral.fieldNames[PstPayGeneral.FLD_COMPANY_NAME]
                    + ", DIVX." + PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    + ", DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    //update by satrya 2014-02-01
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    //update by satrya 2014-02-10
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH"
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    //update by satrya 2012-12-23
                    + " INNER JOIN " + PstPayGeneral.TBL_PAY_GENERAL + " AS COMP"
                    + " ON COMP." + PstPayGeneral.fieldNames[PstPayGeneral.FLD_GEN_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                    + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " AS DIVX"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                    + " = DIVX." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " LEFT JOIN " + PstSection.TBL_HR_SECTION + " AS SEC"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    //end

                    + " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = " + periodId;

            if (srcReportDailyXls.getStatusResign() == PstEmployee.YES_RESIGN) {
                //untuk mencari karyawan risigned
//               sql += "  AND (( EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
//                    + " = " +PstEmployee.YES_RESIGN + " AND " + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
//                    + " BETWEEN \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd  00:00:00")+ "\""  +" AND "+ "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd  23:59:59")+ "\"" 
//                    + " ) OR (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
//                    + " = " + PstEmployee.NO_RESIGN + "))";
                sql += " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.YES_RESIGN;
//                    + " = " +PstEmployee.YES_RESIGN
            } else if (srcReportDailyXls.getStatusResign() == PstEmployee.NO_RESIGN) {
                sql += " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;
            }
            if (srcReportDailyXls.getStsEmpCategorySel() != null && srcReportDailyXls.getStsEmpCategorySel().length() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + " IN  (" + srcReportDailyXls.getStsEmpCategorySel() + ")";
            }
            if (srcReportDailyXls.getOidDepartment() != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcReportDailyXls.getOidDepartment();
            }
            //update by satrya 2013-12-03
            if (srcReportDailyXls.getOidCompany() != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                        + " = " + srcReportDailyXls.getOidCompany();
            }
            if (srcReportDailyXls.getOidDivision() != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + srcReportDailyXls.getOidDivision();
            }
            if (srcReportDailyXls.getOidSection() != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcReportDailyXls.getOidSection();
            }
            //update by satrya 2013-04-08
            if (srcReportDailyXls.getReason_sts() != 0) {
                sql = sql + " AND " + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1]
                        + " = " + srcReportDailyXls.getReason_sts();
            }
            //update by satrya 2012-07-16
            if (srcReportDailyXls.getEmpNum() != null && srcReportDailyXls.getEmpNum().length() > 0) {
                Vector vectNum = logicParser(srcReportDailyXls.getEmpNum());
                sql = sql + " AND ";
                if (vectNum != null && vectNum.size() > 0) {
                    sql = sql + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + ")";
                }

            }
            //update by satrya 2012-07-16
            if (srcReportDailyXls.getFullName() != null && srcReportDailyXls.getFullName().length() > 0) {
                Vector vectFullName = logicParser(srcReportDailyXls.getFullName());
                sql = sql + " AND ";
                if (vectFullName != null && vectFullName.size() > 0) {
                    sql = sql + " ( ";
                    for (int i = 0; i < vectFullName.size(); i++) {
                        String str = (String) vectFullName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }

            }
            //update by satrya 2012-9-28

            if (srcReportDailyXls.getStatusSchedule() != null && srcReportDailyXls.getStatusSchedule().length() > 0) {
                sql = sql + " AND " + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1]
                        + " IN (" + srcReportDailyXls.getStatusSchedule() + ")";
            }

            if (getEmployeePresence != null && getEmployeePresence.length() > 0 && srcReportDailyXls.sizeStsPresenceSel() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN (" + getEmployeePresence + ")";
            }
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                AttendanceReportDaily attendanceReportDaily = new AttendanceReportDaily();

//                String strEmpNum = rs.getString(1);
//                String strEmpName = rs.getString(2);
                long firstSchedule = rs.getLong("SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1]);
                long secondSchedule = rs.getLong("SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]);
                //update by satrya 2012-08-20
//                int reason1nd = rs.getInt(11);
//                int reason2nd = rs.getInt(12);
//                String note1nd = rs.getString(13);
//                String note2nd = rs.getString(14);
                //end
                //update by satrya 2012-10-09
                // long religion_id= rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]);


                // first schedule data
                Vector vectFirstSchedule = PstScheduleSymbol.getScheduleData(firstSchedule, selectedDate);
                String strSchedule1st = "";
                int intCatSchedule1st = 0;
                Date dtScheduleIn1st = null;
                Date dtScheduleOut1st = null;
                //update by satrya 2012-09-26
                Date dtScheduleBo1st = null;
                Date dtScheduleBi1st = null;
                String scheduleDesc1st = "";
                //update by satrya 2013-05-28

//                Date schIn = null;
//                Date schOut = null;
//                Date schBreakIn = null;
//                Date schBreakOut = null;
                if (vectFirstSchedule != null && vectFirstSchedule.size() > 0) {
                    Vector vectSchldTemp = (Vector) vectFirstSchedule.get(0);
                    strSchedule1st = String.valueOf(vectSchldTemp.get(0));
                    intCatSchedule1st = Integer.parseInt(String.valueOf(vectSchldTemp.get(1)));
                    dtScheduleIn1st = (Date) vectSchldTemp.get(2);
                    dtScheduleOut1st = (Date) vectSchldTemp.get(3);
                    //update by satrya 2012-09-26
                    dtScheduleBo1st = (Date) vectSchldTemp.get(4);//untuk schedule BO
                    dtScheduleBi1st = (Date) vectSchldTemp.get(5);//untuk schedule BI
                    scheduleDesc1st = (String) vectSchldTemp.get(6);//untuk schedule Descp

                }

                // second schedule data
//                Vector vectSecondSchedule = PstScheduleSymbol.getScheduleData(secondSchedule, selectedDate);
//                String strSchedule2nd = "";
//                int intCatSchedule2nd = 0;
//                Date dtScheduleIn2nd = null;
//                Date dtScheduleOut2nd = null;
//                //update by satrya 2012-09-26
//                Date dtScheduleBo2nd = null;
//                Date dtScheduleBi2nd = null;
//                String scheduleDesc2nd = "";
//                if (vectSecondSchedule != null && vectSecondSchedule.size() > 0) {
//                    Vector vectSchldTemp = (Vector) vectSecondSchedule.get(0);
//                    strSchedule2nd = String.valueOf(vectSchldTemp.get(0));
//                    intCatSchedule2nd = Integer.parseInt(String.valueOf(vectSchldTemp.get(1)));
//                    dtScheduleIn2nd = (Date) vectSchldTemp.get(2);
//                    dtScheduleOut2nd = (Date) vectSchldTemp.get(3);
//                    //update by satrya 2012-09-26
//                    dtScheduleBo2nd = (Date) vectSchldTemp.get(4);//untuk schedule BO
//                    dtScheduleBi2nd = (Date) vectSchldTemp.get(5);//untuk schedule BI
//                     scheduleDesc2nd = (String) vectSchldTemp.get(6);//untuk schedule Descp
//                }

                //updateby satrya 2013-05-28
                attendanceReportDaily.setSchedule1st(firstSchedule);
                //presenceReportDaily.setScheduleId2(secondSchedule);
                attendanceReportDaily.setSchTimeIn1st(dtScheduleIn1st);
                attendanceReportDaily.setSchTimeOut1st(dtScheduleOut1st);
                attendanceReportDaily.setSchBreakOut1st(dtScheduleBo1st);
                attendanceReportDaily.setSchBreakIn1st(dtScheduleBi1st);

                attendanceReportDaily.setSelectedDt(selectedDate);
                attendanceReportDaily.setPayrollNumb(rs.getString("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                attendanceReportDaily.setFullName(rs.getString("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                attendanceReportDaily.setSchCategory1st(intCatSchedule1st);
                attendanceReportDaily.setScheduleSymbol1st(strSchedule1st);
                //update by satrya 2014-02-10
                attendanceReportDaily.setEmpCategoriId(rs.getLong("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));

                Date actualIn = null;
                Date actualOut = null;
                if (rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]) != null) {
                    actualIn = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]));
                }
                if (rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]) != null) {
                    actualOut = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]));
                }
                attendanceReportDaily.setTimeIn(actualIn);
                attendanceReportDaily.setTimeOut(actualOut);
//                attendanceReportDaily.setSchldCategory2nd(intCatSchedule2nd);
//                attendanceReportDaily.setScheduleSymbol2(strSchedule2nd);
//                attendanceReportDaily.setScheduleIn2nd(dtScheduleIn2nd);
//                attendanceReportDaily.setScheduleOut2nd(dtScheduleOut2nd);

                attendanceReportDaily.setSchBreakOut1st(dtScheduleBo1st);

                attendanceReportDaily.setSchBreakIn1st(dtScheduleBi1st);
                attendanceReportDaily.setNote1st(scheduleDesc1st);

//                presenceReportDaily.setScheduleBO2nd(dtScheduleBo2nd);
//                presenceReportDaily.setScheduleBI2nd(dtScheduleBi2nd);
//                presenceReportDaily.setScheduleDesc2nd(scheduleDesc2nd);

                // presenceReportDaily.setReligion_id(religion_id);
                attendanceReportDaily.setCompanyName(rs.getString(PstPayGeneral.fieldNames[PstPayGeneral.FLD_COMPANY_NAME]));
                attendanceReportDaily.setDivisionName(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                attendanceReportDaily.setDepartementName(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                attendanceReportDaily.setSectionName(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                //update by satrya 2014-02-01
                attendanceReportDaily.setDepartmentId(rs.getLong("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                attendanceReportDaily.setReligionId(rs.getLong("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]));

//                
//                if (rs.getTime("SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]) != null) {
//                    attendanceReportDaily.setSchTimeIn1st(DBHandler.convertDate(rs.getDate("SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]), rs.getTime("SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1])));
//                } else {
//                    attendanceReportDaily.setSchTimeIn1st(null);
//                }
//                
////                
////                if (rs.getTime(6) != null) {
////                    presenceReportDaily.setActualIn2nd(DBHandler.convertDate(rs.getDate(6), rs.getTime(6)));
////                } else {
////                    presenceReportDaily.setActualIn2nd(rs.getTime(6));
////                }
//
//                if (rs.getTime("SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]) != null) {
//                    attendanceReportDaily.setSchTimeOut1st(DBHandler.convertDate(rs.getDate("SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]), rs.getTime("SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1])));
//                } else {
//                    attendanceReportDaily.setSchTimeOut1st(null);
//                }
//               
//                if (rs.getTime(8) != null) {
//                    presenceReportDaily.setActualOut2nd(DBHandler.convertDate(rs.getDate(8), rs.getTime(8)));
//                } else {
//                    presenceReportDaily.setActualOut2nd(rs.getTime(8));
//                }

                attendanceReportDaily.setStatus1st(rs.getInt("SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1]));
                // presenceReportDaily.setStatus2(status2);

                attendanceReportDaily.setReason1st(rs.getInt("SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1]));
                //presenceReportDaily.setReasonNo2nd(reason2nd);
                attendanceReportDaily.setNote1st(rs.getString("SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + idxFieldName - 1]));
                //presenceReportDaily.setNote2nd(note2nd);
                // attendanceReportDaily.setEmpScheduleId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
                //update by satrya 2012-07-23
                attendanceReportDaily.setEmployeeId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]));

                //update by satrya 2012-07-23
//                presenceReportDaily.setDivisionName(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
//                presenceReportDaily.setDepartmentName(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
//                presenceReportDaily.setSectionName(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                //end
                result.add(attendanceReportDaily);
            }
            // dbrs = DBHandler.execQueryResult(sql);
        } catch (Exception e) {
            System.out.println("listEmpPresenceDaily exc : " + e.toString());
            return result;
        } finally {

            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * untuk report excel report daily create by satrya 2013-08-17
     *
     * @param srcReportDailyXls
     * @return
     */
    public static Vector listEmpPresenceDailyXls(SrcReportDailyXls srcReportDailyXls) {
        //public static Vector listEmpPresenceDaily(long departmentId, Date fromDate, Date toDate, long sectionId, String empNumId, String fullName,String status1st, int limitStart, int recordToGet,Vector stStatus) {
//public static Vector listEmpPresenceDaily(long departmentId, Date fromDate, Date toDate, long sectionId, String empNumId, String fullName,String status1st, int limitStart, int recordToGet) {
        Vector result = new Vector();



        // loo[p dateFrom sampai toDate
        //panggil

        if (srcReportDailyXls != null && srcReportDailyXls.getSelectedDateFrom() != null && srcReportDailyXls.getSelectedDateTo() != null) {

            if (srcReportDailyXls.getSelectedDateFrom().getTime() > srcReportDailyXls.getSelectedDateTo().getTime()) {
                Date tempFromDate = srcReportDailyXls.getSelectedDateFrom();
                Date tempToDate = srcReportDailyXls.getSelectedDateTo();
                srcReportDailyXls.setSelectedDateFrom(tempToDate);
                srcReportDailyXls.setSelectedDateTo(tempFromDate);
            }
            long diffStartToFinish = srcReportDailyXls.getSelectedDateTo().getTime() - srcReportDailyXls.getSelectedDateFrom().getTime();
            int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));

            Date startDt = null;
            Date endDt = null;
            String getEmployeePresence = "";
            for (int i = 0; i <= itDate; i++) {
                /**
                 * kasusnya : "2013-03-01 12:00:00" AND "2013-03-04 14:00:00";
                 * maka di pecah menjadi: "2013-03-01 12:00:00" AND "2013-03-01
                 * 23:59:00"; "2013-03-02 00:00:00" AND "2013-03-02 23:59:00";
                 * "2013-03-03 00:00:00" AND "2013-03-03 23:59:00"; "2013-03-04
                 * 00:00:00" AND "2013-03-04 14:00:00";
                 */
                if (srcReportDailyXls.sizeStsPresenceSel() > 0) {
                    if (itDate == 0) {
                        startDt = (Date) srcReportDailyXls.getSelectedDateFrom().clone();
                        endDt = (Date) srcReportDailyXls.getSelectedDateTo().clone();

                    } else if (i == 0 && itDate > 0) {

                        startDt = (Date) srcReportDailyXls.getSelectedDateFrom().clone();

                        endDt = (Date) srcReportDailyXls.getSelectedDateTo().clone();
                        endDt.setHours(23);
                        endDt.setMinutes(59);
                        endDt.setSeconds(59);

                    } else if (itDate == i && itDate > 0) {
                        startDt = (Date) srcReportDailyXls.getSelectedDateTo().clone();
                        startDt.setHours(0);
                        startDt.setMinutes(0);
                        startDt.setSeconds(0);

                        endDt = (Date) srcReportDailyXls.getSelectedDateTo().clone();

                    } else {
                        startDt = (Date) new Date(srcReportDailyXls.getSelectedDateFrom().getTime() + i * 1000L * 60 * 60 * 24).clone();

                        startDt.setHours(0);
                        startDt.setMinutes(0);
                        startDt.setSeconds(0);

                        endDt = (Date) new Date(srcReportDailyXls.getSelectedDateFrom().getTime() + i * 1000L * 60 * 60 * 24).clone();
                        endDt.setHours(23);
                        endDt.setMinutes(59);
                        endDt.setSeconds(59);
                    }
                }
                if (startDt != null && endDt != null) {
                    getEmployeePresence = PstPresence.getEmployee(0, 0, "", srcReportDailyXls.getOidDepartment(), srcReportDailyXls.getFullName().trim(),
                            startDt, endDt, srcReportDailyXls.getOidSection(), srcReportDailyXls.getEmpNum().trim(), srcReportDailyXls.getStatusPresence(), srcReportDailyXls.getOidCompany(), srcReportDailyXls.getOidDivision());

                    if (getEmployeePresence.length() <= 0) {
                        getEmployeePresence = "" + 0;
                    }

                }

                Date selectedDate = new Date(srcReportDailyXls.getSelectedDateFrom().getTime() + i * 1000L * 60 * 60 * 24);//Date(getFromDate().getYear(), getFromDate().getMonth(), (getFromDate().getDate() + i));

                Vector dPresence = getListReportDailyXls(selectedDate, srcReportDailyXls, getEmployeePresence);
                result.addAll(dPresence);

            }
        }
        return result;
    }

    /**
     *
     * @param empId
     * @param selectedDate
     * @return
     */
    //update by satrya 2012-08-11
    //untuk list attendance edit
    public static Vector listEmpPresenceDailyAttendanceEdit(long empId, Date selectedDate) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        // get index field on selectedDate        
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);

        // get periodId from selectedDate        
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);

        try {
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    //update by satrya 2012-08-20
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + idxFieldName - 1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_NOTE + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
                    // end
                    //update by satrya 2012-08-11
                    //menambhakn break in break out
                    /*+ ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_BREAK_OUT + idxFieldName - 1]
                     + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_BREAK_IN + idxFieldName - 1]
                     */
                    //end
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]
                    //update by satrya 2012-08-20
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH"
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = " + periodId;

            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            //System.out.println("\tlistEmpPresenceDaily : " + sql);
            java.sql.Date sqlCurrDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());
            java.sql.Date sqlNextDate = new java.sql.Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate() + 1);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {

                PresenceReportDaily presenceReportDaily = new PresenceReportDaily();

                String strEmpNum = rs.getString(1);
                String strEmpName = rs.getString(2);
                long firstSchedule = rs.getLong(3);
                long secondSchedule = rs.getLong(4);
                //update by satrya 2012-08-20
                int reason1nd = rs.getInt(11);
                int reason2nd = rs.getInt(12);
                String note1nd = rs.getString(13);
                String note2nd = rs.getString(14);
                //end
                presenceReportDaily.setScheduleId1(firstSchedule);
                presenceReportDaily.setScheduleId2(secondSchedule);
                // first schedule data
                Vector vectFirstSchedule = PstScheduleSymbol.getScheduleData(firstSchedule, selectedDate);
                String strSchedule1st = "";
                int intCatSchedule1st = 0;
                Date dtScheduleIn1st = null;
                Date dtScheduleOut1st = null;
                if (vectFirstSchedule != null && vectFirstSchedule.size() > 0) {
                    Vector vectSchldTemp = (Vector) vectFirstSchedule.get(0);
                    strSchedule1st = String.valueOf(vectSchldTemp.get(0));
                    intCatSchedule1st = Integer.parseInt(String.valueOf(vectSchldTemp.get(1)));
                    dtScheduleIn1st = (Date) vectSchldTemp.get(2);
                    dtScheduleOut1st = (Date) vectSchldTemp.get(3);
                }

                // second schedule data
                Vector vectSecondSchedule = PstScheduleSymbol.getScheduleData(secondSchedule, selectedDate);
                String strSchedule2nd = "";
                int intCatSchedule2nd = 0;
                Date dtScheduleIn2nd = null;
                Date dtScheduleOut2nd = null;
                if (vectSecondSchedule != null && vectSecondSchedule.size() > 0) {
                    Vector vectSchldTemp = (Vector) vectSecondSchedule.get(0);
                    strSchedule2nd = String.valueOf(vectSchldTemp.get(0));
                    intCatSchedule2nd = Integer.parseInt(String.valueOf(vectSchldTemp.get(1)));
                    dtScheduleIn2nd = (Date) vectSchldTemp.get(2);
                    dtScheduleOut2nd = (Date) vectSchldTemp.get(3);
                }

                presenceReportDaily.setSelectedDate(selectedDate);
                presenceReportDaily.setEmpNum(strEmpNum);
                presenceReportDaily.setEmpFullName(strEmpName);
                presenceReportDaily.setSchldCategory1st(intCatSchedule1st);
                presenceReportDaily.setScheduleSymbol1(strSchedule1st);
                presenceReportDaily.setScheduleIn1st(dtScheduleIn1st);
                presenceReportDaily.setScheduleOut1st(dtScheduleOut1st);
                presenceReportDaily.setSchldCategory2nd(intCatSchedule2nd);
                presenceReportDaily.setScheduleSymbol2(strSchedule2nd);
                presenceReportDaily.setScheduleIn2nd(dtScheduleIn2nd);
                presenceReportDaily.setScheduleOut2nd(dtScheduleOut2nd);


                if (rs.getTime(5) != null) {
                    presenceReportDaily.setActualIn1st(DBHandler.convertDate(rs.getDate(5), rs.getTime(5)));
                } else {
                    presenceReportDaily.setActualIn1st(rs.getTime(5));
                }

                if (rs.getTime(6) != null) {
                    presenceReportDaily.setActualIn2nd(DBHandler.convertDate(rs.getDate(6), rs.getTime(6)));
                } else {
                    presenceReportDaily.setActualIn2nd(rs.getTime(6));
                }

                if (rs.getTime(7) != null) {
                    presenceReportDaily.setActualOut1st(DBHandler.convertDate(rs.getDate(7), rs.getTime(7)));
                } else {
                    presenceReportDaily.setActualOut1st(rs.getTime(7));
                }

                if (rs.getTime(8) != null) {
                    presenceReportDaily.setActualOut2nd(DBHandler.convertDate(rs.getDate(8), rs.getTime(8)));
                } else {
                    presenceReportDaily.setActualOut2nd(rs.getTime(8));
                }
                //update by satrya 2012-08-11
                //untuk yang break Out
                /*if (rs.getTime(15) != null) {
                 presenceReportDaily.setActualBreakOut(DBHandler.convertDate(rs.getDate(15), rs.getTime(15)));
                 } else {
                 presenceReportDaily.setActualBreakOut(rs.getTime(15));
                 }
                 //end break out
                 //untuk break in
                 if (rs.getTime(16) != null) {
                 presenceReportDaily.setActualBreakIn(DBHandler.convertDate(rs.getDate(16), rs.getTime(16)));
                 } else {
                 presenceReportDaily.setActualBreakIn(rs.getTime(16));
                 }*/
                //end

                int status1 = rs.getInt(9);
                int status2 = rs.getInt(10);

                presenceReportDaily.setStatus1(status1);
                presenceReportDaily.setStatus2(status2);
                //update by satrya 2012-08-20
                presenceReportDaily.setReasonNo1nd(reason1nd);
                presenceReportDaily.setReasonNo2nd(reason2nd);
                presenceReportDaily.setNote1nd(note1nd);
                presenceReportDaily.setNote2nd(note2nd);
                presenceReportDaily.setEmpScheduleId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
                //update by satrya 2012-07-23
                presenceReportDaily.setEmpId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]));
                //end
                result.add(presenceReportDaily);
            }
            dbrs = DBHandler.execQueryResult(sql);
        } catch (Exception e) {
            System.out.println("listEmpPresenceDaily exc : " + e.toString());
            return result;
        } finally {

            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * @param presenceStatus
     * @param presenceReason
     * @return
     */
    public static int getAbsenceReason(int presenceStatus, int presenceReason) {

        int result = -1;

        if (presenceStatus == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) {
            switch (presenceReason) {
                case PstEmpSchedule.REASON_ABSENCE_ALPHA:
                    result = PstEmpSchedule.REASON_ABSENCE_ALPHA;
                    break;

                case PstEmpSchedule.REASON_ABSENCE_SICKNESS:
                    result = PstEmpSchedule.REASON_ABSENCE_SICKNESS;
                    break;

                case PstEmpSchedule.REASON_ABSENCE_DISPENSATION:
                    result = PstEmpSchedule.REASON_ABSENCE_DISPENSATION;
                    break;
            }
        }

        return result;
    }

    /**
     * @Author Roy Andika
     * @Desc Untuk mendapatkan employee reason
     * @param departmentId
     * @param oidSection
     * @param periodId
     * @return
     */
    public static Vector getEmployeeReason(long departmentId, long oidSection, long periodId) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        // System.out.println("departmentName"+departmentName);
        if (departmentId == 0 && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstMarital.TBL_HR_MARITAL + " AS MAR"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH "
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP. " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0";

            String whereClause = "";
            String statusClause = " AND  (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE + ")";



            if (departmentId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + departmentId + " AND ";
            }

            if (oidSection != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + oidSection + " AND ";
            }

            if (periodId != 0) {
                whereClause = whereClause + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause + statusClause;
                //sql = sql + " WHERE " + whereClause;
            } else {
                sql = sql + " WHERE " + statusClause;
            }


            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            //sql = sql + " LIMIT " + start + "," + recordToGet;

            //System.out.println("\t SQL list getReason : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Marital marital = new Marital();
                Religion religion = new Religion();
                EmpSchedule empSchedule = new EmpSchedule();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                vect.add(employee);

                marital.setOID(rs.getLong(PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]));
                marital.setMaritalCode(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]));
                vect.add(marital);

                religion.setOID(rs.getLong(PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]));
                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static Vector getEmployeeReasonYear(long departmentId, Vector currYear, long sectionId) {

        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        // System.out.println("departmentName"+departmentName);
        if (departmentId == 0 && currYear == null) {
            return new Vector(1, 1);
        }

        try {

            String sql = " SELECT DISTINCT (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")"
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstMarital.TBL_HR_MARITAL + " AS MAR"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH "
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP. " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0";


            String whereClause = "";
            String statusClause = " AND  (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE + ")";

            String wherePeriod = "";

            if (departmentId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + departmentId + " AND ";
            }

            if (sectionId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + sectionId + " AND ";
            }

            if (currYear != null && currYear.size() > 0) {

                //whereClause = whereClause + " SCH."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]+"=";
                for (int i = 0; i < currYear.size(); i++) {
                    Period prd = (Period) currYear.get(i);
                    long periodId = prd.getOID();
                    if (i == currYear.size() - 1) {
                        wherePeriod = wherePeriod + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=" + periodId;
                        //System.out.println("terakhir  "+periodId);
                    } else {
                        wherePeriod = wherePeriod + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=" + String.valueOf(periodId) + " OR ";
                    }
                    // System.out.println("wherePeriod000000  ");
                }
                // whereClause = whereClause +"("+wherePeriod+")";


            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause + " AND (" + wherePeriod + ")" + statusClause;
                //sql = sql + " WHERE " + whereClause;
            } else {
                sql = sql + " WHERE " + " AND (" + wherePeriod + ")" + statusClause;
            }


            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            //sql = sql + " LIMIT " + start + "," + recordToGet;

            System.out.println("\t SQL SessEmpSchedule.getEmployeeReasonYear : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Marital marital = new Marital();
                Religion religion = new Religion();
                EmpSchedule empSchedule = new EmpSchedule();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                vect.add(employee);

                marital.setOID(rs.getLong(PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]));
                marital.setMaritalCode(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]));
                vect.add(marital);

                religion.setOID(rs.getLong(PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]));
                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  getEmployeeReasonYear : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static Vector getAbsenceReason(long employeeId, long periodId, int reason) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        // System.out.println("departmentName"+departmentName);
        if (employeeId == 0 && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstMarital.TBL_HR_MARITAL + " AS MAR"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH "
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP. " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + employeeId;


            String whereClause = "";
            String statusClause = " AND  (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE + ")";


            if (periodId != 0) {
                whereClause = whereClause + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }
            /* else
             {
             sql = sql + " WHERE " + statusClause;                  
             }*/
            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            //sql = sql + " LIMIT " + start + "," + recordToGet;

            // System.out.println("\t SQL list getAbsence : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                EmpSchedule empSchedule = new EmpSchedule();
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("1");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("2");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("3");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("4");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("5");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("6");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("7");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("8");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("9");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("10");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("11");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("12");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("13");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("14");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("15");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("16");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("17");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("18");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("19");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("20");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("21");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("22");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("23");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("24");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("25");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("26");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("27");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("28");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("29");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("30");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == reason) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("31");
                }

            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static Vector getEmpAbsence(long employeeId, long periodId) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        /*nilai konstanta untu cuti dan ijin stengah hari..
         *khusus untuk intimas
         */
        int CM = 5;
        int CMR = 3;
        int CSTD = 4;
        int I_STHR = 15;
        // System.out.println("departmentName"+departmentName);
        if (employeeId == 0 && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstMarital.TBL_HR_MARITAL + " AS MAR"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH "
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP. " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + employeeId;


            String whereClause = "";
            String statusClause = " AND  (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE + ")";


            if (periodId != 0) {
                whereClause = whereClause + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }
            /* else
             {
             sql = sql + " WHERE " + statusClause;                  
             }*/
            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            //sql = sql + " LIMIT " + start + "," + recordToGet;

            //System.out.println("\t SQL getEmpAbsence : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                EmpSchedule empSchedule = new EmpSchedule();
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("1");
                }

                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("2");
                }

                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("3");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("4");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("5");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("6");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("7");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("8");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("9");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("10");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("11");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("12");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("13");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("14");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("15");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("16");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("17");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("18");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("19");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("20");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("21");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("22");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("23");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("24");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("25");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("26");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("27");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("28");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("29");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("30");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == I_STHR)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("31");
                }

            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }
    /* this method created to get total employee's absences int meal allowance report
     * Created By Yunny
     */

    public static Vector getEmpAbsenceAllowance(long employeeId, long periodId) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        /*nilai konstanta untu cuti dan ijin stengah hari..
         *khusus untuk intimas
         */
        int CM = 5;
        int CMR = 3;
        int CSTD = 4;
        int I_STHR = 15;
        int NEW = 8;
        int CLBRJDW = 12;
        int LBRJDW = 16;
        int LBRHR = 17;
        int NOAB = 7;

        //id dari karyawan yang dibayar penuh. Khusus untuk intimas
        //long oidPosEng = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_KOMPRESSOR_POSITION")));
        // System.out.println("departmentName"+departmentName);
        if (employeeId == 0 && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstMarital.TBL_HR_MARITAL + " AS MAR"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH "
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP. " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + employeeId;

            String whereClause = "";
            String statusClause = " AND  (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31] + " = " + PstEmpSchedule.STATUS_PRESENCE_ABSENCE + " )";


            if (periodId != 0) {
                whereClause = whereClause + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }
            /* else
             {
             sql = sql + " WHERE " + statusClause;                  
             }*/
            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            //sql = sql + " LIMIT " + start + "," + recordToGet;

            // System.out.println("\t SQL getEmpAbsenceAllowance : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                EmpSchedule empSchedule = new EmpSchedule();
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("1");
                }

                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("2");
                }

                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("3");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("4");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("5");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("6");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("7");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("8");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("9");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("10");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("11");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("12");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("13");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("14");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("15");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("16");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == NEW) | (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("17");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("18");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("19");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("20");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("21");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("22");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("23");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("24");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("25");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("26");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("27");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("28");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("29");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("30");
                }
                if (((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == PstEmpSchedule.REASON_ABSENCE_ALPHA) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == PstEmpSchedule.REASON_ABSENCE_SICKNESS) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == PstEmpSchedule.REASON_ABSENCE_DISPENSATION) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == CM) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == CMR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == CSTD) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == I_STHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == NEW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == CLBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == LBRJDW) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == LBRHR) || (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == NOAB)) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)) {
                    result.add("31");
                }

            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /* this method created to get total employee's absences int meal allowance report
     * Created By Yunny
     */
    public static Vector getEmpPresenceAllowance(long employeeId, long periodId) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        /*nilai konstanta untu presence..
         *khusus untuk intimas
         */

        int ABT = 9;
        int ABRSK = 4;
        int TGS = 6;
        int NAB = 7;
        if (employeeId == 0 && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstMarital.TBL_HR_MARITAL + " AS MAR"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH "
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP. " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + employeeId;

            String whereClause = "";
            String statusClause = " AND  (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31] + " = " + PstEmpSchedule.STATUS_PRESENCE_OK + " )";


            if (periodId != 0) {
                whereClause = whereClause + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }
            /* else
             {
             sql = sql + " WHERE " + statusClause;                  
             }*/
            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            //sql = sql + " LIMIT " + start + "," + recordToGet;

            // System.out.println("\t SQL getEmpAbsenceAllowance : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                EmpSchedule empSchedule = new EmpSchedule();
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("1");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("2");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("3");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("4");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("5");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("6");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("7");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("8");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("9");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("10");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("11");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("12");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("13");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("14");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("15");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("16");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("17");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("18");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("19");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("20");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("21");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("22");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("23");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("24");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("25");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("26");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("27");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("28");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("29");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("30");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]) == PstEmpSchedule.STATUS_PRESENCE_OK)) {
                    result.add("31");
                }

            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /* This method used to get symbol id 
     * @ param oidEmp
     * @ dateSelected
     * created By Yunny
     */
    /*public static long getScheduleId(long oidEmp,Date selectedDate) {
     long result = 0;
     String sFieldDay = "D"+selectedDate.getDate();
     DBResultSet dbrs = null;
     long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
     //String strDate = "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "\"";
     try {
     String sql = "SELECT " + sFieldDay +" FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+
     " WHERE " +PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +"="+periodId+
     " AND "+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+"="+oidEmp;
     System.out.println("SessEmpSchedule.getScheduleId : "+sql);
     dbrs = DBHandler.execQueryResult(sql);
     ResultSet rs = dbrs.getResultSet();
     while (rs.next()) {
     result = rs.getLong(1);
     }
     } catch (Exception e) {
     return 0;
     } finally {
     DBResultSet.close(dbrs);
     return result;
     }
     }*/
    /* this method created to get total employee's late int meal allowance report
     * Created By Yunny
     */
    public static Vector getEmpLateAllowance(long employeeId, long periodId) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        if (employeeId == 0 && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstMarital.TBL_HR_MARITAL + " AS MAR"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH "
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP. " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + employeeId;

            String whereClause = "";
            String statusClause = " AND  (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE + " )";


            if (periodId != 0) {
                whereClause = whereClause + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }
            /* else
             {
             sql = sql + " WHERE " + statusClause;                  
             }*/
            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            //sql = sql + " LIMIT " + start + "," + recordToGet;

            // System.out.println("\t SQL getEmpAbsenceAllowance : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                EmpSchedule empSchedule = new EmpSchedule();
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("1");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("2");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("3");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("4");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("5");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("6");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("7");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("8");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("9");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("10");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("11");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("12");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("13");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("14");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("15");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("16");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("17");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("18");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("19");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("20");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("21");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("22");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("23");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("24");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("25");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("26");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("27");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("28");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("29");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("30");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]) == PstEmpSchedule.STATUS_PRESENCE_LATE)) {
                    result.add("31");
                }

            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /* this method created to get total employee's late int meal allowance report
     * Created By Yunny
     */
    public static Vector getEmpWithoutRegAllowance(long employeeId, long periodId) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        int NAB = 7;

        if (employeeId == 0 && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + ", MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + ", REL." + PstReligion.fieldNames[PstReligion.FLD_RELIGION]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]
                    + ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstMarital.TBL_HR_MARITAL + " AS MAR"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                    + " = MAR." + PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " AS REL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " = REL." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH "
                    + " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP. " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + employeeId;

            String whereClause = "";
            String statusClause = " AND  (SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE
                    + " OR SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31] + " = " + PstEmpSchedule.STATUS_PRESENCE_LATE + " )";


            if (periodId != 0) {
                whereClause = whereClause + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }
            /* else
             {
             sql = sql + " WHERE " + statusClause;                  
             }*/
            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            //sql = sql + " LIMIT " + start + "," + recordToGet;

            // System.out.println("\t SQL getEmpAbsenceAllowance : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                EmpSchedule empSchedule = new EmpSchedule();
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]) == NAB)) {
                    result.add("1");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]) == NAB)) {
                    result.add("2");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]) == NAB)) {
                    result.add("3");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]) == NAB)) {
                    result.add("4");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]) == NAB)) {
                    result.add("5");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]) == NAB)) {
                    result.add("6");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]) == NAB)) {
                    result.add("7");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]) == NAB)) {
                    result.add("8");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]) == NAB)) {
                    result.add("9");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]) == NAB)) {
                    result.add("10");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]) == NAB)) {
                    result.add("11");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]) == NAB)) {
                    result.add("12");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]) == NAB)) {
                    result.add("13");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14])) == NAB) {
                    result.add("14");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]) == NAB)) {
                    result.add("15");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]) == NAB)) {
                    result.add("16");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]) == NAB)) {
                    result.add("17");
                }

                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]) == NAB)) {
                    result.add("18");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]) == NAB)) {
                    result.add("19");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]) == NAB)) {
                    result.add("20");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]) == NAB)) {
                    result.add("21");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]) == NAB)) {
                    result.add("22");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]) == NAB)) {
                    result.add("23");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]) == NAB)) {
                    result.add("24");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]) == NAB)) {
                    result.add("25");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]) == NAB)) {
                    result.add("26");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]) == NAB)) {
                    result.add("27");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]) == NAB)) {
                    result.add("28");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]) == NAB)) {
                    result.add("29");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]) == NAB)) {
                    result.add("30");
                }
                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]) == PstEmpSchedule.STATUS_PRESENCE_ABSENCE) && (rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]) == NAB)) {
                    result.add("31");
                }

            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    /* This method used to get symbol id 
     * @ param oidEmp
     * @ dateSelected
     * created By Yunny
     */
    public static long getScheduleId(long oidEmp, Date selectedDate) {
        long result = 0;
        String sFieldDay = "D" + selectedDate.getDate();
        DBResultSet dbrs = null;
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
        //String strDate = "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "\"";
        try {
            String sql = "SELECT " + sFieldDay + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE
                    + " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=" + periodId
                    + " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + "=" + oidEmp;

            //System.out.println("SessEmpSchedule.getScheduleId : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getLong(1);
            }
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /*
     * 
     * create by roy andika
     *
     */
    public static long getSchId(long oidEmp, Date selectedDate) {

        long result = 0;

        DBResultSet dbrs = null;
        long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);

        try {
            String sql = "SELECT " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE
                    + " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=" + periodId
                    + " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + "=" + oidEmp;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getLong(1);
            }
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
            return 0;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /* This method used to get time in of schedule id
     * @ param oidEmp
     * @ dateSelected
     * created By Yunny
     */
    public static Date getTimeIn(long oidEmp, Date selectedDate) {
        Date result = new Date();
        DBResultSet dbrs = null;
        long scheduleId = SessEmpSchedule.getScheduleId(oidEmp, selectedDate);
        //String strDate = "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "\"";
        try {
            String sql = "SELECT " + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL
                    + " WHERE " + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + "=" + scheduleId;

            //System.out.println("SessEmpSchedule.getTimeIn : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getTime(1);
            }
        } catch (Exception e) {
            return new Date();
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /* this method used to get time in and time out
     * param @ dateSelected
     * param @ empOid
     * created By Yunny
     */
    public static Date getTimeOut(Date dateSelected, long empOid) {
        DBResultSet dbrs = null;
        Date dateResult = new Date();
        long periodId = PstPeriod.getPeriodIdBySelectedDate(dateSelected);
        try {
            String sFieldIn = "IN" + dateSelected.getDate();


            String sql = " SELECT " + sFieldIn + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE
                    + " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + "=" + empOid
                    + " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=" + periodId;
            //System.out.println("SQL SessEmpSchedule.getTimeOut() :::::: "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                dateResult = rs.getTime(1);
            }

        } catch (Exception e) {
            return new Date();
        } finally {
            DBResultSet.close(dbrs);
            return dateResult;
        }
    }

    /* This method used to get duration hours time in, time out
     * @ param hoursSchedule
     * @ param hoursReal
     * created By Yunny
     */
    public static int getDurationHours(int hoursSchedule, int hoursReal) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT (" + hoursSchedule + "-" + hoursReal + ")";
            //System.out.println("SessEmpSchedule.getDurationHours : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /* This method used to get duration minutes time in, time out
     * @ param minutesSchedule
     * @ param minutesReal
     * created By Yunny
     */
    public static int getDurationMinutes(int minutesSchedule, int minutesReal) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT (" + minutesSchedule + "-" + minutesReal + ")";
            //System.out.println("SessEmpSchedule.getDurationMinutes : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * Mengecek type schedule
     */
    public static synchronized Hashtable listScheduleOID(int type) {
        Hashtable hLeave = new Hashtable();
        DBResultSet dbrs = null;

        try {
            String query = "SELECT SY.* FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SY INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT ON SY." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] + " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] + " WHERE CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + type;
            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                ScheduleSymbol sc = new ScheduleSymbol();
                resultToObject(rs, sc);
                hLeave.put(String.valueOf(sc.getOID()), sc);
            }
        } catch (Exception e) {
            return new Hashtable();
        } finally {
            DBResultSet.close(dbrs);
            return hLeave;
        }
    }

    private static void resultToObject(ResultSet rs, ScheduleSymbol schedulesymbol) {
        try {
            schedulesymbol.setOID(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]));
            schedulesymbol.setScheduleCategoryId(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]));
            schedulesymbol.setSchedule(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]));
            schedulesymbol.setSymbol(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
            //schedulesymbol.setTimeIn(rs.getDate(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]));
            //schedulesymbol.setTimeOut(rs.getDate(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]));
            schedulesymbol.setTimeIn(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]));
            schedulesymbol.setTimeOut(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]));

            schedulesymbol.setMaxEntitle(rs.getInt(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_MAX_ENTITLE]));
            schedulesymbol.setPeriode(rs.getInt(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_PERIODE]));
            schedulesymbol.setPeriodeType(rs.getInt(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_PERIODE_TYPE]));
            schedulesymbol.setMinService(rs.getInt(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_MIN_SERVICE]));

        } catch (Exception e) {
        }
    }

    //Mencari schedule setelah suatu tanggal
    public static Vector searchEmpScheduleAfter(long empOid, Date startDate) {
        DBResultSet dbrs = null;

        //Mencari vector yang sesuai
        Vector vPeriod = new Vector(1, 1);
        String strWhere = PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " >= '" + Formater.formatDate(startDate, "yyyy-MM-dd") + "'";
        //System.out.println(">>>>>> "+strWhere);
        vPeriod = PstPeriod.list(0, 0, strWhere, "START_DATE DESC");
        String strPeriod = "";
        for (int i = 0; i < vPeriod.size(); i++) {
            Period period = (Period) vPeriod.get(i);
            if (strPeriod.length() > 0) {
                strPeriod += "," + period.getOID();
            } else {
                strPeriod += period.getOID();
            }
        }


        Vector result = new Vector(1, 1);
        if (empOid == 0) {
            return new Vector(1, 1);
        }

        try {
            String sql = " SELECT EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND2]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND3]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND4]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND5]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND6]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND7]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND8]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND9]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND10]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND11]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND12]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND13]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND14]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND15]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND16]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND17]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND18]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND19]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND20]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND21]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND22]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND23]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND24]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND25]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND26]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND27]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND28]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND29]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND30]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND31]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]
                    + ", PRD." + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]
                    + " FROM "
                    + " " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " EMPSCD "
                    + " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " , " + PstPeriod.TBL_HR_PERIOD + " PRD "
                    + " WHERE "
                    + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = PRD." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]
                    + " AND EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + //added on Wed, 23-01-2003
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = 0 ";

            String whereClause = "";
            if ((empOid > 0)) {
                //whereClause = whereClause + " AND (";
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + " = " + empOid + "";
                whereClause = whereClause + " AND ";
            }

            if (strPeriod.length() > 0) {
                whereClause = whereClause + " EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                        + " IN (" + strPeriod + ") AND ";
            }


            if (whereClause != null && whereClause.length() > 0) {
                whereClause += " 1 = 1 ";
                sql = sql + " AND " + whereClause;
            }

            //sql = sql + " GROUP BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+ " LIMIT " + start + "," + recordToGet;
            sql = sql + " ORDER BY EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMPSCD." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID];


            // System.out.println("\t SQL searchEmpScheduleAfter : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                EmpSchedule empschedule = new EmpSchedule();
                Employee employee = new Employee();
                Period period = new Period();
                //                Department department = new Department();
                //                Position position = new Position();
                //                Section section = new Section();
                //                EmpCategory empcategory = new EmpCategory();
                //                Level level = new Level();

                empschedule.setOID(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
                empschedule.setPeriodId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]));
                empschedule.setEmployeeId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]));
                empschedule.setD1(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1]));
                empschedule.setD2(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2]));
                empschedule.setD3(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3]));
                empschedule.setD4(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4]));
                empschedule.setD5(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5]));
                empschedule.setD6(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6]));
                empschedule.setD7(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7]));
                empschedule.setD8(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8]));
                empschedule.setD9(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9]));
                empschedule.setD10(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10]));
                empschedule.setD11(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11]));
                empschedule.setD12(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12]));
                empschedule.setD13(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13]));
                empschedule.setD14(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14]));
                empschedule.setD15(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15]));
                empschedule.setD16(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16]));
                empschedule.setD17(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17]));
                empschedule.setD18(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18]));
                empschedule.setD19(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19]));
                empschedule.setD20(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20]));
                empschedule.setD21(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21]));
                empschedule.setD22(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22]));
                empschedule.setD23(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23]));
                empschedule.setD24(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24]));
                empschedule.setD25(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25]));
                empschedule.setD26(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26]));
                empschedule.setD27(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27]));
                empschedule.setD28(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28]));
                empschedule.setD29(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29]));
                empschedule.setD30(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30]));
                empschedule.setD31(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31]));

                empschedule.setD2nd1(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1]));
                empschedule.setD2nd2(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND2]));
                empschedule.setD2nd3(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND3]));
                empschedule.setD2nd4(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND4]));
                empschedule.setD2nd5(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND5]));
                empschedule.setD2nd6(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND6]));
                empschedule.setD2nd7(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND7]));
                empschedule.setD2nd8(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND8]));
                empschedule.setD2nd9(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND9]));
                empschedule.setD2nd10(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND10]));
                empschedule.setD2nd11(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND11]));
                empschedule.setD2nd12(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND12]));
                empschedule.setD2nd13(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND13]));
                empschedule.setD2nd14(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND14]));
                empschedule.setD2nd15(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND15]));
                empschedule.setD2nd16(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND16]));
                empschedule.setD2nd17(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND17]));
                empschedule.setD2nd18(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND18]));
                empschedule.setD2nd19(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND19]));
                empschedule.setD2nd20(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND20]));
                empschedule.setD2nd21(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND21]));
                empschedule.setD2nd22(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND22]));
                empschedule.setD2nd23(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND23]));
                empschedule.setD2nd24(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND24]));
                empschedule.setD2nd25(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND25]));
                empschedule.setD2nd26(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND26]));
                empschedule.setD2nd27(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND27]));
                empschedule.setD2nd28(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND28]));
                empschedule.setD2nd29(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND29]));
                empschedule.setD2nd30(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND30]));
                empschedule.setD2nd31(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND31]));

                vect.add(empschedule);

                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                vect.add(employee);

                period.setPeriod(rs.getString(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]));
                period.setStartDate(rs.getDate(PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]));
                vect.add(period);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on searchEmpSchedule : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);

    }

    public static boolean isScheduleOff(Employee employee, Date date) {
        boolean status = false;

        //Cek apakah employee ada jadwal pada tgl tersebut?
        ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
        long lScheduleSymbolId = 0;
        lScheduleSymbolId = SessEmpSchedule.getScheduleId(employee.getOID(), date);

        try {
            scheduleSymbol = (ScheduleSymbol) PstScheduleSymbol.fetchExc(lScheduleSymbolId);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        long lScheduleCategoryId = 0;
        lScheduleCategoryId = scheduleSymbol.getScheduleCategoryId();
        if (lScheduleCategoryId > 0) {
            ScheduleCategory scheduleCategory = new ScheduleCategory();
            try {
                scheduleCategory = (ScheduleCategory) PstScheduleCategory.fetchExc(lScheduleCategoryId);
            } catch (Exception ex) {
                System.out.println("[ERROR] com.dimata.harisma.session.leave.SPLeaveConfig isScheduleOff :::: " + ex.toString());
            }

            //Cek apakah employee day off atau tidak
            //Day Off
            if (scheduleCategory.getCategoryType() == PstScheduleCategory.CATEGORY_OFF) {
                return true;
            }
        }
        return status;
    }

    public static int updateSchedule(Date dateDay, long schId, String SchSymblId) {

        int day = dateDay.getDate();
        long lnSchdSymbl = Long.parseLong(SchSymblId);
        String Fld_Day = "";
        DBResultSet dbrs = null;

        if (day == 1) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1];
        } else if (day == 2) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2];
        } else if (day == 3) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3];
        } else if (day == 4) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4];
        } else if (day == 5) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5];
        } else if (day == 6) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6];
        } else if (day == 7) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7];
        } else if (day == 8) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8];
        } else if (day == 9) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9];
        } else if (day == 10) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10];
        } else if (day == 11) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11];
        } else if (day == 12) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12];
        } else if (day == 13) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13];
        } else if (day == 14) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14];
        } else if (day == 15) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15];
        } else if (day == 16) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16];
        } else if (day == 17) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17];
        } else if (day == 18) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18];
        } else if (day == 19) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19];
        } else if (day == 20) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20];
        } else if (day == 21) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21];
        } else if (day == 22) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22];
        } else if (day == 23) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23];
        } else if (day == 24) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24];
        } else if (day == 25) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25];
        } else if (day == 26) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26];
        } else if (day == 27) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27];
        } else if (day == 28) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28];
        } else if (day == 29) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29];
        } else if (day == 30) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30];
        } else if (day == 31) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31];
        }

        try {
            String sql = "Update " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " SET " + Fld_Day + "="
                    + SchSymblId + "," + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_SCHEDULE_TYPE] + "="
                    + PstEmpSchedule.SCHEDULE_TO_BE_CHECK
                    + " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + " = " + schId;

            int i = DBHandler.execUpdate(sql);

            System.out.println("SQL update schedule " + sql);

            return 1;

        } catch (Exception e) {
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }
    
    public static int updateScheduleReason(Date dateDay, long schId, long lnSchdSymbl) {

        int day = dateDay.getDate();
        String Fld_Day = "";
        DBResultSet dbrs = null;

        if (day == 1) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1];
        } else if (day == 2) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2];
        } else if (day == 3) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3];
        } else if (day == 4) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4];
        } else if (day == 5) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5];
        } else if (day == 6) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6];
        } else if (day == 7) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7];
        } else if (day == 8) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8];
        } else if (day == 9) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9];
        } else if (day == 10) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10];
        } else if (day == 11) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11];
        } else if (day == 12) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12];
        } else if (day == 13) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13];
        } else if (day == 14) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14];
        } else if (day == 15) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15];
        } else if (day == 16) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16];
        } else if (day == 17) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17];
        } else if (day == 18) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18];
        } else if (day == 19) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19];
        } else if (day == 20) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20];
        } else if (day == 21) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21];
        } else if (day == 22) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22];
        } else if (day == 23) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23];
        } else if (day == 24) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24];
        } else if (day == 25) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25];
        } else if (day == 26) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26];
        } else if (day == 27) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27];
        } else if (day == 28) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28];
        } else if (day == 29) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29];
        } else if (day == 30) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30];
        } else if (day == 31) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31];
        }

        try {
            String sql = "Update " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " SET " + Fld_Day + "="
                    + lnSchdSymbl
                    + " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + " = " + schId;

            int i = DBHandler.execUpdate(sql);

            System.out.println("SQL update schedule " + sql);

            return 1;

        } catch (Exception e) {
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }
    
    public static int updateScheduleStatus(Date dateDay, long schId, long status) {

        int day = dateDay.getDate();
        String Fld_Day = "";
        DBResultSet dbrs = null;

        if (day == 1) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1];
        } else if (day == 2) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2];
        } else if (day == 3) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3];
        } else if (day == 4) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4];
        } else if (day == 5) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5];
        } else if (day == 6) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6];
        } else if (day == 7) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7];
        } else if (day == 8) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8];
        } else if (day == 9) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9];
        } else if (day == 10) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10];
        } else if (day == 11) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11];
        } else if (day == 12) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12];
        } else if (day == 13) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13];
        } else if (day == 14) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14];
        } else if (day == 15) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15];
        } else if (day == 16) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16];
        } else if (day == 17) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17];
        } else if (day == 18) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18];
        } else if (day == 19) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19];
        } else if (day == 20) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20];
        } else if (day == 21) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21];
        } else if (day == 22) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22];
        } else if (day == 23) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23];
        } else if (day == 24) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24];
        } else if (day == 25) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25];
        } else if (day == 26) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26];
        } else if (day == 27) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27];
        } else if (day == 28) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28];
        } else if (day == 29) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29];
        } else if (day == 30) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30];
        } else if (day == 31) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31];
        }

        try {
            String sql = "Update " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " SET " + Fld_Day + "="
                    + status
                    + " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + " = " + schId;

            int i = DBHandler.execUpdate(sql);

            System.out.println("SQL update schedule " + sql);

            return 1;

        } catch (Exception e) {
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }

    
    public static int updateScheduleReasonStatusAndSchedule(Date dateDay, long schId, long lnSchdSymbl, String leave) {

        String oidAl = String.valueOf(PstSystemProperty.getValueByName("OID_AL"));
        String oidLl = String.valueOf(PstSystemProperty.getValueByName("OID_LL"));
        String oidDp = String.valueOf(PstSystemProperty.getValueByName("OID_DP"));
        
        String leaveOid = "";
        if (leave.equals("AL")){
            leaveOid = oidAl;  
        } else if(leave.equals("LL")){
            leaveOid = oidLl;
        } else if(leave.equals("DP")){
            leaveOid = oidDp;
        }
        
        int day = dateDay.getDate();
        String Fld_Day = "";
        String Fld_Day_Status = "";
        String Fld_Day_D = "";
        DBResultSet dbrs = null;

        if (day == 1) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1];
        } else if (day == 2) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2];
        } else if (day == 3) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3];
        } else if (day == 4) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4];
        } else if (day == 5) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5];
        } else if (day == 6) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6];
        } else if (day == 7) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7];
        } else if (day == 8) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8];
        } else if (day == 9) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9];
        } else if (day == 10) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10];
        } else if (day == 11) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10];
        } else if (day == 12) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12];
        } else if (day == 13) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13];
        } else if (day == 14) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14];
        } else if (day == 15) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15];
        } else if (day == 16) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16];
        } else if (day == 17) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17];
        } else if (day == 18) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18];
        } else if (day == 19) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19];
        } else if (day == 20) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20];
        } else if (day == 21) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21];
        } else if (day == 22) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22];
        } else if (day == 23) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23];
        } else if (day == 24) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24];
        } else if (day == 25) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25];
        } else if (day == 26) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26];
        } else if (day == 27) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27];
        } else if (day == 28) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28];
        } else if (day == 29) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29];
        } else if (day == 30) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30];
        } else if (day == 31) {
            Fld_Day = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31];
            Fld_Day_Status = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31];
            Fld_Day_D = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31];
        }

        try {
            String sql = "Update " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " SET " + Fld_Day + "="
                    + lnSchdSymbl +","
                    + Fld_Day_Status + "="+ 7 +","
                    + Fld_Day_D + "="+ leaveOid +","
                    + " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + " = " + schId;

            int i = DBHandler.execUpdate(sql);

            System.out.println("SQL update schedule " + sql);

            return 1;

        } catch (Exception e) {
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }
    
    public static Vector listScheduleAbsence() {

        String whereScheduleCategory = PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                + " = " + PstScheduleCategory.CATEGORY_ABSENCE;


        Vector listScheduleCategory = new Vector();

        try {

            listScheduleCategory = PstScheduleCategory.list(0, 0, whereScheduleCategory, null);

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        ScheduleCategory scheduleCategory = new ScheduleCategory();

        if (!(listScheduleCategory != null && listScheduleCategory.size() > 0)) {
            return null;
        }
        String whereScheduleSymbol = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] + " IN (";
        for (int ix = 0; ix < listScheduleCategory.size(); ix++) {
            scheduleCategory = (ScheduleCategory) listScheduleCategory.get(ix);
            whereScheduleSymbol = whereScheduleSymbol + "  " + scheduleCategory.getOID() + (ix == listScheduleCategory.size() - 1 ? ")" : ",");
        }

        Vector result = new Vector();

        try {

            result = PstScheduleSymbol.list(0, 0, whereScheduleSymbol, null);

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        if (result != null && result.size() > 0) {
            return result;
        } else {
            return null;
        }

    }

    /**
     * @Desc : Untuk mendapatkan status schedule apakah AL,LL,DP,or SP
     * @return
     */
    public static boolean getStatusSchedule(long scheduleId) {

        long Al_oid = Long.parseLong(PstSystemProperty.getValueByName("OID_AL"));
        long Ll_oid = Long.parseLong(PstSystemProperty.getValueByName("OID_LL"));
        long Dp_oid = Long.parseLong(PstSystemProperty.getValueByName("OID_DP"));

        String sql_getSpecialLeave = PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                + " = " + PstScheduleCategory.CATEGORY_SPECIAL_LEAVE;

        String sql_getUnpaidLeave = PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                + " = " + PstScheduleCategory.CATEGORY_UNPAID_LEAVE;

        Vector listSpecialSchedule = new Vector();
        Vector listUnpaidSchedule = new Vector();

        listSpecialSchedule = PstScheduleCategory.list(0, 0, sql_getSpecialLeave, null);

        listUnpaidSchedule = PstScheduleCategory.list(0, 0, sql_getUnpaidLeave, null);

        if (scheduleId == Al_oid) {
            return true;
        } else if (scheduleId == Ll_oid) {
            return true;
        } else if (scheduleId == Dp_oid) {
            return true;
        } else {
            if (listSpecialSchedule != null && listSpecialSchedule.size() > 0) {

                ScheduleCategory scheduleCategory = new ScheduleCategory();
                scheduleCategory = (ScheduleCategory) listSpecialSchedule.get(0);

                String whereScheduleSymbol = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] + " = "
                        + scheduleCategory.getOID();

                Vector listScheduleSymbol = PstScheduleSymbol.list(0, 0, whereScheduleSymbol, null);

                if (listScheduleSymbol != null && listScheduleSymbol.size() > 0) {

                    for (int i = 0; i < listScheduleSymbol.size(); i++) {

                        ScheduleSymbol objScheduleSymbol = new ScheduleSymbol();

                        objScheduleSymbol = (ScheduleSymbol) listScheduleSymbol.get(i);

                        if (scheduleId == objScheduleSymbol.getOID()) {

                            return true;

                        }
                    }
                }
            }

            if (listUnpaidSchedule != null && listUnpaidSchedule.size() > 0) {

                ScheduleCategory scheduleCategory = new ScheduleCategory();
                scheduleCategory = (ScheduleCategory) listUnpaidSchedule.get(0);

                String whereScheduleSymbol = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] + " = "
                        + scheduleCategory.getOID();

                Vector listScheduleSymbol = PstScheduleSymbol.list(0, 0, whereScheduleSymbol, null);

                if (listScheduleSymbol != null && listScheduleSymbol.size() > 0) {

                    for (int i = 0; i < listUnpaidSchedule.size(); i++) {

                        ScheduleSymbol objScheduleSymbol = new ScheduleSymbol();

                        objScheduleSymbol = (ScheduleSymbol) listScheduleSymbol.get(i);

                        if (scheduleId == objScheduleSymbol.getOID()) {

                            return true;

                        }

                    }
                }
            }
            return false;
        }
    }

    /**
     * @DESC : UNTUK MENDAPATKAN CONFIGURASI SCHEDULE
     * @DEFAULT 1, SCHEDULE DIUPDATE SETELAH EKSEKUSI
     * @return
     */
    public static int GetConfigurasiUpdtSch() {

        int confUpdSchedule = PstEmpSchedule.UPDATE_SCHEDULE_AFTER_EXECUTION;

        try {
            confUpdSchedule = Integer.parseInt(PstSystemProperty.getValueByName("CONFIGURATION_LEAVE_UPDATE_SCHEDULE"));
        } catch (Exception e) {
            confUpdSchedule = PstEmpSchedule.UPDATE_SCHEDULE_AFTER_EXECUTION;
            System.out.println("Exception " + e.toString());
        }

        return confUpdSchedule;
    }

    public static boolean ScheduleExist(long employeeId, long periodId) {

        String where = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + " = " + employeeId + " AND "
                + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + " = " + periodId;

        Vector listSchedule = new Vector();

        try {
            listSchedule = PstEmpSchedule.list(0, 0, where, null);
        } catch (Exception e) {
            System.out.println();
        }

        if (listSchedule != null && listSchedule.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * @Author Roy A.
     * @param listEmpSch
     * @Desc UNTUK MENGUPDATE STATUS SCHEDULE TO BE CHECK PADA MENU DI HOME
     */
    public static void updateScheduleToBeCheck(Vector listEmpSch) {

        if (listEmpSch != null && listEmpSch.size() > 0) {

            for (int i = 0; i < listEmpSch.size(); i++) {

                EmpSchedule empSchedule = new EmpSchedule();

                try {
                    empSchedule = (EmpSchedule) listEmpSch.get(i);
                } catch (Exception E) {
                    System.out.println("Exception " + E.toString());
                }

                if (empSchedule.getOID() != 0) {
                    SessLeaveApplication.updateStatusSchedule(empSchedule.getOID());
                }
            }
        }
    }

    /**
     * @Author Roy A.
     * @Desc Untuk mendapatkan status dari schedule apakah bisa dubah atau tidak
     * @param day
     * @param postion
     * @return
     */
    public static boolean getDateUpdateSchedule(Date daySch, Position position, int pilihan_configurasi) {

        int hour_before = PstPosition.HOUR_UNLIMITED;
        int hour_after = PstPosition.HOUR_UNLIMITED;
        int hour_leave_before = PstPosition.HOUR_UNLIMITED;
        int hour_leave_after = PstPosition.HOUR_UNLIMITED;
        boolean schUpdate = true; /* true means schedule can update */

        Date test = SessLeaveApplication.DATE_ADD(new Date(), 1);

        if (pilihan_configurasi == PstPosition.UPDATE_SCHEDULE_BEFORE_TIME) {

            if (position.getDeadlineScheduleBefore() == PstPosition.HOUR_UNLIMITED) {

                return true;

            } else {

                long diff = 0;

                long hourDiff = 0;

                diff = (test.getTime() / (24L * 60L * 60L * 1000L) - new Date().getTime() / (24L * 60L * 60L * 1000L)) * (60L * 60L * 1000L);

                hourDiff = diff / 24 * 60 * 60 * 1000;

                System.out.println("value : " + hourDiff);

            }

        } else if (pilihan_configurasi == PstPosition.UPDATE_SCHEDULE_AFTER_TIME) {

            if (position.getDeadlineScheduleAfter() == PstPosition.HOUR_UNLIMITED) {

                return true; // schedule can update

            } else {

                /**
                 * @Desc Untuk batas perubahan schedule
                 */
                hour_after = position.getDeadlineScheduleAfter();

            }

        } else if (pilihan_configurasi == PstPosition.UPDATE_SCHEDULE_LEAVE_BEFORE_TIME) {

            if (position.getDeadlineScheduleLeaveBefore() == PstPosition.HOUR_UNLIMITED) {

                return true; // schedule can update

            } else {

                hour_leave_before = position.getDeadlineScheduleLeaveBefore();

            }

        } else if (pilihan_configurasi == PstPosition.UPDATE_SCHEDULE_LEAVE_AFTER_TIME) {

            if (position.getDeadlineScheduleLeaveAfter() == PstPosition.HOUR_UNLIMITED) { /* tidak ada limit peribahan schedule */

                return true; // schedule can update

            } else {

                hour_leave_after = position.getDeadlineScheduleLeaveAfter();

            }
        }

        return true;

    }

    private static Position getPosition(long employeeId) {

        DBResultSet dbrs = null;

        long positionID = 0;

        try {
            String sql = "SELECT " + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " WHERE "
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + employeeId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                positionID = rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]);

            }

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        Position position = new Position();

        try {

            position = PstPosition.fetchExc(positionID);
            return position;

        } catch (Exception E) {

            System.out.println("Exception " + E.toString());
        }

        return null;

    }

    /**
     * @Author Roy Andika
     * @param employeeId
     * @param typeLimit
     * @Desc 1440 berati 1 hari, 24 jam. 24 x 60
     * @Desc Untuk mendapatkan status apakah boleh mengubah schedule atau tidak
     * @Desc Bila False - > schedule tidak bisa di update
     * @Desc Bila true - > Schedule bisa di update
     * @return
     */
    public static boolean getstatusSchedule(long employeeId, int typeLimit, Date dateCheck) {

        /* bila employee id = 0 */

        if (employeeId == 0) {
            return false;
        }

        if (dateCheck.getDate() != new Date().getDate()) {

            String tmpDt = Formater.formatDate(dateCheck, "yyyy-MM-dd HH:mm:ss");
            dateCheck = Formater.formatDate(tmpDt, "yyyy-MM-dd HH:mm:ss");

        }

        Position position = new Position();

        try {

            position = getPosition(employeeId);

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        if (position != null && position.getOID() != 0) { /* Position id tidak null atau tidak 0 */

            if (typeLimit == PstPosition.UPDATE_SCHEDULE_BEFORE_TIME) {

                if (position.getDeadlineScheduleBefore() == PstPosition.HOUR_UNLIMITED) {
                    return true;
                }

                /* Untuk mengecek apakah datecheck lebih besar dibandingkan date current */
                int diffTime = Integer.parseInt("" + ((dateCheck.getTime() / (60L * 1000L)) - (new Date().getTime() / (60L * 1000L))));

                /* untuk mendapatkan berapa hari boleh dirubah schedulnya */
                long limitSch = position.getDeadlineScheduleBefore() * 60;

                if (diffTime < 0) {
                    diffTime = Integer.parseInt("" + ((new Date().getTime() / (60L * 1000L)) - (dateCheck.getTime() / (60L * 1000L))));
                }

                if (limitSch > diffTime) {

                    return true;

                } else {

                    return false;

                }

            } else if (typeLimit == PstPosition.UPDATE_SCHEDULE_AFTER_TIME) {

                if (position.getDeadlineScheduleAfter() == PstPosition.HOUR_UNLIMITED) {
                    return true;
                }

                long limitSch = position.getDeadlineScheduleAfter() * 60;

                long diffTime = (dateCheck.getTime() / (60L * 1000L)) - (new Date().getTime() / (60L * 1000L));

                if (diffTime < 0) { /* Untuk menjadikan diferent date selalu positif */
                    diffTime = (new Date().getTime() / (60L * 1000L)) - (dateCheck.getTime() / (60L * 1000L));
                }

                if (limitSch > diffTime) {

                    return true;

                } else {
                    return false;
                }

            } else if (typeLimit == PstPosition.UPDATE_SCHEDULE_LEAVE_BEFORE_TIME) {

                if (position.getDeadlineScheduleLeaveBefore() == PstPosition.HOUR_UNLIMITED) {
                    return true;
                }

                long limitSch = position.getDeadlineScheduleLeaveBefore() * 60;

                long diffTime = (dateCheck.getTime() / (60L * 1000L)) - (new Date().getTime() / (60L * 1000L));

                if (diffTime < 0) {
                    diffTime = (new Date().getTime() / (60L * 1000L)) - (dateCheck.getTime() / (60L * 1000L));
                }

                if (limitSch > diffTime) {
                    return true;
                } else {
                    return false;
                }

            } else if (typeLimit == PstPosition.UPDATE_SCHEDULE_LEAVE_AFTER_TIME) {

                if (position.getDeadlineScheduleLeaveBefore() == PstPosition.HOUR_UNLIMITED) {
                    return true;
                }

                long limitSch = position.getDeadlineScheduleLeaveAfter() * 60;

                long diffTime = (dateCheck.getTime() / (60L * 1000L)) - (new Date().getTime() / (60L * 1000L));

                if (diffTime < 0) {
                    diffTime = (new Date().getTime() / (60L * 1000L)) - (dateCheck.getTime() / (60L * 1000L));
                }

                if (limitSch > diffTime) {

                    return true;

                } else {
                    return false;
                }

            }
        }

        /* Jika kondisi false berati schedule tidak bisa diupdate */
        return false;
    }

    /**
     * @Author Roy Andika
     * @param employeeId
     * @param typeLimit
     * @Desc Untuk mendapatkan range waktu tanggal yang akan diubah
     * @Desc True - > tanggal belum lewat, False - > Tanggal sudah lewat
     * @return
     */
    public static boolean getRangeSchedule(long employeeId, Date dateCheck) {

        Position position = new Position();

        try {

            position = getPosition(employeeId);

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        if (position != null && position.getOID() != 0) {

            long diffTimes = (dateCheck.getTime() / (60L * 1000L)) - (new Date().getTime() / (60L * 1000L));

            if (diffTimes >= 0) {

                return true;

            } else {

                return false;

            }

        }

        return false;
    }

    /**
     * @Author Roy A
     * @param DtFirst
     * @param DtSecond
     * @Desc Untuk mendapatkan selisih tanggal
     * @return
     */
    public static boolean DateDiff(Date DtFirst, Date DtSecond) {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT DATEDIFF('" + Formater.formatDate(DtFirst, "yyyy-MM-dd") + "','" + Formater.formatDate(DtSecond, "yyyy-MM-dd") + "')";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                int diff = 0;
                diff = rs.getInt(1);

                if (diff == 0) {
                    return true;    /* tanggal calender berada pada hari ini */
                } else {
                    return false;
                }
            }

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return false;
    }

    /**
     * @Author Roy A
     * @param DtFirst
     * @param DtSecond
     * @Desc Untuk mendapatkan selisih tanggal
     * @return
     */
    public static int DateDifferent(Date DtFirst, Date DtSecond) {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT DATEDIFF('" + Formater.formatDate(DtFirst, "yyyy-MM-dd") + "','" + Formater.formatDate(DtSecond, "yyyy-MM-dd") + "')";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                return rs.getInt(1);

            }

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }

    public static void getDiffTime() {

        long diffTimes = 0;

        Date CurDt = new Date();
        Date PrvDt = new Date();

        CurDt.setHours(9);
        CurDt.setMinutes(40);
        CurDt.setSeconds(0);

        PrvDt.setDate(20);
        PrvDt.setHours(9);
        PrvDt.setMinutes(30);
        PrvDt.setSeconds(0);

        String time_prev = Formater.formatDate(PrvDt, "yyyy-MM-dd HH:mm:ss");
        String time_now = Formater.formatDate(CurDt, "yyyy-MM-dd HH:mm:ss");

        System.out.println("CURRENT TIME " + time_prev);
        System.out.println("CURRENT TIME " + time_now);

        diffTimes = (CurDt.getTime() / (60L * 1000L)) - (PrvDt.getTime() / (60L * 1000L));

    }

    //update by satrya 2012-09-14
    /**
     * Keterangan : listPermision ini adalah untuk mencari list leave dan
     * schedule Personal In dan OUT yang di set itu adalah
     * scheduleSymbolId,Schedule Type (BREAK/AL/DLL),Schedule DateTime,IN atau
     * Out Type
     *
     * @param employeeId
     * @param dateLeave
     * @create by satrya
     * @return
     *
     */
    public static Vector listLeavePermision(long employeeId, Date selectedDate, long departementId, long periodId) {
        Vector lists = new Vector();
        //mengambil schedule pada hari itu
        ScheduleSymbol scheduleDateTime = PstEmpSchedule.getEmpScheduleDateTime(periodId, employeeId, selectedDate, departementId);
        if (scheduleDateTime != null) {
            OutPermision outPermision = new OutPermision();
            //update by satrya 2012-09-25
            outPermision.setScheduleSymbolId(scheduleDateTime.getScheduleSymbolId());
            outPermision.setScheduleType("BREAK");
            outPermision.setTypeScheduleDateTime(scheduleDateTime.getBreakOut());
            outPermision.setInOutType(OutPermision.INOUT_TYPE_OUT);
            lists.add(outPermision);
            outPermision = new OutPermision();
            //update by satrya 2012-09-25
            outPermision.setScheduleSymbolId(scheduleDateTime.getScheduleSymbolId());
            outPermision.setScheduleType("BREAK");
            outPermision.setTypeScheduleDateTime(scheduleDateTime.getBreakIn());
            outPermision.setInOutType(OutPermision.INOUT_TYPE_IN);
            lists.add(outPermision);

        }
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT RST.* from (SELECT LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    /// String sql = "SELECT AL."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " , \"AL\" AS LEAVE_SYMBOL "
                    + " ,AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]
                    + " ,AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]
                    //update by satrya 2012-09-25
                    + " ,AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] + " AS ID_DETAIL_LEAVE "
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN  " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  "
                    + " ON ( AL." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    //update by satrya 2013-06-11                   
                    /*+ " WHERE  LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] 
                     + " = \"" +  employeeId  +"\" AND (LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                     + " BETWEEN  " +PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED
                     +" AND "+PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED+") AND " 
                     + " AL."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]
                     + " BETWEEN "+"\""+ Formater.formatDate(selectedDate, "yyyy-MM-dd 00:00:00") +"\" AND "+"\""+ Formater.formatDate(selectedDate, "yyyy-MM-dd 23:59:59")+"\""
                     + " AND LA." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                     + " = \"" +  employeeId  +"\" "*/
                    + " WHERE "
                    + "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd 23:59:59") + "\" > AL."
                    + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AND AL."
                    + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " , \"DP\" AS LEAVE_SYMBOL "
                    + " ,DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]
                    + " ,DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]
                    //update by satrya 2012-09-25
                    + " ,DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID] + " AS ID_DETAIL_LEAVE "
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + "  AS DP ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = DP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE "
                    /*+LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] 
                     + " = \"" +  employeeId  +"\" AND (LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                     + " BETWEEN  " +PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED
                     +" AND "+PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED+") AND "  
                     + " DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]
                     + " BETWEEN "+"\""+ Formater.formatDate(selectedDate, "yyyy-MM-dd 00:00:00")+"\" AND "+"\""+ Formater.formatDate(selectedDate, "yyyy-MM-dd 23:59:59")+"\""
                     + " AND LA." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                     + " = \"" +  employeeId  +"\" "*/
                    + "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd 23:59:59") + "\" > DP."
                    + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + " AND DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " , \"LL\" AS LEAVE_SYMBOL "
                    + " ,LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]
                    + " ,LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]
                    //update by satrya 2012-09-25
                    + " ,LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] + " AS ID_DETAIL_LEAVE "
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN
                    + " AS LL ON (LL." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE  "
                    /*LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] 
                     + " = \"" +  employeeId  +"\" AND (LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                     + " BETWEEN  " +PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED
                     +" AND "+PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED+") AND " 
                     + " LL."+ PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]
                     + " BETWEEN "+ "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd 00:00:00") + "\" AND "+ "\" "+ Formater.formatDate(selectedDate, "yyyy-MM-dd 23:59:59") +" \""
                     + " AND LA." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                     + " = \"" +  employeeId  +"\" "*/
                    + "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd 23:59:59") + "\" > LL."
                    + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AND LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " , SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + " AS LEAVE_SYMBOL "
                    + " , SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]
                    + " , SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]
                    //update by satrya 2012-09-25
                    + " ,SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID] + " AS ID_DETAIL_LEAVE "
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID] + ")"
                    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " WHERE  "
                    /*LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] 
                     + " = \"" +  employeeId  +"\" AND (LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                     + " BETWEEN  " +PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED
                     +" AND "+PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED+") AND " 
                     + " SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]
                     + " BETWEEN "+ "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd 00:00:00") + "\" AND "+ "\" "+ Formater.formatDate(selectedDate, "yyyy-MM-dd 23:59:59") +" \""
                     + " AND LA." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                     + " = \"" +  employeeId  +"\" )*/
                    + "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd 23:59:59") + "\" > SU."
                    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AND SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA."
                    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED + ")AS RST ORDER BY RST.TAKEN_DATE ";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                //LeaveApplication objLeaveApplication = new LeaveApplication();
                //resultToObject(rs, objLeaveApplication);
                OutPermision outPermision = new OutPermision();
                //update by satrya 2012-09-25
                outPermision.setScheduleSymbolId(rs.getLong("ID_DETAIL_LEAVE"));
                outPermision.setScheduleType(rs.getString("LEAVE_SYMBOL"));
                outPermision.setTypeScheduleDateTime(DBHandler.convertDate(rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]), rs.getTime(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE])));
                outPermision.setInOutType(OutPermision.INOUT_TYPE_OUT);
                lists.add(outPermision);
                outPermision = new OutPermision();
                //update by satrya 2012-09-25
                outPermision.setScheduleSymbolId(rs.getLong("ID_DETAIL_LEAVE"));
                outPermision.setScheduleType(rs.getString("LEAVE_SYMBOL"));
                outPermision.setTypeScheduleDateTime(DBHandler.convertDate(rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]), rs.getTime(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE])));
                outPermision.setInOutType(OutPermision.INOUT_TYPE_IN);
                lists.add(outPermision);
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;

    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the empNum
     */
    public String getEmpNum() {
        return this.empNum;
    }

    /**
     * @param empNum the empNum to set
     */
    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    /**
     * @return the empFullName
     */
    public String getEmpFullName() {
        return empFullName;
    }

    /**
     * @param empFullName the empFullName to set
     */
    public void setEmpFullName(String empFullName) {
        this.empFullName = empFullName;
    }
    //update by satrya2012-07-25

    /**
     * Keterangan : mengamvbil count employe schedule
     *
     * @param sessEmpSchedule
     * @param oidDepartment
     * @param selectedDateFrom
     * @param selectedDateTo
     * @param oidSection
     * @param empNum
     * @param fullName
     * @param status1st
     * @return
     */
    public static int getCountSessEmpSchedule(SessEmpSchedule sessEmpSchedule, long oidDepartment, Date selectedDateFrom, Date selectedDateTo, long oidSection, String empNum, String fullName, String status1st, Vector stsPresenceSel, int reasonSts, String stsEmpCategorySel, int statusResign, long oidCompany, long oidDivision) {
        return  getCountSessEmpSchedule(sessEmpSchedule, oidDepartment, selectedDateFrom, selectedDateTo, oidSection, empNum,fullName, status1st, stsPresenceSel, reasonSts,stsEmpCategorySel, statusResign, oidCompany, oidDivision,null, null);    
    }
    public static int getCountSessEmpSchedule(SessEmpSchedule sessEmpSchedule, long oidDepartment, Date selectedDateFrom, Date selectedDateTo, long oidSection, String empNum, String fullName, String status1st, Vector stsPresenceSel, int reasonSts, String stsEmpCategorySel, int statusResign, long oidCompany, long oidDivision, Date startPayperiod,Date endPayperiod) {
        int count = 0;

        if (selectedDateFrom != null && selectedDateTo != null) {
            if (selectedDateFrom.getTime() > selectedDateTo.getTime()) {
                Date tempFromDate = selectedDateFrom;
                Date tempToDate = selectedDateTo;
                selectedDateFrom = tempToDate;
                selectedDateTo = tempFromDate;
            }
            long diffStartToFinish = selectedDateTo.getTime() - selectedDateFrom.getTime();
            int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));


            Date startDt = new Date();
            Date endDt = new Date();
            String getEmployeePresence = "";
            for (int i = 0; i <= itDate; i++) {
                /**
                 * //update by satrya 2013-04-01 kasusnya : "2013-03-01
                 * 12:00:00" AND "2013-03-04 14:00:00"; maka di pecah menjadi:
                 * "2013-03-01 12:00:00" AND "2013-03-01 23:59:00"; "2013-03-02
                 * 00:00:00" AND "2013-03-02 23:59:00"; "2013-03-03 00:00:00"
                 * AND "2013-03-03 23:59:00"; "2013-03-04 00:00:00" AND
                 * "2013-03-04 14:00:00";
                 */
                if (stsPresenceSel != null && stsPresenceSel.size() > 0) {
                    if (itDate == 0) {
                        startDt = (Date) selectedDateFrom.clone();
                        endDt = (Date) selectedDateTo.clone();

                    } else if (i == 0 && itDate > 0) {

                        startDt = (Date) selectedDateFrom.clone();

                        endDt = (Date) selectedDateFrom.clone();
                        endDt.setHours(23);
                        endDt.setMinutes(59);
                        endDt.setSeconds(59);

                    } else if (itDate == i && itDate > 0) {
                        startDt = (Date) selectedDateTo.clone();
                        startDt.setHours(0);
                        startDt.setMinutes(0);
                        startDt.setSeconds(0);

                        endDt = (Date) selectedDateTo.clone();

                    } else {
                        startDt = (Date) new Date(selectedDateFrom.getTime() + i * 1000L * 60 * 60 * 24).clone();

                        startDt.setHours(0);
                        startDt.setMinutes(0);
                        startDt.setSeconds(0);

                        endDt = (Date) new Date(selectedDateFrom.getTime() + i * 1000L * 60 * 60 * 24).clone();
                        endDt.setHours(23);
                        endDt.setMinutes(59);
                        endDt.setSeconds(59);
                    }
                    getEmployeePresence = PstPresence.getEmployee(0, 0, "", oidDepartment, fullName.trim(),
                            startDt, endDt, oidSection, empNum.trim(), stsPresenceSel, oidCompany, oidDivision);

                    if (getEmployeePresence.length() <= 0) {
                        getEmployeePresence = "" + 0;
                    }
                }

                Date selectedDate = new Date(selectedDateFrom.getTime() + i * 1000L * 60 * 60 * 24);
                int countbyDate = countEmpPresenceDaily(oidDepartment, selectedDate, oidSection, empNum, fullName, status1st, getEmployeePresence, stsPresenceSel, reasonSts, stsEmpCategorySel, statusResign, oidCompany, oidDivision, startPayperiod,endPayperiod);

                //int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);
                int tempCount = count;

                count = tempCount + countbyDate;
            }
        }
        return count;
    }

    /**
     * Keterangan : mencari count schedule pada hari yang di pilih / selectred
     * date
     *
     * @param oidDepartment
     * @param selectedDate
     * @param oidSection
     * @param empNum
     * @param fullName
     * @param status1st
     * @return
     * @throws count = 0;
     */
    //public static int countEmpPresenceDaily(long oidDepartment, Date selectedDate, long oidSection, String empNum, String fullName,int limitStart,int recordToGet,String status1st ){  
    //update by satrya 2013-12-03
    //public static int countEmpPresenceDaily(long oidDepartment, Date selectedDate, long oidSection, String empNum, String fullName,String status1st,String getEmployeePresence,Vector stStatus,int reasonSts,String stsEmpCategorySel,int  statusResign){ 
    public static int countEmpPresenceDaily(long oidDepartment, Date selectedDate, long oidSection, String empNum, String fullName, String status1st, String getEmployeePresence, Vector stStatus, int reasonSts, String stsEmpCategorySel, int statusResign, long oidCompany, long oidDivision) {
       return countEmpPresenceDaily(oidDepartment, selectedDate, oidSection, empNum, fullName, status1st, getEmployeePresence, stStatus, reasonSts, stsEmpCategorySel, statusResign, oidCompany, oidDivision, null,null);
    }
    public static int countEmpPresenceDaily(long oidDepartment, Date selectedDate, long oidSection, String empNum, String fullName, String status1st, String getEmployeePresence, Vector stStatus, int reasonSts, String stsEmpCategorySel, int statusResign, long oidCompany ) {
       return countEmpPresenceDaily(oidDepartment, selectedDate, oidSection, empNum, fullName, status1st, getEmployeePresence, stStatus, reasonSts, stsEmpCategorySel, statusResign, oidCompany,0, null,null);
    }
    public static int countEmpPresenceDaily(long oidDepartment, Date selectedDate, long oidSection, String empNum, String fullName, String status1st, String getEmployeePresence, Vector stStatus, int reasonSts, String stsEmpCategorySel, int statusResign, long oidCompany, long oidDivision,Date startPayPeriod,Date endPayPeriod) {
        //update by satrya 2013-04-08
        //public static int countEmpPresenceDaily(long oidDepartment, Date selectedDate, long oidSection, String empNum, String fullName,String status1st,String getEmployeePresence,Vector stStatus ){

        int count = 0;
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(selectedDate);
        DBResultSet dbrs = null;
        try {
            long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
            String sql = " SELECT COUNT(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + ")"
                    + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH INNER JOIN "
                    + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP ON SCH." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " = " + periodId;
                    //update by satrya 2012-09-10
                    //untuk mencari karyawan risigned
//                    + "  AND (( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
//                    + " = " + PstEmployee.YES_RESIGN + " OR " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
//                    + " >= \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd  00:00:00") + "\""
//                    + " ) OR (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
//                    + " = " + PstEmployee.NO_RESIGN + "))";

            
            
            if (oidDepartment != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + oidDepartment;
            }
            //update by satrya 2013-12-03
            if (oidCompany != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                        + " = " + oidCompany;
            }
            //update by satrya 2013-12-03
            if (oidDivision != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + oidDivision;
            }
            if (oidSection != 0) {
                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + oidSection;
            }
            //update by satrya 2013-04-08
            if (reasonSts != 0) {
                sql = sql + " AND " + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldName - 1]
                        + " = " + reasonSts;
            }
//                    //update by satrya 2012-07-16
//                    if (empNum != null && empNum != "") {
//                        sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                                + " = " + "\"" + empNum + "\"";
//                    }
//                    //update by satrya 2012-07-16
//                    if (fullName != null && fullName != "") {
//                        sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                                + " LIKE " + "\"%" + fullName + "%\"";
//                    }
            //update by satrya 2012-07-16
            if (empNum != null && empNum != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                        + " = " + "\"" + empNumId.trim() + "\"";//penambahan trima
                Vector vectNum = logicParser(empNum);
                sql = sql + " AND ";
                if (vectNum != null && vectNum.size() > 0) {
                    sql = sql + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + ")";
                }

            }
            //update by satrya 2012-07-16
            if (fullName != null && fullName != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                        + " LIKE " + "\"%" + fullName.trim() + "%\"";//penambahan trim
                Vector vectFullName = logicParser(fullName);
                sql = sql + " AND ";
                if (vectFullName != null && vectFullName.size() > 0) {
                    sql = sql + " ( ";
                    for (int i = 0; i < vectFullName.size(); i++) {
                        String str = (String) vectFullName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }

            }
            //update by satrya 2013-08-16
            if (statusResign == PstEmployee.YES_RESIGN) {
                sql += " AND (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.YES_RESIGN
                        +" OR " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]  + " >= \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd  00:00:00") + "\" ) ";
//                    + " = " +PstEmployee.YES_RESIGN
            } else if (statusResign == PstEmployee.NO_RESIGN) {
                if (startPayPeriod != null && endPayPeriod!=null){
                    sql += " AND ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN 
                    +" OR " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]  + " >= \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd  00:00:00") + "\"  "
                    +" OR " + "((EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]  + " BETWEEN \"" + Formater.formatDate(startPayPeriod, "yyyy-MM-dd  00:00:00") + "\" AND \""+ Formater.formatDate(endPayPeriod, "yyyy-MM-dd  00:00:00") +"\" ) AND  (  \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd  00:00:00") + "\" <=  \""+ Formater.formatDate(endPayPeriod, "yyyy-MM-dd  00:00:00") +"\"  ) )) ";        
                } else {
                    sql += " AND ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN 
                    +" OR " + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]  + " >= \"" + Formater.formatDate(selectedDate, "yyyy-MM-dd  00:00:00") + "\" ) ";        
                }
                     
            }
            if (stsEmpCategorySel != null && stsEmpCategorySel.length() > 0) {
                stsEmpCategorySel = stsEmpCategorySel.substring(0, stsEmpCategorySel.length() - 1);
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + " IN  (" + stsEmpCategorySel + ")";
            }


            if (getEmployeePresence != null && getEmployeePresence.length() > 0 && stStatus != null && stStatus.size() > 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN (" + getEmployeePresence + ")";

            }
            //update by satrya 2012-9-28
            if (status1st != null && status1st.length() > 0) {
                status1st = status1st.substring(0, status1st.length() - 1);
                sql = sql + " AND " + " SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldName - 1]
                        + " IN (" + status1st + ")";
            }
            /*if(status2nd!=0){
             sql = sql + " AND " +" SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]
             + " = " + status2nd; 
             }*/
//                       if (limitStart == 0 && recordToGet == 0) {
//                sql = sql + "";
//            } else {
//                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
//            }
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            // System.out.println("countPresence Daily SQL = " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            // System.out.println(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                count = rs.getInt(1);

            }
            rs.close();
        } catch (Exception e) {
            System.out.println("[ERROR] " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return count;
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
                    System.out.println("next...................");
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

    /**
     * @return the departement
     */
    public long getDepartement() {
        return departement;
    }

    /**
     * @param departement the departement to set
     */
    public void setDepartement(long departement) {
        this.departement = departement;
    }

    /**
     * @return the section
     */
    public long getSection() {
        return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(long section) {
        this.section = section;
    }

    /**
     * @return the status1
     */
    public String getStatus1() {
        return status1;
    }

    /**
     * @param status1 the status1 to set
     */
    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the reasonSts
     */
    public int getReasonSts() {
        return reasonSts;
    }

    /**
     * @param reasonSts the reasonSts to set
     */
    public void setReasonSts(int reasonSts) {
        this.reasonSts = reasonSts;
    }

    /**
     * get difference of schedule and actual In presence
     *
     * @param dtSchedule => date schedule IN
     * @param dtActual => date actual IN
     * @return String of difference in Hour and Minutes format
     */
    public static String getDiffIn(Date dtParam, Date dtActual) {
        String result = "";
        if (dtParam == null || dtActual == null) {
            return result;
        }

        // utk mengecek jika waktu di schedule adalah jam 24:00 maka dianggap sebagai jam 00:00 keesokan harinya
        Date dtSchedule = new Date(dtParam.getYear(), dtParam.getMonth(), dtParam.getDate(), dtParam.getHours(), dtParam.getMinutes());
        if (dtSchedule.getHours() == 0 && dtSchedule.getMinutes() == 0) {
            dtSchedule = new Date(dtParam.getYear(), dtParam.getMonth(), dtParam.getDate() + 1, 0, 0);

        }

        if (dtSchedule != null && dtActual != null) {
            dtSchedule.setSeconds(0);
            dtActual.setSeconds(0);
            long iDuration = dtSchedule.getTime() / 60000 - dtActual.getTime() / 60000;
            long iDurationHour = (iDuration - (iDuration % 60)) / 60;
            long iDurationMin = iDuration % 60;
            if (!(iDurationHour == 0 && iDurationMin == 0)) {
                String strDurationHour = iDurationHour + "h, ";
                String strDurationMin = iDurationMin + "m";
                result = strDurationHour + strDurationMin;
            }
        }
        return result;
    }

    /**
     * get difference of schedule and actual Out presence
     *
     * @param dtSchedule => date schedule OUT
     * @param dtActual => date actual OUT
     * @return String of difference Out Hour and Minutes format
     */
    public static String getDiffOut(Date dtParam, Date dtActual) {
        String result = "";
        if (dtParam == null || dtActual == null) {
            return result;
        }
        //int schld1stCategory = PstEmpSchedule.getScheduleCategory(INT_FIRST_SCHEDULE, employeeId, presenceDate);
        //mencari schedule yg ada cross day
        Date dtSchedule = new Date(dtParam.getYear(), dtParam.getMonth(), dtParam.getDate(), dtParam.getHours(), dtParam.getMinutes());
        if (dtSchedule != null && dtActual != null) {
            dtSchedule.setSeconds(0);
            dtActual.setSeconds(0);
            long iDuration = dtActual.getTime() / 60000 - dtSchedule.getTime() / 60000;
            long iDurationHour = (iDuration - (iDuration % 60)) / 60;
            long iDurationMin = iDuration % 60;
            if (!(iDurationHour == 0 && iDurationMin == 0)) {
                String strDurationHour = iDurationHour + "h, ";
                String strDurationMin = iDurationMin + "m";
                result = strDurationHour + strDurationMin;
            }


        }
        return result;
    }

    /**
     * get working duration of actual In and Out presence
     *
     * @param dtActualIn => date actual IN
     * @param dtActualOut => date actual OUT
     * @return String of working duration in Hour and Minutes format
     */
    public static String getWorkingDuration(Date dtActualIn, Date dtActualOut, long breakLong) {
        String result = "";
        Date x = new Date();
        Date dt = new Date(breakLong);
        x = dt;
        if (dtActualIn != null && dtActualOut != null) {
            long iDurTimeIn = dtActualIn.getTime() / 1000;
            long iDurTimeOut = (dtActualOut.getTime() - breakLong) / 1000;
            long iDuration = 0;
            if (iDurTimeIn != iDurTimeOut) {
                iDuration = (iDurTimeIn == 0 || iDurTimeOut == 0) ? 0 : iDurTimeOut - iDurTimeIn;
            }
            long iDurationHour = (iDuration - (iDuration % 3600)) / 3600;
            long iDurationMin = iDuration % 3600 / 60;

            if (!(iDurationHour == 0 && iDurationMin == 0)) {
                String strDurationHour = (iDurationHour != 0) ? iDurationHour + "h, " : "";
                String strDurationMin = (iDurationMin != 0) ? iDurationMin + "m" : "";
                result = strDurationHour + strDurationMin;
            }
        }
        return result;
    }

    /**
     * @return the oidCompany
     */
    public long getOidCompany() {
        return oidCompany;
    }

    /**
     * @param oidCompany the oidCompany to set
     */
    public void setOidCompany(long oidCompany) {
        this.oidCompany = oidCompany;
    }

    /**
     * @return the oidDivision
     */
    public long getOidDivision() {
        return oidDivision;
    }

    /**
     * @param oidDivision the oidDivision to set
     */
    public void setOidDivision(long oidDivision) {
        this.oidDivision = oidDivision;
    }
}
