/*
 * Description : seach espt month entity
 * Date : 2015-02-26
 * Author : H e n d r a - P u t u
 */
package com.dimata.harisma.report;

public class SrcEsptMonth {

    private long companyId = 0;
    private long divisionId = 0;
    private long departmentId = 0;
    private long sectionId = 0;
    private String employeeNum = "";
    private String employeeName = "";
    private String period = "";
    private String masaPajak = "";
    private int tahunPajak = 0;
    private int pembetulan = 0;
    private String npwp = "";
    private int typeForTax = 0;
    private String kodePajak = "";
    private double jumlahBruto = 0;
    private double jumlahPph = 0;

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getMasaPajak() {
        return masaPajak;
    }

    public void setMasaPajak(String masaPajak) {
        this.masaPajak = masaPajak;
    }

    public int getTahunPajak() {
        return tahunPajak;
    }

    public void setTahunPajak(int tahunPajak) {
        this.tahunPajak = tahunPajak;
    }

    public int getPembetulan() {
        return pembetulan;
    }

    public void setPembetulan(int pembetulan) {
        this.pembetulan = pembetulan;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getKodePajak() {
        return kodePajak;
    }

    public void setKodePajak(String kodePajak) {
        this.kodePajak = kodePajak;
    }

    public double getJumlahBruto() {
        return jumlahBruto;
    }

    public void setJumlahBruto(double jumlahBruto) {
        this.jumlahBruto = jumlahBruto;
    }

    public double getJumlahPph() {
        return jumlahPph;
    }

    public void setJumlahPph(double jumlahPph) {
        this.jumlahPph = jumlahPph;
    }

    /**
     * @return the period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * @return the typeForTax
     */
    public int getTypeForTax() {
        return typeForTax;
    }

    /**
     * @param typeForTax the typeForTax to set
     */
    public void setTypeForTax(int typeForTax) {
        this.typeForTax = typeForTax;
    }
}