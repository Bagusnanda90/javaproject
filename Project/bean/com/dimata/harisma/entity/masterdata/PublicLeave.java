/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;
import com.dimata.qdep.entity.Entity;
import java.util.Date;
import java.util.Vector;
/**
 *
 * @author SATRYA Ramayu
 */
public class PublicLeave extends Entity{
    private long publicHolidayId;
    private Date dateLeaveFrom;
    private Date dateLeaveTo;
    private long typeLeave;
    private long empCat;
    private int flagSch;
    private Vector error = new Vector();
    private String err;

    /**
     * @return the publicHolidayId
     */
    public long getPublicHolidayId() {
        return publicHolidayId;
    }

    /**
     * @param publicHolidayId the publicHolidayId to set
     */
    public void setPublicHolidayId(long publicHolidayId) {
        this.publicHolidayId = publicHolidayId;
    }

    /**
     * @return the dateLeaveFrom
     */
    public Date getDateLeaveFrom() {
        return dateLeaveFrom;
    }

    /**
     * @param dateLeaveFrom the dateLeaveFrom to set
     */
    public void setDateLeaveFrom(Date dateLeaveFrom) {
        this.dateLeaveFrom = dateLeaveFrom;
    }

    /**
     * @return the dateLeaveTo
     */
    public Date getDateLeaveTo() {
        return dateLeaveTo;
    }

    /**
     * @param dateLeaveTo the dateLeaveTo to set
     */
    public void setDateLeaveTo(Date dateLeaveTo) {
        this.dateLeaveTo = dateLeaveTo;
    }

    /**
     * @return the typeLeave
     */
    public long getTypeLeave() {
        return typeLeave;
    }

    /**
     * @param typeLeave the typeLeave to set
     */
    public void setTypeLeave(long typeLeave) {
        this.typeLeave = typeLeave;
    }

    /**
     * @return the empCat
     */
    public long getEmpCat() {
        return empCat;
    }

    /**
     * @param empCat the empCat to set
     */
    public void setEmpCat(long empCat) {
        this.empCat = empCat;
    }

    /**
     * @return the flagSch
     */
    public int getFlagSch() {
        return flagSch;
    }

    /**
     * @param flagSch the flagSch to set
     */
    public void setFlagSch(int flagSch) {
        this.flagSch = flagSch;
    }

    /**
     * @return the error
     */
    public Vector getError() { 
        return error;
}

    /**
     * @param error the error to set
     */
    public void setError(Vector error) {
        this.error = error;
    }

    /**
     * @return the err
     */
    public String getErr() {
        return err;
    }

    /**
     * @param err the err to set
     */
    public void setErr(String err) {
        this.err = err;
    }
}
