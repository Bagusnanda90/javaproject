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
public class FrmAlUpload extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private AlUpload alUpload;

    public static final String FRM_AL_UPLOAD = "FRM_AL_UPLOAD";  
    
    public static final int FRM_FLD_AL_UPLOAD_ID = 0;
    public static final int FRM_FLD_OPNAME_DATE = 1;
    public static final int FRM_FLD_EMPLOYEE_ID = 2;
    public static final int FRM_FLD_LAST_PER_TO_CLEAR = 3;
    public static final int FRM_FLD_CURR_PERIOD_TAKEN = 4;
    public static final int FRM_FLD_DATA_STATUS = 5;
    public static final int FRM_FLD_NEW_AR=6;
    public static final int FRM_FLD_PREV_SYS_AL_BAL=7;    
            
    public static String[] fieldNames = {
        "FRM_FLD_AL_UPLOAD_ID",
        "FRM_FLD_OPNAME_DATE",
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FLD_LAST_PER_TO_CLEAR",
        "FRM_FLD_CURR_PERIOD_TAKEN",
        "FRM_FLD_DATA_STATUS",
        "FRM_FLD_NEW_AL",
        "FRM_FLD_PREV_SYS_AL_BAL"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_DATE,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT
    };

    public FrmAlUpload() {
    }

    public FrmAlUpload(AlUpload alUpload) {
        this.alUpload = alUpload;
    }

    public FrmAlUpload(HttpServletRequest request, AlUpload alUpload) {
        super(new FrmAlUpload(alUpload), request);
        this.alUpload = alUpload;
    }

    public String getFormName() {
        return FRM_AL_UPLOAD;
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

    public void requestEntityObject(AlUpload alUpload) {
        try {
            this.requestParam();
            alUpload.setCurrPerTaken(getInt(FRM_FLD_CURR_PERIOD_TAKEN));
            alUpload.setDataStatus(getInt(FRM_FLD_DATA_STATUS));
            alUpload.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
            alUpload.setLastPerToClear(getInt(FRM_FLD_LAST_PER_TO_CLEAR));
            alUpload.setOpnameDate(getDate(FRM_FLD_OPNAME_DATE));
            alUpload.setNewAl(getInt(FRM_FLD_NEW_AR));
            alUpload.setPrevSysAlBalance(getInt(FRM_FLD_PREV_SYS_AL_BAL));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }    
}
