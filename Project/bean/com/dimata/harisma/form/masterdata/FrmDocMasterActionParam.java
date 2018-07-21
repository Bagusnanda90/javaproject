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

public class FrmDocMasterActionParam extends FRMHandler implements I_FRMInterface, I_FRMType{
     private DocMasterActionParam docMasterAction;

    public static final String FRM_NAME_DOC_MASTER_ACTION_PARAM = "FRM_NAME_DOC_MASTER_ACTION_PARAM";

  public static final int FRM_FIELD_DOC_ACTION_PARAM_ID = 0;
  public static final int FRM_FIELD_ACTION_PARAMETER = 1;
  public static final int FRM_FIELD_OBJECT_NAME = 2;
  public static final int FRM_FIELD_OBJECT_ATTRIBUTE = 3;
  public static final int FRM_FIELD_DOC_ACTION_ID = 4;
  
    public static String[] fieldNames = {
       
    "FRM_FIELD_DOC_ACTION_PARAM_ID",
    "FRM_FIELD_ACTION_PARAMETER",
    "FRM_FIELD_OBJECT_NAME",
    "FRM_FIELD_OBJECT_ATTRIBUTE",
    "FRM_FIELD_DOC_ACTION_ID"        
    };

    public static int[] fieldTypes = {
    TYPE_LONG,
    TYPE_STRING,
    TYPE_STRING,
    TYPE_STRING,
    TYPE_LONG
    };

    
    public FrmDocMasterActionParam() {
    }

    public FrmDocMasterActionParam(DocMasterActionParam docMasterAction) {
        this.docMasterAction = docMasterAction;
    }

    public FrmDocMasterActionParam(HttpServletRequest request, DocMasterActionParam docMasterAction) {
        super(new FrmDocMasterActionParam(docMasterAction), request);
        this.docMasterAction = docMasterAction;
    }

    public String getFormName() {
        return FRM_NAME_DOC_MASTER_ACTION_PARAM;
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

    public DocMasterActionParam getEntityObject() {
        return docMasterAction;
    }

    public void requestEntityObject(DocMasterActionParam docMasterAction) {
        try {
            this.requestParam();
            
    docMasterAction.setDocActionParamId(getLong(FRM_FIELD_DOC_ACTION_PARAM_ID));
    docMasterAction.setObjectAtribut(getString(FRM_FIELD_OBJECT_ATTRIBUTE));
    docMasterAction.setObjectName(getString(FRM_FIELD_OBJECT_NAME));
    docMasterAction.setActionParameter(getString(FRM_FIELD_ACTION_PARAMETER));
    docMasterAction.setDocActionId(getLong(FRM_FIELD_DOC_ACTION_ID));
    
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
