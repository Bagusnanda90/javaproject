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
import java.util.Vector;

public class SrcEmployee {
    private String name = "";
    private String empnumber = "";
    private String address = "";
    private int orderBy;
    private long companyId;
    private long department;
    private long position;
    private long section;
    private Date startCommenc;
    private Date endCommenc;
    private int resigned;
    private long divisionid;
    /*edited by yunny
     *untuk kebutuhan intimas yaitu search karyawan by religion dan gender
     */
    private long religion;
    private long gender;
    private long maritalStatus;
    private String salaryLevel;
    

    /** Holds value of property empCategory. */
    private long empCategory = 0;
    
    /* added by Bayu -> data ras karyawan 
     */
    private long raceId;
    private boolean birthdayChecked = true;
    private Date birthday;
    private int birthmonth = 0;
    
    private Date startBirth;
    private Date endBirth;
    
     /* added by Roy -> data spouse
     */
    
    private String spouse = "";
    
    private Date startResign;
    private Date endResign;
    private String addWhere="";
    //update by satrya 2012-11-14
   //private long education_id;
    private Vector educationIds;
    private String blood;
    private long language;
    private long level;
    //update by satrya 2014-06-19
    private String employeeIdLeaveConfig;
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            name = "";
        }
        this.name = name;
    }

    public String getEmpnumber() {
        return empnumber;
    }

    public void setEmpnumber(String empnumber) {
        if (empnumber == null) {
            empnumber = "";
        }
        this.empnumber = empnumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null) {
            address = "";
        }
        this.address = address;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public long getDepartment() {
        return department;
    }

    public void setDepartment(long department) {
        this.department = department;
    }


    public long getDivisionId() {
        return divisionid;
    }

    public void setDivisionId(long divisionId) {
        this.divisionid = divisionId;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getSection() {
        return section;
    }

    public void setSection(long section) {
        this.section = section;
    }

    public Date getStartCommenc() {
        return startCommenc;
    }

    public void setStartCommenc(Date startCommenc) {
        this.startCommenc = startCommenc;
    }

    public Date getEndCommenc() {
        return endCommenc;
    }

    public void setEndCommenc(Date endCommenc) {
        this.endCommenc = endCommenc;
    }

    public int getResigned() {
        return resigned;
    }

    public void setResigned(int resigned) {
        this.resigned = resigned;
    }

    /** Getter for property empCategory.
     * @return Value of property empCategory.
     *
     */
    public long getEmpCategory() {
        return this.empCategory;
    }

    /** Setter for property empCategory.
     * @param empCategory New value of property empCategory.
     *
     */
    public void setEmpCategory(long empCategory) {
        this.empCategory = empCategory;
    }

    /**
     * Getter for property religion.
     * @return Value of property religion.
     */
    public long getReligion() {
        return religion;
    }
    
    /**
     * Setter for property religion.
     * @param religion New value of property religion.
     */
    public void setReligion(long religion) {
        this.religion = religion;
    }
    
    /**
     * Getter for property gender.
     * @return Value of property gender.
     */
   public long getGender() {
        return gender;
    }
    
    /**
     * Setter for property gender.
     * @param gender New value of property gender.
     */
    public void setGender(long gender) {
        this.gender = gender;
    }
    
    /**
     * Getter for property maritalStatus.
     * @return Value of property maritalStatus.
     */
    public long getMaritalStatus() {
        return maritalStatus;
    }
    
    /**
     * Setter for property maritalStatus.
     * @param maritalStatus New value of property maritalStatus.
     */
    public void setMaritalStatus(long maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    
    /**
     * Getter for property salaryLevel.
     * @return Value of property salaryLevel.
     */
    public java.lang.String getSalaryLevel() {
        return salaryLevel;
    }
    
    /**
     * Setter for property salaryLevel.
     * @param salaryLevel New value of property salaryLevel.
     */
    public void setSalaryLevel(java.lang.String salaryLevel) {
        this.salaryLevel = salaryLevel;
    }
    
    public long getRaceId() {
        return raceId;
    }
    
    public void setRaceId(long raceId) {
        this.raceId = raceId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isBirthdayChecked() {
        return birthdayChecked;
    }

    public void setBirthdayChecked(boolean birthdayChecked) {
        this.birthdayChecked = birthdayChecked;
    }

    public int getBirthmonth() {
        return birthmonth;
    }

    public void setBirthmonth(int birthmonth) {
        this.birthmonth = birthmonth;
    }
    
    public void setSpouse(String spouse){
        if(spouse==null)
            spouse = "";
        
        this.spouse=spouse;
    }
    
    public String getSpouse(){
        return spouse;
    }

    public Date getStartBirth() {
        return startBirth;
    }

    public void setStartBirth(Date startBirth) {
        this.startBirth = startBirth;
    }


    public Date getEndBirth() {
        return endBirth;
    }

    public void setEndBirth(Date endBirth) {
        this.endBirth = endBirth;
    }

    /**
     * @return the companyId
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the startResign
     */
    public Date getStartResign() {
        return startResign;
    }

    /**
     * @param startResign the startResign to set
     */
    public void setStartResign(Date startResign) {
        this.startResign = startResign;
    }

    /**
     * @return the endResign
     */
    public Date getEndResign() {
        return endResign;
    }

    /**
     * @param endResign the endResign to set
     */
    public void setEndResign(Date endResign) {
        this.endResign = endResign;
    }

    /**
     * @return the addWhere
     */
    public String getAddWhere() {
        return addWhere;
    }

    /**
     * @param addWhere the addWhere to set
     */
    public void setAddWhere(String addWhere) {
        this.addWhere = addWhere;
    }

    /**
     * @return the education_id
     */
//    public long getEducation_id() {
//        return education_id;
//    }
//
//    /**
//     * @param education_id the education_id to set
//     */
//    public void setEducation_id(long education_id) {
//        this.education_id = education_id;
//    }

    /**
     * @return the blood
     */
    public String getBlood() {
        return blood;
    }

    /**
     * @param blood the blood to set
     */
    public void setBlood(String blood) {
        this.blood = blood;
    }

    /**
     * @return the language
     */
    public long getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(long language) {
        this.language = language;
    }

    /**
     * @return the educationIds
     */
    public Vector getEducationIds() {
        return educationIds;
    }

    /**
     * @param educationIds the educationIds to set
     */
    public void setEducationIds(Vector educationIds) {
        this.educationIds = educationIds;
    }

    /**
     * @return the level
     */
    public long getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(long level) {
        this.level = level;
    }

    /**
     * @return the employeeIdLeaveConfig
     */
    public String getEmployeeIdLeaveConfig() {
        return employeeIdLeaveConfig;
    }

    /**
     * @param employeeIdLeaveConfig the employeeIdLeaveConfig to set
     */
    public void setEmployeeIdLeaveConfig(String employeeIdLeaveConfig) {
        this.employeeIdLeaveConfig = employeeIdLeaveConfig;
    }

  
}
