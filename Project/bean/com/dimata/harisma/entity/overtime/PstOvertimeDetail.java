/*      
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.overtime;

/* package java */
import com.dimata.harisma.entity.attendance.PstPresence;
import java.util.Date;
import java.util.Vector;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
import java.sql.ResultSet;
import java.sql.Time;

/* package qdep */

import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;
import com.dimata.util.Formater;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.harisma.entity.attendance.Presence;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;

//Gede_2April2012{
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.payroll.PstOvt_Employee;
import com.dimata.harisma.entity.payroll.PstPayEmpLevel;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.harisma.session.payroll.SessOvertime;
import com.dimata.util.CalendarCalc;
import com.dimata.util.DateCalc;
import com.dimata.util.LogicParser;
import java.sql.Time;
import java.util.Hashtable;
//}

/**
 *
 * @author Wiweka
 */
public class PstOvertimeDetail extends DBHandler implements I_DBType, I_Language, I_DBInterface, I_PersintentExc, I_DocStatus {

    public static final String TBL_OVERTIME_DETAIL = "hr_overtime_detail";
    public static final int FLD_OVERTIME_DETAIL_ID = 0;
    public static final int FLD_OVERTIME_ID = 1;
    public static final int FLD_EMPLOYEE_ID = 2;
    public static final int FLD_JOBDESK = 3;
    public static final int FLD_DATE_FROM = 4;
    public static final int FLD_DATE_TO = 5;
    public static final int FLD_STATUS = 6;
    public static final int FLD_PAID_BY = 7;
    public static final int FLD_REAL_DATE_FROM = 8;
    public static final int FLD_REAL_DATE_TO = 9;
    public static final int FLD_PERIOD_ID = 10;
    public static final int FLD_EMPLOYEE_NUM = 11;    
    public static final int FLD_STD_WORK_SCHDL = 12;
    public static final int FLD_OVT_DURATION = 13;
    public static final int FLD_OVT_DOC_NR = 14;
    public static final int FLD_PAY_SLIP_ID = 15;
    public static final int FLD_TOT_IDX = 16;
    public static final int FLD_OVT_CODE = 17;     
    public static final int FLD_ALLOWANCE = 18;     
    public static final int FLD_REST_TIME_HR = 19;     
    public static final int FLD_REST_TIME_START = 20;
    //UPDATE BY SATRYA 2012-12-04
    public static final int FLD_FLAG_STATUS = 21;
    
    //UPDATE BY SATRYA 2012-12-04
    public static final int FLD_LOCATION_ID = 22;
    
    public static final String[] fieldNames = {
        "OVERTIME_DETAIL_ID", "OVERTIME_ID", "EMPLOYEE_ID",
        "JOBDESK", "DATE_FROM",
        "DATE_TO", "STATUS", "PAID_BY", "REAL_DATE_FROM", "REAL_DATE_TO",
        "PERIOD_ID",
        "EMPLOYEE_NR_OVTM",        
        "STD_WORK_SCHDL",
        "OVT_DURATION",
        "OVT_DOC_NR",
        "PAY_SLIP_ID",
        "TOT_IDX",
        "OVT_CODE",
        "ALLOWANCE",
        "REST_TIME_HR",
        "REST_TIME_START",
        "FLAG_STATUS",
        "LOCATION_ID"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG, TYPE_STRING, TYPE_STRING, TYPE_FLOAT, TYPE_STRING, TYPE_LONG, TYPE_FLOAT, TYPE_STRING,
        TYPE_INT, TYPE_FLOAT, TYPE_DATE,
        TYPE_INT,TYPE_LONG
    };
    public static final int[] docStValforValidOvDetail = {
        DOCUMENT_STATUS_FINAL,
        DOCUMENT_STATUS_PROCEED,
        DOCUMENT_STATUS_CLOSED,
        DOCUMENT_STATUS_POSTED,
        DOCUMENT_STATUS_PAID
    };
    public static final String sDocStValforValidOvDetail = "("
            + DOCUMENT_STATUS_FINAL + ","
            + DOCUMENT_STATUS_PROCEED + ","
            + DOCUMENT_STATUS_CLOSED + ","
            + DOCUMENT_STATUS_POSTED + ","
            + DOCUMENT_STATUS_PAID + " ) ";

    public PstOvertimeDetail() {
    }

