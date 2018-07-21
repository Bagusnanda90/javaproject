
package com.dimata.harisma.session.employee;

/* java package */
import java.io.*;
import java.util.*;
import java.util.Date;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

/* qdep package */
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.util.*;
import com.dimata.gui.jsp.*;

/* project package */
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.system.entity.system.*;

/**
 *
 * @author bayu
 */

public class SessEmpWarning {
     
    public SessEmpWarning() {
    }
    
    public static Vector getEmployeeWarning(SrcEmpWarning srcWarning, int start, int recordToGet) {
        Vector result = new Vector();
        DBResultSet dbrs = null;
        
        Date breakDateStart = srcWarning.getStartingFactDate();
        Date breakDateEnd = srcWarning.getEndingFactDate();
        Date warnDateStart = srcWarning.getStartingWarnDate();
        Date warnDateEnd = srcWarning.getEndingWarnDate();

        String breakStart = (breakDateStart.getYear() + 1900) + "-" + (breakDateStart.getMonth() + 1) + "-" + breakDateStart.getDate();
        String breakEnd = (breakDateEnd.getYear() + 1900) + "-" + (breakDateEnd.getMonth() + 1) + "-" + breakDateEnd.getDate();
        String warnStart = (warnDateStart.getYear() + 1900) + "-" + (warnDateStart.getMonth() + 1) + "-" + warnDateStart.getDate();
        String warnEnd = (warnDateEnd.getYear() + 1900) + "-" + (warnDateEnd.getMonth() + 1) + "-" + warnDateEnd.getDate();
        
        try {
            StringBuffer sql = new StringBuffer();
            
            sql.append(" SELECT EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]).append(",");
            sql.append(" EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]).append(",");
            sql.append(" EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]).append(",");
            sql.append(" DIVI.").append(PstDivision.fieldNames[PstDivision.FLD_DIVISION]).append(",");
            sql.append(" DEPT.").append(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]).append(",");
            sql.append(" SEC.").append(PstSection.fieldNames[PstSection.FLD_SECTION]).append(",");
            sql.append(" POS.").append(PstPosition.fieldNames[PstPosition.FLD_POSITION]).append(",");
            sql.append(" WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_DATE]).append(",");
            sql.append(" WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_FACT]).append(",");
            sql.append(" WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_BY]);
            sql.append(" FROM ").append(PstEmpWarning.TBL_WARNING).append(" WRN");
            sql.append(" INNER JOIN ").append(PstEmployee.TBL_HR_EMPLOYEE).append(" EMP");
            sql.append(" ON WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID]);
            sql.append(" = EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);  
            sql.append(" INNER JOIN ").append(PstDivision.TBL_HR_DIVISION).append(" DIVI");
            sql.append(" ON WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_DIVISION_ID]);
            sql.append(" = DIVI.").append(PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]);
            sql.append(" INNER JOIN ").append(PstDepartment.TBL_HR_DEPARTMENT).append(" DEPT");
            sql.append(" ON WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_DEPARTMENT_ID]);
            sql.append(" = DEPT.").append(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]);
            sql.append(" INNER JOIN ").append(PstSection.TBL_HR_SECTION).append(" SEC");
            sql.append(" ON WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_SECTION_ID]);
            sql.append(" = SEC.").append(PstSection.fieldNames[PstSection.FLD_SECTION_ID]);
            sql.append(" INNER JOIN ").append(PstPosition.TBL_HR_POSITION).append(" POS");
            sql.append(" ON WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_POSITION_ID]);
            sql.append(" = POS.").append(PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]);
            sql.append(" WHERE (").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_DATE]);
            sql.append(" BETWEEN '").append(breakStart).append("' AND '").append(breakEnd).append("'");
            sql.append(" OR ").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_DATE]);
            sql.append(" BETWEEN '").append(warnStart).append("' AND '").append(warnEnd).append("')");
               
            if(srcWarning.getName() != null && srcWarning.getName().trim().length() > 0) {
                sql.append(" AND EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                sql.append(" LIKE '%").append(srcWarning.getName()).append("%'");
            }
            
            if(srcWarning.getPayroll() != null && srcWarning.getPayroll().trim().length() > 0) {
                sql.append(" AND EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
                sql.append(" = '").append(srcWarning.getPayroll()).append("'");
            }
            
            if(srcWarning.getDivisionId() != 0 ){
                sql.append(" AND WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_DIVISION_ID]);
                sql.append(" = ").append(srcWarning.getDivisionId());
            }
            
            if(srcWarning.getDepartmentId() != 0) {
                sql.append(" AND WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_DEPARTMENT_ID]);
                sql.append(" = ").append(srcWarning.getDepartmentId());
            }

            if(srcWarning.getSectionId() != 0) {
                sql.append(" AND WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_SECTION_ID]);
                sql.append(" = ").append(srcWarning.getSectionId());
            }
            
            if(srcWarning.getPositionId() != 0) {
                sql.append(" AND POS.").append(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]);
                sql.append(" = ").append(srcWarning.getPositionId());
            }

            sql.append(" ORDER BY ").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
            sql.append(" LIMIT ").append(start).append(",").append(recordToGet);
            
            
            //////////////////
            //System.out.println(">>> SQL: " + sql.toString());
            /////////////////
                
            dbrs = DBHandler.execQueryResult(sql.toString());
            ResultSet rs = dbrs.getResultSet();     

            while(rs.next()) 
            {       
                Vector list = new Vector();
                
                Employee emp = new Employee();
                EmpWarning wrn = new EmpWarning();
                Division div = new Division();
                Department dep = new Department();
                Section sec = new Section();   
                Position pos = new Position();
                
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));    
                emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                div.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                dep.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));                
                sec.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                pos.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                wrn.setEmployeeId(rs.getLong(PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID]));
                wrn.setBreakDate(rs.getDate(PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_DATE]));
                wrn.setBreakFact(rs.getString(PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_FACT]));
                wrn.setWarningBy(rs.getString(PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_BY]));
              
                list.add(emp);
                list.add(wrn);
                list.add(div);
                list.add(dep);
                list.add(sec);
                list.add(pos);

                result.add(list);                    
            }                
        }
        catch(Exception e) {
            System.out.println("listWarningExc : "+e.toString());
            return result;
        }
        finally {
            DBResultSet.close(dbrs);
            return result;  
        }                     
    }
    
