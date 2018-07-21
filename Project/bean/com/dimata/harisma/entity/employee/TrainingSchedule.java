
package com.dimata.harisma.entity.employee;

// import java
import java.util.Date;

// import qdep
import com.dimata.qdep.entity.*;

/**
 *
 * @author bayu
 */

public class TrainingSchedule extends Entity {
    
    private long trainPlanId = 0;
    private Date trainDate = new Date();
    private Date startTime = new Date();
    private Date endTime = new Date();
    private long trainVenueId = 0;
    private Date trainEndDate = new Date();
    private int totalHour =0;
    
    
    public TrainingSchedule() {
    }
    

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(Date trainDate) {
        this.trainDate = trainDate;
    }

    public long getTrainPlanId() {
        return trainPlanId;
    }

    public void setTrainPlanId(long trainPlanId) {
        this.trainPlanId = trainPlanId;
    }

    public long getTrainVenueId() {
        return trainVenueId;
    }

    public void setTrainVenueId(long trainVenueId) {
        this.trainVenueId = trainVenueId;
    }    

    /**
     * @return the trainEndDate
     */
    public Date getTrainEndDate() {
        return trainEndDate;
    }

    /**
     * @param trainEndDate the trainEndDate to set
     */
    public void setTrainEndDate(Date trainEndDate) {
        this.trainEndDate = trainEndDate;
    }

    /**
     * @return the totalHour
     */
    public int getTotalHour() {
        return totalHour;
    }

    /**
     * @param totalHour the totalHour to set
     */
    public void setTotalHour(int totalHour) {
        this.totalHour = totalHour;
    }
    
}
