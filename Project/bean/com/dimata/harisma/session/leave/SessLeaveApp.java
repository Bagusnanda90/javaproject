/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.leave;

import com.dimata.harisma.entity.attendance.AlStockManagement;
import com.dimata.harisma.entity.attendance.DpStockManagement;
import com.dimata.harisma.entity.attendance.PstAlStockManagement;
import com.dimata.harisma.entity.leave.PstSpecialUnpaidLeaveTaken;
import com.dimata.harisma.entity.leave.SpecialUnpaidLeaveTaken;
import com.dimata.harisma.entity.attendance.PstAlStockTaken;
import com.dimata.harisma.entity.attendance.AlStockTaken;
import com.dimata.harisma.entity.attendance.LeaveCheckTakenDateFinish;
import com.dimata.harisma.entity.attendance.LlStockTaken;
import com.dimata.harisma.entity.attendance.DpStockTaken;
import com.dimata.harisma.entity.attendance.ExcuseCheckTaken;
import com.dimata.harisma.entity.attendance.LeaveApplicationSummary;
import com.dimata.harisma.entity.attendance.PstDpStockManagement;
import com.dimata.harisma.entity.attendance.PstDpStockTaken;
import com.dimata.harisma.entity.attendance.PstLLStockManagement;
import com.dimata.harisma.entity.attendance.PstLeaveStock;
import com.dimata.harisma.entity.attendance.PstLlStockTaken;
import com.dimata.harisma.entity.attendance.PstSpecialLeave;
import com.dimata.harisma.entity.attendance.PstSpecialLeaveStock;
import com.dimata.harisma.entity.attendance.PstSpecialLeaveTaken;
import com.dimata.harisma.entity.attendance.SpecialLeave;
import com.dimata.harisma.entity.attendance.SpecialLeaveStock;
import com.dimata.harisma.entity.attendance.SpecialLeaveTaken;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.LeaveTarget;
import com.dimata.harisma.entity.masterdata.LeaveTargetDetail;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstLeaveTarget;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.recruitment.PstStaffRequisition;
import com.dimata.harisma.entity.leave.*;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import com.dimata.harisma.entity.search.SrcLeaveManagement;
import com.dimata.harisma.entity.leave.ListSpecialLeave;
import com.dimata.harisma.entity.masterdata.Company;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.HashTblPeriod;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstCompany;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.payroll.PayGeneral;
import com.dimata.harisma.session.attendance.SessLeaveManagement;

/**
 *
 * @author artha
 */
public class SessLeaveApp {

    public static int TAKEN = 0;
    public static int TOBETAKEN = 1;
    
    public static int UNPAID_LEAVE  = 0;
    public static int SPECIAL_LEAVE = 1;

