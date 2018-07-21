/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.overtime;

/**
 *
 * @author Dimata 007
 */
public class OvertimeSummary {
    private long employeeId;
    private double otDuration;
    private double otPaidSummaryDp;
    private double otPaidSummarySallary;
    private double otPaidSummarySallaryAndDP;

    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the otDuration
     */
    public double getOtDuration() {
        return otDuration;
    }

    /**
     * @param otDuration the otDuration to set
     */
    public void setOtDuration(double otDuration) {
        this.otDuration = otDuration;
    }

    /**
     * @return the otPaidSummaryDp
     */
    public double getOtPaidSummaryDp() {
        return otPaidSummaryDp;
    }

    /**
     * @param otPaidSummaryDp the otPaidSummaryDp to set
     */
    public void setOtPaidSummaryDp(double otPaidSummaryDp) {
        this.otPaidSummaryDp = otPaidSummaryDp;
    }

    /**
     * @return the otPaidSummarySallary
     */
    public double getOtPaidSummarySallary() {
        return otPaidSummarySallary;
    }

    /**
     * @param otPaidSummarySallary the otPaidSummarySallary to set
     */
    public void setOtPaidSummarySallary(double otPaidSummarySallary) {
        this.otPaidSummarySallary = otPaidSummarySallary;
    }

    /**
     * @return the otPaidSummarySallaryAndDP
     */
    public double getOtPaidSummarySallaryAndDP() {
        return otPaidSummarySallaryAndDP;
    }

    /**
     * @param otPaidSummarySallaryAndDP the otPaidSummarySallaryAndDP to set
     */
    public void setOtPaidSummarySallaryAndDP(double otPaidSummarySallaryAndDP) {
        this.otPaidSummarySallaryAndDP = otPaidSummarySallaryAndDP;
    }
}
