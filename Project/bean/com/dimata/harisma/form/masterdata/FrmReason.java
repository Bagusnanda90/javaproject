/*
 * FrmReason.java
 *
 * Created on June 20, 2007, 4:54 PM
 */

package com.dimata.harisma.form.masterdata;

/* java package */

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;

/**
 *
 * @author  yunny
 */
public class FrmReason extends FRMHandler implements I_FRMInterface, I_FRMType {
     private Reason reason;
     
     public static final String FRM_NAME_REASON = "FRM_NAME_REASON";
     
     public static final int FRM_FIELD_REASON_ID = 0;
     public static final int FRM_FIELD_NO = 1;
     public static final int FRM_FIELD_REASON = 2;
     public static final int FRM_FIELD_DESCRIPTION = 3;
     public static final int FRM_FIELD_SCHEDULE_ID = 4;
     public static final int FRM_FIELD_REASON_CODE = 5;
     //update by satrya 2013-02-05
     public static final int FRM_FIELD_REASON_TIME =6;
     public static final int FRM_FIELD_FLAG_IN_PAY_INPUT =7;
     public static final int FRM_FIELD_NUMBER_OF_SHOW=8;
     public static final int FRM_FIELD_COUNT_ABLE=9;
     
      public static String[] fieldNames = {
        "FRM_FIELD_REASON_ID",
        "FRM_FIELD_NO", 
        "FRM_FIELD_REASON",
        "FRM_FIELD_DESCRIPTION",
        "FRM_FIELD_SCHEDULE_ID",
        "FRM_FIELD_REASON_CODE",
        "FRM_FIELD_REASON_TIME",
        //update by satrya 2014-04-21
        "FRM_FIELD_FLAG_IN_PAY_INPUT",
        "FRM_FIELD_NUMBER_OF_SHOW",
        "FRM_FIELD_COUNT_ABLE"
        
    };
    
      public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_INT, TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING,
        TYPE_LONG,TYPE_STRING,TYPE_INT,TYPE_INT,TYPE_INT,TYPE_INT
        
    };
    
    /** Creates a new instance of FrmReason */
    public FrmReason() {
    }
    
    public FrmReason(Reason reason) {
        this.reason = reason;
    }
    
    public FrmReason(HttpServletRequest request, Reason reason) {
        super(new FrmReason(reason), request);
        this.reason = reason;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int getFieldSize() {
        return fieldNames.length;
    }
    
    public int[] getFieldTypes() {
         return fieldTypes;
    }
    
    public String getFormName() {
         return FRM_NAME_REASON;
    }
    
    public Reason getEntityObject() {
        return reason;
    }
    
      public void requestEntityObject(Reason reason) {
        try {
            this.requestParam();
            reason.setNo(getInt(FRM_FIELD_NO));
            reason.setReason(getString(FRM_FIELD_REASON));
            reason.setDescription(getString(FRM_FIELD_DESCRIPTION));
            reason.setScheduleId(getLong(FRM_FIELD_SCHEDULE_ID));
            //update by satrya 2012-10-19
            reason.setKodeReason(getString(FRM_FIELD_REASON_CODE));
            //update by satrya 2013-02-03
            reason.setTimeReason(getInt(FRM_FIELD_REASON_TIME));
            reason.setFlagShowInPayInput(getInt(FRM_FIELD_FLAG_IN_PAY_INPUT));
            reason.setNumberOfShow(getInt(FRM_FIELD_NUMBER_OF_SHOW));
            reason.setCountAble(getInt(FRM_FIELD_COUNT_ABLE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
}
