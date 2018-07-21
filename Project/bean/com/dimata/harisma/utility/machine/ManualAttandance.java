/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine;
import java.sql.*;
import java.util.Vector;

/**
 *
 * @author ktanjana
 */
public class ManualAttandance {

    private static ManualAttandance man = null;
    //public static boolean running = false;
    private static TransManagerAssistant assistant =null;
    /**
     * @return the txtProcessList
     */
    public static TransManagerAssistant getAssistant(){
        if(assistant !=null){
            return assistant;
        }
        else{
            return assistant = new TransManagerAssistant();
        }
        
    }
    private ManualAttandance() {
    }

    public static ManualAttandance getInstance(boolean withAssistant) {
        if (man == null) {
            man = new ManualAttandance();            
            if(withAssistant){
                assistant = new TransManagerAssistant();
                assistant.setRunning(false);                
            } else{
                assistant=null;
               
            }
        }
        return man;
    }

    

    
    public void startTransfer(long oidDepartement, String employeeName,java.util.Date fromDate,java.util.Date toDate ,long oidSection, String payrolNum, String EmpCat,long oidDivision) {
       //  public void startTransfer(long oidDepartement, String employeeName,java.util.Date fromDate,java.util.Date toDate ,long oidSection) {
        if(assistant!=null){
            assistant.setOidDivision(oidDivision);
            assistant.setOidDepartement(oidDepartement);
            assistant.setFullName(employeeName);
            assistant.setFromDate(fromDate);
            assistant.setToDate(toDate);
            assistant.setOidsection(oidSection);
            assistant.setPayrolNum(payrolNum);
            assistant.setEmpCat(EmpCat);
            assistant.setRunning(true);
            Runnable task = (Runnable) assistant;                                      
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();                        
        }else {
            assistant= new TransManagerAssistant();
            assistant.setOidDivision(oidDivision);
            assistant.setOidDepartement(oidDepartement);
            assistant.setFullName(employeeName);
            assistant.setFromDate(fromDate);
            assistant.setToDate(toDate);
            assistant.setOidsection(oidSection);
            assistant.setPayrolNum(payrolNum);
            assistant.setEmpCat(EmpCat);
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
