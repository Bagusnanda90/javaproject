/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;
import com.dimata.harisma.session.employee.AssetLocation;
import com.dimata.harisma.entity.masterdata.AssetInventory;
import com.dimata.harisma.session.employee.AssetMaterial;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;


/**
 *
 * @author Gunadi
 */
public class PstEmpAssetInventoryItem extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_EMP_ASSET_INVENTORY_ITEM = "hr_emp_asset_inventory_item";
    public static final int FLD_EMP_ASSET_INVENTORY_ITEM_ID = 0;
    public static final int FLD_EMP_ASSET_INVENTORY_ID = 1;
    public static final int FLD_MATERIAL_ID = 2;
    public static final int FLD_QTY = 3;
    public static final int FLD_DETAIL = 4;
    public static final int FLD_PURPOSE = 5;
    public static final int FLD_LOCATION_ID = 6;
    public static String[] fieldNames = {
        "EMP_ASSET_INVENTORY_ITEM_ID",
        "EMP_ASSET_INVENTORY_ID",
        "MATERIAL_ID",
        "QTY",
        "DETAIL",
        "PURPOSE",
        "LOCATION_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG
    };

    public PstEmpAssetInventoryItem() {
    }

    public PstEmpAssetInventoryItem(int i) throws DBException {
        super(new PstEmpAssetInventoryItem());
    }

    public PstEmpAssetInventoryItem(String sOid) throws DBException {
        super(new PstEmpAssetInventoryItem(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpAssetInventoryItem(long lOid) throws DBException {
        super(new PstEmpAssetInventoryItem(0));
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
        return TBL_EMP_ASSET_INVENTORY_ITEM;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpAssetInventoryItem().getClass().getName();
    }

    public static EmpAssetInventoryItem fetchExc(long oid) throws DBException {
        try {
            EmpAssetInventoryItem entEmpAssetInventoryItem = new EmpAssetInventoryItem();
            PstEmpAssetInventoryItem pstEmpAssetInventoryItem = new PstEmpAssetInventoryItem(oid);
            entEmpAssetInventoryItem.setOID(oid);
            entEmpAssetInventoryItem.setEmpAssetInventoryId(pstEmpAssetInventoryItem.getLong(FLD_EMP_ASSET_INVENTORY_ID));
            entEmpAssetInventoryItem.setMaterialId(pstEmpAssetInventoryItem.getLong(FLD_MATERIAL_ID));
            entEmpAssetInventoryItem.setQty(pstEmpAssetInventoryItem.getInt(FLD_QTY));
            entEmpAssetInventoryItem.setDetail(pstEmpAssetInventoryItem.getString(FLD_DETAIL));
            entEmpAssetInventoryItem.setPurpose(pstEmpAssetInventoryItem.getString(FLD_PURPOSE));
            entEmpAssetInventoryItem.setLocationId(pstEmpAssetInventoryItem.getLong(FLD_LOCATION_ID));
            return entEmpAssetInventoryItem;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAssetInventoryItem(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        EmpAssetInventoryItem entEmpAssetInventoryItem = fetchExc(entity.getOID());
        entity = (Entity) entEmpAssetInventoryItem;
        return entEmpAssetInventoryItem.getOID();
    }

    public static synchronized long updateExc(EmpAssetInventoryItem entEmpAssetInventoryItem) throws DBException {
        try {
            if (entEmpAssetInventoryItem.getOID() != 0) {
                PstEmpAssetInventoryItem pstEmpAssetInventoryItem = new PstEmpAssetInventoryItem(entEmpAssetInventoryItem.getOID());
                pstEmpAssetInventoryItem.setLong(FLD_EMP_ASSET_INVENTORY_ID, entEmpAssetInventoryItem.getEmpAssetInventoryId());
                pstEmpAssetInventoryItem.setLong(FLD_MATERIAL_ID, entEmpAssetInventoryItem.getMaterialId());
                pstEmpAssetInventoryItem.setInt(FLD_QTY, entEmpAssetInventoryItem.getQty());
                pstEmpAssetInventoryItem.setString(FLD_DETAIL, entEmpAssetInventoryItem.getDetail());
                pstEmpAssetInventoryItem.setString(FLD_PURPOSE, entEmpAssetInventoryItem.getPurpose());
                pstEmpAssetInventoryItem.setLong(FLD_LOCATION_ID, entEmpAssetInventoryItem.getLocationId());
                pstEmpAssetInventoryItem.update();
                return entEmpAssetInventoryItem.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAssetInventoryItem(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((EmpAssetInventoryItem) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstEmpAssetInventoryItem pstEmpAssetInventoryItem = new PstEmpAssetInventoryItem(oid);
            pstEmpAssetInventoryItem.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAssetInventoryItem(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(EmpAssetInventoryItem entEmpAssetInventoryItem) throws DBException {
        try {
            PstEmpAssetInventoryItem pstEmpAssetInventoryItem = new PstEmpAssetInventoryItem(0);
            pstEmpAssetInventoryItem.setLong(FLD_EMP_ASSET_INVENTORY_ID, entEmpAssetInventoryItem.getEmpAssetInventoryId());
            pstEmpAssetInventoryItem.setLong(FLD_MATERIAL_ID, entEmpAssetInventoryItem.getMaterialId());
            pstEmpAssetInventoryItem.setInt(FLD_QTY, entEmpAssetInventoryItem.getQty());
            pstEmpAssetInventoryItem.setString(FLD_DETAIL, entEmpAssetInventoryItem.getDetail());
            pstEmpAssetInventoryItem.setString(FLD_PURPOSE, entEmpAssetInventoryItem.getPurpose());
            pstEmpAssetInventoryItem.setLong(FLD_LOCATION_ID, entEmpAssetInventoryItem.getLocationId());
            pstEmpAssetInventoryItem.insert();
            entEmpAssetInventoryItem.setOID(pstEmpAssetInventoryItem.getLong(FLD_EMP_ASSET_INVENTORY_ITEM_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpAssetInventoryItem(0), DBException.UNKNOWN);
        }
        return entEmpAssetInventoryItem.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((EmpAssetInventoryItem) entity);
    }

    public static void resultToObject(ResultSet rs, EmpAssetInventoryItem entEmpAssetInventoryItem) {
        try {
            entEmpAssetInventoryItem.setOID(rs.getLong(PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ITEM_ID]));
            entEmpAssetInventoryItem.setEmpAssetInventoryId(rs.getLong(PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ID]));
            entEmpAssetInventoryItem.setMaterialId(rs.getLong(PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_MATERIAL_ID]));
            entEmpAssetInventoryItem.setQty(rs.getInt(PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_QTY]));
            entEmpAssetInventoryItem.setDetail(rs.getString(PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_DETAIL]));
            entEmpAssetInventoryItem.setPurpose(rs.getString(PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_PURPOSE]));
            entEmpAssetInventoryItem.setLocationId(rs.getLong(PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_LOCATION_ID]));
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
            String sql = "SELECT * FROM " + TBL_EMP_ASSET_INVENTORY_ITEM;
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
                EmpAssetInventoryItem entEmpAssetInventoryItem = new EmpAssetInventoryItem();
                resultToObject(rs, entEmpAssetInventoryItem);
                lists.add(entEmpAssetInventoryItem);
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
    
    public static Vector listEmpAsset(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT item.* FROM " + TBL_EMP_ASSET_INVENTORY_ITEM + " AS item INNER JOIN " + PstEmpAssetInventory.TBL_EMP_ASSET_INVENTORY +""
                    + " asset ON item." + PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_EMP_ASSET_INVENTORY_ID] + " = asset."
                    + "" + PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ID] ;
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
                EmpAssetInventoryItem entEmpAssetInventoryItem = new EmpAssetInventoryItem();
                resultToObject(rs, entEmpAssetInventoryItem);
                lists.add(entEmpAssetInventoryItem);
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
    
    // list Location
    public static Hashtable<String,AssetLocation> listMapLocation(int limitStart,int recordToGet, String whereClause, String order){
        String dbPos = String.valueOf(PstSystemProperty.getValueByNameWithStringNull("PROCHAIN_DB_NAME"));
        Hashtable<String,AssetLocation> lists = new Hashtable(); 
        DBResultSet dbrs = null;
        try {
                String sql = "SELECT * FROM "+dbPos+".location"; 
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
                        AssetLocation location = new AssetLocation();
                        location.setLocationId(rs.getLong("LOCATION_ID"));
                        location.setName(rs.getString("NAME"));
                        lists.put(""+location.getLocationId(), location);
                }
                rs.close();
                return lists;

        }catch(Exception e) {
                System.out.println(e);
        }finally {
                DBResultSet.close(dbrs);
        }
                return new Hashtable();
    }
    
    // list Material
    public static Hashtable<String,AssetMaterial> listMapMaterial(int limitStart,int recordToGet, String whereClause, String order){
        String dbPos = String.valueOf(PstSystemProperty.getValueByNameWithStringNull("PROCHAIN_DB_NAME"));
        
        Hashtable<String,AssetMaterial> lists = new Hashtable(); 
        DBResultSet dbrs = null;
        try {
                String sql = "SELECT material.*, stock.QTY FROM "+dbPos+".pos_material AS material INNER JOIN "+dbPos+".pos_material_stock AS stock ON"
                        + " material.MATERIAL_ID = stock.MATERIAL_UNIT_ID"; 
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
                        AssetMaterial material = new AssetMaterial();
                        material.setMaterialId(rs.getLong("MATERIAL_ID"));
                        material.setName(rs.getString("NAME"));
                        material.setQty(rs.getInt("QTY"));
                        lists.put(""+material.getMaterialId(), material);
                }
                rs.close();
                return lists;

        }catch(Exception e) {
                System.out.println(e);
        }finally {
                DBResultSet.close(dbrs);
        }
                return new Hashtable();
    }

    public static boolean checkOID(long entEmpAssetInventoryItemId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_EMP_ASSET_INVENTORY_ITEM + " WHERE "
                    + PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ITEM_ID] + " = " + entEmpAssetInventoryItemId;
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
            String sql = "SELECT COUNT(" + PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ITEM_ID] + ") FROM " + TBL_EMP_ASSET_INVENTORY_ITEM;
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
    
    public static int getCountEmpAsset(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(item."+PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ITEM_ID]+" FROM " + TBL_EMP_ASSET_INVENTORY_ITEM + " AS item INNER JOIN " + PstEmpAssetInventory.TBL_EMP_ASSET_INVENTORY +""
                    + " asset ON item." + PstEmpAssetInventory.fieldNames[PstEmpAssetInventory.FLD_EMP_ASSET_INVENTORY_ID] + " = asset."
                    + "" + PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ID] ;
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
                    EmpAssetInventoryItem entEmpAssetInventoryItem = (EmpAssetInventoryItem) list.get(ls);
                    if (oid == entEmpAssetInventoryItem.getOID()) {
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
