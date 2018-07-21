/* 
 * Form Name  	:  FrmSrcAppraisal.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.form.search;

/* java package */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.search.*;

public class FrmSrcAppraisal extends FRMHandler implements I_FRMInterface, I_FRMType {

    private SrcAppraisal srcAppraisal;
    public static final String FRM_NAME_APPRAISAL = "FRM_NAME_APPRAISAL";
    public static final int FRM_FIELD_EMPLOYEE = 0;
    public static final int FRM_FIELD_APPRAISOR = 1;
    public static final int FRM_FIELD_AVERAGESTART = 2;
    public static final int FRM_FIELD_RANK = 3;
    public static final int FRM_FIELD_DATESTART = 4;
    public static final int FRM_FIELD_AVERAGEEND = 5;
    public static final int FRM_FIELD_DATEEND = 6;
    public static final int FRM_FIELD_ORDER_BY = 7;
    public static final int FRM_FIELD_APPROVED = 8;//for hardrock
    public static final int FRM_FIELD_DEPARTMENT_ID = 9;
    public static String[] fieldNames = {
        "FRM_FIELD_EMPLOYEE",
        "FRM_FIELD_APPRAISOR",
        "FRM_FIELD_AVERAGESTART",
        "FRM_FIELD_RANK",
        "FRM_FIELD_DATESTART",
        "FRM_FIELD_AVERAGEEND",
        "FRM_FIELD_DATEEND",
        "FRM_FIELD_ORDER_BY",
        "FRM_FIELD_APPROVED",
        "FRM_FIELD_DEPARTMENT_ID"
    };
    public static int[] fieldTypes = {
        TYPE_STRING,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG
    };
    public static final int ORDER_EMPLOYEE = 0;
    public static final int ORDER_APPRAISOR = 1;
    public static final int ORDER_RANK = 2;
    public static final int ORDER_AVERAGE = 3;
    public static final int ORDER_APPRAISAL_DATE = 4;
    public static final String[] orderKey = {"Employee", "Appraisor", "Rank", "Average Score", "Appraisal Date"};
    public static final int[] orderValue = {0, 1, 2, 3, 4};

    public static Vector getOrderKey() {
        Vector order = new Vector();
        for (int i = 0; i < orderKey.length; i++) {
            order.add(orderKey[i]);
        }
        return order;
    }

    public static Vector getOrderValue() {
        Vector order = new Vector();
        for (int i = 0; i < orderValue.length; i++) {
            order.add("" + orderValue[i]);
        }
        return order;
    }

    public FrmSrcAppraisal() {
    }

    public FrmSrcAppraisal(SrcAppraisal srcAppraisal) {
        this.srcAppraisal = srcAppraisal;
    }

    public FrmSrcAppraisal(HttpServletRequest request, SrcAppraisal srcAppraisal) {
        super(new FrmSrcAppraisal(srcAppraisal), request);
        this.srcAppraisal = srcAppraisal;
    }

    public String getFormName() {
        return FRM_NAME_APPRAISAL;
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

    public SrcAppraisal getEntityObject() {
        return srcAppraisal;
    }

    public void requestEntityObject(SrcAppraisal srcAppraisal) {
        try {
            this.requestParam();
            srcAppraisal.setEmployee(getString(FRM_FIELD_EMPLOYEE));
            srcAppraisal.setAppraisor(getString(FRM_FIELD_APPRAISOR));
            srcAppraisal.setAveragestart(getDouble(FRM_FIELD_AVERAGESTART));
            srcAppraisal.setRank(getLong(FRM_FIELD_RANK));
            srcAppraisal.setDatestart(getDate(FRM_FIELD_DATESTART));
            srcAppraisal.setAverageend(getDouble(FRM_FIELD_AVERAGEEND));
            srcAppraisal.setDateend(getDate(FRM_FIELD_DATEEND));
            srcAppraisal.setApproved(getInt(FRM_FIELD_APPROVED));//for hardrock
            srcAppraisal.setOrderBy(getInt(FRM_FIELD_ORDER_BY));
            srcAppraisal.setDepartmentId(getInt(FRM_FIELD_DEPARTMENT_ID)); // add by roy andika
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