    public PstOvertimeDetail(int i) throws DBException {
        super(new PstOvertimeDetail());
    }

    
    public PstOvertimeDetail(String sOid) throws DBException {
        super(new PstOvertimeDetail(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstOvertimeDetail(long lOid) throws DBException {
        super(new PstOvertimeDetail(0));
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
        return new PstOvertimeDetail().getClass().getName();
    }

    public String getTableName() {
        return TBL_OVERTIME_DETAIL;
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((OvertimeDetail) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((OvertimeDetail) ent);
    }

    public long fetchExc(Entity ent) throws Exception {
        OvertimeDetail overtimeDetail = fetchExc(ent.getOID());
        ent = (Entity) overtimeDetail;
        return overtimeDetail.getOID();
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }
        return vector;
    }
    public static OvertimeDetail fetchExc(long oid) throws DBException {
        try {
            OvertimeDetail overtimeDetail = new OvertimeDetail();
            PstOvertimeDetail pstOvertimeDetail = new PstOvertimeDetail(oid);
            overtimeDetail.setOID(oid);

            overtimeDetail.setOvertimeId(pstOvertimeDetail.getlong(FLD_OVERTIME_ID));
            overtimeDetail.setEmployeeId(pstOvertimeDetail.getLong(FLD_EMPLOYEE_ID));
            overtimeDetail.setJobDesk(pstOvertimeDetail.getString(FLD_JOBDESK));
            overtimeDetail.setDateFrom(pstOvertimeDetail.getDate(FLD_DATE_FROM));
            overtimeDetail.setDateTo(pstOvertimeDetail.getDate(FLD_DATE_TO));
            overtimeDetail.setRealDateFrom(pstOvertimeDetail.getDate(FLD_REAL_DATE_FROM));
            overtimeDetail.setRealDateTo(pstOvertimeDetail.getDate(FLD_REAL_DATE_TO));
            overtimeDetail.setStatus(pstOvertimeDetail.getInt(FLD_STATUS));
            overtimeDetail.setPaidBy(pstOvertimeDetail.getInt(FLD_PAID_BY));

            overtimeDetail.setDuration(pstOvertimeDetail.getdouble(FLD_OVT_DURATION));
            overtimeDetail.setEmployee_num(pstOvertimeDetail.getString(FLD_EMPLOYEE_NUM));
            overtimeDetail.setOvt_doc_nr(pstOvertimeDetail.getString(FLD_OVT_DOC_NR));
            overtimeDetail.setPay_slip_id(pstOvertimeDetail.getlong(FLD_PAY_SLIP_ID));
            overtimeDetail.setPeriodId(pstOvertimeDetail.getlong(FLD_PERIOD_ID));
            overtimeDetail.setStatus(pstOvertimeDetail.getInt(FLD_STATUS));
            overtimeDetail.setWork_schedule(pstOvertimeDetail.getString(FLD_STD_WORK_SCHDL));
            overtimeDetail.setOvt_code(pstOvertimeDetail.getString(FLD_OVT_CODE));
            overtimeDetail.setTot_Idx(pstOvertimeDetail.getdouble(FLD_TOT_IDX));
            overtimeDetail.setAllowance(pstOvertimeDetail.getInt(FLD_ALLOWANCE));
            overtimeDetail.setRestTimeinHr(pstOvertimeDetail.getdouble(FLD_REST_TIME_HR));
            overtimeDetail.setRestStart(pstOvertimeDetail.getDate(FLD_REST_TIME_START));
            
            overtimeDetail.setLocationId(pstOvertimeDetail.getInt(FLD_LOCATION_ID));
            return overtimeDetail;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvertimeDetail(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(OvertimeDetail overtimeDetail) throws DBException {
        try {            
            
            PstOvertimeDetail pstOvertimeDetail = new PstOvertimeDetail(0);
            pstOvertimeDetail.setLong(FLD_OVERTIME_ID, overtimeDetail.getOvertimeId());
            pstOvertimeDetail.setLong(FLD_EMPLOYEE_ID, overtimeDetail.getEmployeeId());
            pstOvertimeDetail.setString(FLD_JOBDESK, overtimeDetail.getJobDesk());
            pstOvertimeDetail.setDate(FLD_DATE_FROM, overtimeDetail.getDateFrom());
            pstOvertimeDetail.setDate(FLD_DATE_TO, overtimeDetail.getDateTo());
            pstOvertimeDetail.setDate(FLD_REAL_DATE_FROM, overtimeDetail.getRealDateFrom());
            pstOvertimeDetail.setDate(FLD_REAL_DATE_TO, overtimeDetail.getRealDateTo());
            pstOvertimeDetail.setInt(FLD_STATUS, overtimeDetail.getStatus());
            pstOvertimeDetail.setInt(FLD_PAID_BY, overtimeDetail.getPaidBy());
            try {
                long perId = PstPeriod.getPeriodIdBySelectedDate(overtimeDetail.getDateFrom());
                if (perId != 0) {
                    pstOvertimeDetail.setLong(FLD_PERIOD_ID, perId);
                } else {
                    pstOvertimeDetail.setLong(FLD_PERIOD_ID, overtimeDetail.getPeriodId());
                }
            } catch (Exception exc1) {
                System.out.println(exc1);
            }
            try {
                //String empNum = PstEmployee.getEmployeeName(overtimeDetail.getEmployeeId());
                String empNum = PstEmployee.getEmployeeNumber(overtimeDetail.getEmployeeId());
                if (empNum != null && empNum.length() > 0) {
                    pstOvertimeDetail.setString(FLD_EMPLOYEE_NUM, empNum);
                } else {
                    pstOvertimeDetail.setLong(FLD_PERIOD_ID, overtimeDetail.getPeriodId());
                }
            } catch (Exception exc1) {
                System.out.println(exc1);
            }
            overtimeDetail.setDuration(overtimeDetail.getNetDuration());
            pstOvertimeDetail.setDouble(FLD_OVT_DURATION, overtimeDetail.getDuration());

            pstOvertimeDetail.setString(FLD_OVT_DOC_NR, overtimeDetail.getOvt_doc_nr());
            pstOvertimeDetail.setLong(FLD_PAY_SLIP_ID, overtimeDetail.getPay_slip_id());
            pstOvertimeDetail.setString(FLD_STD_WORK_SCHDL, overtimeDetail.getWork_schedule());
            pstOvertimeDetail.setString(FLD_OVT_CODE, overtimeDetail.getOvt_code());
            pstOvertimeDetail.setDouble(FLD_TOT_IDX, overtimeDetail.getTot_Idx());
            pstOvertimeDetail.setInt(FLD_ALLOWANCE,overtimeDetail.getAllowance());
            pstOvertimeDetail.setDate(FLD_REST_TIME_START, overtimeDetail.getRestStart());            
            pstOvertimeDetail.setDouble(FLD_REST_TIME_HR, overtimeDetail.getRestTimeinHr());
            //update by satrya 2012-12-04
                if(overtimeDetail.getRealDateFrom()!=null && overtimeDetail.getRealDateTo()!=null){
                pstOvertimeDetail.setInt(FLD_FLAG_STATUS, overtimeDetail.getFlagStatus());
                }
            pstOvertimeDetail.setLong(FLD_LOCATION_ID, overtimeDetail.getLocationId());
            pstOvertimeDetail.insert();
            overtimeDetail.setOID(pstOvertimeDetail.getlong(FLD_OVERTIME_DETAIL_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvertimeDetail(0), DBException.UNKNOWN);
        }
        return overtimeDetail.getOID();
    }

    public synchronized  static long updateExc(OvertimeDetail overtimeDetail) throws DBException {
        try {
            if (overtimeDetail.getOID() != 0) {
                PstOvertimeDetail pstOvertimeDetail = new PstOvertimeDetail(overtimeDetail.getOID());
                pstOvertimeDetail.setLong(FLD_OVERTIME_ID, overtimeDetail.getOvertimeId());
                pstOvertimeDetail.setLong(FLD_EMPLOYEE_ID, overtimeDetail.getEmployeeId());
                pstOvertimeDetail.setString(FLD_JOBDESK, overtimeDetail.getJobDesk());
                pstOvertimeDetail.setDate(FLD_DATE_FROM, overtimeDetail.getDateFrom());
                pstOvertimeDetail.setDate(FLD_DATE_TO, overtimeDetail.getDateTo());
                pstOvertimeDetail.setDate(FLD_REAL_DATE_FROM, overtimeDetail.getRealDateFrom());
                pstOvertimeDetail.setDate(FLD_REAL_DATE_TO, overtimeDetail.getRealDateTo());
                pstOvertimeDetail.setInt(FLD_STATUS, overtimeDetail.getStatus());
                pstOvertimeDetail.setInt(FLD_PAID_BY, overtimeDetail.getPaidBy());
                try {
                    long perId = PstPeriod.getPeriodIdBySelectedDate(overtimeDetail.getDateFrom());
                    if (perId != 0) {
                        pstOvertimeDetail.setLong(FLD_PERIOD_ID, perId);
                    } else {
                        pstOvertimeDetail.setLong(FLD_PERIOD_ID, overtimeDetail.getPeriodId());
                    }
                } catch (Exception exc1) {
                    System.out.println(exc1);
                }
                try {
                    String empNum = PstEmployee.getEmployeeName(overtimeDetail.getEmployeeId());
                    if (empNum != null && empNum.length() > 0) {
                        pstOvertimeDetail.setString(FLD_EMPLOYEE_NUM, empNum);
                    } else {
                        pstOvertimeDetail.setLong(FLD_PERIOD_ID, overtimeDetail.getPeriodId());
                    }
                } catch (Exception exc1) {
                    System.out.println(exc1);
                }
                overtimeDetail.setDuration(overtimeDetail.getNetDuration());
                pstOvertimeDetail.setDouble(FLD_OVT_DURATION, overtimeDetail.getDuration());
                pstOvertimeDetail.setString(FLD_OVT_DOC_NR, overtimeDetail.getOvt_doc_nr());
                pstOvertimeDetail.setLong(FLD_PAY_SLIP_ID, overtimeDetail.getPay_slip_id());
                pstOvertimeDetail.setString(FLD_STD_WORK_SCHDL, overtimeDetail.getWork_schedule());
                pstOvertimeDetail.setString(FLD_OVT_CODE, overtimeDetail.getOvt_code());
                pstOvertimeDetail.setDouble(FLD_TOT_IDX, overtimeDetail.getTot_Idx());
                pstOvertimeDetail.setInt(FLD_ALLOWANCE,overtimeDetail.getAllowance());
                pstOvertimeDetail.setDate(FLD_REST_TIME_START, overtimeDetail.getRestStart());                            
                pstOvertimeDetail.setDouble(FLD_REST_TIME_HR, overtimeDetail.getRestTimeinHr());
                //update by satrya 2012-12-04
               // if(overtimeDetail.getRealDateFrom()!=null && overtimeDetail.getRealDateTo()!=null){
                pstOvertimeDetail.setInt(FLD_FLAG_STATUS, overtimeDetail.getFlagStatus());
                //}
                
                pstOvertimeDetail.setLong(FLD_LOCATION_ID, overtimeDetail.getLocationId());
                pstOvertimeDetail.update();
                return overtimeDetail.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvertimeDetail(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static void updateStatusByOvertimeID(long overtimeId, int newStatus, String where) {
        String sql = "UPDATE " + TBL_OVERTIME_DETAIL + " SET " + fieldNames[FLD_STATUS] + "=" + newStatus
                + " WHERE " + fieldNames[FLD_OVERTIME_ID]+"=\""+ overtimeId+"\""+ ((where != null && where.length() > 0) ? (" AND ( " + where+" ) ") : "");
        try {
            DBHandler.execUpdate(sql);
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }
    
    public static void updateAllowanceByOvertimeID(long overtimeId, int newAllowance, String where) {
        String sql = "UPDATE " + TBL_OVERTIME_DETAIL + " SET " + fieldNames[FLD_ALLOWANCE] + "=" + newAllowance
                + " WHERE " + fieldNames[FLD_OVERTIME_ID]+"=\""+ overtimeId+"\""+ ((where != null && where.length() > 0) ? (" AND (  " + where +") " ) : "");
        try {
            DBHandler.execUpdate(sql);
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }
    
    public static void updateRestTimeByOvertimeID(long overtimeId, Date restStart, float restHr, String where) {
        String sql = "";
        // hidden karena restHournya memakai tglbaru 2013-10-05 if(restHr>0.0f){
                sql="UPDATE " + TBL_OVERTIME_DETAIL + " SET " + fieldNames[FLD_REST_TIME_START] + "=\"" + Formater.formatDate(restStart, "yyyy-MM-dd HH:mm:ss" )
                + "\" , " + fieldNames[FLD_REST_TIME_HR] + "=" + restHr
                + " WHERE " + fieldNames[FLD_OVERTIME_ID]+"=\""+ overtimeId+"\""+ ((where != null && where.length() > 0) ? (" AND (  " + where +") " ) : "");
       /* } else {
                sql="UPDATE " + TBL_OVERTIME_DETAIL + " SET " + fieldNames[FLD_REST_TIME_START] + "=NULL"
                + " , " + fieldNames[FLD_REST_TIME_HR] + "=" + restHr
                + " WHERE " + fieldNames[FLD_OVERTIME_ID]+"=\""+ overtimeId+"\""+ ((where != null && where.length() > 0) ? (" AND (  " + where +") " ) : "");
        }*/
        try {
            DBHandler.execUpdate(sql);
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

    /**
     * Create by satrya 2013-01-31
     * @param overtimeDetailId
     * @param oVForm
     * @param allowance
     * @param paid
     * @param dNetOv
     * @param dOverIdx 
     */
    public static void updateOTDetail(long overtimeDetailId,int oVForm,int allowance,int paid,double dNetOv,double dOverIdx,int flagStatus) {
        String sql = "";
        if(overtimeDetailId!=0){
                sql="UPDATE " + TBL_OVERTIME_DETAIL + " AS HOD " 
                + " INNER JOIN "+ PstOvertime.TBL_OVERTIME
                + " AS HO ON(HOD."+fieldNames[FLD_OVERTIME_ID] + " =HO."+PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]+" ) "+ " SET " 
                + " HO."+PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC] + "=" + oVForm
                +  ", HOD." + fieldNames[FLD_ALLOWANCE] + "=" + allowance
                +  ", HOD." + fieldNames[FLD_PAID_BY] + "=" + paid
                +  ", HOD." + fieldNames[FLD_OVT_DURATION] + "=" + dNetOv
                +  ", HOD." + fieldNames[FLD_TOT_IDX] + "=" + dOverIdx        
                +  ", HOD." + fieldNames[FLD_FLAG_STATUS] + "=" + flagStatus 
                + " WHERE " + " HOD."+fieldNames[FLD_OVERTIME_DETAIL_ID]+"=\""+ overtimeDetailId+"\"";
        }
        try {
            DBHandler.execUpdate(sql);
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

    //update by satrya 2013-02-02
    /**
     * Keterangan: update OT idx
     * @param overtimeDetail 
     */
      public static void updateOTIdx(OvertimeDetail overtimeDetail) {
        String sql = "";
        if(overtimeDetail.getOID()!=0){
                sql="UPDATE " + TBL_OVERTIME_DETAIL + " AS HOD " 
                + " SET " 
                +  " HOD." + fieldNames[FLD_TOT_IDX] + "=" + overtimeDetail.getTotIdx()
                + " WHERE " + " HOD."+fieldNames[FLD_OVERTIME_DETAIL_ID]+"=\""+ overtimeDetail.getOID()+"\"";
        }
        try {
            DBHandler.execUpdate(sql);
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

        
    public static long deleteExc(long oid) throws DBException {
        try {
            PstOvertimeDetail pstOvertimeDetail = new PstOvertimeDetail(oid);
            pstOvertimeDetail.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvertimeDetail(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 1000, "", "");
    }

    /**
     * create by satrya 2014-05-26
     * keterangan: untuk mapping OT di outlet
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @param dtSTart
     * @param dtEnd
     * @return 
     */
    public static Vector listJointOvertimeMapping(int limitStart, int recordToGet, String whereClause,String order, Date dtSTart,Date dtEnd) {
        Vector lists = new Vector();
        if(dtSTart==null || dtEnd==null){
            return lists;
        }
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT es.*,esd.*,emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+",emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+",emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
+ " FROM "+ PstEmployee.TBL_HR_EMPLOYEE + " AS emp "
+ " LEFT JOIN "+ PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS esd ON  esd."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]+"=emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
+ " AND \""+Formater.formatDate(dtEnd, "yyyy-MM-dd 23:59") +"\" >= esd."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]+ " AND  esd."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]+ " >= \""+Formater.formatDate(dtSTart, "yyyy-MM-dd 00:00")+"\"" 
+ " LEFT JOIN "+ PstOvertime.TBL_OVERTIME + " AS es ON es."+PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID] + " = esd."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID];

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
                
                Overtime overtime = new Overtime();
                PstOvertime.resultToObject(rs, overtime); 
                listAll.add(overtime);
                
                OvertimeDetail overtimeDetail = new OvertimeDetail();
                resultToObject(rs, overtimeDetail);
                listAll.add(overtimeDetail);
                
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
    public static Vector list(int limitStart, int recordToGet, String whereClauseOv, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_OVERTIME_DETAIL;

            if (whereClauseOv != null && whereClauseOv.length() > 0) {
                sql = sql + " WHERE " + whereClauseOv;
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
                OvertimeDetail overtimeDetail = new OvertimeDetail();
                resultToObject(rs, overtimeDetail);
                lists.add(overtimeDetail);
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
     * Create by satrya 2014-01-24
     * Keterangan: untuk mencari list overtime detail 
     * @param fromDate
     * @param toDate
     * @param employee_id
     * @return 
     */
    public static Vector listOvertimeDetail(int limitStart, int recordToGet,Long oidDepartment, String fullName, Date selectedDateFrom, Date selectedDateTo, Long oidSection, String empNum, String order,Vector stStatus,long oidCompany,long oidDivision) {
        Vector lists = new Vector();
        
         String whereClauseEmpTime = " (1+1) AND ";
         //update by satrya 2012-10-15
      if(selectedDateFrom!=null && selectedDateTo!=null){
         if (selectedDateFrom.getTime() > selectedDateTo.getTime()) {
                Date tempFromDate = selectedDateFrom;
                Date tempToDate = selectedDateTo;
                selectedDateFrom = tempToDate;
                selectedDateTo = tempFromDate;
            }
         String whereClauseReq = " odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " BETWEEN "
                              + "\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd")+ " 00:00:00"+"\"" 
                             + " AND " + "\"" + Formater.formatDate(selectedDateTo, " yyyy-MM-dd ") + "23:59:59"+"\"";

         if((selectedDateFrom != null) && (selectedDateTo != null)) {
            whereClauseEmpTime = whereClauseReq;
        }
         if(oidDepartment !=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ "emp."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = " + oidDepartment;
        }
         if(oidSection !=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ "emp."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+" = " + oidSection;
        }
        if(fullName !=null && fullName.length() > 0){
//            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                                 + " LIKE '%"+fullName.trim()+"%'";
            Vector vectName = logicParser(fullName);
              whereClauseEmpTime = whereClauseEmpTime + " AND ";
                if (vectName != null && vectName.size() > 0) {
                    //whereClause = whereClause + " AND (";
                  
                    whereClauseEmpTime = whereClauseEmpTime + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClauseEmpTime = whereClauseEmpTime + " emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClauseEmpTime = whereClauseEmpTime + str.trim();
        }
                    }
                    whereClauseEmpTime = whereClauseEmpTime + ")";
                }
        }
      /* if(oidSection !=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ ;
        }*/
        if(empNum !=null && empNum.length() > 0){
//            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                                 + " LIKE '%"+empNum.trim()+"%'";
            Vector vectName = logicParser(empNum);
              whereClauseEmpTime = whereClauseEmpTime + " AND ";
                if (vectName != null && vectName.size() > 0) {
                    //whereClause = whereClause + " AND (";
                  
                    whereClauseEmpTime = whereClauseEmpTime + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClauseEmpTime = whereClauseEmpTime + " emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClauseEmpTime = whereClauseEmpTime + str.trim();
        }
                    }
                    whereClauseEmpTime = whereClauseEmpTime + ")";
                }
        }
        if( stStatus!=null && stStatus.size()>0){
                 //whereClauseEmpTime  = whereClauseEmpTime + PstPresence.fieldNames[PstPresence.FLD_STATUS] + " IN ("+ getEmployeePresence +")" ;
            String getEmployeePresence = PstPresence.getEmployee(0 , 0, "", oidDepartment, fullName.trim(),  
                        selectedDateFrom, selectedDateTo, oidSection, empNum.trim(),stStatus); 
               
                if(getEmployeePresence.length()<=0){
                   getEmployeePresence = ""+0;
               }
                whereClauseEmpTime = whereClauseEmpTime + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN ("+ getEmployeePresence +")";
        }
        
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT odt.* FROM "+TBL_OVERTIME_DETAIL + " AS odt "
            + " INNER JOIN " + PstOvertime.TBL_OVERTIME + " AS ov ON odt."+fieldNames[FLD_OVERTIME_ID]+"=ov."+PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]
            + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]+"=emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
                    
            sql = sql + " WHERE " + whereClauseEmpTime + " AND "+PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC] + "!="+I_DocStatus.DOCUMENT_STATUS_CANCELLED;

            //System.out.println("sql::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                OvertimeDetail overtimeDetail = new OvertimeDetail();
                resultToObject(rs, overtimeDetail);
                lists.add(overtimeDetail);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
      }
        return new Vector();
    }
    
    
     


    /**
     * Create by satrya 2014-01-24
     * keterangan: untuk mencari list overtime
     * @param fromDate
     * @param toDate
     * @param employee_id
     * @return 
     */
    public static Vector listOvertimeDetail(Date fromDate, Date toDate,String employee_id) {
        Vector lists = new Vector();
        String where=" (1=1) AND ";
        if(fromDate!=null && toDate!=null){
            where =  "\""+Formater.formatDate(toDate, "yyyy-MM-dd 23:59:59")+"\" > " 
                + fieldNames[FLD_DATE_FROM]+ " AND "
                + fieldNames[FLD_DATE_TO] + " > \""+Formater.formatDate(fromDate, "yyyy-MM-dd 00:00:00")+"\"";
            // update by satrya 2014-04-02 + fieldNames[FLD_DATE_FROM] + " > \""+Formater.formatDate(fromDate, "yyyy-MM-dd 00:00:00")+"\"";
        }
        if(employee_id!=null && employee_id.length()>0){
            if(where!=null && where.length()>0){
                where = where + " AND " +fieldNames[FLD_EMPLOYEE_ID] + " IN ( " + employee_id + ")";
            }else{
                where = where + fieldNames[FLD_EMPLOYEE_ID] + " IN ( " + employee_id + ")";
            }
            
        }
        if(where==null || where.length()==0){
            return new Vector();
        }
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT otd.* FROM "+TBL_OVERTIME_DETAIL + " AS otd "
            + " INNER JOIN " + PstOvertime.TBL_OVERTIME + " AS ov ON otd."+fieldNames[FLD_OVERTIME_ID]+"=ov."+PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID];
                    
            sql = sql + " WHERE " + where  + " AND "+ PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC] + "!="+I_DocStatus.DOCUMENT_STATUS_CANCELLED;

            //System.out.println("sql::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                OvertimeDetail overtimeDetail = new OvertimeDetail();
                resultToObject(rs, overtimeDetail);
                lists.add(overtimeDetail);
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
    public static Vector listWithEmployee(int limitStart, int recordToGet, String whereClauseOv, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DT.*," + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + "," + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + "," + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " FROM " + TBL_OVERTIME_DETAIL
                    + " AS DT INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE
                    + " AS EM ON EM." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=DT."
                    + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID];


            if (whereClauseOv != null && whereClauseOv.length() > 0) {
                sql = sql + " WHERE " + whereClauseOv;
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
                OvertimeDetail overtimeDetail = new OvertimeDetail();
                resultToObjectWithEmployee(rs, overtimeDetail);
                lists.add(overtimeDetail);
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
     * 
     * @param employeeId : oid of employee
     * @param startDate  : date start period
     * @param endDate    : date end period
     * @mealAllowanceBy  : mealAlloawance provided by : meal or money
     * @param paidBy     : paid by type 
     * @param status     : status of overtime detail ( include logical operation e.g.    sratus="= 1"  , status in (1,2)  
     * @return  number of overtime detail according to filter parameters
     */
    public static int countOvertimeDetail(long employeeId, Date startDate, Date endDate, int mealAllowanceBy, int paidBy, String status) {
        DBResultSet dbrs = null;
        try {

            String sql = "SELECT COUNT(DT.," + fieldNames[FLD_EMPLOYEE_ID]
                    + ")  FROM " + TBL_OVERTIME_DETAIL
                    + " AS DT INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE
                    + " AS EM ON EM." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=DT."
                    + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstOvertime.TBL_OVERTIME + " AS OV ON OV." + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID] + "="
                    + fieldNames[FLD_OVERTIME_ID]
                    + " WHERE " + " DT." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]
                    + "=" + employeeId + " AND (" + fieldNames[FLD_DATE_FROM] + " BETWEEN \""
                    + Formater.formatDate(startDate, "yyyy-mm-dd") + "00:00:00\" AND "
                    + Formater.formatDate(endDate, "yyyy-mm-dd") + "23:59:59\" ) "
                    + (paidBy > -1 ? (" DT." + fieldNames[FLD_PAID_BY] + "=") : "(1=1)")
                    + (status != null && status.length() > 0 ? (" AND DT." + fieldNames[FLD_STATUS] + " " + status) : "")
                    + (mealAllowanceBy > -1 ? (" AND OV." + PstOvertime.fieldNames[PstOvertime.FLD_ALLOWANCE] + "=" + mealAllowanceBy) : "");

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int qty = 0;
            while (rs.next()) {
                qty = rs.getInt(1);
            }
            rs.close();
            return qty;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    //update by devin 2014-02-21
    public static Vector getOvertime(Date dateFrom ,Date dateTo,int status,long employeeId) 
    {
        Vector result = new Vector();
        DBResultSet dbrs = null;
        try{
            String sql = "SELECT * FROM " + TBL_OVERTIME_DETAIL + " WHERE \" " + Formater.formatDate(dateFrom , "yyyy-MM-dd") + " 00:00:00\" <= " + fieldNames[FLD_DATE_TO] + " AND  " + fieldNames[FLD_DATE_FROM] + " <=  \""
                          + Formater.formatDate(dateTo, "yyyy-MM-dd") + " 23:59:59\" AND  " + fieldNames[FLD_STATUS] + " = " + 4; 
            if(employeeId >0){
                sql = sql + " AND " +  fieldNames[FLD_EMPLOYEE_ID] + "= " +employeeId;
            }
                  dbrs = DBHandler.execQueryResult(sql);
                  ResultSet rs = dbrs.getResultSet();
                  while(rs.next()){
                      OvertimeDetail ov = new OvertimeDetail();
                      ov.setDateFrom(DBHandler.convertDate(rs.getDate(fieldNames[FLD_DATE_FROM]), rs.getTime(fieldNames[FLD_DATE_FROM]))); 
                      ov.setDateTo(DBHandler.convertDate (rs.getDate(fieldNames[FLD_DATE_TO]),rs.getTime(fieldNames[FLD_DATE_TO])));
                      result.add(ov);
                  }
        }catch(Exception exc){
            
        }finally{
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * search employee list with maximum level in employee detail 
     * @author : Kartika T.
     * @param limitStart
     * @param recordToGet
     * @param whereClauseOv
     * @param order
     * @return 
     */
    public static Vector maxLevelEmployees(long overtimeId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DT.*," + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ",EM." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ",EM." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ",POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + ",POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]
                    + ",POS." + PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_DEPT_SCOPE]
                    + ",POS." + PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_DIV_SCOPE]
                    + " FROM " + TBL_OVERTIME_DETAIL
                    + " AS DT INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE
                    + " AS EM ON EM." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=DT."
                    + fieldNames[FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS "
                    + " ON POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + "="
                    + " EM." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " WHERE DT." + fieldNames[FLD_OVERTIME_ID] + "=" + overtimeId + " AND "
                    + " POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + "="
                    + "(" + "SELECT MAX(POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + ")"
                    + " FROM " + TBL_OVERTIME_DETAIL + " AS DT INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE
                    + " AS EM ON EM." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=DT."
                    + fieldNames[FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS "
                    + " ON POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + "="
                    + " EM." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " WHERE DT." + fieldNames[FLD_OVERTIME_ID] + "=" + overtimeId
                    + " GROUP BY DT." + fieldNames[FLD_OVERTIME_ID] + ")";

            /*            
            SELECT dt.*, pos.POSITION_LEVEL, pos.DISABLED_APP_DEPT_SCOPE, 
             * pos.DISABLED_APP_DIV_SCOPE FROM hr_overtime_detail dt
            INNER JOIN hr_employee em ON em.employee_id= dt.EMPLOYEE_ID
            INNER JOIN hr_position pos ON em.POSITION_ID = pos.POSITION_ID 
            WHERE overtime_id=504404484964058176 AND pos.position_level=
            (SELECT MAX(pos.position_level)FROM hr_overtime_detail dt
            INNER JOIN hr_employee em ON em.employee_id= dt.EMPLOYEE_ID
            INNER JOIN hr_position pos ON em.POSITION_ID = pos.POSITION_ID 
            WHERE overtime_id=504404484964058176 GROUP BY overtime_id );          */


            //System.out.println("sql::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                OvertimeDetail overtimeDetail = new OvertimeDetail();
                resultToObjectWithEmployee(rs, overtimeDetail);
                overtimeDetail.setPositionId(rs.getLong(PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]));
                overtimeDetail.setPositionLevel(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]));
                overtimeDetail.setPositionDisableDepartmentApproval(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_DEPT_SCOPE]));
                overtimeDetail.setPositionDisableDivisionApproval(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_DIV_SCOPE]));
                lists.add(overtimeDetail);
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

    public static void resultToObjectWithEmployee(ResultSet rs, OvertimeDetail overtimeDetail) {
        try {
            overtimeDetail.setOID(rs.getLong(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_DETAIL_ID]));
            overtimeDetail.setOvertimeId(rs.getLong(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID]));
            overtimeDetail.setEmployeeId(rs.getLong(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]));
            overtimeDetail.setPayroll(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
            overtimeDetail.setName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
            overtimeDetail.setEmpDeptId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
            overtimeDetail.setJobDesk(rs.getString(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_JOBDESK]));
            Date tm_start = DBHandler.convertDate(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]), rs.getTime(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]));
            overtimeDetail.setDateFrom(tm_start);
            Date tm_end = DBHandler.convertDate(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]), rs.getTime(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]));
            overtimeDetail.setDateTo(tm_end);

            Date tm_real_start = DBHandler.convertDate(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM]), rs.getTime(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM]));
            overtimeDetail.setRealDateFrom(tm_real_start);
            Date tm_real_end = DBHandler.convertDate(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO]), rs.getTime(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO]));
            overtimeDetail.setRealDateTo(tm_real_end);

            overtimeDetail.setStatus(rs.getInt(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]));
            overtimeDetail.setPaidBy(rs.getInt(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]));

            overtimeDetail.setDuration(rs.getDouble(fieldNames[FLD_OVT_DURATION]));
            overtimeDetail.setEmployee_num(rs.getString(fieldNames[FLD_EMPLOYEE_NUM]));
            overtimeDetail.setOvt_doc_nr(rs.getString(fieldNames[FLD_OVT_DOC_NR]));
            overtimeDetail.setPay_slip_id(rs.getLong(fieldNames[FLD_PAY_SLIP_ID]));
            overtimeDetail.setPeriodId(rs.getLong(fieldNames[FLD_PERIOD_ID]));
            overtimeDetail.setWork_schedule(rs.getString(fieldNames[FLD_STD_WORK_SCHDL]));
            overtimeDetail.setOvt_code(rs.getString(fieldNames[FLD_OVT_CODE]));
            overtimeDetail.setTot_Idx(rs.getDouble(fieldNames[FLD_TOT_IDX]));
            overtimeDetail.setAllowance(rs.getInt(fieldNames[FLD_ALLOWANCE]));
            Date tm_rest_start = DBHandler.convertDate(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_START]), rs.getTime(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_START]));                        
            overtimeDetail.setRestStart(tm_rest_start);            
            overtimeDetail.setRestTimeinHr(rs.getDouble(fieldNames[FLD_REST_TIME_HR]));

        } catch (Exception e) {
            System.out.println("Exception"+e);
        }
    }

    public static void resultToObject(ResultSet rs, OvertimeDetail overtimeDetail) {
        try {
            overtimeDetail.setOID(rs.getLong(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_DETAIL_ID]));
            overtimeDetail.setOvertimeId(rs.getLong(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID]));
            overtimeDetail.setEmployeeId(rs.getLong(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]));
            overtimeDetail.setJobDesk(rs.getString(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_JOBDESK]));
            //overtimeDetail.setDateFrom(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]));
            //overtimeDetail.setDateTo(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]));
            Date tm_start = DBHandler.convertDate(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]), rs.getTime(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]));
            overtimeDetail.setDateFrom(tm_start);
            Date tm_end = DBHandler.convertDate(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]), rs.getTime(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]));
            overtimeDetail.setDateTo(tm_end);

            Date tm_real_start = DBHandler.convertDate(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM]), rs.getTime(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM]));
            overtimeDetail.setRealDateFrom(tm_real_start);
            Date tm_real_end = DBHandler.convertDate(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO]), rs.getTime(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO]));
            overtimeDetail.setRealDateTo(tm_real_end);

            overtimeDetail.setStatus(rs.getInt(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]));
            overtimeDetail.setPaidBy(rs.getInt(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]));

            overtimeDetail.setDuration(rs.getDouble(fieldNames[FLD_OVT_DURATION]));
            overtimeDetail.setEmployee_num(rs.getString(fieldNames[FLD_EMPLOYEE_NUM]));
            overtimeDetail.setOvt_doc_nr(rs.getString(fieldNames[FLD_OVT_DOC_NR]));
            overtimeDetail.setPay_slip_id(rs.getLong(fieldNames[FLD_PAY_SLIP_ID]));
            overtimeDetail.setPeriodId(rs.getLong(fieldNames[FLD_PERIOD_ID]));
            overtimeDetail.setWork_schedule(rs.getString(fieldNames[FLD_STD_WORK_SCHDL]));
            overtimeDetail.setOvt_code(rs.getString(fieldNames[FLD_OVT_CODE]));
            overtimeDetail.setTot_Idx(rs.getDouble(fieldNames[FLD_TOT_IDX]));
            overtimeDetail.setAllowance(rs.getInt(fieldNames[FLD_ALLOWANCE]));
            Date tm_rest_start = DBHandler.convertDate(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_START]), rs.getTime(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_START]));                        
            overtimeDetail.setRestStart(tm_rest_start);            
            overtimeDetail.setRestTimeinHr(rs.getDouble(fieldNames[FLD_REST_TIME_HR]));
            //update by satrya 2012-12-04
            overtimeDetail.setFlagStatus(rs.getInt((fieldNames[FLD_FLAG_STATUS])));

        } catch (Exception e) {
            System.out.println("Exception"+e);
        }
    }

    public static boolean checkOID(long overtimeDetailId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_OVERTIME_DETAIL + " WHERE "
                    + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_DETAIL_ID] + " = '" + overtimeDetailId + "'";

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
/**
 * Untuk mengecek apakah sudah ada OT detailnya
 * ini di pakai untuk request approvall
 * create by satrya 2013-06-11
 * @param overtimeID
 * @return 
 */
  public static boolean checkOIDDetailByOvertrimeId(long overtimeID) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_OVERTIME_DETAIL + " WHERE "
                    + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID] + " = '" + overtimeID + "' LIMIT 0,1 ";

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

    public static int getCount(String whereClauseOv) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_DETAIL_ID] + ") FROM " + TBL_OVERTIME_DETAIL;
            if (whereClauseOv != null && whereClauseOv.length() > 0) {
                sql = sql + " WHERE " + whereClauseOv;
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

    public static int findLimitStart(long oid, int recordToGet, String whereClauseOv, String orderClause) {
        String order = "";
        int size = getCount(whereClauseOv);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClauseOv, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    OvertimeDetail overtimeDetail = (OvertimeDetail) list.get(ls);
                    if (oid == overtimeDetail.getOID()) {
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

    //Gede_2April2012
    //{
    public static Vector list3(int limitStart, int recordToGet, String whereClauseEmpTime, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        String test="";
        try {
            String sql = "SELECT odt.*,o."+PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]
                    +" FROM " + PstOvertimeDetail.TBL_OVERTIME_DETAIL
                    + " as odt INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " as emp ON odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + "= emp."
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " as d ON emp."
                    + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "= d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " INNER JOIN " + PstCompany.TBL_HR_COMPANY + " as c ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + "= c." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " as dep ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "= dep." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " as r ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID] + "= r." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstOvertime.TBL_OVERTIME + " as o ON odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID] + "= o." + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID] +
                    //update by satrya 2013-08-13
                    //penambahan section
                    " LEFT JOIN " + PstSection.TBL_HR_SECTION + " as sec ON sec." + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "= dep." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
            if (whereClauseEmpTime != null && whereClauseEmpTime.length() > 0) {
                sql = sql + " WHERE " + whereClauseEmpTime;
            }
            sql = sql + " GROUP BY " + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_DETAIL_ID];
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }


            //System.out.println("sql overtime detail " + sql);
           // System.out.println("sql overtime detail " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            test = sql;
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                OvertimeDetail overtimeDetail = new OvertimeDetail();
                resultToObject(rs, overtimeDetail);
                overtimeDetail.setOvt_doc_nr(rs.getString("o."+PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]));
                lists.add(overtimeDetail);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(""+test);
            System.out.println("Exception PstOvertimeDetail.list3"+e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    
    //create by satrya 2012-12-27c
    /**
     * 
     * @param departmentId
     * @param fromDate
     * @param toDate
     * @param sectionId
     * @param empNumb
     * @param fullName
     * @param paidBy
     * @param allowance
     * @return 
     */
    public static Hashtable summaryOT(long oidCompany,long oidDivision,long departmentId, Date fromDate, Date toDate, 
                long sectionId, String empNumb, String fullName, int paidBySalary,int paidByDayOff
                    , int allowanceFood, int allowanceMoney, int durTotIdxDur) {
        Hashtable lists = new Hashtable();
        DBResultSet dbrs = null;
        try {
            /**
             * String sql = " select distinct SUM("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVT_DURATION]+") AS DURATION , "
                         + " SUM("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_TOT_IDX]+") AS TOT_IDX , "
                         + " COUNT("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]+") AS ALLOWANCE, "
                         + " COUNT("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+") AS PAID_BY, "
                         + " EMP."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + " AS EMPLOYEE_ID "
                         + " FROM "+ PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS ODT INNER JOIN "
                         + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP on ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] 
                         + " = EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                         + " WHERE  ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM] + " IS NOT NULL AND "
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO]  +" IS NOT NULL ";
             */
            String sql = /*" select  "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVT_DURATION]+" AS DURATION , "*/
                         " select  "
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM] + " AS REAL_DATE_FROM ,"
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO] + " AS REAL_DATE_TO ,"
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_HR] + " AS  REST_TIME_HR ,"
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " AS DATE_FROM ,"
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + " AS DATE_TO ,"
                         //+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVT_DURATION]+" AS DURATION , "
                         + " "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_TOT_IDX]+" AS TOT_IDX , "
                         //update by satrya 2014-04-02
                         //+ " "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]+" AS ALLOWANCE, "
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]+" AS ALLOWANCE, "
                         + " "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+" AS PAID_BY, "
                         + " EMP."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + " AS EMPLOYEE_ID "
                         + " FROM "+ PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS ODT INNER JOIN "
                         + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP on ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] 
                         + " = EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                         //update by satrya 2014-03-21
                         + " INNER JOIN "+PstOvertime.TBL_OVERTIME+ " AS OT ON OT."+PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]+"=ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID]
                        //end update 
                        + " WHERE  ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM] + " IS NOT NULL AND "
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO]  +" IS NOT NULL AND"
                    //update by satrya 2014-03-21    
                    +" OT."+PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]+" NOT IN("+I_DocStatus.DOCUMENT_STATUS_CANCELLED+","+I_DocStatus.DOCUMENT_STATUS_DRAFT+")";
             
            if(paidBySalary==OvertimeDetail.PAID_BY_SALARY){
                        sql+= " AND ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+"="+OvertimeDetail.PAID_BY_SALARY;
             }else if(paidByDayOff==OvertimeDetail.PAID_BY_DAY_OFF){
                  sql+= " AND ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+"="+OvertimeDetail.PAID_BY_DAY_OFF;
             }else if(allowanceFood == Overtime.ALLOWANCE_FOOD){
                  sql+= " AND ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]+"="+Overtime.ALLOWANCE_FOOD;
             }else if(allowanceMoney == Overtime.ALLOWANCE_MONEY){
                  sql+= " AND ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]+"="+Overtime.ALLOWANCE_MONEY;
             }                   
            
             if(fromDate!=null && toDate!=null){
                    sql= sql + "  AND (( EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " +PstEmployee.YES_RESIGN + " AND " + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + " BETWEEN \"" + Formater.formatDate(fromDate, "yyyy-MM-dd  00:00:00")+ "\""  +" AND "+ "\"" + Formater.formatDate(toDate, "yyyy-MM-dd  23:59:59")+ "\"" 
                    + " ) OR (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN + "))"
                    + "AND ("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " BETWEEN \""+Formater.formatDate(fromDate, "yyyy-MM-dd  00:00:00")+"\""+" AND "+"\""+Formater.formatDate(toDate, "yyyy-MM-dd  23:59:59")+ "\""
                    + " AND "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] +" BETWEEN \""+Formater.formatDate(fromDate, "yyyy-MM-dd  00:00:00")+"\""+" AND "+"\""+Formater.formatDate(toDate, "yyyy-MM-dd  23:59:59")+ "\")";
                    
                }   
                    if(departmentId != 0){
                        sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + departmentId;
                    }
                    //update by satrya 2014-01-20
                    if(oidCompany != 0){
                        sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = " + oidCompany;
                    }
                    
                    if(oidDivision != 0){
                        sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = " + oidDivision;
                    }

                    if(sectionId != 0){
                        sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + sectionId;
                    }
            if (empNumb != null && empNumb != "") {
                Vector vectNum = logicParser(empNumb);
                sql = sql + " AND ";
                if (vectNum != null && vectNum.size() > 0) {
                    sql = sql + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i); 
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + ")";
                }
                
            }
            if (fullName != null && fullName != "") {
                Vector vectFullName = logicParser(fullName);
                  sql = sql + " AND ";
                if (vectFullName != null && vectFullName.size() > 0) {
                    sql = sql + " ( ";
                    for (int i = 0; i < vectFullName.size(); i++) {
                        String str = (String) vectFullName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql +" EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }
                 
            }

           // sql += " GROUP BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+",ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM]+ " ASC "; 
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            TableHitungOvertimeDetail tableHitungOvertimeDetail = new TableHitungOvertimeDetail();
            long tmpEmployeeId=0;
            while (rs.next()) {
                //OvertimeDetail overtimeDetail = new OvertimeDetail();
//                
//             if(paidBySalary==OvertimeDetail.PAID_BY_SALARY){
//                  overtimeDetail.setTotPaidBySalary(rs.getInt("PAID_BY"));
//                   overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//             }else if(paidByDayOff==OvertimeDetail.PAID_BY_DAY_OFF){
//                  overtimeDetail.setTotPaidBydayOff(rs.getInt("PAID_BY"));
//                   overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//             }else if(allowanceFood == Overtime.ALLOWANCE_FOOD){
//                  overtimeDetail.setTotAllowanceFood(rs.getInt("ALLOWANCE"));
//                   overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//             }else if(allowanceMoney == Overtime.ALLOWANCE_MONEY){
//                   overtimeDetail.setTotAllowanceMoney(rs.getInt("ALLOWANCE"));
//                    overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//             }else if(durTotIdxDur==1){
//                overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//                overtimeDetail.setTotIdx(rs.getDouble("TOT_IDX"));
//                
//             }
          if(tmpEmployeeId !=rs.getLong("EMPLOYEE_ID")){
              tmpEmployeeId = rs.getLong("EMPLOYEE_ID");
              tableHitungOvertimeDetail = new TableHitungOvertimeDetail();
              if(rs.getInt("ALLOWANCE")==Overtime.ALLOWANCE_FOOD){
                   //update by satrya 2014-02-11 karena jika allowance food maka nilainya 0 tableHitungOvertimeDetail.addvAllowanceFood(rs.getInt("ALLOWANCE"));
                tableHitungOvertimeDetail.addvAllowanceFood(1);
              }else{
                tableHitungOvertimeDetail.addvAllowanceMoney(rs.getInt("ALLOWANCE"));
              }
              
              if(rs.getInt("PAID_BY")==OvertimeDetail.PAID_BY_SALARY){
                  //update by satrya 2014-02-11 karena jika paid by salary maka nilainya 0 tableHitungOvertimeDetail.addvPaidSalary(rs.getInt("PAID_BY"));
                tableHitungOvertimeDetail.addvPaidSalary(1);
              }else{
                tableHitungOvertimeDetail.addvPaidDp(rs.getInt("PAID_BY"));
              }
              // update by satrya 2014-01-25 tableHitungOvertimeDetail.addvDuration(rs.getDouble("DURATION"));
              tableHitungOvertimeDetail.addvDuration(DBHandler.convertDate(rs.getDate("REAL_DATE_FROM"), rs.getTime("REAL_DATE_FROM")),DBHandler.convertDate(rs.getDate("REAL_DATE_TO"), rs.getTime("REAL_DATE_TO")),DBHandler.convertDate(rs.getDate("DATE_FROM"), rs.getTime("DATE_FROM")),DBHandler.convertDate(rs.getDate("DATE_TO"), rs.getTime("DATE_TO")),rs.getDouble("REST_TIME_HR"));
              tableHitungOvertimeDetail.addvTotIdx(rs.getDouble("TOT_IDX"));
              tableHitungOvertimeDetail.setEmployee_id(rs.getLong("EMPLOYEE_ID"));
          }
          else{
              if(rs.getInt("ALLOWANCE")==Overtime.ALLOWANCE_FOOD){
                  //update by satrya 2014-02-11 karena jika allowance food maka nilainya 0 tableHitungOvertimeDetail.addvAllowanceFood(rs.getInt("ALLOWANCE"));
                tableHitungOvertimeDetail.addvAllowanceFood(1);
              }else{
                tableHitungOvertimeDetail.addvAllowanceMoney(rs.getInt("ALLOWANCE"));
              }
              
              if(rs.getInt("PAID_BY")==OvertimeDetail.PAID_BY_SALARY){
                  //update by satrya 2014-02-11 karena jika paid by salary maka nilainya 0 tableHitungOvertimeDetail.addvPaidSalary(rs.getInt("PAID_BY"));
                tableHitungOvertimeDetail.addvPaidSalary(1);
              }else{
                tableHitungOvertimeDetail.addvPaidDp(rs.getInt("PAID_BY"));
              }
              //update by satrya 2014-01-25 tableHitungOvertimeDetail.addvDuration(rs.getDouble("DURATION"));
              tableHitungOvertimeDetail.addvDuration(DBHandler.convertDate(rs.getDate("REAL_DATE_FROM"), rs.getTime("REAL_DATE_FROM")),DBHandler.convertDate(rs.getDate("REAL_DATE_TO"), rs.getTime("REAL_DATE_TO")),DBHandler.convertDate(rs.getDate("DATE_FROM"), rs.getTime("DATE_FROM")),DBHandler.convertDate(rs.getDate("DATE_TO"), rs.getTime("DATE_TO")),rs.getDouble("REST_TIME_HR"));
              tableHitungOvertimeDetail.addvTotIdx(rs.getDouble("TOT_IDX"));
              tableHitungOvertimeDetail.setEmployee_id(rs.getLong("EMPLOYEE_ID"));
          }
                //lists.add(overtimeDetail);
                lists.put(tableHitungOvertimeDetail.getEmployee_id(), tableHitungOvertimeDetail);
            }
            rs.close();
            return lists;

        } catch (Exception e) {

            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }
    
    /**
     * untuk pay INput
     * create by satrya 2014-02-06
     * @param fromDate
     * @param toDate
     * @param employeeId
     * @param paidBySalary
     * @param paidByDayOff
     * @param allowanceFood
     * @param allowanceMoney
     * @param durTotIdxDur
     * @return 
     */
     public static Hashtable summaryOTOffPayInput(Date fromDate, Date toDate, 
                String employeeId,int paidBySalary,int paidByDayOff
                    , int allowanceFood, int allowanceMoney, int durTotIdxDur) {
        Hashtable lists = new Hashtable();
        if(employeeId==null || employeeId.length()==0){
            return new Hashtable();
        }
        DBResultSet dbrs = null;
        try {
            /**
             * String sql = " select distinct SUM("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVT_DURATION]+") AS DURATION , "
                         + " SUM("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_TOT_IDX]+") AS TOT_IDX , "
                         + " COUNT("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]+") AS ALLOWANCE, "
                         + " COUNT("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+") AS PAID_BY, "
                         + " EMP."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + " AS EMPLOYEE_ID "
                         + " FROM "+ PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS ODT INNER JOIN "
                         + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP on ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] 
                         + " = EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                         + " WHERE  ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM] + " IS NOT NULL AND "
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO]  +" IS NOT NULL ";
             */
            String sql = /*" select  "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVT_DURATION]+" AS DURATION , "*/
                         " select  "
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM] + " AS REAL_DATE_FROM ,"
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO] + " AS REAL_DATE_TO ,"
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_HR] + " AS  REST_TIME_HR ,"
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " AS DATE_FROM ,"
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + " AS DATE_TO ,"
                         //+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVT_DURATION]+" AS DURATION , "
                         + " "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_TOT_IDX]+" AS TOT_IDX , "
                         + " "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]+" AS ALLOWANCE, "
                         + " "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+" AS PAID_BY, "
                         + " EMP."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + " AS EMPLOYEE_ID "
                         + " FROM "+ PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS ODT INNER JOIN "
                         + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP on ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] 
                         + " = EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                         + " WHERE  ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM] + " IS NOT NULL AND "
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO]  +" IS NOT NULL ";
             
            if(paidBySalary==OvertimeDetail.PAID_BY_SALARY){
                        sql+= " AND "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+"="+OvertimeDetail.PAID_BY_SALARY;
             }else if(paidByDayOff==OvertimeDetail.PAID_BY_DAY_OFF){
                  sql+= " AND "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+"="+OvertimeDetail.PAID_BY_DAY_OFF;
             }else if(allowanceFood == Overtime.ALLOWANCE_FOOD){
                  sql+= " AND "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]+"="+Overtime.ALLOWANCE_FOOD;
             }else if(allowanceMoney == Overtime.ALLOWANCE_MONEY){
                  sql+= " AND "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]+"="+Overtime.ALLOWANCE_MONEY;
             }                   
            
             if(fromDate!=null && toDate!=null){
                    sql= sql + "  AND (( EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " +PstEmployee.YES_RESIGN + " AND " + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + " BETWEEN \"" + Formater.formatDate(fromDate, "yyyy-MM-dd  00:00:00")+ "\""  +" AND "+ "\"" + Formater.formatDate(toDate, "yyyy-MM-dd  23:59:59")+ "\"" 
                    + " ) OR (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN + "))"
                    + "AND ("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " BETWEEN \""+Formater.formatDate(fromDate, "yyyy-MM-dd  00:00:00")+"\""+" AND "+"\""+Formater.formatDate(toDate, "yyyy-MM-dd  23:59:59")+ "\""
                    + " AND "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] +" BETWEEN \""+Formater.formatDate(fromDate, "yyyy-MM-dd  00:00:00")+"\""+" AND "+"\""+Formater.formatDate(toDate, "yyyy-MM-dd  23:59:59")+ "\")";
                    
                }   
                 if(employeeId!=null && employeeId.length()>0){
                        sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN ("+ employeeId+")";
                    }
               

           // sql += " GROUP BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+",ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM]+ " ASC "; 
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            TableHitungOvertimeDetail tableHitungOvertimeDetail = new TableHitungOvertimeDetail();
            long tmpEmployeeId=0;
            while (rs.next()) {
                //OvertimeDetail overtimeDetail = new OvertimeDetail();
//                
//             if(paidBySalary==OvertimeDetail.PAID_BY_SALARY){
//                  overtimeDetail.setTotPaidBySalary(rs.getInt("PAID_BY"));
//                   overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//             }else if(paidByDayOff==OvertimeDetail.PAID_BY_DAY_OFF){
//                  overtimeDetail.setTotPaidBydayOff(rs.getInt("PAID_BY"));
//                   overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//             }else if(allowanceFood == Overtime.ALLOWANCE_FOOD){
//                  overtimeDetail.setTotAllowanceFood(rs.getInt("ALLOWANCE"));
//                   overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//             }else if(allowanceMoney == Overtime.ALLOWANCE_MONEY){
//                   overtimeDetail.setTotAllowanceMoney(rs.getInt("ALLOWANCE"));
//                    overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//             }else if(durTotIdxDur==1){
//                overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//                overtimeDetail.setTotIdx(rs.getDouble("TOT_IDX"));
//                
//             }
          if(tmpEmployeeId !=rs.getLong("EMPLOYEE_ID")){
              tmpEmployeeId = rs.getLong("EMPLOYEE_ID");
              tableHitungOvertimeDetail = new TableHitungOvertimeDetail();
              if(rs.getInt("ALLOWANCE")==Overtime.ALLOWANCE_FOOD){
                  //update by satrya 2014-02-11 karena jika allowance food maka nilainya 0 tableHitungOvertimeDetail.addvAllowanceFood(rs.getInt("ALLOWANCE"));
                tableHitungOvertimeDetail.addvAllowanceFood(1);
              }else{
                tableHitungOvertimeDetail.addvAllowanceMoney(rs.getInt("ALLOWANCE"));
              }
              
              if(rs.getInt("PAID_BY")==OvertimeDetail.PAID_BY_SALARY){
                  //update by satrya 2014-02-11 karena jika paid by salary maka nilainya 0 tableHitungOvertimeDetail.addvPaidSalary(rs.getInt("PAID_BY"));
                tableHitungOvertimeDetail.addvPaidSalary(1);
              }else{
                tableHitungOvertimeDetail.addvPaidDp(rs.getInt("PAID_BY"));
              }
              // update by satrya 2014-01-25 tableHitungOvertimeDetail.addvDuration(rs.getDouble("DURATION"));
              tableHitungOvertimeDetail.addvDuration(DBHandler.convertDate(rs.getDate("REAL_DATE_FROM"), rs.getTime("REAL_DATE_FROM")),DBHandler.convertDate(rs.getDate("REAL_DATE_TO"), rs.getTime("REAL_DATE_TO")),DBHandler.convertDate(rs.getDate("DATE_FROM"), rs.getTime("DATE_FROM")),DBHandler.convertDate(rs.getDate("DATE_TO"), rs.getTime("DATE_TO")),rs.getDouble("REST_TIME_HR"));
              tableHitungOvertimeDetail.addvTotIdx(rs.getDouble("TOT_IDX"));
              tableHitungOvertimeDetail.setEmployee_id(rs.getLong("EMPLOYEE_ID"));
          }
          else{
              if(rs.getInt("ALLOWANCE")==Overtime.ALLOWANCE_FOOD){
                  //update by satrya 2014-02-11 karena jika allowance food maka nilainya 0 tableHitungOvertimeDetail.addvAllowanceFood(rs.getInt("ALLOWANCE"));
                tableHitungOvertimeDetail.addvAllowanceFood(1);
              }else{
                tableHitungOvertimeDetail.addvAllowanceMoney(rs.getInt("ALLOWANCE"));
              }
              
              if(rs.getInt("PAID_BY")==OvertimeDetail.PAID_BY_SALARY){
                  //update by satrya 2014-02-11 karena jika paid by salary maka nilainya 0 tableHitungOvertimeDetail.addvPaidSalary(rs.getInt("PAID_BY"));
                tableHitungOvertimeDetail.addvPaidSalary(1);
              }else{
                tableHitungOvertimeDetail.addvPaidDp(rs.getInt("PAID_BY"));
              }
              //update by satrya 2014-01-25 tableHitungOvertimeDetail.addvDuration(rs.getDouble("DURATION"));
              tableHitungOvertimeDetail.addvDuration(DBHandler.convertDate(rs.getDate("REAL_DATE_FROM"), rs.getTime("REAL_DATE_FROM")),DBHandler.convertDate(rs.getDate("REAL_DATE_TO"), rs.getTime("REAL_DATE_TO")),DBHandler.convertDate(rs.getDate("DATE_FROM"), rs.getTime("DATE_FROM")),DBHandler.convertDate(rs.getDate("DATE_TO"), rs.getTime("DATE_TO")),rs.getDouble("REST_TIME_HR"));
              tableHitungOvertimeDetail.addvTotIdx(rs.getDouble("TOT_IDX"));
              tableHitungOvertimeDetail.setEmployee_id(rs.getLong("EMPLOYEE_ID"));
          }
                //lists.add(overtimeDetail);
                lists.put(tableHitungOvertimeDetail.getEmployee_id(), tableHitungOvertimeDetail);
                
            }
            rs.close();
            return lists;

        } catch (Exception e) {

            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }
    
    
    
    /**
     * Create by satrya 2014-02-03
     * keterangan mencari Overtime
     * @param oidCompany
     * @param oidDivision
     * @param departmentId
     * @param fromDate
     * @param toDate
     * @param sectionId
     * @param paidBySalary
     * @param paidByDayOff
     * @param allowanceFood
     * @param allowanceMoney
     * @param durTotIdxDur
     * @param dataStatus
     * @param levelCode
     * @param searchNrFrom
     * @param searchNrTo
     * @param searchName
     * @return 
     */
     public static Hashtable summaryOT(long oidCompany,long oidDivision,long departmentId, Date fromDate, Date toDate, 
                long sectionId, int paidBySalary,int paidByDayOff
                    , int allowanceFood, int allowanceMoney, int durTotIdxDur,int dataStatus,String levelCode,String searchNrFrom, String searchNrTo, String searchName) {
        Hashtable lists = new Hashtable();
        DBResultSet dbrs = null;
        try {
            /**
             * String sql = " select distinct SUM("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVT_DURATION]+") AS DURATION , "
                         + " SUM("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_TOT_IDX]+") AS TOT_IDX , "
                         + " COUNT("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]+") AS ALLOWANCE, "
                         + " COUNT("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+") AS PAID_BY, "
                         + " EMP."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + " AS EMPLOYEE_ID "
                         + " FROM "+ PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS ODT INNER JOIN "
                         + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP on ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] 
                         + " = EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                         + " WHERE  ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM] + " IS NOT NULL AND "
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO]  +" IS NOT NULL ";
             */
            String sql = /*" select  "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVT_DURATION]+" AS DURATION , "*/
                         " select  "
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM] + " AS REAL_DATE_FROM ,"
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO] + " AS REAL_DATE_TO ,"
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_HR] + " AS  REST_TIME_HR ,"
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " AS DATE_FROM ,"
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + " AS DATE_TO ,"
                         //+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVT_DURATION]+" AS DURATION , "
                         + " "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_TOT_IDX]+" AS TOT_IDX , "
                         + " "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]+" AS ALLOWANCE, "
                         + " "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+" AS PAID_BY, "
                         + " EMP."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + " AS EMPLOYEE_ID "
                         + " FROM "+ PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS ODT INNER JOIN "
                         + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP on ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] 
                         + " = EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                         
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                     + " INNER JOIN "+ PstPosition.TBL_HR_POSITION + " AS POS ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS PAY ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    
                         
                         + " WHERE  ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM] + " IS NOT NULL AND "
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO]  +" IS NOT NULL ";
             
            if(paidBySalary==OvertimeDetail.PAID_BY_SALARY){
                        sql+= " AND "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+"="+OvertimeDetail.PAID_BY_SALARY;
             }else if(paidByDayOff==OvertimeDetail.PAID_BY_DAY_OFF){
                  sql+= " AND "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+"="+OvertimeDetail.PAID_BY_DAY_OFF;
             }else if(allowanceFood == Overtime.ALLOWANCE_FOOD){
                  sql+= " AND "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]+"="+Overtime.ALLOWANCE_FOOD;
             }else if(allowanceMoney == Overtime.ALLOWANCE_MONEY){
                  sql+= " AND "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_ALLOWANCE]+"="+Overtime.ALLOWANCE_MONEY;
             }                   
            
             if(fromDate!=null && toDate!=null){
                    sql= sql + "  AND (( EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " +PstEmployee.YES_RESIGN + " AND " + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + " BETWEEN \"" + Formater.formatDate(fromDate, "yyyy-MM-dd  00:00:00")+ "\""  +" AND "+ "\"" + Formater.formatDate(toDate, "yyyy-MM-dd  23:59:59")+ "\"" 
                    + " ) OR (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN + "))"
                    + "AND ("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " BETWEEN \""+Formater.formatDate(fromDate, "yyyy-MM-dd  00:00:00")+"\""+" AND "+"\""+Formater.formatDate(toDate, "yyyy-MM-dd  23:59:59")+ "\""
                    + " AND "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] +" BETWEEN \""+Formater.formatDate(fromDate, "yyyy-MM-dd  00:00:00")+"\""+" AND "+"\""+Formater.formatDate(toDate, "yyyy-MM-dd  23:59:59")+ "\")";
                    
                }   
             
            if (levelCode != null && !levelCode.equals("") && !levelCode.equals("0")) {
                sql = sql + " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] + " LIKE '%" + levelCode.trim() + "%' AND ";
                
            }
            if(departmentId != 0){
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + departmentId;
            }
            //update by satrya 2014-01-20
            if(oidCompany != 0){
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = " + oidCompany;
            }

            if(oidDivision != 0){
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = " + oidDivision;
            }

            if(sectionId != 0){
                sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + sectionId;
            }
            if ((searchNrFrom != null) && (searchNrFrom.length() > 0)) {
                if(searchNrTo==null || searchNrTo.length()==0){
                    searchNrTo =searchNrFrom;
                }
                Vector vectNrFrom = logicParser(searchNrFrom);
                if (vectNrFrom != null && vectNrFrom.size() > 0) {
                    sql = sql + " AND (";
                    for (int i = 0; i < vectNrFrom.size(); i++) {
                        String str = (String) vectNrFrom.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " BETWEEN '" + searchNrFrom + "' AND '" + searchNrTo + "'";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + ") ";
                }

            }


            if ((searchName != null) && (searchName.length() > 0)) {
                Vector vectName = logicParser(searchName);
                if (vectName != null && vectName.size() > 0) {
                    sql = sql + " AND (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + ")  ";
                }

            }
              if (dataStatus < 2) {
                sql = sql + " AND PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_STATUS]
                        + " = " + dataStatus + "  ";

            }

            sql += " GROUP BY ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_DETAIL_ID];
            sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+",ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM]+ " ASC "; 
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            TableHitungOvertimeDetail tableHitungOvertimeDetail = new TableHitungOvertimeDetail();
            long tmpEmployeeId=0;
            while (rs.next()) {
                //OvertimeDetail overtimeDetail = new OvertimeDetail();
//                
//             if(paidBySalary==OvertimeDetail.PAID_BY_SALARY){
//                  overtimeDetail.setTotPaidBySalary(rs.getInt("PAID_BY"));
//                   overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//             }else if(paidByDayOff==OvertimeDetail.PAID_BY_DAY_OFF){
//                  overtimeDetail.setTotPaidBydayOff(rs.getInt("PAID_BY"));
//                   overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//             }else if(allowanceFood == Overtime.ALLOWANCE_FOOD){
//                  overtimeDetail.setTotAllowanceFood(rs.getInt("ALLOWANCE"));
//                   overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//             }else if(allowanceMoney == Overtime.ALLOWANCE_MONEY){
//                   overtimeDetail.setTotAllowanceMoney(rs.getInt("ALLOWANCE"));
//                    overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//             }else if(durTotIdxDur==1){
//                overtimeDetail.setTotDuration(rs.getDouble("DURATION"));
//                overtimeDetail.setTotIdx(rs.getDouble("TOT_IDX"));
//                
//             }
          if(tmpEmployeeId !=rs.getLong("EMPLOYEE_ID")){
              tmpEmployeeId = rs.getLong("EMPLOYEE_ID");
              tableHitungOvertimeDetail = new TableHitungOvertimeDetail();
              if(rs.getInt("ALLOWANCE")==Overtime.ALLOWANCE_FOOD){
                  //update by satrya 2014-02-11 karena jika allowance food maka nilainya 0 tableHitungOvertimeDetail.addvAllowanceFood(rs.getInt("ALLOWANCE"));
                tableHitungOvertimeDetail.addvAllowanceFood(1);
              }else{
                tableHitungOvertimeDetail.addvAllowanceMoney(rs.getInt("ALLOWANCE"));
              }
              
              if(rs.getInt("PAID_BY")==OvertimeDetail.PAID_BY_SALARY){
                  //update by satrya 2014-02-11 karena jika paid by salary maka nilainya 0 tableHitungOvertimeDetail.addvPaidSalary(rs.getInt("PAID_BY"));
                tableHitungOvertimeDetail.addvPaidSalary(1);
              }else{
                tableHitungOvertimeDetail.addvPaidDp(rs.getInt("PAID_BY"));
              }
              // update by satrya 2014-01-25 tableHitungOvertimeDetail.addvDuration(rs.getDouble("DURATION"));
              tableHitungOvertimeDetail.addvDuration(DBHandler.convertDate(rs.getDate("REAL_DATE_FROM"), rs.getTime("REAL_DATE_FROM")),DBHandler.convertDate(rs.getDate("REAL_DATE_TO"), rs.getTime("REAL_DATE_TO")),DBHandler.convertDate(rs.getDate("DATE_FROM"), rs.getTime("DATE_FROM")),DBHandler.convertDate(rs.getDate("DATE_TO"), rs.getTime("DATE_TO")),rs.getDouble("REST_TIME_HR"));
              tableHitungOvertimeDetail.addvTotIdx(rs.getDouble("TOT_IDX"));
              tableHitungOvertimeDetail.setEmployee_id(rs.getLong("EMPLOYEE_ID"));
          }
          else{
              if(rs.getInt("ALLOWANCE")==Overtime.ALLOWANCE_FOOD){
                  //update by satrya 2014-02-11 karena jika allowance food maka nilainya 0 tableHitungOvertimeDetail.addvAllowanceFood(rs.getInt("ALLOWANCE"));
                tableHitungOvertimeDetail.addvAllowanceFood(1);
              }else{
                tableHitungOvertimeDetail.addvAllowanceMoney(rs.getInt("ALLOWANCE"));
              }
              
              if(rs.getInt("PAID_BY")==OvertimeDetail.PAID_BY_SALARY){
                  //update by satrya 2014-02-11 karena jika paid by salary maka nilainya 0 tableHitungOvertimeDetail.addvPaidSalary(rs.getInt("PAID_BY"));
                tableHitungOvertimeDetail.addvPaidSalary(1);
              }else{
                tableHitungOvertimeDetail.addvPaidDp(rs.getInt("PAID_BY"));
              }
              //update by satrya 2014-01-25 tableHitungOvertimeDetail.addvDuration(rs.getDouble("DURATION"));
              tableHitungOvertimeDetail.addvDuration(DBHandler.convertDate(rs.getDate("REAL_DATE_FROM"), rs.getTime("REAL_DATE_FROM")),DBHandler.convertDate(rs.getDate("REAL_DATE_TO"), rs.getTime("REAL_DATE_TO")),DBHandler.convertDate(rs.getDate("DATE_FROM"), rs.getTime("DATE_FROM")),DBHandler.convertDate(rs.getDate("DATE_TO"), rs.getTime("DATE_TO")),rs.getDouble("REST_TIME_HR"));
              tableHitungOvertimeDetail.addvTotIdx(rs.getDouble("TOT_IDX"));
              tableHitungOvertimeDetail.setEmployee_id(rs.getLong("EMPLOYEE_ID"));
          }
                //lists.add(overtimeDetail);
                lists.put(tableHitungOvertimeDetail.getEmployee_id(), tableHitungOvertimeDetail);
            }
            rs.close();
            return lists;

        } catch (Exception e) {

            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

    /**
     * Keterangan : summary OT
     * create by satrya 2013-05-06
     * @param departmentId
     * @param idDivision
     * @param sectionId
     * @param searchNrFrom
     * @param searchNrTo
     * @param fullName
     * @param fromDate
     * @param toDate
     * @param statusData
     * @param inclResign
     * @param paidBySalary
     * @param paidByDayOff
     * @param durTotIdxDur
     * @return 
     */
    public static Vector listSummaryOT(long departmentId, long idDivision, long sectionId, String searchNrFrom,
            String searchNrTo, String fullName, Date fromDate,Date toDate, boolean inclResign, int paidBySalary,int paidByDayOff) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " select "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVT_DURATION]+" AS DURATION , "
                         +PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_TOT_IDX]+" AS TOT_IDX , "
                         +PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+" AS PAID_BY, "
                         + " EMP."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + " AS EMPLOYEE_ID "
                         + " FROM "+ PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS ODT INNER JOIN "
                         + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP on ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] 
                         + " = EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                         + " INNER JOIN "+ PstOvertime.TBL_OVERTIME + " AS OV ON ov."+PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]+"=ODT."+fieldNames[FLD_OVERTIME_ID]
                         + " WHERE  ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_FROM] + " IS NOT NULL AND "
                         + " ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REAL_DATE_TO]  +" IS NOT NULL "
                         + " AND OV."+PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC] + "="+I_DocStatus.DOCUMENT_STATUS_PROCEED;
             
            if(paidBySalary==OvertimeDetail.PAID_BY_SALARY){
                   sql+= " AND ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+"="+OvertimeDetail.PAID_BY_SALARY;
             }else if(paidByDayOff==OvertimeDetail.PAID_BY_DAY_OFF){
                  sql+= " AND ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+"="+OvertimeDetail.PAID_BY_DAY_OFF;
             }else{
                 sql = sql + " AND ODT."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PAID_BY]+" IN ("+OvertimeDetail.PAID_BY_SALARY+","+OvertimeDetail.PAID_BY_DAY_OFF+")";
             }                  
            
             if(fromDate!=null && toDate!=null){
                    sql= sql + "  AND (( EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " +PstEmployee.YES_RESIGN + " AND " + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + " BETWEEN \"" + Formater.formatDate(fromDate, "yyyy-MM-dd  00:00:00")+ "\""  +" AND "+ "\"" + Formater.formatDate(toDate, "yyyy-MM-dd  23:59:59")+ "\"" 
                    + " ) OR (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN + "))"
                    + "AND ("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " BETWEEN \""+Formater.formatDate(fromDate, "yyyy-MM-dd  00:00:00")+"\""+" AND "+"\""+Formater.formatDate(toDate, "yyyy-MM-dd  23:59:59")+ "\""
                    + " AND "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] +" BETWEEN \""+Formater.formatDate(fromDate, "yyyy-MM-dd  00:00:00")+"\""+" AND "+"\""+Formater.formatDate(toDate, "yyyy-MM-dd  23:59:59")+ "\")";
                    
                }   
                    if(departmentId != 0){
                        sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + departmentId;
                    }

                    if(sectionId != 0){
                        sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + sectionId;
                    }
                    if(idDivision != 0){
                        sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = " + idDivision;
                    }
                    
                String whereClause="";
                    if ((searchNrFrom != null) && (searchNrFrom.length() > 0)) {
                Vector vectNrFrom = logicParser(searchNrFrom);
                if (vectNrFrom != null && vectNrFrom.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNrFrom.size(); i++) {
                        String str = (String) vectNrFrom.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " BETWEEN '" + searchNrFrom + "' AND '" + searchNrTo + "'";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }


            if (fullName != null && fullName != "") {
                Vector vectFullName = logicParser(fullName);
                  sql = sql + " AND ";
                if (vectFullName != null && vectFullName.size() > 0) {
                    sql = sql + " ( ";
                    for (int i = 0; i < vectFullName.size(); i++) {
                        String str = (String) vectFullName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql +" EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }
                 
            }
            
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }

            sql += " ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            OvertimeSummary overtimeSummary = new OvertimeSummary();
            //double otIdx=0;
            double otDuration=0;
            double otPaidBySalary=0;
            double otPaidByDp=0;
             double otPaidByDpSalary=0;
            while (rs.next()) {
               
                long empId= rs.getLong("EMPLOYEE_ID");
                 if(overtimeSummary.getEmployeeId() != empId){
                           overtimeSummary = new OvertimeSummary();
                           //otIdx=0;
                           otDuration=0;
                           otPaidBySalary=0;
                           otPaidByDp=0;
                           otPaidByDpSalary=0;
                           overtimeSummary.setEmployeeId(empId);
                           lists.add(overtimeSummary);
                 }
                 
                //prosess pembulatan duration
                double dur =rs.getDouble("DURATION");
                if(SessOvertime.getOvertimeRoundTo()>0 && (dur*60)>SessOvertime.getOvertimeRoundStart() ){ 
                    dur = ((double)((Math.round(dur*60)/ SessOvertime.getOvertimeRoundTo())*SessOvertime.getOvertimeRoundTo()))/60d;
                }
                
            if(paidBySalary==OvertimeDetail.PAID_BY_SALARY){
                   otPaidBySalary = otPaidBySalary + rs.getDouble("TOT_IDX"); 
                   overtimeSummary.setOtPaidSummarySallary(otPaidBySalary);
                    otDuration = otDuration + dur;
                 //otIdx = otIdx + rs.getDouble("TOT_IDX");
                overtimeSummary.setOtDuration(otDuration);
             }else if(paidByDayOff==OvertimeDetail.PAID_BY_DAY_OFF){
                  otPaidByDp = otPaidByDp + rs.getDouble("TOT_IDX");
                  overtimeSummary.setOtPaidSummaryDp(otPaidByDp);
                  otDuration = otDuration + dur;
                 //otIdx = otIdx + rs.getDouble("TOT_IDX");
                overtimeSummary.setOtDuration(otDuration);
             }else{
                 otPaidByDpSalary = otPaidByDpSalary + rs.getDouble("TOT_IDX");
                 overtimeSummary.setOtPaidSummarySallaryAndDP(otPaidByDpSalary);
                 otDuration = otDuration + dur;
                 //otIdx = otIdx + rs.getDouble("TOT_IDX");
                overtimeSummary.setOtDuration(otDuration);
             }                

                
                
             
               
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
    //}
    //update by satrya 2012-09-13
    /**
     * 
     * @param limitStart
     * @param recordToGet
     * @param whereClauseEmpTime
     * @param order
     * @return untuk melihat list overtime di Daily Presence report
     */
     public static Vector listOvertime(int limitStart, int recordToGet,Long oidDepartment, String fullName, Date selectedDateFrom, Date selectedDateTo, Long oidSection, String empNum, String order,Vector stStatus) {
         String whereClauseEmpTime = "";
         //update by satrya 2012-10-15
      if(selectedDateFrom!=null && selectedDateTo!=null){
         if (selectedDateFrom.getTime() > selectedDateTo.getTime()) {
                Date tempFromDate = selectedDateFrom;
                Date tempToDate = selectedDateTo;
                selectedDateFrom = tempToDate;
                selectedDateTo = tempFromDate;
            }
         String whereClauseReq = " odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " BETWEEN "
                              + "\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd")+ " 00:00:00"+"\"" 
                             + " AND " + "\"" + Formater.formatDate(selectedDateTo, " yyyy-MM-dd ") + "23:59:59"+"\"";

         if((selectedDateFrom != null) && (selectedDateTo != null)) {
            whereClauseEmpTime = whereClauseReq;
        }
         if(oidDepartment !=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ "dep."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" = " + oidDepartment;
        }
         if(oidSection !=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ "emp."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+" = " + oidSection;
        }
        if(fullName !=null && fullName.length() > 0){
//            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                                 + " LIKE '%"+fullName.trim()+"%'";
            Vector vectName = logicParser(fullName);
              whereClauseEmpTime = whereClauseEmpTime + " AND ";
                if (vectName != null && vectName.size() > 0) {
                    //whereClause = whereClause + " AND (";
                  
                    whereClauseEmpTime = whereClauseEmpTime + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClauseEmpTime = whereClauseEmpTime + " emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClauseEmpTime = whereClauseEmpTime + str.trim();
        }
                    }
                    whereClauseEmpTime = whereClauseEmpTime + ")";
                }
        }
      /* if(oidSection !=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ ;
        }*/
        if(empNum !=null && empNum.length() > 0){
//            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                                 + " LIKE '%"+empNum.trim()+"%'";
            Vector vectName = logicParser(empNum);
              whereClauseEmpTime = whereClauseEmpTime + " AND ";
                if (vectName != null && vectName.size() > 0) {
                    //whereClause = whereClause + " AND (";
                  
                    whereClauseEmpTime = whereClauseEmpTime + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClauseEmpTime = whereClauseEmpTime + " emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClauseEmpTime = whereClauseEmpTime + str.trim();
        }
                    }
                    whereClauseEmpTime = whereClauseEmpTime + ")";
                }
        }
        if( stStatus!=null && stStatus.size()>0){
                 //whereClauseEmpTime  = whereClauseEmpTime + PstPresence.fieldNames[PstPresence.FLD_STATUS] + " IN ("+ getEmployeePresence +")" ;
            String getEmployeePresence = PstPresence.getEmployee(0 , 0, "", oidDepartment, fullName.trim(),  
                        selectedDateFrom, selectedDateTo, oidSection, empNum.trim(),stStatus); 
               
                if(getEmployeePresence.length()<=0){
                   getEmployeePresence = ""+0;
               }
                whereClauseEmpTime = whereClauseEmpTime + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN ("+ getEmployeePresence +")";
        }
        
         return list3(limitStart, recordToGet, whereClauseEmpTime, order);
     }
        return new Vector();
     }

     /**
      * Ketrerangan: fungsi untuk di reoport daily
      * create by satrya 2013-12-03
      * @param limitStart
      * @param recordToGet
      * @param oidDepartment
      * @param fullName
      * @param selectedDateFrom
      * @param selectedDateTo
      * @param oidSection
      * @param empNum
      * @param order
      * @param stStatus
      * @param oidCompany
      * @param oidDivision
      * @return 
      */
     public static Vector listOvertime(int limitStart, int recordToGet,Long oidDepartment, String fullName, Date selectedDateFrom, Date selectedDateTo, Long oidSection, String empNum, String order,Vector stStatus,long oidCompany,long oidDivision) {
         String whereClauseEmpTime = "";
         //update by satrya 2012-10-15
      if(selectedDateFrom!=null && selectedDateTo!=null){
         if (selectedDateFrom.getTime() > selectedDateTo.getTime()) {
                Date tempFromDate = selectedDateFrom;
                Date tempToDate = selectedDateTo;
                selectedDateFrom = tempToDate;
                selectedDateTo = tempFromDate;
            }
         String whereClauseReq = " odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " BETWEEN "
                              + "\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd")+ " 00:00:00"+"\"" 
                             + " AND " + "\"" + Formater.formatDate(selectedDateTo, " yyyy-MM-dd ") + "23:59:59"+"\"";

         if((selectedDateFrom != null) && (selectedDateTo != null)) {
            whereClauseEmpTime = whereClauseReq;
        }
         if(oidDepartment !=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ "dep."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" = " + oidDepartment;
        }
         if(oidSection !=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ "sec."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+" = " + oidSection;
        }
        if(fullName !=null && fullName.length() > 0){
//            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                                 + " LIKE '%"+fullName.trim()+"%'";
            Vector vectName = logicParser(fullName);
              whereClauseEmpTime = whereClauseEmpTime + " AND ";
                if (vectName != null && vectName.size() > 0) {
                    //whereClause = whereClause + " AND (";
                  
                    whereClauseEmpTime = whereClauseEmpTime + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClauseEmpTime = whereClauseEmpTime + " emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClauseEmpTime = whereClauseEmpTime + str.trim();
        }
                    }
                    whereClauseEmpTime = whereClauseEmpTime + ")";
                }
        }
      /* if(oidSection !=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ ;
        }*/
        if(empNum !=null && empNum.length() > 0){
//            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                                 + " LIKE '%"+empNum.trim()+"%'";
            Vector vectName = logicParser(empNum);
              whereClauseEmpTime = whereClauseEmpTime + " AND ";
                if (vectName != null && vectName.size() > 0) {
                    //whereClause = whereClause + " AND (";
                  
                    whereClauseEmpTime = whereClauseEmpTime + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClauseEmpTime = whereClauseEmpTime + " emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClauseEmpTime = whereClauseEmpTime + str.trim();
        }
                    }
                    whereClauseEmpTime = whereClauseEmpTime + ")";
                }
        }
        if( stStatus!=null && stStatus.size()>0){
                 //whereClauseEmpTime  = whereClauseEmpTime + PstPresence.fieldNames[PstPresence.FLD_STATUS] + " IN ("+ getEmployeePresence +")" ;
            String getEmployeePresence = PstPresence.getEmployee(0 , 0, "", oidDepartment, fullName.trim(),  
                        selectedDateFrom, selectedDateTo, oidSection, empNum.trim(),stStatus); 
               
                if(getEmployeePresence.length()<=0){
                   getEmployeePresence = ""+0;
               }
                whereClauseEmpTime = whereClauseEmpTime + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN ("+ getEmployeePresence +")";
        }
        
         return list3(limitStart, recordToGet, whereClauseEmpTime, order);
     }
        return new Vector();
     }
     public static boolean checkOvertimeInVector(Vector<OvertimeDetail> listOvDetail, Date theDate){
         if(listOvDetail==null){
             return false;
         }
         theDate.setHours(0);
         theDate.setMinutes(0);
         theDate.setSeconds(0);
         for(int i=0;i<listOvDetail.size();i++){
             OvertimeDetail ovd = (OvertimeDetail) listOvDetail.get(i);             
             Date ovDate = ovd.getDateFrom();
             ovDate.setHours(0);
             ovDate.setMinutes(0);
             ovDate.setSeconds(0);
             if(ovd.getRealDateFrom().equals(ovDate) || ovd.getRealDateTo().equals(ovDate)  ){
                 return true;
             }
         }
         return false;
     }
      
     public static Vector listOvertimeOverlap(long exceptionDetailOid, int limitStart, int recordToGet,long oidDepartment, String fullName, Date selectedDateFrom, Date selectedDateTo, long oidSection, String empNum, long employeeOid, String order) {
         String whereClauseEmpTime = "";
        
         String whereClauseReq = "(("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+
                 I_DocStatus.DOCUMENT_STATUS_CANCELLED   + ") AND ( (odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] 
                 + " BETWEEN " + "\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd HH:mm")+"\"" 
                             + " AND " + "\"" + Formater.formatDate(selectedDateTo, " yyyy-MM-dd HH:mm") +"\" ) OR "
                 + " (odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + " BETWEEN "
                              + "\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd HH:mm")+ ""+"\"" 
                             + " AND " + "\"" + Formater.formatDate(selectedDateTo, " yyyy-MM-dd HH:mm")+"\" ) ) )" +
                 " AND (odt." +  PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + "<>" +  "\"" + Formater.formatDate(selectedDateTo, " yyyy-MM-dd HH:mm") +"\") "
                 + " AND (odt." +  PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + "<>"
                              + "\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd HH:mm")+ ""+"\")"  ;

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
            whereClauseEmpTime = "( odt."+fieldNames[FLD_OVERTIME_DETAIL_ID]
                                 + " <> '"+exceptionDetailOid+"' AND " + whereClauseEmpTime + ")";
        }

        
        
         return list3(limitStart, recordToGet, whereClauseEmpTime, order);
     }
     /**
     * Create by satrya 2014-01-24
     * keterangan: untuk mencari list overtime
     * @param fromDate
     * @param toDate
     * @param employee_id
     * @return 
     */
    public static Vector listOvertimeDetail(long departmentId,long companyId,long divisionId, int iCalendarType, Date selectedMonth, int weekIndex, long sectionId, String empNum, String fullName) {
        Vector lists = new Vector();
        String where =" (1=1) AND ";
          CalendarCalc objCalendarCalc = new CalendarCalc(iCalendarType);
         Date startDateWeek = objCalendarCalc.getStartDateOfTheWeek(selectedMonth, weekIndex);
        Date endDateWeek = objCalendarCalc.getEndDateOfTheWeek(selectedMonth, weekIndex);
        
       
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT otd.* FROM "+TBL_OVERTIME_DETAIL + " AS otd "
            + " INNER JOIN " + PstOvertime.TBL_OVERTIME + " AS ov ON otd."+fieldNames[FLD_OVERTIME_ID]+"=ov."+PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID];
             sql=sql + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP ";       
            sql = sql + " WHERE (1=1) AND " ;
