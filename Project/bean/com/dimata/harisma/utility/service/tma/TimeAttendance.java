package com.dimata.harisma.utility.service.tma;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import javax.comm.CommPortIdentifier;
import javax.comm.CommPortOwnershipListener;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;



public class TimeAttendance implements SerialPortEventListener, CommPortOwnershipListener {
    
    static CommPortIdentifier portId;
    static Enumeration portList;
    static InputStream inputStream;
    static SerialPort serialPort;
    static OutputStream outputStream;
    String messageString = "*01NOT";
    private static String TMAPortName = "COM1";     /* --- added by rusdianta --- */
    static String cmd;
    static String res;
    
    static boolean portStatus = false;    
    
    public TimeAttendance() 
    {
    }
    
    public boolean getPortStatus() {
        return portStatus;
    }
    
    public void setPortStatus(boolean status) {
        portStatus = status;
    }

    public String getRes() {
        return res;
    }
    
    public SerialPort getSerialPort() {
        return serialPort;
    }
    
    /* --- added by rusdianta --- */    
    public String getTMAPortName() {
        return TMAPortName;
    }
    
    public void setTMAPortName(String TMAPortName) {
        if (TMAPortName.length() > 0)
            this.TMAPortName = TMAPortName;
        else
            this.TMAPortName = "COM1";          /* --- set with default used port --- */
    }
    /* ------------------------------------------- */
    
    public static void execute(String command) {
        cmd = command;
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)               
                //if (portId.getName().equals("COM1")) {                
                if (portId.getName().equals(TMAPortName)) {                  /* --- modified by rusdianta --- */
                    TimeAttendance reader = new TimeAttendance(command);
                }
        }
    }

    public TimeAttendance(String command) {
        if (command.length() > 0) {
            messageString = command;
        }
         
        if (portStatus == false)
            try {
                serialPort = (SerialPort) portId.open("TMA3", 2000);
                portStatus = true;
            } catch (PortInUseException e) { 
                System.err.println("PortInUseException : " + e); 
            }
        
        try {
            serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
        }
        
        try {
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {
        }
        
        try {
            outputStream.write((messageString + "\r").getBytes());
        } catch (IOException e) { 
            System.err.println("IOException 2 : " + e);
        }

	try {
            serialPort.addEventListener(this);
	} catch (TooManyListenersException e) {
        }
        
        serialPort.notifyOnDataAvailable(true);
        serialPort.notifyOnBreakInterrupt(true);
        
	try {
	    serialPort.enableReceiveTimeout(30);
	} catch (UnsupportedCommOperationException e) {
        }
        
        portId.addPortOwnershipListener(this);
    }
    
    /* --- added by rusdianta --- */
     
    public TimeAttendance(String TMAPortName,
                          String command)
    {
        if (TMAPortName.length() > 0)
            this.TMAPortName = TMAPortName;        
        if (command.length() > 0) 
            messageString = command;        
        
        if (portStatus == false)
            try {
                serialPort = (SerialPort) portId.open("TMA3", 2000);
                portStatus = true;
            } catch (PortInUseException e) {
                System.err.println("Port In Use Exception : " + e);
            }
        
        try {
            serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {            
        }
        
        try {
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {
        }
        
        try {
            outputStream.write((messageString + "\r").getBytes());
        } catch (IOException e) {
            System.out.println("IOException 2 : " + e);
        }
        
        try {
            serialPort.addEventListener(this);            
        } catch (TooManyListenersException e) {
        }
        
        serialPort.notifyOnDataAvailable(true);
        serialPort.notifyOnBreakInterrupt(true);
        
        try {
            serialPort.enableReceiveTimeout(30);
        } catch (UnsupportedCommOperationException e) {
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
                String result = new String(inputBuffer);
                res = result.substring(0, result.length() - 1);
                break;
        }
    }

    public void closeConnection() {
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
            res = "OFF";            
            closeConnection();
	}
    }
    
    public static void disconnect() {
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
                //if (portId.getName().equals("COM1")) {                
                if (portId.getName().equals(TMAPortName)) {         /* --- modified by rusdianta --- */
                    TimeAttendance reader = new TimeAttendance("");
                    //System.out.println("portId="+portId);
                    //System.out.println(".:: Close PORT : " + portId.getName());
                    reader.closeConnection();
                }
        }
    }

    /*public static void main(String[] args) {
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                //if (portId.getName().equals("COM1")) {                
                if (portId.getName().equals(TMAPortName)) {         /* --- modified by rusdianta --- */
                   /* TimeAttendance reader = new TimeAttendance("");
                    System.out.println("portId = " + portId);
                    reader.closeConnection();
                }
            }
        }
    } */

}
