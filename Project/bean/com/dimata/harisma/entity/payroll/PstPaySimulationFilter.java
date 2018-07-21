/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

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
 * @author Hendra McHen | 20150210
 */
public class PstPaySimulationFilter extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_PAY_SIMULATION_FILTER = "pay_simulation_filter";
    public static final int FLD_SIMULATION_FILTER_ID = 0;
    public static final int FLD_PAY_SIMULATION_ID = 1;    
    public static final int FLD_FILTER_NAME = 2;
    public static final int FLD_FILTER_TYPE = 3;
    public static final int FLD_FILTER_VALUE= 4;

    public static String[] fieldNames = {
        "SIMULATION_FILTER_ID", //0
        "PAY_SIMULATION_ID",
        "FILTER_NAME",
        "FILTER_TYPE",
        "FILTER_VALUE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID, //0
        TYPE_LONG + TYPE_FK,
        TYPE_STRING ,
        TYPE_INT,
        TYPE_STRING 
    };

    public PstPaySimulationFilter() {
    }

    public PstPaySimulationFilter(int i) throws DBException {
        super(new PstPaySimulationFilter());
    }

    public PstPaySimulationFilter(String sOid) throws DBException {
        super(new PstPaySimulationFilter(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPaySimulationFilter(long lOid) throws DBException {
        super(new PstPaySimulationFilter(0));
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
        return TBL_PAY_SIMULATION_FILTER;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPaySimulationFilter().getClass().getName();
    }

    public static PaySimulationFilter fetchExc(long oid) throws DBException {
        try {
            PaySimulationFilter entPaySimulationFilter = new PaySimulationFilter();
            PstPaySimulationFilter pstPaySimulationStruct = new PstPaySimulationFilter(oid);
            entPaySimulationFilter.setOID(oid);
            entPaySimulationFilter.setPaySimulationId(pstPaySimulationStruct.getLong(FLD_PAY_SIMULATION_ID));
            entPaySimulationFilter.setFilterValue(pstPaySimulationStruct.getString(FLD_FILTER_VALUE));
            entPaySimulationFilter.setFilterName(pstPaySimulationStruct.getString(FLD_FILTER_NAME));
            entPaySimulationFilter.setFilterType(pstPaySimulationStruct.getInt(FLD_FILTER_TYPE));
            return entPaySimulationFilter;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySimulationFilter(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PaySimulationFilter entPaySimulationFilter = fetchExc(entity.getOID());
        entity = (Entity) entPaySimulationFilter;
        return entPaySimulationFilter.getOID();
    }

    public static synchronized long updateExc(PaySimulationFilter entPaySimulationFilter) throws DBException {
        try {
            if (entPaySimulationFilter.getOID() != 0) {
                PstPaySimulationFilter pstPaySimulationStruct = new PstPaySimulationFilter(entPaySimulationFilter.getOID());
                pstPaySimulationStruct.setLong(FLD_PAY_SIMULATION_ID, entPaySimulationFilter.getPaySimulationId());
                pstPaySimulationStruct.setString(FLD_FILTER_VALUE, entPaySimulationFilter.getFilterValue());
                pstPaySimulationStruct.setString(FLD_FILTER_NAME, entPaySimulationFilter.getFilterName());
                pstPaySimulationStruct.setInt(FLD_FILTER_TYPE, entPaySimulationFilter.getFilterType());
                pstPaySimulationStruct.update();
                return entPaySimulationFilter.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySimulationFilter(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((PaySimulationFilter) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstPaySimulationFilter pstPaySimulationFilter = new PstPaySimulationFilter(oid);
            pstPaySimulationFilter.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySimulationFilter(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public static long deleteBy(long paySimulationId, String filterName) {
        try {
            String sql = " DELETE FROM " + TBL_PAY_SIMULATION_FILTER
                    + " WHERE " + fieldNames[FLD_PAY_SIMULATION_ID]
                    + " = " + paySimulationId + ( filterName!=null && filterName.length()>0 ? (" AND " +  fieldNames[FLD_FILTER_NAME]+"=\""+ filterName +"\"") :"");
            int status = DBHandler.execUpdate(sql);
        } catch (Exception exc) {
            System.out.println("error deleteBy " + exc.toString());
        }

        return paySimulationId;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(PaySimulationFilter entPaySimulationFilter) throws DBException {
        try {
            PstPaySimulationFilter pstPaySimulationStruct = new PstPaySimulationFilter(0);
            pstPaySimulationStruct.setLong(FLD_PAY_SIMULATION_ID, entPaySimulationFilter.getPaySimulationId());
            pstPaySimulationStruct.setString(FLD_FILTER_VALUE, entPaySimulationFilter.getFilterValue());
            pstPaySimulationStruct.setString(FLD_FILTER_NAME, entPaySimulationFilter.getFilterName());
            pstPaySimulationStruct.setInt(FLD_FILTER_TYPE, entPaySimulationFilter.getFilterType());
            pstPaySimulationStruct.insert();
            entPaySimulationFilter.setOID(pstPaySimulationStruct.getLong(FLD_SIMULATION_FILTER_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySimulationFilter(0), DBException.UNKNOWN);
        }
        return entPaySimulationFilter.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((PaySimulationFilter) entity);
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_SIMULATION_FILTER;
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
                PaySimulationFilter paySimulationStructure = new PaySimulationFilter();
                resultToObject(rs, paySimulationStructure);
                lists.add(paySimulationStructure);
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

    public static void resultToObject(ResultSet rs, PaySimulationFilter entPaySimulationFilter) {
        try {
            entPaySimulationFilter.setOID(rs.getLong(fieldNames[FLD_SIMULATION_FILTER_ID]));
            entPaySimulationFilter.setPaySimulationId(rs.getLong(fieldNames[FLD_PAY_SIMULATION_ID]));
            entPaySimulationFilter.setFilterValue(rs.getString(fieldNames[FLD_FILTER_VALUE]));
            entPaySimulationFilter.setFilterName(rs.getString(fieldNames[FLD_FILTER_NAME]));
            entPaySimulationFilter.setFilterType(rs.getInt(fieldNames[FLD_FILTER_TYPE]));
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long entPaySimulationFilterId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_SIMULATION_FILTER + " WHERE "
                    + PstPaySimulationFilter.fieldNames[PstPaySimulationFilter.FLD_SIMULATION_FILTER_ID] + " = " + entPaySimulationFilterId;

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
            String sql = "SELECT COUNT(" + PstPaySimulationFilter.fieldNames[PstPaySimulationFilter.FLD_SIMULATION_FILTER_ID] + ") FROM " + TBL_PAY_SIMULATION_FILTER;
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
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    PaySimulationFilter entPaySimulationFilter = (PaySimulationFilter) list.get(ls);
                    if (oid == entPaySimulationFilter.getOID()) {
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
    /* This method used to find command where current data */

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
