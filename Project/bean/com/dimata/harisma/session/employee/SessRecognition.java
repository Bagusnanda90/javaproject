/* 
 * Session Name  	:  SessRecognition.java 
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
package com.dimata.harisma.session.employee;/* java package */ 
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
/* project package */
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBHandler;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;

public class SessRecognition{
    public static final String SESS_SRC_RECOGNITION = "SESSION_SRC_RECOGNITION";
    
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
    
    public static Vector searchRecognition(SrcRecognition srcrecognition, int start, int recordToGet){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcrecognition == null)
            return new Vector(1,1);
        try {
            String sql = " SELECT REC."+PstRecognition.fieldNames[PstRecognition.FLD_RECOGNITION_ID]+
                	 ", REC."+PstRecognition.fieldNames[PstRecognition.FLD_EMPLOYEE_ID]+
                	 ", REC."+PstRecognition.fieldNames[PstRecognition.FLD_RECOG_DATE]+
                	 ", REC."+PstRecognition.fieldNames[PstRecognition.FLD_POINT]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         ", DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
                         ", POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+
                         " FROM "+
                         " "  +PstRecognition.TBL_HR_RECOGNITION + " REC "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEP "+
                         " , "+PstPosition.TBL_HR_POSITION + " POS "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " WHERE "+
                         " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         " = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " AND REC."+PstRecognition.fieldNames[PstRecognition.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
                         
            String whereClause = "";
            if((srcrecognition.getFullName()!= null)&& (srcrecognition.getFullName().length()>0)){
                Vector vectName = logicParser(srcrecognition.getFullName());
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

            if(srcrecognition.getDepartment() != 0)
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                			" = "+srcrecognition.getDepartment() + " AND ";

            if(srcrecognition.getPosition() != 0)
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                			" = "+ srcrecognition.getPosition() + " AND ";

            if(srcrecognition.getEmpNumber().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                			" = "+srcrecognition.getEmpNumber() + " AND ";
            }

            if((srcrecognition.getRecogDateFrom() != null) && (srcrecognition.getRecogDateTo() != null)){
            	whereClause = whereClause +" REC."+ PstRecognition.fieldNames[PstRecognition.FLD_RECOG_DATE] + " BETWEEN '"+
                              Formater.formatDate(srcrecognition.getRecogDateFrom(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcrecognition.getRecogDateTo(), "yyyy-MM-dd")+ "' AND ";
            }

            if(whereClause != null && whereClause.length()>0){
            	//whereClause = whereClause.substring(0,whereClause.length()-4);
                System.out.println("\twhereClause.length() = " + whereClause.length());
                whereClause += " 1 = 1 ";
            	sql = sql + " AND " + whereClause;
            }
            
            sql = sql + " LIMIT " + start + "," + recordToGet;
            
            //System.out.println("\t SQL searchEmpSchedule : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);

                Recognition recognition = new Recognition();
                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();

                recognition.setOID(rs.getLong(PstRecognition.fieldNames[PstRecognition.FLD_RECOGNITION_ID]));
                recognition.setEmployeeId(rs.getLong(PstRecognition.fieldNames[PstRecognition.FLD_EMPLOYEE_ID]));
                recognition.setRecogDate(rs.getDate(PstRecognition.fieldNames[PstRecognition.FLD_RECOG_DATE]));
                recognition.setPoint(rs.getInt(PstRecognition.fieldNames[PstRecognition.FLD_POINT]));
                vect.add(recognition);

                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                employee.setSectionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                vect.add(employee);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

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

    public static int getCountSearch (SrcRecognition srcrecognition){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcrecognition == null)
            return 0;
        try {
            String sql = " SELECT COUNT(REC."+PstRecognition.fieldNames[PstRecognition.FLD_RECOGNITION_ID]+") " +
                         " FROM "+
                         " "  +PstRecognition.TBL_HR_RECOGNITION + " REC "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEP "+
                         " , "+PstPosition.TBL_HR_POSITION + " POS "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " WHERE "+
                         " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         " = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " AND REC."+PstRecognition.fieldNames[PstRecognition.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String whereClause = "";
            if((srcrecognition.getFullName()!= null)&& (srcrecognition.getFullName().length()>0)){
                Vector vectName = logicParser(srcrecognition.getFullName());
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

            if(srcrecognition.getDepartment() != 0)
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                			" = "+srcrecognition.getDepartment() + " AND ";

            if(srcrecognition.getPosition() != 0)
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                			" = "+ srcrecognition.getPosition() + " AND ";

            if(srcrecognition.getEmpNumber().compareToIgnoreCase("0") > 0) {
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                			" = "+srcrecognition.getEmpNumber() + " AND ";
            }

            if((srcrecognition.getRecogDateFrom() != null) && (srcrecognition.getRecogDateTo() != null)){
            	whereClause = whereClause +" REC."+ PstRecognition.fieldNames[PstRecognition.FLD_RECOG_DATE] + " BETWEEN '"+
                              Formater.formatDate(srcrecognition.getRecogDateFrom(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcrecognition.getRecogDateTo(), "yyyy-MM-dd")+ "' AND ";
            }

            if(whereClause != null && whereClause.length()>0){
            	//whereClause = whereClause.substring(0,whereClause.length()-4);
                System.out.println("\twhereClause.length() = " + whereClause.length());
                whereClause += " 1 = 1 ";
            	sql = sql + " AND " + whereClause;
            }
            
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
    
    public static Vector getPointMonthly(int year, int quarter) {
        Vector vResult = new Vector(1, 1);
        DBResultSet dbrs = null;
        String sql = "";

        int start = (quarter - 1) * 4;
        Calendar dateStartM1 = new GregorianCalendar(year,start,21);
        Calendar dateEndM1 = new GregorianCalendar(year,start,21);
        Calendar dateStartM2 = new GregorianCalendar(year,start,21);
        Calendar dateEndM2 = new GregorianCalendar(year,start,21);
        Calendar dateStartM3 = new GregorianCalendar(year,start,21);
        Calendar dateEndM3 = new GregorianCalendar(year,start,21);
        Calendar dateStartM4 = new GregorianCalendar(year,start,21);
        Calendar dateEndM4 = new GregorianCalendar(year,start,21);
        
        dateStartM2.add(Calendar.MONTH,1);
        dateStartM3.add(Calendar.MONTH,2);
        dateStartM4.add(Calendar.MONTH,3);
        
        dateEndM1.add(Calendar.MONTH,1);dateEndM1.add(Calendar.DATE,-1);
        dateEndM2.add(Calendar.MONTH,2);dateEndM2.add(Calendar.DATE,-1);
        dateEndM3.add(Calendar.MONTH,3);dateEndM3.add(Calendar.DATE,-1);
        dateEndM4.add(Calendar.MONTH,4);dateEndM4.add(Calendar.DATE,-1);
        
        Vector vM1 = new Vector(1,1);
        vM1 = getPointByRange(dateStartM1, dateEndM1);
        Vector vM2 = new Vector(1,1);
        vM2 = getPointByRange(dateStartM2, dateEndM2);
        Vector vM3 = new Vector(1,1);
        vM3 = getPointByRange(dateStartM3, dateEndM3);
        Vector vM4 = new Vector(1,1);
        vM4 = getPointByRange(dateStartM4, dateEndM4);

        Vector vEmpId = new Vector(1, 1);
        Vector vEmpNum = new Vector(1, 1);
        Vector vEmp = new Vector(1, 1);
        Vector vPoint = new Vector(1, 1);
        Vector vName = new Vector(1, 1);
        Vector vDep = new Vector(1, 1);
        Vector vPos = new Vector(1, 1);
        
        String sDateStart = String.valueOf(dateStartM1.get(Calendar.YEAR)) + "-" +
                            String.valueOf(dateStartM1.get(Calendar.MONTH)+1) + "-" +
                            String.valueOf(dateStartM1.get(Calendar.DATE));
        String sDateEnd = String.valueOf(dateEndM4.get(Calendar.YEAR)) + "-" +
                          String.valueOf(dateEndM4.get(Calendar.MONTH)+1) + "-" +
                          String.valueOf(dateEndM4.get(Calendar.DATE));
        
        try {
            sql = " select emp.employee_id, emp.employee_num, emp.full_name, dep.department, pos.position, sum(rec.point) as point " +
                  " from hr_employee emp, hr_department dep, hr_position pos, hr_recognition rec " + 
                  " where emp.resigned = 0 and emp.employee_id = rec.employee_id " +
                  " and emp.department_id = dep.department_id " +
                  " and emp.position_id = pos.position_id " +
                  " and (rec.recog_date between '" + sDateStart + "' and '" + sDateEnd + "') " + 
                  " group by emp.employee_id order by point desc ";
            
            System.out.println("\t--- sql ---\n" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                vEmpId.add(String.valueOf(rs.getString("EMPLOYEE_ID")));
                vEmpNum.add(String.valueOf(rs.getString("EMPLOYEE_NUM")));
                vEmp.add(String.valueOf(rs.getString("FULL_NAME")));
                vPoint.add(String.valueOf(rs.getString("POINT")));
                vName.add(String.valueOf(rs.getString("FULL_NAME")));
                vDep.add(String.valueOf(rs.getString("DEPARTMENT")));
                vPos.add(String.valueOf(rs.getString("POSITION")));
            }
            vResult.add(vEmp);   //0
            vResult.add(vPoint); //1
            vResult.add(vName);  //2
            vResult.add(vDep);   //3
            vResult.add(vPos);   //4
            vResult.add(vEmpId); //5
            vResult.add(vEmpNum);//6
        } catch (Exception e) {
            System.out.println("\t Exception on getPoint : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }

        vResult.add(vM1);//7
        vResult.add(vM2);//8
        vResult.add(vM3);//9
        vResult.add(vM4);//10
        
        return vResult;
    }

    public static Vector getPointByRange(Calendar dateStart, Calendar dateEnd) {
        Vector vResult = new Vector(1, 1);
        Vector vEmpId = new Vector(1, 1);
        Vector vPoint = new Vector(1, 1);
        
        DBResultSet dbrs = null;
        String sql = "";
        
        String sDateStart = String.valueOf(dateStart.get(Calendar.YEAR)) + "-" +
                            String.valueOf(dateStart.get(Calendar.MONTH)+1) + "-" +
                            String.valueOf(dateStart.get(Calendar.DATE));
        String sDateEnd = String.valueOf(dateEnd.get(Calendar.YEAR)) + "-" +
                          String.valueOf(dateEnd.get(Calendar.MONTH)+1) + "-" +
                          String.valueOf(dateEnd.get(Calendar.DATE));

        try {
            sql = " select emp.employee_id, sum(rec.point) as point " +
                  " from hr_employee emp, hr_department dep, hr_position pos, hr_recognition rec " + 
                  " where emp.resigned = 0 and emp.employee_id = rec.employee_id " +
                  " and emp.department_id = dep.department_id " +
                  " and emp.position_id = pos.position_id " +
                  " and (rec.recog_date between '" + sDateStart + "' and '" + sDateEnd + "') " + 
                  " group by emp.employee_id";
            
            System.out.println("\t--- sql ---\n" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                vEmpId.add(String.valueOf(rs.getLong("EMPLOYEE_ID")));
                vPoint.add(String.valueOf(rs.getInt("POINT")));
            }
            vResult.add(vEmpId);
            vResult.add(vPoint);
            return vResult;
        } catch (Exception e) {
            System.out.println("\t Exception on getPoint : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        
        return new Vector(1,1);
    }
    
    public static Vector getPointQuarterly(int year) {
        Vector vResult = new Vector(1, 1);
        DBResultSet dbrs = null;
        String sql = "";

        Calendar dateStartQ1 = new GregorianCalendar(year,0,21);
        Calendar dateEndQ1 = new GregorianCalendar(year,4,20);
        Calendar dateStartQ2 = new GregorianCalendar(year,4,21);
        Calendar dateEndQ2 = new GregorianCalendar(year,8,20);
        Calendar dateStartQ3 = new GregorianCalendar(year,8,21);
        Calendar dateEndQ3 = new GregorianCalendar(year+1,0,20);
        
        Vector vQ1 = new Vector(1,1);
        vQ1 = getPointByRange(dateStartQ1, dateEndQ1);
        //System.out.println("\t..vQ1.size() = " + (((Vector) vQ1.get(0)).size()));
        Vector vQ2 = new Vector(1,1);
        vQ2 = getPointByRange(dateStartQ2, dateEndQ2);
        //System.out.println("\t..vQ2.size() = " + (((Vector) vQ2.get(0)).size()));
        Vector vQ3 = new Vector(1,1);
        vQ3 = getPointByRange(dateStartQ3, dateEndQ3);
        //System.out.println("\t..vQ3.size() = " + (((Vector) vQ3.get(0)).size()));

        Vector vEmpId = new Vector(1, 1);
        Vector vEmpNum = new Vector(1, 1);
        Vector vEmp = new Vector(1, 1);
        Vector vPoint = new Vector(1, 1);
        Vector vName = new Vector(1, 1);
        Vector vDep = new Vector(1, 1);
        Vector vPos = new Vector(1, 1);
        
        String sDateStart = String.valueOf(dateStartQ1.get(Calendar.YEAR)) + "-" +
                            String.valueOf(dateStartQ1.get(Calendar.MONTH)+1) + "-" +
                            String.valueOf(dateStartQ1.get(Calendar.DATE));
        String sDateEnd = String.valueOf(dateEndQ3.get(Calendar.YEAR)) + "-" +
                          String.valueOf(dateEndQ3.get(Calendar.MONTH)+1) + "-" +
                          String.valueOf(dateEndQ3.get(Calendar.DATE));
        
        try {
            sql = " select emp.employee_id, emp.employee_num, emp.full_name, dep.department, pos.position, sum(rec.point) as point " +
                  " from hr_employee emp, hr_department dep, hr_position pos, hr_recognition rec " + 
                  " where emp.resigned = 0 and emp.employee_id = rec.employee_id " +
                  " and emp.department_id = dep.department_id " +
                  " and emp.position_id = pos.position_id " +
                  " and (rec.recog_date between '" + sDateStart + "' and '" + sDateEnd + "') " + 
                  " group by emp.employee_id order by point desc ";
            
            System.out.println("\t--- sql ---\n" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                vEmpId.add(String.valueOf(rs.getString("EMPLOYEE_ID")));
                vEmpNum.add(String.valueOf(rs.getString("EMPLOYEE_NUM")));
                vEmp.add(String.valueOf(rs.getString("FULL_NAME")));
                vPoint.add(String.valueOf(rs.getString("POINT")));
                vName.add(String.valueOf(rs.getString("FULL_NAME")));
                vDep.add(String.valueOf(rs.getString("DEPARTMENT")));
                vPos.add(String.valueOf(rs.getString("POSITION")));
            }
            vResult.add(vEmp);   //0
            vResult.add(vPoint); //1
            vResult.add(vName);  //2
            vResult.add(vDep);   //3
            vResult.add(vPos);   //4
            vResult.add(vEmpId); //5
            vResult.add(vEmpNum);//6
        } catch (Exception e) {
            System.out.println("\t Exception on getPoint : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }

        vResult.add(vQ1);//7
        vResult.add(vQ2);//8
        vResult.add(vQ3);//9
        
        return vResult;
    }
    
    public static Vector getDistinctYear() {
        Vector vResult = new Vector(1, 1);
        DBResultSet dbrs = null;
        String sql = "";

        try {
            sql = " select distinct year(recog_date) as yr from hr_recognition order by yr";
            
            System.out.println("\t--- sql ---\n" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                vResult.add(String.valueOf(rs.getString("YR")));
            }
        } catch (Exception e) {
            System.out.println("\t Exception on getDistinctYear : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return vResult;
    }
    
    public static Vector getPointYearly() {
        Vector vResult = new Vector(1, 1);
        DBResultSet dbrs = null;
        String sql = "";

        Vector vYear = new Vector(1, 1);
        vYear = SessRecognition.getDistinctYear();

        Calendar dateStartY = new GregorianCalendar(Integer.parseInt((String)vYear.get(0)),0,21);
        Calendar dateEndY = new GregorianCalendar(Integer.parseInt((String)vYear.get(vYear.size()-1))+1,0,20);

        Vector vEmpId = new Vector(1, 1);
        Vector vEmpNum = new Vector(1, 1);
        Vector vEmp = new Vector(1, 1);
        Vector vPoint = new Vector(1, 1);
        Vector vName = new Vector(1, 1);
        Vector vDep = new Vector(1, 1);
        Vector vPos = new Vector(1, 1);
        
        String sDateStart = String.valueOf(dateStartY.get(Calendar.YEAR)) + "-" +
                            String.valueOf(dateStartY.get(Calendar.MONTH)+1) + "-" +
                            String.valueOf(dateStartY.get(Calendar.DATE));
        String sDateEnd = String.valueOf(dateEndY.get(Calendar.YEAR)) + "-" +
                          String.valueOf(dateEndY.get(Calendar.MONTH)+1) + "-" +
                          String.valueOf(dateEndY.get(Calendar.DATE));
        
        try {
            sql = " select emp.employee_id, emp.employee_num, emp.full_name, dep.department, pos.position, sum(rec.point) as point " +
                  " from hr_employee emp, hr_department dep, hr_position pos, hr_recognition rec " + 
                  " where emp.resigned = 0 and emp.employee_id = rec.employee_id " +
                  " and emp.department_id = dep.department_id " +
                  " and emp.position_id = pos.position_id " +
                  " and (rec.recog_date between '" + sDateStart + "' and '" + sDateEnd + "') " + 
                  " group by emp.employee_id order by point desc ";
            
            System.out.println("\t--- sql ---\n" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                vEmpId.add(String.valueOf(rs.getString("EMPLOYEE_ID")));
                vEmpNum.add(String.valueOf(rs.getString("EMPLOYEE_NUM")));
                vEmp.add(String.valueOf(rs.getString("FULL_NAME")));
                vPoint.add(String.valueOf(rs.getString("POINT")));
                vName.add(String.valueOf(rs.getString("FULL_NAME")));
                vDep.add(String.valueOf(rs.getString("DEPARTMENT")));
                vPos.add(String.valueOf(rs.getString("POSITION")));
            }
            vResult.add(vEmp);   //0
            vResult.add(vPoint); //1
            vResult.add(vName);  //2
            vResult.add(vDep);   //3
            vResult.add(vPos);   //4
            vResult.add(vEmpId); //5
            vResult.add(vEmpNum);//6
        } catch (Exception e) {
            System.out.println("\t Exception on getPoint : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }

        Vector vYy = new Vector(1, 1);
        for (int i=0; i<vYear.size(); i++) {
            Calendar dateStartYy = new GregorianCalendar(Integer.parseInt((String)vYear.get(i)),0,21);
            Calendar dateEndYy = new GregorianCalendar(Integer.parseInt((String)vYear.get(i))+1,0,20);
            vYy = getPointByRange(dateStartYy, dateEndYy);
            System.out.println("\t..vYy.size() = " + (((Vector) vYy.get(0)).size()));
            vResult.add(vYy);//7...
        }
        //vResult.add(vQ1);//7
        //vResult.add(vQ2);//8
        //vResult.add(vQ3);//9
        
        return vResult;
    }
}
