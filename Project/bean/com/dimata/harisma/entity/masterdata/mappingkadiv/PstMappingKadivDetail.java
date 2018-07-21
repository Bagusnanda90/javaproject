/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Satrya Ramayu
 * @version  	:  [version]
 */
/**
 * *****************************************************************
 * Class Description : PstMappingKadivDetail Imput Parameters : [input
 * parameter ...] Output : [output ...]
 ******************************************************************
 */
package com.dimata.harisma.entity.masterdata.mappingkadiv;

/**
 *
 * @author Wiweka
 */
/* package java */
import java.sql.*;
import java.util.*;


/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

public class PstMappingKadivDetail extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_MAPPING_KADIV_DETAIL = "hr_mapping_kadiv_detail";//"hr_company";
    public static final int FLD_MAPPING_KADIV_MAIN_ID = 0;
    public static final int FLD_LOCATION_ID = 1;
    public static final String[] fieldNames = {
        "MAPPING_KADIV_MAIN_ID",
        "LOCATION_ID",
    };
    public static final int[] fieldTypes = {
        TYPE_PK + TYPE_FK + TYPE_LONG,
        TYPE_LONG,
    };

    public PstMappingKadivDetail() {
    }

    public PstMappingKadivDetail(int i) throws DBException {
        super(new PstMappingKadivDetail());
    }

    public PstMappingKadivDetail(String sOid) throws DBException {
        super(new PstMappingKadivDetail(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstMappingKadivDetail(long lOid) throws DBException {
        super(new PstMappingKadivDetail(0));
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
        return TBL_HR_MAPPING_KADIV_DETAIL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstMappingKadivDetail().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        MappingKadivDetail mappingKadivDetail = fetchExc(ent.getOID());
        ent = (Entity) mappingKadivDetail;
        return mappingKadivDetail.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((MappingKadivDetail) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((MappingKadivDetail) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static MappingKadivDetail fetchExc(long oid) throws DBException {
        try {
            MappingKadivDetail mappingKadivDetail = new MappingKadivDetail();
            PstMappingKadivDetail pstMappingKadivDetail = new PstMappingKadivDetail(oid);
            mappingKadivDetail.setOID(oid);

            //mappingKadivDetail.setMappingkadivMainId(pstMappingKadivDetail.getlong(FLD_MAPPING_KADIV_MAIN_ID));
            mappingKadivDetail.setLocationId(pstMappingKadivDetail.getlong(FLD_LOCATION_ID));

            return mappingKadivDetail;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMappingKadivDetail(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(MappingKadivDetail mappingKadivDetail) throws DBException {
        try {
            PstMappingKadivDetail pstMappingKadivDetail = new PstMappingKadivDetail(0);

            //pstMappingKadivDetail.setLong(FLD_MAPPING_KADIV_MAIN_ID, mappingKadivDetail.getMappingkadivMainId());
            pstMappingKadivDetail.setLong(FLD_LOCATION_ID, mappingKadivDetail.getLocationId());


            pstMappingKadivDetail.insert();
            mappingKadivDetail.setOID(pstMappingKadivDetail.getlong(FLD_MAPPING_KADIV_MAIN_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMappingKadivDetail(0), DBException.UNKNOWN);
        }
        return mappingKadivDetail.getOID();
    }

    public static long updateExc(MappingKadivDetail mappingKadivDetail) throws DBException {
        try {
            if (mappingKadivDetail.getOID() != 0) {
                PstMappingKadivDetail pstMappingKadivDetail = new PstMappingKadivDetail(mappingKadivDetail.getOID());

                pstMappingKadivDetail.setLong(FLD_LOCATION_ID, mappingKadivDetail.getLocationId());


                pstMappingKadivDetail.update();
                return mappingKadivDetail.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMappingKadivDetail(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstMappingKadivDetail pstMappingKadivDetail = new PstMappingKadivDetail(oid);
            pstMappingKadivDetail.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMappingKadivDetail(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_MAPPING_KADIV_DETAIL;
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
                MappingKadivDetail mappingKadivDetail = new MappingKadivDetail();
                resultToObject(rs, mappingKadivDetail);
                lists.add(mappingKadivDetail);
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

   
    public static void resultToObject(ResultSet rs, MappingKadivDetail mappingKadivDetail) {
        try {
            mappingKadivDetail.setOID(rs.getLong(PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_MAPPING_KADIV_MAIN_ID]));
            mappingKadivDetail.setLocationId(rs.getLong(PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_LOCATION_ID]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long oidMain) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_MAPPING_KADIV_DETAIL + " WHERE "
                    + PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_MAPPING_KADIV_MAIN_ID] + " = " + oidMain;

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
            String sql = "SELECT COUNT(" + PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_MAPPING_KADIV_MAIN_ID] + ") FROM " + TBL_HR_MAPPING_KADIV_DETAIL;
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
    
    public static long deleteMappingKadivDetail(long oid) {
        PstMappingKadivDetail pstObj = new PstMappingKadivDetail();
        DBResultSet dbrs = null;
        try {
            String sql = "DELETE FROM " + pstObj.getTableName()
                    + " WHERE " + PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_MAPPING_KADIV_MAIN_ID]
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
    
      public static long insert(MappingKadivDetail entObj) {
        try {
            PstMappingKadivDetail pstObj = new PstMappingKadivDetail(0);


            pstObj.setLong(FLD_MAPPING_KADIV_MAIN_ID ,entObj.getMappingkadivMainId());
            pstObj.setLong(FLD_LOCATION_ID, entObj.getLocationId());

            pstObj.insert();
            return entObj.getMappingkadivMainId();
        } catch (DBException e) {
            System.out.println(e);
        }
        return 0;
    }
    
    
     public static boolean setDetailMappingKadiv(long oidMappingKadivMain, Vector vGroupLocation) {

        // do delete
        if (PstMappingKadivDetail.deleteMappingKadivDetail(oidMappingKadivMain) == 0) {
            return false;
        }

        if (vGroupLocation == null || vGroupLocation.size() == 0) {
            return true;
        }

        // than insert
        for (int i = 0; i < vGroupLocation.size(); i++) {
            MappingKadivDetail mappingKadivDetail = (MappingKadivDetail) vGroupLocation.get(i);
            mappingKadivDetail.setMappingkadivMainId(oidMappingKadivMain); 
            if (PstMappingKadivDetail.insert(mappingKadivDetail) == 0) {
                return false;
            }
        }
        return true;
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
                    MappingKadivDetail mappingKadivDetail = (MappingKadivDetail) list.get(ls);
                    if (oid == mappingKadivDetail.getOID()) {
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
    
     public static long getOidMainMappingKadiv(int limitStart, int recordToGet, String whereClause, String order) {
        long oidMainMappingKadiv =0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT km.*,mkd."+PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_LOCATION_ID] + " FROM "+PstMappingKadivMain.TBL_HR_MAPPING_KADIV_MAIN + " AS km "
            + " INNER JOIN "+ PstMappingKadivDetail.TBL_HR_MAPPING_KADIV_DETAIL + " AS mkd ON km."+PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_MAPPING_KADIV_MAIN_ID] +"=mkd."+PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_MAPPING_KADIV_MAIN_ID];
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
               
                oidMainMappingKadiv= rs.getLong(PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_MAPPING_KADIV_MAIN_ID]);
            }
            rs.close();
           

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
             return oidMainMappingKadiv;
        }
    }
     
     public static MappingKadivDetail getMappingKadivJoinDetail(int limitStart, int recordToGet, String whereClause, String order) {
        MappingKadivDetail mappingKadivDetail = new MappingKadivDetail();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT km.*,mkd."+PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_LOCATION_ID] + " FROM "+PstMappingKadivMain.TBL_HR_MAPPING_KADIV_MAIN + " AS km "
            + " INNER JOIN "+ PstMappingKadivDetail.TBL_HR_MAPPING_KADIV_DETAIL + " AS mkd ON km."+PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_MAPPING_KADIV_MAIN_ID] +"=mkd."+PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_MAPPING_KADIV_MAIN_ID];
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
            String sOidLocation="";
            long oidMain=0;
            while (rs.next()) {
                //resultToObject(rs, mappingKadivMain);
                sOidLocation = sOidLocation + rs.getLong(PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_LOCATION_ID])+",";
                oidMain= rs.getLong(PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_MAPPING_KADIV_MAIN_ID]);
            }
            if(sOidLocation!=null && sOidLocation.length()>0){
                sOidLocation = sOidLocation.substring(0, sOidLocation.length()-1);
                 mappingKadivDetail.setLocationIds(sOidLocation.split(","));
                 mappingKadivDetail.setMappingkadivMainId(oidMain); 
            }
           
            rs.close();
           

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
             return mappingKadivDetail;
        }
    }
}
