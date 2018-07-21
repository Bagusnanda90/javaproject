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

public class FrmDocMasterFlow extends FRMHandler implements I_FRMInterface, I_FRMType{
     private DocMasterFlow docMasterFlow;

    public static final String FRM_NAME_DOC_MASTER_FLOW = "FRM_NAME_DOC_MASTER_FLOW";

  public static final int FRM_FIELD_DOC_MASTER_FLOW_ID = 0;
  public static final int FRM_FIELD_DOC_MASTER_ID = 1;
  public static final int FRM_FIELD_FLOW_TITLE = 2;
  public static final int FRM_FIELD_FLOW_INDEX = 3;
  public static final int FRM_FIELD_COMPANY_ID = 4;
  public static final int FRM_FIELD_DIVISION_ID = 5;
  public static final int FRM_FIELD_DEPARTMENT_ID = 6;
  public static final int FRM_FIELD_LEVEL_ID = 7;
  public static final int FRM_FIELD_POSITION_ID = 8;
  public static final int FRM_FIELD_EMPLOYEE_ID = 9;
    
    public static String[] fieldNames = {
       
    "FRM_FIELD_DOC_MASTER_FLOW_ID",
    "FRM_FIELD_DOC_MASTER_ID",
    "FRM_FIELD_FLOW_TITLE",
    "FRM_FIELD_FLOW_INDEX",
    "FRM_FIELD_COMPANY_ID",
    "FRM_FIELD_DIVISION_ID",
    "FRM_FIELD_DEPARTMENT_ID",
    "FRM_FIELD_LEVEL_ID",
    "FRM_FIELD_POSITION_ID",
    "FRM_FIELD_EMPLOYEE_ID"
    };

    public static int[] fieldTypes = {
    TYPE_LONG,
    TYPE_LONG,
    TYPE_STRING,
    TYPE_INT,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_LONG
    };

     //update by priska
    public static final int[] indexValue = {0,1,2,3,4,5,6,7,8};    
    public static final String[] indexKey = {"0","1","2","3","4","5","6","7","8"};
    
    public static Vector getindexValue(){
        Vector indexv = new Vector();
        for (int i= 0; i< indexValue.length; i++){
            indexv.add(String.valueOf(indexValue[i]));
        }
        return indexv;
    }
    
     public static Vector getindexKey(){
        Vector indexk = new Vector();
        for (int i = 0; i < indexKey.length; i++ ){
            indexk.add(indexKey[i]);
        }
        return indexk;
    }   
    
    public FrmDocMasterFlow() {
    }

    public FrmDocMasterFlow(DocMasterFlow docMasterFlow) {
        this.docMasterFlow = docMasterFlow;
    }

    public FrmDocMasterFlow(HttpServletRequest request, DocMasterFlow docMasterFlow) {
        super(new FrmDocMasterFlow(docMasterFlow), request);
        this.docMasterFlow = docMasterFlow;
    }

    public String getFormName() {
        return FRM_NAME_DOC_MASTER_FLOW;
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

    public DocMasterFlow getEntityObject() {
        return docMasterFlow;
    }

    public void requestEntityObject(DocMasterFlow docMasterFlow) {
        try {
            this.requestParam();
    docMasterFlow.setDoc_master_id(getLong(FRM_FIELD_DOC_MASTER_ID));
    docMasterFlow.setFlow_title(getString(FRM_FIELD_FLOW_TITLE));
    docMasterFlow.setFlow_index(getInt(FRM_FIELD_FLOW_INDEX));
    docMasterFlow.setCompany_id(getLong(FRM_FIELD_COMPANY_ID));
    docMasterFlow.setDivision_id(getLong(FRM_FIELD_DIVISION_ID));
    docMasterFlow.setDepartment_id(getLong(FRM_FIELD_DEPARTMENT_ID));
    docMasterFlow.setLevel_id(getLong(FRM_FIELD_LEVEL_ID));
    docMasterFlow.setPosition_id(getLong(FRM_FIELD_POSITION_ID));
    docMasterFlow.setEmployee_id(getLong(FRM_FIELD_EMPLOYEE_ID));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
