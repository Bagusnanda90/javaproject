
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.entity.masterdata;

/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package  harisma */
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.outsource.PstOutSourceEvaluation;
import com.dimata.harisma.entity.outsource.PstOutSourceEvaluationProvider;
import com.dimata.harisma.entity.outsource.PstOutSourcePlan;
import com.dimata.harisma.entity.outsource.PstOutSourcePlanDetail;
import com.dimata.harisma.entity.outsource.PstOutSourcePlanLocation;
import com.dimata.harisma.entity.outsource.SrcObject;
import com.dimata.util.Formater;

public class PstPositionGroup extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    public static final String TBL_HR_POSITION_GROUP = "hr_position_group";
    public static final int FLD_POSITION_GROUP_ID = 0;
    public static final int FLD_POSITION_GROUP_NAME = 1;
    public static final int FLD_DESCRIPTION = 2;

    //}
    public static final String[] fieldNames = {
        "POSITION_GROUP_ID",
        "POSITION_GROUP_NAME",
        "DESCRIPTION"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING
    };
    
    public PstPositionGroup() {
    }

    public PstPositionGroup(int i) throws DBException {
        super(new PstPositionGroup());
    }

    public PstPositionGroup(String sOid) throws DBException {
        super(new PstPositionGroup(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPositionGroup(long lOid) throws DBException {
        super(new PstPositionGroup(0));
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
        return TBL_HR_POSITION_GROUP;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPositionGroup().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        PositionGroup positionGroup = fetchExc(ent.getOID());
        ent = (Entity) positionGroup;
        return positionGroup.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((PositionGroup) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((PositionGroup) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static PositionGroup fetchExc(long oid) throws DBException {
        try {
            PositionGroup positionGroup = new PositionGroup();
            PstPositionGroup pstPositionGroup = new PstPositionGroup(oid);
            positionGroup.setOID(oid);

            positionGroup.setPositionGroupName(pstPositionGroup.getString(FLD_POSITION_GROUP_NAME));
            positionGroup.setDescription(pstPositionGroup.getString(FLD_DESCRIPTION));
            return positionGroup;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionGroup(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(PositionGroup positionGroup) throws DBException {
        try {
            PstPositionGroup pstPositionGroup = new PstPositionGroup(0);

            pstPositionGroup.setString(FLD_POSITION_GROUP_NAME, positionGroup.getPositionGroupName());
            pstPositionGroup.setString(FLD_DESCRIPTION, positionGroup.getDescription());
         
            pstPositionGroup.insert();
            positionGroup.setOID(pstPositionGroup.getlong(FLD_POSITION_GROUP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionGroup(0), DBException.UNKNOWN);
        }
        return positionGroup.getOID();
    }

    public static long updateExc(PositionGroup positionGroup) throws DBException {
        try {
            if (positionGroup.getOID() != 0) {
                PstPositionGroup pstPositionGroup = new PstPositionGroup(positionGroup.getOID());

                pstPositionGroup.setString(FLD_POSITION_GROUP_NAME, positionGroup.getPositionGroupName());
                pstPositionGroup.setString(FLD_DESCRIPTION, positionGroup.getDescription());
              
                pstPositionGroup.update();
                return positionGroup.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionGroup(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPositionGroup pstPositionGroup = new PstPositionGroup(oid);
            pstPositionGroup.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionGroup(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_POSITION_GROUP;
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
           // System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PositionGroup positionGroup = new PositionGroup();
                resultToObject(rs, positionGroup);
                lists.add(positionGroup);
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

    public static Hashtable<String, PositionGroup> listMap(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable<String, PositionGroup> lists = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_POSITION_GROUP;
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
           // System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PositionGroup positionGroup = new PositionGroup();
                resultToObject(rs, positionGroup);
                lists.put(""+positionGroup.getOID(), positionGroup);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Hashtable();
    }
    
    
    /**
     * create by devin 2014-04-09
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
     public static Hashtable listt(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable listt = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_POSITION_GROUP;
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
           // System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PositionGroup positionGroup = new PositionGroup();
                resultToObject(rs, positionGroup);
                listt.put(""+positionGroup.getOID(), positionGroup.getPositionGroupName());
            }
           

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return listt;
        }
      
    }
     
    
    public static void resultToObject(ResultSet rs, PositionGroup positionGroup){
        
        try {
            
            positionGroup.setOID(rs.getLong(PstPositionGroup.fieldNames[PstPositionGroup.FLD_POSITION_GROUP_ID]));
            positionGroup.setPositionGroupName(rs.getString(PstPositionGroup.fieldNames[PstPositionGroup.FLD_POSITION_GROUP_NAME]));
            positionGroup.setDescription(rs.getString(PstPositionGroup.fieldNames[PstPositionGroup.FLD_DESCRIPTION]));
          
        } catch (Exception e) {
            System.out.println("Exception "+e.toString());
        }
    }

    public static boolean checkOID(long positionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_POSITION_GROUP + " WHERE " +
                    PstPositionGroup.fieldNames[PstPositionGroup.FLD_POSITION_GROUP_ID] + " = " + positionId;

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
            String sql = "SELECT COUNT(" + PstPositionGroup.fieldNames[PstPositionGroup.FLD_POSITION_GROUP_ID] + ") FROM " + TBL_HR_POSITION_GROUP;
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

    /* This method used to find current data */
    public static int findLimitStart(long oid, int recordToGet, String whereClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, order);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    PositionGroup positionGroup = (PositionGroup) list.get(ls);
                    if (oid == positionGroup.getOID()) {
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

    public static boolean checkMaster(long oid) {
        if (PstEmployee.checkPosition(oid)) {
            return true;
        } else {
            if (PstCareerPath.checkPosition(oid)) {
                return true;
            } else {
                return false;
            }
        }
    }

  
}

