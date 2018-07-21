/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.form.overtime;

/* java package */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.SimpleDateFormat;
/* qdep package */
import com.dimata.qdep.form.*;

import com.dimata.harisma.entity.overtime.*;
/**
 *
 * @author Wiweka
 */
public class FrmOvertimeDetail extends FRMHandler implements /*I_Language,*/I_FRMInterface, I_FRMType{
    private OvertimeDetail overtimeDetail;
    public static final String FRM_OVERTIME_DETAIL="FRM_OVERTIME_DETAIL";
    
    private Vector vOvertime = new Vector();
    private String msgFrm = "";
    
    public static final int FRM_FIELD_OVERTIME_DETAIL_ID=0;
    public static final int FRM_FIELD_OVERTIME_ID=1;
    public static final int FRM_FIELD_EMPLOYEE_ID=2;    
    public static final int FRM_FIELD_PAYROLL=3;    // hanya untuk tambilan tidak di simpan di database, sdh ada employee_id
    public static final int FRM_FIELD_NAME=4;    // hanya untuk tambilan tidak di simpan di database, sdh ada employee_id
    public static final int FRM_FIELD_JOBDESK=5;
    public static final int FRM_FIELD_DATE_FROM=6;
    public static final int FRM_FIELD_DATE_TO=7;
    public static final int FRM_FIELD_STATUS=8;
    public static final int FRM_FIELD_PAID_BY=9;
    public static final int FRM_FIELD_ALLOWANCE=10;
    public static final int FRM_FIELD_REST_TIME_HR=11;
    public static final int FRM_FIELD_REST_TIME_START=12;
    //update by satrya 2012-12-04
    public static final int FRM_FIELD_REAL_TIME_START=13;
    public static final int FRM_FIELD_REAL_TIME_END=14;
    public static final int FRM_FIELD_FLAG_STATUS=15;
    //update by satrya 2014-01-24
    public static final int FRM_FIELD_MANUAL_SET_REST_TIME=16;
    
    //update by satrya 2014-05-26
    public static final int FRM_FIELD_LOCATION_ID=17;
    public static final int FRM_FIELD_POSITION_ID=18;

    public static final String[] fieldNames={
        "FRM_FIELD_OVERTIME_DETAIL_ID",
        "FRM_FIELD_OVERTIME_ID",
        "FRM_FIELD_EMPLOYEE_ID",        
        "FRM_FIELD_PAYROLL",        
        "FRM_FIELD_NAME",
        "FRM_FIELD_JOBDESK",
        "FRM_FIELD_DATE_FROM",
        "FRM_FIELD_DATE_TO",
        "FRM_FIELD_STATUS",
        "FRM_FIELD_PAID_BY",
        "FRM_FIELD_ALLOWANCE",
        "FRM_FIELD_REST_TIME_HR",
        "FRM_FIELD_REST_TIME_START",
        "FRM_FIELD_REAL_TIME_START",
        "FRM_FIELD_REAL_TIME_END",
        "FRM_FIELD_FLAG_STATUS",
        //UPDATE BY SATRYA 2014-01-24
        "FRM_FIELD_MANUAL_SET_REST_TIME",
        //UPDATE BY SATRYA 2014-05-26
        "FRM_FIELD_LOCATION_ID",
        "FRM_FIELD_POSITION_ID"
    };

    public static final int[] fieldTypes={
        TYPE_LONG,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG  + ENTRY_REQUIRED,        
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE  + ENTRY_REQUIRED,
        TYPE_DATE  + ENTRY_REQUIRED,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT,
        //UPDATE BY SATRYA 2014-01-24
        TYPE_INT,
        TYPE_LONG,
           //UPDATE BY SATRYA 2014-08-05
        TYPE_LONG
    };




public FrmOvertimeDetail(){
	}
	public FrmOvertimeDetail(OvertimeDetail overtimeDetail){
		this.overtimeDetail = overtimeDetail;
	}

