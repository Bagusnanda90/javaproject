/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.aplikasidesktop.attendance;

import com.dimata.util.Formater;
import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class SessDestopApplication {
    private long employeeId;
    private String namaEmployee;
    private String empNumber;
    private String positionName;
    private String barcodeNumber;
    
    private long scheduleSymbolId;
    private String ketSymbol;
    private String symbol;
    //private String NoMesin;
    private Date schTimeIn;
    private Date schTimeOut;
    private Date schBreakOut;
    private Date schBreakIn;
    
    private Date actTimeIn;
    private Date actTimeOut;
    
    private Date tanggalSchedulenya;

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
     * @return the namaEmployee
     */
    public String getNamaEmployee() {
        return namaEmployee;
    }

    /**
     * @param namaEmployee the namaEmployee to set
     */
    public void setNamaEmployee(String namaEmployee) {
        this.namaEmployee = namaEmployee;
    }

    /**
     * @return the empNumber
     */
    public String getEmpNumber() {
        if(empNumber==null || empNumber.length()==0){
            empNumber="";
        }
        return empNumber;
    }

    /**
     * @param empNumber the empNumber to set
     */
    public void setEmpNumber(String empNumber) {
        this.empNumber = empNumber;
    }

    /**
     * @return the positionName
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * @param positionName the positionName to set
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /**
     * @return the scheduleSymbolId
     */
    public long getScheduleSymbolId() {
        return scheduleSymbolId;
    }

    /**
     * @param scheduleSymbolId the scheduleSymbolId to set
     */
    public void setScheduleSymbolId(long scheduleSymbolId) {
        this.scheduleSymbolId = scheduleSymbolId;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

   

    /**
     * @return the schTimeIn
     */
    public String getSchTimeIn() {
        String dt="";
        if(schTimeIn!=null){
            dt=Formater.formatDate(schTimeIn, "HH:mm");
        }
        return dt;
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
    public String getSchTimeOut() {
        String dt="";
        if(schTimeOut!=null){
            dt=Formater.formatDate(schTimeOut, "HH:mm");
        }
        return dt;
        //return schTimeOut;
    }

    /**
     * @param schTimeOut the schTimeOut to set
     */
    public void setSchTimeOut(Date schTimeOut) {
        this.schTimeOut = schTimeOut;
    }

    /**
     * @return the schBreakOut
     */
    public String getSchBreakOut() {
        //return schBreakOut;
        String dt="";
        if(schBreakOut!=null){
            dt=Formater.formatDate(schBreakOut, "HH:mm");
        }
        return dt;
    }

    /**
     * @param schBreakOut the schBreakOut to set
     */
    public void setSchBreakOut(Date schBreakOut) {
        this.schBreakOut = schBreakOut;
    }

    /**
     * @return the schBreakIn
     */
    public String getSchBreakIn() {
        //return schBreakIn;
        String dt="";
        if(schBreakIn!=null){
            dt=Formater.formatDate(schBreakIn, "HH:mm");
        }
        return dt;
    }

    /**
     * @param schBreakIn the schBreakIn to set
     */
    public void setSchBreakIn(Date schBreakIn) {
        this.schBreakIn = schBreakIn;
    }

    /**
     * @return the ketSymbol
     */
    public String getKetSymbol() {
        return ketSymbol;
    }

    /**
     * @param ketSymbol the ketSymbol to set
     */
    public void setKetSymbol(String ketSymbol) {
        this.ketSymbol = ketSymbol;
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
     * @return the actTimeIn
     */
    public Date getActTimeIn() {
        return actTimeIn;
    }

    /**
     * @param actTimeIn the actTimeIn to set
     */
    public void setActTimeIn(Date actTimeIn) {
        this.actTimeIn = actTimeIn;
    }

    /**
     * @return the actTimeOut
     */
    public Date getActTimeOut() {
        return actTimeOut;
    }

    /**
     * @param actTimeOut the actTimeOut to set
     */
    public void setActTimeOut(Date actTimeOut) {
        this.actTimeOut = actTimeOut;
    }

    /**
     * @return the tanggalSchedulenya
     */
    public Date getTanggalSchedulenya() {
        return tanggalSchedulenya;
    }

    /**
     * @param tanggalSchedulenya the tanggalSchedulenya to set
     */
    public void setTanggalSchedulenya(Date tanggalSchedulenya) {
        this.tanggalSchedulenya = tanggalSchedulenya;
    }

    
    
}
