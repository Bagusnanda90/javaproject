/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Satrya Ramayu
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: FrmMappingKadivMain
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.masterdata.mappingkadiv;

/**
 *
 * @author Wiweka
 */

/* java package */

import com.dimata.harisma.form.masterdata.leaveconfiguration.*;
import com.dimata.harisma.form.masterdata.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.masterdata.mappingkadiv.MappingKadivMain;

public class FrmMappingKadivMain extends FRMHandler implements I_FRMInterface, I_FRMType{
     private MappingKadivMain mappingKadivMain;

    public static final String FRM_NAME_MAPPING_KADIV_MAIN = "FRM_NAME_MAPPING_KADIV_MAIN";

    public static final int FRM_FIELD_MAPPING_KADIV_MAIN_ID = 0;
    public static final int FRM_FIELD_EMPLOYEE_ID = 1;
  

    public static String[] fieldNames = {
        "FRM_FIELD_MAPPING_KADIV_MAIN_ID",
        "FRM_FIELD_EMPLOYEE_ID"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG + ENTRY_REQUIRED,
    };

    public FrmMappingKadivMain() {
    }

    public FrmMappingKadivMain(MappingKadivMain mappingKadivMain) {
        this.mappingKadivMain = mappingKadivMain;
    }

    public FrmMappingKadivMain(HttpServletRequest request, MappingKadivMain mappingKadivMain) {
        super(new FrmMappingKadivMain(mappingKadivMain), request);
        this.mappingKadivMain = mappingKadivMain;
    }

    public String getFormName() {
        return FRM_NAME_MAPPING_KADIV_MAIN;
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

    public MappingKadivMain getEntityObject() {
        return mappingKadivMain;
    }

    public void requestEntityObject(MappingKadivMain mappingKadivMain) {
        try {
            this.requestParam();
            mappingKadivMain.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
     

}