    /**
     * SPECIAL LEAVE
     */
    //Mencari Unpaid Leave per year
    public static Vector listUnpaidLeave(Date dateYear) {
        Vector vList = new Vector(1, 1);

        DBResultSet dbrs = null;
        try {
            String sql = "SELECT SL." + PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_SPECIAL_LEAVE_ID] + " , SL." + PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_REMARK] + " , SL." + PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_UNPAID_REASON] + " , EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " , EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " , EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " , DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] + " , POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION] + " FROM " + PstSpecialLeave.TBL_HR_SPECIAL_LEAVE + " AS SL " + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " ON SL." + PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID];

            //Untuk order dan group data
            sql += " ORDER BY " + " DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " ,POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION] + " ,EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " ,SL." + PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_REQUEST_DATE];

            System.out.println("\tSessLLAppMain.countLLAppMain : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;

            Date dateStart = new Date(dateYear.getYear(), 0, 1);
            Date endStart = new Date(dateYear.getYear(), 11, 31);

            while (rs.next()) {
                SpecialLeave sp = new SpecialLeave();
                sp.setOID(rs.getLong(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_SPECIAL_LEAVE_ID]));
                sp.setOtherRemarks(rs.getString(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_REMARK]));
                sp.setUnpaidLeaveReason(rs.getString(PstSpecialLeave.fieldNames[PstSpecialLeave.FLD_UNPAID_REASON]));

                Employee emp = new Employee();
                emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                emp.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));

                Department dep = new Department();
                dep.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));

                Position pos = new Position();
                pos.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));

                Vector vSpStock = new Vector(1, 1);
                vSpStock = getSpecialLeaveStockValUnpaid(sp.getOID(), dateStart, endStart);
                if (vSpStock.size() > 0) {
                    Vector vTemp = new Vector();
                    vTemp.add(sp);
                    vTemp.add(emp);
                    vTemp.add(dep);
                    vTemp.add(pos);
                    vTemp.add(vSpStock);

                    vList.add(vTemp);
                }
            }

            rs.close();
            return vList;

        } catch (Exception e) {
            System.out.println("Exception :" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }

    /**
     * Mencari Special Leave Stock berdasarkan Special Leave ID
     * @param specieal leave id
     * @return Vector :
     * 1. String taken qty
     * 2. Date start taken
     * 2. Date end taken
     * @autor artha
     */
    private static Vector getSpecialLeaveStockValUnpaid(long spOID, Date start, Date end) {
        Vector vSpStock = new Vector(1, 1);
        String strUnpaidSy = PstSystemProperty.getValueByName("UNPAID_LEAVE_SYMBOL");
        long scheduleId = 0;
        if (strUnpaidSy != null) {
            scheduleId = PstScheduleSymbol.getScheduleId(strUnpaidSy);
        }

        String whereClause = PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SPECIAL_LEAVE_ID] + " = " + spOID + " AND " + PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_TAKEN_DATE] + ">= '" + Formater.formatDate(start, "yyyy-MM-dd") + "'" + " AND " + PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_TAKEN_DATE] + "<= '" + Formater.formatDate(end, "yyyy-MM-dd") + "'" + " AND " + PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SYMBOL_ID] + " = " + scheduleId;

        Vector vList = PstSpecialLeaveStock.list(0, 0, whereClause, PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_TAKEN_DATE] + " ASC ");
        if (vList.size() > 0) {
            int countTaken = vList.size();
            Date dateStart = new Date();
            Date dateEnd = new Date();
            for (int i = 0; i < vList.size(); i++) {
                SpecialLeaveStock spStock = new SpecialLeaveStock();
                spStock = (SpecialLeaveStock) vList.get(i);
                if (i == 0) {
                    dateStart = spStock.getTakenDate();
                }
                if (i == (vList.size() - 1)) {
                    dateEnd = spStock.getTakenDate();
                }
            }
            vSpStock.add(String.valueOf(countTaken));
            vSpStock.add(dateStart);
            vSpStock.add(dateEnd);
        }
        return vSpStock;
    }

    /**
     * Mencari Data Dp, AL dan LL
     */
    public static Vector listEndMonthReport(Date periode) {
        Vector vList = new Vector(1, 1);
        Vector vDep = new Vector(1, 1);
        vDep = PstDepartment.list(0, 0, null, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
        Date nextPeriod = new Date(periode.getYear(), periode.getMonth() + 1, 1);

        for (int i = 0; i < vDep.size(); i++) {
            Vector vTemp = new Vector(1, 1);
            Department dep = new Department();
            dep = (Department) vDep.get(i);

            //Mencari jml Employee per dept
            String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + dep.getOID();
            int iEmp = PstEmployee.getCount(whereClause);

            ///////// DP
            int dpPrevBlcBrt = countPrevBalanceDP(dep.getOID(), periode);
            int dpPrevTakenBrt = countPrevTakenDP(dep.getOID(), periode);
            int dpNextBlcBrt = countPrevBalanceDP(dep.getOID(), nextPeriod);
            int dpNextTakenBrt = countPrevTakenDP(dep.getOID(), nextPeriod);
            int dpPrevBlc = dpPrevBlcBrt - dpPrevTakenBrt;
            int dpCurrEnt = dpNextBlcBrt - dpPrevBlcBrt;
            int dpCurrTaken = dpNextTakenBrt - dpPrevTakenBrt;

            Vector vDP = new Vector(1, 1);
            vDP.add("" + dpPrevBlc);
            vDP.add("" + dpCurrEnt);
            vDP.add("" + dpCurrTaken);

            ///////// AL
            int alPrevBlcBrt = countPrevBalanceAL(dep.getOID(), periode);
            int alPrevTakenBrt = countPrevTakenAL(dep.getOID(), periode);
            int alNextBlcBrt = countPrevBalanceAL(dep.getOID(), nextPeriod);
            int alNextTakenBrt = countPrevTakenAL(dep.getOID(), nextPeriod);
            int alPrevBlc = alPrevBlcBrt - alPrevTakenBrt;
            int alCurrEnt = alNextBlcBrt - alPrevBlcBrt;
            int alCurrTaken = alNextTakenBrt - alPrevTakenBrt;

            Vector vAL = new Vector(1, 1);
            vAL.add("" + alPrevBlc);
            vAL.add("" + alCurrEnt);
            vAL.add("" + alCurrTaken);

            ///////// AL
            int llPrevBlcBrt = countPrevBalanceLL(dep.getOID(), periode);
            int llPrevTakenBrt = countPrevTakenLL(dep.getOID(), periode);
            int llNextBlcBrt = countPrevBalanceLL(dep.getOID(), nextPeriod);
            int llNextTakenBrt = countPrevTakenLL(dep.getOID(), nextPeriod);
            int llPrevBlc = llPrevBlcBrt - llPrevTakenBrt;
            int llCurrEnt = llNextBlcBrt - llPrevBlcBrt;
            int llCurrTaken = llNextTakenBrt - llPrevTakenBrt;

            Vector vLL = new Vector(1, 1);
            vLL.add("" + llPrevBlc);
            vLL.add("" + llCurrEnt);
            vLL.add("" + llCurrTaken);

            ////MENAMPUNG DATA
            vTemp.add(dep);
            vTemp.add("" + iEmp);
            vTemp.add(vDP);
            vTemp.add(vAL);
            vTemp.add(vLL);

            vList.add(vTemp);

        }
        return vList;
    }

    //Mencari jml dp balance prev
    private static int countPrevBalanceDP(long depOID, Date periode) {
        int count = 0;
        DBResultSet dbrs = null;
        Date dateStart = new Date(periode.getYear(), periode.getMonth(), 1);
        try {
            String sql = "SELECT DISTINCT SUM(MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + ")" + " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS MAN " + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " ON MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + depOID + " AND MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE] + " >= '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " AND MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] + " < '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " GROUP BY DEP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];

            //    System.out.println("\tSessLLAppMain.countLLAppMain : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

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

    //Mencari jml dp taken prev
    private static int countPrevTakenDP(long depOID, Date periode) {
        int count = 0;
        DBResultSet dbrs = null;
        Date dateStart = new Date(periode.getYear(), periode.getMonth(), 1);
        try {
            String sql = "SELECT DISTINCT SUM(DETAIL." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + ")" + " FROM " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS DETAIL " + " INNER JOIN " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS MAN " + " ON MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] + " = DETAIL." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " ON MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + depOID + " AND MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE] + " >= '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " AND MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] + " < '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " AND DETAIL." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + " < '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " GROUP BY DEP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];

            //    System.out.println("\tSessLLAppMain.countLLAppMain : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

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

    //Mencari jml al balance prev
    private static int countPrevBalanceAL(long depOID, Date periode) {
        int count = 0;
        DBResultSet dbrs = null;
        Date dateStart = new Date(periode.getYear(), periode.getMonth(), 1);
        try {
            String sql = "SELECT DISTINCT SUM(MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + ")" + " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS MAN " + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " ON MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + depOID + " AND MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + " < '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " GROUP BY DEP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];

            //    System.out.println("\tSessLLAppMain.countLLAppMain : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

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

    //Mencari jml al taken prev
    private static int countPrevTakenAL(long depOID, Date periode) {
        int count = 0;
        DBResultSet dbrs = null;
        Date dateStart = new Date(periode.getYear(), periode.getMonth(), 1);
        try {
            String sql = "SELECT DISTINCT SUM(DETAIL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + ")" + " FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS DETAIL " + " INNER JOIN " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS MAN " + " ON MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] + " = DETAIL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID] + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " ON MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + depOID + " AND MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + " < '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " AND DETAIL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " < '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " GROUP BY DEP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];

            //    System.out.println("\tSessLLAppMain.countLLAppMain : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

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

    //Mencari jml al balance prev
    private static int countPrevBalanceLL(long depOID, Date periode) {
        int count = 0;
        DBResultSet dbrs = null;
        Date dateStart = new Date(periode.getYear(), periode.getMonth(), 1);
        try {
            String sql = "SELECT DISTINCT SUM(MAN." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY] + ")" + " FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " AS MAN " + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " ON MAN." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + depOID + " AND MAN." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EXPIRED_DATE] + " >= '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " AND MAN." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] + " < '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " GROUP BY DEP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];

            //    System.out.println("\tSessLLAppMain.countLLAppMain : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

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

    //Mencari jml al taken prev
    private static int countPrevTakenLL(long depOID, Date periode) {
        int count = 0;
        DBResultSet dbrs = null;
        Date dateStart = new Date(periode.getYear(), periode.getMonth(), 1);
        try {
            String sql = "SELECT DISTINCT SUM(DETAIL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY] + ")" + " FROM " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS DETAIL " + " INNER JOIN " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " AS MAN " + " ON MAN." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] + " = DETAIL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID] + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " ON MAN." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + depOID + " AND MAN." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EXPIRED_DATE] + " >= '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " AND MAN." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] + " < '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " AND DETAIL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " < '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " GROUP BY DEP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];

            //    System.out.println("\tSessLLAppMain.countLLAppMain : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

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

    private static LeaveTargetDetail getTargetDetail(Vector vLeaveAndDetail, long depOID) {
        LeaveTargetDetail targetDetail = new LeaveTargetDetail();
        try {
            Vector listTemp = (Vector) vLeaveAndDetail.get(0);
            Vector listTargetDetail = (Vector) listTemp.get(1);
            for (int i = 0; i < listTargetDetail.size(); i++) {
                LeaveTargetDetail lTargetDetailTemp = new LeaveTargetDetail();
                lTargetDetailTemp = (LeaveTargetDetail) listTargetDetail.get(i);
                if (lTargetDetailTemp.getDeparmentId() == depOID) {
                    return lTargetDetailTemp;
                }
            }
        } catch (Exception ex) {
        }
        return targetDetail;
    }

    /**
     * Mencari employeeyang mengambil Special Leave Pada Bulan Tertentu
     */
    public static Vector listSpecialLeave(Date period, long schSymbol) {
        Vector vList = new Vector(1, 1);
        DBResultSet dbrs = null;
        Date dateStart = new Date(period.getYear(), period.getMonth(), 1);
        Date dateNextStart = new Date(period.getYear(), period.getMonth(), 1);
        dateNextStart.setMonth(dateNextStart.getMonth() + 1);
        dateNextStart.setDate(dateNextStart.getDate() - 1);
        try {
            String sql = "SELECT DISTINCT TAKEN." + PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_EMPLOYEE_ID] + " ,EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " ,EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " ,DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] + " ,SEC." + PstSection.fieldNames[PstSection.FLD_SECTION] + " FROM " + PstSpecialLeaveTaken.TBL_HR_SPECIAL_LEAVE_TAKEN + " AS TAKEN" + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" + " ON TAKEN." + PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP" + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " INNER JOIN " + PstSection.TBL_HR_SECTION + " AS SEC" + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = SEC." + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + " WHERE TAKEN." + PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_SYMBOL_ID] + " = " + schSymbol + " AND TAKEN." + PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_TAKEN_DATE] + " >= '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " AND TAKEN." + PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_TAKEN_DATE] + " <= '" + Formater.formatDate(dateNextStart, "yyyy-MM-dd") + "'";

            //    System.out.println("\tSessLLAppMain.countLLAppMain : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vTemp = new Vector(1, 1);

                Employee emp = new Employee();
                Department dep = new Department();
                Section sec = new Section();

                emp.setOID(rs.getLong(1));
                emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));

                dep.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));

                sec.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));

                Vector vDetail = new Vector(1, 1);
                Vector vSP = new Vector(1, 1);
                vDetail = listDetailTaken(period, schSymbol, emp.getOID());
                vSP = listSpecialLeave(vDetail, schSymbol, emp.getOID());

                vTemp.add(emp);
                vTemp.add(dep);
                vTemp.add(sec);
                vTemp.add(vDetail);
                vTemp.add(vSP);

                vList.add(vTemp);

            }
            rs.close();
            return vList;
        } catch (Exception e) {
            return new Vector();
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    private static Vector listDetailTaken(Date period, long schSymbol, long empId) {
        Vector vList = new Vector(1, 1);
        Date dateStart = new Date(period.getYear(), period.getMonth(), 1);
        Date dateNextStart = new Date(period.getYear(), period.getMonth(), 1);
        dateNextStart.setMonth(dateNextStart.getMonth() + 1);
        dateNextStart.setDate(dateNextStart.getDate() - 1);
        String whereClause = PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_EMPLOYEE_ID] + " = " + empId + " AND " + PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_SYMBOL_ID] + " = " + schSymbol + " AND " + PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_TAKEN_DATE] + " >= '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " AND " + PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_TAKEN_DATE] + " <= '" + Formater.formatDate(dateNextStart, "yyyy-MM-dd") + "'";
        Vector vTemp = new Vector(1, 1);
        vTemp = PstSpecialLeaveTaken.list(0, 0, whereClause, PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_TAKEN_DATE]);
        for (int i = 0; i < vTemp.size(); i++) {
            SpecialLeaveTaken spTaken = new SpecialLeaveTaken();
            spTaken = (SpecialLeaveTaken) vTemp.get(i);
            Date dateTaken = spTaken.getTakenDate();
            vList.add(dateTaken);
        }
        return vList;
    }

    private static Vector listSpecialLeave(Vector vDateTaken, long schSymbol, long empId) {
        Vector vList = new Vector();
        for (int i = 0; i < vDateTaken.size(); i++) {
            Date dateTaken = (Date) vDateTaken.get(i);
            String whereClause = PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_EMPLOYEE_ID] + " = " + empId + " AND " + PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SYMBOL_ID] + " = " + schSymbol + " AND " + PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_TAKEN_DATE] + " = '" + Formater.formatDate(dateTaken, "yyyy-MM-dd") + "'";
            Vector vTemp = new Vector(1, 1);
            vTemp = PstSpecialLeaveStock.list(0, 0, whereClause, null);
            SpecialLeaveStock spStock = new SpecialLeaveStock();
            if (vTemp.size() > 0) {
                spStock = (SpecialLeaveStock) vTemp.get(0);
                boolean isHad = false;
                for (int j = 0; j < vList.size(); j++) {
                    SpecialLeave sp = (SpecialLeave) vList.get(j);
                    if (sp.getOID() == spStock.getSpecialLeaveId()) {
                        isHad = true;
                    }
                }
                if (!isHad) {
                    SpecialLeave spLeave = new SpecialLeave();
                    try {
                        spLeave = PstSpecialLeave.fetchExc(spStock.getSpecialLeaveId());
                    } catch (Exception ex) {
                    }
                    vList.add(spLeave);
                }
            }
        }
        return vList;
    }

    /*** NEW CONCEPT FOR LEAVE & DP ***/
    /**
     * Mencari Data Dp, AL dan LL
     * author : kartika
     */
    public static Vector listEndMonthReportNew(Date periode) {
        Vector vList = new Vector(1, 1);
        Vector vDep = new Vector(1, 1);
        vDep = PstDepartment.list(0, 0, null, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
        Date nextPeriod = new Date(periode.getYear(), periode.getMonth() + 1, 1);

        for (int i = 0; i < vDep.size(); i++) {
            Vector vTemp = new Vector(1, 1);
            Department dep = new Department();
            dep = (Department) vDep.get(i);

            //Mencari jml Employee per dept
            String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + dep.getOID();
            int iEmp = PstEmployee.getCount(whereClause);

            ///////// DP
            int dpPrevBlcBrt = countPrevBalanceDP(dep.getOID(), periode);
            int dpPrevTakenBrt = countPrevTakenDP(dep.getOID(), periode);
            int dpNextBlcBrt = countPrevBalanceDP(dep.getOID(), nextPeriod);
            int dpNextTakenBrt = countPrevTakenDP(dep.getOID(), nextPeriod);
            int dpPrevBlc = dpPrevBlcBrt - dpPrevTakenBrt;
            int dpCurrEnt = dpNextBlcBrt - dpPrevBlcBrt;
            int dpCurrTaken = dpNextTakenBrt - dpPrevTakenBrt;

            Vector vDP = new Vector(1, 1);
            vDP.add("" + dpPrevBlc);
            vDP.add("" + dpCurrEnt);
            vDP.add("" + dpCurrTaken);

            ///////// AL
            int alPrevBlcBrt = countPrevBalanceALNew(dep.getOID(), periode);
            int alPrevTakenBrt = countPrevTakenAL(dep.getOID(), periode);
            int alNextBlcBrt = countPrevBalanceAL(dep.getOID(), nextPeriod);
            int alNextTakenBrt = countPrevTakenAL(dep.getOID(), nextPeriod);
            int alPrevBlc = alPrevBlcBrt - alPrevTakenBrt;
            int alCurrEnt = alNextBlcBrt - alPrevBlcBrt;
            int alCurrTaken = alNextTakenBrt - alPrevTakenBrt;

            Vector vAL = new Vector(1, 1);
            vAL.add("" + alPrevBlc);
            vAL.add("" + alCurrEnt);
            vAL.add("" + alCurrTaken);

            ///////// AL
            int llPrevBlcBrt = countPrevBalanceLL(dep.getOID(), periode);
            int llPrevTakenBrt = countPrevTakenLL(dep.getOID(), periode);
            int llNextBlcBrt = countPrevBalanceLL(dep.getOID(), nextPeriod);
            int llNextTakenBrt = countPrevTakenLL(dep.getOID(), nextPeriod);
            int llPrevBlc = llPrevBlcBrt - llPrevTakenBrt;
            int llCurrEnt = llNextBlcBrt - llPrevBlcBrt;
            int llCurrTaken = llNextTakenBrt - llPrevTakenBrt;

            Vector vLL = new Vector(1, 1);
            vLL.add("" + llPrevBlc);
            vLL.add("" + llCurrEnt);
            vLL.add("" + llCurrTaken);

            ////MENAMPUNG DATA
            vTemp.add(dep);
            vTemp.add("" + iEmp);
            vTemp.add(vDP);
            vTemp.add(vAL);
            vTemp.add(vLL);

            vList.add(vTemp);

        }
        return vList;
    }

    /** CREATED BY KARTIKA  **/
    //Mencari jml al balance prev
    private static int countPrevBalanceALNew(long depOID, Date periode) {
        int count = 0;
        DBResultSet dbrs = null;
        Date dateStart = new Date(periode.getYear(), periode.getMonth(), 1);
        try {
            String sql = "SELECT DISTINCT SUM(MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] +
                    "+MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_PREV_BALANCE] + ")" + " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS MAN " + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " ON MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + depOID + " AND MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + " < '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " GROUP BY DEP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];

            //    System.out.println("\tSessLLAppMain.countLLAppMain : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

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

    //Mencari jml dp balance prev
    private static int countPrevBalanceDPNew(long depOID, Date periode) {
        int count = 0;
        DBResultSet dbrs = null;
        Date dateStart = new Date(periode.getYear(), periode.getMonth(), 1);
        try {
            String sql = "SELECT DISTINCT SUM(MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + ")" + " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS MAN " + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " ON MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP " + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + depOID + " AND MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE] + " >= '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " AND MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] + " < '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " GROUP BY DEP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];

            //    System.out.println("\tSessLLAppMain.countLLAppMain : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

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

    /**
     * List stock leave dan DP saat ini summary per department
     * @return
     */
    public static Vector listLeaveAndDpStock(long depOID, Date periode, Date nextPeriod) {

        Vector vList = new Vector(1, 1);
        Vector vDep = new Vector(1, 1);
        vDep = PstDepartment.list(0, 0, null, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);


        for (int i = 0; i < vDep.size(); i++) {
            Vector vTemp = new Vector(1, 1);
            Department dep = new Department();
            dep = (Department) vDep.get(i);

            //Mencari jml Employee per dept
            String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + dep.getOID();
            int iEmp = PstEmployee.getCount(whereClause);

            ///////// DP
            int dpPrevBlcBrt = countPrevBalanceDP(dep.getOID(), periode);
            int dpPrevTakenBrt = countPrevTakenDP(dep.getOID(), periode);
            int dpNextBlcBrt = countPrevBalanceDP(dep.getOID(), nextPeriod);
            int dpNextTakenBrt = countPrevTakenDP(dep.getOID(), nextPeriod);
            int dpPrevBlc = dpPrevBlcBrt - dpPrevTakenBrt;
            int dpCurrEnt = dpNextBlcBrt - dpPrevBlcBrt;
            int dpCurrTaken = dpNextTakenBrt - dpPrevTakenBrt;

            Vector vDP = new Vector(1, 1);
            vDP.add("" + dpPrevBlc);
            vDP.add("" + dpCurrEnt);
            vDP.add("" + dpCurrTaken);

            ///////// AL
            int alPrevBlcBrt = countPrevBalanceAL(dep.getOID(), periode);
            int alPrevTakenBrt = countPrevTakenAL(dep.getOID(), periode);
            int alNextBlcBrt = countPrevBalanceAL(dep.getOID(), nextPeriod);
            int alNextTakenBrt = countPrevTakenAL(dep.getOID(), nextPeriod);
            int alPrevBlc = alPrevBlcBrt - alPrevTakenBrt;
            int alCurrEnt = alNextBlcBrt - alPrevBlcBrt;
            int alCurrTaken = alNextTakenBrt - alPrevTakenBrt;

            Vector vAL = new Vector(1, 1);
            vAL.add("" + alPrevBlc);
            vAL.add("" + alCurrEnt);
            vAL.add("" + alCurrTaken);

            ///////// AL
            int llPrevBlcBrt = countPrevBalanceLL(dep.getOID(), periode);
            int llPrevTakenBrt = countPrevTakenLL(dep.getOID(), periode);
            int llNextBlcBrt = countPrevBalanceLL(dep.getOID(), nextPeriod);
            int llNextTakenBrt = countPrevTakenLL(dep.getOID(), nextPeriod);
            int llPrevBlc = llPrevBlcBrt - llPrevTakenBrt;
            int llCurrEnt = llNextBlcBrt - llPrevBlcBrt;
            int llCurrTaken = llNextTakenBrt - llPrevTakenBrt;

            Vector vLL = new Vector(1, 1);
            vLL.add("" + llPrevBlc);
            vLL.add("" + llCurrEnt);
            vLL.add("" + llCurrTaken);

            ////MENAMPUNG DATA
            vTemp.add(dep);
            vTemp.add("" + iEmp);
            vTemp.add(vDP);
            vTemp.add(vAL);
            vTemp.add(vLL);

            vList.add(vTemp);

        }
        return vList;
    }

    /*** NEW CONCEPT FOR LEAVE & DP ***/
    /**
     * Mencari SummaryData Dp, AL dan LL
     * author : kartika
     */
    public static Vector listSumLeaveAndDPStock(){
        Vector vList = new Vector(1, 1);
        Vector vDep = new Vector(1, 1);
        vDep = PstDepartment.list(0, 0, null, PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+" ASC "/*PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]*/);
        //update by satrya 2013-10-25
        Vector vCompany = PstCompany.listAll();
        Vector vDiv = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+" ASC ");
        //Vector vsec = PstSection.list(0, 0, "", PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+" ASC ");
        if(vCompany!=null && vCompany.size()>0){
            for(int idxComp=0;idxComp< vCompany.size();idxComp++){
                Company company = (Company)vCompany.get(idxComp);
                //Vector vDiv = PstDivision.list(0, 0, PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+company.getOID(), PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+" ASC ");
                if(vDiv!=null && vDiv.size()>0){
                   for(int idxDiv=0;idxDiv<vDiv.size();idxDiv++){
                    Division div = (Division)vDiv.get(idxDiv);
                        if(company.getOID()==div.getCompanyId()){
                          //Vector  vDep = PstDepartment.list(0, 0,PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "="+div.getOID(), PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+" ASC ");
                            if(vDep!=null && vDep.size()>0){
                                for (int i = 0; i < vDep.size(); i++){
                                     Department dep = new Department();
                                     dep = (Department) vDep.get(i);
                                   if(div.getOID()==dep.getDivisionId()){
                                       Vector vsection = PstSection.list(0, 0, PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+" = "+dep.getOID() ,PstSection.fieldNames[PstSection.FLD_SECTION]+" ASC ");
                                        boolean loopOnlyDep=false;
                                        if(vsection==null || vsection.size()<1){
                                          loopOnlyDep = true;  
                                        }
                                        for(int sec=0; sec < vsection.size() || loopOnlyDep==true; sec++){
                                            
                                            com.dimata.harisma.entity.masterdata.Section sectionSel = null;
                                            if(!loopOnlyDep){                                                            
                                                sectionSel = (com.dimata.harisma.entity.masterdata.Section) vsection.get(sec);
                                                String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + dep.getOID()+" AND "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = "+PstEmployee.NO_RESIGN;
                                                 int iEmp = PstEmployee.getCount(whereClause); 
                                                 RepItemLeaveAndDp item = new RepItemLeaveAndDp();
                                                item.setCompanyName(company.getCompany());
                                                item.setCompanyOID(company.getOID());

                                                item.setDivisionId(div.getOID());
                                                item.setDivisionName(div.getDivision());

                                                item.setSectionOID(sectionSel.getOID());  
                                                item.setSectionName(sectionSel.getSection());  
                                               
                                                item.setDepName(dep.getDepartment());
                                                item.setDepartmentOID(dep.getOID());
                                                item.setEmpQty(iEmp);
                                                sumDPStocknew(item);
                                                sumALBalance(item);
                                                sumLLBalance(item);
                                                vList.add(item);
                                    
                                            }else{
                                                String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + dep.getOID()+" AND "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = "+PstEmployee.NO_RESIGN;
                                                 int iEmp = PstEmployee.getCount(whereClause); 
                                                 RepItemLeaveAndDp item = new RepItemLeaveAndDp();
                                                item.setCompanyName(company.getCompany());
                                                item.setCompanyOID(company.getOID());

                                                item.setDivisionId(div.getOID());
                                                item.setDivisionName(div.getDivision());

                                                  item.setSectionOID(0);  
                                               item.setSectionName("");  
                                               
                                                item.setDepName(dep.getDepartment());
                                                item.setDepartmentOID(dep.getOID());
                                                item.setEmpQty(iEmp);
                                                sumDPStocknew(item);
                                                sumALBalance(item);
                                                sumLLBalance(item);
                                                vList.add(item);
                                    
                                            }
                                            loopOnlyDep = false;
                                        }
                                       
                                     
                                    
                                    vDep.remove(i);
                                    i = i -1;
                                   }//div.getOID()==dep.getDivisionId()
                                }
                            }
                            vDiv.remove(idxDiv);
                            idxDiv = idxDiv -1;
                        }//company.getOID()==div.getCompanyId()
                   }
                }
            }
        }
        /* update by satrya 2013-10-25
         * for (int i = 0; i < vDep.size(); i++){
            
            Vector vTemp = new Vector(1, 1);
            Department dep = new Department();
            dep = (Department) vDep.get(i);

            //Mencari jml Employee per dept
            String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + dep.getOID()+" AND "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = "+PstEmployee.NO_RESIGN;
            int iEmp = PstEmployee.getCount(whereClause);            

            RepItemLeaveAndDp item = new RepItemLeaveAndDp();
            item.setDepName(dep.getDepartment());
            item.setDepartmentOID(dep.getOID());
            item.setEmpQty(iEmp);
            sumDPStocknew(item);
            sumALBalance(item);
            sumLLBalance(item);
            vList.add(item);
            
        }*/
        return vList;
    }

    /* WICK */
    private static RepItemLeaveAndDp sumDPStocknew(RepItemLeaveAndDp repItem) {

        if (repItem == null || repItem.getDepartmentOID() == 0) {
            return null;
        }

        DBResultSet dbrs = null;

        try {
            String sql = "SELECT DISTINCT SUM(MAN." +
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + ") AS QTY" +
                    ", SUM(MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] +") AS TAKEN" + " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS MAN " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " ON MAN." +
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    " = " + repItem.getDepartmentOID() + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 " +
                    " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                repItem.setDPQty(rs.getFloat("QTY"));
                repItem.setDPTaken(rs.getFloat("TAKEN"));
                //repItem.setDP2BTaken(rs.getInt("TB_TKN"));

            }
            rs.close();
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return repItem;
        }
    }


    /**
     * summary of DP stock per department
     * @param depOID
     * @return
     */
    private static RepItemLeaveAndDp sumDPStock(RepItemLeaveAndDp repItem) {
        
        if (repItem == null || repItem.getDepartmentOID() == 0) {
            return null;
        }
        
        DBResultSet dbrs = null;
        
        try {
            String sql = "SELECT DISTINCT SUM(MAN." + 
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + ") AS QTY" + 
                    ", SUM(MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] + 
                    ") AS TAKEN" + " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS MAN " + 
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " ON MAN." + 
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + 
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + 
                    " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP " + 
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                    " = " + repItem.getDepartmentOID() + " AND MAN." + 
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE] + " >= '" + 
                    Formater.formatDate(new Date(), "yyyy-MM-dd") + "'" + " AND MAN." + 
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] + 
                    " < '" + Formater.formatDate(new Date(), "yyyy-MM-dd") + "'" + 
                    " GROUP BY DEP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                
                repItem.setDPQty(rs.getInt("QTY"));
                repItem.setDPTaken(rs.getInt("TAKEN"));
                
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return repItem;
        }
    }

    /**
     *  Menghitunga summary AL per department
     * @param depOID     
     * @return
     */
    
    private static RepItemLeaveAndDp sumALBalance(RepItemLeaveAndDp repItem) {
        if (repItem == null || repItem.getDepartmentOID() == 0) {
            return null;
        }

        AlStockManagement alStock = new AlStockManagement();
        DBResultSet dbrs = null;
        Date dateStart = new Date();
        try {
            String sql = "SELECT DISTINCT SUM(MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_PREV_BALANCE] + 
                    ") AS PREV" + ", SUM(MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] +
                    ") AS QTY" + ", SUM(MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] +
                    ") AS TAKEN" + " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT +
                    " AS MAN " + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE +
                    " AS EMP " + " ON MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP " +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + repItem.getDepartmentOID() +
                    " AND MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE] + 
                    " < '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" + " AND MAN." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + " = '" + PstAlStockManagement.AL_STS_AKTIF +
                    "' " + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                repItem.setALPrev(rs.getFloat("PREV"));
                repItem.setALQty(rs.getFloat("QTY"));
                repItem.setALTaken(rs.getFloat("TAKEN"));
            }

            rs.close();
        } catch (Exception e) {
            System.out.println(">>>> AL Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return repItem;
        }
    }

    //Mencari jml al balance prev
    private static RepItemLeaveAndDp sumLLBalance(RepItemLeaveAndDp repItem) {
        
        if (repItem == null || repItem.getDepartmentOID() == 0) {
            return null;
        }

        int count = 0;
        DBResultSet dbrs = null;
        Date dateStart = new Date();
        try {
            
            String sql = "SELECT DISTINCT SUM(MAN." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY] + ") AS QTY" 
                    + ", SUM(MAN." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_PREV_BALANCE] + ") AS PREV" 
                    + ", SUM(MAN." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_USED] + ") AS TAKEN" 
                    + " FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " AS MAN " 
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " 
                    + " ON MAN." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] 
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP " 
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] 
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " 
                    + repItem.getDepartmentOID() + " AND MAN." 
                    + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] 
                    + " = '" + PstLLStockManagement.LL_STS_AKTIF + "'" + " AND MAN." 
                    + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED_DATE] 
                    + " < '" + Formater.formatDate(dateStart, "yyyy-MM-dd") + "'" 
                    + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                repItem.setLLPrev(rs.getFloat("PREV"));
                repItem.setLLQty(rs.getFloat("QTY"));
                repItem.setLLTaken(rs.getFloat("TAKEN"));
            }

            rs.close();
            return repItem;
            
        } catch (Exception e) {
            System.out.println("EXCEPTION "+e.toString());
            return null;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    /**
     * 
     * @param departmentId
     * @return
     */
    private static RepItemLeaveAndDp sumALBalance(long departmentId) {
        
        if(departmentId == 0){
            return null;
        }
        
        DBResultSet dbrs = null;
        
        try{
            
            String sql = "SELECT ";
            
        }catch(Exception e){
            System.out.println("EXCEPTION "+e.toString());
            return null;
        } finally {
            DBResultSet.close(dbrs);
        }
        
        return null;
    }
    
    /**
     * @Author    Roy Andika
     * @Desc    : UNTUK MENDAPATKAN LIST AL DIMANA EXPIRED DATE IS NULL
     * @param   departmentId
     * @return
     */
    private static RepItemLeaveAndDp sumALBalance_I(long departmentId) {
        
        if(departmentId == 0){
            return null;
        }
        
        DBResultSet dbrs = null;
        
        try{
            
            //SELECT EMP.full_name,MAN.PREV_BALANCE AS PREV, MAN.AL_QTY AS QTY, MAN.QTY_USED AS TAKEN,MAN.expired_date FROM hr_al_stock_management AS MAN  INNER JOIN hr_employee AS EMP  ON MAN.EMPLOYEE_ID = EMP.EMPLOYEE_ID INNER JOIN hr_department AS DEP ON EMP.DEPARTMENT_ID = DEP.DEPARTMENT_ID
//WHERE EMP.DEPARTMENT_ID = 504404403995456940 AND MAN.AL_STATUS = '0' AND EMP.RESIGNED = 0 and (MAN.expired_date is null or MAN.expired_date );  
            
            String sql = "SELECT MAN."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_PREV_BALANCE]+" AS PREV,"+
                    " MAN."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY]+" AS QTY, "+
                    " MAN."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED]+" AS TAKEN "+
                    " FROM "+PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT+" MAN INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                    " ON MAN."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEPT ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+
                    " DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" WHERE "+
                    " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+departmentId+" AND EMP."+
                    PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = "+PstEmployee.NO_RESIGN;
            
        }catch(Exception e){
            System.out.println("EXCEPTION "+e.toString());
            return null;
        } finally {
            DBResultSet.close(dbrs);
        }
        
        return null;
    }
    
    

    /**
     * List dari detail stock leave dan DP per employee
     * @param srcLeaveManagement
     * @return vector dari RepItemLeaveAndDp
     */
    public static Vector detailLeaveDPStock(SrcLeaveManagement srcLeaveManagement){

        if (srcLeaveManagement == null) {
            return new Vector();
        }

        Vector vRslt = new Vector();

        DBResultSet dbrs = null;
        try {

            String sql = "SELECT DISTINCT AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]+" AS AL_STOCK_ID " +
                    " ,EMPCAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_ENTITLE_FOR_LEAVE]+
                    ",LL."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID]+" AS LL_STOCK_ID " +                    
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AS EMP_OID, EMP." +
                    PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " AS EMP_NUM, EMP." + 
                    PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " AS EMP_NAME, EMP." + 
                    PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " AS EMP_DEP_ID," + 
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " AS EMP_SEC_ID," + 
                    " SUM(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + ") AS DP_QTY, SUM(DP." + 
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] + ") AS DP_TAKEN, DT.DT_TB_TKN AS DP_TB_TKN, " + 
                    " SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + ") AS AL_QTY, SUM(AL." + 
                    PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_PREV_BALANCE] + ") AS AL_PREV,"
                    + " SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] + ") AS AL_TAKEN, AT.AL_TB_TKN AS AL_TB_TKN, " +
                    //UPDATE BY SATRYA 2013-09-23
                    " AL."+ PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED] +","+
                    
                    " (LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY] + ") AS LL_QTY, "
                    + " (LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_PREV_BALANCE] + ") AS LL_PREV, (LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_USED] + ") AS LL_TAKEN,"
                    + " LT.LL_TB_TKN AS LL_TB_TKN, EXPD.EXPD_QTY AS LL_EXPIRED " + 
                    //update by satrya 2013-10-10
                    ",LL."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED] +
                    ",LL."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED_2] +
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " LEFT JOIN hr_view_dp AS DP ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " LEFT JOIN sum_dp_tb_taken_by_emp AS DT ON DT.EMPLOYEE_ID = EMP.EMPLOYEE_ID " +
                    " LEFT JOIN " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS AL ON AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + " = '" + PstAlStockManagement.AL_STS_AKTIF + "' " + " LEFT JOIN sum_al_tb_taken_by_emp AS AT ON AT.EMPLOYEE_ID = EMP.EMPLOYEE_ID" + " LEFT JOIN " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " AS LL ON LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] + " = '" + PstLLStockManagement.LL_STS_AKTIF + "' " + " LEFT JOIN sum_ll_tb_taken_by_emp AS LT ON LT.EMPLOYEE_ID = EMP.EMPLOYEE_ID" +
                    " LEFT JOIN hr_view_sum_stock_expired EXPD on EXPD." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] + "=LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] +
                    //update by satrya 2013-04-11
                    " INNER JOIN "+PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS EMPCAT ON EMPCAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    +" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                    " WHERE " 
                    //update by satrya 2012-08-22 // corected by Kartika 2012-09-04
                    +(srcLeaveManagement.getEmpNum() ==null || srcLeaveManagement.getEmpNum().length() < 1 ? "" : ("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " = '" + srcLeaveManagement.getEmpNum() + "' AND "))+
                    (srcLeaveManagement.getEmpName() ==null || srcLeaveManagement.getEmpName().length() < 1 ? " (1=1) AND " : ("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " like '%" +
                    srcLeaveManagement.getEmpName() + "%' AND ")) + (srcLeaveManagement.getEmpDeptId() != 0 ? ("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + srcLeaveManagement.getEmpDeptId() + " AND ") : "(1=1) AND ")
                    + (srcLeaveManagement.getEmpSectionId() != 0 ? ("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + srcLeaveManagement.getEmpSectionId() + " AND ") : " (1=1) AND ") 
                    + (srcLeaveManagement.getEmpLevelId() != 0 ? ("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = " + srcLeaveManagement.getEmpLevelId() + " AND ") : " (1=1) AND ") 
                    /// Update by Hendra Putu | 2015-02-17 | Description: mebuat pengecualian untuk category employee DW and Trainee
                    + " EMPCAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_ENTITLE_FOR_LEAVE]+" != 0 AND "
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "='" + PstEmployee.NO_RESIGN + "' " + " GROUP BY  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                    + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] /*+ ",EMP." 
                    + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]*/ + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                try {

                    RepItemLeaveAndDp repItem = new RepItemLeaveAndDp();
                    repItem.setDepartmentOID(rs.getLong("EMP_DEP_ID"));
                    repItem.setSectionOID(rs.getLong("EMP_SEC_ID"));
                    repItem.setEmployeeId(rs.getLong("EMP_OID"));
                    repItem.setPayrollNum(rs.getString("EMP_NUM"));
                    repItem.setEmployeeName(rs.getString("EMP_NAME"));
                    repItem.setDPQty(rs.getFloat("DP_QTY"));
                    repItem.setDPTaken(rs.getFloat("DP_TAKEN"));
                    repItem.setDP2BTaken(rs.getFloat("DP_TB_TKN"));

                    repItem.setALPrev(rs.getFloat("AL_PREV"));
                    repItem.setALQty(rs.getFloat("AL_QTY"));
                    repItem.setALTaken(rs.getFloat("AL_TAKEN"));
                    repItem.setAL2BTaken(rs.getFloat("AL_TB_TKN"));
                    //update by satrya 2013-10-10
                    repItem.setALEntitle(rs.getFloat("AL."+ PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED])); 

                    repItem.setLLPrev(rs.getFloat("LL_PREV"));
                    repItem.setLLQty(rs.getFloat("LL_QTY"));
                    repItem.setLLTaken(rs.getFloat("LL_TAKEN"));
                    repItem.setLL2BTaken(rs.getFloat("LL_TB_TKN"));
                    repItem.setLLExpdQty(rs.getFloat("LL_EXPIRED"));
                    repItem.setALStockId(rs.getLong("AL_STOCK_ID"));
                    repItem.setLLStockId(rs.getLong("LL_STOCK_ID"));  
                    //update by satrya 2013-04-11
                    repItem.setStatusKaryawan(rs.getInt(PstEmpCategory.fieldNames[PstEmpCategory.FLD_ENTITLE_FOR_LEAVE]));
                    
                    //update by satrya 2013-10-10
                    repItem.setLLEntitle(rs.getFloat( "LL."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED]));
                    repItem.setLLEntitle2(rs.getFloat("LL."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED_2]));
                    vRslt.add(repItem);

                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return vRslt;
        }
    }
    
    public static Vector detailLeaveDPStockByEmployee(long employeeOid){

        Vector vRslt = new Vector();

        DBResultSet dbrs = null;
        try {

            String sql = "SELECT DISTINCT AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]+" AS AL_STOCK_ID " +
                    ",LL."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID]+" AS LL_STOCK_ID " +                    
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AS EMP_OID, EMP." +
                    PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " AS EMP_NUM, EMP." + 
                    PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " AS EMP_NAME, EMP." + 
                    PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " AS EMP_DEP_ID," + 
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " AS EMP_SEC_ID," + 
                    " SUM(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + ") AS DP_QTY, SUM(DP." + 
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] + ") AS DP_TAKEN, DT.DT_TB_TKN AS DP_TB_TKN, " + 
                    " SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + ") AS AL_QTY, SUM(AL." + 
                    PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_PREV_BALANCE] + ") AS AL_PREV, SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] + ") AS AL_TAKEN, AT.AL_TB_TKN AS AL_TB_TKN, " + " (LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY] + ") AS LL_QTY, (LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_PREV_BALANCE] + ") AS LL_PREV, (LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_USED] + ") AS LL_TAKEN, LT.LL_TB_TKN AS LL_TB_TKN, EXPD.EXPD_QTY AS LL_EXPIRED " + 
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " LEFT JOIN hr_view_dp AS DP ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " LEFT JOIN sum_dp_tb_taken_by_emp AS DT ON DT.EMPLOYEE_ID = EMP.EMPLOYEE_ID " + " LEFT JOIN " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS AL ON AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + " = '" + PstAlStockManagement.AL_STS_AKTIF + "' " + " LEFT JOIN sum_al_tb_taken_by_emp AS AT ON AT.EMPLOYEE_ID = EMP.EMPLOYEE_ID" + " LEFT JOIN " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " AS LL ON LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] + " = '" + PstLLStockManagement.LL_STS_AKTIF + "' " + " LEFT JOIN sum_ll_tb_taken_by_emp AS LT ON LT.EMPLOYEE_ID = EMP.EMPLOYEE_ID" +
                    " LEFT JOIN hr_view_sum_stock_expired EXPD on EXPD." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] + "=LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] +
                    " WHERE "+(employeeOid == 0 ? "" : "  EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+employeeOid+" AND ")+ 
                    " (DP_QTY>0 OR AL_QTY>0 OR LL_QTY>0) " + " GROUP BY  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                try {

                    RepItemLeaveAndDp repItem = new RepItemLeaveAndDp();
                    repItem.setDepartmentOID(rs.getLong("EMP_DEP_ID"));
                    repItem.setSectionOID(rs.getLong("EMP_SEC_ID"));
                    repItem.setEmployeeId(rs.getLong("EMP_OID"));
                    repItem.setPayrollNum(rs.getString("EMP_NUM"));
                    repItem.setEmployeeName(rs.getString("EMP_NAME"));
                    repItem.setDPQty(rs.getFloat("DP_QTY"));
                    repItem.setDPTaken(rs.getFloat("DP_TAKEN"));
                    repItem.setDP2BTaken(rs.getFloat("DP_TB_TKN"));

                    repItem.setALPrev(rs.getFloat("AL_PREV"));
                    repItem.setALQty(rs.getFloat("AL_QTY"));
                    repItem.setALTaken(rs.getFloat("AL_TAKEN"));
                    repItem.setAL2BTaken(rs.getFloat("AL_TB_TKN"));

                    repItem.setLLPrev(rs.getFloat("LL_PREV"));
                    repItem.setLLQty(rs.getFloat("LL_QTY"));
                    repItem.setLLTaken(rs.getFloat("LL_TAKEN"));
                    repItem.setLL2BTaken(rs.getFloat("LL_TB_TKN"));
                    repItem.setLLExpdQty(rs.getFloat("LL_EXPIRED"));
                    repItem.setALStockId(rs.getLong("AL_STOCK_ID"));
                    repItem.setLLStockId(rs.getLong("LL_STOCK_ID"));                      
                    vRslt.add(repItem);

                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return vRslt;
        }
    }

    //update by satrya 2012-10-24
    /**
     * Keterangan : untuk mencari taken date dan finish date
     * @param srcLeaveManagement
     * @param selectDate
     * @param employeId
     * @return 
     */
      public static Vector checkDetailLeaveTakenDate(long employeeId,Date selectDate){
        Vector vRslt = new Vector();

        DBResultSet dbrs = null;
      if(selectDate !=null){
          String sSelectDate = Formater.formatDate(selectDate, "yyyy-MM-dd");
        try {

            String sql = " SELECT AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AS TK "
                    + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF "
                    + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                    + " , \"AL\" AS LEAVE_SYMBOL "
                    + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
                     + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] + " AS OID_DETAIL "
                    + " FROM "+PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
       + " INNER JOIN  "+ PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  ON ( AL."
         + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID] 
         + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
         + " WHERE "+ "\""+ sSelectDate +"\" BETWEEN DATE(AL."
         + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+") AND DATE(AL."
         + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + ") AND LA."
         + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
         + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
  
  + " UNION SELECT DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]  + "  AS TK "
      + ",DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]  + " AS TF "
      + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
      + " , \"DP\" AS LEAVE_SYMBOL "
      + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
       + ",DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]  + " AS OID_DETAIL "
      + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS DP ON (LA." 
   + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
   + " = DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+")" 
   + " WHERE "+ "\""+ sSelectDate +"\" BETWEEN DATE(DP."
   + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+") AND DATE(DP."
   + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+") AND LA."
   + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
                    //update by satrya 2012-11-08
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
     
 + " UNION SELECT LL."+ PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AS TK "
     + ",LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + "  AS TF "
     + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
     + " , \"LL\" AS LEAVE_SYMBOL "
     + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                   //update by satrya 2012-10-31
      + ",LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] + "  AS OID_DETAIL "
     + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS LL ON (LL."
   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]
   + " = LA." +PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] +")" 
   + " WHERE "+ "\""+ sSelectDate +"\" BETWEEN DATE(LL."
   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + ") AND DATE(LL."
   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+" ) AND LA."
   + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
      
 + " UNION SELECT  SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS TK "
   + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF"
   + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
   + " , SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " AS LEAVE_SYMBOL "
   + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //uypdate by satrya 2012-10-31
    + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID] + " AS OID_DETAIL"
   + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
 + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
    + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]+ ")"
    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU."
    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." +PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
    + " WHERE "+ "\""+ sSelectDate +"\" BETWEEN DATE(SU."
    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+") AND DATE(SU."
    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + ") AND LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
    + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                try {
                    LeaveCheckTakenDateFinish leaveCheckTakenDateFinish = new LeaveCheckTakenDateFinish();
                    leaveCheckTakenDateFinish.setTakenDate(DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK")));
                    leaveCheckTakenDateFinish.setFinishDate(DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF")));
                    leaveCheckTakenDateFinish.setEmployeeId(rs.getLong("EMP"));
                    leaveCheckTakenDateFinish.setLeaveSymbol(rs.getString("LEAVE_SYMBOL"));
                     leaveCheckTakenDateFinish.setLeaveAppId(rs.getLong("LPID"));
                     leaveCheckTakenDateFinish.setOidDetailLeave(rs.getLong("OID_DETAIL"));
                    
                    vRslt.add(leaveCheckTakenDateFinish);
                } catch (Exception exc) {
                    System.out.println("Exception checkDetailLeaveTakenDate"+exc);
                }
            }
        
            rs.close();
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return vRslt;
        }
      }// end if
      return vRslt;
    }
      
 /**
  * saat ini belum di gunakan 
  * update by satrya 2013-04-11
  * Untuk mencari eligible yg ada di setiap leave kecuali special
  * @param employeeId
  * @return 
  */
