/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance;

import com.dimata.util.Formater;
import java.util.Date;
import java.util.Formatter;

/**
 *
 * @author Satrya Ramayu
 */
public class SessEmpScheduleAttendance {
    private Date timeIn;
    private Date timeOut;
    private Date timeIn2Nd;
    private Date timeOut2Nd;
    private String fullName;
    private Date schTimeIn;
    private Date schTimeOut;
    private Date schTimeIn2nd;
    private Date schTimeOut2nd;
    private String symbol;
    private long employeeId;
    private String barcodeNumber;
    
    private String employeeNum;
    private String position;
    private String phone;
    private String handphone;
    private String phoneEmg;
    private String alamat;
    private String alamatPermanent;
    private String alamatEmg;
    
    private int statusEmpTrans;
    
    private Date tanggalSchedulenya;

    /**
     * @return the timeIn
     */
    public Date getTimeIn() {
        return timeIn;
    }

    /**
     * @param timeIn the timeIn to set
     */
    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    /**
     * @return the timeOut
     */
    public Date getTimeOut() {
        return timeOut;
    }

    /**
     * @param timeOut the timeOut to set
     */
    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * @return the timeIn2Nd
     */
    public Date getTimeIn2Nd() {
        return timeIn2Nd;
    }

    /**
     * @param timeIn2Nd the timeIn2Nd to set
     */
    public void setTimeIn2Nd(Date timeIn2Nd) {
        this.timeIn2Nd = timeIn2Nd;
    }

    /**
     * @return the timeOut2Nd
     */
    public Date getTimeOut2Nd() {
        return timeOut2Nd;
    }

    /**
     * @param timeOut2Nd the timeOut2Nd to set
     */
    public void setTimeOut2Nd(Date timeOut2Nd) {
        this.timeOut2Nd = timeOut2Nd;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the schTimeIn
     */
    public Date getSchTimeIn() {
        return schTimeIn;
    }

    public String getSschTimeIn() {
        String sDt="";
        if(schTimeIn!=null){
            sDt=Formater.formatDate(schTimeIn, "HH:mm");
        }
        return sDt;
    }
    /**
     * @param schTimeIn the schTimeIn to set
     */
    public void setSchTimeIn(Date schTimeIn) {
        this.schTimeIn = schTimeIn;
    }

    /**
     * @return the schTimeOut
     */
    public Date getSchTimeOut() {
        return schTimeOut;
    }
    
    public String getSschTimeOut() {
        String sDt="";
        if(schTimeOut!=null){
            sDt=Formater.formatDate(schTimeOut, "HH:mm");
        }
        return sDt;
    }

    /**
     * @param schTimeOut the schTimeOut to set
     */
    public void setSchTimeOut(Date schTimeOut) {
        this.schTimeOut = schTimeOut;
    }

    /**
     * @return the schTimeIn2nd
     */
    public Date getSchTimeIn2nd() {
        return schTimeIn2nd;
    }

    /**
     * @param schTimeIn2nd the schTimeIn2nd to set
     */
    public void setSchTimeIn2nd(Date schTimeIn2nd) {
        this.schTimeIn2nd = schTimeIn2nd;
    }

    /**
     * @return the schTimeOut2nd
     */
    public Date getSchTimeOut2nd() {
        return schTimeOut2nd;
    }

    /**
     * @param schTimeOut2nd the schTimeOut2nd to set
     */
    public void setSchTimeOut2nd(Date schTimeOut2nd) {
        this.schTimeOut2nd = schTimeOut2nd;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        if(symbol==null||symbol.length()==0){
            symbol="";
        }
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
     * @return the barcodeNumber
     */
    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    /**
     * @param barcodeNumber the barcodeNumber to set
     */
    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    /**
     * @return the employeeNum
     */
    public String getEmployeeNum() {
        if(employeeNum==null){
            return "";
        }
        return employeeNum;
    }

    /**
     * @param employeeNum the employeeNum to set
     */
    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        if(position==null){
            return "";
        }
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        if(phone==null){
            return "";
        }
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the handphone
     */
    public String getHandphone() {
        if(handphone==null){
            return "";
        }
        return handphone;
    }

    /**
     * @param handphone the handphone to set
     */
    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    /**
     * @return the phoneEmg
     */
    public String getPhoneEmg() {
        if(phoneEmg==null){
            return "";
        }
        return phoneEmg;
    }

    /**
     * @param phoneEmg the phoneEmg to set
     */
    public void setPhoneEmg(String phoneEmg) {
        this.phoneEmg = phoneEmg;
    }

    /**
     * @return the alamat
     */
    public String getAlamat() {
        if(alamat==null){
            return "";
        }
        return alamat;
    }

    /**
     * @param alamat the alamat to set
     */
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    /**
     * @return the alamatPermanent
     */
    public String getAlamatPermanent() {
        if(alamatPermanent==null){
            return "";
        }
        return alamatPermanent;
    }

    /**
     * @param alamatPermanent the alamatPermanent to set
     */
    public void setAlamatPermanent(String alamatPermanent) {
        this.alamatPermanent = alamatPermanent;
    }

    /**
     * @return the alamatEmg
     */
    public String getAlamatEmg() {
        if(alamatEmg==null){
            return "";
        }
        return alamatEmg;
    }

    /**
     * @param alamatEmg the alamatEmg to set
     */
    public void setAlamatEmg(String alamatEmg) {
        this.alamatEmg = alamatEmg;
    }

    /**
     * @return the statusEmpTrans
     */
    public int getStatusEmpTrans() {
        return statusEmpTrans;
    }

    /**
     * @param statusEmpTrans the statusEmpTrans to set
     */
    public void setStatusEmpTrans(int statusEmpTrans) {
        this.statusEmpTrans = statusEmpTrans;
    }

    /**
     * @return the tanggalSchedulenya
     */
    public String getTanggalSchedulenya() {
        if(tanggalSchedulenya!=null){
            String hasil=" Tgl :"+Formater.formatDate(tanggalSchedulenya, "dd-MM-yyy");
            return hasil;
        }
        return null;
    }

    /**
     * @param tanggalSchedulenya the tanggalSchedulenya to set
     */
    public void setTanggalSchedulenya(Date tanggalSchedulenya) {
        this.tanggalSchedulenya = tanggalSchedulenya;
    }
}
