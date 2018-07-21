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

public class FrmCompany extends FRMHandler implements I_FRMInterface, I_FRMType{
     private Company company;

    public static final String FRM_NAME_COMPANY = "FRM_NAME_COMPANY";

    public static final int FRM_FIELD_COMPANY_ID = 0;
    public static final int FRM_FIELD_COMPANY = 1;
    public static final int FRM_FIELD_DESCRIPTION = 2;
    public static final int FRM_FIELD_CODE_COMPANY = 3;
    public static final int FRM_FIELD_COMPANY_PARENTS_ID = 4;

    public static String[] fieldNames = {
        "FRM_FIELD_COMPANY_ID", "FRM_FIELD_COMPANY",
        "FRM_FIELD_DESCRIPTION","FRM_FIELD_CODE_COMPANY","FRM_FIELD_COMPANY_PARENTS_ID"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING,TYPE_STRING,TYPE_LONG
    };

    public FrmCompany() {
    }

    public FrmCompany(Company company) {
        this.company = company;
    }

    public FrmCompany(HttpServletRequest request, Company company) {
        super(new FrmCompany(company), request);
        this.company = company;
    }

    public String getFormName() {
        return FRM_NAME_COMPANY;
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

    public Company getEntityObject() {
        return company;
    }

    public void requestEntityObject(Company company) {
        try {
            this.requestParam();
            company.setCompany(getString(FRM_FIELD_COMPANY));
            company.setDescription(getString(FRM_FIELD_DESCRIPTION));
            company.setCodeCompany(getString(FRM_FIELD_CODE_COMPANY));
            company.setCompanyParentsId(getLong(FRM_FIELD_COMPANY_PARENTS_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
