/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.attendance;

import com.dimata.harisma.entity.attendance.DpChekBalancing;
import com.dimata.harisma.entity.attendance.DpStockExpired;
import com.dimata.harisma.entity.attendance.DpStockManagement;
import com.dimata.harisma.entity.attendance.DpStockTaken;
import com.dimata.harisma.entity.attendance.PstDpStockExpired;
import com.dimata.harisma.entity.attendance.PstDpStockManagement;
import com.dimata.harisma.entity.attendance.PstDpStockTaken;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.DpBalancing;
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.harisma.entity.leave.dp.PstDpAppDetail;
import com.dimata.harisma.entity.leave.dp.PstDpAppMain;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author artha
 */
public class SessDpStockManagement {

    /**
     * membuat list dp yang dapat di ajukan menjadi dp aplikasi algoritma : cari
     * dp residu pada dp stock management lalu hitung atau cek jumlah dp app
     * detail yang diajukan dari dp stock manajement
     *
     * @param employeeId long
     * @param currDate tanggal sekarang
     * @return vector data yang terdiri dari : 1. DpStockManagement 2. Jml dp
     * app detail yang telah diajukan
     *
     * @create by artha
     */
    public static synchronized Vector listDpStockAvailable(int start, int recordToGet, long employeeId, Date currDate) {
        Vector vData = new Vector(1, 1);
        DBResultSet dbrs = null;

        try {
            String query = "SELECT MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
                    + " ,MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]
                    + " ,MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]
                    + " ,MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]
                    + " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS MAN"
                    + " WHERE MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
                    + " = " + employeeId
                    + " AND MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]
                    + " = " + PstDpStockManagement.DP_STS_AKTIF
                    + " AND MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]
                    + " >= '" + Formater.formatDate(currDate, "yyyy-MM-dd") + "'"
                    + " GROUP BY " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]
                    + " ORDER BY " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]
                    + " ASC";


            if (!(start == 0 && recordToGet == 0)) {
                query += " LIMIT " + start + "," + recordToGet;
            }

            // System.out.println(" ::::::::::::::::::::::::: SessDpStockManagement.listDpStockAvailable = "+query);

