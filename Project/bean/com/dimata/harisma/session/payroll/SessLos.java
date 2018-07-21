/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

import com.dimata.harisma.entity.payroll.LosReport;
import com.dimata.harisma.entity.payroll.LosReportEntity;
import com.dimata.harisma.entity.payroll.PayComponent;
import com.dimata.harisma.entity.payroll.PstPaySlipComp;
import com.dimata.harisma.report.SrcEsptA1;
import com.dimata.harisma.session.leave.SessLeaveClosing;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Description : class relation Date : 2015-02-26
 *
 * @author Hendra Putu
 */
public class SessLos {

   
    public static Vector getLos(LosReport losReport) {
        Vector result= new Vector();
        DBResultSet dbrs = null;
        
        
        
        try {
            
            String sql = " SELECT ";
            sql += " he.FULL_NAME AS \"Full Name\", ";
            sql += " he.EMPLOYEE_ID AS \"EmployeeId\", ";
            sql += " hdiv.`DIVISION` AS \"Division\", "; 
            sql += " hd.`DEPARTMENT` AS \"Department\", ";
            sql += " hs.`SECTION` AS \"Section\", ";
            sql += " he.`COMMENCING_DATE` AS \"Commencing Date\", ";
            sql += " he.employee_num AS \"Payroll Number\", "; 

            sql += " TIMESTAMPDIFF(YEAR, he.`COMMENCING_DATE`, \""+Formater.formatDate(losReport.getAsPerDate(), "yyyy-MM-dd")+"\") AS \"Work Year\", ";
            sql += " TIMESTAMPDIFF(MONTH, he.`COMMENCING_DATE`, \""+Formater.formatDate(losReport.getAsPerDate(), "yyyy-MM-dd")+"\") - (TIMESTAMPDIFF(YEAR, he.`COMMENCING_DATE`, \""+Formater.formatDate(losReport.getAsPerDate(), "yyyy-MM-dd")+"\")) * 12 AS \"Work Month\", ";
            sql += " hec.`EMP_CATEGORY` AS \"Employee Category\", "; 
            sql += " pg.`PAYROLL_GROUP_NAME` AS \"Payroll Group\", ";
            /*
            if (DANA_PENDIDIKAN = 1, "YES", "NO") as "Entitled Dana Pendidikan",
            */

            sql += " IF (TIMESTAMPDIFF(YEAR, he.`COMMENCING_DATE`, \""+Formater.formatDate(losReport.getAsPerDate(), "yyyy-MM-dd")+"\") < 1, 0, "; 
            sql += " IF (TIMESTAMPDIFF(YEAR, he.`COMMENCING_DATE`, \""+Formater.formatDate(losReport.getAsPerDate(), "yyyy-MM-dd")+"\") < 5 OR hec.EMP_CATEGORY = \"Ret-Contract\", 1507500, "; 
            sql += " IF(TIMESTAMPDIFF(YEAR, he.`COMMENCING_DATE`, \""+Formater.formatDate(losReport.getAsPerDate(), "yyyy-MM-dd")+"\") < 10, 1602000, "; 
            sql += " IF(TIMESTAMPDIFF(YEAR, he.`COMMENCING_DATE`, \""+Formater.formatDate(losReport.getAsPerDate(), "yyyy-MM-dd")+"\") < 15, 1746000, "; 
            sql += " IF(TIMESTAMPDIFF(YEAR, he.`COMMENCING_DATE`, \""+Formater.formatDate(losReport.getAsPerDate(), "yyyy-MM-dd")+"\") < 50, 1890000, 0 ))))) AS \"Ammount\" ";

            sql += " FROM hr_employee he ";
            sql += " INNER JOIN hr_division hdiv ON he.`DIVISION_ID` = hdiv.`DIVISION_ID` ";
            sql += " INNER JOIN hr_department hd ON he.`DEPARTMENT_ID` = hd.`DEPARTMENT_ID` ";
            sql += " INNER JOIN hr_section hs ON he.`SECTION_ID` = hs.`SECTION_ID`";
            sql += " INNER JOIN `hr_emp_category` hec ON he.`EMP_CATEGORY_ID` = hec.`EMP_CATEGORY_ID` ";
            sql += " INNER JOIN `payroll_group` pg ON he.`PAYROLL_GROUP` = pg.`PAYROLL_GROUP_ID` ";
            sql += " WHERE he.`DANA_PENDIDIKAN` = 1 ";
            
            if (losReport.getPayrollNumber().length() > 0){
                
                sql += " AND he.`EMPLOYEE_NUM` IN ("+losReport.getPayrollNumber()+") "; 
            }
            
            if (losReport.getArrDivisionAll() != null){
                String divisionS="";
                for (int x = 0 ; x < losReport.getArrDivisionAll().length;x++){
                        divisionS = divisionS + ""+losReport.getArrDivisionAll()[x] + ",";
                }    
                divisionS= divisionS.substring(0,divisionS.length()-1);
                sql += " AND hdiv.`DIVISION_ID` IN ("+divisionS+") ";    
            }
            if (losReport.getArrDepartmentAll() != null){
                String departmentS="";
                for (int x = 0 ; x < losReport.getArrDepartmentAll().length;x++){
                        departmentS = departmentS + ""+losReport.getArrDepartmentAll()[x] + ",";
                }    
                departmentS= departmentS.substring(0,departmentS.length()-1);
                sql += " AND hd.`DEPARTMENT_ID` IN ("+departmentS+") ";    
            }
            if (losReport.getArrSectionAll() != null){
                String sectionS="";
                for (int x = 0 ; x < losReport.getArrSectionAll().length;x++){
                        sectionS = sectionS + ""+losReport.getArrSectionAll()[x] + ",";
                }    
                sectionS= sectionS.substring(0,sectionS.length()-1);
                sql += " AND hs.`SECTION_ID` IN ("+sectionS+") ";    
            }
            if (losReport.getArrEmpCatAll() != null){
                String empCatS="";
                for (int x = 0 ; x < losReport.getArrEmpCatAll().length;x++){
                        empCatS = empCatS + ""+losReport.getArrEmpCatAll()[x] + ",";
                }    
                empCatS= empCatS.substring(0,empCatS.length()-1);
                sql += " AND hec.`EMP_CATEGORY_ID` IN ("+empCatS+") ";    
            }
            if (losReport.getArrPayrollGroupAll() != null){
                String payrolGS="";
                for (int x = 0 ; x < losReport.getArrPayrollGroupAll().length;x++){
                        payrolGS = payrolGS + ""+losReport.getArrPayrollGroupAll()[x] + ",";
                }    
                payrolGS= payrolGS.substring(0,payrolGS.length()-1);
                sql += " AND pg.`PAYROLL_GROUP_ID` IN ("+payrolGS+") ";    
            }
            sql += " AND he.resigned = 0 ";
            sql += " ORDER BY Ammount, Division, Department, Section, \"Work Year\", \"Work Month\"; ";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                LosReportEntity losReportEntity = new LosReportEntity();
                losReportEntity.setFullName(rs.getString("Full Name"));
                losReportEntity.setPayrollNumber(rs.getString("Payroll Number"));
                losReportEntity.setDepartment(rs.getString("Department"));
                losReportEntity.setDivision(rs.getString("Division"));
                losReportEntity.setCommencing(rs.getDate("Commencing Date"));
                losReportEntity.setSection(rs.getString("Section"));
                losReportEntity.setWorkYear(rs.getString("Work Year"));
                losReportEntity.setWorkMonth(rs.getString("Work Month"));
                losReportEntity.setEmpCategory(rs.getString("Employee Category"));
                losReportEntity.setPayrollG(rs.getString("Payroll Group"));
                losReportEntity.setAmount(rs.getDouble("Ammount"));
                
                for (int i=0; i < losReport.getCompShowLos().size(); i ++ ){
                    PayComponent payComponent = new PayComponent();
                    payComponent = (PayComponent) losReport.getCompShowLos().get(i);
                    double nilai = PstPaySlipComp.getCompValueEmployeeDouble(rs.getLong("EmployeeId"), losReport.getPayPeriodId(), payComponent.getCompCode());
                    losReportEntity.addCompValue(payComponent.getCompCode(), nilai);
                }
                
                result.add(losReportEntity);
            }
            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  search los : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    
    
}
