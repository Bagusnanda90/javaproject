/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author	 : Ari_20110930
 * @version	 : -
 */

/*******************************************************************
 * Class Description 	: FamRelation
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Wiweka
 */
/* package java */
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class FamRelation extends Entity{
    private String famRelation = "";
    private int famRelationType = 0;

    /**
     * @return the famRelation
     */
    public String getFamRelation() {
        return famRelation;
    }

    /**
     * @param famRelation the famRelation to set
     */
    public void setFamRelation(String famRelation) {
        this.famRelation = famRelation;
    }

    /**
     * @return the famRelationType
     */
    public int getFamRelationType() {
        return famRelationType;
    }

    /**
     * @param famRelationType the famRelationType to set
     */
    public void setFamRelationType(int famRelationType) {
        this.famRelationType = famRelationType;
    }

   

 
   
    
}
