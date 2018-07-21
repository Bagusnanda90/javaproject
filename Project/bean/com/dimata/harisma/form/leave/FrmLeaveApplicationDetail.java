/*
 * FrmLeaveApplicationDetailDetail.java
 *
 * Created on January 28, 2005, 9:34 AM
 */

package com.dimata.harisma.form.leave;

/* servlet package */
import javax.servlet.http.*;

/* qdep package */
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.leave.*;


/**
 *
 * @author  gedhy
 */
public class FrmLeaveApplicationDetail extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private LeaveApplicationDetail leaveApplicationDetail;

    public static final String FRM_LEAVE_APPLICATION_DETAIL = "FRM_LEAVE_APPLICATION_DETAIL";  
    
    public static final int FRM_FLD_LEAVE_APPLICATION_DETAIL_ID = 0;    
    public static final int FRM_FLD_LEAVE_APPLICATION_MAIN_ID = 1;            
    public static final int FRM_FLD_LEAVE_TYPE = 2;
    public static final int FRM_FLD_TAKEN_DATE = 3;          

    public static String[] fieldNames = 
    {
        "FRM_FLD_LEAVE_APPLICATION_DETAIL_ID",       
        "FRM_FLD_LEAVE_APPLICATION_MAIN_ID",
        "FRM_FLD_LEAVE_TYPE",               
        "FRM_FLD_TAKEN_DATE"
    };

    public static int[] fieldTypes = 
    {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,        
        TYPE_DATE
    };

    public FrmLeaveApplicationDetail() {
    }

    public FrmLeaveApplicationDetail(LeaveApplicationDetail leaveApplicationDetail) {
        this.leaveApplicationDetail = leaveApplicationDetail;
    }

    public FrmLeaveApplicationDetail(HttpServletRequest request, LeaveApplicationDetail leaveApplicationDetail) {
        super(new FrmLeaveApplicationDetail(leaveApplicationDetail), request);
        this.leaveApplicationDetail = leaveApplicationDetail;
    }

    public String getFormName() {
        return FRM_LEAVE_APPLICATION_DETAIL;
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

    public LeaveApplicationDetail getEntityObject() {
        return leaveApplicationDetail;
    }

    public void requestEntityObject(LeaveApplicationDetail objLeaveApplicationDetail) 
    {
        try 
        {
            this.requestParam();
            objLeaveApplicationDetail.setLeaveType(getInt(FRM_FLD_LEAVE_TYPE));
            objLeaveApplicationDetail.setTakenDate(getDate(FRM_FLD_TAKEN_DATE));            
        }
        catch (Exception e) 
        {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }    
    
}
