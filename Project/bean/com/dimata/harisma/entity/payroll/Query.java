/*
 * Query.java
 *
 * Created on August 3, 2007, 11:57 AM
 */

package com.dimata.harisma.entity.payroll;


/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  yunny
 */
public class Query extends Entity {
    // private long genId;
	private String reportTitle="";
	private String reportSubtitle="";
	private String whereParam = "";
	private String orderByParam="";
	private String groupByParam="";
	private String query = "";
        private String subQuery = "";
	private Date date;
        private String description= "";
	
    
    /** Creates a new instance of Query */
    public Query() {
    }
    
    /**
     * Getter for property reportTitle.
     * @return Value of property reportTitle.
     */
    public String getReportTitle() {
        return reportTitle;
    }
    
    /**
     * Setter for property reportTitle.
     * @param reportTitle New value of property reportTitle.
     */
    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }
    
    /**
     * Getter for property reportSubtitle.
     * @return Value of property reportSubtitle.
     */
    public String getReportSubtitle() {
        return reportSubtitle;
    }
    
    /**
     * Setter for property reportSubtitle.
     * @param reportSubtitle New value of property reportSubtitle.
     */
    public void setReportSubtitle(String reportSubtitle) {
        this.reportSubtitle = reportSubtitle;
    }
    
    /**
     * Getter for property whereParam.
     * @return Value of property whereParam.
     */
    public String getWhereParam() {
        return whereParam;
    }
    
    /**
     * Setter for property whereParam.
     * @param whereParam New value of property whereParam.
     */
    public void setWhereParam(String whereParam) {
        this.whereParam = whereParam;
    }
    
    /**
     * Getter for property orderByParam.
     * @return Value of property orderByParam.
     */
    public String getOrderByParam() {
        return orderByParam;
    }
    
    /**
     * Setter for property orderByParam.
     * @param orderByParam New value of property orderByParam.
     */
    public void setOrderByParam(String orderByParam) {
        this.orderByParam = orderByParam;
    }
    
    /**
     * Getter for property groupByParam.
     * @return Value of property groupByParam.
     */
    public String getGroupByParam() {
        return groupByParam;
    }
    
    /**
     * Setter for property groupByParam.
     * @param groupByParam New value of property groupByParam.
     */
    public void setGroupByParam(String groupByParam) {
        this.groupByParam = groupByParam;
    }
    
    /**
     * Getter for property query.
     * @return Value of property query.
     */
    public String getQuery() {
        return query;
    }
    
    /**
     * Setter for property query.
     * @param query New value of property query.
     */
    public void setQuery(String query) {
        this.query = query;
    }
    
    /**
     * Getter for property date.
     * @return Value of property date.
     */
    public Date getDate() {
        return date;
    }
    
    /**
     * Setter for property date.
     * @param date New value of property date.
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    /**
     * Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Getter for property subQuery.
     * @return Value of property subQuery.
     */
    public String getSubQuery() {
        return subQuery;
    }
    
    /**
     * Setter for property subQuery.
     * @param subQuery New value of property subQuery.
     */
    public void setSubQuery(String subQuery) {
        this.subQuery = subQuery;
    }
    
}
