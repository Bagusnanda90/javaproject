/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.harisma.entity.employee.CareerPath;
import com.dimata.harisma.entity.employee.PstCareerPath;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author Hendra Putu
 */
public class SalaryChangeModel1 {
    
    public static String getPreviousStartDate(String startDate){
        String str = "";
        int year = Integer.valueOf(startDate.substring(0, 4));
        int month = Integer.valueOf(startDate.substring(5, 7));
        String monthStr = "";
        String day = startDate.substring(8, 10);
        if (month - 1 == 0){
            month = 12;
            year = year - 1;
            monthStr = String.valueOf(month);
        } else {
            month = month - 1;
            if (String.valueOf(month).length() == 1){
                monthStr = "0"+month;
            } else {
                monthStr = String.valueOf(month);
            }
        }
        str = year + "-" + monthStr + "-" + day;
        return str;
    }
    
    public static String getPreviousPeriod(long periodId){
        String str = "-";
        PayPeriod period = new PayPeriod();
        try {
            period = PstPayPeriod.fetchExc(periodId);
            str = getPreviousStartDate(String.valueOf(period.getStartDate()));
            String where = PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + "='" + str +"'";
            Vector listPayPeriod = PstPayPeriod.list(0, 0, where, "");
            if (listPayPeriod != null && listPayPeriod.size()>0){
                PayPeriod period1 = (PayPeriod)listPayPeriod.get(0);
                str = String.valueOf(period1.getOID());
            } else {
                str = "0";
            }
        } catch(Exception e){
            System.out.println("PayPeriod=>"+e.toString());
        }
        return str;
    }
    
    public static String[] getPeriodDate(long periodId){
        String[] arrDate = {"0","1"};
        PayPeriod period = new PayPeriod();
        try {
            period = PstPayPeriod.fetchExc(periodId);
            arrDate[0] = ""+period.getStartDate();
            arrDate[1] = ""+period.getEndDate();
        } catch(Exception e){
            System.out.println("PayPeriod=>"+e.toString());
        }
        return arrDate;
    }
    
