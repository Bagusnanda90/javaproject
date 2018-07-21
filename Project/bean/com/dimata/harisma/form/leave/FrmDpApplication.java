/*
 * FrmDpApplication.java
 *
 * Created on October 21, 2004, 12:06 PM
 */

package com.dimata.harisma.form.leave;

import javax.servlet.http.*;

/* qdep package */
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.leave.*;

/**
 *
 * @author  gedhy
 */
public class FrmDpApplication extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private DpApplication dpApplication;

    public static final String FRM_DP_APPLICATION = "FRM_DP_APPLICATION";  
    
    public static final int FRM_FLD_DP_APPLICATION_ID = 0;
    public static final int FRM_FLD_SUBMISSION_DATE = 1;
    public static final int FRM_FLD_EMPLOYEE_ID = 2;
    public static final int FRM_FLD_DP_ID = 3;    
    public static final int FRM_FLD_TAKEN_DATE = 4;
    public static final int FRM_FLD_APPROVAL_ID = 5;    
    public static final int FRM_FLD_DOC_STATUS = 6;    
    public static final int FRM_FLD_BALANCE = 7;    
    public static final int FRM_FLD_NEW_BALANCE = 8;    

    public static String[] fieldNames = {
        "FRM_FLD_DP_APPLICATION_ID", 
        "FRM_FLD_SUBMISSION_DATE",
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FLD_DP_ID",
        "FRM_FLD_TAKEN_DATE",
        "FRM_FLD_APPROVAL_ID",
        "FRM_FLD_DOC_STATUS",
        "FRM_FLD_BALANCE",
        "FRM_FLD_NEW_BALANCE"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_DATE,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT
    };

    public FrmDpApplication() {
    }

    public FrmDpApplication(DpApplication dpApplication) {
        this.dpApplication = dpApplication;
    }

    public FrmDpApplication(HttpServletRequest request, DpApplication dpApplication) {
        super(new FrmDpApplication(dpApplication), request);
        this.dpApplication = dpApplication;
    }

    public String getFormName() {
        return FRM_DP_APPLICATION;
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

    public DpApplication getEntityObject() {
        return dpApplication;
    }

    public void requestEntityObject(DpApplication dpApplication) {
        try {
            this.requestParam();
            dpApplication.setSubmissionDate(getDate(FRM_FLD_SUBMISSION_DATE));            
            dpApplication.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));            
            dpApplication.setDpId(getLong(FRM_FLD_DP_ID));            
            dpApplication.setTakenDate(getDate(FRM_FLD_TAKEN_DATE));  
            dpApplication.setApprovalId(getLong(FRM_FLD_APPROVAL_ID));            
            dpApplication.setDocStatus(getInt(FRM_FLD_DOC_STATUS));
            dpApplication.setBalance(getInt(FRM_FLD_BALANCE));
            dpApplication.setNewBalance(getInt(FRM_FLD_NEW_BALANCE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }    
}
