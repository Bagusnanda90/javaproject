/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.jfreechart;

import com.dimata.harisma.entity.admin.AppUser;
import com.dimata.harisma.entity.logrpt.LogSrcReportList;
import com.dimata.harisma.entity.logrpt.PstLogReport;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author dimata005
 */
public class SessJFreeChart {
    
    public SessJFreeChart() {
    }

    public static Vector getReportOnPIC(LogSrcReportList srcReportList)
    {
        /*
         * /*mencari berdasarkan PIC
            SELECT LU.USER_ID, LU.FULL_NAME, COUNT(LG.LOG_REPORT_ID) FROM log_report AS LG
            LEFT JOIN log_user AS LU ON LG.PIC_USER_ID=LU.USER_ID
            GROUP BY LU.USER_ID;
         */
        Vector lists = new Vector();
        DBResultSet dbrs=null;
        try {

            String sql = " SELECT LU.USER_ID, LU.FULL_NAME,LU.EMPLOYEE_ID, COUNT(LG.LOG_REPORT_ID) AS JML,LU.EMPLOYEE_ID, HD.department FROM log_report AS LG "+
                         " INNER JOIN log_user AS LU ON LG.PIC_USER_ID=LU.USER_ID "+
                         " LEFT JOIN hr_employee AS HE ON HE.EMPLOYEE_ID=LU.EMPLOYEE_ID "+
                         " LEFT JOIN hr_department AS HD ON HD.department_id=HE.DEPARTMENT_ID ";
            
            String where = getWhere(srcReportList);
            
            if (where!=null && where.length() > 0) {
                sql = sql + where;
                sql = sql + " AND LG.STATUS!=3 ";
            }else{
                sql = sql + " WHERE LG.STATUS!=3 ";
            }

            sql = sql + " GROUP BY LU.USER_ID ";

            if(srcReportList.getOrderBy().equals("1")){//total
                sql = sql + " ORDER BY COUNT(LG.LOG_REPORT_ID) DESC";

            }else if(srcReportList.getOrderBy().equals("2")){ //nama & department
                sql = sql + " ORDER BY HD.department  ASC, LU.FULL_NAME ASC";
            }else{
                sql = sql + " ORDER BY COUNT(LG.LOG_REPORT_ID) DESC";
            }
            
            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                ReportFreeChart rptFreeChart = new ReportFreeChart();
                resultToObject(rs, rptFreeChart);
                lists.add(rptFreeChart);
            }
            return lists;

       }catch(Exception e) {
            System.out.println(e);
       }
        finally{
            DBResultSet.close(dbrs);
        }

