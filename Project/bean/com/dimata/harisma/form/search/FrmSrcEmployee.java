/*
 * Form Name  	:  FrmSrcEmployee.java
 * Created on 	:  [date] [time] AM/PM
 *
 * @author  	: karya
 * @version  	: 01
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
import com.dimata.harisma.entity.employee.*;

public class FrmSrcEmployee extends FRMHandler implements I_FRMInterface, I_FRMType {
    private SrcEmployee srcEmployee;

    public static final String FRM_NAME_SRCEMPLOYEE = "FRM_NAME_SRCEMPLOYEE";

    public static final int FRM_FIELD_NAME = 0;
    public static final int FRM_FIELD_ADDRESS = 1;
    public static final int FRM_FIELD_EMPNUMBER = 2;
    public static final int FRM_FIELD_START_COMMENC = 3;
    public static final int FRM_FIELD_END_COMMENC = 4;
    public static final int FRM_FIELD_DEPARTMENT = 5;
    public static final int FRM_FIELD_POSITION = 6;
    public static final int FRM_FIELD_SECTION = 7;
    public static final int FRM_FIELD_ORDER = 8;
    public static final int FRM_FIELD_RESIGNED = 9;
    public static final int FRM_FIELD_EMP_CATEGORY = 10;
    public static final int FRM_FIELD_DIVISION_ID = 11;
        /*edited by yunny
     *untuk kebutuhan intimas yaitu search karyawan by religion dan gender
    */
    public static final int FRM_FIELD_RELIGION = 12;
    public static final int FRM_FIELD_SEX = 13;
    public static final int FRM_FIELD_MARITAL_STATUS = 14;
    public static final int FRM_FIELD_SALARY_LEVEL = 15;
     /* added by Bayu -> data ras karyawan
     */
    public static final int FRM_FIELD_RACE = 16;
    public static final int FRM_FIELD_BIRTHDAY_CHECK = 17;
    public static final int FRM_FIELD_BIRTHDAY = 18;
    public static final int FRM_FIELD_BIRTHMONTH = 19;
    
    /* added by Roy -> data spouse
     */
    public static final int FRM_FIELD_SPOUSE = 20;
    /**
     * Ari_20111002
     * menambah FRM_FIELD_COMPANY 
     */
    public static final int FRM_FIELD_COMPANY_ID = 21;

    public static final int FRM_FIELD_START_RESIGN = 22;
    public static final int FRM_FIELD_END_RESIGN = 23;
    public static final int FRM_FIELD_EDUCATION = 24;//update by satrya 2012-11-14
     public static final int FRM_FIELD_BLOOD = 25;
     public static final int FRM_FIELD_LANGUAGE = 26;
     public static final int FRM_FIELD_LEVEL=27;

    
    public static String[] fieldNames = {
        "FRM_FIELD_NAME", "FRM_FIELD_ADDRESS",
        "FRM_FIELD_EMPNUMBER", "FRM_FIELD_START_COMMENC",
        "FRM_FIELD_END_COMMENC", "FRM_FIELD_DEPARTMENT",
        "FRM_FIELD_POSITION", "FRM_FIELD_SECTION",
        "FRM_FIELD_ORDER", "FRM_FIELD_RESIGNED", "FRM_FIELD_EMP_CATEGORY",
        "FRM_FIELD_DIVISION_ID", "FRM_FIELD_RELIGION", "FRM_FIELD_SEX",
        "FRM_FIELD_MARITAL_STATUS","FRM_FIELD_SALARY_LEVEL", "FRM_FIELD_RACE",
        "FRM_FIELD_BIRTHDAY_CHECK", "FRM_FIELD_BIRTHDAY", "FRM_FIELD_BIRTH_MONTH",
        "FRM_FIELD_SPOUSE","FRM_FIELD_COMPANY_ID","FRM_FIELD_START_RESIGN","FRM_FIELD_END_RESIGN",
        //update by satrya 2012-11-14
        "FRM_FIELD_EDUCATION","FRM_FIELD_BLOOD","FRM_FIELD_LANGUAGE","FRM_FIELD_LEVEL"
    };

    public static int[] fieldTypes = {
        TYPE_STRING, TYPE_STRING,
        TYPE_STRING, TYPE_DATE,
        TYPE_DATE, TYPE_LONG,
        TYPE_LONG, TYPE_LONG,
        TYPE_INT, TYPE_INT, TYPE_LONG,
        TYPE_LONG, TYPE_LONG, TYPE_LONG,
        TYPE_LONG,TYPE_STRING, TYPE_LONG,
        TYPE_BOOL, TYPE_DATE, TYPE_INT,TYPE_STRING,TYPE_LONG,TYPE_DATE,TYPE_DATE,
        //update by satrya 2012-11-14
        TYPE_COLLECTION,TYPE_STRING,TYPE_LONG,TYPE_LONG

    };


    public static final int ORDER_EMPLOYEE_NAME = 0;
    public static final int ORDER_DEPARTMENT = 1;
    public static final int ORDER_EMPLOYEE_NUM = 2;
    public static final int ORDER_COMM_DATE = 3;
    public static final int ORDER_COMPANY = 4;
    public static final int ORDER_RESIGN_DATE = 5;

    public static final int[] orderValue = {0, 1, 2, 3, 4};
    
    public static final String[] orderKey = {"Name", "Department", "Employee Number", "Commencing/Resigned Date","Company","Resign Date"};

    public static Vector getOrderValue() {
        Vector order = new Vector();
        for (int i = 0; i < orderValue.length; i++) {
            order.add(String.valueOf(orderValue[i]));
        }
        return order;
    }

    public static Vector getOrderKey() {
        Vector order = new Vector();
        for (int i = 0; i < orderKey.length; i++) {
            order.add(orderKey[i]);
        }
        return order;
    }

    //update by priska
    public static final int[] styleValue = {1,0};    
    public static final String[] styleKey = {"Style_2","Style_1"};
    
    public static Vector getStyleValue(){
        Vector style = new Vector();
        for (int i= 0; i< styleValue.length; i++){
            style.add(String.valueOf(styleValue[i]));
        }
        return style;
    }
    
    public static Vector getStyleKey(){
        Vector style = new Vector();
        for (int i = 0; i < styleKey.length; i++ ){
            style.add(styleKey[i]);
        }
        return style;
    }    
    
    
    
    public FrmSrcEmployee() {
    }

    public FrmSrcEmployee(SrcEmployee srcEmployee) {
        this.srcEmployee = srcEmployee;
    }

    public FrmSrcEmployee(HttpServletRequest request, SrcEmployee srcEmployee) {
        super(new FrmSrcEmployee(srcEmployee), request);
        this.srcEmployee = srcEmployee;
    }

    public String getFormName() {
        return FRM_NAME_SRCEMPLOYEE;
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

    public SrcEmployee getEntityObject() {
        return srcEmployee;
    }

    public void requestEntityObject(SrcEmployee srcEmployee) {
        try {
            this.requestParam();
            srcEmployee.setName(getString(FRM_FIELD_NAME));
            if(getString(FRM_FIELD_EMPNUMBER)!=null){
              srcEmployee.setEmpnumber(getString(FRM_FIELD_EMPNUMBER).replaceAll(" ", ""));
            }
            srcEmployee.setAddress(getString(FRM_FIELD_ADDRESS));
            srcEmployee.setDepartment(getLong(FRM_FIELD_DEPARTMENT));
            srcEmployee.setStartCommenc(getDate(FRM_FIELD_START_COMMENC));
            srcEmployee.setEndCommenc(getDate(FRM_FIELD_END_COMMENC));
            srcEmployee.setPosition(getLong(FRM_FIELD_POSITION));
            srcEmployee.setSection(getLong(FRM_FIELD_SECTION));
            srcEmployee.setOrderBy(getInt(FRM_FIELD_ORDER));
            srcEmployee.setResigned(getInt(FRM_FIELD_RESIGNED));
            srcEmployee.setEmpCategory(getLong(FRM_FIELD_EMP_CATEGORY));
            srcEmployee.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            /*edited by yunny
             *untuk kebutuhan intimas yaitu search karyawan by religion dan gender
            */
            srcEmployee.setReligion(getLong(FRM_FIELD_RELIGION));
            srcEmployee.setGender(getLong(FRM_FIELD_SEX));
            srcEmployee.setMaritalStatus(getLong(FRM_FIELD_MARITAL_STATUS));
            srcEmployee.setSalaryLevel(getString(FRM_FIELD_SALARY_LEVEL));
            
            srcEmployee.setRaceId(getLong(FRM_FIELD_RACE));
            srcEmployee.setBirthdayChecked(getBoolean(FRM_FIELD_BIRTHDAY_CHECK));
            srcEmployee.setBirthday(getDate(FRM_FIELD_BIRTHDAY));
            srcEmployee.setBirthmonth(getInt(FRM_FIELD_BIRTHMONTH));
            
            srcEmployee.setSpouse(getString(FRM_FIELD_SPOUSE));
            /**
             * Ari_2011002
             * Menambah FRM_FIELD_COMPANY
             */
            srcEmployee.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            
            srcEmployee.setStartResign(getDate(FRM_FIELD_START_RESIGN));
            srcEmployee.setEndResign(getDate(FRM_FIELD_END_RESIGN));
            //update by satrya 2012-11-14
            srcEmployee.setEducationIds(getVectorLong(fieldNames[FRM_FIELD_EDUCATION]));   
            srcEmployee.setLanguage(getLong(FRM_FIELD_LANGUAGE));
            srcEmployee.setBlood(getString(FRM_FIELD_BLOOD));
            //update by satrya 2012-12-23
            srcEmployee.setLevel(getLong(FRM_FIELD_LEVEL));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
