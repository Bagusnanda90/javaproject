/*
 * FrmSpecialLeaveStock.java
 *
 * Created on September 10, 2007, 5:14 PM
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
 * @author  yunny
 */
public class FrmSpecialLeaveStock  extends FRMHandler implements I_FRMInterface, I_FRMType {
    private SpecialLeaveStock specialLeaveStock;
    
    public static final String FRM_SPECIAL_LEAVE_STOCK =  "FRM_SPECIAL_LEAVE_STOCK" ;
    
    public static final int FRM_FIELD_SPECIAL_LEAVE_STOCK_ID  =  0 ;    
    public static final int FRM_FIELD_EMPLOYEE_ID             =  1 ;
    public static final int FRM_FIELD_TAKEN_DATE              =  2 ;
    public static final int FRM_FIELD_TAKEN_QTY               =  3 ;
    public static final int FRM_FIELD_SYMBOL_ID               =  4;
    public static final int FRM_FIELD_SPECIAL_LEAVE_ID        =  5 ;
    public static final int FRM_FIELD_LEAVE_STATUS            =  6 ;
    
    public static String[] fieldNames = {
        "FRM_FIELD_SPECIAL_LEAVE_STOCK_ID",  
        "FRM_FIELD_EMPLOYEE_ID",  
        "FRM_FIELD_TAKEN_DATE",
        "FRM_FIELD_TAKEN_QTY",
        "FRM_FIELD_SYMBOL_ID",
        "FRM_FIELD_SPECIAL_LEAVE_ID",
        "FRM_FIELD_LEAVE_STATUS"
    } ;
    
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,  
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT
    } ;
    
    
    /** Creates a new instance of FrmSpecialLeaveStock */
    public FrmSpecialLeaveStock() {
    }
    
    public FrmSpecialLeaveStock(SpecialLeaveStock specialLeaveStock){
        this.specialLeaveStock = specialLeaveStock;
    }
    
     public FrmSpecialLeaveStock(HttpServletRequest request, SpecialLeaveStock specialLeaveStock){
        super(new FrmSpecialLeaveStock(specialLeaveStock), request);
        this.specialLeaveStock = specialLeaveStock;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int getFieldSize() {
        return fieldNames.length; 
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getFormName() {
       return FRM_SPECIAL_LEAVE_STOCK;
    }
     public SpecialLeaveStock getEntityObject(){ return specialLeaveStock; }
     
     public void requestEntityObject(SpecialLeaveStock specialLeaveStock) 
    {
        try
        {
            this.requestParam();
            specialLeaveStock.setOID(getLong(FRM_FIELD_SPECIAL_LEAVE_STOCK_ID));   
            specialLeaveStock.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            specialLeaveStock.setTakenDate(getDate(FRM_FIELD_TAKEN_DATE));
            specialLeaveStock.setTakenQty(getInt(FRM_FIELD_TAKEN_QTY));
            specialLeaveStock.setSymbolId(getLong(FRM_FIELD_SYMBOL_ID));
            specialLeaveStock.setSpecialLeaveId(getLong(FRM_FIELD_SPECIAL_LEAVE_ID));
            specialLeaveStock.setLeaveStatus(FRM_FIELD_LEAVE_STATUS);            
        }
        catch(Exception e)
        {
            System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
    
}
