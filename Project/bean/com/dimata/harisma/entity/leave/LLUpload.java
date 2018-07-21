/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author gArtha
 */
public class LLUpload extends Entity{
   
    private Date opnameDate;
    private long employeeId = 0;
    private float llTakenYear1 = 0;    
    private float llTakenYear2 = 0;    
    private float llTakenYear3 = 0;    
    private float llTakenYear4 = 0;    
    private float llTakenYear5 = 0;    
    private int dataStatus = 0;
    private float stock = 0;
    private float lastPerToClearLL = 0;
    private float newLL = 0;    
    private long llStockId = 0;     
    private float llQty = 0;
    private float newLL2 = 0;
    
    public void setLLQty(float llQty){
        this.llQty = llQty;
    }
    
    public float getLLQty(){
        return llQty;
    }
    
    public void setLastPerToClearLL(float lastPerToClearLL){
        this.lastPerToClearLL = lastPerToClearLL;
    }
    
    public float getLastPerToClearLL(){
        return lastPerToClearLL;
    }
    public void setNewLL(float newLL){
        this.newLL = newLL;
    }
    public float getNewLL(){
        return newLL;
    }
    
    public void setLLStockID(long llStockId){
        this.llStockId = llStockId;
    }
    
    public long getLLStockID(){
        return llStockId;
    }

    public int getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public float getLlTakenYear1() {
        return llTakenYear1;
    }

    public void setLlTakenYear1(float llTakenYear1) {
        this.llTakenYear1 = llTakenYear1;
    }

    public float getLlTakenYear2() {
        return llTakenYear2;
    }

    public void setLlTakenYear2(float llTakenYear2) {
       this.llTakenYear2 = llTakenYear2;
    }

    public float getLlTakenYear3() {
        return llTakenYear3;
    }

    public void setLlTakenYear3(float llTakenYear3) {
        this.llTakenYear3 = llTakenYear3;
    }

    public float getLlTakenYear4() {
       return llTakenYear4;
    }

    public void setLlTakenYear4(float llTakenYear4) {
        this.llTakenYear4 = llTakenYear4;
    }

    public float getLlTakenYear5() {
        return llTakenYear5;
    }

    public void setLlTakenYear5(float llTakenYear5) {
        this.llTakenYear5 = llTakenYear5;
    }

    public Date getOpnameDate() {
        return opnameDate;
    }

    public void setOpnameDate(Date opnameDate) {
        this.opnameDate = opnameDate;
    }

    
    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public float getNewLL2() {
        return newLL2;
    }

    public void setNewLL2(float newLL2) {
        this.newLL2 = newLL2;
    }
    
}
