/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

/**
 *
 * @author Hendra Putu
 */
import com.dimata.qdep.entity.Entity;

public class SalaryChangeEntity {

private String payrollNumber = "";
private String fullName = "";
private String departmentOld = "";
private String sectionOld = "";
private String positionOld = "";
private String levelOld = "";
private String departmentNew = "";
private String sectionNew = "";
private String positionNew = "";
private String levelNew = "";
private double basicOld = 0;
private double meritOld = 0;
private double gradeOld = 0;
private double totalOld = 0;
private double basicNew = 0;
private double meritNew = 0;
private double gradeNew = 0;
private double totalNew = 0;

public String getPayrollNumber(){
return payrollNumber;
}

public void setPayrollNumber(String payrollNumber){
this.payrollNumber = payrollNumber;
}

public String getFullName(){
return fullName;
}

public void setFullName(String fullName){
this.fullName = fullName;
}

public String getDepartmentOld(){
return departmentOld;
}

public void setDepartmentOld(String departmentOld){
this.departmentOld = departmentOld;
}

public String getSectionOld(){
return sectionOld;
}

public void setSectionOld(String sectionOld){
this.sectionOld = sectionOld;
}

public String getPositionOld(){
return positionOld;
}

public void setPositionOld(String positionOld){
this.positionOld = positionOld;
}

public String getLevelOld(){
return levelOld;
}

public void setLevelOld(String levelOld){
this.levelOld = levelOld;
}

public String getDepartmentNew(){
return departmentNew;
}

public void setDepartmentNew(String departmentNew){
this.departmentNew = departmentNew;
}

public String getSectionNew(){
return sectionNew;
}

public void setSectionNew(String sectionNew){
this.sectionNew = sectionNew;
}

public String getPositionNew(){
return positionNew;
}

public void setPositionNew(String positionNew){
this.positionNew = positionNew;
}

public String getLevelNew(){
return levelNew;
}

public void setLevelNew(String levelNew){
this.levelNew = levelNew;
}

public double getBasicOld(){
return basicOld;
}

public void setBasicOld(double basicOld){
this.basicOld = basicOld;
}

public double getMeritOld(){
return meritOld;
}

public void setMeritOld(double meritOld){
this.meritOld = meritOld;
}

public double getGradeOld(){
return gradeOld;
}

public void setGradeOld(double gradeOld){
this.gradeOld = gradeOld;
}

public double getTotalOld(){
return totalOld;
}

public void setTotalOld(double totalOld){
this.totalOld = totalOld;
}

public double getBasicNew(){
return basicNew;
}

public void setBasicNew(double basicNew){
this.basicNew = basicNew;
}

public double getMeritNew(){
return meritNew;
}

public void setMeritNew(double meritNew){
this.meritNew = meritNew;
}

public double getGradeNew(){
return gradeNew;
}

public void setGradeNew(double gradeNew){
this.gradeNew = gradeNew;
}

public double getTotalNew(){
return totalNew;
}

public void setTotalNew(double totalNew){
this.totalNew = totalNew;
}

}
