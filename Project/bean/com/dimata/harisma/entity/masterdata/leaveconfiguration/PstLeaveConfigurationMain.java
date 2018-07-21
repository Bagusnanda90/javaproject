/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Satrya Ramayu
 * @version  	:  [version]
 */
/**
 * *****************************************************************
 * Class Description : PstLeaveConfigurationMain Imput Parameters : [input
 * parameter ...] Output : [output ...]
 ******************************************************************
 */
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

public class PstLeaveConfigurationMain extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_LEAVE_CONFIGURATION_MAIN = "hr_leave_configuration_main";//"hr_company";
    public static final int FLD_LEAVE_CONFIGURATION_MAIN_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final int FLD_PLUS_NOTE_APPROVALL = 2;
    public static final String[] fieldNames = {
        "LEAVE_CONFIGURATION_MAIN_ID",
        "EMPLOYEE_ID",
        "PLUS_NOTE_APPROVALL"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING
    };

    public PstLeaveConfigurationMain() {
    }

    public PstLeaveConfigurationMain(int i) throws DBException {
        super(new PstLeaveConfigurationMain());
    }

    public PstLeaveConfigurationMain(String sOid) throws DBException {
        super(new PstLeaveConfigurationMain(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstLeaveConfigurationMain(long lOid) throws DBException {
        super(new PstLeaveConfigurationMain(0));
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
        return TBL_HR_LEAVE_CONFIGURATION_MAIN;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstLeaveConfigurationMain().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        LeaveConfigurationMain leaveConfigurationMain = fetchExc(ent.getOID());
        ent = (Entity) leaveConfigurationMain;
        return leaveConfigurationMain.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((LeaveConfigurationMain) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((LeaveConfigurationMain) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static LeaveConfigurationMain fetchExc(long oid) throws DBException {
        try {
            LeaveConfigurationMain leaveConfigurationMain = new LeaveConfigurationMain();
            PstLeaveConfigurationMain pstLeaveConfigurationMain = new PstLeaveConfigurationMain(oid);
            leaveConfigurationMain.setOID(oid);

            leaveConfigurationMain.setEmployeeId(pstLeaveConfigurationMain.getlong(FLD_EMPLOYEE_ID));
            leaveConfigurationMain.setPlusNoteApprovall(pstLeaveConfigurationMain.getString(FLD_PLUS_NOTE_APPROVALL));

            return leaveConfigurationMain;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationMain(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(LeaveConfigurationMain leaveConfigurationMain) throws DBException {
        try {
            PstLeaveConfigurationMain pstLeaveConfigurationMain = new PstLeaveConfigurationMain(0);

            pstLeaveConfigurationMain.setLong(FLD_EMPLOYEE_ID, leaveConfigurationMain.getEmployeeId());
            pstLeaveConfigurationMain.setString(FLD_PLUS_NOTE_APPROVALL, leaveConfigurationMain.getPlusNoteApprovall());


            pstLeaveConfigurationMain.insert();
            leaveConfigurationMain.setOID(pstLeaveConfigurationMain.getlong(FLD_LEAVE_CONFIGURATION_MAIN_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationMain(0), DBException.UNKNOWN);
        }
        return leaveConfigurationMain.getOID();
    }

    public static long updateExc(LeaveConfigurationMain leaveConfigurationMain) throws DBException {
        try {
            if (leaveConfigurationMain.getOID() != 0) {
                PstLeaveConfigurationMain pstLeaveConfigurationMain = new PstLeaveConfigurationMain(leaveConfigurationMain.getOID());

                pstLeaveConfigurationMain.setLong(FLD_EMPLOYEE_ID, leaveConfigurationMain.getEmployeeId());
                pstLeaveConfigurationMain.setString(FLD_PLUS_NOTE_APPROVALL, leaveConfigurationMain.getPlusNoteApprovall());


                pstLeaveConfigurationMain.update();
                return leaveConfigurationMain.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationMain(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstLeaveConfigurationMain pstLeaveConfigurationMain = new PstLeaveConfigurationMain(oid);
            pstLeaveConfigurationMain.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveConfigurationMain(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN;
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
                LeaveConfigurationMain leaveConfigurationMain = new LeaveConfigurationMain();
                resultToObject(rs, leaveConfigurationMain);
                lists.add(leaveConfigurationMain);
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

    public static Vector listJoinEmployee(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT cm.*,emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " FROM " + PstLeaveConfigurationMain.TBL_HR_LEAVE_CONFIGURATION_MAIN + " AS cm "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON cm." + PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
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
                LeaveConfigurationMain leaveConfigurationMain = new LeaveConfigurationMain();
                resultToObject(rs, leaveConfigurationMain);
                leaveConfigurationMain.setNamaEmployee(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                lists.add(leaveConfigurationMain);
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
     * untuk mencari approval leave
     * @param departmentId
     * @param positionLevel
     * @param sectionId
     * @param limitStart
     * @param recordToGet
     * @param order
     * @return 
     */
    public static Vector listDepHeadApprovall(String departmentId, int positionLevel, long sectionId, int limitStart, int recordToGet,String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        DBResultSet dbrs2 = null;
        try {
            String sql = "SELECT cm.*,emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS] + " FROM " + PstLeaveConfigurationMain.TBL_HR_LEAVE_CONFIGURATION_MAIN + " AS cm "
                    + " INNER JOIN " + PstLeaveConfigurationDetailDepartement.TBL_HR_LEAVE_CONFIGURATION_MAIN_DEPARTEMENT + " AS cmd  ON cm." + PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_LEAVE_CONFIGURATION_MAIN_ID] + "=cmd." + PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_LEAVE_CONFIGURATION_MAIN_ID]
                    + " INNER JOIN " + PstLeaveConfigurationDetailPosition.TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION + " AS cmp ON cm." + PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_LEAVE_CONFIGURATION_MAIN_ID] + "=cmp." + PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_LEAVE_CONFIGURATION_MAIN_ID]
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON cm." + PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE (" + positionLevel + " BETWEEN cmp." + PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MIN_ID] + " AND cmp." + PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MAX_ID] + ")";
            if (departmentId != null && departmentId.length() > 0) {
                sql = sql + " and cmd." + PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_DEPARTEMENT_ID] + " IN(" + departmentId + ")";
            }
            if (sectionId != 0) {
                sql = sql + " and cmd." + PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_SECTION_ID] + " IN(" + sectionId + ")";
            }
            
                sql = sql + " and emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =  "+PstEmployee.NO_RESIGN;
            

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
                LeaveConfigurationMain leaveConfigurationMain = new LeaveConfigurationMain();
                resultToObject(rs, leaveConfigurationMain);
                leaveConfigurationMain.setNamaEmployee(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                leaveConfigurationMain.setEmailEmployee(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]));
                lists.add(leaveConfigurationMain);
            }
            rs.close();
            
            
            if (lists.size() == 0) {
                            String sql2 = "SELECT cm.*,emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS] + " FROM " + PstLeaveConfigurationMain.TBL_HR_LEAVE_CONFIGURATION_MAIN + " AS cm "
                                    + " INNER JOIN " + PstLeaveConfigurationDetailDepartement.TBL_HR_LEAVE_CONFIGURATION_MAIN_DEPARTEMENT + " AS cmd  ON cm." + PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_LEAVE_CONFIGURATION_MAIN_ID] + "=cmd." + PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_LEAVE_CONFIGURATION_MAIN_ID]
                                    + " INNER JOIN " + PstLeaveConfigurationDetailPosition.TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION + " AS cmp ON cm." + PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_LEAVE_CONFIGURATION_MAIN_ID] + "=cmp." + PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_LEAVE_CONFIGURATION_MAIN_ID]
                                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON cm." + PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                                    + " WHERE (" + positionLevel + " BETWEEN cmp." + PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MIN_ID] + " AND cmp." + PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MAX_ID] + ")";
                            if (departmentId != null && departmentId.length() > 0) {
                                sql2 = sql2 + " and cmd." + PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_DEPARTEMENT_ID] + " IN(" + departmentId + ")";
                            }
                             sql2 = sql2 + " and emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " =  "+PstEmployee.NO_RESIGN;

                            if (order != null && order.length() > 0) {
                                sql2 = sql2 + " ORDER BY " + order;
                            }
                            if (limitStart == 0 && recordToGet == 0) {
                                sql2 = sql2 + "";
                            } else {
                                sql2 = sql2 + " LIMIT " + limitStart + "," + recordToGet;
                            }
                            dbrs2 = DBHandler.execQueryResult(sql2);
                            ResultSet rs2 = dbrs2.getResultSet();
                            while (rs2.next()) {
                                LeaveConfigurationMain leaveConfigurationMain = new LeaveConfigurationMain();
                                resultToObject(rs2, leaveConfigurationMain);
                                leaveConfigurationMain.setNamaEmployee(rs2.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                                leaveConfigurationMain.setEmailEmployee(rs2.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]));
                                lists.add(leaveConfigurationMain);
                            }
                            rs.close();
            }
            
            
            
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    
        /**
     * list join
     * @param departmentId
     * @param positionLevel
     * @param sectionId
     * @param limitStart
     * @param recordToGet
     * @param order
     * @return 
     */
    public static String listJoinDetail(long employeeId) {
        String listsEmp = "";
        if(employeeId==0){
            return listsEmp;
        }
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT cm.*,cmd."+PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_DEPARTEMENT_ID]+",cmd."+PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_SECTION_ID]
            +",cmp."+PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MIN_ID]+",cmp."+PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MAX_ID]
                    + " FROM " + PstLeaveConfigurationMain.TBL_HR_LEAVE_CONFIGURATION_MAIN + " AS cm "
                    + " INNER JOIN " + PstLeaveConfigurationDetailDepartement.TBL_HR_LEAVE_CONFIGURATION_MAIN_DEPARTEMENT + " AS cmd  ON cm." + PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_LEAVE_CONFIGURATION_MAIN_ID] + "=cmd." + PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_LEAVE_CONFIGURATION_MAIN_ID]
                    + " INNER JOIN " + PstLeaveConfigurationDetailPosition.TBL_HR_LEAVE_CONFIGURATION_MAIN_POSITION + " AS cmp ON cm." + PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_LEAVE_CONFIGURATION_MAIN_ID] + "=cmp." + PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_LEAVE_CONFIGURATION_MAIN_ID]
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON cm." + PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " WHERE cm."+PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_EMPLOYEE_ID]+"="+employeeId;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                String whereClause = "emp."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+rs.getLong("cmd."+PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_DEPARTEMENT_ID]) 
                                     + " AND pos."+PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + " BETWEEN "+rs.getLong("cmp."+PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MIN_ID]) +" AND "+rs.getLong("cmp."+PstLeaveConfigurationDetailPosition.fieldNames[PstLeaveConfigurationDetailPosition.FLD_POSITION_MAX_ID]);
                listsEmp = listsEmp + PstEmployee.listEmployeeId(whereClause, "");
                
            }
            rs.close();
            if(listsEmp!=null && listsEmp.length()>0){
                listsEmp = listsEmp.substring(0,listsEmp.length()-1);
            }
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return listsEmp;
        }
    }
    
    /**
     * create by satrya 2014-06-18
     * untuk list Company
     * @param whereClause
     * @param groupBy
     * @param order
     * @return 
     */
    public static Vector listCompLeaveConfig(String whereClause,String order) {
        Vector result = new Vector();
        DBResultSet dbrs = null;
        order = "comp."+PstCompany.fieldNames[PstCompany.FLD_COMPANY] + "," + "divs."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                + ",dpt." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
        try {
            String sql = "SELECT "
                    + " DISTINCT(dpt."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+"),dpt."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    +",divs."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]
                    +",divs."+PstDivision.fieldNames[PstDivision.FLD_DIVISION]
                    +",comp."+PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]
                    +",comp."+PstCompany.fieldNames[PstCompany.FLD_COMPANY]
                    +" FROM "+PstLeaveConfigurationMain.TBL_HR_LEAVE_CONFIGURATION_MAIN + " AS cm "
  + " INNER JOIN "+PstLeaveConfigurationDetailDepartement.TBL_HR_LEAVE_CONFIGURATION_MAIN_DEPARTEMENT + " AS cmd ON cm."+PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_LEAVE_CONFIGURATION_MAIN_ID]+"=cmd."+PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_LEAVE_CONFIGURATION_MAIN_ID]
  + " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT + " AS dpt ON dpt."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+"=cmd."+PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_DEPARTEMENT_ID]
  + " INNER JOIN "+PstDivision.TBL_HR_DIVISION + "  AS divs ON divs."+ PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"=dpt."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]
  + " INNER JOIN "+PstCompany.TBL_HR_COMPANY + " AS comp ON comp."+PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+"=divs."+PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]
  + " LEFT JOIN "+PstSection.TBL_HR_SECTION + " AS sec ON sec."+PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"=dpt."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
  + " WHERE (1=1)";
  if(whereClause!=null && whereClause.length()>0){
      //cm.`EMPLOYEE_ID`=504404544829772201
       sql = sql + " AND "+whereClause;
  }
  if (order != null && order.length() > 0) {
       sql = sql + " ORDER BY " + order;
  }
