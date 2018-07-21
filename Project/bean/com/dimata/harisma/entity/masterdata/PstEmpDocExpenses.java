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
public class PstEmpDocExpenses extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_EMP_DOC_EXPENSE = "hr_emp_doc_expense";
   public static final int FLD_EMP_DOC_EXPENSE_ID = 0;
   public static final int FLD_EMP_DOC_ID = 1;
   public static final int FLD_DOC_MASTER_EXPENSE_ID = 2;
   public static final int FLD_BUDGET_VALUE = 3;
   public static final int FLD_REAL_VALUE = 4;
   public static final int FLD_EXPENSE_UNIT = 5;
   public static final int FLD_TOTAL = 6;
   public static final int FLD_DESCRIPTION = 7;
   public static final int FLD_NOTE = 8;
   
    public static final String[] fieldNames = {
      "EMP_DOC_EXPENSE_ID",
      "EMP_DOC_ID",
      "DOC_MASTER_EXPENSE_ID",
      "BUDGET_VALUE",
      "REAL_VALUE",
      "EXPENSE_UNIT",
      "TOTAL",
      "DESCRIPTION",
      "NOTE"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_STRING
        
        
    };

   public PstEmpDocExpenses() {
   }

    public PstEmpDocExpenses(int i) throws DBException {
        super(new PstEmpDocExpenses());
    }

    public PstEmpDocExpenses(String sOid) throws DBException {
        super(new PstEmpDocExpenses(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpDocExpenses(long lOid) throws DBException {
        super(new PstEmpDocExpenses(0));
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
        return TBL_HR_EMP_DOC_EXPENSE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpDocExpenses().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        EmpDocExpenses empDocExpenses = fetchExc(ent.getOID());
        ent = (Entity) empDocExpenses;
        return empDocExpenses.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((EmpDocExpenses) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((EmpDocExpenses) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static EmpDocExpenses fetchExc(long oid) throws DBException {
        try {
            EmpDocExpenses empDocExpenses = new EmpDocExpenses();
            PstEmpDocExpenses pstEmpDocExpenses = new PstEmpDocExpenses(oid);
            empDocExpenses.setOID(oid);

            empDocExpenses.setEmpDocId(pstEmpDocExpenses.getLong(FLD_EMP_DOC_ID));
            empDocExpenses.setDocMasterExpenseId(pstEmpDocExpenses.getLong(FLD_DOC_MASTER_EXPENSE_ID));
            empDocExpenses.setBudgetValue(pstEmpDocExpenses.getfloat(FLD_BUDGET_VALUE));
            empDocExpenses.setRealvalue(pstEmpDocExpenses.getfloat(FLD_REAL_VALUE));
            empDocExpenses.setExpenseUnit(pstEmpDocExpenses.getInt(FLD_EXPENSE_UNIT));
            empDocExpenses.setTotal(pstEmpDocExpenses.getfloat(FLD_TOTAL));
            empDocExpenses.setDescription(pstEmpDocExpenses.getString(FLD_DESCRIPTION));
            empDocExpenses.setNote(pstEmpDocExpenses.getString(FLD_NOTE));

            return empDocExpenses;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocExpenses(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(EmpDocExpenses empDocExpenses) throws DBException {
        try {
            PstEmpDocExpenses pstEmpDocExpenses = new PstEmpDocExpenses(0);

            pstEmpDocExpenses.setLong(FLD_EMP_DOC_ID, empDocExpenses.getEmpDocId());
            pstEmpDocExpenses.setLong(FLD_DOC_MASTER_EXPENSE_ID, empDocExpenses.getDocMasterExpenseId());
            pstEmpDocExpenses.setFloat(FLD_BUDGET_VALUE, empDocExpenses.getBudgetValue());
            pstEmpDocExpenses.setFloat(FLD_REAL_VALUE, empDocExpenses.getRealvalue());
            pstEmpDocExpenses.setFloat(FLD_EXPENSE_UNIT, empDocExpenses.getExpenseUnit());
            pstEmpDocExpenses.setFloat(FLD_TOTAL, empDocExpenses.getTotal());
            pstEmpDocExpenses.setString(FLD_DESCRIPTION, empDocExpenses.getDescription());
            pstEmpDocExpenses.setString(FLD_NOTE, empDocExpenses.getNote());
          
            pstEmpDocExpenses.insert();
            empDocExpenses.setOID(pstEmpDocExpenses.getlong(FLD_EMP_DOC_EXPENSE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocExpenses(0), DBException.UNKNOWN);
        }
        return empDocExpenses.getOID();
    }

    public static long updateExc(EmpDocExpenses empDocExpenses) throws DBException {
        try {
            if (empDocExpenses.getOID() != 0) {
                PstEmpDocExpenses pstEmpDocExpenses = new PstEmpDocExpenses(empDocExpenses.getOID());

            pstEmpDocExpenses.setLong(FLD_EMP_DOC_ID, empDocExpenses.getEmpDocId());
            pstEmpDocExpenses.setLong(FLD_DOC_MASTER_EXPENSE_ID, empDocExpenses.getDocMasterExpenseId());
            pstEmpDocExpenses.setFloat(FLD_BUDGET_VALUE, empDocExpenses.getBudgetValue());
            pstEmpDocExpenses.setFloat(FLD_REAL_VALUE, empDocExpenses.getRealvalue());
            pstEmpDocExpenses.setFloat(FLD_EXPENSE_UNIT, empDocExpenses.getExpenseUnit());
            pstEmpDocExpenses.setFloat(FLD_TOTAL, empDocExpenses.getTotal());
            pstEmpDocExpenses.setString(FLD_DESCRIPTION, empDocExpenses.getDescription());
            pstEmpDocExpenses.setString(FLD_NOTE, empDocExpenses.getNote());

                pstEmpDocExpenses.update();
                return empDocExpenses.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocExpenses(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmpDocExpenses pstEmpDocExpenses = new PstEmpDocExpenses(oid);
            pstEmpDocExpenses.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocExpenses(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_EXPENSE;
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
                EmpDocExpenses empDocExpenses = new EmpDocExpenses();
                resultToObject(rs, empDocExpenses);
                lists.add(empDocExpenses);
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
    
      public static void resultToObject(ResultSet rs, EmpDocExpenses empDocExpenses) {
        try {
            empDocExpenses.setOID(rs.getLong(PstEmpDocExpenses.fieldNames[PstEmpDocExpenses.FLD_EMP_DOC_EXPENSE_ID]));
            empDocExpenses.setEmpDocId(rs.getLong(PstEmpDocExpenses.fieldNames[PstEmpDocExpenses.FLD_EMP_DOC_ID]));
            empDocExpenses.setDocMasterExpenseId(rs.getLong(PstEmpDocExpenses.fieldNames[PstEmpDocExpenses.FLD_DOC_MASTER_EXPENSE_ID]));
            
            empDocExpenses.setBudgetValue(rs.getFloat(PstEmpDocExpenses.fieldNames[PstEmpDocExpenses.FLD_BUDGET_VALUE]));
            empDocExpenses.setRealvalue(rs.getFloat(PstEmpDocExpenses.fieldNames[PstEmpDocExpenses.FLD_REAL_VALUE]));
            
            empDocExpenses.setExpenseUnit(rs.getInt(PstEmpDocExpenses.fieldNames[PstEmpDocExpenses.FLD_EXPENSE_UNIT]));
            empDocExpenses.setTotal(rs.getFloat(PstEmpDocExpenses.fieldNames[PstEmpDocExpenses.FLD_TOTAL]));
            empDocExpenses.setDescription(rs.getString(PstEmpDocExpenses.fieldNames[PstEmpDocExpenses.FLD_DESCRIPTION]));
            empDocExpenses.setNote(rs.getString(PstEmpDocExpenses.fieldNames[PstEmpDocExpenses.FLD_NOTE]));
            
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long empDocExpensesId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_EXPENSE+ " WHERE "
                    + PstEmpDocExpenses.fieldNames[PstEmpDocExpenses.FLD_EMP_DOC_EXPENSE_ID] + " = " + empDocExpensesId;

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
            String sql = "SELECT COUNT(" + PstEmpDocExpenses.fieldNames[PstEmpDocExpenses.FLD_EMP_DOC_EXPENSE_ID] + ") FROM " + TBL_HR_EMP_DOC_EXPENSE;
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
                    EmpDocExpenses empDocExpenses = (EmpDocExpenses) list.get(ls);
                    if (oid == empDocExpenses.getOID()) {
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
