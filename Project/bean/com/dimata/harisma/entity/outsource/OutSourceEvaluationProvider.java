/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author GUSWIK
 */
public class OutSourceEvaluationProvider extends  Entity{
    private long outsourceEvalProviderId = 0;
    private long outsourceEvalId = 0;
    private long positionId = 0;
    private long providerId = 0;
    private int numberOfPerson = 0;
    private int evalPoint = 0;
    private String justification = "";
    private String suggestion = "";

    /**
     * @return the outsourceEvalProviderId
     */
    public long getOutsourceEvalProviderId() {
        return outsourceEvalProviderId;
    }

    /**
     * @param outsourceEvalProviderId the outsourceEvalProviderId to set
     */
    public void setOutsourceEvalProviderId(long outsourceEvalProviderId) {
        this.outsourceEvalProviderId = outsourceEvalProviderId;
    }

    /**
     * @return the outsourceEvalId
     */
    public long getOutsourceEvalId() {
        return outsourceEvalId;
    }

    /**
     * @param outsourceEvalId the outsourceEvalId to set
     */
    public void setOutsourceEvalId(long outsourceEvalId) {
        this.outsourceEvalId = outsourceEvalId;
    }

    /**
     * @return the positionId
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    /**
     * @return the providerId
     */
    public long getProviderId() {
        return providerId;
    }

    /**
     * @param providerId the providerId to set
     */
    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    /**
     * @return the numberOfPerson
     */
    public int getNumberOfPerson() {
        return numberOfPerson;
    }

    /**
     * @param numberOfPerson the numberOfPerson to set
     */
    public void setNumberOfPerson(int numberOfPerson) {
        this.numberOfPerson = numberOfPerson;
    }

    /**
     * @return the evalPoint
     */
    public int getEvalPoint() {
        return evalPoint;
    }

    /**
     * @param evalPoint the evalPoint to set
     */
    public void setEvalPoint(int evalPoint) {
        this.evalPoint = evalPoint;
    }

    /**
     * @return the justification
     */
    public String getJustification() {
        return justification;
    }

    /**
     * @param justification the justification to set
     */
    public void setJustification(String justification) {
        this.justification = justification;
    }

    /**
     * @return the suggestion
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     * @param suggestion the suggestion to set
     */
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
