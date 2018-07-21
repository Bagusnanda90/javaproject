/*
 * SessAbsence.java
 *
 * Created on July 26, 2004, 2:12 PM
 */

package com.dimata.harisma.utility.service.presence;

import java.util.Date;
import com.dimata.harisma.entity.service.*;

/**
 *
 * @author  gedhy
 */
public class ServiceAbsence  implements Runnable {
    
    /** Creates a new instance of SessAbsence */   
    public ServiceAbsence() {
    }
    
    /**
     * prosesnya ?
     * 1. Pencarian data employee
     * 2. pengecekan public holidays
     * 3. pencarian leave stock
     * 4. pencarian stock dp management
     * 5. proses pengubahan status dp dari NON_AKTIF menjadi AKTIF    
     * @created by Edhy
     */
    public synchronized void run() 
    {        
        System.out.println(".................... ServiceAbsence started ....................");
        AbsenceAnalyser absenceAnalyser = new AbsenceAnalyser();
        Date dt = absenceAnalyser.getDate();
        
        // added on 20040928
        
                
        // sleeping time for first process
        //update by satrya 2012-08-31
        ServiceConfiguration svcConf = new ServiceConfiguration();
        int sleepTime = 10000;
        try{
        svcConf = PstServiceConfiguration.getSvcConfigurationByType(PstServiceConfiguration.SERVICE_TYPE_ABSENCE);
        sleepTime = getSleepTime(new Date(), svcConf.getStartTime());                                       
        System.out.println(".:: First process start running absence services, thread now sleep/pause for "+(sleepTime / (60 * 1000))+" minutes ("+sleepTime+" ms)");
        } catch(Exception ex){
            System.out.println(ex);
            sleepTime = 10000;
        }                                                                    
                    
                   
                
        
        while(absenceAnalyser.isRunning())      
        {
            Date dtTmp = new Date();
            Date dtNow = new Date(dtTmp.getYear(), dtTmp.getMonth(), dtTmp.getDate());            
            try 
            {   
                // added on 20040928
                 Thread.sleep(sleepTime);  
                {
                    
                    // memproses tanggal yang terlambat
                    //System.out.println("dt    : " + dt);
                    //System.out.println("dtNow : " + dtNow);                
                    if ( dt.getTime() < dtNow.getTime() )   
                    {                      
                        //System.out.println("Process absence on : " + dt);                    
                        absenceAnalyser.checkEmployeeAbsence(dt,0,0,"",""); 
                        //update by satrya 2012-08-01
                        //absenceAnalyser.checkEmployeeAbsence(dt,0,0,""); 
                        dt.setDate(dt.getDate() + 1);    
                        Thread.sleep(500);
                    }

                    // jika tanggal sekarang
                    else 
                    {                          
                        // sleeping time for next process
                        System.out.println("Do not process absence on : " + dt);                    
                        //ServiceConfiguration svcConf = PstServiceConfiguration.getSvcConfigurationByType(PstServiceConfiguration.SERVICE_TYPE_ABSENCE);                        
                        
                        // convert periode (in minutes) to miliseconds (multiply by 60 * 1000)
                        sleepTime = svcConf.getPeriode() * 60 * 1000;                        
                        System.out.println(".:: proses cek absence finished, thread now sleep/pause for "+svcConf.getPeriode()+" minutes ("+sleepTime+" ms)");
                        Thread.sleep(sleepTime);                                            
                    }                                        
                }             
                
            }
            catch (Exception e) 
            {                
                System.out.println("Exc ServiceAbsence : " + e.toString());
            }
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
