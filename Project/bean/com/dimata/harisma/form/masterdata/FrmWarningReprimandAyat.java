/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.WarningReprimandAyat;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import static com.dimata.qdep.form.I_FRMType.ENTRY_REQUIRED;
import static com.dimata.qdep.form.I_FRMType.TYPE_LONG;
import static com.dimata.qdep.form.I_FRMType.TYPE_STRING;
import javax.servlet.http.HttpServletRequest;

/**
 * Document : Warning Reprimand Ayat
 * Date     : 2014-11-07
 * @author Hendra McHen
 */
public class FrmWarningReprimandAyat extends FRMHandler implements I_FRMInterface, I_FRMType {
    /*
    AYAT_ID
    AYAT_TITLE
    AYAT_DESCRIPTION
    PASAL_ID
     */
    private WarningReprimandAyat warningReprimandAyat;
    public static final String FRM_NAME_AYAT = "FRM_NAME_AYAT";
    public static final int FRM_FIELD_AYAT_ID = 0;
    public static final int FRM_FIELD_AYAT_TITLE = 1;
    public static final int FRM_FIELD_AYAT_DESCRIPTION = 2;
    public static final int FRM_FIELD_PASAL_ID = 3;
    public static final int FRM_FIELD_AYAT_PAGE = 4;


    public static String[] fieldNames = {
        "FRM_FIELD_AYAT_ID",
        "FRM_FIELD_AYAT_TITLE",
        "FRM_FIELD_AYAT_DESCRIPTION",
        "FRM_FIELD_PASAL_ID",
        "FRM_FIELD_AYAT_PAGE"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, TYPE_STRING + ENTRY_REQUIRED, 
        TYPE_STRING + ENTRY_REQUIRED, TYPE_LONG, TYPE_INT
    };

    public FrmWarningReprimandAyat() {
    }

    public FrmWarningReprimandAyat(WarningReprimandAyat warningReprimandAyat) {
        this.warningReprimandAyat = warningReprimandAyat;
    }

    public FrmWarningReprimandAyat(HttpServletRequest request, WarningReprimandAyat warningReprimandAyat) {
        super(new FrmWarningReprimandAyat(warningReprimandAyat), request);
        this.warningReprimandAyat = warningReprimandAyat;
    }

    public String getFormName() {
        return FRM_NAME_AYAT;
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

    public WarningReprimandAyat getEntityObject() {
        return warningReprimandAyat;
    }

    public void requestEntityObject(WarningReprimandAyat warningReprimandAyat) {
        try {
            this.requestParam();
            warningReprimandAyat.setAyatTitle(getString(FRM_FIELD_AYAT_TITLE));
            warningReprimandAyat.setAyatDescription(getString(FRM_FIELD_AYAT_DESCRIPTION));
            warningReprimandAyat.setPasalId(getLong(FRM_FIELD_PASAL_ID));
            warningReprimandAyat.setAyatPage(getInt(FRM_FIELD_AYAT_PAGE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
