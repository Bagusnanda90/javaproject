/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.aplikasidesktop.attendance;

import com.dimata.harisma.entity.employee.Employee;
import java.awt.Color;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.plaf.ColorUIResource;

/**
 *
 * @author Satrya Ramayu
 */
public class SessAplicationDestopAbsensiAttendance {
    private static SessDestopApplication obSessDestopApplication = new SessDestopApplication();
    private static String urlGambarHarisma;
    private static String urlGambarClient;
    private static String logoDimata;
    private static String noStation="01";
    
    private static String btnHome;
    private static String btnDownloadData;
    private static String btnUploadData;
    private static String btnEmployeeManagement;
    private static String btnBakupData;
    private static String btnLogOut;
    private static Color color = new ColorUIResource(Color.WHITE);
    private static String userNameLogin;
    
    private static String urlImages;
    private static String urlImagesIcon;
    private static String urlLocationMysql;
    private static String portnDbName;
    private static String usernameMysql;
    private static String passwordMysql;
 
    private static long oidSettingOutlet;
    
    private static long oidUserLogin;
    
    private static int tambahanBolehAbs;
    
    //private static String valueSelectedImportFd;
     
    //private static JLabel labelTime;
    //private static JLabel labelDate;

    
    /**
     * @return the urlGambarHarisma
     */
    public static String getUrlGambarHarisma() {
        return urlGambarHarisma;
    }

    /**
     * @return the obSessDestopApplication
     */
    public static SessDestopApplication getObSessDestopApplication() {
        return obSessDestopApplication;
    }

    /**
     * @param aObSessDestopApplication the obSessDestopApplication to set
     */
    public static void setObSessDestopApplication(SessDestopApplication aObSessDestopApplication) {
        obSessDestopApplication = aObSessDestopApplication;
    }

    /**
     * @return the noStation
     */
    public static String getNoStation() {
        return noStation;
    }

    /**
     * @param aNoStation the noStation to set
     */
    public static void setNoStation(String aNoStation) {
        noStation = aNoStation;
    }

    /**
     * @return the btnHome
     */
    public static String getBtnHome() {
        return btnHome;
    }

    /**
     * @param aBtnHome the btnHome to set
     */
    public static void setBtnHome(String aBtnHome) {
        btnHome = aBtnHome;
    }

    /**
     * @return the btnDownloadData
     */
    public static String getBtnDownloadData() {
        return btnDownloadData;
    }

    /**
     * @param aBtnDownloadData the btnDownloadData to set
     */
    public static void setBtnDownloadData(String aBtnDownloadData) {
        btnDownloadData = aBtnDownloadData;
    }

    /**
     * @return the btnUploadData
     */
    public static String getBtnUploadData() {
        return btnUploadData;
    }

    /**
     * @param aBtnUploadData the btnUploadData to set
     */
    public static void setBtnUploadData(String aBtnUploadData) {
        btnUploadData = aBtnUploadData;
    }

    /**
     * @return the btnEmployeeManagement
     */
    public static String getBtnEmployeeManagement() {
        return btnEmployeeManagement;
    }

    /**
     * @param aBtnEmployeeManagement the btnEmployeeManagement to set
     */
    public static void setBtnEmployeeManagement(String aBtnEmployeeManagement) {
        btnEmployeeManagement = aBtnEmployeeManagement;
    }

    /**
     * @return the btnBakupData
     */
    public static String getBtnBakupData() {
        return btnBakupData;
    }

    /**
     * @param aBtnBakupData the btnBakupData to set
     */
    public static void setBtnBakupData(String aBtnBakupData) {
        btnBakupData = aBtnBakupData;
    }

    /**
     * @return the btnLogOut
     */
    public static String getBtnLogOut() {
        return btnLogOut;
    }

    /**
     * @param aBtnLogOut the btnLogOut to set
     */
    public static void setBtnLogOut(String aBtnLogOut) {
        btnLogOut = aBtnLogOut;
    }

    /**
     * @return the color
     */
    public static Color getColor() {
        return color;
    }

    /**
     * @param aColor the color to set
     */
    public static void setColor(Color aColor) {
        color = aColor;
    }

