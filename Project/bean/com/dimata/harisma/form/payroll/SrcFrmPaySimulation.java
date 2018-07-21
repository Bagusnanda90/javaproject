/* 
 * Form Name  	:  FrmPaySimulation.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [Kartika] 
 * @version  	:  [1.0] 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...]
 * *****************************************************************
 */
package com.dimata.harisma.form.payroll;

/* java package */
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.search.SrcPaySimulation ;

public class SrcFrmPaySimulation extends FRMHandler implements I_FRMInterface, I_FRMType {

    private SrcPaySimulation srcPaySimulation;

    public static final String FRM_PAY_SIMULATION = "FRM_PAY_SIMULATION";

    public static final int FRM_FIELD_PAY_SIMULATION_ID = 0;
    public static final int FRM_FIELD_TITLE = 1;
    public static final int FRM_FIELD_OBJECTIVES = 2;
    public static final int FRM_FIELD_CREATED_DATE_FROM = 3;
    public static final int FRM_FIELD_CREATED_DATE_TO = 4;
    public static final int FRM_FIELD_CREATED_BY_ID = 5;
    public static final int FRM_FIELD_REQUEST_DATE_FROM = 6;
    public static final int FRM_FIELD_REQUEST_DATE_TO = 7;
    public static final int FRM_FIELD_REQUEST_BY_ID = 8;
    public static final int FRM_FIELD_DUE_DATE_FROM = 9;
    public static final int FRM_FIELD_DUE_DATE_TO = 10;
    public static final int FRM_FIELD_STATUS_DOC = 11;
    public static final int FRM_FIELD_MAX_TOTAL_BUDGET_FROM = 12;
    public static final int FRM_FIELD_MAX_TOTAL_BUDGET_TO = 13;
    public static final int FRM_FIELD_MAX_ADD_EMPL_FROM = 14;
    public static final int FRM_FIELD_MAX_ADD_EMPL_TO = 15;
    public static final int FRM_FIELD_SOURCE_PAY_PERIOD_ID = 16;
    public static final int FRM_FIELD_SORT_BY = 17;
    
    public static String[] fieldNames = {
        "FRM_FIELD_PAY_SIMULATION_ID",
        "FRM_FIELD_TITLE",
        "FRM_FIELD_OBJECTIVES",
        "FRM_FIELD_CREATED_DATE_FROM",
        "FRM_FIELD_CREATED_DATE_TO",
        "FRM_FIELD_CREATED_BY_ID",
        "FRM_FIELD_REQUEST_DATE_FROM",
        "FRM_FIELD_REQUEST_DATE_TO",
        "FRM_FIELD_REQUEST_BY_ID",
        "FRM_FIELD_DUE_DATE_FROM",
        "FRM_FIELD_DUE_DATE_TO",
        "FRM_FIELD_STATUS_DOC",
        "FRM_FIELD_MAX_TOTAL_BUDGET_FROM",
        "FRM_FIELD_MAX_TOTAL_BUDGET_TO",
        "FRM_FIELD_MAX_ADD_EMPL_FROM",
        "FRM_FIELD_MAX_ADD_EMPL_TO",
        "FRM_FIELD_SOURCE_PAY_PERIOD_ID",
        "FRM_FIELD_SORT_BY"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING ,
        TYPE_STRING ,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_STRING
    };

    public SrcFrmPaySimulation() {
    }

    public SrcFrmPaySimulation(SrcPaySimulation paySimulation) {
        this.srcPaySimulation = paySimulation;
    }

    public SrcFrmPaySimulation(HttpServletRequest request, SrcPaySimulation paySimulation) {
        super(new SrcFrmPaySimulation(paySimulation), request);
        this.srcPaySimulation = paySimulation;
    }

    public String getFormName() {
        return FRM_PAY_SIMULATION;
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

    public SrcPaySimulation getEntityObject() {
        return srcPaySimulation;
    }

    public void requestEntityObject(SrcPaySimulation srcPaySimulation) {
        try {
            this.requestParam();
            srcPaySimulation.setCreadedById(getLong(FRM_FIELD_CREATED_BY_ID));
            srcPaySimulation.setCreatedDate(getDate(FRM_FIELD_CREATED_DATE_FROM));
            srcPaySimulation.setDueDateFrom(getDate(FRM_FIELD_DUE_DATE_FROM));
            srcPaySimulation.setMaxAddEmployeeFrom(getInt(FRM_FIELD_MAX_ADD_EMPL_FROM));
            srcPaySimulation.setMaxTotalBudgetFrom(getDouble(FRM_FIELD_MAX_TOTAL_BUDGET_FROM));
            srcPaySimulation.setObjectives(getString(FRM_FIELD_OBJECTIVES));
            srcPaySimulation.setRequestDateFrom(getDate(FRM_FIELD_REQUEST_DATE_FROM));
            srcPaySimulation.setRequestedById(getLong(FRM_FIELD_REQUEST_BY_ID));
            srcPaySimulation.setSourcePayPeriodId(getLong(FRM_FIELD_SOURCE_PAY_PERIOD_ID));
            srcPaySimulation.setStatusDoc(getVectorInt(fieldNames[FRM_FIELD_STATUS_DOC]));
            srcPaySimulation.setTitle(getString(FRM_FIELD_TITLE));          
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
