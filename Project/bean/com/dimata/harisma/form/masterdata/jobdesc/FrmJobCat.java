/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata.jobdesc;

import com.dimata.harisma.entity.masterdata.jobdesc.JobCat;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class FrmJobCat extends FRMHandler implements I_FRMInterface, I_FRMType {

    private JobCat entJobCat;
    public static final String FRM_NAME_HR_JOB_CATEGORY = "FRM_NAME_HR_JOB_CATEGORY";
    public static final int FRM_FIELD_JOB_CATEGORY_ID = 0;
    public static final int FRM_FIELD_CATEGORY_TITLE = 1;
    public static final int FRM_FIELD_DESCRIPTION = 2;
    public static String[] fieldNames = {
        "FRM_FIELD_JOB_CATEGORY_ID",
        "FRM_FIELD_CATEGORY_TITLE",
        "FRM_FIELD_DESCRIPTION"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmJobCat() {
    }

    public FrmJobCat(JobCat entJobCat) {
        this.entJobCat = entJobCat;
    }

    public FrmJobCat(HttpServletRequest request, JobCat entJobCat) {
        super(new FrmJobCat(entJobCat), request);
        this.entJobCat = entJobCat;
    }

    public String getFormName() {
        return FRM_NAME_HR_JOB_CATEGORY;
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

    public JobCat getEntityObject() {
        return entJobCat;
    }

    public void requestEntityObject(JobCat entJobCat) {
        try {
            this.requestParam();
            entJobCat.setCategoryTitle(getString(FRM_FIELD_CATEGORY_TITLE));
            entJobCat.setDescription(getString(FRM_FIELD_DESCRIPTION));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
