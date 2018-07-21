
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

public class SessEmpReprimand {
     
    public SessEmpReprimand() {
    }
    
    public static Vector getEmployeeReprimand(SrcEmpReprimand srcReprimand, int start, int recordToGet) {
        Vector result = new Vector();
        DBResultSet dbrs = null;
        
        Date reprimandDateStart = srcReprimand.getStartingReprimandDate();
        Date reprimandDateEnd = srcReprimand.getEndingReprimandDate();

        String reprimandStart = (reprimandDateStart.getYear() + 1900) + "-" + (reprimandDateStart.getMonth() + 1) + "-" + reprimandDateStart.getDate();
        String reprimandEnd = (reprimandDateEnd.getYear() + 1900) + "-" + (reprimandDateEnd.getMonth() + 1) + "-" + reprimandDateEnd.getDate();
        
        try {
            StringBuffer sql = new StringBuffer();
            
            sql.append(" SELECT EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]).append(",");
            sql.append(" EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]).append(",");
            sql.append(" EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]).append(",");
            sql.append(" EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]).append(",");
            sql.append(" DIVI.").append(PstDivision.fieldNames[PstDivision.FLD_DIVISION]).append(",");
            sql.append(" DEPT.").append(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]).append(",");
            sql.append(" SEC.").append(PstSection.fieldNames[PstSection.FLD_SECTION]).append(",");
            sql.append(" POS.").append(PstPosition.fieldNames[PstPosition.FLD_POSITION]).append(",");
            sql.append(" RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE]).append(",");
            sql.append(" RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_CHAPTER]).append(",");
            sql.append(" RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_ARTICLE]).append(",");
            sql.append(" RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_PAGE]).append(",");
            sql.append(" RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DESCRIPTION]);
            sql.append(" FROM ").append(PstEmpReprimand.TBL_REPRIMAND).append(" RPM");
            sql.append(" INNER JOIN ").append(PstEmployee.TBL_HR_EMPLOYEE).append(" EMP");
            sql.append(" ON RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID]);
            sql.append(" = EMP.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID]);            
            sql.append(" INNER JOIN ").append(PstDepartment.TBL_HR_DEPARTMENT).append(" DEPT");
            sql.append(" ON RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DEPARTMENT_ID]);
            sql.append(" = DEPT.").append(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]);
            sql.append(" INNER JOIN ").append(PstDivision.TBL_HR_DIVISION).append(" DIVI");
            sql.append(" ON RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DIVISION_ID]);
            sql.append(" = DIVI.").append(PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]);
            sql.append(" INNER JOIN ").append(PstSection.TBL_HR_SECTION).append(" SEC");
            sql.append(" ON RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_SECTION_ID]);
            sql.append(" = SEC.").append(PstSection.fieldNames[PstSection.FLD_SECTION_ID]);
            sql.append(" INNER JOIN ").append(PstPosition.TBL_HR_POSITION).append(" POS");
            sql.append(" ON RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_POSITION_ID]);
            sql.append(" = POS.").append(PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]);
            sql.append(" WHERE (").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE]);
            sql.append(" BETWEEN '").append(reprimandStart).append("' AND '").append(reprimandEnd).append("')");
           
            if(srcReprimand.getName() != null && srcReprimand.getName().trim().length() > 0) {
                sql.append(" AND EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                sql.append(" LIKE '%").append(srcReprimand.getName()).append("%'");
            }
            
            if(srcReprimand.getPayroll() != null && srcReprimand.getPayroll().trim().length() > 0) {
                sql.append(" AND EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
                sql.append(" = '").append(srcReprimand.getPayroll()).append("'");
            }
            
            if(srcReprimand.getDivisionId() != 0) {
                sql.append(" AND RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DIVISION_ID]);
                sql.append(" = ").append(srcReprimand.getDivisionId());
            }
            
            if(srcReprimand.getDepartmentId() != 0) {
                sql.append(" AND RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DEPARTMENT_ID]);
                sql.append(" = ").append(srcReprimand.getDepartmentId());
            }

            if(srcReprimand.getSectionId() != 0) {
                sql.append(" AND RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_SECTION_ID]);
                sql.append(" = ").append(srcReprimand.getSectionId());
            }
            
            if(srcReprimand.getPositionId() != 0) {
                sql.append(" AND RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_POSITION_ID]);
                sql.append(" = ").append(srcReprimand.getPositionId());
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
                EmpReprimand rep = new EmpReprimand();
                Division div = new Division();
                Department dep = new Department();
                Section sec = new Section();   
                Position pos = new Position();
                
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));    
                emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                emp.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                div.setDivision(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
                dep.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));                
                sec.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                pos.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                rep.setEmployeeId(rs.getLong(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID]));
                rep.setValidityDate(rs.getDate(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE]));
                rep.setChapter(rs.getString(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_CHAPTER]));
                rep.setArticle(rs.getString(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_ARTICLE]));
                rep.setPage(rs.getString(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_PAGE]));
                rep.setDescription(rs.getString(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DESCRIPTION]));
               
                list.add(emp);
                list.add(rep);
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
    
    public static int countEmployee(SrcEmpReprimand srcReprimand) {
        Vector result = new Vector();
        DBResultSet dbrs = null;
        
        Date reprimandDateStart= srcReprimand.getStartingReprimandDate();
        Date reprimandDateEnd = srcReprimand.getEndingReprimandDate();

        String reprimandStart = (reprimandDateStart.getYear() + 1900) + "-" + (reprimandDateStart.getMonth() + 1) + "-" + reprimandDateStart.getDate();
        String reprimandEnd = (reprimandDateEnd.getYear() + 1900) + "-" + (reprimandDateEnd.getMonth() + 1) + "-" + reprimandDateEnd.getDate();
        
        try {
            StringBuffer sql = new StringBuffer();
            
            sql.append(" SELECT COUNT(EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]).append(")");
            sql.append(" FROM ").append(PstEmpReprimand.TBL_REPRIMAND).append(" RPM");
            sql.append(" INNER JOIN ").append(PstEmployee.TBL_HR_EMPLOYEE).append(" EMP");
            sql.append(" ON RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID]);
            sql.append(" = EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
            sql.append(" INNER JOIN ").append(PstDivision.TBL_HR_DIVISION).append(" DIVI");
            sql.append(" ON RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DIVISION_ID]);
            sql.append(" = DIVI.").append(PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]);
            sql.append(" INNER JOIN ").append(PstDepartment.TBL_HR_DEPARTMENT).append(" DEPT");
            sql.append(" ON RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DEPARTMENT_ID]);
            sql.append(" = DEPT.").append(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]);
            sql.append(" INNER JOIN ").append(PstSection.TBL_HR_SECTION).append(" SEC");
            sql.append(" ON RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_SECTION_ID]);
            sql.append(" = SEC.").append(PstSection.fieldNames[PstSection.FLD_SECTION_ID]);
            sql.append(" INNER JOIN ").append(PstPosition.TBL_HR_POSITION).append(" POS");
            sql.append(" ON RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_POSITION_ID]);
            sql.append(" = POS.").append(PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]);
            sql.append(" WHERE ").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE]);
            sql.append(" BETWEEN '").append(reprimandStart).append("' AND '").append(reprimandEnd).append("'");
            
            if(srcReprimand.getName() != null && srcReprimand.getName().trim().length() > 0) {
                sql.append(" AND EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                sql.append(" LIKE '%").append(srcReprimand.getName()).append("%'");
            }
            
            if(srcReprimand.getPayroll() != null && srcReprimand.getPayroll().trim().length() > 0) {
                sql.append(" AND EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
                sql.append(" = '").append(srcReprimand.getPayroll()).append("'");
            }
            
            if(srcReprimand.getDivisionId() != 0) {
                sql.append(" AND RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DIVISION_ID]);
                sql.append(" = ").append(srcReprimand.getDivisionId());
            }
            
            if(srcReprimand.getDepartmentId() != 0) {
                sql.append(" AND RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_DEPARTMENT_ID]);
                sql.append(" = ").append(srcReprimand.getDepartmentId());
            }

            if(srcReprimand.getSectionId() != 0) {
                sql.append(" AND RPM.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_SECTION_ID]);
                sql.append(" = ").append(srcReprimand.getSectionId());
            }
            
            if(srcReprimand.getPositionId() != 0) {
                sql.append(" AND POS.").append(PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_POSITION_ID]);
                sql.append(" = ").append(srcReprimand.getPositionId());
            }

            sql.append(" ORDER BY ").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
            
            
            //////////////////
            System.out.println(">>> SQL: " + sql.toString());
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
    
}
