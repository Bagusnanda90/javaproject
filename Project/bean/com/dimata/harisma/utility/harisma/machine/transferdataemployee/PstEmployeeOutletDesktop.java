/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

import com.dimata.harisma.entity.attendance.employeeoutlet.*;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.location.PstLocation;
import com.dimata.harisma.session.attendance.employeeoutlet.SessEmployeeOutlet;
import com.dimata.harisma.utility.harisma.machine.db.DBException;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.db.DBResultSet;
import com.dimata.harisma.utility.harisma.machine.db.I_DBInterface;
import com.dimata.harisma.utility.harisma.machine.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class PstEmployeeOutletDesktop extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{

     public static final String TBL_HR_EMP_OUTLET ="hr_emp_outlet";
    
    public static final int FLD_OUTLET_EMPLOYEE_ID = 0;
    public static final int FLD_LOCATION_ID = 1;
    public static final int FLD_DATE_FROM = 2;
    public static final int FLD_DATE_TO = 3;
    public static final int FLD_POSITION_ID = 4;
    public static final int FLD_EMPLOYEE_ID = 5;
    public static final int FLD_TYPE_SCHEDULE = 6;
    //public static final int FLD_EMP_SCHEDULE_ID = 6;
    //public static final int FLD_EMP_SCHEDULE_ID_2ND = 7;
    
    public static final String[] fieldNames = {
        "OUTLET_EMPLOYEE_ID",
        "LOCATION_ID",
        "DATE_FROM",
        "DATE_TO",
        "POSITION_ID",
        "EMPLOYEE_ID",
        "TYPE_SCHEDULE"
        //"EMP_SCHEDULE_ID",
        //"EMP_SCHEDULE_ID_2ND"
        
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT
        //TYPE_LONG,
        //TYPE_LONG
        
    };
    
     public PstEmployeeOutletDesktop() {
    }
    
    public PstEmployeeOutletDesktop(int i) throws DBException {
        super(new PstEmployeeOutletDesktop());
    }
    
    public PstEmployeeOutletDesktop(String sOid) throws DBException {
        super(new PstEmployeeOutletDesktop(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstEmployeeOutletDesktop(long lOid) throws DBException {
        super(new PstEmployeeOutletDesktop(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    public int getFieldSize() {
         return fieldNames.length;
    }

    public String getTableName() {
        return TBL_HR_EMP_OUTLET;
    }

    public String[] getFieldNames() {
       return fieldNames;
    }

    public int[] getFieldTypes() {
           return fieldTypes;
    }

    public String getPersistentName() {
         return new PstEmployeeOutletDesktop().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
         EmployeeOutlet employeeOutlet = fetchExc(ent.getOID());
        return employeeOutlet.getOID();
    }
    
     public static EmployeeOutlet fetchExc(long oid) throws DBException {
        try {
            EmployeeOutlet employeeOutlet = new EmployeeOutlet();
            PstEmployeeOutletDesktop pstEmployeeOutlet = new PstEmployeeOutletDesktop(oid);
            employeeOutlet.setOID(oid);
            
            
            employeeOutlet.setDtFrom(pstEmployeeOutlet.getDate(FLD_DATE_FROM));
            employeeOutlet.setDtTo(pstEmployeeOutlet.getDate(FLD_DATE_TO));
            employeeOutlet.setEmployeeId(pstEmployeeOutlet.getlong(FLD_EMPLOYEE_ID));
            employeeOutlet.setLocationId(pstEmployeeOutlet.getlong(FLD_LOCATION_ID));
            employeeOutlet.setPositionId(pstEmployeeOutlet.getlong(FLD_POSITION_ID));
            employeeOutlet.setScheduleType(pstEmployeeOutlet.getInt(FLD_TYPE_SCHEDULE));
            //employeeOutlet.setSchedleSymbolId(pstEmployeeOutlet.getlong(FLD_EMP_SCHEDULE_ID));
            //employeeOutlet.setSchedleSymbolId(pstEmployeeOutlet.getlong(FLD_EMP_SCHEDULE_ID_2ND));
            
            return employeeOutlet;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeOutletDesktop(0), DBException.UNKNOWN);
        }
    }

    public long updateExc(Entity ent) throws Exception {
         return updateExc((EmployeeOutlet) ent);
    }

    public static long updateExc(EmployeeOutlet employeeOutlet) throws DBException {
        try {
            if (employeeOutlet.getOID() != 0) {
                PstEmployeeOutletDesktop pstEmployeeOutlet = new PstEmployeeOutletDesktop(employeeOutlet.getOID());
                
                pstEmployeeOutlet.setDate(FLD_DATE_FROM, employeeOutlet.getDtFrom());
                pstEmployeeOutlet.setDate(FLD_DATE_TO, employeeOutlet.getDtTo());
                pstEmployeeOutlet.setLong(FLD_EMPLOYEE_ID, employeeOutlet.getEmployeeId());
                pstEmployeeOutlet.setLong(FLD_LOCATION_ID, employeeOutlet.getLocationId());
                pstEmployeeOutlet.setLong(FLD_POSITION_ID, employeeOutlet.getPositionId());
                
                pstEmployeeOutlet.setLong(FLD_TYPE_SCHEDULE, employeeOutlet.getScheduleType());
                
               // pstEmployeeOutlet.setLong(FLD_EMP_SCHEDULE_ID, employeeOutlet.getSchedleSymbolId());
               // pstEmployeeOutlet.setLong(FLD_EMP_SCHEDULE_ID_2ND, employeeOutlet.getSchedleSymbolId2nd());
                pstEmployeeOutlet.update();
                return employeeOutlet.getOID();
                
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeOutletDesktop(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }

     public static long deleteExc(long oid) throws DBException {
        try {
            PstEmployeeOutletDesktop pstEmployeeOutlet = new PstEmployeeOutletDesktop(oid);
            pstEmployeeOutlet.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeOutletDesktop(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
       return insertExc((EmployeeOutlet)ent);
    }
    public static long insertExc(EmployeeOutlet employeeOutlet) throws DBException {
        try {
            PstEmployeeOutletDesktop pstEmployeeOutlet = new PstEmployeeOutletDesktop(0);
            
                pstEmployeeOutlet.setDate(FLD_DATE_FROM, employeeOutlet.getDtFrom());
                pstEmployeeOutlet.setDate(FLD_DATE_TO, employeeOutlet.getDtTo());
                pstEmployeeOutlet.setLong(FLD_EMPLOYEE_ID, employeeOutlet.getEmployeeId());
                pstEmployeeOutlet.setLong(FLD_LOCATION_ID, employeeOutlet.getLocationId());
                pstEmployeeOutlet.setLong(FLD_POSITION_ID, employeeOutlet.getPositionId());
                pstEmployeeOutlet.setInt(FLD_TYPE_SCHEDULE, employeeOutlet.getScheduleType());
               // pstEmployeeOutlet.setLong(FLD_EMP_SCHEDULE_ID, employeeOutlet.getSchedleSymbolId());
               // pstEmployeeOutlet.setLong(FLD_EMP_SCHEDULE_ID_2ND, employeeOutlet.getSchedleSymbolId2nd());
            pstEmployeeOutlet.insert(employeeOutlet.getOID());
            //employeeOutlet.setOID(pstEmployeeOutlet.getlong(FLD_OUTLET_EMPLOYEE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeOutletDesktop(0), DBException.UNKNOWN);
        }
        return employeeOutlet.getOID();
    }
    
    public static void resultToObject(ResultSet rs, EmployeeOutlet employeeOutlet) {
        try 
        {
            employeeOutlet.setOID(rs.getLong(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_OUTLET_EMPLOYEE_ID]));
             employeeOutlet.setDtFrom(rs.getDate(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_DATE_FROM]));
            employeeOutlet.setDtTo(rs.getDate(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_DATE_TO]));
            employeeOutlet.setEmployeeId(rs.getLong(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_EMPLOYEE_ID]));
            employeeOutlet.setLocationId(rs.getLong(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_LOCATION_ID]));
            employeeOutlet.setPositionId(rs.getLong(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_POSITION_ID]));
            employeeOutlet.setScheduleType(rs.getInt(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_TYPE_SCHEDULE]));
            //employeeOutlet.setSchedleSymbolId(rs.getLong(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_EMP_SCHEDULE_ID]));
            //employeeOutlet.setSchedleSymbolId2nd(rs.getLong(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_EMP_SCHEDULE_ID_2ND]));
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    private static void resultToObjectSessEmployee(ResultSet rs, SessEmployeeOutlet sessEmployeeOutlet) {
        try 
        {
            sessEmployeeOutlet.setEmployeeOutletId(rs.getLong(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_OUTLET_EMPLOYEE_ID]));
             sessEmployeeOutlet.setDtFrom(DBHandler.convertDate(rs.getDate(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_DATE_FROM]), rs.getTime(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_DATE_FROM])));
            sessEmployeeOutlet.setDtTo(DBHandler.convertDate(rs.getDate(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_DATE_TO]),rs.getTime(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_DATE_TO])));
            sessEmployeeOutlet.setEmployeeId(rs.getLong("emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
            sessEmployeeOutlet.setLocationId(rs.getLong(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_LOCATION_ID]));
            sessEmployeeOutlet.setPositionId(rs.getLong(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_POSITION_ID]));
            
            /*sessEmployeeOutlet.setSchedleSymbolId(rs.getLong(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_EMP_SCHEDULE_ID]));
            sessEmployeeOutlet.setSchedleSymbolId2nd(rs.getLong(PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_EMP_SCHEDULE_ID_2ND]));
            
            sessEmployeeOutlet.setBreakIn(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN]));
            sessEmployeeOutlet.setBreakOut(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN]));
            sessEmployeeOutlet.setTimeIn(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]));
            sessEmployeeOutlet.setTimeOut(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]));
            sessEmployeeOutlet.setSymbolSchedule(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
            */
            sessEmployeeOutlet.setLocationColor(rs.getString(PstLocation.fieldNames[PstLocation.FLD_COLOR_LOCATION]));
            
            sessEmployeeOutlet.setLocationName(rs.getString(PstLocation.fieldNames[PstLocation.FLD_NAME]));
            sessEmployeeOutlet.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
            
            sessEmployeeOutlet.setPositionKode(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION_KODE]));
            
            sessEmployeeOutlet.setEmpNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
            sessEmployeeOutlet.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
     public static Vector listAll(){
        return list(0, 500, "","");
    }
    
    public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_OUTLET;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                EmployeeOutlet employeeOutlet = new EmployeeOutlet();
                resultToObject(rs, employeeOutlet);
                lists.add(employeeOutlet);
            }
            rs.close();
            return lists;
            
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    /**
     * untuk list employee outlet
     * create by satrya 2014-03-03
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @param dtFrom
     * @param dtTo
     * @return 
     */
    public static Vector listEmployeeOutletJoin(int limitStart,int recordToGet, String whereClause, String order,Date dtFrom,Date dtTo){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT eo.*,loc."+PstLocation.fieldNames[PstLocation.FLD_COLOR_LOCATION]
            +",loc."+PstLocation.fieldNames[PstLocation.FLD_NAME]+",pos."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+",pos."+PstPosition.fieldNames[PstPosition.FLD_POSITION_KODE]
//            +",sch."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]+",sch."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]
//            +",sch."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]+",sch."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_OUT]
//            +",sch."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN]
              +",emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
              +",emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
              +",emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
            +" FROM "+TBL_HR_EMP_OUTLET  + " AS eo "
+ " INNER JOIN " +PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_EMPLOYEE_ID]+ " = emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
+ " INNER JOIN " +PstLocation.TBL_P2_LOCATION + " AS loc ON eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_LOCATION_ID]+ " = loc."+PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]
+ " INNER JOIN " + PstPosition.TBL_HR_POSITION  + " AS pos ON pos."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+"=eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_POSITION_ID];
//+ " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS sch ON sch."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+"=eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_EMP_SCHEDULE_ID];
if(whereClause!=null && whereClause.length()>0){
    if(dtFrom!=null && dtTo!=null){
        sql = sql + " WHERE " + whereClause + " AND \"" + Formater.formatDate(dtTo, "yyyy-MM-dd 23:59") + "\" >= eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_DATE_FROM]
        + " AND  eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_DATE_TO] + " >= \""+ Formater.formatDate(dtFrom, "yyyy-MM-dd 00:00")+"\"";
    }else{
        sql = sql + " WHERE " + whereClause;
    }
}else{
    if(dtFrom!=null && dtTo!=null){
        sql = sql + " WHERE \""+Formater.formatDate(dtTo, "yyyy-MM-dd HH:mm") + "\" >= eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_DATE_FROM]
        + " AND  eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_DATE_TO] + " >= \""+ Formater.formatDate(dtFrom, "yyyy-MM-dd HH:mm")+"\"";
    }
}
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                SessEmployeeOutlet sessEmployeeOutlet = new SessEmployeeOutlet();
                resultToObjectSessEmployee(rs, sessEmployeeOutlet);
                lists.add(sessEmployeeOutlet);
            }
            rs.close();
            return lists;
            
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    /**
     * mencari employee biarpun tidak ada data di employee outlet
     * create by satrya 2014-03-18
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @param dtFrom
     * @param dtTo
     * @return 
     */
    public static Vector listEmployeeOutletNullJoinEmployee(int limitStart,int recordToGet, String whereClause, String order,Date dtFrom,Date dtTo){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT eo.*,loc."+PstLocation.fieldNames[PstLocation.FLD_COLOR_LOCATION]
            +",loc."+PstLocation.fieldNames[PstLocation.FLD_NAME]+",pos."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+",pos."+PstPosition.fieldNames[PstPosition.FLD_POSITION_KODE]
              +",emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
              +",emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
               +",emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
            +" FROM "+PstEmployee.TBL_HR_EMPLOYEE + " AS emp "
+ " LEFT JOIN " +TBL_HR_EMP_OUTLET + " AS eo ON eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_EMPLOYEE_ID]+ " = emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
if(dtFrom!=null && dtTo!=null){
        sql = sql + " AND \"" + Formater.formatDate(dtTo, "yyyy-MM-dd 23:59") + "\" >= eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_DATE_FROM]
        + " AND  eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_DATE_TO] + " >= \""+ Formater.formatDate(dtFrom, "yyyy-MM-dd 00:00")+"\"";
}
sql = sql +" LEFT JOIN " +PstLocation.TBL_P2_LOCATION + " AS loc ON eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_LOCATION_ID]+ " = loc."+PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]
+ " LEFT JOIN " + PstPosition.TBL_HR_POSITION  + " AS pos ON pos."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+"=eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_POSITION_ID];
//+ " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS sch ON sch."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+"=eo."+PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_EMP_SCHEDULE_ID];
if(whereClause!=null && whereClause.length()>0){
        sql = sql + " WHERE " + whereClause;
}
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                SessEmployeeOutlet sessEmployeeOutlet = new SessEmployeeOutlet();
                resultToObjectSessEmployee(rs, sessEmployeeOutlet);
                lists.add(sessEmployeeOutlet);
            }
            rs.close();
            return lists;
            
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
     public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutletDesktop.FLD_OUTLET_EMPLOYEE_ID] + ") FROM " + TBL_HR_EMP_OUTLET;
            if(whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            int count = 0;
            while(rs.next()) { count = rs.getInt(1); }
            
            rs.close();
            return count;
        }catch(Exception e) {
            return 0;
        }finally {
            DBResultSet.close(dbrs);
        }
    }
     
      /* This method used to find current data */
    public static int findLimitStart( long oid, int recordToGet, String whereClause){
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found =false;
        for(int i=0; (i < size) && !found ; i=i+recordToGet){
            Vector list =  list(i,recordToGet, whereClause, order);
            start = i;
            if(list.size()>0){
                for(int ls=0;ls<list.size();ls++){
                    EmployeeOutlet employeeOutlet = (EmployeeOutlet)list.get(ls);
                    if(oid == employeeOutlet.getOID())
                        found=true;
                }
            }
        }
        if((start >= size) && (size > 0))
            start = start - recordToGet;
        
        return start;
    }
    
    public static Hashtable<String,Boolean> hashEmpMappingIdAda(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashEmpEMployeeIdSdhAda= new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "+fieldNames[FLD_OUTLET_EMPLOYEE_ID]+" FROM " + TBL_HR_EMP_OUTLET;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                hashEmpEMployeeIdSdhAda.put(""+rs.getLong(fieldNames[FLD_OUTLET_EMPLOYEE_ID]), true);
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashEmpEMployeeIdSdhAda;
        }
        
    }
    
    public static String StrEmpMappingIdAdaWithDate(int limitStart, int recordToGet, String whereClause, String order) {
        String StrEmpEMployeeIdSdhAda= " ";
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "+fieldNames[FLD_OUTLET_EMPLOYEE_ID]+" FROM " + TBL_HR_EMP_OUTLET + " AS empoutlet"
                    + " INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS loc ON loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + "=empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID]
                    ;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                StrEmpEMployeeIdSdhAda = StrEmpEMployeeIdSdhAda + rs.getLong(fieldNames[FLD_OUTLET_EMPLOYEE_ID]) + " ,";
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return StrEmpEMployeeIdSdhAda;
        }
        
    }
    
}
