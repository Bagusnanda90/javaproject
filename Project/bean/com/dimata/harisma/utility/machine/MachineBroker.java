/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.machine;

/**
 *
 * @author Administrator
 */
public class MachineBroker {
    private static String CONFIG_FILE = System.getProperty("java.home") + System.getProperty("file.separator") + "dimata" +
    System.getProperty("file.separator") + "dimata_machine.xml";
    private static boolean configLoaded = false;
    private static DSJ_MachineXML cnfReader;// = new DBConfigReader(CONFIG_FILE);

    public MachineBroker() {
    }
    
    private static void loadConfig() { 
        if(!configLoaded) {            
            //getLog();
            try {
                System.out.println("/*********************************************************/");
                System.out.println("                        LOAD MACHINE CONFIG              ");
                System.out.println("/********************************************************/");
                System.out.println( "MACHINE CONFIG_FILE "+CONFIG_FILE);
                cnfReader = new DSJ_MachineXML(CONFIG_FILE);
                configLoaded = true;
            }
            catch(Exception exception) {
                exception.printStackTrace(System.err);
            }
        }
    }
    
    public static I_Machine getMachineByIndex(int index) throws Exception{
        loadConfig();
        MachineConf machineConf = cnfReader.getMachineConfConfig(index);
        I_Machine i_Machine = (I_Machine) Class.forName(machineConf.getMachineDrvClassName()).newInstance();
        i_Machine.initMachine(machineConf);
        return i_Machine;
    }
    public static I_Machine getMachineByNumber(String number) throws Exception{
        try{
        loadConfig();
        MachineConf machineConf = cnfReader.getMachineConfConfigByNumber(number);
        I_Machine i_Machine = (I_Machine) Class.forName(machineConf.getMachineDrvClassName()).newInstance();
        i_Machine.initMachine(machineConf);
        return i_Machine;
        }catch(Exception exc){
            System.out.println(" ==> EXC : getMachineByNumber "+exc);
        }
        return null;
    }
    
}