    /**
     * @return the userNameLogin
     */
    public static String getUserNameLogin() {
        return userNameLogin;
    }

    /**
     * @param aUserNameLogin the userNameLogin to set
     */
    public static void setUserNameLogin(String aUserNameLogin) {
        userNameLogin = aUserNameLogin;
    }

    /**
     * @return the urlImages
     */
    public static String getUrlImages() {
        return urlImages;
    }

    /**
     * @param aUrlImages the urlImages to set
     */
    public static void setUrlImages(String aUrlImages) {
        urlImages = aUrlImages;
    }

    /**
     * @return the urlImagesIcon
     */
    public static String getUrlImagesIcon() {
        return urlImagesIcon;
    }

    /**
     * @param aUrlImagesIcon the urlImagesIcon to set
     */
    public static void setUrlImagesIcon(String aUrlImagesIcon) {
        urlImagesIcon = aUrlImagesIcon;
    }

    /**
     * @return the urlLocationMysql
     * koment mysql misalkan memakai windows maka memakai "cmd.exe", "/c" or linux "sh", "-c", 
     */
    public static String getUrlLocationMysql() {
        return urlLocationMysql;
    }

    /**
     * @param aUrlLocationMysql the urlLocationMysql to set
     */
    public static void setUrlLocationMysql(String aUrlLocationMysql) {
        urlLocationMysql = aUrlLocationMysql;
    }

   

    /**
     * @return the usernameMysql
     */
    public static String getUsernameMysql() {
        return usernameMysql;
    }

    /**
     * @param aUsernameMysql the usernameMysql to set
     */
    public static void setUsernameMysql(String aUsernameMysql) {
        usernameMysql = aUsernameMysql;
    }

    /**
     * @return the passwordMysql
     */
    public static String getPasswordMysql() {
        return passwordMysql;
    }

    /**
     * @param aPasswordMysql the passwordMysql to set
     */
    public static void setPasswordMysql(String aPasswordMysql) {
        passwordMysql = aPasswordMysql;
    }

    /**
     * @return the portnDbName
     */
    public static String getPortnDbName() {
        return portnDbName;
    }

    /**
     * @param aPortnDbName the portnDbName to set
     */
    public static void setPortnDbName(String aPortnDbName) {
        portnDbName = aPortnDbName;
    }

    /**
     * @return the oidSettingOutlet
     */
    public static long getOidSettingOutlet() {
        return oidSettingOutlet;
    }

    /**
     * @param aOidSettingOutlet the oidSettingOutlet to set
     */
    public static void setOidSettingOutlet(long aOidSettingOutlet) {
        oidSettingOutlet = aOidSettingOutlet;
    }

    /**
     * @return the oidUserLogin
     */
    public static long getOidUserLogin() {
        return oidUserLogin;
    }

    /**
     * @param aOidUserLogin the oidUserLogin to set
     */
    public static void setOidUserLogin(long aOidUserLogin) {
        oidUserLogin = aOidUserLogin;
    }

    /**
     * @return the tambahanBolehAbs
     */
    public static int getTambahanBolehAbs() {
        return tambahanBolehAbs;
    }

    /**
     * @param aTambahanBolehAbs the tambahanBolehAbs to set
     */
    public static void setTambahanBolehAbs(int aTambahanBolehAbs) {
        tambahanBolehAbs = aTambahanBolehAbs;
    }

    

   

    /**
     * @param urlGambarHarisma the urlGambarHarisma to set
     */
    public void setUrlGambarHarisma(String urlGambarHarisma) {
        this.urlGambarHarisma = urlGambarHarisma;
    }

    /**
     * @return the urlGambarClient
     */
    public static String getUrlGambarClient() {
        return urlGambarClient;
    }

    /**
     * @param urlGambarClient the urlGambarClient to set
     */
    public void setUrlGambarClient(String urlGambarClient) {
        this.urlGambarClient = urlGambarClient;
    }

    /**
     * @return the logoDimata
     */
    public static String getLogoDimata() {
        return logoDimata;
    }

    /**
     * @param logoDimata the logoDimata to set
     */
    public void setLogoDimata(String logoDimata) {
        this.logoDimata = logoDimata;
    }

    
}
