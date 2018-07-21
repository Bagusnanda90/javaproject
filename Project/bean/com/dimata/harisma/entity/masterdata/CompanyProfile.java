/*
 * CompanyProfile.java
 *
 * Created on July 20, 2006, 5:13 PM
 */

package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author  darmasusila
 */
public class CompanyProfile {
    
    /**
     * Holds value of property compName.
     */
    private String compName;
    
    /**
     * Holds value of property compAddress.
     */
    private String compAddress;
    
    /**
     * Holds value of property compTelp.
     */
    private String compTelp;
    
    /**
     * Holds value of property useHeaderImage.
     */
    private boolean useHeaderImage;
    
    /** Creates a new instance of CompanyProfile */
    public CompanyProfile() {
    }
    
    /**
     * Getter for property compName.
     * @return Value of property compName.
     */
    public String getCompName() {
        return this.compName;
    }
    
    /**
     * Setter for property compName.
     * @param compName New value of property compName.
     */
    public void setCompName(String compName) {
        this.compName = compName;
    }
    
    /**
     * Getter for property compAddress.
     * @return Value of property compAddress.
     */
    public String getCompAddress() {
        return this.compAddress;
    }
    
    /**
     * Setter for property compAddress.
     * @param compAddress New value of property compAddress.
     */
    public void setCompAddress(String compAddress) {
        this.compAddress = compAddress;
    }
    
    /**
     * Getter for property compTelp.
     * @return Value of property compTelp.
     */
    public String getCompTelp() {
        return this.compTelp;
    }
    
    /**
     * Setter for property compTelp.
     * @param compTelp New value of property compTelp.
     */
    public void setCompTelp(String compTelp) {
        this.compTelp = compTelp;
    }
    
    /**
     * Getter for property useHeaderImage.
     * @return Value of property useHeaderImage.
     */
    public boolean getUseHeaderImage() {
        return this.useHeaderImage;
    }
    
    /**
     * Setter for property useHeaderImage.
     * @param useHeaderImage New value of property useHeaderImage.
     */
    public void setUseHeaderImage(boolean useHeaderImage) {
        this.useHeaderImage = useHeaderImage;
    }
    
}
