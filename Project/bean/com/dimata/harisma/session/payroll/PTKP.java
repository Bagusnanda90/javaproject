/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;
import java.util.Date;
/**
 *
 * @author Kartika
 */
public class PTKP {
        private Date startDate = new Date(2015-1900, 1-1, 1);
        /* PTKP : 2015 */
        private double PTKP_DIRI_SENDIRI = 36000000.0;// 
        private double PTKP_KAWIN        = 39000000.0;// 
        private double PTKP_KAWIN_ANAK_1 = 42000000.0;// 
        private double PTKP_KAWIN_ANAK_2 = 45000000.0;// 
        private double PTKP_KAWIN_ANAK_3 = 48000000.0;// 
        
        /* PTKP : 2014
        double PTKP_DIRI_SENDIRI = 24300000.0;//    15.840.000.0;
        double PTKP_KAWIN = 26325000.0;//    17.160.000.0;
        double PTKP_KAWIN_ANAK_1 = 28350000.0;//    18.480.000.0;
        double PTKP_KAWIN_ANAK_2 = 30375000.0;//    19.800.000.0;
        double PTKP_KAWIN_ANAK_3 = 32400000.0;//    21.120.000.0;          
        */

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the PTKP_DIRI_SENDIRI
     */
    public double getPTKP_DIRI_SENDIRI() {
        return PTKP_DIRI_SENDIRI;
    }

    /**
     * @param PTKP_DIRI_SENDIRI the PTKP_DIRI_SENDIRI to set
     */
    public void setPTKP_DIRI_SENDIRI(double PTKP_DIRI_SENDIRI) {
        this.PTKP_DIRI_SENDIRI = PTKP_DIRI_SENDIRI;
    }

    /**
     * @return the PTKP_KAWIN
     */
    public double getPTKP_KAWIN() {
        return PTKP_KAWIN;
    }

    /**
     * @param PTKP_KAWIN the PTKP_KAWIN to set
     */
    public void setPTKP_KAWIN(double PTKP_KAWIN) {
        this.PTKP_KAWIN = PTKP_KAWIN;
    }

    /**
     * @return the PTKP_KAWIN_ANAK_1
     */
    public double getPTKP_KAWIN_ANAK_1() {
        return PTKP_KAWIN_ANAK_1;
    }

    /**
     * @param PTKP_KAWIN_ANAK_1 the PTKP_KAWIN_ANAK_1 to set
     */
    public void setPTKP_KAWIN_ANAK_1(double PTKP_KAWIN_ANAK_1) {
        this.PTKP_KAWIN_ANAK_1 = PTKP_KAWIN_ANAK_1;
    }

    /**
     * @return the PTKP_KAWIN_ANAK_2
     */
    public double getPTKP_KAWIN_ANAK_2() {
        return PTKP_KAWIN_ANAK_2;
    }

    /**
     * @param PTKP_KAWIN_ANAK_2 the PTKP_KAWIN_ANAK_2 to set
     */
    public void setPTKP_KAWIN_ANAK_2(double PTKP_KAWIN_ANAK_2) {
        this.PTKP_KAWIN_ANAK_2 = PTKP_KAWIN_ANAK_2;
    }

    /**
     * @return the PTKP_KAWIN_ANAK_3
     */
    public double getPTKP_KAWIN_ANAK_3() {
        return PTKP_KAWIN_ANAK_3;
    }

    /**
     * @param PTKP_KAWIN_ANAK_3 the PTKP_KAWIN_ANAK_3 to set
     */
    public void setPTKP_KAWIN_ANAK_3(double PTKP_KAWIN_ANAK_3) {
        this.PTKP_KAWIN_ANAK_3 = PTKP_KAWIN_ANAK_3;
    }
        
}
