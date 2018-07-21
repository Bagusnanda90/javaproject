/*
 * FrmLeaveApplication.java
 *
 * Created on October 27, 2004, 11:52 AM
 */

package com.dimata.harisma.form.leave;
  
import javax.servlet.http.*;

/* qdep package */
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.leave.*;
import java.util.Date;

/**
 *
 * @author  gedhy
 */
public class FrmLeaveApplication extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private LeaveApplication leaveApplication;

    public static final String FRM_LEAVE_APPLICATION = "FRM_LEAVE_APPLICATION";  
    
    public static final int FRM_FLD_LEAVE_APPLICATION_ID = 0;    
    public static final int FRM_FLD_SUBMISSION_DATE = 1;
    public static final int FRM_FLD_EMPLOYEE_ID = 2;
    public static final int FRM_FLD_LEAVE_REASON = 3;
    public static final int FRM_FLD_DEP_HEAD_APPROVAL = 4;
    public static final int FRM_FLD_HR_MAN_APPROVAL = 5;    
    public static final int FRM_FLD_DOC_STATUS = 6; 
    public static final int FRM_FLD_DEP_HEAD_APPROVE_DATE = 7;
    public static final int FRM_FLD_HR_MAN_APPROVE_DATE = 8;        
    public static final int FRM_FLD_START_DATE_AL = 9;
    public static final int FRM_FLD_END_DATE_AL = 10;
    public static final int FRM_FLD_START_DATE_LL = 11;
    public static final int FRM_FLD_END_DATE_LL = 12;
    public static final int FRM_FLD_START_DATE_DP = 13;
    public static final int FRM_FLD_END_DATE_DP  = 14;
    public static final int FRM_FLD_START_SPECIAL = 15;
    public static final int FRM_FLD_END_SPECIAL = 16;
    public static final int FRM_FLD_GM_APPROVE = 17;
    public static final int FRM_FLD_GM_APPROVE_DATE = 18;
    //update by satrya 2013-04-11
    public static final int FRM_FLD_TYPE_FORM_LEAVE = 19;
    public static final int FRM_FLD_APPROVAL_1 = 20;
    public static final int FRM_FLD_APPROVAL_1_DATE = 21;
    public static final int FRM_FLD_APPROVAL_2 = 22;
    public static final int FRM_FLD_APPROVAL_2_DATE = 23;
    public static final int FRM_FLD_APPROVAL_3 = 24;
    public static final int FRM_FLD_APPROVAL_3_DATE = 25;
    public static final int FRM_FLD_APPROVAL_4 = 26;
    public static final int FRM_FLD_APPROVAL_4_DATE = 27;
    public static final int FRM_FLD_APPROVAL_5 = 28;
    public static final int FRM_FLD_APPROVAL_5_DATE = 29;
    public static final int FRM_FLD_APPROVAL_6 = 30;
    public static final int FRM_FLD_APPROVAL_6_DATE = 31;

    public static String[] fieldNames = 
    {
        "FRM_FLD_LEAVE_APPLICATION_ID",       
        "FRM_FLD_SUBMISSION_DATE",
        "FRM_FLD_EMPLOYEE_ID",       
        "FRM_FLD_LEAVE_REASON",       
        "FRM_FLD_DEP_HEAD_APPROVAL",       
        "FRM_FLD_HR_MAN_APPROVAL",
        "FRM_FLD_DOC_STATUS",
        "FRM_FLD_DEP_HEAD_APPROVE_DATE",       
        "FRM_FLD_HR_MAN_APPROVE_DATE",        
        "FRM_FLD_START_DATE_AL",                
        "FRM_FLD_END_DATE_AL",
        "FRM_FLD_START_DATE_LL",
        "FRM_FLD_END_DATE_LL",
        "FRM_FLD_START_DATE_DP",
        "FRM_FLD_END_DATE_DP",
        "FRM_FLD_START_SPECIAL",
        "FRM_FLD_END_SPECIAL",
        "FRM_FLD_GM_APPROVE",
        "FRM_FLD_GM_APPROVE_DATE",
        "FRM_FLD_TYPE_FORM_LEAVE",
        "FRM_FLD_APPROVAL_1",
        "FRM_FLD_APPROVAL_1_DATE",
        "FRM_FLD_APPROVAL_2",
        "FRM_FLD_APPROVAL_2_DATE",
        "FRM_FLD_APPROVAL_3",
        "FRM_FLD_APPROVAL_3_DATE",
        "FRM_FLD_APPROVAL_4",
        "FRM_FLD_APPROVAL_4_DATE",
        "FRM_FLD_APPROVAL_5",
        "FRM_FLD_APPROVAL_5_DATE",
        "FRM_FLD_APPROVAL_6",
        "FRM_FLD_APPROVAL_6_DATE"
    };

    public static int[] fieldTypes = 
    {
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
       //update by satrya 2013-04-011
        TYPE_INT,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE
        
    };

    public FrmLeaveApplication() {
    }

    public FrmLeaveApplication(LeaveApplication leaveApplication) {
        this.leaveApplication = leaveApplication;
    }

    public FrmLeaveApplication(HttpServletRequest request, LeaveApplication leaveApplication) {
        super(new FrmLeaveApplication(leaveApplication), request);
        this.leaveApplication = leaveApplication;
    }

    public String getFormName() {
        return FRM_LEAVE_APPLICATION;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public LeaveApplication getEntityObject() {
        return leaveApplication;
    }

    public void requestEntityObject(LeaveApplication objLeaveApplication) 
    {
        try 
        {
            this.requestParam();
            objLeaveApplication.setSubmissionDate(getDate(FRM_FLD_SUBMISSION_DATE));
            objLeaveApplication.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
            objLeaveApplication.setLeaveReason(getString(FRM_FLD_LEAVE_REASON));  
            objLeaveApplication.setDepHeadApproval(getLong(FRM_FLD_DEP_HEAD_APPROVAL));  
            objLeaveApplication.setHrManApproval(getLong(FRM_FLD_HR_MAN_APPROVAL));              
            objLeaveApplication.setDocStatus(getInt(FRM_FLD_DOC_STATUS));  
            objLeaveApplication.setGmApproval(getLong(FRM_FLD_GM_APPROVE));
            //objAlStockTaken.getTakenFinnishDate()==null ? "": Formater.formatDate(objAlStockTaken.getTakenFinnishDate(), "yyyy-MM-dd");            
            //objLeaveApplication.getDepHeadApproval()!= 0 ? "":"";
            
            if(objLeaveApplication.getDepHeadApproval() != 0){
                objLeaveApplication.setDepHeadApproveDate(getDate(FRM_FLD_DEP_HEAD_APPROVE_DATE));
            }else{                
                objLeaveApplication.setDepHeadApproveDate(null);                
            }
            
            if(objLeaveApplication.getHrManApproval() != 0){
                objLeaveApplication.setHrManApproveDate(getDate(FRM_FLD_HR_MAN_APPROVE_DATE));                          
            }else{
                objLeaveApplication.setHrManApproveDate(null);                          
            }
            if(objLeaveApplication.getGmApproval() != 0){
                objLeaveApplication.setGmApprovalDate(getDate(FRM_FLD_GM_APPROVE_DATE));
            }else{
                objLeaveApplication.setGmApprovalDate(null);
            }
            objLeaveApplication.setTypeFormLeave(getInt(FRM_FLD_TYPE_FORM_LEAVE));
            objLeaveApplication.setApproval_1(getLong(FRM_FLD_APPROVAL_1));
            objLeaveApplication.setApproval_1_date(getDate(FRM_FLD_APPROVAL_1_DATE));
            objLeaveApplication.setApproval_2(getLong(FRM_FLD_APPROVAL_2));
            objLeaveApplication.setApproval_2_date(getDate(FRM_FLD_APPROVAL_2_DATE));
            objLeaveApplication.setApproval_3(getLong(FRM_FLD_APPROVAL_3));
            objLeaveApplication.setApproval_3_date(getDate(FRM_FLD_APPROVAL_3_DATE));
            objLeaveApplication.setApproval_4(getLong(FRM_FLD_APPROVAL_4));
            objLeaveApplication.setApproval_4_date(getDate(FRM_FLD_APPROVAL_4_DATE));
            objLeaveApplication.setApproval_5(getLong(FRM_FLD_APPROVAL_5));
            objLeaveApplication.setApproval_5_date(getDate(FRM_FLD_APPROVAL_5_DATE));
            objLeaveApplication.setApproval_6(getLong(FRM_FLD_APPROVAL_6));
            objLeaveApplication.setApproval_6_date(getDate(FRM_FLD_APPROVAL_6_DATE));
        }
        catch (Exception e) 
        {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }    
    
    
    public void requestEntityObjectVer2(LeaveApplication objLeaveApplication) 
    {
        try 
        {
            this.requestParam();
            objLeaveApplication.setSubmissionDate(getDate(FRM_FLD_SUBMISSION_DATE));
            objLeaveApplication.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
            objLeaveApplication.setLeaveReason(getString(FRM_FLD_LEAVE_REASON));  
            objLeaveApplication.setDepHeadApproval(getLong(FRM_FLD_DEP_HEAD_APPROVAL));  
            objLeaveApplication.setHrManApproval(getLong(FRM_FLD_HR_MAN_APPROVAL));              
            objLeaveApplication.setDocStatus(getInt(FRM_FLD_DOC_STATUS));  
            objLeaveApplication.setGmApproval(getLong(FRM_FLD_GM_APPROVE));
            //objAlStockTaken.getTakenFinnishDate()==null ? "": Formater.formatDate(objAlStockTaken.getTakenFinnishDate(), "yyyy-MM-dd");            
            //objLeaveApplication.getDepHeadApproval()!= 0 ? "":"";
            
            if(objLeaveApplication.getDepHeadApproval() != 0){
                objLeaveApplication.setDepHeadApproveDate(getDate(FRM_FLD_DEP_HEAD_APPROVE_DATE));
            }else{                
                objLeaveApplication.setDepHeadApproveDate(null);                
            }
            
            if(objLeaveApplication.getHrManApproval() != 0){
                objLeaveApplication.setHrManApproveDate(getDate(FRM_FLD_HR_MAN_APPROVE_DATE));
                if(objLeaveApplication.getHrManApproveDate()==null){
                    objLeaveApplication.setHrManApproveDate(new Date(getParamLong("ApprovalDate"))); 
                }
            }else{
                objLeaveApplication.setHrManApproveDate(null);                          
            }
            if(objLeaveApplication.getGmApproval() != 0){
                objLeaveApplication.setGmApprovalDate(getDate(FRM_FLD_GM_APPROVE_DATE));
            }else{
                objLeaveApplication.setGmApprovalDate(null);
            }
            objLeaveApplication.setTypeFormLeave(getInt(FRM_FLD_TYPE_FORM_LEAVE));
            
            objLeaveApplication.setApproval_1(getLong(FRM_FLD_APPROVAL_1));
            objLeaveApplication.setApproval_1_date(getDate(FRM_FLD_APPROVAL_1_DATE));
            objLeaveApplication.setApproval_2(getLong(FRM_FLD_APPROVAL_2));
            objLeaveApplication.setApproval_2_date(getDate(FRM_FLD_APPROVAL_2_DATE));
            objLeaveApplication.setApproval_3(getLong(FRM_FLD_APPROVAL_3));
            objLeaveApplication.setApproval_3_date(getDate(FRM_FLD_APPROVAL_3_DATE));
            objLeaveApplication.setApproval_4(getLong(FRM_FLD_APPROVAL_4));
            objLeaveApplication.setApproval_4_date(getDate(FRM_FLD_APPROVAL_4_DATE));
            objLeaveApplication.setApproval_5(getLong(FRM_FLD_APPROVAL_5));
            objLeaveApplication.setApproval_5_date(getDate(FRM_FLD_APPROVAL_5_DATE));
            objLeaveApplication.setApproval_6(getLong(FRM_FLD_APPROVAL_6));
            objLeaveApplication.setApproval_6_date(getDate(FRM_FLD_APPROVAL_6_DATE));
        }
        catch (Exception e) 
        {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }    
    
}
