/*
 * BackupDatabaseProcess.java
 *
 * Created on October 8, 2004, 5:48 PM
 */

package com.dimata.harisma.entity.admin.service;

// package core java
import java.util.Date;

// package qdep
import com.dimata.util.*;

/**
 *
 * @author  gedhy
 */
public class BackupDatabaseProcess {    
    
    static String stCommandScript = ""; // command script yang akan dijalankan
    static boolean running = false; // status service
    
    public BackupDatabaseProcess() {
    }    

    public BackupDatabaseProcess(String strCommand) 
    {
        this.stCommandScript = strCommand;
    }    

    public String getStCommandScript()
    {
        return stCommandScript;
    }

    public void setStCommandScript(String stCommand)
    {
        stCommandScript = stCommand;
    }    

    public boolean getStatus()
    {
        return running;
    }    
    
    public synchronized void startService() 
    {
        if (!running) 
        {
            System.out.println(".:: Backup database service started ... !!!");   
            try 
            {
                running = true;
                Thread thr = new Thread(new ServiceBackup());
                thr.setDaemon(false);
                thr.start();

            }
            catch (Exception e) 
            {
                System.out.println(">>> Exc when Backup database start ... !!!");
            }
        }
    }

    public synchronized void stopService() 
    {
        running = false;
        System.out.println(".:: Backup database service stoped ... !!!");
    }
    
    
    /**
     * @param stCommandScript
     * @return
     * @created by Edhy
     */        
    public static int runCommandScript(String stCommandScript)  
    {        
        ExecCommand execCommand = new ExecCommand();
        return execCommand.runCommmand(stCommandScript);
    }        
    
}
