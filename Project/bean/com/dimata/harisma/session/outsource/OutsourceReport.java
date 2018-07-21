/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.outsource;

import com.dimata.harisma.entity.outsource.InOutReport;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author khirayinnura
 */
public class OutsourceReport {
    
    /*
     * 
     */
    public static Vector listInOutSum(long oidCompany, long oidDivision, long oidProvider, String startDate, String endDate) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DIVISION, PROVIDER, POSITION, SUM(MASUK) AS TOTAL_IN ,"+
                    " SUM(KELUAR) AS TOTAL_OUT FROM"+
                    " ( SELECT d.division_id AS DIVISION, e.provider_id AS PROVIDER, e.`POSITION_ID` AS POSITION,"+
                    " COUNT(e.employee_id) AS MASUK, 0 AS KELUAR FROM hr_employee e"+
                    " INNER JOIN  hr_division d ON d.`DIVISION_ID`=e.`DIVISION_ID`"+
                    " WHERE";
                    if(oidCompany != 0) {
                        sql = sql + " e.company_id = "+oidCompany;
                    } else {
                        sql = sql + " e.company_id > 0";
                    }
                    if(oidDivision != 0) {
                        sql = sql + " AND e.division_id = "+oidDivision;
                    } else {
                        sql = sql + " AND e.division_id > 0";
                    }
                    if(startDate != null && endDate != null){
                        sql = sql + " AND '"+startDate+"' <= e.`COMMENCING_DATE` AND e.`COMMENCING_DATE` <= '"+endDate+"'";
                    }
                    if(oidProvider != 0){
                        sql = sql + " AND e.provider_id = "+oidProvider;
                    } else {
                        sql = sql + " AND e.provider_id > 0";
                    }
                    
                    sql = sql + " GROUP BY e.`DIVISION_ID`, e.`POSITION_ID`"+
                    " UNION"+
                    " SELECT d.division_id AS DIVISION, e.provider_id AS PROVIDER, e.`POSITION_ID` AS POSITION,"+
                    " 0 AS MASUK , COUNT(e.employee_id) AS KELUAR FROM hr_employee e"+
                    " INNER JOIN  hr_division d ON d.`DIVISION_ID`=e.`DIVISION_ID`"+
                    " WHERE";
                    if(oidCompany != 0) {
                        sql = sql + " e.company_id = "+oidCompany;
                    } else {
                        sql = sql + " e.company_id > 0";
                    }
                    if(oidDivision != 0) {
                        sql = sql + " AND e.division_id = "+oidDivision;
                    } else {
                        sql = sql + " AND e.division_id > 0";
                    }
                    if(startDate != null && endDate != null){
                        sql = sql + " AND '"+startDate+"' <= e.`RESIGNED_DATE` AND e.`RESIGNED_DATE` <= '"+endDate+"' AND e.`RESIGNED` =1";
                    }
                    if(oidProvider != 0){
                        sql = sql + " AND e.provider_id = "+oidProvider;
                    } else {
                        sql = sql + " AND e.provider_id > 0";
                    }
                    sql = sql + " GROUP BY e.`DIVISION_ID`, e.`POSITION_ID` ) TBL"+
                    " GROUP BY  TBL.DIVISION,  TBL.PROVIDER, TBL.POSITION"+
                    " ORDER BY  TBL.DIVISION,  TBL.PROVIDER, TBL.POSITION";
            
           // System.out.println(":::::::::::::::::: "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                InOutReport inOutReport = new InOutReport();
                resultToObject(rs, inOutReport);
                lists.add(inOutReport);
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

    public static void resultToObject(ResultSet rs, InOutReport inOutReport) {
        try {
            inOutReport.setDivisionId(rs.getLong("DIVISION"));
            inOutReport.setProviderId(rs.getLong("PROVIDER"));
            inOutReport.setPositionId(rs.getLong("POSITION"));
            inOutReport.setIn(rs.getInt("TOTAL_IN"));
            inOutReport.setOut(rs.getInt("TOTAL_OUT"));
        } catch (Exception e) {
        }
    }
    
    // Report in out BPD
    public static int getTotalInOutAwal(long oidCompany, long oidDivision, String startDate) {
        Vector lists = new Vector();
        int val = 0;
        int sum = 0;
        
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DIVISION, PROVIDER, POSITION, SUM(MASUK) AS TOTAL_IN,"+
                    " SUM(KELUAR) AS TOTAL_OUT, SUM(MASUK)+SUM(KELUAR) AS TOTAL FROM"+
                    " ( SELECT d.division_id AS DIVISION, e.provider_id AS PROVIDER, e.`POSITION_ID` AS POSITION,"+
                    " COUNT(e.employee_id) AS MASUK, 0 AS KELUAR FROM hr_employee e"+
                    " INNER JOIN  hr_division d ON d.`DIVISION_ID`=e.`DIVISION_ID`"+
                    " WHERE"+
                    " e.company_id ="+oidCompany+" AND e.division_id = "+oidDivision+" AND '"+startDate+"' <= e.`COMMENCING_DATE`"+
                    " GROUP BY e.`DIVISION_ID`, e.`POSITION_ID`"+
                    " UNION"+
                    " SELECT d.division_id AS DIVISION, e.provider_id AS PROVIDER, e.`POSITION_ID` AS POSITION,"+
                    " 0 AS MASUK , COUNT(e.employee_id) AS KELUAR FROM hr_employee e"+
                    " INNER JOIN  hr_division d ON d.`DIVISION_ID`=e.`DIVISION_ID`"+
                    " WHERE"+
                    " e.company_id = "+oidCompany+" AND e.division_id = "+oidDivision+" AND '"+startDate+"' <= e.`RESIGNED_DATE`"+
                    " GROUP BY e.`DIVISION_ID`, e.`POSITION_ID` ) TBL"+
                    " GROUP BY  TBL.DIVISION, TBL.POSITION"+
                    " ORDER BY  TBL.DIVISION, TBL.POSITION";
                               
           // System.out.println(":::::::::::::::::: "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                val = rs.getInt("TOTAL");
                
                sum = sum+val;
            }
            rs.close();
            return sum;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return sum;
        }
    }
}
