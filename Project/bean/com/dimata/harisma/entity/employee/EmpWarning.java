
package com.dimata.harisma.entity.employee;


// import java
import java.util.Date;

// import qdep
import com.dimata.qdep.entity.*;

/**
 *
 * @author bayu
 */

public class EmpWarning extends Entity {
    
    private long employeeId = 0;
    private String breakFact = "";
    private Date breakDate = new Date();
    private String warnBy = "";
    private Date warnDate = new Date();
    private Date validDate = new Date();
    //private int warnLevel = 0;
    /**
     * Ari_20110909
     * merubah warnLevel menjadi warnLevelId {
     */
    private long warnLevelId = 0;
    private long companyId = 0;
    private long divisionId = 0;
    private long departmentId = 0;
    private long sectionId = 0;
    private long positionId = 0;
    private long levelId = 0;
    private long empCategoryId = 0;
    private int acknowledgeStatus = 0;
    
    
    public EmpWarning() {
    }
    
    
    public long getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getBreakFact() {
        return breakFact;
    }
    
    public void setBreakFact(String breakFact) {
        this.breakFact = breakFact;
    }
    
    public Date getBreakDate() {
        return breakDate;
    }
    
    public void setBreakDate(Date breakDate) {
        this.breakDate = breakDate;
    }
    
    public String getWarningBy() {
        return warnBy;
    }
    
    public void setWarningBy(String warnBy) {
        this.warnBy = warnBy;
    }
    
    public Date getWarningDate() {
        return warnDate;
    }
    
    public void setWarningDate(Date warnDate) {
        this.warnDate = warnDate;
    }
    
    public Date getValidityDate() {
        return validDate;
    }
    
    public void setValidityDate(Date validDate) {
        this.validDate = validDate;
    }

  
    public long getWarnLevelId() {
        return warnLevelId;
    }

  
    public void setWarnLevelId(long warnLevelId) {
        this.warnLevelId = warnLevelId;
    }
    
    public long getCompanyId(){
        return companyId;
    }
    
    public void setCompanyId(long companyId){
        this.companyId = companyId;
    }
    
    public long getDivisionId(){
        return divisionId;
    }
    
    public void setDivisionId(long divisionId){
        this.divisionId = divisionId;
    }
    
    public long getDepartmentId(){
        return departmentId;
    }
    
    public void setDepartmentId(long departmentId){
        this.departmentId = departmentId;
    }
    
    public long getSectionId(){
        return sectionId;
    }
    
    public void setSectionId(long sectionId){
        this.sectionId = sectionId;
    }
    
    public long getPositionId(){
        return positionId;
    }
    
    public void setPositionId(long positionId){
        this.positionId = positionId;
    }
    
    public long getLevelId(){
        return levelId;
    }
    
    public void setLevelId(long levelId){
        this.levelId = levelId;
    }
    
    public long getEmpCategoryId(){
        return empCategoryId;
    }
    
    public void setEmpCategoryId(long empCategoryId){
        this.empCategoryId = empCategoryId;
    }
   /* public int getWarnLevel() {
        return warnLevel;
    }

    public void setWarnLevel(int warnLevel) {
        this.warnLevel = warnLevel;
    }*/
    /* } */

    /**
     * @return the acknowledgeStatus
     */
    public int getAcknowledgeStatus() {
        return acknowledgeStatus;
    }

    /**
     * @param acknowledgeStatus the acknowledgeStatus to set
     */
    public void setAcknowledgeStatus(int acknowledgeStatus) {
        this.acknowledgeStatus = acknowledgeStatus;
    }
}
