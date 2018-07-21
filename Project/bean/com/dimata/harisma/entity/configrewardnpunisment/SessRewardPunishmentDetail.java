
/**
 *
 * @author GUSWIK
 */

package com.dimata.harisma.entity.configrewardnpunisment;

import com.dimata.qdep.entity.Entity;

public class SessRewardPunishmentDetail {
    private long rewardnPunismentMainId;
    private long employeeId;
    private long koefisienId;
    private int hariKerja;
    private double total;
    private double beban;
    private double tunai;
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
     * @return the tunai
     */
    public double getTunai() {
        return tunai;
    }

    /**
     * @param tunai the tunai to set
     */
    public void setTunai(double tunai) {
        this.tunai = tunai;
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
