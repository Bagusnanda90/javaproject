/*
 * ServiceManagerTMA.java
 * @author  karya
 * Created on December 12, 2002, 7:24 PM
 */

package com.dimata.harisma.utility.service.tma;

import com.dimata.harisma.utility.service.tma.WatcherCardnetic;

public class ServiceManagerCardnetic { 

    public static boolean running = false;    
    
    public boolean getStatus() {
        return running;
    }
    
    public ServiceManagerCardnetic() {
    }

    public void startWatcherCardnetic() 
    {
        if (!running) {
            System.out.println("\r.:: Cardnetic Downloader service started ... !!!");   
            try {
                running = true;
                Thread thr = new Thread(new WatcherCardnetic());     
                thr.setDaemon(false);                
                thr.start();
            } catch (Exception e) {
                System.out.println(">>> Exc when Cardnetic Downloader start ... !!!");
            }             
        }
    }

    public void stopWatcherCardnetic() {  
        running = false;
        System.out.println("\r.:: Cardnetic Downloader service stopped ... !!!");  
    }
}
