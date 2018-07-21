/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.admin;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Gunadi
 */
public class Machine extends Entity{

    private long machineId = 0;
    private String machineName = "";
    private String machineIP = "";
    private int machinePort = 0;
    private int machineComKey = 0;

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineIP() {
        return machineIP;
    }

    public void setMachineIP(String machineIP) {
        this.machineIP = machineIP;
    }

    public int getMachinePort() {
        return machinePort;
    }

    public void setMachinePort(int machinePort) {
        this.machinePort = machinePort;
    }

    public int getMachineComKey() {
        return machineComKey;
    }

    public void setMachineComKey(int machineComKey) {
        this.machineComKey = machineComKey;
    }

    /**
     * @return the machineId
     */
    public long getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the machineId to set
     */
    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }
}
