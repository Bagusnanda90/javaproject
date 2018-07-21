/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Ari_20110930
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: FrmArApCreditor
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

public class FrmArApCreditor extends FRMHandler implements I_FRMInterface, I_FRMType{
     private ArApCreditor arApCreditor;

    public static final String FRM_ARAP_CREDITOR = "FRM_ARAP_CREDITOR";

    public static final int FRM_FIELD_ARAP_CREDITOR_ID = 0;
    public static final int FRM_FIELD_CREDITOR_NAME = 1;
    public static final int FRM_FIELD_DESCRIPTION = 2;
    public static String[] fieldNames = {
        "FRM_FIELD_ARAP_CREDITOR_ID", "FRM_FIELD_CREDITOR_NAME",
        "FRM_FIELD_DESCRIPTION"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING
    };

    public FrmArApCreditor() {
    }

    public FrmArApCreditor(ArApCreditor arApCreditor) {
        this.arApCreditor = arApCreditor;
    }

    public FrmArApCreditor(HttpServletRequest request, ArApCreditor arApCreditor) {
        super(new FrmArApCreditor(arApCreditor), request);
        this.arApCreditor = arApCreditor;
    }

    public String getFormName() {
        return FRM_ARAP_CREDITOR;
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

    public ArApCreditor getEntityObject() {
        return arApCreditor;
    }

    public void requestEntityObject(ArApCreditor arApCreditor) {
        try {
            this.requestParam();
            arApCreditor.setCreditorName(getString(FRM_FIELD_CREDITOR_NAME));
            arApCreditor.setDescription(getString(FRM_FIELD_DESCRIPTION));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
