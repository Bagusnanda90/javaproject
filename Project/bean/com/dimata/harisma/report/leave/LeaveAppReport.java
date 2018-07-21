/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.report.leave;
import java.util.*;
/**
 *
 * @author Tu Roy
 */
public class LeaveAppReport {
    
    private String payRoll;
    private String fullName;
    private String department;
    private String devision;
    private String commencingDate;
    private String documentStatus;
    private String position;
    private String dateRequest;
    private Vector annualLeave;
    private Vector longLeave;
    private Vector specialLeave;
    private Vector dp;
    private String reasonUnpaid;
    private String appHrManager;
    private String appHrDate;
    private String appDepHead;
    private String appDepDate;
    private String appGm;
    private String appGmDate;

    public String getPayRoll() {
        return payRoll;
    }

    public void setPayRoll(String payRoll) {
        this.payRoll = payRoll;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDevision() {
        return devision;
    }

    public void setDevision(String devision) {
        this.devision = devision;
    }

    public String getCommencingDate() {
        return commencingDate;
    }

    public void setCommencingDate(String commencingDate) {
        this.commencingDate = commencingDate;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(String dateRequest) {
        this.dateRequest = dateRequest;
    }

    public String getReasonUnpaid() {
        return reasonUnpaid;
    }

    public void setReasonUnpaid(String reasonUnpaid) {
        this.reasonUnpaid = reasonUnpaid;
    }

    public String getAppHrManager() {
        return appHrManager;
    }

    public void setAppHrManager(String appHrManager) {
        this.appHrManager = appHrManager;
    }

    public String getAppDepHead() {
        return appDepHead;
    }

    public void setAppDepHead(String appDepHead) {
        this.appDepHead = appDepHead;
    }

    public String getAppGm() {
        return appGm;
    }

    public void setAppGm(String appGm) {
        this.appGm = appGm;
    }

    public Vector getAnnualLeave() {
        return annualLeave;
    }

    public void setAnnualLeave(Vector annualLeave) {
        this.annualLeave = annualLeave;
    }

    public Vector getLongLeave() {
        return longLeave;
    }

    public void setLongLeave(Vector longLeave) {
        this.longLeave = longLeave;
    }

    public Vector getSpecialLeave() {
        return specialLeave;
    }

    public void setSpecialLeave(Vector specialLeave) {
        this.specialLeave = specialLeave;
    }

    public String getAppHrDate() {
        return appHrDate;
    }

    public void setAppHrDate(String appHrDate) {
        this.appHrDate = appHrDate;
    }

    public String getAppDepDate() {
        return appDepDate;
    }

    public void setAppDepDate(String appDepDate) {
        this.appDepDate = appDepDate;
    }

    public String getAppGmDate() {
        return appGmDate;
    }

    public void setAppGmDate(String appGmDate) {
        this.appGmDate = appGmDate;
    }

    public Vector getDp() {
        return dp;
    }

    public void setDp(Vector dp) {
        this.dp = dp;
    }
    
}
