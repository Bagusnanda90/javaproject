/*
 * LeaveApplication.java
 *
 * Created on October 27, 2004, 11:51 AM
 */

package com.dimata.harisma.entity.leave;

import com.dimata.qdep.entity.Entity; 
import java.util.Date;

/**
 *
 * @author  gedhy
 */
public class AlUpload extends Entity
{
    Date OpnameDate = new Date();
    long EmployeeId = 0;
    long StockId = 0;
    
    //add by Roy-----------------
    float LastPerEntitle = 0;    
    float LastPerTaken = 0;
    float LastPerExpired = 0;
    //--------------------------
    
    float LastPerToClear = 0;
    float CurrPerTaken = 0;
    int DataStatus = 0;
    private float NewAl = 0;
    private float PrevSysAlBalance = 0;
    private String Note = "";
    private float newQty = 0;
    
    public void setNewQty(float newQty){
        this.newQty=newQty;
    }
    public float getNewQty(){
        return newQty;
    }
    
    public void setNote(String Note){
        this.Note=Note;
    }
    
    public String getNote(){
        return Note;
    }

    public void setStockId(long StockId){
        this.StockId = StockId;
    }
    
    public long getStockId(){
        return StockId;
    }
    
    public void setLastPerEntitle(float LastPerEntitle){
        this.LastPerEntitle = LastPerEntitle;
    }
    
    public float getLastPerEntitle(){
        return LastPerEntitle;
    }
    
    public void setLastPerTaken(float LastPerTaken){
        this.LastPerTaken = LastPerTaken;
    }
    
    public float getLastPerTaken(){
        return LastPerTaken;
    }
    
    public void setLastPerExpired(float LastPerExpired){
        this.LastPerExpired = LastPerExpired;
    }
    
    public float getLastPerExpired(){
        return LastPerExpired;
    }
    
    public float getCurrPerTaken() {
        return CurrPerTaken;
    }

    public void setCurrPerTaken(float CurrPerTaken) {
        this.CurrPerTaken = CurrPerTaken;
    }

    public int getDataStatus() {
        return DataStatus;
    }

    public void setDataStatus(int DataStatus) {
        this.DataStatus = DataStatus;
    }

    public long getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(long EmployeeId) {
        this.EmployeeId = EmployeeId;
    }

    public float getLastPerToClear() {
        return LastPerToClear;
    }

    public void setLastPerToClear(float LastPerToClear) {
        this.LastPerToClear = LastPerToClear;
    }

    public Date getOpnameDate() {
        return OpnameDate;
    }

    public void setOpnameDate(Date OpnameDate) {
        this.OpnameDate = OpnameDate;
    }

    public float getNewAl() {
        return NewAl;
    }

    public void setNewAl(float NewAl) {
        this.NewAl = NewAl;
    }

    public float getPrevSysAlBalance() {
        return PrevSysAlBalance;
    }

    public void setPrevSysAlBalance(float PrevSysAlBalance) {
        this.PrevSysAlBalance = PrevSysAlBalance;
    }

}
