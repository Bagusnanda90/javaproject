/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.employee.assessment;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Satrya Ramayu
 */
public class AssessmentFormMainDetail extends  Entity{
    private long groupRankId;
     private long assMainDetail;

    /**
     * @return the groupRankId
     */
    public long getGroupRankId() {
        return groupRankId;
    }

    /**
     * @param groupRankId the groupRankId to set
     */
    public void setGroupRankId(long groupRankId) {
        this.groupRankId = groupRankId;
    }

    /**
     * @return the assMainDetail
     */
    public long getAssMainDetail() {
        return assMainDetail;
    }

    /**
     * @param assMainDetail the assMainDetail to set
     */
    public void setAssMainDetail(long assMainDetail) {
        this.assMainDetail = assMainDetail;
    }
   
    
}
