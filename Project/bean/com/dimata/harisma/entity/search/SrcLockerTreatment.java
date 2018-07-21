
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

package com.dimata.harisma.entity.search; 
 
/* package java */ 
import java.util.Date;

public class SrcLockerTreatment{ 


	private long location = 0;

	private Date treatmentdatefrom;// = new Date();

	private Date treatmentdateto;// = new Date();

	private String treatment = "";

	public long getLocation(){ 
		return location; 
	} 

	public void setLocation(long location){ 
		this.location = location; 
	} 

	public Date getTreatmentdatefrom(){ 
		return treatmentdatefrom; 
	} 

	public void setTreatmentdatefrom(Date treatmentdatefrom){ 
		this.treatmentdatefrom = treatmentdatefrom; 
	} 

	public Date getTreatmentdateto(){ 
		return treatmentdateto; 
	} 

	public void setTreatmentdateto(Date treatmentdateto){ 
		this.treatmentdateto = treatmentdateto; 
	} 

	public String getTreatment(){ 
		return treatment; 
	} 

	public void setTreatment(String treatment){ 
		if ( treatment == null ) {
			treatment = ""; 
		} 
		this.treatment = treatment; 
	} 

}
