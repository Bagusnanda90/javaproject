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
public class PstDocMasterExpense extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_DOC_MASTER_EXPENSE = "hr_doc_master_expense";
   public static final int FLD_DOC_MASTER_EXPENSE_ID = 0;
   public static final int FLD_DOC_MASTER_ID = 1;
   public static final int FLD_BUDGET_MIN = 2;
   public static final int FLD_BUDGET_MAX = 3;
   public static final int FLD_UNIT_TYPE = 4;
   public static final int FLD_UNIT_NAME = 5;
   public static final int FLD_DESCRIPTION = 6;
   public static final int FLD_DOC_EXPENSE_ID = 7;
   
    public static final String[] fieldNames = {
      "DOC_MASTER_EXPENSE_ID",
      "DOC_MASTER_ID",
      "BUDGET_MIN",
      "BUDGET_MAX",
      "UNIT_TYPE",
      "UNIT_NAME",
      "DESCRIPTION",
      "DOC_EXPENSE_ID"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG
    };

   public PstDocMasterExpense() {
   }

    public PstDocMasterExpense(int i) throws DBException {
        super(new PstDocMasterExpense());
    }

    public PstDocMasterExpense(String sOid) throws DBException {
        super(new PstDocMasterExpense(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDocMasterExpense(long lOid) throws DBException {
        super(new PstDocMasterExpense(0));
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
        return TBL_HR_DOC_MASTER_EXPENSE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDocMasterExpense().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        DocMasterExpense docMasterExpense = fetchExc(ent.getOID());
        ent = (Entity) docMasterExpense;
        return docMasterExpense.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((DocMasterExpense) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((DocMasterExpense) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static DocMasterExpense fetchExc(long oid) throws DBException {
        try {
            DocMasterExpense docMasterExpense = new DocMasterExpense();
            PstDocMasterExpense pstDocMasterExpense = new PstDocMasterExpense(oid);
            docMasterExpense.setOID(oid);

            
            docMasterExpense.setDoc_master_id(pstDocMasterExpense.getLong(FLD_DOC_MASTER_ID));
            docMasterExpense.setBudget_min(pstDocMasterExpense.getfloat(FLD_BUDGET_MIN));
            docMasterExpense.setBudget_max(pstDocMasterExpense.getfloat(FLD_BUDGET_MAX));
            docMasterExpense.setUnit_type(pstDocMasterExpense.getInt(FLD_UNIT_TYPE));
            docMasterExpense.setUnit_name(pstDocMasterExpense.getString(FLD_UNIT_NAME));
            docMasterExpense.setDescription(pstDocMasterExpense.getString(FLD_DESCRIPTION));
            docMasterExpense.setDoc_expense_id(pstDocMasterExpense.getLong(FLD_DOC_EXPENSE_ID));

            return docMasterExpense;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocMasterExpense(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(DocMasterExpense docMasterExpense) throws DBException {
        try {
            PstDocMasterExpense pstDocMasterExpense = new PstDocMasterExpense(0);

            pstDocMasterExpense.setLong(FLD_DOC_MASTER_ID, docMasterExpense.getDoc_master_id());
            pstDocMasterExpense.setFloat(FLD_BUDGET_MIN, docMasterExpense.getBudget_min());
            pstDocMasterExpense.setFloat(FLD_BUDGET_MAX, docMasterExpense.getBudget_max());
            pstDocMasterExpense.setInt(FLD_UNIT_TYPE, docMasterExpense.getUnit_type());
            pstDocMasterExpense.setString(FLD_UNIT_NAME, docMasterExpense.getUnit_name());
            pstDocMasterExpense.setString(FLD_DESCRIPTION, docMasterExpense.getDescription());
            pstDocMasterExpense.setLong(FLD_DOC_EXPENSE_ID, docMasterExpense.getDoc_expense_id());
          
            pstDocMasterExpense.insert();
            docMasterExpense.setOID(pstDocMasterExpense.getlong(FLD_DOC_MASTER_EXPENSE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocMasterExpense(0), DBException.UNKNOWN);
        }
        return docMasterExpense.getOID();
    }

    public static long updateExc(DocMasterExpense docMasterExpense) throws DBException {
        try {
            if (docMasterExpense.getOID() != 0) {
                PstDocMasterExpense pstDocMasterExpense = new PstDocMasterExpense(docMasterExpense.getOID());

                pstDocMasterExpense.setLong(FLD_DOC_MASTER_ID, docMasterExpense.getDoc_master_id());
                pstDocMasterExpense.setFloat(FLD_BUDGET_MIN, docMasterExpense.getBudget_min());
                pstDocMasterExpense.setFloat(FLD_BUDGET_MAX, docMasterExpense.getBudget_max());
                pstDocMasterExpense.setInt(FLD_UNIT_TYPE, docMasterExpense.getUnit_type());
                pstDocMasterExpense.setString(FLD_UNIT_NAME, docMasterExpense.getUnit_name());
                pstDocMasterExpense.setString(FLD_DESCRIPTION, docMasterExpense.getDescription());
                pstDocMasterExpense.setLong(FLD_DOC_EXPENSE_ID, docMasterExpense.getDoc_expense_id());

                
                pstDocMasterExpense.update();
                return docMasterExpense.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocMasterExpense(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstDocMasterExpense pstDocMasterExpense = new PstDocMasterExpense(oid);
            pstDocMasterExpense.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocMasterExpense(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_DOC_MASTER_EXPENSE;
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
                DocMasterExpense docMasterExpense = new DocMasterExpense();
                resultToObject(rs, docMasterExpense);
                lists.add(docMasterExpense);
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
    
      public static void resultToObject(ResultSet rs, DocMasterExpense docMasterExpense) {
        try {
            docMasterExpense.setOID(rs.getLong(PstDocMasterExpense.fieldNames[PstDocMasterExpense.FLD_DOC_MASTER_EXPENSE_ID]));
            docMasterExpense.setDoc_master_id(rs.getLong(PstDocMasterExpense.fieldNames[PstDocMasterExpense.FLD_DOC_MASTER_ID]));
            docMasterExpense.setBudget_min(rs.getFloat(PstDocMasterExpense.fieldNames[PstDocMasterExpense.FLD_BUDGET_MIN]));
            docMasterExpense.setBudget_max(rs.getFloat(PstDocMasterExpense.fieldNames[PstDocMasterExpense.FLD_BUDGET_MAX]));
            docMasterExpense.setUnit_type(rs.getInt(PstDocMasterExpense.fieldNames[PstDocMasterExpense.FLD_UNIT_TYPE]));
            docMasterExpense.setUnit_name(rs.getString(PstDocMasterExpense.fieldNames[PstDocMasterExpense.FLD_UNIT_NAME]));
            docMasterExpense.setDescription(rs.getString(PstDocMasterExpense.fieldNames[PstDocMasterExpense.FLD_DESCRIPTION]));
            docMasterExpense.setDoc_expense_id(rs.getLong(PstDocMasterExpense.fieldNames[PstDocMasterExpense.FLD_DOC_EXPENSE_ID]));
            
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long docMasterExpenseId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_DOC_MASTER_EXPENSE + " WHERE "
                    + PstDocMasterExpense.fieldNames[PstDocMasterExpense.FLD_DOC_MASTER_EXPENSE_ID] + " = " + docMasterExpenseId;

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
            String sql = "SELECT COUNT(" + PstDocMasterExpense.fieldNames[PstDocMasterExpense.FLD_DOC_MASTER_EXPENSE_ID] + ") FROM " + TBL_HR_DOC_MASTER_EXPENSE;
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
                    DocMasterExpense docMasterExpense = (DocMasterExpense) list.get(ls);
                    if (oid == docMasterExpense.getOID()) {
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
