/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine;

import com.dimata.harisma.utility.notification.ProcessNotification;

/**
 *
 * @author GUSWIK
 */
public class NotificationService {

    private static NotificationService man = null;
    //public static boolean running = false;
    private static ProcessNotification assistant =null;
    /**
     * @return the txtProcessList
     */
    public static ProcessNotification getAssistant(){
        if(assistant !=null){
            return assistant;
        }
        else{
            return assistant = new ProcessNotification();
        }
        
    }
    private NotificationService() {
    }

    public static NotificationService getInstance(boolean withAssistant) {
        if (man == null) {
            man = new NotificationService();            
            if(withAssistant){
                assistant = new ProcessNotification();
                assistant.setRunning(false);                
            } else{
                assistant=null;    
            }
        }
        return man;
    }

    

    
    public void startTransfer(java.util.Date fromDate,java.util.Date toDate) {
       //  public void startTransfer(long oidDepartement, String employeeName,java.util.Date fromDate,java.util.Date toDate ,long oidSection) {
        if(assistant!=null){
            assistant.setFromDate(fromDate);
            assistant.setToDate(toDate);
            assistant.setRunning(true);
            Runnable task = (Runnable) assistant;                                      
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();                        
        }else {
            assistant= new ProcessNotification();
            assistant.setFromDate(fromDate);
            assistant.setToDate(toDate);
            assistant.setRunning(true);
            Runnable task = (Runnable) assistant;                                      
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();                        
    }
       
        //running = true;
    }
    

    public void stopTransfer(int i) {

        if(assistant!=null){
            assistant.setRunning(false);
            //running = false; 
        }
    }

    public void stopTransfer() {
        if(assistant!=null){
            assistant.setRunning(false);
            //running = false; 
        }
       // running = false;
    }
    
    public int getTotalRecordAssistant() {                
        if (assistant!=null) {            
            return assistant.getRecordSize();
        } else {
            return 0;
        }
    }

    public int getProcentTransferAssistant() {
            if (assistant!=null) {                        
            return assistant.getProgressSize();
        }
        return 0;
    }

   //update by satrya 2012-09-21
    /**
     * Keterangan : untuk mengetahui total record prosess absensi
     * @return  0
     */
        public int getTotalRecordAssistantAbsence() {                
        if (assistant!=null) {            
            return assistant.getRecordSizeAbsence();
        } else {
            return 0;
        }
    }
/**
 * Keterangan : untuk mengetahui proses transfer absensi
 * @return 
 */
    public int getProcentTransferAssistantAbsence() {
            if (assistant!=null) {                        
            return assistant.getProgressSizeAbsence();
        }
        return 0;
    }
    public String getTransferAssistantManualMessage() {
            if (assistant!=null) {                        
            return assistant.getMessageCalculation();
            // return assistant.getMessage();
        }
        return "";
    }
    //update by satrya 2012-09-04
     public String getCheckProcessAbsenceMessage() {
            if (assistant!=null) {                        
            return assistant.getMessageProsessAbsence();
            // return assistant.getMessage();
        }
        return "";
    }
     //update by satrya 2012-09-04
     public String getCheckProcessLatenessMessage() {
            if (assistant!=null) {                        
            return assistant.getMessageProsessLateness();
            // return assistant.getMessage();
        }
        return "";
    }

    
    public boolean isRunningAssistant() {
        if (assistant!=null) {                    
           return  assistant.isRunning(); 
            
        }
        else{
             return false;
        }
       
       
    }
     public boolean getStatus() {
          if (assistant!=null) {                    
           return  assistant.isRunning(); 
            
        }
        else{
             return false;
        }
       
    }
     
}