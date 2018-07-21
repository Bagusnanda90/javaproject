/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.KPI_Employee_Achiev;
import com.dimata.harisma.entity.masterdata.KPI_Employee_Achiev;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author GUSWIK
 */
public class FrmKPI_Employee_Achiev extends FRMHandler implements I_FRMInterface, I_FRMType{
     private KPI_Employee_Achiev kPI_Employee_Achiev;

    public static final String FRM_NAME_KPI_EMPLOYEE_ACHIEV = "FRM_NAME_KPI_EMPLOYEE_ACHIEV";

    public static final int FRM_FIELD_KPI_EMPLOYEE_ACHIEV_ID = 0;
    public static final int FRM_FIELD_KPI_LIST_ID = 1;
    public static final int FRM_FIELD_STARTDATE = 2;
    public static final int FRM_FIELD_ENDDATE = 3;
    public static final int FRM_FIELD_EMPLOYEE_ID = 4;
    public static final int FRM_FIELD_ENTRYDATE = 5;
    public static final int FRM_FIELD_ACHIEVMENT = 6;
    
    public static String[] fieldNames = {
       
        "FRM_FIELD_KPI_EMPLOYEE_ACHIEV_ID",
        "FRM_FIELD_KPI_LIST_ID",
        "FRM_FIELD_STARTDATE",
        "FRM_FIELD_ENDDATE",
        "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_ENTRYDATE",
        "FRM_FIELD_ACHIEVMENT"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_LONG, 
        TYPE_DATE, 
        TYPE_DATE, 
        TYPE_LONG, 
        TYPE_DATE,
        TYPE_FLOAT
    };

    public FrmKPI_Employee_Achiev() {
    }

    public FrmKPI_Employee_Achiev(KPI_Employee_Achiev kPI_Employee_Achiev) {
        this.kPI_Employee_Achiev = kPI_Employee_Achiev;
    }

    public FrmKPI_Employee_Achiev(HttpServletRequest request, KPI_Employee_Achiev kPI_Employee_Achiev) {
        super(new FrmKPI_Employee_Achiev(kPI_Employee_Achiev), request);
        this.kPI_Employee_Achiev = kPI_Employee_Achiev;
    }

    public String getFormName() {
        return FRM_NAME_KPI_EMPLOYEE_ACHIEV;
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

    public KPI_Employee_Achiev getEntityObject() {
        return kPI_Employee_Achiev;
    }

    public void requestEntityObject(KPI_Employee_Achiev kPI_Employee_Achiev) {
        try {
            this.requestParam();
            kPI_Employee_Achiev.setKpiListId(getLong(FRM_FIELD_KPI_LIST_ID));
            kPI_Employee_Achiev.setStartDate(getDate(FRM_FIELD_STARTDATE));
            kPI_Employee_Achiev.setEndDate(getDate(FRM_FIELD_ENDDATE));
            kPI_Employee_Achiev.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            kPI_Employee_Achiev.setEntryDate(getDate(FRM_FIELD_ENTRYDATE));
            kPI_Employee_Achiev.setAchievement(getDouble(FRM_FIELD_ACHIEVMENT));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
