/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.search;

import com.dimata.harisma.entity.search.SrcOvertimeSummary;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.util.Calendar;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Satrya Ramayu
 */
public class FrmSrcOvertimeSummary extends FRMHandler implements I_FRMInterface, I_FRMType{
    private SrcOvertimeSummary srcOvertimeSummary;
    public static final String FRM_SRC_OVERTIME_SUMMARY = "FRM_SRC_OVERTIME_SUMMARY";
    public static final int FRM_FIELD_EMPLOYEE_NUMBER = 0;
    public static final int FRM_FIELD_EMPLOYEE_NAME = 1;
    public static final int FRM_FIELD_COMPANY_ID = 2;
    public static final int FRM_FIELD_DIVISION_ID = 3;
    public static final int FRM_FIELD_DEPARTEMENT_ID = 4;
    public static final int FRM_FIELD_SECTION_ID = 5;
    public static final int FRM_FIELD_RELIGION_ID = 6;
    public static final int FRM_FIELD_DATE_OVERTIME_START = 7;
    public static final int FRM_FIELD_DATE_OVERTIME_END= 8;
    public static final int FRM_FIELD_SORT_BY= 9;
     public static final int FRM_FIELD_COST_CENTER= 10;
     public static final int FRM_FIELD_GROUP_BY= 11;
     public static final int FRM_FIELD_RESIGNED= 12;
     //update by devin 2014-02-12
     public static final int FRM_FIELD_CEK_USER= 13;
     
     
    public static final String[] fieldNames = {
        "FRM_FIELD_EMPLOYEE_NUMBER",
        "FRM_FIELD_EMPLOYEE_NAME",
        "FRM_FIELD_COMPANY_ID",
        "FRM_FIELD_DIVISION_ID",
        "FRM_FIELD_DEPARTEMENT_ID",
        "FRM_FIELD_SECTION_ID",
        "FRM_FIELD_RELIGION_ID",
        "FRM_FIELD_DATE_OVERTIME_START",
        "FRM_FIELD_DATE_OVERTIME_END",
        "FRM_FIELD_SORT_BY",
        "FRM_FIELD_COST_CENTER",
        "FRM_FIELD_GROUP_BY",
        "FRM_FIELD_RESIGNED",
        //update by devin 2014-02-12
        "FRM_FIELD_CEK_USER"
    };
    public static final int[] fieldTypes = {
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        //update by devin 2014-02-12
        TYPE_BOOL
        
    };
    
    public static final int ORDER_EMPLOYEE_NUMBER = 0;
    public static final int ORDER_DATE_FROM = 1;
    public static final int ORDER_COMPANY = 2;
    public static final int ORDER_DIVISION= 3;
    public static final int ORDER_DEPARTMENT= 4;

    public static final int[] orderValue = {0, 1, 2,3,4};

    public static final String[] orderKey = {"Employee Number", "Date", "Company","Division","Departement"};

    
    public static final int GROUP_BY_DEPARTEMENT=0;
    public static final int GROUP_BY_COST_CENTER=1;
    public static final int[] groupByValue = {0, 1};
    public static final String[] groupByKey = {"Departement", "Cost Center"};

    
    public static final int RESIGN_NO = 0;
    public static final int RESIGN_YES = 1;
    public static final int RESIGN_ALL = 2;
    public static Vector getGroupByValue() {
        Vector order = new Vector();
        for (int i = 0; i < groupByValue.length; i++) {
            order.add(String.valueOf(groupByValue[i]));
        }
        return order;
    }

    public static Vector getGroupByKey() {
        Vector order = new Vector();
        for (int i = 0; i < groupByKey.length; i++) {
            order.add(groupByKey[i]);
        }
        return order;
    } 
    
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

   public FrmSrcOvertimeSummary() {
    }

    public FrmSrcOvertimeSummary(SrcOvertimeSummary srcOvertimeSummary) {
        this.srcOvertimeSummary = srcOvertimeSummary;
    }

    public FrmSrcOvertimeSummary(HttpServletRequest request, SrcOvertimeSummary srcOvertimeSummary) {
        super(new FrmSrcOvertimeSummary(srcOvertimeSummary), request);
        this.srcOvertimeSummary = srcOvertimeSummary;
    }
    //@Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    //@Override
    public String getFormName() {
        return FRM_SRC_OVERTIME_SUMMARY;
    }

    //@Override
    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public SrcOvertimeSummary getEntityObject() {
        return srcOvertimeSummary;
    }