    public static int countEmployee(SrcEmpWarning srcWarning) {
        Vector result = new Vector();
        DBResultSet dbrs = null;
        
        Date breakDateStart = srcWarning.getStartingFactDate();
        Date breakDateEnd = srcWarning.getEndingFactDate();
        Date warnDateStart = srcWarning.getStartingWarnDate();
        Date warnDateEnd = srcWarning.getEndingWarnDate();

        String breakStart = (breakDateStart.getYear() + 1900) + "-" + (breakDateStart.getMonth() + 1) + "-" + breakDateStart.getDate();
        String breakEnd = (breakDateEnd.getYear() + 1900) + "-" + (breakDateEnd.getMonth() + 1) + "-" + breakDateEnd.getDate();
        String warnStart = (warnDateStart.getYear() + 1900) + "-" + (warnDateStart.getMonth() + 1) + "-" + warnDateStart.getDate();
        String warnEnd = (warnDateEnd.getYear() + 1900) + "-" + (warnDateEnd.getMonth() + 1) + "-" + warnDateEnd.getDate();
        
        try {
            StringBuffer sql = new StringBuffer();
            
            sql.append(" SELECT COUNT(EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]).append(")");
            sql.append(" FROM ").append(PstEmpWarning.TBL_WARNING).append(" WRN");
            sql.append(" INNER JOIN ").append(PstEmployee.TBL_HR_EMPLOYEE).append(" EMP");
            sql.append(" ON WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID]);
            sql.append(" = EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);            
            sql.append(" INNER JOIN ").append(PstDepartment.TBL_HR_DEPARTMENT).append(" DEPT");
            sql.append(" ON WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_DEPARTMENT_ID]);
            sql.append(" = DEPT.").append(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]);
            sql.append(" INNER JOIN ").append(PstSection.TBL_HR_SECTION).append(" SEC");
            sql.append(" ON WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_SECTION_ID]);
            sql.append(" = SEC.").append(PstSection.fieldNames[PstSection.FLD_SECTION_ID]);
            sql.append(" INNER JOIN ").append(PstPosition.TBL_HR_POSITION).append(" POS");
            sql.append(" ON WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_POSITION_ID]);
            sql.append(" = POS.").append(PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]);
            sql.append(" WHERE ").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_DATE]);
            sql.append(" BETWEEN '").append(breakStart).append("' AND '").append(breakEnd).append("'");
            sql.append(" AND ").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_DATE]);
            sql.append(" BETWEEN '").append(warnStart).append("' AND '").append(warnEnd).append("'");
               
            if(srcWarning.getName() != null && srcWarning.getName().trim().length() > 0) {
                sql.append(" AND EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                sql.append(" LIKE '%").append(srcWarning.getName()).append("%'");
            }
            
            if(srcWarning.getPayroll() != null && srcWarning.getPayroll().trim().length() > 0) {
                sql.append(" AND EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
                sql.append(" = '").append(srcWarning.getPayroll()).append("'");
            }
            
            if(srcWarning.getDivisionId() != 0){
                sql.append(" AND WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_DIVISION_ID]);
                sql.append(" = ").append(srcWarning.getDivisionId());
            }
            
            if(srcWarning.getDepartmentId() != 0) {
                sql.append(" AND WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_DEPARTMENT_ID]);
                sql.append(" = ").append(srcWarning.getDepartmentId());
            }

            if(srcWarning.getSectionId() != 0) {
                sql.append(" AND WRN.").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_SECTION_ID]);
                sql.append(" = ").append(srcWarning.getSectionId());
            }
            
            if(srcWarning.getPositionId() != 0) {
                sql.append(" AND POS.").append(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]);
                sql.append(" = ").append(srcWarning.getPositionId());
            }

            sql.append(" ORDER BY ").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
            
            
            //////////////////
            //System.out.println(">>> SQL: " + sql.toString());
            /////////////////
                
            dbrs = DBHandler.execQueryResult(sql.toString());
            ResultSet rs = dbrs.getResultSet();     

            int count = 0;
            
            while(rs.next()) {       
                count = rs.getInt(1);
            }         
            
            return count;
        }
        catch(Exception e) {
            System.out.println("countListWarningExc : "+e.toString());
            return -1;
        }
        finally {
            DBResultSet.close(dbrs);
        }                     
    }
    
