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

import java.util.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailDepartement;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailDepartementRequestOnly;

public class FrmLeaveConfigurationDepartmentRequestOnly extends FRMHandler implements I_FRMInterface, I_FRMType{
     private LeaveConfigurationDetailDepartementRequestOnly objLeaveConfigurationDetailDepartementRequestOnly;
     private Vector listGroupDepartement = new Vector();
     private String message="";
    public static final String FRM_NAME_LEAVE_CONFIGURATION_DETAIL_DEPARTMENT_REQUEST_ONLY = "FRM_NAME_LEAVE_CONFIGURATION_DETAIL_DEPARTMENT_REQUEST_ONLY";

    public static final int FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY = 0;
    public static final int FRM_FIELD_DEPARTEMENT_ID = 1;
    public static final int FRM_FIELD_SECTION_ID = 2;

    public static String[] fieldNames = {
        "FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY", "FRM_FIELD_DEPARTEMENT_ID",
        "FRM_FIELD_SECTION_ID"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG
    };

    public FrmLeaveConfigurationDepartmentRequestOnly() {
    }

    public FrmLeaveConfigurationDepartmentRequestOnly(LeaveConfigurationDetailDepartementRequestOnly leaveConfigurationDetailDepartementRequestOnly) {
        this.objLeaveConfigurationDetailDepartementRequestOnly = leaveConfigurationDetailDepartementRequestOnly;
    }

    public FrmLeaveConfigurationDepartmentRequestOnly(HttpServletRequest request, LeaveConfigurationDetailDepartementRequestOnly leaveConfigurationDetailDepartementRequestOnly) {
        super(new FrmLeaveConfigurationDepartmentRequestOnly(leaveConfigurationDetailDepartementRequestOnly), request);
        this.objLeaveConfigurationDetailDepartementRequestOnly = leaveConfigurationDetailDepartementRequestOnly;
    }

    public String getFormName() {
        return FRM_NAME_LEAVE_CONFIGURATION_DETAIL_DEPARTMENT_REQUEST_ONLY;
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

    public LeaveConfigurationDetailDepartementRequestOnly getEntityObject() {
        return objLeaveConfigurationDetailDepartementRequestOnly;
    }

    public void requestEntityObjectMultiple(LeaveConfigurationDetailDepartementRequestOnly leaveConfigurationDetailDepartementRequestOnly) {
        try {
            this.requestParam();
            leaveConfigurationDetailDepartementRequestOnly.setDepartementIds(this.getParamsStringValues(fieldNames[FRM_FIELD_DEPARTEMENT_ID]));
            leaveConfigurationDetailDepartementRequestOnly.setSectionId(getLong(FRM_FIELD_SECTION_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
    public void requestEntityObject(LeaveConfigurationDetailDepartementRequestOnly leaveConfigurationDetailDepartementRequestOnly) {
        try {
            this.requestParam();
            leaveConfigurationDetailDepartementRequestOnly.setDepartementId(getLong(FRM_FIELD_DEPARTEMENT_ID));
            leaveConfigurationDetailDepartementRequestOnly.setSectionId(getLong(FRM_FIELD_SECTION_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    public  Vector getGroupDepartementId(long oidLeaveConfigMain,LeaveConfigurationDetailDepartementRequestOnly leaveConfigurationDetailDepartementRequestOnly){
        try{
            //Vector listResult = new Vector();
           // String sValsDept[] =  this.getParamsStringValues(fieldNames[FRM_FIELD_DEPARTEMENT_ID]);
           // String sValsSec[] =  this.getParamsStringValues(fieldNames[FRM_FIELD_SECTION_ID]);
            //artinya jika sizenya tidak sama maka sistem akan memberikan 
            if( leaveConfigurationDetailDepartementRequestOnly==null || (leaveConfigurationDetailDepartementRequestOnly.getDepartementIds()==null) || (leaveConfigurationDetailDepartementRequestOnly.getDepartementIds().length<1)){
                this.setMessage("selec department is null");
                return getListGroupDepartement();
            }
            if( (leaveConfigurationDetailDepartementRequestOnly.getDepartementIds()!=null && leaveConfigurationDetailDepartementRequestOnly.getDepartementIds().length>1  && leaveConfigurationDetailDepartementRequestOnly.getSectionId()!=0)){
                this.setMessage("please input department some one, if you input section ");
                return getListGroupDepartement();
            }
            for(int i=0; i < leaveConfigurationDetailDepartementRequestOnly.getDepartementIds().length ; i++){
                try{
                   LeaveConfigurationDetailDepartementRequestOnly objleaveConfigurationDetailDepartementRequestOnly = new LeaveConfigurationDetailDepartementRequestOnly();
                   leaveConfigurationDetailDepartementRequestOnly.setLeaveConfigurationMainIdRequestOnly(oidLeaveConfigMain);
                   long oidDepartement = new Long(Long.parseLong(leaveConfigurationDetailDepartementRequestOnly.getDepartementIds()[i]));
                    long oidSection = leaveConfigurationDetailDepartementRequestOnly.getSectionId();
                   objleaveConfigurationDetailDepartementRequestOnly.setDepartementId(oidDepartement);
                   objleaveConfigurationDetailDepartementRequestOnly.setSectionId(oidSection);
                   getListGroupDepartement().add(objleaveConfigurationDetailDepartementRequestOnly); 
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
