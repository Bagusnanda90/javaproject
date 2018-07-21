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
public class PstDocMasterAction extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_DOC_MASTER_ACTION = "hr_doc_action";
   public static final int FLD_DOC_ACTION_ID = 0;
   public static final int FLD_DOC_MASTER_ID = 1;
   public static final int FLD_ACTION_NAME = 2;
   public static final int FLD_ACTION_TITLE = 3;
   
    public static final String[] fieldNames = {
      "DOC_ACTION_ID",
      "DOC_MASTER_ID",
      "ACTION_NAME",
      "ACTION_TITLE"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING
    };

   public PstDocMasterAction() {
   }

    public PstDocMasterAction(int i) throws DBException {
        super(new PstDocMasterAction());
    }

    public PstDocMasterAction(String sOid) throws DBException {
        super(new PstDocMasterAction(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDocMasterAction(long lOid) throws DBException {
        super(new PstDocMasterAction(0));
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
        return TBL_HR_DOC_MASTER_ACTION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDocMasterAction().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        DocMasterAction docMaster = fetchExc(ent.getOID());
        ent = (Entity) docMaster;
        return docMaster.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((DocMasterAction) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((DocMasterAction) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static DocMasterAction fetchExc(long oid) throws DBException {
        try {
            DocMasterAction docAction = new DocMasterAction();
            PstDocMasterAction pstDocMasterAction = new PstDocMasterAction(oid);
            docAction.setOID(oid);

            docAction.setDocMasterId(pstDocMasterAction.getLong(FLD_DOC_MASTER_ID));         
            docAction.setActionName(pstDocMasterAction.getString(FLD_ACTION_NAME));
            docAction.setActionTitle(pstDocMasterAction.getString(FLD_ACTION_TITLE));

            return docAction;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocMasterAction(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(DocMasterAction docAction) throws DBException {
        try {
            PstDocMasterAction pstDocMasterAction = new PstDocMasterAction(0);

            pstDocMasterAction.setLong(FLD_DOC_MASTER_ID, docAction.getDocMasterId());
            pstDocMasterAction.setString(FLD_ACTION_NAME, docAction.getActionName());
            pstDocMasterAction.setString(FLD_ACTION_TITLE, docAction.getActionTitle());
          
            pstDocMasterAction.insert();
            docAction.setOID(pstDocMasterAction.getlong(FLD_DOC_ACTION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocMasterAction(0), DBException.UNKNOWN);
        }
        return docAction.getOID();
    }

    public static long updateExc(DocMasterAction docAction) throws DBException {
        try {
            if (docAction.getOID() != 0) {
                PstDocMasterAction pstDocMasterAction = new PstDocMasterAction(docAction.getOID());

                pstDocMasterAction.setLong(FLD_DOC_MASTER_ID, docAction.getDocMasterId());
                pstDocMasterAction.setString(FLD_ACTION_NAME, docAction.getActionName());
                pstDocMasterAction.setString(FLD_ACTION_TITLE, docAction.getActionTitle());

                pstDocMasterAction.update();
                return docAction.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocMasterAction(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstDocMasterAction pstDocMasterAction = new PstDocMasterAction(oid);
            pstDocMasterAction.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocMasterAction(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_DOC_MASTER_ACTION;
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
                DocMasterAction docMaster = new DocMasterAction();
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
    
      public static void resultToObject(ResultSet rs, DocMasterAction docAction) {
        try {
               docAction.setOID(rs.getLong(PstDocMasterAction.fieldNames[PstDocMasterAction.FLD_DOC_ACTION_ID]));
               docAction.setDocMasterId(rs.getLong(PstDocMasterAction.fieldNames[PstDocMasterAction.FLD_DOC_MASTER_ID]));
               docAction.setActionName(rs.getString(PstDocMasterAction.fieldNames[PstDocMasterAction.FLD_ACTION_NAME]));
               docAction.setActionTitle(rs.getString(PstDocMasterAction.fieldNames[PstDocMasterAction.FLD_ACTION_TITLE]));
            
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long docMasterId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_DOC_MASTER_ACTION + " WHERE "
                    + PstDocMasterAction.fieldNames[PstDocMasterAction.FLD_DOC_ACTION_ID] + " = " + docMasterId;

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
            String sql = "SELECT COUNT(" + PstDocMasterAction.fieldNames[PstDocMasterAction.FLD_DOC_ACTION_ID] + ") FROM " + TBL_HR_DOC_MASTER_ACTION;
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
                    DocMasterAction docMaster = (DocMasterAction) list.get(ls);
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
