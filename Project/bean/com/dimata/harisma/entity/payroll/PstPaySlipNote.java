/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class PstPaySlipNote extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    
    public static final String TBL_PAY_SLIP_NOTE = "pay_slip_note";//"PAY_SLIP";
    public static final int FLD_PAY_SLIP_NOTE_ID = 0; 
    public static final int FLD_PAY_SLIP_NOTE_DATE = 1;
    public static final int FLD_PAY_SLIP_NOTE_PERIODE = 2;
    public static final int FLD_PAY_SLIP_ID = 3;
    public static final int FLD_PAY_SLIP_EMPLOYEE_ID = 4;
    public static final int FLD_PAY_SLIP_NOTE = 5;
    
    
    public static final String[] fieldNames = {
        "PAY_SLIP_NOTE_ID",
        "PAY_SLIP_NOTE_DATE",
        "PAY_SLIP_NOTE_PERIODE",
        "PAY_SLIP_ID",
        "PAY_SLIP_EMPLOYEE_ID",
        "PAY_SLIP_NOTE"
        
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING
        
    };
    
     public PstPaySlipNote() {
    }
     
     public PstPaySlipNote(int i) throws DBException {
        super(new PstPaySlipNote());
    }

    public PstPaySlipNote(String sOid) throws DBException {
        super(new PstPaySlipNote(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPaySlipNote(long lOid) throws DBException {
        super(new PstPaySlipNote(0));
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
    /*
     *  This method used to update working day in pay slip 
     */
    public static void updateNote(String note, long employeeId, long periodId, long paySlipId,Date dtNote) {

        DBResultSet dbrs = null;
        try {
            String sql = "";
            sql = " UPDATE " + TBL_PAY_SLIP_NOTE + " SET "
                    + PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_NOTE] + " =  '" + note + "'"
                    + " WHERE " + PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_EMPLOYEE_ID] + " = " + employeeId
                    + " AND " + PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_ID] + " = " + paySlipId
                    + " AND " + PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_NOTE_DATE] + " = " + dtNote
                    + " AND " + PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_NOTE_PERIODE] + " = " + periodId;

            int status = DBHandler.execUpdate(sql);
            //System.out.println("\tupdateNote : " + sql);
        } catch (Exception e) {
            System.err.println("\tupdateNote : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            //return result;
        }
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_PAY_SLIP_NOTE;
    }

    public String[] getFieldNames() {
       return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
         return new PstPaySlip().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
         PaySlipNote paySlipNote = fetchExc(ent.getOID());
        ent = (Entity) paySlipNote;
        return paySlipNote.getOID();
    }

    public long updateExc(Entity ent) throws Exception {
      return updateExc((PaySlipNote) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
       if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public long insertExc(Entity ent) throws Exception {
       return insertExc((PaySlipNote) ent);
    }
    
     public static PaySlipNote fetchExc(long oid) throws DBException {
        try {
            PaySlipNote paySlipNote = new PaySlipNote();
            PstPaySlipNote pstPaySlipNote = new PstPaySlipNote(oid);
            paySlipNote.setOID(oid);
            paySlipNote.setDtPaySlipNote(pstPaySlipNote.getDate(FLD_PAY_SLIP_NOTE_DATE));
            paySlipNote.setPeriodId(pstPaySlipNote.getlong(FLD_PAY_SLIP_NOTE_PERIODE));
            paySlipNote.setPaySlipId(pstPaySlipNote.getlong(FLD_PAY_SLIP_ID));
            paySlipNote.setEmployeeId(pstPaySlipNote.getlong(FLD_PAY_SLIP_EMPLOYEE_ID));
            paySlipNote.setNote(pstPaySlipNote.getString(FLD_PAY_SLIP_NOTE));
            
            return paySlipNote;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySlip(0), DBException.UNKNOWN);
        }
    }
     
     public static long insertExc(PaySlipNote paySlipNote) throws DBException {
        try {
            PstPaySlipNote pstPaySlipNote = new PstPaySlipNote(0);
            pstPaySlipNote.setDate(FLD_PAY_SLIP_NOTE_DATE, paySlipNote.getDtPaySlipNote());
            pstPaySlipNote.setLong(FLD_PAY_SLIP_NOTE_PERIODE, paySlipNote.getPeriodId());
            pstPaySlipNote.setLong(FLD_PAY_SLIP_ID, paySlipNote.getPaySlipId());
            pstPaySlipNote.setLong(FLD_PAY_SLIP_EMPLOYEE_ID, paySlipNote.getEmployeeId());
            pstPaySlipNote.setString(FLD_PAY_SLIP_NOTE, paySlipNote.getNote());
            
            
            pstPaySlipNote.insert();
            paySlipNote.setOID(pstPaySlipNote.getlong(FLD_PAY_SLIP_NOTE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySlip(0), DBException.UNKNOWN);
        }
        return paySlipNote.getOID();
    }
     
     public static long updateExc(PaySlipNote paySlipNote) throws DBException {
        try {
            if (paySlipNote.getOID() != 0) {
                PstPaySlipNote pstPaySlipNote = new PstPaySlipNote(paySlipNote.getOID());
                 pstPaySlipNote.setDate(FLD_PAY_SLIP_NOTE_DATE, paySlipNote.getDtPaySlipNote());
            pstPaySlipNote.setLong(FLD_PAY_SLIP_NOTE_PERIODE, paySlipNote.getPeriodId());
            pstPaySlipNote.setLong(FLD_PAY_SLIP_ID, paySlipNote.getPaySlipId());
            pstPaySlipNote.setLong(FLD_PAY_SLIP_EMPLOYEE_ID, paySlipNote.getEmployeeId());
            pstPaySlipNote.setString(FLD_PAY_SLIP_NOTE, paySlipNote.getNote());
                
                pstPaySlipNote.update();
                return paySlipNote.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySlip(0), DBException.UNKNOWN);
        }
        return 0;
    }
     
     public static long deleteExc(long oid) throws DBException {
        try {
            PstPaySlipNote pstPaySlipNote = new PstPaySlipNote(oid);
            pstPaySlipNote.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySlip(0), DBException.UNKNOWN);
        }
        return oid;
    }
     
      public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_SLIP_NOTE;
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
            //System.out.println("SQL LIST Pay Slip"+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PaySlipNote paySlipNote = new PaySlipNote();
                resultToObject(rs, paySlipNote);
                lists.add(paySlipNote);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
             return lists;
        }
       
    }
      
      public static void resultToObject(ResultSet rs, PaySlipNote paySlipNote) {
        try {
            paySlipNote.setOID(rs.getLong(PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_NOTE_ID]));
            paySlipNote.setDtPaySlipNote(rs.getDate(PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_NOTE_DATE]));
            paySlipNote.setPeriodId(rs.getLong(PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_NOTE_PERIODE]));
            
            paySlipNote.setPaySlipId(rs.getLong(PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_ID]));
            paySlipNote.setEmployeeId(rs.getLong(PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_EMPLOYEE_ID]));
            paySlipNote.setNote(rs.getString(PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_NOTE]));
          
            
        } catch (Exception e) {
        }
    }
}
