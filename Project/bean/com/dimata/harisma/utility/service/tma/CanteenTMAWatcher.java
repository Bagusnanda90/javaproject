/*
 * CanteenTMAWatcher.java
 * @author  rusdianta
 * Created on January 17, 2005, 12:24 PM
 */

package com.dimata.harisma.utility.service.tma;

import java.util.*;

import com.dimata.system.entity.system.*;

public class CanteenTMAWatcher implements Runnable {
    
    int i = 0;
    String machineNumber = "01";
    String[] machineNumbers;
    /** Creates a new instance of CanteenTMAWatcher */
    public CanteenTMAWatcher() {
    }
    
    public void run() {
        System.out.println(".: [CanteenTMAWathcer] started ....................");
        CanteenTMAService canteenTMAService = new CanteenTMAService();
        ServiceManagerTMA objServiceManagerTMA = new ServiceManagerTMA();        
        machineNumber = String.valueOf(PstSystemProperty.getValueByName("CANTEEN_TMA_NO"));
        StringTokenizer strTokenizer = new StringTokenizer(machineNumber,",");
        machineNumbers = new String[strTokenizer.countTokens()];
        int count = 0;
        while(strTokenizer.hasMoreTokens()){
            machineNumbers[count] = strTokenizer.nextToken();
            System.out.println("CANTEEN MACHINE :::::::::: "+machineNumbers[count]);
            count ++;
        }
        while (canteenTMAService.running) {
            try {
                // download transaction data from TMA Machine
                process();                
                // sleeping time for next process
                long TIMEKEEPING_INTERVAL = Long.parseLong(PstSystemProperty.getValueByName("TIMEKEEPING_INTERVAL"));
                int sleepTime = (int) (TIMEKEEPING_INTERVAL * 60 * 1000);
                System.out.println(".:: [CanteenTMAWatcher] finished, service will sleep for " + TIMEKEEPING_INTERVAL + " minutes\r");
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                System.out.println("\tCanteenTMAWatcher interrupted with message : " + e.toString());
            }
        }
    }
    
    public void process() {
        String resultRead = "";
        String tma01Status = "";
        //String tma02Status = "";
        int numOfTransaction = 0;        
        boolean succeed01 = false;
       // boolean succeed02 = false;        
        Vector vDownload1 = new Vector(1, 1);
        //Vector vDownload2 = new Vector(1, 1);
        
        System.out.println("\r    -> [CanteenTMAWatcher] process #" + i + " @ " + new Date().toLocaleString());
        i++;
        
        //String tmaPort = "COM1";       // Port used by TMA machine
        //boolean checkSweepTime = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_CHECK_SWEEP_TIME")) > 0;
        //int ignoreTime = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_IGNORE_SWEEP_TIME")) * 60 * 1000;
        
        // Watcher machine TMA 1 
        try {
            CanteenTMAAccess canteenTMAAccess1 = new CanteenTMAAccess();
            //canteenTMAAccess1.setUsedPort(tmaPort);
            //canteenTMAAccess1.setCheckSweepTime(checkSweepTime);
            //canteenTMAAccess1.setIgnoreTime(ignoreTime);
            if(machineNumbers.length>0 && !(machineNumbers[0].equals("")) && machineNumbers[0].length()>0){
                vDownload1 = canteenTMAAccess1.executeCommand(canteenTMAAccess1.DOWNLOAD, machineNumbers[0], "");            
                if (String.valueOf(vDownload1.get(0)).equals("OFF") == false)
                    succeed01 = true;
                tma01Status = vDownload1.get(0) + " data download from TMA "+machineNumbers[0];         
            }
        } catch (Exception ERROR) {
            tma01Status = "Unable to download from TMA "+machineNumbers[0];
        }
        
        /*try {
            CanteenTMAAccess canteenTMAAccess2 = new CanteenTMAAccess();
            //canteenTMAAccess2.setUsedPort(tmaPort);
            //canteenTMAAccess2.setCheckSweepTime(checkSweepTime);
            //canteenTMAAccess2.setIgnoreTime(ignoreTime);
            vDownload2 = canteenTMAAccess2.executeCommand(canteenTMAAccess2.DOWNLOAD, "02", "");
            
            if (String.valueOf(vDownload2.get(0)).equals("OFF") == false)
                succeed02 = true;
            tma02Status = vDownload2.get(0) + " data download from TMA 02";            
        } catch (Exception ERROR) {
            tma02Status = "Unable to download from TMA 02";
        } */
        
        System.out.println("     -> Result : ");
        System.out.println("\t" + tma01Status);
        //System.out.println("\t" + tma02Status);
    }    
}
