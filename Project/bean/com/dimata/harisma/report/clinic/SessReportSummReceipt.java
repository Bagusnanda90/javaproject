/*
 * Session Name  	:  SessReportSummReceipt.java
 * Created on 	:  [date] [time] AM/PM
 *
 * @author  	:  [authorName]
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: [project description ... ]
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/
package com.dimata.harisma.report.clinic;

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

/* project package */
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBHandler;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.clinic.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.form.search.*;

public class SessReportSummReceipt {
    
    /**
     * @param periodMonth
     * @param periodYear
     * @param medicalType
     * @param department
     * @return
     */    
    public static Vector listSummaryReceipt(int periodMonth, int periodYear, long medicalType, long department){
        System.out.println("periodMonth : "+periodMonth);
        System.out.println("periodYear : "+periodYear);
        System.out.println("medicalType : "+medicalType);
        System.out.println("department : "+department);
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        try {
            String sql = " SELECT DISTINCT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            " , DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
            " , DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
            " , MDR."+PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_TOTAL]+
            " FROM "+PstDepartment.TBL_HR_DEPARTMENT+ " AS DEP "+
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+ " AS EMP "+
            " ON DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
            " INNER JOIN "+PstMedicalRecord.TBL_HR_MEDICAL_RECORD+ " AS MDR "+
            " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
            " = MDR."+PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID]+
            " INNER JOIN "+PstMedicalType.TBL_HR_MEDICAL_TYPE+ " AS MDT "+
            " ON MDR."+PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID]+
            " = MDT."+PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID];
            
            String whereClause = "";
            if(periodMonth!=0 && periodYear!=0){
                whereClause = " WHERE MONTH(MDR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] + ") = "+periodMonth+
                " AND YEAR(MDR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] + ") = "+periodYear;
            }
            
            if(medicalType!=0){
                if(whereClause!="" && whereClause.length()>0){
                    whereClause = whereClause + " AND MDT." + PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID] + " = "+medicalType;
                }else{
                    whereClause = " WHERE MDT." + PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID] + " = "+medicalType;
                }
            }
            
            if(department!=0){
                if(whereClause!="" && whereClause.length()>0){
                    whereClause = whereClause + " AND DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+ " = "+department;
                }else{
                    whereClause = " WHERE DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+ " = "+department;
                }
            }
            
            sql = sql + whereClause;
            
            System.out.println("SessReportSummReceipt.listSummaryReceipt() sql : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                Vector tempResult = new Vector(1,1);
                Employee emp = new Employee();
                Department dept = new Department();
                MedicalRecord medRecord = new MedicalRecord();
                
                emp.setEmployeeNum(rs.getString(1));
                emp.setFullName(rs.getString(2));
                tempResult.add(emp);
                
                dept.setOID(rs.getLong(3));
                dept.setDepartment(rs.getString(4));
                tempResult.add(dept);
                
                medRecord.setTotal(rs.getDouble(5));
                tempResult.add(medRecord);
                
                result.add(tempResult);
            }
            return result;
        }catch(Exception exc){
            System.out.println("SessReportSummReceipt.listSummaryReceipt() exc : "+exc.toString());
        }finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    /**
     * @param date
     * @param endDate
     * @param medicalType
     * @param department
     * @return
     */    
    public static Vector listSummaryReceipt(Date date, Date endDate, long medicalType, long department){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        try {
            String sql = " SELECT DISTINCT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            " , DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
            " , DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
            " , MDR."+PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_TOTAL]+
            " FROM "+PstDepartment.TBL_HR_DEPARTMENT+ " AS DEP "+
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+ " AS EMP "+
            " ON DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
            " INNER JOIN "+PstMedicalRecord.TBL_HR_MEDICAL_RECORD+ " AS MDR "+
            " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
            " = MDR."+PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_EMPLOYEE_ID]+
            " INNER JOIN "+PstMedicalType.TBL_HR_MEDICAL_TYPE+ " AS MDT "+
            " ON MDR."+PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_MEDICAL_TYPE_ID]+
            " = MDT."+PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID];
            
            String whereClause = "";
            if(date!=null && endDate!=null){
                String strDate = Formater.formatDate(date,"yyyy-MM-dd");
                String strEndDate = Formater.formatDate(endDate,"yyyy-MM-dd");
                
                whereClause = " WHERE MDR." + PstMedicalRecord.fieldNames[PstMedicalRecord.FLD_RECORD_DATE] +
                " BETWEEN '"+strDate+"' AND '"+strEndDate+"'";
            }
            
            if(medicalType!=0){
                if(whereClause!="" && whereClause.length()>0){
                    whereClause = whereClause + " AND MDT." + PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID] + " = "+medicalType;
                }else{
                    whereClause = " WHERE MDT." + PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID] + " = "+medicalType;
                }
            }
            
            if(department!=0){
                if(whereClause!="" && whereClause.length()>0){
                    whereClause = whereClause + " AND DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+ " = "+department;
                }else{
                    whereClause = " WHERE DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+ " = "+department;
                }
            }
            
            sql = sql + whereClause;
            
            System.out.println("SessReportSummReceipt.listSummaryReceipt() sql : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                Vector tempResult = new Vector(1,1);
                Employee emp = new Employee();
                Department dept = new Department();
                MedicalRecord medRecord = new MedicalRecord();
                
                emp.setEmployeeNum(rs.getString(1));
                emp.setFullName(rs.getString(2));
                tempResult.add(emp);
                
                dept.setOID(rs.getLong(3));
                dept.setDepartment(rs.getString(4));
                tempResult.add(dept);
                
                medRecord.setTotal(rs.getDouble(5));
                tempResult.add(medRecord);
                
                result.add(tempResult);
            }
            return result;
        }catch(Exception exc){
            System.out.println("SessReportSummReceipt.listSummaryReceipt() exc : "+exc.toString());
        }finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
}

