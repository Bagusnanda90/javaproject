/*
 */

package com.dimata.harisma.form.attendance;

// import core java package
import javax.servlet.http.HttpServletRequest;


// import qdep package
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;

// import project package
import com.dimata.harisma.entity.attendance.MachineTransaction;


/**
 *
 * @author  artha
 */
public class FrmMachineTransaction  extends FRMHandler implements I_FRMInterface, I_FRMType {
    private MachineTransaction machineTransaction;
    
    public static final String FRM_MACHINE_TRANSACTION =  "FRM_MACHINE_TRANSACTION" ;
    
    public static final int FRM_FIELD_MACHINE_TRANS_ID  =  0 ;    
    public static final int FRM_FIELD_CARD_ID           =  1 ;
    public static final int FRM_FIELD_DATE_TRANS        =  2 ;
    public static final int FRM_FIELD_MODE              =  3 ;
    public static final int FRM_FIELD_STATION           =  4 ;
    public static final int FRM_FIELD_POSTED            =  5 ;
    
    public static String[] fieldNames = {
        "FRM_FIELD_MACHINE_TRANS_ID",  
        "FRM_FIELD_CARD_ID",
        "FRM_FIELD_DATE_TRANS",  
        "FRM_FIELD_MODE",
        "FRM_FIELD_STATION",  
        "FRM_FIELD_POSTED"
    } ;
    
    public static int[] fieldTypes = {
        TYPE_LONG,  
        TYPE_STRING,
        TYPE_DATE,
        TYPE_STRING,  
        TYPE_STRING,  
        TYPE_INT
    } ;
    
    
    /** Creates a new instance of FrmAlStockTaken */
    public FrmMachineTransaction() {
    }
    
    public FrmMachineTransaction(MachineTransaction machineTransaction){
        this.machineTransaction = machineTransaction;
    }
    
     public FrmMachineTransaction(HttpServletRequest request, MachineTransaction machineTransaction){
        super(new FrmMachineTransaction(machineTransaction), request);
        this.machineTransaction = machineTransaction;
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
       return FRM_MACHINE_TRANSACTION;
    }
    
    public MachineTransaction getEntityObject(){ return machineTransaction; }
     
     public void requestEntityObject(MachineTransaction machineTransaction) 
    {
        try
        {
            this.requestParam();
            machineTransaction.setOID(getLong(FRM_FIELD_MACHINE_TRANS_ID));
            machineTransaction.setCardId(getString(FRM_FIELD_CARD_ID));
            machineTransaction.setDateTransaction(getDate(FRM_FIELD_DATE_TRANS));
            machineTransaction.setMode(getString(FRM_FIELD_MODE));
            machineTransaction.setStation(getString(FRM_FIELD_STATION));
            machineTransaction.setPosted(getInt(FRM_FIELD_POSTED));
        }
        catch(Exception e)
        {
            System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
    
}
