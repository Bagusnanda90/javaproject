/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class TabelDataInformationTransfer {
    //hr_period
    private long informationId;
    private String namaInformation;
    private Date dateStart;
    private Date dateEnd;
    /**
     * @return the informationId
     */
    public long getInformationId() {
        return informationId;
    }

    /**
     * @param informationId the informationId to set
     */
    public void setInformationId(long informationId) {
        this.informationId = informationId;
    }

    /**
     * @return the namaInformation
     */
    public String getNamaInformation() {
        return namaInformation;
    }

    /**
     * @param namaInformation the namaInformation to set
     */
    public void setNamaInformation(String namaInformation) {
        this.namaInformation = namaInformation;
    }

    /**
     * @return the dateStart
     */
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * @param dateStart the dateStart to set
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * @return the dateEnd
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * @param dateEnd the dateEnd to set
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

   
    
    
}
