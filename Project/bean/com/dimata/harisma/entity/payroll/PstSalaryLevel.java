/*
 * PstSalaryLevel.java
 *
 * Created on April 3, 2007, 12:01 AM
 */
package com.dimata.harisma.entity.payroll;

/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
import com.dimata.harisma.entity.payroll.*;

/**
 *
 * @author Ana
 */
public class PstSalaryLevel extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_SALARY_LEVEL = "pay_sal_level";//"PAY_SAL_LEVEL";
    public static final int FLD_SAL_LEVEL_ID = 0;
    public static final int FLD_LEVEL_CODE = 1;
    public static final int FLD_SORT_IDX = 2;
    public static final int FLD_LEVEL_NAME = 3;
    public static final int FLD_AMOUNT_IS = 4;
    public static final int FLD_CUR_CODE = 5;
    public static final int FLD_SALARY_LEVEL_STATUS = 6;
    public static final int FLD_LEVEL_ASSIGN = 7;
    public static final int FLD_SALARY_LEVEL_NOTE = 8;
    public static final String[] fieldNames = {
        "SAL_LEVEL_ID",
        "LEVEL_CODE",
        "SORT_IDX",
        "LEVEL_NAME",
        "AMOUNT_IS",
        "CUR_CODE",
        "SALARY_LEVEL_STATUS",
        "LEVEL_ASSIGN",
        "SALARY_LEVEL_NOTE"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG,
        TYPE_STRING
    };
    public static final int BRUTO = 1;
    public static final int NETTO = 2;
    public static final int NONE = 3;
    public static final String[] amount_is = {
        "", "Bruto", "Netto", "None"
    };
    public static final int SALARY_LEVEL = 0;
    public static final int TEMPLATE_SALARY_LEVEL = 1;
    public static final String[] salaryLevelStatusKey = {
        "Salary Level", "Template Salary Level"    
    };
    public static final int[] salaryLevelStatusVal = {0, 1};

    /**
     * Creates a new instance of PstSalaryLevel
     */
    public PstSalaryLevel() {
    }
    
    public PstSalaryLevel(int i) throws DBException {        
        super(new PstSalaryLevel());        
    }
    
    public PstSalaryLevel(String sOid) throws DBException {        
        super(new PstSalaryLevel(0));        
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }        
    }
    
    public PstSalaryLevel(long lOid) throws DBException {        
        super(new PstSalaryLevel(0));        
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
    
    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {            
            throw new DBException(this, DBException.RECORD_NOT_FOUND);            
        }        
        return deleteExc(ent.getOID());
    }
    
    public long fetchExc(Entity ent) throws Exception {
        SalaryLevel salaryLevel = fetchExc(ent.getOID());        
        ent = (Entity) salaryLevel;        
        return salaryLevel.getOID();
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int getFieldSize() {
        return fieldNames.length;        
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;        
    }
    
    public String getPersistentName() {
        return new PstSalaryLevel().getClass().getName();
    }
    
    public String getTableName() {
        return TBL_SALARY_LEVEL;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((SalaryLevel) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((SalaryLevel) ent);        
    }
    
    public static SalaryLevel fetchExc(long oid) throws DBException {        
        try {            
            SalaryLevel salaryLevel = new SalaryLevel();
            PstSalaryLevel pstSalaryLevel = new PstSalaryLevel(oid);            
            salaryLevel.setOID(oid);
            salaryLevel.setLevelCode(pstSalaryLevel.getString(FLD_LEVEL_CODE));
            salaryLevel.setSort_Idx(pstSalaryLevel.getInt(FLD_SORT_IDX));
            salaryLevel.setLevelName(pstSalaryLevel.getString(FLD_LEVEL_NAME));
            salaryLevel.setAmountIs(pstSalaryLevel.getInt(FLD_AMOUNT_IS));
            salaryLevel.setCur_Code(pstSalaryLevel.getString(FLD_CUR_CODE));
            salaryLevel.setSalaryLevelStatus(pstSalaryLevel.getInt(FLD_SALARY_LEVEL_STATUS));
            salaryLevel.setLevelAssign(pstSalaryLevel.getLong(FLD_LEVEL_ASSIGN));
            salaryLevel.setSalaryLevelNote(pstSalaryLevel.getString(FLD_SALARY_LEVEL_NOTE));
            return salaryLevel;
        } catch (DBException dbe) {            
            throw dbe;            
        } catch (Exception e) {            
            throw new DBException(new PstCurrencyType(0), DBException.UNKNOWN);            
        }        
    }
    
    public static long insertExc(SalaryLevel salaryLevel) throws DBException {        
        try {            
            if (salaryLevel.getLevelCode() == null || salaryLevel.getLevelCode().length() < 1
                    || salaryLevel.getLevelName() == null || salaryLevel.getLevelName().length() < 1) {
                return 0;
            }
            PstSalaryLevel pstSalaryLevel = new PstSalaryLevel(0);
            
            pstSalaryLevel.setString(FLD_LEVEL_CODE, salaryLevel.getLevelCode());
            pstSalaryLevel.setInt(FLD_SORT_IDX, salaryLevel.getSort_Idx());
            pstSalaryLevel.setString(FLD_LEVEL_NAME, salaryLevel.getLevelName());
            pstSalaryLevel.setInt(FLD_AMOUNT_IS, salaryLevel.getAmountIs());
            pstSalaryLevel.setString(FLD_CUR_CODE, salaryLevel.getCur_Code());
            pstSalaryLevel.setInt(FLD_SALARY_LEVEL_STATUS, salaryLevel.getSalaryLevelStatus());
            pstSalaryLevel.setLong(FLD_LEVEL_ASSIGN, salaryLevel.getLevelAssign());
            pstSalaryLevel.setString(FLD_SALARY_LEVEL_NOTE, salaryLevel.getSalaryLevelNote());
            
            pstSalaryLevel.insert();            
            salaryLevel.setOID(pstSalaryLevel.getlong(FLD_SAL_LEVEL_ID));
        } catch (DBException dbe) {            
            throw dbe;            
        } catch (Exception e) {            
            throw new DBException(new PstTaxType(0), DBException.UNKNOWN);            
        }
        return salaryLevel.getOID();
    }
    
    public static long insertExcWithDetail(SalaryLevel salaryLevel) throws DBException {        
        try {            
            if (salaryLevel.getLevelCode() == null || salaryLevel.getLevelCode().length() < 1
                    || salaryLevel.getLevelName() == null || salaryLevel.getLevelName().length() < 1) {
                return 0;
            }
            
            PstSalaryLevel pstSalaryLevel = new PstSalaryLevel(0);
            
            pstSalaryLevel.setString(FLD_LEVEL_CODE, salaryLevel.getLevelCode());
            pstSalaryLevel.setInt(FLD_SORT_IDX, salaryLevel.getSort_Idx());
            pstSalaryLevel.setString(FLD_LEVEL_NAME, salaryLevel.getLevelName());
            pstSalaryLevel.setInt(FLD_AMOUNT_IS, salaryLevel.getAmountIs());
            pstSalaryLevel.setString(FLD_CUR_CODE, salaryLevel.getCur_Code());
            pstSalaryLevel.insert();            
            salaryLevel.setOID(pstSalaryLevel.getlong(FLD_SAL_LEVEL_ID));
            int size = salaryLevel.getSalDetailsSize();
            if (size > 0) {
                for (int idx = 0; idx < size; idx++) {
                    SalaryLevelDetail dtl = salaryLevel.getSalDetails(idx);
                    try {
                        PstSalaryLevelDetail.insertExc(dtl);
                    } catch (Exception exc) {                        
                    }
                }
            }
            
        } catch (DBException dbe) {            
            throw dbe;            
        } catch (Exception e) {            
            throw new DBException(new PstTaxType(0), DBException.UNKNOWN);            
        }
        return salaryLevel.getOID();
    }    
    
    public static long updateExc(SalaryLevel salaryLevel) throws DBException {        
        try {            
            if (salaryLevel.getLevelCode() == null || salaryLevel.getLevelCode().length() < 1
                    || salaryLevel.getLevelName() == null || salaryLevel.getLevelName().length() < 1) {
                return 0;
            }
            
            if (salaryLevel.getOID() != 0) {                
                PstSalaryLevel pstSalaryLevel = new PstSalaryLevel(salaryLevel.getOID());
                pstSalaryLevel.setString(FLD_LEVEL_CODE, salaryLevel.getLevelCode());
                pstSalaryLevel.setInt(FLD_SORT_IDX, salaryLevel.getSort_Idx());
                pstSalaryLevel.setString(FLD_LEVEL_NAME, salaryLevel.getLevelName());
                pstSalaryLevel.setInt(FLD_AMOUNT_IS, salaryLevel.getAmountIs());
                pstSalaryLevel.setString(FLD_CUR_CODE, salaryLevel.getCur_Code());
                pstSalaryLevel.setInt(FLD_SALARY_LEVEL_STATUS, salaryLevel.getSalaryLevelStatus());
                pstSalaryLevel.setLong(FLD_LEVEL_ASSIGN, salaryLevel.getLevelAssign());
                pstSalaryLevel.setString(FLD_SALARY_LEVEL_NOTE, salaryLevel.getSalaryLevelNote());
                pstSalaryLevel.update();
                return salaryLevel.getOID();
                
            }
        } catch (DBException dbe) {            
            throw dbe;            
        } catch (Exception e) {            
            throw new DBException(new PstTaxType(0), DBException.UNKNOWN);            
        }
        return 0;
    }
    
    public static long updateExcWithDetail(SalaryLevel salaryLevel) throws DBException {        
        try {            
            if (salaryLevel.getLevelCode() == null || salaryLevel.getLevelCode().length() < 1
                    || salaryLevel.getLevelName() == null || salaryLevel.getLevelName().length() < 1) {
                return 0;
            }
            
            if (salaryLevel.getOID() != 0) {                
                PstSalaryLevel pstSalaryLevel = new PstSalaryLevel(salaryLevel.getOID());
                pstSalaryLevel.setString(FLD_LEVEL_CODE, salaryLevel.getLevelCode());
                pstSalaryLevel.setInt(FLD_SORT_IDX, salaryLevel.getSort_Idx());
                pstSalaryLevel.setString(FLD_LEVEL_NAME, salaryLevel.getLevelName());
                pstSalaryLevel.setInt(FLD_AMOUNT_IS, salaryLevel.getAmountIs());
                pstSalaryLevel.setString(FLD_CUR_CODE, salaryLevel.getCur_Code());
                pstSalaryLevel.update();
                
                PstSalaryLevelDetail pstDetail = new PstSalaryLevelDetail();
                //pstDetail.deleteByLevelCode(salaryLevel.getLevelCode());                        
                int size = salaryLevel.getSalDetailsSize();
                Vector compNotInNewLevel = new Vector();
                if (size > 0) {
                    for (int idx = 0; idx < size; idx++) {
                        SalaryLevelDetail dtl = salaryLevel.getSalDetails(idx);
                        SalaryLevelDetail prevDtl = null;
                        try {
                            Vector lstDtl = PstSalaryLevelDetail.listComponent(0, 1, dtl.getLevelCode(), dtl.getCompCode());
                            if (lstDtl != null && lstDtl.size() > 0) {
                                prevDtl = (SalaryLevelDetail) lstDtl.get(0);
                            } else { // update by Kartika 23 Nov 2012
                                compNotInNewLevel.add(dtl.getLevelCode());
                            }
                        } catch (Exception exc1) {
                            System.out.println(exc1);                            
                        }
                        try {                            
                            if (prevDtl != null && prevDtl.getOID() != 0) {
                                dtl.setOID(prevDtl.getOID());
                                PstSalaryLevelDetail.updateExc(dtl);
                            } else {
                                PstSalaryLevelDetail.insertExc(dtl);
                            }
                        } catch (Exception exc) {                            
                            System.out.println(exc);                            
                        }
                    }
                    // update by Kartika 23 Nov 2012
                    if (compNotInNewLevel != null && compNotInNewLevel.size() > 0) {
                        String sqlWhere = PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE] + " IN (";
                        for (int idx = 0; idx < compNotInNewLevel.size(); idx++) {
                            sqlWhere = sqlWhere + compNotInNewLevel.get(idx)
                                    + (idx < (compNotInNewLevel.size() - 1) ? "," : ") ");
                        }
                        PstSalaryLevelDetail.deleteByWhere(sqlWhere);
                    }
                }                
                return salaryLevel.getOID();
            }
        } catch (DBException dbe) {            
            throw dbe;            
        } catch (Exception e) {            
            throw new DBException(new PstTaxType(0), DBException.UNKNOWN);            
        }
        return 0;
    }
    
    public static long deleteExc(long oid) throws DBException {        
        try {            
            PstSalaryLevel pstSalaryLevel = new PstSalaryLevel(oid);
            pstSalaryLevel.delete();
        } catch (DBException dbe) {            
            throw dbe;            
        } catch (Exception e) {            
            throw new DBException(new PstSalaryLevel(0), DBException.UNKNOWN);            
        }
        return oid;
    }
    
    public static Vector listAll() {        
        return list(0, 1000, "", "");
    }
    
    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();        
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_SALARY_LEVEL;            
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
            System.out.println("sql list  " + sql);
            while (rs.next()) {
                SalaryLevel salaryLevel = new SalaryLevel();
                resultToObject(rs, salaryLevel);
                lists.add(salaryLevel);
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
    
    public static void resultToObject(ResultSet rs, SalaryLevel salaryLevel) {
        try {
            salaryLevel.setOID(rs.getLong(PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_SAL_LEVEL_ID]));
            salaryLevel.setLevelCode(rs.getString(PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_LEVEL_CODE]));
            salaryLevel.setSort_Idx(rs.getInt(PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_SORT_IDX]));
            salaryLevel.setLevelName(rs.getString(PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_LEVEL_NAME]));
            salaryLevel.setAmountIs(rs.getInt(PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_AMOUNT_IS]));
            salaryLevel.setCur_Code(rs.getString(PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_CUR_CODE]));
            salaryLevel.setSalaryLevelStatus(rs.getInt(PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_SALARY_LEVEL_STATUS]));
            salaryLevel.setLevelAssign(rs.getLong(PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_LEVEL_ASSIGN]));
            salaryLevel.setSalaryLevelNote(rs.getString(PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_SALARY_LEVEL_NOTE]));
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long salaryLevelId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_SALARY_LEVEL + " WHERE "
                    + PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_SAL_LEVEL_ID] + " = '" + salaryLevelId + "'";
            
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
    
    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_SAL_LEVEL_ID] + ") FROM " + TBL_SALARY_LEVEL;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }
            
            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);            
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {                    
                    SalaryLevel salaryLevel = (SalaryLevel) list.get(ls);
                    if (oid == salaryLevel.getOID()) {
                        found = true;
                    }
                }
            }
        }
        if ((start >= size) && (size > 0)) {
            start = start - recordToGet;
        }
        
        return start;
    }
    
    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + mdl;
        if (start == 0) {
            cmd = Command.FIRST;
        } else {
            if (start == (vectSize - recordToGet)) {
                cmd = Command.LAST;
            } else {
                start = start + recordToGet;
                if (start <= (vectSize - recordToGet)) {
                    cmd = Command.NEXT;
                    System.out.println("next.......................");
                } else {
                    start = start - recordToGet;
                    if (start > 0) {
                        cmd = Command.PREV;
                        System.out.println("prev.......................");
                    }
                }
            }
        }
        
        return cmd;
    }

    /**
     * this method used to get salaryName
     *
     * @param : levelCode created by Yunny
     */
    public static String getSalaryName(String levelCode) {
        DBResultSet dbrs = null;
        String result = "";
        try {
            String sql = "SELECT LEVEL_NAME FROM " + PstSalaryLevel.TBL_SALARY_LEVEL
                    + " WHERE " + PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_LEVEL_CODE]
                    + " = '" + levelCode.trim() + "'";

            //System.out.println("sql SalaryName  "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while (rs.next()) {                
                result = rs.getString(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        return result;
    }
    
    public static SalaryLevel getByLevelCode(String levelCode) {
        String where = fieldNames[FLD_LEVEL_CODE] + "=\"" + levelCode + "\"";
        Vector lst = list(0, 1, where, "");
        if (lst != null && lst.size() > 0) {
            return (SalaryLevel) lst.get(0);
        }
        return null;
    }
}
