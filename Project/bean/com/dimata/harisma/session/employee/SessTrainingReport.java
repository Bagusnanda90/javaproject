
package com.dimata.harisma.session.employee;

/* java package */ 
import java.io.*;
import java.sql.ResultSet;
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 

/* qdep package */
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.system.entity.PstSystemProperty;

/**
 *
 * @author bayu
 */

public class SessTrainingReport {

    public static Vector getTrainingProfileList(SrcTrainingRpt srcTraining, int start, int recordToGet){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);

        try {
	       
            String sql = " SELECT TH.*, EMP.*, DEP.*, POS.*, TRN.*  " +
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                         " INNER JOIN "+PstTraining.TBL_HR_TRAINING+" TRN " +
                         " ON TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = TRN."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+
                         " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                         " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " LEFT JOIN "+PstPosition.TBL_HR_POSITION+" POS " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " WHERE ";

            if(srcTraining.getDepartmentId()!=0) {                         
                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+" AND ";
            }  

            sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN + 
                   " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " BETWEEN '" + (srcTraining.getStartDate().getYear()+1900) + "-" + (srcTraining.getStartDate().getMonth()+1) + "-" + srcTraining.getStartDate().getDate() +
                   "' AND '" + (srcTraining.getEndDate().getYear()+1900) + "-" + (srcTraining.getEndDate().getMonth()+1) + "-" + srcTraining.getEndDate().getDate() + "'";

            if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_EMPLOYEE_NAME)
                sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
            else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_PAYROLL_NUM)
                sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_DEPARTMENT)
                sql += " ORDER BY DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];

            sql += ",TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " LIMIT " + start + "," + recordToGet;


            System.out.println("SQL ON GET HISTORY = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                Employee employee = new Employee();
                PstEmployee.resultToObject(rs, employee);  

                Position position = new Position();
                PstPosition.resultToObject(rs, position);
              
                Department department = new Department();
                PstDepartment.resultToObject(rs, department);
                
                Training training = new Training();
                PstTraining.resultToObject(rs, training);
                
                TrainingHistory history = new TrainingHistory();
                PstTrainingHistory.resultToObject(rs, history);

                Vector temp = new Vector(1,1);
                temp.add(employee);
                temp.add(position);               
                temp.add(department);
                temp.add(training);
                temp.add(history);

                result.add(temp);
            } 
	        
        }
        catch(Exception e){
            System.out.println("Exception on getting training history list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }
    
    public static Vector getTrainingProfileListByEmp(SrcTrainingRpt srcTraining, long employeeId){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);

        try {
	       
            String sql = " SELECT TH.*, EMP.*, DEP.*, POS.*, TRN.*  " +
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                         " INNER JOIN "+PstTraining.TBL_HR_TRAINING+" TRN " +
                         " ON TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = TRN."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+
                         " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                         " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " LEFT JOIN "+PstPosition.TBL_HR_POSITION+" POS " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " WHERE ";

            if(srcTraining.getDepartmentId()!=0) {                         
                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+" AND ";
            }  

            sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN + 
                   " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " BETWEEN '" + (srcTraining.getStartDate().getYear()+1900) + "-" + (srcTraining.getStartDate().getMonth()+1) + "-" + srcTraining.getStartDate().getDate() +
                   "' AND '" + (srcTraining.getEndDate().getYear()+1900) + "-" + (srcTraining.getEndDate().getMonth()+1) + "-" + srcTraining.getEndDate().getDate() + "'";

            if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_EMPLOYEE_NAME)
                sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
            else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_PAYROLL_NUM)
                sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_DEPARTMENT)
                sql += " ORDER BY DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];

            sql += "," + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE];
             
            System.out.println("SQL ON GET HISTORY = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                Employee employee = new Employee();
                PstEmployee.resultToObject(rs, employee);  

                Position position = new Position();
                PstPosition.resultToObject(rs, position);
              
                Department department = new Department();
                PstDepartment.resultToObject(rs, department);
                
                Training training = new Training();
                PstTraining.resultToObject(rs, training);
                
                TrainingHistory history = new TrainingHistory();
                PstTrainingHistory.resultToObject(rs, history);

                Vector temp = new Vector(1,1);
                temp.add(employee);
                temp.add(position);               
                temp.add(department);
                temp.add(training);
                temp.add(history);

                result.add(temp);
            } 
	        
        }
        catch(Exception e){
            System.out.println("Exception on getting training history list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }
    
    public static Vector getTrainingProfileListS(SrcTrainingRpt srcTraining, int start, int recordToGet){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);

        try {  
            
            String sql = " SELECT EMP.EMPLOYEE_ID "+
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                         " INNER JOIN "+PstTraining.TBL_HR_TRAINING+" TRN " +
                         " ON TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = TRN."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+
                         " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                         " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " LEFT JOIN "+PstPosition.TBL_HR_POSITION+" POS " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " WHERE ";

            if(srcTraining.getDepartmentId()!=0) {                         
                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+" AND ";
            }  

            sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN + 
                   " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " BETWEEN '" + (srcTraining.getStartDate().getYear()+1900) + "-" + (srcTraining.getStartDate().getMonth()+1) + "-" + srcTraining.getStartDate().getDate() +
                   "' AND '" + (srcTraining.getEndDate().getYear()+1900) + "-" + (srcTraining.getEndDate().getMonth()+1) + "-" + srcTraining.getEndDate().getDate() + "'";

            sql = sql +" GROUP BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_EMPLOYEE_NAME)
                sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
            else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_PAYROLL_NUM)
                sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_DEPARTMENT)
                sql += " ORDER BY DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];

            sql += ", TH."+ PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " LIMIT " + start + "," + recordToGet;


            System.out.println("SQL ON GET HISTORY = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                Employee employee = new Employee();
                PstEmployee.resultToObject(rs, employee);  

                Position position = new Position();
                PstPosition.resultToObject(rs, position);
              
                Department department = new Department();
                PstDepartment.resultToObject(rs, department);
                
                Training training = new Training();
                PstTraining.resultToObject(rs, training);
                
                TrainingHistory history = new TrainingHistory();
                PstTrainingHistory.resultToObject(rs, history);

                Vector temp = new Vector(1,1);
                temp.add(employee);
                temp.add(position);               
                temp.add(department);
                temp.add(training);
                temp.add(history);

                result.add(temp);
            } 
	        
        }
        catch(Exception e){
            System.out.println("Exception on getting training history list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }
    
     public static Vector getTrainingTargetList(SrcTrainingTarget srcTraining, int start, int recordToGet){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);

        try {
            
            Calendar calendar = new GregorianCalendar();
            calendar.set(Calendar.YEAR, srcTraining.getMonth().getYear() + 1900);
            calendar.set(Calendar.MONTH, srcTraining.getMonth().getMonth());
            calendar.set(Calendar.DATE, 1);
	       
            String sql = " SELECT SUM(TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_DURATION] + "), EMP.*, DEP.*, POS.*  " +
                         " FROM "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +                        
                         " LEFT JOIN "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+ 
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                         " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " LEFT JOIN "+PstPosition.TBL_HR_POSITION+" POS " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " WHERE ";

            if(srcTraining.getDepartmentId()!=0) {                         
                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+" AND ";
            }  

            sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN + 
                   " AND IF(TH." +PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] + " IS NOT NULL, " +
                   " TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " BETWEEN '" + Formater.formatDate(calendar.getTime(), "yyyy-MM-dd") + "'";
                            
                    calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

                    sql += " AND '" + Formater.formatDate(calendar.getTime(), "yyyy-MM-dd") + "', TRUE)" ;
                           
                    
            sql += " GROUP BY EMP." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID] +
                   " HAVING SUM(TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_DURATION] + ")"; 
                   
                    String tmpminHour = PstSystemProperty.getValueByName("MIN_TRAINING_HOURS");
                    int minHour = 0;
                    
                    try {
                        //minHour = Integer.parseInt(tmpminHour) * 60;
                        minHour = SessTraining.getTrainingDuration(tmpminHour);
                    }
                    catch(Exception e) {}
                    
                    if(srcTraining.getTypeOfSearch() == 0) {
                        sql += ">=" + minHour;
                    }
                    else {
                        sql += "<" + minHour;
                        sql += " OR SUM(TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_DURATION] + ") IS NULL ";
                    }
                    
                    
                   if(srcTraining.getSortBy() == FrmSrcTrainingTarget.ORDER_EMPLOYEE_NAME)
                        sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                   else if(srcTraining.getSortBy() == FrmSrcTrainingTarget.ORDER_PAYROLL_NUM)
                        sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    
                   sql +=  ",TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                           " LIMIT " + start + "," + recordToGet;


            System.out.println("SQL ON GET HISTORY = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                Employee employee = new Employee();
                PstEmployee.resultToObject(rs, employee);  

                Position position = new Position();
                PstPosition.resultToObject(rs, position);
              
                Department department = new Department();
                PstDepartment.resultToObject(rs, department);
              
                Vector temp = new Vector(1,1);
                temp.add(employee);
                temp.add(position);               
                temp.add(department);
                temp.add(rs.getString("SUM(TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_DURATION] + ")"));

                result.add(temp);
            } 
	        
        }
        catch(Exception e){
            System.out.println("Exception on getting training history list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }

    public static int countTrainingProfile(SrcTrainingRpt srcTraining){
        DBResultSet dbrs = null;
        int result = 0;

        try{
            
            String sql = " SELECT COUNT(EMP."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+") " +
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                         " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+                        
                         " WHERE ";

            if(srcTraining.getDepartmentId()!=0)
                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+ " AND ";

            sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN +
                   " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " BETWEEN '" + (srcTraining.getStartDate().getYear()+1900) + "-" + (srcTraining.getStartDate().getMonth()+1) + "-" + srcTraining.getStartDate().getDate() +
                   "' AND '" + (srcTraining.getEndDate().getYear()+1900) + "-" + (srcTraining.getEndDate().getMonth()+1) + "-" + srcTraining.getEndDate().getDate() + "'";


            System.out.println("SQL ON COUNT HISTORY = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                result = rs.getInt(1);
            }

        }
        catch(Exception e){
            System.out.println("Exception on counting training history list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }
    
    public static int countTrainingTarget(SrcTrainingTarget srcTraining){
        DBResultSet dbrs = null;
        int result = 0;

        try{
            Calendar calendar = new GregorianCalendar();
            calendar.set(Calendar.YEAR, srcTraining.getMonth().getYear() + 1900);
            calendar.set(Calendar.MONTH, srcTraining.getMonth().getMonth());
            calendar.set(Calendar.DATE, 1);
            
            
            String sql = " SELECT (EMP." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+") " +
                         " FROM "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " + 
                         " LEFT JOIN "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+                        
                         " WHERE ";
            
                        if(srcTraining.getDepartmentId()!=0)
                            sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+ " AND ";

                        sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN +
                               " AND IF(TH." +PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] + " IS NOT NULL, " +
                               " TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                               " BETWEEN '" + Formater.formatDate(calendar.getTime(), "yyyy-MM-dd") + "'";
                            
                                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

                                sql += " AND '" + Formater.formatDate(calendar.getTime(), "yyyy-MM-dd") + "', TRUE)" ;
                            
               sql += " GROUP BY EMP." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID] +
                      " HAVING SUM(TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_DURATION] + ")"; 
                   
                        String tmpminHour = PstSystemProperty.getValueByName("MIN_TRAINING_HOURS");
                        int minHour = 0;

                        try {
                            //minHour = Integer.parseInt(tmpminHour) * 60;
                            System.out.println(tmpminHour);
                            System.out.println(SessTraining.getTrainingDuration(tmpminHour));
                            minHour = SessTraining.getTrainingDuration(tmpminHour);
                        }
                        catch(Exception e) {}

                        if(srcTraining.getTypeOfSearch() == 0) {
                            sql += ">=" + minHour;
                        }
                        else {
                            sql += "<" + minHour;
                            sql += " OR SUM(TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_DURATION] + ") IS NULL ";
                        }
                    
                    
                       if(srcTraining.getSortBy() == FrmSrcTrainingTarget.ORDER_EMPLOYEE_NAME)
                            sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                       else if(srcTraining.getSortBy() == FrmSrcTrainingTarget.ORDER_PAYROLL_NUM)
                            sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                    
                        sql +=  "," + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE];
                        
            System.out.println("SQL ON COUNT TARGET = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                result++;
            }

        }
        catch(Exception e){
            System.out.println("Exception on counting training target list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }
    
    public static Vector getTrainingList(SrcTrainingRptId srcTraining, int start, int recordToGet){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);

        try {
	       
            String sql = " SELECT TH.*, EMP.*, DEP.*, POS.*, TRN.*  " +
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                         " INNER JOIN "+PstTraining.TBL_HR_TRAINING+" TRN " +
                         " ON TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = TRN."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+
                         " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                         " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " LEFT JOIN "+PstPosition.TBL_HR_POSITION+" POS " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " WHERE ";

            if(srcTraining.getDepartmentId()!=0) {                         
                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+" AND ";
            }  
            
            if(srcTraining.getEmployeeId() != 0) {
                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+srcTraining.getEmployeeId()+" AND ";
            }
            else {
                return new Vector();
            }

            sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN + 
                   " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " BETWEEN '" + (srcTraining.getStartDate().getYear()+1900) + "-" + (srcTraining.getStartDate().getMonth()+1) + "-" + srcTraining.getStartDate().getDate() +
                   "' AND '" + (srcTraining.getEndDate().getYear()+1900) + "-" + (srcTraining.getEndDate().getMonth()+1) + "-" + srcTraining.getEndDate().getDate() + "'" +         
                   " LIMIT " + start + "," + recordToGet;


            System.out.println("SQL ON LIST = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                Employee employee = new Employee();
                PstEmployee.resultToObject(rs, employee);  

                Position position = new Position();
                PstPosition.resultToObject(rs, position);
              
                Department department = new Department();
                PstDepartment.resultToObject(rs, department);
                
                Training training = new Training();
                PstTraining.resultToObject(rs, training);
                
                TrainingHistory history = new TrainingHistory();
                PstTrainingHistory.resultToObject(rs, history);

                Vector temp = new Vector(1,1);
                temp.add(employee);
                temp.add(position);               
                temp.add(department);
                temp.add(training);
                temp.add(history);

                result.add(temp);
            } 
	        
        }
        catch(Exception e){
            System.out.println("Exception on getting training history list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }

    public static int countTrainingList(SrcTrainingRptId srcTraining){
        DBResultSet dbrs = null;
        int result = 0;

        try{
            
            String sql = " SELECT COUNT(EMP."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+") " +
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                         " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+                        
                         " WHERE ";

            if(srcTraining.getDepartmentId()!=0)
                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+ " AND ";

            if(srcTraining.getEmployeeId() != 0) {
                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+srcTraining.getEmployeeId()+" AND ";
            }
            else {
                return 0;
            }
            
            sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN +
                   " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " BETWEEN '" + (srcTraining.getStartDate().getYear()+1900) + "-" + (srcTraining.getStartDate().getMonth()+1) + "-" + srcTraining.getStartDate().getDate() +
                   "' AND '" + (srcTraining.getEndDate().getYear()+1900) + "-" + (srcTraining.getEndDate().getMonth()+1) + "-" + srcTraining.getEndDate().getDate() + "'";


            System.out.println("SQL ON COUNT LIST  = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                result = rs.getInt(1);
            }

        }
        catch(Exception e){
            System.out.println("Exception on counting training history list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }

    public static Vector getTrainingProfileTrainer(SrcTrainingRpt srcTraining, int start, int recordToGet){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);

        try {
	       
            String sql = " SELECT TH.*, EMP.*, DEP.*, POS.*, TRN.*  " +
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                         " INNER JOIN "+PstTraining.TBL_HR_TRAINING+" TRN " +
                         " ON TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = TRN."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+
                         " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                         " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " LEFT JOIN "+PstPosition.TBL_HR_POSITION+" POS " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " WHERE ";

            /*if(srcTraining.getDepartmentId()!=0) {                         
                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+" AND ";
            } */ 
            
            if(srcTraining.getTrainer().trim().length() > 0) {
                 sql += " TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINER]+" = '"+srcTraining.getTrainer()+"' AND ";
            }
            else {
                return new Vector();
            }

            sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN + 
                   " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " BETWEEN '" + (srcTraining.getStartDate().getYear()+1900) + "-" + (srcTraining.getStartDate().getMonth()+1) + "-" + srcTraining.getStartDate().getDate() +
                   "' AND '" + (srcTraining.getEndDate().getYear()+1900) + "-" + (srcTraining.getEndDate().getMonth()+1) + "-" + srcTraining.getEndDate().getDate() + "'";

            if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_EMPLOYEE_NAME)
                sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
            else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_PAYROLL_NUM)
                sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_DEPARTMENT)
                sql += " ORDER BY DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];

            sql += ", TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " LIMIT " + start + "," + recordToGet;


            System.out.println("SQL ON GET HISTORY = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                Employee employee = new Employee();
                PstEmployee.resultToObject(rs, employee);  

                Position position = new Position();
                PstPosition.resultToObject(rs, position);
              
                Department department = new Department();
                PstDepartment.resultToObject(rs, department);
                
                Training training = new Training();
                PstTraining.resultToObject(rs, training);
                
                TrainingHistory history = new TrainingHistory();
                PstTrainingHistory.resultToObject(rs, history);

                Vector temp = new Vector(1,1);
                temp.add(employee);
                temp.add(position);               
                temp.add(department);
                temp.add(training);
                temp.add(history);

                result.add(temp);
            } 
	        
        }
        catch(Exception e){
            System.out.println("Exception on getting training history list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }

    public static int countTrainingProfileTrainer(SrcTrainingRpt srcTraining){
        DBResultSet dbrs = null;
        int result = 0;

        try{
            
            String sql = " SELECT COUNT(EMP."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+") " +
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                         " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+                        
                         " WHERE ";

            /*if(srcTraining.getDepartmentId()!=0)
                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+ " AND ";*/
            
            if(srcTraining.getTrainer().trim().length() > 0) {
                 sql += " TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINER]+" = '"+srcTraining.getTrainer()+"' AND ";
            }
            else {
                return 0;
            }

            sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN +
                   " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " BETWEEN '" + (srcTraining.getStartDate().getYear()+1900) + "-" + (srcTraining.getStartDate().getMonth()+1) + "-" + srcTraining.getStartDate().getDate() +
                   "' AND '" + (srcTraining.getEndDate().getYear()+1900) + "-" + (srcTraining.getEndDate().getMonth()+1) + "-" + srcTraining.getEndDate().getDate() + "'";


            System.out.println("SQL ON COUNT HISTORY = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                result = rs.getInt(1);
            }

        }
        catch(Exception e){
            System.out.println("Exception on counting training history list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }
    
    public static Vector getTrainingProfileCourse(SrcTrainingRpt srcTraining, int start, int recordToGet){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);

        try {
	       
            String sql = " SELECT TH.*, EMP.*, DEP.*, POS.*, TRN.*  " +
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                         " INNER JOIN "+PstTraining.TBL_HR_TRAINING+" TRN " +
                         " ON TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = TRN."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+
                         " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                         " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " LEFT JOIN "+PstPosition.TBL_HR_POSITION+" POS " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " WHERE ";

            if(srcTraining.getTrainingId() != 0) {                         
                sql += " TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = "+srcTraining.getTrainingId()+" AND ";
            } 
            else {
                return new Vector();
            }

            sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN + 
                   " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " BETWEEN '" + (srcTraining.getStartDate().getYear()+1900) + "-" + (srcTraining.getStartDate().getMonth()+1) + "-" + srcTraining.getStartDate().getDate() +
                   "' AND '" + (srcTraining.getEndDate().getYear()+1900) + "-" + (srcTraining.getEndDate().getMonth()+1) + "-" + srcTraining.getEndDate().getDate() + "'";

            if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_EMPLOYEE_NAME)
                sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
            else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_PAYROLL_NUM)
                sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_DEPARTMENT)
                sql += " ORDER BY DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];

            sql += ",TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " LIMIT " + start + "," + recordToGet;


            System.out.println("SQL ON GET HISTORY = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                Employee employee = new Employee();
                PstEmployee.resultToObject(rs, employee);  

                Position position = new Position();
                PstPosition.resultToObject(rs, position);
              
                Department department = new Department();
                PstDepartment.resultToObject(rs, department);
                
                Training training = new Training();
                PstTraining.resultToObject(rs, training);
                
                TrainingHistory history = new TrainingHistory();
                PstTrainingHistory.resultToObject(rs, history);

                Vector temp = new Vector(1,1);
                temp.add(employee);
                temp.add(position);               
                temp.add(department);
                temp.add(training);
                temp.add(history);

                result.add(temp);
            } 
	        
        }
        catch(Exception e){
            System.out.println("Exception on getting training course list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }

    public static int countTrainingProfileCourse(SrcTrainingRpt srcTraining){
        DBResultSet dbrs = null;
        int result = 0;

        try{
            
            String sql = " SELECT COUNT(EMP."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+") " +
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                         " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+                        
                         " WHERE ";

            if(srcTraining.getTrainingId() != 0) {
                sql += " TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = "+srcTraining.getTrainingId()+ " AND ";
            }
            else {
                return 0;
            }

            sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN +
                   " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " BETWEEN '" + (srcTraining.getStartDate().getYear()+1900) + "-" + (srcTraining.getStartDate().getMonth()+1) + "-" + srcTraining.getStartDate().getDate() +
                   "' AND '" + (srcTraining.getEndDate().getYear()+1900) + "-" + (srcTraining.getEndDate().getMonth()+1) + "-" + srcTraining.getEndDate().getDate() + "'";


            System.out.println("SQL ON COUNT HISTORY = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                result = rs.getInt(1);
            }

        }
        catch(Exception e){
            System.out.println("Exception on counting training course list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }
    
    public static Vector getTrainingProfileCourseDate(SrcTrainingRpt srcTraining, int start, int recordToGet){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);

        try {
	       
            String sql = " SELECT TH.*, EMP.*, DEP.*, POS.*, TRN.*  " +
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                         " INNER JOIN "+PstTraining.TBL_HR_TRAINING+" TRN " +
                         " ON TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = TRN."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+
                         " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                         " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " LEFT JOIN "+PstPosition.TBL_HR_POSITION+" POS " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " WHERE MONTH(TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE]+") = "+ (srcTraining.getTrainingMonth().getMonth()+1) +" AND " +
                         " YEAR(TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE]+") = "+(srcTraining.getTrainingMonth().getYear()+1900)+" AND " +
                         " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN ;
                        

            if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_EMPLOYEE_NAME)
                sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
            else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_PAYROLL_NUM)
                sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_DEPARTMENT)
                sql += " ORDER BY DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];

            sql += ",TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] +
                   " LIMIT " + start + "," + recordToGet;


            System.out.println("SQL ON GET HISTORY = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                Employee employee = new Employee();
                PstEmployee.resultToObject(rs, employee);  

                Position position = new Position();
                PstPosition.resultToObject(rs, position);
              
                Department department = new Department();
                PstDepartment.resultToObject(rs, department);
                
                Training training = new Training();
                PstTraining.resultToObject(rs, training);
                
                TrainingHistory history = new TrainingHistory();
                PstTrainingHistory.resultToObject(rs, history);

                Vector temp = new Vector(1,1);
                temp.add(employee);
                temp.add(position);               
                temp.add(department);
                temp.add(training);
                temp.add(history);

                result.add(temp);
            } 
	        
        }
        catch(Exception e){
            System.out.println("Exception on getting training course date list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }
    
    public static int countTrainingProfileCourseDate(SrcTrainingRpt srcTraining){
        DBResultSet dbrs = null;
        int result = 0;

        try{
            
            String sql = " SELECT COUNT(EMP."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+") " +
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                         " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                         " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+                        
                         " WHERE MONTH(TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE]+") = "+ (srcTraining.getTrainingMonth().getMonth()+1) +" AND " +
                         " YEAR(TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE]+") = "+(srcTraining.getTrainingMonth().getYear()+1900) +" AND " +
                         " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN; 
                        

            System.out.println("SQL ON COUNT HISTORY = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()){
                result = rs.getInt(1);
            }

        }
        catch(Exception e){
            System.out.println("Exception on counting training course date list : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }
    
}
