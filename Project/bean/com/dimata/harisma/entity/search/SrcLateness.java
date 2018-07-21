/**
 * Created by IntelliJ IDEA.
 * User: gadnyana
 * Date: Apr 7, 2004
 * Time: 3:24:43 PM
 * To change this template use Options | File Templates.
 */
package com.dimata.harisma.entity.search;

import java.util.Date;

public class SrcLateness {
    public static final int TYPE_DAILY_LATENESS = 0;
    public static final int TYPE_WEEKLY_LATENESS = 1;
    public static final int TYPE_MONTHLY_LATENESS = 2;

    private Date dtLateness;
    private Date dtToLateness;
    private long departmentId;
    private int type;

    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }

    public void setDtToLateness(Date toLateness){
        this.dtToLateness = toLateness;
    }

    public Date getDtToLateness(){
        return dtToLateness;
    }

    public void setDtLateness(Date lateness){
        this.dtLateness =lateness ;
    }

    public Date getDtLateness(){
        return dtLateness;
    }

    public void setDepartmentId(long departmentId){
        this.departmentId = departmentId;
    }

    public long getDepartmentId(){
        return departmentId;
    }

}
