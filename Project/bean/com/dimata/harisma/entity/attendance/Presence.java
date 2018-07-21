
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

package com.dimata.harisma.entity.attendance; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;
import java.util.Vector;
public class Presence extends Entity {         
        public final static int STATUS_IN =0;
        public final static int STATUS_OUT =1;
        public final static int STATUS_OUT_PERSONAL =2;
        public final static int STATUS_IN_PERSONAL =3;
        public final static int STATUS_OUT_ON_DUTY =4;
        public final static int STATUS_CALL_BACK =5;
        public final static int STATUS_INVALID =6;
        public final static int STATUS_TBD_IN_OUT =7; // in dan out ditentukan oleh jam punch in nya
        
        public final static String STATUS_ATT[] = {"IN", "OUT", 
            "OUT PERSONAL", "IN PERSONAL", "OUT ON DUTY", "CALL BACK", "INVALID","TBD IN OUT"};
        
        //update by satrya 2013-03-29
         public final static String STATUS_ATT_ISTIRAHAT[] = { 
            "OUT PERSONAL", "IN PERSONAL", "OUT ON DUTY", "CALL BACK"};
        public final static Integer STATUS_ATT_IDX[] = { 
            2, 3, 4, 5};
        //update by satrya 2012-08-21
        public final static int SCH_TYPE_NONE =0;
        public final static int SCH_TYPE_NORMAL =1;
        public final static int SCH_TYPE_AL =2;
        public final static int SCH_TYPE_LL =3;
        public final static int SCH_TYPE_DP =4;
        public final static int SCH_TYPE_SUL =5;
        
        public final static String SCH_TYPE_NAME[] = {"NONE", "SCHEDULE NORMAL", 
            "SCHEDULE AL", "SCHEDULE LL", "SCHEDULE DP", "SCHEDULE SUL"};
        
                
        
        public static Vector<String> getStatusAttString(){
            Vector sts = new Vector();
            for(int i=0;i< STATUS_ATT.length;i++){
                sts.add(STATUS_ATT[i]);
            }
            return sts;
        }
        
        public static Vector<String> getStatusIndexString(){
            Vector sts = new Vector();
            for(int i=0;i< STATUS_ATT.length;i++){
                sts.add(""+i);
            }
            return sts;
        }
        
        public final static int ANALYZED_NOT_YET =0;
        public final static int ANALYZED_OK =1;        
        public final static int ANALYZED_SUGGEST =2;
        public final static String ANALYZED_STATUS[] = {"NOT YET", "OK", "SYSTEM SUGGEST"};
        
        public final static int NO =0;
        public final static int YES =1; 
        public final static String NOYES[] = {"NO", "YES"};
        
        public static Vector<String> getAnalyzedString(){
            Vector sts = new Vector();
            for(int i=0;i< ANALYZED_STATUS.length;i++){
                sts.add(ANALYZED_STATUS[i]);
            }
            return sts;
        }
        
        public static Vector<String> getAnalyzedIndexString(){
            Vector sts = new Vector();
            for(int i=0;i< ANALYZED_STATUS.length;i++){
                sts.add(""+i);
            }
            return sts;
        }
        
        
	private long employeeId;
	private Date presenceDatetime;
        private Date presenceDatetimeEnd;
	private int status;
        private int analyzed;
        private int transferred;
        //update by satrya 2012-08-19
        private Date scheduleDatetime;
        private int scheduleType;
        private long scheduleLeaveId;
        private long periodId;
        private String empNumb;
        //update by satrya 2013-07-10
        private Date presenceRounded;
        //update by priska 2015-06-08
        private String station;

        private int manualPresence=0;
	public long getEmployeeId(){ 
		return employeeId; 
	} 

	public void setEmployeeId(long employeeId){ 
		this.employeeId = employeeId; 
	} 

	public Date getPresenceDatetime(){ 
		return presenceDatetime; 
	} 

	public void setPresenceDatetime(Date presenceDatetime){ 
		this.presenceDatetime = presenceDatetime; 
	} 

	public int getStatus(){ 
		return status; 
	} 

	public void setStatus(int status){ 
		this.status = status; 
	} 

	public int getAnalyzed(){ 
		return analyzed; 
	} 

	public void setAnalyzed(int analyzed){ 
		this.analyzed = analyzed; 
	} 
        
	public int getTransferred(){ 
		return transferred; 
	} 

	public void setTransferred(int transferred){ 
		this.transferred = transferred; 
	}         

    //update by satrya 2012-08-08
    /**
     * @return the scheduleDatetime
     */
    public Date getScheduleDatetime() {
        return scheduleDatetime;
}

    /**
     * @param scheduleDatetime the scheduleDatetime to set
     */
    public void setScheduleDatetime(Date scheduleDatetime) {
        this.scheduleDatetime = scheduleDatetime;
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
     * @return the scheduleLeaveId
     */
    public long getScheduleLeaveId() {
        return scheduleLeaveId;
    }

    /**
     * @param scheduleLeaveId the scheduleLeaveId to set
     */
    public void setScheduleLeaveId(long scheduleLeaveId) {
        this.scheduleLeaveId = scheduleLeaveId;
    }

    /**
     * @return the periodId
     */
    public long getPeriodId() {
        return periodId;
    }

    /**
     * @param periodId the periodId to set
     */
    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }

    /**
     * @return the empNumb
     */
    public String getEmpNumb() {
        return empNumb;
}

    /**
     * @param empNumb the empNumb to set
     */
    public void setEmpNumb(String empNumb) {
        this.empNumb = empNumb;
    }

    /**
     * @return the presenceRounded
     */
    public Date getPresenceRounded() {
        return presenceRounded;
    }

    /**
     * @param presenceRounded the presenceRounded to set
     */
    public void setPresenceRounded(Date presenceRounded) {
        //merubah jika presencenya masih di bwh 10 menit
      int maxMinute =10;
       if(presenceRounded!=null && maxMinute>=presenceRounded.getMinutes()){
           this.presenceRounded = presenceRounded;
           this.presenceRounded.setMinutes(0);
       }else{
            this.presenceRounded = presenceRounded;
       }
        
    }

    /**
     * @return the presenceDatetimeEnd
     */
    public Date getPresenceDatetimeEnd() {
        return presenceDatetimeEnd;
}

    /**
     * @param presenceDatetimeEnd the presenceDatetimeEnd to set
     */
    public void setPresenceDatetimeEnd(Date presenceDatetimeEnd) {
        this.presenceDatetimeEnd = presenceDatetimeEnd;
    }

    /**
     * @return the station
     */
    public String getStation() {
        return station;
    }

    /**
     * @param station the station to set
     */
    public void setStation(String station) {
        this.station = station;
    }

    /**
     * @return the manualPresence
     */
    public int getManualPresence() {
        return manualPresence;
    }

    /**
     * @param manualPresence the manualPresence to set
     */
    public void setManualPresence(int manualPresence) {
        this.manualPresence = manualPresence;
    }
}
