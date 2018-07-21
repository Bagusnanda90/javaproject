/*
 * ServiceManagerMacine.java
 * @author  artha
 * Created on December 12, 2008, 7:24 PM
 */

package com.dimata.harisma.utility.machine.dcanteen;

import com.dimata.harisma.utility.machine.OpenPorts;


public class ServiceManagerMachineCanteen { 

    public static boolean running = false;    
    
    public boolean getStatus() {
        return running;
    }
    
    public ServiceManagerMachineCanteen() {
    }

    public void startWatcherMachine() 
    {
        if (!running) {
            System.out.println("\r.:: Machine Downloader service started ... !!!");   
            try {
                running = true;
                Thread thr = new Thread(new WatcherMachineCanteen());     
                thr.setDaemon(false);                
                thr.start();
            } catch (Exception e) {
                System.out.println(">>> Exc when Machine Downloader start ... !!!");
            }             
        }
    }

    public void stopWatcherMachine() {  
        OpenPorts.disconnect();
        running = false;
        System.out.println("\r.:: Machine Downloader service stopped ... !!!");  
    }
}
