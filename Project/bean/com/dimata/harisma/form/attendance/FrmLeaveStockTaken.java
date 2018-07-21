/*
 * FrmLeaveStockTaken.java
 *
 * Created on July 23, 2004, 4:19 PM
 */

package com.dimata.harisma.form.attendance;

// import core java package
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// import qdep package
import com.dimata.qdep.form.*;

// import project package
import com.dimata.harisma.entity.attendance.*;

/**
 *
 * @author  gedhy
 */
public class FrmLeaveStockTaken  extends FRMHandler implements I_FRMInterface, I_FRMType 
{    
    private LeaveStockTaken leaveStockTaken;
    
    public static final String FRM_NAME_LEAVESTOCK_TAKEN =  "FRM_NAME_LEAVESTOCK" ;
    
    public static final int FRM_FIELD_LEAVE_STOCK_TAKEN_ID  =  0 ;    
    public static final int FRM_FIELD_LEAVE_STOCK_ID        =  1 ;
    public static final int FRM_FIELD_IDX_LEAVE_TAKEN       =  2 ;
    public static final int FRM_FIELD_EMP_SCHEDULE_ID       =  3 ;
    public static final int FRM_FIELD_IDX_DATE_SCHEDULE     =  4 ;
    public static final int FRM_FIELD_LEAVE_TYPE            =  5 ;
    
    public static String[] fieldNames = {
        "FRM_FIELD_LEAVE_STOCK_TAKEN_ID",  
        "FRM_FIELD_LEAVE_STOCK_ID",
        "FRM_FIELD_IDX_LEAVE_TAKEN",  
        "FRM_FIELD_EMP_SCHEDULE_ID",
        "FRM_FIELD_IDX_DATE_SCHEDULE",  
        "FRM_FIELD_LEAVE_TYPE"
    } ;
    
    public static int[] fieldTypes = {
        TYPE_LONG,  
        TYPE_LONG,
        TYPE_INT,
        TYPE_LONG,  
        TYPE_INT,  
        TYPE_INT
    } ;
    
    public FrmLeaveStockTaken(){
    }
    public FrmLeaveStockTaken(LeaveStockTaken leaveStockTaken){
        this.leaveStockTaken = leaveStockTaken;
    }
    
    public FrmLeaveStockTaken(HttpServletRequest request, LeaveStockTaken leaveStockTaken){
        super(new FrmLeaveStockTaken(leaveStockTaken), request);
        this.leaveStockTaken = leaveStockTaken;
    }
    
    public String getFormName() { return FRM_NAME_LEAVESTOCK_TAKEN; }
    
    public int[] getFieldTypes() { return fieldTypes; }
    
    public String[] getFieldNames() { return fieldNames; }
    
    public int getFieldSize() { return fieldNames.length; }
    
    public LeaveStockTaken getEntityObject(){ return leaveStockTaken; }
    
    public void requestEntityObject(LeaveStockTaken leaveStockTaken) 
    {
        try
        {
            this.requestParam();
            leaveStockTaken.setLeaveStockId(getLong(FRM_FIELD_LEAVE_STOCK_ID));   
            leaveStockTaken.setIdxLeaveTaken(getInt(FRM_FIELD_IDX_LEAVE_TAKEN));
            leaveStockTaken.setEmpScheduleId(getLong(FRM_FIELD_EMP_SCHEDULE_ID));
            leaveStockTaken.setIdxDateSchedule(getInt(FRM_FIELD_IDX_DATE_SCHEDULE));
            leaveStockTaken.setLeaveType(getInt(FRM_FIELD_LEAVE_TYPE));
        }
        catch(Exception e)
        {
            System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
    
}
