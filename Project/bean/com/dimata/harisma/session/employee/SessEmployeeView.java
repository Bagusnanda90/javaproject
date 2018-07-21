/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.employee;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Company;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.Education;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.FamRelation;
import com.dimata.harisma.entity.masterdata.GradeLevel;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.Marital;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstCompany;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstEducation;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstFamRelation;
import com.dimata.harisma.entity.masterdata.PstGradeLevel;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstMarital;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstRace;
import com.dimata.harisma.entity.masterdata.PstReligion;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.Race;
import com.dimata.harisma.entity.masterdata.Religion;
import com.dimata.harisma.entity.masterdata.Section;

/**
 *
 * @author Dimata 007
 */
public class SessEmployeeView {
    private long employeeId = 0;
    public Employee employee = new Employee();
    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
        if (employeeId != 0){
            try {
                employee = PstEmployee.fetchExc(employeeId);
            } catch (Exception exc) {
                employee = new Employee();
                System.out.println("Exception employee" + exc);
            } 
        }
    }
    
    public String getCompanyName(long companyId){
        String name = "-";
        if (companyId != 0){
            try {
                Company company = PstCompany.fetchExc(companyId);
                name = company.getCompany();
            } catch(Exception e){
                System.out.println("Company Name =>"+e.toString());
            }
        }
        return name;
    }
    
    public String getDivisionName(long divisionId){
        String name = "-";
        if (divisionId != 0) {
            try {
                Division division = PstDivision.fetchExc(divisionId);
                name = division.getDivision();
            } catch (Exception e) {
                System.out.println("Division Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getDepartmentName(long departmentId){
        String name = "-";
        if (departmentId != 0){
            try {
                Department depart = PstDepartment.fetchExc(departmentId);
                name = depart.getDepartment();
            } catch (Exception e) {
                System.out.println("Department Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getSectionName(long sectionId){
        String name = "-";
        if (sectionId != 0){
            try {
                Section section = PstSection.fetchExc(sectionId);
                name = section.getSection();
            } catch (Exception e) {
                System.out.println("Section Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getPositionName(long positionId){
        String name = "-";
        if (positionId != 0){
             try {
                Position position = PstPosition.fetchExc(positionId);
                name = position.getPosition();
            } catch (Exception e) {
                System.out.println("Position Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getLevelName(long levelId){
        String name = "-";
        if (levelId != 0){
            try {
                Level level = PstLevel.fetchExc(levelId);
                name = level.getLevel();
            } catch (Exception e) {
                System.out.println("Level Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getGradeLevel(long gradeLevelId){
        String name = "-";
        if (gradeLevelId != 0){
            try {
                GradeLevel gradeLevel = PstGradeLevel.fetchExc(gradeLevelId);
                name = gradeLevel.getCodeLevel();
            } catch (Exception e) {
                System.out.println("Grade Level Name =>" + e.toString());
            }
        }
        return name;
    }

    public String getEmpCategory(long empCategoryId){
        String name = "-";
        if (empCategoryId != 0){
            try {
                EmpCategory empCategory = PstEmpCategory.fetchExc(empCategoryId);
                name = empCategory.getEmpCategory();
            } catch (Exception e){
                System.out.println("Category Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getGender(int genderId){
        String name = "-";
        if (genderId == PstEmployee.sexValue[0]){
            name = "Laki-Laki";
        } else {
            name = "Perempuan";
        }
        return name;
    }
    
    public String getReligion(long religionId){
        String name = "-";
        if (religionId != 0){
            try {
                Religion religion = PstReligion.fetchExc(religionId);
                name = religion.getReligion();
            } catch(Exception e){
                System.out.println("Religion Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getMaritalName(long maritalId){
        String name = "-";
        if (maritalId != 0){
            try {
                Marital marital = PstMarital.fetchExc(maritalId);
                name = marital.getMaritalStatus();
            } catch(Exception e){
                System.out.println("Marital Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getRaceName(long raceId){
        String name = "-";
        if (raceId != 0){
            try {
                Race race = PstRace.fetchExc(raceId);
                name = race.getRaceName();
            } catch(Exception e){
                System.out.println("Race Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getRelationship(long relationId){
        String name = "-";
        if (relationId != 0){
            try {
                FamRelation famRelation = PstFamRelation.fetchExc(relationId);
                name = famRelation.getFamRelation();
            } catch(Exception e){
                System.out.println("RelationShip Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getEducationName(long educationId){
        String name = "-";
        if (educationId != 0){
            try {
                Education edu = PstEducation.fetchExc(educationId);
                name = edu.getEducation();
            } catch(Exception e){
                System.out.println("Education Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getFullNameAndPayroll(long empId){
        String str = "-";
        if (empId != 0){
            try {
                Employee emp = PstEmployee.fetchExc(empId);
                str = emp.getFullName()+" ["+emp.getEmployeeNum()+"]";
            } catch(Exception e){
                System.out.println("Full Name and Payroll=>" + e.toString());
            }
        }
        return str;
    }
    
}
