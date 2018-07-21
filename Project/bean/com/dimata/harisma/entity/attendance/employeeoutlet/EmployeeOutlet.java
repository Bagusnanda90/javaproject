/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance.employeeoutlet;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class EmployeeOutlet extends Entity{
    private long locationId;
    private Date dtFrom;
    private Date dtTo;
    private long positionId;
    private long employeeId;
    //private long schedleSymbolId;
    //private long schedleSymbolId2nd;
    private int scheduleType;

   private String objTableOutlet;
   private String employeeName;
    /**
     * @return the dtFrom
     */
    public Date getDtFrom() {
        return dtFrom;
    }

    /**
     * @param dtFrom the dtFrom to set
     */
    public void setDtFrom(Date dtFrom) {
        this.dtFrom = dtFrom;
    }

    /**
     * @return the dtTo
     */
    public Date getDtTo() {
        return dtTo;
    }

    /**
     * @param dtTo the dtTo to set
     */
    public void setDtTo(Date dtTo) {
        this.dtTo = dtTo;
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
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the locationId
     */
    public long getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the objTableOutlet
     */
    public String getObjTableOutlet(int colom,String color) {
        String tableKu="";
        if(colom==0){
            return "";
        }
        tableKu =
                "<table>"
                    + " <tr>" ;
                                if(colom==1){
                                    for(int x=1;x<=8;x++){
                                        if(x==2 || x==3 || x==4 || x==5 || x==6 || x==7 || x==8){
                                            tableKu = tableKu + "<td bgcolor=\"#FFFFFF\"></td>";
                                        }else{
                                            tableKu = tableKu + "<td bgcolor=\""+color+"\"></td>";
                                        }
                                    }
                                }else if(colom==2){
                                    //tableKu = tableKu + "<td bgcolor=\"#FFFFFF\"></td>";
                                    for(int x=1;x<=8;x++){
                                        if(x==3 || x==4 || x==5 || x==6 || x==7 || x==8){
                                            tableKu = tableKu + "<td bgcolor=\"#FFFFFF\"></td>";
                                        }else{
                                            tableKu = tableKu + "<td bgcolor=\""+color+"\"></td>";
                                        }
                                    }
                                    
                                }else if(colom==3){
                                    for(int x=1;x<=8;x++){
                                        if(x==4 || x==5 || x==6 || x==7 || x==8){
                                            tableKu = tableKu + "<td bgcolor=\"#FFFFFF\"></td>";
                                        }else{
                                            tableKu = tableKu + "<td bgcolor=\""+color+"\"></td>";
                                        }
                                    }
                                }else if(colom==4){
                                    for(int x=1;x<=8;x++){
                                        if(x==5 || x==6 || x==7 || x==8){
                                            tableKu = tableKu + "<td bgcolor=\"#FFFFFF\"></td>";
                                        }else{
                                            tableKu = tableKu + "<td bgcolor=\""+color+"\"></td>";
                                        
                                        }
                                    }
                                }else if(colom==5){
                                     for(int x=1;x<=8;x++){
                                        if(x==6 || x==7 || x==8){
                                            tableKu = tableKu + "<td bgcolor=\"#FFFFFF\"></td>";
                                        }else{
                                            tableKu = tableKu + "<td bgcolor=\""+color+"\"></td>";
                                        
                                        }
                                    }
                                }else if(colom==6){
                                     for(int x=1;x<=8;x++){
                                        if(x==7 || x==8){
                                            tableKu = tableKu + "<td bgcolor=\"#FFFFFF\"></td>";
                                        }else{
                                            tableKu = tableKu + "<td bgcolor=\""+color+"\"></td>";
                                        
                                        }
                                    }
                                }else if(colom==7){
                                    for(int x=1;x<=8;x++){
                                        if(x==7){
                                            tableKu = tableKu + "<td bgcolor=\"#FFFFFF\"></td>";
                                        }else{
                                            tableKu = tableKu + "<td bgcolor=\""+color+"\"></td>";
                                        }
                                    }
                                }else if(colom==8){
                                   for(int x=1;x<=8;x++){
                                       tableKu = tableKu + "<td bgcolor=\""+color+"\"></td>";
                                    }
                                }
                    tableKu = tableKu + "</tr>" + "</table>";
                   objTableOutlet = tableKu; 
        return objTableOutlet;
    }

    /**
     * @return the scheduleType
     */
    public int getScheduleType() {
        return scheduleType;
    }

    /**
     * @param scheduleType the scheduleType to set
     */
    public void setScheduleType(int scheduleType) {
        this.scheduleType = scheduleType;
    }

    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }


   
}
