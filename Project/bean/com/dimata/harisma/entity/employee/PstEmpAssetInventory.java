/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Gunadi
 */
public class PstEmpAssetInventory extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_EMP_ASSET_INVENTORY = "hr_emp_asset_inventory";
    public static final int FLD_EMP_ASSET_INVENTORY_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final int FLD_ASSIGNMENT_DATE = 2;
    public static final int FLD_CHECK_BY = 3;
    public static final int FLD_APPROVED_BY = 4;
    public static final int FLD_RECEIVED_BY = 5;
    public static final int FLD_APPROVED_DATE = 6;
    public static final int FLD_RETURN_DATE = 7;
    public static final int FLD_RECEIVED_DATE = 8;
    public static final int FLD_DETAIL = 9;
    public static final int FLD_STATUS = 10;
    public static final int FLD_DOC_TYPE = 11;
    public static final int FLD_CHECK_DATE = 12;
    public static String[] fieldNames = {
        "EMP_ASSET_INVENTORY_ID",
        "EMPLOYEE_ID",
        "ASSIGNMENT_DATE",
        "CHECK_BY",
        "APPROVED_BY",
        "RECEIVED_BY",
        "APPROVED_DATE",
        "RETURN_DATE",
        "RECEIVED_DATE",
        "DETAIL",
        "STATUS",
        "DOC_TYPE",
        "CHECK_DATE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_DATE
    };

    public static final int HAND_OVER = 0;
    public static final int RETURN = 1;
    
    public static String[] typeDoc = {
        "Hand Over",
        "Return"
    };
    
    public static final int DRAFT = 0;
    public static final int CHECKED = 1;
    public static final int APPROVED = 2;
    public static final int RECEIVED = 3;
    public static final int RETURNED = 4;
    
    public static String[] statusDoc = {
        "Draft",
        "Checked",
        "Approved",
        "Recived",
        "Returned"
    };
    
    public PstEmpAssetInventory() {
    }

    public PstEmpAssetInventory(int i) throws DBException {
        super(new PstEmpAssetInventory());
    }

    public PstEmpAssetInventory(String sOid) throws DBException {
        super(new PstEmpAssetInventory(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpAssetInventory(long lOid) throws DBException {
        super(new PstEmpAssetInventory(0));
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
        return TBL_EMP_ASSET_INVENTORY;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpAssetInventory().getClass().getName();
    }

    public static EmpAssetInventory fetchExc(long oid) throws DBException {
        try {
            EmpAssetInventory entEmpAssetInventory = new EmpAssetInventory();
            PstEmpAssetInventory pstEmpAssetInventory = new PstEmpAssetInventory(oid);
            entEmpAssetInventory.setOID(oid);
            entEmpAssetInventory.setEmployeeId(pstEmpAssetInventory.getLong(FLD_EMPLOYEE_ID));
            entEmpAssetInventory.setAssignmentDate(pstEmpAssetInventory.getDate(FLD_ASSIGNMENT_DATE));
            entEmpAssetInventory.setCheckBy(pstEmpAssetInventory.getLong(FLD_CHECK_BY));
            entEmpAssetInventory.setApprovedBy(pstEmpAssetInventory.getLong(FLD_APPROVED_BY));
            entEmpAssetInventory.setReceivedBy(pstEmpAssetInventory.getLong(FLD_RECEIVED_BY));
            entEmpAssetInventory.setApprovedDate(pstEmpAssetInventory.getDate(FLD_APPROVED_DATE));
            entEmpAssetInventory.setReturnDate(pstEmpAssetInventory.getDate(FLD_RETURN_DATE));
            entEmpAssetInventory.setReceivedDate(pstEmpAssetInventory.getDate(FLD_RECEIVED_DATE));
            entEmpAssetInventory.setDetail(pstEmpAssetInventory.getString(FLD_DETAIL));
            entEmpAssetInventory.setStatus(pstEmpAssetInventory.getInt(FLD_STATUS));
            entEmpAssetInventory.setDocType(pstEmpAssetInventory.getInt(FLD_DOC_TYPE));
            entEmpAssetInventory.setCheckDate(pstEmpAssetInventory.getDate(FLD_CHECK_DATE));
            return entEmpAssetInventory;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAssetInventory(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        EmpAssetInventory entEmpAssetInventory = fetchExc(entity.getOID());
        entity = (Entity) entEmpAssetInventory;
        return entEmpAssetInventory.getOID();
    }

    public static synchronized long updateExc(EmpAssetInventory entEmpAssetInventory) throws DBException {
        try {
            if (entEmpAssetInventory.getOID() != 0) {
                PstEmpAssetInventory pstEmpAssetInventory = new PstEmpAssetInventory(entEmpAssetInventory.getOID());
                pstEmpAssetInventory.setLong(FLD_EMPLOYEE_ID, entEmpAssetInventory.getEmployeeId());
                pstEmpAssetInventory.setDate(FLD_ASSIGNMENT_DATE, entEmpAssetInventory.getAssignmentDate());
                pstEmpAssetInventory.setLong(FLD_CHECK_BY, entEmpAssetInventory.getCheckBy());
                pstEmpAssetInventory.setLong(FLD_APPROVED_BY, entEmpAssetInventory.getApprovedBy());
                pstEmpAssetInventory.setLong(FLD_RECEIVED_BY, entEmpAssetInventory.getReceivedBy());
                pstEmpAssetInventory.setDate(FLD_APPROVED_DATE, entEmpAssetInventory.getApprovedDate());
                pstEmpAssetInventory.setDate(FLD_RETURN_DATE, entEmpAssetInventory.getReturnDate());
                pstEmpAssetInventory.setDate(FLD_RECEIVED_DATE, entEmpAssetInventory.getReceivedDate());
                pstEmpAssetInventory.setString(FLD_DETAIL, entEmpAssetInventory.getDetail());
                pstEmpAssetInventory.setInt(FLD_STATUS, entEmpAssetInventory.getStatus());
                pstEmpAssetInventory.setInt(FLD_DOC_TYPE, entEmpAssetInventory.getDocType());
                pstEmpAssetInventory.setDate(FLD_CHECK_DATE, entEmpAssetInventory.getCheckDate());
                pstEmpAssetInventory.update();
                return entEmpAssetInventory.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAssetInventory(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((EmpAssetInventory) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstEmpAssetInventory pstEmpAssetInventory = new PstEmpAssetInventory(oid);
            pstEmpAssetInventory.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAssetInventory(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(EmpAssetInventory entEmpAssetInventory) throws DBException {
        try {
            PstEmpAssetInventory pstEmpAssetInventory = new PstEmpAssetInventory(0);
            pstEmpAssetInventory.setLong(FLD_EMPLOYEE_ID, entEmpAssetInventory.getEmployeeId());
            pstEmpAssetInventory.setDate(FLD_ASSIGNMENT_DATE, entEmpAssetInventory.getAssignmentDate());
            pstEmpAssetInventory.setLong(FLD_CHECK_BY, entEmpAssetInventory.getCheckBy());
            pstEmpAssetInventory.setLong(FLD_APPROVED_BY, entEmpAssetInventory.getApprovedBy());
            pstEmpAssetInventory.setLong(FLD_RECEIVED_BY, entEmpAssetInventory.getReceivedBy());
            pstEmpAssetInventory.setDate(FLD_APPROVED_DATE, entEmpAssetInventory.getApprovedDate());
            pstEmpAssetInventory.setDate(FLD_RETURN_DATE, entEmpAssetInventory.getReturnDate());
            pstEmpAssetInventory.setDate(FLD_RECEIVED_DATE, entEmpAssetInventory.getReceivedDate());
            pstEmpAssetInventory.setString(FLD_DETAIL, entEmpAssetInventory.getDetail());
            pstEmpAssetInventory.setInt(FLD_STATUS, entEmpAssetInventory.getStatus());
            pstEmpAssetInventory.setInt(FLD_DOC_TYPE, entEmpAssetInventory.getDocType());
            pstEmpAssetInventory.setDate(FLD_CHECK_DATE, entEmpAssetInventory.getCheckDate());
            pstEmpAssetInventory.insert();
            entEmpAssetInventory.setOID(pstEmpAssetInventory.getLong(FLD_EMP_ASSET_INVENTORY_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAssetInventory(0), DBException.UNKNOWN);
        }
        return entEmpAssetInventory.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((EmpAssetInventory) entity);
    }

    public static void resultToObject(ResultSet rs, EmpAssetInventory entEmpAssetInventory) {
        try {
            entEmpAssetInventory.setOID(rs.getLong(PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_EMP_ASSET_INVENTORY_ID]));
            entEmpAssetInventory.setEmployeeId(rs.getLong(PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_EMPLOYEE_ID]));
            entEmpAssetInventory.setAssignmentDate(rs.getDate(PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_ASSIGNMENT_DATE]));
            entEmpAssetInventory.setCheckBy(rs.getLong(PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_CHECK_BY]));
            entEmpAssetInventory.setApprovedBy(rs.getLong(PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_APPROVED_BY]));
            entEmpAssetInventory.setReceivedBy(rs.getLong(PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_RECEIVED_BY]));
            entEmpAssetInventory.setApprovedDate(rs.getDate(PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_APPROVED_DATE]));
            entEmpAssetInventory.setReturnDate(rs.getDate(PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_RETURN_DATE]));
            entEmpAssetInventory.setReceivedDate(rs.getDate(PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_RECEIVED_DATE]));
            entEmpAssetInventory.setDetail(rs.getString(PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_DETAIL]));
            entEmpAssetInventory.setStatus(rs.getInt(PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_STATUS]));
            entEmpAssetInventory.setDocType(rs.getInt(PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_DOC_TYPE]));
            entEmpAssetInventory.setCheckDate(rs.getDate(PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_CHECK_DATE]));
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
            String sql = "SELECT * FROM " + TBL_EMP_ASSET_INVENTORY;
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
                EmpAssetInventory entEmpAssetInventory = new EmpAssetInventory();
                resultToObject(rs, entEmpAssetInventory);
                lists.add(entEmpAssetInventory);
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
    
    //location pada pos
    public static Hashtable listLocation() {
        String dbPos = PstSystemProperty.getValueByName("PROCHAIN_DB_NAME");
        Hashtable hashtableLoc = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM "+dbPos+".location";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                hashtableLoc.put("LOCATION_ID", rs.getLong("LOCATION_ID"));
                hashtableLoc.put("NAME", rs.getString("NAME"));
            }
            rs.close();
            return hashtableLoc;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return  hashtableLoc;
    }
    
    //hashtable material
    public static Hashtable listMaterial(long locationId, long categoryId) {
        String dbPos = PstSystemProperty.getValueByName("PROCHAIN_DB_NAME");
        Hashtable hashtableMaterial = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "
                            + "material.MATERIAL_ID,"
                            + "material.NAME AS MATERIAL_NAME, "
                            + "material.CATEGORY_ID,"
                            + "cat.NAME AS CATEGORY_NAME,"
                            + "loc.LOCATION_ID,"
                            + "loc.NAME AS LOCATION_NAME,"
                            + "stock.QTY "
                        + " FROM pos_material AS material"
                        + " LEFT JOIN pos_material_stock AS stock ON "
                        + " material.MATERIAL_ID = stock.MATERIAL_UNIT_ID "
                        + " LEFT JOIN pos_category AS cat ON "
                        + " material.CATEGORY_ID = cat.CATEGORY_ID "
                        + " LEFT JOIN location AS loc ON "
                        + " stock.LOCATION_ID = loc.LOCATION_ID "
                        + " WHERE material.CATEGORY_ID = "+categoryId+" AND "
                        + " loc.LOCATION_ID = "+locationId;
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                hashtableMaterial.put("MATERIAL_ID", rs.getLong("MATERIAL_ID"));
                hashtableMaterial.put("MATERIAL_NAME", rs.getString("MATERIAL_NAME"));
                hashtableMaterial.put("CATEGORY_ID", rs.getLong("CATEGORY_ID"));
                hashtableMaterial.put("CATEGORY_NAME", rs.getString("CATEGORY_NAME"));
                hashtableMaterial.put("LOCATION_ID", rs.getLong("LOCATION_ID"));
                hashtableMaterial.put("LOCATION_NAME", rs.getString("LOCATION_NAME"));
                hashtableMaterial.put("QTY", rs.getInt("QTY"));
            }
            rs.close();
            return hashtableMaterial;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return  hashtableMaterial;
    }
    
    public static boolean checkOID(long entEmpAssetInventoryId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_EMP_ASSET_INVENTORY + " WHERE "
                    + PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_EMP_ASSET_INVENTORY_ID] + " = " + entEmpAssetInventoryId;
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
            String sql = "SELECT COUNT(" + PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_EMP_ASSET_INVENTORY_ID] + ") FROM " + TBL_EMP_ASSET_INVENTORY;
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
                    EmpAssetInventory entEmpAssetInventory = (EmpAssetInventory) list.get(ls);
                    if (oid == entEmpAssetInventory.getOID()) {
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
