/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.masterdatadesktop.entity;

import com.dimata.harisma.entity.admin.PstAppUser;
import com.dimata.harisma.entity.attendance.employeeoutlet.PstEmployeeOutlet;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.session.aplikasidesktop.attendance.SessDestopApplication;
import com.dimata.harisma.utility.harisma.machine.ActionDataManagementEmployee;
import com.dimata.harisma.utility.harisma.machine.db.DBException;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.db.DBResultSet;
import com.dimata.util.Formater;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class PstEmployeeDesktop {

    /**
     * mencari oid employee  yg not aktiv
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
    public static String oidEmpNotAktiv(int limitStart, int recordToGet, String whereClause, String order) {
        String oidNotAktiv="";
        DBResultSet dbrs = null;
        String oidPosition = PstKonfigurasiOutletSetting.oidPosition();
        try {
            String sql =
                    "SELECT IF(\"" + Formater.formatDate(new Date(), "yyyy-MM-dd 23:59") + "\" >= empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM]
                    + " AND empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO]
                    + " >= \"" + Formater.formatDate(new Date(), "yyyy-MM-dd 00:00") + "\" ||  emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " IN ("+oidPosition+"),\"" + ActionDataManagementEmployee.STATUS_AKTIV + "\",\"" + ActionDataManagementEmployee.STATUS_NON_AKTIV + "\") AS stsemp"
                    + " ,emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp "
                    + " LEFT JOIN " + PstAppUser.TBL_APP_USER + " AS appuser ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=appuser." + PstAppUser.fieldNames[PstAppUser.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS empoutlet ON empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    //update by satrya 2014-09-18
                    + " AND "
                        + "\""+Formater.formatDate(new Date(), "yyyy-MM-dd 23:59")  + "\" >= empoutlet."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND empoutlet."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] +">=\""+ Formater.formatDate(new Date(), "yyyy-MM-dd 00:00")+"\"";

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
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
                if (rs.getInt("stsemp")==ActionDataManagementEmployee.STATUS_NON_AKTIV) {
                    oidNotAktiv = oidNotAktiv + rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] )+",";
                }
            }
            if(oidNotAktiv!=null && oidNotAktiv.length()>0){
                oidNotAktiv = oidNotAktiv.substring(0,oidNotAktiv.length()-1);
            }
            rs.close();
         

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
               return oidNotAktiv;
           
        }

    }
    
    public static String oidEmpCrossDay(Hashtable hashCheckScheduleCrossDay,int limitStart, int recordToGet, String whereClause, String order) {
        String oidEmpCrossDay="";
        if(hashCheckScheduleCrossDay==null && (hashCheckScheduleCrossDay!=null && hashCheckScheduleCrossDay.size()>0)){
            return "0";
        }
        DBResultSet dbrs = null;
        String oidPosition = PstKonfigurasiOutletSetting.oidPosition();
        try {
            String sql =
                    "SELECT IF(\"" + Formater.formatDate(new Date(), "yyyy-MM-dd 23:59") + "\" >= empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM]
                    + " AND empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO]
                    + " >= \"" + Formater.formatDate(new Date(), "yyyy-MM-dd 00:00") + "\" ||  emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " IN ("+oidPosition+"),\"" + ActionDataManagementEmployee.STATUS_AKTIV + "\",\"" + ActionDataManagementEmployee.STATUS_NON_AKTIV + "\") AS stsemp"
                    + " ,emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp "
                    + " LEFT JOIN " + PstAppUser.TBL_APP_USER + " AS appuser ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=appuser." + PstAppUser.fieldNames[PstAppUser.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS empoutlet ON empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    //update by satrya 2014-09-18
                    + " AND "
                        + "\""+Formater.formatDate(new Date(), "yyyy-MM-dd 23:59")  + "\" >= empoutlet."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND empoutlet."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] +">=\""+ Formater.formatDate(new Date(), "yyyy-MM-dd 00:00")+"\"";

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
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
                if ((hashCheckScheduleCrossDay!=null && hashCheckScheduleCrossDay.containsKey(""+rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID])))) {
                    oidEmpCrossDay = oidEmpCrossDay + rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] )+",";
                }
            }
            if(oidEmpCrossDay!=null && oidEmpCrossDay.length()>0){
                oidEmpCrossDay = oidEmpCrossDay.substring(0,oidEmpCrossDay.length()-1);
            }
            rs.close();
         

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
               return oidEmpCrossDay;
           
        }

    }
    /**
     * Keterangan: list untuk employee di desktop create by satrya 2014-02-14
     *
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return
     */
      public static Vector listEmployeeDesktop(Hashtable hashCheckScheduleCrossDay,int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        String oidPosition = PstKonfigurasiOutletSetting.oidPosition();
        try {
            String sql =
                    "SELECT IF(\"" + Formater.formatDate(new Date(), "yyyy-MM-dd 23:59") + "\" >= empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM]
                    + " AND empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO]
                    + " >= \"" + Formater.formatDate(new Date(), "yyyy-MM-dd 00:00") + "\" ||  emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " IN ("+oidPosition+"),\"" + ActionDataManagementEmployee.STATUS_AKTIV + "\",\"" + ActionDataManagementEmployee.STATUS_NON_AKTIV + "\") AS stsemp"
                    + " ,emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + ",pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + ",pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp "
                    + " LEFT JOIN " + PstAppUser.TBL_APP_USER + " AS appuser ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=appuser." + PstAppUser.fieldNames[PstAppUser.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS empoutlet ON empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    //update by satrya 2014-09-20
                    + " AND "
                        + "\""+Formater.formatDate(new Date(), "yyyy-MM-dd 23:59")  + "\" >= empoutlet."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND empoutlet."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] +">=\""+ Formater.formatDate(new Date(), "yyyy-MM-dd 00:00")+"\"";

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
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
                String empId=""+rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
                
                if ( (hashCheckScheduleCrossDay!=null && hashCheckScheduleCrossDay.containsKey(empId))||rs.getInt("stsemp")==ActionDataManagementEmployee.STATUS_AKTIV || empId.equalsIgnoreCase("11202")) {
                    SessDestopApplication sessDestopApplication = new SessDestopApplication();
                    sessDestopApplication.setEmployeeId(rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                    sessDestopApplication.setEmpNumber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                    sessDestopApplication.setNamaEmployee(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    sessDestopApplication.setPositionName(rs.getString("pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                    sessDestopApplication.setBarcodeNumber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                    lists.add(sessDestopApplication);
                }

            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return lists;
        }

    }
}
