/*
 * LeaveApplicationReport.java
 *
 * Created on January 12, 2005, 12:07 PM
 */

package com.dimata.harisma.report.leave;

import java.util.Vector;

/**
 *
 * @author  gedhy
 */
public class LeaveApplicationReport {
    
    /** Holds value of property dateOfApplication. */
    private String dateOfApplication = "";
    
    /** Holds value of property applicatName. */
    private String applicatName = "";
    
    /** Holds value of property applicantPayroll. */
    private String applicantPayroll = "";
    
    /** Holds value of property applicantDepartment. */
    private String applicantDepartment = "";
    
    /** Holds value of property applicantPosition. */
    private String applicantPosition = "";
    
    /** Holds value of property leaveReason. */
    private String leaveReason = "";
    
    /** Holds value of property depHeadApproval. */
    private String depHeadApproval = "";
    
    /** Holds value of property hrManApproval. */
    private String hrManApproval = "";
    
    /** Holds value of property dateRequestApproval. */
    private String dateRequestApproval;
    
    /** Holds value of property dateDeptHeadApproval. */
    private String dateDeptHeadApproval;
    
    /** Holds value of property dateHrManApproval. */
    private String dateHrManApproval;
    
    /** Holds value of property alEntitle. */
    private String alEntitle;
    
    /** Holds value of property alHasBeenTaken. */
    private String alHasBeenTaken;
    
    /** Holds value of property alSubTotal. */
    private String alSubTotal;
    
    /** Holds value of property alToBeTaken. */
    private String alToBeTaken;
    
    /** Holds value of property alBalance. */
    private String alBalance;
    
    /** Holds value of property llEntitle. */
    private String llEntitle;
    
    /** Holds value of property llHasBeenTaken. */
    private String llHasBeenTaken;
    
    /** Holds value of property llSubTotal. */
    private String llSubTotal;
    
    /** Holds value of property llToBeTaken. */
    private String llToBeTaken;
    
    /** Holds value of property llBalance. */
    private String llBalance;
    
    /** Holds value of property listOfDetail. */
    private Vector listOfDetail;
    
    /** Holds value of property vListOfDetailView. */
    private Vector vListOfDetailView;
    
    /** Creates a new instance of LeaveApplicationReport */
    public LeaveApplicationReport() {
    }
    
    /** Getter for property dateOfApplication.
     * @return Value of property dateOfApplication.
     *
     */
    public String getDateOfApplication() {
        return this.dateOfApplication;
    }
    
    /** Setter for property dateOfApplication.
     * @param dateOfApplication New value of property dateOfApplication.
     *
     */
    public void setDateOfApplication(String dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
    }
    
    /** Getter for property applicatName.
     * @return Value of property applicatName.
     *
     */
    public String getApplicatName() {
        return this.applicatName;
    }
    
    /** Setter for property applicatName.
     * @param applicatName New value of property applicatName.
     *
     */
    public void setApplicatName(String applicatName) {
        this.applicatName = applicatName;
    }
    
    /** Getter for property applicantPayroll.
     * @return Value of property applicantPayroll.
     *
     */
    public String getApplicantPayroll() {
        return this.applicantPayroll;
    }
    
    /** Setter for property applicantPayroll.
     * @param applicantPayroll New value of property applicantPayroll.
     *
     */
    public void setApplicantPayroll(String applicantPayroll) {
        this.applicantPayroll = applicantPayroll;
    }
    
    /** Getter for property applicantDepartment.
     * @return Value of property applicantDepartment.
     *
     */
    public String getApplicantDepartment() {
        return this.applicantDepartment;
    }
    
    /** Setter for property applicantDepartment.
     * @param applicantDepartment New value of property applicantDepartment.
     *
     */
    public void setApplicantDepartment(String applicantDepartment) {
        this.applicantDepartment = applicantDepartment;
    }
    
