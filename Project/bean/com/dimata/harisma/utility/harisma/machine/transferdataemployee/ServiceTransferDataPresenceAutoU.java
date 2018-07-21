/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class ServiceTransferDataPresenceAutoU {

    private static ServiceTransferDataPresenceAutoU serviceTransferDataPresenceAutoU = null;
    private static TransferDataPresenceAutoU transferDataPresenceAutoU = null;
    private static Vector<javax.swing.JProgressBar> vJProgressBar = new Vector();
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

    public static void setProgressBar(int idx, javax.swing.JProgressBar jProgressBar) {
        if (vJProgressBar == null) {
            vJProgressBar = new Vector();
        }
        if (idx < 0) {
            return;
        }
        if (idx >= vJProgressBar.size()) {
            for (int i = vJProgressBar.size(); i <= idx; i++) {
                vJProgressBar.add(null);
            }
            vJProgressBar.set(idx, jProgressBar);
        }
    }

    public void startService(Date dtStart, Date dtFinish, String codeLocation, javax.swing.JProgressBar jProgressBar) {
        if (transferDataPresenceAutoU != null) {

            if (dtStart != null && dtFinish != null && dtStart.getTime() > dtFinish.getTime()) {
                Date tempFromDate = dtStart;
                Date tempToDate = dtFinish;
                dtStart = tempToDate;
                dtFinish = tempFromDate;
            }
            transferDataPresenceAutoU.setDtStart(dtStart);
            transferDataPresenceAutoU.setDtFinish(dtFinish);
            transferDataPresenceAutoU.setCodeLocationMesin(codeLocation);
            transferDataPresenceAutoU.setProgressBar(jProgressBar);
            //transferDataPresenceAutoU.setButton(btnRunDtEmployee);
            //transferDataPresenceAutoU.setMessageLabel(msgDownloadData);
            transferDataPresenceAutoU.setRunning(true);
            Runnable task = (Runnable) transferDataPresenceAutoU;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        } else {
            transferDataPresenceAutoU = new TransferDataPresenceAutoU();
            //employeeTransferData.setStartDate(getDate());
            if (dtStart != null && dtFinish != null && dtStart.getTime() > dtFinish.getTime()) {
                Date tempFromDate = dtStart;
                Date tempToDate = dtFinish;
                dtStart = tempToDate;
                dtFinish = tempFromDate;
            }
            transferDataPresenceAutoU.setDtStart(dtStart);
            transferDataPresenceAutoU.setDtFinish(dtFinish);
            transferDataPresenceAutoU.setCodeLocationMesin(codeLocation);
            transferDataPresenceAutoU.setProgressBar(jProgressBar);
            //transferDataPresenceAutoU.setButton(btnRunDtEmployee);
            //transferDataPresenceAutoU.setMessageLabel(msgDownloadData);
            transferDataPresenceAutoU.setRunning(true);
            Runnable task = (Runnable) transferDataPresenceAutoU;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        }
    }

    public boolean getStatus() {
        if (transferDataPresenceAutoU != null) {
            return transferDataPresenceAutoU.isRunning();

        } else {
            return false;
        }
    }

    public void stopService() {
        if (transferDataPresenceAutoU != null) {
            transferDataPresenceAutoU.setRunning(false);
        }
        System.out.println(".................... Service data stoped ....................");
    }

    public static ServiceTransferDataPresenceAutoU getSServiceDataTransfer() {
        if (serviceTransferDataPresenceAutoU != null) {
            return serviceTransferDataPresenceAutoU;
        } else {
            return serviceTransferDataPresenceAutoU = new ServiceTransferDataPresenceAutoU();
        }

    }

    public static ServiceTransferDataPresenceAutoU getInstance(boolean withAssistant) {
        if (serviceTransferDataPresenceAutoU == null) {
            serviceTransferDataPresenceAutoU = new ServiceTransferDataPresenceAutoU();
            if (withAssistant) {
                serviceTransferDataPresenceAutoU = new ServiceTransferDataPresenceAutoU();
                serviceTransferDataPresenceAutoU.setRunning(false);
            } else {
                serviceTransferDataPresenceAutoU = null;

            }
        }
        return serviceTransferDataPresenceAutoU;
    }

    public String getMessageTransferEmployee() {
        if (transferDataPresenceAutoU != null) {
            return transferDataPresenceAutoU.getMessage();
            // return assistant.getMessage();
        }
        return "";
    }

    public int getProgressSize() {
        if (transferDataPresenceAutoU != null) {
            return transferDataPresenceAutoU.getProgressSize();
        }
        return 0;
    }
}
