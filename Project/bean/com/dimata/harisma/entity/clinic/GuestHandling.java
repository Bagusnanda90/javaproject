
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

public class GuestHandling extends Entity { 

	private Date date;
	private String guestName = "";
	private String room = "";
	private String diagnosis = "";
	private String treatment = "";
	private String description = "";

	public Date getDate(){ 
		return date; 
	} 

	public void setDate(Date date){ 
		this.date = date; 
	} 

	public String getGuestName(){ 
		return guestName; 
	} 

	public void setGuestName(String guestName){ 
		if ( guestName == null ) {
			guestName = ""; 
		} 
		this.guestName = guestName; 
	} 

	public String getRoom(){ 
		return room; 
	} 

	public void setRoom(String room){ 
		if ( room == null ) {
			room = ""; 
		} 
		this.room = room; 
	} 

	public String getDiagnosis(){ 
		return diagnosis; 
	} 

	public void setDiagnosis(String diagnosis){ 
		if ( diagnosis == null ) {
			diagnosis = ""; 
		} 
		this.diagnosis = diagnosis; 
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

	public String getDescription(){ 
		return description; 
	} 

	public void setDescription(String description){ 
		if ( description == null ) {
			description = ""; 
		} 
		this.description = description; 
	} 

}
