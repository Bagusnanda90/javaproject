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
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationMain;

public class FrmLeaveConfigurationMain extends FRMHandler implements I_FRMInterface, I_FRMType{
     private LeaveConfigurationMain leaveConfigurationMain;

    public static final String FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME = "FRM_NAME_LEAVE_CONFIGURATION_NAME";

    public static final int FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID = 0;
    public static final int FRM_FIELD_EMPLOYEE_ID = 1;
    public static final int FRM_FIELD_PLUS_NOTE_APPROVALL = 2;//tambahan kata" untuk approvall misalkan satrya ramayu for manager

    public static String[] fieldNames = {
        "FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID", "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_PLUS_NOTE_APPROVALL"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_STRING
    };

    public FrmLeaveConfigurationMain() {
    }

    public FrmLeaveConfigurationMain(LeaveConfigurationMain leaveConfigurationMain) {
        this.leaveConfigurationMain = leaveConfigurationMain;
    }

    public FrmLeaveConfigurationMain(HttpServletRequest request, LeaveConfigurationMain leaveConfigurationMain) {
        super(new FrmLeaveConfigurationMain(leaveConfigurationMain), request);
        this.leaveConfigurationMain = leaveConfigurationMain;
    }

    public String getFormName() {
        return FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME;
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

    public LeaveConfigurationMain getEntityObject() {
        return leaveConfigurationMain;
    }

    public void requestEntityObject(LeaveConfigurationMain leaveConfigurationMain) {
        try {
            this.requestParam();
            //leaveConfigurationMain.setEmployeeId(getLong(FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID));
            leaveConfigurationMain.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            leaveConfigurationMain.setPlusNoteApprovall(getString(FRM_FIELD_PLUS_NOTE_APPROVALL));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
     

}
