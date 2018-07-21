package com.dimata.harisma.session.attendance;

// import core java package
import java.util.Vector;
import java.util.Date;
import java.util.Calendar;
import java.sql.ResultSet;

// import dimata package
import com.dimata.util.LogicParser;
import com.dimata.util.Formater;

// import qdep package
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.DBHandler;

// import project package
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.LeavePeriod;
import com.dimata.harisma.entity.masterdata.PstLeavePeriod;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.DpStockReporting;
import com.dimata.harisma.entity.attendance.PstDpStockManagement;
import com.dimata.harisma.entity.attendance.PstDpStockTaken;
import com.dimata.harisma.entity.attendance.PstDpStockExpired;
import com.dimata.harisma.entity.search.SrcLeaveManagement;

public class DPMontly {
    
    /**
     * @param text
     * @return
     */
    private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for(int i =0;i < vector.size();i++) {
            String code =(String)vector.get(i);
            if(((vector.get(vector.size()-1)).equals(LogicParser.SIGN))&&
            ((vector.get(vector.size()-1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size()-1);
            }
        }
        return vector;
    }
    
    /** gadnyana
     * utk get data dp sesuai dengan parameter periode dan department
     * @param oidPeriod
     * @param oidDept
     * @return
     */
    public Vector getDpMonthly(long oidPeriod, long oidDept) {
        DBResultSet dbrs = null;
        Vector list = new Vector();
        try{
            LeavePeriod leavePeriod = new LeavePeriod();
            leavePeriod = PstLeavePeriod.fetchExc(oidPeriod);
            
            String sQL = "SELECT "+
            " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+", " +
            " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+", " +
            " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+", " +
            " SUM(DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]+") AS QTY," +
            " SUM(DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]+")AS USED," +
            " SUM(DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]+") AS RESIDUE" +
            " FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+" DP" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP ON " +
            " DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
            " WHERE ";
            if(oidDept!=0){
                sQL = sQL +" EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+oidDept+" AND ";
            }            
            
            // edited by edhy
            sQL = sQL + " DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
            " = " + PstDpStockManagement.DP_STS_AKTIF +
            //update ayu
            " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + 
            " = 0 " +
            " GROUP BY DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+" ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            
            System.out.println("sql "+sQL);
            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                Vector vt = new Vector();
                Employee emp = new Employee();
                emp.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                
                vt.add(emp);
                vt.add("0"); // untuk last year
                vt.add(""+rs.getInt("QTY"));
                vt.add(""+rs.getInt("USED"));
                vt.add(""+rs.getInt("RESIDUE"));
                
