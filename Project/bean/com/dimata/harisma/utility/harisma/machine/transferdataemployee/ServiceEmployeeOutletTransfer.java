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
public class ServiceEmployeeOutletTransfer {

    private static ServiceEmployeeOutletTransfer serviceEmployeeOutletTransfer = null;
    private static EmployeeOutletTransferData employeeOutletTransferData = null;
    
   
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
    
//    public static void setProgressBar(javax.swing.JProgressBar jProgressBar){
//        if(employeeOutletTransferData!=null){
//         employeeOutletTransferData.getProgressBar(jProgressBar);
//        }else{
//            jProgressBar.setValue(0);
//        }
//     }

    public void startService(Date dtStart,Date dtFinish,String codeLocation, boolean cbxOutlet,boolean cbxSchedule,boolean cbxScheduleSymbol,boolean cbxKadivMapping,String paramSch,javax.swing.JProgressBar jProgressBar,javax.swing.JLabel msgDownloadInformation,javax.swing.JButton btnRunDtEmployee) {
        if (employeeOutletTransferData != null) {
            
            if (dtStart!=null && dtFinish!=null && dtStart.getTime() > dtFinish.getTime()) {
                        Date tempFromDate = dtStart;
                        Date tempToDate = dtFinish;
                        dtStart = tempToDate;
                        dtFinish = tempFromDate;
            }
            employeeOutletTransferData.setDtStart(dtStart);
            employeeOutletTransferData.setDtFinish(dtFinish);
            employeeOutletTransferData.setCodeLocationMesin(codeLocation);
            employeeOutletTransferData.setCbxOutlet(cbxOutlet);
            employeeOutletTransferData.setCbxSchedule(cbxSchedule);
            employeeOutletTransferData.setCbxScheduleSymbol(cbxScheduleSymbol); 
            employeeOutletTransferData.setjProgressBar(jProgressBar);
            employeeOutletTransferData.setMessageLabel(msgDownloadInformation);
            employeeOutletTransferData.setButton(btnRunDtEmployee);
            employeeOutletTransferData.setCbxKadivMapping(cbxKadivMapping);
            employeeOutletTransferData.setInputParam(paramSch);
            employeeOutletTransferData.setRunning(true);
            Runnable task = (Runnable) employeeOutletTransferData;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        } else {
            employeeOutletTransferData = new EmployeeOutletTransferData();
            //employeeTransferData.setStartDate(getDate());
                        if (dtStart!=null && dtFinish!=null && dtStart.getTime() > dtFinish.getTime()) {
                        Date tempFromDate = dtStart;
                        Date tempToDate = dtFinish;
                        dtStart = tempToDate;
                        dtFinish = tempFromDate;
            }
            employeeOutletTransferData.setDtStart(dtStart);
            employeeOutletTransferData.setDtFinish(dtFinish);
            employeeOutletTransferData.setCodeLocationMesin(codeLocation);
            employeeOutletTransferData.setCbxOutlet(cbxOutlet);
            employeeOutletTransferData.setCbxSchedule(cbxSchedule);
            employeeOutletTransferData.setCbxScheduleSymbol(cbxScheduleSymbol);
            employeeOutletTransferData.setjProgressBar(jProgressBar); 
            employeeOutletTransferData.setMessageLabel(msgDownloadInformation);
            employeeOutletTransferData.setButton(btnRunDtEmployee);
            employeeOutletTransferData.setCbxKadivMapping(cbxKadivMapping);
            employeeOutletTransferData.setInputParam(paramSch);
            employeeOutletTransferData.setRunning(true);
            Runnable task = (Runnable) employeeOutletTransferData;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        }
    }

    public boolean getStatus() {
        if (employeeOutletTransferData != null) {
            return employeeOutletTransferData.isRunning();

        } else {
            return false;
        }
    }

    public void stopService() {
        if (employeeOutletTransferData != null) {
            employeeOutletTransferData.setRunning(false);
        }
        System.out.println(".................... Service data stoped ....................");
    }

    public static ServiceEmployeeOutletTransfer getSServiceDataTransfer() {
        if (serviceEmployeeOutletTransfer != null) {
            return serviceEmployeeOutletTransfer;
        } else {
            return serviceEmployeeOutletTransfer = new ServiceEmployeeOutletTransfer();
        }

    }

    public static ServiceEmployeeOutletTransfer getInstance(boolean withAssistant) {
        if (serviceEmployeeOutletTransfer == null) {
            serviceEmployeeOutletTransfer = new ServiceEmployeeOutletTransfer();
            if (withAssistant) {
                serviceEmployeeOutletTransfer = new ServiceEmployeeOutletTransfer();
                serviceEmployeeOutletTransfer.setRunning(false);
            } else {
                serviceEmployeeOutletTransfer = null;

            }
        }
        return serviceEmployeeOutletTransfer;
    }

    public String getMessageTransferEmployee() {
        if (employeeOutletTransferData != null) {
            return employeeOutletTransferData.getMessage();
            // return assistant.getMessage();
        }
        return "";
    }
     public int getProgressSize(){
        if(employeeOutletTransferData!=null){
            return employeeOutletTransferData.getProgressSize();
        }
        return 0;
    }
}