//  if(groupBy!=null && groupBy.length()>0){
//      //GROUP BY sec.`SECTION_ID`;
//      sql = sql + groupBy;
//  }
  
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while (rs.next()) {
                Department department = new Department();
                //PstDepartment.resultToObject(rs, department);
                department.setOID(rs.getLong("dpt."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]));
                department.setDepartment(rs.getString("dpt."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                //department.setDescription(rs.getString("dpt."+PstDepartment.fieldNames[PstDepartment.FLD_DESCRIPTION]));
                department.setDivisionId(rs.getLong("divs."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]));
                //department.setJoinToDepartmentId(rs.getLong("dpt."+PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID]));
            
                department.setCompany(rs.getString("comp."+PstCompany.fieldNames[PstCompany.FLD_COMPANY]));
                department.setDivision(rs.getString("divs."+PstDivision.fieldNames[PstDivision.FLD_DIVISION])); 
                //update by satrya 2013-09-16
                department.setCompanyId(rs.getLong(PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]));
                result.add(department);
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
        
    }

    public static void resultToObject(ResultSet rs, LeaveConfigurationMain leaveConfigurationMain) {
        try {
            leaveConfigurationMain.setOID(rs.getLong(PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_LEAVE_CONFIGURATION_MAIN_ID]));
            leaveConfigurationMain.setEmployeeId(rs.getLong(PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_EMPLOYEE_ID]));
            leaveConfigurationMain.setPlusNoteApprovall(rs.getString(PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_PLUS_NOTE_APPROVALL]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long oidMain) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN + " WHERE "
                    + PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_LEAVE_CONFIGURATION_MAIN_ID] + " = " + oidMain;

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
            String sql = "SELECT COUNT(" + PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_LEAVE_CONFIGURATION_MAIN_ID] + ") FROM " + TBL_HR_LEAVE_CONFIGURATION_MAIN;
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
                    LeaveConfigurationMain leaveConfigurationMain = (LeaveConfigurationMain) list.get(ls);
                    if (oid == leaveConfigurationMain.getOID()) {
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
