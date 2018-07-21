/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

import java.util.Date;

/**
 *
 * @author Devin
 */
public class Pajak {
    private double ptkpDiriSendiri; 
    private double ptkpKawin; 
    private double ptkpKawinAnak1; 
    private double ptkpKawinAnak2;
    private double ptkpKawinAnak3;
    
    private double biayaJabatanPersen; 
    private double biayaJabatanMaksimal; 
    private double nettoPercenKonsultan;      
    private double range1;        
    private  double percen1;
    private  double range2;
    private  double percen2;      
    private  double range3;
    private  double percen3;          
    private double percen4;    
    
    private String NPWPPemotong ="";
    private String NamaPemotong ="";
    private Date   TanggalBuktiPotong = new Date();
    

    private String htmlDetailCalc = "";
    
    /**
     * @return the ptkpDiriSendiri
     */
    public double getPtkpDiriSendiri() {
        return ptkpDiriSendiri;
    }

    /**
     * @param ptkpDiriSendiri the ptkpDiriSendiri to set
     */
    public void setPtkpDiriSendiri(double ptkpDiriSendiri) {
        this.ptkpDiriSendiri = ptkpDiriSendiri;
    }

    /**
     * @return the ptkpKawin
     */
    public double getPtkpKawin() {
        return ptkpKawin;
    }

    /**
     * @param ptkpKawin the ptkpKawin to set
     */
    public void setPtkpKawin(double ptkpKawin) {
        this.ptkpKawin = ptkpKawin;
    }

    /**
     * @return the ptkpKawinAnak1
     */
    public double getPtkpKawinAnak1() {
        return ptkpKawinAnak1;
    }

    /**
     * @param ptkpKawinAnak1 the ptkpKawinAnak1 to set
     */
    public void setPtkpKawinAnak1(double ptkpKawinAnak1) {
        this.ptkpKawinAnak1 = ptkpKawinAnak1;
    }

    /**
     * @return the ptkpKawinAnak2
     */
    public double getPtkpKawinAnak2() {
        return ptkpKawinAnak2;
    }

    /**
     * @param ptkpKawinAnak2 the ptkpKawinAnak2 to set
     */
    public void setPtkpKawinAnak2(double ptkpKawinAnak2) {
        this.ptkpKawinAnak2 = ptkpKawinAnak2;
    }

    /**
     * @return the ptkpKawinAnak3
     */
    public double getPtkpKawinAnak3() {
        return ptkpKawinAnak3;
    }

    /**
     * @param ptkpKawinAnak3 the ptkpKawinAnak3 to set
     */
    public void setPtkpKawinAnak3(double ptkpKawinAnak3) {
        this.ptkpKawinAnak3 = ptkpKawinAnak3;
    }

    /**
     * @return the biayaJabatanPersen
     */
    public double getBiayaJabatanPersen() {
        return biayaJabatanPersen;
    }

    /**
     * @param biayaJabatanPersen the biayaJabatanPersen to set
     */
    public void setBiayaJabatanPersen(double biayaJabatanPersen) {
        this.biayaJabatanPersen = biayaJabatanPersen;
    }

    /**
     * @return the biayaJabatanMaksimal
     */
    public double getBiayaJabatanMaksimal() {
        return biayaJabatanMaksimal;
    }

    /**
     * @param biayaJabatanMaksimal the biayaJabatanMaksimal to set
     */
    public void setBiayaJabatanMaksimal(double biayaJabatanMaksimal) {
        this.biayaJabatanMaksimal = biayaJabatanMaksimal;
    }

    /**
     * @return the nettoPercenKonsultan
     */
    public double getNettoPercenKonsultan() {
        return nettoPercenKonsultan;
    }

    /**
     * @param nettoPercenKonsultan the nettoPercenKonsultan to set
     */
    public void setNettoPercenKonsultan(double nettoPercenKonsultan) {
        this.nettoPercenKonsultan = nettoPercenKonsultan;
    }

    /**
     * @return the range1
     */
    public double getRange1() {
        return range1;
    }

    /**
     * @param range1 the range1 to set
     */
    public void setRange1(double range1) {
        this.range1 = range1;
    }

    /**
     * @return the percen1
     */
    public double getPercen1() {
        return percen1;
    }

    /**
     * @param percen1 the percen1 to set
     */
    public void setPercen1(double percen1) {
        this.percen1 = percen1;
    }

    /**
     * @return the range2
     */
    public double getRange2() {
        return range2;
    }

    /**
     * @param range2 the range2 to set
     */
    public void setRange2(double range2) {
        this.range2 = range2;
    }

    /**
     * @return the percen2
     */
    public double getPercen2() {
        return percen2;
    }

    /**
     * @param percen2 the percen2 to set
     */
    public void setPercen2(double percen2) {
        this.percen2 = percen2;
    }

    /**
     * @return the range3
     */
    public double getRange3() {
        return range3;
    }

    /**
     * @param range3 the range3 to set
     */
    public void setRange3(double range3) {
        this.range3 = range3;
    }

    /**
     * @return the percen3
     */
    public double getPercen3() {
        return percen3;
    }

    /**
     * @param percen3 the percen3 to set
     */
    public void setPercen3(double percen3) {
        this.percen3 = percen3;
    }

   
    /**
     * @return the percen4
     */
    public double getPercen4() {
        return percen4;
    }

    /**
     * @param percen4 the percen4 to set
     */
    public void setPercen4(double percen4) {
        this.percen4 = percen4;
    }

    /**
     * @return the htmlDetailCalc
     */
    public String getHtmlDetailCalc() {
        return htmlDetailCalc;
    }
 
    /**
     * @param htmlDetailCalc the htmlDetailCalc to set
     */
    public void setHtmlDetailCalc(String htmlDetailCalc) {
        this.htmlDetailCalc = htmlDetailCalc;
}

    /**
     * @return the NPWPPemotong
     */
    public String getNPWPPemotong() {
        return NPWPPemotong;
    }

    /**
     * @param NPWPPemotong the NPWPPemotong to set
     */
    public void setNPWPPemotong(String NPWPPemotong) {
        this.NPWPPemotong = NPWPPemotong;
    }

    /**
     * @return the NamaPemotong
     */
    public String getNamaPemotong() {
        return NamaPemotong;
    }

    /**
     * @param NamaPemotong the NamaPemotong to set
     */
    public void setNamaPemotong(String NamaPemotong) {
        this.NamaPemotong = NamaPemotong;
    }

    /**
     * @return the TanggalBuktiPotong
     */
    public Date getTanggalBuktiPotong() {
        return TanggalBuktiPotong;
    }

    /**
     * @param TanggalBuktiPotong the TanggalBuktiPotong to set
     */
    public void setTanggalBuktiPotong(Date TanggalBuktiPotong) {
        this.TanggalBuktiPotong = TanggalBuktiPotong;
    }

 
}
