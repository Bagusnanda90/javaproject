/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.util.net;

import com.dimata.qdep.entity.Entity;
import java.util.Date;
import java.util.Vector;
import javax.activation.DataSource;

/**
 *
 * @author ktanjana
 */
public class MailProcess extends Entity  implements Runnable {

    public final static int STATUS_IDEL = 0;
    public final static int STATUS_PROCESS = 1;
    public final static int STATUS_DONE_OK = 2;
    public final static int STATUS_DONE_ERROR = 3;
    public final static int[] valStatus={STATUS_IDEL,STATUS_PROCESS, STATUS_DONE_OK,STATUS_DONE_ERROR  };
    public final static String[] strStatus={"IDEL","PROCESS", "DONE_OK","DONE_ERROR"};
    
    private String processMessage = "";
    private int status = STATUS_IDEL;
    private Date sendDate = null; 
    private Vector<String> recTo = new Vector();
    private Vector<String> recCC = new Vector();
    private Vector<String> recBCC = new Vector();
    private String subject = "";
    private String txtMessage = "";
    private String from = "";
    private String host = "";
    private int port = 25;
    private String username = "";
    private String password = "";
    private boolean SSL = false;
    private String attacment = "";
    //update by satrya 2013-11-14
    private boolean configEmailWithImage = false;
    private Vector<DataSource> vAttachmentData = new Vector();
    private Vector<String> vAttachmentDataName = new Vector();

    public MailProcess(Vector<String> recTo, Vector<String> recCC,
            Vector<String> recBCC, String subject, String txtMessage, String from,
            String host, int port, String username, String password, boolean SSL, String attacment, boolean configViewEmail) {
        this.setOID((new Date()).getTime());
        this.recTo = recTo;
        this.recCC = recCC;
        this.recBCC = recBCC;
        this.subject = subject;
        this.txtMessage = txtMessage;
        this.from = from;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.SSL = SSL;
        //update by satrya 2013-11-04
        this.attacment = attacment;
        //update by satrya 2013-11-14
        this.configEmailWithImage = configViewEmail;
    }
    
    public MailProcess(Vector<String> recTo, Vector<String> recCC,
            Vector<String> recBCC, String subject, String txtMessage, String from,
            String host, int port, String username, String password, boolean SSL, Vector<DataSource> attacment, Vector<String> attachmentName, boolean configViewEmail) {
        this.recTo = recTo;
        this.recCC = recCC;
        this.recBCC = recBCC;
        this.subject = subject;
        this.txtMessage = txtMessage;
        this.from = from;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.SSL = SSL;
        //update by satrya 2013-11-04
        this.vAttachmentData = attacment; this.vAttachmentDataName = attachmentName;
        //update by satrya 2013-11-14
        this.configEmailWithImage = configViewEmail;
    }

    public synchronized void run() {
       
        this.status = STATUS_PROCESS;
        this.setProcessMessage(this.getProcessMessage() + "SEND EMAIL PROCESS");
        try {
            MailSender mailer = new MailSender(); setSendDate(new Date());
            if(vAttachmentData!=null && vAttachmentData.size()>0){
                mailer.postMail(this.recTo, this.recCC,
                    this.recBCC, this.subject, this.getTxtMessage(), this.from,
                    this.host, this.port, this.username, this.password, this.SSL, this.vAttachmentData, this.vAttachmentDataName, this.configEmailWithImage);
                            
            }else{
                mailer.postMail(this.recTo, this.recCC,
                    this.recBCC, this.subject, this.getTxtMessage(), this.from,
                    this.host, this.port, this.username, this.password, this.SSL, this.vAttachmentData, this.vAttachmentDataName, this.configEmailWithImage);//this.attacment, this.configEmailWithImage);
            }
            
            this.status = STATUS_DONE_OK;
            this.processMessage =this.processMessage +  " | SENT " ; 
        } catch (Exception e) {
            this.status = STATUS_DONE_ERROR;
            this.processMessage =this.processMessage +  " | " + e.toString();
        }
    }

    /**
     * @return the recTo
     */
    public Vector<String> getRecTo() {
        return recTo;
    }
    
    public String getStrRecTo() {
        if(recTo!=null && recTo.size()>0 ){
            String toStr="";
            for(int idx=0;idx < recTo.size(); idx++){
                toStr = toStr + recTo.get(idx) + ( idx <(recTo.size()-1) ? "; ":"" );
            }
            return toStr;
        }
        return "";
    }

    /**
     * @param recTo the recTo to set
     */
    public void setRecTo(Vector<String> recTo) {
        this.recTo = recTo;
    }

    /**
     * @return the recCC
     */
    public Vector<String> getRecCC() {
        return recCC;
    }
 
    public String getStrRecCC() {
        if(recCC!=null && recCC.size()>0 ){
            String ccStr="";
            for(int idx=0;idx < recCC.size(); idx++){
                ccStr = ccStr + recCC.get(idx) + ( idx <(recCC.size()-1) ? "; ":"" );
            }
            return ccStr;
        }
        return "";
    }

    
    /**
     * @param recCC the recCC to set
     */
    public void setRecCC(Vector<String> recCC) {
        this.recCC = recCC;
    }

    /**
     * @return the recBCC
     */
    public Vector<String> getRecBCC() {
        return recBCC;
    }
    
    public String getStrRecBCC() {
        if(recBCC!=null && recBCC.size()>0 ){
            String bccStr="";
            for(int idx=0;idx < recBCC.size(); idx++){
                bccStr = bccStr + recBCC.get(idx) + ( idx <(recBCC.size()-1) ? "; ":"" );
            }
            return bccStr;
        }
        return "";
    }
    /**
     * @param recBCC the recBCC to set
     */
    public void setRecBCC(Vector<String> recBCC) {
        this.recBCC = recBCC;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the txtMessage
     */
    public String getTxtMessage() {
        return txtMessage;
    }

    /**
     * @param txtMessage the txtMessage to set
     */
    public void setTxtMessage(String txtMessage) {
        this.txtMessage = txtMessage;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the SSL
     */
    public boolean isSSL() {
        return SSL;
    }

    /**
     * @param SSL the SSL to set
     */
    public void setSSL(boolean SSL) {
        this.SSL = SSL;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return the sendDate
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * @param sendDate the sendDate to set
     */
    private void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * @return the vAttachmentData
     */
    public Vector<DataSource> getvAttachmentData() {
        return vAttachmentData;
    }

    /**
     * @return the processMessage
     */
    public String getProcessMessage() {
        return processMessage;
    }

    /**
     * @param processMessage the processMessage to set
     */
    private void setProcessMessage(String processMessage) {
        this.processMessage = processMessage;
    }
    
    public static Vector<String> getValStatus(){
        Vector<String> vals = new Vector();
        for(int i=0;i < valStatus.length;i++ ){
            vals.add(""+valStatus[i]);
        }
        return vals;
    }
    
    public static Vector<String> getStringStatus(){
        Vector<String> vals = new Vector();
        for(int i=0;i < strStatus.length;i++ ){
            vals.add(""+strStatus[i]);
        }
        return vals;
    }

    /**
     * @return the vAttachmentDataName
     */
    public Vector<String> getvAttachmentDataName() {
        return vAttachmentDataName;
    }
}
