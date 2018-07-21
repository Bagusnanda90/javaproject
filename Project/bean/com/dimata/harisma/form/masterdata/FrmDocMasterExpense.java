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

public class FrmDocMasterExpense extends FRMHandler implements I_FRMInterface, I_FRMType{
     private DocMasterExpense docMasterExpense;

    public static final String FRM_NAME_DOC_MASTER_EXPENSE = "FRM_NAME_DOC_MASTER_EXPENSE";

    
       public static final int FRM_FIELD_DOC_MASTER_EXPENSE_ID = 0;
       public static final int FRM_FIELD_DOC_MASTER_ID = 1;
       public static final int FRM_FIELD_BUDGET_MIN = 2;
       public static final int FRM_FIELD_BUDGET_MAX = 3;
       public static final int FRM_FIELD_UNIT_TYPE = 4;
       public static final int FRM_FIELD_UNIT_NAME = 5;
       public static final int FRM_FIELD_DESCRIPTION = 6;
       public static final int FRM_FIELD_DOC_EXPENSE_ID = 7;
    
    
    public static String[] fieldNames = {
       
        "FRM_FIELD_DOC_MASTER_EXPENSE_ID",
        "FRM_FIELD_DOC_MASTER_ID",
        "FRM_FIELD_BUDGET_MIN",
        "FRM_FIELD_BUDGET_MAX",
        "FRM_FIELD_UNIT_TYPE",
        "FRM_FIELD_UNIT_NAME",
        "FRM_FIELD_DESCRIPTION",
        "FRM_FIELD_DOC_EXPENSE_ID"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING, 
        TYPE_LONG
    };

    //update by priska
    public static final int[] unittypeValue = {0,1};    
    public static final String[] unittypeKey = {"ITEM","TOTAL"};
    
    public static Vector getUnitTypeValue(){
        Vector UnitType = new Vector();
        for (int i= 0; i< unittypeValue.length; i++){
            UnitType.add(String.valueOf(unittypeValue[i]));
        }
        return UnitType;
    }
    
     public static Vector getUnitTypeKey(){
        Vector UnitType = new Vector();
        for (int i = 0; i < unittypeKey.length; i++ ){
            UnitType.add(unittypeKey[i]);
        }
        return UnitType;
    }   
    
    public FrmDocMasterExpense() {
    }

    public FrmDocMasterExpense(DocMasterExpense docMasterExpense) {
        this.docMasterExpense = docMasterExpense;
    }

    public FrmDocMasterExpense(HttpServletRequest request, DocMasterExpense docMasterExpense) {
        super(new FrmDocMasterExpense(docMasterExpense), request);
        this.docMasterExpense = docMasterExpense;
    }

    public String getFormName() {
        return FRM_NAME_DOC_MASTER_EXPENSE;
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

    public DocMasterExpense getEntityObject() {
        return docMasterExpense;
    }

    public void requestEntityObject(DocMasterExpense docMasterExpense) {
        try {
            this.requestParam();
            docMasterExpense.setDoc_master_id(getLong(FRM_FIELD_DOC_MASTER_ID));
            docMasterExpense.setBudget_min(getFloat(FRM_FIELD_BUDGET_MIN));
            docMasterExpense.setBudget_max(getFloat(FRM_FIELD_BUDGET_MAX));
            docMasterExpense.setUnit_type(getInt(FRM_FIELD_UNIT_TYPE));
            docMasterExpense.setUnit_name(getString(FRM_FIELD_UNIT_NAME));
            docMasterExpense.setDescription(getString(FRM_FIELD_DESCRIPTION));
            docMasterExpense.setDoc_expense_id(getLong(FRM_FIELD_DOC_EXPENSE_ID));
            
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
