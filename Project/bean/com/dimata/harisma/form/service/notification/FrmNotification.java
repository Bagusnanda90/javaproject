/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.service.notification;

import com.dimata.harisma.entity.service.notification.Notification;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author GUSWIK
 */
public class FrmNotification extends FRMHandler implements I_FRMInterface, I_FRMType {

    private Notification notification;
    public static final String FRM_NAME_NOTIFICATION = "FRM_NAME_NOTIFICATION";
    public static final int FRM_FIELD_NOTIFICATION_ID = 0;
    public static final int FRM_FIELD_MODUL_NAME = 1;
    public static final int FRM_FIELD_TYPE = 2;
    public static final int FRM_FIELD_TIME_DISTANCE = 3;
    public static final int FRM_FIELD_SUBJECT = 4;
    public static final int FRM_FIELD_TEXT = 5;
    public static final int FRM_FIELD_DATETIME = 6;
    public static final int FRM_FIELD_STATUS = 7;
    public static final int FRM_FIELD_TARGET_EMPLOYEE = 8;
    public static final int FRM_FIELD_TIME = 9;
    
    public static String[] fieldNames = {
        "FRM_FIELD_NOTIFICATION_ID",
        "FRM_FIELD_MODUL_NAME",
        "FRM_FIELD_TYPE",
        "FRM_FIELD_TIME_DISTANCE",
        "FRM_FIELD_SUBJECT",
        "FRM_FIELD_TEXT",
        "FRM_FIELD_DATETIME",
        "FRM_FIELD_STATUS",
        "FRM_FIELD_TARGET_EMPLOYEE",
        "FRM_FIELD_TIME"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING
    };

    public FrmNotification() {
    }

    public FrmNotification(Notification notification) {
        this.notification = notification;
    }

    public FrmNotification(HttpServletRequest request, Notification notification) {
        super(new FrmNotification(notification), request);
        this.notification = notification;
    }

    public String getFormName() {
        return FRM_NAME_NOTIFICATION;
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

    public Notification getEntityObject() {
        return notification;
    }

    public void requestEntityObject(Notification notification) {
        try {
            this.requestParam();
            notification.setNotificationId(getLong(FRM_FIELD_NOTIFICATION_ID));
            notification.setModulName(getInt(FRM_FIELD_MODUL_NAME));
            notification.setType(getString(FRM_FIELD_TYPE));
            notification.setTimeDistance(getString(FRM_FIELD_TIME_DISTANCE));
            notification.setSubject(getString(FRM_FIELD_SUBJECT));
            notification.setText(getString(FRM_FIELD_TEXT));
            notification.setDateTime(getDate(FRM_FIELD_DATETIME));
            notification.setStatus(getInt(FRM_FIELD_STATUS));
            notification.setTargetEmployee(getInt(FRM_FIELD_TARGET_EMPLOYEE));
            notification.setTime(getString(FRM_FIELD_TIME));

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
    public void requestEntityObjectSpecial(Notification notification) {
        try {
            this.requestParam();
            notification.setNotificationId(getLong(FRM_FIELD_NOTIFICATION_ID));
            notification.setModulName(getInt(FRM_FIELD_MODUL_NAME));
            
            String typeEmail = this.getParamString("type_email").equals("")?"0":(this.getParamString("type_email")==null?"0":"1");
            String typeSMS = this.getParamString("type_sms").equals("")?"0":(this.getParamString("type_sms")==null?"0":"1");
            String typeAlert = this.getParamString("type_alert").equals("")?"0":(this.getParamString("type_alert")==null?"0":"1");
            
            String time30 = this.getParamString("time30").equals("")?"0":(this.getParamString("time30")==null?"0":"1");
            String time7 = this.getParamString("time7").equals("")?"0":(this.getParamString("time7")==null?"0":"1");
            String time1 = this.getParamString("time1").equals("")?"0":(this.getParamString("time1")==null?"0":"1");
            String time0 = this.getParamString("time0").equals("")?"0":(this.getParamString("time0")==null?"0":"1");
            
            notification.setType(typeEmail+"-"+typeSMS+"-"+typeAlert);
            notification.setTimeDistance(time30+"-"+time7+"-"+time1+"-"+time0);
            notification.setSubject(getString(FRM_FIELD_SUBJECT));
            notification.setText(getString(FRM_FIELD_TEXT));
            notification.setDateTime(getDate(FRM_FIELD_DATETIME));
            notification.setStatus(getInt(FRM_FIELD_STATUS));
            notification.setTargetEmployee(getInt(FRM_FIELD_TARGET_EMPLOYEE));
            notification.addArrRecipient(getParamsStringValuesStaticFrm("recipient"));
            notification.setRecipientAdd(this.getParamString("recipientAdd"));
            notification.setSmsNum(this.getParamString("sms"));
            notification.addArrCC(getParamsStringValuesStaticFrm("recipientCC"));
            notification.setCcAdd(this.getParamString("ccAdd"));
            notification.addArrBCC(getParamsStringValuesStaticFrm("recipientBCC"));
            notification.setBccAdd(this.getParamString("bccAdd"));
            notification.setTime(getString(FRM_FIELD_TIME));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
