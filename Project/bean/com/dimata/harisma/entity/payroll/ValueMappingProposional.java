/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.harisma.entity.employee.CareerPath;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstCareerPath;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Date : 2016-03-07
 *
 * @author Dimata 007 | Hendra Putu
 */
public class ValueMappingProposional {

    private String fieldKey = "";
    private String valueKey = "";

    public double convertDouble(String val){
        BigDecimal bDecimal = new BigDecimal(Double.valueOf(val));
        bDecimal = bDecimal.setScale(0, RoundingMode.HALF_UP);
        return bDecimal.doubleValue();
    }
    /* Convert int */
    public int convertInteger(int scale, double val){
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(scale, RoundingMode.HALF_UP);
        return bDecimal.intValue();
    }
    /* Decimal Format */
    public double customFormat(String pattern, double value ) {
        double outDouble = 0;
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        outDouble = Double.valueOf(output);
        return outDouble;
   }
    
    public String getDigit(int val) {
        String str = "";
        String nilai = String.valueOf(val);
        if (nilai.length() == 1) {
            str = "0" + nilai;
        } else {
            str = nilai;
        }
        return str;
    }

    /**
     * getRangeOfDate : mencari rentangan tanggal dari start date to end date.
     * misal : start date = 2015-09-09 To 2015-09-13, maka hasilnya::
     * 2015-09-09, 2015-09-10, 2015-09-11, 2015-09-12, 2015-09-13
     */
    public Vector<String> getRangeOfDate(String startDate, String endDate) {
        Vector<String> rangeDate = new Vector<String>();
        String[] arrStart = startDate.split("-");
        String[] arrEnd = endDate.split("-");

        int yearStart = Integer.valueOf(arrStart[0]);
        int monthStart = Integer.valueOf(arrStart[1]);
        int dayStart = Integer.valueOf(arrStart[2]);

        int yearEnd = Integer.valueOf(arrEnd[0]);
        int monthEnd = Integer.valueOf(arrEnd[1]);
        int dayEnd = Integer.valueOf(arrEnd[2]);

        String tanggal = "";
        if (yearStart != yearEnd) {
            for (int y = yearStart; y <= yearEnd; y++) {
                if (y < yearEnd) { // 2014-01-01 AND 2016-01-01 (2014-01-01, 2015-01-01, 2016-05-01)
                    if (monthStart == 1) {
                        for (int m = 1; m <= 12; m++) {
                            if (dayStart == 1) {
                                for (int d = 1; d <= 31; d++) {
                                    tanggal = y + "-" + getDigit(m) + "-" + getDigit(d);
                                    rangeDate.add(tanggal);
                                }
                            } else {
                                for (int d = dayStart; d <= 31; d++) {
                                    tanggal = y + "-" + getDigit(m) + "-" + getDigit(d);
                                    rangeDate.add(tanggal);
                                }
                                dayStart = 1;
                            }
                        }
                    } else {
                        for (int m = monthStart; m <= 12; m++) {
                            if (dayStart == 1) {
                                for (int d = 1; d <= 31; d++) {
                                    tanggal = y + "-" + getDigit(m) + "-" + getDigit(d);
                                    rangeDate.add(tanggal);
                                }
                            } else {
                                for (int d = dayStart; d <= 31; d++) {
                                    tanggal = y + "-" + getDigit(m) + "-" + getDigit(d);
                                    rangeDate.add(tanggal);
                                }
                                dayStart = 1;
                            }
                        }
                        monthStart = 1;
                    }
                } else {
                    if (monthStart == monthEnd) {
                        if (dayStart == dayEnd) {
                            tanggal = y + "-" + getDigit(monthStart) + "-" + getDigit(dayStart);
                            rangeDate.add(tanggal);
                        } else {
                            if (dayStart < dayEnd) {
                                for (int d = dayStart; d <= dayEnd; d++) {
                                    tanggal = y + "-" + getDigit(monthStart) + "-" + getDigit(d);
                                    rangeDate.add(tanggal);
                                }
                            }
                        }
                    } else {
                        if (monthStart < monthEnd) { // 2015-01-01 AND 2015-02-05
                            for (int m = monthStart; m <= monthEnd; m++) {
                                if (m < monthEnd) {
                                    if (dayStart == 1) {
                                        for (int d = 1; d <= 31; d++) {
                                            tanggal = y + "-" + getDigit(m) + "-" + getDigit(d);
                                            rangeDate.add(tanggal);
                                        }
                                    } else {
                                        if (dayStart > 1) {
                                            for (int d = dayStart; d <= 31; d++) {
                                                tanggal = y + "-" + getDigit(m) + "-" + getDigit(d);
                                                rangeDate.add(tanggal);
                                            }
                                        }
                                    }
                                } else {
                                    for (int d = 1; d <= dayEnd; d++) {
                                        tanggal = y + "-" + getDigit(m) + "-" + getDigit(d);
                                        rangeDate.add(tanggal);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        } else {
            if (monthStart == monthEnd) {
                if (dayStart == dayEnd) {
                    tanggal = yearStart + "-" + getDigit(monthStart) + "-" + getDigit(dayStart);
                    rangeDate.add(tanggal);
                } else {
                    if (dayStart < dayEnd) {
                        for (int d = dayStart; d <= dayEnd; d++) {
                            tanggal = yearStart + "-" + getDigit(monthStart) + "-" + getDigit(d);
                            rangeDate.add(tanggal);
                        }
                    }
                }
            } else {
                if (monthStart < monthEnd) { // 2015-01-01 AND 2015-02-05
                    for (int m = monthStart; m <= monthEnd; m++) {
                        if (m < monthEnd) {
                            if (dayStart == 1) {
                                for (int d = 1; d <= 31; d++) {
                                    tanggal = yearStart + "-" + getDigit(m) + "-" + getDigit(d);
                                    rangeDate.add(tanggal);
                                }
                            } else {
                                if (dayStart > 1) {
                                    for (int d = dayStart; d <= 31; d++) {
                                        tanggal = yearStart + "-" + getDigit(m) + "-" + getDigit(d);
                                        rangeDate.add(tanggal);
                                    }
                                }
                            }
                        } else {
                            for (int d = 1; d <= dayEnd; d++) {
                                tanggal = yearStart + "-" + getDigit(m) + "-" + getDigit(d);
                                rangeDate.add(tanggal);
                            }
                        }

                    }
                }
            }
        }

        return rangeDate;
    }

    public double getValuemapping(String fromdate, String todate, String employeeId, String salaryComp) {
        DBResultSet dbrs = null;
        double nilai = 0;
        String test = "";
        Employee employee = new Employee();
        try {
            employee = PstEmployee.fetchExc(Long.valueOf(employeeId));
        } catch (Exception e) {
        }
        try {
            String sql = " SELECT * FROM " + PstValue_Mapping.TBL_VALUE_MAPPING + " WHERE "
                    + PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_COMP_CODE] + " = \"" + salaryComp + "\" "
                    + " AND ((" + PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_END_DATE]
                    + " >= \"" + fromdate + " 00:00:00" + "\") OR (END_DATE = \"0000-00-00  00:00:00\")  OR ( END_DATE IS NULL ) )";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while (rs.next()) {
                // Employee employee = PstEmployee.fetchExc(employeeId);

                long VmCompanyId = rs.getLong(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_COMPANY_ID]);
                long VmDivisionId = rs.getLong(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_DIVISION_ID]);
                long VmDepartmentId = rs.getLong(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_DEPARTMENT_ID]);
                long VmSectionId = rs.getLong(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_SECTION_ID]);
                long VmLevelId = rs.getLong(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_LEVEL_ID]);
                long VmMaritalId = rs.getLong(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_MARITAL_ID]);
                double VmLengthOfService = rs.getDouble(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_LENGTH_OF_SERVICE]);
                long VmEmpCategoryId = rs.getLong(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_EMPLOYEE_CATEGORY]);
                long VmPositionId = rs.getLong(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_POSITION_ID]);
                long VmEmployeeId = rs.getLong(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_EMPLOYEE_ID]);
                double VmValue = rs.getDouble(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_VALUE]);
                long VmGradeId = rs.getLong(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_GRADE]);
                int VmSex = rs.getInt(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_SEX]);

                java.util.Date today = new java.util.Date();
                boolean nilaitf = true;
                /* melakukan perbandingan ke object Employee */
                if ((VmCompanyId != 0) && (VmCompanyId > 0)) {
                    if (VmCompanyId != employee.getCompanyId()) {
                        nilaitf = false;
                    } else {
                        setFieldKey(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_COMPANY_ID]);
                        setValueKey("" + VmCompanyId);
                    }
                }

                if ((VmDivisionId != 0) && (VmDivisionId > 0)) {
                    if (VmDivisionId != employee.getDivisionId()) {
                        nilaitf = false;
                    } else {
                        setFieldKey(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_DIVISION_ID]);
                        setValueKey("" + VmDivisionId);
                    }
                }
                if ((VmDepartmentId != 0) && (VmDepartmentId > 0)) {
                    if (VmDepartmentId != employee.getDepartmentId()) {
                        nilaitf = false;
                    } else {
                        setFieldKey(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_DEPARTMENT_ID]);
                        setValueKey("" + VmDepartmentId);
                    }
                }
                if ((VmSectionId != 0) && (VmSectionId > 0)) {
                    if (VmSectionId != employee.getSectionId()) {
                        nilaitf = false;
                    } else {
                        setFieldKey(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_SECTION_ID]);
                        setValueKey("" + VmSectionId);
                    }
                }
                if ((VmPositionId != 0) && (VmPositionId > 0)) {
                    if (VmPositionId != employee.getPositionId()) {
                        nilaitf = false;
                    } else {
                        setFieldKey(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_POSITION_ID]);
                        setValueKey("" + VmPositionId);
                    }
                }
                if ((VmGradeId != 0) && (VmGradeId > 0)) {
                    if (VmGradeId != employee.getGradeLevelId()) {
                        nilaitf = false;
                    } else {
                        setFieldKey(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_GRADE]);
                        setValueKey("" + VmGradeId);
                    }
                }
                if ((VmEmpCategoryId != 0) && (VmEmpCategoryId > 0)) {
                    if (VmEmpCategoryId != employee.getEmpCategoryId()) {
                        nilaitf = false;
                    } else {
                        setFieldKey(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_EMPLOYEE_CATEGORY]);
                        setValueKey("" + VmEmpCategoryId);
                    }
                }
                if ((VmLevelId != 0) && (VmLevelId > 0)) {
                    if (VmLevelId != employee.getLevelId()) {
                        nilaitf = false;
                    } else {
                        setFieldKey(PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_LEVEL_ID]);
                        setValueKey("" + VmLevelId);
                    }
                }
                if ((VmMaritalId != 0) && (VmMaritalId > 0)) {
                    if (VmMaritalId != employee.getMaritalId()) {
                        nilaitf = false;
                    }
                }
                if ((VmEmployeeId != 0) && (VmEmployeeId > 0)) {
                    if (VmEmployeeId != employee.getOID()) {
                        nilaitf = false;
                    }
                }

                if ((VmSex != -1) && (VmSex > -1)) {
                    if (VmSex != employee.getSex()) {
                        nilaitf = false;
                    }
                }

                if ((VmLengthOfService != 0) && (VmLengthOfService > 0)) {
                    double diff = today.getTime() - employee.getCommencingDate().getTime();
                    double yeardiff = diff / (1000 * 60 * 60 * 24 * 365);
                    if ((yeardiff != VmLengthOfService) || (yeardiff < VmLengthOfService)) {
                        nilaitf = false;
                    }
                }
                /* End melakukan perbandingan ke object Employee */
                if (nilaitf) {
                    nilai = VmValue;
                }
            }
            //rs.close();
            return nilai;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    public double getValueMappingTotal(String periodFrom, String periodTo, String employeeId, String salaryComp) {
        Vector listRangeDate = new Vector();
        Vector listVMCareer = new Vector();
        String whereClause = "";
        int typeOfDecimalFormat = 0;
        CareerPath careerPath = new CareerPath();
        int rangeStart = -1;
        int rangeEnd = -1;
        whereClause = PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"='"+salaryComp+"'";
        Vector listComponent = PstPayComponent.list(0, 1, whereClause, "");
        if (listComponent != null && listComponent.size()>0){
            PayComponent payComp = (PayComponent)listComponent.get(0);
            typeOfDecimalFormat = payComp.getDecimalFormat();
        }
        // dapatkan value mapping current
        double valueCurrent = getValuemapping(periodFrom, periodTo, employeeId, salaryComp);
        double valuePrevious = 0;
        double proporsionalCurr = 0;
        double proporsionalPrev = 0;
        double totalValue = 0;
        int amountPrev = 0;
        int amountCurr = 0;
        long oidCareerPath = 0;

        oidCareerPath = getCareerOid(periodFrom, employeeId);
        if (oidCareerPath != 0) {
            try {
                careerPath = PstCareerPath.fetchExc(oidCareerPath);
            } catch (Exception e) {
                System.out.println("" + e.toString());
            }
            /* Get Range of Date from Career Path */
            if (careerPath != null) {
                listRangeDate = getRangeOfDate("" + careerPath.getWorkFrom(), "" + careerPath.getWorkTo());
                if (listRangeDate != null && listRangeDate.size() > 0) {
                    for (int r = 0; r < listRangeDate.size(); r++) {
                        String rDate = (String) listRangeDate.get(r);
                        if (rDate.equals(periodFrom)) {
                            rangeStart = r;
                        }
                        if (rDate.equals(periodTo)) {
                            rangeEnd = r;
                        }
                    }
                }
                if (rangeStart == -1) {
                    rangeStart = listRangeDate.size();
                }
                if (rangeEnd == -1) {
                    rangeEnd = listRangeDate.size();
                }

                amountPrev = rangeEnd - rangeStart;
                amountCurr = 30 - amountPrev;
            }
        } else {
            amountPrev = 0;
            amountCurr = 30;
        }
        

        if (valueCurrent != 0) {
            proporsionalCurr = (valueCurrent / 30) * amountCurr;
        } else {
            proporsionalCurr = valueCurrent;
        }
        /* Foccuss on valuePrevious */

        if (oidCareerPath != 0) {
            whereClause = PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_COMP_CODE] + "='" + salaryComp + "' ";
            whereClause += " AND " + PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_END_DATE] + " >= '" + periodFrom + "'";
            double nilaiVM = 0;
            listVMCareer = PstValue_Mapping.list(0, 0, whereClause, "");
            if (listVMCareer != null && listVMCareer.size() > 0) {
                for (int i = 0; i < listVMCareer.size(); i++) {
                    Value_Mapping valueMapping = (Value_Mapping) listVMCareer.get(i);
                    boolean perbandingan = true;
                    /* melakukan perbandingan ke object career path */
                    if (valueMapping.getCompany_id() != 0) {
                        if (valueMapping.getCompany_id() != careerPath.getCompanyId()) {
                            perbandingan = false;
                        }
                    }
                    if (valueMapping.getDivision_id() != 0) {
                        if (valueMapping.getDivision_id() != careerPath.getDivisionId()) {
                            perbandingan = false;
                        }
                    }
                    if (valueMapping.getDepartment_id() != 0) {
                        if (valueMapping.getDepartment_id() != careerPath.getDepartmentId()) {
                            perbandingan = false;
                        }
                    }
                    if (valueMapping.getSection_id() != 0) {
                        if (valueMapping.getSection_id() != careerPath.getSectionId()) {
                            perbandingan = false;
                        }
                    }
                    if (valueMapping.getLevel_id() != 0) {
                        if (valueMapping.getLevel_id() != careerPath.getLevelId()) {
                            perbandingan = false;
                        }
                    }
                    if (valueMapping.getEmployee_category() != 0) {
                        if (valueMapping.getEmployee_category() != careerPath.getEmpCategoryId()) {
                            perbandingan = false;
                        }
                    }
                    if (valueMapping.getPosition_id() != 0) {
                        if (valueMapping.getPosition_id() != careerPath.getPositionId()) {
                            perbandingan = false;
                        }
                    }
                    if (valueMapping.getGrade() != 0) {
                        if (valueMapping.getGrade() != careerPath.getGradeLevelId()) {
                            perbandingan = false;
                        }
                    } else {
                        //perbandingan = false; /* ? */
                    }
                    if (valueMapping.getEmployee_id() != 0){
                        if(valueMapping.getEmployee_id() != careerPath.getEmployeeId()) {
                            perbandingan = false;
                        }
                    }
                    if (perbandingan == true) {
                        nilaiVM = valueMapping.getValue();
                    }
                }

                valuePrevious = nilaiVM;
                proporsionalPrev = (valuePrevious / 30) * amountPrev;
            }
        } else {
            proporsionalPrev = valuePrevious;
        }
        /* do decimal format */
        switch(typeOfDecimalFormat){
            case 0:
                break;
            case 1:
                proporsionalPrev = customFormat("###.#", proporsionalPrev);
                proporsionalCurr = customFormat("###.#", proporsionalCurr);
                break;
            case 2:
                proporsionalPrev = customFormat("###.##", proporsionalPrev);
                proporsionalCurr = customFormat("###.##", proporsionalCurr);
                break; 
            case 3:
                proporsionalPrev = customFormat("###.###", proporsionalPrev);
                proporsionalCurr = customFormat("###.###", proporsionalCurr);
                break;
            case 4:
                proporsionalPrev = convertInteger(0, proporsionalPrev);
                proporsionalCurr = convertInteger(0, proporsionalCurr);
                break;
            case 5:
                proporsionalPrev = convertInteger(-1, proporsionalPrev);
                proporsionalCurr = convertInteger(-1, proporsionalCurr);
                break;
            case 6:
                proporsionalPrev = convertInteger(-2, proporsionalPrev);
                proporsionalCurr = convertInteger(-2, proporsionalCurr);
                break; 
            case 7:
                proporsionalPrev = convertInteger(-3, proporsionalPrev);
                proporsionalCurr = convertInteger(-3, proporsionalCurr);
                break;
        }
        totalValue = proporsionalPrev + proporsionalCurr;

        return totalValue;
    }
    
    public long getCareerOid(String periodFrom, String employeeId){
        long oidCareer = 0;
        String whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]+"="+employeeId;
        Vector listCareer = PstCareerPath.list(0, 0, whereClause, "");
        if (listCareer != null && listCareer.size()>0){
            for(int i=0; i<listCareer.size(); i++){
                CareerPath career = (CareerPath)listCareer.get(i);
                Vector listRangeDate = getRangeOfDate("" + career.getWorkFrom(), "" + career.getWorkTo());
                if (listRangeDate != null && listRangeDate.size() > 0) {
                    for (int r = 0; r < listRangeDate.size(); r++) {
                        String rDate = (String) listRangeDate.get(r);
                        if (rDate.equals(periodFrom)) {
                            oidCareer = career.getOID();
                        }
                    }
                }
            }
        }
        return oidCareer;
    }

    /**
     * @return the fieldKey
     */
    public String getFieldKey() {
        return fieldKey;
    }

    /**
     * @param fieldKey the fieldKey to set
     */
    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    /**
     * @return the valueKey
     */
    public String getValueKey() {
        return valueKey;
    }

    /**
     * @param valueKey the valueKey to set
     */
    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }
}
