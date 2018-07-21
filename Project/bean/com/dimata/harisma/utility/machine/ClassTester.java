/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.machine;

/**
 *
 * @author Administrator
 */
public class ClassTester {

    public ClassTester() {
        
    }
    
    public static void main(String args[]){
        try {
            I_Machine iM1 = MachineBroker.getMachineByIndex(1);
          //  I_Machine iM2 = MachineBroker.getMachineByNumber("01");
            System.out.println(iM1.processCheckMachine());
            iM1.closeConnection();
        } catch (Exception ex) {
            System.out.println("[ERROR]::::::;"+ex.toString());
        } finally {
            System.exit(0);
        }
    }
    
}
