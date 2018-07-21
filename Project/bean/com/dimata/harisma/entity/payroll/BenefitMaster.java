/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author Hendra McHen - 20150210
 */
public class BenefitMaster extends Entity {
    private long benefitMasterId = 0;
    private Date periodFrom = null;
    private Date periodTo = null;
    private String code = "";
    private String title = "";
    private String description = "";
    private String companyStructure = "";
    private int prorateEmployeePresence = 0;
    private int employeeLevelPoint = 0;
    private String distributionPart1 = "";
    private String distributionCode1 = "";
    private String distributionDescription1 = "";
    private int distributionTotal1 = 0;
    private String distributionPart2 = "";
    private String distributionCode2 = "";
    private String distributionDescription2 = "";
    private int distributionTotal2 = 0;
    private String distributionPart3 = "";
    private String distributionCode3 = "";
    private String distributionDescription3 = "";
    private int distributionTotal3 = 0;
    private String exceptionNoByCategory = "";
    private String exceptionNoByPosition = "";
    private String exceptionNoByPayroll = "";
    private String exceptionNoBySpecialLeave = "";
    private String employeeByEntitle = "";

    /**
     * @return the benefitMasterId
     */
    public long getBenefitMasterId() {
        return benefitMasterId;
    }

    /**
     * @param benefitMasterId the benefitMasterId to set
     */
    public void setBenefitMasterId(long benefitMasterId) {
        this.benefitMasterId = benefitMasterId;
    }

    /**
     * @return the periodFrom
     */
    public Date getPeriodFrom() {
        return periodFrom;
    }

    /**
     * @param periodFrom the periodFrom to set
     */
    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    /**
     * @return the periodTo
     */
    public Date getPeriodTo() {
        return periodTo;
    }

