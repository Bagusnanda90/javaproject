/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.search;

import com.dimata.harisma.entity.payroll.PstPaySimulation;
import com.dimata.util.lang.I_Language;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Kartika
 */
public class SrcPaySimulation {

    /**
     * @return the StrSortrBy
     */
    public static String[][][] getStrSortrBy() {
        return StrSortrBy;
    }

    /**
     * @param aStrSortrBy the StrSortrBy to set
     */
    public static void setStrSortrBy(String[][][] aStrSortrBy) {
        StrSortrBy = aStrSortrBy;
    }
    private String title = "";
    private String objectives = "";
    private Date createdDate = null;
    private long creadedById = 0;
    private Date requestDateFrom = null;
    private Date requestDateTo = null;
    private long requestedById = 0;
    private Date dueDateFrom = null;
    private Date dueDateTo = null;
    private Vector<Integer> statusDoc = new Vector();
    private double maxTotalBudgetFrom = 0;
    private double maxTotalBudgetTo = 0;
    private int maxAddEmployeeFrom = 0;
    private int maxAddEmployeeTo = 0;
    private long sourcePayPeriodId = 0;
    private String sortBy = "";
    private int recordStartFrom = 0;
    private int recordToGet = 50;

    private static String[][][] StrSortrBy = {
        {{"Judul", PstPaySimulation.fieldNames[PstPaySimulation.FLD_TITLE]},
        {"Tujuan", PstPaySimulation.fieldNames[PstPaySimulation.FLD_OBJECTIVES]},
        {"Tanggal buat", PstPaySimulation.fieldNames[PstPaySimulation.FLD_CREATED_DATE]},},
        {{"Title", PstPaySimulation.fieldNames[PstPaySimulation.FLD_TITLE]},
        {"Objectives", PstPaySimulation.fieldNames[PstPaySimulation.FLD_OBJECTIVES]},
        {"Created date", PstPaySimulation.fieldNames[PstPaySimulation.FLD_CREATED_DATE]},}
    };

    public static Vector<String> getSortByValue(int language) {
        int lang = I_Language.LANGUAGE_DEFAULT;
        if (language != I_Language.LANGUAGE_DEFAULT) {
            lang = I_Language.LANGUAGE_FOREIGN;
        };
        Vector vct = new Vector();
        for (int i = 0; i < getStrSortrBy()[lang].length; i++) {
            vct.add(getStrSortrBy()[lang][i][1]);
        }
        return vct;
    }

