/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine;
import com.dimata.system.entity.PstSystemProperty;
import java.sql.*;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author ktanjana
 */
public class TransManager {

    private static TransManager man = null;
    public static boolean running = false;
    //update by satrya 2013-08-12
    private static boolean runningWithLoginApp = false;
    private static javax.swing.JTextArea jTextArea =null;
    private static Vector<String> txtProcessClass = new Vector();   //membuat classbaru dengan vector dengan interface
    private static Vector<AttTransfer_I> txtProcessList = new Vector();//
    private static Vector<javax.swing.JProgressBar> vJProgressBar= new Vector();
    private static TransManagerAssistant assistant =null;
    
    private static MesinAbsensiMessage mesinAbsensiMessage = new MesinAbsensiMessage();
    /**
     * @return the txtProcessList
     */
    public static Vector<AttTransfer_I> getTxtProcessList() {
        return txtProcessList;
    }

//    /**
//     * @return the runningWithLoginApp
//     */
//    public static boolean isRunningWithLoginApp() {
//        return runningWithLoginApp;
//    }

    /**
     * @param aRunningWithLoginApp the runningWithLoginApp to set
     * untuk set true jika ada salah satu karyawan yg login;
     * create by satrya 2013-08-12
     */
    public static void setRunningWithLoginApp(boolean aRunningWithLoginApp) {
        //running = aRunningWithLoginApp;
        
        if(assistant==null){
            assistant = new TransManagerAssistant();
            assistant.setRunning(aRunningWithLoginApp);
            Runnable task = (Runnable) assistant;                                      
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();                        
        }
        running = aRunningWithLoginApp;
        
    }

    /**
     * @return the mesinAbsensiMessage
     */
    public  MesinAbsensiMessage getMesinAbsensiMessage() {
        return mesinAbsensiMessage;
    }

    private TransManager() {
    }

    public static TransManager getInstance(boolean withAssistant) {
        if (man == null) {
            man = new TransManager();            
            if(withAssistant){
                assistant = new TransManagerAssistant();
                assistant.setRunning(false);                
            } else{
                assistant=null;
            }
        }
        return man;
    }

    public static int getTxtProcessClassSize() {
        if(txtProcessClass.size()>0){
        return txtProcessClass.size();
        }
        else
            return 0;
    }

      public static String getTxtProcessClass(int i) {
        if(txtProcessClass!=null && txtProcessClass.size()>0 && txtProcessClass.size()>i){
         return txtProcessClass.get(i);
        }
        else
            return "";
    }

    public static void addTxtProcessClass(String className, long sleepMs, String dsnName,
             String user, String password, String tableName, String employeeID, 
             String machineNumber, String checkTime, String checkType, String verifyCode, String status){
        if (className != null && className.length() > 0) {
            //check if the clsName already exist
            if (txtProcessClass!=null) {
                boolean found = false;
                for (int x = 0; x < txtProcessClass.size(); x++) {
                    if (className.compareTo(txtProcessClass.get(x))==0) {
                        found= true;
                        break;
                    }
                }
                if(!found){
                    txtProcessClass.add(className);
                }                
                initTxtProjectList(className, sleepMs, dsnName, user, password, tableName, employeeID, machineNumber, checkTime, checkType, verifyCode, status);                        
            } else {
                return;
            }
        } else {
            return;
        }
        
    }      
    
    public static void addTxtProcessClass(String className) {
        if (className != null && className.length() > 0) {
            //check if the clsName already exist
            if (txtProcessClass!=null) {
                boolean found = false;
                for (int x = 0; x < txtProcessClass.size(); x++) {
                    if (className.compareTo(txtProcessClass.get(x))==0) {
                        found= true;
                        break;
                    }
                }
                if(!found){
                    txtProcessClass.add(className);
                }
            } else {
                return;
            }
        } else {
            return;
        }
    }
        
