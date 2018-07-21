
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

public class SrcEmpSalary{ 
	private String name = "";
	private String empnumber = "";
	private long position;
	private long department;
	private long level;
	private double currtotalfrom;
	private double currtotalto;
	private double newtotalfrom;
	private double newtotalto;
    private Date commDateFrom;
    private Date commDateTo;
    private int orderBy;

	public String getName(){ 
		return name; 
	} 

	public void setName(String name){ 
		if ( name == null ) {
			name = ""; 
		} 
		this.name = name; 
	} 

	public String getEmpnumber(){ 
		return empnumber; 
	} 

	public void setEmpnumber(String empnumber){ 
		if ( empnumber == null ) {
			empnumber = ""; 
		} 
		this.empnumber = empnumber; 
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

	public long getLevel(){ 
		return level; 
	} 

	public void setLevel(long level){ 
		this.level = level; 
	} 

	public double getCurrtotalfrom(){ 
		return currtotalfrom; 
	} 

	public void setCurrtotalfrom(double currtotalfrom){ 
		this.currtotalfrom = currtotalfrom; 
	} 

	public double getCurrtotalto(){ 
		return currtotalto; 
	} 

	public void setCurrtotalto(double currtotalto){ 
		this.currtotalto = currtotalto; 
	} 

	public double getNewtotalfrom(){ 
		return newtotalfrom; 
	} 

	public void setNewtotalfrom(double newtotalfrom){ 
		this.newtotalfrom = newtotalfrom; 
	} 

	public double getNewtotalto(){ 
		return newtotalto; 
	} 

	public void setNewtotalto(double newtotalto){ 
		this.newtotalto = newtotalto; 
	} 

    public Date getCommDateFrom(){ return commDateFrom; }

    public void setCommDateFrom(Date commDateFrom){ this.commDateFrom = commDateFrom; }

    public Date getCommDateTo(){ return commDateTo; }

    public void setCommDateTo(Date commDateTo){ this.commDateTo = commDateTo; }

    public int getOrderBy(){ return orderBy; }

    public void setOrderBy(int orderBy){ this.orderBy = orderBy; }
}
