/*
 * SessCanteenVisitation.java
 * @author rusdianta
 * Created on January 27, 2005, 11:57 AM
 */
package com.dimata.harisma.session.canteen;

import java.util.*;
import java.sql.ResultSet;

import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.util.Formater;
import com.dimata.util.CalendarCalc;

import com.dimata.harisma.entity.canteen.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.search.*;

import com.dimata.harisma.entity.canteen.CanteenSchedule;
import com.dimata.harisma.entity.canteen.PstCanteenSchedule;
import com.dimata.harisma.utility.machine.Att2000;
import com.dimata.harisma.utility.machine.WatcherMachine;
import com.dimata.system.entity.PstSystemProperty;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.*;
//import java.sql.*;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;
//import java.sql.DriverManager;

public class SessCanteenVisitation {

    public SessCanteenVisitation() {
    }
    // ------------------------- START DETAIL REPORT ----------------------
    public static final String TBL_CANTEEN_DETAIL_REPORT = "hr_canteen_detail_report";//"HR_CANTEEN_DETAIL_REPORT";
    public static final String SESS_SRC_CANTEEN_VISITATION = "SESS_SRC_CANTEEN_VISITATION";
    public static final int REPORT_TYPE_DISPLAY_MORNING_AFTERNOON = 0;
    public static final int REPORT_TYPE_DISPLAY_NIGHT = 1;
    public static final String sTblCanteenDetailFieldNames[] = {
        "employee_payroll",
        "employee_name",
        "ma1",
        "ma2",
        "ma3",
        "ma4",
        "ma5",
        "ma6",
        "ma7",
        "ma8",
        "ma9",
        "ma10",
        "ma11",
        "ma12",
        "ma13",
        "ma14",
        "ma15",
        "ma16",
        "ma17",
        "ma18",
        "ma19",
        "ma20",
        "ma21",
        "ma22",
        "ma23",
        "ma24",
        "ma25",
        "ma26",
        "ma27",
        "ma28",
        "ma29",
        "ma30",
        "ma31",
        "n1",
        "n2",
        "n3",
        "n4",
        "n5",
        "n6",
        "n7",
        "n8",
        "n9",
        "n10",
        "n11",
        "n12",
        "n13",
        "n14",
        "n15",
        "n16",
        "n17",
        "n18",
        "n19",
        "n20",
        "n21",
        "n22",
        "n23",
        "n24",
        "n25",
        "n26",
        "n27",
        "n28",
        "n29",
        "n30",
        "n31"
    };

