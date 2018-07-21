
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.masterdata; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class ResignedReason extends Entity { 

	private String resignedReason = "";
        private String resignedCode = "";

	public String getResignedReason(){ 
		return resignedReason; 
	} 

	public void setResignedReason(String resignedReason){ 
		if ( resignedReason == null ) {
			resignedReason = ""; 
		} 
		this.resignedReason = resignedReason; 
	} 

    /**
     * @return the resignedCode
     */
    public String getResignedCode() {
        return resignedCode;
    }

    /**
     * @param resignedCode the resignedCode to set
     */
    public void setResignedCode(String resignedCode) {
        this.resignedCode = resignedCode;
    }

}
