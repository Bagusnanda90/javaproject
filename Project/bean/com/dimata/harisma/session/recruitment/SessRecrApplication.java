/* 
 * Session Name  	:  SessRecrApplication.java 
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
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBHandler;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;

import com.dimata.harisma.entity.recruitment.*;
import com.dimata.harisma.entity.masterdata.*;

public class SessRecrApplication{
    public static final String SESS_SRC_RECRAPPLICATION = "SESSION_SRC_RECRAPPLICATION";

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

    public static Vector searchRecrApplication(SrcRecrApplication srcrecrapplication, int start, int recordToGet){
     	DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcrecrapplication == null)
            return new Vector(1,1);
        
        try {
            String sql = " SELECT " + 
            "   REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_RECR_APPLICATION_ID]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_POSITION]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_OTHER_POSITION]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_SALARY_EXP]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_DATE_AVAILABLE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FULL_NAME]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_SEX]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_BIRTH_PLACE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_BIRTH_DATE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_RELIGION_ID]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_ADDRESS]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_CITY]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_POSTAL_CODE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_PHONE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_ID_CARD_NUM]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_ASTEK_NUM]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_MARITAL_ID]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_PASSPORT_NO]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_ISSUE_PLACE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_VALID_UNTIL]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_HEIGHT]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_WEIGHT]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_BLOOD_TYPE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_DISTINGUISH_MARKS]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_APPL_DATE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FATHER_NAME]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FATHER_AGE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FATHER_OCCUPATION]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_MOTHER_NAME]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_MOTHER_AGE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_MOTHER_OCCUPATION]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FAMILY_ADDRESS]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FAMILY_CITY]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_FAMILY_PHONE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_SPOUSE_NAME]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_SPOUSE_BIRTH_DATE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_SPOUSE_OCCUPATION]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD1_NAME]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD1_BIRTHDATE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD1_SEX]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD2_NAME]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD2_BIRTHDATE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD2_SEX]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD3_NAME]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD3_BIRTHDATE]+
            " , REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD3_SEX]+

            " , REL."+PstReligion.fieldNames[PstReligion.FLD_RELIGION]+
            " , MAR."+PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]+

            " FROM "+PstRecrApplication.TBL_HR_RECR_APPLICATION + " REA "+
            " , "+PstReligion.TBL_HR_RELIGION +" REL "+
            " , "+PstMarital.TBL_HR_MARITAL + " MAR "+

            " WHERE "+
            " REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_RELIGION_ID]+ 
            " = REL."+PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]+
            " AND REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_MARITAL_ID]+
            " = MAR."+PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID];

            String whereClause = "";

            if((srcrecrapplication.getName()!= null)&& (srcrecrapplication.getName().length()>0)){
                Vector vectName = logicParser(srcrecrapplication.getName());
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

            if((srcrecrapplication.getPosition()!= null)&& (srcrecrapplication.getPosition().length()>0)){
                Vector vectName = logicParser(srcrecrapplication.getPosition());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_POSITION]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if((srcrecrapplication.getAppldateFrom() != null) && (srcrecrapplication.getAppldateTo() != null)){
            	whereClause = whereClause + " SR." + PstRecrApplication.fieldNames[PstRecrApplication.FLD_APPL_DATE] + " BETWEEN '"+
                Formater.formatDate(srcrecrapplication.getAppldateFrom(), "yyyy-MM-dd")+ "' AND '"+
                Formater.formatDate(srcrecrapplication.getAppldateTo(), "yyyy-MM-dd")+ "' AND ";
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

                RecrApplication  recrapplication = new RecrApplication();
                Religion religion = new Religion();
                Marital marital = new Marital();
                
                recrapplication.setOID(rs.getLong(PstRecrApplication.fieldNames[PstRecrApplication.FLD_RECR_APPLICATION_ID]));
                recrapplication.setPosition(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_POSITION]));
                recrapplication.setOtherPosition(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_OTHER_POSITION]));
                recrapplication.setSalaryExp(rs.getInt(PstRecrApplication.fieldNames[PstRecrApplication.FLD_SALARY_EXP]));
                recrapplication.setDateAvailable(rs.getDate(PstRecrApplication.fieldNames[PstRecrApplication.FLD_DATE_AVAILABLE]));
                recrapplication.setFullName(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_FULL_NAME]));
                recrapplication.setSex(rs.getInt(PstRecrApplication.fieldNames[PstRecrApplication.FLD_SEX]));
                recrapplication.setBirthPlace(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_BIRTH_PLACE]));
                recrapplication.setBirthDate(rs.getDate(PstRecrApplication.fieldNames[PstRecrApplication.FLD_BIRTH_DATE]));
                recrapplication.setReligionId(rs.getLong(PstRecrApplication.fieldNames[PstRecrApplication.FLD_RELIGION_ID]));
                recrapplication.setAddress(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_ADDRESS]));
                recrapplication.setCity(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_CITY]));
                recrapplication.setPostalCode(rs.getInt(PstRecrApplication.fieldNames[PstRecrApplication.FLD_POSTAL_CODE]));
                recrapplication.setPhone(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_PHONE]));
                recrapplication.setIdCardNum(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_ID_CARD_NUM]));
                recrapplication.setAstekNum(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_ASTEK_NUM]));
                recrapplication.setMaritalId(rs.getLong(PstRecrApplication.fieldNames[PstRecrApplication.FLD_MARITAL_ID]));
                recrapplication.setPassportNo(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_PASSPORT_NO]));
                recrapplication.setIssuePlace(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_ISSUE_PLACE]));
                recrapplication.setValidUntil(rs.getDate(PstRecrApplication.fieldNames[PstRecrApplication.FLD_VALID_UNTIL]));
                recrapplication.setHeight(rs.getInt(PstRecrApplication.fieldNames[PstRecrApplication.FLD_HEIGHT]));
                recrapplication.setWeight(rs.getInt(PstRecrApplication.fieldNames[PstRecrApplication.FLD_WEIGHT]));
                recrapplication.setBloodType(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_BLOOD_TYPE]));
                recrapplication.setDistinguishMarks(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_DISTINGUISH_MARKS]));
                recrapplication.setApplDate(rs.getDate(PstRecrApplication.fieldNames[PstRecrApplication.FLD_APPL_DATE]));
                recrapplication.setFatherName(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_FATHER_NAME]));
                recrapplication.setFatherAge(rs.getInt(PstRecrApplication.fieldNames[PstRecrApplication.FLD_FATHER_AGE]));
                recrapplication.setFatherOccupation(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_FATHER_OCCUPATION]));
                recrapplication.setMotherName(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_MOTHER_NAME]));
                recrapplication.setMotherAge(rs.getInt(PstRecrApplication.fieldNames[PstRecrApplication.FLD_MOTHER_AGE]));
                recrapplication.setMotherOccupation(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_MOTHER_OCCUPATION]));
                recrapplication.setFamilyAddress(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_FAMILY_ADDRESS]));
                recrapplication.setFamilyCity(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_FAMILY_CITY]));
                recrapplication.setFamilyPhone(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_FAMILY_PHONE]));
                recrapplication.setSpouseName(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_SPOUSE_NAME]));
                recrapplication.setSpouseBirthDate(rs.getDate(PstRecrApplication.fieldNames[PstRecrApplication.FLD_SPOUSE_BIRTH_DATE]));
                recrapplication.setSpouseOccupation(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_SPOUSE_OCCUPATION]));
                recrapplication.setChild1Name(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD1_NAME]));
                recrapplication.setChild1Birthdate(rs.getDate(PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD1_BIRTHDATE]));
                recrapplication.setChild1Sex(rs.getInt(PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD1_SEX]));
                recrapplication.setChild2Name(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD2_NAME]));
                recrapplication.setChild2Birthdate(rs.getDate(PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD2_BIRTHDATE]));
                recrapplication.setChild2Sex(rs.getInt(PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD2_SEX]));
                recrapplication.setChild3Name(rs.getString(PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD3_NAME]));
                recrapplication.setChild3Birthdate(rs.getDate(PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD3_BIRTHDATE]));
                recrapplication.setChild3Sex(rs.getInt(PstRecrApplication.fieldNames[PstRecrApplication.FLD_CHILD3_SEX]));
                vect.add(recrapplication);

                religion.setReligion(rs.getString(PstReligion.fieldNames[PstReligion.FLD_RELIGION]));
                vect.add(religion);

                marital.setMaritalStatus(rs.getString(PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]));
                vect.add(marital);

                result.add(vect);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on search Recr. Application : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);
    }

    public static int getCountSearch (SrcRecrApplication srcrecrapplication){
     	DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcrecrapplication == null)
            return 0;
        
        try {
            String sql = " SELECT " + 
            "   COUNT(REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_RECR_APPLICATION_ID]+ ") "+
            " FROM "+PstRecrApplication.TBL_HR_RECR_APPLICATION + " REA "+
            " , "+PstReligion.TBL_HR_RELIGION +" REL "+
            " , "+PstMarital.TBL_HR_MARITAL + " MAR "+

            " WHERE "+
            " REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_RELIGION_ID]+ 
            " = REL."+PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]+
            " AND REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_MARITAL_ID]+
            " = MAR."+PstMarital.fieldNames[PstMarital.FLD_MARITAL_ID];

            String whereClause = "";

            if((srcrecrapplication.getName()!= null)&& (srcrecrapplication.getName().length()>0)){
                Vector vectName = logicParser(srcrecrapplication.getName());
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

            if((srcrecrapplication.getPosition()!= null)&& (srcrecrapplication.getPosition().length()>0)){
                Vector vectName = logicParser(srcrecrapplication.getPosition());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + " REA."+PstRecrApplication.fieldNames[PstRecrApplication.FLD_POSITION]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if((srcrecrapplication.getAppldateFrom() != null) && (srcrecrapplication.getAppldateTo() != null)){
            	whereClause = whereClause + " REA." + PstRecrApplication.fieldNames[PstRecrApplication.FLD_APPL_DATE] + " BETWEEN '"+
                Formater.formatDate(srcrecrapplication.getAppldateFrom(), "yyyy-MM-dd")+ "' AND '"+
                Formater.formatDate(srcrecrapplication.getAppldateTo(), "yyyy-MM-dd")+ "' AND ";
            }

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
