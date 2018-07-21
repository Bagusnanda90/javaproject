/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

/**
 *
 * @author dimata005
 */
import com.dimata.harisma.entity.outsource.OutSourcePlanDetail;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmOutSourcePlanDetail extends FRMHandler implements I_FRMInterface, I_FRMType {

    private OutSourcePlanDetail entOutSourcePlanDetail;
    public static final String FRM_NAME_OUTSOURCEPLANDETAIL = "FRM_NAME_OUTSOURCEPLANDETAIL";
    public static final int FRM_FIELD_OUTSOURCEPLANDETAILID = 0;
    public static final int FRM_FIELD_OUTSOURCEPLANID = 1;
    public static final int FRM_FIELD_POSITIONID = 2;
    public static final int FRM_FIELD_COSTPERPERSON = 3;
    public static final int FRM_FIELD_GENERALINFO = 4;
    public static final int FRM_FIELD_TYPEOFCONTRACT = 5;
    public static final int FRM_FIELD_CONTRACTPERIOD = 6;
    public static final int FRM_FIELD_OBJECTIVES = 7;
    public static final int FRM_FIELD_COSTNBENEFITANLYSIS = 8;
    public static final int FRM_FIELD_COSTTOTAL = 9;
    public static final int FRM_FIELD_RISKANALIYS = 10;
    public static final int FRM_FIELD_DESCRIPTION = 11;
    public static String[] fieldNames = {
        "FRM_FIELD_OUTSOURCEPLANDETAILID",
        "FRM_FIELD_OUTSOURCEPLANID",
        "FRM_FIELD_POSITIONID",
        "FRM_FIELD_COSTPERPERSON",
        "FRM_FIELD_GENERALINFO",
        "FRM_FIELD_TYPEOFCONTRACT",
        "FRM_FIELD_CONTRACTPERIOD",
        "FRM_FIELD_OBJECTIVES",
        "FRM_FIELD_COSTNBENEFITANLYSIS",
        "FRM_FIELD_COSTTOTAL",
        "FRM_FIELD_RISKANALIYS",
        "FRM_FIELD_DESCRIPTION"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmOutSourcePlanDetail() {
    }

    public FrmOutSourcePlanDetail(OutSourcePlanDetail entOutSourcePlanDetail) {
        this.entOutSourcePlanDetail = entOutSourcePlanDetail;
    }

    public FrmOutSourcePlanDetail(HttpServletRequest request, OutSourcePlanDetail entOutSourcePlanDetail) {
        super(new FrmOutSourcePlanDetail(entOutSourcePlanDetail), request);
        this.entOutSourcePlanDetail = entOutSourcePlanDetail;
    }

    public String getFormName() {
        return FRM_NAME_OUTSOURCEPLANDETAIL;
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

    public OutSourcePlanDetail getEntityObject() {
        return entOutSourcePlanDetail;
    }

    public void requestEntityObject(OutSourcePlanDetail entOutSourcePlanDetail) {
        try {
            this.requestParam();
            entOutSourcePlanDetail.setOutsourcePlanId(getLong(FRM_FIELD_OUTSOURCEPLANID));
            entOutSourcePlanDetail.setPositionId(getLong(FRM_FIELD_POSITIONID));
            entOutSourcePlanDetail.setCostPerPerson(getFloat(FRM_FIELD_COSTPERPERSON));
            entOutSourcePlanDetail.setGeneralInfo(getString(FRM_FIELD_GENERALINFO));
            entOutSourcePlanDetail.setTypeOfContract(getString(FRM_FIELD_TYPEOFCONTRACT));
            entOutSourcePlanDetail.setContractPeriod(getFloat(FRM_FIELD_CONTRACTPERIOD));
            entOutSourcePlanDetail.setObjectives(getString(FRM_FIELD_OBJECTIVES));
            entOutSourcePlanDetail.setCostNBenefitAnlysis(getString(FRM_FIELD_COSTNBENEFITANLYSIS));
            entOutSourcePlanDetail.setCostTotal(getFloat(FRM_FIELD_COSTTOTAL));
            entOutSourcePlanDetail.setRiskAnaliys(getString(FRM_FIELD_RISKANALIYS));
            entOutSourcePlanDetail.setDescription(getString(FRM_FIELD_DESCRIPTION));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}