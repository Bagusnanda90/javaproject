/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.location;

import com.dimata.harisma.entity.masterdata.Kecamatan;
import com.dimata.harisma.entity.masterdata.PstKecamatan;
import com.dimata.harisma.entity.payroll.PstPayGeneral;
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
 * @author Satrya Ramayu
 */
public class PstLocation  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    //public static final  String TBL_P2_LOCATION = "LOCATION";
    public static final String TBL_P2_LOCATION = "location";
    public static final int FLD_LOCATION_ID = 0;
    public static final int FLD_NAME = 1;
    public static final int FLD_CONTACT_ID = 2;
    public static final int FLD_DESCRIPTION = 3;
    public static final int FLD_CODE = 4;
    public static final int FLD_ADDRESS = 5;
    public static final int FLD_TELEPHONE = 6;
    public static final int FLD_FAX = 7;
    public static final int FLD_PERSON = 8;
    public static final int FLD_EMAIL = 9;
    public static final int FLD_TYPE = 10;
    public static final int FLD_PARENT_LOCATION_ID = 11;
    public static final int FLD_WEBSITE = 12;
    
    // tambahan untuk proses di prochain opie 13-06-2012
    public static final int FLD_SERVICE_PERCENT = 13;
    public static final int FLD_TAX_PERCENT = 14;
    
     // tambahan untuk proses di hanoman
    public static final int FLD_DEPARTMENT_ID = 15;
    public static final int FLD_USED_VAL = 16;
    public static final int FLD_SERVICE_VAL = 17;
    public static final int FLD_TAX_VALUE = 18;
    public static final int FLD_SERVICE_VAL_USD = 19;
    public static final int FLD_TAX_VALUE_USD = 20;
    public static final int FLD_REPORT_GROUP = 21;
    public static final int FLD_LOC_INDEX = 22;

    // tambah prochain add opie 03-06-2012
    public static final int FLD_TAX_SVC_DEFAULT= 23;
    public static final int FLD_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER=24;
      //add fitra 29-01-2014
    public static final int FLD_COMPANY_ID=25;
    //create by satrya 2014-02-27
    public static final int FLD_COLOR_LOCATION=26;
    //update by satrya 2014-03-21
    public static final int FLD_SUB_REGENCY_ID=27;
    

    public static final String[] fieldNames = {
        "LOCATION_ID",
        "NAME",
        "CONTACT_ID",
        "DESCRIPTION",
        "CODE",
        "ADDRESS",
        "TELEPHONE",
        "FAX",
        "PERSON",
        "EMAIL",
        "TYPE",
        "PARENT_ID",
        "WEBSITE",
        "SERVICE_PERCENTAGE",
        "TAX_PERCENTAGE",
        "DEPARTMENT_ID",
        "USED_VALUE",
        "SERVICE_VALUE",
        "TAX_VALUE",
        "SERVICE_VALUE_USD",
        "TAX_VALUE_USD",
        "REPORT_GROUP",
        "LOC_INDEX",
        
        //add opie prochain 13-06-2012
        "TAX_SVC_DEFAULT",
        "PERSENTASE_DISTRIBUTION_PURCHASE_ORDER",
        "COMPANY_ID",
        "COLOR_LOCATION",
        "SUB_REGENCY_ID"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_LONG + TYPE_FK,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG + TYPE_FK,
        TYPE_STRING,

        // INI DI GUNAKAN OLEH PROCHAIN 13-06-2012
        TYPE_FLOAT,
        TYPE_FLOAT,

        // INI DI GUNAKAN OLEH HANOMAN
        TYPE_LONG,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_INT,

        //add opie 13-06-2012
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG
    };
    
    public static final int TYPE_LOCATION_WAREHOUSE = 0;
    public static final int TYPE_LOCATION_STORE = 1;
    public static final int TYPE_LOCATION_CARGO = 2;
    public static final int TYPE_LOCATION_VENDOR = 3;
    public static final int TYPE_LOCATION_TRANSFER = 4;
    public static final int TYPE_GALLERY_CUSTOMER = 5;
    public static final int TYPE_GALLERY_CONSIGNOR = 6;
    public static final int TYPE_LOCATION_DEPARTMENT = 7;
    public static final int TYPE_LOCATION_PROJECT = 8;
    public static final String[] fieldLocationType = {
        "Warehouse",
        "Store",
        "Cargo",
        "Vendor",
        "Transfer",
        "Gallery Customer",
        "Gallery Consignor",
        "Department",
        "Project"
    };

     //add opie eyek 12-06-2012 untuk tax n service default
    public static final int TSD_INCLUDE_NOTCHANGABLE = 0;
    public static final int TSD_NOTINCLUDE_NOTCHANGABLE = 1;
    public static final int TSD_INCLUDE_CHANGABLE = 2;
    public static final int TSD_NOTINCLUDE_CHANGABLE =3;

    public static final String tsdNames[][] = {
        {"include - not changable", "notinclude - not changable", "include - changable", "not include - changable"},
        {"include - not changable", "notinclude - not changable", "include - changable", "not include - changable"}
    };

    public PstLocation() {
    }

    public PstLocation(int i) throws DBException {
        super(new PstLocation());
    }

    public PstLocation(String sOid) throws DBException {
        super(new PstLocation(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstLocation(long lOid) throws DBException {
        super(new PstLocation(0));
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
        return TBL_P2_LOCATION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstLocation().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        try {
            Location location = (Location) ent;
            long oid = ent.getOID();
            PstLocation pstLocation = new PstLocation(oid);
            location.setOID(oid);

            location.setName(pstLocation.getString(FLD_NAME));
            location.setContactId(pstLocation.getlong(FLD_CONTACT_ID));
            location.setDescription(pstLocation.getString(FLD_DESCRIPTION));
            location.setCode(pstLocation.getString(FLD_CODE));
            location.setAddress(pstLocation.getString(FLD_ADDRESS));
            location.setTelephone(pstLocation.getString(FLD_TELEPHONE));
            location.setFax(pstLocation.getString(FLD_FAX));
            location.setPerson(pstLocation.getString(FLD_PERSON));
            location.setEmail(pstLocation.getString(FLD_EMAIL));
            location.setType(pstLocation.getInt(FLD_TYPE));
            location.setParentLocationId(pstLocation.getlong(FLD_PARENT_LOCATION_ID));
            location.setWebsite(pstLocation.getString(FLD_WEBSITE));
            location.setLocIndex(pstLocation.getInt(FLD_LOC_INDEX));

            // ini untuk tambahan prochain 13-06-2012
            location.setServicePersen(pstLocation.getdouble(FLD_SERVICE_PERCENT));
            location.setTaxPersen(pstLocation.getdouble(FLD_TAX_PERCENT));

            // ini untuk hanoman
            location.setDepartmentId(pstLocation.getlong(FLD_DEPARTMENT_ID));
            location.setTypeBase(pstLocation.getInt(FLD_USED_VAL));
            location.setServiceValue(pstLocation.getdouble(FLD_SERVICE_VAL));
            location.setTaxValue(pstLocation.getdouble(FLD_TAX_VALUE));
            location.setServiceValueUsd(pstLocation.getdouble(FLD_SERVICE_VAL_USD));
            location.setTaxValueUsd(pstLocation.getInt(FLD_TAX_VALUE_USD));
            location.setReportGroup(pstLocation.getInt(FLD_REPORT_GROUP));

            //ini untuk prochain add opie 13-06-2012
            location.setTaxSvcDefault(pstLocation.getInt(FLD_TAX_SVC_DEFAULT));
            location.setPersentaseDistributionPurchaseOrder(pstLocation.getdouble(FLD_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER));
            //ad fitra
            location.setCompanyId(pstLocation.getlong(FLD_COMPANY_ID));
            
            location.setColorLocation(pstLocation.getString(FLD_COLOR_LOCATION));
            //location.setCompanyName(pstLocation.getString(FLD_COMPANY_NAME));
            //update by satrya 2014-03-21
            location.setSubRegencyId(pstLocation.getlong(FLD_SUB_REGENCY_ID));
            return location.getOID();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLocation(0), DBException.UNKNOWN);
        }
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((Location) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((Location) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static Location fetchExc(long oid) throws DBException {
        try {
            Location location = new Location();

            PstLocation pstLocation = new PstLocation(oid);
            location.setOID(oid);

            location.setName(pstLocation.getString(FLD_NAME));
            location.setContactId(pstLocation.getlong(FLD_CONTACT_ID));
            location.setDescription(pstLocation.getString(FLD_DESCRIPTION));
            location.setCode(pstLocation.getString(FLD_CODE));
            location.setAddress(pstLocation.getString(FLD_ADDRESS));
            location.setTelephone(pstLocation.getString(FLD_TELEPHONE));
            location.setFax(pstLocation.getString(FLD_FAX));
            location.setPerson(pstLocation.getString(FLD_PERSON));
            location.setEmail(pstLocation.getString(FLD_EMAIL));
            location.setType(pstLocation.getInt(FLD_TYPE));
            location.setParentLocationId(pstLocation.getlong(FLD_PARENT_LOCATION_ID));
            location.setWebsite(pstLocation.getString(FLD_WEBSITE));
            location.setLocIndex(pstLocation.getInt(FLD_LOC_INDEX));

            // ini untuk tambahan prochain add opie 13-06-2012
            location.setServicePersen(pstLocation.getdouble(FLD_SERVICE_PERCENT));
            location.setTaxPersen(pstLocation.getdouble(FLD_TAX_PERCENT));

             // ini untuk hanoman
            location.setDepartmentId(pstLocation.getlong(FLD_DEPARTMENT_ID));
            location.setTypeBase(pstLocation.getInt(FLD_USED_VAL));
            location.setServiceValue(pstLocation.getdouble(FLD_SERVICE_VAL));
            location.setTaxValue(pstLocation.getdouble(FLD_TAX_VALUE));
            location.setServiceValueUsd(pstLocation.getdouble(FLD_SERVICE_VAL_USD));
            location.setTaxValueUsd(pstLocation.getdouble(FLD_TAX_VALUE_USD));
            location.setReportGroup(pstLocation.getInt(FLD_REPORT_GROUP));

            //ini untuk prohchain add opie 13-06-2012
            location.setTaxSvcDefault(pstLocation.getInt(FLD_TAX_SVC_DEFAULT));
            location.setPersentaseDistributionPurchaseOrder(pstLocation.getdouble(FLD_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER));
              //add fitra 29-01-2014
               location.setCompanyId(pstLocation.getlong(FLD_COMPANY_ID));
            location.setColorLocation(pstLocation.getString(FLD_COLOR_LOCATION));
             location.setSubRegencyId(pstLocation.getlong(FLD_SUB_REGENCY_ID));
            String s = location.getName();
            return location;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLocation(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(Location location) throws DBException {
        try {
            PstLocation pstLocation = new PstLocation(0);

            pstLocation.setString(FLD_NAME, location.getName());
            pstLocation.setLong(FLD_CONTACT_ID, location.getContactId());
            pstLocation.setString(FLD_DESCRIPTION, location.getDescription());
            pstLocation.setString(FLD_CODE, location.getCode());
            pstLocation.setString(FLD_ADDRESS, location.getAddress());
            pstLocation.setString(FLD_TELEPHONE, location.getTelephone());
            pstLocation.setString(FLD_FAX, location.getFax());
            pstLocation.setString(FLD_PERSON, location.getPerson());
            pstLocation.setString(FLD_EMAIL, location.getEmail());
            pstLocation.setInt(FLD_TYPE, location.getType());
            pstLocation.setLong(FLD_PARENT_LOCATION_ID, location.getParentLocationId());
            pstLocation.setString(FLD_WEBSITE, location.getWebsite());
            pstLocation.setInt(FLD_LOC_INDEX, location.getLocIndex());

            // ini tmbahan untuk prochain add opie 13-06-2012
            pstLocation.setDouble(FLD_SERVICE_PERCENT, location.getServicePersen());
            pstLocation.setDouble(FLD_TAX_PERCENT, location.getTaxPersen());

            // ini hanya untuk di gunakan oleh hsnoman
            pstLocation.setLong(FLD_DEPARTMENT_ID, location.getDepartmentId());
            pstLocation.setInt(FLD_USED_VAL, location.getTypeBase());
            pstLocation.setDouble(FLD_SERVICE_VAL, location.getServiceValue());
            pstLocation.setDouble(FLD_TAX_VALUE, location.getTaxValue());
            pstLocation.setDouble(FLD_SERVICE_VAL_USD, location.getServiceValueUsd());
            pstLocation.setDouble(FLD_TAX_VALUE_USD, location.getTaxValueUsd());
            pstLocation.setInt(FLD_REPORT_GROUP, location.getReportGroup());

            //ini untuk prohchain add opie 13-06-2012
            pstLocation.setInt(FLD_TAX_SVC_DEFAULT, location.getTaxSvcDefault());
            pstLocation.setDouble(FLD_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER, location.getPersentaseDistributionPurchaseOrder());
              //add fitra 29-01-2014
            pstLocation.setLong(FLD_COMPANY_ID, location.getCompanyId());
            
            pstLocation.setString(FLD_COLOR_LOCATION, location.getColorLocation());
            
            pstLocation.setLong(FLD_SUB_REGENCY_ID, location.getSubRegencyId());
            pstLocation.insert();

            location.setOID(pstLocation.getlong(FLD_LOCATION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLocation(0), DBException.UNKNOWN);
        }
        return location.getOID();
    }

    public static long updateExc(Location location) throws DBException {
        try {
            if (location.getOID() != 0) {
                PstLocation pstLocation = new PstLocation(location.getOID());

                pstLocation.setString(FLD_NAME, location.getName());
                pstLocation.setLong(FLD_CONTACT_ID, location.getContactId());
                pstLocation.setString(FLD_DESCRIPTION, location.getDescription());
                pstLocation.setString(FLD_CODE, location.getCode());
                pstLocation.setString(FLD_ADDRESS, location.getAddress());
                pstLocation.setString(FLD_TELEPHONE, location.getTelephone());
                pstLocation.setString(FLD_FAX, location.getFax());
                pstLocation.setString(FLD_PERSON, location.getPerson());
                pstLocation.setString(FLD_EMAIL, location.getEmail());
                pstLocation.setInt(FLD_TYPE, location.getType());
                pstLocation.setLong(FLD_PARENT_LOCATION_ID, location.getParentLocationId());
                pstLocation.setString(FLD_WEBSITE, location.getWebsite());
                pstLocation.setInt(FLD_LOC_INDEX, location.getLocIndex());

                // ini tambahan prochain add opie 13-06-2012
                pstLocation.setDouble(FLD_SERVICE_PERCENT, location.getServicePersen());
                pstLocation.setDouble(FLD_TAX_PERCENT, location.getTaxPersen());

                // ini hanya untuk di gunakan oleh hanoman
                pstLocation.setLong(FLD_DEPARTMENT_ID, location.getDepartmentId());
                pstLocation.setInt(FLD_USED_VAL, location.getTypeBase());
                pstLocation.setDouble(FLD_SERVICE_VAL, location.getServiceValue());
                pstLocation.setDouble(FLD_TAX_VALUE, location.getTaxValue());
                pstLocation.setDouble(FLD_SERVICE_VAL_USD, location.getServiceValueUsd());
                pstLocation.setDouble(FLD_TAX_VALUE_USD, location.getTaxValueUsd());
                pstLocation.setInt(FLD_REPORT_GROUP, location.getReportGroup());

                //ini untuk prohchain add opie 13-06-2012
                pstLocation.setInt(FLD_TAX_SVC_DEFAULT, location.getTaxSvcDefault());
                pstLocation.setDouble(FLD_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER, location.getPersentaseDistributionPurchaseOrder());
                  //add fitra 29-01-2014
                pstLocation.setLong(FLD_COMPANY_ID, location.getCompanyId());
                
                pstLocation.setString(FLD_COLOR_LOCATION, location.getColorLocation());
                
                 pstLocation.setLong(FLD_SUB_REGENCY_ID, location.getSubRegencyId());
                pstLocation.update();

                return location.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLocation(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstLocation pstLocation = new PstLocation(oid);
            pstLocation.delete();
           
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLocation(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    //update by devin 2014-04-23
    public static Hashtable findLocation(){
        Hashtable result = new Hashtable();
        DBResultSet dbrs=null;
        try{
            String sql="SELECT * FROM "+TBL_P2_LOCATION;
            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs =dbrs.getResultSet();
            while(rs.next()){
                Location location= new Location();
                resultToObject(rs,location);
                result.put(""+location.getOID(), location);
            }
        }catch(Exception exc){
            
        }finally{
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    /**
     * filter location to sub regency
     * @return 
     */
    public static Hashtable getHashLocation(){
        Hashtable result = new Hashtable();
        DBResultSet dbrs=null;
        try{
            String sql="SELECT * FROM "+TBL_P2_LOCATION + " ORDER BY "+ fieldNames[FLD_SUB_REGENCY_ID]+" ASC ";
            
            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs =dbrs.getResultSet();
            LocationIdnNameList locationIdnNameList = new LocationIdnNameList();
            while(rs.next()){
                long subRegency = rs.getLong(PstLocation.fieldNames[PstLocation.FLD_SUB_REGENCY_ID]);
                if(locationIdnNameList.getSubRegencyId()!=subRegency){
                    locationIdnNameList = new LocationIdnNameList();
                    locationIdnNameList.setSubRegencyId(subRegency);
                    result.put(subRegency, locationIdnNameList);
                }
                locationIdnNameList.addLocationIDs(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]));
                locationIdnNameList.addLocationNames(rs.getString(PstLocation.fieldNames[PstLocation.FLD_NAME]));
                if(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID])==504404564171867438L){
                    boolean dd=true;
                }
//                Location location= new Location();
//                resultToObject(rs,location);
//                result.put(""+location.getOID(), location);
            }
        }catch(Exception exc){
            
        }finally{
            DBResultSet.close(dbrs);
            return result;
        }
    }
     //update by devin 2014-04-25
    /**
     * berfungsi untuk mencari id mana saja yang di checked
     * @return 
     */
    public static Hashtable findLocationChecked(int limitStart, int recordToGet, String whereClause, String order){
        Hashtable result = new Hashtable();
        DBResultSet dbrs=null;
        try{
            String sql="SELECT * FROM "+TBL_P2_LOCATION;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }
            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs =dbrs.getResultSet();
            while(rs.next()){
                Location location= new Location();
                resultToObject(rs,location);
                result.put(""+location.getOID(), "checked=\"checked\"");
            }
        }catch(Exception exc){
            
        }finally{
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null; 
        try {
            String sql = "SELECT * FROM " + TBL_P2_LOCATION;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            //System.out.println("List sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Location location = new Location();
                resultToObject(rs, location);
                lists.add(location);
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

//update by devin 2014-04-24
    /**
     * untuk menjoinkan tbl location dengan tbl kecamatan
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
    public static Vector listJoinWithRegency(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null; 
        try {
            String sql = "SELECT loc. *, kec."+PstKecamatan.fieldNames[PstKecamatan.FLD_NM_KECAMATAN]+" FROM " + TBL_P2_LOCATION+
                    " AS loc INNER JOIN "+PstKecamatan.TBL_HR_KECAMATAN+" AS kec ON loc."+PstLocation.fieldNames[PstLocation.FLD_SUB_REGENCY_ID]+
                    " = kec."+PstKecamatan.fieldNames[PstKecamatan.FLD_ID_KECAMATAN];
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            //System.out.println("List sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Location location = new Location();
                resultToObject(rs, location);
               
                location.setRegencyName(rs.getString(PstKecamatan.fieldNames[PstKecamatan.FLD_NM_KECAMATAN]));
                lists.add(location);
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
    //update by fitra
    /*untuk menjoinkan tabel lokasi dan nama perusahaan
     *
     */
    
     public static Vector listJoin(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT loc.*,com."+PstPayGeneral.fieldNames[PstPayGeneral.FLD_COMPANY_NAME]
                    +",kec."+PstKecamatan.fieldNames[PstKecamatan.FLD_NM_KECAMATAN]
                    +" FROM "
                    +TBL_P2_LOCATION+" AS loc INNER JOIN "+
                    PstPayGeneral.TBL_PAY_GENERAL+" AS com ON loc."+PstLocation.fieldNames[PstLocation.FLD_COMPANY_ID]+" = com."+PstPayGeneral.fieldNames[PstPayGeneral.FLD_GEN_ID]
                    + " LEFT JOIN "+PstKecamatan.TBL_HR_KECAMATAN + " AS kec ON loc."+PstLocation.fieldNames[PstLocation.FLD_SUB_REGENCY_ID]+"=kec."+PstKecamatan.fieldNames[PstKecamatan.FLD_ID_KECAMATAN];
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            //System.out.println("List sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Location location = new Location();
                resultToObjectJoin(rs, location);
                lists.add(location);
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

       //add fitra 29-01-2014
     private static void resultToObjectJoin(ResultSet rs, Location location) {
        try {
            location.setOID(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]));
            location.setName(rs.getString(PstLocation.fieldNames[PstLocation.FLD_NAME]));
            location.setContactId(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_CONTACT_ID]));
            location.setDescription(rs.getString(PstLocation.fieldNames[PstLocation.FLD_DESCRIPTION]));
            location.setCode(rs.getString(PstLocation.fieldNames[PstLocation.FLD_CODE]));
            location.setAddress(rs.getString(PstLocation.fieldNames[PstLocation.FLD_ADDRESS]));
            location.setTelephone(rs.getString(PstLocation.fieldNames[PstLocation.FLD_TELEPHONE]));
            location.setFax(rs.getString(PstLocation.fieldNames[PstLocation.FLD_FAX]));
            location.setPerson(rs.getString(PstLocation.fieldNames[PstLocation.FLD_PERSON]));
            location.setEmail(rs.getString(PstLocation.fieldNames[PstLocation.FLD_EMAIL]));
            location.setType(rs.getInt(PstLocation.fieldNames[PstLocation.FLD_TYPE]));
            location.setParentLocationId(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_PARENT_LOCATION_ID]));
            location.setWebsite(rs.getString(PstLocation.fieldNames[PstLocation.FLD_WEBSITE]));
            location.setLocIndex(rs.getInt(PstLocation.fieldNames[PstLocation.FLD_LOC_INDEX]));

            // ini digunakan prochain add opie 13-06-2012
            location.setServicePersen(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_SERVICE_PERCENT]));
            location.setTaxPersen(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_TAX_PERCENT]));

             // ini digunakan oleh hanoman
            location.setDepartmentId(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_DEPARTMENT_ID]));
            location.setTypeBase(rs.getInt(PstLocation.fieldNames[PstLocation.FLD_USED_VAL]));
            location.setServiceValue(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_SERVICE_VAL]));
            location.setTaxValue(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_TAX_VALUE]));
            location.setServiceValueUsd(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_SERVICE_VAL_USD]));
            location.setTaxValueUsd(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_TAX_VALUE_USD]));
            location.setReportGroup(rs.getInt(PstLocation.fieldNames[PstLocation.FLD_REPORT_GROUP]));

            //ini untuk prohchain add opie 13-06-2012
            location.setTaxSvcDefault(rs.getInt(PstLocation.fieldNames[PstLocation.FLD_TAX_SVC_DEFAULT]));
            location.setPersentaseDistributionPurchaseOrder(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER]));
              //add fitra 29-01-2014
            location.setCompanyId(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_COMPANY_ID]));
            location.setCompanyName(rs.getString(PstPayGeneral.fieldNames[PstPayGeneral.FLD_COMPANY_NAME]));
            
            location.setColorLocation(rs.getString(PstLocation.fieldNames[PstLocation.FLD_COLOR_LOCATION]));
            location.setSubRegencyId(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_SUB_REGENCY_ID]));
            location.setSubRegencyName(rs.getString("kec."+PstKecamatan.fieldNames[PstKecamatan.FLD_NM_KECAMATAN]));
        } catch (Exception e) {
        }
    }


    private static void resultToObject(ResultSet rs, Location location) {
        try {
            location.setOID(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]));
            location.setName(rs.getString(PstLocation.fieldNames[PstLocation.FLD_NAME]));
            location.setContactId(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_CONTACT_ID]));
            location.setDescription(rs.getString(PstLocation.fieldNames[PstLocation.FLD_DESCRIPTION]));
            location.setCode(rs.getString(PstLocation.fieldNames[PstLocation.FLD_CODE]));
            location.setAddress(rs.getString(PstLocation.fieldNames[PstLocation.FLD_ADDRESS]));
            location.setTelephone(rs.getString(PstLocation.fieldNames[PstLocation.FLD_TELEPHONE]));
            location.setFax(rs.getString(PstLocation.fieldNames[PstLocation.FLD_FAX]));
            location.setPerson(rs.getString(PstLocation.fieldNames[PstLocation.FLD_PERSON]));
            location.setEmail(rs.getString(PstLocation.fieldNames[PstLocation.FLD_EMAIL]));
            location.setType(rs.getInt(PstLocation.fieldNames[PstLocation.FLD_TYPE]));
            location.setParentLocationId(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_PARENT_LOCATION_ID]));
            location.setWebsite(rs.getString(PstLocation.fieldNames[PstLocation.FLD_WEBSITE]));
            location.setLocIndex(rs.getInt(PstLocation.fieldNames[PstLocation.FLD_LOC_INDEX]));

            // ini digunakan prochain add opie 13-06-2012
            location.setServicePersen(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_SERVICE_PERCENT]));
            location.setTaxPersen(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_TAX_PERCENT]));

             // ini digunakan oleh hanoman
            location.setDepartmentId(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_DEPARTMENT_ID]));
            location.setTypeBase(rs.getInt(PstLocation.fieldNames[PstLocation.FLD_USED_VAL]));
            location.setServiceValue(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_SERVICE_VAL]));
            location.setTaxValue(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_TAX_VALUE]));
            location.setServiceValueUsd(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_SERVICE_VAL_USD]));
            location.setTaxValueUsd(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_TAX_VALUE_USD]));
            location.setReportGroup(rs.getInt(PstLocation.fieldNames[PstLocation.FLD_REPORT_GROUP]));

            //ini untuk prohchain add opie 13-06-2012
            location.setTaxSvcDefault(rs.getInt(PstLocation.fieldNames[PstLocation.FLD_TAX_SVC_DEFAULT]));
            location.setPersentaseDistributionPurchaseOrder(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER]));
              //add fitra 29-01-2014
            location.setCompanyId(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_COMPANY_ID]));
            
            location.setColorLocation(rs.getString(PstLocation.fieldNames[PstLocation.FLD_COLOR_LOCATION]));
            location.setSubRegencyId(rs.getLong(PstLocation.fieldNames[PstLocation.FLD_SUB_REGENCY_ID]));
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long locationId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_P2_LOCATION + " WHERE " +
                    PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + " = " + locationId;

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

        }
        return result;
    }

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + ") FROM " + TBL_P2_LOCATION;
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



    //add by fitra 29-01-2014

    public static int getCountJoin(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + ") FROM " + TBL_P2_LOCATION+" AS loc INNER JOIN "+
                    PstPayGeneral.TBL_PAY_GENERAL+" AS com ON loc."+PstLocation.fieldNames[PstLocation.FLD_COMPANY_ID]+" = com."+PstPayGeneral.fieldNames[PstPayGeneral.FLD_GEN_ID]  ;
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
                    Location location = (Location) list.get(ls);
                    if (oid == location.getOID()) {
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

    //priska 2014-12-08
     public static String GetNamaLocation(Long whereClause) {

        String namanya= "";

        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstLocation.fieldNames[PstLocation.FLD_NAME]
                        + " FROM " + TBL_P2_LOCATION;
                sql = sql + " WHERE LOCATION_ID = " + whereClause;
           
            dbrs = DBHandler.execQueryResult(sql);//execute query sql
            ResultSet rs = dbrs.getResultSet();
          
            while (rs.next()) {
                namanya= rs.getString(1);//nilainya
            }
            rs.close();
            return namanya;
        } catch (Exception e) {
            return null;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    /** fungsi ini digunakan untuk mendapatkan list location dalam bentuk hashtable
     * create by: gwawan@dimata 26 Sep 2007
     * @param
     * @return Hashtable
     */
    public static Hashtable getHashListLocation() {
        Hashtable hash = new Hashtable();
        try {
            Vector vctLocation = PstLocation.list(0, 0, "", "");
            for (int i = 0; i < vctLocation.size(); i++) {
                Location location = (Location) vctLocation.get(i);
                hash.put(String.valueOf(location.getOID()), location.getName());
            }
        } catch (Exception e) {
            System.out.println("Exc. in hashListLocation: " + e.toString());
        }
        return hash;
    }
    
    public static Vector listLocationWithRegency() {
        Vector loc_value = new Vector(1, 1);
        Vector loc_key = new Vector(1, 1);
        //Vector listDept = new Vector(1, 1);
        LocationIdnNameList keyList = new LocationIdnNameList();
        Vector listSubRegency = PstKecamatan.listAll();
        Hashtable hashListLocation = PstLocation.getHashLocation();
            if (listSubRegency!=null && listSubRegency.size()>0) {
                for(int idx=0;idx<listSubRegency.size();idx++){
                      Kecamatan kecamatan = (Kecamatan)listSubRegency.get(idx);
                      if(String.valueOf(kecamatan.getNmKecamatan()).equalsIgnoreCase("Kuta Utara")){
                          boolean ddd=true;
                      }
                      loc_key.add("-" + kecamatan.getNmKecamatan() + "-");
                      loc_value.add(""+kecamatan.getOID());
                      if(hashListLocation!=null && hashListLocation.size()>0 && hashListLocation.containsKey(kecamatan.getOID())){
                           LocationIdnNameList locationIdnNameList = (LocationIdnNameList)hashListLocation.get(kecamatan.getOID());
                           if(locationIdnNameList!=null && locationIdnNameList.getLocationIDs()!=null && locationIdnNameList.getLocationNames()!=null){
                               for(int idxloc=0;idxloc<locationIdnNameList.getLocationIDs().size();idxloc++){
                                   loc_key.add("  "+locationIdnNameList.getLocationNames().get(idxloc));
                                   loc_value.add(""+locationIdnNameList.getLocationIDs().get(idxloc));
                               }
                           }
                      }
                }
            }    
        Vector listLocation = new Vector();
        listLocation.add(loc_value);
        listLocation.add(loc_key);
        return listLocation;

    }

    
}