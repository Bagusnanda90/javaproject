/*
 * WatcherTMA.java
 * @author  karya
 * Created on December 12, 2002, 7:40 PM
 */

package com.dimata.harisma.utility.service.tma;

import java.util.*;

import com.dimata.harisma.session.admin.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.attendance.*;
//import com.dimata.harisma.utility.service.tma.AccessTMA;

import com.dimata.system.entity.*;

public class WatcherTMA implements Runnable {

    int i = 0;
    String machineNumber = "01";
    String[] machineNumbers;
    public WatcherTMA(){
    }
    
    public void run(){        
        
        System.out.println(".:: [WatcherTMA] started ....................");
        ServiceManagerTMA objServiceManagerTMA = new ServiceManagerTMA();        
        machineNumber = String.valueOf(PstSystemProperty.getValueByName("ABSEN_TMA_NO"));
        StringTokenizer strTokenizer = new StringTokenizer(machineNumber,",");
        machineNumbers = new String[strTokenizer.countTokens()];
        int count = 0;
        while(strTokenizer.hasMoreTokens()){
            machineNumbers[count] = strTokenizer.nextToken();
            System.out.println("ABSEN MACHINE :::::::::: "+machineNumbers[count]);
            count ++;
        }
        
        while (ServiceManagerTMA.running) {            
            try {
                // proses download data from machine TMA                
                process();   // sleeping time for next process
                long TIMEKEEPING_INTERVAL = Long.parseLong(PstSystemProperty.getValueByName("TIMEKEEPING_INTERVAL"));                
                int sleepTime = (int) (TIMEKEEPING_INTERVAL * 60 * 1000);
                System.out.println(".:: [WatcherTMA] finished, service will sleep for "+TIMEKEEPING_INTERVAL+" minutes\r");                
                Thread.sleep(sleepTime);                
            } catch (Exception e) {
                System.out.println("\t[WatcherTMA] interrupted with message : " + e);
            }
        }        
        
    }

    public void process() {        
        
        String resultRead = "";
        String tma01Status = "";
        String tma02Status = "";                   
        String tma03Status = "";
        String tma04Status = "";                   

        int numOfTransaction = 0 ;
        
        boolean succeed01 = false;
        boolean succeed02 = false;
        boolean succeed03 = false;
        boolean succeed04 = false;
        
        Vector vDownload1 = new Vector(1,1);
        Vector vDownload2 = new Vector(1,1);
        Vector vDownload3 = new Vector(1,1);
        Vector vDownload4 = new Vector(1,1);

        System.out.println("\r     -> [WatcherTMA] process #" + i + " @ " + new Date().toLocaleString());
        i++;

        // Watcher machine TMA 1
        try {            
            AccessTMA accessTMA1 = new AccessTMA();
            if(machineNumbers.length>0 && !(machineNumbers[0].equals("")) && machineNumbers[0].length()>0){
                vDownload1 = AccessTMA.executeCommand(AccessTMA.DOWNLOAD, machineNumbers[0], "");
                if (String.valueOf(vDownload1.get(0)).equals("OFF") == false) 
                    succeed01 = true;
                tma01Status = vDownload1.get(0) + " data downloaded from TMA "+machineNumbers[0];      
            }
        } catch (Exception e) {
            tma01Status = "Unable to download from TMA "+machineNumbers[0];        
        }     
        
        // Watcher machine TMA 2
        try {
            AccessTMA accessTMA2 = new AccessTMA();
            if(machineNumbers.length>1 && !(machineNumbers[1].equals("")) && machineNumbers[1].length()>0){
                vDownload2 = AccessTMA.executeCommand(AccessTMA.DOWNLOAD, machineNumbers[1], "");
                if (String.valueOf(vDownload2.get(0)).equals("OFF") == false) 
                    succeed02 = true;
                tma02Status = vDownload2.get(0) + " data downloaded from TMA "+machineNumbers[1];
            }
        } catch (Exception e) {
            tma02Status = "Unable to download from TMA "+machineNumbers[1];        
        }               
        
        // Watcher machine TMA 3
        try {
            AccessTMA accessTMA3 = new AccessTMA();
            if(machineNumbers.length>2 && !(machineNumbers[2].equals("")) && machineNumbers[2].length()>0){
                vDownload3 = AccessTMA.executeCommand(AccessTMA.DOWNLOAD, machineNumbers[2], "");
                if (String.valueOf(vDownload3.get(0)).equals("OFF") == false) 
                    succeed03 = true;
                tma03Status = vDownload3.get(0) + " data downloaded from TMA "+machineNumbers[2];
            }
        } catch (Exception e) {
            tma03Status = "Unable to download from TMA "+machineNumbers[2];        
        }               

        // Watcher machine TMA 4
        try {
            AccessTMA accessTMA4 = new AccessTMA();
            if(machineNumbers.length>3 && !(machineNumbers[3].equals("")) && machineNumbers[3].length()>0){
                vDownload4 = AccessTMA.executeCommand(AccessTMA.DOWNLOAD, machineNumbers[3], "");
                if (String.valueOf(vDownload4.get(0)).equals("OFF") == false) 
                    succeed04 = true;
                tma04Status = vDownload4.get(0) + " data downloaded from TMA "+machineNumbers[3];
            }
        } catch (Exception e) {
            tma04Status = "Unable to download from TMA "+machineNumbers[3];        
        }               
        
        System.out.println("     -> Result : ");
        System.out.println("\t" + tma01Status);
        System.out.println("\t" + tma02Status);         
        System.out.println("\t" + tma03Status);
        System.out.println("\t" + tma04Status);         
    }
}
