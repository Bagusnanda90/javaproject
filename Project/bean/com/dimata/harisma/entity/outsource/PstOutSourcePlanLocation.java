/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

/**
 *
 * @author dimata005
 */
//public class PstOutSourcePlanLocation {
//}
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstPosition;
import java.sql.*;
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.Command;
import java.util.Hashtable;


import java.util.Vector;

public class PstOutSourcePlanLocation extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_OUTSOURCEPLANLOCATION = "hr_outsrc_plan_loc";
    public static final int FLD_OUTSOURCEPLANLOCID = 0;
    public static final int FLD_OUTSOURCEPLANID = 1;
    public static final int FLD_COMPANYID = 2;
    public static final int FLD_DIVISIONID = 3;
    public static final int FLD_DEPARTMENTID = 4;
    public static final int FLD_SECTIONID = 5;
    public static final int FLD_NUMBERTW1 = 6;
    public static final int FLD_NUMBERTW2 = 7;
    public static final int FLD_NUMBERTW3 = 8;
    public static final int FLD_NUMBERTW4 = 9;
    public static final int FLD_PREV_EXISTING = 10;
    public static final int FLD_POSITION_ID = 11;
    
    public static String[] fieldNames = {
        "OUTSRC_PLAN_LOC_ID",
        "OUTSOURCE_PLAN_ID",
        "COMPANY_ID",
        "DIVISION_ID",
        "DEPARTMENT_ID",
        "SECTION_ID",
        "NUMBER_TW1",
        "NUMBER_TW2",
        "NUMBER_TW3",
        "NUMBER_TW4",
        "PREV_EXISTING",
        "POSITION_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG
    };

    public PstOutSourcePlanLocation() {
    }

    public PstOutSourcePlanLocation(int i) throws DBException {
        super(new PstOutSourcePlanLocation());
    }

    public PstOutSourcePlanLocation(String sOid) throws DBException {
        super(new PstOutSourcePlanLocation(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstOutSourcePlanLocation(long lOid) throws DBException {
        super(new PstOutSourcePlanLocation(0));
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
        return TBL_OUTSOURCEPLANLOCATION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstOutSourcePlanLocation().getClass().getName();
    }

    public static OutSourcePlanLocation fetchExc(long oid) throws DBException {
        try {
            OutSourcePlanLocation entOutSourcePlanLocation = new OutSourcePlanLocation();
            PstOutSourcePlanLocation pstOutSourcePlanLocation = new PstOutSourcePlanLocation(oid);
            entOutSourcePlanLocation.setOID(oid);
            entOutSourcePlanLocation.setOutsourcePlanId(pstOutSourcePlanLocation.getlong(FLD_OUTSOURCEPLANID));
            entOutSourcePlanLocation.setCompanyId(pstOutSourcePlanLocation.getlong(FLD_COMPANYID));
            entOutSourcePlanLocation.setDivisionId(pstOutSourcePlanLocation.getlong(FLD_DIVISIONID));
            entOutSourcePlanLocation.setDepartmentId(pstOutSourcePlanLocation.getlong(FLD_DEPARTMENTID));
            entOutSourcePlanLocation.setSectionId(pstOutSourcePlanLocation.getlong(FLD_SECTIONID));
            entOutSourcePlanLocation.setNumberTW1(pstOutSourcePlanLocation.getInt(FLD_NUMBERTW1));
            entOutSourcePlanLocation.setNumberTW2(pstOutSourcePlanLocation.getInt(FLD_NUMBERTW2));
            entOutSourcePlanLocation.setNumberTW3(pstOutSourcePlanLocation.getInt(FLD_NUMBERTW3));
            entOutSourcePlanLocation.setNumberTW4(pstOutSourcePlanLocation.getInt(FLD_NUMBERTW4));
            entOutSourcePlanLocation.setPrevExisting(pstOutSourcePlanLocation.getInt(FLD_PREV_EXISTING));
            entOutSourcePlanLocation.setPositionId(pstOutSourcePlanLocation.getlong(FLD_POSITION_ID));
            return entOutSourcePlanLocation;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlanLocation(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        OutSourcePlanLocation entOutSourcePlanLocation = fetchExc(entity.getOID());
        entity = (Entity) entOutSourcePlanLocation;
        return entOutSourcePlanLocation.getOID();
    }

    public static synchronized long updateExc(OutSourcePlanLocation entOutSourcePlanLocation) throws DBException {
        try {
            if (entOutSourcePlanLocation.getOID() != 0) {
                PstOutSourcePlanLocation pstOutSourcePlanLocation = new PstOutSourcePlanLocation(entOutSourcePlanLocation.getOID());
                pstOutSourcePlanLocation.setLong(FLD_OUTSOURCEPLANID, entOutSourcePlanLocation.getOutsourcePlanId());
                pstOutSourcePlanLocation.setLong(FLD_COMPANYID, entOutSourcePlanLocation.getCompanyId());
                pstOutSourcePlanLocation.setLong(FLD_DIVISIONID, entOutSourcePlanLocation.getDivisionId());
                pstOutSourcePlanLocation.setLong(FLD_DEPARTMENTID, entOutSourcePlanLocation.getDepartmentId());
                pstOutSourcePlanLocation.setLong(FLD_SECTIONID, entOutSourcePlanLocation.getSectionId());
                pstOutSourcePlanLocation.setInt(FLD_NUMBERTW1, entOutSourcePlanLocation.getNumberTW1());
                pstOutSourcePlanLocation.setInt(FLD_NUMBERTW2, entOutSourcePlanLocation.getNumberTW2());
                pstOutSourcePlanLocation.setInt(FLD_NUMBERTW3, entOutSourcePlanLocation.getNumberTW3());
                pstOutSourcePlanLocation.setInt(FLD_NUMBERTW4, entOutSourcePlanLocation.getNumberTW4());
                pstOutSourcePlanLocation.setInt(FLD_PREV_EXISTING, entOutSourcePlanLocation.getPrevExisting());
                pstOutSourcePlanLocation.setLong(FLD_POSITION_ID, entOutSourcePlanLocation.getPositionId());
                
                pstOutSourcePlanLocation.update();
                return entOutSourcePlanLocation.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlanLocation(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((OutSourcePlanLocation) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstOutSourcePlanLocation pstOutSourcePlanLocation = new PstOutSourcePlanLocation(oid);
            pstOutSourcePlanLocation.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlanLocation(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(OutSourcePlanLocation entOutSourcePlanLocation) throws DBException {
        try {
            PstOutSourcePlanLocation pstOutSourcePlanLocation = new PstOutSourcePlanLocation(0);
            pstOutSourcePlanLocation.setLong(FLD_OUTSOURCEPLANID, entOutSourcePlanLocation.getOutsourcePlanId());
            pstOutSourcePlanLocation.setLong(FLD_COMPANYID, entOutSourcePlanLocation.getCompanyId());
            pstOutSourcePlanLocation.setLong(FLD_DIVISIONID, entOutSourcePlanLocation.getDivisionId());
            pstOutSourcePlanLocation.setLong(FLD_DEPARTMENTID, entOutSourcePlanLocation.getDepartmentId());
            pstOutSourcePlanLocation.setLong(FLD_SECTIONID, entOutSourcePlanLocation.getSectionId());
            pstOutSourcePlanLocation.setInt(FLD_NUMBERTW1, entOutSourcePlanLocation.getNumberTW1());
            pstOutSourcePlanLocation.setInt(FLD_NUMBERTW2, entOutSourcePlanLocation.getNumberTW2());
            pstOutSourcePlanLocation.setInt(FLD_NUMBERTW3, entOutSourcePlanLocation.getNumberTW3());
            pstOutSourcePlanLocation.setInt(FLD_NUMBERTW4, entOutSourcePlanLocation.getNumberTW4());
            pstOutSourcePlanLocation.setInt(FLD_PREV_EXISTING, entOutSourcePlanLocation.getPrevExisting());
            pstOutSourcePlanLocation.setLong(FLD_POSITION_ID, entOutSourcePlanLocation.getPositionId());
            
            pstOutSourcePlanLocation.insert();
            entOutSourcePlanLocation.setOID(pstOutSourcePlanLocation.getlong(FLD_OUTSOURCEPLANLOCID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourcePlanLocation(0), DBException.UNKNOWN);
        }
        return entOutSourcePlanLocation.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((OutSourcePlanLocation) entity);
    }

    public static void resultToObject(ResultSet rs, OutSourcePlanLocation entOutSourcePlanLocation) {
        try {
            
            entOutSourcePlanLocation.setOID(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANLOCID]));
            entOutSourcePlanLocation.setOutsourcePlanId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]));
            entOutSourcePlanLocation.setCompanyId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_COMPANYID]));
            entOutSourcePlanLocation.setDivisionId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID]));
            entOutSourcePlanLocation.setDepartmentId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DEPARTMENTID]));
            entOutSourcePlanLocation.setSectionId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_SECTIONID]));
            entOutSourcePlanLocation.setNumberTW1(rs.getInt(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW1]));
            entOutSourcePlanLocation.setNumberTW2(rs.getInt(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW2]));
            entOutSourcePlanLocation.setNumberTW3(rs.getInt(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW3]));
            entOutSourcePlanLocation.setNumberTW4(rs.getInt(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW4]));
            entOutSourcePlanLocation.setPrevExisting(rs.getInt(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_PREV_EXISTING]));
            entOutSourcePlanLocation.setPositionId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]));
            
        } catch (Exception e) {
        }
    }
    
    
    public static void resultToObjectJoin(ResultSet rs, OutSourcePlanLocation entOutSourcePlanLocation) {
        try {
            
            entOutSourcePlanLocation.setOID(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANLOCID]));
            entOutSourcePlanLocation.setOutsourcePlanId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]));
            entOutSourcePlanLocation.setCompanyId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_COMPANYID]));
            entOutSourcePlanLocation.setDivisionId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID]));
            entOutSourcePlanLocation.setDepartmentId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DEPARTMENTID]));
            entOutSourcePlanLocation.setSectionId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_SECTIONID]));
            entOutSourcePlanLocation.setNumberTW1(rs.getInt(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW1]));
            entOutSourcePlanLocation.setNumberTW2(rs.getInt(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW2]));
            entOutSourcePlanLocation.setNumberTW3(rs.getInt(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW3]));
            entOutSourcePlanLocation.setNumberTW4(rs.getInt(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW4]));
            entOutSourcePlanLocation.setPrevExisting(rs.getInt(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_PREV_EXISTING]));
            entOutSourcePlanLocation.setPositionId(rs.getLong(PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID]));
            entOutSourcePlanLocation.setNameDivision(rs.getString("DIVISION"));
            entOutSourcePlanLocation.setNamePosition(rs.getString("POSITION"));
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
            String sql = "SELECT * FROM " + TBL_OUTSOURCEPLANLOCATION;
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
                OutSourcePlanLocation entOutSourcePlanLocation = new OutSourcePlanLocation();
                resultToObject(rs, entOutSourcePlanLocation);
                lists.add(entOutSourcePlanLocation);
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
    
    public static Hashtable hashList(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable lists = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_OUTSOURCEPLANLOCATION;
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
                OutSourcePlanLocation entOutSourcePlanLocation = new OutSourcePlanLocation();
                resultToObject(rs, entOutSourcePlanLocation);
                lists.put(entOutSourcePlanLocation.getPositionId(),entOutSourcePlanLocation);
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
    
    public static Vector<OutSourcePlanLocation> listJoin(int limitStart, int recordToGet, String whereClause, String order) {
        Vector<OutSourcePlanLocation> lists = new Vector<OutSourcePlanLocation>();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT ho.*, hd.DIVISION, hr.POSITION  FROM "+ TBL_OUTSOURCEPLANLOCATION +" AS ho "+
                         " INNER JOIN "+ PstDivision.TBL_HR_DIVISION+" AS hd "+
                         " ON ho.DIVISION_ID=hd.DIVISION_ID "+
                         " INNER JOIN "+ PstPosition.TBL_HR_POSITION+" AS hr "+
                         " ON hr.POSITION_ID=ho.POSITION_ID";
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
                OutSourcePlanLocation entOutSourcePlanLocation = new OutSourcePlanLocation();
                resultToObjectJoin(rs, entOutSourcePlanLocation);
                lists.add(entOutSourcePlanLocation);
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
    
    
    public static Vector listJoinPositionOnly(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT hr.POSITION_ID , hr.POSITION  FROM "+ TBL_OUTSOURCEPLANLOCATION +" AS ho "+
                         " INNER JOIN "+ PstDivision.TBL_HR_DIVISION+" AS hd "+
                         " ON ho.DIVISION_ID=hd.DIVISION_ID "+
                         " INNER JOIN "+ PstPosition.TBL_HR_POSITION+" AS hr "+
                         " ON hr.POSITION_ID=ho.POSITION_ID";
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
            sql=sql+" GROUP BY hr.POSITION ";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vNilai =new Vector();
                vNilai.add(rs.getLong("POSITION_ID"));
                vNilai.add(rs.getString("POSITION"));
                lists.add(vNilai);
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
    
    
    public static Vector listJoinDivisionOnly(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT hd.DIVISION_ID, hd.DIVISION  FROM "+ TBL_OUTSOURCEPLANLOCATION +" AS ho "+
                         " INNER JOIN "+ PstDivision.TBL_HR_DIVISION+" AS hd "+
                         " ON ho.DIVISION_ID=hd.DIVISION_ID "+
                         " INNER JOIN "+ PstPosition.TBL_HR_POSITION+" AS hr "+
                         " ON hr.POSITION_ID=ho.POSITION_ID";
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
            sql=sql+" GROUP BY hd.DIVISION_ID ";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vNilai =new Vector();
                vNilai.add(rs.getLong("DIVISION_ID"));
                vNilai.add(rs.getString("DIVISION"));
                lists.add(vNilai);
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

    public static boolean checkOID(long entOutSourcePlanLocationId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_OUTSOURCEPLANLOCATION + " WHERE "
                    + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANLOCID] + " = " + entOutSourcePlanLocationId;
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
            String sql = "SELECT COUNT(" + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANLOCID] + ") FROM " + TBL_OUTSOURCEPLANLOCATION;
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
                    OutSourcePlanLocation entOutSourcePlanLocation = (OutSourcePlanLocation) list.get(ls);
                    if (oid == entOutSourcePlanLocation.getOID()) {
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
    
    public static void deleteAllPlanLocation(String whereClause){

            String sql = "DELETE FROM "+TBL_OUTSOURCEPLANLOCATION+

                " WHERE "+whereClause;
            

            try{

                    DBHandler.execUpdate(sql);

            }

            catch(Exception e){

            }
        }
    
    
    public static void updateInputanPlanLocation(String whereClause, String colomnUpdate, double valueUpdate){

            String sql = "UPDATE "+TBL_OUTSOURCEPLANLOCATION+ " SET "+colomnUpdate+"='"+valueUpdate+"' WHERE "+whereClause;
            

            try{

                    DBHandler.execUpdate(sql);

            }

            catch(Exception e){

            }
        }
    
}