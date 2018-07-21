/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.machine;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public interface I_Machine {
    public static final int ST_IDLE = 0;
    public static final int ST_PRINTING = 1;
    public static final int ST_ERR_MACHINE_OFFLINE = 2;
    public static final int ST_ERR_NO_HOST = 3;
    public static final int ST_ERR_PARAMETER = 4;
    public static final int ST_ERR_NO_MACHINE_FOUND = 5;
    public static final int ST_PAUSED = 6;
    public static final int ST_ERR_PORT_USE = 7;
    
    public static final int USE_CANTEEN = 0;
    public static final int USE_ABSENCE = 1;

    public static final String[] errMessage = 
        {"Idle","Machine Offline","No Host","Paramter Incorrect","No Machine Found","Paused","Port In Use"};
    
    public static final int MODE_VALADATION_USE_PIN = 0;
    public static final int MODE_VALADATION_NOT_USE_PIN = 1;
    
    public static final int COMM_MODE_TCP = 0;
    public static final int COMM_MODE_COM = 1;
    
    public final long INTERVAL = 500;
    
    public static final int COMM_STATUS_OPEN = 0;
    public static final int COMM_STATUS_CLOSE = 1;
        
    public boolean initMachine(MachineConf machineConf);

    public boolean sendCommand(String str);
    
    public void closeConnection();
    
    public String getResult();
    
    /* --- process check machine status, available(OK) or not(ERROR) --- */
    public boolean processCheckMachine();
    
    /* --- process reset data on machine (USER and TRANSACTION) --- */
    public boolean processReset();
    
    /* --- process Validation --- */
    public boolean processValidation(int intMode);
    
    /* --- process download data on machine (TRANSACTION) --- */
    public Vector  processDownloadTransaction();
    
    // process list/read used Id data on machine
    public Vector processList();
    
    // process upload data on machine (USERID)
    public boolean processUpload(EmployeeUp employeeUp);
    
    // process delete data from machine (USERID)
    public boolean processDelete(EmployeeUp employeeUp);
    
    // process get machine time
    public Date processGetTime();
    // process set machine time
    public boolean processSetTime(Date date);
    // process send message
    public boolean processSendMessage(EmployeeUp employeeUp, String message);
    // process clear message
    public boolean processClearMessage();
    
    public boolean processOpenComm(int commStatus);
    
    public String getMachineNumber();    
        
}
