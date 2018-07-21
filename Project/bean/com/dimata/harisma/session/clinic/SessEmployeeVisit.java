/* 
 * Session Name  	:  SessEmployeeVisit.java 
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
package com.dimata.harisma.session.clinic;/* java package */ 
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
import com.dimata.harisma.entity.clinic.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.form.search.*;

public class SessEmployeeVisit
{
	public static final String SESS_SRC_EMPVISIT = "SESSION_SRC_EMPVISIT";

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

	public static Vector searchEmployeeVisit(SrcEmployeeVisit srcEmployeeVisit, int start, int recordToGet)
    {
		DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcEmployeeVisit == null)
         	return new Vector(1,1);

        try {
            String sql = " SELECT "+
                		 " EV."+PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_EMP_VISIT_ID]+
                         " , EV."+PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_VISIT_DATE]+
                         " , EV."+PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_DIAGNOSE]+
                         " , EV."+PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_DESCRIPTION]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                         " , DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
                         " , EMPVIS."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" AS VISITOR "+
                         " FROM "+PstEmployeeVisit.TBL_HR_EMP_VISIT+ " EV "+
                         ", "+PstEmployee.TBL_HR_EMPLOYEE+ " EMP "+
                         ", "+PstDepartment.TBL_HR_DEPARTMENT+ " DEP "+
                         ", "+PstEmployee.TBL_HR_EMPLOYEE+ " EMPVIS "+
                         " WHERE EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " = EV."+PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_EMPLOYEE_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
            			 " AND EV."+PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_VISITED_BY]+
                         " = EMPVIS."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" AND ";

            String whereClause = "";
            if((srcEmployeeVisit.getEmployeeName()!= null)&& (srcEmployeeVisit.getEmployeeName().length()>0)){
                Vector vectName = logicParser(srcEmployeeVisit.getEmployeeName());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim()+ " ";
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if(srcEmployeeVisit.getDepartment() != 0){
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                    		 " = "+srcEmployeeVisit.getDepartment()+ " AND ";
            }


            if((srcEmployeeVisit.getVisitDateFrom() != null) && (srcEmployeeVisit.getVisitDateTo() != null)){
            	whereClause = whereClause + " EV."+PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_VISIT_DATE]+ " BETWEEN '"+
                              Formater.formatDate(srcEmployeeVisit.getVisitDateFrom(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcEmployeeVisit.getVisitDateTo(), "yyyy-MM-dd")+ "' AND ";
            }

            if(whereClause != null)
            	sql = sql + whereClause + " 1=1 ";

            System.out.println("srcEmployeeVisit.getSortBy()"+srcEmployeeVisit.getSortBy());
            switch(srcEmployeeVisit.getSortBy())
            {
            case FrmSrcEmployeeVisit.ORDER_EMPLOYEE_NAME:
            	sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                break;
            case FrmSrcEmployeeVisit.ORDER_VISITING_DATE:
                sql = sql + " ORDER BY EV."+PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_VISIT_DATE];
                break;
            case FrmSrcEmployeeVisit.ORDER_VISITOR:
                sql = sql + " ORDER BY EMPVIS."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                break;
            }

            if(start == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + start + ","+ recordToGet ;

            System.out.println("Employee Visit = :"+sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector temp = new Vector(1,1);
                EmployeeVisit empVisit = new EmployeeVisit();
                Employee employee = new Employee();
                Department department = new Department();
                Employee empl2 = new Employee();

                empVisit.setOID(rs.getLong(PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_EMP_VISIT_ID]));
                empVisit.setVisitDate(rs.getDate(PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_VISIT_DATE]));
                empVisit.setDiagnose(rs.getString(PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_DIAGNOSE]));
                empVisit.setDescription(rs.getString(PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_DESCRIPTION]));
                temp.add(empVisit);

                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                temp.add(employee);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                temp.add(department);

                empl2.setFullName(rs.getString("VISITOR"));
                temp.add(empl2);

                result.add(temp);
            }
            return result;
        }catch(Exception exc){
        	System.out.println("error exc search guest handling ="+exc.toString());

        }finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);
	}

	public static int countEmployeeVisit (SrcEmployeeVisit srcEmployeeVisit)
    {
		DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcEmployeeVisit == null)
         	return 0;

        try {
            String sql = " SELECT "+
                		 " COUNT(DISTINCT EV."+PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_EMP_VISIT_ID]+")"+
                         " FROM "+PstEmployeeVisit.TBL_HR_EMP_VISIT+ " EV "+
                         ", "+PstEmployee.TBL_HR_EMPLOYEE+ " EMP "+
                         ", "+PstDepartment.TBL_HR_DEPARTMENT+ " DEP "+
                         ", "+PstEmployee.TBL_HR_EMPLOYEE+ " EMPVIS "+
                         " WHERE EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " = EV."+PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_EMPLOYEE_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
            			 " AND EV."+PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_VISITED_BY]+
                         " = EMPVIS."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" AND ";

            String whereClause = "";
            if((srcEmployeeVisit.getEmployeeName()!= null)&& (srcEmployeeVisit.getEmployeeName().length()>0)){
                Vector vectName = logicParser(srcEmployeeVisit.getEmployeeName());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim()+ " ";
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if(srcEmployeeVisit.getDepartment() != 0){
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                    		 " = "+srcEmployeeVisit.getDepartment()+" AND ";
            }


            if((srcEmployeeVisit.getVisitDateFrom() != null) && (srcEmployeeVisit.getVisitDateTo() != null)){
            	whereClause = whereClause + " EV."+PstEmployeeVisit.fieldNames[PstEmployeeVisit.FLD_VISIT_DATE]+ " BETWEEN '"+
                              Formater.formatDate(srcEmployeeVisit.getVisitDateFrom(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcEmployeeVisit.getVisitDateTo(), "yyyy-MM-dd")+ "' AND ";
            }

            if(whereClause != null)
                sql = sql + whereClause + " 1=1 ";




            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            int count = 0;
            while(rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        }catch(Exception exc){
        	System.out.println("error exc search guest handling ="+exc.toString());

        }finally {
            DBResultSet.close(dbrs);
        }
        return 0;
	}
}
