/*
 * ServiceManLeave.java
 *
 * Created on February 20, 2004, 6:45 AM
 */

package com.dimata.harisma.entity.admin.service;

/**
 *
 * @author  sutaya
 */
import java.sql.*;
import java.util.*;
//import java.util.Date;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.system.entity.system.*;

public class ServiceManagerLeave {
    
    /** Creates a new instance of ServiceManLeave */
   public static boolean running = false;
    
   public ServiceManagerLeave() {
    
  }
   
   public void startService(){
        if(running)
            return;
        ServiceManagerLeave objMan = new ServiceManagerLeave();
        Thread thLocker = new Thread(new ServiceLeave());
        thLocker.setDaemon(false);
        running = true;
        thLocker.start();
   }
   
   public void stopService(){
        running = false;
   }

   public boolean getStatus(){       
        return running;
   }
   
   public static void processEmployee(){
       /*find employee is comencing date today*/
       int i =0;
       //System.out.println("PstSystemProperty.getValueByName(AL+10) : "+PstSystemProperty.getValueByName("AL+10"));
        //System.out.println("PstSystemProperty.getValueByName(AL-10) : "+PstSystemProperty.getValueByName("AL-10"));
        //System.out.println("PstSystemProperty.getValueByName(LL%) : "+PstSystemProperty.getValueByName("LL%"));
        //System.out.println("PstSystemProperty.getValueByName(AL<) : "+PstSystemProperty.getValueByName("AL<"));
        //System.out.println("PstSystemProperty.getValueByName(AL>) : "+PstSystemProperty.getValueByName("AL>"));
        //System.out.println("PstSystemProperty.getValueByName(LL) : "+PstSystemProperty.getValueByName("LL"));
       PstServiceManagerLeave.systemProp[0] = Integer.parseInt(PstSystemProperty.getValueByName("AL+10"));
       PstServiceManagerLeave.systemProp[1] = Integer.parseInt(PstSystemProperty.getValueByName("AL-10"));
       PstServiceManagerLeave.systemProp[2] = Integer.parseInt(PstSystemProperty.getValueByName("LL%"));
       PstServiceManagerLeave.systemProp[3] = Integer.parseInt(PstSystemProperty.getValueByName("AL<"));
       PstServiceManagerLeave.systemProp[4] = Integer.parseInt(PstSystemProperty.getValueByName("AL>"));
       PstServiceManagerLeave.systemProp[5] = Integer.parseInt(PstSystemProperty.getValueByName("LL"));
       
       Employee employee = new Employee();
       Vector vctEmployee = PstServiceManagerLeave.findEmployee();
       while(i < vctEmployee.size()){            
            employee = (Employee)vctEmployee.get(i);            
            PstServiceManagerLeave.processSvcLeaveStock(employee);
            i++;
       }
   }
   
    
}
