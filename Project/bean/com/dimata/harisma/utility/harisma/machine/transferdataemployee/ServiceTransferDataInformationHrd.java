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
public class ServiceTransferDataInformationHrd {

    private static ServiceTransferDataInformationHrd serviceTransferDataInformationHrd = null;
    private static DataInformationTransferHrd dataInformationTransferHrd = null;
    private static Vector<javax.swing.JProgressBar> vJProgressBar= new Vector();
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
    
//    public static void setProgressBar(int idx, javax.swing.JProgressBar jProgressBar){
//         if(vJProgressBar==null){
//             vJProgressBar= new Vector();
//         }
//         if(idx<0){ 
//             return ; 
//         }
//         if(idx>=vJProgressBar.size()){
//             for(int i=vJProgressBar.size();i<=idx;i++ ){
//                vJProgressBar.add(null);                 
//             }
//             vJProgressBar.set(idx, jProgressBar);
//         }
//     }

    public void startService(Date dtStart,Date dtFinish,javax.swing.JProgressBar jProgressBar,javax.swing.JLabel msgDownloadInformation,javax.swing.JButton btnRunDtEmployee) {
        if (dataInformationTransferHrd != null) {
            
            if (dtStart!=null && dtFinish!=null && dtStart.getTime() > dtFinish.getTime()) {
                        Date tempFromDate = dtStart;
                        Date tempToDate = dtFinish;
                        dtStart = tempToDate;
                        dtFinish = tempFromDate;
            }
            dataInformationTransferHrd.setDtStart(dtStart);
            dataInformationTransferHrd.setDtFinish(dtFinish);
            dataInformationTransferHrd.setProgressBar(jProgressBar);
            dataInformationTransferHrd.setButton(btnRunDtEmployee);
            dataInformationTransferHrd.setMessageInfo(msgDownloadInformation);
            dataInformationTransferHrd.setRunning(true);
            Runnable task = (Runnable) dataInformationTransferHrd;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        } else {
            dataInformationTransferHrd = new DataInformationTransferHrd();
            //employeeTransferData.setStartDate(getDate());
                        if (dtStart!=null && dtFinish!=null && dtStart.getTime() > dtFinish.getTime()) {
                        Date tempFromDate = dtStart;
                        Date tempToDate = dtFinish;
                        dtStart = tempToDate;
                        dtFinish = tempFromDate;
            }
            dataInformationTransferHrd.setDtStart(dtStart);
            dataInformationTransferHrd.setDtFinish(dtFinish);
            dataInformationTransferHrd.setProgressBar(jProgressBar);
             dataInformationTransferHrd.setButton(btnRunDtEmployee);
            dataInformationTransferHrd.setMessageInfo(msgDownloadInformation);
            dataInformationTransferHrd.setRunning(true);
            Runnable task = (Runnable) dataInformationTransferHrd;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        }
    }

    public boolean getStatus() {
        if (dataInformationTransferHrd != null) {
            return dataInformationTransferHrd.isRunning();

        } else {
            return false;
        }
    }

    public void stopService() {
        if (dataInformationTransferHrd != null) {
            dataInformationTransferHrd.setRunning(false);
        }
        System.out.println(".................... Service data stoped ....................");
    }

    public static ServiceTransferDataInformationHrd getSServiceDataTransfer() {
        if (serviceTransferDataInformationHrd != null) {
            return serviceTransferDataInformationHrd;
        } else {
            return serviceTransferDataInformationHrd = new ServiceTransferDataInformationHrd();
        }

    }

    public static ServiceTransferDataInformationHrd getInstance(boolean withAssistant) {
        if (serviceTransferDataInformationHrd == null) {
            serviceTransferDataInformationHrd = new ServiceTransferDataInformationHrd();
            if (withAssistant) {
                serviceTransferDataInformationHrd = new ServiceTransferDataInformationHrd();
                serviceTransferDataInformationHrd.setRunning(false);
            } else {
                serviceTransferDataInformationHrd = null;

            }
        }
        return serviceTransferDataInformationHrd;
    }

    public String getMessageTransferEmployee() {
        if (dataInformationTransferHrd != null) {
            return dataInformationTransferHrd.getMessage();
            // return assistant.getMessage();
        }
        return "";
    }
     public int getProgressSize(){
        if(dataInformationTransferHrd!=null){
            return dataInformationTransferHrd.getProgressSize();
        }
        return 0;
    }
}
