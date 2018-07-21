/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.harisma.entity.masterdata.EmpCategory;
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
public class PstPaySimulation extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_PAY_SIMULATION = "pay_simulation";
    public static final int FLD_PAY_SIMULATION_ID = 0;
    public static final int FLD_TITLE = 1;
    public static final int FLD_OBJECTIVES = 2;
    public static final int FLD_CREATED_DATE = 3;
    public static final int FLD_CREATED_BY_ID = 4;
    public static final int FLD_REQUEST_DATE = 5;
    public static final int FLD_REQUEST_BY_ID = 6;
    public static final int FLD_DUE_DATE = 7;
    public static final int FLD_STATUS_DOC = 8;
    public static final int FLD_MAX_TOTAL_BUDGET = 9;
    public static final int FLD_MAX_ADD_EMPL = 10;
    public static final int FLD_SOURCE_PAY_PERIOD_ID = 11;

    public static String[] fieldNames = {
        "PAY_SIMULATION_ID",
        "TITLE",
        "OBJECTIVES",
        "CREATED_DATE",
        "CREATED_BY_ID",
        "REQUEST_DATE",
        "REQUEST_BY_ID",
        "DUE_DATE",
        "STATUS_DOC",
        "MAX_TOTAL_BUDGET",
        "MAX_ADD_EMPL",
        "SOURCE_PAY_PERIOD_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_LONG
    };

    public PstPaySimulation() {
    }

    public PstPaySimulation(int i) throws DBException {
        super(new PstPaySimulation());
    }

    public PstPaySimulation(String sOid) throws DBException {
        super(new PstPaySimulation(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPaySimulation(long lOid) throws DBException {
        super(new PstPaySimulation(0));
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
        return TBL_PAY_SIMULATION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPaySimulation().getClass().getName();
    }

    public static PaySimulation fetchExc(long oid) throws DBException {
        try {
            PaySimulation entPaySimulation = new PaySimulation();
            PstPaySimulation pstPaySimulation = new PstPaySimulation(oid);
            entPaySimulation.setOID(oid);
            entPaySimulation.setCreadedById(pstPaySimulation.getLong(FLD_CREATED_BY_ID));
            entPaySimulation.setCreatedDate(pstPaySimulation.getDate(FLD_CREATED_DATE));
            entPaySimulation.setDueDate(pstPaySimulation.getDate(FLD_DUE_DATE));
            entPaySimulation.setMaxAddEmployee(pstPaySimulation.getInt(FLD_MAX_ADD_EMPL));
            entPaySimulation.setMaxTotalBudget(pstPaySimulation.getdouble(FLD_MAX_TOTAL_BUDGET));
            entPaySimulation.setObjectives(pstPaySimulation.getString(FLD_OBJECTIVES));
            entPaySimulation.setRequestDate(pstPaySimulation.getDate(FLD_REQUEST_DATE));
            entPaySimulation.setRequestedById(pstPaySimulation.getLong(FLD_REQUEST_BY_ID));
            entPaySimulation.setSourcePayPeriodId(pstPaySimulation.getLong(FLD_SOURCE_PAY_PERIOD_ID));
            entPaySimulation.setStatusDoc(pstPaySimulation.getInt(FLD_STATUS_DOC));
            entPaySimulation.setTitle(pstPaySimulation.getString(FLD_TITLE));

            String where = PstPaySimulationFilter.fieldNames[PstPaySimulationFilter.FLD_PAY_SIMULATION_ID] + "=" + oid + " AND "
                    + PstPaySimulationFilter.fieldNames[PstPaySimulationFilter.FLD_FILTER_NAME] + "=\"" + PaySimulationFilter.FILTER_EMP_CATEGORY + "\"";
            Vector listEmpCategoryId = PstPaySimulationFilter.list(0, 0, where, "");
            if (listEmpCategoryId != null && listEmpCategoryId.size() > 0) {
                Vector empCatId = new Vector();
                for (int idx = 0; idx < listEmpCategoryId.size(); idx++) {
                    try {
                        PaySimulationFilter simFilter = (PaySimulationFilter) listEmpCategoryId.get(idx);
                        empCatId.add(new Long(simFilter.getFilterValue()));
                    } catch (Exception exc) {
                    }
                }
                entPaySimulation.setEmployeeCategoryIds(empCatId);
            }

            where = PstPaySimulationFilter.fieldNames[PstPaySimulationFilter.FLD_PAY_SIMULATION_ID] + "=" + oid + " AND "
                    + PstPaySimulationFilter.fieldNames[PstPaySimulationFilter.FLD_FILTER_NAME] + "=\"" + PaySimulationFilter.FILTER_PAY_COMPONENT + "\"";
            Vector listPayCode = PstPaySimulationFilter.list(0, 0, where, "");
            if (listPayCode != null && listPayCode.size() > 0) {
                Vector payCodes = new Vector();
                for (int idx = 0; idx < listPayCode.size(); idx++) {
                    try {
                        PaySimulationFilter simFilter = (PaySimulationFilter) listPayCode.get(idx);
                        payCodes.add(new String(simFilter.getFilterValue()));
                    } catch (Exception exc) {
                    }
                }
                entPaySimulation.setPayrollComponents(payCodes);
            }

            return entPaySimulation;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySimulation(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PaySimulation entPaySimulation = fetchExc(entity.getOID());
        entity = (Entity) entPaySimulation;
        return entPaySimulation.getOID();
    }

    public static synchronized long updateExc(PaySimulation entPaySimulation) throws DBException {
        try {
            if (entPaySimulation.getOID() != 0) {
                PstPaySimulation pstPaySimulation = new PstPaySimulation(entPaySimulation.getOID());
                pstPaySimulation.setLong(FLD_CREATED_BY_ID, entPaySimulation.getCreadedById());
                pstPaySimulation.setDate(FLD_CREATED_DATE, entPaySimulation.getCreatedDate());
                pstPaySimulation.setDate(FLD_DUE_DATE, entPaySimulation.getDueDate());
                pstPaySimulation.setInt(FLD_MAX_ADD_EMPL, entPaySimulation.getMaxAddEmployee());
                pstPaySimulation.setDouble(FLD_MAX_TOTAL_BUDGET, entPaySimulation.getMaxTotalBudget());
                pstPaySimulation.setString(FLD_OBJECTIVES, entPaySimulation.getObjectives());
                pstPaySimulation.setDate(FLD_REQUEST_DATE, entPaySimulation.getRequestDate());
                pstPaySimulation.setLong(FLD_REQUEST_BY_ID, entPaySimulation.getRequestedById());
                pstPaySimulation.setLong(FLD_SOURCE_PAY_PERIOD_ID, entPaySimulation.getSourcePayPeriodId());
                pstPaySimulation.setInt(FLD_STATUS_DOC, entPaySimulation.getStatusDoc());
                pstPaySimulation.setString(FLD_TITLE, entPaySimulation.getTitle());
                pstPaySimulation.update();
                
                PstPaySimulationFilter.deleteBy(entPaySimulation.getOID(), PaySimulationFilter.FILTER_EMP_CATEGORY);
                        if (entPaySimulation.getEmployeeCategoryIds() != null && entPaySimulation.getEmployeeCategoryIds().size() > 0) {
                    for (int idx = 0; idx < entPaySimulation.getEmployeeCategoryIds().size(); idx++) {
                        Long empCatID = entPaySimulation.getEmployeeCategoryIds().get(idx);
                        PaySimulationFilter simFilter = new PaySimulationFilter();
                        simFilter.setFilterName(PaySimulationFilter.FILTER_EMP_CATEGORY);
                        simFilter.setFilterType(TYPE_LONG);
                        simFilter.setFilterValue("" + empCatID);
                        simFilter.setPaySimulationId(entPaySimulation.getOID());
                        PstPaySimulationFilter.insertExc(simFilter);
                    }
                }
                        
                PstPaySimulationFilter.deleteBy(entPaySimulation.getOID(), PaySimulationFilter.FILTER_PAY_COMPONENT);        
                if (entPaySimulation.getPayrollComponents() != null && entPaySimulation.getPayrollComponents().size() > 0) {
                    for (int idx = 0; idx < entPaySimulation.getPayrollComponents().size(); idx++) {
                        String payComp = entPaySimulation.getPayrollComponents().get(idx);
                        PaySimulationFilter simFilter = new PaySimulationFilter();
                        simFilter.setFilterName(PaySimulationFilter.FILTER_PAY_COMPONENT);
                        simFilter.setFilterType(TYPE_STRING);
                        simFilter.setFilterValue("" + payComp);
                        simFilter.setPaySimulationId(entPaySimulation.getOID());
                        PstPaySimulationFilter.insertExc(simFilter);
                    }
                }
                return entPaySimulation.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySimulation(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((PaySimulation) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstPaySimulation pstPaySimulation = new PstPaySimulation(oid);
            PstPaySimulationFilter.deleteBy(oid, null);
            pstPaySimulation.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySimulation(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(PaySimulation entPaySimulation) throws DBException {
        try {
            PstPaySimulation pstPaySimulation = new PstPaySimulation(0);
            pstPaySimulation.setLong(FLD_CREATED_BY_ID, entPaySimulation.getCreadedById());
            pstPaySimulation.setDate(FLD_CREATED_DATE, entPaySimulation.getCreatedDate());
            pstPaySimulation.setDate(FLD_DUE_DATE, entPaySimulation.getDueDate());
            pstPaySimulation.setInt(FLD_MAX_ADD_EMPL, entPaySimulation.getMaxAddEmployee());
            pstPaySimulation.setDouble(FLD_MAX_TOTAL_BUDGET, entPaySimulation.getMaxTotalBudget());
            pstPaySimulation.setString(FLD_OBJECTIVES, entPaySimulation.getObjectives());
            pstPaySimulation.setDate(FLD_REQUEST_DATE, entPaySimulation.getRequestDate());
            pstPaySimulation.setLong(FLD_REQUEST_BY_ID, entPaySimulation.getRequestedById());
            pstPaySimulation.setLong(FLD_SOURCE_PAY_PERIOD_ID, entPaySimulation.getSourcePayPeriodId());
            pstPaySimulation.setInt(FLD_STATUS_DOC, entPaySimulation.getStatusDoc());
            pstPaySimulation.setString(FLD_TITLE, entPaySimulation.getTitle());
            pstPaySimulation.insert();
            entPaySimulation.setOID(pstPaySimulation.getLong(FLD_PAY_SIMULATION_ID));
            if (entPaySimulation.getEmployeeCategoryIds() != null && entPaySimulation.getEmployeeCategoryIds().size() > 0) {
                for (int idx = 0; idx < entPaySimulation.getEmployeeCategoryIds().size(); idx++) {
                    Long empCatID = entPaySimulation.getEmployeeCategoryIds().get(idx);
                    PaySimulationFilter simFilter = new PaySimulationFilter();
                    simFilter.setFilterName(PaySimulationFilter.FILTER_EMP_CATEGORY);
                    simFilter.setFilterType(TYPE_LONG);
                    simFilter.setFilterValue("" + empCatID);
                    simFilter.setPaySimulationId(entPaySimulation.getOID());
                    PstPaySimulationFilter.insertExc(simFilter);
                }
            }
            if (entPaySimulation.getPayrollComponents() != null && entPaySimulation.getPayrollComponents().size() > 0) {
                for (int idx = 0; idx < entPaySimulation.getPayrollComponents().size(); idx++) {
                    String payComp = entPaySimulation.getPayrollComponents().get(idx);
                    PaySimulationFilter simFilter = new PaySimulationFilter();
                    simFilter.setFilterName(PaySimulationFilter.FILTER_PAY_COMPONENT);
                    simFilter.setFilterType(TYPE_STRING);
                    simFilter.setFilterValue("" + payComp);
                    simFilter.setPaySimulationId(entPaySimulation.getOID());
                    PstPaySimulationFilter.insertExc(simFilter);
                }
            }

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySimulation(0), DBException.UNKNOWN);
        }
        return entPaySimulation.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((PaySimulation) entity);
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_SIMULATION;
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
                PaySimulation benefitMaster = new PaySimulation();
                resultToObject(rs, benefitMaster);
                lists.add(benefitMaster);
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

    public static void resultToObject(ResultSet rs, PaySimulation entPaySimulation) {
        try {
            entPaySimulation.setOID(rs.getLong(fieldNames[FLD_PAY_SIMULATION_ID]));
            entPaySimulation.setCreadedById(rs.getLong(fieldNames[FLD_CREATED_BY_ID]));
            entPaySimulation.setCreatedDate(rs.getDate(fieldNames[FLD_CREATED_DATE]));
            entPaySimulation.setDueDate(rs.getDate(fieldNames[FLD_DUE_DATE]));
            entPaySimulation.setMaxAddEmployee(rs.getInt(fieldNames[FLD_MAX_ADD_EMPL]));
            entPaySimulation.setMaxTotalBudget(rs.getDouble(fieldNames[FLD_MAX_TOTAL_BUDGET]));
            entPaySimulation.setObjectives(rs.getString(fieldNames[FLD_OBJECTIVES]));
            entPaySimulation.setRequestDate(rs.getDate(fieldNames[FLD_REQUEST_DATE]));
            entPaySimulation.setRequestedById(rs.getLong(fieldNames[FLD_REQUEST_BY_ID]));
            entPaySimulation.setSourcePayPeriodId(rs.getLong(fieldNames[FLD_SOURCE_PAY_PERIOD_ID]));
            entPaySimulation.setStatusDoc(rs.getInt(fieldNames[FLD_STATUS_DOC]));
            entPaySimulation.setTitle(rs.getString(fieldNames[FLD_TITLE]));
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long entPaySimulationId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_SIMULATION + " WHERE "
                    + PstPaySimulation.fieldNames[PstPaySimulation.FLD_PAY_SIMULATION_ID] + " = " + entPaySimulationId;

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
            String sql = "SELECT COUNT(" + PstPaySimulation.fieldNames[PstPaySimulation.FLD_PAY_SIMULATION_ID] + ") FROM " + TBL_PAY_SIMULATION;
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
                    PaySimulation entPaySimulation = (PaySimulation) list.get(ls);
                    if (oid == entPaySimulation.getOID()) {
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
