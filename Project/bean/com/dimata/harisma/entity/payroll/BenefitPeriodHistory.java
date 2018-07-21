/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.entity.Entity;

public class BenefitPeriodHistory extends Entity {

    private long benefitPeriodId = 0;
    private String benefitConfiguration = "";
    private String periodFrom = "";
    private String periodTo = "";
    private String payrollPeriod = "";
    private double totalRevenue = 0;
    private double distributionValue = 0;
    private String distributionDesc1 = "";
    private String distributionDesc2 = "";
    private int distributionPercent1 = 0;
    private int distributionPercent2 = 0;
    private int part1TotalDivider = 0;
    private int part2TotalDivider = 0;
    private double part1Value = 0;
    private double part2Value = 0;
    private String docStatus = "";
    private String approve1 = "";
    private String approve2 = "";
    private String createdBy = "";

    public long getBenefitPeriodId() {
        return benefitPeriodId;
    }

    public void setBenefitPeriodId(long benefitPeriodId) {
        this.benefitPeriodId = benefitPeriodId;
    }

    public String getBenefitConfiguration() {
        return benefitConfiguration;
    }

    public void setBenefitConfiguration(String benefitConfiguration) {
        this.benefitConfiguration = benefitConfiguration;
    }

    public String getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(String periodFrom) {
        this.periodFrom = periodFrom;
    }

    public String getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(String periodTo) {
        this.periodTo = periodTo;
    }

    public String getPayrollPeriod() {
        return payrollPeriod;
    }

    public void setPayrollPeriod(String payrollPeriod) {
        this.payrollPeriod = payrollPeriod;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getDistributionValue() {
        return distributionValue;
    }

    public void setDistributionValue(double distributionValue) {
        this.distributionValue = distributionValue;
    }

    public String getDistributionDesc1() {
        return distributionDesc1;
    }

    public void setDistributionDesc1(String distributionDesc1) {
        this.distributionDesc1 = distributionDesc1;
    }

    public String getDistributionDesc2() {
        return distributionDesc2;
    }

    public void setDistributionDesc2(String distributionDesc2) {
        this.distributionDesc2 = distributionDesc2;
    }

    public int getDistributionPercent1() {
        return distributionPercent1;
    }

    public void setDistributionPercent1(int distributionPercent1) {
        this.distributionPercent1 = distributionPercent1;
    }

    public int getDistributionPercent2() {
        return distributionPercent2;
    }

    public void setDistributionPercent2(int distributionPercent2) {
        this.distributionPercent2 = distributionPercent2;
    }

    public int getPart1TotalDivider() {
        return part1TotalDivider;
    }

    public void setPart1TotalDivider(int part1TotalDivider) {
        this.part1TotalDivider = part1TotalDivider;
    }

    public int getPart2TotalDivider() {
        return part2TotalDivider;
    }

    public void setPart2TotalDivider(int part2TotalDivider) {
        this.part2TotalDivider = part2TotalDivider;
    }

    public double getPart1Value() {
        return part1Value;
    }

    public void setPart1Value(double part1Value) {
        this.part1Value = part1Value;
    }

    public double getPart2Value() {
        return part2Value;
    }

    public void setPart2Value(double part2Value) {
        this.part2Value = part2Value;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getApprove1() {
        return approve1;
    }

    public void setApprove1(String approve1) {
        this.approve1 = approve1;
    }

    public String getApprove2() {
        return approve2;
    }

    public void setApprove2(String approve2) {
        this.approve2 = approve2;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}
