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
import com.dimata.harisma.entity.leave.dp.DpAppDetail;

/**
 *
 * @author  artha
 */
public class FrmDpAppDetail extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private DpAppDetail dpAppDetail;
    
    public static final String FRM_DP_APP_DETAIL = "FRM_DP_APP_DETAIL";
    
    public static final int FRM_FLD_DP_APP_DETAIL_ID = 0;    
    public static final int FRM_FLD_DP_APP_ID = 1;    
    public static final int FRM_FLD_TAKEN_DATE = 2;
    public static final int FRM_FLD_DP_ID = 3;
    public static final int FRM_FLD_STATUS = 4;
    
    public static final String[] fieldNames = {
        "FRM_FLD_DP_APP_DETAIL_ID",
        "FRM_FLD_DP_APP_ID",
        "FRM_FLD_TAKEN_DATE",
        "FRM_FLD_DP_ID",
        "FRM_FLD_STATUS"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,  
        TYPE_LONG,
        TYPE_INT
    };
     
    
    public FrmDpAppDetail() {
    }

    public FrmDpAppDetail(DpAppDetail dpAppDetail) {
        this.dpAppDetail = dpAppDetail;
    }

    public FrmDpAppDetail(HttpServletRequest request, DpAppDetail dpAppDetail) {
        super(new FrmDpAppDetail(dpAppDetail), request);
        this.dpAppDetail = dpAppDetail;
    }

    public String getFormName() {
        return FRM_DP_APP_DETAIL;
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

    public DpAppDetail getEntityObject() {
        return dpAppDetail;
    }

    public void requestEntityObject(DpAppDetail dpAppDetail) {
        try {
            this.requestParam();
            dpAppDetail.setDpAppMainId(getLong(FRM_FLD_DP_APP_ID));
            dpAppDetail.setTakenDate(getDate(FRM_FLD_TAKEN_DATE));
            dpAppDetail.setDpId(getLong(FRM_FLD_DP_ID));
            dpAppDetail.setStatus(getInt(FRM_FLD_STATUS));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }    
}
