/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.report.lkpbu;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author khirayinnura
 */
public class Lkpbu805 extends Entity {
    private long lkpbu805Id = 0;
    private long levelId = 0;
    private String levelCode = "";
    private long educationId = 0;
    private String educationCode = "";
    private long empCategoryId = 0;
    private String empCategoryCode = "";
    private float lkpbu805YearRealisasi = 0;
    private float lkpbu805YearPrediksi1 = 0;
    private float lkpbu805YearPrediksi2 = 0;
    private float lkpbu805YearPrediksi3 = 0;
    private float lkpbu805YearPrediksi4 = 0;
    private Date lkpbu805StartDate = new Date();
    
    private int umur = 0;
    private Date empCommencingDate = new Date();
    private int empResign = 0;

    /**
     * @return the lkpbu805Id
     */
    public long getLkpbu805Id() {
        return lkpbu805Id;
    }

    /**
     * @param lkpbu805Id the lkpbu805Id to set
     */
    public void setLkpbu805Id(long lkpbu805Id) {
        this.lkpbu805Id = lkpbu805Id;
    }

    /**
     * @return the educationId
     */
    public long getEducationId() {
        return educationId;
    }

    /**
     * @param educationId the educationId to set
     */
    public void setEducationId(long educationId) {
        this.educationId = educationId;
    }

    /**
     * @return the empCategoryId
     */
    public long getEmpCategoryId() {
        return empCategoryId;
    }

    /**
     * @param empCategoryId the empCategoryId to set
     */
    public void setEmpCategoryId(long empCategoryId) {
        this.empCategoryId = empCategoryId;
    }

    /**
     * @return the lkpbu805YearRealisasi
     */
    public float getLkpbu805YearRealisasi() {
        return lkpbu805YearRealisasi;
    }

    /**
     * @param lkpbu805YearRealisasi the lkpbu805YearRealisasi to set
     */
    public void setLkpbu805YearRealisasi(float lkpbu805YearRealisasi) {
        this.lkpbu805YearRealisasi = lkpbu805YearRealisasi;
    }

    /**
     * @return the lkpbu805YearPrediksi1
     */
    public float getLkpbu805YearPrediksi1() {
        return lkpbu805YearPrediksi1;
    }

    /**
     * @param lkpbu805YearPrediksi1 the lkpbu805YearPrediksi1 to set
     */
    public void setLkpbu805YearPrediksi1(float lkpbu805YearPrediksi1) {
        this.lkpbu805YearPrediksi1 = lkpbu805YearPrediksi1;
    }

    /**
     * @return the lkpbu805YearPrediksi2
     */
    public float getLkpbu805YearPrediksi2() {
        return lkpbu805YearPrediksi2;
    }

    /**
     * @param lkpbu805YearPrediksi2 the lkpbu805YearPrediksi2 to set
     */
    public void setLkpbu805YearPrediksi2(float lkpbu805YearPrediksi2) {
        this.lkpbu805YearPrediksi2 = lkpbu805YearPrediksi2;
    }

    /**
     * @return the lkpbu805YearPrediksi3
     */
    public float getLkpbu805YearPrediksi3() {
        return lkpbu805YearPrediksi3;
    }

    /**
     * @param lkpbu805YearPrediksi3 the lkpbu805YearPrediksi3 to set
     */
    public void setLkpbu805YearPrediksi3(float lkpbu805YearPrediksi3) {
        this.lkpbu805YearPrediksi3 = lkpbu805YearPrediksi3;
    }

    /**
     * @return the lkpbu805YearPrediksi4
     */
    public float getLkpbu805YearPrediksi4() {
        return lkpbu805YearPrediksi4;
    }

    /**
     * @param lkpbu805YearPrediksi4 the lkpbu805YearPrediksi4 to set
     */
    public void setLkpbu805YearPrediksi4(float lkpbu805YearPrediksi4) {
        this.lkpbu805YearPrediksi4 = lkpbu805YearPrediksi4;
    }

    /**
     * @return the levelId
     */
    public long getLevelId() {
        return levelId;
    }

    /**
     * @param levelId the levelId to set
     */
    public void setLevelId(long levelId) {
        this.levelId = levelId;
    }

    /**
     * @return the levelCode
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * @param levelCode the levelCode to set
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    /**
     * @return the educationCode
     */
    public String getEducationCode() {
        return educationCode;
    }

    /**
     * @param educationCode the educationCode to set
     */
    public void setEducationCode(String educationCode) {
        this.educationCode = educationCode;
    }

    /**
     * @return the empCategoryCode
     */
    public String getEmpCategoryCode() {
        return empCategoryCode;
    }

    /**
     * @param empCategoryCode the empCategoryCode to set
     */
    public void setEmpCategoryCode(String empCategoryCode) {
        this.empCategoryCode = empCategoryCode;
    }

    /**
     * @return the lkpbu805StartDate
     */
    public Date getLkpbu805StartDate() {
        return lkpbu805StartDate;
    }

    /**
     * @param lkpbu805StartDate the lkpbu805StartDate to set
     */
    public void setLkpbu805StartDate(Date lkpbu805StartDate) {
        this.lkpbu805StartDate = lkpbu805StartDate;
    }

    /**
     * @return the umur
     */
    public int getUmur() {
        return umur;
    }

    /**
     * @param umur the umur to set
     */
    public void setUmur(int umur) {
        this.umur = umur;
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
}
