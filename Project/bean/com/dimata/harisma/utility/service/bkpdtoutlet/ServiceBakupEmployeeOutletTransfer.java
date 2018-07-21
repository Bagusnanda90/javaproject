/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.service.bkpdtoutlet;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class ServiceBakupEmployeeOutletTransfer {

    private static ServiceBakupEmployeeOutletTransfer serviceBakupEmployeeOutletTransfer = null;
    private static BakupEmployeeOutletTransferData bakupEmployeeOutletTransferData = null;
    private static boolean running = false;

    private ServiceBakupEmployeeOutletTransfer(){
    }
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
//        if(bakupEmployeeOutletTransferData!=null){
//         bakupEmployeeOutletTransferData.getProgressBar(jProgressBar);
//        }else{
//            jProgressBar.setValue(0);
//        }
//     }

    public void startService(Date dtStart,Date dtFinish,String codeLocation, boolean cbxOutlet,boolean cbxSchedule,boolean cbxScheduleSymbol,boolean cbxKadivMapping,String paramSch,String locationSave) {
        if (bakupEmployeeOutletTransferData != null) {
            
            if (dtStart!=null && dtFinish!=null && dtStart.getTime() > dtFinish.getTime()) {
                        Date tempFromDate = dtStart;
                        Date tempToDate = dtFinish;
                        dtStart = tempToDate;
                        dtFinish = tempFromDate;
            }
            bakupEmployeeOutletTransferData.setDtStart(dtStart);
            bakupEmployeeOutletTransferData.setDtFinish(dtFinish);
            bakupEmployeeOutletTransferData.setCodeLocationMesin(codeLocation);
            bakupEmployeeOutletTransferData.setCbxOutlet(cbxOutlet);
            bakupEmployeeOutletTransferData.setCbxSchedule(cbxSchedule);
            bakupEmployeeOutletTransferData.setCbxScheduleSymbol(cbxScheduleSymbol); 
            
            bakupEmployeeOutletTransferData.setCbxKadivMapping(cbxKadivMapping);
            bakupEmployeeOutletTransferData.setInputParam(paramSch);
            bakupEmployeeOutletTransferData.setLocationSave(locationSave);
            bakupEmployeeOutletTransferData.setRunning(true);
            Runnable task = (Runnable) bakupEmployeeOutletTransferData;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        } else {
            bakupEmployeeOutletTransferData = new BakupEmployeeOutletTransferData();
            //employeeTransferData.setStartDate(getDate());
                        if (dtStart!=null && dtFinish!=null && dtStart.getTime() > dtFinish.getTime()) {
                        Date tempFromDate = dtStart;
                        Date tempToDate = dtFinish;
                        dtStart = tempToDate;
                        dtFinish = tempFromDate;
            }
            bakupEmployeeOutletTransferData.setDtStart(dtStart);
            bakupEmployeeOutletTransferData.setDtFinish(dtFinish);
            bakupEmployeeOutletTransferData.setCodeLocationMesin(codeLocation);
            bakupEmployeeOutletTransferData.setCbxOutlet(cbxOutlet);
            bakupEmployeeOutletTransferData.setCbxSchedule(cbxSchedule);
            bakupEmployeeOutletTransferData.setCbxScheduleSymbol(cbxScheduleSymbol);
               bakupEmployeeOutletTransferData.setCbxKadivMapping(cbxKadivMapping);
                 bakupEmployeeOutletTransferData.setInputParam(paramSch);
                  bakupEmployeeOutletTransferData.setLocationSave(locationSave);
            bakupEmployeeOutletTransferData.setRunning(true);
            Runnable task = (Runnable) bakupEmployeeOutletTransferData;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        }
    }

    public boolean getStatus() {
        if (bakupEmployeeOutletTransferData != null) {
            return bakupEmployeeOutletTransferData.isRunning();

        } else {
            return false;
        }
    }

    public void stopService() {
        if (bakupEmployeeOutletTransferData != null) {
            bakupEmployeeOutletTransferData.setRunning(false);
        }
        System.out.println(".................... Service data stoped ....................");
    }

    public static ServiceBakupEmployeeOutletTransfer getSServiceDataTransfer() {
        if (serviceBakupEmployeeOutletTransfer != null) {
            return serviceBakupEmployeeOutletTransfer;
        } else {
            return serviceBakupEmployeeOutletTransfer = new ServiceBakupEmployeeOutletTransfer();
        }

    }

    public static ServiceBakupEmployeeOutletTransfer getInstance(boolean withAssistant) {
        if (serviceBakupEmployeeOutletTransfer == null) {
            serviceBakupEmployeeOutletTransfer = new ServiceBakupEmployeeOutletTransfer();
            if (withAssistant) {
                serviceBakupEmployeeOutletTransfer = new ServiceBakupEmployeeOutletTransfer();
                serviceBakupEmployeeOutletTransfer.setRunning(false);
            } else {
                serviceBakupEmployeeOutletTransfer = null;

            }
        }
        return serviceBakupEmployeeOutletTransfer;
    }

    public String getMessageTransferEmployee() {
        if (bakupEmployeeOutletTransferData != null) {
            return bakupEmployeeOutletTransferData.getMessage();
            // return assistant.getMessage();
        }
        return "";
    }
     public int getProgressSize(){
        if(bakupEmployeeOutletTransferData!=null){
            return bakupEmployeeOutletTransferData.getProgressSize();
        }
        return 0;
    }
}
