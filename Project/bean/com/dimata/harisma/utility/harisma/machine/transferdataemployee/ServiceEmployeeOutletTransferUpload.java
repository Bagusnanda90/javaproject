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
public class ServiceEmployeeOutletTransferUpload {

    private static ServiceEmployeeOutletTransferUpload serviceEmployeeOutletTransferUpload = null;
    private static EmployeeOutletTransferUploadData employeeOutletTransferUploadData = null;
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

    public void startService(Date dtStart,Date dtFinish,String codeLocation,javax.swing.JProgressBar jProgressBar,javax.swing.JLabel msgDownloadInformation,javax.swing.JButton btnRunDtEmployee) {
        if (employeeOutletTransferUploadData != null) {
            
            if (dtStart!=null && dtFinish!=null && dtStart.getTime() > dtFinish.getTime()) {
                        Date tempFromDate = dtStart;
                        Date tempToDate = dtFinish;
                        dtStart = tempToDate;
                        dtFinish = tempFromDate;
            }
            employeeOutletTransferUploadData.setDtStart(dtStart);
            employeeOutletTransferUploadData.setDtFinish(dtFinish);
            employeeOutletTransferUploadData.setCodeLocationMesin(codeLocation);
            employeeOutletTransferUploadData.setjProgressBar(jProgressBar);
            employeeOutletTransferUploadData.setMessageLabel(msgDownloadInformation);
            employeeOutletTransferUploadData.setButton(btnRunDtEmployee);
            employeeOutletTransferUploadData.setRunning(true);
            Runnable task = (Runnable) employeeOutletTransferUploadData;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        } else {
            employeeOutletTransferUploadData = new EmployeeOutletTransferUploadData();
            //employeeTransferData.setStartDate(getDate());
                        if (dtStart!=null && dtFinish!=null && dtStart.getTime() > dtFinish.getTime()) {
                        Date tempFromDate = dtStart;
                        Date tempToDate = dtFinish;
                        dtStart = tempToDate;
                        dtFinish = tempFromDate;
            }
            employeeOutletTransferUploadData.setDtStart(dtStart);
            employeeOutletTransferUploadData.setDtFinish(dtFinish);
            employeeOutletTransferUploadData.setCodeLocationMesin(codeLocation);
            employeeOutletTransferUploadData.setjProgressBar(jProgressBar);
            employeeOutletTransferUploadData.setMessageLabel(msgDownloadInformation);
            employeeOutletTransferUploadData.setButton(btnRunDtEmployee);
            employeeOutletTransferUploadData.setRunning(true); 
            Runnable task = (Runnable) employeeOutletTransferUploadData;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        }
    }

    public boolean getStatus() {
        if (employeeOutletTransferUploadData != null) {
            return employeeOutletTransferUploadData.isRunning();

        } else {
            return false;
        }
    }

    public void stopService() {
        if (employeeOutletTransferUploadData != null) {
            employeeOutletTransferUploadData.setRunning(false);
        }
        System.out.println(".................... Service data stoped ....................");
    }

    public static ServiceEmployeeOutletTransferUpload getSServiceDataTransfer() {
        if (serviceEmployeeOutletTransferUpload != null) {
            return serviceEmployeeOutletTransferUpload;
        } else {
            return serviceEmployeeOutletTransferUpload = new ServiceEmployeeOutletTransferUpload();
        }

    }

    public static ServiceEmployeeOutletTransferUpload getInstance(boolean withAssistant) {
        if (serviceEmployeeOutletTransferUpload == null) {
            serviceEmployeeOutletTransferUpload = new ServiceEmployeeOutletTransferUpload();
            if (withAssistant) {
                serviceEmployeeOutletTransferUpload = new ServiceEmployeeOutletTransferUpload();
                serviceEmployeeOutletTransferUpload.setRunning(false);
            } else {
                serviceEmployeeOutletTransferUpload = null;

            }
        }
        return serviceEmployeeOutletTransferUpload;
    }

    public String getMessageTransferEmployee() {
        if (employeeOutletTransferUploadData != null) {
            return employeeOutletTransferUploadData.getMessage();
            // return assistant.getMessage();
        }
        return "";
    }
     public int getProgressSize(){
        if(employeeOutletTransferUploadData!=null){
            return employeeOutletTransferUploadData.getProgressSize();
        }
        return 0;
    }
}
