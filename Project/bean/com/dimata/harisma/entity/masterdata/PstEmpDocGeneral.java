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
 * @author Gunadi
 */
public class PstEmpDocGeneral extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_EMP_DOC_GENERAL = "hr_emp_doc_general";
   public static final int FLD_EMP_DOC_GENERAL_ID = 0;
   public static final int FLD_EMP_DOC_ID = 1;
   public static final int FLD_EMPLOYEE_ID = 2;
   public static final int FLD_ACKNOWLEDGE_STATUS = 3;
   
    public static final String[] fieldNames = {
      "EMP_DOC_GENERAL_ID",
      "EMP_DOC_ID",
      "EMPLOYEE_ID",
      "ACKNOWLEDGE_STATUS"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT
    };

   public PstEmpDocGeneral() {
   }

    public PstEmpDocGeneral(int i) throws DBException {
        super(new PstEmpDocGeneral());
    }

    public PstEmpDocGeneral(String sOid) throws DBException {
        super(new PstEmpDocGeneral(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpDocGeneral(long lOid) throws DBException {
        super(new PstEmpDocGeneral(0));
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
        return TBL_HR_EMP_DOC_GENERAL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpDocGeneral().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        EmpDocGeneral empDocGeneral = fetchExc(ent.getOID());
        ent = (Entity) empDocGeneral;
        return empDocGeneral.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((EmpDocGeneral) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((EmpDocGeneral) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static EmpDocGeneral fetchExc(long oid) throws DBException {
        try {
            EmpDocGeneral empDocGeneral = new EmpDocGeneral();
            PstEmpDocGeneral pstEmpDocGeneral = new PstEmpDocGeneral(oid);
            empDocGeneral.setOID(oid);

            empDocGeneral.setEmpDocId(pstEmpDocGeneral.getLong(FLD_EMP_DOC_ID));
            empDocGeneral.setEmployeeId(pstEmpDocGeneral.getLong(FLD_EMPLOYEE_ID));
            empDocGeneral.setAcknowledgeStatus(pstEmpDocGeneral.getInt(FLD_ACKNOWLEDGE_STATUS));
            
            return empDocGeneral;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocGeneral(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(EmpDocGeneral empDocGeneral) throws DBException {
        try {
            PstEmpDocGeneral pstEmpDocGeneral = new PstEmpDocGeneral(0);
            pstEmpDocGeneral.setLong(FLD_EMP_DOC_ID, empDocGeneral.getEmpDocId());
            pstEmpDocGeneral.setLong(FLD_EMPLOYEE_ID, empDocGeneral.getEmployeeId());
            pstEmpDocGeneral.setLong(FLD_ACKNOWLEDGE_STATUS, empDocGeneral.getAcknowledgeStatus());
          
            pstEmpDocGeneral.insert();
            empDocGeneral.setOID(pstEmpDocGeneral.getlong(FLD_EMP_DOC_GENERAL_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocGeneral(0), DBException.UNKNOWN);
        }
        return empDocGeneral.getOID();
    }

    public static long updateExc(EmpDocGeneral empDocGeneral) throws DBException {
        try {
            if (empDocGeneral.getOID() != 0) {
                PstEmpDocGeneral pstEmpDocGeneral = new PstEmpDocGeneral(empDocGeneral.getOID());

                pstEmpDocGeneral.setLong(FLD_EMP_DOC_ID, empDocGeneral.getEmpDocId());
                pstEmpDocGeneral.setLong(FLD_EMPLOYEE_ID, empDocGeneral.getEmployeeId());
                pstEmpDocGeneral.setLong(FLD_ACKNOWLEDGE_STATUS, empDocGeneral.getAcknowledgeStatus());

                pstEmpDocGeneral.update();
                return empDocGeneral.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocGeneral(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmpDocGeneral pstEmpDocGeneral = new PstEmpDocGeneral(oid);
            pstEmpDocGeneral.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocGeneral(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_GENERAL;
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
                EmpDocGeneral empDocGeneral = new EmpDocGeneral();
                
            empDocGeneral.setOID(rs.getLong(PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_GENERAL_ID]));
            empDocGeneral.setEmpDocId(rs.getLong(PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_ID]));
            empDocGeneral.setEmployeeId(rs.getLong(PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMPLOYEE_ID]));
            empDocGeneral.setAcknowledgeStatus(rs.getInt(PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_ACKNOWLEDGE_STATUS]));
                //resultToObject(rs, empDocGeneral);
                lists.add(empDocGeneral);
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
    
    public static Vector listDataTable(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DOC.* FROM " + TBL_HR_EMP_DOC_GENERAL + " AS DOC"
                    + " INNER JOIN hr_employee AS EMP ON DOC.EMPLOYEE_ID = EMP.EMPLOYEE_ID";
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
                EmpDocGeneral empDocGeneral = new EmpDocGeneral();
                
            empDocGeneral.setOID(rs.getLong(PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_GENERAL_ID]));
            empDocGeneral.setEmpDocId(rs.getLong(PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_ID]));
            empDocGeneral.setEmployeeId(rs.getLong(PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMPLOYEE_ID]));
            empDocGeneral.setAcknowledgeStatus(rs.getInt(PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_ACKNOWLEDGE_STATUS]));
                //resultToObject(rs, empDocGeneral);
                lists.add(empDocGeneral);
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
    
      public static void resultToObject(ResultSet rs, EmpDocGeneral empDocGeneral) {
        try {
            empDocGeneral.setOID(rs.getLong(PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_GENERAL_ID]));
            empDocGeneral.setEmpDocId(rs.getLong(PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_ID]));
            empDocGeneral.setEmployeeId(rs.getLong(PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMPLOYEE_ID]));
            empDocGeneral.setAcknowledgeStatus(rs.getInt(PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_ACKNOWLEDGE_STATUS]));
            
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long empDocGeneralId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_GENERAL + " WHERE "
                    + PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_GENERAL_ID] + " = " + empDocGeneralId;

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
            String sql = "SELECT COUNT(" + PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_GENERAL_ID] + ") FROM " + TBL_HR_EMP_DOC_GENERAL;
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
    
    public static int getCountDataTable(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(DOC." + PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMP_DOC_GENERAL_ID] + ") FROM " + TBL_HR_EMP_DOC_GENERAL + " AS DOC"
                    + " INNER JOIN hr_employee AS EMP ON DOC.EMPLOYEE_ID = EMP.EMPLOYEE_ID";
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
                    EmpDocGeneral empDocGeneral = (EmpDocGeneral) list.get(ls);
                    if (oid == empDocGeneral.getOID()) {
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