            Hashtable hDpAppDetail = new Hashtable();
            hDpAppDetail = listDpAppDetailPerEmp(employeeId, currDate);

            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                DpStockManagement dpStockMan = new DpStockManagement();
                int dpOnReq = 0;
                dpStockMan.setEmployeeId(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]));
                dpStockMan.setOID(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]));
                dpStockMan.setQtyResidue(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]));
                dpStockMan.setDtOwningDate(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]));
                try {
                    dpOnReq = Integer.parseInt((String) hDpAppDetail.get(String.valueOf(dpStockMan.getOID())));
                } catch (Exception ex) {
                }

                Vector vTemp = new Vector(1, 1);
                vTemp.add(dpStockMan);
                vTemp.add(String.valueOf(dpOnReq));

                vData.add(vTemp);
            }
        } catch (Exception e) {
            return new Vector(1, 1);
        } finally {
            DBResultSet.close(dbrs);
            return vData;
        }
    }

    //Count
    public static synchronized int countDpStockAvailable(long employeeId, Date currDate) {
        int count = 0;
        DBResultSet dbrs = null;

        try {
            String query = "SELECT COUNT(MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]
                    + ") FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS MAN"
                    + " WHERE MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
                    + " = " + employeeId
                    + " AND MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]
                    + " = " + PstDpStockManagement.DP_STS_AKTIF
                    + " AND MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]
                    + " >= " + Formater.formatDate(currDate, "yyyy-MM-dd");

            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                count = rs.getInt(0);
            }
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
            return count;
        }
    }

    /**
     * Mencari dp app detail yang masih valid per employee
     *
     * @param employeeId long
     * @param currDate Date
     * @return Hashtable dari DpAppDetail yang masih valid
     */
    private static Hashtable listDpAppDetailPerEmp(long employeeId, Date currDate) {
        Hashtable hDpAppDetail = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String query = "SELECT DET." + PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_ID]
                    + " ,COUNT(DET." + PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_DETAIL_ID] + ")"
                    + " FROM " + PstDpAppDetail.TBL_DP_APP_DETAIL + " AS DET"
                    + " INNER JOIN " + PstDpAppMain.TBL_DP_APP_MAIN + " AS MAIN"
                    + " ON DET." + PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_ID]
                    + " = MAIN." + PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]
                    + " WHERE MAIN." + PstDpAppMain.fieldNames[PstDpAppMain.FLD_EMPLOYEE_ID]
                    + " = " + employeeId
                    + " AND DET." + PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_TAKEN_DATE]
                    + " >= '" + Formater.formatDate(currDate, "yyyy-MM-dd") + "'"
                    + " GROUP BY DET." + PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_ID];
            //     System.out.println("\tSessDpAppDetail.listDpAppDetailPerEmp ::: "+query);

            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                long dpMainOid = 0;
                int count = 0;
                dpMainOid = rs.getLong(1);
                count = rs.getInt(2);
                hDpAppDetail.put(String.valueOf(dpMainOid), String.valueOf(count));
            }
        } catch (Exception e) {
            return new Hashtable();
        } finally {
            DBResultSet.close(dbrs);
            return hDpAppDetail;
        }
    }

    /**
     * Mengambil Dp yangtelah dia ajukan. algoritma : Cari dp app detail yang
     * telah diapprove. Kurangi jumlah Dp balance pada dp stock management lalu
     * bentuk dp app stock taken
     *
     * @param stock management id : long
     * @param taken date : tanggal pengambilan dp
     * @return boolean : TRUE : dp barhasil di taken FALSE : data gagal di taken
     */
    public static boolean createDpTaken(long stockManId, long employeeId, Date takenDate) {
        boolean isSuccess = false;
        try {
            DpStockManagement dpStockMan = new DpStockManagement();
            dpStockMan = PstDpStockManagement.fetchExc(stockManId);
            System.out.println(" createDpTaken =====================> " + PstDpStockTaken.existDpStockTaken(stockManId, dpStockMan.getEmployeeId(), takenDate));
            if (PstDpStockTaken.existDpStockTaken(stockManId, dpStockMan.getEmployeeId(), takenDate) == false) {
                if (dpStockMan.getQtyResidue() > 0) {
                    DpStockTaken dpStockTaken = new DpStockTaken();
                    dpStockTaken.setDpStockId(stockManId);
                    dpStockTaken.setEmployeeId(dpStockMan.getEmployeeId());
                    dpStockTaken.setTakenDate(takenDate);
                    dpStockTaken.setPaidDate(takenDate);
                    dpStockTaken.setTakenQty(1);
                    long oid = PstDpStockTaken.insertExc(dpStockTaken);
                    System.out.println("--- INSERT DP STOCK TAKEN PAID ::: " + oid);

                    dpStockMan.setQtyUsed(dpStockMan.getQtyUsed() + 1);
                    dpStockMan.setQtyResidue(dpStockMan.getQtyResidue() - 1);

                    System.out.println("--- UPDATE DP STOCK MANAGEMENT ::: " + dpStockMan.getOID());
                    PstDpStockManagement.updateExc(dpStockMan);

                    //Jika tidak ada residu buat status nya taken
                    if (dpStockMan.getQtyResidue() <= 0) {
                        dpStockMan.setiDpStatus(PstDpStockManagement.DP_STS_TAKEN);

                        //Jika ada residu, cek apakah masih valid atau sudah expired
                    } else if (dpStockMan.getDtExpiredDate().getTime() < new Date().getTime() && dpStockMan.getiDpStatus() != PstDpStockManagement.DP_STS_EXPIRED) {
                        dpStockMan.setiDpStatus(PstDpStockManagement.DP_STS_EXPIRED);
                        DpStockExpired dpStockEx = new DpStockExpired();
                        dpStockEx.setDpStockId(stockManId);
                        dpStockEx.setExpiredDate(dpStockMan.getDtExpiredDate());
                        dpStockEx.setExpiredQty(dpStockMan.getQtyResidue());
                        long oidEx = PstDpStockExpired.insertExc(dpStockEx);
                        System.out.println("--- INSERT DP STOCK EXPIRED ::: " + oidEx);
                    }
                } else {
                    DpStockTaken dpStockTaken = new DpStockTaken();
                    dpStockTaken.setEmployeeId(employeeId);
                    dpStockTaken.setTakenDate(takenDate);
                    dpStockTaken.setTakenQty(1);
                    long oid = PstDpStockTaken.insertExc(dpStockTaken);
                    System.out.println("--- INSERT DP STOCK TAKEN UNPAID ::: " + oid);
                }
                isSuccess = true;
            }
        } catch (Exception ex) {
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * membuat list dp yang masih dapat di ajukan menjadi dp aplikasi
     *
     * @param employeeId long
     * @return vector data yang terdiri dari : 1. DpStockManagement
     * @create by Roy & Kartika
     */
    public static Vector listDpStockEligible(int start, int recordToGet, long employeeId, Date reqDate) {

        Vector vData = new Vector(1, 1);
        DBResultSet dbrs = null;

        try {
            String query = "SELECT ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
                    + " ,ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]
                    + " ,ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]
                    + " ,ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]
                    + " ,ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]
                    + " , SUM(TK." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + ") as tb_taken "
                    + " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS ST "
                    + " LEFT JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS TK ON ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]
                    + "=" + "TK." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]
                    + " LEFT JOIN " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS AP "
                    + " ON TK." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] + "=AP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " AND AP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + "!=" + PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED
                    + " AND AP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + "!=" + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " WHERE ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
                    + " = " + employeeId
                    + " AND ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]
                    + " = " + PstDpStockManagement.DP_STS_AKTIF
                    + " AND ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]
                    + " >= '" + Formater.formatDate(reqDate, "yyyy-MM-dd") + "'"
                    + " AND ((ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]
                    + " - ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]
                    + " ) >0) AND ST." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "='" + employeeId + "' "
                    + " GROUP BY " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]
                    + " ORDER BY " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]
                    + " ASC ";

            if (!(start == 0 && recordToGet == 0)) {
                query += " LIMIT " + start + "," + recordToGet;
            }


            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                DpStockManagement dpStockMan = new DpStockManagement();

                dpStockMan.setEmployeeId(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]));
                dpStockMan.setOID(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]));
                dpStockMan.setDtOwningDate(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]));
                dpStockMan.setiDpQty(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]));
                dpStockMan.setQtyUsed(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]));
                dpStockMan.setToBeTaken(rs.getInt("tb_taken"));
                vData.add(dpStockMan);

            }

        } catch (Exception e) {
            return null;
        } finally {
            DBResultSet.close(dbrs);
            return vData;
        }
    }

    public static int SizelistDpStockEligible(long employeeId, Date reqDate) {

        DBResultSet dbrs = null;

        try {
            String query = "SELECT COUNT(*) "
                    + " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS ST "
                    + " LEFT JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS TK ON ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]
                    + "=" + "TK." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]
                    + " LEFT JOIN " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS AP "
                    + " ON TK." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] + "=AP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " AND AP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + "!=" + PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED
                    + " AND AP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + "!=" + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " WHERE MAN." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
                    + " = " + employeeId
                    + " AND ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]
                    + " = " + PstDpStockManagement.DP_STS_AKTIF
                    + " AND ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]
                    + " >= '" + Formater.formatDate(reqDate, "yyyy-MM-dd") + "'"
                    + " AND ((ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]
                    + " - ST." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]
                    + " ) >0) AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "='" + employeeId + "' "
                    + " GROUP BY " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]
                    + " ORDER BY " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]
                    + " ASC ";


            int size = 0;
            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                size = rs.getInt(1);
            }

            return size;

        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);

        }
    }

    /**
     * @Author Roy Andika
     * @param employee_id
     * @return
     */
    public static Vector ListDp(long employee_id, long dpStokId) {

        String whereDP = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + employee_id;
        if (dpStokId != 0L) {
            whereDP = whereDP + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] + " = " + dpStokId;
        }
        whereDP = whereDP + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + " = " + PstDpStockManagement.DP_STS_AKTIF
                + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + " - "
                + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] + " > " + 0;

        Vector listDp = new Vector();

        try {
            listDp = PstDpStockManagement.list(0, 0, whereDP, PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]);
        } catch (Exception e) {
            listDp = new Vector();
            System.out.println("Exception " + e.toString());
        }

        Vector vData = new Vector();

        /* kondisi dimana list Dp berisi data */
        if (listDp != null && listDp.size() > 0) {

            for (int i = 0; i < listDp.size(); i++) {

                DpStockManagement dpStockManagement = (DpStockManagement) listDp.get(i);

                float tknExecution = 0;
                // int tknExecution = 0;
                float willTkn = 0;
                //   int willTkn = 0;

                /* taken dp execution */
                tknExecution = getDpTkn(dpStockManagement.getOID());

                /* taken dp will taken */
                willTkn = getWillBeTkn(dpStockManagement.getOID());

                DpStockManagement dpStockMan = new DpStockManagement();

                dpStockMan.setEmployeeId(dpStockManagement.getEmployeeId());
                dpStockMan.setOID(dpStockManagement.getOID());
                dpStockMan.setDtOwningDate(dpStockManagement.getDtOwningDate());
                dpStockMan.setiDpQty(dpStockManagement.getiDpQty());
                dpStockMan.setQtyUsed(tknExecution);
                dpStockMan.setToBeTaken(willTkn);
                dpStockMan.setiExceptionFlag(dpStockManagement.getiExceptionFlag());
                ///update by satrya 2012-11-26
                dpStockMan.setDtExpiredDate(dpStockManagement.getDtExpiredDate());
                dpStockMan.setDtExpiredDateExc(dpStockManagement.getDtExpiredDateExc());
                ///dpStockMan.setDtExpiredDateExc(null);

                vData.add(dpStockMan);

            }

            return vData;

        }

        return null;
    }

    /**
     * @Author Satrya Ramayu 2012-12-14
     * @param employee_id
     * @param dpStokId
     * @return
     */
    public static Vector ListCheckDp(long employee_id, long dpStokId) {

        String whereDP = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + employee_id;
        if (dpStokId != 0L) {
            whereDP = whereDP + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] + " = " + dpStokId;
        }


        Vector listDp = new Vector();

        try {
            listDp = PstDpStockManagement.list(0, 0, whereDP, null);
        } catch (Exception e) {
            listDp = new Vector();
            System.out.println("Exception " + e.toString());
        }

        Vector vData = new Vector();

        /* kondisi dimana list Dp berisi data */
        if (listDp != null && listDp.size() > 0) {

            for (int i = 0; i < listDp.size(); i++) {

                DpStockManagement dpStockManagement = (DpStockManagement) listDp.get(i);

                float tknExecution = 0;
                // int tknExecution = 0;
                float willTkn = 0;
                //   int willTkn = 0;

                /* taken dp execution */
                tknExecution = getDpTkn(dpStockManagement.getOID());

                /* taken dp will taken */
                willTkn = getWillBeTkn(dpStockManagement.getOID());

                DpStockManagement dpStockMan = new DpStockManagement();

                dpStockMan.setEmployeeId(dpStockManagement.getEmployeeId());
                dpStockMan.setOID(dpStockManagement.getOID());
                dpStockMan.setDtOwningDate(dpStockManagement.getDtOwningDate());
                dpStockMan.setiDpQty(dpStockManagement.getiDpQty());
                dpStockMan.setQtyUsed(tknExecution);
                dpStockMan.setToBeTaken(willTkn);
                dpStockMan.setiExceptionFlag(dpStockManagement.getiExceptionFlag());
                ///update by satrya 2012-11-26
                dpStockMan.setDtExpiredDate(dpStockManagement.getDtExpiredDate());
                dpStockMan.setDtExpiredDateExc(dpStockManagement.getDtExpiredDateExc());
                ///dpStockMan.setDtExpiredDateExc(null);

                vData.add(dpStockMan);

            }

            return vData;

        }

        return null;
    }

