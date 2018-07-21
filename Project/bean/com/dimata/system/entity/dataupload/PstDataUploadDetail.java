/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.system.entity.dataupload;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author khirayinnura
 */
public class PstDataUploadDetail extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_DSJ_DATA_DETAIL = "dsj_data_detail";
    public static final int FLD_DATA_DETAIL_ID = 0;
    public static final int FLD_DATA_DETAIL_TITLE = 1;
    public static final int FLD_DATA_DETAIL_DESC = 2;
    public static final int FLD_DATA_MAIN_ID = 3;
    public static final int FLD_FILENAME = 4;
    public static String[] fieldNames = {
        "DATA_DETAIL_ID",
        "DATA_DETAIL_TITLE",
        "DATA_DETAIL_DESC",
        "DATA_MAIN_ID",
        "FILENAME"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING
    };

    public PstDataUploadDetail() {
    }

    public PstDataUploadDetail(int i) throws DBException {
        super(new PstDataUploadDetail());
    }

    public PstDataUploadDetail(String sOid) throws DBException {
        super(new PstDataUploadDetail(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDataUploadDetail(long lOid) throws DBException {
        super(new PstDataUploadDetail(0));
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
        return TBL_DSJ_DATA_DETAIL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDataUploadDetail().getClass().getName();
    }

    public static DataUploadDetail fetchExc(long oid) throws DBException {
        try {
            DataUploadDetail entDataUploadDetail = new DataUploadDetail();
            PstDataUploadDetail pstDataUploadDetail = new PstDataUploadDetail(oid);
            entDataUploadDetail.setOID(oid);
            entDataUploadDetail.setDataDetailTitle(pstDataUploadDetail.getString(FLD_DATA_DETAIL_TITLE));
            entDataUploadDetail.setDataDetailDesc(pstDataUploadDetail.getString(FLD_DATA_DETAIL_DESC));
            entDataUploadDetail.setDataMainId(pstDataUploadDetail.getLong(FLD_DATA_MAIN_ID));
            entDataUploadDetail.setFilename(pstDataUploadDetail.getString(FLD_FILENAME));
            return entDataUploadDetail;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDataUploadDetail(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        DataUploadDetail entDataUploadDetail = fetchExc(entity.getOID());
        entity = (Entity) entDataUploadDetail;
        return entDataUploadDetail.getOID();
    }

    public static synchronized long updateExc(DataUploadDetail entDataUploadDetail) throws DBException {
        try {
            if (entDataUploadDetail.getOID() != 0) {
                PstDataUploadDetail pstDataUploadDetail = new PstDataUploadDetail(entDataUploadDetail.getOID());
                pstDataUploadDetail.setString(FLD_DATA_DETAIL_TITLE, entDataUploadDetail.getDataDetailTitle());
                pstDataUploadDetail.setString(FLD_DATA_DETAIL_DESC, entDataUploadDetail.getDataDetailDesc());
                pstDataUploadDetail.setLong(FLD_DATA_MAIN_ID, entDataUploadDetail.getDataMainId());
                pstDataUploadDetail.setString(FLD_FILENAME, entDataUploadDetail.getFilename());
                pstDataUploadDetail.update();
                return entDataUploadDetail.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDataUploadDetail(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((DataUploadDetail) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstDataUploadDetail pstDataUploadDetail = new PstDataUploadDetail(oid);
            pstDataUploadDetail.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDataUploadDetail(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(DataUploadDetail entDataUploadDetail) throws DBException {
        try {
            PstDataUploadDetail pstDataUploadDetail = new PstDataUploadDetail(0);
            pstDataUploadDetail.setString(FLD_DATA_DETAIL_TITLE, entDataUploadDetail.getDataDetailTitle());
            pstDataUploadDetail.setString(FLD_DATA_DETAIL_DESC, entDataUploadDetail.getDataDetailDesc());
            pstDataUploadDetail.setLong(FLD_DATA_MAIN_ID, entDataUploadDetail.getDataMainId());
            pstDataUploadDetail.setString(FLD_FILENAME, entDataUploadDetail.getFilename());
            pstDataUploadDetail.insert();
            entDataUploadDetail.setOID(pstDataUploadDetail.getLong(FLD_DATA_DETAIL_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDataUploadDetail(0), DBException.UNKNOWN);
        }
        return entDataUploadDetail.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((DataUploadDetail) entity);
    }

    public static void resultToObject(ResultSet rs, DataUploadDetail entDataUploadDetail) {
        try {
            entDataUploadDetail.setOID(rs.getLong(PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_ID]));
            entDataUploadDetail.setDataDetailTitle(rs.getString(PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_TITLE]));
            entDataUploadDetail.setDataDetailDesc(rs.getString(PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_DESC]));
            entDataUploadDetail.setDataMainId(rs.getLong(PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_MAIN_ID]));
            entDataUploadDetail.setFilename(rs.getString(PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_FILENAME]));
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
            String sql = "SELECT * FROM " + TBL_DSJ_DATA_DETAIL;
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
                DataUploadDetail entDataUploadDetail = new DataUploadDetail();
                resultToObject(rs, entDataUploadDetail);
                lists.add(entDataUploadDetail);
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
    
    public static DataUploadDetail getObjDocPicture(long mainId) 
    {
        DataUploadDetail objDataUploadDetail = new DataUploadDetail();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_ID] +
                         ", " + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_TITLE] +
                         ", " + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_DESC] +
                         ", " + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_MAIN_ID] +
                         ", " + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_FILENAME] +
                         " FROM " + TBL_DSJ_DATA_DETAIL +
                         " WHERE " + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_MAIN_ID] +
                         " = " + mainId;
            
            System.out.println("SQL PstEmpRelevantDoc.getObjEmpPicture : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                objDataUploadDetail.setOID(rs.getLong(1));
                objDataUploadDetail.setDataDetailTitle(rs.getString(2));
                objDataUploadDetail.setDataDetailDesc(rs.getString(3));
                objDataUploadDetail.setDataMainId(rs.getLong(4));
                objDataUploadDetail.setFilename(rs.getString(5));
                
                return objDataUploadDetail;
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            DBResultSet.close(dbrs);
            return objDataUploadDetail;
        }
    }
    
    public static void updateFileName(String fileName,long idDetail) {
        try {
            String sql = "UPDATE " + PstDataUploadDetail.TBL_DSJ_DATA_DETAIL+
            " SET " + PstDataUploadDetail.fieldNames[FLD_FILENAME] + " = '" + fileName +"'"+
            " WHERE " + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_ID] +
            " = " + idDetail ;           
            System.out.println("sql PstDataUploadDetail.updateFileName : " + sql);
            int result = DBHandler.execUpdate(sql);
        } catch (Exception e) {
            System.out.println("\tExc updateFileName : " + e.toString());
        } finally {
            //System.out.println("\tFinal updatePresenceStatus");
        }
    }

    public static boolean checkOID(long entDataUploadDetailId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_DSJ_DATA_DETAIL + " WHERE "
                    + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_ID] + " = " + entDataUploadDetailId;
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
            String sql = "SELECT COUNT(" + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_ID] + ") FROM " + TBL_DSJ_DATA_DETAIL;
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
                    DataUploadDetail entDataUploadDetail = (DataUploadDetail) list.get(ls);
                    if (oid == entDataUploadDetail.getOID()) {
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
