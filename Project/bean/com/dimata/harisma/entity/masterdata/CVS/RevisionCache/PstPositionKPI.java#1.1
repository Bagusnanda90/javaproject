/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

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
public class PstPositionKPI extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_POSITION_KPI = "hr_position_kpi";
    public static final int FLD_POS_KPI_ID = 0;
    public static final int FLD_KPI_MIN_ACHIEVMENT = 1;
    public static final int FLD_KPI_RECOMMEND_ACHIEV = 2;
    public static final int FLD_DURATION_MONTH = 3;
    public static final int FLD_POSITION_ID = 4;
    public static final int FLD_KPI_LIST_ID = 5;
    public static final int FLD_SCORE_MIN = 6;
    public static final int FLD_SCORE_RECOMMENDED = 7;
    public static String[] fieldNames = {
        "POS_KPI_ID",
        "KPI_MIN_ACHIEVMENT",
        "KPI_RECOMMEND_ACHIEV",
        "DURATION_MONTH",
        "POSITION_ID",
        "KPI_LIST_ID",
        "SCORE_MIN",
        "SCORE_RECOMMENDED"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT
    };

    public PstPositionKPI() {
    }

    public PstPositionKPI(int i) throws DBException {
        super(new PstPositionKPI());
    }

    public PstPositionKPI(String sOid) throws DBException {
        super(new PstPositionKPI(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPositionKPI(long lOid) throws DBException {
        super(new PstPositionKPI(0));
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
        return TBL_POSITION_KPI;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPositionKPI().getClass().getName();
    }

    public static PositionKPI fetchExc(long oid) throws DBException {
        try {
            PositionKPI entPositionKpi = new PositionKPI();
            PstPositionKPI pstPositionKpi = new PstPositionKPI(oid);
            entPositionKpi.setOID(oid);
            entPositionKpi.setKpiMinAchievment(pstPositionKpi.getInt(FLD_KPI_MIN_ACHIEVMENT));
            entPositionKpi.setKpiRecommendAchiev(pstPositionKpi.getInt(FLD_KPI_RECOMMEND_ACHIEV));
            entPositionKpi.setDurationMonth(pstPositionKpi.getInt(FLD_DURATION_MONTH));
            entPositionKpi.setPositionId(pstPositionKpi.getLong(FLD_POSITION_ID));
            entPositionKpi.setKpiListId(pstPositionKpi.getLong(FLD_KPI_LIST_ID));
            entPositionKpi.setScoreMin(pstPositionKpi.getfloat(FLD_SCORE_MIN));
            entPositionKpi.setScoreRecommended(pstPositionKpi.getfloat(FLD_SCORE_RECOMMENDED));
            return entPositionKpi;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionKPI(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PositionKPI entPositionKpi = fetchExc(entity.getOID());
        entity = (Entity) entPositionKpi;
        return entPositionKpi.getOID();
    }

    public static synchronized long updateExc(PositionKPI entPositionKpi) throws DBException {
        try {
            if (entPositionKpi.getOID() != 0) {
                PstPositionKPI pstPositionKpi = new PstPositionKPI(entPositionKpi.getOID());
                pstPositionKpi.setInt(FLD_KPI_MIN_ACHIEVMENT, entPositionKpi.getKpiMinAchievment());
                pstPositionKpi.setInt(FLD_KPI_RECOMMEND_ACHIEV, entPositionKpi.getKpiRecommendAchiev());
                pstPositionKpi.setInt(FLD_DURATION_MONTH, entPositionKpi.getDurationMonth());
                pstPositionKpi.setLong(FLD_POSITION_ID, entPositionKpi.getPositionId());
                pstPositionKpi.setLong(FLD_KPI_LIST_ID, entPositionKpi.getKpiListId());
                pstPositionKpi.setFloat(FLD_SCORE_MIN, entPositionKpi.getScoreMin());
                pstPositionKpi.setFloat(FLD_SCORE_RECOMMENDED, entPositionKpi.getScoreRecommended());
                pstPositionKpi.update();
                return entPositionKpi.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionKPI(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((PositionKPI) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstPositionKPI pstPositionKpi = new PstPositionKPI(oid);
            pstPositionKpi.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionKPI(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(PositionKPI entPositionKpi) throws DBException {
        try {
            PstPositionKPI pstPositionKpi = new PstPositionKPI(0);
            pstPositionKpi.setInt(FLD_KPI_MIN_ACHIEVMENT, entPositionKpi.getKpiMinAchievment());
            pstPositionKpi.setInt(FLD_KPI_RECOMMEND_ACHIEV, entPositionKpi.getKpiRecommendAchiev());
            pstPositionKpi.setInt(FLD_DURATION_MONTH, entPositionKpi.getDurationMonth());
            pstPositionKpi.setLong(FLD_POSITION_ID, entPositionKpi.getPositionId());
            pstPositionKpi.setLong(FLD_KPI_LIST_ID, entPositionKpi.getKpiListId());
            pstPositionKpi.setFloat(FLD_SCORE_MIN, entPositionKpi.getScoreMin());
            pstPositionKpi.setFloat(FLD_SCORE_RECOMMENDED, entPositionKpi.getScoreRecommended());
            pstPositionKpi.insert();
            entPositionKpi.setOID(pstPositionKpi.getlong(FLD_POS_KPI_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionKPI(0), DBException.UNKNOWN);
        }
        return entPositionKpi.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((PositionKPI) entity);
    }
    
    public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
            Vector lists = new Vector(); 
            DBResultSet dbrs = null;
            try {
                    String sql = "SELECT * FROM " + TBL_POSITION_KPI; 
                    if(whereClause != null && whereClause.length() > 0)
                            sql = sql + " WHERE " + whereClause;
                    if(order != null && order.length() > 0)
                            sql = sql + " ORDER BY " + order;
                    if(limitStart == 0 && recordToGet == 0)
                            sql = sql + "";
                    else
                            sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();
                    while(rs.next()) {
                            PositionKPI positionKPI = new PositionKPI();
                            resultToObject(rs, positionKPI);
                            lists.add(positionKPI);
                    }
                    rs.close();
                    return lists;

            }catch(Exception e) {
                    System.out.println(e);
            }finally {
                    DBResultSet.close(dbrs);
            }
                    return new Vector();
    }
    
    public static Vector listInnerJoin(String where){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql  = "SELECT * FROM " + TBL_POSITION_KPI + 
                    " inner join hr_kpi_list on hr_position_kpi.KPI_LIST_ID=hr_kpi_list.KPI_LIST_ID WHERE "+PstPositionKPI.fieldNames[PstPositionKPI.FLD_POSITION_ID]+" = "+where+"";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vect = new Vector(1,1);
                PositionKPI entPositionKPI = new PositionKPI();
                KPI_List kpiList = new KPI_List();
                resultToObject(rs, entPositionKPI);
                vect.add(entPositionKPI);
                kpiList.setKpi_title(rs.getString(PstKPI_List.fieldNames[PstKPI_List.FLD_KPI_TITLE]));
                vect.add(kpiList);
                lists.add(vect);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }
    public static Vector listInnerJoinVer1(String where){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql  = "SELECT * FROM " + TBL_POSITION_KPI;
                   sql += " INNER JOIN hr_position ON "+TBL_POSITION_KPI+".POSITION_ID=hr_position.POSITION_ID ";
                   sql += " INNER JOIN "+PstKPI_List.TBL_HR_KPI_LIST+" ON "+TBL_POSITION_KPI+
                   "."+fieldNames[FLD_KPI_LIST_ID]+"="+PstKPI_List.TBL_HR_KPI_LIST+"."+PstKPI_List.fieldNames[PstKPI_List.FLD_KPI_LIST_ID];
                   sql += " WHERE "+TBL_POSITION_KPI+"."+fieldNames[FLD_POS_KPI_ID]+" = "+where+"";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vect = new Vector(1,1);
                PositionKPI entPositionKPI = new PositionKPI();
                resultToObject(rs, entPositionKPI);
                vect.add(entPositionKPI);
                // Position
                Position pos = new Position();
                pos.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                vect.add(pos);
                // KPI List
                KPI_List kpiList = new KPI_List();
                kpiList.setKpi_title(rs.getString(PstKPI_List.fieldNames[PstKPI_List.FLD_KPI_TITLE]));
                vect.add(kpiList);
                lists.add(vect);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

    public static void resultToObject(ResultSet rs, PositionKPI entPositionKpi) {
        try {
            entPositionKpi.setOID(rs.getLong(PstPositionKPI.fieldNames[PstPositionKPI.FLD_POS_KPI_ID]));
            entPositionKpi.setKpiMinAchievment(rs.getInt(PstPositionKPI.fieldNames[PstPositionKPI.FLD_KPI_MIN_ACHIEVMENT]));
            entPositionKpi.setKpiRecommendAchiev(rs.getInt(PstPositionKPI.fieldNames[PstPositionKPI.FLD_KPI_RECOMMEND_ACHIEV]));
            entPositionKpi.setDurationMonth(rs.getInt(PstPositionKPI.fieldNames[PstPositionKPI.FLD_DURATION_MONTH]));
            entPositionKpi.setPositionId(rs.getLong(PstPositionKPI.fieldNames[PstPositionKPI.FLD_POSITION_ID]));
            entPositionKpi.setKpiListId(rs.getLong(PstPositionKPI.fieldNames[PstPositionKPI.FLD_KPI_LIST_ID]));
            entPositionKpi.setScoreMin(rs.getFloat(PstPositionKPI.fieldNames[PstPositionKPI.FLD_SCORE_MIN]));
            entPositionKpi.setScoreRecommended(rs.getFloat(PstPositionKPI.fieldNames[PstPositionKPI.FLD_SCORE_RECOMMENDED]));
        } catch (Exception e) {
        }
    }
    public static int getCount(String whereClause){
            DBResultSet dbrs = null;
            try {
                    String sql = "SELECT COUNT("+ PstPositionKPI.fieldNames[PstPositionKPI.FLD_POS_KPI_ID] + ") FROM " + TBL_POSITION_KPI;
                    if(whereClause != null && whereClause.length() > 0)
                            sql = sql + " WHERE " + whereClause;

                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();

                    int count = 0;
                    while(rs.next()) { count = rs.getInt(1); }

                    rs.close();
                    return count;
            }catch(Exception e) {
                    return 0;
            }finally {
                    DBResultSet.close(dbrs);
            }
    }
}
