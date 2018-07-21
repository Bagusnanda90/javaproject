/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.payroll;

/**
 * Description :
 * Date :
 * @author Hendra Putu
 */
import com.dimata.qdep.entity.Entity;
import java.util.Date;

public class CustomRptMain extends Entity {

    private String rptMainTitle = "";
    private String rptMainDesc = "";
    private Date rptMainDateCreate = null;
    private long rptMainCreatedBy = 0;
    private long rptMainPrivLevel = 0;
    private long rptMainPrivPos = 0;

    public String getRptMainTitle() {
        return rptMainTitle;
    }

    public void setRptMainTitle(String rptMainTitle) {
        this.rptMainTitle = rptMainTitle;
    }

    public String getRptMainDesc() {
        return rptMainDesc;
    }

    public void setRptMainDesc(String rptMainDesc) {
        this.rptMainDesc = rptMainDesc;
    }

    public Date getRptMainDateCreate() {
        return rptMainDateCreate;
    }

    public void setRptMainDateCreate(Date rptMainDateCreate) {
        this.rptMainDateCreate = rptMainDateCreate;
    }

    public long getRptMainCreatedBy() {
        return rptMainCreatedBy;
    }

    public void setRptMainCreatedBy(long rptMainCreatedBy) {
        this.rptMainCreatedBy = rptMainCreatedBy;
    }

    public long getRptMainPrivLevel() {
        return rptMainPrivLevel;
    }

    public void setRptMainPrivLevel(long rptMainPrivLevel) {
        this.rptMainPrivLevel = rptMainPrivLevel;
    }

    public long getRptMainPrivPos() {
        return rptMainPrivPos;
    }

    public void setRptMainPrivPos(long rptMainPrivPos) {
        this.rptMainPrivPos = rptMainPrivPos;
    }
}
