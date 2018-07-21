/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author GUSWIK
 */
public class ObjectSearchDoc extends Entity{
    private String formname = "";
    private long oidDoc = 0;
    private String ObjectName = "";
    private String empPathId = "";
    private String empId = "";

    /**
     * @return the formname
     */
    public String getFormname() {
        return formname;
    }

    /**
     * @param formname the formname to set
     */
    public void setFormname(String formname) {
        this.formname = formname;
    }

    /**
     * @return the oidDoc
     */
    public long getOidDoc() {
        return oidDoc;
    }

    /**
     * @param oidDoc the oidDoc to set
     */
    public void setOidDoc(long oidDoc) {
        this.oidDoc = oidDoc;
    }

    /**
     * @return the ObjectName
     */
    public String getObjectName() {
        return ObjectName;
    }

    /**
     * @param ObjectName the ObjectName to set
     */
    public void setObjectName(String ObjectName) {
        this.ObjectName = ObjectName;
    }

    /**
     * @return the empPathId
     */
    public String getEmpPathId() {
        return empPathId;
    }

    /**
     * @param empPathId the empPathId to set
     */
    public void setEmpPathId(String empPathId) {
        this.empPathId = empPathId;
    }

    /**
     * @return the empId
     */
    public String getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
