/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.form.leave;
import javax.servlet.http.*;

/* qdep package */
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.leave.*;
/**
 *
 * @author Tu Roy
 */
public class FrmSpecialUnpaidLeaveTakenUpload extends FRMHandler implements I_FRMInterface, I_FRMType{
    private AlUpload alUpload;

    public static final String FRM_SPECIAL_UNPAID_LEAVE_TAKEN_UPLOAD = "FRM_SPECIAL_UNPAID_LEAVE_TAKEN_UPLOAD";  
    
    public static final int FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID = 0;
    public static final int FRM_FLD_LEAVE_APPLICATION_ID = 1;
    public static final int FRM_FLD_SCHEDULE_ID = 2;   
    public static final int FRM_FLD_EMPLOYEE_ID = 3;
    public static final int FRM_FLD_TAKEN_DATE = 4;
    public static final int FRM_FLD_TAKEN_QTY=5;
    public static final int FRM_FLD_TAKEN_STATUS=6;    
    public static final int FRM_FLD_TAKEN_FROM_STATUS=7;
            
    public static String[] fieldNames = {
        "FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID",
        "FRM_FLD_LEAVE_APPLICATION_ID",
        "FRM_FLD_SCHEDULE_ID",
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FLD_TAKEN_DATE",
        "FRM_FLD_TAKEN_QTY",
        "FRM_FLD_TAKEN_STATUS",
        "FRM_FLD_TAKEN_FROM_STATUS"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,       
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT        
    };

    public FrmSpecialUnpaidLeaveTakenUpload() {
    }

    public FrmSpecialUnpaidLeaveTakenUpload(AlUpload alUpload) {
        this.alUpload = alUpload;
    }

    public FrmSpecialUnpaidLeaveTakenUpload(HttpServletRequest request, AlUpload alUpload) {
        super(new FrmAlUpload(alUpload), request);
        this.alUpload = alUpload;
    }

    public String getFormName() {
        return FRM_SPECIAL_UNPAID_LEAVE_TAKEN_UPLOAD;
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

    public AlUpload getEntityObject() {
        return alUpload;
    }

    public void requestEntityObject(SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken) {
        try {
            this.requestParam();
            objSpecialUnpaidLeaveTaken.setLeaveApplicationId(getLong(FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID));
            objSpecialUnpaidLeaveTaken.setScheduledId(getLong(FRM_FLD_LEAVE_APPLICATION_ID));
            objSpecialUnpaidLeaveTaken.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
            objSpecialUnpaidLeaveTaken.setTakenDate(getDate(FRM_FLD_TAKEN_DATE));
            objSpecialUnpaidLeaveTaken.setTakenQty(getInt(FRM_FLD_TAKEN_QTY));
            objSpecialUnpaidLeaveTaken.setTakenStatus(getInt(FRM_FLD_TAKEN_STATUS));
            objSpecialUnpaidLeaveTaken.setTakenFromStatus(getInt(FRM_FLD_TAKEN_FROM_STATUS));
           
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }   
}
