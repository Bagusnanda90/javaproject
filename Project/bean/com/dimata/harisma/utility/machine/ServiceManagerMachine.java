

package com.dimata.harisma.utility.machine;
import java.util.Vector;

public class ServiceManagerMachine { 
    private static ServiceManagerMachine svcMan= null;
    public static boolean running = false;    /*GEDE_20110831_011{*/
    private static Vector machines= new Vector();

    public static ServiceManagerMachine getInstance(){
        if(svcMan==null){
            svcMan = new ServiceManagerMachine();
        }
        return svcMan;
    }  /* }*/
    public boolean getStatus() {
        return running;
    }
    
    private ServiceManagerMachine() {
    }

    public void startWatcherMachine() 
    {
        if (!running) {
            System.out.println("\r.:: Machine Downloader service started ... !!!");   
            try {
                running = true;
                if(this.machines!=null){
                    this.machines.removeAllElements();
                } else{
                    this.machines = new Vector();
                }
                WatcherMachine mesin = new WatcherMachine();
                mesin.setSleepTimeByRecord(500);
                this.machines.add(mesin);
                Thread thr = new Thread(mesin);
                thr.setDaemon(false);                
                thr.start();
            } catch (Exception e) {
                System.out.println(">>> Exc when Machine Downloader start ... !!!");
            }             
        }
    }

    public void stopWatcherMachine() {  
        OpenPorts.disconnect();
        running = false;
        if (machines!=null && machines.size() > 0){
            for(int i=0;i<machines.size();i++)
            {
               WatcherMachine mesin =  (WatcherMachine) machines.get(i);
               mesin.setRunning(false);
            }
        }
        System.out.println("\r.:: Machine Downloader service stopped ... !!!"); 
    }
    /*GEDE_20110901_01 {*/
    public int getTotalData(int mesinIdx){
          if (machines!=null && mesinIdx >= 0 && mesinIdx < machines.size()){

               WatcherMachine mesin =  (WatcherMachine) machines.get(mesinIdx);
               return mesin.getTotalData();
        }
          return 0;
    }
     public int getTransferData(int mesinIdx){
          if (machines!=null && mesinIdx >= 0 && mesinIdx < machines.size()){

               WatcherMachine mesin =  (WatcherMachine) machines.get(mesinIdx);
               return mesin.getTransferData();
        }
          return 0;
    } /* }*/
      public String getErrMsg(int mesinIdx){
          if (machines!=null && mesinIdx >= 0 && mesinIdx < machines.size()){

               WatcherMachine mesin =  (WatcherMachine) machines.get(mesinIdx);
               return mesin.getErrMsg();
        }
          return"";

    } /* }*/
        public int getEr(int mesinIdx){
          if (machines!=null && mesinIdx >= 0 && mesinIdx < machines.size()){

               WatcherMachine mesin =  (WatcherMachine) machines.get(mesinIdx);
               return mesin.getEr();
        }
          return 0;

    } /* }*/
}
