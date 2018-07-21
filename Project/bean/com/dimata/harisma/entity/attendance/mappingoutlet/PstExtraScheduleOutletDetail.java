/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance.mappingoutlet;

import com.dimata.harisma.entity.attendance.EmpScheduleReport;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.entity.I_Persintent;
import com.dimata.util.Command;
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
public class PstExtraScheduleOutletDetail extends DBHandler implements I_DBType, I_Language, I_DBInterface, I_Persintent {

    public static final String TBL_EXTRA_SCHEDULE_OUTLET_DETAIL = "hr_extra_schedule_outlet_detail";
    
    public static final int FLD_EXTRA_SCHEDULE_MAPPING_DETAIL_ID=0;
    public static final int FLD_EXTRA_SCHEDULE_MAPPING_ID=1;
    public static final int FLD_EMPLOYEE_ID=2;
    public static final int FLD_START_DATE_PLAN=3;
    public static final int FLD_END_DATE_PLAN=4;
    public static final int FLD_START_DATE_REAL=5;
    public static final int FLD_END_DATE_REAL=6;
    public static final int FLD_REST_TIME_START=7;
    public static final int FLD_REST_TIME_HR=8;
    public static final int FLD_JOB_DESCH=9;
    //public static final int FLD_TYPE_OFF_SCHEDULE=10;
    public static final int FLD_LOCATION_ID=10;
    public static final int FLD_STATUS_DOCUMENT=11;
    
    public static final String[] fieldNames = {
    "EXTRA_SCHEDULE_MAPPING_DETAIL_ID",
    "EXTRA_SCHEDULE_MAPPING_ID",
    "EMPLOYEE_ID",
    "START_DATE_PLAN",
    "END_DATE_PLAN",
    "START_DATE_REAL",
    "END_DATE_REAL",
    "REST_TIME_START",
    "REST_TIME_HR",
    "JOB_DESCH",
   // "TYPE_OFF_SCHEDULE",
    "LOCATION_ID",
    "STATUS_DOCUMENT"
    };
    public static final int[] fieldTypes = {
    
     TYPE_LONG + TYPE_PK + TYPE_ID,//"EXTRA_SCHEDULE_MAPPING_DETAIL_ID",
     TYPE_LONG, //"EXTRA_SCHEDULE_MAPPING_ID",
     TYPE_LONG, //"EMPLOYEE_ID",
     TYPE_DATE, //"START_DATE_PLAN",
     TYPE_DATE, //"END_DATE_PLAN",
     TYPE_DATE, //"START_DATE_REAL",
     TYPE_DATE, //"END_DATE_REAL",
     TYPE_DATE, //"REST_TIME_START",
     TYPE_FLOAT,//"REST_TIME_HR",
    TYPE_STRING, //"JOB_DESCH",
    TYPE_LONG, //"LOCATION_ID",
    TYPE_INT, //"STATUS_DOCUMENT"
    };

    public static final int OVERTIME_FORM=1; 
    public static final int EXTRA_SCHEDULE_FORM=2;
    
    public static final String[] fieldTypeSchedule = {
        "Extra Schedule",
        "Overtime",  
    };
    
    public static final int[] fieldTypeVal = {
        OVERTIME_FORM,
        EXTRA_SCHEDULE_FORM
    };
    
    public static Vector getTypeOffScheduleKeys() {
        Vector keys = new Vector();

        for (int i = 0; i < fieldTypeSchedule.length; i++) {
            keys.add(fieldTypeSchedule[i]);
        }

        return keys;
    }

    public static Vector getTypeOffScheduleValues() {
        Vector values = new Vector();

        for (int i = 1; i <= fieldTypeVal.length; i++) {
            values.add(String.valueOf(i));
        }

        return values;
    }

    public PstExtraScheduleOutletDetail() {
    }

    public PstExtraScheduleOutletDetail(int i) throws DBException {
        super(new PstExtraScheduleOutletDetail());
    }

