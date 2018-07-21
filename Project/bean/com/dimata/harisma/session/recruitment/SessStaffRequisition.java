/* 
 * Session Name  	:  SessStaffRequisition.java 
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
package com.dimata.harisma.session.recruitment;
/* java package */ 
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

import com.dimata.harisma.entity.recruitment.*;
import com.dimata.harisma.entity.masterdata.*;

public class SessStaffRequisition{
    public static final String SESS_SRC_STAFFREQUISITION = "SESSION_SRC_STAFFREQUISITION";

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

    public static Vector searchStaffRequisition(SrcStaffRequisition srcstaffrequisition, int start, int recordToGet){
     	DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcstaffrequisition == null)
            return new Vector(1,1);
        
        try {
            String sql = " SELECT " + 
            "   SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_STAFF_REQUISITION_ID]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_DEPARTMENT_ID]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_SECTION_ID]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_POSITION_ID]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EMP_CATEGORY_ID]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUISITION_TYPE]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_MALE]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_FEMALE]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EXP_COMM_DATE]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_TEMP_FOR]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_APPROVED_BY]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_APPROVED_DATE]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_ACKNOWLEDGED_BY]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_ACKNOWLEDGED_DATE]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUESTED_BY]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUESTED_DATE]+
            " , DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
            " , POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+
            " , SEC."+PstSection.fieldNames[PstSection.FLD_SECTION]+
            " , EMPCAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]+
            " FROM "+PstStaffRequisition.TBL_HR_STAFF_REQUISITION + " SR "+
            " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEP "+
            " , "+PstPosition.TBL_HR_POSITION + " POS "+
            " , "+PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "+
            " , "+PstSection.TBL_HR_SECTION+ " SEC "+
            " WHERE "+
            " SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_DEPARTMENT_ID]+ 
            " = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
            " AND SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_SECTION_ID]+
            " = SEC."+PstSection.fieldNames[PstSection.FLD_SECTION_ID]+
            " AND SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_POSITION_ID]+
            " = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
            " AND SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EMP_CATEGORY_ID]+
            " = EMPCAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID];

            String whereClause = "";
            /*
            if((srcstaffrequisition.getReqtype()!= null)&& (srcstaffrequisition.getReqtype().length()>0)){
                Vector vectName = logicParser(srcstaffrequisition.getReqtype());
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
             */

            if((srcstaffrequisition.getReqdateFrom() != null) && (srcstaffrequisition.getReqdateTo() != null)){
            	whereClause = whereClause + " SR." + PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUESTED_DATE] + " BETWEEN '"+
                Formater.formatDate(srcstaffrequisition.getReqdateFrom(), "yyyy-MM-dd")+ "' AND '"+
                Formater.formatDate(srcstaffrequisition.getReqdateTo(), "yyyy-MM-dd")+ "' AND ";
            }

            if(srcstaffrequisition.getDepartment() != 0)
            	whereClause = whereClause + " SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_DEPARTMENT_ID]+
                			" = "+srcstaffrequisition.getDepartment() + " AND ";

            if(srcstaffrequisition.getPosition() != 0)
            	whereClause = whereClause + " SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_POSITION_ID]+
                			" = "+ srcstaffrequisition.getPosition() + " AND ";

            if(srcstaffrequisition.getSection() != 0)
            	whereClause = whereClause + " SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_SECTION_ID]+
                			 " = "+ srcstaffrequisition.getSection() + " AND ";

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

                StaffRequisition  staffrequisition = new StaffRequisition();
                Department department = new Department();
                Section section = new Section();
                Position position = new Position();
                EmpCategory empcategory = new EmpCategory();
                
