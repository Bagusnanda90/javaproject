/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Dimata 007
 */
import com.dimata.harisma.entity.employee.PstEmployee;
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

public class PstMappingPosition extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_MAPPING_POSITION = "hr_mapping_position";
    public static final int FLD_MAPPING_POSITION_ID = 0;
    public static final int FLD_UP_POSITION_ID = 1;
    public static final int FLD_DOWN_POSITION_ID = 2;
    public static final int FLD_START_DATE = 3;
    public static final int FLD_END_DATE = 4;
    public static final int FLD_TYPE_OF_LINK = 5;
    public static final int FLD_TEMPLATE_ID = 6;

    public static String[] fieldNames = {
        "MAPPING_POSITION_ID",
        "UP_POSITION_ID",
        "DOWN_POSITION_ID",
        "START_DATE",
        "END_DATE",
        "TYPE_OF_LINK",
        "TEMPLATE_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT,
        TYPE_LONG
    };

    /* Type of Link */
    
    public static final int LEAVE_APPROVAL = 3;
    public static final int SCHEDULE_CHANGE_APPROVAL = 4;
    public static final int EO_FORM_APPROVAL = 5;
    public static String[] typeOfLink = {
        "none","Supervisory", "Coordination", "Leave Approval", "Schedule Change Form Approval", "EO Form Approval"
    };
    public PstMappingPosition() {
    }

    public PstMappingPosition(int i) throws DBException {
        super(new PstMappingPosition());
    }

    public PstMappingPosition(String sOid) throws DBException {
        super(new PstMappingPosition(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstMappingPosition(long lOid) throws DBException {
        super(new PstMappingPosition(0));
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
        return TBL_MAPPING_POSITION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstMappingPosition().getClass().getName();
    }

    public static MappingPosition fetchExc(long oid) throws DBException {
        try {
            MappingPosition entMappingPosition = new MappingPosition();
            PstMappingPosition pstMappingPosition = new PstMappingPosition(oid);
            entMappingPosition.setOID(oid);
            entMappingPosition.setUpPositionId(pstMappingPosition.getLong(FLD_UP_POSITION_ID));
            entMappingPosition.setDownPositionId(pstMappingPosition.getLong(FLD_DOWN_POSITION_ID));
            entMappingPosition.setStartDate(pstMappingPosition.getDate(FLD_START_DATE));
            entMappingPosition.setEndDate(pstMappingPosition.getDate(FLD_END_DATE));
            entMappingPosition.setTypeOfLink(pstMappingPosition.getInt(FLD_TYPE_OF_LINK));
            entMappingPosition.setTemplateId(pstMappingPosition.getLong(FLD_TEMPLATE_ID));
            return entMappingPosition;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMappingPosition(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        MappingPosition entMappingPosition = fetchExc(entity.getOID());
        entity = (Entity) entMappingPosition;
        return entMappingPosition.getOID();
    }

    public static synchronized long updateExc(MappingPosition entMappingPosition) throws DBException {
        try {
            if (entMappingPosition.getOID() != 0) {
                PstMappingPosition pstMappingPosition = new PstMappingPosition(entMappingPosition.getOID());
                pstMappingPosition.setLong(FLD_UP_POSITION_ID, entMappingPosition.getUpPositionId());
                pstMappingPosition.setLong(FLD_DOWN_POSITION_ID, entMappingPosition.getDownPositionId());
                pstMappingPosition.setDate(FLD_START_DATE, entMappingPosition.getStartDate());
                pstMappingPosition.setDate(FLD_END_DATE, entMappingPosition.getEndDate());
                pstMappingPosition.setInt(FLD_TYPE_OF_LINK, entMappingPosition.getTypeOfLink());
                pstMappingPosition.setLong(FLD_TEMPLATE_ID, entMappingPosition.getTemplateId());
                pstMappingPosition.update();
                return entMappingPosition.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMappingPosition(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((MappingPosition) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstMappingPosition pstMappingPosition = new PstMappingPosition(oid);
            pstMappingPosition.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMappingPosition(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(MappingPosition entMappingPosition) throws DBException {
        try {
            PstMappingPosition pstMappingPosition = new PstMappingPosition(0);
            pstMappingPosition.setLong(FLD_UP_POSITION_ID, entMappingPosition.getUpPositionId());
            pstMappingPosition.setLong(FLD_DOWN_POSITION_ID, entMappingPosition.getDownPositionId());
            pstMappingPosition.setDate(FLD_START_DATE, entMappingPosition.getStartDate());
            pstMappingPosition.setDate(FLD_END_DATE, entMappingPosition.getEndDate());
            pstMappingPosition.setInt(FLD_TYPE_OF_LINK, entMappingPosition.getTypeOfLink());
            pstMappingPosition.setLong(FLD_TEMPLATE_ID, entMappingPosition.getTemplateId());
            pstMappingPosition.insert();
            entMappingPosition.setOID(pstMappingPosition.getLong(FLD_MAPPING_POSITION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMappingPosition(0), DBException.UNKNOWN);
        }
        return entMappingPosition.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((MappingPosition) entity);
    }

    public static void resultToObject(ResultSet rs, MappingPosition entMappingPosition) {
        try {
            entMappingPosition.setOID(rs.getLong(PstMappingPosition.fieldNames[PstMappingPosition.FLD_MAPPING_POSITION_ID]));
            entMappingPosition.setUpPositionId(rs.getLong(PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID]));
            entMappingPosition.setDownPositionId(rs.getLong(PstMappingPosition.fieldNames[PstMappingPosition.FLD_DOWN_POSITION_ID]));
            entMappingPosition.setStartDate(rs.getDate(PstMappingPosition.fieldNames[PstMappingPosition.FLD_START_DATE]));
            entMappingPosition.setEndDate(rs.getDate(PstMappingPosition.fieldNames[PstMappingPosition.FLD_END_DATE]));
            entMappingPosition.setTypeOfLink(rs.getInt(PstMappingPosition.fieldNames[PstMappingPosition.FLD_TYPE_OF_LINK]));
            entMappingPosition.setTemplateId(rs.getLong(PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]));
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
            String sql = "SELECT * FROM " + TBL_MAPPING_POSITION;
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
                MappingPosition entMappingPosition = new MappingPosition();
                resultToObject(rs, entMappingPosition);
                lists.add(entMappingPosition);
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

    public static boolean checkOID(long entMappingPositionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_MAPPING_POSITION + " WHERE "
                    + PstMappingPosition.fieldNames[PstMappingPosition.FLD_MAPPING_POSITION_ID] + " = " + entMappingPositionId;
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
            String sql = "SELECT COUNT(" + PstMappingPosition.fieldNames[PstMappingPosition.FLD_MAPPING_POSITION_ID] + ") FROM " + TBL_MAPPING_POSITION;
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

    public static String listEmployeeTopPositionId(int limitStart, int recordToGet, String whereClause, String order) {
        String listEmployeeTopPositionId = "";
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT HMP.* " +
                    " FROM " + TBL_MAPPING_POSITION + " HMP " +
                    "INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" HE ON HE."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] +" = HMP."+PstMappingPosition.fieldNames[PstMappingPosition.FLD_DOWN_POSITION_ID];
                  //  " WHERE HE."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = 101191 AND HMP.`TYPE_OF_TOP_LINK` = 3"
                    
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
                TopPosition entTopPosition = new TopPosition();
                String topLinkId = (rs.getString("HMP."+PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID]));
                listEmployeeTopPositionId = listEmployeeTopPositionId + topLinkId +",";
                //lists.add(entTopPosition);
            }
            rs.close();
            return listEmployeeTopPositionId;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return  listEmployeeTopPositionId;
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
                    MappingPosition entMappingPosition = (MappingPosition) list.get(ls);
                    if (oid == entMappingPosition.getOID()) {
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