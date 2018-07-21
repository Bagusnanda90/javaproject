/*
 * ServiceManagerTMA.java
 * @author  karya
 * Created on December 12, 2002, 7:24 PM
 */

package com.dimata.harisma.utility.service.tma;

import com.dimata.harisma.utility.service.tma.WatcherTMA;

public class ServiceManagerTMA { 

    public static boolean running = false;    
    
    public boolean getStatus() {
        return running;
    }
    
    public ServiceManagerTMA() {
    }

    public void startWatcherTMA() 
    {
        if (!running) {
            System.out.println("\r.:: TMA Downloader service started ... !!!");   
            try {
                running = true;
                Thread thr = new Thread(new WatcherTMA());     
                thr.setDaemon(false);                
                thr.start();
            } catch (Exception e) {
                System.out.println(">>> Exc when TMA Downloader start ... !!!");
            }             
        }
    }

    public void stopWatcherTMA() {  
        TimeAttendance.disconnect();
        running = false;
        System.out.println("\r.:: TMA Downloader service stopped ... !!!");  
    }
}
