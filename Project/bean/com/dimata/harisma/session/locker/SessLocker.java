/* 
 * Session Name  	:  SessLocker.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
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
import com.dimata.harisma.entity.employee.*;

public class SessLocker{
    public static final String SESS_SRC_LOCKER = "SESSION_SRC_LOCKER";
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

    public static Vector searchLocker(SrcLocker srclocker, int start, int recordToGet) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        Vector list = new Vector(1,1);
        try {
            String sql = ""; 
            sql += " SELECT DISTINCT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    +", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    +", DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    +", A."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]
                    +", B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION]
                    +", B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_SEX]
                    +", A."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]
                    +", A."+PstLocker.fieldNames[PstLocker.FLD_KEY_NUMBER]
                    +", A."+PstLocker.fieldNames[PstLocker.FLD_SPARE_KEY]
                    +", C."+PstLockerCondition.fieldNames[PstLockerCondition.FLD_CONDITION];

            sql += " FROM "+PstLocker.TBL_HR_LOCKER
                   +" AS A LEFT JOIN "+PstLockerCondition.TBL_HR_LOCKER_CONDITION
                   +" AS C ON A."+PstLocker.fieldNames[PstLocker.FLD_CONDITION_ID]
                   +" = C."+PstLockerCondition.fieldNames[PstLockerCondition.FLD_CONDITION_ID]
                   +" INNER JOIN "+PstLockerLocation.TBL_HR_LOCKER_LOCATION
                   +" AS B ON A."+PstLocker.fieldNames[PstLocker.FLD_LOCATION_ID]
                   +" = B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID]
                   +" LEFT JOIN "+PstEmployee.TBL_HR_EMPLOYEE
                   +" AS EMP ON A."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]
                   +" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]
                   +" LEFT JOIN "+PstDepartment.TBL_HR_DEPARTMENT
                   +" AS DEPT ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                   +" = DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
            
            /*sql += " FROM HR_LOCKER A, HR_LOCKER_LOCATION B, HR_LOCKER_CONDITION C ";
            sql += " WHERE A.LOCATION_ID = B.LOCATION_ID ";
            sql += " AND A.CONDITION_ID = C.CONDITION_ID ";*/
            
            String whereClause = "";
            if(srclocker.getLocation() != 0 && String.valueOf(srclocker.getLocation()) != null && String.valueOf(srclocker.getLocation()).length() > 0) {
                if(whereClause.length()==0)
                    whereClause = " B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID]+" = " + srclocker.getLocation() + " ";
                else
                    whereClause += " AND B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID]+" = " + srclocker.getLocation() + " ";
                
                System.out.println("\t srclocker.getLocationId() = " + srclocker.getLocation());
            }
            
            if(srclocker.getLockernumber() != null && srclocker.getLockernumber().length() > 0) {
                if(whereClause.length()==0)
                    whereClause = " A."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]+" LIKE '" + srclocker.getLockernumber() + "%' ";
                else
                    whereClause += " AND A."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]+" LIKE '" + srclocker.getLockernumber() + "%' ";
                
                System.out.println("\t srclocker.getLockernumber() = " + srclocker.getLockernumber());
            }

            if(srclocker.getLockerkey() != null && srclocker.getLockerkey().length() > 0) {
                if(whereClause.length()==0)
                    whereClause = " A."+PstLocker.fieldNames[PstLocker.FLD_KEY_NUMBER]+" LIKE '" + srclocker.getLockerkey() + "%' ";
                else
                    whereClause += " AND A."+PstLocker.fieldNames[PstLocker.FLD_KEY_NUMBER]+" LIKE '" + srclocker.getLockerkey() + "%' ";
                
                System.out.println("\t srclocker.getLockerkey() = " + srclocker.getLockerkey());
            }


            if(srclocker.getSparekey() != null && srclocker.getSparekey().length() > 0) {
                if(whereClause.length()==0)
                    whereClause = " A."+PstLocker.fieldNames[PstLocker.FLD_SPARE_KEY]+" = " + srclocker.getSparekey() + " ";
                else
                    whereClause += " AND A."+PstLocker.fieldNames[PstLocker.FLD_SPARE_KEY]+" = " + srclocker.getSparekey() + " ";
                
                System.out.println("\t srclocker.getLockerkey() = " + srclocker.getSparekey());
            }

            if(srclocker.getCondition() > 0 && String.valueOf(srclocker.getCondition()) != null && String.valueOf(srclocker.getCondition()).length() > 0) {
                if(whereClause.length()==0)
                    whereClause = " C."+PstLockerCondition.fieldNames[PstLockerCondition.FLD_CONDITION_ID]+" = " + srclocker.getCondition() + " ";
                else
                    whereClause += " AND C."+PstLockerCondition.fieldNames[PstLockerCondition.FLD_CONDITION_ID]+" = " + srclocker.getCondition() + " ";
                
                System.out.println("\t srclocker.getConditionId() = " + srclocker.getCondition());
            }  
             
            if(whereClause.length()>0)
                sql += " WHERE "+whereClause;
            
            sql += " GROUP BY A."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID];
            //sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" DESC";
            sql += " ORDER BY A."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER];
            
            if(start==0 && recordToGet==0)
                sql += " ";
            else                
                sql += " LIMIT " + start + "," + recordToGet;
            
            
            System.out.println("\t SQL searchLocker : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                Vector vect = new Vector(1,1);
                Locker locker = new Locker();
                LockerLocation lockerlocation = new LockerLocation();
                LockerCondition lockercondition = new LockerCondition();
                Employee employee = new Employee();
                Department department = new Department();
                
                locker.setOID(rs.getLong(PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]));
                locker.setLockerNumber(rs.getString(PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]));
                locker.setKeyNumber(rs.getString(PstLocker.fieldNames[PstLocker.FLD_KEY_NUMBER]));
                
                locker.setSpareKey(rs.getString(PstLocker.fieldNames[PstLocker.FLD_SPARE_KEY]));
                vect.add(locker); 

                lockerlocation.setLocation(rs.getString(PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION]));
                lockerlocation.setSex(rs.getString(PstLockerLocation.fieldNames[PstLockerLocation.FLD_SEX]));
                vect.add(lockerlocation);

                lockercondition.setCondition(rs.getString(PstLockerCondition.fieldNames[PstLockerCondition.FLD_CONDITION]));
                vect.add(lockercondition);
                
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                vect.add(employee);
                
                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);
                
                result.add(vect);
            }
        }
        catch (Exception e) {
            System.out.println("\t Exception on 'SessLocker.class' -> searchLocker : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static int getCountSearch (SrcLocker srclocker) {
        int count = 0;
        System.out.println("\t Inside 'SessLocker.class' -> getCountSearch()");
        DBResultSet dbrs = null;

        try {
            String sql = "";
            sql += " SELECT COUNT(DISTINCT"
                    +" A."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]
                    +") ";
            sql += " FROM "+PstLocker.TBL_HR_LOCKER
                    +" AS A LEFT JOIN "+PstLockerCondition.TBL_HR_LOCKER_CONDITION
                    +" AS C ON A."+PstLocker.fieldNames[PstLocker.FLD_CONDITION_ID]
                    +" = C."+PstLockerCondition.fieldNames[PstLockerCondition.FLD_CONDITION_ID]
                    +" INNER JOIN "+PstLockerLocation.TBL_HR_LOCKER_LOCATION
                    +" AS B ON A."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_ID]
                    +" = B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID];
            
            /*sql += " FROM HR_LOCKER A, HR_LOCKER_LOCATION B, HR_LOCKER_CONDITION C ";
            sql += " WHERE A.LOCATION_ID = B.LOCATION_ID ";
            sql += " AND A.CONDITION_ID = C.CONDITION_ID ";*/

            /*if(srclocker.getLocation() != 0 && String.valueOf(srclocker.getLocation()) != null && String.valueOf(srclocker.getLocation()).length() > 0) {
                sql += " AND ";
                sql += "B.LOCATION_ID = " + srclocker.getLocation() + " ";
                System.out.println("\t srclocker.getLocationId() = " + srclocker.getLocation());
            }

            if(srclocker.getLockernumber() != null && srclocker.getLockernumber().length() > 0) {
                sql += " AND A.LOCKER_NUMBER LIKE '" + srclocker.getLockernumber() + "%' ";
                System.out.println("\t srclocker.getLockerNumber() = " + srclocker.getLockernumber());
            }

            if(srclocker.getLockerkey() != null && srclocker.getLockerkey().length() > 0) {
                sql += " AND A.KEY_NUMBER LIKE '" + srclocker.getLockerkey() + "%' ";
                System.out.println("\t srclocker.getLockerkey() = " + srclocker.getLockerkey());
            }

            if(srclocker.getSparekey() != null && srclocker.getSparekey().length() > 0) {
                sql += " AND A.SPARE_KEY = " + srclocker.getSparekey() + " ";
                System.out.println("\t srclocker.getLockerkey() = " + srclocker.getSparekey());
            }

            if(srclocker.getCondition() >= 0 && String.valueOf(srclocker.getCondition()) != null && String.valueOf(srclocker.getCondition()).length() > 0) {
                sql += " AND C.CONDITION_ID = " + srclocker.getCondition() + " ";
                System.out.println("\t srclocker.getConditionId() = " + srclocker.getCondition());
            }*/
            String whereClause = "";
            if(srclocker.getLocation() != 0 && String.valueOf(srclocker.getLocation()) != null && String.valueOf(srclocker.getLocation()).length() > 0) {
                if(whereClause.length()==0)
                    whereClause = " B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID]+" = " + srclocker.getLocation() + " ";
                else
                    whereClause += " AND B."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID]+" = " + srclocker.getLocation() + " ";
                
                System.out.println("\t srclocker.getLocationId() = " + srclocker.getLocation());
            }
            
            if(srclocker.getLockernumber() != null && srclocker.getLockernumber().length() > 0) {
                if(whereClause.length()==0)
                    whereClause = " A."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]+" LIKE '" + srclocker.getLockernumber() + "%' ";
                else
                    whereClause += " AND A."+PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]+" LIKE '" + srclocker.getLockernumber() + "%' ";
                
                System.out.println("\t srclocker.getLockernumber() = " + srclocker.getLockernumber());
            }

            if(srclocker.getLockerkey() != null && srclocker.getLockerkey().length() > 0) {
                if(whereClause.length()==0)
                    whereClause = " A."+PstLocker.fieldNames[PstLocker.FLD_KEY_NUMBER]+" LIKE '" + srclocker.getLockerkey() + "%' ";
                else
                    whereClause += " AND A."+PstLocker.fieldNames[PstLocker.FLD_KEY_NUMBER]+" LIKE '" + srclocker.getLockerkey() + "%' ";
                
                System.out.println("\t srclocker.getLockerkey() = " + srclocker.getLockerkey());
            }


            if(srclocker.getSparekey() != null && srclocker.getSparekey().length() > 0) {
                if(whereClause.length()==0)
                    whereClause = " A."+PstLocker.fieldNames[PstLocker.FLD_SPARE_KEY]+" = " + srclocker.getSparekey() + " ";
                else
                    whereClause += " AND A."+PstLocker.fieldNames[PstLocker.FLD_SPARE_KEY]+" = " + srclocker.getSparekey() + " ";
                
                System.out.println("\t srclocker.getLockerkey() = " + srclocker.getSparekey());
            }

            if(srclocker.getCondition() >= 0 && String.valueOf(srclocker.getCondition()) != null && String.valueOf(srclocker.getCondition()).length() > 0) {
                if(whereClause.length()==0)
                    whereClause = " C."+PstLockerCondition.fieldNames[PstLockerCondition.FLD_CONDITION_ID]+" = " + srclocker.getCondition() + " ";
                else
                    whereClause += " AND C."+PstLockerCondition.fieldNames[PstLockerCondition.FLD_CONDITION_ID]+" = " + srclocker.getCondition() + " ";
                
                System.out.println("\t srclocker.getConditionId() = " + srclocker.getCondition());
            }
            
            if(whereClause.length()>0)
                sql += " WHERE "+whereClause;
            
            //sql += " GROUP BY A.LOCKER_ID ";

            System.out.println("\t SQL getCountSearch : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                count = rs.getInt(1);
            }
        }
        catch (Exception e) {
            System.out.println("\t Exception on 'SessLocker.class' -> searchLocker : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
            System.out.println("\t Count : " + count);
            return count;
        }
    }
    
    public static Vector getLockersGlobal() {
        DBResultSet dbrs = null;
        Vector vResult = new Vector(1,1);
        Vector vLocation = new Vector(1,1);
        Vector vCount = new Vector(1,1);
        Vector vLocationId = new Vector(1,1);
        
        try {
            String sql = "";
            
            sql += " SELECT a."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID]
                    +"AS LOCATION_ID,a."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION]
                    +" AS LOCATION, count(*) as COUNT ";
            sql += " FROM "+PstLockerLocation.TBL_HR_LOCKER_LOCATION
                    +" a,"+PstLocker.TBL_HR_LOCKER
                    +" b ";
            sql += " WHERE a."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID]
                    +"= b."+PstLocker.fieldNames[PstLocker.FLD_LOCATION_ID];
            sql += " GROUP BY a."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID];

            System.out.println("\t SQL getLockersGlobal : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                vLocation.add(rs.getString("LOCATION"));
                vCount.add(rs.getString("COUNT"));
                vLocationId.add(rs.getString("LOCATION_ID"));
            }
            vResult.add(vLocation);
            vResult.add(vCount);
            vResult.add(vLocationId);
        }
        catch (Exception e) {
            System.out.println("\t Exception on 'SessLocker.class' -> getLockersGlobal : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return vResult;
    }
    
    public static int getLockerNoKeys(String location) {
        DBResultSet dbrs = null;
        int result = 0;
        
        try {
            String sql = "";
            
            sql += " SELECT count(*) as COUNT ";
            sql += " FROM "+PstLocker.TBL_HR_LOCKER;
            sql += " WHERE "+PstLocker.fieldNames[PstLocker.FLD_LOCATION_ID]+" = " + location + " ";
            sql += " AND spare_key = 0 ";

            System.out.println("\t SQL getLockerNoKeys : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                result = rs.getInt(1);
            }
        }
        catch (Exception e) {
            System.out.println("\t Exception on 'SessLocker.class' -> getLockerNoKeys : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }
    
    /** for get data employee and department by oid locker
     * @param oidLocker
     * @return
     */    
    public static Vector getEmployeeDepartment(long oidLocker){
        DBResultSet dbrs = null;
        Vector list = new Vector(1,1);
        try{
            String sql = "SELECT "+
                " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                ", DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+
                ", DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                " FROM "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP "+
                " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" AS DEPT "+
                " ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                " = DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                " WHERE EMP.LOCKER_ID = "+oidLocker;
            
                System.out.println("\t SQL getEmployeeDepartment : " + sql);

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while(rs.next()) {
                    Employee employee = new Employee();
                    Department department = new Department();
                    Vector rox = new Vector(1,1);
                    
                    employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                    employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                    rox.add(employee);
                    
                    department.setOID(rs.getLong(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]));
                    department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                    rox.add(department);
                    
                    list.add(rox);
                }
            
        }catch(Exception e){
            System.out.println("Err getEmployeeDepartment : "+e.toString());
        }finally{
            DBResultSet.close(dbrs);
        }
        return list;
    }
    
    public static int getLockerConditionCount(String location, String condition) {
        DBResultSet dbrs = null;
        int result = 0;
        
        try {
            String sql = "";
            
            sql += " SELECT count(*) as COUNT ";
            sql += " FROM "+PstLocker.TBL_HR_LOCKER
                    +" a, "+PstLockerLocation.TBL_HR_LOCKER_LOCATION
                    +" b, "+PstLockerCondition.TBL_HR_LOCKER_CONDITION
                    +" c ";
            sql += " WHERE a."+PstLocker.fieldNames[PstLocker.FLD_LOCATION_ID]+" = b."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION_ID]+" ";
            sql += " AND a."+PstLocker.fieldNames[PstLocker.FLD_CONDITION_ID]+" = c."+PstLockerCondition.fieldNames[PstLockerCondition.FLD_CONDITION_ID]+" ";
            sql += " AND b."+PstLockerLocation.fieldNames[PstLockerLocation.FLD_LOCATION]+" LIKE '" + location + "' ";
            sql += " AND c."+PstLockerCondition.fieldNames[PstLockerCondition.FLD_CONDITION]+" LIKE '" + condition + "' ";

            System.out.println("\t SQL getLockerConditionCount : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                result = rs.getInt(1);
            }
        }
        catch (Exception e) {
            System.out.println("\t Exception on 'SessLocker.class' -> getLockerConditionCount : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }
    
    /**
     * Mencari data locker berdasarkan acuan pencarian
     * ditambahkan dengan data employee
     */
    public static Vector listLockerEmp(int start, int recordToGet,SrcLocker srcLocker, boolean isLockerUsed){
        Vector vList = new Vector(1,1);
        Vector vLocker = new Vector(1,1);
        String whereClause = "";
        if(srcLocker.getLocation()>0){
            whereClause += PstLocker.fieldNames[PstLocker.FLD_LOCATION_ID]
                    +" = "+srcLocker.getLocation();
        }
        
        if(srcLocker.getLockernumber()!=null & srcLocker.getLockernumber().length()>0){
            if(whereClause.length()>0){
                whereClause += " AND ";
            }
            whereClause += PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]
                    +" = '"+srcLocker.getLockernumber()+"'";
        }
        
        if(srcLocker.getLockerkey()!=null & srcLocker.getLockerkey().length()>0){
            if(whereClause.length()>0){
                whereClause += " AND ";
            }
            whereClause += PstLocker.fieldNames[PstLocker.FLD_KEY_NUMBER]
                    +" = '"+srcLocker.getLockerkey()+"'";
        }
        
        if(srcLocker.getSparekey()!=null & srcLocker.getSparekey().length()>0){
            if(whereClause.length()>0){
                whereClause += " AND ";
            }
            whereClause += PstLocker.fieldNames[PstLocker.FLD_SPARE_KEY]
                    +" = '"+srcLocker.getSparekey()+"'";
        }
        
        if(srcLocker.getCondition()>1){
            if(whereClause.length()>0){
                whereClause += " AND ";
            }
            whereClause += PstLocker.fieldNames[PstLocker.FLD_CONDITION_ID]
                    +" = "+srcLocker.getCondition();
        }
        
        vLocker = PstLocker.list(start, recordToGet, whereClause, PstLocker.fieldNames[PstLocker.FLD_LOCKER_NUMBER]);
        
        for(int i=0;i<vLocker.size();i++){
            Locker locker = new Locker();
            locker = (Locker)vLocker.get(i);
            String strWhere = PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID]
                    +" = "+locker.getOID()
                    +" AND "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    +" = "+PstEmployee.NO_RESIGN;
            Vector vEmployee = new Vector(1,1);
            String strOrder = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    +", "+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    +", "+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    +", "+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
            vEmployee = PstEmployee.list(0, 0, strWhere, strOrder);
            if(isLockerUsed){
                //Memasukkan locker yang sudah dipergunakan juga
                Vector vTemp = new Vector();
                vTemp.add(locker);
                vTemp.add(vEmployee);
                vList.add(vTemp);
            }else{
                //Mencari yang belum dipergunakan
                if(vEmployee.size()<=0){
                    Vector vTemp = new Vector();
                    vTemp.add(locker);
                    vTemp.add(vEmployee);
                    vList.add(vTemp);
                }
            }
        }
        
        return vList;
    }
    
}
