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
public class FrmLLUpload extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private LLUpload lLUpload;

    public static final String FRM_LL_UPLOAD = "FRM_LL_UPLOAD";  
    
    /**
       LL_UPLOAD_ID         bigint NOT NULL,
       OPNAME_DATE          DATETIME NOT NULL,
       EMPLOYEE_ID          bigint NOT NULL,
       DATA_STATUS   
       LL_TAKEN_YEAR1       integer,
       LL_TAKEN_YEAR2       integer,
       LL_TAKEN_YEAR3       integer,
       LL_TAKEN_YEAR4       integer,
       LL_TAKEN_YEAR5       integer,
     */
    
    public static final int FRM_FLD_LL_UPLOAD_ID = 0;
    public static final int FRM_FLD_OPNAME_DATE = 1;
    public static final int FRM_FLD_EMPLOYEE_ID = 2;    
    public static final int FRM_FLD_LL_TAKEN_YEAR1 = 3;
    public static final int FRM_FLD_DATA_STATUS = 4;
    public static final int FRM_FLD_STOCK = 5;
    public static final int FRM_FLD_NEW_LL = 6;
    public static final int FRM_FLD_LAST_PER_TO_CLEAR_LL = 7;
    public static final int FRM_FLD_LL_STOCK_ID = 8;
    public static final int FRM_FLD_LL_QTY = 9;
    
    
    
    //public static final int FRM_FLD_LL_TAKEN_YEAR2 = 5;
    //public static final int FRM_FLD_LL_TAKEN_YEAR3 = 6;
    //public static final int FRM_FLD_LL_TAKEN_YEAR4 = 7;
    //public static final int FRM_FLD_LL_TAKEN_YEAR5 = 8;
    

    public static String[] fieldNames = {
        "FRM_FLD_AL_UPLOAD_ID",
        "FRM_FLD_OPNAME_DATE",
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FLD_LL_TAKEN_YEAR1",
        "FRM_FLD_DATA_STATUS",
        "FRM_FLD_FLD_STOCK",
        "FRM_FLD_NEW_LL",
        "FRM_FLD_LAST_PER_TO_CLEAR_LL",
        "FRM_FLD_LL_STOCK_ID",
        "FRM_FLD_LL_QTY"        
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_DATE,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT       
    };

    public FrmLLUpload() {
    }

    public FrmLLUpload(LLUpload lLUpload) {
        this.lLUpload = lLUpload;
    }

    public FrmLLUpload(HttpServletRequest request, LLUpload lLUpload) {
        super(new FrmLLUpload(lLUpload), request);
        this.lLUpload = lLUpload;
    }

    public String getFormName() {
        return FRM_LL_UPLOAD;
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

    public LLUpload getEntityObject() {
        return lLUpload;
    }

    public void requestEntityObject(LLUpload lLUpload) {
        try {
            this.requestParam();
            lLUpload.setDataStatus(getInt(FRM_FLD_DATA_STATUS));
            lLUpload.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
            lLUpload.setOpnameDate(getDate(FRM_FLD_OPNAME_DATE));
            lLUpload.setLlTakenYear1(getInt(FRM_FLD_LL_TAKEN_YEAR1));            
            lLUpload.setDataStatus(getInt(FRM_FLD_DATA_STATUS));
            lLUpload.setStock(getInt(FRM_FLD_STOCK));            
            lLUpload.setNewLL(getInt(FRM_FLD_NEW_LL));
            lLUpload.setLastPerToClearLL(getInt(FRM_FLD_LAST_PER_TO_CLEAR_LL));
            lLUpload.setLLStockID(getInt(FRM_FLD_LL_STOCK_ID));
            lLUpload.setLLQty(getInt(FRM_FLD_LL_QTY)); 
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }    
}