/*public static float getLeaveEligible(long employeeId){
        DBResultSet dbrs = null;
        float eligible =0.0F;
        try {

            String sql = " SELECT "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE]+ " AS  QTY_RESIDUE FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " WHERE "+ PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " =" +employeeId
                    + " AND "+ PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+"="+PstAlStockManagement.AL_STS_AKTIF
+ " UNION  SELECT "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE]+ " AS  QTY_RESIDUE FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " WHERE " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + " = "+employeeId
+ " UNION SELECT "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]+ " AS  QTY_RESIDUE FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " WHERE " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " +employeeId;
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                try {
                    eligible = eligible + rs.getFloat("QTY_RESIDUE");
                    
                } catch (Exception exc) {
                    System.out.println("Exception checkDetailLeaveTakenDate"+exc);
                }
            }
        
            rs.close();
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return eligible;
        }
    
    }*/
      /**
       * Create by satrya 2013-01-08
       * @param employeeId
       * @param selectDateFrom
       * @param selectedDateTo
       * @return 
       */
       public static Vector checkOverLapsLeaveTaken(long employeeId,Date selectDateFrom,Date selectedDateTo){
        Vector vRslt = new Vector();

        DBResultSet dbrs = null;
      if(selectDateFrom !=null && selectedDateTo!=null){
          
        try {

            String sql = " SELECT AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AS TK "
                    + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF "
                    + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                    + " , \"AL\" AS LEAVE_SYMBOL "
                   // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
                     + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] + " AS OID_DETAIL "
                   + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] 
                   + " , AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY "                    
                    + " FROM "+PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
       + " INNER JOIN  "+ PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  ON ( AL."
         + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID] 
         + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
         + " WHERE "
/*+ " ((AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
+ " OR (AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
//hiden by satrya 2013-02-24
+ " AND (AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
   */
       //update by satrya 2013-03-01
       + "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > AL." 
       + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+ " AND AL."
       + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
       + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
       + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
  
  + " UNION SELECT DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]  + "  AS TK "
      + ",DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]  + " AS TF "
      + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
      + " , \"DP\" AS LEAVE_SYMBOL "
     // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
       + ",DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]  + " AS OID_DETAIL "
    + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]                    
    + " , DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY "
      + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS DP ON (LA." 
   + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
   + " = DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+")" 
   + " WHERE "
//update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > DP."
       + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+ " AND DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
//+ " ((DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
//+ " OR (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
//+ " AND (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+"<>"+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") "
//hidden by satrya 2013-02-24
//+ " AND (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
//+ " AND (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+"<>"+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
//                    
//                    + "\""+ sSelectDate +"\" BETWEEN DATE(DP."
//   + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+") AND DATE(DP."
//   + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+") "
                    
   + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
                    //update by satrya 2012-11-08
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
     
 + " UNION SELECT LL."+ PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AS TK "
     + ",LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + "  AS TF "
     + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
     + " , \"LL\" AS LEAVE_SYMBOL "
     //+ ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                   //update by satrya 2012-10-31
      + ",LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] + "  AS OID_DETAIL "
+ " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]                    
+ " , LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY "
     + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS LL ON (LL."
   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]
   + " = LA." +PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] +")" 
   + " WHERE "
                    //update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > LL."
       + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+ " AND LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
//+ " ((LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
//+ " OR (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
//+ " AND (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+"<>"+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") "
//hidden by satrya 2013-02-24
//+ " AND (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
//+ " AND (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+"<>"+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"                   
//                    + "\""+ sSelectDate +"\" BETWEEN DATE(LL."
//   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + ") AND DATE(LL."
//   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+" ) "
   
   + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
      
 + " UNION SELECT  SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS TK "
   + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF"
   + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
   + " , SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " AS LEAVE_SYMBOL "
  // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //uypdate by satrya 2012-10-31
    + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID] + " AS OID_DETAIL "
+ " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]
+ " , SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY "
   + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
 + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
    + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]+ ")"
    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU."
    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." +PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
    + " WHERE "
                    //update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > SU."
       + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+ " AND SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
//+ " ((SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
//+ " OR (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
//+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+"<>"+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") "
//hidden by satrya 2013-02-24
//+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
//+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+"<>"+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"                                       
//                    + "\""+ sSelectDate +"\" BETWEEN DATE(SU."
//    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+") AND DATE(SU."
//    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + ") "
      
    + " AND LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
    + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                try {
                    LeaveCheckTakenDateFinish leaveCheckTakenDateFinish = new LeaveCheckTakenDateFinish();
                    leaveCheckTakenDateFinish.setTakenDate(DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK")));
                    leaveCheckTakenDateFinish.setFinishDate(DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF")));
                    leaveCheckTakenDateFinish.setEmployeeId(rs.getLong("EMP"));
                    leaveCheckTakenDateFinish.setLeaveSymbol(rs.getString("LEAVE_SYMBOL"));
                     leaveCheckTakenDateFinish.setLeaveAppId(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
                        leaveCheckTakenDateFinish.setSubmissionDate(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]));
                     leaveCheckTakenDateFinish.setOidDetailLeave(rs.getLong("OID_DETAIL"));
                     leaveCheckTakenDateFinish.setTakenQty(rs.getFloat("TAKEN_QTY"));
                    
                    vRslt.add(leaveCheckTakenDateFinish);
                } catch (Exception exc) {
                    System.out.println("Exception checkDetailLeaveTakenDate"+exc);
                }
            }
        
            rs.close();
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return vRslt;
        }
      }// end if
      return vRslt;
    }
       
/**
 * Create by satrya 2014-01-27
 * untuk summary Attdance
 * @param employeeId
 * @param selectDateFrom
 * @param selectedDateTo
 * @return 
 */       
public static Vector checkOverLapsLeaveTakenNoTime(long employeeId,Date selectDateFrom,Date selectedDateTo){
        Vector vRslt = new Vector();

        DBResultSet dbrs = null;
      if(selectDateFrom !=null && selectedDateTo!=null){
          
        try {

            String sql = " SELECT AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AS TK "
                    + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF "
                    + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                    + " , \"AL\" AS LEAVE_SYMBOL "
                   // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
                     + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] + " AS OID_DETAIL "
                   + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] 
                   + " , AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY "                    
                    + " FROM "+PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
       + " INNER JOIN  "+ PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  ON ( AL."
         + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID] 
         + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
         + " WHERE "
/*+ " ((AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
+ " OR (AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
//hiden by satrya 2013-02-24
+ " AND (AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
   */
       //update by satrya 2013-03-01
       + "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd 23:59:59")+"\" > AL." 
       + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+ " AND AL."
       + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd 00:00:00")+"\""
       + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
       + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
  
  + " UNION SELECT DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]  + "  AS TK "
      + ",DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]  + " AS TF "
      + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
      + " , \"DP\" AS LEAVE_SYMBOL "
     // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
       + ",DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]  + " AS OID_DETAIL "
    + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]                    
    + " , DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY "
      + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS DP ON (LA." 
   + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
   + " = DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+")" 
   + " WHERE "
//update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd 23:59:59")+"\" > DP."
       + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+ " AND DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd 00:00:00")+"\""
//+ " ((DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
//+ " OR (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
//+ " AND (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+"<>"+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") "
//hidden by satrya 2013-02-24
//+ " AND (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
//+ " AND (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+"<>"+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
//                    
//                    + "\""+ sSelectDate +"\" BETWEEN DATE(DP."
//   + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+") AND DATE(DP."
//   + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+") "
                    
   + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
                    //update by satrya 2012-11-08
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
     
 + " UNION SELECT LL."+ PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AS TK "
     + ",LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + "  AS TF "
     + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
     + " , \"LL\" AS LEAVE_SYMBOL "
     //+ ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                   //update by satrya 2012-10-31
      + ",LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] + "  AS OID_DETAIL "
+ " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]                    
+ " , LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY "
     + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS LL ON (LL."
   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]
   + " = LA." +PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] +")" 
   + " WHERE "
                    //update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd 23:59:59")+"\" > LL."
       + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+ " AND LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd 00:00:00")+"\""
//+ " ((LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
//+ " OR (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
//+ " AND (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+"<>"+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") "
//hidden by satrya 2013-02-24
//+ " AND (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
//+ " AND (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+"<>"+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"                   
//                    + "\""+ sSelectDate +"\" BETWEEN DATE(LL."
//   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + ") AND DATE(LL."
//   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+" ) "
   
   + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
      
 + " UNION SELECT  SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS TK "
   + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF"
   + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
   + " , SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " AS LEAVE_SYMBOL "
  // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //uypdate by satrya 2012-10-31
    + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID] + " AS OID_DETAIL "
+ " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]
+ " , SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY "
   + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
 + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
    + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]+ ")"
    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU."
    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." +PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
    + " WHERE "
                    //update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd 23:59:59")+"\" > SU."
       + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+ " AND SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd 00:00:00")+"\""
//+ " ((SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
//+ " OR (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
//+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+"<>"+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") "
//hidden by satrya 2013-02-24
//+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
//+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+"<>"+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"                                       
//                    + "\""+ sSelectDate +"\" BETWEEN DATE(SU."
//    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+") AND DATE(SU."
//    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + ") "
      
    + " AND LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
    + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                try {
                    LeaveCheckTakenDateFinish leaveCheckTakenDateFinish = new LeaveCheckTakenDateFinish();
                    leaveCheckTakenDateFinish.setTakenDate(DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK")));
                    leaveCheckTakenDateFinish.setFinishDate(DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF")));
                    leaveCheckTakenDateFinish.setEmployeeId(rs.getLong("EMP"));
                    leaveCheckTakenDateFinish.setLeaveSymbol(rs.getString("LEAVE_SYMBOL"));
                     leaveCheckTakenDateFinish.setLeaveAppId(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
                        leaveCheckTakenDateFinish.setSubmissionDate(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]));
                     leaveCheckTakenDateFinish.setOidDetailLeave(rs.getLong("OID_DETAIL"));
                     leaveCheckTakenDateFinish.setTakenQty(rs.getFloat("TAKEN_QTY"));
                    
                    vRslt.add(leaveCheckTakenDateFinish);
                } catch (Exception exc) {
                    System.out.println("Exception checkDetailLeaveTakenDate"+exc);
                }
            }
        
            rs.close();
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return vRslt;
        }
      }// end if
      return vRslt;
    }
public static Vector checkOverLapsLeaveTakenWithStsDocExecute(long employeeId,Date selectDateFrom,Date selectedDateTo){
        Vector vRslt = new Vector();

        DBResultSet dbrs = null;
      if(selectDateFrom !=null && selectedDateTo!=null){
          
        try {

            String sql = " SELECT AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AS TK "
                    + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF "
                    + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                    + " , \"AL\" AS LEAVE_SYMBOL "
                   // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
                     + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] + " AS OID_DETAIL "
                   + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] 
                   + " , AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY "                    
                    + " FROM "+PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
       + " INNER JOIN  "+ PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  ON ( AL."
         + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID] 
         + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
         + " WHERE "
/*+ " ((AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
+ " OR (AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
//hiden by satrya 2013-02-24
+ " AND (AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
   */
       //update by satrya 2013-03-01
       + "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > AL."
       + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+ " AND AL."
       + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
       + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
       + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " = " +PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED
  
  + " UNION SELECT DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]  + "  AS TK "
      + ",DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]  + " AS TF "
      + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
      + " , \"DP\" AS LEAVE_SYMBOL "
     // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
       + ",DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]  + " AS OID_DETAIL "
    + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]                    
    + " , DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY "
      + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS DP ON (LA." 
   + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
   + " = DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+")" 
   + " WHERE "
//update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > DP."
       + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+ " AND DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
//+ " ((DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
//+ " OR (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
//+ " AND (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+"<>"+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") "
//hidden by satrya 2013-02-24
//+ " AND (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
//+ " AND (DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+"<>"+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
//                    
//                    + "\""+ sSelectDate +"\" BETWEEN DATE(DP."
//   + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+") AND DATE(DP."
//   + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+") "
                    
   + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
                    //update by satrya 2012-11-08
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " = " +PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED
     
 + " UNION SELECT LL."+ PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AS TK "
     + ",LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + "  AS TF "
     + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
     + " , \"LL\" AS LEAVE_SYMBOL "
     //+ ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                   //update by satrya 2012-10-31
      + ",LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] + "  AS OID_DETAIL "
+ " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]                    
+ " , LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY "
     + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS LL ON (LL."
   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]
   + " = LA." +PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] +")" 
   + " WHERE "
                    //update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > LL."
       + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+ " AND LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
//+ " ((LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
//+ " OR (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
//+ " AND (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+"<>"+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") "
//hidden by satrya 2013-02-24
//+ " AND (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
//+ " AND (LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+"<>"+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"                   
//                    + "\""+ sSelectDate +"\" BETWEEN DATE(LL."
//   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + ") AND DATE(LL."
//   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+" ) "
   
   + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " = " +PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED
      
 + " UNION SELECT  SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS TK "
   + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF"
   + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
   + " , SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " AS LEAVE_SYMBOL "
  // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //uypdate by satrya 2012-10-31
    + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID] + " AS OID_DETAIL "
+ " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]
+ " , SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY "
   + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
 + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
    + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]+ ")"
    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU."
    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." +PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
    + " WHERE "
                    //update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > SU."
       + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+ " AND SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
//+ " ((SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
//+ " OR (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
//+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+"<>"+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") "
//hidden by satrya 2013-02-24
//+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
//+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+"<>"+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"                                       
//                    + "\""+ sSelectDate +"\" BETWEEN DATE(SU."
//    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+") AND DATE(SU."
//    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + ") "
      
    + " AND LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
    + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " = " +PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED;
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                try {
                    LeaveCheckTakenDateFinish leaveCheckTakenDateFinish = new LeaveCheckTakenDateFinish();
                    leaveCheckTakenDateFinish.setTakenDate(DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK")));
                    leaveCheckTakenDateFinish.setFinishDate(DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF")));
                    leaveCheckTakenDateFinish.setEmployeeId(rs.getLong("EMP"));
                    leaveCheckTakenDateFinish.setLeaveSymbol(rs.getString("LEAVE_SYMBOL"));
                     leaveCheckTakenDateFinish.setLeaveAppId(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
                        leaveCheckTakenDateFinish.setSubmissionDate(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]));
                     leaveCheckTakenDateFinish.setOidDetailLeave(rs.getLong("OID_DETAIL"));
                     leaveCheckTakenDateFinish.setTakenQty(rs.getFloat("TAKEN_QTY"));
                    
                    vRslt.add(leaveCheckTakenDateFinish);
                } catch (Exception exc) {
                    System.out.println("Exception checkDetailLeaveTakenDate"+exc);
                }
            }
        
            rs.close();
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return vRslt;
        }
      }// end if
      return vRslt;
    }
       
       /**
        * create by satrya 2013-04-24
        * @param employeeId
        * @param selectDateFrom
        * @param selectedDateTo
        * @return 
        */
        //hideen sementara public static Vector checkLeaveTaken(String employeeId,Date selectDateFrom,Date selectedDateTo,HashTblPeriod hashTblPeriod){
     public static Hashtable checkLeaveTaken(String employeeId,Date selectDateFrom,Date selectedDateTo){
        
        Hashtable lists = new Hashtable();
        //Vector vRslt = new Vector();
        DBResultSet dbrs = null;
      if(selectDateFrom !=null && selectedDateTo!=null && employeeId!=null && employeeId.length()>0){
          
        try {

            String sql = "select TK,TF,EMP,LEAVE_SYMBOL,TAKEN_QTY from ( SELECT AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY  , AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AS TK "
                    + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF "
                    + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                    + " , \"AL\" AS LEAVE_SYMBOL "
                   // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
                     + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] + " AS OID_DETAIL "
                   + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] 
                    + " FROM "+PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
       + " INNER JOIN  "+ PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  ON ( AL."
         + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID] 
         + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
         + " WHERE "
       + "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > AL."
       + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+ " AND AL."
       + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
       + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " IN (" + employeeId + ")"
       + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
  
  + " UNION SELECT DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY  , DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]  + "  AS TK "
      + ",DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]  + " AS TF "
      + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
      + " , \"DP\" AS LEAVE_SYMBOL "
     // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
       + ",DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]  + " AS OID_DETAIL "
    + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]                    
      + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS DP ON (LA." 
   + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
   + " = DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+")" 
   + " WHERE "
//update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > DP."
       + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+ " AND DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""               
   + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " IN (" + employeeId + ")"
                    //update by satrya 2012-11-08
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
     
 + " UNION SELECT LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY , LL."+ PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AS TK "
     + ",LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + "  AS TF "
     + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
     + " , \"LL\" AS LEAVE_SYMBOL "
     //+ ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                   //update by satrya 2012-10-31
      + ",LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] + "  AS OID_DETAIL "
+ " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]                    
     + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS LL ON (LL."
   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]
   + " = LA." +PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] +")" 
   + " WHERE "
                    //update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > LL."
       + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+ " AND LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
