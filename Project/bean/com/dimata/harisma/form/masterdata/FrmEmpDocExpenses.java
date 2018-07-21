/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Ari_20110930
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: FrmCompany
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Priska
 */

/* java package */

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;

public class FrmEmpDocExpenses extends FRMHandler implements I_FRMInterface, I_FRMType{
     private EmpDocExpenses empDocExpenses;

    public static final String FRM_NAME_DOC_EXPENSES = "FRM_NAME_DOC_EXPENSES";

    
   public static final int FRM_FIELD_EMP_DOC_EXPENSE_ID = 0;
   public static final int FRM_FIELD_DOC_ID = 1;
   public static final int FRM_FIELD_DOC_MASTER_EXPENSE_ID = 2;
   public static final int FRM_FIELD_BUDGET_VALUE = 3;
   public static final int FRM_FIELD_REAL_VALUE = 4;
   public static final int FRM_FIELD_EXPENSE_UNIT = 5;
   public static final int FRM_FIELD_TOTAL = 6;
   public static final int FRM_FIELD_DESCRIPTION = 7;
   public static final int FRM_FIELD_NOTE = 8;
    
    public static String[] fieldNames = {
       
        "FRM_FIELD_EMP_DOC_EXPENSE_ID",
        "FRM_FIELD_DOC_ID",
        "FRM_FIELD_DOC_MASTER_EXPENSE_ID",
        "FRM_FIELD_BUDGET_VALUE",
        "FRM_FIELD_REAL_VALUE",
        "FRM_FIELD_EXPENSE_UNIT",
        "FRM_FIELD_TOTAL",
        "FRM_FIELD_DESCRIPTION",
        "FRM_FIELD_NOTE"
    };

    public static int[] fieldTypes = {
        TYPE_LONG ,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmEmpDocExpenses() {
    }

    public FrmEmpDocExpenses(EmpDocExpenses empDocExpenses) {
        this.empDocExpenses = empDocExpenses;
    }

    public FrmEmpDocExpenses(HttpServletRequest request, EmpDocExpenses empDocExpenses) {
        super(new FrmEmpDocExpenses(empDocExpenses), request);
        this.empDocExpenses = empDocExpenses;
    }

    public String getFormName() {
        return FRM_NAME_DOC_EXPENSES;
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

    public EmpDocExpenses getEntityObject() {
        return empDocExpenses;
    }

    public void requestEntityObject(EmpDocExpenses empDocExpenses) {
        try {
            this.requestParam();
            empDocExpenses.setEmpDocId(getLong(FRM_FIELD_DOC_ID));
            empDocExpenses.setDocMasterExpenseId(getLong(FRM_FIELD_DOC_MASTER_EXPENSE_ID));
            empDocExpenses.setBudgetValue(getFloat(FRM_FIELD_BUDGET_VALUE));
            empDocExpenses.setRealvalue(getFloat(FRM_FIELD_REAL_VALUE));
            empDocExpenses.setExpenseUnit(getInt(FRM_FIELD_EXPENSE_UNIT));
            empDocExpenses.setTotal(getFloat(FRM_FIELD_TOTAL));
            empDocExpenses.setDescription(getString(FRM_FIELD_DESCRIPTION));
            empDocExpenses.setNote(getString(FRM_FIELD_NOTE));
            
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
