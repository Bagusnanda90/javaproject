/* 
 * Session Name  	:  SessTrainingHistory.java 
 * Created on 	:  5:19 PM
 * 
 * @author  	:  lkarunia
 * @version  	:  01
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

public class SessTrainingHistory
{
	public static final String SESS_SRC_TRAININGHISTORY = "SESSION_SRC_TRAININGHISTORY";

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

	public static Vector searchTrainingHistory(SrcTrainingHistory srcTrainingHistory, int start, int recordToGet)
    {
		DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcTrainingHistory == null)
            return new Vector(1,1);
        try {
            String sql = " SELECT DISTINCT(TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_HISTORY_ID]+ ")"+
                	 	 ", TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                         ", TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+
                	 	 ", TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE]+
                         ", TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_END_DATE]+
                         ", TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINER]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                         ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+
                         ", DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
                         ", POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY + " TH "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEP "+
                         " , "+PstPosition.TBL_HR_POSITION + " POS "+
                         " WHERE "+
                         " TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         " = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
                         
            String whereClause = "";
            if((srcTrainingHistory.getEmployee()!= null)&& (srcTrainingHistory.getEmployee().length()>0)){
                Vector vectName = logicParser(srcTrainingHistory.getEmployee());
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

            if((srcTrainingHistory.getPayrollNumber()!= null)&& (srcTrainingHistory.getPayrollNumber().length()>0)){
                Vector vectName = logicParser(srcTrainingHistory.getPayrollNumber());
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

            if((srcTrainingHistory.getProgram()!= null)&& (srcTrainingHistory.getProgram().length()>0)){
                Vector vectName = logicParser(srcTrainingHistory.getProgram());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if((srcTrainingHistory.getStartDate() != null) && (srcTrainingHistory.getEndDate() != null)){
            	whereClause = whereClause +" ((TH."+ PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] + " BETWEEN '"+
                              Formater.formatDate(srcTrainingHistory.getStartDate(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcTrainingHistory.getEndDate(), "yyyy-MM-dd")+ "') OR ("+
                              " TH."+ PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_END_DATE] + " BETWEEN '"+
                              Formater.formatDate(srcTrainingHistory.getStartDate(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcTrainingHistory.getEndDate(), "yyyy-MM-dd")+ "')) AND ";
            }


            if((srcTrainingHistory.getTrainer()!= null)&& (srcTrainingHistory.getTrainer().length()>0)){
                Vector vectName = logicParser(srcTrainingHistory.getTrainer());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINER]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if(whereClause != null && whereClause.length()>0){
            	sql = sql + " AND " + whereClause + " 1 = 1 ";
            }

            sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            if(start != 0 && recordToGet != 0){
                	sql = sql + " LIMIT " + start + "," + recordToGet;
            }
           
            //System.out.println("\t SQL search : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);

                TrainingHistory  trainingHistory = new TrainingHistory();
                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();

                trainingHistory.setOID(rs.getLong(PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_HISTORY_ID]));
                trainingHistory.setEmployeeId(rs.getLong(PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]));
                trainingHistory.setTrainingId(rs.getLong(PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]));
                trainingHistory.setStartDate(rs.getDate(PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE]));
                trainingHistory.setEndDate(rs.getDate(PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_END_DATE]));
                trainingHistory.setTrainer(rs.getString(PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINER]));
                vect.add(trainingHistory);

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

	public static int getCountTrainingHistory(SrcTrainingHistory srcTrainingHistory)
    {
    	DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcTrainingHistory == null)
            return 0;
        try {
            String sql = " SELECT COUNT(DISTINCT TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_HISTORY_ID]+")"+
                         " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY + " TH "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEP "+
                         " , "+PstPosition.TBL_HR_POSITION + " POS "+
                         " WHERE "+
                         " TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         " = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " = EMP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
                         
            String whereClause = "";
            if((srcTrainingHistory.getEmployee()!= null)&& (srcTrainingHistory.getEmployee().length()>0)){
                Vector vectName = logicParser(srcTrainingHistory.getEmployee());
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

            if((srcTrainingHistory.getPayrollNumber()!= null)&& (srcTrainingHistory.getPayrollNumber().length()>0)){
                Vector vectName = logicParser(srcTrainingHistory.getPayrollNumber());
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

            if((srcTrainingHistory.getProgram()!= null)&& (srcTrainingHistory.getProgram().length()>0)){
                Vector vectName = logicParser(srcTrainingHistory.getProgram());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if((srcTrainingHistory.getStartDate() != null) && (srcTrainingHistory.getEndDate() != null)){
            	whereClause = whereClause +" ((TH."+ PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE] + " BETWEEN '"+
                              Formater.formatDate(srcTrainingHistory.getStartDate(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcTrainingHistory.getEndDate(), "yyyy-MM-dd")+ "') OR ("+
                              " TH."+ PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_END_DATE] + " BETWEEN '"+
                              Formater.formatDate(srcTrainingHistory.getStartDate(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcTrainingHistory.getEndDate(), "yyyy-MM-dd")+ "')) AND ";
            }


            if((srcTrainingHistory.getTrainer()!= null)&& (srcTrainingHistory.getTrainer().length()>0)){
                Vector vectName = logicParser(srcTrainingHistory.getTrainer());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINER]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }



            if(whereClause != null && whereClause.length()>0){
            	sql = sql + " AND " + whereClause + " 1 = 1 ";
            }

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


    public static Vector searchTrainingHistory(SrcTraining srcTraining, int start, int recordToGet){
		DBResultSet dbrs = null;
		Vector result = new Vector(1,1);

        String order = " ORDER BY ";
        if(srcTraining.getSortBy()==FrmSrcTraining.ORDER_EMPLOYEE_NAME){
            order = order + "EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
        }
        else{
            order = order + "EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
        }

        try{
	        //search for employee that already trained for the training id
	        if(srcTraining.getTypeOfSearch()==0){
				String sql = " SELECT EMP.*, TH.*, DEP.*, POS.*, LEV.* FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
				" INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                " INNER JOIN "+PstPosition.TBL_HR_POSITION+" POS ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                " INNER JOIN "+PstLevel.TBL_HR_LEVEL+" LEV ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+" = LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+                                        
                (srcTraining.getDepartmentId()!=0 ? (                         
                    " WHERE EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+
                    " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN+
	            " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = "+srcTraining.getTrainingId()) 
                    : 
                    (" WHERE  EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN+
	            " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = "+srcTraining.getTrainingId()));



               /* String sql = " SELECT EMP.*, TH.*, DEP.*, POS.*, LEV.* FROM "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
                " LEFT JOIN "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                " INNER JOIN "+PstPosition.TBL_HR_POSITION+" POS ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                " INNER JOIN "+PstLevel.TBL_HR_LEVEL+" LEV ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+" = LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+
				" WHERE EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+
                " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN+
	            " AND ISNULL(TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = "+srcTraining.getTrainingId()+")";
                 */


                sql = sql + order;

                sql = sql + " LIMIT "+start+","+recordToGet;
	
	            dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
	            while(rs.next()){
                    Employee employee = new Employee();
                    PstEmployee.resultToObject(rs, employee);

                    TrainingHistory th = new TrainingHistory();
                    PstTrainingHistory.resultToObject(rs, th);

                    Department department = new Department();
                    PstDepartment.resultToObject(rs, department);

                    Level level = new Level();
                    PstLevel.resultToObject(rs, level);

                    Position position = new Position();
                    PstPosition.resultToObject(rs, position);

                    Vector temp = new Vector(1,1);
                    temp.add(employee);
                    temp.add(th);
                    temp.add(position);
                    temp.add(level);
                    temp.add(department);

                    result.add(temp);
                }
	
	        }
	        //search for employee that haven't got this training
	        else{
                /*String sql = "SELECT DISTINCT EMP.*, DEP.*, POS.*, LEV.* FROM "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
					" INNER JOIN "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" HT ON "+
                    " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" <> HT."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
	                " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
	                " INNER JOIN "+PstPosition.TBL_HR_POSITION+" POS ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
	                " INNER JOIN "+PstLevel.TBL_HR_LEVEL+" LEV ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+" = LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+
					" WHERE EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+
                    " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN+
                    " AND HT."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = "+srcTraining.getTrainingId();
                */

                String sql = "SELECT DISTINCT EMP.*, DEP.*, POS.*, LEV.* FROM "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
					" LEFT JOIN "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" HT ON "+
                    " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = HT."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
	                " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
	                " INNER JOIN "+PstPosition.TBL_HR_POSITION+" POS ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
	                " INNER JOIN "+PstLevel.TBL_HR_LEVEL+" LEV ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+" = LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+
			" WHERE " + 
                        ( srcTraining.getDepartmentId()!=0 ?
                        " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+" AND " : "") +
                        " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN+
                    " AND ISNULL(HT."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = "+srcTraining.getTrainingId()+")";


                sql = sql + order;

                sql = sql + " LIMIT "+start+","+recordToGet;

	            dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
	            while(rs.next()){
                    Employee employee = new Employee();
                    PstEmployee.resultToObject(rs, employee);

                    Department department = new Department();
                    PstDepartment.resultToObject(rs, department);

                    Level level = new Level();
                    PstLevel.resultToObject(rs, level);

                    Position position = new Position();
                    PstPosition.resultToObject(rs, position);

                    Vector temp = new Vector(1,1);
                    temp.add(employee);
                    temp.add(position);
                    temp.add(level);
                    temp.add(department);

                    result.add(temp);
                }
	        }
        }
        catch(Exception e){
            System.out.println("Exception e : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
        }

        return result;
    }

    public static int getSize(SrcTraining srcTraining){
		DBResultSet dbrs = null;
		int result = 0;

        try{
	        //search for employee that already trained for the training id
	        if(srcTraining.getTypeOfSearch()==0){
				String sql = " SELECT COUNT(EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+") FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
					" INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                    " WHERE " + 
                      ( srcTraining.getDepartmentId()!=0 ? (" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+ " AND ") : "" )+
                    " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN+
		    " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = "+srcTraining.getTrainingId();

	            dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
	            while(rs.next()){
                    result = rs.getInt(1);
                }
	
	        }
	        //search for employee that haven't got this training
	        else{
                String sql = "SELECT DISTINCT EMP.* FROM "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+
					" INNER JOIN "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" HT ON "+
                    " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" <> HT."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
					" WHERE " +
                      ( srcTraining.getDepartmentId()!=0 ? " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+
                    " AND" : "") + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN+
                    " AND HT."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = "+srcTraining.getTrainingId();



	            dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                Vector vct = new Vector(1,1);
	            while(rs.next()){
                    Employee employee = new Employee();
                    PstEmployee.resultToObject(rs, employee);
                    vct.add(employee);
                }

                if(vct!=null && vct.size()>0){
                	result = vct.size();
                }
	        }
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
    public static Vector getTrainingHistoryList(SrcTraining srcTraining, int start, int recordToGet){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);

        try{
	        //search for employee  already taken this training
	        if(srcTraining.getTypeOfSearch() == 0) {
                    
                    String sql = " SELECT DISTINCT EMP.*, DIVI.*, DEP.*, POS.*, LEV.* " +
                                 " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
				 " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                                 " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                                 " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP " +
                                 " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                                 " INNER JOIN "+PstDivision.TBL_HR_DIVISION+" AS DIVI "+
                                 " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+" = DIVI."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+
                                 " INNER JOIN "+PstPosition.TBL_HR_POSITION+" POS " +
                                 " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                                 " INNER JOIN "+PstLevel.TBL_HR_LEVEL+" LEV " +
                                 " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+" = LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] +
                                 " WHERE ";
                    
                                if(srcTraining.getDepartmentId()!=0) {                         
                                    sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+" AND ";
                                }  
                                
                                if(srcTraining.getPositionId() != 0) {
                                    sql += "EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = "+srcTraining.getPositionId()+" AND ";
                                }
                                
                                if (srcTraining.getDivisionId() != 0){
                                    sql += "EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+" = "+srcTraining.getDivisionId()+" AND ";
                                }
                                    
                                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN;
                                                    
                                //update by devin 2014-04-10
                                    if(srcTraining.getTrainingId()!=0){
                                        sql += " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = "+srcTraining.getTrainingId();
                                    }
                                if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_EMPLOYEE_NAME)
                                    sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                                else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_PAYROLL_NUM)
                                    sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                                else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_DEPARTMENT)
                                    sql += " ORDER BY DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];


                                sql += " LIMIT " + start + "," + recordToGet;

                                //System.out.println("SQL ON GET HISTORY = " + sql);
                                
                                dbrs = DBHandler.execQueryResult(sql);
                                ResultSet rs = dbrs.getResultSet();

                                while(rs.next()){
                                    Employee employee = new Employee();
                                    PstEmployee.resultToObject(rs, employee);  
                                    
                                    Position position = new Position();
                                    PstPosition.resultToObject(rs, position);
                                    
                                    Level level = new Level();
                                    PstLevel.resultToObject(rs, level);
                                    
                                    Department department = new Department();
                                    PstDepartment.resultToObject(rs, department);
                                    
                                    Division division = new Division();
                                    PstDivision.resultToObject(rs, division);

                                    Vector temp = new Vector(1,1);
                                    temp.add(employee);
                                    temp.add(division);
                                    temp.add(department);
                                    temp.add(position);
                                    temp.add(level);
                                    

                                    result.add(temp);
                                }    
                                
	        //search for employee haven't yet taken this training
                } else {            

                    String sql = " SELECT DISTINCT EMP.*, DEP.*, POS.*, LEV.* " +
                                 " FROM "+PstEmployee.TBL_HR_EMPLOYEE+" EMP "+                                 
                                 " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" DEP " +
                                 " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                                 " INNER JOIN "+PstPosition.TBL_HR_POSITION+" POS " +
                                 " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                                 " LEFT JOIN "+PstLevel.TBL_HR_LEVEL+" LEV " +
                                 " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+" = LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+
                                 " WHERE ";
                                 
                                if(srcTraining.getDepartmentId()!=0)
                                    sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+" AND "; 
                                
                                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN+
                                       " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" NOT IN (" +
                                            " SELECT DISTINCT TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID] + 
                                            " FROM " + PstTrainingHistory.TBL_HR_TRAINING_HISTORY + " TH" +
                                            " WHERE TH."+ PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = "+srcTraining.getTrainingId() + ")";


                                if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_EMPLOYEE_NAME)
                                    sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
                                else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_PAYROLL_NUM)
                                    sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
                                else if(srcTraining.getSortBy() == FrmSrcTraining.ORDER_DEPARTMENT)
                                    sql += " ORDER BY DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];

                                sql += " LIMIT " + start + "," + recordToGet;

                                
                                //System.out.println("SQL ON GET HISTORY = " + sql);
                                
                                dbrs = DBHandler.execQueryResult(sql);
                                ResultSet rs = dbrs.getResultSet();
                                
                                while(rs.next()){
                                    Employee employee = new Employee();
                                    PstEmployee.resultToObject(rs, employee);
                                 
                                    Position position = new Position();
                                    PstPosition.resultToObject(rs, position);
                                    
                                    Level level = new Level();
                                    PstLevel.resultToObject(rs, level);
                                    
                                    Department department = new Department();
                                    PstDepartment.resultToObject(rs, department);
                                    
                                    Vector temp = new Vector(1,1);
                                    temp.add(employee);
                                    temp.add(position);
                                    temp.add(level);
                                    temp.add(department);

                                    result.add(temp);
                                }
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

    public static int countTrainingHistory(SrcTraining srcTraining){
        DBResultSet dbrs = null;
        int result = 0;

        try{
	        //search for employee that already trained for the training id
	        if(srcTraining.getTypeOfSearch()==0){
                    
                    String sql = " SELECT COUNT(DISTINCT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+") " +
                                 " FROM "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" TH "+
                                 " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +
                                 " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+
                                 " WHERE ";
                                 
                                if(srcTraining.getDepartmentId()!=0) {
                                    sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+ " AND ";
                                }
                                if(srcTraining.getPositionId()!=0) {
                                    sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = "+srcTraining.getPositionId()+" AND ";
                                }
                                sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN;
                                if(srcTraining.getTrainingId()!=0){
                                    sql += " AND TH."+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = "+srcTraining.getTrainingId();
                                }

                                
                                //System.out.println("SQL ON COUNT HISTORY = " + sql);
                                
                                dbrs = DBHandler.execQueryResult(sql);
                                ResultSet rs = dbrs.getResultSet();

                                while(rs.next()){
                                    result = rs.getInt(1);
                                }

	        
	        //search for employee that haven't got this training
	        } else {
                    
                    String sql = " SELECT COUNT(EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+") " +
                                 " FROM "+PstEmployee.TBL_HR_EMPLOYEE+" EMP " +                                 
                                 " WHERE ";
                    
                                 if(srcTraining.getDepartmentId()!=0 ){
                                     sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+srcTraining.getDepartmentId()+" AND ";
                                 }
                                 if(srcTraining.getPositionId()!=0) {
                                    sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" = "+srcTraining.getPositionId()+" AND ";
                                 }
                                 sql += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN+
                                        " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " NOT IN ("+
                                            " SELECT TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID] + 
                                            " FROM " + PstTrainingHistory.TBL_HR_TRAINING_HISTORY + " TH" +
                                            " WHERE TH."+ PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+" = "+srcTraining.getTrainingId() + ")";


                                 //System.out.println("SQL ON COUNT HISTORY = " + sql);

                                 dbrs = DBHandler.execQueryResult(sql);
                                 ResultSet rs = dbrs.getResultSet();

                                 while(rs.next()){
                                    result = rs.getInt(1);
                                 }
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


}