//
   + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " IN (" + employeeId + ")"
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
      
 /*+ " UNION SELECT  SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY , SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS TK "
   + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF"
   + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
   + " , SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " AS LEAVE_SYMBOL "
  // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //uypdate by satrya 2012-10-31
    + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID] + " AS OID_DETAIL "
+ " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]                    
   + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
 + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
    + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]+ ")"
    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU."
    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." +PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
    + " WHERE "
                    //update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > SU."
       + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+ " AND SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
//    
    + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE]+"="+PstLeaveApplication.LEAVE_APPLICATION + " AND  LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " IN (" + employeeId + ")"
    + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED*/+") AS T  "/* update by satrya 2014-02-10 GROUP BY T.LEAVE_SYMBOL,T.EMP*/+"ORDER BY T.EMP,T.LEAVE_SYMBOL ASC";
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
             LeaveApplicationSummary leaveApplicationSummary = new LeaveApplicationSummary();
            long tmpEmployeeId=0;
            while (rs.next()) {
                try {
                    
                    /*LeaveCheckTakenDateFinish leaveCheckTakenDateFinish = new LeaveCheckTakenDateFinish();
                    //leaveCheckTakenDateFinish.setTakenDate(DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK")));
                    //leaveCheckTakenDateFinish.setFinishDate(DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF")));
                    leaveCheckTakenDateFinish.setEmployeeId(rs.getLong("EMP"));
                    leaveCheckTakenDateFinish.setLeaveSymbol(rs.getString("LEAVE_SYMBOL"));
                    leaveCheckTakenDateFinish.setTakenQty(rs.getFloat("TAKEN_QTY")); 
                     //leaveCheckTakenDateFinish.setLeaveAppId(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
                    //    leaveCheckTakenDateFinish.setSubmissionDate(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]));
                     //leaveCheckTakenDateFinish.setOidDetailLeave(rs.getLong("OID_DETAIL"));
                    
                    vRslt.add(leaveCheckTakenDateFinish);*/
                 if(tmpEmployeeId !=rs.getLong("EMP")){
                    tmpEmployeeId = rs.getLong("EMP");
                    leaveApplicationSummary = new LeaveApplicationSummary();
                     Date tmpSelectedTo = selectedDateTo==null?null:Formater.reFormatDate(selectedDateTo, "yyyy-MM-dd 00:00:00");
                    double tDate=0;
                    if(rs.getString("LEAVE_SYMBOL").equalsIgnoreCase("AL")){
                        Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                        if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                        
                        
                      leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                      //leaveApplicationSummary.addSymbolAl(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addJumlahTaken(tDate);
                      
                    }else if(rs.getString("LEAVE_SYMBOL").equalsIgnoreCase("LL")){
                        Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                       if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                      leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                       //leaveApplicationSummary.addSymbolLL(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addJumlahTaken(tDate);
                    }else if(rs.getString("LEAVE_SYMBOL").equalsIgnoreCase("DP")){
                       Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                       if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                      //leaveApplicationSummary.addSymbolDp(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addJumlahTaken(tDate);
                    }
                   
                    leaveApplicationSummary.setEmployeeId(rs.getLong("EMP"));
                }
                else{
                    //Date tmpCutiStart=null;
                    Date tmpSelectedTo = selectedDateTo==null?null:Formater.reFormatDate(selectedDateTo, "yyyy-MM-dd 00:00:00");
                    double tDate=0;
                    if(rs.getString("LEAVE_SYMBOL").equalsIgnoreCase("AL")){
                        Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                         if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                      leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                      //leaveApplicationSummary.addSymbolAl(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addJumlahTaken(tDate);
                      
                    }else if(rs.getString("LEAVE_SYMBOL").equalsIgnoreCase("LL")){
                        Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                         if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                      leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                     // leaveApplicationSummary.addSymbolLL(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addJumlahTaken(tDate);
                    }else if(rs.getString("LEAVE_SYMBOL").equalsIgnoreCase("DP")){
                       Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                         if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                      leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                      //leaveApplicationSummary.addSymbolDp(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addJumlahTaken(tDate);
                    }
                   
                    leaveApplicationSummary.setEmployeeId(rs.getLong("EMP"));
                }
                      //lists.add(overtimeDetail);
                      lists.put(leaveApplicationSummary.getEmployeeId(), leaveApplicationSummary);
                 
               
                } catch (Exception exc) {
                    System.out.println("Exception checkDetailLeaveTakenDate"+exc);
                }
            }
           
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return lists;
        }
      }// end if
      return lists;
    }
     //update by devin 2014-04-02
     public static float getTobeTaken(long oidEmployee,Date dateFrom,Date dateTo){
         float result=0;
         DBResultSet dbrs=null;
       
         try{
             String sql="SELECT AP."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS " + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " , "
                     + " SUM(AT."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] +" ) " + " AS AL_TB_TKN FROM ( " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AT JOIN " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AP ON ((AT." 
                     + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = AP. " + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+")))"+
                     "WHERE ((AP."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " = 0) OR (AP."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " = 1) OR (AP."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " = 2))"+
                     "AND AT."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]+ " = " + oidEmployee + " AND '" + Formater.formatDate(dateFrom, "yyyy-MM-dd 00:00:00") + "' <= AT."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE ] +
                     " AND AT."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE ] + " <= '" + Formater.formatDate(dateTo, "yyyy-MM-dd 23:59:59")+"'";
                      dbrs=DBHandler.execQueryResult(sql);
                      ResultSet rs =dbrs.getResultSet();
                      while(rs.next()){
                        result=rs.getFloat(2);
                      }
         }catch(Exception exc){
            
             
             
         }finally{
             DBResultSet.close(dbrs);
             return result;
         }
         
     }
     
     //UPDATE BY DEVIN 2014-04-03 
     //update by devin 2014-04-02
     public static float getTobeTakenDept(long oidDepartment,Date dateFrom,Date dateTo,Vector section){
         if (oidDepartment == 0 || dateFrom == null) {
            return 0;
        }
         String variable="";
        DBResultSet dbrs = null;
        float tknCurrentAl = 0;
        try {

            String sql = "SELECT ALT." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + "," +
                    " ALT." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + "," +
                    " ALT." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] +
                    " FROM " +
                    PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " ALM LEFT JOIN " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " ALT ON " +
                    " ALM." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] + " = ALT." +
                    PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID] + " INNER JOIN " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " APP " +
                    " ON ALT." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID] +
                    " = APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " INNER JOIN " +
                    PstEmployee.TBL_HR_EMPLOYEE + " EMP ON ALM." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " WHERE '" +
                    Formater.formatDate(dateFrom, "yyyy-MM-dd 00:00:00") + "' <= ALT." +PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AND " +
                    " ALT." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " <= '" + Formater.formatDate(dateTo, "yyyy-MM-dd 23:59:59") + "' AND " +
                    "EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + oidDepartment +
                    " AND APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " IN (" + PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED +","+ PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT +","+ PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE + ")"+
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;
             if(section!=null && section.size()>0){
                sql=sql + " AND " + PstSection.fieldNames[PstSection.FLD_SECTION_ID]+" NOT IN (";
                for(int x=0;x<section.size();x++){
                    int hasil = x-section.size();
                    Section sectionn =(Section)section.get(x);
                    variable+=sectionn.getOID();
                    if(hasil!=-1){
                        variable=variable + ",";
                    }
                }
                sql=sql + variable+")";
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            
// int tknCurrentAl = 0;
            while (rs.next()) {

            float alToBeTaken = rs.getFloat(1);
               
            tknCurrentAl = tknCurrentAl + alToBeTaken;

           
            }

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
             return tknCurrentAl;
        }

      
         
     }
     //update by devin 2014-04-07
     public static float getTobeTakenDeptt(long oidDepartment,Date dateFrom,Date dateTo){
         if (oidDepartment == 0 || dateFrom == null) {
            return 0;
        }

        DBResultSet dbrs = null;
        float tknCurrentAl = 0;
        try {

            String sql = "SELECT ALT." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + "," +
                    " ALT." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + "," +
                    " ALT." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] +
                    " FROM " +
                    PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " ALM LEFT JOIN " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " ALT ON " +
                    " ALM." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] + " = ALT." +
                    PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID] + " INNER JOIN " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " APP " +
                    " ON ALT." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID] +
                    " = APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " INNER JOIN " +
                    PstEmployee.TBL_HR_EMPLOYEE + " EMP ON ALM." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " WHERE '" +
                    Formater.formatDate(dateFrom, "yyyy-MM-dd 00:00:00") + "' <= ALT." +PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AND " +
                    " ALT." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " <= '" + Formater.formatDate(dateTo, "yyyy-MM-dd 23:59:59") + "' AND " +
                    "EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + oidDepartment +
                    " AND APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " IN (" + PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED +","+ PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT +","+ PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE + ")"+
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            
// int tknCurrentAl = 0;
            while (rs.next()) {

            float alToBeTaken = rs.getFloat(1);
               
            tknCurrentAl = tknCurrentAl + alToBeTaken;

           
            }

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
             return tknCurrentAl;
        }

      
         
     }
     
     
       //update by devin 2014-04-02
     public static Hashtable leaveTakenExecute(String employeeId,Date selectDateFrom,Date selectedDateTo){
        
        Hashtable lists = new Hashtable();
        //Vector vRslt = new Vector();
        DBResultSet dbrs = null;
      if(selectDateFrom !=null && selectedDateTo!=null && employeeId!=null && employeeId.length()>0){
          
        try {

            String sql = "select TK,TF,EMP,LEAVE_SYMBOL,TAKEN_QTY from ( SELECT AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY  , AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AS TK "
                    + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF "
                    + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                    + " , \"AL\" AS LEAVE_SYMBOL "
                   // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
                     + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] + " AS OID_DETAIL "
                   + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] 
                    + " FROM "+PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
       + " INNER JOIN  "+ PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  ON ( AL."
         + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID] 
         + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
         + " WHERE "
       + "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > AL."
       + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+ " AND AL."
       + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
       + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " IN (" + employeeId + ")"
       + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " = " +PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED
  
  + " UNION SELECT DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY  , DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]  + "  AS TK "
      + ",DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]  + " AS TF "
      + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
      + " , \"DP\" AS LEAVE_SYMBOL "
     // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
       + ",DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]  + " AS OID_DETAIL "
    + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]                    
      + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS DP ON (LA." 
   + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
   + " = DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+")" 
   + " WHERE "
//update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > DP."
       + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+ " AND DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""               
   + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " IN (" + employeeId + ")"
                    //update by satrya 2012-11-08
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
     
 + " UNION SELECT LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY , LL."+ PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AS TK "
     + ",LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + "  AS TF "
     + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
     + " , \"LL\" AS LEAVE_SYMBOL "
     //+ ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                   //update by satrya 2012-10-31
      + ",LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] + "  AS OID_DETAIL "
+ " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]                    
     + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS LL ON (LL."
   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]
   + " = LA." +PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] +")" 
   + " WHERE "
                    //update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > LL."
       + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+ " AND LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
//
   + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " IN (" + employeeId + ")"
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
      
 /*+ " UNION SELECT  SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_QTY] + " AS TAKEN_QTY , SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS TK "
   + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF"
   + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
   + " , SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " AS LEAVE_SYMBOL "
  // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //uypdate by satrya 2012-10-31
    + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID] + " AS OID_DETAIL "
+ " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]                    
   + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
 + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
    + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]+ ")"
    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU."
    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." +PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
    + " WHERE "
                    //update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > SU."
       + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+ " AND SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
//    
    + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE]+"="+PstLeaveApplication.LEAVE_APPLICATION + " AND  LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " IN (" + employeeId + ")"
    + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED*/+") AS T  "/* update by satrya 2014-02-10 GROUP BY T.LEAVE_SYMBOL,T.EMP*/+"ORDER BY T.EMP,T.LEAVE_SYMBOL ASC";
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
             LeaveApplicationSummary leaveApplicationSummary = new LeaveApplicationSummary();
            long tmpEmployeeId=0;
            while (rs.next()) {
                try {
                    
                    /*LeaveCheckTakenDateFinish leaveCheckTakenDateFinish = new LeaveCheckTakenDateFinish();
                    //leaveCheckTakenDateFinish.setTakenDate(DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK")));
                    //leaveCheckTakenDateFinish.setFinishDate(DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF")));
                    leaveCheckTakenDateFinish.setEmployeeId(rs.getLong("EMP"));
                    leaveCheckTakenDateFinish.setLeaveSymbol(rs.getString("LEAVE_SYMBOL"));
                    leaveCheckTakenDateFinish.setTakenQty(rs.getFloat("TAKEN_QTY")); 
                     //leaveCheckTakenDateFinish.setLeaveAppId(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
                    //    leaveCheckTakenDateFinish.setSubmissionDate(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]));
                     //leaveCheckTakenDateFinish.setOidDetailLeave(rs.getLong("OID_DETAIL"));
                    
                    vRslt.add(leaveCheckTakenDateFinish);*/
                 if(tmpEmployeeId !=rs.getLong("EMP")){
                    tmpEmployeeId = rs.getLong("EMP");
                    leaveApplicationSummary = new LeaveApplicationSummary();
                     Date tmpSelectedTo = selectedDateTo==null?null:Formater.reFormatDate(selectedDateTo, "yyyy-MM-dd 00:00:00");
                    double tDate=0;
                    if(rs.getString("LEAVE_SYMBOL").equalsIgnoreCase("AL")){
                        Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                        if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                        
                        
                      leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                      //leaveApplicationSummary.addSymbolAl(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addJumlahTaken(tDate);
                      
                    }else if(rs.getString("LEAVE_SYMBOL").equalsIgnoreCase("LL")){
                        Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                       if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                      leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                       //leaveApplicationSummary.addSymbolLL(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addJumlahTaken(tDate);
                    }else if(rs.getString("LEAVE_SYMBOL").equalsIgnoreCase("DP")){
                       Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                       if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                      //leaveApplicationSummary.addSymbolDp(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addJumlahTaken(tDate);
                    }
                   
                    leaveApplicationSummary.setEmployeeId(rs.getLong("EMP"));
                }
                else{
                    //Date tmpCutiStart=null;
                    Date tmpSelectedTo = selectedDateTo==null?null:Formater.reFormatDate(selectedDateTo, "yyyy-MM-dd 00:00:00");
                    double tDate=0;
                    if(rs.getString("LEAVE_SYMBOL").equalsIgnoreCase("AL")){
                        Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                         if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                      leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                      //leaveApplicationSummary.addSymbolAl(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addJumlahTaken(tDate);
                      
                    }else if(rs.getString("LEAVE_SYMBOL").equalsIgnoreCase("LL")){
                        Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                         if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                      leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                     // leaveApplicationSummary.addSymbolLL(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addJumlahTaken(tDate);
                    }else if(rs.getString("LEAVE_SYMBOL").equalsIgnoreCase("DP")){
                       Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                         if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                      leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                      //leaveApplicationSummary.addSymbolDp(rs.getString("LEAVE_SYMBOL"));
                      leaveApplicationSummary.addJumlahTaken(tDate);
                    }
                   
                    leaveApplicationSummary.setEmployeeId(rs.getLong("EMP"));
                }
                      //lists.add(overtimeDetail);
                      lists.put(leaveApplicationSummary.getEmployeeId(), leaveApplicationSummary);
                 
               
                } catch (Exception exc) {
                    System.out.println("Exception checkDetailLeaveTakenDate"+exc);
                }
            }
           
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return lists;
        }
      }// end if
      return lists;
    }
     /**
      * create by satrya 2013-04-24
      * mencari excuse taken 
      * @param employeeId
      * @param selectDateFrom
      * @param selectedDateTo
      * @return 
      */
     public static Vector checkExcuseTaken(String employeeId,Date selectDateFrom,Date selectedDateTo){
        Vector vRslt = new Vector();

        DBResultSet dbrs = null;
      if(selectDateFrom !=null && selectedDateTo!=null && employeeId!=null && employeeId.length()>0){
          
        try {

            String sql = " SELECT  SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS TK "
   +", LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_REASON_ID] + " AS REASON_ID "
   + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF"
   + ",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
   + " , SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " AS LEAVE_SYMBOL "
    + ",SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID] + " AS OID_DETAIL "
+ " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]                    
   + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
 + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
    + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]+ ")"
    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU."
    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." +PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
    + " WHERE "
                    //update by satrya 2013-03-01
+ "\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\" > SU."
       + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+ " AND SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+ " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""
//    
    + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE]+"="+PstLeaveApplication.EXCUSE_APPLICATION + " AND  LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " IN (" +  employeeId  +")"
    + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED+ " ORDER BY EMP,LEAVE_SYMBOL ASC";
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                try {
                    ExcuseCheckTaken excuseCheckTaken = new ExcuseCheckTaken();
                    excuseCheckTaken.setTakenDate(DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK")));
                    excuseCheckTaken.setFinishDate(DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF")));
                    excuseCheckTaken.setEmployeeId(rs.getLong("EMP"));
                    excuseCheckTaken.setNoReason(rs.getInt("REASON_ID"));
                    excuseCheckTaken.setLeaveSymbol(rs.getString("LEAVE_SYMBOL"));
                    excuseCheckTaken.setLeaveAppId(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
                    excuseCheckTaken.setSubmissionDate(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]));
                     excuseCheckTaken.setOidDetailLeave(rs.getLong("OID_DETAIL"));
                    
                    vRslt.add(excuseCheckTaken);
                } catch (Exception exc) {
                    System.out.println("Exception checkDetailLeaveTakenDate"+exc);
                }
            }
        
            rs.close();
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return vRslt;
        }
      }// end if
      return vRslt;
    }
       
       /**
        * create by satrya 2013-02-27
        * Keterangan: mencari semua taken date yang telah di pilih
        * @param employeeId
        * @param selectDateFrom
        * @param selectedDateTo
        * @return 
        */
public static Vector getTakenDate(long employeeId,Date selectDateFrom,Date selectedDateTo){
          Vector vtakenDateX = new Vector(); 

        DBResultSet dbrs = null;
      if(selectDateFrom !=null && selectedDateTo!=null){
          
        try {

            String sql = " SELECT * FROM ( "
                    + " SELECT AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AS TK "
                   + " , \"AL\" AS LEAVE_SYMBOL "
                   + " FROM "+PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
       + " INNER JOIN  "+ PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  ON ( AL."
         + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID] 
         + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
         + " WHERE "
+ " (EXTRACT(DAY_HOUR FROM \""+Formater.formatDate(selectDateFrom, " yyyy-MM-dd HH:mm:ss")+"\")"
+ " BETWEEN EXTRACT(DAY_HOUR FROM AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+") AND EXTRACT(DAY_HOUR FROM AL."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]+"))"
+ " AND (EXTRACT(YEAR_MONTH FROM AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+") = EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectDateFrom, " yyyy-MM-dd HH:mm:ss")+"\") "
+ " AND EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectedDateTo, " yyyy-MM-dd HH:mm:ss")+"\"))"
       
       + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
       + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
  
  + " UNION SELECT DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]  + "  AS TK "
      + " , \"DP\" AS LEAVE_SYMBOL "
      + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS DP ON (LA." 
   + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
   + " = DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+")" 
   + " WHERE "
                    
+ " (EXTRACT(DAY_HOUR FROM \""+Formater.formatDate(selectDateFrom, " yyyy-MM-dd HH:mm:ss ")+"\")"
+ " BETWEEN EXTRACT(DAY_HOUR FROM DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+") AND EXTRACT(DAY_HOUR FROM DP."+ PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+"))"
+ " AND (EXTRACT(YEAR_MONTH FROM DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+") = EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss ")+"\") "
+ " AND EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss ")+"\"))"
        
   + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
     
 + " UNION SELECT LL."+ PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AS TK "
     + " , \"LL\" AS LEAVE_SYMBOL "
     + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS LL ON (LL."
   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]
   + " = LA." +PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] +")" 
   + " WHERE "

+ " (EXTRACT(DAY_HOUR FROM \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss ")+"\")"
+ " BETWEEN EXTRACT(DAY_HOUR FROM LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+") AND EXTRACT(DAY_HOUR FROM LL."+ PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+"))"
+ " AND (EXTRACT(YEAR_MONTH FROM LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+") = EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectDateFrom, "HH:mm:ss")+"\") "
+ " AND EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss ")+"\"))"
 
    + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
      
 + " UNION SELECT  SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS TK "
   + " , SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " AS LEAVE_SYMBOL "
   + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
 + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
    + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]+ ")"
    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU."
    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." +PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
    + " WHERE "
+ " (EXTRACT(DAY_HOUR FROM \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss ")+"\")"
+ " BETWEEN EXTRACT(DAY_HOUR FROM SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+") AND EXTRACT(DAY_HOUR FROM SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+"))"
+ " AND (EXTRACT(YEAR_MONTH FROM SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+") = EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss ")+"\") "
+ " AND EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss ")+"\"))"
 
    + " AND LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
    + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED+") AS TAKEN_DATE ";
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                try {
                    LeaveCheckTakenDateFinish leaveCheckTakenDateFinish = new LeaveCheckTakenDateFinish();
                    leaveCheckTakenDateFinish.setTakenDate(DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK")));
                    vtakenDateX.add(leaveCheckTakenDateFinish);
                } catch (Exception exc) {
                    System.out.println("Exception checkDetailLeaveTakenDate"+exc);
                }
            }
        
            rs.close();
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return vtakenDateX;
        }
      }// end if
      return vtakenDateX;
    }
/**
 * create by satrya 2013-02-27
 * Keterangan mencari nilai finish taken date yg paling akhir
 * @param employeeId
 * @param selectDateFrom
 * @param selectedDateTo
 * @return 
 */
public static Vector getFinishTakenDate(long employeeId,Date selectDateFrom,Date selectedDateTo){
          Vector vtakenDateX = new Vector(); 

        DBResultSet dbrs = null;
      if(selectDateFrom !=null && selectedDateTo!=null){
          
        try {

            String sql = " SELECT * FROM ( SELECT AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AS FK "
                   + " , \"AL\" AS LEAVE_SYMBOL "
                    + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] + " AS OID_DETAIL "
                   + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] 
                   + " FROM "+PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
       + " INNER JOIN  "+ PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  ON ( AL."
         + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID] 
         + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
         + " WHERE "
+ " (EXTRACT(DAY_HOUR FROM \""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\")"
+ " BETWEEN EXTRACT(DAY_HOUR FROM AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+") AND EXTRACT(DAY_HOUR FROM AL."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]+"))"
+ " AND (EXTRACT(YEAR_MONTH FROM AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+") = EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\") "
+ " AND EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\"))"
       
       + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
       + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
  
  + " UNION SELECT DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]  + "  AS FK "
      + " , \"DP\" AS LEAVE_SYMBOL "
      + ",DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + " AS OID_DETAIL "
                   + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] 
      + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS DP ON (LA." 
   + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
   + " = DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+")" 
   + " WHERE "
                    
