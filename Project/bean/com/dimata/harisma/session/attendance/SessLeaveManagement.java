/*
 * SessLeaveDP.java
 *
 * Created on September 8, 2004, 9:40 AM
 */
package com.dimata.harisma.session.attendance;

// import package core java
import java.util.Vector;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Calendar;

// import package dimata
import com.dimata.qdep.db.*;
import com.dimata.util.LogicParser;
import com.dimata.util.Formater;

// import package project
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.attendance.DpStockManagement;
import com.dimata.harisma.entity.attendance.PstDpStockManagement;
import com.dimata.harisma.entity.attendance.AlStockManagement;
import com.dimata.harisma.entity.attendance.PstAlStockManagement;
import com.dimata.harisma.entity.attendance.PstAlStockTaken;
import com.dimata.harisma.entity.attendance.AlStockTaken;
import com.dimata.harisma.entity.attendance.DpStockExpired;
import com.dimata.harisma.entity.attendance.DpStockTaken;
import com.dimata.harisma.entity.attendance.LLStockManagement;
import com.dimata.harisma.entity.attendance.PstAlStockExpired;
import com.dimata.harisma.entity.attendance.PstDpStockExpired;
import com.dimata.harisma.entity.attendance.PstDpStockTaken;
import com.dimata.harisma.entity.attendance.PstLLStockManagement;
import com.dimata.harisma.entity.attendance.PstLlStockTaken;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.search.SrcLeaveManagement;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import com.dimata.system.entity.PstSystemProperty;
import java.util.Hashtable;

/**
 *
 * @author  gedhy
 */
public class SessLeaveManagement {

    public static final String SESS_MANAGEMENT_LEAVE_DP = "SESS_MANAGEMENT_LEAVE_DP";
    public static final String SESS_MANAGEMENT_LEAVE_AL = "SESS_MANAGEMENT_LEAVE_AL";
    public static final String SESS_MANAGEMENT_LEAVE_LL = "SESS_MANAGEMENT_LEAVE_LL";

