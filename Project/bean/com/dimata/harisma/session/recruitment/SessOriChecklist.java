/* 
 * Session Name  	:  SessOriChecklist.java 
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
package com.dimata.harisma.session.recruitment;/* java package */ 
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
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.entity.recruitment.*;
import com.dimata.harisma.entity.masterdata.*;

public class SessOriChecklist{
    public static final String SESS_SRC_ORICHECKLIST = "SESSION_SRC_ORICHECKLIST";

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

    public static Vector searchOriChecklist(SrcOriChecklist srcorichecklist, int start, int recordToGet){
     	DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcorichecklist == null)
            return new Vector(1,1);
        
        try {
            String sql = " SELECT " + 
            "   OC."+PstOriChecklist.fieldNames[PstOriChecklist.FLD_ORI_CHECKLIST_ID]+
            " , OC."+PstOriChecklist.fieldNames[PstOriChecklist.FLD_RECR_APPLICATION_ID]+
            " , OC."+PstOriChecklist.fieldNames[PstOriChecklist.FLD_INTERVIEWER_ID]+
            " , OC."+PstOriChecklist.fieldNames[PstOriChecklist.FLD_SIGNATURE_DATE]+
            " , OC."+PstOriChecklist.fieldNames[PstOriChecklist.FLD_INTERVIEW_DATE]+
            
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FULL_NAME]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_SEX]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_POSITION_ID]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_DEPARTMENT_ID]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_LEVEL_ID]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_COMM_DATE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_BIRTH_PLACE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_BIRTH_DATE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_RELIGION_ID]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_MARITAL_ID]+
            
            " FROM "+PstRecrApplication.TBL_HR_RECR_APPLICATION + " REA "+
            " , "+PstOriChecklist.TBL_HR_ORI_CHECKLIST +" OC "+

            " WHERE "+
            " REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_RECR_APPLICATION_ID]+ 
            " = OC."+PstOriChecklist.fieldNames[PstOriChecklist.FLD_RECR_APPLICATION_ID];

            String whereClause = "";

            if((srcorichecklist.getName()!= null)&& (srcorichecklist.getName().length()>0)){
                Vector vectName = logicParser(srcorichecklist.getName());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FULL_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if((srcorichecklist.getCommdatestart() != null) && (srcorichecklist.getCommdateend() != null)){
            	whereClause = whereClause +" REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_COMM_DATE]+ " BETWEEN '"+
                              Formater.formatDate(srcorichecklist.getCommdatestart(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcorichecklist.getCommdateend(), "yyyy-MM-dd")+ "' AND ";
            }

            if(srcorichecklist.getDepartment() != 0)
            	whereClause = whereClause + " REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_DEPARTMENT_ID]+
                			" = "+srcorichecklist.getDepartment() + " AND ";


            if(srcorichecklist.getPosition() != 0)
            	whereClause = whereClause + " EMP."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_POSITION_ID]+
                			" = "+ srcorichecklist.getPosition() + " AND ";


            /*
             if(srcEmployee.getSection() != 0)
            	whereClause = whereClause +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                			 " = "+ srcEmployee.getSection() + " AND ";
             */

            if(whereClause != null && whereClause.length()>0){
            	sql = sql + " AND " + whereClause + " 1 = 1 ";
            }

            //sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            if(start != 0 && recordToGet != 0){
                	sql = sql + " LIMIT " + start + "," + recordToGet;
            }

            System.out.println("\t SQL search : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);

                OriChecklist orichecklist = new OriChecklist();
                RecrApplication  recrapplication = new RecrApplication();

                orichecklist.setOID(rs.getLong(PstOriChecklist.fieldNames[PstOriChecklist.FLD_ORI_CHECKLIST_ID]));
                orichecklist.setRecrApplicationId(rs.getLong(PstOriChecklist.fieldNames[PstOriChecklist.FLD_RECR_APPLICATION_ID]));
                orichecklist.setInterviewerId(rs.getLong(PstOriChecklist.fieldNames[PstOriChecklist.FLD_INTERVIEWER_ID]));
                orichecklist.setSignatureDate(rs.getDate(PstOriChecklist.fieldNames[PstOriChecklist.FLD_SIGNATURE_DATE]));
                orichecklist.setInterviewDate(rs.getDate(PstOriChecklist.fieldNames[PstOriChecklist.FLD_INTERVIEW_DATE]));
                vect.add(orichecklist);

                recrapplication.setFnlPositionId(rs.getLong(PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_POSITION_ID]));
                recrapplication.setFnlDepartmentId(rs.getLong(PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_DEPARTMENT_ID]));
                recrapplication.setFnlLevelId(rs.getLong(PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_LEVEL_ID]));
                recrapplication.setFnlCommDate(rs.getDate(PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_COMM_DATE]));
                recrapplication.setFullName(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_FULL_NAME]));
                recrapplication.setSex(rs.getInt(PstRecrApplication.fieldNames[PstRecrApplication.FLD_SEX]));
                recrapplication.setBirthPlace(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_BIRTH_PLACE]));
                recrapplication.setBirthDate(rs.getDate(PstRecrApplication.fieldNames[PstRecrApplication.FLD_BIRTH_DATE]));
                recrapplication.setReligionId(rs.getLong(PstRecrApplication.fieldNames[PstRecrApplication.FLD_RELIGION_ID]));
                recrapplication.setMaritalId(rs.getLong(PstRecrApplication.fieldNames[PstRecrApplication.FLD_MARITAL_ID]));
                vect.add(recrapplication);

                result.add(vect);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on search Orientation Checklist : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);
    }

    public static int getCountSearch (SrcOriChecklist srcorichecklist){
     	DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcorichecklist == null)
            return 0;
        
        try {
            String sql = " SELECT COUNT(" + 
            "   OC."+PstOriChecklist.fieldNames[PstOriChecklist.FLD_ORI_CHECKLIST_ID]+") "+
            
            " FROM "+PstRecrApplication.TBL_HR_RECR_APPLICATION + " REA "+
            " , "+PstOriChecklist.TBL_HR_ORI_CHECKLIST +" OC "+

            " WHERE "+
            " REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_RECR_APPLICATION_ID]+ 
            " = OC."+PstOriChecklist.fieldNames[PstOriChecklist.FLD_RECR_APPLICATION_ID];

            String whereClause = "";

            if((srcorichecklist.getName()!= null)&& (srcorichecklist.getName().length()>0)){
                Vector vectName = logicParser(srcorichecklist.getName());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FULL_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if((srcorichecklist.getCommdatestart() != null) && (srcorichecklist.getCommdateend() != null)){
            	whereClause = whereClause +" REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_COMM_DATE]+ " BETWEEN '"+
                              Formater.formatDate(srcorichecklist.getCommdatestart(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcorichecklist.getCommdateend(), "yyyy-MM-dd")+ "' AND ";
            }

            if(srcorichecklist.getDepartment() != 0)
            	whereClause = whereClause + " REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_DEPARTMENT_ID]+
                			" = "+srcorichecklist.getDepartment() + " AND ";


            if(srcorichecklist.getPosition() != 0)
            	whereClause = whereClause + " EMP."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FNL_POSITION_ID]+
                			" = "+ srcorichecklist.getPosition() + " AND ";


            /*
             if(srcEmployee.getSection() != 0)
            	whereClause = whereClause +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                			 " = "+ srcEmployee.getSection() + " AND ";
             */

            if(whereClause != null && whereClause.length()>0){
            	sql = sql + " AND " + whereClause + " 1 = 1 ";
            }

            //sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            System.out.println("\t SQL search : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int num = 0;
            while(rs.next()) {
            	num = rs.getInt(1);
            }
            rs.close();
            return num;
        } catch (Exception e) {
            System.out.println("\t Exception on search Recr. Application : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }
}
