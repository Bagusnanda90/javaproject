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

public class PstContractType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_CONTRACT_TYPE = "hr_contract_type";
    public static final int FLD_CONTRACT_ID = 0;
    public static final int FLD_CONTRACT_NAME = 1;
    public static String[] fieldNames = {
        "CONTRACT_ID",
        "CONTRACT_NAME"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING
    };

    public PstContractType() {
    }

    public PstContractType(int i) throws DBException {
        super(new PstContractType());
    }

    public PstContractType(String sOid) throws DBException {
        super(new PstContractType(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstContractType(long lOid) throws DBException {
        super(new PstContractType(0));
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
        return TBL_CONTRACT_TYPE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstContractType().getClass().getName();
    }

    public static ContractType fetchExc(long oid) throws DBException {
        try {
            ContractType entContractType = new ContractType();
            PstContractType pstContractType = new PstContractType(oid);
            entContractType.setOID(oid);
            entContractType.setContractName(pstContractType.getString(FLD_CONTRACT_NAME));
            return entContractType;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstContractType(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        ContractType entContractType = fetchExc(entity.getOID());
        entity = (Entity) entContractType;
        return entContractType.getOID();
    }

    public static synchronized long updateExc(ContractType entContractType) throws DBException {
        try {
            if (entContractType.getOID() != 0) {
                PstContractType pstContractType = new PstContractType(entContractType.getOID());
                pstContractType.setString(FLD_CONTRACT_NAME, entContractType.getContractName());
                pstContractType.update();
                return entContractType.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstContractType(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((ContractType) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstContractType pstContractType = new PstContractType(oid);
            pstContractType.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstContractType(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(ContractType entContractType) throws DBException {
        try {
            PstContractType pstContractType = new PstContractType(0);
            pstContractType.setString(FLD_CONTRACT_NAME, entContractType.getContractName());
            pstContractType.insert();
            entContractType.setOID(pstContractType.getLong(FLD_CONTRACT_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstContractType(0), DBException.UNKNOWN);
        }
        return entContractType.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((ContractType) entity);
    }

    public static void resultToObject(ResultSet rs, ContractType entContractType) {
        try {
            entContractType.setOID(rs.getLong(PstContractType.fieldNames[PstContractType.FLD_CONTRACT_ID]));
            entContractType.setContractName(rs.getString(PstContractType.fieldNames[PstContractType.FLD_CONTRACT_NAME]));
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
            String sql = "SELECT * FROM " + TBL_CONTRACT_TYPE;
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
                ContractType entContractType = new ContractType();
                resultToObject(rs, entContractType);
                lists.add(entContractType);
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

    public static boolean checkOID(long entContractTypeId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_CONTRACT_TYPE + " WHERE "
                    + PstContractType.fieldNames[PstContractType.FLD_CONTRACT_ID] + " = " + entContractTypeId;
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
            String sql = "SELECT COUNT(" + PstContractType.fieldNames[PstContractType.FLD_CONTRACT_ID] + ") FROM " + TBL_CONTRACT_TYPE;
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
}