    /**
     * @param text
     * @return
     */
    private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN)) &&
                    ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }
        return vector;
    }

    /**
     * @Author  : Roy Andika
     * @Desc    : Untuk mendapatkan total expired DP
     * @Param   : Employee id
     */
    public static float getTotalExpiredDP(long employee_ID, SrcLeaveManagement srcLeaveManagement) {

        String whereDpStock = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + employee_ID;

        if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
            Date selectedDate = srcLeaveManagement.getLeavePeriod();
            Calendar gre = Calendar.getInstance();
            gre.setTime(selectedDate);
            int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
            Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
            Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

            whereDpStock = whereDpStock + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                    " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                    " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";

        } else if (srcLeaveManagement.getPeriodId() > 0) {
            Period period = new Period();
            try {
                period = PstPeriod.fetchExc(srcLeaveManagement.getPeriodId());
            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            }
            Date dtStartDate = (Date) period.getStartDate().clone();
            Date dtEndDate = (Date) period.getEndDate().clone();

            whereDpStock = whereDpStock + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                    " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                    " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
        }

        Vector listDpStock = PstDpStockManagement.list(0, 0, whereDpStock, null);

        float sumDPExpired = 0;

        if (listDpStock != null && listDpStock.size() > 0) {

            for (int i = 0; i < listDpStock.size(); i++) {

                DpStockManagement dpStockManagement = new DpStockManagement();

                dpStockManagement = (DpStockManagement) listDpStock.get(i);

                String whereDpExpired = PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_DP_STOCK_ID] + " = " + dpStockManagement.getOID();

                Vector listExpired = PstDpStockExpired.list(0, 0, whereDpExpired, null);

                if (listExpired != null && listExpired.size() > 0) {

                    for (int j = 0; j < listExpired.size(); j++) {

                        DpStockExpired dpStockExpired = (DpStockExpired) listExpired.get(j);

                        sumDPExpired = sumDPExpired + dpStockExpired.getExpiredQty();

                    }
                }
            }
        }

        return sumDPExpired;
    }

    /**
     * @Author  : Roy Andika
     * @Desc    : Untuk mendapatkan total expired DP
     * @Param   : Employee id
     */
    public static float getTotalDpExpired(long employee_ID, SrcLeaveManagement srcLeaveManagement) {

        String whereDpStock = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + employee_ID +
                " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                " IN (" + PstDpStockManagement.DP_STS_NOT_AKTIF + ", " + PstDpStockManagement.DP_STS_AKTIF + ") ";

        if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {

            Date selectedDate = srcLeaveManagement.getLeavePeriod();
            Calendar gre = Calendar.getInstance();
            gre.setTime(selectedDate);

            int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
            Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
            Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

            whereDpStock = whereDpStock + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                    " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                    " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";

        } else if (srcLeaveManagement.getPeriodId() > 0) {
            Period period = new Period();
            try {
                period = PstPeriod.fetchExc(srcLeaveManagement.getPeriodId());
            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            }
            Date dtStartDate = (Date) period.getStartDate().clone();
            Date dtEndDate = (Date) period.getEndDate().clone();

            whereDpStock = whereDpStock + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                    " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                    " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
        }

        Vector listDpStock = PstDpStockManagement.list(0, 0, whereDpStock, null);

        float sumDPExpired = 0;

        if (listDpStock != null && listDpStock.size() > 0) {

            for (int i = 0; i < listDpStock.size(); i++) {

                DpStockManagement dpStockManagement = new DpStockManagement();

                dpStockManagement = (DpStockManagement) listDpStock.get(i);

                String whereDpExpired = PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_DP_STOCK_ID] + " = " + dpStockManagement.getOID();

                Vector listExpired = PstDpStockExpired.list(0, 0, whereDpExpired, null);

                if (listExpired != null && listExpired.size() > 0) {

                    for (int j = 0; j < listExpired.size(); j++) {

                        DpStockExpired dpStockExpired = (DpStockExpired) listExpired.get(j);

                        sumDPExpired = sumDPExpired + dpStockExpired.getExpiredQty();

                    }
                }
            }
        }

        return sumDPExpired;
    }
    
   
    public static float getTotalDPExpiredByDepartment(long departmentId){
//public static int getTotalDPExpiredByDepartment(long departmentId){
        DBResultSet dbrs = null;

        //Vector result = new Vector();
        try {
            String sql="SELECT SUM(STO." +  PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] +
                    ") FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " STO INNER JOIN " +
                    PstEmployee.TBL_HR_EMPLOYEE + " EMP ON STO." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + departmentId +
                    " AND STO." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + " = 3" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                float totalExpired = rs.getFloat(1);
                // int totalExpired = rs.getInt(1);
                return totalExpired;
            }


        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }

    /**
     * @Author Roy Andika
     * @param employee_ID
     * @Desc Untuk mendapatkan Expired dari stock Dp management     
     * @return 
     * 
     */
    public static float getDpExpired(long employee_ID, Date startOwning) {
//   public static int getDpExpired(long employee_ID, Date startOwning) {
        String where = "";
       //update by satrya 2013-08-30
        /**
         * update by satrya 2014-01-18
         *   if(employee_ID!=0){
                return 0;
            }
         */
        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e){
            System.out.println("Exception : " + e.getMessage());
        }
       
       if(leaveConfig.getBalanceNotCalculationDpExpired()){
            
       }else{
             if(employee_ID!=0){
                return 0;
            }
       }
        if (startOwning == null) {

            
            //update by satrya 2014-01-18
            //where = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + employee_ID;
            where = Formater.formatDate(new Date(), "yyyy-MM-dd") + " > '" + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]  + "' AND " +
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + employee_ID;
        } else {

            where = Formater.formatDate(startOwning, "yyyy-MM-dd") + " > '" + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]  + "' AND " +
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + employee_ID;
            
            /**
             * //update by satrya 2014-01-18
             * where = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] + " >= '" + Formater.formatDate(startOwning, "yyyy-MM-dd") + "' AND " +
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + employee_ID;
             */

        }

        Vector listDp = new Vector();

        try {

            listDp = PstDpStockManagement.list(0, 0, where, null);

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        if (listDp != null && listDp.size() > 0) {

            String whereIn = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+ " IN "/* update by satrya 2014-01-18 PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_DP_STOCK_ID] + " IN "*/;

            //int max = listDp.size() - 1;
            String whereDpId="";
            for (int idxListDp = 0; idxListDp < listDp.size(); idxListDp++) {

                DpStockManagement dpStockManagement = new DpStockManagement();

                dpStockManagement = (DpStockManagement) listDp.get(idxListDp);
                boolean cekExpired=false;
                Date dtPembanding = new Date();
                dtPembanding.setHours(0);
                dtPembanding.setMinutes(0);
                dtPembanding.setSeconds(0);
                if (dpStockManagement.getiExceptionFlag() == PstDpStockManagement.EXC_STS_NO && leaveConfig.getDPExpired() ) {

                    if ((dpStockManagement.getDtExpiredDate().getTime() / (24L * 60L * 60L * 1000L)) < (dtPembanding.getTime()) / (24L * 60L * 60L * 1000L)) {
                        cekExpired = true;
                    }
                } else if (dpStockManagement.getiExceptionFlag() == PstDpStockManagement.EXC_STS_YES && leaveConfig.getDPExpired()) {

                    if (dpStockManagement.getDtExpiredDateExc() != null && (dpStockManagement.getDtExpiredDateExc().getTime() / (24L * 60L * 60L * 1000L)) < (dtPembanding.getTime()) / (24L * 60L * 60L * 1000L)) {
                        cekExpired = true;
                    }
                }
             
                if(cekExpired){
                    whereDpId = whereDpId + dpStockManagement.getOID() + ",";
                }
                /* update by satrya 2014-01-18 if (idxListDp == 0) {

                    if (listDp.size() == 1) {

                        whereIn = whereIn + " ( " + dpStockManagement.getOID() + " ) ";

                    } else {

                        whereIn = whereIn + " ( " + dpStockManagement.getOID() + ",";

                    }

                } else if (idxListDp == max) {

                    whereIn = whereIn + dpStockManagement.getOID() + " ) ";

                } else {

                    whereIn = whereIn + dpStockManagement.getOID() + ",";

                }*/

            }   
            if(whereDpId!=null && whereDpId.length()>0){
                whereDpId = whereDpId.substring(0, whereDpId.length()-1);
            }
            whereIn = whereIn +"("+whereDpId+")";
            float qty = 0;
            ///int qty = 0;
            try {

                qty = CountDPExpiredAtDPStockManagementQty(whereIn);

            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            }

            return qty;

        }

        return 0;
    }

    /**
     * create by satrya 2013-07-26
     * @param employee_ID
     * @param startOwningDate
     * @return 
     */
    public static float getDpTakenOwningDate(long employee_ID, Date startOwningDate) {
        DBResultSet dbrs = null;
        try {
         if(employee_ID!=0 && startOwningDate!=null){
            String sStartOwningDate = Formater.formatDate(startOwningDate, "yyyy-MM-dd 00:00:00");
            String sql = " SELECT "+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]+" FROM "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN
                    + " WHERE " +PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]+"="+employee_ID
                    + " AND " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_PAID_DATE] + "=\""+sStartOwningDate+"\"";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                float count = 0;
                count = rs.getFloat(1);
                return count;

            }
         }
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }
    
    /**
     * create by satrya 2013-07-27
     * @param dpStockTaken
     * @return 
     */
  public static float getEligibleDayOffLeave(DpStockTaken dpStockTaken){

      float qty=0;
        if(dpStockTaken.getOID() != 0){

            DpStockTaken objDpStockTakenInSystem = new DpStockTaken();
            
            try{
                objDpStockTakenInSystem = PstDpStockTaken.fetchExc(dpStockTaken.getOID());
            }catch(Exception E){
                System.out.println("[exception] "+E.toString());
            }

            qty =  (SessLeaveApplication.getDpEligbleDays(dpStockTaken.getEmployeeId(),objDpStockTakenInSystem)) + dpStockTaken.getTakenQty();
        }
         return qty;
        /*else{

            float qty = SessLeaveApplication.getDpEligbleDays(dpStockTaken.getEmployeeId(),objDpStockTakenInSystem);

            return qty;

        }*/
    }
    public static float CountQty(String where) {
//  public static int CountQty(String where) {
        //update by satrya 2014-01-18
        if(where==null || where.length()==0){
            return 0;
        }
        DBResultSet dbrs = null;

        try {

            String sql = "SELECT SUM(" + PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_EXPIRED_QTY] + ") FROM " +
                    PstDpStockExpired.TBL_HR_DP_STOCK_EXPIRED + " WHERE " + where;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                float count = 0;
                count = rs.getFloat(1);
                return count;

            }

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;


    }

    /**
     * keterangan: untuk mencari dp stock management yg sdh expired
     * create by satrya 2014-01-18
     * @param where
     * @return 
     */
    public static float CountDPExpiredAtDPStockManagementQty(String where) {
//  public static int CountQty(String where) {
        //update by satrya 2014-01-18
        if(where==null || where.length()==0){
            return 0;
        }
        DBResultSet dbrs = null;

        try {

            String sql = "SELECT SUM(" + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + ") FROM " +
                    PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " WHERE " + where;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                float count = 0;
                count = rs.getFloat(1);
                return count;

            }

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;


    }
    /**
     * 
     * @param employee_ID
     * @param srcLeaveManagement
     * @return
     */
    public static float getTotalExpiredDP(long dpStockManagementID) {

        float sumDPExpired = 0;

        String whereDpExpired = PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_DP_STOCK_ID] + " = " + dpStockManagementID;

        Vector listExpired = PstDpStockExpired.list(0, 0, whereDpExpired, null);

        if (listExpired != null && listExpired.size() > 0) {

            for (int j = 0; j < listExpired.size(); j++) {

                DpStockExpired dpStockExpired = (DpStockExpired) listExpired.get(j);

                sumDPExpired = sumDPExpired + dpStockExpired.getExpiredQty();

            }
        }

        return sumDPExpired;
    }

    // ------------------- Start process Dp Stock --------------------------
    /**
     * @param srcLeaveManagement     
     * @param limitStart
     * @param recordToGet          
     * @return
     *     
     * @created by edhy
     */
    public static Vector listSummaryDpStock(SrcLeaveManagement srcLeaveManagement, int limitStart, int recordToGet) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", SUM(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + ")" +
                    ", SUM(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] + ")" +
                    ", SUM(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE] + ")" +
                    " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DP " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";

            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                strLeavePeriodCondition = " DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            } else if (srcLeaveManagement.getPeriodId() > 0) {
                Period period = PstPeriod.fetchExc(srcLeaveManagement.getPeriodId());
                Date dtStartDate = (Date) period.getStartDate().clone();
                Date dtEndDate = (Date) period.getEndDate().clone();

                strLeavePeriodCondition = " DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            //String strDpStatusCondition = " DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
            //        " = " + PstDpStockManagement.DP_STS_AKTIF;

            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }

            /*
            if (strDpStatusCondition != null && strDpStatusCondition.length() > 0) {
            if (whereClause != null && whereClause.length() > 0) {
            whereClause = whereClause + " AND " + strDpStatusCondition;
            } else {
            whereClause = strDpStatusCondition;
            }
            }
             */

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            } else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            // add group by
            sql = sql + " GROUP BY DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];

            // add whereClause 
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];

            // add limit
            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("listSummaryDpStock ::::::::::::: SQL = " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector ls = new Vector(1, 1);

                DpStockManagement dpStockManagement = new DpStockManagement();
                dpStockManagement.setEmployeeId(rs.getLong(1));
                dpStockManagement.setiDpQty(rs.getInt(4));
                dpStockManagement.setQtyUsed(rs.getInt(5));
                dpStockManagement.setQtyResidue(rs.getInt(6));
                ls.add(dpStockManagement);

                Employee emp = new Employee();
                emp.setEmployeeNum(rs.getString(2));
                emp.setFullName(rs.getString(3));
                ls.add(emp);

                lists.add(ls);
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

    /**
     * @param srcLeaveManagement
     * @return
     * @created by edhy
     */
    public static int countSummaryDpStock(SrcLeaveManagement srcLeaveManagement) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(DISTINCT DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + ")" +
                    " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DP " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                strLeavePeriodCondition = " DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            String strDpStatusCondition = " DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                    " = " + PstDpStockManagement.DP_STS_AKTIF;

            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }

            

             if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            } else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            }
            
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            //System.out.println("countSummaryDpStock SQL = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
            return result;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    /**     
     * @param employeeId
     * @param limitStart
     * @param recordToGet          
     * @return     
     * @created by edhy
     */
    public static Vector listDetailDpStock(long employeeId, int limitStart, int recordToGet) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                    ", DSM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_LEAVE_PERIODE_ID] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_NOTE] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXCEPTION_FLAG] +
                    " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DSM " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON DSM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE DSM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = " + employeeId +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] +
                    " = 0 " +
                    " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                    " IN (" + PstDpStockManagement.DP_STS_NOT_AKTIF + ", " + PstDpStockManagement.DP_STS_AKTIF + ")" +
                    " ORDER BY " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];

            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("listDetailDpStock SQL = " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                DpStockManagement dpStockManagement = new DpStockManagement();
                dpStockManagement.setOID(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]));
                dpStockManagement.setEmployeeId(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]));
                dpStockManagement.setLeavePeriodeId(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_LEAVE_PERIODE_ID]));
                dpStockManagement.setDtOwningDate(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]));
                dpStockManagement.setiDpQty(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]));
                dpStockManagement.setQtyUsed(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]));
                dpStockManagement.setQtyResidue(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]));
                dpStockManagement.setiDpStatus(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]));
                dpStockManagement.setDtExpiredDate(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]));
                dpStockManagement.setiExceptionFlag(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXCEPTION_FLAG]));
                dpStockManagement.setDtExpiredDateExc(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC]));
                dpStockManagement.setStNote(rs.getString(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_NOTE]));

                lists.add(dpStockManagement);
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

    /**     
     * @param employeeId
     * @param limitStart
     * @param recordToGet          
     * @return     
     * @created by Roy Andika
     */
    public static Vector listDetailDpStock(long employeeId, int limitStart, int recordToGet, SrcLeaveManagement srcLeaveManagement) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;

        //---wick
        int DpQty=0;
        //----

        try {
            String sql = "SELECT " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                    ", DSM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_LEAVE_PERIODE_ID] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_NOTE] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC] +
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXCEPTION_FLAG] +
                    //update by satrya 2013-02-24
                    ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_FLAG_STOCK] +
                    " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DSM " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON DSM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE DSM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = " + employeeId +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] +
                    " = " + PstEmployee.NO_RESIGN ;

            //----wick----

             //       " AND DSM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] +
             //       " != " + DpQty;
            //------------
            
            //" AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
            //" IN (" + PstDpStockManagement.DP_STS_NOT_AKTIF + ", " + PstDpStockManagement.DP_STS_AKTIF + ") ";


            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {

                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);

                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                sql = sql + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";

            } else if (srcLeaveManagement.getPeriodId() > 0) {
                Period period = new Period();
                try {
                    period = PstPeriod.fetchExc(srcLeaveManagement.getPeriodId());
                } catch (Exception e) {
                    System.out.println("Exception " + e.toString());
                }
                Date dtStartDate = (Date) period.getStartDate().clone();
                Date dtEndDate = (Date) period.getEndDate().clone();

                sql = sql + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            sql = sql + " ORDER BY " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];

            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            //System.out.println("listDetailDpStock SQL = " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                DpStockManagement dpStockManagement = new DpStockManagement();
                dpStockManagement.setOID(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]));
                dpStockManagement.setEmployeeId(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]));
                dpStockManagement.setLeavePeriodeId(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_LEAVE_PERIODE_ID]));
                dpStockManagement.setDtOwningDate(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]));
                dpStockManagement.setiDpQty(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]));
                dpStockManagement.setQtyUsed(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]));
                dpStockManagement.setQtyResidue(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]));
                dpStockManagement.setiDpStatus(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]));
                dpStockManagement.setDtExpiredDate(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]));
                dpStockManagement.setiExceptionFlag(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXCEPTION_FLAG]));
                dpStockManagement.setDtExpiredDateExc(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC]));
                dpStockManagement.setStNote(rs.getString(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_NOTE]));
                //update by satrya 2013-02-24
                dpStockManagement.setFlagStock(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_FLAG_STOCK]));

                lists.add(dpStockManagement);
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

    /**     
     * @param employeeId
     * @return
     *     
     * @created by edhy
     */
    public static float countDetailDpStock(long employeeId) {
        float result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] + ")" +
                    " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT +
                    " WHERE " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = " + employeeId +
                    " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                    " IN (" + PstDpStockManagement.DP_STS_NOT_AKTIF + ", " + PstDpStockManagement.DP_STS_AKTIF + ")";

