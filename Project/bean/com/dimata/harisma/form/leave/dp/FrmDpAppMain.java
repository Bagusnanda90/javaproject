/*
 * FrmDpApplication.java
 *
 * Created on October 21, 2004, 12:06 PM
 */

package com.dimata.harisma.form.leave.dp;

import com.dimata.harisma.form.leave.*;
import javax.servlet.http.*;

/* qdep package */
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.leave.*;
import com.dimata.harisma.entity.leave.dp.DpAppMain;

/**
 *
 * @author  artha
 */
public class FrmDpAppMain extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private DpAppMain dpAppMain;
    
    public static final String FRM_DP_APP_MAIN = "FRM_DP_APP_MAIN";
    
     public static final int FRM_FLD_DP_APP_ID = 0;    
    public static final int FRM_FLD_SUBMISSION_DATE = 1;
    public static final int FRM_FLD_APPROVAL_ID = 2;
    public static final int FRM_FLD_APPROVAL_DATE = 3;
    public static final int FRM_FLD_EMPLOYEE_ID = 4;
    public static final int FRM_FLD_BALANCE = 5;
    public static final int FRM_FLD_DOC_STATUS = 6;
    
    public static final int FRM_FLD_APPROVAL2_ID = 7;
    public static final int FRM_FLD_APPROVAL3_ID = 8;
    public static final int FRM_FLD_APPROVAL2_DATE = 9;
    public static final int FRM_FLD_APPROVAL3_DATE = 10;
    
    
    public static final String[] fieldNames = {
        "FRM_FLD_DP_APP_ID",
        "FRM_FLD_SUBMISSION_DATE",
        "FRM_FLD_APPROVAL_ID",
        "FRM_FLD_APPROVAL_DATE",
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FLD_BALANCE",
        "FRM_FLD_DOC_STATUS",
        "FRM_FLD_APPROVAL2_ID",
        "FRM_FLD_APPROVAL3_ID",
        "FRM_FLD_APPROVAL2_DATE",
        "FRM_FLD_APPROVAL3_DATE"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,        
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,        
        TYPE_DATE        
    };
    
    public FrmDpAppMain() {
    }

    public FrmDpAppMain(DpAppMain dpAppMain) {
        this.dpAppMain = dpAppMain;
    }

    public FrmDpAppMain(HttpServletRequest request, DpAppMain dpAppMain) {
        super(new FrmDpAppMain(dpAppMain), request);
        this.dpAppMain = dpAppMain;
    }

    public String getFormName() {
        return FRM_DP_APP_MAIN;
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

    public DpAppMain getEntityObject() {
        return dpAppMain;
    }

    public void requestEntityObject(DpAppMain dpAppMain) {
        try {
            this.requestParam();
            dpAppMain.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
            dpAppMain.setSubmissionDate(getDate(FRM_FLD_SUBMISSION_DATE));
            dpAppMain.setApprovalId(getLong(FRM_FLD_APPROVAL_ID));
            dpAppMain.setApprovalDate(getDate(FRM_FLD_APPROVAL_DATE));
            dpAppMain.setBalance(getInt(FRM_FLD_BALANCE));
            dpAppMain.setDocumentStatus(getInt(FRM_FLD_DOC_STATUS));
            
            dpAppMain.setApproval2Id(getLong(FRM_FLD_APPROVAL2_ID));
            dpAppMain.setApproval3Id(getLong(FRM_FLD_APPROVAL3_ID));
            dpAppMain.setApproval2Date(getDate(FRM_FLD_APPROVAL2_DATE));
            dpAppMain.setApproval3Date(getDate(FRM_FLD_APPROVAL3_DATE));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }    
}