                staffrequisition.setOID(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_STAFF_REQUISITION_ID]));
                staffrequisition.setDepartmentId(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_DEPARTMENT_ID]));
                staffrequisition.setSectionId(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_SECTION_ID]));
                staffrequisition.setPositionId(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_POSITION_ID]));
                staffrequisition.setEmpCategoryId(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EMP_CATEGORY_ID]));
                staffrequisition.setRequisitionType(rs.getInt(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUISITION_TYPE]));
                staffrequisition.setNeededMale(rs.getInt(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_MALE]));
                staffrequisition.setNeededFemale(rs.getInt(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_FEMALE]));
                staffrequisition.setExpCommDate(rs.getDate(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EXP_COMM_DATE]));
                staffrequisition.setTempFor(rs.getInt(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_TEMP_FOR]));
                staffrequisition.setApprovedBy(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_APPROVED_BY]));
                staffrequisition.setApprovedDate(rs.getDate(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_APPROVED_DATE]));
                staffrequisition.setAcknowledgedBy(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_ACKNOWLEDGED_BY]));
                staffrequisition.setAcknowledgedDate(rs.getDate(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_ACKNOWLEDGED_DATE]));
                staffrequisition.setRequestedBy(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUESTED_BY]));
                staffrequisition.setRequestedDate(rs.getDate(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUESTED_DATE]));
                vect.add(staffrequisition);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empcategory);

                result.add(vect);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on search Staff Requisition : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);
    }

    public static int getCountSearch (SrcStaffRequisition srcstaffrequisition){
     	DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcstaffrequisition == null)
            return 0;
        
        try {
            String sql = " SELECT " + 
            "   COUNT(SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_STAFF_REQUISITION_ID]+ ") "+
            " FROM "+PstStaffRequisition.TBL_HR_STAFF_REQUISITION + " SR "+
            " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEP "+
            " , "+PstPosition.TBL_HR_POSITION + " POS "+
            " , "+PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "+
            " , "+PstSection.TBL_HR_SECTION+ " SEC "+
            " WHERE "+
            " SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_DEPARTMENT_ID]+ 
            " = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
            " AND SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_SECTION_ID]+
            " = SEC."+PstSection.fieldNames[PstSection.FLD_SECTION_ID]+
            " AND SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_POSITION_ID]+
            " = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
            " AND SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EMP_CATEGORY_ID]+
            " = EMPCAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID];

            String whereClause = "";

            if((srcstaffrequisition.getReqdateFrom() != null) && (srcstaffrequisition.getReqdateTo() != null)){
            	whereClause = whereClause + " SR." + PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUESTED_DATE] + " BETWEEN '"+
                Formater.formatDate(srcstaffrequisition.getReqdateFrom(), "yyyy-MM-dd")+ "' AND '"+
                Formater.formatDate(srcstaffrequisition.getReqdateTo(), "yyyy-MM-dd")+ "' AND ";
            }

            if(srcstaffrequisition.getDepartment() != 0)
            	whereClause = whereClause + " SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_DEPARTMENT_ID]+
                			" = "+srcstaffrequisition.getDepartment() + " AND ";

            if(srcstaffrequisition.getPosition() != 0)
            	whereClause = whereClause + " SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_POSITION_ID]+
                			" = "+ srcstaffrequisition.getPosition() + " AND ";

            if(srcstaffrequisition.getSection() != 0)
            	whereClause = whereClause + " SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_SECTION_ID]+
                			 " = "+ srcstaffrequisition.getSection() + " AND ";

            if(whereClause != null && whereClause.length()>0){
            	sql = sql + " AND " + whereClause + " 1 = 1 ";
            }

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
            System.out.println("\t Exception on count search Staff Requisition : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }
    
    public static Vector getStaffRequisitionReminder(){
     	DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        
        try {
            String sql = " SELECT " + 
            "   SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_STAFF_REQUISITION_ID]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_DEPARTMENT_ID]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_SECTION_ID]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_POSITION_ID]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EMP_CATEGORY_ID]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUISITION_TYPE]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_MALE]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_FEMALE]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EXP_COMM_DATE]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_TEMP_FOR]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_APPROVED_BY]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_APPROVED_DATE]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_ACKNOWLEDGED_BY]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_ACKNOWLEDGED_DATE]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUESTED_BY]+
            " , SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUESTED_DATE]+
            " , DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
            " , POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+
            " , SEC."+PstSection.fieldNames[PstSection.FLD_SECTION]+
            " , EMPCAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]+
            " FROM "+PstStaffRequisition.TBL_HR_STAFF_REQUISITION + " SR "+
            " , "+PstDepartment.TBL_HR_DEPARTMENT +" DEP "+
            " , "+PstPosition.TBL_HR_POSITION + " POS "+
            " , "+PstEmpCategory.TBL_HR_EMP_CATEGORY + " EMPCAT "+
            " , "+PstSection.TBL_HR_SECTION+ " SEC "+
            " WHERE "+
            " SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_DEPARTMENT_ID]+ 
            " = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
            " AND SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_SECTION_ID]+
            " = SEC."+PstSection.fieldNames[PstSection.FLD_SECTION_ID]+
            " AND SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_POSITION_ID]+
            " = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+
            " AND SR."+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EMP_CATEGORY_ID]+
            " = EMPCAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID];

            sql += " AND DAYOFYEAR("+PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUESTED_DATE]+
                ") < (DAYOFYEAR(NOW()) + 31) ";

            System.out.println("\t SQL search : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);

                StaffRequisition  staffrequisition = new StaffRequisition();
                Department department = new Department();
                Section section = new Section();
                Position position = new Position();
                EmpCategory empcategory = new EmpCategory();
                
                staffrequisition.setOID(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_STAFF_REQUISITION_ID]));
                staffrequisition.setDepartmentId(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_DEPARTMENT_ID]));
                staffrequisition.setSectionId(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_SECTION_ID]));
                staffrequisition.setPositionId(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_POSITION_ID]));
                staffrequisition.setEmpCategoryId(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EMP_CATEGORY_ID]));
                staffrequisition.setRequisitionType(rs.getInt(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUISITION_TYPE]));
                staffrequisition.setNeededMale(rs.getInt(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_MALE]));
                staffrequisition.setNeededFemale(rs.getInt(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_NEEDED_FEMALE]));
                staffrequisition.setExpCommDate(rs.getDate(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_EXP_COMM_DATE]));
                staffrequisition.setTempFor(rs.getInt(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_TEMP_FOR]));
                staffrequisition.setApprovedBy(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_APPROVED_BY]));
                staffrequisition.setApprovedDate(rs.getDate(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_APPROVED_DATE]));
                staffrequisition.setAcknowledgedBy(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_ACKNOWLEDGED_BY]));
                staffrequisition.setAcknowledgedDate(rs.getDate(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_ACKNOWLEDGED_DATE]));
                staffrequisition.setRequestedBy(rs.getLong(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUESTED_BY]));
                staffrequisition.setRequestedDate(rs.getDate(PstStaffRequisition.fieldNames[PstStaffRequisition.FLD_REQUESTED_DATE]));
                vect.add(staffrequisition);

                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                vect.add(section);

                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                empcategory.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empcategory);

                result.add(vect);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on getStaffRequisitionReminder : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);
    }
}
