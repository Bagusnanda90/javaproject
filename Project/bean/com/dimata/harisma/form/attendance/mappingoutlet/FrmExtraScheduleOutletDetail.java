/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.attendance.mappingoutlet;

import com.dimata.harisma.entity.attendance.mappingoutlet.ExtraScheduleOutletDetail;
import com.dimata.harisma.entity.attendance.mappingoutlet.ExtraScheduleOutletDetail;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.util.Date;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Satrya Ramayu
 */
public class FrmExtraScheduleOutletDetail extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private ExtraScheduleOutletDetail extraScheduleOutletDetail;

	public static final String FRM_NAME_EXTRA_SCHEDULE_OUTLET_DETAIL	 =  "FRM_NAME_EXTRA_SCHEDULE_OUTLET_DETAIL" ;

    public static final int FRM_EXTRA_SCHEDULE_MAPPING_ID = 0;
    private Vector mappingExtraSchedule = new Vector();
    private String msgFrm = "";
    public static final int FRM_FLD_EXTRA_SCHEDULE_MAPPING_DETAIL_ID=0;
    public static final int FRM_FLD_EXTRA_SCHEDULE_MAPPING_ID=1;
    public static final int FRM_FLD_EMPLOYEE_ID=2;
    public static final int FRM_FLD_START_DATE_PLAN=3;
    public static final int FRM_FLD_END_DATE_PLAN=4;
    public static final int FRM_FLD_START_DATE_REAL=5;
    public static final int FRM_FLD_END_DATE_REAL=6;
    public static final int FRM_FLD_REST_TIME_START=7;
    public static final int FRM_FLD_REST_TIME_HR=8;
    public static final int FRM_FLD_JOB_DESCH=9;
    //public static final int FRM_FLD_TYPE_OFF_SCHEDULE=10;
    public static final int FRM_FLD_LOCATION_ID=10;
    public static final int FRM_FLD_STATUS_DOCUMENT=11;
    public static final int FRM_FLD_POSITION_ID=12;

	public static String[] fieldNames = {
            "FRM_FLD_EXTRA_SCHEDULE_MAPPING_DETAIL_ID",
            "FRM_FLD_EXTRA_SCHEDULE_MAPPING_ID",
            "FRM_FLD_EMPLOYEE_ID",
            "FRM_FLD_START_DATE_PLAN",
            "FRM_FLD_END_DATE_PLAN",
            "FRM_FLD_START_DATE_REAL",
            "FRM_FLD_END_DATE_REAL",
            "FRM_FLD_REST_TIME_START",
            "FRM_FLD_REST_TIME_HR",
            "FRM_FLD_JOB_DESCH",
            //"FRM_FLD_TYPE_OFF_SCHEDULE",
            "FRM_FLD_LOCATION_ID",
            "FRM_FLD_STATUS_DOCUMENT",
            "FRM_FLD_POSITION_ID"
	} ;

	public static int[] fieldTypes = {
            //TYPE_LONG,//"FRM_EXTRA_SCHEDULE_MAPPING_ID",
            
            TYPE_LONG,//"FRM_FLD_EXTRA_SCHEDULE_MAPPING_DETAIL_ID",
            TYPE_LONG,//"FRM_FLD_EXTRA_SCHEDULE_MAPPING_ID",
            TYPE_LONG,//"FRM_FLD_EMPLOYEE_ID",
            TYPE_DATE,//"FRM_FLD_START_DATE_PLAN",
            TYPE_DATE,//"FRM_FLD_END_DATE_PLAN",
            TYPE_DATE,//"FRM_FLD_START_DATE_REAL",
            TYPE_DATE,//"FRM_FLD_END_DATE_REAL",
            TYPE_DATE,//"FRM_FLD_REST_TIME_START",
            TYPE_FLOAT,//"FRM_FLD_REST_TIME_HR",
            TYPE_STRING,//"FRM_FLD_JOB_DESCH",
            //TYPE_INT,//"FRM_FLD_TYPE_OFF_SCHEDULE",
            TYPE_LONG,//"FRM_FLD_LOCATION_ID",
            TYPE_INT,//"FRM_FLD_STATUS_DOCUMENT"
            TYPE_LONG,//FRM_FLD_POSITION_ID
	};

	public FrmExtraScheduleOutletDetail(){
	}
	public FrmExtraScheduleOutletDetail(ExtraScheduleOutletDetail objExtraScheduleOutletDetail){
		this.extraScheduleOutletDetail = objExtraScheduleOutletDetail;
	}

	public FrmExtraScheduleOutletDetail(HttpServletRequest request, ExtraScheduleOutletDetail objExtraScheduleOutletDetail){
		super(new FrmExtraScheduleOutletDetail(objExtraScheduleOutletDetail), request);
		this.extraScheduleOutletDetail = objExtraScheduleOutletDetail;
	}

	public String getFormName() { return FRM_NAME_EXTRA_SCHEDULE_OUTLET_DETAIL; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public ExtraScheduleOutletDetail getEntityObject(){ return extraScheduleOutletDetail; }

	public void requestEntityObject(ExtraScheduleOutletDetail objExtraScheduleOutletDetail) {
		try{
            this.requestParam();
            objExtraScheduleOutletDetail.setExtraScheduleMappingId(getLong(FRM_FLD_EXTRA_SCHEDULE_MAPPING_ID));
            
            objExtraScheduleOutletDetail.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
            objExtraScheduleOutletDetail.setStartDatePlan(getDate(FRM_FLD_START_DATE_PLAN));
            objExtraScheduleOutletDetail.setEndDatePlan(getDate(FRM_FLD_END_DATE_PLAN));
            objExtraScheduleOutletDetail.setStartDateReal(getDate(FRM_FLD_START_DATE_REAL));
            objExtraScheduleOutletDetail.setEndDateReal(getDate(FRM_FLD_END_DATE_REAL));
            objExtraScheduleOutletDetail.setRestTimeStart(getDate(FRM_FLD_REST_TIME_START));
            objExtraScheduleOutletDetail.setRestTimeHr(getFloat(FRM_FLD_REST_TIME_HR));
            objExtraScheduleOutletDetail.setJobDesc(getString(FRM_FLD_JOB_DESCH));
            //objExtraScheduleOutletDetail.setTypeOffSchedule(getInt(FRM_FLD_TYPE_OFF_SCHEDULE));
            objExtraScheduleOutletDetail.setLocationId(getLong(FRM_FLD_LOCATION_ID));
            objExtraScheduleOutletDetail.setStatusDocDetail(getInt(FRM_FLD_STATUS_DOCUMENT));
             //update by satrya 2014-08-01
            objExtraScheduleOutletDetail.setPositionId(getLong(FRM_FLD_POSITION_ID));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
        
        public Vector<ExtraScheduleOutletDetail> requestEntityObjectMultiple() {
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
                                ExtraScheduleOutletDetail objExtraScheduleOutletDetail = new ExtraScheduleOutletDetail();
                                
                                objExtraScheduleOutletDetail.setEmployeeId(empId);
                                objExtraScheduleOutletDetail.setOID(oid);
                                objExtraScheduleOutletDetail.setExtraScheduleMappingId(getParamLong(fieldNames[FRM_FLD_EXTRA_SCHEDULE_MAPPING_ID]+"_"+empId));
            
                                objExtraScheduleOutletDetail.setEmployeeId(getParamLong(fieldNames[FRM_FLD_EMPLOYEE_ID]+"_"+empId));
                                
                                Date selectedDateFrom = getParamDateVer2(fieldNames[FRM_FLD_START_DATE_PLAN]+"_"+empId);
                                Date selectedDateTo = getParamDateVer2(fieldNames[FRM_FLD_END_DATE_PLAN]+"_"+empId);
                                if (selectedDateFrom != null && selectedDateTo != null && selectedDateFrom.getTime() > selectedDateTo.getTime()) {
                                    Date tempFromDate = selectedDateFrom;
                                    Date tempToDate = selectedDateTo;
                                    selectedDateFrom = tempToDate;
                                    selectedDateTo = tempFromDate;
                                }
                                objExtraScheduleOutletDetail.setStartDatePlan(selectedDateFrom);
                                objExtraScheduleOutletDetail.setEndDatePlan(selectedDateTo);
                                //objExtraScheduleOutletDetail.setStartDateReal(getDate(fieldNames[FRM_FLD_START_DATE_REAL]+"_"+empId));
                                //objExtraScheduleOutletDetail.setEndDateReal(getDate(fieldNames[FRM_FLD_END_DATE_REAL]+"_"+empId));
                                objExtraScheduleOutletDetail.setRestTimeStart(getParamDateVer2(fieldNames[FRM_FLD_REST_TIME_START]+"_"+empId));
                                objExtraScheduleOutletDetail.setRestTimeHr(getParamFloat(fieldNames[FRM_FLD_REST_TIME_HR]+"_"+empId)); 
                                objExtraScheduleOutletDetail.setJobDesc(getParamString(fieldNames[FRM_FLD_JOB_DESCH]+"_"+empId));
                                //objExtraScheduleOutletDetail.setTypeOffSchedule(getInt(FRM_FLD_TYPE_OFF_SCHEDULE));
                                objExtraScheduleOutletDetail.setLocationId(getParamLong(fieldNames[FRM_FLD_LOCATION_ID]+"_"+empId));
                                objExtraScheduleOutletDetail.setStatusDocDetail(getParamInt(fieldNames[FRM_FLD_STATUS_DOCUMENT]+"_"+empId));
                                
                                 //update by satrya 2014-08-01
                                objExtraScheduleOutletDetail.setPositionId(getParamLong(fieldNames[FRM_FLD_POSITION_ID]+"_"+empId));
                               
                                
                                getMappingExtraSchedule().add(objExtraScheduleOutletDetail);
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
        return getMappingExtraSchedule();
    }

    /**
     * @return the mappingExtraSchedule
     */
    public Vector getMappingExtraSchedule() {
        return mappingExtraSchedule;
    }

    /**
     * @param mappingExtraSchedule the mappingExtraSchedule to set
     */
    public void setMappingExtraSchedule(Vector mappingExtraSchedule) {
        this.mappingExtraSchedule = mappingExtraSchedule;
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
}
