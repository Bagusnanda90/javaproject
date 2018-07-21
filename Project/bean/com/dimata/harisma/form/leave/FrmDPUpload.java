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

public class FrmDPUpload extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private DPUpload dpUpload;

    public static final String FRM_DP_UPLOAD = "FRM_DP_UPLOAD";  
    
    public static final int FRM_FLD_DP_UPLOAD_ID        = 0;
    public static final int FRM_FLD_OPNAME_DATE         = 1;
    public static final int FRM_FLD_EMPLOYEE_ID         = 2;
    public static final int FRM_FLD_DATA_STATUS         = 3;
    public static final int FRM_FLD_ACQUISITION_DATE    = 4;
    public static final int FRM_FLD_DP_ACQUIRED         = 5;            // Dp pada table upload
    public static final int FRM_FLD_DP_SYSTEM           = 6;            // Dp Number pada stock Dp
    public static final int FRM_FLD_DP_STOCK_ID         = 7;
    public static final int FRM_FLD_COMMENCING_DATE     = 8;
    public static final int FRM_FLD_OWNING_DATE         = 9;
    
    
    public static String[] fieldNames = {
        "FRM_FLD_AL_UPLOAD_ID",
        "FRM_FLD_OPNAME_DATE",
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FLD_DATA_STATUS",
        "FRM_FLD_ACQUISITION_DATE",
        "FRM_FLD_DP_ACQUIRED",
        "FRM_FLD_DP_SYSTEM",
        "FRM_FLD_DP_STOCK_ID",
        "FRM_FLD_COMMENCING_DATE",
        "FRM_FLD_OWNING_DATE"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_DATE,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE
    };

    public FrmDPUpload() {
    }

    public FrmDPUpload(DPUpload dpUpload) {
        this.dpUpload = dpUpload;
    }

    public FrmDPUpload(HttpServletRequest request, DPUpload dpUpload) {
        super(new FrmDPUpload(dpUpload), request);
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
            dpUpload.setDataStatus(getInt(FRM_FLD_DATA_STATUS));
            dpUpload.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
            dpUpload.setOpnameDate(getDate(FRM_FLD_OPNAME_DATE));
            dpUpload.setAcquisitionDate(getDate(FRM_FLD_ACQUISITION_DATE));
            dpUpload.setDPNumber(getInt(FRM_FLD_DP_ACQUIRED));
            dpUpload.setDpStock(getInt(FRM_FLD_DP_SYSTEM));
            dpUpload.setDpStockId(getLong(FRM_FLD_DP_STOCK_ID));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }    
}
