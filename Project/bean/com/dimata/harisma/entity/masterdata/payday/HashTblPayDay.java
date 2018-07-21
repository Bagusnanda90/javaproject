/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.payday;

import java.util.Hashtable;

/**
 *
 * @author Satrya Ramayu
 */
public class HashTblPayDay {

    private Hashtable hashTblPayDayDwOnly = new Hashtable();
    private Hashtable hashTblPayDayAll= new Hashtable();
    
    //private Hashtable hashTblPayDayValueDwOnly;
    //private Hashtable hashTblPayDayValueAll;

    public void addHashTblPayDayValueDwOnly(long categoryId, double value) {
        if (categoryId != 0) {
            hashTblPayDayDwOnly.put("" + categoryId, value);
        }
    }

    
    
    public void addPayDayByPosition(long categoryId,long positionId, double value) {
        if (categoryId != 0 && positionId!=0) {
            hashTblPayDayAll.put(""+categoryId+"_"+positionId, value);
        }
    }
    
    public double getPayDay(long categoryId,long empCategoryDwId,long positionId) {
        double valuex=2500;
        if(categoryId!=0){
            if(empCategoryDwId==categoryId){
                if(hashTblPayDayDwOnly!=null && hashTblPayDayDwOnly.size()>0 && hashTblPayDayDwOnly.containsKey(""+categoryId)){
                    valuex = (Double)hashTblPayDayDwOnly.get(""+categoryId);
                 
                }
            }else{
                 //artinya dia tidak dw
                if(categoryId!=0 && positionId!=0 && hashTblPayDayAll!=null && hashTblPayDayAll.size()>0 && hashTblPayDayAll.containsKey(""+categoryId+"_"+positionId)){
                    valuex = (Double)hashTblPayDayAll.get(""+categoryId+"_"+positionId);
                   
                }
               
            }
            
        }
           
        
        return valuex;
    }
}
