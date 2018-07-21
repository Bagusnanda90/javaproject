/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.koefisionposition;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Devin
 */
public class KoefisionPosition extends Entity{
    private long positionId;
    private double nilaiKoefisionOutlet;
    private double nilaiKoefisionDc;
    private String position;

    /**
     * @return the positionId
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

   
    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the nilaiKoefisionOutlet
     */
    public double getNilaiKoefisionOutlet() {
        return nilaiKoefisionOutlet;
    }

    /**
     * @param nilaiKoefisionOutlet the nilaiKoefisionOutlet to set
     */
    public void setNilaiKoefisionOutlet(double nilaiKoefisionOutlet) {
        this.nilaiKoefisionOutlet = nilaiKoefisionOutlet;
    }

    /**
     * @return the nilaiKoefisionDc
     */
    public double getNilaiKoefisionDc() {
        return nilaiKoefisionDc;
    }

    /**
     * @param nilaiKoefisionDc the nilaiKoefisionDc to set
     */
    public void setNilaiKoefisionDc(double nilaiKoefisionDc) {
        this.nilaiKoefisionDc = nilaiKoefisionDc;
    }
}
