/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Ari_20110930
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: FrmDocMasterAction
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Wiweka
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

public class FrmDocMasterAction extends FRMHandler implements I_FRMInterface, I_FRMType{
     private DocMasterAction docMasterAction;
     private DocMasterActionParam docMasterActionParam;
     private Vector vDocMasterActionParam = new Vector();
     
  public static final String FRM_NAME_DOC_MASTER_ACTION = "FRM_NAME_DOC_MASTER_ACTION";

  public static final int FRM_FIELD_DOC_ACTION_ID = 0;
  public static final int FRM_FIELD_DOC_MASTER_ID = 1;
  public static final int FRM_FIELD_ACTION_NAME = 2;
  public static final int FRM_FIELD_ACTION_TITLE = 3;
  
  public static final int FRM_FIELD_DOC_ACTION_PARAM_ID = 4;
  public static final int FRM_FIELD_ACTION_PARAMETER = 5;
  public static final int FRM_FIELD_OBJECT_NAME = 6;
  public static final int FRM_FIELD_OBJECT_ATTRIBUT = 7;

    public static String[] fieldNames = {
    "FRM_FIELD_DOC_ACTION_ID",
    "FRM_FIELD_DOC_MASTER_ID",
    "FRM_FIELD_ACTION_NAME",
    "FRM_FIELD_ACTION_TITLE",
    
    
    "FRM_FIELD_DOC_ACTION_PARAM_ID",
    "FRM_FIELD_ACTION_PARAMETER",
    "FRM_FIELD_OBJECT_NAME",
    "FRM_FIELD_OBJECT_ATTRIBUT"
    };

    public static int[] fieldTypes = {
    TYPE_LONG,
    TYPE_LONG,
    TYPE_STRING,
    TYPE_STRING,
    
    TYPE_LONG,
    TYPE_STRING,
    TYPE_STRING,
    TYPE_STRING
    };

    public FrmDocMasterAction() {
    }

    public FrmDocMasterAction(DocMasterAction docMasterAction) {
        this.docMasterAction = docMasterAction;
    }

    public FrmDocMasterAction(HttpServletRequest request, DocMasterAction docMasterAction) {
        super(new FrmDocMasterAction(docMasterAction), request);
        this.docMasterAction = docMasterAction;
    }

    public String getFormName() {
        return FRM_NAME_DOC_MASTER_ACTION;
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

    public DocMasterAction getEntityObject() {
        return docMasterAction;
    }

    public void requestEntityObject(DocMasterAction docMasterAction) {
        try {
            this.requestParam();
            docMasterAction.setDocMasterId(getLong(FRM_FIELD_DOC_MASTER_ID));
            docMasterAction.setActionName(getString(FRM_FIELD_ACTION_NAME));
            docMasterAction.setActionTitle(getString(FRM_FIELD_ACTION_TITLE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

        public void requestEntityObjectMultiple(String actionNameKey) {
        try {
            this.requestParam();
   
        int cekIndex = DocMasterActionClass.getIndexActionValue(actionNameKey);
        String[] actionListParameter = DocMasterActionClass.actionListParameterKey[cekIndex];
    
        for (int i = 0; i < actionListParameter.length;i++ ){
            DocMasterActionParam docMasterActionParam = new DocMasterActionParam();
            
              docMasterActionParam.setDocActionId(this.getParamLong(fieldNames[FRM_FIELD_DOC_ACTION_ID]+i));     
              docMasterActionParam.setActionParameter(this.getParamString(fieldNames[FRM_FIELD_ACTION_PARAMETER]+i));  
              docMasterActionParam.setObjectAtribut(this.getParamString(fieldNames[FRM_FIELD_OBJECT_ATTRIBUT]+i));
              docMasterActionParam.setObjectName(this.getParamString(fieldNames[FRM_FIELD_OBJECT_NAME]+i));
              
              
              
//            docMasterActionParam.setDocActionId(getLong(FRM_FIELD_DOC_ACTION_PARAM_ID)+i);      
//            docMasterActionParam.setActionParameter(getString(FRM_FIELD_ACTION_PARAMETER)+i);
//            docMasterActionParam.setObjectAtribut(getString(FRM_FIELD_OBJECT_ATTRIBUT)+i);
//            docMasterActionParam.setObjectName(getString(FRM_FIELD_OBJECT_NAME)+i); 
//            docMasterActionParam.setDocActionId(getLong(FRM_FIELD_DOC_ACTION_ID)+i);  
            vDocMasterActionParam.add(docMasterActionParam);
        }
        
        

    
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
        /**
     * @return the vlistEntriOpname
     */
    public Vector getVDocMasterActionParam() {
        return vDocMasterActionParam;
    }
}
