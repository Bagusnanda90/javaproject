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
public class ServiceEmployeeOutletTransferAutoD {

    private static ServiceEmployeeOutletTransferAutoD serviceEmployeeOutletTransferAutoD = null;
    private static EmployeeOutletTransferDataAutoD employeeOutletTransferDataAutoD = null;
    
   // private static EmployeeOutletTransferDataAuto employeeOutletTransferDataAutoDAuto = null;
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
    
//    public static void setProgressBar(javax.swing.JProgressBar jProgressBar){
//        if(employeeOutletTransferDataAutoD!=null){
//         employeeOutletTransferDataAutoD.getProgressBar(jProgressBar);
//        }else{
//            jProgressBar.setValue(0);
//        }
//     }

    //public void startService(Date dtStart,Date dtFinish,String codeLocation, boolean cbxOutlet,boolean cbxSchedule,boolean cbxScheduleSymbol,boolean cbxKadivMapping,String paramSch,javax.swing.JProgressBar jProgressBar,javax.swing.JLabel msgDownloadInformation,javax.swing.JButton btnRunDtEmployee) {
    public void startService(Date dtStart,Date dtFinish,String codeLocation, boolean cbxOutlet,boolean cbxSchedule,boolean cbxScheduleSymbol,boolean cbxKadivMapping,String paramSch,javax.swing.JProgressBar jProgressBar) {
    
    if (employeeOutletTransferDataAutoD != null) {
            
            if (dtStart!=null && dtFinish!=null && dtStart.getTime() > dtFinish.getTime()) {
                        Date tempFromDate = dtStart;
                        Date tempToDate = dtFinish;
                        dtStart = tempToDate;
                        dtFinish = tempFromDate;
            }
            employeeOutletTransferDataAutoD.setDtStart(dtStart);
            employeeOutletTransferDataAutoD.setDtFinish(dtFinish);
            employeeOutletTransferDataAutoD.setCodeLocationMesin(codeLocation);
            employeeOutletTransferDataAutoD.setCbxOutlet(cbxOutlet);
            employeeOutletTransferDataAutoD.setCbxSchedule(cbxSchedule);
            employeeOutletTransferDataAutoD.setCbxScheduleSymbol(cbxScheduleSymbol); 
            employeeOutletTransferDataAutoD.setjProgressBar(jProgressBar);
           // employeeOutletTransferDataAutoD.setMessageLabel(msgDownloadInformation);
           // employeeOutletTransferDataAutoD.setButton(btnRunDtEmployee);
            employeeOutletTransferDataAutoD.setCbxKadivMapping(cbxKadivMapping);
            employeeOutletTransferDataAutoD.setInputParam(paramSch);
            employeeOutletTransferDataAutoD.setRunning(true);
            Runnable task = (Runnable) employeeOutletTransferDataAutoD;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        } else {
            employeeOutletTransferDataAutoD = new EmployeeOutletTransferDataAutoD();
            //employeeTransferData.setStartDate(getDate());
                        if (dtStart!=null && dtFinish!=null && dtStart.getTime() > dtFinish.getTime()) {
                        Date tempFromDate = dtStart;
                        Date tempToDate = dtFinish;
                        dtStart = tempToDate;
                        dtFinish = tempFromDate;
            }
            employeeOutletTransferDataAutoD.setDtStart(dtStart);
            employeeOutletTransferDataAutoD.setDtFinish(dtFinish);
            employeeOutletTransferDataAutoD.setCodeLocationMesin(codeLocation);
            employeeOutletTransferDataAutoD.setCbxOutlet(cbxOutlet);
            employeeOutletTransferDataAutoD.setCbxSchedule(cbxSchedule);
            employeeOutletTransferDataAutoD.setCbxScheduleSymbol(cbxScheduleSymbol);
            employeeOutletTransferDataAutoD.setjProgressBar(jProgressBar); 
           // employeeOutletTransferDataAutoD.setMessageLabel(msgDownloadInformation);
           // employeeOutletTransferDataAutoD.setButton(btnRunDtEmployee);
            employeeOutletTransferDataAutoD.setCbxKadivMapping(cbxKadivMapping);
            employeeOutletTransferDataAutoD.setInputParam(paramSch);
            employeeOutletTransferDataAutoD.setRunning(true);
            Runnable task = (Runnable) employeeOutletTransferDataAutoD;
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();
        }
    }

    
    
    
    
    public boolean getStatus() {
        if (employeeOutletTransferDataAutoD != null) {
            return employeeOutletTransferDataAutoD.isRunning();

        } else {
            return false;
        }
    }

    public void stopService() {
        if (employeeOutletTransferDataAutoD != null) {
            employeeOutletTransferDataAutoD.setRunning(false);
        }
        System.out.println(".................... Service data stoped ....................");
    }

    public static ServiceEmployeeOutletTransferAutoD getSServiceDataTransfer() {
        if (serviceEmployeeOutletTransferAutoD != null) {
            return serviceEmployeeOutletTransferAutoD;
        } else {
            return serviceEmployeeOutletTransferAutoD = new ServiceEmployeeOutletTransferAutoD();
        }

    }

    public static ServiceEmployeeOutletTransferAutoD getInstance(boolean withAssistant) {
        if (serviceEmployeeOutletTransferAutoD == null) {
            serviceEmployeeOutletTransferAutoD = new ServiceEmployeeOutletTransferAutoD();
            if (withAssistant) {
                serviceEmployeeOutletTransferAutoD = new ServiceEmployeeOutletTransferAutoD();
                serviceEmployeeOutletTransferAutoD.setRunning(false);
            } else {
                serviceEmployeeOutletTransferAutoD = null;

            }
        }
        return serviceEmployeeOutletTransferAutoD;
    }

    public String getMessageTransferEmployee() {
        if (employeeOutletTransferDataAutoD != null) {
            return employeeOutletTransferDataAutoD.getMessage();
            // return assistant.getMessage();
        }
        return "";
    }
     public int getProgressSize(){
        if(employeeOutletTransferDataAutoD!=null){
            return employeeOutletTransferDataAutoD.getProgressSize();
        }
        return 0;
    }
}
