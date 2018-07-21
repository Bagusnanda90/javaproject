
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.logrpt; 
 
/* package java */ 

/* package qdep */
import com.dimata.qdep.entity.*;

public class LogPasalKhusus extends Entity { 

  private long pasalUmumId;
  private String pasalKhusus = "";

    public long getPasalUmumId() {
        return pasalUmumId;
    }

    public void setPasalUmumId(long pasalUmumId) {
        this.pasalUmumId = pasalUmumId;
    }

    public String getPasalKhusus() {
        return pasalKhusus;
    }

    public void setPasalKhusus(String pasalKhusus) {
        this.pasalKhusus = pasalKhusus;
    }

}