+ " (EXTRACT(DAY_HOUR FROM \""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\")"
+ " BETWEEN EXTRACT(DAY_HOUR FROM DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+") AND EXTRACT(DAY_HOUR FROM DP."+ PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]+"))"
+ " AND (EXTRACT(YEAR_MONTH FROM DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+") = EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\") "
+ " AND EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\"))"
        
   + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
     
 + " UNION SELECT LL."+ PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AS FK "
     + " , \"LL\" AS LEAVE_SYMBOL "
                     + ",LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID] + " AS OID_DETAIL "
                   + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] 
     + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "  
   + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS LL ON (LL."
   + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]
   + " = LA." +PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] +")" 
   + " WHERE "

+ " (EXTRACT(DAY_HOUR FROM \""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\")"
+ " BETWEEN EXTRACT(DAY_HOUR FROM LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+") AND EXTRACT(DAY_HOUR FROM LL."+ PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]+"))"
+ " AND (EXTRACT(YEAR_MONTH FROM LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+") = EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\") "
+ " AND EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\"))"
 
    + " AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
   + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
      
 + " UNION SELECT  SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS FK "
   + " , SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " AS LEAVE_SYMBOL "
     + ",SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID] + " AS OID_DETAIL "
                   + " ,LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+",LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] 
   + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
 + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
    + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]+ ")"
    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU."
    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." +PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
    + " WHERE "
