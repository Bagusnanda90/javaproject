
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: Satrya Ramayu
 * @version  	: 01
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

/* package java */
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.entity.*;

//Gede_7Feb2012 {
import com.dimata.harisma.session.employee.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.entity.masterdata.*;
//}

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.utility.harisma.machine.db.DBException;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.db.DBResultSet;
import com.dimata.harisma.utility.harisma.machine.db.I_DBInterface;
import com.dimata.harisma.utility.harisma.machine.db.I_DBType;

/**
 * Ari_20111002
 * Menambah Company, Division, Level dan EmpCategory
 * @author Wiweka
 */
public class PstLocationDestopTransfer extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
 
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

    public PstLocationDestopTransfer() {
    }

    public PstLocationDestopTransfer(int i) throws DBException {
        super(new PstLocationDestopTransfer());
    }

    public PstLocationDestopTransfer(String sOid) throws DBException {
        super(new PstLocationDestopTransfer(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstLocationDestopTransfer(long lOid) throws DBException {
        super(new PstLocationDestopTransfer(0));
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
        return new PstLocationDestopTransfer().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
       return 0;
    }

    public long insertExc(Entity ent) throws Exception {
        return 0;
    }

    public long updateExc(Entity ent) throws Exception {
        return 0;
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static long insertExc(TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData) throws DBException {
        try {
            PstLocationDestopTransfer pstPositionDestopTransfer = new PstLocationDestopTransfer(0);

            pstPositionDestopTransfer.setString(FLD_NAME, tabelEmployeeOutletTransferData.getLocationName());
            pstPositionDestopTransfer.setString(FLD_CODE, tabelEmployeeOutletTransferData.getLocationCode());
            
            pstPositionDestopTransfer.insert(tabelEmployeeOutletTransferData.getLocationId());
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLocationDestopTransfer(0), DBException.UNKNOWN);
        }
        return tabelEmployeeOutletTransferData.getWorkHistoryId();
    }

    public static long updateExc(TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData) throws DBException {
        try {
            if (tabelEmployeeOutletTransferData.getWorkHistoryId() != 0) {
                PstLocationDestopTransfer pstPositionDestopTransfer = new PstLocationDestopTransfer(tabelEmployeeOutletTransferData.getLocationId());

               
               pstPositionDestopTransfer.setString(FLD_NAME, tabelEmployeeOutletTransferData.getLocationName());
            pstPositionDestopTransfer.setString(FLD_CODE, tabelEmployeeOutletTransferData.getLocationCode());
            

                pstPositionDestopTransfer.update();
                return tabelEmployeeOutletTransferData.getLocationId();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLocationDestopTransfer(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstLocationDestopTransfer pstCareerPath = new PstLocationDestopTransfer(oid);
            pstCareerPath.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLocationDestopTransfer(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static boolean checkOID(long locationId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_P2_LOCATION + " WHERE "
                    + PstLocationDestopTransfer.fieldNames[PstLocationDestopTransfer.FLD_LOCATION_ID] + " = " + locationId;

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
    public static Hashtable<String,Boolean> hashLocationSdhAda(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashLocationSdhAda= new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "+fieldNames[FLD_LOCATION_ID]+" FROM " + TBL_P2_LOCATION;
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
                hashLocationSdhAda.put(""+rs.getLong(fieldNames[FLD_LOCATION_ID]), true);
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashLocationSdhAda;
        }
        
    }
}
