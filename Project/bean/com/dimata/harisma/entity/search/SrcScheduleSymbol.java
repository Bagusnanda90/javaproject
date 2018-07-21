/*
 * SrcScheduleSymbol.java
 *
 * Created on January 5, 2005, 1:31 PM
 */

package com.dimata.harisma.entity.search;

// import core java package
import java.util.*;

/**
 *
 * @author  gedhy
 */
public class SrcScheduleSymbol {
    
    /** Holds value of property schSymbol. */
    private String schSymbol = "";
    
    /** Holds value of property schCategory. */
    private long schCategory = 0;
    
    /** Holds value of property timeInStart. */
    private Date timeInStart;
    
    /** Holds value of property timeInEnd. */
    private Date timeInEnd;
    
    /** Holds value of property timeOutStart. */
    private Date timeOutStart;
    
    /** Holds value of property timeOutEnd. */
    private Date timeOutEnd;
    
    /** Holds value of property selectTimeIn. */
    private boolean selectTimeIn = true;
    
    /** Holds value of property selectTimeOut. */
    private boolean selectTimeOut = true;
    
    /** Creates a new instance of SrcScheduleSymbol */
    public SrcScheduleSymbol() {
    }
    
    /** Getter for property schSymbol.
     * @return Value of property schSymbol.
     *
     */
    public String getSchSymbol() {
        return this.schSymbol;
    }
    
    /** Setter for property schSymbol.
     * @param schSymbol New value of property schSymbol.
     *
     */
    public void setSchSymbol(String schSymbol) {
        this.schSymbol = schSymbol;
    }
    
    /** Getter for property schCategory.
     * @return Value of property schCategory.
     *
     */
    public long getSchCategory() {
        return this.schCategory;
    }
    
    /** Setter for property schCategory.
     * @param schCategory New value of property schCategory.
     *
     */
    public void setSchCategory(long schCategory) {
        this.schCategory = schCategory;
    }
    
    /** Getter for property timeInStart.
     * @return Value of property timeInStart.
     *
     */
    public Date getTimeInStart() {
        return this.timeInStart;
    }
    
    /** Setter for property timeInStart.
     * @param timeInStart New value of property timeInStart.
     *
     */
    public void setTimeInStart(Date timeInStart) {
        this.timeInStart = timeInStart;
    }
    
    /** Getter for property timeInEnd.
     * @return Value of property timeInEnd.
     *
     */
    public Date getTimeInEnd() {
        return this.timeInEnd;
    }
    
    /** Setter for property timeInEnd.
     * @param timeInEnd New value of property timeInEnd.
     *
     */
    public void setTimeInEnd(Date timeInEnd) {
        this.timeInEnd = timeInEnd;
    }
    
    /** Getter for property timeOutStart.
     * @return Value of property timeOutStart.
     *
     */
    public Date getTimeOutStart() {
        return this.timeOutStart;
    }
    
    /** Setter for property timeOutStart.
     * @param timeOutStart New value of property timeOutStart.
     *
     */
    public void setTimeOutStart(Date timeOutStart) {
        this.timeOutStart = timeOutStart;
    }
    
    /** Getter for property timeOutEnd.
     * @return Value of property timeOutEnd.
     *
     */
    public Date getTimeOutEnd() {
        return this.timeOutEnd;
    }
    
    /** Setter for property timeOutEnd.
     * @param timeOutEnd New value of property timeOutEnd.
     *
     */
    public void setTimeOutEnd(Date timeOutEnd) {
        this.timeOutEnd = timeOutEnd;
    }
    
    /** Getter for property selectTimeIn.
     * @return Value of property selectTimeIn.
     *
     */
    public boolean isSelectTimeIn() {
        return this.selectTimeIn;
    }
    
    /** Setter for property selectTimeIn.
     * @param selectTimeIn New value of property selectTimeIn.
     *
     */
    public void setSelectTimeIn(boolean selectTimeIn) {
        this.selectTimeIn = selectTimeIn;
    }
    
    /** Getter for property selectTimeOut.
     * @return Value of property selectTimeOut.
     *
     */
    public boolean isSelectTimeOut() {
        return this.selectTimeOut;
    }
    
    /** Setter for property selectTimeOut.
     * @param selectTimeOut New value of property selectTimeOut.
     *
     */
    public void setSelectTimeOut(boolean selectTimeOut) {
        this.selectTimeOut = selectTimeOut;
    }
    
}
