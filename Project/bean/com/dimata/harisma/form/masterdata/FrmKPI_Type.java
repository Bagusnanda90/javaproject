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

public class FrmKPI_Type extends FRMHandler implements I_FRMInterface, I_FRMType{
     private KPI_Type kPI_Type;

    public static final String FRM_NAME_KPI_TYPE = "FRM_NAME_KPI_TYPE";

    public static final int FRM_FIELD_KPI_TYPE_ID = 0;
    public static final int FRM_FIELD_TYPE_NAME = 1;
    public static final int FRM_FIELD_DESCRIPTION = 2;
    
    public static String[] fieldNames = {
       
        "FRM_FIELD_KPI_TYPE_ID",
        "FRM_FIELD_TYPE_NAME",
        "FRM_FIELD_DESCRIPTION"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING
    };

    public FrmKPI_Type() {
    }

    public FrmKPI_Type(KPI_Type kPI_Type) {
        this.kPI_Type = kPI_Type;
    }

    public FrmKPI_Type(HttpServletRequest request, KPI_Type kPI_Type) {
        super(new FrmKPI_Type(kPI_Type), request);
        this.kPI_Type = kPI_Type;
    }

    public String getFormName() {
        return FRM_NAME_KPI_TYPE;
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

    public KPI_Type getEntityObject() {
        return kPI_Type;
    }

    public void requestEntityObject(KPI_Type kPI_Type) {
        try {
            this.requestParam();
            kPI_Type.setType_name(getString(FRM_FIELD_TYPE_NAME));
            kPI_Type.setDescription(getString(FRM_FIELD_DESCRIPTION));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
