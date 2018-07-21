/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Gunadi
 */
public class Finger {
    
    public static String executeThroughSocket(int portNo, String portAddress, String template, long employeeId, String fingerType, int type) throws IOException {

    StringBuilder responseString = new StringBuilder();

    PrintWriter writer = null;
    BufferedReader bufferedReader = null;
    Socket clientSocket = null;

    try {
        
        Employee emp = new Employee();
        try {
            emp = PstEmployee.fetchExc(employeeId);
        } catch (Exception exc){}
        
        String pin="";
        if (!emp.getBarcodeNumber().equals("") && !emp.getBarcodeNumber().equals("null")){
            pin = emp.getBarcodeNumber();
        } else {
            pin = emp.getEmployeeNum();
        }
        
        clientSocket = new Socket(portAddress, portNo);
        if (!clientSocket.isConnected())
            throw new SocketException("Could not connect to Socket");

        clientSocket.setKeepAlive(true);
        String request = "";
        if (type == 2){
            request = "<GetUserTemplate><ArgComKey xsi:type=\"xsd:integer\">0</ArgComKey><Arg><PIN xsi:type=\"xsd:integer\">"+pin+"</PIN><FingerID xsi:type=\"xsd:integer\">All</FingerID></Arg></GetUserTemplate>";
        } if (type == 0){
            request = "<SetUserInfo><ArgComKey xsi:type=\"xsd:integer\">0</ArgComKey><Arg><PIN xsi:type=\"xsd:integer\">"+pin+"</PIN><Name>"+emp.getFullName()+"</Name></Arg></SetUserInfo>";
        } if (type == 1){
            request = "<SetUserTemplate><ArgComKey xsi:type=\"xsd:integer\">0</ArgComKey><Arg><PIN xsi:type=\"xsd:integer\">"+pin+"</PIN><FingerID xsi:type=\"xsd:integer\">"+fingerType+"</FingerID><Size>"+template.length()+"</Size><Valid>1</Valid><Template>"+template+"</Template></Arg></SetUserTemplate>";
        } if (type == 4){
            request = "<DeleteTemplate><ArgComKey xsi:type=\"xsd:integer\">0</ArgComKey><Arg><PIN xsi:type=\"xsd:integer\">"+pin+"</PIN></Arg></DeleteTemplate>";
        } if (type == 3){
            request = "<DeleteUser><ArgComKey xsi:type=\"xsd:integer\">0</ArgComKey><Arg><PIN xsi:type=\"xsd:integer\">"+pin+"</PIN></Arg></DeleteUser>";
        }
        String newLine = "\r\n";
        writer = new PrintWriter(clientSocket.getOutputStream(), true);
        //writer.println(command);
        writer.write("POST /iWsService HTTP/1.0"+newLine);
        writer.write("Content-Type: text/xml"+newLine); 
        writer.write("Content-Length: "+request.length()+newLine+newLine);   
        writer.write(request+newLine);  
        writer.flush();

        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            responseString.append(str+" ");
        }

    } finally {
        if (writer != null)
            writer.close();
        if (bufferedReader != null)
            bufferedReader.close();
        if (clientSocket != null)
            clientSocket.close();
    }
    return responseString.toString();
}
    
}
