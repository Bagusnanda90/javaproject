/*
 * CanteenTMAAccess.java
 * @author  rusdianta
 * Created on January 14, 2005, 10:38 AM
 */

package com.dimata.harisma.utility.service.tma;

import java.util.*;

import com.dimata.system.entity.system.*;

import com.dimata.harisma.entity.canteen.*;
import com.dimata.harisma.entity.employee.PstEmployee;

public class CanteenTMAAccess {
    
    public static final int RESET = 0;
    public static final int DOWNLOAD = 1;
    public static final int UPLOAD = 2;
    public static final int LIST = 3;
    public static final int DELETE = 4;
    public static final int GETTIME = 5;
    public static final int SETTIME = 6;
    public static final int CHECK_MACHINE = 7;
    public static final int VALIDATION = 8;
    
    public static final int IDLE = 0;
    public static final int BUSY = 1;
    
    public static final long INTERVAL = 500;
    public static int status = IDLE;
    public static String resultRead = "";
    public static String resultRemove = "";
    
    private static String usedPort = "COM1";
    public static boolean CHECK_SWEEP_TIME = false;   
    public static int IGNORE_TIME = 15 * 60 * 1000;          /* --- in milli seconds --- */
    
    public static Thread tInitRst;
    public static Thread tReset;
    public static Thread tRead;
    public static long t1;
    public static long t2;
    public static int dataCount;
    
    private static boolean IGNORE;
    
    /** Creates a new instance of CanteenTMAAccess */
    public CanteenTMAAccess() {
        String usedPort = PstSystemProperty.getValueByName("CANTEEN_TMA_USED_PORT");
        if (usedPort.length() > 0)
            this.usedPort = usedPort;
        CHECK_SWEEP_TIME = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_CHECK_SWEEP_TIME")) > 0;
        int ignoreTime = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_IGNORE_SWEEP_TIME")) * 60 * 1000;
        if (ignoreTime > 0){
            IGNORE_TIME = ignoreTime;} 
    }
    
    public CanteenTMAAccess(String usedPort) {
        if (usedPort.length() > 0)
            this.usedPort = usedPort;
        // System.out.println("USED PORT = " + getUsedPort());
    }
    
    public String getUsedPort() {
        return usedPort;
    }
    
    public void setUsedPort(String usedPort) {
        if (usedPort.length() > 0)
            this.usedPort = usedPort;
    }
    
    public boolean getCheckSweepTime() {
        return CHECK_SWEEP_TIME;
    }
    
    public void setCheckSweepTime(boolean checkSweepTime) {
        this.CHECK_SWEEP_TIME = checkSweepTime;
    }
    
    public int getIgnoreTime() {
        return IGNORE_TIME;
    }
    
