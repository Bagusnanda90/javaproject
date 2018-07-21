/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.report.lkpbu;

import com.dimata.util.Formater;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author khirayinnura
 */
public class Lkpbu {
    
    private String empCategoryNameCode;
    private String empEduCode;
    private Date empBirthDate;
    private String empLevelCode;
    private String empResignCode;
    private int empSex;
    private Date empCommencingDate;
    private int empResign;
    private int year;
    private int trainAteendes;
    private String code;
    private Date date;
    private String resignCategory;
    
    private String empNumTtd;
    private String nameTtd;
    private String companyTtd;
    private String divisiTtd;
    private String levelTtd;
    
    private int empUmur;
    
    
    
    public static String getCodeUsia(Date birthDate, int checkDate) {
        
        String date=Formater.formatDate(birthDate,"yyyy");
        int ageInt = Integer.parseInt(date);
        int age = checkDate - ageInt;

        if(age < 20 && age > 0) {
            return "01";
        } else if(age < 25) {
            return "02";
        } else if(age < 30) {
            return "03";
        } else if(age < 35) {
            return "04";
        } else if(age < 40) {
            return "05";
        } else if(age < 45) {
            return "06";
        } else if(age < 50) {
            return "07";
        } else if(age < 55) {
            return "08";
        } else if(age < 60) {
            return "09";
        } else {
            return "99";
        }
        
    }
    
    public static int getUsia(Date birthDate, int checkDate) {
        
        String date=Formater.formatDate(birthDate,"yyyy");
        int ageInt = Integer.parseInt(date);
        int age = checkDate - ageInt;
        
        return age;
    }

    /**
     * @return the empBirthDate
     */
    public Date getEmpBirthDate() {
        return empBirthDate;
    }

    /**
     * @param empBirthDate the empBirthDate to set
     */
    public void setEmpBirthDate(Date empBirthDate) {
        this.empBirthDate = empBirthDate;
    }

    /**
     * @return the empSex
     */
    public int getEmpSex() {
        return empSex;
    }

    /**
     * @param empSex the empSex to set
     */
    public void setEmpSex(int empSex) {
        this.empSex = empSex;
    }

    /**
     * @return the empLevelCode
     */
    public String getEmpLevelCode() {
        return empLevelCode;
    }

    /**
     * @param empLevelCode the empLevelCode to set
     */
    public void setEmpLevelCode(String empLevelCode) {
        this.empLevelCode = empLevelCode;
    }

    /**
     * @return the empCategoryNameCode
     */
    public String getEmpCategoryNameCode() {
        return empCategoryNameCode;
    }

    /**
     * @param empCategoryNameCode the empCategoryNameCode to set
     */
    public void setEmpCategoryNameCode(String empCategoryNameCode) {
        this.empCategoryNameCode = empCategoryNameCode;
    }

    /**
     * @return the empEduCode
     */
    public String getEmpEduCode() {
        return empEduCode;
    }

    /**
     * @param empEduCode the empEduCode to set
     */
    public void setEmpEduCode(String empEduCode) {
        this.empEduCode = empEduCode;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the trainAteendes
     */
    public int getTrainAteendes() {
        return trainAteendes;
    }

    /**
     * @param trainAteendes the trainAteendes to set
     */
    public void setTrainAteendes(int trainAteendes) {
        this.trainAteendes = trainAteendes;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the empResignCode
     */
    public String getEmpResignCode() {
        return empResignCode;
    }

    /**
     * @param empResignCode the empResignCode to set
     */
    public void setEmpResignCode(String empResignCode) {
        this.empResignCode = empResignCode;
    }

    /**
     * @return the resignCategory
     */
    public String getResignCategory() {
        return resignCategory;
    }

    /**
     * @param resignCategory the resignCategory to set
     */
    public void setResignCategory(String resignCategory) {
        this.resignCategory = resignCategory;
    }

    /**
     * @return the empNumTtd
     */
    public String getEmpNumTtd() {
        return empNumTtd;
    }

    /**
     * @param empNumTtd the empNumTtd to set
     */
    public void setEmpNumTtd(String empNumTtd) {
        this.empNumTtd = empNumTtd;
    }

    /**
     * @return the nameTtd
     */
    public String getNameTtd() {
        return nameTtd;
    }

    /**
     * @param nameTtd the nameTtd to set
     */
    public void setNameTtd(String nameTtd) {
        this.nameTtd = nameTtd;
    }

    /**
     * @return the companyTtd
     */
    public String getCompanyTtd() {
        return companyTtd;
    }

    /**
     * @param companyTtd the companyTtd to set
     */
    public void setCompanyTtd(String companyTtd) {
        this.companyTtd = companyTtd;
    }

    /**
     * @return the divisiTtd
     */
    public String getDivisiTtd() {
        return divisiTtd;
    }

    /**
     * @param divisiTtd the divisiTtd to set
     */
    public void setDivisiTtd(String divisiTtd) {
        this.divisiTtd = divisiTtd;
    }

    /**
     * @return the levelTtd
     */
    public String getLevelTtd() {
        return levelTtd;
    }

    /**
     * @param levelTtd the levelTtd to set
     */
    public void setLevelTtd(String levelTtd) {
        this.levelTtd = levelTtd;
    }

    /**
     * @return the empCommencingDate
     */
    public Date getEmpCommencingDate() {
        return empCommencingDate;
    }

    /**
     * @param empCommencingDate the empCommencingDate to set
     */
    public void setEmpCommencingDate(Date empCommencingDate) {
        this.empCommencingDate = empCommencingDate;
    }

    /**
     * @return the empResign
     */
    public int getEmpResign() {
        return empResign;
    }

    /**
     * @param empResign the empResign to set
     */
    public void setEmpResign(int empResign) {
        this.empResign = empResign;
    }

    /**
     * @return the empUmur
     */
    public int getEmpUmur() {
        return empUmur;
    }

    /**
     * @param empUmur the empUmur to set
     */
    public void setEmpUmur(int empUmur) {
        this.empUmur = empUmur;
    }
//tess
}
