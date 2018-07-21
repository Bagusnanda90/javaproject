/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.report.lkpbu;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.PstTrainingActivityActual;
import com.dimata.harisma.entity.masterdata.PstCompany;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstEducation;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstResignedReason;
import com.dimata.harisma.entity.masterdata.PstTrainType;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author khirayinnura
 */
public class PstLkpbu extends DBHandler {
    
    
    
    
    public static Vector getDataFiltering(Vector listLkpbu) {
        
        Vector lists = new Vector();
        String codeStatus = "";
        String codeJabatan = "";
        String codeEdu = "";
        int umurPre1 = 0;
        int umurPre2 = 0;
        int umurPre3 = 0;
        int umurPre4 = 0;
        int pensiun = 0;
        int totalRealisasi = 0;

        for(int i = 0; i < listLkpbu.size(); i++) {
            Lkpbu lkpbu = (Lkpbu)listLkpbu.get(i);
            Vector rowx = new Vector(1,1);
            if(i == 0){
                codeJabatan = lkpbu.getEmpLevelCode();
                codeEdu = lkpbu.getEmpEduCode();
                codeStatus = lkpbu.getEmpCategoryNameCode();
            }
            
            if( lkpbu.getEmpCategoryNameCode().equals(codeStatus) && lkpbu.getEmpLevelCode().equals(codeJabatan) && 
                    lkpbu.getEmpEduCode().equals(codeEdu)){
                
                totalRealisasi++;
                
                if(lkpbu.getEmpUmur() == 54){
                    umurPre1++;
                } else if(lkpbu.getEmpUmur() == 53){
                    umurPre2++;
                } else if(lkpbu.getEmpUmur() == 52){
                    umurPre3++;
                } else if(lkpbu.getEmpUmur() == 51){
                    umurPre4++;
                } else if(lkpbu.getEmpUmur() >= 55){
                    pensiun++;
                } else {
                    
                }
                
                 if(i == ( listLkpbu.size()-1) ){
                        rowx.add(lkpbu.getEmpLevelCode());
                        codeEdu = lkpbu.getEmpEduCode();
                        int jml = codeEdu.length();
                        if(jml == 1){
                            rowx.add("0"+codeEdu);
                        } else {
                            rowx.add(""+codeEdu);
                        }
                        rowx.add(lkpbu.getEmpCategoryNameCode());
                        rowx.add(totalRealisasi);
                        int Prediksi1 = totalRealisasi - umurPre1;
                        rowx.add(Prediksi1);
                        int Prediksi2 = totalRealisasi - umurPre2;
                        rowx.add(Prediksi2);
                        int Prediksi3 = totalRealisasi - umurPre3;
                        rowx.add(Prediksi3);
                        int Prediksi4 = totalRealisasi - umurPre4;
                        rowx.add(Prediksi4);
                        
                        lists.add(rowx);
                    }
                                                 
            } else {
                rowx.add(codeJabatan);
                
                int jml = codeEdu.length();
                if(jml == 1){
                    rowx.add("0"+codeEdu);
                } else {
                    rowx.add(""+codeEdu);
                }                
                
                rowx.add(codeStatus);
                rowx.add(totalRealisasi);
                int Prediksi1 = (totalRealisasi - pensiun) - umurPre1;
                rowx.add(Prediksi1);
                int Prediksi2 = (totalRealisasi - pensiun) - umurPre2;
                rowx.add(Prediksi2);
                int Prediksi3 = (totalRealisasi - pensiun) - umurPre3;
                rowx.add(Prediksi3);
                int Prediksi4 = (totalRealisasi - pensiun) - umurPre4;
                rowx.add(Prediksi4);
                        
                codeJabatan = lkpbu.getEmpLevelCode();
                codeEdu = lkpbu.getEmpEduCode();
                codeStatus = lkpbu.getEmpCategoryNameCode();
                
                totalRealisasi=0;
                umurPre1 = 0;
                umurPre2 = 0;
                umurPre3 = 0;
                umurPre4 = 0;
                pensiun = 0;
                
                i--;
                                
                lists.add(rowx);
            } 
    }
        
        return lists;
    
    }
    
