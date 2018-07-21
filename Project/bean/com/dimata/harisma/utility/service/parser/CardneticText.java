/*
 * CardneticCard.java
 *
 * Created on May 20, 2006, 10:36 AM
 */

package com.dimata.harisma.utility.service.parser;

import java.util.*;
import com.dimata.util.*;
import com.dimata.system.entity.system.*;

/**
 *
 * @author  darmasusila
 */
public class CardneticText {
    
    public String dateFormat = "yyyyMMddhhmm";
    public int dateIdxNum = 12;
    public int idIdxNum = 12;
    public int typeIdxNum = 1;
    public int swapIn = 9;
    public int swapOut = 8;
    public String swapp = "";
    
    /** Creates a new instance of CardneticCard */
    public CardneticText(String swapp) {
        this.swapp = swapp;
        this.dateFormat = PstSystemProperty.getValueByName("CARDNETIC_TEXT_DATE_FORMAT");
        this.dateIdxNum = Integer.parseInt(PstSystemProperty.getValueByName("CARDNETIC_TEXT_DATE_IDX_NUM"));
        this.idIdxNum = Integer.parseInt(PstSystemProperty.getValueByName("CARDNETIC_TEXT_ID_IDX_NUM"));
        this.typeIdxNum = Integer.parseInt(PstSystemProperty.getValueByName("CARDNETIC_TEXT_TYPE_IDX_NUM"));
    }
    
    public Date getSwappingDate(){
        if(swapp!=null && swapp.length()>0){
            String s = swapp.substring(0, dateIdxNum);
            return Formater.formatDate(s, dateFormat);
        }
        return null;
    }
    
    public String getSwappingId(){
        if(swapp!=null && swapp.length()>0){
            String s = swapp.substring(dateIdxNum, dateIdxNum+idIdxNum);
            return s;
        }
        return "";
    }
    
    public int getSwappingType(){
        if(swapp!=null && swapp.length()>0){
            String s = swapp.substring(dateIdxNum+idIdxNum, dateIdxNum+idIdxNum+typeIdxNum);
            return Integer.parseInt(s);
        }
        return -1;
    }

}
