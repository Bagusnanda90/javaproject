/*
 * SessUserSession.java
 *
 * Created on April 11, 2002, 6:54 AM
 */
package com.dimata.harisma.session.admin;

import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.admin.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.lang.I_Dictionary;
import java.util.*;

/**
 *
 * @author ktanjana
 * @version
 * @objective represent access session of a user loggin into he system
 */
public class SessUserSession {

    public final static String HTTP_SESSION_NAME = "USER_SESSION";
    public final static int DO_LOGIN_OK = 0;
    public final static int DO_LOGIN_ALREADY_LOGIN = 1;
    public final static int DO_LOGIN_NOT_VALID = 2;
    public final static int DO_LOGIN_SYSTEM_FAIL = 3;
    public final static int DO_LOGIN_GET_PRIV_ERROR = 4;
    public final static int DO_LOGIN_NO_PRIV_ASIGNED = 5;
    //update by satrya 2014-02-13
    public final static int DO_LOGIN_WITH_PIN = 0;
    public final static int DO_LOGIN_WITH_USERNAME = 1;
    public final static String[] soLoginTxt = {
        "Login succeed",
        "User is already logged in",
        "Login ID or Password are invalid",
        "System cannot login you",
        "Can't get privilege",
        "No access asigned, please contact your system administrator"
    };
    private Vector privObj = new Vector();
    private AppUser appUser = new AppUser();
    private Employee employee = new Employee();
    /* Update by Hendra Putu | 20151010 | Division */
    private Division division = new Division();
    private Department department = new Department();
    private Section section = new Section();
    private Position position = new Position();
    private Vector brandGeneric = new Vector(1, 1);
    private I_Dictionary userDictionary;
    // login by Employee 
    private Hashtable<String, Boolean> appG1G2 = new Hashtable(); // contains privilege level G1+G2(key) -> True  added by kartika 2050326 

    /* CVS */
    /**
     * Creates new SessUserSession
     */
    public SessUserSession() {
        appUser.setUserStatus(AppUser.STATUS_LOGOUT);
    }

    public SessUserSession(String hostIP) {
        appUser.setUserStatus(AppUser.STATUS_LOGOUT);
        appUser.setLastLoginIp(hostIP);
    }

