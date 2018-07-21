
package com.dimata.harisma.utility.machine;
/* 
 * @author artha
 * @version 1.0
 * Copyright : PT. Dimata Sora Jayate , 2008
 */

import java.io.*;
import java.util.*;
import javax.comm.*;

public class OpenPorts implements SerialPortEventListener, CommPortOwnershipListener {
    
    private static CommPortIdentifier portId;
    private static Enumeration portList;
    private static InputStream inputStream;
    private static SerialPort serialPort;
    private static OutputStream outputStream;
    private String messageString = "*01NOT";
    private static String machinePortName = "COM1";     /* --- added by rusdianta --- */
    private static String cmd;
    private static String result;
    private static OpenPorts openPorts;
    
    private static boolean portStatus = false;    
    
    private OpenPorts(){
    }
    
    public static OpenPorts getInstant(){
        if(openPorts==null){
            openPorts = new OpenPorts();
        }
        return openPorts;
    }
    
    public boolean getPortStatus() {
        return portStatus;
    }
    
    public void setPortStatus(boolean status) {
        portStatus = status;
    }

    public String getResult() {
        return result;
    }
    
    public SerialPort getSerialPort() {
        return serialPort;
    }
    
    /* --- added by rusdianta --- */    
    public String getMachinePortName() {
        return machinePortName;
    }
    
    public void setMachinePortName(String machinePortName) {
        if (machinePortName.length() > 0)
            OpenPorts.machinePortName = machinePortName;
        else
            OpenPorts.machinePortName = "COM1";          /* --- set with default used port --- */
    }
    /* ------------------------------------------- */
    
    public static void execute(String command) {
        cmd = command;
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)           
                if (portId.getName().equals(machinePortName)) {                  /* --- modified by rusdianta --- */
                    OpenPorts reader = new OpenPorts(command);
                }
        }
    }

    private OpenPorts(String command) {
        if (command.length() > 0) {
            messageString = command;
        }
         
        if (portStatus == false)
            try {
                serialPort = (SerialPort) portId.open("Machine3", 2000);
                portStatus = true;
            } catch (PortInUseException e) { 
                System.err.println("[ERROR] "+I_Machine.errMessage[I_Machine.ST_ERR_PORT_USE] +" : "+ e);
                result = "";
            }
        
        try {
            serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
            result = "";
        }
        
        try {
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) { result = "";
        }
        
        try {
            outputStream.write((messageString + "\r").getBytes());
        } catch (IOException e) { 
            System.err.println("[ERROR] "+I_Machine.errMessage[I_Machine.ST_ERR_PARAMETER] +" : "+ e);  result = "";
        }

	try {
            serialPort.addEventListener(this);
	} catch (TooManyListenersException e) { result = "";
        }
        
        serialPort.notifyOnDataAvailable(true);
        serialPort.notifyOnBreakInterrupt(true);
        
	try {
	    serialPort.enableReceiveTimeout(30);
	} catch (UnsupportedCommOperationException e) { result = "";
        }
        
        portId.addPortOwnershipListener(this);
    }
    
    /* --- added by rusdianta --- */
     
    private OpenPorts(String machinePortName,String command){
        if (machinePortName.length() > 0)
            this.machinePortName = machinePortName;        
        if (command.length() > 0) 
            messageString = command;        
        
        if (portStatus == false)
            try {
                serialPort = (SerialPort) portId.open("Machine3", 2000);
                portStatus = true;
            } catch (PortInUseException e) {
                System.err.println("[ERROR] "+I_Machine.errMessage[I_Machine.ST_ERR_PORT_USE]+" : " + e); result = "";
            }
        
        try {
            serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {   result = "";          
        }
        
        try {
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) { result = "";
        }
        
        try {
            outputStream.write((messageString + "\r").getBytes());
        } catch (IOException e) {
            System.err.println("[ERROR] "+I_Machine.errMessage[I_Machine.ST_ERR_PARAMETER] +" : "+ e); result = "";
        }
        
        try {
            serialPort.addEventListener(this);            
        } catch (TooManyListenersException e) { result = "";
        }
        
        serialPort.notifyOnDataAvailable(true);
        serialPort.notifyOnBreakInterrupt(true);
        
        try {
            serialPort.enableReceiveTimeout(30);
        } catch (UnsupportedCommOperationException e) { result = "";
        }
        
        portId.addPortOwnershipListener(this);
    }
    /* ----------------------------------- */

    public void serialEvent(SerialPortEvent event) {
        switch(event.getEventType()) {
            case SerialPortEvent.BI:                
                break;
            case SerialPortEvent.OE:                
                break;
            case SerialPortEvent.FE:                
                break;
            case SerialPortEvent.PE:                
                break;
            case SerialPortEvent.CD:                
                break;
            case SerialPortEvent.CTS:                
                break;
            case SerialPortEvent.DSR:                
                break;
            case SerialPortEvent.RI:                
                break;
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:                
                break;
            case SerialPortEvent.DATA_AVAILABLE:                
                StringBuffer inputBuffer = new StringBuffer();
                int newData = 0;
                while (newData != -1) {
                    try {
                        newData = inputStream.read();
                        if (newData == -1)
                            break;
                        if ('\r' == (char)newData)
                            inputBuffer.append('\n');
                        else
                            inputBuffer.append((char)newData);
                    } catch (IOException ex) {
                        System.err.println(ex);
                        return;
                    }
                }
                String resultult = new String(inputBuffer);
                result = resultult.substring(0, resultult.length() - 1);
                break;
        }
    }

    synchronized public void closeConnection() {
	if (serialPort != null) {
	    try {
	    	outputStream.close();
	    	inputStream.close();
	    } catch (IOException e) {
		System.err.println(e);
	    }
	    serialPort.close();
            portId.removePortOwnershipListener(this);
            portStatus = false;            
	}
    }
    
    public void ownershipChange(int param) {
	if (param == CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED) {	    
            result = "OFF";            
            closeConnection();
	}
    }
    
    synchronized public static void disconnect() {
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
                if (portId.getName().equals(machinePortName)) {         /* --- modified by rusdianta --- */
                    OpenPorts reader = new OpenPorts("");
                    reader.closeConnection();
                }
        }
    }
}
