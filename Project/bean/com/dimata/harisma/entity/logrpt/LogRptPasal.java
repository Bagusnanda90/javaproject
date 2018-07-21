
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.logrpt; 
 
/* package java */ 

/* package qdep */
import com.dimata.qdep.entity.*;

public class LogRptPasal extends Entity { 

  private long reportId;
  private long pasalUmumId;
  private long pasalKhususId;
  private String pasalUmum="";
  private String pasalKhusus="";

    /**
     * @return the reportId
     */
    public long getReportId() {
        return reportId;
    }

    /**
     * @param reportId the reportId to set
     */
    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    /**
     * @return the pasalUmumId
     */
    public long getPasalUmumId() {
        return pasalUmumId;
    }

    /**
     * @param pasalUmumId the pasalUmumId to set
     */
    public void setPasalUmumId(long pasalUmumId) {
        this.pasalUmumId = pasalUmumId;
    }

    /**
     * @return the pasalKhususId
     */
    public long getPasalKhususId() {
        return pasalKhususId;
    }

    /**
     * @param pasalKhususId the pasalKhususId to set
     */
    public void setPasalKhususId(long pasalKhususId) {
        this.pasalKhususId = pasalKhususId;
    }

    /**
     * @return the pasalUmum
     */
    public String getPasalUmum() {
        return pasalUmum;
    }

    /**
     * @param pasalUmum the pasalUmum to set
     */
    public void setPasalUmum(String pasalUmum) {
        this.pasalUmum = pasalUmum;
    }

    /**
     * @return the pasalKhusus
     */
    public String getPasalKhusus() {
        return pasalKhusus;
    }

    /**
     * @param pasalKhusus the pasalKhusus to set
     */
    public void setPasalKhusus(String pasalKhusus) {
        this.pasalKhusus = pasalKhusus;
    }

}
