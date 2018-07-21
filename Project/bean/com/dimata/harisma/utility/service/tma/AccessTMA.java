/*
 * TimeAttendanceServiceManager.java
 * @author  karya
 * @edited by Edhy
 * Created on November 6, 2002, 4:40 PM
 */

package com.dimata.harisma.utility.service.tma;

import java.util.*;

import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.attendance.*;

public class AccessTMA {

    /* --- start debug --- */
    public static final String debugResult[] = {
        "I01TA03030509520300674",
        "I02TB03030518120300674",
        "I01TA03030609420300674",
        "I02TB03030618220300674",

        "I01TA03030515550300676",  
        "I02TB03030600210300676",
        "I01TA03030611550300676",
        "I02TB03030620310300676"
    };    
    /* --- end of debug --- */
    
    public static final int RESET         = 0;      
    public static final int DOWNLOAD      = 1;
    public static final int UPLOAD        = 2;
    public static final int LIST          = 3;
    public static final int DELETE        = 4;
    public static final int GETTIME       = 5;
    public static final int SETTIME       = 6;
    public static final int CHECK_MACHINE = 7;
    public static final int VALIDATION    = 8;
    
    public static final int IDLE          = 0;
    public static final int BUSY          = 1;
    
    public static final long INTERVAL     = 500;
    private static String usedPort = "COM1";                /* --- added by rusdianta --- */
    static int status = IDLE;
    public static String resultRead = "";
    public static String resultRemove = "";
    
    public static Thread tInitRst;
    public static Thread tReset;
    public static Thread tRead;
    public static long t1;
    public static long t2;
    public static int dataCount;
    
    public AccessTMA() {
    }
    
    /* --- added by rusdianta --- */
    
    public AccessTMA(String usedPort) {
        if (usedPort.length() > 0)
            this.usedPort = usedPort;
        System.out.println("USED PORT : " + this.usedPort);
    }
    
    /* -------------------------- */

    public int getStatus() {
        return status;
    }    
    
    /* --- added by rusdianta --- */
    public String getUsedPort() {
        return usedPort;        
    }
    
    public void setUsedPort(String usedPort) {
        if (usedPort.length() > 0)
            this.usedPort = usedPort;        
    }
    /* --------------------------------------- */
    
    /**
     * Handle process to access machine TMA
     * @param <CODE>iCommand</CODE>command will send to machine TMA as defined above
     * @param <CODE>tma</CODE>machine TMA number
     * @param <CODE>strParameter</CODE>command's parameter send to machine TMA
     * @return
     */    
    
