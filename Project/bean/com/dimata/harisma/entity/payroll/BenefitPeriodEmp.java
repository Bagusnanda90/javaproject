/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

/**
 * Description : Date :
 *
 * @author Hendra Putu
 */
import com.dimata.qdep.entity.Entity;

public class BenefitPeriodEmp extends Entity {

    private long employeeId = 0;
    private double amountPart1 = 0;
    private double amountPart2 = 0;
    private String levelCode = "";
    private int levelPoint = 0;
    private long benefitPeriodId = 0;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public double getAmountPart1() {
        return amountPart1;
    }

    public void setAmountPart1(double amountPart1) {
        this.amountPart1 = amountPart1;
    }

    public double getAmountPart2() {
        return amountPart2;
    }

    public void setAmountPart2(double amountPart2) {
        this.amountPart2 = amountPart2;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public int getLevelPoint() {
        return levelPoint;
    }

    public void setLevelPoint(int levelPoint) {
        this.levelPoint = levelPoint;
    }

    public long getBenefitPeriodId() {
        return benefitPeriodId;
    }

    public void setBenefitPeriodId(long benefitPeriodId) {
        this.benefitPeriodId = benefitPeriodId;
    }
}