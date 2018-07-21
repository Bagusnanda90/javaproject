/* 
 * Form Name  	:  FrmLocation.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: lkarunia
 * @version  	: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.masterdata.location;

/* java package */

import com.dimata.harisma.entity.masterdata.location.Location;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */


public class FrmLocation extends FRMHandler implements I_FRMInterface, I_FRMType {
    private Location location;

    public static final String FRM_NAME_LOCATION = "FRM_NAME_LOCATION";

    public static final int FRM_FIELD_CODE = 0;
    public static final int FRM_FIELD_NAME = 1;
    public static final int FRM_FIELD_ADDRESS = 2;
    public static final int FRM_FIELD_TELEPHONE = 3;
    public static final int FRM_FIELD_FAX = 4;
    public static final int FRM_FIELD_PERSON = 5;
    public static final int FRM_FIELD_EMAIL = 6;
    public static final int FRM_FIELD_WEBSITE = 7;
    public static final int FRM_FIELD_PARENT_LOCATION_ID = 8;
    public static final int FRM_FIELD_CONTACT_ID = 9;
    public static final int FRM_FIELD_TYPE = 10;
    public static final int FRM_FIELD_DESCRIPTION = 11;

    public static final int FRM_FIELD_SERVICE_PERCENT = 12;
    public static final int FRM_FIELD_TAX_PERCENT = 13;
    public static final int FRM_FIELD_DEPARTMENT_ID = 14;
    public static final int FRM_FIELD_USED_VAL = 15;
    public static final int FRM_FIELD_SERVICE_VAL = 16;
    public static final int FRM_FIELD_TAX_VAL = 17;
    public static final int FRM_FIELD_SERVICE_VAL_USD = 18;
    public static final int FRM_FIELD_TAX_VAL_USD = 19;
    public static final int FRM_FIELD_REPORT_GROUP = 20;

    //add opie 13-06-2012
    public static final int FRM_FIELD_TAX_SERVICE_DEFAULT =21;
    public static final int FRM_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER=22;
      //add fitra 29-01-2014
     public static final int FRM_FIELD_COMPANY_ID=23;
 public static final int FRM_FIELD_COMPANY_NAME=24;
 //update by satrya 2014-02-27
 public static final int FRM_FIELD_COLOR_LOCATION=25;
 public static final int FRM_FIELD_SUB_REGENCY=26;
            
    public static String[] fieldNames = {
            "FRM_FIELD_CODE",
            "FRM_FIELD_NAME",
            "FRM_FIELD_ADDRESS",
            "FRM_FIELD_TELEPHONE",
            "FRM_FIELD_FAX",
            "FRM_FIELD_PERSON",
            "FRM_FIELD_EMAIL",
            "FRM_FIELD_WEBSITE",
            "FRM_FIELD_PARENT_LOCATION_ID",
            "FRM_FIELD_CONTACT_ID",
            "FRM_FIELD_TYPE",
            "FRM_FIELD_DESCRIPTION",

            // ini di pakai untuk hanoman
            "FRM_FIELD_SERVICE_PERCENT",
            "FRM_FIELD_TAX_PERCENT",
            "FRM_FIELD_DEPARTMENT_ID",//14
            "FRM_FIELD_USED_VAL",
            "FRM_FIELD_SERVICE_VAL",
            "FRM_FIELD_TAX_VAL",
            "FRM_FIELD_SERVICE_VAL_USD",
            "FRM_FIELD_TAX_VAL_USD",
            "FRM_FIELD_REPORT_GROUP",
            
            //add opie 13-06-2012
            "FRM_FIELD_TAX_SERVICE_DEFAULT",
            "FRM_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER",
              //add fitra 29-01-2014
            "COMPANY_ID",//23
            "COMPANY_NAME",
            //update by satrya 2014-02-27
            "FRM_FIELD_COLOR_LOCATION",
            "FRM_FIELD_SUB_REGENCY"

    };

    public static int[] fieldTypes = {
            TYPE_STRING + ENTRY_REQUIRED,
            TYPE_STRING + ENTRY_REQUIRED,
            TYPE_STRING,
            TYPE_STRING,
            TYPE_STRING,
            TYPE_STRING,
            TYPE_STRING,
            TYPE_STRING,
            TYPE_LONG,
            TYPE_LONG,
            TYPE_INT,
            TYPE_STRING,
            // ini di pakai untuk hanoman
            TYPE_FLOAT,
            TYPE_FLOAT,
            TYPE_LONG,
            TYPE_INT,
            TYPE_FLOAT,
            TYPE_FLOAT,
            TYPE_FLOAT,
            TYPE_FLOAT,
            TYPE_INT,
            //add opie 13-06-2012
            TYPE_INT,
            TYPE_FLOAT,
              //add fitra 29-01-2014
            TYPE_LONG + ENTRY_REQUIRED,
            TYPE_STRING,
            //create by satrya 2014-02-27
            TYPE_STRING,
            TYPE_LONG

    };

    public FrmLocation() {
    }

    public FrmLocation(Location location) {
        this.location = location;
    }

    public FrmLocation(HttpServletRequest request, Location location) {
        super(new FrmLocation(location), request);
        this.location = location;
    }

    public String getFormName() {
        return FRM_NAME_LOCATION;
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

    public Location getEntityObject() {
        return location;
    }

    public void requestEntityObject(Location location) {
        try {
            this.requestParam();
            location.setCode(getString(FRM_FIELD_CODE));
            location.setName(getString(FRM_FIELD_NAME));
            location.setAddress(getString(FRM_FIELD_ADDRESS));
            location.setTelephone(getString(FRM_FIELD_TELEPHONE));
            location.setFax(getString(FRM_FIELD_FAX));
            location.setPerson(getString(FRM_FIELD_PERSON));
            location.setEmail(getString(FRM_FIELD_EMAIL));
            location.setWebsite(getString(FRM_FIELD_WEBSITE));
            location.setParentLocationId(getLong(FRM_FIELD_PARENT_LOCATION_ID));
            location.setContactId(getLong(FRM_FIELD_CONTACT_ID));
            location.setType(getInt(FRM_FIELD_TYPE));
            location.setDescription(getString(FRM_FIELD_DESCRIPTION));

            // tambahan untuk proses di prochain opie 13-06-2012
            location.setServicePersen(getDouble(FRM_FIELD_SERVICE_PERCENT));
            location.setTaxPersen(getDouble(FRM_FIELD_TAX_PERCENT));
            
            // ini di pakai untuk hanoman
            location.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
            location.setTypeBase(getInt(FRM_FIELD_USED_VAL));
            location.setServiceValue(getDouble(FRM_FIELD_SERVICE_VAL));
            location.setTaxValue(getDouble(FRM_FIELD_TAX_VAL));
            location.setServiceValueUsd(getDouble(FRM_FIELD_SERVICE_VAL_USD));
            location.setTaxValueUsd(getDouble(FRM_FIELD_TAX_VAL_USD));
            location.setReportGroup(getInt(FRM_FIELD_REPORT_GROUP));

            //add opie 13-06-2012
            location.setTaxSvcDefault(getInt(FRM_FIELD_TAX_SERVICE_DEFAULT));
            location.setPersentaseDistributionPurchaseOrder(getDouble(FRM_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER));
              //add fitra 29-01-2014
            location.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            location.setCompanyName(getString(FRM_FIELD_COMPANY_NAME));
            
            location.setColorLocation(getString(FRM_FIELD_COLOR_LOCATION));
            
            location.setSubRegencyId(getLong(FRM_FIELD_SUB_REGENCY));
            
            //System.out.println("location.getParentLocationId() : " + location.getParentLocationId());
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
