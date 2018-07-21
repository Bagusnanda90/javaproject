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

public class FrmKPI_List extends FRMHandler implements I_FRMInterface, I_FRMType{
     private KPI_List kPI_List;

//    private long kpi_list_id ;
//    private long company_id ;
//    private String kpi_title = "";
//    private String description = "";
//    private Date valid_from ;
//    private Date valid_to ;  
//    private String value_type;
     
    public static final String FRM_NAME_KPI_LIST = "FRM_NAME_KPI_LIST";

    public static final int FRM_FIELD_KPI_LIST_ID = 0;
    public static final int FRM_FIELD_COMPANY_ID = 1;
    public static final int FRM_FIELD_KPI_TITLE = 2;
    public static final int FRM_FIELD_DESCRIPTION = 3;
    public static final int FRM_FIELD_VALID_FROM = 4;
    public static final int FRM_FIELD_VALID_TO = 5;
    public static final int FRM_FIELD_VALUE_TYPE = 6;
    public static final int FRM_FIELD_KPI_GROUP = 7;
    
    
    public static String[] fieldNames = {
       
        "FRM_FIELD_KPI_LIST_ID",
        "FRM_FIELD_COMPANY_ID",        
        "FRM_FIELD_KPI_TITLE",
        "FRM_FIELD_DESCRIPTION",
        "FRM_FIELD_VALID_FROM",
        "FRM_FIELD_VALID_TO",
        "FRM_FIELD_VALUE_TYPE",
        "FRM_FIELD_KPI_GROUP"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_LONG, 
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_LONG
    };

    public FrmKPI_List() {
    }

    public FrmKPI_List(KPI_List kPI_List) {
        this.kPI_List = kPI_List;
    }

    public FrmKPI_List(HttpServletRequest request, KPI_List kPI_List) {
        super(new FrmKPI_List(kPI_List), request);
        this.kPI_List = kPI_List;
    }

    public String getFormName() {
        return FRM_NAME_KPI_LIST;
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

    public KPI_List getEntityObject() {
        return kPI_List;
    }

    public void requestEntityObject(KPI_List kPI_List) {
        try {
            this.requestParam();
            
           
            kPI_List.setCompany_id(getLong(FRM_FIELD_COMPANY_ID));
            kPI_List.setKpi_title(getString(FRM_FIELD_KPI_TITLE));
            kPI_List.setDescription(getString(FRM_FIELD_DESCRIPTION));
            kPI_List.setValid_from(getDate(FRM_FIELD_VALID_FROM));
            kPI_List.setValid_to(getDate(FRM_FIELD_VALID_TO));
            kPI_List.setValue_type(getString(FRM_FIELD_VALUE_TYPE));
            kPI_List.addArrkpigroup(this.getParamsStringValues(fieldNames[FRM_FIELD_KPI_GROUP]));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
