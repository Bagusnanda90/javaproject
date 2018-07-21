/*
 * LeaveApplication.java
 *
 * Created on October 27, 2004, 11:51 AM
 */

package com.dimata.harisma.entity.leave;

import com.dimata.qdep.entity.Entity; 
import java.util.Date;

/**
 *
 * @author  gedhy
 */
public class DPUpload extends Entity
{
    
    private long EmployeeId = 0;
    private Date OpnameDate = new Date();
    private int DataStatus = 0;
    private Date AcquisitionDate;
    private float DPNumber  = 0;    
    private long dpStockId;
    private long dpUploadId;
    private float dpStock = 0;

   
    public int getDataStatus() {
        return DataStatus;
    }

    public void setDataStatus(int DataStatus) {
        this.DataStatus = DataStatus;
    }

    public long getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(long EmployeeId) {
        this.EmployeeId = EmployeeId;
    }

  
    public Date getOpnameDate() {
        return OpnameDate;
    }

    public void setOpnameDate(Date OpnameDate) {
        this.OpnameDate = OpnameDate;
    }

    public Date getAcquisitionDate() {
        return AcquisitionDate;
    }

    public void setAcquisitionDate(Date AcquisitionDate) {
        this.AcquisitionDate = AcquisitionDate;
    }

    public float getDPNumber() {
        return DPNumber;
    }

    public void setDPNumber(float DPNumber) {
        this.DPNumber = DPNumber;
    }

    public float getDpStock() {
        return dpStock;
    }

    public void setDpStock(float dpStock) {
        this.dpStock = dpStock;
    }

    public long getDpStockId() {
        return dpStockId;
    }

    public void setDpStockId(long dpStockId) {
        this.dpStockId = dpStockId;
    }

    public long getDpUploadId() {
        return dpUploadId;
    }

    public void setDpUploadId(long dpUploadId) {
        this.dpUploadId = dpUploadId;
    }
}
