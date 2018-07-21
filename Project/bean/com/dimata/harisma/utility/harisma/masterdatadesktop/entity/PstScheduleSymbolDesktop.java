
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: Satrya Ramayu
 * @version  	: 01
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.utility.harisma.masterdatadesktop.entity;

/* package java */
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.db.DBResultSet;
import java.util.Date;
import java.sql.ResultSet;


/* package qdep */
import com.dimata.util.Formater;
import java.util.Hashtable;
import java.util.Vector;

public class PstScheduleSymbolDesktop {

    /**
     * create by satrya 2014-02-18
     *
     * @return
     */
    public static String listScheduleIdSymbol(int tambahanBolehABs) {
        DBResultSet dbrs = null;
        String result = "";
        try {
            Date dtStart = new Date();
            Date dtEnd = new Date();
            String dtManual = "";
            boolean crossDay = false;
            //dtStart.setHours(dtStart.getHours()-tambahanBolehABs);
            dtStart = new Date(dtStart.getYear(), dtStart.getMonth(), (dtStart.getDate()), (dtStart.getHours() - tambahanBolehABs), dtStart.getMinutes(), dtStart.getSeconds());
            dtEnd = new Date(dtEnd.getYear(), dtEnd.getMonth(), (dtEnd.getDate()), (dtEnd.getHours() + tambahanBolehABs), dtEnd.getMinutes(), dtEnd.getSeconds());
            int jamMelebihiOneDay = 23;
            if (dtStart.getHours() == 0 || dtStart.getHours() == 23) {
                dtManual = "23:" + "59:" + "59";
                crossDay = true;
                jamMelebihiOneDay = jamMelebihiOneDay + tambahanBolehABs;
            }
            if (dtEnd.getHours() == 0) {
                dtManual = "23:" + "59:" + "59";
                crossDay = true;
                jamMelebihiOneDay = jamMelebihiOneDay + tambahanBolehABs;
            }

//                        String dtManual="";
//                        if(dt.getHours()==0 || dt.getHours()+tambahanBolehABs>=23){
//                            int idtManual = 24+tambahanBolehABs;
//                            dtManual = idtManual+":59:00" ;
//                       }else{
//                            dt.setHours(dt.getHours()+tambahanBolehABs);
//                        }

            String sql = "SELECT * FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL
                    + " WHERE \"00:00:00\" <=" + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]
                    + " AND " + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] + "<= \""
                    + "23:59:00"
                    //+ (dtStart.getHours() == 0 || dtStart.getHours() == 23 || dtEnd.getHours() == 0 ? dtManual : Formater.formatDate(dtEnd, "HH:mm:00"))
                    + "\"";
            //+ " WHERE " + "("+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +" BETWEEN \"00:00:00\" AND \""+ (crossDay?dtManual:Formater.formatDate(dtStart, "HH:mm:00")) +"\") OR ("+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]  +" BETWEEN \"00:00:00\" AND \""+Formater.formatDate(dtEnd, "HH:mm:00")+"\")";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                dtEnd = new Date();
                dtEnd = new Date(dtEnd.getYear(), dtEnd.getMonth(), (dtEnd.getDate()), (dtEnd.getHours() + tambahanBolehABs), dtEnd.getMinutes(), dtEnd.getSeconds());
                
                Date schTimeIn = rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]);

                Date schTimeOut = rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]);
                Date dtTmpSchTimeIn = new Date();
                Date dtTmpSchTimeOut = new Date();


                if (schTimeIn != null && schTimeOut != null) {
                    schTimeOut = new Date(dtTmpSchTimeOut.getYear(), dtTmpSchTimeOut.getMonth(), dtTmpSchTimeOut.getDate(), schTimeOut.getHours(), schTimeOut.getMinutes());
                    schTimeIn = new Date(dtTmpSchTimeIn.getYear(), dtTmpSchTimeIn.getMonth(), dtTmpSchTimeIn.getDate(), schTimeIn.getHours(), schTimeIn.getMinutes());

                    Date dtCobaTimeOut = new Date(schTimeOut.getYear(), schTimeOut.getMonth(), (schTimeOut.getDate()), (schTimeOut.getHours() + tambahanBolehABs), schTimeOut.getMinutes(), schTimeOut.getSeconds());
                    boolean melebihiHari=false;
                    if (schTimeIn.getHours() == 0 || schTimeOut.getHours() == 0) {
                        
                    } else {
                        if (dtCobaTimeOut.getDate() > schTimeIn.getDate()) {
                            //dtEnd = new Date(dtEnd.getYear(), dtEnd.getMonth(), (dtEnd.getDate() + 1), (dtEnd.getHours()), dtEnd.getMinutes(), dtEnd.getSeconds());
                            melebihiHari=true;
                        }

                        if ( (melebihiHari && dtEnd.getHours()<dtCobaTimeOut.getHours()) || schTimeIn.getTime() <= dtEnd.getTime() || (schTimeIn.getHours() > schTimeOut.getHours() && dtEnd.getTime() < schTimeOut.getTime())) {
                            result = result + rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]) + ",";
                        }
                    }





                }

            }
            if (result != null && result.length() > 0) {
                result = result.substring(0, result.length() - 1);
            }
            rs.close();
            return result;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    public static String listScheduleIdSymbolAll() {
        DBResultSet dbrs = null;
        String result = "";
        try {
            String sql = "SELECT " + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " WHERE \"00:00:00\" <=" + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]
                    + " AND " + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] + "<= \"" + Formater.formatDate(new Date(), "23:59:59") + "\"";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = result + rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]) + ",";
            }
            if (result != null && result.length() > 0) {
                result = result.substring(0, result.length() - 1);
            }
            rs.close();
            return result;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }
}
