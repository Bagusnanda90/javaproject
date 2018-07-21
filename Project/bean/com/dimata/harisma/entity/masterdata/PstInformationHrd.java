
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.entity.masterdata;

/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

public class PstInformationHrd extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HRD_INFORMATION = "hr_hrd_information";//"HR_EMP_CATEGORY";
    public static final int FLD_INFORMATION_HRD_ID = 0;
    public static final int FLD_NAMA_INFORMATION = 1;
    public static final int FLD_DATE_START = 2;
    public static final int FLD_DATE_END = 3;
    public static final String[] fieldNames = {
        "INFORMATION_HRD_ID",
        "NAMA_INFORMATION",
        "DATE_START",
        "DATE_END"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,};

    public PstInformationHrd() {
    }

    public PstInformationHrd(int i) throws DBException {
        super(new PstInformationHrd());
    }

    public PstInformationHrd(String sOid) throws DBException {
        super(new PstInformationHrd(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstInformationHrd(long lOid) throws DBException {
        super(new PstInformationHrd(0));
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
        return TBL_HRD_INFORMATION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstInformationHrd().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        InformationHrd informationHrd = fetchExc(ent.getOID());
        ent = (Entity) informationHrd;
        return informationHrd.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((InformationHrd) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((InformationHrd) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static InformationHrd fetchExc(long oid) throws DBException {
        try {
            InformationHrd informationHrd = new InformationHrd();
            PstInformationHrd PstInformationHrd = new PstInformationHrd(oid);
            informationHrd.setOID(oid);

            informationHrd.setNamaInformation(PstInformationHrd.getString(FLD_NAMA_INFORMATION));
            informationHrd.setDtStartInfo(PstInformationHrd.getDate(FLD_DATE_START));
            informationHrd.setDtEndInfo(PstInformationHrd.getDate(FLD_DATE_END));

            return informationHrd;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstInformationHrd(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(InformationHrd informationHrd) throws DBException {
        try {
            PstInformationHrd PstInformationHrd = new PstInformationHrd(0);

            PstInformationHrd.setString(FLD_NAMA_INFORMATION, informationHrd.getNamaInformation());
            PstInformationHrd.setDate(FLD_DATE_START, informationHrd.getDtStartInfo());
            PstInformationHrd.setDate(FLD_DATE_END, informationHrd.getDtEndInfo());

            PstInformationHrd.insert();
            informationHrd.setOID(PstInformationHrd.getlong(FLD_INFORMATION_HRD_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstInformationHrd(0), DBException.UNKNOWN);
        }
        return informationHrd.getOID();
    }

    public static long updateExc(InformationHrd informationHrd) throws DBException {
        try {
            if (informationHrd.getOID() != 0) {
                PstInformationHrd PstInformationHrd = new PstInformationHrd(informationHrd.getOID());

                PstInformationHrd.setString(FLD_NAMA_INFORMATION, informationHrd.getNamaInformation());
                PstInformationHrd.setDate(FLD_DATE_START, informationHrd.getDtStartInfo());
                PstInformationHrd.setDate(FLD_DATE_END, informationHrd.getDtEndInfo());

                PstInformationHrd.update();
                return informationHrd.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstInformationHrd(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstInformationHrd PstInformationHrd = new PstInformationHrd(oid);
            PstInformationHrd.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstInformationHrd(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HRD_INFORMATION;
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
                InformationHrd informationHrd = new InformationHrd();
                resultToObject(rs, informationHrd);
                lists.add(informationHrd);
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

    private static void resultToObject(ResultSet rs, InformationHrd informationHrd) {
        try {
            informationHrd.setOID(rs.getLong(PstInformationHrd.fieldNames[PstInformationHrd.FLD_INFORMATION_HRD_ID]));
            informationHrd.setNamaInformation(rs.getString(PstInformationHrd.fieldNames[PstInformationHrd.FLD_NAMA_INFORMATION]));
            informationHrd.setDtEndInfo(rs.getDate(PstInformationHrd.fieldNames[PstInformationHrd.FLD_DATE_END]));
            informationHrd.setDtStartInfo(rs.getDate(PstInformationHrd.fieldNames[PstInformationHrd.FLD_DATE_START]));

        } catch (Exception e) {
        }
    }
    
    public static Vector listInformationOverlap(long oidInfo,Date selectedDateFrom, Date selectedDateTo, String order) {
         String whereClauseEmpTime = "";
         //melakukan cek jika selectedfrom dan enddate null
         Vector result= new Vector();
         String whereClauseReq = "("
         + "\""+Formater.formatDate(selectedDateTo,"yyyy-MM-dd 23:59")+"\" > "+PstInformationHrd.fieldNames[PstInformationHrd.FLD_DATE_START]+ " AND "+ PstInformationHrd.fieldNames[PstInformationHrd.FLD_DATE_END] + ">"+"\""+Formater.formatDate(selectedDateFrom,"yyyy-MM-dd 00:00")+"\""+")";
         if((selectedDateFrom != null) && (selectedDateTo != null)) {
            whereClauseEmpTime = whereClauseReq;
        }
       

        if(oidInfo!=0){
            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " "+fieldNames[FLD_INFORMATION_HRD_ID]
                                 + " <> '"+oidInfo+"'";
        }
        
        if((selectedDateFrom != null) && (selectedDateTo != null)){
         return list(0, 0, whereClauseEmpTime, order);
        }else{
            return result;
        }
     }

    public static boolean checkOID(long informationHrdId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HRD_INFORMATION + " WHERE "
                    + PstInformationHrd.fieldNames[PstInformationHrd.FLD_INFORMATION_HRD_ID] + " = " + informationHrdId;

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
            String sql = "SELECT COUNT(" + PstInformationHrd.fieldNames[PstInformationHrd.FLD_INFORMATION_HRD_ID] + ") FROM " + TBL_HRD_INFORMATION;
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
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String order) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, order);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    InformationHrd informationHrd = (InformationHrd) list.get(ls);
                    if (oid == informationHrd.getOID()) {
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

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + mdl;
        if (start == 0) {
            cmd = Command.FIRST;
        } else {
            if (start == (vectSize - recordToGet)) {
                cmd = Command.LAST;
            } else {
                start = start + recordToGet;
                if (start <= (vectSize - recordToGet)) {
                    cmd = Command.NEXT;
                    System.out.println("next.......................");
                } else {
                    start = start - recordToGet;
                    if (start > 0) {
                        cmd = Command.PREV;
                        System.out.println("prev.......................");
                    }
                }
            }
        }

        return cmd;
    }

    public static boolean checkMaster(long oid) {
        if (PstInformationHrd.checkOID(oid)) {
            return true;
        } else {
            return false;
        }
    }
}
