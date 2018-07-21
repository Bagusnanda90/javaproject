/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine;

import java.io.*;
 
public class PingUs {
 
public static void main(String[] args) {
 
String ip = "192.168.16.108";//args[0];
String pingResult = "";
int nilai = 1;
String pingCmd = "ping " + ip;
 
try {
Runtime r = Runtime.getRuntime();
Process p = r.exec(pingCmd);
 
BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
String inputLine;
while ((inputLine = in.readLine()) != null) {
System.out.println(inputLine);
pingResult += inputLine;
nilai ++;
}
in.close();
}//try
catch (IOException e) {
System.out.println(e);
}
System.out.println(nilai);
}
 
}