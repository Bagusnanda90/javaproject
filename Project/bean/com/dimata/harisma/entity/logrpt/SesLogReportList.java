/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.logrpt;

import com.dimata.harisma.entity.customer.PstCustomer;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import java.util.Vector;
import com.dimata.qdep.entity.*;
import com.dimata.util.Formater;
import com.mysql.jdbc.ResultSet;

/**
 *
 * @author user 
 */
public class SesLogReportList extends DBHandler {

    public static Vector SesLogReportList_X(LogSrcReportList srcReportList, int start, int recordToGet) {

        DBResultSet dbrs = null;

        try {
            String sql = "SELECT Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_LOG_REPORT_ID] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_DUE_DATETIME] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_LOG_DESC] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_LOG_LOCATION_ID] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_LOG_NUMBER] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_PASAL_KHUSUS_ID] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_PASAL_UMUM_ID] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_PIC_USER_ID] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_REAL_FINISH_DATETIME] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_RECORD_BY_USER_ID] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_RECORD_DATE] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_REPORT_BY_USER_ID] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_REPORT_DATE] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_RPT_CATEGORY_ID] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_RPT_TYPE_ID] + ",Rpt." + PstLogReport.fieldNames[PstLogReport.FLD_STATUS] + " FROM " + PstLogReport.TBL_LOG_REPORT;
            ;


            String where = "";
            if (srcReportList.getDescription().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += " (rpt." + PstLogReport.fieldNames[PstLogReport.FLD_LOG_DESC] + " LIKE \'%" + srcReportList.getDescription() + "%\')";
            }
            if (srcReportList.getNumber().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_LOG_NUMBER] + " LIKE \'%" + srcReportList.getNumber() + "%\')";
            }
            if (srcReportList.getType().size() > 0) {

                for (int i = 0; i < srcReportList.getType().size(); i++) {

                    if (where.length() > 0) {
                        where += " AND ";
                    } else {
                        where += " WHERE ";
                    }

                    where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_RPT_TYPE_ID] + "=" + srcReportList.getType().get(i) + ")";
                }


            }
            if (srcReportList.getCategoryId().size() > 0) {

                for (int i = 0; i < srcReportList.getCategoryId().size(); i++) {

                    if (where.length() > 0) {
                        where += " AND ";
                    } else {
                        where += " WHERE ";
                    }

                    where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_RPT_CATEGORY_ID] + "=" + srcReportList.getCategoryId().get(i) + ")";
                }
            }
            if (srcReportList.getStatus().size() > 0) {

                for (int i = 0; i < srcReportList.getStatus().size(); i++) {

                    if (where.length() > 0) {
                        where += " AND ";
                    } else {
                        where += " WHERE ";
                    }

                    where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_STATUS] + "=" + srcReportList.getStatus().get(i) + ")";
                }
            }
            if (srcReportList.getPasalUmum().length() > 0) {
                if (where.length() > 0) {
                    where += " AND " ;
                } else {
                    where += " WHERE ";
                }
                where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_PASAL_UMUM_ID] + "=" + srcReportList.getPasalUmum() + ")";
            }
            if (srcReportList.getPasalKhusus().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_PASAL_KHUSUS_ID] + "=" + srcReportList.getPasalKhusus() + ")";
            }
            if (srcReportList.getAllRecordDate()!=1 &&  srcReportList.getRecordDateFrom() != null && srcReportList.getRecordDateTo() != null) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_RECORD_DATE] + " between '" +
                        Formater.formatDate(srcReportList.getRecordDateFrom(), "yyyy-MM-dd 00:00:00") + "' AND '" +
                        Formater.formatDate(srcReportList.getRecordDateTo(), "yyyy-MM-dd 23:59:59") + "')";
            }
            if (srcReportList.getAllReportDate()!=1 && srcReportList.getReportDateFrom() != null && srcReportList.getReportDateTo() != null) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_REPORT_DATE] + " between '" +
                        Formater.formatDate(srcReportList.getReportDateFrom(), "yyyy-MM-dd 00:00:00") + "' AND '" +
                        Formater.formatDate(srcReportList.getReportDateTo(), "yyyy-MM-dd 23:59:59") + "')";
            }
            if (srcReportList.getLocationId().size() > 0) {

                for (int i = 0; i < srcReportList.getLocationId().size(); i++) {

                    if (where.length() > 0) {
                        where += " AND ";
                    } else {
                        where += " WHERE ";
                    }

                    where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_LOG_LOCATION_ID] + "=" + srcReportList.getLocationId().get(i) + ")";
                }
            }
            if (srcReportList.getPIC().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_PIC_USER_ID] + " LIKE \'%" + srcReportList.getPIC() + "%\')";
            }
            if (srcReportList.getAllDueDate()!=1 && srcReportList.getDueDateFrom() != null && srcReportList.getDueDateTo() != null) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_DUE_DATETIME] + " between '" +
                        Formater.formatDate(srcReportList.getDueDateFrom(), "yyyy-MM-dd 00:00:00") + "' AND '" +
                        Formater.formatDate(srcReportList.getDueDateTo(), "yyyy-MM-dd 23:59:59") + "')";
            }
            if (srcReportList.getAllFinishDate()!=1 && srcReportList.getFinishDateFrom() != null && srcReportList.getFinishDateTo() != null) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_REAL_FINISH_DATETIME] + " between '" +
                        Formater.formatDate(srcReportList.getFinishDateFrom(), "yyyy-MM-dd 00:00:00") + "' AND '" +
                        Formater.formatDate(srcReportList.getFinishDateTo(), "yyyy-MM-dd 23:59:59") + "')";
            }
            if (where.length() > 0) {
                sql = sql + where;
            }

            if (start == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + start + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = (ResultSet) dbrs.getResultSet();

            Vector result = new Vector(1, 1);
            while (rs.next()) {
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }
    
   
    private  static String getWhere(LogSrcReportList srcReportList, int source){
        String where = "";
        
            if (srcReportList.getRead()!= 0) {
                where += " left join log_reader_list AS lrl ON rpt.LOG_REPORT_ID=lrl.LOG_REPORT_ID ";
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                //LOG_REPORT_ID NOT IN  (SELECT * FROM (SELECT LOG_REPORT_ID FROM log_reader_list WHERE USER_ID='504404197171442086') AS tab);
                 if (srcReportList.getPIC()!=null && srcReportList.getPIC().length() > 0) {
                    if(srcReportList.getRead()==2){
                        where += " (rpt." + PstLogReport.fieldNames[PstLogReport.FLD_LOG_REPORT_ID] + " NOT IN  ";
                        where += " select ("+ PstLogReport.fieldNames[PstLogReport.FLD_LOG_REPORT_ID] +") from log_reader_list where user_id='"+srcReportList.getPIC()+"') AS tab";
                    }else{
                        where += " (rpt." + PstLogReport.fieldNames[PstLogReport.FLD_LOG_REPORT_ID] + " IN ";
                        where += " select ("+ PstLogReport.fieldNames[PstLogReport.FLD_LOG_REPORT_ID] +") from log_reader_list where user_id='"+srcReportList.getPIC()+"') AS tab";
                    }
                }
            }

            if (srcReportList.getDescription().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += " (rpt." + PstLogReport.fieldNames[PstLogReport.FLD_LOG_DESC] + " LIKE \'%" + srcReportList.getDescription() + "%\')";
            }
        
            if (srcReportList.getNumber().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_LOG_NUMBER] + " ='" + srcReportList.getNumber() + "')";
            }
        
            if (srcReportList.getType().size() > 0) {

                    if (where.length() > 0) {
                        where += " AND ( ";
                    } else {
                        where += " WHERE ( ";
                    }
                    
                for (int i = 0; i < srcReportList.getType().size(); i++) {
                    where += " (rpt." + PstLogReport.fieldNames[PstLogReport.FLD_RPT_TYPE_ID] + "=" + srcReportList.getType().get(i) + ") ";
                    if( (srcReportList.getType().size()>1) && (i<(srcReportList.getType().size()-1)) ){
                        where += " OR ";
                    }
                }
                    
                    where += " ) ";

            }
            if (srcReportList.getCategoryId().size() > 0) {                
                    if (where.length() > 0) {
                        where += " AND  (";
                    } else {
                        where += " WHERE (";
                    }
                for (int i = 0; i < srcReportList.getCategoryId().size(); i++) {
                    where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_RPT_CATEGORY_ID] + "=" + srcReportList.getCategoryId().get(i) + ")";
                    if( (srcReportList.getCategoryId().size()>1) && (i<(srcReportList.getCategoryId().size()-1)) ){
                        where += " OR ";
                    }
                }
                    
                    where += " ) ";
            }
            if (srcReportList.getStatus().size() > 0) {

                    if (where.length() > 0) {
                        where += " AND (";
                    } else {
                        where += " WHERE (";
                    }
                for (int i = 0; i < srcReportList.getStatus().size(); i++) {
                    where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_STATUS] + "=" + srcReportList.getStatus().get(i) + ")";
                    if( (srcReportList.getStatus().size()>1) && (i<(srcReportList.getStatus().size()-1)) ){
                        where += " OR ";
                    }
                    
                }
                    where += " ) ";                    
            }
            if (srcReportList.getPasalUmum().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(d." + PstLogPasalUmum.fieldNames[PstLogPasalUmum.FLD_PASAL_UMUM] + " LIKE \"%" + srcReportList.getPasalUmum() + "%\")";
            }
            if (srcReportList.getPasalKhusus().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(e." + PstLogPasalKhusus.fieldNames[PstLogPasalKhusus.FLD_PASAL_KHUSUS] + " LIKE \"%" + srcReportList.getPasalKhusus() + "%\")";
            }
            if (srcReportList.getAllRecordDate()!=1 && srcReportList.getRecordDateFrom() != null && srcReportList.getRecordDateTo() != null) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_RECORD_DATE] + " between '" +
                        Formater.formatDate(srcReportList.getRecordDateFrom(), "yyyy-MM-dd 00:00:00") + "' AND '" +
                        Formater.formatDate(srcReportList.getRecordDateTo(), "yyyy-MM-dd 23:59:59") + "')";
            }
            if (srcReportList.getAllReportDate()!=1 && srcReportList.getReportDateFrom() != null && srcReportList.getReportDateTo() != null) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_REPORT_DATE] + " between '" +
                        Formater.formatDate(srcReportList.getReportDateFrom(), "yyyy-MM-dd 00:00:00") + "' AND '" +
                        Formater.formatDate(srcReportList.getReportDateTo(), "yyyy-MM-dd 23:59:59") + "')";
            }
            if (srcReportList.getLocationId().size() > 0) {

                    if (where.length() > 0) {
                        where += " AND (";
                    } else {
                        where += " WHERE (";
                    }

                for (int i = 0; i < srcReportList.getLocationId().size(); i++) {
                    where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_LOG_LOCATION_ID] + "=" + srcReportList.getLocationId().get(i) + ")";
                    if( (srcReportList.getLocationId().size()>1) && (i<(srcReportList.getLocationId().size()-1)) ){
                        where += " OR ";
                    }                    
                }
                        where += " ) ";                    
            }
            if (srcReportList.getPIC()!=null && srcReportList.getPIC().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                if(source==1){
                    where += "( (rpt." + PstLogReport.fieldNames[PstLogReport.FLD_PIC_USER_ID] + " LIKE \'%" + srcReportList.getPIC() + "%\') )";
                }else{
                    /*where += "( (rpt." + PstLogReport.fieldNames[PstLogReport.FLD_PIC_USER_ID] + " LIKE \'%" + srcReportList.getPIC() + "%\') OR (up."+
                             PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_FLW_UP_BY_USER_ID]+"="+ srcReportList.getPIC() +"))";*/
                    where += "( (rpt." + PstLogReport.fieldNames[PstLogReport.FLD_PIC_USER_ID] + " LIKE \'%" + srcReportList.getPIC() + "%\') )";
                }
            }

            if (srcReportList.getFollowUpBy()!=null && srcReportList.getFollowUpBy().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                 where += "( (up." + PstLogFollowUp.fieldNames[PstLogFollowUp.FLD_FLW_UP_BY_USER_ID] + " LIKE \'%" + srcReportList.getFollowUpBy() + "%\') )";
            }

            if (srcReportList.getReportedBy()!=null && srcReportList.getReportedBy().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_REPORT_BY_USER_ID] + " LIKE \'%" + srcReportList.getReportedBy()
                        + "%\')";
            }
            if (srcReportList.getRecordedBy()!=null && srcReportList.getRecordedBy().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_RECORD_BY_USER_ID] + " LIKE \'%" + srcReportList.getRecordedBy()
                        + "%\')";
            }

            if (srcReportList.getAllDueDate()!=1 && srcReportList.getDueDateTo() != null) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                if(srcReportList.getRecordDateFrom() == null){
                     where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_DUE_DATETIME] + " <= '"+
                        Formater.formatDate(srcReportList.getDueDateTo(), "yyyy-MM-dd") + "')";
                }else{
                     where += "(rpt." + PstLogReport.fieldNames[PstLogReport.FLD_DUE_DATETIME] + " between '" +
                        Formater.formatDate(srcReportList.getDueDateFrom(), "yyyy-MM-dd 00:00:00") + "' AND '" +
                        Formater.formatDate(srcReportList.getDueDateTo(), "yyyy-MM-dd 23:59:59") + "')";
                }
               
            }
            if (srcReportList.getAllFinishDate()!=1 && srcReportList.getFinishDateFrom() != null && srcReportList.getFinishDateTo() != null) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "( (rpt." + PstLogReport.fieldNames[PstLogReport.FLD_REAL_FINISH_DATETIME] + " between '" +
                        Formater.formatDate(srcReportList.getFinishDateFrom(), "yyyy-MM-dd 00:00:00") + "' AND '" +
                        Formater.formatDate(srcReportList.getFinishDateTo(), "yyyy-MM-dd 23:59:59") +  "') AND (rpt."+
                        PstLogReport.fieldNames[PstLogReport.FLD_STATUS] +"="+LogReport.RPT_STATUS_FINISHED+")) ";
            }
        return where;
    }

    public static Vector SesLogReportList(LogSrcReportList srcReportList, int start, int recordToGet, int source) {

        DBResultSet dbrs = null;
        if(srcReportList==null){
            return new Vector();
        }
        try {
            String where="";
            String sql = "SELECT DISTINCT(rpt.log_report_id), rpt.*, t.TYPE_NAME, c.CATEGORY_NAME, d.PASAL_UMUM, e.PASAL_KHUSUS, f.LOC_NAME, cst.CUSTOMER_NAME FROM "+ PstLogReport.TBL_LOG_REPORT + " as rpt "
                         + " left join log_report_type t on rpt.RPT_TYPE_ID=t.RPT_TYPE_ID " 
                         + " left Join log_category c ON rpt.RPT_CATEGORY_ID=c.RPT_CATEGORY_ID " 
                         + " left Join log_pasal_umum d ON rpt.PASAL_UMUM_ID=d.PASAL_UMUM_ID "
                         + " left Join log_pasal_khusus e ON rpt.PASAL_KHUSUS_ID=e.PASAL_KHUSUS_ID "
                         + " left join log_location f on rpt.LOG_LOCATION_ID=f.LOG_LOCATION_ID "
                         + " left join log_follow_up up on rpt.log_report_id=up.log_report_id "
                         + " left join drs_customer cst on rpt.CUSTOMER_ID=cst.CUSTOMER_ID";

            if(source==2){
                sql = sql + " left join log_reader_list AS lrl ON rpt.LOG_REPORT_ID=lrl.LOG_REPORT_ID "+
                            " where ((rpt.STATUS=0 OR rpt.STATUS=1 OR rpt.STATUS=2))  AND ";

                        if(!srcReportList.getFollowUpBy().equals("")){
                            sql = sql + "PIC_USER_ID='"+srcReportList.getFollowUpBy()+"' AND ";
                        }
                
                sql =sql+   " rpt.LOG_REPORT_ID NOT IN "+
                            "(select * from (select LOG_REPORT_ID FROM log_reader_list WHERE USER_ID='"+srcReportList.getPIC()+"') AS tab)";
            
            }else if(source==3){
                sql = sql + " inner join log_notification AS lnt ON rpt.LOG_REPORT_ID = lnt.REPORT_ID "+
                            " where lnt.USER_ID ='"+srcReportList.getFollowUpBy()+"' and lnt.STATUS_NOTIFICATION='0'";
            }else if(source==4){
                sql = sql + " inner join log_notification AS lnt ON rpt.LOG_REPORT_ID = lnt.REPORT_ID "+
                            " where lnt.USER_ID ='"+srcReportList.getFollowUpBy()+"'";
            }else if(source==5){
                sql = sql + " inner join log_subscribe AS lnt ON rpt.LOG_REPORT_ID = lnt.REPORT_ID "+
                            " where lnt.USER_ID ='"+srcReportList.getFollowUpBy()+"'";
            } else {
                where = getWhere(srcReportList,source);
            }

            if (where!=null && where.length() > 0) {
                sql = sql + where;
            }

            if (srcReportList.getOrderBy()!=null && srcReportList.getOrderBy().length() > 0) {
                sql = sql + " ORDER BY "+ srcReportList.getOrderBy();
            }

            if (start == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + start + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = (ResultSet) dbrs.getResultSet();

            Vector result = new Vector(1, 1);
            while (rs.next()) {
                LogReportListItem logReport = new LogReportListItem();
                logReport.setOID(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_LOG_REPORT_ID]));
                logReport.setLogNumber(rs.getString(PstLogReport.fieldNames[PstLogReport.FLD_LOG_NUMBER]));
                logReport.setLogDesc(rs.getString(PstLogReport.fieldNames[PstLogReport.FLD_LOG_DESC]));
                logReport.setReportDate(rs.getDate(PstLogReport.fieldNames[PstLogReport.FLD_REPORT_DATE]));
                logReport.setRecordDate(rs.getDate(PstLogReport.fieldNames[PstLogReport.FLD_RECORD_DATE]));
                logReport.setStatus(rs.getInt(PstLogReport.fieldNames[PstLogReport.FLD_STATUS]));
                logReport.setRptTypeId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_RPT_TYPE_ID]));
                logReport.setRptCategoryId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_RPT_CATEGORY_ID]));
                logReport.setPasalUmumId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_PASAL_UMUM_ID]));
                logReport.setPasalKhususId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_PASAL_KHUSUS_ID]));
                logReport.setReportByUserId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_REPORT_BY_USER_ID]));
                logReport.setRecordByUserId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_RECORD_BY_USER_ID]));
                logReport.setPicUserId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_PIC_USER_ID]));
                logReport.setLogLocationId(rs.getLong(PstLogReport.fieldNames[PstLogReport.FLD_LOG_LOCATION_ID]));
                logReport.setDueDateTime(rs.getDate(PstLogReport.fieldNames[PstLogReport.FLD_DUE_DATETIME]));
                logReport.setRealFinishDateTime(rs.getDate(PstLogReport.fieldNames[PstLogReport.FLD_REAL_FINISH_DATETIME]));
                logReport.setLogTypeName(rs.getString(PstLogReportType.fieldNames[PstLogReportType.FLD_TYPE_NAME]));
                logReport.setLogCategoryName(rs.getString(PstLogCategory.fieldNames[PstLogCategory.FLD_CATEGORY_NAME]));
                logReport.setLogPasalUmum(rs.getString(PstLogPasalUmum.fieldNames[PstLogPasalUmum.FLD_PASAL_UMUM]));
                logReport.setLogPasalKhusus(rs.getString(PstLogPasalKhusus.fieldNames[PstLogPasalKhusus.FLD_PASAL_KHUSUS]));
                logReport.setLogLocation(rs.getString(PstLogLocation.fieldNames[PstLogLocation.FLD_LOC_NAME]));
                logReport.setLogCustomer(rs.getString(PstCustomer.fieldNames[PstCustomer.FLD_CUSTOMER_NAME]));
                logReport.setPriority(rs.getInt(PstLogReport.fieldNames[PstLogReport.FLD_LOG_PRIORITY]));
                result.add(logReport);

            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);
    }
    