    public static synchronized Vector executeCommand(int iCommand, String tma, String strParameter) 
    {
        Vector vResult = new Vector();
        TimeAttendance reader = null;
        try 
        {
            reader = new TimeAttendance();
            
            /* --- modified by rusdianta --- */
            reader.setTMAPortName(usedPort);
            System.out.println("USED PORT : " + reader.getTMAPortName());
            /* ---------------------------------------------------------- */
            
        }
        catch (Exception e) 
        {
            System.out.println("Exc when try to instantiate TimeAttendance : " + e);          
        }
        status = BUSY;        
        
        switch (iCommand) 
        {
            /* --- process check machine status, available(OK) or not(ERROR) --- */ 
            case CHECK_MACHINE :
                try 
                {
                    reader.execute("*" + tma + "X");
                }
                catch (Exception e) 
                {
                    System.err.println("\t[AccessTMA] CHECK MACHINE error : reader.execute(\"*" + tma + "X\") : " + e.toString());
                }
                
                tInitRst = new Thread();
                tInitRst.start();
                try 
                { 
                    tInitRst.sleep(INTERVAL);   
                }
                catch (Exception e) 
                {
                    System.err.println("\tSet interval for Thread : " + e.toString());                    
                }
                
                resultRead = reader.getRes();
                vResult.addElement(resultRead);
                tInitRst.stop();
                break;
            
            /* --- process set machine validation, is permitted to use pin number (1) or not (0) --- */  
            case VALIDATION :
                try 
                {
                    reader.execute("*" + tma + "X");
                }
                catch (Exception e) 
                {
                    System.err.println("\t[AccessTMA] RESET error : reader.execute(\"*" + tma + "X\");");
                }
                
                tInitRst = new Thread();
                tInitRst.start();
                try 
                { 
                    tInitRst.sleep(INTERVAL); 
                }
                catch (Exception e) 
                {
                    System.err.println("\tSet interval for Thread : " + e.toString());                                        
                }
                resultRead = reader.getRes();
                vResult.addElement(resultRead);
                tInitRst.stop();

                reader.execute("*" + tma + "S24" + strParameter);
                tReset = new Thread();
                tReset.start();
                try 
                { 
                    tReset.sleep(INTERVAL); 
                }
                catch (Exception e) 
                {
                    System.err.println("\tSet interval for Thread : " + e.toString());                                                            
                }
                tReset.stop();
                break;                
            
            /* --- process reset data on machine (USER and TRANSACTION) --- */
            case RESET :
                try 
                {
                    reader.execute("*" + tma + "X");
                }
                catch (Exception e) 
                {
                    System.err.println("\t[AccessTMA] RESET error : reader.execute(\"*" + tma + "X\");");
                }
                
                tInitRst = new Thread();
                tInitRst.start();
                try 
                { 
                    tInitRst.sleep(INTERVAL); 
                }
                catch (Exception e) 
                {
                    System.err.println("\tSet interval for Thread : " + e.toString());                                        
                }
                resultRead = reader.getRes();
                vResult.addElement(resultRead);
                tInitRst.stop();

                reader.execute("*" + tma + "RST");
                tReset = new Thread();
                tReset.start();
                try 
                { 
                    tReset.sleep(INTERVAL); 
                }
                catch (Exception e) 
                {
                    System.err.println("\tSet interval for Thread : " + e.toString());                                                            
                }
                tReset.stop();
                break;                
            
            /* --- process download data on machine (TRANSACTION) --- */
            case DOWNLOAD :                
                t1 = System.currentTimeMillis();
                resultRemove = "";
                resultRead = "";
                dataCount = 0;                
                
                Date downloadTransTime = new Date();                               
                try 
                {
                    reader.execute("*" + tma + "X");
                }
                catch (Exception e) 
                {
                    System.err.println("\t[AccessTMA] DOWNLOAD error : reader.execute(\"*" + tma + "X\");");                
                }
                Thread tInit = new Thread();
                tInit.start();
                
                try 
                {
                    tInit.sleep(INTERVAL);
                }
                catch (Exception e) 
                {
                    System.err.println("\tSet interval for Thread : " + e.toString());                                                                                
                }
                
                resultRead = reader.getRes();                
                tInit.stop();
                
              
                // to check number of transaction data on machine 
                // by edhy                
                /* ---
                int transactionOnMachine = 0;               
                try 
                {
                    reader.execute("*" + tma + "NOT"); // command utk ngecek isi transaksi *01NOT  ==> FF
                }
                catch (Exception e) 
                {
                    System.err.println("\t[AccessTMA] DOWNLOAD error : reader.execute(\"*" + tma + "NOT\");");                
                }
                Thread tTransaction = new Thread();
                tTransaction.start();                
                
                try 
                {
                    tTransaction.sleep(INTERVAL);
                }
                catch (Exception e) 
                {
                    System.err.println("\tSet interval for Thread : " + e.toString());                                                                                
                }

                
                String resultTransaction = reader.getRes();
                try
                {
                    String strCount = resultTransaction.substring(2).trim();
                    if(!strCount.equals("OVER"))  
                    {
                        transactionOnMachine = Integer.parseInt(strCount);
                    }
                }
                catch(Exception e)                    
                {
                    System.out.println("Exc when check transactionOnMachine : "+e.toString());      
                }
                
                tTransaction.stop();
                --- */                
                
                if (resultRead.equals("OFF") == false) 
                {
                    try 
                    {
                        while ( !(resultRemove.equals("FFOVER")) )   
                        {
                            reader.execute("*" + tma + "READ");
                            tRead = new Thread();
                            tRead.start();
                            tRead.sleep(INTERVAL);
                            resultRead = reader.getRes();
                            System.out.println("\t[AccessTMA] " + resultRead + " >> " + resultRead.compareToIgnoreCase("FFI"));
                            
                            /* --- variable declaration for presence data will inserted
                                   add by edhy  --- */
                            long oidPresence = 0;    
                            Date downloadDate = new Date();
                            String barcodeNumber = "";
                            String strNote = "";                            
                            
                            if ( (!resultRead.equalsIgnoreCase("FFOVER")) && (resultRead.compareToIgnoreCase("FFI") >= 9) ) 
                            {                                
                                // vResult.addElement(resultRead);
                                dataCount++;
                                System.out.println("\t[AccessTMA] TMA-" + tma + " transaction #" + dataCount + " = " + resultRead);
                                String tNumber = resultRead.substring(1+2, 3+2);
                                String tFunction = resultRead.substring(4+2, 5+2);
                                int tYear = Integer.parseInt(resultRead.substring(5+2, 7+2)) + 100;
                                int tMonth = Integer.parseInt(resultRead.substring(7+2, 9+2)) - 1;
                                int tDate = Integer.parseInt(resultRead.substring(9+2, 11+2));
                                int tHour = Integer.parseInt(resultRead.substring(11+2, 13+2));
                                int tMin = Integer.parseInt(resultRead.substring(13+2, 15+2));
                                String tBarcode = resultRead.substring(15+2, resultRead.length());
                                barcodeNumber = tBarcode;
                                Date tGregCal = new Date(tYear, tMonth, tDate, tHour, tMin);
                                downloadDate = tGregCal;  
                                
                                // update presence status of current presence data
                                // add by Edhy
                                Presence presence = new Presence();
                                int presenceStatus = 0;
                                long empOid = PstEmployee.getEmployeeByBarcode(tBarcode);
                                switch (tFunction.charAt(0)) 
                                {
                                    case 'A' :
                                        presence.setStatus(0);
                                        presenceStatus = 0;
                                        break;
                                        
                                    case 'B' :
                                        presence.setStatus(1);
                                        presenceStatus = 1;
                                        break;
                                        
                                    case 'C' :
                                        presence.setStatus(2);
                                        presenceStatus = 2;
                                        break;
                                        
                                    case 'D' :
                                        presence.setStatus(3);
                                        presenceStatus = 3;
                                        break;
                                        
                                    case 'E' :
                                        presence.setStatus(4);
                                        presenceStatus = 4;
                                        break;
                                        
                                    case 'F' :
                                        presence.setStatus(5);
                                        presenceStatus = 5;
                                        break; 
                                        
                                    default :
                                        break;
                                }                                

                                // inserting downloaded data to database                                   
                                presence.setEmployeeId(empOid);
                                presence.setPresenceDatetime(tGregCal);
                                presence.setAnalyzed(0);                                
                                oidPresence = PstPresence.insertExc(presence);      
                                
                                // update data on employee schedule based on this presence data
                                // add by Edhy
                                long periodId = PstPeriod.getPeriodIdBySelectedDate(tGregCal);                                                                                                    
                                int updatedFieldIndex = -1;    
                                long updatePeriodId = periodId;
                                Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(periodId, empOid, presenceStatus, tGregCal);                                
                                if(vectFieldIndex!=null && vectFieldIndex.size()==2)
                                {
                                    updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                                    updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));
                                }
                                
                                int updateStatus = 0;
                                try  
                                {
                                    //updateStatus = PstEmpSchedule.updateScheduleDataByPresence(periodId, empOid, updatedFieldIndex, tGregCal);
                                    updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, empOid, updatedFieldIndex, tGregCal);
                                    if(updateStatus>0)  
                                    {
                                        presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);    
                                        PstPresence.updateExc(presence);                                        
                                    }
                                }
                                catch(Exception e)
                                {
                                    System.out.println("Update Presence exc : "+e.toString());
                                }
                                
                            }                            
                            tRead.stop();
                            
                            // jika presence berhasil di insert, maka hapus record tersebut dari mesin
                            if(oidPresence!=0)
                            {
                                reader.execute("*" + tma + "REMOVE");
                                Thread tRemove = new Thread();
                                tRemove.start();
                                tRemove.sleep(INTERVAL);
                                resultRemove = reader.getRes();                                                                                                              
                                tRemove.stop();
                            }
                            
                            // jika pada saat di read sudah FFOVER, maka berarti data sudah habis
                            else if( resultRead.equalsIgnoreCase("FFOVER") )
                            {
                                resultRemove = "FFOVER";
                            }                            
                            
                            // add data into download logger  ==> do not use logging
                            // edited by edhy           
                            /* 
                            if(oidPresence>0)
                            {
                               strNote = "Download time = " + downloadTransTime;
                               strNote += " --- Insert presence oid = " + oidPresence;
                               strNote += " --- Transaksi nomor #" + dataCount + " dari " + transactionOnMachine + " barcode : " + barcodeNumber;
                               strNote += " --- " + resultRemove;
                               
                               BarcodeLog barcodeLog = new BarcodeLog();
                               barcodeLog.setCmdType("DOWNLOAD");
                               barcodeLog.setDate(downloadDate);
                               barcodeLog.setNotes(strNote);
                               PstBarcodeLog.insertExc(barcodeLog);                               
                            }                                                                                        
                            */
                        }
                    }
                    catch (Exception e) {
                        System.out.println("\t[WatcherTMA] inside -while- exception : " + e);
                    }
                }
                vResult.add(String.valueOf(dataCount));
                t2 = System.currentTimeMillis();
                System.out.println("\t[AccessTMA] Download time for TMA " + tma + ": " + (t2-t1) + "ms");
                break;             
                
            // process list/read used Id data on machine
            case LIST :
                dataCount = 0;
                try 
                { 
                    reader.execute("*" + tma + "OT"); 
                }
                catch (Exception e) 
                { 
                    System.out.println("\t*" + tma + "OT"); 
                }
                tInit = new Thread();
                tInit.start();
                
                try 
                { 
                    tInit.sleep(INTERVAL); 
                }
                catch (Exception e) 
                {
                    System.err.println("\tSet interval for Thread : " + e.toString());                                                                                
                }
                resultRead = reader.getRes();                
                
                tInit.stop();
                if (resultRead.equals("FFDONE")) 
                {
                    while (!(resultRead.equals("FFOVER"))) 
                    {
                        dataCount++;
                        try 
                        { 
                            reader.execute("*" + tma + "OU"); 
                        }
                        catch (Exception e) 
                        { 
                            System.out.println("\t*" + tma + "OU"); 
                        }
                        tRead = new Thread();
                        tRead.start();
                        
                        try 
                        { 
                            tRead.sleep(INTERVAL); 
                        }
                        catch (Exception e) 
                        {
                            System.err.println("\tSet interval for Thread : " + e.toString());
                        }
                        resultRead = reader.getRes();
                        System.out.println("\t[AccessTMA] " + dataCount + " *" + tma + "OU = " + resultRead);
                        tRead.stop();
                        vResult.add(String.valueOf(resultRead));
                    }
                }
                break;                
                
            // process upload data on machine (USERID)    
            case UPLOAD :
                reader.execute("*" + tma + "OD" + strParameter);
                Thread tDel4 = new Thread();
                tDel4.start();
                try 
                { 
                    tDel4.sleep(INTERVAL); 
                }
                catch (Exception e) 
                {
                    System.err.println("\tSet interval for Thread : " + e.toString());
                }
                tDel4.stop();
                reader.execute("*" + tma + "OI" + strParameter);
                Thread tRemove = new Thread();
                tRemove.start();
                try 
                { 
                    tRemove.sleep(INTERVAL); 
                }
                catch (Exception e) 
                {
                    System.err.println("\tSet interval for Thread : " + e.toString());
                }
                tRemove.stop();
                break;
                   
            // process delete data from machine (USERID)    
            case DELETE :
                reader.execute("*" + tma + "OD" + strParameter);
                Thread tDel3 = new Thread();
                tDel3.start();
                try 
                { 
                    tDel3.sleep(INTERVAL); 
                }
                catch (Exception e) 
                {
                    System.err.println("\tSet interval for Thread : " + e.toString());
                }
                tDel3.stop();
                break;

            // process get machine time
            case GETTIME :
                reader.execute("*" + tma + "TR");
                Thread tGetTime = new Thread();
                tGetTime.start();
                try 
                { 
                    tGetTime.sleep(INTERVAL); 
                }
                catch (Exception e) 
                {
                    System.err.println("\tSet interval for Thread : " + e.toString());
                }
                resultRead = reader.getRes();
                tGetTime.stop();
                vResult.add(String.valueOf(resultRead));
                break;
            
            // process set machine time    
            case SETTIME :
                reader.execute("*" + tma + "TW" + strParameter);
                Thread tSetTime = new Thread();
                tSetTime.start();
                try 
                { 
                    tSetTime.sleep(INTERVAL); 
                }
                catch (Exception e) 
                {
                    System.err.println("\tSet interval for Thread : " + e.toString());
                }
                resultRead = reader.getRes();
                tSetTime.stop();
                vResult.add(String.valueOf(resultRead));
                break;

            default :
                break;
        }
        status = IDLE;
        return vResult;
    }
}
