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
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailPosition;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailPositionRequestOnly;

public class FrmLeaveConfigurationPositionRequestOnly extends FRMHandler implements I_FRMInterface, I_FRMType{
     private LeaveConfigurationDetailPositionRequestOnly leaveConfigurationDetailPositionRequestOnly;
     private Vector listGroupPosition = new Vector();
     private String message ="";
    public static final String FRM_NAME_LEAVE_CONFIGURATION_DETAIL_POSITION_REQUEST_ONLY = "FRM_NAME_LEAVE_CONFIGURATION_DETAIL_POSITION_REQUEST_ONLY";

    public static final int FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY = 0;
    public static final int FRM_FIELD_POSITION_MIN_ID = 1;
    public static final int FRM_FIELD_POSITION_MAX_ID = 2;

    public static String[] fieldNames = {
        "FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY", "FRM_FIELD_POSITION_MIN_ID",
        "FRM_FIELD_POSITION_MAX_ID"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG
    };

    public FrmLeaveConfigurationPositionRequestOnly() {
    }

    public FrmLeaveConfigurationPositionRequestOnly(LeaveConfigurationDetailPositionRequestOnly objLeaveConfigurationDetailPositionRequestOnly) {
        this.leaveConfigurationDetailPositionRequestOnly = objLeaveConfigurationDetailPositionRequestOnly;
    }

    public FrmLeaveConfigurationPositionRequestOnly(HttpServletRequest request, LeaveConfigurationDetailPositionRequestOnly objLeaveConfigurationDetailPositionRequestOnly) {
        super(new FrmLeaveConfigurationPositionRequestOnly(objLeaveConfigurationDetailPositionRequestOnly), request);
        this.leaveConfigurationDetailPositionRequestOnly = objLeaveConfigurationDetailPositionRequestOnly;
    }

    public String getFormName() {
        return FRM_NAME_LEAVE_CONFIGURATION_DETAIL_POSITION_REQUEST_ONLY;
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

    public LeaveConfigurationDetailPositionRequestOnly getEntityObject() {
        return leaveConfigurationDetailPositionRequestOnly;
    }

    public void requestEntityObject(LeaveConfigurationDetailPositionRequestOnly objLeaveConfigurationDetailPositionRequestOnly) {
        try {
            this.requestParam();
            objLeaveConfigurationDetailPositionRequestOnly.setPositionMin(getLong(FRM_FIELD_POSITION_MIN_ID));
            objLeaveConfigurationDetailPositionRequestOnly.setPositionMax(getLong(FRM_FIELD_POSITION_MAX_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

    /**
     * @return the listGroupPosition
     */
    public Vector getListGroupPosition() {
        return listGroupPosition;
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
