/*
 * FrmDpApplication.java
 *
 * Created on October 21, 2004, 12:06 PM
 */

package com.dimata.harisma.form.leave.ll;

import com.dimata.harisma.form.leave.*;
import javax.servlet.http.*;

/* qdep package */
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.leave.*;
import com.dimata.harisma.entity.leave.ll.LLAppMain;
/**
 *
 * @author  artha
 */
public class FrmLLAppMain extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private LLAppMain lLAppMain;
    
    public static final String FRM_LL_APP_MAIN = "FRM_LL_APP_MAIN";
    
     public static final int FRM_FLD_LL_APP_ID = 0;    
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
    
    public static final int FRM_FLD_START_DATE = 11;
    public static final int FRM_FLD_END_DATE = 12;
    public static final int FRM_FLD_REQUEST_QTY = 12;
    public static final int FRM_FLD_TAKEN_QTY = 12;
    
    
    public static final String[] fieldNames = {
        "FRM_FLD_LL_APP_ID",
        "FRM_FLD_SUBMISSION_DATE",
        "FRM_FLD_APPROVAL_ID",
        "FRM_FLD_APPROVAL_DATE",
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FLD_BALANCE",
        "FRM_FLD_DOC_STATUS",
        "FRM_FLD_APPROVAL2_ID",
        "FRM_FLD_APPROVAL3_ID",
        "FRM_FLD_APPROVAL2_DATE",
        "FRM_FLD_APPROVAL3_DATE",
        "FRM_FLD_START_DATE",
        "FRM_FLD_END_DATE",
        "FRM_FLD_REQUEST_QTY",
        "FRM_FLD_TAKEN_QTY"
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
        TYPE_DATE,        
        TYPE_DATE,        
        TYPE_DATE,       
        TYPE_INT,       
        TYPE_INT       
    };
    
    public FrmLLAppMain() {
    }

    public FrmLLAppMain(LLAppMain lLAppMain) {
        this.lLAppMain = lLAppMain;
    }

    public FrmLLAppMain(HttpServletRequest request, LLAppMain lLAppMain) {
        super(new FrmLLAppMain(lLAppMain), request);
        this.lLAppMain = lLAppMain;
    }

    public String getFormName() {
        return FRM_LL_APP_MAIN;
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

    public LLAppMain getEntityObject() {
        return lLAppMain;
    }

    public void requestEntityObject(LLAppMain lLAppMain) {
        try {
            this.requestParam();
            lLAppMain.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
            lLAppMain.setSubmissionDate(getDate(FRM_FLD_SUBMISSION_DATE));
            lLAppMain.setApprovalId(getLong(FRM_FLD_APPROVAL_ID));
            lLAppMain.setApprovalDate(getDate(FRM_FLD_APPROVAL_DATE));
            lLAppMain.setBalance(getInt(FRM_FLD_BALANCE));
            lLAppMain.setDocumentStatus(getInt(FRM_FLD_DOC_STATUS));
            
            lLAppMain.setApproval2Id(getLong(FRM_FLD_APPROVAL2_ID));
            lLAppMain.setApproval3Id(getLong(FRM_FLD_APPROVAL3_ID));
            lLAppMain.setApproval2Date(getDate(FRM_FLD_APPROVAL2_DATE));
            lLAppMain.setApproval3Date(getDate(FRM_FLD_APPROVAL3_DATE));
            
            lLAppMain.setStartDate(getDate(FRM_FLD_START_DATE));
            lLAppMain.setEndDate(getDate(FRM_FLD_END_DATE));
            
            lLAppMain.setRequestQty(getInt(FRM_FLD_REQUEST_QTY));
            lLAppMain.setTakenQty(getInt(FRM_FLD_TAKEN_QTY));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }    
}
