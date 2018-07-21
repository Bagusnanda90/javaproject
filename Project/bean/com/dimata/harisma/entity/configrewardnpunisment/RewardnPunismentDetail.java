/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.configrewardnpunisment;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Satrya Ramayu
 */
public class RewardnPunismentDetail extends Entity{
    private long rewardnPunismentMainId;
    private long employeeId;
    private long koefisienId;
    private int hariKerja;
    private double total;
    private double beban;
    private double Tunai;
    private int lamamasacicilan;
    private int adjusment;

    /**
     * @return the rewardnPunismentMainId
     */
    public long getRewardnPunismentMainId() {
        return rewardnPunismentMainId;
    }

    /**
     * @param rewardnPunismentMainId the rewardnPunismentMainId to set
     */
    public void setRewardnPunismentMainId(long rewardnPunismentMainId) {
        this.rewardnPunismentMainId = rewardnPunismentMainId;
    }

    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the koefisienId
     */
    public long getKoefisienId() {
        return koefisienId;
    }

    /**
     * @param koefisienId the koefisienId to set
     */
    public void setKoefisienId(long koefisienId) {
        this.koefisienId = koefisienId;
    }

    /**
     * @return the hariKerja
     */
    public int getHariKerja() {
        return hariKerja;
    }

    /**
     * @param hariKerja the hariKerja to set
     */
    public void setHariKerja(int hariKerja) {
        this.hariKerja = hariKerja;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @return the beban
     */
    public double getBeban() {
        return beban;
    }

    /**
     * @param beban the beban to set
     */
    public void setBeban(double beban) {
        this.beban = beban;
    }

    /**
     * @return the Tunai
     */
    public double getTunai() {
        return Tunai;
    }

    /**
     * @param Tunai the Tunai to set
     */
    public void setTunai(double Tunai) {
        this.Tunai = Tunai;
    }

    /**
     * @return the lamamasacicilan
     */
    public int getLamamasacicilan() {
        return lamamasacicilan;
    }

    /**
     * @param lamamasacicilan the lamamasacicilan to set
     */
    public void setLamamasacicilan(int lamamasacicilan) {
        this.lamamasacicilan = lamamasacicilan;
    }

    /**
     * @return the adjusment
     */
    public int getAdjusment() {
        return adjusment;
    }

    /**
     * @param adjusment the adjusment to set
     */
    public void setAdjusment(int adjusment) {
        this.adjusment = adjusment;
    }
}
