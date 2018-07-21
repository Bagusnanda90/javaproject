/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

import com.dimata.qdep.entity.*;
/**
 *
 * @author Acer
 */
public class EmpDocumentStatus extends Entity {

private long empDocId = 0;
private long employeeId = 0;
private String status = "";

public long getEmpDocId(){
return empDocId;
}

public void setEmpDocId(long empDocId){
this.empDocId = empDocId;
}

public long getEmployeeId(){
return employeeId;
}

public void setEmployeeId(long employeeId){
this.employeeId = employeeId;
}

public String getStatus(){
return status;
}

public void setStatus(String status){
this.status = status;
}

}