//            System.out.println("countDetailDpStock SQL = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getFloat(1);
            }
            rs.close();
            return result;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }


     public static int countDetailDpStock(long employeeId,SrcLeaveManagement srcLeaveManagement) {

         int result = 0;
        DBResultSet dbrs = null;

        try {

            String sql = "SELECT COUNT(" + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +") "+
                    " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DSM " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON DSM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE DSM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = " + employeeId +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] +
                    " = " + PstEmployee.NO_RESIGN;            

            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {

                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);

                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                sql = sql + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";

            }else if (srcLeaveManagement.getPeriodId() > 0) {
                Period period = new Period();
                try {
                    period = PstPeriod.fetchExc(srcLeaveManagement.getPeriodId());
                } catch (Exception e) {
                    System.out.println("Exception " + e.toString());
                }
                Date dtStartDate = (Date) period.getStartDate().clone();
                Date dtEndDate = (Date) period.getEndDate().clone();

                sql = sql + " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            sql = sql + " ORDER BY " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];

            System.out.println("listDetailDpStock SQL = " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = rs.getInt(1);
            }
            
            rs.close();
            return result;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    // -------------------- End process Dp Stock ------------------------
    // ---------------------- Start Process AL Stock --------------------
    /**
     * @param srcLeaveManagement     
     * @param limitStart
     * @param recordToGet          
     * @return
     *     
     * @created by edhy
     */
    public static Vector listSummaryAlStock(SrcLeaveManagement srcLeaveManagement, int limitStart, int recordToGet) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + ")" +
                    ", SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] + ")" +
                    ", SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] + ")" +
                    " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS AL " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                strLeavePeriodCondition = " AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            String strAlStatusCondition = " AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                    " = " + PstAlStockManagement.AL_STS_AKTIF;

            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }

            if (strAlStatusCondition != null && strAlStatusCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strAlStatusCondition;
                } else {
                    whereClause = strAlStatusCondition;
                }
            }

            //update by ayu
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            } else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            // group by clause
            sql = sql + " GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID];

            // order by clause
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE];

            // limit
            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            //           System.out.println("listSummaryAlStock SQL = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector ls = new Vector(1, 1);

                AlStockManagement alStockManagement = new AlStockManagement();
                alStockManagement.setEmployeeId(rs.getLong(1));
                alStockManagement.setAlQty(rs.getFloat(4));
                alStockManagement.setQtyUsed(rs.getFloat(5));
                alStockManagement.setQtyResidue(rs.getFloat(6));
                ls.add(alStockManagement);

                Employee emp = new Employee();
                emp.setEmployeeNum(rs.getString(2));
                emp.setFullName(rs.getString(3));
                ls.add(emp);

                lists.add(ls);
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

    // ---------------------- Start Process AL Stock (methhod khusus untuk Intimas)--------------------
    /**
     * @param srcLeaveManagement     
     * @param limitStart
     * @param recordToGet          
     * @return
     *     
     * @created by Yunny
     */
    public static Vector listSummaryAlStockInt(SrcLeaveManagement srcLeaveManagement, int limitStart, int recordToGet) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] +
                    ", SUM(AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + ")" +
                    ", AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +
                    " FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";
            String strWhereTakenDate = "";
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                strLeavePeriodCondition = " AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";

            }

            /*String strAlStatusCondition = " AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+
            " = " + PstAlStockManagement.AL_STS_AKTIF;*/
            String strAlStatusCondition = "";
            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }

            if (strAlStatusCondition != null && strAlStatusCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strAlStatusCondition;
                } else {
                    whereClause = strAlStatusCondition;
                }
            }

            //update by ayu
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            } else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            // group by clause
            sql = sql + " GROUP BY AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID];

            // order by clause
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE];

            // limit
            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

