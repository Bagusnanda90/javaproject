/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class InformationHrd extends Entity{
    private String namaInformation;
    private Date dtStartInfo;
    private Date dtEndInfo;

    /**
     * @return the namaInformation
     */
    public String getNamaInformation() {
        if(namaInformation==null){
            return "";
        }
        return namaInformation;
    }

    /**
     * @param namaInformation the namaInformation to set
     */
    public void setNamaInformation(String namaInformation) {
        this.namaInformation = namaInformation;
    }

    /**
     * @return the dtStartInfo
     */
    public Date getDtStartInfo() {
        return dtStartInfo;
    }

    /**
     * @param dtStartInfo the dtStartInfo to set
     */
    public void setDtStartInfo(Date dtStartInfo) {
        this.dtStartInfo = dtStartInfo;
    }

    /**
     * @return the dtEndInfo
     */
    public Date getDtEndInfo() {
        return dtEndInfo;
    }

    /**
     * @param dtEndInfo the dtEndInfo to set
     */
    public void setDtEndInfo(Date dtEndInfo) {
        this.dtEndInfo = dtEndInfo;
    }
}
