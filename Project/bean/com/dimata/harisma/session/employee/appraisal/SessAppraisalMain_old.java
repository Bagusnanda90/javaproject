/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.employee.appraisal;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.appraisal.AppraisalMain;
import com.dimata.harisma.entity.employee.appraisal.PstAppraisalMain;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.GroupRank;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstGroupRank;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.search.SrcAppraisal;
import com.dimata.harisma.form.search.FrmSrcAppraisal;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author artha
 */
public class SessAppraisalMain_old {
    
    public static final String SESS_SRC_APPRAISAL_MAIN = "SESSION_SRC_APPRAISAL_MAIN";
    
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
                         " DISTINCT(EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_APP_MAIN_ID]+")"+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMPLOYEE_ID]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_TOTAL_ASSESSMENT]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_TOTAL_SCORE]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_SCORE_AVERAGE]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_ASS]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_LAST_ASS]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_NEXT_ASS]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_ASSESSOR_ID]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_ASS_POSITION_ID]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+
                         " , EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" AS APPRAISOR "+
                 //        " , DEV_HEAD."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" AS DEV_HEAD_NAME "+
                         " , DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
                         " , POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+
                         " , LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL]+
                         " , GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_NAME]+
                         " FROM "+PstAppraisalMain.TBL_HR_APPRAISAL_MAIN + " EMAP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP2 "+
                 //        " , "+PstEmployee.TBL_HR_EMPLOYEE + " DEV_HEAD "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEPT "+
                         " , "+PstPosition.TBL_HR_POSITION + " POST "+
                         " , "+PstLevel.TBL_HR_LEVEL + " LEV "+
                         " , "+PstGroupRank.TBL_HR_GROUP_RANK+ " GR "+
                         " WHERE "+
                         " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_ASSESSOR_ID]+
                         " = EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                   //      " AND(EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]+
                   //      " = DEV_HEAD."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMP_DEPARTMENT_ID]+
                         " = DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " AND  EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMP_POSITION_ID]+
                         " = POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " AND EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMP_LEVEL_ID]+
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
            	whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_SCORE_AVERAGE]+
                    		  " BETWEEN "+srcAppraisal.getAveragestart()+" AND "+ srcAppraisal.getAverageend()+ " AND ";
            }

            if(srcAppraisal.getRank() != 0){
            	whereClause = whereClause + " GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID]+
                    		  " = "+srcAppraisal.getRank()+" AND ";
            }

            if((srcAppraisal.getDatestart() != null)&& (srcAppraisal.getDateend() != null)){
            	whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_ASS]+
                    		  " BETWEEN '"+Formater.formatDate(srcAppraisal.getDatestart(),"yyyy-MM-dd")+"' AND '"+
                              Formater.formatDate(srcAppraisal.getDateend(),"yyyy-MM-dd")+"' AND ";
            }
            
            if(srcAppraisal.getApproved() >0 ){
                if(srcAppraisal.getApproved()==1){//not approval status
                    whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]+
                                      " IS NULL OR EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]
                                      +"< 1 AND";
                }else{//all approval status
                    whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]+
                                      " > 0 AND ";
                }
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
                    sql = sql + " ORDER BY EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_SCORE_AVERAGE];
           			break;

                case FrmSrcAppraisal.ORDER_APPRAISAL_DATE:
                    sql = sql + " ORDER BY EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_ASS];
           			break;

                default:
                    sql = sql + "";
            }

            if(start == 0 && recordToGet == 0){
				sql = sql + "";
            }else{
                sql = sql + " LIMIT " + start + "," + recordToGet;
            }
            System.out.println("\t SQL search Appraisal : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);

                AppraisalMain empApp = new AppraisalMain();
                Employee employee = new Employee();
                Employee employee2 = new Employee();
                Employee employee3 = new Employee();
                Department department = new Department();
                Position position = new Position();
                Level level = new Level();
                GroupRank groupRank = new  GroupRank();

                empApp.setOID(rs.getLong(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_APP_MAIN_ID]));
                empApp.setEmpLevelId(rs.getLong(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMPLOYEE_ID]));
                empApp.setTotalAssessment(rs.getInt(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_TOTAL_ASSESSMENT]));
                empApp.setTotalScore(rs.getDouble(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_TOTAL_SCORE]));
                empApp.setScoreAverage(rs.getDouble(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_SCORE_AVERAGE]));
                empApp.setDateOfAssessment(rs.getDate(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_ASS]));
                empApp.setDateOfLastAssessment(rs.getDate(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_LAST_ASS]));
                empApp.setDateOfNextAssessment(rs.getDate(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_NEXT_ASS]));
                empApp.setAssesorId(rs.getLong(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_ASSESSOR_ID]));
                empApp.setAssesorPositionId(rs.getLong(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_ASS_POSITION_ID]));
                empApp.setDivisionHeadId(rs.getLong(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]));
                vect.add(empApp);

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                vect.add(employee);

                employee2.setFullName(rs.getString("APPRAISOR"));
                //employee2.setLevelId(start)
                vect.add(employee2);
                        
				department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                vect.add(level);

                groupRank.setGroupName(rs.getString(PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_NAME]));
                vect.add(groupRank);
                
            //    employee3.setFullName(rs.getString("DEV_HEAD_NAME"));
                //employee2.setLevelId(start)
            //    vect.add(employee3);

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

    public static Vector searchAppraisal(SrcAppraisal srcAppraisal, int start, int recordToGet,long whereHRD)
    {
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcAppraisal == null)
         	return new Vector(1,1);

        try {
            String sql = " SELECT "+
                         " DISTINCT(EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_APP_MAIN_ID]+")"+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMPLOYEE_ID]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_TOTAL_ASSESSMENT]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_TOTAL_SCORE]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_SCORE_AVERAGE]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_ASS]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_LAST_ASS]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_NEXT_ASS]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_ASSESSOR_ID]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_ASS_POSITION_ID]+
                         " , EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                         " , EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+
                         " , EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" AS APPRAISOR "+
                 //        " , DEV_HEAD."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" AS DEV_HEAD_NAME "+
                         " , DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
                         " , POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+
                         " , LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL]+
                         " , GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_NAME]+
                         " FROM "+PstAppraisalMain.TBL_HR_APPRAISAL_MAIN + " EMAP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP2 "+
                 //        " , "+PstEmployee.TBL_HR_EMPLOYEE + " DEV_HEAD "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEPT "+
                         " , "+PstPosition.TBL_HR_POSITION + " POST "+
                         " , "+PstLevel.TBL_HR_LEVEL + " LEV "+
                         " , "+PstGroupRank.TBL_HR_GROUP_RANK+ " GR "+
                         " WHERE "+
                         " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_ASSESSOR_ID]+
                         " = EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                   //      " AND(EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]+
                   //      " = DEV_HEAD."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMP_DEPARTMENT_ID]+
                         " = DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " AND  EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMP_POSITION_ID]+
                         " = POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " AND EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMP_LEVEL_ID]+
                         " = LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+
                         " AND LEV."+PstLevel.fieldNames[PstLevel.FLD_GROUP_RANK_ID]+
                         " = GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID] + 
                         " AND DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" = "+
                         whereHRD+" AND ";
            
            System.out.println("\t SQL search Appraisal 1 : " + sql);
            
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
            	whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_SCORE_AVERAGE]+
                    		  " BETWEEN "+srcAppraisal.getAveragestart()+" AND "+ srcAppraisal.getAverageend()+ " AND ";
            }

            if(srcAppraisal.getRank() != 0){
            	whereClause = whereClause + " GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID]+
                    		  " = "+srcAppraisal.getRank()+" AND ";
            }

            if((srcAppraisal.getDatestart() != null)&& (srcAppraisal.getDateend() != null)){
            	whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_ASS]+
                    		  " BETWEEN '"+Formater.formatDate(srcAppraisal.getDatestart(),"yyyy-MM-dd")+"' AND '"+
                              Formater.formatDate(srcAppraisal.getDateend(),"yyyy-MM-dd")+"' AND ";
            }
            
            if(srcAppraisal.getApproved() >0 ){
                if(srcAppraisal.getApproved()==1){//not approval status
                    whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]+
                                      " IS NULL OR EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]
                                      +"< 1 AND";
                }else{//all approval status
                    whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]+
                                      " > 0 AND ";
                }
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
                    sql = sql + " ORDER BY EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_SCORE_AVERAGE];
           			break;

                case FrmSrcAppraisal.ORDER_APPRAISAL_DATE:
                    sql = sql + " ORDER BY EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_ASS];
           			break;

                default:
                    sql = sql + "";
            }

            if(start == 0 && recordToGet == 0){
				sql = sql + "";
            }else{
                sql = sql + " LIMIT " + start + "," + recordToGet;
            }
            System.out.println("\t SQL search Appraisal : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);

                AppraisalMain empApp = new AppraisalMain();
                Employee employee = new Employee();
                Employee employee2 = new Employee();
                Employee employee3 = new Employee();
                Department department = new Department();
                Position position = new Position();
                Level level = new Level();
                GroupRank groupRank = new  GroupRank();

                empApp.setOID(rs.getLong(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_APP_MAIN_ID]));
                empApp.setEmpLevelId(rs.getLong(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMPLOYEE_ID]));
                empApp.setTotalAssessment(rs.getInt(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_TOTAL_ASSESSMENT]));
                empApp.setTotalScore(rs.getDouble(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_TOTAL_SCORE]));
                empApp.setScoreAverage(rs.getDouble(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_SCORE_AVERAGE]));
                empApp.setDateOfAssessment(rs.getDate(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_ASS]));
                empApp.setDateOfLastAssessment(rs.getDate(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_LAST_ASS]));
                empApp.setDateOfNextAssessment(rs.getDate(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_NEXT_ASS]));
                empApp.setAssesorId(rs.getLong(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_ASSESSOR_ID]));
                empApp.setAssesorPositionId(rs.getLong(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_ASS_POSITION_ID]));
                empApp.setDivisionHeadId(rs.getLong(PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]));
                vect.add(empApp);

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                vect.add(employee);

                employee2.setFullName(rs.getString("APPRAISOR"));
                //employee2.setLevelId(start)
                vect.add(employee2);
                        
				department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                level.setLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                vect.add(level);

                groupRank.setGroupName(rs.getString(PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_NAME]));
                vect.add(groupRank);
                
            //    employee3.setFullName(rs.getString("DEV_HEAD_NAME"));
                //employee2.setLevelId(start)
            //    vect.add(employee3);

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
            String sql = " SELECT COUNT(DISTINCT EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMPLOYEE_ID]+")"+
                         " FROM "+PstAppraisalMain.TBL_HR_APPRAISAL_MAIN + " EMAP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP2 "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEPT "+
                         " , "+PstPosition.TBL_HR_POSITION + " POST "+
                         " , "+PstLevel.TBL_HR_LEVEL + " LEV "+
                         " , "+PstGroupRank.TBL_HR_GROUP_RANK+ " GR "+
                         " WHERE "+
                         " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_ASSESSOR_ID]+
                         " = EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMPLOYEE_ID]+
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
            	whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_SCORE_AVERAGE]+
                    		  " BETWEEN "+srcAppraisal.getAveragestart()+" AND "+ srcAppraisal.getAverageend()+ " AND ";
            }

            if(srcAppraisal.getRank() != 0){
            	whereClause = whereClause + " GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID]+
                    		  " = "+srcAppraisal.getRank()+" AND ";
            }

            if((srcAppraisal.getDatestart() != null)&& (srcAppraisal.getDateend() != null)){
            	whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_ASS]+
                    		  " BETWEEN '"+Formater.formatDate(srcAppraisal.getDatestart(),"yyyy-MM-dd")+"' AND '"+
                              Formater.formatDate(srcAppraisal.getDateend(),"yyyy-MM-dd")+"' AND ";
            }
            
            if(srcAppraisal.getApproved() >0 ){
                if(srcAppraisal.getApproved()==1){//not approval status
                    whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]+
                                      " IS NULL OR EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]
                                      +"< 1 AND";
                }else{//all approval status
                    whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]+
                                      " > 0 AND ";
                }
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
    
    public static int countAppraisal(SrcAppraisal srcAppraisal,long whereHRD)
    {
       DBResultSet dbrs = null;
       Vector result = new Vector(1,1);
       if (srcAppraisal == null)
         	return 0;

       try {
            String sql = " SELECT COUNT(DISTINCT EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMPLOYEE_ID]+")"+
                         " FROM "+PstAppraisalMain.TBL_HR_APPRAISAL_MAIN + " EMAP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP "+
                         " , "+PstEmployee.TBL_HR_EMPLOYEE + " EMP2 "+
                         " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEPT "+
                         " , "+PstPosition.TBL_HR_POSITION + " POST "+
                         " , "+PstLevel.TBL_HR_LEVEL + " LEV "+
                         " , "+PstGroupRank.TBL_HR_GROUP_RANK+ " GR "+
                         " WHERE "+
                         " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_ASSESSOR_ID]+
                         " = EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_EMPLOYEE_ID]+
                         " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                         " = DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                         " AND  EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+
                         " = POST."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
                         " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+
                         " = LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+
                         " AND LEV."+PstLevel.fieldNames[PstLevel.FLD_GROUP_RANK_ID]+
                         " = GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID] + 
                         " AND DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" = "+
                         whereHRD+" AND ";
                         

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
            	whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_SCORE_AVERAGE]+
                    		  " BETWEEN "+srcAppraisal.getAveragestart()+" AND "+ srcAppraisal.getAverageend()+ " AND ";
            }

            if(srcAppraisal.getRank() != 0){
            	whereClause = whereClause + " GR."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID]+
                    		  " = "+srcAppraisal.getRank()+" AND ";
            }

            if((srcAppraisal.getDatestart() != null)&& (srcAppraisal.getDateend() != null)){
            	whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DATE_OF_ASS]+
                    		  " BETWEEN '"+Formater.formatDate(srcAppraisal.getDatestart(),"yyyy-MM-dd")+"' AND '"+
                              Formater.formatDate(srcAppraisal.getDateend(),"yyyy-MM-dd")+"' AND ";
            }
            
            if(srcAppraisal.getApproved() >0 ){
                if(srcAppraisal.getApproved()==1){//not approval status
                    whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]+
                                      " IS NULL OR EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]
                                      +"< 1 AND";
                }else{//all approval status
                    whereClause = whereClause + " EMAP."+PstAppraisalMain.fieldNames[PstAppraisalMain.FLD_DIVISION_HEAD_ID]+
                                      " > 0 AND ";
                }
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
}
