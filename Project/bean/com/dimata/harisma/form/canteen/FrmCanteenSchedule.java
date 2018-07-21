/*
 * FrmCanteenSchedule.java
 *
 * Created on April 23, 2005, 11:55 AM
 */

package com.dimata.harisma.form.canteen;

// java package 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 

// qdep package 
import com.dimata.qdep.form.*;

// harisma package 
import com.dimata.harisma.entity.canteen.*;

/**
 *
 * @author  gedhy
 */
public class FrmCanteenSchedule  extends FRMHandler implements I_FRMInterface, I_FRMType 
{
    
    private CanteenSchedule canteenSchedule;
    
    public static final String FRM_NAME_CANTEEN_SCHEDULE	=  "FRM_NAME_CANTEEN_SCHEDULE" ;
    
    public static final  int FRM_FIELD_CANTEEN_SCHEDULE_ID = 0;
    public static final  int FRM_FIELD_CODE = 1;
    public static final  int FRM_FIELD_NAME = 2;
    public static final  int FRM_FIELD_SCHEDULE_DATE = 3;
    public static final  int FRM_FIELD_TIME_OPEN = 4;
    public static final  int FRM_FIELD_TIME_CLOSE = 5;
    
    public static String[] fieldNames = {
        "FRM_FIELD_CANTEEN_SCHEDULE_ID",  "FRM_FIELD_CODE",
        "FRM_FIELD_NAME",  "FRM_FIELD_SCHEDULE_DATE",
        "FRM_FIELD_TIME_OPEN",  "FRM_FIELD_TIME_CLOSE"
    } ;
    
    public static int[] fieldTypes = {
        TYPE_LONG,  TYPE_STRING,
        TYPE_STRING,  TYPE_DATE,
        TYPE_DATE  ,  TYPE_DATE
    } ;
    
    public FrmCanteenSchedule(){
    }
    public FrmCanteenSchedule(CanteenSchedule canteenSchedule){
        this.canteenSchedule = canteenSchedule;
    }
    
    public FrmCanteenSchedule(HttpServletRequest request, CanteenSchedule canteenSchedule){
        super(new FrmCanteenSchedule(canteenSchedule), request);
        this.canteenSchedule = canteenSchedule;
    }
    
    public String getFormName() { return FRM_NAME_CANTEEN_SCHEDULE; }
    
    public int[] getFieldTypes() { return fieldTypes; }
    
    public String[] getFieldNames() { return fieldNames; }
    
    public int getFieldSize() { return fieldNames.length; }
    
    public CanteenSchedule getEntityObject(){ return canteenSchedule; }
    
    public void requestEntityObject(CanteenSchedule canteenSchedule) {
        try{
            this.requestParam();
            canteenSchedule.setSCode(getString(FRM_FIELD_CODE));
            canteenSchedule.setSName(getString(FRM_FIELD_NAME));
            canteenSchedule.setDScheduleDate(getDate(FRM_FIELD_SCHEDULE_DATE));
            canteenSchedule.setTTimeOpen(getDate(FRM_FIELD_TIME_OPEN));
            canteenSchedule.setTTimeClose(getDate(FRM_FIELD_TIME_CLOSE));
        }catch(Exception e){
            System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
}
