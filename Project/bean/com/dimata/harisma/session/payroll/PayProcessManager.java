/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

import java.util.Vector;





/**
 *
 * @author ktanjana
 */
public class PayProcessManager {
    private static PayProcessManager man = null;
    private static boolean running = false;
    private static PayProcess process =null;
    private static long periodId=0;
    private static String levelCode="";
    private static long oidDivision = 0;
    private static long oidDepartment=0;
    private static Vector levelSel;
    private static long payGroupId=0;
    private static long payrollGroupId=0;
    

    /**
     * @return the levelCode
     */
    public static String getLevelCode() {
        return levelCode;
    }

    /**
     * @return the process
     */
    public static PayProcess getProcess() {
        return process;
    }
    
    /**
     * @return the payGroupId
     */
    public static long getPayGroupId() {
        return payGroupId;
    }

    /**
     * @return the payrollGroupId
     */
    public static long getPayrollGroupId() {
        return payrollGroupId;
    }

    /**
     * @param aPayrollGroupId the payrollGroupId to set
     */
    public static void setPayrollGroupId(long aPayrollGroupId) {
        payrollGroupId = aPayrollGroupId;
    }

    
    private PayProcessManager(){
        
    }
   
    public static PayProcessManager getInstance(long periodIdX, String levelCodeX, long oidDepartmentX, Vector levelSelX , long payGroupIdX, long payrollGroupId){
        return getInstance(periodIdX, levelCodeX, 0,  oidDepartmentX, levelSelX , payGroupIdX, payrollGroupId);
    }
    
    
    public static PayProcessManager getInstance(long periodIdX, String levelCodeX,long oidDivisionX, long oidDepartmentX, Vector levelSelX , long payGroupIdX, long payrollGroupId){
        periodId=periodIdX;
        levelCode=levelCodeX;
        oidDivision=oidDivisionX;
        oidDepartment=oidDepartmentX;
        levelSel= levelSelX;
        payGroupId =payGroupIdX;
        payrollGroupId =payrollGroupId;
        if(man==null){
            man= new PayProcessManager();
        }
        if(getProcess()==null){
            process = new PayProcess(periodId, getLevelCode(), oidDivision, oidDepartment, levelSel, payGroupId, payrollGroupId );
            getProcess().setRunning(false);
        }else {
            if(periodId!=0){            
                getProcess().setPayProcess(periodId, getLevelCode(), oidDivision, oidDepartment, levelSel, payGroupId,payrollGroupId );
            }
        }            

        return man;
    }
    
    public void startPayrollProcess(){
        if(getProcess()!=null){
             getProcess().setSumMessage("");
             getProcess().setRunning(true);
             Thread worker = new Thread(getProcess());
             worker.setDaemon(false);
             worker.start();             
        }
    }
    
    public static void stopPayrollProcess(){
        if(getProcess()!=null){
             getProcess().setRunning(false);
        }
    }
    
    public static String getMessage(){
        if(getProcess()!=null){
             return getProcess().getMessage();
        }
        return "No Message";
    }
    
    public static boolean isRunning(){
        if(getProcess()!=null){
            return getProcess().isRunning();                    
        }
        return false;
    }

    public static String getDepartmentName(){
        if(getProcess()!=null && getProcess().getDepartment()!=null){            
            return getProcess().getDepartment().getDepartment();                    
        }
        return "-";
    }

    public static long getDepartmentId(){
        if(getProcess()!=null && getProcess().getDepartment()!=null){            
            return getProcess().getDepartment().getOID();                    
        }
        return 0;
    }
    
    public static String getDivisionName(){
        if(getProcess()!=null && getProcess().getDivision()!=null){            
            return getProcess().getDivision().getDivision();                    
        }
        return "-";
    }

    public static long getDivisionId(){
        if(getProcess()!=null && getProcess().getDivision()!=null){            
            return getProcess().getDivision().getOID();                    
        }
        return 0;
    }
    
    
    public static String getPeriodName(){
        if(getProcess()!=null && getProcess().getPayPeriod()!=null){            
            //  if(process!=null && process.getPeriod()!=null){            
            return getProcess().getPayPeriod().getPeriod();
        }
        return "-";
    }

    public static long getPeriodId(){
        if(getProcess()!=null && getProcess().getPayPeriod()!=null){            
            return getProcess().getPayPeriod().getOID();
        }
        return 0;
    }    
    
    public static Vector getLevels(){
        if(getProcess()!=null && getProcess().getLevelSel()!=null){            
            return getProcess().getLevelSel();
        }
        return new Vector();
    }

    public static String getSumMessage() {
        if(getProcess()!=null){            
            return getProcess().getSumMessage();
        }
        return "Process is not set";
    }
    
}
