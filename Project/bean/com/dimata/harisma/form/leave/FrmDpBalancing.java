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
 * @author  roy andika
 */
public class FrmDpBalancing extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private DPUpload dpUpload;

    public static final String FRM_DP_UPLOAD = "FRM_DP_UPLOAD_BALANCING";  
    
    /*
     private long employee_id;
    private String employee_num;
    private Date commencing_date;
    private long department_id;
    private String full_name;
    
    private long dp_stock_id;
    private int dp_qty;
    private Date owning_date;
    private Date expired_date;
    private int exception_flag;
    private Date expired_date_exc;
    private int dp_status;
    private String note;
    private int qty_used;
    private int qty_residue;
    
    private long dpUploadId;
    private Date dpOpnameDate;
    private Date dpAqqDate;
    private int dpNumber;
    private int dataStatus;
    
    */
    
    public static final int FRM_FLD_EMPLOYEE_ID                 = 0;    
    public static final int FRM_FLD_EMPLOYEE_NUM           = 1;
    public static final int FRM_FLD_COMMENCING_DATE         = 2;
    public static final int FRM_FLD_DEPARTMENT_ID         = 3;
    public static final int FRM_FLD_FULL_NAME    = 4;
    public static final int FRM_FLD_DP_QTY_UPLOAD       = 5;
    public static final int FRM_FLD_DP_QTY_STOCK        = 6;
   // public static final int FRM_FLD_DEPARTMENT_ID       = 7;
    public static final int FRM_FLD_DP_STOCK_ID         = 8;
    
    
    public static String[] fieldNames = {
        "FRM_FLD_DP_UPLOAD_ID",
        "FRM_FLD_OPNAME_DATE",
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FLD_DATA_STATUS",
        "FRM_FLD_ACQUISITION_DATE",
        "FRM_FLD_DP_QTY_UPLOAD",
        "FRM_FLD_DP_QTY_STOCK",
        "FRM_FLD_DEPARTMENT_ID",
        "FRM_FLD_DP_STOCK_ID"        
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_DATE,
        TYPE_LONG,
        TYPE_INT,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG
    };

    public FrmDpBalancing() {
    }

    public FrmDpBalancing(DPUpload dpUpload) {
        this.dpUpload = dpUpload;
    }

    public FrmDpBalancing(HttpServletRequest request, DPUpload dpUpload) {
        super(new FrmDpBalancing(dpUpload), request);
        this.dpUpload = dpUpload;
    }

    public String getFormName() {
        return FRM_DP_UPLOAD;
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

    public DPUpload getEntityObject() {
        return dpUpload;
    }

    public void requestEntityObject(DPUpload dpUpload) {
        try {
            this.requestParam();
            /*
            dpUpload.setDataStatus(getInt(FRM_FLD_DATA_STATUS));
            dpUpload.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
            dpUpload.setOpnameDate(getDate(FRM_FLD_OPNAME_DATE));
            dpUpload.setAcquisitionDate(getDate(FRM_FLD_ACQUISITION_DATE));
            dpUpload.
            dpUpload.setDPNumber(getInt(FRM_FLD_DP_ACQUIRED));
            
            */
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }    
}
