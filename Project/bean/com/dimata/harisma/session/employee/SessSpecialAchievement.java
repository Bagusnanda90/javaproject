/* 
 * Session Name  	:  SessSpecialAchievement.java 
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
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;

public class SessSpecialAchievement
{
	public static final String SESS_SRC_SPECIALACHIEVEMENT = "SESSION_SRC_SPECIALACHIEVEMENT";

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


	public static Vector searchSpecialAchievement(SrcSpecialAchievement srcSpecialAchievement, int start, int recordToGet)
    {
     	DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcSpecialAchievement == null)
            return new Vector(1,1);
        try {
            String sql = " SELECT DISTINCT(SA."+PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_SPECIAL_ACHIEVEMENT_ID]+ ")"+
                	 	 ", SA."+PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_EMPLOYEE_ID]+
                         ", SA."+PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_TYPE_OF_AWARD]+
                	 	 ", SA."+PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_PRESENTED_BY]+
                         ", SA."+PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_DATE]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+
                         ", DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
                         ", POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+
                         " FROM "+PstSpecialAchievement.TBL_HR_SPECIAL_ACHIEVEMENT + " SA "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEP "+
                         " , "+PstPosition.TBL_HR_POSITION + " POS "+
                         " WHERE "+
                         " SA."+PstSpecialAchievement.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         " = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
                         
            String whereClause = "";
            if((srcSpecialAchievement.getName()!= null)&& (srcSpecialAchievement.getName().length()>0)){
                Vector vectName = logicParser(srcSpecialAchievement.getName());
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

            if((srcSpecialAchievement.getPayrollNumber()!= null)&& (srcSpecialAchievement.getPayrollNumber().length()>0)){
                Vector vectName = logicParser(srcSpecialAchievement.getPayrollNumber());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if((srcSpecialAchievement.getAward()!= null)&& (srcSpecialAchievement.getAward().length()>0)){
                Vector vectName = logicParser(srcSpecialAchievement.getAward());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " SA."+PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_TYPE_OF_AWARD]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if((srcSpecialAchievement.getStartDate() != null) && (srcSpecialAchievement.getEndDate() != null)){
            	whereClause = whereClause +" SA."+ PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_DATE] + " BETWEEN '"+
                              Formater.formatDate(srcSpecialAchievement.getStartDate(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcSpecialAchievement.getEndDate(), "yyyy-MM-dd")+ "' AND ";

            }


            if(whereClause != null && whereClause.length()>0){
            	sql = sql + " AND " + whereClause + " 1 = 1 ";
            }

            sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            if(start != 0 && recordToGet != 0){
                	sql = sql + " LIMIT " + start + "," + recordToGet;
            }


            System.out.println("\t SQL search : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);

                SpecialAchievement  specialAchievement = new SpecialAchievement();
                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();

                specialAchievement.setOID(rs.getLong(PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_SPECIAL_ACHIEVEMENT_ID]));
                specialAchievement.setEmployeeId(rs.getLong(PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_EMPLOYEE_ID]));
                specialAchievement.setTypeOfAward(rs.getString(PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_TYPE_OF_AWARD]));
                specialAchievement.setDate(rs.getDate(PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_DATE]));
                specialAchievement.setPresentedBy(rs.getString(PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_PRESENTED_BY]));
                vect.add(specialAchievement);

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                vect.add(employee);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                result.add(vect);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on search Training History : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);


	}



	public static int getCountSpecialAchievement(SrcSpecialAchievement srcSpecialAchievement)
    {
    	DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcSpecialAchievement == null)
            return 0;
        try {
            String sql = " SELECT COUNT(DISTINCT SA."+PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_SPECIAL_ACHIEVEMENT_ID]+")"+
                         " FROM "+PstSpecialAchievement.TBL_HR_SPECIAL_ACHIEVEMENT + " SA "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEP "+
                         " , "+PstPosition.TBL_HR_POSITION + " POS "+
                         " WHERE "+
                         " SA."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         " = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " = EMP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
                         
           String whereClause = "";
            if((srcSpecialAchievement.getName()!= null)&& (srcSpecialAchievement.getName().length()>0)){
                Vector vectName = logicParser(srcSpecialAchievement.getName());
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

            if((srcSpecialAchievement.getPayrollNumber()!= null)&& (srcSpecialAchievement.getPayrollNumber().length()>0)){
                Vector vectName = logicParser(srcSpecialAchievement.getPayrollNumber());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if((srcSpecialAchievement.getAward()!= null)&& (srcSpecialAchievement.getAward().length()>0)){
                Vector vectName = logicParser(srcSpecialAchievement.getAward());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " SA."+PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_TYPE_OF_AWARD]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if((srcSpecialAchievement.getStartDate() != null) && (srcSpecialAchievement.getEndDate() != null)){
            	whereClause = whereClause +" SA."+ PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_DATE] + " BETWEEN '"+
                              Formater.formatDate(srcSpecialAchievement.getStartDate(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcSpecialAchievement.getEndDate(), "yyyy-MM-dd")+ "' AND ";

            }






            if(whereClause != null && whereClause.length()>0){
            	sql = sql + " AND " + whereClause + " 1 = 1 ";
            }


            
            System.out.println("\t SQL search : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            int count = 0;
            while(rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            return count;

        } catch (Exception e) {
            System.out.println("\t Exception on searchLeave : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return 0;

	}
}
