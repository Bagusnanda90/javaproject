/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee;

import com.dimata.harisma.entity.employee.EmpRelvtDocPage;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class FrmEmpRelvtDocPage extends FRMHandler implements I_FRMInterface, I_FRMType {

    private EmpRelvtDocPage entEmpRelvtDocPage;
    public static final String FRM_NAME_EMP_RELVT_DOC_PAGE = "FRM_NAME_EMP_RELVT_DOC_PAGE";
    public static final int FRM_FIELD_EMP_RELVT_DOC_PAGE_ID = 0;
    public static final int FRM_FIELD_PAGE_TITLE = 1;
    public static final int FRM_FIELD_PAGE_DESC = 2;
    public static final int FRM_FIELD_DOC_RELEVANT_ID = 3;
    public static final int FRM_FIELD_FILE_NAME = 4;
    public static String[] fieldNames = {
        "FRM_FIELD_EMP_RELVT_DOC_PAGE_ID",
        "FRM_FIELD_PAGE_TITLE",
        "FRM_FIELD_PAGE_DESC",
        "FRM_FIELD_DOC_RELEVANT_ID",
        "FRM_FIELD_FILE_NAME"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING
    };

    public FrmEmpRelvtDocPage() {
    }

    public FrmEmpRelvtDocPage(EmpRelvtDocPage entEmpRelvtDocPage) {
        this.entEmpRelvtDocPage = entEmpRelvtDocPage;
    }

    public FrmEmpRelvtDocPage(HttpServletRequest request, EmpRelvtDocPage entEmpRelvtDocPage) {
        super(new FrmEmpRelvtDocPage(entEmpRelvtDocPage), request);
        this.entEmpRelvtDocPage = entEmpRelvtDocPage;
    }

    public String getFormName() {
        return FRM_NAME_EMP_RELVT_DOC_PAGE;
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

    public EmpRelvtDocPage getEntityObject() {
        return entEmpRelvtDocPage;
    }

    public void requestEntityObject(EmpRelvtDocPage entEmpRelvtDocPage) {
        try {
            this.requestParam();
            entEmpRelvtDocPage.setPageTitle(getString(FRM_FIELD_PAGE_TITLE));
            entEmpRelvtDocPage.setPageDesc(getString(FRM_FIELD_PAGE_DESC));
            entEmpRelvtDocPage.setDocRelevantId(getLong(FRM_FIELD_DOC_RELEVANT_ID));
            entEmpRelvtDocPage.setFileName(getString(FRM_FIELD_FILE_NAME));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
