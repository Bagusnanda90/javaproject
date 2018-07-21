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

public class FrmCompetitor extends FRMHandler implements I_FRMInterface, I_FRMType{
     private Competitor competitor;

  public static final String FRM_NAME_COMPETITOR = "FRM_NAME_COMPETITOR";
  public static final int FRM_FIELD_COMPETITOR_ID = 0;
  public static final int FRM_FIELD_COMPANY_NAME = 1;
  public static final int FRM_FIELD_ADDRESS = 2;
  public static final int FRM_FIELD_WEBSITE = 3;
  public static final int FRM_FIELD_EMAIL = 4;
  public static final int FRM_FIELD_TELEPHONE = 5;
  public static final int FRM_FIELD_FAX = 6;
  public static final int FRM_FIELD_CONTACT_PERSON = 7;
  public static final int FRM_FIELD_DESCRIPTION = 8;
  public static final int FRM_FIELD_COUNTRY_ID = 9;
  public static final int FRM_FIELD_PROVINCE_ID = 10;
  public static final int FRM_FIELD_REGION_ID = 11;
  public static final int FRM_FIELD_SUBREGION_ID = 12;
  public static final int FRM_FIELD_GEO_ADDRESS = 13;
    
    public static String[] fieldNames = {
       
    "FRM_FIELD_COMPETITOR_ID",
    "FRM_FIELD_COMPANY_NAME",
    "FRM_FIELD_ADDRESS",
    "FRM_FIELD_WEBSITE",
    "FRM_FIELD_EMAIL",
    "FRM_FIELD_TELEPHONE",
    "FRM_FIELD_FAX",
    "FRM_FIELD_CONTACT_PERSON",
    "FRM_FIELD_DESCRIPTION",
    "FRM_FIELD_COUNTRY_ID",
    "FRM_FIELD_PROVINCE_ID",
    "FRM_FIELD_REGION_ID",
    "FRM_FIELD_SUBREGION_ID",
    "FRM_FIELD_GEO_ADDRESS"
    };

    public static int[] fieldTypes = {
    TYPE_LONG,
    TYPE_STRING,
    TYPE_STRING,
    TYPE_STRING,
    TYPE_STRING,
    TYPE_STRING,
    TYPE_STRING,
    TYPE_STRING,
    TYPE_STRING,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_STRING
    };

    public FrmCompetitor() {
    }

    public FrmCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }

    public FrmCompetitor(HttpServletRequest request, Competitor competitor) {
        super(new FrmCompetitor(competitor), request);
        this.competitor = competitor;
    }

    public String getFormName() {
        return FRM_NAME_COMPETITOR;
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

    public Competitor getEntityObject() {
        return competitor;
    }

    public void requestEntityObject(Competitor competitor) {
        try {
            this.requestParam();
            competitor.setCompetitorId(getLong(FRM_FIELD_COMPETITOR_ID));
            competitor.setCompanyName(getString(FRM_FIELD_COMPANY_NAME));
            competitor.setAddress(getString(FRM_FIELD_ADDRESS));
            competitor.setWebsite(getString(FRM_FIELD_WEBSITE));
            competitor.setEmail(getString(FRM_FIELD_EMAIL));
            competitor.setTelephone(getString(FRM_FIELD_TELEPHONE));
            competitor.setFax(getString(FRM_FIELD_FAX));
            competitor.setContact_person(getString(FRM_FIELD_CONTACT_PERSON));
            competitor.setDescription(getString(FRM_FIELD_DESCRIPTION));
            competitor.setCountryId(getLong(FRM_FIELD_COUNTRY_ID));
            competitor.setProvinceId(getLong(FRM_FIELD_PROVINCE_ID));
            competitor.setRegionId(getLong(FRM_FIELD_REGION_ID));
            competitor.setSubregionId(getLong(FRM_FIELD_SUBREGION_ID));
            competitor.setGeoAddress(getString(FRM_FIELD_GEO_ADDRESS));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
