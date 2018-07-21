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

public class PstAlStockManagement extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_AL_STOCK_MANAGEMENT = "hr_al_stock_management";//HR_AL_STOCK_MANAGEMENT";

    public static final int FLD_AL_STOCK_ID = 0;
    public static final int FLD_LEAVE_PERIODE_ID = 1;  
    public static final int FLD_AL_QTY = 2;
    public static final int FLD_OWNING_DATE = 3;
    public static final int FLD_AL_STATUS = 4;
    public static final int FLD_NOTE = 5;
    public static final int FLD_QTY_RESIDUE = 6;
    public static final int FLD_QTY_USED = 7;
    public static final int FLD_EMPLOYEE_ID = 8;
    public static final int FLD_ENTITLED = 9;   
    public static final int FLD_COMMENCING_DATE_HAVE = 10;
    public static final int FLD_OPENING = 11;
    public static final int FLD_RECORD_DATE = 12;
    public static final int FLD_ENTITLE_DATE = 13;
    public static final int FLD_PREV_BALANCE = 14;
    public static final int FLD_EXPIRED_DATE = 15;
    //update by satrya 2013-02-25
    public static final int FLD_EXTRA_AL = 16;
    public static final int FLD_EXTRA_AL_DATE = 17;

    public static final String[] fieldNames = {
        "AL_STOCK_ID", //0
        "LEAVE_PERIOD_ID",
        "AL_QTY",
        "OWNING_DATE",
        "AL_STATUS",
        "NOTE", //5
        "QTY_RESIDUE",
        "QTY_USED",
        "EMPLOYEE_ID",
        "ENTITLED",        
        "COMMENCING_DATE_HAVE", //10
        "OPENING",
        "RECORD_DATE",
        "ENTITLE_DATE",
        "PREV_BALANCE",
        "EXPIRED_DATE",
        "EXTRA_AL",
        "EXTRA_AL_DATE"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID, // 0
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_INT,
        TYPE_STRING, //5
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_FLOAT,       
        TYPE_DATE, //10
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_DATE,
       //Update by satrya
        TYPE_FLOAT,
        TYPE_DATE
    };

    public static int AL_STS_AKTIF = 0; // AL BISA DI AMBIL
    public static int AL_STS_NOT_AKTIF = 1; // AL BELUM BISA DI AMBIL
    public static int AL_STS_TAKEN = 2; // AL SUDAH HABIS
    public static int AL_STS_EXPIRED = 3; // AL MASA BERLAKUNYA SUDAH BERAKHIR
    public static int AL_STS_NOT_VALID = 4; // AL TIDAK VALID