    /**
     * @param periodTo the periodTo to set
     */
    public void setPeriodTo(Date periodTo) {
        this.periodTo = periodTo;
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the companyStructure
     */
    public String getCompanyStructure() {
        return companyStructure;
    }

    /**
     * @param companyStructure the companyStructure to set
     */
    public void setCompanyStructure(String companyStructure) {
        this.companyStructure = companyStructure;
    }

    /**
     * @return the prorateEmployeePresence
     */
    public int getProrateEmployeePresence() {
        return prorateEmployeePresence;
    }

    /**
     * @param prorateEmployeePresence the prorateEmployeePresence to set
     */
    public void setProrateEmployeePresence(int prorateEmployeePresence) {
        this.prorateEmployeePresence = prorateEmployeePresence;
    }

    /**
     * @return the employeeLevelPoint
     */
    public int getEmployeeLevelPoint() {
        return employeeLevelPoint;
    }

    /**
     * @param employeeLevelPoint the employeeLevelPoint to set
     */
    public void setEmployeeLevelPoint(int employeeLevelPoint) {
        this.employeeLevelPoint = employeeLevelPoint;
    }

    /**
     * @return the distributionPart1
     */
    public String getDistributionPart1() {
        return distributionPart1;
    }

    /**
     * @param distributionPart1 the distributionPart1 to set
     */
    public void setDistributionPart1(String distributionPart1) {
        this.distributionPart1 = distributionPart1;
    }

    /**
     * @return the distributionCode1
     */
    public String getDistributionCode1() {
        return distributionCode1;
    }

    /**
     * @param distributionCode1 the distributionCode1 to set
     */
    public void setDistributionCode1(String distributionCode1) {
        this.distributionCode1 = distributionCode1;
    }

    /**
     * @return the distributionDescription1
     */
    public String getDistributionDescription1() {
        return distributionDescription1;
    }

    /**
     * @param distributionDescription1 the distributionDescription1 to set
     */
    public void setDistributionDescription1(String distributionDescription1) {
        this.distributionDescription1 = distributionDescription1;
    }

    /**
     * @return the distributionTotal1
     */
    public int getDistributionTotal1() {
        return distributionTotal1;
    }

    /**
     * @param distributionTotal1 the distributionTotal1 to set
     */
    public void setDistributionTotal1(int distributionTotal1) {
        this.distributionTotal1 = distributionTotal1;
    }

    /**
     * @return the distributionPart2
     */
    public String getDistributionPart2() {
        return distributionPart2;
    }

    /**
     * @param distributionPart2 the distributionPart2 to set
     */
    public void setDistributionPart2(String distributionPart2) {
        this.distributionPart2 = distributionPart2;
    }

    /**
     * @return the distributionCode2
     */
    public String getDistributionCode2() {
        return distributionCode2;
    }

    /**
     * @param distributionCode2 the distributionCode2 to set
     */
    public void setDistributionCode2(String distributionCode2) {
        this.distributionCode2 = distributionCode2;
    }

    /**
     * @return the distributionDescription2
     */
    public String getDistributionDescription2() {
        return distributionDescription2;
    }

    /**
     * @param distributionDescription2 the distributionDescription2 to set
     */
    public void setDistributionDescription2(String distributionDescription2) {
        this.distributionDescription2 = distributionDescription2;
    }

    /**
     * @return the distributionTotal2
     */
    public int getDistributionTotal2() {
        return distributionTotal2;
    }

    /**
     * @param distributionTotal2 the distributionTotal2 to set
     */
    public void setDistributionTotal2(int distributionTotal2) {
        this.distributionTotal2 = distributionTotal2;
    }

    /**
     * @return the distributionPart3
     */
    public String getDistributionPart3() {
        return distributionPart3;
    }

    /**
     * @param distributionPart3 the distributionPart3 to set
     */
    public void setDistributionPart3(String distributionPart3) {
        this.distributionPart3 = distributionPart3;
    }

    /**
     * @return the distributionCode3
     */
    public String getDistributionCode3() {
        return distributionCode3;
    }

    /**
     * @param distributionCode3 the distributionCode3 to set
     */
    public void setDistributionCode3(String distributionCode3) {
        this.distributionCode3 = distributionCode3;
    }

    /**
     * @return the distributionDescription3
     */
    public String getDistributionDescription3() {
        return distributionDescription3;
    }

    /**
     * @param distributionDescription3 the distributionDescription3 to set
     */
    public void setDistributionDescription3(String distributionDescription3) {
        this.distributionDescription3 = distributionDescription3;
    }

    /**
     * @return the distributionTotal3
     */
    public int getDistributionTotal3() {
        return distributionTotal3;
    }

    /**
     * @param distributionTotal3 the distributionTotal3 to set
     */
    public void setDistributionTotal3(int distributionTotal3) {
        this.distributionTotal3 = distributionTotal3;
    }

    /**
     * @return the exceptionNoByCategory
     */
    public String getExceptionNoByCategory() {
        return exceptionNoByCategory;
    }

    /**
     * @param exceptionNoByCategory the exceptionNoByCategory to set
     */
    public void setExceptionNoByCategory(String exceptionNoByCategory) {
        this.exceptionNoByCategory = exceptionNoByCategory;
    }

    /**
     * @return the exceptionNoByPosition
     */
    public String getExceptionNoByPosition() {
        return exceptionNoByPosition;
    }

    /**
     * @param exceptionNoByPosition the exceptionNoByPosition to set
     */
    public void setExceptionNoByPosition(String exceptionNoByPosition) {
        this.exceptionNoByPosition = exceptionNoByPosition;
    }

    /**
     * @return the exceptionNoByPayroll
     */
    public String getExceptionNoByPayroll() {
        return exceptionNoByPayroll;
    }

    /**
     * @param exceptionNoByPayroll the exceptionNoByPayroll to set
     */
    public void setExceptionNoByPayroll(String exceptionNoByPayroll) {
        this.exceptionNoByPayroll = exceptionNoByPayroll;
    }

    /**
     * @return the exceptionNoBySpecialLeave
     */
    public String getExceptionNoBySpecialLeave() {
        return exceptionNoBySpecialLeave;
    }

    /**
     * @param exceptionNoBySpecialLeave the exceptionNoBySpecialLeave to set
     */
    public void setExceptionNoBySpecialLeave(String exceptionNoBySpecialLeave) {
        this.exceptionNoBySpecialLeave = exceptionNoBySpecialLeave;
    }

    /**
     * @return the employeeByEntitle
     */
    public String getEmployeeByEntitle() {
        return employeeByEntitle;
    }

    /**
     * @param employeeByEntitle the employeeByEntitle to set
     */
    public void setEmployeeByEntitle(String employeeByEntitle) {
        this.employeeByEntitle = employeeByEntitle;
    }
}
