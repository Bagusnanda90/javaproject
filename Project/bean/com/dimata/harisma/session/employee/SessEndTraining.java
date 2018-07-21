package com.dimata.harisma.session.employee;

/* java package */
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

/* qdep package */
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.util.*;
import com.dimata.gui.jsp.*;
import java.util.Date;

/* project package */
import com.dimata.harisma.entity.search.SrcEndTraining;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.Section;

/**
 *
 * @author guest
 */

public class SessEndTraining {
   
    public SessEndTraining() {
    }
    
    public static Vector getEndTraining(SrcEndTraining srcEndTraining) {
        Vector list = new Vector();
        DBResultSet dbrs = null;
        
        int endPeriod = srcEndTraining.getEndPeriod();
        long deptId = srcEndTraining.getDepartmentId();
        long secId = srcEndTraining.getSectionId();
        String sort = srcEndTraining.getSortField();
        
        try {
            StringBuffer sql = new StringBuffer();
            
            sql.append("SELECT ").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]).append(",");
            sql.append(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]).append(",");
            sql.append(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]).append(",");
            sql.append(PstSection.fieldNames[PstSection.FLD_SECTION]).append(",");
            sql.append(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]).append(",");
            sql.append(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]);
            sql.append(" FROM ").append(PstEmployee.TBL_HR_EMPLOYEE).append(" EMP");
            sql.append(" INNER JOIN ").append(PstDepartment.TBL_HR_DEPARTMENT).append(" DEPT");
            sql.append(" ON EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]);
            sql.append(" = DEPT.").append(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]);
            sql.append(" INNER JOIN ").append(PstSection.TBL_HR_SECTION).append(" SEC");
            sql.append(" ON EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]);
            sql.append(" = SEC.").append(PstSection.fieldNames[PstSection.FLD_SECTION_ID]);
            sql.append(" INNER JOIN ").append(PstEmpCategory.TBL_HR_EMP_CATEGORY).append(" CAT");
            sql.append(" ON EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]);
            sql.append(" = CAT.").append(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]);
            sql.append(" WHERE");
            sql.append(" CAT.").append(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]).append(" = 'TRAINEE'");
            sql.append(" AND ").append(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]);
            sql.append(" BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ").append(endPeriod * 7).append(" DAY)");
            
            if(deptId != 0) {
                sql.append(" AND EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]);
                sql.append(" = ").append(deptId);
            }
            
            if(secId != 0) {
                sql.append(" AND EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]);
                sql.append(" = ").append(secId);
            }
            
            sql.append(" ORDER BY ").append(sort);
            
            /////////////////////
            System.out.println(">>> SQL: " + sql.toString());
            /////////////////////
            
            dbrs = DBHandler.execQueryResult(sql.toString());
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()) {
                Vector vect = new Vector();
                
                Employee emp = new Employee();
                Department dept = new Department();
                Section sec = new Section();
                
                emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                emp.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                emp.setResignedDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]));
                
                dept.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                
                sec.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                
                vect.add(emp);
                vect.add(dept);
                vect.add(sec);
                
                list.add(vect);
            }
            
            rs.close();
        }
        catch(Exception e) {
            System.out.println("Err >>getEndTraining : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return list;            
        }
     
    }
}
