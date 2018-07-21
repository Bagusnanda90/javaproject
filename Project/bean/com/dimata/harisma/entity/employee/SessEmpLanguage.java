/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

/**
 *
 * @author Dimata 007
 */
public class SessEmpLanguage {
    private long employee_id;
    private String language;
    private String oral;
    private String written;
    private String description;

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the oral
     */
    public String getOral() {
        return oral;
    }

    /**
     * @param oral the oral to set
     */
    public void setOral(String oral) {
        this.oral = oral;
    }

    /**
     * @return the written
     */
    public String getWritten() {
        return written;
    }

    /**
     * @param written the written to set
     */
    public void setWritten(String written) {
        this.written = written;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the employee_id
     */
    public long getEmployee_id() {
        return employee_id;
    }

    /**
     * @param employee_id the employee_id to set
     */
    public void setEmployee_id(long employee_id) {
        this.employee_id = employee_id;
    }
}
