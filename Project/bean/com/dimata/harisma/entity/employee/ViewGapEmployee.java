/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

import com.dimata.harisma.entity.masterdata.Competency;
import com.dimata.harisma.entity.masterdata.Education;
import com.dimata.harisma.entity.masterdata.EmployeeCompetency;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PositionCompetencyRequired;
import com.dimata.harisma.entity.masterdata.PositionEducationRequired;
import com.dimata.harisma.entity.masterdata.PositionTrainingRequired;
import com.dimata.harisma.entity.masterdata.PstCompetency;
import com.dimata.harisma.entity.masterdata.PstEducation;
import com.dimata.harisma.entity.masterdata.PstEmployeeCompetency;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstPositionCompetencyRequired;
import com.dimata.harisma.entity.masterdata.PstPositionEducationRequired;
import com.dimata.harisma.entity.masterdata.PstPositionTrainingRequired;
import com.dimata.harisma.entity.masterdata.PstTraining;
import com.dimata.harisma.entity.masterdata.Training;
import java.util.Vector;

/**
 *
 * @author Dimata 007
 */
public class ViewGapEmployee {
    
    public String getCompetencyName(long oid){
        String str = "-";
        try {
            Competency comp = PstCompetency.fetchExc(oid);
            str = comp.getCompetencyName();
        } catch(Exception e){
            System.out.println("getCompetency = "+e.toString());
        }
        return str;
    }
    
    public String getTrainingName(long oid){
        String str = "-";
        try {
            Training training = PstTraining.fetchExc(oid);
            str = training.getName();
        } catch(Exception e){
            System.out.println("getTrainingName = "+e.toString());
        }
        return str;
    }
    
    public String getEducationName(long oid){
        String str = "-";
        try {
            Education edu = PstEducation.fetchExc(oid);
            str = edu.getEducation();
        } catch(Exception e){
            System.out.println("getEducationName = "+e.toString());
        }
        return str;
    }
    
