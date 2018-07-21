/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class ServiceScheduleTransfer {

    private static ServiceScheduleTransfer serviceScheduleTransfer = null;
    private static ScheduleTransferData scheduleTransferData = null;
    private static boolean running = false;

    /**
     * @return the running
     */
    public static boolean isRunning() {
        return running;
    }

    /**
     * @param aRunning the running to set
     */
    public static void setRunning(boolean aRunning) {
        running = aRunning;
    }

    public void startService() {
        if (scheduleTransferData != null) {
            
            
            scheduleTransferData.setRunning(true);
            Runnable task = (Runnable) scheduleTransferData;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        } else {
            scheduleTransferData = new ScheduleTransferData();
            
           
            scheduleTransferData.setRunning(true);
            Runnable task = (Runnable) scheduleTransferData;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        }
    }

    public boolean getStatus() {
        if (scheduleTransferData != null) {
            return scheduleTransferData.isRunning();

        } else {
            return false;
        }
    }

    public void stopService() {
        if (scheduleTransferData != null) {
            scheduleTransferData.setRunning(false);
        }
        System.out.println(".................... Service data stoped ....................");
    }

    public static ServiceScheduleTransfer getSServiceDataTransfer() {
        if (serviceScheduleTransfer != null) {
            return serviceScheduleTransfer;
        } else {
            return serviceScheduleTransfer = new ServiceScheduleTransfer();
        }

    }

    public static ServiceScheduleTransfer getInstance(boolean withAssistant) {
        if (serviceScheduleTransfer == null) {
            serviceScheduleTransfer = new ServiceScheduleTransfer();
            if (withAssistant) {
                serviceScheduleTransfer = new ServiceScheduleTransfer();
                serviceScheduleTransfer.setRunning(false);
            } else {
                serviceScheduleTransfer = null;

            }
        }
        return serviceScheduleTransfer;
    }

    public String getTransferEmployee() {
        if (scheduleTransferData != null) {
            return scheduleTransferData.getMessage();
            // return assistant.getMessage();
        }
        return "";
    }
}