////update by satrya 2012-12-14
//    /**
//     * 
//     * @param cekVer2
//     * @return 
//     */
//    
//    public static Vector ListLeaveForBalanceDp(boolean CekDpStockId,String emp_numb) {
//        DBResultSet dbrs = null;
//        Vector lists = new Vector();
//        String whereDP = "";
//        if(emp_numb!=null && emp_numb.length()>0){
//            whereDP = "he."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+"="+emp_numb;
//        }
////        if(CekDpStockId){
////            //whereDP = whereDP + 
////        }
////        if (cekVer2) {
////            whereDP = whereDP +"((m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]
////                    + " < 0  AND  m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]
////                    + " >= t." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + ")  AND( t."
////                    + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + "+"
////                    + "m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]
////                    + ">=0 ) OR (m."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]+" <0))"  
////                    +" ORDER BY " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID];
////        } else {
////            whereDP =  whereDP +"( m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]
////                    + " < t." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + ")"
////                    + " GROUP BY m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID];
////
////        }
//
//        try {
//            String sql = " SELECT st."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+","
//+ " sm."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]+","+"st."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]
//+",sm."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]+",sm."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]
//+",st."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+","
//+ " st."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]
//+",st."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]
//+",sm."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+",st."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]+","
//+ " sm."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+",st."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]+",sm."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+ " FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+ " sm " 
//+ " INNER JOIN "+ PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " st ON (st."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]+"=sm."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+")"
//+ " INNER JOIN "+ PstEmployee.TBL_HR_EMPLOYEE + " he ON (he."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = st."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]+")"
//+ " INNER JOIN "+PstLeaveApplication.TBL_LEAVE_APPLICATION + " la ON (la."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+"=st."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+")"
//+ " WHERE la."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+"="+PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED;                   
//                    
//                    
////                    " SELECT  t." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]
////                    + ",t." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]
////                    + ",t." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]
////                    + ", t." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]
////                    + ",t." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]
////                    + ",t." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]
////                    + ",t." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]
////                    //+ ",t." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]+ " AS DP_STOCK_ID_TAKEN "
////                    + ",m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]
////                    + ",m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
////                    + ",m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]
////                    + ",m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]
////                    + ",m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]
////                    + ",m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]
////                    + ",m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC]
////                    + ",m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]
////                    + ",m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]
////                    + " ,m." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXCEPTION_FLAG]
////                    + " FROM " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN
////                    + " AS t INNER JOIN " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT
////                    + " AS m ON(t." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + " = m."
////                    + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] + ") INNER JOIN "
////                    + PstEmployee.TBL_HR_EMPLOYEE +" AS he on (he."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +" =t." +PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]+")";
//
//
//            if (whereDP != null && whereDP.length() > 0) {
//                sql = sql + " AND " + whereDP;
//            }
//            //sql += " ORDER BY sm."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];
//
//            dbrs = DBHandler.execQueryResult(sql);
//            ResultSet rs = dbrs.getResultSet();
//
//            while (rs.next()) {
//                //  x = x + (rs.getInt(1));
//                DpChekBalancing dpChekBalancing = new DpChekBalancing();
//                 //java.util.Date dtExpiredDate = rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]);
//                 //java.util.Date dtExpiredDateExc = rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC]);
//                 java.util.Date dtPaid = DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]), rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE])) ;
//                     java.util.Date dtTaken =  DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]),rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]));
//                       java.util.Date dtFinnish = DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]),rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]));
//                        java.util.Date dtOwning = rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]);
//                        
//                dpChekBalancing.setDp_employee_id(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]));
//                //dpChekBalancing.setDp_expiredDate(dtExpiredDate);
//                //dpChekBalancing.setDp_expiredDateExc(dtExpiredDateExc);
//                dpChekBalancing.setDp_paidDate(dtPaid);
//                dpChekBalancing.setDp_qty(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]));
//                dpChekBalancing.setDp_qtyResidue(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]));
//                dpChekBalancing.setDp_qtyUsed(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]));
//                dpChekBalancing.setDp_stokId(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]));
//                //dpChekBalancing.setDp_stokId_taken(rs.getLong("DP_STOCK_ID_TAKEN"));
//                dpChekBalancing.setDp_stokTakenId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]));
//                dpChekBalancing.setDp_takenQty(rs.getFloat(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]));
//                //dpChekBalancing.setDp_exceptionFlag(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXCEPTION_FLAG]));
//                ///  float tknExecution = getDpTkn(dpChekBalancing.getDp_stokId());
//                dpChekBalancing.setDp_takenDate(dtTaken);
//                dpChekBalancing.setDp_finishDate(dtFinnish);
//                dpChekBalancing.setDp_owningDate(dtOwning);
//                /* taken dp will taken */
//                //float willTkn = getWillBeTkn(dpChekBalancing.getDp_stokId());
//                //dpChekBalancing.setDp_toBetaken(willTkn);
//                dpChekBalancing.setLeaveApplicationId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]));
//                lists.add(dpChekBalancing);
//            }
//            rs.close();
//            // return lists;
//
//        } catch (Exception e) {
//            System.out.println(e);
//        } finally {
//            DBResultSet.close(dbrs);
//        }
//
//        return lists;
//    }
//
//    /**
//     * create by satrya 2012-12-18
//     * @param dpChekBalancing
//     * @param paidDate 
//     */
//     public synchronized static void updateTakenAndQtyUseAndResidueAndPaidDate( DpChekBalancing dpChekBalancing, boolean cekPaidDate) {
//
//        DBResultSet dbrs = null;
//        java.util.Date paidDate = dpChekBalancing.getDp_paidDate();
//        try {
//            String sql = " UPDATE "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN +" AS t INNER JOIN "
//                   +PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS m ON(t."
//                   +PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]
//                   + " = m."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+")" 
//                   + " SET "
//                   + " t."+ PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + " = " + dpChekBalancing.getDp_takenQty()
//                   + ", m."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]
//                   + " = "+dpChekBalancing.getDp_qtyUsed()
//                   + ",m."+ PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]
//                   + " = "+(dpChekBalancing.getDp_qtyResidue());
//                   
//                   if(cekPaidDate){
//                   sql = sql + ", t."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]
//                   + " = "+"\""+paidDate+"\"";
//                 }
//                   if(dpChekBalancing.getDp_qtyResidue()==0){
//                   sql+= ",m."+ PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]
//                   + " = "+PstDpStockManagement.DP_STS_TAKEN;
//                   }
////                   sql = sql + ", t."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]
////                   + " = "+"\""+dpChekBalancing.getDp_takenQty()+"\"";
////                   
//                   sql = sql + ", t."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]
//                   + " = "+"\""+Formater.formatDate(dpChekBalancing.getDp_finishDate(), "yyyy-MM-dd HH:mm:ss") +"\""
//                   + ", t."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]
//                   + " = "+"\""+Formater.formatDate(dpChekBalancing.getDp_takenDate(), "yyyy-MM-dd HH:mm:ss") +"\"";
////                  /* sql = sql + " WHERE ( m."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]
////                   + " < t."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]+") "
////                   + " AND t."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]+" = " + dpChekBalancing.getDp_stokTakenId();*/
//                   sql = sql + " WHERE t."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]+" = " + dpChekBalancing.getDp_stokTakenId();
//
//            //System.out.println("SQL update doc status " + sql);
//
//            int i = DBHandler.execUpdate(sql);
//
//        } catch (Exception e) {
//            System.out.println("Exception " + e.toString());
//        }
//
//    }
//    /**
//     * Create by satrya 2013-01-03
//     * @param employee_id
//     * @param dpStokId
//     * @return 
//     */
      public static long ExcDpBalanceCheckByEmpNumber(String empNumb) {

        
        String whereDP = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + "="+ empNumb;
                //PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + employee_id;
        whereDP = whereDP + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + " = " + PstDpStockManagement.DP_STS_AKTIF
                + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + " - "
                + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] + " > " + 0;
        String order="";
        Vector listDp = new Vector();

        try {
            listDp = PstDpStockManagement.listByEmpNumb(whereDP,order); 
        } catch (Exception e) {
            listDp = new Vector();
            System.out.println("Exception " + e.toString());
        }

        long excBalance=0;
        boolean cekUpdate= true;
        /* kondisi dimana list Dp berisi data */
        if (listDp != null && listDp.size() > 0) {

            for (int i = 0; i < listDp.size(); i++) {

                DpStockManagement dpStockManagement = (DpStockManagement) listDp.get(i);

                float tknExecution = getDpTkn(dpStockManagement.getOID());
                
//                DpChekBalancing dpChekBalancing = new DpChekBalancing();
//
//                dpChekBalancing.setDp_stokId(dpStockManagement.getOID());
                    if(tknExecution!=0){
                          dpStockManagement.setQtyUsed(tknExecution);
                          if(tknExecution==dpStockManagement.getQtyResidue()){
                          dpStockManagement.setQtyResidue(0);
                         // cekUpdate= true;
                          }
                          if( dpStockManagement.getOID()!=0){
                              // if(cekUpdate && dpStockManagement.getOID()!=0){
                              PstDpStockManagement.updateQtyUsedResidue(dpStockManagement); 
                              //cekUpdate=false;
                          }
                    }
             }

            return excBalance;

        }

        return excBalance;
    }
    /**
     * Create by satrya 2013-01-10
     * @param emp_numb
     * @return 
     */
    public static Vector ListDpManagementToDpBalance(String emp_numb, boolean isResidueNotNol) {
        DBResultSet dbrs = null;
        Vector lists = new Vector();
        String whereDP = "";
        if (emp_numb != null && emp_numb.length() > 0) {
            whereDP = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + "=" + emp_numb;
        }
        if(isResidueNotNol && (emp_numb!=null && emp_numb.length() > 0)){
              whereDP = whereDP + " AND SM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE] + "!=" + 0;
        }else if(isResidueNotNol){
            whereDP = whereDP + " ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + "=" + 0;
        }
        

        try {
            String sql = " SELECT SM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] 
                    + ",SM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] 
                    + ",SM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] 
                    + ",SM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] 
                    + ",SM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
                    + ",SM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]
                    + ",SM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]
                    + " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " SM "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON (SM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")";

            if (whereDP != null && whereDP.length() > 0) {
                sql = sql + " WHERE " + whereDP;
            }
            //sql += " ORDER BY sm."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                //  x = x + (rs.getInt(1));
                DpStockManagement dpStockManagement = new DpStockManagement();

                //java.util.Date dtPaid = DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]), rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE])) ;
                //java.util.Date dtTaken =  DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]),rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]));
                //java.util.Date dtFinnish = DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]),rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]));
                java.util.Date dtOwning = rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]);

                dpStockManagement.setOID(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]));
                dpStockManagement.setiDpQty(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]));
                dpStockManagement.setDtOwningDate(dtOwning);
                dpStockManagement.setiDpStatus(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]));
                dpStockManagement.setEmployeeId(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]));
                dpStockManagement.setQtyResidue(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]));
                dpStockManagement.setQtyUsed(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]));
                lists.add(dpStockManagement);
            }
            rs.close();
            // return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }

        return lists;
    }

    /**
     * Create by satrya 20130110
     * @param emp_numb
     * @return 
     */
    public static Vector ListDpTakenToDpBalance(long empId,boolean stockIdIsNol) {
        DBResultSet dbrs = null;
        Vector lists = new Vector();
        String whereDP = "";// ST."+ PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_FLAG_DP_BALANCE] + "=" + dpBalance;
        /*if (emp_numb != null && emp_numb.length() > 0) {
            whereDP = whereDP + ;
        }
        if(stockIdIsNol && (emp_numb!=null && emp_numb.length() > 0)){
              whereDP = whereDP + " AND ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + "=" + 0;
        }else*/ if(stockIdIsNol){
            whereDP =  " ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + "=" + 0;
        }

        try {
            String sql = " SELECT ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID] + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]
                    + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE] + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] 
                    + ",LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + ",LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] 
                    //+  ",ST."+ PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_FLAG_DP_BALANCE]
                    + " FROM "
                    + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " ST "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON (ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")"
                    + " INNER JOIN " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " LA ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + "=ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] + ")";

            if (whereDP != null && whereDP.length() > 0) {
                sql = sql + " WHERE    EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + empId +" AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+"!="+ PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED + " AND " + whereDP;
            }else{
                sql = sql + " WHERE   EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + empId +" AND LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+"!="+ PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;
            }
            //sql += " ORDER BY sm."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                //  x = x + (rs.getInt(1));
                DpStockTaken dpStockTaken = new DpStockTaken();

                java.util.Date dtPaid = DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]), rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]));
                java.util.Date dtTaken = DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]), rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]));
                java.util.Date dtFinnish = DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]), rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]));
                //java.util.Date dtOwning = rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]);

                dpStockTaken.setOID(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]));
                dpStockTaken.setDpStockId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]));
                dpStockTaken.setEmployeeId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]));
                dpStockTaken.setTakenDate(dtTaken);
                dpStockTaken.setTakenFinnishDate(dtFinnish);
                dpStockTaken.setPaidDate(dtPaid);
                dpStockTaken.setTakenQty(rs.getFloat(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]));
                dpStockTaken.setLeaveApplicationId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]));
                dpStockTaken.setDocStatus(rs.getInt(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]));
                dpStockTaken.setSubMissionDate(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]));
