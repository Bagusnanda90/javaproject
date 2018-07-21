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
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.payroll.PaySimulation;
import com.dimata.harisma.entity.payroll.PaySimulationStructure;
import com.dimata.harisma.entity.payroll.PstPaySimulationStructure;
import java.util.Vector;

public class FrmPaySimulation extends FRMHandler implements I_FRMInterface, I_FRMType {

    private PaySimulation paySimulation;

    public static final String FRM_PAY_SIMULATION = "FRM_PAY_SIMULATION";

    public static final int FRM_FIELD_PAY_SIMULATION_ID = 0;
    public static final int FRM_FIELD_TITLE = 1;
    public static final int FRM_FIELD_OBJECTIVES = 2;
    public static final int FRM_FIELD_CREATED_DATE = 3;
    public static final int FRM_FIELD_CREATED_BY_ID = 4;
    public static final int FRM_FIELD_REQUEST_DATE = 5;
    public static final int FRM_FIELD_REQUEST_BY_ID = 6;
    public static final int FRM_FIELD_DUE_DATE = 7;
    public static final int FRM_FIELD_STATUS_DOC = 8;
    public static final int FRM_FIELD_MAX_TOTAL_BUDGET = 9;
    public static final int FRM_FIELD_MAX_ADD_EMPL = 10;
    public static final int FRM_FIELD_SOURCE_PAY_PERIOD_ID = 11;
    public static final int FRM_FIELD_EMPLOYEE_CATEGORIES = 12;
    public static final int FRM_FIELD_PAYROLL_COMPONENTS = 13;

    public static String[] fieldNames = {
        "FRM_FIELD_PAY_SIMULATION_ID",
        "FRM_FIELD_TITLE",
        "FRM_FIELD_OBJECTIVES",
        "FRM_FIELD_CREATED_DATE",
        "FRM_FIELD_CREATED_BY_ID",
        "FRM_FIELD_REQUEST_DATE",
        "FRM_FIELD_REQUEST_BY_ID",
        "FRM_FIELD_DUE_DATE",
        "FRM_FIELD_STATUS_DOC",
        "FRM_FIELD_MAX_TOTAL_BUDGET",
        "FRM_FIELD_MAX_ADD_EMPL",
        "FRM_FIELD_SOURCE_PAY_PERIOD_ID",
        "FRM_FIELD_EMPLOYEE_CATEGORIES",
        "FRM_FIELD_PAYROLL_COMPONENTS"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_VECTOR_LONG,
        TYPE_VECTOR_STRING
    };

    public FrmPaySimulation() {
    }

    public FrmPaySimulation(PaySimulation paySimulation) {
        this.paySimulation = paySimulation;
    }

    public FrmPaySimulation(HttpServletRequest request, PaySimulation paySimulation) {
        super(new FrmPaySimulation(paySimulation), request);
        this.paySimulation = paySimulation;
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

    public PaySimulation getEntityObject() {
        return paySimulation;
    }

    public void requestEntityObject(PaySimulation paySimulation) {
        try {
            this.requestParam();
            paySimulation.setOID(getLong(FRM_FIELD_PAY_SIMULATION_ID));
            paySimulation.setCreadedById(getLong(FRM_FIELD_CREATED_BY_ID));
            paySimulation.setCreatedDate(getDate(FRM_FIELD_CREATED_DATE));
            paySimulation.setDueDate(getDate(FRM_FIELD_DUE_DATE));
            paySimulation.setMaxAddEmployee(getInt(FRM_FIELD_MAX_ADD_EMPL));
            paySimulation.setMaxTotalBudget(getDouble(FRM_FIELD_MAX_TOTAL_BUDGET));
            paySimulation.setObjectives(getString(FRM_FIELD_OBJECTIVES));
            paySimulation.setRequestDate(getDate(FRM_FIELD_REQUEST_DATE));
            paySimulation.setRequestedById(getLong(FRM_FIELD_REQUEST_BY_ID));
            paySimulation.setSourcePayPeriodId(getLong(FRM_FIELD_SOURCE_PAY_PERIOD_ID));
            paySimulation.setStatusDoc(getInt(FRM_FIELD_STATUS_DOC));
            paySimulation.setTitle(getString(FRM_FIELD_TITLE));
            paySimulation.setEmployeeCategoryIds(getVectorLong(fieldNames[FRM_FIELD_EMPLOYEE_CATEGORIES]));
            paySimulation.setPayrollComponents(getVectorString(fieldNames[FRM_FIELD_PAYROLL_COMPONENTS]));

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
    public void requestPaySimulationStructure(PaySimulation paySimulation) {
        try {
            this.requestParam();
            
            Vector vPaySimStruct = PstPaySimulationStructure.listByPaySimulation(paySimulation);
            if (vPaySimStruct != null && vPaySimStruct.size() > 0) {
                paySimulation.setPaySimulationStruct(vPaySimStruct);
                for (int idx = 0; idx < vPaySimStruct.size(); idx++) {
                    PaySimulationStructure paySimStruct = (PaySimulationStructure) vPaySimStruct.get(idx);
                    double addPayAmount = getParamDouble("ADD_AMOUNT_" + paySimStruct.getOID());// PstPaySimulationStructure.getKeyMap(paySimStruct));
                    int addEmployee = getParamInt("ADD_EMPLOYEE_" + paySimStruct.getOID());//PstPaySimulationStructure.getKeyMap(paySimStruct)));
                    paySimStruct.setSalaryAmountAdd(addPayAmount);
                    paySimStruct.setNewEmployeeAdd(addEmployee);
                }
            }
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
