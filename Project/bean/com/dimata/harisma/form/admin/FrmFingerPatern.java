/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.admin;
import com.dimata.harisma.entity.admin.FingerPatern;
import com.dimata.qdep.form.*;
import java.util.*;
import javax.servlet.http.*;
/**
 *
 * @author Acer
 */

public class FrmFingerPatern extends FRMHandler implements I_FRMInterface, I_FRMType {
    private FingerPatern fingerPatern;

    public static final String FRM_NAME_FINGER_PATERN = "FRM_NAME_FINGER_PATERN";

    public static final int FRM_FIELD_FINGER_PATERN_ID = 0;
    public static final int FRM_FIELD_EMPLOYEE_ID = 1;
    public static final int FRM_FIELD_FINGER_TYPE = 2;
    public static final int FRM_FIELD_FINGER_PATERN_DATA = 3;
    

    public static String[] fieldNames = {
        "FRM_FIELD_FINGER_PATERN_ID", 
        "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_FINGER_TYPE", 
        "FRM_FIELD_FINGER_PATERN_DATA"
    };

    public static int[] fieldTypes = {
        TYPE_LONG + ENTRY_REQUIRED, 
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_INT + ENTRY_REQUIRED,
        TYPE_STRING
    };

    public FrmFingerPatern() {
    }

    public FrmFingerPatern(FingerPatern fingerPatern) {
        this.fingerPatern = fingerPatern;
    }

    public FrmFingerPatern(HttpServletRequest request, FingerPatern fingerPatern) {
        super(new FrmFingerPatern(fingerPatern), request);
        this.fingerPatern = fingerPatern;
    }

    public String getFormName() {
        return FRM_NAME_FINGER_PATERN;
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

    public FingerPatern getEntityObject() {
        return fingerPatern;
    }

    public void requestEntityObject(FingerPatern fingerPatern) {
        try {
            this.requestParam();
            fingerPatern.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            fingerPatern.setFingerType(getInt(FRM_FIELD_FINGER_TYPE));
            fingerPatern.setFingerPatern(getString(FRM_FIELD_FINGER_PATERN_DATA));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
