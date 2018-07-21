/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Ari_20110930
 * @version  	:  [version]
 */
/*******************************************************************
 * Class Description 	: PstLeaveConfigurationMain
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/
package com.dimata.harisma.entity.masterdata.leaveconfiguration;

/**
 *
 * @author Wiweka
 */
/* package java */
import com.dimata.harisma.entity.masterdata.*;
import java.sql.*;
import java.util.*;


/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

public class PstLeaveConfigurationDetailPosition extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION = "hr_leave_configuration_main_position";//"hr_company";
    public static final int FLD_LEAVE_CONFIGURATION_MAIN_ID = 0;
    public static final int FLD_POSITION_MIN_ID = 1;
    public static final int FLD_POSITION_MAX_ID = 2;
    
    public static final String[] fieldNames = {
        "LEAVE_CONFIGURATION_MAIN_ID",
        "POSITION_MIN_ID",
        "POSITION_MAX_ID",
    };
    public static final int[] fieldTypes = {
        TYPE_PK + TYPE_FK + TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
    };

    public PstLeaveConfigurationDetailPosition() {
    }

    public PstLeaveConfigurationDetailPosition(int i) throws DBException {
        super(new PstLeaveConfigurationDetailPosition());
    }

    public PstLeaveConfigurationDetailPosition(String sOid) throws DBException {
        super(new PstLeaveConfigurationDetailPosition(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstLeaveConfigurationDetailPosition(long lOid) throws DBException {
        super(new PstLeaveConfigurationDetailPosition(0));
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
        return TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstLeaveConfigurationDetailPosition().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        LeaveConfigurationDetailPosition leaveConfigurationDetailPosition = fetchExc(ent.getOID());
        ent = (Entity) leaveConfigurationDetailPosition;
        return leaveConfigurationDetailPosition.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((LeaveConfigurationDetailPosition) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((LeaveConfigurationDetailPosition) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static LeaveConfigurationDetailPosition fetchExc(long oid) throws DBException {
        try {
            LeaveConfigurationDetailPosition leaveConfigurationDetailPosition = new LeaveConfigurationDetailPosition();
            PstLeaveConfigurationDetailPosition pstLeaveConfigurationMain = new PstLeaveConfigurationDetailPosition(oid);
            leaveConfigurationDetailPosition.setOID(oid);

            leaveConfigurationDetailPosition.setPositionMin(pstLeaveConfigurationMain.getlong(FLD_POSITION_MIN_ID));
            leaveConfigurationDetailPosition.setPositionMax(pstLeaveConfigurationMain.getlong(FLD_POSITION_MAX_ID));
            leaveConfigurationDetailPosition.setLeaveConfigurationMainId(pstLeaveConfigurationMain.getlong(FLD_LEAVE_CONFIGURATION_MAIN_ID));

            return leaveConfigurationDetailPosition;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationDetailPosition(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(LeaveConfigurationDetailPosition leaveConfigurationDetailPosition) throws DBException {
        try {
            PstLeaveConfigurationDetailPosition pstLeaveConfigurationMain = new PstLeaveConfigurationDetailPosition(0);

            pstLeaveConfigurationMain.setLong(FLD_POSITION_MIN_ID, leaveConfigurationDetailPosition.getPositionMin());
            pstLeaveConfigurationMain.setLong(FLD_POSITION_MAX_ID, leaveConfigurationDetailPosition.getPositionMax());
            

            pstLeaveConfigurationMain.insert();
            leaveConfigurationDetailPosition.setOID(pstLeaveConfigurationMain.getlong(FLD_LEAVE_CONFIGURATION_MAIN_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationDetailPosition(0), DBException.UNKNOWN);
        }
        return leaveConfigurationDetailPosition.getOID();
    }

    public static long updateExc(LeaveConfigurationDetailPosition leaveConfigurationDetailPosition) throws DBException {
        try {
            if (leaveConfigurationDetailPosition.getOID() != 0) {
                PstLeaveConfigurationDetailPosition pstLeaveConfigurationMain = new PstLeaveConfigurationDetailPosition(leaveConfigurationDetailPosition.getOID());

                pstLeaveConfigurationMain.setLong(FLD_POSITION_MIN_ID, leaveConfigurationDetailPosition.getPositionMin());
            pstLeaveConfigurationMain.setLong(FLD_POSITION_MAX_ID, leaveConfigurationDetailPosition.getPositionMax()); 
                

                pstLeaveConfigurationMain.update();
                return leaveConfigurationDetailPosition.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationDetailPosition(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstLeaveConfigurationDetailPosition pstLeaveConfigurationMain = new PstLeaveConfigurationDetailPosition(oid);
            pstLeaveConfigurationMain.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationDetailPosition(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION;
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
                LeaveConfigurationDetailPosition leaveConfigurationDetailPosition = new LeaveConfigurationDetailPosition();
                resultToObject(rs, leaveConfigurationDetailPosition);
                lists.add(leaveConfigurationDetailPosition);
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
    
    public static LeaveConfigurationDetailPosition getPositionName(int limitStart, int recordToGet, String whereClause, String order) {
        LeaveConfigurationDetailPosition leaveConfigurationDetailPosition = new LeaveConfigurationDetailPosition();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION;
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
               leaveConfigurationDetailPosition.setLeaveConfigurationMainId(rs.getLong(PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_LEAVE_CONFIGURATION_MAIN_ID]));
               leaveConfigurationDetailPosition.setPositionMin(rs.getLong(PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MIN_ID]));
               leaveConfigurationDetailPosition.setPositionMax(rs.getLong(PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MAX_ID]));
               leaveConfigurationDetailPosition.setNamaPosMin(PstPosition.strPositionLevelNames[Integer.parseInt(""+rs.getLong(PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MIN_ID]))]); 
               leaveConfigurationDetailPosition.setNamaPosMax(PstPosition.strPositionLevelNames[Integer.parseInt(""+rs.getLong(PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MAX_ID]))]);
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return leaveConfigurationDetailPosition;
        }
    }

    
    public static void resultToObject(ResultSet rs, LeaveConfigurationDetailPosition leaveConfigurationDetailPosition) {
        try {
            leaveConfigurationDetailPosition.setOID(rs.getLong(PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_LEAVE_CONFIGURATION_MAIN_ID]));
            leaveConfigurationDetailPosition.setPositionMin(rs.getLong(PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MIN_ID]));
            leaveConfigurationDetailPosition.setPositionMax(rs.getLong(PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MAX_ID]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long oidMain) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION + " WHERE "
                    + PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_LEAVE_CONFIGURATION_MAIN_ID] + " = " + oidMain;

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
            String sql = "SELECT COUNT(" + PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_LEAVE_CONFIGURATION_MAIN_ID] + ") FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION;
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
                    LeaveConfigurationDetailPosition leaveConfigurationDetailPosition = (LeaveConfigurationDetailPosition) list.get(ls);
                    if (oid == leaveConfigurationDetailPosition.getOID()) {
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
    
    
    public static long deleteDetailConfigurationPosition(long oid) {
        PstLeaveConfigurationDetailPosition pstObj = new PstLeaveConfigurationDetailPosition();
        DBResultSet dbrs = null;
        try {
            String sql = "DELETE FROM " + pstObj.getTableName()
                    + " WHERE " + PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_LEAVE_CONFIGURATION_MAIN_ID]
                    + " = '" + oid + "'";

            int status = DBHandler.execUpdate(sql);
            return oid;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }
    
      public static long insert(LeaveConfigurationDetailPosition entObj) {
        try {
            PstLeaveConfigurationDetailPosition pstObj = new PstLeaveConfigurationDetailPosition(0);


            pstObj.setLong(FLD_LEAVE_CONFIGURATION_MAIN_ID, entObj.getLeaveConfigurationMainId());
            pstObj.setLong(FLD_POSITION_MIN_ID, entObj.getPositionMin());
            pstObj.setLong(FLD_POSITION_MAX_ID, entObj.getPositionMax());

            pstObj.insert();
            return entObj.getLeaveConfigurationMainId();
        } catch (DBException e) {
            System.out.println(e);
        }
        return 0;
    }
    
    public static boolean setDetailConfigurationPosition(long oidLeaveMainConfig, LeaveConfigurationDetailPosition leaveConfigurationDetailPosition) {

        // do delete
        if (PstLeaveConfigurationDetailPosition.deleteDetailConfigurationPosition(oidLeaveMainConfig) == 0) {
            return false;
        }

//        if (vGroupPosition == null || vGroupPosition.size() == 0) {
//            return true;
//        }

        // than insert
        //for (int i = 0; i < vGroupPosition.size(); i++) {
          //  LeaveConfigurationDetailPosition leaveConfigurationDetailPosition = (LeaveConfigurationDetailPosition) vGroupPosition.get(i);
        if(leaveConfigurationDetailPosition!=null){
            leaveConfigurationDetailPosition.setLeaveConfigurationMainId(oidLeaveMainConfig); 
            if (PstLeaveConfigurationDetailPosition.insert(leaveConfigurationDetailPosition) == 0) {
                return false;
            }
        }
        //}
        return true;
    }
}
