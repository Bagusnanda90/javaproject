/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

/**
 *
 * @author dimata005
 */
//public class PstOutSourcePlan {
//    
//}
import com.dimata.harisma.entity.employee.PstEmployee;
import java.sql.*;
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.util.Vector;

public class PstOutSourcePlan extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_OUTSOURCEPLAN = "hr_outsource_plan";
    public static final int FLD_OUTSOURCEPLANID = 0;
    public static final int FLD_PLANYEAR = 1;
    public static final int FLD_TITLE = 2;
    public static final int FLD_COMPANYID = 3;
    public static final int FLD_CREATEDDATE = 4;
    public static final int FLD_CREATEDBYID = 5;
    public static final int FLD_APPROVEDDATE = 6;
    public static final int FLD_APPROVEBYID = 7;
    public static final int FLD_NOTE = 8;
    public static final int FLD_STATUS = 9;
    public static final int FLD_VALID_FROM = 10;
    public static final int FLD_VALID_TO = 11;
    public static String[] fieldNames = {
        "OUTSOURCE_PLAN_ID",
        "PLAN_YEAR",
        "TITLE",
        "COMPANY_ID",
        "CREATED_DATE",
        "CREATED_BY_ID",
        "APPROVED_DATE",
        "APPROVED_BY_ID",
        "NOTE",
        "STATUS_DOC",
        "VALID_FROM",
        "VALID_TO"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_INT,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE
    };

    public PstOutSourcePlan() {
    }

    public PstOutSourcePlan(int i) throws DBException {
        super(new PstOutSourcePlan());
    }

    public PstOutSourcePlan(String sOid) throws DBException {
        super(new PstOutSourcePlan(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstOutSourcePlan(long lOid) throws DBException {
        super(new PstOutSourcePlan(0));
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
        return TBL_OUTSOURCEPLAN;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstOutSourcePlan().getClass().getName();
    }

    public static OutSourcePlan fetchExc(long oid) throws DBException {
        try {
            OutSourcePlan entOutSourcePlan = new OutSourcePlan();
            PstOutSourcePlan pstOutSourcePlan = new PstOutSourcePlan(oid);
            entOutSourcePlan.setOID(oid);
            entOutSourcePlan.setPlanYear(pstOutSourcePlan.getInt(FLD_PLANYEAR));
            entOutSourcePlan.setTitle(pstOutSourcePlan.getString(FLD_TITLE));
            entOutSourcePlan.setCompanyId(pstOutSourcePlan.getlong(FLD_COMPANYID));
            entOutSourcePlan.setCreatedDate(pstOutSourcePlan.getDate(FLD_CREATEDDATE));
            entOutSourcePlan.setCreatedById(pstOutSourcePlan.getlong(FLD_CREATEDBYID));
            entOutSourcePlan.setApprovedDate(pstOutSourcePlan.getDate(FLD_APPROVEDDATE));
            entOutSourcePlan.setApproveById(pstOutSourcePlan.getlong(FLD_APPROVEBYID));
            entOutSourcePlan.setNote(pstOutSourcePlan.getString(FLD_NOTE));
            entOutSourcePlan.setStatus(pstOutSourcePlan.getInt(FLD_STATUS));
            entOutSourcePlan.setValidFrom(pstOutSourcePlan.getDate(FLD_VALID_FROM));
            entOutSourcePlan.setValidTo(pstOutSourcePlan.getDate(FLD_VALID_TO));
            return entOutSourcePlan;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlan(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        OutSourcePlan entOutSourcePlan = fetchExc(entity.getOID());
        entity = (Entity) entOutSourcePlan;
        return entOutSourcePlan.getOID();
    }

    public static synchronized long updateExc(OutSourcePlan entOutSourcePlan) throws DBException {
        try {
            if (entOutSourcePlan.getOID() != 0) {
                PstOutSourcePlan pstOutSourcePlan = new PstOutSourcePlan(entOutSourcePlan.getOID());
                pstOutSourcePlan.setInt(FLD_PLANYEAR, entOutSourcePlan.getPlanYear());
                pstOutSourcePlan.setString(FLD_TITLE, entOutSourcePlan.getTitle());
                pstOutSourcePlan.setLong(FLD_COMPANYID, entOutSourcePlan.getCompanyId());
                pstOutSourcePlan.setDate(FLD_CREATEDDATE, entOutSourcePlan.getCreatedDate());
                pstOutSourcePlan.setLong(FLD_CREATEDBYID, entOutSourcePlan.getCreatedById());
                pstOutSourcePlan.setDate(FLD_APPROVEDDATE, entOutSourcePlan.getApprovedDate());
                pstOutSourcePlan.setLong(FLD_APPROVEBYID, entOutSourcePlan.getApproveById());
                pstOutSourcePlan.setString(FLD_NOTE, entOutSourcePlan.getNote());
                pstOutSourcePlan.setInt(FLD_STATUS, entOutSourcePlan.getStatus());
                pstOutSourcePlan.setDate(FLD_VALID_FROM, entOutSourcePlan.getValidFrom());
                pstOutSourcePlan.setDate(FLD_VALID_TO, entOutSourcePlan.getValidTo());
                
                pstOutSourcePlan.update();
                return entOutSourcePlan.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlan(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((OutSourcePlan) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstOutSourcePlan pstOutSourcePlan = new PstOutSourcePlan(oid);
            pstOutSourcePlan.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlan(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(OutSourcePlan entOutSourcePlan) throws DBException {
        try {
            PstOutSourcePlan pstOutSourcePlan = new PstOutSourcePlan(0);
            pstOutSourcePlan.setInt(FLD_PLANYEAR, entOutSourcePlan.getPlanYear());
            pstOutSourcePlan.setString(FLD_TITLE, entOutSourcePlan.getTitle());
            pstOutSourcePlan.setLong(FLD_COMPANYID, entOutSourcePlan.getCompanyId());
            pstOutSourcePlan.setDate(FLD_CREATEDDATE, entOutSourcePlan.getCreatedDate());
            pstOutSourcePlan.setLong(FLD_CREATEDBYID, entOutSourcePlan.getCreatedById());
            pstOutSourcePlan.setDate(FLD_APPROVEDDATE, entOutSourcePlan.getApprovedDate());
            pstOutSourcePlan.setLong(FLD_APPROVEBYID, entOutSourcePlan.getApproveById());
            pstOutSourcePlan.setString(FLD_NOTE, entOutSourcePlan.getNote());
            pstOutSourcePlan.setInt(FLD_STATUS, entOutSourcePlan.getStatus());
            pstOutSourcePlan.setDate(FLD_VALID_FROM, entOutSourcePlan.getValidFrom());
            pstOutSourcePlan.setDate(FLD_VALID_TO, entOutSourcePlan.getValidTo());
                
            pstOutSourcePlan.insert();
            entOutSourcePlan.setOID(pstOutSourcePlan.getlong(FLD_OUTSOURCEPLANID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlan(0), DBException.UNKNOWN);
        }
        return entOutSourcePlan.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((OutSourcePlan) entity);
    }

    public static void resultToObject(ResultSet rs, OutSourcePlan entOutSourcePlan) {
        try {
            entOutSourcePlan.setOID(rs.getLong(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_OUTSOURCEPLANID]));
            entOutSourcePlan.setPlanYear(rs.getInt(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_PLANYEAR]));
            entOutSourcePlan.setTitle(rs.getString(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_TITLE]));
            entOutSourcePlan.setCompanyId(rs.getLong(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_COMPANYID]));
            entOutSourcePlan.setCreatedDate(rs.getDate(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_CREATEDDATE]));
            entOutSourcePlan.setCreatedById(rs.getLong(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_CREATEDBYID]));
            entOutSourcePlan.setApprovedDate(rs.getDate(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_APPROVEDDATE]));
            entOutSourcePlan.setApproveById(rs.getLong(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_APPROVEBYID]));
            entOutSourcePlan.setNote(rs.getString(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_NOTE]));
            entOutSourcePlan.setStatus(rs.getInt(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_STATUS]));
            entOutSourcePlan.setValidFrom(rs.getDate(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_VALID_FROM]));
            entOutSourcePlan.setValidTo(rs.getDate(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_VALID_TO]));
        } catch (Exception e) {
        }
    }
    
    
    public static void resultToObjectJoin(ResultSet rs, OutSourcePlan entOutSourcePlan) {
        try {
            entOutSourcePlan.setOID(rs.getLong(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_OUTSOURCEPLANID]));
            entOutSourcePlan.setPlanYear(rs.getInt(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_PLANYEAR]));
            entOutSourcePlan.setTitle(rs.getString(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_TITLE]));
            entOutSourcePlan.setCompanyId(rs.getLong(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_COMPANYID]));
            entOutSourcePlan.setCreatedDate(rs.getDate(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_CREATEDDATE]));
            entOutSourcePlan.setCreatedById(rs.getLong(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_CREATEDBYID]));
            entOutSourcePlan.setApprovedDate(rs.getDate(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_APPROVEDDATE]));
            entOutSourcePlan.setApproveById(rs.getLong(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_APPROVEBYID]));
            entOutSourcePlan.setNote(rs.getString(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_NOTE]));
            entOutSourcePlan.setStatus(rs.getInt(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_STATUS]));
            entOutSourcePlan.setValidFrom(rs.getDate(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_VALID_FROM]));
            entOutSourcePlan.setValidTo(rs.getDate(PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_VALID_TO]));
            entOutSourcePlan.setCreateName(rs.getString("CREATED_NAME"));
            entOutSourcePlan.setApproveName(rs.getString("APPROVE_NAME"));
        } catch (Exception e) {
        }
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_OUTSOURCEPLAN;
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
                OutSourcePlan entOutSourcePlan = new OutSourcePlan();
                resultToObject(rs, entOutSourcePlan);
                lists.add(entOutSourcePlan);
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
    
    
    public static  OutSourcePlan getPreviousPlan(OutSourcePlan outSourcePlanCheck) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_OUTSOURCEPLAN;
            OutSourcePlan outSourcePlan = null;
            if(outSourcePlanCheck==null || outSourcePlanCheck.getOID()==0 || outSourcePlanCheck.getValidTo()==null ){
              sql = sql + "  ORDER BY " + fieldNames[FLD_VALID_TO] + " DESC LIMIT 0,1";
              }else{
              sql = sql +" WHERE "+ fieldNames[FLD_VALID_TO]+ "<"+ Formater.formatDate(outSourcePlanCheck.getValidFrom(), "yyyy-MM-dd 00:00:00") +" ORDER BY " + fieldNames[FLD_VALID_TO] + " DESC LIMIT 0,1";
            }
     
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                 outSourcePlan = new OutSourcePlan();
                resultToObject(rs, outSourcePlan);
            }
            rs.close();
            return outSourcePlan;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return null;
    }

    
    public static Vector listJoin(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
           String sql = "SELECT ho.*, he."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" as CREATED_NAME, hy."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" as APPROVE_NAME FROM "+TBL_OUTSOURCEPLAN+" as ho "+
                        " INNER JOIN "+ PstEmployee.TBL_HR_EMPLOYEE+" as he on ho."+fieldNames[FLD_CREATEDBYID]+"=he."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                        " LEFT JOIN "+ PstEmployee.TBL_HR_EMPLOYEE+" as hy on ho."+fieldNames[FLD_APPROVEBYID]+"=hy."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
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
                OutSourcePlan entOutSourcePlan = new OutSourcePlan();
                resultToObjectJoin(rs, entOutSourcePlan);
                lists.add(entOutSourcePlan);
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

    
    public static boolean checkOID(long entOutSourcePlanId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_OUTSOURCEPLAN + " WHERE "
                    + PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_OUTSOURCEPLANID] + " = " + entOutSourcePlanId;
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
            String sql = "SELECT COUNT(" + PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_OUTSOURCEPLANID] + ") FROM " + TBL_OUTSOURCEPLAN;
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
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    OutSourcePlan entOutSourcePlan = (OutSourcePlan) list.get(ls);
                    if (oid == entOutSourcePlan.getOID()) {
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
        vectSize = vectSize + (recordToGet - mdl);
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
}
