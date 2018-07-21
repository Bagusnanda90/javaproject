
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya
 * @version  	: 01
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.clinic; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class MedExpenseType extends Entity { 

	private String type = "";
        private int showStatus = 0; // 2015-01-10 | Hendra McHen

	public String getType(){ 
		return type; 
	} 

	public void setType(String type){ 
		if ( type == null ) {
			type = ""; 
		} 
		this.type = type; 
	} 

    /**
     * @return the showStatus
     */
    public int getShowStatus() {
        return showStatus;
    }

    /**
     * @param showStatus the showStatus to set
     */
    public void setShowStatus(int showStatus) {
        this.showStatus = showStatus;
    }


}
