/*
 * Session Name  	:  SessLeave.java 
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
import java.io.*; 
import java.util.*; 
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.util.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
/* project package */
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBHandler;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;

public class SessLeave{
    public static final String SESS_SRC_LEAVE = "SESSION_SRC_LEAVE";

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

    public static Vector searchLeave(SrcLeave srcleave, int start, int recordToGet){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcleave == null)
            return new Vector(1,1);
        try {
            String sql = " SELECT LV."+PstLeave.fieldNames[PstLeave.FLD_LEAVE_ID]+
                	 ", LV."+PstLeave.fieldNames[PstLeave.FLD_EMPLOYEE_ID]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_SUBMIT_DATE]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_LEAVE_FROM]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_LEAVE_TO]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_DURATION]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_REASON]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_LONG_LEAVE]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_ANNUAL_LEAVE]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_LEAVE_WO_PAY]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_MATERNITY_LEAVE]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_DAY_OFF]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_PUBLIC_HOLIDAY]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_EXTRA_DAY_OFF]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_SICK_LEAVE]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_PERIOD_AL_FROM]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_PERIOD_AL_TO]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_AL_ENTITLEMENT]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_AL_TAKEN]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_AL_BALANCE]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_PERIOD_LL_FROM]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_PERIOD_LL_TO]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_LL_ENTITLEMENT]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_LL_TAKEN]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_LL_BALANCE]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_APR_SPV_DATE]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_APR_DEPTHEAD_DATE]+
                         ", LV."+PstLeave.fieldNames[PstLeave.FLD_APR_PMGR_DATE]+
                         //", LV."+PstLeave.fieldNames[PstLeave.]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+
                         " FROM "+
                         " "  +PstLeave.TBL_HR_LEAVE + " LV "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " WHERE "+
                         " LV."+PstLeave.fieldNames[PstLeave.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
                         
            String whereClause = "";
            if((srcleave.getFullName()!= null)&& (srcleave.getFullName().length()>0)){
                Vector vectName = logicParser(srcleave.getFullName());
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

            if(srcleave.getDepartment().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                			" = "+srcleave.getDepartment() + " AND ";
            }

            if(srcleave.getPosition().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                			" = "+srcleave.getPosition() + " AND ";
            }

            if(srcleave.getSection().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                			" = "+srcleave.getSection() + " AND ";
            }

            if(whereClause != null && whereClause.length()>0){
            	//whereClause = whereClause.substring(0,whereClause.length()-4);
                System.out.println("\twhereClause.length() = " + whereClause.length());
                whereClause += " 1 = 1 ";
            	sql = sql + " AND " + whereClause;
            }
            
            sql = sql + " AND ("+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = 0)";

            sql = sql + " LIMIT " + start + "," + recordToGet;
            
            //System.out.println("\t SQL searchEmpSchedule : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);

                Leave leave = new Leave();
                Employee employee = new Employee();

			leave.setOID(rs.getLong(PstLeave.fieldNames[PstLeave.FLD_LEAVE_ID]));
			leave.setEmployeeId(rs.getLong(PstLeave.fieldNames[PstLeave.FLD_EMPLOYEE_ID]));
			leave.setSubmitDate(rs.getDate(PstLeave.fieldNames[PstLeave.FLD_SUBMIT_DATE]));
			leave.setLeaveFrom(rs.getDate(PstLeave.fieldNames[PstLeave.FLD_LEAVE_FROM]));
			leave.setLeaveTo(rs.getDate(PstLeave.fieldNames[PstLeave.FLD_LEAVE_TO]));
			leave.setDuration(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_DURATION]));
			leave.setReason(rs.getString(PstLeave.fieldNames[PstLeave.FLD_REASON]));
			leave.setLongLeave(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_LONG_LEAVE]));
			leave.setAnnualLeave(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_ANNUAL_LEAVE]));
			leave.setLeaveWoPay(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_LEAVE_WO_PAY]));
			leave.setMaternityLeave(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_MATERNITY_LEAVE]));
			leave.setDayOff(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_DAY_OFF]));
			leave.setPublicHoliday(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_PUBLIC_HOLIDAY]));
			leave.setExtraDayOff(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_EXTRA_DAY_OFF]));
			leave.setSickLeave(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_SICK_LEAVE]));
			leave.setPeriodAlFrom(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_PERIOD_AL_FROM]));
			leave.setPeriodAlTo(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_PERIOD_AL_TO]));
			leave.setAlEntitlement(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_AL_ENTITLEMENT]));
			leave.setAlTaken(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_AL_TAKEN]));
			leave.setAlBalance(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_AL_BALANCE]));
			leave.setPeriodLlFrom(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_PERIOD_LL_FROM]));
			leave.setPeriodLlTo(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_PERIOD_LL_TO]));
			leave.setLlEntitlement(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_LL_ENTITLEMENT]));
			leave.setLlTaken(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_LL_TAKEN]));
			leave.setLlBalance(rs.getInt(PstLeave.fieldNames[PstLeave.FLD_LL_BALANCE]));
			leave.setAprSpvDate(rs.getDate(PstLeave.fieldNames[PstLeave.FLD_APR_SPV_DATE]));
			leave.setAprDeptheadDate(rs.getDate(PstLeave.fieldNames[PstLeave.FLD_APR_DEPTHEAD_DATE]));
			leave.setAprPmgrDate(rs.getDate(PstLeave.fieldNames[PstLeave.FLD_APR_PMGR_DATE]));
                        vect.add(leave);

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

    public static int getCountSearch (SrcLeave srcleave){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcleave == null)
            return 0;
        try {
            String sql = " SELECT COUNT(LV."+PstLeave.fieldNames[PstLeave.FLD_LEAVE_ID]+") " +
                         " FROM "+
                         " "  +PstLeave.TBL_HR_LEAVE + " LV "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " WHERE "+
                         " LV."+PstLeave.fieldNames[PstLeave.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
                         
            String whereClause = "";
            if((srcleave.getFullName()!= null)&& (srcleave.getFullName().length()>0)){
                Vector vectName = logicParser(srcleave.getFullName());
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

            if(srcleave.getDepartment().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                			" = "+srcleave.getDepartment() + " AND ";
            }

            if(srcleave.getPosition().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                			" = "+srcleave.getPosition() + " AND ";
            }

            if(srcleave.getSection().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                			" = "+srcleave.getSection() + " AND ";
            }

            if(whereClause != null && whereClause.length()>0){
            	//whereClause = whereClause.substring(0,whereClause.length()-4);
                System.out.println("\twhereClause.length() = " + whereClause.length());
                whereClause += " 1 = 1 ";
            	sql = sql + " AND " + whereClause;
            }

            //System.out.println("\t SQL searchEmpSchedule : " + sql);    
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

    public static int getTakenAnualLeave(long empOID){
        int taken = 0;
        if(empOID!=0){
            DBResultSet dbrs = null;
	        try{
                String sql = "SELECT SUM("+PstLeave.fieldNames[PstLeave.FLD_ANNUAL_LEAVE]+") AS AN "+/*, SUM("+PstLeave.fieldNames[PstLeave.FLD_LEAVE_WO_PAY]+") AS WO, "+
                    "SUM("+PstLeave.fieldNames[PstLeave.FLD_MATERNITY_LEAVE]+") AS MAT, SUM("+PstLeave.fieldNames[PstLeave.FLD_DAY_OFF]+") AS DOF,"+
                    "SUM("+PstLeave.fieldNames[PstLeave.FLD_PUBLIC_HOLIDAY]+") AS PUB, SUM("+PstLeave.fieldNames[PstLeave.FLD_EXTRA_DAY_OFF]+") AS EX,"+
                    "SUM("+PstLeave.fieldNames[PstLeave.FLD_SICK_LEAVE]+") AS SIC*/
                    " FROM "+PstLeave.TBL_HR_LEAVE+
                    " WHERE "+PstLeave.fieldNames[PstLeave.FLD_EMPLOYEE_ID]+"="+empOID;//("+PstLeave.fieldNames[PstLeave.FLD_LEAVE_TYPE]+"="+PstLeave.LEAVE_TYPE_AL+")";


                System.out.println(sql);

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while(rs.next()){
                    taken = rs.getInt("AN");
                /*    taken = taken + rs.getInt("WO");
                    taken = taken + rs.getInt("MAT");
                    taken = taken + rs.getInt("DOF");
                    taken = taken + rs.getInt("PUB");
                    taken = taken + rs.getInt("EX");
                    taken = taken + rs.getInt("SIC");*/
                }

                rs.close();
	
	        }
	        catch(Exception e){
	         	System.out.println("exception e : "+e.toString());
	        }
	        finally{
                DBResultSet.close(dbrs);
	        }
        }

        return taken;
    }


    public static int getTakenLongLeave(long empOID){
        int taken = 0;
        if(empOID!=0){
            DBResultSet dbrs = null;
	        try{
                String sql = "SELECT SUM("+PstLeave.fieldNames[PstLeave.FLD_LONG_LEAVE]+") AS AN "+/*, SUM("+PstLeave.fieldNames[PstLeave.FLD_LEAVE_WO_PAY]+") AS WO, "+
                    "SUM("+PstLeave.fieldNames[PstLeave.FLD_MATERNITY_LEAVE]+") AS MAT, SUM("+PstLeave.fieldNames[PstLeave.FLD_DAY_OFF]+") AS DOF,"+
                    "SUM("+PstLeave.fieldNames[PstLeave.FLD_PUBLIC_HOLIDAY]+") AS PUB, SUM("+PstLeave.fieldNames[PstLeave.FLD_EXTRA_DAY_OFF]+") AS EX,"+
                    "SUM("+PstLeave.fieldNames[PstLeave.FLD_SICK_LEAVE]+") AS SIC */
                    " FROM "+PstLeave.TBL_HR_LEAVE+
                    " WHERE "+PstLeave.fieldNames[PstLeave.FLD_EMPLOYEE_ID]+"="+empOID;//+PstLeave.fieldNames[PstLeave.FLD_LEAVE_TYPE]+"="+PstLeave.LEAVE_TYPE_LL;

                System.out.println(sql);

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while(rs.next()){
                    taken = rs.getInt("AN");
                  /*  taken = taken + rs.getInt("WO");
                    taken = taken + rs.getInt("MAT");
                    taken = taken + rs.getInt("DOF");
                    taken = taken + rs.getInt("PUB");
                    taken = taken + rs.getInt("EX");
                    taken = taken + rs.getInt("SIC");*/
                }

                rs.close();
	
	        }
	        catch(Exception e){
	         	System.out.println("exception e : "+e.toString());
	        }
	        finally{
                DBResultSet.close(dbrs);
	        }
        }

        return taken;
    }

    public static int getTakenLongLeaveByDepartment(long departmentOID){
        int taken = 0;
        if(departmentOID!=0){
            DBResultSet dbrs = null;
	        try{
                String sql = "SELECT SUM("+PstLeave.fieldNames[PstLeave.FLD_LONG_LEAVE]+") AS AN "+
                    " FROM "+PstLeave.TBL_HR_LEAVE+" AS LV "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+
                    " LV."+PstLeave.fieldNames[PstLeave.FLD_EMPLOYEE_ID]+
                    " WHERE EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentOID;

                sql = sql + " AND (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = 0)";

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while(rs.next()){
                    taken = rs.getInt("AN");
                }

                rs.close();
	
	        }
	        catch(Exception e){
	         	System.out.println("exception e : "+e.toString());
	        }
	        finally{
                DBResultSet.close(dbrs);
	        }
        }

        return taken;
    }

    public static int getTakenAnualLeaveByDepartment(long departmentOID){
        int taken = 0;
        if(departmentOID!=0){
            DBResultSet dbrs = null;
	        try{
                String sql = "SELECT SUM("+PstLeave.fieldNames[PstLeave.FLD_ANNUAL_LEAVE]+") AS AN "+
                    " FROM "+PstLeave.TBL_HR_LEAVE+" AS LV "+
                    " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+
                    " LV."+PstLeave.fieldNames[PstLeave.FLD_EMPLOYEE_ID]+
                    " WHERE EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentOID;

                sql = sql + " AND (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = 0)";

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while(rs.next()){
                    taken = rs.getInt("AN");
                }

                rs.close();
	
	        }
	        catch(Exception e){
	         	System.out.println("exception e : "+e.toString());
	        }
	        finally{
                DBResultSet.close(dbrs);
	        }
        }

        return taken;
    }


}
