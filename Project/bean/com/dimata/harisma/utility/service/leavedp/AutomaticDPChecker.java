
package com.dimata.harisma.utility.service.leavedp;

import com.dimata.harisma.entity.service.*;
import java.util.Date;

/**
 *
 * @author bayu
 */

public class AutomaticDPChecker implements Runnable {
    
    
    public AutomaticDPChecker() {        
    }

    
    /*
     * this method will be executed by thread
     */
     
    public void run() {
        System.out.println(".:: Automatic DP checker service started  ::.");
        
        /* get processor class */
        DPCheckerService service = new DPCheckerService();
        
        /* get the appropriate service configuration */
        ServiceConfiguration dpCheckService = PstServiceConfiguration.getSvcConfigurationByType(PstServiceConfiguration.SERVICE_TYPE_DAY_PAYMENT);
                
        /* service initialization status */
        boolean firstRun = true;
        
        
        /* repeat process while service is running */        
        while(DPCheckerService.getStatus()) 
        {
            try {                
               
                if(firstRun)    // service started just now
                {
                    // get thread sleep time
                    int sleepTime = getSleepTime(new Date(), dpCheckService.getStartTime());
                    
                    // sleep the thread until config start date is reached
                    System.out.println(".:: First process start running DP checker services, thread now sleep/pause for "+(sleepTime / (60 * 1000))+" minutes ("+sleepTime+" ms)");
                    Thread.sleep(sleepTime);                                                               
                       
                    firstRun = false;  // not first run anymore on next iteration
                    
                }
                else            // service already been started before
                {
                    // main processing appears here
                    service.process();
                    
                    // get thread sleep time for next checking process
                    int sleepTime = dpCheckService.getPeriode() * 60 * 1000;
                    
                    // sleep the thread for next checking period 
                    System.out.println(".:: DP checker process finish, thread now sleep/pause for "+ dpCheckService.getPeriode()+" minutes ("+sleepTime+" ms)");
                    Thread.sleep(sleepTime);    
                    
                }
            }
            catch(InterruptedException e) {
                System.out.println("Thread is interrupted");
                System.out.println("Service is now halting");
            }
            catch(Exception e) {
                System.out.println(">>> Exception on AutomaticDPChecker service : " + e.getMessage());  
                break;
            }            
        }
    }
    
    /**
     * @desc count sleep time range from now on 
     *       to service configuration start time
     * @param serviceDateStart
     * @param configDateStart
     * @return sleep time
     */    
    
    public int getSleepTime(Date serviceDateStart, Date configDateStart){
        Date date1 = new Date();
        Date date2 = new Date();
        
        date1.setHours(serviceDateStart.getHours());
        date1.setMinutes(serviceDateStart.getMinutes());
        date1.setSeconds(serviceDateStart.getSeconds());
        
        date2.setHours(configDateStart.getHours());
        date2.setMinutes(configDateStart.getMinutes());
        date2.setSeconds(configDateStart.getSeconds());
        
        /* if config start date have been left, start on next day */
        if(configDateStart.getHours() < serviceDateStart.getHours())
        {
            int dtEnd = date2.getDate();
            date2.setDate(dtEnd+1);
        }        
        
        /* get the difference in miliseconds */
        long st = date1.getTime();
        long en = date2.getTime();
        long diff = en - st;
        
        if(diff < 0)
            diff = 0;
        
        return (new Long(diff)).intValue();
    }    
    
}
