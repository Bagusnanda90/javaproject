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
import java.util.Hashtable;
import java.util.Vector;


/**
 *
 * @author Gunadi
 */
public class PstEmpDocRecipient extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_EMP_DOC_RECIPIENT = "hr_emp_doc_recipient";
   public static final int FLD_EMP_DOC_RECIPIENT_ID = 0;
   public static final int FLD_EMP_DOC_ID = 1;
   public static final int FLD_COMPANY_ID = 2;
   public static final int FLD_DIVISION_ID = 3;
   public static final int FLD_DEPARTMENT_ID = 4;
   public static final int FLD_SECTION_ID = 5;
   public static final int FLD_POSITION_ID = 6;
   public static final int FLD_LEVEL_ID = 7;
   public static final int FLD_ALIAS = 8;
   
    public static final String[] fieldNames = {
      "EMP_DOC_RECIPIENT_ID",
      "EMP_DOC_ID",
      "COMPANY_ID",
      "DIVISION_ID",
      "DEPARTMENT_ID",
      "SECTION_ID",
      "POSITION_ID",
      "LEVEL_ID",
      "ALIAS"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING
    };

   public PstEmpDocRecipient() {
   }

    public PstEmpDocRecipient(int i) throws DBException {
        super(new PstEmpDocRecipient());
    }

    public PstEmpDocRecipient(String sOid) throws DBException {
        super(new PstEmpDocRecipient(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpDocRecipient(long lOid) throws DBException {
        super(new PstEmpDocRecipient(0));
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
        return TBL_HR_EMP_DOC_RECIPIENT;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpDocRecipient().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        EmpDocRecipient empDocList = fetchExc(ent.getOID());
        ent = (Entity) empDocList;
        return empDocList.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((EmpDocRecipient) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((EmpDocRecipient) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static EmpDocRecipient fetchExc(long oid) throws DBException {
        try {
            EmpDocRecipient empDocRecipient = new EmpDocRecipient();
            PstEmpDocRecipient pstEmpDocRecipient = new PstEmpDocRecipient(oid);
            empDocRecipient.setOID(oid);

            empDocRecipient.setEmpDocId(pstEmpDocRecipient.getLong(FLD_EMP_DOC_ID));
            empDocRecipient.setCompanyId(pstEmpDocRecipient.getLong(FLD_COMPANY_ID));
            empDocRecipient.setDivisionId(pstEmpDocRecipient.getLong(FLD_DIVISION_ID));
            empDocRecipient.setDepartmentId(pstEmpDocRecipient.getLong(FLD_DEPARTMENT_ID));
            empDocRecipient.setSectionId(pstEmpDocRecipient.getLong(FLD_SECTION_ID));
            empDocRecipient.setPositionId(pstEmpDocRecipient.getLong(FLD_POSITION_ID));
            empDocRecipient.setLevelId(pstEmpDocRecipient.getLong(FLD_LEVEL_ID));
            empDocRecipient.setAlias(pstEmpDocRecipient.getString(FLD_ALIAS));

            return empDocRecipient;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocRecipient(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(EmpDocRecipient empDocList) throws DBException {
        try {
            PstEmpDocRecipient pstEmpDocRecipient = new PstEmpDocRecipient(0);
            pstEmpDocRecipient.setLong(FLD_EMP_DOC_ID, empDocList.getEmpDocId());
            pstEmpDocRecipient.setLong(FLD_COMPANY_ID, empDocList.getCompanyId());
            pstEmpDocRecipient.setLong(FLD_DIVISION_ID, empDocList.getDivisionId());
            pstEmpDocRecipient.setLong(FLD_DEPARTMENT_ID, empDocList.getDepartmentId());
            pstEmpDocRecipient.setLong(FLD_SECTION_ID, empDocList.getSectionId());
            pstEmpDocRecipient.setLong(FLD_POSITION_ID, empDocList.getPositionId());
            pstEmpDocRecipient.setLong(FLD_LEVEL_ID, empDocList.getLevelId());
            pstEmpDocRecipient.setString(FLD_ALIAS, empDocList.getAlias());
          
            pstEmpDocRecipient.insert();
            empDocList.setOID(pstEmpDocRecipient.getlong(FLD_EMP_DOC_RECIPIENT_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocRecipient(0), DBException.UNKNOWN);
        }
        return empDocList.getOID();
    }

    public static long updateExc(EmpDocRecipient empDocList) throws DBException {
        try {
            if (empDocList.getOID() != 0) {
                PstEmpDocRecipient pstEmpDocRecipient = new PstEmpDocRecipient(empDocList.getOID());

                pstEmpDocRecipient.setLong(FLD_EMP_DOC_ID, empDocList.getEmpDocId());
                pstEmpDocRecipient.setLong(FLD_COMPANY_ID, empDocList.getCompanyId());
                pstEmpDocRecipient.setLong(FLD_DIVISION_ID, empDocList.getDivisionId());
                pstEmpDocRecipient.setLong(FLD_DEPARTMENT_ID, empDocList.getDepartmentId());
                pstEmpDocRecipient.setLong(FLD_SECTION_ID, empDocList.getSectionId());
                pstEmpDocRecipient.setLong(FLD_POSITION_ID, empDocList.getPositionId());
                pstEmpDocRecipient.setLong(FLD_LEVEL_ID, empDocList.getLevelId());
                pstEmpDocRecipient.setString(FLD_ALIAS, empDocList.getAlias());
          

                pstEmpDocRecipient.update();
                return empDocList.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocRecipient(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmpDocRecipient pstEmpDocRecipient = new PstEmpDocRecipient(oid);
            pstEmpDocRecipient.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDocRecipient(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_RECIPIENT;
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
                EmpDocRecipient empDocList = new EmpDocRecipient();
                
            empDocList.setOID(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_RECIPIENT_ID]));
            empDocList.setEmpDocId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_ID]));
            empDocList.setCompanyId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_COMPANY_ID]));
            empDocList.setDivisionId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_DIVISION_ID]));
            empDocList.setDepartmentId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_DEPARTMENT_ID]));
            empDocList.setSectionId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_SECTION_ID]));
            empDocList.setPositionId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_POSITION_ID]));
            empDocList.setLevelId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_LEVEL_ID]));
            empDocList.setAlias(rs.getString(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_ALIAS]));
                //resultToObject(rs, empDocList);
                lists.add(empDocList);
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
    
      public static void resultToObject(ResultSet rs, EmpDocRecipient empDocList) {
        try {
            empDocList.setOID(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_RECIPIENT_ID]));
            empDocList.setEmpDocId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_ID]));
            empDocList.setCompanyId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_COMPANY_ID]));
            empDocList.setDivisionId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_DIVISION_ID]));
            empDocList.setDepartmentId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_DEPARTMENT_ID]));
            empDocList.setSectionId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_SECTION_ID]));
            empDocList.setPositionId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_POSITION_ID]));
            empDocList.setLevelId(rs.getLong(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_LEVEL_ID]));
            empDocList.setAlias(rs.getString(PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_ALIAS]));
            
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long empDocListId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_RECIPIENT + " WHERE "
                    + PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_RECIPIENT_ID] + " = " + empDocListId;

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
            String sql = "SELECT COUNT(" + PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_RECIPIENT_ID] + ") FROM " + TBL_HR_EMP_DOC_RECIPIENT;
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
                    EmpDocRecipient empDocList = (EmpDocRecipient) list.get(ls);
                    if (oid == empDocList.getOID()) {
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
