/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine;

/**
 *
 * @author Dimata 007
 */
public class DBMachineConfig {
            private String ClassName = "";
            private String dsn="";
            private String user ="";
            private String pwd ="";
            private String host ="";
            private String port =""; 
            

    /**
     * @return the ClassName
     */
    public String getClassName() {
        return ClassName;
    }

    /**
     * @param ClassName the ClassName to set
     */
    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the dsn
     */
    public String getDsn() {
        return dsn;
    }

    /**
     * @param dsn the dsn to set
     */
    public void setDsn(String dsn) {
        this.dsn = dsn;
    }

    
}
