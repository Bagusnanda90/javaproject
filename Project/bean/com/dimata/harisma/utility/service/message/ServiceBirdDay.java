/*
 * SessAbsence.java
 *
 * Created on July 26, 2004, 2:12 PM
 * 
 * TIDAK DIPERGUNAKAN 
 * PROSES PENGECEKAN ULANG TAHUN SUDAH DISISIPKAN 
 * PADA PROSES PENGIRIMAN PESAN
 * 
 */

package com.dimata.harisma.utility.service.message;

import com.dimata.harisma.utility.machine.*;
import com.dimata.harisma.entity.employee.EmpMessage;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmpMessage;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.utility.service.presence.*;
import java.util.Date;
import com.dimata.harisma.entity.service.*;
import com.dimata.harisma.session.employee.SessEmpMessage;
import com.dimata.system.entity.PstSystemProperty;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author  gedhy
 */
public class ServiceBirdDay  implements Runnable {
    
    String machineNumber = "01";
    String[] machineNumbers;
    String machineNumberCan = "01";
    String[] machineNumbersCan;
    
    /** Creates a new instance of SessAbsence */   
    public ServiceBirdDay() {
    }
    
    /**
     * prosesnya ?
     1. Mengecek apakah ada pesan pada table emp_message yang masil valid untuk dikirim
     * @created by Artha
     */
    public synchronized void run() 
    {        
        System.out.println(".................... Bird Day started ....................");
        
        
        // added on 20040928
        boolean firstProcess = true;
        
        while (MessageToMachine.running)      
        {
            Date dtTmp = new Date();
            //Date dtNow = new Date(dtTmp.getYear(), dtTmp.getMonth(), dtTmp.getDate());            
            try 
            {
                
                // added on 20040928
                if(firstProcess)
                {
                    // sleeping time for first process
                    ServiceConfiguration svcConf = PstServiceConfiguration.getSvcConfigurationByType(PstServiceConfiguration.SERVICE_TYPE_MESSAGE);
                    int sleepTime = getSleepTime(new Date(), svcConf.getStartTime());                                       
                    System.out.println(".:: First process start running Bird Day services, thread now sleep/pause for "+(sleepTime / (60 * 1000))+" minutes ("+sleepTime+" ms)");
                    Thread.sleep(sleepTime);                                                               
                    
                    firstProcess = false;                                       
                }                
                
                
                else
                {
                    process();
                    ServiceConfiguration svcConf = PstServiceConfiguration.getSvcConfigurationByType(PstServiceConfiguration.SERVICE_TYPE_MESSAGE);

                    // convert periode (in minutes) to miliseconds (multiply by 60 * 1000)
                    int sleepTime = svcConf.getPeriode() * 60 * 1000;                        
                    System.out.println(".:: proses cek Bird Day finished, thread now sleep/pause for "+svcConf.getPeriode()+" minutes ("+sleepTime+" ms)");
                    Thread.sleep(sleepTime);               
                }             
                
            }
            catch (Exception e) 
            {          
                MessageToMachine.running = false;
                System.out.println("Exc ServiceBirdDay : " + e.toString());
            }
        }
    }
    
    
    private void process(){
        Vector vData = new Vector(1,1);
        vData = SessEmpMessage.listEmpBirdDay(new Date());
        String mesage = "";
            mesage = PstSystemProperty.getValueByName("BIRD_DAY_MESSAGE");
        try{}catch(Exception ex){}
        for(int i=0;i<vData.size();i++){
            Employee emp = new Employee();
            emp = (Employee)vData.get(i);
            
            EmpMessage empMessage = new EmpMessage();
            empMessage = PstEmpMessage.getEmpMessageByEmpId(emp.getOID());
            empMessage.setEmployeeId(emp.getOID());
            empMessage.setIsSend(0);
            empMessage.setMessage(mesage);
            empMessage.setStartDate(new Date());
            Date endDate = new Date();
            endDate.setDate(endDate.getDate()+3);//Akan hilang dalam 3 hari
            empMessage.setEndDate(endDate);
            System.out.println(" - BIRD DAY TOMMOROW : "+emp.getFullName());
            try{
                if(empMessage.getOID()>0){
                    PstEmpMessage.updateExc(empMessage);
                }else{
                    PstEmpMessage.insertExc(empMessage);
                }
            }catch(Exception ex){}
        }
    }
    
    
    /**
     * @param start
     * @param end
     * @return
     */    
    public int getSleepTime(Date start, Date end)
    {
        Date s = new Date();
        Date e = new Date();
        
        s.setHours(start.getHours());
        s.setMinutes(start.getMinutes());
        s.setSeconds(start.getSeconds());
        
        e.setHours(end.getHours());
        e.setMinutes(end.getMinutes());
        e.setSeconds(end.getSeconds());
        
        if(end.getHours() < start.getHours())
        {
            int dtEnd = e.getDate();
            e.setDate(dtEnd+1);
        }        
        
        long st = s.getTime();
        long en = e.getTime();
        long rs = en - st;
        if(rs < 0)
        {
            rs = 0;
        }
        
        return (new Long(rs)).intValue();
    }    
    
    
        

}
