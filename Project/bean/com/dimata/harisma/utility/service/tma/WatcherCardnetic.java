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
import com.dimata.system.entity.system.*;
import com.dimata.harisma.utility.odbc.*;

public class WatcherCardnetic implements Runnable {

    int i = 0;
    
    public WatcherCardnetic() {
    }
    
    public void run() {        
        System.out.println(".:: [WatcherCardnetic] started ....................");
        ServiceManagerCardnetic objServiceManagerCardnetic = new ServiceManagerCardnetic();        
        
        while (objServiceManagerCardnetic.running) {            
            try {
                // proses download data from machine TMA                
                process();   // sleeping time for next process
                long TIMEKEEPING_INTERVAL = Long.parseLong(PstSystemProperty.getValueByName("TIMEKEEPING_INTERVAL"));                
                int sleepTime = (int) (TIMEKEEPING_INTERVAL * 60 * 1000);
                System.out.println(".:: [WatcherCardnetic] finished, service will sleep for "+TIMEKEEPING_INTERVAL+" minutes\r");                
                Thread.sleep(sleepTime);                
            } catch (Exception e) {
                System.out.println("\t[WatcherCardnetic] interrupted with message : " + e);
            }
        }        
    }

    public void process() {        
        
        String txt = PstSystemProperty.getValueByName("CARDNETIC_DATA_SOURCE");
	
	System.out.println(txt);
	if(txt.equalsIgnoreCase("TEXT")){
		TransferPresenceFromMdfText transferText = new TransferPresenceFromMdfText();
		transferText.transferDataPresence();
	}
	else{
		TransferPresenceFromDb transfer = new TransferPresenceFromDb();
		transfer.transferDataPresence();
	}
        
    }
}
