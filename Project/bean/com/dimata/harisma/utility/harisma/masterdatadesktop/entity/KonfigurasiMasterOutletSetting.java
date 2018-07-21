/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.masterdatadesktop.entity;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Satrya Ramayu
 */
public class KonfigurasiMasterOutletSetting extends Entity{
    private String urlImages;
    private String urlImagesIcon;
    private String urlLetakMysqlExe;
    private String usernameMysql;
    private String passwordMysql;
    private String portMysqlnDbName;
    private String oidPositionKonfig;
    
    private String autoStart;
    private int tambahanBolehAbs;

    /**
     * @return the urlImages
     */
    public String getUrlImages() {
        if(urlImages==null){
            return "";
        }
        return urlImages;
    }

    /**
     * @param urlImages the urlImages to set
     */
    public void setUrlImages(String urlImages) {
        this.urlImages = urlImages;
    }

    /**
     * @return the urlImagesIcon
     */
    public String getUrlImagesIcon() {
        if(urlImagesIcon==null){
            return "";
        }
        return urlImagesIcon;
    }

    /**
     * @param urlImagesIcon the urlImagesIcon to set
     */
    public void setUrlImagesIcon(String urlImagesIcon) {
        this.urlImagesIcon = urlImagesIcon;
    }

    /**
     * @return the urlLetakMysqlExe
     */
    public String getUrlLetakMysqlExe() {
        if(urlLetakMysqlExe==null){
            return "";
        }
        return urlLetakMysqlExe;
    }

    /**
     * @param urlLetakMysqlExe the urlLetakMysqlExe to set
     */
    public void setUrlLetakMysqlExe(String urlLetakMysqlExe) {
        this.urlLetakMysqlExe = urlLetakMysqlExe;
    }

    /**
     * @return the usernameMysql
     */
    public String getUsernameMysql() {
        if(usernameMysql==null){
            return "";
        }
        return usernameMysql;
    }

    /**
     * @param usernameMysql the usernameMysql to set
     */
    public void setUsernameMysql(String usernameMysql) {
        this.usernameMysql = usernameMysql;
    }

    /**
     * @return the passwordMysql
     */
    public String getPasswordMysql() {
        if(passwordMysql==null){
            return "";
        }
        return passwordMysql;
    }

    /**
     * @param passwordMysql the passwordMysql to set
     */
    public void setPasswordMysql(String passwordMysql) {
        this.passwordMysql = passwordMysql;
    }

   
    /**
     * @return the portMysqlnDbName
     */
    public String getPortMysqlnDbName() {
        if(portMysqlnDbName==null){
            return "";
        }
        return portMysqlnDbName;
    }

    /**
     * @param portMysqlnDbName the portMysqlnDbName to set
     */
    public void setPortMysqlnDbName(String portMysqlnDbName) {
        this.portMysqlnDbName = portMysqlnDbName;
    }

    /**
     * @return the oidPositionKonfig
     */
    public String getOidPositionKonfig() {
        return oidPositionKonfig;
    }

    /**
     * @param oidPositionKonfig the oidPositionKonfig to set
     */
    public void setOidPositionKonfig(String oidPositionKonfig) {
        this.oidPositionKonfig = oidPositionKonfig;
    }

    /**
     * @return the tambahanBolehAbs
     */
    public int getTambahanBolehAbs() {
        return tambahanBolehAbs;
    }

    /**
     * @param tambahanBolehAbs the tambahanBolehAbs to set
     */
    public void setTambahanBolehAbs(int tambahanBolehAbs) {
        this.tambahanBolehAbs = tambahanBolehAbs;
    }

    /**
     * @return the autoStart
     */
    public String getAutoStart() {
        return autoStart;
    }

    /**
     * @param autoStart the autoStart to set
     */
    public void setAutoStart(String autoStart) {
        this.autoStart = autoStart;
    }

    
}
