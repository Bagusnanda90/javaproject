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
import com.dimata.harisma.entity.masterdata.PstPosition;
import java.sql.*;
import java.util.*;


/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;


public class PstLeaveConfigurationDetailPositionRequestOnly extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION_REQUEST_ONLY = "hr_leave_configuration_main_position_request_only";//"hr_company";
    public static final int FLD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY = 0;
    public static final int FLD_POSITION_MIN_ID = 1;
    public static final int FLD_POSITION_MAX_ID = 2;
    
    public static final String[] fieldNames = {
        "LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY",
        "POSITION_MIN_ID",
        "POSITION_MAX_ID",
    };
    public static final int[] fieldTypes = {
        TYPE_PK + TYPE_FK + TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
    };

    public PstLeaveConfigurationDetailPositionRequestOnly() {
    }

    public PstLeaveConfigurationDetailPositionRequestOnly(int i) throws DBException {
        super(new PstLeaveConfigurationDetailPositionRequestOnly());
    }

    public PstLeaveConfigurationDetailPositionRequestOnly(String sOid) throws DBException {
        super(new PstLeaveConfigurationDetailPositionRequestOnly(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstLeaveConfigurationDetailPositionRequestOnly(long lOid) throws DBException {
        super(new PstLeaveConfigurationDetailPositionRequestOnly(0));
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
        return TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION_REQUEST_ONLY;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstLeaveConfigurationDetailPositionRequestOnly().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        LeaveConfigurationDetailPositionRequestOnly leaveConfigurationDetailPositionRequestOnly = fetchExc(ent.getOID());
        ent = (Entity) leaveConfigurationDetailPositionRequestOnly;
        return leaveConfigurationDetailPositionRequestOnly.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((LeaveConfigurationDetailPositionRequestOnly) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((LeaveConfigurationDetailPositionRequestOnly) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static LeaveConfigurationDetailPositionRequestOnly fetchExc(long oid) throws DBException {
        try {
            LeaveConfigurationDetailPositionRequestOnly leaveConfigurationDetailPositionRequestOnly = new LeaveConfigurationDetailPositionRequestOnly();
            PstLeaveConfigurationDetailPositionRequestOnly pstLeaveConfigurationMain = new PstLeaveConfigurationDetailPositionRequestOnly(oid);
            leaveConfigurationDetailPositionRequestOnly.setOID(oid);

            leaveConfigurationDetailPositionRequestOnly.setPositionMin(pstLeaveConfigurationMain.getlong(FLD_POSITION_MIN_ID));
            leaveConfigurationDetailPositionRequestOnly.setPositionMax(pstLeaveConfigurationMain.getlong(FLD_POSITION_MAX_ID));
            leaveConfigurationDetailPositionRequestOnly.setLeaveConfigurationMainIdRequestOnly(pstLeaveConfigurationMain.getlong(FLD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY));

            return leaveConfigurationDetailPositionRequestOnly;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationDetailPositionRequestOnly(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(LeaveConfigurationDetailPositionRequestOnly leaveConfigurationDetailPositionRequestOnly) throws DBException {
        try {
            PstLeaveConfigurationDetailPositionRequestOnly pstLeaveConfigurationDetailPositionRequestOnly = new PstLeaveConfigurationDetailPositionRequestOnly(0);

            pstLeaveConfigurationDetailPositionRequestOnly.setLong(FLD_POSITION_MIN_ID, leaveConfigurationDetailPositionRequestOnly.getPositionMin());
            pstLeaveConfigurationDetailPositionRequestOnly.setLong(FLD_POSITION_MAX_ID, leaveConfigurationDetailPositionRequestOnly.getPositionMax());
            

            pstLeaveConfigurationDetailPositionRequestOnly.insert();
            leaveConfigurationDetailPositionRequestOnly.setOID(pstLeaveConfigurationDetailPositionRequestOnly.getlong(FLD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationDetailPositionRequestOnly(0), DBException.UNKNOWN);
        }
        return leaveConfigurationDetailPositionRequestOnly.getOID();
    }

    public static long updateExc(LeaveConfigurationDetailPositionRequestOnly leaveConfigurationDetailPositionRequestOnly) throws DBException {
        try {
            if (leaveConfigurationDetailPositionRequestOnly.getOID() != 0) {
                PstLeaveConfigurationDetailPositionRequestOnly pstLeaveConfigurationDetailPositionRequestOnly = new PstLeaveConfigurationDetailPositionRequestOnly(leaveConfigurationDetailPositionRequestOnly.getOID());

                pstLeaveConfigurationDetailPositionRequestOnly.setLong(FLD_POSITION_MIN_ID, leaveConfigurationDetailPositionRequestOnly.getPositionMin());
            pstLeaveConfigurationDetailPositionRequestOnly.setLong(FLD_POSITION_MAX_ID, leaveConfigurationDetailPositionRequestOnly.getPositionMax()); 
                

                pstLeaveConfigurationDetailPositionRequestOnly.update();
                return leaveConfigurationDetailPositionRequestOnly.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationDetailPositionRequestOnly(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstLeaveConfigurationDetailPositionRequestOnly pstLeaveConfigurationMain = new PstLeaveConfigurationDetailPositionRequestOnly(oid);
            pstLeaveConfigurationMain.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationDetailPositionRequestOnly(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION_REQUEST_ONLY;
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
                LeaveConfigurationDetailPositionRequestOnly leaveConfigurationDetailPositionRequestOnly = new LeaveConfigurationDetailPositionRequestOnly();
                resultToObject(rs, leaveConfigurationDetailPositionRequestOnly);
                lists.add(leaveConfigurationDetailPositionRequestOnly);
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
    
    public static LeaveConfigurationDetailPositionRequestOnly getPositionName(int limitStart, int recordToGet, String whereClause, String order) {
        LeaveConfigurationDetailPositionRequestOnly leaveConfigurationDetailPositionRequestOnly = new LeaveConfigurationDetailPositionRequestOnly();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION_REQUEST_ONLY;
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
               leaveConfigurationDetailPositionRequestOnly.setLeaveConfigurationMainIdRequestOnly(rs.getLong(PstLeaveConfigurationDetailPositionRequestOnly.fieldNames[PstLeaveConfigurationDetailPositionRequestOnly.FLD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY]));
               leaveConfigurationDetailPositionRequestOnly.setPositionMin(rs.getLong(PstLeaveConfigurationDetailPositionRequestOnly.fieldNames[PstLeaveConfigurationDetailPositionRequestOnly.FLD_POSITION_MIN_ID]));
               leaveConfigurationDetailPositionRequestOnly.setPositionMax(rs.getLong(PstLeaveConfigurationDetailPositionRequestOnly.fieldNames[PstLeaveConfigurationDetailPositionRequestOnly.FLD_POSITION_MAX_ID]));
               leaveConfigurationDetailPositionRequestOnly.setNamaPosMin(PstPosition.strPositionLevelNames[Integer.parseInt(""+rs.getLong(PstLeaveConfigurationDetailPositionRequestOnly.fieldNames[PstLeaveConfigurationDetailPositionRequestOnly.FLD_POSITION_MIN_ID]))]); 
               leaveConfigurationDetailPositionRequestOnly.setNamaPosMax(PstPosition.strPositionLevelNames[Integer.parseInt(""+rs.getLong(PstLeaveConfigurationDetailPositionRequestOnly.fieldNames[PstLeaveConfigurationDetailPositionRequestOnly.FLD_POSITION_MAX_ID]))]);
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return leaveConfigurationDetailPositionRequestOnly;
        }
    }

    
    public static void resultToObject(ResultSet rs, LeaveConfigurationDetailPositionRequestOnly leaveConfigurationDetailPositionRequestOnly) {
        try {
            leaveConfigurationDetailPositionRequestOnly.setOID(rs.getLong(PstLeaveConfigurationDetailPositionRequestOnly.fieldNames[PstLeaveConfigurationDetailPositionRequestOnly.FLD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY]));
            leaveConfigurationDetailPositionRequestOnly.setPositionMin(rs.getLong(PstLeaveConfigurationDetailPositionRequestOnly.fieldNames[PstLeaveConfigurationDetailPositionRequestOnly.FLD_POSITION_MIN_ID]));
            leaveConfigurationDetailPositionRequestOnly.setPositionMax(rs.getLong(PstLeaveConfigurationDetailPositionRequestOnly.fieldNames[PstLeaveConfigurationDetailPositionRequestOnly.FLD_POSITION_MAX_ID]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long oidMain) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION_REQUEST_ONLY + " WHERE "
                    + PstLeaveConfigurationDetailPositionRequestOnly.fieldNames[PstLeaveConfigurationDetailPositionRequestOnly.FLD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY] + " = " + oidMain;

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
            String sql = "SELECT COUNT(" + PstLeaveConfigurationDetailPositionRequestOnly.fieldNames[PstLeaveConfigurationDetailPositionRequestOnly.FLD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY] + ") FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION_REQUEST_ONLY;
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
        PstLeaveConfigurationDetailPositionRequestOnly pstObj = new PstLeaveConfigurationDetailPositionRequestOnly();
        DBResultSet dbrs = null;
        try {
            String sql = "DELETE FROM " + pstObj.getTableName()
                    + " WHERE " + PstLeaveConfigurationDetailPositionRequestOnly.fieldNames[PstLeaveConfigurationDetailPositionRequestOnly.FLD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY]
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
    
      public static long insert(LeaveConfigurationDetailPositionRequestOnly entObj) {
        try {
            PstLeaveConfigurationDetailPositionRequestOnly pstObj = new PstLeaveConfigurationDetailPositionRequestOnly(0);


            pstObj.setLong(FLD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY, entObj.getLeaveConfigurationMainIdRequestOnly());
            pstObj.setLong(FLD_POSITION_MIN_ID, entObj.getPositionMin());
            pstObj.setLong(FLD_POSITION_MAX_ID, entObj.getPositionMax());

            pstObj.insert();
            return entObj.getLeaveConfigurationMainIdRequestOnly();
        } catch (DBException e) {
            System.out.println(e);
        }
        return 0;
    }
    
    public static boolean setDetailConfigurationPosition(long oidLeaveMainConfig, LeaveConfigurationDetailPositionRequestOnly leaveConfigurationDetailPositionRequestOnly) {

        // do delete
        if (PstLeaveConfigurationDetailPositionRequestOnly.deleteDetailConfigurationPosition(oidLeaveMainConfig) == 0) {
            return false;
        }

//        if (vGroupPosition == null || vGroupPosition.size() == 0) {
//            return true;
//        }

        // than insert
        //for (int i = 0; i < vGroupPosition.size(); i++) {
          //  LeaveConfigurationDetailPosition leaveConfigurationDetailPosition = (LeaveConfigurationDetailPosition) vGroupPosition.get(i);
        if(leaveConfigurationDetailPositionRequestOnly!=null){
            leaveConfigurationDetailPositionRequestOnly.setLeaveConfigurationMainIdRequestOnly(oidLeaveMainConfig); 
            if (PstLeaveConfigurationDetailPositionRequestOnly.insert(leaveConfigurationDetailPositionRequestOnly) == 0) {
                return false;
            }
        }
        //}
        return true;
    }
}
