/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.service.presence;

/**
 *
 * @author roy andika
 */

public class LeaveAlAutoService {

    public static boolean running = false;

    public boolean getStatus() {
        return running;
    }

    public LeaveAlAutoService() {
    }

    public void startAutoAlEntitle()
    {
        if (!running) {

            System.out.println("\r.:: Start Auto Entitle ::.");

            try {

                running = true;
                //Thread thr = new Thread(new WatcherMachine());
                //thr.setDaemon(false);
                //thr.start();

            } catch (Exception e) {
                System.out.println(".:: Exc when Start Auto Entitle ::.");
            }
        }
    }

    public void stopAutoAlEntitle() {        
        running = false;
        System.out.println("\r.:: Auto Entitle Stop ::.");
    }

}
