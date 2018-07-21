/*
 * WatcherTMA.java
 *
 * Created on December 12, 2002, 7:40 PM
 */

package com.dimata.harisma.utility.service.presence;

import java.util.Vector;
import java.util.Date;

import com.dimata.harisma.utility.service.presence.ServiceManagerPresence;
/**
 *
 * @author  karya
 * @version 
 */
public class WatcherPresence implements Runnable {

    int i = 0;
    /** Creates new WatcherTMA */
    public WatcherPresence() {
    }

    public void run() {
        System.out.println("\t[WatcherPresence] starting...");
        while (ServiceManagerPresence.running) {
            try {
                process();

                int sleepTime = (int) (1 * 1 * 60 * 1000); // hour * min * sec * ms
                //System.out.println("\t[WatcherTMA] sleeptime = " + sleepTime);
                Thread.sleep(sleepTime);

                if (!ServiceManagerPresence.running)
                    break;
            }
            catch (Exception e) {
                System.out.println("\t[WatcherPresence] interrupted with message : " + e);
            }
        }
        System.out.println("\t[WatcherPresence] stopped.");
    }

    public void process() {
        System.out.println("\t[WatcherPresence] process #" + i + " @ " + new Date().toLocaleString());
        i++;
        
        /*************************************************************
         * for every record in HR_PRESENCE where ANALYZED = 0
         *     if STATUS = 0 (in)
         *         find the nearest TIME_IN on the nearest schedule
         *         get the TIME_OUT from the schedule
         *     end if
         *     find the nearest/matched schedule
         *     store in HR_PRESENCE_ANALYZE
         *     mark the current record in HR_PRESENCE as ANALYZED = 1
         * end for
        **************************************************************/
        
        AnalyzePresence analyzer = new AnalyzePresence();
        
    }
}
