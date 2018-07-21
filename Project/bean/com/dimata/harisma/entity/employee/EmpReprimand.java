
package com.dimata.harisma.entity.employee;


// import java
import java.util.Date;

// import qdep
import com.dimata.qdep.entity.*;

/**
 *
 * @author bayu
 */

public class EmpReprimand extends Entity {
    
    private long employeeId = 0;
    private int number = 0;
    private String chapter = "";
    private String article = "";
    private String verse = "";
    private String page = "";
    private String description = "";
    private Date reprimandDate = new Date();
    private Date validDate = new Date();
    private String document = "";
    private int acknowledgeStatus = 0;
    /**
     * Ari_20110909
     * merubah reprimandLevel menjadi reprimandLevelId {
     */
    private long reprimandLevelId = 0;
    
    private long companyId = 0;
    private long divisionId = 0;
    private long departmentId = 0;
    private long sectionId = 0;
    private long positionId = 0;
    private long levelId = 0;
    private long empCategoryId = 0;
    private long empDocId = 0;

    
    
    public EmpReprimand() {
    }
    
    
    public long getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
    public int getReprimandNumber() {
        return number;
    }
    
    public void setReprimandNumber(int number) {
        this.number = number;
    }
    
    public String getChapter() {
        return chapter;
    }
    
    public void setChapter(String chapter) {
        this.chapter = chapter;
    }
    
    public String getArticle() {
        return article;
    }
    
    public void setArticle(String article) {
        this.article = article;
    }
    
    public String getPage() {
        return page;
    }
    
    public void setPage(String page) {
        this.page = page;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getReprimandDate() {
        return reprimandDate;
    }
    
    public void setReprimandDate(Date reprimandDate) {
        this.reprimandDate = reprimandDate;
    }
    
    public Date getValidityDate() {
        return validDate;
    }
    
    public void setValidityDate(Date validDate) {
        this.validDate = validDate;
    }

    /**
     * @return the reprimandLevelId
     */
    public long getReprimandLevelId() {
        return reprimandLevelId;
    }

    /**
     * @param reprimandLevelId the reprimandLevelId to set
     */
    public void setReprimandLevelId(long reprimandLevelId) {
        this.reprimandLevelId = reprimandLevelId;
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
   /* public int getReprimandLevel() {
        return reprimandLevel;
    }

    public void setReprimandLevel(int reprimandLevel) {
        this.reprimandLevel = reprimandLevel;
    }*/
    /*}*/

    /**
     * @return the verse
     */
    public String getVerse() {
        return verse;
}

    /**
     * @param verse the verse to set
     */
    public void setVerse(String verse) {
        this.verse = verse;
    }

    /**
     * @return the document
     */
    public String getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * @return the empDocId
     */
    public long getEmpDocId() {
        return empDocId;
    }

    /**
     * @param empDocId the empDocId to set
     */
    public void setEmpDocId(long empDocId) {
        this.empDocId = empDocId;
    }

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