                list.add(vt);
            }
            rs.close();
            DBResultSet.close(dbrs);
            
            sQL = "SELECT "+
            " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+", " +
            " SUM(DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]+") AS RESIDUE" +
            " FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+" DP" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP ON " +
            " DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
            " WHERE EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+oidDept+
            //update ayu
            " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + 
            " = 0 " +
            //
            " AND DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+" < '"+Formater.formatDate(leavePeriod.getStartDate(),"yyyy-MM-dd")+"'"+
            " AND (DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+"!= "+PstDpStockManagement.DP_STS_EXPIRED+
            " AND DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+"!= "+PstDpStockManagement.DP_STS_EXPIRED+
            " ) GROUP BY DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+" ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            
            dbrs = DBHandler.execQueryResult(sQL);
            rs = dbrs.getResultSet();
            while(rs.next()){
                list = equalDpCurrentLast(list, rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), rs.getInt("RESIDUE"));
            }
            rs.close();
        }catch(Exception e){
            System.out.println("Err >>getDpMonthly : "+e.toString());
        }finally{
            DBResultSet.close(dbrs);
        }
        return list;
    }
    
    /** gadnyana
     * untuk mencari jumlah residu yang terakhir
     * dengan acuan < dari tanggal periode parameter
     * @param oidPeriod
     * @return
     */
    public Vector getDp(long oidPeriod) {
        DBResultSet dbrs = null;
        Vector list = new Vector();
        try {
            LeavePeriod leavePeriod = new LeavePeriod();
            leavePeriod = PstLeavePeriod.fetchExc(oidPeriod);
            String sQL = "SELECT "+
            " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+", " +
            " SUM(DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]+") AS RESIDUE" +
            " FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+" DP" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP ON " +
            " DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
            
            // edited by edhy
            " WHERE DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+" < '"+Formater.formatDate(new Date(),"yyyy-MM-dd")+"'"+
            //update by ayu
            " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + 
            " = 0 " +
            //
            " AND ( DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+"!= "+PstDpStockManagement.DP_STS_EXPIRED+
            " AND DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+"!= "+PstDpStockManagement.DP_STS_NOT_AKTIF+
            ") GROUP BY DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+" ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            
            
//            System.out.println("...................::::::::::sql DP : " + sQL);
            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                Vector vt = new Vector(1,1);
                vt.add(""+rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                vt.add(""+rs.getInt("RESIDUE"));
                
                list.add(vt);
            }
            rs.close();
        }
        catch(Exception e) {
            System.out.println("ERR : getDp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs); 
        }
        return list;
    }
    
    
    /**
     * untuk mencari jumlah residu yang terakhir
     * dengan acuan < dari tanggal periode parameter
     * @param oidPeriod
     * @return
     * @created by Edhy
     */
    public static Vector getDpStock() {
        DBResultSet dbrs = null;
        Vector list = new Vector(); 
        try {
            String sQL = "SELECT "+
            " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+", " +
            " SUM(DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]+") AS RESIDUE" +
            " FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+" DP" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP ON " +
            " DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
            " WHERE DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+" < '"+Formater.formatDate(new Date(),"yyyy-MM-dd")+"'"+
            //update by ayu
            " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + 
            " = 0 " +
            //
            " AND ( DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+"!= "+PstDpStockManagement.DP_STS_EXPIRED+
            " AND DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+"!= "+PstDpStockManagement.DP_STS_NOT_AKTIF+
            ") GROUP BY DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+" ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
          
            
//            System.out.println("...................::::::::::sql DP : " + sQL); 
            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                Vector vt = new Vector(1,1);
                vt.add(""+rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                vt.add(""+rs.getInt("RESIDUE"));
                
                list.add(vt);
            }
            rs.close();
        }
        catch(Exception e) {
            System.out.println("ERR : getDpStock : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return list;
    }
    
    
    /**
     * untuk mencari jumlah residu yang terakhir
     * dengan acuan < dari tanggal periode parameter
     * @param oidPeriod
     * @return
     * @created by Edhy
     */
    public static Vector getAdvanceDpStock() {
        DBResultSet dbrs = null;
        Vector list = new Vector();
        try {
            String sQL = "SELECT "+
            " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+", " +
            " SUM(DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]+") AS RESIDUE" +
            " FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+" DP" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" EMP ON " +
            " DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
            " WHERE DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+" < '"+Formater.formatDate(new Date(),"yyyy-MM-dd")+"'"+
            " AND ( DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+"!= "+PstDpStockManagement.DP_STS_EXPIRED+
            " AND DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+"!= "+PstDpStockManagement.DP_STS_NOT_AKTIF+
            ") GROUP BY DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+" ORDER BY EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            
            
            //System.out.println("...................::::::::::sql DP : " + sQL);
            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                Vector vt = new Vector(1,1);
                vt.add(""+rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                vt.add(""+rs.getInt("RESIDUE"));
                
                list.add(vt);
            }
            rs.close();
        }
        catch(Exception e) {
            System.out.println("ERR : getDpStock : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return list;
    }
    
    
    /**
     * @param empOid
     * @return
     * @created by Edhy
     */
    public int getCurrentDPStock(long empOid) {
        DBResultSet dbrs = null;
        int result = 0;
        try {
            String sQL = "SELECT SUM(" + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE] + ")" +
            " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT +
            " WHERE " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
            " = " + empOid +
            " GROUP BY " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];
            
            System.out.println("getCurrentDPStock.sql : " + sQL);
            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
                break;
            }
            rs.close();
        }
        catch (Exception e) {
            System.out.println("Exc on " + getClass().getName() + " : " + e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }
    
    /**gadnyana
     * penggabungan qty sisa last year dan
     * di gabung dengan current year
     * @param vect
     * @param oidEmp
     * @param qty
     * @return
     */
    public Vector equalDpCurrentLast(Vector vect, long oidEmp, int qty){
        if(vect!=null && vect.size()>0){
            for(int k=0;k<vect.size();k++){
                Vector vt = (Vector)vect.get(k);
                Employee emp = (Employee)vt.get(0);
                if(oidEmp==emp.getOID()){
                    vt.setElementAt(""+qty,1);
                    vect.setElementAt(vt,k);
                    break;
                }
            }
        }
        return vect;
    }
    
    
    /** gadnyana
     * method yang di panggil dari jsp,
     * ini proses nya yang mencari dp yang
     * sesuai dengan periode parameter
     * @return vector
     */
    public static Vector prosessGetdp(long oidPeriod){
        Vector vt = new Vector(1,1);
        try{
            if(oidPeriod!=0){
                Period period = PstPeriod.fetchExc(oidPeriod);
                LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(period.getStartDate());
                DPMontly dp = new DPMontly();
                vt = dp.getDp(leavePeriod.getOID());
            }
        }catch(Exception e){
            System.out.println("ERR > prosessGetdp : "+e.toString());
        }
        return vt;
    }
    
    
    // -------------- Start DP Reporting --------------------
    /**
     * @param srcLeaveManagement
     * @return
     * @created by Edhy
     */
    synchronized public static Vector listDpStockReport(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_dp_stock_temp";  //"HR_DP_STOCK_TEMP";
        DBResultSet dbrs = null;
        Vector result = new Vector();
        try {
            // empty table Dp stock temporary table    
            int status = emptyTemp();
            
            // add data into Dp stock temporary table (prev, earn, used and expr)
            status = addPrevToTemp(srcLeaveManagement);
            status = addEarnToTemp(srcLeaveManagement);
            status = addUsedToTemp(srcLeaveManagement);
            status = addExprToTemp(srcLeaveManagement);
            
            String sQL = "SELECT PAYROLL, NAME, SUM(PREV_AMOUNT), SUM(EARNED), SUM(USED), SUM(EXPIRED) "+
            " FROM " + tblDpReportName +
            
            " GROUP BY PAYROLL";
            
            System.out.println("listDpStockReport SQL : " + sQL);
            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                DpStockReporting objDpStockReporting = new DpStockReporting();
                
                objDpStockReporting.setPayroll(rs.getString(1));
                objDpStockReporting.setName(rs.getString(2));
                objDpStockReporting.setPrevAmount(rs.getInt(3));
                objDpStockReporting.setEarn(rs.getInt(4));
                objDpStockReporting.setUsed(rs.getInt(5));
                objDpStockReporting.setExpired(rs.getInt(6));
                
                result.add(objDpStockReporting);
            }
            
            // empty table Dp stock temporary table
            status = emptyTemp();
            rs.close();
        }
        catch(Exception e) {
            System.out.println("Exc when listDpStockReport : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    /**
     * @param srcLeaveManagement
     * created by Edhy
     */
    synchronized public static int emptyTemp() {
        String tblDpReportName = "hr_dp_stock_temp";  //"HR_DP_STOCK_TEMP";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "DELETE FROM " + tblDpReportName;
            System.out.println("emptyTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc emptyTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }
    

    /**
     * @param srcLeaveManagement
     * created by Edhy
     */
    synchronized public static int emptyTempPrev() {
        String tblDpReportName = "hr_dp_stock_temp_prev"; //"HR_DP_STOCK_TEMP_PREV";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "DELETE FROM " + tblDpReportName;
            System.out.println("emptyTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc emptyTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }
    
    /**
     * get earned DP in current period per employee based on parameter in search object
     * and then insert them into temporary table called "HR_DP_STOCK_TEMP"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */
    synchronized public static int addEarnToTempPrev(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName =  "hr_dp_stock_temp_prev"; //"HR_DP_STOCK_TEMP_PREV";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", SUM(DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]+")" +
            ", 0" + // used
            ", 0" + // expired
            " FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+" AS DP" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP" +
            " ON DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            
            String strCommCondition = genCommonDpWhereClause(srcLeaveManagement);            
            
            String strStatusCondition = "DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                                        " <> "+PstDpStockManagement.DP_STS_NOT_AKTIF;                         
            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar cal = Calendar.getInstance();
                cal.setTime(selectedDate);                
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);                
                strLeavePeriodCondition = " DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+
                " < \""+Formater.formatDate(dtStartDate,"yyyy-MM-dd") + "\"";                
            }
            
            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strStatusCondition!=null && strStatusCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strStatusCondition;
                }
                else {
                    whereClause = strStatusCondition;
                }
            }
            
            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }
            
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            

            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];            
            
            System.out.println("addEarnToTempPrev SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addEarnToTempPrev : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }
    
    
    /**
     * get used DP in current period per employee based on parameter in search object
     * and then insert them into temporary table called "HR_DP_STOCK_TEMP"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */
    synchronized public static int addUsedToTempPrev(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_dp_stock_temp_prev";  //"HR_DP_STOCK_TEMP_PREV";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+            
            ", 0" + // earned
            ", SUM(TAKEN."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]+")" +
            ", 0" + // expired                    
            " FROM "+PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
            " INNER JOIN "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS TAKEN" +
            " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
            " = TAKEN."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID];
            
            /*
            " FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DP" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
            " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
            " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +            
            " INNER JOIN "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS TAKEN" +
            " ON DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+
            " = TAKEN."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID];
            */
            
            String strCommCondition = genCommonDpWhereClause(srcLeaveManagement);                                                            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod()!=null)
            {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar cal = Calendar.getInstance();
                cal.setTime(selectedDate);                
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);                
                strLeavePeriodCondition = " TAKEN."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+
                " < \""+Formater.formatDate(dtStartDate,"yyyy-MM-dd") + "\"";                
            }
            
            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }
            
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
             
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY TAKEN."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID];
            
            
            System.out.println("addUsedToTempPrev SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addUsedToTempPrev : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }
    
    /**
     * get expired DP in current period per employee based on parameter in search object
     * and then insert them into temporary table called "HR_DP_STOCK_TEMP"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addExprToTempPrev(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName =  "hr_dp_stock_temp_prev"; //"HR_DP_STOCK_TEMP_PREV";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", 0" + // earned
            ", 0" + // used            
            ", SUM(EXP."+PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_EXPIRED_QTY]+")" +            
            " FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DP" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
            " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
            " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +            
            " INNER JOIN "+PstDpStockExpired.TBL_HR_DP_STOCK_EXPIRED + " AS EXP" +
            " ON DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+
            " = EXP."+PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_DP_STOCK_ID];
            
            String strCommCondition = genCommonDpWhereClause(srcLeaveManagement);                                                            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod()!=null)
            {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar cal = Calendar.getInstance();
                cal.setTime(selectedDate);                
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);                
                strLeavePeriodCondition = " EXP."+PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_EXPIRED_DATE]+
                " < \""+Formater.formatDate(dtStartDate,"yyyy-MM-dd") + "\"";                  
            }
            
            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }     
            
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];
            
            
            System.out.println("addExprToTempPrev SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addExprToTempPrev : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }

    
    /**
     * get stock DP in previous period per employee based on parameter in search object
     * and then insert them into temporary table called "HR_DP_STOCK_TEMP"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */
    synchronized public static int addPrevToTemp(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_dp_stock_temp"; //"HR_DP_STOCK_TEMP";
        String tblDpReportNamePrev = "hr_dp_stock_temp_prev"; //"HR_DP_STOCK_TEMP_PREV";
        DBResultSet dbrs = null;
        int status = 0;
        try {

            // empty table Dp stock temporary table
            status = emptyTempPrev();
            
            // add data into Dp stock temporary table (prev, earn, used and expr)            
            status = addEarnToTempPrev(srcLeaveManagement);
            status = addUsedToTempPrev(srcLeaveManagement);
            status = addExprToTempPrev(srcLeaveManagement);
            
            String sQL = "INSERT INTO " + tblDpReportName + 
                         " SELECT PAYROLL, NAME, (SUM(EARNED)-SUM(USED)-SUM(EXPIRED)), 0, 0, 0 "+
                         " FROM " + tblDpReportNamePrev +
                         " GROUP BY PAYROLL";            
            
            System.out.println("addPrevToTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
            
            // empty table Dp stock temporary table
            status = emptyTempPrev();            
        }
        catch(Exception e) {
            System.out.println("Exc addPrevToTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }
    
    
    /**
     * get earned DP in current period per employee based on parameter in search object
     * and then insert them into temporary table called "HR_DP_STOCK_TEMP"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */
    synchronized public static int addEarnToTemp(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_dp_stock_temp"; //HR_DP_STOCK_TEMP";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", 0" + // prev_amount
            ", SUM(DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]+")" +
            ", 0" + // used
            ", 0" + // expired
            " FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+" AS DP" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP" +
            " ON DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            
            String strCommCondition = genCommonDpWhereClause(srcLeaveManagement);  
            
            String strStatusCondition = "DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                                        " <> "+PstDpStockManagement.DP_STS_NOT_AKTIF;                         
            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                Calendar cal = Calendar.getInstance();
                cal.setTime(selectedDate);                
                Date dtStartDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date dtEndDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                strLeavePeriodCondition = " DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+
                " BETWEEN \""+Formater.formatDate(dtStartDate,"yyyy-MM-dd") +
                " \" AND \"" + Formater.formatDate(dtEndDate,"yyyy-MM-dd") + "\"";
            }
            
            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strStatusCondition!=null && strStatusCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strStatusCondition;
                }
                else {
                    whereClause = strStatusCondition;
                }
            }

            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }
            
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];            
            
            System.out.println("addEarnToTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addEarnToTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }
    
    
    /**
     * get used DP in current period per employee based on parameter in search object
     * and then insert them into temporary table called "HR_DP_STOCK_TEMP"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */
    synchronized public static int addUsedToTemp(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_dp_stock_temp";  //"HR_DP_STOCK_TEMP";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", 0" + // prev_amount
            ", 0" + // earned
            ", SUM(TAKEN."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_QTY]+")" +
            ", 0" + // expired                        
            " FROM "+PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
            " INNER JOIN "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS TAKEN" +
            " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
            " = TAKEN."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID];
            
            /*
            " FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DP" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
            " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
            " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +            
            " INNER JOIN "+PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS TAKEN" +
            " ON DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+
            " = TAKEN."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID];
            */
            
            String strCommCondition = genCommonDpWhereClause(srcLeaveManagement);                                                            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod()!=null)
            {
                java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                cal.setTime(selectedDate);
                Date startDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date endDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), cal.getActualMaximum(cal.DAY_OF_MONTH));
                strLeavePeriodCondition = " TAKEN."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] +
                                          " BETWEEN \"" + Formater.formatDate(startDate,"yyyy-MM-dd") + "\"" +
                                          " AND \"" +Formater.formatDate(endDate,"yyyy-MM-dd") + "\""; 
            }
            
            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }              
            
            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }
            
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY TAKEN."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID];
            
            
            System.out.println("addUsedToTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addUsedToTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }
    
    /**
     * get expired DP in current period per employee based on parameter in search object
     * and then insert them into temporary table called "HR_DP_STOCK_TEMP"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addExprToTemp(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_dp_stock_temp"; //"HR_DP_STOCK_TEMP";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", 0" + // prev_amount
            ", 0" + // earned
            ", 0" + // used            
            ", SUM(EXP."+PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_EXPIRED_QTY]+")" +            
            " FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DP" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
            " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
            " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +            
            " INNER JOIN "+PstDpStockExpired.TBL_HR_DP_STOCK_EXPIRED + " AS EXP" +
            " ON DP."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+
            " = EXP."+PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_DP_STOCK_ID];
            
            String strCommCondition = genCommonDpWhereClause(srcLeaveManagement);                                                            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod()!=null)
            {
                java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
                Date selectedDate = srcLeaveManagement.getLeavePeriod();
                cal.setTime(selectedDate);
                Date startDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date endDate = new Date(selectedDate.getYear(), selectedDate.getMonth(), cal.getActualMaximum(cal.DAY_OF_MONTH));
                strLeavePeriodCondition = " EXP."+PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_EXPIRED_DATE] +
                                          " BETWEEN \"" + Formater.formatDate(startDate,"yyyy-MM-dd") + "\"" +
                                          " AND \"" +Formater.formatDate(endDate,"yyyy-MM-dd") + "\""; 
            }
            
            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }
            
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            else {
                whereClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+ " =0 ";
            }
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID];
            
            System.out.println("addExprToTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addExprToTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }

    
    /**
     * @param srcLeaveManagement
     * @return  
     */    
    public static String genCommonDpWhereClause(SrcLeaveManagement srcLeaveManagement)
    {
        String strNameCondition = "";
        if((srcLeaveManagement.getEmpName()!= null)&& (srcLeaveManagement.getEmpName().length()>0)) {
            Vector vectName = logicParser(srcLeaveManagement.getEmpName());
            if(vectName != null && vectName.size()>0) {
                strNameCondition = " (";
                for(int i = 0; i <vectName.size();i++) {
                    String str = (String)vectName.get(i);
                    if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        strNameCondition = strNameCondition + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                        " LIKE '%"+str.trim()+"%' ";
                    }
                    else {
                        strNameCondition = strNameCondition + str.trim();
                    }
                }
                strNameCondition = strNameCondition + ")";
            }
        }

        String strNumCondition = "";
        if((srcLeaveManagement.getEmpNum()!= null)&& (srcLeaveManagement.getEmpNum().length()>0)) {
            Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
            if(vectNum != null && vectNum.size()>0) {
                strNumCondition = " (";
                for(int i = 0; i <vectNum.size();i++) {
                    String str = (String)vectNum.get(i);
                    if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        strNumCondition = strNumCondition + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                        " LIKE '"+str.trim()+"%' ";
                    }
                    else {
                        strNumCondition = strNumCondition + str.trim();
                    }
                }
                strNumCondition = strNumCondition + ")";
            }
        }

        String strCategoryCondition = "";
        if(srcLeaveManagement.getEmpCatId() != 0) {
            strCategoryCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+
            " = "+srcLeaveManagement.getEmpCatId();
        }

        String strDepartmentCondition = "";
        if(srcLeaveManagement.getEmpDeptId() != 0) {
            strDepartmentCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
            " = "+srcLeaveManagement.getEmpDeptId();
        }

        String strSectionCondition = "";
        if(srcLeaveManagement.getEmpSectionId() != 0) {
            strSectionCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
            " = "+srcLeaveManagement.getEmpSectionId();
        }

        String strPositionCondition = "";
        if(srcLeaveManagement.getEmpPosId() != 0) {
            strPositionCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
            " = "+srcLeaveManagement.getEmpPosId();
        }

        String whereClause = "";
        if(strNameCondition!=null && strNameCondition.length()>0) {
            whereClause = strNameCondition;
        }

        if(strNumCondition!=null && strNumCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strNumCondition;
            }
            else {
                whereClause = strNumCondition;
            }
        }

        if(strCategoryCondition!=null && strCategoryCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strCategoryCondition;
            }
            else {
                whereClause = strCategoryCondition;
            }
        }


        if(strDepartmentCondition!=null && strDepartmentCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strDepartmentCondition;
            }
            else {
                whereClause = strDepartmentCondition;
            }
        }

        if(strSectionCondition!=null && strSectionCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strSectionCondition;
            }
            else {
                whereClause = strSectionCondition;
            }
        }

        if(strPositionCondition!=null && strPositionCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strPositionCondition;
            }
            else {
                whereClause = strPositionCondition;
            }
        }
        
        return whereClause;
    }
    // -------------- End DP Reporting --------------------
    
    
    

    /**
     * @param args
     */    
    public static void main(String args[])
    {
        SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();
        srcLeaveManagement.setPeriodChecked(false);
        srcLeaveManagement.setLeavePeriod(new Date());
        
        Vector vectResult = listDpStockReport(srcLeaveManagement);
        if(vectResult!=null && vectResult.size()>0)
        {
            int maxResult = vectResult.size();
            for(int i=0; i<maxResult; i++)
            {
                DpStockReporting objDpStockReporting = (DpStockReporting) vectResult.get(i);
                System.out.println("Payroll : " + objDpStockReporting.getPayroll());
                System.out.println("Name    : " + objDpStockReporting.getName());
                System.out.println("Prev    : " + objDpStockReporting.getPrevAmount());
                System.out.println("Earn    : " + objDpStockReporting.getEarn());
                System.out.println("Used    : " + objDpStockReporting.getUsed());
                System.out.println("Expired : " + objDpStockReporting.getExpired());                
                System.out.println("");
            }
        }
        else
        {
            System.out.println("Result null ...");
        }                
    }       
    
}
