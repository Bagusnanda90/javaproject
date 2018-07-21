/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author artha
 */
public class ImageAssign extends Entity{
    /*
       	IMG_ASSIGN_OID  	bigint(20)  
	EMPLOYEE_OID 	bigint(20)
	PATH 	varchar(100)
     */
    private long employeeOid;
    private String path;

    public long getEmployeeOid() {
        return employeeOid;
    }

    public void setEmployeeOid(long employeeOid) {
        this.employeeOid = employeeOid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
}
