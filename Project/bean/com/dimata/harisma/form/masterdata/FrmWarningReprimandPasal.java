/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;


import com.dimata.harisma.entity.masterdata.WarningReprimandPasal;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import static com.dimata.qdep.form.I_FRMType.ENTRY_REQUIRED;
import static com.dimata.qdep.form.I_FRMType.TYPE_LONG;
import static com.dimata.qdep.form.I_FRMType.TYPE_STRING;
import javax.servlet.http.HttpServletRequest;

/**
 * Document : Form Warning Reprimand Pasal
 * Date : 2014-11-07
 * @author Hendra McHen
 */
public class FrmWarningReprimandPasal extends FRMHandler implements I_FRMInterface, I_FRMType {
    /*
    PASAL_ID
    PASAL_TITLE
    BAB_ID
     */
    private WarningReprimandPasal warningReprimandPasal;
    public static final String FRM_NAME_PASAL = "FRM_NAME_PASAL";
    public static final int FRM_FIELD_PASAL_ID = 0;
    public static final int FRM_FIELD_PASAL_TITLE = 1;
    public static final int FRM_FIELD_BAB_ID = 2;


    public static String[] fieldNames = {
        "FRM_FIELD_PASAL_ID", "FRM_FIELD_PASAL_TITLE", "FRM_FIELD_BAB_ID"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, TYPE_STRING + ENTRY_REQUIRED, TYPE_LONG
    };

    public FrmWarningReprimandPasal() {
    }

    public FrmWarningReprimandPasal(WarningReprimandPasal warningReprimandPasal) {
        this.warningReprimandPasal = warningReprimandPasal;
    }

    public FrmWarningReprimandPasal(HttpServletRequest request, WarningReprimandPasal warningReprimandPasal) {
        super(new FrmWarningReprimandPasal(warningReprimandPasal), request);
        this.warningReprimandPasal = warningReprimandPasal;
    }

    public String getFormName() {
        return FRM_NAME_PASAL;
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

    public WarningReprimandPasal getEntityObject() {
        return warningReprimandPasal;
    }

    public void requestEntityObject(WarningReprimandPasal warningReprimandPasal) {
        try {
            this.requestParam();
            warningReprimandPasal.setPasalTitle(getString(FRM_FIELD_PASAL_TITLE));
            warningReprimandPasal.setBabId(getLong(FRM_FIELD_BAB_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
