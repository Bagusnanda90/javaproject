/*
 * SrcSection.java
 *
 * Created on January 5, 2005, 1:30 PM
 */

package com.dimata.harisma.entity.search;

/**
 *
 * @author  gedhy
 */
public class SrcSection {
    
    /** Holds value of property secName. */
    private String secName = "";
    
    /** Holds value of property secDepartment. */
    private long secDepartment = 0;
    
    /** Creates a new instance of SrcSection */
    public SrcSection() {
    }
    
    /** Getter for property secName.
     * @return Value of property secName.
     *
     */
    public String getSecName() {
        return this.secName;
    }
    
    /** Setter for property secName.
     * @param secName New value of property secName.
     *
     */
    public void setSecName(String secName) {
        this.secName = secName;
    }
    
    /** Getter for property secDepartment.
     * @return Value of property secDepartment.
     *
     */
    public long getSecDepartment() {
        return this.secDepartment;
    }
    
    /** Setter for property secDepartment.
     * @param secDepartment New value of property secDepartment.
     *
     */
    public void setSecDepartment(long secDepartment) {
        this.secDepartment = secDepartment;
    }
    
}
