/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author khirayinnura
 */
public class InOutReport {
    private long positionId = 0;
    private String positionName = "";
    private long providerId = 0;
    private String providerName = "";
    private long divisionId = 0;
    private String divisionName = "";
    private int in = 0;
    private int out = 0;

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
     * @return the divisionId
     */
    public long getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @return the in
     */
    public int getIn() {
        return in;
    }

    /**
     * @param in the in to set
     */
    public void setIn(int in) {
        this.in = in;
    }

    /**
     * @return the out
     */
    public int getOut() {
        return out;
    }

    /**
     * @param out the out to set
     */
    public void setOut(int out) {
        this.out = out;
    }

    /**
     * @return the positionName
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * @param positionName the positionName to set
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
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
     * @return the providerName
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * @param providerName the providerName to set
     */
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    /**
     * @return the divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @param divisionName the divisionName to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}