    public boolean isLoggedIn() {
        if ( /*(this.appUser!=null) && (this.appUser.getOID()!=0) && */(this.appUser.getUserStatus() == AppUser.STATUS_LOGIN)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPrivilege(int objCode) {
        if (!isLoggedIn()) {
            return false;
        }
        return SessAppPrivilegeObj.existCode(this.privObj, objCode);
    }

    public boolean checkPrivG1G2(int privG1, int privG2) {
        if (!isLoggedIn()) {
            return false;
        }
        if (appG1G2 == null) {
            appG1G2 = new Hashtable();
        }
        String strG1G2 = "" + AppObjInfo.composeObjCode(privG1, privG2);
        Boolean privG1G2 = appG1G2.contains(strG1G2);
        if ((privG1G2 == null || privG1G2 == false) && this.privObj != null) {
            for (int i = 0; i < this.privObj.size(); i++) {
                if (((privG1 << AppObjInfo.SHIFT_CODE_G1) + (privG2 << AppObjInfo.SHIFT_CODE_G2))
                        == ((((Integer) this.privObj.get(i)).intValue()) & (AppObjInfo.FILTER_CODE_G1 + AppObjInfo.FILTER_CODE_G2))) {
                    appG1G2.put(strG1G2, new Boolean(true));
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    public boolean checkPrivG1(int privG1) {
        if (!isLoggedIn()) {
            return false;
        }
        if (appG1G2 == null) {
            appG1G2 = new Hashtable();
        }
        String strG1 = "" + AppObjInfo.composeObjCode(privG1);
        Boolean bPrivG1 = appG1G2.contains(strG1);
        if ((bPrivG1 == null || bPrivG1 == false) && this.privObj != null) {
            for (int i = 0; i < this.privObj.size(); i++) {
                if ((privG1 << AppObjInfo.SHIFT_CODE_G1)
                        == ((((Integer) this.privObj.get(i)).intValue()) & (AppObjInfo.FILTER_CODE_G1))) {
                    appG1G2.put(strG1, new Boolean(true));
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    public int doLogin(String loginID, String password) {
        AppUser user = PstAppUser.getByLoginIDAndPassword(loginID, password);
//        System.out.println("user.getOID() : "+user.getOID());
        if (user == null) {
//            System.out.println("DO_LOGIN_SYSTEM_FAIL");
            return DO_LOGIN_SYSTEM_FAIL;
        }

        if (user.getOID() == 0) {
//            System.out.println("DO_LOGIN_NOT_VALID");
            return DO_LOGIN_NOT_VALID;
        }

        /* un comment this to enable checking of user host IP */
        /*         
         if( (user.getUserStatus()==AppUser.STATUS_LOGIN) &&
         !(this.appUser.getLastLoginIp().equals(user.getLastLoginIp())))
         return DO_LOGIN_ALREADY_LOGIN;
         **/

        user.setLastLoginIp(this.appUser.getLastLoginIp());
        user.setUserStatus(AppUser.STATUS_LOGIN);
        user.setLastLoginDate(new Date());

//        if(PstAppUser.updateUserByLoggedIn(user)==0)
//        {
//            System.out.println("DO_LOGIN_SYSTEM_FAIL");
//            return DO_LOGIN_SYSTEM_FAIL;
//        }    

        this.appUser = user;

        privObj = PstAppUser.getUserPrivObj(this.appUser.getOID());

        if (privObj == null) {
            privObj = new Vector(1, 1);
//            System.out.println("DO_LOGIN_GET_PRIV_ERROR");
            return DO_LOGIN_GET_PRIV_ERROR;
        }

//        System.out.println(" User login OK status (DO_LOGIN_OK) ->" + appUser.getUserStatus());        
        return DO_LOGIN_OK;
    }

    public void doLogout() {
        if ((this.appUser != null) && (this.appUser.getUserStatus() == AppUser.STATUS_LOGIN)) {

            long updateStatusResult = PstAppUser.updateUserStatus(appUser.getOID(), AppUser.STATUS_LOGOUT);
            if (updateStatusResult != 0) {
                System.out.println("Do logout success, user status : " + AppUser.statusTxt[AppUser.STATUS_LOGOUT]);
            } else {
                System.out.println("Do logout failed, user status : " + AppUser.statusTxt[appUser.getUserStatus()]);
            }
        }
    }

    public void printAppUser() {
        System.out.println(" ==== AppUser ====");
        System.out.println(" user ID = " + this.appUser.getOID());
        System.out.println(" login ID = " + this.appUser.getLoginId());
        System.out.println(" employee = " + this.appUser.getEmployeeId());
        System.out.println(" status = " + this.appUser.getUserStatus());
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(long employeeId) {
        appUser.setEmployeeId(employeeId);
    }

    public void setEmployee() {
        // this.setEmployee(getEmployee());
        try {
            if (appUser.getEmployeeId() != 0) {
//                                System.out.println("Fetch Employee");
                this.employee = PstEmployee.fetchExc(appUser.getEmployeeId());
                this.setDepartment();
                this.setDivision();
            } else {
                this.employee = new Employee();
            }
        } catch (Exception e) {
        }
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision() {
        try {
            if (this.getEmployee().getOID() != 0) {
                this.division = PstDivision.fetchExc(this.getEmployee().getDivisionId());
            } else {
                this.division = new Division();
            }
        } catch (Exception e) {
            System.out.println("SessUserSession.setDivision=>" + e.toString());
        }
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment() {
        //this.department = department;
        try {
            if (this.getEmployee().getOID() != 0) {
//                                System.out.println("Fetch Department");                            
                this.department = PstDepartment.fetchExc(this.getEmployee().getDepartmentId());

            } else {
                this.department = new Department();
            }
        } catch (Exception e) {
        }
    }

    public Section getSection() {
        return section;
    }

    public void setSection() {
        // this.section = section;
        try {
            if (this.getEmployee().getOID() != 0) {
//                                System.out.println("Fetch Section");                            
                this.section = PstSection.fetchExc(this.getEmployee().getSectionId());
            } else {
                this.section = new Section();
            }
        } catch (Exception e) {
        }
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition() {
        //this.position = position;
        try {
            if (this.getEmployee().getOID() != 0) {
//                                System.out.println("Fetch Position");                            
                this.position = PstPosition.fetchExc(this.getEmployee().getPositionId());
            } else {
                this.position = new Position();
            }
        } catch (Exception e) {
        }
    }

    public void setUserHrdData() {
        this.setEmployee();
        this.setDepartment();
        this.setSection();
        this.setPosition();
        this.setBrandGeneric(this.brandGeneric);
    }

    public Vector getBrandGeneric() {
        return brandGeneric;
    }

    public void setBrandGeneric(Vector brandGeneric) {
        this.brandGeneric = brandGeneric;
    }

    /**
     * @Aythor Roy A.
     * @Desc Untuk mendapatkan System property Application Configurasi
     * @return String system property
     */
    public static String getSystenPropertyApplication() {

        String valueSysConf = "";

        try {
            valueSysConf = PstSystemProperty.getValueByName("CONFIGURASI_HARISMA");

            //valueSysConf = valueSysConf.

            if (valueSysConf.compareTo("") != 0) {
                return valueSysConf;
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
            return valueSysConf;
        }

    }

    /**
     * @return the userDictionary
     */
    public I_Dictionary getUserDictionary() {
        return userDictionary;
    }

    /**
     * @param userDictionary the userDictionary to set
     */
    public void setUserDictionary(I_Dictionary userDictionary) {
        this.userDictionary = userDictionary;
    }
}
