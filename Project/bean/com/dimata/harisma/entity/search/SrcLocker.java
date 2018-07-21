
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

package com.dimata.harisma.entity.search; 
 
/* package java */ 
import java.util.Date;

public class SrcLocker{ 

	private long location;
	private String lockernumber = "";
	private String lockerkey = "";
	private String sparekey;
	private long condition;

	public long getLocation(){ 
		return location; 
	} 

	public void setLocation(long location){ 
		this.location = location; 
	} 

	public String getLockernumber(){ 
		return lockernumber; 
	} 

	public void setLockernumber(String lockernumber){ 
		if ( lockernumber == null ) {
			lockernumber = ""; 
		} 
		this.lockernumber = lockernumber; 
	} 

	public String getLockerkey(){ 
		return lockerkey; 
	} 

	public void setLockerkey(String lockerkey){ 
		if ( lockerkey == null ) {
			lockerkey = ""; 
		} 
		this.lockerkey = lockerkey; 
	} 

	public String getSparekey(){ 
		return sparekey; 
	} 

	public void setSparekey(String sparekey){ 
		if ( sparekey == null ) {
			sparekey = ""; 
		} 
		this.sparekey = sparekey; 
	} 

	public long getCondition(){ 
		return condition; 
	} 

	public void setCondition(long condition){ 
		this.condition = condition; 
	} 

}
