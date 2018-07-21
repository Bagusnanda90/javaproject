/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.report.lkpbu;

import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstEducation;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
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
public class PstLkpbu805 extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_LKPBU_805 = "hr_report_lkpbu_805";
    public static final int FLD_LKPBU_805_ID = 0;
    public static final int FLD_LEVEL_ID = 1;
    public static final int FLD_EDUCATION_ID = 2;
    public static final int FLD_EMP_CATEGORY_ID = 3;
    public static final int FLD_LKPBU_805_YEAR_REALISASI = 4;
    public static final int FLD_LKPBU_805_YEAR_PREDIKSI_1 = 5;
    public static final int FLD_LKPBU_805_YEAR_PREDIKSI_2 = 6;
    public static final int FLD_LKPBU_805_YEAR_PREDIKSI_3 = 7;
    public static final int FLD_LKPBU_805_YEAR_PREDIKSI_4 = 8;
    public static final int FLD_LKPBU_805_START_DATE = 9;
    public static String[] fieldNames = {
        "LKPBU_805_ID",
        "LEVEL_ID",
        "EDUCATION_ID",
        "EMP_CATEGORY_ID",
        "LKPBU_805_YEAR_REALISASI",
        "LKPBU_805_YEAR_PREDIKSI_1",
        "LKPBU_805_YEAR_PREDIKSI_2",
        "LKPBU_805_YEAR_PREDIKSI_3",
        "LKPBU_805_YEAR_PREDIKSI_4",
        "LKPBU_805_START_DATE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_DATE
    };

    public PstLkpbu805() {
    }

    public PstLkpbu805(int i) throws DBException {
        super(new PstLkpbu805());
    }

    public PstLkpbu805(String sOid) throws DBException {
        super(new PstLkpbu805(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstLkpbu805(long lOid) throws DBException {
        super(new PstLkpbu805(0));
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
        return TBL_LKPBU_805;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstLkpbu805().getClass().getName();
    }

    public static Lkpbu805 fetchExc(long oid) throws DBException {
        try {
            Lkpbu805 entLkpbu805 = new Lkpbu805();
            PstLkpbu805 pstLkpbu805 = new PstLkpbu805(oid);
            entLkpbu805.setOID(oid);
            entLkpbu805.setLevelId(pstLkpbu805.getLong(FLD_LEVEL_ID));
            entLkpbu805.setEducationId(pstLkpbu805.getLong(FLD_EDUCATION_ID));
            entLkpbu805.setEmpCategoryId(pstLkpbu805.getLong(FLD_EMP_CATEGORY_ID));
            entLkpbu805.setLkpbu805YearRealisasi(pstLkpbu805.getfloat(FLD_LKPBU_805_YEAR_REALISASI));
            entLkpbu805.setLkpbu805YearPrediksi1(pstLkpbu805.getfloat(FLD_LKPBU_805_YEAR_PREDIKSI_1));
            entLkpbu805.setLkpbu805YearPrediksi2(pstLkpbu805.getfloat(FLD_LKPBU_805_YEAR_PREDIKSI_2));
            entLkpbu805.setLkpbu805YearPrediksi3(pstLkpbu805.getfloat(FLD_LKPBU_805_YEAR_PREDIKSI_3));
            entLkpbu805.setLkpbu805YearPrediksi4(pstLkpbu805.getfloat(FLD_LKPBU_805_YEAR_PREDIKSI_4));
            entLkpbu805.setLkpbu805StartDate(pstLkpbu805.getDate(FLD_LKPBU_805_START_DATE));
            return entLkpbu805;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLkpbu805(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        Lkpbu805 entLkpbu805 = fetchExc(entity.getOID());
        entity = (Entity) entLkpbu805;
        return entLkpbu805.getOID();
    }

    public static synchronized long updateExc(Lkpbu805 entLkpbu805) throws DBException {
        try {
            if (entLkpbu805.getOID() != 0) {
                PstLkpbu805 pstLkpbu805 = new PstLkpbu805(entLkpbu805.getOID());
                pstLkpbu805.setLong(FLD_LEVEL_ID, entLkpbu805.getLevelId());
                pstLkpbu805.setLong(FLD_EDUCATION_ID, entLkpbu805.getEducationId());
                pstLkpbu805.setLong(FLD_EMP_CATEGORY_ID, entLkpbu805.getEmpCategoryId());
                pstLkpbu805.setFloat(FLD_LKPBU_805_YEAR_REALISASI, entLkpbu805.getLkpbu805YearRealisasi());
                pstLkpbu805.setFloat(FLD_LKPBU_805_YEAR_PREDIKSI_1, entLkpbu805.getLkpbu805YearPrediksi1());
                pstLkpbu805.setFloat(FLD_LKPBU_805_YEAR_PREDIKSI_2, entLkpbu805.getLkpbu805YearPrediksi2());
                pstLkpbu805.setFloat(FLD_LKPBU_805_YEAR_PREDIKSI_3, entLkpbu805.getLkpbu805YearPrediksi3());
                pstLkpbu805.setFloat(FLD_LKPBU_805_YEAR_PREDIKSI_4, entLkpbu805.getLkpbu805YearPrediksi4());
                pstLkpbu805.setDate(FLD_LKPBU_805_START_DATE, entLkpbu805.getLkpbu805StartDate());
                pstLkpbu805.update();
                return entLkpbu805.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLkpbu805(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((Lkpbu805) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstLkpbu805 pstLkpbu805 = new PstLkpbu805(oid);
            pstLkpbu805.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLkpbu805(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(Lkpbu805 entLkpbu805) throws DBException {
        try {
            PstLkpbu805 pstLkpbu805 = new PstLkpbu805(0);
            pstLkpbu805.setLong(FLD_LEVEL_ID, entLkpbu805.getLevelId());
            pstLkpbu805.setLong(FLD_EDUCATION_ID, entLkpbu805.getEducationId());
            pstLkpbu805.setLong(FLD_EMP_CATEGORY_ID, entLkpbu805.getEmpCategoryId());
            pstLkpbu805.setFloat(FLD_LKPBU_805_YEAR_REALISASI, entLkpbu805.getLkpbu805YearRealisasi());
            pstLkpbu805.setFloat(FLD_LKPBU_805_YEAR_PREDIKSI_1, entLkpbu805.getLkpbu805YearPrediksi1());
            pstLkpbu805.setFloat(FLD_LKPBU_805_YEAR_PREDIKSI_2, entLkpbu805.getLkpbu805YearPrediksi2());
            pstLkpbu805.setFloat(FLD_LKPBU_805_YEAR_PREDIKSI_3, entLkpbu805.getLkpbu805YearPrediksi3());
            pstLkpbu805.setFloat(FLD_LKPBU_805_YEAR_PREDIKSI_4, entLkpbu805.getLkpbu805YearPrediksi4());
            pstLkpbu805.setDate(FLD_LKPBU_805_START_DATE, entLkpbu805.getLkpbu805StartDate());
            pstLkpbu805.insert();
            entLkpbu805.setOID(pstLkpbu805.getlong(FLD_LKPBU_805_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLkpbu805(0), DBException.UNKNOWN);
        }
        return entLkpbu805.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((Lkpbu805) entity);
    }
    
    public static Vector listAll() {
        return list(0, 500, "", "");
    }
    
    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        
        try {
            String sql = "SELECT * FROM " + TBL_LKPBU_805;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                Lkpbu805 lkpbu805 = new Lkpbu805();
                resultToObject(rs, lkpbu805);
                lists.add(lkpbu805);
            }
            rs.close();
            return lists;
        } catch(Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    public static Vector listJoin(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        
        try {
            String sql = "SELECT lkpbu.LKPBU_805_ID, lkpbu.LEVEL_ID, lkpbu.EDUCATION_ID, lkpbu.EMP_CATEGORY_ID, lvl.CODE AS level_code, edu.EDUCATION_LEVEL, cat.CODE AS cat_code,"+
                    " lkpbu.LKPBU_805_YEAR_REALISASI, lkpbu.LKPBU_805_YEAR_PREDIKSI_1, lkpbu.LKPBU_805_YEAR_PREDIKSI_2,"+
                    " lkpbu.LKPBU_805_YEAR_PREDIKSI_3, lkpbu.LKPBU_805_YEAR_PREDIKSI_4, lkpbu.LKPBU_805_START_DATE from " + TBL_LKPBU_805 + " AS lkpbu"+
                    " INNER JOIN hr_level AS lvl ON lkpbu.LEVEL_ID=lvl.LEVEL_ID"+
                    " INNER JOIN hr_education AS edu ON lkpbu.EDUCATION_ID=edu.EDUCATION_ID"+
                    " INNER JOIN hr_emp_category AS cat ON lkpbu.EMP_CATEGORY_ID=cat.EMP_CATEGORY_ID";
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                Lkpbu805 lkpbu805 = new Lkpbu805();
                resultToObjectJoin(rs, lkpbu805);
                lists.add(lkpbu805);
            }
            rs.close();
            return lists;
        } catch(Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    public static Vector listLkpbuRealisasi(String whereClause, int year) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT emp.EMPLOYEE_ID, lvl.LEVEL_ID, lvl.CODE AS lvl_code, ed.EDUCATION_ID, MAX(ed.EDUCATION_LEVEL) AS EDU_LEVEL,"+
                    " cat.EMP_CATEGORY_ID, cat.CODE AS cat_code, '"+year+"'-YEAR(emp.BIRTH_DATE) AS umur, emp.COMMENCING_DATE, emp.RESIGNED,"+
                    " emp.RESIGNED_DATE"+
                    " FROM hr_employee AS emp"+
                    " INNER JOIN hr_emp_category AS cat ON emp.EMP_CATEGORY_ID = cat.EMP_CATEGORY_ID"+
                    " INNER JOIN hr_level AS lvl ON emp.LEVEL_ID = lvl.LEVEL_ID"+
                    " INNER JOIN hr_emp_education AS emp_ed ON emp.EMPLOYEE_ID=emp_ed.EMPLOYEE_ID"+
                    " INNER JOIN hr_education AS ed ON emp_ed.EDUCATION_ID=ed.EDUCATION_ID";
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            sql = sql + " GROUP BY emp.employee_id ORDER BY lvl_code, EDU_LEVEL, cat_code, umur";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
                Lkpbu805 lkpbu805 = new Lkpbu805();
                resultToObjectRealisasi(rs, lkpbu805);
                lists.add(lkpbu805);
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
    public static void resultToObjectRealisasi(ResultSet rs, Lkpbu805 lkpbu805) {
        try {
            //lkpbu805.setOID(rs.getLong(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_ID]));
            lkpbu805.setLevelId(rs.getLong(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LEVEL_ID]));
            lkpbu805.setLevelCode(rs.getString("lvl_code"));
            lkpbu805.setEducationId(rs.getLong(PstLkpbu805.fieldNames[PstLkpbu805.FLD_EDUCATION_ID]));
            lkpbu805.setEducationCode(rs.getString("EDU_LEVEL"));
            lkpbu805.setEmpCategoryId(rs.getLong(PstLkpbu805.fieldNames[FLD_EMP_CATEGORY_ID]));
            lkpbu805.setEmpCategoryCode(rs.getString("cat_code"));
            lkpbu805.setUmur(rs.getInt("umur"));
            lkpbu805.setEmpCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
            lkpbu805.setEmpResign(rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]));
            
        } catch (Exception e) {
        }
    }

    public static void resultToObject(ResultSet rs, Lkpbu805 entLkpbu805) {
        try {
            entLkpbu805.setOID(rs.getLong(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_ID]));
            entLkpbu805.setLevelId(rs.getLong(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LEVEL_ID]));
            entLkpbu805.setEducationId(rs.getLong(PstLkpbu805.fieldNames[PstLkpbu805.FLD_EDUCATION_ID]));
            entLkpbu805.setEmpCategoryId(rs.getLong(PstLkpbu805.fieldNames[PstLkpbu805.FLD_EMP_CATEGORY_ID]));
            entLkpbu805.setLkpbu805YearRealisasi(rs.getFloat(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_YEAR_REALISASI]));
            entLkpbu805.setLkpbu805YearPrediksi1(rs.getFloat(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_YEAR_PREDIKSI_1]));
            entLkpbu805.setLkpbu805YearPrediksi2(rs.getFloat(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_YEAR_PREDIKSI_2]));
            entLkpbu805.setLkpbu805YearPrediksi3(rs.getFloat(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_YEAR_PREDIKSI_3]));
            entLkpbu805.setLkpbu805YearPrediksi4(rs.getFloat(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_YEAR_PREDIKSI_4]));
        } catch (Exception e) {
        }
    }
    
    public static void resultToObjectJoin(ResultSet rs, Lkpbu805 entLkpbu805) {
        try {
            entLkpbu805.setOID(rs.getLong(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_ID]));
            entLkpbu805.setLevelId(rs.getLong(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LEVEL_ID]));
            entLkpbu805.setEducationId(rs.getLong(PstLkpbu805.fieldNames[PstLkpbu805.FLD_EDUCATION_ID]));
            entLkpbu805.setEmpCategoryId(rs.getLong(PstLkpbu805.fieldNames[PstLkpbu805.FLD_EMP_CATEGORY_ID]));
            entLkpbu805.setLevelCode(rs.getString("level_code"));
            entLkpbu805.setEducationCode(rs.getString(PstEducation.fieldNames[PstEducation.FLD_EDUCATION_LEVEL]));
            entLkpbu805.setEmpCategoryCode(rs.getString("cat_code"));
            entLkpbu805.setLkpbu805YearRealisasi(rs.getFloat(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_YEAR_REALISASI]));
            entLkpbu805.setLkpbu805YearPrediksi1(rs.getFloat(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_YEAR_PREDIKSI_1]));
            entLkpbu805.setLkpbu805YearPrediksi2(rs.getFloat(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_YEAR_PREDIKSI_2]));
            entLkpbu805.setLkpbu805YearPrediksi3(rs.getFloat(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_YEAR_PREDIKSI_3]));
            entLkpbu805.setLkpbu805YearPrediksi4(rs.getFloat(PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_YEAR_PREDIKSI_4]));
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long lkpbuId) {
        DBResultSet dbrs = null;
        boolean result = false;
        
        try {
            String sql = "SELECT * FROM " + TBL_LKPBU_805 + " WHERE " +
                            PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_ID] +
                            " = " + lkpbuId;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()) {
                result = true;
            }
            rs.close();
        } catch(Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    public static int getCount(String whereClause){
		com.dimata.qdep.db.DBResultSet dbrs = null;
		try {
			String sql = "SELECT COUNT("+ PstLkpbu805.fieldNames[PstLkpbu805.FLD_LKPBU_805_ID] + ") FROM " + TBL_LKPBU_805;
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;

			dbrs = com.dimata.qdep.db.DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			int count = 0;
			while(rs.next()) { count = rs.getInt(1); }

			rs.close();
			return count;
		}catch(Exception e) {
			return 0;
		}finally {
			com.dimata.qdep.db.DBResultSet.close(dbrs);
		}
	}
}
