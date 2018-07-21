/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.masterdatadesktop.entity;

import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.db.DBResultSet;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class PstPeriodDesktop {
    
        /**
     * This method used to list periodID where selectDate between startDate and dueDate of period
     * @param selectedDate --> specify selectedDate
     */
    public static long getPeriodeIdBetween(Date selectedDate) {
        DBResultSet dbrs = null;
        long result = 0;
        String strDate = "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "\"";
        try {
            String sql = "SELECT " + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] +
                    " FROM " + PstPeriod.TBL_HR_PERIOD +
                    " WHERE " + strDate + " BETWEEN " + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] +
                    " AND " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = rs.getLong(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]);
                break;
            }
        } catch (Exception e) {
            System.out.println("Err list period : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    public static long getPeriodIdBySelectedDate(Date selectedDate) {
        
        long result = 0;
        DBResultSet dbrs = null;
        String strDate = "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "\"";
        try {
            String sql = "SELECT " + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] +
                    " FROM " + PstPeriod.TBL_HR_PERIOD +
                    " WHERE " + strDate +
                    " BETWEEN " + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] +   
                    " AND " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE];
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getLong(1);
            }
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
}
