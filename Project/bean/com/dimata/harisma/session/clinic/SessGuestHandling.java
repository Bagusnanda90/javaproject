/* 
 * Session Name  	:  SessGuestHandling.java 
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
import com.dimata.harisma.form.search.*;

public class SessGuestHandling
{
	public static final String SESS_SRC_GUESTHANDLING = "SESSION_SRC_GUESTHANDLING";

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

	public static Vector searchGuestHandling(SrcGuestHandling srcGuestHandling, int start, int recordToGet)
    {
		DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcGuestHandling == null)
         	return new Vector(1,1);

        try {
            String sql = " SELECT * FROM "+PstGuestHandling.TBL_HR_GUEST_HANDLING;

            String whereClause = "";
            if((srcGuestHandling.getGuestName()!= null)&& (srcGuestHandling.getGuestName().length()>0)){
                Vector vectName = logicParser(srcGuestHandling.getGuestName());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + PstGuestHandling.fieldNames[PstGuestHandling.FLD_GUEST_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim()+ " ";
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if((srcGuestHandling.getDiagnosis()!= null)&& (srcGuestHandling.getDiagnosis().length()>0)){
                Vector vectName = logicParser(srcGuestHandling.getDiagnosis());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + PstGuestHandling.fieldNames[PstGuestHandling.FLD_DIAGNOSIS]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim()+ " ";
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if((srcGuestHandling.getDateFrom() != null) && (srcGuestHandling.getDateTo() != null)){
            	whereClause = whereClause +PstGuestHandling.fieldNames[PstGuestHandling.FLD_DATE]+ " BETWEEN '"+
                              Formater.formatDate(srcGuestHandling.getDateFrom(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcGuestHandling.getDateTo(), "yyyy-MM-dd")+ "' AND ";
            }

            if(whereClause != null && whereClause.length()>0)
                sql = sql + " WHERE "+ whereClause + " 1=1 ";

            if((srcGuestHandling.getSortBy()!= null)&& (srcGuestHandling.getSortBy().length()>0))
            	sql = sql + " ORDER BY "+ srcGuestHandling.getSortBy();

            if(start == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + start + ","+ recordToGet ;

            System.out.println("src guest handling = :"+sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                GuestHandling guestHandling = new GuestHandling();
            	PstGuestHandling.resultToObject(rs,guestHandling);
                result.add(guestHandling);
            }
            return result;
        }catch(Exception exc){
        	System.out.println("error exc search guest handling ="+exc.toString());

        }finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);
	}

	public static int countGuestHandling (SrcGuestHandling srcGuestHandling)
    {
		DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        if (srcGuestHandling == null)
         	return 0;

        try {
            String sql = " SELECT COUNT(DISTINCT "+PstGuestHandling.fieldNames[PstGuestHandling.FLD_GUEST_CLINIC_ID] + ")"+
                		 " FROM "+PstGuestHandling.TBL_HR_GUEST_HANDLING;

            String whereClause = "";
            if((srcGuestHandling.getGuestName()!= null)&& (srcGuestHandling.getGuestName().length()>0)){
                Vector vectName = logicParser(srcGuestHandling.getGuestName());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + PstGuestHandling.fieldNames[PstGuestHandling.FLD_GUEST_NAME]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim() + " ";
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }


            if((srcGuestHandling.getDiagnosis()!= null)&& (srcGuestHandling.getDiagnosis().length()>0)){
                Vector vectName = logicParser(srcGuestHandling.getDiagnosis());
                if(vectName != null && vectName.size()>0){
                    whereClause = whereClause + " (";
                    for(int i = 0; i <vectName.size();i++){
                        String str = (String)vectName.get(i);
                        if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)){
                    		whereClause = whereClause + PstGuestHandling.fieldNames[PstGuestHandling.FLD_DIAGNOSIS]+
                                		  " LIKE '%"+str.trim()+"%' ";
                        }else{
                        	whereClause = whereClause + str.trim()+" ";
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }
            }

            if((srcGuestHandling.getDateFrom() != null) && (srcGuestHandling.getDateTo() != null)){
            	whereClause = whereClause +PstGuestHandling.fieldNames[PstGuestHandling.FLD_DATE]+ " BETWEEN '"+
                              Formater.formatDate(srcGuestHandling.getDateFrom(), "yyyy-MM-dd")+ "' AND '"+
                              Formater.formatDate(srcGuestHandling.getDateTo(), "yyyy-MM-dd")+ "' AND ";
            }

            if(whereClause != null && whereClause.length()>0)
                sql = sql + " WHERE "+whereClause + " 1=1 ";

            if((srcGuestHandling.getSortBy()!= null)&& (srcGuestHandling.getSortBy().length()>0))
            	sql = sql + " ORDER BY "+ srcGuestHandling.getSortBy();

            System.out.println(sql);
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
