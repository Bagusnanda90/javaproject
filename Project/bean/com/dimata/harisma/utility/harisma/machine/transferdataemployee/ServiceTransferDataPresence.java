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
public class ServiceTransferDataPresence {

    private static ServiceTransferDataPresence serviceTransferDataPresence = null;
    private static TransferDataPresence transferDataPresence = null;
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

    public void startService(Date dtStart, Date dtFinish, String codeLocation, javax.swing.JProgressBar jProgressBar, javax.swing.JLabel msgDownloadData, javax.swing.JButton btnRunDtEmployee) {
        if (transferDataPresence != null) {

            if (dtStart != null && dtFinish != null && dtStart.getTime() > dtFinish.getTime()) {
                Date tempFromDate = dtStart;
                Date tempToDate = dtFinish;
                dtStart = tempToDate;
                dtFinish = tempFromDate;
            }
            transferDataPresence.setDtStart(dtStart);
            transferDataPresence.setDtFinish(dtFinish);
            transferDataPresence.setCodeLocationMesin(codeLocation);
            transferDataPresence.setProgressBar(jProgressBar);
            transferDataPresence.setButton(btnRunDtEmployee);
            transferDataPresence.setMessageLabel(msgDownloadData);
            transferDataPresence.setRunning(true);
            Runnable task = (Runnable) transferDataPresence;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        } else {
            transferDataPresence = new TransferDataPresence();
            //employeeTransferData.setStartDate(getDate());
            if (dtStart != null && dtFinish != null && dtStart.getTime() > dtFinish.getTime()) {
                Date tempFromDate = dtStart;
                Date tempToDate = dtFinish;
                dtStart = tempToDate;
                dtFinish = tempFromDate;
            }
            transferDataPresence.setDtStart(dtStart);
            transferDataPresence.setDtFinish(dtFinish);
            transferDataPresence.setCodeLocationMesin(codeLocation);
            transferDataPresence.setProgressBar(jProgressBar);
            transferDataPresence.setButton(btnRunDtEmployee);
            transferDataPresence.setMessageLabel(msgDownloadData);
            transferDataPresence.setRunning(true);
            Runnable task = (Runnable) transferDataPresence;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        }
    }

    public boolean getStatus() {
        if (transferDataPresence != null) {
            return transferDataPresence.isRunning();

        } else {
            return false;
        }
    }

    public void stopService() {
        if (transferDataPresence != null) {
            transferDataPresence.setRunning(false);
        }
        System.out.println(".................... Service data stoped ....................");
    }

    public static ServiceTransferDataPresence getSServiceDataTransfer() {
        if (serviceTransferDataPresence != null) {
            return serviceTransferDataPresence;
        } else {
            return serviceTransferDataPresence = new ServiceTransferDataPresence();
        }

    }

    public static ServiceTransferDataPresence getInstance(boolean withAssistant) {
        if (serviceTransferDataPresence == null) {
            serviceTransferDataPresence = new ServiceTransferDataPresence();
            if (withAssistant) {
                serviceTransferDataPresence = new ServiceTransferDataPresence();
                serviceTransferDataPresence.setRunning(false);
            } else {
                serviceTransferDataPresence = null;

            }
        }
        return serviceTransferDataPresence;
    }

    public String getMessageTransferEmployee() {
        if (transferDataPresence != null) {
            return transferDataPresence.getMessage();
            // return assistant.getMessage();
        }
        return "";
    }

    public int getProgressSize() {
        if (transferDataPresence != null) {
            return transferDataPresence.getProgressSize();
        }
        return 0;
    }
}
