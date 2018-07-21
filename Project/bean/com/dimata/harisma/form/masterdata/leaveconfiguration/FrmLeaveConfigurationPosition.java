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
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailPosition;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailPosition;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailPosition;

public class FrmLeaveConfigurationPosition extends FRMHandler implements I_FRMInterface, I_FRMType{
     private LeaveConfigurationDetailPosition leaveConfigurationDetailPosition;
     private Vector listGroupPosition = new Vector();
     private String message ="";
    public static final String FRM_NAME_LEAVE_CONFIGURATION_DETAIL_POSITION = "FRM_NAME_LEAVE_CONFIGURATION_DETAIL_POSITION";

    public static final int FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID = 0;
    public static final int FRM_FIELD_POSITION_MIN_ID = 1;
    public static final int FRM_FIELD_POSITION_MAX_ID = 2;

    public static String[] fieldNames = {
        "FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID", "FRM_FIELD_POSITION_MIN_ID",
        "FRM_FIELD_POSITION_MAX_ID"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG
    };

    public FrmLeaveConfigurationPosition() {
    }

    public FrmLeaveConfigurationPosition(LeaveConfigurationDetailPosition leaveConfigurationDetailPosition) {
        this.leaveConfigurationDetailPosition = leaveConfigurationDetailPosition;
    }

    public FrmLeaveConfigurationPosition(HttpServletRequest request, LeaveConfigurationDetailPosition leaveConfigurationDetailPosition) {
        super(new FrmLeaveConfigurationPosition(leaveConfigurationDetailPosition), request);
        this.leaveConfigurationDetailPosition = leaveConfigurationDetailPosition;
    }

    public String getFormName() {
        return FRM_NAME_LEAVE_CONFIGURATION_DETAIL_POSITION;
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

    public LeaveConfigurationDetailPosition getEntityObject() {
        return leaveConfigurationDetailPosition;
    }

    public void requestEntityObject(LeaveConfigurationDetailPosition leaveConfigurationDetailPosition) {
        try {
            this.requestParam();
            leaveConfigurationDetailPosition.setPositionMin(getLong(FRM_FIELD_POSITION_MIN_ID));
            leaveConfigurationDetailPosition.setPositionMax(getLong(FRM_FIELD_POSITION_MAX_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
//     public  Vector getGroupPositionId(long oidLeaveConfigMain,LeaveConfigurationDetailPosition leaveConfigurationDetailPosition){
//        try{
//            //Vector listResult = new Vector();
//            //String sValsMin[] =  this.getParamsStringValues(fieldNames[FRM_FIELD_POSITION_MIN_ID]);
//            //String sValsMax[] =  this.getParamsStringValues(fieldNames[FRM_FIELD_POSITION_MAX_ID]);
//            //artinya jika sizenya tidak sama maka sistem akan memberikan 
//            if( (sValsMin==null) || (sValsMin.length<1) || sValsMax==null || sValsMax.length<1){
//                this.setMessage("selec Position is null");
//                return this.getListGroupPosition();
//            }
//           
//            for(int i=0; i < sValsMin.length ; i++){
//                try{
//                   LeaveConfigurationDetailPosition leaveConfigurationDetailPosition = new LeaveConfigurationDetailPosition();
//                   leaveConfigurationDetailPosition.setLeaveConfigurationMainId(oidLeaveConfigMain);
//                   long oidPosMin = new Long(Long.parseLong(sValsMin[i]));
//                    long oidPosMax = new Long(Long.parseLong(sValsMax[i]));
//                   leaveConfigurationDetailPosition.setPositionMin(oidPosMin);
//                   leaveConfigurationDetailPosition.setPositionMax(oidPosMax);
//                   this.getListGroupPosition().add(leaveConfigurationDetailPosition); 
//                }catch (Exception exc){
//                    System.out.println("EXC : getVectorLong i="+i+" - "+exc);
//                }
//            }
//            
//            return getListGroupPosition();
//            
//        }catch(Exception e){
//        }
//        
//        return new Vector(1,1);
//        
//    }

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
