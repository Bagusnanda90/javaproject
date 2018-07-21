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
public class PstDocMasterFlow extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_DOC_MASTER_FLOW = "hr_doc_master_flow";
   public static final int FLD_DOC_MASTER_FLOW_ID = 0;
   public static final int FLD_DOC_MASTER_ID = 1;
   public static final int FLD_FLOW_TITLE = 2;
   public static final int FLD_FLOW_INDEX = 3;
   public static final int FLD_COMPANY_ID = 4;
   public static final int FLD_DIVISION_ID = 5;
   public static final int FLD_DEPARTMENT_ID = 6;
   public static final int FLD_LEVEL_ID = 7;
   public static final int FLD_POSITION_ID = 8;
   public static final int FLD_EMPLOYEE_ID = 9;
   
    public static final String[] fieldNames = {
      "DOC_MASTER_FLOW_ID",
      "DOC_MASTER_ID",
      "FLOW_TITLE",
      "FLOW_INDEX",
      "COMPANY_ID",
      "DIVISION_ID",
      "DEPARTMENT_ID",
      "LEVEL_ID",
      "POSITION_ID",
      "EMPLOYEE_ID"

    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
      TYPE_STRING,
      TYPE_INT,
      TYPE_LONG,
      TYPE_LONG,
      TYPE_LONG,
      TYPE_LONG,
      TYPE_LONG,
      TYPE_LONG
    };

   public PstDocMasterFlow() {
   }

    public PstDocMasterFlow(int i) throws DBException {
        super(new PstDocMasterFlow());
    }

    public PstDocMasterFlow(String sOid) throws DBException {
        super(new PstDocMasterFlow(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDocMasterFlow(long lOid) throws DBException {
        super(new PstDocMasterFlow(0));
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
        return TBL_HR_DOC_MASTER_FLOW;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDocMasterFlow().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        DocMasterFlow docMasterFlow = fetchExc(ent.getOID());
        ent = (Entity) docMasterFlow;
        return docMasterFlow.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((DocMasterFlow) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((DocMasterFlow) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static DocMasterFlow fetchExc(long oid) throws DBException {
        try {
            DocMasterFlow docMasterFlow = new DocMasterFlow();
            PstDocMasterFlow pstDocMasterFlow = new PstDocMasterFlow(oid);
            docMasterFlow.setOID(oid);

         docMasterFlow.setDoc_master_id(pstDocMasterFlow.getlong(FLD_DOC_MASTER_ID));
         docMasterFlow.setFlow_title(pstDocMasterFlow.getString(FLD_FLOW_TITLE));
         docMasterFlow.setFlow_index(pstDocMasterFlow.getInt(FLD_FLOW_INDEX));
         docMasterFlow.setCompany_id(pstDocMasterFlow.getlong(FLD_COMPANY_ID));
         docMasterFlow.setDivision_id(pstDocMasterFlow.getlong(FLD_DIVISION_ID));
         docMasterFlow.setDepartment_id(pstDocMasterFlow.getlong(FLD_DEPARTMENT_ID ));
         docMasterFlow.setLevel_id(pstDocMasterFlow.getlong(FLD_LEVEL_ID));
         docMasterFlow.setPosition_id(pstDocMasterFlow.getlong(FLD_POSITION_ID));
         docMasterFlow.setEmployee_id(pstDocMasterFlow.getlong(FLD_EMPLOYEE_ID));
         
            
            
            return docMasterFlow;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocMasterFlow(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(DocMasterFlow docMasterFlow) throws DBException {
        try {
            PstDocMasterFlow pstDocMasterFlow = new PstDocMasterFlow(0);

            pstDocMasterFlow.setLong(FLD_DOC_MASTER_ID, docMasterFlow.getDoc_master_id());
            pstDocMasterFlow.setString(FLD_FLOW_TITLE, docMasterFlow.getFlow_title());
            pstDocMasterFlow.setInt(FLD_FLOW_INDEX, docMasterFlow.getFlow_index());
            pstDocMasterFlow.setLong(FLD_COMPANY_ID, docMasterFlow.getCompany_id());
            pstDocMasterFlow.setLong(FLD_DIVISION_ID, docMasterFlow.getDivision_id());
            pstDocMasterFlow.setLong(FLD_DEPARTMENT_ID , docMasterFlow.getDepartment_id());
            pstDocMasterFlow.setLong(FLD_LEVEL_ID, docMasterFlow.getLevel_id());
            pstDocMasterFlow.setLong(FLD_POSITION_ID, docMasterFlow.getPosition_id());
            pstDocMasterFlow.setLong(FLD_EMPLOYEE_ID, docMasterFlow.getEmployee_id());
            
            pstDocMasterFlow.insert();
            docMasterFlow.setOID(pstDocMasterFlow.getlong(FLD_DOC_MASTER_FLOW_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocMasterFlow(0), DBException.UNKNOWN);
        }
        return docMasterFlow.getOID();
    }

    public static long updateExc(DocMasterFlow docMasterFlow) throws DBException {
        try {
            if (docMasterFlow.getOID() != 0) {
                PstDocMasterFlow pstDocMasterFlow = new PstDocMasterFlow(docMasterFlow.getOID());

                pstDocMasterFlow.setLong(FLD_DOC_MASTER_ID, docMasterFlow.getDoc_master_id());
                pstDocMasterFlow.setString(FLD_FLOW_TITLE, docMasterFlow.getFlow_title());
                pstDocMasterFlow.setInt(FLD_FLOW_INDEX, docMasterFlow.getFlow_index());
                pstDocMasterFlow.setLong(FLD_COMPANY_ID, docMasterFlow.getCompany_id());
                pstDocMasterFlow.setLong(FLD_DIVISION_ID, docMasterFlow.getDivision_id());
                pstDocMasterFlow.setLong(FLD_DEPARTMENT_ID , docMasterFlow.getDepartment_id());
                pstDocMasterFlow.setLong(FLD_LEVEL_ID, docMasterFlow.getLevel_id());
                pstDocMasterFlow.setLong(FLD_POSITION_ID, docMasterFlow.getPosition_id());
                pstDocMasterFlow.setLong(FLD_EMPLOYEE_ID, docMasterFlow.getEmployee_id());
            
                pstDocMasterFlow.update();
                return docMasterFlow.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocMasterFlow(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstDocMasterFlow pstDocMasterFlow = new PstDocMasterFlow(oid);
            pstDocMasterFlow.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDocMasterFlow(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_DOC_MASTER_FLOW;
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
                DocMasterFlow docMasterFlow = new DocMasterFlow();
                resultToObject(rs, docMasterFlow);
                lists.add(docMasterFlow);
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
    
      public static void resultToObject(ResultSet rs, DocMasterFlow docMasterFlow) {
        try {
  
               docMasterFlow.setOID(rs.getLong(PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DOC_MASTER_FLOW_ID]));
               docMasterFlow.setDoc_master_id(rs.getLong(PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DOC_MASTER_ID]));
               docMasterFlow.setFlow_title(rs.getString(PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_FLOW_TITLE]));
               docMasterFlow.setFlow_index(rs.getInt(PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_FLOW_INDEX]));
               docMasterFlow.setCompany_id(rs.getLong(PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_COMPANY_ID]));
               docMasterFlow.setDivision_id(rs.getLong(PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DIVISION_ID]));
               docMasterFlow.setDepartment_id(rs.getLong(PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DEPARTMENT_ID]));
               docMasterFlow.setLevel_id(rs.getLong(PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_LEVEL_ID]));
               docMasterFlow.setPosition_id(rs.getLong(PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_POSITION_ID]));
               docMasterFlow.setEmployee_id(rs.getLong(PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_EMPLOYEE_ID]));

        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long docMasterFlowId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_DOC_MASTER_FLOW + " WHERE "
                    + PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DOC_MASTER_FLOW_ID] + " = " + docMasterFlowId;

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
            String sql = "SELECT COUNT(" + PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DOC_MASTER_FLOW_ID] + ") FROM " + TBL_HR_DOC_MASTER_FLOW;
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
                    DocMasterFlow docMasterFlow = (DocMasterFlow) list.get(ls);
                    if (oid == docMasterFlow.getOID()) {
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
