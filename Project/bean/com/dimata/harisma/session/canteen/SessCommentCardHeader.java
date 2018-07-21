/* 
 * Session Name  	:  SessCommentCardHeader.java 
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
package com.dimata.harisma.session.canteen;/* java package */ 
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

import com.dimata.harisma.entity.canteen.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;

public class SessCommentCardHeader{
    public static final String SESS_SRC_COMMENTCARDHEADER = "SESSION_SRC_COMMENTCARDHEADER";

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
    
    public static Vector searchCommentCardHeader(SrcCommentCardHeader srccommentcardheader, int start, int recordToGet){
     	DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srccommentcardheader == null)
            return new Vector(1,1);
        try {
            String sql = " SELECT " + 
            "   CCH." + PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_COMMENT_CARD_HEADER_ID] +
            " , CCH." + PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_EMPLOYEE_ID] +
            " , CCH." + PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_CARD_DATETIME] +
            " , EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
            " , DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] +
            " , POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION] +

            " FROM " + PstCommentCardHeader.TBL_HR_COMMENT_CARD_HEADER + " CCH " +
            " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP " +
            " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEP " +
            " , " + PstPosition.TBL_HR_POSITION + " POS " +

            " WHERE "+
            " CCH." + PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_EMPLOYEE_ID] +
            " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
            " AND DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
            " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
            " AND POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] +
            " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID];

            String whereClause = "";

            if((srccommentcardheader.getName()!= null)&& (srccommentcardheader.getName().length()>0)){
                Vector vectName = logicParser(srccommentcardheader.getName());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i=0; i<vectName.size(); i++){
                        String str = (String) vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if(srccommentcardheader.getDepartment() != 0)
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                			" = "+srccommentcardheader.getDepartment() + " AND ";

            if((srccommentcardheader.getCarddatefrom() != null) && (srccommentcardheader.getCarddateto() != null)){
            	whereClause = whereClause + " CCH." + PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_CARD_DATETIME] + " BETWEEN '"+
                Formater.formatDate(srccommentcardheader.getCarddatefrom(), "yyyy-MM-dd")+ "' AND '"+
                Formater.formatDate(srccommentcardheader.getCarddateto(), "yyyy-MM-dd")+ "' AND ";
            }

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

                CommentCardHeader  commentcardheader = new CommentCardHeader();
                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();
                
                commentcardheader.setOID(rs.getLong(PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_COMMENT_CARD_HEADER_ID]));
                commentcardheader.setEmployeeId(rs.getLong(PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_EMPLOYEE_ID]));
                
                int y = rs.getDate(PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_CARD_DATETIME]).getYear();
                int M = rs.getDate(PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_CARD_DATETIME]).getMonth();
                int d = rs.getDate(PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_CARD_DATETIME]).getDate();
                int h = rs.getTime(PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_CARD_DATETIME]).getHours();
                int m = rs.getTime(PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_CARD_DATETIME]).getMinutes();
                java.util.Date dt = new java.util.Date(y, M, d, h, m);
                //commentcardheader.setCardDatetime(rs.getDate(PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_CARD_DATETIME]));
                commentcardheader.setCardDatetime(dt);
                vect.add(commentcardheader);

                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
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
            System.out.println("\t Exception on search SessCommentCardHeader : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);
    }

    public static int getCountSearch (SrcCommentCardHeader srccommentcardheader){
     	DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srccommentcardheader == null)
            return 0;
        
        try {
            String sql = " SELECT COUNT(" + 
            "   CCH." + PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_COMMENT_CARD_HEADER_ID] + ") " +
            " FROM " + PstCommentCardHeader.TBL_HR_COMMENT_CARD_HEADER + " CCH " +
            " , " + PstEmployee.TBL_HR_EMPLOYEE + " EMP " +
            " , " + PstDepartment.TBL_HR_DEPARTMENT + " DEP " +
            " , " + PstPosition.TBL_HR_POSITION + " POS " +

            " WHERE "+
            " CCH." + PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_EMPLOYEE_ID] +
            " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
            " AND DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
            " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
            " AND POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] +
            " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID];

            String whereClause = "";

            if((srccommentcardheader.getName()!= null)&& (srccommentcardheader.getName().length()>0)){
                Vector vectName = logicParser(srccommentcardheader.getName());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i=0; i<vectName.size(); i++){
                        String str = (String) vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if(srccommentcardheader.getDepartment() != 0)
            	whereClause = whereClause + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                			" = "+srccommentcardheader.getDepartment() + " AND ";

            if((srccommentcardheader.getCarddatefrom() != null) && (srccommentcardheader.getCarddateto() != null)){
            	whereClause = whereClause + " CCH." + PstCommentCardHeader.fieldNames[PstCommentCardHeader.FLD_CARD_DATETIME] + " BETWEEN '"+
                Formater.formatDate(srccommentcardheader.getCarddatefrom(), "yyyy-MM-dd")+ "' AND '"+
                Formater.formatDate(srccommentcardheader.getCarddateto(), "yyyy-MM-dd")+ "' AND ";
            }

            if(whereClause != null && whereClause.length()>0){
            	sql = sql + " AND " + whereClause + " 1 = 1 ";
            }

            //sql = sql + " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            //if(start != 0 && recordToGet != 0){
            //    	sql = sql + " LIMIT " + start + "," + recordToGet;
            //}

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
            System.out.println("\t Exception on count SessCommentCardHeader : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }
}