    /*
     * SELECT hr_emp_category.CODE AS category_code, BIRTH_DATE, hr_level.CODE AS level_code,  MAX(hr_education.EDUCATION_LEVEL) AS EDU_LEVEL, SEX FROM hr_employee
        INNER JOIN hr_emp_category ON hr_employee.EMP_CATEGORY_ID = hr_emp_category.EMP_CATEGORY_ID
        INNER JOIN hr_level ON hr_employee.LEVEL_ID = hr_level.LEVEL_ID
        INNER JOIN hr_emp_education ON hr_employee.EMPLOYEE_ID=hr_emp_education.EMPLOYEE_ID
        INNER JOIN hr_education ON hr_emp_education.EDUCATION_ID=hr_education.EDUCATION_ID
        WHERE hr_employee.COMMENCING_DATE LIKE '%2014%' 
        GROUP BY hr_employee.employee_id
        ORDER BY category_code, level_code, EDU_LEVEL;
     *
     */
    public static Vector listEmployeeJabatan(/*dedy_20150904 int whereYear*/) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT hr_emp_category."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_CODE]+" AS category_code, BIRTH_DATE, hr_level."+PstLevel.fieldNames[PstLevel.FLD_CODE]+
                    " AS level_code,  MAX(hr_education."+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_LEVEL]+") AS EDU_LEVEL, SEX"+
                    " FROM hr_employee"+
                    " INNER JOIN hr_emp_category ON hr_employee.EMP_CATEGORY_ID = hr_emp_category.EMP_CATEGORY_ID"+
                    " INNER JOIN hr_level ON hr_employee.LEVEL_ID = hr_level.LEVEL_ID"+
                    " INNER JOIN hr_emp_education ON hr_employee.EMPLOYEE_ID=hr_emp_education.EMPLOYEE_ID"+
                    " INNER JOIN hr_education ON hr_emp_education.EDUCATION_ID=hr_education.EDUCATION_ID"+
                    /* dedy_20150904 " WHERE hr_employee.COMMENCING_DATE LIKE '%"+whereYear+"%'"+ */ 
                    " GROUP BY hr_employee.employee_id"+
                    " ORDER BY category_code, level_code, EDU_LEVEL";
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Lkpbu lkpbu = new Lkpbu();
                resultToObjectJabatan(rs, lkpbu);
                lists.add(lkpbu);
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
    
    /*
     * SELECT resign.RESIGNED_CODE, lvl.CODE AS level_code, SEX FROM hr_employee AS emp
        INNER JOIN hr_resigned_reason AS resign ON emp.RESIGNED_REASON_ID = resign.RESIGNED_REASON_ID
        INNER JOIN hr_level AS lvl ON emp.LEVEL_ID = lvl.LEVEL_ID
WHERE emp.RESIGNED_DATE LIKE '%2015%' 
        GROUP BY emp.employee_id
        ORDER BY resign.RESIGNED_CODE, level_code;
     */
    
    public static Vector listEmployeePensiun(int whereYear) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT resign.RESIGNED_CODE, lvl.CODE AS level_code, SEX"+
                    " FROM hr_employee AS emp"+
                    " INNER JOIN hr_resigned_reason AS resign ON emp.RESIGNED_REASON_ID = resign.RESIGNED_REASON_ID"+
                    " INNER JOIN hr_level AS lvl ON emp.LEVEL_ID = lvl.LEVEL_ID"+
                    " WHERE emp.RESIGNED_DATE LIKE '%"+whereYear+"%'"+
                    " GROUP BY emp.employee_id"+
                    " ORDER BY resign.RESIGNED_CODE, level_code";
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Lkpbu lkpbu = new Lkpbu();
                resultToObjectPensiun(rs, lkpbu);
                lists.add(lkpbu);
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
    
    /*
     * lkpbu 806
      SELECT
        tt.CODE,
        YEAR(taa.DATE),
        taa.ATENDEES
      FROM hr_training_activity_actual AS taa
        INNER JOIN hr_training AS t
          ON taa.TRAINING_ID = t.TRAINING_ID
        INNER JOIN hr_training_type AS tt
          ON t.TYPE = tt.TYPE_ID
      WHERE YEAR(TAA.DATE) = 2015
      ORDER BY tt.CODE;
     */
    public static Vector listTrainingAtt(int whereYear) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT tt.CODE, taa.DATE, taa.ATENDEES FROM hr_training_activity_actual AS taa"+
                    " INNER JOIN hr_training AS t ON taa.TRAINING_ID = t.TRAINING_ID"+
                    " INNER JOIN hr_training_type AS tt ON t.TYPE = tt.TYPE_ID"+
                    " WHERE YEAR(TAA.DATE) = '"+whereYear+"'"+
                    " ORDER BY tt.CODE;";
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Lkpbu lkpbu = new Lkpbu();
                resultToObjectTrainAtt(rs, lkpbu);
                lists.add(lkpbu);
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
    
    
    public static void resultToObjectJabatan(ResultSet rs, Lkpbu lkpbu) {
        try {
            lkpbu.setEmpCategoryNameCode(rs.getString("category_code"));
            lkpbu.setEmpBirthDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]));
            lkpbu.setEmpLevelCode(rs.getString("level_code"));
            lkpbu.setEmpEduCode(rs.getString("EDU_LEVEL"));
            lkpbu.setEmpSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
            
        } catch (Exception e) {
        }
    }
    
    public static void resultToObjectTrainAtt(ResultSet rs, Lkpbu lkpbu) {
        try {
            lkpbu.setCode(rs.getString(PstTrainType.fieldNames[PstTrainType.FLD_TRAIN_TYPE_CODE]));
            lkpbu.setDate(rs.getDate(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_DATE]));
            lkpbu.setTrainAteendes(rs.getInt(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_ATENDEES]));
            
        } catch (Exception e) {
        }
    }
    
    public static void resultToObjectPensiun(ResultSet rs, Lkpbu lkpbu) {
        try {
            lkpbu.setResignCategory(rs.getString(PstResignedReason.fieldNames[PstResignedReason.FLD_RESIGNED_CODE]));
            lkpbu.setEmpLevelCode(rs.getString("level_code"));
            lkpbu.setEmpSex(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
            
        } catch (Exception e) {
        }
    }
    /*
     * SELECT emp.EMPLOYEE_NUM, emp.FULL_NAME, gen.COMPANY, d.DIVISION FROM hr_employee AS emp
        INNER JOIN pay_general AS gen
        ON
        gen.GEN_ID=emp.COMPANY_ID
        INNER JOIN hr_division AS d
        ON
        d.DIVISION_ID=emp.DIVISION_ID
        WHERE POSITION_ID='';
     */
    public static Vector listPosition(String whereClause) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT emp.EMPLOYEE_NUM, emp.FULL_NAME, gen.COMPANY, d.DIVISION"+
                    " FROM hr_employee AS emp"+
                    " INNER JOIN pay_general AS gen ON gen.GEN_ID=emp.COMPANY_ID"+
                    " INNER JOIN hr_division AS d ON d.DIVISION_ID=emp.DIVISION_ID";
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
                Lkpbu lkpbu = new Lkpbu();
                resultToObjectKadivSdm(rs, lkpbu);
                lists.add(lkpbu);
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
    public static void resultToObjectKadivSdm(ResultSet rs, Lkpbu lkpbu) {
        try {
            lkpbu.setEmpNumTtd(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
            lkpbu.setNameTtd(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
            lkpbu.setCompanyTtd(rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
            lkpbu.setDivisiTtd(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
            
        } catch (Exception e) {
        }
    }
}
