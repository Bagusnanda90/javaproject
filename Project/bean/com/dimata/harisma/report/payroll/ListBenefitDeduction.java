/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.payroll;

import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.util.LogicParser;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class ListBenefitDeduction {
    private String payrollNumber;
    private String fullName;
    private long companyId;
    private long divisionId;
    private long deptId;
    private long sectionId;
    private long periodeId;
    private int resignSts;
    private String empCategory;
    private String whereClauseEMployee = "(1=1)";
    
    private int sourceTYpe;
private String judul;
private Vector listCompany= new Vector();

  public String getPayrollNumber() {
        if (payrollNumber == null) {
            return "";
        }
        return payrollNumber;
    }

    /**
     * @param payrollNumber the payrollNumber to set
     */
    public void setPayrollNumber(String payrollNumber) {
        this.payrollNumber = payrollNumber;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        if (fullName == null) {
            return "";
        }
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the companyId
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the divisionId
     */
    public long getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @return the deptId
     */
    public long getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    /**
     * @return the sectionId
     */
    public long getSectionId() {
        return sectionId;
    }

    /**
     * @param sectionId the sectionId to set
     */
    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * @return the empCategory
     */
    public String getEmpCategory() {
        if (empCategory == null) {
            return "";
        }else{
            if(empCategory!=null && empCategory.length()>0){
                String tmp="";
                tmp = empCategory.trim();// substring(0, empCategory.length()-1);
                if(tmp.endsWith(",")){
                  empCategory = tmp.substring(0, tmp.length());    
                }else{
                     empCategory = tmp;
                }
                
            }
        }
        return empCategory;
    }

    /**
     * @param empCategory the empCategory to set
     */
    public void setEmpCategory(String empCategory) {
        this.empCategory = empCategory;
    }

    /**
     * @return the resignSts
     */
    public int getResignSts() {
        return resignSts;
    }

    /**
     * @param resignSts the resignSts to set
     */
    public void setResignSts(int resignSts) {
        this.resignSts = resignSts;
    }

    private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }
        return vector;
    }
    /**
     * @return the whereClauseEMployee
     */
    public String getWhereClauseEMployee() {
        if (this.getDivisionId() != 0) {
            whereClauseEMployee = whereClauseEMployee +" AND "+ PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "=" + this.getDivisionId();
        }
        if(this.getDeptId()!=0){
            whereClauseEMployee = whereClauseEMployee +" AND "+ PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + this.getDeptId();
        }
        if(this.getSectionId()!=0){
            whereClauseEMployee = whereClauseEMployee +" AND "+  PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + this.getSectionId();
        }
        
        if(this.getPayrollNumber()!=null && this.getPayrollNumber().length()>0){
            //whereClauseEMployee = whereClauseEMployee + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + "=" + this.getPayrollNumber();
            Vector vectName = logicParser(payrollNumber);
            whereClauseEMployee = whereClauseEMployee + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whereClauseEMployee = whereClauseEMployee + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClauseEMployee = whereClauseEMployee + " "+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClauseEMployee = whereClauseEMployee + " "+str.trim();
                    }
                }
                whereClauseEMployee = whereClauseEMployee + ")";
            }
        }
        
        if(this.getFullName()!=null && this.getFullName().length()>0){
            //whereClauseEMployee = whereClauseEMployee + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + "=" + this.getFullName();
            Vector vectName = logicParser(fullName);
            whereClauseEMployee = whereClauseEMployee + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whereClauseEMployee = whereClauseEMployee + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClauseEMployee = whereClauseEMployee + " "+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClauseEMployee = whereClauseEMployee + " "+str.trim();
                    }
                }
                whereClauseEMployee = whereClauseEMployee + ")";
            }
        }
        
        if(this.getResignSts()!=2){
            whereClauseEMployee = whereClauseEMployee +" AND "+  PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + this.getResignSts();
        }
        String emPcat=this.getEmpCategory();
        if(emPcat!=null &&emPcat.length()>0){
            whereClauseEMployee = whereClauseEMployee +" AND "+  PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " IN(" + emPcat+")";
        }


        return whereClauseEMployee;
    }
    
    public String whereClauseEmpId(String empId){
        
        if(empId!=null && empId.length()>0){
            return (PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" IN("+empId+")");
        }else{
            return "";
        }
    }

   

    /**
     * @return the listCompany
     */
    public Vector getListCompany() {
        return listCompany;
    }

    /**
     * @param listCompany the listCompany to set
     */
    public void setListCompany(Vector listCompany) {
        this.listCompany = listCompany;
    }

    

    /**
     * @return the judul
     */
    public String getJudul() {
        return judul;
    }

    /**
     * @param judul the judul to set
     */
    public void setJudul(String judul) {
        this.judul = judul;
    }

    /**
     * @return the sourceTYpe
     */
    public int getSourceTYpe() {
        return sourceTYpe;
    }

    /**
     * @param sourceTYpe the sourceTYpe to set
     */
    public void setSourceTYpe(int sourceTYpe) {
        this.sourceTYpe = sourceTYpe;
    }

    /**
     * @return the periodeId
     */
    public long getPeriodeId() {
        return periodeId;
    }

    /**
     * @param periodeId the periodeId to set
     */
    public void setPeriodeId(long periodeId) {
        this.periodeId = periodeId;
    }
}