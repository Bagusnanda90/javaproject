/*
 * PayBanks.java
 *
 * Created on March 31, 2007, 10:29 AM
 */

package com.dimata.harisma.entity.payroll;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  autami
 */
public class PayBanks extends Entity{
    
    /**
     * Holds value of property bankName.
     */
    private String bankName="";
    
    /**
     * Holds value of property branch.
     */
    private String bankBranch="";
    
    /**
     * Holds value of property address.
     */
    private String bankAddress="";
    
    /**
     * Holds value of property swiftCode.
     */
    private String swiftCode="";
    
    /**
     * Holds value of property telp.
     */
    private String bankTelp="";
    
    /**
     * Holds value of property fax.
     */
    private String bankFax="";
    
    /** Creates a new instance of PayBanks */
    public PayBanks() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    /**
     * Getter for property bankName.
     * @return Value of property bankName.
     */
    public String getBankName() {
        return this.bankName;
    }
    
    /**
     * Setter for property bankName.
     * @param bankName New value of property bankName.
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    /**
     * Getter for property branch.
     * @return Value of property branch.
     */
    public String getBankBranch() {
        return this.bankBranch;
    }
    
    /** 
     * Setter for property branch.
     * @param branch New value of property branch.
     */
    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }
    
    /**
     * Getter for property address.
     * @return Value of property address.
     */
    public String getBankAddress() {
        return this.bankAddress;
    }
    
    /**
     * Setter for property address.
     * @param address New value of property address.
     */
    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }
    
    /**
     * Getter for property swiftCode.
     * @return Value of property swiftCode.
     */
    public String getSwiftCode() {
        return this.swiftCode;
    }
    
    /**
     * Setter for property swiftCode.
     * @param swiftCode New value of property swiftCode.
     */
    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
    
    /**
     * Getter for property telp.
     * @return Value of property telp.
     */
    public String getBankTelp() {
        return this.bankTelp;
    }
    
    /**
     * Setter for property telp.
     * @param telp New value of property telp.
     */
    public void setBankTelp(String bankTelp) {
        this.bankTelp = bankTelp;
    }
    
    /**
     * Getter for property fax.
     * @return Value of property fax.
     */
    public String getBankFax() {
        return this.bankFax;
    }
    
    /**
     * Setter for property fax.
     * @param fax New value of property fax.
     */
    public void setBankFax(String bankFax) {
        this.bankFax = bankFax;
    }
    
}
