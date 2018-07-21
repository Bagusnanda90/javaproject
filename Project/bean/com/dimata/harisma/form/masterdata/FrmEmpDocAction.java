/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Ari_20110930
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: FrmEmpDocAction
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

public class FrmEmpDocAction extends FRMHandler implements I_FRMInterface, I_FRMType{
     private EmpDocAction empDocAction;
     //private EmpDocActionParam empDocActionParam;
     private Vector vEmpDocActionParam = new Vector();
     
  public static final String FRM_NAME_DOC_MASTER_ACTION = "FRM_NAME_DOC_MASTER_ACTION";

  public static final int FRM_FIELD_EMP_DOC_ACTION_ID = 0;
  public static final int FRM_FIELD_EMP_DOC_ID = 1;
  public static final int FRM_FIELD_ACTION_NAME = 2;
  public static final int FRM_FIELD_ACTION_TITLE = 3;
  
  public static final int FRM_FIELD_EMP_DOC_ACTION_PARAM_ID = 4;
  public static final int FRM_FIELD_ACTION_PARAMETER = 5;
  public static final int FRM_FIELD_OBJECT_NAME = 6;
  public static final int FRM_FIELD_OBJECT_ATTRIBUT = 7;

    public static String[] fieldNames = {
    "FRM_FIELD_EMP_DOC_ACTION_ID",
    "FRM_FIELD_EMP_DOC_ID",
    "FRM_FIELD_ACTION_NAME",
    "FRM_FIELD_ACTION_TITLE",
    
    
    "FRM_FIELD_EMP_DOC_ACTION_PARAM_ID",
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

    public FrmEmpDocAction() {
    }

    public FrmEmpDocAction(EmpDocAction empDocAction) {
        this.empDocAction = empDocAction;
    }

    public FrmEmpDocAction(HttpServletRequest request, EmpDocAction empDocAction) {
        super(new FrmEmpDocAction(empDocAction), request);
        this.empDocAction = empDocAction;
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

    public EmpDocAction getEntityObject() {
        return empDocAction;
    }

    public void requestEntityObject(EmpDocAction empDocAction) {
        try {
            this.requestParam();
            empDocAction.setEmpDocId(getLong(FRM_FIELD_EMP_DOC_ID));
            empDocAction.setActionName(getString(FRM_FIELD_ACTION_NAME));
            empDocAction.setActionTitle(getString(FRM_FIELD_ACTION_TITLE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

        public void requestEntityObjectMultiple(String actionNameKey) {
        try {
//            this.requestParam();
//   
//        int cekIndex = EmpDocActionClass.getIndexActionValue(actionNameKey);
//        String[] actionListParameter = EmpDocActionClass.actionListParameterKey[cekIndex];
//    
//        for (int i = 0; i < actionListParameter.length;i++ ){
//            EmpDocActionParam empDocActionParam = new EmpDocActionParam();
//            
//              empDocActionParam.setDocActionId(this.getParamLong(fieldNames[FRM_FIELD_DOC_ACTION_ID]+i));     
//              empDocActionParam.setActionParameter(this.getParamString(fieldNames[FRM_FIELD_ACTION_PARAMETER]+i));  
//              empDocActionParam.setObjectAtribut(this.getParamString(fieldNames[FRM_FIELD_OBJECT_ATTRIBUT]+i));
//              empDocActionParam.setObjectName(this.getParamString(fieldNames[FRM_FIELD_OBJECT_NAME]+i));
//              
//              
//
//            vEmpDocActionParam.add(empDocActionParam);
//        }
        
        

    
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
        /**
     * @return the vlistEntriOpname
     */
    public Vector getVEmpDocActionParam() {
        return vEmpDocActionParam;
    }
}
