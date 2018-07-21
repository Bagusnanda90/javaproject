/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.employee.assessment;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Artha
 */
public class AssessmentFormSection extends Entity{
    private long assFormMainId;
    private String section;
    private String description;
    private String section_L2;
    private String description_L2;
    private int orderNumber;
    private int page;
    private int type;
    private long pointEvaluationId=0;
    private long predicateEvaluationId=0;
    private float weightPoint =0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    
    
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
    
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public long getAssFormMainId() {
        return assFormMainId;
    }

    public void setAssFormMainId(long assFormMainId) {
        this.assFormMainId = assFormMainId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_L2() {
        return description_L2;
    }

    public void setDescription_L2(String description_L2) {
        this.description_L2 = description_L2;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSection_L2() {
        return section_L2;
    }

    public void setSection_L2(String section_L2) {
        this.section_L2 = section_L2;
    }

    /**
     * @return the pointEvaluationId
     */
    public long getPointEvaluationId() {
        return pointEvaluationId;
    }

    /**
     * @param pointEvaluationId the pointEvaluationId to set
     */
    public void setPointEvaluationId(long pointEvaluationId) {
        this.pointEvaluationId = pointEvaluationId;
    }

    /**
     * @return the predicateEvaluationId
     */
    public long getPredicateEvaluationId() {
        return predicateEvaluationId;
    }

    /**
     * @param predicateEvaluationId the predicateEvaluationId to set
     */
    public void setPredicateEvaluationId(long predicateEvaluationId) {
        this.predicateEvaluationId = predicateEvaluationId;
    }

    /**
     * @return the weightPoint
     */
    public float getWeightPoint() {
        return weightPoint;
    }

    /**
     * @param weightPoint the weightPoint to set
     */
    public void setWeightPoint(float weightPoint) {
        this.weightPoint = weightPoint;
    }

   
    
}
