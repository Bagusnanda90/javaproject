/* 
 * Session Name  	:  SessDayOfPayment.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.session.attendance;
/* java package */ 
import com.dimata.harisma.entity.attendance.DayOfPayment;
import com.dimata.harisma.entity.attendance.DpStockManagement;
import com.dimata.harisma.entity.attendance.EmpSchedule;
import com.dimata.harisma.entity.attendance.PstDayOfPayment;
import com.dimata.harisma.entity.attendance.PstDpStockManagement;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.DpApplication;
import com.dimata.harisma.entity.leave.PstDpApplication;
import com.dimata.harisma.entity.leave.dp.PstDpAppDetail;
import com.dimata.harisma.entity.leave.dp.PstDpAppMain;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstPeriod;

/* qdep package */ 
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.util.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
/* project package */
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBHandler;
import com.dimata.harisma.entity.search.SrcDPExpiration;
import com.dimata.harisma.entity.search.SrcDayOfPayment;
import com.dimata.harisma.entity.search.SrcEmployee;
import com.dimata.harisma.entity.search.SrcDPExpiration;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JOptionPane;
public class SessDayOfPayment{
    public static final String SESS_SRC_DAYOFPAYMENT = "SESSION_SRC_DAYOFPAYMENT";

    private static Vector logicParser(String text)
    {
        Vector vector = LogicParser.textSentence(text);
        for(int i =0;i < vector.size();i++){
            String code =(String)vector.get(i);
            if(((vector.get(vector.size()-1)).equals(LogicParser.SIGN))&&
              ((vector.get(vector.size()-1)).equals(LogicParser.ENGLISH)))
                vector.remove(vector.size()-1);
        }
        return vector;
    }
    public static Vector searchDayOfPayment(SrcDayOfPayment srcdayofpayment, int start, int recordToGet){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcdayofpayment == null)
            return new Vector(1,1);
        try {
            String sql = " SELECT DOP."+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_DAY_OF_PAYMENT_ID]+
                	 ", DOP."+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_EMPLOYEE_ID]+
                         ", DOP."+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_DURATION]+
                         ", DOP."+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_DP_FROM]+
                         ", DOP."+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_DP_TO]+
                         ", DOP."+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_APR_DEPTHEAD_DATE]+
                         ", DOP."+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_CONTACT_ADDRESS]+
                         ", DOP."+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_REMARKS]+
                         //", DOP."+PstDayOfPayment.fieldNames[PstDayOfPayment.]+
                         
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+
                         " FROM "+
                         " "  +PstDayOfPayment.TBL_HR_DAY_OF_PAYMENT + " DOP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " WHERE "+
                         " DOP."+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
                         
            String whereClause = "";
            if((srcdayofpayment.getFullName()!= null)&& (srcdayofpayment.getFullName().length()>0)){
                Vector vectName = logicParser(srcdayofpayment.getFullName());
                if(vectName != null && vectName.size()>0){
                    //whereClause = whereClause + " AND (";
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if(srcdayofpayment.getDepartment().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                			" = "+srcdayofpayment.getDepartment() + " AND ";
            }

            if(srcdayofpayment.getPosition().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                			" = "+srcdayofpayment.getPosition() + " AND ";
            }

            if(srcdayofpayment.getSection().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                			" = "+srcdayofpayment.getSection() + " AND ";
            }

            if(whereClause != null && whereClause.length()>0){
            	//whereClause = whereClause.substring(0,whereClause.length()-4);
                //System.out.println("\twhereClause.length() = " + whereClause.length());
                whereClause += " 1 = 1 ";
            	sql = sql + " AND  " + whereClause;
            }

            sql = sql + " LIMIT " + start + "," + recordToGet;
            
            //System.out.println("\t SQL searchDayOfPayment : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);

                DayOfPayment dayofpayment = new DayOfPayment();
                Employee employee = new Employee();

			dayofpayment.setOID(rs.getLong(PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_DAY_OF_PAYMENT_ID]));
			dayofpayment.setEmployeeId(rs.getLong(PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_EMPLOYEE_ID]));
			dayofpayment.setDuration(rs.getInt(PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_DURATION]));
			dayofpayment.setDpFrom(rs.getDate(PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_DP_FROM]));
			dayofpayment.setDpTo(rs.getDate(PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_DP_TO]));
			dayofpayment.setAprDeptheadDate(rs.getDate(PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_APR_DEPTHEAD_DATE]));
			dayofpayment.setContactAddress(rs.getString(PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_CONTACT_ADDRESS]));
			dayofpayment.setRemarks(rs.getString(PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_REMARKS]));
                        vect.add(dayofpayment);

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
            System.out.println("\t Exception on searchLeave : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);
    }

    public static int getCountSearch (SrcDayOfPayment srcdayofpayment){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcdayofpayment == null)
            return 0;
        try {
            String sql = " SELECT COUNT(DOP."+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_DAY_OF_PAYMENT_ID]+") " +
                         " FROM "+
                         " "  +PstDayOfPayment.TBL_HR_DAY_OF_PAYMENT + " DOP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " WHERE "+
                         " DOP."+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
                         
            String whereClause = "";
            if((srcdayofpayment.getFullName()!= null)&& (srcdayofpayment.getFullName().length()>0)){
                Vector vectName = logicParser(srcdayofpayment.getFullName());
                if(vectName != null && vectName.size()>0){
                    //whereClause = whereClause + " AND (";
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if(srcdayofpayment.getDepartment().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                			" = "+srcdayofpayment.getDepartment() + " AND ";
            }

            if(srcdayofpayment.getPosition().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                			" = "+srcdayofpayment.getPosition() + " AND ";
            }

            if(srcdayofpayment.getSection().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                			" = "+srcdayofpayment.getSection() + " AND ";
            }

            if(whereClause != null && whereClause.length()>0){
            	//whereClause = whereClause.substring(0,whereClause.length()-4);
                //System.out.println("\twhereClause.length() = " + whereClause.length());
                whereClause += " 1 = 1 ";
            	sql = sql + " AND " + whereClause;
            }

            //System.out.println("\t SQL searchDayOfPayment : " + sql);    
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int num = 0;
            while(rs.next()) {
            	num = rs.getInt(1);
            }
            return num;
        } catch (Exception e) {
            System.out.println("\t Exception on getCountSearch : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }


    public static int getEmpDayOff(long empOID){
        DBResultSet dbrs = null;
        int result = 0;
        try{
            String sql = "SELECT SUM("+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_DURATION]+") FROM "+PstDayOfPayment.TBL_HR_DAY_OF_PAYMENT+
                " WHERE "+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_EMPLOYEE_ID]+"="+empOID;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                result = rs.getInt(1);
            }

            rs.close();

        }
        catch(Exception e){
            System.out.println("Exception e : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }
        return result;
    }


    public static int getDayOffByDepartment(long departmentOID){
        DBResultSet dbrs = null;
        int result = 0;
        try{
            String sql = "SELECT SUM("+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_DURATION]+") FROM "+PstDayOfPayment.TBL_HR_DAY_OF_PAYMENT+" AS DOF "+
                " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+
                " DOF."+PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_EMPLOYEE_ID]+
                " WHERE EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentOID;

            sql = sql + " AND (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = 0)";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                result = rs.getInt(1);
            }

            rs.close();

        }
        catch(Exception e){
            System.out.println("Exception e : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }
        return result;
    }
    
    // added by Bayu
    public static Vector getExpiredDPPeriod(SrcDPExpiration srcDPExpiration) {
        DBResultSet dbrs = null;
        Vector list = new Vector();
        
        String name = srcDPExpiration.getEmpName().trim();
        String payroll = srcDPExpiration.getEmpNum().trim();
        long deptID = srcDPExpiration.getEmpDeptId();
        int expPeriod = srcDPExpiration.getExpirationPeriod();
        Date tgl = srcDPExpiration.getStartDate();
        String str_tgl="";
        str_tgl=Formater.formatDate(tgl, "yyyy-MM-dd");

        try {           
            StringBuffer sql = new StringBuffer();
            
            sql.append("SELECT ");
            sql.append(" EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]).append(",");
            sql.append(" EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]).append(",");
            sql.append(" DP.").append(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]).append(",");
            sql.append(" DP.").append(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]).append(",");
            sql.append(" DP.").append(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC]).append(",");
            sql.append(" DP.").append(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXCEPTION_FLAG]);
            sql.append(" FROM ").append(PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT).append(" DP");
            sql.append(" INNER JOIN ").append(PstEmployee.TBL_HR_EMPLOYEE).append(" EMP ON");
            sql.append(" DP.").append(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]);
            sql.append(" = EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
            sql.append(" INNER JOIN ").append(PstDepartment.TBL_HR_DEPARTMENT).append(" DEPT ON");
            sql.append(" DEPT.").append(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]);
            sql.append(" = EMP.").append(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]);
            sql.append(" WHERE ").append(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]);
            
            if(expPeriod == 0) {
                sql.append(" >= '").append(str_tgl).append("'");
            }
            else {
                sql.append(" BETWEEN '").append(str_tgl).append("' AND DATE_ADD( '").append(str_tgl).append("', INTERVAL ").append(expPeriod * 7).append(" DAY)");
            }
            
            if(name != null && name.length() > 0) {
                sql.append(" AND ").append(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]).append(" LIKE '%").append(name).append("%'");
            }
            
            if(payroll != null && payroll.length() > 0) {
                sql.append(" AND ").append(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]).append(" = '").append(payroll).append("'");
            }
            
            if(deptID != 0) {
                sql.append(" AND DEPT.").append(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]).append(" = ").append(deptID);
            }
            
            sql.append(" AND DP.").append(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]).append(" != 0 ");

            sql.append(" AND DP.").append(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]).append(" != 0 ");
            
            sql.append(" ORDER BY DP.").append(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]);
            
            
            //////////////////
            System.out.println(">>> SQL Command: " + sql.toString());
            /////////////////
            //JOptionPane.showMessageDialog(null, sql);
            dbrs = DBHandler.execQueryResult(sql.toString());
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                Vector vt = new Vector();
                
                Employee emp = new Employee();
                DpStockManagement dp = new DpStockManagement();
                
                emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
    
                dp.setiDpQty(rs.getInt("dp_qty"));
                dp.setDtExpiredDate(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]));
                dp.setiExceptionFlag(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXCEPTION_FLAG]));
                dp.setDtExpiredDateExc(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC]));
                
                vt.add(emp);
                vt.add(dp);
                
                list.add(vt);
            }
            rs.close();
        }
        catch(Exception e){
            System.out.println("Err >>getExpiredDpPeriod : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }
        
        return list;
    }
    
    /**
     * add by artha
     * digunakan untuk mencari dp pada periode tertentu
     */
    public static Vector getDpEntitledAtPeriod(SrcEmployee srcEmp, long periodId){
        DBResultSet dbRs = null;
        Vector vData = new Vector(1, 1);
        try {
            Period period = new Period();
            try {
                //System.out.println(">>> SessDayOfPayment.getDpEntitledAtPeriod ::::: "+periodId);
                period = PstPeriod.fetchExc(periodId);
            } catch (Exception ex) {
            }
            String query = "SELECT DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] 
                    + ", COUNT(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] 
                    + ")" + " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT 
                    + " AS DP " + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE 
                    + " AS EMP " + " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] 
                    + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                    + " WHERE (DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] 
                    + " BETWEEN " + Formater.formatDate(period.getStartDate(), "yyyy-MM-dd") + " AND " + Formater.formatDate(period.getEndDate(), "yyyy-MM-dd") + ")";
            if (srcEmp.getDivisionId() > 0) {
                query += " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "=" + srcEmp.getDivisionId();
            }
            if (srcEmp.getDepartment() > 0) {
                query += " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + srcEmp.getDepartment();
            }
            if (srcEmp.getSection() > 0) {
                query += " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + srcEmp.getSection();
            }
            query += " GROUP BY DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];

            /////////////////OUT/////////////////////
        //    System.out.println("[SQL] SessDayOfPayment.getDpEntitledAtPeriod : " + query);
            /////////////////////////////////////////
            
            dbRs = DBHandler.execQueryResult(query);
            ResultSet rs = dbRs.getResultSet();
            
            while(rs.next()){
                Vector vTemp = new Vector();
                vTemp.add(rs.getString(1));
                vTemp.add(rs.getString(2));
                vData.add(vTemp);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DBException ex) {
            ex.printStackTrace();
        } finally {
            DBResultSet.close(dbRs);
        }
        return vData;
    }    
    /**
     * Mencari stock dp pada saat pengajuan DP
     * ini dipergunakan untuk mengatahui jumlah dp yang tersisa dari sisa pengajuan.
     * @param empOid : oid employee
     * @return value of dp stock available
     * @created by artha
     */
    public static int getDpBalance(long employeeId){
        int dpBalance = 0;
        dpBalance = countDpStockValid(employeeId);
        int tempValApp = countDpAppValid(employeeId);
        
        dpBalance -= tempValApp;
        return dpBalance;
    }
    
    /**
     * Mencari dp yangmasih valid pada dp stock management
     * @param employeeId
     * @return jumlah Dp yang masih valid
     */
    private static int countDpStockValid(long employeeId){
        int val = 0;
        DBResultSet dbRs = null;
        try {
            String queryManVal = "SELECT DISTINCT DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
                +" , SUM(DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]+")"
                +" FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+" AS DP "
                +" INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP "
                +" ON DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
                +" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                +" WHERE DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]
                +" = "+PstDpStockManagement.DP_STS_AKTIF
                +" AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                +" = "+PstEmployee.NO_RESIGN
                +" AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                +" = "+employeeId
                +" GROUP BY DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
                +" ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                +" , DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]
                +" LIMIT 0,1"
                ;
                
            /////////////////OUT/////////////////////
            System.out.println("[SQL] SessDayOfPayment.countDpStockValid : " + queryManVal);
            /////////////////////////////////////////
            
            dbRs = DBHandler.execQueryResult(queryManVal);
            ResultSet rs = dbRs.getResultSet();
            
            while(rs.next()){
                val = rs.getInt(2);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DBException ex) {
            ex.printStackTrace();
        } finally {
            DBResultSet.close(dbRs);
        }
        return val;
    }
    
    /**
     * Mencari dp yang masih dalam proses pengajuan dan belum diambil
     * @param employeeId
     * @return jumlah Dp yang masih valid
     */
    private static int countDpApplicationValid(long employeeId){
        int val = 0;
        DBResultSet dbRs = null;
        try {
            String queryManVal = "SELECT DISTINCT DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_EMPLOYEE_ID]
                +" ,COUNT(DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_DP_APPLICATION_ID]+")"
                +" FROM "+PstDpApplication.TBL_DP_APPLICATION+" AS DP "
                +" INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP "
                +" ON DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_EMPLOYEE_ID]
                +" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                +" WHERE (DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS]
                +" = "+PstDpApplication.FLD_DOC_STATUS_VALID
                +" OR DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS]
                +" = "+PstDpApplication.FLD_DOC_STATUS_INCOMPLATE+")" 
                +" AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                +" = "+PstEmployee.NO_RESIGN
                +" AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                +" = "+employeeId
                +" AND DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_TAKEN_DATE]
                +" >= "+Formater.formatDate(new Date(), "yyyy-MM-dd")
                +" GROUP BY DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_EMPLOYEE_ID]
                +" ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                +" , DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_SUBMISSION_DATE]
                +" LIMIT 0,1"
                ;
                
            /////////////////OUT/////////////////////
            //System.out.println("[SQL] SessDayOfPayment.countDpApplicationValid : " + queryManVal);
            /////////////////////////////////////////
            
            dbRs = DBHandler.execQueryResult(queryManVal);
            ResultSet rs = dbRs.getResultSet();
            
            while(rs.next()){
                val = rs.getInt(2);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DBException ex) {
            ex.printStackTrace();
        } finally {
            DBResultSet.close(dbRs);
        }
        return val;
    }
    
    /**
     * Mencari dp yang masih dalam proses pengajuan dan belum diambil
     * @param employeeId
     * @return jumlah Dp yang masih valid
     */
    private static int countDpAppValid(long employeeId){
        int val = 0;
        DBResultSet dbRs = null;
        try {
            String queryManVal = "SELECT DISTINCT DPM."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_EMPLOYEE_ID]
                +" ,COUNT(DPD."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_DETAIL_ID]+")"
                +" FROM "+PstDpAppDetail.TBL_DP_APP_DETAIL+" AS DPD "
                +" INNER JOIN "+PstDpAppMain.TBL_DP_APP_MAIN+" AS DPM "
                +" ON DPD."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_ID]
                +" = DPM."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]
                +" INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP "
                +" ON DPM."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_EMPLOYEE_ID]
                +" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                +" WHERE (DPM."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_DOC_STATUS]
                +" = "+PstDpAppMain.FLD_DOC_STATUS_VALID+")"
                +" AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                +" = "+PstEmployee.NO_RESIGN
                +" AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                +" = "+employeeId
                +" AND DPD."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_TAKEN_DATE]
                +" >= "+Formater.formatDate(new Date(), "yyyy-MM-dd")
                +" GROUP BY DPM."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_EMPLOYEE_ID]
                +" ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                +" , DPM."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_SUBMISSION_DATE]
                +" LIMIT 0,1"
                ;
                
            /////////////////OUT/////////////////////
            System.out.println("[SQL] SessDayOfPayment.countDpAppValid : " + queryManVal);
            /////////////////////////////////////////
            
            dbRs = DBHandler.execQueryResult(queryManVal);
            ResultSet rs = dbRs.getResultSet();
            
            while(rs.next()){
                val = rs.getInt(2);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DBException ex) {
            ex.printStackTrace();
        } finally {
            DBResultSet.close(dbRs);
        }
        return val;
    }
    
    /**
     * Mencari DP Aplicatio yang belum di approved
     * @param employeeId
     * @return Vector list dp aplication yang belum di approve oleh Div Head
     */
    public static synchronized Vector listAplicationNotApproved(long employeeId){
        Vector vData = new Vector(1,1);
        DBResultSet dbRs = null;
        try {
            String queryManVal = "SELECT DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_DP_APPLICATION_ID]
                +" ,DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_EMPLOYEE_ID]
                +" ,DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_DP_ID]
                +" ,DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_SUBMISSION_DATE]
                +" ,DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_TAKEN_DATE]
                +" ,DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_APPROVAL_ID]
                +" ,DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS]
                +" FROM "+PstDpApplication.TBL_DP_APPLICATION+" AS DP "
                +" INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP "
                +" ON DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_EMPLOYEE_ID]
                +" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                +" WHERE (DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS]
                +" = "+PstDpApplication.FLD_DOC_STATUS_VALID
                +" OR DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS]
                +" = "+PstDpApplication.FLD_DOC_STATUS_INCOMPLATE+")"
                +" AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                +" = "+PstEmployee.NO_RESIGN
                +" AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                +" = "+employeeId
                +" AND DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_APPROVAL_ID]
                +" <= 0"
                +" AND DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_TAKEN_DATE]
                +" >= "+Formater.formatDate(new Date(), "yyyy-MM-dd")
                +" ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                +" , DP."+PstDpApplication.fieldNames[PstDpApplication.FLD_SUBMISSION_DATE]
                ;
                
            /////////////////OUT/////////////////////
           // System.out.println("[SQL] SessDayOfPayment.listAplicationNotApproved : " + queryManVal);
            /////////////////////////////////////////
            
            dbRs = DBHandler.execQueryResult(queryManVal);
            ResultSet rs = dbRs.getResultSet();
            
            while(rs.next()){
                DpApplication dpApplication = new DpApplication();
                dpApplication.setOID(rs.getLong(PstDpApplication.fieldNames[PstDpApplication.FLD_DP_APPLICATION_ID]));
                dpApplication.setEmployeeId(rs.getLong(PstDpApplication.fieldNames[PstDpApplication.FLD_EMPLOYEE_ID]));
                dpApplication.setDpId(rs.getLong(PstDpApplication.fieldNames[PstDpApplication.FLD_DP_ID]));
                dpApplication.setSubmissionDate(rs.getDate(PstDpApplication.fieldNames[PstDpApplication.FLD_SUBMISSION_DATE]));
                dpApplication.setTakenDate(rs.getDate(PstDpApplication.fieldNames[PstDpApplication.FLD_TAKEN_DATE]));
                dpApplication.setApprovalId(rs.getLong(PstDpApplication.fieldNames[PstDpApplication.FLD_APPROVAL_ID]));
                dpApplication.setDocStatus(rs.getInt(PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS]));
                vData.add(dpApplication);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DBException ex) {
            ex.printStackTrace();
        } finally {
            DBResultSet.close(dbRs);
        }
        return vData;
    }
    
}