       return new Vector();
    }


     public static Vector getReportOnDueDateReport(LogSrcReportList srcReportList)
    {
        /*
         *  SELECT LU.USER_ID, LU.FULL_NAME,LU.EMPLOYEE_ID, COUNT(LG.LOG_REPORT_ID) AS JML,LU.EMPLOYEE_ID, HD.department
            FROM log_report AS LG
            INNER JOIN log_user AS LU ON LG.PIC_USER_ID=LU.USER_ID
            LEFT JOIN hr_employee AS HE ON HE.EMPLOYEE_ID=LU.EMPLOYEE_ID
            LEFT JOIN hr_department AS HD ON HD.department_id=HE.DEPARTMENT_ID
            WHERE (LG.STATUS=0 OR LG.STATUS=1)
            AND (LG.DUE_DATETIME <= '2014-01-05')
            GROUP BY LU.USER_ID
            ORDER BY COUNT(LG.LOG_REPORT_ID) DESC
         */
        Date date = new Date();
        Vector lists = new Vector();
        DBResultSet dbrs=null;
        try {

            String sql = " SELECT LU.USER_ID, LU.FULL_NAME,LU.EMPLOYEE_ID, COUNT(LG.LOG_REPORT_ID) AS JML,LU.EMPLOYEE_ID, HD.department "+
                         " FROM log_report AS LG "+
                         " INNER JOIN log_user AS LU ON LG.PIC_USER_ID=LU.USER_ID "+
                         " LEFT JOIN hr_employee AS HE ON HE.EMPLOYEE_ID=LU.EMPLOYEE_ID "+
                         " LEFT JOIN hr_department AS HD ON HD.department_id=HE.DEPARTMENT_ID "+
                         " WHERE (LG.STATUS=0 OR LG.STATUS=1) "+
                         " AND (LG.DUE_DATETIME <= '"+Formater.formatDate(srcReportList.getDueDateFrom(), "yyyy-MM-dd 00:00:00")+"')";

            sql = sql + " GROUP BY LU.USER_ID ";

            if(srcReportList.getOrderBy().equals("1")){//total
                sql = sql + " ORDER BY COUNT(LG.LOG_REPORT_ID) DESC";

            }else if(srcReportList.getOrderBy().equals("2")){ //nama & department
                sql = sql + " ORDER BY HD.department  ASC, LU.FULL_NAME ASC";
            }else{
                sql = sql + " ORDER BY COUNT(LG.LOG_REPORT_ID) DESC";
            }

            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                ReportFreeChart rptFreeChart = new ReportFreeChart();
                resultToObject(rs, rptFreeChart);
                lists.add(rptFreeChart);
            }
            return lists;

       }catch(Exception e) {
            System.out.println(e);
       }
        finally{
            DBResultSet.close(dbrs);
        }

       return new Vector();
    }


    public static Vector getReportBy(LogSrcReportList srcReportList)
    {
        /*
         * /*mencari berdasarkan PIC
            SELECT LU.USER_ID, LU.FULL_NAME, COUNT(LG.LOG_REPORT_ID) FROM log_report AS LG
            LEFT JOIN log_user AS LU ON LG.PIC_USER_ID=LU.USER_ID
            GROUP BY LU.USER_ID;
         */
        Vector lists = new Vector();
        DBResultSet dbrs=null;
        try {

            String sql = " SELECT LU.USER_ID, LU.FULL_NAME, COUNT(LG.LOG_REPORT_ID) AS JML, LU.EMPLOYEE_ID, HD.department FROM log_report AS LG "+
                         " INNER JOIN log_user AS LU ON LG.REPORT_BY_USER_ID=LU.USER_ID "+
                         " LEFT JOIN hr_employee AS HE ON HE.EMPLOYEE_ID=LU.EMPLOYEE_ID "+
                         " LEFT JOIN hr_department AS HD ON HD.department_id=HE.DEPARTMENT_ID ";

            String where = getWhere(srcReportList);

            if (where!=null && where.length() > 0) {
                sql = sql + where;
                sql = sql + " AND LG.STATUS!=3 ";
            }else{
                sql = sql + " WHERE LG.STATUS!=3 ";
            }

            sql = sql + "GROUP BY LU.USER_ID";

            if(srcReportList.getOrderBy().equals("1")){//total
                sql = sql + " ORDER BY COUNT(LG.LOG_REPORT_ID) DESC";

            }else if(srcReportList.getOrderBy().equals("2")){ //nama & department
                sql = sql + " ORDER BY HD.department  ASC, LU.FULL_NAME ASC";
            }else{
                sql = sql + " ORDER BY COUNT(LG.LOG_REPORT_ID) DESC";
            }

            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                ReportFreeChart rptFreeChart = new ReportFreeChart();
                resultToObject(rs, rptFreeChart);
                lists.add(rptFreeChart);
            }
            return lists;

       }catch(Exception e) {
            System.out.println(e);
       }
        finally{
            DBResultSet.close(dbrs);
        }

       return new Vector();
    }


    public static Vector getRecordBy(LogSrcReportList srcReportList)
    {
        /*
         * /*mencari berdasarkan PIC
            SELECT LU.USER_ID, LU.FULL_NAME, COUNT(LG.LOG_REPORT_ID) FROM log_report AS LG
            LEFT JOIN log_user AS LU ON LG.PIC_USER_ID=LU.USER_ID
            GROUP BY LU.USER_ID;
         */
        Vector lists = new Vector();
        DBResultSet dbrs=null;
        try {

            String sql = " SELECT LU.USER_ID, LU.FULL_NAME, COUNT(LG.LOG_REPORT_ID) AS JML, LU.EMPLOYEE_ID, HD.department FROM log_report AS LG "+
                         " INNER JOIN log_user AS LU ON LG.RECORD_BY_USER_ID=LU.USER_ID "+
                         " LEFT JOIN hr_employee AS HE ON HE.EMPLOYEE_ID=LU.EMPLOYEE_ID "+
                         " LEFT JOIN hr_department AS HD ON HD.department_id=HE.DEPARTMENT_ID ";

            String where = getWhere(srcReportList);

            if (where!=null && where.length() > 0) {
                sql = sql + where;
                sql = sql + " AND LG.STATUS!=3 ";
            }else{
                sql = sql + " WHERE LG.STATUS!=3 ";
            }

            sql = sql + "GROUP BY LU.USER_ID";

             if(srcReportList.getOrderBy().equals("1")){//total
                sql = sql + " ORDER BY COUNT(LG.LOG_REPORT_ID) DESC";

            }else if(srcReportList.getOrderBy().equals("2")){ //nama & department
                sql = sql + " ORDER BY HD.department  ASC, LU.FULL_NAME ASC";
            }else{
                sql = sql + " ORDER BY COUNT(LG.LOG_REPORT_ID) DESC";
            }

            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                ReportFreeChart rptFreeChart = new ReportFreeChart();
                resultToObject(rs, rptFreeChart);
                lists.add(rptFreeChart);
            }
            return lists;

       }catch(Exception e) {
            System.out.println(e);
       }
        finally{
            DBResultSet.close(dbrs);
        }

       return new Vector();
    }


    public static Vector getReadBy(LogSrcReportList srcReportList)
    {
        /*
         * /*mencari berdasarkan PIC
            SELECT LU.USER_ID, LU.FULL_NAME, COUNT(LG.LOG_REPORT_ID) FROM log_report AS LG
            LEFT JOIN log_user AS LU ON LG.PIC_USER_ID=LU.USER_ID
            GROUP BY LU.USER_ID;
         */
        Vector lists = new Vector();
        DBResultSet dbrs=null;
        try {

            String sql = " SELECT LU.USER_ID, LU.FULL_NAME, COUNT(LG.LOG_REPORT_ID) AS JML, LU.EMPLOYEE_ID, HD.department FROM log_report AS LG "+
                         " INNER JOIN log_reader_list AS LRL ON LRL.LOG_REPORT_ID=LG.LOG_REPORT_ID " +
                         " INNER JOIN log_user AS LU ON LRL.USER_ID=LU.USER_ID "+
                         " LEFT JOIN hr_employee AS HE ON HE.EMPLOYEE_ID=LU.EMPLOYEE_ID "+
                         " LEFT JOIN hr_department AS HD ON HD.department_id=HE.DEPARTMENT_ID ";

            String where = getWhere(srcReportList);

            if (where!=null && where.length() > 0) {
                sql = sql + where;
                sql = sql + " AND LG.STATUS!=3 ";
            }else{
                sql = sql + " WHERE LG.STATUS!=3 ";
            }

            sql = sql + "GROUP BY LU.USER_ID ";

             if(srcReportList.getOrderBy().equals("1")){//total
                sql = sql + " ORDER BY COUNT(LG.LOG_REPORT_ID) DESC";

            }else if(srcReportList.getOrderBy().equals("2")){ //nama & department
                sql = sql + " ORDER BY HD.department  ASC, LU.FULL_NAME ASC";
            }else{
                sql = sql + " ORDER BY COUNT(LG.LOG_REPORT_ID) DESC";
            }

            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                ReportFreeChart rptFreeChart = new ReportFreeChart();
                resultToObject(rs, rptFreeChart);
                lists.add(rptFreeChart);
            }
            return lists;

       }catch(Exception e) {
            System.out.println(e);
       }
        finally{
            DBResultSet.close(dbrs);
        }

       return new Vector();
    }


    public static Vector getUnReadBy(LogSrcReportList srcReportList)
    {
        /*
         * /*mencari berdasarkan PIC
            SELECT LU.USER_ID, LU.FULL_NAME, COUNT(LG.LOG_REPORT_ID) FROM log_report AS LG
            LEFT JOIN log_user AS LU ON LG.PIC_USER_ID=LU.USER_ID
            GROUP BY LU.USER_ID;
         */
        Vector lists = new Vector();
        DBResultSet dbrs=null;
        try {

            String sql = " SELECT LU.USER_ID, LU.FULL_NAME, COUNT(LG.LOG_REPORT_ID) AS JML FROM log_report AS LG "+
                         " INNER JOIN log_reader_list AS LRL ON LRL.LOG_REPORT_ID=LG.LOG_REPORT_ID " +
                         " INNER JOIN log_user AS LU ON LRL.USER_ID=LU.USER_ID ";

            String where = getWhere(srcReportList);

            if (where!=null && where.length() > 0) {
                sql = sql + where;
            }

            sql = sql + "GROUP BY LU.USER_ID ORDER BY COUNT(LG.LOG_REPORT_ID) DESC";

            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                ReportFreeChart rptFreeChart = new ReportFreeChart();
                resultToObject(rs, rptFreeChart);
                lists.add(rptFreeChart);
            }
            return lists;

       }catch(Exception e) {
            System.out.println(e);
       }
        finally{
            DBResultSet.close(dbrs);
        }

       return new Vector();
    }



    public static Vector getFollowUpBy(LogSrcReportList srcReportList)
    {
        /*
         * /*mencari berdasarkan PIC
            SELECT LU.USER_ID, LU.FULL_NAME, COUNT(LG.LOG_REPORT_ID) FROM log_report AS LG
            LEFT JOIN log_user AS LU ON LG.PIC_USER_ID=LU.USER_ID
            GROUP BY LU.USER_ID;
         */
        Vector lists = new Vector();
        DBResultSet dbrs=null;
        try {

            String sql = " SELECT LU.USER_ID, LU.FULL_NAME, COUNT(LG.LOG_REPORT_ID) AS JML, LU.EMPLOYEE_ID, HD.department FROM log_report AS LG "+
                         " INNER JOIN log_follow_up AS LRL ON LRL.LOG_REPORT_ID=LG.LOG_REPORT_ID " +
                         " INNER JOIN log_user AS LU ON LRL.FLW_UP_BY_USER_ID=LU.USER_ID "+
                         " LEFT JOIN hr_employee AS HE ON HE.EMPLOYEE_ID=LU.EMPLOYEE_ID "+
                         " LEFT JOIN hr_department AS HD ON HD.department_id=HE.DEPARTMENT_ID ";

            String where = getWhere(srcReportList);

            if (where!=null && where.length() > 0) {
                sql = sql + where;
                sql = sql + " AND LG.STATUS!=3 ";
            }else{
                sql = sql + " WHERE LG.STATUS!=3 ";
            }

            sql = sql + "GROUP BY LU.USER_ID";

            if(srcReportList.getOrderBy().equals("1")){//total
                sql = sql + " ORDER BY COUNT(LG.LOG_REPORT_ID) DESC";

            }else if(srcReportList.getOrderBy().equals("2")){ //nama & department
                sql = sql + " ORDER BY HD.department  ASC, LU.FULL_NAME ASC";
            }else{
                sql = sql + " ORDER BY COUNT(LG.LOG_REPORT_ID) DESC";
            }


            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {
                ReportFreeChart rptFreeChart = new ReportFreeChart();
                resultToObject(rs, rptFreeChart);
                lists.add(rptFreeChart);
            }
            return lists;

       }catch(Exception e) {
            System.out.println(e);
       }
        finally{
            DBResultSet.close(dbrs);
        }

       return new Vector();
    }

    public static int  getCategoryBy(long userId, long categoryId, LogSrcReportList srcReportList)
    {
       /**
        *   SELECT LU.USER_ID, LU.FULL_NAME,c.CATEGORY_NAME,c.RPT_CATEGORY_ID, COUNT(LG.RPT_CATEGORY_ID)  AS JML, LU.EMPLOYEE_ID, HD.department
            FROM log_report AS LG
            LEFT JOIN log_user AS LU ON LG.REPORT_BY_USER_ID=LU.USER_ID
            LEFT JOIN hr_employee AS HE ON HE.EMPLOYEE_ID=LU.EMPLOYEE_ID
            LEFT JOIN hr_department AS HD ON HD.department_id=HE.DEPARTMENT_ID
            LEFT JOIN log_category c ON c.RPT_CATEGORY_ID =LG.RPT_CATEGORY_ID
            WHERE LG.STATUS!=3
            AND lu.user_id=''
            GROUP BY LU.USER_ID, c.RPT_CATEGORY_ID ORDER BY c.CATEGORY_NAME DESC;
        */

       
        int result=0;
        DBResultSet dbrs=null;
        try {
            String sql = " SELECT COUNT(LG.RPT_CATEGORY_ID)  AS JML "+
                                 " FROM log_report AS LG "+
                                 " LEFT JOIN log_category c ON c.RPT_CATEGORY_ID =LG.RPT_CATEGORY_ID "+
                                 " WHERE LG.STATUS!=3 "+
                                 " AND LG.REPORT_BY_USER_ID='"+userId+"'" +
                                 " AND c.RPT_CATEGORY_ID='"+categoryId+"'";
                    
                    if(srcReportList.getAllReportDate()==0){
                        sql = sql + " AND (LG." + PstLogReport.fieldNames[PstLogReport.FLD_REPORT_DATE] + " between '" +
                                    Formater.formatDate(srcReportList.getReportDateFrom(), "yyyy-MM-dd 00:00:00") + "' AND '" +
                                    Formater.formatDate(srcReportList.getReportDateTo(), "yyyy-MM-dd 23:59:59") + "')";
                    }
                    
                    dbrs=DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();
                    while(rs.next()) {
                        result=rs.getInt("JML");
                    }
            
            return result;

       }catch(Exception e) {
            System.out.println(e);
       }
        finally{
            DBResultSet.close(dbrs);
        }

       return result;
    }

    private static void resultToObject(ResultSet rs, ReportFreeChart rptFreeChart) {
        try {
            rptFreeChart.setUserId(rs.getLong("USER_ID"));
            rptFreeChart.setFullName(rs.getString("FULL_NAME"));
            rptFreeChart.setCountReport(rs.getDouble("JML"));
            rptFreeChart.setEmployeeId(rs.getLong("EMPLOYEE_ID"));
            rptFreeChart.setDepartmentName(rs.getString("department"));
        }catch(Exception e){
            System.out.println("resultToObject() " + e.toString());
        }
    }

     private static void resultToObjectCategory(ResultSet rs, ReportFreeChart rptFreeChart) {
        try {
            rptFreeChart.setUserId(rs.getLong("USER_ID"));
            rptFreeChart.setFullName(rs.getString("FULL_NAME"));
            rptFreeChart.setCountReport(rs.getDouble("JML"));
            rptFreeChart.setEmployeeId(rs.getLong("EMPLOYEE_ID"));
            rptFreeChart.setDepartmentName(rs.getString("department"));
            rptFreeChart.setCategoryId(rs.getLong("RPT_CATEGORY_ID"));
            rptFreeChart.setCategoryName(rs.getString("CATEGORY_NAME"));
            //CATEGORY_NAME,c.RPT_CATEGORY_ID
        }catch(Exception e){
            System.out.println("resultToObject() " + e.toString());
        }
    }


    private  static String getWhere(LogSrcReportList srcReportList){
            String where = "";
            if (srcReportList.getAllReportDate()!=1 && srcReportList.getReportDateFrom() != null && srcReportList.getReportDateTo() != null) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(LG." + PstLogReport.fieldNames[PstLogReport.FLD_REPORT_DATE] + " between '" +
                        Formater.formatDate(srcReportList.getReportDateFrom(), "yyyy-MM-dd 00:00:00") + "' AND '" +
                        Formater.formatDate(srcReportList.getReportDateTo(), "yyyy-MM-dd 23:59:59") + "')";
            }
        return where;
    }

}
