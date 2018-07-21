/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.attendance;

import com.dimata.qdep.entity.Entity;

import java.util.Date;
/**
 *
 * @author Tu Roy
 */
public class AlStockExtended extends Entity{
    private long al_stock_extended_id;
    
    private long al_stock_id;
    
    private Date extended_date;
    
    private float extended_qty = 0;
    
    private Date decision_date;
    
    private String note = "";
    
    private String extended_by_pic;
    
    private String approve_by_pic;
    
    
    public void setAlStockExtendedId(long al_stock_extended_id){
        this.al_stock_extended_id=al_stock_extended_id;
    }
    
    public long getAlStockExtendedId(){
        return al_stock_extended_id;
    }
    
    public void setAlStockId(long al_stock_id){
        this.al_stock_id=al_stock_id;
    }
    
    public long getAlStockId(){
        return al_stock_id;
    }
    
    public void setExtendedDate(Date extended_date){
        this.extended_date=extended_date;
    }
    
    public Date getExtendedDate(){
        return extended_date;
    }
    
    public void setExtendedQty(float extended_qty){
        this.extended_qty=extended_qty;
    }
    
    public float getExtendedQty(){
        return extended_qty;
    }
    
    public void setDecisionDate(Date decision_date){
        this.decision_date=decision_date;
    } 
    
    public Date getDecisionDate(){
        return decision_date;
    }
    
    public void setNote(String note){
        this.note=note;
    }
    
    public String getNote(){
        return note;
    }
    
    public void setExtendedByPic(String extended_by_pic){
        this.extended_by_pic=extended_by_pic;
    }
    
    public String getExtendedByPic(){
        return extended_by_pic;
    }
    
    public void setApproveByPic(String approve_by_pic){
        this.approve_by_pic=approve_by_pic;
    }
    
    public String getApproveByPic(){
        return approve_by_pic;
    }
    
}
