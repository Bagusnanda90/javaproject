
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

public class EmployeeStart extends Entity { 

	private String reg = "";
	private String nik = "";
	private String name = "";
	private String address1 = "";
	private String address2 = "";
	private String city = "";
	private String phone;
	private String sex = "";
    private String level = "";
	private String religion = "";
	private double divition;
	private String dep = "";
	private String location = "";
	private String status = "";
	private String position = "";
	private double child;
	private String dob = "";
	private Date start;
    private Date birthDate;

	public String getReg(){
		return reg; 
	} 

	public void setReg(String reg){ 
		if ( reg == null ) {
			reg = ""; 
		} 
		this.reg = reg; 
	} 

	public String getNik(){ 
		return nik; 
	} 

	public void setNik(String nik){ 
		if ( nik == null ) {
			nik = ""; 
		} 
		this.nik = nik; 
	} 

	public String getName(){ 
		return name; 
	} 

	public void setName(String name){ 
		if ( name == null ) {
			name = ""; 
		} 
		this.name = name; 
	} 

	public String getAddress1(){ 
		return address1; 
	} 

	public void setAddress1(String address1){ 
		if ( address1 == null ) {
			address1 = ""; 
		} 
		this.address1 = address1; 
	} 

	public String getAddress2(){ 
		return address2; 
	} 

	public void setAddress2(String address2){ 
		if ( address2 == null ) {
			address2 = ""; 
		} 
		this.address2 = address2; 
	} 

	public String getCity(){ 
		return city; 
	} 

	public void setCity(String city){ 
		if ( city == null ) {
			city = ""; 
		} 
		this.city = city; 
	} 

	public String getPhone(){
		return phone; 
	} 

	public void setPhone(String phone){
		this.phone = phone; 
	} 

	public String getSex(){ 
		return sex; 
	} 

	public void setSex(String sex){ 
		if ( sex == null ) {
			sex = ""; 
		} 
		this.sex = sex; 
	}

    public String getLevel(){
        return level;
    }

    public void setLevel(String level){
        if ( level == null ) {
            level = "";
        }
        this.level = level;
    }

	public String getReligion(){
		return religion; 
	} 

	public void setReligion(String religion){ 
		if ( religion == null ) {
			religion = ""; 
		} 
		this.religion = religion; 
	} 

	public double getDivition(){ 
		return divition; 
	} 

	public void setDivition(double divition){ 
		this.divition = divition; 
	} 

	public String getDep(){ 
		return dep; 
	} 

	public void setDep(String dep){ 
		if ( dep == null ) {
			dep = ""; 
		} 
		this.dep = dep; 
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

	public String getStatus(){ 
		return status; 
	} 

	public void setStatus(String status){ 
		if ( status == null ) {
			status = ""; 
		} 
		this.status = status; 
	} 

	public String getPosition(){ 
		return position; 
	} 

	public void setPosition(String position){ 
		if ( position == null ) {
			position = ""; 
		} 
		this.position = position; 
	} 

	public double getChild(){ 
		return child; 
	} 

	public void setChild(double child){ 
		this.child = child; 
	} 

	public String getDob(){ 
		return dob; 
	} 

	public void setDob(String dob){ 
		if ( dob == null ) {
			dob = ""; 
		} 
		this.dob = dob; 
	} 

	public Date getStart(){ 
		return start; 
	} 

	public void setStart(Date start){ 
		this.start = start; 
	}

    public Date getBirthDate(){
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }

}