    public String drawGapEmployee(long employeeId){
        String html = "";
        String tempHtml = "";
        String whereClause = "";
        Employee employee = new Employee();
        Position position = new Position();
        if (employeeId != 0){
            try {
                /* Get Employee Data */
                employee = PstEmployee.fetchExc(employeeId);
                /* Get Position Data */
                position = PstPosition.fetchExc(employee.getPositionId());
                whereClause = PstPositionCompetencyRequired.fieldNames[PstPositionCompetencyRequired.FLD_POSITION_ID]+"="+employee.getPositionId();
                Vector listCompetency = PstPositionCompetencyRequired.list(0, 0, whereClause, "");
                whereClause = PstPositionTrainingRequired.fieldNames[PstPositionTrainingRequired.FLD_POSITION_ID]+"="+employee.getPositionId();
                Vector listTraining = PstPositionTrainingRequired.list(0, 0, whereClause, "");
                whereClause = PstPositionEducationRequired.fieldNames[PstPositionEducationRequired.FLD_POSITION_ID]+"="+employee.getPositionId();
                Vector listEducation = PstPositionEducationRequired.list(0, 0, whereClause, "");
                whereClause = PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID]+"="+employeeId;
                Vector listEmpCompetency = PstEmployeeCompetency.list(0, 0, whereClause, "");
                whereClause = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+"="+employeeId;
                Vector listEmpTraining = PstTrainingHistory.list(0, 0, whereClause, "");
                whereClause = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+"="+employeeId;
                Vector listEmpEducation = PstEmpEducation.list(0, 0, whereClause, "");
                html += "<h2>"+position.getPosition()+"</h2>";
                html += "<div class=\"title-part\">Competency</div>";
                html += "<table class=\"tblStyle\">";
                html += "<tr>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\" colspan=\"2\">Competency Required</td>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\" colspan=\"2\">Competency Employee</td>";
                html += "</tr>";
                html += "<tr>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\">Title</td>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\">Score</td>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\">Score</td>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\">Gap</td>";
                html += "</tr>";
                if (listCompetency != null && listCompetency.size()>0){
                    for (int i=0; i<listCompetency.size(); i++){
                        PositionCompetencyRequired posComp = (PositionCompetencyRequired)listCompetency.get(i);
                        html += "<tr>";
                        html += "<td>"+getCompetencyName(posComp.getCompetencyId())+"</td>";
                        html += "<td>"+posComp.getScoreReqRecommended()+"</td>";
                        if (listEmpCompetency != null && listEmpCompetency.size()>0){
                            for (int j=0; j<listEmpCompetency.size(); j++){
                                EmployeeCompetency empComp = (EmployeeCompetency)listEmpCompetency.get(j);
                                if (posComp.getCompetencyId()==empComp.getOID()){
                                    tempHtml  = "<td>"+empComp.getLevelValue()+"</td>";
                                    tempHtml += "<td>"+(posComp.getScoreReqRecommended()-empComp.getLevelValue())+"</td>";
                                }
                            }
                            if (tempHtml.equals("")){
                                html += "<td>-</td>";
                                html += "<td>-</td>";
                            } else {
                                html += tempHtml;
                                tempHtml = "";
                            }
                        } else {
                            html += "<td>-</td>";
                            html += "<td>-</td>";
                        }

                        html += "</tr>";
                    }
                }
                html += "</table>";
                
                html += "<div class=\"title-part\">Training</div>";
                html += "<table class=\"tblStyle\">";
                html += "<tr>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\" colspan=\"2\">Training Required</td>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\" colspan=\"2\">Training Employee</td>";
                html += "</tr>";
                html += "<tr>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\">Title</td>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\">Score</td>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\">Score</td>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\">Gap</td>";
                html += "</tr>";
                if (listTraining != null && listTraining.size()>0){
                    for (int i=0; i<listTraining.size(); i++){
                        PositionTrainingRequired posTrain = (PositionTrainingRequired)listTraining.get(i);
                        html += "<tr>";
                        html += "<td>"+getTrainingName(posTrain.getTrainingId())+"</td>";
                        html += "<td>"+posTrain.getPointRecommended()+"</td>";
                        if (listEmpTraining != null && listEmpTraining.size()>0){
                            for (int j=0; j<listEmpTraining.size(); j++){
                                TrainingHistory empTrain = (TrainingHistory)listEmpTraining.get(j);
                                if (posTrain.getTrainingId()==empTrain.getTrainingId()){
                                    tempHtml  = "<td>"+empTrain.getPoint()+"</td>";
                                    tempHtml += "<td>"+(posTrain.getPointRecommended()-empTrain.getPoint())+"</td>";
                                }
                            }
                            if (tempHtml.equals("")){
                                html += "<td>-</td>";
                                html += "<td>-</td>";
                            } else {
                                html += tempHtml;
                                tempHtml = "";
                            }
                        } else {
                            html += "<td>-</td>";
                            html += "<td>-</td>";
                        }

                        html += "</tr>";
                    }
                }
                html += "</table>";
                
                html += "<div class=\"title-part\">Education</div>";
                html += "<table class=\"tblStyle\">";
                html += "<tr>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\" colspan=\"2\">Education Required</td>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\" colspan=\"2\">Education Employee</td>";
                html += "</tr>";
                html += "<tr>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\">Title</td>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\">Score</td>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\">Score</td>";
                html += "<td class=\"title_tbl\" style=\"background-color:#DDD;\">Gap</td>";
                html += "</tr>";
                if (listEducation != null && listEducation.size()>0){
                    for (int i=0; i<listEducation.size(); i++){
                        PositionEducationRequired posEdu = (PositionEducationRequired)listEducation.get(i);
                        html += "<tr>";
                        html += "<td>"+ getEducationName(posEdu.getEducationId()) +"</td>";
                        html += "<td>"+ posEdu.getPointRecommended() +"</td>";
                        if (listEmpEducation != null && listEmpEducation.size()>0){
                            for (int j=0; j<listEmpEducation.size(); j++){
                                EmpEducation empEdu = (EmpEducation)listEmpEducation.get(j);
                                if (posEdu.getEducationId()==empEdu.getEducationId()){
                                    tempHtml  = "<td>"+empEdu.getPoint()+"</td>";
                                    tempHtml += "<td>"+(posEdu.getPointRecommended()-empEdu.getPoint())+"</td>";
                                }
                            }
                            if (tempHtml.equals("")){
                                html += "<td>-</td>";
                                html += "<td>-</td>";
                            } else {
                                html += tempHtml;
                                tempHtml = "";
                            }
                        } else {
                            html += "<td>-</td>";
                            html += "<td>-</td>";
                        }

                        html += "</tr>";
                    }
                }
                html += "</table>";
            } catch(Exception e){
                System.out.println("Cant view employee gap :"+e.toString());
            }
        }
        
        return html;
    }
}
