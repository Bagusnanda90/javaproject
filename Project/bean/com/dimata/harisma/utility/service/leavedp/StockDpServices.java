/**
 * Created by IntelliJ IDEA.
 * User: gadnyana
 * Date: Jun 4, 2004
 * Time: 10:42:50 AM
 * To change this template use Options | File Templates.
 */
package com.dimata.harisma.utility.service.leavedp;

import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.masterdata.PstPublicHolidays;
import com.dimata.util.Formater;

import java.util.Vector;
import java.util.Date;

public class StockDpServices {
    static Vector listLeave = new Vector(1, 1);
    static boolean running = false;

    public static void loadLeaveStock() {
        listLeave = new Vector(1, 1);
        System.out.println("== > PROCESS LOAD ");
        try {
            StockDpServices stockDpServices = new StockDpServices();
            running = true;
          //  Thread thr = new Thread(new AutomaticStockDpUpdater());
          //  thr.setDaemon(false);
           // thr.start();

        } catch (Exception e) {
            System.out.println(" error ==> " + e.toString());
        }
    }

    public void startService() {
        if (!running) {
            loadLeaveStock();
        }
    }

    public boolean getStatus() {
        return running;
    }

    public void stopService() {
        running = false;
        System.out.println("==> STOP SERVICE ...!!!");
    }

    public static void main(String[] args) {
        loadLeaveStock();
    }


    private boolean cekPresenceEmployee(long oid, Date dtNow) {
        try {
            String whereClause = "";
            switch (dtNow.getDate()) {
                case 1:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN1] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT1] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 2:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 3:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN3] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT3] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 4:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN4] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT4] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 5:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN5] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT5] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 6:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN6] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT6] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 7:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN7] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT7] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 8:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN8] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT8] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 9:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN9] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT9] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 10:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN10] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT10] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 11:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN11] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT11] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 12:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN12] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT12] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 13:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN13] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT13] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 14:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN14] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT14] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 15:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN15] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT15] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 16:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN16] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT16] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 17:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN17] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT17] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 18:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN18] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT18] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 19:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN19] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT19] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 20:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN20] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT20] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 21:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN21] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT21] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 22:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN22] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT22] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 23:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN23] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT23] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 24:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN24] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT24] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 25:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN25] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT25] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 26:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN26] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT26] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 27:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN27] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT27] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 28:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN28] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT28] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 29:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN29] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT29] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 30:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN30] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT30] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
                case 31:
                    whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN31] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd") +
                            " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT31] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd");
                    break;
            }

            Vector vtEmpSch = PstEmpSchedule.list(0, 0, whereClause, "");
            if (vtEmpSch != null && vtEmpSch.size() > 0)
                return true;

        } catch (Exception e) {
        }
        return false;
    }

    public void processStockDp(Vector vectEmp) {
        try{
            Date dtNow = new Date();
            if (vectEmp != null && vectEmp.size() > 0) {
                if (cekPublicHolidays(dtNow)) {
                    for (int k = 0; k < vectEmp.size(); k++) {
                        Employee employee = (Employee) vectEmp.get(k);
                        String where = PstLeaveStock.fieldNames[PstLeaveStock.FLD_EMPLOYEE_ID] + "=" + employee.getOID();
                        Vector vtLeave = PstLeaveStock.list(0, 0, where, "");
                        if (vtLeave != null && vtLeave.size() > 0) {
                            LeaveStock leaveStock = (LeaveStock) vtLeave.get(0);
                            Vector vtDpStock = PstDpStockManagement.listDpStock(leaveStock.getOID());
                            if (vtDpStock != null && vtDpStock.size() > 0) {
                                for (int i = 0; i < vtDpStock.size(); i++) {
                                    DpStockManagement dpStockMng = (DpStockManagement) vtDpStock.get(i);
                                    String strNowDt = Formater.formatDate(dtNow, "yyyy-MM-dd");
                                    String strDpDt = Formater.formatDate(dpStockMng.getDtOwningDate(), "yyyy-MM-dd");
                                    if (strNowDt.equals(strDpDt)) {
                                        if (cekPresenceEmployee(employee.getOID(), dtNow)) {
                                            if (dpStockMng.getiDpStatus() == PstDpStockManagement.DP_STS_NOT_AKTIF) {
                                                try {
                                                    dpStockMng.setiDpStatus(PstDpStockManagement.DP_STS_AKTIF);
                                                    PstDpStockManagement.updateExc(dpStockMng);
                                                } catch (Exception e) {
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch(Exception e){}
    }

    private boolean cekPublicHolidays(Date dtNow){
        try{
            String whereClause = PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE]+"="+Formater.formatDate(dtNow,"yyyy-MM-dd");
            Vector vtList = PstPublicHolidays.list(0,0,whereClause,"");
            if(vtList!=null && vtList.size()>0)
                return true;
        }catch(Exception e){}
        return false;
    }

}
