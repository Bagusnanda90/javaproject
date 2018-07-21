/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.employee.appraisal;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Administrator
 */
public class Appraisal extends Entity{
    /*
     HR_APPRAISAL_ID      BIGINT,
       EMP_COMMENT          text,
       ASS_COMMENT          text,
       RATING               FLOAT,
       ASS_FORM_ITEM_ID     BIGINT,
       HR_APP_MAIN_ID       BIGINT NOT NULL
     */
    private long appMainId = 0;
    private long assFormItemId = 0;
    private String empComment = "";
    private String assComment = "";
    private double rating = 0;
    private String answer_1 = "";
    private String answer_2 = "";
    private String answer_3 = "";
    private String answer_4 = "";
    private String answer_5 = "";
    private String answer_6 = "";
    private float realization=0.0f;
    private String evidence="";
    private float point=0.0f;

    public long getAppMainId() {
        return appMainId;
    }

    public void setAppMainId(long appMainId) {
        this.appMainId = appMainId;
    }

    public String getAssComment() {
        return assComment;
    }

    public void setAssComment(String assComment) {
        this.assComment = assComment;
    }

    public long getAssFormItemId() {
        return assFormItemId;
    }

    public void setAssFormItemId(long assFormItemId) {
        this.assFormItemId = assFormItemId;
    }

    public String getEmpComment() {
        return empComment;
    }

    public void setEmpComment(String empComment) {
        this.empComment = empComment;
    }

    public String getAnswer_1() {
        return answer_1;
    }

    public void setAnswer_1(String answer_1) {
        this.answer_1 = answer_1;
    }

    public String getAnswer_2() {
        return answer_2;
    }

    public void setAnswer_2(String answer_2) {
        this.answer_2 = answer_2;
    }

    public String getAnswer_3() {
        return answer_3;
    }

    public void setAnswer_3(String answer_3) {
        this.answer_3 = answer_3;
    }

    public String getAnswer_4() {
        return answer_4;
    }

    public void setAnswer_4(String answer_4) {
        this.answer_4 = answer_4;
    }

    public String getAnswer_5() {
        return answer_5;
    }

    public void setAnswer_5(String answer_5) {
        this.answer_5 = answer_5;
    }

    public String getAnswer_6() {
        return answer_6;
    }

    public void setAnswer_6(String answer_6) {
        this.answer_6 = answer_6;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * @return the realization
     */
    public float getRealization() {
        return realization;
    }

    /**
     * @param realization the realization to set
     */
    public void setRealization(float realization) {
        this.realization = realization;
    }

    /**
     * @return the evidence
     */
    public String getEvidence() {
        return evidence;
    }

    /**
     * @param evidence the evidence to set
     */
    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    /**
     * @return the point
     */
    public float getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(float point) {
        this.point = point;
    }

    
    
}
