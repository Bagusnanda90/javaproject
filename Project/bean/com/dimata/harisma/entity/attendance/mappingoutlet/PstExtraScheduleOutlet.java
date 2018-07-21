/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance.mappingoutlet;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.entity.I_Document;
import com.dimata.qdep.entity.I_Persintent;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Satrya Ramayu
 */
public class PstExtraScheduleOutlet extends DBHandler implements I_DBType, I_Language, I_DBInterface, I_Persintent {

    public static final String TBL_EXTRA_SCHEDULE_OUTLET = "hr_extra_schedule_outlet";
    public static final int FLD_EXTRA_SCHEDULE_MAPPING_ID = 0;
    public static final int FLD_REQUEST_DATE_EXTRA_SCHEDULE = 1;
    public static final int FLD_COMPANY_ID = 2;
    public static final int FLD_DIVISION_ID = 3;
    public static final int FLD_DEPARTMENT_ID = 4;
    public static final int FLD_SECTION_ID = 5;
    public static final int FLD_COST_CENTER_ID = 6;
    public static final int FLD_EXTRA_SCHEDULE_ADJECTIVE = 7;
    public static final int FLD_EXTRA_SCHEDULE_NUMBER = 8;
    public static final int FLD_STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE = 9;
    public static final int FLD_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT = 10;
    public static final int FLD_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT = 11;
    public static final int FLD_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT = 12;
    public static final int FLD_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT_DATE = 13;
    public static final int FLD_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE = 14;
    public static final int FLD_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT_DATE = 15;
    public static final int FLD_COUNT_IDX = 16;
    public static final int FLD_FLAG_EMPLOYEE_SAVE = 17;
    public static final String[] fieldNames = {
        "EXTRA_SCHEDULE_MAPPING_ID",
        "REQUEST_DATE_EXTRA_SCHEDULE",
        "COMPANY_ID",
        "DIVISION_ID",
        "DEPARTMENT_ID",
        "SECTION_ID",
        "COST_CENTER_ID",
        "EXTRA_SCHEDULE_ADJECTIVE",
        "EXTRA_SCHEDULE_NUMBER",
        "STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE",
        "REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT",
        "REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT",
        "REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT",
        "REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT_DATE",
        "REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE",
        "REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT_DATE",
        "COUNT_IDX",
        "FLAG_EMPLOYEE_SAVE"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,//"EXTRA_SCHEDULE_MAPPING_ID",
        TYPE_DATE,//"REQUEST_DATE_EXTRA_SCHEDULE",
        TYPE_LONG,//"COMPANY_ID",
        TYPE_LONG,//"DIVISION_ID",
        TYPE_LONG,//"DEPARTMENT_ID",
        TYPE_LONG,//"SECTION_ID",
        TYPE_LONG,//"COST_CENTER_ID",
        TYPE_STRING,//"EXTRA_SCHEDULE_ADJECTIVE",
        TYPE_STRING,//"EXTRA_SCHEDULE_NUMBER",
        TYPE_INT,//"STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE",
        TYPE_LONG,//"REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT",
        TYPE_LONG,//"REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT",
        TYPE_LONG,//"REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT",
        TYPE_DATE,//"REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT_DATE",
        TYPE_DATE,//"REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE",
        TYPE_DATE,//"REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT_DATE",
        TYPE_INT,//COUNT_IDX
        TYPE_STRING
    };

    public static Vector getDocKeys() {
        Vector keys = new Vector();

        for (int i = 0; i < I_DocStatus.fieldDocumentStatus.length; i++) {
            keys.add(I_DocStatus.fieldDocumentStatus[i]);
        }

        return keys;
    }

    public static Vector getDocValues() {
        Vector values = new Vector();

        for (int i = 0; i < I_DocStatus.fieldDocumentStatusVal.length; i++) {
            values.add(String.valueOf(i));
        }

        return values;
    }

    public PstExtraScheduleOutlet() {
    }

    public PstExtraScheduleOutlet(int i) throws DBException {
        super(new PstExtraScheduleOutlet());
    }