//            System.out.println("[SQL] employee.attendance.SessLeaveManagement.listSummaryAlStock = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector ls = new Vector(1, 1);

                AlStockTaken alStockTaken = new AlStockTaken();
                alStockTaken.setEmployeeId(rs.getLong(1));
                alStockTaken.setTakenQty(rs.getFloat(5));
                alStockTaken.setTakenDate(rs.getDate(6));
                ls.add(alStockTaken);

                Employee emp = new Employee();
                emp.setEmployeeNum(rs.getString(2));
                emp.setFullName(rs.getString(3));
                emp.setCommencingDate(rs.getDate(4));
                ls.add(emp);

                lists.add(ls);
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

    // ---------------------- Start Process AL Stock (GLOBAL PROSES)--------------------
    /**
     * @param srcLeaveManagement     
     * @param limitStart
     * @param recordToGet          
     * @return
     *     
     * @created by Artha
     */
    public static Vector listSummaryAlStockIntOnMan(SrcLeaveManagement srcLeaveManagement, int limitStart, int recordToGet) {
        //  System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        StringBuffer strId = new StringBuffer();


        try {
            String sql = "SELECT DISTINCT AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] //1
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] //2
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] //3
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] //4
                    + ", SUM(AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED] + ")" //5
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] // 6 -- added by Bayu
                    + " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS AL " + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " ON AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            String sql_id_only = "SELECT DISTINCT AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS AL " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";
            String strWhereTakenDate = "";
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                /*  strLeavePeriodCondition = " AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE]+
                " BETWEEN \""+Formater.formatDate(dtStartDate,"yyyy-MM-dd") + 
                " \" AND \"" + Formater.formatDate(dtEndDate,"yyyy-MM-dd") + "\"";  
                 */
                strLeavePeriodCondition = " AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] +
                        " <= \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
                /*  strWhereTakenDate = " TK."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+
                " BETWEEN \""+Formater.formatDate(dtStartDate,"yyyy-MM-dd") + 
                " \" AND \"" + Formater.formatDate(dtEndDate,"yyyy-MM-dd") + "\""; 
                 * */
                strWhereTakenDate = " TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +
                        " <= \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            /*String strAlStatusCondition = " AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+
            " = " + PstAlStockManagement.AL_STS_AKTIF;*/



            String strAlStatusCondition = "";
            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }

            if (strAlStatusCondition != null && strAlStatusCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strAlStatusCondition;
                } else {
                    whereClause = strAlStatusCondition;
                }
            }

            //update by ayu
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            } else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";
            }
            Date selectedDate = srcLeaveManagement.getLeavePeriod();
            Calendar gre = Calendar.getInstance();
            gre.setTime(selectedDate);
            int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
            Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
            Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

            /////////////////////////////// Mencari id yang tidak dipakai//////////////////////////
            try {
                String strSqlSubQuery = " SELECT EXS." + PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_AL_STOCK_ID] + " FROM " + PstAlStockExpired.TBL_HR_AL_STOCK_EXPIRED + " AS EXS " + " WHERE EXS." + PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_EXPIRED_DATE] + " <= \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
                System.out.println("SQL ::: " + strSqlSubQuery);
                DBResultSet dbrs2a = DBHandler.execQueryResult(strSqlSubQuery);
                ResultSet rs_takena = dbrs2a.getResultSet();
                strId.append("");
                while (rs_takena.next()) {
                    if (!strId.toString().equals("")) {
                        strId.append(",");
                    }
                    strId.append(rs_takena.getString(1));
                }
                rs_takena.close();
            } catch (Exception ex) {
            }
            ///////////////////////////////////////////////////////////////////////////////////////

            if (strId.toString().length() > 0) {
                whereClause += " AND AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] + " NOT IN (" + strId.toString() + ") ";
                if (strWhereTakenDate.length() > 0) {
                    strWhereTakenDate += " AND TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID] + " NOT IN (" + strId.toString() + ") ";
                } else {
                    strWhereTakenDate = " TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID] + " NOT IN (" + strId.toString() + ") ";
                }
            }
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
                sql_id_only += " WHERE " + whereClause;
            }

            // group by clause
            sql = sql + " GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID];
            sql_id_only = sql_id_only + " GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID];

            // order by clause
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE];
            sql_id_only = sql_id_only + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE];

            // limit
            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            // sql_id_only = sql_id_only + " LIMIT " + limitStart + ","+ recordToGet ;
            }

            //   sql_id_only += ") AS UN";

            /////////////////////////////////////////////////
            StringBuffer strIdOnly = new StringBuffer();
            try {
                String strSqlSubQuery = sql_id_only;
                // System.out.println("[SQL] SessLeaveManagement.listSummaryAlStockIntOnMan ::: "+strSqlSubQuery);
                DBResultSet dbrs2a = DBHandler.execQueryResult(strSqlSubQuery);
                ResultSet rs_takena = dbrs2a.getResultSet();
                strIdOnly.append("");
                while (rs_takena.next()) {
                    if (!strIdOnly.toString().equals("")) {
                        strIdOnly.append(",");
                    }
                    strIdOnly.append(rs_takena.getString(1));
                }
                rs_takena.close();
            } catch (Exception ex) {
            }

            ////////////////////////////////////////////////


            //Count taken at set date
            String sql_taken = "SELECT DISTINCT TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] + ", SUM(TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + ")" + " FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS TK " + " WHERE TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] + " IN (" + (strIdOnly.toString()) + ") " + " AND " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + ">0 ";
            if (strWhereTakenDate.length() > 0) {
                sql_taken += " AND " + strWhereTakenDate;
            }
            sql_taken += " GROUP BY TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID];
            // System.out.println("XXXXX ::: "+sql_taken);
            //System.out.println("[SQL] Count Taken : "+sql_taken);



            //         System.out.println("[SQL] employee.attendance.SessLeaveManagement.listSummaryAlStock = "+sql_taken);
            //         System.out.println("[SQL] employee.attendance.SessLeaveManagement.listSummaryAlStock = "+sql);
            //Count taken
            DBResultSet dbrs2 = DBHandler.execQueryResult(sql_taken);
            ResultSet rs_taken = dbrs2.getResultSet();

            Hashtable hastTable = new Hashtable();
            while (rs_taken.next()) {
                hastTable.put(rs_taken.getString(1), rs_taken.getString(2));
            }
            rs_taken.close();


            //ADD
            //  sql += ") AS UN";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector ls = new Vector(1, 1);

                AlStockManagement alStockMan = new AlStockManagement();
                alStockMan.setEmployeeId(rs.getLong(1));
                alStockMan.setEntitled(rs.getFloat(5));
                int countTaken = 0;
                String strCountTaken = "";
                strCountTaken = (String) hastTable.get(rs.getString(1));
                try {
                    countTaken = Integer.parseInt(strCountTaken);
                } catch (Exception ex) {
                    countTaken = 0;
                }
                alStockMan.setQtyUsed(countTaken);
                alStockMan.setQtyResidue(rs.getFloat(5) - countTaken);
                ls.add(alStockMan);

                Employee emp = new Employee();
                emp.setEmployeeNum(rs.getString(2));
                emp.setFullName(rs.getString(3));
                emp.setCommencingDate(rs.getDate(4));
                emp.setOID(rs.getLong(6));
                ls.add(emp);

                lists.add(ls);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println("[ERROR] " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    /**
     * @param srcLeaveManagement     
     * @return
     *     
     * @created by artha   
     */
    public static int countSummaryAlStockOnMan(SrcLeaveManagement srcLeaveManagement) {
        int count = 0;
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        StringBuffer strId = new StringBuffer();


        try {
            String sql = "SELECT COUNT(UN.EMPLOYEE_ID) FROM (SELECT DISTINCT AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " AS EMPLOYEE_ID FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS AL " + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + " ON AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            String sql_id_only = "SELECT DISTINCT AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS AL " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";
            String strWhereTakenDate = "";
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                /*  strLeavePeriodCondition = " AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE]+
                " BETWEEN \""+Formater.formatDate(dtStartDate,"yyyy-MM-dd") + 
                " \" AND \"" + Formater.formatDate(dtEndDate,"yyyy-MM-dd") + "\"";  
                 */
                strLeavePeriodCondition = " AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] +
                        " <= \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
                /*  strWhereTakenDate = " TK."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+
                " BETWEEN \""+Formater.formatDate(dtStartDate,"yyyy-MM-dd") + 
                " \" AND \"" + Formater.formatDate(dtEndDate,"yyyy-MM-dd") + "\""; 
                 * */
                strWhereTakenDate = " TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +
                        " <= \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            /*String strAlStatusCondition = " AL."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+
            " = " + PstAlStockManagement.AL_STS_AKTIF;*/



            String strAlStatusCondition = "";
            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }

            if (strAlStatusCondition != null && strAlStatusCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strAlStatusCondition;
                } else {
                    whereClause = strAlStatusCondition;
                }
            }

            //update by ayu
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            } else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";
            }
            Date selectedDate = srcLeaveManagement.getLeavePeriod();
            Calendar gre = Calendar.getInstance();
            gre.setTime(selectedDate);
            int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
            Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
            Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

            /////////////////////////////// Mencari id yang tidak dipakai//////////////////////////
            try {
                String strSqlSubQuery = " SELECT EXS." + PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_AL_STOCK_ID] + " FROM " + PstAlStockExpired.TBL_HR_AL_STOCK_EXPIRED + " AS EXS " + " WHERE EXS." + PstAlStockExpired.fieldNames[PstAlStockExpired.FLD_EXPIRED_DATE] + " <= \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
                //System.out.println("SQL ::: "+strSqlSubQuery);
                DBResultSet dbrs2a = DBHandler.execQueryResult(strSqlSubQuery);
                ResultSet rs_takena = dbrs2a.getResultSet();
                strId.append("");
                while (rs_takena.next()) {
                    if (!strId.toString().equals("")) {
                        strId.append(",");
                    }
                    strId.append(rs_takena.getString(1));
                }
                rs_takena.close();
            } catch (Exception ex) {
            }
            ///////////////////////////////////////////////////////////////////////////////////////

            if (strId.toString().length() > 0) {
                whereClause += " AND AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] + " NOT IN (" + strId.toString() + ") ";
                if (strWhereTakenDate.length() > 0) {
                    strWhereTakenDate += " AND TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID] + " NOT IN (" + strId.toString() + ") ";
                } else {
                    strWhereTakenDate = " TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID] + " NOT IN (" + strId.toString() + ") ";
                }
            }
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
                sql_id_only += " WHERE " + whereClause;
            }

            // group by clause
            sql = sql + " GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID];
            sql_id_only = sql_id_only + " GROUP BY AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID];



            // order by clause
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE];
            sql_id_only = sql_id_only + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE];

            //    sql_id_only += ") AS UN";

            /////////////////////////////////////////////////
            StringBuffer strIdOnly = new StringBuffer();
            try {
                String strSqlSubQuery = sql_id_only;
                //    System.out.println("SQL ::: "+strSqlSubQuery);
                DBResultSet dbrs2a = DBHandler.execQueryResult(strSqlSubQuery);
                ResultSet rs_takena = dbrs2a.getResultSet();
                strIdOnly.append("");
                while (rs_takena.next()) {
                    if (!strIdOnly.toString().equals("")) {
                        strIdOnly.append(",");
                    }
                    strIdOnly.append(rs_takena.getString(1));
                }
                rs_takena.close();
            } catch (Exception ex) {
            }

            ////////////////////////////////////////////////


            //Count taken at set date
            String sql_taken = "SELECT DISTINCT TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] + ", SUM(TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + ")" + " FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS TK " + " WHERE TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] + " IN (" + (strIdOnly.toString().length() > 0 ? strIdOnly.toString() : "0") + ") " + " AND " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + ">0 ";
            if (strWhereTakenDate.length() > 0) {
                sql_taken += " AND " + strWhereTakenDate;
            }
            sql_taken += " GROUP BY TK." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID];

            //System.out.println("[SQL] Count Taken : "+sql_taken);

            //ADD
            sql += ") AS UN";

            //System.out.println("[SQL] employee.attendance.SessLeaveManagement.countSummaryAlStock = "+sql);
            //Count taken
            DBResultSet dbrs2 = DBHandler.execQueryResult(sql_taken);
            ResultSet rs_taken = dbrs2.getResultSet();

            Hashtable hastTable = new Hashtable();
            while (rs_taken.next()) {
                hastTable.put(rs_taken.getString(1), rs_taken.getString(2));
            }
            rs_taken.close();


            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                count = rs.getInt(1);

            }
            rs.close();
            return count;

        } catch (Exception e) {
            System.out.println("[ERROR] " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return count;
    }

    /**
     * @param srcLeaveManagement     
     * @return
     *     
     * @created by edhy   
     */
    public static int countSummaryAlStock(SrcLeaveManagement srcLeaveManagement) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(DISTINCT AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + ")" +
                    " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS AL " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                strLeavePeriodCondition = " AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            String strAlStatusCondition = " AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                    " = " + PstAlStockManagement.AL_STS_AKTIF;

            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }

            if (strAlStatusCondition != null && strAlStatusCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strAlStatusCondition;
                } else {
                    whereClause = strAlStatusCondition;
                }
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            System.out.println("countSummaryAlStock SQL = " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
            return result;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    /**
     * @param srcLeaveManagement     
     * @return
     *     
     * @created by Yunny   
     */
    public static int countSummaryAlStockInt(SrcLeaveManagement srcLeaveManagement) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(DISTINCT AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] + ")" +
                    " FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                strLeavePeriodCondition = " AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            /*String strAlStatusCondition = " AL."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STATUS]+
            " = " + PstAlStockManagement.AL_STS_AKTIF;*/
            String strAlStatusCondition = "";
            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }

            if (strAlStatusCondition != null && strAlStatusCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strAlStatusCondition;
                } else {
                    whereClause = strAlStatusCondition;
                }
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            //    System.out.println("countSummaryAlStockInt SQL = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
            return result;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    /**     
     * @param employeeId
     * @param limitStart
     * @param recordToGet          
     * @return
     *     
     * @created by edhy
     */
    public static Vector listDetailAlStock(long employeeId, int limitStart, int recordToGet) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] +
                    ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_LEAVE_PERIODE_ID] +
                    ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] +
                    ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] +
                    ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] +
                    ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] +
                    ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                    ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_NOTE] +
                    " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT +
                    " WHERE " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = " + employeeId +
                    " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                    " IN (" + PstAlStockManagement.AL_STS_AKTIF + ", " + PstAlStockManagement.AL_STS_NOT_AKTIF + ")" +
                    " ORDER BY " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE];

            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            //        System.out.println("listDetailAlStock SQL = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AlStockManagement alStockManagement = new AlStockManagement();
                alStockManagement.setOID(rs.getLong(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]));
                alStockManagement.setEmployeeId(rs.getLong(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]));
                alStockManagement.setLeavePeriodeId(rs.getLong(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_LEAVE_PERIODE_ID]));
                alStockManagement.setDtOwningDate(rs.getDate(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE]));
                alStockManagement.setAlQty(rs.getFloat(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY]));
                alStockManagement.setQtyUsed(rs.getFloat(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED]));
                alStockManagement.setQtyResidue(rs.getFloat(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE]));
                alStockManagement.setAlStatus(rs.getInt(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]));
                alStockManagement.setStNote(rs.getString(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_NOTE]));

                lists.add(alStockManagement);
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

    /**     
     * @param employeeId
     * @param limitStart
     * @param recordToGet          
     * @return
     *     
     * @created by yunny
     */
    public static Vector listDetailAlStockTaken(long employeeId, int limitStart, int recordToGet) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] +
                    ", " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] +
                    ", " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +
                    ", " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] +
                    " FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN +
                    " WHERE " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] +
                    " = " + employeeId +
                    " ORDER BY " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE];

            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            //         System.out.println("listDetailAlStock SQL = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AlStockTaken alStockTaken = new AlStockTaken();
                alStockTaken.setEmployeeId(rs.getLong(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]));
                alStockTaken.setOID(rs.getLong(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID]));
                alStockTaken.setTakenDate(rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]));
                alStockTaken.setTakenQty(rs.getInt(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY]));
                lists.add(alStockTaken);
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

    /**     
     * @param employeeId
     * @return
     *     
     * @created by edhy
     */
    public static int countDetailAlStock(long employeeId) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] + ")" +
                    " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT +
                    " WHERE " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = " + employeeId +
                    " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                    " IN (" + PstAlStockManagement.AL_STS_AKTIF + ", " + PstAlStockManagement.AL_STS_NOT_AKTIF + ")";

            //System.out.println("countDetailAlStock SQL = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
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

    /**     
     * @param employeeId
     * @return
     *     
     * @created by Yunny
     */
    public static int countDetailAlStockTaken(long employeeId) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] + ")" +
                    " FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN +
                    " WHERE " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] +
                    " = " + employeeId;

            System.out.println("countDetailAlStockTaken SQL = " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
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
    // -------------------------- End Process AL Stock -------------------------
    // ------------------------ Start process LL Stock --------------------------
    /**
     * @param srcLeaveManagement     
     * @param limitStart
     * @param recordToGet          
     * @return
     *     
     * @created by edhy
     */
    public static Vector listSummaryLlStock(SrcLeaveManagement srcLeaveManagement, int limitStart, int recordToGet) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", SUM(LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY] + ")" +
                    ", SUM(LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_USED] + ")" +
                    ", SUM(LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE] + ")" +
                    " FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " AS LL " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                strLeavePeriodCondition = " LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            String strLlStatusCondition = " LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                    " = " + PstLLStockManagement.LL_STS_AKTIF;

            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }

            if (strLlStatusCondition != null && strLlStatusCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLlStatusCondition;
                } else {
                    whereClause = strLlStatusCondition;
                }
            }

            //update by ayu
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            } else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            // group by clause
            sql = sql + " GROUP BY LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID];

            // order by clause
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE];

            // limit
            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("listSummaryLlStock SQL = " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector ls = new Vector(1, 1);

                LLStockManagement llStockManagement = new LLStockManagement();
                llStockManagement.setEmployeeId(rs.getLong(1));
                llStockManagement.setLLQty(rs.getInt(4));
                llStockManagement.setQtyUsed(rs.getInt(5));
                llStockManagement.setQtyResidue(rs.getInt(6));
                ls.add(llStockManagement);

                Employee emp = new Employee();
                emp.setEmployeeNum(rs.getString(2));
                emp.setFullName(rs.getString(3));
                ls.add(emp);

                lists.add(ls);
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

    /**
     * @param srcLeaveManagement     
     * @param limitStart
     * @param recordToGet          
     * @return
     *     
     * @created by edhy, edit bya artha
     */
    public static Vector listSummaryLlStockValid(SrcLeaveManagement srcLeaveManagement, int limitStart, int recordToGet) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", SUM(LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY] + ")" +
                    " FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " AS LL " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);
                dtStartDate.setYear(dtStartDate.getYear() - 6);
                strLeavePeriodCondition = " LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            String strLlStatusCondition = " LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                    " = " + PstLLStockManagement.LL_STS_AKTIF;

            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }

            if (strLlStatusCondition != null && strLlStatusCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLlStatusCondition;
                } else {
                    whereClause = strLlStatusCondition;
                }
            }

            //update by ayu
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            } else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            // group by clause
            sql = sql + " GROUP BY LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID];

            // order by clause
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE];

            // limit
            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("listSummaryLlStock SQL = " + sql);

            Hashtable hLlTaken = new Hashtable();
            hLlTaken = listSummaryLlStockTaken(srcLeaveManagement, limitStart, recordToGet);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector ls = new Vector(1, 1);

                LLStockManagement llStockManagement = new LLStockManagement();
                llStockManagement.setEmployeeId(rs.getLong(1));
                llStockManagement.setLLQty(rs.getInt(4));
                int iTaken = 0;
                try {
                    String strTaken = (String) hLlTaken.get(rs.getString(1));
                    iTaken = Integer.parseInt(strTaken);
                } catch (Exception ex) {
                    iTaken = 0;
                }
                llStockManagement.setQtyUsed(iTaken);
                llStockManagement.setQtyResidue(rs.getInt(4) - iTaken);
                ls.add(llStockManagement);

                Employee emp = new Employee();
                emp.setEmployeeNum(rs.getString(2));
                emp.setFullName(rs.getString(3));
                ls.add(emp);

                lists.add(ls);
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

    /**
     * Mencari banyak lll yang sudah taken
     */
    public static Hashtable listSummaryLlStockTaken(SrcLeaveManagement srcLeaveManagement, int limitStart, int recordToGet) {
        Hashtable hLlTaken = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                    ", SUM(TK." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY] + ")" +
                    " FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " AS LL " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS TK " +
                    " ON LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] +
                    " = TK." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);
                dtStartDate.setYear(dtStartDate.getYear() - 6);

                strLeavePeriodCondition = " LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"" +
                        " AND TK." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] +
                        " < \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            String strLlStatusCondition = " LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                    " = " + PstLLStockManagement.LL_STS_AKTIF;

            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }

            if (strLlStatusCondition != null && strLlStatusCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLlStatusCondition;
                } else {
                    whereClause = strLlStatusCondition;
                }
            }

            //update by ayu
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            } else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            // group by clause
            sql = sql + " GROUP BY LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID];

            // order by clause
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE];

            // limit
            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("listSummaryLlStock Cek Taken LL SQL = " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                hLlTaken.put(rs.getString(1), rs.getString(2));
            }
            rs.close();
            return hLlTaken;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return hLlTaken;
    }

    /**
     * @param srcLeaveManagement     
     * @return
     *     
     * @created by edhy   
     */
    public static int countSummaryLlStock(SrcLeaveManagement srcLeaveManagement) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(DISTINCT LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + ")" +
                    " FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " AS LL " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                strLeavePeriodCondition = " LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            String strLlStatusCondition = " LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                    " = " + PstLLStockManagement.LL_STS_AKTIF;

            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }

            if (strLlStatusCondition != null && strLlStatusCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLlStatusCondition;
                } else {
                    whereClause = strLlStatusCondition;
                }
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

