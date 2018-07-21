/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Satrya Ramayu
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: FrmLeaveConfigurationMain
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.masterdata.leaveconfiguration;

/**
 *
 * @author Wiweka
 */

/* java package */

import com.dimata.harisma.form.masterdata.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailDepartement;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailDepartement;

public class FrmLeaveConfigurationDepartment extends FRMHandler implements I_FRMInterface, I_FRMType{
     private LeaveConfigurationDetailDepartement leaveConfigurationDetailDepartement;
     private Vector listGroupDepartement = new Vector();
     private String message="";
    public static final String FRM_NAME_LEAVE_CONFIGURATION_DETAIL_DEPARTMENT = "FRM_NAME_LEAVE_CONFIGURATION_DETAIL_DEPARTMENT";

    public static final int FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID = 0;
    public static final int FRM_FIELD_DEPARTEMENT_ID = 1;
    public static final int FRM_FIELD_SECTION_ID = 2;

    public static String[] fieldNames = {
        "FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID", "FRM_FIELD_DEPARTEMENT_ID",
        "FRM_FIELD_SECTION_ID"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG
    };

    public FrmLeaveConfigurationDepartment() {
    }

    public FrmLeaveConfigurationDepartment(LeaveConfigurationDetailDepartement leaveConfigurationDetailDepartement) {
        this.leaveConfigurationDetailDepartement = leaveConfigurationDetailDepartement;
    }

    public FrmLeaveConfigurationDepartment(HttpServletRequest request, LeaveConfigurationDetailDepartement leaveConfigurationDetailDepartement) {
        super(new FrmLeaveConfigurationDepartment(leaveConfigurationDetailDepartement), request);
        this.leaveConfigurationDetailDepartement = leaveConfigurationDetailDepartement;
    }

    public String getFormName() {
        return FRM_NAME_LEAVE_CONFIGURATION_DETAIL_DEPARTMENT;
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

    public LeaveConfigurationDetailDepartement getEntityObject() {
        return leaveConfigurationDetailDepartement;
    }

    public void requestEntityObjectMultiple(LeaveConfigurationDetailDepartement leaveConfigurationDetailDepartement) {
        try {
            this.requestParam();
            leaveConfigurationDetailDepartement.setDepartementIds(this.getParamsStringValues(fieldNames[FRM_FIELD_DEPARTEMENT_ID]));
            leaveConfigurationDetailDepartement.setSectionId(getLong(FRM_FIELD_SECTION_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
    public void requestEntityObject(LeaveConfigurationDetailDepartement leaveConfigurationDetailDepartement) {
        try {
            this.requestParam();
            leaveConfigurationDetailDepartement.setDepartementId(getLong(FRM_FIELD_DEPARTEMENT_ID));
            leaveConfigurationDetailDepartement.setSectionId(getLong(FRM_FIELD_SECTION_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    public  Vector getGroupDepartementId(long oidLeaveConfigMain,LeaveConfigurationDetailDepartement leaveConfigurationDetailDepartement){
        try{
            //Vector listResult = new Vector();
           // String sValsDept[] =  this.getParamsStringValues(fieldNames[FRM_FIELD_DEPARTEMENT_ID]);
           // String sValsSec[] =  this.getParamsStringValues(fieldNames[FRM_FIELD_SECTION_ID]);
            //artinya jika sizenya tidak sama maka sistem akan memberikan 
            if( leaveConfigurationDetailDepartement==null || (leaveConfigurationDetailDepartement.getDepartementIds()==null) || (leaveConfigurationDetailDepartement.getDepartementIds().length<1)){
                this.setMessage("selec department is null");
                return getListGroupDepartement();
            }
            if( (leaveConfigurationDetailDepartement.getDepartementIds()!=null && leaveConfigurationDetailDepartement.getDepartementIds().length>1  && leaveConfigurationDetailDepartement.getSectionId()!=0)){
                this.setMessage("please input department some one, if you input section ");
                return getListGroupDepartement();
            }
            for(int i=0; i < leaveConfigurationDetailDepartement.getDepartementIds().length ; i++){
                try{
                   LeaveConfigurationDetailDepartement objleaveConfigurationDetailDepartement = new LeaveConfigurationDetailDepartement();
                   leaveConfigurationDetailDepartement.setLeaveConfigurationMainId(oidLeaveConfigMain);
                   long oidDepartement = new Long(Long.parseLong(leaveConfigurationDetailDepartement.getDepartementIds()[i]));
                    long oidSection = leaveConfigurationDetailDepartement.getSectionId();
                   objleaveConfigurationDetailDepartement.setDepartementId(oidDepartement);
                   objleaveConfigurationDetailDepartement.setSectionId(oidSection);
                   getListGroupDepartement().add(objleaveConfigurationDetailDepartement); 
                }catch (Exception exc){
                    System.out.println("EXC : getVectorLong i="+i+" - "+exc);
                }
            }
            
            return getListGroupDepartement();
            
        }catch(Exception e){
        }
        
        return new Vector(1,1);
        
    }

    /**
     * @return the listGroupDepartement
     */
    public Vector getListGroupDepartement() {
        return listGroupDepartement;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    
   

}
