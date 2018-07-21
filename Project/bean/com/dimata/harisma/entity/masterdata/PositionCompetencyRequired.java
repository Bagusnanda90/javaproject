/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author Hendra McHen | 2015-02-02
 */
public class PositionCompetencyRequired extends Entity {
    private long positionId = 0;
    private long competencyId = 0;
    private float scoreReqMin = 0;
    private float scoreReqRecommended = 0;
    private int competencyLevelIdMin = 0;
    private int competencyLevelIdRecommended = 0;
    private long competencyLevelId = 0;
    private String note = "";
    private int reTrainOrSertfcReq = 0;
    private Date validStart = new Date();
    private Date validEnd = new Date();

    /**
     * @return the competencyId
     */
    public long getCompetencyId() {
        return competencyId;
    }

    /**
     * @param competencyId the competencyId to set
     */
    public void setCompetencyId(long competencyId) {
        this.competencyId = competencyId;
    }

    /**
     * @return the scoreReqMin
     */
    public float getScoreReqMin() {
        return scoreReqMin;
    }

    /**
     * @param scoreReqMin the scoreReqMin to set
     */
    public void setScoreReqMin(float scoreReqMin) {
        this.scoreReqMin = scoreReqMin;
    }

    /**
     * @return the scoreReqRecommended
     */
    public float getScoreReqRecommended() {
        return scoreReqRecommended;
    }

    /**
     * @param scoreReqRecommended the scoreReqRecommended to set
     */
    public void setScoreReqRecommended(float scoreReqRecommended) {
        this.scoreReqRecommended = scoreReqRecommended;
    }

    /**
     * @return the competencyLevelIdMin
     */
    public int getCompetencyLevelIdMin() {
        return competencyLevelIdMin;
    }

    /**
     * @param competencyLevelIdMin the competencyLevelIdMin to set
     */
    public void setCompetencyLevelIdMin(int competencyLevelIdMin) {
        this.competencyLevelIdMin = competencyLevelIdMin;
    }

    /**
     * @return the competencyLevelIdRecommended
     */
    public int getCompetencyLevelIdRecommended() {
        return competencyLevelIdRecommended;
    }

    /**
     * @param competencyLevelIdRecommended the competencyLevelIdRecommended to set
     */
    public void setCompetencyLevelIdRecommended(int competencyLevelIdRecommended) {
        this.competencyLevelIdRecommended = competencyLevelIdRecommended;
    }

    /**
     * @return the competencyLevelId
     */
    public long getCompetencyLevelId() {
        return competencyLevelId;
    }

    /**
     * @param competencyLevelId the competencyLevelId to set
     */
    public void setCompetencyLevelId(long competencyLevelId) {
        this.competencyLevelId = competencyLevelId;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
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
     * @return the reTrainOrSertfcReq
     */
    public int getReTrainOrSertfcReq() {
        return reTrainOrSertfcReq;
    }

    /**
     * @param reTrainOrSertfcReq the reTrainOrSertfcReq to set
     */
    public void setReTrainOrSertfcReq(int reTrainOrSertfcReq) {
        this.reTrainOrSertfcReq = reTrainOrSertfcReq;
    }

    /**
     * @return the validStart
     */
    public Date getValidStart() {
        return validStart;
    }

    /**
     * @param validStart the validStart to set
     */
    public void setValidStart(Date validStart) {
        this.validStart = validStart;
    }

    /**
     * @return the validEnd
     */
    public Date getValidEnd() {
        return validEnd;
    }

    /**
     * @param validEnd the validEnd to set
     */
    public void setValidEnd(Date validEnd) {
        this.validEnd = validEnd;
    }
}