if(startDateWeek!=null && endDateWeek!=null){
            sql = sql + "\""+Formater.formatDate(endDateWeek, "yyyy-MM-dd 23:59:59")+"\" > " 
                + fieldNames[FLD_DATE_FROM]+ " AND "
                + fieldNames[FLD_DATE_FROM] + " > \""+Formater.formatDate(startDateWeek, "yyyy-MM-dd 00:00:00")+"\" AND ";
        }
            
            if (departmentId != 0) {
                    sql = sql + "  EMP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                            + " = " + departmentId + " AND ";
                }
                //update by devin 2014-01-27
                 if (companyId != 0) {
                    sql = sql + "  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                            + " = " + companyId + " AND ";
                }
                  if (divisionId != 0) {
                    sql = sql + "  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                            + " = " + divisionId + " AND ";
                }

                if (sectionId != 0) {
                    sql = sql + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                            + " = " + sectionId +  " AND ";
                }
               //update by satrya 2012-07-30

                 if (empNum != null && empNum != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                        + " = " + "\"" + empNumId.trim() + "\"";//penambahan trima
                Vector vectNum = logicParser(empNum);
                sql = sql + "";
                if (vectNum != null && vectNum.size() > 0) {
                    sql = sql + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i); 
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql +" "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' AND ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + ")";
                }
                
            }
            //update by devin 2014-01-30
            if (fullName != null && fullName != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                        + " LIKE " + "\"%" + fullName.trim() + "%\"";//penambahan trim
                Vector vectFullName = logicParser(fullName);
                  sql = sql + "";
                if (vectFullName != null && vectFullName.size() > 0) {
                    sql = sql + " ( ";
                    for (int i = 0; i < vectFullName.size(); i++) {
                        String str = (String) vectFullName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql +" "+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' AND ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }
                 
            }
            sql = sql + " (1=1)";
            //System.out.println("sql::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                OvertimeDetail overtimeDetail = new OvertimeDetail();
                resultToObject(rs, overtimeDetail);
                lists.add(overtimeDetail);
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
    
    public static Vector listOvertimeDetailMonthly(long departmentId,long companyId,long divisionId, int iCalendarType, long periodId, long sectionId, String empNum, String fullName) {
        Vector lists = new Vector();
        String where =" (1=1) AND ";
        Period period = new Period();
        
            if(periodId!=0){
                try{
                    period=PstPeriod.fetchExc(periodId); 
                }catch(Exception exc){
                    
                }
           
            }
         
         Date startDate = period.getStartDate()!=null?period.getStartDate():null;
        Date endDate = period.getEndDate()!=null?period.getEndDate():null;
        
        if(startDate==null ||endDate==null){
            return new Vector();
        }
        
       
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT otd.* FROM "+TBL_OVERTIME_DETAIL + " AS otd "
            + " INNER JOIN " + PstOvertime.TBL_OVERTIME + " AS ov ON otd."+fieldNames[FLD_OVERTIME_ID]+"=ov."+PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID];
             sql=sql + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP ";       
            sql = sql + " WHERE (1=1) AND " ;
if(startDate!=null && endDate!=null){
            sql = sql + "\""+Formater.formatDate(endDate, "yyyy-MM-dd 23:59:59")+"\" > " 
                + fieldNames[FLD_DATE_FROM]+ " AND "
                + fieldNames[FLD_DATE_FROM] + " > \""+Formater.formatDate(startDate, "yyyy-MM-dd 00:00:00")+"\" AND ";
        }
            
            if (departmentId != 0) {
                    sql = sql + "  EMP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                            + " = " + departmentId + " AND ";
                }
                //update by devin 2014-01-27
                 if (companyId != 0) {
                    sql = sql + "  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                            + " = " + companyId + " AND ";
                }
                  if (divisionId != 0) {
                    sql = sql + "  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                            + " = " + divisionId + " AND ";
                }

                if (sectionId != 0) {
                    sql = sql + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                            + " = " + sectionId +  " AND ";
                }
               //update by satrya 2012-07-30

                 if (empNum != null && empNum != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                        + " = " + "\"" + empNumId.trim() + "\"";//penambahan trima
                Vector vectNum = logicParser(empNum);
                sql = sql + "";
                if (vectNum != null && vectNum.size() > 0) {
                    sql = sql + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i); 
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql +" "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' AND ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + ")";
                }
                
            }
            //update by devin 2014-01-30
            if (fullName != null && fullName != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                        + " LIKE " + "\"%" + fullName.trim() + "%\"";//penambahan trim
                Vector vectFullName = logicParser(fullName);
                  sql = sql + "";
                if (vectFullName != null && vectFullName.size() > 0) {
                    sql = sql + " ( ";
                    for (int i = 0; i < vectFullName.size(); i++) {
                        String str = (String) vectFullName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql +" "+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' AND ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }
                 
            }
            sql = sql + " (1=1)";
            //System.out.println("sql::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                OvertimeDetail overtimeDetail = new OvertimeDetail();
                resultToObject(rs, overtimeDetail);
                lists.add(overtimeDetail);
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
     //create by satrya 2013-05-06
     public static Vector listOvertimeOverlapVer2(long exceptionDetailOid, 
             int limitStart, int recordToGet,long oidDepartment, String fullName,
             Date selectedDateFrom, Date selectedDateTo, long oidSection, String empNum, long employeeOid, String order) {
         String whereClauseEmpTime = "";
         /*String whereClauseReq = "(("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+
                 I_DocStatus.DOCUMENT_STATUS_CANCELLED   + ") AND ( (odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] 
                 + " BETWEEN " + "\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd 00:00")+"\"" 
                             + " AND " + "\"" + Formater.formatDate(selectedDateTo, " yyyy-MM-dd 23:59") +"\" ) OR "
                 + " (odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + " BETWEEN "
                              + "\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd 00:00")+ ""+"\"" 
                             + " AND " + "\"" + Formater.formatDate(selectedDateTo, " yyyy-MM-dd  23:59")+"\" ) ) )" +
                 " AND (odt." +  PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + "<>" +  "\"" + Formater.formatDate(selectedDateTo, " yyyy-MM-dd 23:59") +"\") "
                 + " AND (odt." +  PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + "<>"
                              + "\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd 00:00")+ ""+"\")"  ;*/
         
         String whereClauseReq = "("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+
                 I_DocStatus.DOCUMENT_STATUS_CANCELLED   + ") AND ("
                 + "\""+Formater.formatDate(selectedDateTo,"yyyy-MM-dd 23:59")+"\" > odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]+ " AND odt. "+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + ">"+"\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd 00:00")+"\""+")";
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
            whereClauseEmpTime = "( odt."+fieldNames[FLD_OVERTIME_DETAIL_ID]
                                 + " <> '"+exceptionDetailOid+"' AND " + whereClauseEmpTime + ")";
        }

        
        
         return list3(limitStart, recordToGet, whereClauseEmpTime, order);
     }
     
     /**
      * Create by satrya 2014-02-03
      * untuk pencarian table overtime
      * @param exceptionDetailOid
      * @param limitStart
      * @param recordToGet
      * @param getListEmployeeId
      * @param selectedDateFrom
      * @param selectedDateTo
      * @param oidSection
      * @param empNum
      * @param employeeOid
      * @param order
      * @return 
      */
      public static HashTblOvertimeDetail HashOvertimeOverlapVer2(long exceptionDetailOid, 
             int limitStart, int recordToGet,String getListEmployeeId,
             Date selectedDateFrom, Date selectedDateTo, String order) {
         String whereClauseEmpTime = "";
        
        HashTblOvertimeDetail hashTblOvertimeDetail = new HashTblOvertimeDetail();
        if(getListEmployeeId==null || getListEmployeeId.length()==0){
            return hashTblOvertimeDetail;
        }
        DBResultSet dbrs = null;
        String test="";
        try {
            String sql = "SELECT odt.*,o."+PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]
                    +" FROM " + PstOvertimeDetail.TBL_OVERTIME_DETAIL
                    + " as odt INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " as emp ON odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + "= emp."
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " as d ON emp."
                    + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "= d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " INNER JOIN " + PstCompany.TBL_HR_COMPANY + " as c ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + "= c." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " as dep ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "= dep." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " as r ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID] + "= r." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstOvertime.TBL_OVERTIME + " as o ON odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID] + "= o." + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID] +
                    //update by satrya 2013-08-13
                    //penambahan section
                    " LEFT JOIN " + PstSection.TBL_HR_SECTION + " as sec ON sec." + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "= dep." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
            String whereClauseReq = "("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+
                 I_DocStatus.DOCUMENT_STATUS_CANCELLED   + ") AND ("
                 + "\""+Formater.formatDate(selectedDateTo,"yyyy-MM-dd 23:59")+"\" > odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]+ " AND odt. "+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + ">"+"\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd 00:00")+"\""+")";
         if((selectedDateFrom != null) && (selectedDateTo != null)) {
            whereClauseEmpTime = whereClauseReq;
        }
          //update by satrya 2014-02-03
         if(getListEmployeeId !=null && getListEmployeeId.length()>0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ "emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" IN (" + getListEmployeeId +")";
        }
        
        if(exceptionDetailOid!=0){
            whereClauseEmpTime = "( odt."+fieldNames[FLD_OVERTIME_DETAIL_ID]
                                 + " <> '"+exceptionDetailOid+"' AND " + whereClauseEmpTime + ")";
        }
            if (whereClauseEmpTime != null && whereClauseEmpTime.length() > 0) {
                sql = sql + " WHERE " + whereClauseEmpTime;
            }
            sql = sql + " GROUP BY " + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_DETAIL_ID];
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }


            //System.out.println("sql overtime detail " + sql);
           // System.out.println("sql overtime detail " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            test = sql;
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
               // hashTblOvertimeDetail = new HashTblOvertimeDetail();
                hashTblOvertimeDetail.addOvetime(rs.getLong(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]), DBHandler.convertDate(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]), rs.getTime(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM])),DBHandler.convertDate(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]), rs.getTime(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO])));
                
                
            }
            rs.close();
            return hashTblOvertimeDetail;

        } catch (Exception e) {
            System.out.println(""+test);
            System.out.println("Exception PstOvertimeDetail.list3"+e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return hashTblOvertimeDetail;
     }
     
      public static Vector listOvertimeOverlapVer3(long exceptionDetailOid, 
             int limitStart, int recordToGet,long oidDepartment, String fullName,
             Date selectedDateFrom, Date selectedDateTo, long oidSection, String empNum, long employeeOid, String order) {
         String whereClauseEmpTime = "";
         /*String whereClauseReq = "(("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+
                 I_DocStatus.DOCUMENT_STATUS_CANCELLED   + ") AND ( (odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] 
                 + " BETWEEN " + "\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd 00:00")+"\"" 
                             + " AND " + "\"" + Formater.formatDate(selectedDateTo, " yyyy-MM-dd 23:59") +"\" ) OR "
                 + " (odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + " BETWEEN "
                              + "\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd 00:00")+ ""+"\"" 
                             + " AND " + "\"" + Formater.formatDate(selectedDateTo, " yyyy-MM-dd  23:59")+"\" ) ) )" +
                 " AND (odt." +  PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + "<>" +  "\"" + Formater.formatDate(selectedDateTo, " yyyy-MM-dd 23:59") +"\") "
                 + " AND (odt." +  PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + "<>"
                              + "\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd 00:00")+ ""+"\")"  ;*/
         
         String whereClauseReq = "("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+
                 I_DocStatus.DOCUMENT_STATUS_CANCELLED   + ") AND ("
                 + "\""+Formater.formatDate(selectedDateTo,"yyyy-MM-dd HH:mm")+"\" > odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]+ " AND odt. "+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + ">"+"\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd HH:mm")+"\""+")";
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
            whereClauseEmpTime = "( odt."+fieldNames[FLD_OVERTIME_DETAIL_ID]
                                 + " <> '"+exceptionDetailOid+"' AND " + whereClauseEmpTime + ")";
        }

        
        
         return list3(limitStart, recordToGet, whereClauseEmpTime, order);
     }
     
      /**
       * Keterangan: mencari overtime detail hari yg sama jam dan tglnya
       * create by satrya 2014-05-15
       * @param exceptionDetailOid
       * @param limitStart
       * @param recordToGet
       * @param oidDepartment
       * @param fullName
       * @param selectedDateFrom
       * @param selectedDateTo
       * @param oidSection
       * @param empNum
       * @param employeeOid
       * @param order
       * @return 
       */
      public static Vector listOvertimeOverlapVer4(long exceptionDetailOid, 
             int limitStart, int recordToGet,long oidDepartment, String fullName,
             Date selectedDateFrom, Date selectedDateTo, long oidSection, String empNum, long employeeOid, String order) {
         String whereClauseEmpTime = "";
      
         String whereClauseReq = "("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+
                 I_DocStatus.DOCUMENT_STATUS_CANCELLED   + ") AND ("
                 + "\""+Formater.formatDate(selectedDateTo,"yyyy-MM-dd HH:mm")+"\" >= odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]+ " AND odt. "+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + ">="+"\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd HH:mm")+"\""+")";
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
            whereClauseEmpTime = "( odt."+fieldNames[FLD_OVERTIME_DETAIL_ID]
                                 + " <> '"+exceptionDetailOid+"' AND " + whereClauseEmpTime + ")";
        }

        
        
         return list3(limitStart, recordToGet, whereClauseEmpTime, order);
     }
    /**
     * mencari overtime yg ada berdasarkan parameter
     * create by satrya 2013-08-12
     * @param exceptionDetailOid
     * @param limitStart
     * @param recordToGet
     * @param oidDepartment
     * @param fullName
     * @param selectedDateFrom
     * @param selectedDateTo
     * @param oidSection
     * @param empNum
     * @param employeeOid
     * @param order
     * @return 
     */
    public static Hashtable getListOvertimeExistPerEmpId(long exceptionDetailOid, 
             int limitStart, int recordToGet,long oidDepartment, String fullName,
             Date selectedDateFrom, Date selectedDateTo, long oidSection, String empNum, long employeeOid, String order) {
         String whereClauseEmpTime = "";
      
         String whereClauseReq = "("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+
                 I_DocStatus.DOCUMENT_STATUS_CANCELLED   + ") AND ("
                 + "\""+Formater.formatDate(selectedDateTo,"yyyy-MM-dd 23:59")+"\" > odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]+ " AND odt. "+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + ">"+"\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd 00:00")+"\""+")";
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
            whereClauseEmpTime = "( odt."+fieldNames[FLD_OVERTIME_DETAIL_ID]
                                 + " <> '"+exceptionDetailOid+"' AND " + whereClauseEmpTime + ")";
        }

        Hashtable hashListsOtExist = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_DETAIL_ID]
                +",odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]
                +",odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]
                +",odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]
                +",odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_HR]
                +",odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_START]
                    +" FROM " + PstOvertimeDetail.TBL_OVERTIME_DETAIL
                    + " as odt INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " as emp ON odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + "= emp."
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " INNER JOIN " + PstDivision.TBL_HR_DIVISION + " as d ON emp."
                    + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "= d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    + " INNER JOIN " + PstCompany.TBL_HR_COMPANY + " as c ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + "= c." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    + " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " as dep ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "= dep." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " INNER JOIN " + PstReligion.TBL_HR_RELIGION + " as r ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID] + "= r." + PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]
                    + " INNER JOIN " + PstOvertime.TBL_OVERTIME + " as o ON odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID] + "= o." + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID];
            if (whereClauseEmpTime != null && whereClauseEmpTime.length() > 0) {
                sql = sql + " WHERE " + whereClauseEmpTime;
            }
            sql = sql + " GROUP BY " + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_DETAIL_ID];
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
            OvertimeTblExis overtimeTblExis = new OvertimeTblExis();
            while (rs.next()) {
                
                //overtimeTblExis.setEmployeeId(0);
                overtimeTblExis.setOvertimeDetailId(rs.getLong("odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_DETAIL_ID])); 
                
                overtimeTblExis.addStartDate(DBHandler.convertDate(rs.getDate("odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]), rs.getTime("odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM])));
                overtimeTblExis.addEndDate(DBHandler.convertDate(rs.getDate("odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]), rs.getTime("odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]))); 
                //overtimeDetail.setOvt_doc_nr(rs.getString("o."+PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]));
                overtimeTblExis.setRestTimeHr(rs.getDouble("odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_HR]));
                overtimeTblExis.addRestStart(DBHandler.convertDate(rs.getDate("odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_START]), rs.getTime("odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_START])));
                overtimeTblExis.addRestEnd(DBHandler.convertDate(rs.getDate("odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_START]), rs.getTime("odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_REST_TIME_START])));
                
                hashListsOtExist.put(rs.getLong("odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]), overtimeTblExis);
                
            }
            rs.close();
            return hashListsOtExist;

        } catch (Exception e) {

            System.out.println("Exception PstOvertimeDetail.list3"+e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return hashListsOtExist;
        
        // return list3(limitStart, recordToGet, whereClauseEmpTime, order);
     }
     
     
     public static Vector listOvertimeOverlapVer2(long employeeOid,Date selectedDateFrom, Date selectedDateTo, String order) {
         String whereClauseEmpTime = "";
         
         String whereClauseReq = "("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+
                 I_DocStatus.DOCUMENT_STATUS_CANCELLED   + ") AND ("
                 + "\""+Formater.formatDate(selectedDateTo,"yyyy-MM-dd 23:59")+"\" > odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]+ " AND odt. "+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + ">"+"\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd 00:00")+"\""+")";
         if((selectedDateFrom != null) && (selectedDateTo != null)) {
            whereClauseEmpTime = whereClauseReq;
        }
       

        if(employeeOid!=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " odt."+fieldNames[FLD_EMPLOYEE_ID]
                                 + " = '"+employeeOid+"'";
        }
        
        
         return list3(0, 0, whereClauseEmpTime, order);
     }
     /**
      * 
      * @param employeeOid
      * @param selectedDateFrom
      * @param selectedDateTo
      * @param order
      * @return 
      */
      public static Vector listOvertimeOverlapVer3(long employeeOid,Date selectedDateFrom, Date selectedDateTo, String order) {
         String whereClauseEmpTime = "";
         //melakukan cek jika selectedfrom dan enddate null
         Vector result= new Vector();
         String whereClauseReq = "("+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+
                 I_DocStatus.DOCUMENT_STATUS_CANCELLED   + ") AND ("
                // + "\""+Formater.formatDate(selectedDateTo,"yyyy-MM-dd HH:mm")+"\" > odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]+ " AND odt. "+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + ">"+"\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd HH:mm")+"\""+")";
         + "\""+Formater.formatDate(selectedDateTo,"yyyy-MM-dd 23:59")+"\" > odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]+ " AND odt. "+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + ">"+"\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd 00:00")+"\""+")";
         if((selectedDateFrom != null) && (selectedDateTo != null)) {
            whereClauseEmpTime = whereClauseReq;
        }
       

        if(employeeOid!=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " odt."+fieldNames[FLD_EMPLOYEE_ID]
                                 + " = '"+employeeOid+"'";
        }
        
        if((selectedDateFrom != null) && (selectedDateTo != null)){
         return list3(0, 0, whereClauseEmpTime, order);
        }else{
            return result;
        }
     }
    public Vector getDocStatusFor(int docType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getDocStatusName(int indexStatus) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getDocStatusName(int docType, int indexStatus) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param overtime  : overtime detail yang akan di set real in dan out
     * @param rangeInCheck :  +/- Overtime start time 
     * @param rangeOutCheck : +/- Overtime end time
     * @return 
     */
    public static String setRealTime(OvertimeDetail overtime, long rangeInCheck, long rangeOutCheck, boolean reloadOvertimeIndexMap, float minOvertimeHour,int docStatusMain) {
        if (overtime == null || overtime.getDateFrom() == null || overtime.getDateTo() == null) {
            return "Overtime detail data is not complete";
        }
        if (overtime.getStatus() ==I_DocStatus.DOCUMENT_STATUS_PAID) {
            return "Overtime detail is paid";
        }
        //update by devin 2014-02-21
         if (docStatusMain !=I_DocStatus.DOCUMENT_STATUS_PROCEED) {
            return "Overtime Status is not proced";
        }
        
//        OvertimeDetail overtimeDetailX = new OvertimeDetail();
//        if(overtime.getOID()!=0){
//            try{
//            overtimeDetailX = PstOvertimeDetail.fetchExc(overtime.getOID());
//            }catch(Exception ex){
//                System.out.println("Exception PstOvertimeDetail.fetchExc(overtime.getOID())"+ex);
//            }
//        }
        //update by satrya 2012-12-09
        long preBreakOut = 0;
           long preBreakIn=0;
           long breakDuration=0;
           //long breakDurationPersonal = 0; 
           Date preBO=null;
           //Date preBI=null;
           //float breakX =0.0f;
           // double roundBreakX=0.0;
        overtime.setDocStatusByOtMain(docStatusMain);    
        String msg = "";
    if(overtime!=null && overtime.getFlagStatus()!=2){//artinya jika tidak sama dengan 2(lewat manual attd ) maka tidak akan di aupdate
        Date inBeginCheck = new Date(overtime.getDateFrom().getTime() - rangeInCheck);
        Date inEndCheck = new Date(overtime.getDateFrom().getTime() + rangeInCheck);
        Date outBeginCheck = new Date(overtime.getDateTo().getTime() - rangeOutCheck);
        Date outEndCheck = new Date(overtime.getDateTo().getTime() + rangeOutCheck);
        //update by satrya 2012-12-04
     
        if(overtime.getFlagStatus()==0){//jika user memilih real Start OT dan real End OT automatic
        // get IN(mulai) overtime , jika kosong berarti tidak keluar kantor, jam kerja normal langsung lembur
        String order = PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " ASC "; // ambil yang paling awal
        //update by satrya 2013-01-18
        //String addWhere = " AND ("+  PstPresence.fieldNames[PstPresence.FLD_STATUS]+" IN ("+Presence.STATUS_IN+","+Presence.STATUS_OUT+"))";
        String addWhere = " AND ("+  PstPresence.fieldNames[PstPresence.FLD_STATUS]+" = "+Presence.STATUS_IN +")";
        Vector<Presence> presences = PstPresence.listAsDateTimeFromTo(0, 1, order, 0, overtime.getEmployeeId(), inBeginCheck, inEndCheck, 0, addWhere);
        if (presences != null && presences.size() > 0) {
            overtime.setRealDateFrom(((Presence) presences.get(0)).getPresenceDatetime());
        } else {
            //coba 1 perpanjang waktu sebelum IN sampai 4 jam sebelum  dan setengah jarak mulai dan berakhir overtime
            inBeginCheck = new Date(overtime.getDateFrom().getTime() - (4 * 1000 * 60 * 60)); 
            inEndCheck = new Date(overtime.getDateFrom().getTime() + (overtime.getDateTo().getTime()-overtime.getDateFrom().getTime())/2);

            presences = PstPresence.listAsDateTimeFromTo(0, 1, order, 0, overtime.getEmployeeId(), inBeginCheck, inEndCheck, 0, addWhere);
            if (presences != null && presences.size() > 0) {
                overtime.setRealDateFrom(((Presence) presences.get(0)).getPresenceDatetime());
            } else {
                //coba 1 perpanjang sebelumn waktu IN sampai 10 jam sebelum mulai di hari yang sama, karena mungkin saja kerja pagi langsung lembur tanpa out
                inBeginCheck = new Date(overtime.getDateFrom().getTime() - (10 * 1000 * 60 * 60)); 
                inEndCheck = new Date(overtime.getDateFrom().getTime() + (overtime.getDateTo().getTime()-overtime.getDateFrom().getTime())/2);
                presences = PstPresence.listAsDateTimeFromTo(0, 1, order, 0, overtime.getEmployeeId(), inBeginCheck, inEndCheck, 0, addWhere);
                if (presences != null && presences.size() > 0) {
                    overtime.setRealDateFrom(((Presence) presences.get(0)).getPresenceDatetime());
                } else {
                    //update by satrya 2012-11-25
                    //msg = "No punch in found";
                        //coba 1 perpanjang sebelumn waktu IN sampai 10 jam sebelum mulai di hari yang sama, karena mungkin saja kerja pagi langsung lembur tanpa out
                    inBeginCheck = new Date(overtime.getDateFrom().getTime() - (10 * 1000 * 60 * 60)); 
                    inEndCheck = new Date(overtime.getDateFrom().getTime() + ((overtime.getDateTo().getTime()-overtime.getDateFrom().getTime())*3)/5);
                    presences = PstPresence.listAsDateTimeFromTo(0, 1, order, 0, overtime.getEmployeeId(), inBeginCheck, inEndCheck, 0, addWhere);
                    if (presences != null && presences.size() > 0) {
                        overtime.setRealDateFrom(((Presence) presences.get(0)).getPresenceDatetime());
                    }else{
                        //update by satrya 2013-01-20
                         //jika ada karyawan yg OT jam 6 s/d jam 8 pagi dan OT lagi dari jam 17.00 s/d 20.00
                        inBeginCheck = new Date(overtime.getDateFrom().getTime() - (13 * 1000 * 60 * 60)); 
                        inEndCheck = new Date(overtime.getDateFrom().getTime() + ((overtime.getDateTo().getTime()-overtime.getDateFrom().getTime())*3)/5);
                        presences = PstPresence.listAsDateTimeFromTo(0, 1, order, 0, overtime.getEmployeeId(), inBeginCheck, inEndCheck, 0, addWhere);
                        if (presences != null && presences.size() > 0) {
                            overtime.setRealDateFrom(((Presence) presences.get(0)).getPresenceDatetime());
                        }else{
                            //update by satrya 2013 - 11- 19
                            //kasus ada yg overtime dari start plan 19-11-2013 / 20:00 : end plan 20-11-2013 / 04:00, dan attd nya jam 6 smpai 04:58
                            presences = PstPresence.listAsDateTimeFromToVer2(0,1,order, overtime.getEmployeeId(),overtime.getDateFrom(),addWhere);
                           if (presences != null && presences.size() > 0) {
                            overtime.setRealDateFrom(((Presence) presences.get(0)).getPresenceDatetime());
                          }else{
                               Date existPresenceSch1In=null;
                               if(overtime.getDateFrom()!=null){
                                   Period period =PstPeriod.getPeriodBySelectedDate(overtime.getDateFrom());
                                   int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(overtime.getDateFrom());
                                   int idxSch1OnPresenceIn = PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1;
                                  if(period!=null){
                                     existPresenceSch1In = PstEmpSchedule.getPresenceOnSchedule(idxSch1OnPresenceIn, overtime.getEmployeeId(), period.getOID());
                                  }
                               }
                               
                               if(existPresenceSch1In!=null){
                                   //karena jika tidak sesuai denganplan'nya maka akan di hitung jadinya OT'nya
                                   if(overtime.getDateFrom()!=null && overtime.getDateFrom().getTime()>=existPresenceSch1In.getTime()){
                                        overtime.setRealDateFrom(existPresenceSch1In);
                                   }
                                  
                               }else{
                                msg = "No punch in found";
                               }
                               /**
                                * //update by satrya 2013-12-03
                                * else{
                                *  msg = "No punch in found";
                                * }
                                */
                            }
                        }
                    }
                }
            }
        }
        //update by satrya 2013-01-18
        //pencarian yg lebih mendalam, jika overtime STart Real masih Null
        //jika Outnya lebih dari satu dan selisihnya > 2 menit
        if(overtime.getRealDateFrom()==null){
            order = PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " ASC "; // ambil yang paling awal
            addWhere = " AND ("+  PstPresence.fieldNames[PstPresence.FLD_STATUS]+" = "+Presence.STATUS_OUT +")";
            presences = PstPresence.listAsDateTimeFromTo(0, 0, order, 0, overtime.getEmployeeId(), inBeginCheck, inEndCheck, 0, addWhere);
           
            if (presences != null && presences.size() > 1) {
                long tmpRealDateX = 0;
                long tmpRealDateY = 0;
               
                for(int x=0; x<presences.size(); x++){
                    Presence presenceX = (Presence) presences.get(x);
                    tmpRealDateX = presenceX.getPresenceDatetime().getTime();
                    
                   if(((x+1) < presences.size())){
                         presenceX = (Presence) presences.get(x+1);
                         tmpRealDateY = presenceX.getPresenceDatetime().getTime();
                    }
                  
                  //jika hasil pengurangan tersebut harus lebih besar dari 2 menit baru di set Start Real 
                    if(tmpRealDateY!=0 && (tmpRealDateY-tmpRealDateX)> (2*60*1000)){
                         
                        overtime.setRealDateFrom(presenceX.getPresenceDatetime());
                        //break;
                    }
                }
                
            }
        }

        // get OUT(akhir) overtime , jika kosong berarti tidak ada out
        order = PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " DESC "; // ambil yang paling akhir keluar
        //update by satrya 2013-01-18
        //addWhere = " AND ("+  PstPresence.fieldNames[PstPresence.FLD_STATUS]+" IN ("+Presence.STATUS_IN+","+Presence.STATUS_OUT+"))";
        addWhere = " AND ("+  PstPresence.fieldNames[PstPresence.FLD_STATUS]+" IN ("+Presence.STATUS_OUT+"))";
        presences = PstPresence.listAsDateTimeFromTo(0, 1, order, 0, overtime.getEmployeeId(), outBeginCheck, outEndCheck, 0,addWhere );
        if (presences != null && presences.size() > 0) {
            overtime.setRealDateTo(((Presence) presences.get(0)).getPresenceDatetime());
        } else {
            // coba ambil 1 jam setelah waktu mulai dan  4 jam lebih lebar ke belakang
            outBeginCheck = new Date(overtime.getDateFrom().getTime() + ((overtime.getDateTo().getTime()-overtime.getDateFrom().getTime())/2));// (1000 * 60 * 60));
            outEndCheck = new Date(overtime.getDateTo().getTime() + 4 * (1000 * 60 * 60));
            //order = PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " DESC "; // ambil yang paling akhir keluar
            presences = PstPresence.listAsDateTimeFromTo(0, 1, order, 0, overtime.getEmployeeId(), outBeginCheck, outEndCheck, 0, addWhere);
                if (presences != null && presences.size() > 0) {
                    overtime.setRealDateTo(((Presence) presences.get(0)).getPresenceDatetime());
                } else {                               
                // coba ambil 1 jam setelah waktu mulai dan  9 jam lebih lama ke belakang
                outBeginCheck = new Date(overtime.getDateFrom().getTime() + (1000 * 60 * 60));
                outEndCheck = new Date(overtime.getDateTo().getTime() + 9  * (1000 * 60 * 60));
                //order = PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " ASC "; // ambil yang paling awal keluar
                presences = PstPresence.listAsDateTimeFromTo(0, 1, order, 0, overtime.getEmployeeId(), outBeginCheck, outEndCheck, 0, addWhere);
                if (presences != null && presences.size() > 0) {
                    overtime.setRealDateTo(((Presence) presences.get(0)).getPresenceDatetime());
                } else {
                    Date existPresenceSch1Out=null;
                               if(overtime.getDateTo()!=null){
                                   Period period =PstPeriod.getPeriodBySelectedDate(overtime.getDateTo());
                                   int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(overtime.getDateTo());
                                   int idxSch1OnPresenceOut = PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1;
                                  if(period!=null){
                                     existPresenceSch1Out = PstEmpSchedule.getPresenceOnSchedule(idxSch1OnPresenceOut, overtime.getEmployeeId(), period.getOID());
                                     //update by satrya 2013-12-05
                                     //untuk mencegah kasusnya sama dengan plan schedule start
                                     if(overtime.getEmployeeId()==10309l){
                                         boolean test=true;
                                     }
                                     if(existPresenceSch1Out!=null && overtime.getDateTo()!=null && existPresenceSch1Out.getTime()>=overtime.getDateTo().getTime() /*update by satrya 2014-06-02 +((long)minOvertimeHour * 60 * 60 * 1000L)*/){
                                         ///tidak di rubah
                                         int x=0;
                                     }else{
                                         existPresenceSch1Out=null;
                                     }
                                  }
                               }
                               
                               if(existPresenceSch1Out!=null){
                                   //update by satrya 2014-06-02
                                    //karena jika tidak sesuai denganplan'nya maka akan di hitung jadinya OT'nya
                                   if(overtime.getDateTo()!=null && existPresenceSch1Out.getTime() >= overtime.getDateTo().getTime()){
                                       overtime.setRealDateTo(existPresenceSch1Out);
                                   }else{
                                        msg = "No punch in found";
                                   }
                                   
                               }else{
                                msg = "No punch in found";
                               }
                    //update by satrya 2013-12-03
                    //msg = "No punch out found";
                }
            }
        }
        //update by satrya 2013-01-19
        //jika ada karyawan yg OT jam 6 s/d jam 8 pagi dan OT lagi dari jam 17.00 s/d 20.00
        // jika ada karyawan yg OT dari jam 5 s/d jam 8 pagi truz OT lagi dari jam 17.00 s/d 23.00
        if(overtime.getRealDateTo()==null){
            order = PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " DESC "; // ambil yang paling awal
            addWhere = " AND ("+  PstPresence.fieldNames[PstPresence.FLD_STATUS]+" = "+Presence.STATUS_OUT +")";
            outBeginCheck = new Date(overtime.getDateFrom().getTime() + (1000 * 60 * 60));
            outEndCheck = new Date(overtime.getDateTo().getTime() + 12 /*update by satrya 2014-06-02 15*/  * (1000 * 60 * 60) + ( (1000 * 60 * 60)/2));
            presences = PstPresence.listAsDateTimeFromTo(0, 0, order, 0, overtime.getEmployeeId(), outBeginCheck, outEndCheck, 0, addWhere);
            if (presences != null && presences.size() > 0) {
                Date dtPresence = ((Presence) presences.get(0)).getPresenceDatetime();
                if(dtPresence!=null && overtime.getDateTo()!=null && dtPresence.getTime() >= overtime.getDateTo().getTime()){
                    //update by satrya 2014-06-02 overtime.setRealDateTo(((Presence) presences.get(0)).getPresenceDatetime());
                    overtime.setRealDateTo(dtPresence);
                }
                    
            } 
        }

      }
      //end
       
        if( overtime.getDateFrom()!=null && overtime.getRealDateFrom()!=null && (overtime.getDateFrom().compareTo(overtime.getRealDateFrom())>0) ){
            overtime.setRealDateFrom(overtime.getDateFrom());
        }        
        if( overtime.getDateTo()!=null && overtime.getRealDateTo()!=null && (overtime.getDateTo().compareTo(overtime.getRealDateTo())<0) ){
            overtime.setRealDateTo(overtime.getDateTo());
        }
       //set rest Time
        //update by satrya 2012-11-22
     //update by satrya 2013-07-13
    //mengecek jika overtime realnya harus lebih besar atau schedulenya sama maka real timenya tidak di set null
    // if(overtime.getRealDateTo()!=null && schedule.getTimeOut > overtime.getRealDateTo().getTime()){
       //     overtime.setRealDateTo(null);
     //}
        
//     update by satrya 2014-01-24 karena istirahatnya sampai lebih dari 100 if(overtime.getRealDateFrom()!=null && overtime.getRealDateTo()!=null){
//        String orders= PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " ASC ";
//        Vector status = new Vector(1,1);
//        status.add(Presence.STATUS_OUT_PERSONAL);
//        status.add(Presence.STATUS_IN_PERSONAL);
//           Vector listPresencePersonalInOut = PstPresence.listAsDateTimeFromToWithStatus(0, 0, orders , overtime.getEmpDeptId(), overtime.getEmployeeId(), overtime.getRealDateFrom(), overtime.getRealDateTo(), 0,status); 
//           
//            //harusnya schedule OT - presencenya = total restime
//            //dan di tambahkan di menu analisa jika dia ada OT maka breaknya itu sesuai schedule OT'nya
//           if(listPresencePersonalInOut!=null && listPresencePersonalInOut.size() > 0 ){
//                         Date dtSchDateTime = null; 
//                         Date dtpresenceDateTime = null;
//                         //di update
//                         // Date dtSchDateTime = new Date(); 
//                         //Date dtpresenceDateTime = new Date();
//                      boolean flagPreBo= true;
//                     for(int idxPer = 0;idxPer < listPresencePersonalInOut.size();idxPer++){ 
//                        if(flagPreBo){
//                         Presence presenceBreakX = (Presence) listPresencePersonalInOut.get(0);//yang di cari harus ada leavenya   
//                         //update by satrya 2013-06-03
//                         if(presenceBreakX.getPresenceDatetime()!=null){
//                             presenceBreakX.getPresenceDatetime().setSeconds(0); 
//                            preBO = presenceBreakX.getPresenceDatetime();
//                         }
//                         flagPreBo=false;
//                        }
//                         Presence presenceBreak = (Presence) listPresencePersonalInOut.get(idxPer);//yang di cari harus ada leavenya 
//                         
//                         if(presenceBreak.getScheduleDatetime()!=null){
//                            dtSchDateTime = (Date)presenceBreak.getScheduleDatetime().clone();
//                           // dtSchDateTime.setHours(dtSchDateTime.getHours());
//                           // dtSchDateTime.setMinutes(dtSchDateTime.getMinutes());
//                            dtSchDateTime.setSeconds(0); 
//                           //schPresence =  presenceBreak.getScheduleDatetime().getDate(); 
//                         }
//                         if(presenceBreak.getPresenceDatetime() !=null){ 
//                                //update by satrya 2012-10-17
//                            dtpresenceDateTime = (Date)presenceBreak.getPresenceDatetime().clone();
//                            //dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
//                            //dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
//                            dtpresenceDateTime.setSeconds(0);       
//                         }
//                          if(overtime.getRestStart() !=null
//                                 && overtime.getRestEnd() !=null
//                                 && presenceBreak.getEmployeeId()==overtime.getEmployeeId()){
//                             //kenapa di buat presenceBreak.getScheduleDatetime()!=null ini berpengaruh pada DateCalc.dayDifference(presenceBreak.getScheduleDatetime() xxxx yg menyebabkan exception
//                             if(presenceBreak.getStatus()== Presence.STATUS_OUT_PERSONAL){
//                                  //update by satrya 2012-09-27
//                                if(dtpresenceDateTime!=null && (dtpresenceDateTime.getTime() < overtime.getRestStart().getTime())){ ///jika karyawan mendahului istirahat
//                                    preBreakOut = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PO 
//                                   
//                                  }else{
//                                    preBreakOut = overtime.getRestStart().getTime(); //yang di pakai mengurangi adalah schedule PO
//                                 }
//                                 //preBreakOut= presenceBreak.getPresenceDatetime().getTime();
//                                 //preBO= presenceBreak.getPresenceDatetime();
//                                 
//                                //ipoVal=1;
//                                listPresencePersonalInOut.remove(idxPer);
//                                idxPer=idxPer-1;
//                              }else if(presenceBreak.getStatus()== Presence.STATUS_IN_PERSONAL){ 
//                                  //istirahat terlamabat 
//                                 if(preBreakOut !=0L){   
//                                   if(dtpresenceDateTime!=null && dtpresenceDateTime.getTime() > overtime.getRestEnd().getTime()){ ///jika karyawan melewati jam istirahat
//                                        preBreakIn = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
//                                    }else{
//                                      preBreakIn =  overtime.getRestEnd().getTime(); //yang di pakai mengurangi adalah schedule PI
//                                    }
//                                    //preBreakIn= presenceBreak.getPresenceDatetime().getTime();
//                                    //preBI= presenceBreak.getPresenceDatetime();
//                                 
//                                 breakDurationPersonal = (breakDurationPersonal + preBreakIn) -  preBreakOut;
//                                  //preBreakOut =0L;
//                                 }
//                                 //ipiVal=1;
//                                listPresencePersonalInOut.remove(idxPer); 
//                                idxPer=idxPer-1;
//                              }//end IN personal else if
//                             
//                             
//                         }
//                          
//                           
//                     }//end for
//                      
//           } 
//            breakX = (float) breakDurationPersonal/(3600000);
//                                   String sBreakX= Formater.formatNumber(breakX, "#.##");
//                                   if(sBreakX!=null && sBreakX.length()>0){
//                                       roundBreakX = Double.parseDouble(sBreakX);
//                            }
//      }
//      //set rest Time
//        //update by satrya 2012-11-22
//        if(overtime.getRestTimeinHr()==0){
//                               if(preBreakOut!=0 && preBreakIn!=0){
//                                  // breakDurationPersonal = breakDurationPersonal + preBreakIn -  preBreakOut;
//                                   overtime.setRestTimeinHr(roundBreakX);
//                                    overtime.setRestStart(preBO);
//                                    preBreakOut=0L;
//                                    preBreakIn=0L;
//                               }
//        }else{
//           if(overtime.getRestTimeinHr()!=0 && overtime.getRestTimeinHr()>0){
//
//
//               if(preBreakOut!=0 && preBreakIn!=0){
//                   //if(roundBreakX + overtimeDetailX.getRestTimeinHr()> overtime.getRestTimeinHr()){
//                   if(roundBreakX  > overtime.getRestTimeinHr()){
//                       //masih di tanyakan karena nilainya terus bertambah jika di save n prosess
//                      //overtime.setRestTimeinHr(overtime.getRestTimeinHr());
//                      overtime.setRestTimeinHr(roundBreakX );
//                   }
//                   /*else if(roundBreakX > overtime.getRestTimeinHr()){
//
//                       overtime.setRestTimeinHr(roundBreakX);
//                   }*/
//                   //update by satrya 2013-06-03
//                  if(preBreakOut!=0 && overtime.getRestStart()!=null && preBreakOut < overtime.getRestStart().getTime()){
//                  overtime.setRestStart(preBO);
//                  }
//                  preBreakOut=0L;
//                  preBreakIn=0L;
//               }else{
//                   overtime.setRestTimeinHr( overtime.getRestTimeinHr());
//               }
//           }
//        }
   
      if(overtime.getRealDateFrom()!=null && overtime.getRealDateTo()!=null && overtime.getManualSetRestTime()==0){
        String orders= PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " ASC ";
        Vector status = new Vector(1,1);
        status.add(Presence.STATUS_OUT_PERSONAL);
        status.add(Presence.STATUS_IN_PERSONAL);
           Vector listPresencePersonalInOut = PstPresence.listAsDateTimeFromToWithStatus(0, 0, orders , overtime.getEmpDeptId(), overtime.getEmployeeId(), overtime.getRealDateFrom(), overtime.getRealDateTo(), 0,status); 
           
             //update by satrya 2012-10-10 
           
           Presence presenceBreak = new Presence();
                 if(listPresencePersonalInOut!=null && listPresencePersonalInOut.size() > 0 ){
                         //Date dtSchDateTime = null; 
                         Date dtpresenceDateTime = null;
                         
                          Date dtBreakOut=null; 
                          Date dtBreakIn=null;
                          boolean ispreBreakOutsdhdiambil = false; 
                     for(int bIdx = 0;bIdx < listPresencePersonalInOut.size();bIdx++){
                         presenceBreak = (Presence) listPresencePersonalInOut.get(bIdx);//yang di cari harus ada leavenya 
                         
//                         if(presenceBreak.getScheduleDatetime()!=null){
//                            dtSchDateTime = (Date)presenceBreak.getScheduleDatetime().clone();
//                            dtSchDateTime.setHours(dtSchDateTime.getHours());
//                            dtSchDateTime.setMinutes(dtSchDateTime.getMinutes());
//                            dtSchDateTime.setSeconds(0);                            
//                         }
                         if(presenceBreak.getPresenceDatetime() !=null){ 
                                //update by satrya 2012-10-17
                            dtpresenceDateTime = (Date)presenceBreak.getPresenceDatetime().clone();
                            dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
                            dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
                            dtpresenceDateTime.setSeconds(0);       
                         }
                         if(presenceBreak.getEmployeeId()==overtime.getEmployeeId() && overtime.getRestStart()!=null && overtime.getRestEnd()!=null
                                  && (DateCalc.dayDifference(presenceBreak.getPresenceDatetime(),overtime.getRestStart())==0 )
                                  && presenceBreak.getScheduleDatetime()== null ){ 
                              if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                                  listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                              }
                              else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                  listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                              }
                          }
                         else if(presenceBreak.getScheduleDatetime() !=null && overtime.getRestStart()!=null && overtime.getRestEnd()!=null
                                 && presenceBreak.getEmployeeId()==overtime.getEmployeeId()
                                 &&(DateCalc.dayDifference(presenceBreak.getPresenceDatetime(),overtime.getRestStart())==0 ))  
                                    //||  DateCalc.dayDifference(presenceBreak.getPresenceDatetime(),overtime.getRestEnd())==0 ))
                                {
                             //kenapa di buat presenceBreak.getScheduleDatetime()!=null ini berpengaruh pada DateCalc.dayDifference(presenceBreak.getScheduleDatetime() xxxx yg menyebabkan exception
                             if(presenceBreak.getStatus()== Presence.STATUS_OUT_PERSONAL){
                                  //update by satrya 2012-09-27
                                 //if((presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){
                                 //update by satrya 2013-07-28
                                      
                                  //jika sewaktu presence Out melewati schedule BI maka setlah presencenya
                                  //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                   //preBreakOutX  = dtpresenceDateTime==null?0:dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO  
                                  dtBreakOut = dtpresenceDateTime; 
                                  if(overtime.getRestEnd()!=null && presenceBreak.getPresenceDatetime()!=null && presenceBreak.getPresenceDatetime().getTime() > overtime.getRestEnd().getTime()){
                                      preBreakOut = presenceBreak.getPresenceDatetime().getTime();
                                      preBO=presenceBreak.getPresenceDatetime();
                                  }
                                  else if((presenceBreak.getPresenceDatetime()!=null && presenceBreak.getPresenceDatetime().getTime() < overtime.getRestStart().getTime())){ ///jika karyawan mendahului istirahat
                                      preBreakOut = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PO 
                                      preBO=presenceBreak.getPresenceDatetime();
                                  }
                                  else{
                                       preBreakOut = overtime.getRestStart().getTime(); //yang di pakai mengurangi adalah schedule PO
                                       preBO=overtime.getRestStart();
                                  }
                                 
                                  ispreBreakOutsdhdiambil = false; 
                              }else if(presenceBreak.getStatus()== Presence.STATUS_IN_PERSONAL){
                                  //istirahat terlamabat 
                                   //preBreakInX = presenceBreak.getPresenceDatetime()==null?0:presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                   dtBreakIn = presenceBreak.getPresenceDatetime();
                                 if(preBreakOut !=0L){   
                                  //update by satrya 2012-09-27
                                    //if(presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){
                                     //update by satrya 2013-07-28\
                                    //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                     
                                  if(overtime.getRestEnd()!=null && dtBreakOut!=null && dtBreakIn!=null &&
                                          dtBreakOut.getTime() > overtime.getRestEnd().getTime() && dtBreakIn.getTime() > overtime.getRestEnd().getTime()){
                                      //karena sudah pasti melewatijam istirahatnya
                                     long  tmpBreakDuration = (long)overtime.getRestTimeinHr()*60*60*1000;
                                      preBreakIn = presenceBreak.getPresenceDatetime().getTime() + tmpBreakDuration;
                                  }   
                                  else if(presenceBreak.getPresenceDatetime().getTime() > overtime.getRestEnd().getTime()){ ///jika karyawan melewati jam istirahat
                                      preBreakIn = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                  }
                                  else{
                                      preBreakIn = overtime.getRestEnd().getTime(); //yang di pakai mengurangi adalah schedule PI
                                  }  
                                   breakDuration = breakDuration + (preBreakIn -  preBreakOut);
                                
                                 ispreBreakOutsdhdiambil = true;
                                   preBreakOut =0L;
                                   
                                    //breakDuration = breakDuration + presenceBreak.getPresenceDatetime().getTime()-  preBOut.getPresenceDatetime().getTime(); 
                                   // preBOut=null;
                                  }
                                 // diffBi = diffBi+ (presenceBreak.getScheduleDatetime().getTime() - presenceBreak.getPresenceDatetime().getTime());
                                 
                              }else if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                              } else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                  listPresencePersonalInOut.remove(bIdx);                              
                                   bIdx=bIdx-1;
                              }
                             
                             if(ispreBreakOutsdhdiambil){
                                   //preBreakOutX=0L;
                                   //preBreakInX=0L;
                             }
                          // }
                              
                         }//end else if
                           
                     }//end for break In
                         //update by satrya 2012-10-18
                             //jika di loop tersebut tidak cocok maka di kurangi schedulenya
                                if(breakDuration ==0){  //&& sPresenceDateTime.equals(pSelectedDate)){
                                        try{                          
                                         breakDuration = (long)overtime.getRestTimeinHr()*60*60*1000; 
                                        }catch(Exception ex){
                                            System.out.println("Exception scheduleSymbol"+ex.toString());
                                        }
                                      }
                    }
                   //jika employee tidak ada yang keluar maka akan di potong jam istirahat default
                  else{
                    if(breakDuration ==0 ){
                        try{                          
                         breakDuration = (long)overtime.getRestTimeinHr()*60*60*1000;
                        }catch(Exception ex){
                            System.out.println("Exception scheduleSymbol"+ex.toString());
                           
                        }
                      } 
                  }
      }
      
      /** update by satrya 2014-01-24
       * //harusnya schedule OT - presencenya = total restime
            //dan di tambahkan di menu analisa jika dia ada OT maka breaknya itu sesuai schedule OT'nya
           if(listPresencePersonalInOut!=null && listPresencePersonalInOut.size() > 0 ){
                         Date dtSchDateTime = null; 
                         Date dtpresenceDateTime = null;
                         //di update
                         // Date dtSchDateTime = new Date(); 
                         //Date dtpresenceDateTime = new Date();
                      boolean flagPreBo= true;
                     for(int idxPer = 0;idxPer < listPresencePersonalInOut.size();idxPer++){ 
                        if(flagPreBo){
                         Presence presenceBreakX = (Presence) listPresencePersonalInOut.get(0);//yang di cari harus ada leavenya   
                         //update by satrya 2013-06-03
                         if(presenceBreakX.getPresenceDatetime()!=null){
                             presenceBreakX.getPresenceDatetime().setSeconds(0); 
                            preBO = presenceBreakX.getPresenceDatetime();
                         }
                         flagPreBo=false;
                        }
                         Presence presenceBreak = (Presence) listPresencePersonalInOut.get(idxPer);//yang di cari harus ada leavenya 
                         
                         if(presenceBreak.getScheduleDatetime()!=null){
                            dtSchDateTime = (Date)presenceBreak.getScheduleDatetime().clone();
                           // dtSchDateTime.setHours(dtSchDateTime.getHours());
                           // dtSchDateTime.setMinutes(dtSchDateTime.getMinutes());
                            dtSchDateTime.setSeconds(0); 
                           //schPresence =  presenceBreak.getScheduleDatetime().getDate(); 
                         }
                         if(presenceBreak.getPresenceDatetime() !=null){ 
                                //update by satrya 2012-10-17
                            dtpresenceDateTime = (Date)presenceBreak.getPresenceDatetime().clone();
                            //dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
                            //dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
                            dtpresenceDateTime.setSeconds(0);       
                         }
                          if(overtime.getRestStart() !=null
                                 && overtime.getRestEnd() !=null
                                 && presenceBreak.getEmployeeId()==overtime.getEmployeeId()){
                             //kenapa di buat presenceBreak.getScheduleDatetime()!=null ini berpengaruh pada DateCalc.dayDifference(presenceBreak.getScheduleDatetime() xxxx yg menyebabkan exception
                             if(presenceBreak.getStatus()== Presence.STATUS_OUT_PERSONAL){
                                  //update by satrya 2012-09-27
                                if(dtpresenceDateTime!=null && (dtpresenceDateTime.getTime() < overtime.getRestStart().getTime())){ ///jika karyawan mendahului istirahat
                                    preBreakOut = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PO 
                                   
                                  }else{
                                    preBreakOut = overtime.getRestStart().getTime(); //yang di pakai mengurangi adalah schedule PO
                                 }
                                 //preBreakOut= presenceBreak.getPresenceDatetime().getTime();
                                 //preBO= presenceBreak.getPresenceDatetime();
                                 
                                //ipoVal=1;
                                listPresencePersonalInOut.remove(idxPer);
                                idxPer=idxPer-1;
                              }else if(presenceBreak.getStatus()== Presence.STATUS_IN_PERSONAL){ 
                                  //istirahat terlamabat 
                                 if(preBreakOut !=0L){   
                                   if(dtpresenceDateTime!=null && dtpresenceDateTime.getTime() > overtime.getRestEnd().getTime()){ ///jika karyawan melewati jam istirahat
                                        preBreakIn = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                    }else{
                                      preBreakIn =  overtime.getRestEnd().getTime(); //yang di pakai mengurangi adalah schedule PI
                                    }
                                    //preBreakIn= presenceBreak.getPresenceDatetime().getTime();
                                    //preBI= presenceBreak.getPresenceDatetime();
                                 
                                 breakDurationPersonal = (breakDurationPersonal + preBreakIn) -  preBreakOut;
                                  //preBreakOut =0L;
                                 }
                                 //ipiVal=1;
                                listPresencePersonalInOut.remove(idxPer); 
                                idxPer=idxPer-1;
                              }//end IN personal else if
                             
                             
                         }
                          
                           
                     }//end for
                      
           } 
            breakX = (float) breakDurationPersonal/(3600000);
            String sBreakX= Formater.formatNumber(breakX, "#.##");
                if(sBreakX!=null && sBreakX.length()>0){
                    roundBreakX = Double.parseDouble(sBreakX);
                }
       */
      //set rest Time

       double roundBreakX=0;
        if(breakDuration!=0){
           double roundBreak =  (double) breakDuration/(60*60*1000);
           String sBreakX= Formater.formatNumber(roundBreak, "#.##");
               if(sBreakX!=null && sBreakX.length()>0){
                   roundBreakX = Double.parseDouble(sBreakX);
               }
        }
        if(overtime.getRestTimeinHr()==0){
                               if(preBreakOut!=0 && preBreakIn!=0 && roundBreakX!=0){
                                  // breakDurationPersonal = breakDurationPersonal + preBreakIn -  preBreakOut;
                                   overtime.setRestTimeinHr(roundBreakX);
                                    overtime.setRestStart(preBO);
                                    preBreakOut=0L;
                                    preBreakIn=0L;
                               }
       }else{
                               if(overtime.getRestTimeinHr()!=0 && overtime.getRestTimeinHr()>0){
                                 
                                 
                                    
                                   if(preBO!=null  && preBreakIn!=0){
                                       //if(roundBreakX + overtimeDetailX.getRestTimeinHr()> overtime.getRestTimeinHr()){
                                       if(roundBreakX  > overtime.getRestTimeinHr()){
                                           //masih di tanyakan karena nilainya terus bertambah jika di save n prosess
                                          //overtime.setRestTimeinHr(overtime.getRestTimeinHr());
                                          overtime.setRestTimeinHr(roundBreakX);
                                       }
                                       /*else if(roundBreakX > overtime.getRestTimeinHr()){
                                            
                                           overtime.setRestTimeinHr(roundBreakX);
                                       }*/
                                       //update by satrya 2013-06-03
                                      if(preBO!=null && overtime.getRestStart()!=null && preBO.getTime() < overtime.getRestStart().getTime()){
                                        overtime.setRestStart(preBO);
                                      }
                                      preBreakOut=0L;
                                      preBreakIn=0L;
                                   }
                                  
                               }
                            }
                   
             
       
        if(overtime.getNetDuration()>0.0 ){
          msg=SessOvertime.calcOvTmIndex(overtime, reloadOvertimeIndexMap, minOvertimeHour); 
          ///update by satrya 2012-12-20
          //// masih ada yg kurang kasusnya jika belum di approvce oleh GM harusnya belum bisa, tpi masih bisa processed
          //jika Main OT  masih final jadi tdk bisa statusnya proced 
         if(overtime.getStatus()==I_DocStatus.DOCUMENT_STATUS_FINAL && docStatusMain == I_DocStatus.DOCUMENT_STATUS_PROCEED){
            overtime.setStatus(I_DocStatus.DOCUMENT_STATUS_PROCEED);
          }
        }        
        try{
            PstOvertimeDetail.updateExc(overtime);
            
        }catch(Exception exc){
            msg=exc.toString();
        }
    }   
        return msg;
    }
    
    
    
    /**
     * //update by satrya 2013-02-02
     * ini masih belum kepakai
     * @param overtimeDetail
     * @param minOvertimeHour
     * @param docStatusMain
     * @return 
     */
    public static String setManualOt(OvertimeDetail overtimeDetail,float minOvertimeHour){
       String msg=SessOvertime.calcOvTmIndex(overtimeDetail ,true,minOvertimeHour); 

        try{
            PstOvertimeDetail.updateOTIdx(overtimeDetail);
            
        }catch(Exception exc){
            msg=exc.toString();
        }
                return msg;
    }
    /**
     * Menghitung total index overtime 
     * @param empoyeeId
     * @param periodeId
     * @return 
     */
    public static double getTotIdx(long empoyeeId, long periodeId){
        return getTotIdx(empoyeeId, periodeId, OvertimeDetail.PAID_BY_SALARY);
    }
    
    public static double getTotIdx(long empoyeeId, long periodeId, int paidBy){
		DBResultSet dbrs = null;
                double sumSalary = 0;
		try {
                        String sql = "SELECT SUM("+fieldNames[FLD_TOT_IDX]+") FROM "+
                                     TBL_OVERTIME_DETAIL+
                                     " WHERE "+fieldNames[FLD_EMPLOYEE_ID]+
                                     " = "+empoyeeId + " AND " + fieldNames[FLD_PERIOD_ID]+"="+periodeId + 
                                  "  AND "+ fieldNames[FLD_PAID_BY]+"="+paidBy ;
                      //  System.out.println("SQL getTotIdx"+sql);
                        dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
                        
                        while(rs.next()) { sumSalary = rs.getDouble(1); }
                    	rs.close();
			return sumSalary;
		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);
		}
	}
    /**
     *  keterangan: proses kalkulasi Overtime Index untuk payroll
     * @param empoyeeId
     * @param paidBy
     * @param start
     * @param end
     * @return 
     */
    public static double getCalculateTotIdx(long empoyeeId, int paidBy,Date start, Date end){
		DBResultSet dbrs = null;
                double sumSalary = 0;
                
		try {
                    if(start!=null && end!=null){
                        String sql = "SELECT SUM("+fieldNames[FLD_TOT_IDX]+") FROM "+
                                     TBL_OVERTIME_DETAIL+
                                     " WHERE "+fieldNames[FLD_EMPLOYEE_ID]+
                                     " = "+empoyeeId 
                                + " AND (( "+fieldNames[FLD_DATE_FROM]+ " BETWEEN \""+Formater.formatDate(start, "yyyy-MM-dd 00:00:00")  +"\"" +" AND \""+ Formater.formatDate(end, "yyyy-MM-dd 23:59:59") +"\") "
                                + " OR ("+fieldNames[FLD_DATE_TO] + " BETWEEN \""+ Formater.formatDate(start, "yyyy-MM-dd 00:00:00")  +"\"" +" AND \""+ Formater.formatDate(end, "yyyy-MM-dd 23:59:59") +"\" )) "
                                + "  AND "+ fieldNames[FLD_PAID_BY]+"="+paidBy + "  AND "+ fieldNames[FLD_STATUS]+"="+I_DocStatus.DOCUMENT_STATUS_PROCEED ;
                      //  System.out.println("SQL getTotIdx"+sql);
                        dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
                        
                        while(rs.next()) { sumSalary = rs.getDouble(1); }
                    	rs.close();
                }
			return sumSalary;
		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);
		}
	}

   /**
    * Ketrangan: mencari total idx
    * create by satrya 2013-05-6
    * @param departmentName
    * @param levelCode
    * @param sectionName
    * @param searchNrFrom
    * @param searchNrTo
    * @param searchName
    * @param paidBy
    * @param start
    * @param end
    * @return 
    */
     public static Vector listGetCalculateTotIdx(long departmentName,long companyId,long divisionId, String levelCode, long sectionName, String searchNrFrom, String searchNrTo, String searchName,int dataStatus, int paidBy,Date start, Date end){
		DBResultSet dbrs = null;
                Vector result = new Vector(1, 1);

                try {
                    if(start!=null && end!=null){
                        String sql = "SELECT SUM("+fieldNames[FLD_TOT_IDX]+") AS TOT_IDX,EMP."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]+
                                " FROM "+ TBL_OVERTIME_DETAIL+ " AS ODTDETAIL  "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP "
                    + " ON ODTDETAIL." + fieldNames[FLD_EMPLOYEE_ID] + " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV "
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " INNER JOIN "+ PstPosition.TBL_HR_POSITION + " AS POS ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS PAY ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    + " WHERE " 
                                + "  (( "+fieldNames[FLD_DATE_FROM]+ " BETWEEN \""+Formater.formatDate(start, "yyyy-MM-dd 00:00:00")  +"\"" +" AND \""+ Formater.formatDate(end, "yyyy-MM-dd 23:59:59") +"\") "
                                + " OR ("+fieldNames[FLD_DATE_TO] + " BETWEEN \""+ Formater.formatDate(start, "yyyy-MM-dd 00:00:00")  +"\"" +" AND \""+ Formater.formatDate(end, "yyyy-MM-dd 23:59:59") +"\" )) "
                                + "  AND "+ fieldNames[FLD_PAID_BY]+"="+paidBy + "  AND ODTDETAIL."+ fieldNames[FLD_STATUS]+"="+I_DocStatus.DOCUMENT_STATUS_PROCEED ;
            
            String whereClause = "";

            if (departmentName > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + departmentName + " AND ";
            }

            //System.out.println("department"+srcEmployee.getDepartment());

            if (levelCode != null && !levelCode.equals("") && !levelCode.equals("0")) {
                whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                        + " LIKE '%" + levelCode.trim() + "%' AND ";
            }

            if (sectionName > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + sectionName + " AND ";

            }
            
            if (companyId > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                        + " = " + companyId + " AND ";

            }
            
            if (divisionId > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + divisionId + " AND ";

            }
            if ((searchNrFrom != null) && (searchNrFrom.length() > 0)) {
                if(searchNrTo==null || searchNrTo.length()==0){
                    searchNrTo =searchNrFrom;
                }
                Vector vectNrFrom = logicParser(searchNrFrom);
                if (vectNrFrom != null && vectNrFrom.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectNrFrom.size(); i++) {
                        String str = (String) vectNrFrom.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " BETWEEN '" + searchNrFrom + "' AND '" + searchNrTo + "'";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }


            if ((searchName != null) && (searchName.length() > 0)) {
                Vector vectName = logicParser(searchName);
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }
              if (dataStatus < 2) {
                whereClause = whereClause + " PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_STATUS]
                        + " = " + dataStatus + " AND ";

            }
            
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                
            }
            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];


                      //  System.out.println("SQL getTotIdx"+sql);
                        dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
                        
                        while(rs.next()) {
                            OvertimeDetail overtimeDetail= new OvertimeDetail();
                            overtimeDetail.setEmployeeId(rs.getLong(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]));
                            overtimeDetail.setTot_Idx(rs.getDouble(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_TOT_IDX]));
                            result.add(overtimeDetail);
                        }
                    	rs.close();
                }
			return result;
		}catch(Exception e) {
			return new Vector();
		}finally {
			DBResultSet.close(dbrs);
		}
	}
     

    public static double getTotDuration(long empoyeeId, long periodeId,int paidBy){
		DBResultSet dbrs = null;
                double sumSalary = 0;
		try {
                        String sql = "SELECT SUM( TIME_TOSEC(TIMEDIFF("+fieldNames[FLD_DATE_TO]+","+fieldNames[FLD_DATE_FROM]+"))/60 ) FROM "+
                                     TBL_OVERTIME_DETAIL+
                                     " WHERE "+fieldNames[FLD_EMPLOYEE_ID]+
                                     " = "+empoyeeId + " AND " + fieldNames[FLD_PERIOD_ID]+"="+periodeId +
                                     " AND "+ fieldNames[FLD_PAID_BY]+"="+paidBy;
                      //  System.out.println("SQL getTotIdx"+sql);
                        dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
                        
                        while(rs.next()) { sumSalary = rs.getDouble(1); }
                    	rs.close();
			return sumSalary;
		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);
		}
	}
    
