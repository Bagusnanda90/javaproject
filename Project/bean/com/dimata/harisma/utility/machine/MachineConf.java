/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.machine;

/**
 *
 * @author Artha
 */
public class MachineConf {
    private int machineIndex;
    private String machineName;
    private String machineDrvClassName;
    private String machinePort;
    private String machineIp;
    private int machineStatus=I_Machine.ST_IDLE;
    private int machineUse=I_Machine.USE_ABSENCE;
    private int machineCommMode=I_Machine.COMM_MODE_COM;
    private String machineNumber = "01";
    private javax.swing.JProgressBar jProgressBar=null;
    public void setProgreeBar(javax.swing.JProgressBar jProgressBar){
        this.jProgressBar = jProgressBar;
    }
    public String getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(String machineNumber) {
        this.machineNumber = machineNumber;
    }

    
    
    public int getMachineCommMode() {
        return machineCommMode;
    }

    public void setMachineCommMode(int machineCommMode) {
        this.machineCommMode = machineCommMode;
    }
    
    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }

    public int getMachineUse() {
        return machineUse;
    }

    public void setMachineUse(int machineUse) {
        this.machineUse = machineUse;
    }
    
    public String getMachineDrvClassName() {
        return machineDrvClassName;
    }

    public void setMachineDrvClassName(String machineDrvClassName) {
        this.machineDrvClassName = machineDrvClassName;
    }

    public int getMachineIndex() {
        return machineIndex;
    }

    public void setMachineIndex(int machineIndex) {
        this.machineIndex = machineIndex;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachinePort() {
        return machinePort;
    }

    public void setMachinePort(String machinePort) {
        this.machinePort = machinePort;
    }

    public int getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(int machineStatus) {
        this.machineStatus = machineStatus;
    }
    
    
}