    private static void initTxtProjectList(String className, long sleepMs, String dsnName,
             String user, String password, String tableName, String employeeID, 
             String machineNumber, String checkTime, String checkType, String verifyCode, String status){
        
               if (txtProcessClass != null && txtProcessClass.size() > 0) {
            if (getTxtProcessList() == null) {
                txtProcessList = new Vector();
            }
            for (int x = 0; x < txtProcessClass.size(); x++) {
                try {
                    Runnable task = null;
                    AttTransfer_I txProcess = null;
                    if (getTxtProcessList().size() >  x) {
                        txProcess = (AttTransfer_I) getTxtProcessList().get(x);
                    }

                    String clsNameExist = txtProcessClass.get(x);
                    if(className!=null && clsNameExist!=null && className.compareTo(clsNameExist)==0 ){
                        if (txProcess == null && !txProcess.isRunning() ) {                                                    
                            task = (Runnable) Class.forName(clsNameExist).newInstance();
                            txProcess = (AttTransfer_I) task;
                            getTxtProcessList().add(txProcess);                            
                            txProcess.setRunning(false);
                            //txProcess.setSleepMs(300);
                        } else {
                            if (!txProcess.isRunning()) {
                                txProcess.setRunning(false);
                                //task = (Runnable) txProcess;
                            }
                        }
                        txProcess.initClass(sleepMs, dsnName, user, password, tableName, employeeID, machineNumber, checkTime, checkType, verifyCode, status);
                   }
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }
         running = true;
        }
    }
    
    public void startTransfer() {
        if (txtProcessClass != null && txtProcessClass.size() > 0) {
            
 /*di hidden dlu 
  * String odbcClasses = PstSystemProperty.getValueByName("ATT_MACHINE_ODBC_CLASS");            
 Vector vClassSysProp = com.dimata.util.StringParser.parseGroup(odbcClasses);
 Hashtable attMachClass = new Hashtable(); 
 Vector vClass = new Vector();
 if(vClassSysProp !=null && vClassSysProp.size() > 0){ 
     for(int idx=0;idx<vClassSysProp.size();idx++){        
        String[] strGrup = (String[]) vClassSysProp.get(idx); 
        if(strGrup!=null && strGrup.length>0 ){
            String className = "com.dimata.harisma.utility.machine."+strGrup[0];
            String user ="";
            String pwd ="";
            String dsnName="";
            String host ="";
            String port ="";            
            if(strGrup.length>1){
                Vector vParams = com.dimata.util.StringParser.parseGroup(strGrup[1],"&","=");
                if(vParams!=null && vParams.size()>0){
                    for(int i=0;i<vParams.size();i++){
                       String[] strParam = (String[]) vParams.get(i);
                       if(strParam!=null && strParam.length>0 ){
                          String paramName  = strParam[0];
                          String paramValue = strParam.length >1 ? strParam[1]:"";
                          if(paramName!=null && paramName.equalsIgnoreCase("user")){
                            user = paramValue;  
                          } else if(paramName!=null && paramName.equalsIgnoreCase("pwd")){
                            pwd = paramValue;  
                          } else if(paramName!=null && paramName.equalsIgnoreCase("dsnName")){
                            dsnName = paramValue;  
                          }else if(paramName!=null && paramName.equalsIgnoreCase("host")){
                            host = paramValue;  
                          }else if(paramName!=null && paramName.equalsIgnoreCase("port")){
                            port = paramValue;  
                          }
                       }                       
                    }
                }
           }
           DBMachineConfig dbC = new DBMachineConfig();
           dbC.setClassName(className);
           dbC.setUser(user);
           dbC.setPwd(pwd);
           dbC.setDsn(dsnName);
           dbC.setHost(host);
           dbC.setPort(port);
           attMachClass.put(className,dbC );
           vClass.add(className);
       }
   }
 }*/            
            
            
            if (getTxtProcessList() == null) {
                txtProcessList = new Vector();
            }
            for (int x = 0; x < txtProcessClass.size(); x++) {
                try {
                    Runnable task = null;
                    AttTransfer_I txProcess = null;
                    if (getTxtProcessList().size() >  x) {
                        txProcess = (AttTransfer_I) getTxtProcessList().get(x);
                    }

                    if (txProcess == null) {
                        String clsName = txtProcessClass.get(x);
                        //AttTransfer_I attI = (AttTransfer_I) Class.forName(clsName).newInstance();
                        task = (Runnable) Class.forName(clsName).newInstance();                                                
                        txProcess = (AttTransfer_I) task;
                        
                       /* DBMachineConfig dbC = (DBMachineConfig) attMachClass.get(clsName);
                        
                        txProcess.initDBConfig(dbC);*/
                        
                        if(vJProgressBar!=null && x<vJProgressBar.size()){
                            txProcess.setProgressBar(vJProgressBar.get(x));
                        }
                        txProcess.setTextArea(jTextArea);
                        getTxtProcessList().add(txProcess);
                        //txProcess = (Att2000Transfer) task;
                        //txProcess.setSleepMs(300);
                        Thread worker = new Thread(task);
                        worker.setDaemon(false);
                        worker.start();

                    } else {
                        if (!txProcess.isRunning()) {
                            txProcess.setRunning(true);
                            task = (Runnable) txProcess;
                            Thread worker = new Thread(task);
                            worker.start();
                        }
                    }
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }         
        }
        
        if(assistant!=null){
            assistant.setRunning(true);
            Runnable task = (Runnable) assistant;                                      
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();                        
        }
        running = true;
    }

    public void startTransfer(java.util.Date dtFrom,java.util.Date dtTo) {
        if (txtProcessClass != null && txtProcessClass.size() > 0) {       
            
            if (getTxtProcessList() == null) {
                txtProcessList = new Vector();
            }
            for (int x = 0; x < txtProcessClass.size(); x++) {
                try {
                    Runnable task = null;
                    AttTransfer_I txProcess = null;
                    if (getTxtProcessList().size() >  x) {
                        txProcess = (AttTransfer_I) getTxtProcessList().get(x);
                    }

                    if (txProcess == null) {
                        String clsName = txtProcessClass.get(x);
                        //AttTransfer_I attI = (AttTransfer_I) Class.forName(clsName).newInstance();
                        task = (Runnable) Class.forName(clsName).newInstance();                                                
                        txProcess = (AttTransfer_I) task;
                        
                       /* DBMachineConfig dbC = (DBMachineConfig) attMachClass.get(clsName);
                        
                        txProcess.initDBConfig(dbC);*/
                        
                        if(vJProgressBar!=null && x<vJProgressBar.size()){
                            txProcess.setProgressBar(vJProgressBar.get(x));
                        }
                        if (dtFrom!=null && dtTo!=null){
                            try{
                                txProcess.setDateFromTo(dtFrom, dtTo);
                            } catch (Exception e){}
                        }
                        txProcess.setTextArea(jTextArea);
                        
                        getTxtProcessList().add(txProcess);
                        //txProcess = (Att2000Transfer) task;
                        //txProcess.setSleepMs(300);
                        Thread worker = new Thread(task);
                        worker.setDaemon(false);
                        worker.start();

                    } else {
                        if (!txProcess.isRunning()) {
                            txProcess.setRunning(true);
                            task = (Runnable) txProcess;
                            Thread worker = new Thread(task);
                            worker.start();
                        }
                    }
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }         
        }
        
        if(assistant!=null){
            assistant.setRunning(true);
            Runnable task = (Runnable) assistant;                                      
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();                        
        }
        running = true;
    }
    /**
     * <pre>Create by satrya 2013-12-18</pre>
     * <pre>Keterangan : untuk mencari data di mesin berdasarkan parameter</pre>
     * @param startDate
     * @param endDate
     * @param statusParam 
     */
     public void startTransfer(java.util.Date startDate,java.util.Date endDate, int statusParam,int changeAutomaticManualFinish) {
        if (txtProcessClass != null && txtProcessClass.size() > 0) {
            
 /*di hidden dlu 
  * String odbcClasses = PstSystemProperty.getValueByName("ATT_MACHINE_ODBC_CLASS");            
 Vector vClassSysProp = com.dimata.util.StringParser.parseGroup(odbcClasses);
 Hashtable attMachClass = new Hashtable(); 
 Vector vClass = new Vector();
 if(vClassSysProp !=null && vClassSysProp.size() > 0){ 
     for(int idx=0;idx<vClassSysProp.size();idx++){        
        String[] strGrup = (String[]) vClassSysProp.get(idx); 
        if(strGrup!=null && strGrup.length>0 ){
            String className = "com.dimata.harisma.utility.machine."+strGrup[0];
            String user ="";
            String pwd ="";
            String dsnName="";
            String host ="";
            String port ="";            
            if(strGrup.length>1){
                Vector vParams = com.dimata.util.StringParser.parseGroup(strGrup[1],"&","=");
                if(vParams!=null && vParams.size()>0){
                    for(int i=0;i<vParams.size();i++){
                       String[] strParam = (String[]) vParams.get(i);
                       if(strParam!=null && strParam.length>0 ){
                          String paramName  = strParam[0];
                          String paramValue = strParam.length >1 ? strParam[1]:"";
                          if(paramName!=null && paramName.equalsIgnoreCase("user")){
                            user = paramValue;  
                          } else if(paramName!=null && paramName.equalsIgnoreCase("pwd")){
                            pwd = paramValue;  
                          } else if(paramName!=null && paramName.equalsIgnoreCase("dsnName")){
                            dsnName = paramValue;  
                          }else if(paramName!=null && paramName.equalsIgnoreCase("host")){
                            host = paramValue;  
                          }else if(paramName!=null && paramName.equalsIgnoreCase("port")){
                            port = paramValue;  
                          }
                       }                       
                    }
                }
           }
           DBMachineConfig dbC = new DBMachineConfig();
           dbC.setClassName(className);
           dbC.setUser(user);
           dbC.setPwd(pwd);
           dbC.setDsn(dsnName);
           dbC.setHost(host);
           dbC.setPort(port);
           attMachClass.put(className,dbC );
           vClass.add(className);
       }
   }
 }*/            
            
            
            if (getTxtProcessList() == null) {
                txtProcessList = new Vector();
            }
            for (int x = 0; x < txtProcessClass.size(); x++) {
                try {
                    Runnable task = null;
                    AttTransfer_I txProcess = null;
                    if (getTxtProcessList().size() >  x) {
                        txProcess = (AttTransfer_I) getTxtProcessList().get(x);
                    }

                    if (txProcess == null) {
                        String clsName = txtProcessClass.get(x);
                        //AttTransfer_I attI = (AttTransfer_I) Class.forName(clsName).newInstance();
                        task = (Runnable) Class.forName(clsName).newInstance();                                                
                        txProcess = (AttTransfer_I) task;
                        //update by satrya 2013-12-19
                        txProcess.searchDBMachine(startDate, endDate, statusParam,changeAutomaticManualFinish);
                        mesinAbsensiMessage.setAutomaticContinueSearch(changeAutomaticManualFinish);
                        mesinAbsensiMessage.setStatusHr(statusParam);
                        mesinAbsensiMessage.setStartDate(startDate);
                        mesinAbsensiMessage.setEndDate(endDate);
                        mesinAbsensiMessage.setUsePushStop(true);
                        
                       /* DBMachineConfig dbC = (DBMachineConfig) attMachClass.get(clsName);
                        
                        txProcess.initDBConfig(dbC);*/
                        
                        if(vJProgressBar!=null && x<vJProgressBar.size()){
                            txProcess.setProgressBar(vJProgressBar.get(x));
                        }
                        txProcess.setTextArea(jTextArea);
                        getTxtProcessList().add(txProcess);
                        //txProcess = (Att2000Transfer) task;
                        //txProcess.setSleepMs(300);
                        Thread worker = new Thread(task);
                        worker.setDaemon(false);
                        worker.start();

                    } else {
                        if (!txProcess.isRunning()) {
                            txProcess.setRunning(true);
                            //update by satrya 2013-12-19
                            txProcess.searchDBMachine(startDate, endDate, statusParam,changeAutomaticManualFinish);
                              mesinAbsensiMessage.setAutomaticContinueSearch(changeAutomaticManualFinish);
                        mesinAbsensiMessage.setStatusHr(statusParam);
                        mesinAbsensiMessage.setStartDate(startDate);
                        mesinAbsensiMessage.setEndDate(endDate);
                        mesinAbsensiMessage.setUsePushStop(true);
                          
                            task = (Runnable) txProcess;
                            Thread worker = new Thread(task);
                            worker.start();
                        }
                    }
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }         
        }
        
        if(assistant!=null){
            assistant.setRunning(true);
            Runnable task = (Runnable) assistant;                                      
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();                        
        }
        running = true;
    }
    public void stopTransfer(int i) {
        AttTransfer_I txProcess = null;
        if (getTxtProcessList().size() < i) {
            txProcess = (AttTransfer_I) getTxtProcessList().get(i);
            txProcess.setRunning(false);
            //update by satrya 2013-12-19
            txProcess.searchDBMachine(null, null, 0,1);
//            mesinAbsensiMessage.setAutomaticContinueSearch(1);
//           mesinAbsensiMessage.setStatusHr(0);
//            mesinAbsensiMessage.setStartDate(null);
//            mesinAbsensiMessage.setEndDate(null);
            mesinAbsensiMessage.setUsePushStop(false);
          
        }
        if(assistant!=null){
            assistant.setRunning(false);
            running = false; 
        }
    }

    public void stopTransfer() {
        AttTransfer_I txProcess = null;

        if (getTxtProcessList()!=null  && getTxtProcessList().size() >0) {
           for(int i =0; i<getTxtProcessList().size();i++) {
            txProcess = (AttTransfer_I) getTxtProcessList().get(i);
            txProcess.setRunning(false);
            //update by satrya 2013-12-19
            txProcess.searchDBMachine(null, null, 0,1);
//                mesinAbsensiMessage.setAutomaticContinueSearch(1);
//           mesinAbsensiMessage.setStatusHr(0);
//            mesinAbsensiMessage.setStartDate(null);
//            mesinAbsensiMessage.setEndDate(null);
            mesinAbsensiMessage.setUsePushStop(false);
        }
        }
        if(assistant!=null){
            assistant.setRunning(false);
            //running = false; 
        }
        running = false;
    }

    /**
     * total jumlah record di table mesin
     * @return
     */
    public int getTotalRecord(int i) {
        AttTransfer_I txProses = null;
        if (getTxtProcessList()!=null && getTxtProcessList().size() > i) {
            txProses = (AttTransfer_I) getTxtProcessList().get(i);
            return txProses.getRecordSize();
        } else {
            return 0;
        }
    }

    public int getProcentTransfer(int i) {
        AttTransfer_I txProcess = null;
        if (getTxtProcessList()!=null && getTxtProcessList().size() > i) {
            txProcess = (AttTransfer_I) getTxtProcessList().get(i);
            return txProcess.getProgressSize();
        }
        return 0;
    }
  
    public String getMessageTransfer(int i) {
        AttTransfer_I txProcess = null;
        if (getTxtProcessList()!=null && getTxtProcessList().size() > i) {
            txProcess = (AttTransfer_I) getTxtProcessList().get(i);
            return txProcess.getMessage();
        }
        return "";
    }
    
    public void isRunning(int i) {
        AttTransfer_I txProcess = null;
        if (getTxtProcessList()!=null && getTxtProcessList().size() > i) {
            txProcess = (AttTransfer_I) getTxtProcessList().get(i);
            txProcess.setRunning(false); 
        }

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
    
    //update by satrya 2013-07-25
    /**
     * untuk mengetahui total size analyze
     * @return 
     */
    public int getTotalAnalyzeRecordAssistant() {                
        if (assistant!=null) {            
            return assistant.getRecordSizeAbsence();
        } else {
            return 0;
        }
    }
 //update by satrya 2013-07-25
    /**
     * untuk mengetahui prosentase analyse
     * @return 
     */
    public int getProcentAnalyzeTransferAssistant() {
            if (assistant!=null) {                        
            return assistant.getProgressSizeAbsence();
        }
        return 0;
    }

    public String getTransferAssistantMessage() {
            if (assistant!=null) {                        
            return assistant.getMessage();
        }
        return "";
    }
    //update by satrya 2013-07-25
    /**
     * keterangan : memberikan message mengenai analize absence
     * @return 
     */
    public String getTransferAnalizeAssistantMessage() {
            if (assistant!=null) {                        
            return assistant.getMessageProsessAbsence();
        }
        return "";
    }

    
    public boolean isRunningAssistant() {
        if (assistant!=null) {                    
            assistant.isRunning(); 
        }
        return false;
    }
    
    
  /*  public String getErrMsg(int mesinIdx){
          if (machines!=null && mesinIdx >= 0 && mesinIdx < machines.size()){

               WatcherMachine mesin =  (WatcherMachine) machines.get(mesinIdx);
               return mesin.getErrMsg();
        }
          return"";

    } /* }
        public int getEr(int mesinIdx){
          if (machines!=null && mesinIdx >= 0 && mesinIdx < machines.size()){

               WatcherMachine mesin =  (WatcherMachine) machines.get(mesinIdx);
               return mesin.getEr();
        }
          return 0;

    }*/
     public boolean getStatus() {
        return running;
    }
     
     public static void setProgressBar(int idx, javax.swing.JProgressBar jProgressBar){
         if(vJProgressBar==null){
             vJProgressBar= new Vector();
         }
         if(idx<0){ 
             return ; 
         }
         if(idx>=vJProgressBar.size()){
             for(int i=vJProgressBar.size();i<=idx;i++ ){
                vJProgressBar.add(null);                 
             }
             vJProgressBar.set(idx, jProgressBar);
         }
     }
     
     public static void setTextArea(javax.swing.JTextArea jTextAreaPar){
         jTextArea = jTextAreaPar;
     }
}