public static double calcMealsAllowance(long empoyeeId, long periodeId){
   return calcMealsAllowance(empoyeeId, periodeId, -1,null,null);
}
/**
 * Keterangan: untuk kalkulasi uang makan di payroll
 * @param empoyeeId
 * @param periodeId
 * @param status
 * @param start
 * @param end
 * @return 
 */
public static double calcMealsAllowance(long empoyeeId, long periodeId, int status,Date start, Date end){
    //update by satrya 2013-02-21
    //public static double calcMealsAllowance(long empoyeeId, long periodeId, int status)
		DBResultSet dbrs = null;
                double sumSalary = 0;
		try {
                    if(start!=null && end!=null ){
                        String sql = "SELECT COUNT("+fieldNames[FLD_ALLOWANCE]+") FROM "+
                                     TBL_OVERTIME_DETAIL+
                                     " WHERE "+fieldNames[FLD_EMPLOYEE_ID]+
                                     " = "+empoyeeId 
                                //+ " AND " + fieldNames[FLD_PERIOD_ID]+"="+periodeId +
                                + " AND (( "+fieldNames[FLD_DATE_FROM]+ " BETWEEN \""+Formater.formatDate(start, "yyyy-MM-dd 00:00:00")  +"\"" +" AND \""+ Formater.formatDate(end, "yyyy-MM-dd 23:59:59") +"\") "
                                + " OR ("+fieldNames[FLD_DATE_TO] + " BETWEEN \""+ Formater.formatDate(start, "yyyy-MM-dd 00:00:00")  +"\"" +" AND \""+ Formater.formatDate(end, "yyyy-MM-dd 23:59:59") +"\" )) "
                                + " AND "+ fieldNames[FLD_ALLOWANCE]+"="+Overtime.ALLOWANCE_MONEY +
                                (status> -1 ? ( " AND " + fieldNames[FLD_STATUS]+"="+status ):("")  );
                      //  System.out.println("SQL getTotIdx"+sql);
                        dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
                        
                        while(rs.next()) { sumSalary = rs.getDouble(1); }
                    	rs.close();
                }
			return sumSalary;
		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);
		}
	}

