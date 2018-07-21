/*
 * FrmCanteenVisitation.java
 *
 * Created on May 18, 1999, 11:42 AM
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
public class FrmCanteenVisitation extends FRMHandler implements I_FRMInterface, I_FRMType 
{
    
    private CanteenVisitation canteenVisitation;
    
    public static final String FRM_NAME_CANTEEN_VISITATION	=  "FRM_NAME_CANTEEN_VISITATION" ;
    
    public static final int FRM_FIELD_VISITATION_ID = 0;
    public static final int FRM_FIELD_EMPLOYEE_ID = 1;
    public static final int FRM_FIELD_VISITATION_TIME = 2;
    public static final int FRM_FIELD_STATUS = 3;
    public static final int FRM_FIELD_ANALYZED = 4;
    public static final int FRM_FIELD_TRANSFERRED = 5;
    public static final int FRM_FIELD_NUM_OF_VISITATION = 6;
    
    public static final String fieldNames[] = {
        "VISITATION_ID",
        "EMPLOYEE_ID",
        "VISITATION_TIME",
        "STATUS",
        "ANALYZED",
        "TRANSFERRED",
        "NUM_OF_VISITATION"
    };
    
    public static final int fieldTypes[] = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT
    };
    
    
    public FrmCanteenVisitation(){
    }
    public FrmCanteenVisitation(CanteenVisitation canteenVisitation){
        this.canteenVisitation = canteenVisitation;
    }
    
    public FrmCanteenVisitation(HttpServletRequest request, CanteenVisitation canteenVisitation){
        super(new FrmCanteenVisitation(canteenVisitation), request);
        this.canteenVisitation = canteenVisitation;
    }
    
    public String getFormName() { return FRM_NAME_CANTEEN_VISITATION; }
    
    public int[] getFieldTypes() { return fieldTypes; }
    
    public String[] getFieldNames() { return fieldNames; }
    
    public int getFieldSize() { return fieldNames.length; }
    
    public CanteenVisitation getEntityObject(){ return canteenVisitation; }
    
    public void requestEntityObject(CanteenVisitation canteenVisitation) {
        try{
            this.requestParam();
            canteenVisitation.setVisitationId(getLong(FRM_FIELD_EMPLOYEE_ID));
            canteenVisitation.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            canteenVisitation.setVisitationTime(getDate(FRM_FIELD_VISITATION_TIME));
            canteenVisitation.setStatus(getInt(FRM_FIELD_STATUS));  
            canteenVisitation.setAnalyzed(getInt(FRM_FIELD_ANALYZED));
            canteenVisitation.setTransferred(getInt(FRM_FIELD_TRANSFERRED));
            canteenVisitation.setNumOfVisitation(getInt(FRM_FIELD_NUM_OF_VISITATION));
        }catch(Exception e){
            System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
    
}
