/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.WarningReprimandBab;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import static com.dimata.qdep.form.I_FRMType.ENTRY_REQUIRED;
import static com.dimata.qdep.form.I_FRMType.TYPE_LONG;
import static com.dimata.qdep.form.I_FRMType.TYPE_STRING;
import javax.servlet.http.HttpServletRequest;

/**
 * Document : Form Warning Reprimand Bab
 * Date : 2014-11-07
 * @author Hendra McHen
 */
public class FrmWarningReprimandBab extends FRMHandler implements I_FRMInterface, I_FRMType {
    private WarningReprimandBab warningReprimandBab;

    public static final String FRM_NAME_BAB = "FRM_NAME_BAB";

    public static final int FRM_FIELD_BAB_ID = 0;
    public static final int FRM_FIELD_BAB_TITLE = 1;


    public static String[] fieldNames = {
        "FRM_FIELD_BAB_ID", "FRM_FIELD_BAB_TITLE"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, TYPE_STRING + ENTRY_REQUIRED
    };

    public FrmWarningReprimandBab() {
    }

    public FrmWarningReprimandBab(WarningReprimandBab warningReprimandBab) {
        this.warningReprimandBab = warningReprimandBab;
    }

    public FrmWarningReprimandBab(HttpServletRequest request, WarningReprimandBab warningReprimandBab) {
        super(new FrmWarningReprimandBab(warningReprimandBab), request);
        this.warningReprimandBab = warningReprimandBab;
    }

    public String getFormName() {
        return FRM_NAME_BAB;
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

    public WarningReprimandBab getEntityObject() {
        return warningReprimandBab;
    }

    public void requestEntityObject(WarningReprimandBab warningReprimandBab) {
        try {
            this.requestParam();
            warningReprimandBab.setBabTitle(getString(FRM_FIELD_BAB_TITLE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
