/* 
 * Session Name  	:  SessLockerTreatment.java 
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
package com.dimata.harisma.session.locker;/* java package */ 
import java.io.*; 
import java.util.*; 
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.util.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
/* project package */
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBHandler;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.entity.masterdata.*;

public class SessLockerTreatment{
	public static final String SESS_SRC_LOCKERTREATMENT = "SESSION_SRC_LOCKERTREATMENT";

    private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for(int i =0;i < vector.size();i++){
            String code =(String)vector.get(i);
            if(((vector.get(vector.size()-1)).equals(LogicParser.SIGN))&&
              ((vector.get(vector.size()-1)).equals(LogicParser.ENGLISH)))
                vector.remove(vector.size()-1);
        }
        return vector;
    }

    public static Vector searchLockerTreatment(SrcLockerTreatment srclockertreatment, int start, int recordToGet){
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        try {
            String sql = "";
            sql += " SELECT A."+PstLockerTreatment.fieldNames[PstLockerTreatment.FLD_TREATMENT_ID]
                    +" ,B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION]
                    +" ,B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID]
                    +" ,B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_SEX]
                    +" ,A."+PstLockerTreatment.fieldNames[PstLockerTreatment.FLD_TREATMENT]
                    +" ,A."+PstLockerTreatment.fieldNames[PstLockerTreatment.FLD_TREATMENT_DATE];
            sql += " FROM "+PstLockerTreatment.TBL_HR_LOCKER_TREATMENT+" A" 
                    +", "+PstLockerLocation.TBL_HR_LOCKER_LOCATION+" B ";
            sql += " WHERE A."+PstLockerTreatment.fieldNames[PstLockerTreatment.FLD_LOCATION_ID]
                    +" = B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID];
            
            if(srclockertreatment.getLocation() != 0 && String.valueOf(srclockertreatment.getLocation()) != null && String.valueOf(srclockertreatment.getLocation()).length() > 0) {
                sql += " AND B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID]+" = " + srclockertreatment.getLocation() + " ";
                System.out.println("\t srclockertreatment.getLocationId() = " + srclockertreatment.getLocation());
            }
            
            if(srclockertreatment.getTreatment() != null && srclockertreatment.getTreatment().length() > 0) {
                sql += " AND A."+PstLockerTreatment.fieldNames[PstLockerTreatment.FLD_TREATMENT]+" LIKE '%" + srclockertreatment.getTreatment() + "%' ";
                System.out.println("\t srclockertreatment.getTreatment() = " + srclockertreatment.getTreatment());
            }

            if((srclockertreatment.getTreatmentdatefrom() != null) && (srclockertreatment.getTreatmentdateto() != null)){
            	sql += " AND A."+PstLockerTreatment.fieldNames[PstLockerTreatment.FLD_TREATMENT_DATE]+" BETWEEN '"+
                    Formater.formatDate(srclockertreatment.getTreatmentdatefrom(), "yyyy-MM-dd")+ "' AND '"+
                    Formater.formatDate(srclockertreatment.getTreatmentdateto(), "yyyy-MM-dd")+ "' ";
            }

            sql += " LIMIT " + start + "," + recordToGet;

            System.out.println("\t SQL searchLockerTreatment : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);
                LockerTreatment lockertreatment = new LockerTreatment();
                LockerLocation lockerlocation = new LockerLocation();
                
                lockertreatment.setOID(rs.getLong(PstLockerTreatment.fieldNames[PstLockerTreatment.FLD_TREATMENT_ID]));
                lockertreatment.setLocationId(rs.getLong(PstLockerTreatment.fieldNames[PstLockerTreatment.FLD_LOCATION_ID]));
                lockertreatment.setTreatmentDate(rs.getDate(PstLockerTreatment.fieldNames[PstLockerTreatment.FLD_TREATMENT_DATE]));
                lockertreatment.setTreatment(rs.getString(PstLockerTreatment.fieldNames[PstLockerTreatment.FLD_TREATMENT]));
                vect.add(lockertreatment);

                lockerlocation.setLocation(rs.getString(PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION]));
                lockerlocation.setSex(rs.getString(PstLockerLocation.fieldNames[PstLockerLocation.FLD_SEX]));
                vect.add(lockerlocation);

                result.add(vect);
            }
        }
        catch (Exception e) {
            System.out.println("\t Exception on 'SessLockerTreatment.class' -> searchLockerTreatment : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static int getCountSearch (SrcLockerTreatment srclockertreatment){
        int count = 0;
        System.out.println("\t Inside 'SessLocker.class' -> getCountSearch()");
        DBResultSet dbrs = null;

        try {
            String sql = "";
            sql += " SELECT COUNT(*) ";
            sql += " FROM "+PstLockerTreatment.TBL_HR_LOCKER_TREATMENT+" A"
                    +", "+PstLockerLocation.TBL_HR_LOCKER_LOCATION+" B ";
            sql += " WHERE A."+PstLockerTreatment.fieldNames[PstLockerTreatment.FLD_LOCATION_ID]
                    +" = B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID];

            if(srclockertreatment.getLocation() != 0 && String.valueOf(srclockertreatment.getLocation()) != null && String.valueOf(srclockertreatment.getLocation()).length() > 0) {
                sql += " AND B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID]+" = " + srclockertreatment.getLocation() + " ";
                System.out.println("\t srclockertreatment.getLocationId() = " + srclockertreatment.getLocation());
            }
            
            if(srclockertreatment.getTreatment() != null && srclockertreatment.getTreatment().length() > 0) {
                sql += " AND A."+PstLockerTreatment.fieldNames[PstLockerTreatment.FLD_TREATMENT]+" LIKE '%" + srclockertreatment.getTreatment() + "%' ";
                System.out.println("\t srclockertreatment.getTreatment() = " + srclockertreatment.getTreatment());
            }

            if((srclockertreatment.getTreatmentdatefrom() != null) && (srclockertreatment.getTreatmentdateto() != null)){
            	sql += " AND A."+PstLockerTreatment.fieldNames[PstLockerTreatment.FLD_TREATMENT_DATE]+" BETWEEN '"+
                    Formater.formatDate(srclockertreatment.getTreatmentdatefrom(), "yyyy-MM-dd")+ "' AND '"+
                    Formater.formatDate(srclockertreatment.getTreatmentdateto(), "yyyy-MM-dd")+ "' ";
            }

            System.out.println("\t SQL getCountSearch : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                count = rs.getInt(1);
            }
        }
        catch (Exception e) {
            System.out.println("\t Exception on 'SessLockerTreatment.class' -> searchLockerTreatment : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
            System.out.println("\t Count : " + count);
            return count;
        }
    }
}
