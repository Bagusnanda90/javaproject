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
import com.dimata.util.Formater;

public class FrmKPI_Employee_Target extends FRMHandler implements I_FRMInterface, I_FRMType{
     private KPI_Employee_Target kPI_Employee_Target;
     private Vector vlistKpiEmployeeTarget = new Vector();

   
     
    public static final String FRM_NAME_KPI_EMPLOYEE_TARGET = "FRM_NAME_KPI_EMPLOYEE_TARGET";

    public static final int FRM_FIELD_KPI_EMPLOYEE_TARGET_ID = 0;
    public static final int FRM_FIELD_KPI_LIST_ID = 1;
    public static final int FRM_FIELD_STARTDATE = 2;
    public static final int FRM_FIELD_ENDDATE = 3;
    public static final int FRM_FIELD_EMPLOYEE_ID = 4;
    public static final int FRM_FIELD_TARGET = 5;
    public static final int FRM_FIELD_ACHIEVEMENT = 6;
    public static final int FRM_FIELD_PERCENT = 7;
    

    public static String[] fieldNames = {
       
        "FRM_FIELD_KPI_EMPLOYEE_TARGET_ID",
        "FRM_FIELD_KPI_LIST_ID",
        "FRM_FIELD_STARTDATE",
        "FRM_FIELD_ENDDATE",
        "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_TARGET",
        "FRM_FIELD_ACHIEVEMENT",
        "FRM_FIELD_PERCENT"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT
    };

    public FrmKPI_Employee_Target() {
    }

    public FrmKPI_Employee_Target(KPI_Employee_Target kPI_Employee_Target) {
        this.kPI_Employee_Target = kPI_Employee_Target;
    }

    public FrmKPI_Employee_Target(HttpServletRequest request, KPI_Employee_Target kPI_Employee_Target) {
        super(new FrmKPI_Employee_Target(kPI_Employee_Target), request);
        this.kPI_Employee_Target = kPI_Employee_Target;
    }

    public String getFormName() {
        return FRM_NAME_KPI_EMPLOYEE_TARGET;
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

    public KPI_Employee_Target getEntityObject() {
        return kPI_Employee_Target;
    }

     
        public void requestEntityMultipleObjectEmployee(long kpiListId, int tahun,long companyId, Vector listKpiEmployeeTarget ) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            
                            Date startdate = new  Date();
                            Date enddate = new  Date();
                    for (int x = 0; x < listKpiEmployeeTarget.size(); x++) {       
                        KPI_ListAllDataEmp kPI_ListAllDataEmp = (KPI_ListAllDataEmp)listKpiEmployeeTarget.get(x);
                           // double target= this.getParamDouble(fieldNames[FRM_FIELD_TARGET]+x);
                            double percent= this.getParamDouble(fieldNames[FRM_FIELD_PERCENT]+x);
                            double Achievement= this.getParamDouble(fieldNames[FRM_FIELD_ACHIEVEMENT]+x);
                            KPI_Employee_Target  kPI_Employee_Target = new KPI_Employee_Target();
                            
                            String dateStartString = "01-01-"+tahun;
                            String dateEndString = "30-12-"+tahun;
                            
                            double total = 0;
                            Vector vtotal = PstKPI_Company_Target.listAlldata(tahun, kpiListId, companyId);
                            for (int ik=1; ik<=12;ik++){
                            total = total + ( ik<vtotal.size() ? ((Double)vtotal.get(ik)).doubleValue() : 0.0 );
                            }
                             
                            double target = total/100*percent;
                            
                            kPI_Employee_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
                            kPI_Employee_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
                            kPI_Employee_Target.setEmployeeId(kPI_ListAllDataEmp.getEmployeeId());
                            kPI_Employee_Target.setTarget(target);
                            kPI_Employee_Target.setAchievement(Achievement);
                           
                            vlistKpiEmployeeTarget.add(kPI_Employee_Target);
                    }
            
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
    public void requestEntityObject(KPI_Employee_Target kPI_Employee_Target) {
        try {
            this.requestParam();
            kPI_Employee_Target.setKpiListId(getLong(FRM_FIELD_KPI_LIST_ID));
            kPI_Employee_Target.setStartDate(getDate(FRM_FIELD_STARTDATE));
            kPI_Employee_Target.setEndDate(getDate(FRM_FIELD_ENDDATE));
            kPI_Employee_Target.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            kPI_Employee_Target.setTarget(getDouble(FRM_FIELD_TARGET));
            kPI_Employee_Target.setAchievement(getDouble(FRM_FIELD_ACHIEVEMENT));
         
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
       public Vector getVlistKpiEmployeeTarget() {
        return vlistKpiEmployeeTarget;
    }
}
