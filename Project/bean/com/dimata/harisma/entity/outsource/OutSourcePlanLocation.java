/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

/**
 *
 * @author Kartika
 */
//public class OutSourcePlanLocation {
//}


import com.dimata.qdep.entity.Entity;

public class OutSourcePlanLocation extends Entity {

    private long outsourcePlanId = 0;
    private long companyId = 0;
    private long divisionId = 0;
    private long departmentId = 0;
    private long sectionId = 0;
    private long positionId=0;
    private int numberTW1 = 0;
    private int numberTW2 = 0;
    private int numberTW3 = 0;
    private int numberTW4 = 0;
    private int prevExisting=0;
    private String nameDivision="";
    private String namePosition="";
    private String nameColomn="";
    private double valueInput=0;
    
    public long getOutsourcePlanId() {
        return outsourcePlanId;
    }

    public void setOutsourcePlanId(long outsourcePlanId) {
        this.outsourcePlanId = outsourcePlanId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public int getNumberTW1() {
        return numberTW1;
    }

    public void setNumberTW1(int numberTW1) {
        this.numberTW1 = numberTW1;
    }

    public int getNumberTW2() {
        return numberTW2;
    }

    public void setNumberTW2(int numberTW2) {
        this.numberTW2 = numberTW2;
    }

    public int getNumberTW3() {
        return numberTW3;
    }

    public void setNumberTW3(int numberTW3) {
        this.numberTW3 = numberTW3;
    }

    public int getNumberTW4() {
        return numberTW4;
    }

    public void setNumberTW4(int numberTW4) {
        this.numberTW4 = numberTW4;
    }

    /**
     * @return the prevExisting
     */
    public int getPrevExisting() {
        return prevExisting;
    }

    /**
     * @param prevExisting the prevExisting to set
     */
    public void setPrevExisting(int prevExisting) {
        this.prevExisting = prevExisting;
    }

    /**
     * @return the positionId
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    /**
     * @return the nameDivision
     */
    public String getNameDivision() {
        return nameDivision;
    }

    /**
     * @param nameDivision the nameDivision to set
     */
    public void setNameDivision(String nameDivision) {
        this.nameDivision = nameDivision;
    }

    /**
     * @return the namePosition
     */
    public String getNamePosition() {
        return namePosition;
    }

    /**
     * @param namePosition the namePosition to set
     */
    public void setNamePosition(String namePosition) {
        this.namePosition = namePosition;
    }

    /**
     * @return the nameColomn
     */
    public String getNameColomn() {
        return nameColomn;
    }

    /**
     * @param nameColomn the nameColomn to set
     */
    public void setNameColomn(String nameColomn) {
        this.nameColomn = nameColomn;
    }

    /**
     * @return the valueInput
     */
    public double getValueInput() {
        return valueInput;
    }

    /**
     * @param valueInput the valueInput to set
     */
    public void setValueInput(double valueInput) {
        this.valueInput = valueInput;
    }

}