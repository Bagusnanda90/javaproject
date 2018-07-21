/*
 * SessAbsence.java
 *
 * Created on July 26, 2004, 2:12 PM
 */

package com.dimata.harisma.utility.service.message;

import com.dimata.harisma.utility.machine.*;
import com.dimata.harisma.entity.employee.EmpMessage;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmpMessage;
import com.dimata.harisma.utility.service.presence.*;
import java.util.Date;
import com.dimata.harisma.entity.service.*;
import com.dimata.harisma.session.employee.SearchSpecialQuery;
import com.dimata.harisma.session.employee.SessEmpMessage;
import com.dimata.harisma.session.employee.SessSpecialEmployee;
import com.dimata.harisma.session.employee.SessTmpSpecialEmployee;
import com.dimata.harisma.util.email;
import com.dimata.system.entity.PstSystemProperty;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author  gedhy
 */
public class ServiceMessageToMachine  implements Runnable {
    
    String machineNumber = "01";
    String[] machineNumbers;
    String machineNumberCan = "01";
    String[] machineNumbersCan;
    
    /** Creates a new instance of SessAbsence */   
    public ServiceMessageToMachine() {
    }
    
    /**
     * prosesnya ?
     1. Mengecek apakah ada pesan pada table emp_message yang masil valid untuk dikirim
     * @created by Artha
     */
    public synchronized void run() 
    {        
        System.out.println(".................... ServiceMessageToMachine started ....................");
        
        machineNumber = String.valueOf(PstSystemProperty.getValueByName("ABSEN_TMA_NO"));
        machineNumberCan = String.valueOf(PstSystemProperty.getValueByName("CANTEEN_TMA_NO"));
        StringTokenizer strTokenizer = new StringTokenizer(machineNumber,",");
        StringTokenizer strTokenizerCan = new StringTokenizer(machineNumberCan,",");
        machineNumbers = new String[strTokenizer.countTokens()];
        machineNumbersCan = new String[strTokenizerCan.countTokens()];
        int count = 0;
        int countCan = 0;
        while(strTokenizer.hasMoreTokens()){
            machineNumbers[count] = strTokenizer.nextToken();
            System.out.println("ABSEN MACHINE :::::::::: "+machineNumbers[count]);
            count ++;
        }
        while(strTokenizerCan.hasMoreTokens()){
            machineNumbersCan[countCan] = strTokenizerCan.nextToken();
            System.out.println("CANTEEN MACHINE :::::::::: "+machineNumbersCan[countCan]);
            countCan ++;
        }
        
        MessageToMachine msgTMachine = new MessageToMachine();
        Date dt = msgTMachine.getDate();
        
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
                   // System.out.println("=========================0");
                    ServiceConfiguration svcConf = PstServiceConfiguration.getSvcConfigurationByType(PstServiceConfiguration.SERVICE_TYPE_MESSAGE);
                    //System.out.println("=========================1");
                    int sleepTime = getSleepTime(new Date(), svcConf.getStartTime());                                       
                    System.out.println(".:: First process start running ServiceMessageToMachine services, thread now sleep/pause for "+(sleepTime / (60 * 1000))+" minutes ("+sleepTime+" ms)");
                    Thread.sleep(sleepTime);                                                               
                    
                    firstProcess = false;                                       
                
                }else{
                    
                    process();
                    ServiceConfiguration svcConf = PstServiceConfiguration.getSvcConfigurationByType(PstServiceConfiguration.SERVICE_TYPE_MESSAGE);

                    // convert periode (in minutes) to miliseconds (multiply by 60 * 1000)
                    int sleepTime = svcConf.getPeriode() * 60 * 1000;                        
                    System.out.println(".:: proses cek ServiceMessageToMachine finished, thread now sleep/pause for "+svcConf.getPeriode()+" minutes ("+sleepTime+" ms)");
                    Thread.sleep(sleepTime);               
                }             
                
            }
            catch (Exception e) 
            {                
                MessageToMachine.running = false;
                System.out.println("Exc ServiceMessageToMachine : " + e.toString());
            }
        }
    }
    
    
    private void process(){
        checkBirdday();
        
         Date now = new Date();
               Vector listEmpEndedContractMail = new Vector(1, 1);
               Date dtmail = new Date();
               int lengthContractEndmail = 3;   
               dtmail.setDate(dtmail.getDate()+lengthContractEndmail);
               SearchSpecialQuery searchSpecialQueryMail = new SearchSpecialQuery();
               searchSpecialQueryMail.setRadioendcontract(1);
               searchSpecialQueryMail.addEndcontractfrom(now);
               searchSpecialQueryMail.addEndcontractto(dtmail);
               searchSpecialQueryMail.setiSex(2);
               listEmpEndedContractMail=SessSpecialEmployee.searchSpecialEmployee(searchSpecialQueryMail, 0, 0);
                String Desc = "Daftar nama employee yang akan end contract bulan ini : ";
                String emailhrd = PstSystemProperty.getValueByName("ADMIN_EMAIL");
                for (int m = 0; m < listEmpEndedContractMail.size(); m++) {
                SessTmpSpecialEmployee sessTmpSpecialEmployeeMail =  (SessTmpSpecialEmployee) listEmpEndedContractMail.get(m);
                             Desc =Desc + ", " +  sessTmpSpecialEmployeeMail.getFullName() + "(" + sessTmpSpecialEmployeeMail.getEndContractEmployee() + ")" ;
              
                }
                 email mailx = new email();
                                 
                                    mailx.sendEmail(emailhrd,"END CONTRACT DAY", Desc, true); 
        
        Vector vData = new Vector(1,1);
        vData = SessEmpMessage.listDataEmpMessageReady(new Date());
        for(int i=0;i<machineNumbers.length;i++){
            try {
                I_Machine i_Machine = MachineBroker.getMachineByNumber(machineNumbers[i]);
                Vector vTemp = new Vector(1, 1);
                EmployeeUp empUp = new EmployeeUp();
                Employee emp = new Employee();
                EmpMessage empMsg = new EmpMessage();
                i_Machine.processClearMessage();
                System.out.println("Send data to machine absent : "+i);
                for (int j = 0; j < vData.size(); j++) {
                    vTemp = (Vector) vData.get(j);
                    emp = (Employee) vTemp.get(0);
                    empMsg = (EmpMessage) vTemp.get(1);
                    
                    empUp.setBarcode(emp.getBarcodeNumber());
                    i_Machine.processSendMessage(empUp, empMsg.getMessage());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        for(int i=0;i<machineNumbersCan.length;i++){
            try {
                I_Machine i_Machine = MachineBroker.getMachineByNumber(machineNumbersCan[i]);
                Vector vTemp = new Vector(1, 1);
                EmployeeUp empUp = new EmployeeUp();
                Employee emp = new Employee();
                EmpMessage empMsg = new EmpMessage();
                i_Machine.processClearMessage();
                System.out.println("Send data to machine canteen : "+i);
                for (int j = 0; j < vData.size(); j++) {
                    vTemp = (Vector) vData.get(j);
                    emp = (Employee) vTemp.get(0);
                    empMsg = (EmpMessage) vTemp.get(1);
                    empUp.setBarcode(emp.getBarcodeNumber());
                    System.out.println("- MESSAGE : "+emp.getFullName()+" = "+empMsg.getMessage());
                    i_Machine.processSendMessage(empUp, empMsg.getMessage());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Mengecek yang ulang tahun besol dan 
     */
    private static void checkBirdday(){
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
        if(start==null || end==null){
            return 5000; // kalau ada alah null  set 5 detik
        }
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