    public static Vector getSlipComp(long paySlipId){

        Vector list = new Vector();
        String where = PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+"="+paySlipId;
        where += " AND "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+" IN('ALW01','ALW04','ALW05')";
        
        DBResultSet dbrs = null;
        try {
            String sql = "";
            sql += " SELECT * FROM "+PstPaySlipComp.TBL_PAY_SLIP_COMP;
            sql += " WHERE "+where;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                double nilai = rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]);
                list.add(nilai);
            }
            rs.close();
            return list;

        } catch (Exception e) {

            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        
        return list;
    }
    
    public static Vector getOrganizationStructureNew(String periodId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "";
            sql += " SELECT "; 
            sql += PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+", ";
            sql += PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+", ";
            sql += PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+", ";
            sql += PstSection.TBL_HR_SECTION+"."+PstSection.fieldNames[PstSection.FLD_SECTION]+", ";
            sql += PstPosition.TBL_HR_POSITION+"."+PstPosition.fieldNames[PstPosition.FLD_POSITION]+", ";
            sql += PstLevel.TBL_HR_LEVEL+"."+PstLevel.fieldNames[PstLevel.FLD_LEVEL]+", ";
            sql += PstPaySlip.TBL_PAY_SLIP+"."+PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]+" ";
            sql += " FROM "+PstEmployee.TBL_HR_EMPLOYEE;
            sql += " INNER JOIN pay_slip ON hr_employee.EMPLOYEE_ID=pay_slip.EMPLOYEE_ID ";
            sql += " INNER JOIN hr_department ON hr_employee.DEPARTMENT_ID=hr_department.DEPARTMENT_ID ";
            sql += " INNER JOIN hr_section ON hr_employee.SECTION_ID=hr_section.SECTION_ID ";
            sql += " INNER JOIN hr_position ON hr_employee.POSITION_ID=hr_position.POSITION_ID ";
            sql += " INNER JOIN hr_level ON hr_employee.LEVEL_ID=hr_level.LEVEL_ID ";
            sql += " WHERE pay_slip.PERIOD_ID="+periodId;
            sql += " ORDER BY hr_employee.EMPLOYEE_NUM ASC";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                long paySlipId = rs.getLong(PstPaySlip.TBL_PAY_SLIP+"."+PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]);
                Vector listSlipComp = getSlipComp(paySlipId);
                SalaryChangeEntity salaryChange = new SalaryChangeEntity();
                salaryChange.setPayrollNumber(rs.getString(PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                salaryChange.setFullName(rs.getString(PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                salaryChange.setDepartmentNew(rs.getString(PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                salaryChange.setSectionNew(rs.getString(PstSection.TBL_HR_SECTION+"."+PstSection.fieldNames[PstSection.FLD_SECTION]));
                salaryChange.setPositionNew(rs.getString(PstPosition.TBL_HR_POSITION+"."+PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                salaryChange.setLevelNew(rs.getString(PstLevel.TBL_HR_LEVEL+"."+PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                salaryChange.setDepartmentOld("-");
                salaryChange.setSectionOld("-");
                salaryChange.setPositionOld("-");
                salaryChange.setLevelOld("-");
                salaryChange.setBasicOld(1);
                salaryChange.setMeritOld(1);
                salaryChange.setGradeOld(1);
                salaryChange.setTotalOld(1);
                double totalNew = 0;
                if (listSlipComp != null && listSlipComp.size()>0){
                    if (listSlipComp.size() < 3){
                        for(int i=0; i<3; i++){
                            listSlipComp.add(0);
                        }
                    }
                    Double ALW01 = (Double)listSlipComp.get(0);
                    Double ALW04 = (Double)listSlipComp.get(1);
                    Double ALW05 = (Double)listSlipComp.get(2);
                    salaryChange.setBasicNew(ALW01);
                    salaryChange.setMeritNew(ALW04);
                    salaryChange.setGradeNew(ALW05);
                    totalNew = ALW01 + ALW04 + ALW05;
                }
                
                salaryChange.setTotalNew(totalNew);
                lists.add(salaryChange);
            }
            rs.close();
            return lists;

        } catch (Exception e) {

            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    public static Vector getOrganizationStructure(String periodId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "";
            sql += " SELECT "; 
            sql += " hr_employee.EMPLOYEE_ID, ";
            sql += " hr_employee.EMPLOYEE_NUM, ";
            sql += " hr_employee.FULL_NAME, ";
            sql += " pay_slip.DEPARTMENT, ";
            sql += " pay_slip.SECTION, ";
            sql += " pay_slip.POSITION, ";
            sql += " hr_employee.LEVEL_ID, ";
            sql += " pay_slip.PAY_SLIP_ID ";
            sql += " FROM hr_employee ";
            sql += " INNER JOIN pay_slip ON hr_employee.EMPLOYEE_ID=pay_slip.EMPLOYEE_ID ";
            sql += " WHERE pay_slip.PERIOD_ID="+periodId;
            sql += " ORDER BY hr_employee.EMPLOYEE_NUM ASC";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                long paySlipId = rs.getLong(PstPaySlip.TBL_PAY_SLIP+"."+PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]);
                Vector listSlipComp = getSlipComp(paySlipId);
                
                long employeeId = rs.getLong(PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
                Vector careerPath = listCareerPath(employeeId);
                SalaryChangeEntity salaryChange = new SalaryChangeEntity();
                salaryChange.setPayrollNumber(rs.getString(PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                salaryChange.setFullName(rs.getString(PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                salaryChange.setDepartmentNew(rs.getString(PstPaySlip.TBL_PAY_SLIP+"."+PstPaySlip.fieldNames[PstPaySlip.FLD_DEPARTMENT]));
                salaryChange.setSectionNew(rs.getString(PstPaySlip.TBL_PAY_SLIP+"."+PstPaySlip.fieldNames[PstPaySlip.FLD_SECTION]));
                salaryChange.setPositionNew(rs.getString(PstPaySlip.TBL_PAY_SLIP+"."+PstPaySlip.fieldNames[PstPaySlip.FLD_POSITION]));
                salaryChange.setLevelNew("-");

                salaryChange.setDepartmentOld("-");
                salaryChange.setSectionOld("-");
                salaryChange.setPositionOld("-");
                salaryChange.setLevelOld("-");
                
                String[] arrDate = getPeriodDate(Long.valueOf(periodId));
                Vector listPeriod = new Vector();
                if (arrDate.length > 0){
                    listPeriod = getRangeOfDate(arrDate[0], arrDate[1]);
                }
                
                if (careerPath != null && careerPath.size()>0){
                    for(int c=0; c<careerPath.size(); c++){
                        CareerPath path = (CareerPath)careerPath.get(c);
                        Vector listDate = getRangeOfDate(""+path.getWorkFrom(), ""+path.getWorkTo());
                        boolean status = false;
                        // cari kesamaan tanggal (yyyy-mm-hh)
                        if (listPeriod != null && listPeriod.size()>0){
                            for(int p=0; p<listPeriod.size(); p++){
                                String periodDate = (String)listPeriod.get(p);
                                if (listDate != null && listDate.size()>0){
                                    for(int d=0; d<listDate.size(); d++){
                                        String careerDate = (String)listDate.get(d);
                                        if (periodDate.equals(careerDate)){
                                            status = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (status == true){
                            salaryChange.setDepartmentOld(path.getDepartment());
                            salaryChange.setSectionOld(path.getSection());
                            salaryChange.setPositionOld(path.getPosition());
                            salaryChange.setLevelOld(path.getLevel());
                        }
                    }
                }
                
                salaryChange.setBasicOld(1);
                salaryChange.setMeritOld(1);
                salaryChange.setGradeOld(1);
                salaryChange.setTotalOld(1);
                double totalNew = 0;
                if (listSlipComp != null && listSlipComp.size()>0){
                    if (listSlipComp.size() < 3){
                        for(int i=0; i<3; i++){
                            listSlipComp.add(0);
                        }
                    }
                    Double ALW01 = (Double)listSlipComp.get(0);
                    Double ALW04 = (Double)listSlipComp.get(1);
                    Double ALW05 = (Double)listSlipComp.get(2);
                    salaryChange.setBasicNew(ALW01);
                    salaryChange.setMeritNew(ALW04);
                    salaryChange.setGradeNew(ALW05);
                    totalNew = ALW01 + ALW04 + ALW05;
                }
                
                salaryChange.setTotalNew(totalNew);
                lists.add(salaryChange);
            }
            rs.close();
            return lists;

        } catch (Exception e) {

            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    public static Vector listSalaryChange(long periodId) {
        /* ganti analisa dengan mengambil data sesuai dengna EMPLOYEE_NUM*/
        Vector listCompare = new Vector();
        Vector listOld = getOrganizationStructure(getPreviousPeriod(periodId));
        Vector listNew = getOrganizationStructureNew(String.valueOf(periodId));
        if (listNew != null && listNew.size()>0){
            for(int i=0; i<listNew.size(); i++){
                SalaryChangeEntity scNew = (SalaryChangeEntity)listNew.get(i);
                SalaryChangeEntity salaryChange = new SalaryChangeEntity();
                double totalOld = 0;
                double totalNew = 0;
                
                salaryChange.setDepartmentOld("-");
                salaryChange.setSectionOld("-");
                salaryChange.setPositionOld("-");
                salaryChange.setLevelOld("-");
                
                salaryChange.setBasicOld(0);
                salaryChange.setMeritOld(0);
                salaryChange.setGradeOld(0);
                
                for(int j=0; j<listOld.size(); j++){
                    SalaryChangeEntity scOld = (SalaryChangeEntity)listOld.get(j);
                    if (scNew.getPayrollNumber().equals(scOld.getPayrollNumber())){
                        if ((scOld.getDepartmentOld().equals("-"))&&(scOld.getSectionOld().equals("-"))&&(scOld.getPositionOld().equals("-"))&&(scOld.getLevelOld().equals("-"))){
                            salaryChange.setDepartmentOld(scNew.getDepartmentNew());
                            salaryChange.setSectionOld(scNew.getSectionNew());
                            salaryChange.setPositionOld(scNew.getPositionNew());
                            salaryChange.setLevelOld(scNew.getLevelNew());
                        } else {
                            salaryChange.setDepartmentOld(scOld.getDepartmentOld());
                            salaryChange.setSectionOld(scOld.getSectionOld());
                            salaryChange.setPositionOld(scOld.getPositionOld());
                            salaryChange.setLevelOld(scOld.getLevelOld());
                        }
                        
                        salaryChange.setBasicOld(convertLong(scOld.getBasicNew()));
                        salaryChange.setMeritOld(convertLong(scOld.getMeritNew()));
                        salaryChange.setGradeOld(convertLong(scOld.getGradeNew()));
                        salaryChange.setTotalOld(convertLong(scOld.getTotalNew()));
                        totalOld = scOld.getTotalNew();
                    }
                }
                
                salaryChange.setPayrollNumber(scNew.getPayrollNumber());
                salaryChange.setFullName(scNew.getFullName());
                
                salaryChange.setDepartmentNew(scNew.getDepartmentNew());
                salaryChange.setSectionNew(scNew.getSectionNew());
                salaryChange.setPositionNew(scNew.getPositionNew());
                salaryChange.setLevelNew(scNew.getLevelNew());
                
                salaryChange.setBasicNew(convertLong(scNew.getBasicNew()));
                salaryChange.setMeritNew(convertLong(scNew.getMeritNew()));
                salaryChange.setGradeNew(convertLong(scNew.getGradeNew()));
                salaryChange.setTotalNew(convertLong(scNew.getTotalNew()));
                totalNew = scNew.getTotalNew();
                if (totalOld != totalNew){
                    listCompare.add(salaryChange);
                }
                
            }
        }
        return listCompare;
    }
    
    /* Convert Long */
    public static long convertLong(double val){
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(0, RoundingMode.HALF_DOWN);
        return bDecimal.longValue();
    }
    
    public static Vector listCareerPath(long employeeId){
        Vector list = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM hr_work_history_now ";
            sql += " WHERE hr_work_history_now.EMPLOYEE_ID="+employeeId;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CareerPath career = new CareerPath();
                career.setDepartment(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT]));
                career.setSection(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION]));
                career.setPosition(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION]));
                career.setLevel(rs.getString(PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL]));
                career.setWorkFrom(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]));
                career.setWorkTo(rs.getDate(PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]));
                list.add(career);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return list;
    }
    
    public static Vector getRangeOfDate(String startDate, String endDate){
        Vector rangeDate = new Vector();
        String[] arrStart = startDate.split("-");
        String[] arrEnd = endDate.split("-");
        
        int yearStart = Integer.valueOf(arrStart[0]);
        int monthStart = Integer.valueOf(arrStart[1]);
        int dayStart = Integer.valueOf(arrStart[2]);
        
        int yearEnd = Integer.valueOf(arrEnd[0]);
        int monthEnd = Integer.valueOf(arrEnd[1]);
        int dayEnd = Integer.valueOf(arrEnd[2]);
        
        String tanggal = "";
        for(int y=yearStart; y<=yearEnd; y++){
            if (y < yearEnd){
                for(int m=monthStart; m<12; m++){
                    if (dayStart > 1){
                        for(int d=dayStart; d<=31; d++){
                            tanggal = y+"-"+getDigit(m)+"-"+getDigit(d);
                            rangeDate.add(tanggal);
                        }
                        dayStart = 1;
                    } else {
                        for(int d=1; d<=31; d++){
                            tanggal = y+"-"+getDigit(m)+"-"+getDigit(d);
                            rangeDate.add(tanggal);
                        }
                    }
                }
                monthStart = 1;
            }
            if (y == yearEnd){
                if (monthStart > monthEnd){
                    for(int m=monthStart; m<=12; m++){
                        if (dayStart > 1){
                            for(int d=dayStart; d<=31; d++){
                                tanggal = y+"-"+getDigit(m)+"-"+getDigit(d);
                                rangeDate.add(tanggal);
                            }
                            dayStart = 1;
                        } else {
                            for(int d=1; d<=31; d++){
                                tanggal = y+"-"+getDigit(m)+"-"+getDigit(d);
                                rangeDate.add(tanggal);
                            }
                        }
                    }
                    monthStart = 1;
                    for(int m=1; m<=monthEnd; m++){
                        if (m == monthEnd){
                            for(int d=1; d<=dayEnd; d++){
                                tanggal = y+"-"+getDigit(m)+"-"+getDigit(d);
                                rangeDate.add(tanggal);
                            }
                        } else {
                            for(int d=1; d<=31; d++){
                                tanggal = y+"-"+getDigit(m)+"-"+getDigit(d);
                                rangeDate.add(tanggal);
                            }
                        }
                    }
                } else {
                    if (monthStart < monthEnd){
                        for(int m=monthStart; m<=monthEnd; m++){
                            if (m == monthEnd){
                                for(int d=1; d<=dayEnd; d++){
                                    tanggal = y+"-"+getDigit(m)+"-"+getDigit(d);
                                    rangeDate.add(tanggal);
                                }
                            } else {
                                if (dayStart > 1){
                                    for(int d=dayStart; d<=31; d++){
                                        tanggal = y+"-"+getDigit(m)+"-"+getDigit(d);
                                        rangeDate.add(tanggal);
                                    }
                                    dayStart = 1;
                                } else {
                                    for(int d=1; d<=31; d++){
                                        tanggal = y+"-"+getDigit(m)+"-"+getDigit(d);
                                        rangeDate.add(tanggal);
                                    }
                                }
                            }
                        }
                    }
                    if (monthStart == monthEnd){
                        for(int d=dayStart; d<=dayEnd; d++){
                            tanggal = y+"-"+getDigit(monthStart)+"-"+getDigit(d);
                            rangeDate.add(tanggal);
                        }
                    }
                }
            }
        }
        
        return rangeDate;
    }
    
    public static String getDigit(int val){
        String str = "";
        String nilai = String.valueOf(val);
        if (nilai.length() == 1){
            str = "0"+nilai;
        } else {
            str = nilai;
        }
        return str;
    }
    
}