	public FrmOvertimeDetail(HttpServletRequest request, OvertimeDetail overtimeDetail){
		super(new FrmOvertimeDetail(overtimeDetail), request);
		this.overtimeDetail = overtimeDetail;
	}

	public String getFormName() { return FRM_OVERTIME_DETAIL; }

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; }

	public int getFieldSize() { return fieldNames.length; }

	public OvertimeDetail getEntityObject(){ return overtimeDetail; }
//update by satrya 2012-12-04
	public void requestEntityObjectManualy(OvertimeDetail overtimeDetail) {
		try{
			this.requestParam();
                        //update by satrya 2014-01-24
                           overtimeDetail.setManualSetRestTime(getInt(FRM_FIELD_MANUAL_SET_REST_TIME));
                           
                        //update by satrya 2012-12-04
                        long lSelectRealDateFrom = 0;
                        Date selecteDateRealFrom = null;
                        long lSelectRealDateTo = 0;
                        Date selecteDateRealTo = null;
                        String sSelectedDateFrom = this.getParamString("selectRealDateFrom");
                        String sSelectedDateTo = this.getParamString("selectRealDateTo");
                        if(sSelectedDateFrom!=null && sSelectedDateFrom.length()>0){
                        lSelectRealDateFrom = Long.parseLong(sSelectedDateFrom);
                        selecteDateRealFrom = new Date(lSelectRealDateFrom);
                        }
                        if(sSelectedDateTo!=null && sSelectedDateTo.length()>0){
                        lSelectRealDateTo = Long.parseLong(sSelectedDateTo);
                        selecteDateRealTo = new Date(lSelectRealDateTo);
                        }
                        if(getLong(FRM_FIELD_OVERTIME_ID)!=0){
			overtimeDetail.setOvertimeId(getLong(FRM_FIELD_OVERTIME_ID));}
                        if(getLong(FRM_FIELD_EMPLOYEE_ID)!=0){
			overtimeDetail.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));}
                         if(getString(FRM_FIELD_PAYROLL)!=null && getString(FRM_FIELD_PAYROLL).length()>0){
                        overtimeDetail.setPayroll(getString(FRM_FIELD_PAYROLL));}
                       if(getString(FRM_FIELD_NAME)!=null && getString(FRM_FIELD_NAME).length()>0){
                        overtimeDetail.setName(getString(FRM_FIELD_NAME));}
                        if(getString(FRM_FIELD_JOBDESK)!=null && getString(FRM_FIELD_JOBDESK).length()>0){
                        overtimeDetail.setJobDesk(getString(FRM_FIELD_JOBDESK));}
                        if(getDate(FRM_FIELD_DATE_FROM)!=null){
                        overtimeDetail.setDateFrom(getDate(FRM_FIELD_DATE_FROM));}
                        if(getDate(FRM_FIELD_DATE_TO)!=null){
                        overtimeDetail.setDateTo(getDate(FRM_FIELD_DATE_TO));}
                         if(getInt(FRM_FIELD_STATUS)!=0){
                        overtimeDetail.setStatus(getInt(FRM_FIELD_STATUS));}
                         if(getInt(FRM_FIELD_PAID_BY)!=0){
                        overtimeDetail.setPaidBy(getInt(FRM_FIELD_PAID_BY));}
                          if(getInt(FRM_FIELD_ALLOWANCE)!=0){
                        overtimeDetail.setAllowance(getInt(FRM_FIELD_ALLOWANCE));}
                            if(getDate(FRM_FIELD_REST_TIME_HR)!=null){
                        overtimeDetail.setRestTimeinHr(getDouble(FRM_FIELD_REST_TIME_HR));}
                           if(getDate(FRM_FIELD_REST_TIME_START)!=null){  
                        overtimeDetail.setRestStart(getDate(FRM_FIELD_REST_TIME_START));}
                        //update by satrya 2012-12-04
                            
                        overtimeDetail.setRealDateFrom(selecteDateRealFrom);
                             
                        overtimeDetail.setRealDateTo(selecteDateRealTo);
                               if(getInt(FRM_FIELD_FLAG_STATUS)!=0){  
                        overtimeDetail.setFlagStatus(getInt(FRM_FIELD_FLAG_STATUS));}
                        overtimeDetail.setLocationId(getLong(FRM_FIELD_LOCATION_ID));
                        //update by satrya 2014-08-05
                        overtimeDetail.setPositionId(getLong(FRM_FIELD_POSITION_ID));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
        
        public void requestEntityObject(OvertimeDetail overtimeDetail) {
		try{
			this.requestParam();
                        //update by satrya 2014-01-24
                           overtimeDetail.setManualSetRestTime(getInt(FRM_FIELD_MANUAL_SET_REST_TIME));
                           
                        //update by satrya 2012-12-04
                        if(getLong(FRM_FIELD_OVERTIME_ID)!=0){
			overtimeDetail.setOvertimeId(getLong(FRM_FIELD_OVERTIME_ID));}
                        if(getLong(FRM_FIELD_EMPLOYEE_ID)!=0){
			overtimeDetail.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));}
                         if(getString(FRM_FIELD_PAYROLL)!=null && getString(FRM_FIELD_PAYROLL).length()>0){
                        overtimeDetail.setPayroll(getString(FRM_FIELD_PAYROLL));}
                       if(getString(FRM_FIELD_NAME)!=null && getString(FRM_FIELD_NAME).length()>0){
                        overtimeDetail.setName(getString(FRM_FIELD_NAME));}
                        if(getString(FRM_FIELD_JOBDESK)!=null && getString(FRM_FIELD_JOBDESK).length()>0){
                        overtimeDetail.setJobDesk(getString(FRM_FIELD_JOBDESK));}
                        if(getDate(FRM_FIELD_DATE_FROM)!=null){
                        overtimeDetail.setDateFrom(getDate(FRM_FIELD_DATE_FROM));}
                        if(getDate(FRM_FIELD_DATE_TO)!=null){
                        overtimeDetail.setDateTo(getDate(FRM_FIELD_DATE_TO));}
                         if(getInt(FRM_FIELD_STATUS)!=0){
                        overtimeDetail.setStatus(getInt(FRM_FIELD_STATUS));}
                         //karena paid by ketika di rubah menjadi salary di form tidak bisa dari DP
                         //update by satrya 2013-08-13
                        // if(getInt(FRM_FIELD_PAID_BY)!=0){
                        overtimeDetail.setPaidBy(getInt(FRM_FIELD_PAID_BY));
                         //}
                        //karena paid by ketika di rubah menjadi food di form tidak bisa dari salary
                         //update by satrya 2013-08-13
                         // if(getInt(FRM_FIELD_ALLOWANCE)!=0){
                        overtimeDetail.setAllowance(getInt(FRM_FIELD_ALLOWANCE));
                        //}
                            if(getDate(FRM_FIELD_REST_TIME_HR)!=null){
                        overtimeDetail.setRestTimeinHr(getDouble(FRM_FIELD_REST_TIME_HR));}
                           if(getDate(FRM_FIELD_REST_TIME_START)!=null){  
                        overtimeDetail.setRestStart(getDate(FRM_FIELD_REST_TIME_START));}
                        //update by satrya 2012-12-04
                            if(getDate(FRM_FIELD_REAL_TIME_START)!=null){  
                        overtimeDetail.setRealDateFrom(getDate(FRM_FIELD_REAL_TIME_START));}
                              if(getDate(FRM_FIELD_REAL_TIME_END)!=null){  
                        overtimeDetail.setRealDateTo(getDate(FRM_FIELD_REAL_TIME_END));}
                               if(getInt(FRM_FIELD_FLAG_STATUS)!=0){  
                        overtimeDetail.setFlagStatus(getInt(FRM_FIELD_FLAG_STATUS));}
                        
                        overtimeDetail.setLocationId(getInt(FRM_FIELD_LOCATION_ID));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
     public static String getStringOfDate(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     * @return the vOvertime
     */
    public Vector getvOvertime() {
        return vOvertime;
    }

    /**
     * @param vOvertime the vOvertime to set
     */
    public void setvOvertime(Vector vOvertime) {
        this.vOvertime = vOvertime;
    }

    /**
     * @return the msgFrm
     */
    public String getMsgFrm() {
        return msgFrm;
    }

    /**
     * @param msgFrm the msgFrm to set
     */
    public void setMsgFrm(String msgFrm) {
        this.msgFrm = msgFrm;
    }
    
       public Vector<OvertimeDetail> requestEntityObjectMultiple() {
        try {
            this.requestParam();

            String[] userSelect = null;
            userSelect = this.getParamsStringValues("userSelected");
       
            if (userSelect != null && userSelect.length > 0) {
                
                try {
                    for (int i = 0; i < userSelect.length; i++) {
                        
                        long empId = Long.parseLong((userSelect[i].split("_")[0]));
                        long oid = Long.parseLong((userSelect[i].split("_")[1]));
                        if (empId != 0) {

                            try {
                                OvertimeDetail objOvertimeDetail = new OvertimeDetail();
                                
                                objOvertimeDetail.setEmployeeId(empId);
                                objOvertimeDetail.setOID(oid);
                                objOvertimeDetail.setOvertimeId(getParamLong(fieldNames[FRM_FIELD_OVERTIME_ID]+"_"+empId));
            
                                objOvertimeDetail.setEmployeeId(getParamLong(fieldNames[FRM_FIELD_EMPLOYEE_ID]+"_"+empId));
                                
                                Date selectedDateFrom = getParamDateVer2(fieldNames[FRM_FIELD_DATE_FROM]+"_"+empId);
                                Date selectedDateTo = getParamDateVer2(fieldNames[FRM_FIELD_DATE_TO]+"_"+empId);
                                if (selectedDateFrom != null && selectedDateTo != null && selectedDateFrom.getTime() > selectedDateTo.getTime()) {
                                    Date tempFromDate = selectedDateFrom;
                                    Date tempToDate = selectedDateTo;
                                    selectedDateFrom = tempToDate;
                                    selectedDateTo = tempFromDate;
                                }
                                objOvertimeDetail.setDateFrom(selectedDateFrom);
                                objOvertimeDetail.setDateTo(selectedDateTo);
                                //objExtraScheduleOutletDetail.setStartDateReal(getDate(fieldNames[FRM_FLD_START_DATE_REAL]+"_"+empId));
                                //objExtraScheduleOutletDetail.setEndDateReal(getDate(fieldNames[FRM_FLD_END_DATE_REAL]+"_"+empId));
                                objOvertimeDetail.setRestStart(getParamDateVer2(fieldNames[FRM_FIELD_REST_TIME_START]+"_"+empId));
                                objOvertimeDetail.setRestTimeinHr(getParamFloat(fieldNames[FRM_FIELD_REST_TIME_HR]+"_"+empId)); 
                                objOvertimeDetail.setJobDesk(getParamString(fieldNames[FRM_FIELD_JOBDESK]+"_"+empId));
                                //objExtraScheduleOutletDetail.setTypeOffSchedule(getInt(FRM_FLD_TYPE_OFF_SCHEDULE));
                                objOvertimeDetail.setLocationId(getParamLong(fieldNames[FRM_FIELD_LOCATION_ID]+"_"+empId));
                                objOvertimeDetail.setPositionId(getParamLong(fieldNames[FRM_FIELD_POSITION_ID]+"_"+empId));
                                objOvertimeDetail.setStatus(getParamInt(fieldNames[FRM_FIELD_STATUS]+"_"+empId));
                                
                               
                                
                                getvOvertime().add(objOvertimeDetail);
                            } catch (Exception e) {
                                System.out.println("Exception " + e.toString());
                            }

                        }
                    }
                } catch (Exception exc) {
                    System.out.println("Exc frmMappingOutlet" + exc);
                }
            } else {
                this.setMsgFrm("Employee can not selected"); 
            }
        } catch (Exception exc) {
            System.out.println("Exception rs to frmEmployeeOutlet" + exc);
        }
        return getvOvertime();
    }


}
