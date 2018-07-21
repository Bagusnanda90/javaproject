/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author khirayinnura
 */
public class PstOutSourceCost extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_OUTSOURCE_COST = "hr_outsource_cost";
    public static final int FLD_OUTSOURCE_COST_ID = 0;
    public static final int FLD_TITLE = 1;
    public static final int FLD_COMPANY_ID = 2;
    public static final int FLD_CREATED_DATE = 3;
    public static final int FLD_CREATED_BY_ID = 4;
    public static final int FLD_APPROVED_DATE = 5;
    public static final int FLD_APPROVED_BY_ID = 6;
    public static final int FLD_NOTE = 7;
    public static final int FLD_STATUS_DOC = 8;
    public static final int FLD_DIVISION_ID = 9;
    public static final int FLD_DEPARTMENT_ID = 10;
    public static final int FLD_SECTION_ID = 11;
    public static final int FLD_DATE_OF_PAY = 12;
    public static final int FLD_PERIOD_ID = 13;
    public static String[] fieldNames = {
        "OUTSOURCE_COST_ID",
        "TITLE",
        "COMPANY_ID",
        "CREATED_DATE",
        "CREATED_BY_ID",
        "APPROVED_DATE",
        "APPROVED_BY_ID",
        "NOTE",
        "STATUS_DOC",
        "DIVISION_ID",
        "DEPARTMENT_ID",
        "SECTION_ID",
        "DATE_OF_PAYMENT",
        "PERIOD_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG
    };

    public PstOutSourceCost() {
    }

    public PstOutSourceCost(int i) throws DBException {
        super(new PstOutSourceCost());
    }

    public PstOutSourceCost(String sOid) throws DBException {
        super(new PstOutSourceCost(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstOutSourceCost(long lOid) throws DBException {
        super(new PstOutSourceCost(0));
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
        return TBL_OUTSOURCE_COST;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstOutSourceCost().getClass().getName();
    }

    public static OutSourceCost fetchExc(long oid) throws DBException {
        try {
            OutSourceCost entOutSourceCost = new OutSourceCost();
            PstOutSourceCost pstOutSourceCost = new PstOutSourceCost(oid);
            entOutSourceCost.setOID(oid);
            entOutSourceCost.setTitle(pstOutSourceCost.getString(FLD_TITLE));
            entOutSourceCost.setCompanyId(pstOutSourceCost.getLong(FLD_COMPANY_ID));
            entOutSourceCost.setCreatedDate(pstOutSourceCost.getDate(FLD_CREATED_DATE));
            entOutSourceCost.setCreatedById(pstOutSourceCost.getLong(FLD_CREATED_BY_ID));
            entOutSourceCost.setApprovedDate(pstOutSourceCost.getDate(FLD_APPROVED_DATE));
            entOutSourceCost.setApprovedById(pstOutSourceCost.getLong(FLD_APPROVED_BY_ID));
            entOutSourceCost.setNote(pstOutSourceCost.getString(FLD_NOTE));
            entOutSourceCost.setStatusDoc(pstOutSourceCost.getInt(FLD_STATUS_DOC));
            entOutSourceCost.setDivisionId(pstOutSourceCost.getLong(FLD_DIVISION_ID));
            entOutSourceCost.setDepartmentId(pstOutSourceCost.getLong(FLD_DEPARTMENT_ID));
            entOutSourceCost.setSectionId(pstOutSourceCost.getLong(FLD_SECTION_ID));
            entOutSourceCost.setDateOfPay(pstOutSourceCost.getDate(FLD_DATE_OF_PAY));
            entOutSourceCost.setPeriodId(pstOutSourceCost.getLong(FLD_PERIOD_ID));
            return entOutSourceCost;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceCost(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        OutSourceCost entOutSourceCost = fetchExc(entity.getOID());
        entity = (Entity) entOutSourceCost;
        return entOutSourceCost.getOID();
    }

    public static synchronized long updateExc(OutSourceCost entOutSourceCost) throws DBException {
        try {
            if (entOutSourceCost.getOID() != 0) {
                PstOutSourceCost pstOutSourceCost = new PstOutSourceCost(entOutSourceCost.getOID());
                pstOutSourceCost.setString(FLD_TITLE, entOutSourceCost.getTitle());
                pstOutSourceCost.setLong(FLD_COMPANY_ID, entOutSourceCost.getCompanyId());
                pstOutSourceCost.setDate(FLD_CREATED_DATE, entOutSourceCost.getCreatedDate());
                pstOutSourceCost.setLong(FLD_CREATED_BY_ID, entOutSourceCost.getCreatedById());
                pstOutSourceCost.setDate(FLD_APPROVED_DATE, entOutSourceCost.getApprovedDate());
                pstOutSourceCost.setLong(FLD_APPROVED_BY_ID, entOutSourceCost.getApprovedById());
                pstOutSourceCost.setString(FLD_NOTE, entOutSourceCost.getNote());
                pstOutSourceCost.setInt(FLD_STATUS_DOC, entOutSourceCost.getStatusDoc());
                pstOutSourceCost.setLong(FLD_DIVISION_ID, entOutSourceCost.getDivisionId());
                pstOutSourceCost.setLong(FLD_DEPARTMENT_ID, entOutSourceCost.getDepartmentId());
                pstOutSourceCost.setLong(FLD_SECTION_ID, entOutSourceCost.getSectionId());
                pstOutSourceCost.setDate(FLD_DATE_OF_PAY, entOutSourceCost.getDateOfPay());
                pstOutSourceCost.setLong(FLD_PERIOD_ID, entOutSourceCost.getPeriodId());
                pstOutSourceCost.update();
                return entOutSourceCost.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceCost(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((OutSourceCost) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstOutSourceCost pstOutSourceCost = new PstOutSourceCost(oid);
            pstOutSourceCost.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceCost(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(OutSourceCost entOutSourceCost) throws DBException {
        try {
            PstOutSourceCost pstOutSourceCost = new PstOutSourceCost(0);
            pstOutSourceCost.setString(FLD_TITLE, entOutSourceCost.getTitle());
            pstOutSourceCost.setLong(FLD_COMPANY_ID, entOutSourceCost.getCompanyId());
            pstOutSourceCost.setDate(FLD_CREATED_DATE, entOutSourceCost.getCreatedDate());
            pstOutSourceCost.setLong(FLD_CREATED_BY_ID, entOutSourceCost.getCreatedById());
            pstOutSourceCost.setDate(FLD_APPROVED_DATE, entOutSourceCost.getApprovedDate());
            pstOutSourceCost.setLong(FLD_APPROVED_BY_ID, entOutSourceCost.getApprovedById());
            pstOutSourceCost.setString(FLD_NOTE, entOutSourceCost.getNote());
            pstOutSourceCost.setInt(FLD_STATUS_DOC, entOutSourceCost.getStatusDoc());
            pstOutSourceCost.setLong(FLD_DIVISION_ID, entOutSourceCost.getDivisionId());
            pstOutSourceCost.setLong(FLD_DEPARTMENT_ID, entOutSourceCost.getDepartmentId());
            pstOutSourceCost.setLong(FLD_SECTION_ID, entOutSourceCost.getSectionId());
            pstOutSourceCost.setDate(FLD_DATE_OF_PAY, entOutSourceCost.getDateOfPay());
            pstOutSourceCost.setLong(FLD_PERIOD_ID, entOutSourceCost.getPeriodId());
            pstOutSourceCost.insert();
            entOutSourceCost.setOID(pstOutSourceCost.getlong(FLD_OUTSOURCE_COST_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceCost(0), DBException.UNKNOWN);
        }
        return entOutSourceCost.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((OutSourceCost) entity);
    }
    
    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_OUTSOURCE_COST;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            
           // System.out.println(":::::::::::::::::: "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                OutSourceCost outSourceCost = new OutSourceCost();
                resultToObject(rs, outSourceCost);
                lists.add(outSourceCost);
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
    
    public static void resultToObject(ResultSet rs, OutSourceCost entOutSourceCost) {
        try {
            entOutSourceCost.setTitle(rs.getString(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_TITLE]));
            entOutSourceCost.setCompanyId(rs.getLong(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_COMPANY_ID]));
            entOutSourceCost.setCreatedDate(rs.getDate(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_CREATED_DATE]));
            entOutSourceCost.setCreatedById(rs.getLong(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_CREATED_BY_ID]));
            entOutSourceCost.setApprovedDate(rs.getDate(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_APPROVED_DATE]));
            entOutSourceCost.setApprovedById(rs.getLong(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_APPROVED_BY_ID]));
            entOutSourceCost.setNote(rs.getString(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_NOTE]));
            entOutSourceCost.setStatusDoc(rs.getInt(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_STATUS_DOC]));
            entOutSourceCost.setDivisionId(rs.getLong(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_DIVISION_ID]));
            entOutSourceCost.setDepartmentId(rs.getLong(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_DEPARTMENT_ID]));
            entOutSourceCost.setSectionId(rs.getLong(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_SECTION_ID]));
            entOutSourceCost.setDateOfPay(rs.getDate(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_DATE_OF_PAY]));
            entOutSourceCost.setPeriodId(rs.getLong(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_PERIOD_ID]));
        } catch (Exception e) {
        }
    }
    
    /*
     * SELECT di.DIVISION, per.PERIOD, oe.CREATED_DATE, empc.FULL_NAME AS created_by, oe.STATUS_DOC, empa.FULL_NAME AS approv_by, oe.APPROVED_DATE, oe.NOTE
        FROM hr_outsource_cost AS oe
        INNER JOIN hr_division AS di
        ON di.DIVISION_ID=oe.DIVISION_ID
        " INNER JOIN hr_period AS per"+
        " ON per.PERIOD_ID=oe.PERIOD_ID"+
        " INNER JOIN hr_employee AS empc"+
        " ON empc.EMPLOYEE_ID=oe.CREATED_BY_ID"+
        " LEFT JOIN hr_employee AS empa"+
        " ON empa.EMPLOYEE_ID=oe.APPROVED_BY_ID"
     */
    
    public static Vector listJoin(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT oe.OUTSOURCE_COST_ID, oe.DIVISION_ID, di.DIVISION, oe.PERIOD_ID, per.PERIOD, oe.CREATED_DATE, empc.FULL_NAME AS created_by, oe.STATUS_DOC, empa.FULL_NAME AS approv_by, oe.APPROVED_DATE, oe.NOTE"+
                    " FROM " + TBL_OUTSOURCE_COST +" as oe"+
                    " INNER JOIN hr_division AS di"+
                    " ON di.DIVISION_ID=oe.DIVISION_ID"+
                    " INNER JOIN hr_period AS per"+
                    " ON per.PERIOD_ID=oe.PERIOD_ID"+
                    " INNER JOIN hr_employee AS empc"+
                    " ON empc.EMPLOYEE_ID=oe.CREATED_BY_ID"+
                    " LEFT JOIN hr_employee AS empa"+
                    " ON empa.EMPLOYEE_ID=oe.APPROVED_BY_ID";
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            
           // System.out.println(":::::::::::::::::: "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                OutSourceCost outSourceCost = new OutSourceCost();
                resultToObjectJoin(rs, outSourceCost);
                lists.add(outSourceCost);
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
    
    public static void resultToObjectJoin(ResultSet rs, OutSourceCost entOutSourceCost) {
        try {
            entOutSourceCost.setOID(rs.getLong(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_OUTSOURCE_COST_ID]));
            entOutSourceCost.setDivisionId(rs.getLong(PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]));
            entOutSourceCost.setDivisionName(rs.getString(PstDivision.fieldNames[PstDivision.FLD_DIVISION]));
            entOutSourceCost.setPeriodId(rs.getLong(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]));
            entOutSourceCost.setPeriodName(rs.getString(PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]));
            entOutSourceCost.setCreatedDate(rs.getDate(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_CREATED_DATE]));
            entOutSourceCost.setCreateByName(rs.getString("created_by"));
            entOutSourceCost.setStatusDoc(rs.getInt(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_STATUS_DOC]));
            entOutSourceCost.setApprovedByName(rs.getString("approv_by"));
            entOutSourceCost.setApprovedDate(rs.getDate(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_APPROVED_DATE]));
            entOutSourceCost.setNote(rs.getString(PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_NOTE]));
            
        } catch (Exception e) {
        }
    }
}
