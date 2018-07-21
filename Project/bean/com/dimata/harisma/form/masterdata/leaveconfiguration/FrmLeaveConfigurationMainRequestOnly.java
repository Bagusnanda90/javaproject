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

import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationMainRequestLeaveOnly;

public class FrmLeaveConfigurationMainRequestOnly extends FRMHandler implements I_FRMInterface, I_FRMType{
     private LeaveConfigurationMainRequestLeaveOnly objLeaveConfigurationMainRequestLeaveOnly;

    public static final String FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME_REQUEST_ONLY = "FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME_REQUEST_ONLY";

    public static final int FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY = 0;
    public static final int FRM_FIELD_EMPLOYEE_ID = 1;
    public static final int FRM_FIELD_PLUS_NOTE_APPROVALL = 2;//tambahan kata" untuk approvall misalkan satrya ramayu for manager

    public static String[] fieldNames = {
        "FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY", "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_PLUS_NOTE_APPROVALL"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_STRING
    };

    public FrmLeaveConfigurationMainRequestOnly() {
    }

    public FrmLeaveConfigurationMainRequestOnly(LeaveConfigurationMainRequestLeaveOnly leaveConfigurationMainRequestLeaveOnly) {
        this.objLeaveConfigurationMainRequestLeaveOnly = leaveConfigurationMainRequestLeaveOnly;
    }

    public FrmLeaveConfigurationMainRequestOnly(HttpServletRequest request, LeaveConfigurationMainRequestLeaveOnly leaveConfigurationMainRequestLeaveOnly) {
        super(new FrmLeaveConfigurationMainRequestOnly(leaveConfigurationMainRequestLeaveOnly), request);
        this.objLeaveConfigurationMainRequestLeaveOnly = leaveConfigurationMainRequestLeaveOnly;
    }

    public String getFormName() {
        return FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME_REQUEST_ONLY;
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

    public LeaveConfigurationMainRequestLeaveOnly getEntityObject() {
        return objLeaveConfigurationMainRequestLeaveOnly;
    }

    public void requestEntityObject(LeaveConfigurationMainRequestLeaveOnly leaveConfigurationMainRequestLeaveOnly) {
        try {
            this.requestParam();
            //leaveConfigurationMain.setEmployeeId(getLong(FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID));
            leaveConfigurationMainRequestLeaveOnly.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            leaveConfigurationMainRequestLeaveOnly.setPlusNoteApprovall(getString(FRM_FIELD_PLUS_NOTE_APPROVALL));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
     

}