/**
 * Create by satrya 2013-02-20
 * Keterangan: mencari overtime 
 * @param dateOfMonth
 * @param periodId
 * @param empId
 * @return 
 */
    public static double getOvtDuration(String dateOfMonth,long periodId,long empId){
        DBResultSet dbrs = null;
        double result = 0;
        try{
	        String sql = "SELECT "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVT_DURATION] +
			     " FROM " +PstOvertimeDetail.TBL_OVERTIME_DETAIL + 
                             " WHERE " +PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_PERIOD_ID]+
                             " = "+periodId+
                             " AND (DAYOFMONTH("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]+
                             ")) = "+dateOfMonth+
                             " AND "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]+
                             " = '"+empId+"'";
                    
                    //System.out.println("sqlgetOvtDuration   "+sql);
                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();
                   // System.out.println("sql   "+sql);
                    while(rs.next()) { 
                        result = rs.getDouble(1);}
                    rs.close();
	        
        }catch(Exception e){
            System.out.println("Error");
        }
        return result;
	}
    /**
     * Create by satrya 2013-02-20
     * Keterangan: mencari totral DateOvertime
     * @param dates
     * @param periodId
     * @param empNum
     * @param minOvtmHours
     * @return 
     */
public static int getTotalDatesOverTm(Vector dates, long periodId, long empId, double minOvtmHours){
        int tl=0;
        ///karena belum tahu berapa min dia harus OT
        minOvtmHours = 0.0;
        if((dates==null) || (dates.size()<1) )
            return tl;
        
        for (int i=0;i< dates.size();i++){
            Date dt = (Date) dates.get(i);
            double ovtLen = PstOvertimeDetail.getOvtDuration(""+dt.getDate(),periodId,empId);
            if(ovtLen!=0.0 && ovtLen>=minOvtmHours){
                // if(ovtLen>=minOvtmHours){
                tl++;
            }            
        }                
        return tl;
    }
    
}

        