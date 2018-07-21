/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.jobdesc;

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
 * @author khirayinnura
 */
public class PstJobCat extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_JOB_CATEGORY = "hr_job_category";
    public static final int FLD_JOB_CATEGORY_ID = 0;
    public static final int FLD_CATEGORY_TITLE = 1;
    public static final int FLD_DESCRIPTION = 2;
    public static String[] fieldNames = {
        "JOB_CATEGORY_ID",
        "CATEGORY_TITLE",
        "DESCRIPTION"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstJobCat() {
    }

    public PstJobCat(int i) throws DBException {
        super(new PstJobCat());
    }

    public PstJobCat(String sOid) throws DBException {
        super(new PstJobCat(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstJobCat(long lOid) throws DBException {
        super(new PstJobCat(0));
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
        return TBL_HR_JOB_CATEGORY;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstJobCat().getClass().getName();
    }

    public static JobCat fetchExc(long oid) throws DBException {
        try {
            JobCat entJobCat = new JobCat();
            PstJobCat pstJobCat = new PstJobCat(oid);
            entJobCat.setOID(oid);
            entJobCat.setCategoryTitle(pstJobCat.getString(FLD_CATEGORY_TITLE));
            entJobCat.setDescription(pstJobCat.getString(FLD_DESCRIPTION));
            return entJobCat;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstJobCat(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        JobCat entJobCat = fetchExc(entity.getOID());
        entity = (Entity) entJobCat;
        return entJobCat.getOID();
    }

    public static synchronized long updateExc(JobCat entJobCat) throws DBException {
        try {
            if (entJobCat.getOID() != 0) {
                PstJobCat pstJobCat = new PstJobCat(entJobCat.getOID());
                pstJobCat.setString(FLD_CATEGORY_TITLE, entJobCat.getCategoryTitle());
                pstJobCat.setString(FLD_DESCRIPTION, entJobCat.getDescription());
                pstJobCat.update();
                return entJobCat.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstJobCat(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((JobCat) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstJobCat pstJobCat = new PstJobCat(oid);
            pstJobCat.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstJobCat(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(JobCat entJobCat) throws DBException {
        try {
            PstJobCat pstJobCat = new PstJobCat(0);
            pstJobCat.setString(FLD_CATEGORY_TITLE, entJobCat.getCategoryTitle());
            pstJobCat.setString(FLD_DESCRIPTION, entJobCat.getDescription());
            pstJobCat.insert();
            entJobCat.setOID(pstJobCat.getlong(FLD_JOB_CATEGORY_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstJobCat(0), DBException.UNKNOWN);
        }
        return entJobCat.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((JobCat) entity);
    }
    
    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_JOB_CATEGORY;
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
                JobCat jobCat = new JobCat();
                resultToObject(rs, jobCat);
                lists.add(jobCat);
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

    public static void resultToObject(ResultSet rs, JobCat entJobCat) {
        try {
            entJobCat.setOID(rs.getLong(PstJobCat.fieldNames[PstJobCat.FLD_JOB_CATEGORY_ID]));
            entJobCat.setCategoryTitle(rs.getString(PstJobCat.fieldNames[PstJobCat.FLD_CATEGORY_TITLE]));
            entJobCat.setDescription(rs.getString(PstJobCat.fieldNames[PstJobCat.FLD_DESCRIPTION]));
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long jobCatId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_JOB_CATEGORY + " WHERE "
                    + PstJobCat.fieldNames[PstJobCat.FLD_JOB_CATEGORY_ID] + " = " + jobCatId;

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
            String sql = "SELECT COUNT(" + PstJobCat.fieldNames[PstJobCat.FLD_JOB_CATEGORY_ID] + ") FROM " + TBL_HR_JOB_CATEGORY;
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
    
    
    public static String getJobDescName(long oid) {
        DBResultSet dbrs = null;
        try{
            String sql = "SELECT "+PstJobCat.fieldNames[PstJobCat.FLD_CATEGORY_TITLE]+" FROM "+TBL_HR_JOB_CATEGORY+
                    " WHERE "+PstJobCat.fieldNames[PstJobCat.FLD_JOB_CATEGORY_ID]+"="+oid;
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            String name = "";
            while (rs.next()) {
                name = rs.getString(1);
            }

            rs.close();
            return name;
        } catch(Exception e) {
            return "";
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
                    JobCat jobCat = (JobCat) list.get(ls);
                    if (oid == jobCat.getOID()) {
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