//            System.out.println("countSummaryLlStock SQL = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
            return result;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    /**     
     * @param employeeId
     * @param limitStart
     * @param recordToGet          
     * @return
     *     
     * @created by edhy
     */
    public static Vector listDetailLlStock(long employeeId, int limitStart, int recordToGet) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] +
                    ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                    ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LEAVE_PERIODE_ID] +
                    ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] +
                    ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED] +
                    ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY] +
                    ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE] +
                    ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_USED] +
                    ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                    ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_NOTE] +
                    " FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT +
                    " WHERE " + PstLLStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = " + employeeId +
                    " AND " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                    " IN (" + PstLLStockManagement.LL_STS_AKTIF + ", " + PstLLStockManagement.LL_STS_NOT_AKTIF + ")" +
                    " ORDER BY " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE];

            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

//            System.out.println("listDetailLlStock SQL = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                LLStockManagement llStockManagement = new LLStockManagement();
                llStockManagement.setOID(rs.getLong(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID]));
                llStockManagement.setEmployeeId(rs.getLong(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]));
                llStockManagement.setLeavePeriodeId(rs.getLong(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LEAVE_PERIODE_ID]));
                llStockManagement.setDtOwningDate(rs.getDate(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE]));
                llStockManagement.setEntitled(rs.getInt(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED]));
                llStockManagement.setLLQty(rs.getInt(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY]));
                llStockManagement.setQtyUsed(rs.getInt(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_USED]));
                llStockManagement.setQtyResidue(rs.getInt(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE]));
                llStockManagement.setLLStatus(rs.getInt(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS]));
                llStockManagement.setStNote(rs.getString(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_NOTE]));

                lists.add(llStockManagement);
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

    /**     
     * @param employeeId
     * @return
     *     
     * @created by edhy
     */
    public static int countDetailLlStock(long employeeId) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] + ")" +
                    " FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT +
                    " WHERE " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                    " = " + employeeId +
                    " AND " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                    " IN (" + PstLLStockManagement.LL_STS_AKTIF + ", " + PstLLStockManagement.LL_STS_NOT_AKTIF + ")";

