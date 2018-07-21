/*
 * ServicePresence.java
 *
 * Created on October 5, 2004, 9:03 AM
 */

package com.dimata.harisma.utility.service.presence;

import java.util.Date;
import com.dimata.harisma.entity.service.*;

/**
 *
 * @author  gedhy
 */
public class ServicePresence implements Runnable {
    
    /** Creates a new instance of ServicePresence */
    public ServicePresence() {
    }
    
    /**     
     * 1. Pencarian data employee
     * 2. pengecekan public holidays
     * 3. pencarian leave stock
     * 4. pencarian stock dp management
     * 5. proses pengubahan status dp dari NON_AKTIF menjadi AKTIF    
     * @created by Edhy
     */
    public synchronized void run() 
    {        
        System.out.println(".................... Service Presence started ....................");
        PresenceAnalyser presenceAnalyser = new PresenceAnalyser();        
        
        boolean firstProcess = true;        
        
        while (presenceAnalyser.running)      
        {
            try 
            {                    
                if(firstProcess)
                {
                    //do the first time 
                    presenceAnalyser.analyzeEmpPresenceData(0,0,"","");
                    //update by satrya 2012-08-01
                    //presenceAnalyser.analyzeEmpPresenceData(0,0,"");
                    // sleeping time for first process                    
                    ServiceConfiguration svcConf = PstServiceConfiguration.getSvcConfigurationByType(PstServiceConfiguration.SERVICE_TYPE_PRESENCE);
                    int sleepTime = getSleepTime(new Date(), svcConf.getStartTime());                                       
                    System.out.println(".:: First process start running presence services, thread now sleep/pause for "+(sleepTime / (60 * 1000))+" minutes ("+sleepTime+" ms)");
                    Thread.sleep(sleepTime);                                                               
                    
                    firstProcess = false;                                       
                }                
                else
                {
                    // memproses data presence diikuti dengan absence dan lateness status
                    presenceAnalyser.analyzeEmpPresenceData(0,0,"","");
                    //update by satrya 2012-08-01
                    // presenceAnalyser.analyzeEmpPresenceData(0,0,"");

                    // sleeping time for next process
                    ServiceConfiguration svcConf = PstServiceConfiguration.getSvcConfigurationByType(PstServiceConfiguration.SERVICE_TYPE_PRESENCE);                        
                        
                    // convert periode (in minutes) to miliseconds (multiply by 60 * 1000)
                    int sleepTime = svcConf.getPeriode() * 60 * 1000;                        
                    System.out.println(".:: proses cek presence data, absence and lateness status finished, thread now sleep/pause for "+svcConf.getPeriode()+" minutes ("+sleepTime+" ms)");
                    Thread.sleep(sleepTime);                                            
                }             
                
            }
            catch (Exception e) 
            {                
                System.out.println("Exc ServicePresence : " + e.toString());
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
