/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

/**
 *
 * @author dimata005
 */
//public class PstOutSourcePlanCost {
//    
//}
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.outsource.PstOutSourcePlanLocation;

import java.sql.*;
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.Command;
import java.util.Hashtable;
import java.util.Vector;

public class PstOutSourcePlanCost extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_OUTSOURCEPLANCOST = "hr_outsrc_plan_cost";
    public static final int FLD_OUTSOURCEPLANCOSTID = 0;
    public static final int FLD_OUTSOURCEPLANLOCID = 1;
    public static final int FLD_OUTSOURCECOSTID = 2;
    public static final int FLD_INCRSTOPREVYEAR = 3;
    public static final int FLD_PLANAVRGCOST = 4;
    public static String[] fieldNames = {
        "OUTSRC_PLAN_COST_ID",
        "OUTSRC_PLAN_LOC_ID",
        "OUTSRC_COST_ID",
        "INCRS_TO_PREV_YEAR",
        "PLAN_AVRG_COST"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT
    };

    public PstOutSourcePlanCost() {
    }

    public PstOutSourcePlanCost(int i) throws DBException {
        super(new PstOutSourcePlanCost());
    }

    public PstOutSourcePlanCost(String sOid) throws DBException {
        super(new PstOutSourcePlanCost(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstOutSourcePlanCost(long lOid) throws DBException {
        super(new PstOutSourcePlanCost(0));
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
        return TBL_OUTSOURCEPLANCOST;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstOutSourcePlanCost().getClass().getName();
    }

    public static OutSourcePlanCost fetchExc(long oid) throws DBException {
        try {
            OutSourcePlanCost entOutSourcePlanCost = new OutSourcePlanCost();
            PstOutSourcePlanCost pstOutSourcePlanCost = new PstOutSourcePlanCost(oid);
            entOutSourcePlanCost.setOID(oid);
            entOutSourcePlanCost.setOutSourcePlanLocId(pstOutSourcePlanCost.getlong(FLD_OUTSOURCEPLANLOCID));
            entOutSourcePlanCost.setOutSourceCostId(pstOutSourcePlanCost.getlong(FLD_OUTSOURCECOSTID));
            entOutSourcePlanCost.setIncrsToPrevYear(pstOutSourcePlanCost.getdouble(FLD_INCRSTOPREVYEAR));
            entOutSourcePlanCost.setPlanAvrgCost(pstOutSourcePlanCost.getdouble(FLD_PLANAVRGCOST));
            return entOutSourcePlanCost;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlanCost(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        OutSourcePlanCost entOutSourcePlanCost = fetchExc(entity.getOID());
        entity = (Entity) entOutSourcePlanCost;
        return entOutSourcePlanCost.getOID();
    }

    public static synchronized long updateExc(OutSourcePlanCost entOutSourcePlanCost) throws DBException {
        try {
            if (entOutSourcePlanCost.getOID() != 0) {
                PstOutSourcePlanCost pstOutSourcePlanCost = new PstOutSourcePlanCost(entOutSourcePlanCost.getOID());
                pstOutSourcePlanCost.setLong(FLD_OUTSOURCEPLANLOCID, entOutSourcePlanCost.getOutSourcePlanLocId());
                pstOutSourcePlanCost.setLong(FLD_OUTSOURCECOSTID, entOutSourcePlanCost.getOutSourceCostId());
                pstOutSourcePlanCost.setDouble(FLD_INCRSTOPREVYEAR, entOutSourcePlanCost.getIncrsToPrevYear());
                pstOutSourcePlanCost.setDouble(FLD_PLANAVRGCOST, entOutSourcePlanCost.getPlanAvrgCost());
                pstOutSourcePlanCost.update();
                return entOutSourcePlanCost.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlanCost(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((OutSourcePlanCost) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstOutSourcePlanCost pstOutSourcePlanCost = new PstOutSourcePlanCost(oid);
            pstOutSourcePlanCost.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlanCost(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(OutSourcePlanCost entOutSourcePlanCost) throws DBException {
        try {
            PstOutSourcePlanCost pstOutSourcePlanCost = new PstOutSourcePlanCost(0);
            pstOutSourcePlanCost.setLong(FLD_OUTSOURCEPLANLOCID, entOutSourcePlanCost.getOutSourcePlanLocId());
            pstOutSourcePlanCost.setLong(FLD_OUTSOURCECOSTID, entOutSourcePlanCost.getOutSourceCostId());
            pstOutSourcePlanCost.setDouble(FLD_INCRSTOPREVYEAR, entOutSourcePlanCost.getIncrsToPrevYear());
            pstOutSourcePlanCost.setDouble(FLD_PLANAVRGCOST, entOutSourcePlanCost.getPlanAvrgCost());
            pstOutSourcePlanCost.insert();
            entOutSourcePlanCost.setOID(pstOutSourcePlanCost.getlong(FLD_OUTSOURCEPLANCOSTID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlanCost(0), DBException.UNKNOWN);
        }
        return entOutSourcePlanCost.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((OutSourcePlanCost) entity);
    }

    public static void resultToObject(ResultSet rs, OutSourcePlanCost entOutSourcePlanCost) {
        try {
            entOutSourcePlanCost.setOID(rs.getLong(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCEPLANCOSTID]));
            entOutSourcePlanCost.setOutSourcePlanLocId(rs.getLong(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCEPLANLOCID]));
            entOutSourcePlanCost.setOutSourceCostId(rs.getLong(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCECOSTID]));
            entOutSourcePlanCost.setIncrsToPrevYear(rs.getDouble(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_INCRSTOPREVYEAR]));
            entOutSourcePlanCost.setPlanAvrgCost(rs.getDouble(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_PLANAVRGCOST]));
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
            String sql = "SELECT * FROM " + TBL_OUTSOURCEPLANCOST;
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
                OutSourcePlanCost entOutSourcePlanCost = new OutSourcePlanCost();
                resultToObject(rs, entOutSourcePlanCost);
                lists.add(entOutSourcePlanCost);
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

    public static boolean checkOID(long entOutSourcePlanCostId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_OUTSOURCEPLANCOST + " WHERE "
                    + PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCEPLANCOSTID] + " = " + entOutSourcePlanCostId;
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
            String sql = "SELECT COUNT(" + PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCEPLANCOSTID] + ") FROM " + TBL_OUTSOURCEPLANCOST;
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
                    OutSourcePlanCost entOutSourcePlanCost = (OutSourcePlanCost) list.get(ls);
                    if (oid == entOutSourcePlanCost.getOID()) {
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
    
    public static Vector<OutSourcePlanCost> listJoin(int limitStart, int recordToGet, String whereClause, String order) {
        Vector<OutSourcePlanCost> lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT co.*, ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID] + 
                          " ,ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID] + 
                         " FROM "+ TBL_OUTSOURCEPLANCOST +" AS co "+
                         " INNER JOIN "+ PstOutSourcePlanLocation.TBL_OUTSOURCEPLANLOCATION + " AS ho ON ho."+ PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANLOCID]+" = co."+fieldNames[FLD_OUTSOURCEPLANLOCID];
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
                OutSourcePlanCost entOutSourcePlanCost = new OutSourcePlanCost();
                resultToObjectJoin(rs, entOutSourcePlanCost);
                lists.add(entOutSourcePlanCost);
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
    
    public static void resultToObjectJoin(ResultSet rs, OutSourcePlanCost entOutSourcePlanCost) {
        try {
            entOutSourcePlanCost.setOID(rs.getLong(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCEPLANCOSTID]));
            entOutSourcePlanCost.setOutSourcePlanLocId(rs.getLong(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCEPLANLOCID]));
            entOutSourcePlanCost.setOutSourceCostId(rs.getLong(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCECOSTID]));
            entOutSourcePlanCost.setIncrsToPrevYear(rs.getDouble(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_INCRSTOPREVYEAR]));
            entOutSourcePlanCost.setPlanAvrgCost(rs.getDouble(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_PLANAVRGCOST]));
            entOutSourcePlanCost.setPlanLocDivisionId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID]));
            entOutSourcePlanCost.setPlanLocPositionId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]));
        } catch (Exception e) {
        }
    }
    
    public static double getcostOutSourcePlanCost(int limitStart, int recordToGet, String whereClause, String order,long divisiId, SrcObject srcObject, long position) {
        double value = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT SUM(ospc.PLAN_AVRG_COST) FROM hr_outsrc_plan_cost AS ospc  INNER JOIN hr_outsrc_plan_loc AS ospl  ON (   ospc.OUTSRC_PLAN_LOC_ID = ospl.OUTSRC_PLAN_LOC_ID AND ospl.DIVISION_ID = "+divisiId+"    AND ospl.POSITION_ID = "+position+"  ) ";
            //" INNER JOIN "+ PstOutsrcCostProvDetail.TBL_HR_OUTSRC_COST_PROV_DETAIL+" AS ocp ON (osep."+ PstOutsrcCostProv.fieldNames[PstOutsrcCostProv.FLD_OUTSRC_COST_PROVIDER_ID]+" = ocp."+ PstOutsrcCostProvDetail.fieldNames[PstOutsrcCostProvDetail.FLD_OUTSRC_COST_PROVIDER_ID] + " ) ";
            
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
                value = rs.getDouble(1);
            }
            rs.close();
            return value;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    } 
    
    
    public static Hashtable<String, Double> hPlanCostKeyLocPos(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable<String, Double> lists = new Hashtable<String, Double>();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT co.*, ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID] + 
                          " ,ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID] + 
                         " FROM "+ TBL_OUTSOURCEPLANCOST +" AS co "+
                         " INNER JOIN "+ PstOutSourcePlanLocation.TBL_OUTSOURCEPLANLOCATION + " AS ho ON ho."+ PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANLOCID]+" = co."+fieldNames[FLD_OUTSOURCEPLANLOCID];
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
                Double value = rs.getDouble(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_PLANAVRGCOST]);
                String key = "" + rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID])+"_"
                          + rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]);
                
                lists.put(key, value);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return null;
    }
    
    public static Hashtable<String, Double> hSumPlanAllCostKeyLocPos(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable<String, Double> lists = new Hashtable<String, Double>();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT co.*, ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID] + 
                          " ,ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID] + 
                         ", SUM(" + fieldNames[FLD_PLANAVRGCOST]+") AS SUM_COST " +
                         " FROM "+ TBL_OUTSOURCEPLANCOST +" AS co "+
                         " INNER JOIN "+ PstOutSourcePlanLocation.TBL_OUTSOURCEPLANLOCATION + " AS ho ON ho."+ PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANLOCID]+" = co."+fieldNames[FLD_OUTSOURCEPLANLOCID];
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
            
            sql = sql + " GROUP BY ho."+ PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID] +" ,ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID] ;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Double value = rs.getDouble("SUM_COST");//PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_PLANAVRGCOST]);
                String key = "" + rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID])+"_"
                          + rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]);
                
                lists.put(key, value);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return null;
    }    
    
}