//            System.out.println("countDetailLlStock SQL = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
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
    // ---------------------- End process LL stock -----------------------------
    /**     
     * @param departmentId
     * @param yearFrom
     * @param yearTo
     * @return
     *     
     * @created by edhy
     */
    public static Vector getEmployeeLeave(long departmentId, long sectionId, long levelId, String stCurrYear, String yearFrom, String yearTo) {
        Vector lists = new Vector(1, 1);
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT distinct(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")" +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEAVE_STATUS] +
                    ", DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] +
                    ", POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION] +
                    ", DSM." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED] +
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    //" INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    //" ON DSM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + 
                    //" = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    " INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " AS DEP " +
                    " ON  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    "= DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +
                    " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS POS" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] +
                    " = POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] +
                    " LEFT JOIN " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS DSM " +
                    " ON DSM." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE (YEAR(" + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + "))" +
                    " BETWEEN '" + yearFrom + "' AND '" + yearTo + "'" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] +
                    " = 0 " +
                    " AND (YEAR(" + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + "))" +
                    " = '" + stCurrYear + "'";
            //" AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
            //" IN (" + PstDpStockManagement.DP_STS_NOT_AKTIF + ", " + PstDpStockManagement.DP_STS_AKTIF + ")" +


            String whereClause = "";

            if (departmentId != 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + departmentId;
                sql = sql + whereClause;
            }
            if (sectionId != 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + sectionId;
                sql = sql + whereClause;
            }
            if (levelId != 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] +
                        " = " + levelId;
                sql = sql + whereClause;
            }

            sql = sql + " GROUP BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY " + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE];

            //       System.out.println("SessLeaveManagement.getEmployeeLeave... = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vect = new Vector(1, 1);
                /*DpStockManagement dpStockManagement = new DpStockManagement(); 
                dpStockManagement.setQtyResidue(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]));                                
                lists.add(dpStockManagement);*/

                Employee employee = new Employee();
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                employee.setDepartmentId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                employee.setLeaveStatus(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_LEAVE_STATUS]));
                vect.add(employee);

                Department department = new Department();
                department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                vect.add(department);

                Position position = new Position();
                position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(position);

                AlStockManagement alStockManagement = new AlStockManagement();
                alStockManagement.setEntitled(rs.getInt(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED]));
                vect.add(alStockManagement);


                lists.add(vect);
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

    //By artha ----> report Hard Rock
    /**
     * @desc Mencari banyak dp yang valid sampai awal period
     * @param 
     */
    /*
    SELECT EMP.FULL_NAME,MAN.EMPLOYEE_ID, SUM(MAN.DP_QTY), SUM(TK.TAKEN_QTY) FROM hr_dp_stock_management AS MAN LEFT JOIN hr_dp_stock_taken AS TK  ON MAN.DP_STOCK_ID =TK.DP_STOCK_ID 
    INNER JOIN hr_employee AS EMP ON EMP.EMPLOYEE_ID = MAN.EMPLOYEE_ID
    WHERE MAN.EXPIRED_DATE > '2008-12-1'
    GROUP BY MAN.EMPLOYEE_ID
     */
    public static Vector listSummaryDpStockValid(SrcLeaveManagement srcLeaveManagement, int limitStart, int recordToGet) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", SUM(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + ")" +
                    ", SUM(TK." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + ")" +
                    " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DP " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " LEFT JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS TK " +
                    " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                    " = TK." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                strLeavePeriodCondition = " DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            } else if (srcLeaveManagement.getPeriodId() > 0) {
                Period period = PstPeriod.fetchExc(srcLeaveManagement.getPeriodId());
                Date dtStartDate = (Date) period.getStartDate().clone();
                Date dtEndDate = (Date) period.getEndDate().clone();
                dtStartDate.setMonth(dtStartDate.getMonth() - 3);
                strLeavePeriodCondition = " DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }


            //update by ayu
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            } else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            // add group by
            sql = sql + " GROUP BY DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];

            // add whereClause 
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];

            // add limit
            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("listSummaryDpStockValid ::::::::::::: SQL = " + sql);
            Hashtable hDpTaken = new Hashtable();
            hDpTaken = listDpStockValidTaken(srcLeaveManagement, limitStart, recordToGet);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector ls = new Vector(1, 1);

                DpStockManagement dpStockManagement = new DpStockManagement();
                dpStockManagement.setEmployeeId(rs.getLong(1));
                dpStockManagement.setiDpQty(rs.getInt(4));

                int iTaken = 0;
                try {
                    String strTaken = "";
                    strTaken = (String) hDpTaken.get(rs.getString(1));
                    iTaken = Integer.parseInt(strTaken);
                } catch (Exception ex) {
                    iTaken = 0;
                }

                dpStockManagement.setQtyUsed(iTaken);
                dpStockManagement.setQtyResidue(rs.getInt(4) - iTaken);
                ls.add(dpStockManagement);

                Employee emp = new Employee();
                emp.setEmployeeNum(rs.getString(2));
                emp.setFullName(rs.getString(3));
                ls.add(emp);

                lists.add(ls);
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

    public static Hashtable listDpStockValidTaken(SrcLeaveManagement srcLeaveManagement, int limitStart, int recordToGet) {
        Hashtable hDpStockTaken = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", SUM(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + ")" +
                    ", SUM(TK." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + ")" +
                    " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DP " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " LEFT JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS TK " +
                    " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                    " = TK." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strCategoryCondition = "";
            if (srcLeaveManagement.getEmpCatId() != 0) {
                strCategoryCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] +
                        " = " + srcLeaveManagement.getEmpCatId();
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strSectionCondition = "";
            if (srcLeaveManagement.getEmpSectionId() != 0) {
                strSectionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpSectionId();
            }

            String strPositionCondition = "";
            if (srcLeaveManagement.getEmpPosId() != 0) {
                strPositionCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + srcLeaveManagement.getEmpPosId();
            }

            String strLeavePeriodCondition = "";
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                strLeavePeriodCondition = " DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            } else if (srcLeaveManagement.getPeriodId() > 0) {
                Period period = PstPeriod.fetchExc(srcLeaveManagement.getPeriodId());
                Date dtStartDate = (Date) period.getStartDate().clone();
                Date dtEndDate = (Date) period.getEndDate().clone();
                dtStartDate.setMonth(dtStartDate.getMonth() - 3);
                strLeavePeriodCondition = " DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"" +
                        "  AND TK." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] +
                        " <= \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strCategoryCondition != null && strCategoryCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCategoryCondition;
                } else {
                    whereClause = strCategoryCondition;
                }
            }


            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strSectionCondition != null && strSectionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSectionCondition;
                } else {
                    whereClause = strSectionCondition;
                }
            }

            if (strPositionCondition != null && strPositionCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strPositionCondition;
                } else {
                    whereClause = strPositionCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }
            //update by ayu
            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            } else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            // add group by
            sql = sql + " GROUP BY DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];

            // add whereClause 
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];

            // add limit
            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                hDpStockTaken.put(rs.getString(1), rs.getString(5));
            }
            rs.close();
            return hDpStockTaken;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return hDpStockTaken;
    }

    /**
     * @AUTHOR  : ROY ANDIKA
     * @CREATED : 20-04-2010
     * @DESC    : UNTUK MENDAPATKAN DP STOCK MANAGEMENT BERDASARKAN DEPARTMENT
     * @PARAM   : EMPLOYEE ID
     */
    public static Vector getDpStockByDepartment(long departmentId) {

        DBResultSet dbrs = null;
        Vector result = new Vector();
        try {
            String sql = "SELECT DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] + " FROM " +
                    PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " DP INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON " +
                    "DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " +
                    "EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " WHERE " +
                    "EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + departmentId + " AND DP." +
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + " = " + PstDpStockManagement.DP_STS_AKTIF;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                DpStockManagement dpStockManagement = new DpStockManagement();
                dpStockManagement.setOID(rs.getLong(1));
                result.add(dpStockManagement);
            }

            return result;

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return null;
    }

    /**
     * @AUTHOR  : ROY ANDIKA
     * @CREATED : 20-04-2010
     * @DESC    : UNTUK MENDAPATKAN DP STOCK MANAGEMENT BERDASARKAN DEPARTMENT
     * @PARAM   : EMPLOYEE ID
     */
    public static int getTotExpDpStockByDepartment(long departmentId) {

        Vector result = getDpStockByDepartment(departmentId);

        if (result != null && result.size() > 0) {

            int sum = 0;

            for (int i = 0; i < result.size(); i++) {

                DpStockManagement dpStockManagement = (DpStockManagement) result.get(i);

                int qtyExpired = totalExpiredDpDetail(dpStockManagement.getOID());

                sum = sum + qtyExpired;
            }

            return sum;

        }

        return 0;
    }

    /**
     * @AUTHOR  : ROY ANDIKA
     * @CREATED : 16-04-2010
     * @DESC    : UNTUK MENDAPATKAN TOTAL EXPIRED DP BERDASARKAN EMPLOYEE
     * @PARAM   : EMPLOYEE ID
     */
    public static int totalExpiredDp(long employee_id) {

        String whereDp = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + " = " + PstDpStockManagement.DP_STS_AKTIF + " AND " +
                PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + employee_id;

        Vector listDp = PstDpStockManagement.list(0, 0, whereDp, null);

        int sum = 0;

        if (listDp != null && listDp.size() > 0) {

            for (int i = 0; i < listDp.size(); i++) {

                DpStockManagement dpStockManagement = (DpStockManagement) listDp.get(i);

                int qtyExpired = totalExpiredDpDetail(dpStockManagement.getOID());

                sum = sum + qtyExpired;
            }

            return sum;

        }

        return 0;
    }

    /**
     * @AUTHOR  : ROY ANDIKA
     * @CREATED : 16-04-2010
     * @DESC    : UNTUK MENDAPATKAN TOTAL EXPIRED DP BERDASARKAN DP STOCK
     * @PARAM   : DP STOCK ID
     */
    public static int totalExpiredDpDetail(long dpStockId) {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT SUM(" + PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_EXPIRED_QTY] + ") FROM " +
                    PstDpStockExpired.TBL_HR_DP_STOCK_EXPIRED + " WHERE " +

        
                    PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_DP_STOCK_ID] + " = " + dpStockId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                int totalExpired = rs.getInt(1);
                return totalExpired;
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }

    public static int getEligibleDp(long employee_id, long dp_stock_id) {

        DBResultSet dbrs = null;

        try {
            String sql = "SELECT COUNT(dpt." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY] + ") FROM " +
                    PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " dpt INNER JOIN " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " app " +
                    " ON app." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] +
                    " = dpt." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] + " WHERE " +
                    " app." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = " + employee_id +
                    " AND app." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + " = " + dp_stock_id;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                int qty = 0;
                qty = rs.getInt(1);
                return qty;
            }


        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }

    public static int getEligibleDp(long employeeId, SrcLeaveManagement srcLeaveManagement) {

        String strLeavePeriodCondition = "";

        //select sum(dpt.taken_qty) from hr_leave_application app inner join 
        //hr_dp_Stock_taken dpt on app.leave_application_id = dpt.leave_application_id 
        //inner join hr_dp_stock_management dpm on dpt.dp_stock_id = dpm.dp_stock_id;
        
        try {

            /*String sql = "SELECT SUM(dpt."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]+") FROM "+
                    PstLeaveApplication.TBL_LEAVE_APPLICATION+" app INNER JOIN "+
                    PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN+" dpt ON app."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+
                    " = dpt."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID]+" INNER JOIN "+
                    PstDpStockManagement.fieldNames[PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT]+" dpm ON dpt."+PstDpStockTaken.fieldNames[PstDpSt];
            */
        
            
            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                strLeavePeriodCondition = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

        } catch (Exception E) {
            System.out.println("Exeption " + E.toString());
        }
        return 0;
    }

    public static Vector listSummaryDpManagement(SrcLeaveManagement srcLeaveManagement, int limitStart, int recordToGet) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", SUM(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + ")" +
                    ", SUM(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] + ")" +
                    ", SUM(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE] + ")" +
                    " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DP " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strDivCondition = "";
            if (srcLeaveManagement.getDivisionId() != 0) {
                strDivCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] +
                        " = " + srcLeaveManagement.getDivisionId();
            }
            
            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strLeavePeriodCondition = "";

            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                strLeavePeriodCondition = " DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }
            
            if (strDivCondition != null && strDivCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDivCondition;
                } else {
                    whereClause = strDivCondition;
                }
            }

            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            } else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =0 ";
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            // add group by
            sql = sql + " GROUP BY DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];

            // add whereClause 
            sql = sql + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];

            // add limit
            if (!(limitStart == 0 && recordToGet == 0)) {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("List Summary Dp Stock Management SQL = " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                Vector ls = new Vector(1, 1);

                DpStockManagement dpStockManagement = new DpStockManagement();
                dpStockManagement.setEmployeeId(rs.getLong(1));
                dpStockManagement.setiDpQty(rs.getFloat(4));

                dpStockManagement.setQtyUsed(rs.getFloat(5));
                //dpStockManagement.setQtyResidue(rs.getInt(6));
                ls.add(dpStockManagement);

                Employee emp = new Employee();
                emp.setEmployeeNum(rs.getString(2));
                emp.setFullName(rs.getString(3));
                ls.add(emp);

                lists.add(ls);
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
    
    /**
     * @Author Roy Andila
     * @param srcLeaveManagement
     * @param start
     * @param fns
     * @return
     */
    public static Vector listDpManagement(SrcLeaveManagement srcLeaveManagement,Date start,Date fns){
        
        DBResultSet dbrs = null;
        
        try{
            
            String sql = "SELECT DISTINCT DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", SUM(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + ")" +
                    ", SUM(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] + ")" +
                    ", SUM(DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE] + ")" +
                    " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DP " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                    " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            
            String strNameCondition = "";
            if ((srcLeaveManagement.getEmpName() != null) && (srcLeaveManagement.getEmpName().length() > 0)) {
                Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                if (vectName != null && vectName.size() > 0) {
                    strNameCondition = " (";
                    for (int i = 0; i < vectName.size(); i++) {
                        String str = (String) vectName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNameCondition = strNameCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                    " LIKE '%" + str.trim() + "%' ";
                        } else {
                            strNameCondition = strNameCondition + str.trim();
                        }
                    }
                    strNameCondition = strNameCondition + ")";
                }
            }

            String strNumCondition = "";
            if ((srcLeaveManagement.getEmpNum() != null) && (srcLeaveManagement.getEmpNum().length() > 0)) {
                Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                if (vectNum != null && vectNum.size() > 0) {
                    strNumCondition = " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            strNumCondition = strNumCondition + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                    " LIKE '" + str.trim() + "%' ";
                        } else {
                            strNumCondition = strNumCondition + str.trim();
                        }
                    }
                    strNumCondition = strNumCondition + ")";
                }
            }

            String strDepartmentCondition = "";
            if (srcLeaveManagement.getEmpDeptId() != 0) {
                strDepartmentCondition = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + srcLeaveManagement.getEmpDeptId();
            }

            String strLeavePeriodCondition = "";

            if (srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar gre = Calendar.getInstance();
                gre.setTime(selectedDate);
                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), maxDay);

                strLeavePeriodCondition = " DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                        " BETWEEN \"" + Formater.formatDate(dtStartDate, "yyyy-MM-dd") +
                        " \" AND \"" + Formater.formatDate(dtEndDate, "yyyy-MM-dd") + "\"";
            }

            String whereClause = "";
            if (strNameCondition != null && strNameCondition.length() > 0) {
                whereClause = strNameCondition;
            }

            if (strNumCondition != null && strNumCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strNumCondition;
                } else {
                    whereClause = strNumCondition;
                }
            }

            if (strDepartmentCondition != null && strDepartmentCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strDepartmentCondition;
                } else {
                    whereClause = strDepartmentCondition;
                }
            }

            if (strLeavePeriodCondition != null && strLeavePeriodCondition.length() > 0) {
                if (whereClause != null && whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                } else {
                    whereClause = strLeavePeriodCondition;
                }
            }
            
            
        }catch(Exception E){
            System.out.println("[ exception ] "+E.toString());
        }finally{
            try{
                DBResultSet.close(dbrs);
            }catch(Exception E){
                System.out.println("[ exception ] "+E.toString());
            }
        }
        
        return null;
    }
    
    /**
     * @Author  ROy Andika
     * @param   StartDate
     * @param   EndDate
     * @return
     */
    
    public static float dpQty(Date StartDate,Date EndDate){
        
        DBResultSet dbrs = null;
        
        try{
            
            String sql = "SELECT SUM("+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]+") FROM "+
                    PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+" WHERE "+
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+" >= '"+Formater.formatDate(StartDate,"yyyy-MM-dd")+"' AND "+
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+" <= '"+Formater.formatDate(EndDate,"yyyy-MM-dd")+"' AND "+
                    " GROUP BY "+
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];
            
            System.out.println("SQL GET SUM DP QTY = " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()){
                
                float sum = rs.getFloat(1);
                return sum;
                
            }
            
        }catch(Exception E){
            System.out.println("Exception "+E.toString());
        }finally{
            DBResultSet.close(dbrs);
        }
        
        return 0;
    }
    
}