public static int SesLogReportListCount(LogSrcReportList srcReportList, int source) {

        DBResultSet dbrs = null;
        int rslt=0;
        try {
            String where="";
            String sql = "SELECT COUNT(DISTINCT(rpt.LOG_REPORT_ID)) AS JML FROM "+ PstLogReport.TBL_LOG_REPORT + " as rpt "
                         + " left join log_report_type t on rpt.RPT_TYPE_ID=t.RPT_TYPE_ID " 
                         + " left Join log_category c ON rpt.RPT_CATEGORY_ID=c.RPT_CATEGORY_ID " 
                         + " left Join log_pasal_umum d ON rpt.PASAL_UMUM_ID=d.PASAL_UMUM_ID "
                         + " left Join log_pasal_khusus e ON rpt.PASAL_KHUSUS_ID=e.PASAL_KHUSUS_ID "
                         + " left join log_location f on rpt.LOG_LOCATION_ID=f.LOG_LOCATION_ID "
                         + " left join log_follow_up up on rpt.log_report_id=up.log_report_id ";

            if(source==2){
                sql = sql + " left join log_reader_list AS lrl ON rpt.LOG_REPORT_ID=lrl.LOG_REPORT_ID "+
                            " where ((rpt.STATUS=0 OR rpt.STATUS=1 OR rpt.STATUS=2))  AND ";

                         if(!srcReportList.getFollowUpBy().equals("")){
                            sql = sql + "PIC_USER_ID='"+srcReportList.getFollowUpBy()+"' AND ";
                        }

                sql =sql+   " rpt.LOG_REPORT_ID NOT IN "+
                            "(select LOG_REPORT_ID FROM log_reader_list WHERE USER_ID='"+srcReportList.getPIC()+"')";
            }else if(source==3){
                sql = sql + " inner join log_notification AS lnt ON rpt.LOG_REPORT_ID = lnt.REPORT_ID "+
                            " where lnt.USER_ID ='"+srcReportList.getFollowUpBy()+"' and lnt.STATUS_NOTIFICATION='0'";
            }else if(source==4){
                sql = sql + " inner join log_notification AS lnt ON rpt.LOG_REPORT_ID = lnt.REPORT_ID "+
                            " where lnt.USER_ID ='"+srcReportList.getFollowUpBy()+"'";
            }else if(source==5){
                sql = sql + " inner join log_subscribe AS lnt ON rpt.LOG_REPORT_ID = lnt.REPORT_ID "+
                            " where lnt.USER_ID ='"+srcReportList.getFollowUpBy()+"'";
            } else {
                where = getWhere(srcReportList,source);
            }
            //String where = getWhere(srcReportList,source);
            
            if (where.length() > 0) {
                sql = sql + where;
            }

            dbrs = DBHandler.execQueryResult(sql);
            System.out.println(sql);
            ResultSet rs = (ResultSet) dbrs.getResultSet();

            while (rs.next()) {                
                rslt=rs.getInt("JML");
            }
            rs.close();
            
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return rslt;
        }        
 }