    public static int chekcActiveWarning(Date date, long empId) {
        int result = -1;
        DBResultSet dbrs = null;
        
        String dateStr = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                
        try {
            StringBuffer sql = new StringBuffer();
            
            /*sql.append(" SELECT ").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_VALID_UNTIL]);
            sql.append(" FROM ").append(PstEmpWarning.TBL_WARNING);
            sql.append(" WHERE ").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_VALID_UNTIL]);
            sql.append(" >= '").append(dateStr).append("'");*/
            
            sql.append(" SELECT MAX(").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_LEVEL_ID]).append(")");
            sql.append(" FROM ").append(PstEmpWarning.TBL_WARNING);
            sql.append(" WHERE ").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID]);
            sql.append(" = ").append(empId);
            sql.append(" AND ").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_VALID_UNTIL]);
            sql.append(" >= '").append(dateStr).append("'");
            sql.append(" GROUP BY ").append(PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID]);
            
            //////////////////
            System.out.println(">>> SQL: " + sql.toString());
            /////////////////
                
            dbrs = DBHandler.execQueryResult(sql.toString());
            ResultSet rs = dbrs.getResultSet();     
            
            while(rs.next()) {       
                result = rs.getInt(1);
            }         
        }
        catch(Exception e) {
            System.out.println("getActiveWarningExc : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return result;
        }                     
    }
    
    public static int chekcActiveReprimand(Date date, long empId) {
        int result = -1;
        DBResultSet dbrs = null;
        
        String dateStr = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                
        try {
            StringBuffer sql = new StringBuffer();
         
            sql.append(" SELECT MAX(").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_LEVEL_ID]).append(")");
            sql.append(" FROM ").append(PstEmpReprimand.TBL_REPRIMAND);
            sql.append(" WHERE ").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID]);
            sql.append(" = ").append(empId);
            sql.append(" AND ").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_VALIDITY]);
            sql.append(" >= '").append(dateStr).append("'");
            sql.append(" GROUP BY ").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID]);
            
            //////////////////
            System.out.println(">>> SQL: " + sql.toString());
            /////////////////
                
            dbrs = DBHandler.execQueryResult(sql.toString());
            ResultSet rs = dbrs.getResultSet();     
            
            while(rs.next()) {       
                result = rs.getInt(1);
            }         
        }
        catch(Exception e) {
            System.out.println("getActiveWarningExc : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return result;
        }                     
    }
    
}
