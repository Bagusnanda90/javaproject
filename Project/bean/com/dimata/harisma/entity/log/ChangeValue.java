/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.log;

import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Company;
import com.dimata.harisma.entity.masterdata.Competency;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.Education;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.EmployeeCompetency;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstCompany;
import com.dimata.harisma.entity.masterdata.PstCompetency;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstEducation;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstEmployeeCompetency;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.Section;

/**
 *
 * @author Dimata 007
 */
public class ChangeValue {
    
    public String getStringValue(String field, String value){
        String str = value;
        /* Employee */
        if (field.equals("EMPLOYEE_ID")){
            str = getEmployeeName(Long.valueOf(value));
        }
        /* Education */
        if (field.equals("EDUCATION_ID")){
            str = getEducation(Long.valueOf(value));
        }
        /* ContactList */
        if (field.equals("INSTITUTION_ID")){
            str = getContactList(Long.valueOf(value));
        }
        /* Competency  */
        if (field.equals("COMPETENCY_ID")){
            str = getCompetency(Long.valueOf(value));
        }
        /* Company */
        if (field.equals("COMPANY_ID")){
            str = getCompanyName(Long.valueOf(value));
        }
        /* Division */
        if (field.equals("DIVISION_ID")){
            str = getDivisionName(Long.valueOf(value));
        }
        /* Department */
        if (field.equals("DEPARTMENT_ID")){
            str = getDepartmentName(Long.valueOf(value));
        }
        /* Section */
        if (field.equals("SECTION_ID")){
            str = getSectionName(Long.valueOf(value));
        }
        /* Level */
        if (field.equals("LEVEL_ID")){
            str = getLevelName(Long.valueOf(value));
        }
        /* Emp category */
        if (field.equals("EMP_CATEGORY_ID")){
            str = getEmpCategory(Long.valueOf(value));
        }
        /* Position */
        if (field.equals("POSITION_ID")){
            str = getPositionName(Long.valueOf(value));
        }
        /* Warning */
        /* Reprimand */
        /* Award */
        /* Relevand Doc */
        /* Experience*/
        return str;
    }
    
    public String getEmployeeName(long oid) {
        String str = "-";
        try {
            Employee emp = PstEmployee.fetchExc(oid);
            str = emp.getFullName();
        } catch (Exception ex) {
            System.out.println("Employee ==> " + ex.toString());
        }
        return str;
    }
    
    public String getEducation(long oid) {
        String str = "-";
        try {
            Education education = PstEducation.fetchExc(oid);
            str = education.getEducation();
        } catch (Exception ex) {
            System.out.println("Education ==> " + ex.toString());
        }
        return str;
    }
    
    public String getContactList(long oid){
        String str = "-";
        try {
            ContactList contactList = PstContactList.fetchExc(oid);
            str = contactList.getCompName();
        } catch (Exception ex) {
            System.out.println("Contact List ==> " + ex.toString());
        }
        return str;
    }
    
    public String getCompetency(long oid){
        String str = "-";
        try {
            Competency competency = PstCompetency.fetchExc(oid);
            str = competency.getCompetencyName();
        } catch (Exception ex) {
            System.out.println("Competency Name ==> " + ex.toString());
        }
        return str;
    }
    
    public String getCompanyName(long companyId) {
        String str = "-";
        try {
            Company company = PstCompany.fetchExc(companyId);
            str = company.getCompany();
        } catch(Exception e){
            str = "-";
            System.out.println("getCompanyName()=>"+e.toString());
        }
        return str;
    }
    
    public String getDivisionName(long divisionId) {
        String str = "-";
        try {
            Division division = PstDivision.fetchExc(divisionId);
            str = division.getDivision();
        } catch(Exception e){
            str = "-";
            System.out.println("getDivisionName()=>"+e.toString());
        }
        return str;
    }
    
    public String getDepartmentName(long departmentId) {
        String str = "-";
        try {
            Department department = PstDepartment.fetchExc(departmentId);
            str = department.getDepartment();
        } catch(Exception e){
            str = "-";
            System.out.println("getDepartment()=>"+e.toString());
        }
        return str;
    }
    
    public String getSectionName(long sectionId) {
        String str = "-";
        try {
            Section section = PstSection.fetchExc(sectionId);
            str = section.getSection();
        } catch(Exception e){
            str = "-";
            System.out.println("getSection()=>"+e.toString());
        }
        return str;
    }
    
    public String getLevelName(long oid) {
        String str = "-";
        try {
            Level level = PstLevel.fetchExc(oid);
            str = level.getLevel();
        } catch(Exception e){
            str = "-";
            System.out.println("getLevel()=>"+e.toString());
        }
        return str;
    }
    
    public String getPositionName(long posId) {
        String position = "-";
        Position pos = new Position();
        try {
            pos = PstPosition.fetchExc(posId);
            position = pos.getPosition();
        } catch (Exception ex) {
            System.out.println("getPositionName ==> " + ex.toString());
        }
        return position;
    }
    
    public String getEmpCategory(long oid) {
        String str = "-";
        try {
            EmpCategory empCat = PstEmpCategory.fetchExc(oid);
            str = empCat.getEmpCategory();
        } catch (Exception ex) {
            System.out.println("getEmpCategory ==> " + ex.toString());
        }
        return str;
    }
}
