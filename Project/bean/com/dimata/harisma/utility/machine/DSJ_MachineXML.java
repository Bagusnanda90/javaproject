

package com.dimata.harisma.utility.machine;
/*
 * @author artha
 * @version 1.0
 * Copyright : PT. Dimata Sora Jayate , 2008
 *
 *  * List of absence machine will be stored into XML FILE :
 *  - Host Name, IP, Port, RMI object name, Shared status ( 0= not shared, 1=shared)
 * e.g. 
    index;
    name;
    driver; Nama drivernya
    port;
    ip; Ini diisi jika mengunakan mode TCP/IP. Diisikan dengan ip dari mesin
    use; 0 = CANTEEN, 1 ABSENCE
    number; Number at machine
 * <?xml version="1.0"?>
   <machines>
	<machine>
		<index>1</index>
		<name>Canteen1</name>
		<driver>com.dimata.harisma.utility.machine.TDX_2000</driver>	
		<port>COM1</port>
                <ip></ip>
                <use>0</use>
                <number>01</number>
    	</machine>
 	<machine>
		<index>2</index>
		<name>Absence1</name>
		<driver>com.dimata.harisma.utility.machine.TDX_2000</driver>	
		<port>80</port>
                <ip>192.168.16.2</ip>
                <use>1</use>
                <number>02</number>
 	</machine>
  </machines>
 */

//import com.dimata.printman.*;
import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import com.sun.xml.tree.*;
import com.sun.xml.parser.Resolver;
import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;

public class DSJ_MachineXML {
    private String pathConfigFile=System.getProperty("java.home") + System.getProperty("file.separator") + "dimata" +
    System.getProperty("file.separator") + "dimata_machine.xml";
    private Vector listMachine = null;
    public DSJ_MachineXML(String path){
        setPathConfigFile(path);
        try{
            if((listMachine==null) || (listMachine.size()<0)){
                
                loadMachine();
            }
        } catch(Exception exc){
            System.out.println("EXC : getMacineConfig-> loadMachine\n " + exc);
        }
    }
    public DSJ_MachineXML(){
        try{
            if((listMachine==null) || (listMachine.size()<0)){
                
                loadMachine();
            }
        } catch(Exception exc){
            System.out.println("EXC : getMacineConfig-> loadMachine\n " + exc);
        }
    }
    
    public void loadMachine() throws IOException {
        System.out.println("-------------------------[ Start load Machine ]-------------------------");
        InputStream inStream;
        Element currentElement;
        try {
            SAXBuilder builder = new SAXBuilder(); //"
            //get the Configuration Document, with validation
            inStream = new FileInputStream(getPathConfigFile());
            Document doc = builder.build(inStream);
            // get the root element
            Element root = doc.getRootElement();
            if (root == null) {
                System.out.println("NULL ROOT.......................");
                throw new IOException("NULL XML ROOT.......................");
            }
            // get Value Element index
            List dbElement = root.getChildren("machine");
            Iterator iList = dbElement.iterator();
            MachineConf machConf= null;
            listMachine= new Vector();
                
            while (iList.hasNext()) {               
                Element currElement = (Element)iList.next();
                machConf = new MachineConf();
                machConf.setMachineIndex(Integer.parseInt(currElement.getChild("index").getText()));
                machConf.setMachineName(currElement.getChild("name").getText());
                machConf.setMachineNumber(currElement.getChild("number").getText());
                machConf.setMachinePort(currElement.getChild("port").getText());
                machConf.setMachineDrvClassName(currElement.getChild("driver").getText());
                machConf.setMachineIp(currElement.getChild("ip").getText());
                machConf.setMachineCommMode((machConf.getMachineIp().equals("")?I_Machine.COMM_MODE_COM : I_Machine.COMM_MODE_TCP));                
                
                listMachine.add(machConf);
                System.out.println("Load machine : "+machConf.getMachineIndex()+ " "+machConf.getMachineName()+" "+
                                    machConf.getMachinePort()+" "+machConf.getMachineDrvClassName()+" " +
                                    machConf.getMachineIp());
                
            }
            
            
        } catch (Exception exc) {
            throw new IOException(exc.getMessage());
        }
        System.out.println("-------------------------[ finish load Machine ]-------------------------");
    }
    
    public MachineConf getMachineConfConfig(int index){
        if(listMachine==null || listMachine.size()<0){
            try{
                loadMachine();
            } catch(Exception exc){
                System.out.println("EXC : getMacineConfig-> loadMachine\n " + exc);
            }
        }
        if(listMachine!=null && listMachine.size()>0){
            for(int i = 0; i<listMachine.size(); i++){
                MachineConf machineConf = (MachineConf)listMachine.get(i);
                if(machineConf.getMachineIndex()==index)
                    return machineConf;
            }
        }
        return null;
    }
    
    public MachineConf getMachineConfConfigByNumber(String number){
        if(listMachine==null || listMachine.size()<0){
            try{
                loadMachine();
            } catch(Exception exc){
                System.out.println("EXC : getMacineConfig-> loadMachine\n " + exc);
            }
        }
        if(listMachine!=null && listMachine.size()>0){
            for(int i = 0; i<listMachine.size(); i++){
                MachineConf machineConf = (MachineConf)listMachine.get(i);
                if(machineConf.getMachineNumber().equals(number))
                    return machineConf;
            }
        }
        return null;
    }
    
    public String getPathConfigFile(){
        return pathConfigFile;
    }
    
    private void setPathConfigFile(String pathConfigFl){
        pathConfigFile = pathConfigFl;
    }
    
    public Vector getListMachine(){
        if((listMachine==null) || listMachine.size()<0){
            try{
                loadMachine();
            } catch(Exception exc){
                System.out.println("EXC : getMacineConfig-> loadMachine\n " + exc);
            }
        }
        
        return listMachine;
    }   
}
