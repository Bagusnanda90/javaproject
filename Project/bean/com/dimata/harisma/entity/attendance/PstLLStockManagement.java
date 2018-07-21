/**
 * User: gadnyana
 * Date: Apr 8, 2004
 * Time: 1:00:51 PM
 * Version: 1.0
 */
package com.dimata.harisma.entity.attendance;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.util.lang.I_Language;
import com.dimata.util.Formater;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.masterdata.PstLeavePeriod;
import com.dimata.harisma.entity.masterdata.LeavePeriod;
import com.dimata.harisma.entity.masterdata.PstPeriod;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Date;
import java.util.Hashtable;

public class PstLLStockManagement extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_LL_STOCK_MANAGEMENT ="hr_ll_stock_management";// "HR_LL_STOCK_MANAGEMENT";

    public static final int FLD_LL_STOCK_ID = 0; //0
    public static final int FLD_LEAVE_PERIODE_ID = 1;
    public static final int FLD_EMPLOYEE_ID = 2;
    public static final int FLD_OWNING_DATE = 3;
    public static final int FLD_OPENING_LL = 4;    
    public static final int FLD_LL_QTY = 5; //5
    public static final int FLD_QTY_USED = 6;
    public static final int FLD_QTY_RESIDUE = 7;    
    public static final int FLD_LL_STATUS = 8;
    public static final int FLD_ENTITLED = 9;
    public static final int FLD_NOTE = 10; //10
    public static final int FLD_EXPIRED_DATE = 11;   
    public static final int FLD_RECORD_DATE = 12;
    public static final int FLD_ENTITLED_DATE = 13;
    public static final int FLD_PREV_BALANCE = 14;    
    public static final int FLD_ENTITLED_2 = 15; //15
    public static final int FLD_EXPIRED_DATE_2 = 16;
    public static final int FLD_ENTITLED_DATE_2 = 17;
    public static final int FLD_RECORD_DATE_2 = 18;

    public static final String[] fieldNames = {
        "LL_STOCK_ID",
        "LEAVE_PERIOD_ID",
        "EMPLOYEE_ID",
        "OWNING_DATE",        
        "OPENING_LL",        
        "LL_QTY",
        "QTY_USED",        
        "QTY_RESIDUE",
        "LL_STATUS",        
        "ENTITLED",        
        "NOTE",      
        "EXP_DATE",
        "RECORD_DATE",
        "ENTITLE_DATE",
        "PREV_BALANCE",
        "ENTITLE_2",
        "EXP_DATE_2",
        "ENTITLE_DATE_2",
        "RECORD_DATE_2"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID, //0
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,         
        TYPE_FLOAT,       
        TYPE_FLOAT, //5
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_FLOAT,        
        TYPE_STRING, //10  
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_FLOAT,//15
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE
    };

    public static int LL_STS_AKTIF      = 0; // LL BISA DI AMBIL
    public static int LL_STS_NOT_AKTIF  = 1; // LL BELUM BISA DI AMBIL
    public static int LL_STS_TAKEN      = 2; // LL SUDAH HABIS
    public static int LL_STS_EXPIRED    = 3; // LL MASA BERLAKUNYA SUDAH BERAKHIR
    public static int LL_STS_INVALID    = 4; // LL MASA BERLAKUNYA SUDAH BERAKHIR

    public static final String[] fieldStatus = {
        "ACTIVE",
        "NOT ACTIVE",  
        "TAKEN",
        "EXPIRED"
    };

    public PstLLStockManagement() {
    }

    public PstLLStockManagement(int i) throws DBException {
        super(new PstLLStockManagement());
    }

    public PstLLStockManagement(String sOid) throws DBException {
        super(new PstLLStockManagement(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public PstLLStockManagement(long lOid) throws DBException {
        super(new PstLLStockManagement(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_LL_STOCK_MANAGEMENT;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstLLStockManagement().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        LLStockManagement objAlStockMgn = fetchExc(ent.getOID());
        return objAlStockMgn.getOID();
    }

    public static LLStockManagement fetchExc(long oid) throws DBException {
        try {
            LLStockManagement objLLStockMgn = new LLStockManagement();
            PstLLStockManagement objPstAlStockMgn = new PstLLStockManagement(oid);
            objLLStockMgn.setOID(oid);

            objLLStockMgn.setLeavePeriodeId(objPstAlStockMgn.getlong(FLD_LEAVE_PERIODE_ID));
            objLLStockMgn.setEmployeeId(objPstAlStockMgn.getlong(FLD_EMPLOYEE_ID));
            objLLStockMgn.setLLQty(objPstAlStockMgn.getfloat(FLD_LL_QTY));
            objLLStockMgn.setDtOwningDate(objPstAlStockMgn.getDate(FLD_OWNING_DATE));
            objLLStockMgn.setLLStatus(objPstAlStockMgn.getInt(FLD_LL_STATUS));
            objLLStockMgn.setEntitled(objPstAlStockMgn.getfloat(FLD_ENTITLED));
            objLLStockMgn.setStNote(objPstAlStockMgn.getString(FLD_NOTE));
            objLLStockMgn.setQtyResidue(objPstAlStockMgn.getfloat(FLD_QTY_RESIDUE));
            objLLStockMgn.setQtyUsed(objPstAlStockMgn.getfloat(FLD_QTY_USED));
            objLLStockMgn.setExpiredDate(objPstAlStockMgn.getDate(FLD_EXPIRED_DATE));            
            objLLStockMgn.setRecordDate(objPstAlStockMgn.getDate(FLD_RECORD_DATE));
            objLLStockMgn.setEntitledDate(objPstAlStockMgn.getDate(FLD_ENTITLED_DATE));
            objLLStockMgn.setPrevBalance(objPstAlStockMgn.getfloat(FLD_PREV_BALANCE));              
            objLLStockMgn.setEntitle2(objPstAlStockMgn.getfloat(FLD_ENTITLED_2));  
            objLLStockMgn.setExpiredDate2(objPstAlStockMgn.getDate(FLD_EXPIRED_DATE_2));  
            objLLStockMgn.setOpeningLL(objPstAlStockMgn.getfloat(FLD_OPENING_LL));
            objLLStockMgn.setEntitleDate2(objPstAlStockMgn.getDate(FLD_ENTITLED_DATE_2));
            objLLStockMgn.setRecordDate2(objPstAlStockMgn.getDate(FLD_RECORD_DATE_2));

            return objLLStockMgn;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLLStockManagement(0), DBException.UNKNOWN);
        }
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((DpStockManagement) ent);
    }

    public static long updateExc(LLStockManagement objAlStockMgn) throws DBException {
        try {
            if (objAlStockMgn.getOID() != 0) {
                PstLLStockManagement objPstLLStockMgn = new PstLLStockManagement(objAlStockMgn.getOID());

                objPstLLStockMgn.setLong(FLD_LEAVE_PERIODE_ID, objAlStockMgn.getLeavePeriodeId());
                objPstLLStockMgn.setLong(FLD_EMPLOYEE_ID, objAlStockMgn.getEmployeeId());
                objPstLLStockMgn.setFloat(FLD_LL_QTY, objAlStockMgn.getLLQty());
                objPstLLStockMgn.setFloat(FLD_ENTITLED, objAlStockMgn.getEntitled());
                objPstLLStockMgn.setDate(FLD_OWNING_DATE, objAlStockMgn.getDtOwningDate());
                objPstLLStockMgn.setInt(FLD_LL_STATUS, objAlStockMgn.getLLStatus());
                objPstLLStockMgn.setString(FLD_NOTE, objAlStockMgn.getStNote());
                objPstLLStockMgn.setFloat(FLD_QTY_RESIDUE, objAlStockMgn.getQtyResidue());
                objPstLLStockMgn.setFloat(FLD_QTY_USED, objAlStockMgn.getQtyUsed());
                objPstLLStockMgn.setDate(FLD_EXPIRED_DATE, objAlStockMgn.getExpiredDate());                
                objPstLLStockMgn.setDate(FLD_RECORD_DATE, objAlStockMgn.getRecordDate());
                objPstLLStockMgn.setDate(FLD_ENTITLED_DATE, objAlStockMgn.getEntitledDate());
                objPstLLStockMgn.setFloat(FLD_PREV_BALANCE, objAlStockMgn.getPrevBalance());                
                objPstLLStockMgn.setFloat(FLD_ENTITLED_2, objAlStockMgn.getEntitle2());
                objPstLLStockMgn.setDate(FLD_EXPIRED_DATE_2, objAlStockMgn.getExpiredDate2());
                objPstLLStockMgn.setFloat(FLD_OPENING_LL, objAlStockMgn.getOpeningLL());
                objPstLLStockMgn.setDate(FLD_RECORD_DATE_2, objAlStockMgn.getRecordDate2());
                objPstLLStockMgn.setDate(FLD_ENTITLED_DATE_2, objAlStockMgn.getEntitleDate2());
                objPstLLStockMgn.update();
                return objAlStockMgn.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLLStockManagement(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstLLStockManagement objPstDpStockMgn = new PstLLStockManagement(oid);
            objPstDpStockMgn.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLLStockManagement(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((LLStockManagement) ent);
    }

    public static long insertExc(LLStockManagement objLLStockMgn) throws DBException {
        try {
            PstLLStockManagement objPstLLStockMgn = new PstLLStockManagement(0);

            objPstLLStockMgn.setLong(FLD_LEAVE_PERIODE_ID, objLLStockMgn.getLeavePeriodeId());
            objPstLLStockMgn.setLong(FLD_EMPLOYEE_ID, objLLStockMgn.getEmployeeId());
            objPstLLStockMgn.setFloat(FLD_LL_QTY, objLLStockMgn.getLLQty());
            objPstLLStockMgn.setDate(FLD_OWNING_DATE, objLLStockMgn.getDtOwningDate());
            objPstLLStockMgn.setInt(FLD_LL_STATUS, objLLStockMgn.getLLStatus());
            objPstLLStockMgn.setString(FLD_NOTE, objLLStockMgn.getStNote());
            objPstLLStockMgn.setFloat(FLD_QTY_RESIDUE, objLLStockMgn.getQtyResidue());
            objPstLLStockMgn.setFloat(FLD_QTY_USED, objLLStockMgn.getQtyUsed());
            objPstLLStockMgn.setFloat(FLD_ENTITLED, objLLStockMgn.getEntitled());
            objPstLLStockMgn.setDate(FLD_EXPIRED_DATE, objLLStockMgn.getExpiredDate());
            objPstLLStockMgn.setDate(FLD_RECORD_DATE, objLLStockMgn.getRecordDate());
            objPstLLStockMgn.setDate(FLD_ENTITLED_DATE, objLLStockMgn.getEntitledDate());
            objPstLLStockMgn.setFloat(FLD_PREV_BALANCE, objLLStockMgn.getPrevBalance());
            objPstLLStockMgn.setFloat(FLD_ENTITLED_2, objLLStockMgn.getEntitle2());
            objPstLLStockMgn.setDate(FLD_EXPIRED_DATE_2, objLLStockMgn.getExpiredDate2());
            objPstLLStockMgn.setFloat(FLD_OPENING_LL, objLLStockMgn.getOpeningLL());
            objPstLLStockMgn.setDate(FLD_RECORD_DATE_2, objLLStockMgn.getRecordDate2());
            objPstLLStockMgn.setDate(FLD_ENTITLED_DATE_2, objLLStockMgn.getEntitleDate2());
            objPstLLStockMgn.insert();
            objLLStockMgn.setOID(objPstLLStockMgn.getlong(FLD_LL_STOCK_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLLStockManagement(0), DBException.UNKNOWN);
        }
        return objLLStockMgn.getOID();
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_LL_STOCK_MANAGEMENT;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                LLStockManagement llStockManagement = new LLStockManagement();
                resultToObject(rs, llStockManagement);
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
    
    
    public static Vector listStockManagementLL(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT LLMAN.*,LT.LL_TB_TKN FROM " + TBL_LL_STOCK_MANAGEMENT + " AS LLMAN "
             + " LEFT JOIN sum_ll_tb_taken_by_emp AS LT ON LT.EMPLOYEE_ID=LLMAN."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID];
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                LLStockManagement llStockManagement = new LLStockManagement();
                resultToObject(rs, llStockManagement);
                llStockManagement.setToBeTaken(rs.getFloat("LT.LL_TB_TKN")); 
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


    public static Vector list(int limitStart, int recordToGet, long oidPeriod, long oidDepatment) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT LL." +fieldNames[FLD_LL_STOCK_ID]+
                    ",LL."+fieldNames[FLD_EMPLOYEE_ID]+
                    ",LL."+fieldNames[FLD_LEAVE_PERIODE_ID]+
                    ",LL."+fieldNames[FLD_OWNING_DATE]+
                    ",LL."+fieldNames[FLD_LL_QTY]+
                    ",LL."+fieldNames[FLD_ENTITLED]+
                    ",LL."+fieldNames[FLD_QTY_RESIDUE]+
                    ",LL."+fieldNames[FLD_QTY_USED]+
                    ",LL."+fieldNames[FLD_LL_STATUS]+
                    ",LL."+fieldNames[FLD_NOTE]+
                    ",LL."+fieldNames[FLD_EXPIRED_DATE]+
                    ",EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                    ",EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                    ",LP."+PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_START_DATE]+
                    " FROM " + TBL_LL_STOCK_MANAGEMENT+ " LL " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON " +
                    "LL." + fieldNames[FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstLeavePeriod.TBL_HR_LEAVE_PERIOD + " LP ON " +
                    "LL." + fieldNames[FLD_LEAVE_PERIODE_ID] + " = LP." + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_LEAVE_PERIOD_ID] +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + oidDepatment +
                    " AND LP." + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_LEAVE_PERIOD_ID] + " = " + oidPeriod;

            if (recordToGet != 0)
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;

            //System.out.println("sQl = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector ls = new Vector(1,1);
                LLStockManagement llStockManagement = new LLStockManagement();
                Employee emp = new Employee();
                LeavePeriod lp = new LeavePeriod();
                resultToObject(rs, llStockManagement);
                ls.add(llStockManagement);

                emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                ls.add(emp);

                lp.setStartDate(rs.getDate(PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_START_DATE]));
                ls.add(lp);

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


    public static void resultToObject(ResultSet rs, LLStockManagement objLLStockMgn) {
        //update by satrya 2013-09-27
        //private static void resultToObject(ResultSet rs, LLStockManagement objLLStockMgn) {
        try {
            objLLStockMgn.setOID(rs.getLong(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID]));
            objLLStockMgn.setLeavePeriodeId(rs.getLong(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LEAVE_PERIODE_ID]));
            objLLStockMgn.setEmployeeId(rs.getLong(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]));
            objLLStockMgn.setLLQty(rs.getFloat(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY]));
            objLLStockMgn.setEntitled(rs.getFloat(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED]));
            objLLStockMgn.setDtOwningDate(rs.getDate(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE]));
            objLLStockMgn.setLLStatus(rs.getInt(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS]));
            objLLStockMgn.setStNote(rs.getString(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_NOTE]));
            objLLStockMgn.setQtyResidue(rs.getFloat(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE]));
            objLLStockMgn.setQtyUsed(rs.getFloat(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_USED]));
            objLLStockMgn.setExpiredDate(rs.getDate(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EXPIRED_DATE]));
            objLLStockMgn.setPrevBalance(rs.getFloat(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_PREV_BALANCE]));
            objLLStockMgn.setOpeningLL(rs.getFloat(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OPENING_LL]));
            objLLStockMgn.setRecordDate(rs.getDate(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_RECORD_DATE]));
            objLLStockMgn.setEntitledDate(rs.getDate(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED_DATE]));
            objLLStockMgn.setRecordDate2(rs.getDate(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_RECORD_DATE_2]));
            objLLStockMgn.setEntitleDate2(rs.getDate(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED_DATE_2]));
            objLLStockMgn.setEntitle2(rs.getFloat(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED_2]));
            objLLStockMgn.setExpiredDate2(rs.getDate(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EXPIRED_DATE_2]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * gadnyana
     * untuk mencari data al stock
     * di pakai oleh service
     *
     * @param leavePerioId
     * @param empOid
     * @return
     */
    public static LLStockManagement checkLLStockManagement(long leavePerioId, long empOid) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        LLStockManagement llStockManagement = new LLStockManagement();
        try {
            String sql = "SELECT * FROM " + TBL_LL_STOCK_MANAGEMENT + " WHERE " +
                    fieldNames[FLD_LEAVE_PERIODE_ID] + " = '" + leavePerioId + "'" +
                    " AND " + fieldNames[FLD_EMPLOYEE_ID] + " = '" + empOid + "'";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                resultToObject(rs, llStockManagement);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return llStockManagement;
        }
    }
    
    
    /**
     * @param leavePeriodeId
     * @param employeeId
     * @param presenceDate
     * @return
     * @created by Edhy
     */    
    public static LLStockManagement getLlStockPerEmpFirst()
    {
        LLStockManagement llStockManagement = new LLStockManagement();
        DBResultSet dbrs;
        String stSQL = " SELECT " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LEAVE_PERIODE_ID] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] +                       
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_USED] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_NOTE] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EXPIRED_DATE] +
                       " FROM "+ TBL_LL_STOCK_MANAGEMENT +
                       " WHERE " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                       //" = " + LL_STS_AKTIF +
                       " IN (" + LL_STS_AKTIF + ", " + LL_STS_NOT_AKTIF + ")" +   
                       " ORDER BY " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE];
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                resultToObject(rs, llStockManagement);
                
                break;
            }
        }
        catch(DBException dbe)
        {
            dbe.printStackTrace();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return llStockManagement;
    }

    
    /**
     * @param leavePeriodeId
     * @param employeeId
     * @param presenceDate
     * @return
     * @created by Edhy
     */    
    public static LLStockManagement getLlStockPerEmpFirst(long employeeId)
    {
        LLStockManagement llStockManagement = new LLStockManagement();
        DBResultSet dbrs;
        String stSQL = " SELECT " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LEAVE_PERIODE_ID] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] +                       
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_USED] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_NOTE] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EXPIRED_DATE] +
                       " FROM "+ TBL_LL_STOCK_MANAGEMENT +
                       " WHERE " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                       //" = " + LL_STS_AKTIF +
                       " IN (" + LL_STS_AKTIF + ", " + LL_STS_NOT_AKTIF + ")" +   
                       " AND " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                       " = " + employeeId + 
                       " ORDER BY " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE];
        try  
        {            
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                resultToObject(rs, llStockManagement);
                
                break;
            }
        }
        catch(DBException dbe)
        {
            dbe.printStackTrace();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return llStockManagement;
    }

    
    /**
     * @param leavePeriodeId
     * @param employeeId
     * @param presenceDate
     * @return
     * @created by Edhy
     */    
    public static LLStockManagement getLlStockPerEmpLast()
    {
        LLStockManagement llStockManagement = new LLStockManagement();
        DBResultSet dbrs;
        String stSQL = " SELECT " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LEAVE_PERIODE_ID] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] +                       
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_USED] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_NOTE] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EXPIRED_DATE] +
                       " FROM "+ TBL_LL_STOCK_MANAGEMENT +
                       //" WHERE " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LEAVE_PERIODE_ID] +
                       //" = " + LL_STS_AKTIF +
                       " ORDER BY " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE];
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                resultToObject(rs, llStockManagement);
                
                break;
            }
        }
        catch(DBException dbe)
        {
            dbe.printStackTrace();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return llStockManagement;
    }

    
    /**
     * @param leavePeriodeId
     * @param employeeId
     * @param presenceDate
     * @return
     * @created by Edhy
     */    
    public static LLStockManagement getLlStockPerEmployee(long leavePeriodeId, long employeeId, Date presenceDate)
    {
        LLStockManagement llStockManagement = new LLStockManagement();
        DBResultSet dbrs;
        String stSQL = " SELECT " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LEAVE_PERIODE_ID] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] +                       
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_USED] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_NOTE] +
                       ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EXPIRED_DATE] +
                       " FROM "+ TBL_LL_STOCK_MANAGEMENT +
                       " WHERE " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LEAVE_PERIODE_ID] +
                       " = " + leavePeriodeId +
                       " AND " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " AND " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] +
                       " = \"" + Formater.formatDate(presenceDate, "yyyy-MM-dd") + "\"";
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                resultToObject(rs, llStockManagement);
            }
        }
        catch(DBException dbe)
        {
            dbe.printStackTrace();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return llStockManagement;
    }
    
    
    /**
     * @param presenceDate
     * @param employeeId
     */  
    public void updateLLStockManagementPresence(Date presenceDate, long employeeId) 
    {                
        // mencari jumlah stock LL yang terpakai dan sisanya
        // jika Dp itu sudah terpakai semua, maka set status menjadi TAKEN
        LLStockManagement llStockManagement = getLlStockPerEmpFirst(employeeId);          
        float intLlUsed  = llStockManagement.getQtyUsed()+1;
        float intResidue = llStockManagement.getLLQty() - intLlUsed;
        llStockManagement.setQtyUsed(intLlUsed);
        llStockManagement.setQtyResidue(intResidue);          
        if(intResidue == 0)   
        {
            llStockManagement.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);  
        }

        try 
        {                       
            // jika ada / punya LL stock yang active
            boolean llTakenExist = PstLlStockTaken.existLlStockTaken(employeeId, presenceDate);
            if( (llStockManagement.getOID()!=0) && (!llTakenExist) )
            {                         
                PstLLStockManagement.updateExc(llStockManagement);   
            }

            // insert data di Ll stock taken              
            if(!llTakenExist)
            {                            
                // insert data di Dp stock taken  
                PstLlStockTaken objPstLlStockTaken = new PstLlStockTaken();
                LlStockTaken objLlStockTaken = new LlStockTaken();
                objLlStockTaken.setLlStockId(llStockManagement.getOID());
                objLlStockTaken.setEmployeeId(employeeId);
                objLlStockTaken.setTakenDate(presenceDate);
                objLlStockTaken.setTakenQty(1);                        
                objPstLlStockTaken.insertExc(objLlStockTaken);                            
            }
        }
        catch(Exception e) 
        {
            System.out.println("Exc when update LL stock on PstLLStockManagement : " + e.toString());
        }
    }
    
    
    /**
     * @created by Edhy
     * @param presenceDate
     * @param employeeId
     */
    public void deleteLLStockManagement(long employeeId, Date presenceDate) 
    {        
        long leavePeriodeId = PstPeriod.getPeriodeIdBetween(presenceDate);                
        LLStockManagement llStockManagement = getLlStockPerEmployee(leavePeriodeId, employeeId, presenceDate);
        try   
        {
            PstLLStockManagement.deleteExc(llStockManagement.getOID()); 
        }
        catch(Exception e) 
        {
            System.out.println("Exc when delete LL stock on PstLLStockManagement : " + e.toString());
        }
    }

    /**
     * @created by Edhy
     * @param presenceDate
     * @param employeeId
     */
    public static LLStockManagement generateNewLLTakenImmediately(Date owningDate, long employeeId, long leavePeriodId, int llCount) 
    {    
        LLStockManagement llStockManagement = new LLStockManagement();
        
        llStockManagement.setLLStatus(LL_STS_NOT_AKTIF);
        llStockManagement.setLLQty(26);
        llStockManagement.setDtOwningDate(owningDate);
        llStockManagement.setEmployeeId(employeeId);
        llStockManagement.setEntitled(llCount+1);
        llStockManagement.setLeavePeriodeId(leavePeriodId);   
        llStockManagement.setQtyUsed(1);
        llStockManagement.setQtyResidue(llStockManagement.getLLQty() - llStockManagement.getQtyUsed());
        llStockManagement.setStNote("LL Debt on " + owningDate);        
        
        return llStockManagement;
    }    
    
    
    /**
     * get LL stock count belongs to specified employee
     * @created by Edhy 
     */    
    public static int getCountLLBelogsToEmp(long employeeId)
    {
        int result = 0;
        DBResultSet dbrs;
        String stSQL = " SELECT COUNT(" + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] + ")" +
                       " FROM "+ TBL_LL_STOCK_MANAGEMENT +
                       " WHERE " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + 
                       " = " + employeeId;
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                result = rs.getInt(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * @param selectedDate
     * @param employeeId
     * @return
     * @created by Edhy
     */    
    public float getLlStockAmountPerEmployee(long employeeId)
    {
        float result = 0;
        DBResultSet dbrs = null;
        String stSQL = " SELECT SUM(" + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE] + ")" +
                       " FROM "+ TBL_LL_STOCK_MANAGEMENT +
                       " WHERE " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +                       
                       " AND " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                       " = " + PstLLStockManagement.LL_STS_AKTIF;
        try
        {
//            System.out.println(getClass().getName() + ".getLlStockAmountPerEmployee() stSQL : " + stSQL);            
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                result = rs.getInt(1);
                break;
            }
        }
        catch(Exception e)
        {
            System.out.println(getClass().getName() + ".getLlStockAmountPerEmployee() exc : " + e.toString());            
        }
        finally
        {
            DBResultSet.close(dbrs);
        }
        return result;
    }
    
    
    /** 
     * Proses pebayaran hutang LL, dengan asumsi vectDpStock yang berisi object LlStock tidak null/kosong          
     * @param objLlStockManagement
     * @param employeeId
     * @return
     * @created by Edhy
     */    
    public static Vector paidLlPayable(long employeeId, LLStockManagement objLlStockManagement)
    {        
        Vector result = new Vector(1,1);
        Vector listLlPayable = PstLlStockTaken.getLlPayable(employeeId);
        if(listLlPayable!=null && listLlPayable.size()>0)
        {            
            long llStockManagementOid = objLlStockManagement.getOID();
            Date dtLlStockActive = objLlStockManagement.getDtOwningDate();
            float maxLlStock = objLlStockManagement.getQtyResidue();
            float maxLlPayable = listLlPayable.size();
            float maxIterate = (maxLlStock >= maxLlPayable) ? maxLlPayable : maxLlStock;                                    
            for(int i=0; i<maxIterate; i++)
            {
                try
                {
                    LlStockTaken objLlStockTaken = (LlStockTaken) listLlPayable.get(i);
                    objLlStockTaken.setLlStockId(llStockManagementOid);    
                    objLlStockTaken.setPaidDate(dtLlStockActive);                        
                    
                    long updatedOid = PstLlStockTaken.updateExc(objLlStockTaken);
                    result.add(String.valueOf(updatedOid));
                }
                catch(Exception e)
                {
                    System.out.println("Exc when update objLlStockTaken : " + e.toString());
                }
            }            
            
            // update object AlStockManagement karena semua Qty sudah digunakan utk membayar hutang
            try
            {
                if(result!=null && result.size()>0)   
                {
                    float llUsed = objLlStockManagement.getQtyUsed();                                           
                    objLlStockManagement.setQtyUsed(llUsed + result.size());
                    objLlStockManagement.setQtyResidue(objLlStockManagement.getLLQty() - objLlStockManagement.getQtyUsed());
                    if(objLlStockManagement.getQtyResidue() == 0)
                    {
                        objLlStockManagement.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);
                    }
                    long alManUpdatedOid = PstLLStockManagement.updateExc(objLlStockManagement);                    
                }
            }
            catch(Exception e)
            {
                System.out.println("Exc when update object LlStockManagement : " + e.toString());
            }            
        }
        
        return result;
    }        
    
    
    /**
     * Proses generate data LL Stock Taken sesuai dengan data Ll used pada object LL Management     
     *
     * @return
     * @created by Edhy
     */        
    public static Vector generateLlTaken()
    {        
        return generateLlTaken(0);
    }
    
    /**
     * Proses generate data LL Stock Taken sesuai dengan data Ll used pada object Ll Management
     * @param employeeId
     *
     * @return
     * @created by Edhy
     */        
    public static Vector generateLlTaken(long employeeId)
    {        
        Vector result = new Vector(1,1);
        String strCommCondition = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                                  " IN (" + PstLLStockManagement.LL_STS_AKTIF + "," + PstLLStockManagement.LL_STS_TAKEN + ")" +
                                  " AND " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_USED] +
                                  " > 0";  
        
        String strEmployeeCondition = "";
        if(employeeId != 0)
        {
            strEmployeeCondition = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + " = " + employeeId;            
        }
        
        String whereClause = "";
        if(strCommCondition!=null && strCommCondition.length()>0) {
            whereClause = strCommCondition;
        }            

        if(strEmployeeCondition!=null && strEmployeeCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strEmployeeCondition;
            }
            else 
            {
                whereClause = strEmployeeCondition;
            }
        }
        
        String strOrderBy = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE];
        Vector vectOfLlStock = PstLLStockManagement.list(0, 0, whereClause, strOrderBy);
        if(vectOfLlStock!=null && vectOfLlStock.size()>0)
        {
            int maxVectOfLlStock = vectOfLlStock.size();
            for(int i=0; i<maxVectOfLlStock; i++)
            {
                LLStockManagement objLLStockManagement = (LLStockManagement) vectOfLlStock.get(i);
                float maxLlUsed = objLLStockManagement.getQtyUsed();
                for(int j=0; j<maxLlUsed; j++)
                {
                    LlStockTaken objLlStockTaken = new LlStockTaken();
                    objLlStockTaken.setLlStockId(objLLStockManagement.getOID());
                    objLlStockTaken.setEmployeeId(objLLStockManagement.getEmployeeId());
                    objLlStockTaken.setTakenDate(objLLStockManagement.getDtOwningDate());
                    objLlStockTaken.setTakenQty(1);
                    objLlStockTaken.setPaidDate(objLLStockManagement.getDtOwningDate());
                    
                    try
                    {
                        long oid = PstLlStockTaken.insertExc(objLlStockTaken);
                        result.add(String.valueOf(oid));
                    }
                    catch(Exception e) 
                    {
                        System.out.println("Exc when generateLlTaken : " + e.toString());
                    }
                }
            }                
        }
        
        return result;
    }


    
    
    /**
     * Proses generate data LL Stock Expired sesuai dengan data Ll used pada object LL Management     
     *
     * @return
     * @created by Edhy
     */        
    public static Vector generateLlExpired()
    {        
        return generateLlExpired(0);
    }
    
    /**
     * Proses generate data LL Stock Expired sesuai dengan data Ll used pada object LL Management
     * @param employeeId
     *
     * @return
     * @created by Edhy
     */        
    public static Vector generateLlExpired(long employeeId)
    {        
        Vector result = new Vector(1,1);
        String strCommCondition = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] +
                                  " = " + PstLLStockManagement.LL_STS_EXPIRED;
        
        String strEmployeeCondition = "";
        if(employeeId != 0)
        {
            strEmployeeCondition = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + " = " + employeeId;            
        }
        
        String whereClause = "";
        if(strCommCondition!=null && strCommCondition.length()>0) 
        {
            whereClause = strCommCondition;
        }            

        if(strEmployeeCondition!=null && strEmployeeCondition.length()>0) 
        {
            if(whereClause!=null && whereClause.length()>0) 
            {
                whereClause = whereClause + " AND " + strEmployeeCondition;
            }
            else 
            {
                whereClause = strEmployeeCondition;
            }
        }
        
        String strOrderBy = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE];
        Vector vectOfLlStock = PstLLStockManagement.list(0, 0, whereClause, strOrderBy);
        if(vectOfLlStock!=null && vectOfLlStock.size()>0)
        {  
            int maxVectOfLlStock = vectOfLlStock.size();
            for(int i=0; i<maxVectOfLlStock; i++)
            {
                LLStockManagement objLLStockManagement = (LLStockManagement) vectOfLlStock.get(i);
                float maxLlUsed = objLLStockManagement.getQtyUsed();
                for(int j=0; j<maxLlUsed; j++)  
                {
                    LlStockExpired objLlStockExpired = new LlStockExpired();
                    objLlStockExpired.setLlStockId(objLLStockManagement.getOID());                    
                    objLlStockExpired.setExpiredDate(objLLStockManagement.getDtOwningDate());
                    objLlStockExpired.setExpiredQty(1);                    
                    
                    try
                    {
                        long oid = PstLlStockExpired.insertExc(objLlStockExpired);
                        result.add(String.valueOf(oid));
                    }
                    catch(Exception e) 
                    {
                        System.out.println("Exc when generateLlExpired : " + e.toString());
                    }
                }
            }                
        }
        
        return result;  
    }
    
    
    /**
     * @param lDepartmentOid
     * @created by Edhy     
     */
    public void deleteLlStockManagementPerDepartment(long lDepartmentOid) 
    {                
        DBResultSet dbrs = null;
        String stSQL = " SELECT LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] +                       
                       ", LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                       " FROM "+ TBL_LL_STOCK_MANAGEMENT + " AS LL" +
                       " INNER JOIN "+ PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                       " ON LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                       " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                       
                       " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                       " = " + lDepartmentOid;
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                long lLlStockOid = rs.getLong(1);
                long lEmployeeOid = rs.getLong(2);
                
                // delete Ll expired
                PstLlStockExpired.deleteByLlStock(lLlStockOid);
                
                // delete Ll stock
                try
                {
                    long oidLlStock = PstLLStockManagement.deleteExc(lLlStockOid);
                }
                catch(Exception e)
                {
                    System.out.println("Exc : " + e.toString());
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Exc : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
        }        
    }
    
    
    /**
     * @param lDepartmentOid
     * @created by Edhy     
     */
    public void deleteLlStockPerDepartment(long lDepartmentOid) 
    {   
        PstLlStockTaken objPstLlStockTaken = new PstLlStockTaken();
        objPstLlStockTaken.deleteLlStockTakenPerDepartment(lDepartmentOid); 
        deleteLlStockManagementPerDepartment(lDepartmentOid);
    }    
    
    
    /**
     * @param args
     */    
    public static void main(String args[])
    {
        System.out.println("Start process");        
        
        Vector vectResult = generateLlTaken();
        if(vectResult!=null && vectResult.size()>0)
        {
            int maxVectResult = vectResult.size();
            for(int i=0; i<maxVectResult; i++) 
            {
                System.out.println("oid ke " + (i+1) + " = " + String.valueOf(vectResult.get(i)));
            }
        }                
        
        /*
        Vector vectResult = generateLlExpired();
        if(vectResult!=null && vectResult.size()>0)
        {            
            int maxVectResult = vectResult.size();
            for(int i=0; i<maxVectResult; i++)
            {
                System.out.println("oid ke " + (i+1) + " = " + String.valueOf(vectResult.get(i)));
            }
        }
        */
        
        System.out.println("Stop process");
    }    
    
}
