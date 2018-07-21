
/* Created on 	:  20 April 2015 [time] AM/PM
 *
 * @author  	:  Priska_20150420
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: ArApCreditor
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Priska
 */

/* package java */
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class ArApCreditor extends Entity {
    private String creditorName = "";
    private String description = "";

    /**
     * @return the creditorName
     */
    public String getCreditorName() {
        return creditorName;
    }

    /**
     * @param creditorName the creditorName to set
     */
    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the creditorName
     */

}
