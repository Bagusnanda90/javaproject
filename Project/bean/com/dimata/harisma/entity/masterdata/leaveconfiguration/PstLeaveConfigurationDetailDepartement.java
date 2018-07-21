/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Ari_20110930
 * @version  	:  [version]
 */
/*******************************************************************
 * Class Description 	: PstLeaveConfigurationMain
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/
package com.dimata.harisma.entity.masterdata.leaveconfiguration;

/**
 *
 * @author Wiweka
 */
/* package java */
import com.dimata.harisma.entity.masterdata.*;
import java.sql.*;
import java.util.*;


/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

public class PstLeaveConfigurationDetailDepartement extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_LEAVE_CONFIGURATION_MAIN_DEPARTEMENT = "hr_leave_configuration_main_departement";//"hr_company";
    public static final int FLD_LEAVE_CONFIGURATION_MAIN_ID = 0;
    public static final int FLD_DEPARTEMENT_ID = 1;
    public static final int FLD_SECTION_ID = 2;
    
    public static final String[] fieldNames = {
        "LEAVE_CONFIGURATION_MAIN_ID",
        "DEPARTEMENT_ID",
        "SECTION_ID",
    };
    public static final int[] fieldTypes = {
        TYPE_PK + TYPE_FK + TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
    };

    public PstLeaveConfigurationDetailDepartement() {
    }

    public PstLeaveConfigurationDetailDepartement(int i) throws DBException {
        super(new PstLeaveConfigurationDetailDepartement());
    }

    public PstLeaveConfigurationDetailDepartement(String sOid) throws DBException {
        super(new PstLeaveConfigurationDetailDepartement(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstLeaveConfigurationDetailDepartement(long lOid) throws DBException {
        super(new PstLeaveConfigurationDetailDepartement(0));
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
        return TBL_HR_LEAVE_CONFIGURATION_MAIN_DEPARTEMENT;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstLeaveConfigurationDetailDepartement().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        LeaveConfigurationDetailDepartement leConfigurationDetailDepartement = fetchExc(ent.getOID());
        ent = (Entity) leConfigurationDetailDepartement;
        return leConfigurationDetailDepartement.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((LeaveConfigurationDetailDepartement) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((LeaveConfigurationDetailDepartement) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static LeaveConfigurationDetailDepartement fetchExc(long oid) throws DBException {
        try {
            LeaveConfigurationDetailDepartement leConfigurationDetailDepartement = new LeaveConfigurationDetailDepartement();
            PstLeaveConfigurationDetailDepartement pstLeaveConfigurationMain = new PstLeaveConfigurationDetailDepartement(oid);
            leConfigurationDetailDepartement.setOID(oid);

            leConfigurationDetailDepartement.setDepartementId(pstLeaveConfigurationMain.getlong(FLD_DEPARTEMENT_ID));
            leConfigurationDetailDepartement.setSectionId(pstLeaveConfigurationMain.getlong(FLD_SECTION_ID));
            leConfigurationDetailDepartement.setLeaveConfigurationMainId(pstLeaveConfigurationMain.getlong(FLD_LEAVE_CONFIGURATION_MAIN_ID));

            return leConfigurationDetailDepartement;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationDetailDepartement(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(LeaveConfigurationDetailDepartement leConfigurationDetailDepartement) throws DBException {
        try {
            PstLeaveConfigurationDetailDepartement pstLeaveConfigurationMain = new PstLeaveConfigurationDetailDepartement(0);

            pstLeaveConfigurationMain.setLong(FLD_DEPARTEMENT_ID, leConfigurationDetailDepartement.getDepartementId());
            pstLeaveConfigurationMain.setLong(FLD_SECTION_ID, leConfigurationDetailDepartement.getSectionId());
            

            pstLeaveConfigurationMain.insert();
            leConfigurationDetailDepartement.setOID(pstLeaveConfigurationMain.getlong(FLD_LEAVE_CONFIGURATION_MAIN_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationDetailDepartement(0), DBException.UNKNOWN);
        }
        return leConfigurationDetailDepartement.getOID();
    }

    public static long updateExc(LeaveConfigurationDetailDepartement leConfigurationDetailDepartement) throws DBException {
        try {
            if (leConfigurationDetailDepartement.getOID() != 0) {
                PstLeaveConfigurationDetailDepartement pstLeaveConfigurationMain = new PstLeaveConfigurationDetailDepartement(leConfigurationDetailDepartement.getOID());

                pstLeaveConfigurationMain.setLong(FLD_DEPARTEMENT_ID, leConfigurationDetailDepartement.getDepartementId());
                pstLeaveConfigurationMain.setLong(FLD_SECTION_ID, leConfigurationDetailDepartement.getSectionId());
                

                pstLeaveConfigurationMain.update();
                return leConfigurationDetailDepartement.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationDetailDepartement(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstLeaveConfigurationDetailDepartement pstLeaveConfigurationMain = new PstLeaveConfigurationDetailDepartement(oid);
            pstLeaveConfigurationMain.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationDetailDepartement(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN_DEPARTEMENT;
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
                LeaveConfigurationDetailDepartement leConfigurationDetailDepartement = new LeaveConfigurationDetailDepartement();
                resultToObject(rs, leConfigurationDetailDepartement);
                lists.add(leConfigurationDetailDepartement);
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
     * mencari leaveconfigdept yg dipilih
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
     public static LeaveConfigurationDetailDepartement listJoin(int limitStart, int recordToGet, String whereClause, String order) {
        LeaveConfigurationDetailDepartement leConfigurationDetailDepartement = new LeaveConfigurationDetailDepartement();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT md.*,dpt."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+",sec."+PstSection.fieldNames[PstSection.FLD_SECTION]+ " FROM "+PstLeaveConfigurationDetailDepartement.TBL_HR_LEAVE_CONFIGURATION_MAIN_DEPARTEMENT + " AS md "
            + " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT + " AS dpt ON md."+PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_DEPARTEMENT_ID] + "=dpt."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
            + " LEFT JOIN  "+PstSection.TBL_HR_SECTION  + " AS sec ON sec."+PstSection.fieldNames[PstSection.FLD_SECTION_ID] + "=md."+PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_SECTION_ID];
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
            String departmentId = "";
            String departmentName = "";
           
            while (rs.next()) {
                departmentId = departmentId + rs.getLong("md."+PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_DEPARTEMENT_ID])+",";
                departmentName = departmentName + rs.getString("dpt."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT])+",";
                leConfigurationDetailDepartement.setSectionId(rs.getLong(PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_SECTION_ID]));
                leConfigurationDetailDepartement.setOID(rs.getLong(PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_LEAVE_CONFIGURATION_MAIN_ID]));
                leConfigurationDetailDepartement.setSectionName(rs.getString("sec."+PstSection.fieldNames[PstSection.FLD_SECTION]));
            }
            if(departmentId!=null && departmentId.length()>0){
                departmentId = departmentId.substring(0, departmentId.length()-1);
                departmentName = departmentName.substring(0,departmentName.length()-1);
                leConfigurationDetailDepartement.setDepartementIds(departmentId.split(",")); 
                leConfigurationDetailDepartement.setDepartementNama(departmentName); 
            }
            rs.close();
           

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
             return leConfigurationDetailDepartement;
        }
    }

    
    public static void resultToObject(ResultSet rs, LeaveConfigurationDetailDepartement leConfigurationDetailDepartement) {
        try {
            leConfigurationDetailDepartement.setOID(rs.getLong(PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_LEAVE_CONFIGURATION_MAIN_ID]));
            leConfigurationDetailDepartement.setDepartementId(rs.getLong(PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_DEPARTEMENT_ID]));
            leConfigurationDetailDepartement.setSectionId(rs.getLong(PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_SECTION_ID]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long oidMain) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN_DEPARTEMENT + " WHERE "
                    + PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_LEAVE_CONFIGURATION_MAIN_ID] + " = " + oidMain;

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
            String sql = "SELECT COUNT(" + PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_LEAVE_CONFIGURATION_MAIN_ID] + ") FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN_DEPARTEMENT;
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
                    LeaveConfigurationDetailDepartement leConfigurationDetailDepartement = (LeaveConfigurationDetailDepartement) list.get(ls);
                    if (oid == leConfigurationDetailDepartement.getOID()) {
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
    
    public static long deleteDetailConfigurationDepartment(long oid) {
        PstLeaveConfigurationDetailDepartement pstObj = new PstLeaveConfigurationDetailDepartement();
        DBResultSet dbrs = null;
        try {
            String sql = "DELETE FROM " + pstObj.getTableName()
                    + " WHERE " + PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_LEAVE_CONFIGURATION_MAIN_ID]
                    + " = '" + oid + "'";

            int status = DBHandler.execUpdate(sql);
            return oid;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }
    
      public static long insert(LeaveConfigurationDetailDepartement entObj) {
        try {
            PstLeaveConfigurationDetailDepartement pstObj = new PstLeaveConfigurationDetailDepartement(0);


            pstObj.setLong(FLD_LEAVE_CONFIGURATION_MAIN_ID, entObj.getLeaveConfigurationMainId());
            pstObj.setLong(FLD_DEPARTEMENT_ID, entObj.getDepartementId());
            pstObj.setLong(FLD_SECTION_ID, entObj.getSectionId());

            pstObj.insert();
            return entObj.getLeaveConfigurationMainId();
        } catch (DBException e) {
            System.out.println(e);
        }
        return 0;
    }
    
     public static boolean setDetailConfigurationDepartment(long oidLeaveMainConfig, Vector vGroupDept) {
 
        // do delete
        if (PstLeaveConfigurationDetailDepartement.deleteDetailConfigurationDepartment(oidLeaveMainConfig) == 0) {
            return false;
        }

        if (vGroupDept == null || vGroupDept.size() == 0) {
            return true;
        }

        // than insert
        for (int i = 0; i < vGroupDept.size(); i++) {
            LeaveConfigurationDetailDepartement leaveConfigurationDetailDepartement = (LeaveConfigurationDetailDepartement) vGroupDept.get(i);
            leaveConfigurationDetailDepartement.setLeaveConfigurationMainId(oidLeaveMainConfig); 
            if (PstLeaveConfigurationDetailDepartement.insert(leaveConfigurationDetailDepartement) == 0) {
                return false;
            }
        }
        return true;
    }
}