    /** Getter for property applicationPosition.
     * @return Value of property applicationPosition.
     *
     */
    public String getApplicantPosition() {
        return this.applicantPosition;
    }
    
    /** Setter for property applicationPosition.
     * @param applicationPosition New value of property applicationPosition.
     *
     */
    public void setApplicantPosition(String applicantPosition) {
        this.applicantPosition = applicantPosition;
    }
    
    /** Getter for property leaveReason.
     * @return Value of property leaveReason.
     *
     */
    public String getLeaveReason() {
        return this.leaveReason;
    }
    
    /** Setter for property leaveReason.
     * @param leaveReason New value of property leaveReason.
     *
     */
    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }
    
    /** Getter for property depHeadApproval.
     * @return Value of property depHeadApproval.
     *
     */
    public String getDepHeadApproval() {
        return this.depHeadApproval;
    }
    
    /** Setter for property depHeadApproval.
     * @param depHeadApproval New value of property depHeadApproval.
     *
     */
    public void setDepHeadApproval(String depHeadApproval) {
        this.depHeadApproval = depHeadApproval;
    }
    
    /** Getter for property hrManApproval.
     * @return Value of property hrManApproval.
     *
     */
    public String getHrManApproval() {
        return this.hrManApproval;
    }
    
    /** Setter for property hrManApproval.
     * @param hrManApproval New value of property hrManApproval.
     *
     */
    public void setHrManApproval(String hrManApproval) {
        this.hrManApproval = hrManApproval;
    }
    
    /** Getter for property dateRequestApproval.
     * @return Value of property dateRequestApproval.
     *
     */
    public String getDateRequestApproval() {
        return this.dateRequestApproval;
    }
    
    /** Setter for property dateRequestApproval.
     * @param dateRequestApproval New value of property dateRequestApproval.
     *
     */
    public void setDateRequestApproval(String dateRequestApproval) {
        this.dateRequestApproval = dateRequestApproval;
    }
    
    /** Getter for property dateDeptHeadApproval.
     * @return Value of property dateDeptHeadApproval.
     *
     */
    public String getDateDeptHeadApproval() {
        return this.dateDeptHeadApproval;
    }
    
    /** Setter for property dateDeptHeadApproval.
     * @param dateDeptHeadApproval New value of property dateDeptHeadApproval.
     *
     */
    public void setDateDeptHeadApproval(String dateDeptHeadApproval) {
        this.dateDeptHeadApproval = dateDeptHeadApproval;
    }
    
    /** Getter for property dateHrManApproval.
     * @return Value of property dateHrManApproval.
     *
     */
    public String getDateHrManApproval() {
        return this.dateHrManApproval;
    }
    
    /** Setter for property dateHrManApproval.
     * @param dateHrManApproval New value of property dateHrManApproval.
     *
     */
    public void setDateHrManApproval(String dateHrManApproval) {
        this.dateHrManApproval = dateHrManApproval;
    }
    
    /** Getter for property alEntitle.
     * @return Value of property alEntitle.
     *
     */
    public String getAlEntitle() {
        return this.alEntitle;
    }
    
    /** Setter for property alEntitle.
     * @param alEntitle New value of property alEntitle.
     *
     */
    public void setAlEntitle(String alEntitle) {
        this.alEntitle = alEntitle;
    }
    
    /** Getter for property alHasBeenTaken.
     * @return Value of property alHasBeenTaken.
     *
     */
    public String getAlHasBeenTaken() {
        return this.alHasBeenTaken;
    }
    
    /** Setter for property alHasBeenTaken.
     * @param alHasBeenTaken New value of property alHasBeenTaken.
     *
     */
    public void setAlHasBeenTaken(String alHasBeenTaken) {
        this.alHasBeenTaken = alHasBeenTaken;
    }
    
    /** Getter for property alSubTotal.
     * @return Value of property alSubTotal.
     *
     */
    public String getAlSubTotal() {
        return this.alSubTotal;
    }
    
    /** Setter for property alSubTotal.
     * @param alSubTotal New value of property alSubTotal.
     *
     */
    public void setAlSubTotal(String alSubTotal) {
        this.alSubTotal = alSubTotal;
    }
    
    /** Getter for property alToBeTaken.
     * @return Value of property alToBeTaken.
     *
     */
    public String getAlToBeTaken() {
        return this.alToBeTaken;
    }
    
    /** Setter for property alToBeTaken.
     * @param alToBeTaken New value of property alToBeTaken.
     *
     */
    public void setAlToBeTaken(String alToBeTaken) {
        this.alToBeTaken = alToBeTaken;
    }
    
    /** Getter for property alBalance.
     * @return Value of property alBalance.
     *
     */
    public String getAlBalance() {
        return this.alBalance;
    }
    
    /** Setter for property alBalance.
     * @param alBalance New value of property alBalance.
     *
     */
    public void setAlBalance(String alBalance) {
        this.alBalance = alBalance;
    }
    
    /** Getter for property llEntitle.
     * @return Value of property llEntitle.
     *
     */
    public String getLlEntitle() {
        return this.llEntitle;
    }
    
    /** Setter for property llEntitle.
     * @param llEntitle New value of property llEntitle.
     *
     */
    public void setLlEntitle(String llEntitle) {
        this.llEntitle = llEntitle;
    }
    
    /** Getter for property llHasBeenTaken.
     * @return Value of property llHasBeenTaken.
     *
     */
    public String getLlHasBeenTaken() {
        return this.llHasBeenTaken;
    }
    
    /** Setter for property llHasBeenTaken.
     * @param llHasBeenTaken New value of property llHasBeenTaken.
     *
     */
    public void setLlHasBeenTaken(String llHasBeenTaken) {
        this.llHasBeenTaken = llHasBeenTaken;
    }
    
    /** Getter for property llSubTotal.
     * @return Value of property llSubTotal.
     *
     */
    public String getLlSubTotal() {
        return this.llSubTotal;
    }
    
    /** Setter for property llSubTotal.
     * @param llSubTotal New value of property llSubTotal.
     *
     */
    public void setLlSubTotal(String llSubTotal) {
        this.llSubTotal = llSubTotal;
    }
    
    /** Getter for property llToBeTaken.
     * @return Value of property llToBeTaken.
     *
     */
    public String getLlToBeTaken() {
        return this.llToBeTaken;
    }
    
    /** Setter for property llToBeTaken.
     * @param llToBeTaken New value of property llToBeTaken.
     *
     */
    public void setLlToBeTaken(String llToBeTaken) {
        this.llToBeTaken = llToBeTaken;
    }
    
    /** Getter for property llBalance.
     * @return Value of property llBalance.
     *
     */
    public String getLlBalance() {
        return this.llBalance;
    }
    
    /** Setter for property llBalance.
     * @param llBalance New value of property llBalance.
     *
     */
    public void setLlBalance(String llBalance) {
        this.llBalance = llBalance;
    }
    
    /** Getter for property listOfDetail.
     * @return Value of property listOfDetail.
     *
     */
    public Vector getListOfDetail() {
        return this.listOfDetail;
    }
    
    /** Setter for property listOfDetail.
     * @param listOfDetail New value of property listOfDetail.
     *
     */
    public void setListOfDetail(Vector listOfDetail) {
        this.listOfDetail = listOfDetail;
    }
    
    /** Getter for property vListOfDetailView.
     * @return Value of property vListOfDetailView.
     *
     */
    public Vector getVListOfDetailView() {
        return this.vListOfDetailView;
    }
    
    /** Setter for property vListOfDetailView.
     * @param vListOfDetailView New value of property vListOfDetailView.
     *
     */
    public void setVListOfDetailView(Vector vListOfDetailView) {
        this.vListOfDetailView = vListOfDetailView;
    }
    
}
