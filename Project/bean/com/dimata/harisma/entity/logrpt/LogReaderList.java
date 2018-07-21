
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
import java.util.Date;
/* package qdep */
import com.dimata.qdep.entity.*;

public class LogReaderList extends Entity {

    private long logReportId;
    private long userId;
    private String userName="";
    private Date readDateTime = new Date();
    private String comment = "";

    public long getLogReportId() {
        return logReportId;
    }

    public void setLogReportId(long logReportId) {
        this.logReportId = logReportId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getReadDateTime() {
        return readDateTime;
    }

    public void setReadDateTime(Date readDateTime) {
        this.readDateTime = readDateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
