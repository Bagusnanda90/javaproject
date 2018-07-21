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
public class PstCompetitor extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_HR_COMPETITOR = "hr_competitor";
   public static final int FLD_COMPETITOR_ID = 0;
   public static final int FLD_COMPANY_NAME = 1;
   public static final int FLD_ADDRESS = 2;
   public static final int FLD_WEBSITE = 3;
   public static final int FLD_EMAIL = 4;
   public static final int FLD_TELEPHONE = 5;
   public static final int FLD_FAX = 6;
   public static final int FLD_CONTACT_PERSON = 7;
   public static final int FLD_DESCRIPTION = 8;
   public static final int FLD_COUNTRY_ID = 9;
   public static final int FLD_PROVINCE_ID = 10;
   public static final int FLD_REGION_ID = 11;
   public static final int FLD_SUBREGION_ID = 12;
   public static final int FLD_GEO_ADDRESS = 13;
   
    public static final String[] fieldNames = {
      "COMPETITOR_ID",
      "COMPANY_NAME",
      "ADDRESS",
      "WEBSITE",
      "EMAIL",
      "TELEPHONE",
      "FAX",
      "CONTACT_PERSON",
      "DESCRIPTION",
      "COUNTRY_ID",
      "PROVINCE_ID",
      "REGION_ID",
      "SUBREGION_ID",
      "GEO_ADDRESS"
    };
    public static final int[] fieldTypes = {
      TYPE_LONG + TYPE_PK + TYPE_ID,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_LONG,
      TYPE_LONG,
      TYPE_LONG,
      TYPE_LONG,
      TYPE_STRING
    };

   public PstCompetitor() {
   }

    public PstCompetitor(int i) throws DBException {
        super(new PstCompetitor());
    }

    public PstCompetitor(String sOid) throws DBException {
        super(new PstCompetitor(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstCompetitor(long lOid) throws DBException {
        super(new PstCompetitor(0));
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
        return TBL_HR_COMPETITOR;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstCompetitor().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        Competitor competitor = fetchExc(ent.getOID());
        ent = (Entity) competitor;
        return competitor.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((Competitor) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((Competitor) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static Competitor fetchExc(long oid) throws DBException {
        try {
         
         Competitor competitor = new Competitor();
         PstCompetitor pstCompetitor = new PstCompetitor(oid);
         competitor.setOID(oid);
         competitor.setCompetitorId(pstCompetitor.getlong(FLD_COMPETITOR_ID));
         competitor.setCompanyName(pstCompetitor.getString(FLD_COMPANY_NAME));
         competitor.setAddress(pstCompetitor.getString(FLD_ADDRESS));
         competitor.setWebsite(pstCompetitor.getString(FLD_WEBSITE));
         competitor.setEmail(pstCompetitor.getString(FLD_EMAIL));
         competitor.setTelephone(pstCompetitor.getString(FLD_TELEPHONE));
         competitor.setFax(pstCompetitor.getString(FLD_FAX));
         competitor.setContact_person(pstCompetitor.getString(FLD_CONTACT_PERSON));
         competitor.setDescription(pstCompetitor.getString(FLD_DESCRIPTION));
         competitor.setCountryId(pstCompetitor.getlong(FLD_COUNTRY_ID));
         competitor.setProvinceId(pstCompetitor.getlong(FLD_PROVINCE_ID));
         competitor.setRegionId(pstCompetitor.getlong(FLD_REGION_ID));
         competitor.setSubregionId(pstCompetitor.getlong(FLD_SUBREGION_ID));
         competitor.setGeoAddress(pstCompetitor.getString(FLD_GEO_ADDRESS));
 
            return competitor;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetitor(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(Competitor competitor) throws DBException {
        try {
            PstCompetitor pstCompetitor = new PstCompetitor(0);
            
          
            pstCompetitor.setString(FLD_COMPANY_NAME, competitor.getCompanyName());
            pstCompetitor.setString(FLD_ADDRESS, competitor.getAddress());
            pstCompetitor.setString(FLD_WEBSITE, competitor.getWebsite());
            pstCompetitor.setString(FLD_EMAIL, competitor.getEmail());
            pstCompetitor.setString(FLD_TELEPHONE, competitor.getTelephone());
            pstCompetitor.setString(FLD_FAX, competitor.getFax());
            pstCompetitor.setString(FLD_CONTACT_PERSON, competitor.getContact_person());
            pstCompetitor.setString(FLD_DESCRIPTION, competitor.getDescription());
            pstCompetitor.setLong(FLD_COUNTRY_ID, competitor.getCountryId());
            pstCompetitor.setLong(FLD_PROVINCE_ID, competitor.getProvinceId());
            pstCompetitor.setLong(FLD_REGION_ID, competitor.getRegionId());
            pstCompetitor.setLong(FLD_SUBREGION_ID, competitor.getSubregionId());
            pstCompetitor.setString(FLD_GEO_ADDRESS, competitor.getGeoAddress());
          
            pstCompetitor.insert();
            competitor.setOID(pstCompetitor.getlong(FLD_COMPETITOR_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetitor(0), DBException.UNKNOWN);
        }
        return competitor.getOID();
    }

    public static long updateExc(Competitor competitor) throws DBException {
        try {
            if (competitor.getOID() != 0) {
                PstCompetitor pstCompetitor = new PstCompetitor(competitor.getOID());

            pstCompetitor.setLong(FLD_COMPETITOR_ID, competitor.getCompetitorId());
            pstCompetitor.setString(FLD_COMPANY_NAME, competitor.getCompanyName());
            pstCompetitor.setString(FLD_ADDRESS, competitor.getAddress());
            pstCompetitor.setString(FLD_WEBSITE, competitor.getWebsite());
            pstCompetitor.setString(FLD_EMAIL, competitor.getEmail());
            pstCompetitor.setString(FLD_TELEPHONE, competitor.getTelephone());
            pstCompetitor.setString(FLD_FAX, competitor.getFax());
            pstCompetitor.setString(FLD_CONTACT_PERSON, competitor.getContact_person());
            pstCompetitor.setString(FLD_DESCRIPTION, competitor.getDescription());
            pstCompetitor.setLong(FLD_COUNTRY_ID, competitor.getCountryId());
            pstCompetitor.setLong(FLD_PROVINCE_ID, competitor.getProvinceId());
            pstCompetitor.setLong(FLD_REGION_ID, competitor.getRegionId());
            pstCompetitor.setLong(FLD_SUBREGION_ID, competitor.getSubregionId());
            pstCompetitor.setString(FLD_GEO_ADDRESS, competitor.getGeoAddress());

                pstCompetitor.update();
                return competitor.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetitor(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstCompetitor pstCompetitor = new PstCompetitor(oid);
            pstCompetitor.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCompetitor(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_COMPETITOR;
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
                Competitor competitor = new Competitor();
                resultToObject(rs, competitor);
                lists.add(competitor);
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
    
      public static void resultToObject(ResultSet rs, Competitor competitor) {
        try {
            competitor.setCompetitorId(rs.getLong(PstCompetitor.fieldNames[PstCompetitor.FLD_COMPETITOR_ID]));
            competitor.setCompanyName(rs.getString(PstCompetitor.fieldNames[PstCompetitor.FLD_COMPANY_NAME]));
            competitor.setAddress(rs.getString(PstCompetitor.fieldNames[PstCompetitor.FLD_ADDRESS]));
            competitor.setWebsite(rs.getString(PstCompetitor.fieldNames[PstCompetitor.FLD_WEBSITE]));
            competitor.setEmail(rs.getString(PstCompetitor.fieldNames[PstCompetitor.FLD_EMAIL]));
            competitor.setTelephone(rs.getString(PstCompetitor.fieldNames[PstCompetitor.FLD_TELEPHONE]));
            competitor.setFax(rs.getString(PstCompetitor.fieldNames[PstCompetitor.FLD_FAX]));
            competitor.setContact_person(rs.getString(PstCompetitor.fieldNames[PstCompetitor.FLD_CONTACT_PERSON]));
            competitor.setDescription(rs.getString(PstCompetitor.fieldNames[PstCompetitor.FLD_DESCRIPTION]));
            competitor.setCountryId(rs.getLong(PstCompetitor.fieldNames[PstCompetitor.FLD_COUNTRY_ID]));
            competitor.setProvinceId(rs.getLong(PstCompetitor.fieldNames[PstCompetitor.FLD_PROVINCE_ID]));
            competitor.setRegionId(rs.getLong(PstCompetitor.fieldNames[PstCompetitor.FLD_REGION_ID]));
            competitor.setSubregionId(rs.getLong(PstCompetitor.fieldNames[PstCompetitor.FLD_SUBREGION_ID]));
            competitor.setGeoAddress(rs.getString(PstCompetitor.fieldNames[PstCompetitor.FLD_GEO_ADDRESS]));
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long competitorId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_COMPETITOR + " WHERE "
                    + PstCompetitor.fieldNames[PstCompetitor.FLD_COMPETITOR_ID] + " = " + competitorId;

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
            String sql = "SELECT COUNT(" + PstCompetitor.fieldNames[PstCompetitor.FLD_COMPETITOR_ID] + ") FROM " + TBL_HR_COMPETITOR;
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
                    Competitor competitor = (Competitor) list.get(ls);
                    if (oid == competitor.getOID()) {
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