    public PstExtraScheduleOutletDetail(String sOid) throws DBException {
        super(new PstExtraScheduleOutletDetail(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstExtraScheduleOutletDetail(long lOid) throws DBException {
        super(new PstExtraScheduleOutletDetail(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstExtraScheduleOutletDetail().getClass().getName();
    }

    public String getTableName() {
        return TBL_EXTRA_SCHEDULE_OUTLET_DETAIL;
    }

   public long fetch(Entity ent) {
        ExtraScheduleOutletDetail extraScheduleOutletDetail = new ExtraScheduleOutletDetail();
        try {
            extraScheduleOutletDetail = fetchExc(ent.getOID());
        } catch (DBException ex) {
            System.out.println("Exc"+ex);
        }
        return extraScheduleOutletDetail.getOID();
    }

    public long update(Entity ent) {
        long oid=0;
        try {
            oid= updateExc((ExtraScheduleOutletDetail) ent);
        } catch (DBException ex) {
            System.out.println("Exc"+ex);
        }
        return oid;
    }

    public long delete(Entity ent) {
        long oid=0;
         if (ent == null) {
            try {
                oid = deleteExc(ent.getOID());
               
            } catch (DBException ex) {
                  System.out.println("Exc"+ex);
            }
        }
        return oid;
    }

    public long insert(Entity ent) {
        long oid=0;
        try {
            oid= insertExc((ExtraScheduleOutletDetail) ent);
        } catch (DBException ex) {
            System.out.println("Exc"+ex);
        }
         return oid;
    }
   

  
    public static ExtraScheduleOutletDetail fetchExc(long oid) throws DBException {
        try {
            ExtraScheduleOutletDetail extraScheduleOutletDetail = new ExtraScheduleOutletDetail();
            PstExtraScheduleOutletDetail pstExtraScheduleOutletDetail = new PstExtraScheduleOutletDetail(oid);
            extraScheduleOutletDetail.setOID(oid);

            
            extraScheduleOutletDetail.setExtraScheduleMappingId(pstExtraScheduleOutletDetail.getlong(FLD_EXTRA_SCHEDULE_MAPPING_ID));
            extraScheduleOutletDetail.setEmployeeId(pstExtraScheduleOutletDetail.getlong(FLD_EMPLOYEE_ID));
            extraScheduleOutletDetail.setStartDatePlan(pstExtraScheduleOutletDetail.getDate(FLD_START_DATE_PLAN));
            extraScheduleOutletDetail.setEndDatePlan(pstExtraScheduleOutletDetail.getDate(FLD_END_DATE_PLAN));
            extraScheduleOutletDetail.setStartDateReal(pstExtraScheduleOutletDetail.getDate(FLD_START_DATE_REAL));
            extraScheduleOutletDetail.setEndDateReal(pstExtraScheduleOutletDetail.getDate(FLD_END_DATE_REAL));
            extraScheduleOutletDetail.setRestTimeStart(pstExtraScheduleOutletDetail.getDate(FLD_REST_TIME_START));
            extraScheduleOutletDetail.setRestTimeHr(pstExtraScheduleOutletDetail.getfloat(FLD_REST_TIME_HR));
            extraScheduleOutletDetail.setJobDesc(pstExtraScheduleOutletDetail.getString(FLD_JOB_DESCH));
            //extraScheduleOutletDetail.setTypeOffSchedule(pstExtraScheduleOutletDetail.getInt(FLD_TYPE_OFF_SCHEDULE));
            extraScheduleOutletDetail.setLocationId(pstExtraScheduleOutletDetail.getlong(FLD_LOCATION_ID));
            extraScheduleOutletDetail.setStatusDocDetail(pstExtraScheduleOutletDetail.getInt(FLD_STATUS_DOCUMENT));
            

            return extraScheduleOutletDetail;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstExtraScheduleOutletDetail(0), DBException.UNKNOWN);
        }
    }

    public synchronized static long insertExc(ExtraScheduleOutletDetail extraScheduleOutletDetail) throws DBException {
        try {
            PstExtraScheduleOutletDetail pstExtraScheduleOutletDetail = new PstExtraScheduleOutletDetail(0);
            
            pstExtraScheduleOutletDetail.setLong(FLD_EXTRA_SCHEDULE_MAPPING_ID, extraScheduleOutletDetail.getExtraScheduleMappingId());
            
            pstExtraScheduleOutletDetail.setLong(FLD_EMPLOYEE_ID, extraScheduleOutletDetail.getEmployeeId());
            pstExtraScheduleOutletDetail.setDate(FLD_START_DATE_PLAN, extraScheduleOutletDetail.getStartDatePlan());
            pstExtraScheduleOutletDetail.setDate(FLD_END_DATE_PLAN, extraScheduleOutletDetail.getEndDatePlan());
            pstExtraScheduleOutletDetail.setDate(FLD_START_DATE_REAL, extraScheduleOutletDetail.getStartDateReal());
            pstExtraScheduleOutletDetail.setDate(FLD_END_DATE_REAL, extraScheduleOutletDetail.getEndDateReal());
            pstExtraScheduleOutletDetail.setDate(FLD_REST_TIME_START, extraScheduleOutletDetail.getRestTimeStart());
            pstExtraScheduleOutletDetail.setFloat(FLD_REST_TIME_HR, extraScheduleOutletDetail.getRestTimeHr());
            pstExtraScheduleOutletDetail.setString(FLD_JOB_DESCH, extraScheduleOutletDetail.getJobDesc());
            //pstExtraScheduleOutletDetail.setInt(FLD_TYPE_OFF_SCHEDULE, extraScheduleOutletDetail.getTypeOffSchedule());
            pstExtraScheduleOutletDetail.setLong(FLD_LOCATION_ID, extraScheduleOutletDetail.getLocationId());
            pstExtraScheduleOutletDetail.setInt(FLD_STATUS_DOCUMENT, extraScheduleOutletDetail.getStatusDocDetail());
            

            pstExtraScheduleOutletDetail.insert();
            extraScheduleOutletDetail.setOID(pstExtraScheduleOutletDetail.getlong(FLD_EXTRA_SCHEDULE_MAPPING_DETAIL_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstExtraScheduleOutletDetail(0), DBException.UNKNOWN);
        }
        return extraScheduleOutletDetail.getOID();
    }

    public static long updateExc(ExtraScheduleOutletDetail extraScheduleOutletDetail) throws DBException {
        try {
            if (extraScheduleOutletDetail.getOID() != 0) {
                
                PstExtraScheduleOutletDetail pstExtraScheduleOutletDetail = new PstExtraScheduleOutletDetail(extraScheduleOutletDetail.getOID());
                
            pstExtraScheduleOutletDetail.setLong(FLD_EXTRA_SCHEDULE_MAPPING_ID, extraScheduleOutletDetail.getExtraScheduleMappingId());
            pstExtraScheduleOutletDetail.setLong(FLD_EMPLOYEE_ID, extraScheduleOutletDetail.getEmployeeId());
            pstExtraScheduleOutletDetail.setDate(FLD_START_DATE_PLAN, extraScheduleOutletDetail.getStartDatePlan());
            pstExtraScheduleOutletDetail.setDate(FLD_END_DATE_PLAN, extraScheduleOutletDetail.getEndDatePlan());
            pstExtraScheduleOutletDetail.setDate(FLD_START_DATE_REAL, extraScheduleOutletDetail.getStartDateReal());
            pstExtraScheduleOutletDetail.setDate(FLD_END_DATE_REAL, extraScheduleOutletDetail.getEndDateReal());
            pstExtraScheduleOutletDetail.setDate(FLD_REST_TIME_START, extraScheduleOutletDetail.getRestTimeStart());
            pstExtraScheduleOutletDetail.setFloat(FLD_REST_TIME_HR, extraScheduleOutletDetail.getRestTimeHr());
            pstExtraScheduleOutletDetail.setString(FLD_JOB_DESCH, extraScheduleOutletDetail.getJobDesc());
            //pstExtraScheduleOutletDetail.setInt(FLD_TYPE_OFF_SCHEDULE, extraScheduleOutletDetail.getTypeOffSchedule());
            pstExtraScheduleOutletDetail.setLong(FLD_LOCATION_ID, extraScheduleOutletDetail.getLocationId());
            pstExtraScheduleOutletDetail.setInt(FLD_STATUS_DOCUMENT, extraScheduleOutletDetail.getStatusDocDetail()); 

                pstExtraScheduleOutletDetail.update();
               
                return extraScheduleOutletDetail.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstExtraScheduleOutletDetail(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstExtraScheduleOutletDetail pstExtraScheduleOutletDetail = new PstExtraScheduleOutletDetail(oid);
            pstExtraScheduleOutletDetail.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstExtraScheduleOutletDetail(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 1000, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_EXTRA_SCHEDULE_OUTLET_DETAIL;

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

            //System.out.println("sql::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                ExtraScheduleOutletDetail extraScheduleOutletDetail = new ExtraScheduleOutletDetail();
                resultToObject(rs, extraScheduleOutletDetail);
                lists.add(extraScheduleOutletDetail);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    
    public static Vector list3(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT o.*, odt."+PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE]
            + ", emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
            + " FROM " + TBL_EXTRA_SCHEDULE_OUTLET_DETAIL + " as o"
            + " INNER JOIN " + PstExtraScheduleOutlet.TBL_EXTRA_SCHEDULE_OUTLET + " as o ON odt." + PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_EXTRA_SCHEDULE_MAPPING_ID] + "= o." + PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_EXTRA_SCHEDULE_MAPPING_ID]
            + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " as emp ON odt." + fieldNames[FLD_EMPLOYEE_ID] + "= emp."+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
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

            //System.out.println("sql::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                ExtraScheduleOutletDetail extraScheduleOutletDetail = new ExtraScheduleOutletDetail();
                resultToObject(rs, extraScheduleOutletDetail);
                extraScheduleOutletDetail.setDocNumber(rs.getString("odt."+PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE]));
                 extraScheduleOutletDetail.setFullName(rs.getString("emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                lists.add(extraScheduleOutletDetail);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
   /**
    * Create by satrya 2014-05-26
    * untuk list  extra schedule
    * @param limitStart
    * @param recordToGet
    * @param whereClause
    * @param order
    * @param dtSTart
    * @param dtEnd
    * @return 
    */
    public static Vector listJointExtraSchedule(int limitStart, int recordToGet, String whereClause,String order, Date dtSTart,Date dtEnd) {
        Vector lists = new Vector();
        if(dtSTart==null || dtEnd==null){
            return lists;
        }
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT es.*,esd.*,emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+",emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+",emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
+ " FROM "+ PstEmployee.TBL_HR_EMPLOYEE + " AS emp "
+ " LEFT JOIN "+ PstExtraScheduleOutletDetail.TBL_EXTRA_SCHEDULE_OUTLET_DETAIL + " AS esd ON  esd."+PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EMPLOYEE_ID]+"=emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
+ " AND \""+Formater.formatDate(dtEnd, "yyyy-MM-dd 23:59") +"\" >= esd."+PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_START_DATE_PLAN]+ " AND  esd."+PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_END_DATE_PLAN]+ " >= \""+Formater.formatDate(dtSTart, "yyyy-MM-dd 00:00")+"\"" 
+ " LEFT JOIN "+ PstExtraScheduleOutlet.TBL_EXTRA_SCHEDULE_OUTLET + " AS es ON es."+PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_EXTRA_SCHEDULE_MAPPING_ID] + " = esd."+PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EXTRA_SCHEDULE_MAPPING_ID];

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
                Vector listAll = new Vector();
                Employee employee = new Employee();
                employee.setOID(rs.getLong("emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                listAll.add(employee);
                
                ExtraScheduleOutlet extraScheduleOutlet = new ExtraScheduleOutlet();
                PstExtraScheduleOutlet.resultToObject(rs, extraScheduleOutlet); 
                listAll.add(extraScheduleOutlet);
                
                ExtraScheduleOutletDetail extraScheduleOutletDetail = new ExtraScheduleOutletDetail();
                resultToObject(rs, extraScheduleOutletDetail);
                listAll.add(extraScheduleOutletDetail);
                
                lists.add(listAll); 
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    public static void resultToObject(ResultSet rs, ExtraScheduleOutletDetail extraScheduleOutletDetail) {
        try {
            extraScheduleOutletDetail.setOID(rs.getLong(PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EXTRA_SCHEDULE_MAPPING_DETAIL_ID]));
            
            extraScheduleOutletDetail.setExtraScheduleMappingId(rs.getLong(fieldNames[FLD_EXTRA_SCHEDULE_MAPPING_ID]));
            extraScheduleOutletDetail.setEmployeeId(rs.getLong(fieldNames[FLD_EMPLOYEE_ID]));
            extraScheduleOutletDetail.setStartDatePlan(DBHandler.convertDate(rs.getDate(fieldNames[FLD_START_DATE_PLAN]), rs.getTime(fieldNames[FLD_START_DATE_PLAN])));
            extraScheduleOutletDetail.setEndDatePlan(DBHandler.convertDate(rs.getDate(fieldNames[FLD_END_DATE_PLAN]), rs.getTime(fieldNames[FLD_END_DATE_PLAN])));
            extraScheduleOutletDetail.setStartDateReal(DBHandler.convertDate(rs.getDate(fieldNames[FLD_START_DATE_REAL]), rs.getTime(fieldNames[FLD_START_DATE_REAL])));
            extraScheduleOutletDetail.setEndDateReal(DBHandler.convertDate(rs.getDate(fieldNames[FLD_END_DATE_REAL]), rs.getTime(fieldNames[FLD_END_DATE_REAL])));
            extraScheduleOutletDetail.setRestTimeStart(DBHandler.convertDate(rs.getDate(fieldNames[FLD_REST_TIME_START]), rs.getTime(fieldNames[FLD_REST_TIME_START])));
            extraScheduleOutletDetail.setRestTimeHr(rs.getFloat(fieldNames[FLD_REST_TIME_HR]));
            extraScheduleOutletDetail.setJobDesc(rs.getString(fieldNames[FLD_JOB_DESCH]));
            //extraScheduleOutletDetail.setTypeOffSchedule(rs.getInt(fieldNames[FLD_TYPE_OFF_SCHEDULE]));
            extraScheduleOutletDetail.setLocationId(rs.getLong(fieldNames[FLD_LOCATION_ID]));
            extraScheduleOutletDetail.setStatusDocDetail(rs.getInt(fieldNames[FLD_STATUS_DOCUMENT]));
            
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long extraScheduleId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_EXTRA_SCHEDULE_OUTLET_DETAIL + " WHERE "
                    + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EXTRA_SCHEDULE_MAPPING_DETAIL_ID] + " = '" + extraScheduleId + "'";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EXTRA_SCHEDULE_MAPPING_DETAIL_ID] + ") FROM " + TBL_EXTRA_SCHEDULE_OUTLET_DETAIL;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    ExtraScheduleOutletDetail extraScheduleOutletDetail = (ExtraScheduleOutletDetail) list.get(ls);
                    if (oid == extraScheduleOutletDetail.getOID()) {
                        found = true;
                    }
                }
            }
        }
        if ((start >= size) && (size > 0)) {
            start = start - recordToGet;
        }

        return start;
    }

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + mdl;
        if (start == 0) {
            cmd = Command.FIRST;
        } else {
            if (start == (vectSize - recordToGet)) {
                cmd = Command.LAST;
            } else {
                start = start + recordToGet;
                if (start <= (vectSize - recordToGet)) {
                    cmd = Command.NEXT;
                    System.out.println("next.......................");
                } else {
                    start = start - recordToGet;
                    if (start > 0) {
                        cmd = Command.PREV;
                        System.out.println("prev.......................");
                    }
                }
            }
        }

        return cmd;
    }
    
    
    public static Vector listExtraScheduleOverlapVer3(long exceptionDetailOid, 
             int limitStart, int recordToGet,long oidDepartment, String fullName,
             Date selectedDateFrom, Date selectedDateTo, long oidSection, String empNum, long employeeOid, String order) {
         String whereClauseEmpTime = "";
        
         String whereClauseReq = "("+ fieldNames[FLD_STATUS_DOCUMENT]+"<>"+
                 I_DocStatus.DOCUMENT_STATUS_CANCELLED   + ") AND ("
                 + "\""+Formater.formatDate(selectedDateTo,"yyyy-MM-dd HH:mm")+"\" > odt."+fieldNames[FLD_START_DATE_PLAN]+ " AND odt. "+ fieldNames[FLD_END_DATE_PLAN] + ">"+"\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd HH:mm")+"\""+")";
         if((selectedDateFrom != null) && (selectedDateTo != null)) {
            whereClauseEmpTime = whereClauseReq;
        }
         if(oidDepartment !=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ "dep."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" = " + oidDepartment;
        }
        if(fullName !=null && fullName.length() > 0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                 + " LIKE '%"+fullName.trim()+"%'";
        }
      /* if(oidSection !=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ ;
        }*/
        if(empNum !=null && empNum.length() > 0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                 + " LIKE '%"+empNum.trim()+"%'";
        }

        if(employeeOid!=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " odt."+fieldNames[FLD_EMPLOYEE_ID]
                                 + " = '"+employeeOid+"'";
        }
        
        if(exceptionDetailOid!=0){
            whereClauseEmpTime = "( odt."+fieldNames[FLD_EXTRA_SCHEDULE_MAPPING_DETAIL_ID]
                                 + " <> '"+exceptionDetailOid+"' AND " + whereClauseEmpTime + ")";
        }

         
        
         return list3(limitStart, recordToGet, whereClauseEmpTime, order);
     }
     
    
    
    public static java.util.Hashtable isScheduleOverlapExtraSchedule(long employeeId,Vector Vschedule,long ScheduleOff,Date selectedFromDate,Date selectedToDate,  EmpScheduleReport empScheduleReport) { 
        int kk=0;
         java.util.Hashtable hasOverlapOT = new java.util.Hashtable();
        if(employeeId==0 || Vschedule==null || Vschedule.size()==0 || selectedFromDate==null || selectedToDate==null || ScheduleOff==0){
            return hasOverlapOT;
        }
       // boolean scheduleOverlapOvertime=false;
      
        ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
        Hashtable hasSchedule = new Hashtable();
        Date selectedFrom= (Date)selectedFromDate.clone();
        selectedFrom = new Date(selectedFrom.getTime() -  24*60*60*1000);
        Date selectedTo = (Date)selectedToDate.clone();
        //di hidden karena pasti dia overlaping jdinya
       // selectedTo = new Date(selectedToDate.getTime() + 24*60*60*1000);/
        if(Vschedule!=null && Vschedule.size()>0){
              while (selectedFrom.before(selectedTo) || selectedFrom.equals(selectedTo)) {
                Date dtScheduleTimeIn = null;
                Date dtScheduleTimeOut = null;
               
                Date dtIdx = empScheduleReport.getDtIDate(kk);
                Long oidSch1St = empScheduleReport.getEmpSchedules1st(kk);
                    if(scheduleSymbol!=null && scheduleSymbol.getOID()!=oidSch1St){
                   scheduleSymbol = (ScheduleSymbol)hasSchedule.get(""+oidSch1St);
                   if(scheduleSymbol==null){

                    if(oidSch1St!=0){
                      try{
                     scheduleSymbol  = PstScheduleSymbol.fetchExc(oidSch1St);
                     }catch(Exception exc){}
                     hasSchedule.put(""+oidSch1St,scheduleSymbol );
                   }
                   }
                }else{
                   if(scheduleSymbol==null){
                       if(oidSch1St!=0){
                          try{
                         scheduleSymbol  = PstScheduleSymbol.fetchExc(oidSch1St);
                         }catch(Exception exc){}
                         hasSchedule.put(""+oidSch1St,scheduleSymbol );
                       }
                   }
               }
               if(oidSch1St!=0 && oidSch1St==ScheduleOff){
                   //scheduleOverlapOvertime=false;
                   hasOverlapOT.put(false,oidSch1St+"_"+employeeId);
                   //return hasOverlapOT;  
               }else{
               if(scheduleSymbol!=null && dtIdx!=null){
                   if(scheduleSymbol.getTimeIn()!=null){
                        dtScheduleTimeIn = (Date)dtIdx.clone();
                        dtScheduleTimeIn.setHours(scheduleSymbol.getTimeIn().getHours());
                        dtScheduleTimeIn.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                        dtScheduleTimeIn.setSeconds(0);
                    }
                    if(scheduleSymbol.getTimeOut()!=null){
                        dtScheduleTimeOut = (Date)dtIdx.clone();
                        dtScheduleTimeOut.setHours(scheduleSymbol.getTimeOut().getHours());
                        dtScheduleTimeOut.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                        dtScheduleTimeOut.setSeconds(0);
                        //jika ada cross day
                        if(dtScheduleTimeOut.getHours()<dtScheduleTimeIn.getHours()){
                            dtScheduleTimeOut.setTime(dtScheduleTimeOut.getTime()+24*60*60*1000); 
                        }
                    }
                    
                    if(scheduleSymbol.getTimeIn()!=null && scheduleSymbol.getTimeOut()!=null){
                      // if(selectedToDate.getTime() >=dtScheduleTimeIn.getTime()&& dtScheduleTimeOut.getTime()>=selectedFromDate.getTime()){
                        if(selectedToDate.getTime()>dtScheduleTimeIn.getTime() && dtScheduleTimeOut.getTime()> selectedFromDate.getTime()){
                              hasOverlapOT.put(true,oidSch1St+"_"+employeeId);
                            return hasOverlapOT;
                        }
                    
                    }
               }
              }
              selectedFrom = new Date(selectedFrom.getTime()+ 24*60*60*1000);
              kk = kk +1;
            }
            
        }
        
        return hasOverlapOT;
    }

 

}
