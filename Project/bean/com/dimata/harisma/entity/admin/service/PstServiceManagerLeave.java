/*
 * PstServiceManagerLeave.java
 *
 * Created on February 20, 2004, 7:38 AM
 */

package com.dimata.harisma.entity.admin.service;

/**
 *
 * @author  sutaya
 */
import java.sql.*;
import java.util.*;
import java.util.Date;
import com.dimata.util.*;
import com.dimata.qdep.db.*;

import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.system.entity.*;

public class PstServiceManagerLeave {
    
    /** Creates a new instance of PstServiceManagerLeave */
       
    public static final int ALUP = 0;
    public static final int ALDOWN = 1;
    public static final int ALMOD = 2;
    public static final int ALLESS = 3;
    public static final int ALMORE = 4;
    public static final int LL = 5;
    
    public static final int systemProp[] = {0,0,0,0,0,0};
    
    public PstServiceManagerLeave() {
    }
    
    public static ServiceManagerLeave fetchServiceConfig(){
        ServiceManagerLeave serviceManagerLeave = new ServiceManagerLeave();
        return serviceManagerLeave;
    }
    
    /*this method used to find employee is commencing date today*/
    public static Vector findEmployee(){
        Date dtNow = new Date();
        Vector vctEmp = new Vector(1,1);
        try{
            String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+ " LIKE '%"+Formater.formatDate(dtNow,"MM-dd")+"%'";
            System.out.println("whereClause : "+whereClause);
            vctEmp = PstEmployee.list(0,0,whereClause,"");
            System.out.println("Ukuran vectEmployee : "+vctEmp.size());
        }catch(Exception e){
            System.out.println("Error at findEmployee : "+e.toString());
            return new Vector();
        }
        return vctEmp;
    }
    
    /*this mmethod used to cek long time employee join */
    public static int cekLongWork(Employee employee){
        int result = 0;
        try{
            Date dtCommencing = employee.getCommencingDate();
            Date dtNow = new Date();
            String dc = Formater.formatDate(dtCommencing,"yyyy");
            String dn = Formater.formatDate(dtNow,"yyyy");
            result = Integer.parseInt(dn) - Integer.parseInt(dc);
        }catch(Exception e){
            System.out.println("Error at cek long work : "+e.toString());
        }
        return result;
    }
    
    /*this method used cek AL or not*/
    public static boolean isAlShort(int longTimeWork){
        if(longTimeWork <= PstServiceManagerLeave.systemProp[PstServiceManagerLeave.ALLESS]) //angka  11 akan diganti dg data dari data base
            return true;
        else
            return false;
    }
    /*this mmethod used cek AL  or not*/
    public static boolean isAlLong(int longTimeWork){
        if(longTimeWork >= PstServiceManagerLeave.systemProp[PstServiceManagerLeave.ALMORE])
            return true;
        else
            return false;
    }
    
    /*this mmethod used cek LL or not*/
    public static boolean isLL(int longTimeWork){
        if(longTimeWork%PstServiceManagerLeave.systemProp[PstServiceManagerLeave.ALMOD] == 0) //angka 5 akan diganti
            return true;
        else
            return false;
    }
    
    /*this method used update leave stock*/
    public static long updateLeaveStock(Employee employee){
        
        System.out.println("PstServiceManagerLeave.sytemProp[PstServiceManagerLeave.ALUP] : "+PstServiceManagerLeave.systemProp[PstServiceManagerLeave.ALUP]);
        System.out.println("PstServiceManagerLeave.sytemProp[PstServiceManagerLeave.ALDOWN] : "+PstServiceManagerLeave.systemProp[PstServiceManagerLeave.ALDOWN]);
        long oidLvStk = 0;
        LeaveStock leaveStock = new LeaveStock();
        String whereClause = PstLeaveStock.fieldNames[PstLeaveStock.FLD_EMPLOYEE_ID]+ " = "+employee.getOID();
       
        Vector vctLvStk = PstLeaveStock.list(0,0, whereClause, "");       
        if(vctLvStk.size() > 0){
           leaveStock = (LeaveStock)vctLvStk.get(0);
        }
        
        /*add AL to leave stock*/
        if(isAlShort(cekLongWork(employee))){
            leaveStock.setAlAmount(leaveStock.getAlAmount() + PstServiceManagerLeave.systemProp[PstServiceManagerLeave.ALDOWN]); 
        }else{
            leaveStock.setAlAmount(leaveStock.getAlAmount() + PstServiceManagerLeave.systemProp[PstServiceManagerLeave.ALUP]); 
        }
        /*add LL to Leave stock*/
        if(isLL(cekLongWork(employee))){
            leaveStock.setLlAmount(leaveStock.getLlAmount() + PstServiceManagerLeave.systemProp[PstServiceManagerLeave.LL]);
        }
        
        /*update leave stock if id leave stock not zero*/
        try{
            if(leaveStock.getOID() != 0){
                oidLvStk = PstLeaveStock.updateExc(leaveStock);
                System.out.println("Proses update leave stock sukses dilakukan....");
            }
        }catch(Exception e){
            System.out.println("Errorr updateLeaveStock : "+e.toString());
            return 0;
        }
        return oidLvStk;
    }
    
    /*this method used  insert to service leave stock*/
    public static void processSvcLeaveStock(Employee employee){
        /*add AL to leave stock*/
        
        long oidLeaveStck = 0;
        SvcLeaveStock svcLeaveStock = new SvcLeaveStock();
        try{
            oidLeaveStck = updateLeaveStock(employee);
        }catch(Exception e){
            System.out.println("Error processSvcLeaveStock : "+e.toString());
        }
        
        svcLeaveStock.setLeaveStockId(oidLeaveStck);
        svcLeaveStock.setPeriode(new Date());
        if(isAlShort(cekLongWork(employee))){
            svcLeaveStock.setAlAmount(PstServiceManagerLeave.systemProp[PstServiceManagerLeave.ALDOWN]); // 12 akan diganti
        }else{
            svcLeaveStock.setAlAmount(PstServiceManagerLeave.systemProp[PstServiceManagerLeave.ALUP]); //24 akan diganti
        }
        
        /*add LL to Leave stock*/
        if(isLL(cekLongWork(employee))){
            svcLeaveStock.setLlAmount(PstServiceManagerLeave.systemProp[PstServiceManagerLeave.LL]);
        }
        svcLeaveStock.setLoss(cekLongWork(employee));
        
        /*insert to table if oidLeaveStck not zero*/
        try{
            if(oidLeaveStck != 0){
                long oidSvcLvStk = PstSvcLeaveStock.insertExc(svcLeaveStock);
                System.out.println("Proses insert telah success...");
            }
        }catch(Exception e){                
             System.out.println("Error at insert : "+e.toString());
          }
        }
        
    }
