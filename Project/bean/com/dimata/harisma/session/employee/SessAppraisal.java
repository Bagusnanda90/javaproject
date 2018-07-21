/* 
 * Session Name  	:  SessAppraisal.java 
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
/* project package */
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBHandler;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;

public class SessAppraisal
{
	public static final String SESS_SRC_APPRAISAL = "SESSION_SRC_APPRAISAL";

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


    public static Vector searchAppraisal(SrcAppraisal srcAppraisal, int start, int recordToGet)
    {
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcAppraisal == null)
         	return new Vector(1,1);

        try {
            String sql = " SELECT "+
                	 " DISTINCT(EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_EMPLOYEE_APPRAISAL_ID]+")"+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_TOTAL_CRITERIA]+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_TOTAL_SCORE]+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_SCORE_AVERAGE]+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_OF_APPRAISAL]+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_PERFORMANCE]+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_LAST_APPRAISAL]+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_APPRAISOR_ID]+
                      //   " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_TIME_PERFORMANCE]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+
                         " , EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" AS APPRAISOR "+
                         " , DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
                         " , POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+
                         " , LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL]+
                         " , GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_NAME]+
                         " FROM "+PstEmpAppraisal.TBL_HR_EMPLOYEE_APPRAISAL + " EMAP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP2 "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEPT "+
                         " , "+PstPosition.TBL_HR_POSITION + " POST "+
                         " , "+PstLevel.TBL_HR_LEVEL + " LEV "+
                         " , "+PstGroupRank.TBL_HR_GROUP_RANK+ " GR "+
                         " WHERE "+
                         " EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_APPRAISOR_ID]+
                         " = EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " = DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " AND  EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         " = POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+
                         " = LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+
                         " AND LEV."+PstLevel.fieldNames[PstLevel.FLD_GROUP_RANK_ID]+
                         " = GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID] + " AND ";