//                dpStockTaken.setFlagDpBalance(rs.getInt(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_FLAG_DP_BALANCE]));
                lists.add(dpStockTaken);
            }
            rs.close();
            // return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }

        return lists;
    }
    /**
     * Keterangan: untuk mencari list taken sisa yang belum mempnyai paid date
     * @param empId
     * @param stockIdIsNol
     * @return 
     */
     public static Vector ListDpTakenSisa(long empId,boolean stockIdIsNol) {
        DBResultSet dbrs = null;
        Vector lists = new Vector();
        String whereDP = "";
        if (empId != 0) {
            whereDP = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + empId;
        }
        if(stockIdIsNol && (empId!=0)){
              whereDP = whereDP + " AND ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + "=" + 0;
        }else if(stockIdIsNol){
            whereDP = whereDP + " ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + "=" + 0;
        }

        try {
            String sql = " SELECT ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID] + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]
                    + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE] + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + ",ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] 
                    + ",LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + ",LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]
                    + " FROM "
                    + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " ST "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON (ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] + "=EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")"
                    + " INNER JOIN " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " LA ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + "=ST." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] + ")";

            if (whereDP != null && whereDP.length() > 0) {
                sql = sql + " WHERE  LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+"!="+ PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED + " AND " + whereDP;
            }else{
                sql = sql + " WHERE " + " LA."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+"!="+ PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;
            }
            //sql += " ORDER BY sm."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                //  x = x + (rs.getInt(1));
                DpStockTaken dpStockTaken = new DpStockTaken();

                java.util.Date dtPaid = DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]), rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE]));
                java.util.Date dtTaken = DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]), rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]));
                java.util.Date dtFinnish = DBHandler.convertDate(rs.getDate(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]), rs.getTime(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]));
                //java.util.Date dtOwning = rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]);

                dpStockTaken.setOID(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID]));
                dpStockTaken.setDpStockId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]));
                dpStockTaken.setEmployeeId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]));
                dpStockTaken.setTakenDate(dtTaken);
                dpStockTaken.setTakenFinnishDate(dtFinnish);
                dpStockTaken.setPaidDate(dtPaid);
                dpStockTaken.setTakenQty(rs.getFloat(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]));
                dpStockTaken.setLeaveApplicationId(rs.getLong(PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]));
                dpStockTaken.setDocStatus(rs.getInt(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]));
                dpStockTaken.setSubMissionDate(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]));
                lists.add(dpStockTaken);
            }
            rs.close();
            // return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }

        return lists;
    }
