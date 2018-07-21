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
public class PstDocExpenses extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_DOC_EXPENSES = "hr_doc_expenses";
   public static final int FLD_DOC_EXPENSE_ID = 0;
   public static final int FLD_EXPENSE_NAME = 1;
   public static final int FLD_PLAN_EXPENSE_VALUE = 2;
   public static final int FLD_NOTE = 3;
   
    public static final String[] fieldNames = {
      "DOC_EXPENSE_ID",
      "EXPENSE_NAME",
      "PLAN_EXPENSE_VALUE",
      "NOTE"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_STRING
    };

   public PstDocExpenses() {
   }

    public PstDocExpenses(int i) throws DBException {
        super(new PstDocExpenses());
    }

    public PstDocExpenses(String sOid) throws DBException {
        super(new PstDocExpenses(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDocExpenses(long lOid) throws DBException {
        super(new PstDocExpenses(0));
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
        return TBL_HR_DOC_EXPENSES;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDocExpenses().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        DocExpenses docExpenses = fetchExc(ent.getOID());
        ent = (Entity) docExpenses;
        return docExpenses.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((DocExpenses) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((DocExpenses) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static DocExpenses fetchExc(long oid) throws DBException {
        try {
            DocExpenses docExpenses = new DocExpenses();
            PstDocExpenses pstDocExpenses = new PstDocExpenses(oid);
            docExpenses.setOID(oid);

            docExpenses.setExpense_name(pstDocExpenses.getString(FLD_EXPENSE_NAME));
            docExpenses.setPlan_expense_value(pstDocExpenses.getfloat(FLD_PLAN_EXPENSE_VALUE));
            docExpenses.setNote(pstDocExpenses.getString(FLD_NOTE));

            return docExpenses;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocExpenses(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(DocExpenses docExpenses) throws DBException {
        try {
            PstDocExpenses pstDocExpenses = new PstDocExpenses(0);

            pstDocExpenses.setString(FLD_EXPENSE_NAME, docExpenses.getExpense_name());
            pstDocExpenses.setFloat(FLD_PLAN_EXPENSE_VALUE, docExpenses.getPlan_expense_value());
            pstDocExpenses.setString(FLD_NOTE, docExpenses.getNote());
          
            pstDocExpenses.insert();
            docExpenses.setOID(pstDocExpenses.getlong(FLD_DOC_EXPENSE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocExpenses(0), DBException.UNKNOWN);
        }
        return docExpenses.getOID();
    }

    public static long updateExc(DocExpenses docExpenses) throws DBException {
        try {
            if (docExpenses.getOID() != 0) {
                PstDocExpenses pstDocExpenses = new PstDocExpenses(docExpenses.getOID());

                pstDocExpenses.setString(FLD_EXPENSE_NAME, docExpenses.getExpense_name());
                pstDocExpenses.setFloat(FLD_PLAN_EXPENSE_VALUE, docExpenses.getPlan_expense_value());
                pstDocExpenses.setString(FLD_NOTE, docExpenses.getNote());;

                pstDocExpenses.update();
                return docExpenses.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocExpenses(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstDocExpenses pstDocExpenses = new PstDocExpenses(oid);
            pstDocExpenses.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocExpenses(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_DOC_EXPENSES;
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
                DocExpenses docExpenses = new DocExpenses();
                resultToObject(rs, docExpenses);
                lists.add(docExpenses);
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
    
      public static void resultToObject(ResultSet rs, DocExpenses docExpenses) {
        try {
            docExpenses.setOID(rs.getLong(PstDocExpenses.fieldNames[PstDocExpenses.FLD_DOC_EXPENSE_ID]));
            docExpenses.setExpense_name(rs.getString(PstDocExpenses.fieldNames[PstDocExpenses.FLD_EXPENSE_NAME]));
            docExpenses.setPlan_expense_value(rs.getFloat(PstDocExpenses.fieldNames[PstDocExpenses.FLD_PLAN_EXPENSE_VALUE]));
            docExpenses.setNote(rs.getString(PstDocExpenses.fieldNames[PstDocExpenses.FLD_NOTE]));
            
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long docExpensesId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_DOC_EXPENSES+ " WHERE "
                    + PstDocExpenses.fieldNames[PstDocExpenses.FLD_DOC_EXPENSE_ID] + " = " + docExpensesId;

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
            String sql = "SELECT COUNT(" + PstDocExpenses.fieldNames[PstDocExpenses.FLD_DOC_EXPENSE_ID] + ") FROM " + TBL_HR_DOC_EXPENSES;
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
                    DocExpenses docExpenses = (DocExpenses) list.get(ls);
                    if (oid == docExpenses.getOID()) {
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
