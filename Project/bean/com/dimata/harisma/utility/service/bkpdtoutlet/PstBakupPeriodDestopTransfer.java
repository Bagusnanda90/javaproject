
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: Satrya Ramayu
 * @version  	: 01
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.utility.service.bkpdtoutlet;

/* package java */
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.entity.*;

//Gede_7Feb2012 {
import com.dimata.harisma.session.employee.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.entity.masterdata.*;
//}

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.employee.*;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;

/**
 * Ari_20111002
 * Menambah Company, Division, Level dan EmpCategory
 * @author Wiweka
 */
public class PstBakupPeriodDestopTransfer extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_PERIOD = "hr_period";//"HR_PERIOD";

    public static final int FLD_PERIOD_ID = 0;
    public static final int FLD_PERIOD = 1;
    public static final int FLD_START_DATE = 2;
    public static final int FLD_END_DATE = 3;
    
    public static final int FLD_WORK_DAYS = 4;
    public static final int FLD_PAY_SLIP_DATE = 5;
    public static final int FLD_MIN_REG_WAGE = 6;
    
    public static final int FLD_PAY_PROCESS = 7;
    public static final int FLD_PAY_PROC_BY = 8;
    public static final int FLD_PAY_PROC_DATE = 9;
    
    public static final int FLD_TAX_IS_PAID = 10;
    public static final int FLD_PAY_PROCESS_CLOSE = 11;
    public static final int FLD_PAY_PROC_BY_CLOSE = 12;
    public static final int FLD_PAY_PROC_DATE_CLOSE = 13;

    public static final String[] fieldNames = {
        "PERIOD_ID",
        "PERIOD",
        "START_DATE",
        "END_DATE",
        
        "WORK_DAYS",
        "PAY_SLIP_DATE",
        "MIN_REG_WAGE",
        
        "PAY_PROCESS",
        "PAY_PROC_BY",
        "PAY_PROC_DATE",
        
        "TAX_IS_PAID",
        "PAY_PROCESS_CLOSE",
        "PAY_PROC_BY_CLOSE",
        "PAY_PROC_DATE_CLOSE"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        
        TYPE_INT,
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_INT,
        
        TYPE_INT,
        TYPE_STRING,
        TYPE_DATE
        
    };

    public PstBakupPeriodDestopTransfer() {
    }

    public PstBakupPeriodDestopTransfer(int i) throws DBException {
        super(new PstBakupPeriodDestopTransfer());
    }

    public PstBakupPeriodDestopTransfer(String sOid) throws DBException {
        super(new PstBakupPeriodDestopTransfer(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstBakupPeriodDestopTransfer(long lOid) throws DBException {
        super(new PstBakupPeriodDestopTransfer(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_HR_PERIOD;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstBakupPeriodDestopTransfer().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
       return 0;
    }

    public long insertExc(Entity ent) throws Exception {
        return 0;
    }

    public long updateExc(Entity ent) throws Exception {
        return 0;
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static String insertExc(TabelEmployeeScheduleTransfer tabelEmployeeScheduleTransfer) throws DBException {
        String sql="";
        try {
            PstBakupPeriodDestopTransfer pstPositionDestopTransfer = new PstBakupPeriodDestopTransfer(0);

            pstPositionDestopTransfer.setString(FLD_PERIOD, tabelEmployeeScheduleTransfer.getNamaPeriod());
            
            pstPositionDestopTransfer.setDate(FLD_START_DATE, tabelEmployeeScheduleTransfer.getDtStartPeriod());
            pstPositionDestopTransfer.setDate(FLD_END_DATE, tabelEmployeeScheduleTransfer.getDtEndPeriod());
            
            
            sql = pstPositionDestopTransfer.SyntacInsert(tabelEmployeeScheduleTransfer.getPeriodId());
        }catch (Exception e) {
            System.out.print("Exc"+e);
        }
        return sql;
    }

    public static long updateExc(TabelEmployeeScheduleTransfer tabelEmployeeScheduleTransfer) throws DBException {
        try {
            if (tabelEmployeeScheduleTransfer.getPeriodId() != 0) {
                PstBakupPeriodDestopTransfer pstPositionDestopTransfer = new PstBakupPeriodDestopTransfer(tabelEmployeeScheduleTransfer.getPeriodId());

               
              pstPositionDestopTransfer.setString(FLD_PERIOD, tabelEmployeeScheduleTransfer.getNamaPeriod());
            
            pstPositionDestopTransfer.setDate(FLD_START_DATE, tabelEmployeeScheduleTransfer.getDtStartPeriod());
            pstPositionDestopTransfer.setDate(FLD_END_DATE, tabelEmployeeScheduleTransfer.getDtEndPeriod());
            

                pstPositionDestopTransfer.update();
                return tabelEmployeeScheduleTransfer.getPeriodId();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBakupPeriodDestopTransfer(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstBakupPeriodDestopTransfer pstCareerPath = new PstBakupPeriodDestopTransfer(oid);
            pstCareerPath.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBakupPeriodDestopTransfer(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static boolean checkOID(long periodId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_PERIOD + " WHERE "
                    + PstBakupPeriodDestopTransfer.fieldNames[PstBakupPeriodDestopTransfer.FLD_PERIOD_ID] + " = " + periodId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    public static Hashtable<String,Boolean> hashPeriodSdhAda(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashPeriodSdhAda= new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "+fieldNames[FLD_PERIOD_ID]+" FROM " + TBL_HR_PERIOD;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                hashPeriodSdhAda.put(""+rs.getLong(fieldNames[FLD_PERIOD_ID]), true);
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashPeriodSdhAda;
        }
        
    }
}
