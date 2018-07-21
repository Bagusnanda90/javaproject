/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

/**
 * Description : search benefit employee
 * Date : 2015-03-09
 * @author Hendra Putu
 */
public class SrcBenefitEmp {

    private String period = "";
    private String employeeNum = "";
    private String employeeName = "";
    private String levelCode = "";
    private int levelPoint = 0;
    private double flatService = 0;
    private double servicePoint = 0;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    public double getFlatService() {
        return flatService;
    }

    public void setFlatService(double flatService) {
        this.flatService = flatService;
    }

    public double getServicePoint() {
        return servicePoint;
    }

    public void setServicePoint(double servicePoint) {
        this.servicePoint = servicePoint;
    }
}
