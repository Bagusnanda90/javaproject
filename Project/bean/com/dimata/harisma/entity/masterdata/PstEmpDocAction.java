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

/**
 *
 * @author GUSWIK
 */
public class PstEmpDocAction extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_EMP_DOC_ACTION = "hr_emp_doc_action";
   public static final int FLD_EMP_DOC_ACTION_ID = 0;
   public static final int FLD_ACTION_NAME = 1;
   public static final int FLD_ACTION_TITLE = 2;
   public static final int FLD_EMP_DOC_ID = 3;
   
    public static final String[] fieldNames = {
      "EMP_DOC_ACTION_ID",
      "ACTION_NAME",
      "ACTION_TITLE",
      "EMP_DOC_ID"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG
    };

   public PstEmpDocAction() {
   }

    public PstEmpDocAction(int i) throws DBException {
        super(new PstEmpDocAction());
    }

    public PstEmpDocAction(String sOid) throws DBException {
        super(new PstEmpDocAction(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpDocAction(long lOid) throws DBException {
        super(new PstEmpDocAction(0));
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
        return TBL_HR_EMP_DOC_ACTION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpDocAction().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        EmpDocAction docMaster = fetchExc(ent.getOID());
        ent = (Entity) docMaster;
        return docMaster.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((EmpDocAction) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((EmpDocAction) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static EmpDocAction fetchExc(long oid) throws DBException {
        try {
            EmpDocAction empDocAction = new EmpDocAction();
            PstEmpDocAction pstEmpDocAction = new PstEmpDocAction(oid);
            empDocAction.setOID(oid);

            empDocAction.setEmpDocId(pstEmpDocAction.getLong(FLD_EMP_DOC_ID));         
            empDocAction.setActionName(pstEmpDocAction.getString(FLD_ACTION_NAME));
            empDocAction.setActionTitle(pstEmpDocAction.getString(FLD_ACTION_TITLE));

            return empDocAction;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocAction(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(EmpDocAction empDocAction) throws DBException {
        try {
            PstEmpDocAction pstEmpDocAction = new PstEmpDocAction(0);

            pstEmpDocAction.setLong(FLD_EMP_DOC_ID, empDocAction.getEmpDocId());
            pstEmpDocAction.setString(FLD_ACTION_NAME, empDocAction.getActionName());
            pstEmpDocAction.setString(FLD_ACTION_TITLE, empDocAction.getActionTitle());
          
            pstEmpDocAction.insert();
            empDocAction.setOID(pstEmpDocAction.getlong(FLD_EMP_DOC_ACTION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocAction(0), DBException.UNKNOWN);
        }
        return empDocAction.getOID();
    }

    public static long updateExc(EmpDocAction empDocAction) throws DBException {
        try {
            if (empDocAction.getOID() != 0) {
                PstEmpDocAction pstEmpDocAction = new PstEmpDocAction(empDocAction.getOID());

                pstEmpDocAction.setLong(FLD_EMP_DOC_ID, empDocAction.getEmpDocId());
                pstEmpDocAction.setString(FLD_ACTION_NAME, empDocAction.getActionName());
                pstEmpDocAction.setString(FLD_ACTION_TITLE, empDocAction.getActionTitle());

                pstEmpDocAction.update();
                return empDocAction.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocAction(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmpDocAction pstEmpDocAction = new PstEmpDocAction(oid);
            pstEmpDocAction.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocAction(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_ACTION;
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
                EmpDocAction docMaster = new EmpDocAction();
                resultToObject(rs, docMaster);
                lists.add(docMaster);
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
    
      public static void resultToObject(ResultSet rs, EmpDocAction empDocAction) {
        try {
               empDocAction.setOID(rs.getLong(PstEmpDocAction.fieldNames[PstEmpDocAction.FLD_EMP_DOC_ACTION_ID]));
               empDocAction.setEmpDocId(rs.getLong(PstEmpDocAction.fieldNames[PstEmpDocAction.FLD_EMP_DOC_ID]));
               empDocAction.setActionName(rs.getString(PstEmpDocAction.fieldNames[PstEmpDocAction.FLD_ACTION_NAME]));
               empDocAction.setActionTitle(rs.getString(PstEmpDocAction.fieldNames[PstEmpDocAction.FLD_ACTION_TITLE]));
            
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long docMasterId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_ACTION + " WHERE "
                    + PstEmpDocAction.fieldNames[PstEmpDocAction.FLD_EMP_DOC_ACTION_ID] + " = " + docMasterId;

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
            String sql = "SELECT COUNT(" + PstEmpDocAction.fieldNames[PstEmpDocAction.FLD_EMP_DOC_ACTION_ID] + ") FROM " + TBL_HR_EMP_DOC_ACTION;
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
                    EmpDocAction docMaster = (EmpDocAction) list.get(ls);
                    if (oid == docMaster.getOID()) {
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

  
}
