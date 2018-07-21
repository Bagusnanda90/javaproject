
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

package com.dimata.harisma.entity.masterdata; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class CategoryCriteria extends Entity { 

	private long categoryAppraisalId;
	private String criteria = "";
        private String desc1 = "";
        private String desc2 = "";
        private String desc3 = "";
        private String desc4 = "";
        private String desc5 = "";

	public long getCategoryAppraisalId(){ 
		return categoryAppraisalId; 
	} 

	public void setCategoryAppraisalId(long categoryAppraisalId){ 
		this.categoryAppraisalId = categoryAppraisalId; 
	} 

	public String getCriteria(){ 
		return criteria; 
	} 

	public void setCriteria(String criteria){ 
		if ( criteria == null ) {
			criteria = ""; 
		} 
		this.criteria = criteria; 
	} 

	public String getDesc1(){ 
		return desc1; 
	} 

	public void setDesc1(String desc){ 
		if ( desc == null ) {
			this.desc1 = ""; 
		} else
		this.desc1 = desc; 
	} 
        
	public String getDesc2(){ 
		return desc2; 
	} 
	public void setDesc2(String desc){ 
		if ( desc == null ) {
			this.desc2 = ""; 
		} else
		this.desc2 = desc; 
	} 
        
	public String getDesc3(){ 
		return desc3; 
	} 
	public void setDesc3(String desc){ 
		if ( desc == null ) {
			this.desc3 = ""; 
		} else
		this.desc3 = desc; 
	} 
        
	public String getDesc4(){ 
		return desc4; 
	} 
	public void setDesc4(String desc){ 
		if ( desc == null ) {
			this.desc4 = ""; 
		} else
		this.desc4 = desc; 
	} 
        
	public String getDesc5(){ 
		return desc5; 
	} 
	public void setDesc5(String desc){ 
		if ( desc == null ) {
			this.desc5 = ""; 
		} else
		this.desc5 = desc; 
	} 
        
}
