/*
 * CanteenTMAService.java
 * @author  rusdianta
 * Created on January 17, 2005, 12:20 PM
 */

package com.dimata.harisma.utility.service.tma;

public class CanteenTMAService {
    
    public static boolean running = false;
    
    /** Creates a new instance of CanteenTMAService */
    public CanteenTMAService() {
    }
    
    public boolean getStatus() {
        return running;
    }
    
    public void startCanteenTMAWatcher() {
        if (!running) {
            System.out.println("\r.:: TMA Downloader service started ...");
            try {
                running = true;
                Thread thr = new Thread (new CanteenTMAWatcher());
                thr.setDaemon(false);
                thr.start();
            } catch (Exception e) {
                System.out.println(">>> Exception when TMA Downloader start ... !!!");
            }
        }
    }
    
    public void stopCanteenTMAWatcher() {
        TimeAttendance.disconnect();
        running = false;
        System.out.println("\r.:: TMA Downloader service stopped ... !!!");
    }
}
