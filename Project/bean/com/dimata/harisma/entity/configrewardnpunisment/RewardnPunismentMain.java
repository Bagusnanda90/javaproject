/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.configrewardnpunisment;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author Satrya Ramayu    
 */
public class RewardnPunismentMain extends Entity{
    private String detailNbhNo;
    private long jenisSoId;
    private long locationId;
    private Date dtFromPeriod;
    private Date dtToPeriod;
    private int statusDoc;
    private Date dtCreateDocument;
    private long approvallOne;
    private long approvallTwo;
    private long approvallThree;
    private int countIdx;
    
    private double netSales;
    private double barangHilang;
    private String statusOpname;
    private double nilaiStatusOpname;
    private String createFormMain;
    
    private long entriOpnameId;

    /**
     * @return the detailNbhNo
     */
    public String getDetailNbhNo() {
        return detailNbhNo;
    }

    /**
     * @param detailNbhNo the detailNbhNo to set
     */
    public void setDetailNbhNo(String detailNbhNo) {
        this.detailNbhNo = detailNbhNo;
    }

    /**
     * @return the jenisSoId
     */
    public long getJenisSoId() {
        return jenisSoId;
    }

    /**
     * @param jenisSoId the jenisSoId to set
     */
    public void setJenisSoId(long jenisSoId) {
        this.jenisSoId = jenisSoId;
    }

    /**
     * @return the locationId
     */
    public long getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

   

    /**
     * @return the statusDoc
     */
    public int getStatusDoc() {
        return statusDoc;
    }

    /**
     * @param statusDoc the statusDoc to set
     */
    public void setStatusDoc(int statusDoc) {
        this.statusDoc = statusDoc;
    }

    /**
     * @return the dtCreateDocument
     */
    public Date getDtCreateDocument() {
        return dtCreateDocument;
    }

    /**
     * @param dtCreateDocument the dtCreateDocument to set
     */
    public void setDtCreateDocument(Date dtCreateDocument) {
        this.dtCreateDocument = dtCreateDocument;
    }

    /**
     * @return the approvallOne
     */
    public long getApprovallOne() {
        return approvallOne;
    }

    /**
     * @param approvallOne the approvallOne to set
     */
    public void setApprovallOne(long approvallOne) {
        this.approvallOne = approvallOne;
    }

    /**
     * @return the approvallTwo
     */
    public long getApprovallTwo() {
        return approvallTwo;
    }

    /**
     * @param approvallTwo the approvallTwo to set
     */
    public void setApprovallTwo(long approvallTwo) {
        this.approvallTwo = approvallTwo;
    }

    /**
     * @return the approvallThree
     */
    public long getApprovallThree() {
        return approvallThree;
    }

    /**
     * @param approvallThree the approvallThree to set
     */
    public void setApprovallThree(long approvallThree) {
        this.approvallThree = approvallThree;
    }

    /**
     * @return the countIdx
     */
    public int getCountIdx() {
        return countIdx;
    }

    /**
     * @param countIdx the countIdx to set
     */
    public void setCountIdx(int countIdx) {
        this.countIdx = countIdx;
    }

    /**
     * @return the netSales
     */
    public double getNetSales() {
        return netSales;
    }

    /**
     * @param netSales the netSales to set
     */
    public void setNetSales(double netSales) {
        this.netSales = netSales;
    }

    /**
     * @return the barangHilang
     */
    public double getBarangHilang() {
        return barangHilang;
    }

    /**
     * @param barangHilang the barangHilang to set
     */
    public void setBarangHilang(double barangHilang) {
        this.barangHilang = barangHilang;
    }

    /**
     * @return the statusOpname
     */
    public String getStatusOpname() {
        return statusOpname;
    }

    /**
     * @param statusOpname the statusOpname to set
     */
    public void setStatusOpname(String statusOpname) {
        this.statusOpname = statusOpname;
    }

    /**
     * @return the nilaiStatusOpname
     */
    public double getNilaiStatusOpname() {
        return nilaiStatusOpname;
    }

    /**
     * @param nilaiStatusOpname the nilaiStatusOpname to set
     */
    public void setNilaiStatusOpname(double nilaiStatusOpname) {
        this.nilaiStatusOpname = nilaiStatusOpname;
    }

    /**
     * @return the createFormMain
     */
    public String getCreateFormMain() {
        return createFormMain;
    }

    /**
     * @param createFormMain the createFormMain to set
     */
    public void setCreateFormMain(String createFormMain) {
        this.createFormMain = createFormMain;
    }

    /**
     * @return the dtFromPeriod
     */
    public Date getDtFromPeriod() {
        return dtFromPeriod;
    }

    /**
     * @param dtFromPeriod the dtFromPeriod to set
     */
    public void setDtFromPeriod(Date dtFromPeriod) {
        this.dtFromPeriod = dtFromPeriod;
    }

    /**
     * @return the dtToPeriod
     */
    public Date getDtToPeriod() {
        return dtToPeriod;
    }

    /**
     * @param dtToPeriod the dtToPeriod to set
     */
    public void setDtToPeriod(Date dtToPeriod) {
        this.dtToPeriod = dtToPeriod;
    }

    /**
     * @return the entriOpnameId
     */
    public long getEntriOpnameId() {
        return entriOpnameId;
    }

    /**
     * @param entriOpnameId the entriOpnameId to set
     */
    public void setEntriOpnameId(long entriOpnameId) {
        this.entriOpnameId = entriOpnameId;
    }
}
