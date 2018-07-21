/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.overtime;

/**
 *
 * @author Gede115
 */
import java.util.Date;
import java.util.Vector;
import com.dimata.qdep.entity.*;

public class OvertimeReport {
    private long overtimeDetailId;
    private long overtimeId;
    private long employeeId = 0;
    private String payroll="";
    private Date dateFrom = new Date();
    private Date dateTo = new Date();
    private int status;


public static Vector<String> getStatusAttString(){
        Vector sts = new Vector();
        for(int i=0;i< I_DocStatus.fieldDocumentStatus.length;i++){
            sts.add(I_DocStatus.fieldDocumentStatus[i]);
        }
        return sts;
    }

    public static Vector<String> getStatusIndexString(){
        Vector sts = new Vector();
        for(int i=0;i< I_DocStatus.fieldDocumentStatus.length;i++){
            sts.add(""+i);
        }
        return sts;
    }
    
 public static Vector<String> getStatusAttString(int[] exceptStatus){
        Vector sts = new Vector();
        for(int i=0;i< I_DocStatus.fieldDocumentStatus.length;i++){
            boolean addStatus =true;
            if(exceptStatus!=null){
            for(int idx=0; idx <exceptStatus.length;idx++){
                  if(i==exceptStatus[idx]){
                      addStatus = false;
                      break;
                  }
               }
            } 
            if(addStatus){
             sts.add(I_DocStatus.fieldDocumentStatus[i]);
            }
        }
        return sts;
    }

    public static Vector<String> getStatusIndexString(int[] exceptStatus){
        Vector sts = new Vector();
        for(int i=0;i< I_DocStatus.fieldDocumentStatus.length;i++){
            boolean addStatus =true;
            if(exceptStatus!=null){
            for(int idx=0; idx <exceptStatus.length;idx++){
                  if(i==exceptStatus[idx]){
                      addStatus = false;
                      break;
                  }
               }
            } 
            if(addStatus){
                sts.add(""+i);            
            }
        }
        return sts;
    }
        
    
    
    

    /**
     * @return the overtimeDetailId
     */
    public long getOvertimeDetailId() {
        return overtimeDetailId;
    }

    /**
     * @param overtimeDetailId the overtimeDetailId to set
     */
    public void setOvertimeDetailId(long overtimeDetailId) {
        this.overtimeDetailId = overtimeDetailId;
    }

    /**
     * @return the overtimeId
     */
    public long getOvertimeId() {
        return overtimeId;
    }

    /**
     * @param overtimeId the overtimeId to set
     */
    public void setOvertimeId(long overtimeId) {
        this.overtimeId = overtimeId;
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
     * @return the payroll
     */
    public String getPayroll() {
        return payroll;
    }

    /**
     * @param payroll the payroll to set
     */
    public void setPayroll(String payroll) {
        this.payroll = payroll;
    }

    /**
     * @return the dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom the dateFrom to set
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the dateTo
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * @param dateTo the dateTo to set
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

}
