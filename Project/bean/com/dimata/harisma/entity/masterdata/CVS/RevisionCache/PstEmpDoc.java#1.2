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
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class PstEmpDoc extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
 public static final String TBL_HR_EMP_DOC = "hr_emp_doc";  
 public static final int FLD_EMP_DOC_ID = 0; 
 public static final int FLD_DOC_MASTER_ID = 1 ; 
 public static final int FLD_DOC_TITLE = 2 ;
 public static final int FLD_REQUEST_DATE = 3 ;
 public static final int FLD_DOC_NUMBER = 4 ;
 public static final int FLD_DATE_OF_ISSUE = 5 ;
 public static final int FLD_PLAN_DATE_FROM = 6 ;
 public static final int FLD_PLAN_DATE_TO = 7 ;
 public static final int FLD_REAL_DATE_FROM = 8 ;
 public static final int FLD_REAL_DATE_TO = 9 ;
 public static final int FLD_OBJECTIVES = 10 ;
 public static final int FLD_DETAILS = 11 ;
 public static final int FLD_COUNTRY_ID = 12 ;
 public static final int FLD_PROVINCE_ID = 13 ;
 public static final int FLD_REGION_ID = 14 ;
 public static final int FLD_SUBREGION_ID = 15 ;
 public static final int FLD_GEO_ADDRESS = 16 ;

    public static final String[] fieldNames = {
       "EMP_DOC_ID",
 "DOC_MASTER_ID", 
 "DOC_TITLE",
 "REQUEST_DATE",
 "DOC_NUMBER",
 "DATE_OF_ISSUE",
 "PLAN_DATE_FROM",
 "PLAN_DATE_TO",
 "REAL_DATE_FROM",
 "REAL_DATE_TO",
 "OBJECTIVES",
 "DETAILS",
 "COUNTRY_ID",
 "PROVINCE_ID",
 "REGION_ID",
 "SUBREGION_ID",
 "GEO_ADDRESS"
            
            
            
            
            
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING
    };

   public PstEmpDoc() {
   }

    public PstEmpDoc(int i) throws DBException {
        super(new PstEmpDoc());
    }

    public PstEmpDoc(String sOid) throws DBException {
        super(new PstEmpDoc(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpDoc(long lOid) throws DBException {
        super(new PstEmpDoc(0));
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
        return TBL_HR_EMP_DOC;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpDoc().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        EmpDoc empDoc = fetchExc(ent.getOID());
        ent = (Entity) empDoc;
        return empDoc.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((EmpDoc) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((EmpDoc) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static EmpDoc fetchExc(long oid) throws DBException {
        try {
            EmpDoc empDoc = new EmpDoc();
            PstEmpDoc pstEmpDoc = new PstEmpDoc(oid);
            empDoc.setOID(oid);

            empDoc.setDoc_master_id(pstEmpDoc.getLong(FLD_DOC_MASTER_ID));
            empDoc.setDoc_title(pstEmpDoc.getString(FLD_DOC_TITLE));
            empDoc.setRequest_date(pstEmpDoc.getDate(FLD_REQUEST_DATE));
            empDoc.setDoc_number(pstEmpDoc.getString(FLD_DOC_NUMBER));
            empDoc.setDate_of_issue(pstEmpDoc.getDate(FLD_DATE_OF_ISSUE));
            empDoc.setPlan_date_from(pstEmpDoc.getDate(FLD_PLAN_DATE_FROM));
            empDoc.setPlan_date_to(pstEmpDoc.getDate(FLD_PLAN_DATE_TO));
            empDoc.setReal_date_from(pstEmpDoc.getDate(FLD_REAL_DATE_FROM));
            empDoc.setReal_date_to(pstEmpDoc.getDate(FLD_REAL_DATE_TO));
            empDoc.setObjectives(pstEmpDoc.getString(FLD_OBJECTIVES));
            empDoc.setDetails(pstEmpDoc.getString(FLD_DETAILS));
            empDoc.setCountry_id(pstEmpDoc.getLong(FLD_COUNTRY_ID));
            empDoc.setProvince_id(pstEmpDoc.getLong(FLD_PROVINCE_ID));
            empDoc.setRegion_id(pstEmpDoc.getLong(FLD_REGION_ID));
            empDoc.setSubregion_id(pstEmpDoc.getLong(FLD_SUBREGION_ID));
            empDoc.setGeo_address(pstEmpDoc.getString(FLD_GEO_ADDRESS));
             fetchGeoAddress(empDoc);

            return empDoc;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDoc(0), DBException.UNKNOWN);
        }
    }

    
    public static Hashtable fetchExcHashtable(long oid) throws DBException {
        try {
            Hashtable empDocH = new Hashtable();
            EmpDoc empDoc = new EmpDoc();
            PstEmpDoc pstEmpDoc = new PstEmpDoc(oid);
            empDoc.setOID(oid);

            empDoc.setDoc_master_id(pstEmpDoc.getLong(FLD_DOC_MASTER_ID));
            empDocH.put(PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_MASTER_ID], empDoc.getOID());
            empDoc.setDoc_title(pstEmpDoc.getString(FLD_DOC_TITLE));
            empDocH.put(PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_TITLE], (empDoc.getDoc_title() != null ? empDoc.getDoc_title() : "" ));
            empDoc.setRequest_date(pstEmpDoc.getDate(FLD_REQUEST_DATE));
            empDocH.put(PstEmpDoc.fieldNames[PstEmpDoc.FLD_REQUEST_DATE], (empDoc.getRequest_date() != null?empDoc.getRequest_date():"") );
           
            empDoc.setDoc_number(pstEmpDoc.getString(FLD_DOC_NUMBER));
            empDocH.put(PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_NUMBER], (empDoc.getDoc_number() != null ? empDoc.getDoc_number() : "" ));
            
            empDoc.setDate_of_issue(pstEmpDoc.getDate(FLD_DATE_OF_ISSUE));
            empDocH.put(PstEmpDoc.fieldNames[PstEmpDoc.FLD_DATE_OF_ISSUE], (empDoc.getDate_of_issue() != null?empDoc.getDate_of_issue():"") );
            
            empDoc.setPlan_date_from(pstEmpDoc.getDate(FLD_PLAN_DATE_FROM));
            empDocH.put(PstEmpDoc.fieldNames[PstEmpDoc.FLD_PLAN_DATE_FROM], (empDoc.getPlan_date_from() != null?empDoc.getPlan_date_from():"") );
            
            empDoc.setPlan_date_to(pstEmpDoc.getDate(FLD_PLAN_DATE_TO));
            empDocH.put(PstEmpDoc.fieldNames[PstEmpDoc.FLD_PLAN_DATE_TO], (empDoc.getPlan_date_to() != null?empDoc.getPlan_date_to():"") );
            empDoc.setReal_date_from(pstEmpDoc.getDate(FLD_REAL_DATE_FROM));
            empDocH.put(PstEmpDoc.fieldNames[PstEmpDoc.FLD_REAL_DATE_FROM], (empDoc.getReal_date_from() != null?empDoc.getReal_date_from():"") );
            empDoc.setReal_date_to(pstEmpDoc.getDate(FLD_REAL_DATE_TO));
            empDocH.put(PstEmpDoc.fieldNames[PstEmpDoc.FLD_REAL_DATE_TO], (empDoc.getReal_date_to() != null?empDoc.getReal_date_to():"") );
            empDoc.setObjectives(pstEmpDoc.getString(FLD_OBJECTIVES));
            empDocH.put(PstEmpDoc.fieldNames[PstEmpDoc.FLD_OBJECTIVES], (empDoc.getObjectives() != null?empDoc.getObjectives():"") );
            empDoc.setDetails(pstEmpDoc.getString(FLD_DETAILS));
            empDoc.setCountry_id(pstEmpDoc.getLong(FLD_COUNTRY_ID));
            empDoc.setProvince_id(pstEmpDoc.getLong(FLD_PROVINCE_ID));
            empDoc.setRegion_id(pstEmpDoc.getLong(FLD_REGION_ID));
            empDoc.setSubregion_id(pstEmpDoc.getLong(FLD_SUBREGION_ID));
            empDoc.setGeo_address(pstEmpDoc.getString(FLD_GEO_ADDRESS));
             fetchGeoAddress(empDoc);

            return empDocH;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDoc(0), DBException.UNKNOWN);
        }
    }
    
    public static long insertExc(EmpDoc empDoc) throws DBException {
        try {
            PstEmpDoc pstEmpDoc = new PstEmpDoc(0);

            pstEmpDoc.setLong(FLD_DOC_MASTER_ID, empDoc.getDoc_master_id());
            pstEmpDoc.setString(FLD_DOC_TITLE, empDoc.getDoc_title());
            pstEmpDoc.setDate(FLD_REQUEST_DATE, empDoc.getRequest_date());
            pstEmpDoc.setString(FLD_DOC_NUMBER, empDoc.getDoc_number());
            pstEmpDoc.setDate(FLD_DATE_OF_ISSUE, empDoc.getDate_of_issue());
            pstEmpDoc.setDate(FLD_PLAN_DATE_FROM, empDoc.getPlan_date_from());
            pstEmpDoc.setDate(FLD_PLAN_DATE_TO, empDoc.getPlan_date_to());
            pstEmpDoc.setDate(FLD_REAL_DATE_FROM, empDoc.getReal_date_from());
            pstEmpDoc.setDate(FLD_REAL_DATE_TO, empDoc.getReal_date_to());
            pstEmpDoc.setString(FLD_OBJECTIVES, empDoc.getObjectives());
            pstEmpDoc.setString(FLD_DETAILS, empDoc.getDetails());
            pstEmpDoc.setLong(FLD_COUNTRY_ID, empDoc.getCountry_id());
            pstEmpDoc.setLong(FLD_PROVINCE_ID, empDoc.getProvince_id());
            pstEmpDoc.setLong(FLD_REGION_ID, empDoc.getRegion_id());
            pstEmpDoc.setLong(FLD_SUBREGION_ID, empDoc.getSubregion_id());
            pstEmpDoc.setString(FLD_GEO_ADDRESS, empDoc.getGeo_address());
            
            
            
            
            pstEmpDoc.insert();
            empDoc.setOID(pstEmpDoc.getlong(FLD_EMP_DOC_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDoc(0), DBException.UNKNOWN);
        }
        return empDoc.getOID();
    }

    public static long updateExc(EmpDoc empDoc) throws DBException {
        try {
            if (empDoc.getOID() != 0) {
                PstEmpDoc pstEmpDoc = new PstEmpDoc(empDoc.getOID());

               pstEmpDoc.setLong(FLD_DOC_MASTER_ID, empDoc.getDoc_master_id());
            pstEmpDoc.setString(FLD_DOC_TITLE, empDoc.getDoc_title());
            pstEmpDoc.setDate(FLD_REQUEST_DATE, empDoc.getRequest_date());
            pstEmpDoc.setString(FLD_DOC_NUMBER, empDoc.getDoc_number());
            pstEmpDoc.setDate(FLD_DATE_OF_ISSUE, empDoc.getDate_of_issue());
            pstEmpDoc.setDate(FLD_PLAN_DATE_FROM, empDoc.getPlan_date_from());
            pstEmpDoc.setDate(FLD_PLAN_DATE_TO, empDoc.getPlan_date_to());
            pstEmpDoc.setDate(FLD_REAL_DATE_FROM, empDoc.getReal_date_from());
            pstEmpDoc.setDate(FLD_REAL_DATE_TO, empDoc.getReal_date_to());
            pstEmpDoc.setString(FLD_OBJECTIVES, empDoc.getObjectives());
            pstEmpDoc.setString(FLD_DETAILS, empDoc.getDetails());
            pstEmpDoc.setLong(FLD_COUNTRY_ID, empDoc.getCountry_id());
            pstEmpDoc.setLong(FLD_PROVINCE_ID, empDoc.getProvince_id());
            pstEmpDoc.setLong(FLD_REGION_ID, empDoc.getRegion_id());
            pstEmpDoc.setLong(FLD_SUBREGION_ID, empDoc.getSubregion_id());
            pstEmpDoc.setString(FLD_GEO_ADDRESS, empDoc.getGeo_address());
            

                pstEmpDoc.update();
                return empDoc.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDoc(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmpDoc pstEmpDoc = new PstEmpDoc(oid);
            pstEmpDoc.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpDoc(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC;
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
                EmpDoc empDoc = new EmpDoc();
                resultToObject(rs, empDoc);
                lists.add(empDoc);
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
    
    public static Vector listSpecial(int limitStart, int recordToGet, String whereClause, String order, searchEmpDoc seaDoc) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC;
            sql =sql + " WHERE 1=1 ";
            if (seaDoc.getDoc_master_id() > 0){
                sql =sql + " AND " + PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_MASTER_ID] + " = " + seaDoc.getDoc_master_id();
            }
            if (seaDoc.getDocTitle().length() > 0){
                sql =sql + " AND " + PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_TITLE] + " LIKE \"%" + seaDoc.getDocTitle() + "%\"";
            }
            if (seaDoc.getDocNumber().length() > 0){
                sql =sql + " AND " + PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_NUMBER] + " LIKE \"%" + seaDoc.getDocNumber() + "%\"";
            }
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " AND " + whereClause;
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
                EmpDoc empDoc = new EmpDoc();
                resultToObject(rs, empDoc);
                lists.add(empDoc);
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
    
        public static void fetchGeoAddress(EmpDoc empDoc) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        if ((empDoc == null) || (empDoc.getOID() == 0)) {
            return;
        }
        try {
            String sql =
                    "SELECT e." + fieldNames[FLD_EMP_DOC_ID]
                    + ", n." + PstNegara.fieldNames[PstNegara.FLD_NM_NEGARA] + " AS NEG "
                    + ", p." + PstProvinsi.fieldNames[PstProvinsi.FLD_NM_PROVINSI] + " AS PROV "
                    + ", k." + PstKabupaten.fieldNames[PstKabupaten.FLD_NM_KABUPATEN] + " AS KAB "
                    + ", c." + PstKecamatan.fieldNames[PstKecamatan.FLD_NM_KECAMATAN] + " AS KEC "
                    + " FROM " + TBL_HR_EMP_DOC + " e "
                    + " LEFT JOIN " + PstNegara.TBL_BKD_NEGARA + " n ON e." + fieldNames[FLD_COUNTRY_ID] + "=n." + PstNegara.fieldNames[PstNegara.FLD_ID_NEGARA]
                    + " LEFT JOIN " + PstProvinsi.TBL_HR_PROPINSI + " p ON e." + fieldNames[FLD_PROVINCE_ID] + "= p." + PstProvinsi.fieldNames[PstProvinsi.FLD_ID_PROVINSI]
                    + " LEFT JOIN " + PstKabupaten.TBL_HR_KABUPATEN + " k ON e." + fieldNames[FLD_REGION_ID] + " = k." + PstKabupaten.fieldNames[PstKabupaten.FLD_ID_KABUPATEN]
                    + " LEFT JOIN " + PstKecamatan.TBL_HR_KECAMATAN + " c ON e." + fieldNames[FLD_SUBREGION_ID] + "= c." + PstKecamatan.fieldNames[PstKecamatan.FLD_ID_KECAMATAN]
                    + " WHERE " + fieldNames[FLD_EMP_DOC_ID] + "=\"" + empDoc.getOID() + "\"";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                empDoc.setGeo_address(
                        "" + rs.getString("NEG") + ", "
                        + rs.getString("PROV") + ", "
                        + rs.getString("KAB") + ", "
                        + rs.getString("KEC"));
            }
            empDoc.setGeo_address(empDoc.getGeo_address().replaceAll("null", "-"));
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return;
    }
    
         public static String GetGeoAddress(EmpDoc empDoc) {
        Vector lists = new Vector();
        String geo = "";
        DBResultSet dbrs = null;
        if ((empDoc == null) || (empDoc.getOID() == 0)) {
            return null;
        }
        try {
            String sql =
                    "SELECT e." + fieldNames[FLD_EMP_DOC_ID]
                    + ", n." + PstNegara.fieldNames[PstNegara.FLD_NM_NEGARA] + " AS NEG "
                    + ", p." + PstProvinsi.fieldNames[PstProvinsi.FLD_NM_PROVINSI] + " AS PROV "
                    + ", k." + PstKabupaten.fieldNames[PstKabupaten.FLD_NM_KABUPATEN] + " AS KAB "
                    + ", c." + PstKecamatan.fieldNames[PstKecamatan.FLD_NM_KECAMATAN] + " AS KEC "
                    + " FROM " + TBL_HR_EMP_DOC + " e "
                    + " LEFT JOIN " + PstNegara.TBL_BKD_NEGARA + " n ON e." + fieldNames[FLD_COUNTRY_ID] + "=n." + PstNegara.fieldNames[PstNegara.FLD_ID_NEGARA]
                    + " LEFT JOIN " + PstProvinsi.TBL_HR_PROPINSI + " p ON e." + fieldNames[FLD_PROVINCE_ID] + "= p." + PstProvinsi.fieldNames[PstProvinsi.FLD_ID_PROVINSI]
                    + " LEFT JOIN " + PstKabupaten.TBL_HR_KABUPATEN + " k ON e." + fieldNames[FLD_REGION_ID] + " = k." + PstKabupaten.fieldNames[PstKabupaten.FLD_ID_KABUPATEN]
                    + " LEFT JOIN " + PstKecamatan.TBL_HR_KECAMATAN + " c ON e." + fieldNames[FLD_SUBREGION_ID] + "= c." + PstKecamatan.fieldNames[PstKecamatan.FLD_ID_KECAMATAN]
                    + " WHERE " + fieldNames[FLD_EMP_DOC_ID] + "=\"" + empDoc.getOID() + "\"";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                geo =  ("" + rs.getString("NEG") + ", " + rs.getString("PROV") + ", " + rs.getString("KAB") + ", " + rs.getString("KEC"));
            }
            geo = geo.replaceAll("null", "-");
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return geo;
    }
    
      public static void resultToObject(ResultSet rs, EmpDoc empDoc) {
        try {
            


            empDoc.setOID(rs.getLong(PstEmpDoc.fieldNames[PstEmpDoc.FLD_EMP_DOC_ID]));
            empDoc.setDoc_master_id(rs.getLong(PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_MASTER_ID]));
            empDoc.setDoc_title(rs.getString(PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_TITLE]));
            empDoc.setRequest_date(rs.getDate(PstEmpDoc.fieldNames[PstEmpDoc.FLD_REQUEST_DATE]));
            empDoc.setDoc_number(rs.getString(PstEmpDoc.fieldNames[PstEmpDoc.FLD_DOC_NUMBER]));
            empDoc.setDate_of_issue(rs.getDate(PstEmpDoc.fieldNames[PstEmpDoc.FLD_DATE_OF_ISSUE]));
            empDoc.setPlan_date_from(rs.getDate(PstEmpDoc.fieldNames[PstEmpDoc.FLD_PLAN_DATE_FROM]));
            empDoc.setPlan_date_to(rs.getDate(PstEmpDoc.fieldNames[PstEmpDoc.FLD_PLAN_DATE_TO]));
            empDoc.setReal_date_from(rs.getDate(PstEmpDoc.fieldNames[PstEmpDoc.FLD_REAL_DATE_FROM]));
            empDoc.setReal_date_to(rs.getDate(PstEmpDoc.fieldNames[PstEmpDoc.FLD_REAL_DATE_TO]));
            empDoc.setObjectives(rs.getString(PstEmpDoc.fieldNames[PstEmpDoc.FLD_OBJECTIVES]));
            empDoc.setDetails(rs.getString(PstEmpDoc.fieldNames[PstEmpDoc.FLD_DETAILS]));
            empDoc.setCountry_id(rs.getLong(PstEmpDoc.fieldNames[PstEmpDoc.FLD_COUNTRY_ID]));
            empDoc.setProvince_id(rs.getLong(PstEmpDoc.fieldNames[PstEmpDoc.FLD_PROVINCE_ID]));
            empDoc.setRegion_id(rs.getLong(PstEmpDoc.fieldNames[PstEmpDoc.FLD_REGION_ID]));
            empDoc.setSubregion_id(rs.getLong(PstEmpDoc.fieldNames[PstEmpDoc.FLD_SUBREGION_ID]));
            empDoc.setGeo_address(rs.getString(PstEmpDoc.fieldNames[PstEmpDoc.FLD_GEO_ADDRESS]));

            
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long empDocId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC + " WHERE "
                    + PstEmpDoc.fieldNames[PstEmpDoc.FLD_EMP_DOC_ID] + " = " + empDocId;

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
            String sql = "SELECT COUNT(" + PstEmpDoc.fieldNames[PstEmpDoc.FLD_EMP_DOC_ID] + ") FROM " + TBL_HR_EMP_DOC;
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
    

public static int findLimitCommand(int start, int recordToGet, int vectSize){
		 int cmd = Command.LIST;
		 int mdl = vectSize % recordToGet;
		 vectSize = vectSize + (recordToGet - mdl);
		 if(start == 0)
			 cmd =  Command.FIRST;
		 else{
			 if(start == (vectSize-recordToGet))
				 cmd = Command.LAST;
			 else{
				 start = start + recordToGet;
				 if(start <= (vectSize - recordToGet)){
					 cmd = Command.NEXT;
					 System.out.println("next.......................");
				 }else{
					 start = start - recordToGet;
					 if(start > 0){
						 cmd = Command.PREV; 
						 System.out.println("prev.......................");
					 } 
				 }
			 } 
		 }

		 return cmd;
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
                    EmpDoc empDoc = (EmpDoc) list.get(ls);
                    if (oid == empDoc.getOID()) {
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