    public static Vector<String> getSortByKey(int language) {
        int lang = I_Language.LANGUAGE_DEFAULT;
        if (language != I_Language.LANGUAGE_DEFAULT) {
            lang = I_Language.LANGUAGE_FOREIGN;
        };
        Vector vct = new Vector();
        for (int i = 0; i < getStrSortrBy()[lang].length; i++) {
            vct.add(getStrSortrBy()[lang][i][0]);
        }
        return vct;
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
     * @return the objectives
     */
    public String getObjectives() {
        return objectives;
    }

    /**
     * @param objectives the objectives to set
     */
    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the creadedById
     */
    public long getCreadedById() {
        return creadedById;
    }

    /**
     * @param creadedById the creadedById to set
     */
    public void setCreadedById(long creadedById) {
        this.creadedById = creadedById;
    }

    /**
     * @return the requestDateFrom
     */
    public Date getRequestDateFrom() {
        return requestDateFrom;
    }

    /**
     * @param requestDateFrom the requestDateFrom to set
     */
    public void setRequestDateFrom(Date requestDateFrom) {
        this.requestDateFrom = requestDateFrom;
    }

    /**
     * @return the requestDateTo
     */
    public Date getRequestDateTo() {
        return requestDateTo;
    }

    /**
     * @param requestDateTo the requestDateTo to set
     */
    public void setRequestDateTo(Date requestDateTo) {
        this.requestDateTo = requestDateTo;
    }

    /**
     * @return the requestedById
     */
    public long getRequestedById() {
        return requestedById;
    }

    /**
     * @param requestedById the requestedById to set
     */
    public void setRequestedById(long requestedById) {
        this.requestedById = requestedById;
    }

    /**
     * @return the dueDateFrom
     */
    public Date getDueDateFrom() {
        return dueDateFrom;
    }

    /**
     * @param dueDateFrom the dueDateFrom to set
     */
    public void setDueDateFrom(Date dueDateFrom) {
        this.dueDateFrom = dueDateFrom;
    }

    /**
     * @return the dueDateTo
     */
    public Date getDueDateTo() {
        return dueDateTo;
    }

    /**
     * @param dueDateTo the dueDateTo to set
     */
    public void setDueDateTo(Date dueDateTo) {
        this.dueDateTo = dueDateTo;
    }

    /**
     * @return the statusDoc
     */
    public Vector<Integer> getStatusDoc() {
        return statusDoc;
    }

    /**
     * @param statusDoc the statusDoc to set
     */
    public void setStatusDoc(Vector<Integer> statusDoc) {
        this.statusDoc = statusDoc;
    }

    /**
     * @return the maxTotalBudgetFrom
     */
    public double getMaxTotalBudgetFrom() {
        return maxTotalBudgetFrom;
    }

    /**
     * @param maxTotalBudgetFrom the maxTotalBudgetFrom to set
     */
    public void setMaxTotalBudgetFrom(double maxTotalBudgetFrom) {
        this.maxTotalBudgetFrom = maxTotalBudgetFrom;
    }

    /**
     * @return the maxTotalBudgetTo
     */
    public double getMaxTotalBudgetTo() {
        return maxTotalBudgetTo;
    }

    /**
     * @param maxTotalBudgetTo the maxTotalBudgetTo to set
     */
    public void setMaxTotalBudgetTo(double maxTotalBudgetTo) {
        this.maxTotalBudgetTo = maxTotalBudgetTo;
    }

    /**
     * @return the maxAddEmployeeFrom
     */
    public int getMaxAddEmployeeFrom() {
        return maxAddEmployeeFrom;
    }

    /**
     * @param maxAddEmployeeFrom the maxAddEmployeeFrom to set
     */
    public void setMaxAddEmployeeFrom(int maxAddEmployeeFrom) {
        this.maxAddEmployeeFrom = maxAddEmployeeFrom;
    }

    /**
     * @return the maxAddEmployeeTo
     */
    public int getMaxAddEmployeeTo() {
        return maxAddEmployeeTo;
    }

    /**
     * @param maxAddEmployeeTo the maxAddEmployeeTo to set
     */
    public void setMaxAddEmployeeTo(int maxAddEmployeeTo) {
        this.maxAddEmployeeTo = maxAddEmployeeTo;
    }

    /**
     * @return the sourcePayPeriodId
     */
    public long getSourcePayPeriodId() {
        return sourcePayPeriodId;
    }

    /**
     * @param sourcePayPeriodId the sourcePayPeriodId to set
     */
    public void setSourcePayPeriodId(long sourcePayPeriodId) {
        this.sourcePayPeriodId = sourcePayPeriodId;
    }

    /**
     * @return the sortBy
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * @param sortBy the sortBy to set
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * @return the recordStartFrom
     */
    public int getRecordStartFrom() {
        return recordStartFrom;
    }

    /**
     * @param recordStartFrom the recordStartFrom to set
     */
    public void setRecordStartFrom(int recordStartFrom) {
        this.recordStartFrom = recordStartFrom;
    }

    /**
     * @return the recordToGet
     */
    public int getRecordToGet() {
        return recordToGet;
    }

    /**
     * @param recordToGet the recordToGet to set
     */
    public void setRecordToGet(int recordToGet) {
        this.recordToGet = recordToGet;
    }

    public String getWhere() {
        String where = "";
        if (title != null && title.length() > 0) {
            where = PstPaySimulation.fieldNames[PstPaySimulation.FLD_TITLE] + " LIKE \"%" + title + "%\"";
        }
        if (objectives != null && objectives.length() > 0) {
            if (where.length() > 0) {
                where = where + " AND " + PstPaySimulation.fieldNames[PstPaySimulation.FLD_OBJECTIVES] + " LIKE \"%" + objectives + "%\"";
            } else {
                where = PstPaySimulation.fieldNames[PstPaySimulation.FLD_OBJECTIVES] + " LIKE \"%" + objectives + "%\"";
            }
        }

        return where;
    }
}
