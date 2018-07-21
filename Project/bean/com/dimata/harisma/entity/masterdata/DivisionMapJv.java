/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Dimata 007
 */
import com.dimata.qdep.entity.Entity;

public class DivisionMapJv extends Entity {

    private long divisionId = 0;
    private String divisionName = "";
    private long divisionCodeId = 0;

    public long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public long getDivisionCodeId() {
        return divisionCodeId;
    }

    public void setDivisionCodeId(long divisionCodeId) {
        this.divisionCodeId = divisionCodeId;
    }
}