/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

/**
 *
 * @author dimata005
 */
//public class FrmOutSourcePlan {
//    
//}
import com.dimata.harisma.entity.outsource.OutSourcePlan;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmOutSourcePlan extends FRMHandler implements I_FRMInterface, I_FRMType {

    private OutSourcePlan entOutSourcePlan;
    public static final String FRM_NAME_OUTSOURCEPLAN = "FRM_NAME_OUTSOURCEPLAN";
    public static final int FRM_FIELD_OUTSOURCEPLANID = 0;
    public static final int FRM_FIELD_PLANYEAR = 1;
    public static final int FRM_FIELD_TITLE = 2;
    public static final int FRM_FIELD_COMPANYID = 3;
    public static final int FRM_FIELD_CREATEDDATE = 4;
    public static final int FRM_FIELD_CREATEDBYID = 5;
    public static final int FRM_FIELD_APPROVEDDATE = 6;
    public static final int FRM_FIELD_APPROVEBYID = 7;
    public static final int FRM_FIELD_NOTE = 8;
    public static final int FRM_FIELD_STATUS = 9;
    public static final int FRM_FIELD_VALID_FROM = 10;
    public static final int FRM_FIELD_VALID_TO = 11;
    public static String[] fieldNames = {
        "FRM_FIELD_OUTSOURCEPLANID",
        "FRM_FIELD_PLANYEAR",
        "FRM_FIELD_TITLE",
        "FRM_FIELD_COMPANYID",
        "FRM_FIELD_CREATEDDATE",
        "FRM_FIELD_CREATEDBYID",
        "FRM_FIELD_APPROVEDDATE",
        "FRM_FIELD_APPROVEBYID",
        "FRM_FIELD_NOTE",
        "FRM_FIELD_STATUS",
        "FRM_FIELD_VALID_FROM",
        "FRM_FIELD_VALID_TO"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_INT,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE
    };

    public FrmOutSourcePlan() {
    }

    public FrmOutSourcePlan(OutSourcePlan entOutSourcePlan) {
        this.entOutSourcePlan = entOutSourcePlan;
    }

    public FrmOutSourcePlan(HttpServletRequest request, OutSourcePlan entOutSourcePlan) {
        super(new FrmOutSourcePlan(entOutSourcePlan), request);
        this.entOutSourcePlan = entOutSourcePlan;
    }

    public String getFormName() {
        return FRM_NAME_OUTSOURCEPLAN;
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

    public OutSourcePlan getEntityObject() {
        return entOutSourcePlan;
    }

    public void requestEntityObject(OutSourcePlan entOutSourcePlan) {
        try {
            this.requestParam();
            //entOutSourcePlan.setOutsourcePlanId(getLong(FRM_FIELD_OUTSOURCEPLANID));
            entOutSourcePlan.setPlanYear(getInt(FRM_FIELD_PLANYEAR));
            entOutSourcePlan.setTitle(getString(FRM_FIELD_TITLE));
            entOutSourcePlan.setCompanyId(getLong(FRM_FIELD_COMPANYID));
            entOutSourcePlan.setCreatedDate(getDate(FRM_FIELD_CREATEDDATE));
            entOutSourcePlan.setCreatedById(getLong(FRM_FIELD_CREATEDBYID));
            entOutSourcePlan.setApprovedDate(getDate(FRM_FIELD_APPROVEDDATE));
            entOutSourcePlan.setApproveById(getLong(FRM_FIELD_APPROVEBYID));
            entOutSourcePlan.setNote(getString(FRM_FIELD_NOTE));
            entOutSourcePlan.setStatus(getInt(FRM_FIELD_STATUS));
            entOutSourcePlan.setValidFrom(getDate(FRM_FIELD_VALID_FROM));
            entOutSourcePlan.setValidTo(getDate(FRM_FIELD_VALID_TO));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}