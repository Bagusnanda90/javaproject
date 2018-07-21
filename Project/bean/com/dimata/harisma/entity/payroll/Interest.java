
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

package com.dimata.hanoman.entity.masterdata;
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class Interest extends Entity { 


	public long getCustomerId(){ 
		return this.getOID(0); 
	} 

	public void setCustomerId(long customerId){ 
		this.setOID(0, customerId); 
	} 

	public long getMasterTypeId(){ 
		return this.getOID(1); 
	} 

	public void setMasterTypeId(long masterTypeId){ 
		this.setOID(1, masterTypeId); 
	} 

}
