/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.payroll;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.payroll.PayEmpLevel;
import com.dimata.harisma.entity.payroll.PayInput;
import com.dimata.harisma.entity.payroll.PayPeriod;
import com.dimata.harisma.entity.payroll.PaySlip;
import com.dimata.harisma.entity.payroll.PstPayEmpLevel;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class SessPayInput {
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
    public static Vector listPayInput(long departmentName,long companyId,long divisionId, String levelCode, long sectionName, String searchNrFrom, String searchNrTo, String searchName, long periodId, int dataStatus,int isChekedRadioButtonSearchNr,String searchNr) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        if (departmentName == 0 && levelCode.equals("") && sectionName == 0 && searchNrFrom == null && searchNrTo == null && searchName == null && periodId == 0 && dataStatus == 0) {
            return new Vector(1, 1);
        }
        
         PayPeriod payPeriod123 = new PayPeriod();
        try {
            payPeriod123 = PstPayPeriod.fetchExc(periodId); 
        } catch (Exception e){
            System.out.printf("period Id nya mana?");
        }
        try {
            String sql = " SELECT distinct EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    //update by satrya 2013-02-21
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    + ", POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_DAY_PRESENT]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_DAY_PAID_LV]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_DAY_ABSENT]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_DAY_UNPAID_LV]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_DAY_LATE]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_NOTE]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_STATUS]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_PROCENTASE_PRESENCE]
                    + ", LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    //update by satrya 2013-02-20
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_DATE_OK_WITH_LEAVE]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_INSENTIF]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_TOTAL_DAY_OFF_OVERTIME]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_DAY_OFF_SCHEDULE]
                    //update by satrya 2013-05-06
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE]
                    
                    //update by satrya 2014-02-06
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_OVERTIME_IDX]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_MEAL_ALLOWANCE_MONEY]
                    + ", PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_MEAL_ALLOWANCE_MONEY_ADJUSMENT]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS PAY"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " INNER JOIN "+ PstPosition.TBL_HR_POSITION + " AS POS ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "!=0"
                    + " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]
                    + "=" + PstPayEmpLevel.CURRENT
                  //  + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.STS_COMMING;
  
                    //update by priska pengambilan employee berdasarkan resigned date nya seduah startdate pay period
                   + " AND ( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.STS_COMMING + " OR EMP."+ PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] +" > \"" + Formater.formatDate(payPeriod123.getStartDate(), "yyyy-MM-dd HH:mm:ss")+ " \" ) " ;
                   
            String whereClause = "";

            if (departmentName > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + departmentName + " AND ";
            }

            //System.out.println("department"+srcEmployee.getDepartment());

            if (levelCode != null && !levelCode.equals("") && !levelCode.equals("0")) {
                whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                        + " LIKE '%" + levelCode.trim() + "%' AND ";
            }

            if (sectionName > 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + sectionName + " AND ";

            }
            
            //update by satrya 2014-02-03
            if (companyId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                        + " = " + companyId + " AND ";
            }
            //update by satrya 2014-02-03
            if (divisionId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                        + " = " + divisionId + " AND ";
            }
            if (periodId != 0) {
                whereClause = whereClause + " PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]
                        + " = " + periodId + " AND ";

            }

            if(isChekedRadioButtonSearchNr==1){
                if(searchNr!=null && searchNr.length()>0){
                            if (searchNr != null && searchNr != "") {
        //                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
        //                        + " = " + "\"" + empNumId.trim() + "\"";//penambahan trima
                        Vector vectNum = logicParser(searchNr);
                        sql = sql + " AND ";
                        if (vectNum != null && vectNum.size() > 0) {
                            sql = sql + " (";
                            for (int i = 0; i < vectNum.size(); i++) {
                                String str = (String) vectNum.get(i); 
                                if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                    sql = sql +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                            + " LIKE '%" + str.trim() + "%' ";
                                } else {
                                    sql = sql + str.trim();
                                }
                            }
                            sql = sql + ")";
                        }

                    }
                }
            }else{
                    if ((searchNrFrom != null) && (searchNrFrom.length() > 0)) {
                    if(searchNrTo==null || searchNrTo.length()==0){
                        searchNrTo =searchNrFrom;
                    }
                    Vector vectNrFrom = logicParser(searchNrFrom);
                    if (vectNrFrom != null && vectNrFrom.size() > 0) {
                        whereClause = whereClause + " (";
                        for (int i = 0; i < vectNrFrom.size(); i++) {
                            String str = (String) vectNrFrom.get(i);
                            if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                        + " BETWEEN '" + searchNrFrom + "' AND '" + searchNrTo + "'";
                            } else {
                                whereClause = whereClause + str.trim();
                            }
                        }
                        whereClause = whereClause + ") AND ";
                    }

                }
            }
            


            if ((searchName != null) && (searchName.length() > 0)) {
                Vector vectName = logicParser(searchName);
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") AND ";
                }

            }

            if (dataStatus < 2) {
                whereClause = whereClause + " PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_STATUS]
                        + " = " + dataStatus + " AND ";

            }

            /* if(statusData < 2){
            whereClause += " SLIP."+PstPaySlip.fieldNames[PstPaySlip.FLD_STATUS]+
            " = "+ statusData + " AND ";
            }*/

            /*if(dataStatus < 2){
            if(dataStatus==0)
            whereClause += " PAY."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+
            " = "+ dataStatus + " AND ";
            if(dataStatus==1){
            Date now = new Date();
            int monthNow =now.getMonth()+1;
            int yearNow = now.getYear()+1900;
            whereClause += " PAY."+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+
            " = "+ dataStatus + " AND ";
            }
            }*/


            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }


            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            //sql = sql + " LIMIT " + start + "," + recordToGet;

            //System.out.println("\t SQL listPayInput : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

               Employee employee = new Employee();
                PaySlip paySlip = new PaySlip();
                PayEmpLevel payEmpLevel = new PayEmpLevel();
                
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                vect.add(employee);
                paySlip.setOID(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]));
                paySlip.setEmployeeId(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]));
                paySlip.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                //tidak muncul positionnya update by satrya 2013-02-18
                //  paySlip.setPosition(rs.getString(PstPaySlip.fieldNames[PstPaySlip.FLD_POSITION]));
                paySlip.setDayPresent(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_DAY_PRESENT]));
                paySlip.setDayPaidLv(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_DAY_PAID_LV]));
                paySlip.setDayAbsent(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_DAY_ABSENT]));
                paySlip.setDayUnpaidLv(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_DAY_UNPAID_LV]));
                paySlip.setDayLate(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_DAY_LATE]));
                paySlip.setNote(rs.getString(PstPaySlip.fieldNames[PstPaySlip.FLD_NOTE]));
                paySlip.setStatus(rs.getInt(PstPaySlip.fieldNames[PstPaySlip.FLD_STATUS]));
                paySlip.setProcentasePresence(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_PROCENTASE_PRESENCE]));
                //update by satrya 2013-02-20
                 paySlip.setDaysOkWithLeave(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_DATE_OK_WITH_LEAVE]));
                paySlip.setInsentif(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_INSENTIF]));
                paySlip.setTotDayOffOt(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_TOTAL_DAY_OFF_OVERTIME]));
                paySlip.setDayOffSch(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_DAY_OFF_SCHEDULE]));
                
                paySlip.setOvIdxAdj(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT]));
                
                //update by satrya 2014-02-06
                paySlip.setOvertimeIdxByForm(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_OVERTIME_IDX])); 
                paySlip.setMealAllowanceMoneyByForm(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_MEAL_ALLOWANCE_MONEY]));
                paySlip.setMealAllowanceMoneyAdj(rs.getDouble(PstPaySlip.fieldNames[PstPaySlip.FLD_MEAL_ALLOWANCE_MONEY_ADJUSMENT]));
                if(rs.getString(PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE])==null || rs.getString(PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE]).equalsIgnoreCase("null")){
                    paySlip.setPrivateNote("");
                }else{
                    paySlip.setPrivateNote(rs.getString(PstPaySlip.fieldNames[PstPaySlip.FLD_PRIVATE_NOTE]));
                }
                
                vect.add(paySlip);

                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));

                vect.add(payEmpLevel);

                result.add(vect);
                
                
            }

            
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
       
    }

}