/**
     * create by satrya 2013-01-13
     * Keterangan: untuk melakukan pengecekan apakah next'nya ada sisa stock, fungsi ini di gunakan untuk cek balancing
     * @param empID
     * @param dp_stock_id
     * @return 
     */
    public static boolean checkAdaSisaStock(long empID,long dp_stock_id) {
         String where = " SM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] 
                 +" > (SELECT SM1."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]
                 +" FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT
                 + " SM1 WHERE SM1."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]
                 +"="+dp_stock_id+") AND "
                 +" SM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
                 +"="+empID+ " AND SM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]+"!=0";
     
        Vector vStock = PstDpStockManagement.listDPStock(0, 1, where, null); 

        if (vStock.size() > 0) {
           return false;
        } else {
            return true;
        }
    }
    //update by satrya 2012-12-06
    /**
     * Ketrangan : untuk mencari total TakenQty dari DP
     *
     * @param leaveAppId
     * @param empId
     * @param dt
     * @return
     */
    public static float getTotalSelectedDateLeave(long leaveAppId, long empId, Date dt) {
        DBResultSet dbrs = null;
        float x = 0.0f;
        String sDt = "";
        if (dt != null) {
            sDt = Formater.formatDate(dt, "yyyy-MM-dd");
        }
        try {
            String sql = "SELECT SUM(" + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + ") FROM " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN
                    + " WHERE " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]
                    + " = " + leaveAppId + " AND " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] + "=" + empId
                    + " AND TO_DAYS(" + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + ")"
                    + " >=  TO_DAYS(\"" + sDt + "\") AND  TO_DAYS(" + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + ")"
                    + " <=  TO_DAYS(\"" + sDt + "\")";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                x = x + (rs.getInt(1));
            }
            rs.close();
            return x;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return x;
    }

    /**
     * @Author Roy Andika
     * @param dp_stock_id
     * @return
     * @Desc : untuk mendapatkan total dp taken
     */
    public static float getDpTkn(long dp_stock_id) {
        // public static int getDpTkn(long dp_stock_id){
        DBResultSet dbrs = null;

        try {

            String sql = "SELECT DPT." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION
                    + " APP INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " DPT ON APP."
                    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = DPT."
                    + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] + " WHERE DPT."
                    + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + " = " + dp_stock_id
                    + " AND APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " = "
                    + PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            float totalDp = 0;
            // int totalDp = 0;
            while (rs.next()) {

                totalDp = totalDp + rs.getFloat(1);
                //  totalDp = totalDp + rs.getInt(1); 

            }

            return totalDp;

        } catch (Exception e) {
            System.out.println("Exception getDpTkn:" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    public static float getWillBeTkn(long dp_stock_id) {
        //public static int getWillBeTkn(long dp_stock_id){
        DBResultSet dbrs = null;

        try {

            String sql = "SELECT DPT." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION
                    + " APP INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " DPT ON APP."
                    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = DPT."
                    + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] + " WHERE DPT."
                    + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + " = " + dp_stock_id
                    + " AND ( APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " = "
                    + PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT + " OR APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " = "
                    + PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE + " OR APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " = "
                    + PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED + " ) ";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            //update by satrya 2012-10-22
            float totalDp = 0L;
            //// int totalDp = 0;
            while (rs.next()) {

                totalDp = totalDp + rs.getFloat(1);
                //update by satrya 2012-10-22
                //totalDp = totalDp + rs.getInt(1);

            }

            return totalDp;

        } catch (Exception e) {
            System.out.println("Exception getWillBeTkn:" + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }
}
