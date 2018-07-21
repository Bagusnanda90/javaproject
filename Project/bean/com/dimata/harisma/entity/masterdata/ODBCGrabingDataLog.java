
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

public class ODBCGrabingDataLog extends Entity { 

	private Date date;
        private String status;

        public java.util.Date getDate() {
            return date;
        }        
	
        
        public void setDate(java.util.Date date) {
            this.date = date;
        }    
        
       
        public String getStatus() {
            return status;
        }        
        
       
        public void setStatus(String status) {
            if(status==null){
                status ="";
            }
            this.status = status;
        }        

}
