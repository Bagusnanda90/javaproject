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

public class FrmKPI_Group extends FRMHandler implements I_FRMInterface, I_FRMType{
     private KPI_Group kPI_Group;

    public static final String FRM_NAME_KPI_GROUP = "FRM_NAME_KPI_GROUP";

    public static final int FRM_FIELD_KPI_GROUP_ID = 0;
    public static final int FRM_FIELD_KPI_TYPE_ID = 1;
    public static final int FRM_FIELD_GROUP_TITLE = 2;
    public static final int FRM_FIELD_DESCRIPTION = 3;
    
    public static String[] fieldNames = {
       
        "FRM_FIELD_KPI_GROUP_ID",
        "FRM_FIELD_KPI_TYPE_ID",
        "FRM_FIELD_GROUP_TITLE",
        "FRM_FIELD_DESCRIPTION"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_LONG, 
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmKPI_Group() {
    }

    public FrmKPI_Group(KPI_Group kPI_Group) {
        this.kPI_Group = kPI_Group;
    }

    public FrmKPI_Group(HttpServletRequest request, KPI_Group kPI_Group) {
        super(new FrmKPI_Group(kPI_Group), request);
        this.kPI_Group = kPI_Group;
    }

    public String getFormName() {
        return FRM_NAME_KPI_GROUP;
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

    public KPI_Group getEntityObject() {
        return kPI_Group;
    }

    public void requestEntityObject(KPI_Group kPI_Group) {
        try {
            this.requestParam();
            kPI_Group.setKpi_type_id(getLong(FRM_FIELD_KPI_TYPE_ID));
            kPI_Group.setGroup_title(getString(FRM_FIELD_GROUP_TITLE));
            kPI_Group.setDescription(getString(FRM_FIELD_DESCRIPTION));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
