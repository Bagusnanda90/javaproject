
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya
 * @version  	: 01
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.masterdata; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class Period extends Entity { 

	private String period = "";
	private Date startDate;
	private Date endDate;
        private int workDays = 0;
        private Date paySlipDate;
        private double minRegWage;
        private int payProcess;
        private String payProcBy = "";
        private Date payProcDate;
        private int payProcessClose;
        private String payProcByClose = "";
        private Date payProcDateClose;
        private int taxIsPaid = 0;        

	public String getPeriod(){ 
		return period; 
	} 

	public void setPeriod(String period){ 
		if ( period == null ) {
			period = ""; 
		} 
		this.period = period; 
	} 

	public Date getStartDate(){ 
		return startDate; 
	} 

	public void setStartDate(Date startDate){ 
		this.startDate = startDate; 
	} 

	public Date getEndDate(){ 
		return endDate; 
	} 

	public void setEndDate(Date endDate){ 
		this.endDate = endDate; 
	} 

        public int getDayInPeriod(){
            if(startDate==null || endDate==null){
                return 0;
            }
            Date stDate = new Date(startDate.getTime());
            Date nDate = new Date(endDate.getTime());
            stDate.setHours(12); stDate.setMinutes(0);stDate.setSeconds(0); // make the time of start end end date of periode same
            nDate.setHours(12);nDate.setMinutes(0);nDate.setSeconds(0);
            
            long stDt = stDate.getTime();
            long endDt = nDate.getTime();
            
            return (int) (((endDt - stDt) / (24*60*60*1000))+1); 
            
        }
        
        /**
         * Getter for property paySlipDate.
         * @return Value of property paySlipDate.
         */
        public java.util.Date getPaySlipDate() {
            return paySlipDate;
        }
        
        /**
         * Setter for property paySlipDate.
         * @param paySlipDate New value of property paySlipDate.
         */
        public void setPaySlipDate(java.util.Date paySlipDate) {
            this.paySlipDate = paySlipDate;
        }
        
        /**
         * Getter for property minRegWage.
         * @return Value of property minRegWage.
         */
        public double getMinRegWage() {
            return minRegWage;
        }
        
        /**
         * Setter for property minRegWage.
         * @param minRegWage New value of property minRegWage.
         */
        public void setMinRegWage(double minRegWage) {
            this.minRegWage = minRegWage;
        }
        
        /**
         * Getter for property payProcess.
         * @return Value of property payProcess.
         */
        public int getPayProcess() {
            return payProcess;
        }
        
        /**
         * Setter for property payProcess.
         * @param payProcess New value of property payProcess.
         */
        public void setPayProcess(int payProcess) {
            this.payProcess = payProcess;
        }
        
        /**
         * Getter for property payProcBy.
         * @return Value of property payProcBy.
         */
        public java.lang.String getPayProcBy() {
            return payProcBy;
        }
        
        /**
         * Setter for property payProcBy.
         * @param payProcBy New value of property payProcBy.
         */
        public void setPayProcBy(java.lang.String payProcBy) {
            this.payProcBy = payProcBy;
        }
        
        /**
         * Getter for property payProcDate.
         * @return Value of property payProcDate.
         */
        public java.util.Date getPayProcDate() {
            return payProcDate;
        }
        
        /**
         * Setter for property payProcDate.
         * @param payProcDate New value of property payProcDate.
         */
        public void setPayProcDate(java.util.Date payProcDate) {
            this.payProcDate = payProcDate;
        }
        
        /**
         * Getter for property taxIsPaid.
         * @return Value of property taxIsPaid.
         */
        public int getTaxIsPaid() {
            return taxIsPaid;
        }
        
        /**
         * Setter for property taxIsPaid.
         * @param taxIsPaid New value of property taxIsPaid.
         */
        public void setTaxIsPaid(int taxIsPaid) {
            this.taxIsPaid = taxIsPaid;
        }
        
        /**
         * Getter for property workDays.
         * @return Value of property workDays.
         */
        public int getWorkDays() {
            return workDays;
        }
        
        /**
         * Setter for property workDays.
         * @param workDays New value of property workDays.
         */
        public void setWorkDays(int workDays) {
            this.workDays = workDays;
        }
        
        /**
         * Getter for property payProcessClose.
         * @return Value of property payProcessClose.
         */
        public int getPayProcessClose() {
            return payProcessClose;
        }
        
        /**
         * Setter for property payProcessClose.
         * @param payProcessClose New value of property payProcessClose.
         */
        public void setPayProcessClose(int payProcessClose) {
            this.payProcessClose = payProcessClose;
        }
        
        /**
         * Getter for property payProcByClose.
         * @return Value of property payProcByClose.
         */
        public java.lang.String getPayProcByClose() {
            return payProcByClose;
        }
        
        /**
         * Setter for property payProcByClose.
         * @param payProcByClose New value of property payProcByClose.
         */
        public void setPayProcByClose(java.lang.String payProcByClose) {
            this.payProcByClose = payProcByClose;
        }
        
        /**
         * Getter for property payProcDateClose.
         * @return Value of property payProcDateClose.
         */
        public java.util.Date getPayProcDateClose() {
            return payProcDateClose;
        }
        
        /**
         * Setter for property payProcDateClose.
         * @param payProcDateClose New value of property payProcDateClose.
         */
        public void setPayProcDateClose(java.util.Date payProcDateClose) {
            this.payProcDateClose = payProcDateClose;
        }
        
        /**
         * Menentukan object tanggal 
         * @param date : tanggal 1 - 31
         * @return 
         */
        public Date getObjDateByIndex(int dateIdx ){
            if(dateIdx<1 || dateIdx > 31 || startDate==null || endDate==null){
                return null;
            } else {
               if(dateIdx < startDate.getDate() && dateIdx <= endDate.getDate()){
                   Date objDate = new Date(endDate.getTime());
                   objDate.setDate(dateIdx);
                   return objDate;
               } else {
                   Date objDate = new Date(startDate.getTime());
                   objDate.setDate(dateIdx);
                   return objDate;                   
               }
            }
        }
}
