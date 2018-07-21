/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.session.employee.SessEmployeePicture;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import java.sql.ResultSet;
import java.util.Vector;

/**
 * Date : 2015-09-11
 *
 * @author Hendra Putu
 */
public class StructureModule {
    private long empId = 0;
    private String empName = "";
    private String empPayroll = "";
    private int empResignStatus = 0;
    private String empPhoto = "";
    private String whereEmployee;
    
    public String getWhereEmployee() {
        return whereEmployee;
    }

    public void setWhereEmployee(String whereEmployee) {
        this.whereEmployee = whereEmployee;
    }
    
    public long getTopPosition(Vector listData) {
        long[] arrUp = new long[listData.size()];
        long[] arrDown = new long[listData.size()];
        long topMain = 0;
        int checkUp = 0;
        if (listData != null && listData.size() > 0) {
            for (int i = 0; i < listData.size(); i++) {
                MappingPosition map = (MappingPosition) listData.get(i);
                arrUp[i] = map.getUpPositionId();
                arrDown[i] = map.getDownPositionId();
            }
            for (int j = 0; j < arrUp.length; j++) {
                for (int k = 0; k < arrDown.length; k++) {
                    if (arrUp[j] == arrDown[k]) {
                        checkUp++;
                    }
                }
                if (checkUp == 0) {
                    topMain = arrUp[j];
                }
                checkUp = 0;
            }
        } else {
            topMain = 0;
        }
        return topMain;
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

    public String getCompetencyPosition(long oidPosition) {
        String str = "";
        String whereClause = "" + oidPosition;
        Vector listPosCompetency = PstPositionCompetencyRequired.listInnerJoin(whereClause);
        if (listPosCompetency != null && listPosCompetency.size() > 0) {
            str += "<table>";
            for (int i = 0; i < listPosCompetency.size(); i++) {
                Vector vect = (Vector) listPosCompetency.get(i);
                PositionCompetencyRequired posCom = (PositionCompetencyRequired) vect.get(0);
                Competency comp = (Competency) vect.get(1);
                str += "<tr>";
                str += "<td>" + (i + 1) + ") " + comp.getCompetencyName() + "</td>";
                str += "<td style=\"padding-left:21px;\">" + posCom.getScoreReqMin() + "</td>";
                str += "</tr>";
            }
            str += "</table>";
        }
        return str;
    }
    
    public String getEducationPosition(long oidPosition){
        String str = "";
        String whereClause = "" + oidPosition;
        Vector listPosEducation = PstPositionEducationRequired.listInnerJoin(whereClause);
        if (listPosEducation != null && listPosEducation.size()>0){
            str += "<table>";
            for (int i=0; i<listPosEducation.size(); i++){
                Vector vect = (Vector)listPosEducation.get(i);
                PositionEducationRequired posEdu = (PositionEducationRequired)vect.get(0);
                Education edu = (Education)vect.get(1);
                str += "<tr>";
                str += "<td>" + (i + 1) + ") " + edu.getEducation() + "</td>";
                str += "<td style=\"padding-left:21px;\">" + posEdu.getPointRecommended() + "</td>";
                str += "</tr>";
            }
            str += "</table>";
        }
        return str;
    }
    
    public String getTrainingPosition(long oidPosition){
        String str = "";
        String whereClause = "" + oidPosition;
        Vector listPosTraining = PstPositionTrainingRequired.listInnerJoin(whereClause);
        if (listPosTraining != null && listPosTraining.size()>0){
            str += "<table>";
            for (int i=0; i<listPosTraining.size(); i++){
                Vector vect = (Vector)listPosTraining.get(i);
                PositionTrainingRequired posTrain = (PositionTrainingRequired)vect.get(0);
                Training training = (Training)vect.get(1);
                str += "<tr>";
                str += "<td>" + (i + 1) + ") " + training.getName() + "</td>";
                str += "<td style=\"padding-left:21px;\">" + posTrain.getPointRecommended() + "</td>";
                str += "</tr>";
            }
            str += "</table>";
        }
        return str;
    }

    public String getCompetencyEmployee(long oidPosition, long oidEmployee) {
        String str = "";
        String whereClause = "" + oidPosition;
        Vector listPosCompetency = PstPositionCompetencyRequired.listInnerJoin(whereClause);
        if (listPosCompetency != null && listPosCompetency.size() > 0) {
            str += "<table>";
            for (int i = 0; i < listPosCompetency.size(); i++) {
                Vector vect = (Vector) listPosCompetency.get(i);
                PositionCompetencyRequired posCom = (PositionCompetencyRequired) vect.get(0);
                Competency comp = (Competency) vect.get(1);
                String where = PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID] + "=" + oidEmployee;
                where += " AND " + PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_COMPETENCY_ID] + "=" + posCom.getCompetencyId();
                Vector listEmpComp = PstEmployeeCompetency.list(0, 0, where, "");
                if (listEmpComp != null && listEmpComp.size() > 0) {
                    for (int j = 0; j < listEmpComp.size(); j++) {
                        EmployeeCompetency empComp = (EmployeeCompetency) listEmpComp.get(j);
                        
                        if (posCom.getCompetencyId() == empComp.getCompetencyId()) {
                            str += "<tr>";
                            str += "<td>" + (i + 1) + ") " + comp.getCompetencyName() + "</td>";
                           // str += "<td style=\"padding-left:21px;\">" + empComp.getScoreValue() + "</td>";
                            str += "</tr>";
                        }
                    }
                } else {
                    str += "<tr>";
                    str += "<td>" + (i + 1) + ") " + comp.getCompetencyName() + "</td>";
                    str += "<td style=\"padding-left:21px;\">0</td>";
                    str += "</tr>";
                }

            }
            str += "</table>";
        }
        return str;
    }

    public String getCompetencyGap(long oidPosition, long oidEmployee) {
        String str = "";
        String whereClause = "" + oidPosition;
        Vector listPosCompetency = PstPositionCompetencyRequired.listInnerJoin(whereClause);
        if (listPosCompetency != null && listPosCompetency.size() > 0) {
            str += "<table>";
            for (int i = 0; i < listPosCompetency.size(); i++) {
                Vector vect = (Vector) listPosCompetency.get(i);
                PositionCompetencyRequired posCom = (PositionCompetencyRequired) vect.get(0);
                Competency comp = (Competency) vect.get(1);
                String where = PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID] + "=" + oidEmployee;
                where += " AND " + PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_COMPETENCY_ID] + "=" + posCom.getCompetencyId();
                Vector listEmpComp = PstEmployeeCompetency.list(0, 0, where, "");
                if (listEmpComp != null && listEmpComp.size() > 0) {
                    for (int j = 0; j < listEmpComp.size(); j++) {
                        EmployeeCompetency empComp = (EmployeeCompetency) listEmpComp.get(j);
                        
                        if (posCom.getCompetencyId() == empComp.getCompetencyId()) {
                            double gap = posCom.getScoreReqMin() - 0;//empComp.getScoreValue();
                            str += "<tr>";
                            str += "<td style=\"padding-left:21px;\">" + gap + "</td>";
                            str += "</tr>";
                        }
                    }
                } else {
                    str += "<tr>";
                    str += "<td style=\"padding-left:21px;\">0</td>";
                    str += "</tr>";
                }

            }
            str += "</table>";
        }
        return str;
    }
    
    public void setupEmployee(String whereOther){
        String whereClause = getWhereEmployee() + whereOther;
        Vector listEmp = PstEmployee.list(0, 0, whereClause, "");
        if (listEmp != null && listEmp.size() > 0) {
            for (int i = 0; i < listEmp.size(); i++) {
                Employee emp = (Employee) listEmp.get(i);
                this.empId = emp.getOID();
                this.empName = emp.getFullName();
                this.empPayroll = emp.getEmployeeNum();
                this.empPhoto = emp.getEmployeeNum();
                this.empResignStatus = emp.getResigned();
            }
        } else {
            this.empId = 0;
            this.empName = "-Kosong-";
            this.empPayroll = "0000";
            this.empPhoto = "employee-sample";
            this.empResignStatus = -1;
        }
    }

    public String getEmployeeName() {
        return empName;
    }

    public long getEmployeeId() {
        return empId;
    }

    public String getEmployeePhoto() {
        return empPhoto;
    }

    public String getEmployeePayroll() {
        return empPayroll;
    }
    
    public int getEmployeeResign() {
        return empResignStatus;
    }

    public int getLevelPosition(long positionId) {
        int levelRank = 0;
        long levelId = 0;
        String whereClause = PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + "=" + positionId;
        Vector listPosition = PstPosition.list(0, 0, whereClause, "");
        if (listPosition != null && listPosition.size() > 0) {
            for (int i = 0; i < listPosition.size(); i++) {
                Position position = (Position) listPosition.get(i);
                levelId = position.getLevelId();
            }
            whereClause = PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + "=" + levelId;
            Vector listLevel = PstLevel.list(0, 0, whereClause, "");
            if (listLevel != null && listLevel.size() > 0) {
                for (int j = 0; j < listLevel.size(); j++) {
                    Level level = (Level) listLevel.get(j);
                    levelRank = level.getLevelRank();
                }
            }
        }
        return levelRank;
    }
    
    public String getLevelName(int levelRank){
        String str = "";
        String whereClause = PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+"="+levelRank;
        Vector listLevel = PstLevel.list(0, 0, whereClause, "");
        if (listLevel != null && listLevel.size()>0){
            for(int i=0; i<listLevel.size(); i++){
                Level level = (Level)listLevel.get(i);
                str = level.getLevel();
            }
        }
        return str;
    }

    public String getDrawDownPosition(long oidPosition, long oidTemplate, String whereEmployee, String approot, int chkPhoto, int levelRank) {
        String str = "";
        SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
        int rank = 0;
        String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID] + "=" + oidPosition;
        whereClause += " AND " + PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID] + "=" + oidTemplate;
        Vector listDown = PstMappingPosition.list(0, 0, whereClause, "");
        if (listDown != null && listDown.size() > 0) {
            str = "<table class=\"tblStyle\"><tr>";
            for (int i = 0; i < listDown.size(); i++) {
                MappingPosition pos = (MappingPosition) listDown.get(i);
                rank = getLevelPosition(pos.getDownPositionId());
                if (rank <= levelRank) {
                    whereEmployee = " AND "+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+pos.getDownPositionId();
                    setupEmployee(whereEmployee);
                    if (getEmployeeResign()== 0){
                        String pictPath = sessEmployeePicture.fetchImageEmployee(getEmployeeId());
                        str += "<td>";
                        if (chkPhoto == 1) {
                            str += "<div><img width=\"64\" src=\"" + approot + "/" + pictPath + "\" style=\"padding:3px; background-color: #373737;\" /></div>";
                        }
                        str += "<div style=\"color: #373737\"><a id=\"linkStyle\" href=\"javascript:cmdViewEmployee('" + getEmployeeId() + "')\">";
                        str += "<strong>" + getEmployeeName() + "</strong></a></div>";
                        str += "<div>" + getPositionName(pos.getDownPositionId()) + "</div>" + getDrawDownPosition(pos.getDownPositionId(), oidTemplate, whereEmployee, approot, chkPhoto, levelRank) + "</td>";
                    } else {
                        str += "<td>";
                        str += "<div style=\"color: #373737\"><strong>-Kosong-</strong></div>";
                        str += "<div>" + getPositionName(pos.getDownPositionId()) + "</div>" + getDrawDownPosition(pos.getDownPositionId(), oidTemplate, whereEmployee, approot, chkPhoto, levelRank) + "</td>";
                    }
                }
            }
            str += "</tr></table>";
        }

        return str;
    }

    public String getViewList(long oidPosition, long oidTemplate, String whereEmployee, int inc, String approot, int chkPhoto, int chkGap) {
        String str = "";
        SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
        String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID] + "=" + oidPosition;
        whereClause += " AND " + PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID] + "=" + oidTemplate;
        Vector listDown = PstMappingPosition.list(0, 0, whereClause, "");
        if (listDown != null && listDown.size() > 0) {
            inc = inc + 32;
            for (int i = 0; i < listDown.size(); i++) {
                MappingPosition pos = (MappingPosition) listDown.get(i);
                whereEmployee = " AND "+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+pos.getDownPositionId();
                setupEmployee(whereEmployee);
                String pictPath = sessEmployeePicture.fetchImageEmployee(getEmployeeId());
                str += "<table><tr><td style=\"padding-left:" + inc + "px;\">";
                str += "<table class=\"tblStyle1\"><tr>";
                str += "<td valign=\"top\"><div class=\"box1\">" + (i + 1) + "</div></td>";
                str += "<td>";
                str += "<div class=\"box\">";
                if (chkPhoto == 1 && getEmployeeResign() == 0) {
                    str += "<img width=\"64\" src=\"" + approot + "/" + pictPath + "\" style=\"padding:3px; background-color: #373737;\" />";
                }
                str += "<div style=\"color: #373737\">";
                if (getEmployeeResign() == 0){
                    str += "<a id=\"linkStyle\" href=\"javascript:cmdViewEmployee('" + getEmployeeId() + "')\">";
                    str += "<strong>" + getEmployeeName() + "</strong> | ";
                    str += "<strong>" + getEmployeePayroll() + "</strong>";
                    str += "</a>";
                } else {
                    str += "<strong>-Kosong-</strong> | ";
                    str += "<strong>0000</strong>";
                }
                str += "</div>";
                str += getPositionName(pos.getDownPositionId());
                str += "</div>";
                str += "</td>";
                if (chkGap > 0){
                    str += "<td valign=\"top\">";
                    str += "<strong>Kompetensi yang dibutuhkan</strong>";
                    str += getCompetencyPosition(pos.getDownPositionId());
                    str += "</td>";
                    if (getEmployeeResign() == 0){
                        str += "<td valign=\"top\">";
                        str += "<strong>Kompetensi yang dimiliki</strong>";
                        str += getCompetencyEmployee(pos.getDownPositionId(), getEmployeeId());
                        str += "</td>";
                        str += "<td valign=\"top\">";
                        str += "<strong>Gap</strong>";
                        str += getCompetencyGap(pos.getDownPositionId(), getEmployeeId());
                        str += "</td>";
                    }
                    str += "<td valign=\"top\">";
                    str += "<strong>Pendidikan yang dibutuhkan</strong>";
                    str += getEducationPosition(pos.getDownPositionId());
                    str += "</td>";
                    str += "<td valign=\"top\">";
                    str += "<strong>Pendidikan yang dimiliki</strong>";
                    str += "data";
                    str += "</td>";
                    str += "<td valign=\"top\">";
                    str += "<strong>Gap Pendidikan</strong>";
                    str += "data";
                    str += "</td>";
                    
                    str += "<td valign=\"top\">";
                    str += "<strong>Pelatihan yang dibutuhkan</strong>";
                    str += getTrainingPosition(pos.getDownPositionId());
                    str += "</td>";
                    str += "<td valign=\"top\">";
                    str += "<strong>Pelatihan yang dimiliki</strong>";
                    str += "data";
                    str += "</td>";
                    str += "<td valign=\"top\">";
                    str += "<strong>Gap Pelatihan</strong>";
                    str += "data";
                    str += "</td>";
                    
                    str += "<td valign=\"top\">";
                    str += "<strong>KPI yang dibutuhkan</strong>";
                    str += "data";
                    str += "</td>";
                    str += "<td valign=\"top\">";
                    str += "<strong>KPI yang dimiliki</strong>";
                    str += "data";
                    str += "</td>";
                    str += "<td valign=\"top\">";
                    str += "<strong>Gap KPI</strong>";
                    str += "data";
                    str += "</td>";
                }
                str += "</tr></table>";
                str += "</td></tr></table>";

                str += getViewList(pos.getDownPositionId(), oidTemplate, whereEmployee, inc, approot, chkPhoto, chkGap);
            }
        }

        return str;
    }
    
    public String getViewPrint(long oidPosition, long oidTemplate, String whereEmployee) {
        String str = "";
        String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID] + "=" + oidPosition;
        whereClause += " AND " + PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID] + "=" + oidTemplate;
        Vector listDown = PstMappingPosition.list(0, 0, whereClause, "");
        if (listDown != null && listDown.size() > 0) {
            for (int i = 0; i < listDown.size(); i++) {
                MappingPosition pos = (MappingPosition) listDown.get(i);
                whereEmployee = " AND "+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+pos.getDownPositionId();
                setupEmployee(whereEmployee);
                str += "<tr>";
                    str += "<td>"+getPositionName(oidPosition)+"</td>";
                    str += "<td>"+getPositionName(pos.getDownPositionId())+"</td>";
                    str += "<td>"+getEmployeePayroll()+"</td>";
                    str += "<td>"+getEmployeeName()+"</td>";
                str += "</tr>";
                str += getViewPrint(pos.getDownPositionId(), oidTemplate, whereEmployee);
            }
        }
        return str;
    }

    public String getListEmployeeByPosition(String whereClause) {
        String str = "";
        Vector listEmployee = PstEmployee.list(0, 0, whereClause, "");
        if (listEmployee != null && listEmployee.size() > 0) {
            str += "<table class=\"tblStyle1\">";
            for (int i = 0; i < listEmployee.size(); i++) {
                Employee emp = (Employee) listEmployee.get(i);
                str += "<tr>";
                str += "<td><a id=\"linkStyle\" href=\"javascript:cmdViewEmployee('" + emp.getOID() + "')\">" + emp.getFullName() + " | " + emp.getEmployeeNum() + "</a></td>";
                str += "</tr>";
            }
            str += "</table>";
        }
        return str;
    }
    
    public String getDigit(int val){
        String str = "";
        String nilai = String.valueOf(val);
        if (nilai.length() == 1){
            str = "0"+nilai;
        } else {
            str = nilai;
        }
        return str;
    }
    
    /**
     * getRangeOfDate :
     * mencari rentangan tanggal dari start date to end date.
     * misal :
     * start date = 2015-09-09 To 2015-09-13, maka hasilnya::
     * 2015-09-09, 2015-09-10, 2015-09-11, 2015-09-12, 2015-09-13
     */
    public Vector getRangeOfDate(String startDate, String endDate){
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
    
    public String getCompanyName(long companyId) {
        String str = "";
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
        String str = "";
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
        String str = "";
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

}
