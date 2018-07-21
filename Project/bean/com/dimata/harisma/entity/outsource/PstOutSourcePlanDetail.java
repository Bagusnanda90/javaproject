/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

/**
 *
 * @author dimata005
 */
import java.sql.*;
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.Command;
import java.util.Vector;

public class PstOutSourcePlanDetail extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_OUTSOURCEPLANDETAIL = "hr_outsource_plan_detail";
    public static final int FLD_OUTSOURCEPLANDETAILID = 0;
    public static final int FLD_OUTSOURCEPLANID = 1;
    public static final int FLD_POSITIONID = 2;
    public static final int FLD_COSTPERPERSON = 3;
    public static final int FLD_GENERALINFO = 4;
    public static final int FLD_TYPEOFCONTRACT = 5;
    public static final int FLD_CONTRACTPERIOD = 6;
    public static final int FLD_OBJECTIVES = 7;
    public static final int FLD_COSTNBENEFITANLYSIS = 8;
    public static final int FLD_COSTTOTAL = 9;
    public static final int FLD_RISKANALIYS = 10;
    public static final int FLD_DESCRIPTION = 11;
    public static String[] fieldNames = {
        "OUTSRC_PLAN_DETAIL_ID",
        "OUTSOURCE_PLAN_ID",
        "POSITION_ID",
        "COST_PER_PERSON",
        "GENERAL_INFO",
        "TYPE_OF_CONTRACT",
        "CONTRACT_PERIOD",
        "OBJECTIVES",
        "COST_N_BENEFIT_ANLYSIS",
        "COST_TOTAL",
        "RISK_ANALISYS",
        "DESCRIPTION"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstOutSourcePlanDetail() {
    }

    public PstOutSourcePlanDetail(int i) throws DBException {
        super(new PstOutSourcePlanDetail());
    }

    public PstOutSourcePlanDetail(String sOid) throws DBException {
        super(new PstOutSourcePlanDetail(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstOutSourcePlanDetail(long lOid) throws DBException {
        super(new PstOutSourcePlanDetail(0));
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
        return TBL_OUTSOURCEPLANDETAIL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstOutSourcePlanDetail().getClass().getName();
    }

    public static OutSourcePlanDetail fetchExc(long oid) throws DBException {
        try {
            OutSourcePlanDetail entOutSourcePlanDetail = new OutSourcePlanDetail();
            PstOutSourcePlanDetail pstOutSourcePlanDetail = new PstOutSourcePlanDetail(oid);
            entOutSourcePlanDetail.setOID(oid);
            entOutSourcePlanDetail.setOutsourcePlanId(pstOutSourcePlanDetail.getlong(FLD_OUTSOURCEPLANID));
            entOutSourcePlanDetail.setPositionId(pstOutSourcePlanDetail.getlong(FLD_POSITIONID));
            entOutSourcePlanDetail.setCostPerPerson(pstOutSourcePlanDetail.getdouble(FLD_COSTPERPERSON));
            entOutSourcePlanDetail.setGeneralInfo(pstOutSourcePlanDetail.getString(FLD_GENERALINFO));
            entOutSourcePlanDetail.setTypeOfContract(pstOutSourcePlanDetail.getString(FLD_TYPEOFCONTRACT));
            entOutSourcePlanDetail.setContractPeriod(pstOutSourcePlanDetail.getdouble(FLD_CONTRACTPERIOD));
            entOutSourcePlanDetail.setObjectives(pstOutSourcePlanDetail.getString(FLD_OBJECTIVES));
            entOutSourcePlanDetail.setCostNBenefitAnlysis(pstOutSourcePlanDetail.getString(FLD_COSTNBENEFITANLYSIS));
            entOutSourcePlanDetail.setCostTotal(pstOutSourcePlanDetail.getdouble(FLD_COSTTOTAL));
            entOutSourcePlanDetail.setRiskAnaliys(pstOutSourcePlanDetail.getString(FLD_RISKANALIYS));
            entOutSourcePlanDetail.setDescription(pstOutSourcePlanDetail.getString(FLD_DESCRIPTION));
            return entOutSourcePlanDetail;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlanDetail(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        OutSourcePlanDetail entOutSourcePlanDetail = fetchExc(entity.getOID());
        entity = (Entity) entOutSourcePlanDetail;
        return entOutSourcePlanDetail.getOID();
    }

    public static synchronized long updateExc(OutSourcePlanDetail entOutSourcePlanDetail) throws DBException {
        try {
            if (entOutSourcePlanDetail.getOID() != 0) {
                PstOutSourcePlanDetail pstOutSourcePlanDetail = new PstOutSourcePlanDetail(entOutSourcePlanDetail.getOID());
                pstOutSourcePlanDetail.setLong(FLD_OUTSOURCEPLANID, entOutSourcePlanDetail.getOutsourcePlanId());
                pstOutSourcePlanDetail.setLong(FLD_POSITIONID, entOutSourcePlanDetail.getPositionId());
                pstOutSourcePlanDetail.setDouble(FLD_COSTPERPERSON, entOutSourcePlanDetail.getCostPerPerson());
                pstOutSourcePlanDetail.setString(FLD_GENERALINFO, entOutSourcePlanDetail.getGeneralInfo());
                pstOutSourcePlanDetail.setString(FLD_TYPEOFCONTRACT, entOutSourcePlanDetail.getTypeOfContract());
                pstOutSourcePlanDetail.setDouble(FLD_CONTRACTPERIOD, entOutSourcePlanDetail.getContractPeriod());
                pstOutSourcePlanDetail.setString(FLD_OBJECTIVES, entOutSourcePlanDetail.getObjectives());
                pstOutSourcePlanDetail.setString(FLD_COSTNBENEFITANLYSIS, entOutSourcePlanDetail.getCostNBenefitAnlysis());
                pstOutSourcePlanDetail.setDouble(FLD_COSTTOTAL, entOutSourcePlanDetail.getCostTotal());
                pstOutSourcePlanDetail.setString(FLD_RISKANALIYS, entOutSourcePlanDetail.getRiskAnaliys());
                pstOutSourcePlanDetail.setString(FLD_DESCRIPTION, entOutSourcePlanDetail.getDescription());
                pstOutSourcePlanDetail.update();
                return entOutSourcePlanDetail.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlanDetail(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((OutSourcePlanDetail) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstOutSourcePlanDetail pstOutSourcePlanDetail = new PstOutSourcePlanDetail(oid);
            pstOutSourcePlanDetail.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlanDetail(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(OutSourcePlanDetail entOutSourcePlanDetail) throws DBException {
        try {
            PstOutSourcePlanDetail pstOutSourcePlanDetail = new PstOutSourcePlanDetail(0);
            pstOutSourcePlanDetail.setLong(FLD_OUTSOURCEPLANID, entOutSourcePlanDetail.getOutsourcePlanId());
            pstOutSourcePlanDetail.setLong(FLD_POSITIONID, entOutSourcePlanDetail.getPositionId());
            pstOutSourcePlanDetail.setDouble(FLD_COSTPERPERSON, entOutSourcePlanDetail.getCostPerPerson());
            pstOutSourcePlanDetail.setString(FLD_GENERALINFO, entOutSourcePlanDetail.getGeneralInfo());
            pstOutSourcePlanDetail.setString(FLD_TYPEOFCONTRACT, entOutSourcePlanDetail.getTypeOfContract());
            pstOutSourcePlanDetail.setDouble(FLD_CONTRACTPERIOD, entOutSourcePlanDetail.getContractPeriod());
            pstOutSourcePlanDetail.setString(FLD_OBJECTIVES, entOutSourcePlanDetail.getObjectives());
            pstOutSourcePlanDetail.setString(FLD_COSTNBENEFITANLYSIS, entOutSourcePlanDetail.getCostNBenefitAnlysis());
            pstOutSourcePlanDetail.setDouble(FLD_COSTTOTAL, entOutSourcePlanDetail.getCostTotal());
            pstOutSourcePlanDetail.setString(FLD_RISKANALIYS, entOutSourcePlanDetail.getRiskAnaliys());
            pstOutSourcePlanDetail.setString(FLD_DESCRIPTION, entOutSourcePlanDetail.getDescription());
            pstOutSourcePlanDetail.insert();
            entOutSourcePlanDetail.setOID(pstOutSourcePlanDetail.getlong(FLD_OUTSOURCEPLANDETAILID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlanDetail(0), DBException.UNKNOWN);
        }
        return entOutSourcePlanDetail.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((OutSourcePlanDetail) entity);
    }

    public static void resultToObject(ResultSet rs, OutSourcePlanDetail entOutSourcePlanDetail) {
        try {
            entOutSourcePlanDetail.setOID(rs.getLong(PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_OUTSOURCEPLANDETAILID]));
            entOutSourcePlanDetail.setOutsourcePlanId(rs.getLong(PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_OUTSOURCEPLANID]));
            entOutSourcePlanDetail.setPositionId(rs.getLong(PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_POSITIONID]));
            entOutSourcePlanDetail.setCostPerPerson(rs.getDouble(PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_COSTPERPERSON]));
            entOutSourcePlanDetail.setGeneralInfo(rs.getString(PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_GENERALINFO]));
            entOutSourcePlanDetail.setTypeOfContract(rs.getString(PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_TYPEOFCONTRACT]));
            entOutSourcePlanDetail.setContractPeriod(rs.getDouble(PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_CONTRACTPERIOD]));
            entOutSourcePlanDetail.setObjectives(rs.getString(PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_OBJECTIVES]));
            entOutSourcePlanDetail.setCostNBenefitAnlysis(rs.getString(PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_COSTNBENEFITANLYSIS]));
            entOutSourcePlanDetail.setCostTotal(rs.getDouble(PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_COSTTOTAL]));
            entOutSourcePlanDetail.setRiskAnaliys(rs.getString(PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_RISKANALIYS]));
            entOutSourcePlanDetail.setDescription(rs.getString(PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_DESCRIPTION]));
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
            String sql = "SELECT * FROM " + TBL_OUTSOURCEPLANDETAIL;
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
                OutSourcePlanDetail entOutSourcePlanDetail = new OutSourcePlanDetail();
                resultToObject(rs, entOutSourcePlanDetail);
                lists.add(entOutSourcePlanDetail);
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
    
    public static boolean checkOID(long entOutSourcePlanDetailId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_OUTSOURCEPLANDETAIL + " WHERE "
                    + PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_OUTSOURCEPLANDETAILID] + " = " + entOutSourcePlanDetailId;
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
            String sql = "SELECT COUNT(" + PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_OUTSOURCEPLANDETAILID] + ") FROM " + TBL_OUTSOURCEPLANDETAIL;
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
                    OutSourcePlanDetail entOutSourcePlanDetail = (OutSourcePlanDetail) list.get(ls);
                    if (oid == entOutSourcePlanDetail.getOID()) {
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