/*
 * ServiceLeave.java
 *
 * Created on February 20, 2004, 6:52 AM
 */

package com.dimata.harisma.entity.admin.service;

import java.util.*;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.util.log.*;
import com.dimata.harisma.entity.employee.*;
/**
 *
 * @author  sutaya
 */
public class ServiceLeave implements Runnable{
    
    /** Creates a new instance of ServiceLeave */
    public ServiceLeave() {
    }
    
    public void run() {
        System.out.println("Start service...............");
        while(ServiceManagerLeave.running){
            
            try{
                System.out.println("Processing running ............");
                /*processing begin*/
                ServiceManagerLeave.processEmployee();                
                //if(Formater.formatDate(new Date(), "MM").equals("01"))
                Thread.sleep(1000*60*60*24);                
                
                if(!ServiceManagerLeave.running)                
                    break;
                
            }catch(Exception e){
                System.out.println("Interupted : "+e);
            }
        }
        System.out.println("Stop service..............");
        
    }
    
   /*this method used to find employee is commencing date today*/
    /*public static Vector findEmployee(){ 
        Date dtNow = new Date();
        
        String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+ " LIKE '%xxxx%' ";
        Vector vctEmp = PstEmployee.list(0,0,whereClause,"");
        
        return vctEmp;
    }*/    
}
