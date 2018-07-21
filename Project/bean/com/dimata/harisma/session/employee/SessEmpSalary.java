/* 
 * Session Name  	:  SessEmployee.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: lkarunia
 * @version  	: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.session.employee;

/* java package */ 
import java.io.*; 
import java.util.*; 
import java.sql.*;
import java.util.Date;
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

public class SessEmpSalary
{

    public static final String SESS_SRC_SALARY = "SESSION_SRC_SALARY";


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


    public static Vector searchEmpSalary(SrcEmpSalary srcEmpSalary, int start, int recordToGet)
    {
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcEmpSalary == null)
         	return new Vector(1,1);

        try {
            String sql = " SELECT "+
               		 " EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_EMP_SALARY_ID]+ 
               		 ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+
                         ", POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+
                         ", DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
                         ", LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL]+
                         ", MAR."+PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]+
                         ", EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_DATE]+
                         ", EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_LOS1]+
                         ", EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_LOS2]+
                         ", EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_BASIC]+
                         ", EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_TRANSPORT]+
                         ", EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_TOTAL]+
                         ", EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_NEW_BASIC]+
                         ", EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_NEW_TRANSPORT]+
                         ", EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_NEW_TOTAL]+
                         ", EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_ADDITIONAL]+
                         " FROM "+ PstEmpSalary.TBL_HR_EMP_SALARY + " EMSAL "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstPosition.TBL_HR_POSITION + " POST "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT + " DEPT "+
                         " , "+PstLevel.TBL_HR_LEVEL + " LEV "+
                         " , "+PstMarital.TBL_HR_MARITAL + " MAR "+
                         " WHERE "+
                         " EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         " AND DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " AND LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+
                         " AND MAR."+PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID];


            String whereClause = "";
            if((srcEmpSalary.getName()!= null)&& (srcEmpSalary.getName().length()>0)){
                Vector vectName = logicParser(srcEmpSalary.getName());
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


            if((srcEmpSalary.getEmpnumber()!= null)&& (srcEmpSalary.getEmpnumber().length()>0)){
                Vector vectNum = logicParser(srcEmpSalary.getEmpnumber());
                if(vectNum != null && vectNum.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectNum.size();i++){
                        String str = (String)vectNum.get(i);
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


            if((srcEmpSalary.getCommDateFrom() != null) && (srcEmpSalary.getCommDateTo() != null)){
            	whereClause = whereClause +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+ " BETWEEN '"+
                              Formater.formatDate(srcEmpSalary.getCommDateFrom(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcEmpSalary.getCommDateTo(), "yyyy-MM-dd")+ "' AND ";
            }


            if(srcEmpSalary.getPosition() != 0)
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                			" = "+ srcEmpSalary.getPosition() + " AND ";

            if(srcEmpSalary.getDepartment() != 0)
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                			" = "+ srcEmpSalary.getDepartment() + " AND ";

            if(srcEmpSalary.getLevel() != 0)
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+
                			" = "+ srcEmpSalary.getLevel() + " AND ";


            if((srcEmpSalary.getCurrtotalfrom() != 0) && (srcEmpSalary.getCurrtotalto() != 0)){
            	whereClause = whereClause +" EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_TOTAL]+ " BETWEEN "+
                              Formater.formatNumber(srcEmpSalary.getCurrtotalfrom(), "#.00")+ " AND "+
                              Formater.formatNumber(srcEmpSalary.getCurrtotalto(), "#.00")+ " AND ";
            }

            if((srcEmpSalary.getNewtotalfrom() != 0) && (srcEmpSalary.getNewtotalto() != 0)){
            	whereClause = whereClause +" EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_NEW_TOTAL]+ " BETWEEN "+
                              Formater.formatNumber(srcEmpSalary.getNewtotalfrom(), "#.00")+ " AND "+
                              Formater.formatNumber(srcEmpSalary.getNewtotalto(), "#.00")+ " AND ";
            }

            if((whereClause != null) && (whereClause.length()>0)){
            	sql = sql + " AND "+whereClause + " 1 = 1";
            }

            switch(srcEmpSalary.getOrderBy()){
            	case FrmSrcEmpSalary.ORDER_EMPLOYEE_NAME :
                    sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                    		" AND  EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_DATE];
            		break;
                case FrmSrcEmpSalary.ORDER_POSITION:
                    sql = sql + " ORDER BY POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION] +
                    	" AND  EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_DATE];
            		break;
                case FrmSrcEmpSalary.ORDER_EMPLOYEE_NUMBER:
                    sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    	" AND  EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_DATE];
            		break;
                case FrmSrcEmpSalary.ORDER_LEVEL:
                    sql = sql + " ORDER BY LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL] +
                    	" AND  EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_DATE];
            		break;
                case FrmSrcEmpSalary.ORDER_COMM_DATE:
                    sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] +
                    	" AND  EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_DATE];
            		break;
                default:
                    sql = sql + " ORDER BY EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_DATE] ;
            }

            if(start==0 && recordToGet==0)
                sql = sql + "";
            else
                sql = sql + " LIMIT "+start+","+recordToGet;

            System.out.println("\t SQL searchSalary : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);

                Employee employee = new Employee();
                Position position = new Position();
                Department department = new Department();
                Level level = new Level();
                Marital marital = new Marital();
                EmpSalary empSalary =  new EmpSalary();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
		employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
		employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                vect.add(employee);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                vect.add(level);

                marital.setMaritalCode(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]));
                vect.add(marital);

                empSalary.setOID(rs.getLong(PstEmpSalary.fieldNames[PstEmpSalary.FLD_EMP_SALARY_ID]));
                empSalary.setCurrDate(rs.getDate(PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_DATE]));
                empSalary.setCurrBasic(rs.getDouble(PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_BASIC]));
                empSalary.setCurrTransport(rs.getDouble(PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_TRANSPORT]));
                empSalary.setLos1(rs.getInt(PstEmpSalary.fieldNames[PstEmpSalary.FLD_LOS1]));
                empSalary.setLos2(rs.getInt(PstEmpSalary.fieldNames[PstEmpSalary.FLD_LOS2]));
                empSalary.setCurrTotal(rs.getDouble(PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_TOTAL]));
                empSalary.setNewBasic(rs.getDouble(PstEmpSalary.fieldNames[PstEmpSalary.FLD_NEW_BASIC]));
                empSalary.setNewTransport(rs.getDouble(PstEmpSalary.fieldNames[PstEmpSalary.FLD_NEW_TRANSPORT]));
                empSalary.setNewTotal(rs.getDouble(PstEmpSalary.fieldNames[PstEmpSalary.FLD_NEW_TOTAL]));
                empSalary.setAdditional(rs.getDouble(PstEmpSalary.fieldNames[PstEmpSalary.FLD_ADDITIONAL]));
                vect.add(empSalary);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchSalary : " + e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);

	}

    public static int countEmpSalary(SrcEmpSalary srcEmpSalary)
    {
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcEmpSalary == null)
         	return 0;

        try {
            String sql = " SELECT COUNT(EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_EMP_SALARY_ID]+ ")"+
                         " FROM "+ PstEmpSalary.TBL_HR_EMP_SALARY + " EMSAL "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstPosition.TBL_HR_POSITION + " POST "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT + " DEPT "+
                         " , "+PstLevel.TBL_HR_LEVEL + " LEV "+
                         " , "+PstMarital.TBL_HR_MARITAL + " MAR "+
                         " WHERE "+
                         " EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND  POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         " AND DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " AND LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+
                         " AND MAR."+PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID];


            String whereClause = "";
            if((srcEmpSalary.getName()!= null)&& (srcEmpSalary.getName().length()>0)){
                Vector vectName = logicParser(srcEmpSalary.getName());
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


            if((srcEmpSalary.getEmpnumber()!= null)&& (srcEmpSalary.getEmpnumber().length()>0)){
                Vector vectNum = logicParser(srcEmpSalary.getEmpnumber());
                if(vectNum != null && vectNum.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectNum.size();i++){
                        String str = (String)vectNum.get(i);
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


            if((srcEmpSalary.getCommDateFrom() != null) && (srcEmpSalary.getCommDateTo() != null)){
            	whereClause = whereClause +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+ " BETWEEN "+
                              Formater.formatDate(srcEmpSalary.getCommDateFrom(), "yyyy-MM-dd")+ " AND "+
                              Formater.formatDate(srcEmpSalary.getCommDateTo(), "yyyy-MM-dd")+ " AND ";
            }


            if(srcEmpSalary.getDepartment() != 0)
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                			" = "+ srcEmpSalary.getDepartment() + " AND ";

            if(srcEmpSalary.getPosition() != 0)
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                			" = "+ srcEmpSalary.getPosition() + " AND ";

            if(srcEmpSalary.getLevel() != 0)
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+
                			" = "+ srcEmpSalary.getLevel() + " AND ";


            if((srcEmpSalary.getCurrtotalfrom() != 0) && (srcEmpSalary.getCurrtotalto() != 0)){
            	whereClause = whereClause +" EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_TOTAL]+ " BETWEEN "+
                              Formater.formatNumber(srcEmpSalary.getCurrtotalfrom(), "#.00")+ " AND "+
                              Formater.formatNumber(srcEmpSalary.getCurrtotalto(), "#.00")+ " AND ";
            }

            if((srcEmpSalary.getNewtotalfrom() != 0) && (srcEmpSalary.getNewtotalto() != 0)){
            	whereClause = whereClause +" EMSAL."+PstEmpSalary.fieldNames[PstEmpSalary.FLD_NEW_TOTAL]+ " BETWEEN "+
                              Formater.formatNumber(srcEmpSalary.getNewtotalfrom(), "#.00")+ " AND "+
                              Formater.formatNumber(srcEmpSalary.getNewtotalto(), "#.00")+ " AND ";
            }

            if((whereClause != null) && (whereClause.length()>0)){
            	sql = sql + " AND "+whereClause + " 1 = 1";
            }

            System.out.println("\t SQL countSalary : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            int count  = 0;
            while(rs.next()) {
            	count = rs.getInt(1);
            }
            return count;

        } catch (Exception e) {
            System.out.println("\t Exception on  countSalary : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return 0;


	}

    /* This method used to find current data */
	public static int findLimitStart( long oid, int recordToGet, SrcEmpSalary srcEmpSalary, int size){
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  searchEmpSalary(srcEmpSalary,start,recordToGet);
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){
                   Vector tmp = (Vector)list.get(ls);
			  	   EmpSalary empSalary = (EmpSalary)tmp.get(4);
				   if(oid == empSalary.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}
}
