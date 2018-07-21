/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

public class BenefitPeriod extends Entity {

    private long benefitConfigId = 0;
    private Date periodFrom = null;
    private Date periodTo = null;
    private long periodId = 0;
    private double totalRevenue = 0;
    private double part1Value = 0;
    private int part1TotalDivider = 0;
    private double part2Value = 0;
    private int part2TotalDivider = 0;
    private long approve1EmpId = 0;
    private Date approve1Date = null;
    private long approve2EmpId = 0;
    private Date approve2Date = null;
    private long createEmpId = 0;
    private Date createEmpDate = null;
    private int docStatus = 0;

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

    public long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getPart1Value() {
        return part1Value;
    }

    public void setPart1Value(double part1Value) {
        this.part1Value = part1Value;
    }

    public int getPart1TotalDivider() {
        return part1TotalDivider;
    }

    public void setPart1TotalDivider(int part1TotalDivider) {
        this.part1TotalDivider = part1TotalDivider;
    }

    public double getPart2Value() {
        return part2Value;
    }

    public void setPart2Value(double part2Value) {
        this.part2Value = part2Value;
    }

    public int getPart2TotalDivider() {
        return part2TotalDivider;
    }

    public void setPart2TotalDivider(int part2TotalDivider) {
        this.part2TotalDivider = part2TotalDivider;
    }

    public long getApprove1EmpId() {
        return approve1EmpId;
    }

    public void setApprove1EmpId(long approve1EmpId) {
        this.approve1EmpId = approve1EmpId;
    }

    public Date getApprove1Date() {
        return approve1Date;
    }

    public void setApprove1Date(Date approve1Date) {
        this.approve1Date = approve1Date;
    }

    public long getApprove2EmpId() {
        return approve2EmpId;
    }

    public void setApprove2EmpId(long approve2EmpId) {
        this.approve2EmpId = approve2EmpId;
    }

    public Date getApprove2Date() {
        return approve2Date;
    }

    public void setApprove2Date(Date approve2Date) {
        this.approve2Date = approve2Date;
    }

    public long getCreateEmpId() {
        return createEmpId;
    }

    public void setCreateEmpId(long createEmpId) {
        this.createEmpId = createEmpId;
    }

    public Date getCreateEmpDate() {
        return createEmpDate;
    }

    public void setCreateEmpDate(Date createEmpDate) {
        this.createEmpDate = createEmpDate;
    }

    public int getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(int docStatus) {
        this.docStatus = docStatus;
    }
}