public static int SesLogReportListCountHome(LogSrcReportList srcReportList, int source) {

        DBResultSet dbrs = null;
        int rslt=0;
        try {
            String sql = "SELECT COUNT(DISTINCT(rpt.LOG_REPORT_ID)) AS JML FROM "+ PstLogReport.TBL_LOG_REPORT + " as rpt "
                         + " left join log_report_type t on rpt.RPT_TYPE_ID=t.RPT_TYPE_ID "
                         + " left Join log_category c ON rpt.RPT_CATEGORY_ID=c.RPT_CATEGORY_ID "
                         + " left Join log_pasal_umum d ON rpt.PASAL_UMUM_ID=d.PASAL_UMUM_ID "
                         + " left Join log_pasal_khusus e ON rpt.PASAL_KHUSUS_ID=e.PASAL_KHUSUS_ID "
                         + " left join log_location f on rpt.LOG_LOCATION_ID=f.LOG_LOCATION_ID "
                         + " left join log_follow_up up on rpt.log_report_id=up.log_report_id ";

            String where = getWhere(srcReportList,source);
            where = where + " AND rpt.STATUS!=3 ";

            if (where.length() > 0) {
                sql = sql + where;
            }

            dbrs = DBHandler.execQueryResult(sql);
            System.out.println(sql);
            ResultSet rs = (ResultSet) dbrs.getResultSet();

            while (rs.next()) {
                rslt=rs.getInt("JML");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return rslt;
        }
    }


public static int countNotif(long oid, int type) {

        DBResultSet dbrs = null;
        int rslt=0;
        try {
            /**
             * SELECT COUNT(NOTIFICATION_ID) FROM log_notification WHERE user_id=504404536025508848;
             */
            String sql = "SELECT COUNT(DISTINCT("+PstLogNotification.fieldNames[PstLogNotification.FLD_REPORT_ID]+")) AS JML FROM "+ PstLogNotification.TBL_LOG_NOTIFICATION +
                         " WHERE "+PstLogNotification.fieldNames[PstLogNotification.FLD_USER_ID]+"='"+oid+"'";
            
            if(type==0){
                sql=sql+ " AND "+PstLogNotification.fieldNames[PstLogNotification.FLD_STATUS_NOTIFICATION]+"='0'";
            }

            dbrs = DBHandler.execQueryResult(sql);
            System.out.println(sql);
            ResultSet rs = (ResultSet) dbrs.getResultSet();

            while (rs.next()) {
                rslt=rs.getInt("JML");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return rslt;
        }
    }


public static int countSubscribe(long oid, int type) {

        DBResultSet dbrs = null;
        int rslt=0;
        try {
            /**
             * SELECT COUNT(NOTIFICATION_ID) FROM log_notification WHERE user_id=504404536025508848;
             */
            String sql = "SELECT COUNT(DISTINCT("+PstLogSubscibe.fieldNames[PstLogSubscibe.FLD_REPORT_ID]+")) AS JML FROM "+ PstLogSubscibe.TBL_LOG_SUBSCRIBE +
                         " WHERE "+PstLogSubscibe.fieldNames[PstLogSubscibe.FLD_USER_ID]+"='"+oid+"'";

            if(type==0){
                sql=sql+ " AND "+PstLogNotification.fieldNames[PstLogNotification.FLD_STATUS_NOTIFICATION]+"='0'";
            }

            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println(sql);
            ResultSet rs = (ResultSet) dbrs.getResultSet();

            while (rs.next()) {
                rslt=rs.getInt("JML");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return rslt;
        }
    }

/*
 * mencari jumlah report yang belum di baca
 */

public static int SesLogReadCount(long oidUser, int type) {
        DBResultSet dbrs = null;
        int rslt=0;
        try {
            /*
             * SELECT * FROM log_report WHERE log_report_id NOT IN
               (SELECT * FROM (SELECT log_report_id FROM log_reader_list WHERE user_id='504404197171442086') AS tab);
             */
            String sql = "SELECT COUNT(LOG_REPORT_ID) AS JML FROM "+PstLogReport.TBL_LOG_REPORT+" WHERE STATUS!=3 AND ";
                if(type==1){
                    sql = sql + "PIC_USER_ID='"+oidUser+"' AND ";
                }
                sql= sql+" LOG_REPORT_ID NOT IN "+
                         " (SELECT LOG_REPORT_ID FROM log_reader_list WHERE USER_ID='"+oidUser+"')";

            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println(sql);
            ResultSet rs = (ResultSet) dbrs.getResultSet();

            while (rs.next()) {
                rslt=rs.getInt("JML");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return rslt;
        }
 }

public static int SesStatusReportCount(long oidUser, int status) {
        DBResultSet dbrs = null;
        int rslt=0;
        try {
            /*
             * SELECT * FROM log_report WHERE log_report_id NOT IN
               (SELECT * FROM (SELECT log_report_id FROM log_reader_list WHERE user_id='504404197171442086') AS tab);
             */
            String sql = "SELECT COUNT(LOG_REPORT_ID) AS JML FROM "+PstLogReport.TBL_LOG_REPORT+" WHERE STATUS='"+status+"' AND PIC_USER_ID='"+oidUser+"'";

            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println(sql);
            ResultSet rs = (ResultSet) dbrs.getResultSet();

            while (rs.next()) {
                rslt=rs.getInt("JML");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return rslt;
        }
 }


public static int SesStatusSearchReportCount(long oidUser, int status,LogSrcReportList srcReportList) {
        DBResultSet dbrs = null;
        int rslt=0;
        try {
            /*
             * SELECT * FROM log_report WHERE log_report_id NOT IN
               (SELECT * FROM (SELECT log_report_id FROM log_reader_list WHERE user_id='504404197171442086') AS tab);
             */
            String sql = "SELECT COUNT(LG.LOG_REPORT_ID) AS JML FROM "+PstLogReport.TBL_LOG_REPORT+" AS LG WHERE LG.STATUS='"+status+"' AND LG.PIC_USER_ID='"+oidUser+"'";
            String where = getWhere(srcReportList);
            if (where!=null && where.length() > 0) {
                sql = sql +" AND "+ where;
            }

            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println(sql);
            ResultSet rs = (ResultSet) dbrs.getResultSet();

            while (rs.next()) {
                rslt=rs.getInt("JML");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return rslt;
        }
 }

public static int SesSearchStatusReportCount(long oidUser, int status,LogSrcReportList srcReportList) {
        DBResultSet dbrs = null;
        int rslt=0;
        try {
            /*
             * SELECT * FROM log_report WHERE log_report_id NOT IN
               (SELECT * FROM (SELECT log_report_id FROM log_reader_list WHERE user_id='504404197171442086') AS tab);
             */
            String sql = "SELECT COUNT(LG.LOG_REPORT_ID) AS JML FROM "+PstLogReport.TBL_LOG_REPORT+" AS LG WHERE LG.STATUS='"+status+"' AND LG.PIC_USER_ID='"+oidUser+"'";
            String where = getWhere(srcReportList);
            if (where!=null && where.length() > 0) {
                sql = sql +" AND "+ where;
            }
            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println(sql);
            ResultSet rs = (ResultSet) dbrs.getResultSet();

            while (rs.next()) {
                rslt=rs.getInt("JML");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return rslt;
        }
 }


public static int SesStatusNotif(long oidUser) {
        DBResultSet dbrs = null;
        int rslt=0;
        try {
            /*
             * SELECT * FROM log_report WHERE log_report_id NOT IN
               (SELECT * FROM (SELECT log_report_id FROM log_reader_list WHERE user_id='504404197171442086') AS tab);
             */
            String sql = "SELECT COUNT(LOG_REPORT_ID) AS JML FROM "+PstLogReport.TBL_LOG_REPORT+" WHERE STATUS_FOLLOW_UP='1' AND PIC_USER_ID='"+oidUser+"'";
            
            dbrs = DBHandler.execQueryResult(sql);
            System.out.println(sql);
            ResultSet rs = (ResultSet) dbrs.getResultSet();

            while (rs.next()) {
                rslt=rs.getInt("JML");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return rslt;
        }
 }

  
public static int SesLogReadCount(long oidUser, int type, LogSrcReportList srcReportList) {
        DBResultSet dbrs = null;
        int rslt=0;
        try {
            String sql = "SELECT COUNT(LOG_REPORT_ID) AS JML FROM "+PstLogReport.TBL_LOG_REPORT+" as LG WHERE LG.STATUS!=3 AND ";
                
                if(type==1){
                    sql = sql + "LG.PIC_USER_ID='"+oidUser+"' AND ";
                }
                String where = getWhere(srcReportList);
                if (where!=null && where.length() > 0) {
                    sql = sql + where+" AND ";
                }
                sql= sql+" LG.LOG_REPORT_ID NOT IN "+
                         " (SELECT LOG_REPORT_ID FROM log_reader_list WHERE USER_ID='"+oidUser+"')";

            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println(sql);
            ResultSet rs = (ResultSet) dbrs.getResultSet();

            while (rs.next()) {
                rslt=rs.getInt("JML");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return rslt;
        }
 }

 private  static String getWhere(LogSrcReportList srcReportList){
            String where = "";
            if (srcReportList.getAllReportDate()!=1 && srcReportList.getReportDateFrom() != null && srcReportList.getReportDateTo() != null) {
                where += "(LG." + PstLogReport.fieldNames[PstLogReport.FLD_REPORT_DATE] + " between '" +
                        Formater.formatDate(srcReportList.getReportDateFrom(), "yyyy-MM-dd 00:00:00") + "' AND '" +
                        Formater.formatDate(srcReportList.getReportDateTo(), "yyyy-MM-dd 23:59:59") + "')";
            }
        return where;
    }

}