//update by satrya 2013-02-25
    public static int STATUS_ADD_BY_SERVICE=1;
    
    public static final String[] fieldStatus = {
        "ACTIVE",
        "NOT ACTIVE",
        "TAKEN",
        "EXPIRED",
        "NOT VALID"
    };

    // jumlah AL default yang ditambahkan setiap bulannya
    public static int AL_QTY_ADD = 1;
    public static int AL_PERIOD_COMMENCING=0;
    public static int AL_PERIOD_NEW_YEAR=1;
    
    public PstAlStockManagement() {
    }

    public PstAlStockManagement(int i) throws DBException {
        super(new PstAlStockManagement());
    }

    public PstAlStockManagement(String sOid) throws DBException {
        super(new PstAlStockManagement(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public PstAlStockManagement(long lOid) throws DBException {
        super(new PstAlStockManagement(0));
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
        return TBL_AL_STOCK_MANAGEMENT;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstAlStockManagement().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        AlStockManagement objAlStockMgn = fetchExc(ent.getOID());
        return objAlStockMgn.getOID();
    }

    public static AlStockManagement fetchExc(long oid) throws DBException {
        try {
            AlStockManagement objAlStockMgn = new AlStockManagement();
            PstAlStockManagement objPstAlStockMgn = new PstAlStockManagement(oid);
            objAlStockMgn.setOID(oid);

            objAlStockMgn.setLeavePeriodeId(objPstAlStockMgn.getlong(FLD_LEAVE_PERIODE_ID));
            objAlStockMgn.setEmployeeId(objPstAlStockMgn.getlong(FLD_EMPLOYEE_ID));
            objAlStockMgn.setAlQty(objPstAlStockMgn.getfloat(FLD_AL_QTY));
            // objAlStockMgn.setAlQty(objPstAlStockMgn.getInt(FLD_AL_QTY));
            objAlStockMgn.setDtOwningDate(objPstAlStockMgn.getDate(FLD_OWNING_DATE));
            objAlStockMgn.setAlStatus(objPstAlStockMgn.getInt(FLD_AL_STATUS));
            objAlStockMgn.setStNote(objPstAlStockMgn.getString(FLD_NOTE));
            //objAlStockMgn.setQtyResidue(objPstAlStockMgn.getInt(FLD_QTY_RESIDUE));
            objAlStockMgn.setQtyResidue(objPstAlStockMgn.getfloat(FLD_QTY_RESIDUE));
            //objAlStockMgn.setQtyUsed(objPstAlStockMgn.getInt(FLD_QTY_USED));
            objAlStockMgn.setQtyUsed(objPstAlStockMgn.getfloat(FLD_QTY_USED));
            //objAlStockMgn.setEntitled(objPstAlStockMgn.getInt(FLD_ENTITLED));
            objAlStockMgn.setEntitled(objPstAlStockMgn.getfloat(FLD_ENTITLED));
            objAlStockMgn.setCommencingDateHave(objPstAlStockMgn.getDate(FLD_COMMENCING_DATE_HAVE));
            //  objAlStockMgn.setOpening(objPstAlStockMgn.getInt(FLD_OPENING));
            objAlStockMgn.setOpening(objPstAlStockMgn.getfloat(FLD_OPENING));
            objAlStockMgn.setRecordDate(objPstAlStockMgn.getDate(FLD_RECORD_DATE));
            objAlStockMgn.setEntitleDate(objPstAlStockMgn.getDate(FLD_ENTITLE_DATE));
            //objAlStockMgn.setPrevBalance(objPstAlStockMgn.getInt(FLD_PREV_BALANCE));
            objAlStockMgn.setPrevBalance(objPstAlStockMgn.getfloat(FLD_PREV_BALANCE));
            objAlStockMgn.setExpiredDate(objPstAlStockMgn.getDate(FLD_EXPIRED_DATE));
            //update by satrya 2013-02-25
            objAlStockMgn.setExtraAl(objPstAlStockMgn.getInt(FLD_EXTRA_AL));
            objAlStockMgn.setExtraAlDate(objPstAlStockMgn.getDate(FLD_EXTRA_AL_DATE));
            
            return objAlStockMgn;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockManagement(0), DBException.UNKNOWN);
        }
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((AlStockManagement) ent);
    }

    public static long updateExc(AlStockManagement objAlStockMgn) throws DBException {
        try {
            if (objAlStockMgn.getOID() != 0) {
                PstAlStockManagement objPstAlStockMgn = new PstAlStockManagement(objAlStockMgn.getOID());
                objPstAlStockMgn.setLong(FLD_LEAVE_PERIODE_ID, objAlStockMgn.getLeavePeriodeId());
                objPstAlStockMgn.setLong(FLD_EMPLOYEE_ID, objAlStockMgn.getEmployeeId());
                objPstAlStockMgn.setFloat(FLD_AL_QTY, objAlStockMgn.getAlQty());
                objPstAlStockMgn.setDate(FLD_OWNING_DATE, objAlStockMgn.getDtOwningDate());
                objPstAlStockMgn.setInt(FLD_AL_STATUS, objAlStockMgn.getAlStatus());
                objPstAlStockMgn.setString(FLD_NOTE, objAlStockMgn.getStNote());
                objPstAlStockMgn.setFloat(FLD_QTY_RESIDUE, objAlStockMgn.getQtyResidue());
                objPstAlStockMgn.setFloat(FLD_QTY_USED, objAlStockMgn.getQtyUsed());
                objPstAlStockMgn.setFloat(FLD_ENTITLED, objAlStockMgn.getEntitled()); 
                objPstAlStockMgn.setDate(FLD_COMMENCING_DATE_HAVE,objAlStockMgn.getCommencingDateHave());
                objPstAlStockMgn.setFloat(FLD_OPENING,objAlStockMgn.getOpening());
                objPstAlStockMgn.setDate(FLD_RECORD_DATE,objAlStockMgn.getRecordDate());
                objPstAlStockMgn.setDate(FLD_ENTITLE_DATE,objAlStockMgn.getEntitleDate());                
                objPstAlStockMgn.setFloat(FLD_PREV_BALANCE,objAlStockMgn.getPrevBalance());
                objPstAlStockMgn.setDate(FLD_EXPIRED_DATE,objAlStockMgn.getExpiredDate());
                //update by satrya 2013-02-25
                objPstAlStockMgn.setDouble(FLD_EXTRA_AL,objAlStockMgn.getExtraAl());
                objPstAlStockMgn.setDate(FLD_EXTRA_AL_DATE,objAlStockMgn.getExtraAlDate());
                
                objPstAlStockMgn.update();
                return objAlStockMgn.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockManagement(0), DBException.UNKNOWN);            
        }
        return 0;
    }

    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstAlStockManagement objPstDpStockMgn = new PstAlStockManagement(oid);
            objPstDpStockMgn.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockManagement(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((AlStockManagement) ent);
    }

    public static long insertExc(AlStockManagement objAlStockMgn) throws DBException {
        try {
            PstAlStockManagement objPstAlStockMgn = new PstAlStockManagement(0);

            objPstAlStockMgn.setLong(FLD_LEAVE_PERIODE_ID, objAlStockMgn.getLeavePeriodeId());
            objPstAlStockMgn.setLong(FLD_EMPLOYEE_ID, objAlStockMgn.getEmployeeId());
            objPstAlStockMgn.setFloat(FLD_AL_QTY, objAlStockMgn.getAlQty());
            objPstAlStockMgn.setDate(FLD_OWNING_DATE, objAlStockMgn.getDtOwningDate());
            objPstAlStockMgn.setInt(FLD_AL_STATUS, objAlStockMgn.getAlStatus());
            objPstAlStockMgn.setString(FLD_NOTE, objAlStockMgn.getStNote());
            objPstAlStockMgn.setFloat(FLD_QTY_RESIDUE, objAlStockMgn.getQtyResidue());
            objPstAlStockMgn.setFloat(FLD_QTY_USED, objAlStockMgn.getQtyUsed());
            objPstAlStockMgn.setFloat(FLD_ENTITLED, objAlStockMgn.getEntitled());            
            objPstAlStockMgn.setDate(FLD_COMMENCING_DATE_HAVE,objAlStockMgn.getCommencingDateHave());
            objPstAlStockMgn.setFloat(FLD_OPENING,objAlStockMgn.getOpening());
            objPstAlStockMgn.setDate(FLD_RECORD_DATE,objAlStockMgn.getRecordDate());
            objPstAlStockMgn.setDate(FLD_ENTITLE_DATE,objAlStockMgn.getEntitleDate());
            objPstAlStockMgn.setFloat(FLD_PREV_BALANCE,objAlStockMgn.getPrevBalance());
            objPstAlStockMgn.setDate(FLD_EXPIRED_DATE,objAlStockMgn.getExpiredDate());
            //update by satrya 2013-02-25
            objPstAlStockMgn.setDouble(FLD_EXTRA_AL,objAlStockMgn.getExtraAl());
            objPstAlStockMgn.setDate(FLD_EXTRA_AL_DATE,objAlStockMgn.getExtraAlDate());
            
            objPstAlStockMgn.insert();
            objAlStockMgn.setOID(objPstAlStockMgn.getlong(FLD_AL_STOCK_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAlStockManagement(0), DBException.UNKNOWN);
        }
        return objAlStockMgn.getOID();
    }
//update by satrya 2012-10-16
    /**
     * Keterangan: untuk listStockManagement
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
       public static Vector listStockManagement(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT *, AT.AL_TB_TKN FROM " + TBL_AL_STOCK_MANAGEMENT
                   + " LEFT JOIN sum_al_tb_taken_by_emp AS AT ON AT.EMPLOYEE_ID = "+TBL_AL_STOCK_MANAGEMENT+".EMPLOYEE_ID ";
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println("SQL "+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AlStockManagement alStockManagement = new AlStockManagement();
                resultToObject(rs, alStockManagement);
                //update by satrya 2012-10-16
                alStockManagement.setALtoBeTaken(rs.getFloat("AL_TB_TKN"));
            
                lists.add(alStockManagement);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println("Exception PstAlStockManagenment list: " +e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT * FROM " + TBL_AL_STOCK_MANAGEMENT;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println("SQL "+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AlStockManagement alStockManagement = new AlStockManagement();
                resultToObject(rs, alStockManagement);
                lists.add(alStockManagement);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println("Exception PstAlStockManagenment list: " +e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static boolean CekAdaDataStok(String whereClause) {
        boolean adaTidak = false;
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT * FROM " + TBL_AL_STOCK_MANAGEMENT;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            sql = sql + " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +" = " + 0; 
            sql =sql + " ORDER BY " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE] + " DESC ";
            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println("SQL "+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AlStockManagement alStockManagement = new AlStockManagement();
                resultToObject(rs, alStockManagement);
                long employeeId = alStockManagement.getEmployeeId();
                Date commencingDateClone = PstEmployee.getCommercingDatePerEmployee(employeeId); 
                
                Date selectedDateCom = new Date((commencingDateClone.getYear()+1), commencingDateClone.getMonth(), (commencingDateClone.getDate()));
                   
                if( alStockManagement.getEntitleDate().getTime() >= selectedDateCom.getTime()){
                    adaTidak = true;    
                }
                
                
            }
            rs.close();
            return adaTidak;

        } catch (Exception e) {
            System.out.println("Exception PstAlStockManagenment list: " +e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return adaTidak;
    }
    
      public static boolean CekPertamaClosing(String whereClause) {
        boolean adaTidak = false;
        DBResultSet dbrs = null;
        Vector nilai = new Vector();
        try {
            String sql = " SELECT * FROM " + TBL_AL_STOCK_MANAGEMENT;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            sql = sql + " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +" = " + 0; 
            sql =sql + " ORDER BY " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE] + " DESC ";
            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println("SQL "+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AlStockManagement alStockManagement = new AlStockManagement();
                resultToObject(rs, alStockManagement);
              
                nilai.add(alStockManagement);
                
            }
            rs.close();
            
            if (nilai.size() == 1){
                AlStockManagement alStockManagement = (AlStockManagement) nilai.get(0);
                if ((alStockManagement.getAlQty() == 0 ) && (alStockManagement.getEntitled() == 0 ) ){
                    return true;
                }
            }
            return adaTidak;

        } catch (Exception e) {
            System.out.println("Exception PstAlStockManagenment list: " +e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return adaTidak;
    }
    
    
    
    public static boolean afterComencingYear(long employeeId, Date opnameDate) {
        boolean status = false;
        DBResultSet dbrs = null;
        try {
                Employee employee = PstEmployee.fetchExc(employeeId);
                  
                Date nextEntitle =  (Date) employee.getCommencingDate().clone();
                nextEntitle.setYear(nextEntitle.getYear()+1);
             
                nextEntitle.setYear(nextEntitle.getYear()+1);
                nextEntitle.setMonth(0);
                nextEntitle.setDate(1);
                if( nextEntitle.getTime() <= opnameDate.getTime()){
                    status = true;    
                }

            return status;

        } catch (Exception e) {
            System.out.println("Exception PstAlStockManagenment list: " +e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return status;
    }

    public static Vector list(int limitStart, int recordToGet, long oidPeriod, long oidDepatment) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT AL." +fieldNames[FLD_AL_STOCK_ID]+
                    ",AL."+fieldNames[FLD_EMPLOYEE_ID]+
                    ",AL."+fieldNames[FLD_LEAVE_PERIODE_ID]+
                    ",AL."+fieldNames[FLD_OWNING_DATE]+
                    ",AL."+fieldNames[FLD_AL_QTY]+
                    ",AL."+fieldNames[FLD_QTY_RESIDUE]+
                    ",AL."+fieldNames[FLD_QTY_USED]+
                    ",AL."+fieldNames[FLD_AL_STATUS]+
                    ",AL."+fieldNames[FLD_NOTE]+
                    ",AL."+fieldNames[FLD_ENTITLED]+                    
                    ",AL."+fieldNames[FLD_COMMENCING_DATE_HAVE]+
                    ",EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                    ",EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                    ",LP."+PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_START_DATE]+
                    " FROM " + TBL_AL_STOCK_MANAGEMENT + " AL " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON " +
                    "AL." + fieldNames[FLD_EMPLOYEE_ID] + " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstLeavePeriod.TBL_HR_LEAVE_PERIOD + " LP ON " +
                    "AL." + fieldNames[FLD_LEAVE_PERIODE_ID] + " = LP." + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_LEAVE_PERIOD_ID] +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + oidDepatment +
                    " AND LP." + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_LEAVE_PERIOD_ID] + " = " + oidPeriod;

            if (recordToGet != 0)
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;

            //System.out.println("sQl = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector ls = new Vector(1,1);
                AlStockManagement alStockManagement = new AlStockManagement();
                Employee emp = new Employee();
                LeavePeriod lp = new LeavePeriod();
                resultToObject(rs, alStockManagement);
                ls.add(alStockManagement);

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


    public static void resultToObject(ResultSet rs, AlStockManagement objAlStockMgn) {
        try {
            objAlStockMgn.setOID(rs.getLong(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]));
            objAlStockMgn.setLeavePeriodeId(rs.getLong(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_LEAVE_PERIODE_ID]));
            objAlStockMgn.setEmployeeId(rs.getLong(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]));
            objAlStockMgn.setAlQty(rs.getFloat(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY]));
            float qty = objAlStockMgn.getAlQty();
            objAlStockMgn.setDtOwningDate(rs.getDate(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE]));
            objAlStockMgn.setAlStatus(rs.getInt(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]));
            objAlStockMgn.setStNote(rs.getString(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_NOTE]));
            objAlStockMgn.setQtyResidue(rs.getFloat(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE]));
            objAlStockMgn.setQtyUsed(rs.getFloat(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED]));
            objAlStockMgn.setEntitled(rs.getFloat(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED]));
            float entitled = objAlStockMgn.getEntitled();

            objAlStockMgn.setCommencingDateHave(rs.getDate(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_COMMENCING_DATE_HAVE]));
            objAlStockMgn.setOpening(rs.getFloat(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OPENING]));
            objAlStockMgn.setRecordDate(rs.getDate(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_RECORD_DATE]));
            objAlStockMgn.setEntitleDate(rs.getDate(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]));           
            objAlStockMgn.setPrevBalance(rs.getFloat(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_PREV_BALANCE]));
            // update by satrya 2014-12-19 objAlStockMgn.setExpiredDate(rs.getDate(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EXPIRED_DATE]));
            Date dtx=DBHandler.convertDate(rs.getDate(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EXPIRED_DATE]), rs.getTime(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EXPIRED_DATE]));
            if(dtx!=null){
                dtx.setHours(23);
                dtx.setMinutes(59);
                dtx.setSeconds(59);
            }
            objAlStockMgn.setExpiredDate(dtx);
            
            //update by satrya 2013-02-25
            objAlStockMgn.setExtraAl(rs.getDouble(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EXTRA_AL]));
            objAlStockMgn.setExtraAlDate(rs.getDate(PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EXTRA_AL_DATE]));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**gadnyana
     * untuk update entitled qty per employee dan tahun yang sama
     * @param entQty
     * @param oidEmployee
     * @param tahun
     */
    public void updateQtyEntitled(float entQty, long oidEmployee, int tahun){
        try{
            String sQL = "UPDATE "+TBL_AL_STOCK_MANAGEMENT+
                    " SET "+fieldNames[FLD_ENTITLED]+"="+entQty+
                    " WHERE YEAR("+fieldNames[FLD_OWNING_DATE]+")="+tahun+
                    " AND "+fieldNames[FLD_EMPLOYEE_ID]+"="+oidEmployee;
            DBHandler.execUpdate(sQL);
        }catch(Exception e){
            System.out.println("error updateQtyEntitled : "+e.toString());
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
    public static AlStockManagement checkAlStockManagement(long leavePerioId, long empOid) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        AlStockManagement alStockManagement = new AlStockManagement();
        try {
            String sql = "SELECT * FROM " + TBL_AL_STOCK_MANAGEMENT + " WHERE " +
                    fieldNames[FLD_LEAVE_PERIODE_ID] + "=" + leavePerioId +
                    " AND " + fieldNames[FLD_EMPLOYEE_ID] + "=" + empOid;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                resultToObject(rs, alStockManagement);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return alStockManagement;
        }
    }

/**
 * Untruk mengambil isi objek alstock managemnt
 * @param empOid
 * @return 
 */
     public static AlStockManagement getAlStockManagement(long empOid) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        AlStockManagement alStockManagement = new AlStockManagement();
        try {
            String sql = "SELECT * FROM " + TBL_AL_STOCK_MANAGEMENT + " WHERE " +
                    fieldNames[FLD_AL_STATUS] + "=" + PstAlStockManagement.AL_STS_AKTIF +
                    " AND " + fieldNames[FLD_EMPLOYEE_ID] + "=" + empOid;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                resultToObject(rs, alStockManagement);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return alStockManagement;
        }
    }

    /**
     * untuk mencari data al stock
     * di pakai oleh service
     * @param owningDate
     * @param empOid
     * @return
     */
    public static AlStockManagement checkAlStockManagement(Date owningDate, long empOid) 
    {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        AlStockManagement alStockManagement = new AlStockManagement();
        String strOwningDate = "\"" + Formater.formatDate(owningDate, "yyyy-MM-dd") + "\"";        
        try 
        {
            String sql = "SELECT * FROM " + TBL_AL_STOCK_MANAGEMENT + 
                         " WHERE " + fieldNames[FLD_OWNING_DATE] + 
                         "=" + strOwningDate +
                         " AND " + fieldNames[FLD_EMPLOYEE_ID] + 
                         "=" + empOid;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                resultToObject(rs, alStockManagement);
            }
            rs.close();
        }
        catch (Exception e) 
        {
            System.out.println("err : " + e.toString());
        }
        finally 
        {
            DBResultSet.close(dbrs);
            return alStockManagement;
        }
    }

    
    /** gadnyana
     * untuk mencari tanggal yang paling tinggi/terakhir
     * mendapatkan al
     * @param empOid
     * @return
     */
    public static Date getMaxALManagement(long empOid) {
        DBResultSet dbrs = null;
        Date dt = null;
        AlStockManagement alStockManagement = new AlStockManagement();
        try {
            String sql = "SELECT MAX("+fieldNames[FLD_OWNING_DATE]+") AS MAX_DT " +
                    " FROM " + TBL_AL_STOCK_MANAGEMENT + " WHERE " +
                    fieldNames[FLD_EMPLOYEE_ID] + "=" + empOid;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                dt = rs.getDate("MAX_DT");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return dt;
        }
    }


    /**
     * get first record of AlStockManagement with sort ascending by OWNING_DATE with active status
     * @return
     * @created by Edhy
     */    
    public static AlStockManagement getAlStockPerEmpFirst()
    {
        AlStockManagement alStockManagement = new AlStockManagement();
        DBResultSet dbrs;
        String stSQL = " SELECT " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_LEAVE_PERIODE_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] +                       
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_NOTE] +
                       " FROM "+ TBL_AL_STOCK_MANAGEMENT +
                       " WHERE " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                       //" = " + AL_STS_AKTIF + 
                       " IN (" + AL_STS_AKTIF + ", " + AL_STS_NOT_AKTIF + ")" +   
                       " ORDER BY " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE];
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            AlStockManagement objDpStockMgn = new AlStockManagement();
            while(rs.next())
            {
                alStockManagement.setOID(rs.getLong(1));
                alStockManagement.setLeavePeriodeId(rs.getLong(2));
                alStockManagement.setEmployeeId(rs.getLong(3));
                alStockManagement.setDtOwningDate(rs.getDate(4));                
                alStockManagement.setEntitled(rs.getFloat(5));
                alStockManagement.setAlQty(rs.getFloat(6));   
                alStockManagement.setQtyUsed(rs.getFloat(7));
                alStockManagement.setQtyResidue(rs.getFloat(8));
                alStockManagement.setAlStatus(rs.getInt(9));
                alStockManagement.setStNote(rs.getString(10));
                
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
        return alStockManagement;
    }
    

    /**
     * get first record of AlStockManagement with sort ascending by OWNING_DATE with active status
     * @return
     * @created by Edhy
     */    
    public static AlStockManagement getAlStockPerEmpFirst(long employeeId)
    {
        AlStockManagement alStockManagement = new AlStockManagement();
        DBResultSet dbrs;
        String stSQL = " SELECT " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_LEAVE_PERIODE_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] +                       
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_NOTE] +
                       " FROM "+ TBL_AL_STOCK_MANAGEMENT +
                       " WHERE " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                       //" = " + AL_STS_AKTIF +
                       " IN (" + AL_STS_AKTIF + ", " + AL_STS_NOT_AKTIF + ")" +   
                       " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " ORDER BY " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE];
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            AlStockManagement objDpStockMgn = new AlStockManagement();
            while(rs.next())
            {
                alStockManagement.setOID(rs.getLong(1));
                alStockManagement.setLeavePeriodeId(rs.getLong(2));
                alStockManagement.setEmployeeId(rs.getLong(3));
                alStockManagement.setDtOwningDate(rs.getDate(4));                
                alStockManagement.setEntitled(rs.getFloat(5));
                alStockManagement.setAlQty(rs.getFloat(6));   
                alStockManagement.setQtyUsed(rs.getFloat(7));
                alStockManagement.setQtyResidue(rs.getFloat(8));
                alStockManagement.setAlStatus(rs.getInt(9));
                alStockManagement.setStNote(rs.getString(10));
                
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
        return alStockManagement;
    }

    
    /**
     * get last record of AlStockManagement with sort descending by OWNING_DATE with active status
     * @return
     * @created by Edhy
     */    
    public static AlStockManagement getAlStockPerEmpLast()
    {
        AlStockManagement alStockManagement = new AlStockManagement();
        DBResultSet dbrs;
        String stSQL = " SELECT " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_LEAVE_PERIODE_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] +                       
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_NOTE] +
                       " FROM "+ TBL_AL_STOCK_MANAGEMENT +
                       //" WHERE " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                       //" = " + AL_STS_AKTIF +
                       //" IN (" + AL_STS_AKTIF + ", " + AL_STS_NOT_AKTIF + ")" +   
                       " ORDER BY " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] +
                       " DESC";
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            AlStockManagement objDpStockMgn = new AlStockManagement();
            while(rs.next())
            {
                alStockManagement.setOID(rs.getLong(1));
                alStockManagement.setLeavePeriodeId(rs.getLong(2));
                alStockManagement.setEmployeeId(rs.getLong(3));
                alStockManagement.setDtOwningDate(rs.getDate(4));                
                alStockManagement.setEntitled(rs.getFloat(5));
                alStockManagement.setAlQty(rs.getFloat(6));   
                alStockManagement.setQtyUsed(rs.getFloat(7));
                alStockManagement.setQtyResidue(rs.getFloat(8));
                alStockManagement.setAlStatus(rs.getInt(9));
                alStockManagement.setStNote(rs.getString(10));
                
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
        return alStockManagement;
    }
    

    /**
     * get last record of AlStockManagement with sort descending by OWNING_DATE with active status
     * per employee
     * @return
     * @created by Edhy
     */    
    public static AlStockManagement getAlStockPerEmpLast(long employeeId)
    {
        AlStockManagement alStockManagement = new AlStockManagement();
        DBResultSet dbrs;
        String stSQL = " SELECT " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_LEAVE_PERIODE_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] +                       
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_NOTE] +
                       " FROM "+ TBL_AL_STOCK_MANAGEMENT +
                       " WHERE " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +                       
                       " ORDER BY " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] +
                       " DESC";
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);  
            ResultSet rs = dbrs.getResultSet();
            AlStockManagement objDpStockMgn = new AlStockManagement();
            while(rs.next())
            {
                alStockManagement.setOID(rs.getLong(1));
                alStockManagement.setLeavePeriodeId(rs.getLong(2));
                alStockManagement.setEmployeeId(rs.getLong(3));
                alStockManagement.setDtOwningDate(rs.getDate(4));                
                alStockManagement.setEntitled(rs.getFloat(5));
                alStockManagement.setAlQty(rs.getFloat(6));   
                alStockManagement.setQtyUsed(rs.getFloat(7));
                alStockManagement.setQtyResidue(rs.getFloat(8));
                alStockManagement.setAlStatus(rs.getInt(9));
                alStockManagement.setStNote(rs.getString(10));
                
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
        return alStockManagement;
    }

    
    /**
     * get entitle value of last AlStockManagement record
     * @return
     * @created by Edhy
     */    
    public static float getEntitleAlLast(long employeeId)
    {        
        AlStockManagement alStockManagement = getAlStockPerEmpLast(employeeId);        
        return alStockManagement.getEntitled();
    }    

    
    /**
     * get last record of AlStockManagement with sort descending by OWNING_DATE with active status
     * per employee   
     * @return
     * @created by Edhy
     */    
    public static float getTotalEarnALYearly(long employeeId, Date dtYear)
    {
        float result = 0;
        DBResultSet dbrs;
        String stSQL = " SELECT SUM(" + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + ")" +
                       " FROM "+ TBL_AL_STOCK_MANAGEMENT +
                       " WHERE " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +               
                       " AND YEAR(" + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + ")" +  
                       " = \"" + (dtYear.getYear() + 1900) + "\"";
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);  
            ResultSet rs = dbrs.getResultSet();
            while(rs.next())
            {
                result = rs.getFloat(1);                
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
        return result;
    }
    
    /**
     * @param leavePeriodeId
     * @param employeeId
     * @param presenceDate
     * @return
     * @created by Edhy
     */    
    public static AlStockManagement getAlStockPerEmployee(long leavePeriodeId, long employeeId, Date presenceDate)
    {
        AlStockManagement alStockManagement = new AlStockManagement();
        DBResultSet dbrs;
        String stSQL = " SELECT " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_LEAVE_PERIODE_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] +                       
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                       ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_NOTE] +
                       " FROM "+ TBL_AL_STOCK_MANAGEMENT +
                       " WHERE " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_LEAVE_PERIODE_ID] +
                       " = " + leavePeriodeId +
                       " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] +
                       " = \"" + Formater.formatDate(presenceDate, "yyyy-MM-dd") + "\"";
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            AlStockManagement objDpStockMgn = new AlStockManagement();
            while(rs.next())
            {
                alStockManagement.setOID(rs.getLong(0));
                alStockManagement.setLeavePeriodeId(rs.getLong(1));
                alStockManagement.setEmployeeId(rs.getLong(2));
                alStockManagement.setDtOwningDate(rs.getDate(3));                
                alStockManagement.setEntitled(rs.getFloat(4));
                alStockManagement.setAlQty(rs.getFloat(5));   
                alStockManagement.setQtyUsed(rs.getFloat(6));
                alStockManagement.setQtyResidue(rs.getFloat(7));
                alStockManagement.setAlStatus(rs.getInt(8));
                alStockManagement.setStNote(rs.getString(9));
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
        return alStockManagement;
    }
    
    /**
     * @param presenceDate
     * @param employeeId  
     */
    public void updateAlStockManagementByPresence(Date presenceDate, long employeeId) 
    {          
        // mencari jumlah stock AL yang terpakai dan sisanya
        // jika Dp itu sudah terpakai semua, maka set status menjadi TAKEN
        AlStockManagement alStockManagement = getAlStockPerEmpFirst(employeeId);    
        float intAlUsed  = alStockManagement.getQtyUsed()+1;
        float intResidue = alStockManagement.getAlQty() - intAlUsed;
        alStockManagement.setQtyUsed(intAlUsed);
        alStockManagement.setQtyResidue(intResidue);          
        if(intResidue == 0) 
        {
            alStockManagement.setAlStatus(PstAlStockManagement.AL_STS_TAKEN);
        }

        try 
        {            
            // jika ada / punya Al stock yang active
            boolean alTakenExist = PstAlStockTaken.existAlStockTaken(employeeId, presenceDate);
            if( (alStockManagement.getOID()!=0) && (!alTakenExist) )
            {                             
                PstAlStockManagement.updateExc(alStockManagement);  
            }
            
            // insert data di Al stock taken              
            if(!alTakenExist) 
            {                                        
                // insert data di Dp stock taken  
                PstAlStockTaken objPstAlStockTaken = new PstAlStockTaken();
                AlStockTaken objAlStockTaken = new AlStockTaken();
                objAlStockTaken.setAlStockId(alStockManagement.getOID());  
                objAlStockTaken.setEmployeeId(employeeId);
                objAlStockTaken.setTakenDate(presenceDate);
                objAlStockTaken.setTakenQty(1);                        
                objPstAlStockTaken.insertExc(objAlStockTaken);                
            }
        }
        catch(Exception e)  
        {
            System.out.println("Exc when update AL stock on PstAlStockManagement : " + e.toString());
        }
    }

    
    /**
     * @created by Edhy
     * @param presenceDate
     * @param employeeId
     */
    public void deleteALStockManagement(long employeeId, Date presenceDate) 
    {        
        long leavePeriodeId = PstPeriod.getPeriodeIdBetween(presenceDate);                
        AlStockManagement alStockManagement = getAlStockPerEmployee(leavePeriodeId, employeeId, presenceDate);
        try 
        {
            PstAlStockManagement.deleteExc(alStockManagement.getOID()); 
        }
        catch(Exception e) 
        { 
            System.out.println("Exc when delete AL stock on PstALStockManagement : " + e.toString());
        }
    }    
    
    /**
     * @created by Edhy
     * @param presenceDate
     * @param employeeId
     */
    public static AlStockManagement generateNewALTakenImmediately(Date owningDate, long employeeId, long leavePeriodId) 
    {    
        AlStockManagement alStockManagement = new AlStockManagement();
        
        alStockManagement.setAlStatus(AL_STS_TAKEN);
        alStockManagement.setAlQty(0);
        alStockManagement.setDtOwningDate(owningDate);
        alStockManagement.setEmployeeId(employeeId);
        alStockManagement.setEntitled(12);
        alStockManagement.setLeavePeriodeId(leavePeriodId);
        alStockManagement.setQtyUsed(1);
        alStockManagement.setQtyResidue(alStockManagement.getAlQty() - alStockManagement.getQtyUsed());
        alStockManagement.setStNote("AL Debt on " + owningDate);        
        
        return alStockManagement;
    }        
    
    
    /**
     * @param selectedDate
     * @param employeeId
     * @return
     * @created by Edhy  
     */    
    public float getAlStockAmountPerEmployee(long employeeId)
    {
        float result = 0;
        DBResultSet dbrs = null;
        String stSQL = " SELECT SUM(" + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE] + ")" +
                       " FROM "+ TBL_AL_STOCK_MANAGEMENT +
                       " WHERE " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +                       
                       " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                       " = " + PstAlStockManagement.AL_STS_AKTIF;
        try
        {
//            System.out.println(getClass().getName() + ".getAlStockAmountPerEmployee() stSQL : " + stSQL);            
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                result = rs.getFloat(1);
                break;
            }
        }
        catch(Exception e)
        {
            System.out.println(getClass().getName() + ".getAlStockAmountPerEmployee() exc : " + e.toString());            
        }
        finally
        {
            DBResultSet.close(dbrs);
        }
        return result;
    }
    
    
    /** 
     * Proses pebayaran hutang AL, dengan asumsi vectDpStock yang berisi object AlStock tidak null/kosong          
     * @param objAlStockManagement
     * @param employeeId
     * @return
     * @created by Edhy
     */    
    public static Vector paidAlPayable(long employeeId, AlStockManagement objAlStockManagement)
    {        
        Vector result = new Vector(1,1);
        Vector listAlPayable = PstAlStockTaken.getAlPayable(employeeId);
        if(listAlPayable!=null && listAlPayable.size()>0)
        {                        
            long alStockManagementOid = objAlStockManagement.getOID();
            Date dtAlStockActive = objAlStockManagement.getDtOwningDate();
            float maxAlStock = objAlStockManagement.getQtyResidue();
            int maxAlPayable = listAlPayable.size();
            float maxIterate = (maxAlStock >= maxAlPayable) ? maxAlPayable : maxAlStock;                                    
            for(int i=0; i<maxIterate; i++)
            {
                try
                {
                    AlStockTaken objAlStockTaken = (AlStockTaken) listAlPayable.get(i);
                    objAlStockTaken.setAlStockId(alStockManagementOid);
                    objAlStockTaken.setPaidDate(dtAlStockActive);
                    
                    long updatedOid = PstAlStockTaken.updateExc(objAlStockTaken);
                    result.add(String.valueOf(updatedOid));
                }
                catch(Exception e)
                {
                    System.out.println("Exc when update objAlStockTaken : " + e.toString());
                }
            }            
            
            // update object AlStockManagement karena semua Qty sudah digunakan utk membayar hutang
            try
            {
                if(result!=null && result.size()>0)
                {
                    float alUsed = objAlStockManagement.getQtyUsed();    
                    objAlStockManagement.setQtyUsed(alUsed + result.size());
                    objAlStockManagement.setQtyResidue(objAlStockManagement.getAlQty() - objAlStockManagement.getQtyUsed());
                    if(objAlStockManagement.getQtyResidue() == 0)
                    {                        
                        objAlStockManagement.setAlStatus(PstAlStockManagement.AL_STS_TAKEN);
                    }
                    long alManUpdatedOid = PstAlStockManagement.updateExc(objAlStockManagement);
                }
            }
            catch(Exception e)
            {
                System.out.println("Exc when update object AlStockManagement : " + e.toString());
            }            
        }
        
        return result;
    }    

    
    /**
     * Proses generate data AL Stock Taken sesuai dengan data Al used pada object DP Management     
     *
     * @return
     * @created by Edhy
     */        
    public static Vector generateAlTaken()
    {        
        return generateAlTaken(0);
    }
    
    /**
     * Proses generate data AL Stock Taken sesuai dengan data Al used pada object DP Management     
     * @param employeeId
     *
     * @return
     * @created by Edhy
     */        
    public static Vector generateAlTaken(long employeeId)
    {        
        Vector result = new Vector(1,1);
        String strCommCondition = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                                  " IN (" + PstAlStockManagement.AL_STS_TAKEN + "," + PstAlStockManagement.AL_STS_TAKEN + ")" +
                                  " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] +
                                  " > 0";   
        
        String strEmployeeCondition = "";
        if(employeeId != 0)
        {
            //update by satrya 2012-10-16
            strEmployeeCondition = PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT+"."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = " + employeeId;            
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
        
        String strOrderBy = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE];
        Vector vectOfAlStock = PstAlStockManagement.list(0, 0, whereClause, strOrderBy);
        if(vectOfAlStock!=null && vectOfAlStock.size()>0)
        {
            int maxVectOfAlStock = vectOfAlStock.size();
            for(int i=0; i<maxVectOfAlStock; i++)
            {
                AlStockManagement objAlStockManagement = (AlStockManagement) vectOfAlStock.get(i);
                float maxAlUsed = objAlStockManagement.getQtyUsed();
                for(int j=0; j<maxAlUsed; j++)
                {
                    AlStockTaken objAlStockTaken = new AlStockTaken();
                    objAlStockTaken.setAlStockId(objAlStockManagement.getOID());
                    objAlStockTaken.setEmployeeId(objAlStockManagement.getEmployeeId());
                    objAlStockTaken.setTakenDate(objAlStockManagement.getDtOwningDate());
                    objAlStockTaken.setTakenQty(1);
                    objAlStockTaken.setPaidDate(objAlStockManagement.getDtOwningDate());
                    
                    try
                    {
                        long oid = PstAlStockTaken.insertExc(objAlStockTaken);
                        result.add(String.valueOf(oid));
                    }
                    catch(Exception e) 
                    {
                        System.out.println("Exc when generateAlTaken : " + e.toString());
                    }
                }
            }                
        }
        
        return result;
    }


    
    
    /**
     * Proses generate data AL Stock Expired sesuai dengan data Al expired pada object AL Management     
     *
     * @return
     * @created by Edhy
     */        
    public static Vector generateAlExpired()
    {        
        return generateAlExpired(0);
    }
    
    /**
     * Proses generate data Al Stock Expired sesuai dengan data Al used pada object AL Management
     * @param employeeId
     *
     * @return
     * @created by Edhy
     */        
    public static Vector generateAlExpired(long employeeId)
    {        
        Vector result = new Vector(1,1);
        String strCommCondition = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                                  " = " + PstAlStockManagement.AL_STS_EXPIRED;
        
        String strEmployeeCondition = "";
        if(employeeId != 0)
        {

            strEmployeeCondition = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = " + employeeId;            
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
        
        String strOrderBy = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE];
        Vector vectOfAlStock = PstAlStockManagement.list(0, 0, whereClause, strOrderBy);
        if(vectOfAlStock!=null && vectOfAlStock.size()>0)
        {  
            int maxVectOfAlStock = vectOfAlStock.size();
            for(int i=0; i<maxVectOfAlStock; i++)
            {
                AlStockManagement objAlStockManagement = (AlStockManagement) vectOfAlStock.get(i);
                float maxAlUsed = objAlStockManagement.getQtyUsed();
                for(int j=0; j<maxAlUsed; j++)  
                {
                    AlStockExpired objAlStockExpired = new AlStockExpired();
                    objAlStockExpired.setAlStockId(objAlStockManagement.getOID());                    
                    objAlStockExpired.setExpiredDate(objAlStockManagement.getDtOwningDate());
                    objAlStockExpired.setExpiredQty(1.0f);                    
                    
                    try
                    {
                        long oid = PstAlStockExpired.insertExc(objAlStockExpired);
                        result.add(String.valueOf(oid));
                    }
                    catch(Exception e) 
                    {
                        System.out.println("Exc when generateAlExpired : " + e.toString());
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
    public void deleteAlStockManagementPerDepartment(long lDepartmentOid) 
    {                
        DBResultSet dbrs = null;
        String stSQL = " SELECT AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] +                       
                       ", AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                       " FROM "+ TBL_AL_STOCK_MANAGEMENT + " AS AL" +
                       " INNER JOIN "+ PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                       " ON AL." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] +
                       " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                       
                       " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                       " = " + lDepartmentOid;
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                long lAlStockOid = rs.getLong(1);
                long lEmployeeOid = rs.getLong(2);
                
                // delete Al expired
                PstDpStockExpired.deleteByDpStock(lAlStockOid);
                
                // delete Al stock
                try
                {
                    long oidAlStock = PstAlStockManagement.deleteExc(lAlStockOid);
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
    public void deleteAlStockPerDepartment(long lDepartmentOid) 
    {   
        PstAlStockTaken objPstAlStockTaken = new PstAlStockTaken();
        objPstAlStockTaken.deleteAlStockTakenPerDepartment(lDepartmentOid); 
        deleteAlStockManagementPerDepartment(lDepartmentOid);
    }    
    
    
    /**
     * @param args
     */    
    public static void main(String args[])
    {
        System.out.println("Start process");        
        
        /*
        Vector vectResult = generateAlTaken();
        if(vectResult!=null && vectResult.size()>0)
        {
            int maxVectResult = vectResult.size();
            for(int i=0; i<maxVectResult; i++) 
            {
                System.out.println("oid ke " + (i+1) + " = " + String.valueOf(vectResult.get(i)));
            }
        } 
        */               
        
        
        Vector vectResult = generateAlExpired();
        if(vectResult!=null && vectResult.size()>0)  
        {            
            int maxVectResult = vectResult.size();
            for(int i=0; i<maxVectResult; i++)
            {
                System.out.println("oid ke " + (i+1) + " = " + String.valueOf(vectResult.get(i)));
            }
        }        
        
        System.out.println("Stop process");
    }    
    
    //khsusus untuk resetAL
    public static void resetAL(long oid) {
        DBResultSet dbrs = null;
        //boolean result = false;
        
        try{
            String sql = " UPDATE " + TBL_AL_STOCK_MANAGEMENT + 
                " SET " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + " = 0 "+
                "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] + " = 0 "+
                "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE]+ "= 0"+
                "," + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED]+ "= 0"+
                " where " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+"="+oid;

            int status = DBHandler.execUpdate(sql);
            
            //add by artha
            PstAlStockTaken.resetAL(oid);
            Vector vListAlMan = new Vector(1,1);
            //update by satrya 2012-10-16
            String where = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+"="+oid;
            vListAlMan = PstAlStockManagement.list(0, 0, where, null);
            for(int i=0;i<vListAlMan.size();i++){
                AlStockManagement objAlStockManagement = (AlStockManagement)vListAlMan.get(i);
                PstAlStockExpired.deleteByAlStock(objAlStockManagement.getOID());
            }
            
            System.out.println("SQL RESET AL  "+sql);
        } catch(Exception e) {
            System.err.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    
    public static Vector listByCheck(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_AL_STOCK_MANAGEMENT;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            dbrs = DBHandler.execQueryResult(sql);
            System.out.println("SQL "+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AlStockManagement alStockManagement = new AlStockManagement();
                resultToObject(rs, alStockManagement);
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
    
    public static String getDateClose(Date entitled){
        
        String result = "";
        
        
        
        
        return result;
    }

    
    
}
