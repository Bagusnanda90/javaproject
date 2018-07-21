/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 * Description : Benefit Config Entity
 * Date : Feb, 21 2015
 * @author Hendra Putu
 */
public class BenefitConfig extends Entity {

    private long benefitConfigId = 0;
    private Date periodFrom = null;
    private Date periodTo = null;
    private String code = "";
    private String title = "";
    private String description = "";
    private String distributionPart1 = "";
    private int distributionPercent1 = 0;
    private String distributionDescription1 = "";
    private int distributionTotal1 = 0;
    private String distributionPart2 = "";
    private int distributionPercent2 = 0;
    private String distributionDescription2 = "";
    private int distributionTotal2 = 0;
    private String exceptionNoByCategory = "";
    private String exceptionNoByPosition = "";
    private String exceptionNoByPayroll = "";
    private String exceptionNoByDivision = "";
    private long approve1EmpId = 0;
    private long approve2EmpId = 0;

    public long getBenefitConfigId() {
        return benefitConfigId;
    }

    public void setBenefitConfigId(long benefitConfigId) {
        this.benefitConfigId = benefitConfigId;
    }

    public Date getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    public Date getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(Date periodTo) {
        this.periodTo = periodTo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistributionPart1() {
        return distributionPart1;
    }

    public void setDistributionPart1(String distributionPart1) {
        this.distributionPart1 = distributionPart1;
    }

    public int getDistributionPercent1() {
        return distributionPercent1;
    }

    public void setDistributionPercent1(int distributionPercent1) {
        this.distributionPercent1 = distributionPercent1;
    }

    public String getDistributionDescription1() {
        return distributionDescription1;
    }

    public void setDistributionDescription1(String distributionDescription1) {
        this.distributionDescription1 = distributionDescription1;
    }

    public int getDistributionTotal1() {
        return distributionTotal1;
    }

    public void setDistributionTotal1(int distributionTotal1) {
        this.distributionTotal1 = distributionTotal1;
    }

    public String getDistributionPart2() {
        return distributionPart2;
    }

    public void setDistributionPart2(String distributionPart2) {
        this.distributionPart2 = distributionPart2;
    }

    public int getDistributionPercent2() {
        return distributionPercent2;
    }

    public void setDistributionPercent2(int distributionPercent2) {
        this.distributionPercent2 = distributionPercent2;
    }

    public String getDistributionDescription2() {
        return distributionDescription2;
    }

    public void setDistributionDescription2(String distributionDescription2) {
        this.distributionDescription2 = distributionDescription2;
    }

    public int getDistributionTotal2() {
        return distributionTotal2;
    }

    public void setDistributionTotal2(int distributionTotal2) {
        this.distributionTotal2 = distributionTotal2;
    }

    public String getExceptionNoByCategory() {
        return exceptionNoByCategory;
    }

    public void setExceptionNoByCategory(String exceptionNoByCategory) {
        this.exceptionNoByCategory = exceptionNoByCategory;
    }

    public String getExceptionNoByPosition() {
        return exceptionNoByPosition;
    }

    public void setExceptionNoByPosition(String exceptionNoByPosition) {
        this.exceptionNoByPosition = exceptionNoByPosition;
    }

    public String getExceptionNoByPayroll() {
        return exceptionNoByPayroll;
    }

    public void setExceptionNoByPayroll(String exceptionNoByPayroll) {
        this.exceptionNoByPayroll = exceptionNoByPayroll;
    }

    /**
     * @return the exceptionNoByDivision
     */
    public String getExceptionNoByDivision() {
        return exceptionNoByDivision;
    }

    /**
     * @param exceptionNoByDivision the exceptionNoByDivision to set
     */
    public void setExceptionNoByDivision(String exceptionNoByDivision) {
        this.exceptionNoByDivision = exceptionNoByDivision;
    }

    /**
     * @return the approve1EmpId
     */
    public long getApprove1EmpId() {
        return approve1EmpId;
    }

    /**
     * @param approve1EmpId the approve1EmpId to set
     */
    public void setApprove1EmpId(long approve1EmpId) {
        this.approve1EmpId = approve1EmpId;
    }

    /**
     * @return the approve2EmpId
     */
    public long getApprove2EmpId() {
        return approve2EmpId;
    }

    /**
     * @param approve2EmpId the approve2EmpId to set
     */
    public void setApprove2EmpId(long approve2EmpId) {
        this.approve2EmpId = approve2EmpId;
    }
}