+ " (EXTRACT(DAY_HOUR FROM \""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\")"
+ " BETWEEN EXTRACT(DAY_HOUR FROM SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+") AND EXTRACT(DAY_HOUR FROM SU."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+"))"
+ " AND (EXTRACT(YEAR_MONTH FROM SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+") = EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\") "
+ " AND EXTRACT(YEAR_MONTH FROM \""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\"))"
 
    + " AND LA."
    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" +  employeeId  +"\" "
    + " AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+ " != " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED+") AS TAKEN_DATE ";
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                try {
                    LeaveCheckTakenDateFinish leaveCheckTakenDateFinish = new LeaveCheckTakenDateFinish();
                    leaveCheckTakenDateFinish.setFinishDate(DBHandler.convertDate(rs.getDate("FK"),rs.getTime("FK")));
                    leaveCheckTakenDateFinish.setLeaveAppId(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
                    leaveCheckTakenDateFinish.setSubmissionDate(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]));
                     leaveCheckTakenDateFinish.setOidDetailLeave(rs.getLong("OID_DETAIL")); 
                    vtakenDateX.add(leaveCheckTakenDateFinish);
                } catch (Exception exc) {
                    System.out.println("Exception checkDetailLeaveTakenDate"+exc);
                }
            }
        
            rs.close();
        } catch (Exception e) {
            System.out.println(">>>> Dp Stock" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return vtakenDateX;
        }
      }// end if
      return vtakenDateX;
    }

    public static Vector getListVectTakenAl(long appId) {
        DBResultSet dbrs = null;
        Vector result = new Vector();
        try {
            String sql = "SELECT " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + "," +
                    PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " FROM " +
                    PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN +
                    " WHERE " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID] + "=" +
                    appId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AlStockTaken alStockTaken = new AlStockTaken();
                alStockTaken.setTakenDate(rs.getDate(1));
                alStockTaken.setTakenFinnishDate(rs.getDate(2));
                result.add(alStockTaken);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }
        return null;
    }

    public static Vector getListVectTakenLl(long appId) {
        DBResultSet dbrs = null;
        Vector result = new Vector();
        try {
            String sql = "SELECT " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + "," +
                    PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + " FROM " +
                    PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN +
                    " WHERE " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID] + "=" +
                    appId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                LlStockTaken llStockTaken = new LlStockTaken();
                llStockTaken.setTakenDate(rs.getDate(1));
                llStockTaken.setTakenFinnishDate(rs.getDate(2));
                result.add(llStockTaken);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }
        return null;
    }

    public static Vector getListVectTakenDp(long appId) {
        DBResultSet dbrs = null;
        Vector result = new Vector();
        try {
            String sql = "SELECT " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + "," +
                    PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + " FROM " +
                    PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN +
                    " WHERE " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] + "=" +
                    appId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                DpStockTaken dpStockTaken = new DpStockTaken();
                dpStockTaken.setTakenDate(rs.getDate(1));
                dpStockTaken.setTakenFinnishDate(rs.getDate(2));
                result.add(dpStockTaken);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }
        return null;
    }

    public static Vector getListVectTakenSp(long appId) {
        DBResultSet dbrs = null;
        Vector result = new Vector();
        try {
            String sql = "SELECT " + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + "," +
                    PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " FROM " +
                    PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN +
                    " WHERE " + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID] + "=" +
                    appId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                SpecialUnpaidLeaveTaken specialUnpaidLeaveTaken = new SpecialUnpaidLeaveTaken();
                specialUnpaidLeaveTaken.setTakenDate(rs.getDate(1));
                specialUnpaidLeaveTaken.setTakenFinnishDate(rs.getDate(2));
                result.add(specialUnpaidLeaveTaken);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }
        return null;
    }

    public static Vector getCompareDate(Date strtDate, Date fnsDate, long appId) {

        int interval = getInterValDate(strtDate, fnsDate);

        int intStrtDate = ((int) strtDate.getTime() / (24 * 60 * 60 * 1000));
        int intFnsDate = ((int) fnsDate.getTime() / (24 * 60 * 60 * 1000));

        /*
        String lstDate = "";
        Vector listDate = new Vector();
        Date newDate = new Date();
        newDate = strtDate;
        for(int indxIn = 0 ; indxIn < interval ; indxIn++){
        lstDate = Formater.formatDate(newDate, "yyyy-MM-dd");
        listDate.add(lstDate);
        long tmpDate = newDate.getTime() + (24 * 60 * 60 * 1000);
        newDate = new Date(tmpDate);
        }
         */

        Vector resultAl = getListVectTakenAl(appId);
        Vector resultLl = getListVectTakenLl(appId);
        Vector resultDp = getListVectTakenDp(appId);
        Vector resultSp = getListVectTakenSp(appId);
        Vector oldTaken = new Vector();
        String strOldTaken = "";

        Vector resultNotValid = new Vector();

        for (int indx = 0; indx < resultAl.size(); indx++) {
            AlStockTaken alStockTaken = new AlStockTaken();
            alStockTaken = (AlStockTaken) resultAl.get(indx);

            int intdtDate = ((int) alStockTaken.getTakenDate().getTime() / (24 * 60 * 60 * 1000));
            int intfnDate = ((int) alStockTaken.getTakenFinnishDate().getTime() / (24 * 60 * 60 * 1000));


            if (intStrtDate >= intdtDate && intStrtDate <= intfnDate) {

                resultNotValid.add(alStockTaken);

            } else if (intFnsDate >= intdtDate && intFnsDate <= intfnDate) {

                resultNotValid.add(alStockTaken);

            } else if (intStrtDate < intdtDate && intFnsDate > intfnDate) {

                resultNotValid.add(alStockTaken);
            }
        }

        for (int indxLl = 0; indxLl < resultLl.size(); indxLl++) {
            LlStockTaken llStockTaken = new LlStockTaken();
            llStockTaken = (LlStockTaken) resultLl.get(indxLl);

            int intdtDate = ((int) llStockTaken.getTakenDate().getTime() / (24 * 60 * 60 * 1000));
            int intfnDate = ((int) llStockTaken.getTakenFinnishDate().getTime() / (24 * 60 * 60 * 1000));


            if (intStrtDate >= intdtDate && intStrtDate <= intfnDate) {

                resultNotValid.add(llStockTaken);

            } else if (intFnsDate >= intdtDate && intFnsDate <= intfnDate) {

                resultNotValid.add(llStockTaken);

            } else if (intStrtDate < intdtDate && intFnsDate > intfnDate) {

                resultNotValid.add(llStockTaken);
            }
        }

        for (int indxDp = 0; indxDp < resultDp.size(); indxDp++) {
            DpStockTaken dpStockTaken = new DpStockTaken();
            dpStockTaken = (DpStockTaken) resultDp.get(indxDp);

            int intdtDate = ((int) dpStockTaken.getTakenDate().getTime() / (24 * 60 * 60 * 1000));
            int intfnDate = ((int) dpStockTaken.getTakenFinnishDate().getTime() / (24 * 60 * 60 * 1000));


            if (intStrtDate >= intdtDate && intStrtDate <= intfnDate) {

                resultNotValid.add(dpStockTaken);

            } else if (intFnsDate >= intdtDate && intFnsDate <= intfnDate) {

                resultNotValid.add(dpStockTaken);

            } else if (intStrtDate < intdtDate && intFnsDate > intfnDate) {

                resultNotValid.add(dpStockTaken);
            }
        }

        for (int indxsp = 0; indxsp < resultSp.size(); indxsp++) {
            SpecialUnpaidLeaveTaken specialUnpaidLeaveTaken = new SpecialUnpaidLeaveTaken();
            specialUnpaidLeaveTaken = (SpecialUnpaidLeaveTaken) resultSp.get(indxsp);

            int intdtDate = ((int) specialUnpaidLeaveTaken.getTakenDate().getTime() / (24 * 60 * 60 * 1000));
            int intfnDate = ((int) specialUnpaidLeaveTaken.getTakenFinnishDate().getTime() / (24 * 60 * 60 * 1000));


            if (intStrtDate >= intdtDate && intStrtDate <= intfnDate) {

                resultNotValid.add(specialUnpaidLeaveTaken);

            } else if (intFnsDate >= intdtDate && intFnsDate <= intfnDate) {

                resultNotValid.add(specialUnpaidLeaveTaken);

            } else if (intStrtDate < intdtDate && intFnsDate > intfnDate) {

                resultNotValid.add(specialUnpaidLeaveTaken);
            }
        }

        if (resultNotValid.size() > 0) {
            return resultNotValid;
        } else {
            return null;
        }
    }

    public static int getInterValDate(Date strtDate, Date fnsDate) {
        DBResultSet dbrs = null;
        String strStrDate = Formater.formatDate(strtDate, "yyyy-MM-dd");
        String strFnsDate = Formater.formatDate(fnsDate, "yyyy-MM-dd");
        try {
            String sql = "SELECT DATEDIFF('" + strFnsDate + "','" + strStrDate + "')";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            int sum = 0;
            while (rs.next()) {
                sum = rs.getInt(1) + 1;
            }
            rs.close();
            return sum;
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }
        return 0;
    }

    public static AlStockManagement getStockAlAktif(long employee_id) {

        //DBResultSet dbrs = null;
        ///update by satrya 2012-10-16
        String where = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + "=" + employee_id + " AND " +
                PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + "=" + PstAlStockManagement.AL_STS_AKTIF;

        Vector list = PstAlStockManagement.list(0, 0, where, "");

        if (list!=null && list.size() == 1) {
            return (AlStockManagement) list.get(0);
        }

        return null;
    }

    public static boolean inputTakeAl(Date dateTake, long employee_id, float qty) {

        DBResultSet dbrs = null;

        Date inputDt = dateTake;

        AlStockManagement alStockManagement = getStockAlAktif(employee_id);
        if(alStockManagement==null) return false;
        String frmtEntDt = Formater.formatDate(alStockManagement.getEntitleDate(), "yyyy-MM-dd");

        String nxtYear = getEntNextYear(alStockManagement.getEntitleDate());

        for (int i = 0; i < qty; i++) {

            String frmtTkDt = Formater.formatDate(inputDt, "yyyy-MM-dd");

            try {

                String sql = "SELECT '" + frmtTkDt + "' BETWEEN '" + frmtEntDt + "' AND '" + nxtYear + "'";

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while (rs.next()) {

                    if (rs.getInt(1) == 0) {
                        return true;
                    }
                }

            } catch (Exception e) {
                System.out.println("Exception ::::" + e.toString());
            }

            long tmpDate = inputDt.getTime() + (24 * 60 * 60 * 1000);

            inputDt = new Date(tmpDate);

        }

        return false;
    }

    public static String getEntNextYear(Date entitle){
        DBResultSet dbrs = null;
        String ent = Formater.formatDate(entitle, "yyyy-MM-dd");
        String result = "";
        try {

            String sql = "SELECT DATE_ADD('" + ent + "', INTERVAL 1 YEAR)";

            //System.out.println("SQL interval : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                result = rs.getString(1);
                return result;

            }

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return null;
    }

    /*
     * untuk mendapatkan list special leave
     * 
     */
    public static Vector ListSpecialLeave(SrcLeaveManagement srcLeaveManagement) {

        DBResultSet dbrs = null;
        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " as empId " +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " as empNum " +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " as fullName " +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " as depId " +
                    ",SUM(SP." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_QTY] + ") as tknQty " +
                    " WHERE ";

            long SpecialLeaveOID = 0;

            try {
                SpecialLeaveOID = Long.parseLong(PstSystemProperty.getValueByName("OID_SPECIAL"));
            } catch (Exception e) {
                System.out.println("EXCEPTION PARSING ERORR " + e);
            }

            String whereClause = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] + " = " + SpecialLeaveOID;

            Vector listDataSpecial = PstScheduleSymbol.list(0, 0, whereClause, null);

            if (listDataSpecial == null || listDataSpecial.size() <= 0) {
                return null;
            }

            int max = listDataSpecial.size() - 1;


            for (int i = 0; i < listDataSpecial.size(); i++) {

                ScheduleSymbol scheduleSymbol = (ScheduleSymbol) listDataSpecial.get(i);

                if (i == 0) {
                    sql = sql + " ( ";
                }

                sql = sql + " SP." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = " + scheduleSymbol.getOID();

                if (i != 0 && i != max) {
                    sql = sql + " OR ";
                }

                if (i == max) {
                    sql = sql + " ) ";
                }

            }

            if (srcLeaveManagement.getEmpName() != null) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " like '%" + srcLeaveManagement.getEmpName() + "%' ";
            }

            if (srcLeaveManagement.getEmpNum() != null) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " = '" + srcLeaveManagement.getEmpNum() + "' ";
            }

            if (srcLeaveManagement.getEmpDeptId() != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = '" + srcLeaveManagement.getEmpDeptId() + "' ";
            }

            if (srcLeaveManagement.getEmpLevelId() != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = '" + srcLeaveManagement.getEmpLevelId() + "' ";
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            System.out.println("SQL Special Leave : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Vector result = new Vector();

            while (rs.next()) {
                ListSpecialLeave listSpecialLeave = new ListSpecialLeave();
                listSpecialLeave.setEmployeeID(rs.getLong("empId"));
                listSpecialLeave.setFullName(rs.getString("empNum"));
                listSpecialLeave.setFullName(rs.getString("fullName"));
                listSpecialLeave.setTknQTY(rs.getInt("tknQty"));
                listSpecialLeave.setDepartmentID(rs.getLong("depId"));

                result.add(listSpecialLeave);
            }

            return result;

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return null;

    }

    /*
     * untuk mendapatkan list unpaid leave
     * 
     */
    public static Vector ListUnpaidLeave(SrcLeaveManagement srcLeaveManagement) {

        DBResultSet dbrs = null;
        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " as empId " +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " as empNum " +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " as fullName " +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " as depId " +
                    ",SUM(SP." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_QTY] + ") " + " as tknQty " +
                    " WHERE ";

            long SpecialLeaveOID = 0;

            try {
                SpecialLeaveOID = Long.parseLong(PstSystemProperty.getValueByName("OID_UNPAID"));
            } catch (Exception e) {
                System.out.println("EXCEPTION PARSING ERORR " + e);
            }

            String whereClause = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] + " = " + SpecialLeaveOID;

            Vector listDataSpecial = PstScheduleSymbol.list(0, 0, whereClause, null);

            if (listDataSpecial == null || listDataSpecial.size() <= 0) {
                return null;
            }

            int max = listDataSpecial.size() - 1;

            for (int i = 0; i < listDataSpecial.size(); i++) {

                ScheduleSymbol scheduleSymbol = (ScheduleSymbol) listDataSpecial.get(i);

                if (i == 0) {
                    sql = sql + " ( ";
                }

                sql = sql + " SP." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = " + scheduleSymbol.getOID();

                if (i != 0 && i != max) {
                    sql = sql + " OR ";
                }

                if (i == max) {
                    sql = sql + " ) ";
                }

            }

            if (srcLeaveManagement.getEmpName() != null) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " like '%" + srcLeaveManagement.getEmpName() + "%' ";
            }

            if (srcLeaveManagement.getEmpNum() != null) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " = '" + srcLeaveManagement.getEmpNum() + "' ";
            }

            if (srcLeaveManagement.getEmpDeptId() != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = '" + srcLeaveManagement.getEmpDeptId() + "' ";
            }

            if (srcLeaveManagement.getEmpLevelId() != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = '" + srcLeaveManagement.getEmpLevelId() + "' ";
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            System.out.println("SQL Unpaid Leave : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Vector result = new Vector();

            while (rs.next()) {

                ListSpecialLeave listSpecialLeave = new ListSpecialLeave();
                listSpecialLeave.setEmployeeID(rs.getLong("empId"));
                listSpecialLeave.setFullName(rs.getString("empNum"));
                listSpecialLeave.setFullName(rs.getString("fullName"));
                listSpecialLeave.setTknQTY(rs.getInt("tknQty"));
                listSpecialLeave.setDepartmentID(rs.getLong("depId"));

                result.add(listSpecialLeave);

            }

            return result;

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return null;
    }

    public static Vector listTakenSpecialLeave(SrcLeaveManagement srcLeaveManagement, int status) {

        DBResultSet dbrs = null;

        if (status != TOBETAKEN && status != TAKEN) {
            return null;
        }

        long SpecialLeaveOID = 0;

        try {
            SpecialLeaveOID = Long.parseLong(PstSystemProperty.getValueByName("OID_SPECIAL"));
        } catch (Exception e) {
            System.out.println("EXCEPTION PARSING ERORR " + e);
            return null;
        }

        try {

            String sql = "";

            if (status == TOBETAKEN) {
                sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " as empId " +
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " as fullName " +
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " as depId " +
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " as empNum " +
                        ", SUM(SP.TAKEN_QTY) as tknQty " +
                        " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP LEFT JOIN " +
                        " hr_view_special_leave SP on EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                        " = SP.EMPLOYEE_ID " +
                        " WHERE ( SP.DOC_STATUS = " + PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT + " OR " +
                        " SP.DOC_STATUS = " + PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE + " OR " +
                        " SP.DOC_STATUS = " + PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED + ")";

            } else if (status == TAKEN) {
                sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " as empId " +
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " as fullName " +
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " as depId " +
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " as empNum " +
                        ", SUM(SP.TAKEN_QTY) as tknQty " +
                        " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP LEFT JOIN " +
                        " hr_view_special_leave SP on EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                        " = SP.EMPLOYEE_ID " +
                        " WHERE ( SP.DOC_STATUS = " + PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED + " ) ";

            }

            String whereClause = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] + " = " + SpecialLeaveOID;

            Vector listDataSpecial = PstScheduleSymbol.list(0, 0, whereClause, null);

            if (listDataSpecial == null || listDataSpecial.size() <= 0) {
                return null;
            }

            int max = listDataSpecial.size() - 1;

            for (int i = 0; i < listDataSpecial.size(); i++) {

                ScheduleSymbol scheduleSymbol = (ScheduleSymbol) listDataSpecial.get(i);

                if (i == 0) {
                    sql = sql + " AND ( ";
                }

                sql = sql + " SP." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = " + scheduleSymbol.getOID();

                if (i != max) {
                    sql = sql + " OR ";
                }
                if (i == max) {
                    sql = sql + " ) ";
                }

            }

            if (srcLeaveManagement.getEmpName() != null && srcLeaveManagement.getEmpName() != "") {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " like '%" + srcLeaveManagement.getEmpName() + "%' ";
            }

            if (srcLeaveManagement.getEmpNum() != null && srcLeaveManagement.getEmpNum() != "") {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " = '" + srcLeaveManagement.getEmpNum() + "' ";
            }

            if (srcLeaveManagement.getEmpDeptId() != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = '" + srcLeaveManagement.getEmpDeptId() + "' ";
            }

            if (srcLeaveManagement.getEmpLevelId() != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = '" + srcLeaveManagement.getEmpLevelId() + "' ";
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            if (status == TOBETAKEN) {
                System.out.println("SQL Specail Leave ( to be taken ) : " + sql);
            } else if (status == TAKEN) {
                System.out.println("SQL Specail Leave ( taken ) : " + sql);
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Vector result = new Vector();

            while (rs.next()) {

                ListSpecialLeave listSpecialLeave = new ListSpecialLeave();
                listSpecialLeave.setEmployeeID(rs.getLong("empId"));
                listSpecialLeave.setFullName(rs.getString("empNum"));
                listSpecialLeave.setFullName(rs.getString("fullName"));
                listSpecialLeave.setTknQTY(rs.getInt("tknQty"));
                listSpecialLeave.setDepartmentID(rs.getLong("depId"));

                result.add(listSpecialLeave);


            }

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        return null;
    }

    public static Vector listTakenUnpaidLeave(SrcLeaveManagement srcLeaveManagement, int status) {

        DBResultSet dbrs = null;

        if (status != TOBETAKEN && status != TAKEN) {
            return null;
        }

        long UnpaidLeaveOID = 0;

        try {
            UnpaidLeaveOID = Long.parseLong(PstSystemProperty.getValueByName("OID_UNPAID"));
        } catch (Exception e) {
            System.out.println("EXCEPTION PARSING ERORR " + e);
            return null;
        }

        try {

            String sql = "";

            if (status == TOBETAKEN) {
                sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " as empId " +
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " as fullName " +
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " as depId " +
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " as empNum " +
                        ", SUM(SP.TAKEN_QTY) as tknQty " +
                        " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP LEFT JOIN " +
                        " hr_view_special_leave SP on EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                        " = SP.EMPLOYEE_ID " +
                        " WHERE ( SP.DOC_STATUS = " + PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT + " OR " +
                        " SP.DOC_STATUS = " + PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE + " OR " +
                        " SP.DOC_STATUS = " + PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED + ")";

            } else if (status == TAKEN) {
                sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " as empId " +
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " as fullName " +
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " as depId " +
                        ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " as empNum " +
                        ", SUM(SP.TAKEN_QTY) as tknQty " +
                        " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP LEFT JOIN " +
                        " hr_view_special_leave SP on EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                        " = SP.EMPLOYEE_ID " +
                        " WHERE ( SP.DOC_STATUS = " + PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED + " ) ";

            }

            String whereClause = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] + " = " + UnpaidLeaveOID;

            Vector listDataUnpaid = PstScheduleSymbol.list(0, 0, whereClause, null);

            if (listDataUnpaid == null || listDataUnpaid.size() <= 0) {
                return null;
            }

            int max = listDataUnpaid.size() - 1;

            for (int i = 0; i < listDataUnpaid.size(); i++) {

                ScheduleSymbol scheduleSymbol = (ScheduleSymbol) listDataUnpaid.get(i);

                if (i == 0) {
                    sql = sql + " AND ( ";
                }

                sql = sql + " SP." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = " + scheduleSymbol.getOID();

                if (i != max) {
                    sql = sql + " OR ";
                }

                if (i == max) {
                    sql = sql + " ) ";
                }

            }

            if (srcLeaveManagement.getEmpName() != null && srcLeaveManagement.getEmpName() != "") {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " like '%" + srcLeaveManagement.getEmpName() + "%' ";
            }

            if (srcLeaveManagement.getEmpNum() != null && srcLeaveManagement.getEmpNum() != "") {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " = '" + srcLeaveManagement.getEmpNum() + "' ";
            }

            if (srcLeaveManagement.getEmpDeptId() != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = '" + srcLeaveManagement.getEmpDeptId() + "' ";
            }

            if (srcLeaveManagement.getEmpLevelId() != 0) {
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = '" + srcLeaveManagement.getEmpLevelId() + "' ";
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            if (status == TOBETAKEN) {
                System.out.println("SQL Specail Leave ( to be taken ) : " + sql);
            } else if (status == TAKEN) {
                System.out.println("SQL Specail Leave ( taken ) : " + sql);
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Vector result = new Vector();

            while (rs.next()) {

                ListSpecialLeave listSpecialLeave = new ListSpecialLeave();
                listSpecialLeave.setEmployeeID(rs.getLong("empId"));
                listSpecialLeave.setFullName(rs.getString("empNum"));
                listSpecialLeave.setFullName(rs.getString("fullName"));
                listSpecialLeave.setTknQTY(rs.getInt("tknQty"));
                listSpecialLeave.setDepartmentID(rs.getLong("depId"));

                result.add(listSpecialLeave);


            }

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        return null;
    }

    // leave application taken
    public static Vector LeaveApplicationTaken(long employee_id) {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT " + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " FROM " +
                    PstLeaveApplication.TBL_LEAVE_APPLICATION + " WHERE ( " +
                    PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + "=" + PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT +
                    " OR " + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + "=" + PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE +
                    " OR " + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + "=" + PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED +
                    " ) AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + employee_id;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Vector result = new Vector();

            while (rs.next()) {
                result.add("" + rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
            }

            return result;

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return null;
    }

    // untuk mendapatkan leave application to be taken
    
    public static Vector LeaveApplicationToBeTaken(long employee_id){

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT " + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " FROM " +
                    PstLeaveApplication.TBL_LEAVE_APPLICATION + " WHERE " +
                    PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + "=" + PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED +
                    " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + employee_id;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Vector result = new Vector();

            while (rs.next()) {
                result.add("" + rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
            }

            return result;

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return null;

    }
    
    
    /*
     * 
     * param StatusLeave    = UNPAID_LEAVE or SPECIAL_LEAVE
     * param statusTkn      = TAKEN or TOBETAKEN
     * 
     */
    
    public static Vector getListSpecialLeave(SrcLeaveManagement srcLeaveManagement, int statusLeave, int statusTkn){
        
        Vector result = new Vector(1,1);

        String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;

        if (srcLeaveManagement.getEmpName() != null && srcLeaveManagement.getEmpName() != "") {
            whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " like '%" + srcLeaveManagement.getEmpName() + "%' ";
        }

        if (srcLeaveManagement.getEmpNum() != null && srcLeaveManagement.getEmpNum() != "") {
            whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " = '" + srcLeaveManagement.getEmpNum() + "' ";
        }

        if (srcLeaveManagement.getEmpDeptId() != 0) {
            whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = '" + srcLeaveManagement.getEmpDeptId() + "' ";
        }

        if (srcLeaveManagement.getEmpLevelId() != 0) {
            whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = '" + srcLeaveManagement.getEmpLevelId() + "' ";
        }

        whereClause = whereClause + " GROUP BY " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
        
        String order = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+","+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

        Vector listEmployee = PstEmployee.list(0, 0, whereClause, order);

        long OIDScheduleCategory = 0;
        
        Vector listSchedule = new Vector(1,1);
        
        // untuk mengambil data OID schedule Category apakah berdasarkan special leave atau unpaid leave
        
        if(listEmployee != null && listEmployee.size() > 0){
                
            if(statusLeave == SessLeaveApp.SPECIAL_LEAVE){  // bila data yang dipilih berdasarkan data taken
                
                try{
                    
                    OIDScheduleCategory = Long.parseLong(PstSystemProperty.getValueByName("OID_SPECIAL")); 
                    
                } catch (Exception e) {
                    
                    System.out.println("EXCEPTION PARSING ERORR " + e.toString());
                    
                }
                
            }else if(statusLeave == SessLeaveApp.UNPAID_LEAVE){         // bila data yang dipilih data to be taken
                
                try {

                    OIDScheduleCategory = Long.parseLong(PstSystemProperty.getValueByName("OID_UNPAID"));   // oid schedule category

                } catch (Exception e) {

                    System.out.println("EXCEPTION PARSING ERORR " + e.toString());

                }
            }
            
            String whereEmpSchedule = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] + " = " 
                                    + OIDScheduleCategory;          // kondisi kondisi untuk pencarian untuk employee schedule   

            listSchedule = PstScheduleSymbol.list(0, 0, whereEmpSchedule, null);        // list data schedule ( berdasarkan special or unpaid leave ) 
                
        }
        
        for (int i = 0; i < listEmployee.size(); i++) { // looping sebanyak jumlah employee sesuai dengan pencarian
            
            float sumTkn = 0;                             // untuk value taken yang diambil

            Employee employee = new Employee();

            employee = (Employee) listEmployee.get(i);
            
            Vector listApplication = new Vector();  // untuk menyimpan leave application ( Taken || to be taken )
            
            if( statusTkn == TAKEN ){               // status yang dipilih taken
            
                listApplication = LeaveApplicationTaken(employee.getOID());     // untuk mendapatkan application yang to be taken
                
            }else if(statusTkn == TOBETAKEN){       // status yang dipilih to be taken
                
                listApplication = LeaveApplicationToBeTaken(employee.getOID()); // untuk mendapatkan application yang to be taken
                
            }
            
            for(int idxApp = 0 ; idxApp < listApplication.size() ; idxApp ++){  // looping sebanyak leave application yang sudah diambil
                
                LeaveApplication leaveApplication = new LeaveApplication();     // object untuk menyimpan data leave application 
                
                leaveApplication = (LeaveApplication)listApplication.get(idxApp);
                
                String whereSchLeave = "";
                
                int max = listSchedule.size() - 1;
                
                for(int idxSch = 0 ; idxSch < listSchedule.size() ; idxSch ++){
                    
                    ScheduleSymbol scheduleSymbol = (ScheduleSymbol) listSchedule.get(i);

                    if (i == 0) {
                        
                        whereSchLeave = whereSchLeave + " AND ( ";
                        
                    }

                    whereSchLeave = whereSchLeave + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = " + scheduleSymbol.getOID();

                    if (i != max) {
                        
                            whereSchLeave = whereSchLeave + " OR ";
                            
                    }

                    if (i == max) {
                        
                        whereSchLeave = whereSchLeave + " ) ";
                        
                    }
                    
                }
                
                whereSchLeave = whereSchLeave + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]+
                            " = "+leaveApplication.getOID();
                    
                Vector listSpecialLeaveTkn = new Vector();
                    
                listSpecialLeaveTkn = PstSpecialUnpaidLeaveTaken.list(0, 0, whereSchLeave, null);
                
                for(int idxListSp = 0 ; idxListSp < listSpecialLeaveTkn.size() ; idxListSp++){
                    
                    SpecialUnpaidLeaveTaken specialUnpaidLeaveTaken = new SpecialUnpaidLeaveTaken();
                    
                    specialUnpaidLeaveTaken = (SpecialUnpaidLeaveTaken)listSpecialLeaveTkn.get(idxListSp);
                    
                    sumTkn = sumTkn + specialUnpaidLeaveTaken.getTakenQty();
                    
                }                
            }
            
            ListSpecialLeave listSpecialLeave = new ListSpecialLeave();
            
            listSpecialLeave.setEmployeeID(employee.getOID());
                    
            listSpecialLeave.setFullName(employee.getFullName());
                    
            listSpecialLeave.setEmployeeNum(employee.getEmployeeNum());
                    
            listSpecialLeave.setTknQTY(sumTkn);
                    
            listSpecialLeave.setDepartmentID(employee.getDepartmentId());
            
            result.add(listSpecialLeave);
            
        }

        if(result != null || result.size() > 0){
            
            return result;              // jika ada data yang disimpan dalam result
            
        }else{
            
            return null;                // jika result == null
            
        }
    }
    
    
    public static Vector ReportAttandanceDp(long employeeId,long periodeId){
        
        Date currentDate = new Date();
        
        Vector reportAttendanceSummaryDp = new Vector();
        
        String where = PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED+
                " AND "+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]+" = "+employeeId;
        
        Vector resultLeaveExcuted = new Vector();
        
        try{
            resultLeaveExcuted = PstLeaveApplication.list(0, 0, where, null);
        }catch(Exception e){
            System.out.println("EXCEPTION "+e.toString());
        }
        
        Period period = new Period();
        
        try{
            
            period = PstPeriod.fetchExc(periodeId);
            
        }catch(Exception e){
            System.out.println("EXCEPTION "+e.toString());
        }
        
        float qty         = 0;
        float takenTotal  = 0;  
        float willTkn     = 0;
        
        if(period.getStartDate().getTime() < currentDate.getTime()  && period.getEndDate().getTime() < currentDate.getTime()){
            
            for(int i = 0 ; i < resultLeaveExcuted.size() ; i++){
                
                LeaveApplication leaveApplication = new LeaveApplication();
                
                leaveApplication = (LeaveApplication)resultLeaveExcuted.get(i);
                
                String whereDp = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+"="+leaveApplication.getOID()+" AND "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]+" = "+employeeId;
                
                Vector resultDpTkn = new Vector();
                
                resultDpTkn = PstDpStockTaken.list(0, 0, whereDp, null);
                
                for(int idx = 0 ; idx < resultDpTkn.size() ; idx++){
                    
                    DpStockTaken dpStockTaken = new DpStockTaken();
                    
                    dpStockTaken = (DpStockTaken)resultDpTkn.get(idx);
                     
                    if(dpStockTaken.getTakenDate().getTime() <= period.getEndDate().getTime()){
                        
                        takenTotal = takenTotal + dpStockTaken.getTakenQty();
                        
                    }                                              
                }
            }
            
            String whereDpMan = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+" <= "+period.getEndDate().getTime()+
                    " AND "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+" = "+employeeId;
            
            Vector vResultDpMan = new Vector();
            
            try{
                
                vResultDpMan = PstDpStockManagement.list(0,0,whereDpMan,null);
                
            }catch(Exception e){
                System.out.println("EXCEPTION "+e.toString());
            }
            
            for(int id = 0 ; id < vResultDpMan.size() ; id++){
                
                DpStockManagement dpStockManagement = new DpStockManagement();
                
                dpStockManagement = (DpStockManagement)vResultDpMan.get(id);
                
                if(dpStockManagement.getiExceptionFlag() == PstDpStockManagement.EXC_STS_NO){
                    
                    if(dpStockManagement.getDtExpiredDate().getTime() <= period.getEndDate().getTime()){
                        
                        qty = qty + dpStockManagement.getiDpQty();
                        
                    }
                    
                }else{
                    
                    if(dpStockManagement.getDtExpiredDateExc().getTime() <= period.getEndDate().getTime()){
                        
                        qty = qty + dpStockManagement.getiDpQty();
                        
                    }
                    
                }
                
            }
            
            String whereDpWillBeTkn = "("+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT+" OR "+
                PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE+" OR "+    
                PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED+")"+
                " AND "+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]+" = "+employeeId;
            
            Vector vDpWillBeTkn = new Vector();
            
            vDpWillBeTkn = PstLeaveApplication.list(0, 0, whereDpWillBeTkn, null);
            
            for(int i = 0 ; i < vDpWillBeTkn.size() ; i++){
                
                  LeaveApplication leaveApplication = new LeaveApplication();
                  
                  leaveApplication = (LeaveApplication)vDpWillBeTkn.get(i);
                  
                  String whereDpEligible = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+"="+leaveApplication.getOID()+" AND "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]+" = "+employeeId;
                  
                  Vector resultDpWillTkn = new Vector();
                
                  resultDpWillTkn = PstDpStockTaken.list(0, 0, whereDpEligible, null);
                
                  for(int ie = 0 ; ie < resultDpWillTkn.size() ; ie++){
                      
                      DpStockTaken dpStockWillTaken = (DpStockTaken)resultDpWillTkn.get(ie);
                      
                        if(dpStockWillTaken.getTakenDate().getTime() <= period.getEndDate().getTime()){
                        
                            willTkn = willTkn + dpStockWillTaken.getTakenQty();
                        
                        }                
                  }
            }            
            
            
        }else if(period.getStartDate().getTime() <= currentDate.getTime() && period.getEndDate().getTime() >= currentDate.getTime()){
            
            for(int i = 0 ; i < resultLeaveExcuted.size() ; i++){
                
                LeaveApplication leaveApplication = new LeaveApplication();
                
                leaveApplication = (LeaveApplication)resultLeaveExcuted.get(i);
                
                String whereDp = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+"="+leaveApplication.getOID();
                
                Vector resultDpTkn = new Vector();
                
                resultDpTkn = PstDpStockTaken.list(0, 0, whereDp, null);
                
                for(int idx = 0 ; idx < resultDpTkn.size() ; idx++){
                    
                    DpStockTaken dpStockTaken = new DpStockTaken();
                    
                    dpStockTaken = (DpStockTaken)resultDpTkn.get(idx);
                     
                    if(dpStockTaken.getTakenDate().getTime() <= currentDate.getTime()){
                        
                        takenTotal = takenTotal + dpStockTaken.getTakenQty();
                        
                    }                     
                }
            }
            
            
            String whereDpMan = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+" <= "+currentDate.getTime()+
                    " AND "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+" = "+employeeId;
            
            Vector vResultDpMan = new Vector();
            
            try{
                
                vResultDpMan = PstDpStockManagement.list(0,0,whereDpMan,null);
                
            }catch(Exception e){
                System.out.println("EXCEPTION "+e.toString());
            }
            
            for(int id = 0 ; id < vResultDpMan.size() ; id++){
                
                DpStockManagement dpStockManagement = new DpStockManagement();
                
                dpStockManagement = (DpStockManagement)vResultDpMan.get(id);
                
                if(dpStockManagement.getiExceptionFlag() == PstDpStockManagement.EXC_STS_NO){
                    
                    if(dpStockManagement.getDtExpiredDate().getTime() <= currentDate.getTime()){
                        
                        qty = qty + dpStockManagement.getiDpQty();
                        
                    }
                    
                }else{
                    
                    if(dpStockManagement.getDtExpiredDateExc().getTime() <= currentDate.getTime()){
                        
                        qty = qty + dpStockManagement.getiDpQty();
                        
                    }
                    
                }
                
            }
            
            String whereDpWillBeTkn = "("+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT+" OR "+
                PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE+" OR "+    
                PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED+")"+
                " AND "+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]+" = "+employeeId;
            
            Vector vDpWillBeTkn = new Vector();
            
            vDpWillBeTkn = PstLeaveApplication.list(0, 0, whereDpWillBeTkn, null);
            
            for(int i = 0 ; i < vDpWillBeTkn.size() ; i++){
                
                  LeaveApplication leaveApplication = new LeaveApplication();
                  
                  leaveApplication = (LeaveApplication)vDpWillBeTkn.get(i);
                  
                  String whereDpEligible = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+"="+leaveApplication.getOID()+" AND "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]+" = "+employeeId;
                  
                  Vector resultDpWillTkn = new Vector();
                
                  resultDpWillTkn = PstDpStockTaken.list(0, 0, whereDpEligible, null);
                
                  for(int ie = 0 ; ie < resultDpWillTkn.size() ; ie++){
                      
                      DpStockTaken dpStockWillTaken = (DpStockTaken)resultDpWillTkn.get(ie);
                      
                        if(dpStockWillTaken.getTakenDate().getTime() <= currentDate.getTime()){
                        
                            willTkn = willTkn + dpStockWillTaken.getTakenQty();
                        
                        }                
                  }
            }
            
            
        }else if(period.getStartDate().getTime() > currentDate.getTime()  && period.getEndDate().getTime() > currentDate.getTime()){
            
            for(int i = 0 ; i < resultLeaveExcuted.size() ; i++){
                
                LeaveApplication leaveApplication = new LeaveApplication();
                
                leaveApplication = (LeaveApplication)resultLeaveExcuted.get(i);
                
                String whereDp = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+"="+leaveApplication.getOID();
                
                Vector resultDpTkn = new Vector();
                
                resultDpTkn = PstDpStockTaken.list(0, 0, whereDp, null);
                
                for(int idx = 0 ; idx < resultDpTkn.size() ; idx++){
                    
                    DpStockTaken dpStockTaken = new DpStockTaken();
                    
                    dpStockTaken = (DpStockTaken)resultDpTkn.get(idx);
                     
                     if(dpStockTaken.getTakenDate().getTime() <= period.getEndDate().getTime()){
                        
                        takenTotal = takenTotal + dpStockTaken.getTakenQty();
                        
                    }                     
                }
            }
            
            String whereDpMan = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+" <= "+period.getEndDate().getTime()+
                    " AND "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+" = "+employeeId;
            
            Vector vResultDpMan = new Vector();
            
            try{
                
                vResultDpMan = PstDpStockManagement.list(0,0,whereDpMan,null);
                
            }catch(Exception e){
                System.out.println("EXCEPTION "+e.toString());
            }
            
            for(int id = 0 ; id < vResultDpMan.size() ; id++){
                
                DpStockManagement dpStockManagement = new DpStockManagement();
                
                dpStockManagement = (DpStockManagement)vResultDpMan.get(id);
                
                if(dpStockManagement.getiExceptionFlag() == PstDpStockManagement.EXC_STS_NO){
                    
                    if(dpStockManagement.getDtExpiredDate().getTime() <= period.getEndDate().getTime()){
                        
                        qty = qty + dpStockManagement.getiDpQty();
                        
                    }
                    
                }else{
                    
                    if(dpStockManagement.getDtExpiredDateExc().getTime() <= period.getEndDate().getTime()){
                        
                        qty = qty + dpStockManagement.getiDpQty();
                        
                    }
                    
                }
                
            }
            
            String whereDpWillBeTkn = "("+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT+" OR "+
                PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE+" OR "+    
                PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED+")"+
                " AND "+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]+" = "+employeeId;
            
            Vector vDpWillBeTkn = new Vector();
            
            vDpWillBeTkn = PstLeaveApplication.list(0, 0, whereDpWillBeTkn, null);
            
            for(int i = 0 ; i < vDpWillBeTkn.size() ; i++){
                
                  LeaveApplication leaveApplication = new LeaveApplication();
                  
                  leaveApplication = (LeaveApplication)vDpWillBeTkn.get(i);
                  
                  String whereDpEligible = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+"="+leaveApplication.getOID()+" AND "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]+" = "+employeeId;
                  
                  Vector resultDpWillTkn = new Vector();
                
                  resultDpWillTkn = PstDpStockTaken.list(0, 0, whereDpEligible, null);
                
                  for(int ie = 0 ; ie < resultDpWillTkn.size() ; ie++){
                      
                      DpStockTaken dpStockWillTaken = (DpStockTaken)resultDpWillTkn.get(ie);
                      
                        if(dpStockWillTaken.getTakenDate().getTime() <= period.getEndDate().getTime()){
                        
                            willTkn = willTkn + dpStockWillTaken.getTakenQty();
                        
                        }                
                  }
            }              
        }
        
        ReportAttendanceSummary reportAttendanceSummary = new ReportAttendanceSummary();
        
        reportAttendanceSummary.setDpQty(qty);
        reportAttendanceSummary.setDpTkn(takenTotal);
        reportAttendanceSummary.setDpTkn(willTkn);
        
        reportAttendanceSummaryDp.add(reportAttendanceSummary);
        
        return reportAttendanceSummaryDp;
        
    }
    
    public static Vector ReportAttandanceAl(long employeeId,long periodId){
        
        Date currentDate = new Date();
        Period period = new Period();
        
        try{
            
            period = PstPeriod.fetchExc(periodId);
            
        }catch(Exception e){
            System.out.println("EXCEPTION "+e.toString());
        }
        
        String whereStartPeriod = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]+
                " >= '"+Formater.formatDate(period.getStartDate(),"yyyy-MM-dd")+
                "' AND "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]+ 
                " < DATE_ADD('"+Formater.formatDate(period.getStartDate(),"yyyy-MM-dd")+"', INTERVAL 1 YEAR )";
        
        String whereEndPeriod = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]+
                " >= '"+Formater.formatDate(period.getEndDate(),"yyyy-MM-dd")+
                "' AND "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]+ 
                " < DATE_ADD('"+Formater.formatDate(period.getEndDate(),"yyyy-MM-dd")+"', INTERVAL 1 YEAR )";
        
        Vector vAlStart = new Vector();
        Vector vAlEnd = new Vector();
        
        try{
            vAlStart = PstAlStockManagement.list(0, 0, whereStartPeriod, null);
        }catch(Exception e){
            System.out.println("EXCEPTION "+e.toString());
        }
            
        try{
            vAlEnd = PstAlStockManagement.list(0, 0, whereEndPeriod, null);
        }catch(Exception e){
            System.out.println("EXCEPTION "+e.toString());
        }
            
        AlStockManagement alStockManagementStart = new AlStockManagement();
        AlStockManagement alStockManagementEnd = new AlStockManagement();
        
        alStockManagementStart = (AlStockManagement)vAlStart.get(0);
        alStockManagementEnd   = (AlStockManagement)vAlEnd.get(0);
        
        float TotalPrevBalance = 0;
        float Entitle          = 0;
        float CurrEntitle      = 0;
        float TotalTknExecute  = 0;
        float TotalWillTkn     = 0;
        float Balance          = 0;
        
        /**
         * @Desc    : Bila stock management 1 record
         */
        if(alStockManagementStart.getOID()!= 0 && alStockManagementEnd.getOID() != 0 && alStockManagementStart.getOID()==alStockManagementEnd.getOID()){
            
            if(period.getStartDate().getTime() < currentDate.getTime()  && period.getEndDate().getTime() < currentDate.getTime()){
                
                Vector vobjAlExecuted = getObjAlStockTakenExecuted(employeeId,alStockManagementStart.getOID());
                
                for(int iExc = 0 ; iExc < vobjAlExecuted.size() ; iExc++){
                 
                    AlStockTaken alStockTaken = (AlStockTaken)vobjAlExecuted.get(iExc);
                    
                    Date strtTkn = alStockTaken.getTakenDate();
                    Date newDate = new Date();
                    
                    for(int iTkn = 0 ; iTkn < alStockTaken.getTakenQty(); iTkn++){                        
                        
                        if(strtTkn.getTime() <= period.getEndDate().getTime()){
                        
                            TotalTknExecute = TotalTknExecute + 1;
                        }
                        
                        long tmpDate = strtTkn.getTime() + (24 * 60 * 60 * 1000);
                        newDate = new Date(tmpDate);
                        strtTkn = newDate;
                        
                    }
                    
                }
                
                Vector vobjAlWillTaken = getObjAlStockWillTaken(employeeId,alStockManagementStart.getOID());
                
                for(int iWil = 0; iWil < vobjAlWillTaken.size() ; iWil++){
                    
                    AlStockTaken alStockTaken = (AlStockTaken)vobjAlWillTaken.get(iWil);
                    
                    Date strtTknWil = alStockTaken.getTakenDate();
                    Date newDate = new Date();
                    
                    for(int iTknWil = 0 ; iTknWil < alStockTaken.getTakenQty() ; iTknWil++){
                        
                        if(strtTknWil.getTime() <= period.getEndDate().getTime()){
                        
                            TotalWillTkn = TotalWillTkn + 1;
                        }
                        
                        long tmpDate = strtTknWil.getTime() + (24 * 60 * 60 * 1000);
                        newDate = new Date(tmpDate);
                        strtTknWil = newDate;
                    }
                }
            }
            
            if(alStockManagementStart.getExpiredDate().getTime() <= period.getEndDate().getTime()){
                
                TotalPrevBalance = alStockManagementStart.getPrevBalance();
            }else{
                
                TotalPrevBalance = 0;
                
            }
            
            CurrEntitle = alStockManagementStart.getAlQty();
            
            Entitle = alStockManagementStart.getEntitled();
            
            Balance = TotalPrevBalance + CurrEntitle - TotalTknExecute - TotalWillTkn;        
            
        }else if(alStockManagementStart.getOID()!= 0 && alStockManagementEnd.getOID() != 0 && alStockManagementStart.getOID()!=alStockManagementEnd.getOID()){
            
            if(period.getStartDate().getTime() < currentDate.getTime()  && period.getEndDate().getTime() < currentDate.getTime()){
                
                Vector vobjAlExecuted = getObjAlStockTakenExecuted(employeeId,alStockManagementEnd.getOID());
                
                for(int iExc = 0 ; iExc < vobjAlExecuted.size() ; iExc++){
                 
                    AlStockTaken alStockTaken = (AlStockTaken)vobjAlExecuted.get(iExc);
                    
                    Date strtTkn = alStockTaken.getTakenDate();
                    Date newDate = new Date();
                    
                    for(int iTkn = 0 ; iTkn < alStockTaken.getTakenQty(); iTkn++){                        
                        
                        if(strtTkn.getTime() <= period.getEndDate().getTime()){
                        
                            TotalTknExecute = TotalTknExecute + 1;
                        }
                        
                        long tmpDate = strtTkn.getTime() + (24 * 60 * 60 * 1000);
                        newDate = new Date(tmpDate);
                        strtTkn = newDate;
                        
                    }
                    
                }
                
                Vector vobjAlWillTaken = getObjAlStockWillTaken(employeeId,alStockManagementEnd.getOID());
                
                for(int iWil = 0; iWil < vobjAlWillTaken.size() ; iWil++){
                    
                    AlStockTaken alStockTaken = (AlStockTaken)vobjAlWillTaken.get(iWil);
                    
                    Date strtTknWil = alStockTaken.getTakenDate();
                    Date newDate = new Date();
                    
                    for(int iTknWil = 0 ; iTknWil < alStockTaken.getTakenQty() ; iTknWil++){
                        
                        if(strtTknWil.getTime() <= period.getEndDate().getTime()){
                        
                            TotalWillTkn = TotalWillTkn + 1;
                        }
                        
                        long tmpDate = strtTknWil.getTime() + (24 * 60 * 60 * 1000);
                        newDate = new Date(tmpDate);
                        strtTknWil = newDate;
                    }
                }
            }
            
            if(alStockManagementEnd.getExpiredDate().getTime() <= period.getEndDate().getTime()){
                
                TotalPrevBalance = alStockManagementEnd.getPrevBalance();
                
            }else{
                
                TotalPrevBalance = 0;
                
            }
            
            CurrEntitle = alStockManagementEnd.getAlQty();
            
            Entitle = alStockManagementEnd.getEntitled();
            
            Balance = TotalPrevBalance + CurrEntitle - TotalTknExecute - TotalWillTkn;     
            
            
        }
        
        Vector resultReportAttendanceSummaryDp = new Vector();
        
        ReportAttendanceSummary reportAttendanceSummary = new ReportAttendanceSummary();
        reportAttendanceSummary.setAlPrevBal(TotalPrevBalance);
        reportAttendanceSummary.setAlEntitle(Entitle);
        reportAttendanceSummary.setAlQty(CurrEntitle);        
        reportAttendanceSummary.setAlTkn(TotalTknExecute);
        reportAttendanceSummary.setAlWillBeTkn(TotalWillTkn);
        
        resultReportAttendanceSummaryDp.add(reportAttendanceSummary);
        
        return resultReportAttendanceSummaryDp;
    }
    
    
    public static Vector getObjAlStockTakenExecuted(long employeeId,long alStockManagementId){
        
        DBResultSet dbrs = null;        
        Vector result = new Vector();

        try{
            
            String sql = "SELECT ALT."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] +
                    ",ALT."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +
                    " FROM "+PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT+" ALM INNER JOIN "+
                    PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN+" ALT ON ALM."+
                    PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]+" = ALT."+
                    PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID]+" INNER JOIN "+PstLeaveApplication.TBL_LEAVE_APPLICATION+" APP ON ALT."+
                    PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID]+" = APP."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+
                    " WHERE APP."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED+" AND "+
                    PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]+" = "+alStockManagementId+" AND "+
                    PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = "+employeeId;
            
            System.out.println("sql : "+sql);
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()){
               
                AlStockTaken alStockTaken = new AlStockTaken();
                alStockTaken.setTakenQty(rs.getInt(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY]));
                alStockTaken.setTakenDate(rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]));
                
                result.add(alStockTaken);
                
            }
            
            return result;
            
        }catch(Exception e){
            System.out.println("EXCEPTION "+e.toString());
        }
        
        return null;
    }
    
    /**
     * 
     * @param employeeId
     * @param alStockManagementId
     * @return
     */
    public static Vector getObjAlStockWillTaken(long employeeId,long alStockManagementId){
        
        DBResultSet dbrs = null;        
        Vector result = new Vector();

        try{
            
            String sql = "SELECT ALT."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] +
                    ",ALT."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +
                    " FROM "+PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT+" ALM INNER JOIN "+
                    PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN+" ALT ON ALM."+
                    PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]+" = ALT."+
                    PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID]+" INNER JOIN "+PstLeaveApplication.TBL_LEAVE_APPLICATION+" APP ON ALT."+
                    PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID]+" = APP."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+
                    " WHERE ( APP."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT+" OR "+
                    " APP."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE+" OR "+
                    " APP."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+" = "+PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT+" ) AND "+
                    PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]+" = "+alStockManagementId+" AND "+
                    PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = "+employeeId;
            
            System.out.println("sql : "+sql);
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()){
               
                AlStockTaken alStockTaken = new AlStockTaken();
                alStockTaken.setTakenQty(rs.getInt(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY]));
                alStockTaken.setTakenDate(rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]));
                
                result.add(alStockTaken);
                
            }
            
            return result;
            
        }catch(Exception e){
            System.out.println("EXCEPTION "+e.toString());
        }
        
        return null;
    }
    
    /**
     * @author Roy Andika
     * @param employeeId
     * @param periodId
     * @return
     */
    
    public static Vector ReportAttandanceLl(long employeeId,long periodId){
        
        Period period = new Period();
        
        try{
            
            period = PstPeriod.fetchExc(periodId);
            
        }catch(Exception e){
            System.out.println("Exception "+e.toString());
        }
        
        I_Leave leaveConfig = null;

        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception ::::: " + e.getMessage());
        }

        int interval = leaveConfig.getIntervalLLinMonths()[LeaveConfigHR.iINTERVAL_LL_HARDROCK_I];      // range mendapatkan LL (dalam bulan)
        int interval_II = leaveConfig.getIntervalLLinMonths()[LeaveConfigHR.iINTERVAL_LL_HARDROCK_II];  // range mendapatkan LL (dalam bulan)          
         
        String whereStartPeriod = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED_DATE]+
                " >= '"+Formater.formatDate(period.getStartDate(),"yyyy-MM-dd")+
                "' AND "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]+ 
                " < DATE_ADD('"+Formater.formatDate(period.getStartDate(),"yyyy-MM-dd")+"', INTERVAL "+interval+" MONTH )";
        
        String whereEndPeriod = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]+
                " >= '"+Formater.formatDate(period.getEndDate(),"yyyy-MM-dd")+
                "' AND "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]+ 
                " < DATE_ADD('"+Formater.formatDate(period.getEndDate(),"yyyy-MM-dd")+"', INTERVAL "+interval+" YEAR )";
        
        
        return null;        
        
    }
    
    /**
     *@Author   Roy A
     *@Desc     Untuk mendapatakan stock dp yang sudah tidak aktif     
     */     
    public static int getDpNotAktiv(long employeeId){
        
        DBResultSet dbrs = null;
        
        try{
            
            String sql = "SELECT COUNT("+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]+") FROM "+
                    PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+" WHERE "+
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+"="+employeeId+" AND "+
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+"="+PstDpStockManagement.DP_STS_NOT_AKTIF;
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            int sumDp = 0;
            while(rs.next()){
                sumDp = rs.getInt(1);
                return sumDp;
            }
            
        }catch(Exception e){
            System.out.println("[exception] "+e.toString());
        }finally{
            DBResultSet.close(dbrs);
        }
        return 0;
        
    }
    
}