    public PstExtraScheduleOutlet(String sOid) throws DBException {
        super(new PstExtraScheduleOutlet(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstExtraScheduleOutlet(long lOid) throws DBException {
        super(new PstExtraScheduleOutlet(0));
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

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstExtraScheduleOutlet().getClass().getName();
    }

    public String getTableName() {
        return TBL_EXTRA_SCHEDULE_OUTLET;
    }

   public long fetch(Entity ent) {
        ExtraScheduleOutlet extraScheduleOutlet = new ExtraScheduleOutlet();
        try {
            extraScheduleOutlet = fetchExc(ent.getOID());
        } catch (DBException ex) {
            System.out.println("Exc"+ex);
        }
        return extraScheduleOutlet.getOID();
    }

    public long update(Entity ent) {
        long oid=0;
        try {
            oid= updateExc((ExtraScheduleOutlet) ent);
        } catch (DBException ex) {
            System.out.println("Exc"+ex);
        }
        return oid;
    }

    public long delete(Entity ent) {
        long oid=0;
         if (ent == null) {
            try {
                oid = deleteExc(ent.getOID());
               
            } catch (DBException ex) {
                  System.out.println("Exc"+ex);
            }
        }
        return oid;
    }

    public long insert(Entity ent) {
        long oid=0;
        try {
            oid= insertExc((ExtraScheduleOutlet) ent);
        } catch (DBException ex) {
            System.out.println("Exc"+ex);
        }
         return oid;
    }
   

  
    public static ExtraScheduleOutlet fetchExc(long oid) throws DBException {
        try {
            ExtraScheduleOutlet extraScheduleOutlet = new ExtraScheduleOutlet();
            PstExtraScheduleOutlet pstExtraScheduleOutlet = new PstExtraScheduleOutlet(oid);
            extraScheduleOutlet.setOID(oid);

            extraScheduleOutlet.setRequestDate(pstExtraScheduleOutlet.getDate(FLD_REQUEST_DATE_EXTRA_SCHEDULE));
            extraScheduleOutlet.setNumberForm(pstExtraScheduleOutlet.getString(FLD_EXTRA_SCHEDULE_NUMBER));

            extraScheduleOutlet.setCompanyId(pstExtraScheduleOutlet.getlong(FLD_COMPANY_ID));
            extraScheduleOutlet.setDivisionId(pstExtraScheduleOutlet.getlong(FLD_DIVISION_ID));
            extraScheduleOutlet.setDepartmentId(pstExtraScheduleOutlet.getlong(FLD_DEPARTMENT_ID));
            extraScheduleOutlet.setSectionId(pstExtraScheduleOutlet.getlong(FLD_SECTION_ID));
            extraScheduleOutlet.setCountIdx(pstExtraScheduleOutlet.getInt(FLD_COUNT_IDX));
            extraScheduleOutlet.setCostCenterId(pstExtraScheduleOutlet.getlong(FLD_COST_CENTER_ID));
            extraScheduleOutlet.setDocStsForm(pstExtraScheduleOutlet.getInt(FLD_STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE));
            //update by satrya 2013-04-30
            extraScheduleOutlet.setExtraScheduleObjctive(pstExtraScheduleOutlet.getString(FLD_EXTRA_SCHEDULE_ADJECTIVE));
            extraScheduleOutlet.setEmpApprovall(pstExtraScheduleOutlet.getlong(FLD_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT));
            extraScheduleOutlet.setDtEmpApprovall(pstExtraScheduleOutlet.getDate(FLD_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT_DATE));
            extraScheduleOutlet.setEmpApprovall1(pstExtraScheduleOutlet.getlong(FLD_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT));
            extraScheduleOutlet.setDtEmpApprovall1(pstExtraScheduleOutlet.getDate(FLD_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE));
            extraScheduleOutlet.setEmpApprovall2(pstExtraScheduleOutlet.getlong(FLD_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT));
            extraScheduleOutlet.setDtEmpApprovall2(pstExtraScheduleOutlet.getDate(FLD_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT_DATE));
            extraScheduleOutlet.setFlagSaveEmployee(pstExtraScheduleOutlet.getString(FLD_FLAG_EMPLOYEE_SAVE));

            return extraScheduleOutlet;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstExtraScheduleOutlet(0), DBException.UNKNOWN);
        }
    }

    public synchronized static long insertExc(ExtraScheduleOutlet extraScheduleOutlet) throws DBException {
        try {
            PstExtraScheduleOutlet pstExtraScheduleOutlet = new PstExtraScheduleOutlet(0);
            ExtraScheduleOutlet ovMax = fetchOvertimeMaxNumber();
            if (ovMax != null && ovMax.getOID() != 0) {
                int n = 1;
                try {
                    n = (ovMax.getCountIdx()) + 1;
                } catch (Exception exc) {
                    // klu gagal
                    n = ovMax.getCountIdx() + 1; // 
                }
                extraScheduleOutlet.setNumberForm("Ex." + n);
            } else {
                extraScheduleOutlet.setNumberForm("Ex.1");
            }
            pstExtraScheduleOutlet.setDate(FLD_REQUEST_DATE_EXTRA_SCHEDULE, extraScheduleOutlet.getRequestDate());
            pstExtraScheduleOutlet.setString(FLD_EXTRA_SCHEDULE_NUMBER, extraScheduleOutlet.getNumberForm());
            pstExtraScheduleOutlet.setLong(FLD_COMPANY_ID, extraScheduleOutlet.getCompanyId());
            pstExtraScheduleOutlet.setLong(FLD_DIVISION_ID, extraScheduleOutlet.getDivisionId());
            pstExtraScheduleOutlet.setLong(FLD_DEPARTMENT_ID, extraScheduleOutlet.getDepartmentId());
            pstExtraScheduleOutlet.setLong(FLD_SECTION_ID, extraScheduleOutlet.getSectionId());
            pstExtraScheduleOutlet.setInt(FLD_COUNT_IDX, extraScheduleOutlet.getCountIdx());
            pstExtraScheduleOutlet.setLong(FLD_COST_CENTER_ID, extraScheduleOutlet.getCostCenterId());
            pstExtraScheduleOutlet.setInt(FLD_STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE, extraScheduleOutlet.getDocStsForm());

            pstExtraScheduleOutlet.setString(FLD_EXTRA_SCHEDULE_ADJECTIVE, extraScheduleOutlet.getExtraScheduleObjctive());
            pstExtraScheduleOutlet.setLong(FLD_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT, extraScheduleOutlet.getEmpApprovall());
            pstExtraScheduleOutlet.setDate(FLD_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT, extraScheduleOutlet.getDtEmpApprovall());

            pstExtraScheduleOutlet.setLong(FLD_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT, extraScheduleOutlet.getEmpApprovall1());
            pstExtraScheduleOutlet.setDate(FLD_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT, extraScheduleOutlet.getDtEmpApprovall1());

            pstExtraScheduleOutlet.setLong(FLD_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT, extraScheduleOutlet.getEmpApprovall2());
            pstExtraScheduleOutlet.setDate(FLD_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT, extraScheduleOutlet.getDtEmpApprovall2());
            
             pstExtraScheduleOutlet.setString(FLD_FLAG_EMPLOYEE_SAVE, extraScheduleOutlet.getFlagSaveEmployee());
            


            pstExtraScheduleOutlet.insert();
            extraScheduleOutlet.setOID(pstExtraScheduleOutlet.getlong(FLD_EXTRA_SCHEDULE_MAPPING_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstExtraScheduleOutlet(0), DBException.UNKNOWN);
        }
        return extraScheduleOutlet.getOID();
    }

    public static long updateExc(ExtraScheduleOutlet extraScheduleOutlet) throws DBException {
        try {
            if (extraScheduleOutlet.getOID() != 0) {
                if (extraScheduleOutlet.getNumberForm() == null || extraScheduleOutlet.getNumberForm().length() < 1) {
                    ExtraScheduleOutlet ovMax = fetchOvertimeMaxNumber();
                    if (ovMax != null && ovMax.getOID() != 0) {
                        int n = 1;
                        try {
                            n = (ovMax.getCountIdx()) + 1;
                        } catch (Exception exc) {
                            // klu gagal
                            n = ovMax.getCountIdx() + 1; // 
                        }
                        extraScheduleOutlet.setNumberForm("Ex." + n);
                    } else {
                        extraScheduleOutlet.setNumberForm("Ex.1");
                    }
                }
                PstExtraScheduleOutlet pstExtraScheduleOutlet = new PstExtraScheduleOutlet(extraScheduleOutlet.getOID());


                pstExtraScheduleOutlet.setDate(FLD_REQUEST_DATE_EXTRA_SCHEDULE, extraScheduleOutlet.getRequestDate());
                pstExtraScheduleOutlet.setString(FLD_EXTRA_SCHEDULE_NUMBER, extraScheduleOutlet.getNumberForm());
                pstExtraScheduleOutlet.setLong(FLD_COMPANY_ID, extraScheduleOutlet.getCompanyId());
                pstExtraScheduleOutlet.setLong(FLD_DIVISION_ID, extraScheduleOutlet.getDivisionId());
                pstExtraScheduleOutlet.setLong(FLD_DEPARTMENT_ID, extraScheduleOutlet.getDepartmentId());
                pstExtraScheduleOutlet.setLong(FLD_SECTION_ID, extraScheduleOutlet.getSectionId());
                pstExtraScheduleOutlet.setInt(FLD_COUNT_IDX, extraScheduleOutlet.getCountIdx());
                pstExtraScheduleOutlet.setLong(FLD_COST_CENTER_ID, extraScheduleOutlet.getCostCenterId());
                pstExtraScheduleOutlet.setInt(FLD_STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE, extraScheduleOutlet.getDocStsForm());

                pstExtraScheduleOutlet.setString(FLD_EXTRA_SCHEDULE_ADJECTIVE, extraScheduleOutlet.getExtraScheduleObjctive());
                pstExtraScheduleOutlet.setLong(FLD_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT, extraScheduleOutlet.getEmpApprovall());
                pstExtraScheduleOutlet.setDate(FLD_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT, extraScheduleOutlet.getDtEmpApprovall());

                pstExtraScheduleOutlet.setLong(FLD_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT, extraScheduleOutlet.getEmpApprovall1());
                pstExtraScheduleOutlet.setDate(FLD_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT, extraScheduleOutlet.getDtEmpApprovall1());

                pstExtraScheduleOutlet.setLong(FLD_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT, extraScheduleOutlet.getEmpApprovall2());
                pstExtraScheduleOutlet.setDate(FLD_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT, extraScheduleOutlet.getDtEmpApprovall2());
                
                pstExtraScheduleOutlet.setString(FLD_FLAG_EMPLOYEE_SAVE, extraScheduleOutlet.getFlagSaveEmployee());
                pstExtraScheduleOutlet.update();
               
                return extraScheduleOutlet.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstExtraScheduleOutlet(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstExtraScheduleOutlet pstExtraScheduleOutlet = new PstExtraScheduleOutlet(oid);
            pstExtraScheduleOutlet.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstExtraScheduleOutlet(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 1000, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_EXTRA_SCHEDULE_OUTLET;

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

            //System.out.println("sql::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                ExtraScheduleOutlet extraScheduleOutlet = new ExtraScheduleOutlet();
                resultToObject(rs, extraScheduleOutlet);
                lists.add(extraScheduleOutlet);
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
   

    /**
     * mencari oidMainExtraSchedule ( hanya salah satu saja )
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
    public static ExtraScheduleOutlet getExtraSchedule(int limitStart, int recordToGet, String whereClause, String order) {
         ExtraScheduleOutlet objExtraScheduleOutlet = new ExtraScheduleOutlet();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT so.* FROM "+ PstExtraScheduleOutlet.TBL_EXTRA_SCHEDULE_OUTLET + " AS so "
            + " INNER JOIN "+PstExtraScheduleOutletDetail.TBL_EXTRA_SCHEDULE_OUTLET_DETAIL 
            + " AS sod ON so."+PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_EXTRA_SCHEDULE_MAPPING_ID]+"=sod."+PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EXTRA_SCHEDULE_MAPPING_ID];
//WHERE so.`REQUEST_DATE_EXTRA_SCHEDULE`="2014-06-15" AND sod.`EMPLOYEE_ID` IN(1,2) AND so.`COMPANY_ID`=1 AND so.`DIVISION_ID`=1 AND so.`DEPARTMENT_ID`=1;;

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

            //System.out.println("sql::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
               //oidMainExtraSch = rs.getLong(PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_EXTRA_SCHEDULE_MAPPING_ID]);
                resultToObject(rs, objExtraScheduleOutlet);
            }
            rs.close();
          

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
              return objExtraScheduleOutlet;
        }
    }
    public static ExtraScheduleOutlet fetchOvertimeMaxNumber() {
        DBResultSet dbrs = null;
        ExtraScheduleOutlet extraScheduleOutlet = new ExtraScheduleOutlet();
        try {
            String sql = "SELECT * FROM " + TBL_EXTRA_SCHEDULE_OUTLET + "  ORDER BY " + fieldNames[FLD_COUNT_IDX] + " DESC LIMIT 0,1";
            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                resultToObject(rs, extraScheduleOutlet);
                //lists.add(extraScheduleOutlet);
            }
            rs.close();


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return extraScheduleOutlet;
        }

    }

    public static void resultToObject(ResultSet rs, ExtraScheduleOutlet extraScheduleOutlet) {
        try {
            extraScheduleOutlet.setOID(rs.getLong(PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_EXTRA_SCHEDULE_MAPPING_ID]));
            
            Date tm_req = DBHandler.convertDate(rs.getDate(PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_REQUEST_DATE_EXTRA_SCHEDULE]), rs.getTime(PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_REQUEST_DATE_EXTRA_SCHEDULE]));
            extraScheduleOutlet.setRequestDate(tm_req);
            extraScheduleOutlet.setNumberForm(rs.getString(PstExtraScheduleOutlet.fieldNames[FLD_EXTRA_SCHEDULE_NUMBER]));

            extraScheduleOutlet.setCompanyId(rs.getLong(PstExtraScheduleOutlet.fieldNames[FLD_COMPANY_ID]));
            extraScheduleOutlet.setDivisionId(rs.getLong(PstExtraScheduleOutlet.fieldNames[FLD_DIVISION_ID]));
            extraScheduleOutlet.setDepartmentId(rs.getLong(PstExtraScheduleOutlet.fieldNames[FLD_DEPARTMENT_ID]));
            extraScheduleOutlet.setSectionId(rs.getLong(PstExtraScheduleOutlet.fieldNames[FLD_SECTION_ID]));
            extraScheduleOutlet.setCountIdx(rs.getInt(PstExtraScheduleOutlet.fieldNames[FLD_COUNT_IDX]));
            extraScheduleOutlet.setCostCenterId(rs.getLong(PstExtraScheduleOutlet.fieldNames[FLD_COST_CENTER_ID]));
            extraScheduleOutlet.setDocStsForm(rs.getInt(PstExtraScheduleOutlet.fieldNames[FLD_STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE]));
            //update by satrya 2013-04-30
            extraScheduleOutlet.setExtraScheduleObjctive(rs.getString(PstExtraScheduleOutlet.fieldNames[FLD_EXTRA_SCHEDULE_ADJECTIVE]));
            extraScheduleOutlet.setEmpApprovall(rs.getLong(PstExtraScheduleOutlet.fieldNames[FLD_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT]));
            Date tm = DBHandler.convertDate(rs.getDate(PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT_DATE]), rs.getTime(PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT_DATE]));
            extraScheduleOutlet.setDtEmpApprovall(tm);
            extraScheduleOutlet.setEmpApprovall1(rs.getLong(PstExtraScheduleOutlet.fieldNames[FLD_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT]));
            Date tm1 = DBHandler.convertDate(rs.getDate(PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE]), rs.getTime(PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE]));
            extraScheduleOutlet.setDtEmpApprovall1(tm1);
            extraScheduleOutlet.setEmpApprovall2(rs.getLong(PstExtraScheduleOutlet.fieldNames[FLD_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT]));
            Date tm2 = DBHandler.convertDate(rs.getDate(PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT_DATE]), rs.getTime(PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT_DATE]));
            extraScheduleOutlet.setDtEmpApprovall2(tm2);
            
            extraScheduleOutlet.setFlagSaveEmployee(rs.getString(PstExtraScheduleOutlet.fieldNames[FLD_FLAG_EMPLOYEE_SAVE])); 
            
            
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long extraScheduleId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_EXTRA_SCHEDULE_OUTLET + " WHERE "
                    + PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_EXTRA_SCHEDULE_MAPPING_ID] + " = '" + extraScheduleId + "'";

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
            String sql = "SELECT COUNT(" + PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_EXTRA_SCHEDULE_MAPPING_ID] + ") FROM " + TBL_EXTRA_SCHEDULE_OUTLET;
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

    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    ExtraScheduleOutlet extraScheduleOutlet = (ExtraScheduleOutlet) list.get(ls);
                    if (oid == extraScheduleOutlet.getOID()) {
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

 

}
