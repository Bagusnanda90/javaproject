
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

public class SrcGuestHandling{ 


	private String guestName = "";

	private String diagnosis = "";

	private Date dateFrom ;

	private Date dateTo;

	private String sortBy = "";

	public String getGuestName(){ return guestName; }

	public void setGuestName(String guestName){
    	if ( guestName == null ) {
			guestName = "";
		} 
		this.guestName = guestName;
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

	public Date getDateFrom(){ return dateFrom; } 

	public void setDateFrom(java.util.Date dateFrom){ this.dateFrom = dateFrom; } 

	public Date getDateTo(){ return dateTo; } 

	public void setDateTo(java.util.Date dateTo){ this.dateTo = dateTo; } 

	public String getSortBy(){ return sortBy; } 

	public void setSortBy(java.lang.String sortBy){ this.sortBy = sortBy; } 

}