    public void requestEntityObject(SrcOvertimeSummary srcOvertimeSummary) {
        try {
            this.requestParam();
            srcOvertimeSummary.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            srcOvertimeSummary.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            srcOvertimeSummary.setDepartementId(getLong(FRM_FIELD_DEPARTEMENT_ID));
            srcOvertimeSummary.setSectionId(getLong(FRM_FIELD_SECTION_ID));
            srcOvertimeSummary.setReligionId(getLong(FRM_FIELD_RELIGION_ID));
            srcOvertimeSummary.setSortBy(getInt(FRM_FIELD_SORT_BY));
            srcOvertimeSummary.setCostCenterDptId(getLong(FRM_FIELD_COST_CENTER)); 
            srcOvertimeSummary.setGroupBy(getInt(FRM_FIELD_GROUP_BY));
            srcOvertimeSummary.setResignStatus(getInt(FRM_FIELD_RESIGNED)); 
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
    public void requestEntityObject(SrcOvertimeSummary srcOvertimeSummary,HttpServletRequest request) {
        try {
            //this.requestParam();
            String sEmployeeNumber = request.getParameter(fieldNames[FRM_FIELD_EMPLOYEE_NUMBER]);
            
            
            String sFullName = request.getParameter(fieldNames[FRM_FIELD_EMPLOYEE_NAME]);
           
            
            String sCompanyId = request.getParameter(fieldNames[FRM_FIELD_COMPANY_ID]);
            long companyId=0;
            if(sCompanyId!=null && sCompanyId.length()>0){
                companyId = Long.parseLong(sCompanyId);
            }
            String sDivisionId = request.getParameter(fieldNames[FRM_FIELD_DIVISION_ID]);
            long divisionId=0;
            if(sDivisionId!=null && sDivisionId.length()>0){
                divisionId = Long.parseLong(sDivisionId);
            }
            String sDepartmentId = request.getParameter(fieldNames[FRM_FIELD_DEPARTEMENT_ID]);
            long departmentId=0;
            if(sDepartmentId!=null && sDepartmentId.length()>0){
                departmentId = Long.parseLong(sDepartmentId);
            }
            //update by devin 2014-02-12
            boolean cekLogin = FRMQueryString.requestBoolean(request,fieldNames[FRM_FIELD_CEK_USER]);
           
            String sSectionId = request.getParameter(fieldNames[FRM_FIELD_SECTION_ID]);
            long sectionId=0;
            if(sSectionId!=null && sSectionId.length()>0){
                sectionId = Long.parseLong(sSectionId);
            }
            String sReligionId = request.getParameter(fieldNames[FRM_FIELD_RELIGION_ID]);
            long religionId=0;
            if(sReligionId!=null && sReligionId.length()>0){
                religionId = Long.parseLong(sReligionId);
            }
            String sSortBy = request.getParameter(fieldNames[FRM_FIELD_SORT_BY]);
            int sortBy=0;
            if(sSortBy!=null && sSortBy.length()>0){
                sortBy = Integer.parseInt(sSortBy);
            }
            String sCostCenter = request.getParameter(fieldNames[FRM_FIELD_COST_CENTER]);
            long dptCostCenterId=0;
            if(sCostCenter!=null && sCostCenter.length()>0){
                dptCostCenterId = Long.parseLong(sCostCenter);
            }
            String sGroupBy = request.getParameter(fieldNames[FRM_FIELD_GROUP_BY]);
            int groupBy=0;
            if(sGroupBy!=null && sGroupBy.length()>0){
                groupBy = Integer.parseInt(sGroupBy);
            }
            
            String sResigned = request.getParameter(fieldNames[FRM_FIELD_RESIGNED]);
            int resigned=0;
            if(sResigned!=null && sResigned.length()>0){
                resigned = Integer.parseInt(sResigned);
            }
            
            java.util.Date dtFrom    = FRMQueryString.requestDateVer5(request, fieldNames[FRM_FIELD_DATE_OVERTIME_START]);
            java.util.Date dtTo      = FRMQueryString.requestDateVer5(request, fieldNames[FRM_FIELD_DATE_OVERTIME_END]);

            srcOvertimeSummary.setEmpNumber(sEmployeeNumber);
            srcOvertimeSummary.setFullName(sFullName);
            srcOvertimeSummary.setCompanyId(companyId);
            srcOvertimeSummary.setDivisionId(divisionId);
            srcOvertimeSummary.setDepartementId(departmentId);
            srcOvertimeSummary.setSectionId(sectionId);
            srcOvertimeSummary.setReligionId(religionId);
            srcOvertimeSummary.setSortBy(sortBy);
            srcOvertimeSummary.setCostCenterDptId(dptCostCenterId); 
            srcOvertimeSummary.setGroupBy(groupBy);
            srcOvertimeSummary.setStartOvertime(dtFrom);
            srcOvertimeSummary.setEndOvertime(dtTo);
             srcOvertimeSummary.setResignStatus(resigned); 
             srcOvertimeSummary.setCekUser(cekLogin); 
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

    /*public String getMessageEntryReqired(ControlLine ctrLine, int fieldIndex){
    if ((fieldTypes[fieldIndex] & FILTER_ENTRY) == ENTRY_REQUIRED)
    return ((getErrors().get(fieldIndex + "") == null)) ? "&nbsp;&nbsp;<img src=\"" + ctrLine.getLocationImg() + "/warning.png\" width=\"15\" height=\"15\">" : "&nbsp;&nbsp;<img src=\"" + ctrLine.getLocationImg() + "/critical.png\" width=\"15\" height=\"15\">"+"&nbsp;"+getErrorMsg(fieldIndex);
    return "";
    }*/
    public static void main(String[] arg) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 7);
        cal.set(Calendar.AM_PM, Calendar.AM);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR, 15);
        cal2.set(Calendar.AM_PM, Calendar.AM);
        System.out.println(cal.getTime() + "===" + cal.get(Calendar.AM_PM) + "=" + Calendar.AM);
        System.out.println(cal2.getTime() + "===" + cal2.get(Calendar.AM_PM) + "=" + Calendar.AM);
        System.out.println(cal.before(cal2));
        long l1 = cal.getTime().getTime();
        long l2 = cal2.getTime().getTime(); 
        System.out.println((double) (l2 - l1) / 3600000);
    }
}