    public void setIgnoreTime(int ignoreTime) {
        this.IGNORE_TIME = ignoreTime;
    } 
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public static synchronized Vector executeCommand(int iCommand,
                                                     String TMAMachineNumber,
                                                     String parameter)
    {
        Vector results = new Vector();
        TimeAttendance reader = null;
        try {
            reader = new TimeAttendance();
            reader.setTMAPortName(usedPort);
        } catch (Exception e) {
            System.out.println("Exception when try to instantiate TimeAttendance : " + e.toString());
        }
        status = BUSY;
        switch (iCommand) {
            /* --- this command used to check machine, whether it available or not --- */ 
            case CHECK_MACHINE : 
                try {
                    reader.execute("*" + TMAMachineNumber + "X");
                } catch (Exception e) {
                    System.err.println("\t[CanteenTMAAccess] Command CHECK_MACHINE error : reader.execute(\"*" + TMAMachineNumber + "X\") : " + e.toString());
                }
                
                tInitRst = new Thread();
                tInitRst.start();
                try {
                    tInitRst.sleep(INTERVAL);
                } catch (Exception e) {
                    System.err.println("\tSet interval for Thread : " + e.toString());
                }
                resultRead = reader.getRes();
                results.addElement(resultRead);
                tInitRst.stop();                
                
                //System.out.println("USED PORT = " + reader.getTMAPortName());
                //System.out.println("CHECKING MACHINE = " + TMAMachineNumber);    
                
                break;
            /* --- process set machine validation --- */
            case VALIDATION:
                try {
                    reader.execute("*" + TMAMachineNumber + "X");
                } catch (Exception ERROR) {
                    System.err.println("\t[CanteenTMAAccess] VALIDATION error : reader.execute(\"*" + TMAMachineNumber + "X\")");
                }
                
                tInitRst = new Thread();
                tInitRst.start();
                try {
                    tInitRst.sleep(INTERVAL);
                } catch (Exception e) {
                    System.err.println("\tSet interval for Thread : " + e.toString());
                }
                resultRead = reader.getRes();
                results.addElement(resultRead);
                tInitRst.stop();
                
                reader.execute("*" + TMAMachineNumber + "S24" + parameter);
                tReset = new Thread();
                tReset.start();
                try {
                    tReset.sleep(INTERVAL);
                } catch (Exception ERROR) {
                    System.err.println("\tSet interval for Thread : " + ERROR.toString());
                }
                tReset.stop();
                break;
            /* --- process reset transaction and employee data on machine --- */
            case RESET :
                try {
                    reader.execute("*" + TMAMachineNumber + "X");
                } catch (Exception ERROR) {
                    System.err.println("\t[CanteenTMAAccess] RESET error : reader.execute(\"*" + TMAMachineNumber + "X\")");                    
                }
                tInitRst = new Thread();
                tInitRst.start();
                try {
                    tInitRst.sleep(INTERVAL);
                } catch (Exception ERROR) {
                    System.err.println("\tSet interval for Thread : " + ERROR.toString());
                }
                resultRead = reader.getRes();
                results.addElement(resultRead);
                tInitRst.stop();
                
                reader.execute("*" + TMAMachineNumber + "RST");
                tReset = new Thread();
                tReset.start();
                try {
                    tReset.sleep(INTERVAL);
                } catch (Exception ERROR) {
                    System.err.println("\tSet interval for Thread : " + ERROR.toString());
                }
                tReset.stop();
                break;
            /* --- process load transaction from machine and store it to database --- */    
            case DOWNLOAD:
                t1 = System.currentTimeMillis();
                resultRemove = "";
                resultRead = "";
                dataCount = 0;
                Date downloadTransTime = new Date();
                try {
                    reader.execute("*" + TMAMachineNumber + "X");
                } catch (Exception e) {
                    System.err.println("\t[CanteenTMAAccess] DOWNLOAD error : reader.execute(\"" + TMAMachineNumber + "X\")");
                }
                
                Thread tInit = new Thread();
                tInit.start();                
                try {
                    tInit.sleep(INTERVAL);
                } catch (Exception e) {
                    System.err.println("\tSet interval for Thread : " + e.toString());
                }
                resultRead = reader.getRes();
                tInit.stop();
                
                if (!resultRead.equals("OFF")) {
                    try {
                        while (!resultRead.equals("FFOVER")) {
                            reader.execute("*" + TMAMachineNumber + "READ");
                            tRead = new Thread();
                            tRead.start();
                            tRead.sleep(INTERVAL);
                            resultRead = reader.getRes();                            
                            //System.out.println("\t[CanteenTMAAccess] " + resultRead + " >> " + resultRead.compareToIgnoreCase("FFI"));
                            
                            long oidVisitation = 0;
                            Date downloadDate = new Date();
                            String barcodeNumber = "";
                            String strNote = "";
                            if (!resultRead.equalsIgnoreCase("FFOVER") && (resultRead.compareToIgnoreCase("FFI") >= 9)) {
                                dataCount++;
                                System.out.println("\tCanteenTMAAccess TMA-" + TMAMachineNumber + " transaction #" + dataCount + " = " + resultRead);
                                String tNumber = resultRead.substring(3, 5);
                                String tFunction = resultRead.substring(6, 7);
                                int tYear = Integer.parseInt(resultRead.substring(7, 9)) + 100;
                                int tMonth = Integer.parseInt(resultRead.substring(9, 11)) - 1;
                                int tDate = Integer.parseInt(resultRead.substring(11, 13));
                                int tHour = Integer.parseInt(resultRead.substring(13, 15));
                                int tMin = Integer.parseInt(resultRead.substring(15, 17));
                                String tBarcode = resultRead.substring(17, resultRead.length());
                                barcodeNumber = tBarcode;
                                // String employeeNumber = resultRead.substring(17, resultRead.length());
                                Date tGregCal = new Date(tYear, tMonth, tDate, tHour, tMin);
                                downloadDate = tGregCal;
                                
                                /* --- Checking transaction download --- */
                                /*System.out.println("TMA Machine Number = " + tNumber);
                                System.out.println("Transaction Status = " + tFunction);
                                System.out.println("Transaction Year   = " + tYear);
                                System.out.println("Transaction Month  = " + tMonth);
                                System.out.println("Transaction Date   = " + tDate);
                                System.out.println("Transaction Hour   = " + tHour);
                                System.out.println("Transaction Minute = " + tMin);
                                System.out.println("Transaction Employee ID = " + tBarcode); */
                                // System.out.println("Transaction Employee Number = " + employeeNumber);
                                
                                // update status of canteen visitor
                                
                                CanteenVisitation canteenVisitation = new CanteenVisitation();                          
                                long employeeOid = PstEmployee.getEmployeeByBarcode(tBarcode);                                
                                IGNORE = false;
                                
                                if (CHECK_SWEEP_TIME) {                                
                                    canteenVisitation = PstCanteenVisitation.getLatestVisitation(employeeOid);
                                    if (canteenVisitation == null) 
                                        IGNORE = false;
                                    else {                                        
                                        Date lastEmpTransTime = canteenVisitation.getVisitationTime();                                    
                                        long transactionTime = tGregCal.getTime();
                                        long lastEmployeeVisitation = lastEmpTransTime.getTime();
                                        long diff = transactionTime - lastEmployeeVisitation;
                                        IGNORE = (diff <= IGNORE_TIME);
                                        if (IGNORE)
                                            System.out.println("Visitation data with employeeOid = " + employeeOid + " is IGNORED ...");                                        
                                        
                                    }
                                    //long employeeOid = PstEmployee.getEmployeeIdByNum(employeeNumber);
                                }
                                
                                //System.out.println("Employee OID = " + employeeOid);
                                //System.out.println("Value Of tFunction(0) = " + tFunction.charAt(0));
                                
                                if (!IGNORE) {
                                    canteenVisitation = new CanteenVisitation();
                                    int canteenVisitationStatus = 0;
                                    
                                    switch (tFunction.charAt(0)) {
                                    case 'A' :
                                        canteenVisitation.setStatus(0);
                                        canteenVisitationStatus = 0;
                                    break;
                                    case 'B' :
                                        canteenVisitation.setStatus(1);
                                        canteenVisitationStatus = 1;
                                    break;
                                    case 'C' :
                                        canteenVisitation.setStatus(2);
                                        canteenVisitationStatus = 2;
                                    break;
                                    case 'D' :
                                        canteenVisitation.setStatus(3);
                                        canteenVisitationStatus = 3;
                                    break;
                                    case 'E' :
                                        canteenVisitation.setStatus(4);
                                        canteenVisitationStatus = 4;
                                    break;
                                    case 'F' :
                                        canteenVisitation.setStatus(5);
                                        canteenVisitationStatus = 5;
                                    break;
                                    default :    
                                    }
                                
                                    // inserting download data to database
                                    canteenVisitation.setEmployeeId(employeeOid);
                                    //canteenVisitation.setEmployeeId(tBarcode);
                                    canteenVisitation.setVisitationTime(tGregCal);
                                    canteenVisitation.setAnalyzed(0);
                                    canteenVisitation.setTransferred(0);
                                    canteenVisitation.setNumOfVisitation(1);
                                    oidVisitation = PstCanteenVisitation.insertExc(canteenVisitation);
                                } else
                                    oidVisitation = employeeOid;
                            }   
                                     
                            tRead.stop();
                            if (oidVisitation != 0) {
                                reader.execute("*" + TMAMachineNumber + "REMOVE");
                                Thread tRemove = new Thread();
                                tRemove.start();
                                tRemove.sleep(INTERVAL);
                                resultRemove = reader.getRes();
                                tRemove.stop();
                            } else if (resultRead.equalsIgnoreCase("FFOVER"))           // if result of reading process is "FFOVER", it's mean there is no more transaction to downloaded
                                resultRemove = "FFOVER";                            
                        }
                    } catch (Exception ERROR) {
                        System.out.println("\t[CanteenTMAAccess] inside -while- exception : " + ERROR.toString());
                    }
                }
                results.add(String.valueOf(dataCount));
                t2 = System.currentTimeMillis();
                System.out.println("\t[CanteenTMAAccess] Download time for TMA" + TMAMachineNumber + " : " + (t2 - t1) + " mili seconds");
                break;
            /* --- process to list or get employee Id that stored in that machine --- */    
            case LIST :
                dataCount = 0;
                try {
                    reader.execute("*" + TMAMachineNumber + "OT");
                } catch (Exception e) {
                    System.out.println("\t*" + TMAMachineNumber + "OT");
                }
                
                tInit = new Thread();
                tInit.start();
                try {
                    tInit.sleep(INTERVAL);
                } catch (Exception ERROR) {
                    System.err.println("\tSet interval for Thread : " + ERROR.toString());
                }
                resultRead = reader.getRes();                
                tInit.stop();
                
                if (resultRead.equals("FFDONE")) {
                    while (!resultRead.equals("FFOVER")) {
                        dataCount++;
                        try {
                            reader.execute("*" + TMAMachineNumber + "OU");
                        } catch (Exception ERROR) {
                            System.out.println("\t*" + TMAMachineNumber + "OU");
                        }
                        
                        tRead = new Thread();
                        tRead.start();                        
                        try {
                            tRead.sleep(INTERVAL);
                        } catch (Exception ERROR) {
                            System.err.println("\tSet interval for Thread : " + ERROR.toString());
                        }
                        resultRead = reader.getRes();
                        System.out.println("\t[CanteenTMAAccess] " + dataCount + " *" + TMAMachineNumber + "OU = " + resultRead);                        
                        tRead.stop();
                        results.add(String.valueOf(resultRead));
                    }
                }
                break;
            /* --- upload employee id and pin number to machine --- */    
            case UPLOAD :
                reader.execute("*" + TMAMachineNumber + "OD" + parameter);
                Thread tDel4 = new Thread();
                tDel4.start();
                try {
                    tDel4.sleep(INTERVAL);
                } catch (Exception ERROR) {
                    System.err.println("\tSet interval for Thread : " + ERROR.toString());
                }
                tDel4.stop();
                reader.execute("*" + TMAMachineNumber + "OI" + parameter);
                Thread tRemove = new Thread();
                tRemove.start();
                try {
                    tRemove.sleep(INTERVAL);
                } catch (Exception ERROR) {
                    System.err.println("\tSet interval for Thread : " + ERROR.toString());
                }
                tRemove.stop();
                break;
            
            /* --- delete user id from machine --- */    
            case DELETE :
                reader.execute("*" + TMAMachineNumber + "OD" + parameter);
                Thread tDel3 = new Thread();
                tDel3.start();
                try {
                    tDel3.sleep(INTERVAL);
                } catch (Exception ERROR) {
                    System.err.println("\tSet interval for Thread : " + ERROR.toString());
                }
                tDel3.stop();
                break;
            /* --- get machine current date and time --- */    
            case GETTIME :
                reader.execute("*" + TMAMachineNumber + "TR");
                Thread tGetTime = new Thread();
                tGetTime.start();
                try {
                    tGetTime.sleep(INTERVAL);
                } catch (Exception ERROR) {
                    System.err.println("\tSet interval for Thread : " + ERROR.toString());
                }
                resultRead = reader.getRes();
                tGetTime.stop();
                results.add(String.valueOf(resultRead));
                break;
            /* --- set machine date and time --- */
            case SETTIME :
                reader.execute("*" + TMAMachineNumber + "TW");
                Thread tSetTime = new Thread();
                tSetTime.start();
                try {
                    tSetTime.sleep(INTERVAL);
                } catch (Exception ERROR) {
                    System.err.println("\tSet interval for Thread : " + ERROR.toString());
                }
                resultRead = reader.getRes();
                tSetTime.stop();
                results.add(String.valueOf(resultRead));
                break;
            default :                
        }
        status = IDLE;
        return results;
    }
}
