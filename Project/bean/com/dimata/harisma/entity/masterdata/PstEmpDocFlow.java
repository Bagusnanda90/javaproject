/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.harisma.entity.configrewardnpunisment.PstRewardAndPunishmentDetail;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class PstEmpDocFlow extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_EMP_DOC_FLOW = "hr_emp_doc_flow";
   public static final int FLD_EMP_DOC_FLOW_ID = 0;
   public static final int FLD_FLOW_TITLE = 1;
   public static final int FLD_FLOW_INDEX = 2;
   public static final int FLD_SIGNED_BY = 3;
   public static final int FLD_SIGNED_DATE = 4;
   public static final int FLD_NOTE = 5;
   public static final int FLD_EMP_DOC_ID = 6;
   
    public static final String[] fieldNames = {
      "EMP_DOC_FLOW_ID",
      "FLOW_TITLE",
      "FLOW_INDEX",
      "SIGNED_BY",
      "SIGNED_DATE",
      "NOTE",
      "EMP_DOC_ID"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_LONG
    };

   public PstEmpDocFlow() {
   }

    public PstEmpDocFlow(int i) throws DBException {
        super(new PstEmpDocFlow());
    }

    public PstEmpDocFlow(String sOid) throws DBException {
        super(new PstEmpDocFlow(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpDocFlow(long lOid) throws DBException {
        super(new PstEmpDocFlow(0));
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
        return TBL_HR_EMP_DOC_FLOW;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpDocFlow().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        EmpDocFlow empDocFlow = fetchExc(ent.getOID());
        ent = (Entity) empDocFlow;
        return empDocFlow.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((EmpDocFlow) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((EmpDocFlow) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static EmpDocFlow fetchExc(long oid) throws DBException {
        try {
            EmpDocFlow empDocFlow = new EmpDocFlow();
            PstEmpDocFlow pstEmpDocFlow = new PstEmpDocFlow(oid);
            empDocFlow.setOID(oid);

            empDocFlow.setFlowIndex(pstEmpDocFlow.getInt(FLD_FLOW_INDEX));
            empDocFlow.setFlowTitle(pstEmpDocFlow.getString(FLD_FLOW_TITLE));
            empDocFlow.setNote(pstEmpDocFlow.getString(FLD_NOTE));
            empDocFlow.setSignedBy(pstEmpDocFlow.getLong(FLD_SIGNED_BY));
            empDocFlow.setSignedDate(pstEmpDocFlow.getDate(FLD_SIGNED_DATE));
            empDocFlow.setEmpDocId(pstEmpDocFlow.getLong(FLD_EMP_DOC_ID));
            
            return empDocFlow;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocFlow(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(EmpDocFlow empDocFlow) throws DBException {
        try {
            PstEmpDocFlow pstEmpDocFlow = new PstEmpDocFlow(0);
            pstEmpDocFlow.setString(FLD_FLOW_TITLE, empDocFlow.getFlowTitle());
            pstEmpDocFlow.setInt(FLD_FLOW_INDEX, empDocFlow.getFlowIndex());
            pstEmpDocFlow.setLong(FLD_SIGNED_BY, empDocFlow.getSignedBy());
            pstEmpDocFlow.setDate(FLD_SIGNED_DATE, empDocFlow.getSignedDate());
            pstEmpDocFlow.setString(FLD_NOTE, empDocFlow.getNote());
            pstEmpDocFlow.setLong(FLD_EMP_DOC_ID, empDocFlow.getEmpDocId());
          
            pstEmpDocFlow.insert();
            empDocFlow.setOID(pstEmpDocFlow.getlong(FLD_EMP_DOC_FLOW_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocFlow(0), DBException.UNKNOWN);
        }
        return empDocFlow.getOID();
    }

    public static long updateExc(EmpDocFlow empDocFlow) throws DBException {
        try {
            if (empDocFlow.getOID() != 0) {
                PstEmpDocFlow pstEmpDocFlow = new PstEmpDocFlow(empDocFlow.getOID());

                        pstEmpDocFlow.setString(FLD_FLOW_TITLE, empDocFlow.getFlowTitle());
                        pstEmpDocFlow.setInt(FLD_FLOW_INDEX, empDocFlow.getFlowIndex());
                        pstEmpDocFlow.setLong(FLD_SIGNED_BY, empDocFlow.getSignedBy());
                        pstEmpDocFlow.setDate(FLD_SIGNED_DATE, empDocFlow.getSignedDate());
                        pstEmpDocFlow.setString(FLD_NOTE, empDocFlow.getNote());
                        pstEmpDocFlow.setLong(FLD_EMP_DOC_ID, empDocFlow.getEmpDocId());

                pstEmpDocFlow.update();
                return empDocFlow.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocFlow(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmpDocFlow pstEmpDocFlow = new PstEmpDocFlow(oid);
            pstEmpDocFlow.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocFlow(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_FLOW;
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
                EmpDocFlow empDocFlow = new EmpDocFlow();
                
            empDocFlow.setOID(rs.getLong(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_FLOW_ID]));
            empDocFlow.setFlowTitle(rs.getString(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_FLOW_TITLE]));
            empDocFlow.setFlowIndex(rs.getInt(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_FLOW_INDEX]));
            empDocFlow.setSignedBy(rs.getLong(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_SIGNED_BY]));
            empDocFlow.setNote(rs.getString(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_NOTE]));
            empDocFlow.setSignedDate(rs.getDate(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_SIGNED_DATE]));
            empDocFlow.setEmpDocId(rs.getLong(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_ID]));
                //resultToObject(rs, empDocFlow);
                lists.add(empDocFlow);
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
    
    public static Hashtable Hlist(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable Hlists = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_FLOW;
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
                EmpDocFlow empDocFlow = new EmpDocFlow();
                
            empDocFlow.setOID(rs.getLong(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_FLOW_ID]));
            empDocFlow.setFlowTitle(rs.getString(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_FLOW_TITLE]));
            empDocFlow.setFlowIndex(rs.getInt(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_FLOW_INDEX]));
            empDocFlow.setSignedBy(rs.getLong(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_SIGNED_BY]));
            empDocFlow.setNote(rs.getString(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_NOTE]));
            empDocFlow.setSignedDate(rs.getDate(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_SIGNED_DATE]));
            empDocFlow.setEmpDocId(rs.getLong(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_ID]));
                //resultToObject(rs, empDocFlow);
                Hlists.put(empDocFlow.getSignedBy(),empDocFlow);
            }
            rs.close();
            return Hlists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Hashtable();
    }
    
    
      public static void resultToObject(ResultSet rs, EmpDocFlow empDocFlow) {
        try {
            empDocFlow.setOID(rs.getLong(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_FLOW_ID]));
            empDocFlow.setFlowTitle(rs.getString(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_FLOW_TITLE]));
            empDocFlow.setFlowIndex(rs.getInt(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_FLOW_INDEX]));
            empDocFlow.setSignedBy(rs.getLong(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_SIGNED_BY]));
            empDocFlow.setNote(rs.getString(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_NOTE]));
            empDocFlow.setSignedDate(rs.getDate(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_SIGNED_DATE]));
            
            empDocFlow.setEmpDocId(rs.getLong(PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_ID]));
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long empDocFlowId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_FLOW + " WHERE "
                    + PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_FLOW_ID] + " = " + empDocFlowId;

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
            String sql = "SELECT COUNT(" + PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_FLOW_ID] + ") FROM " + TBL_HR_EMP_DOC_FLOW;
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
//
//public static long deletewhere(long employeeId, long empDocId, String objekName) {
//        DBResultSet dbrs = null;
//        long resulthasil =0;
//        try {
//            String sql = "DELETE  FROM " + TBL_HR_EMP_DOC_FLOW + " WHERE "
//                    + PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_ID] + " = " + empDocId
//                    + " AND "+ PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMPLOYEE_ID] + " = " + employeeId
//                    + " AND "+ PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_OBJECT_NAME] + " = \"" + objekName+"\"";
//            
//            DBHandler.execSqlInsert(sql);
//        } catch (Exception e) {
//            System.out.println("err : " + e.toString());
//            
//        } finally {
//            DBResultSet.close(dbrs);
//            return resulthasil;
//        }
//    }
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
                    EmpDocFlow empDocFlow = (EmpDocFlow) list.get(ls);
                    if (oid == empDocFlow.getOID()) {
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
