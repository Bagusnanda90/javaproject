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

public class FrmDocExpenses extends FRMHandler implements I_FRMInterface, I_FRMType{
     private DocExpenses docExpenses;

    public static final String FRM_NAME_DOC_EXPENSES = "FRM_NAME_DOC_EXPENSES";

    public static final int FRM_FIELD_DOC_EXPENSE_ID = 0;
    public static final int FRM_FIELD_EXPENSE_NAME = 1;
    public static final int FRM_FIELD_PLAN_EXPENSE_VALUE = 2;
    public static final int FRM_FIELD_NOTE = 3;

    public static String[] fieldNames = {
       
        "FRM_FIELD_DOC_EXPENSE_ID",
        "FRM_FIELD_EXPENSE_NAME",
        "FRM_FIELD_PLAN_EXPENSE_VALUE",
        "FRM_FIELD_NOTE"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_FLOAT,
        TYPE_STRING
    };

    public FrmDocExpenses() {
    }

    public FrmDocExpenses(DocExpenses docExpenses) {
        this.docExpenses = docExpenses;
    }

    public FrmDocExpenses(HttpServletRequest request, DocExpenses docExpenses) {
        super(new FrmDocExpenses(docExpenses), request);
        this.docExpenses = docExpenses;
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

    public DocExpenses getEntityObject() {
        return docExpenses;
    }

    public void requestEntityObject(DocExpenses docExpenses) {
        try {
            this.requestParam();
            docExpenses.setExpense_name(getString(FRM_FIELD_EXPENSE_NAME));
            docExpenses.setPlan_expense_value(getFloat(FRM_FIELD_PLAN_EXPENSE_VALUE));
            docExpenses.setNote(getString(FRM_FIELD_NOTE));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
