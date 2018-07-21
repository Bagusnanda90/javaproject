/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.masterdatadesktop.entity;

import com.dimata.harisma.entity.admin.AppUser;
import com.dimata.harisma.entity.admin.PstAppPrivilegeObj;
import com.dimata.harisma.entity.admin.PstAppUser;
import com.dimata.harisma.entity.admin.PstGroupPriv;
import com.dimata.harisma.entity.admin.PstUserGroup;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.session.admin.SessUserSession;
import com.dimata.harisma.utility.harisma.machine.db.DBException;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.db.DBResultSet;
import com.dimata.harisma.utility.harisma.machine.db.I_DBInterface;
import com.dimata.harisma.utility.harisma.machine.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_Persintent;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class PstAppUserDesktop {

    private AppUser appUser = new AppUser();
    private Vector privObj = new Vector();

    public int doLogin(String loginID, String password) {
        AppUser user = PstAppUserDesktop.getByLoginIDAndPassword(loginID, password);
//        System.out.println("user.getOID() : "+user.getOID());
        if (user == null) {
//            System.out.println("DO_LOGIN_SYSTEM_FAIL");
            return SessUserSession.DO_LOGIN_SYSTEM_FAIL;
        }
        if (user.getOID() == 0) {
//            System.out.println("DO_LOGIN_NOT_VALID");
            return SessUserSession.DO_LOGIN_NOT_VALID;
        }

        user.setLastLoginIp(this.appUser.getLastLoginIp());
        user.setUserStatus(AppUser.STATUS_LOGIN);
        user.setLastLoginDate(new Date());

        this.appUser = user;

        privObj = PstAppUserDesktop.getUserPrivObj(this.appUser.getOID());

        if (privObj == null) {
            privObj = new Vector(1, 1);
//            System.out.println("DO_LOGIN_GET_PRIV_ERROR");
            return SessUserSession.DO_LOGIN_GET_PRIV_ERROR;
        }
//        System.out.println(" User login OK status (DO_LOGIN_OK) ->" + appUser.getUserStatus());        
        return SessUserSession.DO_LOGIN_OK;
    }

    public static AppUser getByLoginIDAndPassword(String loginID, String password) {
        if ((loginID == null) || (loginID.length() < 1) || (password == null) || (password.length() < 1)) {
            return null;
        }
        try {
            String whereClause = " " + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID] + "='" + loginID.trim() + "' AND "
                    + PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD] + "='" + password.trim() + "'";
            Vector appUsers = listFullObj(0, 0, whereClause, "");
            if ((appUsers == null) || (appUsers.size() != 1)) {
                return new AppUser();
            }
            return (AppUser) appUsers.get(0);
        } catch (Exception e) {
            System.out.println("getByLoginIDAndPassword " + e);
            return null;
        }
    }

    public static Vector getUserPrivObj(long userOID) {
        if (userOID == 0) {
            return new Vector(1, 1);
        }
        DBResultSet dbrs = null;
        try {
            String sql =
                    "SELECT DISTINCT(PO." + PstAppPrivilegeObj.fieldNames[PstAppPrivilegeObj.FLD_CODE] + ") AS CODE FROM " + PstAppPrivilegeObj.TBL_APP_PRIVILEGE_OBJ + " AS PO "
                    + " INNER JOIN " + PstGroupPriv.TBL_GROUP_PRIV + " AS GP ON GP." + PstGroupPriv.fieldNames[PstGroupPriv.FLD_PRIV_ID] + "=PO." + PstAppPrivilegeObj.fieldNames[PstAppPrivilegeObj.FLD_PRIV_ID]
                    + " INNER JOIN " + PstUserGroup.TBL_USER_GROUP + " AS UG ON UG." + PstUserGroup.fieldNames[PstUserGroup.FLD_GROUP_ID] + "=GP." + PstGroupPriv.fieldNames[PstGroupPriv.FLD_GROUP_ID]
                    + " WHERE UG." + PstUserGroup.fieldNames[PstUserGroup.FLD_USER_ID] + "='" + userOID + "'";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            Vector privObjs = new Vector(10, 2);
            while (rs.next()) {
                privObjs.add(new Integer(rs.getInt("CODE")));
            }
            return privObjs;
        } catch (Exception e) {
            System.out.println("getUserPrivObj " + e);
        } finally {
            DBResultSet.close(dbrs);
        }

        return new Vector(1, 1);
    }

    public static Vector listFullObj(int start, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null; 
        try {
            String sql = "SELECT " + PstAppUser.fieldNames[PstAppUser.FLD_USER_ID]
                    + " FROM " + PstAppUser.TBL_APP_USER;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if ((start == 0) && (recordToGet == 0)) {
                sql = sql + "";  //nothing to do
            } else {
                sql = sql + " LIMIT " + start + "," + recordToGet;
            }
//            System.out.println(sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AppUser appUser = new AppUser();
                appUser = PstAppUserDesktop.fetch(rs.getLong(PstAppUser.fieldNames[PstAppUser.FLD_USER_ID]));
                lists.add(appUser);
            }
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    private static void resultToObject(ResultSet rs, AppUser entObj) {
        try {
            entObj.setOID(rs.getLong(PstAppUser.fieldNames[PstAppUser.FLD_USER_ID]));
            entObj.setLoginId(rs.getString(PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]));
            entObj.setFullName(rs.getString(PstAppUser.fieldNames[PstAppUser.FLD_FULL_NAME]));
            entObj.setEmail(rs.getString(PstAppUser.fieldNames[PstAppUser.FLD_EMAIL]));
            entObj.setUserStatus(rs.getInt(PstAppUser.fieldNames[PstAppUser.FLD_USER_STATUS]));
            entObj.setEmployeeId(rs.getLong(PstAppUser.fieldNames[PstAppUser.FLD_EMPLOYEE_ID]));
        } catch (Exception e) {
            System.out.println("resultToObject() appuser -> : " + e.toString());
        }
    }

    public static AppUser fetch(long oid) {
        AppUser appUser = new AppUser();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * "
                    + " FROM " + PstAppUser.TBL_APP_USER + " WHERE " + PstAppUser.fieldNames[PstAppUser.FLD_USER_ID] + "=" + oid;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                appUser = new AppUser();
                resultToObject(rs, appUser);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return appUser;
        }
    }
}
