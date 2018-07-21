
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

package com.dimata.harisma.entity.clinic; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class DiseaseType extends Entity { 

	private String diseaseType = "";

	public String getDiseaseType(){ 
		return diseaseType; 
	} 

	public void setDiseaseType(String diseaseType){ 
		if ( diseaseType == null ) {
			diseaseType = ""; 
		} 
		this.diseaseType = diseaseType; 
	} 

}
