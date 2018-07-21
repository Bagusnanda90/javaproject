/*
 * absenceAnalyser.java
 *
 * Created on June 7, 2004, 9:33 AM
 */

package com.dimata.harisma.utility.service.message;   

// package core java
import com.dimata.harisma.utility.service.presence.*;
import java.util.Vector;
import java.util.Date;
import java.sql.*;

// package qdep
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.*;

// package harisma
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;


public class BirdDay 
{          
    static Date dtStartSvc; // untuk start pertama service
    static Date dtHistory; // untuk start pertama service  
    static boolean running = false; // status service  

    public Date getDate()
    {
        return dtStartSvc;
    }

    public void setDate(Date dt )
    {
        dtHistory = dt;
        dtStartSvc = dt;
    }
                                                             
    public Date getDateHistory()
    {
        return dtHistory;
    }

    public boolean getStatus()
    {
        return running;
    }
    
    public BirdDay() 
    {
    }

    
    public synchronized void startService() 
    {
        if (!running) 
        {
            System.out.println(".:: Service send message to machine service started ... !!!");   
            try 
            {
                running = true;
                Thread thr = new Thread(new ServiceBirdDay());
                thr.setDaemon(false);
                thr.start();

            }
            catch (Exception e) 
            {
                System.out.println(">>> Exc when Service send message to machine start ... !!!");
            }
        }
    }

    public synchronized void stopService() 
    {
        running = false;
        System.out.println(".:: Service send message to machine service stoped ... !!!");
    }
    
}