    /**
     * @param lDepartmentOid
     * @param lSectionOid
     * @param dSelectedDate
     * @return
     */
    public Vector getDetailVisitationReportDaily(long lDepartmentOid, long lSectionOid, Date dSelectedDate, int rptType) {

        Vector vResult = new Vector(1, 1);
        DBResultSet dbrs = null;

        try {

            String strSelectedDate = Formater.formatDate(dSelectedDate, "yyyy-MM-dd");

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                    + ", CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION]
                    + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION + " AS CAN "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + " = " + lDepartmentOid;

            if (lSectionOid != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + lSectionOid;
            }

            Vector vOfWhClause = new Vector();

            if (rptType == REPORT_TYPE_DISPLAY_MORNING_AFTERNOON) {
                //         System.out.println("--> 1");
                vOfWhClause = genVOfWhClsBaseOnCanSchd("CAN", dSelectedDate);
            } else {
                //vOfWhClause = genVOfWhClsWithoutCanSchd("CAN", dSelectedDate);
                if (rptType == REPORT_TYPE_DISPLAY_NIGHT) {
                    //                System.out.println("--> 2");
                    vOfWhClause = genVOfWhClsWithoutCanSchd("CAN", dSelectedDate);
                } else {
                    //                System.out.println("--> 3");
                    String theDate = com.dimata.util.Formater.formatDate(dSelectedDate, "yyyy-MM-dd");
                    String strWhereTime = "DATE_FORMAT(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                            + ",\"%Y-%m-%d\")=\"" + theDate + "\"";
                    vOfWhClause.add(strWhereTime);
                }

            }

            if (vOfWhClause != null && vOfWhClause.size() > 0) {
                String sTemp = "";
                int iWhClauseCount = vOfWhClause.size();
                for (int i = 0; i < vOfWhClause.size(); i++) {
                    sTemp = sTemp + vOfWhClause.get(i) + " OR ";
                }

                if (sTemp != null && sTemp.length() > 3) {
                    sTemp = sTemp.substring(0, sTemp.length() - 3);
                    sql = sql + " AND (" + sTemp + ")";
                }
            }


            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", " + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            DetailDailyVisitation dDailyVisitation = new DetailDailyVisitation();
            String visitTimes = "";
            String numPayroll = "";
            int visitQty = 0;
            boolean status = false;

            while (rs.next()) {
                status = true;

                String strEmpPayrollNum = rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
                String strEmpName = rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                String strVisitationTime = String.valueOf(rs.getTime(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]));
                int iNumOfVisitation = rs.getInt(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION]);

                if (numPayroll.equals(strEmpPayrollNum)) {
                    visitQty = visitQty + iNumOfVisitation;
                    visitTimes = visitTimes + " / " + strVisitationTime;
                } else {
                    if (dDailyVisitation.getEmployeePayroll() == "") {
                        dDailyVisitation.setEmployeePayroll(strEmpPayrollNum);
                        dDailyVisitation.setEmployeeName(strEmpName);
                        dDailyVisitation.setScheduleSymbol(getEmpDailyScheduleSymbol(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), dSelectedDate));
                        visitTimes = strVisitationTime;
                        visitQty = iNumOfVisitation;
                    } else {
                        dDailyVisitation.setStrVisitTimes(visitTimes);
                        dDailyVisitation.setNumVisits(visitQty);
                        vResult.add(dDailyVisitation);

                        dDailyVisitation = new DetailDailyVisitation();
                        dDailyVisitation.setEmployeePayroll(strEmpPayrollNum);
                        dDailyVisitation.setEmployeeName(strEmpName);
                        dDailyVisitation.setScheduleSymbol(getEmpDailyScheduleSymbol(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), dSelectedDate));
                        visitTimes = strVisitationTime;
                        visitQty = iNumOfVisitation;
                    }
                }
                numPayroll = dDailyVisitation.getEmployeePayroll();
            }

            if (status) {
                dDailyVisitation.setStrVisitTimes(visitTimes);
                dDailyVisitation.setNumVisits(visitQty);
                vResult.add(dDailyVisitation);
            }
        } catch (Exception e) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getDetailDailyVisitation() exc : " + e.toString());
        }
        return vResult;
    }

    /**
     * @Author  Roy Andika
     * @Desc    Untuk mendapatkan list canteen visitation detail
     * @param   lDepartmentOid
     * @param   lSectionOid
     * @param   dSelectedDate
     * @return
     */
    public Vector getDetailVisitationReportDaily(long lDepartmentOid, long lSectionOid, Date dSelectedDate, String canteenScheduleId[], int rptType) {

        Vector canteenSchedule = new Vector();
        String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE] + " ASC ";

        try {
            canteenSchedule = PstCanteenSchedule.list(0, 0, null, order);
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        Vector vResult = new Vector(1, 1);
        DBResultSet dbrs = null;

        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                    + ", CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION]
                    + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION + " AS CAN "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + lDepartmentOid;

            if (lSectionOid != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + lSectionOid;
            }

            String where = "";

            if (canteenScheduleId.length > 0) {

                int mak = 0;

                for (int i = 0; i < canteenSchedule.size(); i++) {

                    CanteenSchedule canteenSch = new CanteenSchedule();

                    canteenSch = (CanteenSchedule) canteenSchedule.get(i);

                    if (canteenScheduleId[i].compareTo("1") == 0) {

                        if (where.length() <= 0) {

                            where = where + " ( CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN "
                                    + " '" + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSch.getTTimeOpen(), "HH:mm:ss") + "' AND '"
                                    + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " "
                                    + Formater.formatDate(canteenSch.getTTimeClose(), "HH:mm:ss") + "' ) ";

                        } else {

                            where = where + " OR ( CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN "
                                    + " '" + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSch.getTTimeOpen(), "HH:mm:ss") + "' AND '"
                                    + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " "
                                    + Formater.formatDate(canteenSch.getTTimeClose(), "HH:mm:ss") + "' ) ";
                        }

                    }

                    mak++;

                }

                if (canteenScheduleId[mak].compareTo("1") == 0) {

                    String whereOutSchedule = timeOutSchedule("CAN", dSelectedDate);

                    if (where.length() > 0) {

                        where = where + " OR " + whereOutSchedule;

                    }

                    
                    else {

                        where = where + whereOutSchedule;

                    }
                }
             //tgl 24 appril 2012 by satrya

            }
            if (where == ""){
                        where =  where + " CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN "
                        + " '" + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " 00:00:00' AND '"+ Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " 23:59:59' ";

            }

            //else{
               //  where = where + " CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN "
                       // + " '" + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " 00:00:00' AND '"+ Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " 23:59:59' ";

           // }
            //tgl 23 april by satrya
            //if(where ==""){
               // sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                 //+ ", " + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME];
            
            //}else{
            
            sql = sql + " AND ( " + where + " ) ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", " + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME];
            //}

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            DetailDailyVisitation dDailyVisitation = new DetailDailyVisitation();
            String visitTimes = "";
            String numPayroll = "";
            int visitQty = 0;
            boolean status = false;

            while (rs.next()) {
                status = true;

                String strEmpPayrollNum = rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
                String strEmpName = rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                String strVisitationTime = String.valueOf(rs.getTime(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]));
                int iNumOfVisitation = rs.getInt(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION]);

                if (numPayroll.equals(strEmpPayrollNum)) {
                    visitQty = visitQty + iNumOfVisitation;
                    visitTimes = visitTimes + " / " + strVisitationTime;
                } else {
                    if (dDailyVisitation.getEmployeePayroll() == "") {
                        dDailyVisitation.setEmployeePayroll(strEmpPayrollNum);
                        dDailyVisitation.setEmployeeName(strEmpName);
                        dDailyVisitation.setScheduleSymbol(getEmpDailyScheduleSymbol(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), dSelectedDate));
                        visitTimes = strVisitationTime;
                        visitQty = iNumOfVisitation;
                    } else {
                        dDailyVisitation.setStrVisitTimes(visitTimes);
                        dDailyVisitation.setNumVisits(visitQty);
                        vResult.add(dDailyVisitation);

                        dDailyVisitation = new DetailDailyVisitation();
                        dDailyVisitation.setEmployeePayroll(strEmpPayrollNum);
                        dDailyVisitation.setEmployeeName(strEmpName);
                        dDailyVisitation.setScheduleSymbol(getEmpDailyScheduleSymbol(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), dSelectedDate));
                        visitTimes = strVisitationTime;
                        visitQty = iNumOfVisitation;
                    }
                }
                numPayroll = dDailyVisitation.getEmployeePayroll();
            }

            if (status) {
                dDailyVisitation.setStrVisitTimes(visitTimes);
                dDailyVisitation.setNumVisits(visitQty);
                vResult.add(dDailyVisitation);
            }
        } catch (Exception e) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getDetailDailyVisitation() exc : " + e.toString());
        }
        return vResult;
    }

    /**
     * @param lDepartmentOid
     * @param lSectionOid
     * @param dSelectedDate
     * @return
     */
    public Vector getDetailVisitationReportWeekly(long lDepartmentOid, long lSectionOid, Date dSelectedDate, int iCalendarType, int iWeekIndex, int rptType) {
        Vector vResult = new Vector(1, 1);

        CalendarCalc objCalendarCalc = new CalendarCalc(iCalendarType);
        Date dStartSelectedDate = objCalendarCalc.getStartDateOfTheWeek(dSelectedDate, iWeekIndex);
        Date dEndSelectedDate = objCalendarCalc.getEndDateOfTheWeek(dSelectedDate, iWeekIndex);

        try {
            vResult = this.getDetailVisitationReport(lDepartmentOid, lSectionOid, dStartSelectedDate, dEndSelectedDate, rptType);

        } catch (Exception error) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getMonthlyDetailVisitationReport() exc : " + error.toString());
        } finally {
            return vResult;
        }
    }

    /**
     *@Author   Roy Andika
     * @param   lDepartmentOid
     * @param   lSectionOid
     * @param   dSelectedDate
     * @param   iCalendarType
     * @param   iWeekIndex
     * @param   rptType
     * @return
     */
    public Vector getDetailVisitationReportWeekly(long lDepartmentOid, long lSectionOid, Date dSelectedDate, int iCalendarType, int iWeekIndex, String canteenScheduleId[]) {

        Vector vResult = new Vector(1, 1);

        CalendarCalc objCalendarCalc = new CalendarCalc(iCalendarType);
        Date dStartSelectedDate = objCalendarCalc.getStartDateOfTheWeek(dSelectedDate, iWeekIndex);
        Date dEndSelectedDate = objCalendarCalc.getEndDateOfTheWeek(dSelectedDate, iWeekIndex);

        try {

            vResult = this.getDetailVisitationReport(lDepartmentOid, lSectionOid, dStartSelectedDate, dEndSelectedDate, canteenScheduleId);

        } catch (Exception error) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getMonthlyDetailVisitationReport() exc : " + error.toString());
        } finally {
            return vResult;
        }
    }

    /**
     * @param lDepartmentOid
     * @param lSectionOid
     * @param dSelectedDate
     * @return
     */
    public Vector getDetailVisitationReportMonthly(long lDepartmentOid, long lSectionOid, Date dSelectedDate, int reType) {
        Vector vResult = new Vector(1, 1);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dSelectedDate);
        Date dStartSelectedDate = new Date(dSelectedDate.getYear(), dSelectedDate.getMonth(), 1);
        Date dEndSelectedDate = new Date(dSelectedDate.getYear(), dSelectedDate.getMonth(), cal.getActualMaximum(cal.DAY_OF_MONTH));

        try {
            vResult = getDetailVisitationReport(lDepartmentOid, lSectionOid, dStartSelectedDate, dEndSelectedDate, reType);

        } catch (Exception error) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getMonthlyDetailVisitationReport() exc : " + error.toString());
        } finally {
            return vResult;
        }
    }

    /**
     * @Author  Roy Andika
     * @param   lDepartmentOid
     * @param   lSectionOid
     * @param   dSelectedDate
     * @param   reType
     * @Desc    Untuk mendapatkan report visitation bulanan
     * @return
     */
    public synchronized Vector getDetailVisitationReportMonthly(long lDepartmentOid, long lSectionOid, Date dSelectedDate, String canteenScheduleId[]) {

        Vector vResult = new Vector(1, 1);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dSelectedDate);
        Date dStartSelectedDate = new Date(dSelectedDate.getYear(), dSelectedDate.getMonth(), 1);
        Date dEndSelectedDate = new Date(dSelectedDate.getYear(), dSelectedDate.getMonth(), cal.getActualMaximum(cal.DAY_OF_MONTH));

        try {

            vResult = getDetailVisitationReport(lDepartmentOid, lSectionOid, dStartSelectedDate, dEndSelectedDate, canteenScheduleId);

        } catch (Exception error) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getMonthlyDetailVisitationReport() exc : " + error.toString());
        } finally {
            return vResult;
        }
    }

    public Vector getDetailVisitationReportMonthly(long[] lDepartmentOids, Date dSelectedDate, int reType) {

        Vector vResult = new Vector(1, 1);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dSelectedDate);
        Date dStartSelectedDate = new Date(dSelectedDate.getYear(), dSelectedDate.getMonth(), 1);
        Date dEndSelectedDate = new Date(dSelectedDate.getYear(), dSelectedDate.getMonth(), cal.getActualMaximum(cal.DAY_OF_MONTH));

        try {
            vResult = getDetailVisitationReport(lDepartmentOids, dStartSelectedDate, dEndSelectedDate, reType);

        } catch (Exception error) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getMonthlyDetailVisitationReport() exc : " + error.toString());
        } finally {
            return vResult;
        }

    }

    /**
     * @Author  Roy Andika
     * @param   lDepartmentOids
     * @param   dSelectedDate
     * @param   canteenScheduleId
     * @Desc    Untuk mendapatkan list summary report bulanan
     * @return
     */
    public Vector getDetailVisitationReportMonthly(long[] lDepartmentOids, Date dSelectedDate, String canteenScheduleId[]) {
        Vector vResult = new Vector(1, 1);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dSelectedDate);
        Date dStartSelectedDate = new Date(dSelectedDate.getYear(), dSelectedDate.getMonth(), 1);
        Date dEndSelectedDate = new Date(dSelectedDate.getYear(), dSelectedDate.getMonth(), cal.getActualMaximum(cal.DAY_OF_MONTH));

        try {

            vResult = getDetailVisitationReport(lDepartmentOids, dStartSelectedDate, dEndSelectedDate, canteenScheduleId);

        } catch (Exception error) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getMonthlyDetailVisitationReport() exc : " + error.toString());
        } finally {
            return vResult;
        }
    }

    /**
     * @param dStartSelectedDate 
     * @param dEndSelectedDate
     * @param lDepartmentOid
     * @param lSectionOid
     * @return
     */
    public Vector getDetailVisitationReport(long lDepartmentOid, long lSectionOid, Date dStartSelectedDate, Date dEndSelectedDate, int rptType) {

        Vector vResult = new Vector(1, 1);
        DBResultSet dbrs = null;

        try {

            // pastikan table temporary untuk report detail dalam keadaan kosong
            int iDeleteStatus = deleteDetailReportTemporaryData();

            // insert data visitation yang sesuai dengan canteen schedule (Morning + Afternoon)
            getDetailMealsMorningAfternoon(lDepartmentOid, lSectionOid, dStartSelectedDate, dEndSelectedDate, rptType);

            // lakukan iterasi untuk mengambil jumlah visitation per harinya dari table temporary
            String sSQL = "SELECT DISTINCT " + sTblCanteenDetailFieldNames[0] + ", " + sTblCanteenDetailFieldNames[1];
            for (int i = 0; i < 62; i++) {
                sSQL = sSQL + ", SUM(" + sTblCanteenDetailFieldNames[2 + i] + ")";
            }
            sSQL = sSQL + " FROM " + TBL_CANTEEN_DETAIL_REPORT + " GROUP BY " + sTblCanteenDetailFieldNames[0] + ", " + sTblCanteenDetailFieldNames[1];

            dbrs = DBHandler.execQueryResult(sSQL);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                MonthlyDetailVisitation monthlyDetailVisitation = new MonthlyDetailVisitation();

                monthlyDetailVisitation.setEmployeePayroll(rs.getString(1));
                monthlyDetailVisitation.setEmployeeName(rs.getString(2));
                monthlyDetailVisitation.setMa1(rs.getInt(3));
                monthlyDetailVisitation.setMa2(rs.getInt(4));
                monthlyDetailVisitation.setMa3(rs.getInt(5));
                monthlyDetailVisitation.setMa4(rs.getInt(6));
                monthlyDetailVisitation.setMa5(rs.getInt(7));
                monthlyDetailVisitation.setMa6(rs.getInt(8));
                monthlyDetailVisitation.setMa7(rs.getInt(9));
                monthlyDetailVisitation.setMa8(rs.getInt(10));
                monthlyDetailVisitation.setMa9(rs.getInt(11));
                monthlyDetailVisitation.setMa10(rs.getInt(12));
                monthlyDetailVisitation.setMa11(rs.getInt(13));
                monthlyDetailVisitation.setMa12(rs.getInt(14));
                monthlyDetailVisitation.setMa13(rs.getInt(15));
                monthlyDetailVisitation.setMa14(rs.getInt(16));
                monthlyDetailVisitation.setMa15(rs.getInt(17));
                monthlyDetailVisitation.setMa16(rs.getInt(18));
                monthlyDetailVisitation.setMa17(rs.getInt(19));
                monthlyDetailVisitation.setMa18(rs.getInt(20));
                monthlyDetailVisitation.setMa19(rs.getInt(21));
                monthlyDetailVisitation.setMa20(rs.getInt(22));
                monthlyDetailVisitation.setMa21(rs.getInt(23));
                monthlyDetailVisitation.setMa22(rs.getInt(24));
                monthlyDetailVisitation.setMa23(rs.getInt(25));
                monthlyDetailVisitation.setMa24(rs.getInt(26));
                monthlyDetailVisitation.setMa25(rs.getInt(27));
                monthlyDetailVisitation.setMa26(rs.getInt(28));
                monthlyDetailVisitation.setMa27(rs.getInt(29));
                monthlyDetailVisitation.setMa28(rs.getInt(30));
                monthlyDetailVisitation.setMa29(rs.getInt(31));
                monthlyDetailVisitation.setMa30(rs.getInt(32));
                monthlyDetailVisitation.setMa31(rs.getInt(33));
                monthlyDetailVisitation.setN1(rs.getInt(34));
                monthlyDetailVisitation.setN2(rs.getInt(35));
                monthlyDetailVisitation.setN3(rs.getInt(36));
                monthlyDetailVisitation.setN4(rs.getInt(37));
                monthlyDetailVisitation.setN5(rs.getInt(38));
                monthlyDetailVisitation.setN6(rs.getInt(39));
                monthlyDetailVisitation.setN7(rs.getInt(40));
                monthlyDetailVisitation.setN8(rs.getInt(41));
                monthlyDetailVisitation.setN9(rs.getInt(42));
                monthlyDetailVisitation.setN10(rs.getInt(43));
                monthlyDetailVisitation.setN11(rs.getInt(44));
                monthlyDetailVisitation.setN12(rs.getInt(45));
                monthlyDetailVisitation.setN13(rs.getInt(46));
                monthlyDetailVisitation.setN14(rs.getInt(47));
                monthlyDetailVisitation.setN15(rs.getInt(48));
                monthlyDetailVisitation.setN16(rs.getInt(49));
                monthlyDetailVisitation.setN17(rs.getInt(50));
                monthlyDetailVisitation.setN18(rs.getInt(51));
                monthlyDetailVisitation.setN19(rs.getInt(52));
                monthlyDetailVisitation.setN20(rs.getInt(53));
                monthlyDetailVisitation.setN21(rs.getInt(54));
                monthlyDetailVisitation.setN22(rs.getInt(55));
                monthlyDetailVisitation.setN23(rs.getInt(56));
                monthlyDetailVisitation.setN24(rs.getInt(57));
                monthlyDetailVisitation.setN25(rs.getInt(58));
                monthlyDetailVisitation.setN26(rs.getInt(59));
                monthlyDetailVisitation.setN27(rs.getInt(60));
                monthlyDetailVisitation.setN28(rs.getInt(61));
                monthlyDetailVisitation.setN29(rs.getInt(62));
                monthlyDetailVisitation.setN30(rs.getInt(63));
                monthlyDetailVisitation.setN31(rs.getInt(64));
                monthlyDetailVisitation.setMaTotal();
                monthlyDetailVisitation.setNTotal();

                vResult.add(monthlyDetailVisitation);
            }

            // pastikan table temporary untuk report detail dalam keadaan kosong
            int iDeleteStatusLast = deleteDetailReportTemporaryData();
        } catch (Exception error) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getDetailVisitationReport() exc : " + error.toString());
        } finally {
            DBResultSet.close(dbrs);
            return vResult;
        }
    }

    /**
     * @Author  Roy Andika
     * @param   lDepartmentOid
     * @param   lSectionOid
     * @param   dStartSelectedDate
     * @param   dEndSelectedDate
     * @param   canteenScheduleId
     * @return
     */
    public synchronized Vector getDetailVisitationReport(long lDepartmentOid, long lSectionOid, Date dStartSelectedDate, Date dEndSelectedDate, String canteenScheduleId[]) {

        Vector vResult = new Vector(1, 1);
        DBResultSet dbrs = null;

        try {

            // pastikan table temporary untuk report detail dalam keadaan kosong            
            deleteDetailReportTemporary();

            // insert data visitation yang sesuai dengan canteen schedule (Morning + Afternoon)
            getDetailMealsMorningAfternoon(lDepartmentOid, lSectionOid, dStartSelectedDate, dEndSelectedDate, canteenScheduleId);

            // lakukan iterasi untuk mengambil jumlah visitation per harinya dari table temporary
            String sSQL = "SELECT DISTINCT " + sTblCanteenDetailFieldNames[0] + ", " + sTblCanteenDetailFieldNames[1];
            for (int i = 0; i < 62; i++) {
                sSQL = sSQL + ", SUM(" + sTblCanteenDetailFieldNames[2 + i] + ")";
            }
            sSQL = sSQL + " FROM " + TBL_CANTEEN_DETAIL_REPORT + " GROUP BY " + sTblCanteenDetailFieldNames[0] + ", " + sTblCanteenDetailFieldNames[1];

            dbrs = DBHandler.execQueryResult(sSQL);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                MonthlyDetailVisitation monthlyDetailVisitation = new MonthlyDetailVisitation();

                monthlyDetailVisitation.setEmployeePayroll(rs.getString(1));
                monthlyDetailVisitation.setEmployeeName(rs.getString(2));
                monthlyDetailVisitation.setMa1(rs.getInt(3));
                monthlyDetailVisitation.setMa2(rs.getInt(4));
                monthlyDetailVisitation.setMa3(rs.getInt(5));
                monthlyDetailVisitation.setMa4(rs.getInt(6));
                monthlyDetailVisitation.setMa5(rs.getInt(7));
                monthlyDetailVisitation.setMa6(rs.getInt(8));
                monthlyDetailVisitation.setMa7(rs.getInt(9));
                monthlyDetailVisitation.setMa8(rs.getInt(10));
                monthlyDetailVisitation.setMa9(rs.getInt(11));
                monthlyDetailVisitation.setMa10(rs.getInt(12));
                monthlyDetailVisitation.setMa11(rs.getInt(13));
                monthlyDetailVisitation.setMa12(rs.getInt(14));
                monthlyDetailVisitation.setMa13(rs.getInt(15));
                monthlyDetailVisitation.setMa14(rs.getInt(16));
                monthlyDetailVisitation.setMa15(rs.getInt(17));
                monthlyDetailVisitation.setMa16(rs.getInt(18));
                monthlyDetailVisitation.setMa17(rs.getInt(19));
                monthlyDetailVisitation.setMa18(rs.getInt(20));
                monthlyDetailVisitation.setMa19(rs.getInt(21));
                monthlyDetailVisitation.setMa20(rs.getInt(22));
                monthlyDetailVisitation.setMa21(rs.getInt(23));
                monthlyDetailVisitation.setMa22(rs.getInt(24));
                monthlyDetailVisitation.setMa23(rs.getInt(25));
                monthlyDetailVisitation.setMa24(rs.getInt(26));
                monthlyDetailVisitation.setMa25(rs.getInt(27));
                monthlyDetailVisitation.setMa26(rs.getInt(28));
                monthlyDetailVisitation.setMa27(rs.getInt(29));
                monthlyDetailVisitation.setMa28(rs.getInt(30));
                monthlyDetailVisitation.setMa29(rs.getInt(31));
                monthlyDetailVisitation.setMa30(rs.getInt(32));
                monthlyDetailVisitation.setMa31(rs.getInt(33));
                monthlyDetailVisitation.setN1(rs.getInt(34));
                monthlyDetailVisitation.setN2(rs.getInt(35));
                monthlyDetailVisitation.setN3(rs.getInt(36));
                monthlyDetailVisitation.setN4(rs.getInt(37));
                monthlyDetailVisitation.setN5(rs.getInt(38));
                monthlyDetailVisitation.setN6(rs.getInt(39));
                monthlyDetailVisitation.setN7(rs.getInt(40));
                monthlyDetailVisitation.setN8(rs.getInt(41));
                monthlyDetailVisitation.setN9(rs.getInt(42));
                monthlyDetailVisitation.setN10(rs.getInt(43));
                monthlyDetailVisitation.setN11(rs.getInt(44));
                monthlyDetailVisitation.setN12(rs.getInt(45));
                monthlyDetailVisitation.setN13(rs.getInt(46));
                monthlyDetailVisitation.setN14(rs.getInt(47));
                monthlyDetailVisitation.setN15(rs.getInt(48));
                monthlyDetailVisitation.setN16(rs.getInt(49));
                monthlyDetailVisitation.setN17(rs.getInt(50));
                monthlyDetailVisitation.setN18(rs.getInt(51));
                monthlyDetailVisitation.setN19(rs.getInt(52));
                monthlyDetailVisitation.setN20(rs.getInt(53));
                monthlyDetailVisitation.setN21(rs.getInt(54));
                monthlyDetailVisitation.setN22(rs.getInt(55));
                monthlyDetailVisitation.setN23(rs.getInt(56));
                monthlyDetailVisitation.setN24(rs.getInt(57));
                monthlyDetailVisitation.setN25(rs.getInt(58));
                monthlyDetailVisitation.setN26(rs.getInt(59));
                monthlyDetailVisitation.setN27(rs.getInt(60));
                monthlyDetailVisitation.setN28(rs.getInt(61));
                monthlyDetailVisitation.setN29(rs.getInt(62));
                monthlyDetailVisitation.setN30(rs.getInt(63));
                monthlyDetailVisitation.setN31(rs.getInt(64));
                monthlyDetailVisitation.setMaTotal();
                monthlyDetailVisitation.setNTotal();

                vResult.add(monthlyDetailVisitation);
            }

            // pastikan table temporary untuk report detail dalam keadaan kosong
            int iDeleteStatusLast = deleteDetailReportTemporaryData();
        } catch (Exception error) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getDetailVisitationReport() exc : " + error.toString());
        } finally {
            DBResultSet.close(dbrs);
            return vResult;
        }
    }

    public synchronized Vector getDetailVisitationReport(long[] lDepartmentOids, Date dStartSelectedDate, Date dEndSelectedDate, int rptType) {
        Vector allResults = new Vector(1, 1);
        Vector vResult = new Vector(1, 1);
        DBResultSet dbrs = null;
        int totalVisits = 0;

        try {

            for (int j = 0; j < lDepartmentOids.length; j++) {
                // pastikan table temporary untuk report detail dalam keadaan kosong
                int iDeleteStatus = deleteDetailReportTemporaryData();

                // insert data visitation yang sesuai dengan canteen schedule (Morning + Afternoon)
                getDetailMealsMorningAfternoon(lDepartmentOids[j], 0, dStartSelectedDate, dEndSelectedDate, rptType);

                // lakukan iterasi untuk mengambil jumlah visitation per harinya dari table temporary
                String sSQL = "SELECT DISTINCT " + sTblCanteenDetailFieldNames[0] + ", " + sTblCanteenDetailFieldNames[1];
                for (int i = 0; i < 62; i++) {
                    sSQL = sSQL + ", SUM(" + sTblCanteenDetailFieldNames[2 + i] + ")";
                }
                sSQL = sSQL + " FROM " + TBL_CANTEEN_DETAIL_REPORT + " GROUP BY " + sTblCanteenDetailFieldNames[0] + ", " + sTblCanteenDetailFieldNames[1];

                dbrs = DBHandler.execQueryResult(sSQL);
                ResultSet rs = dbrs.getResultSet();

                vResult = new Vector(1, 1);

                while (rs.next()) {
                    MonthlyDetailVisitation monthlyDetailVisitation = new MonthlyDetailVisitation();

                    monthlyDetailVisitation.setEmployeePayroll(rs.getString(1));
                    monthlyDetailVisitation.setEmployeeName(rs.getString(2));
                    monthlyDetailVisitation.setMa1(rs.getInt(3));
                    monthlyDetailVisitation.setMa2(rs.getInt(4));
                    monthlyDetailVisitation.setMa3(rs.getInt(5));
                    monthlyDetailVisitation.setMa4(rs.getInt(6));
                    monthlyDetailVisitation.setMa5(rs.getInt(7));
                    monthlyDetailVisitation.setMa6(rs.getInt(8));
                    monthlyDetailVisitation.setMa7(rs.getInt(9));
                    monthlyDetailVisitation.setMa8(rs.getInt(10));
                    monthlyDetailVisitation.setMa9(rs.getInt(11));
                    monthlyDetailVisitation.setMa10(rs.getInt(12));
                    monthlyDetailVisitation.setMa11(rs.getInt(13));
                    monthlyDetailVisitation.setMa12(rs.getInt(14));
                    monthlyDetailVisitation.setMa13(rs.getInt(15));
                    monthlyDetailVisitation.setMa14(rs.getInt(16));
                    monthlyDetailVisitation.setMa15(rs.getInt(17));
                    monthlyDetailVisitation.setMa16(rs.getInt(18));
                    monthlyDetailVisitation.setMa17(rs.getInt(19));
                    monthlyDetailVisitation.setMa18(rs.getInt(20));
                    monthlyDetailVisitation.setMa19(rs.getInt(21));
                    monthlyDetailVisitation.setMa20(rs.getInt(22));
                    monthlyDetailVisitation.setMa21(rs.getInt(23));
                    monthlyDetailVisitation.setMa22(rs.getInt(24));
                    monthlyDetailVisitation.setMa23(rs.getInt(25));
                    monthlyDetailVisitation.setMa24(rs.getInt(26));
                    monthlyDetailVisitation.setMa25(rs.getInt(27));
                    monthlyDetailVisitation.setMa26(rs.getInt(28));
                    monthlyDetailVisitation.setMa27(rs.getInt(29));
                    monthlyDetailVisitation.setMa28(rs.getInt(30));
                    monthlyDetailVisitation.setMa29(rs.getInt(31));
                    monthlyDetailVisitation.setMa30(rs.getInt(32));
                    monthlyDetailVisitation.setMa31(rs.getInt(33));
                    monthlyDetailVisitation.setN1(rs.getInt(34));
                    monthlyDetailVisitation.setN2(rs.getInt(35));
                    monthlyDetailVisitation.setN3(rs.getInt(36));
                    monthlyDetailVisitation.setN4(rs.getInt(37));
                    monthlyDetailVisitation.setN5(rs.getInt(38));
                    monthlyDetailVisitation.setN6(rs.getInt(39));
                    monthlyDetailVisitation.setN7(rs.getInt(40));
                    monthlyDetailVisitation.setN8(rs.getInt(41));
                    monthlyDetailVisitation.setN9(rs.getInt(42));
                    monthlyDetailVisitation.setN10(rs.getInt(43));
                    monthlyDetailVisitation.setN11(rs.getInt(44));
                    monthlyDetailVisitation.setN12(rs.getInt(45));
                    monthlyDetailVisitation.setN13(rs.getInt(46));
                    monthlyDetailVisitation.setN14(rs.getInt(47));
                    monthlyDetailVisitation.setN15(rs.getInt(48));
                    monthlyDetailVisitation.setN16(rs.getInt(49));
                    monthlyDetailVisitation.setN17(rs.getInt(50));
                    monthlyDetailVisitation.setN18(rs.getInt(51));
                    monthlyDetailVisitation.setN19(rs.getInt(52));
                    monthlyDetailVisitation.setN20(rs.getInt(53));
                    monthlyDetailVisitation.setN21(rs.getInt(54));
                    monthlyDetailVisitation.setN22(rs.getInt(55));
                    monthlyDetailVisitation.setN23(rs.getInt(56));
                    monthlyDetailVisitation.setN24(rs.getInt(57));
                    monthlyDetailVisitation.setN25(rs.getInt(58));
                    monthlyDetailVisitation.setN26(rs.getInt(59));
                    monthlyDetailVisitation.setN27(rs.getInt(60));
                    monthlyDetailVisitation.setN28(rs.getInt(61));
                    monthlyDetailVisitation.setN29(rs.getInt(62));
                    monthlyDetailVisitation.setN30(rs.getInt(63));
                    monthlyDetailVisitation.setN31(rs.getInt(64));
                    monthlyDetailVisitation.setMaTotal();
                    monthlyDetailVisitation.setNTotal();

                    vResult.add(monthlyDetailVisitation);

                }

                allResults.add(vResult);

                // pastikan table temporary untuk report detail dalam keadaan kosong
                int iDeleteStatusLast = deleteDetailReportTemporaryData();
            }

        } catch (Exception error) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getDetailVisitationReport() exc : " + error.toString());
        } finally {
            DBResultSet.close(dbrs);
            return allResults;
        }
    }

    /**
     * @Author  Roy Andika
     * @param   lDepartmentOids
     * @param   dStartSelectedDate
     * @param   dEndSelectedDate
     * @param   canteenScheduleId
     * @return
     */
    public synchronized Vector getDetailVisitationReport(long[] lDepartmentOids, Date dStartSelectedDate, Date dEndSelectedDate, String canteenScheduleId[]) {

        Vector allResults = new Vector(1, 1);
        Vector vResult = new Vector(1, 1);
        DBResultSet dbrs = null;

        try {

            for (int j = 0; j < lDepartmentOids.length; j++) {

                // pastikan table temporary untuk report detail dalam keadaan kosong
                int iDeleteStatus = deleteDetailReportTemporaryData();

                // insert data visitation yang sesuai dengan canteen schedule (Morning + Afternoon)
                getDetailMealsMorningAfternoon(lDepartmentOids[j], 0, dStartSelectedDate, dEndSelectedDate, canteenScheduleId);

                // lakukan iterasi untuk mengambil jumlah visitation per harinya dari table temporary
                String sSQL = "SELECT DISTINCT " + sTblCanteenDetailFieldNames[0] + ", " + sTblCanteenDetailFieldNames[1];
                for (int i = 0; i < 62; i++) {
                    sSQL = sSQL + ", SUM(" + sTblCanteenDetailFieldNames[2 + i] + ")";
                }
                sSQL = sSQL + " FROM " + TBL_CANTEEN_DETAIL_REPORT + " GROUP BY " + sTblCanteenDetailFieldNames[0] + ", " + sTblCanteenDetailFieldNames[1];

                dbrs = DBHandler.execQueryResult(sSQL);
                ResultSet rs = dbrs.getResultSet();

                vResult = new Vector(1, 1);

                while (rs.next()) {
                    MonthlyDetailVisitation monthlyDetailVisitation = new MonthlyDetailVisitation();

                    monthlyDetailVisitation.setEmployeePayroll(rs.getString(1));
                    monthlyDetailVisitation.setEmployeeName(rs.getString(2));
                    monthlyDetailVisitation.setMa1(rs.getInt(3));
                    monthlyDetailVisitation.setMa2(rs.getInt(4));
                    monthlyDetailVisitation.setMa3(rs.getInt(5));
                    monthlyDetailVisitation.setMa4(rs.getInt(6));
                    monthlyDetailVisitation.setMa5(rs.getInt(7));
                    monthlyDetailVisitation.setMa6(rs.getInt(8));
                    monthlyDetailVisitation.setMa7(rs.getInt(9));
                    monthlyDetailVisitation.setMa8(rs.getInt(10));
                    monthlyDetailVisitation.setMa9(rs.getInt(11));
                    monthlyDetailVisitation.setMa10(rs.getInt(12));
                    monthlyDetailVisitation.setMa11(rs.getInt(13));
                    monthlyDetailVisitation.setMa12(rs.getInt(14));
                    monthlyDetailVisitation.setMa13(rs.getInt(15));
                    monthlyDetailVisitation.setMa14(rs.getInt(16));
                    monthlyDetailVisitation.setMa15(rs.getInt(17));
                    monthlyDetailVisitation.setMa16(rs.getInt(18));
                    monthlyDetailVisitation.setMa17(rs.getInt(19));
                    monthlyDetailVisitation.setMa18(rs.getInt(20));
                    monthlyDetailVisitation.setMa19(rs.getInt(21));
                    monthlyDetailVisitation.setMa20(rs.getInt(22));
                    monthlyDetailVisitation.setMa21(rs.getInt(23));
                    monthlyDetailVisitation.setMa22(rs.getInt(24));
                    monthlyDetailVisitation.setMa23(rs.getInt(25));
                    monthlyDetailVisitation.setMa24(rs.getInt(26));
                    monthlyDetailVisitation.setMa25(rs.getInt(27));
                    monthlyDetailVisitation.setMa26(rs.getInt(28));
                    monthlyDetailVisitation.setMa27(rs.getInt(29));
                    monthlyDetailVisitation.setMa28(rs.getInt(30));
                    monthlyDetailVisitation.setMa29(rs.getInt(31));
                    monthlyDetailVisitation.setMa30(rs.getInt(32));
                    monthlyDetailVisitation.setMa31(rs.getInt(33));
                    monthlyDetailVisitation.setN1(rs.getInt(34));
                    monthlyDetailVisitation.setN2(rs.getInt(35));
                    monthlyDetailVisitation.setN3(rs.getInt(36));
                    monthlyDetailVisitation.setN4(rs.getInt(37));
                    monthlyDetailVisitation.setN5(rs.getInt(38));
                    monthlyDetailVisitation.setN6(rs.getInt(39));
                    monthlyDetailVisitation.setN7(rs.getInt(40));
                    monthlyDetailVisitation.setN8(rs.getInt(41));
                    monthlyDetailVisitation.setN9(rs.getInt(42));
                    monthlyDetailVisitation.setN10(rs.getInt(43));
                    monthlyDetailVisitation.setN11(rs.getInt(44));
                    monthlyDetailVisitation.setN12(rs.getInt(45));
                    monthlyDetailVisitation.setN13(rs.getInt(46));
                    monthlyDetailVisitation.setN14(rs.getInt(47));
                    monthlyDetailVisitation.setN15(rs.getInt(48));
                    monthlyDetailVisitation.setN16(rs.getInt(49));
                    monthlyDetailVisitation.setN17(rs.getInt(50));
                    monthlyDetailVisitation.setN18(rs.getInt(51));
                    monthlyDetailVisitation.setN19(rs.getInt(52));
                    monthlyDetailVisitation.setN20(rs.getInt(53));
                    monthlyDetailVisitation.setN21(rs.getInt(54));
                    monthlyDetailVisitation.setN22(rs.getInt(55));
                    monthlyDetailVisitation.setN23(rs.getInt(56));
                    monthlyDetailVisitation.setN24(rs.getInt(57));
                    monthlyDetailVisitation.setN25(rs.getInt(58));
                    monthlyDetailVisitation.setN26(rs.getInt(59));
                    monthlyDetailVisitation.setN27(rs.getInt(60));
                    monthlyDetailVisitation.setN28(rs.getInt(61));
                    monthlyDetailVisitation.setN29(rs.getInt(62));
                    monthlyDetailVisitation.setN30(rs.getInt(63));
                    monthlyDetailVisitation.setN31(rs.getInt(64));
                    monthlyDetailVisitation.setMaTotal();
                    monthlyDetailVisitation.setNTotal();

                    vResult.add(monthlyDetailVisitation);

                }

                allResults.add(vResult);

                // pastikan table temporary untuk report detail dalam keadaan kosong
                int iDeleteStatusLast = deleteDetailReportTemporaryData();
            }

        } catch (Exception error) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getDetailVisitationReport() exc : " + error.toString());
        } finally {
            DBResultSet.close(dbrs);
            return allResults;
        }
    }

    /**
     * @param lDepartmentOid
     * @param lSectionOid
     * @param dSelectedDate
     * @return
     */
    private int getDetailMealsMorningAfternoon(long lDepartmentOid, long lSectionOid, Date dStartSelectedDate, Date dEndSelectedDate, int rptType) {

        int iResult = 0;

        try {

            // lakukan iterasi sebanyak interval waktu (dStartSelectedDate dan dEndSelectedDate) untuk insert data ke table temporary
            String sSQL = "";
            int iStartIterate = dStartSelectedDate.getDate();
            int iEndIterate = dEndSelectedDate.getDate();

            for (int i = iStartIterate; i <= iEndIterate; i++) {

                Date dSelectedDate = new Date(dStartSelectedDate.getYear(), dStartSelectedDate.getMonth(), i);
                sSQL = "INSERT INTO " + TBL_CANTEEN_DETAIL_REPORT
                        + " (" + sTblCanteenDetailFieldNames[0] + ", " + sTblCanteenDetailFieldNames[1] + ", " + sTblCanteenDetailFieldNames[2 + (i - 1)] + ")"
                        + " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + ", SUM(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION] + ")"
                        + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION
                        + " AS CAN INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE
                        + " AS EMP ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                        + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + lDepartmentOid;

                if (lSectionOid != 0) {
                    sSQL = sSQL + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                            + " = " + lSectionOid;
                }

                Vector vOfWhClause = new Vector();
                if (rptType == REPORT_TYPE_DISPLAY_MORNING_AFTERNOON) {
                    vOfWhClause = genVOfWhClsBaseOnCanSchd("CAN", dSelectedDate);
                } else {
                    //vOfWhClause = genVOfWhClsWithoutCanSchd("CAN", dSelectedDate);
                    if (rptType == REPORT_TYPE_DISPLAY_NIGHT) {
                        vOfWhClause = genVOfWhClsWithoutCanSchd("CAN", dSelectedDate);
                    } else {
                        String theDate = com.dimata.util.Formater.formatDate(dSelectedDate, "yyyy-MM-dd");
                        String strWhereTime = "DATE_FORMAT(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                                + ",\"%Y-%m-%d\")=\"" + theDate + "\"";
                        vOfWhClause.add(strWhereTime);
                    }

                }

                if (vOfWhClause != null && vOfWhClause.size() > 0) {
                    String sTemp = "";
                    int iWhClauseCount = vOfWhClause.size();
                    for (int j = 0; j < vOfWhClause.size(); j++) {
                        sTemp = sTemp + vOfWhClause.get(j) + " OR ";
                    }

                    if (sTemp != null && sTemp.length() > 3) {
                        sTemp = sTemp.substring(0, sTemp.length() - 3);
                        sSQL = sSQL + " AND (" + sTemp + ")";
                    }
                }

                sSQL = sSQL + " GROUP BY CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                        + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

                //              System.out.println(new SessCanteenVisitation().getClass().getName()+".getDetailMealsMorningAfternoon() sSQL : " + sSQL);
                iResult = DBHandler.execUpdate(sSQL);
            }
        } catch (Exception e) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getDetailMealsMorningAfternoon() exc : " + e.toString());
        }
        return iResult;
    }

    /**
     * @Author  Roy Andika
     * @param   lDepartmentOid
     * @param   lSectionOid
     * @param   dStartSelectedDate
     * @param   dEndSelectedDate
     * @param   canteenScheduleId
     * @param   rptType
     * @Desc    Untuk mendapatkan canteen visitation mingguan
     * @return
     */
    private synchronized int getDetailMealsMorningAfternoon(long lDepartmentOid, long lSectionOid, Date dStartSelectedDate, Date dEndSelectedDate, String canteenScheduleId[]) {

        int iResult = 0;

        Vector canteenSchedule = new Vector();
        String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE] + " ASC ";

        try {
            canteenSchedule = PstCanteenSchedule.list(0, 0, null, order);
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        try {

            String sSQL = "";
            int iStartIterate = dStartSelectedDate.getDate();
            int iEndIterate = dEndSelectedDate.getDate();

            for (int i = iStartIterate; i <= iEndIterate; i++) {

                Date dSelectedDate = new Date(dStartSelectedDate.getYear(), dStartSelectedDate.getMonth(), i);

                sSQL = "INSERT INTO " + TBL_CANTEEN_DETAIL_REPORT
                        + " (" + sTblCanteenDetailFieldNames[0] + ", " + sTblCanteenDetailFieldNames[1] + ", " + sTblCanteenDetailFieldNames[2 + (i - 1)] + ")"
                        + " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + ", SUM(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION] + ")"
                        + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION
                        + " AS CAN INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE
                        + " AS EMP ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                        + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + lDepartmentOid;

                if (lSectionOid != 0) {
                    sSQL = sSQL + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                            + " = " + lSectionOid;
                }


                String where = "";

                if (canteenScheduleId.length > 0) {

                    int mak = 0;

                    for (int j = 0; j < canteenSchedule.size(); j++) {

                        CanteenSchedule canteenSch = new CanteenSchedule();

                        canteenSch = (CanteenSchedule) canteenSchedule.get(j);

                        if (canteenScheduleId[j].compareTo("1") == 0) {

                            if (where.length() <= 0) {

                                where = where + " ( CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN "
                                        + " '" + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSch.getTTimeOpen(), "HH:mm:ss") + "' AND '"
                                        + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " "
                                        + Formater.formatDate(canteenSch.getTTimeClose(), "HH:mm:ss") + "' ) ";

                            } else {

                                where = where + " OR ( CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN "
                                        + " '" + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSch.getTTimeOpen(), "HH:mm:ss") + "' AND '"
                                        + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " "
                                        + Formater.formatDate(canteenSch.getTTimeClose(), "HH:mm:ss") + "' ) ";
                            }

                        }

                        mak++;

                    }

                    if (canteenScheduleId[mak].compareTo("1") == 0) {

                        String whereOutSchedule = timeOutSchedule("CAN", dSelectedDate);

                        if (where.length() > 0) {

                            where = where + " OR " + whereOutSchedule;

                        } else {

                            where = where + whereOutSchedule;

                        }
                    }

                } else {

                    where = where + " CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " = "
                            + " '" + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " 00:00:00'";

                }


                if (where.length() > 0) {

                    sSQL = sSQL + " AND ( " + where + " )";

                }


                sSQL = sSQL + " GROUP BY CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                        + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];


                iResult = DBHandler.execUpdate(sSQL);
            }
        } catch (Exception e) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getDetailMealsMorningAfternoon() exc : " + e.toString());
        } finally {
            DBResultSet.close(null);
        }
        return iResult;
    }

    /**
     * @param lDepartmentOid
     * @param lSectionOid
     * @param dSelectedDate
     * @return
     */
    private int getDetailMealsNight(long lDepartmentOid, long lSectionOid, Date dStartSelectedDate, Date dEndSelectedDate) {
        int iResult = 0;

        try {
            // lakukan iterasi sebanyak interval waktu (dStartSelectedDate dan dEndSelectedDate) untuk insert data ke table temporary
            String sSQL = "";
            int iStartIterate = dStartSelectedDate.getDate();
            int iEndIterate = dEndSelectedDate.getDate();
            for (int i = iStartIterate; i <= iEndIterate; i++) {
                Date dSelectedDate = new Date(dStartSelectedDate.getYear(), dStartSelectedDate.getMonth(), i);
                sSQL = "INSERT INTO " + TBL_CANTEEN_DETAIL_REPORT
                        + " (" + sTblCanteenDetailFieldNames[0] + ", " + sTblCanteenDetailFieldNames[1] + ", " + sTblCanteenDetailFieldNames[2 + 31 + (i - 1)] + ")"
                        + " SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + //", COUNT(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID] + ")" +
                        ", SUM(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION] + ")"
                        + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION
                        + " AS CAN INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE
                        + " AS EMP ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                        + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + lDepartmentOid;

                if (lSectionOid != 0) {
                    sSQL = sSQL + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                            + " = " + lSectionOid;
                }

                Vector vOfWhClause = genVOfWhClsWithoutCanSchd("CAN", dSelectedDate);
                if (vOfWhClause != null && vOfWhClause.size() > 0) {
                    String sTemp = "";
                    int iWhClauseCount = vOfWhClause.size();
                    for (int j = 0; j < vOfWhClause.size(); j++) {
                        sTemp = sTemp + vOfWhClause.get(j) + " OR ";
                    }

                    if (sTemp != null && sTemp.length() > 3) {
                        sTemp = sTemp.substring(0, sTemp.length() - 3);
                        sSQL = sSQL + " AND (" + sTemp + ")";
                    }
                }

                sSQL = sSQL + " GROUP BY CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                        + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

//                                System.out.println(new SessCanteenVisitation().getClass().getName()+".getDetailMealsNight() sSQL : " + sSQL);
                iResult = DBHandler.execUpdate(sSQL);
            }
        } catch (Exception e) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".getDetailMealsNight() exc : " + e.toString());
        }
        return iResult;
    }

    /**
     * @param dSelectedDate
     * @return
     */
    private Vector genVOfWhClsBaseOnCanSchd(String sPrefiks, Date dSelectedDate) {
        Vector vResult = new Vector();

        Vector vListOfCanteenSchedule = PstCanteenSchedule.list(0, 0, "", "");
        if (vListOfCanteenSchedule != null && vListOfCanteenSchedule.size() > 0) {
            String sSelectedDate = Formater.formatDate(dSelectedDate, "yyyy-MM-dd");

            String sTableAlias = (sPrefiks != null && sPrefiks.length() > 0) ? sPrefiks + "." : "";
            int iListOfCanteenScheduleCount = vListOfCanteenSchedule.size();
            for (int i = 0; i < iListOfCanteenScheduleCount; i++) {
                CanteenSchedule objCanteenSchedule = (CanteenSchedule) vListOfCanteenSchedule.get(i);
                String sScheduleOpen = Formater.formatDate(objCanteenSchedule.getTTimeOpen(), "HH:mm:ss");
                String sScheduleClose = Formater.formatDate(objCanteenSchedule.getTTimeClose(), "HH:mm:ss");

                String sWhClause = sTableAlias + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                        + " BETWEEN \"" + sSelectedDate + " " + sScheduleOpen + "\""
                        + " AND \"" + sSelectedDate + " " + sScheduleClose + "\"";
                vResult.add(sWhClause);
            }
        }

        return vResult;
    }

    /**
     *
     * @param sPrefiks
     * @param dSelectedDate
     * @param objCanteenSchedule
     * @return
     */
    private Vector getWhereInOnScd(String sPrefiks, Date dSelectedDate, CanteenSchedule objCanteenSchedule) {
        Vector vResult = new Vector();

        if (objCanteenSchedule.getOID() != 0) {
            String sSelectedDate = Formater.formatDate(dSelectedDate, "yyyy-MM-dd");
            String sTableAlias = (sPrefiks != null && sPrefiks.length() > 0) ? sPrefiks + "." : "";
            String sScheduleOpen = Formater.formatDate(objCanteenSchedule.getTTimeOpen(), "HH:mm:ss");
            String sScheduleClose = Formater.formatDate(objCanteenSchedule.getTTimeClose(), "HH:mm:ss");
            String sWhClause = sTableAlias + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                    + " BETWEEN \"" + sSelectedDate + " " + sScheduleOpen + "\""
                    + " AND \"" + sSelectedDate + " " + sScheduleClose + "\"";
            vResult.add(sWhClause);
        }
        return vResult;
    }

    /**
     * @param dSelectedDate
     * @return
     */
    private Vector genVOfWhClsWithoutCanSchd(String sPrefiks, Date dSelectedDate) {

        Vector vResult = new Vector();

        String sOrderBy = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_TIME_OPEN];
        Vector vListOfCanteenSchedule = PstCanteenSchedule.list(0, 0, "", sOrderBy);

        if (vListOfCanteenSchedule != null && vListOfCanteenSchedule.size() > 0) {

            String sSelectedDate = Formater.formatDate(dSelectedDate, "yyyy-MM-dd");

            String sTableAlias = (sPrefiks != null && sPrefiks.length() > 0) ? sPrefiks + "." : "";
            String sStartBoundary = "";
            String sEndBoundary = "";
            int iListOfCanteenScheduleCount = vListOfCanteenSchedule.size();

            for (int i = 0; i < iListOfCanteenScheduleCount; i++) {
                CanteenSchedule objCanteenSchedule = (CanteenSchedule) vListOfCanteenSchedule.get(i);
                sEndBoundary = Formater.formatDate(objCanteenSchedule.getTTimeOpen(), "HH:mm:ss");

                String sWhClause = "";
                if (sStartBoundary != null && sStartBoundary.length() > 0) {
                    sWhClause = "(" + sTableAlias + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                            + " > \"" + sSelectedDate + " " + sStartBoundary + "\""
                            + " AND " + sTableAlias + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                            + " < \"" + sSelectedDate + " " + sEndBoundary + "\")";
                } else {
                    sWhClause = "(" + sTableAlias + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                            + " > \"" + sSelectedDate + " 00:00:00\""
                            + " AND " + sTableAlias + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                            + " < \"" + sSelectedDate + " " + sEndBoundary + "\")";
                }

                sEndBoundary = "";
                sStartBoundary = Formater.formatDate(objCanteenSchedule.getTTimeClose(), "HH:mm:ss");

                vResult.add(sWhClause);
            }


            if (sStartBoundary != null && sStartBoundary.length() > 0) {
                String sWhClause = "(" + sTableAlias + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                        + " > \"" + sSelectedDate + " " + sStartBoundary + "\""
                        + " AND " + sTableAlias + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                        + " < \"" + sSelectedDate + " 23:59:59\")";
                vResult.add(sWhClause);
            }
        }

        return vResult;
    }

    /**
     * @Author Roy A.
     * @param dSelectedDate
     * @return
     */
    private static String timeOutSchedule(String sPrefiks, Date dSelectedDate) {

        String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE] + " ASC ";

        Vector vCanteenSchedule = new Vector();

        try {
            vCanteenSchedule = PstCanteenSchedule.list(0, 0, null, order);
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        if (vCanteenSchedule != null && vCanteenSchedule.size() > 0) {

            String where = "";

            for (int i = 0; i < vCanteenSchedule.size(); i++) {

                CanteenSchedule canteenSchedule = new CanteenSchedule();

                canteenSchedule = (CanteenSchedule) vCanteenSchedule.get(i);

                if (i == 0) {

                    CanteenSchedule canteenScheduleLast = new CanteenSchedule();
                    canteenScheduleLast = (CanteenSchedule) vCanteenSchedule.get(vCanteenSchedule.size() - 1);

                    where = where + " ( " + sPrefiks + "." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN '"
                            + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " " + Formater.formatDate(canteenScheduleLast.getTTimeClose(), "HH:mm:ss") + "' AND '"
                            + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSchedule.getTTimeOpen(), "HH:mm:ss") + "' ) ";

                } else {

                    if (where.length() > 0) {

                        CanteenSchedule canteenSchedulePrev = new CanteenSchedule();
                        canteenSchedulePrev = (CanteenSchedule) vCanteenSchedule.get(i - 1);

                        where = where + " OR ( " + sPrefiks + "." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN '"
                                + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSchedulePrev.getTTimeClose(), "HH:mm:ss") + "' AND '"
                                + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSchedule.getTTimeOpen(), "HH:mm:ss") + "' ) ";

                    } else {

                        CanteenSchedule canteenSchedulePrev = new CanteenSchedule();
                        canteenSchedulePrev = (CanteenSchedule) vCanteenSchedule.get(i - 1);

                        where = where + " ( " + sPrefiks + "." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN '"
                                + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSchedulePrev.getTTimeClose(), "HH:mm:ss") + "' AND '"
                                + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSchedule.getTTimeOpen(), "HH:mm:ss") + "' ) ";

                    }
                }
            }

            return where;
        }
        return null;
    }


    /**
     * @Author Roy A.
     * @param dSelectedDate
     * @return
     */
    private static String timeOutSchedule(String sPrefiks) {

        String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE] + " ASC ";

        Vector vCanteenSchedule = new Vector();

        try {
            vCanteenSchedule = PstCanteenSchedule.list(0, 0, null, order);
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        if (vCanteenSchedule != null && vCanteenSchedule.size() > 0) {

            String where = "";

            for (int i = 0; i < vCanteenSchedule.size(); i++) {

                CanteenSchedule canteenSchedule = new CanteenSchedule();

                canteenSchedule = (CanteenSchedule) vCanteenSchedule.get(i);

                if (i == 0) {

                    //(TIME(CANT.visitation_time) between '10:30' and '14:00')
                    CanteenSchedule canteenScheduleLast = new CanteenSchedule();
                    canteenScheduleLast = (CanteenSchedule) vCanteenSchedule.get(vCanteenSchedule.size() - 1);

                    where = where + " ( TIME( " + sPrefiks + "." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " ) BETWEEN '"
                            + Formater.formatDate(canteenScheduleLast.getTTimeClose(), "HH:mm:ss") + "' AND '"
                            + Formater.formatDate(canteenSchedule.getTTimeOpen(), "HH:mm:ss") + "' ) ";

                } else {

                    if (where.length() > 0) {

                        CanteenSchedule canteenSchedulePrev = new CanteenSchedule();
                        canteenSchedulePrev = (CanteenSchedule) vCanteenSchedule.get(i - 1);

                        where = where + " OR ( TIME ( " + sPrefiks + "." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " ) BETWEEN '"
                                + Formater.formatDate(canteenSchedulePrev.getTTimeClose(), "HH:mm:ss") + "' AND '"
                                + Formater.formatDate(canteenSchedule.getTTimeOpen(), "HH:mm:ss") + "' ) ";

                    } else {

                        CanteenSchedule canteenSchedulePrev = new CanteenSchedule();
                        canteenSchedulePrev = (CanteenSchedule) vCanteenSchedule.get(i - 1);

                        where = where + " ( TIME( " + sPrefiks + "." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " ) BETWEEN '"
                                + Formater.formatDate(canteenSchedulePrev.getTTimeClose(), "HH:mm:ss") + "' AND '"
                                + Formater.formatDate(canteenSchedule.getTTimeOpen(), "HH:mm:ss") + "' ) ";

                    }
                }
            }

            return where;
        }
        return null;
    }



    /**
     * @Author Roy A.
     * @param dSelectedDate
     * @return
     */
    private static String timeOutScheduleAccess() {

        String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE] + " ASC ";

        Vector vCanteenSchedule = new Vector();

        try {
            vCanteenSchedule = PstCanteenSchedule.list(0, 0, null, order);
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        if (vCanteenSchedule != null && vCanteenSchedule.size() > 0) {

            String where = "";

            for (int i = 0; i < vCanteenSchedule.size(); i++) {

                CanteenSchedule canteenSchedule = new CanteenSchedule();

                canteenSchedule = (CanteenSchedule) vCanteenSchedule.get(i);

                if (i == 0) {

                    CanteenSchedule canteenScheduleLast = new CanteenSchedule();
                    canteenScheduleLast = (CanteenSchedule) vCanteenSchedule.get(vCanteenSchedule.size() - 1);

                    where = where + " ( Format$(["+Att2000.Tbl_CheckInOut+"].["+Att2000.Fld_InOut_CHECKTIME+"],'hh:mm:dd') BETWEEN '"
                            + Formater.formatDate(canteenScheduleLast.getTTimeClose(), "HH:mm:ss") + "' AND '"
                            + Formater.formatDate(canteenSchedule.getTTimeOpen(), "HH:mm:ss") + "' ) ";

                } else {

                    if (where.length() > 0) {

                        CanteenSchedule canteenSchedulePrev = new CanteenSchedule();
                        canteenSchedulePrev = (CanteenSchedule) vCanteenSchedule.get(i - 1);

                        where = where + " OR ( Format$(["+Att2000.Tbl_CheckInOut+"].["+Att2000.Fld_InOut_CHECKTIME+"],'hh:mm:dd') BETWEEN '"
                                + Formater.formatDate(canteenSchedulePrev.getTTimeClose(), "HH:mm:ss") + "' AND '"
                                + Formater.formatDate(canteenSchedule.getTTimeOpen(), "HH:mm:ss") + "' ) ";

                    } else {

                        CanteenSchedule canteenSchedulePrev = new CanteenSchedule();
                        canteenSchedulePrev = (CanteenSchedule) vCanteenSchedule.get(i - 1);

                        where = where + " ( Format$(["+Att2000.Tbl_CheckInOut+"].["+Att2000.Fld_InOut_CHECKTIME+"],'hh:mm:dd') BETWEEN '"
                                + Formater.formatDate(canteenSchedulePrev.getTTimeClose(), "HH:mm:ss") + "' AND '"
                                + Formater.formatDate(canteenSchedule.getTTimeOpen(), "HH:mm:ss") + "' ) ";

                    }
                }
            }

            return where;
        }
        return null;
    }




    /**
     * @return
     */
    private int deleteDetailReportTemporaryData() {

        int iResult = 0;

        try {

            String sql = "DELETE FROM " + TBL_CANTEEN_DETAIL_REPORT;

            iResult = DBHandler.execUpdate(sql);
        } catch (Exception e) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".deleteDetailReportTemporaryData() exc : " + e.toString());
        }

        return iResult;
    }

    private void deleteDetailReportTemporary() {

        try {

            String sql = "DELETE FROM " + TBL_CANTEEN_DETAIL_REPORT;

            DBHandler.execUpdate(sql);

        } catch (Exception e) {
            System.out.println(new SessCanteenVisitation().getClass().getName() + ".deleteDetailReportTemporaryData() exc : " + e.toString());
        }

    }
    // ------------------------- FINISH DETAIL REPORT ----------------------
    // ------------------------- START SUMMARY REPORT ----------------------
    public static final String TBL_CANTEEN_SUMMARY_REPORT = "hr_canteen_summary_report";//"HR_CANTEEN_SUMMARY_REPORT";

    public static String getEmpDailyScheduleSymbol(long employeeOid,
            java.util.Date scheduleDate) {
        long periodId = PstPeriod.getPeriodeIdBetween(scheduleDate);
        String whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                + " = " + periodId
                + " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                + " = " + employeeOid;
        String orderClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID];
        Vector empScheduleData = PstEmpSchedule.list(0, 0, whereClause, orderClause);

        String results = "";
        int empScheduleDataSize = empScheduleData.size();
        for (int item = 0; item < empScheduleDataSize; item++) {
            EmpSchedule empSchedule = (EmpSchedule) empScheduleData.get(0);

            byte selectedDate = (byte) scheduleDate.getDate();
            long symbolOid1 = 0;
            long symbolOid2 = 0;
            String symbol1 = "";
            String symbol2 = "";

            if (empSchedule.getOID() > 0) {
                switch (selectedDate) {
                    case 1:
                        symbolOid1 = empSchedule.getD1();
                        symbolOid2 = empSchedule.getD2nd1();
                        break;
                    case 2:
                        symbolOid1 = empSchedule.getD2();
                        symbolOid2 = empSchedule.getD2nd2();
                        break;
                    case 3:
                        symbolOid1 = empSchedule.getD3();
                        symbolOid2 = empSchedule.getD2nd3();
                        break;
                    case 4:
                        symbolOid1 = empSchedule.getD4();
                        symbolOid2 = empSchedule.getD2nd4();
                        break;
                    case 5:
                        symbolOid2 = empSchedule.getD5();
                        symbolOid2 = empSchedule.getD2nd5();
                        break;
                    case 6:
                        symbolOid1 = empSchedule.getD6();
                        symbolOid2 = empSchedule.getD2nd6();
                        break;
                    case 7:
                        symbolOid1 = empSchedule.getD7();
                        symbolOid2 = empSchedule.getD2nd7();
                        break;
                    case 8:
                        symbolOid1 = empSchedule.getD8();
                        symbolOid2 = empSchedule.getD2nd8();
                        break;
                    case 9:
                        symbolOid1 = empSchedule.getD9();
                        symbolOid2 = empSchedule.getD2nd9();
                        break;
                    case 10:
                        symbolOid1 = empSchedule.getD10();
                        symbolOid2 = empSchedule.getD2nd10();
                        break;
                    case 11:
                        symbolOid1 = empSchedule.getD11();
                        symbolOid2 = empSchedule.getD2nd11();
                        break;
                    case 12:
                        symbolOid1 = empSchedule.getD12();
                        symbolOid2 = empSchedule.getD2nd12();
                        break;
                    case 13:
                        symbolOid1 = empSchedule.getD13();
                        symbolOid2 = empSchedule.getD2nd13();
                        break;
                    case 14:
                        symbolOid1 = empSchedule.getD14();
                        symbolOid2 = empSchedule.getD2nd14();
                        break;
                    case 15:
                        symbolOid1 = empSchedule.getD15();
                        symbolOid2 = empSchedule.getD2nd15();
                        break;
                    case 16:
                        symbolOid1 = empSchedule.getD16();
                        symbolOid2 = empSchedule.getD2nd16();
                        break;
                    case 17:
                        symbolOid1 = empSchedule.getD17();
                        symbolOid2 = empSchedule.getD2nd17();
                        break;
                    case 18:
                        symbolOid1 = empSchedule.getD18();
                        symbolOid2 = empSchedule.getD2nd18();
                        break;
                    case 19:
                        symbolOid1 = empSchedule.getD19();
                        symbolOid2 = empSchedule.getD2nd19();
                        break;
                    case 20:
                        symbolOid1 = empSchedule.getD20();
                        symbolOid2 = empSchedule.getD2nd20();
                        break;
                    case 21:
                        symbolOid1 = empSchedule.getD21();
                        symbolOid2 = empSchedule.getD2nd21();
                        break;
                    case 22:
                        symbolOid1 = empSchedule.getD22();
                        symbolOid2 = empSchedule.getD2nd22();
                        break;
                    case 23:
                        symbolOid1 = empSchedule.getD23();
                        symbolOid2 = empSchedule.getD2nd23();
                        break;
                    case 24:
                        symbolOid1 = empSchedule.getD24();
                        symbolOid2 = empSchedule.getD2nd24();
                        break;
                    case 25:
                        symbolOid1 = empSchedule.getD25();
                        symbolOid2 = empSchedule.getD2nd25();
                        break;
                    case 26:
                        symbolOid1 = empSchedule.getD26();
                        symbolOid2 = empSchedule.getD2nd26();
                        break;
                    case 27:
                        symbolOid1 = empSchedule.getD27();
                        symbolOid2 = empSchedule.getD2nd27();
                        break;
                    case 28:
                        symbolOid1 = empSchedule.getD28();
                        symbolOid2 = empSchedule.getD2nd28();
                        break;
                    case 29:
                        symbolOid1 = empSchedule.getD29();
                        symbolOid2 = empSchedule.getD2nd29();
                        break;
                    case 30:
                        symbolOid1 = empSchedule.getD30();
                        symbolOid2 = empSchedule.getD2nd30();
                        break;
                    case 31:
                        symbolOid1 = empSchedule.getD31();
                        symbolOid2 = empSchedule.getD2nd31();
                }

                try {
                    ScheduleSymbol schSymbol = new ScheduleSymbol();
                    if (symbolOid1 > 0) {
                        schSymbol = (ScheduleSymbol) PstScheduleSymbol.fetchExc(symbolOid1);
                    }
                    symbol1 = schSymbol.getSymbol();
                    if (symbolOid2 > 0) {
                        schSymbol = (ScheduleSymbol) PstScheduleSymbol.fetchExc(symbolOid2);
                    } else {
                        schSymbol = new ScheduleSymbol();
                    }
                    symbol2 = schSymbol.getSymbol();
                } catch (Exception error) {
                    System.out.println("There are exception occur in SessCanteenVisitation - getEmpDailyScheduleSymbol() : " + error.toString());
                }
            }
            String buffer = "";
            if (symbol1.length() > 0) {
                if (symbol2.length() > 0) {
                    buffer = symbol1 + " / " + symbol2;
                } else {
                    buffer = symbol1;
                }
            } else if (symbol2.length() > 0) {
                buffer = symbol2;
            }
            if (results.length() > 0) {
                results += " / " + buffer;
            } else {
                results = buffer;
            }
        }
        if (results.length() > 0) {
            return results;
        }
        return "-";
    }

    /**
     *
     * @param visitDate
     * @param employeeOid
     * @return
     */
    public static Vector getEmpDailySchedule(java.util.Date visitDate, long employeeOid) {
        long periodId = PstPeriod.getPeriodeIdBetween(visitDate);
        String whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                + " = " + periodId
                + " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                + " = " + employeeOid;

        String orderClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID];
        Vector empScheduleData = PstEmpSchedule.list(0, 0, whereClause, orderClause);

        int empScheduleDataSize = empScheduleData.size();
        String schString = "";
        boolean nightShift = false;
        Vector results = new Vector();
        for (int item = 0; item < empScheduleDataSize; item++) {
            EmpSchedule empSchedule = (EmpSchedule) empScheduleData.get(item);

            byte selectedDate = (byte) visitDate.getDate();
            long symbolOid1 = 0;
            long symbolOid2 = 0;

            if (empSchedule.getOID() > 0) {
                switch (selectedDate) {
                    case 1:
                        symbolOid1 = empSchedule.getD1();
                        symbolOid2 = empSchedule.getD2nd1();
                        break;
                    case 2:
                        symbolOid1 = empSchedule.getD2();
                        symbolOid2 = empSchedule.getD2nd2();
                        break;
                    case 3:
                        symbolOid1 = empSchedule.getD3();
                        symbolOid2 = empSchedule.getD2nd3();
                        break;
                    case 4:
                        symbolOid1 = empSchedule.getD4();
                        symbolOid2 = empSchedule.getD2nd4();
                        break;
                    case 5:
                        symbolOid1 = empSchedule.getD5();
                        symbolOid2 = empSchedule.getD2nd5();
                        break;
                    case 6:
                        symbolOid1 = empSchedule.getD6();
                        symbolOid2 = empSchedule.getD2nd6();
                        break;
                    case 7:
                        symbolOid1 = empSchedule.getD7();
                        symbolOid2 = empSchedule.getD2nd7();
                        break;
                    case 8:
                        symbolOid1 = empSchedule.getD8();
                        symbolOid2 = empSchedule.getD2nd8();
                        break;
                    case 9:
                        symbolOid1 = empSchedule.getD9();
                        symbolOid2 = empSchedule.getD2nd9();
                        break;
                    case 10:
                        symbolOid1 = empSchedule.getD10();
                        symbolOid2 = empSchedule.getD2nd10();
                        break;
                    case 11:
                        symbolOid1 = empSchedule.getD11();
                        symbolOid2 = empSchedule.getD2nd11();
                        break;
                    case 12:
                        symbolOid1 = empSchedule.getD12();
                        symbolOid2 = empSchedule.getD2nd12();
                        break;
                    case 13:
                        symbolOid1 = empSchedule.getD13();
                        symbolOid2 = empSchedule.getD2nd13();
                        break;
                    case 14:
                        symbolOid1 = empSchedule.getD14();
                        symbolOid2 = empSchedule.getD2nd14();
                        break;
                    case 15:
                        symbolOid1 = empSchedule.getD15();
                        symbolOid2 = empSchedule.getD2nd15();
                        break;
                    case 16:
                        symbolOid1 = empSchedule.getD16();
                        symbolOid2 = empSchedule.getD2nd16();
                        break;
                    case 17:
                        symbolOid1 = empSchedule.getD17();
                        symbolOid2 = empSchedule.getD2nd17();
                        break;
                    case 18:
                        symbolOid1 = empSchedule.getD18();
                        symbolOid2 = empSchedule.getD2nd18();
                        break;
                    case 19:
                        symbolOid1 = empSchedule.getD19();
                        symbolOid2 = empSchedule.getD2nd19();
                        break;
                    case 20:
                        symbolOid1 = empSchedule.getD20();
                        symbolOid2 = empSchedule.getD2nd20();
                        break;
                    case 21:
                        symbolOid1 = empSchedule.getD21();
                        symbolOid2 = empSchedule.getD2nd21();
                        break;
                    case 22:
                        symbolOid1 = empSchedule.getD22();
                        symbolOid2 = empSchedule.getD2nd22();
                        break;
                    case 23:
                        symbolOid1 = empSchedule.getD23();
                        symbolOid2 = empSchedule.getD2nd23();
                        break;
                    case 24:
                        symbolOid1 = empSchedule.getD24();
                        symbolOid2 = empSchedule.getD2nd24();
                        break;
                    case 25:
                        symbolOid1 = empSchedule.getD25();
                        symbolOid2 = empSchedule.getD2nd25();
                        break;
                    case 26:
                        symbolOid1 = empSchedule.getD26();
                        symbolOid2 = empSchedule.getD2nd26();
                        break;
                    case 27:
                        symbolOid1 = empSchedule.getD27();
                        symbolOid2 = empSchedule.getD2nd27();
                        break;
                    case 28:
                        symbolOid1 = empSchedule.getD28();
                        symbolOid2 = empSchedule.getD2nd28();
                        break;
                    case 29:
                        symbolOid1 = empSchedule.getD29();
                        symbolOid2 = empSchedule.getD2nd29();
                        break;
                    case 30:
                        symbolOid1 = empSchedule.getD30();
                        symbolOid2 = empSchedule.getD2nd30();
                        break;
                    case 31:
                        symbolOid1 = empSchedule.getD31();
                        symbolOid2 = empSchedule.getD2nd31();
                }

                String symbol1 = "";
                String symbol2 = "";
                long schCategoryId1 = 0;
                long schCategoryId2 = 0;

                try {
                    ScheduleSymbol schSymbol = new ScheduleSymbol();
                    if (symbolOid1 > 0) {
                        schSymbol = (ScheduleSymbol) PstScheduleSymbol.fetchExc(symbolOid1);
                    }
                    symbol1 = schSymbol.getSymbol();
                    schCategoryId1 = schSymbol.getScheduleCategoryId();
                    ScheduleCategory schCategory = new ScheduleCategory();
                    if (schCategoryId1 > 0) {
                        schCategory = PstScheduleCategory.fetchExc(schCategoryId1);
                        if (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_NIGHT_WORKER) {
                            nightShift = true;
                        }
                    }

                    //System.out.println("Nilai dari symbolOid2 = " + symbolOid2);
                    if (symbolOid2 > 0) {
                        schSymbol = PstScheduleSymbol.fetchExc(symbolOid2);
                    } else {
                        schSymbol = new ScheduleSymbol();
                    }
                    symbol2 = schSymbol.getSymbol();
                    schCategoryId2 = schSymbol.getScheduleCategoryId();
                    if (!nightShift && schCategoryId2 > 0) {
                        schCategory = PstScheduleCategory.fetchExc(schCategoryId2);
                        if (schCategory.getCategoryType() == PstScheduleCategory.CATEGORY_NIGHT_WORKER) {
                            nightShift = true;
                        }
                    }
                } catch (Exception error) {
                    System.out.println("There are error occur in SessCanteenVisitation - getEmpDailySchedule() : " + error.toString());
                    //System.out.println("Schedule Category Id = " + schCategoryId1);
                    //System.out.println("Schedule Category Id = " + schCategoryId2);
                }

                String empSchSymbol = "";

                if (symbol1.length() > 0) {
                    if (symbol2.length() > 0) {
                        empSchSymbol = symbol1 + " / " + symbol2;
                    } else {
                        empSchSymbol = symbol1;
                    }
                } else if (symbol2.length() > 0) {
                    empSchSymbol = symbol2;
                }
                if (schString.length() > 0) {
                    schString += " / " + empSchSymbol;
                } else {
                    schString = empSchSymbol;
                }
            }
        }

        if (schString.length() > 0) {
            results.add(schString);
            if (nightShift) {
                results.add("1");
            } else {
                results.add("0");
            }
        } else {
            results.add("-");
            results.add("0");
        }
        return results;
    }

    /**
     *
     * @param dSelectedDate
     * @param lDepartmentOid
     * @param rptType
     * @return
     */
    public Vector getSummaryDailyVisitation(Date dSelectedDate, long lDepartmentOid, long sectionId, int rptType) {
        DBResultSet dbrs = null;
        Vector outputs = new Vector();
        try {

            String strSelectedDate = Formater.formatDate(dSelectedDate, "yyyy-MM-dd");
            String sql = "SELECT SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + //", COUNT(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID] + ") AS VISITS " +
                    ", SUM(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION] + ") AS VISITS "
                    + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION
                    + " AS CAN INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstSection.TBL_HR_SECTION
                    + " AS SEC ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID];

            if (lDepartmentOid != 0) {
                sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + lDepartmentOid;
            }

            if (sectionId != 0) {
                if (lDepartmentOid != 0) {
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                            + " = " + sectionId;
                } else {
                    sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                            + " = " + sectionId;
                }
            }

            Vector vOfWhClause = new Vector();
            if (rptType == REPORT_TYPE_DISPLAY_MORNING_AFTERNOON) {
                vOfWhClause = genVOfWhClsBaseOnCanSchd("CAN", dSelectedDate);
            } else {
                //vOfWhClause = genVOfWhClsWithoutCanSchd("CAN", dSelectedDate);
                if (rptType == REPORT_TYPE_DISPLAY_NIGHT) {
                    vOfWhClause = genVOfWhClsWithoutCanSchd("CAN", dSelectedDate);
                } else {
                    String theDate = com.dimata.util.Formater.formatDate(dSelectedDate, "yyyy-MM-dd");
                    String strWhereTime = "DATE_FORMAT(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                            + ",\"%Y-%m-%d\")=\"" + theDate + "\"";
                    vOfWhClause.add(strWhereTime);
                }

            }

            if (vOfWhClause != null && vOfWhClause.size() > 0) {
                String sTemp = "";
                int iWhClauseCount = vOfWhClause.size();
                for (int i = 0; i < vOfWhClause.size(); i++) {
                    sTemp = sTemp + vOfWhClause.get(i) + " OR ";
                }

                if (sTemp != null && sTemp.length() > 3) {
                    sTemp = sTemp.substring(0, sTemp.length() - 3);
                    if (lDepartmentOid == 0 && sectionId == 0) {
                        sql = sql + " WHERE (" + sTemp + ")";
                    } else {
                        sql = sql + " AND (" + sTemp + ")";
                    }
                }
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID];
            sql = sql + " ORDER BY SEC." + PstSection.fieldNames[PstSection.FLD_SECTION];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            Vector vListOfCanteenSchedule = new Vector();
            if (rptType == REPORT_TYPE_DISPLAY_MORNING_AFTERNOON) {
                vListOfCanteenSchedule = PstCanteenSchedule.list(0, 0, "", PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_TIME_OPEN]);
            }
            while (rs.next()) {
                SummaryDailyVisitation summaryDailyVisitation = new SummaryDailyVisitation();
                summaryDailyVisitation.setDepartmentName(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                summaryDailyVisitation.setDepartmentOid(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                summaryDailyVisitation.setNumVisits(rs.getInt("VISITS"));

                if (rptType == REPORT_TYPE_DISPLAY_MORNING_AFTERNOON) {
                    if (vListOfCanteenSchedule != null && vListOfCanteenSchedule.size() > 0) {
                        int iListOfCanteenScheduleCount = vListOfCanteenSchedule.size();
                        for (int i = 0; i < iListOfCanteenScheduleCount; i++) {
                            CanteenSchedule objCanteenSchedule = (CanteenSchedule) vListOfCanteenSchedule.get(i);
                            SummaryDailyVisitation sumDailyVisit = getSummaryDepartmentVisitation(dSelectedDate, lDepartmentOid, summaryDailyVisitation.getDepartmentOid(), getWhereInOnScd("CAN", dSelectedDate, objCanteenSchedule));
                            summaryDailyVisitation.setValues(sumDailyVisit.getNumVisits());
                        }
                    }
                } else {
                    SummaryDailyVisitation sumDailyVisit = getSummaryDepartmentVisitation(dSelectedDate, lDepartmentOid, summaryDailyVisitation.getDepartmentOid(), vOfWhClause);
                    summaryDailyVisitation.setValues(sumDailyVisit.getNumVisits());
                }
                outputs.add(summaryDailyVisitation);
            }
        } catch (Exception error) {
            System.out.println("Exception in class SessCanteenVisitation - getSummaryDailyVisitation() : " + error.toString());
        }
        return outputs;
    }

    /**
     * @Author Roy Andika
     * @param dSelectedDate
     * @param lDepartmentOid
     * @param sectionId
     * @param canteenScheduleId
     * @return
     */
    public Vector getSummaryDailyVisitation(Date dSelectedDate, long lDepartmentOid, long sectionId, String canteenScheduleId[]) {

        Vector canteenSchedule = new Vector();
        String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE] + " ASC ";

        try {
            canteenSchedule = PstCanteenSchedule.list(0, 0, null, order);
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        DBResultSet dbrs = null;
        Vector outputs = new Vector();

        try {

            String sql = "SELECT SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + ", SUM(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION] + ") AS VISITS "
                    + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION
                    + " AS CAN INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstSection.TBL_HR_SECTION
                    + " AS SEC ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID];

            if (lDepartmentOid != 0) {
                sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + lDepartmentOid;
            }

            if (sectionId != 0) {
                if (lDepartmentOid != 0) {
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                            + " = " + sectionId;
                } else {
                    sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                            + " = " + sectionId;
                }
            }

            String where = "";

            String whereSch = "";

            if (canteenScheduleId.length > 0) {

                int mak = 0;

                for (int i = 0; i < canteenSchedule.size(); i++) {

                    CanteenSchedule canteenSch = new CanteenSchedule();

                    canteenSch = (CanteenSchedule) canteenSchedule.get(i);

                    if (whereSch.length() > 0) {

                        whereSch = whereSch + " OR " + PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CANTEEN_SCHEDULE_ID] + " = " + canteenSch.getOID();

                    } else {

                        whereSch = whereSch + PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CANTEEN_SCHEDULE_ID] + " = " + canteenSch.getOID();

                    }

                    if (canteenScheduleId[i].compareTo("1") == 0) {

                        if (where.length() <= 0) {

                            where = where + " ( CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN "
                                    + " '" + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSch.getTTimeOpen(), "HH:mm:ss") + "' AND '"
                                    + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " "
                                    + Formater.formatDate(canteenSch.getTTimeClose(), "HH:mm:ss") + "' ) ";

                        } else {

                            where = where + " OR ( CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN "
                                    + " '" + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSch.getTTimeOpen(), "HH:mm:ss") + "' AND '"
                                    + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " "
                                    + Formater.formatDate(canteenSch.getTTimeClose(), "HH:mm:ss") + "' ) ";
                        }

                    }

                    mak++;

                }

                if (canteenScheduleId[mak].compareTo("1") == 0) {

                    String whereOutSchedule = timeOutSchedule("CAN", dSelectedDate);

                    if (where.length() > 0) {

                        where = where + " OR " + whereOutSchedule;

                    } else {

                        where = where + whereOutSchedule;

                    }
                }

            } else {

                where = where + " CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " = "
                        + " '" + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " 00:00:00'";

            }


            if (where.length() > 0) {

                if (lDepartmentOid != 0 || sectionId != 0) {

                    sql = sql + " AND (" + where + ") ";

                } else {

                    sql = sql + " WHERE ( " + where + " ) ";

                }

            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID];
            sql = sql + " ORDER BY SEC." + PstSection.fieldNames[PstSection.FLD_SECTION];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();


            Vector vListOfCanteenSchedule = new Vector();

            vListOfCanteenSchedule = PstCanteenSchedule.list(0, 0, whereSch, PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE]);

            while (rs.next()) {

                SummaryDailyVisitation summaryDailyVisitation = new SummaryDailyVisitation();
                summaryDailyVisitation.setDepartmentName(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                summaryDailyVisitation.setDepartmentOid(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                summaryDailyVisitation.setNumVisits(rs.getInt("VISITS"));

                if (vListOfCanteenSchedule != null && vListOfCanteenSchedule.size() > 0) {

                    int iListOfCanteenScheduleCount = vListOfCanteenSchedule.size();
                    for (int i = 0; i < iListOfCanteenScheduleCount; i++) {
                        CanteenSchedule objCanteenSchedule = (CanteenSchedule) vListOfCanteenSchedule.get(i);
                        SummaryDailyVisitation sumDailyVisit = getSummaryDepartmentVisitation(dSelectedDate, lDepartmentOid, summaryDailyVisitation.getDepartmentOid(), getWhereInOnScd("CAN", dSelectedDate, objCanteenSchedule));
                        summaryDailyVisitation.setValues(sumDailyVisit.getNumVisits());
                    }

                }

                outputs.add(summaryDailyVisitation);
            }
        } catch (Exception error) {
            System.out.println("Exception in class SessCanteenVisitation - getSummaryDailyVisitation() : " + error.toString());
        }
        return outputs;
    }

    /**
     * ini funcsignya untuk mencari data
     * yang makan di pagi, afternoon dan night per department
     * @param dSelectedDate
     * @param lDepartmentOid
     * @param vOfWhClause
     * @return
     */
    public SummaryDailyVisitation getSummaryDepartmentVisitation(Date dSelectedDate, long lDepartmentOid, long sectionId, Vector vOfWhClause) {
        DBResultSet dbrs = null;
        SummaryDailyVisitation summaryDailyVisitation = new SummaryDailyVisitation();
        try {

            String strSelectedDate = Formater.formatDate(dSelectedDate, "yyyy-MM-dd");
            String sql = "SELECT SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                    + ", SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                    + //", COUNT(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID] + ") AS VISITS " +
                    ", SUM(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION] + ") AS VISITS "
                    + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION
                    + " AS CAN INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstSection.TBL_HR_SECTION
                    + " AS SEC ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID];

            if (lDepartmentOid != 0) {
                sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + lDepartmentOid;
            }

            if (sectionId != 0) {
                if (lDepartmentOid != 0) {
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                            + " = " + sectionId;
                } else {
                    sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                            + " = " + sectionId;
                }
            }

            if (vOfWhClause != null && vOfWhClause.size() > 0) {
                String sTemp = "";
                int iWhClauseCount = vOfWhClause.size();
                for (int i = 0; i < vOfWhClause.size(); i++) {
                    sTemp = sTemp + vOfWhClause.get(i) + " OR ";
                }

                if (sTemp != null && sTemp.length() > 3) {
                    sTemp = sTemp.substring(0, sTemp.length() - 3);
                    if (lDepartmentOid == 0 && sectionId == 0) {
                        sql = sql + " WHERE " + sTemp;
                    } else {
                        sql = sql + " AND (" + sTemp + ")";
                    }
                }
            }

            sql = sql + " GROUP BY SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID];
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                summaryDailyVisitation.setDepartmentName(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                summaryDailyVisitation.setNumVisits(rs.getInt("VISITS"));
                summaryDailyVisitation.setDepartmentOid(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
            }
        } catch (Exception error) {
            System.out.println("Exception in class SessCanteenVisitation - getSummaryDepartmentVisitation() : " + error.toString());
        }
        return summaryDailyVisitation;
    }

    /**
     * ini untuk mencari data summary
     * total meal per department
     * @param periodDate
     * @return
     */
    public Vector getPeriodicSummaryVisitation(Date dStartSelectedDate, Date dEndSelectedDate, long lDepartmentOid, long sectionId, int rptType) {
        Vector results = new Vector();
        try {
            String sql = "DELETE FROM " + TBL_CANTEEN_SUMMARY_REPORT;
            DBHandler.execUpdate(sql);
            Date dtSrch = new Date(dStartSelectedDate.getTime());
            int iStartIterate = dStartSelectedDate.getDate();
            int iEndIterate = dEndSelectedDate.getDate();
            iEndIterate = iEndIterate + 1;

            // memasukkan data ke table yang di anggap sebagai temp data
            for (int j = iStartIterate; j <= iEndIterate; j++) {
                dtSrch.setDate(j);
                sql = "INSERT INTO " + TBL_CANTEEN_SUMMARY_REPORT
                        + " (DEPARTMENT_ID, DEPARTMENT_NAME, MA" + String.valueOf(j) + ") "
                        + " SELECT SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                        + " ,SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                        + //" ,COUNT(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID] + ") " +
                        " ,SUM(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION] + ") "
                        + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION + " AS CAN "
                        + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                        + " ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + " INNER JOIN " + PstSection.TBL_HR_SECTION + " AS SEC "
                        + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID];

                if (lDepartmentOid != 0) {
                    sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                            + " = " + lDepartmentOid;
                }

                if (sectionId != 0) {
                    if (lDepartmentOid != 0) {
                        sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + sectionId;
                    } else {
                        sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + sectionId;
                    }
                }

                Vector vOfWhClause = new Vector();
                if (rptType == REPORT_TYPE_DISPLAY_MORNING_AFTERNOON) {
                    vOfWhClause = genVOfWhClsBaseOnCanSchd("CAN", dtSrch);
                } else {
                    if (rptType == REPORT_TYPE_DISPLAY_NIGHT) {
                        vOfWhClause = genVOfWhClsWithoutCanSchd("CAN", dtSrch);
                    } else {
                        String theDate = com.dimata.util.Formater.formatDate(dtSrch, "yyyy-MM-dd");
                        String strWhereTime = "DATE_FORMAT(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                                + ",\"%Y-%m-%d\")=\"" + theDate + "\"";
                        vOfWhClause.add(strWhereTime);
                    }
                }

                if (vOfWhClause != null && vOfWhClause.size() > 0) {
                    String sTemp = "";
                    for (int i = 0; i < vOfWhClause.size(); i++) {
                        sTemp = sTemp + vOfWhClause.get(i) + " OR ";
                    }

                    if (sTemp != null && sTemp.length() > 3) {
                        sTemp = sTemp.substring(0, sTemp.length() - 3);
                        if (lDepartmentOid == 0 && sectionId == 0) {
                            sql = sql + " WHERE " + sTemp;
                        } else {
                            sql = sql + " AND (" + sTemp + ")";
                        }
                    }
                }

                sql = sql + " GROUP BY SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID];
                sql = sql + " ORDER BY SEC." + PstSection.fieldNames[PstSection.FLD_SECTION];
                System.out.println("-----> " + sql);
                DBHandler.execUpdate(sql);
            }

            sql = "SELECT DISTINCT DEPARTMENT_ID, DEPARTMENT_NAME";
            for (int j = iStartIterate; j <= iEndIterate; j++) {
                sql += ", SUM(MA" + String.valueOf(j) + ") AS SUM" + String.valueOf(j);
            }

            sql += " FROM " + TBL_CANTEEN_SUMMARY_REPORT
                    + " GROUP BY DEPARTMENT_ID ORDER BY DEPARTMENT_NAME";

            DBResultSet dbrs = DBHandler.execQueryResult(sql);
            ResultSet rsl = dbrs.getResultSet();

            while (rsl.next()) {
                MonthlySummary msv = new MonthlySummary();
                msv.setDepartmentName(rsl.getString("DEPARTMENT_NAME"));

                for (int j = iStartIterate; j <= iEndIterate; j++) {
                    switch (j) {
                        case 1:
                            msv.setDate1(rsl.getInt("SUM1"));
                            break;
                        case 2:
                            msv.setDate2(rsl.getInt("SUM2"));
                            break;
                        case 3:
                            msv.setDate3(rsl.getInt("SUM3"));
                            break;
                        case 4:
                            msv.setDate4(rsl.getInt("SUM4"));
                            break;
                        case 5:
                            msv.setDate5(rsl.getInt("SUM5"));
                            break;
                        case 6:
                            msv.setDate6(rsl.getInt("SUM6"));
                            break;
                        case 7:
                            msv.setDate7(rsl.getInt("SUM7"));
                            break;
                        case 8:
                            msv.setDate8(rsl.getInt("SUM8"));
                            break;
                        case 9:
                            msv.setDate9(rsl.getInt("SUM9"));
                            break;
                        case 10:
                            msv.setDate10(rsl.getInt("SUM10"));
                            break;
                        case 11:
                            msv.setDate11(rsl.getInt("SUM11"));
                            break;
                        case 12:
                            msv.setDate12(rsl.getInt("SUM12"));
                            break;
                        case 13:
                            msv.setDate13(rsl.getInt("SUM13"));
                            break;
                        case 14:
                            msv.setDate14(rsl.getInt("SUM14"));
                            break;
                        case 15:
                            msv.setDate15(rsl.getInt("SUM15"));
                            break;
                        case 16:
                            msv.setDate16(rsl.getInt("SUM16"));
                            break;
                        case 17:
                            msv.setDate17(rsl.getInt("SUM17"));
                            break;
                        case 18:
                            msv.setDate18(rsl.getInt("SUM18"));
                            break;
                        case 19:
                            msv.setDate19(rsl.getInt("SUM19"));
                            break;
                        case 20:
                            msv.setDate20(rsl.getInt("SUM20"));
                            break;
                        case 21:
                            msv.setDate21(rsl.getInt("SUM21"));
                            break;
                        case 22:
                            msv.setDate22(rsl.getInt("SUM22"));
                            break;
                        case 23:
                            msv.setDate23(rsl.getInt("SUM23"));
                            break;
                        case 24:
                            msv.setDate24(rsl.getInt("SUM24"));
                            break;
                        case 25:
                            msv.setDate25(rsl.getInt("SUM25"));
                            break;
                        case 26:
                            msv.setDate26(rsl.getInt("SUM26"));
                            break;
                        case 27:
                            msv.setDate27(rsl.getInt("SUM27"));
                            break;
                        case 28:
                            msv.setDate28(rsl.getInt("SUM28"));
                            break;
                        case 29:
                            msv.setDate29(rsl.getInt("SUM29"));
                            break;
                        case 30:
                            msv.setDate30(rsl.getInt("SUM30"));
                            break;
                        case 31:
                            msv.setDate31(rsl.getInt("SUM31"));
                    }
                }
                msv.calculateTotal();
                results.add(msv);
            }

            sql = "DELETE FROM " + TBL_CANTEEN_SUMMARY_REPORT;
            DBHandler.execUpdate(sql);

        } catch (Exception error) {
            System.out.println("There are error occur in SessCanteenVisitation : " + error.toString());
        }
        return results;
    }

    /**
     * @Author Roy A.
     * @param  dStartSelectedDate
     * @param  dEndSelectedDate
     * @param  lDepartmentOid
     * @param  sectionId
     * @param  rptType
     * @return
     */
    public synchronized Vector getPeriodicSummaryVisitation(Date dStartSelectedDate, Date dEndSelectedDate, long lDepartmentOid, long sectionId, String canteenScheduleId[]) {

        Vector results = new Vector();
        try {

            String sql = "DELETE FROM " + TBL_CANTEEN_SUMMARY_REPORT;
            DBHandler.execUpdate(sql);

            Date dtSrch = new Date(dStartSelectedDate.getTime());
            int iStartIterate = dStartSelectedDate.getDate();
            int iEndIterate = dEndSelectedDate.getDate();
            iEndIterate = iEndIterate + 1;

            Vector canteenSchedule = new Vector();
            String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE] + " ASC ";

            try {
                canteenSchedule = PstCanteenSchedule.list(0, 0, null, order);
            } catch (Exception E) {
                System.out.println("Exception " + E.toString());
            }

            // memasukkan data ke table yang di anggap sebagai temp data
            for (int j = iStartIterate; j <= iEndIterate; j++) {

                dtSrch.setDate(j);
                sql = "INSERT INTO " + TBL_CANTEEN_SUMMARY_REPORT
                        + " (DEPARTMENT_ID, DEPARTMENT_NAME, MA" + String.valueOf(j) + ") "
                        + " SELECT SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                        + " ,SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                        + " ,SUM(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION] + ") "
                        + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION + " AS CAN "
                        + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                        + " ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + " INNER JOIN " + PstSection.TBL_HR_SECTION + " AS SEC "
                        + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID];

                if (lDepartmentOid != 0) {
                    sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                            + " = " + lDepartmentOid;
                }

                if (sectionId != 0) {
                    if (lDepartmentOid != 0) {
                        sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + sectionId;
                    } else {
                        sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + sectionId;
                    }
                }

                String where = "";

                if (canteenScheduleId.length > 0) {

                    int mak = 0;

                    for (int i = 0; i < canteenSchedule.size(); i++) {

                        CanteenSchedule canteenSch = new CanteenSchedule();

                        canteenSch = (CanteenSchedule) canteenSchedule.get(i);

                        if (canteenScheduleId[i].compareTo("1") == 0) {

                            if (where.length() <= 0) {

                                where = where + " ( CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN "
                                        + " '" + Formater.formatDate(dtSrch, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSch.getTTimeOpen(), "HH:mm:ss") + "' AND '"
                                        + Formater.formatDate(dtSrch, "yyyy-MM-dd") + " "
                                        + Formater.formatDate(canteenSch.getTTimeClose(), "HH:mm:ss") + "' ) ";

                            } else {

                                where = where + " OR ( CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN "
                                        + " '" + Formater.formatDate(dtSrch, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSch.getTTimeOpen(), "HH:mm:ss") + "' AND '"
                                        + Formater.formatDate(dtSrch, "yyyy-MM-dd") + " "
                                        + Formater.formatDate(canteenSch.getTTimeClose(), "HH:mm:ss") + "' ) ";
                            }

                        }

                        mak++;

                    }

                    if (canteenScheduleId[mak].compareTo("1") == 0) {

                        String whereOutSchedule = timeOutSchedule("CAN", dtSrch);

                        if (where.length() > 0) {

                            where = where + " OR " + whereOutSchedule;

                        } else {

                            where = where + whereOutSchedule;

                        }
                    }

                } else {

                    where = where + " CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " = "
                            + " '" + Formater.formatDate(dtSrch, "yyyy-MM-dd") + " 00:00:00'";

                }


                if (where.length() > 0) {

                    if (lDepartmentOid != 0 || sectionId != 0) {

                        sql = sql + " AND ( " + where + " ) ";

                    } else {

                        sql = sql + " WHERE ( " + where + " ) ";

                    }

                }

                sql = sql + " GROUP BY SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID];
                sql = sql + " ORDER BY SEC." + PstSection.fieldNames[PstSection.FLD_SECTION];
                System.out.println("-----> " + sql);
                DBHandler.execUpdate(sql);
            }

            sql = "SELECT DISTINCT DEPARTMENT_ID, DEPARTMENT_NAME";
            for (int j = iStartIterate; j <= iEndIterate; j++) {
                sql += ", SUM(MA" + String.valueOf(j) + ") AS SUM" + String.valueOf(j);
            }

            sql += " FROM " + TBL_CANTEEN_SUMMARY_REPORT
                    + " GROUP BY DEPARTMENT_ID ORDER BY DEPARTMENT_NAME";

            DBResultSet dbrs = DBHandler.execQueryResult(sql);
            ResultSet rsl = dbrs.getResultSet();

            while (rsl.next()) {
                MonthlySummary msv = new MonthlySummary();
                msv.setDepartmentName(rsl.getString("DEPARTMENT_NAME"));

                for (int j = iStartIterate; j <= iEndIterate; j++) {
                    switch (j) {
                        case 1:
                            msv.setDate1(rsl.getInt("SUM1"));
                            break;
                        case 2:
                            msv.setDate2(rsl.getInt("SUM2"));
                            break;
                        case 3:
                            msv.setDate3(rsl.getInt("SUM3"));
                            break;
                        case 4:
                            msv.setDate4(rsl.getInt("SUM4"));
                            break;
                        case 5:
                            msv.setDate5(rsl.getInt("SUM5"));
                            break;
                        case 6:
                            msv.setDate6(rsl.getInt("SUM6"));
                            break;
                        case 7:
                            msv.setDate7(rsl.getInt("SUM7"));
                            break;
                        case 8:
                            msv.setDate8(rsl.getInt("SUM8"));
                            break;
                        case 9:
                            msv.setDate9(rsl.getInt("SUM9"));
                            break;
                        case 10:
                            msv.setDate10(rsl.getInt("SUM10"));
                            break;
                        case 11:
                            msv.setDate11(rsl.getInt("SUM11"));
                            break;
                        case 12:
                            msv.setDate12(rsl.getInt("SUM12"));
                            break;
                        case 13:
                            msv.setDate13(rsl.getInt("SUM13"));
                            break;
                        case 14:
                            msv.setDate14(rsl.getInt("SUM14"));
                            break;
                        case 15:
                            msv.setDate15(rsl.getInt("SUM15"));
                            break;
                        case 16:
                            msv.setDate16(rsl.getInt("SUM16"));
                            break;
                        case 17:
                            msv.setDate17(rsl.getInt("SUM17"));
                            break;
                        case 18:
                            msv.setDate18(rsl.getInt("SUM18"));
                            break;
                        case 19:
                            msv.setDate19(rsl.getInt("SUM19"));
                            break;
                        case 20:
                            msv.setDate20(rsl.getInt("SUM20"));
                            break;
                        case 21:
                            msv.setDate21(rsl.getInt("SUM21"));
                            break;
                        case 22:
                            msv.setDate22(rsl.getInt("SUM22"));
                            break;
                        case 23:
                            msv.setDate23(rsl.getInt("SUM23"));
                            break;
                        case 24:
                            msv.setDate24(rsl.getInt("SUM24"));
                            break;
                        case 25:
                            msv.setDate25(rsl.getInt("SUM25"));
                            break;
                        case 26:
                            msv.setDate26(rsl.getInt("SUM26"));
                            break;
                        case 27:
                            msv.setDate27(rsl.getInt("SUM27"));
                            break;
                        case 28:
                            msv.setDate28(rsl.getInt("SUM28"));
                            break;
                        case 29:
                            msv.setDate29(rsl.getInt("SUM29"));
                            break;
                        case 30:
                            msv.setDate30(rsl.getInt("SUM30"));
                            break;
                        case 31:
                            msv.setDate31(rsl.getInt("SUM31"));
                    }
                }
                msv.calculateTotal();
                results.add(msv);
            }

            sql = "DELETE FROM " + TBL_CANTEEN_SUMMARY_REPORT;
            DBHandler.execUpdate(sql);

        } catch (Exception error) {
            System.out.println("There are error occur in SessCanteenVisitation : " + error.toString());
        }
        return results;
    }

    // ------------------------- FINISH SUMMARY REPORT ----------------------
    /**
     * untuk mencari data summary meal payment per bulan.
     * @param dStartSelectedDate
     * @param dEndSelectedDate
     * @param lDepartmentOid
     * @param sectionId
     * @param rptType
     * @return
     */
    public Vector getPeriodicPaymentMealReport(Date dStartSelectedDate, Date dEndSelectedDate, long lDepartmentOid, long sectionId, int rptType) {
        Vector results = new Vector();
        try {
            Date dtSrch = new Date(dStartSelectedDate.getTime());
            int iStartIterate = dStartSelectedDate.getDate();
            int iEndIterate = dEndSelectedDate.getDate();

            // memasukkan data ke table yang di anggap sebagai temp data
            for (int j = iStartIterate; j <= iEndIterate; j++) {
                dtSrch.setDate(j);
                //String sql = " SELECT COUNT(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID] + ") AS VISITS " +
                String sql = " SELECT SUM(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION] + ") AS VISITS "
                        + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION + " AS CAN "
                        + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                        + " ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + (lDepartmentOid != 0 ? (" INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT "
                        + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " ") : " ");

                if (lDepartmentOid != 0) {
                    sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                            + " = " + lDepartmentOid;
                }

                if (sectionId != 0) {
                    if (lDepartmentOid != 0) {
                        sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + sectionId;
                    } else {
                        sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + sectionId;
                    }
                }

                Vector vOfWhClause = new Vector();
                if (rptType == REPORT_TYPE_DISPLAY_MORNING_AFTERNOON) {
                    vOfWhClause = genVOfWhClsBaseOnCanSchd("CAN", dtSrch);
                } else {
                    if (rptType == REPORT_TYPE_DISPLAY_NIGHT) {
                        vOfWhClause = genVOfWhClsWithoutCanSchd("CAN", dtSrch);
                    } else {
                        String theDate = com.dimata.util.Formater.formatDate(dtSrch, "yyyy-MM-dd");
                        String strWhereTime = "DATE_FORMAT(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                                + ",\"%Y-%m-%d\")=\"" + theDate + "\"";
                        vOfWhClause.add(strWhereTime);
                    }
                }

                if (vOfWhClause != null && vOfWhClause.size() > 0) {
                    String sTemp = "";
                    for (int i = 0; i < vOfWhClause.size(); i++) {
                        sTemp = sTemp + vOfWhClause.get(i) + " OR ";
                    }

                    if (sTemp != null && sTemp.length() > 3) {
                        sTemp = sTemp.substring(0, sTemp.length() - 3);
                        if (lDepartmentOid == 0 && sectionId == 0) {
                            sql = sql + " WHERE " + sTemp;
                        } else {
                            sql = sql + " AND (" + sTemp + ")";
                        }
                    }
                }

                if (lDepartmentOid != 0) //sql = sql + " GROUP BY DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
                {
                    sql = sql + " ORDER BY DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
                }
                DBResultSet dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while (rs.next()) {
                    Date dtVisits = new Date(dtSrch.getTime());
                    SummaryDailyVisitation summaryDailyVisitation = new SummaryDailyVisitation();
                    //summaryDailyVisitation.setDepartmentName(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                    summaryDailyVisitation.setDate(dtVisits);
                    summaryDailyVisitation.setNumVisits(rs.getInt("VISITS"));
                    //summaryDailyVisitation.setDepartmentOid(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                    results.add(summaryDailyVisitation);
                }
            }

        } catch (Exception error) {
            System.out.println("There are error occur in SessCanteenVisitation : " + error.toString());
        }
        return results;
    }

    /**
     * @Author  Roy Andika
     * @param   dStartSelectedDate
     * @param   dEndSelectedDate
     * @param   lDepartmentOid
     * @param   sectionId
     * @param   canteenScheduleId
     * @param   rptType
     * @Desc    Untuk mendapatkan meal report summary
     * @return
     */
    public Vector getPeriodicPaymentMealReport(Date dStartSelectedDate, Date dEndSelectedDate, long lDepartmentOid,
            long sectionId, String canteenScheduleId[], int rptType) {

        Vector canteenSchedule = new Vector();
        String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE] + " ASC ";

        try {
            canteenSchedule = PstCanteenSchedule.list(0, 0, null, order);
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        Vector results = new Vector();

        try {

            Date dtSrch = new Date(dStartSelectedDate.getTime());
            int iStartIterate = dStartSelectedDate.getDate();
            int iEndIterate = dEndSelectedDate.getDate();

            for (int j = iStartIterate; j <= iEndIterate; j++) {

                dtSrch.setDate(j);

                String sql = " SELECT SUM(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION] + ") AS VISITS "
                        + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION + " AS CAN "
                        + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                        + " ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + (lDepartmentOid != 0 ? (" INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEPT "
                        + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " ") : " ");

                if (lDepartmentOid != 0) {
                    sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                            + " = " + lDepartmentOid;
                }

                if (sectionId != 0) {
                    if (lDepartmentOid != 0) {
                        sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + sectionId;
                    } else {
                        sql = sql + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                                + " = " + sectionId;
                    }
                }

                String where = "";

                if (canteenScheduleId.length > 0) {

                    int mak = 0;

                    for (int i = 0; i < canteenSchedule.size(); i++) {

                        CanteenSchedule canteenSch = new CanteenSchedule();

                        canteenSch = (CanteenSchedule) canteenSchedule.get(i);

                        if (canteenScheduleId[i].compareTo("1") == 0) {

                            if (where.length() <= 0) {

                                where = where + " ( CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN "
                                        + " '" + Formater.formatDate(dtSrch, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSch.getTTimeOpen(), "HH:mm:ss") + "' AND '"
                                        + Formater.formatDate(dtSrch, "yyyy-MM-dd") + " "
                                        + Formater.formatDate(canteenSch.getTTimeClose(), "HH:mm:ss") + "' ) ";

                            } else {

                                where = where + " OR ( CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN "
                                        + " '" + Formater.formatDate(dtSrch, "yyyy-MM-dd") + " " + Formater.formatDate(canteenSch.getTTimeOpen(), "HH:mm:ss") + "' AND '"
                                        + Formater.formatDate(dtSrch, "yyyy-MM-dd") + " "
                                        + Formater.formatDate(canteenSch.getTTimeClose(), "HH:mm:ss") + "' ) ";
                            }

                        }

                        mak++;

                    }

                    if (canteenScheduleId[mak].compareTo("1") == 0) {

                        String whereOutSchedule = timeOutSchedule("CAN", dtSrch);

                        if (where.length() > 0) {

                            where = where + " OR " + whereOutSchedule;

                        } else {

                            where = where + whereOutSchedule;

                        }
                    }

                } else {

                    where = where + " CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " = "
                            + " '" + Formater.formatDate(dtSrch, "yyyy-MM-dd") + " 00:00:00'";

                }

                if (where.length() > 0) {
                    sql = sql + " AND ( " + where + " ) ";
                }

                if (lDepartmentOid != 0) {
                    sql = sql + " ORDER BY DEPT." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
                }

                DBResultSet dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while (rs.next()) {

                    Date dtVisits = new Date(dtSrch.getTime());
                    SummaryDailyVisitation summaryDailyVisitation = new SummaryDailyVisitation();
                    summaryDailyVisitation.setDate(dtVisits);
                    summaryDailyVisitation.setNumVisits(rs.getInt("VISITS"));
                    results.add(summaryDailyVisitation);
                }
            }

        } catch (Exception error) {
            System.out.println("There are error occur in SessCanteenVisitation : " + error.toString());
        } finally {
            DBResultSet.close(null);
        }

        return results;
    }

    // ------ added by edhy for canteen visitation editor ----
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

    public static int getCountSearch(SrcCanteenVisitation srcCanteenVisitation) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        if (srcCanteenVisitation == null) {
            return 0;
        }

        try {
            String sql = " SELECT COUNT(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_ID] + ") "
                    + " FROM "
                    + " " + PstCanteenVisitation.TBL_CANTEEN_VISITATION + " CAN "
                    + " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " WHERE "
                    + " CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String whereClause = "";
            if ((srcCanteenVisitation.getFullname() != null) && (srcCanteenVisitation.getFullname().length() > 0)) {
                Vector vectName = logicParser(srcCanteenVisitation.getFullname());
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

            if ((srcCanteenVisitation.getEmpnumber() != null) && (srcCanteenVisitation.getEmpnumber().length() > 0)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        + " = \"" + srcCanteenVisitation.getEmpnumber() + "\" AND ";
            }

            if (srcCanteenVisitation.getDepartment().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcCanteenVisitation.getDepartment() + " AND ";
            }

            if (srcCanteenVisitation.getPosition().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcCanteenVisitation.getPosition() + " AND ";
            }

            if (srcCanteenVisitation.getSection().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcCanteenVisitation.getSection() + " AND ";
            }

            if (!srcCanteenVisitation.isPeriodCheck()) {
                if ((srcCanteenVisitation.getDatefrom() != null) && (srcCanteenVisitation.getDateto() != null)) {
                    String strDateFrom = Formater.formatDate(srcCanteenVisitation.getDatefrom(), "yyyy-MM-dd");
                    String strDateTo = Formater.formatDate(srcCanteenVisitation.getDateto(), "yyyy-MM-dd");

                    whereClause = whereClause + " CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                            + " BETWEEN \"" + strDateFrom + " 00:00:00\""
                            + " AND \"" + strDateTo + " 23:59:59\" AND ";
                }
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + "1 = 1";
                sql = sql + " AND " + whereClause;
            }

            //            System.out.println("SQL count : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int num = 0;
            while (rs.next()) {
                num = rs.getInt(1);
            }
            //            System.out.println("\tsearchPresence - getCountSearch = " + num);
            return num;
        } catch (Exception e) {
            System.out.println("\t Exception on getCountSearch : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    public static Vector searchCanteenVisitation(SrcCanteenVisitation srcCanteenVisitation, int start, int recordToGet) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        if (srcCanteenVisitation == null) {
            return new Vector(1, 1);
        }

        try {
            String sql = " SELECT CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_ID]
                    + ", CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                    + ", CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                    + ", CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_STATUS]
                    + ", CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_ANALYZED]
                    + ", CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_TRANSFERRED]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION + " CAN "
                    + " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " WHERE "
                    + " CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String whereClause = "";
            if ((srcCanteenVisitation.getFullname() != null) && (srcCanteenVisitation.getFullname().length() > 0)) {
                Vector vectName = logicParser(srcCanteenVisitation.getFullname());
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

            if ((srcCanteenVisitation.getEmpnumber() != null) && (srcCanteenVisitation.getEmpnumber().length() > 0)) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        + " = \"" + srcCanteenVisitation.getEmpnumber() + "\" AND ";
            }

            if (srcCanteenVisitation.getDepartment().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + srcCanteenVisitation.getDepartment() + " AND ";
            }

            if (srcCanteenVisitation.getPosition().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + srcCanteenVisitation.getPosition() + " AND ";
            }

            if (srcCanteenVisitation.getSection().compareToIgnoreCase("0") > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + srcCanteenVisitation.getSection() + " AND ";
            }

            if (!srcCanteenVisitation.isPeriodCheck()) {
                if ((srcCanteenVisitation.getDatefrom() != null) && (srcCanteenVisitation.getDateto() != null)) {
                    String strDateFrom = Formater.formatDate(srcCanteenVisitation.getDatefrom(), "yyyy-MM-dd");
                    String strDateTo = Formater.formatDate(srcCanteenVisitation.getDateto(), "yyyy-MM-dd");

                    whereClause = whereClause + " CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]
                            + " BETWEEN \"" + strDateFrom + " 00:00:00\""
                            + " AND \"" + strDateTo + " 23:59:59\" AND ";
                }
            }


            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + "1 = 1";
                sql = sql + " AND " + whereClause;
            }

            sql = sql + " ORDER BY CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID] + ", " + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME];

            if (start > 0 || recordToGet > 0) {
                sql = sql + " LIMIT " + start + "," + recordToGet;
            }


            //            System.out.println("sql : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                CanteenVisitation objCanteenVisitation = new CanteenVisitation();
                Employee employee = new Employee();

                objCanteenVisitation.setOID(rs.getLong(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_ID]));
                objCanteenVisitation.setEmployeeId(rs.getLong(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]));
                int y = rs.getDate(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]).getYear();
                int M = rs.getDate(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]).getMonth();
                int d = rs.getDate(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]).getDate();
                int h = rs.getTime(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]).getHours();
                int m = rs.getTime(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]).getMinutes();
                java.util.Date dt = new java.util.Date(y, M, d, h, m);
                objCanteenVisitation.setVisitationTime(dt);
                objCanteenVisitation.setStatus(rs.getInt(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_STATUS]));
                objCanteenVisitation.setAnalyzed(rs.getInt(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_ANALYZED]));
                objCanteenVisitation.setTransferred(rs.getInt(PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_TRANSFERRED]));
                vect.add(objCanteenVisitation);

                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                vect.add(employee);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on searchCanteenVisitation : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }

    public static void main(String args[]) {
        System.out.println("method main started ...");

        SessCanteenVisitation objSessCanteenVisitation = new SessCanteenVisitation();
        long lDepartmentOid = 504404240100922425L;
        long lSectionOid = 0L;
        Date dSelectedDate = new Date();

        //        Vector vOfDailyCanteenReport = objSessCanteenVisitation.getDetailDailyVisitation(504404240100922425L, 0L, new Date(105,3,24));
        //        int iMonthlyCanteenReport = objSessCanteenVisitation.getDetailMealsMA(504404240100922425L, 0L, new Date(105,3,1), new Date(105,3,30));
        //        int iMonthlyCanteenReport = objSessCanteenVisitation.getDetailMealsNight(504404240100922425L, 0L, new Date(105,3,1), new Date(105,3,30));

        Vector vReport = objSessCanteenVisitation.getDetailVisitationReportMonthly(lDepartmentOid, lSectionOid, dSelectedDate, 0);
        if (vReport != null && vReport.size() > 0) {
            int iReportCount = vReport.size();
            for (int i = 0; i < iReportCount; i++) {
                MonthlyDetailVisitation objMonthlyDetailVisitation = (MonthlyDetailVisitation) vReport.get(i);
                System.out.print(objMonthlyDetailVisitation.getEmployeePayroll());
                System.out.print("  " + objMonthlyDetailVisitation.getEmployeeName());
                System.out.print("  " + objMonthlyDetailVisitation.getMa1());
                System.out.print("  " + objMonthlyDetailVisitation.getMa2());
                System.out.print("  " + objMonthlyDetailVisitation.getMa3());
                System.out.print("  " + objMonthlyDetailVisitation.getMa4());
                System.out.print("  " + objMonthlyDetailVisitation.getMa5());
                System.out.print("  " + objMonthlyDetailVisitation.getMa6());
                System.out.print("  " + objMonthlyDetailVisitation.getMa7());
                System.out.print("  " + objMonthlyDetailVisitation.getMa8());
                System.out.print("  " + objMonthlyDetailVisitation.getMa9());
                System.out.print("  " + objMonthlyDetailVisitation.getMa10());
                System.out.print("  " + objMonthlyDetailVisitation.getMa11());
                System.out.print("  " + objMonthlyDetailVisitation.getMa12());
                System.out.print("  " + objMonthlyDetailVisitation.getMa13());
                System.out.print("  " + objMonthlyDetailVisitation.getMa14());
                System.out.print("  " + objMonthlyDetailVisitation.getMa15());
                System.out.print("  " + objMonthlyDetailVisitation.getMa16());
                System.out.print("  " + objMonthlyDetailVisitation.getMa17());
                System.out.print("  " + objMonthlyDetailVisitation.getMa18());
                System.out.print("  " + objMonthlyDetailVisitation.getMa19());
                System.out.print("  " + objMonthlyDetailVisitation.getMa20());
                System.out.print("  " + objMonthlyDetailVisitation.getMa21());
                System.out.print("  " + objMonthlyDetailVisitation.getMa22());
                System.out.print("  " + objMonthlyDetailVisitation.getMa23());
                System.out.print("  " + objMonthlyDetailVisitation.getMa24());
                System.out.print("  " + objMonthlyDetailVisitation.getMa25());
                System.out.print("  " + objMonthlyDetailVisitation.getMa26());
                System.out.print("  " + objMonthlyDetailVisitation.getMa27());
                System.out.print("  " + objMonthlyDetailVisitation.getMa28());
                System.out.print("  " + objMonthlyDetailVisitation.getMa29());
                System.out.print("  " + objMonthlyDetailVisitation.getMa30());
                System.out.print("  " + objMonthlyDetailVisitation.getMa31());
                System.out.println("  " + objMonthlyDetailVisitation.getMaTotal());
            }
        }


        //        Vector vOfWhClause = objSessCanteenVisitation.genVOfWhClsWithoutCanSchd("EMP",new Date());
        //        if(vOfWhClause!=null && vOfWhClause.size()>0)
        //        {
        //            int iWhClauseCount = vOfWhClause.size();
        //            for(int i=0; i<vOfWhClause.size(); i++)
        //            {
        //                System.out.println(i + " : " + vOfWhClause.get(i));
        //            }
        //        }

        System.out.println("method main finish ...");
    }

    /**
     * @Author  Roy Andika
     * @param   start
     * @param   end
     * @param   sch
     * @param   nominal
     * @return
     */
    public static Vector summaryDept(Date startDt, Date endDt, String sch[], double nominal[]) {

        DBResultSet dbrs = null;

        int i_start = 0;
        int i_end   = 0;

        try {
            i_start = startDt.getDate();
        } catch (Exception E) {
            System.out.println("[exception] " + E.toString());
        }

        try {
            i_end = endDt.getDate();
        } catch (Exception E) {
            System.out.println("[exception] " + E.toString());
        }

        Vector divDept = new Vector();

        divDept = getDivDepartment();

        /* looping sebanyak department */
        for (int loopDept = 0; loopDept < divDept.size(); loopDept++) {

            DivDepartment divDepartment = new DivDepartment();
            divDepartment = (DivDepartment) divDept.get(loopDept);

            long departmenId = divDepartment.getDepartmentId();














        }





        Date dtSrch = new Date(startDt.getTime());

        int loopMx = 0;

        loopMx = PstPresence.DATEDIFF(endDt, startDt);

        Date dtProces = startDt;

        int loop = 0;

        while (loop < loopMx) {

            Date nextProcess = (Date) dtProces.clone();
            nextProcess.setDate(nextProcess.getDate() + 1);

            try {

                String sql = "INSERT INTO " + TBL_CANTEEN_SUMMARY_REPORT
                        + " (DEPARTMENT_ID, DEPARTMENT_NAME, MA" + String.valueOf(loop) + ") "
                        + " SELECT SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                        + " ,SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                        + " ,SUM(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION] + ") "
                        + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION + " AS CAN "
                        + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                        + " ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + " INNER JOIN " + PstSection.TBL_HR_SECTION + " AS SEC "
                        + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID];


                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();


            } catch (Exception E) {
            }


            dtProces = nextProcess;
            loop++;

        }













        for (int i = i_start; i <= i_end; i++) {

            try {

                dtSrch.setDate(i);

                String sql = "INSERT INTO " + TBL_CANTEEN_SUMMARY_REPORT
                        + " (DEPARTMENT_ID, DEPARTMENT_NAME, MA" + String.valueOf(i) + ") "
                        + " SELECT SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                        + " ,SEC." + PstSection.fieldNames[PstSection.FLD_SECTION]
                        + " ,SUM(CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION] + ") "
                        + " FROM " + PstCanteenVisitation.TBL_CANTEEN_VISITATION + " AS CAN "
                        + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                        + " ON CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + " INNER JOIN " + PstSection.TBL_HR_SECTION + " AS SEC "
                        + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID];


                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while (rs.next()) {
                }

            } catch (Exception E) {
                System.out.println("[exception] " + E.toString());
            } finally {
                DBResultSet.close(dbrs);
            }

        }


        return null;
    }


    /**
     * @Author  Roy Andika
     * @param   departmentId
     * @param   dSelectedDate
     * @param   canteenScheduleId
     * @return
     */
    private static int getDayVisitation(long departmentId, Date dSelectedDate, CanteenSchedule objCanteenSchedule){

        String sql = "";
        DBResultSet dbrs = null;
        
        try{

            sql = "SELECT SUM(CAN."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION]+") FROM "+
                    PstCanteenVisitation.TBL_CANTEEN_VISITATION+" AS CAN INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP ON "+
                    " CAN."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]+" = EMP."+
                    PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" WHERE EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+
                    departmentId;

            String where = "";

            if(objCanteenSchedule.getOID() != 0){

                if(objCanteenSchedule.getTTimeClose() != null && objCanteenSchedule.getTTimeClose() != null){
                    sql = sql + " AND ( CAN." + PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME] + " BETWEEN "
                        + " '" + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " " + Formater.formatDate(objCanteenSchedule.getTTimeOpen(), "HH:mm:ss") + "' AND '"
                        + Formater.formatDate(dSelectedDate, "yyyy-MM-dd") + " "
                        + Formater.formatDate(objCanteenSchedule.getTTimeClose(), "HH:mm:ss") + "' ) ";
                }

            }
            
            sql = sql + " AND "+where;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            int sum = 0;

            while(rs.next()){

                sum = 0;

                sum = rs.getInt(sum);

            }

            return sum;
            
        }catch(Exception E){
            System.out.println("[exception] "+E.toString());
        }finally{
            DBResultSet.close(dbrs);
        }

        return 0;
    }

    /**
     * @Author  Roy Andika
     * @Desc    Untuk mendapatkan list meal     
     * @param   startDt
     * @param   endDt
     * @param   sch
     * @param   nominal
     * @return
     */
    public static Vector getListSummaryReport(Date startDt, Date endDt, String sch[]){

        Vector canteenSchedule = new Vector();
        String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE] + " ASC ";

        try {
            canteenSchedule = PstCanteenSchedule.list(0, 0, null, order);
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        try {

            Vector divDept = new Vector();

            divDept = getDivDepartment();

            Vector result = new Vector();

            /* looping sebanyak department */
            for (int loopDept = 0; loopDept < divDept.size(); loopDept++){

                try{
                    
                    DivDepartment divDepartment = new DivDepartment();
                    divDepartment = (DivDepartment) divDept.get(loopDept);

                    SumReportDepartment sumReportDepartment = new SumReportDepartment();
                    sumReportDepartment.setDivisionId(divDepartment.getDivisionId());
                    sumReportDepartment.setDivision(divDepartment.getDivision());
                    sumReportDepartment.setDepartmentId(divDepartment.getDepartmentId());
                    sumReportDepartment.setDepartment(divDepartment.getDepartment());

                    int loopMx = 0;

                    loopMx = PstPresence.DATEDIFF(endDt, startDt);

                    Date dtProces = startDt;

                    Vector visitByDate = new Vector();

                    int loop = 0;

                    while (loop < loopMx) {

                        int visitBySch[] = new int[loopMx];

                        int idx = 0;
                        for(int loopCntSch = 0; loopCntSch < canteenSchedule.size() ; loopCntSch++){

                            if (sch[loopCntSch].compareTo("1") == 0) {

                                CanteenSchedule objCnteenSchedule = new CanteenSchedule();
                                objCnteenSchedule = (CanteenSchedule)canteenSchedule.get(loopCntSch);

                                visitBySch[idx] = getDayVisitation(divDepartment.getDivisionId(), dtProces, objCnteenSchedule);

                                idx++;

                            }                            

                        }                        

                        visitByDate.add(visitBySch);

                        Date nextProcess = (Date) dtProces.clone();
                        nextProcess.setDate(nextProcess.getDate() + 1);
                        dtProces = nextProcess;


                    }

                    sumReportDepartment.setVisitByDate(visitByDate);

                    result.add(sumReportDepartment);

                }catch(Exception E){
                    System.out.println("[exception] "+E.toString());
                }
            }

            return result;
            
        } catch (Exception E) {
            System.out.println("[exception] " + E.toString());
        }

        return null;
    }

    /**
     * @Author  : Roy Andika
     * @Desc    : untuk mendapatkan division dan department
     * @return
     */
    private static Vector getDivDepartment() {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT DIVI." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + " as div_id, "
                    + " DIVI." + PstDivision.fieldNames[PstDivision.FLD_DIVISION] + " as div, "
                    + " DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " as dep_id, "
                    + " DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] + " as dep "
                    + " FROM " + PstDivision.TBL_HR_DIVISION + " DIVI INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEP ON DIVI."
                    + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + " = DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]
                    + " ORDER BY DIVI." + PstDivision.fieldNames[PstDivision.FLD_DIVISION] + ",DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Vector result = new Vector();

            while (rs.next()) {

                DivDepartment divDepartment = new DivDepartment();
                divDepartment.setDivisionId(rs.getLong("div_id"));
                divDepartment.setDivision(rs.getString("div"));
                divDepartment.setDepartmentId(rs.getLong("dep_id"));
                divDepartment.setDepartment(rs.getString("dep"));
                result.add(divDepartment);
            }

            return result;

        } catch (Exception E) {
            System.out.println("[exception] " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return null;

    }


    /**
     * @Author  Roy Andika
     * @Desc    Untuk mendapatkan summary report per department
     * @param   start
     * @param   end
     * @param   schIdx
     * @return
     */    
    public static Vector getSummaryReportDepartment(Date start,Date end,String schIdx){

        Vector canteenSchedule = new Vector();
        String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE] + " ASC ";

        try {
            canteenSchedule = PstCanteenSchedule.list(0, 0, null, order);
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }
        
        try{

            Vector ResultAll = new Vector();

            for(int i = 0 ; i < canteenSchedule.size() ; i++){

                CanteenSchedule objCanteenSchedule = new CanteenSchedule();
                objCanteenSchedule = (CanteenSchedule)canteenSchedule.get(i);

                Vector result = new Vector();

                if (schIdx.compareTo("1") == 0) {

                    DBResultSet dbrs = null;

                    try{
               
                        String sql = "SELECT DIVI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" as division_id,"+
                            " DIVI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" as division,"+
                            " DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" as department_id,"+
                            " DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" as department,"+
                            " DATE(CANT."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+") as visitation_date,"+
                            " SUM(CANT."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION]+" as sum) FROM "+
                            PstCanteenVisitation.TBL_CANTEEN_VISITATION+" CANT INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP ON "+
                            " CANT."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]+"=EMP."+
                            PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" LEFT JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEPT ON EMP."+
                            PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"=DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                            " INNER JOIN "+PstDivision.TBL_HR_DIVISION+" DIVI ON DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+" = "+
                            " DIVI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+
                            " WHERE (DATE(CANT."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+") BETWEEN '"+
                            Formater.formatDate(start,"yyyy-MM-dd")+"' AND '"+Formater.formatDate(end,"yyyy-MM-dd")+"' ) AND (TIME(CANT."+
                            PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_ID]+") BETWEEN '"+objCanteenSchedule.getTTimeOpen()+"' AND '"+
                            objCanteenSchedule+"') GROUP BY DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+",DATE(CANT."+
                            PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+") ORDER BY DIVI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+
                            ",DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+",DATE(CANT."+
                            PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+")";

                        dbrs = DBHandler.execQueryResult(sql);
                        ResultSet rs = dbrs.getResultSet();
                   
                        while(rs.next()){

                            SumReportDepartment sumReportDepartment = new SumReportDepartment();
                            sumReportDepartment.setDivisionId(rs.getLong("division_id"));
                            sumReportDepartment.setDivision(rs.getString("division"));
                            sumReportDepartment.setDepartmentId(rs.getLong("department_id"));
                            sumReportDepartment.setDepartment(rs.getString("department"));
                            sumReportDepartment.setCanteenVisitation(rs.getDate("visitation_date"));
                            sumReportDepartment.setSummary(rs.getInt("sum"));

                            result.add(sumReportDepartment);

                        }
                    }catch(Exception E){
                        System.out.println("[exception] "+E.toString());
                    }finally{
                        DBResultSet.close(dbrs);
                    }
                }

                ResultAll.add(result);
            }

        }catch(Exception E){
            System.out.println("[exception] "+E.toString());
        }
        return null;

    }


    /**
     * @Author  Roy Andika
     * @param   start
     * @param   end
     * @param   schIdx
     * @Desc    Untuk membuat list sch report canteen dari mesin acces
     * @return
     */
     public static Vector getSummaryReportDepartmentAccess(Date start,Date end,String schIdx[]){

        String strMachineCanteen = String.valueOf(PstSystemProperty.getValueByName("CANTEEN_TMA_NO"));

        StringTokenizer strTokenizerCnt = new StringTokenizer(strMachineCanteen, ",");
        
        String whereNoMch = "";
        while (strTokenizerCnt.hasMoreTokens()) {
            WatcherMachine watcherMachine = new WatcherMachine();
            String machineNum = watcherMachine.getMachineAlias(strTokenizerCnt.nextToken());
            
            if(whereNoMch.length()<=0){
                whereNoMch = whereNoMch + " ( "+Att2000.Fld_InOut_SENSORID+" = '"+machineNum+"'";
            }else{
                whereNoMch = whereNoMch + " OR "+Att2000.Fld_InOut_SENSORID+" = '"+machineNum+"'";

            }
        }

        if(whereNoMch.length()>0){
            whereNoMch = whereNoMch + ") ";
        }
        Vector canteenSchedule = new Vector();
        String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE] + " ASC ";

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            db_access = "";
            System.out.println("Exception " + e.toString());
        }

        try {
            canteenSchedule = PstCanteenSchedule.list(0, 0, null, order);
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        try{

            Vector ResultAll = new Vector();

            int max = 0;
            
            for(int i = 0 ; i < canteenSchedule.size() ; i++){

                CanteenSchedule objCanteenSchedule = new CanteenSchedule();
                objCanteenSchedule = (CanteenSchedule)canteenSchedule.get(i);

                Vector result = new Vector();

                if (schIdx[i].compareTo("1") == 0) {

                    Connection cn = null;
                    Statement stm = null;

                    try{

                        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                        String cs = "jdbc:odbc:" + db_access;
                        cn = DriverManager.getConnection(cs);
                 
                        String sql = "SELECT DISTINCTROW Format$(["+Att2000.Tbl_CheckInOut+"].["+Att2000.Fld_InOut_CHECKTIME+"],'yyyy-MM-dd') AS [CheckTimeByDay]"+
                               ",Count("+Att2000.Tbl_CheckInOut+"."+Att2000.Fld_InOut_USERID+") AS CountOfUSERID "+
                               " FROM "+Att2000.Tbl_CheckInOut+
                               " WHERE ( "+Att2000.Tbl_CheckInOut+"."+Att2000.Fld_InOut_STATUS+" IS NULL OR "+
                               Att2000.Tbl_CheckInOut+"."+Att2000.Fld_InOut_STATUS+" = '" + Att2000.Status_Not_Transfered + "' ) ";


                        if(whereNoMch.length()>0){
                            sql = sql + " AND "+ whereNoMch;
                        }

                        sql = sql +" AND Format$(["+Att2000.Tbl_CheckInOut+"].["+Att2000.Fld_InOut_CHECKTIME+"],'yyyy-MM-dd') BETWEEN '"+
                               Formater.formatDate(start,"yyyy-MM-dd")+"' AND '"+Formater.formatDate(end,"yyyy-MM-dd")+"' AND "+
                               " Format$(["+Att2000.Tbl_CheckInOut+"].["+Att2000.Fld_InOut_CHECKTIME+"],'HH:mm:dd') BETWEEN '"+
                               objCanteenSchedule.getTTimeOpen()+"' AND '"+objCanteenSchedule.getTTimeClose()+"' GROUP BY"+
                               " Format$(["+Att2000.Tbl_CheckInOut+"].["+Att2000.Fld_InOut_CHECKTIME+"],'yyyy-MM-dd') ORDER BY "+
                               " Format$(["+Att2000.Tbl_CheckInOut+"].["+Att2000.Fld_InOut_CHECKTIME+"],'yyyy-MM-dd')";

                        stm = cn.createStatement();
                        ResultSet rs = stm.executeQuery(sql);                       

                        while(rs.next()){

                            SumReportDepartment sumReportDepartment = new SumReportDepartment();
                            sumReportDepartment.setDivisionId(0);
                            sumReportDepartment.setDivision("Unknown");
                            sumReportDepartment.setDepartmentId(0);
                            sumReportDepartment.setDepartment("Unknown");

                            /* Canteen Visitation */
                            sumReportDepartment.setCanteenVisitation(rs.getDate("CheckTimeByDay"));
                            sumReportDepartment.setSummary(rs.getInt("CountOfUSERID"));

                            result.add(sumReportDepartment);

                        }
                    }catch(Exception E){
                        System.out.println("[exception] "+E.toString());
                    }finally{
                        try {
                            stm.close();
                            cn.close();
                        } catch (Exception e) {
                            System.out.println("EXCEPTION " + e.toString());
                        }
                    }
                    
                }
                max++;
                ResultAll.add(result);
            }

            Vector result = new Vector();

            if (schIdx[max].compareTo("1") == 0) {

                    String whereOutSchedule = timeOutScheduleAccess();                    

                    Connection cn = null;
                    Statement stm = null;

                    try{

                        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                        String cs = "jdbc:odbc:" + db_access;
                        cn = DriverManager.getConnection(cs);

                        String sql = "SELECT DISTINCTROW Format$(["+Att2000.Tbl_CheckInOut+"].["+Att2000.Fld_InOut_CHECKTIME+"],'yyyy-MM-dd') AS [CheckTimeByDay]"+
                               ",Count("+Att2000.Tbl_CheckInOut+"."+Att2000.Fld_InOut_USERID+") AS CountOfUSERID "+
                               " FROM "+Att2000.Tbl_CheckInOut+
                               " WHERE ( "+Att2000.Tbl_CheckInOut+"."+Att2000.Fld_InOut_STATUS+" IS NULL OR "+
                               Att2000.Tbl_CheckInOut+"."+Att2000.Fld_InOut_STATUS+" = '" + Att2000.Status_Not_Transfered + "' ) AND ( "+
                               " Format$(["+Att2000.Tbl_CheckInOut+"].["+Att2000.Fld_InOut_CHECKTIME+"],'yyyy-MM-dd') BETWEEN '"+
                               Formater.formatDate(start,"yyyy-MM-dd")+"' AND '"+Formater.formatDate(end,"yyyy-MM-dd")+"' )";

                        if(whereNoMch.length()>0){
                            sql = sql +" AND "+ whereNoMch;
                        }
                        
                        if(whereOutSchedule.length()>0){
                            sql = sql + " AND ( "+ whereOutSchedule+" ) ";
                        }

                        sql = sql + " GROUP BY "+
                             " Format$(["+Att2000.Tbl_CheckInOut+"].["+Att2000.Fld_InOut_CHECKTIME+"],'yyyy-MM-dd') ORDER BY "+
                               " Format$(["+Att2000.Tbl_CheckInOut+"].["+Att2000.Fld_InOut_CHECKTIME+"],'yyyy-MM-dd')";

                        stm = cn.createStatement();
                        ResultSet rs = stm.executeQuery(sql);                       

                        while(rs.next()){

                            SumReportDepartment sumReportDepartment = new SumReportDepartment();
                            sumReportDepartment.setDivisionId(0);
                            sumReportDepartment.setDivision("Unknown");
                            sumReportDepartment.setDepartmentId(0);
                            sumReportDepartment.setDepartment("Unknown");

                            /* Canteen Visitation */
                            sumReportDepartment.setCanteenVisitation(rs.getDate("CheckTimeByDay"));
                            sumReportDepartment.setSummary(rs.getInt("CountOfUSERID"));

                            result.add(sumReportDepartment);

                        }

                        ResultAll.add(result);

                    }catch(Exception E){
                        System.out.println("[exception] "+E.toString());
                    }finally{
                        try {
                            stm.close();
                            cn.close();
                        } catch (Exception e) {
                            System.out.println("EXCEPTION " + e.toString());
                        }
                    }

             }
            
            return ResultAll;
            
       }catch(Exception E){
            System.out.println("[exception] "+E.toString());
        }
        return null;


     }
    /**
     * @Author  Roy Andika
     * @param   start
     * @param   end
     * @param   schIdx
     * @return
     */
    public static Vector getSummaryReportDepartment(Date start,Date end,String schIdx[]){

        Vector canteenSchedule = new Vector();
        String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE] + " ASC ";

        try {
            canteenSchedule = PstCanteenSchedule.list(0, 0, null, order);
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        try{

            Vector ResultAll = new Vector();

            int max = 0;
            
            for(int i = 0 ; i < canteenSchedule.size() ; i++){

                CanteenSchedule objCanteenSchedule = new CanteenSchedule();
                objCanteenSchedule = (CanteenSchedule)canteenSchedule.get(i);

                Vector result = new Vector();

                if (schIdx[i].compareTo("1") == 0) {

                    DBResultSet dbrs = null;

                    try{

                        String sql = "SELECT DIVI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" as division_id,"+
                            " DIVI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" as division,"+
                            " DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" as department_id,"+
                            " DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" as department,"+
                            " DATE(CANT."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+") as visitation_date,"+
                            " SUM(CANT."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION]+") as sum FROM "+
                            PstCanteenVisitation.TBL_CANTEEN_VISITATION+" CANT INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP ON "+
                            " CANT."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]+"=EMP."+
                            PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" LEFT JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEPT ON EMP."+
                            PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"=DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                            " INNER JOIN "+PstDivision.TBL_HR_DIVISION+" DIVI ON DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+" = "+
                            " DIVI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+
                            " WHERE (DATE(CANT."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+") BETWEEN '"+
                            Formater.formatDate(start,"yyyy-MM-dd")+"' AND '"+Formater.formatDate(end,"yyyy-MM-dd")+"' ) AND (TIME(CANT."+
                            PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+") BETWEEN '"+objCanteenSchedule.getTTimeOpen()+"' AND '"+
                            objCanteenSchedule.getTTimeClose()+"') GROUP BY DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+",DATE(CANT."+
                            PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+") ORDER BY DIVI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+
                            ",DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+",DATE(CANT."+
                            PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+")";

                        dbrs = DBHandler.execQueryResult(sql);
                        ResultSet rs = dbrs.getResultSet();

                        while(rs.next()){

                            SumReportDepartment sumReportDepartment = new SumReportDepartment();
                            sumReportDepartment.setDivisionId(rs.getLong("division_id"));
                            sumReportDepartment.setDivision(rs.getString("division"));
                            sumReportDepartment.setDepartmentId(rs.getLong("department_id"));
                            sumReportDepartment.setDepartment(rs.getString("department"));

                            /* Canteen Visitation */
                            sumReportDepartment.setCanteenVisitation(rs.getDate("visitation_date"));
                            sumReportDepartment.setSummary(rs.getInt("sum"));

                            result.add(sumReportDepartment);

                        }
                    }catch(Exception E){
                        System.out.println("[exception] "+E.toString());
                    }finally{
                        DBResultSet.close(dbrs);
                    }
                    
                }
                max++;
                ResultAll.add(result);
            }

            Vector result = new Vector();

            if (schIdx[max].compareTo("1") == 0) {

                    String whereOutSchedule = timeOutSchedule("CANT");
                    DBResultSet dbrs = null;

                    try{

                        String sql = "SELECT DIVI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" as division_id,"+
                            " DIVI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" as division,"+
                            " DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" as department_id,"+
                            " DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" as department,"+
                            " DATE(CANT."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+") as visitation_date,"+
                            " SUM(CANT."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_NUM_OF_VISITATION]+") as sum FROM "+
                            PstCanteenVisitation.TBL_CANTEEN_VISITATION+" CANT INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP ON "+
                            " CANT."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]+"=EMP."+
                            PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" LEFT JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEPT ON EMP."+
                            PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"=DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                            " INNER JOIN "+PstDivision.TBL_HR_DIVISION+" DIVI ON DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+" = "+
                            " DIVI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+
                            " WHERE (DATE(CANT."+PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+") BETWEEN '"+
                            Formater.formatDate(start,"yyyy-MM-dd")+"' AND '"+Formater.formatDate(end,"yyyy-MM-dd")+"' ) " ;

                        if(whereOutSchedule.length()>0){
                            sql = sql + " AND ( "+ whereOutSchedule +" ) ";
                        }

                        sql = sql + " GROUP BY DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+",DATE(CANT."+
                            PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+") ORDER BY DIVI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]+
                            ",DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+",DATE(CANT."+
                            PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+")";

                        dbrs = DBHandler.execQueryResult(sql);
                        ResultSet rs = dbrs.getResultSet();

                        while(rs.next()){

                            SumReportDepartment sumReportDepartment = new SumReportDepartment();
                            sumReportDepartment.setDivisionId(rs.getLong("division_id"));
                            sumReportDepartment.setDivision(rs.getString("division"));
                            sumReportDepartment.setDepartmentId(rs.getLong("department_id"));
                            sumReportDepartment.setDepartment(rs.getString("department"));

                            /* Canteen Visitation */
                            sumReportDepartment.setCanteenVisitation(rs.getDate("visitation_date"));
                            sumReportDepartment.setSummary(rs.getInt("sum"));

                            result.add(sumReportDepartment);

                        }

                        ResultAll.add(result);

                    }catch(Exception E){
                        System.out.println("[exception] "+E.toString());
                    }finally{
                        DBResultSet.close(dbrs);
                    }

             }
            
            return ResultAll;

        }catch(Exception E){
            System.out.println("[exception] "+E.toString());
        }
        return null;

    }

}


