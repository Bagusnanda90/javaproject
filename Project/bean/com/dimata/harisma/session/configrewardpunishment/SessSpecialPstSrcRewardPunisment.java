/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.configrewardpunishment;

import com.dimata.harisma.entity.attendance.employeeoutlet.PstEmployeeOutlet;
import com.dimata.harisma.entity.configrewardnpunisment.PstEntriOpnameSales;
import com.dimata.harisma.entity.configrewardnpunisment.PstRewardAndPunishmentDetail;
import com.dimata.harisma.entity.configrewardnpunisment.PstRewardAndPunishmentMain;
import com.dimata.harisma.entity.configrewardnpunisment.SrcRewardPunishment;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.koefisionposition.PstKoefisionPosition;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author PRISKA
 */
public class SessSpecialPstSrcRewardPunisment {
      public static final String SESS_SRC_REWARD_PUNISMENT = "SESSION_SPECIAL_PST_SRC_REWARD_PUNISMENT";
    public static final double DAYS_IN_A_MONTH=30.4375;//365.25 hari /12 bulan  
    
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
    
 public static Vector searchSpecialRewardPunisment(SrcRewardPunishment srcRewardPunishment){
    return searchSpecialRewardPunisment(srcRewardPunishment, 0, 0);
 }
    //public static Vector searchSpecialEmployee(SrcSpecialEmployee srcspecialemployee, int start, int recordToGet){
 public static Vector searchSpecialRewardPunisment(SrcRewardPunishment srcRewardPunishment,int limit,int recordToget) {
        //public static Vector searchSpecialEmployee(Vector vparam) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);

        
        try {
            String sql = " SELECT "
                    
                    +" RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_DETAIL_ID]
                    +",E."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    +",E."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    +",RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_KOEFISIEN_POSITION]
                    +",RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_WORKING_DAYS]
                    +",RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_TOTAL]
                    +",RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_BEBAN]
                    +",RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_TUNAI]
                    +",RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_LAMA_MASA_CICILAN]
                    +",RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_STATUS_OPNAME]
                    +",RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_ENTRI_OPNAME_SALES_ID]
                    +",RPD."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_REWARD_PUNISMENT_MAIN_ID]
                    +",RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_ADJUSMENT]
                    +",RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_STATUS_OPNAME]
                    ;
                    
                 sql = sql + " FROM " 
                    + PstRewardAndPunishmentDetail.TBL_HR_REWARD_PUNISMENT_DETAIL +" AS RPD"
                    + " INNER JOIN  " + PstEmployee.TBL_HR_EMPLOYEE + " AS E  ON (E."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "  = RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_EMPLOYEE_ID]+")" 
                    + " INNER JOIN  " + PstRewardAndPunishmentMain.TBL_HR_REWARD_PUNISMENT_MAIN + " AS RPM  ON (RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_REWARD_PUNISMENT_MAIN_ID] + "  = RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_MAIN_ID]+")"; 

                 sql +=   " WHERE (1=1)";
              
            String whereClause = "";
      

            if ((srcRewardPunishment.getFullNameEmp() != null) && (srcRewardPunishment.getFullNameEmp().length() > 0)) {
                Vector vectName = logicParser(srcRewardPunishment.getFullNameEmp());
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " AND (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " E." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") ";
                }
            }
            
            if ((srcRewardPunishment.getEmpnumber()  != null) && (srcRewardPunishment.getEmpnumber().length() > 0)) {
                Vector vectNum = logicParser(srcRewardPunishment.getEmpnumber());
                if (vectNum != null && vectNum.size() > 0) {
                    whereClause = whereClause + " AND (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " E." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") ";
                }
            }
            
            if ((srcRewardPunishment.getRewardPunismentMainId()  != null) && (srcRewardPunishment.getRewardPunismentMainId().length() > 0)) {
                Vector vectMainId = logicParser(srcRewardPunishment.getRewardPunismentMainId());
                if (vectMainId != null && vectMainId.size() > 0) {
                    whereClause = whereClause + " AND (";
                    for (int i = 0; i < vectMainId.size(); i++) {
                        String str = (String) vectMainId.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " RPD." + PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_MAIN_ID]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") ";
                }
            }
            
            
            if (srcRewardPunishment.getArrDivision(0)!=null) {
                String[] divisionId = srcRewardPunishment.getArrDivision(0);
                    if (! (divisionId!=null && (divisionId[0].equals("0")))) {
                    whereClause += " AND (";
                    for (int i=0; i<divisionId.length; i++) {
                        whereClause = whereClause + " E."+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+
                                    " = "+ divisionId[i] + " OR ";
                        if (i==(divisionId.length-1)) {
                            whereClause = whereClause.substring(0, whereClause.length()-4);
                        }
                    }
                    whereClause += " )";
                }
               
            }
            
            // parameter : DEPARTMENT = 0
          if(srcRewardPunishment.getArrDepartmentId(0)!=null){
              //if((String[]) vparam.get(1)!=null){
            String[] departmentId = srcRewardPunishment.getArrDepartmentId(0);
            //String[] departmentId = (String[]) vparam.get(1);
            if (departmentId!=null && ! ( (departmentId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i=0; i<departmentId.length; i++) {
                    whereClause = whereClause + " E."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                                " = "+ departmentId[i] + " OR ";
                    if (i==(departmentId.length-1)) {
                        whereClause = whereClause.substring(0, whereClause.length()-4);
                    }
                }
                whereClause += " )";
            }
          }
          
          
         // parameter : LOCATION = 2
            String[] locationId = srcRewardPunishment.getArrLocationId(0);
            //String[] locationId = (String[]) vparam.get(2);
            if (locationId!=null && !( (locationId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i=0; i<locationId.length; i++) {
                    whereClause = whereClause + " RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_LOCATION_ID]+
                                " = "+ locationId[i] + " OR ";
                    if (i==(locationId.length-1)) {
                        whereClause = whereClause.substring(0, whereClause.length()-4);
                    }
                }
                whereClause += " )";
            }
           
            String[] companyId = srcRewardPunishment.getArrCompany(0);
            //String[] positionId = (String[]) vparam.get(2);
            if (companyId!=null && !( (companyId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i=0; i<companyId.length; i++) {
                    whereClause = whereClause + " E."+PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+
                                " = "+ companyId[i] + " OR ";
                    if (i==(companyId.length-1)) {
                        whereClause = whereClause.substring(0, whereClause.length()-4);
                    }
                }
                whereClause += " )";
            }
            
            // parameter : SECTION = 3
            String[] sectionId = srcRewardPunishment.getArrSectionId(0);
            //String[] sectionId = (String[]) vparam.get(3);
            if (sectionId!=null && !( (sectionId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i=0; i<sectionId.length; i++) {
                    whereClause = whereClause + " E."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                                " = "+ sectionId[i] + " OR ";
                    if (i==(sectionId.length-1)) {
                        whereClause = whereClause.substring(0, whereClause.length()-4);
                    }
                }
                whereClause += " )";
            }
            
            // parameter : JENISSO
            String[] jenissoId = srcRewardPunishment.getArrJenisSo(0);
            //String[] sectionId = (String[]) vparam.get(3);
            if (jenissoId!=null && !( (jenissoId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i=0; i<jenissoId.length; i++) {
                    whereClause = whereClause + " RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_JENIS_SO_ID]+
                                " = "+ jenissoId[i] + " OR ";
                    if (i==(jenissoId.length-1)) {
                        whereClause = whereClause.substring(0, whereClause.length()-4);
                    }
                }
                whereClause += " )";
            }
            
            int radioperiode = srcRewardPunishment.getRadioperiode();//Integer.parseInt(vparam.get(12).toString());
            if (radioperiode > 0) {
                java.util.Date periodefrom=null;
                java.util.Date periodeto = null;
                if(srcRewardPunishment.getDtPeriodFrom(0)!=null){//if (vparam.get(13) != null) {
                    periodefrom = srcRewardPunishment.getDtPeriodFrom(0);//(java.util.Date) vparam.get(13);
                }
                if (srcRewardPunishment.getDtPeriodTo(0) != null) {//if (vparam.get(14) != null) {
                    periodeto = srcRewardPunishment.getDtPeriodTo(0);//(java.util.Date) vparam.get(14);
                }
                if((periodefrom != null) && (periodeto != null)){
                    
//                    sql = sql + " AND (EO."+ PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " BETWEEN \"" + Formater.formatDate(dtperiodfrom, "yyyy-MM-dd") + " \" AND \"" + Formater.formatDate(dtperiodto, "yyyy-MM-dd") + " \" ";
//                  sql = sql + " OR EO."+ PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " BETWEEN \"" + Formater.formatDate(dtperiodfrom, "yyyy-MM-dd") + " \" AND \"" + Formater.formatDate(dtperiodto, "yyyy-MM-dd") + " \" )";
//            
                    whereClause = whereClause +" AND (RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_START_DATE_PERIOD]+ " = '"+
                                  Formater.formatDate((java.util.Date) periodefrom, "yyyy-MM-dd")+ "' AND RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_END_DATE_PERIOD]+ " = '"+
                                  Formater.formatDate((java.util.Date) periodeto, "yyyy-MM-dd") + "' )";
                }else if((periodefrom != null) && (periodeto == null)){
                        whereClause = whereClause +" AND (RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_START_DATE_PERIOD]+ " = '"+
                                  Formater.formatDate((java.util.Date) periodefrom, "yyyy-MM-dd")+ "' )";
                }else if((periodefrom == null) && (periodeto != null)){
                        whereClause = whereClause +" AND (RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_END_DATE_PERIOD]+ " = '"+
                                  Formater.formatDate((java.util.Date) periodeto, "yyyy-MM-dd")+ "' )";
                }
            }
            
            
            if (srcRewardPunishment.getStatusOpname()!=0 ) {
                whereClause += " AND (";
                if (srcRewardPunishment.getStatusOpname()==1){
                    whereClause = whereClause + " RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_STATUS_OPNAME]+
                                " = 'REWARD' ";
                } else {
                    whereClause = whereClause + " RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_STATUS_OPNAME]+
                                " = 'PUNISMENT' ";
                }
                whereClause += " )";
            }
            
             if (whereClause.length() > 0) {
                sql = sql + whereClause;            
            }
            
            
//           if(srcRewardPunishment.getSort(0)!=null){
//               //if(vparam.get(23)!=null){
//           int sortby = Integer.parseInt(srcRewardPunishment.getSort(0));
//            switch(sortby){
//                case SrcRewardPunishment.ORDER_EMPLOYEE_NAME :
//                    sql = sql + " ORDER BY E."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] ;
//                    break;
//                case SrcRewardPunishment.ORDER_BEBAN:
//                    sql = sql + " ORDER BY RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_BEBAN] ;
//                    break;
//                case SrcRewardPunishment.ORDER_EMPLOYEE_NUM:
//                    sql = sql + " ORDER BY E."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] ;
//                    break;
//                case SrcRewardPunishment.ORDER_TOTAL:
//                    sql = sql + " ORDER BY RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_TOTAL] ;
//                    break;
//                case SrcRewardPunishment.ORDER_TUNAI:
//                    sql = sql + " ORDER BY RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_TUNAI] ;
//                    break;
//                default:
//                    sql = sql + "";
//            }
//           }
           
           if(limit == 0 && recordToget == 0)
		sql = sql + "";
            else
		sql = sql + " LIMIT " + limit + ","+ recordToget ;
         
            //System.out.println(" SQL searchEmployee : \r" + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
          
            while(rs.next()) {
                
                SessTmpSrcRewardPunisment sessTmpSrcRewardPunisment = new SessTmpSrcRewardPunisment();
                
                sessTmpSrcRewardPunisment.setRewardpunishmentdetailid(rs.getLong(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_DETAIL_ID]));
                sessTmpSrcRewardPunisment.setFullNameEmp(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                sessTmpSrcRewardPunisment.setEmpnumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                
                //carinamaposisi
                String wherekp = " KP." + PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID] + " = " + rs.getString(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_KOEFISIEN_POSITION]);
                Vector namaposisi = PstKoefisionPosition.carinamaposisi(limit, recordToget, wherekp, sql);
                String np="";
                
                for (int npos = 0; npos < namaposisi.size(); npos++){
                 
                Position position = (Position) namaposisi.get(npos);
                if (position.getPosition()!=null && position.getPosition().length()>0){
                    np= position.getPosition();   
                    }   
                }
                
                
                sessTmpSrcRewardPunisment.setEmpposition(np);
                //mencari tipe toleransi
                String whereentriopname = rs.getLong(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_ENTRI_OPNAME_SALES_ID]) +" = EOS."+PstEntriOpnameSales.fieldNames[PstEntriOpnameSales.FLD_ENTRI_OPNAME_SALES_ID];
                int nilaitipetoleransi  = PstEntriOpnameSales.gettypetoleransi(whereentriopname);
                //mencari nilai koefisien
                String typetoleransi="";
                                                if ((nilaitipetoleransi==0)){
                                                    typetoleransi=PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_DC];
                                                } else{
                                                    typetoleransi=PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_OUTLET];
                                                }
                                                String  wherekoefisienid= PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID]+ " = " + rs.getLong(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_KOEFISIEN_POSITION]);
                                                double nKoefisien = PstKoefisionPosition.koefisiennilai( wherekoefisienid, typetoleransi);
                                                
                                                
                sessTmpSrcRewardPunisment.setKoefisien(nKoefisien);
                sessTmpSrcRewardPunisment.setHarikerja(rs.getInt(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_WORKING_DAYS]));
                sessTmpSrcRewardPunisment.setTotal(rs.getDouble(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_TOTAL]));
                sessTmpSrcRewardPunisment.setBeban(rs.getDouble(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_BEBAN]));
                sessTmpSrcRewardPunisment.setTunai(rs.getDouble(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_TUNAI]));
                sessTmpSrcRewardPunisment.setMasacicilan(rs.getInt(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_LAMA_MASA_CICILAN]));
                sessTmpSrcRewardPunisment.setPotongangaji(rs.getDouble(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_BEBAN]));
                sessTmpSrcRewardPunisment.setRewardpunishmentMainId(rs.getLong(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_MAIN_ID]));
                sessTmpSrcRewardPunisment.setAdjusment(rs.getInt(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_ADJUSMENT]));
                sessTmpSrcRewardPunisment.setStatuOpname(rs.getString(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_STATUS_OPNAME]));
                
                result.add(sessTmpSrcRewardPunisment);
                  
            }
            return result;
        } 
        catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }
 
 
 public static int countsearchSpecialRewardPunisment(SrcRewardPunishment srcRewardPunishment,int limit,int recordToget) {
        //public static Vector searchSpecialEmployee(Vector vparam) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1,1);
        int count=0;
        
        try {
            String sql = " SELECT COUNT"
                    
                    +"(RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_DETAIL_ID]+")";
                    
                 sql = sql + " FROM " 
                    + PstRewardAndPunishmentDetail.TBL_HR_REWARD_PUNISMENT_DETAIL +" AS RPD"
                    + " INNER JOIN  " + PstEmployee.TBL_HR_EMPLOYEE + " AS E  ON (E."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "  = RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_EMPLOYEE_ID]+")" 
                    + " INNER JOIN  " + PstRewardAndPunishmentMain.TBL_HR_REWARD_PUNISMENT_MAIN + " AS RPM  ON (RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_REWARD_PUNISMENT_MAIN_ID] + "  = RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_MAIN_ID]+")"; 

                 sql +=   " WHERE (1=1)";
              
            String whereClause = "";
      
            if ((srcRewardPunishment.getFullNameEmp() != null) && (srcRewardPunishment.getFullNameEmp().length() > 0)) {
                Vector vectName = logicParser(srcRewardPunishment.getFullNameEmp());
                if (vectName != null && vectName.size() > 0) {
                    whereClause = whereClause + " AND (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " E." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") ";
                }
            }
            
            if ((srcRewardPunishment.getEmpnumber()  != null) && (srcRewardPunishment.getEmpnumber().length() > 0)) {
                Vector vectNum = logicParser(srcRewardPunishment.getEmpnumber());
                if (vectNum != null && vectNum.size() > 0) {
                    whereClause = whereClause + " AND (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            whereClause = whereClause + " E." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            whereClause = whereClause + str.trim();
                        }
                    }
                    whereClause = whereClause + ") ";
                }
            }
            
            if (srcRewardPunishment.getArrDivision(0)!=null) {
                String[] divisionId = srcRewardPunishment.getArrDivision(0);
                    if (! (divisionId!=null && (divisionId[0].equals("0")))) {
                    whereClause += " AND (";
                    for (int i=0; i<divisionId.length; i++) {
                        whereClause = whereClause + " E."+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+
                                    " = "+ divisionId[i] + " OR ";
                        if (i==(divisionId.length-1)) {
                            whereClause = whereClause.substring(0, whereClause.length()-4);
                        }
                    }
                    whereClause += " )";
                }
               
            }
            
            // parameter : DEPARTMENT = 0
          if(srcRewardPunishment.getArrDepartmentId(0)!=null){
              //if((String[]) vparam.get(1)!=null){
            String[] departmentId = srcRewardPunishment.getArrDepartmentId(0);
            //String[] departmentId = (String[]) vparam.get(1);
            if (departmentId!=null && ! ( (departmentId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i=0; i<departmentId.length; i++) {
                    whereClause = whereClause + " E."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                                " = "+ departmentId[i] + " OR ";
                    if (i==(departmentId.length-1)) {
                        whereClause = whereClause.substring(0, whereClause.length()-4);
                    }
                }
                whereClause += " )";
            }
          }
          
          
          // parameter : LOCATION = 2
            String[] locationId = srcRewardPunishment.getArrLocationId(0);
            //String[] locationId = (String[]) vparam.get(2);
            if (locationId!=null && !( (locationId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i=0; i<locationId.length; i++) {
                    whereClause = whereClause + " RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_LOCATION_ID]+
                                " = "+ locationId[i] + " OR ";
                    if (i==(locationId.length-1)) {
                        whereClause = whereClause.substring(0, whereClause.length()-4);
                    }
                }
                whereClause += " )";
            }
            
           
            String[] companyId = srcRewardPunishment.getArrCompany(0);
            //String[] positionId = (String[]) vparam.get(2);
            if (companyId!=null && !( (companyId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i=0; i<companyId.length; i++) {
                    whereClause = whereClause + " E."+PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+
                                " = "+ companyId[i] + " OR ";
                    if (i==(companyId.length-1)) {
                        whereClause = whereClause.substring(0, whereClause.length()-4);
                    }
                }
                whereClause += " )";
            }
            
            // parameter : SECTION = 3
            String[] sectionId = srcRewardPunishment.getArrSectionId(0);
            //String[] sectionId = (String[]) vparam.get(3);
            if (sectionId!=null && !( (sectionId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i=0; i<sectionId.length; i++) {
                    whereClause = whereClause + " E."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                                " = "+ sectionId[i] + " OR ";
                    if (i==(sectionId.length-1)) {
                        whereClause = whereClause.substring(0, whereClause.length()-4);
                    }
                }
                whereClause += " )";
            }
            
            // parameter : JENISSO
            String[] jenissoId = srcRewardPunishment.getArrJenisSo(0);
            //String[] sectionId = (String[]) vparam.get(3);
            if (jenissoId!=null && !( (jenissoId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i=0; i<jenissoId.length; i++) {
                    whereClause = whereClause + " RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_JENIS_SO_ID]+
                                " = "+ jenissoId[i] + " OR ";
                    if (i==(jenissoId.length-1)) {
                        whereClause = whereClause.substring(0, whereClause.length()-4);
                    }
                }
                whereClause += " )";
            }
            
            int radioperiode = srcRewardPunishment.getRadioperiode();//Integer.parseInt(vparam.get(12).toString());
            if (radioperiode > 0) {
                java.util.Date periodefrom=null;
                java.util.Date periodeto = null;
                if(srcRewardPunishment.getDtPeriodFrom(0)!=null){//if (vparam.get(13) != null) {
                    periodefrom = srcRewardPunishment.getDtPeriodFrom(0);//(java.util.Date) vparam.get(13);
                }
                if (srcRewardPunishment.getDtPeriodTo(0) != null) {//if (vparam.get(14) != null) {
                    periodeto = srcRewardPunishment.getDtPeriodTo(0);//(java.util.Date) vparam.get(14);
                }
                if((periodefrom != null) && (periodeto != null)){
                    
//                    sql = sql + " AND (EO."+ PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " BETWEEN \"" + Formater.formatDate(dtperiodfrom, "yyyy-MM-dd") + " \" AND \"" + Formater.formatDate(dtperiodto, "yyyy-MM-dd") + " \" ";
//                  sql = sql + " OR EO."+ PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " BETWEEN \"" + Formater.formatDate(dtperiodfrom, "yyyy-MM-dd") + " \" AND \"" + Formater.formatDate(dtperiodto, "yyyy-MM-dd") + " \" )";
//            
                    whereClause = whereClause +" AND (RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_START_DATE_PERIOD]+ " = '"+
                                  Formater.formatDate((java.util.Date) periodefrom, "yyyy-MM-dd")+ "' AND RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_END_DATE_PERIOD]+ " = '"+
                                  Formater.formatDate((java.util.Date) periodeto, "yyyy-MM-dd") + "' )";
                }else if((periodefrom != null) && (periodeto == null)){
                        whereClause = whereClause +" AND (RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_START_DATE_PERIOD]+ " = '"+
                                  Formater.formatDate((java.util.Date) periodefrom, "yyyy-MM-dd")+ "' )";
                }else if((periodefrom == null) && (periodeto != null)){
                        whereClause = whereClause +" AND (RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_END_DATE_PERIOD]+ " = '"+
                                  Formater.formatDate((java.util.Date) periodeto, "yyyy-MM-dd")+ "' )";
                }
            }
            
            
            if (srcRewardPunishment.getStatusOpname()!=0 ) {
                whereClause += " AND (";
                if (srcRewardPunishment.getStatusOpname()==1){
                    whereClause = whereClause + " RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_STATUS_OPNAME]+
                                " = 'REWARD' ";
                } else {
                    whereClause = whereClause + " RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_STATUS_OPNAME]+
                                " = 'PUNISMENT' ";
                }
                whereClause += " )";
            }
            
             if (whereClause.length() > 0) {
                sql = sql + whereClause;            
            }
            
            
//           if(srcRewardPunishment.getSort(0)!=null){
//               //if(vparam.get(23)!=null){
//           int sortby = Integer.parseInt(srcRewardPunishment.getSort(0));
//            switch(sortby){
//                case SrcRewardPunishment.ORDER_EMPLOYEE_NAME :
//                    sql = sql + " ORDER BY E."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] ;
//                    break;
//                case SrcRewardPunishment.ORDER_BEBAN:
//                    sql = sql + " ORDER BY RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_BEBAN] ;
//                    break;
//                case SrcRewardPunishment.ORDER_EMPLOYEE_NUM:
//                    sql = sql + " ORDER BY E."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] ;
//                    break;
//                case SrcRewardPunishment.ORDER_TOTAL:
//                    sql = sql + " ORDER BY RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_TOTAL] ;
//                    break;
//                case SrcRewardPunishment.ORDER_TUNAI:
//                    sql = sql + " ORDER BY RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_TUNAI] ;
//                    break;
//                default:
//                    sql = sql + "";
//            }
//           }
           
           if(limit == 0 && recordToget == 0)
		sql = sql + "";
            else
		sql = sql + " LIMIT " + limit + ","+ recordToget ;
         
            //System.out.println(" SQL searchEmployee : \r" + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
          
            while(rs.next()) {
                
                count = rs.getInt(1);
            }
            return count;
        } 
        catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return count;
    }
    
}

