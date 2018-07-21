/*
 * ServiceManagerPresence.java
 *
 * Created on December 12, 2002, 7:25 PM
 */

package com.dimata.harisma.utility.service.presence;
import com.dimata.harisma.utility.service.tma.*;
/**
 *
 * @author  karya
 * @version 
 */
public class ServiceManagerPresence {

    public static boolean running = false;
    public static WatcherTMA watcherTMA = new WatcherTMA();

    /** Creates new ServiceManagerPresence */
    public ServiceManagerPresence() {
    }

    public void startWatcherPresence() {
        if (running) return;
        ServiceManagerPresence objMan = new ServiceManagerPresence();
        Thread thLocker = new Thread(new WatcherPresence());
        thLocker.setDaemon(false);
        running = true;
        thLocker.start();
    }

    public void stopWatcherPresence() {
        running = false;
    }

    public boolean getStatus() {
        return running;
    }
}
