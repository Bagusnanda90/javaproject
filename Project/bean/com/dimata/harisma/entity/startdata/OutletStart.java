
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

package com.dimata.harisma.entity.startdata; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class OutletStart extends Entity { 

	private String dep = "";
	private String depName = "";
	private String location = "";
	private String locName = "";

	public String getDep(){ 
		return dep; 
	} 

	public void setDep(String dep){ 
		if ( dep == null ) {
			dep = ""; 
		} 
		this.dep = dep; 
	} 

	public String getDepName(){ 
		return depName; 
	} 

	public void setDepName(String depName){ 
		if ( depName == null ) {
			depName = ""; 
		} 
		this.depName = depName; 
	} 

	public String getLocation(){ 
		return location; 
	} 

	public void setLocation(String location){ 
		if ( location == null ) {
			location = ""; 
		} 
		this.location = location; 
	} 

	public String getLocName(){ 
		return locName; 
	} 

	public void setLocName(String locName){ 
		if ( locName == null ) {
			locName = ""; 
		} 
		this.locName = locName; 
	} 

}