            String whereClause = "";
            if((srcAppraisal.getEmployee()!= null)&& (srcAppraisal.getEmployee().length()>0)){
                Vector vectName = logicParser(srcAppraisal.getEmployee());
                if(vectName != null && vectName.size()>0){
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


            if((srcAppraisal.getAppraisor()!= null)&& (srcAppraisal.getAppraisor().length()>0)){
                Vector vectName = logicParser(srcAppraisal.getAppraisor());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }



            if((srcAppraisal.getAveragestart() != 0)&& (srcAppraisal.getAverageend() != 0)){
            	whereClause = whereClause + " EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_SCORE_AVERAGE]+
                    		  " BETWEEN "+srcAppraisal.getAveragestart()+" AND "+ srcAppraisal.getAverageend()+ " AND ";
            }

            if(srcAppraisal.getRank() != 0){
            	whereClause = whereClause + " GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID]+
                    		  " = "+srcAppraisal.getRank()+" AND ";
            }

            if((srcAppraisal.getDatestart() != null)&& (srcAppraisal.getDateend() != null)){
            	whereClause = whereClause + " EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_OF_APPRAISAL]+
                    		  " BETWEEN '"+Formater.formatDate(srcAppraisal.getDatestart(),"yyyy-MM-dd")+"' AND '"+
                              Formater.formatDate(srcAppraisal.getDateend(),"yyyy-MM-dd")+"' AND ";
            }


           sql = sql + whereClause + " 1 = 1";

            switch(srcAppraisal.getOrderBy()){
            	case FrmSrcAppraisal.ORDER_EMPLOYEE:
                    sql = sql + " ORDER BY  EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;

            	case FrmSrcAppraisal.ORDER_APPRAISOR:
                    sql = sql + " ORDER BY EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
            	case FrmSrcAppraisal.ORDER_RANK:
                    sql = sql + " ORDER BY GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_NAME];
                    break;
            	case FrmSrcAppraisal.ORDER_AVERAGE:
                    sql = sql + " ORDER BY EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_SCORE_AVERAGE];
           			break;

                case FrmSrcAppraisal.ORDER_APPRAISAL_DATE:
                    sql = sql + " ORDER BY EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_OF_APPRAISAL];
           			break;

                default:
                    sql = sql + "";
            }


            sql = sql + " LIMIT " + start + "," + recordToGet;

            System.out.println("\t SQL search Appraisal : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);

                EmpAppraisal empApp = new EmpAppraisal();
                Employee employee = new Employee();
                Employee employee2 = new Employee();
                Department department = new Department();
                Position position = new Position();
                Level level = new Level();
                GroupRank groupRank = new  GroupRank();

                empApp.setOID(rs.getLong(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_EMPLOYEE_APPRAISAL_ID]));
                empApp.setTotalCriteria(rs.getInt(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_TOTAL_CRITERIA]));
                empApp.setTotalScore(rs.getInt(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_TOTAL_SCORE]));
                empApp.setScoreAverage(rs.getDouble(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_SCORE_AVERAGE]));
                empApp.setDateOfAppraisal(rs.getDate(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_OF_APPRAISAL]));
                empApp.setDatePerformance(rs.getDate(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_PERFORMANCE]));
                empApp.setLastAppraisal(rs.getDate(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_LAST_APPRAISAL]));
                empApp.setAppraisorId(rs.getLong(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_APPRAISOR_ID]));
               // empApp.setTimePerformance(rs.getDate(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_TIME_PERFORMANCE]));
                vect.add(empApp);

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
				employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
				employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                vect.add(employee);

                employee2.setFullName(rs.getString("APPRAISOR"));
                vect.add(employee2);
                        
				department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                vect.add(level);

                groupRank.setGroupName(rs.getString(PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_NAME]));
                vect.add(groupRank);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);

	}
    
    public static Vector searchAppraisal(SrcAppraisal srcAppraisal, int start, int recordToGet,String where)
    {
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcAppraisal == null)
         	return new Vector(1,1);

        try {
            String sql = " SELECT "+
                	 " DISTINCT(EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_EMPLOYEE_APPRAISAL_ID]+")"+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_TOTAL_CRITERIA]+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_TOTAL_SCORE]+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_SCORE_AVERAGE]+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_OF_APPRAISAL]+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_PERFORMANCE]+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_LAST_APPRAISAL]+
                         " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_APPRAISOR_ID]+
                      //   " , EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_TIME_PERFORMANCE]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+
                         " , EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" AS APPRAISOR "+
                         " , DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
                         " , POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+
                         " , LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL]+
                         " , GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_NAME]+
                         " FROM "+PstEmpAppraisal.TBL_HR_EMPLOYEE_APPRAISAL + " EMAP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP2 "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEPT "+
                         " , "+PstPosition.TBL_HR_POSITION + " POST "+
                         " , "+PstLevel.TBL_HR_LEVEL + " LEV "+
                         " , "+PstGroupRank.TBL_HR_GROUP_RANK+ " GR "+
                         " WHERE "+
                         " EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_APPRAISOR_ID]+
                         " = EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " = DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " AND  EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         " = POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+
                         " = LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+
                         " AND LEV."+PstLevel.fieldNames[PstLevel.FLD_GROUP_RANK_ID]+
                         " = GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID] + " AND ";

            String whereClause = "";
            if((srcAppraisal.getEmployee()!= null)&& (srcAppraisal.getEmployee().length()>0)){
                Vector vectName = logicParser(srcAppraisal.getEmployee());
                if(vectName != null && vectName.size()>0){
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


            if((srcAppraisal.getAppraisor()!= null)&& (srcAppraisal.getAppraisor().length()>0)){
                Vector vectName = logicParser(srcAppraisal.getAppraisor());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }



            if((srcAppraisal.getAveragestart() != 0)&& (srcAppraisal.getAverageend() != 0)){
            	whereClause = whereClause + " EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_SCORE_AVERAGE]+
                    		  " BETWEEN "+srcAppraisal.getAveragestart()+" AND "+ srcAppraisal.getAverageend()+ " AND ";
            }

            if(srcAppraisal.getRank() != 0){
            	whereClause = whereClause + " GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID]+
                    		  " = "+srcAppraisal.getRank()+" AND ";
            }

            if((srcAppraisal.getDatestart() != null)&& (srcAppraisal.getDateend() != null)){
            	whereClause = whereClause + " EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_OF_APPRAISAL]+
                    		  " BETWEEN '"+Formater.formatDate(srcAppraisal.getDatestart(),"yyyy-MM-dd")+"' AND '"+
                              Formater.formatDate(srcAppraisal.getDateend(),"yyyy-MM-dd")+"' AND ";
            }


           sql = sql + whereClause + " 1 = 1";

            switch(srcAppraisal.getOrderBy()){
            	case FrmSrcAppraisal.ORDER_EMPLOYEE:
                    sql = sql + " ORDER BY  EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;

            	case FrmSrcAppraisal.ORDER_APPRAISOR:
                    sql = sql + " ORDER BY EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                    break;
            	case FrmSrcAppraisal.ORDER_RANK:
                    sql = sql + " ORDER BY GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_NAME];
                    break;
            	case FrmSrcAppraisal.ORDER_AVERAGE:
                    sql = sql + " ORDER BY EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_SCORE_AVERAGE];
           			break;

                case FrmSrcAppraisal.ORDER_APPRAISAL_DATE:
                    sql = sql + " ORDER BY EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_OF_APPRAISAL];
           			break;

                default:
                    sql = sql + "";
            }


            sql = sql + " LIMIT " + start + "," + recordToGet;

            System.out.println("\t SQL search Appraisal : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);

                EmpAppraisal empApp = new EmpAppraisal();
                Employee employee = new Employee();
                Employee employee2 = new Employee();
                Department department = new Department();
                Position position = new Position();
                Level level = new Level();
                GroupRank groupRank = new  GroupRank();

                empApp.setOID(rs.getLong(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_EMPLOYEE_APPRAISAL_ID]));
                empApp.setTotalCriteria(rs.getInt(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_TOTAL_CRITERIA]));
                empApp.setTotalScore(rs.getInt(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_TOTAL_SCORE]));
                empApp.setScoreAverage(rs.getDouble(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_SCORE_AVERAGE]));
                empApp.setDateOfAppraisal(rs.getDate(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_OF_APPRAISAL]));
                empApp.setDatePerformance(rs.getDate(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_PERFORMANCE]));
                empApp.setLastAppraisal(rs.getDate(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_LAST_APPRAISAL]));
                empApp.setAppraisorId(rs.getLong(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_APPRAISOR_ID]));
               // empApp.setTimePerformance(rs.getDate(PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_TIME_PERFORMANCE]));
                vect.add(empApp);

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
				employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
				employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                vect.add(employee);

                employee2.setFullName(rs.getString("APPRAISOR"));
                vect.add(employee2);
                        
				department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                vect.add(level);

                groupRank.setGroupName(rs.getString(PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_NAME]));
                vect.add(groupRank);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);

	}

    public static int countAppraisal(SrcAppraisal srcAppraisal)
    {
       DBResultSet dbrs = null;
       Vector result = new Vector(1,1);
       if (srcAppraisal == null)
         	return 0;

       try {
            String sql = " SELECT COUNT(DISTINCT EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_EMPLOYEE_APPRAISAL_ID]+")"+
                         " FROM "+PstEmpAppraisal.TBL_HR_EMPLOYEE_APPRAISAL + " EMAP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP2 "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEPT "+
                         " , "+PstPosition.TBL_HR_POSITION + " POST "+
                         " , "+PstLevel.TBL_HR_LEVEL + " LEV "+
                         " , "+PstGroupRank.TBL_HR_GROUP_RANK+ " GR "+
                         " WHERE "+
                         " EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_APPRAISOR_ID]+
                         " = EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " = DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " AND  EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         " = POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+
                         " = LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+
                         " AND LEV."+PstLevel.fieldNames[PstLevel.FLD_GROUP_RANK_ID]+
                         " = GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID] + " AND ";

            String whereClause = "";
            System.out.println("srcAppraisal.getEmployee() "+srcAppraisal.getEmployee());
            if((srcAppraisal.getEmployee()!= null)&& (srcAppraisal.getEmployee().length()>0)){
                Vector vectName = logicParser(srcAppraisal.getEmployee());
                if(vectName != null && vectName.size()>0){
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

            System.out.println("srcAppraisal.getAppraisor() "+srcAppraisal.getAppraisor());
            if((srcAppraisal.getAppraisor()!= null)&& (srcAppraisal.getAppraisor().length()>0)){
                Vector vectName = logicParser(srcAppraisal.getAppraisor());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if((srcAppraisal.getAveragestart() != 0)&& (srcAppraisal.getAverageend() != 0)){
            	whereClause = whereClause + " EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_SCORE_AVERAGE]+
                    		  " BETWEEN "+srcAppraisal.getAveragestart()+" AND "+ srcAppraisal.getAverageend()+ " AND ";
            }

            if(srcAppraisal.getRank() != 0){
            	whereClause = whereClause + " GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID]+
                    		  " = "+srcAppraisal.getRank()+" AND ";
            }

            if((srcAppraisal.getDatestart() != null)&& (srcAppraisal.getDateend() != null)){
            	whereClause = whereClause + " EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_OF_APPRAISAL]+
                    		  " BETWEEN '"+Formater.formatDate(srcAppraisal.getDatestart(),"yyyy-MM-dd")+"' AND '"+
                              Formater.formatDate(srcAppraisal.getDateend(),"yyyy-MM-dd")+"' AND ";
            }


            sql = sql + whereClause + " 1 = 1 ";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int num = 0;
            while(rs.next()) {
            	num = rs.getInt(1);
            }

            return num;
        } catch (Exception e) {
            System.out.println("\t Exception on  search Appraisal: " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return 0;

	}
    
    
    public static int countAppraisal(SrcAppraisal srcAppraisal,boolean isSecretary,boolean isHRD,boolean isGM,Vector DepOID)
    {
       DBResultSet dbrs = null;
       Vector result = new Vector(1,1);
       if (srcAppraisal == null)
         	return 0;

       try {
            String sql = " SELECT COUNT(DISTINCT EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_EMPLOYEE_APPRAISAL_ID]+")"+
                         " FROM "+PstEmpAppraisal.TBL_HR_EMPLOYEE_APPRAISAL + " EMAP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP2 "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEPT "+
                         " , "+PstPosition.TBL_HR_POSITION + " POST "+
                         " , "+PstLevel.TBL_HR_LEVEL + " LEV "+
                         " , "+PstGroupRank.TBL_HR_GROUP_RANK+ " GR "+
                         " WHERE ";
               //if(isSecretary && !isHRD && !isGM){
                 //   if(DepOID==null || )
               //}          
                         
                         
                        sql+= " EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_APPRAISOR_ID]+
                         " = EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " = DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " AND  EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         " = POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+
                         " = LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+
                         " AND LEV."+PstLevel.fieldNames[PstLevel.FLD_GROUP_RANK_ID]+
                         " = GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID] + " AND ";

            String whereClause = "";
            System.out.println("srcAppraisal.getEmployee() "+srcAppraisal.getEmployee());
            if((srcAppraisal.getEmployee()!= null)&& (srcAppraisal.getEmployee().length()>0)){
                Vector vectName = logicParser(srcAppraisal.getEmployee());
                if(vectName != null && vectName.size()>0){
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

            System.out.println("srcAppraisal.getAppraisor() "+srcAppraisal.getAppraisor());
            if((srcAppraisal.getAppraisor()!= null)&& (srcAppraisal.getAppraisor().length()>0)){
                Vector vectName = logicParser(srcAppraisal.getAppraisor());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if((srcAppraisal.getAveragestart() != 0)&& (srcAppraisal.getAverageend() != 0)){
            	whereClause = whereClause + " EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_SCORE_AVERAGE]+
                    		  " BETWEEN "+srcAppraisal.getAveragestart()+" AND "+ srcAppraisal.getAverageend()+ " AND ";
            }

            if(srcAppraisal.getRank() != 0){
            	whereClause = whereClause + " GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID]+
                    		  " = "+srcAppraisal.getRank()+" AND ";
            }

            if((srcAppraisal.getDatestart() != null)&& (srcAppraisal.getDateend() != null)){
            	whereClause = whereClause + " EMAP."+PstEmpAppraisal.fieldNames[PstEmpAppraisal.FLD_DATE_OF_APPRAISAL]+
                    		  " BETWEEN '"+Formater.formatDate(srcAppraisal.getDatestart(),"yyyy-MM-dd")+"' AND '"+
                              Formater.formatDate(srcAppraisal.getDateend(),"yyyy-MM-dd")+"' AND ";
            }


            sql = sql + whereClause + " 1 = 1 ";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int num = 0;
            while(rs.next()) {
            	num = rs.getInt(1);
            }

            return num;
        } catch (Exception e) {
            System.out.println("\t Exception on  search Appraisal: " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return 0;

	}
    
    public static double calScore(String score, double factor, String excelentScoreCode, double excValue){
         try{
             if(score.trim().equals(excelentScoreCode)){
                 return excValue*factor;
             }
             return Double.parseDouble(score)*factor;
         }catch(Exception exc){
             return 0.0;
         }
    }
    
}
