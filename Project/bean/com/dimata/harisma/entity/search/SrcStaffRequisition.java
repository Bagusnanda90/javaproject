
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

public class SrcStaffRequisition{ 
    private int reqtype;
	private long department;
	private long position;
	private long section;
    private long status;
    private Date reqdatefrom;
    private Date reqdateto;

    public int getReqtype(){ 
        return reqtype; 
    } 

    public void setReqtype(int reqtype){ 
        this.reqtype = reqtype; 
    } 

	public long getDepartment(){ 
		return department; 
	} 

	public void setDepartment(long department){
		this.department = department; 
	} 

	public long getPosition(){ 
		return position; 
	} 

	public void setPosition(long position){
		this.position = position; 
	} 

	public long getSection(){ 
		return section; 
	} 

	public void setSection(long section){
		this.section = section; 
	}

    public long getStatus(){ 
        return status; 
    } 

    public void setStatus(long status){ 
        this.status = status; 
    } 

    public Date getReqdateFrom(){ 
        return reqdatefrom; 
    } 

    public void setReqdateFrom(Date reqdatefrom){ 
        this.reqdatefrom = reqdatefrom; 
    } 

    public Date getReqdateTo(){ 
        return reqdateto; 
    } 

    public void setReqdateTo(Date reqdateto){ 
        this.reqdateto = reqdateto; 
    } 
}
