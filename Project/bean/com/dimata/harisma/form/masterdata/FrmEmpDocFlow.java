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

import com.dimata.harisma.entity.configrewardnpunisment.EntriOpnameSales;
import com.dimata.harisma.entity.configrewardnpunisment.PstEntriOpnameSales;
import com.dimata.harisma.entity.employee.Employee;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;

public class FrmEmpDocFlow extends FRMHandler implements I_FRMInterface, I_FRMType{
    private EmpDocFlow empDocFlow;

    public static final String FRM_NAME_EMP_DOC_FLOW = "FRM_NAME_EMP_DOC_FLOW";

    public static final int FRM_FIELD_EMP_DOC_FLOW_ID = 0;
    public static final int FRM_FIELD_FLOW_TITLE = 1;
    public static final int FRM_FIELD_FLOW_INDEX = 2;
    public static final int FRM_FIELD_SIGNED_BY = 3;
    public static final int FRM_FIELD_SIGNED_DATE = 4;
    public static final int FRM_FIELD_NOTE = 5;
    public static final int FRM_FIELD_EMP_DOC_ID = 6;
    
    public static String[] fieldNames = {
       
        "FRM_FIELD_EMP_DOC_FLOW_ID",
        "FRM_FIELD_FLOW_TITLE",
        "FRM_FIELD_FLOW_INDEX",
        "FRM_FIELD_SIGNED_BY",
        "FRM_FIELD_SIGNED_DATE",
        "FRM_FIELD_NOTE",
        "FRM_FIELD_EMP_DOC_ID"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_LONG
    };

    public FrmEmpDocFlow() {
    }

    public FrmEmpDocFlow(EmpDocFlow empDocFlow) {
        this.empDocFlow = empDocFlow;
    }

    public FrmEmpDocFlow(HttpServletRequest request, EmpDocFlow empDocFlow) {
        super(new FrmEmpDocFlow(empDocFlow), request);
        this.empDocFlow = empDocFlow;
    }

    public String getFormName() {
        return FRM_NAME_EMP_DOC_FLOW;
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

    public EmpDocFlow getEntityObject() {
        return empDocFlow;
    }

    public void requestEntityObject(EmpDocFlow empDocFlow) {
        try {
            this.requestParam();
            empDocFlow.setEmpDocId(getLong(FRM_FIELD_EMP_DOC_ID));
            empDocFlow.setFlowTitle(getString(FRM_FIELD_FLOW_TITLE));
            empDocFlow.setFlowIndex(getInt(FRM_FIELD_FLOW_INDEX));
            empDocFlow.setSignedBy(getLong(FRM_FIELD_SIGNED_BY));
            empDocFlow.setSignedDate(getDate(FRM_FIELD_SIGNED_DATE));
            empDocFlow.setNote(getString(FRM_FIELD_NOTE));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
